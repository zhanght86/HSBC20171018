package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPDutyBL;
import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPPremBL;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMDutyGetAliveDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMDutyGetAliveSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * Title: 保全重算类
 * </p>
 * <p>
 * Description: 通过传入的保单信息、责任信息、保费信息和领取信息进行重算
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-07-01
 */
public class ReCalBL {
private static Logger logger = Logger.getLogger(ReCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	Reflections mReflections = new Reflections();
	LPPolSchema mLPPolSchema;
	LPEdorItemSchema mLPEdorItemSchema;
	LJSGetEndorseSchema mLJSGetEndorseSchema;

	// 重算前数据
	public LCPolSchema preLCPolSchema = new LCPolSchema();
	public LCDutySet preLCDutySet = new LCDutySet();
	public LCPremSet preLCPremSet = new LCPremSet();
	public LCGetSet preLCGetSet = new LCGetSet();

	// 重算后数据
	public LPContSet aftLPContSet = new LPContSet();
	public LPPolSet aftLPPolSet = new LPPolSet();
	public LPDutySet aftLPDutySet = new LPDutySet();
	public LPPremSet aftLPPremSet = new LPPremSet();
	public LPGetSet aftLPGetSet = new LPGetSet();
	public LJSGetEndorseSet aftLJSGetEndorseSet = new LJSGetEndorseSet();
	double mGetMoney = 0;
	double mChgPrem = 0;

	// 特殊信息传值 add by zhangtao 2005-10-21
	private TransferData mTransferData = new TransferData();

	public ReCalBL(LPPolSchema pLPPolSchema, LPEdorItemSchema pLPEdorItemSchema) {
		mLPPolSchema = pLPPolSchema.getSchema();
		mLPEdorItemSchema = pLPEdorItemSchema.getSchema();
	}

	public ReCalBL(LPPolSchema pLPPolSchema,
			LPEdorItemSchema pLPEdorItemSchema, TransferData pTransferData) {
		mLPPolSchema = pLPPolSchema.getSchema();
		mLPEdorItemSchema = pLPEdorItemSchema.getSchema();
		mTransferData = pTransferData;
	}

	/**
	 * 准备数据，然后重算
	 * 
	 * @return
	 */
	public boolean recal() {
		try {
			LPEdorItemSchema tLPEdorItemSchema = mLPEdorItemSchema.getSchema();
			tLPEdorItemSchema.setPolNo(mLPPolSchema.getPolNo());

			// 准备重算需要的保单表数据
			LCPolBL tLCPolBL = getRecalPol(mLPPolSchema);

			// 准备重算需要的责任表数据
			LCDutyBLSet tLCDutyBLSet = getRecalDuty(tLPEdorItemSchema);

			// 准备重算需要的保费项表数据
			LCPremSet tLCPermSet = getRecalPrem(tLPEdorItemSchema);

			// 准备重算需要的领取项表数据
			LCGetBLSet tLCGetBLSet = getRecalGet(tLPEdorItemSchema);

			// 重算
			if (!recalWithData(tLCPolBL, tLCDutyBLSet, tLCPermSet, tLCGetBLSet,
					tLPEdorItemSchema)) {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "保全重算失败");

			return false;
		}

		return true;
	}

	/**
	 * 用准备好的数据进行重算
	 * 
	 * @param pLCPolBL
	 * @param pLCDutyBLSet
	 * @param pLCPermSet
	 * @param pLCGetBLSet
	 * @return
	 */
	public boolean recalWithData(LCPolBL pLCPolBL, LCDutyBLSet pLCDutyBLSet,
			LCPremSet pLCPermSet, LCGetBLSet pLCGetBLSet,
			LPEdorItemSchema pLPEdorItemSchema) {
		try {
			// 开始重算
			mTransferData.setNameAndValue("EdorNo", pLPEdorItemSchema
					.getEdorNo());
			CalBL tCalBL = new CalBL(pLCPolBL, pLCDutyBLSet, pLCGetBLSet,
					mTransferData, pLPEdorItemSchema.getEdorType());
			tCalBL.setOperator(pLPEdorItemSchema.getOperator());

//			if (CheckRiskAssociate(pLCPolBL.getRiskCode()) == 1) {
				if (!tCalBL.calPol()) {
					// CError.buildErr(this, "CalBL.calPol()计算失败",
					// tCalBL.mErrors);
					return false;
				}
//			} else if (CheckRiskAssociate(pLCPolBL.getRiskCode()) == 2) {
//				if (pLCPermSet != null && pLCPermSet.size() > 0) {
//					tCalBL.setNoCalFalg(true);
//				}
//				if (tCalBL.calPol2(pLCPermSet) == false) {
//					// CError.buildErr(this, "CalBL.calPol2()计算失败",
//					// tCalBL.mErrors);
//					return false;
//				}
//			} else {
//				CError.buildErr(this, "获取险种信息失败！");
//				return false;
//			}

			// 得到计算结果
			getCalResult(tCalBL.getLCPol(), tCalBL.getLCDuty(), tCalBL
					.getLCPrem(), tCalBL.getLCGet());
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "保全重算失败");

			return false;
		}

		return true;
	}

