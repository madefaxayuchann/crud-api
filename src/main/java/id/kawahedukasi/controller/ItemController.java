package id.kawahedukasi.controller;

import id.kawahedukasi.models.Item;
import id.kawahedukasi.models.dto.UploadItemRequest;
import id.kawahedukasi.service.ExcelService;
import id.kawahedukasi.service.ItemService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

  @Inject
  ItemService itemService;

  @Inject
  ExcelService excelService;

  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response upload(@MultipartForm UploadItemRequest
                                 request) throws IOException {
    System.out.println(request);
    return excelService.upload(request);
  }

  @POST
  @Transactional
  public Response create(JsonObject request) {
    Item item = new Item();
    item.name = request.getString("name");
    item.count = request.getInt("count");
    item.price = request.getInt("price");
    item.description = request.getString("description");
    item.type = request.getString("type");

    if (item.name == null || item.price == null || item.type == null || item.count == null) {
      return Response
              .status(Response.Status.BAD_REQUEST)
              .entity(Map.of("message", "BAD_REQUEST"))
              .build();
    }

    item.persist();
    return Response.ok().entity(item).build();
  }

  //  DELETE Item
  @DELETE
  @Path("/{id}")
  @Transactional
  public Response delete(@PathParam("id") Integer id) {
    Item.deleteById(id);
    return Response.ok().entity(new HashMap<>()).build();
  }

  //  UPDATE PRODUCT
  @PUT
  @Path("/{id}")
  @Transactional
  public Response update(@PathParam("id") Integer id, JsonObject request) {
    Item product = Item.findById(id); //select * from peserta where id = :1
    product.name = request.getString("name");
    product.count = request.getInt("count");
    product.type = request.getString("type");
    product.price = request.getInt("price");
    product.description = request.getString("description");


    //save
    product.persist();

    return Response.ok().entity(product).build();
  }

  //  GET PRODUCT BY ID
  @GET
  @Path("/{id}")
  public Response getById(@PathParam("id") Integer id) {
    Item product = Item.findById(id);
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
    return Response.ok().entity(Item.listAll()).build();
  }

}

