package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPayRuleFactoryDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis_New
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
 * @author Caihy
 * @version 1.0
 */

public class QueryPayRuleBL {
private static Logger logger = Logger.getLogger(QueryPayRuleBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private Vector mResult = new Vector();

	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	private LCPayRuleFactorySchema mLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
	private LCPayRuleFactorySet mLCPayRuleFactorySet = new LCPayRuleFactorySet();

	public QueryPayRuleBL() {
	}

	public boolean submitData(VData cInputData) {
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("after getInputData");
		if (!queryPayRule()) {
			return false;
		}
		logger.debug("after queryPayRule");
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCPayRuleFactorySchema = (LCPayRuleFactorySchema) cInputData
				.getObjectByObjectName("LCPayRuleFactorySchema", 0);
		return true;
	}

	private boolean queryPayRule() {
		LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
		tLCPayRuleFactoryDB.setSchema(mLCPayRuleFactorySchema);
		LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
		tLCPayRuleFactorySet = tLCPayRuleFactoryDB.query();
		logger.debug("size:" + tLCPayRuleFactorySet.size());

		// 工作年限：系统中应记录员工的入司日期，工作年限=本次操作的业务生效日－入司日期；
		// 计划参与年限＝本次操作日期的业务生效日－该员工保单生效的日期
		// 本次（缴费）操作的业务生效日：承保缴费＝承保生效日，
		// 续期缴费＝续期缴费生效日（定期续期缴费取应缴费日期，不定期则为财务缴费的次日）。
		Vector tVector = getOpValiDate();
		String tOpValiDate = tVector.get(0).toString();
		String tCValiDate = tVector.get(1).toString();

		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		String tFactoryname = "";

		for (int i = 1; i <= tLCPayRuleFactorySet.size(); i++) {
			tLCPayRuleFactorySchema = tLCPayRuleFactorySet.get(i);
			tFactoryname = tLCPayRuleFactorySchema.getFactoryName();
			PubCalculator tPubCalculator = new PubCalculator();
			if (tFactoryname.equals("ServiceYear")) { // 服务年限
				String tJoinCompanyDate = (String) mTransferData
						.getValueByName("JoinCompanyDate");
				// 舍弃法算保单生效时员工的服务年限
				String tServiceYear = Integer.toString(PubFun.calInterval(
						tJoinCompanyDate, tOpValiDate, "Y"));
				logger.debug("serviceYear:" + tServiceYear
						+ "  OpValiDate:" + tOpValiDate + "JoinCompanyDate"
						+ tJoinCompanyDate);
				tPubCalculator.addBasicFactor(tFactoryname, tServiceYear);
			} else { // JoinYear参加计划年限
				// 舍弃法算保单生效时员工的计划参与年限
				String tJoinYear = Integer.toString(PubFun.calInterval(
						tCValiDate, tOpValiDate, "Y"));
				tPubCalculator.addBasicFactor(tFactoryname, tJoinYear);
			}

			tPubCalculator.setCalSql(tLCPayRuleFactorySchema.getCalSql());
			String tRate = tPubCalculator.calculate();
			logger.debug("tRate:" + tRate);
			mResult.add(tLCPayRuleFactorySchema.getOtherNo()); // 交费编码
			mResult.add(tRate);
		}
		return true;
	}

	// 取得本次（缴费）操作的业务生效日
	// 承保缴费＝承保生效日，
	// 续期缴费＝续期缴费生效日（定期续期缴费取应缴费日期，不定期则为财务缴费的次日）。
	private Vector getOpValiDate() {
		String tValiDate = ""; // 本次（缴费）操作的业务生效日
		String tCValiDate = ""; // 保单生效日
		String tAppFlag = (String) mTransferData.getValueByName("AppFlag"); // 0-承保缴费,1-续期缴费
		String tInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		Vector tVector = new Vector();

		logger.debug("AppFlag:" + tAppFlag);
		logger.debug("InsuredNo" + tInsuredNo);
		if (tAppFlag.equals("0")) { // 承保缴费
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(mLCPayRuleFactorySchema.getGrpContNo());
			tLCGrpPolDB.setRiskCode(mLCPayRuleFactorySchema.getRiskCode());
			LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
			tLCGrpPolSet = tLCGrpPolDB.query();
			tValiDate = tLCGrpPolSet.get(1).getCValiDate();
			tCValiDate = tLCGrpPolSet.get(1).getCValiDate();

		} else { // 续期缴费
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setGrpContNo(mLCPayRuleFactorySchema.getGrpContNo());
			tLCPolDB.setRiskCode(mLCPayRuleFactorySchema.getRiskCode());
			tLCPolDB.setInsuredNo(tInsuredNo);
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.query();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1);

			if (tLCPolSchema.getPayIntv() == -1) { // 不定期
				String tSql = "select min(EnterAccDate)+1 days from ljtempfee where "
						+ " otherno = '"
						+ tLCPolSchema.getGrpContNo()
						+ "'"
						+ " and confflag = '0' and operstate='0'";
				ExeSQL tExeSQL = new ExeSQL();
				tValiDate = tExeSQL.getOneValue(tSql);
			} else { // 定期
				String tSql = "select max(paydate) from ljspaygrp where"
						+ " grpcontno = '" + tLCPolSchema.getGrpContNo()
						+ "' and operstate='0'";
				ExeSQL tExeSQL = new ExeSQL();
				tValiDate = tExeSQL.getOneValue(tSql);
			}
			tCValiDate = tLCPolSchema.getCValiDate();
		}
		tVector.add(tValiDate);
		tVector.add(tCValiDate);
		return tVector;
	}

	public Vector getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}

}
