package io.openems.edge.controller.symmetric.balancing;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsException;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.controller.api.Controller;
import io.openems.edge.ess.api.ManagedSymmetricEss;
import io.openems.edge.ess.api.SymmetricEss;
import io.openems.edge.ess.api.SymmetricEss.GridMode;
import io.openems.edge.ess.power.api.Phase;
import io.openems.edge.ess.power.api.Pwr;
import io.openems.edge.meter.api.SymmetricMeter;

@Designate(ocd = Config.class, factory = true)
@Component(name = "Controller.Symmetric.Balancing", immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class Balancing extends AbstractOpenemsComponent implements Controller, OpenemsComponent {

	private final Logger log = LoggerFactory.getLogger(Balancing.class);

	public Balancing() {
		Utils.initializeChannels(this).forEach(channel -> this.addChannel(channel));
	}

	@Reference
	protected ComponentManager componentManager;

	private Config config;

	@Activate
	void activate(ComponentContext context, Config config) {
		super.activate(context, config.service_pid(), config.id(), config.enabled());
		this.config = config;
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	/**
	 * Calculates required charge/discharge power.
	 * 
	 * @param ess   the Ess
	 * @param meter the Meter
	 * @return the required power
	 */
	private int calculateRequiredPower(ManagedSymmetricEss ess, SymmetricMeter meter) {
		return meter.getActivePower().value().orElse(0) /* current buy-from/sell-to grid */
				+ ess.getActivePower().value().orElse(0) /* current charge/discharge Ess */;
	}

	@Override
	public void run() {
		ManagedSymmetricEss ess = this.componentManager.getComponent(this.config.ess_id());
		SymmetricMeter meter = this.componentManager.getComponent(this.config.meter_id());

		/*
		 * Check that we are On-Grid (and warn on undefined Grid-Mode)
		 */
		GridMode gridMode = ess.getGridMode().value().asEnum();
		if (gridMode.isUndefined()) {
			this.logWarn(this.log, "Grid-Mode is [UNDEFINED]");
		}
		if (gridMode != SymmetricEss.GridMode.ON_GRID) {
			return;
		}
		/*
		 * Calculates required charge/discharge power
		 */
		int calculatedPower = this.calculateRequiredPower(ess, meter);

		// adjust value so that it fits into Min/MaxActivePower
		calculatedPower = ess.getPower().fitValueIntoMinMaxActivePower(ess, Phase.ALL, Pwr.ACTIVE, calculatedPower);

		/*
		 * set result
		 */
		try {
			ess.getSetActivePowerEquals().setNextWriteValue(calculatedPower);
			ess.getSetReactivePowerEquals().setNextWriteValue(0);
		} catch (OpenemsException e) {
			this.logError(this.log, e.getMessage());
		}
	}
}
