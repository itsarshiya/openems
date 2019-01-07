package io.openems.edge.simulator.ess.asymmetric.reacting;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "Simulator EssAsymmetric Reacting", //
		description = "This simulates a 'reacting' asymmetric Energy Storage System.")
@interface Config {
	String service_pid();

	String id() default "ess0";

	boolean enabled() default true;

	@AttributeDefinition(name = "Datasource-ID", description = "ID of Simulator Datasource.")
	String datasource_id() default "datasource0";

	@AttributeDefinition(name = "Max Apparant Power [VA]")
	int maxApparentPower() default 10000;

	@AttributeDefinition(name = "Capacity [Wh]")
	int capacity() default 10000;

	@AttributeDefinition(name = "Initial State of Charge [%]")
	int initialSoc() default 50;

	@AttributeDefinition(name = "Datasource target filter", description = "This is auto-generated by 'Datasource-ID'.")
	String datasource_target() default "";

	String webconsole_configurationFactory_nameHint() default "Simulator EssAsymmetric Reacting [{id}]";
}