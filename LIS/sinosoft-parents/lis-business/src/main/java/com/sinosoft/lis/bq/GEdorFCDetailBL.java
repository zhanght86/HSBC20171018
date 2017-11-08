package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单遗失补发项目明细</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Alex
 * @version 1.0
 */


import com.sinosoft.lis.db.LPGrpFreeEdorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPGrpFreeEdorSchema;
import com.sinosoft.lis.vschema.LPGrpFreeEdorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class GEdorFCDetailBL {
private static Logger logger = Logger.getLogger(GEdorFCDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpFreeEdorSchema mLPGrpFreeEdorSchema = new LPGrpFreeEdorSchema();

	public GEdorFCDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) // 数据提交
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "GEdorFCDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("GEdorFCDetailBL End PubSubmit");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPGrpFreeEdorSchema = (LPGrpFreeEdorSchema) mInputData
					.getObjectByObjectName("LPGrpFreeEdorSchema", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GEdorFCDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mGlobalInput == null || mLPGrpFreeEdorSchema == null) {
			CError tError = new CError();
			tError.moduleName = "GEdorFCDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		mLPGrpFreeEdorSchema.setOperator(mGlobalInput.Operator);
		mLPGrpFreeEdorSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpFreeEdorSchema.setModifyTime(PubFun.getCurrentTime());
		String tSql = "select * from LPGrpFreeEdor where 1=1"
				+ " and  EdorNo='" + "?EdorNo?" + "'"
				+ " and  EdorType='" + "?EdorType?" + "'"
				+ " and  GrpContNo='" + "?GrpContNo?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("EdorNo", mLPGrpFreeEdorSchema.getEdorNo());
		sqlbv.put("EdorType", mLPGrpFreeEdorSchema.getEdorType());
		sqlbv.put("GrpContNo", mLPGrpFreeEdorSchema.getGrpContNo());
		LPGrpFreeEdorDB tLPGrpFreeEdorDB = new LPGrpFreeEdorDB();
		LPGrpFreeEdorSet tLPGrpFreeEdorSet = new LPGrpFreeEdorSet();
		tLPGrpFreeEdorSet = tLPGrpFreeEdorDB.executeQuery(sqlbv);
		if (tLPGrpFreeEdorSet.size() == 1) {
			LPGrpFreeEdorSchema aSchema = tLPGrpFreeEdorSet.get(1);
			mLPGrpFreeEdorSchema.setMakeDate(aSchema.getMakeDate());
			mLPGrpFreeEdorSchema.setMakeTime(aSchema.getMakeTime());
			String aSql = "delete from LPGrpFreeEdor where" + " EdorNo ='"
					+ "?EdorNo?" + "'"
					+ " and EdorType='" + "?EdorType?"
					+ "'" + " and GrpContNo='"
					+ "?GrpContNo?" + "'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(aSql);
			sbv.put("EdorNo", mLPGrpFreeEdorSchema.getEdorNo());
			sbv.put("EdorType", mLPGrpFreeEdorSchema.getEdorType());
			sbv.put("GrpContNo", mLPGrpFreeEdorSchema.getGrpContNo());
			mMap.put(sbv, "DELETE");
		} else {
			mLPGrpFreeEdorSchema.setMakeDate(PubFun.getCurrentDate());
			mLPGrpFreeEdorSchema.setMakeTime(PubFun.getCurrentTime());
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareData() {
		mMap.put(mLPGrpFreeEdorSchema, "INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}
}
