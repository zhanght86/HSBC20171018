package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPayRuleFactoryDB;
import com.sinosoft.lis.db.LCPayRuleParamsDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPayRuleParamsSchema;
import com.sinosoft.lis.schema.LMCalFactorSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.vschema.LCPayRuleParamsSet;
import com.sinosoft.lis.vschema.LMCalFactorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;

/*
 * <p>Title: 保费计算类 </p>
 * <p>Description: 通过传入的保单信息和责任信息构建出保费信息和领取信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft</p>
 * @author
 * @version 1.0
 * @date 2006-05-18
 */
public class PayRuleFee {
private static Logger logger = Logger.getLogger(PayRuleFee.class);
	public static void main(String[] args) {
		PayRuleFee tC = new PayRuleFee();
		LMCalFactorSchema tLMCalFactorSchema = new LMCalFactorSchema();
		tC.addBasicFactor("ServiceYear", "0");
		tC.addBasicFactor("tInsured", "0000000010");
		tC.addBasicFactor("tContNo", "240110000000001");
		tC.setCalCode("jf0001");
		// logger.debug(tC.calculateEx("A","240110000000001"));
		if (tC.mErrors.needDealError()) {
			logger.debug(tC.mErrors.getFirstError());
		}

	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 计算需要用到的保单号码 */
	public String PolNo;
	public String mGrpContNo; // 团体保单号
	public String mPayRuleCode; // 缴费规则类型

	/**
	 * 各种要素存放的琏表 1--基本要素、和常量要素相同，但是优先级最低 2--扩展要素，根据SQL语句从新计算 3--常量要素（只取默认值）
	 */
	private LMCalFactorSet mCalFactors1 = new LMCalFactorSet(); // 存放基本要素

	public LMCalFactorSet mCalFactors = new LMCalFactorSet();

	// @Field
	// 计算编码
	private String CalCode = "";
	// 算法对应SQL语句所在表结构
	private LMCalModeSchema mLMCalMode = new LMCalModeSchema();
	private LCPayRuleFactorySchema mLCPayRuleFactorySchema = new LCPayRuleFactorySchema();

	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();

	/**
	 * 增加基本要素
	 * 
	 * @param cFactorCode
	 *            要素的编码
	 * @param cFactorValue
	 *            要素的数据值
	 */
	public void addBasicFactor(String cFactorCode, String cFactorValue) {
		LMCalFactorSchema tS = new LMCalFactorSchema();
		tS.setFactorCode(cFactorCode);
		tS.setFactorDefault(cFactorValue);
		tS.setFactorType("1");
		mCalFactors1.add(tS);
		// logger.debug("the factorcode is ++"+cFactorCode);
		// logger.debug("the factorValue is ++"+cFactorValue);
	}

	public void testmCalFactors1() {
		int mySize = mCalFactors1.size();
		for (int i = 1; i <= mySize; i++) {
			LMCalFactorSchema tS = new LMCalFactorSchema();
			tS = mCalFactors1.get(i);
			String myCode = tS.getFactorCode();
			String myValue = tS.getFactorDefault();
			// logger.debug("myCode is myCode"+myCode);
			// logger.debug("myValue is myValue"+myValue);
		}
		// logger.debug("mySize is "+mySize);

	}

	// @Method
	public void setCalCode(String tCalCode) {
		CalCode = tCalCode;
	}

	/**
	 * 公式计算函数
	 * 
	 * @return: String 计算的结果，只能是单值的数据（数字型的转换成字符型）
	 * @author: YT
	 */
	public String calculate() {

		logger.debug("start calculate++++++++++++++");
		if (!checkCalculate()) {
			return "0";
		}
		// 取得数据库中计算要素
		LMCalFactorSet tLMCalFactorSet = mCRI
				.findCalFactorByCalCodeClone(CalCode);
		if (tLMCalFactorSet == null) {
			return "0";
		}

		mCalFactors.add(tLMCalFactorSet);
		logger.debug(tLMCalFactorSet.size());
		// 增加基本要素
		mCalFactors.add(mCalFactors1);
		logger.debug(mCalFactors1.size());
		LMCalFactorSchema tLMCalFactorSchema = new LMCalFactorSchema();
		for (int i = 1; i <= mCalFactors.size(); i++) {
			tLMCalFactorSchema = mCalFactors.get(i);
			String tCode = tLMCalFactorSchema.getFactorCode();
			String tValue = tLMCalFactorSchema.getFactorDefault();
			// logger.debug("the tCode is"+tCode);
			// logger.debug("the tValue is "+tValue);
		}
		// logger.debug("the mCalFacotrsl
		// is"+mCalFactors1.get(1).getFactorDefault());
		// 解释计算要素
		if (!interpretFactors()) {
			return "0";
		}

		// 读取SQL语句
		if (!getSQL()) {
			return "0";
		}

		// 解释SQL语句中的变量
		if (!interpretFactorInSQL()) {
			return "0";
		}
		// 执行SQL语句
		logger.debug("start execute SQL.....");
		return executeSQL();
	}

	public String calculateEx(String strPayRuleCode, String strGrpContNo,
			String tFactoryName, String tFactoryValue, String tOtherNo,
			String tInerSerialNo) {
		logger.debug("start calculateEx++++++++++++++");
		if (!checkCalculate()) {
			logger.debug("not here ");
			return "1";
		}
		// 取得数据库中计算要素
		LMCalFactorSet tLMCalFactorSet = mCRI
				.findCalFactorByCalCodeClone(CalCode);
		logger.debug("calCode :" + CalCode);
		logger.debug(tLMCalFactorSet.size());
		// logger.debug("strPayRuleCode :"+strPayRuleCode );
		// logger.debug("strGrpContNo:"+strGrpContNo);
		String tGrpContNo = strGrpContNo;
		String tPayRuleCode = strPayRuleCode;

		if (tLMCalFactorSet == null) {
			logger.debug("tLCPayRuleFactorySet is null");
			return "0";
		}
		// 增加基本要素
		mCalFactors.add(mCalFactors1);
		mCalFactors.add(tLMCalFactorSet);
		// 解释计算要素
		if (!interpretFactorsEx()) {
			logger.debug("the interpretFactors is not right");
			return "2";
		}
		logger.debug("the interpretFactors is right");

		// 读取SQL语句
		if (!getSQLEx(tGrpContNo, tPayRuleCode, tFactoryName, tFactoryValue,
				tOtherNo, tInerSerialNo)) {
			return "0";
		}

		// 解释SQL语句中的变量
		if (!interpretFactorInSQLEx()) {
			return "0";
		}
		// 执行SQL语句
		logger.debug("start execute SQL.....");
		return executeSQL();
	}

	/**
	 * 执行SQL语句
	 * 
	 * @return
	 */
	private String executeSQL() {
		String tReturn = "0";
		ExeSQL tExeSQL = new ExeSQL();
		tReturn = tExeSQL.getOneValue(mLMCalMode.getCalSQL());
		logger.debug("the tReturn is" + tReturn);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "executeSQL";
			tError.errorMessage = "执行SQL语句：" + mLMCalMode.getCalCode() + "失败!";
			this.mErrors.addOneError(tError);
			return "0";
		}
		return tReturn;
	}

