package com.sinosoft.lis.ebusiness;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bq.AddEdorItemCheckBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;


import java.util.*;

/**
 * <p>Title: Web电子商务系统</p>
 * <p>Description: 内网保全工作流调用接口</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */
public class PEdorBL
{
private static Logger logger = Logger.getLogger(PEdorBL.class);

    /** 传入数据的容器 */
    private VData mInputData = new VData();
    private VData mOutputData = new VData();
    /** 返回数据的容器 */
    private VData mResult = new VData();

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String mContNo = null;//合同号
	private String mCustomerNo = null;//投保人/被保人 客户号
	private String mGrpName =null;//名字
	private String mPostalAddress = null;//通讯地址
	private String mZipCode = null;//邮政编码
	private String mPhone = null;//单位电话
	private String mPhone2 = null;//家庭电话
	private String mMobile = null; //移动电话
	private String mEMail = null;//电子邮件
	private String mEdorType = null;//EC-投保人保全 ED-被保人保全
	private String tAddressNo = null;//旧地址号
	private String aAddressNo = null;//新地址号
	private String mEdorAppDate = PubFun.getCurrentDate();
	private String strAct = null;
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    public PEdorBL()
    {
    }

    public Vector submitData(Vector inParam)
    {
        strAct = (String) inParam.get(0);
        for(int i=1;i<inParam.size();i++){
        mInputData.add((String) inParam.get(i));
        }
        if (strAct.equals("ACEDOR||CONFIRM"))
        {
            PEdorACConfirm();
        }
        else if (strAct.equals("BBEDOR||CONFIRM"))
        {
            PEdorBBConfirm();
        }
        
        logger.debug("Start PEdorBL: " + strAct);
        if (strAct.equals("INSERT||PEDOR"))
        {
            VData tVData = new VData();
            GlobalInput mGlobalInput = new GlobalInput();
            TransferData mTransferData = new TransferData();

            /** 全局变量 */
            mGlobalInput.Operator = (String) mOutputData.get(1);
            mGlobalInput.ComCode = (String) mOutputData.get(2);
            mGlobalInput.ManageCom = (String) mOutputData.get(3);

            mTransferData.setNameAndValue("ContNo", (String) mOutputData.get(4));
            mTransferData.setNameAndValue("EdorType", (String) mOutputData.get(5));
            mTransferData.setNameAndValue("Operate", (String) mOutputData.get(6));

            mTransferData.setNameAndValue("CustomerNo", (String) mOutputData.get(7));
            mTransferData.setNameAndValue("GrpName", (String) mOutputData.get(8));
            mTransferData.setNameAndValue("PostalAddress",
                (String) mOutputData.get(9));
            mTransferData.setNameAndValue("ZipCode", (String) mOutputData.get(10));
            mTransferData.setNameAndValue("Phone", (String) mOutputData.get(11));
            mTransferData.setNameAndValue("Phone2", (String) mOutputData.get(12));
            mTransferData.setNameAndValue("Mobile", (String) mOutputData.get(13));
            mTransferData.setNameAndValue("EMail", (String) mOutputData.get(14));

            //
            PubFun.out(this, "Operator:" + (String) mOutputData.get(1));
            PubFun.out(this, "ComCode:" + (String) mOutputData.get(2));
            PubFun.out(this, "ManageCom:" + (String) mOutputData.get(3));

            PubFun.out(this, "ContNo:" + (String) mOutputData.get(4));
            PubFun.out(this, "EdorType:" + (String) mOutputData.get(5));
            PubFun.out(this, "Operate:" + (String) mOutputData.get(6));

            PubFun.out(this, "CustomerNo:" + (String) mOutputData.get(7));
            PubFun.out(this, "GrpName:" + (String) mOutputData.get(8));
            PubFun.out(this, "PostalAddress:" + (String) mOutputData.get(9));
            PubFun.out(this, "ZipCode:" + (String) mOutputData.get(10));
            PubFun.out(this, "Phone:" + (String) mOutputData.get(11));
            PubFun.out(this, "Phone2:" + (String) mOutputData.get(12));
            PubFun.out(this, "Mobile:" + (String) mOutputData.get(13));
            PubFun.out(this, "EMail:" + (String) mOutputData.get(14));

            //        mTransferData.setNameAndValue("PolNo", request.getParameter("PolNo"));
            //        mTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
            //        mTransferData.setNameAndValue("Operate", "INSERT||EDOR");
            //
            //        mTransferData.setNameAndValue("CustomerNo", "0000000899");
            //        mTransferData.setNameAndValue("GrpName", request.getParameter("GrpName"));
            //        mTransferData.setNameAndValue("PostalAddress", request.getParameter("PostalAddress"));
            //        mTransferData.setNameAndValue("ZipCode", request.getParameter("ZipCode")) ;
            //        mTransferData.setNameAndValue("Phone", request.getParameter("Phone"));
            //        mTransferData.setNameAndValue("Phone2",request.getParameter("Phone2"));
            //        mTransferData.setNameAndValue("Mobile",request.getParameter("Mobile"));
            //        mTransferData.setNameAndValue("EMail",request.getParameter("EMail"));
            /**总变量*/
            tVData.add(mGlobalInput);
            tVData.add(mTransferData);
        	
            //进行后台处理
            try{
	            if(deal(tVData))
	            {
	            	mResult.clear();
	                mResult.add(CError.TYPE_NONE);
	            }else{
	            	mResult.clear();
	                mResult.add(CError.TYPE_FORBID);
	                mResult.add(mErrors.getErrContent());
	            }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            
            return mResult;
        }
        else if (strAct.equals("QUERY||PEDOR"))
        {
            String strContNo = (String) inParam.get(1);
            String strSQL = "select appntno,insuredno from lccont where contno='"+"?strContNo?"+"'";
            logger.debug("strSQL: " + strSQL);
            
            SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
            sqlbv1.sql(strSQL);
            sqlbv1.put("strContNo", strContNo);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(sqlbv1);

            if (tSSRS.MaxRow <= 0)
            {
                // @@ 错误处理
                mResult.clear();
                mResult.add(CError.TYPE_FORBID);
                return mResult;
            }

            //取得投保人和被保人号码
            String strAppntNo = tSSRS.GetText(1, 1);
            String strInsuredNo = tSSRS.GetText(1, 2);
            
            String strSQLt = " select a.customerno,b.appntname,a.postaladdress,a.zipcode,a.companyphone,a.homephone,a.mobile,a.email "
            	+ "from lcaddress a, lcappnt b "
            	+ "where a.customerno = '"+"?strAppntNo?"+"' "
            	+ " and a.customerno = b.appntno "
            	+ " and a.addressno = b.addressno "
            	+ " and b.contno = '"+"?strContNo?"+"' ";
//            tSSRS = tExeSQL.execSQL(strSQL);
//            String strReturnt = tExeSQL.getEncodedResult(strSQLt, 1);
//            String strRt = strReturnt.substring(0, 3);
//            if (strRt.equals("100"))
//            {
//                mResult.clear();
//                mResult.add("FAIL");
//            }
//            mResult.add(strReturnt);
            SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
            sqlbv2.sql(strSQLt);
            sqlbv2.put("strAppntNo", strAppntNo);
            sqlbv2.put("strContNo", strContNo);
            tSSRS = tExeSQL.execSQL(sqlbv2);

            if (tSSRS.MaxRow > 0)
            {
                mResult.clear();
                mResult.add(CError.TYPE_NONE);
                mResult.add(tSSRS.encode());
            }
            else
            {
                //错误处理
                mResult.clear();
                mResult.add(CError.TYPE_FORBID);
                return mResult;
            }

           String  strSQLb = " select a.customerno,b.name,a.postaladdress,a.zipcode,a.companyphone,a.homephone,a.mobile,a.email "
            	+ "from lcaddress a, lcinsured b "
            	+ "where customerno = '"+"?strInsuredNo?"+"'"
            	+ " and a.customerno = b.insuredno"
            	+ " and a.addressno = b.addressno"
            	+ " and b.contno = '"+"?strContNo?"+"' ";
//            tSSRS = tExeSQL.execSQL(strSQL);
//            String strReturnb = tExeSQL.getEncodedResult(strSQLb, 1);
//            String strRb = strReturnb.substring(0, 3);
//            if (strRb.equals("100"))
//            {
//                mResult.clear();
//                mResult.add("FAIL");
//            }
//            mResult.add(strReturnb);
           SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
           sqlbv3.sql(strSQLb);
           sqlbv3.put("strInsuredNo", strInsuredNo);
           sqlbv3.put("strContNo", strContNo);
           tSSRS = tExeSQL.execSQL(sqlbv3);
           if (tSSRS.MaxRow > 0)
           {
               mResult.add(tSSRS.encode());
           }
           else
           {
               //错误处理
               mResult.clear();
               mResult.add(CError.TYPE_FORBID);
               return mResult;
           }
        }
        return mResult;
    }

    //后台处理
    private boolean deal(VData cInputData) {
    	// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		
		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		//数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");
		
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备要返回的数据
		if (!prepareOutputData()) {
			return false;
		}


		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult))
		{
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			return false;
		}
		
