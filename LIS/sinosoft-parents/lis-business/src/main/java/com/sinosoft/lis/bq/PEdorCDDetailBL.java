package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web 业务系统</p>
 * <p>Description: 客户地址信息变更BL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */


import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

public class PEdorCDDetailBL {
private static Logger logger = Logger.getLogger(PEdorCDDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private Reflections mReflections = new Reflections();

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet(); // 因为是客户层，所以根据保全受理号去做
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 被选中的EdorNo */
	private String mLuckyEdorNo = "";

	/** 当前操作的AddressNo，可能是新生成的 */
	private String mNewAddressNo = "";
	/** 是否生成了新的AddressNo的标志 */
	private boolean mMakeNewAddressNoFlag = false;

	public PEdorCDDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
					.getObjectByObjectName("LPAddressSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mLPEdorItemSchema == null || mLPAddressSchema == null) {
				CError.buildErr(this, "接收数据失败！");
				return false;
			}
			// 获得被选中的EdorNo
			if (!getLuckyEdorNo()) {
				return false;
			}
			mLPAddressSchema.setEdorNo(this.mLuckyEdorNo);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();

		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorType("CD");
		tLPEdorItemDB.setEdorState("2");
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (mLPEdorItemSet != null && mLPEdorItemSet.size() > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改！";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorType("CD");
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据 注：界面不传ContNo，这里根据不同ContNo从一组生成多组
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		String tSql = "";
		SSRS tSSRS = null;
		ExeSQL tExeSQL = new ExeSQL();

		// 为了人工核保能查到数据，这里将被告知人涉及的所有保单和险种数据复制到P表
		LPContSet tLPContSet = new LPContSet();
		LPContSchema tLPContSchema = null;
		LCPolDB tLCPolDB = new LCPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		LCPolSet tContLCPolSet = null;
		LPPolSchema tLPPolSchema = null;
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LPDutySet tLPDutySet = new LPDutySet();
		LCDutySet tContLCDutySet = null;
		LPDutySchema tLPDutySchema = null;
		LCPremDB tLCPremDB = new LCPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		LCPremSet tContLCPremSet = null;
		LPPremSchema tLPPremSchema = null;
		LCGetDB tLCGetDB = new LCGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		LCGetSet tContLCGetSet = null;
		LPGetSchema tLPGetSchema = null;

		// 准备要更新的LPAddress表的最新信息，这组信息要先准备，因为地址号有可能新增加。
		// 如果新增了，要把新的地址号更新到相应的表中。
		LPAddressBL tLPAddressBL = new LPAddressBL();
		// 先获得当前可用的地址号
		// String newAddressNo = tLPAddressBL.getNewAddressNo(mLPAddressSchema);
		if (!this.getNewAddressNo()) {
			return false;
		}
		String newAddressNo = this.mNewAddressNo;
		// 获得当前对应地址信息
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		if (!this.mMakeNewAddressNoFlag) {
			if (!tLPAddressBL.queryLPAddress(this.mLPAddressSchema)) {
				return false;
			}
			tLPAddressSchema = tLPAddressBL.getSchema();
		}
		// 更新保全相关信息
		tLPAddressSchema.setCustomerNo(this.mLPAddressSchema.getCustomerNo());
		tLPAddressSchema.setAddressNo(newAddressNo); // 最新可用的地址号
		tLPAddressSchema.setEdorNo(mLPAddressSchema.getEdorNo());
		tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressSchema.setProvince(mLPAddressSchema.getProvince());
		tLPAddressSchema.setCity(mLPAddressSchema.getCity());
		tLPAddressSchema.setCounty(mLPAddressSchema.getCounty());
		tLPAddressSchema.setPostalAddress(mLPAddressSchema.getPostalAddress());
		tLPAddressSchema.setZipCode(mLPAddressSchema.getZipCode());
		tLPAddressSchema.setMobile(mLPAddressSchema.getMobile());
		tLPAddressSchema.setCompanyPhone(mLPAddressSchema.getCompanyPhone());
		tLPAddressSchema.setFax(mLPAddressSchema.getFax());
		tLPAddressSchema.setHomePhone(mLPAddressSchema.getHomePhone());
		tLPAddressSchema.setEMail(mLPAddressSchema.getEMail());
		tLPAddressSchema.setGrpName(mLPAddressSchema.getGrpName());
		tLPAddressSchema.setOperator(this.mGlobalInput.Operator);
		tLPAddressSchema.setModifyDate(strCurrentDate);
		tLPAddressSchema.setModifyTime(strCurrentTime);
		if (this.mMakeNewAddressNoFlag) {
			tLPAddressSchema.setMakeDate(strCurrentDate);
			tLPAddressSchema.setMakeTime(strCurrentTime);
		}
		mMap.put(tLPAddressSchema, "DELETE&INSERT");

		// ============================ 往LPAddress表分EdorNo插记录(可插多条) ==========
		// Begin add by lizhuo at 2005-10-19
		LPAddressSet aLPAddressSet = new LPAddressSet();
		LPAddressSchema aLPAddressSchema;
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			aLPAddressSchema = new LPAddressSchema();
			aLPAddressSchema.setSchema(tLPAddressSchema);
			if (!StrTool.compareString(aLPAddressSchema.getEdorNo(),
					mLPEdorItemSet.get(i).getEdorNo())) {
				aLPAddressSchema.setEdorNo(mLPEdorItemSet.get(i).getEdorNo());
				aLPAddressSet.add(aLPAddressSchema);
			}
		}
		if (aLPAddressSet.size() > 0) {
			mMap.put(aLPAddressSet, "DELETE&INSERT");
		}
		// ============================ 往LPAddress表分EdorNo插记录(可插多条) ==========
		// End

		// 提前声明
		LCContDB tLCContDB = new LCContDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = mLPEdorItemSet.get(i);

			// 获取相应保单信息
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContDB.setContNo(tLPEdorItemSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCDDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保单信息失败！保单号："
						+ tLPEdorItemSchema.getContNo();
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCContSchema.setSchema(tLCContDB.getSchema());

			// 投保人-------------------->
			if (tLCContSchema.getAppntNo() != null
					&& tLCContSchema.getAppntNo().equals(
							mLPEdorItemSchema.getInsuredNo())) {
				tLCAppntDB.setContNo(tLCContSchema.getContNo());
				if (!tLCAppntDB.getInfo()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorCDDetailBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保单投保人信息失败！保单号："
							+ tLPEdorItemSchema.getContNo();
					this.mErrors.addOneError(tError);
					return false;
				}
				LPAppntSchema tLPAppntSchema = new LPAppntSchema();
				this.mReflections.transFields(tLPAppntSchema, tLCAppntDB
						.getSchema());
				tLPAppntSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType(tLPEdorItemSchema.getEdorType());
				tLPAppntSchema.setAddressNo(newAddressNo);
				tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
				tLPAppntSchema.setModifyDate(strCurrentDate);
				tLPAppntSchema.setModifyTime(strCurrentTime);
				tLPAppntSet.add(tLPAppntSchema);
			}
			// else
			// {
			// 被保人-------------------->
			LCInsuredSet tempLCInsuredSet = new LCInsuredSet();
			tLCInsuredDB.setContNo(tLCContSchema.getContNo());
			tempLCInsuredSet = tLCInsuredDB.query();
			if (tempLCInsuredSet == null || tempLCInsuredSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCDDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保单被保人信息失败！保单号："
						+ tLPEdorItemSchema.getContNo();
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int ii = 1; ii <= tempLCInsuredSet.size(); ii++) {
				if (tempLCInsuredSet.get(ii).getInsuredNo() != null
						&& tempLCInsuredSet.get(ii).getInsuredNo().equals(
								mLPEdorItemSchema.getInsuredNo())) {
					LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
					this.mReflections.transFields(tLPInsuredSchema,
							tempLCInsuredSet.get(ii));
					tLPInsuredSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPInsuredSchema.setEdorType(tLPEdorItemSchema
							.getEdorType());
					tLPInsuredSchema.setAddressNo(newAddressNo);
					tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
					tLPInsuredSchema.setModifyDate(strCurrentDate);
					tLPInsuredSchema.setModifyTime(strCurrentTime);
					tLPInsuredSet.add(tLPInsuredSchema);
				}
			}
			// 修改“个险保全项目表”相应信息
			tLPEdorItemSchema.setEdorState("3");
			tLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			tLPEdorItemSchema.setModifyDate(strCurrentDate);
			tLPEdorItemSchema.setModifyTime(strCurrentTime);
			tLPEdorItemSet.add(tLPEdorItemSchema);

			// 复制保单数据到P表，为人工核保
			tLPContSchema = new LPContSchema();
			mReflections.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPContSet.add(tLPContSchema);
			// 复制保单险种数据到P表，为人工核保
			tLCPolDB.setContNo(tLCContSchema.getContNo());
			tContLCPolSet = new LCPolSet();
			tContLCPolSet = tLCPolDB.query();
			if (tContLCPolSet != null && tContLCPolSet.size() > 0) {
				for (int m = 1; m <= tContLCPolSet.size(); m++) {
					tLPPolSchema = new LPPolSchema();
					mReflections
							.transFields(tLPPolSchema, tContLCPolSet.get(m));
					tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPPolSet.add(tLPPolSchema);
				}
			}
			// 复制保单险种责任数据到P表，为人工核保
			tLCDutyDB.setContNo(tLCContSchema.getContNo());
			tContLCDutySet = new LCDutySet();
			tContLCDutySet = tLCDutyDB.query();
			if (tContLCDutySet != null && tContLCDutySet.size() > 0) {
				for (int m = 1; m <= tContLCDutySet.size(); m++) {
					tLPDutySchema = new LPDutySchema();
					mReflections.transFields(tLPDutySchema, tContLCDutySet
							.get(m));
					tLPDutySchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPDutySet.add(tLPDutySchema);
				}
			}
			// 复制保单险种给付责任数据到P表，为人工核保
			tLCPremDB.setContNo(tLCContSchema.getContNo());
			tContLCPremSet = new LCPremSet();
			tContLCPremSet = tLCPremDB.query();
			if (tContLCPremSet != null && tContLCPremSet.size() > 0) {
				for (int m = 1; m <= tContLCPremSet.size(); m++) {
					tLPPremSchema = new LPPremSchema();
					mReflections.transFields(tLPPremSchema, tContLCPremSet
							.get(m));
					tLPPremSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPPremSet.add(tLPPremSchema);
				}
			}
			// 复制保单险种领取责任数据到P表，为人工核保
			tLCGetDB.setContNo(tLCContSchema.getContNo());
			tContLCGetSet = new LCGetSet();
			tContLCGetSet = tLCGetDB.query();
			if (tContLCGetSet != null && tContLCGetSet.size() > 0) {
				for (int m = 1; m <= tContLCGetSet.size(); m++) {
					tLPGetSchema = new LPGetSchema();
					mReflections
							.transFields(tLPGetSchema, tContLCGetSet.get(m));
					tLPGetSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPGetSet.add(tLPGetSchema);
				}
			}
		}

		mMap.put(tLPAppntSet, "DELETE&INSERT");
		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPEdorItemSet, "UPDATE");
		mMap.put(tLPContSet, "DELETE&INSERT");
		mMap.put(tLPPolSet, "DELETE&INSERT");
		mMap.put(tLPDutySet, "DELETE&INSERT");
		mMap.put(tLPPremSet, "DELETE&INSERT");
		mMap.put(tLPGetSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);

