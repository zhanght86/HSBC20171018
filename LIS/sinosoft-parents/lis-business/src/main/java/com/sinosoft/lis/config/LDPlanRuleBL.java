package com.sinosoft.lis.config;

import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LMFactoryModeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDPlanRuleSchema;
import com.sinosoft.lis.schema.LMFactoryModeSchema;
import com.sinosoft.lis.vschema.LDPlanRuleSet;
import com.sinosoft.lis.vschema.LMFactoryModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class LDPlanRuleBL {
	private static Logger logger = Logger.getLogger(LDPlanRuleBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	private StringBuffer mQueryResult = new StringBuffer(256);
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private Hashtable m_hashInfo = new Hashtable(); // 保存每个规则的最大编号

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LDPlanRuleSchema mLDPlanRuleSchema = new LDPlanRuleSchema();
	private LDPlanRuleSet mLDPlanRuleSet = new LDPlanRuleSet();
	private LDPlanRuleSet mcLDPlanRuleSet = new LDPlanRuleSet();

	/** 数据操作字符串 */
	private String mOperate;

	private String mFactoryType = "";
	private String mFactoryCode = "";
	private String mFactorySubCode = "";

	private String mContPlanCode = "";

	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值
	String mCurrentTime = PubFun.getCurrentTime();

	public LDPlanRuleBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("产品组合->底层处理程序：LDPlanRuleBL－>submitData:Begin");
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = cInputData;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			return false;
		}

		if (this.mOperate.equals("QUERY||PLANRULE")) {
			return true;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start PubSubmit Submit...");
		PubSubmit tPubSubmit = new PubSubmit();
		// 如果有需要处理的错误，则返回
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		logger.debug("End PubSubmit Submit...");
		logger.debug("产品组合->底层处理程序：LDPlanRuleBL－>submitData:End");

		return true;
	}

	private boolean getInputData() {
		mcLDPlanRuleSet = (LDPlanRuleSet) mInputData.getObjectByObjectName(
				"LDPlanRuleSet", 0);
		if (mcLDPlanRuleSet == null || mcLDPlanRuleSet.size() == 0) {
			buildError("getInputData", "没有传入投保规则数据!");
			return false;
		}

		mContPlanCode = mcLDPlanRuleSet.get(1).getContPlanCode();

		this.mGlobalInput.setSchema((GlobalInput) mInputData
				.getObjectByObjectName("GlobalInput", 0));

		return true;
	}

	private boolean checkData() {
		if (!this.mOperate.equals("QUERY||PLANRULE")) {
			String tSQL = "select state from ldplan where contplancode='"
					+ "?mContPlanCode?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("mContPlanCode", mContPlanCode);
			ExeSQL tExeSQL = new ExeSQL();
			String tResult = tExeSQL.getOneValue(sqlbv1);
			if (tResult != null && "9".equals(tResult)) {
				buildError("checkData", "产品组合已经进行确认，不能再做操作!");
				return false;
			}

			if (tResult == null || "".equals(tResult)) {
				buildError("checkData", "产品组合没有定义，请先定义产品组合再进行该操作!");
				return false;
			}

		}
		return true;
	}

	private boolean dealData() {
		// 循环要素计算Sql集合，准备要素计算Sql中的计算子要素信息
		for (int i = 1; i <= mcLDPlanRuleSet.size(); i++) {
			LDPlanRuleSchema tLDPlanRuleSchema = new LDPlanRuleSchema();
			tLDPlanRuleSchema = mcLDPlanRuleSet.get(i);
			if (this.mOperate.equals("QUERY||PLANRULE")) {
				if (queryPlanRule(tLDPlanRuleSchema.getSchema()) == false) {
					return false;
				}
			}

			if (this.mOperate.equals("INSERT||PLANRULE")) {
				if (insertData(tLDPlanRuleSchema.getSchema()) == false) {
					return false;
				}
			}

			if (this.mOperate.equals("UPDATE||PLANRULE")) {
				if (!deleteData(tLDPlanRuleSchema.getSchema())) {
					return false;
				}
				if (insertData(tLDPlanRuleSchema.getSchema()) == false) {
					return false;
				}
			}

			if (this.mOperate.equals("DELETE||PLANRULE")) {
				if (!deleteData(tLDPlanRuleSchema.getSchema())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 逐条准备计算要素信息
	 * 
	 * @param tLCFactorySchema
	 *            LCFactorySchema
	 * @param tIndex
	 *            int
	 * @return boolean 如果发生错误则返回false,否则返回true
	 */
	private boolean insertData(LDPlanRuleSchema tLDPlanRuleSchema) {
		logger.debug("产品组合->底层处理程序：LDPlanRuleBL－>insertData:Begin");
		int tRuleCode = 0;
		String tContPlanCode = tLDPlanRuleSchema.getContPlanCode();

		if (tLDPlanRuleSchema.getParam() == null
				|| "".equals(tLDPlanRuleSchema.getParam())) {
			buildError("PrepareNewData",
					"计算编码为：" + tLDPlanRuleSchema.getFactoryCode() + "没有输入要素值!");
			return false;

		}

		// 基本录入信息校验
		mFactoryType = tLDPlanRuleSchema.getFactoryType(); // 要素类别
		mFactoryCode = tLDPlanRuleSchema.getFactoryCode(); // 要素计算编码
		mFactorySubCode = tLDPlanRuleSchema.getFactorySubCode(); // 要素计算编码小号
		if ("INSERT||PLANRULE".equals(this.mOperate)) {
			tRuleCode = this.getMaxRuleCode(mFactoryCode);
			if (tRuleCode == -1) {
				String sql1 = "select max(rulecode) from ldplanrule where contplancode = '"
						+ "?tContPlanCode?"
						+ "' and factoryCode='"
						+ "?mFactoryCode?" + "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(sql1);
				sqlbv4.put("tContPlanCode", tContPlanCode);
				sqlbv4.put("mFactoryCode", mFactoryCode);
				ExeSQL tExeSQL = new ExeSQL();
				String tMaxRuleCode = tExeSQL.getOneValue(sqlbv4);
				logger.debug("产品组合->底层处理程序：LDPlanRuleBL－>insertData:tMaxRuleCode="
						+ tMaxRuleCode);
				if (tMaxRuleCode == null || "".equals(tMaxRuleCode)) {
					tRuleCode = 0;
				} else {
					tRuleCode = Integer.parseInt(tMaxRuleCode);
				}
				logger.debug("产品组合->底层处理程序：LDPlanRuleBL－>insertData:tRuleCode="
						+ tRuleCode);
			}
			tRuleCode = tRuleCode + 1;
		} else {
			tRuleCode = Integer.parseInt(tLDPlanRuleSchema.getRuleCode());
		}
		// 取要素描述信息
		LMFactoryModeDB tLMFactoryModeDB = new LMFactoryModeDB();
		LMFactoryModeSet tLMFactoryModeSet = new LMFactoryModeSet();

		tLMFactoryModeDB.setRiskCode("000000");
		tLMFactoryModeDB.setFactoryType(mFactoryType);
		tLMFactoryModeDB.setFactoryCode(mFactoryCode);
		tLMFactoryModeDB.setFactorySubCode(mFactorySubCode);
		tLMFactoryModeSet = tLMFactoryModeDB.query();
		// 如果集合为空，或者集合数不唯一，则数据异常
		if (tLMFactoryModeSet == null || tLMFactoryModeSet.size() != 1) {
			// @@错误处理
			buildError("PrepareNewData",
					"取计算编码为：" + tLDPlanRuleSchema.getFactoryCode() + "的计算类型为："
							+ tLDPlanRuleSchema.getFactoryType() + "的计算Sql失败!");
			return false;
		}
		LMFactoryModeSchema tLMFactoryModeSchema = new LMFactoryModeSchema();
		// 取查询出的要素信息
		tLMFactoryModeSchema = tLMFactoryModeSet.get(1);
		if (tLMFactoryModeSchema.getParams() == null
				|| "".equals(tLMFactoryModeSchema.getParams())) {
			buildError("PrepareNewData",
					"取计算编码为：" + tLDPlanRuleSchema.getFactoryCode()
							+ "的参数描述失败(表LMFacotryMode)!");
			return false;
		}

		String[] tParams = null;
		String[] tRelParams = null;
		// 根据，拆分字符串，返回数组
		tParams = PubFun.split(tLMFactoryModeSchema.getParams(), ",");
		tRelParams = PubFun.split(tLDPlanRuleSchema.getParam(), ",");
		// 如果数组不为空，表示参数有多个
		if (tRelParams != null && tParams != null) {
			if (tRelParams.length != tParams.length) {
				this.buildError("InserData", "输入的参数个数与需要的参数个数不一致!");
				return false;
			}
			for (int i = 0; i < tRelParams.length; i++) {
				mLDPlanRuleSchema = new LDPlanRuleSchema();
				mLDPlanRuleSchema.setContPlanCode(tContPlanCode);
				mLDPlanRuleSchema.setFactoryCode(mFactoryCode);
				mLDPlanRuleSchema.setFactoryName(tLDPlanRuleSchema
						.getFactoryName());
				mLDPlanRuleSchema.setFactorySubCode(mFactorySubCode);
				mLDPlanRuleSchema.setFactoryType(mFactoryType);
				// 准备计算要素参数信息数据
				mLDPlanRuleSchema.setRuleCode(Integer.toString(tRuleCode));
				mLDPlanRuleSchema.setParamName(tParams[i]);
				mLDPlanRuleSchema.setParam(tRelParams[i]);
				mLDPlanRuleSchema.setOperator(mGlobalInput.Operator);
				mLDPlanRuleSchema.setMakeDate(mCurrentDate); // 当前值
				mLDPlanRuleSchema.setMakeTime(mCurrentTime);
				mLDPlanRuleSchema.setModifyDate(mCurrentDate); // 当前值
				mLDPlanRuleSchema.setModifyTime(mCurrentTime);
				mLDPlanRuleSet.add(mLDPlanRuleSchema);
			}
		} else {
			mLDPlanRuleSchema = new LDPlanRuleSchema();
			mLDPlanRuleSchema.setContPlanCode(tContPlanCode);
			mLDPlanRuleSchema.setFactoryCode(mFactoryCode);
			mLDPlanRuleSchema
					.setFactoryName(tLDPlanRuleSchema.getFactoryName());
			mLDPlanRuleSchema.setFactorySubCode(mFactorySubCode);
			mLDPlanRuleSchema.setFactoryType(mFactoryType);
			mLDPlanRuleSchema.setRuleCode(Integer.toString(tRuleCode));
			mLDPlanRuleSchema.setOperator(mGlobalInput.Operator);
			mLDPlanRuleSchema.setParamName(tLMFactoryModeSchema.getParams());
			mLDPlanRuleSchema.setParam(tLDPlanRuleSchema.getParam());
			mLDPlanRuleSchema.setMakeDate(mCurrentDate); // 当前值
			mLDPlanRuleSchema.setMakeTime(mCurrentTime);
			mLDPlanRuleSchema.setModifyDate(mCurrentDate); // 当前值
			mLDPlanRuleSchema.setModifyTime(mCurrentTime);
			mLDPlanRuleSet.add(mLDPlanRuleSchema);
		}
		this.setMaxRuleCode(mFactoryCode, tRuleCode);
		logger.debug("产品组合->底层处理程序：LDPlanRuleBL－>insertData:End");
		return true;
	}

	/**
	 * 逐条准备计算要素信息
	 * 
	 * @param tLCFactorySchema
	 *            LCFactorySchema
	 * @param tIndex
	 *            int
	 * @return boolean 如果发生错误则返回false,否则返回true
	 */
	private boolean deleteData(LDPlanRuleSchema tLDPlanRuleSchema) {
		logger.debug("产品组合->底层处理程序->deleteData beg");
		String tSQL = "delete from ldplanrule where contplancode='"
				+ tLDPlanRuleSchema.getContPlanCode() + "' and factorycode='"
				+ tLDPlanRuleSchema.getFactoryCode() + "' and rulecode='"
				+ tLDPlanRuleSchema.getRuleCode() + "'";
		mMap.put(tSQL, "DELETE");
		logger.debug("产品组合->底层处理程序->deleteData end");
		return true;
	}

	private boolean queryPlanRule(LDPlanRuleSchema cLDPlanRuleSchema) {

		// LDPlanRuleSchema tLDPlanRuleSchema = new LDPlanRuleSchema();
		String tContPlanCode = cLDPlanRuleSchema.getContPlanCode();
		String tSQL = "select distinct a.contplancode,a.factorycode,a.rulecode,a.factorytype,"
				+ "b.params,b.factoryname,b.calremark,a.factorysubcode "
				+ "from ldplanrule a,lmfactorymode b"
				+ " where b.factorytype = a.factorytype and b.factorycode=a.factorycode "
				+ "and a.contplancode='" + "?tContPlanCode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("tContPlanCode", tContPlanCode);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			return true;
		}
		int tMaxRow = tSSRS.getMaxRow();
		for (int i = 1; i <= tMaxRow; i++) {
			SSRS tRuleSSRS = new SSRS();
			String tParams = tSSRS.GetText(i, 5);
			tSQL = "select paramname,param from ldplanrule where contplancode='"
					+ "?tContPlanCode?"
					+ "' and rulecode='"
					+ "?rulecode?"
					+ "' and factorycode='"
					+ "?factorycode?"
					+ "' and factorytype='" + "?factorytype?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL);
			sqlbv3.put("tContPlanCode", tContPlanCode);
			sqlbv3.put("rulecode", tSSRS.GetText(i, 3));
			sqlbv3.put("factorycode", tSSRS.GetText(i, 2));
			sqlbv3.put("factorytype", tSSRS.GetText(i, 4));
			tRuleSSRS = tExeSQL.execSQL(sqlbv3);
			if (tRuleSSRS == null || tRuleSSRS.getMaxRow() <= 0) {
				continue;
			}
			for (int j = 1; j <= tRuleSSRS.getMaxRow(); j++) {
				String tParamName = tRuleSSRS.GetText(j, 1);
				String tParam = tRuleSSRS.GetText(j, 2);
				tParams = tParams.replaceAll(tParamName, tParam);
			}

			mQueryResult.append(tSSRS.GetText(i, 2) + SysConst.PACKAGESPILTER
					+ tSSRS.GetText(i, 6) + SysConst.PACKAGESPILTER
					+ tSSRS.GetText(i, 7) + SysConst.PACKAGESPILTER + tParams
					+ SysConst.PACKAGESPILTER + tSSRS.GetText(i, 4)
					+ SysConst.PACKAGESPILTER + tSSRS.GetText(i, 8)
					+ SysConst.PACKAGESPILTER + tSSRS.GetText(i, 3));

			mQueryResult.append(SysConst.RECORDSPLITER);
		}

		mQueryResult.insert(0, "0|" + String.valueOf(tMaxRow)
				+ SysConst.RECORDSPLITER);
		mQueryResult.delete(mQueryResult.length() - 1, mQueryResult.length());
		return true;
	}

	private boolean prepareOutputData() {
		this.mInputData.clear();
		if (mLDPlanRuleSet != null && mLDPlanRuleSet.size() > 0) {
			mMap.put(mLDPlanRuleSet, "INSERT");
		}
		this.mInputData.add(mMap);
		mResult.add(mLDPlanRuleSet);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public String getQueryResult() {
		return mQueryResult.toString();
	}

	private void setMaxRuleCode(String cFactorCode, int cRuleCode) {
		m_hashInfo.put(cFactorCode, String.valueOf(cRuleCode));
	}

	private int getMaxRuleCode(String cFactorCode) {
		String str = (String) m_hashInfo.get(cFactorCode);
		if (str != null && !str.equals("")) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}

	/**
	 * 错误处理
	 * 
	 * @param cFunc
	 *            String 出错函数名称
	 * @param cErrMsg
	 *            String 出错信息
	 */
	private void buildError(String cFunc, String cErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LDPlanBL";
		cError.functionName = cFunc;
		cError.errorMessage = cErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		LDPlanRuleBL ldplanrulebl = new LDPlanRuleBL();
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.ManageCom = "86";
		tG.Operator = "001";
		LDPlanRuleSchema tLDPlanRuleSchema = new LDPlanRuleSchema();
		LDPlanRuleSet tLDPlanRuleSet = new LDPlanRuleSet();

		tLDPlanRuleSchema.setContPlanCode("86000007");
		tLDPlanRuleSchema.setFactoryType("000009");

		tLDPlanRuleSchema.setFactoryCode("zh0001");
		tLDPlanRuleSchema.setFactorySubCode("1");
		tLDPlanRuleSchema.setParam("13,16,1,1");
		tLDPlanRuleSchema.setFactoryName("年龄与投保份数的限制 ");

		tLDPlanRuleSet.add(tLDPlanRuleSchema);

		tLDPlanRuleSchema = new LDPlanRuleSchema();
		tLDPlanRuleSchema.setContPlanCode("86000007");
		tLDPlanRuleSchema.setFactoryType("000009");

		tLDPlanRuleSchema.setFactoryCode("zh0001");
		tLDPlanRuleSchema.setFactorySubCode("1");
		tLDPlanRuleSchema.setParam("17,21,1,1");
		tLDPlanRuleSchema.setFactoryName("年龄与投保份数的限制 ");

		tLDPlanRuleSet.add(tLDPlanRuleSchema);

		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(tLDPlanRuleSet);

		if (!ldplanrulebl.submitData(tVData, "INSERT||PLANRULE")) {
			logger.debug(ldplanrulebl.mErrors.toString());
		}

	}
}
