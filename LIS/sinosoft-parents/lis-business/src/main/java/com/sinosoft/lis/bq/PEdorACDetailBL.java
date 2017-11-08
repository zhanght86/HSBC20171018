package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPersonBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vdb.LPAppntDBSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单投保人信息变更
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @ReWrite Nicholas
 * @version 1.0
 */
public class PEdorACDetailBL {
private static Logger logger = Logger.getLogger(PEdorACDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();
	private LPAppntSet mLPAppntSet = new LPAppntSet();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	// private LPAddressSet mLPAddressSet = new LPAddressSet();
	// private LPPersonSet mLPPersonSet = new LPPersonSet();
	// private LPContSet mLPContSet = new LPContSet();
	// private LPPolSet mLPPolSet = new LPPolSet();
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorACDetailBL() {
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
	 * 数据查询业务处理(queryData())
	 */
	// private boolean queryData()
	// {
	// if (mOperate.equals("QUERY||MAIN"))
	// {
	// LPAppntBL tLPAppntBL = new LPAppntBL();
	// LPAppntSet tLPAppntSet = new LPAppntSet();
	// tLPAppntBL.setSchema(mLPAppntSchema);
	//
	// tLPAppntSet = tLPAppntBL.queryLPAppnt(mLPEdorItemSchema);
	//
	// String tReturn;
	// tReturn = tLPAppntSet.encode();
	// tReturn = "0|" + tLPAppntSet.size() + "^" + tReturn;
	//
	// mResult.clear();
	// mResult.add(tReturn);
	// }
	// else if (mOperate.equals("QUERY||DETAIL"))
	// {
	// LPAppntSchema tLPAppntSchema = new LPAppntSchema();
	// LPAppntBL tLPAppntBL = new LPAppntBL();
	// tLPAppntBL.setSchema(mLPAppntSchema);
	//
	// if (!tLPAppntBL.queryLPAppnt(mLPAppntSchema))
	// {
	// return false;
	// }
	//
	// tLPAppntSchema = tLPAppntBL.getSchema();
	//
	// String tReturn = tLPAppntSchema.encode();
	// mResult.clear();
	// mResult.add(tReturn);
	// }
	// else
	// {
	// }
	//
	// return true;
	// }
	// /**
	// * 数据操作类业务处理
	// * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	// */
	// private boolean insertData()
	// {
	// Connection conn = DBConnPool.getConnection();
	//
	// try
	// {
	// conn.setAutoCommit(false);
	//
	// LPAppntDBSet tLPAppntDBSet = new LPAppntDBSet(conn);
	// tLPAppntDBSet.set(mLPAppntSet);
	//
	// //数据提交
	// if (!tLPAppntDBSet.insert())
	// {
	// // @@错误处理
	// this.mErrors.copyAllErrors(tLPAppntDBSet.mErrors);
	//
	// CError tError = new CError();
	// tError.moduleName = "tLPAppntDB";
	// tError.functionName = "insertData";
	// tError.errorMessage = "数据提交失败!";
	// this.mErrors.addOneError(tError);
	//
	// conn.rollback();
	// conn.close();
	//
	// return false;
	// }
	//
	// conn.commit();
	// conn.close();
	// }
	// catch (Exception ex)
	// {
	// try
	// {
	// conn.rollback();
	// conn.close();
	// }
	// catch (Exception e)
	// {
	// }
	// }
	//
	// return true;
	// }
	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPAppntSchema = (LPAppntSchema) mInputData.getObjectByObjectName(
					"LPAppntSchema", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
					.getObjectByObjectName("LPAddressSchema", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPInsuredSet = (LPInsuredSet) mInputData.getObjectByObjectName(
					"LPInsuredSet", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLPAppntSchema == null || mLPAddressSchema == null
					|| mLPEdorItemSchema == null) {
				return false;
			}

			// 获得mLPEdorItemSchema的其它信息
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				logger.debug("---Error:在个险保全项目表中无匹配记录！---");
			}
			this.mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	/**
	 * 查询符合条件的暂交费信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean updateData() {
		Connection conn = DBConnPool.getConnection();

		try {
			conn.setAutoCommit(false);

			LPAppntDBSet tLPAppntDBSet = new LPAppntDBSet(conn);
			tLPAppntDBSet.set(mLPAppntSet);

			// 数据提交
			if (!tLPAppntDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLPAppntDBSet.mErrors);

				CError tError = new CError();
				tError.moduleName = "tLPAppntDB";
				tError.functionName = "insertData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				conn.rollback();
				conn.close();

				return false;
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);

		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorACDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据！";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorACDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		String tSql = "";
		SSRS tSSRS = null;
		ExeSQL tExeSQL = new ExeSQL();
		String strNewAddressNo = "";
		// 当前投保人是否同为被保人标记，默认为false
		boolean appntIsInsuredFlag = false;

		// ====del===zhangtao====2006-06-05=======目前AC界面不提供要件信息变更录入，所以不需要校验，以后需要校验也是添加校验规则实现，不用写到程序里====BGN====
		// 先查看该投保人是否同时为被保人
		// ====Ins===PST====2007-12-05=======目前AC界面提供要件信息变更录入，所以不需要校验，以后需要校验也是添加校验规则实现，不用写到程序里====BGN====
		tSql = "SELECT (case"
				+ " when exists(select 'X' from LCInsured where ContNo='"
				+ "?ContNo?" + "' and InsuredNo='"
				+ "?InsuredNo?" + "') then '1'" + " else '0'"
				+ " end)" + " FROM dual";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", mLPAppntSchema.getContNo());
		sqlbv.put("InsuredNo", mLPAppntSchema.getAppntNo());
		tSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		this.mErrors.copyAllErrors(tExeSQL.mErrors);
		if (tSSRS.GetText(1, 1) != null
				&& tSSRS.GetText(1, 1).trim().equals("1")) {
			appntIsInsuredFlag = true;
			// 执行到这里证明是该投保人同时为被保人，再比较要件信息判断当前是否是变更投保人要件信息
			tSql = "SELECT (case"
					+ " when exists(select 'X' from LCAppnt where "
					+ " ContNo = '"
					+ "?ContNo?"
					+ "' and trim(AppntName) = '"
					+ "?AppntName?"
					+ "' and trim(AppntSex) = '"
					+ "?AppntSex?"
					+ "' and AppntBirthday = to_date('"
					+ "?AppntBirthday?"
					+ "','yyyy-mm-dd') and trim(IDType) = '"
					+ "?IDType?"
					+ "' and trim(IDNo) = '"
					+ "?IDNo?"
					+ "') then '0'" + " else '1'" + " end)" + " FROM dual";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", mLPAppntSchema.getContNo().trim());
			sqlbv.put("AppntName", StrTool
					.GBKToUnicode(mLPAppntSchema.getAppntName().trim()));
			sqlbv.put("AppntSex", mLPAppntSchema.getAppntSex().trim());
			sqlbv.put("AppntBirthday", mLPAppntSchema.getAppntBirthday().trim());
			sqlbv.put("IDType", mLPAppntSchema.getIDType().trim());
			sqlbv.put("IDNo", StrTool.GBKToUnicode(mLPAppntSchema.getIDNo().trim()));
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			if (tSSRS.GetText(1, 1) != null
					&& tSSRS.GetText(1, 1).trim().equals("1")) {
				// 执行到这里证明是该投保人同时为被保人，并且当前是要件变更，这是不允许的，返回提示
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorACDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "当前投保人同时为被保人，要变更其要件信息请申请被保人要件变更！";
				this.mErrors.addOneError(tError);
				logger.debug("当前投保人同时为被保人，不能做要件变更！");
				return false;
			}
		}
		// ====Ins===PST====2007-12-05=======目前AC界面提供要件信息变更录入，所以不需要校验，以后需要校验也是添加校验规则实现，不用写到程序里====END====

		// 准备要更新的LPAddress表的最新信息，这组信息要先准备，因为地址号有可能新增加。
		// 如果新增了，要把新的地址号更新到相应的表中。
		LPAddressBL tLPAddressBL = new LPAddressBL();
		// 先获得当前可用的地址号
		String newAddressNo = tLPAddressBL.getNewAddressNo(mLPAddressSchema);
		// 获得当前对应地址信息
		if (!tLPAddressBL.queryLPAddress(this.mLPAddressSchema)) {
			return false;
		}
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		tLPAddressSchema = tLPAddressBL.getSchema();
		// 更新保全相关信息
		tLPAddressSchema.setAddressNo(newAddressNo);
		tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
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
		mMap.put(tLPAddressSchema, "DELETE&INSERT");

		// 准备要更新的LPAppnt表的最新信息
		LPAppntBL tLPAppntBL = new LPAppntBL();
		if (!tLPAppntBL.queryLastLPAppnt(this.mLPEdorItemSchema,
				this.mLPAppntSchema)) {
			return false;
		}
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema = tLPAppntBL.getSchema();
		// 更新保全相关信息
		tLPAppntSchema.setAddressNo(newAddressNo);
		tLPAppntSchema.setAppntNo(this.mLPAppntSchema.getAppntNo());
		// ///////// changed by pst on 2007-11-07/////////////
		tLPAppntSchema.setAppntName(mLPAppntSchema.getAppntName());
		tLPAppntSchema.setAppntSex(mLPAppntSchema.getAppntSex());
		tLPAppntSchema.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPAppntSchema.setIDType(mLPAppntSchema.getIDType());
		tLPAppntSchema.setIDNo(mLPAppntSchema.getIDNo());
		// ///////////end changed /////////////
		// ///-------------更新职业级别--------------------------------------------\
		// tSql = "SELECT OccupationType FROM LDOccupation WHERE
		// OccupationCode='"
		// + mLPAppntSchema.getOccupationCode() + "'";
		// logger.debug("-----------" + tSql);
		// logger.debug("-----------" + tExeSQL);
		// String tOccupationType = tExeSQL.getOneValue(tSql);
		// if (tOccupationType != null && !"".equals(tOccupationType))
		// {
		// tLPAppntSchema.setOccupationType(tOccupationType);
		// }
		// \-------------------------------------------------------------------/
		tLPAppntSchema.setBankCode(mLPAppntSchema.getBankCode());
		tLPAppntSchema.setBankAccNo(mLPAppntSchema.getBankAccNo());
		tLPAppntSchema.setAccName(mLPAppntSchema.getAccName());
		// 要件

		tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
		tLPAppntSchema.setModifyDate(strCurrentDate);
		tLPAppntSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPAppntSchema, "DELETE&INSERT");

		// 准备要更新的LPPerson表的最新信息
		LPPersonBL tLPPersonBL = new LPPersonBL();
		LPPersonSchema gLPPersonSchema = new LPPersonSchema();
		gLPPersonSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		gLPPersonSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		gLPPersonSchema.setCustomerNo(this.mLPAppntSchema.getAppntNo());
		if (!tLPPersonBL.queryLastLPPerson(this.mLPEdorItemSchema,
				gLPPersonSchema)) {
			return false;
		}
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonSchema.setSchema(tLPPersonBL.getSchema());
		// 更新保全相关信息
		// tLPPersonSchema.setMarriage(mLPAppntSchema.getMarriage());
		// tLPPersonSchema.setNativePlace(mLPAppntSchema.getNativePlace());
		// tLPPersonSchema.setLicenseType(mLPAppntSchema.getLicenseType());
		// tLPPersonSchema.setOccupationCode(mLPAppntSchema.getOccupationCode());
		// ///-------------更新职业级别--------------------------------------------\
		// if (tOccupationType != null && !"".equals(tOccupationType))
		// {
		// tLPPersonSchema.setOccupationType(tOccupationType);
		// }
		// \-------------------------------------------------------------------/
		tLPPersonSchema.setGrpName(mLPAddressSchema.getGrpName());
		// 要件
		tLPPersonSchema.setName(mLPAppntSchema.getAppntName());
		tLPPersonSchema.setSex(mLPAppntSchema.getAppntSex());
		tLPPersonSchema.setBirthday(mLPAppntSchema.getAppntBirthday());
		tLPPersonSchema.setIDType(mLPAppntSchema.getIDType());
		tLPPersonSchema.setIDNo(mLPAppntSchema.getIDNo());

		tLPPersonSchema.setOperator(this.mGlobalInput.Operator);
		tLPPersonSchema.setModifyDate(strCurrentDate);
		tLPPersonSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPPersonSchema, "DELETE&INSERT");

		// 准备要更新的LPAccount表的最新信息，如果传入的帐户名与投保人姓名相同，则在LPAccount表中“DELETE&INSERT”一条记录
		if (!StrTool.cTrim(this.mLPAppntSchema.getBankAccNo()).equals("")
				&& !StrTool.cTrim(this.mLPAppntSchema.getAccName()).equals("")
				&& !StrTool.cTrim(this.mLPAppntSchema.getBankCode()).equals("")
				&& (StrTool.cTrim(this.mLPAppntSchema.getAppntName())
						.equals(StrTool.cTrim(this.mLPAppntSchema.getAccName())))) {
			LPAccountSchema tLPAccountSchema = new LPAccountSchema();
			tLPAccountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAccountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAccountSchema.setCustomerNo(mLPAppntSchema.getAppntNo());
			tLPAccountSchema.setAccKind("N");
			tLPAccountSchema.setBankCode(mLPAppntSchema.getBankCode());
			tLPAccountSchema.setBankAccNo(mLPAppntSchema.getBankAccNo());
			tLPAccountSchema.setAccName(mLPAppntSchema.getAccName());
			tLPAccountSchema.setOperator(this.mGlobalInput.Operator);
			tLPAccountSchema.setModifyDate(strCurrentDate);
			tLPAccountSchema.setModifyTime(strCurrentTime);
			// 查询入机日期和时间，这两项不会变，所以可直接从C表里查
			tSql = "SELECT (case"
					+ " when max(MakeDate) is null then to_char(now(),'YYYY-MM-DD')"
					+ " else to_char(max(MakeDate),'YYYY-MM-DD')"
					+ " end),"
					+ " (case"
					+ " when max(MakeTime) is null then to_char(now(),'hh24:mi:ss')"
					+ " else max(MakeTime)" + " end)" + " FROM LCAccount"
					+ " WHERE CustomerNo='" + "?CustomerNo?"
					+ "' and BankCode='" + "?BankCode?"
					+ "' and BankAccNo='" + "?BankAccNo?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("CustomerNo", mLPAppntSchema.getAppntNo());
			sqlbv.put("BankCode", mLPAppntSchema.getBankCode());
			sqlbv.put("BankAccNo", mLPAppntSchema.getBankAccNo());
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			if (tSSRS == null) {
				tLPAccountSchema.setMakeDate(strCurrentDate);
				tLPAccountSchema.setMakeTime(strCurrentTime);
			} else {
				tLPAccountSchema.setMakeDate(tSSRS.GetText(1, 1));
				tLPAccountSchema.setMakeTime(tSSRS.GetText(1, 2));
			}
			mMap.put(tLPAccountSchema, "DELETE&INSERT");
		}

		// 准备要更新的LPCont表的最新信息（主要是银行帐户信息）
		LPContBL tLPContBL = new LPContBL();
		LPContSchema gLPContSchema = new LPContSchema();
		gLPContSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		gLPContSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		gLPContSchema.setContNo(this.mLPEdorItemSchema.getContNo());
		if (!tLPContBL.queryLastLPCont(this.mLPEdorItemSchema, gLPContSchema)) {
			return false;
		}
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema.setSchema(tLPContBL.getSchema());
		// 更新保全相关信息
		tLPContSchema.setBankCode(mLPAppntSchema.getBankCode());
		tLPContSchema.setBankAccNo(mLPAppntSchema.getBankAccNo());
		tLPContSchema.setAccName(mLPAppntSchema.getAccName());

		if (tLPContSchema.getBankCode() != null
				&& !tLPContSchema.getBankCode().equals("")
				&& tLPContSchema.getBankAccNo() != null
				&& !tLPContSchema.getBankAccNo().equals("")
				&& tLPContSchema.getAccName() != null
				&& !tLPContSchema.getAccName().equals("")) {
			// XinYQ modified on 2007-05-10 : 只有当录入信息不为空且与原信息不同时才更新续期缴费形式
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLPContSchema.getContNo());
			LCContSet tLCContSet = new LCContSet();
			try {
				tLCContSet = tLCContDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询新契约合同表获取原银行账户信息出现异常！");
				return false;
			}
			if (tLCContSet == null || tLCContSet.size() <= 0) {
				CError.buildErr(this, "在查询新契约合同表中找不到待操作的保单信息！");
				return false;
			} else {
				LCContSchema tLCContSchema = new LCContSchema();
				tLCContSchema = tLCContSet.get(1);
				if (!tLPContSchema.getBankCode().equals(
						tLCContSchema.getBankCode())
						|| !tLPContSchema.getBankAccNo().equals(
								tLCContSchema.getBankAccNo())
						|| !tLPContSchema.getAccName().equals(
								tLCContSchema.getAccName())) {
					tLPContSchema.setPayLocation("0");
				}
				tLCContSchema = null;
			}
			tLCContDB = null;
			tLCContSet = null;
		} else {
			// 没有填写，默认续期缴费形式为现金
			tLPContSchema.setBankCode("");
			tLPContSchema.setBankAccNo("");
			tLPContSchema.setAccName("");
			tLPContSchema.setPayLocation("2");
		}

		// 要件
		tLPContSchema.setAppntName(mLPAppntSchema.getAppntName());
		tLPContSchema.setAppntSex(mLPAppntSchema.getAppntSex());
		tLPContSchema.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPContSchema.setAppntIDType(mLPAppntSchema.getIDType());
		tLPContSchema.setAppntIDNo(mLPAppntSchema.getIDNo());

		// tLPContSchema.setOperator(this.mGlobalInput.Operator);
		tLPContSchema.setModifyDate(strCurrentDate);
		tLPContSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPContSchema, "DELETE&INSERT");

		// 准备要更新的LPInsured表的最新信息，如果该投保人同时是被保人，则同时将被保人表更新
		// if (appntIsInsuredFlag)
		// {
		// //准备要更新的LPInsured表的最新信息
		// LPInsuredBL tLPInsuredBL = new LPInsuredBL();
		// LPInsuredSchema gLPInsuredSchema = new LPInsuredSchema();
		// gLPInsuredSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		// gLPInsuredSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		// gLPInsuredSchema.setContNo(this.mLPEdorItemSchema.getContNo());
		// gLPInsuredSchema.setInsuredNo(this.mLPAppntSchema.getAppntNo());
		// if
		// (!tLPInsuredBL.queryLastLPInsured(this.mLPEdorItemSchema,gLPInsuredSchema))
		// {
		// return false;
		// }
		// LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		// tLPInsuredSchema.setSchema(tLPInsuredBL.getSchema());
		// //更新保全相关信息
		// tLPInsuredSchema.setAddressNo(newAddressNo);
		// //tLPInsuredSchema.setMarriage(mLPAppntSchema.getMarriage());
		// //tLPInsuredSchema.setNativePlace(mLPAppntSchema.getNativePlace());
		// //tLPInsuredSchema.setLicenseType(mLPAppntSchema.getLicenseType());
		// //tLPInsuredSchema.setOccupationCode(mLPAppntSchema.getOccupationCode());
		// // tLPInsuredSchema.setBankCode(mLPAppntSchema.getBankCode());
		// // tLPInsuredSchema.setBankAccNo(mLPAppntSchema.getBankAccNo());
		// // tLPInsuredSchema.setAccName(mLPAppntSchema.getAccName());
		// tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
		// tLPInsuredSchema.setModifyDate(strCurrentDate);
		// tLPInsuredSchema.setModifyTime(strCurrentTime);
		// mMap.put(tLPInsuredSchema,"DELETE&INSERT");
		// }

		// 准备要更新的LPInsured表的最新信息，只是更新关系
		if (mLPInsuredSet != null && mLPInsuredSet.size() > 0) {
			LPInsuredBL tLPInsuredBL = new LPInsuredBL();
			LPInsuredSchema tLPInsuredSchema = null;
			LPInsuredSchema sLPInsuredSchema = null;
			LPInsuredSet tLPInsuredSet = new LPInsuredSet();
			for (int j = 1; j <= mLPInsuredSet.size(); j++) {
				tLPInsuredSchema = new LPInsuredSchema();
				tLPInsuredSchema = mLPInsuredSet.get(j);
				sLPInsuredSchema = mLPInsuredSet.get(j);
				if (!tLPInsuredBL.queryLastLPInsured(this.mLPEdorItemSchema,
						tLPInsuredSchema)) {
					return false;
				}
				LPInsuredSchema oLPInsuredSchema = new LPInsuredSchema();
				oLPInsuredSchema.setSchema(tLPInsuredBL.getSchema());
				// 更新保全相关信息
				// 准备要更新的LPInsured表的最新信息，如果该投保人同时是被保人，则同时将被保人表更新
				if (appntIsInsuredFlag) {
					// 如果当前被保人就是投保人，则一并更新其它信息
					oLPInsuredSchema.setAddressNo(newAddressNo);
					oLPInsuredSchema.setMarriage(mLPAppntSchema.getMarriage());
					oLPInsuredSchema.setNativePlace(mLPAppntSchema
							.getNativePlace());
					oLPInsuredSchema.setLicenseType(mLPAppntSchema
							.getLicenseType());
					oLPInsuredSchema.setOccupationCode(mLPAppntSchema
							.getOccupationCode());
					oLPInsuredSchema.setBankCode(mLPAppntSchema.getBankCode());
					oLPInsuredSchema
							.setBankAccNo(mLPAppntSchema.getBankAccNo());
					oLPInsuredSchema.setAccName(mLPAppntSchema.getAccName());
				}
				oLPInsuredSchema.setRelationToAppnt(sLPInsuredSchema
						.getRelationToAppnt());
				oLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
				oLPInsuredSchema.setModifyDate(strCurrentDate);
				oLPInsuredSchema.setModifyTime(strCurrentTime);
				tLPInsuredSet.add(oLPInsuredSchema);
			}
			mMap.put(tLPInsuredSet, "DELETE&INSERT");
		}

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mResult.add(mBqCalBase);

		return true;
	}
}
