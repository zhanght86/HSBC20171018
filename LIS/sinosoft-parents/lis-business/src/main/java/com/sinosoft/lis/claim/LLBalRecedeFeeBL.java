package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LASPayPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LOPRTManagerSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LLJSPayPersonSchema;
import com.sinosoft.lis.schema.LLJSPaySchema;
import com.sinosoft.lis.vschema.LASPayPersonSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LLJSPayPersonSet;
import com.sinosoft.lis.vschema.LLJSPaySet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * Description: 退出险日期以后的应收保费，用于合同处理，豁免处理
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
public class LLBalRecedeFeeBL {
private static Logger logger = Logger.getLogger(LLBalRecedeFeeBL.class);

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

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mClmNo = ""; // 赔案号

	private String mCalDate = ""; // 计算时点
	private String mDealDate = ""; // 处理时间,1-包括计算时点,2-不包括计算时点
	private String mDealType = ""; // 处理方式,1-指定日期之后,2-指定日期之前
	private String mDealMode = ""; // 处理模式,1-直接删除,2-不删除
	private String mDealSql = ""; // 指定查找的SQL语句

	public LLBalRecedeFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------退指定日期以后的保费-----LLBalRecedeFee测试-----开始----------");

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

		// if (!pubSubmit())
		// {
		// return false;
		// }

