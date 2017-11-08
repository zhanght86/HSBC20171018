package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔系统：通用方法</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLClaimPubFunBL
{
private static Logger logger = Logger.getLogger(LLClaimPubFunBL.class);

    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();


    private String mClmNo    = "";     //赔案号
    private String mCusNo    = "";     //客户号



    public LLClaimPubFunBL(){}


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－产品定义信息－－－－－－－－－－－－－－－－－－
           private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
           private ExeSQL mExeSQL = new ExeSQL();
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    
    
	/**
	 * 2009-5-07 zhangzheng
	 * 日期比较
	 * @param StateDate
	 * @param EndDate
	 * @return
	 */
    public static boolean checkDate(String StateDate, String EndDate)
    {
        String flag = "";
        FDate tFDate = new FDate();
        Date tStateDate = tFDate.getDate(StateDate);
        Date tEndDate = tFDate.getDate(EndDate);

        if (tStateDate.after(tEndDate))
        {
            flag = "0";
            logger.debug("开始日期晚于结束日期！！！！");

            return false;
        }
        else
        {
            flag = "1";
            logger.debug("开始日期早于结束日期！！！");

            return true;
        }
    }
    
	/**
	 * 目的:得到个人立案信息
	 * 
	 */
	public LLRegisterSchema getLLRegister(String pClmNo) {

		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(pClmNo);
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();

		if (tLLRegisterDB.mErrors.needDealError()) {
			mErrors.addOneError("个人赔案号[" + pClmNo + "]的立案信息没有取到!!!");
			return null;
		}

		if (tLLRegisterSet.size() == 1) {
			tLLRegisterSchema = tLLRegisterSet.get(1);
		} else {
			mErrors.addOneError("个人赔案号[" + pClmNo + "]的立案信息为空没有取到!!!");
			return null;
		}

		return tLLRegisterSchema;
	}
    
    /**
	 * 责任给付赔付
	 * 
	 * @param pGetDutyKind
	 * @param pGetDutyCode
	 * @return
	 */
	public LMDutyGetClmSchema getLMDutyGetClm(String pGetDutyKind,
			String pGetDutyCode) {

		LMDutyGetClmSchema tLMDutyGetClmSchema = new LMDutyGetClmSchema();
		LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
		tLMDutyGetClmDB.setGetDutyKind(pGetDutyKind);
		tLMDutyGetClmDB.setGetDutyCode(pGetDutyCode);

		LMDutyGetClmSet tLMDutyGetClmSet = tLMDutyGetClmDB.query();

		if (tLMDutyGetClmDB.mErrors.needDealError()) {
			mErrors.addOneError("理赔类型[" + pGetDutyKind + "],给付责任["
					+ pGetDutyCode + "]的产品定义信息没有取到!!!"
					+ tLMDutyGetClmDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMDutyGetClmSet.size() == 1) {
			tLMDutyGetClmSchema = tLMDutyGetClmSet.get(1);
		} else {
			mErrors.addOneError("理赔类型[" + pGetDutyKind + "],给付责任["
					+ pGetDutyCode + "]的产品定义信息为空!!!");
			return null;
		}

		return tLMDutyGetClmSchema;
	}
    
    
    /**
	 * 查询赔案设计的社保和第三方给付金额
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	public double[] getSocOtherFee(String tClmno,String tCustomer,String destype) {
		
		double[] tSocOtherFee={0.0,0.0};
		
		// 社保赔付金额。
		double p_Sum_SocFranchise = 0;
		p_Sum_SocFranchise = Double.parseDouble(new DecimalFormat("0.00")
				.format(p_Sum_SocFranchise));
		
		//第三方给付金额
		double p_Sum_OtherFranchise = 0;
		p_Sum_OtherFranchise = Double.parseDouble(new DecimalFormat("0.00")
				.format(p_Sum_OtherFranchise));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		

		tSql = "select currency,FactorCode,nvl(sum(FactorValue),0) "
			/*+ " nvl(sum(case FactorCode  when 'D001' then FactorValue end),0),"
			+ " nvl(sum(case FactorCode  when 'D002' then FactorValue end),0) "*/
			+ " from LLOtherFactor  where 1 = 1 " + " and Feeitemtype='D' and ClmNo ='"
			+ tClmno + "' and CustomerNo='"+tCustomer+"'"
			+" group by currency,FactorCode";

	    logger.debug("------------------------------------------------------");
	    logger.debug("--计算社保给付,第三方给付:" + tSql);
	    logger.debug("------------------------------------------------------");

	    tSSRS = tExeSQL.execSQL(tSql);

	    if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算社保给付,第三方给付发生错误,"+tExeSQL.mErrors.getLastError());
			return tSocOtherFee;
		}
	    
	    LDExch tLDExch = new LDExch();

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			
			if(tSSRS.GetText(i,2).equals("D001"))
			{
				if(tSSRS.GetText(i,1).equals(destype))
					tSocOtherFee[0] = tSocOtherFee[0] +Double.parseDouble(tSSRS.GetText(i,3));
				else
					tSocOtherFee[0] = tSocOtherFee[0] +tLDExch.toOtherCur(tSSRS.GetText(i,1),destype,PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}				
			if(tSSRS.GetText(i,2).equals("D002"))
			{
				if(tSSRS.GetText(i,1).equals(destype))
					tSocOtherFee[1] = tSocOtherFee[1] + Double.parseDouble(tSSRS.GetText(i,3));
				else
					tSocOtherFee[1] = tSocOtherFee[1] +tLDExch.toOtherCur(tSSRS.GetText(i,1),destype,PubFun.getCurrentDate(),Double.parseDouble(tSSRS.GetText(i,3)));
			}
		}
		
		logger.debug("案件:"+tClmno+",客户:"+tCustomer+"的社保给付总金额:"+tSocOtherFee[0]+",第三方给付金额:"+tSocOtherFee[1]);
		
		return tSocOtherFee;
	}
	
	  /**
	 * 判断团单是否录入了特约/走特约处理
	 * true 录入,false 未录入特约(走产品定义条款),
	 * 针对当控制级别为保障计划级别时存在特殊查询规则:不仅需要在理赔责任控制中录入了保障计划,且每个出险人同样需要走保障计划承保
	 * @param tGrpContNo,tCostomerNo,tCtrlLevel(控制级别:4--保障计划)
	 * @return
	 */
	public boolean getCheckLLDutyCtrl(String tGrpContNo,String tCustomerNo,String tCtrlLevel) {
		
		
		LLDutyCtrlDB tLLDutyCtrlDB=new LLDutyCtrlDB();
		
		//控制级别为保障计划
		if(tCtrlLevel.equals("4"))		
		{
			String sql="select a.*  from lldutyctrl a, lcinsured b  where a.grpcontno = b.grpcontno"
					  +" and b.contplancode = a.ContPlanCode and b.insuredno = '"+tCustomerNo+"'"
					  +" and a.grpcontno='"+tGrpContNo+"'";
			
			logger.debug("--校验团单:"+tGrpContNo+",客户"+tCustomerNo+",是否通过理赔责任控制所规定的保障计划承保sql:"+sql);
			
			if(tLLDutyCtrlDB.executeQuery(sql).size()>0)
			{
				return true;
			}
			
		}
		else
		{
			tLLDutyCtrlDB.setGrpContNo(tGrpContNo);
			if(tLLDutyCtrlDB.query().size()>0)
			{
				return true;
			}
		}		
		
		tLLDutyCtrlDB=null;

		return false;
	}
	
	
	
	
	  /**
	 * 查询团单理赔责任控制
	 * @param tGrpContNo
	 * @return LLDutyCtrlSchema
	 */
	public LLDutyCtrlSet getLLDutyCtrl(String tGrpContNo) {
		
		LLDutyCtrlDB tLLDutyCtrlDB=null;	
		LLDutyCtrlSet tLLDutyCtrlSet=null;
		
		if(tGrpContNo==null||tGrpContNo.equals(""))
		{
			return null;
		}
		else
		{
			String sql="select * from lldutyctrl where grpcontno='"+tGrpContNo+"' order by ctrllevel";
			logger.debug("查询团单:"+tGrpContNo+"的理赔责任控制sql:"+sql);
			
			tLLDutyCtrlDB=new LLDutyCtrlDB();
			tLLDutyCtrlSet=tLLDutyCtrlDB.executeQuery(sql);
			if(tLLDutyCtrlSet.size()==0)
			{
				return null;
			}
			else
			{
				return tLLDutyCtrlSet;
			}
		}
	}
	
	
	 /**
	 * 获得团单理赔责任控制点的值
	 * @param tGrpContNo,String tCustomerNo,tDutyCode,tGetDutyCode,tObservePointName(字段名称),tProperty(控制属性),tCtrlLevel(控制级别)
	 * 对于限额有特殊处理,需要指定查询特定控制级别的限额,对于其他控制点该参数不起作用
	 * @return LLDutyCtrlSchema
	 */
	public String getLLDutyCtrlPoint(String tGrpContNo,String tCustomerNo,String tDutyCode,String tGetDutyCode,
			String tObservePointName,String tProperty,String tCtrlLevel) {
		
		String mPropertyValue="";
		String sql="select nvl(getGrpClaimCtrlValue('"+tGrpContNo+"','"+tCustomerNo+"','"+tDutyCode+"','"+tGetDutyCode+"','"
												 +tObservePointName+"','"+tProperty+"',"+tCtrlLevel+"),-1) from dual";
		logger.debug("--查询团单:"+tGrpContNo+","+tCustomerNo+","+tDutyCode+","+tGetDutyCode+","
				 +tObservePointName+","+tProperty+","+tCtrlLevel+"的理赔责任控制点sql:"+sql);
			
		ExeSQL tExeSQL = new ExeSQL();
		
		mPropertyValue=tExeSQL.getOneValue(sql);
		
		
		logger.debug("查询团单:"+tGrpContNo+","+tCustomerNo+","+tDutyCode+","+tGetDutyCode+","
				 +tObservePointName+","+tProperty+","+tCtrlLevel+"的理赔责任控制点值为:"+mPropertyValue);
		
		return mPropertyValue;
	
	}
	
	 /**
	 * 获得团单保障计划下的险种集合
	 * @param tGrpContNo,String tContPlanCode
	 * 对于限额有特殊处理,需要指定查询特定控制级别的限额,对于其他控制点该参数不起作用
	 * @return LLDutyCtrlSchema
	 */
	public LCContPlanRiskSet getLCContPlanRisk(String tGrpContNo,String tContPlanCode) {
		
		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		tLCContPlanRiskDB.setGrpContNo(tGrpContNo);
		tLCContPlanRiskDB.setContPlanCode(tContPlanCode);
		
		LCContPlanRiskSet tLCContPlanRiskSet=tLCContPlanRiskDB.query();
		
		
		if(tLCContPlanRiskSet.size()==0)
		{
			return null;
		}
		
		tLCContPlanRiskDB=null;
		return tLCContPlanRiskSet;
	
	}
	
	
    
    /**
	 * 获取出险日期
	 * 规则：若存在“其他出险日期”，则使用“其他出险日期”，若不存在“其他出险日期”，则使用“医疗出险日期”
	 * @return
	 */
	public String getInsDate(String pClmNo) {
		
		String tReturn = "";
		String tSql = "";
		tSql = "select AccDate,MedAccDate from LLCase where CaseNo='" + pClmNo + "'";
		
		logger.debug("--得到赔案号[" + pClmNo + "]的出险日期的sql:"+ tSql);

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS=new SSRS();
		tSSRS=tExeSQL.execSQL(tSql);
		
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "得到赔案号[" + pClmNo + "]的出险时间发生错误!"+this.mErrors.getLastError());
		}
		else{
			
			if(tSSRS.GetText(1,1)==null||tSSRS.GetText(1,1).equals("")){
				tReturn=tSSRS.GetText(1,2);
			}
			else{
				tReturn=tSSRS.GetText(1,1);
			}
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			tReturn = tReturn.substring(0, 10);
		}
		
		logger.debug("--得到赔案号[" + pClmNo + "]的出险日期[" + tReturn + "]!");


		return tReturn;
	}
    
    
    /**
	 * 根据险种代码和给付责任取得结算信息的财务类型FeeFinaType
	 * @param tRiskCode:险种编码
	 * @param tGetDutyKind:理赔类型
	 * @return tFeeFinaType:财务类型
	 */
	public String getFeeFinaType(String tRiskCode,String tGetDutyKind) {
		
		 logger.debug("***************** Start getFeeFinaType");
		 
		 logger.debug("传入RiskCode:"+tRiskCode+",传入GetDutyKind:"+tGetDutyKind);
		 String tFeeFinaType=null;//财务类型
		 
		 if(tRiskCode==null||tRiskCode.equals("")){
			 CError.buildErr(this, "传入险种信息为空!");
			 return tFeeFinaType;
		 }
		 
		 if(tGetDutyKind==null||tGetDutyKind.equals("")){
			 CError.buildErr(this, "传入理赔类型为空!");
			 return tFeeFinaType;
		 }
		 
		 /**
		  * 判断顺序:1 健康委托产品; 2 短期险; 3 根据理赔类型判断
		  * 
		  */
		 LMRiskAppDB tLMRiskAppDB=new LMRiskAppDB();
		 tLMRiskAppDB.setRiskCode(tRiskCode);
		 
		 LMRiskAppSet tLMRiskAppSet=tLMRiskAppDB.query();
		 
		 logger.debug("tLMRiskAppSet.get(1).getHealthType():"+tLMRiskAppSet.get(1).getHealthType());
		 logger.debug("tLMRiskAppSet.get(1).getRiskType():"+tLMRiskAppSet.get(1).getRiskType());
		 
         if(tLMRiskAppSet.size()>0)
         {
        	 boolean CMFlag=false;
        	 
        	 //首先判断是不是健康委托产品
        	 if(!(tLMRiskAppSet.get(1).getRiskType()==null||tLMRiskAppSet.get(1).getRiskType().equals("")))
        	 {
        		 if(!(tLMRiskAppSet.get(1).getHealthType()==null|| tLMRiskAppSet.get(1).getHealthType().equals("")))
            	 {
                 	 if((tLMRiskAppSet.get(1).getRiskType().equals("H"))&&
                			 (tLMRiskAppSet.get(1).getHealthType().equals("1"))){
                		 
                    	 tFeeFinaType="CM";////健康委托产品特殊财务类型
                    	 CMFlag=true;
                	 } 
            	 }
        	 }
        	 
        	 //如果不是健康委托产品,则第二步判断是不是短期险赔款
        	 if(CMFlag==false)
        	 {
        		 if(tLMRiskAppSet.get(1).getRiskPeriod().equals("M")||
            			 tLMRiskAppSet.get(1).getRiskPeriod().equals("S")){
            		 
            		 tFeeFinaType="DQPK";//	短期险赔款
            	 }
            	 else{		 
            		 
            		 //前两步都无法确定财务类型的情况再根据理赔类型来确定财务类型
            		 if(tGetDutyKind.substring(1,3).equals("00")
            				 ||tGetDutyKind.substring(1,3).equals("04")){
            			 
            			 tFeeFinaType= "YLPK";//医疗给付赔款
            		 }
            		 
               		 else if(tGetDutyKind.substring(1,3).equals("02")){
            			 
            			 tFeeFinaType= "SWPK";//身故给付赔款
            		 }
            		 
            		 else if(tGetDutyKind.substring(1,3).equals("01")
            				 ||tGetDutyKind.substring(1,3).equals("03")){
            			 
            			 tFeeFinaType= "SCPK";//伤残/高残给付赔款
            		 }
            		 else{

            			 CError.buildErr(this, "传入理赔类型错误!");	
            			 tFeeFinaType=null;
            		 }
            		 
            	 }
        	 }
         }
         else{
        	 
			 CError.buildErr(this, "查询不到险种"+tRiskCode+"的险种信息!");
			 tFeeFinaType=null;
         }
		 
         logger.debug("RiskCode:"+tRiskCode+",GetDutyKind:"+tGetDutyKind+"确定的FeeFinaType:"+tFeeFinaType);
		 logger.debug("***************** End getFeeFinaType");
		 
		 tLMRiskAppDB=null;
		 tLMRiskAppSet=null;
		 return tFeeFinaType;		
	}
    

	/**
	 * 目的:得到被保人信息
	 * 
	 */
	public LCInsuredSchema getLCInsured(String pContNo, String pCustNo) {

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(pContNo);
		tLCInsuredDB.setInsuredNo(pCustNo);
		LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();

		if (tLCInsuredDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + pContNo + "],客户号[" + pCustNo
					+ "]的被保人信息没有取到!!!"
					+ tLCInsuredDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCInsuredSet.size() == 1) {
			tLCInsuredSchema = tLCInsuredSet.get(1);
		} else {
			mErrors.addOneError("合同号[" + pContNo + "],客户号[" + pCustNo
					+ "]的被保人信息为空没有取到!!!");
			return null;
		}

		return tLCInsuredSchema;
	}

    /**
     * 得到险种信息
     * @param pRiskCode
     * @return
     */
    public LMRiskSchema getLMRisk(String pRiskCode)
    {
        String tSql = "select * from LMRisk where 1=1 "
            +" and riskcode = '"+pRiskCode+"'";

        LMRiskDB tLMRiskDB = new LMRiskDB();
        LMRiskSet tLMRiskSet = tLMRiskDB.executeQuery(tSql);

        if (tLMRiskDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLMRiskDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询险种信息出错!["+pRiskCode+"]";
            this.mErrors.addOneError(tError);
            return null;
        }

        if (tLMRiskSet.size() == 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLMRiskDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询险种信息为空!险种编号:["+pRiskCode+"]";
            this.mErrors.addOneError(tError);
            return null;
        }

        return tLMRiskSet.get(1);
    }




    /**
     * 得到险种信息
     * @param pRiskCode
     * @return
     */
    public LMRiskAppSchema getLMRiskApp(String pRiskCode)
    {
        String tSql = "select * from LMRiskApp where 1=1 "
            +" and riskcode = '"+pRiskCode+"'";

        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.executeQuery(tSql);

        if (tLMRiskAppDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询险种信息出错!"+tSql;
            this.mErrors.addOneError(tError);
            return null;
        }

        if (tLMRiskAppSet.size() == 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询险种信息出错!险种编号:["+pRiskCode+"]";
            this.mErrors.addOneError(tError);
            return null;
        }

        return tLMRiskAppSet.get(1);
    }





    /**
     * 对LMRiskSort进行判断
     * 7.理赔保额冲减
     * 8.理赔医疗类附加险单独理算[225]
     * 9.附加险退未满期保费[225,264]
     * 10.主险终止附加险必须终止
     * 11.长期附加险应退现金价值[277,280,332]
     * 12.附加险处理等同于主险[266,277]
     * @return
     */
    public boolean getLMRiskSort(String pRiskCode,String pRiskType)
    {
        LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
        tLMRiskSortDB.setRiskCode(pRiskCode);
        tLMRiskSortDB.setRiskSortType(pRiskType);
        LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();

        //没有查到定义的数据
        if (tLMRiskSortSet.size()>0)
        {
            return true;
        }

        return false;
    }
    
    
    /**
     * 对LMRiskSort进行判断
     * 7.理赔保额冲减
     * 8.理赔医疗类附加险单独理算[225]
     * 9.附加险退未满期保费[225,264]
     * 10.主险终止附加险必须终止
     * 11.长期附加险应退现金价值[277,280,332]
     * 12.附加险处理等同于主险[266,277]
     * @return
     */
    public boolean getLMRiskSort(String pRiskCode,String pRiskType,String pRiskSortValue)
    {
        LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
        tLMRiskSortDB.setRiskCode(pRiskCode);
        tLMRiskSortDB.setRiskSortType(pRiskType);
        tLMRiskSortDB.setRiskSortValue(pRiskSortValue);
        LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();

        //没有查到定义的数据
        if (tLMRiskSortSet.size()>0)
        {
            return true;
        }

        return false;
    }



    /**
     * 得到给付责任的名称
     * @return
     */
    public String getGetDutyName(LCGetSchema tLCGetSchema)
    {
        String tReturn = "";
        String tSql = "select GetDutyName from LMDutyGetRela where 1=1 "
            +" and GetDutyCode = '"+ tLCGetSchema.getGetDutyCode() + "'";

        ExeSQL tExeSQL = new ExeSQL();
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError())
        {
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询保项名称时出错!";
            this.mErrors.addOneError(tError);
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getGetDutyName()--查询保项名称时出错!"+tSql);
            logger.debug("------------------------------------------------------");
        }
        return tReturn;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－产品定义信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－承保处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**
     * 目的:得到合同信息
     *
     */
    public LCContSchema getLCCont(String pContNo)
    {

        LCContSchema tLCContSchema = new LCContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(pContNo);
        LCContSet tLCContSet = tLCContDB.query();

        if (tLCContDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCContDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "calDutyPay";
            tError.errorMessage = "LCPol表查询失败!";
            this.mErrors.addOneError(tError);
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getLCPol()--LCPol表查询发生错误!");
            logger.debug("------------------------------------------------------");
            return null;
        }

        if ( tLCContSet.size() == 1)
        {
            tLCContSchema = tLCContSet.get(1);
        }
        else
        {
            return null;
        }

        return tLCContSchema;
    }


   /**
     * 目的:得到保单险种信息
     *
     */
    public LCPolSchema getLCPol(String pPolNo)
    {

        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(pPolNo);
        LCPolSet tLCPolSet = tLCPolDB.query();

        if (tLCPolDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "calDutyPay";
            tError.errorMessage = "LCPol表查询失败!";
            this.mErrors.addOneError(tError);
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getLCPol()--LCPol表查询发生错误!");
            logger.debug("------------------------------------------------------");
            return null;
        }

        if ( tLCPolSet.size() == 1)
        {
            tLCPolSchema = tLCPolSet.get(1);
        }
        else
        {
            return null;
        }

        return tLCPolSchema;
    }




    /**
	 * 目的:计算两个日期之间的缴费期数 pBDate:开始时间 pEDate:终止时间 pPayIntv:缴费间隔 pStandByDate:出险时间
	 */
	public double getLCPremPeriodTimes(String pBDate, String pEDate,
			String pPayIntv, String pStandByDate) {
		double tDPeriodTimes = 0; // 返回的最后缴费期数
		int tIPayIntv = 0; // 缴费间隔
		String tEDate = ""; // 计算后的缴费截止日期

		logger.debug("-------------------计算两个日期之间的缴费期数------开始-------------------\n");

		logger.debug("-----开始时间:[" + pBDate + "],结束时间:[" + pEDate
				+ "],缴费间隔:[" + pPayIntv + "],出险时间:[" + pStandByDate + "]\n");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 0 －－ 趸缴 1 －－ 月缴 3 －－ 季缴 6 －－
		 * 半年缴 12 －－ 年缴 36 －－ 三年缴 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 如果 出险时间 < 缴费终止日
		 * 2004-01-01 < 2005-10-01 则 计算终止日期 = 出险时间 否则 计算终止日期 = 缴费终止日 -
		 * 缴费间隔月数[将缴费终止日期往前推一期]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		int tDays = PubFun.calInterval(pStandByDate, pEDate, "D");
		if (tDays > 0) {
			tEDate = pStandByDate;
		} else {
			tIPayIntv = Integer.parseInt(pPayIntv);
			tEDate = PubFun.calDate(pEDate, -tIPayIntv, "M", pEDate);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 计算开始时间，结束时间间隔的月数
		 * 对于月缴[1]的数据，需要将月数进行加一处理 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		int tIMonth = PubFun.calInterval(pBDate, tEDate, "M");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 对于月缴[1]的数据，需要将月数进行加一处理 2005-01-02 到 2006-01-01 PubFun计算为 11 个月
		 * 可实际业务中应为12个月 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (pPayIntv.equals("1")) {
			tIMonth = tIMonth + 1;
		}

		logger.debug("-----经过的整月数:[" + tIMonth + "],开始时间:[" + pBDate
				+ "],结束时间:[" + tEDate + "]");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 计算2个日期的天数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		int tISMonth = getDateUnit(pBDate, "M");
		int tIEMonth = getDateUnit(tEDate, "M");

		int tISDay = getDateUnit(pBDate, "D");
		int tIEDay = getDateUnit(tEDate, "D");

		if (!pPayIntv.equals("1") && tISMonth <= tIEMonth && tISDay <= tIEDay) {
			tIMonth = tIMonth + 1;
			logger.debug("-----计算后经过的月数:[" + tIMonth + "]");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 最后确定的缴费月数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double tDMonth = Double.parseDouble(String.valueOf(tIMonth));
		logger.debug("-----最后确定的缴费月数:[" + tIMonth + "]");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
		 * 当缴费方式为趸交,或者两个日期为同月时,返回的期数为1
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (pPayIntv.equals("0") || pPayIntv.equals("-1") || tIMonth == 0) {
			tDPeriodTimes = 1;
		} else {
			tDPeriodTimes = Arith.div(tDMonth, Double.parseDouble(pPayIntv));
			double tMod = tDMonth % Double.parseDouble(pPayIntv);

			if (tMod != 0) {
				tDPeriodTimes = tDPeriodTimes + 1;
				tDPeriodTimes = (int) tDPeriodTimes;
			}

			logger.debug("-----初步确定的缴费期数:[" + tDPeriodTimes + "]");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1 开始日期 + 计算的期数 * 期
			 * 与 终止日期相比较 如果大于等于终止日期，需要期数再加一，否则不动
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tSSumMonth = String.valueOf((int) Arith.round(tDPeriodTimes
					* Double.parseDouble(pPayIntv), 0));// 总月数
			int tISumMonth = Integer.parseInt(tSSumMonth);
			String tStandaDate = PubFun
					.calDate(pBDate, tISumMonth, "M", pBDate);
			logger.debug("-----开始日期 + 期数 * 期后的日期:[" + tStandaDate + "]");
			int tCalDay = PubFun.calInterval(tStandaDate, tEDate, "D");
			if (tCalDay >= 0) {
				tDPeriodTimes = tDPeriodTimes + 1;
				tDPeriodTimes = (int) tDPeriodTimes;
			}

		}

		logger.debug("-----最后确定的缴费期数:[" + tDPeriodTimes + "]");

		logger.debug("\n-------------------计算两个日期之间的缴费期数------结束-------------------");
		return tDPeriodTimes;
	}

	/**
	 * 得到日期的部件
	 */
	public int getDateUnit(String pDate, String pUnit) {
		int tIReturn = 0;

		FDate tFDate = new FDate();
		Date tDate = tFDate.getDate(pDate);

		GregorianCalendar tCalendar = new GregorianCalendar();
		tCalendar.setTime(tDate);

		if (pUnit.equals("Y")) {
			tIReturn = tCalendar.get(Calendar.YEAR);
		}

		if (pUnit.equals("M")) {
			tIReturn = tCalendar.get(Calendar.MONTH) + 1;
		}

		if (pUnit.equals("D")) {
			tIReturn = tCalendar.get(Calendar.DAY_OF_YEAR);// 一年中的第N天
			tIReturn = tCalendar.get(Calendar.DAY_OF_MONTH);// 一月中的第N天

		}

		return tIReturn;
	}

    /**
     * 判断当期保费是否已交 1-应交已交 0-应交未交
     * @param pLCPolSchema 保单信息
     * @return String
     */
    private String getPayFeeFlag(LCPolSchema pLCPolSchema, String sCalDate)
    {
        //暂时只是判断交至日期与退保点的关系
        //疑问：是否要去判断应收实收和暂交费？ 如果有暂交费没有核销算不算已交 zhangtao 2005-07-11
        //确认：暂交费未核销的不算已交
        String sPayNextFlag = "";
        if (pLCPolSchema.getPaytoDate() == null || pLCPolSchema.getPaytoDate().equals(""))
        {
            CError.buildErr(this, "合同号:["+pLCPolSchema.getContNo()+"],保险险种号:["+pLCPolSchema.getPolNo()+"],交费对应日为空,没有交费!");
            return null;
        }

        if (pLCPolSchema.getPayIntv() == 0)  //趸交
        {
            sPayNextFlag = "1"; //1-应交已交
            return sPayNextFlag;
        }

        int intval = PubFun.calInterval(sCalDate, pLCPolSchema.getPaytoDate(), "D");

        if (intval > 0)
        {
            sPayNextFlag = "1"; //1-应交已交
        }
        else
        {
            sPayNextFlag = "0"; //0-应交未交
        }

        return sPayNextFlag;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－承保处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－保全处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 找事故日期之后的经过保全处理的批单号
     * 如果没有,将返回值置为空
     * @return
     */
    public LPEdorItemSchema getLPEdorItemAfter(String pContNo,String pPolNo,String pDate,String pPosType)
    {

        pPosType = StrTool.cTrim(pPosType);
        String tReturn = "";
        String tSql = "";

        ExeSQL tExeSQL = new ExeSQL();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No1.0 根据保单的合同号、被保人编号
         *  在LPEdorItem个险保全项目表中找出最后的批改生效日期
         *  在事故之后发生了保全变更
         *  事故日期    <= 保全生效日期
         *  2005-05-01 <=  2005-07-01
         *  AA:附加险加保变更,PT:减保,and EdorType in ('PT','AA')
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select max(EdorValiDate) from LPEdorItem where 1=1 "
            +" and ContNo ='"+pContNo+"'"
            +" and (PolNo ='"+pPolNo+"' or  PolNo in ('000000'))"  //承保时的保单号
            +" and to_date('"+pDate+"','YYYY-MM-DD') <= EdorValiDate"
            ;

        if ( pPosType.length()>0 )
        {
            tSql = tSql + " and EdorType ='"+pPosType+"'";
        }


        String tEdorValiDate = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "获取保全处理的批改生效日期发生错误!";
            logger.debug("-----------------------------------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批改生效日期发生错误!"+tSql);
            logger.debug("-----------------------------------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return null;
        }

        logger.debug("-----------------------------------------------------------------------------------");
        logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批改生效日期["+tEdorValiDate+"]:"+tSql);
        logger.debug("-----------------------------------------------------------------------------------");

        if (tEdorValiDate == null || tEdorValiDate.length() == 0)
        {
            return null;
        }
        else
        {
            tEdorValiDate = tEdorValiDate.substring(0,10);
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No2.0 根据最早的核保完成生效时间
         *  在LPEdorItem个险保全项目表中找出批单号
         *  如果批单号空，则说明没有发生保全变更项目，取C表的基本保额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select * from LPEdorItem where 1=1 "
            +" and ContNo ='"+pContNo+"'"
            +" and (PolNo ='"+pPolNo+"' or  PolNo in ('000000'))"  //承保时的保单号
            +" and EdorValiDate = to_date('"+tEdorValiDate+ "','yyyy-mm-dd')";


        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(tSql);

        if (tLPEdorItemDB.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "获取保全处理的批单号发生错误!";
            logger.debug("-----------------------------------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批单号发生错误!"+tSql);
            logger.debug("-----------------------------------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return null;
        }

        logger.debug("-----------------------------------------------------------------------------------");
        logger.debug("--LLClaimPubFunBL.getLPEdorItemAfter()--获取保全处理的批单号["+tLPEdorItemSet.size()+"]:"+tSql);
        logger.debug("-----------------------------------------------------------------------------------");


        if ( tLPEdorItemSet == null || tLPEdorItemSet.size() == 0 )
        {
            return null;
        }


        return tLPEdorItemSet.get(1);
    }




    /**
     * 找事故日期之前的经过保全处理的批单号
     * 如果没有,将返回值置为空
     * @return
     */
    public LPEdorItemSchema getLPEdorItemBefore(String pContNo,String pPolNo,String pDate,String pPosType)
    {

        pPosType = StrTool.cTrim(pPosType);
        String tReturn = "";
        String tSql = "";

        ExeSQL tExeSQL = new ExeSQL();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No1.0 根据保单的合同号、被保人编号
         *  在LPEdorItem个险保全项目表中找出最后的批改生效日期
         *  在事故之后发生了保全变更
         *  事故日期    <= 保全生效日期
         *  2005-05-01 <=  2005-07-01
         *  AA:附加险加保变更,PT:减保,and EdorType in ('PT','AA')
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select max(EdorValiDate) from LPEdorItem where 1=1 "
            +" and ContNo ='"+pContNo+"'"
            +" and (PolNo ='"+pPolNo+"' or  PolNo in ('000000'))"  //承保时的保单号
            +" and to_date('"+pDate+"','YYYY-MM-DD') >= EdorValiDate"
            ;

        if ( pPosType.length()>0 )
        {
            tSql = tSql + " and EdorType ='"+pPosType+"'";
        }


        String tEdorValiDate = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "获取保全处理的批改生效日期发生错误!";
            logger.debug("-----------------------------------------------------------------------------------");
            logger.debug("--LLClaimCalMatchBL.getLPEdorItemBefore()--获取保全处理的批改生效日期发生错误!"+tSql);
            logger.debug("-----------------------------------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return null;
        }

        logger.debug("-----------------------------------------------------------------------------------");
        logger.debug("--LLClaimPubFunBL.getLPEdorItemBefore()--获取保全处理的批改生效日期["+tEdorValiDate+"]:"+tSql);
        logger.debug("-----------------------------------------------------------------------------------");

        if (tEdorValiDate == null || tEdorValiDate.length() == 0)
        {
            return null;
        }
        else
        {
            tEdorValiDate = tEdorValiDate.substring(0,10);
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No2.0 根据最早的核保完成生效时间
         *  在LPEdorItem个险保全项目表中找出批单号
         *  如果批单号空，则说明没有发生保全变更项目，取C表的基本保额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select * from LPEdorItem where 1=1 "
            +" and ContNo ='"+pContNo+"'"
            +" and (PolNo ='"+pPolNo+"' or  PolNo in ('000000'))"  //承保时的保单号
            +" and EdorValiDate = to_date('"+tEdorValiDate+ "','yyyy-mm-dd')";


        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(tSql);

        if (tLPEdorItemDB.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "获取保全处理的批单号发生错误!";
            logger.debug("-----------------------------------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getLPEdorItemBefore()--获取保全处理的批单号发生错误!"+tSql);
            logger.debug("-----------------------------------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return null;
        }

        logger.debug("-----------------------------------------------------------------------------------");
        logger.debug("--LLClaimCalMatchBL.getLPEdorItemBefore()--获取保全处理的批单号["+tLPEdorItemSet.size()+"]:"+tSql);
        logger.debug("-----------------------------------------------------------------------------------");


        if ( tLPEdorItemSet == null || tLPEdorItemSet.size() == 0 )
        {
            return null;
        }


        return tLPEdorItemSet.get(1);
    }



    /**
     * 得到保全处理LPGet数据
     * @return
     */
    public LPGetSchema getLPGet(String pPosEdorNo,String pNBPolNo,LCGetSchema pLCGetSchema)
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No1.0 根据批单号，承保保单号，责任编码
         *  在LPDuty保全保单险种责任表找出基本保额Amnt
         *  如果数据空，则说明没有发生保全变更项目，取C表的基本保额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "select * from LPGet where 1=1 "
            +" and EdorNo ='"+pPosEdorNo+"'"
            +" and PolNo  ='"+pNBPolNo+"'"
            +" and DutyCode ='"+pLCGetSchema.getDutyCode()+"'"
            +" and GetDutyCode ='"+pLCGetSchema.getGetDutyCode()+"'";

        LPGetDB tLPGetDB = new LPGetDB();
        LPGetSet tLPGetSet = tLPGetDB.executeQuery(tSql);

        if ( tLPGetDB.mErrors.needDealError() )
        {
            this.mErrors.copyAllErrors(tLPGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPubFunBL";
            tError.functionName = "getLPGet";
            tError.errorMessage = "查询保全处理的领取项发生错误!";
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getLPGet()--查询保全处理的领取项发生错误!"+tSql);
            logger.debug("------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return null;
        }

        logger.debug("------------------------------------------------------------------------------------");
        logger.debug("--LLClaimPubFunBL.getLPGet()--获取保全处理的领取项信息:["+tLPGetSet.size()+"]"+tSql);
        logger.debug("------------------------------------------------------------------------------------");


        if (tLPGetSet.size()==1)
        {
            return tLPGetSet.get(1);
        }
        else
        {
            return null;
        }

    }


    /**
     * 得到累计年度红利
     * @return
     */
    public double getYearBonus(String pPolNo,String pDate)
    {
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 得到年度红利信息
         * nvl(sum(a.BonusAmnt),0)
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

        LOEngBonusPolDB tLOEngBonusPolDB = new LOEngBonusPolDB();
        tLOEngBonusPolDB.setPolNo(pPolNo);
        LOEngBonusPolSet tLOEngBonusPolSet = tLOEngBonusPolDB.query();

        if (tLOEngBonusPolDB.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLOEngBonusPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getYearBonus";
            tError.errorMessage = "获取年度红利发生错误!";
            logger.debug("-----------------------------------------------------------------------------------");
            logger.debug("--LLClaimPubFunBL.getYearBonus()--获取年度红利发生错误!");
            logger.debug("-----------------------------------------------------------------------------------");
            this.mErrors.addOneError(tError);
        }

        for (int i=1;i<=tLOEngBonusPolSet.size();i++)
        {
            LOEngBonusPolSchema tLOEngBonusPolSchema = tLOEngBonusPolSet.get(i);
        }
        */

        EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
        t_Sum_Return = tEdorCalZT.getSumAmntBonus(pPolNo,pDate);

        if (t_Sum_Return < 0 )
        {
            t_Sum_Return = 0 ;
        }

        t_Sum_Return = Arith.round(t_Sum_Return,2);

        logger.debug("--LLClaimPubFunBL.getYearBonus()--年度红利:["+t_Sum_Return+"]");
        logger.debug("-----------------------------------------------------------------------------------");

        return t_Sum_Return;
    }


    /**
     * 得到终了红利
     * @return
     */
    public double getFinalBonus(String pPolNo,String pDate)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 红利计算
         *  有效保额 = 基本保额 + 累计红利保额
         *  有效保额 = 基本保额 + 累计红利保额 + 终了红利
         *  基本保额 = LCDutySchema.getAmnt()
         *  年度红利 = LOEngBonusPol，BonusAmnt
         *  累计红利 = LOEngBonusPol，SUM(BonusAmnt)
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(t_Sum_Return));

        String tSql = "";
        ExeSQL tExeSQL = new ExeSQL();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 D02：计算终了红利
         * 调用保全公式
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        logger.debug("-----------------------------------计算终了红利开始------------------------------------");
        EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
        t_Sum_Return = tEdorCalZT.getFinalBonus(pPolNo,pDate);

        t_Sum_Return = Arith.round(t_Sum_Return,2);
        if (t_Sum_Return < 0 )
        {
            t_Sum_Return = 0 ;
        }

        logger.debug("--LLClaimPubFunBL.getFinalBonus()--终了红利:["+t_Sum_Return+"]");
        logger.debug("-----------------------------------计算终了红利结束------------------------------------");
        return t_Sum_Return;
    }



    /**
     * 判断险种是否是分红险
     * Y:是
     * N:不是
     * @return
     */
    public String getBonus(LCPolSchema pLCPolSchema)
    {
        String tReturn = "";

        String sql = " select BonusFlag from lmriskapp where 1=1"
            +" and riskcode = '" + pLCPolSchema.getRiskCode() + "'"
            ;

        ExeSQL tExeSQL = new ExeSQL();
        String sBonusFlag = tExeSQL.getOneValue(sql);
        if (tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "险种["+pLCPolSchema.getRiskCode()+"],查询分红险标志失败!");
            return null;
        }

        if (sBonusFlag == null || sBonusFlag.equals("") || sBonusFlag.equals("0"))
        {
            //不是分红险
            sBonusFlag = "N";
        }
        else
        {
            sBonusFlag = "Y";
        }

        tReturn = sBonusFlag;
        return tReturn;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－保全处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－理赔处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 目的:计算该保单是否发生过理赔
     * @param tLCPolSchema
     * @return
     */
    public boolean getPolNoAddPay(String pClmNo,String pNBPolNo,LCPolSchema pLCPolSchema)
    {
        String tSql = "select a.* from LLClaimDetail a,LLClaim d where 1 = 1"
            + " and a.ClmNo = d.ClmNo"
            + " and d.ClmState in ('50','60') "//只计算状态为结案,完成的费用
            + " and a.PolNo = '"+pLCPolSchema.getPolNo()+"'"
            ;

        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tSql);

        logger.debug("------------------------------------------------------");
        logger.debug("--LLClaimPubFunBL.getPolNoAddPay()--计算保单累计支付:["+tLLClaimDetailSet.size()+"]"+tSql);
        logger.debug("------------------------------------------------------");

        if (tLLClaimDetailDB.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalFranchiseBL";
            tError.functionName = "getLCPolNoPay";
            tError.errorMessage = "计算保单累计支付发生错误!保单号["+pLCPolSchema.getPolNo()+"]";
            this.mErrors.addOneError(tError);
            return false;
        }

        if ( tLLClaimDetailSet.size() > 1 )
        {
            return true;
        }

        return false;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－理赔处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－其他处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－其他处理信息－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


		 /**
     * 得到理赔操作员姓名
     * @return
     */
    public String getUserName(String pUserCode)
    {
        String tReturn = "";
        String tSql = "select a.UserName from LLClaimUser a where 1=1 "
            +" and UserCode = '"+ pUserCode + "'";

        ExeSQL tExeSQL = new ExeSQL();
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));

        if (tExeSQL.mErrors.needDealError())
        {
        	CError tError = new CError();
          tError.moduleName = "LLClaimConfirmPassBL";
          tError.functionName = "dealData";
          tError.errorMessage = "查询操作员名称时出错!";
          this.mErrors.addOneError(tError);
        }

        return tReturn;
    }

    /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 理赔结案需要添加一条信息到[理赔销售渠道信息表]中,供理赔查询统计
     * 万泽辉
     * 2006-02-13
     * String tCaseNo 赔案号
     * String tOperator 操作人
     * String tManageCom 管理机构
     * return LLClaimSaleChnlSchema
     * update 2006-02-17 P.D 险种代码多条处理
     －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    public LLClaimSaleChnlSet insertLLClaimSaleChnl(String tCaseNo,String tOperator,String tManageCom)
    {
         String tSql = "";
         String tRealPay   = "";//核赔赔付金额
         String tSaleChnl  = "";//销售渠道
         String tStartDate = "";//出险日期
//         String tEndDate   = "";//结案日期
         String tRiskCode  = "";//险种代码
         String tGrpName   = "";//单位名称
         String tGrpContNo = "";//团体保单号
         ExeSQL tExeSQL = new ExeSQL();
         LLClaimSaleChnlSet tLLClaimSaleChnlSet = new LLClaimSaleChnlSet();
         /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
          * No.1 查询险种号码和团体保单号
           －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
          */
         tSql = "select distinct riskcode,grpcontno  from llclaimpolicy where clmno = '" + tCaseNo + "'";
         SSRS tSSRS21 = tExeSQL.execSQL(tSql);
         if (tSSRS21.getMaxRow() <= 0) {
             CError tError = new CError();
             tError.moduleName = "LLClaimPubFunBL";
             tError.functionName = "insertLLClaimSaleChnl";
             tError.errorMessage = "查询赔案号['" + tCaseNo + "']险种代码出错!";
             this.mErrors.addOneError(tError);
         }
         for (int a = 1; a <= tSSRS21.getMaxRow(); a++) {
             LLClaimSaleChnlSchema tLLClaimSaleChnlSchema = new LLClaimSaleChnlSchema();
             tRiskCode  = tSSRS21.GetText(a,1);
             tGrpContNo = tSSRS21.GetText(a,2);
             /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              * No.2 查询核赔赔付金额
              －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              */
             tSql = "select realpay from llclaim where clmno = '" + tCaseNo +
                    "'";
             SSRS tSSRS = tExeSQL.execSQL(tSql);
             if (tSSRS.getMaxRow() <= 0) {
                 CError tError = new CError();
                 tError.moduleName = "LLClaimPubFunBL";
                 tError.functionName = "insertLLClaimSaleChnl";
                 tError.errorMessage = "查询赔案号['" + tCaseNo + "']的核赔赔付金额出错!";
                 this.mErrors.addOneError(tError);
             }
             tRealPay = tSSRS.GetText(1, 1);
             /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              * No.3 查询出险日期，结案日期，单位名称
              －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              */
             tSql = "select RgtDate,grpname from llregister where rgtno = '" +
                    tCaseNo + "'";
             SSRS tSSRS2 = tExeSQL.execSQL(tSql);
             if (tSSRS2.getMaxRow() <= 0) {
                 CError tError = new CError();
                 tError.moduleName = "LLClaimPubFunBL";
                 tError.functionName = "insertLLClaimSaleChnl";
                 tError.errorMessage = "查询赔案号['" + tCaseNo + "']的开始日期和结束日期出错!";
                 this.mErrors.addOneError(tError);
             }
             tStartDate = tSSRS2.GetText(1, 1);
             tGrpName = tSSRS2.GetText(1, 2);
             /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              * No.4 查询销售渠道
              －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              */
             tSql = "select salechnl from lcgrpcont where grpcontno = '" +
                    tGrpContNo + "'";
             SSRS tSSRS1 = tExeSQL.execSQL(tSql);
             if (tSSRS1.getMaxRow() <= 0) {
                 CError tError = new CError();
                 tError.moduleName = "LLClaimPubFunBL";
                 tError.functionName = "insertLLClaimSaleChnl";
                 tError.errorMessage = "查询团体保单号号['" + tGrpContNo + "']的销售渠道出错!";
                 this.mErrors.addOneError(tError);
             }
             tSaleChnl = tSSRS1.GetText(1, 1);
             /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              * No.5 准备LLClaimSaleChnlSchema信息
              －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              */
             tLLClaimSaleChnlSchema.setCaseNo(tCaseNo);
             tLLClaimSaleChnlSchema.setGrpContNo(tGrpContNo);
             tLLClaimSaleChnlSchema.setGrpName(tGrpName);
             tLLClaimSaleChnlSchema.setEndCaseDate(CurrentDate);
             tLLClaimSaleChnlSchema.setAccStartDate(tStartDate);
             tLLClaimSaleChnlSchema.setRealPay(tRealPay);
             tLLClaimSaleChnlSchema.setRiskCode(tRiskCode);
             tLLClaimSaleChnlSchema.setSaleChnl(tSaleChnl);
             tLLClaimSaleChnlSchema.setRgtState("60");
             tLLClaimSaleChnlSchema.setOperator(tOperator);
             tLLClaimSaleChnlSchema.setManageCom(tManageCom);
             tLLClaimSaleChnlSchema.setMakeDate(CurrentDate);
             tLLClaimSaleChnlSchema.setMakeTime(CurrentTime);
             tLLClaimSaleChnlSchema.setModifyDate(CurrentDate);
             tLLClaimSaleChnlSchema.setModifyTime(CurrentTime);
             tLLClaimSaleChnlSet.add(tLLClaimSaleChnlSchema);
         }
         return tLLClaimSaleChnlSet;
    }
    
    public static String Display_Year(String date)
    {
        String EndDate = "";

        if (!((date == null) || date.equals("")))
        {
            String t = "";
            String t1 = date.substring(0, 4);
            String t2;

            if (date.substring(5, 6).equals("0"))
            {
                t2 = date.substring(6, 7);
            }
            else
            {
                t2 = date.substring(5, 7);
            }

            String t3;

            if (date.substring(8, 9).equals("0"))
            {
                t3 = date.substring(9, 10);
            }
            else
            {
                t3 = date.substring(8, 10);
            }

            t = t1 + "年" + t2 + "月" + t3 + "日";
            logger.debug("转换后的日期是" + t);
            EndDate = t;
        }

        return EndDate;
    }
    
    /**
	 * 判断案件给付类型
	 * 一般案件：系统显示为“0-一般给付件”。除短期出险件外的案件。
     * 短期出险件：系统显示为“1-短期出险件”。涉及赔付的险种保险期间在一年（不含）以上的，且出险日期在保单生效或复效日期两年以内的，出险原因为疾病，
     * 理赔类型为身故或重大疾病，系统判断为短期出险件
     * 3 责任免除件(拒付案件)
	 * @param tClmNo,赔案号
	 * @return casePayType: 0-一般给付件,2-短期给付件,3-责任免除件
	 */
	public String  getCheckCasePayType(String tClmNo) {
		
		String casePayType="";//案件给付类型
		
		//案件类型过滤
        String sql = " select * from LMCheckField where " +
        " riskcode = '000000'" + " and pageLocation = 'CLAIMGRP'" +
        " and FieldName = 'CheckCasePayType'" + " ";
        
        logger.debug("过滤案件类型sql:"+sql);
        
		LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
		LMCheckFieldSet tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sql);
		
		for (int i = 1; i <= tLMCheckFieldSet.size(); i++)
		{
			Calculator tCalculator = new Calculator();
			
			tCalculator.setCalCode(tLMCheckFieldSet.get(i).getCalCode());
			tCalculator.addBasicFactor("clmno", tClmNo);            
			tCalculator.addBasicFactor("AccidentDate", String.valueOf(getInsDate(tClmNo)));// 出险日期
			
			casePayType = tCalculator.calculate();
			
			if (!casePayType.trim().equals(""))
			{
				break;
			}

		}
		
		//都不符合时即默认为0-一般给付件
		if ((casePayType == null) || casePayType.trim().equals(""))
		{
			casePayType="0";
		}
		
	    logger.debug("案件:"+tClmNo+"的案件類型確定為:"+casePayType);
		
		return casePayType;
	}
	
	/**
	 * 
	 * @param tClmNo 案件号,tUserCode,操作员
	 * @return true,有权限,false 无权限;
	 */
	public boolean getCheckPopedom(String tClmNo,String tUserCode) {
		
		 LLClaimSchema tLLClaimSchema=getLLClaim(tClmNo);
		 
		 LLClaimUWMainSchema tLLClaimUWMainSchema=getLLClaimUWMain(tClmNo);
		 
		 logger.debug("案件:"+tClmNo+"的审核结论:"+tLLClaimUWMainSchema.getAuditConclusion());
		 
		 /**
		  * 2009-05-08 zhangzheng
		  * 如果审核结论是拒付,由于拒付金额是存放在declinepay字段不是存放的realpay,所以权限校验金额字段取declinepay
		  */
		 
		 String sql="";
		 
		 if(tLLClaimUWMainSchema.getAuditConclusion().trim().equals("1"))
		 {
			 
			 sql=" select distinct 1 from LLgrpClaimPopedom b,llgrpclaimuser a where a.jobcategory=b.jobcategory "
			       +" and a.usercode='"+  tUserCode+"' and b.casecategory='4'"
		           +" and b.basemin<='"+tLLClaimSchema.getDeclinePay()+"' and b.basemax>='"+tLLClaimSchema.getDeclinePay()+"'";
			 
			 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tLLClaimSchema.getDeclinePay()
			          +"理赔金:"+tLLClaimSchema.getDeclinePay()+"的权限sql:"+sql);
	
		 }
		 else
		 {
			 
			 sql=" select distinct 1 from LLgrpClaimPopedom b,llgrpclaimuser a where a.jobcategory=b.jobcategory "
			       +" and a.usercode='"+  tUserCode+"' and b.casecategory='"+tLLClaimSchema.getCasePayType()+"'"
		           +" and b.basemin<='"+tLLClaimSchema.getRealPay()+"' and b.basemax>='"+tLLClaimSchema.getRealPay()+"'";
			 
			 logger.debug("--查询理赔人员:"+tUserCode+"是否有处理案件:"+tClmNo+",案件类型:"+tLLClaimSchema.getCasePayType()
			          +"理赔金:"+tLLClaimSchema.getRealPay()+"的权限sql:"+sql);
	
		 } 

		 
		 ExeSQL tExeSQL=new ExeSQL();
		 String tFlag=tExeSQL.getOneValue(sql);
		 
		 if(tFlag.equals("")){
			 return false;
		 }
		 
		 tExeSQL=null;
		 tFlag=null;
		 
		 return true;
	}
	
	
	/**
	 * 目的:得到赔案保单理算信息
	 * 
	 */
	public LLClaimPolicySet getLLClaimPolicy(String pClmNo) {


		LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		tLLClaimPolicyDB.setCaseNo(pClmNo);
		LLClaimPolicySet tLLClaimPolicySet = tLLClaimPolicyDB.query();

		if (tLLClaimPolicyDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案保单理算信息没有取到!!!"
					+ tLLClaimPolicyDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLClaimPolicySet.size() == 0) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案保单理算信息为空没有取到!!!");
			return null;
		}

		return tLLClaimPolicySet;
	}
	
	/**
	 * 目的:得到赔案理算信息
	 * 
	 */
	public LLClaimSchema getLLClaim(String pClmNo) {

		LLClaimSchema tLLClaimSchema = new LLClaimSchema();

		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setCaseNo(pClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();

		if (tLLClaimDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案理算信息没有取到!!!"
					+ tLLClaimDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLClaimSet.size() == 1) {
			tLLClaimSchema = tLLClaimSet.get(1);
		} else {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案理算信息为空没有取到!!!");
			return null;
		}

		return tLLClaimSchema;
	}
	
	/**
	 * 目的:得到赔案审核,审批信息
	 * 
	 */
	public LLClaimUWMainSchema getLLClaimUWMain(String pClmNo) {


		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(pClmNo);
		LLClaimUWMainSet tLLClaimUWMainSet = tLLClaimUWMainDB.query();

		if (tLLClaimUWMainDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案审核审批信息没有取到!!!"
					+ tLLClaimUWMainDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLLClaimUWMainSet.size() != 1) {

			mErrors.addOneError("赔案号[" + pClmNo + "]的赔案审核审批信息为空没有取到!!!");
			return null;
		}

		return tLLClaimUWMainSet.get(1);
	}
	
    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args)
    {

        LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
        double tDReturn = 0;
        //tDReturn = tLLClaimPubFunBL.getLCPremPeriodTimes("2005-01-01","2005-9-01","3");
        LLClaimSaleChnlSet tReturn = tLLClaimPubFunBL.insertLLClaimSaleChnl("520501000126","tk","0101");
    }
}
