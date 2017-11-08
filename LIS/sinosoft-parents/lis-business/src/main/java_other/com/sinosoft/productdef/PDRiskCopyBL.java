

package com.sinosoft.productdef;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Method;
import com.sinosoft.lis.db.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMRiskSortSchema;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.lis.schema.LRTemplateSchema;
import com.sinosoft.lis.schema.PD_LMCalFactorSchema;
import com.sinosoft.lis.schema.PD_LMCalModeSchema;
import com.sinosoft.lis.schema.PD_LMRiskSortSchema;
import com.sinosoft.lis.vschema.LRRuleDataSet;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.lis.vschema.PD_LMCalFactorSet;
import com.sinosoft.lis.vschema.PD_LMCalModeSet;

/**
 * 实现产品复制功能
 * 
 * @author NicolE
 * @see PDDeployBL.java
 */
public class PDRiskCopyBL implements BusinessService {

	public CErrors mErrors = new CErrors();

	private MMap map = new MMap();

	private VData mResult = new VData();

	private GlobalInput mGi = new GlobalInput();

	private String mSQLSepaChar = "@";

	private String mSQLParaName = "RISKCODE";

	// 存储新的主键名称和值
	private HashMap mPKVaueMap = new HashMap();
	// 存储原险种算法编码
	private Set<String> mCalCodeSet = new HashSet<String>();
	private Map<String, String> mCalCodeMap = new HashMap<String, String>();
	private boolean isPD_LMCalMode = false;
	// 已发布的被复制目标险种
	private String mRiskCode = "";

	private Hashtable mCalCodeHashtable = new Hashtable();
	private Hashtable mRuleKeyHashtable = new Hashtable();
	// 新定义的险种编码
	private String mNewRiskCode = "";
	private LRTemplateSet mLRTemplateSet = new LRTemplateSet();