	/**
	 * 解释SQL语句中的变量
	 * 
	 * @return
	 */
	private boolean interpretFactorInSQL() {
		String tSql, tStr = "", tStr1 = "";
		tSql = mLMCalMode.getCalSQL();
		// logger.debug("the Sql is+++"+tSql);
		// tSql=tSql.toLowerCase() ;
		try {
			while (true) {
				tStr = PubFun.getStr(tSql, 2, "?");
				if (tStr.equals("")) {
					break;
				}
				tStr1 = "?" + tStr.trim() + "?";
				// 替换变量
				tSql = StrTool.replaceEx(tSql, tStr1, getValueByName(tStr));
				// tSql=tSql.replace(tStr,getValueByName(tStr));
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "interpretFactorInSQL";
			tError.errorMessage = "解释" + tSql + "的变量:" + tStr + "时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLMCalMode.setCalSQL(tSql);
		logger.debug("lmcalmdoe.calsql" + mLMCalMode.getCalSQL());
		return true;
	}

	private boolean interpretFactorInSQLEx() {
		String tSql, tStr = "", tStr1 = "";
		tSql = mLCPayRuleFactorySchema.getCalSql();
		logger.debug("---tSql:" + tSql);
		// tSql=tSql.toLowerCase() ;
		try {
			while (true) {
				tStr = PubFun.getStr(tSql, 2, "?");
				logger.debug("tStr:" + tStr);
				if (tStr.equals("")) {
					break;
				}
				tStr1 = "?" + tStr.trim() + "?";
				logger.debug("tStr1:" + tStr1);
				// 替换变量
				tSql = StrTool.replaceEx(tSql, tStr1, getValueByName(tStr));
				// tSql=tSql.replace(tStr,getValueByName(tStr));
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "interpretFactorInSQL";
			tError.errorMessage = "解释" + tSql + "的变量:" + tStr + "时出错。";
			this.mErrors.addOneError(tError);
			logger.debug(tError.errorMessage);
			return false;
		}
		mLMCalMode.setCalSQL(tSql);
		logger.debug("tsql:" + tSql);
		return true;
	}

	/**
	 * 计算子要素的值
	 * 
	 * @return
	 */
	private String calSubFactors(LMCalFactorSchema cF) {
		logger.debug("start calSubFactors---");
		int i, iMax;
		String tReturn = "";
		LMCalFactorSchema tC = new LMCalFactorSchema();
		PayRuleFee tNewC = new PayRuleFee();
		iMax = mCalFactors.size();
		logger.debug("iMax is****" + iMax);
		for (i = 1; i <= iMax; i++) {
			tC = mCalFactors.get(i);
			String tCode = tC.getFactorCode();
			String tValue = tC.getFactorDefault();
			String tType = tC.getFactorType();
			// logger.debug("the tCode is "+tCode);
			// logger.debug("the tValue is"+tValue);
			// logger.debug("the tType is "+tType);
			// 如果是基本要素或常量要素，则传入下一个要素中
			if (tC.getFactorType().toUpperCase().equals("1")
					|| tC.getFactorType().toUpperCase().equals("3")) {
				tNewC.mCalFactors.add(tC);
			}
		}
		tNewC.setCalCode(cF.getFactorCalCode());
		logger.debug("----SubFactor---calcode = " + cF.getFactorCalCode());
		logger.debug(tNewC.mCalFactors.size());
		tReturn = String.valueOf(tNewC.calculate());
		logger.debug("----SubFactor = " + tReturn);
		// 如果有错误，则将错误拷贝到上一要素,并且返回"0"
		if (tNewC.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tNewC.mErrors);
			tReturn = "0";
		}
		return tReturn;
	}

	/**
	 * 读取SQL语句
	 * 
	 * @return
	 */
	private boolean getSQL() {
		CachedRiskInfo cri = CachedRiskInfo.getInstance();
		LMCalModeSchema tLMCalModeSchema = cri.findCalModeByCalCode(CalCode);

		if (tLMCalModeSchema == null) {
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "getSql";
			tError.errorMessage = "得到" + CalCode + "的SQL语句时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLMCalMode.setSchema(tLMCalModeSchema);

		return true;
	}

	private boolean getSQLEx(String tGrpContNo, String tPayRuleCode,
			String tFactoryName, String tFactoryValue, String tOtherNo,
			String tInerSerialNo) {
		CachedRiskInfo cri = CachedRiskInfo.getInstance();
		// 根据缴费规则要素表确定使用哪一条缴费规则
		// ......
		// LCPayRuleFactorySet tLCPayRuleFactorySet =
		// cri.findCalSqlClone(CalCode,tGrpContNo,tPayRuleCode);
		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();

		LCPayRuleParamsSet tLCPayRuleParamsSet = new LCPayRuleParamsSet();
		LCPayRuleParamsSchema tLCPayRuleParamsSchema = new LCPayRuleParamsSchema();
		LCPayRuleParamsDB tLCPayRuleParamsDB = new LCPayRuleParamsDB();
		tLCPayRuleParamsDB.setGrpContNo(tGrpContNo);
		tLCPayRuleParamsDB.setPayRuleCode(tPayRuleCode);
		tLCPayRuleParamsDB.setFactoryName(tFactoryName);
		tLCPayRuleParamsDB.setOtherNo(tOtherNo);
		tLCPayRuleParamsDB.setInerSerialNo(tInerSerialNo);
		tLCPayRuleParamsDB.setParamName("a");
		tLCPayRuleParamsSet = tLCPayRuleParamsDB.query();

		for (int i = 1; i <= tLCPayRuleParamsSet.size(); i++) {
			LCPayRuleParamsDB pLCPayRuleParamsDB = new LCPayRuleParamsDB();
			LCPayRuleParamsSet pLCPayRuleParamsSet = new LCPayRuleParamsSet();
			tLCPayRuleParamsSchema = tLCPayRuleParamsSet.get(i);
			// logger.debug("tFactoryValue:"+tFactoryValue+"
			// param:"+tLCPayRuleParamsSchema.getParam());
			if (Float.parseFloat(tFactoryValue) >= Float
					.parseFloat(tLCPayRuleParamsSchema.getParam())) {
				pLCPayRuleParamsDB.setGrpPolNo(tLCPayRuleParamsSchema
						.getGrpPolNo());
				pLCPayRuleParamsDB.setPayRuleCode(tLCPayRuleParamsSchema
						.getPayRuleCode());
				pLCPayRuleParamsDB.setFactoryType(tLCPayRuleParamsSchema
						.getFactoryType());
				pLCPayRuleParamsDB.setOtherNo(tLCPayRuleParamsSchema
						.getOtherNo());
				pLCPayRuleParamsDB.setFactoryCode(tLCPayRuleParamsSchema
						.getFactoryCode());
				pLCPayRuleParamsDB.setFactorySubCode(tLCPayRuleParamsSchema
						.getFactorySubCode());
				pLCPayRuleParamsDB.setInerSerialNo(tLCPayRuleParamsSchema
						.getInerSerialNo());
				pLCPayRuleParamsDB.setParamName("b");
				pLCPayRuleParamsSet = pLCPayRuleParamsDB.query();
				LCPayRuleParamsSchema pLCPayRuleParamsSchema = new LCPayRuleParamsSchema();
				if (true) {
					pLCPayRuleParamsSchema = pLCPayRuleParamsSet.get(1);
					// logger.debug("---size:"+pLCPayRuleParamsSet.size());
					// logger.debug("----tFactoryValue:"+tFactoryValue+"
					// param:"+pLCPayRuleParamsSchema.getParam());
					if (Float.parseFloat(tFactoryValue) < Float
							.parseFloat(pLCPayRuleParamsSchema.getParam())) {
						tLCPayRuleFactorySchema
								.setGrpContNo(pLCPayRuleParamsSchema
										.getGrpContNo());
						tLCPayRuleFactorySchema
								.setGrpPolNo(pLCPayRuleParamsSchema
										.getGrpPolNo());
						tLCPayRuleFactorySchema
								.setRiskCode(pLCPayRuleParamsSchema
										.getRiskCode());
						tLCPayRuleFactorySchema
								.setPayRuleCode(pLCPayRuleParamsSchema
										.getPayRuleCode());
						tLCPayRuleFactorySchema
								.setFactoryType(pLCPayRuleParamsSchema
										.getFactoryType());
						tLCPayRuleFactorySchema
								.setOtherNo(pLCPayRuleParamsSchema.getOtherNo());
						tLCPayRuleFactorySchema
								.setFactoryCode(pLCPayRuleParamsSchema
										.getFactoryCode());
						tLCPayRuleFactorySchema
								.setFactorySubCode(pLCPayRuleParamsSchema
										.getFactorySubCode());
						tLCPayRuleFactorySchema
								.setInerSerialNo(pLCPayRuleParamsSchema
										.getInerSerialNo());
						break;
					}
				}
			}
		}
		// logger.debug(tLCPayRuleFactorySchema.encode());
		// if( tLCPayRuleFactorySchema == null ) {
		// CError tError =new CError();
		// tError.moduleName="Calculator";
		// tError.functionName="getSql";
		// tError.errorMessage="得到"+CalCode+"的SQL语句时出错。";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		if (!("".equals(tLCPayRuleFactorySchema.getGrpPolNo())
				|| tLCPayRuleFactorySchema.getGrpPolNo() == null
				|| "".equals(tLCPayRuleFactorySchema.getPayRuleCode()) || tLCPayRuleFactorySchema
				.getPayRuleCode() == null)) {
			LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
			tLCPayRuleFactoryDB.setSchema(tLCPayRuleFactorySchema);
			if (tLCPayRuleFactoryDB.getInfo()) {
				mLCPayRuleFactorySchema.setSchema(tLCPayRuleFactoryDB
						.getSchema());
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 解释要素连表中的非变量要素
	 * 
	 * @return
	 */
	private boolean interpretFactors() {
		int i, iMax;
		LMCalFactorSchema tC = new LMCalFactorSchema();
		iMax = mCalFactors.size();
		for (i = 1; i <= iMax; i++) {
			tC = mCalFactors.get(i);
			// 如果是扩展要素，则解释该扩展要素
			if (tC.getFactorType().toUpperCase().equals("2")) {
				tC.setFactorDefault(calSubFactors(tC));
				// 如果在计算子要素的时候发生错误，则返回false
				if (this.mErrors.needDealError()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean interpretFactorsEx() {
		logger.debug("start interpretFactorEx----");
		int i, iMax;
		LMCalFactorSchema tC = new LMCalFactorSchema();
		iMax = mCalFactors.size();
		logger.debug("iMax is" + iMax + "^^^^^^");
		for (i = 0; i < iMax; i++) {
			tC = mCalFactors.get(i + 1);
			// 如果是扩展要素，则解释该扩展要素
			if (tC.getFactorType().toUpperCase().equals("2")) {
				tC.setFactorDefault(calSubFactors(tC));
				// 如果在计算子要素的时候发生错误，则返回false
				if (this.mErrors.needDealError()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 校验计算的输入是否足够
	 * 
	 * @return boolean 如果不正确返回false
	 */
	private boolean checkCalculate() {
		if (CalCode == null || CalCode.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "checkCalculate";
			tError.errorMessage = "计算时必须有计算编码。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据变量名得到变量的值
	 * 
	 * @return String 如果不正确返回"",否则返回变量值
	 */
	private String getValueByName(String cVarName) {
		cVarName = cVarName.toLowerCase();
		// logger.debug("cVarName++"+cVarName);
		int i, iMax;
		String tReturn = "";
		LMCalFactorSchema tC = new LMCalFactorSchema();
		iMax = mCalFactors.size();
		for (i = 1; i <= iMax; i++) {
			tC = mCalFactors.get(i);
			String tCode = tC.getFactorCalCode();
			String tValue = tC.getFactorDefault();
			// logger.debug("the tCode is +"+tCode);
			// logger.debug("the tValue is"+tValue);
			if (tC.getFactorCode().toLowerCase().equals(cVarName)) {
				tReturn = tC.getFactorDefault();
				break;
			}
		}
		return tReturn;
	}

	/**
	 * Kevin 2003-08-20 得到解析过的SQL语句，而不是SQL语句执行后的值。
	 * 
	 * @return
	 */
	public String getCalSQL() {
		if (!checkCalculate()) {
			return "0";
		}

		LMCalFactorSet tLMCalFactorSet = mCRI
				.findCalFactorByCalCodeClone(CalCode);

		if (tLMCalFactorSet == null) {
			return "0";
		}

		mCalFactors.add(tLMCalFactorSet);
		// 增加基本要素
		mCalFactors.add(mCalFactors1);
		// 解释计算要素
		if (!interpretFactors()) {
			return "0";
		}

		// 读取SQL语句
		if (!getSQL()) {
			return "0";
		}

		// 解释SQL语句中的变量
		if (!interpretFactorInSQL()) {
			return "0";
		}

		// 返回解析过的SQL语句
		return mLMCalMode.getCalSQL();
	}
}
