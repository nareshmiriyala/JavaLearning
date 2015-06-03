package shop.services;

import shop.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customersjaxb")
public class CustomerResourceJaxB
{
   private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
   private AtomicInteger idCounter = new AtomicInteger();

   @POST
   @Consumes("application/example-java")
   public Response createCustomer(Customer customer)
   {
      customer.setId(idCounter.incrementAndGet());
      customerDB.put(customer.getId(), customer);
      System.out.println("Created customer " + customer.getId());
      return Response.created(URI.create("/customersjaxb/" + customer.getId())).build();

   }

   @GET
   @Path("{id}")
   @Produces("application/example-java")
   public Customer getCustomer(@PathParam("id") int id)
   {
      Customer customer = customerDB.get(id);
      if (customer == null)
      {
         throw new WebApplicationException(Response.Status.NOT_FOUND);
      }
      return customer;
   }

   @PUT
   @Path("{id}")
   @Consumes("application/example-java")
   public void updateCustomer(@PathParam("id") int id, Customer update)
   {
      Customer current = customerDB.get(id);
      if (current == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

      current.setFirstName(update.getFirstName());
      current.setLastName(update.getLastName());
      current.setStreet(update.getStreet());
      current.setState(update.getState());
      current.setZip(update.getZip());
      current.setCountry(update.getCountry());
   }
}