		logger.debug("----------退指定日期以后的保费-----LLBalRecedeFee测试-----结束----------");
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
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mCalDate = (String) mTransferData.getValueByName("CalDate"); // 计算时点
		this.mDealDate = StrTool.cTrim((String) mTransferData
				.getValueByName("DealDate")); // 处理时间,1-包括计算时点,2-不包括计算时点
		this.mDealType = StrTool.cTrim((String) mTransferData
				.getValueByName("DealType")); // 处理方式,1-指定日期之后,2-指定日期之前
		this.mDealMode = StrTool.cTrim((String) mTransferData
				.getValueByName("DealMode")); // 处理模式,1-直接删除,2-不删除
		this.mDealSql = StrTool.cTrim((String) mTransferData
				.getValueByName("DealSql")); // 指定查找的SQL语句
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (!getLLJSPay(mLCPolSchema, this.mCalDate)) {
			return false;
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－进行退费操作－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 进行个人应收总表退费处理,将信息放入理赔暂存表
	 * 
	 * @param
	 * @return
	 */
	private boolean getLLJSPay(LCPolSchema pLCPolSchema, String pDate) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * mDealType = 1 -- 豁免处理，需要退掉豁免起期之前的保费 并且 包括出险时点 其他，需要退掉出险时点 之后 的应收保费
		 * 
		 * --------<------出险时点5.1---<---应收,实收[退费]6.1-------- --------<------豁免起期5.1---<=-------豁免[退费]6.1--------
		 * 
		 * DealDate:处理时间,1-包括计算时点,2-不包括计算时点 DealType:处理方式,1-指定日期之后,2-指定日期之前
		 * DealMode:处理模式,1-直接删除,2-不删除
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LJSPaySet tLJSPaySaveSet = new LJSPaySet();
		LJSPayPersonSet tLJSPayPersonSaveSet = new LJSPayPersonSet();

		LLJSPaySet tLLJSPaySaveSet = new LLJSPaySet();
		LLJSPayPersonSet tLLJSPayPersonSaveSet = new LLJSPayPersonSet();

		LASPayPersonSet tLASPayPersonSaveSet = new LASPayPersonSet();
		LOPRTManagerSet tLOPRTManagerSaveSet = new LOPRTManagerSet();
		LOPRTManagerSubSet tLOPRTManagerSubSaveSet = new LOPRTManagerSubSet();

		LCPolSet tLCPolSaveSet = new LCPolSet();
		LCDutySet tLCDutySaveSet = new LCDutySet();
		LCPremSet tLCPremSaveSet = new LCPremSet();
		LCGetSet tLCGetSaveSet = new LCGetSet();

		String tContNo = StrTool.cTrim(pLCPolSchema.getContNo());
		String tPolNo = StrTool.cTrim(pLCPolSchema.getPolNo());

		String tSql = "";

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 根据传入条件，拼装成SQL语句
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = " select * from LJSPay where 1=1 "
				+ " and GetNoticeNo in (select distinct GetNoticeNo from LJSPayPerson where 1=1 "
				+ " and (ContNo = '" + "?ContNo?" + "' or PolNo = '" + "?PolNo?"
				+ "' )";

		if (mDealDate.equals("1") && mDealType.equals("1")) {
			tSql = tSql + " and LastPayToDate>= to_date('" + "?pDate?"
					+ "','yyyy-mm-dd') )";
		} else if (mDealDate.equals("2") && mDealType.equals("1")) {
			tSql = tSql + " and LastPayToDate > to_date('" + "?pDate?"
					+ "','yyyy-mm-dd') )";

		}

		if (mDealDate.equals("1") && mDealType.equals("2")) {
			tSql = tSql + " and LastPayToDate<= to_date('" + "?pDate?"
					+ "','yyyy-mm-dd') )";
		} else if (mDealDate.equals("2") && mDealType.equals("2")) {
			tSql = tSql + " and LastPayToDate < to_date('" + "?pDate?"
					+ "','yyyy-mm-dd') )";

		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 如果传入了指定的SQL语句，则调用
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mDealSql.length() > 0) {
			tSql = mDealSql;
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", tContNo);
		sqlbv.put("PolNo", tPolNo);
		sqlbv.put("pDate", pDate);
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);

		logger.debug("------------------------------------------------------");
		logger.debug("--个人应收汇总信息[" + tLJSPaySet.size() + "]");
		logger.debug("--SQL:" + tSql);
		logger.debug("------------------------------------------------------");
		if (tLJSPayDB.mErrors.needDealError() == true) {
			this.mErrors.addOneError("查询个人应收汇总信息发生错误!"
					+ tLJSPayDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 将数据放入暂存表,准备审批通过后删除
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLJSPaySet.size(); i++) {
			LJSPaySchema tLJSPaySchema = tLJSPaySet.get(i);

			String tGetNoticeNo = tLJSPaySchema.getGetNoticeNo();
			String tBankOnTheWayFlag = StrTool.cTrim(tLJSPaySchema
					.getBankOnTheWayFlag());// 1--银行在途标志，不允许删除

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 处理银行在途
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tBankOnTheWayFlag.equals("1")) {
				mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
						+ tContNo + "]的交费信息银行在途,不允许删除其应收记录,等回销后在进行此操作!!!");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.2
			 * 处理暂交费，如果为空，可以删除应收汇总记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LJTempFeeSet tLJTempFeeSet = mLLClaimPubFunBL
					.getLJTempFee(tGetNoticeNo);
			if (tLJTempFeeSet != null) {
				mErrors
						.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
								+ tContNo
								+ "]存在暂交费记录,不允许删除其应收记录，请先进行暂交费退费,然后再进行此操作!!!");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.3 将应收汇总记录放入理赔暂存表中
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLJSPaySchema tLLJSPaySchema = new LLJSPaySchema();
			mReflections.transFields(tLLJSPaySchema, tLJSPaySchema);
			tLLJSPaySchema.setClmNo(this.mClmNo);
			tLLJSPaySaveSet.add(tLLJSPaySchema);

			tLJSPaySaveSet.add(tLJSPaySchema);// 准备删除

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.4 处理应收明细记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setGetNoticeNo(tGetNoticeNo);
			LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();

			logger.debug("------------------------------------------------------");
			logger.debug("--个人应收明细信息[" + tLJSPayPersonSet.size() + "]");
			logger.debug("------------------------------------------------------");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.5 对应收明细记录进行判断
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tLJSPayPersonDB.mErrors.needDealError() == true) {
				this.mErrors.addOneError("查询个人应收明细信息发生错误!"
						+ tLJSPayPersonDB.mErrors.getError(0).errorMessage);
				return false;
			}

			if (tLJSPayPersonSet.size() == 0) {
				mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
						+ tContNo + "]个人应收明细信息的记录数为空,不能进行此操作!!!");
				return false;
			}

