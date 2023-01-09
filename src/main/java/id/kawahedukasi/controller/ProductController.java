package id.kawahedukasi.controller;

import id.kawahedukasi.models.Product;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

  // CREATE DATA
  @POST
  @Transactional
  public Response create(JsonObject request) {
    Product product = new Product();

    product.  name = request.getString("name");
    product.count = request.getInt("count");
    product.price = request.getInt("price");
    product.description = request.getString("description");
    product.type = request.getString("type");

    product.persist();
    return Response.ok().entity(product).build();
  }

  //  DELETE PRODUCT
  @DELETE
  @Path("/{id}")
  @Transactional
  public Response delete(@PathParam("id") Integer id) {
    Product.deleteById(id);
    return Response.ok().entity(new HashMap<>()).build();
  }

  //  UPDATE PRODUCT
  @PUT
  @Path("/{id}")
  @Transactional
  public Response update(@PathParam("id") Integer id, JsonObject request) {
    Product product = Product.findById(id); //select * from peserta where id = :1
    product.name = request.getString("name");
    product.count = request.getInt("count");
    product.type = request.getString("type");
    product.description = request.getString("description");

    //save
    product.persist();

    return Response.ok().entity(product).build();
  }

  //  GET PRODUCT BY ID
  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Integer id) {
    Product product = Product.findById(id);
    if (product == null) {
      return Response.status(Response.Status.BAD_REQUEST)
              .entity(Map.of("message", "PRODUCT NOT FOUND"))
              .build();
    }

    return Response.ok().entity(product).build();
  }

  //  GET LIST ALL PRODUCT
  @GET
  public Response list() {
    return Response.ok().entity(Product.listAll()).build();
  }

}

