package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.*;
import com.lowagie.text.Font;
import com.lowagie.text.html.HtmlWriter;
import java.awt.color.*;
import java.awt.font.*;
import com.sinosoft.utility.ExeSQL;
import com.lowagie.text.pdf.fonts.*;
import java.io.FileOutputStream;
import java.io.IOException;
import com.sinosoft.lis.pubfun.PubFun.*;
import com.sinosoft.utility.DBConnPool;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.awt.font.*;
import java.io.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfContentByte;
import com.sinosoft.utility.*;


//程序功能：将字符串数组生成套模板的pdf文件,本程序只生成一页文件，没有多页内容！！
//模板中的变量名为Text1、Text2...........
public class TemplateToPdf {
private static Logger logger = Logger.getLogger(TemplateToPdf.class);
  /*程序已经通过测试
   public static void main(String[] args)
   {
       String [] str={"1","2","3"};
       TemplateToPdf my=new TemplateToPdf();
      //                                         生成文件      模板文件
       logger.debug("my:"+my.CreatePDF(str,"susu1.pdf","temp.pdf"));
   }
   */
//打印理赔退费收据（领款收据）
  public String[] CreateGetBill(String RgtNo) {
    String[] data = new String[30];
    ExeSQL my = new ExeSQL();
    String num = my.getOneValue(
        "select count(*) from LJAGet  where OtherNoType='5' and OtherNo='" +
        RgtNo + "' and OPERSTATE='0'");
    if (Stringtools.stringtoint(num) < 1) {
      Stringtools.log("", "====没有退费数据===com.tk.endor.TemplateToPdf.java\n", "");
      data[0] = "0";
      data[1] = "理赔赔案号：" + RgtNo.trim() + "没有退费数据!!";
      return data;
    }
    //取保单号
    String GrpContNo = my.getOneValue(
        "select GrpContNo from LLClaimPolicy where ClmNo='" + RgtNo +
        "' and  rownum<=1");
    if (GrpContNo.trim().length() == 0) {
      data[0] = "0";
      data[1] = "理赔赔案号：" + RgtNo.trim() +
                " 取保单号失败!SQL:select GrpContNo from LLClaimPolicy where clmno='" +
                RgtNo + "' and  GiveType='0'";
      Stringtools.log("", data[1], "");
      return data;
    }
    //投保单位
    String GrpName = my.getOneValue("select GrpName from lcgrpcont where grpcontno='"+GrpContNo+"' and rownum<=1");
    //取总退费
    String StringTotalPremium = my.getOneValue(
        "select abs(sum(SumGetMoney)) from LJAGet where OtherNoType='5' and OtherNo='" +
        RgtNo + "' and OPERSTATE='0'");
    //总保费中文
    double DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                                stringtodouble(StringTotalPremium);
    String ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
        DoubleTotalPremium);
    //收据号
    String ActuGetNo = my.getOneValue(
        "select ActuGetNo from LJAGet  where  OtherNoType='5' and OtherNo='" +
        RgtNo + "' and OPERSTATE='0'");
    data[0] = ActuGetNo;
    data[1] = GrpName;
    data[2] = GrpContNo;
    data[3] = RgtNo;
    if (data[0].trim().length() == 0 || data[1].trim().length() == 0 ||
        data[2].trim().length() == 0 || data[3].trim().length() == 0) {
      Stringtools.log("",
                      "====退费收据==取投保人等信息失败===com.tk.endor.TemplateToPdf.java\n" +
                      RgtNo, "");
      data[0] = "0";
      data[1] = "批单号：" + RgtNo.trim() + "取投保人信息等失败!!";
      return data;
    }
    //循环取 险种，保费  数组下标范围data[4]---data[19]
    String Sql = "select a.RiskCode,b.RiskName,abs(sum(a.RealPay)) from LLClaimPolicy a,LMRiskApp b";
    Sql = Sql + " where a.RiskCode=b.RiskCode  and a.ClmNo='" + RgtNo + "'";
    Sql = Sql + " and (a.GiveType='0' or a.GiveType='2' or a.GiveType='3' )";
    Sql = Sql + " group by a.RiskCode,b.RiskName";
    Stringtools.log("", " 循环取 险种，保费  数组下标范围data[4]---data[19]===\n" + Sql, "");
    //数据库连接
    try {
      SSRS rs = new SSRS();
      rs = my.execSQL(Sql);
    	
    	
      String RiskCode = "";
      String RiskName = "";
      int i = 4;
      for(int j=1;j<=rs.getMaxRow();j++) {

        if (i > 19) {

          data[0] = "0";
          data[1] =
              "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
          Stringtools.log("", data[1], "");
          break;
        }
        RiskCode = rs.GetText(j, 1).trim();
        RiskName = rs.GetText(j, 2).trim();
        data[i] = RiskCode + "--" + RiskName;
        data[i + 1] = "￥" +  Stringtools.stringtostring(rs.GetText(j, 3).trim(),2) + "  元";
        i = i + 2;
      }
      
      //合计金额小写 大写
      String total = my.getOneValue(
          "select sum(SumGetMoney) from LJAGet where OtherNoType='5' and OtherNo='" +
          RgtNo + "' and OPERSTATE='0'");
      data[20] = PubFun.getChnMoney(Stringtools.stringtodouble(total));
      data[21] = Stringtools.stringtostring(total, 2);
      //业务经办：
      String Operator = my.getOneValue(
          "select Operator from LLRegister where  RgtNo='" + RgtNo + "' ");
      data[22] = my.getOneValue("select username from lduser where usercode='" +
                                Operator + "'");
      //保全核保人
      String ClmUWer = my.getOneValue(
          "select ClmUWer from LLClaim where RgtNo='" + RgtNo +
          "'  and GiveType='0'");
      data[23] = my.getOneValue("select username from lduser where usercode='" +
                                ClmUWer + "'");
      //data[23] =data[22] ;
      //data[22] ="";

      //结案日期(暂时不取这个日期,模板上有这个位置，先空白)
      String EndCaseDate = ""; //my.getOneValue("select to_char(EndCaseDate,'yyyy-mm-dd') from LLRegister where RgtNo='"+RgtNo+"'");
      //if (EndCaseDate.length()>=10)
      //{
      data[24] = ""; //EndCaseDate.substring(0,4);
      data[25] = ""; //EndCaseDate.substring(5,7);
      data[26] = ""; //EndCaseDate.substring(8,10);
      //}
      /*
                 else
                 {
          data[0]="0";
          data[1]="取结案日期出错！"+"赔案号："+RgtNo;
          Stringtools.log("",data[1],"");
          return data;
                 }*/
      // 领款类别
      data[27] = "团险理赔";
      //业务员
      //String AgentCode=my.getOneValue("select trim(a.agentcode)||'--'||trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='"+GrpContNo+"'");
      String AgentCode = my.getOneValue("select trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='" +
                                        GrpContNo + "'");

      if (AgentCode.trim().length() == 0) {
        data[0] = "0";
        data[1] = "取业务员错误" + "赔案号：" + RgtNo + "保单号：(LpGrpCont)" + GrpContNo;
        Stringtools.log("", data[1], "");
        return data;

      }
      data[28] = AgentCode;
      //新增加一个字段(支付 XXXX等)
      SSRS tSSRS=new SSRS();
      ExeSQL tExeSQL=new ExeSQL();
      String peoplestr="支付 ";
      tSSRS=tExeSQL.execSQL("select distinct a.customerno,b.insuredname from llclaimdetail  a,lcpol b where a.clmno='"+RgtNo+"' and b.grpcontno='"+GrpContNo+"' and a.customerno=b.insuredno  and a.riskcode=b.riskcode and rownum<=2");
      if (tSSRS.getMaxRow()==1)
      {
          peoplestr=peoplestr+tSSRS.GetText(1,2)+"      1人 ";
      }
      else
      {
          peoplestr=peoplestr+tSSRS.GetText(1,2)+"、"+tSSRS.GetText(2,2);
          String tmpnum=tExeSQL.getOneValue("select count(unique customerno) from llclaimdetail where clmno='"+RgtNo+"'");
          int num1=Stringtools.stringtoint(tmpnum);
     if (num1==2)
     {
         peoplestr=peoplestr+" 2 人";
     }
     else
     {
         peoplestr=peoplestr+" 等 "+tmpnum+" 人";
     }
 }
     peoplestr=peoplestr+"理赔款。";
     data[29]=peoplestr;

    } catch (Exception e) {
      Stringtools.log("",
                      "===理赔退费收据=组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                      e.getMessage(), "");
      data[0] = "0";
      data[1] = "====理赔组织数据时出错(打印退费收据)===" + e.getMessage();

    }
    return data;
  }


