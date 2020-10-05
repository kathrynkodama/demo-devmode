package it.io.openliberty.sample;

import java.net.URL;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.openliberty.sample.system.SimpleHello;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@RunWith(Arquillian.class)
public class HelloIT {

    private final static String WARNAME = "arquillian-managed.war";

    @Deployment(testable = true)
    public static WebArchive createEndpointTestDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, WARNAME).addPackages(true,
                "io.openliberty.sample.system");
        return archive;
    }

    @ArquillianResource
    private URL baseURL;

    @Inject
    SimpleHello simpleHello;

    @Test
    @RunAsClient
    public void testHelloEndpoint() {
        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(baseURL + "/hello");
        Response response = target.request().get();

        System.out.println("STATUS: " + response.getStatus());
        Assert.assertEquals("Incorrect response code from " + baseURL, 200, response.getStatus());
    }

}
