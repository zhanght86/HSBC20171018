package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 清单打印：未结案件清单--LLPRBNoEndCase.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaoy，2005-9-2 17:28
 * @version 1.0
 */
public class LLPRBNoEndCaseBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBNoEndCaseBL.class);

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

	// private String mPrtCode = "PCT005" ; //打印编码
	private String strMngCom = "";
	private String strOverTime = "";
	private String strWorkPool = "";
	private String mNCLType = "";// 申请类型

	public LLPRBNoEndCaseBL() {
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

		if (!dealData()) {
			return false;
		}
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
		this.mInputData = cInputData;

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.strMngCom = (String) mTransferData.getValueByName("strMngCom");
		this.strOverTime = (String) mTransferData.getValueByName("strOverTime");
		this.strWorkPool = (String) mTransferData.getValueByName("strWorkPool");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType");
		logger.debug("统计口径" + strWorkPool);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		TextTag texttag = new TextTag();
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LLPRBNoEndCase.vts", ""); // appoint to a
															// special output
															// .vts file
		ListTable tlistTable = new ListTable();
		tlistTable.setName("UNSOCA");
		// 申请类型判断
		String tApplType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where trim(llregister.rgtno)=a.missionprop1 and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where trim(llregister.rgtno)=a.missionprop1 and llregister.rgtobj='2') ";

		double sumMoney = 0;
		int sumcount = 0;
		String strSql = "";
		String ClmState = "";
		String ComName = "";
		// 统计口径为立案时的判断
		String mWorkPool = "";
		if (mNCLType.equals("2") && strWorkPool.equals("0000005015")) {// 申请类型为团体，统计口径为立案时特殊处理
			mWorkPool = "0000005215";
		} else {
			mWorkPool = strWorkPool;
		}
		strSql = " select a.MissionProp1,a.MissionProp4,a.MissionProp6,a.MissionProp2,a.MakeDate,a.DefaultOperator,a.MissionProp3,a.MissionProp20 "
				+ " from lwmission a where a.activityid = '"
				+ mWorkPool
				+ "'"
				+ " and a.MissionProp20 like concat('"
				+ "?mngcom?"
				+ "','%')"
				+ tApplType
				+ " and datediff(to_date('"
				+ "?curdate?"
				+ "','YYYY-MM-DD'),a.MakeDate)>= to_number('"
				+ "?overtime?"
				+ "')" + " order by a.MakeDate,a.MissionProp1";

		logger.debug("开始执行sql操作:" + strSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("mngcom", strMngCom);
		sqlbv.put("curdate", CurrentDate);
		sqlbv.put("overtime", strOverTime);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv);

		if (ssrs.getMaxRow() != 0) {
			sumcount = ssrs.getMaxRow();
			for (int index = 1; index <= ssrs.getMaxRow(); index++) {
				String[] cols = new String[16];
				String ClmNo = ssrs.GetText(index, 1);
				String CusNo = ssrs.GetText(index, 7);
				logger.debug("ClmNo" + ClmNo);
				logger.debug("CusNo" + CusNo);

				LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
				LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
				tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(ClmNo);

				LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
				tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(ClmNo);

				String tRgtDate = tLLRegisterSchema.getRgtDate();// 立案时间
				String tOperator = tLLRegisterSchema.getOperator();// 立案人代码
				String tAuditDate = tLLClaimUWMainSchema.getAuditDate();// 审核时间
				String tAuditPer = tLLClaimUWMainSchema.getAuditPer();// 审核人代码
				String tExamDate = tLLClaimUWMainSchema.getExamDate();// 审批时间
				String tExamPer = tLLClaimUWMainSchema.getExamPer();// 审批人代码

				LLClaimUserSchema tLLClaimUserOp = new LLClaimUserSchema();// 将立案人，审核人和审批人代码汉化
				tLLClaimUserOp = tLLPRTPubFunBL.getLLClaimUser(tOperator);
				String UserNameOp = tLLClaimUserOp.getUserName();// 立案人名称
				LLClaimUserSchema tLLClaimUserAu = new LLClaimUserSchema();
				tLLClaimUserAu = tLLPRTPubFunBL.getLLClaimUser(tAuditPer);
				String UserNameAu = tLLClaimUserAu.getUserName();// 审核人名字
				LLClaimUserSchema tLLClaimUserEx = new LLClaimUserSchema();
				tLLClaimUserEx = tLLPRTPubFunBL.getLLClaimUser(tExamPer);
				String UserNameEx = tLLClaimUserEx.getUserName();// 审批人名字

				// 案件状态
				String tClmState = tLLRegisterSchema.getClmState();
				if (tClmState == null || tClmState.equals("")) {
					ClmState = "待立案";
				} else {
					switch (Integer.parseInt(tClmState)) {
					case 20:
						ClmState = "立案";
						break;// 20立案
					case 30:
						ClmState = "审核";
						break;// 30审核
					case 40:
						ClmState = "审批";
						break;// 40审批
					case 50:
						ClmState = "结案";
						break;// 50结案
					case 60:
						ClmState = "完成";
						break;// 60完成
					default:
						ClmState = "";
						break;
					}
				}
				if (strWorkPool.equals("0000005035")
						&& (tAuditDate == null || tAuditDate.equals(""))) {
					ClmState = "待审核";
				}
				if (strWorkPool.equals("0000005055")
						&& (tExamDate == null || tAuditDate.equals(""))) {
					ClmState = "待审批";
				}
				// 取得意外事故发生日期
				strSql = " select a.AccDate from LLAccident a,LLCaseRela b where a.AccNo=b.CaseRelaNo and b.CaseNo='"
						+ "?clmno?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("clmno", ClmNo);
				String AccDate = exeSQL.getOneValue(sqlbv1);
				AccDate = AccDate.substring(0, 10);
				logger.debug("意外事故发生日期" + AccDate);
				// 取得报案人和联系方式
				String tSQLRptor = " select rptorname,rptorphone from llreport where RptNo = '"
						+ "?clmno?" + "' and RgtFlag != '30' ";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSQLRptor);
				sqlbv2.put("clmno", ClmNo);
				SSRS tRptorSSRS = new SSRS();
				tRptorSSRS = exeSQL.execSQL(sqlbv2);
				String RptorName = "";
				String RptorPhone = "";
				if (tRptorSSRS.getMaxRow() > 0) {
					RptorName = tRptorSSRS.GetText(1, 1);
					RptorPhone = tRptorSSRS.GetText(1, 2);
				}

				// 取得金额
				LLClaimDB tLLClaimDB = new LLClaimDB();
				tLLClaimDB.setClmNo(ClmNo);
				double StandPay = 0;
				double RealPay = 0;
				double pay = 0;
				if (tLLClaimDB.getInfo()) {
					StandPay = tLLClaimDB.getStandPay();
					RealPay = tLLClaimDB.getRealPay();
				}
				// 数据存储
				cols[0] = ClmNo; // 赔案号
				cols[1] = ssrs.GetText(index, 2); // 出险人
				cols[2] = RptorName; // 报案人
				cols[3] = RptorPhone; // 联系方式
				cols[4] = AccDate; // 意外事故发生日期
				cols[5] = ssrs.GetText(index, 3); // 出险日期
				cols[6] = ClmState; // 案件状态
				cols[7] = tRgtDate; // 立案时间
				cols[8] = UserNameOp; // 立案人

				if (strWorkPool.trim().equals("0000005055")
						|| strWorkPool.trim().equals("0000005075")) {
					pay = RealPay; // 核赔金额
				} else {
					pay = StandPay; // 预估金额
				}
				sumMoney = sumMoney + pay;
				cols[9] = Double.toString(pay); // 金额
				String tMngCom = ssrs.GetText(index, 8);// 机构代码
				LDComSchema tLDComSchema = new LDComSchema();
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(tMngCom);
				tLDComDB.getInfo();
				tLDComSchema = tLDComDB.getSchema();
				String tMngComName = tLDComSchema.getName();// 机构名称
				cols[10] = tMngCom; // 机构代码
				cols[11] = tMngComName; // 机构名称
				cols[12] = tAuditDate; // 审核时间
				cols[13] = UserNameAu; // 审核人
				cols[14] = tExamDate; // 审批时间
				cols[15] = UserNameEx; // 审批人
				tlistTable.add(cols);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBNoEndCaseBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		String[] col = new String[1];
		col[0] = "";

		xmlexport.addListTable(tlistTable, col);
		// 统计条件
		// 取得机构名称
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strMngCom);
		if (tLDComDB.getInfo()) {
			ComName = tLDComDB.getName();
		}
		// 申请类型: $=/CSApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		String StatCondition = "统计机构：" + ComName + " 统计口径选项：" + ClmState
				+ " 统计时长：" + strOverTime + "天" + " 申请类型：" + mNCLTypeName;
		texttag.add("StatCondition", StatCondition);
		// 统计日期
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		texttag.add("StatDate", SysDate);
		texttag.add("StatAmount", sumcount); // 统计数量
		texttag.add("StatMoney", sumMoney); // 统计金额

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("D:\\", "test11"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("strMngCom", "86");
		tTransferData.setNameAndValue("strOverTime", "5");
		tTransferData.setNameAndValue("strWorkPool", "0000005015");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRBNoEndCaseBL tLLPRBNoEndCaseBL = new LLPRBNoEndCaseBL();
		if (tLLPRBNoEndCaseBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRBNoEndCaseBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRBNoEndCaseBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
