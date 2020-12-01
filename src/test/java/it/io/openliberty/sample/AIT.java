package it.io.openliberty.sample;

import jakarta.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
// import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
// import org.junit.Assert;
// import org.junit.Test;
// import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import io.openliberty.sample.system.Greeter;

@Test
public class AIT extends Arquillian
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