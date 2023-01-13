package id.kawahedukasi.service;

import id.kawahedukasi.models.Item;
import id.kawahedukasi.models.dto.UploadItemRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class ExcelService {
  public Response upload(UploadItemRequest request) throws IOException {

    List<Item> pesertaList = new ArrayList<>();

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);
    XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
    XSSFSheet sheet = workbook.getSheetAt(0);

    //remove header
    sheet.removeRow(sheet.getRow(0));

    return Response.ok().build();
  }

}
