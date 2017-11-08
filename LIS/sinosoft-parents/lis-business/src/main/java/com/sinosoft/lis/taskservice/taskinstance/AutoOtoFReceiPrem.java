package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

/**
 * <p>Title: Lis_New</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 *
 * <p>Company: </p>
 *
 * @author Sinosoft
 * @version 1.0
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
//import com.sinosoft.lis.agentcalculate.*;
import com.sinosoft.utility.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
import com.sinosoft.lis.otof.*;

public class AutoOtoFReceiPrem extends TaskThread {
private static Logger logger = Logger.getLogger(AutoOtoFReceiPrem.class);
  public AutoOtoFReceiPrem() {
  }
  public CErrors mErrors = new CErrors();
private String mDate=null;
private LDComSet mLDComSet = new LDComSet();

public boolean dealMain()
{
  /*业务处理逻辑*/
  logger.debug("开始应收保费的提取---------");
  String tCurrentDate = PubFun.getCurrentDate();
  String bDate = PubFun.calDate(tCurrentDate, -1, "D", tCurrentDate);
  String lbDate = PubFun.calDate(bDate, -1, "M", bDate);
  String accountDate =tCurrentDate;
  String tCalDate[] = new String[2];
  tCalDate = PubFun.calFLDate(bDate);
  String eDate = tCalDate[1];  //提取最后一日
  String lCalDate[] = new String[2];
  lCalDate = PubFun.calFLDate(lbDate);
  String leDate = lCalDate[1];  //冲消最后一日
  String Managecom = "86";
  VData vData = new VData();             //存放本月提取的相关参数
  VData lData = new VData();             //存放上月提取的相关参数
  GlobalInput tG = new GlobalInput();
  tG.Operator = "001";
  tG.ManageCom = "86";
  logger.debug("冲消的截止日期---"+leDate+"---提取的截止日期---"+eDate);
  vData.addElement(tG);
  lData.addElement(tG);
  TransferData tTransferData = new TransferData();
  TransferData lTransferData = new TransferData();
  Integer itemp = new Integer(61);
  tTransferData.setNameAndValue("bDate",bDate);//提取起始日期
  lTransferData.setNameAndValue("bDate",lbDate);//提取起始日期
  tTransferData.setNameAndValue("eDate",eDate);//提取终止日期
  lTransferData.setNameAndValue("eDate",leDate);//提取终止日期
  tTransferData.setNameAndValue("accountDate", accountDate);//记账日期
  lTransferData.setNameAndValue("accountDate", accountDate);//记账日期
  tTransferData.setNameAndValue("itemp", itemp);//凭证类别（应收保费15）
  lTransferData.setNameAndValue("itemp", itemp);//凭证类别（应收保费15）
  tTransferData.setNameAndValue("Managecom", Managecom);
  lTransferData.setNameAndValue("Managecom", Managecom);
  tTransferData.setNameAndValue("Flag", "1");//自动提取标志
  vData.addElement(tTransferData);
  lData.addElement(lTransferData);
  OtoFReverPremBL tOtoFReverPremBL = new OtoFReverPremBL();
  tOtoFReverPremBL.submitData(lData,"Reverse");        //冲消上月应收保费凭证
  logger.debug("结束冲消上月应收保费凭证---------");
  OtoFReceiPremBL tOtoFReceiPremBL = new OtoFReceiPremBL();
  tOtoFReceiPremBL.submitData(vData, "Prem");      //提取本月应收保费凭证
  logger.debug("结束应收保费的提取---------");
  return true;
}
public static void main(String[] args)
{
  AutoOtoFReceiPrem tAutoOtoFReceiPrem = new AutoOtoFReceiPrem();
  tAutoOtoFReceiPrem.dealMain();
}

}
