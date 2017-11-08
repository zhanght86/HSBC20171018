package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMCertifyDesDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LDSysVarSet;
import com.sinosoft.lis.vschema.LMCertifyDesSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * hzm 用来回收单证的公用类(已用于暂加费核销和投保单核销)
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author hzm
 * @version 1.0
 */

public class PubCertifyTakeBack {
private static Logger logger = Logger.getLogger(PubCertifyTakeBack.class);
	// 定已单证类型的常量
	public final String CERTIFY_Proposal = "1101";// 个人投保单

	public final String CERTIFY_ProposalBank = "1501";// 银行险

	public final String CERTIFY_GrpProposal = "1201";// 集体投保单

	public final String CERTIFY_ProSpec = "1401"; // 定额投保单

	public final String CERTIFY_ProSimple = "1601"; // 简易投保单

	public final String CERTIFY_TempFee = "3201";// 暂交费收据

	public final String CERTIFY_CheckNo1 = "1";// 验证暂交费收据号

	public final String CERTIFY_CheckNo2 = "2";// 验证投保单印刷号码

	public final String CERTIFY_XQTempFee = "3209";// 续期暂交费收据

	public final String CERTIFY_YBTempFee = "5201";// 银行邮政保险代理收款收据

	public final String CERTIFY_TBTempFee = "250101";// 邮保通保险单

	// 错误类：发生错误可直接返回该变量
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public PubCertifyTakeBack() {
	}

	/**
	 * 单证回收
	 * 
	 * @param StartCertifyNo
	 *            单证起始号 (如果只有一个号，那么起始和终止号相同)
	 * @param EndCertifyNo
	 *            单证终止号
	 * @param CertifyType
	 *            单证编码
	 * @param globalInput
	 *            登录机构等信息
	 * @return
	 */
	public boolean CertifyTakeBack_A(String StartCertifyNo, String EndCertifyNo, String CertifyType,
			GlobalInput globalInput) {
		if (CheckCertifyBack(CertifyType)) {
			LZCardSchema schemaLZCard = new LZCardSchema();
			schemaLZCard.setCertifyCode(CertifyType);
			schemaLZCard.setStartNo(StartCertifyNo);
			schemaLZCard.setEndNo(EndCertifyNo);

			LZCardSet setLZCard = new LZCardSet();
			setLZCard.add(schemaLZCard);

			VData vData = new VData();
			vData.addElement(setLZCard);
			vData.addElement(globalInput);
			CertTakeBackUI tCertTakeBackUI = new CertTakeBackUI();
			if (!tCertTakeBackUI.submitData(vData, "TAKEBACK")) {
				mErrors.copyAllErrors(tCertTakeBackUI.mErrors);
				logger.debug(tCertTakeBackUI.mErrors.getFirstError());
				return false;
			}
			mResult = tCertTakeBackUI.getResult();
			return true;
		}

		return true;
	}

