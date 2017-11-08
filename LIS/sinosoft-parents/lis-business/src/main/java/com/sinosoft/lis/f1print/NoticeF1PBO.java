package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LDBankSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
/**
 * <p>Title: </p>
 * <p>Description:通知书打印的基础类 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author kevin
 * @version 1.0
 */

public class NoticeF1PBO {
private static Logger logger = Logger.getLogger(NoticeF1PBO.class);

	public CErrors mErrors = new CErrors();

	public NoticeF1PBO() {
	}

	public String getRiskName(String strRiskCode) throws Exception {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			throw new Exception("在取得主险LMRisk的数据时发生错误");
		}
		return tLMRiskDB.getRiskName();
	}

	public String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	public String getComName(String strComCode) throws Exception {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			throw new Exception("在取得LDCode的数据时发生错误");
		}
		return tLDCodeDB.getCodeName();
	}

	public String getBankName(String strBankCode) throws Exception {
		LDBankDB tLDBankDB = new LDBankDB();

		tLDBankDB.setBankCode(strBankCode);
		LDBankSet tLDBankSet = new LDBankSet();
		tLDBankSet = tLDBankDB.query();
		if (tLDBankSet.size() <= 0) {
			mErrors.copyAllErrors(tLDBankDB.mErrors);
			throw new Exception("在取得LDBankDB的数据时发生错误");
		}

		return tLDBankSet.get(1).getBankName();
	}

	/**
	 * 根据保单信息查询银行信息
	 * 
	 * @param aLCPolSchema
	 * @return
	 */
	public LJTempFeeClassSchema queryBankInfo(LCPolSchema aLCPolSchema) {
		// tongmeng 2007-12-28 modify
		// 修改取数逻辑
		String strSQL = "SELECT * FROM LJTempFee" + " WHERE ((OtherNo ='"
				+ "?ContNo?" + "' " + " OR OtherNo = '"
				+ "?PrtNo?" + "'))";
		logger.debug("判断缴费是否银行SQL:" + strSQL);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("ContNo", aLCPolSchema.getContNo());
		sqlbv1.put("PrtNo", aLCPolSchema.getPrtNo());
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeDB().executeQuery(sqlbv1);

		for (int nInx = 0; nInx < tLJTempFeeSet.size(); nInx++) {
			LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(nInx + 1);

			// 查询这笔交费是按何种方式处理的
			LJTempFeeClassSchema tLJTempFeeClassSchema = queryBankInfo(tLJTempFeeSchema
					.getTempFeeNo());

			if (tLJTempFeeClassSchema != null) {
				return tLJTempFeeClassSchema;
			}
		}

		return null;
	}

	/**
	 * 根据保单信息查询银行信息
	 * 
	 * @param aLCPolSchema
	 * @return
	 */
	public LJTempFeeClassSchema queryBankInfo(LCContSchema tLCContSchema) {
		String strSQL = "SELECT * FROM LJTempFee"
				+ " WHERE (( OtherNoType = '0' AND OtherNo IN ( SELECT PolNo FROM LCPol WHERE TRIM(PrtNo) = '"
				+ "?tPrtNo?" + "'))"
				+ " OR ( OtherNoType = '4' AND OtherNo = '"
				+ "?PrtNo?" + "'))";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("tPrtNo", tLCContSchema.getPrtNo().trim());
		sqlbv2.put("PrtNo", tLCContSchema.getPrtNo());
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeDB().executeQuery(sqlbv2);

		for (int nInx = 0; nInx < tLJTempFeeSet.size(); nInx++) {
			LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(nInx + 1);

			// 查询这笔交费是按何种方式处理的
			LJTempFeeClassSchema tLJTempFeeClassSchema = queryBankInfo(tLJTempFeeSchema
					.getTempFeeNo());

			if (tLJTempFeeClassSchema != null) {
				return tLJTempFeeClassSchema;
			}
		}

		return null;
	}

	/**
	 * 根据暂交费号查询银行信息
	 * 
	 * @param aLCPolSchema
	 * @return
	 */
	public LJTempFeeClassSchema queryBankInfo(String strTempFeeNo) {
		// 查询这笔交费是按何种方式处理的
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("SELECT * FROM LJTempFeeClass WHERE PayMode = '4' AND TempFeeNo = '"
				+ "?strTempFeeNo?" + "'");
		sqlbv3.put("strTempFeeNo", strTempFeeNo);
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassDB().executeQuery(sqlbv3);
		if (tLJTempFeeClassSet.size() > 0) {
			return tLJTempFeeClassSet.get(1);
		}

		return null;
	}
}
