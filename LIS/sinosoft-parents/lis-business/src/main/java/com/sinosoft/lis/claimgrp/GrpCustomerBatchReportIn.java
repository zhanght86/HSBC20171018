package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
import com.sinosoft.service.BusinessService;
import java.io.*;

import javax.xml.parsers.*;
import java.util.*;
import org.w3c.dom.*;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import com.sinosoft.workflow.claimgrp.*;

public class GrpCustomerBatchReportIn implements BusinessService{
private static Logger logger = Logger.getLogger(GrpCustomerBatchReportIn.class);

	// 错误处理类
	public CErrors logErrors = new CErrors();
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private VData mInputData = new VData();// 保存界面传入的信息,以及解析后的数据
	private VData tVData = null;// 保存解析后的数据
	private MMap tMap = null;
	private MMap tMMap = new MMap();
	private VData mResult = new VData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	// 文件路径
	private String FileName;
	private String FilePath = "";// 文件存放路径
	private String SaveExcelPath = "";// 上传的Excel文件存放的最里层目录
	private String ImportAbsoluteExcelPath;// 存放上传Excel的绝对路径

	// 案件信息
	private LLReportSchema mLLReportSchema = new LLReportSchema();// 报案信息集合
	private LLSubReportSet mLLSubReportSet = null;// 出险人信息集合
	private LLReportReasonSet mLLReportReasonSet = null;// 报案理赔类型集合
	private LLCaseRelaSet mLLCaseRelaSet = null;// 报案事件表
	private LCGrpCustomerImportLogSet tLCGrpCustomerImportLogSet = new LCGrpCustomerImportLogSet();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	// 操作类型
	private String mOperate = "";// 操作类型
	private String AccNo = "";// 事件号
	private String mTempRptNo = "";
	/** 错误标记 */
	private boolean mErrorFlag = false;

	// LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema = new
	// LCGrpCustomerImportLogSchema();
	// LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema = new
	// LCGrpCustomerImportLogSchema();

	public GrpCustomerBatchReportIn() {

	}

	public boolean submitData(VData cInputData, String cOperate) {

		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;

		logger.debug("----------GrpCustomerBatchReportIn after getInputData ----------");

		// 检查数据
		if (!checkData()) {
			return false;
		}

		logger.debug("----------GrpCustomerBatchReportIn after checkData----------");
		logger.debug("开始时间:" + PubFun.getCurrentTime());

		try {

			// 把Excel转换为Schema,数据处理
			if (!ParseExcelToSchema()) {
				return false;
			}

			logger.debug("----------GrpCustomerBatchReportIn after ParseXml----------");
		}

		catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			CError.buildErr(this, "导入文件格式有误!");
			return false;
		}

		logger.debug("结束时间:" + PubFun.getCurrentTime());

