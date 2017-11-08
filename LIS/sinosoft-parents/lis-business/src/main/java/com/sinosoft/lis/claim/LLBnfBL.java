/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
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
 * Description: 理赔立案结论逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLBnfBL {
private static Logger logger = Logger.getLogger(LLBnfBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mG = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private LLBnfSet mLLBnfSet = new LLBnfSet();// 受益人明细集合

	private String mPolNo = "";

	private String mClmNo = "";

	private String mBnfKind = "";

	private String mCustomerNo = "";

	private String mFeeOperationType = "";

	private double mBalancePay = 0.0;// 本次案件总分配金额(结算金额+合同处理金额+理算金额)

	public LLBnfBL() {
	}

	public static void main(String[] args) {
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
		logger.debug("----------LLBnfBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after checkInputData----------");

		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLBnfBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after prepareOutputData----------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			CError.buildErr(this, "数据提交失败," + tPubSubmit.mErrors.getLastError());
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		mLLBnfSet = (LLBnfSet) mInputData.getObjectByObjectName("LLBnfSet", 0);

		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		mBnfKind = (String) mTransferData.getValueByName("BnfKind");
		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		mFeeOperationType = (String) mTransferData.getValueByName("FeeOperationType");

		return true;
	}

	/**
	 * 校验传入的信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		if (mClmNo == null || mClmNo.equals("")) {
			CError.buildErr(this, "传入的赔案号为空!");
			return false;
		}

		if (mCustomerNo == null || mCustomerNo.equals("")) {
			CError.buildErr(this, "传入的客户号为空!");
			return false;
		}

		if (mBnfKind == null || mBnfKind.equals("")) {
			CError.buildErr(this, "传入的受益人分配类型为空!");
			return false;
		}

		String strSQL = "";
		ExeSQL tExeSQL = new ExeSQL();

		if (mBnfKind.equals("B")) {
			strSQL = "select (case when abs(sum(pay)) is null then 0 else abs(sum(pay)) end) from LLBalance a where 1=1 "
					+ " and Feeoperationtype='B'" + " and clmno='" + "?clmno?" + "'";
		} else {
			strSQL = "select (case when sum(pay) is null then 0 else sum(pay) end) from LLBalance a where 1=1 "
					+ " and Feeoperationtype!='B'" + " and clmno='" + "?clmno?" + "'";
		}
		logger.debug("--查询赔案:" + mClmNo + ",待分配金额的SQL:" + strSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("clmno", mClmNo);
		mBalancePay = Double.parseDouble(tExeSQL.getOneValue(sqlbv));
		logger.debug("--查询赔案:" + mClmNo + ",待分配金额:" + mBalancePay);

		if (mBalancePay < 0) {
			CError.buildErr(this, "赔案:" + mClmNo + ",待分配总金额<0,无法进行受益人分配金额!");
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		if (cOperate.equals("INSERT")) {
			// 增加对转年金的处理 周磊 2006-3-3 18:08
			if (!dealPesionData()) {
				return false;
			}

			// 添加受益人分配信息
			if (!dealLLBnf()) {
				return false;
			}

		}
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealPesionData() {
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(this.mClmNo);
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.query();
		if (tLLRegisterSet == null || tLLRegisterSet.size() <= 0) {
			CError.buildErr(this, "查询赔案信息失败!");
			return false;
		}

		if (!"2".equals(tLLRegisterSet.get(1).getGetMode())) {
			return true;
		}

		// 删除转年金相关信息
		String tSql1 = " delete from llget where " + " ClmNo = '" + "?ClmNo?" + "'" + " and PolNo = '"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql1);
		sqlbv1.put("ClmNo", mClmNo);
		sqlbv1.put("PolNo", mPolNo);
		map.put(sqlbv1, "DELETE");

		String tSql2 = " delete from llduty where " + " ClmNo = '" + "?ClmNo?" + "'"
				+ " and PolNo = '" + "?PolNo?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql1);
		sqlbv2.put("ClmNo", mClmNo);
		sqlbv2.put("PolNo", mPolNo);
		map.put(sqlbv2, "DELETE");

		return true;
	}

	private boolean dealLLBnf() {
		for (int i = 1; i <= mLLBnfSet.size(); i++) {
			mLLBnfSet.get(i).setOperator(mG.Operator);
			mLLBnfSet.get(i).setMakeDate(CurrentDate);
			mLLBnfSet.get(i).setMakeTime(CurrentTime);
			mLLBnfSet.get(i).setModifyDate(CurrentDate);
			mLLBnfSet.get(i).setModifyTime(CurrentTime);
		}
		map.put(mLLBnfSet, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
