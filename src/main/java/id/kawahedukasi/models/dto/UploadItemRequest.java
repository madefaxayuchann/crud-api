package id.kawahedukasi.models.dto;

import javax.ws.rs.FormParam;

public class UploadItemRequest {
  @FormParam("file")
  public byte[] file;
}
