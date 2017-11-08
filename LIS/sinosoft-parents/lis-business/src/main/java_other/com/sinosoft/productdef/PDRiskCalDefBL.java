

package com.sinosoft.productdef;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LDDeployScriptInfoDB;
import com.sinosoft.lis.db.LDMaxNoDB;
import com.sinosoft.lis.db.LRRuleDataBDB;
import com.sinosoft.lis.db.LRRuleDataDB;
import com.sinosoft.lis.db.LRTemplateBDB;
import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.db.PD_LMCalModeDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LDDeployScriptInfoSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.schema.LRRuleDataBSchema;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.lis.schema.LRTemplateSchema;
import com.sinosoft.lis.schema.PD_LMCalModeSchema;
import com.sinosoft.lis.vdb.LRRuleDataBDBSet;
import com.sinosoft.lis.vdb.LRRuleDataDBSet;
import com.sinosoft.lis.vdb.LRTemplateBDBSet;
import com.sinosoft.lis.vschema.LRRuleDataBSet;
import com.sinosoft.lis.vschema.LRRuleDataSet;
import com.sinosoft.lis.vschema.LRTemplateBSet;
import com.sinosoft.lis.vschema.LRTemplateSet;

public class PDRiskCalDefBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private String RiskCode;

	private String CalCode;

	private String Operator;

	private String CalModeType;

	private String Des;

	private String ID;

	private String NewRiskCode;

	private String NewCalCode;

	private String NewCalModeType;

	private String NewDes;

	private String OldCalCode;

	private ArrayList<String> mSQLArray = new ArrayList<String>();

	public PDRiskCalDefBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData)) {
			return false;
		}
		if (!check()) {
			return false;
		}
		// 进行业务处理
		if (!deal()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult)) {
			return false;
		}
		return true;
	}

	private boolean check() {
		if (Operator == null || "".equals(Operator.trim())) {
			this.mErrors.addOneError("没有操作类型！");
			return false;
		}
		if (CalCode == null || "".equals(CalCode.trim())) {
			this.mErrors.addOneError("算法编码不能为空！");
			return false;
		}
		// if(!CalCode.startsWith("RU")){
		// this.mErrors.addOneError("不符合规则引擎算法编码规则！算法编码应该以RU开头。");
		// return false;
		// }
		if (!"deploy".equals(Operator.toLowerCase())) {
//			if (!("PC".equals(CalModeType) || "SC".equals(CalModeType)
//					|| "RC".equals(CalModeType) || "CC".equals(CalModeType))) {
//				this.mErrors.addOneError("算法类型错误！算法类型是：" + CalModeType);
//				return false;
//			}
		}
		if ("copy".equals(Operator.toLowerCase())) {
			if (OldCalCode == null || "".equals(OldCalCode.trim())) {
				this.mErrors.addOneError("原有算法编码不能为空！");
				return false;
			}
			if (NewCalCode == null || "".equals(NewCalCode)
					|| !NewCalCode.startsWith("RU")) {
				this.mErrors.addOneError("复制的算法编码有误！");
				return false;
			}
//			if (!("PC".equals(NewCalModeType) || "SC".equals(NewCalModeType)
//					|| "CC".equals(NewCalModeType) || "RC".equals(CalModeType))) {
//				this.mErrors.addOneError("算法类型错误！算法类型是：" + CalModeType);
//				return false;
//			}
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {

		TransferData data = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		Operator = (String) data.getValueByName("Operator");
		CalModeType = (String) data.getValueByName("CalModeType");
		CalCode = (String) data.getValueByName("CalCode");
		RiskCode = (String) data.getValueByName("RiskCode");
		Des = (String) data.getValueByName("Des");
		// 产品复制
		NewCalModeType = (String) data.getValueByName("NewCalModeType");
		NewCalCode = (String) data.getValueByName("NewCalCode");
		NewRiskCode = (String) data.getValueByName("NewRiskCode");
		OldCalCode = (String) data.getValueByName("OldCalCode");
		NewDes = (String) data.getValueByName("NewDes");
		ID = (String) data.getValueByName("ID");

		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public boolean deal() {
		try {

			if ("save".equals(Operator.toLowerCase())) {

				PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
				tPD_LMCalModeDB.setCalCode(CalCode);
				if (tPD_LMCalModeDB.getInfo()) {
					mErrors.addOneError("该算法已经存在，不能重复添加！");
					return false;
				}
				PD_LMCalModeSchema tPD_LMCalModeSchema = new PD_LMCalModeSchema();
				tPD_LMCalModeSchema.setCalCode(CalCode);
				tPD_LMCalModeSchema.setOperator(mGlobalInput.Operator);
				tPD_LMCalModeSchema.setType(CalModeType);
				tPD_LMCalModeSchema.setRiskCode(RiskCode);
				tPD_LMCalModeSchema
						.setCalSQL(CalCode.startsWith("RU") ? CalCode
								.substring(2) : CalCode);
				tPD_LMCalModeSchema.setRemark(Des);
				tPD_LMCalModeSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMCalModeSchema.setMakeTime(PubFun.getCurrentTime());
				tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(tPD_LMCalModeSchema, "INSERT");

			} else if ("del".equals(Operator.toLowerCase())) {
				// 删除PD_LMCalMode表
				PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
				tPD_LMCalModeDB.setCalCode(CalCode);
				if (tPD_LMCalModeDB.getInfo()) {
					PD_LMCalModeSchema tPD_LMCalModeSchema = tPD_LMCalModeDB
							.getSchema();
					map.put(tPD_LMCalModeSchema, "DELETE");
				}

				LRTemplateBDB tLRTemplateBDB = new LRTemplateBDB();
				tLRTemplateBDB.setRuleName(CalCode);
				LRTemplateBSet tLRTemplateBSet = tLRTemplateBDB.query();

				LRRuleDataBSet tLRRuleDataBSet = new LRRuleDataBSet();
				for (int i = 0; i < tLRTemplateBSet.size(); i++) {
					LRRuleDataBDB tLRRuleDataBDB = new LRRuleDataBDB();
					tLRRuleDataBDB.setId(tLRTemplateBSet.get(i + 1).getId());
					if (tLRRuleDataBDB.getInfo()) {
						LRRuleDataBSchema tLRRuleDataBSchema = tLRRuleDataBDB
								.getSchema();
						tLRRuleDataBSet.add(tLRRuleDataBSchema);

					}

				}
				// 删除LRTemplateB表与LRRuleDataB表
				map.put(tLRTemplateBSet, "DELETE");
				map.put(tLRRuleDataBSet, "DELETE");

				LRTemplateDB tLRTemplateDB = new LRTemplateDB();
				tLRTemplateDB.setRuleName(CalCode);
				LRTemplateSet tLRTemplateSet = tLRTemplateDB.query();

				LRRuleDataSet tLRRuleDataSet = new LRRuleDataSet();
				for (int i = 0; i < tLRTemplateSet.size(); i++) {
					LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
					tLRRuleDataDB.setId(tLRTemplateSet.get(i + 1).getId());
					if (tLRRuleDataDB.getInfo()) {
						LRRuleDataSchema tLRRuleDataSchema = tLRRuleDataDB
								.getSchema();
						tLRRuleDataSet.add(tLRRuleDataSchema);

					}

				}
				// 删除LRTemplateB表与LRRuleDataB表
				map.put(tLRTemplateSet, "DELETE");
				map.put(tLRRuleDataSet, "DELETE");
			} else if ("copy".equals(Operator.toLowerCase())) {
				ExeSQL tExeSQL = new ExeSQL();
				String SQL = "";
				LRTemplateDB tLRTemplateDB = new LRTemplateDB();
				tLRTemplateDB.setRuleName(OldCalCode);
				LRTemplateSet tLRTemplateSet = tLRTemplateDB.query();
				if (tLRTemplateSet.size() < 1) {
					mErrors.addOneError("待复制算法不存在或者没有发布！");
					return false;
				}
				String tNewID = PubFun1.CreateMaxNo("ibrmsTemplateID", 20);
				LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
				tLRRuleDataDB.setId(tLRTemplateSet.get(1).getId());
				if (tLRRuleDataDB.getInfo()) {

					LRRuleDataSchema tLRRuleDataSchema = new LRRuleDataSchema();
					tLRRuleDataSchema = tLRRuleDataDB.getSchema();
					tLRRuleDataSchema.setId(tNewID);
					tLRRuleDataSchema.setModifyDate(PubFun.getCurrentDate());
					tLRRuleDataSchema.setModifyTime(PubFun.getCurrentTime());
					map.put(tLRRuleDataSchema, "INSERT");
				}
				LRTemplateSchema tLRTemplateSchema = tLRTemplateSet.get(1);
				tLRTemplateSchema.setRuleName(NewCalCode);
				tLRTemplateSchema.setId(tNewID);
				tLRTemplateSchema.setVersion(1);
				tLRTemplateSchema.setModifyDate(PubFun.getCurrentDate());
				tLRTemplateSchema.setModifyTime(PubFun.getCurrentTime());
				if (tLRTemplateSchema.getTableName().startsWith("DT")) {
					String IBRMSDTTNO = PubFun1.CreateMaxNo("ibrmsDTTNo", 4);
					String tablename = "DT" + IBRMSDTTNO;
					SQL = "create table " + tablename + " as select * from "
							+ tLRTemplateSchema.getTableName();
					// tLRTemplateSchema.setTableName(tablename);
					tLRTemplateSchema.setSQLStatement(tLRTemplateSchema
							.getSQLStatement()
							.replace(tLRTemplateSchema.getTableName(),
									tablename));
					tLRTemplateSchema.setTableName(tablename);
					tLRTemplateSchema.setSQLStatement(tLRTemplateSchema
							.getSQLStatement()
							.replaceAll(tLRTemplateSchema.getTableName(),
									tablename));
					tExeSQL.execUpdateSQL(SQL);
				}

				PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
				tPD_LMCalModeDB.setCalCode(NewCalCode);
				if (tPD_LMCalModeDB.getInfo()) {
					mErrors.addOneError("复制的算法在平台已经存在！");
					return false;
				}
				PD_LMCalModeSchema tPD_LMCalModeSchema = new PD_LMCalModeSchema();
				tPD_LMCalModeSchema.setCalCode(NewCalCode);
				tPD_LMCalModeSchema.setType(NewCalModeType);
				tPD_LMCalModeSchema.setRemark(NewDes);
				tPD_LMCalModeSchema.setRiskCode(NewRiskCode);
				tPD_LMCalModeSchema.setOperator(mGlobalInput.Operator);
				tPD_LMCalModeSchema
						.setCalSQL(NewCalModeType.startsWith("RU") ? NewCalModeType
								.substring(2)
								: NewCalModeType);
				tPD_LMCalModeSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMCalModeSchema.setMakeTime(PubFun.getCurrentTime());
				tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());

				map.put(tLRTemplateSchema, "INSERT");
				map.put(tPD_LMCalModeSchema, "INSERT");
			} else if ("modify".equals(Operator.toLowerCase())) {
				PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
				tPD_LMCalModeDB.setCalCode(CalCode);
				if (!tPD_LMCalModeDB.getInfo()) {
					mErrors.addOneError("要修改的算法不存在！");
					return false;
				}
				PD_LMCalModeSchema tPD_LMCalModeSchema = tPD_LMCalModeDB
						.getSchema();
				tPD_LMCalModeSchema.setRemark(Des);
				tPD_LMCalModeSchema.setType(CalModeType);
				tPD_LMCalModeSchema.setRiskCode(RiskCode);
				map.put(tPD_LMCalModeSchema, "UPDATE");

			} else if ("deploy".equals(Operator.toLowerCase())) {
				return deploy();
			}

			mResult.add(map);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean deploy() {
		if (CalCode.startsWith("RU")) {
			if (ID == null || "".equals(ID)) {
				mErrors.addOneError("没有算法ID不能生成发布脚本！");
				return false;
			}
			LRTemplateDB tLRTemplateDB = new LRTemplateDB();
			tLRTemplateDB.setId(ID);
			if (!tLRTemplateDB.getInfo()) {
				mErrors.addOneError("没有该算法信息或者该算法还未定义完成！");
				return false;
			}
			LRTemplateSchema tLRTemplateSchema = tLRTemplateDB.getSchema();
			String delSQL = PDDeploySQLMaker.makeDeleteSQL(tLRTemplateSchema);
			String insertSQL = PDDeploySQLMaker
					.makeInsertSQL(tLRTemplateSchema);
			mSQLArray.add(delSQL);
			mSQLArray.add(insertSQL);
			LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
			tLRRuleDataDB.setId(ID);
			if (tLRRuleDataDB.getInfo()) {
				LRRuleDataSchema tLRRuleDataSchema = tLRRuleDataDB.getSchema();
				delSQL = PDDeploySQLMaker.makeDeleteSQL(tLRRuleDataSchema);
				insertSQL = PDDeploySQLMaker.makeInsertSQL(tLRRuleDataSchema);
				mSQLArray.add(delSQL);
				mSQLArray.add(insertSQL);
			}
			// PD_LMCalModeDB tPD_LMCalModeDB=new PD_LMCalModeDB();
			// tPD_LMCalModeDB.setCalCode(tLRTemplateSchema.getRuleName());
			// if(tPD_LMCalModeDB.getInfo()){
			// PD_LMCalModeSchema
			// tPD_LMCalModeSchema=tPD_LMCalModeDB.getSchema();
			// LMCalModeSchema tLMCalModeSchema=new LMCalModeSchema();
			// tLMCalModeSchema.setCalCode(tPD_LMCalModeSchema.getCalCode());
			// tLMCalModeSchema.setRiskCode(tPD_LMCalModeSchema.getRiskCode());
			// tLMCalModeSchema.setType(tPD_LMCalModeSchema.getType());
			// tLMCalModeSchema.setCalSQL(tPD_LMCalModeSchema.getCalSQL());
			// tLMCalModeSchema.setRemark(tPD_LMCalModeSchema.getRemark());
			// delSQL=PDDeploySQLMaker.makeDeleteSQL(tLMCalModeSchema);
			// insertSQL=PDDeploySQLMaker.makeInsertSQL(tLMCalModeSchema);
			// mSQLArray.add(delSQL);
			// mSQLArray.add(insertSQL);
			// }
			String TableName = tLRTemplateSchema.getTableName();
			if (TableName != null && TableName.startsWith("DT")) {
				String tQuery_SQL_DDL = "select  DBMS_METADATA.GET_DDL('TABLE',UPPER('"
						+ TableName + "')) FROM DUAL ";
				ExeSQL tExeSQL = new ExeSQL();
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

				// 获取当前的用户
				String tSQL_CurrentUser = "select SYS_CONTEXT('USERENV','CURRENT_USER') from dual ";
				String tTableUser = tExeSQL.getOneValue(tSQL_CurrentUser);
				System.out.println("CurrentUser:" + tTableUser);
				if (tTableUser != null && !tTableUser.equals("")) {
					tSQL_DDL = StrTool
							.replaceEx(tSQL_DDL, tTableUser + ".", "");
				}
				System.out.println("建表语句:" + tSQL_DDL);

				if (!(tSQL_DDL == null || "".equals(tSQL_DDL.trim()))) {
					mSQLArray.add("drop table " + TableName + ";");
					mSQLArray.add(tSQL_DDL + ";");
				}
				// 生成插入决策表的语句
				SSRS tDTSSRS = new SSRS();
				String tSQL_DT = "select * from " + TableName;
				tDTSSRS = tExeSQL.execSQL(tSQL_DT);

				String tSQL_ColSSRS = "select column_name,data_type from user_tab_cols where upper(table_name) =upper('"
						+ TableName.toLowerCase() + "') order by column_id";
				SSRS tColsSSRS = new SSRS();
				tColsSSRS = tExeSQL.execSQL(tSQL_ColSSRS);
				for (int m = 1; m <= tDTSSRS.getMaxRow(); m++) {
					// 每一行的数据
					String tInsertSQL = "insert into " + TableName;

					String tCols = "";
					String tValues = " ";
					tCols = tCols + "(";
					tValues = tValues + "(";
					for (int n = 1; n <= tColsSSRS.getMaxRow(); n++) {
						String tColName = tColsSSRS.GetText(n, 1);
						String tColType = tColsSSRS.GetText(n, 2);
						String tColValue = tDTSSRS.GetText(m, n);
						// pdSchema.getFieldCount()

						tCols = tCols + tColName;
						if (tColType.toUpperCase().equals("VARCHAR2")
								|| tColType.toUpperCase().equals("CHAR")
								|| tColType.toUpperCase().equals("VARCHAR")
								|| tColType.toUpperCase().equals("DATE")) {
							tValues = tValues + "'" + tColValue + "'";
						} else {

							tValues = tValues + tColValue;
						}
						if (n != tColsSSRS.getMaxRow()) {
							tCols = tCols + ",";
							tValues = tValues + ",";
						}
					}
					tCols = tCols + ")";
					tValues = tValues + ")";

					tInsertSQL = tInsertSQL + " " + tCols + " values "
							+ tValues;
					System.out.println("tInsertSQL:" + tInsertSQL);
					mSQLArray.add(tInsertSQL + ";");
				}
			}
		} else {
			LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
			PD_LMCalModeSchema tPD_LMCalModeSchema = new PD_LMCalModeDB()
					.executeQuery(
							"select * from pd_lmcalmode where calcode = '"
									+ CalCode + "'").get(1);
			tLMCalModeSchema.setCalCode(tPD_LMCalModeSchema.getCalCode());
			tLMCalModeSchema.setCalSQL(tPD_LMCalModeSchema.getCalSQL());
			tLMCalModeSchema.setRemark(tPD_LMCalModeSchema.getRemark());
			tLMCalModeSchema.setRiskCode(tPD_LMCalModeSchema.getRiskCode());
			tLMCalModeSchema.setType(tPD_LMCalModeSchema.getType());
			String insertSQL = PDDeploySQLMaker.makeInsertSQL(tLMCalModeSchema);
			mSQLArray.add(insertSQL);
		}

		if (mSQLArray.size() > 0) {
			// SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddhhmmss");
			// String seri=sd.format(new Date());
			// String tFileName = "CAL-"+CalCode+"-"+seri+"-SQL.sql";
			// 设置算法发布环境默认值
			String ENV = "01";
			ExeSQL tExeSQL = new ExeSQL();
			String SQL = "select code from ldcode where codetype = 'pd_release' and comcode='"
					+ ENV + "'";
			String envCode = tExeSQL.getOneValue(SQL);
			String tFileName = PubFun.getScripName("CAL", envCode, CalCode);
			SQL = "select nvl(max(to_number(version))+1,1) from LDDeployScriptInfo where riskcode ='"
					+ RiskCode
					+ "' and environment='"
					+ ENV
					+ "' and type='3' and name like 'CAL_"
					+ envCode
					+ "_"
					+ CalCode + "_%.sql' ";
			String version = tExeSQL.getOneValue(SQL);
			LDDeployScriptInfoSchema aLDDeployScriptInfoSchema = new LDDeployScriptInfoSchema();
			aLDDeployScriptInfoSchema.setRiskCode(RiskCode);
			aLDDeployScriptInfoSchema.setType("3");
			aLDDeployScriptInfoSchema.setName(tFileName);
			aLDDeployScriptInfoSchema.setPath(PubFun.getProductDeployPath());
			// 默认为LIS环境
			aLDDeployScriptInfoSchema.setEnvironment(ENV);
			aLDDeployScriptInfoSchema.setVersion(version);
			aLDDeployScriptInfoSchema.setOperator(mGlobalInput.Operator);
			if (!PDDeployScriptDownload.recored(aLDDeployScriptInfoSchema)) {
				this.mErrors.addOneError("算法发布信息记录失败!");
				return false;
			}
			PubFun.ProductWriteToFile(tFileName, mSQLArray);
		}
		return true;
	}

	public static void main(String[] args) {
	}
}