	public PDRiskCopyBL() {
		mCalCodeHashtable.put("CALCODE", "CALCODE");
		mCalCodeHashtable.put("CNTERCALCODE", "CNTERCALCODE");
		mCalCodeHashtable.put("OTHCALCODE", "OTHCALCODE");
		mCalCodeHashtable.put("PCALCODE", "PCALCODE");
		mCalCodeHashtable.put("RCALPREMCODE", "RCALPREMCODE");
		mCalCodeHashtable.put("ADDFEECALCODE", "ADDFEECALCODE");
		mCalCodeHashtable.put("CLMFEECALCODE", "CLMFEECALCODE");
		// mCalCodeHashtable.put("ACCCANCELCODE", "ACCCANCELCODE");
		mCalCodeHashtable.put("FEECALCODE", "FACTORCALCODE");
		mCalCodeHashtable.put("FACTORCODE", "FACTORCODE");
		mCalCodeHashtable.put("FACTORCALCODE", "FACTORCALCODE");
	}

	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PDRiskCopyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * @author NicolE
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData) {

		mGi = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		TransferData data = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mNewRiskCode = (String) data.getValueByName("NewRiskCode");
		if (mNewRiskCode == null || mNewRiskCode.equals("")) {
			mErrors.addOneError("获取新定义险种编码信息失败！");
			return false;
		}
		mRiskCode = (String) data.getValueByName("DpyRiskCode");
		if (mRiskCode == null || mRiskCode.equals("")) {
			mErrors.addOneError("获取已上线险种编码信息失败！");
			return false;
		}
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(mRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.addOneError("获取已上线险种编码信息失败！");
			return false;
		}
		if (mGi == null) {
			mErrors.addOneError("获取用户登录信息失败！");
			return false;
		}
		return true;
	}

	/**
	 * @author NicolE 校验是否可以对产品进行复制
	 * @return
	 */
	private boolean checkInputData() {

		PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();
		tPD_LMRiskDB.setRiskCode(mNewRiskCode);
		if (tPD_LMRiskDB.getInfo()) {
			mErrors.addOneError("险种" + mNewRiskCode + "已存在定义信息，不能进行复制操作！");
			return false;
		}
		// 获取被复制产品险种编码
		getRiskCalCode();
		return true;
	}

	/**
	 * @author NicolE
	 * @desc 复制已上线产品信息内容
	 * @see PDDeployBL.java ,The method deploy()
	 * @return
	 */
	private boolean dealData() {

		// String key = "";
		// 查询险种相关表名及查询SQL
		SSRS tableNameAndSelect = getTableNameAndSelect();
		try {
			if (tableNameAndSelect != null) {

				// 开始循环各个表
				for (int tableIndex = 0; tableIndex < tableNameAndSelect.getMaxRow(); tableIndex++) {
					// deal one table
					String pdTableCode = tableNameAndSelect.GetText(tableIndex + 1, 1);
					String selectSQL = tableNameAndSelect.GetText(tableIndex + 1, 3);
					// 执行SQL，将查询结果写入Set
					SchemaSet pdSet = getProductData(pdTableCode, selectSQL);
					if("PD_LMRiskSort".equals(pdTableCode)){//PD_LMRiskSort 特殊处理
						copyLMRiskSortSchema(pdSet);
					} else if (pdSet != null && pdSet.size() > 0) {// 开始处理其中一张表中的若干条记录
						copySchema(pdSet);
					}// 一个表的所有记录内容复制完毕
				}// 所有的表复制完毕
				//-------- add by jucy
				//处理NB插入算法
				PD_LMCalModeSet copyPD_LMCalModeSet = new PD_LMCalModeSet();
				PD_LMCalModeDB copyPD_LMCalModeDB = new PD_LMCalModeDB(); 
				copyPD_LMCalModeDB.setRiskCode(mRiskCode);
				copyPD_LMCalModeSet = copyPD_LMCalModeDB.query();
				copyPD_LMCalModeSchema(copyPD_LMCalModeSet);
				
				//-------- end
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			mErrors.addOneError("复制产品信息失败，具体原因是：" + e.toString());
			return false;
		}
		return true;
	}
	//-------- add by jucy
	/**
	 * 复制schema (PD_LMCalMode)
	 * @param pdSet
	 */
	private void copyPD_LMCalModeSchema(SchemaSet pdSet) {
		int schemaCount = pdSet.size();
		for (int i = 0; i < schemaCount; i++) {
			Schema pdSchema = (Schema) pdSet.getObj(i + 1);
			PD_LMCalModeSchema tPD_LMCalModeSchema = (PD_LMCalModeSchema) pdSchema;
			//String aPD_LMCalModeType= tPD_LMCalModeSchema.getType();
			String oldPD_LMCalModeCalCode= tPD_LMCalModeSchema.getCalCode();
			System.out.println("oldPD_LMCalModeCalCode========"+oldPD_LMCalModeCalCode);
			String newPD_LMCalModeCalCode = "";
			//CV,MCV,SIV
			if (oldPD_LMCalModeCalCode.equals(mRiskCode+"CV")||oldPD_LMCalModeCalCode.equals(mRiskCode+"MCV")||oldPD_LMCalModeCalCode.equals(mRiskCode+"SIV")) {
				if (oldPD_LMCalModeCalCode.startsWith("RUCAL")|| oldPD_LMCalModeCalCode.startsWith("CAL")) {
					newPD_LMCalModeCalCode = oldPD_LMCalModeCalCode;
				}
				else if (!oldPD_LMCalModeCalCode.equals("")) {
					newPD_LMCalModeCalCode = getNewCalcode(oldPD_LMCalModeCalCode);
				}
				if (newPD_LMCalModeCalCode.startsWith("RU")){
					copyRuleEngine(mNewRiskCode,oldPD_LMCalModeCalCode,newPD_LMCalModeCalCode);
				}else{
					copyRuleSQL(mNewRiskCode,oldPD_LMCalModeCalCode,newPD_LMCalModeCalCode);
				}
			}
			
		}
	}
	//-------- end
	/**
	 * 复制schema (一般情况)
	 * 
	 * @param pdSet
	 */
	private void copySchema(SchemaSet pdSet) {
		int schemaCount = pdSet.size();
		// 循环处理每条记录
		for (int i = 0; i < schemaCount; i++) {
			Schema pdSchema = (Schema) pdSet.getObj(i + 1);
			if (pdSchema instanceof PD_LMCalModeSchema) {
				isPD_LMCalMode = true;
			} else {
				isPD_LMCalMode = false;
			}
			System.out.println("#####:" + pdSchema.getClass().getName());
			if (pdSchema.getClass().getName().indexOf("PD_LMRiskAccPay") != -1) {
				System.out.println("#####:" + pdSchema.getClass().getName());
			}
			// 判断是否需要对新险种写入该条
			if (filterData(pdSchema)) {
				continue;
			}
			for (int n = 0; n < pdSchema.getFieldCount(); n++) {
				String tFieldName = pdSchema.getFieldName(n);
				String tFieldValue = pdSchema.getV(n);
				prepareRiskPKInfo(tFieldName, tFieldValue);
				String tKey = tFieldName.toUpperCase() + tFieldValue;
				String tNewValue = tFieldValue;
				if (tNewValue != null && !tNewValue.trim().equals("")&& !tNewValue.trim().equals("null")) {
					if (mPKVaueMap.containsKey(tKey)) {
						tNewValue = (String) mPKVaueMap.get(tKey);
					}
				}
				System.out.println("tFieldName:" + tFieldName + ":tFieldValue:"+ tFieldValue + ":tNewValue:" + tNewValue);
				pdSchema.setV(tFieldName, tNewValue);
			}
			map.put(pdSchema, "DELETE&INSERT");
		} // 表内一条记录复制完毕
	}

	/**
	 * 复制schema (PD_LMRiskSort)
	 * LMRiskSort：如果RISKSORTTYPE为PF、ED，则RISKSORTVALUE为算法编码
	 * 
	 * @param pdSet
	 */
	private void copyLMRiskSortSchema(SchemaSet pdSet) {
		int schemaCount = pdSet.size();
		// 循环处理每条记录
		for (int i = 0; i < schemaCount; i++) {
			Schema pdSchema = (Schema) pdSet.getObj(i + 1);
			PD_LMRiskSortSchema tLMRiskSortSchema = (PD_LMRiskSortSchema) pdSchema;
			tLMRiskSortSchema.setRiskCode(this.mNewRiskCode);
			String oldRiskSortValue = tLMRiskSortSchema.getRiskSortValue();
			String aRiskSortValue = "";
			if (tLMRiskSortSchema.getRiskSortType().equals("PF")|| tLMRiskSortSchema.getRiskSortType().equals("ED")) {
				if (oldRiskSortValue.startsWith("RUCAL")|| oldRiskSortValue.startsWith("CAL")) {
					aRiskSortValue = oldRiskSortValue;

				}
				// else if(aRiskSortValue.startsWith("RU")){
				//					
				// aRiskSortValue="RU"+mNewRiskCode.substring(mNewRiskCode.length()-3)+aRiskSortValue.substring(aRiskSortValue.length()-3);
				//				
				// }
				else if (!oldRiskSortValue.equals("")) {
					// aRiskSortValue=mNewRiskCode.substring(mNewRiskCode.length()-3)+aRiskSortValue.substring(aRiskSortValue.length()-3);
					aRiskSortValue = getNewCalcode(oldRiskSortValue);
					if(null!=aRiskSortValue&&!"".equals(aRiskSortValue)){
						if (aRiskSortValue.startsWith("RU")){
							copyRuleEngine(mNewRiskCode,oldRiskSortValue,aRiskSortValue);
						}else{
							copyRuleSQL(mNewRiskCode,oldRiskSortValue,aRiskSortValue);
						}
					}
				}
			} else if (tLMRiskSortSchema.getRiskSortType().equals("00")) {
				aRiskSortValue = mNewRiskCode;
				tLMRiskSortSchema.setRiskSortValue(aRiskSortValue);
				//map.put(tLMRiskSortSchema, "DELETE&INSERT");
			}
			//modify by xhl start 20130605
			if(null!=aRiskSortValue && !"".equals(aRiskSortValue)){
				tLMRiskSortSchema.setRiskSortValue(aRiskSortValue);
			}	
//			tLMRiskSortSchema.setRiskSortValue(aRiskSortValue);
//			System.out.println(i+"---------------------------------");
//			System.out.println("RiskCode:"+tLMRiskSortSchema.getRiskCode());
//			System.out.println("RiskSortValue:"+tLMRiskSortSchema.getRiskSortValue());
//			System.out.println("RiskSortType:"+tLMRiskSortSchema.getRiskSortType());
			tLMRiskSortSchema.setMakeDate(PubFun.getCurrentDate());
			tLMRiskSortSchema.setMakeTime(PubFun.getCurrentTime());
			tLMRiskSortSchema.setModifyDate(PubFun.getCurrentDate());
			tLMRiskSortSchema.setModifyTime(PubFun.getCurrentTime());
			tLMRiskSortSchema.setOperator(mGi.Operator);
			System.out.println(mGi.Operator);
			map.put(tLMRiskSortSchema, "DELETE&INSERT");
		}
	}

	/**
	 *  @取 copy后的calcode 
	 * @param oldCalcode
	 * @return
	 */
	private String getNewCalcode(String oldCalcode) {
		String newCalcode = "";
		String sql = "select CopyToCalcodeSeq from PROCopyCode where calcode ='"
				+ oldCalcode
				+ "' and oldRiskcode='"
				+ mRiskCode
				+ "' and CopyToRiskCode='" + mNewRiskCode + "'";
		ExeSQL esq = new ExeSQL();
		SSRS ssrs = esq.execSQL(sql);
		if (ssrs == null || ssrs.getMaxRow() == 0) {
			if (oldCalcode != null && !oldCalcode.trim().equals("")
					&& !oldCalcode.trim().equals("null")) {
				if (oldCalcode.startsWith("RU")) {
					newCalcode = "RUCO" + PubFun1.CreateMaxNo("CalCodeCopy", 6);
				} else {
					newCalcode = "COPY" + PubFun1.CreateMaxNo("CalCodeCopy", 6);
				}
				PROCopyCodeDB proCodeDB = new PROCopyCodeDB();
				proCodeDB.setCalcode(oldCalcode);
				proCodeDB.setCopyToCalcodeSeq(newCalcode);
				proCodeDB.setOldRiskCode(mRiskCode);
				proCodeDB.setCopyToRiskCode(mNewRiskCode);
				proCodeDB.setMakeDate(PubFun.getCurrentDate());
				proCodeDB.setMakeTime(PubFun.getCurrentTime());
				proCodeDB.setModifyDate(PubFun.getCurrentDate());
				proCodeDB.setModifyTime(PubFun.getCurrentTime());
				proCodeDB.setOpereator(this.mGi.Operator);
				proCodeDB.setSerialno("PE"+PubFun1.CreateMaxNo("PE", 18));
				if (!proCodeDB.insert()) {
					return null;
				}
			} else {
				return "";
			}
		} else {
			newCalcode = ssrs.GetText(1, 1);
		}
		return newCalcode;
	}

	/**
	 * 获取产品定义所有相关表及【查询表内容的SQL】
	 * 
	 * @see PDDeployBL.java ,The method getTableNameAndSelect()
	 * @return
	 */
	private SSRS getTableNameAndSelect() {
		String selectSQL = "select distinct a.Tablecode, '', b.Standbyflag1 from Pd_Tablemap a "
				+ " inner join Pd_Basetable b on upper(a.Tablecode) = upper(b.Tablecode) "
				+ " and b.Standbyflag1 is not null and substr(a.standbyflag2,-1,1) = '1' "
				+ " and  a.Tablecode <>'PD_LMCalMode'"
				+ " and  a.Tablecode <>'InterfacePToSInfo'"
				+ " and  a.Tablecode <>'SUBRISKCODETOPLANCODEINFO'"
				+ " and  a.Tablecode <>'PD_LACommissionRate'"
				+ " and  a.Tablecode <>'PD_LDStaffRate'"
				+ " and  a.Tablecode <>'PD_ProceedsExpress'";

		SSRS ssrs = getSSRSResult(selectSQL, 3);
		return ssrs;
	}

	/**
	 * 将带参数的selectSQL处理为可执行的SQL
	 * 
	 * @see PDDeployBL.java
	 * @param selectSQL
	 * @param indexOfRiskCode
	 * @return
	 */
	private SSRS getSSRSResult(String selectSQL, int indexOfRiskCode) {

		SSRS ssrs = new SSRS();
		ExeSQL exec = new ExeSQL();
		ssrs = exec.execSQL(selectSQL);

		for (int i = 0; i < ssrs.getMaxRow(); i++) {
			String sql = ssrs.GetText(i + 1, indexOfRiskCode);

			String tParaStr, tStr1;
			while (true) {
				tParaStr = PubFun.getStr(sql, 2, mSQLSepaChar);
				if (tParaStr.equals("")) {
					break;
				} else if (tParaStr.equalsIgnoreCase(this.mSQLParaName)) {
					tStr1 = mSQLSepaChar + tParaStr.trim() + mSQLSepaChar;
					// 替换变量
					sql = StrTool.replaceEx(sql, tStr1, this.mRiskCode);
				} else {
					tStr1 = mSQLSepaChar + tParaStr.trim() + mSQLSepaChar;
					// 替换变量
					sql = StrTool.replaceEx(sql, tStr1, "无用");
				}
			}// while 循环结束

			System.out.println("第" + (i + 1) + "个可执行的查询SQL：" + sql);

			ssrs.setTextAt(i + 1, indexOfRiskCode, sql);
		}
		return ssrs;
	}

	/**
	 * @see PDDeployBL.java
	 * @param pdTableCode
	 * @param selectSQL
	 * @return
	 */
	private SchemaSet getProductData(String pdTableCode, String selectSQL) {

		try {
			// get data using pd schema
			if (selectSQL.equals("")) {
				return null;
			}
			Class pdSetClass = Class.forName("com.sinosoft.lis.vschema."
					+ pdTableCode + "Set");
			SchemaSet pdSet = (SchemaSet) pdSetClass.newInstance();

			Class pdDBClass = Class.forName("com.sinosoft.lis.db."
					+ pdTableCode + "DB");
			Schema pdDB = (Schema) pdDBClass.newInstance();

			Class[] paras = new Class[1];
			paras[0] = String.class;
			Object[] args = new Object[1];
			args[0] = selectSQL;

			Method m = pdDB.getClass().getMethod("executeQuery", paras);
			pdSet = (SchemaSet) m.invoke(pdDB, args);
			return pdSet;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @author NicolE
	 * @desc 准备产品所有表的主键信息
	 * @param tFieldName
	 *            组成规则为：字段名+原字段值
	 * @param tFieldValue
	 *            新字段值
	 * @return
	 */
	private void prepareRiskPKInfo(String tFieldName, String tFieldValue) {
		tFieldName = tFieldName.toUpperCase();
		String key = tFieldName + tFieldValue;
		if (mNewRiskCode.length() < 3) {
			System.out.println("险种号码小于3位，不能复制！");
			return;
		}
		if (mPKVaueMap.containsKey(key)) {
			return;
		}
		if (tFieldName.equals("RISKCODE")) {
			mPKVaueMap.put(key, mNewRiskCode);
		} else if (tFieldName.equals("RISKVER")|| tFieldName.equals("RISKVERSION")) {
			mPKVaueMap.put(key, tFieldValue);
		} else if (tFieldName.equals("UWCODE")
				|| tFieldName.equals("OTHERCODE")
				|| tFieldName.equals("DUTYCODE")
				|| tFieldName.equals("PAYPLANCODE")
				|| tFieldName.equals("GETDUTYCODE")
				// -------- delete by jucy
				// 复制时不会重新生成这两个编码
				|| tFieldName.equals("ACCOUNTCODE")
				// || tFieldName.equals("INSUACCNO")
				// -------- end
				|| tFieldName.equals("FEECODE")) {
			String tName = "";
			if (tFieldValue.trim().equals("000000")) {
				tName = tFieldValue;
			} else {
				tName = mNewRiskCode.substring(mNewRiskCode.length() - 3)
						+ tFieldValue.substring(tFieldValue.length() - 3);
			}
			mPKVaueMap.put(key, tName);
		} else if (tFieldName.equals("INSUACCNO")) {
			String tName = "";
			String AccTypeSql = "Select acctype From PD_LMRiskInsuAcc Where InsuAccNo='"
					+ tFieldValue + "'";
			ExeSQL exelm = new ExeSQL();
			SSRS AccTypeSqlRes = exelm.execSQL(AccTypeSql);
			int sResult = AccTypeSqlRes.getMaxRow();
			if (sResult != 0) {
				String AccType = AccTypeSqlRes.GetText(1, 1);
				if (AccType.equals("007")) {

				} else {
					tName = mNewRiskCode.substring(mNewRiskCode.length() - 3)
							+ tFieldValue.substring(tFieldValue.length() - 3);
					mPKVaueMap.put(key, tName);
				}
			}
		}
		// 替换算法编码
		else if (this.mCalCodeHashtable.containsKey(tFieldName)) {
			String tName = "";
			if (tFieldValue.startsWith("RUCAL")|| tFieldValue.startsWith("CAL")) {
				tName = tFieldValue;
				// }else if(tFieldValue.startsWith("RU")){
				// tName="RU"+mNewRiskCode.substring(mNewRiskCode.length()-3)+tFieldValue.substring(tFieldValue.length()-3);
			} else if (!tFieldValue.equals("")&&!tFieldValue.equals("null")) {
				// tName=mNewRiskCode.substring(mNewRiskCode.length()-3)+tFieldValue.substring(tFieldValue.length()-3);
				tName = getNewCalcode(tFieldValue);
				if (tFieldValue.startsWith("RU")){
					copyRuleEngine(mNewRiskCode,tFieldValue,tName);
				}else{
					copyRuleSQL(mNewRiskCode,tFieldValue,tName);
				}
			}
			mPKVaueMap.put(key, tName);
		} else {
			// 其他字段值不需重新编码，但须记录所有的编码，否则会主键冲突
			mPKVaueMap.put(key, tFieldValue);
		}
	}
	
	//-------- add by jucy
	private  void copyRuleSQL(String newRiskCode,String oldCalCode,String newCalCode) {
		// TODO Auto-generated method stub
		PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
		tPD_LMCalModeDB.setCalCode(oldCalCode);
		System.out.println("===== oldRiskCode:"+oldCalCode);
		if (!tPD_LMCalModeDB.getInfo()) {
			System.out.println("**************无被复制的SQL定义算法！**************");
			//return;
		}else{
			PD_LMCalModeSchema tPD_LMCalModeSchema = new PD_LMCalModeSchema();
			tPD_LMCalModeSchema.setCalCode(newCalCode);
			tPD_LMCalModeSchema.setRiskCode(newRiskCode);
			tPD_LMCalModeSchema.setType(tPD_LMCalModeDB.getType());
			tPD_LMCalModeSchema.setCalSQL(tPD_LMCalModeDB.getCalSQL());
			tPD_LMCalModeSchema.setRemark(tPD_LMCalModeDB.getRemark());
			tPD_LMCalModeSchema.setOperator(this.mGi.Operator);
			tPD_LMCalModeSchema.setMakeDate(PubFun.getCurrentDate());
			tPD_LMCalModeSchema.setMakeTime(PubFun.getCurrentTime());
			tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());

			map.put(tPD_LMCalModeSchema, "DELETE&INSERT");
			
			PD_LMCalFactorDB fPD_LMCalFactorDB = new PD_LMCalFactorDB();
			fPD_LMCalFactorDB.setCalCode(oldCalCode);
			PD_LMCalFactorSet fPD_LMCalFactorSet = fPD_LMCalFactorDB.query();
			
			if(fPD_LMCalFactorSet.size()>0){
				boolean checkFacEquCal = (fPD_LMCalFactorSet.get(1).getCalCode()).equals(fPD_LMCalFactorSet.get(1).getFactorCode());
				for(int i = 1 ; i<=fPD_LMCalFactorSet.size() ; i++){
					String newFactorCode = getNewCalcode(fPD_LMCalFactorSet.get(i).getFactorCalCode());
					PD_LMCalFactorSchema fPD_LMCalFactorSchema = fPD_LMCalFactorSet.get(i);
					fPD_LMCalFactorSchema.setCalCode(newCalCode);
					fPD_LMCalFactorSchema.setFactorCode(newFactorCode);
					fPD_LMCalFactorSchema.setFactorName(fPD_LMCalFactorSet.get(i).getFactorName());
					fPD_LMCalFactorSchema.setFactorType(fPD_LMCalFactorSet.get(i).getFactorType());
					fPD_LMCalFactorSchema.setFactorGrade(fPD_LMCalFactorSet.get(i).getFactorGrade());
					fPD_LMCalFactorSchema.setFactorCalCode(newFactorCode);
					fPD_LMCalFactorSchema.setFactorDefault(fPD_LMCalFactorSet.get(i).getFactorDefault());
	
					fPD_LMCalFactorSchema.setMakeDate(PubFun.getCurrentDate());
					fPD_LMCalFactorSchema.setMakeTime(PubFun.getCurrentTime());
					fPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
					fPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());
					fPD_LMCalFactorSchema.setOperator(this.mGi.Operator);
					
					map.put(fPD_LMCalFactorSchema, "DELETE&INSERT");
					
					PD_LMCalModeDB ttPD_LMCalModeDB = new PD_LMCalModeDB();
					ttPD_LMCalModeDB.setCalCode(fPD_LMCalFactorSet.get(i).getFactorCode());
					PD_LMCalModeSet ttPD_LMCalModeSet = ttPD_LMCalModeDB.query();
					if(ttPD_LMCalModeSet.size()>0){
						String ttName = "";
						String ttCalCode = "";
						ttCalCode = fPD_LMCalFactorSet.get(i).getCalCode();
						if (ttCalCode.startsWith("RUCAL")|| ttCalCode.startsWith("CAL")) {
							ttName = ttCalCode;
						} else if (!ttCalCode.equals("")) {
							ttName = getNewCalcode(ttCalCode);
							if(!checkFacEquCal){
								copyRuleSQL(newRiskCode,fPD_LMCalFactorSet.get(i).getFactorCode(),ttName);
							}
						}
					}
				}
			}
		}
	}
	private  void copyRuleEngine(String newRiskCode,String oldRuleName,String newRuleName) {
		// TODO Auto-generated method stub
		ExeSQL tExeSQL = new ExeSQL();
		System.out.println("===== oldRiskCode:"+oldRuleName);
		
		String SQL = "";
		LRTemplateDB tLRTemplateDB = new LRTemplateDB();
		tLRTemplateDB.setRuleName(oldRuleName);
		LRTemplateSet tLRTemplateSet = tLRTemplateDB.query();
		if (tLRTemplateSet.size() < 1) {
			System.out.println("**************无被复制的规则引擎算法！**************");
			return;
		}
		String tNewID ="PE" + PubFun1.CreateMaxNo("PE", 18);
		LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
		tLRRuleDataDB.setId(tLRTemplateSet.get(1).getId());
		if (tLRRuleDataDB.getInfo()) {
			LRRuleDataSchema tLRRuleDataSchema = new LRRuleDataSchema();
			tLRRuleDataSchema = tLRRuleDataDB.getSchema();
			tLRRuleDataSchema.setId(tNewID);
			tLRRuleDataSchema.setModifyDate(PubFun.getCurrentDate());
			tLRRuleDataSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(tLRRuleDataSchema, "DELETE&INSERT");
		}
		LRTemplateSchema tLRTemplateSchema = tLRTemplateSet.get(1);
		tLRTemplateSchema.setRuleName(newRuleName);
		tLRTemplateSchema.setId(tNewID);
		tLRTemplateSchema.setVersion(1);
		tLRTemplateSchema.setModifyDate(PubFun.getCurrentDate());
		tLRTemplateSchema.setModifyTime(PubFun.getCurrentTime());
		if (tLRTemplateSchema.getTableName().startsWith("DT")) {
			String IBRMSDTTNO = PubFun1.CreateMaxNo("ibrmsDTTNo", 4);
			String tablename = "DT" + IBRMSDTTNO;
			SQL = "create table " + tablename + " as select * from " + tLRTemplateSchema.getTableName();
			tLRTemplateSchema.setSQLStatement(tLRTemplateSchema.getSQLStatement().replace(tLRTemplateSchema.getTableName(),tablename));
			tLRTemplateSchema.setTableName(tablename);
			tLRTemplateSchema.setSQLStatement(tLRTemplateSchema.getSQLStatement().replaceAll(tLRTemplateSchema.getTableName(),tablename));
			tExeSQL.execUpdateSQL(SQL);
		}

		map.put(tLRTemplateSchema, "DELETE&INSERT");
		
		PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
		tPD_LMCalModeDB.setCalCode(oldRuleName);
		if (!tPD_LMCalModeDB.getInfo()) {
			System.out.println("无被复制的规则引擎算法！");
			return ;
		}
		PD_LMCalModeSchema tPD_LMCalModeSchema = new PD_LMCalModeSchema();
		tPD_LMCalModeSchema.setCalCode(newRuleName);
		tPD_LMCalModeSchema.setRiskCode(newRiskCode);
		tPD_LMCalModeSchema.setType(tPD_LMCalModeDB.getType());
		tPD_LMCalModeSchema.setRemark(tPD_LMCalModeDB.getRemark());
		tPD_LMCalModeSchema.setOperator(this.mGi.Operator);
		tPD_LMCalModeSchema.setCalSQL(tPD_LMCalModeDB.getType().startsWith("RU") ? tPD_LMCalModeDB.getType().substring(2): tPD_LMCalModeDB.getType());
		tPD_LMCalModeSchema.setMakeDate(PubFun.getCurrentDate());
		tPD_LMCalModeSchema.setMakeTime(PubFun.getCurrentTime());
		tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
		tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());

		map.put(tPD_LMCalModeSchema, "DELETE&INSERT");
		
	}
	
	//-------- end
	private boolean deployRuleEngine() {
		boolean tResultFlag = true;
		LRTemplateSet tLRTemplateSet = new LRTemplateSet();
		PD_LMCalModeSet tPD_LMCalModeSet = new PD_LMCalModeSet();
		if (this.mLRTemplateSet.size() > 0) {
			LRRuleDataSet tLRRuleDataSet = new LRRuleDataSet();
			// 需要带上LRRuleData
			for (int i = 1; i <= mLRTemplateSet.size(); i++) {

				String tId = mLRTemplateSet.get(i).getId();
				String rulename = mLRTemplateSet.get(i).getRuleName();
				if (!mCalCodeMap.containsKey(rulename))
					continue;

				//String tNewID = PubFun1.CreateMaxNo("ibrmsTemplateID", 20);
				String tNewID ="PE" + PubFun1.CreateMaxNo("PE", 18);
				mLRTemplateSet.get(i).setId(tNewID);
				mLRTemplateSet.get(i).setRuleName(mCalCodeMap.get(rulename));

				String tTableName = mLRTemplateSet.get(i).getTableName();

				if (tTableName.toUpperCase().equals("RULEDATA")) {
					LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
					tLRRuleDataDB.setId(tId);
					if (tLRRuleDataDB.getInfo()) {
						tLRRuleDataDB.getSchema().setId(tNewID);
						tLRRuleDataSet.add(tLRRuleDataDB.getSchema());
					}
				} else if (tTableName.toUpperCase().equals("LDSYSVAR")) {
					continue;
				} else if (tTableName.toUpperCase().indexOf("DT") != -1) {
					// 决策表,需要获取决策表的建表语句和数据
					//
					String DTTableSerialNumber = PubFun1.CreateMaxNo(
							"ibrmsDTTNo", 4);
					System.out.println("获取的流水号(DTTableSerialNumber)是："
							+ DTTableSerialNumber);

					String tNewTableName = "DT" + DTTableSerialNumber;
					// 先判断目标库是否已经有该表,如果有,先drop,然后重新创建.

					// 获取当前表的建表语句

					String tQuery_SQL_DDL = "select  DBMS_METADATA.GET_DDL('TABLE',UPPER('"
							+ tTableName + "')) FROM DUAL ";
					ExeSQL tExeSQL = new ExeSQL();
					System.out.println(tExeSQL.getOneValue(tQuery_SQL_DDL));
					String tSQL_DDL = "";
					tSQL_DDL = tExeSQL.getOneValue(tQuery_SQL_DDL);
					
					if (tSQL_DDL.indexOf("USING INDEX") != -1) {
						tSQL_DDL = tSQL_DDL.substring(0, tSQL_DDL
								.indexOf("USING INDEX"));
						tSQL_DDL = tSQL_DDL + " )";
					} else if (tSQL_DDL.indexOf("PCTFREE") != -1) {
						tSQL_DDL = tSQL_DDL.substring(0, tSQL_DDL
								.indexOf("PCTFREE"));
					}

					tSQL_DDL = StrTool.replaceEx(tSQL_DDL, "\"", "");
					tSQL_DDL = StrTool.replaceEx(tSQL_DDL, tTableName
							.toUpperCase(), tNewTableName.toUpperCase());

					// 获取当前的用户
					String tSQL_CurrentUser = "select SYS_CONTEXT('USERENV','CURRENT_USER') from dual ";
					String tTableUser = tExeSQL.getOneValue(tSQL_CurrentUser);
					System.out.println("CurrentUser:" + tTableUser);
					if (tTableUser != null && !tTableUser.equals("")) {
						tSQL_DDL = StrTool.replaceEx(tSQL_DDL,
								tTableUser + ".", "");
					}

					System.out.println("建表语句:" + tSQL_DDL);

					this.map.put(tSQL_DDL, "CREATE");
					
					// 生成插入决策表的语句
					SSRS tDTSSRS = new SSRS();
					String tSQL_DT = "select * from " + tTableName;
					tDTSSRS = tExeSQL.execSQL(tSQL_DT);

					String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where lower(table_name) ='"
							+ tTableName.toLowerCase() + "' order by column_id";
					SSRS tColsSSRS = new SSRS();
					tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);

					for (int m = 1; m <= tDTSSRS.getMaxRow(); m++) {
						// 每一行的数据
						String tInsertSQL = "insert into " + tNewTableName;

						String tCols = "";
						String tValues = " ";
						tCols = tCols + "(";
						tValues = tValues + "(";
						for(int n=1;n<=tColsSSRS.getMaxRow();n++)
						{
							String tColName = tColsSSRS.GetText(n, 1);
							String tColType = tColsSSRS.GetText(n, 2);
							String tColValue = tDTSSRS.GetText(m, n);
							//pdSchema.getFieldCount()
	
							
							tCols = tCols + tColName ;
							if(tColType.toUpperCase().equals("VARCHAR2")||
									tColType.toUpperCase().equals("CHAR")||
									tColType.toUpperCase().equals("VARCHAR")||tColType.toUpperCase().equals("DATE"))
							{
								tValues = tValues + "'" + tColValue + "'" ;
							}
							else 
							{
								
								tValues = tValues + tColValue ;
							}
							if(n!=tColsSSRS.getMaxRow())
							{
								tCols = tCols + ",";
								tValues = tValues + ",";
							}
						}
						tCols = tCols + ")";
						tValues = tValues + ")";
						
						tInsertSQL = tInsertSQL + " " + tCols + " values " + tValues;
						System.out.println("tInsertSQL:"+tInsertSQL);
						this.map.put(tInsertSQL, "INSERT");
					}
					mLRTemplateSet.get(i).setTableName(tNewTableName);
					mLRTemplateSet.get(i).setSQLStatement(mLRTemplateSet.get(i).getSQLStatement().replaceAll(tTableName.toUpperCase(), tNewTableName));
					tLRTemplateSet.add(mLRTemplateSet.get(i).getSchema());
				}

			}

			// this.map.put(mLRTemplateSet, "DELETE&INSERT");
			this.map.put(tLRTemplateSet, "DELETE&INSERT");
			// ================================================
			// 生成PD_LMCalMode数据
			Object[] mCalCodeSetArr = mCalCodeSet.toArray();
			for (int j = 0; j < mCalCodeSetArr.length; j++) {
				if (mCalCodeMap.size() > 0&& mCalCodeMap.containsKey(mCalCodeSetArr[j])) {
					PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
					tPD_LMCalModeDB.setCalCode(mCalCodeSetArr[j].toString());
					PD_LMCalModeSchema tPD_LMCalModeSchema = new PD_LMCalModeSchema();
					if (tPD_LMCalModeDB.getInfo()) {
						tPD_LMCalModeSchema = tPD_LMCalModeDB.getSchema();
						tPD_LMCalModeSchema.setCalCode(mCalCodeMap.get(mCalCodeSetArr[j]));
						tPD_LMCalModeSchema.setType("CC");
						tPD_LMCalModeSchema.setRiskCode(mNewRiskCode);
						tPD_LMCalModeSchema.setOperator(this.mGi.Operator);
						tPD_LMCalModeSet.add(tPD_LMCalModeSchema);
					} else {
						tPD_LMCalModeSchema.setCalCode(mCalCodeMap.get(mCalCodeSetArr[j]));
						tPD_LMCalModeSchema.setType("CC");
						tPD_LMCalModeSchema.setRiskCode(mNewRiskCode);
						tPD_LMCalModeSchema.setOperator(this.mGi.Operator);
						tPD_LMCalModeSchema.setMakeDate(PubFun.getCurrentDate());
						tPD_LMCalModeSchema.setMakeTime(PubFun.getCurrentTime());
						tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
						tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());
						tPD_LMCalModeSchema.setCalSQL("select 'CC' from dual");
						tPD_LMCalModeSet.add(tPD_LMCalModeSchema);

					}
					mCalCodeMap.remove(mCalCodeSetArr[j]);
				}

			}
			// ================================================
			if (tLRRuleDataSet.size() > 0) {
				this.map.put(tLRRuleDataSet, "DELETE&INSERT");
			}
		}

		return tResultFlag;
	}

	/**
	 * @author NicolE
	 * @desc 过滤数据
	 * @return
	 */
	private boolean filterData(Schema pdSchema) {

		// String className = pdSchema.getClass().getName();

		// if (className.indexOf("LMUWSchema") > -1) {
		// return true;
		// } else if (className.indexOf("LMCalModeSchema") > -1) {
		// return true;
		// } else if (className.indexOf("LMCalFactorSchema") > -1) {
		// return true;
		// } else if (className.indexOf("LMCalFactorSchema") > -1) {
		// return true;
		// }

		return false;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	private boolean prepareOutputData() {
		mResult.add(map);
		return true;
	}

	private void getRiskCalCode() {
		PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
		tPD_LMCalModeDB.setRiskCode(mRiskCode);
		PD_LMCalModeSet tPD_LMCalModeSet = tPD_LMCalModeDB.query();
		for (int i = 1; i <= tPD_LMCalModeSet.size(); i++) {
			if (!tPD_LMCalModeSet.get(i).getCalCode().startsWith("RU"))
				mCalCodeSet.add("RU" + tPD_LMCalModeSet.get(i).getCalCode());
			else
				mCalCodeSet.add(tPD_LMCalModeSet.get(i).getCalCode());
		}

	}
	

	public static void main(String[] args) {

		try {
			/*
			 * String sql = "SELECT * FROM Pd_Lmrisk WHERE riskcode = '7788'";
			 * PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB(); Schema fromSchema =
			 * (tPD_LMRiskDB.executeQuery(sql)).get(1); String[] pk =
			 * fromSchema.getPK();
			 * 
			 * System.out.println("---->" + fromSchema.getClass().getName());
			 * 
			 * Method m; // Class[] c = new Class[1]; // c[0] = String.class; //
			 * m = fromSchema.getClass().getMethod("getPK", c); //
			 * System.out.println("m is :" + m.toString()); String fieldValue =
			 * "8888"; int size = pk.length;
			 * 
			 * for (int i = 0; i < size; i++) {
			 * 
			 * System.out.println(i + "--" + pk[i]);
			 * 
			 * Class[] c = new Class[1]; c[0] = String.class; m =
			 * fromSchema.getClass().getMethod("getV", c);
			 * 
			 * Object[] o = new Object[1]; o[0] = pk[i]; String pkValue =
			 * (String) m.invoke(fromSchema, o); System.out.println("pkValue:" +
			 * pkValue);
			 * 
			 * Class[] coreClass = new Class[2]; coreClass[0] = String.class;
			 * coreClass[1] = String.class; m =
			 * fromSchema.getClass().getMethod("setV", coreClass);
			 * 
			 * Object[] coreObject = new Object[2]; coreObject[0] = pk[i];
			 * coreObject[1] = fieldValue;
			 * 
			 * m.invoke(fromSchema, coreObject); }
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

