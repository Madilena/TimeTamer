import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	
//	public WriteExcel() {}
//	XSSFWorkbook workbook = new XSSFWorkbook();
//    XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
//    Object[][] datatypes = {
//            {"Datatype", "Type", "Size(in bytes)"},
//            {"int", "Primitive", 2},
//            {"float", "Primitive", 4},
//            {"double", "Primitive", 8},
//            {"char", "Primitive", 1},
//            {"String", "Non-Primitive", "No fixed size"}
//    };
//
//    int rowNum = 0;
//    System.out.println("Creating excel");
//
//    for (Object[] datatype : datatypes) {
//        Row row = sheet.createRow(rowNum++);
//        int colNum = 0;
//        for (Object field : datatype) {
//            Cell cell = row.createCell(colNum++);
//            if (field instanceof String) {
//                cell.setCellValue((String) field);
//            } else if (field instanceof Integer) {
//                cell.setCellValue((Integer) field);
//            }
//        }
//    }
//
//    try {
//        FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
//        workbook.write(outputStream);
//        workbook.close();
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//    System.out.println("Done");
}

