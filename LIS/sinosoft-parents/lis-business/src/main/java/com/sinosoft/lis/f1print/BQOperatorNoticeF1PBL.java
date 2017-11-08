package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPIndUWMasterDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPIndUWMasterSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPIndUWMasterSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.utility.XmlExportNew;

public class BQOperatorNoticeF1PBL implements PrintService{
private static Logger logger = Logger.getLogger(BQOperatorNoticeF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	private String ReasonInfo1 = "您无需补交保险费！";
	private String lys_Flag = "0";
	private String lys_Flag_main = "0";
	private String lys_Flag_ab = "0";
	// private String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private String mOperate = "";
	private String mRealPayMoney = "";
	private String strPolNo = ""; // 合同号
	private MMap map = new MMap();
	private VData mInputData = new VData();
	private String mPrtSeq="";

	public BQOperatorNoticeF1PBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {

			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
					&& !cOperate.equals("PRINT2")&&!cOperate.equals("PRINTdis")
					&& !cOperate.equals("PRINTwai")&&!cOperate.equals("PRINTepi")
					&& !cOperate.equals("PRINTbre")&&!cOperate.equals("PRINTche")
					&& !cOperate.equals("PRINTtum")&&!cOperate.equals("PRINTbab")
					&& !cOperate.equals("PRINTabr")&&!cOperate.equals("PRINTpar")
					&& !cOperate.equals("PRINTpar")&&!cOperate.equals("PRINTaf1")
					&& !cOperate.equals("PRINTaf2")&&!cOperate.equals("PRINTali")
					&& !cOperate.equals("PRINTcsi")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("PRINT")) {

				mResult.clear();
				// tongmeng 2007-10-18 modify
				// 增加业务员问题件的打印和发放.
				// 修改打印核保通知书逻辑
				// 1-查询打印管理表
				LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

				tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
				if (tLOPRTManagerDB.getInfo() == false) {
					mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
					throw new Exception("在取得打印队列中数据时发生错误");
				}
				mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
				// message！
				// 查询问题件表
				/*
				 * String tsql = "select BackObjType from LCIssuePol where
				 * NeedPrint = 'Y' " + " and PrtSeq = '" +
				 * mLOPRTManagerSchema.getOldPrtSeq() + "'"; ExeSQL tExeSQL =
				 * new ExeSQL(); String tBackObjType = ""; tBackObjType =
				 * tExeSQL.getOneValue(tsql);
				 * logger.debug("@@tBackObjType:" + tBackObjType);
				 */
				if (mLOPRTManagerSchema.getCode().equals("25")
						|| mLOPRTManagerSchema.getCode().equals("TB90")) {
					// 准备所有要打印的数据
					// 发放给客户的问题件
					logger.debug("打印核保通知书");
					// getPrintData();
					getPrintData_HB();
				}
				
				else if (mLOPRTManagerSchema.getCode().equals("27")) {
					// 准备所有要打印的数据
					// 发放给客户的问题件
					logger.debug("打印核保通知书");
					// getPrintData();
					getPrintData_HBN();
				}
				
				else if (mLOPRTManagerSchema.getCode().equals("BQ84")) {
					// 准备所有要打印的数据
					// 拒保通知书
					logger.debug("打印拒保通知书");
					// getPrintData();
					getPrintData_HBR();
				}

				else if (mLOPRTManagerSchema.getCode().equals("14")) {
					// BackObjType ='3'
					// 发放给业务员的问题件
					//
					logger.debug("打印发放给业务员的问题件");
					getPrintData_agent();
				} else {
					buildError("submitData", "打印数据有问题.");
					return false;
				}

			}
			if (cOperate.equals("PRINT2")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData1();
				// UpdateData(); //更新数据库write by yaory

			}
			if (cOperate.equals("PRINTdis"))
			{
				mResult.clear();
				//// 准备所有要打印的数据
				getPrintData_dis(); //add by liuqh2008-11-21 
			}
			if (cOperate.equals("PRINTwai")){
				mResult.clear();
                // 准备所有要打印的数据
				getPrintData_wai();
			}
			if (cOperate.equals("PRINTepi")){
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_epi();
			}
			if (cOperate.equals("PRINTabr")){
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_abr();
			}
			if (cOperate.equals("PRINTaf1")){
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_af1();
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData_agent() throws Exception {
		LPContDB tLCGrpContDB = new LPContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();// get all message！
		tLCGrpContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		LPContSet tempLPContSet = tLCGrpContDB.query();

		if (tempLPContSet.size() != 1) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			throw new Exception("在查询保单信息时出错！");
		}

		tLCGrpContDB.setSchema(tempLPContSet.get(1));

		String strContNo = tLCGrpContDB.getContNo();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		// tLCIssuePolDB.setPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
		// tLCIssuePolDB.setBackObjType("3");
		// tLCIssuePolDB.setNeedPrint("Y");
		// tLCIssuePolDB.setContNo(strContNo);
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		String tSQL = "";
		String InputOperator = tLCGrpContDB.getInputOperator();//录入员		
		String UWOperator = tLCGrpContDB.getUWOperator();//核保员
		String ApproveOperator = tLCGrpContDB.getApproveCode();//复核员
		
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		tSQL = "select * from es_doc_main where doccode='"+"?doccode?"+"'";
		sqlbv1.sql(tSQL);
		sqlbv1.put("doccode", tLCGrpContDB.getPrtNo());
		tES_DOC_MAINSet.set(tES_DOC_MAINDB.executeQuery(sqlbv1));
		if (tES_DOC_MAINDB.mErrors.needDealError() == true) {
			mErrors.copyAllErrors(tES_DOC_MAINDB.mErrors);
			throw new Exception("在取得扫描员数据时发生错误");
		}
		String ScanOperator = "无";
		if(tES_DOC_MAINSet.size()>0)
		    ScanOperator = tES_DOC_MAINSet.get(1).getScanOperator();//扫描员
		tSQL = "select * from LCIssuePol where BackObjType = '3' and NeedPrint = 'Y' and state in ('0','1') "
				+ " and contno='" + "?strContNo?" + "' ";
		sqlbv1.sql(tSQL);
		sqlbv1.put("strContNo", strContNo);
		tLCIssuePolSet.set(tLCIssuePolDB.executeQuery(sqlbv1));
		if (tLCIssuePolDB.mErrors.needDealError() == true) {
			mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
			throw new Exception("在取得问题件中数据时发生错误");
		}
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("QUESTION");
		String content = "";
		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			//strArr = new String[1];
			//strArr[0] = i + "." + tLCIssuePolSet.get(i).getIssueCont();			
			//tlistTable.add(strArr);
			content= i + "." + tLCIssuePolSet.get(i).getIssueCont();	
			getContent(tlistTable, content, 40);
		}
		strArr = new String[1];
		strArr[0] = "QUESTION";

		TextTag texttag = new TextTag();

		texttag.add("AppntName", tLCGrpContDB.getAppntName());
		texttag.add("PrtNo", tLCGrpContDB.getPrtNo());
		texttag.add("AgentName", getAgentName(tLCGrpContDB.getAgentCode()));
		texttag.add("AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCGrpContDB.getManageCom()));
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("CurrentDay", df.format(new Date()));
		texttag.add("InputOperator", InputOperator);
		texttag.add("UWOperator", UWOperator);
		texttag.add("ScanOperator", ScanOperator);
		texttag.add("ApproveOperator", ApproveOperator);

		String tPrintName = "业务员通知书";
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCGrpContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

		if (texttag.size() > 0)
			xmlExport.addTextTag(texttag);
		xmlExport.addListTable(tlistTable, strArr);
		mResult.clear();
		mResult.addElement(xmlExport);
	}

	private void getPrintData1() throws Exception {

		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		xmlExport.createDocument("GrpRateFault.vts", "printer"); // 最好紧接着就初始化xml文档

		String mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		logger.debug("=============" + mLOPRTManagerSchema.getPrtSeq());
		String temprtseq = mLOPRTManagerSchema.getPrtSeq();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误!");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
			// message！

			// 打印时传入的是主险保单的保单号
			tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo()); // 合同号
			String no = mLOPRTManagerSchema.getOtherNo();
			if (!tLCGrpContDB.getInfo()) {
				mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				throw new Exception("在获取保单信息时出错!!");
			}
			LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
			mLCGrpContSchema = tLCGrpContDB.getSchema();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
			logger.debug("号码======" + no);
			tLCGrpAppntDB.setGrpContNo(no);
			if (tLCGrpAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误;;;");
			}

			String mAgentCode = mLCGrpContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误!!!");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
				// throw new Exception("在取得打印队列中数据时发生错误!!!!");
			}

