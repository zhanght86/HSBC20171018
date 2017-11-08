package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vschema.LCPremSet;
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
public class LCPremQueryBL {
private static Logger logger = Logger.getLogger(LCPremQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 保费项表 */
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	private LCPremSet mLCPremSet = new LCPremSet();

	public LCPremQueryBL() {
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
		if (!queryLJLCPrem())
			return false;
		logger.debug("---queryLJLCPrem---");

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
		mLCPremSchema.setSchema((LCPremSchema) cInputData
				.getObjectByObjectName("LCPremSchema", 0));

		if (mLCPremSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPremQueryBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 查询保费项表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLJLCPrem() {
		// 拼写SQL串,多条件查询
		String PolNo = mLCPremSchema.getPolNo();
		String UrgePayFlag = mLCPremSchema.getUrgePayFlag();
		String State = mLCPremSchema.getState();

		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setSchema(mLCPremSchema);

		if (State == null)
			State = "";
		if (State.equals("JSPayPer"))// 如果状态编码对应去应收个人交费表查询
		{
			String sqlStr = "select * from LCPrem ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			if (PolNo != null && PolNo != ""){
				sqlStr = sqlStr + " where PolNo='?PolNo?'";
				sqlbv.put("PolNo", PolNo);
			}
			if (UrgePayFlag != null && UrgePayFlag != ""){
				sqlStr = sqlStr + " and UrgePayFlag='?UrgePayFlag?'";
				sqlbv.put("UrgePayFlag", UrgePayFlag);
			}
			sqlStr = sqlStr
					+ " and PolNo not in(select PolNo from LJSPayPerson ) ";

			
			sqlbv.sql(sqlStr);
			mLCPremSet = tLCPremDB.executeQuery(sqlbv);
		} else { // 单表根据字段值去查询
			mLCPremSet = tLCPremDB.query();
		}
		if (tLCPremDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPremQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保费项表查询失败!";
			this.mErrors.addOneError(tError);
			mLCPremSet.clear();
			return false;
		}
		if (mLCPremSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPremQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCPremSet.clear();
			return false;
		}
		mResult.clear();
		mResult.add(mLCPremSet);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		try {
			mResult.add(mLCPremSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPremQueryBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
