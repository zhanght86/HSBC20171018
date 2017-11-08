package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 无名单补名单签单核保、财务处理</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCPrem_1DB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LJAGetOtherDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCPrem_1Schema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.tb.ProposalUI;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPrem_1Set;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpAddNameSignBL {
private static Logger logger = Logger.getLogger(GrpAddNameSignBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate = "";
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = null;
	/** 额外传递的参数 */
	private TransferData mTransferData = null;

	/** 传入的业务数据 */
	private String inNewPolNo; // 新增保单的保单号
	private String inSerialNo; // 统一的流水号
	private String inPrem; // 新增保单的保费
	private String inOldPolNo; // 无名单的保单号，存在新增保单的MasterPolNo字段中

	private LCPolSchema inLCPolSchema = new LCPolSchema();
	private LCDutySet inLCDutySet = new LCDutySet();
	private LCPremSet inLCPremSet = new LCPremSet();

	/** 传出的业务数据 */

	public GrpAddNameSignBL() {
	}

	/**
	 * 处理核保状态
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean dealUW(VData cInputData, String cOperate) {
		String proposalNo = "";
		try {
			// 获取传入的数据
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			proposalNo = (String) mTransferData.getValueByName("ProposalNo");

			mInputData.clear();

			// 获取该集体保单下的无名单个单数据
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(proposalNo);
			if (!tLCPolDB.getInfo())
				return false;
			// 暂时取第一条无名单个单数据
			LCPolSchema tLCPolSchema = tLCPolDB.getSchema();

			// 对新增保单进行复核
			tLCPolDB.setPolNo(proposalNo);
			if (!tLCPolDB.getInfo())
				return false;
			LCPolSchema tLCPolSchema2 = tLCPolDB.getSchema();
			tLCPolSchema2.setApproveFlag("9");
			tLCPolSchema2.setUWFlag("9");
			tLCPolSchema2.setApproveCode(tLCPolSchema.getApproveCode());
			tLCPolSchema2.setApproveDate(tLCPolSchema.getApproveDate());
			tLCPolSchema2.setUWCode(tLCPolSchema.getUWCode());
			tLCPolSchema2.setUWDate(tLCPolSchema.getUWDate());
			mInputData.add(tLCPolSchema2);

			// 获取该无名单个单的核保数据，并修改投保单号
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setPolNo(tLCPolSchema.getMasterPolNo());
			LCUWMasterSet tLCUWMasterSet = tLCUWMasterDB.query();

			for (int i = 0; i < tLCUWMasterSet.size(); i++) {
				LCUWMasterSchema tLCUWMasterSchema = tLCUWMasterSet.get(i + 1);
				tLCUWMasterSchema.setProposalNo(proposalNo);
				tLCUWMasterSchema.setPolNo(proposalNo);
			}
			mInputData.add(tLCUWMasterSet);

			// 获取该无名单个单的核保数据，并修改投保单号
			LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
			tLCUWErrorDB.setPolNo(tLCPolSchema.getMasterPolNo());
			LCUWErrorSet tLCUWErrorSet = tLCUWErrorDB.query();

			for (int i = 0; i < tLCUWErrorSet.size(); i++) {
				LCUWErrorSchema tLCUWErrorSchema = tLCUWErrorSet.get(i + 1);
				tLCUWErrorSchema.setPolNo(proposalNo);
			}
			mInputData.add(tLCUWErrorSet);

			// 获取该无名单个单的核保数据，并修改投保单号
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			tLCUWSubDB.setPolNo(tLCPolSchema.getMasterPolNo());
			LCUWSubSet tLCUWSubSet = tLCUWSubDB.query();

			for (int i = 0; i < tLCUWSubSet.size(); i++) {
				LCUWSubSchema tLCUWSubSchema = tLCUWSubSet.get(i + 1);
				tLCUWSubSchema.setProposalNo(proposalNo);
				tLCUWSubSchema.setPolNo(proposalNo);
			}
			mInputData.add(tLCUWSubSet);

			// 设置数据库操作方式
			mTransferData = new TransferData();
			mTransferData.setNameAndValue(tLCPolSchema2.toString(), "UPDATE");
			mTransferData.setNameAndValue(tLCUWMasterSet.toString(), "INSERT");
			mTransferData.setNameAndValue(tLCUWErrorSet.toString(), "INSERT");
			mTransferData.setNameAndValue(tLCUWSubSet.toString(), "INSERT");
			mInputData.add(mTransferData);

			GrpAddNameSignBLS tGrpAddNameSignBLS = new GrpAddNameSignBLS();
			if (tGrpAddNameSignBLS.submitData(mInputData, cOperate) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tGrpAddNameSignBLS.mErrors);
				mResult.clear();
				mResult.add(mErrors.getFirstError());
				return false;
			} else {
				mResult = tGrpAddNameSignBLS.getResult();
			}
			logger.debug("End GrpAddNameSign BLS Submit...");
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			try {
				dealDraw(proposalNo, mGlobalInput);
			} catch (Exception ex2) {
				ex2.printStackTrace();
				CError tError = new CError();
				tError.moduleName = "GrpAddNameSignBL";
				tError.functionName = "dealSign";
				tError.errorMessage = "无名单补名单删除投保单失败！请另外进行投保单删除操作！"
						+ ex2.getMessage();
				this.mErrors.addOneError(tError);
				return false;
			}

			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBL";
			tError.functionName = "dealUW";
			tError.errorMessage = "处理核保状态失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public boolean dealSign(LCPolSchema mLCPolSchema, GlobalInput tG) {
		try {
			// 无名单补名单核保
			GrpAddNameSignBL tGrpAddNameSignBL = new GrpAddNameSignBL();
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ProposalNo", mLCPolSchema
					.getProposalNo());
			tVData.add(tTransferData);
			tVData.add(tG);
			if (tGrpAddNameSignBL.dealUW(tVData, "INSERT") == false) {
				throw new Exception("无名单补名单核保失败! ");
			}
			logger.debug("End 无名单补名单核保");

			// 无名单补名单签单
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpProposalNo(mLCPolSchema.getGrpPolNo());
			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
			LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolSet.get(1);
			logger.debug("End get LCGrpPol Data");

			// 生成一个序列号
			String tLimit = PubFun.getNoLimit(tG.ComCode);
			String mSerialNo = PubFun1.CreateMaxNo("SerialNo", tLimit);

			// 获取实收总表数据
			LJAPayDB tLJAPayDB = new LJAPayDB();
			// tLJAPayDB.setIncomeNo(tLCGrpPolSchema.getGrpPolNo());
			tLJAPayDB.setIncomeNo(tLCGrpPolSchema.getGrpContNo());
			tLJAPayDB.setIncomeType("1");
			LJAPaySet tLJAPaySet = tLJAPayDB.query();
			LJAPaySchema tLJAPaySchema = tLJAPaySet.get(1);

			// 准备调用签单需要的数据
			VData vPolPub = new VData();
			vPolPub.add(tG);
			vPolPub.add(mSerialNo);
			vPolPub.add(tLJAPaySchema);

			tTransferData = new TransferData();
			tTransferData.setNameAndValue("EdorType", "GANAME");

			// 准备签单数据
			logger.debug("Start 准备签单数据...");
			ProposalSignBL tProposalSignBL = new ProposalSignBL();
			// 接口发生改变
			// tVData = tProposalSignBL.dealOnePol("",
			// tLCGrpPolSchema.getGrpPolNo(), mLCPolSchema.getProposalNo(),
			// vPolPub, tTransferData);
			if (tVData == null) {
				this.mErrors.copyAllErrors(tProposalSignBL.mErrors);
				throw new Exception("准备签单数据出错！");
			}

			// 调用签单程序
			logger.debug("Start 调用签单程序...");
			VData tVData2 = new VData();
			tVData2.add(tVData);
			ProposalSignBLS tProposalSignBLS = new ProposalSignBLS();
			if (!tProposalSignBLS.submitData(tVData2, "")) {
				this.mErrors.copyAllErrors(tProposalSignBLS.mErrors);
				throw new Exception("无名单补名单签单失败！");
			}
			logger.debug("End 调用签单程序...");
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();

			try {
				dealDraw(mLCPolSchema.getProposalNo(), tG);
			} catch (Exception ex2) {
				ex2.printStackTrace();
				CError tError = new CError();
				tError.moduleName = "GrpAddNameSignBL";
				tError.functionName = "dealSign";
				tError.errorMessage = "无名单补名单删除投保单失败！请另外进行投保单删除操作！"
						+ ex2.getMessage();
				this.mErrors.addOneError(tError);
				return false;
			}

			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBL";
			tError.functionName = "dealSign";
			tError.errorMessage = "无名单补名单签单失败！已经进行投保单删除！" + ex.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
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
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("---End dealData---");

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
			inLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
					"LCPolSchema", 0);
			inLCDutySet = (LCDutySet) mInputData.getObjectByObjectName(
					"LCDutySet", 0);
			inLCPremSet = (LCPremSet) mInputData.getObjectByObjectName(
					"LCPremSet", 0);

			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			inNewPolNo = (String) mTransferData.getValueByName("NewPolNo");
			// inNewProposalNo =
			// (String)mTransferData.getValueByName("NewProposalNo");
			inPrem = String.valueOf(mTransferData.getValueByName("NewPrem"));
			inSerialNo = (String) mTransferData.getValueByName("SerialNo");
			inOldPolNo = (String) mTransferData.getValueByName("OldPolNo");
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
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
		try {
			if (mOperate.equals("INSERT")) {
			}
			// 无名单补名单
			else if (mOperate.equals("QUERY")) {
				mResult.clear();

				// 获取无名单保单数据
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(inOldPolNo);
				if (!tLCPolDB.getInfo())
					throw new Exception("获取无名单保单数据失败！");

				// 校验保费
				if (Double.parseDouble(inPrem) > tLCPolDB.getPrem()) {
					throw new Exception("补名单保费大于原无名单保费，不能进行补名单！");
				}
				// 校验人数
				if (tLCPolDB.getInsuredPeoples() - 1 < 0) {
					throw new Exception("原无名单投保人数已经为0，不能进行补名单！");
				}

				double rate = 0.0;
				// 计算新保费和原无名单保费的比例
				if (tLCPolDB.getPrem() != 0) {
					rate = Double.parseDouble(inPrem) / tLCPolDB.getPrem();
				}

				// 修改无名单保单的数据
				LCPolSchema outLCPolSchema = tLCPolDB.getSchema();
				// 人数减1
				outLCPolSchema.setInsuredPeoples(outLCPolSchema
						.getInsuredPeoples() - 1);

				outLCPolSchema.setPrem(outLCPolSchema.getPrem()
						- Double.parseDouble(inPrem));
				outLCPolSchema.setStandPrem(outLCPolSchema.getStandPrem()
						- outLCPolSchema.getStandPrem() * rate);
				outLCPolSchema.setSumPrem(outLCPolSchema.getSumPrem()
						- outLCPolSchema.getSumPrem() * rate);
				outLCPolSchema.setLeavingMoney(outLCPolSchema.getLeavingMoney()
						- outLCPolSchema.getLeavingMoney() * rate);
				outLCPolSchema.setAmnt(outLCPolSchema.getAmnt()
						- outLCPolSchema.getAmnt() * rate);
				outLCPolSchema.setRiskAmnt(outLCPolSchema.getRiskAmnt()
						- outLCPolSchema.getRiskAmnt() * rate);

				// 修改补名单保单的数据
				inLCPolSchema.setPaytoDate(outLCPolSchema.getPaytoDate()); // 交至日期
				inLCPolSchema.setSumPrem(tLCPolDB.getSumPrem() * rate); // 累计保费
				inLCPolSchema.setSignDate(outLCPolSchema.getSignDate()); // 签单日期
				/*
				 * Lis5.3 upgrade get
				 * inLCPolSchema.setGetPolDate(outLCPolSchema.getGetPolDate());
				 * //送达日期
				 * inLCPolSchema.setCustomGetPolDate(outLCPolSchema.getCustomGetPolDate());
				 * //保单回执客户签收日期
				 */
				LCPolSet outLCPolSet = new LCPolSet();
				outLCPolSet.add(outLCPolSchema);
				outLCPolSet.add(inLCPolSchema);
				mResult.add(outLCPolSet);

				// 修改责任表
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(inOldPolNo);
				LCDutySet outLCDutySet = tLCDutyDB.query();
				LCDutySchema outLCDutySchema;
				for (int i = 0; i < outLCDutySet.size(); i++) {
					outLCDutySchema = outLCDutySet.get(i + 1);
					outLCDutySchema.setPrem(outLCDutySchema.getPrem()
							- outLCDutySchema.getPrem() * rate);
					outLCDutySchema.setStandPrem(outLCDutySchema.getStandPrem()
							- outLCDutySchema.getStandPrem() * rate);
					outLCDutySchema.setSumPrem(outLCDutySchema.getSumPrem()
							- outLCDutySchema.getSumPrem() * rate);
					outLCDutySchema.setAmnt(outLCDutySchema.getAmnt()
							- outLCDutySchema.getAmnt() * rate);
					outLCDutySchema.setRiskAmnt(outLCDutySchema.getRiskAmnt()
							- outLCDutySchema.getRiskAmnt() * rate);
				}
				// 修改补名单保单的数据
				for (int i = 0; i < inLCDutySet.size(); i++) {
					outLCDutySchema = inLCDutySet.get(i + 1);
					outLCDutySchema.setPaytoDate(outLCPolSchema.getPaytoDate()); // 交至日期
					outLCDutySchema.setSumPrem(tLCPolDB.getSumPrem() * rate); // 累计保费
				}
				outLCDutySet.add(inLCDutySet);
				mResult.add(outLCDutySet);

				// 修改保费项表
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(inOldPolNo);
				LCPremSet outLCPremSet = tLCPremDB.query();
				LCPremSchema outLCPremSchema;
				for (int i = 0; i < outLCPremSet.size(); i++) {
					outLCPremSchema = outLCPremSet.get(i + 1);
					outLCPremSchema.setPrem(outLCPremSchema.getPrem()
							- outLCPremSchema.getPrem() * rate);
					outLCPremSchema.setStandPrem(outLCPremSchema.getStandPrem()
							- outLCPremSchema.getStandPrem() * rate);
					outLCPremSchema.setSumPrem(outLCPremSchema.getSumPrem()
							- outLCPremSchema.getSumPrem() * rate);
				}
				// 修改补名单保单的数据
				for (int i = 0; i < inLCPremSet.size(); i++) {
					outLCPremSchema = inLCPremSet.get(i + 1);
					outLCPremSchema.setPaytoDate(outLCPolSchema.getPaytoDate()); // 交至日期
					outLCPremSchema.setSumPrem(tLCPolDB.getSumPrem() * rate); // 累计保费
				}
				outLCPremSet.add(inLCPremSet);
				mResult.add(outLCPremSet);

				// 修改保费项附表1
				LCPrem_1DB tLCPrem_1DB = new LCPrem_1DB();
				tLCPrem_1DB.setPolNo(inOldPolNo);
				LCPrem_1Set outLCPrem_1Set = tLCPrem_1DB.query();
				LCPrem_1Schema outLCPrem_1Schema;
				for (int i = 0; i < outLCPrem_1Set.size(); i++) {
					outLCPrem_1Schema = outLCPrem_1Set.get(i + 1);
					outLCPrem_1Schema.setPrem(outLCPrem_1Schema.getPrem()
							- outLCPrem_1Schema.getPrem() * rate);
				}
				mResult.add(outLCPrem_1Set);

				// 修改领取项表
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setPolNo(inOldPolNo);
				LCGetSet outLCGetSet = tLCGetDB.query();
				LCGetSchema outLCGetSchema;
				for (int i = 0; i < outLCGetSet.size(); i++) {
					outLCGetSchema = outLCGetSet.get(i + 1);
					outLCGetSchema.setStandMoney(outLCGetSchema.getStandMoney()
							- outLCGetSchema.getStandMoney() * rate);
					outLCGetSchema.setActuGet(outLCGetSchema.getActuGet()
							- outLCGetSchema.getActuGet() * rate);
					outLCGetSchema.setSumMoney(outLCGetSchema.getSumMoney()
							- outLCGetSchema.getSumMoney() * rate);
				}
				mResult.add(outLCGetSet);

				// 修改保险帐户表
				LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
				tLCInsureAccDB.setPolNo(inOldPolNo);
				LCInsureAccSet outLCInsureAccSet = tLCInsureAccDB.query();
				// 新增保单的数据
				LCInsureAccSet outAddLCInsureAccSet = new LCInsureAccSet();
				// outAddLCInsureAccSet.set(outLCInsureAccSet);
				// 无名单保单的数据修改
				LCInsureAccSchema outLCInsureAccSchema;
				for (int i = 0; i < outLCInsureAccSet.size(); i++) {
					outLCInsureAccSchema = outLCInsureAccSet.get(i + 1);
					// 克隆Set
					outAddLCInsureAccSet.add(outLCInsureAccSchema.getSchema());

					outLCInsureAccSchema.setSumPay(outLCInsureAccSchema
							.getSumPay()
							- outLCInsureAccSchema.getSumPay() * rate);
					outLCInsureAccSchema.setInsuAccBala(outLCInsureAccSchema
							.getInsuAccBala()
							- outLCInsureAccSchema.getInsuAccBala() * rate);
					outLCInsureAccSchema
							.setInsuAccGetMoney(outLCInsureAccSchema
									.getInsuAccGetMoney()
									- outLCInsureAccSchema.getInsuAccGetMoney()
									* rate);
					outLCInsureAccSchema.setSumPaym(outLCInsureAccSchema
							.getSumPaym()
							- outLCInsureAccSchema.getSumPaym() * rate);
					outLCInsureAccSchema.setFrozenMoney(outLCInsureAccSchema
							.getFrozenMoney()
							- outLCInsureAccSchema.getFrozenMoney() * rate);
				}
				// 新增保单数据的修改
				for (int i = 0; i < outAddLCInsureAccSet.size(); i++) {
					outLCInsureAccSchema = outAddLCInsureAccSet.get(i + 1);
					outLCInsureAccSchema.setPolNo(inNewPolNo);
					// outLCInsureAccSchema.setOtherNo(inNewPolNo);
					outLCInsureAccSchema.setInsuredNo(inLCPolSchema
							.getInsuredNo());
					// outLCInsureAccSchema.setAppntName(inLCPolSchema.getAppntName());

					outLCInsureAccSchema.setSumPay(outLCInsureAccSchema
							.getSumPay()
							* rate);
					outLCInsureAccSchema.setInsuAccBala(outLCInsureAccSchema
							.getInsuAccBala()
							* rate);
					outLCInsureAccSchema
							.setInsuAccGetMoney(outLCInsureAccSchema
									.getInsuAccGetMoney()
									* rate);
					outLCInsureAccSchema.setSumPaym(outLCInsureAccSchema
							.getSumPaym()
							* rate);
					outLCInsureAccSchema.setFrozenMoney(outLCInsureAccSchema
							.getFrozenMoney()
							* rate);
				}
				// 先删除原无名单的数据，再插入修改好的数据
				// LCInsureAccSet outDelLCInsureAccSet = new LCInsureAccSet();
				// outDelLCInsureAccSet.set(outLCInsureAccSet);

				outLCInsureAccSet.add(outAddLCInsureAccSet);
				mResult.add(outLCInsureAccSet);
				// mResult.add(outDelLCInsureAccSet);
				// mResult.add(outAddLCInsureAccSet);

				// 修改保险帐户表记价履历表
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				tLCInsureAccTraceDB.setPolNo(inOldPolNo);
				LCInsureAccTraceSet outLCInsureAccTraceSet = tLCInsureAccTraceDB
						.query();
				// 新增保单的数据
				LCInsureAccTraceSet outAddLCInsureAccTraceSet = new LCInsureAccTraceSet();
				// outAddLCInsureAccTraceSet.set(outLCInsureAccTraceSet);
				// 无名单保单的数据修改
				LCInsureAccTraceSchema outLCInsureAccTraceSchema;
				for (int i = 0; i < outLCInsureAccTraceSet.size(); i++) {
					outLCInsureAccTraceSchema = outLCInsureAccTraceSet
							.get(i + 1);
					// 克隆Set
					outAddLCInsureAccTraceSet.add(outLCInsureAccTraceSchema
							.getSchema());

					outLCInsureAccTraceSchema
							.setMoney(outLCInsureAccTraceSchema.getMoney()
									- outLCInsureAccTraceSchema.getMoney()
									* rate);
				}
				// 新增保单数据的修改
				for (int i = 0; i < outAddLCInsureAccTraceSet.size(); i++) {
					outLCInsureAccTraceSchema = outAddLCInsureAccTraceSet
							.get(i + 1);
					String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
					String mSerialNo = PubFun1.CreateMaxNo("SerialNo", tLimit);
					outLCInsureAccTraceSchema.setSerialNo(mSerialNo);
					outLCInsureAccTraceSchema.setPolNo(inNewPolNo);
					outLCInsureAccTraceSchema.setOtherNo(inNewPolNo);
					// outLCInsureAccTraceSchema.setInsuredNo(inLCPolSchema.getInsuredNo());
					/*
					 * Lis5.3 upgrade set
					 * outLCInsureAccTraceSchema.setAppntName(inLCPolSchema.getAppntName());
					 */
					outLCInsureAccTraceSchema
							.setMoney(outLCInsureAccTraceSchema.getMoney()
									* rate);
				}
				// 先删除原无名单的数据，再插入修改好的数据
				// LCInsureAccTraceSet outDelLCInsureAccTraceSet = new
				// LCInsureAccTraceSet();
				// outDelLCInsureAccTraceSet.set(outLCInsureAccTraceSet);

				outLCInsureAccTraceSet.add(outAddLCInsureAccTraceSet);
				mResult.add(outLCInsureAccTraceSet);
				// mResult.add(outDelLCInsureAccTraceSet);
				// mResult.add(outAddLCInsureAccTraceSet);

				// 修改实收个人交费表
				LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
				tLJAPayPersonDB.setPolNo(inOldPolNo);
				LJAPayPersonSet outLJAPayPersonSet = tLJAPayPersonDB.query();
				// 新增保单的数据
				LJAPayPersonSet outAddLJAPayPersonSet = new LJAPayPersonSet();
				// outAddLJAPayPersonSet.set(outLJAPayPersonSet);
				// 无名单保单的数据修改
				LJAPayPersonSchema outLJAPayPersonSchema;
				for (int i = 0; i < outLJAPayPersonSet.size(); i++) {
					outLJAPayPersonSchema = outLJAPayPersonSet.get(i + 1);
					// 克隆Set
					outAddLJAPayPersonSet
							.add(outLJAPayPersonSchema.getSchema());

					outLJAPayPersonSchema
							.setSumDuePayMoney(outLJAPayPersonSchema
									.getSumDuePayMoney()
									- outLJAPayPersonSchema.getSumDuePayMoney()
									* rate);
					outLJAPayPersonSchema
							.setSumActuPayMoney(outLJAPayPersonSchema
									.getSumActuPayMoney()
									- outLJAPayPersonSchema
											.getSumActuPayMoney() * rate);
				}
				// 新增保单数据的修改
				for (int i = 0; i < outAddLJAPayPersonSet.size(); i++) {
					outLJAPayPersonSchema = outAddLJAPayPersonSet.get(i + 1);
					outLJAPayPersonSchema.setPolNo(inNewPolNo);

					outLJAPayPersonSchema
							.setSumDuePayMoney(outLJAPayPersonSchema
									.getSumDuePayMoney()
									* rate);
					outLJAPayPersonSchema
							.setSumActuPayMoney(outLJAPayPersonSchema
									.getSumActuPayMoney()
									* rate);
				}
				// 先删除原无名单的数据，再插入修改好的数据
				// LJAPayPersonSet outDelLJAPayPersonSet = new
				// LJAPayPersonSet();
				// outDelLJAPayPersonSet.set(outLJAPayPersonSet);

				outLJAPayPersonSet.add(outAddLJAPayPersonSet);
				mResult.add(outLJAPayPersonSet);
				// mResult.add(outDelLJAPayPersonSet);
				// mResult.add(outAddLJAPayPersonSet);

				// 修改给付表(生存领取_实付)
				LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
				tLJAGetDrawDB.setPolNo(inOldPolNo);
				LJAGetDrawSet outLJAGetDrawSet = tLJAGetDrawDB.query();
				// 新增保单的数据
				LJAGetDrawSet outAddLJAGetDrawSet = new LJAGetDrawSet();
				// outAddLJAGetDrawSet.set(outLJAGetDrawSet);
				// 无名单保单的数据修改
				LJAGetDrawSchema outLJAGetDrawSchema;
				for (int i = 0; i < outLJAGetDrawSet.size(); i++) {
					outLJAGetDrawSchema = outLJAGetDrawSet.get(i + 1);
					// 克隆Set
					outAddLJAGetDrawSet.add(outLJAGetDrawSchema.getSchema());

					outLJAGetDrawSchema.setGetMoney(outLJAGetDrawSchema
							.getGetMoney()
							- outLJAGetDrawSchema.getGetMoney() * rate);
				}
				// 新增保单数据的修改
				for (int i = 0; i < outAddLJAGetDrawSet.size(); i++) {
					outLJAGetDrawSchema = outAddLJAGetDrawSet.get(i + 1);
					outLJAGetDrawSchema.setPolNo(inNewPolNo);
					outLJAGetDrawSchema.setInsuredNo(inLCPolSchema
							.getInsuredNo());

					outLJAGetDrawSchema.setGetMoney(outLJAGetDrawSchema
							.getGetMoney()
							* rate);
				}
				// 先删除原无名单的数据，再插入修改好的数据
				// LJAGetDrawSet outDelLJAGetDrawSet = new LJAGetDrawSet();
				// outDelLJAGetDrawSet.set(outLJAGetDrawSet);

				outLJAGetDrawSet.add(outAddLJAGetDrawSet);
				mResult.add(outLJAGetDrawSet);
				// mResult.add(outDelLJAGetDrawSet);
				// mResult.add(outAddLJAGetDrawSet);

				// 修改其他退费实付表
				LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
				tLJAGetOtherDB.setOtherNo(inOldPolNo);
				tLJAGetOtherDB.setOperState("0");
				LJAGetOtherSet outLJAGetOtherSet = tLJAGetOtherDB.query();
				// 新增保单的数据
				LJAGetOtherSet outAddLJAGetOtherSet = new LJAGetOtherSet();
				// outAddLJAGetOtherSet.set(outLJAGetOtherSet);
				// 无名单保单的数据修改
				LJAGetOtherSchema outLJAGetOtherSchema;
				for (int i = 0; i < outLJAGetOtherSet.size(); i++) {
					outLJAGetOtherSchema = outLJAGetOtherSet.get(i + 1);
					// 克隆Set
					outAddLJAGetOtherSet.add(outLJAGetOtherSchema.getSchema());

					outLJAGetOtherSchema.setGetMoney(outLJAGetOtherSchema
							.getGetMoney()
							- outLJAGetOtherSchema.getGetMoney() * rate);
				}
				// 新增保单数据的修改
				for (int i = 0; i < outAddLJAGetOtherSet.size(); i++) {
					outLJAGetOtherSchema = outAddLJAGetOtherSet.get(i + 1);
					outLJAGetOtherSchema.setOtherNo(inNewPolNo);

					outLJAGetOtherSchema.setGetMoney(outLJAGetOtherSchema
							.getGetMoney()
							* rate);
				}
				// 先删除原无名单的数据，再插入修改好的数据
				// LJAGetOtherSet outDelLJAGetOtherSet = new LJAGetOtherSet();
				// outDelLJAGetOtherSet.set(outLJAGetOtherSet);

				outLJAGetOtherSet.add(outAddLJAGetOtherSet);
				mResult.add(outLJAGetOtherSet);
				// mResult.add(outDelLJAGetOtherSet);
				// mResult.add(outAddLJAGetOtherSet);

				// 修改红利给付实付表
				LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
				tLJABonusGetDB.setOtherNo(inOldPolNo);
				LJABonusGetSet outLJABonusGetSet = tLJABonusGetDB.query();
				// 新增保单的数据
				LJABonusGetSet outAddLJABonusGetSet = new LJABonusGetSet();
				// outAddLJABonusGetSet.set(outLJABonusGetSet);
				// 无名单保单的数据修改
				LJABonusGetSchema outLJABonusGetSchema;
				for (int i = 0; i < outLJABonusGetSet.size(); i++) {
					outLJABonusGetSchema = outLJABonusGetSet.get(i + 1);
					// 克隆Set
					outAddLJABonusGetSet.add(outLJABonusGetSchema.getSchema());

					outLJABonusGetSchema.setGetMoney(outLJABonusGetSchema
							.getGetMoney()
							- outLJABonusGetSchema.getGetMoney() * rate);
				}
				// 新增保单数据的修改
				for (int i = 0; i < outAddLJABonusGetSet.size(); i++) {
					outLJABonusGetSchema = outAddLJABonusGetSet.get(i + 1);
					outLJABonusGetSchema.setOtherNo(inNewPolNo);

					outLJABonusGetSchema.setGetMoney(outLJABonusGetSchema
							.getGetMoney()
							* rate);
				}
				// 先删除原无名单的数据，再插入修改好的数据
				// LJABonusGetSet outDelLJABonusGetSet = new LJABonusGetSet();
				// outDelLJABonusGetSet.set(outLJABonusGetSet);

				outLJABonusGetSet.add(outAddLJABonusGetSet);
				mResult.add(outLJABonusGetSet);
				// mResult.add(outDelLJABonusGetSet);
				// mResult.add(outAddLJABonusGetSet);
			}
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			// mInputData.clear();
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 因为签单失败而进行投保单删除操作
	 * 
	 * @param proposalNo
	 * @param tG
	 * @return
	 */
	public boolean dealDraw(String proposalNo, GlobalInput tG) {
		try {
			logger.debug("Start Delete Grp Add Name...");
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setProposalNo(proposalNo);

			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.addElement(tLCPolSchema);
			tVData.addElement(tG);

			// 数据传输
			ProposalUI tProposalUI = new ProposalUI();
			if (!tProposalUI.submitData(tVData, "DELETE||PROPOSAL")) {
				throw new Exception(
						tProposalUI.mErrors.getError(0).errorMessage);
			}

			logger.debug("Start 撤销补名单的核保轨迹...");
			// 撤销补名单的核保轨迹
			mInputData.clear();
			// 获取该无名单个单的核保数据，并修改投保单号
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setPolNo(proposalNo);
			LCUWMasterSet tLCUWMasterSet = tLCUWMasterDB.query();
			mInputData.add(tLCUWMasterSet);

			// 获取该无名单个单的核保数据，并修改投保单号
			LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
			tLCUWErrorDB.setPolNo(proposalNo);
			LCUWErrorSet tLCUWErrorSet = tLCUWErrorDB.query();
			mInputData.add(tLCUWErrorSet);

			// 获取该无名单个单的核保数据，并修改投保单号
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			tLCUWSubDB.setPolNo(proposalNo);
			LCUWSubSet tLCUWSubSet = tLCUWSubDB.query();
			mInputData.add(tLCUWSubSet);

			// 设置数据库操作方式
			mTransferData = new TransferData();
			mTransferData.setNameAndValue(tLCUWMasterSet.toString(), "DELETE");
			mTransferData.setNameAndValue(tLCUWErrorSet.toString(), "DELETE");
			mTransferData.setNameAndValue(tLCUWSubSet.toString(), "DELETE");
			mInputData.add(mTransferData);

			GrpAddNameSignBLS tGrpAddNameSignBLS = new GrpAddNameSignBLS();
			if (tGrpAddNameSignBLS.submitData(mInputData, "") == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tGrpAddNameSignBLS.mErrors);
				mResult.clear();
				mResult.add(mErrors.getFirstError());
				return false;
			} else {
				mResult = tGrpAddNameSignBLS.getResult();
			}
			logger.debug("End Delete Grp Add Name...");
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBL";
			tError.functionName = "dealDraw";
			tError.errorMessage = "因为签单失败而进行投保单删除操作出错! " + ex.getMessage();
			this.mErrors.addOneError(tError);
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

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		GrpAddNameSignBL GrpAddNameSignBL1 = new GrpAddNameSignBL();

		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ComCode = "86110000";
		mGlobalInput.ManageCom = "86110000";
		mGlobalInput.Operator = "001";

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo("86110020030110006333");
		tLCPolDB.getInfo();

		GrpAddNameSignBL1.dealSign(tLCPolDB.getSchema(), mGlobalInput);
	}
}
