/*
 * A very simple JAX-RS resource for Liferay. Demonstrates how to obtain the user ID of the logged-in user and then
 * use the OSGi-injected UserLocalService to access the User object and return an abbreviated JSON reprsentation of
 * the logged in user.
 * 
 * A design decision was made to keep all OSGi-related (injected) artefacts in the parent Application class. That parent
 * class is the only class in this example that is exposed as a service in terms of OSGi, and thus the only one that 
 * contains injectable references such as the UserLocalService. The application class is passed into the constructor
 * for this class and used as a holder for services provided by OSGi.
 */
package com.xtivia.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

@Path("/users")
public class LiferayUserResource {
	
	SampleRESTApplication _app;
	
	public LiferayUserResource(SampleRESTApplication app) {
	   this._app = app;	
	}
	
	@GET
	@Path("/current")
	@Produces("application/json")
	public LiferayUser getCurrentUser(@Context HttpServletRequest request) {
		
		/* NOTE: the JEE standard request.getRemoteUser() returns a string representation the numeric ID of the logged-in user.
		 * If no user is logged in yet this ID is currently (GA2) the ID of the Liferay 'default' user. But we also
		 * check for null 'just in case'
		 */
		UserLocalService userLocalService = _app.getUserLocalService();
		String userid = request.getRemoteUser();
		LiferayUser liferayUser = null;
		
		try {
			if (userid != null && userLocalService != null) {
				User user = userLocalService.getUser(new Long(userid));
				if (user != null) {
					liferayUser = new LiferayUser(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (liferayUser != null) {
			return liferayUser;
		} else {
		    throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
