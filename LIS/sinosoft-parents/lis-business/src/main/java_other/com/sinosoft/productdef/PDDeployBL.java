

/**
 * <p>Title: PDAlgoTempLib</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

// import java.io.File;
// import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
// import java.sql.ResultSet;
// import java.util.List;
// import java.util.Iterator;

// import javax.xml.parsers.DocumentBuilderFactory;

// import javax.swing.text.html.HTMLDocument.Iterator;
// import javax.xml.parsers.DocumentBuilderFactory;
//
// import org.apache.xpath.XPathAPI;
// import org.apache.xpath.objects.XObject;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;

// import com.sinosoft.lis.db.*;
// import com.sinosoft.lis.vdb.*;
// import com.sinosoft.lis.sys.*;
// import com.sinosoft.lis.schema.*;
// import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;

// import com.sinosoft.msreport.CircJSDateParserXML;
// import org.dom4j.*;
// import org.dom4j.io.*;

public class PDDeployBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	// private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mRiskCode;

	private String mSQLParaName = "RISKCODE";

	private String mSQLSepaChar = "@";

	private String mIsDeleteCoreDataBeforeInsert; // 1:执行删除操作,0:不执行

	public PDDeployBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {

			this.mInputData = cInputData;

			if (!getInputData()) {
				return false;
			}

			if (!check()) {
				return false;
			}

			// 删除核心系统中原有的该产品信息
			if (!deleteCoreData()) {
				return false;
			}

			// 进行业务处理
			if (!deploy()) {
				return false;
			}

			// 处理费率表、现金价值表
			if (!deployRateCVData()) {
				return false;
			}

			this.mResult.clear();
			this.mResult.add(this.map);

			// this.pubSubmit();

		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean check() {

		return true;
	}

	// deploy rate and cashvalue data
	public boolean deployRateCVData() throws Exception {
		String select = "select coretablename from Pd_Ratecvsql where riskcode = '"
				+ this.mRiskCode + "' and sqltype = '1'";
		ExeSQL exec = new ExeSQL();
		SSRS ssrs = exec.execSQL(select);
		if (ssrs.MaxRow > 0) {
			for (int i = 1; i <= ssrs.MaxRow; i++) {
				String coreTableName = ssrs.GetText(i, 1);

				String tReleasePlatform = (String) ((TransferData) this.mInputData
						.getObjectByObjectName("TransferData", 0))
						.getValueByName("ReleasePlatform");

				// insert
				String tDeployeMode = (String)this.mInputData.getObjectByObjectName("String", 0);
				String insertSQL;
				if(tDeployeMode!=null && "0".equals(tDeployeMode)){
					insertSQL = "insert into " + coreTableName + "@"
						+ tReleasePlatform + " (select * from " + coreTableName
						+ ")";
					this.map.put(insertSQL, "INSERT");
				}else{
					insertSQL = "insert into " + coreTableName
						+ " (select * from " + coreTableName + ")";
				}

				//this.map.put(insertSQL, "INSERT");
			}
		}

		return true;
	}

	/**
	 * 提交数据，进行数据库操作
	 * 
	 * @return
	 */
	// private boolean pubSubmit() {
	// // 进行数据提交
	// PubSubmit tPubSubmit = new PubSubmit();
	// if (!tPubSubmit.submitData(this.mResult, "")) {
	// this.mErrors.addOneError("数据提交失败!");
	// return false;
	// }
	// return true;
	//
	// }
	/**
	 * 得到传入数据
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			this.mErrors.addOneError("无操作员信息，请重新登录!");
			return false;
		}
		// 获得业务数据
		if (mTransferData == null) {
			this.mErrors.addOneError("前台传输业务数据失败!");
			return false;
		}

		this.mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		if (this.mRiskCode == null) {
			this.mErrors.addOneError("险种代码不能为空!");
			return false;
		}

		this.mIsDeleteCoreDataBeforeInsert = (String) mTransferData
				.getValueByName("IsDeleteCoreDataBeforeInsert");

		return true;
	}

	public boolean deleteCoreData() {
		if (this.mIsDeleteCoreDataBeforeInsert.equals("1")) {
			SSRS deleteSQLs = getDeleteSQLs();

			if (deleteSQLs != null) {
				for (int index = 0; index < deleteSQLs.getMaxRow(); index++) {
					String deleteSQL = deleteSQLs.GetText(index + 1, 1);

					if (!deleteSQL.equals("")) {
						this.map.put(deleteSQL, "DELETE");
					}
				}
			}
		}

		return true;
	}

	private SSRS getDeleteSQLs() {
		SSRS ssrs = new SSRS();

		String deleteSQLs = "select standbyflag1 from Pd_Tablemap where standbyflag1 is not null and substr(standbyflag2,-1,1) = '1' order by standbyflag3";

		ssrs = getSSRSResult(deleteSQLs, 1);

		return ssrs;

	}

	public boolean deploy() throws Exception {
		// get product define table names, core table names, select sql
		SSRS tableNameAndSelect = getTableNameAndSelect();

		if (tableNameAndSelect != null) {
			for (int tableIndex = 0; tableIndex < tableNameAndSelect
					.getMaxRow(); tableIndex++) {
				// deal one table

				String pdTableCode = tableNameAndSelect.GetText(tableIndex + 1,
						1);
				String coreTableCode = tableNameAndSelect.GetText(
						tableIndex + 1, 2);
				String selectSQL = tableNameAndSelect
						.GetText(tableIndex + 1, 3);

				// get data
				SchemaSet pdSet = getProductData(pdTableCode, selectSQL);

				if (pdSet != null) {
					int schemaCount = pdSet.size();

					for (int i = 0; i < schemaCount; i++) {
						Schema pdSchema = (Schema) pdSet.getObj(i + 1);

						// directly from pd schema to core schema
						Class coreSchemaClass = Class
								.forName("com.sinosoft.lis.schema."
										+ coreTableCode + "Schema");
						Schema coreSchema = (Schema) coreSchemaClass
								.newInstance();
						convertData(pdSchema, coreSchema);
						if(pdTableCode.equals("PD_LMRiskEdorItem"))
						{
							System.out.println("test");
						}
						// If there is info in PD_FieldMap, reset the new data
						dealMap(pdTableCode, coreTableCode, pdSchema,
								coreSchema);

						// add to map
						this.map.put(coreSchema, "INSERT");
					}
				}
			}
		}

		return true;
	}

	private void convertData(Schema fromSchema, Schema toSchema)
			throws Exception {
		int fieldCount = fromSchema.getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++) {
			String fieldName = fromSchema.getFieldName(fieldIndex);
			convertData(fromSchema, toSchema, fieldName, fieldName);
		}
	}

	/***************************************************************************
	 * 
	 * @return SSRS:PDTableName,SelectSQL,CoreTableName
	 */
	private SSRS getTableNameAndSelect() throws Exception {
		String selectSQL = "select a.Tablecode,a.Coretablecode,b.Standbyflag1 from Pd_Tablemap a "
				+ " inner join Pd_Basetable b "
				+ " on upper(a.Tablecode) = b.Tablecode "
				+ " and b.Standbyflag1 is not null and substr(a.standbyflag2,-1,1) = '1'";

		SSRS ssrs = getSSRSResult(selectSQL, 3);

		return ssrs;
	}

	private SSRS getSSRSResult(String selectSQL, int indexOfRiskCode) {
		SSRS ssrs = new SSRS();
		ExeSQL exec = new ExeSQL();
		ssrs = exec.execSQL(selectSQL);

		System.out.println("\n查询语句：");

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
					// 针对发布产品和套餐时都需要用到的表，第二个参数是没有用的
					// delete from LMRiskComCtrl where
					// riskcode=Trim('@RISKCODE@') or riskcode =
					// Trim('@CARDSETMEAL@')
					tStr1 = mSQLSepaChar + tParaStr.trim() + mSQLSepaChar;
					// 替换变量
					sql = StrTool.replaceEx(sql, tStr1, "无用");

				}
			}

			System.out.println(sql);

			ssrs.setTextAt(i + 1, indexOfRiskCode, sql);
		}
		return ssrs;
	}

	/**
	 * @author NicolE 修改PD_LMdutyGet表的数据，对单给付责任对应多责任的情况进行处理
	 * @param pdTableCode
	 * @param selectSQL
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private SchemaSet getProductData(String pdTableCode, String selectSQL)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		// get data using pd schema

		if (selectSQL.equals("")) {
			return null;
		}

		Class pdSetClass = Class.forName("com.sinosoft.lis.vschema."
				+ pdTableCode + "Set");
		SchemaSet pdSet = (SchemaSet) pdSetClass.newInstance();

		Class pdDBClass = Class.forName("com.sinosoft.lis.db." + pdTableCode
				+ "DB");
		Schema pdDB = (Schema) pdDBClass.newInstance();

		Class[] paras = new Class[1];
		paras[0] = String.class;
		Object[] args = new Object[1];
		args[0] = selectSQL;

		Method m = pdDB.getClass().getMethod("executeQuery", paras);
		pdSet = (SchemaSet) m.invoke(pdDB, args);

		pdSet = dealDutyGetData(pdTableCode, pdSet);

		return pdSet;
	}

	private void dealMap(String pdTableCode, String coreTableCode,
			Schema pdSchema, Schema coreSchema) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String sql = "select PDFieldCode,CoreFiledCode from Pd_Fieldmap where upper(tablecode) = upper('"
				+ pdTableCode
				+ "') and upper(coreTableCode) = upper('"
				+ coreTableCode + "')";
		SSRS ssrs = new SSRS();
		ExeSQL exec = new ExeSQL();
		ssrs = exec.execSQL(sql);

		if (ssrs != null) {
			for (int fieldIndex = 0; fieldIndex < ssrs.getMaxRow(); fieldIndex++) {
				String pdFieldCode = ssrs.GetText(fieldIndex + 1, 1);
				String coreFieldCode = ssrs.GetText(fieldIndex + 1, 2);

				convertData(pdSchema, coreSchema, pdFieldCode, coreFieldCode);
			}
		}
	}

	/***************************************************************************
	 * 将fromSchema中的fromFieldCode的值传递给toSchema的toFieldCode。
	 * 
	 * @param fromSchema
	 * @param toSchema
	 * @param fromFieldCode
	 * @param toFieldCode
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void convertData(Schema fromSchema, Schema toSchema,
			String fromFieldCode, String toFieldCode)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Method m;

		try {
			Class[] c = new Class[1];
			c[0] = String.class;
			m = fromSchema.getClass().getMethod("getV", c);

			Object[] o = new Object[1];
			o[0] = fromFieldCode;
			String fieldValue = (String) m.invoke(fromSchema, o);

			Class[] coreClass = new Class[2];
			coreClass[0] = String.class;
			coreClass[1] = String.class;
			m = toSchema.getClass().getMethod("setV", coreClass);

			Object[] coreObject = new Object[2];
			coreObject[0] = toFieldCode;
			coreObject[1] = fieldValue;
			m.invoke(toSchema, coreObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fromSchema:"+fromSchema+":fromFieldCode:"+fromFieldCode+":toFieldCode:"+toFieldCode);
		} 
	}

	public VData getResult() {
		return this.mResult;
	}

	public void test(String pdTableCode) throws Exception {
		Class pdSchemaClass = Class.forName("com.sinosoft.lis.schema."
				+ pdTableCode + "Schema");
		Schema pdSchema = (Schema) pdSchemaClass.newInstance();

		Class[] c = new Class[1];
		c[0] = String.class;

		Method m = pdSchema.getClass().getMethod("getCode", null);
		Object[] o = new Object[1];
		o[0] = "Code";

		Object value = m.invoke(pdSchema, null);

		if (value == null) {
			System.out.println("null");
		} else if (value.equals("null")) {
			System.out.println(value);
		}
	}

	/**
	 * @author NicolE 从PD_LMDutyGet/PD_LMDutyPay表中获取唯一的GetDutycode/Payplancode数据
	 * @return
	 */
	private SchemaSet dealDutyGetData(String tTableCode, SchemaSet pdSet) {

		SchemaSet newSet = new SchemaSet();
		java.util.HashMap hashMap = new java.util.HashMap();
		if (tTableCode.equalsIgnoreCase("PD_LMDutyGet")) {
			for (int i = 1; i <= pdSet.size(); i++) {
				PD_LMDutyGetSchema pdSchema = (PD_LMDutyGetSchema) pdSet
						.getObj(i);
				String tDutyCode = pdSchema.getDutyCode();
				String tGetDutycode = pdSchema.getGetDutyCode();
				if (hashMap.containsKey(tGetDutycode)) {
					continue;
				} else {
					hashMap.put(tGetDutycode, tDutyCode);
					newSet.add(pdSchema);
				}
			}
			return newSet;
		} else if (tTableCode.equalsIgnoreCase("PD_LMDutyPay")) {
			for (int i = 1; i <= pdSet.size(); i++) {
				PD_LMDutyPaySchema pdSchema = (PD_LMDutyPaySchema) pdSet
						.getObj(i);
				String tDutyCode = pdSchema.getDutyCode();
				String tPayPlanCode = pdSchema.getPayPlanCode();
				if (hashMap.containsKey(tPayPlanCode)) {
					continue;
				} else {
					hashMap.put(tPayPlanCode, tDutyCode);
					newSet.add(pdSchema);
				}
			}
			return newSet;
		} else {
			return pdSet;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String sql = "SELECT * FROM pd_lmdutyget WHERE getdutycode like '4122%'";
			com.sinosoft.lis.db.PD_LMDutyGetDB db = new com.sinosoft.lis.db.PD_LMDutyGetDB();
			SchemaSet pdSet = db.executeQuery(sql);
			System.out.println("pdSet:" + pdSet.size());
			SchemaSet newSet = new SchemaSet();
			java.util.HashMap hashMap = new java.util.HashMap();
			for (int i = 1; i <= pdSet.size(); i++) {
				PD_LMDutyGetSchema pdSchema = (PD_LMDutyGetSchema) pdSet
						.getObj(i);
				String tDutyCode = pdSchema.getDutyCode();
				String tGetDutycode = pdSchema.getGetDutyCode();
				System.out.println("tDutyCode:" + tDutyCode + ";tGetDutycode:"
						+ tGetDutycode);
				if (hashMap.containsKey(tGetDutycode)) {
					continue;
				} else {
					hashMap.put(tGetDutycode, tDutyCode);
					newSet.add(pdSchema);
				}
			}
			System.out.println("newSet:" + newSet.size());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
