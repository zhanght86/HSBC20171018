package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LMDutyGetClmDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LMDutyGetClmSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔外部接口文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimExternalInterfaceBL {
private static Logger logger = Logger.getLogger(LLClaimExternalInterfaceBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();
	private ExeSQL mExeSQL = new ExeSQL();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private JdbcUrl mJdbcUrl = new JdbcUrl();

	// 业务变量
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mClmNo = ""; // 赔案号
	private String mInsDate = ""; // 出险时间

	public LLClaimExternalInterfaceBL() {
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－保全外部接口方法－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * pContNo--合同号 pPolNo --险种号 pDutyCode--责任编码 pDate--指定日期
	 * pDealType--处理方式，P-用PolNo计算，D-用DutyCode计算
	 */
	public double getPosAddUpPay(String pContNo, String pPolNo,
			String pDutyCode, String pDate, String pDealType) {

		logger.debug("-------------------------理赔外部方法接口-----保全类-----计算指定险种,责任的累计保额冲减[getPosAddUpPay]----开始-------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义各种变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		String tSql = "";

		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 找出指定日期之前(包括该时点)的赔案明细
		 * 
		 * -----审批通过时间4.1-----<=------指定时点5.1---<---6.1--------
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select b.* from LLClaimUWMain a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo " + " and a.ExamDate <= to_date('"
				+ "?pDate?" + "','yyyy-mm-dd')" + " and b.ContNo = '" + "?ContNo?"
				+ "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("pDate", pDate);
		sqlbv.put("ContNo", pContNo);
		tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sqlbv);
		logger.debug("-----------------------------------------------------------------");
		logger.debug("--指定险种指定日期之后的赔案明细[" + tLLClaimDetailSet.size()
				+ "]:" + tSql);
		logger.debug("-----------------------------------------------------------------");

		if (tLLClaimDetailDB.mErrors.needDealError() == true) {
			this.mErrors.addOneError("查询赔案明细信息发生错误!"
					+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
			return -1;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 计算本责任保额冲减,循环赔案明细，计算冲减的值
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);

			String tClmNo = StrTool.cTrim(tLLClaimDetailSchema.getClmNo());
			String tContNo = StrTool.cTrim(tLLClaimDetailSchema.getContNo());
			String tPolNo = StrTool.cTrim(tLLClaimDetailSchema.getPolNo());
			String tDutyCode = StrTool
					.cTrim(tLLClaimDetailSchema.getDutyCode());
			String tGetDutyKind = StrTool.cTrim(tLLClaimDetailSchema
					.getGetDutyKind());
			String tGetDutyCode = StrTool.cTrim(tLLClaimDetailSchema
					.getGetDutyCode());
			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tAcctFlag = StrTool
					.cTrim(tLLClaimDetailSchema.getAcctFlag());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1
			 * 如果是本险种，本责任，认为不符合条件，可以继续循环 P-用PolNo计算，D-用DutyCode计算
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (pDealType.equals("P"))// 按照险种编码进行统计
			{
				if (tPolNo.equals(pPolNo)) {
					if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "14")) {
						t_Sum_Return = Arith.round(t_Sum_Return
								+ tLLClaimDetailSchema.getRealPay(), 2);
					} else {
						logger.debug("------------------------------------------------------------");
						logger.debug("--险种[" + tRiskCode + "]无须冲减保额，被过滤掉");
						logger.debug("------------------------------------------------------------");
					}
					continue;
				}
			} else if (pDealType.equals("D"))// 按照责任编码进行统计
			{
				if (tPolNo.equals(pPolNo) && tDutyCode.equals(pDutyCode)) {
					if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "14")) {
						t_Sum_Return = Arith.round(t_Sum_Return
								+ tLLClaimDetailSchema.getRealPay(), 2);
					} else {
						logger.debug("------------------------------------------------------------");
						logger.debug("--险种[" + tRiskCode + "]无须冲减保额，被过滤掉");
						logger.debug("------------------------------------------------------------");
					}
					continue;
				}
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1
			 * 判断本责任是否符合条件,是否在LMDutyGetClm中定义
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tSqlTA = " select * from LMDutyGetClm where 1=1 "
					+ " and CnterCalCode is not null "
					+ " and OthCalCode   is not null " + " and GetDutyKind = '"
					+ "?tGetDutyKind?" + "'" + " and GetDutyCode = '"
					+ "?GetDutyCode?"+ "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSqlTA);
			sqlbv1.put("GetDutyKind", tGetDutyKind);
			sqlbv1.put("GetDutyCode", tGetDutyCode);
			LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
			LMDutyGetClmSet tLMDutyGetClmSet = tLMDutyGetClmDB
					.executeQuery(sqlbv1);
			logger.debug("-----------------------------------------------------------------");
			logger.debug("--判断本责任是否在产品中定义[" + tLMDutyGetClmSet.size()
					+ "]:" + tSqlTA);
			logger.debug("-----------------------------------------------------------------");

			if (tLMDutyGetClmDB.mErrors.needDealError() == true) {
				this.mErrors.addOneError("查询合同号['" + pContNo + "']的赔案明细信息发生错误!"
						+ tLMDutyGetClmDB.mErrors.getError(0).errorMessage);
				return -1;
			}

			if (tLMDutyGetClmSet.size() != 1) {
				continue;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.2 计算支付金额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			this.mInsDate = mLLClaimPubFunBL.getInsDate(tClmNo);
			double tDReturn = getOtherLCGet(tLLClaimDetailSchema,
					tLMDutyGetClmSet.get(1), pPolNo, pDutyCode, pDealType);
			if (tDReturn == -1) {
				return -1;
			} else {
				t_Sum_Return = t_Sum_Return + tDReturn;
			}

		}

		t_Sum_Return = Arith.round(t_Sum_Return, 2);
		logger.debug("--返回值：[" + t_Sum_Return + "]");

		logger.debug("-------------------------理赔外部方法接口-----保全类-----计算指定险种,责任的累计保额冲减[getPosAddUpPay]----结束-------------------------");
		return t_Sum_Return;
	}

	/**
	 * 进行其他起责任保额冲减
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private double getOtherLCGet(LLClaimDetailSchema pLLClaimDetailSchema,
			LMDutyGetClmSchema pLMDutyGetClmSchema, String pPolNo,
			String pDutyCode, String pDealType) {

		logger.debug("---------------------------------进行其他责任保额冲减----开始---------------------------------");

		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1 查询LCPol的信息,用于计算给付金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = pLLClaimDetailSchema.getPolNo();
		String tDutyCode = pLLClaimDetailSchema.getDutyCode();

		mLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
		if (mLCPolSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return -1;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2 通过公式计算出冲减其他责任的金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tCnterCalCode = StrTool.cTrim(pLMDutyGetClmSchema
				.getCnterCalCode());// 获取LCGet的公式
		String tOthCalCode = StrTool.cTrim(pLMDutyGetClmSchema.getOthCalCode()); // 计算金额的公式
		String tGetDutyCode = tCnterCalCode;

		String tSql = executeCalCode(tCnterCalCode, pLLClaimDetailSchema); // 解析后的LCGet语句
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(tSql);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 查询其他责任
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbva);
		if (tLCGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],领取项信息失败!" + tLCGetDB.mErrors.getError(0).errorMessage);
			return -1;
		}

		if (tLCGetSet.size() == 0) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],查询出的记录数为:[" + tLCGetSet.size() + "],领取项信息不能为空!");
			return -1;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.5 对其他责任的保额进行冲减
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCGetSet tLCGetSaveSet = new LCGetSet();
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);
			String tSPolNo = StrTool.cTrim(tLCGetSchema.getPolNo());
			String tSDutyCode = StrTool.cTrim(tLCGetSchema.getDutyCode());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.6 计算出来的责任 =
			 * 传进来的责任[准备要冲减的责任] 相一致，才去计算要冲减的值
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			if (pDealType.equals("P"))// 按照险种编码进行统计
			{
				if (tSPolNo.equals(pPolNo)) {
					t_Sum_Return = t_Sum_Return
							+ executePay(tOthCalCode, pLLClaimDetailSchema);
				}

			} else if (pDealType.equals("D"))// 按照责任编码进行统计
			{
				if (tSPolNo.equals(pPolNo) && tSDutyCode.equals(pDutyCode)) {
					t_Sum_Return = t_Sum_Return
							+ executePay(tOthCalCode, pLLClaimDetailSchema);
				}
			}

		}

		logger.debug("---------------------------------进行其他责任保额冲减----结束---------------------------------");
		return t_Sum_Return;
	}

	/**
	 * 目的：执行产品指向的算法公式
	 */
	private String executeCalCode(String pCalCode,
			LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";

		if (pCalCode == null || pCalCode.length() == 0) {
			return tReturn;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种参数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("-----------开始---------执行产品指向的算法公式-------------------------------");

		logger.debug("===========================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		// 合同号
		tTransferData.setNameAndValue("ContNo", String
				.valueOf(pLLClaimDetailSchema.getContNo()));

		// 保单险种号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(pLLClaimDetailSchema.getPolNo()));

		// 责任
		tTransferData.setNameAndValue("DutyCode", String
				.valueOf(pLLClaimDetailSchema.getDutyCode()));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		logger.debug("===========================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算要素--tName:" + tName + "  tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("===========================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("executeCalCode--tName:" + tName + " tValue:"
			// + tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}

		tReturn = tCalculator.getCalSQL();
		logger.debug("----------------------------------------------------------------------------------\n");
		logger.debug("解析前的计算公式==[" + pCalCode + "]");
		logger.debug("解析后的计算公式==[" + tReturn + "]");
		logger.debug("\n----------------------------------------------------------------------------------");

		logger.debug("\n-----------结束---------执行产品指向的算法公式-------------------------------");

		return tReturn;

	}

	/**
	 * 目的：理赔计算
	 * 
	 * @param pCalSum
	 *            计算金额
	 * @param pCalCode
	 *            计算公式
	 * @return double
	 */
	private double executePay(String pCalCode,
			LLClaimDetailSchema pLLClaimDetailSchema) {

		logger.debug("\n-----------开始---------计算公式的金额-------------------------------\n");

		double rValue;

		if (null == pCalCode || "".equals(pCalCode)) {
			return 0;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种计算要素 ?Amnt?
		 * \?PolNo??\?GetDutyCode?\?GetDutyKind?\?ClmNO?
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		logger.debug("==================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();

		// 基本保额,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Amnt", String
				.valueOf(getAmnt(pLLClaimDetailSchema)));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(pLLClaimDetailSchema.getPolNo()));

		// 给付责任类型
		tTransferData.setNameAndValue("GetDutyCode", String
				.valueOf(pLLClaimDetailSchema.getGetDutyCode()));

		// 赔案号
		tTransferData.setNameAndValue("ClmNO", String
				.valueOf(pLLClaimDetailSchema.getClmNo()));

		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("PubCalculator.addBasicFactor--tName:" + tName + "
			// tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		logger.debug("==================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算其他责任计算要素名称--tName:" + tName + "  tValue:"
					+ tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("==================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 进行计算，Calculator.calculate()
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tStr = "";
		logger.debug("计算公式=" + tCalculator.getCalSQL());
		tStr = tCalculator.calculate();
		if (tStr.trim().equals("")) {
			rValue = 0;
		} else {
			rValue = Arith.round(Double.parseDouble(tStr), 2);
		}

		if (tCalculator.mErrors.needDealError()) {
			mErrors.addOneError("计算公式错误!!!"
					+ tCalculator.mErrors.getError(0).errorMessage);
		}
		logger.debug("==================================================================");

		logger.debug("\n-----------结束---------计算公式的金额-------------------------------\n");
		return rValue;
	}

	/**
	 * 得到基本保额,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private double getAmnt(LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "";

		String tPosFlag = StrTool.cTrim(pLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全 */
		if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LCGet where 1=1 "
					+ " and PolNo ='" + "?PolNo?" + "'"
					+ " and DutyCode ='" + "?DutyCode?"
					+ "'" + " and GetDutyCode ='"
					+ "?GetDutyCode?" + "'";
		} else {

			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LPGet where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?"
					+ "'" + " and PolNo ='" + "?PolNo?"
					+ "'" + " and DutyCode ='"
					+ "?DutyCode?" + "'"
					+ " and GetDutyCode ='"
					+ "?GetDutyCode?" + "'";

		}

		logger.debug("------------------------------------------------------------");
		logger.debug("--要素Amnt[基本保额]：[" + tSql + "]");
		logger.debug("------------------------------------------------------------");
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("PolNo", pLLClaimDetailSchema.getPolNo());
		sqlbv2.put("DutyCode", pLLClaimDetailSchema.getDutyCode());
		sqlbv2.put("GetDutyCode", pLLClaimDetailSchema.getGetDutyCode());
		sqlbv2.put("EdorNo", pLLClaimDetailSchema.getPosEdorNo());
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv2));
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("得到基本保额发生错误!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			t_Sum_Return = Double.parseDouble(tReturn);
		}

		return t_Sum_Return;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－保全外部接口方法－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	public static void main(String[] args) {

		double tDReturn = 0;
		String pContNo = "000000034529";
		String pPolNo = "210320000001063";
		String pDutyCode = "623000";
		String pDate = "2006-12-02";
		LLClaimExternalInterfaceBL tLLClaimExternalInterfaceBL = new LLClaimExternalInterfaceBL();

		tDReturn = tLLClaimExternalInterfaceBL.getPosAddUpPay(pContNo, pPolNo,
				pDutyCode, pDate, "P");
		logger.debug(tDReturn);

		// logger.debug("C30".substring(0,2));

	}

}
