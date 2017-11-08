package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 新保加人保全项目</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * ReWrite ZhangRong
 * @version 1.0
 */

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPolOtherDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPPolOtherSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolOtherSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class GrpEdorNRDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorNRDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 额外传递的参数 */

	/** 传入的业务数据 */
	private Reflections mRef = new Reflections();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	private String CValidate; // 生效日期
	private String Enddate; // 终止日期
	private String Paytodate; // 交至日期
	private String Payenddate; // 终交日期


	/** 传出的业务数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	public GrpEdorNRDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		
		PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "GrpEdorNRDetailBLF";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

		logger.debug("End GEdorNRDetail PubSubmit OK...");

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败!");
			return false;
		}

		if (mGlobalInput == null || mLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "接收数据失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 删除上次保存过的数据
		if (!delPData()) {
			return false;
		}

		LPContSet aLPContSet = new LPContSet();
		LCContSet aLCContSet = new LCContSet();// 待更新C表数据使用
		LPContSchema aLPContSchema;
		LCContSchema aLCContSchema;
		LPPolSet aLPPolSet = new LPPolSet();
		LCPolSet aLCPolSet = new LCPolSet();
		LPPolSchema aLPPolSchema;
		LCPolSchema aLCPolSchema;
		LPDutySet aLPDutySet = new LPDutySet();
		LCDutySet aLCDutySet = new LCDutySet();
		LPDutySchema aLPDutySchema;
		LCDutySchema aLCDutySchema;
		LPPremSet aLPPremSet = new LPPremSet();
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSchema aLPPremSchema;
		LCPremSchema aLCPremSchema;
		LPGetSet aLPGetSet = new LPGetSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSchema aLPGetSchema;
		LCGetSchema aLCGetSchema;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();
		LCPolOtherSet aLCPolOtherSet = new LCPolOtherSet();
		LPPolOtherSchema aLPPolOtherSchema;
		LCPolOtherSchema aLCPolOtherSchema = new LCPolOtherSchema();
		// --------------

		LJSPayPersonSet aLJSPayPersonSet = new LJSPayPersonSet();
		LJSPayPersonSchema aLJSPayPersonSchema = null;
		LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSchema aLJSGetEndorseSchema = null;
		LPEdorMainSet aLPEdorMainSet = new LPEdorMainSet();
		LPEdorMainSchema aLPEdorMainSchema = null;
		LPEdorItemSet aLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemSchema aLPEdorItemSchema = null;

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}

		// 取出该团单下各个险种的原始被保人的保险期止期 
		//SSRS tSSRS = getOldEndDate(mLPGrpEdorItemSchema.getGrpContNo());
		
		String tSql = "select * from lccont where grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"' and "
					+ " contno in (select contno from lcpol where grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"' and appflag = '2')";

		double AllPrem = 0;
		double AllInterest = 0;
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContSet = tLCContDB.executeQuery(tSql);
		if (tLCContSet.size() < 1) {
			CError.buildErr(this, "查询新增保单信息失败!");
			return false;
		}
		for (int i = 1; i <= tLCContSet.size(); i++) {
			// 确定CValidate,Enddate 待定
			if (!CertifyDate()) {
				return false;
			}
			
			String ContNo = tLCContSet.get(i).getContNo();
			String tSQL = "select max(enddate) from lcpol where contno = '"+ContNo+"' and appflag = '1'";
			ExeSQL tExeSQL = new ExeSQL();
			String tContPayToDate = tExeSQL.getOneValue(tSQL);
			if(tContPayToDate == null ||tContPayToDate.equals(""))
			{
				CError.buildErr(this, "查询被保人合同终止日期失败,被保人号："+ tLCContSet.get(i).getInsuredNo());
				return false;
			}
			int intv = PubFun.calInterval(CValidate, tContPayToDate, "D");//计算出该团单险种中断了几天
			if(intv<=0)
			{
				CError.buildErr(this, "被保人合同已经过期,不能添加!被保人号："
						+ tLCContSet.get(i).getInsuredNo());
				return false;
			}
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setContNo(ContNo);
			tLCPolDB.setAppFlag("2");
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet.size() < 1) {
				CError.buildErr(this, "查询新增险种信息失败!被保人号："
						+ tLCContSet.get(i).getInsuredNo());
				return false;
			}
			for (int j = 1; j <= tLCPolSet.size(); j++) {
				aLCPolSchema = tLCPolSet.get(j);

				// 先备份原始数据
				Enddate = aLCPolSchema.getEndDate();
				Payenddate = aLCPolSchema.getPayEndDate();
				Paytodate = aLCPolSchema.getPaytoDate();
				
				intv = PubFun.calInterval(Enddate, tContPayToDate, "D");//计算出该团单险种中断了几天
				if(intv>0)
				{
					CError.buildErr(this, "被保人新增险种的止期小于主险的止期!不能添加!\n被保人号："
							+ tLCContSet.get(i).getInsuredNo()+"新增险种号:"+aLCPolSchema.getRiskCode());
					return false;
				}
				String tRiskCode = aLCPolSchema.getRiskCode();
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(tRiskCode);
				if(!tLMRiskAppDB.getInfo())
				{
					CError.buildErr(this, "查询新增险种定义失败!被保人号："
							+ tLCContSet.get(i).getInsuredNo());
					return false;
				}

				if (!tLMRiskAppDB.getRiskPeriod().equals("L")) {
					// 短期险
					String sEnddate = aLCPolSchema.getEndDate();
					

					if(tContPayToDate != null && !tContPayToDate.equals(""))
					{
						int tIntv = PubFun.calInterval(sEnddate, tContPayToDate, "D");
						if(tIntv<0)
						{
							sEnddate = tContPayToDate;
						}
					}
					
					if (sEnddate != null && !sEnddate.equals("")) {
						Enddate = sEnddate; // 新增被保人保险止期与原有被保人取齐
						Payenddate = Enddate; // 缴费止期即为保险止期
						Paytodate = Payenddate; // 交至日期即为缴费止期
					}
				} else {
					// 长期险，调用新契约生成的数据已经正确，不用再做修改
					Paytodate = Payenddate; // 加人都是趸交，交至日期即为缴费止期
				}
				String PolNo = aLCPolSchema.getPolNo();
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				tLCDutyDB.setPolNo(PolNo);
				tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet.size() < 1) {
					CError.buildErr(this, "查询新增被保人责任信息失败!");
					return false;
				}
				for (int k = 1; k <= tLCDutySet.size(); k++) {
					// //待处理CValidate，Enddate, Paytodate
					String DutyCode = tLCDutySet.get(k).getDutyCode();
					LCPremDB tLCPremDB = new LCPremDB();
					LCPremSet tLCPremSet = new LCPremSet();
					tLCPremDB.setPolNo(PolNo);
					tLCPremDB.setDutyCode(DutyCode);
					tLCPremSet = tLCPremDB.query();
					if (tLCPremSet.size() < 1) {
						CError.buildErr(this, "查询新增被保人保费信息失败!");
						return false;
					}
					for (int m = 1; m <= tLCPremSet.size(); m++) {
						// 待处理CValidate，Enddate —— firstPaytodate,paytodate,
						aLCPremSchema = tLCPremSet.get(m).getSchema();
						aLCPremSchema.setPayStartDate(CValidate);
						aLCPremSchema.setPaytoDate(Paytodate);
						aLCPremSchema.setPayEndDate(Payenddate);
						aLCPremSet.add(aLCPremSchema);
						// 根据保费项生成批改补退费信息
						aLJSGetEndorseSchema = this.initLJSGetEndorse(
								aLCPolSchema, aLCPremSchema, "BF");
						if (aLJSGetEndorseSchema == null) {
							return false;
						}
						aLJSGetEndorseSet.add(aLJSGetEndorseSchema);

						// 根据保费项生成财务应收数据
						aLJSPayPersonSchema = this
						.initLJSPayPerson(aLJSGetEndorseSchema);
						aLJSPayPersonSchema.setCurPayToDate(aLCPremSchema
								.getPaytoDate());
						aLJSPayPersonSet.add(aLJSPayPersonSchema);
						

						aLPPremSchema = new LPPremSchema();
						mRef.transFields(aLPPremSchema, aLCPremSchema);
						aLPPremSchema.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						aLPPremSchema.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						aLPPremSchema.setOperator(mGlobalInput.Operator);
						aLPPremSchema.setModifyDate(CurrDate);
						aLPPremSchema.setModifyTime(CurrTime);
						aLPPremSet.add(aLPPremSchema);
					}

					LCGetDB tLCGetDB = new LCGetDB();
					LCGetSet tLCGetSet = new LCGetSet();
					tLCGetDB.setPolNo(PolNo);
					tLCGetDB.setDutyCode(DutyCode);
					tLCGetSet = tLCGetDB.query();
					if (tLCGetSet.size() < 1) {
						CError.buildErr(this, "查询新增被保人给付责任信息失败!");
						return false;
					}
					for (int m = 1; m <= tLCGetSet.size(); m++) {
						// 待处理CValidate，Enddate —— getstartdate,getenddate,
						aLCGetSchema = tLCGetSet.get(m).getSchema();
						// aLCGetSchema.setGetStartDate(CValidate);
						// aLCGetSchema.setGettoDate(CValidate);
						// aLCGetSchema.setGetEndDate(Enddate);
						aLCGetSet.add(aLCGetSchema);

						aLPGetSchema = new LPGetSchema();
						mRef.transFields(aLPGetSchema, aLCGetSchema);
						aLPGetSchema
								.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
						aLPGetSchema.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						aLPGetSchema.setOperator(mGlobalInput.Operator);
						aLPGetSchema.setModifyDate(CurrDate);
						aLPGetSchema.setModifyTime(CurrTime);
						aLPGetSet.add(aLPGetSchema);
					}

					aLCDutySchema = tLCDutySet.get(k).getSchema();
					aLCDutySchema.setCValiDate(CValidate);
					aLCDutySchema.setEndDate(Enddate);
					aLCDutySchema.setFirstPayDate(CValidate);
					aLCDutySchema.setPaytoDate(Paytodate);
					aLCDutySchema.setPayEndDate(Payenddate);
					aLCDutySet.add(aLCDutySchema);

					aLPDutySchema = new LPDutySchema();
					mRef.transFields(aLPDutySchema, aLCDutySchema);
					aLPDutySchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
					aLPDutySchema.setEdorType(mLPGrpEdorItemSchema
							.getEdorType());
					aLPDutySchema.setOperator(mGlobalInput.Operator);
					aLPDutySchema.setModifyDate(CurrDate);
					aLPDutySchema.setModifyTime(CurrTime);
					aLPDutySet.add(aLPDutySchema);
				}

				// 处理险种表数据
				aLCPolSchema = aLCPolSchema.getSchema();
				aLCPolSchema.setSumPrem(aLCPolSchema.getPrem());
				aLCPolSchema.setSignCom(mGlobalInput.ManageCom);
				aLCPolSchema.setCValiDate(CValidate);
				aLCPolSchema.setSignDate(CValidate);
				aLCPolSchema.setSignTime(CurrTime);
				aLCPolSchema.setFirstPayDate(CValidate);
				aLCPolSchema.setEndDate(Enddate);
				// aLCPolSchema.setGetStartDate(CValidate);
				aLCPolSchema.setPaytoDate(Paytodate);
				aLCPolSchema.setPayEndDate(Payenddate);
				aLCPolSchema.setSpecifyValiDate("Y");
				aLCPolSchema.setUWFlag("9");
				aLCPolSet.add(aLCPolSchema);

				aLPPolSchema = new LPPolSchema();
				mRef.transFields(aLPPolSchema, aLCPolSchema);
				aLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				aLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				aLPPolSchema.setOperator(mGlobalInput.Operator);
				aLPPolSchema.setModifyDate(CurrDate);
				aLPPolSchema.setModifyTime(CurrTime);
				aLPPolSet.add(aLPPolSchema);
				AllPrem += aLPPolSchema.getPrem();
				aLPEdorItemSchema = initLPEdorItem(aLCPolSchema);
				aLPEdorItemSchema.setInsuredNo(tLCContSet.get(i).getInsuredNo());
				aLPEdorItemSet.add(aLPEdorItemSchema);
				
			}

			// 处理保单表数据
			aLCContSchema = tLCContSet.get(i).getSchema();
			aLCContSchema.setSignDate(CValidate);
			aLCContSchema.setSignTime(CurrTime);
			aLCContSchema.setCValiDate(CValidate);
			aLCContSchema.setPaytoDate(Paytodate);
			aLCContSchema.setFirstPayDate(CValidate);
			aLCContSchema.setInputOperator(mGlobalInput.Operator);
			aLCContSchema.setInputDate(CurrDate);
			aLCContSchema.setInputTime(CurrTime);
			aLCContSchema.setPolApplyDate(CurrDate);
			aLCContSchema.setGetPolDate(CValidate);
			aLCContSchema.setGetPolTime(CurrTime);
			aLCContSchema.setCustomGetPolDate(CValidate);
			aLCContSchema.setUWFlag("9");
			aLCContSet.add(aLCContSchema);

			aLPContSchema = new LPContSchema();
			mRef.transFields(aLPContSchema, aLCContSchema);
			aLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			aLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			aLPContSchema.setModifyDate(CurrDate);
			aLPContSchema.setModifyTime(CurrTime);
			aLPContSet.add(aLPContSchema);

			// AllPrem += tLCContSet.get(i).getPrem();

			

			aLPEdorMainSchema = initLPEdorMain(aLPEdorItemSchema);
			aLPEdorMainSet.add(aLPEdorMainSchema);

			LCPolOtherDB tLCPolOtherDB = new LCPolOtherDB();
			LCPolOtherSet tLCPolOtherSet = new LCPolOtherSet();
			tLCPolOtherDB.setContNo(tLCContSet.get(i).getContNo());
			tLCPolOtherSet = tLCPolOtherDB.query();
			if (tLCPolOtherSet != null && tLCPolOtherSet.size() > 0) {
				for (int j = 1; j <= tLCPolOtherSet.size(); j++) {
					aLCPolOtherSchema.setSchema(tLCPolOtherSet.get(j)
							.getSchema());
					aLCPolOtherSchema.setOperator(mGlobalInput.Operator);
					aLCPolOtherSchema.setModifyDate(CurrDate);
					aLCPolOtherSchema.setModifyTime(CurrTime);
					aLCPolOtherSet.add(aLCPolOtherSchema);

					aLPPolOtherSchema = new LPPolOtherSchema();
					mRef.transFields(aLPPolOtherSchema, aLCPolOtherSchema);
					aLPPolOtherSchema.setEdorNo(mLPGrpEdorItemSchema
							.getEdorNo());
					aLPPolOtherSchema.setEdorType(mLPGrpEdorItemSchema
							.getEdorType());
					aLPPolOtherSchema.setOperator(mGlobalInput.Operator);
					aLPPolOtherSchema.setModifyDate(CurrDate);
					aLPPolOtherSchema.setModifyTime(CurrTime);
					aLPPolOtherSet.add(aLPPolOtherSchema);
				}
			}

		}

		mLPGrpEdorItemSchema.setEdorState("1");
		mLPGrpEdorItemSchema.setGetMoney(AllPrem + AllInterest);
		mLPGrpEdorItemSchema.setGetInterest(AllInterest);
		mLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		mMap.put(aLCPolOtherSet, "DELETE&INSERT");// Add By QianLy on
													// 2006-10-17

		//mMap.put(aLCContSet, "DELETE&INSERT");
		mMap.put(aLCPolSet, "DELETE&INSERT");
		mMap.put(aLCDutySet, "DELETE&INSERT");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		mMap.put(aLCGetSet, "DELETE&INSERT");
		mMap.put(aLPPolOtherSet, "DELETE&INSERT");// Add By QianLy on 2006-10-17

		mMap.put(aLPContSet, "DELETE&INSERT");
		mMap.put(aLPPolSet, "DELETE&INSERT");
		mMap.put(aLPDutySet, "DELETE&INSERT");
		mMap.put(aLPPremSet, "DELETE&INSERT");
		mMap.put(aLPGetSet, "DELETE&INSERT");

		mMap.put(aLPEdorItemSet, "DELETE&INSERT");
		mMap.put(aLPEdorMainSet, "DELETE&INSERT");
		mMap.put(aLJSPayPersonSet, "DELETE&INSERT");
		mMap.put(aLJSGetEndorseSet, "DELETE&INSERT");

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpPolDB.setAppFlag("1");
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		Reflections tRef = new Reflections();
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tRef.transFields(tLPGrpPolSchema, tLCGrpPolSet.get(i));
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setModifyDate(CurrDate);
			tLPGrpPolSchema.setModifyTime(CurrTime);
			tLPGrpPolSet.add(tLPGrpPolSchema);
		}

		String sql = "delete from lpgrppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		mMap.put(sql, "DELETE");

		mMap.put(tLPGrpPolSet, "INSERT");

		// 更新团体险种表表更后的保费、保额、份数
		sql = " update lpgrppol g set "
				+ " prem = prem + (select sum(prem) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " amnt = amnt + (select sum(amnt) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " mult = mult + (select sum(p.mult) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "') "
				+ " where g.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' "
				+ " and g.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and g.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' ";
		mMap.put(sql, "UPDATE");
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------END-------

		return true;
	}

	private LJSGetEndorseSchema initLJSGetEndorse(LCPolSchema cLCPolSchema,
			LCPremSchema cLCPremSchema, String sFeeFinaType) {
		
		LJSGetEndorseSchema cLJSGetEndorseSchema = new LJSGetEndorseSchema();
		mRef.transFields(cLJSGetEndorseSchema, cLCPremSchema);
		cLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
		cLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
		cLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema
				.getEdorType());
		cLJSGetEndorseSchema.setFeeFinaType("BF");
		cLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		cLJSGetEndorseSchema.setGrpPolNo(cLCPolSchema.getGrpPolNo());
		cLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
		cLJSGetEndorseSchema.setOtherNoType("3");
		cLJSGetEndorseSchema.setInsuredNo(cLCPolSchema.getInsuredNo());
		cLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
		cLJSGetEndorseSchema.setGetMoney(cLCPremSchema.getPrem());
		cLJSGetEndorseSchema.setKindCode(cLCPolSchema.getKindCode());
		cLJSGetEndorseSchema.setRiskCode(cLCPolSchema.getRiskCode());
		cLJSGetEndorseSchema.setRiskVersion(cLCPolSchema.getRiskVersion());
		cLJSGetEndorseSchema.setPolType(cLCPolSchema.getPolTypeFlag());
		cLJSGetEndorseSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		cLJSGetEndorseSchema.setAgentCom(mLCGrpContSchema.getAgentCom());
		cLJSGetEndorseSchema.setAgentGroup(mLCGrpContSchema.getAgentGroup());
		cLJSGetEndorseSchema.setAgentType(mLCGrpContSchema.getAgentType());
		cLJSGetEndorseSchema.setGrpName(mLCGrpContSchema.getGrpName());
		cLJSGetEndorseSchema.setGetFlag("0");
		if (cLCPremSchema.getPayPlanType().trim().equals("")) {
			CError.buildErr(this, "查询新增被保人缴费类型失败!");
			return null;
		} else if (cLCPremSchema.getPayPlanType().trim().equals("0")) {
			cLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem);
		} else if (cLCPremSchema.getPayPlanType().trim().equals("1")) {
			cLJSGetEndorseSchema
					.setSubFeeOperationType(BqCode.Pay_InsurAddPremHealth);
		} else if (cLCPremSchema.getPayPlanType().trim().equals("2")) {
			cLJSGetEndorseSchema
					.setSubFeeOperationType(BqCode.Pay_InsurAddPremOccupation);
		} else {
			CError.buildErr(this, "新增被保人缴费类型不符合规范！");
			return null;
		}
		cLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		cLJSGetEndorseSchema.setMakeDate(CurrDate);
		cLJSGetEndorseSchema.setMakeTime(CurrTime);
		cLJSGetEndorseSchema.setModifyDate(CurrDate);
		cLJSGetEndorseSchema.setModifyTime(CurrTime);
		return cLJSGetEndorseSchema;
	}

	private LJSPayPersonSchema initLJSPayPerson(
			LJSGetEndorseSchema cLJSGetEndorseSchema) {
		LJSPayPersonSchema cLJSPayPersonSchema = new LJSPayPersonSchema();
		mRef.transFields(cLJSPayPersonSchema, cLJSGetEndorseSchema);
		cLJSPayPersonSchema.setPayAimClass("2");
		cLJSPayPersonSchema.setPayCount(1);
		cLJSPayPersonSchema.setSumActuPayMoney(cLJSGetEndorseSchema
				.getGetMoney());
		cLJSPayPersonSchema.setSumDuePayMoney(cLJSGetEndorseSchema
				.getGetMoney());
		cLJSPayPersonSchema.setPayDate(mLPGrpEdorItemSchema.getEdorValiDate());
		cLJSPayPersonSchema.setPayType(mLPGrpEdorItemSchema.getEdorType());
		cLJSPayPersonSchema.setLastPayToDate(mLPGrpEdorItemSchema
				.getEdorValiDate());
		return cLJSPayPersonSchema;
	}

	private LPEdorMainSchema initLPEdorMain(LPEdorItemSchema cLPEdorItemSchema) {
		LPEdorMainSchema cLPEdorMainSchema = new LPEdorMainSchema();
		mRef.transFields(cLPEdorMainSchema, mLPEdorAppSchema);
		mRef.transFields(cLPEdorMainSchema, cLPEdorItemSchema);
		return cLPEdorMainSchema;
	}

	private LPEdorItemSchema initLPEdorItem(LCPolSchema cLCPolSchema) {
		LPEdorItemSchema cLPEdorItemSchema = new LPEdorItemSchema();
		mRef.transFields(cLPEdorItemSchema, mLPGrpEdorItemSchema);
		cLPEdorItemSchema.setContNo(cLCPolSchema.getContNo());
		cLPEdorItemSchema.setInsuredNo(cLPEdorItemSchema.getInsuredNo());
		cLPEdorItemSchema.setPolNo(cLCPolSchema.getPolNo());
		cLPEdorItemSchema.setGetMoney(cLCPolSchema.getPrem());
		cLPEdorItemSchema.setEdorState("1");
		cLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
		cLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		cLPEdorItemSchema.setMakeDate(CurrDate);
		cLPEdorItemSchema.setMakeTime(CurrTime);
		cLPEdorItemSchema.setModifyDate(CurrDate);
		cLPEdorItemSchema.setModifyTime(CurrTime);
		return cLPEdorItemSchema;
	}

	private boolean CertifyDate() {
		// 根据保全生效日期确定新增个人单生效日期
		CValidate = mLPGrpEdorItemSchema.getEdorValiDate();
		return true;
	}



	private boolean checkData() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "查询保全声请信息失败!");
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团体保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemDB.getSchema();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询团单信息失败!");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();
		return true;
	}

	/**
	 * 删除上次保存过的数据
	 * 
	 * @return boolean
	 */
	private boolean delPData() {

		// 清除P表中上次保存过的数据
		String edorno = mLPGrpEdorItemSchema.getEdorNo();
		String edortype = mLPGrpEdorItemSchema.getEdorType();
		String sqlWhere = " grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' and edorno = '"
				+ edorno + "' and edortype = '" + edortype + "'";
		String sqlWhere_NoGrpContNo = " edorno = '" + edorno
				+ "' and edortype = '" + edortype + "'";

		mMap.put("delete from lpcont where" + sqlWhere, "DELETE");
		mMap.put("delete from lppol where" + sqlWhere, "DELETE");
		mMap.put("delete from LPDuty where" + sqlWhere_NoGrpContNo, "DELETE");
		mMap.put("delete from LPPrem where" + sqlWhere, "DELETE");
		mMap.put("delete from LPGet where" + sqlWhere, "DELETE");
		mMap.put("delete from LPEdorItem where" + sqlWhere, "DELETE");
		mMap.put("delete from LPEdorMain where edorno = '" + edorno + "' ",
				"DELETE");

		mMap.put(" delete from LJSGetEndorse " + " where EndorsementNo='"
				+ edorno + "' and FeeOperationType='" + edortype
				+ "' and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "'", "DELETE");
		mMap.put(" delete from LJSPayPerson " + " where Getnoticeno = '"
				+ edorno + "' and Paytype = '" + edortype
				+ "' and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "'", "DELETE");

		return true;
	}




	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public String cal_CValiDate(LCPolSchema tLCPolSchema, String EdorType) { // 计算101保单生效对应日
		String tCValiDate = tLCPolSchema.getCValiDate();
		String edor_date = tLCPolSchema.getCValiDate();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		String bqSql = "select * from LPGrpEdorItem where grpcontno='"
				+ tLCPolSchema.getGrpContNo()
				+ "' and edorstate!='0' and edortype='" + EdorType + "'";
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB
				.executeQuery(bqSql);
		if ( tLPGrpEdorItemSet.size() > 0) {
			edor_date = tLPGrpEdorItemSet.get(1).getEdorValiDate();
		}

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
		LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();
		if (tLMRiskAppSet.get(1).getRiskPeriod().equals("L")) {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			if(tLCGrpPolDB.query().size()>0)
			{
				LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolDB.query().get(1);
				String grp_date = tLCGrpPolSchema.getCValiDate();
				logger.debug("edor_date" + edor_date);
				logger.debug("grp_date" + grp_date);
				tCValiDate = edor_date.substring(0, 4) + grp_date.substring(4);
				logger.debug("tCValiDate_1" + tCValiDate);
				int interval = PubFun.calInterval(tCValiDate, edor_date, "D");
				if (interval < 0) {
					tCValiDate = PubFun.calDate(tCValiDate, -1, "Y", null);
				}
			}

			logger.debug("tCValiDate_2" + tCValiDate);
		}

		return tCValiDate;
	}
}
