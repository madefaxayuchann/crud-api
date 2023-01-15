package id.kawahedukasi.controller;

import id.kawahedukasi.models.dto.UploadItemRequest;
import id.kawahedukasi.service.ExcelService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExcelController {

  @Inject
  ExcelService excelService;

  @POST
  @Path("/importFile")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response uploadDocument(@MultipartForm UploadItemRequest request) {
    try {
      excelService.upload(request);
      Map<String, Object> result = new HashMap<>();
      result.put("message", "SUCCESS");
      return Response.status(Response.Status.CREATED).entity(result).build();
    } catch (ValidationException | IOException e) {
      Map<String, Object> result = new HashMap<>();
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
  }
}