	/**
	 * 获取保全重算需要的保单表数据
	 * 
	 * @param pLPEdorItemSchema
	 * @return
	 */
	public LCPolBL getRecalPol(LPPolSchema pLPPolSchema) {
		// 准备重算需要的保单表数据
		LCPolSchema tLCPolSchema = new LCPolSchema();
		mReflections.transFields(tLCPolSchema, pLPPolSchema);

		LCPolBL tLCPolBL = new LCPolBL();
		tLCPolBL.setSchema(tLCPolSchema);

		// 备份重算前数据
		preLCPolSchema.setSchema(tLCPolSchema);

		return tLCPolBL;
	}

	/**
	 * 获取保全重算需要的责任表数据
	 * 
	 * @param pLPEdorItemSchema
	 * @return
	 */
	public LCDutyBLSet getRecalDuty(LPEdorItemSchema pLPEdorItemSchema) {
		LPDutyBL tLPDutyBL = new LPDutyBL();
		LPDutySet tLPDutySet = tLPDutyBL
				.queryAllLPDutyForReCal(pLPEdorItemSchema);

		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();

		if (tLPDutySet.size() > 1) {
			for (int i = 0; i < tLPDutySet.size(); i++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				mReflections.transFields(tLCDutySchema, tLPDutySet.get(i + 1));
				/**
				 * CalRule调整 对于个单如CalRule为3，均调整成1 Modify by lizhuo at 2005-10-19
				 */
				// Begin
				if (tLCDutySchema.getCalRule() != null
						&& tLCDutySchema.getCalRule().trim().equals("3")) {
					LCPolDB rLCPolDB = new LCPolDB();
					LCPolSet rLCPolSet = new LCPolSet();
					rLCPolDB.setPolNo(tLCDutySchema.getPolNo());
					rLCPolDB.setContType("1");
					rLCPolSet = rLCPolDB.query();
					if (rLCPolSet.size() > 0) {
						tLCDutySchema.setCalRule("0");
					}
				}
				// End
				tLCDutyBLSet.add(tLCDutySchema);
				// 备份重算前数据
				preLCDutySet.add(tLCDutySchema.getSchema());
			}
		} else if (tLPDutySet.size() == 1) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			mReflections.transFields(tLCDutySchema, tLPDutySet.get(1));
			// tLCDutySchema.setDutyCode(null);
			/**
			 * CalRule调整 对于个单如CalRule为3，均调整成1 Modify by lizhuo at 2005-10-19
			 */
			// Begin
			if (tLCDutySchema.getCalRule() != null
					&& tLCDutySchema.getCalRule().trim().equals("3")) {
				LCPolDB rLCPolDB = new LCPolDB();
				LCPolSet rLCPolSet = new LCPolSet();
				rLCPolDB.setPolNo(tLCDutySchema.getPolNo());
				rLCPolDB.setContType("1");
				rLCPolSet = rLCPolDB.query();
				if (rLCPolSet.size() > 0) {
					tLCDutySchema.setCalRule("0");
				}
			}
			// End

			tLCDutyBLSet.add(tLCDutySchema);

			// 备份重算前数据
			preLCDutySet.add(tLCDutySchema.getSchema());
		}

