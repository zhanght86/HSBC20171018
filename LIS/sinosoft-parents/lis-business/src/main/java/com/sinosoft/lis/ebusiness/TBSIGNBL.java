package com.sinosoft.lis.ebusiness;
import org.apache.log4j.Logger;

//import com.sinosoft.lis.bl.*;
//import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
//import com.sinosoft.lis.vbl.*;
//import com.sinosoft.lis.vdb.*;
//import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.utility.*;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author zy
 * @version 1.0
 */

public class TBSIGNBL {
private static Logger logger = Logger.getLogger(TBSIGNBL.class);

  public TBSIGNBL() {
  }

  /** 传入数据的容器 */
  private VData mInputData = new VData();

  /** 传送数据的容器 */
  private VData mOutputData = new VData();
  private GlobalInput mGlobalInput = new GlobalInput();

  /** 返回数据的容器 */
  private VData mResult = new VData();

  /** 数据操作字符串 */
  private String mOperate;

  /** 错误处理类 */
  public CErrors mErrors = new CErrors();

  public Vector submitData(Vector inParam)
  {
    String strAct = (String) inParam.get(0);
    logger.debug("==> Begin to TBSIGNBL1");
    logger.debug("==> Act = "+ strAct);
    if (strAct.equals("TB"))
    {
      //注册时通过用户输入信息，返回客户信息
      if (!exeTB(inParam))
      {
        // @@ 错误处理
        mResult.add("在线投保失败"); //投保信息出错
      }
      return mResult;
    }
    else if (strAct.equals("Check"))
    {
      if (!exeCheck(inParam))
      {
        // @@ 错误处理
        mResult.add("保单份数校验失败"); //签单信息出错
      }
      return mResult;
    }

//    else if (strAct.equals("INPORT"))
//    {
//      if (!exeInport(inParam))
//      {
//        // @@ 错误处理
//        mResult.add("00125"); //导库信息出错
//      }
//      return mResult;
//    }
    return mResult;
  }

  /**
   * 投保
   * @param inParam
   * @return
   */
  private boolean exeTB(Vector inParam)
  {
    String strAct = (String) inParam.get(0);
    logger.debug("==> :"+strAct);
    mResult.clear();
    mResult.add("SUCCESS");
    VData tVData = new VData();
//    tVData=(VData)inParam.clone();
    for(int i=0;i<inParam.size();i++)
    {
      tVData.add((String)inParam.get(i));
    }
    BeforeTBDeal tBeforeTBDeal = new BeforeTBDeal();
    if( !tBeforeTBDeal.submitData(tVData,"INSERT||PROPOSAL") )
    {
        mResult.add("N");
    	this.mErrors.copyAllErrors(tBeforeTBDeal.mErrors);
    	mResult.add(this.mErrors.getErrContent());
    }
    else {
      //返回投保成功信息
    mResult.add("Y");
    mResult.add(tBeforeTBDeal.getResult().get(0));  //获取保单号和客户号
//    mResult.add(tBeforeTBDeal.getResult().get(1));//客户号
//    	mResult.add(tBeforeTBDeal.getResult());
    
    }
    return true;
  }

  private boolean exeCheck(Vector inParam)
  {
    mResult.clear();
    mResult.add("SUCCESS");
    String Polno=(String) inParam.get(1);
    String RiskCode=(String) inParam.get(2);
    String Mult=(String) inParam.get(3);
    String InsuredName=(String) inParam.get(4);
    String InsuredBirthday=(String) inParam.get(5);
    String Sex=(String) inParam.get(6);

    String SQL="select 1 from dual where ( select ( case when sum(mult) is not null then sum(mult) else 0 end) from lcpol a where exists "+
               "(select 1 from lcinsured where name='"+"?InsuredName?"+"' and birthday='"+"?InsuredBirthday?"+"' and sex='"+"?Sex?"+"' and contno=a.contno ) "
               +"and riskcode='"+"?RiskCode?"+"' and grppolno='00000000000000000000' "
              +")<=(4-"+"?Mult?"+")";
    
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    sqlbv1.sql(SQL);
    sqlbv1.put("InsuredName",InsuredName);
    sqlbv1.put("InsuredBirthday",InsuredBirthday);
    sqlbv1.put("RiskCode",RiskCode);
    sqlbv1.put("Sex",Sex);
    sqlbv1.put("Mult",Mult);
    ExeSQL tSQL = new ExeSQL();
    String Result = tSQL.getOneValue(sqlbv1);
    if(Result==null||Result.equals(""))
    {
      mResult.add("N");
    }
    else if (Result.equals("1"))
      mResult.add("Y");
    else
      mResult.add("N");
    return true;
  }


