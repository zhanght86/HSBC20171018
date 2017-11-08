/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 简易案件判断类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */

public class LLClaimSimpleSetBL {
private static Logger logger = Logger.getLogger(LLClaimSimpleSetBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap map = new MMap();
	/** 往后面传输的数据库操作 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	// 全局变量
	private String mClmNo = "";
	private String mRgtState = "";
	private String mRgtDate = "";
	private String mSimpleFlag = "";// 是否简易案件,0不是1是
	private String mAdjPay = "";

	public LLClaimSimpleSetBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimSimpleSetBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		mInputData = null;
		logger.debug("----------LLClaimSimpleSetBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		this.mOperate = cOperate;

		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mAdjPay = (String) mTransferData.getValueByName("adjpay");

		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据[操作员编码]失败!");
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		// ----------------------------------------------------------------------BEG
		// No.1： 判定是否已经设置案件类型,如是系统不必进行进一步判定
		// 所需变量：mClmNo
		// 更新变量：
		// ----------------------------------------------------------------------
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterDB.setRgtNo(mClmNo);
		tLLRegisterDB.getInfo();
		
		tLLRegisterSchema = tLLRegisterDB.getSchema();
		mRgtState = tLLRegisterSchema.getRgtState();
//		if (mRgtState != null) {
//			logger.debug("案件类型判断:非简易案件,因为用户已经设置案件类型!");
//			mSimpleFlag = "0";
//			return true;
//		}
		
		// 获得立案时间,为No.5准备
		mRgtDate = tLLRegisterSchema.getRgtDate();
		if (mRgtDate == null || mRgtDate.equals("")) {
			// @@错误处理
			CError.buildErr(this, "查询立案时间失败!");
			return false;
		}
		
		// ----------------------------------------------------------------------END
		// ----------------------------------------------------------------------BEG
		// No.2： 判定在报案阶段没有发起调查或呈报,如是系统不必进行进一步判定
		// 所需变量：mClmNo
		// 更新变量：
		// ----------------------------------------------------------------------
		String tSelectSql1 = "";
		tSelectSql1 = " select sum(s) from ("
			        + " select count(*) s from LLInqApply where 1=1 "+ " and ClmNo = '" + "?ClmNo?" + "'"
			        + " union all"
			        + " select count(*) s from LLSubmitApply where 1=1 "+ " and ClmNo = '" + "?ClmNo?" + "'"
			        + " ) g";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSelectSql1);
		sqlbv.put("ClmNo", mClmNo);
		logger.debug("--判定在报案阶段没有发起调查或呈报sql:"+tSelectSql1);
		ExeSQL tExeSQL = new ExeSQL();
		int tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));

		if (tCount>0) {
			logger.debug("案件类型判断:非简易案件,因为已经发起调查或呈报!");
			mSimpleFlag = "0";
			return true;
		}
		
		// ----------------------------------------------------------------------END
		// ----------------------------------------------------------------------BEG
		// No.3： 赔案的案件的理赔类型必须是医疗类,且仅有医疗类，否则不符合条件,按正常案件处理
		// 所需变量：mClmNo
		// 更新变量：
		// ----------------------------------------------------------------------
		LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
		LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet();
		tLLAppClaimReasonDB.setCaseNo(mClmNo);
		tLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
		if (tLLAppClaimReasonSet == null || tLLAppClaimReasonSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "查询理赔类型信息失败,"+tLLAppClaimReasonDB.mErrors.getLastError());
			return false;
		}
		
		boolean tIsNo = false;
		for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
			String tReasonCode = tLLAppClaimReasonSet.get(j).getReasonCode()
					.substring(1, 3);
			if (!tReasonCode.equals("00")) {
				tIsNo = true;
				break;
			}
		}
		
		if (tIsNo) {
			logger.debug("案件类型判断:非简易案件,因为理赔类型不是“医疗类”!");
			mSimpleFlag = "0";
			return true;
		}
		
		// ----------------------------------------------------------------------END
		// ----------------------------------------------------------------------BEG
		// No.4： 系统用“给付金额调整”字段数值与系统设置的简易案件标准匹配判断，小于等于该
		// 标准的案件，被判定为简易案件。
		// 所需变量：mClmNo
		// 更新变量：
		// ----------------------------------------------------------------------
		String tComCode = ""; // 登陆机构6位
		if (mGlobalInput.ComCode.length() == 8) {
			tComCode = mGlobalInput.ComCode.substring(0, 6);
		} else if (mGlobalInput.ComCode.length() == 6) {
			tComCode = mGlobalInput.ComCode.substring(0, 6);
		} else if (mGlobalInput.ComCode.length() == 4) {
			tComCode = mGlobalInput.ComCode.substring(0, 4);
		}

		
		tCount = 0;
		String tSelectSql2 = "select count(1) from LLParaClaimSimple where 1=1 "
				+ " and comcode = '" + "?comcode?" + "'"
				+ " and basemin < " + "?mAdjPay?" + " and basemax >= " + "?mAdjPay?";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSelectSql2);
		sqlbv1.put("comcode", tComCode);
		sqlbv1.put("mAdjPay", Double.parseDouble(mAdjPay));
		logger.debug("--查询机构的简易案件处理金额sql:"+tSelectSql2);
		
		tCount=Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		
		if (tCount==0) {
			logger.debug("案件类型判断:非简易案件,因为不符合简易案件标准!");
			mSimpleFlag = "0";		
			return true;
		}
		
		// ----------------------------------------------------------------------END
		// ----------------------------------------------------------------------BEG
		// No.5： 从当前立案日期起向前计算，此出险人一年内不能有超过三个结案的简易案件
		// 所需变量：mClmNo,mRgtDate
		// 更新变量：
		// ----------------------------------------------------------------------
		LLCaseDB tLLCaseDB = new LLCaseDB();
		LLCaseSet tLLCaseSet = new LLCaseSet();
		tLLCaseDB.setCaseNo(mClmNo);
		tLLCaseSet = tLLCaseDB.query();
		if (tLLCaseSet == null || tLLCaseSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "查询分案信息失败,"+tLLCaseDB.mErrors.getLastError());
			return false;
		}
		
		for (int j = 1; j <= tLLCaseSet.size(); j++) {
			
			tCount=0;

            String tYearString = mRgtDate.substring(0, 4);
            int tYearInt = Integer.parseInt(tYearString) - 1;
            String mLastDate = String.valueOf(tYearInt)+mRgtDate.substring(4)+" 00:00:00";//立案日期往前推一年

			logger.debug("立案日期:"+mRgtDate+",上一年的立案日期:"+mLastDate);
			String tSelectSql3=" select count(*) from llregister"
			        +" where exists(select 1 from llcase where llcase.rgtno=llregister.rgtno and  customerno ='"+"?customerno?"+"')"
			        +" and Rgtno!='"+"?Rgtno?"+"'"
			        +" and RgtDate >=to_date('"+"?date1?"+"','yyyy-mm-dd hh24:mi:ss')"
			        +" and RgtDate <=to_date('"+"?date2?"+" 00:00:00','yyyy-mm-dd hh24:mi:ss')"
			        +" and RgtState='01'";
			
			logger.debug("--判断在立案日期追溯一年内有至少三次的理赔案件时本次案件不能立为简易案件的SQL:"+tSelectSql3);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSelectSql3);
			sqlbv2.put("customerno", tLLCaseSet.get(j).getCustomerNo());
			sqlbv2.put("Rgtno", tLLCaseSet.get(j).getCaseNo());
			sqlbv2.put("date1", mLastDate);
			sqlbv2.put("date2", mRgtDate);
			tCount=Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		
			if (tCount >= 3) {
				logger.debug("案件类型判断:非简易案件,因为从当前立案日期起向前计算一年内已经存在三个结案的简易案件!");
				mSimpleFlag = "0";
				return true;
			}

			tSelectSql3=null;
			mLastDate=null;
			tYearString=null;
		}
		// ----------------------------------------------------------------------END

		// 排除以上情况,可认定案件为简易案件
		logger.debug("案件类型判断:此案件为简易案件!");
		mSimpleFlag = "1";
		
		//释放强引用
		tSelectSql2=null;
		tLLCaseSet=null;
		tLLCaseDB=null;

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);

			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SimpleFlag", mSimpleFlag);
			mResult.add(tTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimSimpleSetBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
		LLClaimSimpleSetBL mLLClaimSimpleSetBL = new LLClaimSimpleSetBL();
		mLLClaimSimpleSetBL.dealData();
	}
}
