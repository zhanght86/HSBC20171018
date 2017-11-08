package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
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
public class LJAGetQueryBL {
private static Logger logger = Logger.getLogger(LJAGetQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 暂交费表 */
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public LJAGetQueryBL() {
	}

	public static void main(String[] args) {
		String OtherNo = "";
		OtherNo = "00000000222916";
		String ActuGetNo = "";

		LJAGetSchema tLJAGetSchema; // 实付总表
//		LJAGetSet tLJAGetSet;

		// 2-查询实付总表
		tLJAGetSchema = new LJAGetSchema();
		if (OtherNo != null && OtherNo != "")
			tLJAGetSchema.setOtherNo(OtherNo);
		if (ActuGetNo != null && !ActuGetNo.equals(""))
			tLJAGetSchema.setActuGetNo(ActuGetNo);
		tLJAGetSchema.setEnterAccDate("");
		VData tVData = new VData();
		tVData.add(tLJAGetSchema);
		logger.debug("Start 查询实付总表");
		LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
		if (!tLJAGetQueryUI.submitData(tVData, "QUERY")) {
			logger.debug("EEROR");
		}
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
		if (!queryLJAGet())
			return false;
		logger.debug("---queryLJAGet---");

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
		mLJAGetSchema.setSchema((LJAGetSchema) cInputData
				.getObjectByObjectName("LJAGetSchema", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mLJAGetSchema == null) {
			// @@错误处理
			CError.buildErr(this, "请输入查询条件!");
			return false;
		}
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "操作人员数据传输失败，请重新登录!");
			return false;
		}
		return true;
	}

	/**
	 * 查询暂交费表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLJAGet() {
		// 保单信息
		LJAGetDB tLJAGetDB = new LJAGetDB();
		logger.debug("开始查询！！！！！！");
		// tLJAGetDB.setSchema(mLJAGetSchema);
		String strSql = "select * from ljaget where EnterAccDate is null and ConfDate is null  and (bankonthewayflag='0' or bankonthewayflag is null) "
			          + "and managecom like concat('?managecom?','%')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.put("managecom", mGlobalInput.ManageCom);
		if (mLJAGetSchema.getActuGetNo() != null
				&& !mLJAGetSchema.getActuGetNo().equals("")){
			strSql = strSql + " and ActuGetNo='?ActuGetNo?'";
		sqlbv.put("ActuGetNo", mLJAGetSchema.getActuGetNo());
		}
		if (mLJAGetSchema.getOtherNo() != null
				&& !mLJAGetSchema.getOtherNo().equals("")){
			strSql = strSql + " and OtherNo='?OtherNo?'";
			sqlbv.put("OtherNo", mLJAGetSchema.getOtherNo());
		}

		logger.debug(strSql);
		// mLJAGetSet = tLJAGetDB.query();
		sqlbv.sql(strSql);
		mLJAGetSet = tLJAGetDB.executeQuery(sqlbv);
		if (tLJAGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetDB.mErrors);
			CError.buildErr(this, "实付查询失败!");
			mLJAGetSet.clear();
			return false;
		}
		if (mLJAGetSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据");
			mLJAGetSet.clear();
			return false;
		}
		for(int  i=1;i<=mLJAGetSet.size();i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema = mLJAGetSet.get(i);
			if (tLJAGetSchema.getEnterAccDate() != null
					&& tLJAGetSchema.getEnterAccDate() != "") {
				// @@错误处理
				CError.buildErr(this, "该数据已给付核销");
				mLJAGetSet.clear();
				return false;
			}
			if ("1".equals(tLJAGetSchema.getBankOnTheWayFlag())) {
					// @@错误处理
					CError.buildErr(this, "在送银行途中");
					mLJAGetSet.clear();
					return false;
				
			}
		}
		logger.debug("LJAGet num=" + mLJAGetSet.size());
		mResult.clear();
		mResult.add(mLJAGetSet);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
//	private boolean prepareOutputData() {
//		mResult.clear();
//		try {
//			mResult.add(mLJAGetSet);
//		} catch (Exception ex) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "LJAGetQueryBL";
//			tError.functionName = "prepareOutputData";
//			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		return true;
//	}
}