		return true;
	

	}
    
    private boolean getInputData() 
	{
    
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		mGrpName = (String) mTransferData.getValueByName("GrpName");
		mPostalAddress = (String) mTransferData.getValueByName("PostalAddress");
		mZipCode = (String) mTransferData.getValueByName("ZipCode");
		mPhone = (String) mTransferData.getValueByName("Phone");
		mPhone2 = (String) mTransferData.getValueByName("Phone2");
		mMobile = (String) mTransferData.getValueByName("Mobile");
		mEMail = (String) mTransferData.getValueByName("EMail");
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		
		
		return true;
	}
	
	private boolean checkData() 
	{
		if (!checkContState(mContNo)) 
		{
			return false;
		}
		
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setContNo(mContNo);
		tLPEdorItemSchema.setEdorType(mEdorType);
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo(mCustomerNo);
		tLPEdorItemSchema.setEdorAppDate(mEdorAppDate);
		AddEdorItemCheckBL tAddEdorItemCheckBL = new AddEdorItemCheckBL();
		if(!tAddEdorItemCheckBL.exeCheckCode(tLPEdorItemSchema))
		{
			mErrors.copyAllErrors(tAddEdorItemCheckBL.mErrors);
			return false;
		}
		return true;
	}
	
	private boolean checkContState(String sContNo) 
	{

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(sContNo);
		 tLCContDB.setAppFlag("1");
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单查询失败，请确认保单号输入是否正确");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() != 1) {
			CError.buildErr(this, "该保单号不存在，请检查保单号输入是否正确");
			return false;
		}
		// 校验保单是否挂起 
		String bsql = " select 1 from lcconthangupstate where contno= '"
				+ "?sContNo?"
				+ "' and hanguptype='2'  and posflag='1' ";
		logger.debug(bsql);
		
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(bsql);
		sqlbv4.put("sContNo", sContNo);
		ExeSQL bExeSQL = new ExeSQL();
		String fl = bExeSQL.getOneValue(sqlbv4);
		if (!"".equals(fl)) {
			logger.debug("此保单正进行其它保全操作，请稍候再进行保全");
			return false;
		}

		// 校验保单生存领取状态是否终止,sGetEndState为1未终止,为空已终止
		String asql = " select distinct 1 from lcget where contno = '"+"?sContNo?"
				+ "' and livegettype = '0' and ( case when getendstate is not null then getendstate else '0' end) = '0'";
		logger.debug(asql);
		
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(asql);
		sqlbv5.put("sContNo", sContNo);
		ExeSQL aExeSQL = new ExeSQL();
		String sGetEndState = aExeSQL.getOneValue(sqlbv5);
		if (aExeSQL.mErrors.needDealError()) {
			logger.debug("保单生存领取状态查询失败");
			return false;
		}
		
		if (!tLCContSet.get(1).getAppFlag().equals("1")) {
			String sql = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				sql = " select statereason from lccontstate where statetype in ('Terminate') and state = '1' "
						+ " and ((startdate <= '"+"?mEdorAppDate?"+"' and '"+"?mEdorAppDate?" 
						+ "' <= enddate) or "
						+ " (startdate <= '"+"?mEdorAppDate?"+"' and enddate is null)) "
						+ " and polno = (select polno from lcpol where contno = '"
						+ "?sContNo?"+"' and polno = mainpolno and rownum = 1 )";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				sql = " select statereason from lccontstate where statetype in ('Terminate') and state = '1' "
						+ " and ((startdate <= '"+"?mEdorAppDate?"+"' and '"+"?mEdorAppDate?" 
						+ "' <= enddate) or "
						+ " (startdate <= '"+"?mEdorAppDate?"+"' and enddate is null)) "
						+ " and polno = (select polno from lcpol where contno = '"
						+ "?sContNo?"+"' and polno = mainpolno limit 1 )";
			}
			logger.debug(sql);
			
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(sql);
			sqlbv6.put("mEdorAppDate", mEdorAppDate);
