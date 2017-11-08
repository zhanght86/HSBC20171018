package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class PrepareAutoDueFeeMultiBL {
private static Logger logger = Logger.getLogger(PrepareAutoDueFeeMultiBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private GlobalInput mGI = new GlobalInput();
	private LJSPaySet mLJSPaySet = new LJSPaySet();

	/** 数据操作字符串 */
	private String mOperate;

	private String startDate = "";
	private String endDate = "";
	private int succNum;
	private int failNum;
	private int totalNum;
	private String[] errContent;

	// 业务处理相关变量
	public PrepareAutoDueFeeMultiBL() {
	}

	public static void main(String[] args) {
		VData inputVData = new VData();
		String StartDate = "2003-07-01";
		String EndDate = "2003-11-19";

		GlobalInput tGI = new GlobalInput();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", StartDate);
		tTransferData.setNameAndValue("EndDate", EndDate);

		inputVData.add(tTransferData);
		inputVData.add(tGI);

		PrepareAutoDueFeeMultiBL tPrepareAutoDueFeeMultiBL = new PrepareAutoDueFeeMultiBL();
		tPrepareAutoDueFeeMultiBL.submitData(inputVData, "VERIFY");

	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		if (queryLDSysVar() == false)
			return false;
		if (queryLJSPay() == false)
			return false;
		if (autoVerify() == false)
			return false;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		// 应收总表
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		mGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		if (tTransferData == null || mGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PrepareAutoDueFeeMultiBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// startDate=(String)tTransferData.getValueByName("StartDate");
		// endDate=(String)tTransferData.getValueByName("EndDate");
		// if(startDate==null||endDate==null)
		// {
		// CError tError =new CError();
		// tError.moduleName="PrepareAutoDueFeeMultiBL";
		// tError.functionName="getInputData";
		// tError.errorMessage="没有得到日期，请您确认!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		mInputData = new VData();
		return true;
	}

	/**
	 * 公共核销程序
	 * 
	 * @param TempFeeNo
	 *            暂交费号
	 * @return 包含纪录的集合(纪录如何处理具体见prepareOutputData函数)
	 */
	public VData ReturnData(String TempFeeNo) {
		return mInputData;
	}

	// 查询应收总表
	private boolean queryLJSPay() {
		LJSPayDB tLJSPayDB = new LJSPayDB();
		String sqlStr = "select * from LJSPay where 1=1 ";
		// sqlStr=sqlStr+" and PayDate>='"+startDate+"'";
		// sqlStr=sqlStr+" and PayDate<='"+endDate+"'";
		sqlStr = sqlStr + " and OtherNoType='2'";
		sqlStr = sqlStr + " and SumDuePayMoney=0";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlStr);

		mLJSPaySet = tLJSPayDB.executeQuery(sqlbv);
		if (tLJSPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "queryMultLJVerDuePayFee";
			tError.errorMessage = "应收总表查询失败!";
			this.mErrors.addOneError(tError);
			mLJSPaySet.clear();
			return false;
		}
		if (mLJSPaySet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "queryMultLJVerDuePayFee";
			tError.errorMessage = "没有符合条件的数据!";
			this.mErrors.addOneError(tError);
			mLJSPaySet.clear();
			return false;
		}

		return true;
	}

	// 批量核销
	private boolean autoVerify() {
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		VData tVData = new VData();
		totalNum = mLJSPaySet.size();
		String[] Content = new String[totalNum];
		for (int n = 1; n <= mLJSPaySet.size(); n++) {
			tVData.clear();
			tLJSPaySchema = mLJSPaySet.get(n);
			tVData.add(tLJSPaySchema);
			tVData.add(mGI);
			AutoDueFeeMultiBL tAutoDueFeeMultiBL = new AutoDueFeeMultiBL();
			tAutoDueFeeMultiBL.submitData(tVData, "VERIFY");
			if (tAutoDueFeeMultiBL.mErrors.needDealError() == true) {
				Content[failNum] = "应收总表(通知书号" + tLJSPaySchema.getGetNoticeNo()
						+ ")发生错误:" + tAutoDueFeeMultiBL.mErrors.getFirstError();
				failNum = failNum + 1;
				continue;
			}
			succNum = succNum + 1;
		}
		errContent = new String[failNum];
		for (int i = 0; i < failNum; i++) {
			errContent[i] = Content[i];
		}

		// test(errContent);
		return true;
	}

	public VData getResult() {
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		String strtotalNum = String.valueOf(totalNum);
		String strsuccNum = String.valueOf(succNum);
		String strfailNum = String.valueOf(failNum);
		String content = "";
		for (int i = 0; i < errContent.length; i++) {
			content = content + errContent[i];
		}
		tTransferData.setNameAndValue("recordCount", strtotalNum);
		tTransferData.setNameAndValue("succNum", strsuccNum);
		tTransferData.setNameAndValue("failNum", strfailNum);
		tTransferData.setNameAndValue("errContent", content);
		tVData.add(tTransferData);
		return tVData;
	}

	private boolean queryLDSysVar() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("AutoVerifyStation");
		if (tLDSysVarDB.getInfo() == false) {
			this.mErrors.copyAllErrors(tLDSysVarDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "queryLDSysVar";
			tError.errorMessage = "系统变量表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLDSysVarDB.getSysVarValue() == null
				|| !tLDSysVarDB.getSysVarValue().equals("1")) {
			this.mErrors.copyAllErrors(tLDSysVarDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "queryLDSysVar";
			tError.errorMessage = "系统变量表中核销位置不匹配!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean test(String[] t) {
		TransferData temp = new TransferData();
		temp.setNameAndValue("array", t);
		String t1[] = (String[]) temp.getValueByName("array");

		return true;
	}

}
