/*
 * A simple configuration object for this REST services bundle
 * For now it doesn't really do anything other than provide an
 * example of how a rich configuration object can be defined
 * instead of simple strings.
 * 
 * To edit this configuration based on the definition below,
 * use the Liferay Control Panel, navigate to System Settings,
 * then Foundation, and select/search for 'Sample_REST_Configuration'
 */
package com.xtivia.rest;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@ExtendedObjectClassDefinition(category = "foundation", scope = ExtendedObjectClassDefinition.Scope.SYSTEM	)

@Meta.OCD(
		factory = true,
		id = "com.xtivia.rest.SampleRESTConfiguration",
		localization = "content/Language", 
		name = "Sample_REST_Configuration"
)		
public interface SampleRESTConfiguration {
	
	@Meta.AD(description= "Information", deflt="unknown", required=false)
	public String info();

	@Meta.AD(description= "Ranking", deflt="5", required=false)
	public int infoNum();

}