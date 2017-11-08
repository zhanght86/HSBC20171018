/*
 * @(#)LCContSignBL.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBContDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.AgentPubFun;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LBContSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 合同签单处理类</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft</p>
 * @author wujs
 * @version 6.0
 */

public class LCContSignBL {
private static Logger logger = Logger.getLogger(LCContSignBL.class);
	public LCContSignBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 存放结果
	 */
	public VData mVResult = new VData();

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();

	/**
	 * 往后面传输数据的容器
	 */
	private VData mInputData;

	/**
	 * 全局数据
	 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSet mLCContSet = null;
	private final String INSERT = "INSERT";
	private final String UPDATE = "UPDATE";
	private final String DELETE = "DELETE";
	private final int ERROR = 100;
	private boolean isPlanMark = false; // 产品组合标志 用于团体产品组合卡单非结算签单
	private String mContPlanCode = ""; // 组合编码
	private String mBalFlag; // 结算标志<用于判断是否结算签单>
	private String mBalanceNo; // 结算号<用于结算签单>
	private SimpleDateFormat tSDF = new SimpleDateFormat("yyyy-MM-dd");
	private String mark; // add by yaory
	private String CardContNo; // add by zhangxing
	private String mSaleChnl;

	// 交费日期相关
	private Date mFirstPayDate = null;
	private Date maxPayDate = null;
	private Date maxEnterAccDate = null;
	TransferData mTransferData;
	// 当前时间
	private String mCurrentDate;
	private String mCurrentTime;

	// 记录已有交费记录
	private HashMap mLJTempFeeMap = new HashMap();

	// 保单表
	LCPolSchema mLCPolSchema = new LCPolSchema();
	LCContStateSet mLCContStateSet = new LCContStateSet();

	// 保单表
	LCPolSet mLCPolSet = new LCPolSet();

	// 被保险人客户号
	private String mInsuredNo;
	private String KDCheckFlag = "0";//自助卡单标记，1--自助卡单，0--正常保单
	private String ECheckFlag="0";//电子商务标记，1--电子商务，0--其它保单

	// 被保人年龄变化标志
	private String mInsuredAppAgeChangeFlag;
	private FDate fDate = new FDate();

	/**
	 * 险种保单签单类
	 */
	private ProposalSignBL proposalSignBL = new ProposalSignBL();
	private LCInsureAccClassSet mLCInsureAccClassSet;
	private LCInsureAccTraceSet mLCInsureAccTraceSet;
	private LCInsureAccFeeSet mLCInsureAccFeeSet;
	private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet;
	private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet;
	
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC0002")) {
			return false;
		}
		return true;
	}
	public VData getResult() {
		return this.mVResult;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (this.getInputData() == false) {
			return false;
		}
		 // 日志监控,过程监控
		PubFun.logTrack (mGlobalInput,mLCContSet.get(1).getContNo(),"保单"+mLCContSet.get(1).getContNo()+"签单开始");
		// 数据操作业务处理
		if (this.signLCContPol(mLCContSet) == false) {
			PubFun.logState(mGlobalInput,mLCContSet.get(1).getContNo(),"保单"+mLCContSet.get(1).getContNo()+"签单失败","0");
			PubFun.logTrack (mGlobalInput,mLCContSet.get(1).getContNo(),"保单"+mLCContSet.get(1).getContNo()+"签单结束");
			return false;
		}
		PubFun.logState (mGlobalInput,mLCContSet.get(1).getContNo(),"保单"+mLCContSet.get(1).getContNo()+"签单成功","1");
		PubFun.logTrack (mGlobalInput,mLCContSet.get(1).getContNo(),"保单"+mLCContSet.get(1).getContNo()+"签单结束");
		return true;
	}

	/**
	 * 对合同进行签单， 团险卡单签单和个险相同处理
	 * 
	 * @param globalInput
	 *            GlobalInput
	 * @param inLCContSet
	 *            LCContSet
	 * @return boolean 修改
	 */
	private boolean signLCContPol(LCContSet inLCContSet) {
		this.mVResult = new VData();
		MMap contMap = new MMap();
		LCContSchema tLCContSchema = null;
		LCPolDB tLCPolDB = null;
		LCPolSet tLCPolSet = null;
		LCPolSchema tPolSchema = null;
		LCContDB tContDB = null;
		VData bufferData = new VData();
		int signCount = 0;
		if (inLCContSet == null || inLCContSet.size() <= 0) {
			CError.buildErr(this, "没有传入要签单的数据");
			return false;
		}
		/** 以一个合同为单位签单 */
		for (int i = 1; i <= inLCContSet.size(); i++) {
			String tPrtNo=inLCContSet.get(i).getContNo();
		
			//签单并发控制 add by liuqh 2009-04-29
			/*if (!lockNo(tPrtNo)) {
				logger.debug("印刷号：["+tPrtNo+"]锁定号码失败!");
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				//tLockFlag = false;
				//mPubLock.unLock();
				continue;
			}*/
			try {
				tLCContSchema = inLCContSet.get(i);

				if (tLCContSchema == null) {
					continue;
				}
				// 获取合同所有信息
				tContDB = new LCContDB();
				tContDB.setContNo(tLCContSchema.getContNo());
				if (!tContDB.getInfo()) {
					continue;
				}

				if (tLCContSchema.getSaleChnl() != null) {
					this.mSaleChnl = tLCContSchema.getSaleChnl();
				} else {
					this.mSaleChnl = tContDB.getSaleChnl();
				}
				tLCContSchema.setSchema(tContDB.getSchema());
				
				//tongmeng 2008
				//首先校验是否进入签单限制控制
				//如果限制的话,跳过
				if(!checkScanDate(tLCContSchema))
				{
					continue;
				}
				mInsuredNo = tLCContSchema.getInsuredNo();
				// 对合同信息做校验
				//tongmeng 2008-09-12 modify
				//暂时放开这个校验,为了测试~ 
				
//				if (!this.checkOneCount(tLCContSchema)) {
//					continue;
//				}
				//统一对合同做缴费校验,校验合同下的所有险种是否都足额
//				if(!this.checkFinance(tLCContSchema.getPrtNo()))
//				{
//					continue;
//				}
				// 获取合同下所有核保通过的保单
				//tongmeng 2008-09-11 add
				//按照主险数据量进行拆分
				//注:如果以后签单不做保单拆分,那么只要把下面的SQL循环该修改直接查询出合同下的所有险种即可!
				sqlbv = new SQLwithBindVariables();
				tLCPolDB = new LCPolDB();
				String tSQL_main = "select * from lcpol a where contno = '?ContNo?' " 
				                 + " and appflag!='1' and mainpolno=polno and uwflag in ('3','4','9','d') "
				                 + " and exists (select '1' from lmriskapp where riskcode=a.riskcode and (risktype7 is null or risktype7='0')) "
				                 + " order by mainpolno ";
				sqlbv.sql(tSQL_main);
				sqlbv.put("ContNo", tLCContSchema.getContNo());
				//豁免险的处理要注意了~~
				//投保人和被保人豁免险在后面处理 
				tLCPolSet = tLCPolDB.executeQuery(sqlbv);
				VData tRiskVData = new VData(); 
				//循环主险
				logger.debug("pol Sign sql===" + sqlbv.sql());
				if (tLCPolSet == null || tLCPolSet.size() <= 0) {
					continue;
				}
				
				
				// tongmeng 2008-08-12 add
				// 生效时间在外面计算,险种的生效时间直接取计算结果
				String tCValiDate = "";
				tCValiDate = calContCValiDate(tLCContSchema.getContNo());
				logger.debug("##########:"+tCValiDate+"$$$:"+tLCContSchema.getPolApplyDate());
				Date tCValiDateD;
				try {
					tCValiDateD = tSDF.parse(tCValiDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					CError.buildErr(this,"生效时间转化错误!","01","007");
					continue;
				}
				if (tCValiDate == null || tCValiDate.equals("")) {
					continue;
				}
				//tongmeng 2008-09-24 modify
				//做拆分的小合同使用同一个PayNo
				String tLimit = PubFun.getNoLimit(tLCContSchema.getManageCom());
				String tPaySerialNo = PubFun1.CreateMaxNo("PAYNO", tLimit);
				//tongmeng 2008-09-11 add
				//直接在外面校验生日是否变化,proposalSignBL里面之后不做校验了~~~
				//提高效率
				// 按照主险的数量进行循环
				for (int main = 1; main <= tLCPolSet.size(); main++) {
					
					
					
					//查询一个主险下的主险及所有附加险信息
					//需要排除
					LCPolSet subLCPolSet = new LCPolSet();
					LCPolDB subLCPolDB = new LCPolDB();
					//update by cxq 2016-3-8 23:27:35 修改绑定变量
					sqlbv = new SQLwithBindVariables();
					String sql = "select * from lcpol a where contno ='?ContNo?' and appflag!='1' "
						+ " and mainpolno='?MainPolNo?'  "
						//排除豁免险
						+ " and exists (select '1' from lmriskapp where riskcode=a.riskcode and (risktype7 is null or risktype7='0' ) ) "
					    + " and uwflag in ('3','4','9','d') order by polno";
					sqlbv.sql(sql);
					sqlbv.put("ContNo", tLCContSchema.getContNo());
					sqlbv.put("MainPolNo", tLCPolSet.get(main).getMainPolNo());
					logger.debug("SQL:"+sqlbv.sql());
					subLCPolSet = subLCPolDB.executeQuery(sqlbv);
					
					// 校验生日是否发生变化!!!
					for (int n = 1; n <= subLCPolSet.size(); n++) {
						if (!this.checkAgeChange(tCValiDateD, subLCPolSet.get(n))) {
//							日志监控，过程监控。 合同***生日原因导致保费变化，不允许签单；
							PubFun.logTrack (mGlobalInput, subLCPolSet.get(n).getContNo(),"保单"+ subLCPolSet.get(n).getContNo()+"生日原因导致保费变化，不允许签单");	
						
							return false;
						}
						
//						LCPolSchema subLCPolSchema = subLCPolSet.get(n);
//						if(!subLCPolSchema.getMainPolNo().equals(subLCPolSchema.getPolNo()))
//						{
//							Product rider = this.buildProduct(subLCPolSchema);
//							rider.setEffectiveDate(tCValiDateD);
//							mainProduct.addRider(rider);
//							
//						}
						
					}	
				
					// tongmeng 2007-11-29 modify
					// 修改合同号生成规则				
					String tReturn = PubFun1.CreateMaxNo("CONTNO", tLimit); //
					String newContNo = tReturn;
					
					
//					//重算
//					mainProduct.reBulidProduct();
//					//构建账户
//					mainProduct.buildAccount();
										
					
					if (mTransferData == null) {
						mTransferData = new TransferData();
					}
					mTransferData.removeByName("CValiDate");
					mTransferData.setNameAndValue("CValiDate", tCValiDate);
					
					

					/**
					 * 如果是团单且不是卡单签单，则不进行个单换号
					 */
					if ("2".equals(tLCContSchema.getContType())
							&& !"card".equals(mark)) {
						newContNo = tLCContSchema.getContNo();
					}
					// 如果是银保通不进行保单号替换
					if (mTransferData != null
							&& mTransferData.getValueByName("YBT") != null
							&& mTransferData.getValueByName("YBT").equals("1")) {
						newContNo = tLCContSchema.getContNo();
						logger.debug("YBT  newContno=" + newContNo);
					}
					//卡单正常生成合同号
					// 如果是卡单，签单时不用换号，而是界面录入的号码 modify by zhangxing
//				if (mTransferData != null
//						&& mTransferData.getValueByName("CardContNo") != null
//						&& CardContNo != null) {
//					newContNo = CardContNo;
//				}	
					//自助卡单不进行保单号替换
					if(KDCheckFlag != null && KDCheckFlag.equals("1"))
					{
						newContNo = tLCContSchema.getContNo();
					}
					//电子商务不进行保单号替换
					if(ECheckFlag != null && ("1".equals(ECheckFlag)))
					{
						newContNo = tLCContSchema.getContNo();
					}
					logger.debug("newContNo="+newContNo);
				
					VData tInput = new VData();
					tInput.add(this.mGlobalInput);
					tInput.add(subLCPolSet);
					tInput.add(newContNo);
					tInput.add(mTransferData);
					// 提交保单签单
					//注意生效时间是在前面计算好的,险种的生效时间直接使用就可以了.
					//dealFinance 里面使用 
					proposalSignBL = new ProposalSignBL();
					boolean signResult = proposalSignBL.submitData(tInput, null);
					if (!signResult) {
						if (mark != null && mark.equals("card")) {
							if (!proposalSignBL.mErrors.needDealError()) {
								CError.buildErr(this, "签单没有通过");
							} else {
								this.mErrors.copyAllErrors(proposalSignBL.mErrors);
							}
							return false;

						} else {
							if (proposalSignBL.mErrors.needDealError()) {
								mErrors.copyAllErrors(proposalSignBL.mErrors);
							} else {
								CError.buildErr(this, "签单没有通过");							
							}
							continue;
						}
					} else {
						this.mErrors.copyAllErrors(proposalSignBL.mErrors);
					}

					VData oneCont = new VData();
					oneCont = proposalSignBL.getResult();
					//tongmeng 2008-09-11
					VData oneRiskData  = new VData();
					oneRiskData =  proposalSignBL.getPolDetailData();
					//开始财务处理
					VData bufferdPolData = proposalSignBL.getBufferedPolData();
					//??????????tongmeng 2008-09-17 add
					//对帐户的处理??????此处.....
					//统一使用dealLCPolFinance方法处理.
					if(!dealLCPolFinance(oneCont,tLCContSchema,tPaySerialNo,newContNo,bufferdPolData,mVResult)
						)
					{

						CError.buildErr(this,"处理保单:"+tLCContSchema.getPrtNo()+"财务数据出错!","01","008");
						break;
					}
					tRiskVData.add(oneRiskData);
				}//end 处理主险
				//
				logger.debug("begin deal FreeRisk...");
				//只处理豁免险
				String tFreeRiskFlag = "0";
				//update by cxq 2016-3-8 23:29:38修改绑定变量
				sqlbv = new SQLwithBindVariables();
				String tSQL_free = "select * from lcpol a where contno='?ContNo?' "
				                 + " and exists (select '1' from lmriskapp where riskcode=a.riskcode and risktype7 in ('1','2')) "
				                 + " and uwflag in ('3','4','9','d') ";
				sqlbv.sql(tSQL_free);
				sqlbv.put("ContNo", tLCContSchema.getContNo());
				tLCPolSet = tLCPolDB.executeQuery(sqlbv);
				logger.debug("end deal FreeRisk...");
				//tongmeng 2009-06-18 modify
				//校验生日是否发生变化,如果是投保人豁免险.
				// 校验生日是否发生变化!!!
				for (int n = 1; n <= tLCPolSet.size(); n++) {
					if (!this.checkAgeChange(tCValiDateD, tLCPolSet.get(n))) {
						return false;
					}
				}	
				
				if(tLCPolSet!=null&&tLCPolSet.size()>=1)
				{
					tFreeRiskFlag=String.valueOf(tLCPolSet.size());
					logger.debug("豁免险数量:"+tFreeRiskFlag);
					// tongmeng 2007-11-29 modify
					// 修改合同号生成规则				
					String tReturn = PubFun1.CreateMaxNo("CONTNO", tLimit); //
					String newContNo = tReturn;
					//处理豁免险集合
					VData tInput = new VData();
					tInput.add(this.mGlobalInput);
					tInput.add(tLCPolSet);
					//???合同号问题待处理 
					tInput.add(newContNo);
					tInput.add(mTransferData);
					// 提交保单签单
					//注意生效时间是在前面计算好的,险种的生效时间直接使用就可以了.
					proposalSignBL = new ProposalSignBL();
					boolean signResult = proposalSignBL.submitData(tInput, null);
					if (!signResult) {
						
							if (proposalSignBL.mErrors.needDealError()) {
								mErrors.copyAllErrors(proposalSignBL.mErrors);
							} else {
								CError.buildErr(this, "签单没有通过");
							}
							continue;
					} else {
						this.mErrors.copyAllErrors(proposalSignBL.mErrors);
					}
					//tongmeng 2008-09-11
					VData oneRiskData  = new VData();
					oneRiskData =  proposalSignBL.getPolDetailData();
					//开始财务处理
					VData oneCont = new VData();
					oneCont = proposalSignBL.getResult();
					VData bufferdPolData = proposalSignBL.getBufferedPolData();
					//??????????tongmeng 2008-09-17 add
					//对帐户的处理??????此处.....
					if(!dealLCPolFinance(oneCont,tLCContSchema,tPaySerialNo,newContNo,bufferdPolData,mVResult)
						)
					{
						CError.buildErr(this,"处理保单:"+tLCContSchema.getPrtNo()+"财务数据出错!","01","008");
						continue;
					}
					tRiskVData.add(oneRiskData);
				}
				//tongmeng 2008-09-11 modify
				//针对MS的多主险和家庭单,在保单签发时,按照合同中的主险的数量将一个大合同拆分为若干小合同
				//涉及到需要拆分的表有 
				//lccont lcinsured lcappnt
				//需要更新的表有
				//对保单的处理
				logger.debug("begin split one cont...:"+tLCContSchema.getContNo());
				//存放保单拆分程序中需要处理的一些字段
				TransferData spTransferData = new TransferData();
				spTransferData.setNameAndValue("Operator", this.mGlobalInput.Operator);
				spTransferData.setNameAndValue("FirstPayDate",this.mFirstPayDate);
				spTransferData.setNameAndValue("EnterAccDate",this.maxEnterAccDate);
				spTransferData.setNameAndValue("MaxPayDate",this.maxPayDate);
				spTransferData.setNameAndValue("PAYNO",tPaySerialNo);
				spTransferData.setNameAndValue("SellType", tLCContSchema.getSellType());
				spTransferData.setNameAndValue("GlobalInput",this.mGlobalInput);
				spTransferData.setNameAndValue("ContNo", tLCContSchema.getContNo());
				spTransferData.setNameAndValue("Mark",mark);//卡单需要
				//计算合同成立日期 最后一次核保通过日期
				TransferData nnTransferData = new TransferData();
				nnTransferData = calContOrganizeDate(tLCContSchema.getContNo());
				String OrganizeDate = (String)nnTransferData.getValueByName("OrganizeDate");
				String OrganizeTime = (String)nnTransferData.getValueByName("OrganizeTime");
				spTransferData.setNameAndValue("OrganizeDate",OrganizeDate);
				spTransferData.setNameAndValue("OrganizeTime",OrganizeTime);
				IndContSplitBL tIndContSplitBL = new IndContSplitBL();
				
				//tongmeng 2008-10-24 add
				//判断前面程序是否处理正确
				if(tRiskVData.size()<=0)
				{
					CError.buildErr(this,"处理保单:"+tLCContSchema.getPrtNo()+"出错!","01","021");
					continue;
				}
				//
				if(!tIndContSplitBL.submitData(tRiskVData,spTransferData,tFreeRiskFlag))
				{
					//copy error
					this.mErrors.copyAllErrors(tIndContSplitBL.mErrors);
					mVResult.clear();
					continue;
				}
				else
				{
					//获取结果集,提交数据 
					if(this.mVResult.size()>0)
					{
						MMap tempMMap = (MMap)this.mVResult.getObjectByObjectName("MMap",0);
						tempMMap.add((MMap)tIndContSplitBL.getResult().getObjectByObjectName("MMap",0));	
					}
					else
					{
						this.mVResult.add((MMap)tIndContSplitBL.getResult().getObjectByObjectName("MMap",0));
					}
				}
				logger.debug("end split one cont...:"+tLCContSchema.getContNo());

				logger.debug("00000000000000000000");
				signCount++;
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//mPubLock.unLock();
			}			
	
		}	
		if (mark != null && mark.equals("card")) 
		{
			// 提交程序 add by yaory for carTB
			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(mVResult, "Insert")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError.buildErr(this, "数据提交失败!");
				return false;
			}

		}
		if(this.mErrors.getErrorCount()!=0){
			return false;
		}
		return true;

	}

	/**
	 * 处理财务核销~险种的核销统一在该方法中做.
	 * @param ontCont
	 * @param tLCContSchema
	 * @param tPaySerialNo
	 * @param finaMap
	 * @param tOneRiskLJAPayPersonSet
	 * @return
	 */
	private boolean dealLCPolFinance(VData oneCont, LCContSchema tLCContSchema,
			String tPaySerialNo,String  newContNo,VData bufferdPolData,VData mVResult
			) {
	
		try {
			TransferData tTransferData = (TransferData) oneCont
			.getObjectByObjectName("TransferData", 0);
			LCPolSet theLCPolSet = (LCPolSet) oneCont
				.getObjectByObjectName("LCPolSet", 0);

			mInsuredAppAgeChangeFlag = (String) tTransferData
				.getValueByName("InsuredAppAgeChangeFlag");
			//开始万能险与财务处理
			mLCPolSchema = (LCPolSchema) proposalSignBL.getResult()
				.getObjectByObjectName("LCPolSchema", 0);
			mLCInsureAccClassSet = (LCInsureAccClassSet) proposalSignBL
				.getResult().getObjectByObjectName(
					"LCInsureAccClassSet", 0);
			mLCInsureAccTraceSet = (LCInsureAccTraceSet) proposalSignBL
				.getResult().getObjectByObjectName(
					"LCInsureAccTraceSet", 0);
			mLCInsureAccFeeSet = (LCInsureAccFeeSet) proposalSignBL
				.getResult().getObjectByObjectName("LCInsureAccFeeSet",
					0);
			mLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) proposalSignBL
				.getResult().getObjectByObjectName(
					"LCInsureAccClassFeeSet", 0);
			mLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) proposalSignBL
				.getResult().getObjectByObjectName(
					"LCInsureAccFeeTraceSet", 0);
			if ("1".equals(tLCContSchema.getContType())
					|| ("2".equals(tLCContSchema.getContType()) && "card"
					.equals(mark))) {
				logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&");
				logger.debug("开始财务处理与万能险处理...");
				// 财务处理与万能险处理
				//tongmeng 2008-09-24 modify
				//记录实收个人缴费记录,便于在保单拆分程序中汇总实收总表信息~
//				日志监控，性能监控。 			
				PubFun.logPerformance (mGlobalInput,tLCContSchema.getContNo(),"开始处理保单"+tLCContSchema.getContNo()+"财务数据","2");	
				//VData bufferdPolData = proposalSignBL.getBufferedPolData();
				MMap finaMap = this.dealFinance(tLCContSchema,
						bufferdPolData, newContNo, tPaySerialNo );
//				日志监控，性能监控。 
				PubFun.logPerformance (mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"财务数据处理完毕","3");
				if (finaMap == null) {			
					  //日志监控，过程监控
					PubFun.logTrack(mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"财务数据处理出错");
					return false;
				}
				  //日志监控，过程监控
				PubFun.logTrack(mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"财务数据处理成功");

				logger.debug("结束财务处理与万能险处理...");
				//tongmeng 2008-09-11 add
				//先注释掉。。。。稍后加
				//srvResult.add(finaMap);
				mVResult.add(finaMap);
				//将一套主险及其下面附加险的信息放到险种容器中。
				//注意目前没有处理财务的信息 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	
	
	/**
	 * 续涛，2005-09-20添加， 校验核保通过后的险种是否有主险。
	 * 
	 * @return
	 */
	private boolean checkMainRisk(LCPolSet tLCPolSet) {
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			String tPolNo = StrTool.cTrim(tLCPolSchema.getPolNo());
			String tMainPolNo = StrTool.cTrim(tLCPolSchema.getMainPolNo());
			if (tPolNo.equals(tMainPolNo)) {
				return true;
			}

		}

		return false;
	}
	/**
	 *比较产品组合保单交费
	 * 
	 * 
	 */
	private boolean compareTempFeePlanErr(LCPolSet inPolSet) {
		//update by cxq 2016-3-8 23:29:38修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String sql = "select sum( paymoney ),riskcode "
				+ " from ljtempfee where otherno='?PrtNo?' "
				+ " and trim(agentcode) in (select trim(agentcode) from lacommisiondetail where grpcontno='?PrtNo?')"
				+ " and confdate is null "
				+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')"
				+ " and riskcode='?RiskCode?' group by riskcode  ";
		sqlbv.sql(sql);
		sqlbv.put("PrtNo", inPolSet.get(1).getPrtNo());
		sqlbv.put("RiskCode", mContPlanCode);
		ExeSQL exesql = new ExeSQL();
		SSRS ssrs = exesql.execSQL(sqlbv);
		if (ssrs == null || ssrs.getMaxRow() == 0) {
			CError.buildErr(this, "(1)产品组合" + mContPlanCode
					+ "未查到任何交费信息或交费没有到帐;(2)交费信息中业务员和保单业务员不符。");
			return false;
		}
		double sumtmpfeemoney = 0;
		sumtmpfeemoney = Double.parseDouble(ssrs.GetText(1, 1));
		double polprem = 0;
		for (int j = 1; j <= inPolSet.size(); j++) {
			polprem += inPolSet.get(j).getPrem();
		}

		double differ = polprem - sumtmpfeemoney;
		logger.debug("differ====" + polprem + "-" + sumtmpfeemoney);
		if (differ > 0) {
			/**
			 * @todo 如果年龄有变化在此提示 mod by HYQ 2006-3-23
			 */
			if (mInsuredAppAgeChangeFlag != null
					&& mInsuredAppAgeChangeFlag.equals("true")) {
				CError.buildErr(this, "签单时被保人年龄变化！");
			}

			CError.buildErr(this, "保单总交费不足或交费没有到帐");

			return false;
		}

		return true;
	}

	/**
	 * 比较保单的交费 --精确到险种 --没查到报错
	 * 
	 * @param tmpPolSchema
	 *            LCPolSchema
	 * @return LJTempFeeSet
	 */
	private boolean compareTempFeeRiskErr(LCPolSet inPolSet) {
		/**
		 * @todo 对于预收金额的校验
		 */
		int tPolFlag = 0; // add by heyq 如果此险种被拒保或延期，则不需交验是否已交费
		String sql = "";
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		// 安险种分组保单交费求和
		if (mTransferData != null
				&& mTransferData.getValueByName("YBT") != null
				&& mTransferData.getValueByName("YBT").equals("1")) {
			sql = "select sum( paymoney ),riskcode,'1' "
					+ " from ljtempfee where otherno='?PrtNo?' "
					// + " and agentcode ='" + inPolSet.get(1).getAgentCode() +
					// "'"
					+ " and trim(agentcode) in (select trim(agentcode) from lacommisiondetail where grpcontno='?ContNo?')"
					+ " and confdate is null "
					+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')"
					+ " group by riskcode  ";
			sqlbv.sql(sql);
			sqlbv.put("PrtNo", inPolSet.get(1).getPrtNo());
			sqlbv.put("ContNo",inPolSet.get(1).getContNo());

		} else {
			// tongmeng 2008-01-16 modify
			// 修改此段逻辑,错误提示信息不清晰
			// sql = "select sum( paymoney ),riskcode "
			// + " from ljtempfee where otherno='" +
			// inPolSet.get(1).getPrtNo() + "' "
			// //+ " and agentcode ='" + inPolSet.get(1).getAgentCode() + "'"
			// +
			// " and trim(agentcode) in (select trim(agentcode) from lacommisiondetail where grpcontno='"
			// +
			// inPolSet.get(1).getPrtNo() + "')"
			// + " and confdate is null "
			// +
			// " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')"
			// + " group by riskcode  ";
			
			//update by cxq 2016-3-3 18:08:54 nvl替换成case when
			sql = " select (case when sum(A.x) is not null then sum(A.x) else 0 end),A.y,A.z from ("
					// 1-累计保费
					+ " select paymoney x,riskcode y,'1' z "
					+ " from ljtempfee a where otherno='?PrtNo?' "
					+ " and exists (select 1 from lacommisiondetail where grpcontno='?PrtNo?' and agentcode=a.agentcode)"
					+ " and confdate is null "
					+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01') "
					+ " union "
					// 2-交费信息和保单信息业务员不符
					+ " select paymoney x,riskcode y,'2' z "
					+ " from ljtempfee a where otherno='?PrtNo?' "
					+ " and not exists (select 1 from lacommisiondetail where grpcontno='?PrtNo?' and agentcode=a.agentcode)"
					+ " and confdate is null "
					+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01') "
					+ " ) A " + " group by A.y,A.z  ";
			sqlbv.sql(sql);
			sqlbv.put("PrtNo", inPolSet.get(1).getPrtNo());
		}
		logger.debug("查询财务到帐sql:" + sqlbv.sql());
		ExeSQL exesql = new ExeSQL();
		SSRS ssrs = exesql.execSQL(sqlbv);
		if (ssrs == null || ssrs.getMaxRow() == 0) {
			// CError.buildErr(this, "(1)保费不足或未到帐;(2)交费信息和保单信息业务员不符。");
			CError.buildErr(this, "保费未到帐。");
			return false;
		}
		double sumtmpfeemoney = 0;
		double sumpolprem = 0;
		String summoney = "0";
		// 按险种比较交费信息
		for (int i = 1; i <= ssrs.getMaxRow(); i++) {
			// tongmeng 2008-01-16 add
			String tErrFlag = "";
			tErrFlag = ssrs.GetText(i, 3);
			if (tErrFlag.equals("2")) {
				CError.buildErr(this, "交费信息和保单信息业务员不符。");
				return false;
			}
			tPolFlag = 0;
			String riskcode = (String) ssrs.GetText(i, 2);
			summoney = summoney + "+" + ssrs.GetText(i, 1); // 拼累加的sql
			double tempfeemoney = Double.parseDouble(ssrs.GetText(i, 1));
			sumtmpfeemoney += tempfeemoney;
			logger.debug("程序中累加结果feemoney===" + sumtmpfeemoney);
			double polprem = 0;
			for (int j = 1; j <= inPolSet.size(); j++) {
				if (inPolSet.get(j).getRiskCode().equals(riskcode)) {
					polprem += inPolSet.get(j).getPrem();
					tPolFlag = 1; // add by heyq 如果此险种被拒保或延期，则不需交验是否已交费
				}
			}
			if (tPolFlag == 1) {
			}
		}

		// -----modify by haopan ---2007-1-30--把保费的累加放到sql里
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String calSQL = "select ?summoney? from dual ";
		sqlbv.sql(calSQL);
		sqlbv.put("summoney", summoney);
		logger.debug(sqlbv.sql());
		SSRS ssrscal = exesql.execSQL(sqlbv);
		sumtmpfeemoney = Double.parseDouble(ssrscal.GetText(1, 1));
		logger.debug("sql中累加结果feemoney====" + sumtmpfeemoney);

		// 整个个人保单交费保存
		String polprem = "0";
		for (int j = 1; j <= inPolSet.size(); j++) {
			sumpolprem += inPolSet.get(j).getPrem();
			logger.debug("程序中累加的结果polprem======" + sumpolprem);
			polprem = polprem + "+" + inPolSet.get(j).getPrem();
		}
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String polSQL = "select ?polprem? from dual"; // 把累加放到sql里--hp
		sqlbv.sql(polSQL);
		sqlbv.put("polprem", polprem);
		logger.debug(sqlbv.sql());
		SSRS ssrspol = exesql.execSQL(sqlbv);
		sumpolprem = Double.parseDouble(ssrspol.GetText(1, 1));
		logger.debug("sql中累加的结果polprem======" + sumpolprem);

		double differ = sumpolprem - sumtmpfeemoney;
		logger.debug("differ====" + sumpolprem + "-" + sumtmpfeemoney);
		if (differ > 0) {
			/**
			 * @todo 如果年龄有变化在此提示 mod by HYQ 2006-3-23
			 */
			if (mInsuredAppAgeChangeFlag != null
					&& mInsuredAppAgeChangeFlag.equals("true")) {
				CError.buildErr(this, "签单时被保人年龄变化！");
			}
			CError.buildErr(this, "保单总交费不足");

			return false;
		}
		return true;

	}

	/**
	 * 比较保单的交费 --精确到险种 --没查到报错
	 * 
	 * @param tmpPolSchema
	 *            LCPolSchema
	 * @return LJTempFeeSet
	 */
	private boolean compareTempFeeRiskErr(LCPolSchema tmpPolSchema) {
		LJTempFeeSet tLJTempFeeSet;
		LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setOtherNo(tmpPolSchema.getPrtNo());
		// tLJTempFeeSchema.setOtherNoType("6");
		// if (this.mSaleChnl != null && this.mSaleChnl.equals("3"))
		// {
		// tLJTempFeeSchema.setOtherNoType("7");
		// }
		tLJTempFeeSchema.setConfFlag("0");
		tLJTempFeeSchema.setRiskCode(tmpPolSchema.getRiskCode());
		tLJTempFeeDB.setSchema(tLJTempFeeSchema);

		tLJTempFeeSet = tLJTempFeeDB.query();

		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "交费信息查询错误", tLJTempFeeDB.mErrors);
			return false;
		}
		if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
			CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode() + "]没有交费");
			return false;
		}
		double prem = 0.00;
		for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
			if (tLJTempFeeSet.get(i).getEnterAccDate() == null) {
				CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode()
						+ "]交费没有到帐");
				return false;
			}
			// 所交保费求和
			prem += tLJTempFeeSet.get(i).getPayMoney();
		}
		if (Math.abs(tmpPolSchema.getPrem() - prem) <= 0.001) {
			CError.buildErr(this, "险种[" + tmpPolSchema.getRiskCode()
					+ "]交费金额与保单载明金额不一致，请查证");
			return false;
		}
		return true;

	}

	/**
	 * 处理财务数据 包括暂交费，实交费表，退费处理等,更新LCCont中保费保额
	 * 
	 * @param financeData
	 *            VData financeData ---(VData) PolData --- LCPolSchema ---
	 *            LCPremSet <p/> ---- LCTempFeeSet //(运行过程种，加入暂交费信息) ----
	 *            计算过程仲改变险种保单的living money
	 */
	private MMap dealFinance(LCContSchema tLCContSchema, VData financeData,
			String newContNo, String tPaySerialNo) {
		VData result = new VData();
		VData onePolVData = null;
		LCPolSchema tmpPolSchema = null;
		LCPremSet tmpPremSet = null;
		VData lackVData = new VData(); // 保费不足的保单列表
		VData redundantVData = new VData(); // 保费有余的保单列表
		// VData balanceVData = new VData();
		double sumPrem = 0.0;
		double sumAmnt = 0.0;
		double sumContPrem = 0.0;

		MMap tmpMap = new MMap();
		double sumTmpRiskFee = 0.0;
		// 循环合同保单中所有险种保单，进行保费均匀
		for (int i = 0; i < financeData.size(); i++) {
			onePolVData = (VData) financeData.get(i);
			if (onePolVData == null || onePolVData.size() <= 0) {
				continue;
			}

			tmpPolSchema = (LCPolSchema) onePolVData.getObjectByObjectName(
					"LCPolSchema", 0);

			sumAmnt += tmpPolSchema.getAmnt();
			tmpPremSet = (LCPremSet) onePolVData.getObjectByObjectName(
					"LCPremSet", 0);
			// 求总保费
			for (int t = 1; t <= tmpPremSet.size(); t++) {
				sumPrem += tmpPremSet.get(t).getPrem();
			}

			// sumContPrem += sumPrem; /** 如果多被保险人同一险种交费，会造成保费求和错误，替换算法
			// 2005-3-10 **/
			sumContPrem += tmpPolSchema.getPrem();

			/** 如果多被保险人同一险种交费，会造成保费求和，故去掉 2005-3-9 wujs **/
			/*** tmpPolSchema.setPrem(PubFun.setPrecision(sumPrem,"0.00")); ***/

			if (!"Y".equals(mBalFlag)) {
				// 根据险种取暂交费
				LJTempFeeSet riskTempFeeSet = this
						.queryTempFeeRisk(tmpPolSchema);

				if (riskTempFeeSet != null && riskTempFeeSet.size() > 0) {
					// 更新暂交费信息,
					//财务核销不在这里做~
					this
					.prepareFeeInfo(tmpPolSchema, riskTempFeeSet,
							tmpPremSet, tPaySerialNo, newContNo);
			
					// 缓存暂交费信息
					this.cacheTempFee(tmpPolSchema.getRiskCode(),
							riskTempFeeSet.get(1));
				}

				// 求同一险种暂交费总和
				double tmpRiskFee = sumPayMoney(riskTempFeeSet);
			}
			// 如果是结算签单则，根据 结算号取出交费日期及到账日期等信息
			if (mBalFlag != null && mBalanceNo != null && mBalFlag.equals("Y")
					&& !mBalanceNo.equals("")) {
				FDate fDate = new FDate();
				//update by cxq 修改绑定变量
				sqlbv = new SQLwithBindVariables();
				String strTempfee = "select min(paydate),max(paydate),max(enteraccdate) from ljtempfee "
						+ " where otherno='?BalanceNo?' and confdate is null  "
						+ " and (enteraccdate is not null and enteraccdate<>'3000-01-01')";
				sqlbv.sql(strTempfee);
				sqlbv.put("BalanceNo", mBalanceNo);
				ExeSQL tempExeSQL = new ExeSQL();
				SSRS tempSSRS = new SSRS();
				tempSSRS = tempExeSQL.execSQL(sqlbv);
				for (int j = 1; j <= tempSSRS.getMaxRow(); j++) {
					mFirstPayDate = fDate.getDate(tempSSRS.GetText(j, 1));
					maxPayDate = fDate.getDate(tempSSRS.GetText(j, 2));
					maxEnterAccDate = fDate.getDate(tempSSRS.GetText(j, 3));
				}
			}
			// add by yaory
			// 在此将902险种的ContNo和PolNo复制
			LCInsureAccClassSet newLCInsureAccClassSet = new LCInsureAccClassSet();
			LCInsureAccTraceSet newLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LCInsureAccFeeSet newLCInsureAccFeeSet = new LCInsureAccFeeSet();
			LCInsureAccClassFeeSet newLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
			LCInsureAccFeeTraceSet newLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
			//
			String mainpolno = tmpPolSchema.getMainPolNo();
			String aPolNo = tmpPolSchema.getPolNo();
			String InsuAccValiDate = tmpPolSchema.getCValiDate();
			// tongmeng 2008-07-03 modify
			// 支持多主险录入,本处有可能会出现多个主险,需要判断是帐户型才做以下操作!
			String tRiskCode = tmpPolSchema.getRiskCode();
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tRiskCode);
			tLMRiskDB.getInfo();


				
			String tInsuAccFlag = tLMRiskDB.getInsuAccFlag();
			if (tInsuAccFlag.equals("Y") && aPolNo.equals(mainpolno)
					&& mLCInsureAccClassSet != null
					&& mLCInsureAccTraceSet != null) {
				
				//tongmeng 2010-11-23 modify
				//增加对投资生效的日处理
				LMRiskAppDB aLMRiskAppDB = new LMRiskAppDB();
				aLMRiskAppDB.setRiskCode(tmpPolSchema.getRiskCode());
				if (aLMRiskAppDB.getInfo())
				{
					if (aLMRiskAppDB.getRiskType3() != null && aLMRiskAppDB.getRiskType3().equals("3"))
					{
						// 投连险犹豫期之后生效，承保后计划变更不考虑日期问题
						//为保证计价，修改为签单日+犹豫期+1（即当前日期+11天）
						if (tmpPolSchema.getUintLinkValiFlag() != null && tmpPolSchema.getUintLinkValiFlag().equals("4"))
						{
							//InsuAccValiDate = "3000-01-01";
							InsuAccValiDate = PubFun.calDate(mCurrentDate, 11, "D", null);
						}
						else if (tmpPolSchema.getUintLinkValiFlag() != null
								&& tmpPolSchema.getUintLinkValiFlag().equals("2"))
						{
							//update by cxq 修改绑定变量
							sqlbv = new SQLwithBindVariables();
							String tSql = "select * from lbcont where prtno='?prtno?' and prtno='?prtno?' order by modifydate";
							sqlbv.sql(tSql);
							sqlbv.put("prtno", tmpPolSchema.getPrtNo());
							
							LBContDB tLBContDB = new LBContDB();
							LBContSet tLBContSet = new LBContSet();;
							tLBContSet = tLBContDB.executeQuery(sqlbv);
							if (tLBContSet.size() > 0)
							{
								InsuAccValiDate = tLBContSet.get(1).getSignDate();
							}
							else
							{
								InsuAccValiDate = mCurrentDate;
							}
						} // Mod by gaoht 核保订正后签单后生效取之前的日期
						else
						{
							//InsuAccValiDate = "3000-01-01";
							InsuAccValiDate = PubFun.calDate(mCurrentDate, 11, "D", null);
						}

						
					}
				}
				
				for (int j = 0; j < mLCInsureAccClassSet.size(); j++) {
					LCInsureAccClassSchema aLCInsureAccClassSchema = new LCInsureAccClassSchema();
					aLCInsureAccClassSchema = mLCInsureAccClassSet.get(j + 1);
					aLCInsureAccClassSchema.setPolNo(aPolNo);
					aLCInsureAccClassSchema.setContNo(newContNo);
					newLCInsureAccClassSet.add(aLCInsureAccClassSchema);
				}
				for (int j = 0; j < mLCInsureAccTraceSet.size(); j++) {
					LCInsureAccTraceSchema aLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					aLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(j + 1);
					aLCInsureAccTraceSchema.setPolNo(aPolNo);
					aLCInsureAccTraceSchema.setContNo(newContNo);
					aLCInsureAccTraceSchema.setPayDate(InsuAccValiDate);
					newLCInsureAccTraceSet.add(aLCInsureAccTraceSchema);
				}
				for (int j = 0; j < mLCInsureAccFeeSet.size(); j++) {
					LCInsureAccFeeSchema aLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
					aLCInsureAccFeeSchema = mLCInsureAccFeeSet.get(j + 1);
					aLCInsureAccFeeSchema.setPolNo(aPolNo);
					aLCInsureAccFeeSchema.setContNo(newContNo);
					newLCInsureAccFeeSet.add(aLCInsureAccFeeSchema);
				}
				for (int j = 0; j < mLCInsureAccClassFeeSet.size(); j++) {
					LCInsureAccClassFeeSchema aLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
					aLCInsureAccClassFeeSchema = mLCInsureAccClassFeeSet
							.get(j + 1);
					aLCInsureAccClassFeeSchema.setPolNo(aPolNo);
					aLCInsureAccClassFeeSchema.setContNo(newContNo);
					newLCInsureAccClassFeeSet.add(aLCInsureAccClassFeeSchema);
				}

				for (int j = 0; j < mLCInsureAccFeeTraceSet.size(); j++) {
					LCInsureAccFeeTraceSchema aLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
					aLCInsureAccFeeTraceSchema = mLCInsureAccFeeTraceSet
							.get(j + 1);
					aLCInsureAccFeeTraceSchema.setPolNo(aPolNo);
					aLCInsureAccFeeTraceSchema.setContNo(newContNo);
					newLCInsureAccFeeTraceSet.add(aLCInsureAccFeeTraceSchema);
				}

				tmpMap.put(newLCInsureAccClassSet, "INSERT");
				tmpMap.put(newLCInsureAccTraceSet, "INSERT");
				tmpMap.put(newLCInsureAccFeeSet, "INSERT");
				tmpMap.put(newLCInsureAccClassFeeSet, "INSERT");
				tmpMap.put(newLCInsureAccFeeTraceSet, "INSERT");
			}
		} // end for financedata

		PubFun.setPrecision(sumContPrem, "0.00");
		return tmpMap;
	}


	/**
	 * 根据传入的暂交费集合,求暂交费和
	 * 
	 * @param riskTempFeeSet
	 *            LJTempFeeSet
	 * @return double
	 */
	private double sumPayMoney(LJTempFeeSet riskTempFeeSet) {
		double tmpRiskFee = 0.0;
		for (int j = 1; j <= riskTempFeeSet.size(); j++) {
			tmpRiskFee += riskTempFeeSet.get(j).getPayMoney();
		}
		return tmpRiskFee;
	}
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// @@可以处理保单集和单个保单的情况
		mLCContSet = (LCContSet) mInputData.getObjectByObjectName("LCContSet",
				0);

		if (mLCContSet == null || mLCContSet.size() <= 0) {
			CError.buildErr(this, "没有传入数据集!");
			return false;
		}
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		// 卡单签单使用统一时间
		if (mTransferData != null) {
			mCurrentDate = PubFun.getCurrentDate();
			mCurrentTime = PubFun.getCurrentTime();
			mark = (String) mTransferData.getValueByName("mark");
			CardContNo = (String) mTransferData.getValueByName("CardContNo"); // 卡单保单号码
			mBalFlag = (String) mTransferData.getValueByName("BalFlag"); // 结算标志
			mBalanceNo = (String) mTransferData.getValueByName("BalanceNo"); // 结算号
			KDCheckFlag = (String) mTransferData.getValueByName("KDCheckFlag");
			ECheckFlag =(String) mTransferData.getValueByName("ECheckFlag");//电子商务标志
		}
		if (mCurrentDate == null) {
			mCurrentDate = PubFun.getCurrentDate();
			mCurrentTime = PubFun.getCurrentTime();

		}

		return true;

	}
	/**
	 * 暂交费更新 输出：如果发生错误则返回null,否则返回VData 修改tLCPolSchema的firstpaydate
	 */
	private MMap prepareFeeInfo(LCPolSchema tLCPolSchema,
			LJTempFeeSet tLJTempFeeSet, LCPremSet tLCPremSet,
			String tPaySerialNo, String newContNo) {

		MMap tReturn = new MMap();
		String tOldPolNo = tLCPolSchema.getProposalNo();

		String modifyDate = PubFun.getCurrentDate();
		String modifyTime = PubFun.getCurrentTime();
		double sumPay = 0;
		double left = 0;
		FDate fDate = new FDate();
		Date tFirstPayDate = null;
		Date tmaxPayDate = null;
		Date tmaxEnterAccDate = null;

		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();

		int n = tLJTempFeeSet.size();

		for (int i = 1; i <= n; i++) {
			LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);

			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			LJTempFeeClassSet tLJTempFeeClassSet1 = tLJTempFeeClassDB.query();

			if (tLJTempFeeClassDB.mErrors.needDealError()) {
				CError.buildErr(this, "LJTempFeeClass表取数失败!");
				return null;
			}

			// end of if
			int m = tLJTempFeeClassSet1.size();

			for (int j = 1; j <= m; j++) {
				LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet1
						.get(j);
				tLJTempFeeClassSchema.setConfDate(mCurrentDate);
				tLJTempFeeClassSchema.setConfFlag("1");
				tLJTempFeeClassSchema.setModifyDate(modifyDate);
				tLJTempFeeClassSchema.setModifyTime(modifyTime);
				tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
			}

			// end of for
			sumPay += tLJTempFeeSchema.getPayMoney();

			tLJTempFeeSchema.setOtherNo(newContNo);
			/*
			 * comment by heyq othernotype表示险种的渠道，即使是交叉销售也不能修改其属性
			 * //tLJTempFeeSchema.setOtherNoType("2");
			 * tLJTempFeeSchema.setOtherNoType("6"); //20050806 hl if
			 * (this.mSaleChnl != null && this.mSaleChnl.equals("3")) {
			 * tLJTempFeeSchema.setOtherNoType("7"); //20050806 hl
			 * 
			 * }
			 */
			tLJTempFeeSchema.setConfDate(mCurrentDate);
			tLJTempFeeSchema.setConfFlag("1");
			tLJTempFeeSchema.setModifyDate(modifyDate);
			tLJTempFeeSchema.setModifyTime(modifyTime);

			// 取首次交费日期
			Date payDate = fDate.getDate(tLJTempFeeSchema.getPayDate());

			if (tFirstPayDate == null || payDate.before(tFirstPayDate)) {
				tFirstPayDate = payDate;
			}
			// LJAPay中使用
			if (tmaxPayDate == null || tmaxPayDate.before(payDate)) {
				tmaxPayDate = payDate;
			}
			Date enterAccDate = fDate.getDate(tLJTempFeeSchema
					.getEnterAccDate());
			if (tmaxEnterAccDate == null
					|| tmaxEnterAccDate.before(enterAccDate)) {
				tmaxEnterAccDate = enterAccDate;
			}
			tLCPolSchema.setFirstPayDate(tFirstPayDate);
			tLJTempFeeSet.set(i, tLJTempFeeSchema);
		}

		// 全局交至日期，到帐日期等
		if (maxEnterAccDate == null || maxEnterAccDate.before(tmaxEnterAccDate)) {
			maxEnterAccDate = tmaxEnterAccDate;
		}
		if (mFirstPayDate == null || tFirstPayDate.before(mFirstPayDate)) {
			mFirstPayDate = tFirstPayDate;
		}
		if (maxPayDate == null || tmaxPayDate.before(maxPayDate)) {
			maxPayDate = tmaxPayDate;
		}

		if (tLJTempFeeClassSet.size() > 0) {
			tReturn.put(tLJTempFeeClassSet, this.UPDATE);
		}
		if (tLJTempFeeSet.size() > 0) {
			tReturn.put(tLJTempFeeSet, this.UPDATE);
		}
		// end of for
		// }

		return tReturn;
	}
	/**
	 * 打印所有错误信息
	 */
	public void outErrors() {
		if (this.mErrors.needDealError()) {
			logger.debug("错误信息:");
			for (int i = 0; i < this.mErrors.getErrorCount(); i++) {
				logger.debug(this.mErrors.getError(i).errorMessage);
			}
		}
	}
	/**
	 * 缓存暂交费信息
	 * 
	 * @param key
	 *            Object
	 * @param tempFeeSchema
	 *            LJTempFeeSchema
	 */
	private void cacheTempFee(Object key, LJTempFeeSchema tempFeeSchema) {
		this.mLJTempFeeMap.put(key, tempFeeSchema);
	}

	/**
	 * 获取暂交费信息.
	 * 
	 * @param key
	 *            String
	 * @return Object
	 */
	private Object getTempFee(String key) {
		return mLJTempFeeMap.get(key);
	}

	/**
	 * 判断保单险种是否为产品组合
	 * 
	 * @param LCContSchema
	 *            tLCContSchema
	 */
	public boolean isContPlan(String tContNo) {
		ExeSQL sExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String contPlanSQL = "Select contplancode from lcinsured where contno='?tContNo?'";
		sqlbv.sql(contPlanSQL);
		sqlbv.put("tContNo", tContNo);
		mContPlanCode = sExeSQL.getOneValue(sqlbv).trim();
		if (mContPlanCode == null || mContPlanCode == "") {
			return false;
		}
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String strSOL = "select * from ldplan " + " where contplancode ='?mContPlanCode?'" + " and PlanType  in ('0','3')";
		sqlbv.sql(strSOL);
		sqlbv.put("mContPlanCode", mContPlanCode);
		tSSRS = sExeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() == 0) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("86110000000012");
		LCContSet mLCContSet = new LCContSet();
		mLCContSet.add(tLCContSchema);
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.ComCode = "86";
		tG.Operator = "001";
		tVData.add(tG);
		tVData.add(mLCContSet);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CurrentDate", "2006-07-29");
		tTransferData.setNameAndValue("CurrentTime", "16:27:00");
