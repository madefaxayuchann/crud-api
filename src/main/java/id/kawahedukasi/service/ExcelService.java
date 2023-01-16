package id.kawahedukasi.service;

import id.kawahedukasi.models.Item;
import id.kawahedukasi.models.dto.UploadItemRequest;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

  public Response generateReport() throws JRException {
    File file = new File("src/main/resources/Items.jrxml");

    JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Item.listAll());

    //build object jasper report dari load template
    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

    Map<String, Object> param = new HashMap<>();
    param.put("DATASOURCE", jrBeanCollectionDataSource);
    //bikin object jasperPrint
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());

    //export jasperPrint dalam bentuk byte array
    byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

    //return response dengan content type pdf
    return Response.ok().type("application/pdf").entity(jasperResult).build();

  }

}
