package shop.services;

import com.restfully.shop.services.CustomerResource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class ShoppingApplication extends Application
{
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> classes = new HashSet<Class<?>>();

   public ShoppingApplication()
   {
      singletons.add(new CustomerResource());
      singletons.add(new CustomerResourceJaxB());
      classes.add(JavaMarshaller.class);
   }

   @Override
   public Set<Class<?>> getClasses()
   {
      return classes;
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }
}
