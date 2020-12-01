/*
 * Copyright 2018, IBM Corporation, and other contributors
 * identified by the Git commit log. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.io.openliberty.sample;

// import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
// import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;

import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
// import org.junit.Test;
// import org.junit.runner.RunWith;
import org.testng.annotations.Test;

import io.openliberty.sample.system.SimpleHello;
import jakarta.inject.Inject;

@Test
public class WebArchiveIT extends Arquillian {

//    if testable = true, test fails on 
//    java.lang.RuntimeException: Could not inject members
//    Caused by: org.jboss.arquillian.test.spi.ArquillianProxyException: 
//    org.jboss.weld.exceptions.IllegalArgumentException : WELD-001408: Unsatisfied dependencies for type SimpleHello with qualifiers @Default
//    at injection point [BackedAnnotatedField] @Inject it.io.openliberty.sample.WebArchiveIT.simpleHello
   @Deployment(testable = false) 
   public static EnterpriseArchive createDeployment() {
      return ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
            .addAsModule(ShrinkWrap.create(WebArchive.class, "test1.war")
                              .addClass(SimpleHello.class))
            .addAsModule(ShrinkWrap.create(JavaArchive.class, "test.jar")
                              .addClass(WebArchiveIT.class)
                              .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml"));
   }

   @ArquillianResource
   private URL baseURL;

   @Inject
   SimpleHello simpleHello;

   @Test
   @RunAsClient
   public void simpleHelloTest() throws Exception {
      URL url = new URL(baseURL, "hello");
      Assert.assertEquals("/test1/", baseURL.getPath());
      String response = readAllAndClose(url.openStream());
      Assert.assertEquals("Hello Jakarta EE 9!\n", response);
   }

   private String readAllAndClose(InputStream is) throws Exception {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try {
         int read;
         while ((read = is.read()) != -1) {
            out.write(read);
         }
      } finally {
         try {
            is.close();
         } catch (Exception e) {
         }
      }
      return out.toString();
   }
}