package com.sinosoft.lis.sys;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOBonusMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOBonusMainSchema;
import com.sinosoft.lis.vschema.LOBonusMainSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class BonusCheckBL implements BusinessService {
	private static Logger logger = Logger.getLogger(BonusCheckBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LOBonusMainSchema mLOBonusMainSchema = new LOBonusMainSchema();

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	public BonusCheckBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!checkDate()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BonusCheckBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败BonusCheckBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		mInputData = null;
		mResult.clear();
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL校验 很多校验已经在前台实现，这里只对日期进行校验 如果在处理过程中出错，则返回false,否则返回true
	 */

	private boolean checkDate() {

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (mLOBonusMainSchema != null) {
			LOBonusMainSchema tLOBonusMainSchema = new LOBonusMainSchema();
			LOBonusMainSet tLOBonusMainSet = new LOBonusMainSet();
			LOBonusMainDB tLOBonusMainDB = new LOBonusMainDB();
			if ("update".equals(mOperate)) {
				tLOBonusMainDB
						.setFiscalYear(mLOBonusMainSchema.getFiscalYear());
				tLOBonusMainDB.setGroupID(mLOBonusMainSchema.getGroupID());
				tLOBonusMainSet = tLOBonusMainDB.query();
				if (tLOBonusMainSet.size() < 1) {
					CError tError = new CError();
					tError.moduleName = "BonusCheckBL";
					tError.functionName = "submitData";
					tError.errorMessage = "没有相关数据";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					tLOBonusMainSchema = tLOBonusMainSet.get(1);
					tLOBonusMainSchema.setOperator(mGlobalInput.Operator);
					tLOBonusMainSchema.setModifyDate(tCurMakeDate);
					tLOBonusMainSchema.setModifyTime(tCurMakeTime);
					tLOBonusMainSchema.setState("1");
					map.put(tLOBonusMainSchema, "UPDATE");
				}
				// 校验完毕
				String tUPDateSQL = "update LOBonusMain set ComputeState='4',ModifyDate='"
						+ "?tCurMakeDate?"
						+ "',ModifyTime='"
						+ "?tCurMakeTime?"
						+ "'"
						+ " where GroupID='1' and FiscalYear="
						+ "?tLOBonusMainSchema?";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tUPDateSQL);
				sqlbv.put("tCurMakeDate", tCurMakeDate);
				sqlbv.put("tCurMakeTime", tCurMakeTime);
				sqlbv.put("tLOBonusMainSchema", tLOBonusMainSchema.getFiscalYear());
				map.put(sqlbv, "UPDATE");
			}
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLOBonusMainSchema = ((LOBonusMainSchema) cInputData
				.getObjectByObjectName("LOBonusMainSchema", 0));
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mResult.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BonusCheckBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			CError tError = new CError();
			tError.moduleName = "BonusCheckBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "数据提交失败。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