	/**
	 * 是否检验单证
	 * 
	 * @return
	 */
	public boolean CheckNewType() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("CheckNewType");
		tLDSysVarDB.setSysVarValue("1");
		LDSysVarSet tLDSysVarSet = new LDSysVarSet();
		tLDSysVarSet = tLDSysVarDB.query();
		if (tLDSysVarDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDSysVarDB.mErrors);
			return false;
		}
		if (tLDSysVarSet.size() == 0)
			return false;

		return true;
	}

	/**
	 * 是否检验单证
	 * 
	 * @return
	 */
	public boolean CheckNewType(String CerType) {
		// 查询凡是同一个收据号的纪录
		String sqlStr = "select * from LDSysVar where SysVar='CheckNewType' and (SysVarValue='" + "?CerType?"
				+ "' or SysVarValue='3')";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		LDSysVarSet tLDSysVarSet = new LDSysVarSet();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sqlStr);
		sqlbv.put("CerType",CerType);
		tLDSysVarSet = tLDSysVarDB.executeQuery(sqlbv);
		if (tLDSysVarDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDSysVarDB.mErrors);
			return false;
		}
		if (tLDSysVarSet.size() == 0)
			return false;

		return true;
	}

	/**
	 * 是否可以进行单证回收：参数是单证类型
	 * 
	 * @return
	 */
	public boolean CheckCertifyBack(String CerType) {
		String sqlStr = "select * from LMCertifyDes where CertifyCode='" + "?CerType?" + "' and TackBackFlag='Y'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sqlStr);
		sqlbv1.put("CerType",CerType);
		logger.debug("是否可以进行单证回收的SQL是" + sqlStr);
		LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
		LMCertifyDesSet tLMCertifyDesSet = new LMCertifyDesSet();

		tLMCertifyDesSet = tLMCertifyDesDB.executeQuery(sqlbv1);
		if (tLMCertifyDesDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			return false;
		}
		if (tLMCertifyDesSet.size() == 0)
			return false;

		return true;
	}

	/**
	 * 回收所有没有回收的暂交费单证
	 * 
	 * @return
	 */
	public boolean TakeBackTempfee(GlobalInput globalInput) {
		// 1 查询所有未回收的暂交费单证
		String strSQL = "select distinct ljtempfee.tempfeeno,ljtempfee.TempFeeType from ljtempfee, lzcard where 1=1 ";
		strSQL = strSQL + " and tempfeeno between lzcard.startno and lzcard.endno ";
		strSQL = strSQL + " and lzcard.receivecom like 'D%'";
		strSQL = strSQL + " and lzcard.certifycode in ('3201', '1401')";
		
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		ExeSQL mExeSQL = new ExeSQL();
		SSRS tSSRS = mExeSQL.execSQL(sqlbv2);

		// 2 循环，判断暂交费类型，调用相应的回收程序
		String tempfeeno = "";
		String tempfeetype = "";
		String takebackType = "";
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tempfeeno = tSSRS.GetText(i, 1);
			tempfeetype = tSSRS.GetText(i, 2);

			if (tempfeetype.equals("6"))// 定额单
			{
				takebackType = CERTIFY_ProSpec;
			} else // 普通单证
			{
				takebackType = CERTIFY_TempFee;
			}
			logger.debug("开始回收");
			if (!CertifyTakeBack_A(tempfeeno, tempfeeno, takebackType, globalInput)) {
				logger.debug("单证回收错误（" + i + "）:" + tempfeeno);
				logger.debug("错误原因：" + mErrors.getFirstError().toString());
			}
		}
		return true;
	}

	/**
	 * 回收所有没有回收的个人投保单单证
	 * 
	 * @return
	 */
	public boolean TakeBackLCPol(GlobalInput globalInput) {
		// 1 查询所有未回收的投保单单证(包括已经签单的)
		String strSQL = "select distinct lcpol.prtno from lcpol, lzcard where 1=1 ";
		strSQL = strSQL + " and lcpol.prtno like '861101%' ";
		strSQL = strSQL + " and lcpol.prtno between lzcard.startno and lzcard.endno";
		strSQL = strSQL + " and lzcard.receivecom like 'D%'";
		strSQL = strSQL + " and lzcard.certifycode = '1101'";
		
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSQL);
		ExeSQL mExeSQL = new ExeSQL();
		SSRS tSSRS = mExeSQL.execSQL(sqlbv3);
		String prtno = "";
		String takebackType = "";
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			prtno = tSSRS.GetText(i, 1);

			if (prtno.substring(2, 4).equals("15")) // 银行险的编码15，个险11，团险12,定额单
			{
				takebackType = CERTIFY_ProposalBank;
			} else {
				if (prtno.substring(2, 4).equals("14")) {
					takebackType = CERTIFY_ProSpec;
				} else if (prtno.substring(2, 4).equals("16")) {
					takebackType = CERTIFY_ProSimple;
				} else {
					takebackType = CERTIFY_Proposal;
				}
			}

			if (!CertifyTakeBack_A(prtno, prtno, takebackType, globalInput)) {
				logger.debug("单证回收错误（个人投保单）:" + prtno);
				logger.debug("错误原因：" + mErrors.getFirstError().toString());
			}
		}

		return true;
	}

	/**
	 * 回收所有没有回收的集体投保单单证
	 * 
	 * @return
	 */
	public boolean TakeBackLCGrpPol(GlobalInput globalInput) {
		// 1 查询所有未回收的投保单单证(包括已经签单的)
		String strSQL = "select distinct lcgrppol.prtno from lcgrppol, lzcard where 1=1 ";
		strSQL = strSQL + " and lcgrppol.prtno like '861201%' ";
		strSQL = strSQL + " and lcgrppol.prtno between lzcard.startno and lzcard.endno";
		strSQL = strSQL + " and lzcard.receivecom like 'D%'";
		strSQL = strSQL + " and lzcard.certifycode = '1201'";
		
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSQL);
		ExeSQL mExeSQL = new ExeSQL();
		SSRS tSSRS = mExeSQL.execSQL(sqlbv4);
		String prtno = "";
		String takebackType = "";
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			prtno = tSSRS.GetText(i, 1);
			takebackType = CERTIFY_GrpProposal;
			if (!CertifyTakeBack_A(prtno, prtno, takebackType, globalInput)) {
				logger.debug("单证回收错误（集体投保单）:" + prtno);
				logger.debug("错误原因：" + mErrors.getFirstError().toString());
			}
		}
		return true;
	}

	/**
	 * 得到结果
	 * 
	 * @return boolean
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
		GlobalInput globalInput = new GlobalInput();

		tPubCertifyTakeBack.TakeBackLCPol(globalInput);
		tPubCertifyTakeBack.TakeBackLCGrpPol(globalInput);
		tPubCertifyTakeBack.TakeBackTempfee(globalInput);

	}
}