			strPolNo = tLCGrpContDB.getGrpContNo();
			// logger.debug("测试 by
			// yaory"+mLOPRTManagerSchema.getPrtSeq());
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String q_sql = "";
			q_sql = "select IssueCont from LCGrpIssuePol where grpcontno='"
					+ "?strPolNo?" + "' and PrtSeq='"
					+ "?PrtSeq?"
					+ "' and NeedPrint = 'Y' and replyresult is null"; // Gaoht
			sqlbv2.sql(q_sql);
			sqlbv2.put("strPolNo", strPolNo);
			sqlbv2.put("PrtSeq", mLOPRTManagerSchema.getOldPrtSeq());
			// modify
			// 新契约打印不走工作流，只打印未回复问题
			ExeSQL q_exesql = new ExeSQL();
			logger.debug("SQL------>" + q_sql);
			SSRS q_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sqlbv2);
			String strArr[] = null;
			ListTable tlistTable = new ListTable();
			if (q_ssrs != null && q_ssrs.getMaxRow() > 0
					&& q_ssrs.getMaxCol() > 0) {
				if ((q_ssrs.GetText(1, 1).equals("0")
						|| q_ssrs.GetText(1, 1).trim().equals("") || q_ssrs
						.GetText(1, 1).equals("null"))) {
					logger.debug("没有问题件记录");
				} else {
					logger.debug("问题中有" + q_ssrs.getMaxRow() + "条记录！！！");
				}

				tlistTable.setName("QUESTION");
				for (int i = 1; i <= q_ssrs.getMaxRow(); i++) {
					strArr = new String[1];
					strArr[0] = i + "." + q_ssrs.GetText(i, 1);
					tlistTable.add(strArr);
				}
			}
			else {
				buildError("getprintData", "没有问题件记录！");
				return;
			}

			TextTag texttag = new TextTag();
			/**
			 * ==========================================================================
			 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
			 * LABranchGroup.Name：取8位编码的营业销售部 Sumprem : 取用标准的格式：0.00
			 * ==========================================================================
			 */
			// 中支机构名称
			String tManageComCode = tLCGrpContDB.getManageCom();
			if (tLCGrpContDB.getManageCom().length() > 6) {
				tManageComCode = tLCGrpContDB.getManageCom().substring(0, 6);
			}

			String tManageComName = getComName(tManageComCode);

			texttag.add("BarCode1", "UN061");
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam2",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("AppntName", GetAppntname(tLCGrpContDB.getGrpContNo()));
			texttag.add("LPCont.AppntName", GetAppntname(tLCGrpContDB
					.getGrpContNo()));
			texttag.add("LPCont.ProposalContNo", tLCGrpContDB.getGrpContNo());
			texttag.add("LAAgent.Name", getAgentName(tLCGrpContDB
					.getAgentCode()));
			texttag.add("LAAgent.AgentCode", tLCGrpContDB.getAgentCode());
			texttag.add("ManageComName", tManageComName);
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Operator", mLOPRTManagerSchema.getReqOperator());
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			texttag.add("SysDate", df.format(new Date()));

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			xmlExport.addListTable(tlistTable, strArr);
			// xmlExport.outputDocumentToFile("e:\\", "t2HZM");
			mResult.clear();
			mResult.addElement(xmlExport);
		}
	}

	// tongmeng 2007-11-12 modify
	// 修改,使用5.3的核保通知书打印逻辑
	private boolean getPrintData_HB() {
		LCPolSet gLCPolSet = new LCPolSet();
		LPPolSet gLPPolSet = new LPPolSet();
		// 根据印刷号查询打印队列中的纪录
		ExeSQL tExeSQL = new ExeSQL();
		String PrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			CError.buildErr(this, "在取得打印队列中数据时发生错误");

			return false;
		}
		
		//查询工作流数据等
		LWMissionDB tLWMissionDB = new LWMissionDB();
		if(mLOPRTManagerSchema.getCode().equals("25"))
			tLWMissionDB.setActivityID("0000000320");
