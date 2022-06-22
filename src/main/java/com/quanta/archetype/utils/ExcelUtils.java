package com.quanta.archetype.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.quanta.archetype.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Excel数据填充工具类
 */
@Slf4j
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ExcelUtils {
    @Autowired
    FileUtils fileUtils;

    /**
     * 生成Excel路径
     */
    public String makeExcel() {
        return fileUtils.getResourcePath() + "static/excel/" + fileUtils.getRandomFileName() + ".xlsx";
    }

    /**
     * 写入数据
     * 返回写入excel路径
     */
    public String buildExcel(List<?> data, Class<?> c) {
        String filePath = makeExcel();
        EasyExcel.write(filePath, c)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("数据")
                .doWrite(data);
        return filePath;
    }

    /**
     * 写入数据 可自定义表名
     * 返回写入excel路径
     */
    public String buildExcel(List<?> data, Class<?> c, String sheetName) {
        String filePath = makeExcel();
        EasyExcel.write(filePath, c)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(sheetName)
                .doWrite(data);
        return filePath;
    }


    /**
     * 以输出流的形式返回数据
     * 返回格式xlsx(application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)
     */
    public void returnExcel(List<?> data, Class<?> c,String fileName, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), c).excelType(ExcelTypeEnum.XLSX).sheet("数据").doWrite(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Excel生成失败");
        }
    }

    /**
     * 以输出流的形式返回数据(忽略某一列)
     * 返回格式xlsx(application/vnd.openxmlformats-officedocument.spreadsheetml.sheet)
     */
    public void returnExcel(List<?> data, Class<?> c,String fileName, HttpServletResponse response , Set<String> excludeColumn) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), c).excelType(ExcelTypeEnum.XLSX).sheet("数据").excludeColumnFiledNames(excludeColumn).doWrite(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("Excel生成失败");
        }
    }

}
