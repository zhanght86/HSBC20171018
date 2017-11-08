package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;

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
 *社保帐单给付通知Excel版
 *
 *  */
public class LLCaseReceiptClassExcel {
private static Logger logger = Logger.getLogger(LLCaseReceiptClassExcel.class);
    public String CreateReceiptExcel(String filename,String RgtNo)
    {
        String state="";
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
        font2.setFontHeightInPoints((short)9);
        HSSFCellStyle normal= wb.createCellStyle();
        normal.setFont(font2);
        normal.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        normal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

//样式2    宋体左对齐  写正文时用
        HSSFFont fontleft = wb.createFont();
        fontleft.setColor(HSSFFont.COLOR_NORMAL);
        fontleft.setFontName("宋体");
        fontleft.setFontHeightInPoints((short)9);
        HSSFCellStyle normal_left= wb.createCellStyle();
        normal_left.setFont(fontleft);
        normal_left.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平左齐
        normal_left.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

//样式2    宋体右对齐  写正文时用
        HSSFFont fontright = wb.createFont();
        fontright.setColor(HSSFFont.COLOR_NORMAL);
        fontright.setFontName("宋体");
        fontright.setFontHeightInPoints((short)9);
        HSSFCellStyle normal_right= wb.createCellStyle();
        normal_right.setFont(fontright);
        normal_right.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平左齐
        normal_right.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

//样式3   宋体   写正文用  有边框
        HSSFFont font3 = wb.createFont();
        font3.setColor(HSSFFont.COLOR_NORMAL);
        font3.setFontName("宋体");
        font3.setFontHeightInPoints((short)9);
        HSSFCellStyle border= wb.createCellStyle();
        border.setFont(font3);
        border.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
        border.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
        border.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        border.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        border.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        border.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框

        //样式3_1   宋体  有边框
       HSSFFont font3_1 = wb.createFont();
       font3_1.setColor(HSSFFont.COLOR_NORMAL);
//      font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       font3_1.setFontHeightInPoints((short)9);
       font3_1.setFontName("宋体");
       HSSFCellStyle borderright= wb.createCellStyle();
       borderright.setFont(font3_1);
       HSSFDataFormat format = wb.createDataFormat();
       borderright.setDataFormat(format.getFormat("0.00"));
       borderright.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
       borderright.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
       borderright.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
       borderright.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
       borderright.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框


//建立一个新的工作表
        HSSFSheet sheet = wb.createSheet();
        wb.setSheetName( 0, " 理 赔 给 付 通 知 " );
        sheet.setMargin((short) 1, 0.5);
        sheet.setMargin((short) 2, 0.5);
        sheet.setMargin((short) 3, 0.5);
        sheet.setMargin((short) 0, 0.5);
        sheet.setHorizontallyCenter(true);
        //sheet.setVerticallyCenter(true);

//打印设置
HSSFPrintSetup myPrintSetup=sheet.getPrintSetup();
myPrintSetup.setPaperSize((short) 9);   //设置成A4纸
myPrintSetup.setLandscape(false);        //纸张横放
//int aa=myPrintSetup.getFitWidth();
//logger.debug("========aa===:"+aa);
//myPrintSetup.setFitWidth((short)(aa+80));
//声明变量
HSSFRow row;
HSSFCell cell;
HSSFCell csCell;
sheet.setDefaultColumnWidth(8);

        //行
        int i=0;
        //列
        int j=0;

/*        //合并单元格写标题
        row = sheet.createRow((short)i);//建立新行
        cell = row.createCell(j);//建立新cell
        cell.setCellStyle(title);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
        cell.setCellValue(" 理 赔 给 付 通 知 ");//设置中西文结合字符串

      //单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0,(short)0,1,(short)9));
*/
        String sql="select Customerno,sum(realpay) from llclaimdetail ";
        sql=sql + " where clmno='"+RgtNo+"'";
        sql=sql + " group by Customerno";
        ExeSQL myExeSQL=new ExeSQL();
        SSRS tSSRS=myExeSQL.execSQL(sql);
        int Row=0;
        try{
            Row=tSSRS.getMaxRow();
        }catch(Exception e)
        {
            state="没有取到下载赔明细数据！";
            return state;
        }
        //保单号
        String GrpContNo=myExeSQL.getOneValue("select GrpContNo from llregister where rgtno='"+RgtNo+"'");
        if (GrpContNo.trim().length()==0)
        {
            state="取保单号失败，赔案号："+RgtNo;
            return state;
        }
        for (int k=1;k<=Row;k++)
        {
                if (k>1)
                {
                   sheet.setRowBreak(i+2);
                   i=i+3;
                }

                i++;
                //合并单元格写标题
                j=0;
                row = sheet.createRow((short)i);//建立新行
                cell = row.createCell(j);//建立新cell
                cell.setCellStyle(title);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
                cell.setCellValue(" 理 赔 给 付 通 知 ");//设置中西文结合字符串
                sheet.addMergedRegion(new CellRangeAddress(i,0,(i+1),9));

            //被保人姓名
            String Name=myExeSQL.getOneValue("select Name from lcinsured where InsuredNo='"+tSSRS.GetText(k,1)+"'");
            i++;
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue("尊敬的 " + Name+ " 先生/女士"); //设置中西文结合字符串
            sheet.addMergedRegion(new CellRangeAddress(i,0,i,4));

            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue("    您的理赔申请已经审核通过，赔付详情如下所示，如您有任何疑问，请致电本公司客户服务热线，在此祝您身体健康！"); //设置中西文结合字符串
            sheet.addMergedRegion(new CellRangeAddress(i,(short)0,(i+2),(short)9));
            i++;
            i++;
            j=0;
            String GrpName="投保单位："+myExeSQL.getOneValue("select GrpName from lcgrpcont where GrpContNo='"+GrpContNo+"'");
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(GrpName);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)0,i,(short)4));
            j=5;
            String InsuredNo="客户号："+tSSRS.GetText(k,1);
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(InsuredNo);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)5,i,(short)9));
            i++;
            j=0;
            String ID="身份证号："+myExeSQL.getOneValue("select IDNO from lcinsured where InsuredNo='"+tSSRS.GetText(k,1)+"'");
            row = sheet.createRow((short) i); //建立新行
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(ID);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)0,i,(short)4));
            String ClmNo="赔案号："+RgtNo;
            j=5;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(ClmNo);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)5,i,(short)9));

            //转帐银行及帐号
            i++;
            j=0;
            String bankcode="转帐银行："+myExeSQL.getOneValue("select bankcode from llaccount where grpcontno='"+GrpContNo+"' and InsuredNo='"+tSSRS.GetText(k,1)+"'");
            String account="帐号："+myExeSQL.getOneValue("select account from llaccount where grpcontno='"+GrpContNo+"' and InsuredNo='"+tSSRS.GetText(k,1)+"'");
            row = sheet.createRow((short) i); //建立新行
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(bankcode);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)0,i,(short)4));

            j=5;
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(account);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)5,i,(short)9));


            //赔付日期及收款人
            i++;
            j=0;
            String endcasedate="赔付日期："+myExeSQL.getOneValue("select to_char(endcasedate,'yyyy-mm-dd') from llregister where rgtno='"+RgtNo+"'");
            String accountname="收款人："+myExeSQL.getOneValue("select InsuredName from llaccount where grpcontno='"+GrpContNo+"' and Insuredno='"+tSSRS.GetText(k,1)+"'");
            row = sheet.createRow((short) i); //建立新行
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(endcasedate);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)0,i,(short)4));
            j=5;
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(accountname);
            sheet.addMergedRegion(new CellRangeAddress(i,(short)5,i,(short)9));

            i++;
            i++;
            j=0;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell( j); //建立新cell
            cell.setCellStyle(normal_left);
            normal_left.setWrapText(true);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue("赔付结果：");
            sheet.addMergedRegion(new CellRangeAddress(i,(short)0,i,(short)9));

            //总费用
            String totalfee=myExeSQL.getOneValue("select sum(fee) from llcasereceipt where clmno='"+RgtNo+"' and Customerno='"+tSSRS.GetText(k,1)+"'");
            if (totalfee.trim().length()==0)
            {
                totalfee = "0";
            }
            //收据合计张数
            String billcount=myExeSQL.getOneValue("select count(unique mainfeeno) from llcasereceipt where clmno='"+RgtNo+"' and Customerno='"+tSSRS.GetText(k,1)+"'");
            if (billcount.trim().length()==0)
            {
                        billcount="0.00";
            }

            //总合理费
            String totaladjfee=myExeSQL.getOneValue("select sum(adjsum) from llcasereceipt where clmno='"+RgtNo+"' and Customerno='"+tSSRS.GetText(k,1)+"'");
            if (totaladjfee.trim().length()==0)
            {
                        totaladjfee="0.00";
            }

            //门诊费用合理费用
            String fee_a=myExeSQL.getOneValue("select sum(adjsum) from llcasereceipt where clmno='"+RgtNo+"' and Customerno='"+tSSRS.GetText(k,1)+"' and feeitemtype='A'");
            if (fee_a.trim().length()==0)
            {
                        fee_a="0.00";
            }

            //住院费用合理费用
            String fee_b=myExeSQL.getOneValue("select sum(adjsum) from llcasereceipt where clmno='"+RgtNo+"' and Customerno='"+tSSRS.GetText(k,1)+"' and feeitemtype='B'");
            if (fee_b.trim().length()==0)
            {
                        fee_b="0.00";
            }

            //女性生育
            String fee_woman=myExeSQL.getOneValue("select sum(adjsum) from llcasereceipt where clmno='"+RgtNo+"' and Customerno='"+tSSRS.GetText(k,1)+"' and feeitemcode='41'");
            if (fee_woman.trim().length()==0)
            {
                        fee_woman="0.00";
            }
            //本次赔付
            String realpay=tSSRS.GetText(k,2);
            //累计赔付
            String total_realpay=myExeSQL.getOneValue("select sum(realpay) from llclaimpolicy where grpcontno='"+GrpContNo+"' and insuredno='"+tSSRS.GetText(k,1)+"'");

             String str="    您此次所提交的理赔申请材料中，医疗费用合计:"+totalfee+"。收据合计："+billcount+"张。依据保险合同的约定，经过审慎核定，合理费用为："+totaladjfee+"元。其中，门诊费用："+fee_a+"元。住院费用："+fee_b+"元。（女性生育费用："+fee_woman+"元。）本次赔付："+realpay+"元。您在本保险年度累计赔付达："+total_realpay+"元。";
             i++;
             j=0;
             row = sheet.createRow((short) i); //建立新行
             cell = row.createCell( j); //建立新cell
             cell.setCellStyle(normal_left);
             normal_left.setWrapText(true);
             //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
             cell.setCellValue(str);
             sheet.addMergedRegion(new CellRangeAddress(i,(short)0,(i+4),(short)9));
             int count=Stringtools.stringtoint(myExeSQL.getOneValue("select count(*) from llcasereceiptclass where clmno='"+RgtNo+"'"));
             if (count>=1)//如果有社保帐单信息才进行下面的程序
             {
                 //写表头


                 i=i+5;
                 j = 0;
                 row = sheet.createRow((short) i); //建立新行
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(normal_left);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("您的帐单信息：");
                 sheet.addMergedRegion(new CellRangeAddress(i, (short) 0, i,
                                                  (short) 9));
                 i++;
                 j=0;
                 row = sheet.createRow((short) i); //建立新行
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("就诊日期");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("收据号");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("费用类别");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("就诊费用");

                 String sql1="select codename from ldcode1 where codetype='colname' and code=(select mngcom from llregister where rgtno='"+RgtNo+"' and rownum<=1) order by code1";
                 SSRS colname1=myExeSQL.execSQL(sql1);
                 for (int tmpk=1;tmpk<=colname1.getMaxRow();tmpk++)
                 {
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     normal_left.setWrapText(true);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue(colname1.GetText(tmpk,1));
                 }
                 String strSQL = "";
                 strSQL = "select receiptdate,billno,feeitemtype,totalfee,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10 from llcasereceiptclass";
                 strSQL = strSQL + " where insuredno='" + tSSRS.GetText(k,1) + "'";
                 strSQL = strSQL + "and clmno='" + RgtNo + "'";
                 SSRS tSSRS1 = myExeSQL.execSQL(strSQL);

                 i++;
                 j=-1;
                 row = sheet.createRow((short) i); //建立新行

                 int colcount=Stringtools.stringtoint(myExeSQL.getOneValue("select count(*) from ldcode1 where codetype='colname' and code=(select mngcom from llregister where rgtno='"+RgtNo+"' and rownum<=1)"));
                 for (int tmp1i=1;tmp1i<=tSSRS1.getMaxRow();tmp1i++)
                 {
                     for (int tmp2i=1;tmp2i<=(colcount+4);tmp2i++)
                     {
                         String colvalue=tSSRS1.GetText(tmp1i,tmp2i);
                         if (colvalue.equals("A"))
                         {
                             colvalue="门诊";
                         }
                         if (colvalue.equals("B"))
                         {
                             colvalue="住院";
                         }
                         j++;
                         cell = row.createCell( j); //建立新cell
                         cell.setCellStyle(borderright);
                         normal_left.setWrapText(true);
                         //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                         cell.setCellValue(colvalue);
                     }
                     i++;
                     j=-1;
                     row = sheet.createRow((short) i); //建立新行

                 }

                 //开始写扣除费用信息
                 i++;
                 j=0;
                 row = sheet.createRow((short) i); //建立新行
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(normal_left);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("费用扣除明细：");
                 sheet.addMergedRegion(new CellRangeAddress(i, (short) 0, i,(short) 3));
                 i++;
                 j=0;
                 row = sheet.createRow((short) i); //建立新行
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("收据号");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 normal_left.setWrapText(true);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("扣除金额");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("扣除原因");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");
                 j++;
                 cell = row.createCell( j); //建立新cell
                 cell.setCellStyle(borderright);
                 //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                 cell.setCellValue("");

                 sheet.addMergedRegion(new CellRangeAddress(i, (short) 2, i,(short) 9));
                 sql1="select billno,totalfee-totaladjfee,";
                 for(int tmpk=1;tmpk<=colcount;tmpk++)
                 {
                     sql1=sql1+"reason"+Stringtools.inttostring(tmpk)+"||'  '||";
                 }
                 sql1=sql1+" reason";
                 sql1=sql1+"  from llcasereceiptclass ";
                 sql1=sql1+" where InsuredNo='"+tSSRS.GetText(k,1)+"' and clmno='"+RgtNo+"' and totalfee>totaladjfee";
                 sql1=sql1+ " order by billno";
                 SSRS tSSRS3=myExeSQL.execSQL(sql1);
                 //i++;
                 for (int tmpk=1;tmpk<=tSSRS3.getMaxRow();tmpk++)
                 {
                     i++;
                     j=0;
                     row = sheet.createRow((short) i); //建立新行
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     normal_left.setWrapText(true);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue(tSSRS3.GetText(tmpk,1));
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     normal_left.setWrapText(true);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue(tSSRS3.GetText(tmpk,2));
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     normal_left.setWrapText(true);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue(tSSRS3.GetText(tmpk,3));

                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     j++;
                     cell = row.createCell( j); //建立新cell
                     cell.setCellStyle(borderright);
                     //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                     cell.setCellValue("");
                     sheet.addMergedRegion(new CellRangeAddress(i, (short) 2, i,(short) 9));

                 }
             }//有社保帐单信息  结束
             i=i+1;

        }//for循环

        try{
        FileOutputStream fileOut = new FileOutputStream(filename);
        wb.write(fileOut);
        fileOut.close();
        logger.debug("====excel文件生成成功：========"+filename);
     }catch(Exception e1)
     {
         logger.debug("===="+filename+"=====Excel文件行数为："+"文件无法继续写下去了！");
         state="写EXCEL文件出错，出错信息："+e1.getMessage();
         return state;
     }


        return state;
    }
}
