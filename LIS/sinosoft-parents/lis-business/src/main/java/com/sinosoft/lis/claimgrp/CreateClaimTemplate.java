package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.io.File;
import com.sinosoft.utility.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.awt.*;
import com.sinosoft.lis.pubfun.*;


public class CreateClaimTemplate {
private static Logger logger = Logger.getLogger(CreateClaimTemplate.class);
    public CreateClaimTemplate() {
    }
    //生成批量理赔模板(赔案号,模板文件名)
    public boolean CreateSimpleClaimTemplate(String RgtNo,String FileName)
    {
        ExeSQL tExeSQL=new ExeSQL();
        String tcheck=tExeSQL.getOneValue("select count(1) from llsubreport where subrptno='"+RgtNo+"'");
        if (tcheck.trim().length()==0)
        {
            logger.debug("=======查询表llsubreport没有找到数据！======");
            return false;
        }
        HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象

        HSSFFont font = wb.createFont();

        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)12);
        HSSFCellStyle normal= wb.createCellStyle();
        normal.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
        normal.setFont(font);
        normal.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
        normal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
        normal.setDataFormat((short) 0x31);

        //建立一个新的工作表
        HSSFSheet sheet = wb.createSheet();
        wb.setSheetName( 0, "出险人信息" );
        //wb.setSheetName(0,"出险人信息");



        //打印设置
        //HSSFPrintSetup myPrintSetup=sheet.getPrintSetup();
        //myPrintSetup.setPaperSize((short) 9);   //设置成A4纸
        //myPrintSetup.setLandscape(true);        //纸张横放

        //声明变量
        HSSFRow row;
        HSSFCell cell;
        HSSFCell csCell;
        sheet.setDefaultColumnWidth(14);

        int i=0;
        int j=0;

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
        cell.setCellValue("保单险种号"); //设置中西文结合字符串

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
        cell.setCellValue("性别"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件类型"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件号码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险类型"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险原因"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险日期"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("立案号"); //设置中西文结合字符串

        String SQL="";
        SQL = "select customerno,customername,to_char(accdate,'yyyy-mm-dd'),subrptno,condoleflag  from llsubreport ";
        SQL = SQL + " where subrptno='"+RgtNo+"'";
        //SQL = SQL + " order by customerno ";
        SQL = SQL + " order by to_number(CondoleFlag) "; //按导入时的ID号导出
        SSRS tSSRS=new SSRS();
        tSSRS=tExeSQL.execSQL(SQL);
        if (tSSRS!=null)
        {
            int num=tSSRS.getMaxRow();
            for (int l=1;l<=num;l++)
            {
                i++;
                j=0;
                //案件ID
                row = sheet.createRow((short) i); //建立新行

                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(l); //设置中西文结合字符串
//                cell.setCellValue(tSSRS.GetText(l,5)); //案件ID改成导出报案表里的顺序

                //团体客户号
                String AppntNo=tExeSQL.getOneValue("select appntno from llreport where rptno='"+RgtNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(AppntNo); //设置中西文结合字符串

                //团体保单号
                String GrpContNo=tExeSQL.getOneValue("select GrpContNo from llreport where rptno='"+RgtNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(GrpContNo); //设置中西文结合字符串

                //保单险种号
                String RiskCode=tExeSQL.getOneValue("select RiskCode from llreport where rptno='"+RgtNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(RiskCode); //设置中西文结合字符串

                //个人客户号
                String InsuredNo=tSSRS.GetText(l,1);
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(InsuredNo); //设置中西文结合字符串

                //出险人姓名
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(tSSRS.GetText(l,2)); //设置中西文结合字符串

                //性别
                String Sex=tExeSQL.getOneValue("select sex from lcinsured where GrpContNo='"+GrpContNo+"' and insuredno='"+InsuredNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(Sex); //设置中西文结合字符串

                //证件类型
                String IDType=tExeSQL.getOneValue("select idtype from lcinsured where GrpContNo='"+GrpContNo+"' and insuredno='"+InsuredNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(IDType); //设置中西文结合字符串

                //证件号码
                String IDNo=tExeSQL.getOneValue("select IDNo from lcinsured where GrpContNo='"+GrpContNo+"' and insuredno='"+InsuredNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(IDNo); //设置中西文结合字符串

                //出险类型
                String rgtclass=tExeSQL.getOneValue("select reasoncode  from LLReportReason where rpno='"+RgtNo+"' and customerno='"+InsuredNo+"'");
                //出险原因
                String claimreason=rgtclass.substring(0,1);
                //出险类型
                String claimtype="";
                SSRS tClaimTypeSSRS = new SSRS();
                tClaimTypeSSRS = tExeSQL.execSQL("select reasoncode  from LLReportReason where rpno='"+RgtNo+"' and customerno='"+InsuredNo+"'");
                if(tClaimTypeSSRS.getMaxRow()>0){
                	claimtype = tClaimTypeSSRS.GetText(1, 1).substring(1,3);
                }
                for(int k=1;k<tClaimTypeSSRS.getMaxRow();k++){
                	claimtype = claimtype+","+tClaimTypeSSRS.GetText(k+1, 1).substring(1,3);
                }
               //出险类型
//                String claimtype=rgtclass.substring(1,3);
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(claimtype); //设置中西文结合字符串

                //出险原因
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(claimreason); //设置中西文结合字符串

                //出险日期
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(tSSRS.GetText(l,3)); //设置中西文结合字符串

                //赔案号
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(RgtNo); //设置中西文结合字符串
            }
        }


        //建立第二个工作表
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(14);
        wb.setSheetName( 1, "帐单信息" );
        //wb.setSheetName(1,"帐单信息");
         i=0;
         j=0;

        row = sheet.createRow((short) i); //建立新行

        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("序号"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("个人客户号"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("帐单种类"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("医院代码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("收据号码"); //设置中西文结合字符串

//        j++;
//        cell = row.createCell(j); //建立新cell
//        cell.setCellStyle(normal);
//        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
//        cell.setCellValue("疾病代码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("开始日期"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("结束日期"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("费用类型"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("原始费用"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("扣除费用"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("扣除原因代码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("具体扣除说明"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("社保赔付费用"); //设置中西文结合字符串

//        j++;
//        cell = row.createCell(j); //建立新cell
//        cell.setCellStyle(normal);
//        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
//        cell.setCellValue("住院起付线"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种或社保第三方费用类型"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种或社保第三方费用代码"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种或社保给付费用"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("服务机构名称"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种费用起始日期"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种费用结束日期"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种或社保第三方给付费用备注"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("比例给付代码"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("比例给付类型"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("比例给付级别代码"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("鉴定机构"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("鉴定日期"); //设置中西文结合字符串
        
        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("比例给付备注"); //设置中西文结合字符串
        
        //建立第三个工作表(写帮助信息)
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(20);
        //wb.setSheetName(2,"帮助");
        wb.setSheetName( 2, "帮助" );
        i=0;
        j=0;
        row = sheet.createRow((short) i); //建立新行

        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("说明："); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("为保证导入数据的准确性，请将所有单元格格式设置成文本格式！"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("序号、个人客户号、账单种类、医院代码、收据号码、开始日期、结束日期、费用类型为必录项，否则可能会导致无法查询到错误信息！"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("账单信息Sheet每行只能录一条账单信息"); //设置中西文结合字符串
        
        i++;
        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险人信息工作表："); //设置中西文结合字符串
        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件类型："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select code||'：'||codename  from ldcode where codetype='idtype' and code<>'9'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险类型："); //设置中西文结合字符串
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("00：医疗"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("01：残疾、烧伤、骨折、重要器官切除 "); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("02：身故"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("03：高残 "); //设置中西文结合字符串
        
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("04：重大疾病"); //设置中西文结合字符串
        
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("05：特种疾病"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("09：豁免 "); //设置中西文结合字符串

        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险原因："); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("1：意外"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("2：疾病"); //设置中西文结合字符串


        i++;
        i++;
        j=j-1;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("帐单信息工作表："); //设置中西文结合字符串

        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("帐单种类："); //设置中西文结合字符串
        
        tSSRS=tExeSQL.execSQL("select code||'：'||codename from ldcode where codetype='llfeetype' and code<>'C'");
        for (int l=1;l<=tSSRS.getMaxRow();l++) {
	        i++;
	        row = sheet.createRow((short) i); //建立新行
	        cell = row.createCell(j); //建立新cell
	        cell.setCellStyle(normal);
	        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
	        cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
//        i++;
//        row = sheet.createRow((short) i); //建立新行
//        cell = row.createCell(j); //建立新cell
//        cell.setCellStyle(normal);
//        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
//        cell.setCellValue("01：门诊"); //设置中西文结合字符串
//
//        i++;
//        row = sheet.createRow((short) i); //建立新行
//        cell = row.createCell(j); //建立新cell
//        cell.setCellStyle(normal);
//        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
//        cell.setCellValue("02：住院"); //设置中西文结合字符串
//        i++;
//        row = sheet.createRow((short) i); //建立新行
//        cell = row.createCell(j); //建立新cell
//        cell.setCellStyle(normal);
//        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
//        cell.setCellValue("03：综合"); //设置中西文结合字符串

        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("扣除原因代码："); //设置中西文结合字符串

        tSSRS=tExeSQL.execSQL("select code||'：'||codename from ldcode where codetype='deductreason'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }

        //建立第四个工作表(费用类型)
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(20);
        //wb.setSheetName(2,"帮助");
        wb.setSheetName( 3, "费用类型" );
        i=0;
        j=0;
        row = sheet.createRow((short) i); //建立新行

        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("说明："); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("为保证导入数据的准确性，请将所有单元格格式设置成文本格式！"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("序号、个人客户号、账单种类、医院代码、收据号码、开始日期、结束日期、费用类型为必录项，否则可能会导致无法查询到错误信息！"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("账单信息Sheet每行只能录一条账单信息"); //设置中西文结合字符串
        
        i++;
        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("门诊单证费用类型"); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select code||'：'||codename from ldcode where 1 = 1 and codetype = 'llmedfeetype' order by Code");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("住院单证费用类型："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL(" select code||'：'||codename from ldcode where 1 = 1 and codetype = 'llhospitalfeetype' order by Code");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("D:社保第三方给付"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("D：社保第三方给付费用代码"); //设置中西文结合字符串
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("D001：社保给付"); //设置中西文结合字符串
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("D002：第三方给付给付"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种费用类型："); //设置中西文结合字符串
        
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("TP:救援服务费用类型"); //设置中西文结合字符串
        
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("特种费用代码："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select code||'：'||codename from ldcode where 1 = 1 and codetype = 'llfactypesuccor' order by Code");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("比例给付类型："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select code||'：'||codename from ldcode where codetype='lldefotype'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("比例给付类型为“LZ:骨折”时比例给付级别代码："); //设置中西文结合字符串
        
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("K：开放性"); //设置中西文结合字符串
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("R：非开放性"); //设置中西文结合字符串
        
        //建立第五个工作表(比例给付代码)
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(20);
        //wb.setSheetName(2,"帮助");
        wb.setSheetName( 4, "比例给付代码" );
        i=0;
        j=0;
        row = sheet.createRow((short) i); //建立新行

        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("说明："); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("为保证导入数据的准确性，请将所有单元格格式设置成文本格式！"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("序号、个人客户号、账单种类、医院代码、收据号码、开始日期、结束日期、费用类型为必录项，否则可能会导致无法查询到错误信息！"); //设置中西文结合字符串
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("账单信息Sheet每行只能录一条账单信息"); //设置中西文结合字符串
        
        i++;
        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("伤残比例给付代码："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select defocode||'：'||defoname from LLParaDeformity where 1=1  and DefoType = 'LF'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("烧伤比例给付代码："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select defocode||'：'||defoname from LLParaDeformity where 1=1  and DefoType = 'LG'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("重要器官切除比例给付代码："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select defocode||'：'||defoname from LLParaDeformity where 1=1  and DefoType = 'LI'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("高度残疾比例给付代码："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select defocode||'：'||defoname from LLParaDeformity where 1=1  and DefoType = 'LM'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("骨折比例给付代码："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select defocode||'：'||defoname from LLParaDeformity where 1=1  and DefoType = 'LZ' order by defocode");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
        	i++;
        	row = sheet.createRow((short) i); //建立新行
        	cell = row.createCell(j); //建立新cell
        	cell.setCellStyle(normal);
//        	cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        	cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        
        try{
            FileOutputStream fileOut = new FileOutputStream(FileName);
            wb.write(fileOut);
            fileOut.close();
            logger.debug("====excel文件生成成功：========"+FileName);
        }catch(Exception e1)
        {
            logger.debug("===="+FileName+"=====Excel文件行数为："+"文件无法继续写下去了！");
            String state="写EXCEL文件出错，出错信息："+e1.getMessage();
            e1.printStackTrace();
            logger.debug(state);
            return false;
        }
        return true;
    }
    //帐户理赔模板
    public boolean CreateAccClaimTemplate(String RgtNo,String FileName)
    {
        ExeSQL tExeSQL=new ExeSQL();
        String tcheck=tExeSQL.getOneValue("select count(1) from llsubreport where subrptno='"+RgtNo+"'");
        if (tcheck.trim().length()==0)
        {
            logger.debug("=======查询表llsubreport没有找到数据！======");
            return false;
        }
        HSSFWorkbook wb = new HSSFWorkbook();//建立新HSSFWorkbook对象

        HSSFFont font = wb.createFont();

        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)12);
        HSSFCellStyle normal= wb.createCellStyle();
        normal.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
        normal.setFont(font);
        normal.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
        normal.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

        //建立一个新的工作表
        HSSFSheet sheet = wb.createSheet();
        wb.setSheetName( 0, "出险人信息" );
        //wb.setSheetName(0,"出险人信息");

        //打印设置
        //HSSFPrintSetup myPrintSetup=sheet.getPrintSetup();
        //myPrintSetup.setPaperSize((short) 9);   //设置成A4纸
        //myPrintSetup.setLandscape(true);        //纸张横放

        //声明变量
        HSSFRow row;
        HSSFCell cell;
        HSSFCell csCell;
        sheet.setDefaultColumnWidth(14);

        int i=0;
        int j=0;

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
        cell.setCellValue("保单险种号"); //设置中西文结合字符串

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
        cell.setCellValue("性别"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件类型"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件号码"); //设置中西文结合字符串

       j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险日期"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("是否使用团体帐户金额"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("个人账户赔付金额"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("团体账户赔付金额"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("理赔类型"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险原因"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("赔案号"); //设置中西文结合字符串

        String SQL="";
        SQL = "select customerno,customername,to_char(accdate,'yyyy-mm-dd'),subrptno,condoleflag  from llsubreport ";
        SQL = SQL + " where subrptno='"+RgtNo+"'";
        //SQL = SQL + " order by customerno ";
        SQL = SQL + " order by to_number(CondoleFlag) "; //按导入时的ID号导出
        SSRS tSSRS=new SSRS();
        tSSRS=tExeSQL.execSQL(SQL);
        //是否使用团体账户
        String rgtObj = tExeSQL.getOneValue("select rgtobj from llreport where rptno='" +RgtNo +"' and rownum<=1");
        if(rgtObj==null||rgtObj.length()<1){
        	rgtObj = "";
        }
        if (tSSRS!=null)
        {
            int num=tSSRS.getMaxRow();
            for (int l=1;l<=num;l++)
            {
                i++;
                j=0;
                //案件ID
                row = sheet.createRow((short) i); //建立新行

                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                //cell.setCellValue(l); //设置中西文结合字符串
                cell.setCellValue(tSSRS.GetText(l,5)); //案件ID改成导出报案表里的顺序

                //团体客户号
                String AppntNo=tExeSQL.getOneValue("select appntno from llreport where rptno='"+RgtNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(AppntNo); //设置中西文结合字符串

                //团体保单号
                String GrpContNo=tExeSQL.getOneValue("select GrpContNo from llreport where rptno='"+RgtNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(GrpContNo); //设置中西文结合字符串

                //保单险种号
                String RiskCode=tExeSQL.getOneValue("select RiskCode from llreport where rptno='"+RgtNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(RiskCode); //设置中西文结合字符串

                //个人客户号
                String InsuredNo=tSSRS.GetText(l,1);
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(InsuredNo); //设置中西文结合字符串

                //出险人姓名
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(tSSRS.GetText(l,2)); //设置中西文结合字符串

                //性别
                String Sex=tExeSQL.getOneValue("select sex from lcinsured where GrpContNo='"+GrpContNo+"' and insuredno='"+InsuredNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(Sex); //设置中西文结合字符串

                //证件类型
                String IDType=tExeSQL.getOneValue("select idtype from lcinsured where GrpContNo='"+GrpContNo+"' and insuredno='"+InsuredNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(IDType); //设置中西文结合字符串

                //证件号码
                String IDNo=tExeSQL.getOneValue("select IDNo from lcinsured where GrpContNo='"+GrpContNo+"' and insuredno='"+InsuredNo+"'");
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(IDNo); //设置中西文结合字符串

                //出险日期
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(tSSRS.GetText(l,3)); //设置中西文结合字符串

                //是否使用团体帐户金额
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                cell.setCellValue(rgtObj); //设置中西文结合字符串

                //个人帐户赔付金额
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                cell.setCellValue(""); //设置中西文结合字符串

                //团体帐户赔付金额
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                cell.setCellValue(""); //设置中西文结合字符串


                String rgtclass=tExeSQL.getOneValue("select reasoncode  from LLReportReason where rpno='"+RgtNo+"' and customerno='"+InsuredNo+"'");
                //出险原因
                String claimreason=rgtclass.substring(0,1);
               //出险类型
                String claimtype=rgtclass.substring(1,3);

                //理赔类型
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(claimtype); //设置中西文结合字符串

                //出险原因
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(claimreason); //设置中西文结合字符串

//              赔案号
                j++;
                cell = row.createCell(j); //建立新cell
                cell.setCellStyle(normal);
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
                cell.setCellValue(RgtNo); //设置中西文结合字符串
            }
        }


        //建立第二个工作表
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(14);
        wb.setSheetName( 1, "帐单信息" );
        //wb.setSheetName(1,"帐单信息");
         i=0;
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
        cell.setCellValue("帐单种类"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("医院代码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("收据号码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("疾病代码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("开始日期"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("结束日期"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("费用类型"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("原始费用"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("扣除费用"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("扣除原因代码"); //设置中西文结合字符串

        j++;
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("具体扣除说明"); //设置中西文结合字符串

        //建立第三个工作表(写帮助信息)
        sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(20);
        wb.setSheetName( 2, "帮助" );
        //wb.setSheetName(2,"帮助");
        i=0;
        j=0;
        row = sheet.createRow((short) i); //建立新行

        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("说明："); //设置中西文结合字符串
        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险人信息工作表："); //设置中西文结合字符串
        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("证件类型："); //设置中西文结合字符串
        tSSRS=tExeSQL.execSQL("select code||'：'||codename  from ldcode where codetype='idtype'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }
        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险类型："); //设置中西文结合字符串
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("00：医疗"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("01：伤残"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("02：死亡"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("04：大病"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("06：失能"); //设置中西文结合字符串

        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("出险原因："); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("1：意外"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("2：疾病"); //设置中西文结合字符串

        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("是否使用团体帐户金额："); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("10：是"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("20：否"); //设置中西文结合字符串


        i++;
        i++;
        j=j-1;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("帐单信息工作表："); //设置中西文结合字符串

        i++;
        j++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("帐单种类："); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("A：门诊"); //设置中西文结合字符串

        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("B：住院"); //设置中西文结合字符串

        i++;
        i++;
        row = sheet.createRow((short) i); //建立新行
        cell = row.createCell(j); //建立新cell
        cell.setCellStyle(normal);
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
        cell.setCellValue("扣除原因代码："); //设置中西文结合字符串

        tSSRS=tExeSQL.execSQL("select code||'：'||codename from ldcode where codetype='deductreason'");
        for (int l=1;l<=tSSRS.getMaxRow();l++)
        {
            i++;
            row = sheet.createRow((short) i); //建立新行
            cell = row.createCell(j); //建立新cell
            cell.setCellStyle(normal);
            //cell.setEncoding(HSSFCell.ENCODING_UTF_16); //设置cell编码解决中文高位字节截断
            cell.setCellValue(tSSRS.GetText(l,1)); //设置中西文结合字符串
        }

        try{
            FileOutputStream fileOut = new FileOutputStream(FileName);
            wb.write(fileOut);
            fileOut.close();
            logger.debug("====excel文件生成成功：========"+FileName);
        }catch(Exception e1)
        {
            logger.debug("===="+FileName+"=====Excel文件行数为："+"文件无法继续写下去了！");
            String state="写EXCEL文件出错，出错信息："+e1.getMessage();
            logger.debug(state);
            return false;
        }


        return true;
    }

}
