package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
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
public class TempFeeQueryForUrgeGetBL {
private static Logger logger = Logger.getLogger(TempFeeQueryForUrgeGetBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 暂交费表 */
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();

	private String CurrentDate = PubFun.getCurrentDate();

	public TempFeeQueryForUrgeGetBL() {
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
		if (!queryLJTempFee())
			return false;
		logger.debug("---queryLJTempFee---");

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
		mLJTempFeeSchema.setSchema((LJTempFeeSchema) cInputData
				.getObjectByObjectName("LJTempFeeSchema", 0));

		if (mLJTempFeeSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 查询暂交费表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLJTempFee() {
		// 保单信息
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeDB.setSchema(mLJTempFeeSchema);

		String TempFeeNo = mLJTempFeeSchema.getTempFeeNo();
		String TempFeeType = mLJTempFeeSchema.getTempFeeType();
		String OtherNo = mLJTempFeeSchema.getOtherNo();
		String OtherNoType = mLJTempFeeSchema.getOtherNoType();
		String StartDate = mLJTempFeeSchema.getEnterAccDate();
		String EndDate = mLJTempFeeSchema.getConfDate();
		String ConfFlag = mLJTempFeeSchema.getConfFlag();

		logger.debug("TempFeeNo:" + TempFeeNo);
		logger.debug("ConfFlag:" + ConfFlag);

		if (TempFeeNo == null) {
			String sqlStr = "select * from LJTempFee where 1=1 and enteraccdate is not null and enteraccdate <> '3000-1-1'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			if (TempFeeType != null){
				sqlStr = sqlStr + " and TempFeeType='?TempFeeType?'";
			    sqlbv.put("TempFeeType", TempFeeType);
			}
			if (OtherNo != null){
				sqlStr = sqlStr + " and OtherNo='?OtherNo?'";
			    sqlbv.put("OtherNo", OtherNo);
			}
			if (OtherNoType != null){
				sqlStr = sqlStr + " and OtherNoType='?OtherNoType?'";
				 sqlbv.put("OtherNoType", OtherNoType);
			}
			if (StartDate != null){
				sqlStr = sqlStr + " and PayDate>='?StartDate?'";
				sqlbv.put("StartDate", StartDate);
			}
			if (EndDate != null){
				sqlStr = sqlStr + " and PayDate<='?EndDate?'";
				sqlbv.put("EndDate", EndDate);
			}
			if (ConfFlag != null) {
				if (ConfFlag.equals("0"))
					sqlStr = sqlStr
							+ " and (ConfFlag='0' or ConfFlag is null) ";
				if (ConfFlag.equals("1"))
					sqlStr = sqlStr + " and ConfFlag='1' ";
			}
			logger.debug("sqlStr=" + sqlStr);
			sqlbv.sql(sqlStr);
			mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
		} 
		else 
		{
			mLJTempFeeSet = tLJTempFeeDB.query();
		}
		if (tLJTempFeeDB.mErrors.needDealError() == true) 
		{
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "TempFeeQueryForUrgeGetBL";
			tError.functionName = "queryData";
			tError.errorMessage = "暂交费查询失败!";
			this.mErrors.addOneError(tError);
			mLJTempFeeSet.clear();
			return false;
		}

		if (mLJTempFeeSet.size() == 0) 
		{
			//判断是否应收为0
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setOtherNo(OtherNo);
			tLJSPayDB.setOtherNoType("2");
			LJSPaySet tLJSPaySet = new LJSPaySet();
			tLJSPaySet = tLJSPayDB.query();
			if (tLJSPaySet.size() <= 0) 
			{
				CError tError = new CError();
				tError.moduleName = "TempFeeQueryForUrgeGetBL";
				tError.functionName = "queryData";
				tError.errorMessage = "未找到应收数据!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
			tLJSPaySchema = tLJSPaySet.get(1);

			if(tLJSPaySchema.getSumDuePayMoney()==0)
			{
				// 为预交续期保费生成一条虚拟数据
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema.setOtherNo(OtherNo);
				//tLJTempFeeSchema.setPayMoney(tLCPolSchema.getLeavingMoney());
				tLJTempFeeSchema.setEnterAccDate(CurrentDate);
				tLJTempFeeSchema.setOtherNoType("00");
				tLJTempFeeSchema.setTempFeeNo(tLJSPaySchema.getGetNoticeNo());
				tLJTempFeeSchema.setPayMoney(tLJSPaySchema.getSumDuePayMoney());
				tLJTempFeeSchema.setCurrency(tLJSPaySchema.getCurrency());
				mLJTempFeeSet.add(tLJTempFeeSchema);
			}
			else
			{
				this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "TempFeeQueryForUrgeGetBL";
				tError.functionName = "queryData";
				tError.errorMessage = "暂交费查询失败!";
				this.mErrors.addOneError(tError);
				mLJTempFeeSet.clear();
				return false;
			}
		}

		mResult.add(mLJTempFeeSet);
		return true;

	}
}
