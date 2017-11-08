package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.GrpUWIssuePolPrintBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGeneralDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpIssuePolDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpIssuePolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGeneralSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 团体新契约录入完毕
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author heyq
 * @version 1.0
 */

public class GrpInputConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpInputConfirmAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mIssueFlag; // 是否有问题件flag
	private String mOutIssueFlag; // 是否是外部问题件flag

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;

	private String mGrpContNo;
	private String mContSql;
	private String mGrpContSql;
	private String[] mGrpPolSql;
	private String mNoticeType;
	private String mCustomerNoticeType;
	private String mPrtSeq1;
	private String mPrtSeq2;
	private LOPRTManagerSet mLOPRTManagerSet;
	private MMap mMap;
	private LCGrpIssuePolSchema mLCGrpIssuePolSchema;

	public GrpInputConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}
		// 校验某些核保规则 卡单没有人工核保 工作流无法返回 所以要在录入岗进行校验
		if(!checkUW()){
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.add(this.mMap);
		map.put(mContSql, "UPDATE");
		// map.put(mLCGrpContSchema, "UPDATE");
		 map.put(mGrpContSql, "UPDATE");
		 for (int i = 0; i < mGrpPolSql.length; i++) {
		 map.put(mGrpPolSql[i], "UPDATE");
		 }
		mResult.add(map);
		return true;
	}

	/**
	 * 核保校验
	 * 
	 * @return
	 * */
	private boolean checkUW(){
		//1.所有被保险人均属于某一保险套餐 mGrpContNo
		SSRS tSSRS =new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		String tCount1 =tExeSQL.getOneValue(" select count(1) from lcinsured a where grpcontno ='"+mGrpContNo+"' and contplancode is null"
				                           +" and exists (select 1 from lcpol where contno =a.contno and riskcode in ('311603','211607','241601','241804'))");
		if(!"0".equals(tCount1)){
			CError.buildErr(this, "所有被保人均应该属于某一保险套餐！");
			return false;
		}
		//2.每一被保人份数不超过套餐限制
		return true;
	}
	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		// 增加统括保单的校验
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tsql = "select distinct executecom from lcinsured where grpcontno='"
				+ mGrpContNo + "'";
		tSSRS = tExeSQL.execSQL(tsql);
		if (tSSRS.MaxRow > 1) {
			LCGeneralDB tLCGeneralDB = new LCGeneralDB();
			LCGeneralSet tLCGeneralSet = new LCGeneralSet();
			tsql = "select  * from LCGeneral where grpcontno='" + mGrpContNo
					+ "'";
			tLCGeneralSet = tLCGeneralDB.executeQuery(tsql);
			if (tLCGeneralSet == null || tLCGeneralSet.size() == 0) {
				CError.buildErr(this,"您没有做分单定制！");
				return false;

			}
		}

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this,"查询集体合同信息失败，请确认是否录入正确!");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet.size() == 0) {
			CError.buildErr(this,"未录入集体保单信息！");
			return false;
		}
		// 增加分红校验
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			tsql = "select count(distinct bonusgetmode)  from lcduty where polno in (select polno from lcpol where grppolno='"
					+ tLCGrpPolSet.get(i).getGrpPolNo()
					+ "' and poltypeflag<>'2' and riskcode in (select riskcode  from lmriskapp where bonusflag='1')) ";
			SSRS tttSSRS = new SSRS();
			tttSSRS = tExeSQL.execSQL(tsql);
			if (tttSSRS.MaxRow > 0) {
				if (tttSSRS.GetText(1, 1).compareTo("1") > 0) {
					CError.buildErr(this,"存在分红险下被保人选择不同的红利分配方式！");
					return false;

				}
			}
		}
		// 校验此险种是否已经录入被保人
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			// 校验团单下是否有被保险人
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = new LCContSet();
			String sql = "select * From lccont where grpcontno='" + mGrpContNo
					+ "'";
			tLCContSet = tLCContDB.executeQuery(sql);
			if (tLCContSet == null || tLCContSet.size() == 0) {
				CError.buildErr(this,"团单下未录入被保人！");
				return false;

			} else {
				LCContSchema tLCContSchema;
				LCPolDB tLCPolDB = new LCPolDB();
				for (int t = 1; t <= tLCContSet.size(); t++) {
					tLCContSchema = new LCContSchema();
					tLCContSchema = tLCContSet.get(t);

					LCPolSet tLCPolSet = new LCPolSet();
					String msql = "select * From lcpol where contno='"
							+ tLCContSchema.getContNo() + "'";
					tLCPolSet = tLCPolDB.executeQuery(msql);
					logger.debug(msql);
					if (tLCPolSet == null || tLCPolSet.size() == 0) {
						CError.buildErr(this,"团单下存在被保人未选择险种！");
						return false;

					}
				}
			}
			// if (tLCGrpPolSet.get(i).getPeoples2()==0)
			// {
			// CError tError = new CError();
			// tError.moduleName = "GrpFirstWorkFlowCheck";
			// tError.functionName = "checkData";
			// tError.errorMessage = "险种" + tLCGrpPolSet.get(i).getRiskCode() +
			// "下未录入被保人，请删除此险种或录入被保险人！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			tExeSQL = new ExeSQL();
			String tSql = "select distinct 1 from lcpol where 1=1 "
					+ " and grpcontno = '" + mGrpContNo + "' and riskcode = '"
					+ tLCGrpPolSet.get(i).getRiskCode() + "'";
			String rs = tExeSQL.getOneValue(tSql);
			if (rs == null || rs.length() == 0) {
				CError.buildErr(this,"集体险种下未录入个人险种信息！");
				return false;
			}
		}
		// 如果存在万能产品，那么企业缴费必须录入归属
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		String ql = "select * From lcduty where polno in (select polno from lcpol where grpcontno='"
				+ mGrpContNo + "') and dutycode='188003'";
		tLCDutySet = tLCDutyDB.executeQuery(ql);
		if (tLCDutySet != null && tLCDutySet.size() > 0) {
			for (int t = 1; t <= tLCDutySet.size(); t++) {
				if (tLCDutySet.get(t).getAscriptionRuleCode() == null
						|| tLCDutySet.get(t).getAscriptionRuleCode().trim()
								.equals("")) {
					CError.buildErr(this,"个人交费中的企业交费责任存在没有选择归属规则的数据！");
					return false;

				}
			}
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据Operate失败!");
			return false;
		}

		mOperate = cOperate;
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中MissionID失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this,"前台传输业务数据中mContNo失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LCGrpIssuePolDB aLCGrpIssuePolDB = new LCGrpIssuePolDB();
		String aSQL = "select * from LCGrpIssuePol where Grpcontno='"
				+ mGrpContNo + "' " + " and (state = '0' or state is null)";
		// logger.debug("111111111111aSQL===" + aSQL);
		LCGrpIssuePolSet aLCGrpIssuePolSet = aLCGrpIssuePolDB
				.executeQuery(aSQL);
		// 有问题件

		if (aLCGrpIssuePolSet.size() > 0) {
			this.mIssueFlag = "1";
		} else {
			this.mIssueFlag = "0";
		}

		this.mNoticeType = "0";
		this.mCustomerNoticeType = "0";

		// 有外部问题件
		// 问题件类型：1-内部 2-外部

		aSQL = aSQL + " and BackObjType='2'";
		LCGrpIssuePolSet bLCGrpIssuePolSet = aLCGrpIssuePolDB
				.executeQuery(aSQL);
		// logger.debug("22222222222aSQL===" + aSQL);
		if (bLCGrpIssuePolSet.size() > 0) {
			this.mOutIssueFlag = "1";
			// 如果是外部问题件，发送外部问题件通知书
			// 添加相关业务类
			// 为调用打印新契约通知书准备数据
			if (!prepareLCGrpCont()) {
				CError.buildErr(this,"不存在符合该合同号的合同信息!");
				return false;

			}
			GrpUWIssuePolPrintBL aUWGrpIssuePolPrintBL = new GrpUWIssuePolPrintBL();
			VData aVData = new VData();
			aVData.add(this.mGlobalInput);
			aVData.add(this.mLCGrpContSchema);
			boolean tResult = aUWGrpIssuePolPrintBL.submitData(aVData, "");
			if (tResult) {
				mMap = (MMap) aUWGrpIssuePolPrintBL.getResult()
						.getObjectByObjectName("MMap", 0);
				mLOPRTManagerSet = (LOPRTManagerSet) aUWGrpIssuePolPrintBL
						.getResult()
						.getObjectByObjectName("LOPRTManagerSet", 0);

				this.mNoticeType = "0";
				this.mCustomerNoticeType = "0";
				// 将打印序列号赋值
				logger.debug("mLOPRTManagerSet.size()"
						+ mLOPRTManagerSet.size());
				logger.debug("prtseq:"
						+ mLOPRTManagerSet.get(1).getPrtSeq());
				for (int i = 0; i < this.mLOPRTManagerSet.size(); i++) {
					if (this.mLOPRTManagerSet.get(i + 1).getCode().equals("54")) {
						this.mPrtSeq1 = this.mLOPRTManagerSet.get(i + 1)
								.getPrtSeq(); // 一般通知书打印序列号
						this.mNoticeType = "1";

					} else if (this.mLOPRTManagerSet.get(i + 1).getCode()
							.equals("56")) {
						this.mPrtSeq2 = this.mLOPRTManagerSet.get(i + 1)
								.getPrtSeq(); // 客户合并打印序列号
						this.mCustomerNoticeType = "1";

					}
				}

			} else {
				this.mErrors.copyAllErrors(aUWGrpIssuePolPrintBL.mErrors);
			}

		} else {
			this.mOutIssueFlag = "0";
		}
		// logger.debug("==this.mIssueFlag==" + this.mIssueFlag);
		// logger.debug("==this.mOutIssueFlag==" + this.mOutIssueFlag);
		// logger.debug("==this.mNoticeType==" + this.mNoticeType);
		// logger.debug("==this.mCustomerNoticeType==" +
		// this.mCustomerNoticeType);
		// logger.debug("判断结束！");

		// 为团体合同表存入录入人、录入时间
		mLCGrpContSchema.setInputOperator(mOperater);
		mLCGrpContSchema.setInputDate(PubFun.getCurrentDate());
		mLCGrpContSchema.setInputTime(PubFun.getCurrentTime());

		// 为合同表存入录入人、录入时间
		mContSql = "update lCGrpcont set InputOperator ='" + mOperater + "',"
				+ "InputDate = '" + PubFun.getCurrentDate() + "',"
				+ "InputTime = '" + PubFun.getCurrentTime() + "'"
				+ " where grpcontno = '" + mGrpContNo + "'";

		// 重新对个人合同，集体险种，集体合同进行重新统计合计人数，合计保费，合计保额
		 if (!sumData()) {
		 return false;
		 }

		return true;

	}

	/**
	 * sumData 进行保费、保额的汇总
	 * 
	 * @return boolean
	 */
	private boolean sumData() {
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		tLCGrpPolSet = tLCGrpPolDB.query();
		mGrpPolSql = new String[tLCGrpPolSet.size()];
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			//tongmeng 2009-03-23 modify
			//修改团单的payenddate
			String tGrpPolNo = tLCGrpPolSet.get(i).getGrpPolNo();
			mGrpPolSql[i - 1] = "update lcgrppol set prem = (select sum(prem) from lcpol where grppolno = '"
					+ tGrpPolNo
					+ "'),"
					+ "amnt = (select sum(amnt) from lcpol where grppolno = '"
					+ tGrpPolNo
					+ "'),"
					+ "peoples2 = (select sum(InsuredPeoples) from lcpol where grppolno = '"
					+ tGrpPolNo + "'), "
					+ " payenddate=(select max(payenddate) from lcpol where grppolno='"+tGrpPolNo+"') "
					+ " where grppolno = '" + tGrpPolNo + "'";
			;
		}

		mGrpContSql = "update lcgrpcont set prem = (select sum(prem) from lccont where grpcontno = '"
				+ mGrpContNo
				+ "'),"
				+ "amnt=(select sum(amnt) from lccont where grpcontno = '"
				+ mGrpContNo
				+ "'),"
				+ "Peoples = (select sum(Peoples) from lccont where grpcontno = '"
				+ mGrpContNo + "')" + " where grpcontno = '" + mGrpContNo + "'";
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ProposalGrpContNo", mLCGrpContSchema
				.getProposalGrpContNo());
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());

		// 加入问题件相关变量 zhangxing 20050516
		mTransferData.setNameAndValue("IssueFlag", this.mIssueFlag);
		mTransferData.setNameAndValue("OutIssueFlag", this.mOutIssueFlag);
		mTransferData.setNameAndValue("NoticeType", this.mNoticeType);
		mTransferData.setNameAndValue("CustomerNoticeType",
				this.mCustomerNoticeType);
		if (this.mNoticeType.equals("1")) {
			mTransferData.setNameAndValue("PrtSeq1", this.mPrtSeq1);

		}
		if (this.mCustomerNoticeType.equals("1")) {
			mTransferData.setNameAndValue("PrtSeq2", this.mPrtSeq2);

		}

		return true;
	}

	/**
	 * 通过ContNo查询LCCont表的信息
	 * 
	 * @return boolean
	 * 
	 */
	private boolean prepareLCGrpCont() {
		LCGrpContDB aLCGrpContDB = new LCGrpContDB();
		aLCGrpContDB.setGrpContNo(this.mGrpContNo);
		LCGrpContSet aLCGrpContSet = aLCGrpContDB.query();
		if (aLCGrpContSet.size() == 0) {
			CError.buildErr(this,"通过合同号查询LCCont表没有数据!");
			return false;

		}
		this.mLCGrpContSchema = aLCGrpContSet.get(1);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