//		tLWMissionDB.setProcessID("0000000000");
		tLWMissionDB.setMissionProp2(mLOPRTManagerSchema.getOtherNo());
		tLWMissionDB.setMissionProp3(PrtSeq);
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if(tLWMissionSet!=null && tLWMissionSet.size()<1) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError.buildErr(this, "在取得工作流表数据时发生错误");

			return false;
		}
		LWMissionSchema tLWMissionSchema = tLWMissionSet.get(1);	
		String tContNo = mLOPRTManagerSchema.getOtherNo();
		String tEdorNo = tLWMissionSchema.getMissionProp8();
		String tEdorType = tLWMissionSchema.getMissionProp9();
		
        //判断是否是公司层面保全任务
		String CompanyFlag="0"; //0,非公司层面；1，公司层面；默认为0
		String CHECK_sql="";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("select edoracceptno from lpedoritem where edorno='"+"?tEdorNo?"+"'");
		sqlbv3.put("tEdorNo", tEdorNo);
		String mEdorAcceptNo=tExeSQL.getOneValue(sqlbv3);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		CHECK_sql="select createoperator from lbmission a where missionprop1='"+"?mEdorAcceptNo?"
		+"' and activityid in ('0000000001','0000000002') and exists(select 1 from lduwuser where usercode=a.createoperator) ";
		sqlbv4.sql(CHECK_sql);
		sqlbv4.put("mEdorAcceptNo", mEdorAcceptNo);
		String CreateOperator =tExeSQL.getOneValue(sqlbv4);
		if(CreateOperator!=null && !"".equals(CreateOperator))
		{
			CompanyFlag="1";
		}
		
		LPContDB tLPContDB = new LPContDB();
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String strSQL = "SELECT * FROM LPCont WHERE EdorNo='"+"?tEdorNo?"+"' and ContNo = '"
				+ "?tContNo?" + "'";
		sqlbv5.sql(strSQL);
		sqlbv5.put("tEdorNo", tEdorNo);
		sqlbv5.put("tContNo", tContNo);
		LPContSet tempLPContSet = tLPContDB.executeQuery(sqlbv5);

		if (tempLPContSet==null || tempLPContSet.size() == 0) {
			mErrors.copyAllErrors(tLPContDB.mErrors);
			CError.buildErr(this, "在LPCont表中找不到相关信息");
			return false;
		}

		int m;
		int i;
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema = tempLPContSet.get(1);

		String tAgentCode = tLPContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tAgentCode);

		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError.buildErr(this, "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		
//		*************************被保人信息**************************
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema = tLPInsuredDB.getSchema(); // 保存被保人信息
		tLPInsuredDB.setEdorNo(tEdorNo);
		tLPInsuredDB.setEdorType(tEdorType);
		tLPInsuredDB.setContNo(tLPContSchema.getContNo());
		tLPInsuredDB.setInsuredNo(tLPContSchema.getInsuredNo());	
		if (!tLPInsuredDB.getInfo()) {
			mErrors.copyAllErrors(tLPInsuredDB.mErrors);
			CError.buildErr(this, "在取得LPInsured的数据时发生错误");	
			//return false;
		}	
		tLPInsuredSchema = tLPInsuredDB.getSchema(); // 保存被保人信息
		
		boolean SpecFlag = false;		
		boolean ChangePolFlag = false;
		boolean Reason = false;//承保计划变更和加费原因
		String content = "";
		
		//是否有特约
		String[] SpecTitle = new String[1];
		SpecTitle[0] = "SpecInfo";      
        
		ListTable tSpecListTable = new ListTable();
		tSpecListTable.setName("SPECINFO"); // 对应模版特别约定部分的行对象名
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String tForSpec = "select speccontent from lpcspec where edorno='"
						+"?tEdorNo?"+"' and edortype='"+"?tEdorType?"+"'"
						+" and contno='"+"?tContNo?"+"' and needprint='Y'"
						+ " AND TRIM(SpecContent) IS NOT NULL"
						+ " and prtflag is null "	
						//只打出本次特约，排除以前的特约信息
						+ " and not exists(select 1 from lccspec where serialno=lpcspec.serialno)"
						+ " ORDER BY ModifyDate, ModifyTime DESC";;
		sqlbv6.sql(tForSpec);
		sqlbv6.put("tEdorNo", tEdorNo);
		sqlbv6.put("tEdorType", tEdorType);
		sqlbv6.put("tContNo", tContNo);
		SSRS tSSRSForSpec = new SSRS();
		tSSRSForSpec = tExeSQL.execSQL(sqlbv6);
		logger.debug("查询特约SQL:" + tForSpec);
		SSRS yssrs = new SSRS();
		ExeSQL yExeSQL = new ExeSQL();
		yssrs = yExeSQL.execSQL(sqlbv6);

		if (yssrs.getMaxRow() == 0) {
			SpecFlag = false;
		} else {
			int n=0;
			for(int j=1;j<=yssrs.getMaxRow();j++)
			if (!((yssrs.GetText(j, 1).equals("")) || (yssrs.GetText(j, 1) == null))) {
				SpecFlag = true;//特约标志
				n++;
				content = n +"、" + yssrs.GetText(j, 1);
				getContent(tSpecListTable, content ,40);
			}
		}
		logger.debug("是否有特约标志**********    "+SpecFlag);
		
		ListTable tChangeListTable = new ListTable();
		tChangeListTable.setName("CHANGE"); // 对应模版问题部分的行对象名		
		String[] ChangeTitle = new String[1];
		ChangeTitle[0]= "Change";
		
		//是否有承保计划变更和加费
		// 是否有承保计划变更
//		String strSql = " select count(1) from( "
//						+" select 1 from LPUWMaster where edorno='"
//						+tEdorNo+"' and contno='" + tContNo + "' and ChangePolFlag='1' "
//						+ " union all "
//						+ " select 1 from LPPol where edorno='"+tEdorNo+"' and contno='"
//						+ tContNo + "' and uwflag in ('1','2','a') and appflag<>'4' "
//						+ ")";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		//附加险是否有非标准体和次标准体险种结论
		String strSql = " select count(1) from LPPol where edorno='"+"?tEdorNo?"+"' and contno='"
			+ "?tContNo?" + "' and polno<>mainpolno and uwflag in ('1','2','a') and appflag<>'4' ";
		sqlbv7.sql(strSql);
		sqlbv7.put("tEdorNo", tEdorNo);
		sqlbv7.put("tContNo", tContNo);
		tExeSQL = new ExeSQL();
		int rs_change = Integer.parseInt(tExeSQL.getOneValue(sqlbv7));
		if (rs_change > 0) {		    
			ChangePolFlag = true;
		}	

		// 是否有加费
		strSql = "select count(1) from lpprem where edorno='"
						+"?tEdorNo?"+"' and contno='"+ "?tContNo?" 
						+ "' and payplancode like '000000%%' ";
		sqlbv7.sql(strSql);
		sqlbv7.put("tEdorNo", tEdorNo);
		sqlbv7.put("tContNo", tContNo);
		tExeSQL = new ExeSQL();
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv7));
		if (rs_addfee > 0) {		    
			ChangePolFlag = true;
		}
		logger.debug("是否有承保计划变更标志**********    "+ChangePolFlag);
		if(ChangePolFlag == true)
		{
			//取承保计划变更和加费原因
			LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
			tLPIndUWMasterDB.setEdorNo(tEdorNo);
			tLPIndUWMasterDB.setContNo(tLPContSchema.getContNo());
			LPIndUWMasterSet tLPIndUWMasterSet = tLPIndUWMasterDB.query();
			if (tLPIndUWMasterSet == null || tLPIndUWMasterSet.size()<1) {
				mErrors.copyAllErrors(tLPIndUWMasterDB.mErrors);
				CError.buildErr(this, "在取得LPIndUWMaster的数据时发生错误");
				return false;
			}
			LPIndUWMasterSchema tLPIndUWMasterSchema = tLPIndUWMasterSet.get(1);
			if(tLPIndUWMasterSchema.getUWIdea()!=null && !tLPIndUWMasterSchema.getUWIdea().equals(""))
			{
				Reason = true;
				content = "  " + tLPIndUWMasterSchema.getUWIdea();//取变更和加费原因
				getContent(tChangeListTable, content ,40);
			}
		}
		logger.debug("是否有承保计划变更和加费原因**********    "+ChangePolFlag);				
		//************************end***********************

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();		

		// 1-险种信息：
		logger.debug("***************开始查询本次保全前的保单信息***********************");
		ListTable tRiskListTable = new ListTable();
		String[] RiskInfoTitle = new String[9];
		String[] RiskInfo = new String[9];
		tRiskListTable.setName("MAIN"); // 对应模版投保信息部分的行对象名
		RiskInfoTitle[0] = "RiskName"; // 险种名称
		RiskInfoTitle[1] = "Amnt"; // 保险金额
		RiskInfoTitle[2] = "PayYears"; // 缴费年期
		RiskInfoTitle[3] = "PayIntv"; // 缴费方式（间隔）
		RiskInfoTitle[4] = "Prem"; // 保费
		RiskInfoTitle[5] = "JobAddPrem"; // 职业加费
		RiskInfoTitle[6] = "HealthAddPrem"; // 健康加费
		RiskInfoTitle[7] = "LiveAddPrem"; // 居住地加费
		RiskInfoTitle[8] = "HobbyAddPrem"; // 爱好加费
		
		double oldSumPrem = 0.00; // 合计保费
		double oldSumJobAddFee = 0.00; // 合计加费       
		double oldSumLiveAddFee = 0.00;
		double oldSumHobbyAddFee = 0.00;
		double oldSumHealthAddFee = 0.00; // 最后的合计金额	
		LMRiskDB tLMRiskDB;
		String sTemp;
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS tempSSRS = new SSRS();
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			String Sql = "select * from LCPol where contno='"
			+ "?tContNo?" + "' and uwflag not in ('1','2','a') and appflag not in ('2','4','9') " 
			+ " order by mainpolno,polno ";
			sqlbv9.sql(Sql);
			sqlbv9.put("tContNo", tContNo);
			LCPolDB tempLCPolDB = new LCPolDB();
			LCPolSchema tLCPolSchema_sub = new LCPolSchema();
			LCPolSet tLCPolSet_sub = tempLCPolDB.executeQuery(sqlbv9);
		
			if (tLCPolSet_sub != null) {
				gLCPolSet.set(tLCPolSet_sub); // 保存附加险保单集合,后用
		
				for (int n = 1; n <= gLCPolSet.size(); n++) {
					tLCPolSchema_sub = gLCPolSet.get(n);
					RiskInfo = new String[9];
					tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLCPolSchema_sub.getRiskCode());
		
					if (!tLMRiskDB.getInfo()) {
						mErrors.copyAllErrors(tLMRiskDB.mErrors);
						CError.buildErr(this, "在取得LMRisk的数据时发生错误");		
						return false;
					}
		
					RiskInfo[0] = tLMRiskDB.getRiskName(); // 险种名称
					RiskInfo[1] = mDecimalFormat.format(tLCPolSchema_sub.getAmnt()); // 保险金额
		
					if ((tLCPolSchema_sub.getPayEndYear() == 1000)
							&& tLCPolSchema_sub.getPayEndYearFlag().equals("A")) {
						RiskInfo[2] = "终生"; // 交费年期
					} else {
						RiskInfo[2] = (new Integer(tLCPolSchema_sub.getPayYears()))
								.toString(); // 交费年期
					}
		
					sTemp = "";
		
					if (tLCPolSchema_sub.getPayIntv() == -1) {
						sTemp = "不定期交费";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 0) {
						sTemp = "趸交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 1) {
						sTemp = "月交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 3) {
						sTemp = "季交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 6) {
						sTemp = "半年交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 12) {
						sTemp = "年交";
					}
		
					RiskInfo[3] = sTemp; // 交费方式
					RiskInfo[4] = mDecimalFormat.format(tLCPolSchema_sub
							.getStandPrem()); // 保费				
					oldSumPrem = oldSumPrem + tLCPolSchema_sub.getStandPrem(); // 原保费合计
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype='02'");
					sqlbv10.put("PolNo", tLCPolSchema_sub.getPolNo());
					// 险种的职业加费
					tempSSRS = tempExeSQL.execSQL(sqlbv10);
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("累计职业加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[5] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumJobAddFee = oldSumJobAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[5] = "0.00";
						}
					}
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype='01'");
					sqlbv11.put("PolNo", tLCPolSchema_sub.getPolNo());
					// 险种的健康加费
					tempSSRS = tempExeSQL.execSQL(sqlbv11);
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {								
							logger.debug("累计健康加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[6] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumHealthAddFee = oldSumHealthAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[6] = "0.00";
						}
					}
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype='03'");
					sqlbv12.put("PolNo", tLCPolSchema_sub.getPolNo());
					// 险种的居住地加费
					tempSSRS = tempExeSQL.execSQL(sqlbv12);
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("累计居住地加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[7] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumLiveAddFee = oldSumLiveAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[7] = "0.00";
						}
					}
					SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
					sqlbv13.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype='04'");
					sqlbv13.put("PolNo", tLCPolSchema_sub.getPolNo());
					// 险种的爱好加费
					tempSSRS = tempExeSQL.execSQL(sqlbv13);
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("累计爱好加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[8] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumHobbyAddFee = oldSumHobbyAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[8] = "0.00";
						}
					}
					
					tRiskListTable.add(RiskInfo); // 加入险种信息
				}
			}
		
			oldSumPrem = Double.parseDouble(mDecimalFormat.format(oldSumPrem)); // 转换计算后的保费(规定的精度)		
			oldSumHealthAddFee = Double.parseDouble(mDecimalFormat.format(oldSumHealthAddFee)); // 转换计算后的保费(规定的精度)
			oldSumJobAddFee = Double.parseDouble(mDecimalFormat
					.format(oldSumJobAddFee));
			oldSumLiveAddFee = Double.parseDouble(mDecimalFormat
					.format(oldSumLiveAddFee));
			oldSumHobbyAddFee = Double.parseDouble(mDecimalFormat
					.format(oldSumHobbyAddFee));
			
			double TotleSumPrem=0.00;
			double AllSumPrem = 0.00;
			double SumPrem = 0.00;
			double SumJobAddFee = 0.00;
			double SumLiveAddFee = 0.00;
			double SumHobbyAddFee = 0.00;
			double SumHealthAddFee = 0.00; // 最后的合计金额
			ListTable tChangePolListTable = new ListTable();
			String[] ChangePol = new String[9];
			String[] ChangePolTitle = new String[9];

			ChangePolTitle[0] = "RiskName"; // 险种名称
			ChangePolTitle[1] = "Amnt"; // 保险金额
			ChangePolTitle[2] = "PayYears"; // 缴费年期
			ChangePolTitle[3] = "PayIntv"; // 缴费方式（间隔）
			ChangePolTitle[4] = "Prem"; // 保费
			ChangePolTitle[5] = "JobAddPrem"; // 职业加费
			ChangePolTitle[6] = "HealthAddPrem"; // 健康加费
			ChangePolTitle[7] = "LivePrem"; // 居住地加费
			ChangePolTitle[8] = "HobbyPrem"; // 爱好加费
			tChangePolListTable.setName("ChangePol"); // 对应模版加费部分的行对象名
		if(ChangePolFlag ==true)
		{
			// 4-现在的保单信息
			// 上面补充的保险计划变更已经查询
		//	String AddFeeReason = tLCCUWMasterDB.getAddPremReason(); // 得到加费原因,后用

			logger.debug("***************开始查询现在的保单信息***********************");						
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				Sql = "select * from LPPol where edorno='"+"?tEdorNo?"+"' and contno='"
				+ "?tContNo?" + "' and uwflag not in ('1','2','a') and appflag<>'4' order by mainpolno,polno ";
				sqlbv14.sql(Sql);
				sqlbv14.put("tEdorNo", tEdorNo);
				sqlbv14.put("tContNo", tContNo);
				LPPolDB tempLPPolDB = new LPPolDB();
				LPPolSchema tLPPolSchema_sub = new LPPolSchema();
				LPPolSet tLPPolSet_sub = tempLPPolDB.executeQuery(sqlbv14);
			
				if (tLPPolSet_sub != null) {
					gLPPolSet.set(tLPPolSet_sub); // 保存附加险保单集合,后用
			
					for (int n = 1; n <= gLPPolSet.size(); n++) {
						tLPPolSchema_sub = gLPPolSet.get(n);
						ChangePol = new String[9];
						tLMRiskDB = new LMRiskDB();
						tLMRiskDB.setRiskCode(tLPPolSchema_sub.getRiskCode());
			
						if (!tLMRiskDB.getInfo()) {
							mErrors.copyAllErrors(tLMRiskDB.mErrors);
							CError.buildErr(this, "在取得LMRisk的数据时发生错误");
			
							return false;
						}
			
						ChangePol[0] = tLMRiskDB.getRiskName(); // 险种名称
						ChangePol[1] = mDecimalFormat.format(tLPPolSchema_sub.getAmnt()); // 保险金额
			
						if ((tLPPolSchema_sub.getPayEndYear() == 1000)
								&& tLPPolSchema_sub.getPayEndYearFlag().equals("A")) {
							ChangePol[2] = "终生"; // 交费年期
						} else {
							ChangePol[2] = (new Integer(tLPPolSchema_sub.getPayYears()))
									.toString(); // 交费年期
						}
			
						sTemp = "";
			
						if (tLPPolSchema_sub.getPayIntv() == -1) {
							sTemp = "不定期交费";
						}
			
						if (tLPPolSchema_sub.getPayIntv() == 0) {
							sTemp = "趸交";
						}
			
						if (tLPPolSchema_sub.getPayIntv() == 1) {
							sTemp = "月交";
						}
			
						if (tLPPolSchema_sub.getPayIntv() == 3) {
							sTemp = "季交";
						}
			
						if (tLPPolSchema_sub.getPayIntv() == 6) {
							sTemp = "半年交";
						}
			
						if (tLPPolSchema_sub.getPayIntv() == 12) {
							sTemp = "年交";
						}
			
						ChangePol[3] = sTemp; // 交费方式
						ChangePol[4] = mDecimalFormat.format(tLPPolSchema_sub
								.getStandPrem()); // 保费				
						SumPrem = SumPrem + tLPPolSchema_sub.getStandPrem(); // 原保费合计
						AllSumPrem = tLPPolSchema_sub.getStandPrem(); //统计所有保费
			
						SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
						sqlbv15.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LPPrem where edorno='"+"?tEdorNo?"+"' and PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='02' and (addform is null or addform<>'3')");
						sqlbv15.put("tEdorNo", tEdorNo);
						sqlbv15.put("PolNo", tLPPolSchema_sub.getPolNo());
						// 险种的职业加费
						tempSSRS = tempExeSQL.execSQL(sqlbv15);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计职业加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[5] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumJobAddFee = SumJobAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
								AllSumPrem = AllSumPrem + Double.parseDouble(tempSSRS.GetText(1, 1)); 
							} else {
								ChangePol[5] = "0.00";
							}
						}
						SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
						sqlbv16.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LPPrem where edorno='"+"?tEdorNo?"+"' and PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='01' and (addform is null or addform<>'3')");
						sqlbv16.put("tEdorNo", tEdorNo);
						sqlbv16.put("PolNo", tLPPolSchema_sub.getPolNo());
						// 险种的健康加费
						tempSSRS = tempExeSQL.execSQL(sqlbv16);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {								
								logger.debug("累计健康加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[6] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumHealthAddFee = SumHealthAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
								AllSumPrem = AllSumPrem + Double.parseDouble(tempSSRS.GetText(1, 1)); 
							} else {
								ChangePol[6] = "0.00";
							}
						}
						SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
						sqlbv17.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LPPrem where edorno='"+"?tEdorNo?"+"' and PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='03' and (addform is null or addform<>'3')");
						sqlbv17.put("tEdorNo", tEdorNo);
						sqlbv17.put("PolNo", tLPPolSchema_sub.getPolNo());
						// 险种的居住地加费 // 现改为险种的其他加费
						tempSSRS = tempExeSQL.execSQL(sqlbv17);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
//								logger.debug("累计居住地加费是"
//										+ tempSSRS.GetText(1, 1));
								logger.debug("累计其他加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[7] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumLiveAddFee = SumLiveAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
								AllSumPrem = AllSumPrem + Double.parseDouble(tempSSRS.GetText(1, 1)); 
							} else {
								ChangePol[7] = "0.00";
							}
						}
						
						if("1".equals(CompanyFlag))
						{
							ChangePol[8] = mDecimalFormat.format(AllSumPrem);
							TotleSumPrem = TotleSumPrem+AllSumPrem;
						}
						else
						{
							SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
							sqlbv18.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LPPrem where edorno='"+"?tEdorNo?"+"' and PolNo='"
									+ "?PolNo?"
									+ "' and PayPlanCode like '000000%'  and payplantype='04' and (addform is null or addform<>'3')");
							sqlbv18.put("tEdorNo", tEdorNo);
							sqlbv18.put("PolNo", tLPPolSchema_sub.getPolNo());
