package io.openems.impl.device.pro;

import io.openems.api.channel.numeric.NumericChannel;
import io.openems.api.device.nature.MeterNature;
import io.openems.api.exception.ConfigException;
import io.openems.impl.protocol.modbus.ModbusChannel;
import io.openems.impl.protocol.modbus.ModbusDeviceNature;
import io.openems.impl.protocol.modbus.internal.ElementBuilder;
import io.openems.impl.protocol.modbus.internal.ModbusProtocol;
import io.openems.impl.protocol.modbus.internal.ModbusRange;
import io.openems.impl.protocol.modbus.internal.channel.ModbusChannelBuilder;

public class FeneconProNapMeter extends ModbusDeviceNature implements MeterNature {

	private final ModbusChannel _reactivePower = new ModbusChannelBuilder().nature(this).unit("var").build();
	private final ModbusChannel _activePowerPhaseA = new ModbusChannelBuilder().nature(this).unit("W").delta(-10000)
			.build();
	private final ModbusChannel _activePowerPhaseB = new ModbusChannelBuilder().nature(this).unit("W").delta(-10000)
			.build();
	private final ModbusChannel _activePowerPhaseC = new ModbusChannelBuilder().nature(this).unit("W").delta(-10000)
			.build();
	private final ModbusChannel _activePower = new ModbusChannelBuilder().nature(this).unit("W").build();

	public FeneconProNapMeter(String thingId) {
		super(thingId);
	}

	@Override
	public NumericChannel activeNegativeEnergy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericChannel activePositiveEnergy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericChannel activePower() {
		return _activePower;
	}

	@Override
	public NumericChannel apparentEnergy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericChannel apparentPower() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericChannel reactiveNegativeEnergy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericChannel reactivePositiveEnergy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumericChannel reactivePower() {
		return _reactivePower;
	}

	@Override
	protected ModbusProtocol defineModbusProtocol() throws ConfigException {
		return new ModbusProtocol( //
				new ModbusRange(139, //
						new ElementBuilder().address(139).channel(_activePower).signed().build(), //
						new ElementBuilder().address(140).channel(_reactivePower).signed().build() //
				), new ModbusRange(2018, //
						new ElementBuilder().address(2018).channel(_activePowerPhaseA).build()),
				new ModbusRange(2118, //
						new ElementBuilder().address(2118).channel(_activePowerPhaseB).build()),
				new ModbusRange(2218, //
						new ElementBuilder().address(2218).channel(_activePowerPhaseC).build()));
	}

}
