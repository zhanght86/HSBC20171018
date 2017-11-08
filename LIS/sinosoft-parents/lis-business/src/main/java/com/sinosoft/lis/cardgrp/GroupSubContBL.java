package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGeneralDB;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGeneralSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.vschema.LCGeneralSet;
import com.sinosoft.lis.vschema.LCGrpAddressSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单分单定制
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wzw
 * @version 1.0
 */
public class GroupSubContBL {
private static Logger logger = Logger.getLogger(GroupSubContBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 传输到后台处理的map */
	private MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpAppntSchema mLCGrpAppntSchema = new LCGrpAppntSchema();
	private LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();
	private LCGeneralSchema mLCGeneralSchema = new LCGeneralSchema();
	private String mCustomerNo = "";

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public GroupSubContBL() {
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

		if (this.dealData() == false) {
			return false;
		}

		logger.debug("---dealdata successful---");

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			return false;
		}

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CustomerNo", mCustomerNo);
		mResult.clear();
		mResult.add(tTransferData);

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

		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 团体客户地址实例
		mLCGrpAddressSchema.setSchema((LCGrpAddressSchema) mInputData
				.getObjectByObjectName("LCGrpAddressSchema", 0));

		// 团体客户实例
		mLDGrpSchema.setSchema((LDGrpSchema) mInputData.getObjectByObjectName(
				"LDGrpSchema", 0));
		logger.debug("mLDGrpSchema.getGrpName():"
				+ mLDGrpSchema.getGrpName());

		// 团体分单
		mLCGeneralSchema.setSchema((LCGeneralSchema) mInputData
				.getObjectByObjectName("LCGeneralSchema", 0));

		if ((mLCGrpAddressSchema == null) || (mLDGrpSchema == null)
				|| (mLCGeneralSchema == null)) {
			mErrors.addOneError(new CError("传入数据不完全!"));

			return false;
		}

		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (mOperate.equals("DELETE")) {
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setGrpContNo(mLCGeneralSchema.getGrpContNo());
			tLCInsuredDB.setExecuteCom(mLCGeneralSchema.getExecuteCom());
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
			if (tLCInsuredDB.mErrors.needDealError()) {
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			if (tLCInsuredSet != null && tLCInsuredSet.size() > 0) {
				mErrors.addOneError(new CError("已有被保人选择了该处理机构，无法删除！"));
				return false;
			}
			map.put(mLCGeneralSchema, "DELETE");
		} else if (mOperate.equals("INSERT")) {
			String tLimit = "";

			// 如果缺少客户号码，生成一个
			if ((mLCGeneralSchema.getCustomerNo() == null)
					|| mLCGeneralSchema.getCustomerNo().trim().equals("")) {
				LDGrpDB tLDGrpDB = new LDGrpDB();
				LDGrpSet tLDGrpSet = tLDGrpDB
						.executeQuery("select a.* from LDGrp a, LCGrpAddress b where a.CustomerNo = b.CustomerNo and a.GrpName = '"
								+ mLDGrpSchema.getGrpName()
								+ "' and b.GrpAddress = '"
								+ mLCGrpAddressSchema.getGrpAddress() + "'");

				if (tLDGrpDB.mErrors.needDealError()) {
					mErrors.addOneError(new CError("团体客户地址表查询失败"));

					return false;
				}

				if ((tLDGrpSet != null) && (tLDGrpSet.size() > 0)) {
					mLCGeneralSchema.setCustomerNo(tLDGrpSet.get(1)
							.getCustomerNo());

					LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
					tLCGrpAddressDB.setCustomerNo(mLCGeneralSchema
							.getCustomerNo());

					LCGrpAddressSet tLCGrpAddressSet = tLCGrpAddressDB.query();
					mLCGeneralSchema.setAddressNo(tLCGrpAddressSet.get(1)
							.getAddressNo());
					mLCGeneralSchema.setManageCom(mManageCom);
					mLCGeneralSchema.setOperator(mOperator);
					mLCGeneralSchema.setMakeDate(PubFun.getCurrentDate());
					mLCGeneralSchema.setMakeTime(PubFun.getCurrentTime());
					mLCGeneralSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGeneralSchema.setModifyTime(PubFun.getCurrentTime());

					map.put(mLCGeneralSchema, "INSERT");
				} else {
					tLimit = "SN";

					String tCustomerNo = PubFun1.CreateMaxNo("GRPNO", tLimit);
					mCustomerNo = tCustomerNo;
					if (tCustomerNo.equals("")) {
						mErrors.addOneError(new CError("客户号码生成失败"));

						return false;
					}

					mLCGeneralSchema.setCustomerNo(tCustomerNo);
					mLDGrpSchema.setCustomerNo(tCustomerNo);
					mLCGrpAddressSchema.setCustomerNo(tCustomerNo);

					tLimit = PubFun.getNoLimit(mManageCom);

					String tAddressNo = PubFun1
							.CreateMaxNo("AddressNo", tLimit);

					if (tAddressNo.equals("")) {
						mErrors.addOneError(new CError("客户地址号码生成失败"));

						return false;
					}

					mLCGeneralSchema.setAddressNo(tAddressNo);
					mLCGrpAddressSchema.setAddressNo(tAddressNo);

					mLCGeneralSchema.setOperator(mOperator);
					mLCGeneralSchema.setManageCom(mManageCom);
					mLCGeneralSchema.setMakeDate(PubFun.getCurrentDate());
					mLCGeneralSchema.setMakeTime(PubFun.getCurrentTime());
					mLCGeneralSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGeneralSchema.setModifyTime(PubFun.getCurrentTime());
					mLDGrpSchema.setOperator(mOperator);
					mLDGrpSchema.setMakeDate(PubFun.getCurrentDate());
					mLDGrpSchema.setMakeTime(PubFun.getCurrentTime());
					mLDGrpSchema.setModifyDate(PubFun.getCurrentDate());
					mLDGrpSchema.setModifyTime(PubFun.getCurrentTime());
					mLCGrpAddressSchema.setOperator(mOperator);
					mLCGrpAddressSchema.setMakeDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setMakeTime(PubFun.getCurrentTime());
					mLCGrpAddressSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setModifyTime(PubFun.getCurrentTime());

					map.put(mLCGeneralSchema, "INSERT");
					map.put(mLDGrpSchema, "INSERT");
					map.put(mLCGrpAddressSchema, "INSERT");
				}
			} else {
				LDGrpDB tLDGrpDB = new LDGrpDB();
				tLDGrpDB.setCustomerNo(mLCGeneralSchema.getCustomerNo());

				LDGrpSet tLDGrpSet = tLDGrpDB.query();
				LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
				tLCGrpAddressDB.setCustomerNo(mLCGeneralSchema.getCustomerNo());

				LCGrpAddressSet tLCGrpAddressSet = tLCGrpAddressDB.query();

				if ((tLDGrpSet == null) || (tLDGrpSet.size() <= 0)
						|| (tLCGrpAddressSet == null)
						|| (tLCGrpAddressSet.size() <= 0)) {
					mErrors.addOneError(new CError("无法查到客户号为"
							+ mLCGeneralSchema.getCustomerNo() + "的记录"));

					return false;
				}

				if (!StrTool.compareString(tLDGrpSet.get(1).getGrpName(),
						mLDGrpSchema.getGrpName())) {
					mErrors
							.addOneError(new CError("录入的数据(单位名称)与客户号为"
									+ mLCGeneralSchema.getCustomerNo()
									+ "的已有记录内容不匹配！"));

					return false;
				}

				mLCGeneralSchema.setAddressNo(tLCGrpAddressSet.get(1)
						.getAddressNo());
				mLCGeneralSchema.setManageCom(mManageCom);
				mLCGeneralSchema.setOperator(mOperator);
				mLCGeneralSchema.setMakeDate(PubFun.getCurrentDate());
				mLCGeneralSchema.setMakeTime(PubFun.getCurrentTime());
				mLCGeneralSchema.setModifyDate(PubFun.getCurrentDate());
				mLCGeneralSchema.setModifyTime(PubFun.getCurrentTime());

				if (!StrTool.compareString(tLCGrpAddressSet.get(1)
						.getGrpAddress(), mLCGrpAddressSchema.getGrpAddress())) {
					tLimit = PubFun.getNoLimit(mManageCom);

					String tAddressNo = PubFun1
							.CreateMaxNo("AddressNo", tLimit);

					if (tAddressNo.equals("")) {
						mErrors.addOneError(new CError("客户地址号码生成失败"));

						return false;
					}

					mLCGeneralSchema.setAddressNo(tAddressNo);
					mLCGrpAddressSchema.setAddressNo(tAddressNo);
					mLCGrpAddressSchema.setCustomerNo(mLCGeneralSchema
							.getCustomerNo());
					mLCGrpAddressSchema.setOperator(mOperator);
					mLCGrpAddressSchema.setMakeDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setMakeTime(PubFun.getCurrentTime());
					mLCGrpAddressSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setModifyTime(PubFun.getCurrentTime());
					map.put(mLCGrpAddressSchema, "INSERT");
				}

				map.put(mLCGeneralSchema, "INSERT");
			}
		} else { // mOperate.equals("UPDATE")

			String tLimit = "";
			LCGeneralDB tLCGeneralDB = new LCGeneralDB();
			tLCGeneralDB.setGrpContNo(mLCGeneralSchema.getGrpContNo());
			tLCGeneralDB.setExecuteCom(mLCGeneralSchema.getExecuteCom());

			LCGeneralSet tLCGeneralSet = tLCGeneralDB.query();

			if ((tLCGeneralSet == null) || (tLCGeneralSet.size() <= 0)) {
				mErrors.addOneError(new CError("无法查到原有分单记录，无法更新！"));

				return false;
			}

			LCGeneralSchema tOldLCGeneralSchema = tLCGeneralSet.get(1);

			if ((mLCGeneralSchema.getCustomerNo() == null)
					|| mLCGeneralSchema.getCustomerNo().trim().equals("")) {
				LDGrpDB tLDGrpDB = new LDGrpDB();
				LDGrpSet tLDGrpSet = tLDGrpDB
						.executeQuery("select a.* from LDGrp a, LCGrpAddress b where a.CustomerNo = b.CustomerNo and a.GrpName = '"
								+ mLDGrpSchema.getGrpName()
								+ "' and b.GrpAddress = '"
								+ mLCGrpAddressSchema.getGrpAddress() + "'");

				if (tLDGrpDB.mErrors.needDealError()) {
					mErrors.addOneError(new CError("团体客户地址表查询失败"));

					return false;
				}

				if ((tLDGrpSet != null) && (tLDGrpSet.size() > 0)) {
					mLCGeneralSchema.setCustomerNo(tLDGrpSet.get(1)
							.getCustomerNo());

					LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
					tLCGrpAddressDB.setCustomerNo(mLCGeneralSchema
							.getCustomerNo());

					LCGrpAddressSet tLCGrpAddressSet = tLCGrpAddressDB.query();
					mLCGeneralSchema.setAddressNo(tLCGrpAddressSet.get(1)
							.getAddressNo());
					mLCGeneralSchema.setManageCom(mManageCom);
					mLCGeneralSchema.setOperator(mOperator);
					mLCGeneralSchema.setMakeDate(tOldLCGeneralSchema
							.getMakeDate());
					mLCGeneralSchema.setMakeTime(tOldLCGeneralSchema
							.getMakeTime());
					mLCGeneralSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGeneralSchema.setModifyTime(PubFun.getCurrentTime());

					map.put(mLCGeneralSchema, "UPDATE");
				} else {
					tLimit = "SN";

					String tCustomerNo = PubFun1.CreateMaxNo("GRPNO", tLimit);
					mCustomerNo = tCustomerNo;

					if (tCustomerNo.equals("")) {
						mErrors.addOneError(new CError("客户号码生成失败"));

						return false;
					}

					mLCGeneralSchema.setCustomerNo(tCustomerNo);
					mLDGrpSchema.setCustomerNo(tCustomerNo);
					mLCGrpAddressSchema.setCustomerNo(tCustomerNo);

					tLimit = PubFun.getNoLimit(mManageCom);

					String tAddressNo = PubFun1
							.CreateMaxNo("AddressNo", tLimit);

					if (tAddressNo.equals("")) {
						mErrors.addOneError(new CError("客户地址号码生成失败"));

						return false;
					}

					mLCGeneralSchema.setAddressNo(tAddressNo);
					mLCGrpAddressSchema.setAddressNo(tAddressNo);

					mLCGeneralSchema.setOperator(mOperator);
					mLCGeneralSchema.setManageCom(mManageCom);
					mLCGeneralSchema.setMakeDate(tOldLCGeneralSchema
							.getMakeDate());
					mLCGeneralSchema.setMakeTime(tOldLCGeneralSchema
							.getMakeTime());
					mLCGeneralSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGeneralSchema.setModifyTime(PubFun.getCurrentTime());
					mLDGrpSchema.setOperator(mOperator);
					mLDGrpSchema.setMakeDate(PubFun.getCurrentDate());
					mLDGrpSchema.setMakeTime(PubFun.getCurrentTime());
					mLDGrpSchema.setModifyDate(PubFun.getCurrentDate());
					mLDGrpSchema.setModifyTime(PubFun.getCurrentTime());
					mLCGrpAddressSchema.setOperator(mOperator);
					mLCGrpAddressSchema.setMakeDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setMakeTime(PubFun.getCurrentTime());
					mLCGrpAddressSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setModifyTime(PubFun.getCurrentTime());

					map.put(mLCGeneralSchema, "UPDATE");
					map.put(mLDGrpSchema, "INSERT");
					map.put(mLCGrpAddressSchema, "INSERT");
				}
			} else {
				LDGrpDB tLDGrpDB = new LDGrpDB();
				tLDGrpDB.setCustomerNo(mLCGeneralSchema.getCustomerNo());

				LDGrpSet tLDGrpSet = tLDGrpDB.query();
				LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
				tLCGrpAddressDB.setCustomerNo(mLCGeneralSchema.getCustomerNo());

				LCGrpAddressSet tLCGrpAddressSet = tLCGrpAddressDB.query();

				if ((tLDGrpSet == null) || (tLDGrpSet.size() <= 0)
						|| (tLCGrpAddressSet == null)
						|| (tLCGrpAddressSet.size() <= 0)) {
					mErrors.addOneError(new CError("无法查到客户号为"
							+ mLCGeneralSchema.getCustomerNo() + "的记录"));

					return false;
				}

				if (!StrTool.compareString(tLDGrpSet.get(1).getGrpName(),
						mLDGrpSchema.getGrpName())) {
					mErrors
							.addOneError(new CError("录入的数据(单位名称)与客户号为"
									+ mLCGeneralSchema.getCustomerNo()
									+ "的已有记录内容不匹配！"));

					return false;
				}

				mLCGeneralSchema.setAddressNo(tLCGrpAddressSet.get(1)
						.getAddressNo());
				mLCGeneralSchema.setManageCom(mManageCom);
				mLCGeneralSchema.setOperator(mOperator);
				mLCGeneralSchema.setMakeDate(tOldLCGeneralSchema.getMakeDate());
				mLCGeneralSchema.setMakeTime(tOldLCGeneralSchema.getMakeTime());
				mLCGeneralSchema.setModifyDate(PubFun.getCurrentDate());
				mLCGeneralSchema.setModifyTime(PubFun.getCurrentTime());

				if (!StrTool.compareString(tLCGrpAddressSet.get(1)
						.getGrpAddress(), mLCGrpAddressSchema.getGrpAddress())) {
					tLimit = PubFun.getNoLimit(mManageCom);

					String tAddressNo = PubFun1
							.CreateMaxNo("AddressNo", tLimit);

					if (tAddressNo.equals("")) {
						mErrors.addOneError(new CError("客户地址号码生成失败"));

						return false;
					}

					mLCGeneralSchema.setAddressNo(tAddressNo);
					mLCGrpAddressSchema.setAddressNo(tAddressNo);
					mLCGrpAddressSchema.setCustomerNo(mLCGeneralSchema
							.getCustomerNo());
					mLCGrpAddressSchema.setOperator(mOperator);
					mLCGrpAddressSchema.setMakeDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setMakeTime(PubFun.getCurrentTime());
					mLCGrpAddressSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGrpAddressSchema.setModifyTime(PubFun.getCurrentTime());
					map.put(mLCGrpAddressSchema, "INSERT");
				}

				map.put(mLCGeneralSchema, "UPDATE");
			}
		}

		return true;
	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		mResult.clear();
		mInputData.add(map);
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}
}
