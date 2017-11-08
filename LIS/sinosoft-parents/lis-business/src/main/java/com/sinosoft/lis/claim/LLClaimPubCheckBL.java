package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.db.LLBnfDB;
import com.sinosoft.lis.db.LLGetDB;
import com.sinosoft.lis.db.LLPrepayDetailDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLGetSet;
import com.sinosoft.lis.vschema.LLPrepayDetailSet;
import com.sinosoft.lis.vschema.LMDutyGetSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔系统：通用校验方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLClaimPubCheckBL {
private static Logger logger = Logger.getLogger(LLClaimPubCheckBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	public LLClaimPubCheckBL() {
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－理陪校验信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 校验该赔案是否符合[理赔金转年金]的规则
	 * 
	 * @param pClmNo
	 * @return
	 */
	public boolean checkClmToPension(String pClmNo) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该赔案的结算信息是否唯一
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select * from LLBalance where ClmNo='" + "?clmno?"
				+ "' and FeeOperationType = 'A' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("clmno", pClmNo);
		LLBalanceDB tLLBalanceDB = new LLBalanceDB();
		LLBalanceSet tLLBalanceSet = tLLBalanceDB.executeQuery(sqlbv);

		if (tLLBalanceSet.size() != 1) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的赔款结算信息不唯一!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 取出变量信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = StrTool.cTrim(tLLBalanceSet.get(1).getContNo());
		String tPolNo = StrTool.cTrim(tLLBalanceSet.get(1).getPolNo());
		String tRiskCode = StrTool.cTrim(tLLBalanceSet.get(1).getRiskCode());
		String tGetDutyKind = StrTool.cTrim(tLLBalanceSet.get(1)
				.getGetDutyKind());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 在产品中判断有转年金的责任信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LMDutyGetSet tLMDutyGetSet = mLLClaimPubFunBL.getLMDutyGetForPension(
				tRiskCode, "0");
		if (tLMDutyGetSet == null) {
			mErrors.addOneError("合同号[" + tContNo + "]的中[" + tRiskCode
					+ "]险种不符合理陪金转年金的产品定义要求!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.2 判断每种年金的受益人信息是否唯一
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLMDutyGetSet.size(); i++) {
			String tNJGetDutyCode = StrTool.cTrim(tLMDutyGetSet.get(i)
					.getGetDutyCode());// 年金的责任代码
			String tNJGetDutyName = StrTool.cTrim(tLMDutyGetSet.get(i)
					.getGetDutyName());// 年金的责任名称

			tSql = "select * from LLGet where ClmNo='" + "?ClmNo?"
					+ "' and PolNo='" + "?PolNo?" + "' and GetDutyCode = '"
					+ "?GetDutyCode?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("ClmNo", pClmNo);
			sqlbv1.put("PolNo", tPolNo);
			sqlbv1.put("GetDutyCode", tNJGetDutyCode);
			LLGetDB tLLGetDB = new LLGetDB();
			LLGetSet tLLGetSet = tLLGetDB.executeQuery(sqlbv1);

			if (tLLGetSet.size() > 1) {
				mErrors.addOneError("[" + tNJGetDutyName
						+ "]责任上的受益人个数不能超过一个!!!");
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 判断该赔案，该险种受益人信息不能超过2个
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LLBnf where 1=1 " + " and ClmNo='" + "?ClmNo?"
				+ "' and PolNo='" + "?PolNo?" + "'" + " and BnfKind='A'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("ClmNo", pClmNo);
		sqlbv2.put("PolNo", tPolNo);
		LLBnfDB tLLBnfDB = new LLBnfDB();
		LLBnfSet tLLBnfSet = tLLBnfDB.executeQuery(sqlbv2);
		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--受益人记录数：[" + tLLBnfSet.size() + "]:" + tSql);
		logger.debug("----------------------------------------------------------------------------");
		if (tLLBnfDB.mErrors.needDealError()) {
			mErrors.addOneError("赔案号[" + pClmNo + "]的受益人信息没有取到!!!");
			return false;
		}

		if (tLLBnfSet.size() > 2) {
			mErrors.addOneError("赔案号[" + pClmNo + "],险种号[" + tPolNo
					+ "]的受益人信息不能超过2个!!!");
			return false;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.2 判断每个受益人信息所在年金责任是否唯一
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLLBnfSet.size(); i++) {
			String tBnfNo = tLLBnfSet.get(i).getBnfNo(); // 受益人序号
			String tBnfName = tLLBnfSet.get(i).getName(); // 受益人姓名

			tSql = "select * from LLGet where ClmNo='" + "?ClmNo?"
					+ "' and PolNo='" + "?PolNo?" + "' and BnfNo = '" + "?BnfNo?"
					+ "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables() ;
			sqlbv3.sql(tSql);
			sqlbv3.put("ClmNo", pClmNo);
			sqlbv3.put("PolNo", tPolNo);
			sqlbv3.put("BnfNo", tBnfNo);
			LLGetDB tLLGetDB = new LLGetDB();
			LLGetSet tLLGetSet = tLLGetDB.executeQuery(sqlbv3);

			if (tLLGetSet.size() != 1) {
				mErrors.addOneError("受益人[" + tBnfName + "]的年金责任不唯一!!!");
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 判断该保单是否作过预付处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLPrepayDetailDB tLLPrepayDetailDB = new LLPrepayDetailDB();
		tLLPrepayDetailDB.setClmNo(pClmNo);
		tLLPrepayDetailDB.setPolNo(tPolNo);
		LLPrepayDetailSet tLLPrepayDetailSet = tLLPrepayDetailDB.query();

		if (tLLPrepayDetailSet.size() > 0) {
			mErrors.addOneError("该赔案做过预付处理，不允许按转年金进行领取!!!");
			return false;
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－理陪校验信息－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
		double tDReturn = 0;
		int tITempData = 0;
		String tSTempData = "";
		String tTempData = "";

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 测试2个日期之间经过的月数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tSDate = "2005-12-23";
		String tEDate = "2015-12-23";
		String tSBDate = "2009-12-22";

		// tITempData = PubFun.calInterval(tSDate,tSBDate,"M");//计算出时间间隔的月数
		// logger.debug(tITempData);

		tDReturn = tLLClaimPubFunBL.getLCPremPeriodTimes(tSDate, tEDate, "12",
				tSBDate);
		logger.debug(tDReturn);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 计算未成年人保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// LCPolSchema tLCPolSchema = tLLClaimPubFunBL.getLCPol("HB010327011000314238");
		// tDReturn = Arith.round(tLLClaimPubFunBL.getLLParaPupilAmnt(tLCPolSchema),2);
		// if (tDReturn==-1)
		// {
		// logger.debug(tDReturn);
		// }
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		// tTempData
		// =String.valueOf(PubFun.getCurrentDate()).substring(5,7)+String.valueOf(PubFun.getCurrentDate()).substring(8,10);
		// tSDate = PubFun.calDate(tSDate,12,"M",tSDate);
		// logger.debug(tSDate);
		// int tIDateUnit = tLLClaimPubFunBL.getDateUnit("20061211","D");
		// logger.debug(tIDateUnit);
		// tDReturn =
		// tLLClaimPubFunBL.getLCPremPeriodTimes("2005-01-01","2005-12-11","0");
		// // tDReturn = Arith.div(11,3);
		// // tDReturn = 10 % 5 ;
		// // logger.debug(tDReturn);
		//
		//
		// LCGetSchema tLCGetSchema = new LCGetSchema();
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// String tReturn = tLLClaimPubFunBL.getGetStartDate(null,tLCGetSchema);
		//
		// tReturn = tLLClaimPubFunBL.getLRFlag(" ","");
		// String tSignCom="12345678";
		// tSignCom = tSignCom.substring(0,5);
		// logger.debug(tSignCom);
		// LCPolSchema pLCPolSchema = new LCPolSchema();
		// pLCPolSchema.setPolNo("HB020421411000027225");
		// tLLClaimPubFunBL.getPolNoAddPay("","a",pLCPolSchema);
		// tLLClaimPubFunBL.getLPEdorItemAfter("HB010226011000077","0000","2005-01-01","CT','BC','CM");
		// tLLClaimPubFunBL.getLPEdorItemAfter("HB010226011000077","0000","2005-01-01","CT");
	}

}
