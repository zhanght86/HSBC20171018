package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGeneralDB;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCCGrpSpecDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.CommonCheck;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCDiseaseImpartSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCHistoryImpartSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiseaseImpartSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpServInfoSet;
import com.sinosoft.lis.vschema.LCHistoryImpartSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCCGrpSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GroupContBL {
private static Logger logger = Logger.getLogger(GroupContBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 传输到后台处理的map */
	private MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String mCustomerNO = ""; // 用于将CustomerNo传送到告知数据库中

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	Reflections ref = new Reflections();
	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpAppntSchema mLCGrpAppntSchema = new LCGrpAppntSchema();
	private LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();
	private LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCCustomerImpartParamsSet mLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
	private LCHistoryImpartSet mLCHistoryImpartSet = new LCHistoryImpartSet();
	private LCDiseaseImpartSet mLCDiseaseImpartSet = new LCDiseaseImpartSet();
	private LCDiseaseImpartSchema mLCDiseaseImpartSchema = new LCDiseaseImpartSchema();
	private LCHistoryImpartSchema mLCHistoryImpartSchema = new LCHistoryImpartSchema();
	private LCCGrpSpecSchema mLCCGrpSpecSchema = new LCCGrpSpecSchema();
	private LCGrpServInfoSet mLCGrpServInfoSet = new LCGrpServInfoSet();
	private LACommisionDetailSet mLACommisionDetailSet;

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public GroupContBL() {
	}

	/**
	 * 处理实际的业务逻辑。
	 * 
	 * @param cInputData
	 *            VData 从前台接收的表单数据
	 * @param cOperate
	 *            String 操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将数据取到本类变量中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 将VData数据还原成业务需要的类
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData successful---");
		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData successful---");
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealdata successful---");

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");
		PubSubmit tPubSubmit = new PubSubmit();
		// tPubSubmit.submitData(mInputData, cOperate);
		if (tPubSubmit.submitData(mInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));
			return false;
		}
		// 团体保单实例
		mLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));
		// 团单投保人实例
		mLCGrpAppntSchema.setSchema((LCGrpAppntSchema) mInputData
				.getObjectByObjectName("LCGrpAppntSchema", 0));
		// 团体客户地址实例
		mLCGrpAddressSchema.setSchema((LCGrpAddressSchema) mInputData
				.getObjectByObjectName("LCGrpAddressSchema", 0));
		// 团体客户实例
		mLDGrpSchema.setSchema((LDGrpSchema) mInputData.getObjectByObjectName(
				"LDGrpSchema", 0));
		// 团体告知
		mLCCustomerImpartSet.set((LCCustomerImpartSet) mInputData
				.getObjectByObjectName("LCCustomerImpartSet", 0));
		// 既往情况告知
		mLCHistoryImpartSet.set((LCHistoryImpartSet) mInputData
				.getObjectByObjectName("LCHistoryImpartSet", 0));
		// 严重疾病告知
		mLCDiseaseImpartSet.set((LCDiseaseImpartSet) mInputData
				.getObjectByObjectName("LCDiseaseImpartSet", 0));
		// 客户服务信息
		mLCGrpServInfoSet.set((LCGrpServInfoSet) mInputData
				.getObjectByObjectName("LCGrpServInfoSet", 0));
		// 团险特约录入
		mLCCGrpSpecSchema.setSchema((LCCGrpSpecSchema) mInputData
				.getObjectByObjectName("LCCGrpSpecSchema", 0));

		// 多业务员佣金比例
		this.mLACommisionDetailSet = (LACommisionDetailSet) mInputData
				.getObjectByObjectName("LACommisionDetailSet", 0);
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);

		return true;
	}

	/**
	 * 处理保存逻辑，保存处理过的数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		// 校验代理人编码并置上代理人组别
		if (mLCGrpContSchema.getAgentCode() == null
				|| mLCGrpContSchema.getAgentCode().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "dealData";
			tError.errorMessage = "请您确认有：代理人编码!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mLCGrpContSchema.getAgentCode());
			if (tLAAgentDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "请您确认：代理人编码没有输入错误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLAAgentDB.getManageCom() == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "代理人编码对应数据库中的管理机构为空！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!tLAAgentDB.getManageCom().equals(
					mLCGrpContSchema.getManageCom())) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "您录入的管理机构和数据库中代理人编码对应的管理机构不符合！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCGrpContSchema.setAgentGroup(tLAAgentDB.getAgentGroup());

		}

		boolean customerFlag = false;
		boolean addressFlag = false;
		String tLimit;
		// 如果缺少投保单号生成一个
		if (mLCGrpContSchema.getProposalGrpContNo() == null
				|| mLCGrpContSchema.getProposalGrpContNo().equals("")) {
			tLimit = PubFun.getNoLimit(mLCGrpContSchema.getManageCom());
			// 生成ProposalGrpContNo
			String tProposalGrpContNo = PubFun1
					.CreateMaxNo("GRPCONTNO", tLimit);
			if (!tProposalGrpContNo.equals("")) {
				mLCGrpContSchema.setProposalGrpContNo(tProposalGrpContNo);
				mLCGrpContSchema.setGrpContNo(tProposalGrpContNo);
				mLCGrpAppntSchema.setGrpContNo(tProposalGrpContNo);
			} else {
				mErrors.addOneError(new CError("集体投保单号生成失败"));
				return false;
			}
		} else {
			mLCGrpContSchema.setGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
			mLCGrpAppntSchema.setGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
		}
		// 如果缺少客户号码，生成一个
		if (mLCGrpAppntSchema.getCustomerNo() == null
				|| mLCGrpAppntSchema.getCustomerNo().trim().equals("")) {
			customerFlag = true;
			tLimit = "SN";
			String CustomerNO = PubFun1.CreateMaxNo("CUSTOMERNO", tLimit);
			if (!CustomerNO.equals("")) {
				mCustomerNO = CustomerNO;
				mLCGrpContSchema.setAppntNo(CustomerNO);
				mLCGrpAppntSchema.setCustomerNo(CustomerNO);
				mLDGrpSchema.setCustomerNo(CustomerNO);
				mLCGrpAddressSchema.setCustomerNo(CustomerNO);
			} else {
				mErrors.addOneError(new CError("客户号码生成失败"));
				return false;
			}
			//
		}
		// 如果缺少地址号码，生成一个
		if (mLCGrpContSchema.getAddressNo() == null
				|| mLCGrpContSchema.getAddressNo().trim().equals("")) {
			try {
				addressFlag = true;
				SSRS tSSRS = new SSRS();
				String sql = "Select Case When max(AddressNo) Is Null Then '0' Else trim(cast(max(to_number(addressno)) as char(20))) End from LCGrpAddress where CustomerNo='"
						+ mLCGrpAppntSchema.getCustomerNo() + "'";
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sql);
				Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
				int ttNo = firstinteger.intValue() + 1;
				Integer integer = new Integer(ttNo);
				String AddressNo = integer.toString();
				logger.debug("得到的地址码是：" + AddressNo);
				if (!AddressNo.equals("")) {
					mLCGrpContSchema.setAddressNo(AddressNo);
					mLCGrpAppntSchema.setAddressNo(AddressNo);
					mLCGrpAddressSchema.setAddressNo(AddressNo);
				} else {
					mErrors.addOneError(new CError("客户地址号码生成失败"));
					return false;
				}
			} catch (Exception e) {
				CError tError = new CError();
				tError.moduleName = "groupContBL";
				tError.functionName = "createAddressNo";
				tError.errorMessage = "地址码超长,生成号码失败,请先删除原来的超长地址码!";
				this.mErrors.addOneError(tError);
			}
		}
		if (mLCGrpContSchema.getAppFlag() == null
				|| mLCGrpContSchema.getAppFlag().equals("")) {
			mLCGrpContSchema.setAppFlag("0");
			mLCGrpContSchema.setState("0");
		}
		mLCGrpContSchema.setApproveFlag("0");
		mLCGrpContSchema.setUWFlag("0");
		mLCGrpContSchema.setSpecFlag("0");
		// 记录入机日期
		mLCGrpContSchema.setMakeDate(theCurrentDate);
		mLCGrpContSchema.setContType("2");
		mLCGrpAppntSchema.setMakeDate(theCurrentDate);
		// 记录入机时间
		mLCGrpContSchema.setMakeTime(theCurrentTime);
		mLCGrpAppntSchema.setMakeTime(theCurrentTime);
		if (customerFlag) {
			// 记录入机日期

			mLDGrpSchema.setMakeDate(theCurrentDate);
			mLDGrpSchema.setMakeTime(theCurrentTime);
			map.put(mLDGrpSchema, "INSERT");
		}
		if (addressFlag) {
			// 记录入机时间
			mLCGrpAddressSchema.setMakeTime(theCurrentTime);
			mLCGrpAddressSchema.setMakeDate(theCurrentDate);
			map.put(mLCGrpAddressSchema, "INSERT");
		}
		map.put(mLCGrpContSchema, "INSERT");
		map.put(mLCGrpAppntSchema, "INSERT");
//		团险特约保存 add  by liuqh 2009-02-27
		LCCGrpSpecSchema tLCCGrpSpecSchema=new LCCGrpSpecSchema();
		if(mLCCGrpSpecSchema.getSpecContent()!=null&&!"".equals(mLCCGrpSpecSchema.getSpecContent())){
			tLCCGrpSpecSchema = mLCCGrpSpecSchema;
			String serial = PubFun1.CreateMaxNo("SubNo", 11);
			tLCCGrpSpecSchema.setMakeDate(theCurrentDate);
			tLCCGrpSpecSchema.setMakeTime(theCurrentTime);
			tLCCGrpSpecSchema.setModifyDate(theCurrentDate);
			tLCCGrpSpecSchema.setModifyTime(theCurrentTime);
			tLCCGrpSpecSchema.setOperator(mGlobalInput.Operator);
			tLCCGrpSpecSchema.setSerialNo(serial);
			tLCCGrpSpecSchema.setPrtSeq(serial);
			map.put(tLCCGrpSpecSchema, "INSERT");
		}
		//特约保存

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			logger.debug("asdfjasdfja;dfljka");
			for (int i = 1; i <= mLCCustomerImpartSet.size(); i++) {
				if (mCustomerNO != "") {
					logger.debug("45646456456;dfljka");
					mLCCustomerImpartSet.get(i).setCustomerNo(mCustomerNO);
					mLCCustomerImpartSet.get(i).setContNo(SysConst.ZERONO);
					mLCCustomerImpartSet.get(i).setGrpContNo(
							mLCGrpContSchema.getGrpContNo());
					mLCCustomerImpartSet.get(i).setPrtNo(
							mLCGrpContSchema.getPrtNo());
					mLCCustomerImpartSet.get(i).setProposalContNo(
							SysConst.ZERONO);
					mLCCustomerImpartSet.get(i).setOperator(
							mGlobalInput.Operator);
					mLCCustomerImpartSet.get(i).setMakeDate(
							PubFun.getCurrentDate());
					mLCCustomerImpartSet.get(i).setMakeTime(
							PubFun.getCurrentTime());
					mLCCustomerImpartSet.get(i).setModifyDate(
							PubFun.getCurrentDate());
					mLCCustomerImpartSet.get(i).setModifyTime(
							PubFun.getCurrentTime());
				}
				logger.debug("asdfjasdfja;7896979789789");
			}
			CustomerImpartBL mCustomerImpartBL = new CustomerImpartBL();
			VData tempVData = new VData();
			tempVData.add(mLCCustomerImpartSet);
			tempVData.add(mGlobalInput);
			mCustomerImpartBL.submitData(tempVData, "IMPART||DEAL");
			if (mCustomerImpartBL.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "GroupContBL";
				tError.functionName = "insert";
				tError.errorMessage = mCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			tempVData.clear();
			tempVData = mCustomerImpartBL.getResult();
			if (null != (LCCustomerImpartSet) tempVData.getObjectByObjectName(
					"LCCustomerImpartSet", 0)) {
				mLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				logger.debug("告知条数" + mLCCustomerImpartSet.size());
			} else {
				logger.debug("告知条数为空");
			}
			if (null != (LCCustomerImpartParamsSet) tempVData
					.getObjectByObjectName("LCCustomerImpartParamsSet", 0)) {
				mLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			}

			if (mLCCustomerImpartSet != null) {
				map.put(mLCCustomerImpartSet, "INSERT");

			}
			if (mLCCustomerImpartParamsSet != null) {
				map.put(mLCCustomerImpartParamsSet, "INSERT");
			}
		}

		// logger.debug(mLCHistoryImpartSet.get(1).getInsuYear());
		if (mLCHistoryImpartSet != null && mLCHistoryImpartSet.size() > 0) {
			for (int i = 1; i <= mLCHistoryImpartSet.size(); i++) {
				mLCHistoryImpartSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCHistoryImpartSet.get(i).setSerialNo(
						PubFun1.CreateMaxNo("HisImpSerlNo", 20));
				mLCHistoryImpartSet.get(i)
						.setPrtNo(mLCGrpContSchema.getPrtNo());
				mLCHistoryImpartSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLCHistoryImpartSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLCHistoryImpartSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCHistoryImpartSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
				// logger.debug(mLCHistoryImpartSet.get(i).getPeoples());
			}

			if (mLCHistoryImpartSet != null) {
				map.put(mLCHistoryImpartSet, "INSERT");
			}
		}

		if (mLCDiseaseImpartSet != null && mLCDiseaseImpartSet.size() > 0) {
			for (int i = 1; i <= mLCDiseaseImpartSet.size(); i++) {
				mLCDiseaseImpartSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCDiseaseImpartSet.get(i).setSerialNo(
						PubFun1.CreateMaxNo("DisImpSerlNo", 20));
				mLCDiseaseImpartSet.get(i)
						.setPrtNo(mLCGrpContSchema.getPrtNo());
				mLCDiseaseImpartSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLCDiseaseImpartSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLCDiseaseImpartSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCDiseaseImpartSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
				// logger.debug(mLCHistoryImpartSet.get(i).getPeoples());
			}

			if (mLCDiseaseImpartSet != null) {
				map.put(mLCDiseaseImpartSet, "INSERT");
			}
		}
		// 客户服务信息
		if (mLCGrpServInfoSet != null && mLCGrpServInfoSet.size() > 0) {
			logger.debug("--Star servInfo--");
			for (int i = 1; i <= mLCGrpServInfoSet.size(); i++) {
				mLCGrpServInfoSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCGrpServInfoSet.get(i).setProposalGrpContNo(
						mLCGrpContSchema.getProposalGrpContNo());
				mLCGrpServInfoSet.get(i).setPrtNo(mLCGrpContSchema.getPrtNo());
				mLCGrpServInfoSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLCGrpServInfoSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLCGrpServInfoSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLCGrpServInfoSet.get(i).setModifyTime(PubFun.getCurrentTime());
				// logger.debug(mLCHistoryImpartSet.get(i).getPeoples());
			}

			if (mLCGrpServInfoSet != null) {
				map.put(mLCGrpServInfoSet, "INSERT");
			}
		}
		// 多业务员情况
		for (int i = 0; i < this.mLACommisionDetailSet.size(); i++) {			
			this.mLACommisionDetailSet.get(i + 1).setGrpContNo(
					mLCGrpContSchema.getGrpContNo());
			this.mLACommisionDetailSet.get(i + 1).setMakeDate(
					this.theCurrentDate);
			this.mLACommisionDetailSet.get(i + 1).setMakeTime(
					this.theCurrentTime);
			this.mLACommisionDetailSet.get(i + 1).setModifyDate(
					this.theCurrentDate);
			this.mLACommisionDetailSet.get(i + 1).setModifyTime(
					this.theCurrentTime);
			//mLCGrpContSchema
			this.mLACommisionDetailSet.get(i + 1).setAgentGroup(
					mLCGrpContSchema.getAgentGroup());
			
			this.mLACommisionDetailSet.get(i + 1).setOperator(
					mGlobalInput.Operator);
		}

		logger.debug("##################################");
		logger.debug("##################################");
		logger.debug(this.mLACommisionDetailSet.get(1).getBusiRate());
		logger.debug("##################################");
		logger.debug("##################################");

		map.put(this.mLACommisionDetailSet, "INSERT");

		return true;
	}

	/**
	 * 处理更新逻辑，更新处理过的数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean updateData() {
		// 校验代理人编码并置上代理人组别
		if (mLCGrpContSchema.getAgentCode() == null
				|| mLCGrpContSchema.getAgentCode().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "dealData";
			tError.errorMessage = "请您确认有：代理人编码!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mLCGrpContSchema.getAgentCode());
			if (tLAAgentDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "请您确认：代理人编码没有输入错误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLAAgentDB.getManageCom() == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "代理人编码对应数据库中的管理机构为空！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!tLAAgentDB.getManageCom().equals(
					mLCGrpContSchema.getManageCom())) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "dealData";
				tError.errorMessage = "您录入的管理机构和数据库中代理人编码对应的管理机构不符合！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCGrpContSchema.setAgentGroup(tLAAgentDB.getAgentGroup());

		}

		LCGrpContDB oldLCGrpContDB = new LCGrpContDB();
		oldLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		if (!oldLCGrpContDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(oldLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GroupContBL";
			tError.functionName = "dealData";
			tError.errorMessage = "LCGrpCont表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		//如果修改团单合同级销售渠道则同时修改该团单下所有信息的销售渠道
		if(!oldLCGrpContDB.getSaleChnl().equals(mLCGrpContSchema.getSaleChnl())){
			String tUpdateLCGrpPol = "update lcgrppol set salechnl='"+mLCGrpContSchema.getSaleChnl()
						+ "' where grpcontno='"+mLCGrpContSchema.getGrpContNo()+"'";
			String tUpdateLCPol = "update lcpol set salechnl='"+mLCGrpContSchema.getSaleChnl()
						+ "' where grpcontno='"+mLCGrpContSchema.getGrpContNo()+"'";
			String tUpdateLCCont = "update lccont set salechnl='"+mLCGrpContSchema.getSaleChnl()
						+ "' where grpcontno='"+mLCGrpContSchema.getGrpContNo()+"'";
			map.put(tUpdateLCGrpPol, "UPDATE");
			map.put(tUpdateLCPol, "UPDATE");
			map.put(tUpdateLCCont, "UPDATE");
		}
		
		LCPolDB mLCPolDB = new LCPolDB();
		mLCPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		int lccontNum = mLCPolDB.getCount();
		if (mLCPolDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "updateData";
			tError.errorMessage = "查询个人合同失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (lccontNum > 0
				&& !StrTool.compareString(mLCGrpContSchema.getCValiDate(),
						oldLCGrpContDB.getCValiDate())) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "updateData";
			tError.errorMessage = "该总单下已经有个人险种，不能修改生效日期!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGrpAppntDB oldLCGrpAppntDB = new LCGrpAppntDB();
		oldLCGrpAppntDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		// oldLCGrpAppntDB.setCustomerNo(mLCGrpContSchema.getAppntNo());
		oldLCGrpAppntDB.setSchema(oldLCGrpAppntDB.query().get(1));
		if (oldLCGrpAppntDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(oldLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GroupContBL";
			tError.functionName = "dealData";
			tError.errorMessage = "LCGrpAppnt表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGrpAddressDB oldLCGrpAddressDB = new LCGrpAddressDB();
		oldLCGrpAddressDB.setCustomerNo(oldLCGrpAppntDB.getCustomerNo());
		oldLCGrpAddressDB.setAddressNo(oldLCGrpAppntDB.getAddressNo());
		if (!oldLCGrpAddressDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(oldLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GroupContBL";
			tError.functionName = "dealData";
			tError.errorMessage = "LCGrpAddress表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		boolean customerFlag = false;
		boolean addressFlag = false;
		String tLimit = PubFun.getNoLimit(mLCGrpContSchema.getManageCom());

		// 如果缺少客户号码，生成一个

		if (mLCGrpAppntSchema.getCustomerNo() == null
				|| mLCGrpAppntSchema.getCustomerNo().trim().equals("")) {
			customerFlag = true;
			tLimit = "SN";

			String CustomerNO = PubFun1.CreateMaxNo("GRPNO", tLimit);

			if (!CustomerNO.equals("")) {
				mLCGrpContSchema.setAppntNo(CustomerNO);
				mLCGrpAppntSchema.setCustomerNo(CustomerNO);
				mLDGrpSchema.setCustomerNo(CustomerNO);
				mLCGrpAddressSchema.setCustomerNo(CustomerNO);
			} else {
				mErrors.addOneError(new CError("客户号码生成失败"));
				return false;
			}
		}

		mLCGrpAppntSchema.setMakeDate(oldLCGrpAddressDB.getMakeDate());
		mLCGrpAppntSchema.setMakeTime(oldLCGrpAddressDB.getMakeTime());

		// 如果缺少地址号码，生成一个
		if (mLCGrpContSchema.getAddressNo() == null
				|| mLCGrpContSchema.getAddressNo().trim().equals("")) {
			try {
				addressFlag = true;
				SSRS tSSRS = new SSRS();
				String sql = "Select Case When max(AddressNo) Is Null Then '0' Else max(AddressNo) End from  LCGrpAddress where CustomerNo='"
						+ mLCGrpAppntSchema.getCustomerNo() + "'";
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sql);
				Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
				int ttNo = firstinteger.intValue() + 1;
				Integer integer = new Integer(ttNo);
				String AddressNo = integer.toString();
				logger.debug("得到的地址码是：" + AddressNo);
				if (AddressNo == null || (!AddressNo.equals(""))) {
					mLCGrpContSchema.setAddressNo(AddressNo);
					mLCGrpAppntSchema.setAddressNo(AddressNo);
					mLCGrpAddressSchema.setAddressNo(AddressNo);
				} else {
					mErrors.addOneError(new CError("客户地址号码生成失败"));
					return false;
				}

			} catch (Exception e) {
				CError tError = new CError();
				tError.moduleName = "groupContBL";
				tError.functionName = "createAddressNo";
				tError.errorMessage = "地址码超长,生成号码失败,请先删除原来的超长地址码!";
				this.mErrors.addOneError(tError);
			}
		}
//		团险特约保存 add  by liuqh 2009-02-27
		if(mLCCGrpSpecSchema.getSpecContent()!=null&&!"".equals(mLCCGrpSpecSchema.getSpecContent())){
			if(mLCCGrpSpecSchema.getProposalGrpContNo()!=null&&
					mLCCGrpSpecSchema.getProposalGrpContNo()!=""){
				LCCGrpSpecSchema tLCCGrpSpecSchema=new LCCGrpSpecSchema();
				LCCGrpSpecDB tLCCGrpSpecDB=new LCCGrpSpecDB();
				tLCCGrpSpecDB.setProposalGrpContNo(mLCCGrpSpecSchema.getProposalGrpContNo());
				tLCCGrpSpecDB.setSerialNo(mLCCGrpSpecSchema.getSerialNo());
				if(!tLCCGrpSpecDB.getInfo()){
					//CError.buildErr(this, "查询要修改的特约记录错误！");
					//查不到有可能是修改时新增特约 但不能确定合同下有误特约
					tLCCGrpSpecDB=new LCCGrpSpecDB();
					tLCCGrpSpecDB.setProposalGrpContNo(mLCCGrpSpecSchema.getProposalGrpContNo());
					LCCGrpSpecSet tLCCGrpSpecSet =new LCCGrpSpecSet();
					tLCCGrpSpecSet =tLCCGrpSpecDB.query();
					if(tLCCGrpSpecSet.size()<=0){
						//如果没有记录说明是第一次在修改是的新增特约
						tLCCGrpSpecSchema = mLCCGrpSpecSchema;
						String serial = PubFun1.CreateMaxNo("SubNo", 11);
						tLCCGrpSpecSchema.setMakeDate(theCurrentDate);
						tLCCGrpSpecSchema.setMakeTime(theCurrentTime);
						tLCCGrpSpecSchema.setModifyDate(theCurrentDate);
						tLCCGrpSpecSchema.setModifyTime(theCurrentTime);
						tLCCGrpSpecSchema.setOperator(mGlobalInput.Operator);
						tLCCGrpSpecSchema.setSerialNo(serial);
						tLCCGrpSpecSchema.setPrtSeq(serial);
						map.put(tLCCGrpSpecSchema, "INSERT");
					}
				}else{
				tLCCGrpSpecSchema = tLCCGrpSpecDB.getSchema();
				tLCCGrpSpecSchema.setSpecContent(mLCCGrpSpecSchema.getSpecContent());
				map.put(tLCCGrpSpecSchema, "DELETE&INSERT");
				}
			}
		}
		//特约保存

		mLCGrpAddressSchema.setMakeDate(oldLCGrpAddressDB.getMakeDate());
		mLCGrpAddressSchema.setMakeTime(oldLCGrpAddressDB.getMakeTime());

		mLCGrpContSchema.setAppFlag(oldLCGrpContDB.getAppFlag());
		mLCGrpContSchema.setState(oldLCGrpContDB.getAppFlag());
		// 复核标记
		mLCGrpContSchema.setApproveFlag(oldLCGrpContDB.getApproveFlag());
		mLCGrpContSchema.setApproveCode(oldLCGrpContDB.getApproveCode());

		mLCGrpContSchema.setContType("2");
		mLCGrpContSchema.setApproveDate(oldLCGrpContDB.getApproveDate());
		mLCGrpContSchema.setApproveTime(oldLCGrpContDB.getApproveTime());
		// 签单标记
		mLCGrpContSchema.setUWFlag(oldLCGrpContDB.getUWFlag());
		mLCGrpContSchema.setUWOperator(oldLCGrpContDB.getUWOperator());
		mLCGrpContSchema.setUWDate(oldLCGrpContDB.getApproveDate());
		mLCGrpContSchema.setUWTime(oldLCGrpContDB.getApproveTime());

		mLCGrpContSchema.setSpecFlag(oldLCGrpContDB.getSpecFlag());

		mLCGrpContSchema.setMakeDate(oldLCGrpContDB.getMakeDate());
		mLCGrpContSchema.setMakeTime(oldLCGrpContDB.getMakeTime());

		if (customerFlag) {
			// 记录入机日期

			mLDGrpSchema.setMakeDate(theCurrentDate);
			mLDGrpSchema.setMakeTime(theCurrentTime);
			map.put(mLDGrpSchema, "INSERT");
		} else {
			// 在团体客户层只更新“公司性质”和“总人数”字段
			// String aSQL = "update LDGrp set grpNature='" +
			// mLDGrpSchema.getGrpNature() + "'," +
			// "peoples='" + mLDGrpSchema.getPeoples() + "'," +
			// "asset='" + mLDGrpSchema.getAsset() + "'," +
			// "offWorkPeoples='" + mLDGrpSchema.getOffWorkPeoples() +
			// "'," +
			// "onWorkPeoples='" + mLDGrpSchema.getOnWorkPeoples() +
			// "'," +
			// "otherPeoples='" + mLDGrpSchema.getOtherPeoples() +
			// "'," +
			// "ModifyDate='" + theCurrentDate + "'," +
			// "ModifyTime='" + theCurrentTime + "' ";
			// map.put(aSQL, "UPDATE");
			mLDGrpSchema.setMakeDate(PubFun.getCurrentDate());
			mLDGrpSchema.setMakeTime(PubFun.getCurrentTime());
			map.put(mLDGrpSchema, "DELETE&INSERT");
		}

		// 记录入机时间
		mLCGrpAddressSchema.setMakeTime(theCurrentTime);
		mLCGrpAddressSchema.setMakeDate(theCurrentDate);
		map.put(mLCGrpAddressSchema, "DELETE&INSERT");

		// map.put(oldLCGrpAddressDB.getSchema(), "DELETE");

		map.put(mLCGrpContSchema, "UPDATE");
		map.put(oldLCGrpAppntDB.getSchema(), "DELETE");
		map.put(mLCGrpAppntSchema, "INSERT");
		/*
		 * 要更新所有的LCCont,LCInsured,LCPol,LCGeneral,LCInsuredRelated,LCCustomerImpart,LCCustomerImpartParams
		 * 的ManageCom,SaleChnl，AgentCom，AgentType，AgentCode，AgentGroup,AgentCode1
		 */
		String wherePart = "GrpContNo='" + mLCGrpContSchema.getGrpContNo()
				+ "'";
		if (!StrTool.compareString(mLCGrpContSchema.getCValiDate(),
				oldLCGrpContDB.getCValiDate())) {
			String updatecontsql = "update lccont set " + "CValiDate ='"
					+ mLCGrpContSchema.getCValiDate() + "'," + "ModifyDate ='"
					+ theCurrentDate + "'," + "ModifyTime ='" + theCurrentTime
					+ "' " + "where " + wherePart;
			map.put(updatecontsql, "UPDATE");
		}
		if (!mLCGrpContSchema.getPrtNo().equals(oldLCGrpContDB.getPrtNo())) {
			String updateSql = "";
			updateSql = updateSql + "PrtNo='" + mLCGrpContSchema.getPrtNo()
					+ "'," + "ModifyDate ='" + theCurrentDate + "',"
					+ "ModifyTime ='" + theCurrentTime + "' " + "where "
					+ wherePart;

			String updateLCCustomerImpartSql = "update LCCustomerImpart set "
					+ updateSql;
			String updateLCCustomerImpartParamsSql = "update LCCustomerImpartParams set "
					+ updateSql;

			map.put(updateLCCustomerImpartSql, "UPDATE");
			map.put(updateLCCustomerImpartParamsSql, "UPDATE");
		}
		if (!mLCGrpContSchema.getManageCom().equals(
				oldLCGrpContDB.getManageCom())
				|| !mLCGrpContSchema.getPrtNo().equals(
						oldLCGrpContDB.getPrtNo())) {
			String updateSql = "";
			updateSql = updateSql + "ManageCom='"
					+ mLCGrpContSchema.getManageCom() + "',";
			updateSql = updateSql + "PrtNo='" + mLCGrpContSchema.getPrtNo()
					+ "',";
			updateSql = updateSql + "ModifyDate ='" + theCurrentDate + "',"
					+ "ModifyTime ='" + theCurrentTime + "' " + "where "
					+ wherePart;

			String updateLCGeneralSql = "update LCGeneral set " + updateSql;
			map.put(updateLCGeneralSql, "UPDATE");
		}

		if (!mLCGrpContSchema.getManageCom().equals(
				oldLCGrpContDB.getManageCom())
				|| !mLCGrpContSchema.getPrtNo().equals(
						oldLCGrpContDB.getPrtNo())
				|| !mLCGrpContSchema.getAppntNo().equals(
						oldLCGrpContDB.getAppntNo())) {

			String updateLCInsuredSql1 = "update LCInsured set "
					+ "ManageCom='" + mLCGrpContSchema.getManageCom() + "',"
					+ "PrtNo='" + mLCGrpContSchema.getPrtNo() + "',"
					+ "AppntNo='" + mLCGrpContSchema.getAppntNo() + "',"
					+ "ModifyDate ='" + theCurrentDate + "'," + "ModifyTime ='"
					+ theCurrentTime + "' " + "where " + wherePart
					+ " and ManageCom<>ExecuteCom";

			String updateLCInsuredSql = "update LCInsured set " + "ManageCom='"
					+ mLCGrpContSchema.getManageCom() + "'," + "ExecuteCom='"
					+ mLCGrpContSchema.getManageCom() + "'," + "PrtNo='"
					+ mLCGrpContSchema.getPrtNo() + "'," + "AppntNo='"
					+ mLCGrpContSchema.getAppntNo() + "'," + "ModifyDate ='"
					+ theCurrentDate + "'," + "ModifyTime ='" + theCurrentTime
					+ "'" + "where " + wherePart + " and ManageCom=ExecuteCom";

			map.put(updateLCInsuredSql1, "UPDATE");
			map.put(updateLCInsuredSql, "UPDATE");

		}

		if ((!StrTool.compareString(mLCGrpContSchema.getManageCom(),
				oldLCGrpContDB.getManageCom()))
				|| (!StrTool.compareString(mLCGrpContSchema.getPrtNo(),
						oldLCGrpContDB.getPrtNo()))
				|| (!StrTool.compareString(mLCGrpContSchema.getPolApplyDate(),
						oldLCGrpContDB.getPolApplyDate()))
				|| (!StrTool.compareString(mLCGrpContSchema.getAppntNo(),
						oldLCGrpContDB.getAppntNo()))
				|| (!StrTool.compareString(mLCGrpContSchema.getGrpName(),
						oldLCGrpContDB.getGrpName()))
				|| (!StrTool.compareString(mLCGrpContSchema.getAgentCom(),
						oldLCGrpContDB.getAgentCom()))
				|| (!StrTool.compareString(mLCGrpContSchema.getAgentCode(),
						oldLCGrpContDB.getAgentCode()))
				|| (!StrTool.compareString(mLCGrpContSchema.getAgentGroup(),
						oldLCGrpContDB.getAgentGroup()))
				|| (!StrTool.compareString(mLCGrpContSchema.getAgentCode1(),
						oldLCGrpContDB.getAgentCode1()))
				|| (!StrTool.compareString(mLCGrpContSchema.getAgentType(),
						oldLCGrpContDB.getAgentType()))
				|| (!StrTool.compareString(mLCGrpContSchema.getSaleChnl(),
						oldLCGrpContDB.getSaleChnl()))
				|| (!StrTool.compareString(mLCGrpContSchema.getPayMode(),
						oldLCGrpContDB.getPayMode()))) {
			String updateLCContSql = "update LCCont set ";
			if (mLCGrpContSchema.getManageCom() != null) {
				updateLCContSql = updateLCContSql + "ManageCom='"
						+ mLCGrpContSchema.getManageCom() + "',";
			}
			if (mLCGrpContSchema.getManageCom() != null) {
				updateLCContSql = updateLCContSql + "ExecuteCom='"
						+ mLCGrpContSchema.getManageCom() + "',";
			}
			if (mLCGrpContSchema.getPrtNo() != null) {
				updateLCContSql = updateLCContSql + "PrtNo='"
						+ mLCGrpContSchema.getPrtNo() + "',";
			}
			if (mLCGrpContSchema.getPolApplyDate() != null) {
				updateLCContSql = updateLCContSql + "PolApplyDate='"
						+ mLCGrpContSchema.getPolApplyDate() + "',";
			}
			if (mLCGrpContSchema.getAppntNo() != null) {
				updateLCContSql = updateLCContSql + "AppntNo='"
						+ mLCGrpContSchema.getAppntNo() + "',";
			}
			if (mLCGrpContSchema.getGrpName() != null) {
				updateLCContSql = updateLCContSql + "AppntName='"
						+ mLCGrpContSchema.getGrpName() + "',";
			}
			if (mLCGrpContSchema.getAgentCom() != null) {
				updateLCContSql = updateLCContSql + "AgentCom='"
						+ mLCGrpContSchema.getAgentCom() + "',";
			}
			if (mLCGrpContSchema.getAgentCode() != null) {
				updateLCContSql = updateLCContSql + "AgentCode='"
						+ mLCGrpContSchema.getAgentCode() + "',";
			}
			if (mLCGrpContSchema.getAgentGroup() != null) {
				updateLCContSql = updateLCContSql + "AgentGroup='"
						+ mLCGrpContSchema.getAgentGroup() + "',";
			}

			if (mLCGrpContSchema.getSaleChnl() != null) {
				updateLCContSql = updateLCContSql + "SaleChnl='"
						+ mLCGrpContSchema.getSaleChnl() + "',";
			}
			if (mLCGrpContSchema.getPayMode() != null) {
				updateLCContSql = updateLCContSql + "PayMode='"
						+ mLCGrpContSchema.getPayMode() + "',";
			}
			// + "PayLocation='" + mLCGrpContSchema.getPayLocation() + "',"
			updateLCContSql = updateLCContSql + "ModifyDate ='"
					+ theCurrentDate + "',";
			updateLCContSql = updateLCContSql + "ModifyTime ='"
					+ theCurrentTime + "' ";
			updateLCContSql = updateLCContSql + "where " + wherePart
					+ " and ManageCom=ExecuteCom";

			String updateLCContSql1 = "update LCCont set ";
			if (mLCGrpContSchema.getManageCom() != null) {
				updateLCContSql1 = updateLCContSql1 + "ManageCom='"
						+ mLCGrpContSchema.getManageCom() + "',";
			}
			if (mLCGrpContSchema.getPrtNo() != null) {
				updateLCContSql1 = updateLCContSql1 + "PrtNo='"
						+ mLCGrpContSchema.getPrtNo() + "',";
			}
			if (mLCGrpContSchema.getPolApplyDate() != null) {
				updateLCContSql1 = updateLCContSql1 + "PolApplyDate='"
						+ mLCGrpContSchema.getPolApplyDate() + "',";
			}
			if (mLCGrpContSchema.getAppntNo() != null) {
				updateLCContSql1 = updateLCContSql1 + "AppntNo='"
						+ mLCGrpContSchema.getAppntNo() + "',";
			}
			if (mLCGrpContSchema.getGrpName() != null) {
				updateLCContSql1 = updateLCContSql1 + "AppntName='"
						+ mLCGrpContSchema.getGrpName() + "',";
			}
			if (mLCGrpContSchema.getAgentCom() != null) {
				updateLCContSql1 = updateLCContSql1 + "AgentCom='"
						+ mLCGrpContSchema.getAgentCom() + "',";
			}
			if (mLCGrpContSchema.getAgentCode() != null) {
				updateLCContSql1 = updateLCContSql1 + "AgentCode='"
						+ mLCGrpContSchema.getAgentCode() + "',";
			}
			if (mLCGrpContSchema.getAgentGroup() != null) {
				updateLCContSql1 = updateLCContSql1 + "AgentGroup='"
						+ mLCGrpContSchema.getAgentGroup() + "',";
			}

			if (mLCGrpContSchema.getSaleChnl() != null) {
				updateLCContSql1 = updateLCContSql1 + "SaleChnl='"
						+ mLCGrpContSchema.getSaleChnl() + "',";
			}
			if (mLCGrpContSchema.getPayMode() != null) {
				updateLCContSql1 = updateLCContSql1 + "PayMode='"
						+ mLCGrpContSchema.getPayMode() + "',";
			}

			updateLCContSql1 = updateLCContSql1 + "ModifyDate ='"
					+ theCurrentDate + "',";
			updateLCContSql1 = updateLCContSql1 + "ModifyTime ='"
					+ theCurrentTime + "' ";
			updateLCContSql1 = updateLCContSql1 + "where " + wherePart
					+ " and ManageCom<>ExecuteCom";

			String updateLCPolSql = "update LCPol set ";
			if (mLCGrpContSchema.getManageCom() != null) {
				updateLCPolSql = updateLCPolSql + "ManageCom='"
						+ mLCGrpContSchema.getManageCom() + "',";
			}
			if (mLCGrpContSchema.getPrtNo() != null) {
				updateLCPolSql = updateLCPolSql + "PrtNo='"
						+ mLCGrpContSchema.getPrtNo() + "',";
			}
			if (mLCGrpContSchema.getPolApplyDate() != null) {
				updateLCPolSql = updateLCPolSql + "PolApplyDate='"
						+ mLCGrpContSchema.getPolApplyDate() + "',";
			}
			if (mLCGrpContSchema.getAgentCom() != null) {
				updateLCPolSql = updateLCPolSql + "AgentCom='"
						+ mLCGrpContSchema.getAgentCom() + "',";
			}
			if (mLCGrpContSchema.getAgentCode() != null) {
				updateLCPolSql = updateLCPolSql + "AgentCode='"
						+ mLCGrpContSchema.getAgentCode() + "',";
			}
			if (mLCGrpContSchema.getAgentGroup() != null) {
				updateLCPolSql = updateLCPolSql + "AgentGroup='"
						+ mLCGrpContSchema.getAgentGroup() + "',";
			}
			if (mLCGrpContSchema.getSaleChnl() != null) {
				updateLCPolSql = updateLCPolSql + "SaleChnl='"
						+ mLCGrpContSchema.getSaleChnl() + "',";
			}
			if (mLCGrpContSchema.getPayMode() != null) {
				updateLCPolSql = updateLCPolSql + "PayMode='"
						+ mLCGrpContSchema.getPayMode() + "',";
			}

			updateLCPolSql = updateLCPolSql + "ModifyDate ='" + theCurrentDate
					+ "'," + "ModifyTime ='" + theCurrentTime + "' " + "where "
					+ wherePart;
			map.put(updateLCPolSql, "UPDATE");

			map.put(updateLCContSql, "UPDATE");
			map.put(updateLCContSql1, "UPDATE");
		}
		// 团体告知修改
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= mLCCustomerImpartSet.size(); i++) {
				if (mCustomerNO != "") {
					mLCCustomerImpartSet.get(i).setCustomerNo(mCustomerNO);
				}
				mLCCustomerImpartSet.get(i).setContNo(SysConst.ZERONO);
				mLCCustomerImpartSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCCustomerImpartSet.get(i).setPrtNo(
						mLCGrpContSchema.getPrtNo());
				mLCCustomerImpartSet.get(i).setProposalContNo(SysConst.ZERONO);
				mLCCustomerImpartSet.get(i).setOperator(mGlobalInput.Operator);
				mLCCustomerImpartSet.get(i)
						.setMakeDate(PubFun.getCurrentDate());
				mLCCustomerImpartSet.get(i)
						.setMakeTime(PubFun.getCurrentTime());
				mLCCustomerImpartSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCCustomerImpartSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
			}
			// 处理告知参数表
			CustomerImpartBL mCustomerImpartBL = new CustomerImpartBL();
			VData tempVData = new VData();
			tempVData.add(mLCCustomerImpartSet);
			tempVData.add(mGlobalInput);
			mCustomerImpartBL.submitData(tempVData, "IMPART||DEAL");
			if (mCustomerImpartBL.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "GroupContBL";
				tError.functionName = "insert";
				tError.errorMessage = mCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			tempVData.clear();
			tempVData = mCustomerImpartBL.getResult();
			if (null != (LCCustomerImpartSet) tempVData.getObjectByObjectName(
					"LCCustomerImpartSet", 0)) {
				mLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				logger.debug("告知条数" + mLCCustomerImpartSet.size());
			} else {
				logger.debug("告知条数为空");
			}
			if (null != (LCCustomerImpartParamsSet) tempVData
					.getObjectByObjectName("LCCustomerImpartParamsSet", 0)) {
				mLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			}

			if (mLCCustomerImpartParamsSet != null) {
				map.put("delete from LCCustomerImpartParams where GrpContNo='"
						+ mLCGrpContSchema.getGrpContNo()
						+ "' and CustomerNo='" + mLCGrpContSchema.getAppntNo()
						+ "'", "DELETE");
				map.put(mLCCustomerImpartParamsSet, "INSERT");
			}
			// 修改团体告知
			if (mLCCustomerImpartSet != null) {
				map.put("delete from LCCustomerImpart where GrpContNo='"
						+ mLCGrpContSchema.getGrpContNo()
						+ "' and CustomerNo='" + mLCGrpContSchema.getAppntNo()
						+ "'", "DELETE");
				map.put(mLCCustomerImpartSet, "INSERT");
			}
		}

		if (mLCHistoryImpartSet != null && mLCHistoryImpartSet.size() > 0) {
			for (int i = 1; i <= mLCHistoryImpartSet.size(); i++) {
				mLCHistoryImpartSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCHistoryImpartSet.get(i).setSerialNo(
						PubFun1.CreateMaxNo("HisImpSerlNo", 20));
				mLCHistoryImpartSet.get(i)
						.setPrtNo(mLCGrpContSchema.getPrtNo());
				mLCHistoryImpartSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLCHistoryImpartSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLCHistoryImpartSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCHistoryImpartSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
				// logger.debug(mLCHistoryImpartSet.get(i).getPeoples());
			}

			if (mLCHistoryImpartSet != null) {
				map.put(mLCHistoryImpartSet, "DELETE&INSERT");
			}
		}

		if (mLCDiseaseImpartSet != null && mLCDiseaseImpartSet.size() > 0) {
			for (int i = 1; i <= mLCDiseaseImpartSet.size(); i++) {
				mLCDiseaseImpartSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCDiseaseImpartSet.get(i).setSerialNo(
						PubFun1.CreateMaxNo("DisImpSerlNo", 20));
				mLCDiseaseImpartSet.get(i)
						.setPrtNo(mLCGrpContSchema.getPrtNo());
				mLCDiseaseImpartSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLCDiseaseImpartSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLCDiseaseImpartSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCDiseaseImpartSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
				// logger.debug(mLCHistoryImpartSet.get(i).getPeoples());
			}

			if (mLCDiseaseImpartSet != null) {
				map.put(mLCDiseaseImpartSet, "DELETE&INSERT");
			}
		}
		// 客户服务信息
		if (mLCGrpServInfoSet != null && mLCGrpServInfoSet.size() > 0) {
			logger.debug("--Star servInfo--");
			for (int i = 1; i <= mLCGrpServInfoSet.size(); i++) {
				mLCGrpServInfoSet.get(i).setGrpContNo(
						mLCGrpContSchema.getGrpContNo());
				mLCGrpServInfoSet.get(i).setProposalGrpContNo(
						mLCGrpContSchema.getProposalGrpContNo());
				mLCGrpServInfoSet.get(i).setPrtNo(mLCGrpContSchema.getPrtNo());
				mLCGrpServInfoSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLCGrpServInfoSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLCGrpServInfoSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLCGrpServInfoSet.get(i).setModifyTime(PubFun.getCurrentTime());
				// logger.debug(mLCHistoryImpartSet.get(i).getPeoples());
			}

			if (mLCGrpServInfoSet != null) {
				// map.put(mLCGrpServInfoSet, "DELETE&INSERT");
				map.put("delete from LCGrpServInfo where GrpContNo='"
						+ mLCGrpContSchema.getGrpContNo() + "'", "DELETE");
				map.put(mLCGrpServInfoSet, "INSERT");
			}
		}
		// //////////////////////////////////////////////////////////////////////////////
		// 多业务员
		// LACommisionDetailDB oldLACommisionDetailDB = new
		// LACommisionDetailDB();
		// oldLACommisionDetailDB.setGrpContNo(mLCGrpContSchema.
		// getGrpContNo());
		// oldLACommisionDetailDB.setAgentCode(mLCGrpContSchema.getAgentCode());
		// if (!oldLACommisionDetailDB.getInfo()) {
		// // @@错误处理
		// this.mErrors.copyAllErrors(oldLCGrpContDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "GroupContBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "LACommisionDetail表查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		for (int i = 0; i < this.mLACommisionDetailSet.size(); i++) {
            
			this.mLACommisionDetailSet.get(i + 1).setMakeDate(
					PubFun.getCurrentDate());
			this.mLACommisionDetailSet.get(i + 1).setMakeTime(
					PubFun.getCurrentTime());
			this.mLACommisionDetailSet.get(i + 1).setModifyDate(
					this.theCurrentDate);
			this.mLACommisionDetailSet.get(i + 1).setModifyTime(
					this.theCurrentTime);
			this.mLACommisionDetailSet.get(i + 1).setOperator(
					mGlobalInput.Operator);
		}

		String SQL = "delete from LACommisionDetail where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo() + "'";
		map.put(SQL, "DELETE");
		map.put(this.mLACommisionDetailSet, "INSERT");
		map.put("update lcgrppol set cvalidate='"
				+ mLCGrpContSchema.getCValiDate() + "',customerno='"
				+ mLCGrpContSchema.getAppntNo() + "' where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo() + "'", "UPDATE");
		// /////////////////////////////////////////////////////////////////////////////
		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (mOperate.equals("DELETE||GROUPPOL")) {
			return deleteData();
		} else if (mOperate.equals("INSERT||GROUPPOL")) {
			return insertData();
		} else { // mOperate.equals("UPDATE||GROUPPOL")
			return updateData();
		}
	}

	/**
	 * 根据业务需要进行合法性检查，不满足条件返回false
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		CommonCheck ck = new CommonCheck();
		if (mOperate.equals("DELETE||GROUPPOL")) {
			if (mLCGrpContSchema.getGrpContNo() == null
					|| mLCGrpContSchema.getGrpContNo().equals("")) {
				mErrors.addOneError(new CError("没有找到集体保单号"));
				return false;
			}
			return true;
		}
		if (mOperate.equals("UPDATE||GROUPPOL")) {
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			LCGrpContSet tLCGrpContSet = new LCGrpContSet();
			tLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
			tLCGrpContSet = tLCGrpContDB.query();
			String tCvalidate = tLCGrpContSet.get(1).getCValiDate();
			if(mLCGrpContSchema.getCValiDate()==null&&tCvalidate!=null
					||mLCGrpContSchema.getCValiDate()!=null&&tCvalidate==null)
			{
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
				tLCPolSet = tLCPolDB.query();
				if (tLCPolSet != null && tLCPolSet.size() > 0) {
					CError.buildErr(this,"该投保单号下已经录入险种，不能再修改保单生效日!");
					return false;

				}
			}
			if (!tCvalidate.equals(mLCGrpContSchema.getCValiDate())) {
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSet tLCPolSet = new LCPolSet();
				tLCPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
				tLCPolSet = tLCPolDB.query();
				if (tLCPolSet != null && tLCPolSet.size() > 0) {
					CError.buildErr(this,"该投保单号下已经录入险种，不能再修改保单生效日!");
					return false;

				}
			}

		}
		if (mOperate.equals("INSERT||GROUPPOL")) {

			String strSQL = "select count(*) from LCGrpCont where prtno='"
					+ mLCGrpContSchema.getPrtNo() + "'";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(strSQL);
			String strCount = tSSRS.GetText(1, 1);
			int SumCount = Integer.parseInt(strCount);
			if (SumCount > 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该投保单印刷号下已经存在合同!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}
		if (!mOperate.equals("DELETE||GROUPPOL")) {
			if (mLCGrpContSchema.getManageCom() == null
					|| mLCGrpContSchema.getManageCom().trim().equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpContBL";
				tError.functionName = "checkData";
				tError.errorMessage = "请录入管理机构!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (!checkLCGrpAddress()) {
				return false;
			}
			// 非空检查
			ck.checknoempty("印刷号码", mLCGrpContSchema.getPrtNo());
			ck.checknoempty("单位名称", mLCGrpContSchema.getGrpName());
			ck.checknoempty("保单生效日", mLCGrpContSchema.getCValiDate());
			// 重复值检查
			// 匹配值检查
			// 如果数据检查中出现了违反完整性的数据
			if (ck.mErrors.getErrorCount() != 0) {
				mErrors.copyAllErrors(ck.mErrors);
				return false;
			}

			// 校验多业务员的佣金比例
			for (int i = 0; i < this.mLACommisionDetailSet.size(); i++) {
				if (mLACommisionDetailSet.size() == 1) {
					if (mLACommisionDetailSet.get(i + 1).getBusiRate() != 1) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkData";
						tError.errorMessage = "业务员佣金比例分配错误!";
						this.mErrors.addOneError(tError);
						return false;

					}
				}
				if (mLACommisionDetailSet.size() > 1
						&& !mLCGrpContSchema.getAgentType().equals("03")) {
					if (this.mLACommisionDetailSet.get(i + 1).getBusiRate() <= 0
							|| this.mLACommisionDetailSet.get(i + 1)
									.getBusiRate() >= 1) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkData";
						tError.errorMessage = "业务员佣金比例分配错误!";
						this.mErrors.addOneError(tError);
						return false;

					}
				}

			}
			int repeat = 0;
			String AgentCode = "";
			LAAgentDB tLAAgentDB = new LAAgentDB();
			String tsql = "";
			for (int x = 1; x <= this.mLACommisionDetailSet.size(); x++) {
				repeat = 0;
				AgentCode = mLACommisionDetailSet.get(x).getAgentCode();
				// 增加交叉销售的支持
				LAAgentSet tLAAgentSet = new LAAgentSet();
				tsql = "select * From laagent where agentcode='" + AgentCode
						+ "'";
				tLAAgentSet = tLAAgentDB.executeQuery(tsql);
				if (tLAAgentSet == null || tLAAgentSet.size() == 0) {
					// tongmeng 2008-07-17 调试基版注释
					// mLACommisionDetailSet.get(x).setAgentFlag("2");
				} else {
					// mLACommisionDetailSet.get(x).setAgentFlag("1");
				}

				for (int y = 1; y <= this.mLACommisionDetailSet.size(); y++) {
					if (mLACommisionDetailSet.get(y).getAgentCode().equals(
							AgentCode)) {
						repeat++;
					}
					if (repeat > 1) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkData";
						tError.errorMessage = "业务员录入重复!";
						this.mErrors.addOneError(tError);
						return false;
					}
				}
			}

		}
		return true;
	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		// 记录当前操作员
		mLCGrpContSchema.setOperator(mGlobalInput.Operator);
		mLCGrpAppntSchema.setOperator(mGlobalInput.Operator);
		mLCGrpAddressSchema.setOperator(mGlobalInput.Operator);
		mLDGrpSchema.setOperator(mGlobalInput.Operator);
		// 记录最后一次修改日期
		mLCGrpContSchema.setModifyDate(theCurrentDate);
		mLCGrpAppntSchema.setModifyDate(theCurrentDate);
		mLCGrpAddressSchema.setModifyDate(theCurrentDate);
		mLDGrpSchema.setModifyDate(theCurrentDate);
		// 记录最后一次修改时间
		mLCGrpContSchema.setModifyTime(theCurrentTime);
		mLCGrpAppntSchema.setModifyTime(theCurrentTime);
		mLCGrpAddressSchema.setModifyTime(theCurrentTime);
		mLDGrpSchema.setModifyTime(theCurrentTime);

		mInputData.clear();
		mInputData.add(map);
		// 数据放入容器，传向UI层
		mResult.clear();
		mResult.add(mLCGrpContSchema);
		mResult.add(mLCGrpAppntSchema);
		mResult.add(mLCGrpAddressSchema);
		mResult.add(mLDGrpSchema);
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 处理删除逻辑，删除处理过的数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		LCContDB mLCContDB = new LCContDB();
		mLCContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		int lccontNum = mLCContDB.getCount();
		if (mLCContDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "查询个人合同失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (lccontNum > 0) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "该团体合同下还有个人合同没有删除，不能进行删除操作！";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGrpPolDB mLCGrpPolDB = new LCGrpPolDB();
		mLCGrpPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		int lcGrpPolNum = mLCGrpPolDB.getCount();
		if (mLCGrpPolDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "查询集体险种失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (lcGrpPolNum > 0) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "该团体合同下还有集体险种没有删除，不能进行删除操作！";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGeneralDB mLCGeneralDB = new LCGeneralDB();
		mLCGeneralDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		int lcGeneralNum = mLCGeneralDB.getCount();
		if (mLCGeneralDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "查询总括保单分单失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (lcGeneralNum > 0) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "该团体合同下还有总括分单没有删除，不能进行删除操作！";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCGrpAppntDB mLCGrpAppntDB = new LCGrpAppntDB();
		mLCGrpAppntDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		mLCGrpAppntDB.setCustomerNo(mLCGrpContSchema.getAppntNo());
		if (!mLCGrpAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "deleteData";
			tError.errorMessage = "查询集体投保人失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 开始日志记录
		String tGrpContNo = mLCGrpContSchema.getGrpContNo();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		if (tLCGrpContDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpContBL";
			tError.functionName = "dealData";
			tError.errorMessage = "请您确认：要删除的集体合同代码输入错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
//		团险特约 add  by liuqh 2009-02-27
		map.put("delete from lccgrpspec where grpcontno = '"+mLCCGrpSpecSchema.getGrpContNo()+"'", "DELETE");
		//特约

		mLCDelPolLog.setOtherNo(tLCGrpContDB.getGrpContNo());
		mLCDelPolLog.setOtherNoType("0");
		mLCDelPolLog.setPrtNo(tLCGrpContDB.getPrtNo());
		if (tLCGrpContDB.getAppFlag().equals("1")) {
			mLCDelPolLog.setIsPolFlag("1");
		} else {
			mLCDelPolLog.setIsPolFlag("0");
		}
		mLCDelPolLog.setOperator(mGlobalInput.Operator);
		mLCDelPolLog.setManageCom(mGlobalInput.ManageCom);
		mLCDelPolLog.setMakeDate(theCurrentDate);
		mLCDelPolLog.setMakeTime(theCurrentTime);
		mLCDelPolLog.setModifyDate(theCurrentDate);
		mLCDelPolLog.setModifyTime(theCurrentTime);
		map.put(mLCDelPolLog, "INSERT");
		// 开始备份
		map.put(
				"insert into LOBGrpCont (select GRPCONTNO, PROPOSALGRPCONTNO, PRTNO, SALECHNL, MANAGECOM, AGENTCOM, AGENTTYPE, AGENTCODE, AGENTGROUP, AGENTCODE1, PASSWORD, PASSWORD2, APPNTNO, ADDRESSNO, PEOPLES2, GRPNAME, BUSINESSTYPE, GRPNATURE, RGTMONEY, ASSET, NETPROFITRATE, MAINBUSSINESS, CORPORATION, COMAERA, FAX, PHONE, GETFLAG, SATRAP, EMAIL, FOUNDDATE, GRPGROUPNO, BANKCODE, BANKACCNO, ACCNAME, DISPUTEDFLAG, OUTPAYFLAG, GETPOLMODE, LANG, CURRENCY, LOSTTIMES, PRINTCOUNT, REGETDATE, LASTEDORDATE, LASTGETDATE, LASTLOANDATE, SPECFLAG, GRPSPEC, PAYMODE, SIGNCOM, SIGNDATE, SIGNTIME, CVALIDATE, PAYINTV, MANAGEFEERATE, EXPPEOPLES, EXPPREMIUM, EXPAMNT, PEOPLES, MULT, PREM, AMNT, SUMPREM, SUMPAY, DIF, REMARK, STANDBYFLAG1, STANDBYFLAG2, STANDBYFLAG3, INPUTOPERATOR, INPUTDATE, INPUTTIME, APPROVEFLAG, APPROVECODE, APPROVEDATE, APPROVETIME, UWOPERATOR, UWFLAG, UWDATE, UWTIME, APPFLAG, POLAPPLYDATE, CUSTOMGETPOLDATE, GETPOLDATE, GETPOLTIME, STATE, OPERATOR, MAKEDATE, MAKETIME, MODIFYDATE, MODIFYTIME, ENTERKIND, AMNTGRADE, PEOPLES3, ONWORKPEOPLES, OFFWORKPEOPLES, OTHERPEOPLES, RELAPEOPLES, RELAMATEPEOPLES, RELAYOUNGPEOPLES, RELAOTHERPEOPLES, FIRSTTRIALOPERATOR, FIRSTTRIALDATE, FIRSTTRIALTIME, RECEIVEOPERATOR, RECEIVEDATE, RECEIVETIME, TEMPFEENO, HANDLERNAME, HANDLERDATE, HANDLERPRINT, AGENTDATE, BUSINESSBIGTYPE, MARKETTYPE, REPORTNO, CONFERNO, SIGNREPORTNO, AGENTCONFERNO, CONTTYPE, EDORCALTYPE, CLIENTCARE, FUNDREASON, BACKDATEREMARK, CLIENTNEEDJUDGE, DONATECONTFLAG, FUNDJUDGE, EXAMANDAPPNO, EDORTRANSPERCENT, CARDFLAG from LCGrpCont where GrpContNo='"
						+ tGrpContNo + " ')", "INSERT");
		// map.put("insert into LOBCont (select * from LCCont where
		// GrpContNo='"+tGrpContNo+" ')", "INSERT");
		map.put(
				"insert into LOBGrpAppnt (select * from LCGrpAppnt where GrpContNo='"
						+ tGrpContNo + " ')", "INSERT");
		// map.put("insert into LOBGrpPol (select * from LCGrpPol where
		// GrpContNo='"+tGrpContNo+" ')", "INSERT");
		// map.put("insert into LOBPol (select * from LCPol where
		// GrpContNo='"+tGrpContNo+" ')", "INSERT");
		// 开始删除
		map.put(mLCGrpAppntDB.getSchema(), "DELETE");
		map.put(mLCGrpContSchema, "DELETE");
		map.put("delete from LCDiseaseImpart where GrpContNo='" + tGrpContNo
				+ "'", "DELETE");
		map.put("delete from LCHistoryImpart where GrpContNo='" + tGrpContNo
				+ "'", "DELETE");
		map.put("delete from LCGrpServInfo where GrpContNo='" + tGrpContNo
				+ "'", "DELETE");
		// 删除多业务员
		map.put("delete from LACommisionDetail where GrpContNo='" + tGrpContNo
				+ "'", "DELETE");

		return true;
	}

	/**
	 * 检查地址数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 * 
	 * @return boolean
	 */

	private boolean checkLCGrpAddress() {

		if (mLCGrpAddressSchema != null) {
			if (mLCGrpAddressSchema.getAddressNo() != null) {
				// 如果有地址号
				if (!mLCGrpAddressSchema.getAddressNo().equals("")) {
					LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();

					tLCGrpAddressDB.setAddressNo(mLCGrpAddressSchema
							.getAddressNo());
					tLCGrpAddressDB.setCustomerNo(mLCGrpAddressSchema
							.getCustomerNo());
					if (tLCGrpAddressDB.getInfo() == false) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "请去掉单位地址编码!";
						this.mErrors.addOneError(tError);

						return false;
					}
					// if (!StrTool.compareString(mLCGrpAddressSchema.
					// getCustomerNo(),
					// tLCGrpAddressDB.getCustomerNo()))
					// {
					// CError tError = new CError();
					// tError.moduleName = "ContBL";
					// tError.functionName = "checkAddress";
					// tError.errorMessage =
					// "您输入的地址信息中的客户号"+mLCGrpAddressSchema.
					// getCustomerNo()+"与数据库里对应地址的客户号"+tLCGrpAddressDB.getCustomerNo()+"不符，请去掉地址号，重新生成!";
					// this.mErrors.addOneError(tError);
					//
					// return false;
					//
					// }
					//
					// if (!StrTool.compareString(mLCGrpAddressSchema.
					// getAddressNo(),
					// tLCGrpAddressDB.getAddressNo()))
					// {
					// CError tError = new CError();
					// tError.moduleName = "ContBL";
					// tError.functionName = "checkAddress";
					// tError.errorMessage =
					// "您输入的地址信息中的客户号"+mLCGrpAddressSchema.
					// getCustomerNo()+"与数据库里对应地址的客户号"+tLCGrpAddressDB.getCustomerNo()+"不符，请去掉地址号，重新生成!";
					// this.mErrors.addOneError(tError);
					//
					// return false;
					//
					// }

					if (!StrTool.compareString(mLCGrpAddressSchema
							.getGrpAddress(), tLCGrpAddressDB.getGrpAddress())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的单位地址"
								+ mLCGrpAddressSchema.getGrpAddress()
								+ "与数据库里对应地址的单位地址"
								+ tLCGrpAddressDB.getGrpAddress()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema
							.getGrpZipCode(), tLCGrpAddressDB.getGrpZipCode())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的单位邮政编码"
								+ mLCGrpAddressSchema.getGrpZipCode()
								+ "与数据库里对应地址的单位邮政编码"
								+ tLCGrpAddressDB.getGrpZipCode()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema
							.getLinkMan1(), tLCGrpAddressDB.getLinkMan1())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人一"
								+ mLCGrpAddressSchema.getLinkMan1()
								+ "与数据库里对应地址的联系人一"
								+ tLCGrpAddressDB.getLinkMan1()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool
							.compareString(
									mLCGrpAddressSchema.getDepartment1(),
									tLCGrpAddressDB.getDepartment1())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人一部门"
								+ mLCGrpAddressSchema.getDepartment1()
								+ "与数据库里对应地址的联系人一部门"
								+ tLCGrpAddressDB.getDepartment1()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema
							.getHeadShip1(), tLCGrpAddressDB.getHeadShip1())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人一职务"
								+ mLCGrpAddressSchema.getDepartment1()
								+ "与数据库里对应地址的联系人一职务"
								+ tLCGrpAddressDB.getDepartment1()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema.getPhone1(),
							tLCGrpAddressDB.getPhone1())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人一电话"
								+ mLCGrpAddressSchema.getDepartment1()
								+ "与数据库里对应地址的联系人一电话"
								+ tLCGrpAddressDB.getDepartment1()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(
							mLCGrpAddressSchema.getE_Mail1(), tLCGrpAddressDB
									.getE_Mail1())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人一EMAIL"
								+ mLCGrpAddressSchema.getDepartment1()
								+ "与数据库里对应地址的联系人一EMAIL"
								+ tLCGrpAddressDB.getDepartment1()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema.getFax1(),
							tLCGrpAddressDB.getFax1())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人一传真"
								+ mLCGrpAddressSchema.getDepartment1()
								+ "与数据库里对应地址的联系人一传真"
								+ tLCGrpAddressDB.getDepartment1()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema
							.getLinkMan2(), tLCGrpAddressDB.getLinkMan2())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人二"
								+ mLCGrpAddressSchema.getLinkMan2()
								+ "与数据库里对应地址的联系人二"
								+ tLCGrpAddressDB.getLinkMan2()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool
							.compareString(
									mLCGrpAddressSchema.getDepartment2(),
									tLCGrpAddressDB.getDepartment2())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人二部门"
								+ mLCGrpAddressSchema.getDepartment2()
								+ "与数据库里对应地址的联系人二部门"
								+ tLCGrpAddressDB.getDepartment2()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema
							.getHeadShip2(), tLCGrpAddressDB.getHeadShip2())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人二职务"
								+ mLCGrpAddressSchema.getDepartment2()
								+ "与数据库里对应地址的联系人二职务"
								+ tLCGrpAddressDB.getDepartment2()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema.getPhone2(),
							tLCGrpAddressDB.getPhone2())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人二电话"
								+ mLCGrpAddressSchema.getDepartment2()
								+ "与数据库里对应地址的联系人二电话"
								+ tLCGrpAddressDB.getDepartment2()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(
							mLCGrpAddressSchema.getE_Mail2(), tLCGrpAddressDB
									.getE_Mail2())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人二EMAIL"
								+ mLCGrpAddressSchema.getDepartment2()
								+ "与数据库里对应地址的联系人二EMAIL"
								+ tLCGrpAddressDB.getDepartment2()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

					if (!StrTool.compareString(mLCGrpAddressSchema.getFax2(),
							tLCGrpAddressDB.getFax2())) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkAddress";
						tError.errorMessage = "您输入的地址信息中的联系人二传真"
								+ mLCGrpAddressSchema.getDepartment2()
								+ "与数据库里对应地址的联系人二传真"
								+ tLCGrpAddressDB.getDepartment2()
								+ "不符，请去掉地址号，重新生成!";
						this.mErrors.addOneError(tError);

						return false;

					}

				}
			}

		}

		return true;
	}

}
