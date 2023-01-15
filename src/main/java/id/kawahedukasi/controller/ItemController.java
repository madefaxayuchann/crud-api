package id.kawahedukasi.controller;

import id.kawahedukasi.service.ExcelService;
import id.kawahedukasi.service.ItemService;
import io.vertx.core.json.JsonObject;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

  @Inject
  ItemService itemService;

  @Inject
  ExcelService excelService;

  //  CREATE ITEM
  @POST
  public Response create(JsonObject request) {
    Map<String, Object> data = itemService.create(request);
    Map<String, Object> result = new HashMap<>();
    result.put("data", data);
    return Response.status(Response.Status.CREATED).entity(result).build();

  }

  //  DELETE Item
  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") String id) {
    try {

      Map<String, Object> data = ItemService.delete(id);
      Map<String, Object> result = new HashMap<>();
      result.put("data", data);
      return Response.ok().entity(data).build();
    } catch (ValidationException e) {
      Map<String, Object> result = new HashMap<>();
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
  }

  //  UPDATE PRODUCT
  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") String id, JsonObject request) {
    try {
      Map<String, Object> data = itemService.update(id, request);
      Map<String, Object> result = new HashMap<>();
      result.put("data", data);
      return Response.ok().entity(result).build();
    } catch (ValidationException e) {
      Map<String, Object> result = new HashMap<>();
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
  }


  //  GET PRODUCT BY ID
  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") String id) {
    try {
      Map<String, Object> result = ItemService.getById(id);
      return Response.ok().entity(result).build();
    } catch (ValidationException e) {
      Map<String, Object> result = new HashMap<>();
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();

    }
  }

  //  GET LIST ALL PRODUCT
  @GET
  public Response list() {
    List<Map<String, Object>> items = itemService.getAll();
    Map<String, Object> result = new HashMap<>();
    result.put("data", items);
    return Response.ok().entity(result).build();
  }

}