//		tTransferData.setNameAndValue("mark", "card");
//		tTransferData.setNameAndValue("CardContNo", "48795000006107");
		tVData.add(tTransferData);
		LCContSignBL tLCContSignBL = new LCContSignBL();
		
//		LCContDB tLCContDB = new LCContDB();
//		tLCContDB.setContNo("86110000000012");
//		tLCContDB.getInfo();
//
//		tLCContSignBL.checkScanDate(tLCContDB.getSchema());
		boolean flag = tLCContSignBL.submitData(tVData, "sign");
		if (!flag) {
			logger.debug(tLCContSignBL.mErrors.getFirstError());
		}

	}

	/**
	 * 计算生效时间
	 * 
	 * @param tContNo
	 * @return
	 */
	private String calContCValiDate(String tContNo) {
		String tCValiDate = "";
		
		//校验是否生效日制定
	
		
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询合同信息出错!","01","006");
			return null;
		}
		String tBack = getSignValiDateBackFlag(tContNo,tLCContDB.getSchema());
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String tSQL = "select * from lcpol a where contno = '?tContNo?' " 
			        + " and appflag!='1' and mainpolno=polno and uwflag in ('3','4','9','d') ";
		sqlbv.sql(tSQL);
		sqlbv.put("tContNo", tContNo);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		String specCValidate = "";
		//tongmeng 2008-11-13 
		//如果是生效日指定的话，不重新计算生效日。
		for(int i=1;i<=tLCPolSet.size();i++)
		{
			if(tLCPolSet.get(i).getSpecifyValiDate()!=null&&tLCPolSet.get(i).getSpecifyValiDate().equals("Y"))
			{
				specCValidate = tLCPolSet.get(i).getCValiDate();
				break;
			}
		}
		if(specCValidate!=null&&!specCValidate.equals(""))
		{
			return specCValidate;
		}
		Date tDate = this.calValiDate(tLCContDB.getSchema(), tBack);
		if (tDate == null) {
			return null;
		}
		tCValiDate = tSDF.format(tDate);

		return tCValiDate;

	}
	
	/**
	 * 计算合同成立时间
	 * 
	 * @param tContNo
	 * @return
	 */
	private TransferData calContOrganizeDate(String tContNo) {
		TransferData tTransferData = new TransferData();
		String tOrganizeDate = "";
		String tOrganizeTime = "";

		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String tSQL = "select * from lccuwsub a where contno = '?tContNo?' and passflag in ('4','9') " 
			        + " order by makedate desc,maketime desc";
		sqlbv.sql(tSQL);
		sqlbv.put("tContNo", tContNo);
		tLCCUWSubSet = tLCCUWSubDB.executeQuery(sqlbv);
		if(tLCCUWSubSet!=null && tLCCUWSubSet.size()>0)
		{
			tOrganizeDate = tLCCUWSubSet.get(1).getMakeDate();
			tOrganizeTime = tLCCUWSubSet.get(1).getMakeTime();
		}		
		tTransferData.setNameAndValue("OrganizeDate", tOrganizeDate);
		tTransferData.setNameAndValue("OrganizeTime", tOrganizeTime);
		
		return tTransferData;

	}

	/**
	 * 根据险种，查找出新生效日期的计算方法，如果是附加险，为了保证主附险生效日期一致，则取主险的生效日期回溯方式
	 * 
	 * @parameter LCPolSchema tLCPolSchema
	 * @return 生效日期回溯方式 SignValiDateBackFlag 1 签单次日 2 最晚一笔保费到帐日期次日 3
	 *         最早一笔保费到帐日期次日 4 投保单申请日期次日 现在系统除银代险种外默认为3，即保费最到到帐次日，银代险种为4 投保日期次日
	 */
	private String getSignValiDateBackFlag(String tContNo,LCContSchema tLCContSchema) {
		String tBackFlag = ""; // 生效日期回溯方式 返回值
		String ttBackFlag = "";//十一前
		String tYYBBackFlag = "";//十一后银邮保通
		String tOBackFlag = "";//十一后银邮保通计算方式
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();

		//String today = PubFun.getCurrentDate();
		String today = tLCContSchema.getPolApplyDate();//改为取投保申请日期
		logger.debug("today: " + today);
		tSQL = "select sysvarvalue from ldsysvar where sysvar ='ybtcalmodestartdate'"; // 附加险需获取主险的的SignValiDateBackFlag
		sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		String ybtcalmodestartdate = tExeSQL.getOneValue(sqlbv);
		if (ybtcalmodestartdate == null || ybtcalmodestartdate.equals("")) {
			ybtcalmodestartdate = "";
			logger.debug("系统没有定义邮保通算法7生效时间");
		}
		tSQL = "select sysvarvalue from ldsysvar where sysvar ='fybtcalmodestartdate'"; // 附加险需获取主险的的SignValiDateBackFlag
		sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		String fybtcalmodestartdate = tExeSQL.getOneValue(sqlbv);
		if (fybtcalmodestartdate == null || fybtcalmodestartdate.equals("")) {
			fybtcalmodestartdate = "";
			logger.debug("系统没有定义非邮保通算法6生效时间");
		}
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		tSQL = "select signdatecalmode,signdatecalmode2,signdatecalmode3 from LMRiskApp where RiskCode in(select Riskcode from LCPol where contno='?tContNo?' and polno=mainpolno)"; // 附加险需获取主险的的SignValiDateBackFlag
		sqlbv.sql(tSQL);
		sqlbv.put("tContNo", tContNo);
		logger.debug("tSQL: " + sqlbv.sql());
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if(tSSRS!=null&&tSSRS.getMaxRow()>0)
		{
			ttBackFlag = tSSRS.GetText(1,1);
			tYYBBackFlag = tSSRS.GetText(1,2);
			tOBackFlag = tSSRS.GetText(1,3);
		}
		
		String tSellType = tLCContSchema.getSellType();
		if(tSellType!=null && tSellType.equals("08"))//银邮保通保单
		{
			if(ybtcalmodestartdate.equals(""))
			{
				tBackFlag = ttBackFlag;
			}
			else if(PubFun.checkDate(ybtcalmodestartdate, today))//投保申请日期大于等于10月1日
			{
				tBackFlag = tYYBBackFlag;
			}
			else
				tBackFlag = ttBackFlag;	
		}
		else
		{
			if(fybtcalmodestartdate.equals(""))
			{
				tBackFlag = ttBackFlag;
			}
			else if(PubFun.checkDate(fybtcalmodestartdate, today))//投保申请日期大于等于10月1日
			{
				tBackFlag = tOBackFlag;
			}
			else
				tBackFlag = ttBackFlag;	
		}			
		
		logger.debug("tBackFlag: " + tBackFlag);
		return tBackFlag;
	}

	/**
	 * 改变险种保单生效日期 输出：LCPolSchema
	 */
	private Date calValiDate(LCContSchema tLCContSchema, String flag) {
		Date tValiDate = null;
		// 选择签单次日
		if (flag.equals("1")) {
			tValiDate = fDate.getDate(PubFun.getCurrentDate());
			tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
		}

		// 选择交费的到帐日期的最大值
		if (flag.equals("2") || flag.equals("3")) {
			Date maxDate = fDate.getDate("1900-01-01");
			Date minDate = fDate.getDate("3000-01-01");

			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String tTempFeeSql = "select * from LJTempFee where " // 现金方式,现金支票
					+ " OtherNo ='?PrtNo?' "
					+ "and ConfFlag <>'1' and EnterAccDate is not null "
					+ " and TempFeeType = '1' " ;
//					+ "union all "
//					+ "select * from LJTempFee where TempFeeType = '1' " // 银行转帐方式
//					+ "and OtherNoType='0' and OtherNo in (select ProposalNo from LCPol where PrtNo ='"
//					+ tLCContSchema.getPrtNo()
//					+ "') "
//					+ "and ConfFlag <>'1' and EnterAccDate is not null";
			sqlbv.sql(tTempFeeSql);
			sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
			logger.debug("*****calValiDate__QueryTempFee: " + sqlbv.sql());
			LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
			tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);

			if (tLJTempFeeDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
				CError.buildErr(this, tLCContSchema.getPrtNo() + " 财务信息取数失败!","01","002");
				return null;
			}

			if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {

				this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
				CError.buildErr(this, tLCContSchema.getPrtNo() + " 保费未到账","01","003");
				return null;
			}
			int n = tLJTempFeeSet.size();

			for (int i = 1; i <= n; i++) {
				LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);

				Date enterAccDate = fDate.getDate(tLJTempFeeSchema
						.getEnterAccDate());

				if (enterAccDate.before(minDate)) {
					minDate = enterAccDate;
				}

				if (enterAccDate.after(maxDate)) {
					maxDate = enterAccDate;
				}
			}

			if (flag.equals("2")) {
				tValiDate = PubFun.calDate(maxDate, 1, "D", null);
			}

			if (flag.equals("3")) {
				tValiDate = PubFun.calDate(minDate, 1, "D", null);
			}
		}

		// 选择保单申请次日
		if (flag.equals("4")) {
			tValiDate = fDate.getDate(tLCContSchema.getPolApplyDate());
			tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
		}
		// tongmeng 2008-04-22 add
		// 增加生效日期算法5
		// 首期交费到帐日和投保单申请次日最大值
		if (flag.equals("5")) {
			//update by cxq 2016-3-3 18:15:02 日期计算及行号
			String tSQL = "";
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQL = "select to_char(B.m+1,'yyyy-mm-dd') from ( "
						// 取财务到帐和保单投保日期最大日
						+ " select A.x m from ( "
						+ " select enteraccdate x from LJTempFee where TempFeeType = '1' "
						+ " and OtherNo ='?PrtNo?' "
						+ " and ConfFlag <>'1' and EnterAccDate is not null "
						+ " union " + " select to_date('?PolApplyDate?','yyyy-mm-dd') x from dual "
						+ " ) A order by A.x desc " + " ) B where rownum=1 ";
				sqlbv.sql(tSQL);
				sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
				sqlbv.put("PolApplyDate", tLCContSchema.getPolApplyDate());
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSQL = "select to_char(adddate(B.m,1),'yyyy-mm-dd') from ( "
						// 取财务到帐和保单投保日期最大日
						+ " select A.x m from ( "
						+ " select enteraccdate x from LJTempFee where TempFeeType = '1' "
						+ " and OtherNo ='?PrtNo?' "
						+ " and ConfFlag <>'1' and EnterAccDate is not null "
						+ " union " + " select to_date('?PolApplyDate?','yyyy-mm-dd') x from dual "
						+ " ) A order by A.x desc " + " ) B limit 0,1 ";
				sqlbv.sql(tSQL);
				sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
				sqlbv.put("PolApplyDate", tLCContSchema.getPolApplyDate());
			}
			ExeSQL tExeSQL = new ExeSQL();
			String tempCvalidate = "";
			logger.debug("Cal cvalidate :" + sqlbv.sql());
			tempCvalidate = tExeSQL.getOneValue(sqlbv);
			if (tempCvalidate.equals("")) {
				CError.buildErr(this, tLCContSchema.getPrtNo() + " 生效时间计算错误!","01","020");
			}
			logger.debug("Cal cvalidate result :" + tempCvalidate);
			tValiDate = fDate.getDate(tempCvalidate);
		}
		// ln 2009-09-01 add
		// 增加生效日期算法6
		// 首期交费到帐日和投保单申请次日最大值
		if (flag.equals("6")) {
			//update by cxq 2016-3-3 18:15:02 行号
			String tSQL = "";
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQL = "select to_char(B.m,'yyyy-mm-dd') from ( "
						// 取财务到帐和保单投保日期最大日
						+ " select A.x m from ( "
						+ " select enteraccdate x from LJTempFee where TempFeeType = '1' "
						+ " and OtherNo ='?PrtNo?' "
						+ " and ConfFlag <>'1' and EnterAccDate is not null "
						+ " union " + " select to_date('?PolApplyDate?','yyyy-mm-dd') x from dual "
						+ " ) A order by A.x desc " + " ) B where rownum=1 ";
				sqlbv.sql(tSQL);
				sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
				sqlbv.put("PolApplyDate", tLCContSchema.getPolApplyDate());
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSQL = "select to_char(B.m,'yyyy-mm-dd') from ( "
						// 取财务到帐和保单投保日期最大日
						+ " select A.x m from ( "
						+ " select enteraccdate x from LJTempFee where TempFeeType = '1' "
						+ " and OtherNo ='?PrtNo?' "
						+ " and ConfFlag <>'1' and EnterAccDate is not null "
						+ " union " + " select to_date('?PolApplyDate?','yyyy-mm-dd') x from dual "
						+ " ) A order by A.x desc " + " ) B limit 0,1 ";
				sqlbv.sql(tSQL);
				sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
				sqlbv.put("PolApplyDate", tLCContSchema.getPolApplyDate());
			}
			ExeSQL tExeSQL = new ExeSQL();
			String tempCvalidate = "";
			logger.debug("Cal cvalidate :" + sqlbv.sql());
			tempCvalidate = tExeSQL.getOneValue(sqlbv);
			if (tempCvalidate.equals("")) {
				CError.buildErr(this, tLCContSchema.getPrtNo() + " 生效时间计算错误!","01","020");
			}
			logger.debug("Cal cvalidate result :" + tempCvalidate);
			tValiDate = fDate.getDate(tempCvalidate);
		}
		
		// ln 2009-09-02 add
		// 增加生效日期算法7
		// 取最后一次核保通过日期
		if (flag.equals("7")) {
			TransferData nnTransferData = new TransferData();
			nnTransferData = calContOrganizeDate(tLCContSchema.getContNo());
			String OrganizeDate = (String)nnTransferData.getValueByName("OrganizeDate");
			if (OrganizeDate.equals("")) {
				CError.buildErr(this, tLCContSchema.getPrtNo() + " 生效时间计算错误!","01","020");
			}
			logger.debug("Cal cvalidate result :" + OrganizeDate);
			tValiDate = fDate.getDate(OrganizeDate);
		}
		
		
		return tValiDate;
	}

	/**
	 * 校验合同的信息
	 * 
	 * @param tLCContSchema
	 * @return
	 */
	private boolean checkOneCount(LCContSchema tLCContSchema) {
		// 复核,核保校验
		if (tLCContSchema.getAppFlag().equals("1")) {
			logger.debug("提示信息:合同(" + tLCContSchema.getContNo() + ")已签单;");
			return false;
		}
		// tongmeng 2007-12-24 add
		// 增加撤单申请的判断
		//tongmeng 2009-05-11 modify
		// 修改校验条件
//		String sql_a = "select count(1) from LCApplyRecallPol where prtno='"
//				+ tLCContSchema.getContNo() + "' and applytype='0'";
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String sql_a = "select count(1) from es_doc_main where "
			         + " subtype='UR201' and doccode='?PrtNo?'";
		sqlbv.sql(sql_a);
		sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
		ExeSQL tExeSQL = new ExeSQL();
		String tResult = "";
		logger.debug("判断是否有撤单申请:" + sqlbv.sql());
		tResult = tExeSQL.getOneValue(sqlbv);
		if (Integer.parseInt(tResult) > 0) {
			 // 日志监控,过程监控
			PubFun.logTrack (mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"处于撤单申请中，不能签单");			
			logger.debug("印刷号:" + tLCContSchema.getContNo() + "有撤单申请!");
			CError
					.buildErr(this, "印刷号:" + tLCContSchema.getContNo()
							+ "有撤单申请!","01","001");
			return false;
		}
		//09-07-01  增加校验，如果险种的uwflag不为1  合同的uwflag不为4或9则 不允许签单
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String tCheckSql = "select count(1) from lcpol where prtno='?PrtNo?' and uwflag='0'";
		sqlbv.sql(tCheckSql);
		sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
		String tUWResult = tExeSQL.getOneValue(sqlbv);
		if(tUWResult!=null&&!tUWResult.equals("")&&Integer.parseInt(tUWResult)>0){
//			日志监控，过程监控。如果险种的uwflag不为1，不允许签单；
			PubFun.logTrack (mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"险种未核保通过，不能签单");			
			CError.buildErr(this, tLCContSchema.getPrtNo()+" 险种的核保结论为0!","01","030");
			return false;
		}
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		tCheckSql = "select count(1) from lccont where prtno='?PrtNo?' and uwflag not in('4','9')";
		sqlbv.sql(tCheckSql);
		sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
		String tContUW = tExeSQL.getOneValue(sqlbv);
		if(tContUW!=null&&!tContUW.equals("")&&Integer.parseInt(tContUW)>0){
//			日志监控，过程监控。 合同的uwflag不为4或9则，不允许签单；
			PubFun.logTrack (mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"合同未核保通过，不能签单");			
			CError.buildErr(this, tLCContSchema.getPrtNo()+" 合同的核保结论不为4或9!","01","031");
			return false;
		}
		
		// 校验财务是否到帐
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String tTempFeeSql = "select * from LJTempFee where 1=1 and TempFeeType = '1' " // 现金方式
				// ,
				// 现金支票
				+ "and OtherNo ='?PrtNo?' "
				+ "and ConfFlag <>'1' and EnterAccDate is not null "
				+ " and confdate is null "
				//+ "union all "
				//+ "select * from LJTempFee where TempFeeType = '1' " // 银行转帐方式
				//+ "and OtherNoType='0' and OtherNo in (select ProposalNo from LCPol where PrtNo ='"
				//+ tLCContSchema.getPrtNo()
				//+ "') "
				
				//+ "and ConfFlag <>'1' and EnterAccDate is not null"
				;
		sqlbv.sql(tTempFeeSql);
		sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);

		if (tLJTempFeeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			CError.buildErr(this, tLCContSchema.getPrtNo() + " 财务信息取数失败!","01","002");
			return false;
		}

		if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