  //取数据以打印保全退费收据（领款收据）
  public String[] PayBillData(String GrpEdorNo) {
    String[] data = new String[29];
    ExeSQL my = new ExeSQL();
    String num = my.getOneValue(
        "select count(*) from LJAGet  where OtherNoType='10' and OtherNo='" +
        GrpEdorNo + "' and OPERSTATE='0'");
    if (com.sinosoft.lis.claimgrp.Stringtools.stringtoint(num) < 1) {
      Stringtools.log("", "====没有退费数据===com.tk.endor.TemplateToPdf.java\n", "");
      data[0] = "0";
      data[1] = "批单号：" + GrpEdorNo.trim() + "没有退费数据!!";
      return data;
    }

    //取保单号
    String GrpContNo = my.getOneValue(
        "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo + "'");
    //投保单位
    String GrpName = my.getOneValue(
        "select GrpName from LCGrpCont where GrpContNo='" + GrpContNo + "'");
    //取总退费
    String StringTotalPremium = my.getOneValue(
        "select abs(sum(GetMoney)) from LJAGetEndorse where EndorsementNo='" +
        GrpEdorNo + "' and OPERSTATE='0' "); //and GetMoney<0");
    //总退费中文
    double DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                                stringtodouble(StringTotalPremium);
    String ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
        DoubleTotalPremium);
    //收据号
    String ActuGetNo = my.getOneValue(
        "select ActuGetNo from LJAGet  where  OtherNoType='10' and OtherNo='" +
        GrpEdorNo + "' and OPERSTATE='0'");
    data[0] = ActuGetNo;
    data[1] = GrpName;
    data[2] = GrpContNo;
    data[3] = GrpEdorNo;
    if (data[0].trim().length() == 0 || data[1].trim().length() == 0 ||
        data[2].trim().length() == 0 || data[3].trim().length() == 0) {
      Stringtools.log("",
                      "====退费收据==取投保人等信息失败===com.tk.endor.TemplateToPdf.java\n" +
                      GrpEdorNo, "");
      data[0] = "0";
      data[1] = "批单号：" + GrpEdorNo.trim() + "取投保人信息等失败!!";
      return data;
    }
    //循环取 险种，保费  数组下标范围data[4]---data[19]
    String Sql = "select RiskCode,abs(sum(GetMoney)) from LJAGetEndorse ";
    Sql = Sql + " where  EndorsementNo='" + GrpEdorNo + "' and OPERSTATE='0'";
    // Sql = Sql + " and a.GetMoney<0";
    Sql = Sql + " group by RiskCode";
    Stringtools.log("", " 循环取 险种，保费  数组下标范围data[4]---data[19]===\n" + Sql, "");
    //数据库连接

    try {
      SSRS tSSRS = my.execSQL(Sql);
      int row = tSSRS.getMaxRow();
      String RiskCode = "";
      String RiskName = "";
      int i = 4;
      for (int j = 1; j <= row; j++) {

        if (i > 19) {

          data[0] = "0";
          data[1] =
              "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
          Stringtools.log("", data[1], "");
          break;
        }
        RiskCode = tSSRS.GetText(j, 1);
        RiskName = my.getOneValue(
            "select RiskName from LMRiskApp " +
            " where RiskCode='" + RiskCode +
            "'");

        data[i] = RiskCode + "--" + RiskName;
        data[i + 1] = "￥" + tSSRS.GetText(j, 2) + "  元";
        i = i + 2;
      }

      //合计金额小写 大写
      String total = StringTotalPremium; //my.getOneValue("select sum(SumGetMoney) from LJAGet where OtherNoType='10' and OtherNo='"+GrpEdorNo+"'");
      data[20] = PubFun.getChnMoney(Stringtools.stringtodouble(total));
      data[21] = Stringtools.stringtostring(total, 2);
      //业务经办：
      Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
      Sql = Sql +
            " where trim(UserCode)= (select trim(operator) from LPEdorApp ";
      Sql = Sql + " where EdorAcceptNo='" + GrpEdorNo + "')";
      data[22] = ""; // my.getOneValue(Sql);
      //保全核保人
      Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
      Sql = Sql +
            " where trim(UserCode)= (select trim(UWOperator) from LPGrpEdorMain ";
      Sql = Sql + " where EdorNo='" + GrpEdorNo + "')";
      data[23] = my.getOneValue(Sql);
      //保全确认日期(暂时不要这个日期)
      String TmpDate = ""; //my.getOneValue("select ConfDate from LPGrpEdorMain where EdorNo='"+GrpEdorNo+"'").trim();
      //if (TmpDate.length()>=10)
      //{
      data[24] = ""; //TmpDate.substring(0,4);
      data[25] = ""; //TmpDate.substring(5,7);
      data[26] = ""; //TmpDate.substring(8,10);
      //}
      /*
                      else
                      {
          data[0]="0";
          data[1]="取核保日期出错！"+"批单号："+GrpEdorNo;
          Stringtools.log("",data[1],"");
          return data;
                      }*/
      // 领款类别
      data[27] = "团险保全";
      //业务员
      //String AgentCode=my.getOneValue("select trim(a.agentcode)||'--'||trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='"+GrpContNo+"'");
      String AgentCode = my.getOneValue("select trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='" +
                                        GrpContNo + "'");

      if (AgentCode.trim().length() == 0) {
        data[0] = "0";
        data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" + GrpContNo;
        Stringtools.log("", data[1], "");
        return data;

      }
      data[28] = AgentCode;

    } catch (Exception e) {
      Stringtools.log("",
                      "===退费收据=组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                      e.getMessage(), "");
      data[0] = "0";
      data[1] = "====组织数据时出错(打印退费收据)===" + e.getMessage();

    }

    return data;
  }


  //取数据以打印保险费发票

  public String[] getBillData(String GrpEdorNo) {

    String[] data = new String[32];
    //保单号：
    ExeSQL my = new ExeSQL();
    String num = my.getOneValue(
        "select count(*) from LJApay  where otherno='" + GrpEdorNo +
        "' ");
    if (com.sinosoft.lis.claimgrp.Stringtools.stringtoint(num) < 1) {
      data[0] = "0";
      data[1] = "批单号：" + GrpEdorNo.trim() + "没有交费数据!!";
      Stringtools.log("",
                      "====没有交费数据===com.tk.endor.TemplateToPdf.java\n" + data[1],
                      "");
      return data;
    }
    String GrpContNo = my.getOneValue(
        "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo + "'");
    //投保单位
    String GrpName = my.getOneValue(
        "select GrpName from LCGrpCont where GrpContNo='" + GrpContNo + "'");
    //总保费
    String StringTotalPremium = my.getOneValue(
        "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
        GrpEdorNo + "' and OPERSTATE='0' ");
    double DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                                stringtodouble(StringTotalPremium);
    //大写保费
    String ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
        DoubleTotalPremium);
    data[0] = GrpName;
    data[1] = ChinaMeony;
    data[2] = "￥" + StringTotalPremium.trim() + "  元";
    data[3] = GrpContNo;
    if (data[0].trim().length() == 0 || data[1].trim().length() == 0 ||
        data[2].trim().length() == 0 || data[3].trim().length() == 0) {
      data[0] = "0";
      data[1] = "批单号：" + GrpEdorNo.trim() + "取投保人信息失败!!";
      Stringtools.log("",
                      "====取投保人信息失败===com.tk.endor.TemplateToPdf.java\n" +
                      GrpEdorNo,
                      "");
      return data;
    }
    //循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]
    String Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b";
    Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" + GrpEdorNo +
          "' and a.OPERSTATE='0'";
    //Sql = Sql + "  and a.GetMoney>0";
    Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";
    Stringtools.log("", " 循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]===\n" + Sql,
                    "");
    //数据库连接
    try {
      Connection conn = DBConnPool.getConnection();
      Statement stmt = null;
      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = null;
      Stringtools.log("", Sql, "");
      rs = stmt.executeQuery(Sql);
      String RiskCode = "";
      String RiskName = "";
      String PayIntv = "";
      String GrpPolNo = "";
      //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
      int i = 4;
      while (rs.next()) {if (i > 24) {
        data[0] = "0";
        data[1] = "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
        Stringtools.log("", data[1], "");
        break;
      }
      RiskCode = rs.getString(1).trim();
      RiskName = rs.getString(2).trim();
      data[i] = RiskCode + "--" + RiskName;

      GrpPolNo = rs.getString(3);
      PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                               GrpPolNo + "'");

      if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
          "-1")) {
        data[i + 1] = "不定期交";

      }
      if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
          "0")) {
        data[i + 1] = "趸交";
      }
      if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
          "1")) {
        data[i + 1] = "月交";
      }
      if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
          "3")) {
        data[i + 1] = "季交";
      }
      if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
          "6")) {
        data[i + 1] = "半交";
      }
      if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
          "12")) {
        data[i + 1] = "年交";
      }
      String je = rs.getString(4);
      data[i + 2] = "￥" + je.trim() + "  元";
      i = i + 3;
      } //循环结束
      rs.close();
      stmt.close();
      conn.close();

      //交费收据
      String PayNo = my.getOneValue(
          "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
          "' and OPERSTATE='0' and rownum<=1 and OPERSTATE='0'");
      data[25] = PayNo;

      //经办人   保全核保人
      Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
      Sql = Sql +
            " where trim(UserCode)= (select trim(Operator) from LjaGetEndorse";
      Sql = Sql + " where endorsementno='" + GrpEdorNo + "' and OPERSTATE='0' and rownum<=1)";
      data[26] = my.getOneValue(Sql);
      //业务员
      //data[27]=my.getOneValue("select trim(AgentCode)||'--'||trim(Name) from LAAgent where AgentCode in (select AgentCode from LJAPay where InComeType='10' and InComeNo='"+GrpEdorNo+"')");

      String AgentCode = my.getOneValue("select trim(a.AgentCode)||'--'||trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='" +
                                        GrpContNo + "'");
      if (AgentCode.trim().length() == 0) {
        data[0] = "0";
        data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" + GrpContNo;
        Stringtools.log("", data[1], "");
        return data;
      }
      data[27] = AgentCode;

      //年月日
      DateFormat df = DateFormat.getDateInstance();
      String s = df.format(new Date());
      data[28] = s.substring(0, s.indexOf("-"));
      s = s.substring(s.indexOf("-") + 1, s.length());
      data[29] = s.substring(0, s.indexOf("-"));
      data[30] = s.substring(s.indexOf("-") + 1, s.length());
      Stringtools.log("",
                      "data[25--30]" + data[25] + "==" + data[26] + "==" +
                      data[27] + "==" + data[28] + "==" + data[29] + "==" +
                      data[30], "");
      data[31]=my.getOneValue("select '泰康人寿保险股份有限公司 '||name from ldcom where comcode in (select managecom from lpgrpedoritem where edorno='"+GrpEdorNo+"'");
      data[31]= StrTool.replace( data[31],"本部","");
      //
    } catch (Exception e) {
      Stringtools.log("",
                      "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                      e.getMessage(),
                      "");
      data[0] = "0";
      data[1] = "====组织数据时出错===" + e.getMessage();
    }

    return data;
  }


  //**************************8非标准发票打印取数据******传入批单号及2位的区站*************/
  public String getBillDataOther(String GrpEdorNo, String ManageCom,
                                 String filename, String RealPath) {
    String[] datatmp = new String[1];
    TemplateToPdf myTemplateToPdf = new TemplateToPdf();
    String ErrInfo = "";
    ExeSQL my = new ExeSQL();
    //String RealPath=myTemplateToPdf.getPdfRealPath();
    //生成的pdf文件名
    //String filename = RealPath+"pdffiles\\EndorPay"+GrpEdorNo.trim()+".pdf";
    logger.debug("=====filename==发票=====:" + filename +
                       "=========================");
    Stringtools.log("",
                    "=====filename====发票===:" + filename +
                    "=========================",
                    "");

    // if (comcode==13)
    ManagePrtBill myTemplateFilename = new ManagePrtBill();
    String TemplateFilename = RealPath + "pdftemplate\\" +
                              myTemplateFilename.
                              getTemplateFileName(ManageCom, "BQ");
    int comcode = Stringtools.stringtoint(ManageCom.substring(0, 2));

    switch (comcode) {
    
    
    /*****************************************重庆发票开始   区站：13*****************************************************/

    case 13:

      //String TemplateFilename=RealPath+"pdftemplate\\EdorPaybill13.pdf";
      logger.debug("模板文件名：" + TemplateFilename);
      Stringtools.log("", "模板文件名" + TemplateFilename, "");
      logger.debug("========分公司码：=============" + comcode);
      String[] data = new String[29];

      String num = my.getOneValue(
              "select count(*) from LJApaygrp  where endorsementno='" + GrpEdorNo +
      "' and OPERSTATE='0'");
      if (com.sinosoft.lis.claimgrp.Stringtools.stringtoint(num) < 1) {
        data[0] = "0";
        data[1] = "批单号：" + GrpEdorNo.trim() + "没有交费数据!!";
        Stringtools.log("",
                        "====没有交费数据===com.tk.endor.TemplateToPdf.java\n" +
                        data[1], "");
        ErrInfo = data[1];
        return ErrInfo;
        //return data;
      }

      //交费收据
      String PayNo = my.getOneValue(
          "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
          "' and OPERSTATE='0' and rownum<=1");
      data[0] = PayNo;

      //保单号：
      String GrpContNo = my.getOneValue(
          "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo +
          "'");
      //投保单位
      String GrpName = my.getOneValue(
          "select Name from LCGrpAppnt where GrpContNo='" + GrpContNo + "'");
      data[1] = GrpName;

      //年月日
      DateFormat df = DateFormat.getDateInstance();
      String s = df.format(new Date());
      data[2] = s.substring(0, s.indexOf("-"));
      s = s.substring(s.indexOf("-") + 1, s.length());
      data[3] = s.substring(0, s.indexOf("-"));
      data[4] = s.substring(s.indexOf("-") + 1, s.length());
      data[5] = GrpContNo;
      data[6] = "团体保险";

      String Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b ";
      Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" +
            GrpEdorNo + "' and a.OPERSTATE='0'";
      Sql = Sql + "  and  (a.feefinatype='BF' or a.feefinatype='GL')";
      //Sql = Sql + "  and a.GetMoney>0";
      Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";

      //总保费
      String StringTotalPremium = my.getOneValue(
          "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
          GrpEdorNo + "' and (feefinatype='BF' or feefinatype='GL') and OPERSTATE='0' ");
      double DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                                  stringtodouble(StringTotalPremium);

      //大写保费
      String ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
          DoubleTotalPremium);
      if (data[0].trim().length() == 0 || data[4].trim().length() == 0) {
        data[0] = "0";
        data[1] = "批单号：" + GrpEdorNo.trim() + "取投保人信息失败!!";
        Stringtools.log("",
                        "====取投保人信息失败===com.tk.endor.TemplateToPdf.java\n" +
                        GrpEdorNo,
                        "");
        ErrInfo = data[1];
      }

      //数据库连接
      try {
        Connection conn = DBConnPool.getConnection();
        Statement stmt = null;
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = null;
        Stringtools.log("", Sql, "");
        rs = stmt.executeQuery(Sql);
        String RiskCode = "";
        String RiskName = "";
        String PayIntv = "";
        String tmpPayIntv = "";
        String GrpPolNo = "";
        //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
        int i = 7;
        while (rs.next()) {if (i > 22) {
          data[0] = "0";
          data[1] =
              "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
          Stringtools.log("", data[1], "");
          ErrInfo = data[1];
          break;
        }
        RiskCode = rs.getString(1).trim();
        RiskName = rs.getString(2).trim();
        //data[i]=RiskCode+"--"+RiskName;
        data[i] = RiskName;
        GrpPolNo = rs.getString(3);
        PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                                 GrpPolNo + "'");

        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "-1")) {
          tmpPayIntv = "不定期交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "0")) {
          tmpPayIntv = "趸交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "1")) {
          tmpPayIntv = "月交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "3")) {
          tmpPayIntv = "季交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "6")) {
          tmpPayIntv = "半交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "12")) {
          tmpPayIntv = "年交";
        }
        String je = rs.getString(4);
        data[i +
            1] = my.getOneValue("select years from lcpol where grppolno='" +
                                GrpPolNo +
                                "'");
        data[i +
            2] = my.getOneValue("select to_char(FirstPayDate,'yyyy-mm-dd')||' 至 '||to_char(PayEndDate,'yyyy-mm-dd') from lcgrppol where grppolno='" +
                                GrpPolNo +
                                "'");
        data[i + 3] = "￥" + je.trim() + "  元";
        i = i + 4;
        } //循环结束
        rs.close();
        stmt.close();
        conn.close();

        //业务员
        String AgentCode = my.getOneValue(
            "select AgentCode from lCgrpcont  where grpcontno='" + GrpContNo +
            "'");
        if (AgentCode.trim().length() == 0) {
          data[0] = "0";
          data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" +
                    GrpContNo;
          Stringtools.log("", data[1], "");
          ErrInfo = data[1];
          return ErrInfo;
        }
        String AgentName = my.getOneValue(
            "select name from laagent where agentcode='" + AgentCode + "'");
        data[23] = ChinaMeony;
        data[25] = AgentName;
        data[26] = AgentCode;
        data[24] = StringTotalPremium;
        data[27] = my.getOneValue("select username from lduser where usercode=(select operator from ljapay where incomeno='" +
                                  GrpEdorNo + "' and rownum<=1)");
        data[28] = tmpPayIntv;
      } catch (Exception e) {
        Stringtools.log("",
                        "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                        e.getMessage(), "");
        data[0] = "0";
        data[1] = "====组织数据时出错===" + e.getMessage();
        ErrInfo = data[1];
        return ErrInfo;
      }

      if (ErrInfo.trim().length() == 0) {
        if (!myTemplateToPdf.CreatePDF(data, filename, TemplateFilename)) {
          ErrInfo = "打印保全收费发票套模板时出错！出错的方法：TemplateToPdf.CreatePDF";
          return ErrInfo;
        }
      }
      return ErrInfo;
      /**************************重庆发票结束*******************************************************/
      /**************************江苏发票开始*******************************************************/
    case 7: //江苏发票
      logger.debug("模板文件名：" + TemplateFilename);
      Stringtools.log("", "模板文件名" + TemplateFilename, "");
      logger.debug("========分公司码：=============" + comcode);
      File myfile = new File(TemplateFilename);
      if (myfile.exists()) {
        logger.debug("====模板文件已经找到===");
      } else {
        ErrInfo = "模板文件没有找到!!" + TemplateFilename;
        return ErrInfo;
      }
      data = new String[36];
      //开票日期：（当前系统日期）
      data[0] = my.getOneValue(
          "select to_char(sysdate,'yyyy-mm-dd')  from dual");
      //保单号：
      String GrpContno = my.getOneValue(
          "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo +
          "'");
      data[2] = GrpContno;
      //付款人：
      data[1] = my.getOneValue("select Name from LCGrpAppnt where GrpContNo='" +
                               GrpContno + "'");
      //保费交至日期:
      data[3] = "";
      //交费收据号
      PayNo = my.getOneValue(
          "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
          "' and OPERSTATE='0' and rownum<=1");
      //保全批单号：
      data[4] = "保全批单号：" + GrpEdorNo + "   收据序列号：" + PayNo;
      //投保人
      data[5] = ""; //"投保人："+my.getOneValue("select Name from LCGrpAppnt where GrpContNo='"+GrpContno+"'");
      //循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]
      Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b";
      Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" +
            GrpEdorNo + "' and a.OPERSTATE='0'";
      Sql = Sql + "  and  (a.feefinatype='BF' or a.feefinatype='GL')";
      //Sql = Sql + "  and a.GetMoney>0";
      Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";
      Stringtools.log("",
                      " 循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]===\n" + Sql,
                      "");
      //数据库连接
      try {
        Connection conn = DBConnPool.getConnection();
        Statement stmt = null;
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = null;
        Stringtools.log("", Sql, "");
        rs = stmt.executeQuery(Sql);
        String RiskCode = "";
        String RiskName = "";
        String PayIntv = "";
        String GrpPolNo = "";
        //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
        int i = 9;
        data[6] = "险种名称";
        data[7] = "交费方式";
        data[8] = "交费金额";
        while (rs.next()) {
          if (i > 30) {
            data[0] = "0";
            data[1] =
                "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
            Stringtools.log("", data[1], "");
            break;
          }
          RiskCode = rs.getString(1).trim();
          RiskName = rs.getString(2).trim();
         
          
          data[i] = RiskCode + "--" + RiskName;

          GrpPolNo = rs.getString(3);
          PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                                   GrpPolNo + "'");

          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "-1")) {
            data[i + 1] = "不定期缴";

          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "0")) {
            data[i + 1] = "趸缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "1")) {
            data[i + 1] = "月缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "3")) {
            data[i + 1] = "季缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "6")) {
            data[i + 1] = "半年缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "12")) {
            data[i + 1] = "年缴";
          }
          String je = rs.getString(4);
          data[i + 2] = "￥" + Stringtools.stringtostring(je.trim(),2) + "  元";
          i = i + 3;
        } //循环结束
        rs.close();
        stmt.close();
        conn.close();
        //总保费
        StringTotalPremium = my.getOneValue(
            "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
            GrpEdorNo + "' and (feefinatype='BF' or feefinatype='GL') and OPERSTATE='0'");
        DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                             stringtodouble(StringTotalPremium);
        //大写保费
        ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
            DoubleTotalPremium);

 
        //金额大写
        data[30] = ChinaMeony;
        //金额小写
        data[31] = "￥" + Stringtools.stringtostring(StringTotalPremium, 2) + "  元";
        
        //wood根据施燕燕要求添加业务员     
