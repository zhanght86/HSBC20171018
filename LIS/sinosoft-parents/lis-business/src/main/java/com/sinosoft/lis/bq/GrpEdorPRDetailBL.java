package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPGrpAddressBL;
import com.sinosoft.lis.bl.LPGrpAppntBL;
import com.sinosoft.lis.bl.LPGrpContBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGeneralDB;
import com.sinosoft.lis.db.LCGeneralToRiskDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGeneralSchema;
import com.sinosoft.lis.schema.LCGeneralToRiskSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGeneralSchema;
import com.sinosoft.lis.schema.LPGeneralToRiskSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGeneralSet;
import com.sinosoft.lis.vschema.LCGeneralToRiskSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGeneralSet;
import com.sinosoft.lis.vschema.LPGeneralToRiskSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全团单迁移
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0
 */
public class GrpEdorPRDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorPRDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private String manageCom = "";
	String AddressNo = "";

	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPGrpContSchema mLPGrpContSchema = new LPGrpContSchema();
	private LPGrpAppntSchema mLPGrpAppntSchema = new LPGrpAppntSchema();

	private LPGrpAddressSchema mLPGrpAddressSchema = new LPGrpAddressSchema();
	private LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	private LCGeneralSchema mLCGeneralSchema = new LCGeneralSchema();
	private LCGeneralToRiskSchema mLCGeneralToRiskSchema = new LCGeneralToRiskSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	MMap map = new MMap();

	boolean newaddrFlag = false;

	public GrpEdorPRDetailBL() {
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
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		// 数据查询业务处理(queryData())

		if (!queryData())
			return false;

		// 数据校验操作（checkdata)
		if (!checkData())
			return false;
		// 数据准备操作（preparedata())
		if (!prepareData())
			return false;

		if (!dealData())
			return false;

		PubSubmit tPubSubmit = new PubSubmit();
		if (tPubSubmit.submitData(mInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据查询业务处理(queryData())
	 * 
	 */
	private boolean queryData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		map.put(mLPGrpEdorItemSchema, "UPDATE");
		map.put(mLPGrpAppntSchema, "DELETE&INSERT");
		map.put(mLPGrpAddressSchema, "DELETE&INSERT");
		map.put(mLPGrpContSchema, "DELETE&INSERT");

		mInputData.clear();
		mInputData.add(map);

		mResult.clear();
		mResult.add(mLPGrpAddressSchema);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private void getInputData(VData cInputData) {
		// addrFlag=(String)cInputData.get(0);
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mLPGrpAppntSchema = (LPGrpAppntSchema) cInputData
				.getObjectByObjectName("LPGrpAppntSchema", 0);
		mLPGrpAddressSchema = (LPGrpAddressSchema) cInputData
				.getObjectByObjectName("LPGrpAddressSchema", 0);
		manageCom = (String) cInputData.getObjectByObjectName("String", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

	}

	/**
	 * 更新集体保单保全信息
	 * 
	 * @return
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		boolean flag = true;
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PAppntGrpBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全项目数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB);

		if (!tLPGrpEdorItemDB.getEdorState().trim().equals("1")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PAppntGrpBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全项目已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		// 如果地址号不为空，需要校验该地址号是否为本次保全申请产生，如果不是本次保全申请产生，则不让修改
		return flag;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		// 准备团体投保人信息
		LPGrpAppntBL tLPGrpAppntBL = new LPGrpAppntBL();
		if (!tLPGrpAppntBL.queryLPGrpAppnt(mLPGrpEdorItemSchema)) {
			CError.buildErr(this, "查询投保人信息失败！");
			return false;
		}

		tLPGrpAppntBL.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpAppntBL.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAppntBL.setEdorType(mLPGrpEdorItemSchema.getEdorType());

		tLPGrpAppntBL.setEdorNo(mLPGrpAppntSchema.getEdorNo());
		tLPGrpAppntBL.setEdorType(mLPGrpAppntSchema.getEdorType());
		tLPGrpAppntBL.setPostalAddress(mLPGrpAppntSchema.getPostalAddress());
		tLPGrpAppntBL.setZipCode(mLPGrpAppntSchema.getZipCode());
		tLPGrpAppntBL.setAddressNo(mLPGrpAppntSchema.getAddressNo());
		tLPGrpAppntBL.setPhone(mLPGrpAppntSchema.getPhone());
		tLPGrpAppntBL.setCustomerNo(mLPGrpAppntSchema.getCustomerNo());
		// logger.debug(mLPGrpAppntSchema.getCustomerNo());

		LPGrpContBL tLPGrpContBL = new LPGrpContBL();
		if (!tLPGrpContBL.queryLPGrpCont(mLPGrpEdorItemSchema)) {
			CError.buildErr(this, "查询保单信息失败！");
			return false;
		}

		tLPGrpContBL.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContBL.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContBL.setAppntNo(tLPGrpAppntBL.getCustomerNo()); // 客户号码
		tLPGrpContBL.setManageCom(manageCom);
		tLPGrpContBL.setAddressNo(mLPGrpAppntSchema.getAddressNo());
		tLPGrpContBL.setFirstTrialDate(PubFun.getCurrentDate());

		LPGrpAddressBL tLPGrpAddressBL = new LPGrpAddressBL();
		// 如果地址号不为空
		try {
			if (StrTool.compareString(mLPGrpAppntSchema.getAddressNo(), "")) {
				newaddrFlag = true;
				SSRS tSSRS = new SSRS();

				String sql = " select max(AddressNo) from LPGrpAddress where CustomerNo='"
						+ mLPGrpAddressSchema.getCustomerNo()
						+ "' and edorno='"
						+ mLPGrpAddressSchema.getEdorNo()
						+ "'";
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sql);

				if (!tExeSQL.mErrors.needDealError()
						&& StrTool.compareString(tSSRS.GetText(1, 1), "")) {
					sql = " select max(AddressNo) from LPGrpAddress a,LPGrpEdorItem b where a.CustomerNo='"
							+ mLPGrpAddressSchema.getCustomerNo()
							+ "' and a.edorno=b.edorno"
							+ " and b.EdorState='2'";
					tSSRS = tExeSQL.execSQL(sql);
					if (!tExeSQL.mErrors.needDealError()
							&& StrTool.compareString(tSSRS.GetText(1, 1), "")) {
						sql = "Select Case When max(AddressNo) Is Null Then '0' Else max(AddressNo) End from LCGrpAddress where CustomerNo='"
								+ mLPGrpAddressSchema.getCustomerNo() + "'";
						tSSRS = tExeSQL.execSQL(sql);
						if (!tExeSQL.mErrors.needDealError()
								&& StrTool.compareString(tSSRS.GetText(1, 1),
										"")) {
							CError.buildErr(this, "查询客户地址失败");
							return false;
						} else if (tExeSQL.mErrors.needDealError()) {
							CError.buildErr(this, "查询客户地址失败");
							return false;
						}

					} else if (tExeSQL.mErrors.needDealError()) {
						CError.buildErr(this, "查询客户地址失败");
						return false;
					}

				} else if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "查询客户地址失败");
					return false;
				}

				Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
				int ttNo = firstinteger.intValue() + 1;
				Integer integer = new Integer(ttNo);
				AddressNo = integer.toString();
				logger.debug("得到的地址码是：" + AddressNo);
				if (!AddressNo.equals("")) {
					tLPGrpAddressBL.setAddressNo(AddressNo);
					tLPGrpAppntBL.setAddressNo(AddressNo);
					tLPGrpContBL.setAddressNo(AddressNo);
				} else {
					mErrors.addOneError(new CError("客户地址号码生成失败"));
					return false;
				}
			} else {
				AddressNo = mLPGrpAppntSchema.getAddressNo();
				tLPGrpAddressBL.setAddressNo(AddressNo);
				tLPGrpAppntBL.setAddressNo(AddressNo);
				tLPGrpContBL.setAddressNo(AddressNo);
			}

		} catch (java.lang.NumberFormatException ex) {
			logger.debug("得到最大地址号失败");
			return false;
		}

		tLPGrpAddressBL.setEdorNo(mLPGrpAddressSchema.getEdorNo());
		tLPGrpAddressBL.setEdorType(mLPGrpAddressSchema.getEdorType());
		tLPGrpAddressBL.setCustomerNo(mLPGrpAddressSchema.getCustomerNo()); // 客户号码
		tLPGrpAddressBL.setAddressNo(AddressNo); // 地址号码
		tLPGrpAddressBL.setGrpAddress(mLPGrpAddressSchema.getGrpAddress()); // 单位地址
		tLPGrpAddressBL.setGrpZipCode(mLPGrpAddressSchema.getGrpZipCode()); // 单位邮编
		tLPGrpAddressBL.setLinkMan1(mLPGrpAddressSchema.getLinkMan1());
		tLPGrpAddressBL.setPhone1(mLPGrpAddressSchema.getPhone1());
		tLPGrpAddressBL.setE_Mail1(mLPGrpAddressSchema.getE_Mail1());
		tLPGrpAddressBL.setFax1(mLPGrpAddressSchema.getFax1());

		tLPGrpAppntBL.setDefaultFields();
		tLPGrpAppntBL.setOperator(mGlobalInput.Operator);

		tLPGrpContBL.setOperator(mGlobalInput.Operator);
		tLPGrpAddressBL.setDefaultFields();
		tLPGrpAddressBL.setOperator(mGlobalInput.Operator);

		mLPGrpContSchema.setSchema(tLPGrpContBL);
		mLPGrpAppntSchema.setSchema(tLPGrpAppntBL);
		mLPGrpAddressSchema.setSchema(tLPGrpAddressBL);

		mLPGrpEdorItemSchema.setManageCom(manageCom);

		// 将一下几个表的管理机构进行变更
		// *********************************************//

		Reflections tRef = new Reflections();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();

		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// tLCGrpPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet.size() < 1 || tLCGrpPolSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "团单险种表查询失败!";
			this.mErrors.addOneError(tError);
			// return false;
		} else {
			for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
				LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
				mLCGrpPolSchema.setSchema(tLCGrpPolSet.get(i).getSchema());
				tRef.transFields(mLPGrpPolSchema, mLCGrpPolSchema);
				mLPGrpPolSchema.setManageCom(manageCom);
				mLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				mLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				mLPGrpPolSchema.setMakeDate(PubFun.getCurrentDate());
				mLPGrpPolSchema.setMakeTime(PubFun.getCurrentTime());
				mLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
				mLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
				tLPGrpPolSet.add(mLPGrpPolSchema);
			}
			map.put(tLPGrpPolSet, "DELETE&INSERT");
		}

		LCGeneralDB tLCGeneralDB = new LCGeneralDB();

		tLCGeneralDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// tLCGeneralDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPGeneralSet tLPGeneralSet = new LPGeneralSet();
		LCGeneralSet tLCGeneralSet = tLCGeneralDB.query();
		if (tLCGeneralSet.size() < 1 || tLCGeneralSet == null) {
		} else {
			for (int i = 1; i <= tLCGeneralSet.size(); i++) {
				LPGeneralSchema mLPGeneralSchema = new LPGeneralSchema();
				mLCGeneralSchema.setSchema(tLCGeneralSet.get(i).getSchema());
				tRef.transFields(mLPGeneralSchema, mLCGeneralSchema);
				mLPGeneralSchema.setManageCom(manageCom);
				mLPGeneralSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				mLPGeneralSchema
						.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				mLPGeneralSchema.setMakeDate(PubFun.getCurrentDate());
				mLPGeneralSchema.setMakeTime(PubFun.getCurrentTime());
				mLPGeneralSchema.setModifyDate(PubFun.getCurrentDate());
				mLPGeneralSchema.setModifyTime(PubFun.getCurrentTime());
				tLPGeneralSet.add(mLPGeneralSchema);
			}
			map.put(tLPGeneralSet, "DELETE&INSERT");
		}

		LCGeneralToRiskDB tLCGeneralToRiskDB = new LCGeneralToRiskDB();

		tLCGeneralToRiskDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// tLCGeneralToRiskDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPGeneralToRiskSet tLPGeneralToRiskSet = new LPGeneralToRiskSet();
		LCGeneralToRiskSet tLCGeneralToRiskSet = tLCGeneralToRiskDB.query();
		if (tLCGeneralToRiskSet.size() < 1 || tLCGeneralToRiskSet == null) {
		} else {
			for (int i = 1; i <= tLCGeneralToRiskSet.size(); i++) {

				LPGeneralToRiskSchema mLPGeneralToRiskSchema = new LPGeneralToRiskSchema();
				mLCGeneralToRiskSchema.setSchema(tLCGeneralToRiskSet.get(i)
						.getSchema());
				tRef
						.transFields(mLPGeneralToRiskSchema,
								mLCGeneralToRiskSchema);
				mLPGeneralToRiskSchema.setManageCom(manageCom);
				mLPGeneralToRiskSchema.setEdorNo(mLPGrpEdorItemSchema
						.getEdorNo());
				mLPGeneralToRiskSchema.setEdorType(mLPGrpEdorItemSchema
						.getEdorType());
				mLPGeneralToRiskSchema.setMakeDate(PubFun.getCurrentDate());
				mLPGeneralToRiskSchema.setMakeTime(PubFun.getCurrentTime());
				mLPGeneralToRiskSchema.setModifyDate(PubFun.getCurrentDate());
				mLPGeneralToRiskSchema.setModifyTime(PubFun.getCurrentTime());
				tLPGeneralToRiskSet.add(mLPGeneralToRiskSchema);
			}
			map.put(tLPGeneralToRiskSet, "DELETE&INSERT");
		}

		// 处理团单下面的个单.
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCContSet = tLCContDB.query();
		logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		logger.debug(tLCContSet.size());
		for (int i = 1; i <= tLCContSet.size(); i++) {
			LCContSchema tLCContSchema = new LCContSchema();

			// main表
			LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();

			LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
			LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
			tLPGrpEdorMainDB.setEdorAcceptNo(mLPGrpEdorItemSchema
					.getEdorAcceptNo());
			tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpEdorMainDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tLPGrpEdorMainSchema = tLPGrpEdorMainDB.query().get(1);
			tRef.transFields(tLPEdorMainSchema, tLPGrpEdorMainSchema);
			tLPEdorMainSchema.setContNo(tLCContSet.get(i).getContNo());
			tLPEdorMainSet.add(tLPEdorMainSchema);

			// item表
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

			LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			tLPGrpEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema
					.getEdorAcceptNo());
			tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);
			tRef.transFields(tLPEdorItemSchema, tLPGrpEdorItemSchema);

			// 设置个单的被保人号,险种号
			String contNo = tLCContSet.get(i).getContNo();

			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(contNo);
			String insuredNo = tLCInsuredDB.query().get(1).getInsuredNo();
			tLPEdorItemSchema.setInsuredNo(insuredNo);

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(contNo);
			String polNo = tLCPolDB.query().get(1).getPolNo();
			tLPEdorItemSchema.setPolNo(polNo);

			tLPEdorItemSchema.setContNo(contNo);
			tLPEdorItemSet.add(tLPEdorItemSchema);

		}
		map.put(tLPEdorMainSet, "DELETE&INSERT");
		map.put(tLPEdorItemSet, "DELETE&INSERT");

		return true;

	}
}
