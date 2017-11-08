package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 单证打印：PCT020-----理赔查勘费用调查单------LpchkfyBxdC000230.vts
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author ccc
 * @version 1.0
 */
public class LLPRTInteInqPayBL {
private static Logger logger = Logger.getLogger(LLPRTInteInqPayBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();
	private PubFun mPubFun = new PubFun();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private String mPrtCode = "PCT020";// 打印编码
	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号
	private String mInqNo = ""; // 调查序号
	private String mPayee = ""; // 查勘人

	public LLPRTInteInqPayBL() {
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

		logger.debug("----------单证打印：理赔查勘费用调查单-----LLPRTInteInqPayBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------单证打印：理赔查勘费用调查单------LLPRTInteInqTaskBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		mPayee = (String) mTransferData.getValueByName("Payee"); // 查勘人
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/** ----------------- 赔案号 、出险人客户号、调查序号 由外部传入------------- */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LpchkfyBxdC000230.vts", "");

		// 赔案号-----传入
		// 查勘人姓名----tName
		// ExeSQL tExeSQL1 = new ExeSQL();
		// ExeSQL tExeSQL2 = new ExeSQL();
		// String tSql1 = "select inqper1 from llinqcourse where clmno = '"+mClmNo+"'";
		// String tSql2 = "select inqper2 from llinqcourse where clmno = '"+mClmNo+"'";
		// String tPerson1 = tExeSQL1.getOneValue(tSql1); //第一调查人
		// String tPerson2 = tExeSQL2.getOneValue(tSql2); //第二调查人

		// #############################################################################
		// 查询调查申请表-----调查机构
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		tLLClaimDB.getInfo();
		//
		// //llinqfee
		// LLInqFeeDB tLLInqFeeDB = new LLInqFeeDB();
		// tLLInqFeeDB.setClmNo(mClmNo);
		// tLLInqFeeDB.setInqNo(mInqNo);
		// tLLInqFeeDB.getInfo();

		// 立案分公司
		String tLLRegisterSql = "select mngcom from llregister where rgtno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tLLRegisterSql);
		sqlbv.put("clmno", mClmNo);
		ExeSQL twExeSQL = new ExeSQL();
		String tmngCom = twExeSQL.getOneValue(sqlbv);
		String feesum = "0.00";
		String feesum1 = "0.00";
		String feesum2 = "0.00";
		double zjexx = 0.00;
		String sumMoneyd = "0.00";
		ExeSQL t1ExeSQL = new ExeSQL();
		String t1LLinqFeesumSql = "select sum(feesum) from llinqfee where clmno = '"
				+ "?clmno?" + "' and feetype = '01' and payee = '" + "?payee?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(t1LLinqFeesumSql);
		sqlbv1.put("clmno", mClmNo);
		sqlbv1.put("payee", mPayee);
		feesum1 = t1ExeSQL.getOneValue(sqlbv1);
		ExeSQL t2ExeSQL = new ExeSQL();
		String t2LLinqFeesumSql = "select sum(feesum) from llinqfee where clmno = '"
				+ "?clmno?" + "' and feetype = '02' and payee = '" + "?payee?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(t2LLinqFeesumSql);
		sqlbv2.put("clmno", mClmNo);
		sqlbv2.put("payee", mPayee);
		feesum2 = t2ExeSQL.getOneValue(sqlbv2);
		if (feesum1.equals("")) {
			feesum1 = "0.00";
		}
		if (feesum2.equals("")) {
			feesum2 = "0.00";
		}
		String feesum11 = new DecimalFormat("0.00").format(Double
				.parseDouble(feesum1));
		String feesum22 = new DecimalFormat("0.00").format(Double
				.parseDouble(feesum2));
		zjexx = Double.parseDouble(feesum1) + Double.parseDouble(feesum2);
		String zjex = new DecimalFormat("0.00").format(zjexx);
		sumMoneyd = mPubFun.getChnMoney(zjexx);
		logger.debug("--------以下为单个数据变量赋值--------");

		tTextTag.add("LLClaim.ClmNo1", mClmNo); // 赔案号
		tTextTag.add("llinqcourse.inqper1", mPayee); // 查勘人
		String tManageComNameSql = "select name from LDCom where ComCode='"
				+ "?mngcom?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tManageComNameSql);
		sqlbv3.put("mngcom", tmngCom);
		ExeSQL comExeSQL = new ExeSQL();
		String tManageComName = comExeSQL.getOneValue(sqlbv3);
		tTextTag.add("lianfgs1", tManageComName); // 立案分公司
		String LiPeijl = "";
		if (tLLClaimDB.getGiveType().equals("0")) {
			LiPeijl = "给付";
		}
		if (tLLClaimDB.getGiveType().equals("1")) {
			LiPeijl = "拒付";
		}
		if (tLLClaimDB.getGiveType().equals("2")) {
			LiPeijl = "客户撤案";
		}
		if (tLLClaimDB.getGiveType().equals("3")) {
			LiPeijl = "公司撤案";
		}
		tTextTag.add("LLInqApplyDB.InqConclusion1", LiPeijl); // 理赔结论
		tTextTag.add("llinqapply.InqEndDate1", tLLClaimDB.getEndCaseDate()); // 结案日期
		tTextTag.add("LLInqFee.FeeSumA1", feesum11); // 取证工料费
		tTextTag.add("LLInqFee.FeeSumB1", feesum22); // 取证劳务费
		tTextTag.add("danzhengsum1", ""); // 总计单证张数///////////
		tTextTag.add("zje1", zjex); // 总计金额
		tTextTag.add("sumpay1", sumMoneyd); // 大写
		tTextTag.add("teshushm1", ""); // 特殊说明
		tTextTag.add("caiwuf1", ""); // 财务负责人
		tTextTag.add("fuhe1", ""); // 复核
		tTextTag.add("baoxiaoren1", ""); // 报销人
		tTextTag.add("zhurenshp1", ""); // 主任核赔人审批
		tTextTag.add("jigoufzrshp1", ""); // 机构负责人审批

		tTextTag.add("LLClaim.ClmNo2", mClmNo); // 赔案号
		tTextTag.add("llinqcourse.inqper2", mPayee); // 查勘人
		tTextTag.add("lianfgs2", tManageComName); // 立案分公司
		tTextTag.add("LLInqApplyDB.InqConclusion2", LiPeijl); // 理赔结论
		tTextTag.add("llinqapply.InqEndDate2", tLLClaimDB.getEndCaseDate()); // 结案日期
		tTextTag.add("LLInqFee.FeeSumA2", feesum11); // 取证工料费
		tTextTag.add("LLInqFee.FeeSumB2", feesum22); // 取证劳务费
		tTextTag.add("danzhengsum2", ""); // 总计单证张数
		tTextTag.add("zje2", zjex); // 总计金额
		tTextTag.add("sumpay2", sumMoneyd);// 大写
		tTextTag.add("teshushm2", ""); // 特殊说明
		tTextTag.add("caiwuf2", ""); // 财务负责人
		tTextTag.add("fuhe2", ""); // 复核
		tTextTag.add("baoxiaoren2", ""); // 报销人
		tTextTag.add("zhurenshp2", ""); // 主任核赔人审批
		tTextTag.add("jigoufzrshp2", ""); // 机构负责人审批

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRTInteInqPayBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
