package id.kawahedukasi.controller;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.logging.Level;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Map<String, Object> create(JsonObject request) {
//        String name = (String) request.get("name");
//        String gender = (String) request.get("gender");

    return Map.of(
            "data", request
    );
  }


  @GET
  @Path("/{id}")
  public Map<String, Object> get(@PathParam("id") Integer id) {
    return Map.of("id", id,
            "name", "niken",
            "gender", "level");
  }

  @GET
  @Path("/name/{name}")
  public Map<String, Object> get(@PathParam("name") String name) {
    return Map.of("id", -99,
            "name", name,
            "gender", "");
  }

  @GET
  public Map<String, Object> get(@QueryParam("id") Integer id, @QueryParam("name") String name, @QueryParam("gender") String gender) {
    return Map.of("id", id,
            "name", name,
            "gender", gender);
  }

  @PUT
  public Map<String, String> put() {
    return Map.of("message", "updated");
  }

  @DELETE
  public Map<String, String> delete() {
    return Map.of("message", "SUCCESS DELETE USERS");
  }

}
