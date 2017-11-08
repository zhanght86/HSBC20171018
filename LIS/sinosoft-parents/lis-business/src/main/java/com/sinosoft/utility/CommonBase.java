package com.sinosoft.utility;
import org.apache.log4j.Logger;

/**
 * <p>Title: CommonBL.java</p>
 * <p>Description: 对数据库进行增、删、改操作</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：$date$
 */


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;

// import com.sinosoft.lis.db.PD_LDCode1DB;
import java.lang.reflect.Method;

import com.sinosoft.productdef.PDRiskOperationTrackBL;

public class CommonBase {
private static Logger logger = Logger.getLogger(CommonBase.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	// private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 前台页面操作的表名，可能为空 */
	private String mTableName;

	private TransferData mTransferData = new TransferData();

	private ArrayList mList = new ArrayList();

	private ArrayList mListFiled = new ArrayList();

	private ArrayList mListFlag = new ArrayList();

	private ArrayList mListType = new ArrayList();
	/** 业务处理相关变量 */
	// private $tableName$Schema m$tableName$Schema=new $tableName$Schema();
	private MMap map = new MMap();

	private java.text.SimpleDateFormat tempDate = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");

	private String currDate = tempDate.format(new java.util.Date());

	private java.text.SimpleDateFormat tempTimeFormat = new java.text.SimpleDateFormat(
			"hh:mm:ss");

	private String currTime = tempTimeFormat.format(new java.util.Date());

	private ExeSQL exec = new ExeSQL();

	private Schema mSchema = null;

	private Object mObject = null;

	private String mIsSubmitData = "1";

	private VData mInputData = null;
	
	private Hashtable mHashtable = new Hashtable();

	public CommonBase() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!prepareSubmitData(cInputData, cOperate)) {
			CError tError = new CError();
			tError.moduleName = "CommonBase";
			tError.functionName = "prepareSubmitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();

		if (mIsSubmitData == null || mIsSubmitData.equals("1")) {
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "CommonBase";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		// mInputData = null;
		return true;
	}

	/**
	 * 准备数据，但不提交执行
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean prepareSubmitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mOperate = cOperate;
		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData(cOperate)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "$tableName$BL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if(!"Mulsave||MOD".equals(mOperate) && !"Mulsave||DEL".equals(mOperate)) {
		// 记录操作轨迹
			if ("update".equals(mOperate) || "del".equals(mOperate)) {
				PDRiskOperationTrackBL bl = new PDRiskOperationTrackBL();
				boolean flag = bl.submitData(mInputData, "");
				if (flag) {
					PD_LBRiskInfoSchema schema = (PD_LBRiskInfoSchema) bl.saveResult
							.getObjectByObjectName("PD_LBRiskInfoSchema", 0);
					schema.setStandByFlag50(mOperate);
					map.put(schema, "INSERT");
	
				}
			}
		}
		mResult.clear();
		mResult.addElement(map);

		// mInputData = null;
		return true;
	}

	private boolean check() {
		Class[] parameterC = new Class[1]; // 调用方法的参数数组
		Object[] parameterO = new Object[1]; // 调用方法的对象数组
		String keysSelect = "select fieldcode,isprimary from PD_BASEFIELD where isdisplay = 1 and tablecode = upper('?tablecode?') order by displayorder";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(keysSelect);
		sqlbv.put("tablecode", this.mTableName);
		SSRS ssrs = exec.execSQL(sqlbv);

		if (ssrs != null) {
			for (int i = 1; i <= ssrs.MaxRow; i++) {
				if (ssrs.GetText(i, 2).equals("1")) {
					this.mSchema.setV(ssrs.GetText(i, 1), String.valueOf(mList
							.get(i - 1)));
				}
			}

			try {
				parameterC[0] = mSchema.getClass();
				Method m = mObject.getClass()
						.getMethod("setSchema", parameterC);
				parameterO[0] = mSchema;
				m.invoke(mObject, parameterO);

				Method method = mObject.getClass().getMethod("getInfo", null);
				m = method;
				Boolean b = (Boolean) m.invoke(mObject, null);
				if (b.booleanValue()) {
					m = mObject.getClass().getMethod("getMakeDate", null);
					String tMakeDate = (String) m.invoke(mObject, null);
					this.mSchema.setV("MakeDate", tMakeDate);
					m = mObject.getClass().getMethod("getMakeTime", null);
					String tMakeTime = (String) m.invoke(mObject, null);
					this.mSchema.setV("MakeTime", tMakeTime);
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {
				logger.debug(ex.toString());
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		boolean tReturn = false;
		logger.debug("CommonBL begin...");
		logger.debug(" operator : " + cOperate);

		if ("Mulsave||MOD".equals(mOperate)) {
			if (mulInsert()) {
				return true;
			}
		} else if ("Mulsave||DEL".equals(mOperate)) {
			if (mulDelete()) {
				return true;
			}
		} else if ("save".equals(mOperate)) {
			if (insert()) {
				return true;
			}
		} else if ("update".equals(mOperate)) {
			if (update()) {
				return true;
			}
		} else if ("del".equals(mOperate)) {
			if (delete()) {
				return true;
			}
		}

		return tReturn;
	}
	private boolean mulDelete() {

		String tDutyCode = (String) mTransferData.getValueByName("DutyCode");
		String tPayPlanCode = (String) mTransferData.getValueByName("PayPlanCode");
		String tInitSql = "delete " + mTableName + " where DUTYCODE = '?tDutyCode?' and OTHERCODE = '?tPayPlanCode?' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tInitSql);
		sqlbv1.put("tDutyCode", tDutyCode);
		sqlbv1.put("tPayPlanCode", tPayPlanCode);
		if(!exec.execUpdateSQL(sqlbv1))
		{
			this.mErrors.addOneError("删除旧数据失败！");
			return false;
		}
		return true;
	}
	private boolean mulInsert() {

		if(!mulDelete()){
			return false;
		}
		String tDutyCode = (String) mTransferData.getValueByName("DutyCode");
		String tPayPlanCode = (String) mTransferData.getValueByName("PayPlanCode");
		String sql = "select fieldcode from PD_BASEFIELD where isdisplay = 1 and tablecode = upper('?tablecode?') order by displayorder ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("tablecode", this.mTableName);
		SSRS ssrs = exec.execSQL(sqlbv2);
		int rowCount = ssrs.MaxRow;
		try {
			Class tSchemaClass = Class.forName("com.sinosoft.lis.schema." + mTableName + "Schema");
			for (int i = 0; i< mListFiled.size(); i++){
				mSchema = (Schema) tSchemaClass.newInstance();
				for (int j = 1; j <= rowCount; j++) {
					String tFieldCode = ssrs.GetText(j, 1);
					if(tFieldCode.equals("DUTYCODE")) {
						this.mSchema.setV(tFieldCode, String.valueOf(tDutyCode));
					} else if (tFieldCode.equals("OTHERCODE")) {
						this.mSchema.setV(tFieldCode, String.valueOf(tPayPlanCode));
					} else if (tFieldCode.equals("FIELDNAME")) {
						this.mSchema.setV(tFieldCode, String.valueOf(mListFiled.get(i)));
					} else if (tFieldCode.equals("INPFLAG")) {
						this.mSchema.setV(tFieldCode, String.valueOf(mListFlag.get(i)));
					} else if (tFieldCode.equals("CTRLTYPE")) {
						this.mSchema.setV(tFieldCode, String.valueOf(mListType.get(i)));
					} else {
						this.mSchema.setV(tFieldCode, "");
					}
				}
				this.mSchema.setV("Operator", this.mGlobalInput.Operator);
				this.mSchema.setV("MakeDate", this.currDate);
				this.mSchema.setV("MakeTime", this.currTime);
				this.mSchema.setV("ModifyDate", this.currDate);
				this.mSchema.setV("ModifyTime", this.currTime);
				this.map.put(mSchema, "INSERT");
			}
		} catch (Exception ex) {
			logger.debug(ex.toString());
			return false;
		}
		return true;
	}
	private boolean insert() {
		if (check()) {
			this.mErrors.addOneError("数据库中存在该记录，不能再重复增加");
			return false;
		}

		String sql = "select fieldcode from PD_BASEFIELD where isdisplay = 1 and tablecode = upper('?tablecode?') order by displayorder ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("tablecode", this.mTableName);
		SSRS ssrs = exec.execSQL(sqlbv3);
		int rowCount = ssrs.MaxRow;
		for (int i = 1; i <= rowCount; i++) {
			this.mSchema.setV(ssrs.GetText(i, 1), String.valueOf(mList
					.get(i - 1)));
		}
		sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('?tablecode?') order by displayorder ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("tablecode", this.mTableName);
		ssrs = exec.execSQL(sqlbv4);
		rowCount = ssrs.MaxRow; 
		for (int i = 1; i <= rowCount; i++) {
			this.mSchema.setV(ssrs.GetText(i, 1), ssrs.GetText(i, 2));
		}
		if(this.mHashtable!=null){
			for   (Iterator   it   =   mHashtable.keySet().iterator();   it.hasNext();   )   {   
		          String key=(String)it.next();   
		          String value=(String)mHashtable.get(key); 
		          this.mSchema.setV(key, value);
			}   
		}
		this.mSchema.setV("Operator", this.mGlobalInput.Operator);
		this.mSchema.setV("MakeDate", this.currDate);
		this.mSchema.setV("MakeTime", this.currTime);
		this.mSchema.setV("ModifyDate", this.currDate);
		this.mSchema.setV("ModifyTime", this.currTime);
		this.map.put(mSchema, "INSERT");
		return true;
	}

	// 此种方式要求页面MulLine不需显示所有非空字段，否则会将非空字段set为null而报错，Insert同样
	private boolean update() {
		if (!check()) {
			this.mErrors.addOneError("待修改的数据不存在，不能修改");
			return false;
		}

		String sql = "select fieldcode from PD_BASEFIELD where isdisplay = 1 and tablecode = upper('?tablecode?') order by displayorder ";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("tablecode", this.mTableName);
		SSRS ssrs = exec.execSQL(sqlbv5);
		int rowCount = ssrs.MaxRow;
		for (int i = 1; i <= rowCount; i++) {
			this.mSchema.setV(ssrs.GetText(i, 1), String.valueOf(mList
					.get(i - 1)));
		}
		sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('?tablecode?') order by displayorder ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("tablecode", this.mTableName);
		ssrs = exec.execSQL(sqlbv6);
		rowCount = ssrs.MaxRow; 
		for (int i = 1; i <= rowCount; i++) {
			this.mSchema.setV(ssrs.GetText(i, 1), ssrs.GetText(i, 2));
		}
		if(this.mHashtable!=null){
			for   (Iterator   it   =   mHashtable.keySet().iterator();   it.hasNext();   )   {   
		          String key=(String)it.next();   
		          String value=(String)mHashtable.get(key); 
		          this.mSchema.setV(key, value);
			}   
		}
		this.mSchema.setV("Operator", this.mGlobalInput.Operator);
		this.mSchema.setV("MakeDate", this.currDate);
		this.mSchema.setV("MakeTime", this.currTime);
		this.mSchema.setV("ModifyDate", this.currDate);
		this.mSchema.setV("ModifyTime", this.currTime);
		this.map.put(mSchema, "UPDATE");

		return true;
	}

	private boolean delete() {
		if (!check()) {
			this.mErrors.addOneError("待删除的记录不存在，不能删除");
			return false;
		}

		this.map.put(this.mSchema, "DELETE");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mTableName = (String) mTransferData.getValueByName("tableName");
		mList = (ArrayList) mTransferData.getValueByName("list");
		mListFiled = (ArrayList) mTransferData.getValueByName("ListFiled");
		mListFlag = (ArrayList) mTransferData.getValueByName("ListFlag");
		mListType = (ArrayList) mTransferData.getValueByName("ListType");
		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		this.mIsSubmitData = (String) mTransferData
				.getValueByName("IsSubmitData");
		mHashtable = (Hashtable) mTransferData.getValueByName("Hashtable");

		try {
			Class tSchemaClass = Class.forName("com.sinosoft.lis.schema."
					+ mTableName + "Schema");
			mSchema = (Schema) tSchemaClass.newInstance();
			Class tDBClass = Class.forName("com.sinosoft.lis.db." + mTableName
					+ "DB");
			mObject = (Object) tDBClass.newInstance();
		} catch (Exception ex) {
			logger.debug(ex.toString());
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		CommonBase commonBL = new CommonBase();

		ArrayList array = new ArrayList();
		array.add("要素名3");
		array.add("代码");
		array.add("1");
		array.add("2");
		array.add("2");
		array.add("2");
		array.add("2");

		commonBL.mList = array;
		commonBL.mGlobalInput.Operator = "1";
		commonBL.mTableName = "PD_LDCode1";
		{
			commonBL.insert();
			commonBL.update();
			commonBL.delete();
		}
	}
}
