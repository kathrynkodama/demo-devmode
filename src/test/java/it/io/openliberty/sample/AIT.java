package it.io.openliberty.sample;

import jakarta.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.openliberty.sample.system.Greeter;

@RunWith(Arquillian.class)
public class AIT
{
   @Deployment
   public static JavaArchive createDeployment() {
       JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
           .addClass(Greeter.class)
           .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
       return jar;
   }
   
   @Inject
   Greeter greeter;

   @Test
   public void should_create_greeting() {
       Assert.assertEquals("Hello, Earthling!",
           greeter.createGreeting("Earthling"));
   }
}