//			日志监控，过程监控。 合同***的保费未到账，不允许签单；
			PubFun.logTrack (mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"的保费未到账，不允许签单");	
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			CError.buildErr(this, tLCContSchema.getPrtNo() + " 保费未到账","01","003");
			return false;
		}
        //增加对投保单代理人和暂交费表中代理人的比较 add by liuqh 2008-10-25
		//tongmeng 2009-06-03 modify
		//核保提出放开投保单代理人和暂交费代理人一致的校验.
		/*
		String tLCContAgent = tLCContSchema.getAgentCode();
		LJTempFeeSet tLJTempFeeSet1 = new LJTempFeeSet();
		tLJTempFeeDB.setOtherNo(tLCContSchema.getPrtNo());
		tLJTempFeeSet1 = tLJTempFeeDB.query();
		for(int i =1;i<=tLJTempFeeSet1.size();i++)
		{
			if(!tLJTempFeeSet1.get(i).getAgentCode().equals(tLCContAgent))
			{
				CError.buildErr(this, "投保单代理人与暂交费代理人不一致","01","040");
				return false;
			}
		}*/
		
		
		
		return true;
	}
	
	/**
	 * 校验生日是否发生变化
	 * @param tCValidate
	 * @param tLCPolSchema
	 * @return
	 */
	private boolean checkAgeChange(Date tCValidate,LCPolSchema tLCPolSchema)
	{
		//Y
		//?????
		//tongmeng 2008-11-13 modify
		//忘记当初为什么要加这个判断了。。。以后再想想是什么问题导致的。。。
//		if(tLCPolSchema.getSpecifyValiDate()!=null&&tLCPolSchema.getSpecifyValiDate().equals("Y"))
//		{
//			
//			//生效日制定后，不校验生日单。
//			return true;
//		}
		int newAge = PubFun.calInterval(fDate.getDate(tLCPolSchema
				.getInsuredBirthday()), tCValidate, "Y");
		logger.debug("newAge:"+newAge+"Old:"+tLCPolSchema.getInsuredAppAge());
		if(newAge!=tLCPolSchema.getInsuredAppAge())
		{
			CError.buildErr(this,"生日原因导致保费发生变化!","01","005");
			return false;
		}
		
		//tongmeng 2009-06-18 modify
		//如果是投保人豁免险,需要校验投保人年龄是否发生变化.
		String tRiskCode = tLCPolSchema.getRiskCode();
		if(tRiskCode!=null&&tRiskCode.equals("121301"))
		{
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String tSQLAppnt = "select a.appntbirthday,b.cvalidate from "
				             + " lcappnt a,lccont b where a.contno=b.contno " 
				             + " and b.contno='?ContNo?'";
			sqlbv.sql(tSQLAppnt);
			sqlbv.put("ContNo", tLCPolSchema.getContNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if(tSSRS!=null&&tSSRS.getMaxRow()>0)
			{
				try {
					String tAppntBirthday = tSSRS.GetText(1,1);
					String tPolApplyDate = tSSRS.GetText(1,2);
					int oldAppntAge =  PubFun.calInterval(fDate.getDate(tAppntBirthday), fDate.getDate(tPolApplyDate), "Y");
					int newAppntAge = PubFun.calInterval(fDate.getDate(tAppntBirthday), tCValidate, "Y");
					if(oldAppntAge!=newAppntAge)
					{
						CError.buildErr(this,"生日原因导致保费发生变化!","01","005");
						return false;
					}

				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return true;
			
	}
	
	/**
	 * 整体校验合同下的财务数据是否都足额到账
	 * @param tContNo
	 * @return
	 */
	private boolean checkFinance(String tPrtNo)
	{
		//累计合同下的不同险种的保费,同以险种做汇总
		//所有险种都要足额到账
		try {
			/*String tSQL_LCPOL = "  select count(*) from "
			                  + " (select otherno z,riskcode x,sum(paymoney) y  from ljtempfee "
			                  + " where otherno='"+tPrtNo+"' "
			                  + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') "
			                  + " group by otherno,riskcode) A, "
			                  + " ( select prtno z,riskcode x,sum(prem) y from lcpol "
			                  + " where prtno='"+tPrtNo+"' "
			                  + " group by prtno,riskcode) B "
			                  + " where " 
			                  + " A.z = B.z and  A.x=B.x " 
			                  + " and A.y>=B.y ";
			*/
			//tongmeng 2010-11-08 modify 
			//增加多币种校验
			//tongmeng 2010-11-30 modify
			//增加投资账户分币种缴费的处理
			String tSQL_LCPOL = "";
			//update by cxq 2016-3-3 18:24:06 decode替换case when
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQL_LCPOL = "select (case when count(*) = 0 then 0 else 1 end) from " 
			              + " ( "
//			              + " select 1 from " 
//			              + " ( "
//			              + " select contno z,riskcode x,currency m,sum(prem) y from lcpol " 
//			              + " where prtno='"+tPrtNo+"' and uwflag in ('3','4','9','d') "
//			              + " and (length(trim(prtno))<>20 or substr(prtno,3,1)<>'7')"
//			              + " group by contno,riskcode,currency "
//			              + " ) A where A.y>(select nvl(sum(paymoney),0) from ljtempfee where otherno='"+tPrtNo+"' " 
//			              + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') " 
//			              + " and confflag='0' "
//			              + " and riskcode=A.x and currency=A.m) "
//			              + " union all "
			              + " select 1 from " 
			              + " ( "
			              + " select contno z,currency x,sum(prem) y from lcpol " 
			              + " where prtno='?tPrtNo?' and uwflag in ('3','4','9','d') "
			              + " and (length(trim(prtno))=20 and substr(prtno,3,1)='7')"//产品组合自助卡单
			              + " group by contno,currency "
			              + " ) A where A.y>(select (case when sum(paymoney) is not null then sum(paymoney) else 0 end) from ljtempfee where otherno='?tPrtNo?' " 
			              + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') " 
			              + " and confflag='0' and currency=A.x) "
			              + " union all "
			              + " select 1 from " 
			              + " ( " 
			              + " select a.contno z, a.riskcode x,a.currency m, sum(a.sumduepaymoney) y from ljspayperson a,lcpol b "
			              + " where a.polno=b.polno and b.prtno='?tPrtNo?' and b.uwflag in ('3','4','9','d') " 
			              + " and (length(trim(b.prtno))<>20 or substr(b.prtno,3,1)<>'7') "
			              + " group by a.contno,a.riskcode,a.currency "
			              + " ) A where A.y>(select (case when sum(paymoney) is not null then sum(paymoney) else 0 end) from ljtempfee where otherno='?tPrtNo?' " 
			              + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') " 
			              + " and confflag='0' "
			              + " and currency=A.m and riskcode=A.x)"
			              + " )B";
				sqlbv.sql(tSQL_LCPOL);
				sqlbv.put("tPrtNo", tPrtNo);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSQL_LCPOL = "select (case when count(*) = 0 then 0 else 1 end) from " 
			              + " ( "
//			              + " select 1 from " 
//			              + " ( "
//			              + " select contno z,riskcode x,currency m,sum(prem) y from lcpol " 
//			              + " where prtno='"+tPrtNo+"' and uwflag in ('3','4','9','d') "
//			              + " and (length(trim(prtno))<>20 or substr(prtno,3,1)<>'7')"
//			              + " group by contno,riskcode,currency "
//			              + " ) A where A.y>(select nvl(sum(paymoney),0) from ljtempfee where otherno='"+tPrtNo+"' " 
//			              + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') " 
//			              + " and confflag='0' "
//			              + " and riskcode=A.x and currency=A.m) "
//			              + " union all "
			              + " select 1 from " 
			              + " ( "
			              + " select contno z,currency x,sum(prem) y from lcpol " 
			              + " where prtno='?tPrtNo?' and uwflag in ('3','4','9','d') "
			              + " and (char_length(trim(prtno))=20 and substr(prtno,3,1)='7')"//产品组合自助卡单
			              + " group by contno,currency "
			              + " ) A where A.y>(select (case when sum(paymoney) is not null then sum(paymoney) else 0 end) from ljtempfee where otherno='?tPrtNo?' " 
			              + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') " 
			              + " and confflag='0' and currency=A.x) "
			              + " union all "
			              + " select 1 from " 
			              + " ( " 
			              + " select a.contno z, a.riskcode x,a.currency m, sum(a.sumduepaymoney) y from ljspayperson a,lcpol b "
			              + " where a.polno=b.polno and b.prtno='?tPrtNo?' and b.uwflag in ('3','4','9','d') " 
			              + " and (char_length(trim(b.prtno))<>20 or substr(b.prtno,3,1)<>'7') "
			              + " group by a.contno,a.riskcode,a.currency "
			              + " ) A where A.y>(select (case when sum(paymoney) is not null then sum(paymoney) else 0 end) from ljtempfee where otherno='?tPrtNo?' " 
			              + " and tempfeetype='1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') " 
			              + " and confflag='0' "
			              + " and currency=A.m and riskcode=A.x)"
			              + " )B";
				sqlbv.sql(tSQL_LCPOL);
				sqlbv.put("tPrtNo", tPrtNo);
			}
			String tResult = "";
			logger.debug("tSQL_LCPOL:"+sqlbv.sql());
			ExeSQL tExeSQL = new ExeSQL();
			tResult = tExeSQL.getOneValue(sqlbv);
			if(tResult!=null&&!tResult.equals("")&&Integer.parseInt(tResult)>0)
			{
//				日志监控，过程监控。 合同***缴费金额不足，不允许签单；
				PubFun.logTrack (mGlobalInput,tPrtNo,"保单"+tPrtNo+"缴费金额不足，不允许签单");	
				CError.buildErr(this,"合同:"+tPrtNo+"下缴费金额不足!","01","004");
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void jbInit() throws Exception {
	}
	  /**
     * 查询保单的交费 --精确到险种
     *
     * @param tmpPolSchema LCPolSchema
     * @return LJTempFeeSet
     */
    private LJTempFeeSet queryTempFeeRisk(LCPolSchema tmpPolSchema)
    {
        LJTempFeeSet tLJTempFeeSet;
        //LJTempFeeSchema tLJTempFeeSchema = null;
        LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
        //update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
        String querytempfeeSQL = "select * from LJTempFee where OtherNo='?PrtNo?' "
                                 + " and ConfFlag='0' and ConfDate is null"
                                 +
                                 " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')";
        sqlbv.sql(querytempfeeSQL);
        sqlbv.put("PrtNo", tmpPolSchema.getPrtNo());
        tLJTempFeeSet = (tLJTempFeeDB.executeQuery(sqlbv));
        if (tLJTempFeeDB.mErrors.needDealError())
        {
            CError.buildErr(this, "queryTempFee", tLJTempFeeDB.mErrors);
            return null;
        }
        return tLJTempFeeSet;

    }
    /**
     * 校验扫描时间是否符合签单的要求.
     * @param tLCContSchema
     * @return
     * 描述位置:

		select sysvarvalue from ldsysvar where sysvar='PolSignDateLimit'

	   描述格式: 

		限制起期 时间(限制起期标记);限制止期 时间(限制止期标记);限制扫描日期 时间 # 限制起期 时间(限制起期标记);限制止期 时间(限制止期标记);限制扫描日期 时间

	   描述格式说明:

		分隔符 #  分隔多个限制条件
       		  ; 同一个限制条件间的分隔
       		  - 月份区间
       		  ^ 月份分隔符
       
		限制起期标志 
       
       		  a - 月初
       		  b - 月末
       
       		  + 下
              - 上
       
       		  +a 下月初 
       		  -b 上月末
       
		例:
		a)限制条件:

			1月时 从27号早08:00:00 到 30号晚23:59:59  不签发扫描时间在27号08:00:00之前扫描的保单
			2月时 从26号早08:00:00 到下月初00:00:00 不签发扫描时间在26号08:00:00之前扫描的保单
			3月至12月 从28号08:00:00 到月底23:59:59 不签发扫描时间在28号08:00:00之前扫描的保单

		上述限制条件可描述为
			1^27 08:00:00;30 23:59:59;27 08:00:00#2^26 08:00:00;+a 00:00:00;26 08:00:00#3-12^28:08:00;b 23:59:59;28 08:00:00

		b)限制条件:
			除2月外,其余各月 28日08:00:00 到下月1号00:00:00 不签发扫描时间在28日08:00:00之前的保单
			2月 26日08:00:00 到下月1号00:00:00 不签发扫描时间在26日08:00:00之前的保单

		上述限制条件可描述为
			1^28 08:00:00;+a 00:00:00;28 08:00:00#3-12^28 08:00:00;+a 00:00:00;28 08:00:00#2^26 08:00:00;+a 00:00:00;26 08:00:00
     */
    public boolean checkScanDate(LCContSchema tLCContSchema)
    {
    	//////////////////////////////////////////////////////////////////
    	//以下部分为不做签单的特殊校验
    	String tSellType="";
    	tSellType = tLCContSchema.getSellType();
    	//modify 2009-07-30 银保通、自助卡单、电子商务无签单限制    	    	
    	if(tSellType!=null&&(tSellType.equals("08")||tSellType.equals("13")||tLCContSchema.getSaleChnl().equals("04")))
    	{
    		return true;
    	}
    	//tongmeng 2009-08-25 个险渠道特殊签单不做限制
    	/*
    	 *  1.总体规则签单限制只限制个险渠道且有扫描件的保单
		 *  2.支持特殊签单处理，可以将该批保单签单
    	 */
    	//非个险渠道不签单
    	if(tLCContSchema.getSaleChnl()!=null&&!tLCContSchema.getSaleChnl().equals("02"))
    	{
    		return true;
    	}
    	//某些工号不做限制
        if(this.mGlobalInput.Operator!=null&&
        		(this.mGlobalInput.Operator.equals("000047")
        		||this.mGlobalInput.Operator.equals("000217")))
        {
        	return true;
        }
        //非扫描件不签单。
        //update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
        String tSQL_CheckScan = " select 1 from es_doc_main where subtype='UA001' "
        	                  + " and doccode='?PrtNo?'";
        sqlbv.sql(tSQL_CheckScan);
        sqlbv.put("PrtNo", tLCContSchema.getPrtNo());
        ExeSQL tScanExeSQL = new ExeSQL();
        String tValue = "";
        tValue = tScanExeSQL.getOneValue(sqlbv);
        if(tValue==null||!tValue.equals("1"))
        {
        	return true;
        }
        //////////////////////////////////////////////////////////////////
        
        
    	//1-查询系统配置限制签单时间段
     	String tSQL = "select sysvarvalue from ldsysvar where sysvar='PolSignDateLimit'";
     	sqlbv = new SQLwithBindVariables();
     	sqlbv.sql(tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		String tLimitResult = tExeSQL.getOneValue(sqlbv);
		if (tLimitResult == null || tLimitResult.equals("")) {
			logger.debug("没有描述签单限制时间范围");
			return true;
		}
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		logger.debug("系统描述签单限制范围:" + tLimitResult);
		try {
			String[] tAllCondition = PubFun.split(tLimitResult,"#");
			boolean checkLimitYear = false; 
			for(int i=0;i<tAllCondition.length;i++)
			{
				if(checkLimitYear)
				{
					break;
				}
				//获取每一个子条件
				String tCondition = tAllCondition[i];
				//确定年月起止区间
				//判断当前月份是否进入校验月
				//
				String tSignDateCondition = "";
				if(tCondition.indexOf("^")==-1)
				{
					//不限制起止年月
					//设置校验通过
					checkLimitYear = true; 
					tSignDateCondition = tCondition;
				}
				else
				{
					String tMonthBetween = tCondition.substring(0,tCondition.indexOf("^"));
					if(!checkMonthRule(tCurrentDate,tMonthBetween))
					{
						continue;
					}
					checkLimitYear = true;
					tSignDateCondition = tCondition.substring(tCondition.indexOf("^")+1);
				}
			//	
				//获得校验的起止区间
				logger.debug("tSignDateCondition:"+tSignDateCondition);
				String[] tSignDateLimit = getLimitBeginEnd(tCurrentDate,tSignDateCondition);
				String tSignDateLimitStart = tSignDateLimit[0];
				String tSignDateLimitEnd = tSignDateLimit[1];
				String tScanDateLimit = tSignDateLimit[2];
				if(tSignDateLimitStart==null||tSignDateLimitEnd==null||tScanDateLimit==null)
				{
					logger.debug("系统描述签单限制范围出错!");
					return false;
				}
				String tYearMonth = tCurrentDate.substring(0,8);
				logger.debug("tYearMonth:"+tYearMonth);
				//update by cxq 2016-3-3 18:40:48 ||连字符修改为concat
				//update by cxq 修改绑定变量
				sqlbv = new SQLwithBindVariables();
				String tSQL_a = " select count(*) from dual where "
					          + " to_date(concat(concat('?tCurrentDate?',' '),'?tCurrentTime?'),'yyyy-mm-dd hh24:MI:ss')>= "
				              + " to_date('?tSignDateLimitStart?','yyyy-mm-dd hh24:MI:ss') "
				              + " and to_date(concat(concat('?tCurrentDate?',' '),'?tCurrentTime?'),'yyyy-mm-dd hh24:MI:ss')<= "
				              + " to_date('?tSignDateLimitEnd?','yyyy-mm-dd hh24:MI:ss') "
				              ;
				sqlbv.sql(tSQL_a);
				sqlbv.put("tCurrentDate", tCurrentDate);
				sqlbv.put("tCurrentTime", tCurrentTime);
				sqlbv.put("tSignDateLimitStart", tSignDateLimitStart);
				sqlbv.put("tSignDateLimitEnd", tSignDateLimitEnd);
				logger.debug("tSQL_a:"+sqlbv.sql());
				
				if(Integer.parseInt(tExeSQL.getOneValue(sqlbv))<=0)
				{
					//非限制时间
					return true;
				}
				//限制签单时间
				//判断扫描时间,决定是否该签单
				String sql = "";
			    String scanDate = "";
			    String scanTime = "";
			    SSRS sScanDate_SSRS = new SSRS();
			    //update by cxq 修改绑定变量
				sqlbv = new SQLwithBindVariables();
			    sql = "select makedate,maketime from es_doc_main where doccode='?ContNo?' ";
			    sqlbv.sql(sql);
			    sqlbv.put("ContNo", tLCContSchema.getContNo());
			    sScanDate_SSRS = tExeSQL.execSQL(sqlbv);
			    
			    //scanDate = sScanDate_SSRS.g
			    logger.debug(scanDate);
			    if (sScanDate_SSRS.getMaxRow()<=0) {
			    	SSRS tempSSRS = new SSRS();
			      //无扫描件,取录入时间
			      //传入的一定是主险数据.所以只要根据保单号查即可
			    	//update by cxq 修改绑定变量
			    	sqlbv = new SQLwithBindVariables();
			    	sql = " select makedate,maketime from lccont where contno='?ContNo?' ";
			    	sqlbv.sql(sql);
			    	sqlbv.put("ContNo", tLCContSchema.getContNo());
			    	tempSSRS = tExeSQL.execSQL(sqlbv);
			    	scanDate = tempSSRS.GetText(1, 1);
			    	scanTime = tempSSRS.GetText(1, 2);
			    }
			    else
			    {
			    	scanDate = sScanDate_SSRS.GetText(1, 1);
			    	scanTime = sScanDate_SSRS.GetText(1, 2);
			    }
			    scanDate = AgentPubFun.formatDate(scanDate, "yyyy-MM-dd");
			    //如果扫描时间为空，默认为"00:00:00"
			    if(scanTime==null||scanTime.equals(""))
			    {
			    	scanTime = "00:00:00";
			    }
			    logger.debug("scanDate scanTime:"+scanDate+" "+scanTime);
			    //update by cxq 2016-3-3 18:42:49 ||修改为concat
			    //update by cxq 修改绑定变量
			    sqlbv = new SQLwithBindVariables();
			    String tSQL_b = "select count(*) from dual where "
			    	          + " to_date(concat(concat('?scanDate?',' '),'?scanTime?'),'yyyy-mm-dd hh24:MI:ss')<= to_date('?tScanDateLimit?','yyyy-mm-dd hh24:MI:ss') ";
			    sqlbv.sql(tSQL_b);
			    sqlbv.put("scanDate", scanDate);
			    sqlbv.put("scanTime", scanTime);
			    sqlbv.put("tScanDateLimit", tScanDateLimit);
				if(Double.parseDouble(tExeSQL.getOneValue(sqlbv))>0)
				{
					return true;
				}
				else
				{
			        CError.buildErr(this,tLCContSchema.getContNo().trim()+"扫描时间: "+scanDate+" " +scanTime + " 大于"+tScanDateLimit+",在"+tSignDateLimitStart+"至"+tSignDateLimitEnd+"之间禁止签单!");
					return false;	
				}
				
			}
			
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
		return true;
    }
 
    /**
     * 按照描述的限制止期算法转换限制止期
     * 		  a - 月初
       		  b - 月末
       
       		  + 下
              - 上
       
       		  +a 下月初 
       		  -b 上月末
       		  
     * @param tCurrentDate
     * @param tCondition
     * @return
     */
 private String changeContionToData(String tCurrentDate,String tCondition)
 {
	 String tResult = "";
	 //获取运算符
	 //获取计算时间
	 String tOper = tCondition.substring(0,tCondition.indexOf(" "));
	 String tTime = tCondition.substring(tCondition.indexOf(" ")+1);
	 String tSQL = "";
	 //update by cxq 修改绑定变量
	 sqlbv = new SQLwithBindVariables();
	 //月初的相关算法
	 if(tOper.indexOf("a")!=-1)
	 { 
		 int tLen = 0;
		 if(tOper.indexOf("-")!=-1)
		 {
			 //上月初
			 tLen = -1;
		 }
		 else if(tOper.indexOf("+")!=-1)
		 {
			 tLen = 1;
		 }
		 //update by cxq 2016-3-3 18:44:29 ||修改为concat
		 tSQL = "select to_char(add_months(to_date(concat(substr('?tCurrentDate?',1,8),'01'),'yyyy-mm-dd'),?tLen?),'yyyy-mm-dd') from dual ";
		 sqlbv.sql(tSQL);
		 sqlbv.put("tCurrentDate", tCurrentDate);
		 sqlbv.put("tLen", tLen);
	 }
	 //月末的相关算法
	 else if(tOper.indexOf("b")!=-1)
	 { 
		 int tLen = 1;
		 if(tOper.indexOf("-")!=-1)
		 {
			 //上月末
			 tLen = 0;
		 }
		 else if(tOper.indexOf("+")!=-1)
		 {
			 tLen = 2;
		 }
		 //update by cxq 2016-3-3 18:44:29 ||修改为concat且日期计算使用subdate
		 tSQL = "select to_char(add_months(subdate(to_date(concat(substr('?tCurrentDate?',1,8),'01'),'yyyy-mm-dd'),1),1),'yyyy-mm-dd') from dual ";
		 sqlbv.sql(tSQL);
		 sqlbv.put("tCurrentDate", tCurrentDate);
	 }
	 ExeSQL tExeSQL = new ExeSQL();
	 String tDate = "";
	 tDate = tExeSQL.getOneValue(sqlbv);
	 if(tDate==null||tDate.equals(""))
	 {
		 tResult = "";
	 }
	 else
	 {
		 tResult = tDate + " " + tTime;
	 }
		 
	 
	 return tResult;
 }
 
 /**
  * 校验当前月份是否符合描述的月份范围
  * @param tCurrentDate
  * @param tMonthLimit
  * @return
  */
 private boolean checkMonthRule(String tCurrentDate,String tMonthLimit)
 {
	boolean tSuccFlag = false;
	String tCurrMonth = "";
	//2008-12-11
	tCurrMonth = tCurrentDate.substring(5,7);
	String tMonthBegin = "";
	String tMonthEnd = "";
	String[] tMonthStr = tMonthLimit.split("-");
	if(tMonthStr.length==1)
	{
		tMonthBegin = tMonthStr[0];
		tMonthEnd = tMonthStr[0];
	}
	else
	{
		tMonthBegin = tMonthStr[0];
		tMonthEnd = tMonthStr[1];
	}
	if(Integer.parseInt(tCurrMonth)>=Integer.parseInt(tMonthBegin)&&
			Integer.parseInt(tCurrMonth)<=Integer.parseInt(tMonthEnd))
	{
		//在月份限制范围内,校验通过
		tSuccFlag = true;
	}
	
	
	return tSuccFlag;
 }
 
 /**
  * 解析校验条件,获得限制时间的各种参数
  * @param tCurrentDate
  * @param tLimitCondition
  * @return
  */
 private String[] getLimitBeginEnd(String tCurrentDate,String tLimitCondition)
 {
	 String[] tResult = new String[3];
	 String[] tLimit = tLimitCondition.split(";");
	 String tDateBegin = tLimit[0];
	 String tDateEnd = tLimit[1];
	 String tScanDate = tLimit[2];
	 //1^27 08:00:00;30 23:59:59;27 08:00:00 #
	 //2^26 08:00:00;+a 00:00:00;26 08:00:00 #
	 //3-12^28:08:00;b 23:59:59;28 08:00:00
	String tYearMonth = tCurrentDate.substring(0,8);
	logger.debug("tYearMonth:"+tYearMonth);
	 //处理起期
	 //判断处理类型 是直接描述时间还是计算标记
	 if(tDateBegin.indexOf("a")==-1&&tDateBegin.indexOf("b")==-1)
	 {
		 //描述的是时间
		 tDateBegin = tYearMonth + "" + tDateBegin;
		 
	 }
	 else
	 {
		 tDateBegin = changeContionToData(tCurrentDate,tDateBegin) ;
	 }
	 
	 //处理止期
	 if(tDateEnd.indexOf("a")==-1&&tDateEnd.indexOf("b")==-1)
	 {
		 //描述的是时间
		 tDateEnd = tYearMonth + "" + tDateEnd;
		 
	 }
	 else
	 {
		 tDateEnd = changeContionToData(tCurrentDate,tDateEnd);
	 }
	 
	 //处理扫描日期
	 if(tScanDate.indexOf("a")==-1&&tScanDate.indexOf("b")==-1)
	 {
		 //描述的是时间
		 tScanDate = tYearMonth + "" + tScanDate;
		 
	 }
	 else
	 {
		 tScanDate = changeContionToData(tCurrentDate,tScanDate);
	 }
	 tResult[0] = tDateBegin;
	 tResult[1] = tDateEnd;
	 tResult[2] = tScanDate;
	 
	 return tResult;
 }

}
