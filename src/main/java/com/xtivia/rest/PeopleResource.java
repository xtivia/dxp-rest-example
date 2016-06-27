package com.xtivia.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/*
  Demonstrates how to create multiple REST endpoints in a single JAX-RS resource
  via the use of annotated methods. Provides a sample in-memory CRUD operations for a list of people.
  
  Everything needed to implement the entire set of CRUD actions is included in this
  single source file including the value object used for marshalling data to/from the
  client, the endpoint methods, as well as utility functions used by the endpoints/routes.
 */

@Path("/people")
public class PeopleResource {
	
	/*
	 *  the class used for our data model (i.e. the class converted to/from JSON)
	 */
	public static class Person {
		
		public long   id = -1;
		public String lastName;
		public String firstName;
		public String location;
		
		public Person() {}
		
		public Person(long id, String first, String last, String location) {
			this.id = id;
			this.firstName = first;
			this.lastName = last;
			this.location = location;
		}	
	}
	
	// return all people
	@GET
	@Produces("application/json")
	public List<Person> getAllPeople() {
		return __people;
	}

	// return a single person based on supplied ID
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getPerson(@PathParam("id") String id) {
		ResponseBuilder builder;
		try {
		  Person person = findById(new Long(id));
		  if (person != null) {
			  builder = Response.ok(person);
		  } else {
			  builder = Response.status(Response.Status.NOT_FOUND).entity("Person not found for ID: " + id);
		  }
		} catch (Exception e) {
			builder = Response.status(Response.Status.NOT_FOUND).entity("Exception occurred for ID: " + id);
			e.printStackTrace();
		}
		return builder.build();
	}
		
    // add a new person
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Person addPerson(Person newPerson) {	
		// generate a trivial ID based on epoch time
		newPerson.id = new java.util.Date().getTime();
		__people.add(newPerson);
		return newPerson;
	}
	
	// update an existing person based on supplied ID
	@PUT
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatePerson(Person newPerson, @PathParam("id") String id) {
		  ResponseBuilder builder = null;
		  Person oldPerson = findById(new Long(id));
		  if (oldPerson != null) {
			  oldPerson.firstName = newPerson.firstName;
			  oldPerson.lastName = newPerson.lastName;
			  oldPerson.location = newPerson.location;
			  builder = Response.ok(oldPerson);
		  } else {
			  builder = Response.status(Response.Status.NOT_FOUND).entity("Person not found for ID: " + id);
		  }
		
		return builder.build();
	}
	
   // delete a single person based on supplied ID
   @DELETE
   @Path("{id}")
	public Response deletePerson(@PathParam("id") String id) {	
		ResponseBuilder builder =  Response.status(Response.Status.NOT_FOUND).entity("Person not found for ID: " + id);
		long pid = new Long(id);
		int ndx = 0;
		for (Person person : __people) {
			if (person.id == pid) {
				__people.remove(ndx);
				builder = Response.ok();
				break;
			} else {
				ndx++;
			}
		}
		return builder.build();
	}
	
	// 
	// Utility function used by multiple endpoints
	//
	private static Person findById(long id) {
		for (Person person : __people) {
			if (person.id == id) {
				return person;
			}
		}
		return null;
	}
	
	// some sample data for our demo resource
    private static List<Person> __people = new ArrayList<Person> ();
    static {
    	__people.add(new Person(1, "Daffy","Duck","Missouri"));
       	__people.add(new Person(2, "Minnie","Mouse","Ohio"));
       	__people.add(new Person(3, "Elmer","Fudd","Texas"));
       	__people.add(new Person(4, "Foghorn","Leghorn","South Carolina"));
       	__people.add(new Person(5, "Mother","Goose","New York"));
       	__people.add(new Person(6, "Bugs","Bunny","Colorado"));
    }
}
