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

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.openliberty.sample.system.SimpleHello;
import jakarta.inject.Inject;

@RunWith(Arquillian.class)
public class WebArchiveIT {

//    if testable = true, test fails on 
//    java.lang.RuntimeException: Could not inject members
//    Caused by: org.jboss.arquillian.test.spi.ArquillianProxyException: 
//    org.jboss.weld.exceptions.IllegalArgumentException : WELD-001408: Unsatisfied dependencies for type SimpleHello with qualifiers @Default
//    at injection point [BackedAnnotatedField] @Inject it.io.openliberty.sample.WebArchiveIT.simpleHello
   @Deployment(testable = false) 
   public static WebArchive createDeployment() {
      WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "testapp.war");
      webArchive.addClass(SimpleHello.class);
      return webArchive;
   }

   @ArquillianResource
   private URL baseURL;

   @Inject
   SimpleHello simpleHello;

   @Test
   public void simpleHelloTest() throws Exception {
      URL url = new URL(baseURL, "hello");
      assertEquals("/testapp/", baseURL.getPath());
      String response = readAllAndClose(url.openStream());
      assertEquals("Hello Jakarta EE 9!\n", response);
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