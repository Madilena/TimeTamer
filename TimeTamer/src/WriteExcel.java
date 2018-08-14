import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	static TimeOps time = new TimeOps();
	final static String userDir = System.getProperty("user.dir");
	final static String userHomeDir = System.getProperty("user.home");
	static File dir = new File(userHomeDir + "/TomatoNursery");

	final static String FILE_NAME = dir + "/TomatoTimeTamer_" + time.todaysDate() + ".xlsx";
	static int rowNum = 1;
	static XSSFWorkbook workbook = new XSSFWorkbook();
	static XSSFSheet sheet = workbook.createSheet("Tomato Time Tamer");

	public static void createExcelSheet() {
		createFolder();
		List<List<? extends Object>> tomatoValues = TomatoOps.tomatoes.stream()
				.map(x -> Arrays.asList(x.getTomatoNumber(), x.getGoal(), x.getWorkTime(), x.getBreakTime(),
						x.getTomatoStartTime(), x.getWorkEndTime(), x.getBreakEndTime()))
				.collect(Collectors.toList());

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Tomato Number");
		header.createCell(1).setCellValue("Tomato Goal");
		header.createCell(2).setCellValue("Work Time (min)");
		header.createCell(3).setCellValue("Break Time (min)");
		header.createCell(4).setCellValue("Tomato Start Time");
		header.createCell(5).setCellValue("Work End Time");
		header.createCell(6).setCellValue("Break End Time");
		{
			for (List<? extends Object> t : tomatoValues) {
				Row row = sheet.createRow(rowNum++);
				int colNum = 0;
				for (Object field : t) {
					Cell cell = row.createCell(colNum++);
					if (field instanceof String) {
						cell.setCellValue((String) field);
					} else if (field instanceof Integer) {
						cell.setCellValue((Integer) field);
					} else if (field instanceof LocalTime) {
						String timeAsString = field.toString();
						cell.setCellValue((String) timeAsString);
					}
				}
			}

			try {
				FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
				workbook.write(outputStream);
				workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Your Goal Sheet has been created in directory: " + FILE_NAME);
		}
	}

	public static void createFolder() {
		if (!dir.exists()) {
			try {
				if (dir.mkdir()) {
					System.out.println("Directory Created");
				} else {
					System.out.println("Directory is not created");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {System.out.println("Directory exists");}
	}
}