//      业务员
        String AgentCode = my.getOneValue(
            "select AgentCode from lCgrpcont  where grpcontno='" + GrpContno +
            "'");
        if (AgentCode.trim().length() == 0) {
          data[0] = "0";
          data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" +
          GrpContno;
          Stringtools.log("", data[1], "");
          ErrInfo = data[1];
          return ErrInfo;
        }
        String AgentName = my.getOneValue(
            "select name from laagent where agentcode='" + AgentCode + "'");
        //经办人   保全核保人
        Sql = "select trim(UserName) from lduser";
        Sql = Sql +
              " where trim(UserCode)= (select trim(Operator) from LjaGetEndorse";
        Sql = Sql + " where endorsementno='" + GrpEdorNo + "' and rownum<=1 and OPERSTATE='0')";
//        data[32] = my.getOneValue("select operator from ljapay where OtherNo='" +
//                                  GrpEdorNo + "'");
        data[32] = AgentCode ;
        data[33] = AgentName;
        //复核
        data[34] = my.getOneValue(Sql);
        //电话
        data[35] = "95522"+ "                                               " + my.getOneValue("select Address from ldcom where comcode=(select managecom from lcgrpcont where grpcontno='" +
                GrpContno + "' and rownum<=1)");
        //地址
        
      } catch (Exception e) {
        Stringtools.log("",
                        "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                        e.getMessage(), "");
        data[0] = "0";
        data[1] = "====组织数据时出错===" + e.getMessage();
        ErrInfo = data[1];
        return ErrInfo;
      }
      if (ErrInfo.trim().length() == 0) {
        if (!myTemplateToPdf.CreatePDF(data, filename, TemplateFilename)) {
          ErrInfo = "打印保全收费发票套模板时出错！出错的方法：TemplateToPdf.CreatePDF";
          return ErrInfo;
        }
      }

      return ErrInfo;
      /**************************江苏发票结束******************************************************/
   
      
      //***************************山东发票**********************************/
    case 10:

        //*************************正常的发票*****************************/
        //TemplateFilename=RealPath+"pdftemplate\\EdorPaybill.pdf";
        logger.debug("模板文件名：" + TemplateFilename);
        Stringtools.log("", "模板文件名" + TemplateFilename, "");
        logger.debug("========山东分公司码：=============" + comcode);
        myfile = new File(TemplateFilename);
        if (myfile.exists()) {
          logger.debug("====模板文件已经找到===");
        } else {
          ErrInfo = "模板文件没有找到!!" + TemplateFilename;
          return ErrInfo;
        }

        data = new String[32];
        //保单号：
        my = new ExeSQL();
        num = my.getOneValue(
                "select count(*) from LJApaygrp  where endorsementno='" + GrpEdorNo +
                "' and OPERSTATE='0'");
        if (com.sinosoft.lis.claimgrp.Stringtools.stringtoint(num) < 1) {
          data[0] = "0";
          data[1] = "批单号：" + GrpEdorNo.trim() + "没有交费数据!!";
          Stringtools.log("",
                          "====没有交费数据===com.tk.endor.TemplateToPdf.java\n" +
                          data[1], "");
          ErrInfo = data[1];
          return ErrInfo;
        }
        GrpContNo = my.getOneValue(
            "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo +
            "'");
        //投保单位
        GrpName = my.getOneValue(
            "select GrpName from LCGrpCont where GrpContNo='" + GrpContNo + "'");
        //总保费
        StringTotalPremium = my.getOneValue(
            "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
            GrpEdorNo + "' and (feefinatype='BF' or feefinatype='GL') and OPERSTATE='0'");
        DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                             stringtodouble(StringTotalPremium);
        //大写保费
        ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
            DoubleTotalPremium);
        data[0] = GrpName;
        data[1] = ChinaMeony;
        data[2] = "￥" + StringTotalPremium.trim() + "  元";
        data[3] = GrpContNo;
        if (data[0].trim().length() == 0 || data[1].trim().length() == 0 ||
            data[2].trim().length() == 0 || data[3].trim().length() == 0) {
          data[0] = "0";
          data[1] = "批单号：" + GrpEdorNo.trim() + "取投保人信息失败!!";
          Stringtools.log("",
                          "====取投保人信息失败===com.tk.endor.TemplateToPdf.java\n" +
                          GrpEdorNo,
                          "");
          ErrInfo = data[1];
          return ErrInfo;
        }

        //循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]
        Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b";
        Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" +
              GrpEdorNo + "' and a.OPERSTATE='0' ";
        Sql = Sql + "  and  (a.feefinatype='BF' or a.feefinatype='GL')";
        //Sql = Sql + "  and a.GetMoney>0";
        Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";
        Stringtools.log("",
                        " 循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]===\n" + Sql,
                        "");
        //数据库连接
        try {
          Connection conn = DBConnPool.getConnection();
          Statement stmt = null;
          stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                      ResultSet.CONCUR_UPDATABLE);
          ResultSet rs = null;
          Stringtools.log("", Sql, "");
          rs = stmt.executeQuery(Sql);
          String RiskCode = "";
          String RiskName = "";
          String PayIntv = "";
          String GrpPolNo = "";
          //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
          int i = 4;
          while (rs.next()) {if (i > 24) {
            data[0] = "0";
            data[1] =
                "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
            Stringtools.log("", data[1], "");
            break;
          }
          RiskCode = rs.getString(1).trim();
          RiskName = rs.getString(2).trim();
          data[i] = RiskCode + "--" + RiskName;

          GrpPolNo = rs.getString(3);
          PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                                   GrpPolNo + "'");

          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "-1")) {
            data[i + 1] = "不定期交";

          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "0")) {
            data[i + 1] = "趸交";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "1")) {
            data[i + 1] = "月交";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "3")) {
            data[i + 1] = "季交";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "6")) {
            data[i + 1] = "半交";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "12")) {
            data[i + 1] = "年交";
          }
          String je = rs.getString(4);
          data[i + 2] = "￥" + je.trim() + "  元";
          i = i + 3;
          } //循环结束
          rs.close();
          stmt.close();
          conn.close();

          //交费收据
          String PayNodefault = my.getOneValue(
              "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
              "' and OPERSTATE='0' and rownum<=1");
          data[25] = PayNodefault;

          //经办人   保全核保人
          Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
          Sql = Sql +
                " where trim(UserCode)= (select trim(Operator) from LjaGetEndorse";
          Sql = Sql + " where endorsementno='" + GrpEdorNo + "' and rownum<=1 and OPERSTATE='0')";
          data[26] = my.getOneValue(Sql);
          //业务员
          //data[27]=my.getOneValue("select trim(AgentCode)||'--'||trim(Name) from LAAgent where AgentCode in (select AgentCode from LJAPay where InComeType='10' and InComeNo='"+GrpEdorNo+"')");

          String AgentCode = my.getOneValue("select trim(a.AgentCode)||'--'||trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='" +
                                            GrpContNo + "'");
          if (AgentCode.trim().length() == 0) {
            data[0] = "0";
            data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" +
                      GrpContNo;
            Stringtools.log("", data[1], "");
            ErrInfo = data[1];
            return ErrInfo;
          }
          data[27] = AgentCode;

          //年月日
          DateFormat df1 = DateFormat.getDateInstance();
          String s1 = df1.format(new Date());
          data[28] = s1.substring(0, s1.indexOf("-"));
          s1 = s1.substring(s1.indexOf("-") + 1, s1.length());
          data[29] = s1.substring(0, s1.indexOf("-"));
          data[30] = s1.substring(s1.indexOf("-") + 1, s1.length());
          Stringtools.log("",
                          "data[25--30]" + data[25] + "==" + data[26] + "==" +
                          data[27] + "==" + data[28] + "==" + data[29] + "==" +
                          data[30], "");
          data[31]=my.getOneValue("select '泰康人寿保险股份有限公司 '||name from ldcom where comcode in (select managecom from lpgrpedoritem where edorno='"+GrpEdorNo+"')");
          data[31]= StrTool.replace( data[31],"本部","");

          //
        } catch (Exception e) {
          Stringtools.log("",
                          "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                          e.getMessage(), "");
          data[0] = "0";
          data[1] = "====组织数据时出错===" + e.getMessage();
          ErrInfo = data[1];
          return ErrInfo;
        }
        if (ErrInfo.trim().length() == 0) {
          if (!myTemplateToPdf.CreatePDF(data, filename, TemplateFilename)) {
            ErrInfo = "打印保全收费发票套模板时出错！出错的方法：TemplateToPdf.CreatePDF";
            return ErrInfo;
          }
        }

        return ErrInfo;
    //***************************山东发票结束*******************************/
        /**************************青岛发票开始*******************************************************/
    case 19: //青岛发票
      logger.debug("模板文件名：" + TemplateFilename);
      Stringtools.log("", "模板文件名" + TemplateFilename, "");
      logger.debug("========分公司码：=============" + comcode);
      myfile = new File(TemplateFilename);
      if (myfile.exists()) {
        logger.debug("====模板文件已经找到===");
      } else {
        ErrInfo = "模板文件没有找到!!" + TemplateFilename;
        return ErrInfo;
      }
      data = new String[36];
      //开票日期：（当前系统日期）
      data[29] = my.getOneValue(
          "select to_char(sysdate,'yyyy-mm-dd')  from dual");
      data[0] = "泰康人寿保险股份有限公司青岛分公司";
      //保单号：
      GrpContno = my.getOneValue(
          "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo +
          "'");
      data[2] = GrpContno;
      //付款人：
      data[1] = my.getOneValue("select Name from LCGrpAppnt where GrpContNo='" +
                               GrpContno + "'");
      //保费交至日期:
      //data[3] = "";
      //交费收据号
      PayNo = my.getOneValue(
          "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
          "' and OPERSTATE='0' and rownum<=1");
      //保全批单号：
      data[3] = "保全批单号：" + GrpEdorNo + "   收据序列号：" + PayNo;
      //投保人
      //data[4] = ""; //"投保人："+my.getOneValue("select Name from LCGrpAppnt where GrpContNo='"+GrpContno+"'");
      //循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]
      Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b";
      Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" +
            GrpEdorNo + "' and a.OPERSTATE='0'";
      Sql = Sql + "  and  (a.feefinatype='BF' or a.feefinatype='GL')";
      Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";
      Stringtools.log("",
                      " 循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]===\n" + Sql,
                      "");
      //数据库连接
      try {
        Connection conn = DBConnPool.getConnection();
        Statement stmt = null;
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = null;
        Stringtools.log("", Sql, "");
        rs = stmt.executeQuery(Sql);
        String RiskCode = "";
        String RiskName = "";
        String PayIntv = "";
        String GrpPolNo = "";
        //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
        int i = 7;
        data[4] = "险种名称";
        data[5] = "交费方式";
        data[6] = "交费金额";
        while (rs.next()) {
          if (i > 24) {
            data[0] = "0";
            data[1] =
                "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
            Stringtools.log("", data[1], "");
            break;
          }
          RiskCode = rs.getString(1).trim();
          RiskName = rs.getString(2).trim();
         
          
          data[i] = RiskCode + "--" + RiskName;

          GrpPolNo = rs.getString(3);
          PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                                   GrpPolNo + "'");

          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "-1")) {
            data[i + 1] = "不定期缴";

          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "0")) {
            data[i + 1] = "趸缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "1")) {
            data[i + 1] = "月缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "3")) {
            data[i + 1] = "季缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "6")) {
            data[i + 1] = "半年缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "12")) {
            data[i + 1] = "年缴";
          }
          String je = rs.getString(4);
          data[i + 2] = "￥" + Stringtools.stringtostring(je.trim(),2) + "  元";
          i = i + 3;
        } //循环结束
        rs.close();
        stmt.close();
        conn.close();
        //总保费
        StringTotalPremium = my.getOneValue(
            "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
            GrpEdorNo + "' and (feefinatype='BF' or feefinatype='GL') and OPERSTATE='0'");
        DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                             stringtodouble(StringTotalPremium);
        //大写保费
        ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
            DoubleTotalPremium);

 
        //金额大写
        data[25] = ChinaMeony;
        //金额小写
        data[26] = "￥" + Stringtools.stringtostring(StringTotalPremium, 2) + "  元";
        
        //青岛不需要打印业务员     