//							 险种的爱好加费
							tempSSRS = tempExeSQL.execSQL(sqlbv18);
				
							if (tempSSRS.MaxCol > 0) {
								if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
										.GetText(1, 1).trim().equals(""))) {
									logger.debug("累计爱好加费是"
											+ tempSSRS.GetText(1, 1));
									ChangePol[8] = mDecimalFormat.format(Double
											.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
									SumHobbyAddFee = SumHobbyAddFee
											+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
								} else {
									ChangePol[8] = "0.00";
								}
							}
						}
						tChangePolListTable.add(ChangePol); // 加入险种信息
					}
				}
			
				SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); // 转换计算后的保费(规定的精度)		
				SumHealthAddFee = Double.parseDouble(mDecimalFormat.format(SumHealthAddFee)); // 转换计算后的保费(规定的精度)
				SumJobAddFee = Double.parseDouble(mDecimalFormat
						.format(SumJobAddFee));
				SumLiveAddFee = Double.parseDouble(mDecimalFormat
						.format(SumLiveAddFee));
				SumHobbyAddFee = Double.parseDouble(mDecimalFormat
						.format(SumHobbyAddFee));
				TotleSumPrem =  Double.parseDouble(mDecimalFormat
						.format(TotleSumPrem));
		}
//******************************准备打印需要的数据********************************************
//********************************************************************************************
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String printName = "";
		if("1".equals(CompanyFlag))
		{//公司层面保全调用专用模板
			printName = "保全核保通知书2";
		}
		else
		{
			printName = "保全核保通知书";
		}
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tempLCPolDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

		String replyDate1 = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 10, "D","" );
		String today = PubFun.getCurrentDate();
		String replyDate = replyDate1;
		String replyDate2 = PubFun.calDate(tLPContSchema.getCValiDate(), PubFun.calPolYear(tLPContSchema.getCValiDate(), today), "Y","" );//生效日期
		String replyDateA = PubFun.getBeforeDate(mLOPRTManagerSchema.getMakeDate(),replyDate2) ;
		if(!replyDateA.equals(replyDate2))
			replyDate = PubFun.getBeforeDate(replyDate1,replyDate2) ;

		replyDate = replyDate + "-";
		String CheckDate = StrTool.decodeStr(replyDate, "-", 1) + "年"
			+ StrTool.decodeStr(replyDate, "-", 2) + "月"
			+ StrTool.decodeStr(replyDate, "-", 3) + "日";


		// 生成-年-月-日格式的日期
		String SysDate = new SimpleDateFormat("yyyy年mm月dd日").format(new Date());

		// 模版自上而下的元素
		logger.debug("mLOPRTManagerSchema code:"
				+ mLOPRTManagerSchema.getCode());	

		texttag.add("AppntName", tLPContSchema.getAppntName()); // 投保人名称		
		texttag.add("ContNo", tLPContSchema.getContNo()); // 印刷号
		texttag.add("SysDate", SysDate);		
		String date = tLPContSchema.getCValiDate();
		date = date + "-";
		String ldate = StrTool.decodeStr(date, "-", 1) + "年"
		+ StrTool.decodeStr(date, "-", 2) + "月"
		+ StrTool.decodeStr(date, "-", 3) + "日";
		texttag.add("Date", ldate);
		texttag.add("InsuredName", tLPInsuredSchema.getName()); // 被保人名称
		texttag.add("InsuredIDNo", tLPInsuredSchema.getIDNo()); // 被保人号码		
		//texttag.add("CheckDate", checkDate); 
		texttag.add("CheckDate", CheckDate);
		xmlExport.addDisplayControl("displayold"); // 显示投保险种信息
		xmlExport.addListTable(tRiskListTable, RiskInfoTitle); 
		texttag.add("oldSumPrem", mDecimalFormat.format(oldSumPrem)); // 合计保费
		texttag.add("oldSumJobAddFee", mDecimalFormat.format(oldSumJobAddFee)); // 合计职业加费
		texttag.add("oldSumHealthAddFee", mDecimalFormat.format(oldSumHealthAddFee)); // 合计健康加费
		texttag.add("oldSumLiveAddFee", mDecimalFormat.format(oldSumLiveAddFee));
		texttag.add("oldSumHobbyAddFee", mDecimalFormat.format(oldSumHobbyAddFee));

		if(SpecFlag == true)
		{
			xmlExport.addDisplayControl("display");
			xmlExport.addDisplayControl("displayspec"); // 显示特约信息
			xmlExport.addListTable(tSpecListTable, SpecTitle); 
		}			
		
		if(ChangePolFlag == true)
		{
			if(Reason == true)
			{
				xmlExport.addDisplayControl("displaychangepolreason"); // 显示承保计划变更原因信息
				xmlExport.addListTable(tChangeListTable, ChangeTitle);
			}			
			
			xmlExport.addDisplayControl("display");
			xmlExport.addDisplayControl("displaychangepol"); // 显示承保计划变更信息
			xmlExport.addListTable(tChangePolListTable, ChangePolTitle); 
			texttag.add("SumPrem", mDecimalFormat.format(SumPrem)); // 合计保费
			texttag.add("SumJobAddFee", mDecimalFormat.format(SumJobAddFee)); // 合计职业加费
			texttag.add("SumHealthAddFee", mDecimalFormat.format(SumHealthAddFee)); // 合计健康加费
			texttag.add("SumLiveAddFee", mDecimalFormat.format(SumLiveAddFee));
			texttag.add("SumHobbyAddFee", mDecimalFormat.format(SumHobbyAddFee));
			texttag.add("SumAllPrem", mDecimalFormat.format(TotleSumPrem));
			
			//texttag.add("SumOtherAddFee", mDecimalFormat.format(SumLiveAddFee + SumHobbyAddFee));
		}		
		
		texttag.add("AgentName", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", tLPContSchema.getAgentCode()); // 代理人业务号
		texttag.add("ManageCom", tLPContSchema.getManageCom()); // 营业机构
		texttag.add("PrtSeq", PrtSeq); // 流水号
		// 核保师代码 核保<--核保师代码
		texttag.add("UWCode", mLOPRTManagerSchema.getReqOperator());
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
        //add 保全员工号
		String BQOperator_sql="select operator from lpedoritem where edorno='"+"?tEdorNo?"+"'";
		sqlbv19.sql(BQOperator_sql);
		sqlbv19.put("tEdorNo", tEdorNo);
		texttag.add("BQOperator", tExeSQL.getOneValue(sqlbv19)); // 保全员工号
		
		String phone = "";
		if(mLAAgentSchema.getPhone()!=null && !mLAAgentSchema.getPhone().equals(""))
		    phone = mLAAgentSchema.getPhone();
		else
			phone = mLAAgentSchema.getMobile();
		texttag.add("Phone", phone);		

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlExport);
		return true;
	}
	
	
	
	private boolean getPrintData_HBN() {
		LCPolSet gLCPolSet = new LCPolSet();
		// 根据印刷号查询打印队列中的纪录
		ExeSQL tExeSQL = new ExeSQL();

		String PrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			CError.buildErr(this, "在取得打印队列中数据时发生错误");

			return false;
		}
		
		//查询工作流数据等
		LWMissionDB tLWMissionDB = new LWMissionDB();
		if(mLOPRTManagerSchema.getCode().equals("27"))
			tLWMissionDB.setActivityID("0000000351");
		//tLWMissionDB.setProcessID("0000000000");
		tLWMissionDB.setMissionProp2(mLOPRTManagerSchema.getOtherNo());
		tLWMissionDB.setMissionProp3(PrtSeq);
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if(tLWMissionSet!=null && tLWMissionSet.size()<1) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError.buildErr(this,  "在取得工作流表数据时发生错误");

			return false;
		}
		LWMissionSchema tLWMissionSchema = tLWMissionSet.get(1);
		
		//***********************获得客户号等*********************88		
		String tContNo = tLWMissionSchema.getMissionProp2();
		String tEdorNo = tLWMissionSchema.getMissionProp8();
		String tEdorType = tLWMissionSchema.getMissionProp9();//		

        //判断是否是公司层面保全任务
		String CompanyFlag="0"; //0,非公司层面；1，公司层面；默认为0
		String CHECK_sql="";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql("select edoracceptno from lpedoritem where edorno='"+"?tEdorNo?"+"'");
		sqlbv20.put("tEdorNo", tEdorNo);
		String mEdorAcceptNo=tExeSQL.getOneValue(sqlbv20);
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		CHECK_sql="select createoperator from lbmission a where missionprop1='"+"?mEdorAcceptNo?"
		+"' and activityid in ('0000000001','0000000002') and exists(select 1 from lduwuser where usercode=a.createoperator) ";
		sqlbv21.sql(CHECK_sql);
		sqlbv21.put("mEdorAcceptNo", mEdorAcceptNo);
		String CreateOperator =tExeSQL.getOneValue(sqlbv21);
		if(CreateOperator!=null && !"".equals(CreateOperator))
		{
			CompanyFlag="1";
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();			

		LPContDB tLPContDB = new LPContDB();
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		String strSQL = "SELECT * FROM LPCont WHERE EdorNo='"+"?tEdorNo?"+"' and ContNo = '"
				+ "?ContNo?" + "'";
		sqlbv22.sql(strSQL);
		sqlbv22.put("tEdorNo", tEdorNo);
		sqlbv22.put("ContNo", mLOPRTManagerSchema.getOtherNo());
		LPContSet tempLPContSet = tLPContDB.executeQuery(sqlbv22);

		if (tempLPContSet==null || tempLPContSet.size() == 0) {
			mErrors.copyAllErrors(tLPContDB.mErrors);
			CError.buildErr(this, "在LPCont表中找不到相关信息");
			return false;
		}

		int m;
		int i;
		tLPContDB.setSchema(tempLPContSet.get(1));
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema = tLPContDB.getSchema();

		String tAgentCode = tLPContSchema.getAgentCode();

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tAgentCode);

		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError.buildErr(this, "在取得LAAgent的数据时发生错误");

			return false;
		}

		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