//			sqlbv6.put("mEdorAppDate", mEdorAppDate);
//			sqlbv6.put("mEdorAppDate", mEdorAppDate);
			sqlbv6.put("sContNo", sContNo);
			ExeSQL tExeSQL = new ExeSQL();
			String sStateReason = tExeSQL.getOneValue(sqlbv6);
			if (tExeSQL.mErrors.needDealError()) {
				logger.debug("保单状态查询失败");
				return false;
			}
			if (sStateReason == null || sStateReason.trim().equals("")) {
				CError.buildErr(this, "保单已经终止，不能受理保全");
				return false;
			} else if (sStateReason.trim().equals("01")
					|| sStateReason.trim().equals("05")
					|| sStateReason.trim().equals("06")
					|| sStateReason.trim().equals("02")
					|| sStateReason.trim().equals("04")) {
				if (sStateReason.trim().equals("04")
						&& (sGetEndState == null || sGetEndState.trim().equals(
								""))) {
					CError.buildErr(this, "保单已经理赔终止，不能受理保全!"); // 理赔终止且生存领取也终止的,不可以受理
					return false;
				} else {
					return true; // 退保、满期、自垫、贷款终止,可以受理; 理赔终止且有生存领取的，可以受理
				}
			} else {
				if (sStateReason.trim().equals("03")) {
					CError.buildErr(this, "保单已经解约终止，不能受理保全");
					return false;
				} else if (sStateReason.trim().equals("07")) {
					CError.buildErr(this, "保单已经失效终止，不能受理保全");
					return false;
				} else {
					CError.buildErr(this, "保单已经终止，不能受理保全");
					return false;
				}
			}
		}


		return true;
	}
	
	private boolean dealData() 
	{
		//查询旧地址号
		ExeSQL tExeSQL= new ExeSQL();
		String sql = null;
		if("ED".equals(mEdorType))
		{
			sql = "select AddressNo from LCInsured where InsuredNo='"+"?mCustomerNo?"+"'  and contno='"+"?mContNo?"+"'" ;
		}else{
			sql = "select AddressNo from LCAppnt where AppntNo='"+"?mCustomerNo?"+"' and contno='"+"?mContNo?"+"'" ;
		}
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("mCustomerNo", mCustomerNo);
		sqlbv7.put("mContNo", mContNo);
		tAddressNo = tExeSQL.getOneValue(sqlbv7).toString();
		
		//查询新地址号
		sql = "select (case when Max(AddressNo) is not null then Max(AddressNo) else 0 end) + 1 from LCAddress where CustomerNo = '"+"?mCustomerNo?"+"'";
		logger.debug(sql);
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("mCustomerNo", mCustomerNo);
		aAddressNo = tExeSQL.getOneValue(sqlbv8).toString();
		// 设置地址表信息
		logger.debug("新的地址编码:"+aAddressNo);
		
		Reflections tRef = new Reflections();
		

		String mCurrentDate = PubFun.getCurrentDate();
		String mCurrentTime = PubFun.getCurrentTime();
		
		String strLimit = PubFun.getNoLimit("86");
		String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo", strLimit);
		String strEdorConfNo = PubFun1.CreateMaxNo("EdorConfNo", strLimit);
		//add by jiaqiangli 2009-09-09 号段冲突 lis-9287
		String strEdorNo = PubFun1.CreateMaxNo("EdorAppNo",strLimit);
		
		logger.debug("strEdorAcceptNo="+strEdorAcceptNo);
		logger.debug("strEdorConfNo="+strEdorConfNo);
		logger.debug("strEdorNo="+strEdorNo);
		
		mLPEdorAppSchema.setEdorAcceptNo(strEdorAcceptNo);
		mLPEdorAppSchema.setOtherNo(mContNo);
		mLPEdorAppSchema.setOtherNoType("3");
		mLPEdorAppSchema.setEdorConfNo(strEdorConfNo);
		mLPEdorAppSchema.setEdorAppDate(mCurrentDate);
		mLPEdorAppSchema.setEdorState("0");
		mLPEdorAppSchema.setManageCom(mGlobalInput.ManageCom);
		mLPEdorAppSchema.setEdorAppDate(mCurrentDate);
		mLPEdorAppSchema.setEdorAppName(mGrpName);
		mLPEdorAppSchema.setConfDate(mCurrentDate);
		mLPEdorAppSchema.setConfTime(mCurrentTime);
		mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setUWDate(mCurrentDate);
		mLPEdorAppSchema.setUWState("9");
		mLPEdorAppSchema.setUWTime(mCurrentTime);
		mLPEdorAppSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setMakeDate(mCurrentDate);
		mLPEdorAppSchema.setMakeTime(mCurrentTime);
		mLPEdorAppSchema.setModifyDate(mCurrentDate);
		mLPEdorAppSchema.setModifyTime(mCurrentTime);
		mLPEdorAppSchema.setAppType("7");
		map.put(mLPEdorAppSchema, "DELETE&INSERT");
		
		mLPEdorMainSchema.setEdorAcceptNo(strEdorAcceptNo);
		mLPEdorMainSchema.setEdorNo(strEdorNo);
		mLPEdorMainSchema.setContNo(mContNo);
		mLPEdorMainSchema.setEdorAppNo(strEdorNo);
		mLPEdorMainSchema.setEdorAppDate(mCurrentDate);
		mLPEdorMainSchema.setEdorState("0");
		mLPEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
		mLPEdorMainSchema.setEdorAppDate(mCurrentDate);
		mLPEdorMainSchema.setEdorAppName(mGrpName);
		mLPEdorMainSchema.setEdorValiDate(mCurrentDate);
		mLPEdorMainSchema.setConfDate(mCurrentDate);
		mLPEdorMainSchema.setConfTime(mCurrentTime);
		mLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setUWDate(mCurrentDate);
		mLPEdorMainSchema.setUWState("9");
		mLPEdorMainSchema.setUWTime(mCurrentTime);
		mLPEdorMainSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setMakeDate(mCurrentDate);
		mLPEdorMainSchema.setMakeTime(mCurrentTime);
		mLPEdorMainSchema.setModifyDate(mCurrentDate);
		mLPEdorMainSchema.setModifyTime(mCurrentTime);
		map.put(mLPEdorMainSchema, "DELETE&INSERT");
		
		mLPEdorItemSchema.setEdorAcceptNo(strEdorAcceptNo);
		mLPEdorItemSchema.setEdorNo(strEdorNo);
		mLPEdorItemSchema.setEdorAppNo(strEdorNo);
		mLPEdorItemSchema.setEdorAppDate(mCurrentDate);
		mLPEdorItemSchema.setEdorType(mEdorType);
		mLPEdorItemSchema.setDisplayType("1");
		mLPEdorItemSchema.setContNo(mContNo);
		mLPEdorItemSchema.setInsuredNo(mCustomerNo);
		mLPEdorItemSchema.setPolNo("000000");
		mLPEdorItemSchema.setEdorState("0");
		mLPEdorItemSchema.setManageCom(mGlobalInput.ManageCom);
		mLPEdorItemSchema.setEdorAppDate(mCurrentDate);
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setMakeDate(mCurrentDate);
		mLPEdorItemSchema.setMakeTime(mCurrentTime);
		mLPEdorItemSchema.setModifyDate(mCurrentDate);
		mLPEdorItemSchema.setModifyTime(mCurrentTime);
		mLPEdorItemSchema.setUWDate(mCurrentDate);
		mLPEdorItemSchema.setUWFlag("9");
		mLPEdorItemSchema.setUWTime(mCurrentTime);
		mLPEdorItemSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setStandbyFlag1(("EC".equals(mEdorType))?"0":"1");
		mLPEdorItemSchema.setStandbyFlag2(tAddressNo);
		mLPEdorItemSchema.setStandbyFlag3(aAddressNo);
		mLPEdorItemSchema.setGrpContNo("00000000000000000000");
		mLPEdorItemSchema.setEdorValiDate(mCurrentDate);
		map.put(mLPEdorItemSchema, "DELETE&INSERT");

		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		//更新投保人/被保人表里的地址号
		if("EC".equals(mEdorType))
		{
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setAppntNo(mCustomerNo);
			tLCAppntDB.setContNo(mContNo);
			if(!tLCAppntDB.getInfo())
			{
				CError.buildErr(this, "无法获取投保人信息，请检查输入信息是否正确");
				return false;
			}
			tLCAppntSchema = tLCAppntDB.getSchema();
			//备份到LPAppnt
			tLPAppntSchema.setEdorNo(strEdorNo);
			tLPAppntSchema.setEdorType(mEdorType);
			tRef.transFields(tLPAppntSchema, tLCAppntSchema);
			tLPAppntSchema.setModifyDate(mCurrentDate);
			tLPAppntSchema.setModifyTime(mCurrentTime);
			map.put(tLPAppntSchema, "DELETE&INSERT");	
			
			//更新LCAppnt
			tLCAppntSchema.setAddressNo(aAddressNo);
			tLCAppntSchema.setAppntName(mGrpName);
			tLCAppntSchema.setOperator(mGlobalInput.Operator);
			tLCAppntSchema.setModifyDate(mCurrentDate);
			tLCAppntSchema.setModifyTime(mCurrentTime);
			map.put(tLCAppntSchema, "UPDATE");
		}else
		{
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			
			tLCInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());
			tLCInsuredSchema.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLCInsuredDB.setSchema(tLCInsuredSchema);
			if(!tLCInsuredDB.getInfo())
			{
				CError.buildErr(this, "无法获取被保人信息，请检查输入信息是否正确");
				return false;
			}
			tLCInsuredSchema = tLCInsuredDB.getSchema();
			//备份到LPInsured
			tLPInsuredSchema.setEdorNo(strEdorNo);
			tLPInsuredSchema.setEdorType(mEdorType);
			tRef.transFields(tLPInsuredSchema, tLCInsuredSchema);
			tLPInsuredSchema.setModifyDate(mCurrentDate);
			tLPInsuredSchema.setModifyTime(mCurrentTime);
			map.put(tLPInsuredSchema, "DELETE&INSERT");	
			//更新LCInsured
			tLCInsuredSchema.setAddressNo(aAddressNo);
			tLCInsuredSchema.setName(mGrpName);
			tLCInsuredSchema.setOperator(mGlobalInput.Operator);
			tLCInsuredSchema.setModifyDate(mCurrentDate);
			tLCInsuredSchema.setModifyTime(mCurrentTime);
			map.put(tLCInsuredSchema, "UPDATE");
		}
		//备份到LPAddress
		LCAddressSchema tempLCAddressSchema = new LCAddressSchema();
		LCAddressDB tempLCAddressDB = new LCAddressDB();
		tempLCAddressSchema.setAddressNo(tAddressNo);
		tempLCAddressSchema.setCustomerNo(mCustomerNo);
		tempLCAddressDB.setSchema(tempLCAddressSchema);
		if(!tempLCAddressDB.getInfo())
		{
			CError.buildErr(this, "无法获取联系方式信息，请检查输入信息是否正确");
			return false;
		}
		tempLCAddressSchema = tempLCAddressDB.getSchema();
		
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		tRef.transFields(tLPAddressSchema, tempLCAddressSchema);
		tLPAddressSchema.setEdorNo(strEdorNo);
		tLPAddressSchema.setEdorType(mEdorType);
		tLPAddressSchema.setModifyDate(mCurrentDate);
		tLPAddressSchema.setModifyTime(mCurrentTime);
		map.put(tLPAddressSchema, "DELETE&INSERT"); // 加入Map
		//插入到LCAddress
		tempLCAddressSchema.setCustomerNo(mCustomerNo);
		tempLCAddressSchema.setAddressNo(aAddressNo);
		tempLCAddressSchema.setPostalAddress(mPostalAddress);
		tempLCAddressSchema.setZipCode(mZipCode);
		tempLCAddressSchema.setMobile(mMobile);
		tempLCAddressSchema.setEMail(mEMail);
		tempLCAddressSchema.setHomePhone(mPhone2);
		tempLCAddressSchema.setCompanyPhone(mPhone);

		tempLCAddressSchema.setOperator(mGlobalInput.Operator);
		tempLCAddressSchema.setModifyDate(mCurrentDate);
		tempLCAddressSchema.setModifyTime(mCurrentTime);
		tempLCAddressSchema.setMakeDate(mCurrentDate);
		tempLCAddressSchema.setMakeTime(mCurrentTime);
		map.put(tempLCAddressSchema, "DELETE&INSERT"); // 加入Map
		
		return true;
	}
	
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }
    

    private boolean PEdorACConfirm()
    {
    	mOutputData.add(strAct);
        String strPolno = (String) mInputData.get(0);
        String strCustomerno = (String) mInputData.get(1);
        mOutputData.add("M001");
        mOutputData.add("86");
        mOutputData.add("86");
        mOutputData.add(strPolno);
        mOutputData.add("EC");//投保人
        mOutputData.add("INSERT||EDOR");
        mOutputData.add(strCustomerno);

        for (int i = 2; i < mInputData.size(); i++)
        {
            mOutputData.add((String) mInputData.get(i));
        }
        strAct = "INSERT||PEDOR";
        return true;
    }

    private boolean PEdorBBConfirm()
    {
    	mOutputData.add(strAct);
        String strPolno = (String) mInputData.get(0);
        String strCustomerno = (String) mInputData.get(1);
        mOutputData.add("M001");
        mOutputData.add("86");
        mOutputData.add("86");
        mOutputData.add(strPolno);
        mOutputData.add("ED");//被保人
        mOutputData.add("INSERT||EDOR");
        mOutputData.add(strCustomerno);

        for (int i = 2; i < mInputData.size(); i++)
        {
            mOutputData.add((String) mInputData.get(i));
        }
        strAct = "INSERT||PEDOR";
        return true;
    }

    
    public static void main(String[] args)
    {
    	/**
        PEdorBL tPEdorBL = new PEdorBL();
        Vector inParam = new Vector();
        inParam.add("INSERT||PEDOR");
        inParam.add("M001");
        inParam.add("86");
        inParam.add("86");
        inParam.add("26230020080219110980");
        inParam.add("EC");//投保人
        inParam.add("INSERT||EDOR");
        inParam.add("lllbbbjjj");
        inParam.add("tongxundizhi");
        inParam.add("123456");
        inParam.add("123123123");
        inParam.add("456456456");
        inParam.add("789789789");
        inParam.add("y@1.com");
        tPEdorBL.submitData(inParam);
        */
//        inParam.add("QUERY||PEDOR");
//        inParam.add("26230020080219110979");
//        tPEdorBL.submitData(inParam);
//        VData Result = tPEdorBL.getResult();
//        String strResult = (String) Result.get(1);
//             logger.debug(strResult);
//        strResult = (String) Result.get(2);
//             logger.debug(strResult);
    	Vector inParam = new Vector();
   
        
    }
}
