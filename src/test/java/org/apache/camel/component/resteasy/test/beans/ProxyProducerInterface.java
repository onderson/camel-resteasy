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
package org.apache.camel.component.resteasy.test.beans;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/customer")
public interface ProxyProducerInterface {


//    @HEAD
    @GET
    @Produces("application/json")
    @Consumes()
    @Path("/getAll")
    public Response getAllCustomers();

    @GET
    @Produces("application/json")
    @Path("/getCustomer")
    public Response getCustomer(@QueryParam("id") Integer id);

    @GET
    @Produces("application/json")
    @Path("/getSpecificThreeCustomers")
    public Response getSpecificThreeCustomers(@QueryParam("c1") Integer customerId1, @QueryParam("c2") Integer customerId2, @QueryParam("c3") Integer customerId3);

    @DELETE
    @Path("/deleteCustomer")
    public Response deleteCustomer(@QueryParam("id") Integer id);

    @POST
    @Consumes("application/json")
    @Path("/createCustomer")
    public Response createCustomer(Customer customer) ;

    @PUT
    @Consumes("application/json")
    @Path("/updateCustomer")
    public Response updateCustomer(Customer customer);

    @GET
    @Produces("application/json")
    @Path("/getCustomerWithoutResponse")
    public Customer getCustomerWithoutResponse(@QueryParam("c1") Integer customerId1);

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/checkCustomer")
    public Response checkIfCustomerExists(@QueryParam("c1") Integer customerId1, Customer customer);

}
