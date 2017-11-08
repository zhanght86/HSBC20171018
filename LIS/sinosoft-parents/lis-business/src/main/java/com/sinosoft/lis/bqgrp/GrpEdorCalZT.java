/**
 * 
 */
package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 团体保全退保计算类.
 * <p>Title: 保全退保计算类</p>
 * <p>Description: 通过传入的批改信息计算出交退费变动信息</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft</p>
 * @author 孙少贤
 * @version 1.0
 * @CreateDate：2008-12-24
 */
public class GrpEdorCalZT {
private static Logger logger = Logger.getLogger(GrpEdorCalZT.class);
	
    private GlobalInput mGlobalInput = new GlobalInput();
	private String mAccFlag = "N";
	private String mClaimFlag = "N";
	private String mAnnuityFlag = "N";
	private String mHealthFlag = "N";//康福健康类产品
	
	private LPInsureAccSchema mLPInsureAccSchema = new  LPInsureAccSchema();
	private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
	private LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet();
	
	private LCInsureAccSchema mLCInsureAccSchema = new  LCInsureAccSchema();
	private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
	
	private double personalGet = 0.0;//个人分得的部分
	private double companyGet = 0.0;//单位分得的部分
	
	private Reflections mRef = new Reflections();
	
    public double getCompanyGet() {
		return companyGet;
	}

	public double getPersonalGet() {
		return personalGet;
	}

	public void setGlobalInput(GlobalInput iGlobalInput) {
    	
		mGlobalInput = iGlobalInput;
		
	}

	public String getCalAccFlag() {
		
		return mAccFlag;
		
	}
	
	public String getClaimFlag() {
		
		return mClaimFlag;
		
	}
	
	public String getAnnuityFlag() {
		
		return mAnnuityFlag;
		
	}
	
	

	public String getHealthFlag() {
		return mHealthFlag;
	}

	public LCInsureAccSchema getLCInsureAccSchema() {
		return mLCInsureAccSchema;
	}

	public LCInsureAccClassSet getLCInsureAccClassSet() {
		return mLCInsureAccClassSet;
	}

	public LCInsureAccTraceSet getLCInsureAccTraceSet() {
		return mLCInsureAccTraceSet;
	}

	public LPInsureAccClassSet getLPInsureAccClassSet() {
		
		return mLPInsureAccClassSet;
	}

	public LPInsureAccSchema getLPInsureAccSchema() {
		
		return mLPInsureAccSchema;
	}

	public LPInsureAccTraceSet getLPInsureAccTraceSet() {
		
		return mLPInsureAccTraceSet;
	}

