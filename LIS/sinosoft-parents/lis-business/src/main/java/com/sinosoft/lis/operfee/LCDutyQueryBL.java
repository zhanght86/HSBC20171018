package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单查询业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class LCDutyQueryBL {
private static Logger logger = Logger.getLogger(LCDutyQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 保险责任表 */
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LCDutySet mLCDutySet = new LCDutySet();

	public LCDutyQueryBL() {
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
		if (!getInputData(cInputData))
			return false;
		logger.debug("---getInputData---");

		// 进行业务处理
		if (!queryLCDuty()) {
			logger.debug("-in BL 查询 失败---");
			return false;
		}
		logger.debug("---queryLCDuty---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		mLCDutySchema.setSchema((LCDutySchema) cInputData
				.getObjectByObjectName("LCDutySchema", 0));

		if (mLCDutySchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCDutyQueryBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 查询保险责任表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLCDuty() {
		// 拼写SQL串,多条件查询
		String PolNo = mLCDutySchema.getPolNo();
		String DutyCode = mLCDutySchema.getDutyCode();
		String sqlStr = "select * from LCDuty where PolNo='?PolNo?' and DutyCode='?DutyCode?'";
		sqlStr = sqlStr
				+ " and (FreeFlag!='1' or FreeFlag is null) and FreeRate<1 ";

		logger.debug("in BL SQL=" + sqlStr);

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setSchema(mLCDutySchema);

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlStr);
		sqlbv.put("PolNo", PolNo);
		sqlbv.put("DutyCode", DutyCode);
		mLCDutySet = tLCDutyDB.executeQuery(sqlbv);
		if (tLCDutyDB.mErrors.needDealError() == true) {
			logger.debug("---in BL queryLCDuty():保险责任表查询失败---");
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCDutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保险责任表查询失败!";
			this.mErrors.addOneError(tError);
			mLCDutySet.clear();
			return false;
		}
		if (mLCDutySet.size() == 0) {
			logger.debug("---in BL queryLCDuty():mLCDutySet.size() == 0---");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCDutyQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCDutySet.clear();
			return false;
		}
		logger.debug("Success");
		mResult.clear();
		mResult.add(mLCDutySet);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		try {
			mResult.add(mLCDutySet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCDutyQueryBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