		return tLCDutyBLSet;
	}

	/**
	 * 获取保全重算需要的保费项表数据
	 * 
	 * @param pLPEdorItemSchema
	 * @return
	 */
	public LCPremSet getRecalPrem(LPEdorItemSchema pLPEdorItemSchema) {
		LPPremBL tLPPremBL = new LPPremBL();
		LPPremSet tLPPremSet = tLPPremBL
				.queryAllLPPremForReCal(pLPEdorItemSchema);

		LCPremSet tLCPremSet = new LCPremSet();

		for (int i = 0; i < tLPPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			mReflections.transFields(tLCPremSchema, tLPPremSet.get(i + 1));
			tLCPremSet.add(tLCPremSchema);

			// 备份重算前数据
			preLCPremSet.add(tLCPremSchema.getSchema());
		}

		return tLCPremSet;
	}

	/**
	 * 获取保全重算需要的领取项表数据
	 * 
	 * @param pLPEdorItemSchema
	 * @return
	 */
	public LCGetBLSet getRecalGet(LPEdorItemSchema pLPEdorItemSchema) {
		LPGetBL tLPGetBL = new LPGetBL();
		LPGetSet tLPGetSet = tLPGetBL.queryAllLPGetForReCal(pLPEdorItemSchema);

		LCGetBLSet tLCGetBLSet = new LCGetBLSet();

		for (int i = 0; i < tLPGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			
			mReflections.transFields(tLCGetSchema, tLPGetSet.get(i + 1));
			
			//对于MS如意年年两全保险(分红型) 特殊处理
			if("112207".equals(preLCPolSchema.getRiskCode()))
			{
				// 如果是趸交，则需要特殊处理
				if(preLCPolSchema.getPayIntv()==0)
				{
					//为后续重算，提前对LCGet的GetDutyKind进行重置
					LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
					tLMDutyGetAliveDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
					tLMDutyGetAliveDB.setGetStartUnit("Y");
					tLMDutyGetAliveDB.setGetStartPeriod(1); //描叙表为领取年为交费期满的下一年
					LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.query();
					if(tLMDutyGetAliveSet.size()>0)
					{
						tLCGetSchema.setGetDutyKind(tLMDutyGetAliveSet.get(1).getGetDutyKind());
					}
				}else
				{
					//为后续重算，提前对LCGet的GetDutyKind进行重置
					LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
					tLMDutyGetAliveDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
					tLMDutyGetAliveDB.setGetStartUnit("Y");
					tLMDutyGetAliveDB.setGetStartPeriod(preLCPolSchema.getPayEndYear()+1); //描叙表为领取年为交费期满的下一年
					LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.query();
					if(tLMDutyGetAliveSet.size()>0)
					{
						tLCGetSchema.setGetDutyKind(tLMDutyGetAliveSet.get(1).getGetDutyKind());
					}
					
				}				
			}

			//对于MS长顺两全保险(分红型) 特殊处理
			if("112202".equals(preLCPolSchema.getRiskCode()))
			{
				// 如果A 计划，即交至60岁，到60岁领取生存保险金,无需重置GetDutyKind,因为领取时间已经确定，所以不做处理
				// 如果B 计划，分10年交，20年交，领取起始日期为缴费结束日期,由于B计划不支持趸交，所以无需对趸交的情况进行处理
                if("Y".equals(preLCPolSchema.getPayEndYearFlag())&&(preLCPolSchema.getPayEndYear()==20||preLCPolSchema.getPayEndYear()==10))
				{
					//为后续重算，提前对LCGet的GetDutyKind进行重置
					LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
					tLMDutyGetAliveDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
					tLMDutyGetAliveDB.setGetStartUnit("Y");
					tLMDutyGetAliveDB.setGetStartPeriod(preLCPolSchema.getPayEndYear()); 
					LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.query();
					if(tLMDutyGetAliveSet.size()>0)
					{
						tLCGetSchema.setGetDutyKind(tLMDutyGetAliveSet.get(1).getGetDutyKind());
					}
					
				}				
			}


			tLCGetBLSet.add(tLCGetSchema);

			// 备份重算前数据
			preLCGetSet.add(tLCGetSchema.getSchema());
		}

		return tLCGetBLSet;
	}

	/**
	 * 判断险种是否和帐户关联-如果和帐户关联，那么在重算的时候需要传入保费项作为计算要素
	 * 
	 * @param RiskcCode
	 * @return
	 */
	public int CheckRiskAssociate(String aRiskcCode) {
		// LMRiskToAccDB tLMRiskToAccDB = new LMRiskToAccDB();
		// tLMRiskToAccDB.setRiskCode(RiskcCode);
		//
		// LMRiskToAccSet tLMRiskToAccSet = tLMRiskToAccDB.query();
		//
		// if (tLMRiskToAccSet == null)
		// {
		// return -1;
		// }
		//
		// for (int n = 1; n <= tLMRiskToAccSet.size(); n++)
		// {
		// if (tLMRiskToAccSet.get(n).getInsuAccNo().equals("000002")
		// || tLMRiskToAccSet.get(n).getInsuAccNo().equals("000003"))
		// {
		// return true;
		// }
		// }
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(aRiskcCode);
		if (!tLMRiskAppDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
			mErrors.addOneError(new CError("查询险种信息失败！"));
			return -1;
		}

		if (tLMRiskAppDB.getInpPayPlan() == null
				|| tLMRiskAppDB.getInpPayPlan().equals("")) {
			return 1;
		} else if (tLMRiskAppDB.getInpPayPlan().equals("N")) {
			return 1;
		} else if (tLMRiskAppDB.getInpPayPlan().equals("Y")) {
			return 2;
		} else {
			return -1;
		}
	}

	/**
	 * 处理重算结果
	 * 
	 * @param pLCPolBL
	 * @param pLCDutyBLSet
	 * @param pLCPremBLSet
	 * @param pLCGetBLSet
	 * @return
	 */
	private boolean getCalResult(LCPolBL pLCPolBL, LCDutyBLSet pLCDutyBLSet,
			LCPremBLSet pLCPremBLSet, LCGetBLSet pLCGetBLSet) {
		// 获取重算后保单表数据，并重置原来的的保单表未变更信息
		LPPolSchema tLPPolSchema = new LPPolSchema();
		mReflections.transFields(tLPPolSchema, pLCPolBL.getSchema());
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchema.setPaytoDate(preLCPolSchema.getPaytoDate());
		tLPPolSchema.setSignCom(preLCPolSchema.getSignCom());
		tLPPolSchema.setSignDate(preLCPolSchema.getSignDate());
		tLPPolSchema.setAppFlag(preLCPolSchema.getAppFlag());
		tLPPolSchema.setHandler(preLCPolSchema.getHandler());
		tLPPolSchema.setStandPrem(tLPPolSchema.getPrem());
		tLPPolSchema.setPrem(tLPPolSchema.getPrem());
		tLPPolSchema.setAmnt(tLPPolSchema.getAmnt());
		aftLPPolSet.add(tLPPolSchema);

		// 获取重算后责任表数据，并重置原来的的责任表未变更信息
		for (int i = 1; i <= pLCDutyBLSet.size(); i++) {
			LPDutySchema tLPDutySchema = new LPDutySchema();
			mReflections.transFields(tLPDutySchema, pLCDutyBLSet.get(i)
					.getSchema());

			tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutySchema.setOperator(mLPEdorItemSchema.getOperator());
			tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLPDutySchema.setModifyTime(PubFun.getCurrentTime());

			aftLPDutySet.add(tLPDutySchema);
		}

		// 补上由于重算导致的丢失的责任数据
		if (aftLPDutySet.size() == 1) {
			// 此时dutycode 为空
			aftLPDutySet.get(1).setSumPrem(preLCDutySet.get(1).getSumPrem());
			aftLPDutySet.get(1)
					.setPaytoDate(preLCDutySet.get(1).getPaytoDate());
		} else {
			for (int index = 1; index <= preLCDutySet.size(); index++) {
				LCDutySchema preDutySchema = preLCDutySet.get(index);

				for (int j = 1; j <= aftLPDutySet.size(); j++) {
					if (aftLPDutySet.get(j).getDutyCode().equals(
							preDutySchema.getDutyCode())) {
						aftLPDutySet.get(j).setSumPrem(
								preDutySchema.getSumPrem());
						aftLPDutySet.get(j).setPaytoDate(
								preDutySchema.getPaytoDate());

						break;
					}
				}
			}
		}

		// 获取重算后保费项表数据，,补上保费项丢失的信息(交至日期)
		for (int i = 1; i <= pLCPremBLSet.size(); i++) {
			LPPremSchema tLPPremSchema = new LPPremSchema();
			mReflections.transFields(tLPPremSchema, pLCPremBLSet.get(i)
					.getSchema());

			for (int j = 1; j <= preLCPremSet.size(); j++) {
				if (tLPPremSchema.getPolNo().equals(
						preLCPremSet.get(j).getPolNo())
						&& tLPPremSchema.getDutyCode().equals(
								preLCPremSet.get(j).getDutyCode())
						&& tLPPremSchema.getPayPlanCode().equals(
								preLCPremSet.get(j).getPayPlanCode())) {
					tLPPremSchema.setPaytoDate(preLCPremSet.get(j)
							.getPaytoDate());
					tLPPremSchema.setSumPrem(preLCPremSet.get(j).getSumPrem());
					// 置回交费次数
					tLPPremSchema
							.setPayTimes(preLCPremSet.get(j).getPayTimes());
					// 置回豁免标志
					tLPPremSchema
							.setFreeFlag(preLCPremSet.get(j).getFreeFlag());
					tLPPremSchema
							.setFreeRate(preLCPremSet.get(j).getFreeRate());
					tLPPremSchema.setFreeStartDate(preLCPremSet.get(j)
							.getFreeStartDate());
					tLPPremSchema.setFreeEndDate(preLCPremSet.get(j)
							.getFreeEndDate());
					
					//add by jiaqiangli 2009-02-12 保全重算的现场恢复（好像汇编中的中断哦）
					//lpprem.makedate maketime会在calbl.java中置为sysdate,systime 对保全而言需要保留现场
					tLPPremSchema.setMakeDate(preLCPremSet.get(j).getMakeDate());
					tLPPremSchema.setMakeTime(preLCPremSet.get(j).getMakeTime());
					//lpprem.makedate maketime会在calbl.java中置为sysdate,systime 对保全而言需要保留现场
					//add by jiaqiangli 2009-02-12 保全重算的现场恢复（好像汇编中的中断哦）
				}
			}

			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremSchema.setOperator(mLPEdorItemSchema.getOperator());
			tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPremSchema.setModifyTime(PubFun.getCurrentTime());

			aftLPPremSet.add(tLPPremSchema);
		}

		// 补上因重算丢失的保费信息（承保、核保加费）
		for (int m = 0; m < preLCPremSet.size(); m++) {
			boolean isExistPrem = false;

			for (int n = 0; n < aftLPPremSet.size(); n++) {
				// 发现重算后的保费项集合中有数据，则退出内循环
				if (aftLPPremSet.get(n + 1).getDutyCode().equals(
						preLCPremSet.get(m + 1).getDutyCode())
						&& aftLPPremSet.get(n + 1).getPayPlanCode().equals(
								preLCPremSet.get(m + 1).getPayPlanCode())) {
					isExistPrem = true;

					break;
				}
			}

			if (!isExistPrem) {
				// 为责任项补上丢失保费项的保费信息
				boolean isExistDuty = false;

				for (int l = 0; l < aftLPDutySet.size(); l++) {
					LPDutySchema tLPDutySchema = aftLPDutySet.get(l + 1);

					if (tLPDutySchema.getDutyCode().equals(
							preLCPremSet.get(m + 1).getDutyCode())) {
						tLPDutySchema.setPrem(tLPDutySchema.getPrem()
								+ preLCPremSet.get(m + 1).getPrem());
						tLPDutySchema.setSumPrem(tLPDutySchema.getSumPrem()
								+ preLCPremSet.get(m + 1).getSumPrem());
						isExistDuty = true;
					}
				}

				if (!isExistDuty) {
					break;
				}

				// 未找到保费项数据，则为丢失数据，加入到重算结果中
				LPPremSchema tLPPremSchema = new LPPremSchema();
				mReflections.transFields(tLPPremSchema, preLCPremSet.get(m + 1)
						.getSchema());
				tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremSchema.setOperator(mLPEdorItemSchema.getOperator());
				tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
				tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
				aftLPPremSet.add(tLPPremSchema);

				// 为保单补上丢失保费项的保费信息
				for (int l = 0; l < aftLPPolSet.size(); l++) {
					tLPPolSchema = aftLPPolSet.get(l + 1);

					if (tLPPolSchema.getPolNo().equals(
							preLCPremSet.get(m + 1).getPolNo())) {
						tLPPolSchema.setPrem(tLPPolSchema.getPrem()
								+ preLCPremSet.get(m + 1).getPrem());
						tLPPolSchema.setSumPrem(tLPPolSchema.getSumPrem()
								+ preLCPremSet.get(m + 1).getSumPrem());
					}
				}
			}
		}

		// 处理领取项数据
		for (int i = 1; i <= pLCGetBLSet.size(); i++) {
			LPGetSchema tLPGetSchema = new LPGetSchema();
			mReflections.transFields(tLPGetSchema, pLCGetBLSet.get(i)
					.getSchema());

			for (int j = 1; j <= preLCGetSet.size(); j++) {
				if (tLPGetSchema.getPolNo().equals(
						preLCGetSet.get(j).getPolNo())
						&& tLPGetSchema.getDutyCode().equals(
								preLCGetSet.get(j).getDutyCode())
						&& tLPGetSchema.getGetDutyCode().equals(
								preLCGetSet.get(j).getGetDutyCode())) {
					tLPGetSchema.setUrgeGetFlag(preLCGetSet.get(j)
							.getUrgeGetFlag());
					// XinYQ added on 2007-03-12 进入领取期之后的保单要注意更新 GettoDate 等
					//不再修改
					//tLPGetSchema 内部重算是支持的 GettoDate的重置
//							.setGettoDate(preLCGetSet.get(j).getGettoDate());
					//add by jiaqiangli 2009-09-23 是需要重置的 86330120040210001777 lis-9419
					//导致已经开始领取了，之后做IC重算后，gettodate又被重置为getstartdate CT的getdraw校验出错
					
					//add by jiaqiangli 2009-09-24 与蒋莱讨论后的结果 对生存金满期金做如下增强判断
					//if c.getstartdate==p.getstartdate then p.gettodate=c.gettodate 否则calbl重算出来的gettodate=getstartdate对已经开始领取的会重新催付
					//但对于诸如长顺的20年变10年则不能重置 否则getstartdate='2018-10-25'而gettodate='2028-10-25'
					if (tLPGetSchema.getGetStartDate() != null && preLCGetSet.get(j).getGetStartDate() != null) {
						if ("0".equals(tLPGetSchema.getLiveGetType())
								&& tLPGetSchema.getGetStartDate().equals(preLCGetSet.get(j).getGetStartDate()))
							//也可以判断c.getstartdate<>c.gettodate 则需要重置
							tLPGetSchema.setGettoDate(preLCGetSet.get(j).getGettoDate());
					}
					//comment by jiaqiangli 产品定义就保证了已经领取的变年龄不会导致getstartdate的变化
					//comment by jiaqiangli 已经进入领取期的重要资料变更如增大一岁 此问题解决不了 应该是不能做此保全项
					//add by jiaqiangli 2009-09-23 是需要重置的 86330120040210001777 lis-9419
					
					tLPGetSchema.setSumMoney(preLCGetSet.get(j).getSumMoney());
					//comment by jiaqiangli 2009-04-14 注释掉，由契约，理赔维护字段，保全不做处理 
					//add by jiaqiangli 2009-04-11 actuget字段在1413CalBL.calOneDuty需要重置回来
					//tLPGetSchema.setActuGet(preLCGetSet.get(j).getActuGet());
					// add by jiaqiangli 2009-04-11 actuget字段在1413CalBL.calOneDuty需要重置回来
					break;
				}
			}
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetSchema.setOperator(mLPEdorItemSchema.getOperator());
			tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGetSchema.setModifyTime(PubFun.getCurrentTime());

			aftLPGetSet.add(tLPGetSchema);
		}

		// 产生交退费信息
		// if (aftLPPremSet.size() > 0)
		// {
		// double tChgPrem = 0;
		// double tGetMoney = 0;
		//
		// for (int i = 1; i <= aftLPPremSet.size(); i++)
		// {
		// LPPremSchema tLPPremSchema = aftLPPremSet.get(i);
		//
		// //计算标记
		// boolean tCalFlag = false;
		//
		// for (int j = 1; j <= preLCPremSet.size(); j++)
		// {
		// if (tLPPremSchema.getPolNo().equals(preLCPremSet.get(j)
		// .getPolNo())
		// && tLPPremSchema.getDutyCode().equals(preLCPremSet.get(j)
		// .getDutyCode())
		// && tLPPremSchema.getPayPlanCode().equals(preLCPremSet.get(j)
		// .getPayPlanCode()))
		// {
		// //重算后的保费大于原保费
		// if (tLPPremSchema.getPrem() > preLCPremSet.get(j)
		// .getPrem())
		// {
		// if (PubFun.getApproximation(tLPPremSchema.getPrem()
		// - preLCPremSet.get(j)
		// .getPrem()) != 0)
		// {
		// tCalFlag = true;
		// tChgPrem = (tChgPrem + tLPPremSchema.getPrem())
		// - preLCPremSet.get(j).getPrem();
		//
		// //从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		// String finType = BqCalBL.getFinType(mLPEdorItemSchema
		// .getEdorType(),
		// "BF",
		// tLPPremSchema
		// .getPolNo());
		//
		// if (finType.equals(""))
		// {
		// CError.buildErr(this,
		// "在LDCode1中缺少保全财务接口转换类型编码");
		//
		// return false;
		// }
		//
		// this.CalGetMoney(tLPPremSchema,
		// tLPPremSchema.getPrem()
		// - preLCPremSet.get(j).getPrem(),
		// finType);
		//
		// //生成利息交费项
		// double interest = 0; // 这儿调用利息计算函数，目前暂时定为0
		// double balance = tLPPremSchema.getPrem()
		// - preLCPremSet.get(j).getPrem();
		// AccountManage tAccountManage = new AccountManage();
		// interest = tAccountManage.getMultiAccInterest("0",
		// balance,
		// preLCPremSet.get(j)
		// .getPayStartDate(),
		// mLPEdorItemSchema
		// .getEdorValiDate(),
		// "C",
		// "D");
		//
		// PubFun.out(this,
		// "-----interest is : " + interest);
		//
		// LJSGetEndorseSchema tInterestLJSGetEndorseSchema =
		// mLJSGetEndorseSchema
		// .getSchema();
		// tInterestLJSGetEndorseSchema.setPayPlanCode(tLPPremSchema
		// .getPayPlanCode());
		// tInterestLJSGetEndorseSchema.setOtherNo(tLPPremSchema
		// .getPayPlanCode());
		// tInterestLJSGetEndorseSchema.setOtherNoType("PAY");
		//
		// //从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		// finType = BqCalBL.getFinType(mLPEdorItemSchema
		// .getEdorType(),
		// "LX",
		// tLPPremSchema
		// .getPolNo());
		//
		// if (finType.equals(""))
		// {
		// CError.buildErr(this,
		// "在LDCode1中缺少保全财务接口转换类型编码");
		//
		// return false;
		// }
		// tInterestLJSGetEndorseSchema.setFeeFinaType(finType);
		//
		// tInterestLJSGetEndorseSchema.setDutyCode(tLPPremSchema
		// .getDutyCode());
		// tInterestLJSGetEndorseSchema.setGetMoney((double) interest);
		// tInterestLJSGetEndorseSchema.setGetFlag("0");
		//
		// mLJSGetEndorseSchema.setPayPlanCode(tLPPremSchema
		// .getPayPlanCode());
		// mLJSGetEndorseSchema.setOtherNo(tLPPremSchema
		// .getPayPlanCode());
		// mLJSGetEndorseSchema.setOtherNoType("PAY");
		// mLJSGetEndorseSchema.setDutyCode(tLPPremSchema
		// .getDutyCode());
		// mLJSGetEndorseSchema.setGetFlag("0");
		// tGetMoney = tGetMoney
		// + mLJSGetEndorseSchema.getGetMoney()
		// + tInterestLJSGetEndorseSchema
		// .getGetMoney();
		//
		// aftLJSGetEndorseSet.add(mLJSGetEndorseSchema
		// .getSchema());
		//
		// if (interest != 0.0)
		// {
		// aftLJSGetEndorseSet.add(tInterestLJSGetEndorseSchema
		// .getSchema());
		// }
		// }
		// }
		//
		// //重算后保费小于原保费
		// else if (tLPPremSchema.getPrem() < preLCPremSet.get(j)
		// .getPrem())
		// {
		// if (PubFun.getApproximation(tLPPremSchema.getPrem()
		// - preLCPremSet.get(j)
		// .getPrem()) != 0)
		// {
		// tCalFlag = true;
		// tChgPrem = tChgPrem
		// - preLCPremSet.get(j).getPrem()
		// + tLPPremSchema.getPrem();
		//
		// //从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		// String finType = BqCalBL.getFinType(mLPEdorItemSchema
		// .getEdorType(),
		// "TF",
		// tLPPremSchema
		// .getPolNo());
		//
		// if (finType.equals(""))
		// {
		// CError.buildErr(this,
		// "在LDCode1中缺少保全财务接口转换类型编码");
		//
		// return false;
		// }
		//
		// this.CalGetMoney(tLPPremSchema,
		// preLCPremSet.get(j).getPrem()
		// - tLPPremSchema.getPrem(),
		// finType);
		//
		// mLJSGetEndorseSchema.setPayPlanCode(tLPPremSchema
		// .getPayPlanCode());
		// mLJSGetEndorseSchema.setOtherNo(tLPPremSchema
		// .getPayPlanCode());
		// mLJSGetEndorseSchema.setOtherNoType("GET");
		// mLJSGetEndorseSchema.setGetFlag("1");
		// mLJSGetEndorseSchema.setDutyCode(tLPPremSchema
		// .getDutyCode());
		// tGetMoney = tGetMoney
		// - mLJSGetEndorseSchema.getGetMoney();
		//
		// aftLJSGetEndorseSet.add(mLJSGetEndorseSchema
		// .getSchema());
		// }
		// }
		// }
		// }
		//
		// if (tCalFlag == true)
		// {
		// mGetMoney = mGetMoney + tGetMoney;
		// mChgPrem = mChgPrem + tChgPrem;
		// }
		// }
		// }

		return true;
	}

	/**
	 * 交退费计算
	 * 
	 * @return
	 */
	private boolean CalGetMoney(LPPremSchema tLPPremSchema, double aChgPrem,
			String aFeeType) {
		BqCalBase tBqCalBase = new BqCalBase();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = mLPEdorItemSchema.getOperator();
		tGlobalInput.ManageCom = mLPEdorItemSchema.getManageCom();

		BqCalBL tBqCalBL = new BqCalBL(tBqCalBase, "", "");
		mLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(mLPEdorItemSchema,
				preLCPolSchema, null, aFeeType, aFeeType, aChgPrem,
				tGlobalInput);

		return true;
	}
}
