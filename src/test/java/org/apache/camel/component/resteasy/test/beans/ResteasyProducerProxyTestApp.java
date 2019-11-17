package org.apache.camel.component.resteasy.test.beans;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;

@Provider
public class ResteasyProducerProxyTestApp extends Application
{
   public Set<Class<?>> getClasses()
   {
      HashSet<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(CustomerService.class);
      classes.add(Customer.class);
      classes.add(CustomerList.class);
      //classes.add(ProxyProducerInterface.class);
      return classes;
   }
}
