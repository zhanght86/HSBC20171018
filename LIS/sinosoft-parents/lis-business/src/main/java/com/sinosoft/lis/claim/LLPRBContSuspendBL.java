package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContHangUpStateDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LLCaseSet;
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
 * Title:
 * </p>
 * <p>
 * Description: 理赔保单挂起清单打印，模板为LLPRBContSuspend.vts
 * </p>
 * 统计界面：统计机构（某分公司、某中支、某支公司，默认为操作用户所在的权限机构面）、统计时间段 表头：报表名称、统计条件、统计日期
 * 数据项：赔案号、保单号,案件类型、出险人、事故日期和出险日期、案件的状态、立案时间、保单挂起时间、保单挂起项目 算法：统计口径选项（保单挂起时间）
 * 排序：保单挂起时间、赔案号 表尾：统计数量
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj 20050831
 * @version 1.0 Modify by ZHaorx 2005-10-30
 */

public class LLPRBContSuspendBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBContSuspendBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 向后台提交,后面传输数据的容器
	private VData mInputData = new VData();
	// 返回前台的数据包,往界面传输数据的容器
	private VData mResult = new VData();
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mComCode = ""; // 统计机构
	// private String mStartDate = ""; //开始日期
	// private String mEndDate = ""; //结束日期
	private String mNCLType = ""; // 申请类型

	public LLPRBContSuspendBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------保单挂起清单LLPRBContSuspend.vts打印测试开始----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		// 准备输出数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("-----------保单挂起清单LLPRBContSuspend.vts打印测试结束----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// mStartDate = (String) mTransferData.getValueByName("StartDate");
		// //开始日期
		// mEndDate = (String) mTransferData.getValueByName("EndDate"); //结束日期
		mComCode = (String) mTransferData.getValueByName("ComCode"); // 统计机构
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRBContSuspend.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		// 定义列表标题，共10列
		String[] Title = new String[10];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/HANGUP/ROW/COL内容，并为列表赋值--");

		// 在LCContHangUpState表中,standflag3字段用来存放赔案号,保单挂起时间用modifydate字段,contno为保单号
		// 一个赔案下可能会有好几个保单,故取max(modifydate)
		// String mClmNoSQL="select standflag3,max(modifydate),contno from
		// LCContHangUpState where 1=1"
		// +" and (standflag3 in( select RgtNo from llregister where
		// mngcom='"+mComCode+"')"
		// //报案阶段也有保单挂起,下面所有的查询都应该有这两中情况
		// +" OR standflag3 in( select rptNo from llreport where
		// mngcom='"+mComCode+"'))"
		// +" and (modifydate between to_date('"+mStartDate+"','yyyy-mm-dd') and
		// to_date('"+mEndDate+"','yyyy-mm-dd'))"
		// +" group by standflag3,contno" //因为取最大保单挂起时间,所以要排序
		// +" order by max(modifydate),standflag3";
		String applType = mNCLType.trim().equals("1") ? " and rgtobj='1' "
				: " and rgtobj='2' ";

		String mClmNoSQL = " select hangupno,max(modifydate),contno from LCContHangUpState  where 1=1"
				// 承保挂起,保全挂起,理赔挂起,渠道挂起,续期挂起
				+ " and ( posflag='1' or agentflag='1' or rnflag='1') "// or
																		// claimflag='1'理赔挂起
																		// or
																		// nbflag='1'承保挂起
				// Modify by zhaorx 2006-03-22
				// hanguptype='4'时HangUpNo为赔案号(HangUpType=1：新契约2：保全3：续期4：理赔5：渠道)
				+ " and hanguptype='4' "
				// 立案阶段和审核阶段的保单挂起
				// + " and (hangupno in( select RgtNo from llregister where
				// mngcom like '"+mComCode+"%')"
				+ " and (hangupno in( select RgtNo from llregister where mngcom like concat('"
				+ "?comcode?"
				+ "','%')"
				+ applType
				+ ")"
				// 报案阶段的保单挂起(赔案号在llreport中,而不在llregister中)
				// + " OR hangupno in( select rptNo from llreport where mngcom
				// like '"+mComCode+"%'"
				+ " OR hangupno in( select rptNo from llreport where mngcom like concat('"
				+ "?comcode?"
				+ "','%')"
				+ applType
				+ " and rptno not in(  select RgtNo from llregister )))"
				// + " and (modifydate between
				// to_date('"+mStartDate+"','yyyy-mm-dd') and
				// to_date('"+mEndDate+"','yyyy-mm-dd'))"
				+ " group by hangupno,contno" // 因为取最大保单挂起时间,所以要排序
				+ " order by max(modifydate),hangupno";

		logger.debug("----------查询赔案号语句为-----------" + "\n" + mClmNoSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mClmNoSQL);
		sqlbv.put("comcode", mComCode);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("HANGUP"); // 这个名称要根据模板而定

		int tStatCount = 0; // 用来统计符合条件的记录的数量

		// 循环处理每一条打印记录
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			logger.debug("-------------第 " + i + " 次循环开始----------------");
			String tClmNo = tSSRS.GetText(i, 1); // 赔案号
			String tcontno = tSSRS.GetText(i, 3); // 保单号,一个赔案下可能会有好几个合同

			// 下面根据赔案号,去查询模板中的别的字段
			LLRegisterSchema tLLRegisteSchema = new LLRegisterSchema();
			tLLRegisteSchema = tLLPRTPubFunBL.getLLRegister(tClmNo);
			LLReportSchema tLLReportSchema = new LLReportSchema();
			tLLReportSchema = tLLPRTPubFunBL.getLLReport(tClmNo);

			// 用保单号去查询
			LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
			LCContHangUpStateSet tLCContHangUpStateSet = new LCContHangUpStateSet();
			tLCContHangUpStateDB.setContNo(tcontno);
			tLCContHangUpStateSet.set(tLCContHangUpStateDB.query());
			// 先查询保单挂起项目,用保单号ContNo去查询时记录不唯一
			for (int j = 1; j <= tLCContHangUpStateSet.size(); j++) {
				LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
				tLCContHangUpStateSchema = tLCContHangUpStateSet.get(j);
				// String tNBFlag = tLCContHangUpStateSchema.getNBFlag(); //承保挂起
				String tPosFlag = tLCContHangUpStateSchema.getPosFlag(); // 保全挂起
				// String tClaimFlag = tLCContHangUpStateSchema.getClaimFlag();
				// //理赔挂起
				String tAgentFlag = tLCContHangUpStateSchema.getAgentFlag(); // 渠道挂起
				String tRNFlag = tLCContHangUpStateSchema.getRNFlag(); // 续期挂起
				// 保单挂起项目
				String tHangUpState = "";
				// if (tNBFlag != null && tNBFlag.equals("1"))//没有承包挂起
				// {
				// tHangUpState = "承保挂起";
				// }
				if (tPosFlag != null && tPosFlag.equals("1")) {
					tHangUpState = tHangUpState + " " + "保全挂起";
				}
				// if (tClaimFlag!=null && tClaimFlag.equals("1"))
				// {
				// tHangUpState = tHangUpState + " " + "理赔挂起";
				// }
				if (tAgentFlag != null && tAgentFlag.equals("1")) {
					tHangUpState = tHangUpState + " " + "渠道挂起";
				}
				if (tRNFlag != null && tRNFlag.equals("1")) {
					tHangUpState = tHangUpState + " " + "续期挂起";
				}

				// 机构代码和名称
				String tMngCom = tLLRegisteSchema.getMngCom();
				if (tMngCom == null || tMngCom.equals("")) {
					tMngCom = tLLReportSchema.getMngCom();
				}
				LDComSchema tLDComSchema = new LDComSchema();
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(tMngCom);
				tLDComDB.getInfo();
				tLDComSchema = tLDComDB.getSchema();
				String tMngComName = tLDComSchema.getName();

				// 案件类型
				String ttRgtState = tLLRegisteSchema.getRgtState();
				String tRgtState = tLLPRTPubFunBL.getLDCode("llrgttype",
						ttRgtState);

				// 案件状态,用赔案状态
				String ttClmState = tLLRegisteSchema.getClmState();
				String tClmState = "";
				if (ttClmState == null || ttClmState == "") // 报案阶段也有保单挂起
				{
					tClmState = "报案";
				} else {
					tClmState = tLLPRTPubFunBL.getLDCode("llclaimstate",
							ttClmState);
				}

				// 立案时间
				String tRgtDate = tLLRegisteSchema.getRgtDate();

				// 保单挂起时间
				String tmodifyDate = tSSRS.GetText(i, 2);

				// 事故日期
				LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
				tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(tClmNo);
				String tAccDate = tLLAccidentSchema.getAccDate();

				// 出险日期,用出险结束日期代替
				String tAccidentDate = "";
				tAccidentDate = tLLRegisteSchema.getAccidentDate();
				if (tAccidentDate == null)// 处于报案状态
				{
					String tAccidentDateSQL = " select llsubreport.accdate from llsubreport where llsubreport.subrptno='"
							+ "?clmno?" + "' ";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tAccidentDateSQL);
					sqlbv1.put("clmno", tClmNo);
					ExeSQL tExeAccidentDateSQL = new ExeSQL();
					tAccidentDate = tExeAccidentDateSQL
							.getOneValue(sqlbv1);
					tAccidentDate = tAccidentDate.substring(0, 10);
				}

				// 出险人
				String tCustomerName = "";
				LLCaseDB tLLCaseDB = new LLCaseDB();
				tLLCaseDB.setCaseNo(tClmNo);
				LLCaseSet tLLCaseSet = new LLCaseSet();
				tLLCaseSet = tLLCaseDB.query();
				for (int k = 1; k <= tLLCaseSet.size(); k++) {
					String tSql = " select a.name from ldperson a where a.customerno = '"
							+ "?customerno?" + "'";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSql);
					sqlbv2.put("customerno", tLLCaseSet.get(k).getCustomerNo());
					ExeSQL tExeSQL = new ExeSQL();
					String ttCustomerName = tExeSQL.getOneValue(sqlbv2);
					if (k == 1) {
						tCustomerName = ttCustomerName;
					} else { // 如果出险人不唯一
						tCustomerName = tCustomerName + " " + ttCustomerName;
					}
				}
				if (tCustomerName.equals("") || tCustomerName == null) {
					String tNameSQL = " select a.name from ldperson a where a.customerno in "
							+ " (select llsubreport.customerno from llsubreport "
							+ " where llsubreport.subrptno = '" + "?clmno?" + "')";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(tNameSQL);
					sqlbv3.put("clmno", tClmNo);
					ExeSQL tExeNameSQL = new ExeSQL();
					SSRS tSSSR = new SSRS();
					tSSSR = tExeNameSQL.execSQL(sqlbv3);
					String tNAME = tExeNameSQL.getOneValue(sqlbv3);
					for (int m = 1; m <= tSSSR.getMaxRow(); m++) {
						if (tSSSR.getMaxRow() == 1) {
							tCustomerName = tNAME;
						} else {
							tCustomerName = tCustomerName + " " + tNAME;
						}
					}
				}

				// 为模板的每一列赋值
				String[] stra = new String[12];
				stra[0] = tClmNo; // 赔案号
				stra[1] = tcontno; // 保单号
				stra[2] = tRgtState; // 案件类型
				stra[3] = tCustomerName; // 出险人
				stra[4] = tAccDate; // 事故日期
				stra[5] = tAccidentDate; // 出险日期
				stra[6] = tClmState; // 案件状态
				stra[7] = tRgtDate; // 立案时间
				stra[8] = tmodifyDate; // 保单挂起时间
				stra[9] = tHangUpState; // 保单挂起项目
				stra[10] = tMngCom; // 机构代码
				stra[11] = tMngComName; // 机构名称
				tListTable.add(stra);
				tStatCount = tStatCount + 1; // 记录数量加1
				logger.debug("-------------第 " + i
						+ " 次循环结束----------------");
			}
		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计时间,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mComCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型

		String tStatCondition = "统计机构:" + tMngCom + "   申请类型：" + tApplTypeName;// 统计时间段:"+mStartDate+"至"+mEndDate+"";
																				// //统计条件
		tTextTag.add("StatCondition", tStatCondition); // 统计条件：$/=StatCondition$
		tTextTag.add("StatDate", SysDate); // 统计日期：$=/StatDate$
		tTextTag.add("StatCount", tStatCount); // 统计数量：$=/StatCount$

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath = " E:/lis/ui/f1print/NCLtemplate/ ";
		// String strVFPathName = strTemplatePath + " AAAtest.vts "; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);
		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	// test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ComCode", "86");// 统计机构
		// tTransferData.setNameAndValue("StartDate","2005-10-28" );//开始日期
		// tTransferData.setNameAndValue("EndDate","2005-10-28"); //结束日期

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBContSuspendBL tLLPRBContSuspendBL = new LLPRBContSuspendBL();
		if (tLLPRBContSuspendBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：保单挂起清单出错---------------");
		}
	}
}
