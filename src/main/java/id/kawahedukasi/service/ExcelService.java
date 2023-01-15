package id.kawahedukasi.service;

import id.kawahedukasi.models.Item;
import id.kawahedukasi.models.dto.UploadItemRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class ExcelService {

  @Inject
  ItemService itemService;

  public void upload(UploadItemRequest request) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);

    //   create workbook
    XSSFWorkbook xssfWorkbook = new XSSFWorkbook(byteArrayInputStream);

    //    take sheet index 0
    XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

    // remove header at index 0
    sheet.removeRow(sheet.getRow(0));

    for (Row row : sheet) {
      itemService.persistItem(
              row.getCell(0).getStringCellValue(),
              row.getCell(1).getStringCellValue(),
              row.getCell(2).getStringCellValue(),
              Double.valueOf(row.getCell(3).getNumericCellValue()).longValue(),
              row.getCell(4).getNumericCellValue()
      );
    }
  }

}
