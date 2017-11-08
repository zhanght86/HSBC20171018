package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CreateExcel {
private static Logger logger = Logger.getLogger(CreateExcel.class);
/*测试用主方法
    public static void main(String [] args)
    {
        String filename="test.xls";
        String [] data={"1中国","2中国","double3","4中国","5中国","double6","中国7","中国8","double9","中国10","中国11","double12"};
        try{
        logger.debug("程序执行结果："+Crtexcel(filename,data,3));
    }catch(java.io.IOException e1)
    {

    }

    }
*/
    public CreateExcel()
    {
    }

                                                 //文件名,数据数组,列数
    public static String Crtexcel(String filename,String [] data,int num)
     throws IOException
    {
     String state="1";
     Stringtools.log("","开始写excel文件："+filename,"");
     //写文件的路径
    HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
   //建立一个新的工作表
   HSSFSheet sheet = wb.createSheet("sheet1");
   HSSFRow row;
   HSSFCell cell;
   HSSFCell csCell;
   int i=0;
   int k=0;
   while (k<data.length)
   {
          int j=0;
       while (j<num)
       {
           row = sheet.createRow((short)i);//建立新行
           cell = row.createCell(j);//建立新cell
           //logger.debug("data["+k+"]"+"=="+data[k]);
           Stringtools.log("","data["+k+"]"+"=="+data[k],"");
           if (data[k]==null)
           {
               data[k]="";
           }
           if (data[k].indexOf("null")>=0)
           {
               data[k]="";
           }
           if (data[k].indexOf("double")>=0)
           {
               String tmpstr1=data[k].substring(6,data[k].length());
               row.createCell(j).setCellValue(Stringtools.stringtodouble(tmpstr1));
           }
           else
           {
               csCell =row.createCell(j);
//               csCell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
               csCell.setCellValue(data[k]);//设置中西文结合字符串
           }


           j++;
           k++;
       }
       i++;
       if (i>=65535)
       {
           logger.debug("===="+filename+"=====Excel文件行数为："+i+"文件无法继续写下去了！");
           state="0Excel文件超过其允许的最大行数(65535)";
           Stringtools.log("","fileanme:"+state,"");
           break;

       }
   }
   try{
       FileOutputStream fileOut = new FileOutputStream(filename);
       wb.write(fileOut);
       fileOut.close();
       Stringtools.log("","excel文件生成成功："+filename,"");

   }catch(Exception e1)
   {
       Stringtools.log("","===="+filename+"=====Excel文件行数为："+i+"文件无法继续写下去了！","");
       state="0Excel文件超过其允许的最大行数(65535)";
   }

     return state;
    }
}
