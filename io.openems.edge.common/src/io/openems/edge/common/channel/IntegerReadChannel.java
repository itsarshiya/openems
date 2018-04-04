package io.openems.edge.common.channel;

import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.doc.ChannelId;
import io.openems.edge.common.component.OpenemsComponent;

public class IntegerReadChannel extends AbstractReadChannel<Integer> {

	public IntegerReadChannel(OpenemsComponent component, ChannelId channelId) {
		super(OpenemsType.INTEGER, component, channelId);
	}

}
