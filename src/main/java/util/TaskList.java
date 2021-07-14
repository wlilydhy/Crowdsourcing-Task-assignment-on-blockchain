package util;

import bean.Task;
import bean.Worker;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TaskList {
    public ArrayList<Task> getTasks(double startTime, double duration) throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        //1、获取文件输入流
        FileInputStream fis = new FileInputStream("/Users/fhn/OneDrive - hdu.edu.cn/科研/众包论文/数据/taskdata2.0.xlsx");
        //2、获取Excel工作簿对象
        Workbook workbook = new XSSFWorkbook(fis);
        //3、得到Excel工作表对象
        Sheet sheetAt = workbook.getSheetAt(0);
        //4、循环读取表格数据
        for (Row row : sheetAt) {
            //首行（即表头）不读取
            if (row.getRowNum() == 0) {
                continue;
            }

            double time = row.getCell(4).getNumericCellValue();
            if (time < startTime) {
                continue;
            }
            if (time > startTime+duration) {
                break;
            }

            //读取当前行中单元格数据，索引从0开始
            double id = row.getCell(0).getNumericCellValue();
            double longitude = row.getCell(2).getNumericCellValue();
            double latitude = row.getCell(3).getNumericCellValue();

            double skill1 =  row.getCell(7).getNumericCellValue();
            double skill2 = row.getCell(8).getNumericCellValue();
            double skill3 = row.getCell(9).getNumericCellValue();
            HashSet<Double> skills = new HashSet<>();
            skills.add(skill1);
            skills.add(skill2);
            skills.add(skill3);
            //先不设置budget
            //double budget = row.getCell(10).getNumericCellValue();

            Task task = new Task();
            task.setId(id);
            task.setLongitude(longitude);
            task.setLatitude(latitude);
            task.setTime(time);
            task.setSkills(skills);
            task.setSkillNumber(skills.size());
            System.out.println(task.toString());
            tasks.add(task);
        }

        return tasks;
    }
}