	/**
     * 计算单个保单的生存退保数据
     */
	public double calZTData(LPEdorItemSchema iLPEdorItemSchema) throws Exception
	{
		String tPolNo = iLPEdorItemSchema.getPolNo();//保单号
		String tEdorValiDate = iLPEdorItemSchema.getEdorValiDate();//保全生效日期
		String tEdorTypeCal = iLPEdorItemSchema.getEdorTypeCal();//算法描述
		double dZTMoney = 0.0;

		if(tPolNo==null||tPolNo.equals("")||tEdorValiDate==null||tEdorValiDate.equals("")){
			throw new Exception("获取保单批改信息失败");
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tPolNo);
		if(!tLCPolDB.getInfo()){
			throw new Exception("获取个单信息失败");
		}
		LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
		String tRiskCode = tLCPolSchema.getRiskCode();//险种号
		String tContNo = tLCPolSchema.getContNo();
		//理赔的校验真屎,要这么多!
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimDB.setPolNo(tPolNo);
		LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.query();
		if(tLJAGetClaimSet != null && tLJAGetClaimSet.size()>0){
			mClaimFlag = "Y";	
		}
		String tSql = "select distinct 1 from llclaimpolicy where contno = '"+tContNo+"' and clmstate in ('10','20','30','35','40','50')";
		ExeSQL rExeSQL = new ExeSQL();
		String tRet = rExeSQL.getOneValue(tSql);
		if (tRet != null && tRet.equals("1")) {
			mClaimFlag = "Y";
		}

		tSql="select 1 from llclaimdetail where contno= '"+tContNo+"'";
		rExeSQL = new ExeSQL();
		tRet = rExeSQL.getOneValue(tSql);
		if (tRet != null && tRet.equals("1")) {
			mClaimFlag = "Y";
		}
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tRiskCode);
		if(!tLMRiskDB.getInfo()){
			throw new Exception("获取险种定义信息失败");
		}
		LMRiskSchema tLMRiskSchema = tLMRiskDB.getSchema();
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tRiskCode);

		if(!tLMRiskAppDB.getInfo()){
			throw new Exception("获取险种承保定义失败");
		}
		LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
		String tRiskPeriod = tLMRiskAppSchema.getRiskPeriod();
		String tInsuAccFlag = tLMRiskSchema.getInsuAccFlag();
		if (tRiskPeriod != null && !tRiskPeriod.equals("") &&tRiskPeriod.equals("L")){ 
			if (tInsuAccFlag.trim().equals("Y")){
				try{
					mAccFlag = "Y";
					dZTMoney = getLAZTData(tLCPolSchema,iLPEdorItemSchema);
				} catch (Exception e) {

					throw new Exception("长期帐户型退保计算失败,原因："+e.getMessage());
				}	
			}else {
				//长险非帐户型退保 目前MS团险无此险种，留着待扩展 marked by sunsx
				try {

					dZTMoney = getLPZTData(tLCPolSchema,tEdorValiDate);
				} catch (Exception e) {

					throw new Exception("长期非帐户型退保计算失败,原因："+e.getMessage());
				}
			}
		} else {
			//短期险退保
			if (tInsuAccFlag.trim().equals("Y")) {
				//短险帐户型退保 目前MS团险无此险种，留着待扩展 marked by sunsx
				try {
					mHealthFlag = "Y";
					dZTMoney = getSAZTData(tLCPolSchema,iLPEdorItemSchema);
				} catch (Exception e) {

					throw new Exception("短期帐户型退保计算失败,原因："+e.getMessage());
				}

			}else {
				//短险非帐户型退保 
				try {
					if("RED".equals(tEdorTypeCal)){
						//建工险有效日期累计生效日期,去掉失效日期（Remove the expiration date）
						String tSQL = "SELECT * FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NULL AND State = '1'";
						LCContStateDB tLCContStateDB = new LCContStateDB();
						LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(tSQL);
						if(tLCContStateSet!=null && tLCContStateSet.size() > 0){
							String tLastEndDate = tLCContStateSet.get(1).getRemark();
							String tNowStopDate = tLCContStateSet.get(1).getStartDate();
							tLCPolSchema.setPayEndDate(tLastEndDate);
							tLCPolSchema.setPaytoDate(tLastEndDate);
							int tPolIntv = 0;
							if(tNowStopDate.compareTo(tEdorValiDate)>0){
								tSQL = "SELECT NVL(SUM(Remark),0) FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate < '"+tEdorValiDate+"'";
								ExeSQL tExeSQL = new ExeSQL();
								int tIntv = Integer.parseInt(tExeSQL.getOneValue(tSQL));
								tSQL = "SELECT StartDate FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate >= '"+tEdorValiDate+"' And StartDate <= '"+tEdorValiDate+"'";
								String tStartDate = tExeSQL.getOneValue(tSQL);
								if (tStartDate != null && !tStartDate.equals("")) {
									tEdorValiDate = tStartDate.substring(0, 10);
								}
								tEdorValiDate = PubFun.newCalDate(tEdorValiDate,"D",-tIntv);
								tSQL = "SELECT NVL(SUM(Remark),0) FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate < '"+tNowStopDate+"'";
								tPolIntv = Integer.parseInt(tExeSQL.getOneValue(tSQL));

							}else{
								tEdorValiDate = tNowStopDate;
								tSQL = "SELECT NVL(SUM(Remark),0) FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate < '"+tEdorValiDate+"'";

								ExeSQL tExeSQL = new ExeSQL();
								int tIntv = Integer.parseInt(tExeSQL.getOneValue(tSQL));
								tEdorValiDate = PubFun.newCalDate(tEdorValiDate,"D",-tIntv);

								tPolIntv = tIntv;
							}
							tLastEndDate = PubFun.newCalDate(tLastEndDate,"D",-tPolIntv);
							tLCPolSchema.setPayEndDate(tLastEndDate);
							tLCPolSchema.setPaytoDate(tLastEndDate);
						}else {
							ExeSQL tExeSQL = new ExeSQL();
							tSQL = "SELECT StartDate FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate >= '"+tEdorValiDate+"' And StartDate <= '"+tEdorValiDate+"'";
							String tStartDate = tExeSQL.getOneValue(tSQL);
							if (tStartDate != null && !tStartDate.equals("")) {
								tEdorValiDate = tStartDate.substring(0, 10);
							}
							tSQL = "SELECT NVL(SUM(Remark),0) FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate < '"+tEdorValiDate+"'";
							int tIntv = Integer.parseInt(tExeSQL.getOneValue(tSQL));
							tEdorValiDate = PubFun.newCalDate(tEdorValiDate,"D",-tIntv);
							tSQL = "SELECT NVL(SUM(Remark),0) FROM LCContState WHERE ContNo = '"+tContNo+"' AND PolNo = '"+tPolNo+"' AND StateType = 'Available' AND EndDate IS NOT NULL AND State = '1' AND EndDate < '"+tLCPolSchema.getPaytoDate()+"'";
							int tPolIntv = Integer.parseInt(tExeSQL.getOneValue(tSQL));
							String tTruePayToDate = PubFun.newCalDate(tLCPolSchema.getPaytoDate(),"D",-tPolIntv);
							tLCPolSchema.setPayEndDate(tTruePayToDate);
							tLCPolSchema.setPaytoDate(tTruePayToDate);
						}
					}
					dZTMoney = getSPZTData(tLCPolSchema,tEdorValiDate);
				} catch (Exception e) {

					throw new Exception("短期非帐户型退保计算失败,原因："+e.getMessage());
				}				
			}
		}

		return dZTMoney;
	}
	
	/**
     * 计算单个保单的生存退保数据(长期险非帐户型算法)
     */
	public double getLPZTData(LCPolSchema iLCPolSchema,String iZTDate) throws Exception
	{
		//团险非帐户型长期险退保算法
		return 0;
	}
	
	/**
     * 计算单个保单的生存退保数据(长期险帐户型算法)
     * wrote by sunsx on 2009-1-12
     * @param LCPolSchema 保单险种
     * @param LPEdorItemSchema 
     * @return double 返回-1表示错误
     */

	public double getLAZTData(LCPolSchema iLCPolSchema,LPEdorItemSchema iLPEdorItemSchema) throws Exception {
		double dPolCashValue = 0.0;
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(iLCPolSchema.getPolNo());
        LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
        if (tLCInsureAccDB.mErrors.needDealError()){
        	throw new Exception("获取帐户险种信息失败");
        }
        if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1){
        	throw new Exception("保单没有帐户数据!");
        }
        LCInsureAccSchema tLCInsureAccSchema =tLCInsureAccSet.get(1);
        if("1".equals(tLCInsureAccSchema.getState())){
        	mAnnuityFlag = "Y";
        }
        
    	TransferData tTransferData = new TransferData();
    	tTransferData.setNameAndValue("InsuAccNo",tLCInsureAccSchema.getInsuAccNo());
    	tTransferData.setNameAndValue("PolNo", tLCInsureAccSchema.getPolNo());
    	tTransferData.setNameAndValue("BalaDate", iLPEdorItemSchema.getEdorValiDate());
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		InsuAccBala tInsuAccBala = new InsuAccBala();
		if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
			throw new Exception("分红结算失败！");
		}
		VData tResult  = tInsuAccBala.getResult();
		mLCInsureAccSchema = ((LCInsureAccSet)tResult.getObjectByObjectName("LCInsureAccSet", 0)).get(1);
		mLCInsureAccClassSet = (LCInsureAccClassSet)tResult.getObjectByObjectName("LCInsureAccClassSet", 0);
		mLCInsureAccTraceSet = (LCInsureAccTraceSet)tResult.getObjectByObjectName("LCInsureAccTraceSet", 0);
		
		int intvYears = PubFun.calInterval(iLCPolSchema.getCValiDate(), iLPEdorItemSchema.getEdorValiDate(), "Y");
		if(intvYears < 0){
			throw new Exception("帐户信息出错！");
		}
		double manageRate =  GrpZTChargeRate.getZTChargeRate(iLCPolSchema.getRiskCode(),intvYears);
		
		dPolCashValue = PubFun.round((mLCInsureAccSchema.getInsuAccBala()*manageRate),2);
		
		mRef.transFields(mLPInsureAccSchema, mLCInsureAccSchema);
		mLPInsureAccSchema.setEdorNo(iLPEdorItemSchema.getEdorNo());
		mLPInsureAccSchema.setEdorType(iLPEdorItemSchema.getEdorType());
		mLPInsureAccSchema.setState("4");
		mLPInsureAccSchema.setInsuAccBala(0);
		mLPInsureAccSchema.setBalaDate(iLPEdorItemSchema.getEdorValiDate());
		mLPInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
		mLPInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
		
		LPInsureAccClassSchema tLPInsureAccClassSchema = null;
		LCInsureAccClassSchema tLCInsureAccClassSchema = null;
		
		personalGet = 0.0;
		companyGet = 0.0;
		for(int i = 1; i <= mLCInsureAccClassSet.size(); i++) {
			tLCInsureAccClassSchema = mLCInsureAccClassSet.get(i);
			if("2".equals(iLCPolSchema.getPolTypeFlag())){
				companyGet += tLCInsureAccClassSchema.getInsuAccBala()*manageRate;
			}else {
				String tPayPlanCode = tLCInsureAccClassSchema.getPayPlanCode();
				LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
				tLMDutyPayDB.setPayPlanCode(tPayPlanCode);
				if (!tLMDutyPayDB.getInfo()) {
					throw new Exception("查询责任缴费信息失败");
				}						
				String tPayAimClass = tLMDutyPayDB.getPayAimClass();
				if("2".equals(tPayAimClass)){
					companyGet += tLCInsureAccClassSchema.getInsuAccBala()*manageRate;

				}else if("1".equals(tPayAimClass)){
					personalGet += tLCInsureAccClassSchema.getInsuAccBala()*manageRate;
				}
			}

			tLPInsureAccClassSchema = new LPInsureAccClassSchema();
			mRef.transFields(tLPInsureAccClassSchema, tLCInsureAccClassSchema);
			tLPInsureAccClassSchema.setEdorNo(iLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema.setEdorType(iLPEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setState("4");
			tLPInsureAccClassSchema.setInsuAccBala(0);
			tLPInsureAccClassSchema.setBalaDate(iLPEdorItemSchema.getEdorValiDate());
			tLPInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
			tLPInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
			mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
		}
		
		LPInsureAccTraceSchema tLPInsureAccTraceSchema = null;
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = null;
		for(int i = 1; i <= mLCInsureAccTraceSet.size(); i++){
			tLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(i);
			tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
			mRef.transFields(tLPInsureAccTraceSchema, tLCInsureAccTraceSchema);
			tLPInsureAccTraceSchema.setEdorNo(iLPEdorItemSchema.getEdorNo());
			tLPInsureAccTraceSchema.setEdorType(iLPEdorItemSchema.getEdorType());
			tLPInsureAccTraceSchema.setState("4");
			tLPInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
			tLPInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
			mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
		}
		
		if("Y".equals(mClaimFlag)){
			dPolCashValue = 0.0;
			companyGet = 0.0;
			personalGet = 0.0;
		}
		return dPolCashValue;
	}
	
	/**
     * 计算单个保单的生存退保数据(短期险帐户型算法)
     */
	public double getSAZTData(LCPolSchema iLCPolSchema,LPEdorItemSchema iLPEdorItemSchema) throws Exception {
		//团险帐户型短期险退保算法
		double polCashValue = 0.0;
		//校验退保当日之后账户轨迹是否还有变动
		
		String tSQL = "select distinct 1 from lcinsureacctrace where polno = '"+iLCPolSchema.getPolNo()+"' and paydate > '"+iLPEdorItemSchema.getEdorValiDate()+"'";
		ExeSQL tExeSQL = new ExeSQL();
		String tRet = tExeSQL.getOneValue(tSQL);
		if("1".equals(tRet)){
			throw new Exception("保单号:"+iLCPolSchema.getContNo()+"的账户在"+iLPEdorItemSchema.getEdorValiDate()+"后有变动！无法退保或进行试算！");
		}
		tSQL = "select nvl(sum(money),0) from lcinsureacctrace where polno = '"+iLCPolSchema.getPolNo()+"' and paydate <='"+iLPEdorItemSchema.getEdorValiDate()+"'";
		tRet = tExeSQL.getOneValue(tSQL);
		polCashValue = Double.parseDouble(tRet);
		if(polCashValue<0){
			throw new Exception("计算发生异常！");
		}
		
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(iLCPolSchema.getPolNo());
        LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
        if (tLCInsureAccDB.mErrors.needDealError()){
        	throw new Exception("获取帐户险种信息失败");
        }
        if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1){
        	throw new Exception("保单没有帐户数据!");
        }
        mLCInsureAccSchema =tLCInsureAccSet.get(1);
		mRef.transFields(mLPInsureAccSchema, mLCInsureAccSchema);
		mLPInsureAccSchema.setEdorNo(iLPEdorItemSchema.getEdorNo());
		mLPInsureAccSchema.setEdorType(iLPEdorItemSchema.getEdorType());
		mLPInsureAccSchema.setState("4");
		mLPInsureAccSchema.setInsuAccBala(0);
		mLPInsureAccSchema.setBalaDate(iLPEdorItemSchema.getEdorValiDate());
		mLPInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
		mLPInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
		
	    LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
	    tLCInsureAccClassDB.setPolNo(iLCPolSchema.getPolNo());
	    tLCInsureAccClassDB.setInsuAccNo(mLCInsureAccSchema.getInsuAccNo());
	    mLCInsureAccClassSet = tLCInsureAccClassDB.query();
	    
		LPInsureAccClassSchema tLPInsureAccClassSchema = null;
		LCInsureAccClassSchema tLCInsureAccClassSchema = null;
		

		for(int i = 1; i <= mLCInsureAccClassSet.size(); i++) {
			tLCInsureAccClassSchema = mLCInsureAccClassSet.get(i);
			tLPInsureAccClassSchema = new LPInsureAccClassSchema();
			mRef.transFields(tLPInsureAccClassSchema, tLCInsureAccClassSchema);
			tLPInsureAccClassSchema.setEdorNo(iLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema.setEdorType(iLPEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setState("4");
			tLPInsureAccClassSchema.setInsuAccBala(0);
			tLPInsureAccClassSchema.setBalaDate(iLPEdorItemSchema.getEdorValiDate());
			tLPInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
			tLPInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
			mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
		}

		return polCashValue;
	}
	
	/**
     * 计算单个保单的生存退保数据(短期险非帐户型算法)
     */
	public double getSPZTData(LCPolSchema iLCPolSchema,String iZTDate) throws Exception {
		
		double polCashValue = 0.0;
		
		if("Y".equals(mClaimFlag)){
			polCashValue = 0.0;
			return polCashValue;
		}
		
		String tPolCValiDate = iLCPolSchema.getCValiDate();
		String tPolPaytoDate = iLCPolSchema.getPaytoDate();
		String tEdorValiDate = iZTDate;
		
		if(tPolCValiDate == null || tPolCValiDate.equals("")||tPolPaytoDate == null || tPolPaytoDate.equals("")||tEdorValiDate == null || tEdorValiDate.equals("")){
			throw new Exception("传入的保单信息不完整");
		}
		

		
		if(iLCPolSchema.getPayIntv()!=0){
			polCashValue = 0.0;
			return polCashValue;
		}
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(iLCPolSchema.getGrpPolNo());
		if(!tLCGrpPolDB.getInfo()){
			throw new Exception("获取团体险种信息失败");
		}
		LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolDB.getSchema();
		String tGrpCValidate = tLCGrpPolSchema.getCValiDate();
		String tGrpPaytoDate = tLCGrpPolSchema.getPaytoDate();
		
		if(tGrpCValidate == null || tGrpCValidate.equals("")||tGrpPaytoDate == null || tGrpPaytoDate.equals("")){
			throw new Exception("团体险种信息不完整");
		}
		String tPaytoDate = null;
		String tCValiDate = null;
		int comIntv = PubFun.calInterval(tPolPaytoDate, tGrpPaytoDate, "D");
		
		if(comIntv <= 0){
			tPaytoDate =  tGrpPaytoDate;
			tCValiDate = tPolCValiDate;
		}else{
			tPaytoDate = tPolPaytoDate;
			tCValiDate = tPolCValiDate;
		}
		int grpIntv = PubFun.calInterval(tGrpCValidate, tGrpPaytoDate, "D");//团体保单保险期间
		int polIntv = PubFun.calInterval(tCValiDate, tPaytoDate, "D");//保单保险期间
		int hasPolIntv = PubFun.calInterval(tCValiDate, tEdorValiDate, "D");//保单已经经过天数
		//分枝开始
		
		//查询分枝日期
		String tSQL = "select sysvarvalue  from ldsysvar where sysvar='CTVersionDate'";
		ExeSQL tExeSQL = new ExeSQL();
		String tCTVersionDate = tExeSQL.getOneValue(tSQL);
		if(tCTVersionDate == null || tCTVersionDate.equals("")){
			throw new Exception("退保算法日期分枝点查询失败！");
		}
		int newIntv = PubFun.calInterval(tGrpCValidate, tCTVersionDate, "D");//新保险法退保算法,以团单生效日为基准
		if(newIntv>0){
			//2009-10-01前生效的团体保单延用以前的退保算法。
			
			if (iLCPolSchema.getInsuYearFlag() != null && iLCPolSchema.getInsuYearFlag().equals("M")){
				if(iLCPolSchema.getInsuYear()== 1){
					polCashValue = 0.0;
					return polCashValue;	
				}

			}
			if(polIntv <= 31){
				polCashValue = 0.0;
				return polCashValue;
			}
			
			if(grpIntv <= 0 || polIntv <= 0 || hasPolIntv <0){
				throw new Exception("保险期间计算错误！");
			}

			int Interval = (int)Math.ceil((double)hasPolIntv*12/polIntv);
			if (iLCPolSchema.getInsuYearFlag() != null && ((iLCPolSchema.getInsuYear()==12 && iLCPolSchema.getInsuYearFlag().equals("M"))||(iLCPolSchema.getInsuYear()==1 && iLCPolSchema.getInsuYearFlag().equals("Y")))){
				//一年的
				Interval = PubFun.calInterval2(tCValiDate, tEdorValiDate, "M");
			}


			String tRiskCode = iLCPolSchema.getRiskCode();
			double dChargeRate = GrpZTChargeRate.getZTChargeRate(tRiskCode, Interval);
			if(dChargeRate == -10 ||dChargeRate < 0){
				tRiskCode = "000000";
				dChargeRate = GrpZTChargeRate.getZTChargeRate(tRiskCode, Interval);
			}

			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(iLCPolSchema.getPolNo());
			LCPremSet tLCPremSet = tLCPremDB.query();

			if(tLCPremSet.size()<=0){
				polCashValue = PubFun.round(iLCPolSchema.getPrem()*(1-dChargeRate), 2);
				return polCashValue;
			}

			for(int i = 1 ;i <=tLCPremSet.size(); i++){
				double tPrem = tLCPremSet.get(i).getPrem();
				polCashValue += tPrem*(1-dChargeRate);
			}
		}else {
			/**2009-10-01后的退保算法
			 * 返还本合同的未满期净保险费。
			 * 未满期净保险费：净保险费×（1-本合同已保障天数/本合同保险期间天数）。已保障天数天数不足一天的按一天计算。
			 * 净保险费：指不包含公司营业费用、佣金等其他费用的保险费。除本合同另有约定外，其计算公式为“保险费×（1-25%）”。
			 */
			double dNetPrem = iLCPolSchema.getPrem()*(1-0.25);//净保险费
			polCashValue = dNetPrem*(1-((double)hasPolIntv/polIntv));
			
		}
		return PubFun.round(polCashValue, 2);
	}
	/**
	 * 这个表就像一砣屎一样,让人心烦!完完全全就是一砣屎!!!!!!!!要多屎,有多屎!
	 */
	public LPGrpContStateSchema creatGrpContState(LPGrpEdorItemSchema iLPGrpEdorItemSchema){
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		
		LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
		tLPGrpContStateSchema.setEdorNo(iLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContStateSchema.setEdorType(iLPGrpEdorItemSchema.getEdorType());
		tLPGrpContStateSchema.setGrpContNo(iLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContStateSchema.setGrpPolNo("000000");
		tLPGrpContStateSchema.setStateType("Terminate");
		tLPGrpContStateSchema.setState("1");
		if ("CT".equals(iLPGrpEdorItemSchema.getEdorType())||"AT".equals(iLPGrpEdorItemSchema.getEdorType())) {
			tLPGrpContStateSchema.setStateReason("02");
		} else if ("WT".equals(iLPGrpEdorItemSchema.getEdorType())) {
			tLPGrpContStateSchema.setStateReason("06");
		} else if ("XT".equals(iLPGrpEdorItemSchema.getEdorType())||"AX".equals(iLPGrpEdorItemSchema.getEdorType())) {
			tLPGrpContStateSchema.setStateReason("05");
		} else{
			tLPGrpContStateSchema.setStateReason("02");
		}
		tLPGrpContStateSchema.setStartDate(iLPGrpEdorItemSchema.getEdorAppDate());
		tLPGrpContStateSchema.setOperator(iLPGrpEdorItemSchema.getOperator());
		tLPGrpContStateSchema.setMakeDate(tCurrentDate);
		tLPGrpContStateSchema.setMakeTime(tCurrentTime);
		tLPGrpContStateSchema.setModifyDate(tCurrentDate);
		tLPGrpContStateSchema.setModifyTime(tCurrentTime);
		return tLPGrpContStateSchema;
	}
	/**
	 * 这个表就像一砣屎一样,让人心烦!完完全全就是一砣屎!!!!!!!!要多屎,有多屎!
	 */
	public LPContStateSchema creatContState(LPEdorItemSchema iLPEdorItemSchema){
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		
		LPContStateSchema tLPContStateSchema = new LPContStateSchema();
		tLPContStateSchema.setEdorNo(iLPEdorItemSchema.getEdorNo());
		tLPContStateSchema.setEdorType(iLPEdorItemSchema.getEdorType());
		tLPContStateSchema.setContNo(iLPEdorItemSchema.getContNo());
		tLPContStateSchema.setInsuredNo("000000");
		tLPContStateSchema.setPolNo(iLPEdorItemSchema.getPolNo());
		tLPContStateSchema.setStateType("Terminate");
		tLPContStateSchema.setState("1");
		if ("CT".equals(iLPEdorItemSchema.getEdorType())||"AT".equals(iLPEdorItemSchema.getEdorType())) {
			tLPContStateSchema.setStateReason("02");
		} else if ("WT".equals(iLPEdorItemSchema.getEdorType())) {
			tLPContStateSchema.setStateReason("06");
		} else if ("XT".equals(iLPEdorItemSchema.getEdorType())||"AX".equals(iLPEdorItemSchema.getEdorType())) {
			tLPContStateSchema.setStateReason("05");
		} else{
			tLPContStateSchema.setStateReason("02");
		}
		tLPContStateSchema.setStartDate(iLPEdorItemSchema.getEdorAppDate());
		tLPContStateSchema.setOperator(iLPEdorItemSchema.getOperator());
		tLPContStateSchema.setMakeDate(tCurrentDate);
		tLPContStateSchema.setMakeTime(tCurrentTime);
		tLPContStateSchema.setModifyDate(tCurrentDate);
		tLPContStateSchema.setModifyTime(tCurrentTime);
		return tLPContStateSchema;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int comIntv1 = PubFun.calInterval("2009-09-30", "2009-10-01", "D");
		int comIntv2 = PubFun.calInterval("2009-10-01", "2009-10-01", "D");
		int comIntv3 = PubFun.calInterval("2009-10-02", "2009-10-01", "D");
		
		logger.debug(comIntv1);
		logger.debug(comIntv2);
		logger.debug(comIntv3);
		logger.debug(100*(1-0.25));

		//logger.debug("2010-11-15".compareTo("2009-03-05"));

	}

}
