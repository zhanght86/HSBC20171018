package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;


import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.util.*;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.sinosoft.utility.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.awt.*;
import com.sinosoft.lis.pubfun.*;

/**
 *苏建栋
 * 生成导入模板（社保帐单）
 * 2006-09-04
 * @version 1.0
 */
public class CreateReceiptClassImportTemplate {
private static Logger logger = Logger.getLogger(CreateReceiptClassImportTemplate.class);
    public boolean CrtReceiptClassImportTemplate(String ManageCom,
                                                 String filename) {
        logger.debug("====filename=in=java="+filename);
        //Stringtools.log("","|"+filename,"");
        HSSFWorkbook wb = new HSSFWorkbook(); //建立新HSSFWorkbook对象
        //HSSFSheet sheet = wb.createSheet(StrTool.GBKToUnicode("出险人信息"));
        HSSFSheet sheet = wb.createSheet();
        wb.setSheetName( 0, "出险人信息" );
        sheet.setDefaultColumnWidth(15);

        //样式2    宋体  写正文时用
        HSSFFont font2 = wb.createFont();
        font2.setColor(HSSFFont.COLOR_NORMAL);
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        HSSFCellStyle normal = wb.createCellStyle();
        normal.setFont(font2);
        normal.setAlignment(HSSFCellStyle.ALIGN_LEFT); //水平居中
        normal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP); //垂直居中

        HSSFRow row;
        HSSFCell cell;
        HSSFCell csCell;

        //行
        int i = 0;
        //列
        int j = 0;
        j = 0;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("案件ID"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("团体客户号"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("团体保单号"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("险种代码"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("个人客户号"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险人姓名"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("性别(0-男1-女2-不详)"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件类型(0-身份证,9-无证件)"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件号码"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险类型(00-医疗01-伤残02-死亡04-大病06-失能)"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险原因"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险日期(输入格式：2006-01-01)"); //设置中西文结合字符串
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("赔案号(增量理赔时输入)"); //设置中西文结合字符串
//********************8写第二个工作表
         sheet = wb.createSheet();
         wb.setSheetName( 1, "帐单信息" );
         sheet.setDefaultColumnWidth( 15);

         //行
         i=0;
         //列
         j=0;
         row = sheet.createRow((short) i); //建立新行
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("案件ID"); //设置中西文结合字符串

         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("帐单种类(A-门诊B-住院)"); //设置中西文结合字符串

         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("医院代码"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("收据号"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("收据日期(输入格式：2006-01-01)"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("疾病代码"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("开始日期(输入格式：2006-01-01)"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("结束日期(输入格式：2006-01-01)"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("费用类型"); //设置中西文结合字符串
         j++;
         cell = row.createCell(j); //建立新cell
         cell.setCellStyle(normal);
         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
         cell.setCellValue("收据总费用"); //设置中西文结合字符串
//************************开始写动态部分***********************************************//
         ExeSQL myExeSQL=new ExeSQL();
         SSRS tSSRS=myExeSQL.execSQL("select codename from ldcode1 where codetype='colname' and code like '"+ManageCom+"%'");
         if (tSSRS==null)
         {
             return false;
         }
         if (tSSRS.getMaxRow()==0)
         {
             return false;
         }
         for (int tmpi=1;tmpi<=tSSRS.getMaxRow();tmpi++)
         {
             j++;
             cell = row.createCell(j); //建立新cell
             cell.setCellStyle(normal);
             //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
             cell.setCellValue(tSSRS.GetText(tmpi,1)); //设置中西文结合字符串
         }

         try{
                 FileOutputStream fileOut = new FileOutputStream(filename);
                 wb.write(fileOut);
                 fileOut.close();
                 logger.debug("====动态模板文件excel文件生成成功：========"+filename);
              }catch(Exception e1)
              {
                  return false;
              }
        return true;
    }
}