			tLJSPayPersonSaveSet.add(tLJSPayPersonSet);// 准备删除

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.6 将应收明细记录放入理赔暂存表中
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int j = 1; j <= tLJSPayPersonSet.size(); j++) {
				LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet
						.get(j);

				LLJSPayPersonSchema tLLJSPayPersonSchema = new LLJSPayPersonSchema();
				mReflections.transFields(tLLJSPayPersonSchema,
						tLJSPayPersonSchema);
				tLLJSPayPersonSchema.setClmNo(this.mClmNo);

				tLLJSPayPersonSaveSet.add(tLLJSPayPersonSchema);
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.7 处理 渠道 应收数据
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LASPayPersonDB tLASPayPersonDB = new LASPayPersonDB();
			tLASPayPersonDB.setGetNoticeNo(tGetNoticeNo);
			LASPayPersonSet tLASPayPersonSet = tLASPayPersonDB.query();
			tLASPayPersonSaveSet.add(tLASPayPersonSet);
			logger.debug("------------------------------------------------------");
			logger.debug("--渠道应收明细信息[" + tLASPayPersonSet.size() + "]");
			logger.debug("------------------------------------------------------");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.8 处理 打印管理表的数据
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
//
//			tSql = "select * from LOPRTManager where 1=1 "
//					+ " and otherno='"
//					+ tContNo
//					+ "'"
//					+ " and (code in ('31','32') or code in ('35','36')) and othernotype ='00'"
//					+ " and ((Standbyflag1='" + tGetNoticeNo
//					+ "') or (Standbyflag2='" + tGetNoticeNo + "'))";
			tSql = "select * from LOPRTManager where otherno='"+"?tContNo?"+ "' and (code in ('47','32') or code in ('35','36')) and othernotype ='02' "
				 + "and ((Standbyflag1='" +"?tGetNoticeNo?"+ "') or (Standbyflag2='" +"?tGetNoticeNo?"+ "'))";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tContNo", tContNo);
			sqlbv1.put("tGetNoticeNo", tGetNoticeNo);
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB
					.executeQuery(sqlbv1);

			logger.debug("------------------------------------------------------");
			logger.debug("--打印管理表汇总信息[" + tLOPRTManagerSet.size() + "]");
			logger.debug("--SQL:" + tSql);
			logger.debug("------------------------------------------------------");

			if (tLOPRTManagerDB.mErrors.needDealError() == true) {
				this.mErrors.addOneError("查询打印管理表汇总信息发生错误!!!");
				return false;
			}
			tLOPRTManagerSaveSet.add(tLOPRTManagerSet);

			LOPRTManagerSubDB tLOPRTManagerSubDB = new LOPRTManagerSubDB();
			tLOPRTManagerSubDB.setGetNoticeNo(tGetNoticeNo);
			LOPRTManagerSubSet tLOPRTManagerSubSet = tLOPRTManagerSubDB.query();
			tLOPRTManagerSubSaveSet.add(tLOPRTManagerSubSet);
			logger.debug("------------------------------------------------------");
			logger.debug("--打印管理表明细信息[" + tLOPRTManagerSubSet.size()
					+ "]");
			logger.debug("------------------------------------------------------");

		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 处理 该合同 下的所有险种
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCPolSet tLCPolSet = mLLClaimPubFunBL.getLCPolSet(tContNo);
		if (tLCPolSet == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		for (int i = 1; i <= tLCPolSet.size(); i++) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.2 声明变量
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			String tAppFlag = StrTool.cTrim(tLCPolSchema.getAppFlag());
			String tTmpPolNo = StrTool.cTrim(tLCPolSchema.getPolNo());
			String tTmpMainPolNo = StrTool.cTrim(tLCPolSchema.getMainPolNo());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.4 校验主险是否失效
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 * 
			 * if (tTmpPolNo.equals(tTmpMainPolNo)) { tSql = "select * from
			 * lccontstate where 1=1 " +" and ContNo= '"+tContNo+"'" +" and
			 * PolNo = '"+tTmpPolNo+"'" +" and StateType in ('Available') and
			 * state='1'" +" and StartDate<='"+CurrentDate+"'" +" and enddate
			 * is null";
			 * 
			 * LCContStateDB tLCContStateDB = new LCContStateDB();
			 * LCContStateSet tLCContStateSet = new LCContStateSet();
			 * tLCContStateSet = tLCContStateDB.executeQuery(tSql); if
			 * (tLCContStateSet.size()>0) {
			 * mErrors.addOneError("合同号["+tContNo+"]主险失效,不能进行此操作!!!"); return
			 * false; } }
			 */

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.8 处理 该合同
			 * 下抽档未核销的所有险种信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tAppFlag.equals("9")) {
				LCDutySet tLCDutySet = mLLClaimPubFunBL.getLCDuty(tTmpPolNo);
				if (tLCDutySet == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				LCPremSet tLCPremSet = mLLClaimPubFunBL
						.getLCPremForPol(tTmpPolNo);
				if (tLCPremSet == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				LCGetSet tLCGetSet = mLLClaimPubFunBL.getLCGetForPol(tTmpPolNo);
				if (tLCGetSet == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				tLCPolSaveSet.add(tLCPolSchema);
				tLCDutySaveSet.add(tLCDutySet);
				tLCPremSaveSet.add(tLCPremSet);
				tLCGetSaveSet.add(tLCGetSet);
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql("delete from lcrnewstatehistory where proposalno = '"
						+ "?proposalno?" + "'");
				sqlbv2.put("proposalno", tLCPolSchema.getPolNo());
				mMMap.put(sqlbv2, "DELETE");
			}

		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.1
		 * DealMode:处理模式,1-直接删除,2-不删除
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mDealMode.equals("1")) {
			mMMap.put(tLJSPaySaveSet, "DELETE");
			mMMap.put(tLJSPayPersonSaveSet, "DELETE");
		}

		mMMap.put(tLLJSPaySaveSet, "DELETE&INSERT");
		mMMap.put(tLLJSPayPersonSaveSet, "DELETE&INSERT");
		mMMap.put(tLASPayPersonSaveSet, "DELETE");
		mMMap.put(tLOPRTManagerSaveSet, "DELETE");
		mMMap.put(tLOPRTManagerSubSaveSet, "DELETE");

		mMMap.put(tLCPolSaveSet, "DELETE");
		mMMap.put(tLCDutySaveSet, "DELETE");
		mMMap.put(tLCPremSaveSet, "DELETE");
		mMMap.put(tLCGetSaveSet, "DELETE");

		return true;
	}

	/**
	 * [废弃不用]进行个人应收表退费处理,将信息放入理赔暂存表
	 * 
	 * @param tLLExemptSchema
	 * @return
	 */
	private boolean getLLJSPayPerson(LCPolSchema pLCPolSchema, String pDate) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 mDealType = 1 --
		 * 豁免处理，需要退掉豁免起期之前的保费 并且 包括出险时点 其他，需要退掉出险时点 之后 的应收保费
		 * 
		 * -----豁免[退费]4.1---<---出险时点5.1---<---应收,实收[退费]6.1-----
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		tSql = "select * from LJSPayPerson where 1=1 " + " and PolNo = '"
				+ "?PolNo?" + "'";

		if (mDealDate.equals("1") && mDealType.equals("1")) {
			tSql = tSql + " and LastPayToDate >= to_date('" + "?pDate?"
					+ "','yyyy-mm-dd') ";
		} else {
			tSql = tSql + " and LastPayToDate > to_date('" + "?pDate?"
					+ "','yyyy-mm-dd') ";
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("PolNo", pLCPolSchema.getPolNo());
		sqlbv3.put("pDate", pDate);
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv3);
		logger.debug("------------------------------------------------------");
		logger.debug("--个人应收明细信息[" + tLJSPayPersonSet.size() + "]:"
				+ tSql);
		logger.debug("------------------------------------------------------");
		if (tLJSPayPersonDB.mErrors.needDealError() == true) {
			this.mErrors.addOneError("查询个人应收信息发生错误!"
					+ tLJSPayPersonDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 将数据放入暂存表,准备审批通过后删除
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLJSPaySet tLLJSPaySet = new LLJSPaySet();
		LLJSPayPersonSet tLLJSPayPersonSet = new LLJSPayPersonSet();
		for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
			LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet.get(i);

			LLJSPayPersonSchema tLLJSPayPersonSchema = new LLJSPayPersonSchema();
			mReflections.transFields(tLLJSPayPersonSchema, tLJSPayPersonSchema);
			tLLJSPayPersonSchema.setClmNo(this.mClmNo);

			tLLJSPayPersonSet.add(tLLJSPayPersonSchema);
		}

		mMMap.put(tLLJSPaySet, "DELETE&INSERT");
		mMMap.put(tLLJSPayPersonSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－进行退费操作－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);

		mResult.clear();
		mResult.add(mMMap);
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
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		String tClaimFeeCode = "abcdefg";
		tClaimFeeCode = tClaimFeeCode.substring(1);

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86000000";
		tG.ComCode = "86110000";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", "90000004960");
		tTransferData.setNameAndValue("AccDate", "2005-07-22");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimCalPortalBL tLLClaimCalPortalBL = new LLClaimCalPortalBL();
		tLLClaimCalPortalBL.submitData(tVData, "");
	}

}