  /**
   * 导库
   * @param inParam
   * @return
   */
//  private boolean exeInport(Vector inParam)
//  {
//    String strAct = (String) inParam.get(0);
//    logger.debug("==> :"+strAct);
//    mResult.clear();
//    mResult.add("SUCCESS");
//    VData tVData = new VData();
//    for(int i=0;i<inParam.size();i++)
//    {
//      tVData.add((String)inParam.get(i));
//    }
//    InportData tInportData = new InportData();
//    if( !tInportData.submitData(tVData) )
//    {
//      mResult.add("N");
//    }
//    else {
//      //返回投保成功信息
//    mResult.add("Y");}
//    return true;
//  }
  public static void main(String[] args)
  {
	  int p = 13 ;
	  int i1 = 968 + p;
	  int i2 = 68 + p;
	  int i3 = 17 + p;
    //        WebServiceServer webServiceServer1 = new WebServiceServer();
		BeforeTBDeal tBeforeTBDeal = new BeforeTBDeal();
//    	Vector v = new Vector();
//    	String a = new String("1");
//    	v.add("TB");
//    	v.add(a);
//    	v.add("^|");
//    	v.add("36230020080219110"+i1);
//    	v.add("3263501010590"+i2);
//    	v.add("311603");
//    	v.add("86110000");
//    	v.add("80000");
//    	v.add("800000");
//    	v.add("1");
//    	v.add("LBJ"+i3);
//    	v.add("1");
//    	v.add("1985-11-11");
//    	v.add("1");
//    	v.add("310205198511112121");
//    	v.add("010-8484805");
//    	v.add("15319851111");
//    	v.add("crif");
//    	v.add("123456");
//    	v.add("player");
//    	v.add("9000000274");
////    	v.add("9124000"+i3);
//    	v.add("2009-02-10");
//    	v.add("2009-02-10");
//    	v.add("1");
//    	v.add("LBJ2");
//    	v.add("310205198511112121");
//    	v.add("15319851111");
//    	v.add("dizhi");
//    	v.add("1");
//    	v.add("0");
//    	v.add("00");
		VData tVData = new VData();
		tVData.add("TB");
        tVData.add("1");
        tVData.add("^|");
        tVData.add("00110020030210017861");
        tVData.add("00110020030231");
        tVData.add("311603");
        tVData.add("86110000");
        tVData.add("20000");
        tVData.add("200000");
        tVData.add("1");
        tVData.add("钱七");
        tVData.add("0");
        tVData.add("1970-12-24");
        tVData.add("0");
        tVData.add("03040219701224156x");
//      tVData.add("1970-12-24");
        tVData.add("010-12345678");
        tVData.add("12345678912");
        tVData.add("地球一角");
//        tVData.add("1@com");
        
//        tVData.add("1");
        tVData.add("000000");
        tVData.add("00");
//        tVData.add("1");
        tVData.add("");
        tVData.add("2009-02-24");
        tVData.add("2009-02-24");
        tVData.add("1");
        tVData.add("钱七1");
//        tVData.add("0");
        tVData.add("03040219701223156x");
        tVData.add("010-12345678");
//      tVData.add("00");
//      tVData.add("00");
//      tVData.add("1");
//      tVData.add("1");
//      tVData.add("1970-12-23");
//        tVData.add("000000");
        tVData.add("地球一角");
//        tVData.add("1@com");
        tVData.add("1");
        tVData.add("1");
        tVData.add("00");
    	TBSIGNBL tTBSIGNBL = new TBSIGNBL();
    	tTBSIGNBL.submitData(tVData);

    		logger.debug("success");
    	
//    	if(tBeforeTBDeal.submitData(v, "INSERT||PROPOSAL")){
//    		logger.debug("success");
//    	};
	  
  }
}
