/*
 * <p>ClassName: OLDUWUserBL </p>
 * <p>Description: OLDUWUserBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDUWAmntGradeSchema;
import com.sinosoft.lis.schema.LDUWGradeSchema;
import com.sinosoft.lis.schema.LDUWUserSchema;
import com.sinosoft.lis.vschema.LDUWAmntGradeSet;
import com.sinosoft.lis.vschema.LDUWGradeSet;
import com.sinosoft.lis.vschema.LDUWUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class OLDEdorUserBL {
private static Logger logger = Logger.getLogger(OLDEdorUserBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LDUWUserSchema mLDUWUserSchema = new LDUWUserSchema();
	private LDUWUserSchema mLDUWUserSchema1 = new LDUWUserSchema();
	private LDUWGradeSet mLDUWGradeSet = new LDUWGradeSet();
	private LDUWAmntGradeSet mLDUWAmntGradeSet = new LDUWAmntGradeSet();
	private LDUWAmntGradeSet mmLDUWAmntGradeSet = new LDUWAmntGradeSet();
	// private LDUWAmntGradeSet mmmLUWAmntGradeSet = new LDUWAmntGradeSet();
	private LDUWGradeSet mmLDUWGradeSet = new LDUWGradeSet();

	// private LDUWGradeDBSet mLDUWGradeDBSet = new LDUWGradeDBSet(); //private
	// LDUWUserSet mLDUWUserSet=new LDUWUserSet();
	public OLDEdorUserBL() {
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
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLDUWUserBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLDUWUserBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start OLDUWUserBL Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "OLDUWUserBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---commitData---");

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		// tLDUWUserDB.setUserCode(mLDUWUserSchema.getUserCode());

		logger.debug("tLDUWUserDB.setUserCode(mLDUWUserSchema.getUserCode()) :"
						+ mLDUWUserSchema.getUserCode());
		// if (!tLDUWUserDB.getInfo()) //验证LDUWUser表中是否存在该合同项记录
		if (mLDUWUserSchema.getUserCode() == null) {
			CError tError = new CError();
			tError.moduleName = "dealData";
			tError.functionName = "dealData";
			tError.errorMessage = "核保师不存在";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (this.mOperate.equals("INSERT||MAIN")) {
			LDUWUserDB tLDUWUserDB = new LDUWUserDB();
			tLDUWUserDB.setUserCode(mLDUWUserSchema.getUserCode());
			tLDUWUserDB.setUWType(mLDUWUserSchema.getUWType());
			tLDUWUserDB.setUWPopedom(mLDUWUserSchema.getUWPopedom());
			tLDUWUserDB.getInfo();
			logger.debug(mLDUWUserSchema.getUserCode() + "  bbbbb");
			logger.debug(tLDUWUserDB.getCount() + "     aaaaaa");
			if (tLDUWUserDB.getCount() > 0) {
				CError tError = new CError();
				tError.moduleName = "dealData";
				tError.functionName = "dealData";
				tError.errorMessage = "该核保师记录已经存在！不能再次增加！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLDUWUserSchema.setManageCom(mGlobalInput.ManageCom);
			mLDUWUserSchema.setOperator(mGlobalInput.Operator);
			logger.debug("mLDUWUserSchema.getManageCom() :"
					+ mLDUWUserSchema.getManageCom());
			logger.debug("mLDUWUserSchema.getUWType() :"
					+ mLDUWUserSchema.getUWType());

			mLDUWUserSchema.setMakeDate(PubFun.getCurrentDate());
			logger.debug("mLDUWUserSchema.setMakeDate(PubFun.getCurrentDate()) :"
							+ PubFun.getCurrentDate());

			mLDUWUserSchema.setMakeTime(PubFun.getCurrentTime());
			mLDUWUserSchema.setModifyDate(PubFun.getCurrentDate());
			mLDUWUserSchema.setModifyTime(PubFun.getCurrentTime());
			LDUWUserSet mLDUWUserSet = new LDUWUserSet();
			mLDUWUserSet.add(mLDUWUserSchema);
			map.put(mLDUWUserSet, "INSERT");
		}

		if (mOperate.equals("UPDATE||MAIN")) {
			String Sql1 = "delete from LDUWUser where UserCode='"
					+ "?UserCode?" + "'" + " and UWType='"
					+ "?UWType?" + "' and UWPopedom='"
					+ "?UWPopedom?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(Sql1);
			sqlbv1.put("UserCode",mLDUWUserSchema1.getUserCode());
			sqlbv1.put("UWType",mLDUWUserSchema1.getUWType());
			sqlbv1.put("UWPopedom",mLDUWUserSchema1.getUWPopedom());
			map.put(sqlbv1, "DELETE");
			
			mLDUWUserSchema.setManageCom(mGlobalInput.ManageCom);
			mLDUWUserSchema.setOperator(mGlobalInput.Operator);
			logger.debug("mLDUWUserSchema.getManageCom() :"
					+ mLDUWUserSchema.getManageCom());
			logger.debug("mLDUWUserSchema.getUWType() :"
					+ mLDUWUserSchema.getUWType());

			mLDUWUserSchema.setMakeDate(PubFun.getCurrentDate());
			logger.debug("mLDUWUserSchema.setMakeDate(PubFun.getCurrentDate()) :"
							+ PubFun.getCurrentDate());

			mLDUWUserSchema.setMakeTime(PubFun.getCurrentTime());
			mLDUWUserSchema.setModifyDate(PubFun.getCurrentDate());
			mLDUWUserSchema.setModifyTime(PubFun.getCurrentTime());
			LDUWUserSet mLDUWUserSet = new LDUWUserSet();
			mLDUWUserSet.add(mLDUWUserSchema);
			map.put(mLDUWUserSet, "INSERT");

		}

		if (this.mOperate.equals("DELETE||MAIN")) {
			map.put(mLDUWUserSchema, "DELETE");
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean deleteData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			this.mLDUWUserSchema = ((LDUWUserSchema) cInputData.get(0));
			this.mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			if (mOperate.equals("UPDATE||MAIN")) {
				this.mLDUWUserSchema1 = ((LDUWUserSchema) cInputData.get(1));
			}
			logger.debug(mGlobalInput.Operator);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "dealData";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据读取错误！" + ex.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		// LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		// tLDUWUserDB.setSchema(this.mLDUWUserSchema);
		// 如果有需要处理的错误，则返回
		if (mLDUWUserSchema.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(mLDUWUserSchema.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDUWUserBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			// this.mInputData.clear();
			// this.mInputData.add(this.mLDUWUserSchema);
			// mInputData.add(map);
			mResult.clear();
			// mResult.add(this.mLDUWUserSchema);
			mResult.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWUserBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
