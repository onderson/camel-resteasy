package org.apache.camel.component.resteasy.test;

import org.apache.camel.component.resteasy.test.beans.SimpleService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.io.File;

/**
* @author : Roman Jakubco | rjakubco@redhat.com on 09/03/15.
*/
@RunWith(Arquillian.class)
public class ResteasyRestDSLTest {
    private final static String URI = "http://localhost:8080/test/";
    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsResource(new File("src/test/resources/contexts/restDSL.xml"), "applicationContext.xml")
                .addAsWebInfResource(new File("src/test/resources/web.xml"))
                .addClasses(SimpleService.class)
                .addPackage("org.apache.camel.component.resteasy")
                .addPackage("org.apache.camel.component.resteasy.servlet")
                .addAsLibraries(Maven.resolver().loadPomFromFile("src/test/resources/pom.xml").importRuntimeAndTestDependencies().resolve()
                        .withTransitivity().asFile())
                .addAsLibraries(Maven.resolver().resolve("org.apache.camel:camel-http:2.14.0").withTransitivity().asFile());
    }




    @Test
    public void testRestDSL() throws Exception {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(URI + "simpleService/getMsg");
        Response response = target.request().get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("Text from camel. Response from REST : Message1 from Rest service", response.readEntity(String.class));

    }
}
