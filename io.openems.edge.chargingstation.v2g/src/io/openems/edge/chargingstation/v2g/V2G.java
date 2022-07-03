package io.openems.edge.chargingstation.v2g;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.Designate;

import io.openems.common.channel.AccessMode;
import io.openems.common.channel.PersistencePriority;
import io.openems.common.channel.Unit;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.DoubleWriteChannel;
import io.openems.edge.common.channel.IntegerWriteChannel;
import io.openems.edge.common.channel.StringDoc;
import io.openems.edge.common.channel.StringWriteChannel;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;

public interface V2G extends OpenemsComponent, EventHandler {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		GRID_L1_VOLTS(Doc.of(OpenemsType.DOUBLE).unit(Unit.VOLT).accessMode(AccessMode.READ_WRITE)
				.persistencePriority(PersistencePriority.HIGH)),
		POWERUNIT_TEMPERATURE(Doc.of(OpenemsType.DOUBLE).unit(Unit.DEGREE_CELSIUS).accessMode(AccessMode.READ_WRITE)
				.persistencePriority(PersistencePriority.HIGH)),
		STATUS(Doc.of(OpenemsType.INTEGER).unit(Unit.NONE).accessMode(AccessMode.READ_WRITE)
				.persistencePriority(PersistencePriority.HIGH)),;

		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}
	}

	public default DoubleWriteChannel getGridL1VoltsChannel() {
		return this.channel(ChannelId.GRID_L1_VOLTS);
	}

	public default void _setGridL1Volts(double value) {
		this.getGridL1VoltsChannel().setNextValue(value);
	}

	public default DoubleWriteChannel getPowerUnitTemperatureChannel() {
		return this.channel(ChannelId.POWERUNIT_TEMPERATURE);
	}

	public default void _setPowerUnitTemperature(double value) {
		this.getPowerUnitTemperatureChannel().setNextValue(value);
	}

	public default IntegerWriteChannel getStatusChannel() {
		return this.channel(ChannelId.STATUS);
	}

	public default void _setStatus(int value) {
		this.getStatusChannel().setNextValue(value);
	}

	public Config getConfig();

}