//      业务员
        String AgentCode = my.getOneValue(
            "select AgentCode from lCgrpcont  where grpcontno='" + GrpContno +
            "'");
        if (AgentCode.trim().length() == 0) {
          data[0] = "0";
          data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" +
          GrpContno;
          Stringtools.log("", data[1], "");
          ErrInfo = data[1];
          return ErrInfo;
        }
        String AgentName = my.getOneValue(
            "select name from laagent where agentcode='" + AgentCode + "'");
        //经办人   保全核保人
        Sql = "select trim(UserName) from lduser";
        Sql = Sql +
              " where trim(UserCode)= (select trim(Operator) from LjaGetEndorse";
        Sql = Sql + " where endorsementno='" + GrpEdorNo + "' and rownum<=1 and OPERSTATE='0')";
       
        String operator = my.getOneValue("select operator from ljapay where OtherNo='" +
                GrpEdorNo + "'");
        data[27] = my.getOneValue("select username  from lduser where usercode='" +
        		operator + "'");
        //data[32] = AgentCode ;
       // data[33] = AgentName;
        //复核
        //data[28] = my.getOneValue(Sql);
        
        
      } catch (Exception e) {
        Stringtools.log("",
                        "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                        e.getMessage(), "");
        data[0] = "0";
        data[1] = "====组织数据时出错===" + e.getMessage();
        ErrInfo = data[1];
        return ErrInfo;
      }
      if (ErrInfo.trim().length() == 0) {
        if (!myTemplateToPdf.CreatePDF(data, filename, TemplateFilename)) {
          ErrInfo = "打印保全收费发票套模板时出错！出错的方法：TemplateToPdf.CreatePDF";
          return ErrInfo;
        }
      }

      return ErrInfo;
      /**************************青岛发票结束******************************************************/
      
      /**************************贵州发票开始*******************************************************/
    case 31: //贵州发票
      logger.debug("模板文件名：" + TemplateFilename);
      Stringtools.log("", "模板文件名" + TemplateFilename, "");
      logger.debug("========分公司码：=============" + comcode);
      myfile = new File(TemplateFilename);
      if (myfile.exists()) {
        logger.debug("====模板文件已经找到===");
      } else {
        ErrInfo = "模板文件没有找到!!" + TemplateFilename;
        return ErrInfo;
      }
      data = new String[36];
      //开票日期：（当前系统日期）
      data[29] = my.getOneValue(
          "select to_char(sysdate,'yyyy-mm-dd')  from dual");
      data[0] = "泰康人寿保险股份有限公司贵州分公司";
      //保单号：
      GrpContno = my.getOneValue(
          "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo +
          "'");
      data[2] = GrpContno;
      //付款人：
      data[1] = my.getOneValue("select Name from LCGrpAppnt where GrpContNo='" +
                               GrpContno + "'");
      //保费交至日期:
      //data[3] = "";
      //交费收据号
      PayNo = my.getOneValue(
          "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
          "' and OPERSTATE='0' and rownum<=1");
      //保全批单号：
      data[3] = "保全批单号：" + GrpEdorNo + "   收据序列号：" + PayNo;
      //投保人
      //data[4] = ""; //"投保人："+my.getOneValue("select Name from LCGrpAppnt where GrpContNo='"+GrpContno+"'");
      //循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]
      Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b";
      Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" +
            GrpEdorNo + "' and a.OPERSTATE='0'";
      Sql = Sql + "  and  (a.feefinatype='BF' or a.feefinatype='GL')";
      Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";
      Stringtools.log("",
                      " 循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]===\n" + Sql,
                      "");
      //数据库连接
      try {
        Connection conn = DBConnPool.getConnection();
        Statement stmt = null;
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = null;
        Stringtools.log("", Sql, "");
        rs = stmt.executeQuery(Sql);
        String RiskCode = "";
        String RiskName = "";
        String PayIntv = "";
        String GrpPolNo = "";
        //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
        int i = 7;
        data[4] = "";
        data[5] = "";
        data[6] = "";
        while (rs.next()) {
          if (i > 24) {
            data[0] = "0";
            data[1] =
                "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
            Stringtools.log("", data[1], "");
            break;
          }
          RiskCode = rs.getString(1).trim();
          RiskName = rs.getString(2).trim();
         
          
          data[i] =  RiskName;

          GrpPolNo = rs.getString(3);
          PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                                   GrpPolNo + "'");

          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "-1")) {
            data[i + 1] = "不定期缴";

          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "0")) {
            data[i + 1] = "趸缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "1")) {
            data[i + 1] = "月缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "3")) {
            data[i + 1] = "季缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "6")) {
            data[i + 1] = "半年缴";
          }
          if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
              "12")) {
            data[i + 1] = "年缴";
          }
          String je = rs.getString(4);
          data[i + 2] = "￥" + Stringtools.stringtostring(je.trim(),2) + "  元";
          i = i + 3;
        } //循环结束
        rs.close();
        stmt.close();
        conn.close();
        //总保费
        StringTotalPremium = my.getOneValue(
            "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
            GrpEdorNo + "' and (feefinatype='BF' or feefinatype='GL') and OPERSTATE='0'");
        DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                             stringtodouble(StringTotalPremium);
        //大写保费
        ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
            DoubleTotalPremium);

 
        //金额大写
        data[25] = ChinaMeony;
        //金额小写
        data[26] = "￥" + Stringtools.stringtostring(StringTotalPremium, 2) + "  元";
        
        //青岛不需要打印业务员     