//		*************************被保人信息**************************
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema = tLPInsuredDB.getSchema(); // 保存被保人信息
		tLPInsuredDB.setEdorNo(tEdorNo);
		tLPInsuredDB.setEdorType(tEdorType);
		tLPInsuredDB.setContNo(tLPContSchema.getContNo());
		tLPInsuredDB.setInsuredNo(tLPContSchema.getInsuredNo());
	
		if (!tLPInsuredDB.getInfo()) {
			mErrors.copyAllErrors(tLPInsuredDB.mErrors);
			CError.buildErr(this, "在取得LPInsured的数据时发生错误");
	
			//return false;
		}
	
			tLPInsuredSchema = tLPInsuredDB.getSchema(); // 保存代理人信息
						
// 3-问题信息		
	    String content = "";
		String[] QuestionTitle = new String[1];
		QuestionTitle[0] = "Question";
		ListTable tQuestionListTable = new ListTable();
		
			tQuestionListTable.setName("QUESTION"); // 对应模版问题部分的行对象名

			String q_sql = "";
			String q_sql_printFlag = "";
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			// 查询待打印的问题件
			q_sql = "select IssueCont from LPIssuePol where BackObjType = '2' and NeedPrint = 'Y' "
					+ " and ContNo = '"
					+ "?ContNo?"					
					+ "' and state in ('0','1') ";
			q_sql = q_sql + q_sql_printFlag;
			sqlbv23.sql(q_sql);
			sqlbv23.put("ContNo", tLPContSchema.getContNo());
			logger.debug("问题件中所执行的sql是" + q_sql);

			ExeSQL q_exesql = new ExeSQL();
			SSRS q_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sqlbv23);

			if (q_ssrs.getMaxRow() == 0) {
				logger.debug("没有问题件记录");
				
			} else {
				logger.debug("问题中有" + q_ssrs.getMaxRow() + "条记录！！！");

				for (i = 1; i <= q_ssrs.getMaxRow(); i++) {
					content = i + "、" + q_ssrs.GetText(1, i); // 问题件内容
					getContent(tQuestionListTable, content ,40);
				}
			}

			// 4-保全前的保单信息
			// 上面补充的保险计划变更已经查询
		//	String AddFeeReason = tLCCUWMasterDB.getAddPremReason(); // 得到加费原因,后用

			logger.debug("***************开始查询保全前的保单信息***********************");
			LMRiskDB tLMRiskDB;
			String sTemp;
			ExeSQL tempExeSQL = new ExeSQL();
			SSRS tempSSRS = new SSRS();
			double SumPrem = 0.00;
			double SumJobAddFee = 0.00;
			double SumLiveAddFee = 0.00;
			double SumHobbyAddFee = 0.00;
			double SumHealthAddFee = 0.00; // 最后的合计金额
			ListTable tChangePolListTable = new ListTable();
			String[] ChangePol = new String[9];
			String[] ChangePolTitle = new String[9];

			ChangePolTitle[0] = "RiskName"; // 险种名称
			ChangePolTitle[1] = "Amnt"; // 保险金额
			ChangePolTitle[2] = "PayYears"; // 缴费年期
			ChangePolTitle[3] = "PayIntv"; // 缴费方式（间隔）
			ChangePolTitle[4] = "Prem"; // 保费
			ChangePolTitle[5] = "JobAddPrem"; // 职业加费
			ChangePolTitle[6] = "HealthAddPrem"; // 健康加费
			ChangePolTitle[7] = "LivePrem"; // 居住地加费
			ChangePolTitle[8] = "HobbyPrem"; // 爱好加费
			tChangePolListTable.setName("ChangePol"); // 对应模版加费部分的行对象名			
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				String Sql = "select * from LCPol where contno='"
				+ "?contno?" + "' and uwflag not in ('1','2','a') and appflag not in ('2','4','9') " 
				+ " order by mainpolno,polno ";
				sqlbv24.sql(Sql);
				sqlbv24.put("contno", tLPContSchema.getContNo());
				LCPolDB tempLCPolDB = new LCPolDB();
				LCPolSchema tLCPolSchema_sub = new LCPolSchema();
				LCPolSet tLCPolSet_sub = tempLCPolDB.executeQuery(sqlbv24);
			
				if (tLCPolSet_sub != null) {
					gLCPolSet.set(tLCPolSet_sub); // 保存附加险保单集合,后用
			
					for (int n = 1; n <= gLCPolSet.size(); n++) {
						tLCPolSchema_sub = gLCPolSet.get(n);
						ChangePol = new String[9];
						tLMRiskDB = new LMRiskDB();
						tLMRiskDB.setRiskCode(tLCPolSchema_sub.getRiskCode());
			
						if (!tLMRiskDB.getInfo()) {
							mErrors.copyAllErrors(tLMRiskDB.mErrors);
							CError.buildErr(this, "在取得LMRisk的数据时发生错误");
			
							return false;
						}
			
						ChangePol[0] = tLMRiskDB.getRiskName(); // 险种名称
						ChangePol[1] = mDecimalFormat.format(tLCPolSchema_sub.getAmnt()); // 保险金额
			
						if ((tLCPolSchema_sub.getPayEndYear() == 1000)
								&& tLCPolSchema_sub.getPayEndYearFlag().equals("A")) {
							ChangePol[2] = "终生"; // 交费年期
						} else {
							ChangePol[2] = (new Integer(tLCPolSchema_sub.getPayYears()))
									.toString(); // 交费年期
						}
			
						sTemp = "";
			
						if (tLCPolSchema_sub.getPayIntv() == -1) {
							sTemp = "不定期交费";
						}
			
						if (tLCPolSchema_sub.getPayIntv() == 0) {
							sTemp = "趸交";
						}
			
						if (tLCPolSchema_sub.getPayIntv() == 1) {
							sTemp = "月交";
						}
			
						if (tLCPolSchema_sub.getPayIntv() == 3) {
							sTemp = "季交";
						}
			
						if (tLCPolSchema_sub.getPayIntv() == 6) {
							sTemp = "半年交";
						}
			
						if (tLCPolSchema_sub.getPayIntv() == 12) {
							sTemp = "年交";
						}
			
						ChangePol[3] = sTemp; // 交费方式
						ChangePol[4] = mDecimalFormat.format(tLCPolSchema_sub
								.getStandPrem()); // 保费				
						SumPrem = SumPrem + tLCPolSchema_sub.getStandPrem(); // 原保费合计
						SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
						sqlbv25.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='02'");
						sqlbv25.put("PolNo", tLCPolSchema_sub.getPolNo());
						// 险种的职业加费
						tempSSRS = tempExeSQL.execSQL(sqlbv25);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计职业加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[5] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumJobAddFee = SumJobAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[5] = "0.00";
							}
						}
						
						// 险种的健康加费
						SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
						sqlbv26.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='01'");
						sqlbv26.put("PolNo", tLCPolSchema_sub.getPolNo());
						tempSSRS = tempExeSQL.execSQL(sqlbv26);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {								
								logger.debug("累计健康加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[6] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumHealthAddFee = SumHealthAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[6] = "0.00";
							}
						}
						SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
						sqlbv27.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
								+"?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='03'");
						sqlbv27.put("PolNo", tLCPolSchema_sub.getPolNo());
						// 险种的居住地加费
						tempSSRS = tempExeSQL.execSQL(sqlbv27);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计居住地加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[7] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumLiveAddFee = SumLiveAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[7] = "0.00";
							}
						}
						
						// 险种的爱好加费
						SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
						sqlbv28.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
								+"?PolNo?"
								+ "' and PayPlanCode like '000000%'  and payplantype='04'");
						sqlbv28.put("PolNo", tLCPolSchema_sub.getPolNo());
						tempSSRS = tempExeSQL.execSQL(sqlbv28);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计爱好加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[8] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumHobbyAddFee = SumHobbyAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[8] = "0.00";
							}
						}
						
						tChangePolListTable.add(ChangePol); // 加入险种信息
					}
				}
			
				SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); // 转换计算后的保费(规定的精度)		
				SumHealthAddFee = Double.parseDouble(mDecimalFormat.format(SumHealthAddFee)); // 转换计算后的保费(规定的精度)
				SumJobAddFee = Double.parseDouble(mDecimalFormat
						.format(SumJobAddFee));
				SumLiveAddFee = Double.parseDouble(mDecimalFormat
						.format(SumLiveAddFee));
				SumHobbyAddFee = Double.parseDouble(mDecimalFormat
						.format(SumHobbyAddFee));		
			
	//******************************准备打印需要的数据********************************************
	//********************************************************************************************
			// 其它模版上单独不成块的信息
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String printName = "" ;
			if("1".equals(CompanyFlag))
			{//公司层面保全调用专用模板
				printName = "保全补充资料通知书2";
			}
			else
			{
				printName = "保全补充资料通知书";
			}
			XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
			xmlExport.createDocument(printName);//初始化数据存储类
			PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
			String uLanguage = PrintTool.getCustomerLanguage(tLPContSchema.getAppntNo());
			if (uLanguage != null && !"".equals(uLanguage)) 
				xmlExport.setUserLanguage(uLanguage);
			xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

			// 生成-年-月-日格式的日期
			StrTool tSrtTool = new StrTool();
			String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
					+ tSrtTool.getDay() + "日";

			// 模版自上而下的元素
			logger.debug("mLOPRTManagerSchema code:"
					+ mLOPRTManagerSchema.getCode());			

			texttag.add("SysDate", SysDate);
			texttag.add("AppntName", tLPContSchema.getAppntName()); // 投保人名称
			texttag.add("ContNo", tLPContSchema.getContNo()); // 
			String date = tLPContSchema.getCValiDate();
			date = date + "-";
			String ldate = StrTool.decodeStr(date, "-", 1) + "年"
			+ StrTool.decodeStr(date, "-", 2) + "月"
			+ StrTool.decodeStr(date, "-", 3) + "日";
			texttag.add("Date", ldate);
			texttag.add("InsuredName", tLPInsuredSchema.getName()); // 被保人名称
			texttag.add("InsuredIDNo", tLPInsuredSchema.getIDNo()); // 被保人号码	
			
			//加入保单信息
			xmlExport.addListTable(tChangePolListTable, ChangePolTitle); 
			texttag.add("SumPrem", mDecimalFormat.format(SumPrem)); // 合计保费
			texttag.add("SumJobAddFee", mDecimalFormat.format(SumJobAddFee)); // 合计职业加费
			texttag.add("SumHealthAddFee", mDecimalFormat.format(SumHealthAddFee)); // 合计健康加费
			texttag.add("SumLiveAddFee", mDecimalFormat.format(SumLiveAddFee));
			texttag.add("SumHobbyAddFee", mDecimalFormat.format(SumHobbyAddFee));	
			
			String tNextPolDate = PubFun.calDate(tLPContSchema.getCValiDate(), 1, "Y",tLPContSchema.getCValiDate()) + "-";
			tNextPolDate = StrTool.decodeStr(tNextPolDate, "-", 1) + "年"
			+ StrTool.decodeStr(tNextPolDate, "-", 2) + "月"
			+ StrTool.decodeStr(tNextPolDate, "-", 3) + "日";
			texttag.add("NextPolDate", tNextPolDate); // 次年保单生效日期
			
			
