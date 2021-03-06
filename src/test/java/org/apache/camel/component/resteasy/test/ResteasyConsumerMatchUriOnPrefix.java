/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.resteasy.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;

import javax.ws.rs.core.Response;

import org.apache.camel.component.resteasy.test.beans.SimpleService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class ResteasyConsumerMatchUriOnPrefix {
	@Deployment
	public static Archive<?> createTestArchive() {

		 WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
		 .addClasses(SimpleService.class)
		 .addPackage("org.apache.camel.component.resteasy")
		 .addPackage("org.apache.camel.component.resteasy.servlet")
		 .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
		 .importRuntimeAndTestDependencies().resolve().withTransitivity().asFile())
		 .addAsWebInfResource(new File("src/test/resources/contexts/consumerMatch.xml"), "applicationContext.xml")
		 .addAsWebInfResource("web.xml");
		
		 return war;

//		File pomFile = Maven.resolver().loadPomFromFile("pom.xml")
//				.resolve("org.jboss.resteasy:resteasy-servlet-initializer").withoutTransitivity().asSingleFile();
//
//		WebArchive war = ShrinkWrap.create(WebArchive.class, "RESTEASY-1630-2-two.war").addClasses(SimpleService.class)
//				.addPackage("org.apache.camel.component.resteasy")
//				.addPackage("org.apache.camel.component.resteasy.servlet").addAsLibraries(pomFile)
//				.addAsWebInfResource("web.xml")
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-core:5.2.1.RELEASE")
//						.withTransitivity().asFile())
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-web:5.2.1.RELEASE")
//						.withTransitivity().asFile())
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-webmvc:5.2.1.RELEASE")
//						.withTransitivity().asFile())
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-context:5.2.1.RELEASE")
//						.withTransitivity().asFile())
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-expression:5.2.1.RELEASE")
//						.withTransitivity().asFile())
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-beans:5.2.1.RELEASE")
//						.withTransitivity().asFile())
//				.addAsLibraries(Maven.resolver().resolve("org.springframework:spring-aop:5.2.1.RELEASE")
//						.withTransitivity().asFile());
//		return war;
	}

	@ArquillianResource
	URI baseUri;

	/**
	 * App declares files via the web.xml
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEndpoint() throws Exception {
		Response response = ResteasyClientBuilder.newClient().target(baseUri.toString() + "simpleService/match/prefix")
				.request().get();
		String entity = response.readEntity(String.class);
		assertEquals(200, response.getStatus());
		Assert.assertEquals("Body set from camel route", entity);
	}
}