		// if(!DealErrors()){
		//    	
		// }

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {

		logger.debug("--start getInputData()");
		// 获取输入信息

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mTransferData == null && mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "数据传输失败!");
			return false;
		}

		SaveExcelPath = (String) mTransferData.getValueByName("SaveExcelPath");
		logger.debug("后台获得的SaveExcelPath:" + SaveExcelPath);

		FileName = (String) mTransferData.getValueByName("FileName");
		logger.debug("后台获得的FileName:" + FileName);

		mLLReportSchema = (LLReportSchema) mInputData.getObjectByObjectName(
				"LLReportSchema", 0);
		mTempRptNo = mLLReportSchema.getRptNo();

		return true;

	}

	/**
	 * 解析Excel转换为报案的数据
	 * 
	 * @return
	 */
	private boolean ParseExcelToSchema() {

		String ttID = "";
		int I = 0;
		String ttcustomerNo = "";
		String ttcustomerName = "";
		String ttflagRgtNo = "";

		// Vector vec=new Vector();

		// 校验待导入的文件是否存在
		if (!initImportFile())
			return false;

		logger.debug("*************结束校验待导入的文件*************");

		// 解析Excel中的数据生成记录
		if (!parseData())
			return false;

		logger.debug("*************结束执行Excel文件的解析*************");

		// 进行批量导入的校验
		if (!checkBatchData())
			// return false;

			logger.debug("*************结束批量导入的校验*************");

		// 准备数据
		if (!prepareData()) {
			return false;
		}
		logger.debug("*************准备数据结束*************");

		if (mErrorFlag == false) {
			// 数据处理,新增案件
			if (mOperate.equals("FirstInsert")) {

				// 提交工作流
				if (!commitWorkFlow()) {
					return false;
				}
			}
			// 增加出险人
			else {

				// 提交数据库处理
				if (!insertCustomer()) {
					return false;
				}
			}
		}

		logger.debug("*************提交后台处理结束*************");
		if (mErrorFlag == true) {
			// 保存错误信息到LCGrpCustomerImportLog表中
			if (!DeatErrors()) {
				return false;
			}
			return false;
		}

		return true;

	}

	/**
	 * 增加出险人,提交数据库
	 * 
	 */
	private boolean insertCustomer() {

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(tVData, "")) {
			// @@错误处理
			CError.buildErr(this, "增加出险人失败！");
			tPubSubmit = null;
			return false;

		}

		tPubSubmit = null;
		return true;
	}

	/**
	 * 提交工作流处理
	 * 
	 */
	private boolean commitWorkFlow() {

		GrpClaimWorkFlowBL tGrpClaimWorkFlowBL = new GrpClaimWorkFlowBL();
		logger.debug("---GrpClaimWorkFlowBL submitData---");

		if (!tGrpClaimWorkFlowBL.submitData(tVData, "9999999998")) {

			CError.buildErr(this, "创建工作流节点失败！");

			tMap = null;
			if (mLLReportSchema.getRptNo() != null
					&& !mLLReportSchema.getRptNo().equals("")) {

				tMap.put("delete from llreport where rptno='"
						+ mLLReportSchema.getRptNo() + "' ", "DELETE");
				tMap.put("delete from llsubreport where subrptno='"
						+ mLLReportSchema.getRptNo() + "' ", "DELETE");
				tMap.put("delete from llreportreason where rpno='"
						+ mLLReportSchema.getRptNo() + "' ", "DELETE");
				tMap
						.put(
								"delete from llaccident where accno in (select subrptno from llreportrela rpno='"
										+ mLLReportSchema.getRptNo() + "' )",
								"DELETE");
				tMap
						.put(
								"delete from llaccidentsub where accno in (select subrptno from llreportrela rpno='"
										+ mLLReportSchema.getRptNo() + "' ) ",
								"DELETE");
				tMap.put("delete from llreportrela where rptno='"
						+ mLLReportSchema.getRptNo() + "' ", "DELETE");
				tMap.put("delete from llcaserela where caseno='"
						+ mLLReportSchema.getRptNo() + "' ", "DELETE");

				mInputData.clear();
				mInputData.add(tMap);

				PubSubmit tPubSubmit = new PubSubmit();

				if (!tPubSubmit.submitData(mInputData, "")) {
					// @@错误处理
					CError.buildErr(this, "数据提交失败！");
					tPubSubmit = null;
					return false;

				}

				tPubSubmit = null;
			}
			return false;
		}

		return true;
	}

	/**
	 * 准备传往工作流处理的数据
	 * 
	 */
	private boolean prepareData() {

		tVData = new VData();
		tMap = new MMap();
		logger.debug("LLReportSchema.getRptNo():"
				+ mLLReportSchema.getRptNo());

		// 新增案件
		if (mLLReportSchema.getRptNo() == null
				|| mLLReportSchema.getRptNo().equals("")) {

			String RptNo = "";// 报案号

			String strlimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			logger.debug("strlimit=" + strlimit);
			RptNo = PubFun1.CreateMaxNo("GRPRPTNO", strlimit);
			logger.debug("-----生成报案号= " + RptNo);

			AccNo = PubFun1.CreateMaxNo("ACCNO", 10); // 生成事件号流水号
			AccNo = "8" + AccNo;
			logger.debug("-----生成事件号= " + AccNo);

			// 事件表
			LLAccidentSchema mLLAccidentSchema = new LLAccidentSchema();// 事件表
			mLLAccidentSchema.setAccNo(AccNo); // 事件号
			mLLAccidentSchema.setAccType(mLLReportSchema.getAccidentReason());// 事故类型
			mLLAccidentSchema.setAccDate(mLLReportSchema.getAccidentDate());// 事故日期
			mLLAccidentSchema.setOperator(mGlobalInput.Operator);
			mLLAccidentSchema.setMngCom(mGlobalInput.ManageCom);
			mLLAccidentSchema.setMakeDate(PubFun.getCurrentDate());
			mLLAccidentSchema.setMakeTime(PubFun.getCurrentTime());
			mLLAccidentSchema.setModifyDate(PubFun.getCurrentDate());
			mLLAccidentSchema.setModifyTime(PubFun.getCurrentTime());

			// 报案表
			mLLReportSchema.setRptNo(RptNo);
			mLLReportSchema.setRgtClass("2");// 申请类型：1 个人,2 团体,3 家庭单
			mLLReportSchema.setRgtObj("2");// 号码类型:1个险报案号,2团险报案号
			mLLReportSchema.setRgtObjNo(mLLReportSchema.getRptNo());// 备份报案号，
			// 在立案时可能需要覆盖报案表的报案号
			// ，
			// 取这个字段保留当时生成的报案号
			mLLReportSchema.setRptDate(PubFun.getCurrentDate()); // 报案受理日期
			mLLReportSchema.setAvaiFlag("10"); // 案件有效标志10表示保存未确认
			mLLReportSchema.setRgtFlag("10");// 立案标志:10-未立案
			mLLReportSchema.setOperator(mGlobalInput.Operator);
			mLLReportSchema.setPeoples2(mLLSubReportSet.size());// 投保人数
			mLLReportSchema.setMngCom(mGlobalInput.ManageCom);
			mLLReportSchema.setMakeDate(PubFun.getCurrentDate());
			mLLReportSchema.setMakeTime(PubFun.getCurrentTime());
			mLLReportSchema.setModifyDate(PubFun.getCurrentDate());
			mLLReportSchema.setModifyTime(PubFun.getCurrentTime());

			// 报案分案关联表
			LLReportRelaSchema mLLReportRelaSchema = new LLReportRelaSchema();// 事件表
			mLLReportRelaSchema.setRptNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
			mLLReportRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); // 分案号=
			// 赔案号

			// 分案事件关联表
			LLCaseRelaSchema mLLCaseRelaSchema = new LLCaseRelaSchema();
			mLLCaseRelaSchema.setCaseRelaNo(mLLAccidentSchema.getAccNo()); // 事件号
			mLLCaseRelaSchema.setCaseNo(mLLReportSchema.getRptNo()); // 分案号=赔案号
			mLLCaseRelaSchema.setSubRptNo(mLLReportSchema.getRptNo()); //分案号=赔案号

			// 理赔类型表(多条添加)
			for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
				mLLReportReasonSet.get(i).setRpNo(mLLReportSchema.getRptNo()); // 分案号
				// =
				// 赔案号
				mLLReportReasonSet.get(i).setOperator(mGlobalInput.Operator);
				mLLReportReasonSet.get(i).setMngCom(mGlobalInput.ManageCom);
				mLLReportReasonSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLLReportReasonSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLLReportReasonSet.get(i)
						.setModifyDate(PubFun.getCurrentDate());
				mLLReportReasonSet.get(i)
						.setModifyTime(PubFun.getCurrentTime());
			}

			// 分事件表
			LLAccidentSubSet mLLAccidentSubSet = new LLAccidentSubSet();
			LLAccidentSubSchema mLLAccidentSubSchema = null;

			// 出险人信息表
			for (int i = 1; i <= mLLSubReportSet.size(); i++) {
				mLLSubReportSet.get(i).setSubRptNo(mLLReportSchema.getRptNo()); // 分案号
				// =
				// 赔案号
				mLLSubReportSet.get(i).setOperator(mGlobalInput.Operator);
				mLLSubReportSet.get(i).setMngCom(mGlobalInput.ManageCom);
				mLLSubReportSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLLSubReportSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLLSubReportSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLLSubReportSet.get(i).setModifyTime(PubFun.getCurrentTime());

				// 分事件
				mLLAccidentSubSchema = new LLAccidentSubSchema();
				mLLAccidentSubSchema.setAccNo(mLLAccidentSchema.getAccNo());
				mLLAccidentSubSchema.setCustomerNo(mLLSubReportSet.get(i)
						.getCustomerNo());
				mLLAccidentSubSet.add(mLLAccidentSubSchema);

				mLLAccidentSubSchema = null;
			}
			
			for(int i=1;i<=mLOPRTManagerSet.size();i++){
				mLOPRTManagerSet.get(i).setOtherNo(mLLReportSchema.getRptNo());
			}

			// 添加数据
			tMap.put(mLLAccidentSchema, "DELETE&INSERT");// 事件表
			tMap.put(mLLAccidentSubSet, "DELETE&INSERT");// 分事件表
			tMap.put(mLLReportSchema, "DELETE&INSERT");// 报案表
			tMap.put(mLLSubReportSet, "DELETE&INSERT");// 出险人信息表
			tMap.put(mLLReportReasonSet, "DELETE&INSERT");// 报案理赔类型表
			tMap.put(mLLCaseRelaSchema, "DELETE&INSERT");// 分案事件关联表
			tMap.put(mLLReportRelaSchema, "DELETE&INSERT");// 报案事件关联表
			tMap.put(mLOPRTManagerSet, "DELETE&INSERT");// 打印管理表

			// 准备工作流数据
			mTransferData.setNameAndValue("RptNo", mLLReportSchema.getRptNo());// 报案号
			mTransferData.setNameAndValue("RptorName", mLLReportSchema
					.getRptorName());// 报案人姓名
			mTransferData.setNameAndValue("CustomerNo", "");// 出险人客户号
			mTransferData.setNameAndValue("CustomerName", "");// 出险人姓名
			mTransferData.setNameAndValue("CustomerSex", "");// 出险人性别
			mTransferData.setNameAndValue("AccDate", mLLReportSchema
					.getAccidentDate());// 批量导入时用事故日期,不用出险日期
			mTransferData.setNameAndValue("MngCom", mGlobalInput.ManageCom);// 管理机构
			mTransferData.setNameAndValue("GrpContNo", mLLReportSchema
					.getGrpContNo());// 团体合同号
			mTransferData.setNameAndValue("RgtState", mLLReportSchema
					.getRgtState());// 报案类型

			mOperate = "FirstInsert";
		}
		// 增加出险人
		else {

			// 分事件表
			LLAccidentSubSet mLLAccidentSubSet = new LLAccidentSubSet();
			LLAccidentSubSchema mLLAccidentSubSchema = null;

			// 出险人信息表
			for (int i = 1; i <= mLLSubReportSet.size(); i++) {
				mLLSubReportSet.get(i).setSubRptNo(mLLReportSchema.getRptNo()); // 分案号
				// =
				// 赔案号
				mLLSubReportSet.get(i).setOperator(mGlobalInput.Operator);
				mLLSubReportSet.get(i).setMngCom(mGlobalInput.ManageCom);
				mLLSubReportSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLLSubReportSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLLSubReportSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLLSubReportSet.get(i).setModifyTime(PubFun.getCurrentTime());

				// 分事件
				mLLAccidentSubSchema = new LLAccidentSubSchema();
				mLLAccidentSubSchema.setAccNo(mLLCaseRelaSet.get(1)
						.getCaseRelaNo());
				mLLAccidentSubSchema.setCustomerNo(mLLSubReportSet.get(i)
						.getCustomerNo());
				mLLAccidentSubSet.add(mLLAccidentSubSchema);

				mLLAccidentSubSchema = null;
			}

			// 理赔类型表(多条添加)
			for (int i = 1; i <= mLLReportReasonSet.size(); i++) {
				mLLReportReasonSet.get(i).setRpNo(mLLReportSchema.getRptNo()); // 分案号
				// =
				// 赔案号
				mLLReportReasonSet.get(i).setOperator(mGlobalInput.Operator);
				mLLReportReasonSet.get(i).setMngCom(mGlobalInput.ManageCom);
				mLLReportReasonSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLLReportReasonSet.get(i).setMakeTime(PubFun.getCurrentTime());
				mLLReportReasonSet.get(i)
						.setModifyDate(PubFun.getCurrentDate());
				mLLReportReasonSet.get(i)
						.setModifyTime(PubFun.getCurrentTime());
			}

			// 更新报案信息
			LLReportDB tLLReportDB = new LLReportDB();
			tLLReportDB.setRptNo(mLLReportSchema.getRptNo());
			if (!tLLReportDB.getInfo()) {

				CError.buildErr(this, "根据" + mLLReportSchema.getRptNo()
						+ "查询不到报案信息!");
				return false;
			}

			mLLReportSchema.setSchema(tLLReportDB.getSchema());
			mLLReportSchema.setPeoples2(mLLSubReportSet.size()
					+ mLLReportSchema.getPeoples2());
			mLLReportSchema.setOperator(mGlobalInput.Operator);
			mLLReportSchema.setMngCom(mGlobalInput.ManageCom);
			mLLReportSchema.setMakeDate(PubFun.getCurrentDate());
			mLLReportSchema.setMakeTime(PubFun.getCurrentTime());
			mLLReportSchema.setModifyDate(PubFun.getCurrentDate());
			mLLReportSchema.setModifyTime(PubFun.getCurrentTime());

			tMap.put(mLLReportSchema, "DELETE&INSERT");
			tMap.put(mLLSubReportSet, "DELETE&INSERT");
			tMap.put(mLLAccidentSubSet, "DELETE&INSERT");
			tMap.put(mLLReportReasonSet, "DELETE&INSERT");

			// 准备工作流数据
			mTransferData.setNameAndValue("RptNo", mLLReportSchema.getRptNo());// 报案号

			mOperate = "InsertCustomer";
		}

		String tDelSql = "delete from LCGrpCustomerImportLog where batchno='"
				+ FileName + "' ";
		tMap.put(tDelSql, "DELETE");
		tVData.add(tMap);
		tVData.add(mTransferData);
		tVData.add(mGlobalInput);

		return true;

	}

	/**
	 * 校验批量导入规则
	 * 
	 */
	private boolean checkBatchData() {

		logger.debug("***************Start checkBatchData**************");

		logger.debug("LLSubReportSet.size():" + mLLSubReportSet.size());
		logger.debug("tLLReportReasonSet.size():"
				+ mLLReportReasonSet.size());
		// 首先将batchno=当前模板名的记录全部清除

		if (mLLSubReportSet.size() == 0 || mLLReportReasonSet.size() == 0) {
			CError.buildErr(this, "模板中没有任何需要导入的出险人记录!");
			return false;
		}

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		for (int i = 1; i <= mLLSubReportSet.size(); i++) {

			logger.debug("校验第" + i + "个出险人记录");
			// 校验客户是否属于该团单下

			if (mLLSubReportSet.get(i).getCustomerNo() == null
					|| mLLSubReportSet.get(i).getCustomerNo().equals("")) {
				CError.buildErr(this, "模板中第" + i + "行记录的客户号不能为空!");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "模板中第" + i + "行记录的客户号为空!";
				this.logErrors.addOneError(tError);
				// return false;
			} else {
				String tCustomerNo = mLLSubReportSet.get(i).getCustomerNo();
				String tGrpContNo = mLLReportSchema.getGrpContNo();
				String tCheckSql = "select count(1) from lcinsured where 1=1"
						+ " and insuredno='" + tCustomerNo + "'"
						+ " and grpcontno='" + tGrpContNo + "'";
				ExeSQL tExeSQL1 = new ExeSQL();
				String tCheckFlag = tExeSQL1.getOneValue(tCheckSql);
				if (!"1".equals(tCheckFlag)) {
					CError.buildErr(this, "本团单号：" + tGrpContNo + "下未查询到客户号为："
							+ tCustomerNo + "的客户信息，请核实!");
					mErrorFlag = true;
					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "本团单号：" + tGrpContNo + "下未查询到客户号为："
							+ tCustomerNo + "的客户信息，请核实!";
					this.logErrors.addOneError(tError);
					// return false;
				}
			}

			if (mLLSubReportSet.get(i).getSeqNo() == 0) {
				CError
						.buildErr(this, "客户号为"
								+ mLLSubReportSet.get(i).getCustomerNo()
								+ "的案件ID不能为空!");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "客户号为"
						+ mLLSubReportSet.get(i).getCustomerNo() + "的案件ID不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
			}

			if (mLLSubReportSet.get(i).getAccDate() == null
					|| mLLSubReportSet.get(i).getAccDate().equals("")) {
				CError
						.buildErr(this, "客户号为"
								+ mLLSubReportSet.get(i).getCustomerNo()
								+ "的出险日期不能为空!");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "客户号为"
						+ mLLSubReportSet.get(i).getCustomerNo() + "的出险日期不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
			}
			else
			{
				if(mLLClaimPubFunBL.checkDate(mLLSubReportSet.get(i).getAccDate(), PubFun.getCurrentDate())==false)
				{
					CError.buildErr(this, "客户号为"
							+ mLLSubReportSet.get(i).getCustomerNo()
							+ "的出险日期晚于当前日期!");
					mErrorFlag = true;
		
					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i).getCustomerName();
					tError.errorMessage = "客户号为"
							+ mLLSubReportSet.get(i).getCustomerNo() + "的出险日期晚于当前日期!";
					this.logErrors.addOneError(tError);
				}
			}

			// 报案的理赔类型=出险原因+模板中的出险类型,第1位是界面上选择的出险原因,后两位才是模板中录入的出险日期
			if (mLLReportReasonSet.get(i).getReasonCode().trim().length() <= 1) {
				CError
						.buildErr(this, "客户号为"
								+ mLLSubReportSet.get(i).getCustomerNo()
								+ "的出险类型不能为空!");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "客户号为"
						+ mLLSubReportSet.get(i).getCustomerNo() + "的出险类型不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
			}

//			/**
//			 * 帐户型险种不能通过批量报案进入到系统中
//			 * ***/
//			String checkSql1 = "select distinct riskcode,(select insuaccflag from lmrisk where lmrisk.riskcode=lcgrppol.riskcode) from lcgrppol where grpcontno='"
//					+ mLLReportSchema.getGrpContNo() + "' ";
//			logger.debug("--校验是否是帐户型险种sql" + checkSql1);
//			tSSRS = tExeSQL.execSQL(checkSql1);
//			logger.debug("riskSSRS.GetText(1,2):" + tSSRS.GetText(1, 2));
//
//			if (mLLReportSchema.getRptNo().trim().equals("")
//					&& !mLLReportSchema.getRgtState().equals("02")
//					&& tSSRS.getMaxRow() == 1
//					&& "Y".equals(tSSRS.GetText(1, 2))) {
//				CError.buildErr(this, "该保单只有帐户型险种，请选普通案件报案！");
//				mErrorFlag = true;
//
//				CError tError = new CError();
//				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
//				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
//				tError.errorMessage = "该保单只有帐户型险种，请选择普通案件报案！";
//				this.logErrors.addOneError(tError);
//				// return false;
//			}

//			String checkSql2 = " select count(*) from lcinsured where grpcontno='"
//					+ mLLReportSchema.getGrpContNo()
//					+ "' and insuredno='"
//					+ mLLSubReportSet.get(i).getCustomerNo()
//					+ "' and managecom like '"
//					+ mGlobalInput.ManageCom
//					+ "%'"
//					+ " or ExecuteCom like '"
//					+ mGlobalInput.ManageCom
//					+ "%'   ";
//			logger.debug("--校验校验保单授权机构sql" + checkSql2);
//
//			int tComCount = Integer.parseInt(tExeSQL.getOneValue(checkSql2));
//			if (tComCount == 0) {
//
//				CError.buildErr(this, "模板中第" + i + "行的客户("
//						+ mLLSubReportSet.get(i).getCustomerNo() + ")，没有授权给"
//						+ mGlobalInput.ManageCom + "机构！");
//				mErrorFlag = true;
//
//				CError tError = new CError();
//				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
//				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
//				tError.errorMessage = "模板中第" + i + "行的客户("
//						+ mLLSubReportSet.get(i).getCustomerNo() + ")，没有授权给"
//						+ mGlobalInput.ManageCom + "机构！";
//				this.logErrors.addOneError(tError);
//				// return false;
//			}

			/**
			 * 增加对出险人的报案情况进行判断，如果针对一次案件已经有相应的报案记录 就不允许再对此人做报案,避免重复报案-
			 * ***/
			String checkSql3 = "select subrptno,customerno from llsubreport a ,llreport b where a.subrptno=b.rptno and b.grpcontno='"
					+ mLLReportSchema.getGrpContNo()
					+ "' and a.customerno='"
					+ mLLSubReportSet.get(i).getCustomerNo()
					+ "' and not exists ( select 'X' from llregister where rgtno = a.subrptno ) ";
			logger.debug("--校验避免重复报案sql" + checkSql3);

			// 清除上一次的查询结果
			tSSRS.Clear();
			tSSRS = tExeSQL.execSQL(checkSql3);
			if (tSSRS.getMaxRow() > 0) {
				CError.buildErr(this, "模板中第" + i + "行的客户("
						+ tSSRS.GetText(1, 2) + ")在赔案(" + tSSRS.GetText(1, 1)
						+ ")有报案数据，请不要重复报案！");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的客户("
						+ tSSRS.GetText(1, 2) + ")在赔案(" + tSSRS.GetText(1, 1)
						+ ")有报案数据!";
				this.logErrors.addOneError(tError);
				// return false;

			}

			/**
			 * 增加对出险人是否作过死亡理赔的判断，如作过不允许再作理赔
			 * ***/
			String checkSql4 = " select rpno,Customerno "
					+ " from LLReportReason,llreport where llreport.rptno=llreportReason.Rpno"
					+ " and substr(reasoncode,2)='02'" + " and  Customerno = '"
					+ mLLSubReportSet.get(i).getCustomerNo() + "'"
					+ " and  grpcontno = '" + mLLReportSchema.getGrpContNo()
					+ "'";
			logger.debug("--校验增加对出险人是否作过死亡理赔的判断，如作过不允许再作理赔sql"
					+ checkSql4);

			// 清除上一次的查询结果
			tSSRS.Clear();
			tSSRS = tExeSQL.execSQL(checkSql4);
			if (tSSRS.getMaxRow() > 0) {

				CError.buildErr(this, "模板中第" + i + "行的客户("
						+ tSSRS.GetText(1, 2) + ")在赔案(" + tSSRS.GetText(1, 1)
						+ ")存在死亡报案记录，请不要重复报案！");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的客户("
						+ tSSRS.GetText(1, 2) + ")在赔案(" + tSSRS.GetText(1, 1)
						+ ")存在死亡报案记录!";
				this.logErrors.addOneError(tError);
				// return false;

			}

			/**
			 * 增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
			 * ***/
			String checkSql5 = " select a.caseno,a.Customerno from llcase a,llregister b where a.rgtno=b.rgtno and b.grpcontno='"
					+ mLLReportSchema.getGrpContNo()
					+ "' and a.customerno = '"
					+ mLLSubReportSet.get(i).getCustomerNo()
					+ "' and  b.clmstate not in ('60','50','70') ";
			logger.debug("--增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增sql"
					+ checkSql5);

			// 清除上一次的查询结果
			tSSRS.Clear();
			tSSRS = tExeSQL.execSQL(checkSql5);
			if (tSSRS.getMaxRow() > 0) {
				CError.buildErr(this, "模板中第" + i + "行的客户("
						+ tSSRS.GetText(1, 2) + ")在赔案（" + tSSRS.GetText(1, 1)
						+ ")中有未结案件，请结案后再做新增！");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的客户("
						+ tSSRS.GetText(1, 2) + ")在赔案（" + tSSRS.GetText(1, 1)
						+ ")中有未结案件！";
				this.logErrors.addOneError(tError);
				// return false;

			}

			/**
			 * 帐户型导入的案件的客户必须存在帐户
			 * ***/
			if (mLLReportSchema.getRgtState().equals("02"))// 导入的账户案件
			{
				/**
				 * 如果是账户--查询lcinsureaccclass表
				 * **/
				String checkSql6 = "select count(*),insuredno from lcinsureaccclass where grpcontno='"
						+ mLLReportSchema.getGrpContNo()
						+ "' and insuredno='"
						+ mLLSubReportSet.get(i).getCustomerNo() + "'";
				logger.debug("校验帐户型导入的案件的客户必须存在帐户sql:" + checkSql6);

				// 清除上一次的查询结果
				tSSRS.Clear();
				tSSRS = tExeSQL.execSQL(checkSql6);

				if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的客户("
							+ tSSRS.GetText(1, 2) + ")没有账户信息!不允许做新增");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的客户("
							+ tSSRS.GetText(1, 2) + ")没有账户信息!不允许做新增";
					this.logErrors.addOneError(tError);
					// return false;
				}

				checkSql6 = null;

			}
			
			//受益人姓名不能为空
			if (mLLSubReportSet.get(i).getBnfName() == null
					|| mLLSubReportSet.get(i).getBnfName().trim().equals("")) {

				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的受益人姓名不能为空!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的受益人姓名不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
				//}
			}

			if (mLLSubReportSet.get(i).getBankCode() != null
					&& !mLLSubReportSet.get(i).getBankCode().equals("")) {
				String checkSql7 = "select 1 from ldbank where bankcode='"
						+ mLLSubReportSet.get(i).getBankCode() + "'";
				logger.debug("校验银行编码是否合法的sql:" + checkSql7);

				// 清除上一次的查询结果
				tSSRS.Clear();
				tSSRS = tExeSQL.execSQL(checkSql7);
				if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的客户的银行编码不合法!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的客户的银行编码不合法!";
					this.logErrors.addOneError(tError);
					// return false;
				}
				checkSql7 = null;
			}

			if (mLLSubReportSet.get(i).getRelationToInsured() != null
					&& !mLLSubReportSet.get(i).getRelationToInsured()
							.equals("")) {
				
				String checkSql8 = "select 1 from ldcode where codetype='relation' and code='"
						+ mLLSubReportSet.get(i).getRelationToInsured() + "'";
				logger.debug("校验与被保险人关系是否合法的sql:" + checkSql8);

				// 清除上一次的查询结果
				tSSRS.Clear();
				tSSRS = tExeSQL.execSQL(checkSql8);
				if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的客户的与被保险人关系代码不合法!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的客户的与被保险人关系代码不合法!";
					this.logErrors.addOneError(tError);
					// return false;
				}
				checkSql8 = null;
			}else{
//				if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的受益人与被保人关系不能为空!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的受益人与被保人关系不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
//				}
			}
			if (mLLSubReportSet.get(i).getCaseGetMode() == null
					|| mLLSubReportSet.get(i).getCaseGetMode().equals("")) {

				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的客户的领取方式不能为空!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的客户的领取方式不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
				//}
			}
			if (mLLSubReportSet.get(i).getBnfIDNo() == null
					|| mLLSubReportSet.get(i).getBnfIDNo().trim().equals("")) {
				
				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "模板中第" + i + "行的受益人证件号码不能为空!");
					mErrorFlag = true;
					
					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
					.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的受益人证件号码不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
				//}
			}
			if (mLLSubReportSet.get(i).getBnfIDType() == null
					|| mLLSubReportSet.get(i).getBnfIDType().trim().equals("")) {
				
				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "模板中第" + i + "行的受益人证件类型不能为空!");
				mErrorFlag = true;
				
				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i)
				.getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的受益人证件类型不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
				//}
			}
			
			
			if (mLLSubReportSet.get(i).getBnfSex() == null
					|| mLLSubReportSet.get(i).getBnfSex().trim().equals("")) {
				
				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "模板中第" + i + "行的受益人性别不能为空!");
				mErrorFlag = true;
				
				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i)
				.getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的受益人性别不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
				//}
			}
			
			
			if (mLLSubReportSet.get(i).getBirthday() == null
					|| mLLSubReportSet.get(i).getBirthday().trim().equals("")) {
				
				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "模板中第" + i + "行的受益人出生日期不能为空!");
				mErrorFlag = true;
				
				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i)
				.getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的受益人出生日期不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
				//}
			}
			
			
			if ("4".equals(mLLSubReportSet.get(i).getCaseGetMode())) {
				
				if (mLLSubReportSet.get(i).getBankCode() == null
						|| mLLSubReportSet.get(i).getBankCode().trim().equals("")) {
					CError.buildErr(this, "模板中第" + i + "行的开户银行不能为空!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的开户银行不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
				}
				
				if (mLLSubReportSet.get(i).getBankAccNo() == null
						|| mLLSubReportSet.get(i).getBankAccNo().trim().equals("")) {
					CError.buildErr(this, "模板中第" + i + "行的银行账号不能为空!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的银行账号不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
				}
				
				if (mLLSubReportSet.get(i).getBnfAccName() == null
						|| mLLSubReportSet.get(i).getBnfAccName().trim().equals("")) {
					CError.buildErr(this, "模板中第" + i + "行的银行账户名不能为空!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的银行账户名不能为空!";
					this.logErrors.addOneError(tError);
					// return false;
				}
				
				if (mLLSubReportSet.get(i).getBnfIDType() == null
						|| !mLLSubReportSet.get(i).getBnfIDType().trim().equals("0")) {
					CError.buildErr(this, "模板中第" + i + "行的领取方式为银行转账,而受益人证件类型需为身份证!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的领取方式为银行转账,而受益人证件类型需为身份证!";
					this.logErrors.addOneError(tError);
					// return false;
				}
			}

			if (!"4".equals(mLLSubReportSet.get(i).getCaseGetMode())
					&& !"1".equals(mLLSubReportSet.get(i).getCaseGetMode())) {
				CError.buildErr(this, "模板中第" + i + "行的领取方式不为1或4!");
				mErrorFlag = true;

				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i).getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的领取方式不为1或4!";
				this.logErrors.addOneError(tError);
			}

			if ("1".equals(mLLSubReportSet.get(i).getCaseGetMode())) {
				if (mLLSubReportSet.get(i).getBankAccNo() != null
						&& !"".equals(mLLSubReportSet.get(i).getBankAccNo())) {
					CError.buildErr(this, "模板中第" + i + "行的领取方式为1，银行账号不能有值!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的领取方式为1，银行账号不能有值!";
					this.logErrors.addOneError(tError);
				}
				if (mLLSubReportSet.get(i).getBankCode() != null
						&& !"".equals(mLLSubReportSet.get(i).getBankCode())) {
					CError.buildErr(this, "模板中第" + i + "行的领取方式为1，银行编码不能有值!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的领取方式为1，银行编码不能有值!";
					this.logErrors.addOneError(tError);
				}
			}
			
			if(mLLSubReportSet.get(i).getRelationToInsured().equals("00"))
			{
				if(!mLLSubReportSet.get(i).getCustomerName().trim().equals(mLLSubReportSet.get(i).getBnfName()))
				{
					CError.buildErr(this, "模板中第" + i + "行的受益人与被保险人关系为本人,但姓名不相符!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的受益人与被保险人关系为本人,但姓名不相符!";
					this.logErrors.addOneError(tError);
				}
				
				if(!mLLSubReportSet.get(i).getIDType().trim().equals(mLLSubReportSet.get(i).getBnfIDType()))
				{
					CError.buildErr(this, "模板中第" + i + "行的受益人与被保险人关系为本人,但证件类型不相符!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的受益人与被保险人关系为本人,但证件类型不相符!";
					this.logErrors.addOneError(tError);
				}
				
				
				if(!mLLSubReportSet.get(i).getBnfIDNo().trim().equals(mLLSubReportSet.get(i).getBnfIDNo()))
				{
					CError.buildErr(this, "模板中第" + i + "行的受益人与被保险人关系为本人,但证件号码不相符!");
					mErrorFlag = true;

					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的受益人与被保险人关系为本人,但证件号码不相符!";
					this.logErrors.addOneError(tError);
				}
			}
			if((mLLReportSchema.getAccidentDate()!=null&&!"".equals(mLLReportSchema.getAccidentDate()))&&(mLLSubReportSet.get(i).getAccDate()!=null&&!"".equals(mLLSubReportSet.get(i).getAccDate()))){
				String tDateSql = "select 1 from dual where to_date('"+mLLReportSchema.getAccidentDate()+"')>to_date('"+mLLSubReportSet.get(i).getAccDate()+"')";
				String tDate = tExeSQL.getOneValue(tDateSql);
				
				if("1".equals(tDate)){
					CError.buildErr(this, "模板中第" + i + "行的事故日期晚于出险日期!");
					mErrorFlag = true;
	
					CError tError = new CError();
					tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
					tError.functionName = mLLSubReportSet.get(i)
							.getCustomerName();
					tError.errorMessage = "模板中第" + i + "行的事故日期晚于出险日期";
					this.logErrors.addOneError(tError);
				}
			}
			
			if (mLLSubReportSet.get(i).getAccResult1()== null
					|| mLLSubReportSet.get(i).getAccResult1().trim().equals("")) {
				
				// 清除上一次的查询结果
				//if (tSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "模板中第" + i + "行的出险结果不能为空!");
				mErrorFlag = true;
				
				CError tError = new CError();
				tError.moduleName = mLLSubReportSet.get(i).getCustomerNo();
				tError.functionName = mLLSubReportSet.get(i)
				.getCustomerName();
				tError.errorMessage = "模板中第" + i + "行的出险结果不能为空!";
				this.logErrors.addOneError(tError);
				// return false;
				//}
			}
			
			//checkSql1 = null;
//			checkSql2 = null;
			checkSql3 = null;
			checkSql4 = null;
			checkSql5 = null;
		}

		// 释放强引用
		tExeSQL = null;
		tSSRS = null;
		logger.debug("***************End  checkBatchData***************");
		if (mErrorFlag == true) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据Excel的内容生成出险人数据
	 * 
	 */
	private boolean parseData() {

		LLSubReportSchema tLLSubReportSchema = null;// 出险人信息
		LLReportReasonSchema tLLReportReasonSchema = null;// 报案理赔类型

		mLLSubReportSet = new LLSubReportSet();// 出险人信息集合
		mLLReportReasonSet = new LLReportReasonSet();// 报案理赔类型集合

		BookModelImpl bmTemplate = new com.f1j.ss.BookModelImpl();
		try {

			bmTemplate.read(ImportAbsoluteExcelPath,
					new com.f1j.ss.ReadParams());
			int mSumCount = bmTemplate.getLastRow();
			logger.debug("Excel文件一共有" + mSumCount + "行");

			if (mSumCount <= 0) {
				CError.buildErr(this, "上传文件记录为空，请填写后重新上传!");
				return false;
			} 
			else {

				logger.debug("界面传入的报案号:" + mLLReportSchema.getRptNo());

				logger.debug("new insert Customer Information!");
				
				/**
				 * 2009-04-25 zhangzheng
				 * 出险人部分信息:姓名,性别,证件类型,证件号码均根据客户号从ldperson中查询,如果客户填写正确,则从ldperson中提取这些信息点,否则从模板中提取
				 */
				LDPersonDB tLDPersonDB=new LDPersonDB();

				for (int i = 1; i <= mSumCount; i++) {

					logger.debug("循环第" + i + "条出险人记录的案件ID："
							+ bmTemplate.getText(i, 0));
					logger.debug("循环第" + i + "条出险人记录的出险人客户号: "
							+ bmTemplate.getText(i, 1));
					logger.debug("循环第" + i + "条出险人记录的出险人姓名: "
							+ bmTemplate.getText(i, 2));
					logger.debug("循环第" + i + "条出险人记录的出险人性别: "
							+ bmTemplate.getText(i, 3));
					logger.debug("循环第" + i + "条出险人记录的出险人证件类型: "
							+ bmTemplate.getText(i, 4));
					logger.debug("循环第" + i + "条出险人记录的出险人证件号码: "
							+ bmTemplate.getText(i, 5));
					logger.debug("循环第" + i + "条出险人记录的出险类型: "
							+ bmTemplate.getText(i, 6));
					logger.debug("循环第" + i + "条出险人记录的出险原因: "
							+ bmTemplate.getText(i, 7));
					logger.debug("循环第" + i + "条出险人记录的出险日期: "
							+ bmTemplate.getText(i, 8));
					logger.debug("循环第" + i + "条出险人记录的赔案号: "
							+ bmTemplate.getText(i, 9));
					logger.debug("循环第" + i + "条出险人记录的治疗情况: "
							+ bmTemplate.getText(i, 10));
					logger.debug("循环第" + i + "条出险人记录的收款人姓名: "
							+ bmTemplate.getText(i, 11));
					logger.debug("循环第" + i + "条出险人记录的开户银行: "
							+ bmTemplate.getText(i, 12));
					logger.debug("循环第" + i + "条出险人记录的银行账号: "
							+ bmTemplate.getText(i, 13));
					logger.debug("循环第" + i + "条出险人记录的受益人证件类型: "
							+ bmTemplate.getText(i, 14));
					logger.debug("循环第" + i + "条出险人记录的受益人证件号码: "
							+ bmTemplate.getText(i, 15));
					logger.debug("循环第" + i + "条出险人记录的与被保险人关系: "
							+ bmTemplate.getText(i, 16));
					logger.debug("循环第" + i + "领取方式: "
							+ bmTemplate.getText(i, 17));
					logger.debug("循环第" + i + "受益人性别: "
							+ bmTemplate.getText(i, 18));
					logger.debug("循环第" + i + "受益人出生日期: "
							+ bmTemplate.getText(i, 19));
					logger.debug("循环第" + i + "受益人银行账户名: "
							+ bmTemplate.getText(i, 20));
					logger.debug("循环第" + i + "出险结果: "
							+ bmTemplate.getText(i, 21));

					// 出险人信息
					tLLSubReportSchema = new LLSubReportSchema();
					tLLSubReportSchema.setSubRptNo(mLLReportSchema.getRptNo());// 报案号

					if (bmTemplate.getText(i, 0) == null
							|| bmTemplate.getText(i, 0).equals("")) {

						tLLSubReportSchema.setSeqNo(0);// 默认客户序号为0
					} else {

						tLLSubReportSchema.setSeqNo(bmTemplate.getText(i, 0));// 客户序号
					}
					

					tLLSubReportSchema.setCustomerNo(bmTemplate.getText(i, 1));// 出险人客户号
					
					tLDPersonDB.setCustomerNo(tLLSubReportSchema.getCustomerNo());
					
					if(tLDPersonDB.getInfo())
					{
						tLLSubReportSchema.setCustomerName(tLDPersonDB.getName());
						tLLSubReportSchema.setSex(tLDPersonDB.getSex());// 出险人性别
						
						tLLSubReportSchema.setIDType(tLDPersonDB.getIDType());// 出险人证件类型
						tLLSubReportSchema.setIDNo(tLDPersonDB.getIDNo());// 出险人证件号
					}
					else
					{
						tLLSubReportSchema.setCustomerName(bmTemplate.getText(i, 2));// 出险人姓名
						tLLSubReportSchema.setSex(bmTemplate.getText(i, 3));// 出险人性别
						
						tLLSubReportSchema.setIDType(bmTemplate.getText(i, 4));// 出险人证件类型
						tLLSubReportSchema.setIDNo(bmTemplate.getText(i, 5));// 出险人证件号
					}				

					tLLSubReportSchema.setAccidentType(mLLReportSchema.getAccidentReason());// 出险原因
					
					tLLSubReportSchema.setAccDate(bmTemplate.getText(i, 8));// 出险日期
					tLLSubReportSchema.setMedAccDate(bmTemplate.getText(i, 8));// 医疗出险日期
					tLLSubReportSchema.setCureDesc(bmTemplate.getText(i, 10));// 治疗情况
					tLLSubReportSchema.setBnfName(bmTemplate.getText(i, 11));// 收款人姓名
					tLLSubReportSchema.setBankCode(bmTemplate.getText(i, 12));// 开户银行
					tLLSubReportSchema.setBankAccNo(bmTemplate.getText(i, 13));// 银行账号
					tLLSubReportSchema.setBnfIDType(bmTemplate.getText(i, 14));//受益人证件类型
					tLLSubReportSchema.setBnfIDNo(bmTemplate.getText(i, 15));// 受益人证件号码
					tLLSubReportSchema.setRelationToInsured(bmTemplate.getText(i, 16));// 与被保险人关系
					tLLSubReportSchema.setCaseGetMode(bmTemplate.getText(i, 17));// 领取方式
					tLLSubReportSchema.setBnfSex(bmTemplate.getText(i, 18));//受益人性别
					tLLSubReportSchema.setBirthday(bmTemplate.getText(i, 19));//受益人出生日期
					tLLSubReportSchema.setBnfAccName(bmTemplate.getText(i, 20));//受益人银行账户名
					tLLSubReportSchema.setAccResult1(bmTemplate.getText(i, 21));//出险结果
					tLLSubReportSchema.setAccResult2(bmTemplate.getText(i, 21));//出险结果
					mLLSubReportSet.add(tLLSubReportSchema);

					// 报案理赔类型
					String[] arr=bmTemplate.getText(i, 6).split(","); 
					
					for(int k=0;k<arr.length;k++)
					{
						tLLReportReasonSchema = new LLReportReasonSchema();
						tLLReportReasonSchema.setRpNo(mLLReportSchema.getRptNo());// 报案号
						tLLReportReasonSchema.setCustomerNo(tLLSubReportSchema.getCustomerNo());// 出险人客户号
						tLLReportReasonSchema.setReasonCode(mLLReportSchema.getAccidentReason()+ arr[k]);// 理赔类型=出险原因+模板中的出险类型
						mLLReportReasonSet.add(tLLReportReasonSchema);		
						tLLReportReasonSchema=null;
					}
		
					if ("01".equals(bmTemplate.getText(i, 6))||"03".equals(bmTemplate.getText(i, 6))) {
						// 生成打印数据--伤残鉴定通知书
						if (!insertLOPRTManager("PCT001",bmTemplate.getText(i, 1))) {
							return false;
						}
					}
				// 生成打印数据--单证通知书
				if (!insertLOPRTManager("PCT002",bmTemplate.getText(i, 1))) {
					return false;
				}
					// 释放强引用
					tLLSubReportSchema = null;
					tLLReportReasonSchema = null;
				}

			}
			
			
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "解析上传文件错误!");
			return false;

		} catch (F1Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "解析上传文件错误!");
			return false;

		}

		return true;
	}

	/**
	 * 初始化上传文件
	 * 
	 * @return
	 */
	private boolean initImportFile() {
		this.getFilePath();

		ImportAbsoluteExcelPath = SaveExcelPath + FilePath + FileName;// Excel文件的绝对路径

		logger.debug("ImportAbsoluteExcelPath-----"
				+ ImportAbsoluteExcelPath);

		logger.debug("开始导入文件-----");

		File tFile = new File(ImportAbsoluteExcelPath);
		if (!tFile.exists()) {
			// @@错误处理
			CError.buildErr(this, "未上传文件到指定路径" + ImportAbsoluteExcelPath);
			return false;
		}

		logger.debug("-----导入文件结束!");
		return true;
	}

	/**
	 * 得到生成文件路径
	 * 
	 * @return
	 */
	private boolean getFilePath() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("XmlPath");
		if (!tLDSysVarDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "缺少文件导入路径!");
			return false;
		} else
			FilePath = tLDSysVarDB.getSysVarValue();

		logger.debug("数据库中存储的存放文件路径:" + FilePath);

		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "全局信息传输失败!");
			return false;
		}

		if (mLLReportSchema == null && mLLReportSchema == null) {

			// @@错误处理
			CError.buildErr(this, "报案信息传输失败!");
			return false;
		}

		if (mLLReportSchema.getGrpContNo() == null
				&& mLLReportSchema.getGrpContNo() == null) {

			// @@错误处理
			CError.buildErr(this, "团体保单号不能为空!");
			return false;
		}

		if (mLLReportSchema.getAccidentReason() == null
				&& mLLReportSchema.getAccidentReason() == null) {

			// @@错误处理
			CError.buildErr(this, "出险原因不能为空!");
			return false;
		}

		if (mLLReportSchema.getAppntNo() == null
				&& mLLReportSchema.getAppntNo() == null) {

			// @@错误处理
			CError.buildErr(this, "团体客户号不能为空!");
			return false;
		}

		// 防止录入的出险原因代码长度大于1位
		if (mLLReportSchema.getAccidentReason().trim().length() != 1) {

			// @@错误处理
			CError.buildErr(this, "出险原因录入错误，请重新原则!");
			return false;
		}

		// 当传入的报案号不为空时，须和数据库中进行校验
		if (!(mLLReportSchema.getRptNo() == null || mLLReportSchema.getRptNo()
				.equals(""))) {

			LLCaseRelaDB mLLCaseRelaDB = new LLCaseRelaDB();
			mLLCaseRelaDB.setCaseNo(mLLReportSchema.getRptNo());
			mLLCaseRelaSet = mLLCaseRelaDB.query();
			if (mLLCaseRelaSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "报案号传输错误!");
				return false;
			}
		}

		return true;

	}

	/**
	 * 得到日志显示结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		mResult.add(mLLReportSchema.getRptNo());
		mResult.add(AccNo);
		return mResult;

	}

	/**
	 * 将批量导入时的错误信息插入到数据库中
	 * 
	 */
	public boolean LogError(String tRgtNo, String tBatchNo, int ID,
			String tCustomerNo, String customerName,
			// String operator,CErrors tErrors,String tErrorType ) {
			String operator, CError tError, String tErrorType) {
		// MMap tMMap = new MMap();
		// String tDelSql =
		// "delete from LCGrpCustomerImportLog where batchno='"+FileName+"' ";

		LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema = new LCGrpCustomerImportLogSchema();
		LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema = new LCGrpCustomerImportLogSchema();

		// 删除日志
		delLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
		delLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
		delLCGrpCustomerImportLogSchema.setID(String.valueOf(ID));

		// 插入日志
		InLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
		InLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
		InLCGrpCustomerImportLogSchema.setID(String.valueOf(ID));
		InLCGrpCustomerImportLogSchema.setErrorType(tErrorType);
		InLCGrpCustomerImportLogSchema.setCustomerNo(tCustomerNo);
		InLCGrpCustomerImportLogSchema.setCustomerName(customerName);
		InLCGrpCustomerImportLogSchema.setOperator(operator);

		// if(tErrorState)
		// InLCGrpCustomerImportLogSchema.setErrorState("1");
		// else
		// InLCGrpCustomerImportLogSchema.setErrorState("0");

		InLCGrpCustomerImportLogSchema.setMakeDate(PubFun.getCurrentDate());
		InLCGrpCustomerImportLogSchema.setMakeTime(PubFun.getCurrentTime());

		String errorMess = "";

		// for (int i = 0;i<tErrors.getErrorCount();i++)
		// {
		// errorMess +=tErrors.getError(i).errorMessage;
		// }
		errorMess = tError.errorMessage;
		if (errorMess.equals(""))
			errorMess = "未捕捉到错误";

		InLCGrpCustomerImportLogSchema.setErrorInfo(errorMess);

		tLCGrpCustomerImportLogSet.add(InLCGrpCustomerImportLogSchema);
		// tMMap.put(delLCGrpCustomerImportLogSchema, "DELETE");

		// 释放强引用
		delLCGrpCustomerImportLogSchema = null;
		InLCGrpCustomerImportLogSchema = null;

		return true;

	}

	// //取得日志信息
	public LCGrpCustomerImportLogSchema getLogInfo(String mRgtNo,
			String batchNo, String ID) {
		LCGrpCustomerImportLogDB tLCGrpCustomerImportLogDB = new LCGrpCustomerImportLogDB();
		tLCGrpCustomerImportLogDB.setRgtNo(mRgtNo);
		tLCGrpCustomerImportLogDB.setBatchNo(batchNo);
		tLCGrpCustomerImportLogDB.setID(ID);
		if (tLCGrpCustomerImportLogDB.getInfo()) {
			return tLCGrpCustomerImportLogDB.getSchema();
		}
		return null;
	}

	/**
	 * 保存错误信息
	 * */
	private boolean DeatErrors() {
		String tRpgNo = "";
		if (mTempRptNo == null || mTempRptNo.equals("")) {
			tRpgNo = "00000000000000000000";
		} else {
			tRpgNo = mTempRptNo;
		}
		tMMap.put("delete from LCGrpCustomerImportLog where batchno='"
				+ FileName + "' ", "DELETE");
		for (int i = 0; i < logErrors.getErrorCount(); i++) {
			CError tCError = new CError();
			tCError = logErrors.getError(i);
			if (!this.LogError(tRpgNo, FileName, i + 1, tCError.moduleName,
					tCError.functionName, mGlobalInput.Operator, tCError, "0")) {
				CError.buildErr(this, "保存导入错误信息失败！");
				return false;
			}
		}
		tMMap.put(tLCGrpCustomerImportLogSet, "INSERT");
		mInputData.clear();
		mInputData.add(tMMap);
		if (logErrors.getErrorCount() > 0) {
			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "数据提交失败！");
				return false;

			}
		}
		return true;
	}
	
	
	/**
	 * 添加打印数据 2009-4-24 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode,String tCustomerno) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 10); // 生成印刷流水号
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mLLReportSchema.getRptNo()); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期(报案日期)
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); // 批打检索日期
		tLOPRTManagerSchema.setStandbyFlag4(tCustomerno); // 客户号码
		// tLOPRTManagerSchema.setStandbyFlag5(CurrentDate); //立案日期
		tLOPRTManagerSchema.setStandbyFlag6("10"); // 赔案状态
		mLOPRTManagerSet.add(tLOPRTManagerSchema);
//		mDelSql = "delect from loprtmanager where code='"+tCode+"' and standbyflag4='"+tCustomerno+"' "
//						+"and otherno='"+mLLReportSchema.getRptNo()+"'";
//		tMap.put(tDelSql, "DELETE");
		return true;
	}

	public static void main(String[] args) {
		GrpCustomerBatchReportIn tPGI = new GrpCustomerBatchReportIn();
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "20050426.xls");
		tTransferData.setNameAndValue("FilePath", "E:");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "claim";
		tG.ManageCom = "86";

		long a = System.currentTimeMillis();
		logger.debug(a);
		tVData.add(tTransferData);
		tVData.add(tG);
		tPGI.submitData(tVData, "");
		logger.debug(System.currentTimeMillis() - a);
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
