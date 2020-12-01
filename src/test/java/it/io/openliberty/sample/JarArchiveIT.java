package it.io.openliberty.sample;

import java.net.URL;

// import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
// import org.jboss.arquillian.container.test.api.RunAsClient;
// import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
// import org.junit.Assert;
// import org.junit.Test;
// import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import io.openliberty.sample.system.SimpleHello;
import jakarta.inject.Inject;

// import static org.junit.Assert.assertNotNull;

@Test
public class JarArchiveIT extends Arquillian {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addClass(SimpleHello.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml"); 
                // need addAsManifestResource, else java.lang.RuntimeException: Could not inject members
        return jar;
    }

    @Inject
    SimpleHello simpleHello;

    @Test
    public void should_create_greeting() {
        Assert.assertNotNull(simpleHello, "Simple Hello should be injectable.");
    }

}