package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;


import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 *程序功能：生成excel文件
 * 苏建栋
 * 2006-09-06
 * @version 1.0
 */

public class PublicCreateExcel
{
private static Logger logger = Logger.getLogger(PublicCreateExcel.class);

    private String state="";
    private SSRS tSSRS;
    private String Title="";
    private String Subject="";
    private String SheetName="";
    private int Row=0;
    private int Col=0;
    private String FileName="";
    private String WherePart="";
    TransferData mTransferData = new TransferData(); //传入页面信息

    //取数据
    public boolean SubmitData(VData InputData)
    {

        try{
            mTransferData = (TransferData) InputData.getObjectByObjectName(
                    "TransferData", 0);

            //Excel数据  double23.5表示写double型数据，其它都写成字符型
            //工作表标题
            this.Subject = (String) mTransferData.getValueByName("Subject");
            if (StrTool.isUnicodeString(this.Subject))
            {
                this.Subject=StrTool.unicodeToGBK(this.Subject);
            }

            //字段名(用@分开)
            this.Title = (String) mTransferData.getValueByName("Title");
            if (StrTool.isUnicodeString(this.Title))
            {
                this.Title=StrTool.unicodeToGBK(this.Title);
            }
            logger.debug("this.title:"+this.Title);

            //工作表名称
            this.SheetName = (String) mTransferData.getValueByName("SheetName");
            if (StrTool.isUnicodeString(this.SheetName))
            {
                this.SheetName=StrTool.unicodeToGBK(this.SheetName);
            }

            //Excel文件名
            this.FileName = (String) mTransferData.getValueByName("FileName");
            //查询条件
            this.WherePart = (String) mTransferData.getValueByName("WherePart");

            this.tSSRS=(SSRS) InputData.get(1);


        }catch(Exception e)
        {
            this.state="取数据出错，错误信息："+e.getMessage();
            return false;
        }
        if (tSSRS==null)
        {
            this.state="没有数据用来写Excel表格！";
            return false;
        }
        try{
            this.Row  =  tSSRS.getMaxRow();
            this.Col  =  tSSRS.getMaxCol();
        }catch(Exception e)
        {
            this.state="取tSSRS的记录数出错！";
            return false;
        }
        if (!PubCreateExcel())
        {
            this.state="写Excel文件出错！";
            return false;
        }
        return true;
    }//写excel
    public boolean PubCreateExcel()
    {
        HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
        /*********************样式定义******************************************/
        //样式1    黑体字  写标题用
        HSSFFont font1 = wb.createFont();
        font1.setColor(HSSFFont.COLOR_NORMAL);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font1.setFontName("黑体");
        font1.setFontHeightInPoints((short)18);
        HSSFCellStyle title= wb.createCellStyle();
        title.setFont(font1);
        title.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //样式2正文部分   无边框
        //样式2    宋体  写正文时用
        HSSFFont font2 = wb.createFont();
        font2.setColor(HSSFFont.COLOR_NORMAL);
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        HSSFCellStyle normal= wb.createCellStyle();
        normal.setFont(font2);
        normal.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        normal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

        //样式2    宋体左对齐  写正文时用
        HSSFFont fontleft = wb.createFont();
        fontleft.setColor(HSSFFont.COLOR_NORMAL);
        fontleft.setFontName("宋体");
        fontleft.setFontHeightInPoints((short)12);
        HSSFCellStyle normal_left= wb.createCellStyle();
        normal_left.setFont(fontleft);
        normal_left.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平左齐
        normal_left.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

        //样式2    宋体右对齐  写正文时用
        HSSFFont fontright = wb.createFont();
        fontright.setColor(HSSFFont.COLOR_NORMAL);
        fontright.setFontName("宋体");
        fontright.setFontHeightInPoints((short)12);
        HSSFCellStyle normal_right= wb.createCellStyle();
        normal_right.setFont(fontright);
        normal_right.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平左齐
        normal_right.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中





        //样式3   宋体   写正文用  有边框
        HSSFFont font3 = wb.createFont();
        font3.setColor(HSSFFont.COLOR_NORMAL);
        font3.setFontName("宋体");
        font3.setFontHeightInPoints((short)12);
        HSSFCellStyle border= wb.createCellStyle();
        border.setFont(font3);
        border.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
        border.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
        border.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        border.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        border.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        border.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框

        //建立一个新的工作表
        HSSFSheet sheet = wb.createSheet();
        wb.setSheetName( 0, this.SheetName );
        //打印设置
        HSSFPrintSetup myPrintSetup=sheet.getPrintSetup();
        myPrintSetup.setPaperSize((short) 9);   //设置成A4纸
        myPrintSetup.setLandscape(true);        //纸张横放

        //声明变量
        HSSFRow row;
        HSSFCell cell;
        HSSFCell csCell;
        sheet.setDefaultColumnWidth(14);

        //行
        int i=0;
        //列
        int j=0;

        //合并单元格写标题
        row = sheet.createRow((short)i);//建立新行
        cell = row.createCell(j);//建立新cell
        cell.setCellStyle(title);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
        cell.setCellValue(this.Subject);//设置中西文结合字符串
        //单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0,(short)0,1,(short)(this.Col-1)));
        i=3;
        j=0;
        //写查询条件
        row = sheet.createRow((short)i);//建立新行
        cell = row.createCell(j);//建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
        cell.setCellValue(this.WherePart);//设置中西文结合字符串
        //单元格合并
        sheet.addMergedRegion(new CellRangeAddress(3,(short)0,3,(short)(this.Col-1)));

        //写列名
        i=i+1;
        j=0;
        /*******************************************************************************************
         *
         * 循环开始60000行一个工作表
         *
         ******************************************************************************************/
        //int maintmpi=1;
           row = sheet.createRow((short) i); //建立新行
           try {
               for (int tmpi = 1; tmpi <= Col; tmpi++) {
                   cell = row.createCell( j); //建立新cell
                   cell.setCellStyle(normal);
                   //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                   cell.setCellValue(StrTool.getStr(this.Title, tmpi, "@")); //设置中西文结合字符串
                   cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
                   j++;
               }
           } catch (Exception e) {
               this.state = "写表头出错，请检查正确的列数应该是 " + Row + " 列";
               return false;
           }
           //开始写真正的正文数据
           i++;
           int k=1;
           for (int maintmpi=1; maintmpi <=Row; maintmpi++)
           {
               logger.debug("===maintmpi===" + maintmpi);
               row = sheet.createRow((short) i); //建立新行
               j = 0;
               for (int tmpj = 1; tmpj <= Col; tmpj++) {
                   String value = tSSRS.GetText(maintmpi, tmpj);
                   cell = row.createCell( j); //建立新cell
                   cell.setCellStyle(normal_left);
                   //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                   cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
                   if (value.indexOf("double") >= 0) {
                       value = value.substring(6, value.length());
                       try {
                           cell.setCellStyle(normal_right);
                           cell.setCellValue(Stringtools.stringtodouble(value)); //设置中西文结合字符串
                       } catch (Exception e) {
                           this.state = value + " 转成double出错！";
                           return false;
                       }
                   } else {
                       cell.setCellValue(value); //设置中西文结合字符串
                   }
                   j++;
               }
               i++;
               if (maintmpi>=65000*k)
               {
                   k++;
                       //建立一个新的工作表
                       sheet = wb.createSheet();
                       if (Row-maintmpi>0)
                       {
                           String str=Stringtools.inttostring((Row/maintmpi)-1);
                           wb.setSheetName((k-1), this.SheetName + str);
                         }
                         //打印设置
                         myPrintSetup=sheet.getPrintSetup();
                         myPrintSetup.setPaperSize((short) 9);   //设置成A4纸
                         myPrintSetup.setLandscape(true);        //纸张横放

                         i=0;
                         j=0;
                         row = sheet.createRow((short)i);//建立新行
                         try {
                             for (int tmpi = 1; tmpi <= Col; tmpi++)
                             {
                                 cell = row.createCell( j); //建立新cell
                                 cell.setCellStyle(normal);
                                 cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
                                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                                 cell.setCellValue(StrTool.getStr(this.Title, tmpi, "@")); //设置中西文结合字符串
                                 j++;
                             }
                             i=1;
                             j=0;
                         } catch (Exception e) {
                             this.state = "写表头出错，请检查正确的列数应该是 " + this.Col + " 列";
                             return false;
                         }

                     }//大于65000

           }//写数据那个循环

        try{
        FileOutputStream fileOut = new FileOutputStream(this.FileName);
        wb.write(fileOut);
        fileOut.close();
        logger.debug("====excel文件生成成功：========"+this.FileName);
     }catch(Exception e1)
     {
         logger.debug("===="+this.FileName+"=====Excel文件行数为："+this.Row+"文件无法继续写下去了！");
         this.state="写EXCEL文件出错，出错信息："+e1.getMessage();
         e1.printStackTrace();
         return false;
     }


        return true;
    }
    //返回错误信息
    public String getState()
    {
        return this.state;
    }
}
