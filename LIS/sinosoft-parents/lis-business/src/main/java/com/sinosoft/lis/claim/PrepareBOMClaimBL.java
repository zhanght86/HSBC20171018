package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMBnf;
import com.sinosoft.ibrms.bom.BOMCalClaim;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMMainPol;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.ibrms.bom.BOMSubPol;
import com.sinosoft.ibrms.bom.BOMSubPol2;
import com.sinosoft.lis.cardgrp.CachedRiskInfo;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCIndUWErrorDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndUWErrorSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.lis.tb.ProductSaleControlBL;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCIndUWErrorSet;
import com.sinosoft.lis.vschema.LCIndUWMasterSet;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: 理赔计算
 * </p>
 * 
 * <p>
 * Description: 理赔计算
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangfh
 * @version 6.0
 */
public class PrepareBOMClaimBL {
private static Logger logger = Logger.getLogger(PrepareBOMClaimBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ExeSQL mExeSQL = new ExeSQL();
	private String theDate = "";
	private static GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot.getInstance();
	private List tPolList = new ArrayList();//存放险种自核不通过信息的list
	private List tContList = new ArrayList();//存放合同自核不通过提示信息的list
	private List tInsList = new ArrayList();//存放被保人自核不通过提示信息的list
	
	private List mBomList = new ArrayList();
	
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mContNo = "";
	private String tRiskCode="";

	
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();
	LCDutySchema hLCDutySchema=new LCDutySchema();

	/** 发送拒保通知书 */
	LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private double AllSumAmnt = 0;
	private String mBankInsu = "0";
	private String tEndDate= PubFun.getCurrentDate();
	private int insuredCount=0;

	private String locCurrency = "";//本币信息
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
//	private int theCount = 0;
	public PrepareBOMClaimBL() {
	}


	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	public List dealData(TransferData tTransferData) {
		/** 将该合同下的所有信息查询出来,并将相应的数据转储到相应的BOM中*/
		try {
			if(((String)tTransferData.getValueByName("PolNo"))!=null && !((String)tTransferData.getValueByName("PolNo")).equals(""))
			{
				LCDutySchema tLCDutySchema=new LCDutySchema();
				if(!(((String)tTransferData.getValueByName("DutyCode"))==null || ((String)tTransferData.getValueByName("DutyCode")).equals("")))
				{
					LCDutyDB tLCDutyDB=new LCDutyDB();
					tLCDutyDB.setDutyCode((String)tTransferData.getValueByName("DutyCode"));
					tLCDutyDB.setPolNo((String)tTransferData.getValueByName("PolNo"));
					tLCDutySchema=tLCDutyDB.query().get(1);
				}
				//得到保单数据
				LCPolDB tLCPolDB=new LCPolDB();
				tLCPolDB.setPolNo((String)tTransferData.getValueByName("PolNo"));
				LCPolSchema tLCPolSchema=tLCPolDB.query().get(1);
				mContNo = tLCPolSchema.getContNo(); // 获得保单号
				tRiskCode=tLCPolSchema.getRiskCode();
				LCContDB tLCContDB=new LCContDB();
				tLCContDB.setContNo(mContNo);
				LCContSchema tLCContSchema = tLCContDB.query().get(1);
				mLCContSchema=tLCContSchema;
				//被保人信息
				LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
				tFreeLCInsuredDB.setContNo(mContNo);
				insuredCount= tFreeLCInsuredDB.query().size();
				tFreeLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
				LCInsuredSchema tFreeLCInsuredSchema=tFreeLCInsuredDB.query().get(1);
				//投保人信息
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mContNo);
				if(!tLCAppntDB.getInfo()){
					CError.buildErr(this, "查询投保人信息失败！");
					return this.mBomList;
				}
				//赋给保单级
				tEndDate=tLCDutySchema.getEndDate();

				//准备BOMCont数据
				BOMCont cont = new BOMCont();
				cont = DealBOMCont(tLCContSchema,tTransferData);
				mBomList.add(cont);
				
				//准备被保人BOMInsured数据
				BOMInsured insureds = new  BOMInsured();
				insureds = DealBOMInsured(tFreeLCInsuredSchema,tLCContSchema,tTransferData);
				mBomList.add(insureds);

				//准备投保人BOMAppnt数据
				BOMAppnt appnt = new  BOMAppnt();
				appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContSchema,tTransferData);
				mBomList.add(appnt);
				
				//准备险种BOMPol数据
				BOMPol pol = new BOMPol();
				pol = DealBOMPol(tLCPolSchema,insureds,tLCDutySchema,tTransferData);
				mBomList.add(pol);
				
				//准备险种BOMPol数据
				BOMCalClaim claim = new BOMCalClaim();
				claim = DealBOMClaim(tTransferData);
				mBomList.add(claim);
			}
			else
			{
				//准备险种BOMPol数据
				BOMCalClaim claim = new BOMCalClaim();
				claim = DealBOMClaim(tTransferData);
				mBomList.add(claim);
			}
			LCContDB tLCContDB = new LCContDB();

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return this.mBomList;
		}
		return this.mBomList;
	}
	

	private BOMCalClaim DealBOMClaim(TransferData tTransferData){
		BOMCalClaim claim = new BOMCalClaim();//多个险种
		try{
			ExeSQL tempExeSQL = new ExeSQL();
			boolean FreeFlag = false;
			
			//账户价值
			if(!(((String)tTransferData.getValueByName("InsureAccBalance"))==null||((String)tTransferData.getValueByName("InsureAccBalance")).equals("")))
				claim.setInsureAccBalance(new Double((String)tTransferData.getValueByName("InsureAccBalance")));
			//复效到出险时已保天数
			if(!(((String)tTransferData.getValueByName("MLRDays"))==null||((String)tTransferData.getValueByName("MLRDays")).equals("")))
				claim.setMLRDays(Double.valueOf((String)tTransferData.getValueByName("MLRDays")));
			//复效到出险时已保年期
			if(!(((String)tTransferData.getValueByName("MLRYears"))==null||((String)tTransferData.getValueByName("MLRYears")).equals("")))
				claim.setMLRYears(Double.valueOf((String)tTransferData.getValueByName("MLRYears")));
			//保单是否复效的标记 1复效 0无复效
			if(!(((String)tTransferData.getValueByName("MLRFlag"))==null||((String)tTransferData.getValueByName("MLRFlag")).equals("")))
				claim.setMLRFlag((String)tTransferData.getValueByName("MLRFlag"));
			//附险险的有效保额
			if(!(((String)tTransferData.getValueByName("TRMAmnt"))==null||((String)tTransferData.getValueByName("TRMAmnt")).equals("")))
				claim.setTRMAmnt(Double.valueOf((String)tTransferData.getValueByName("TRMAmnt")));//
			//附险险的理赔给付金
			if(!(((String)tTransferData.getValueByName("RMAmnt"))==null||((String)tTransferData.getValueByName("RMAmnt")).equals("")))
				claim.setRMAmnt(Double.valueOf((String)tTransferData.getValueByName("RMAmnt")));
			//账单结束日期
			if(!(((String)tTransferData.getValueByName("FeereceiEndDate"))==null||((String)tTransferData.getValueByName("FeereceiEndDate")).equals("")))
				claim.setFeereceiEndDate(sdf.parse((String)tTransferData.getValueByName("FeereceiEndDate")+" 00:00:00"));
			//账单费用项目编码
			if(!(((String)tTransferData.getValueByName("DutyFeeCode"))==null||((String)tTransferData.getValueByName("DutyFeeCode")).equals("")))
				claim.setDutyFeeCode((String)tTransferData.getValueByName("DutyFeeCode"));
			// 给付责任的赔付次数
			if(!(((String)tTransferData.getValueByName("ClaimCount"))==null||((String)tTransferData.getValueByName("ClaimCount")).equals("")))
				claim.setClaimCount(Double.valueOf((String)tTransferData.getValueByName("ClaimCount")));
			//共享保额的给付责任算出的理赔金,共享不同的保额
			if(!(((String)tTransferData.getValueByName("CurrentClassifiDutyPay"))==null||((String)tTransferData.getValueByName("CurrentClassifiDutyPay")).equals("")))
				claim.setCurrentClDutyPay(Double.valueOf((String)tTransferData.getValueByName("CurrentClassifiDutyPay")));
			// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任共享险种保额
			if(!(((String)tTransferData.getValueByName("CurrentDutyPay"))==null||((String)tTransferData.getValueByName("CurrentDutyPay")).equals("")))
				claim.setCurrentDutyPay(Double.valueOf((String)tTransferData.getValueByName("CurrentDutyPay")));
			//得到每个给付责任的累计赔付金额,适用于费用补偿型险种
			if(!(((String)tTransferData.getValueByName("CompensateDutySumPay"))==null||((String)tTransferData.getValueByName("CompensateDutySumPay")).equals("")))
				claim.setCompensateDutySumPay(Double.valueOf((String)tTransferData.getValueByName("CompensateDutySumPay")));
			//	得到每个给付责任的累计赔付金额,适用于除费用补偿型险种之外的所有险种
			if(!(((String)tTransferData.getValueByName("GetDutySumPay"))==null||((String)tTransferData.getValueByName("GetDutySumPay")).equals("")))
				claim.setGetDutySumPay(Double.valueOf((String)tTransferData.getValueByName("GetDutySumPay")));
			//未成年人限额
			if(!(((String)tTransferData.getValueByName("PupilAmnt"))==null||((String)tTransferData.getValueByName("PupilAmnt")).equals("")))
				claim.setPupilAmnt(Double.valueOf((String)tTransferData.getValueByName("PupilAmnt")));
			// 已部分领取的账户价值
			if(!(((String)tTransferData.getValueByName("SumAccGet"))==null||((String)tTransferData.getValueByName("SumAccGet")).equals("")))
				claim.setSumAccGet(Double.valueOf((String)tTransferData.getValueByName("SumAccGet")));
			// 有效保额
			if(!(((String)tTransferData.getValueByName("ValidAmnt"))==null||((String)tTransferData.getValueByName("ValidAmnt")).equals("")))
				claim.setValidAmnt(Double.valueOf((String)tTransferData.getValueByName("ValidAmnt")));//
			// 社保赔付比例
			if(!(((String)tTransferData.getValueByName("SocialInsuRate"))==null||((String)tTransferData.getValueByName("SocialInsuRate")).equals("")))
				claim.setSocialInsuRate(Double.valueOf((String)tTransferData.getValueByName("SocialInsuRate")));
			//合同号
			if(!(((String)tTransferData.getValueByName("ContNo"))==null||((String)tTransferData.getValueByName("ContNo")).equals("")))
				claim.setContNo((String)tTransferData.getValueByName("ContNo"));
			// LCGet的开始时间
			if(!(((String)tTransferData.getValueByName("LCGetStartDate"))==null||((String)tTransferData.getValueByName("LCGetStartDate")).equals("")))
				claim.setGetStartDate(sdf.parse((String)tTransferData.getValueByName("LCGetStartDate")+" 00:00:00"));
			if(!(((String)tTransferData.getValueByName("TotalLimit"))==null||((String)tTransferData.getValueByName("TotalLimit")).equals("")))
				claim.setTotalLimit(Double.valueOf((String)tTransferData.getValueByName("TotalLimit")));
			// LCGet的终止时间
			if(!(((String)tTransferData.getValueByName("LCGetEndDate"))==null||((String)tTransferData.getValueByName("LCGetEndDate")).equals("")))
				claim.setGetEndDate(sdf.parse((String)tTransferData.getValueByName("LCGetEndDate")+" 00:00:00"));
			
			// 利差返还后增加的保额
			if(!(((String)tTransferData.getValueByName("RateAmnt"))==null||((String)tTransferData.getValueByName("RateAmnt")).equals("")))
				claim.setRateAmnt(Double.valueOf((String)tTransferData.getValueByName("RateAmnt")));

			// 险种号
			if(!(((String)tTransferData.getValueByName("PolNo"))==null||((String)tTransferData.getValueByName("PolNo")).equals("")))
				claim.setPolNo((String)tTransferData.getValueByName("PolNo"));

//			复效到出险时已保天数
			if(!(((String)tTransferData.getValueByName("LRDays"))==null||((String)tTransferData.getValueByName("LRDays")).equals("")))
			if(Double.valueOf((String)tTransferData.getValueByName("LRDays"))!=0)
			{
				if(!(((String)tTransferData.getValueByName("LRDays"))==null||((String)tTransferData.getValueByName("LRDays")).equals("")))
				claim.setMLRDays(Double.valueOf((String)tTransferData.getValueByName("LRDays")));
				//复效到出险时已保年期
				if(!(((String)tTransferData.getValueByName("LRYears"))==null||((String)tTransferData.getValueByName("LRYears")).equals("")))
				claim.setMLRYears(Double.valueOf((String)tTransferData.getValueByName("LRYears")));
				//保单是否复效的标记 1复效 0无复效
				if(!(((String)tTransferData.getValueByName("LRFlag"))==null||((String)tTransferData.getValueByName("LRFlag")).equals("")))
				claim.setMLRFlag((String)tTransferData.getValueByName("LRFlag"));
			}
			//
//			claim.setCustomerNo((Double)tTransferData.getValueByName("CustomerNo"));
			//终交年龄年期

			// 事故号
			if(!(((String)tTransferData.getValueByName("CaseRelaNo"))==null||((String)tTransferData.getValueByName("CaseRelaNo")).equals("")))
				claim.setCaseRelaNo((String)tTransferData.getValueByName("CaseRelaNo"));
            
			//累计保费(包括健康加费和职业加费)
			if(!(((String)tTransferData.getValueByName("SumPrem"))==null||((String)tTransferData.getValueByName("SumPrem")).equals("")))
				claim.setSumPrem(Double.valueOf((String)tTransferData.getValueByName("SumPrem")));
			
			//累计增额红利 
			if(!(((String)tTransferData.getValueByName("Additionalbonus"))==null||((String)tTransferData.getValueByName("Additionalbonus")).equals("")))
				claim.setAdditionalbonus(Double.valueOf((String)tTransferData.getValueByName("Additionalbonus")));
			
			//首个生存保险金给付日 当日24时之前
			if(!(((String)tTransferData.getValueByName("BeFirstSurvivalBenefit"))==null||((String)tTransferData.getValueByName("BeFirstSurvivalBenefit")).equals("")))
				claim.setBeFirstSurvivalBenefit((String)tTransferData.getValueByName("BeFirstSurvivalBenefit"));
			
			//被保险人身故之日起至满期日未给付的生存保险金期数
			if(!(((String)tTransferData.getValueByName("NotPayOfAnnuity"))==null||((String)tTransferData.getValueByName("NotPayOfAnnuity")).equals("")))
				claim.setNotPayOfAnnuity(Double.valueOf((String)tTransferData.getValueByName("NotPayOfAnnuity")));
			
			//年满60周岁后的首个保单周年日24时之后
			if(!(((String)tTransferData.getValueByName("Af60YearsOfInsured"))==null||((String)tTransferData.getValueByName("Af60YearsOfInsured")).equals("")))
				claim.setAf60YearsOfInsured((String)tTransferData.getValueByName("Af60YearsOfInsured"));
			
			//年满60周岁后的首个保单周年日24时之前
			if(!(((String)tTransferData.getValueByName("Be60YearsOfInsured"))==null||((String)tTransferData.getValueByName("Be60YearsOfInsured")).equals("")))
				claim.setBe60YearsOfInsured((String)tTransferData.getValueByName("Be60YearsOfInsured"));
			
			
			
			//身故到满期之前的周年个数   yhy start
			if(!(((String)tTransferData.getValueByName("DieAndPolicyAnnDays"))==null||((String)tTransferData.getValueByName("DieAndPolicyAnnDays")).equals("")))
				claim.setDieAndPolicyAnnDays(Double.valueOf((String)tTransferData.getValueByName("DieAndPolicyAnnDays")));
			
			//身故前已领取金额
			if(!(((String)tTransferData.getValueByName("BD12FThePayment"))==null||((String)tTransferData.getValueByName("BD12FThePayment")).equals("")))
				claim.setBD12FThePayment(Double.valueOf((String)tTransferData.getValueByName("BD12FThePayment")));
			
			
			//end 
			
			
			
			//已交保费减去累计已给付的基本生存保险金
			if(!(((String)tTransferData.getValueByName("PremiumedMinusAnnuityed"))==null||((String)tTransferData.getValueByName("PremiumedMinusAnnuityed")).equals("")))
				claim.setPremiumedMinusAnnuityed(Double.valueOf((String)tTransferData.getValueByName("PremiumedMinusAnnuityed")));
			
			//首个生存保险金给付日 当日24时之后
			if(!(((String)tTransferData.getValueByName("AfFirstSurvivalBenefit"))==null||((String)tTransferData.getValueByName("AfFirstSurvivalBenefit")).equals("")))
				claim.setAfFirstSurvivalBenefit((String)tTransferData.getValueByName("AfFirstSurvivalBenefit"));
			
			// 累计红利保额
			if(!(((String)tTransferData.getValueByName("SumAmntBonus"))==null||((String)tTransferData.getValueByName("SumAmntBonus")).equals("")))
				claim.setSumAmntBonus(Double.valueOf((String)tTransferData.getValueByName("SumAmntBonus")));

			// 出险时已保整月数
			if(!(((String)tTransferData.getValueByName("Rgtmonths"))==null||((String)tTransferData.getValueByName("Rgtmonths")).equals("")))
				claim.setRgtmonths(Double.valueOf((String)tTransferData.getValueByName("Rgtmonths")));

			// 给付责任编码
			if(!(((String)tTransferData.getValueByName("GetDutyCode"))==null||((String)tTransferData.getValueByName("GetDutyCode")).equals("")))
				claim.setGetDutyCode((String)tTransferData.getValueByName("GetDutyCode"));

			// 给付责任类型
			if(!(((String)tTransferData.getValueByName("GetDutyKind"))==null||((String)tTransferData.getValueByName("GetDutyKind")).equals("")))
				claim.setGetDutyKind((String)tTransferData.getValueByName("GetDutyKind"));

			// 赔案号
			if(!(((String)tTransferData.getValueByName("CaseNo"))==null||((String)tTransferData.getValueByName("CaseNo")).equals("")))
				claim.setCaseNo((String)tTransferData.getValueByName("CaseNo"));

			// 出险人编号
			if(!(((String)tTransferData.getValueByName("InsuredNo"))==null||((String)tTransferData.getValueByName("InsuredNo")).equals("")))
				claim.setInsuredNo((String)tTransferData.getValueByName("InsuredNo"));

			// 出险时已保年期
			if(!(((String)tTransferData.getValueByName("RgtYears"))==null||((String)tTransferData.getValueByName("RgtYears")).equals("")))
				claim.setRgtYears(Double.valueOf((String)tTransferData.getValueByName("RgtYears")));


			// 出险时已保天数
			if(!(((String)tTransferData.getValueByName("RgtDays"))==null||((String)tTransferData.getValueByName("RgtDays")).equals("")))
				claim.setRgtDays(Double.valueOf((String)tTransferData.getValueByName("RgtDays")));

			if(!(((String)tTransferData.getValueByName("ObserveDate"))==null||((String)tTransferData.getValueByName("ObserveDate")).equals("")))
				claim.setObserveDate(Double.valueOf((String)tTransferData.getValueByName("ObserveDate")));
			
			if(!(((String)tTransferData.getValueByName("exemptMoney"))==null||((String)tTransferData.getValueByName("exemptMoney")).equals("")))
				claim.setExemptMoney(Double.valueOf((String)tTransferData.getValueByName("exemptMoney")));
			// 已交费年期,取自出险时点,需要考虑保全
			if(!(((String)tTransferData.getValueByName("AppAge"))==null||((String)tTransferData.getValueByName("AppAge")).equals("")))
				claim.setPaytoDatYears(Double.valueOf((String)tTransferData.getValueByName("AppAge")));


			// 被保人0岁保单生效对应日
			if(!(((String)tTransferData.getValueByName("InsuredvalidBirth"))==null||((String)tTransferData.getValueByName("InsuredvalidBirth")).equals("")))
				claim.setInsuredvalidBirth(sdf.parse((String)tTransferData.getValueByName("InsuredvalidBirth")+" 00:00:00"));

			// 计算给付金
			if(!(((String)tTransferData.getValueByName("Je_gf"))==null||((String)tTransferData.getValueByName("Je_gf")).equals("")))
				claim.setJegf(Double.valueOf((String)tTransferData.getValueByName("Je_gf")));

			// [重新计算]份数,取自出险时点,需要考虑保全
			/////claim.setMult((String)tTransferData.getValueByName("Mult"));

			// 交费间隔
			/////claim.setPayIntv((Double)tTransferData.getValueByName("PayIntv"));

			// 医疗费用序号
			if(!(((String)tTransferData.getValueByName("DutyFeeStaNo"))==null||((String)tTransferData.getValueByName("DutyFeeStaNo")).equals("")))
				claim.setDutyFeeStaNo((String)tTransferData.getValueByName("DutyFeeStaNo"));

			// 总保费[该要素将被删除],取自出险时点,需要考虑保全
			/////claim.setPrem((Double)tTransferData.getValueByName("Prem"));//


			// [重新计算]保单累计支付,取自出险时点,适用于责任下多个给付责任共享险种保额
			if(!(((String)tTransferData.getValueByName("PolPay"))==null||((String)tTransferData.getValueByName("PolPay")).equals("")))
				claim.setPolPay(Double.valueOf((String)tTransferData.getValueByName("PolPay")));

			// 医疗费用编码
			if(!(((String)tTransferData.getValueByName("DutyFeeCode"))==null||((String)tTransferData.getValueByName("DutyFeeCode")).equals("")))
				claim.setDutyFeeCode((String)tTransferData.getValueByName("DutyFeeCode"));
			
			// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
			if(!(((String)tTransferData.getValueByName("Amnt"))==null||((String)tTransferData.getValueByName("Amnt")).equals("")))
				claim.setAmnt(Double.valueOf((String)tTransferData.getValueByName("Amnt")));
			
			// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额不一致时(既lcget的standmoney不一致)时
			if(!(((String)tTransferData.getValueByName("MaxAmnt"))==null||((String)tTransferData.getValueByName("MaxAmnt")).equals("")))
				claim.setMaxAmnt(Double.valueOf((String)tTransferData.getValueByName("MaxAmnt")));

			// [重新计算]初始基本保额,取自出险时点,需要考虑保全
			if(!(((String)tTransferData.getValueByName("Oamnt"))==null||((String)tTransferData.getValueByName("Oamnt")).equals("")))
				claim.setOamnt(Double.valueOf((String)tTransferData.getValueByName("Oamnt")));

			// 交费年期
			/////claim.setPayYears((Double)tTransferData.getValueByName("PayYears"));//

			// 出险时年龄
			if(!(((String)tTransferData.getValueByName("GetAge"))==null||((String)tTransferData.getValueByName("GetAge")).equals("")))
				claim.setGetAge(Double.valueOf((String)tTransferData.getValueByName("GetAge")));
			
			//身故时上一保单周年日领取的养老年金金额 
			if(!(((String)tTransferData.getValueByName("AnnAmntBeforeDeath"))==null||((String)tTransferData.getValueByName("AnnAmntBeforeDeath")).equals("")))
				claim.setAnnAmntBeforeDeath(Double.valueOf((String)tTransferData.getValueByName("AnnAmntBeforeDeath")));
			
			//已给付养老年金的保单年度个数
			if(!(((String)tTransferData.getValueByName("YearsOfAnnitied"))==null||((String)tTransferData.getValueByName("YearsOfAnnitied")).equals("")))
				claim.setYearsOfAnnitied(Double.valueOf((String)tTransferData.getValueByName("YearsOfAnnitied")));
			

			// 责任代码
			if(!(((String)tTransferData.getValueByName("DutyCode"))==null||((String)tTransferData.getValueByName("DutyCode")).equals("")))
				claim.setDutyCode((String)tTransferData.getValueByName("DutyCode"));

//			 出险原因
			if(!(((String)tTransferData.getValueByName("AccidentDate"))==null||((String)tTransferData.getValueByName("AccidentDate")).equals("")))
				claim.setAccDate(sdf.parse((String)tTransferData.getValueByName("AccidentDate")+" 00:00:00"));

			// 终了红利
			if(!(((String)tTransferData.getValueByName("FinalBonus"))==null||((String)tTransferData.getValueByName("FinalBonus")).equals("")))
				claim.setFinalBonus(Double.valueOf((String)tTransferData.getValueByName("FinalBonus")));

			// [重新计算]投保人职业类别
			//    claim.setOccupationType((String)tTransferData.getValueByName("OccupationType"));
			// 住院天数
			if(!(((String)tTransferData.getValueByName("DaysInHos"))==null||((String)tTransferData.getValueByName("DaysInHos")).equals("")))
				claim.setDaysInHos(Double.valueOf((String)tTransferData.getValueByName("DaysInHos")));

			// 保险结束日期
			if(!(((String)tTransferData.getValueByName("EndPolDate"))=="null"||((String)tTransferData.getValueByName("EndPolDate"))==null||((String)tTransferData.getValueByName("EndPolDate")).equals("")))
				claim.setEndPolDate(sdf.parse((String)tTransferData.getValueByName("EndPolDate")+" 00:00:00"));

			// 费用开始日期
			if(!(((String)tTransferData.getValueByName("StartFeeDate"))=="null"||((String)tTransferData.getValueByName("StartFeeDate")).equals("")))
				claim.setStartFeeDate(sdf.parse((String)tTransferData.getValueByName("StartFeeDate")+" 00:00:00"));

			// 费用结束日期
			if(!(((String)tTransferData.getValueByName("EndFeeDate"))=="null"||((String)tTransferData.getValueByName("EndFeeDate"))==null||((String)tTransferData.getValueByName("EndFeeDate")).equals("")))
				claim.setEndFeeDate(sdf.parse((String)tTransferData.getValueByName("EndFeeDate")+" 00:00:00"));

			// 伤残比例
			if(!(((String)tTransferData.getValueByName("DefoRate"))==null||((String)tTransferData.getValueByName("DefoRate")).equals("")))
				claim.setDefoRate(Double.valueOf((String)tTransferData.getValueByName("DefoRate")));

			// 每天床位费
			if(!(((String)tTransferData.getValueByName("InHospdayFee"))==null||((String)tTransferData.getValueByName("InHospdayFee")).equals("")))
				claim.setInHospdayFee(Double.valueOf((String)tTransferData.getValueByName("InHospdayFee")));

			// 治疗类型（B为住院治疗；A为门诊治疗）
			if(!(((String)tTransferData.getValueByName("CureType"))==null||((String)tTransferData.getValueByName("CureType")).equals("")))
				claim.setCureType((String)tTransferData.getValueByName("CureType"));

			// [重新计算]保费（包括健康加费和职业加费及出险时点的保全补退费）,取自出险时间,需要考虑保全
			if(!(((String)tTransferData.getValueByName("TotalPrem"))==null||((String)tTransferData.getValueByName("TotalPrem")).equals("")))
				claim.setTotalPrem(Double.valueOf((String)tTransferData.getValueByName("TotalPrem")));
			// [重新计算]起领日期,取自出险时点,需要考虑保全
			/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
			if(!(((String)tTransferData.getValueByName("GetStartDate"))==null||((String)tTransferData.getValueByName("GetStartDate")).equals("")))
				claim.setGetStartDate(sdf.parse((String)tTransferData.getValueByName("GetStartDate")+" 00:00:00"));
			//连带被保人每个给付责任累计赔付额
			if(!(((String)tTransferData.getValueByName("GetDutyRelateSumPay"))==null||((String)tTransferData.getValueByName("GetDutyRelateSumPay")).equals("")))
				claim.setGetDutyRelateSumPay(Double.valueOf((String)tTransferData.getValueByName("GetDutyRelateSumPay")));
			if(!(((String)tTransferData.getValueByName("CurrentRelateDutyPay"))==null||((String)tTransferData.getValueByName("CurrentRelateDutyPay")).equals("")))
				claim.setCurrentRelateDutyPay(Double.valueOf((String)tTransferData.getValueByName("CurrentRelateDutyPay")));
			// 赔案号
			if(!(((String)tTransferData.getValueByName("ClmNo"))==null||((String)tTransferData.getValueByName("ClmNo")).equals("")))
				claim.setClmNo((String)tTransferData.getValueByName("ClmNo"));
			//续保次数,用于医疗类附加险计算
			if(!(((String)tTransferData.getValueByName("RenewCount"))==null||((String)tTransferData.getValueByName("RenewCount")).equals("")))
				claim.setRenewCount(Double.valueOf((String)tTransferData.getValueByName("RenewCount")));
			//出险细节，用于过滤像397这样多责任的险种
			if(!(((String)tTransferData.getValueByName("AccidentDetail"))==null||((String)tTransferData.getValueByName("AccidentDetail")).equals("")))
				claim.setAccidentDetail((String)tTransferData.getValueByName("AccidentDetail"));
			//死亡日期,也就是出险时间
			if(!(((String)tTransferData.getValueByName("DeathDate"))==null||((String)tTransferData.getValueByName("DeathDate")).equals("")))
				claim.setDeathDate(sdf.parse((String)tTransferData.getValueByName("DeathDate")+" 00:00:00"));
			ExeSQL riskSql = new ExeSQL();
			if(((String)tTransferData.getValueByName("PolNo"))!=null && !((String)tTransferData.getValueByName("PolNo")).equals("")&&claim.getInsureAccBalance()!=null)
			{
				//身故和全殊帐户保险金
				String deadDisabAccInsSQL="select case when "+"?age?"+">=18 then "+"?amnt?"+" + "+"?insaccbalance?"+" +( select (case when sum(money) is null then 0 else sum(money) end) from lpinsureacctrace "
								+ "where edorno='"+"?caseno?"+"')+(select (case when sum(fee) is null then 0 else sum(fee) end) from lpinsureaccfeetrace where edorno='"+"?caseno?"+"') "
								+ "Else (select greatest ( ("+"?sumprem?"+"-(select (case when sum(SumPaym) is null then 0 else sum(SumPaym) end) from lcinsureacc where polno='"+"?polno?"+"'))"
								+","+"?insaccbalance?"+" +( select (case when sum(money) is null then 0 else sum(money) end) from lpinsureacctrace where edorno='"+"?caseno?"+"')+(select (case when sum(fee) is null then 0 else sum(fee) end)"
								+" from lpinsureaccfeetrace where edorno='"+"?caseno?"+"')) from dual) end from dual";
				logger.debug(deadDisabAccInsSQL); 
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(deadDisabAccInsSQL);
				sqlbv.put("age", claim.getGetAge());
				sqlbv.put("amnt", claim.getAmnt());
				sqlbv.put("insaccbalance", claim.getInsureAccBalance());
				sqlbv.put("caseno", claim.getCaseNo());
				sqlbv.put("sumprem", claim.getSumPrem());
				sqlbv.put("polno", claim.getPolNo());
				SSRS deadDisabAccIns = riskSql.execSQL(sqlbv);
				claim.setDeadDisabAccIns(Double.valueOf(deadDisabAccIns.GetText(1, 1)));
			}
			ExeSQL tExeSQL = new ExeSQL();
			String sumMoneySql="select summoney from lcget where polno = '"+"?polno?"+"' and getdutycode = '610206'";
			logger.debug(sumMoneySql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sumMoneySql);
			sqlbv1.put("polno", claim.getPolNo());
			String sumMoney= tExeSQL.getOneValue(sqlbv1);
			if(!(sumMoney.equals("")||sumMoney==null))
			{
				claim.setSumMoney(Double.valueOf(sumMoney));
			}
			

			//险种GLS类储蓄保险计划身故保险金和Death benefit
			if(tRiskCode.equals("GLSN")||tRiskCode.equals("GLS2"))
			{
				String RiskGLSYSql="select case when "+"?rgtyears?"+"<1 then (select (case when sum(SumActuPayMoney) is null then 0 else sum(SumActuPayMoney) end) from ljapayperson where polno='"+"?polno?"+"')*1.1 when "+"?rgtyears?"+">=1 then greatest("+"?amnt?"+",?CashValue?) end from dual";
				logger.debug(RiskGLSYSql);
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(RiskGLSYSql);
				sqlbv2.put("polno", claim.getPolNo());
				sqlbv2.put("rgtyears", claim.getRgtYears());
				sqlbv2.put("amnt", claim.getAmnt());
				String RiskGLSYDeathBenefit = tExeSQL.getOneValue(sqlbv2);
				claim.setRiskGLSYDeathBenefit(Double.valueOf(RiskGLSYDeathBenefit));
			}
			
			String RealAdjSumBSql="select sum(adjsum-selfamnt) from  llcasereceipt where clmno='"+"?caseno?"+"' and customerno='"+"?insuredno?"+"' and feeitemtype='B'";
			logger.debug(RealAdjSumBSql);
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(RealAdjSumBSql);
			sqlbv3.put("caseno", claim.getCaseNo());
			sqlbv3.put("insuredno", claim.getInsuredNo());
			String RealAdjSumB = tExeSQL.getOneValue(sqlbv3);
			//自付金额后的实际参加理算的住院金额
			if(!(RealAdjSumB.equals("")||RealAdjSumB==null))
			{
				claim.setRealAdjSumB(Double.valueOf(RealAdjSumB));
			}
			//自付金额后的实际参加理算的门诊金额
			String RealAdjSumASql="select sum(adjsum - selfamnt)from llcasereceipt where clmno = '"+"?caseno?"+"' and customerno = '"+"?insuredno?"+"' and feeitemtype = 'A'";
			logger.debug(RealAdjSumASql);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(RealAdjSumASql);
			sqlbv4.put("caseno", claim.getCaseNo());
			sqlbv4.put("insuredno", claim.getInsuredNo());
			String RealAdjSumA= tExeSQL.getOneValue(sqlbv4);
			if(!(RealAdjSumA.equals("")||RealAdjSumA==null))
			{
				claim.setRealAdjSumA(Double.valueOf(RealAdjSumA));
			}
			
			if(tRiskCode.equals("112208"))
			{
				//意外身故保险金
				String AcciDeathInsuraSql="select ("+"?jegf?"+"-"+"?dutysumpay?"+" -(select (case when sum(a.realpay) is null then 0.0 else sum(a.realpay) end)  from llclaimpolicy a, lcpol b where clmno <> '"+"?caseno?"+"' and a.polno = b.polno and b.mainpolno = '"+"?polno?"+"' and b.polno <> '"+"?polno?"+"'   and b.riskcode = '121501' and exists (select 1  from dual where (select add_months((case when MedAccDate is not null then MedAccDate else AccDate end), '-12')  from LLCase where caseno = a.clmno) > b.cvalidate))) from LDSysVar where SysVar = 'onerow'";
				logger.debug(AcciDeathInsuraSql);
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(AcciDeathInsuraSql);
				sqlbv5.put("jegf", claim.getJegf());
				sqlbv5.put("dutysumpay", claim.getGetDutySumPay());
				sqlbv5.put("caseno", claim.getCaseNo());
				sqlbv5.put("polno", claim.getPolNo());
				String AcciDeathInsura= tExeSQL.getOneValue(sqlbv5);
				claim.setAcciDeathInsura(Double.valueOf(AcciDeathInsura));
				//疾病身故保险金
				String DiseaDeathInsuraSql="select "+"?sumprem?"+" from LDSysVar where SysVar = 'onerow' and "+"?rgtyears?"+" < 1 union select ("+"?jegf?"+" - "+"?dutysumpay?"+" - (select (case when sum(a.realpay) is null then 0.0 else sum(a.realpay) end) from llclaimpolicy a,lcpol b where  clmno<>'"+"?caseno?"+"' and  a.polno=b.polno and b.mainpolno= '"+"?polno?"+"' and b.polno <> '"+"?polno?"+"' and b.riskcode = '121501' and exists(select 1 from dual where (select add_months((case when MedAccDate is null then AccDate else MedAccDate end),'-12')  from LLCase where caseno = a.clmno) > b.cvalidate)) ) from LDSysVar where SysVar = 'onerow' and "+"?rgtyears?"+" >= 1";
				logger.debug(DiseaDeathInsuraSql);
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(DiseaDeathInsuraSql);
				sqlbv6.put("polno", claim.getPolNo());
				sqlbv6.put("rgtyears", claim.getRgtYears());
				sqlbv6.put("dutysumpay", claim.getGetDutySumPay());
				sqlbv6.put("jegf", claim.getJegf());
				sqlbv6.put("caseno", claim.getCaseNo());
				sqlbv6.put("sumprem", claim.getSumPrem());
				String DiseaDeathInsura= tExeSQL.getOneValue(sqlbv6);
				claim.setDiseaDeathInsura(Double.valueOf(DiseaDeathInsura));
			}
			//MmaKind
//			String RealAdjSumHSql="select nvl(sum(LLCaseReceipt.Fee),0) from LLCaseReceipt  where caseno='?CaseNo?'  and feeitemtype='H' and feeitemcode like'WE%'  and customerno='?CustomerNo?'";
//			String RealAdjSumH= tExeSQL.getOneValue(RealAdjSumHSql);
//			claim.setRealAdjSumH(Double.valueOf(RealAdjSumH));
			
			

	}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
		return claim;
	}

	private BOMCont DealBOMCont(LCContSchema tLCContSchema,TransferData tTransferData){
		BOMCont cont = new BOMCont();
		try{
			//保单号码
			cont.setContNo(tLCContSchema.getContNo());
			//保费
			cont.setPrem(new Double(mLCContSchema.getPrem()));
			//份数
			cont.setMult(new Double(mLCContSchema.getMult()));
			//保额
			cont.setAmnt(Double.valueOf(tLCContSchema.getAmnt()));
			//自动垫交标记
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			//银行账号
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			//帐户名
			cont.setAccName(tLCContSchema.getAccName());
			//开户行
			cont.setBankCode(tLCContSchema.getBankCode());
			//卡单标记
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setSellType(tLCContSchema.getSellType());//出单方式
			//终止日期
			if(!((tEndDate==null)||"".equals(tEndDate))){
				tEndDate = tEndDate+" "+" 00:00:00";
				cont.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			if(!(tLCContSchema.getCValiDate()==null||"".equals(tLCContSchema.getCValiDate()))){
				theDate=tLCContSchema.getCValiDate()+" 00:00:00";
				cont.setCvalidate(sdf.parse(theDate));
			}
			//被保人数目
			cont.setInsuredPeoples(Double.valueOf(String.valueOf(insuredCount)));
			if(!((tLCContSchema.getMakeDate()==null)||"".equals(tLCContSchema.getMakeDate()))){
				theDate = tLCContSchema.getMakeDate()+" "+tLCContSchema.getMakeTime();
				cont.setMakeDate(sdf.parse(theDate));
			}
			//管理机构
			cont.setManageCom(tLCContSchema.getManageCom());
			//需要考虑加在程序里的函数。
			String tPayIntv=String.valueOf(hLCDutySchema.getPayIntv());
			//交费间隔
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				cont.setPayIntv("0");
			}
			else
			{
				cont.setPayIntv(tPayIntv);
			}
			
			cont.setPayIntv((String)tTransferData.getValueByName("PayIntv"));
			String tNewPayMode = tLCContSchema.getNewPayMode();
			String tPayMode = tLCContSchema.getPayLocation();
			String tAccName = "";
			if(tNewPayMode!=null&&tNewPayMode.equals("0")){
				cont.setPayMode("0");
				tAccName = tLCContSchema.getNewAccName();
			}else if(tPayMode!=null&&tPayMode.equals("0")) {
				 cont.setPayMode("0");
				 tAccName = tLCContSchema.getAccName();
			}
			else
			{
				cont.setPayMode(tLCContSchema.getPayMode());
			}
			if(!((tLCContSchema.getPolApplyDate()==null)||"".equals(tLCContSchema.getPolApplyDate()))){
				theDate=tLCContSchema.getPolApplyDate()+" 00:00:00";
				cont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			if(!(tLCContSchema.getRemark()==null||"".equals(tLCContSchema.getRemark()))){
				cont.setRemarkFlag("1");
			}else{
				cont.setRemarkFlag("0");
			}
			//自动续保标记
			cont.setRnewFlag((String)tTransferData.getValueByName("AppFlag"));
			cont.setAppFlag(tLCContSchema.getAppFlag());
			//销售渠道
			cont.setSaleChnl(tLCContSchema.getSaleChnl());
			cont.setSecondaryManagecom(getManagetCom(tLCContSchema.getManageCom(),4));//二级机构
			cont.setThirdStageManagecom(getManagetCom(tLCContSchema.getManageCom(),6));//三级机构
			cont.setFourStageManagecom(getManagetCom(tLCContSchema.getManageCom(),8));//四级机构
			cont.setFirstTrialDate(sdf.parse(tLCContSchema.getFirstTrialDate()+ " "+ "00:00:00"));//合同的【初审日期】
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema,LCContSchema tLCContSchema,TransferData tTransferData){
		BOMInsured insured= new  BOMInsured();
		try{
			insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			ExeSQL tExeSQL2 = new ExeSQL();			
			
			if(!((tLCInsuredSchema.getBirthday()==null))||"".equals(tLCInsuredSchema.getBirthday())){
					theDate=tLCInsuredSchema.getBirthday()+" 00:00:00";
					insured.setBirthday(sdf.parse(theDate));
			}

			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			//被保人投保年龄
			insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));
			if(!((tLCInsuredSchema.getJoinCompanyDate()==null))||"".equals(tLCInsuredSchema.getJoinCompanyDate())){
				theDate=tLCInsuredSchema.getJoinCompanyDate()+" 00:00:00";
				insured.setJoinCompanyDate(sdf.parse(theDate));
			}
			//驾照
			insured.setLicense(tLCInsuredSchema.getLicense());
			//驾照类型
			insured.setLicenseType(tLCInsuredSchema.getLicenseType());
			//婚姻状况
			insured.setMarriage(tLCInsuredSchema.getMarriage());
			//婚姻日期
			if(!(tLCInsuredSchema.getMarriageDate()==null||"".equals(tLCInsuredSchema.getMarriageDate()))){
				theDate=tLCInsuredSchema.getMarriageDate()+" 00:00:00";
				insured.setMarriageDate(sdf.parse(theDate));
			}

			insured.setCreditGrade(tLCInsuredSchema.getCreditGrade());
			
			insured.setDegree(tLCInsuredSchema.getDegree());
			//民族
			insured.setNationality(tLCInsuredSchema.getNationality());
			//国籍
			insured.setNativePlace(tLCInsuredSchema.getNativePlace());
			//职位
			insured.setPosition(tLCInsuredSchema.getPosition());
			//职业类型
			insured.setOccupationType(tLCInsuredSchema.getOccupationType());
			//职业代码
			insured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
			//与投保人关系
			insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			//户口所在地
			insured.setRgtAddress(tLCInsuredSchema.getRgtAddress());
			//工资
			insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			//性别
			insured.setSex(tLCInsuredSchema.getSex());

		}catch(Exception e){
			CError.buildErr(this, "准备BOMInsured出错！");
			e.printStackTrace();
		}
		return insured;
	}
	
	
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema,BOMInsured insureds,LCDutySchema tLCDutySchema,TransferData tTransferData){
		BOMPol pol = new BOMPol();//多个险种
		try{
			ExeSQL tempExeSQL = new ExeSQL();
			boolean FreeFlag = false;
			BOMInsured tCurrentInsured = new BOMInsured();
			BOMInsured insured = new BOMInsured();
			insured=insureds;
		    if(insured.getInsuredNo().equals(tLCPolSchema.getInsuredNo())){
				pol.setFatherBOM(insured);
				tCurrentInsured = insured;
			}
				
			//总基本保额	
			pol.setAmnt(Double.valueOf(String.valueOf(tLCDutySchema.getAmnt())));
			//红利金领取方式
			pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			//红利领取人类型
			pol.setBonusManType(tLCPolSchema.getBonusMan());
			//险种生效日期
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				pol.setCValiDate(sdf.parse(theDate));
			}
			if(!(((String)tTransferData.getValueByName("PayEndDate"))==null||((String)tTransferData.getValueByName("PayEndDate")).equals("")))
				pol.setPayEndDate(sdf.parse((String)tTransferData.getValueByName("PayEndDate")+" 00:00:00"));
			if(!(((String)tTransferData.getValueByName("SumPrem"))==null||((String)tTransferData.getValueByName("SumPrem")).equals("")))
				pol.setSumPrem(new Double((String)tTransferData.getValueByName("SumPrem")));
			if(!(((String)tTransferData.getValueByName("Prem"))==null||((String)tTransferData.getValueByName("Prem")).equals("")))
				pol.setPrem(new Double(String.valueOf(tTransferData.getValueByName("Prem"))));
			//浮动费率
			pol.setFloatRate(new Double(tLCDutySchema.getFloatRate()));
			//被保人号码
			pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			//保险期间
			pol.setInsuYear(Double.valueOf(String.valueOf(tLCDutySchema.getInsuYear())));
			pol.setAppFlag(tLCPolSchema.getAppFlag());
			//保险年期	
			if(!(((String)tTransferData.getValueByName("Years"))==null||((String)tTransferData.getValueByName("Years")).equals("")))
				pol.setYears(new Double((String)tTransferData.getValueByName("Years")));

			//现金价值  
			if(!(((String)tTransferData.getValueByName("CashValue"))==null||((String)tTransferData.getValueByName("CashValue")).equals("")))
				pol.setCashValue(new Double((String)tTransferData.getValueByName("CashValue")));
			
			//累计该险种保费
			if(!(((String)tTransferData.getValueByName("SumThisPrem"))==null||((String)tTransferData.getValueByName("SumThisPrem")).equals("")))
				pol.setSumThisPrem(new Double((String)tTransferData.getValueByName("SumThisPrem")));
			
			//yhy  2011-11-11  总保费减去已支付年金  Start
			if(!(((String)tTransferData.getValueByName("PremiumDeductedAnnuity"))==null||((String)tTransferData.getValueByName("PremiumDeductedAnnuity")).equals("")))
				pol.setPremiumDeductedAnnuity(new Double(String.valueOf(tTransferData.getValueByName("PremiumDeductedAnnuity"))));
			//end 
			
			
			//交费期间
			pol.setPayYears(new Double(tLCDutySchema.getPayYears()));
			if(!(((String)tTransferData.getValueByName("PayYears"))==null||((String)tTransferData.getValueByName("PayYears")).equals("")))
				pol.setPayYears(new Double((String)tTransferData.getValueByName("PayYears")));
			//保险期间单位
			pol.setInsuYearFlag(tLCDutySchema.getInsuYearFlag());
			//终交年龄年期
			if(!(((String)tTransferData.getValueByName("PayEndYear"))==null||((String)tTransferData.getValueByName("PayEndYear")).equals("")))
				pol.setPayEndYear(new Double((String)tTransferData.getValueByName("PayEndYear")));
			//终交年龄年期标志
			pol.setPayEndYearFlag(tLCDutySchema.getPayEndYearFlag());
			//起领期间
			pol.setGetYear(new Double(tLCDutySchema.getGetYear()));
			//起领期间单位
			pol.setGetYearFlag(tLCDutySchema.getGetYearFlag());
			//生存金领取方式
			pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			//主险号码
			pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			//总份数
			pol.setMult(new Double(tLCDutySchema.getMult()));
			if(!(((String)tTransferData.getValueByName("Mult"))==null||((String)tTransferData.getValueByName("Mult")).equals("")))
			pol.setMult(new Double((String)tTransferData.getValueByName("Mult")));
			pol.setPeakLine(tLCDutySchema.getPeakLine());
			pol.setGetLimit(tLCDutySchema.getGetLimit());
			//险种号码
			pol.setPolNo(tLCPolSchema.getPolNo());
			pol.setInsuredPeoples(new Double(tLCPolSchema.getInsuredPeoples()));
			//保费
			pol.setPrem(new Double(tLCDutySchema.getPrem()));
			if(!(((String)tTransferData.getValueByName("StandPrem"))==null||((String)tTransferData.getValueByName("StandPrem")).equals("")))
			pol.setStandPrem(new Double((String)tTransferData.getValueByName("StandPrem")));
			//险种编码
			pol.setRiskCode(tLCPolSchema.getRiskCode());
			//停售标记
			pol.setStopFlag(tLCPolSchema.getStopFlag());
			//赔付比例
			pol.setGetRate(tLCDutySchema.getGetRate());
			if(!(((String)tTransferData.getValueByName("MainPolNo"))==null||((String)tTransferData.getValueByName("MainPolNo")).equals("")))
				pol.setMainPolNo((String)tTransferData.getValueByName("MainPolNo"));
			//单位保额
			if(!(tLCDutySchema.getDutyCode()==null||tLCDutySchema.equals("")))
			{
				LMDutySchema tLMDutySchema = mCRI.findDutyByDutyCodeClone(tLCDutySchema
					.getDutyCode().substring(0, 6));
				if (tLMDutySchema == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError.buildErr(this, "LMDuty表查询失败!");
				}
			}
			pol.setDutyCode(tLCDutySchema.getDutyCode());
			if(!(((String)tTransferData.getValueByName("VPU"))==null||((String)tTransferData.getValueByName("VPU")).equals("")))
				pol.setVPU(new Double((String)tTransferData.getValueByName("VPU")));
			//币别
			pol.setCurrency(tLCPolSchema.getCurrency());
			//备用1
			pol.setStandbyFlag1(tLCDutySchema.getStandbyFlag1());
			//备用2
			pol.setStandbyFlag2(tLCDutySchema.getStandbyFlag2());
			//备用3
			pol.setStandbyFlag3(tLCDutySchema.getStandbyFlag3());
			// 给付责任编码
			if(!(((String)tTransferData.getValueByName("GetDutyCode"))==null||((String)tTransferData.getValueByName("GetDutyCode")).equals("")))
				pol.setGetDutyCode((String)tTransferData.getValueByName("GetDutyCode"));
			ExeSQL tExeSQL = new ExeSQL();

	}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
		return pol;
	}
	
	
	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public float parseFloat(String s) {
		if (s.length() < 0 || s.equals("")) {
			return 0;
		}
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		return f1;
	}

	private double getStandPrem(double Prem,double FlooRate)
	{

		 if(Prem <= 200)
		     return 200;
		 else if(Prem <= 400 && Prem>200)
			 return 400;
		 else if (Prem <= 800 && Prem>400)
		  	 return 800;
		 
		 return 0;
	}

	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema,LCContSchema tLCContSchema,TransferData tTransferData){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			int tAppAge = PubFun.calInterval2(tLCAppntSchema.getAppntBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			appnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLCAppntSchema.getAppntBirthday()==null))||"".equals(tLCAppntSchema.getAppntBirthday())){
				theDate=tLCAppntSchema.getAppntBirthday()+" 00:00:00";
				appnt.setAppntBirthday(sdf.parse(theDate));
			}
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());			
			String tBlackList="select (case when BlacklistFlag is null then '0' else BlacklistFlag end) from ldperson where customerno='"+"?customerno?"+"'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tBlackList);
			sqlbv7.put("customerno", tLCAppntSchema.getAppntNo());
			String tBlackFlag = tempExeSQL.getOneValue(sqlbv7);
			
			appnt.setBlackListFlag(tBlackFlag);//黑名单标记
			appnt.setCreditGrade(tLCAppntSchema.getCreditGrade());
			appnt.setDegree(tLCAppntSchema.getDegree());			


			if(!((tLCAppntSchema.getJoinCompanyDate()==null)||"".equals(tLCAppntSchema.getJoinCompanyDate()))){
				theDate=tLCAppntSchema.getJoinCompanyDate()+" 00:00:00";
				appnt.setJoinCompanyDate(sdf.parse(theDate));
			}
			appnt.setMarriage(tLCAppntSchema.getMarriage());
			if(!((tLCAppntSchema.getMarriageDate()==null))||"".equals(tLCAppntSchema.getMarriageDate())){
				theDate=tLCAppntSchema.getMarriageDate()+" 00:00:00";
				appnt.setMarriageDate(sdf.parse(theDate));
			}
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setOccupationType((String)tTransferData.getValueByName("OccupationType"));
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			if(!((tLCAppntSchema.getStartWorkDate()==null))||"".equals(tLCAppntSchema.getStartWorkDate())){
				theDate=tLCAppntSchema.getStartWorkDate()+" 00:00:00";
				appnt.setStartWorkDate(sdf.parse(theDate));
			}
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	/**
	 * 取保单管理机构的前*位
	 * 
	 * 
	 * 如果个人保单的管理机构为6位时默认在后面补“4”(反正系统里面不会有以两个4结尾的机构，
	 * 所以就用4补齐了-_-||)
	 * 
	 * */
	private String getManagetCom(String tManageCom,int tLength){
		int strLen = tManageCom.length();

		StringBuffer strReturn = new StringBuffer();
		if (strLen > tLength) {
			strReturn.append(tManageCom.substring(0, tLength));
		} else {
			if (strLen == 0) {
				strReturn.append("");
			} else {
				strReturn.append(tManageCom);
			}

			for (int i = strLen; i < tLength; i++) {
				strReturn.append("4");
			}
		}
		return strReturn.toString();
	}
}