//			当前日期+10天
			/*ExeSQL tExeSQL=new ExeSQL();
			String tSql = "select to_date('"+mLOPRTManagerSchema.getMakeDate()+"')+10 from dual";
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSql);
			String tDate = tSSRS.GetText(1, 1);
			logger.debug(tDate);
			String tArr[] = tDate.split("-");
			String tempStr = tArr[2];
			String tRi = tempStr.substring(0, 2);
			String CheckDate = StrTool.decodeStr(tDate, "-", 1) + "年"
						+ StrTool.decodeStr(tDate, "-", 2) + "月"
						+ tRi + "日";*/
			String replyDate1 = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 10, "D","" );
			String today = PubFun.getCurrentDate();
			String replyDate = replyDate1;
			String replyDate2 = PubFun.calDate(tLPContSchema.getCValiDate(), PubFun.calPolYear(tLPContSchema.getCValiDate(), today), "Y","" );//生效日期
			String replyDateA = PubFun.getBeforeDate(mLOPRTManagerSchema.getMakeDate(),replyDate2) ;
			if(!replyDateA.equals(replyDate2))
				replyDate = PubFun.getBeforeDate(replyDate1,replyDate2) ;

			replyDate = replyDate + "-";
			String CheckDate = StrTool.decodeStr(replyDate, "-", 1) + "年"
				+ StrTool.decodeStr(replyDate, "-", 2) + "月"
				+ StrTool.decodeStr(replyDate, "-", 3) + "日";


			// 保存问题信息
			xmlExport.addListTable(tQuestionListTable, QuestionTitle); // 保存问题信息	
			
			texttag.add("AgentName", mLAAgentSchema.getName()); // 代理人姓名
			texttag.add("AgentCode", tLPContSchema.getAgentCode()); // 代理人业务号
			texttag.add("ManageCom", tLPContSchema.getManageCom()); // 营业机构
			texttag.add("CheckDate", CheckDate); 
			texttag.add("PrtSeq", PrtSeq); // 流水号
			// 核保师代码 核保<--核保师代码
			texttag.add("UWCode", mLOPRTManagerSchema.getReqOperator());
	        //add 保全员工号
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			String BQOperator_sql="select operator from lpedoritem where edorno='"+"?tEdorNo?"+"'";
			sqlbv29.sql(BQOperator_sql);
			sqlbv29.put("tEdorNo", tEdorNo);
			texttag.add("BQOperator", tExeSQL.getOneValue(sqlbv29)); // 保全员工号
			String phone = "";
			if(mLAAgentSchema.getPhone()!=null && !mLAAgentSchema.getPhone().equals(""))
			    phone = mLAAgentSchema.getPhone();
			else
				phone = mLAAgentSchema.getMobile();
			texttag.add("Phone", phone);			

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
	
			mResult.clear();
			mResult.addElement(xmlExport);

		return true;
	}
	
	private boolean getPrintData_HBR() {
		LCPolSet gLCPolSet = new LCPolSet();
		// 根据印刷号查询打印队列中的纪录
		ExeSQL tExeSQL = new ExeSQL();

		String PrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			CError.buildErr(this, "在取得打印队列中数据时发生错误");

			return false;
		}
		
		//查询工作流数据等
		LWMissionDB tLWMissionDB = new LWMissionDB();
		if(mLOPRTManagerSchema.getCode().equals("BQ84"))
			tLWMissionDB.setActivityID("0000000009");
		//tLWMissionDB.setProcessID("0000000000");
		tLWMissionDB.setMissionProp2(mLOPRTManagerSchema.getOtherNo());
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if(tLWMissionSet!=null && tLWMissionSet.size()<1) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError.buildErr(this,  "在取得工作流表数据时发生错误");

			return false;
		}
		LWMissionSchema tLWMissionSchema = tLWMissionSet.get(1);
		
		//***********************获得客户号等*********************88		
		String tContNo = tLWMissionSchema.getMissionProp2();
		String tEdorAcceptNo = tLWMissionSchema.getMissionProp1();
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		String strSQL = "SELECT * FROM LPEdorMain WHERE edoracceptno='"+"?tEdorAcceptNo?"+"' ";
		sqlbv30.sql(strSQL);
		sqlbv30.put("tEdorAcceptNo", tEdorAcceptNo);
		LPEdorMainSet tempLPEdorMainSet = tLPEdorMainDB.executeQuery(sqlbv30);
		if(tempLPEdorMainSet == null || tempLPEdorMainSet.size()<1)
		{
			CError.buildErr(this, "在LPEdorMain表中找不到相关信息");
			return false;
		}
		String tEdorNo = tempLPEdorMainSet.get(1).getEdorNo();		
		
        //判断是否是公司层面保全任务
		String CompanyFlag="0"; //0,非公司层面；1，公司层面；默认为0
		String CHECK_sql="";
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql("select edoracceptno from lpedoritem where edorno='"+"?tEdorNo?"+"'");
		sqlbv31.put("tEdorNo", tEdorNo);
		String mEdorAcceptNo=tExeSQL.getOneValue(sqlbv31);
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		CHECK_sql="select createoperator from lbmission a where missionprop1='"+"?mEdorAcceptNo?"
		+"' and activityid in ('0000000001','0000000002') and exists(select 1 from lduwuser where usercode=a.createoperator) ";
		sqlbv32.sql(CHECK_sql);
		sqlbv32.put("mEdorAcceptNo", mEdorAcceptNo);
		String CreateOperator =tExeSQL.getOneValue(sqlbv32);
		if(CreateOperator!=null && !"".equals(CreateOperator))
		{
			CompanyFlag="1";
		}


		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();			

		LPContDB tLPContDB = new LPContDB();
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		strSQL = "SELECT * FROM LPCont WHERE EdorNo='"+"?tEdorNo?"+"' and ContNo = '"
				+ "?tContNo?" + "'";
		sqlbv33.sql(strSQL);
		sqlbv33.put("tEdorNo", tEdorNo);
		sqlbv33.put("tContNo", tContNo);
		LPContSet tempLPContSet = tLPContDB.executeQuery(sqlbv33);

		if (tempLPContSet==null || tempLPContSet.size() == 0) {
			mErrors.copyAllErrors(tLPContDB.mErrors);
			CError.buildErr(this, "在LPCont表中找不到相关信息");
			return false;
		}

		int m;
		int i;
		tLPContDB.setSchema(tempLPContSet.get(1));
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema = tLPContDB.getSchema();

		String tAgentCode = tLPContSchema.getAgentCode();

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tAgentCode);

		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError.buildErr(this, "在取得LAAgent的数据时发生错误");

			return false;
		}

		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		
		//拒保原因
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(tLPContSchema.getEdorNo());
		tLPCUWMasterDB.setEdorType(tLPContSchema.getEdorType());
		tLPCUWMasterDB.setContNo(tLPContSchema.getContNo());
		LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();
		if (tLPCUWMasterSet == null || tLPCUWMasterSet.size() <= 0) {
			CError.buildErr(this, "在取得LPCUWMaster的数据时发生错误");
			return false;
		}
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("REASON");
		String content = "";		
		content= tLPCUWMasterSet.get(1).getUWIdea();
		String reasonLine1 = "";
		if(content == null || content.equals(""))
		{
			logger.debug("---------没有录入拒保原因-----------");
		}
		else
		{
			int tcontentLen = content.length();		
			int nMaxCharsInOneLine1 = 30;
			int nMaxCharsInOneLine = 35;
			if(tcontentLen - nMaxCharsInOneLine1 > 0)
			{
				reasonLine1 = content.substring(0, nMaxCharsInOneLine1);			
				String content1 = content.substring(nMaxCharsInOneLine1);				
				getContent(tlistTable, content1, nMaxCharsInOneLine);			
			}
			else
				reasonLine1 = content;	
		}			
			
		//getContentHead(tlistTable, content, 55);	
		strArr = new String[1];
		strArr[0] = "REASON";
	//******************************准备打印需要的数据********************************************
	//********************************************************************************************
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String printName = "";
		if("1".equals(CompanyFlag))
		{//公司层面保全调用专用模板
			printName = "保全核保拒保通知书";
		}
		else
		{
			printName = "保全核保拒保通知书";
		}
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLPContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

		String replyDate1 = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 10, "D","" );
		// 生成-年-月-日格式的日期
		StrTool tSrtTool = new StrTool();
		String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";
		
		// 模版自上而下的元素
		logger.debug("mLOPRTManagerSchema code:"
				+ mLOPRTManagerSchema.getCode());			

		texttag.add("Today", SysDate);
		texttag.add("AppntName", tLPContSchema.getAppntName()); // 投保人名称
		texttag.add("ContNo", tLPContSchema.getContNo()); // 	
		texttag.add("PrtNo", tLPContSchema.getPrtNo());
		texttag.add("UWError", "");
		texttag.add("AgentName", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", tLPContSchema.getAgentCode()); // 代理人业务号
		texttag.add("ManageCom", tLPContSchema.getManageCom()); // 营业机构
		texttag.add("PrtSeq", PrtSeq); // 流水号
		// 核保师代码 核保<--核保师代码
		texttag.add("UWCode", mLOPRTManagerSchema.getReqOperator());
        //add 保全员工号
		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
		String BQOperator_sql="select operator from lpedoritem where edorno='"+"?tEdorNo?"+"'";
		sqlbv34.sql(BQOperator_sql);
		sqlbv34.put("tEdorNo", tEdorNo);
		texttag.add("BQOperator", tExeSQL.getOneValue(sqlbv34)); // 保全员工号
		
		String phone = "";
		if(mLAAgentSchema.getPhone()!=null && !mLAAgentSchema.getPhone().equals(""))
		    phone = mLAAgentSchema.getPhone();
		else
			phone = mLAAgentSchema.getMobile();
		texttag.add("Phone", phone);			
		texttag.add("Head", reasonLine1);
		
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		
		xmlExport.addListTable(tlistTable, strArr);

		mResult.clear();
		mResult.addElement(xmlExport);
		return true;
	}
	
	  //问卷的打印 add by liuqh 2008-11-21  残疾问卷
	private void getPrintData_dis() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";

			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "DisabilityData.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}
	//问卷 add by liuqh 2008-11-21  腰椎疾病问卷
	private void getPrintData_wai() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "WaistData.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}
	//问卷 add by liuqh 2008-11-21  癫痫病问卷
	private void getPrintData_epi() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "EpilepsyData.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}
	
	//问卷 add by liuqh 2008-11-21  出国人员问卷
	private void getPrintData_abr() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "AbroadData.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}
	//问卷 add by liuqh 2008-11-21  财务情况补充问卷（甲）
	private void getPrintData_af1() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "Addfinance1Data.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}

	// 下面是一些辅助函数

	/**
	 * 根据印刷号码查询扫描员
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private String getScaner(String tPrtNO) {
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(tPrtNO);

		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tES_DOC_MAINSet = tES_DOC_MAINDB.query();

		if (tES_DOC_MAINSet == null) {
			buildError("getScaner", "没有找到扫描员！");

			return null;
		}

		if (tES_DOC_MAINSet.size() == 0) {
			buildError("getScaner", "没有找到扫描员！");

			return null;
		}

		ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
		tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);

		return tES_DOC_MAINSchema.getScanOperator();
	}
	
	// 下面是一些辅助函数

	/**
	 * 对打印的文字过长一行显示不完时作调整
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private void getContent(ListTable tListTable, String content ,int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr ;
		while (nSpecReasonLen > nMaxCharsInOneLine) {
			content = strSpecReason.substring(0, nMaxCharsInOneLine);
			strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
			nSpecReasonLen -= nMaxCharsInOneLine;
			
			strArr = new String[1];
			strArr[0] = content;
			tListTable.add(strArr);
		}

		if (nSpecReasonLen > 0) {
			strArr = new String[1];
			strArr[0] = strSpecReason;
			tListTable.add(strArr);
		}
	}
	
	/**
	 * 对打印的文字过长一行显示不完时作调整，可以对第一行做特殊处理
	 * 一般第一行截取长度小于后面行，nMaxCharsInOneLine1小于nMaxCharsInOneLine
	 * @param tListTable
	 * @param nMaxCharsInOneLine1 第一行长度
	 * @param nMaxCharsInOneLine
	 * @return content
	 */
	private void getContentHead(ListTable tListTable, String content , int nMaxCharsInOneLine1, int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr ;
		
		if (nSpecReasonLen > nMaxCharsInOneLine1) {
			content = strSpecReason.substring(0, nMaxCharsInOneLine1);
			strSpecReason = strSpecReason.substring(nMaxCharsInOneLine1);
			nSpecReasonLen -= nMaxCharsInOneLine1;
			
			strArr = new String[1];
			strArr[0] = content;
			tListTable.add(strArr);
			
			//剩余字符处理
			while (nSpecReasonLen > nMaxCharsInOneLine) {
				content = strSpecReason.substring(0, nMaxCharsInOneLine);
				strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
				nSpecReasonLen -= nMaxCharsInOneLine;
				
				strArr = new String[1];
				strArr[0] = content;
				tListTable.add(strArr);
			}
		}		

		if (nSpecReasonLen > 0) {
			strArr = new String[1];
			strArr[0] = strSpecReason;
			tListTable.add(strArr);
		}
	}

	private String getRiskName(String strRiskCode) throws Exception {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			throw new Exception("在取得主险LMRisk的数据时发生错误");
		}

		return tLMRiskDB.getRiskShortName();
	}

	private String GetAppntname(String no) {
		String name = "";
		String q_sql = "";
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		q_sql = "select name from lcgrpappnt where grpcontno='" + "?no?" + "'";
		sqlbv35.sql(q_sql);
		sqlbv35.put("no", no);
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv35);
		if (q_ssrs.MaxRow > 0) {
			name = q_ssrs.GetText(1, 1);
		}
		return name;
	}

	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			// throw new Exception("在取得LDCom的数据时发生错误");
		}
		return tLDComDB.getShortName();
	}

	private String getBankName(String strBankCode) throws Exception {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strBankCode);
		tLDCodeDB.setCodeType("bank");
		if (tLDCodeDB.getInfo()) {
			return tLDCodeDB.getCodeName();
		} else {
			return tLDCodeDB.getCodeName();
		}
	}

	private void UpdateData() {
		SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
		sqlbv36.sql("update LCGrpIssuePol set needprint='N' where grpcontno='"
				+ "?strPolNo?" + "' and issuetype not in ('99','15')");
		sqlbv36.put("strPolNo", strPolNo);
		map.put(sqlbv36, "UPDATE");
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");
		mInputData.clear();
		mInputData.add(map);
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);

		}

	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	private void jbInit() throws Exception {
	}

}
