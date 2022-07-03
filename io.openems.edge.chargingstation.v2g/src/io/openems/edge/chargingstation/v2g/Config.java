package io.openems.edge.chargingstation.v2g;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "Charging Station V2G", //
		description = "")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "v2g0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;
	
	@AttributeDefinition(name = "IP-Address", description = "IP-Address of the V2G Charging Station API")
	String ipAddress() default "";

	@AttributeDefinition(name = "Port", description = "Port of the V2G Charging Station API")
	int port() default 443;

	String webconsole_configurationFactory_nameHint() default "Charging Station V2G [{id}]";

}