		if (!deleteLastTimeData()) {
			return false;
		}

		return true;
	}

	/**
	 * 删除上次保存的记录
	 */
	private boolean deleteLastTimeData() {
		// 删除上次的记录
		try {
			PubSubmit tSubmit = new PubSubmit();
			VData tVDR = new VData();
			MMap tMMap = new MMap();
			String tSqla = "DELETE FROM LPAppnt WHERE LPAppnt.EdorType='CD' and exists(select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPAppnt.EdorNo)";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSqla);
			sbv1.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			tMMap.put(sbv1, "DELETE");
			String tSqlb = "DELETE FROM LPInsured WHERE LPInsured.EdorType='CD' and exists(select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPInsured.EdorNo)";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSqlb);
			sbv2.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			tMMap.put(sbv2, "DELETE");
			String tSqlc = "DELETE FROM LPAddress WHERE LPAddress.EdorType='CD' and exists(select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPAddress.EdorNo)";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tSqlc);
			sbv3.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			tMMap.put(sbv3, "DELETE");
			tVDR.add(tMMap);
			if (!tSubmit.submitData(tVDR, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorCDDetailBL";
				tError.functionName = "deleteLastTimeData";
				tError.errorMessage = "删除上次保存的信息时产生错误，请务必重新保存！";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "deleteLastTimeData";
			tError.errorMessage = "删除上次保存的信息时产生错误，请务必重新保存！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 获得被选中的EdorNo。这个EdorNo是在第一次保存的时候确定的，如果存在，则在LPAddress和LPEdorItem中都存在
	 * 如果没查到，即第一次保存，则从LPEdorItem表里选一条 结果存在mLuckyEdorNo
	 */
	private boolean getLuckyEdorNo() {
		try {
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "SELECT EdorNo"
					+ " FROM LPEdorItem a"
					+ " WHERE EdorAcceptNo='?EdorAcceptNo?'"
					+ " and EdorType='CD'"
					+ " and exists(select 'x' from LPAddress where EdorType='CD'"
					+ " and CustomerNo='?CustomerNo?'" + " and EdorNo=a.EdorNo)";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			sbv1.put("CustomerNo", mLPEdorItemSchema.getInsuredNo());
			tSSRS = tExeSQL.execSQL(sbv1);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				this.mLuckyEdorNo = tSSRS.GetText(1, 1);
				return true;
			}
			tSql = "SELECT EdorNo" + " FROM LPEdorItem"
					+ " WHERE EdorAcceptNo='?EdorAcceptNo?'"
					+ " and EdorType='CD'" + " ORDER BY EdorNo";
			sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv1);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				this.mLuckyEdorNo = tSSRS.GetText(1, 1);
				return true;
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCDDetailBL";
				tError.functionName = "getLuckyEdorNo";
				tError.errorMessage = "获得保全个人客户地址表中使用的批单号时产生错误！";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "getLuckyEdorNo";
			tError.errorMessage = "获得当前保全个人客户地址表中使用的批单号时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 获得新的地址号。这里不再考虑P表了，就比较四个关键字段，如果与C表里的一条相同就修改这 一条，如果都不同则增加地址号。
	 * 结果存在mNewAddressNo里
	 */
	private boolean getNewAddressNo() {
		try {
			String tSql = "SELECT AddressNo" + " FROM LCAddress"
					+ " WHERE CustomerNo='?CustomerNo?'"
					+ " and Province='?Province?'" + " and City='?City?'" + " and County='?County?'" + " and PostalAddress='?PostalAddress?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("CustomerNo", this.mLPAddressSchema.getCustomerNo());
			sqlbv.put("Province", this.mLPAddressSchema.getProvince());
			sqlbv.put("City", this.mLPAddressSchema.getCity());
			sqlbv.put("County", this.mLPAddressSchema.getCounty());
			sqlbv.put("PostalAddress", this.mLPAddressSchema.getPostalAddress());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				this.mMakeNewAddressNoFlag = false;
				this.mNewAddressNo = StrTool.cTrim(tSSRS.GetText(1, 1));
				return true;
			}
			tSql = "SELECT (case when max(AddressNo) is not null then max(AddressNo) else 0 end)+1" + " FROM LCAddress"
					+ " WHERE CustomerNo='?CustomerNo?'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("CustomerNo", this.mLPAddressSchema.getCustomerNo());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				this.mMakeNewAddressNoFlag = true;
				this.mNewAddressNo = StrTool.cTrim(tSSRS.GetText(1, 1));
				return true;
			}
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "getNewAddressNo";
			tError.errorMessage = "未获得当前可操作地址号！";
			this.mErrors.addOneError(tError);
			return false;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "getNewAddressNo";
			tError.errorMessage = "获得当前可操作地址号时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

}