//      业务员
        String AgentCode = my.getOneValue(
            "select AgentCode from lCgrpcont  where grpcontno='" + GrpContno +
            "'");
        if (AgentCode.trim().length() == 0) {
          data[0] = "0";
          data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" +
          GrpContno;
          Stringtools.log("", data[1], "");
          ErrInfo = data[1];
          return ErrInfo;
        }
        String AgentName = my.getOneValue(
            "select name from laagent where agentcode='" + AgentCode + "'");
        //经办人   保全核保人
        Sql = "select trim(UserName) from lduser";
        Sql = Sql +
              " where trim(UserCode)= (select trim(Operator) from LjaGetEndorse";
        Sql = Sql + " where endorsementno='" + GrpEdorNo + "' and rownum<=1 and OPERSTATE='0')";
       
        String operator = my.getOneValue("select operator from ljapay where OtherNo='" +
                GrpEdorNo + "'");
        data[27] = my.getOneValue("select username  from lduser where usercode='" +
        		operator + "'");
        //data[28] = AgentCode + "       " + AgentName;
       // data[33] = AgentName;
        //复核
        //data[28] = my.getOneValue(Sql);
        
        
      } catch (Exception e) {
        Stringtools.log("",
                        "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                        e.getMessage(), "");
        data[0] = "0";
        data[1] = "====组织数据时出错===" + e.getMessage();
        ErrInfo = data[1];
        return ErrInfo;
      }
      if (ErrInfo.trim().length() == 0) {
        if (!myTemplateToPdf.CreatePDF(data, filename, TemplateFilename)) {
          ErrInfo = "打印保全收费发票套模板时出错！出错的方法：TemplateToPdf.CreatePDF";
          return ErrInfo;
        }
      }

      return ErrInfo;
      /**************************青岛发票结束******************************************************/
      
      
      /**************************默认值**************************************************/
    default:

      //*************************正常的发票*****************************/
      //TemplateFilename=RealPath+"pdftemplate\\EdorPaybill.pdf";
      logger.debug("模板文件名：" + TemplateFilename);
      Stringtools.log("", "模板文件名" + TemplateFilename, "");
      logger.debug("========分公司码：=============" + comcode);
      myfile = new File(TemplateFilename);
      if (myfile.exists()) {
        logger.debug("====模板文件已经找到===");
      } else {
        ErrInfo = "模板文件没有找到!!" + TemplateFilename;
        return ErrInfo;
      }

      data = new String[40];
      //保单号：
      my = new ExeSQL();
      num = my.getOneValue(
    	        "select count(*) from LJApaygrp  where EndorsementNo='" + GrpEdorNo +
    	        "' ");
      if (com.sinosoft.lis.claimgrp.Stringtools.stringtoint(num) < 1) {
        data[0] = "0";
        data[1] = "批单号：" + GrpEdorNo.trim() + "没有交费数据!!";
        Stringtools.log("",
                        "====没有交费数据===com.tk.endor.TemplateToPdf.java\n" +
                        data[1], "");
        ErrInfo = data[1];
        return ErrInfo;
      }
      GrpContNo = my.getOneValue(
          "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo +
          "'");
      //投保单位
      GrpName = my.getOneValue(
          "select GrpName from LCGrpCont where GrpContNo='" + GrpContNo + "'");
      //总保费
      StringTotalPremium = my.getOneValue(
          "select sum(GetMoney) from LJAGetEndorse where EndorsementNo='" +
          GrpEdorNo + "' and (feefinatype='BF' or feefinatype='GL') and OPERSTATE='0'");
      DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                           stringtodouble(StringTotalPremium);
      //大写保费
      ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
          DoubleTotalPremium);
      data[0] = GrpName;
      data[1] = ChinaMeony;
      data[2] = "￥" + StringTotalPremium.trim() + "  元";
      data[3] = GrpContNo;
      if (data[0].trim().length() == 0 || data[1].trim().length() == 0 ||
          data[2].trim().length() == 0 || data[3].trim().length() == 0) {
        data[0] = "0";
        data[1] = "批单号：" + GrpEdorNo.trim() + "取投保人信息失败!!";
        Stringtools.log("",
                        "====取投保人信息失败===com.tk.endor.TemplateToPdf.java\n" +
                        GrpEdorNo,
                        "");
        ErrInfo = data[1];
        return ErrInfo;
      }

      //循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]
      Sql = "select a.RiskCode,b.RiskName,a.GrpPolNo,sum(a.GetMoney) from LJAGetEndorse a,LMRiskApp b";
      Sql = Sql + " where a.RiskCode=b.RiskCode  and EndorsementNo='" +
            GrpEdorNo + "' and a.OPERSTATE='0'";
      Sql = Sql + "  and (a.feefinatype='BF' or a.feefinatype='GL')";
      Sql = Sql + " group by a.RiskCode,b.RiskName,a.GrpPolNo";
      Stringtools.log("",
                      " 循环取 险种，交费方式，保费  数组下标范围data[4]---data[24]===\n" + Sql,
                      "");
      //数据库连接
      try {
        Connection conn = DBConnPool.getConnection();
        Statement stmt = null;
        stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                    ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = null;
        Stringtools.log("", Sql, "");
        rs = stmt.executeQuery(Sql);
        String RiskCode = "";
        String RiskName = "";
        String PayIntv = "";
        String GrpPolNo = "";
        //-1 -- 不定期交,   0  -- 趸交,    1  -- 月交     3  -- 季交      6  -- 半年交    12 -- 年交
        int i = 4;
        while (rs.next()) {if (i > 24) {
          data[0] = "0";
          data[1] =
              "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
          Stringtools.log("", data[1], "");
          break;
        }
        RiskCode = rs.getString(1).trim();
        RiskName = rs.getString(2).trim();
        data[i] = RiskCode + "--" + RiskName;

        GrpPolNo = rs.getString(3);
        PayIntv = my.getOneValue("select PayIntv from LCpol where GrpPolNo='" +
                                 GrpPolNo + "'");

        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "-1")) {
          data[i + 1] = "不定期交";

        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "0")) {
          data[i + 1] = "趸交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "1")) {
          data[i + 1] = "月交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "3")) {
          data[i + 1] = "季交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "6")) {
          data[i + 1] = "半交";
        }
        if (com.sinosoft.lis.claimgrp.Stringtools.stringequals(PayIntv,
            "12")) {
          data[i + 1] = "年交";
        }
        String je = rs.getString(4);
        data[i + 2] = "￥" + je.trim() + "  元";
        i = i + 3;
        } //循环结束
        rs.close();
        stmt.close();
        conn.close();

        //交费收据
        String PayNodefault = my.getOneValue(
            "select PayNo from ljapaygrp  where  endorsementno='" + GrpEdorNo +
            "' and OPERSTATE='0' and rownum<=1 and OPERSTATE='0'");
        data[25] = PayNodefault;

        //经办人   保全核保人
        Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
        Sql = Sql +
              " where trim(UserCode)= (select trim(Operator) from LjaGetEndorse";
        Sql = Sql + " where endorsementno='" + GrpEdorNo + "' and rownum<=1 and OPERSTATE='0')";
        data[26] = my.getOneValue(Sql);
        //业务员
        //data[27]=my.getOneValue("select trim(AgentCode)||'--'||trim(Name) from LAAgent where AgentCode in (select AgentCode from LJAPay where InComeType='10' and InComeNo='"+GrpEdorNo+"')");

        String AgentCode = my.getOneValue("select trim(a.AgentCode)||'--'||trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='" +
                                          GrpContNo + "'");
        if (AgentCode.trim().length() == 0) {
          data[0] = "0";
          data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" +
                    GrpContNo;
          Stringtools.log("", data[1], "");
          ErrInfo = data[1];
          return ErrInfo;
        }
        data[27] = AgentCode;

        //年月日
        DateFormat df1 = DateFormat.getDateInstance();
        String s1 = df1.format(new Date());
        data[28] = s1.substring(0, s1.indexOf("-"));
        s1 = s1.substring(s1.indexOf("-") + 1, s1.length());
        data[29] = s1.substring(0, s1.indexOf("-"));
        data[30] = s1.substring(s1.indexOf("-") + 1, s1.length());
        Stringtools.log("",
                        "data[25--30]" + data[25] + "==" + data[26] + "==" +
                        data[27] + "==" + data[28] + "==" + data[29] + "==" +
                        data[30], "");
        //对于甘肃的发票
        //paytodate
        data[31]=my.getOneValue("select to_char(curpaytodate,'yyyy-mm-dd') from ljapaygrp where payno='"+data[25]+"' and OPERSTATE='0'");
        //地址、电话
        String Address=my.getOneValue("select BranchAddress from LABranchGroup where agentgroup=(select agentgroup from lcgrpcont    where grpcontno='"+data[3]+"' and rownum<=1)");
        String Phone=my.getOneValue("select BranchPhone from LABranchGroup where agentgroup=(select agentgroup from lcgrpcont    where grpcontno='"+data[3]+"' and rownum<=1)");
        data[32]=Address;
        data[33]=Phone;
      } catch (Exception e) {
        Stringtools.log("",
                        "====组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                        e.getMessage(), "");
        data[0] = "0";
        data[1] = "====组织数据时出错===" + e.getMessage();
        ErrInfo = data[1];
        return ErrInfo;
      }
      if (ErrInfo.trim().length() == 0) {
        if (!myTemplateToPdf.CreatePDF(data, filename, TemplateFilename)) {
          ErrInfo = "打印保全收费发票套模板时出错！出错的方法：TemplateToPdf.CreatePDF";
          return ErrInfo;
        }
      }

      return ErrInfo;

      /*******************正常的发票结束*****************************/
    }

  }


  //                                  字符串数组   生成的文件名       模板文件名
  public boolean CreatePDF(String[] str, String filename,
                           String TemplateFilename) {
    boolean yn = true;
    try {
      PdfReader reader = new PdfReader(TemplateFilename);
      PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(filename));
      PdfContentByte under = stamp.getUnderContent(1);
      
      BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                                        BaseFont.NOT_EMBEDDED);
      Font FontChinese = new Font(bf, 12, Font.NORMAL);
      AcroFields form = stamp.getAcroFields();
      Stringtools.log("", "==数组长度===" + str.length, "");
      for (int i = 0; i <= str.length - 1; i++) {
        if (str[i] == null) {
          str[i] = "";
          Stringtools.log("", "str[" + i + "] is null", "");
        }
        //Stringtools.log("","Text"+(i+1),"");
        form.setField("Text" + (i + 1), str[i]);

      }
      form.setField("a", "xianzhong");
      stamp.setFormFlattening(true);
      stamp.close();
    } catch (DocumentException de) {
      yn = false;
      Stringtools.log("", "DocumentException:" + de.getMessage(), "");
    } catch (IOException ioe) {
      yn = false;
      Stringtools.log("", "IO ERROR:" + ioe.getMessage(), "");
    }
    Stringtools.log("", "====yn===" + yn, "");
    return yn;
  }


  //保全收费通知书(无模板)     保全申请号

  public String CreateLjsPay(String OtherNo, String filename) {
    //state如果是0表示生成文件失败，从第二个字符开始叙述失败的可控原因（比如：“0取投保人信息失败”）；如果是1表示生成文件成功！
    String state = "1";

    Document document = new Document(PageSize.B5);
    try {
      BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                                        BaseFont.NOT_EMBEDDED);
      Font FontChinese = new Font(bf, 12, Font.NORMAL);

      Font Font10 = new Font(bf, 10, Font.NORMAL);
      //生成的PDF文件文件名
      PdfWriter writer = PdfWriter.getInstance(document,
                                               new FileOutputStream(filename));
      //页边距的定义
      //页边距的定义
      //Paragraph paragraph = new Paragraph();
      //paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      /************与mm的换算：1 mm = 2.8346 pt***** 72pt=1 英寸******************/
      /******************  左  右  上  下  *************************/
      document.setMargins(36, 36, 36, 36);
      document.open();
      document.add(new Paragraph(
          "                                                                保全交费通知单",
          FontChinese));
      document.add(new Paragraph(" ", FontChinese));
      document.add(new Paragraph("保全申请号：" + OtherNo, FontChinese));

      ExeSQL my = new ExeSQL();

      //取保单号
      String GrpContNo = my.getOneValue(
          "select OtherNo from LPEdorApp where OtherNoType='4' and EdorAcceptNo='" +
          OtherNo + "'");
      //取投保人
      String GrpName = my.getOneValue(
          "select Name from LCGrpAppnt where GrpContNo='" + GrpContNo + "'");
      document.add(new Paragraph("投 保 人：" + GrpName, FontChinese));
      document.add(new Paragraph("保险单号：" + GrpContNo, FontChinese));
      if (GrpContNo.length() == 0 || GrpName.length() == 0 ||
          OtherNo.length() == 0) {
        Stringtools.log("", "==保全收费通知书，查询无可打印数据(保单号、投何人、申请号中有为空的数据)!", "");
        state = "0保全申请号 " + OtherNo + " 查无可打印的收费通知数据!(保单号、投何人、申请号中有为空的数据)";
        return state;
      }
      document.add(new Paragraph(" ", FontChinese));
      document.add(new Paragraph(" ", FontChinese));
      document.add(new Paragraph(" ", FontChinese));

      String SumGetMoney = "";
      String SumLX = "";
      String SumGL = "";
      String SumZD = "";
      double total = 0.00;

      //**20060906前老苏写的
       //数据库连接
       try {
         String Sql = "select a.polno,a.RiskCode,b.RiskName,sum(a.GetMoney) from LJSGetEndorse  a,LMRiskApp b";
         Sql = Sql + " where a.GetFlag='0'  and  a.EndorsementNo='" + OtherNo +
               "'  and a.RiskCode=b.RiskCode  and FeeFinaType in ('BF','GL','ZD') ";
         Sql = Sql + " group by a.polno,a.RiskCode,b.RiskName order by a.polno";
         SSRS tSSRS = my.execSQL(Sql);
         int row = tSSRS.getMaxRow();
         if (row >= 1) {
           int col = tSSRS.getMaxCol();
           Stringtools.log("", "======Sql====" + Sql, "");
           //开始写表头
           float[] widths = {20f, 15f, 50f, 15f};
           PdfPTable table = new PdfPTable(widths);
           PdfPCell cell = new PdfPCell();
           cell = new PdfPCell(new Phrase("客户号", Font10));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(cell);
           cell = new PdfPCell(new Phrase("姓名", Font10));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(cell);
           cell = new PdfPCell(new Phrase("险种", Font10));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(cell);
           cell = new PdfPCell(new Phrase("交费金额", Font10));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(cell);

           //定义变量
           String insuredno = "";
           String PolNo = "";
           String RiskCode = "";
           String RiskName = "";
           String insuredname = "";
           String getMoney = "";
           //double jfje = 0.00;

           for (int i = 1; i <= row; i++) {
             PolNo = tSSRS.GetText(i, 1);
             insuredno = my.getOneValue("select insuredno from lcpol where polno='" + PolNo + "'");
             insuredname = my.getOneValue(
                 "select name from lcinsured where Grpcontno='" +
                 GrpContNo + "' and insuredno='" + insuredno +
                 "'");
             RiskCode = tSSRS.GetText(i, 2).trim();
             RiskName = tSSRS.GetText(i, 3).trim();
             getMoney = tSSRS.GetText(i, 4).trim();
             //jfje = Stringtools.stringtodouble(tSSRS.GetText(i, 4));
             //total = total + jfje;
             cell = new PdfPCell(new Phrase(insuredno, Font10));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(cell);
             cell = new PdfPCell(new Phrase(insuredname, Font10));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(cell);

             cell = new PdfPCell(new Phrase(RiskCode + " -- " +
                                            RiskName, Font10));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell);

             cell = new PdfPCell(new Phrase(Stringtools.stringtostring(getMoney,
                 2), Font10));
             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             table.addCell(cell);

             //table.addCell(new Phrase(insuredno, FontChinese));
             //table.addCell(new Phrase(insuredname, FontChinese));
             //table.addCell(new Phrase(RiskCode + " -- " +
             //                        RiskName, FontChinese));
             //table.addCell(new Phrase(Stringtools.stringtostring(getMoney, 2),
             //                        FontChinese));

           }
           table.setWidthPercentage(100);
           document.add(table);
         } else {

           Sql =
               "select FeeOperationType,sum(GetMoney) from LJSGetEndorse  ";
           Sql = Sql + " where GetFlag='0'  and  EndorsementNo='" + OtherNo +
                 "'";
           Sql = Sql + " group by FeeOperationType";
           tSSRS = my.execSQL(Sql);
           row = tSSRS.getMaxRow();
           //开始写表头
           float[] widths = {20f, 10f};
           PdfPTable table = new PdfPTable(widths);
           PdfPCell cell = new PdfPCell();
           cell = new PdfPCell(new Phrase("保全项目", Font10));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(cell);
           cell = new PdfPCell(new Phrase("交费金额", Font10));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           table.addCell(cell);

           //定义变量
           String FeeOperationType = "";
           String EdorName = "";

           String getMoney = "";
           String edorItem = "";

           for (int i = 1; i <= row; i++) {
             FeeOperationType = tSSRS.GetText(i, 1).trim();
             getMoney = tSSRS.GetText(i, 2);
             getMoney = Stringtools.stringtostring(getMoney, 2);
             EdorName = my.getOneValue(
                 "select EdorName from lmedorItem  where EdorCode='" +
                 FeeOperationType + "' and AppObj='G'").trim();
             edorItem = FeeOperationType + "--" + EdorName;
             cell = new PdfPCell(new Phrase(edorItem, Font10));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(cell);
             cell = new PdfPCell(new Phrase(Stringtools.stringtostring(getMoney,
                 2), Font10));
             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             table.addCell(cell);
           }
           table.setWidthPercentage(100);
           document.add(table);
         }
         /**20060906修改结束*/

         SumGetMoney = my.getOneValue(
             "select sum(GetMoney) from LJSGetEndorse  "
             + " where GetFlag='0'  and  EndorsementNo='" + OtherNo +
             "' and FeeFinaType='BF'");
         SumGL = my.getOneValue(
             "select sum(GetMoney) from LJSGetEndorse  "
             + " where GetFlag='0'  and  EndorsementNo='" + OtherNo +
             "' and FeeFinaType='GL'");
          SumZD = my.getOneValue(
            "select sum(GetMoney) from LJSGetEndorse  "
            + " where GetFlag='0'  and  EndorsementNo='" + OtherNo +
            "' and FeeFinaType='ZD'");

         if (SumGL.trim().length() == 0) {
           SumGL = "0.00";
         }

         SumLX = my.getOneValue(
             "select sum(GetMoney) from LJSGetEndorse  "
             + " where GetFlag='0'  and  EndorsementNo='" + OtherNo +
             "' and FeeFinaType='LX'");

         if (SumLX.trim().length() == 0) {
           SumLX = "0.00";
         }
         total = Stringtools.stringtodouble(my.getOneValue(
             "select sum(GetMoney) from LJSGetEndorse  "
             + " where GetFlag='0'  and  EndorsementNo='" + OtherNo +
             "' and FeeFinaType in ('BF','LX','GL','ZD')")
                                            );
         SumGetMoney = Stringtools.stringtostring(SumGetMoney, 2);
         SumLX = Stringtools.stringtostring(SumLX, 2);

         document.add(new Phrase("补交利息：" + Stringtools.stringtostring(SumLX, 2) +
                                 " 元，保险费：" +
                                 Stringtools.stringtostring(SumGetMoney, 2) +
                                 "元，管理费：" + Stringtools.stringtostring(SumGL, 2) +
                                 "元，其它费用："+Stringtools.stringtostring(SumZD, 2) + "元，合计金额：" + Stringtools.stringtostring(
             Stringtools.doubletostring(total), 2) + "元，", FontChinese));

         document.add(new Phrase("人民币大写：" +
                                 com.sinosoft.lis.pubfun.PubFun.
                                 getChnMoney(total) + "。", FontChinese));
         document.add(new Paragraph(" ", FontChinese));
         document.add(new Paragraph(" ", FontChinese));
         document.add(new Paragraph(" ", FontChinese));
         document.add(new Paragraph(" ", FontChinese));
         ExeSQL MY = new ExeSQL();
         //取业务代表
         String AgentCode = my.getOneValue(
             "select AgentCode from LJSGetEndorse where GetFlag='0' and EndorsementNo='" +
             OtherNo + "'");
         String AgentName = my.getOneValue(
             "select Name from LAAgent where AgentCode='" + AgentCode + "'");
         //取经办operator
         String Operator = my.getOneValue(
             "select Operator from LpGrpEdorMain where   EdorNo='" + OtherNo +
             "'");
         String OperatorName = my.getOneValue(
             "select UserName from LDUser where UserCode='" + Operator + "'");
         document.add(new Paragraph("                经办：" + OperatorName +
                                    "                       业务代表：" +
                                    AgentCode.trim() + "--" + AgentName,
                                    FontChinese));
         document.close();

       } catch (Exception e) {
         Stringtools.log("", "打印收费通知书失败" + state, "");
         state = "0SSRS取数据失败";
         return state;
       }

    } catch (DocumentException e) {
      Stringtools.log("", "打印收费通知书失败" + state, "");
      state = "0定义字体出错!" + e.getMessage();
      return state;
    } catch (java.io.IOException e1) {
      state = "0定义字体出错,I/O ERROR!" + e1.getMessage();
      Stringtools.log("", "打印收费通知书失败" + state, "");

      return state;
    }
    return state;
  }


  //****************保全多收费的退费程序   取数据以打印保全退费收据（领款收据）******************//
  public String[] PayBillDataYET(String GrpEdorNo) {
    String[] data = new String[29];
    ExeSQL my = new ExeSQL();
    String num = my.getOneValue(
        "select count(*) from LJAGet  where OtherNoType='8' and OtherNo='" +
        GrpEdorNo + "' and OPERSTATE='0'");
    if (com.sinosoft.lis.claimgrp.Stringtools.stringtoint(num) < 1) {
      Stringtools.log("", "====没有退费数据===com.tk.endor.TemplateToPdf.java\n", "");
      data[0] = "0";
      data[1] = "批单号：" + GrpEdorNo.trim() + "没有退费数据!!";
      return data;
    }

    //取保单号
    String GrpContNo = my.getOneValue(
        "select GrpContNo from LPGrpEdorItem where EdorNo='" + GrpEdorNo + "'");
    //投保单位
    String GrpName = my.getOneValue(
        "select GrpName from LCGrpCont where GrpContNo='" + GrpContNo + "'");
    //取总退费
    String StringTotalPremium = my.getOneValue(
        "select sum(SumGetMoney) from LJAGet where OtherNo='" + GrpEdorNo +
        "' and OPERSTATE='0'");
    //总退费中文
    double DoubleTotalPremium = com.sinosoft.lis.claimgrp.Stringtools.
                                stringtodouble(StringTotalPremium);
    String ChinaMeony = com.sinosoft.lis.pubfun.PubFun.getChnMoney(
        DoubleTotalPremium);
    //收据号
    String ActuGetNo = my.getOneValue(
        "select ActuGetNo from LJAGet  where  OtherNoType='8' and OtherNo='" +
        GrpEdorNo + "' and OPERSTATE='0'");
    data[0] = ActuGetNo;
    data[1] = GrpName;
    data[2] = GrpContNo;
    data[3] = GrpEdorNo;
    if (data[0].trim().length() == 0 || data[1].trim().length() == 0 ||
        data[2].trim().length() == 0 || data[3].trim().length() == 0) {
      Stringtools.log("",
                      "====退费收据==取投保人等信息失败===com.tk.endor.TemplateToPdf.java\n" +
                      GrpEdorNo, "");
      data[0] = "0";
      data[1] = "批单号：" + GrpEdorNo.trim() + "取投保人信息等失败!!";
      return data;
    }
    //循环取 险种，保费  数组下标范围data[4]---data[19]
    String Sql = "select a.RiskCode,b.RiskName,abs(sum(a.GetMoney)) from LJAGetEndorse a,LMRiskApp b";
    Sql = Sql + " where a.RiskCode=b.RiskCode  and a.EndorsementNo='" +
          GrpEdorNo + "' and a.OPERSTATE='0' ";
    Sql = Sql + " and a.GetMoney<0";
    Sql = Sql + " group by a.RiskCode,b.RiskName";
    Stringtools.log("", " 循环取 险种，保费  数组下标范围data[4]---data[19]===\n" + Sql, "");
    //数据库连接
    try {
      Connection conn = DBConnPool.getConnection();
      Statement stmt = null;
      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = null;
      Stringtools.log("", Sql, "");
      rs = stmt.executeQuery(Sql);
      String RiskCode = "";
      String RiskName = "";
      int i = 4;
      while (rs.next()) {

        if (i > 19) {

          data[0] = "0";
          data[1] =
              "====组织数据时出错:有过多保全险种,数组长度不够！com.tk.endor.TemplateToPdf.java";
          Stringtools.log("", data[1], "");
          break;
        }
        RiskCode = rs.getString(1).trim();
        RiskName = rs.getString(2).trim();
        data[i] = RiskCode + "--" + RiskName;
        data[i + 1] = "￥" + rs.getString(3).trim() + "  元";
        i = i + 2;
      }
      rs.close();
      stmt.close();
      conn.close();
      //合计金额小写 大写
      String total = StringTotalPremium; //my.getOneValue("select sum(SumGetMoney) from LJAGet where OtherNoType='10' and OtherNo='"+GrpEdorNo+"'");
      data[20] = PubFun.getChnMoney(Stringtools.stringtodouble(total));
      data[21] = Stringtools.stringtostring(total, 2);
      //业务经办：
      Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
      Sql = Sql +
            " where trim(UserCode)= (select trim(operator) from LPEdorApp ";
      Sql = Sql + " where EdorAcceptNo='" + GrpEdorNo + "')";
      data[22] = my.getOneValue(Sql);
      //保全核保人
      Sql = "select trim(UserCode)||'--'||trim(UserName) from lduser";
      Sql = Sql +
            " where trim(UserCode)= (select trim(UWOperator) from LPGrpEdorMain ";
      Sql = Sql + " where EdorNo='" + GrpEdorNo + "')";
      data[23] = my.getOneValue(Sql);
      //保全确认日期(暂时不要这个日期)
      String TmpDate = ""; //my.getOneValue("select ConfDate from LPGrpEdorMain where EdorNo='"+GrpEdorNo+"'").trim();
      //if (TmpDate.length()>=10)
      //{
      data[24] = ""; //TmpDate.substring(0,4);
      data[25] = ""; //TmpDate.substring(5,7);
      data[26] = ""; //TmpDate.substring(8,10);
      //}
      /*
                      else
                      {
          data[0]="0";
          data[1]="取核保日期出错！"+"批单号："+GrpEdorNo;
          Stringtools.log("",data[1],"");
          return data;
                      }*/
      // 领款类别
      data[27] = "团险保全";
      //业务员
      //String AgentCode=my.getOneValue("select trim(a.agentcode)||'--'||trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='"+GrpContNo+"'");
      String AgentCode = my.getOneValue("select trim(b.Name) Agent from lCgrpcont a,LaAgent b where trim(a.AgentCode)=trim(b.AgentCode) and a.grpcontno='" +
                                        GrpContNo + "'");

      if (AgentCode.trim().length() == 0) {
        data[0] = "0";
        data[1] = "取业务员错误" + "保全号：" + GrpEdorNo + "保单号：(LpGrpCont)" + GrpContNo;
        Stringtools.log("", data[1], "");
        return data;

      }
      data[28] = AgentCode;

    } catch (Exception e) {
      Stringtools.log("",
                      "===退费收据=组织数据时出错===com.tk.endor.TemplateToPdf.java\n" +
                      e.getMessage(), "");
      data[0] = "0";
      data[1] = "====组织数据时出错(打印退费收据)===" + e.getMessage();

    }

    return data;
  }

  public String getPdfRealPath() {
    ExeSQL my = new ExeSQL();
    return my.getOneValue(
        "select sysvarvalue from ldsysvar where sysvar='PdfRealPath' ");

  }

}
