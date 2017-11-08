package com.sinosoft.lis.sys;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
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
 * Title:
 * </p>
 * <p>
 * Description: 扫描补扫清单打印，模板为IssueDocQuery.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj 20060606
 * @version 1.0
 */

public class IssueDocQueryBL implements PrintService {
	private static Logger logger = Logger.getLogger(IssueDocQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 向后台提交,后面传输数据的容器
	private VData mInputData = new VData();
	// 返回前台的数据包,往界面传输数据的容器
	private VData mResult = new VData();
	private MMap mMMap = new MMap();
	private TransferData mTransferData = new TransferData();

	private String mStatiCode = ""; // 统计机构
	private String mMakeDate = ""; // 问题件发出日期
	private String mPromptOperator = ""; // 问题件发出人
	private String mBussType = ""; // 业务类型
	private String mSubType = ""; // 单证类型
	private String mBussNo = ""; // 单证号码
	private String mStatus = ""; // 处理状态（0已发出问题件，1已回复问题件）
	private String mScanNo = ""; // 扫描批次号
	private String mEndDate = ""; // 问题件发出止期
	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间

	public IssueDocQueryBL() {
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
		logger.debug("----------补扫清单IssueDocQueryBL打印测试开始----------");
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
		logger.debug("-----------补扫清单IssueDocQueryBL打印测试结束----------");
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
		mInputData = cInputData; // 得到外部传入的数据,将数据备份到本类中

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mMakeDate = (String) mTransferData.getValueByName("MakeDate"); // 问题件发出日期
		mPromptOperator = (String) mTransferData
				.getValueByName("PromptOperator"); // 问题件发出人
		mBussType = (String) mTransferData.getValueByName("BussType"); // 业务类型
		mSubType = (String) mTransferData.getValueByName("SubType"); // 单证类型
		mBussNo = (String) mTransferData.getValueByName("BussNo"); // 单证号码
		mStatus = (String) mTransferData.getValueByName("Status"); // 处理状态
		mScanNo = (String) mTransferData.getValueByName("ScanNo"); // 扫描批次号
		mEndDate = (String) mTransferData.getValueByName("EndDate");

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		// if(mStatiCode.equals("") && mMakeDate.equals("") &&
		// mPromptOperator.equals("")
		// && mBussType.equals("") && mSubType.equals("") && mBussNo.equals("")
		// && mStatus.equals("") && mScanNo.equals(""))
		// {
		// logger.debug("查询条件为空，请至少输入一个查询条件!");
		// CError tError = new CError();
		// tError.moduleName = "IssueDocQueryBL";
		// tError.functionName = "checkInputData";
		// tError.errorMessage = "没有要统计的数据!";
		// mErrors.addOneError(tError);
		// return false;
		// }

		if (mStatiCode.trim().equals("") || mStatiCode == null) {
			logger.debug("输入数据有误，管理机构不能为空!");
			CError tError = new CError();
			tError.moduleName = "IssueDocQueryBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "管理机构不能为空!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mMakeDate.equals("") || mMakeDate == null) {
			logger.debug("输入数据有误，问题件发出起止日期不能为空!");
			CError tError = new CError();
			tError.moduleName = "IssueDocQueryBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "问题件发出起止日期不能为空!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mEndDate.equals("") || mEndDate == null) {
			logger.debug("输入数据有误，问题件发出起止日期不能为空!");
			CError tError = new CError();
			tError.moduleName = "IssueDocQueryBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "问题件发出起止日期不能为空!";
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("IssueDocQuery.vts", ""); // 所用模板名称

		// 定义列表标题，共14列
		String[] Title = new String[14];
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
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		Title[13] = "";

		logger.debug("********************************************");
		logger.debug("---以下查询列表$*/IssueDoc/ROW/COL内容，并为列表赋值--");
		// 查询符合条件的补扫信息
		String tStrSQL = " select (select d.busstypename from es_doc_def d where c.busstype=d.busstype and c.subtype=d.subtype),"
				+ " a.subtype,(select d.subtypename from es_doc_def d where c.busstype=d.busstype and c.subtype=d.subtype),"
				+ " a.bussno,c.scanno,"
				+ " (select e.username from lduser e where trim(e.usercode)=a.promptoperator)," // a.promptoperator
				+ " a.makedate,a.issuedesc,"
				+ " (select e.username from lduser e where trim(e.usercode)=a.replyoperator)," // a.replyoperator
				+ " (case a.status when '0' then '已发出问题件' when '1' then '已回复问题件' end), "
				+ " (case a.result when 'y' then '问题件回复成功' else '问题件未回复' end ), "
				+ " a.modifydate,c.managecom, "
				+ " (select f.name from ldcom f where trim(f.comcode)=c.managecom ) "
				// +" c.docid,c.busstype "
				+ " from es_issuedoc a,es_doc_relation b,es_doc_main c "
				+ " where 1 = 1 "
				+ " and a.bussno = b.bussno "
				+ " and a.bussnotype = b.bussnotype "
				+ " and a.busstype = b.busstype "
				+ " and a.subtype = b.subtype "
				+ " and b.docid = c.docid "
				// + getWherePart('a.makedate','MakeDate')
				// + getWherePart('a.promptoperator','PromptOperator')
				// + getWherePart('c.busstype','BussType')
				// + getWherePart('c.subtype','SubType')
				// + getWherePart('a.bussno','BussNo')
				// + getWherePart('a.status','Status')
				// + getWherePart('c.scanno','ScanNo')
				+ " and a.makedate between '"
				+ "?mMakeDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " and c.managecom like concat('"
				+ "?mStatiCode?" + "','%') ";

		// //拼入问题件发出日前MakeDate条件
		// if(mMakeDate.equals("") || mMakeDate.equals(null))
		// {
		// tStrSQL = tStrSQL;
		// }
		// else
		// {
		// tStrSQL = tStrSQL +" and a.makedate='"+ mMakeDate +"' ";
		// }
		// 拼入问题件发出人PromptOperator条件
		if (mPromptOperator.equals("") || mPromptOperator.equals(null)) {
			tStrSQL = tStrSQL;
		} else {
			tStrSQL = tStrSQL + " and a.promptoperator='" + "?mPromptOperator?"
					+ "' ";
		}

		// 拼入业务类型BussType条件
		if (mBussType.equals("") || mBussType.equals(null)) {
			tStrSQL = tStrSQL;
		} else {
			tStrSQL = tStrSQL + " and c.busstype='" + "?mBussType?" + "' ";
		}

		// 拼入单证类型SubType条件
		if (mSubType.equals("") || mSubType.equals(null)) {
			tStrSQL = tStrSQL;
		} else {
			tStrSQL = tStrSQL + " and c.subtype='" + "?mSubType?" + "' ";
		}
		// 拼入单证号码BussNo条件
		if (mBussNo.equals("") || mBussNo.equals(null)) {
			tStrSQL = tStrSQL;
		} else {
			tStrSQL = tStrSQL + " and a.bussno='" + "?mBussNo?" + "' ";
		}
		// 拼入处理状态Status条件
		if (mStatus.equals("") || mStatus.equals(null)) {
			tStrSQL = tStrSQL;
		} else {
			tStrSQL = tStrSQL + " and a.status='" + "?mStatus?" + "' ";
		}
		// 拼入扫描批次号ScanNo条件
		if (mScanNo.equals("") || mScanNo.equals(null)) {
			tStrSQL = tStrSQL;
		} else {
			tStrSQL = tStrSQL + " and c.scanno='" + "?mScanNo?" + "' ";
		}

		// 拼入排序条件
		tStrSQL = tStrSQL + " order by a.busstype,a.subtype ";
		logger.debug("----------查询补扫信息语句为-----------" + tStrSQL);

		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStrSQL);
		sqlbv1.put("mMakeDate", mMakeDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mStatiCode", mStatiCode);
		sqlbv1.put("mPromptOperator", mPromptOperator);
		sqlbv1.put("mBussType", mBussType);
		sqlbv1.put("mSubType", mSubType);
		sqlbv1.put("mBussNo", mBussNo);
		sqlbv1.put("mStatus", mStatus);
		sqlbv1.put("mScanNo", mScanNo);

		tSSRS = cusExeSQL.execSQL(sqlbv1);

		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("IssueDoc");

		int tStatCount = 0; // 用来统计符合条件的记录的数量
		if (tSSRS.getMaxRow() != 0) {
			// 循环处理每一条打印记录
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				logger.debug("-------------第 " + i + " 次循环开始----------------");
				String tBussTypeName = tSSRS.GetText(i, 1); // 业务类型名称
				String tSubType = tSSRS.GetText(i, 2); // 单证类型
				String tSubTypeName = tSSRS.GetText(i, 3); // 单证类型名称
				String tBussNo = tSSRS.GetText(i, 4); // 单证号码
				String tScanNo = tSSRS.GetText(i, 5); // 扫描批次号
				String tPromptOperator = tSSRS.GetText(i, 6); // 问题件发出人
				String tMakedata = tSSRS.GetText(i, 7); // 问题件发出日期
				String tIssueDesc = tSSRS.GetText(i, 8); // 问题描述
				String tReplyOperator = tSSRS.GetText(i, 9); // 问题件回复人
				String tStatus = tSSRS.GetText(i, 10); // 处理状态
				String tResult = tSSRS.GetText(i, 11); // 处理结果
				String tModifydate = tSSRS.GetText(i, 12); // 修改日期
				String tMngCode = tSSRS.GetText(i, 13); // 管理机构代码
				String tMngName = tSSRS.GetText(i, 14); // 管理机构名称

				// 为模板的每一列赋值
				String[] stra = new String[24];
				stra[0] = tBussTypeName;
				stra[1] = tSubType;
				stra[2] = tSubTypeName;
				stra[3] = tBussNo;
				stra[4] = tScanNo;
				stra[5] = tPromptOperator;
				stra[6] = tMakedata;
				stra[7] = tIssueDesc;
				stra[8] = tReplyOperator;
				stra[9] = tStatus;
				stra[10] = tResult;
				stra[11] = tModifydate;
				stra[12] = tMngCode;
				stra[13] = tMngName;

				tListTable.add(stra);
				tStatCount = tStatCount + 1; // 记录数量加1
				logger.debug("-------------第 " + i + " 次循环结束----------------");
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "IssueDocQueryBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计时间,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 问题件发出人
		String tPromptOperator = "";
		if (mPromptOperator.equals("") || mPromptOperator.equals(null)) {
			tPromptOperator = "";
		} else {
			String tSQL1 = " select e.username from lduser e where 1=1 "
					+ " and trim(e.usercode)='" + "?mPromptOperator?" + "' ";
			ExeSQL cusExeSQL1 = new ExeSQL();
			SSRS tSSRS1 = new SSRS();
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL1);
			sqlbv2.put("mPromptOperator", mPromptOperator);
			tSSRS1 = cusExeSQL1.execSQL(sqlbv2);
			tPromptOperator = tSSRS1.GetText(1, 1);
		}
		// 问题件发出日期
		// String tMakeData = "";
		// if(mMakeDate.equals("") || mMakeDate.equals(null))
		// {
		// tMakeData = "";
		// }
		// else
		// {
		// tMakeData = mMakeDate;
		// }
		String tMakeData = mMakeDate;
		String tEndDate = mEndDate;

		// 业务类型
		String tBussTypeName = "";
		if (mBussType.equals("TB")) {
			tBussTypeName = "承保业务";
		} else if (mBussType.equals("BQ")) {
			tBussTypeName = "保全业务";
		} else if (mBussType.equals("LP")) {
			tBussTypeName = "理赔业务";
		} else {
			tBussTypeName = "";
		}
		// 单证类型
		String tSubTypeName = "";
		if (mSubType.equals("") || mSubType.equals(null)) {
			tSubTypeName = "";
		} else {
			String tSQL2 = " select d.subtypename from es_doc_def d where 1=1 "
					+ " and trim(d.subtype)='" + "?sqlbv3?" + "' ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL2);
			sqlbv3.put("mSubType", mSubType);
			ExeSQL cusExeSQL2 = new ExeSQL();
			SSRS tSSRS2 = new SSRS();
			tSSRS2 = cusExeSQL2.execSQL(sqlbv3);
			// tongmeng 2007-08-29 modify
			tSubTypeName = mSubType + "-" + tSSRS2.GetText(1, 1);
			logger.debug("tSubTypeName==" + tSubTypeName);
		}

		// 单证号码
		String tBussNo = "";
		if (mBussNo.equals("") || mBussNo.equals(null)) {
			tBussNo = "";
		} else {
			tBussNo = mBussNo;
		}
		// 处理状态
		String tStatus = "";
		if (mStatus.equals("0")) {
			tStatus = "已发出问题件";
		} else if (mStatus.equals("1")) {
			tStatus = "已回复问题件";
		} else {
			tStatus = "";
		}
		// 扫描批次号
		String tScanNo = "";
		if (mScanNo.equals("") || mScanNo.equals(null)) {
			tScanNo = "";
		} else {
			tScanNo = mScanNo;
		}

		// 把这些变量的值输入到模板中
		tTextTag.add("StatDate", SysDate); // 统计日期：$=/StatDate$
		tTextTag.add("StatiCode", tMngCom); // 统计机构：$=/StatiCode$
		tTextTag.add("PromptOperator", tPromptOperator); // 问题件发出人：$=/PromptOperator$
		tTextTag.add("MakeDate", tMakeData); // 问题件发出日期：$=/MakeDate$
		tTextTag.add("EndDate", tEndDate);
		tTextTag.add("BussType", tBussTypeName); // 业务类型：$=/BussType$
		tTextTag.add("SubType", tSubTypeName); // 单证类型：$=/SubType$
		tTextTag.add("BussNo", tBussNo); // 单证号码：$=/BussNo$
		tTextTag.add("Status", tStatus); // 处理状态：$=/Status$
		tTextTag.add("ScanNo", tScanNo); // 扫描批次号：$=/ScanNo$
		tTextTag.add("Count", tStatCount); // 数据量：$=/Count$

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

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
		tG.ComCode = "862100";
		tG.ManageCom = "862100";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "862100"); // 统计机构
		tTransferData.setNameAndValue("MakeDate", "");
		tTransferData.setNameAndValue("PromptOperator", "001");
		tTransferData.setNameAndValue("BussType", "TB");
		tTransferData.setNameAndValue("SubType", "UA001");
		tTransferData.setNameAndValue("BussNo", "");
		tTransferData.setNameAndValue("Status", "");
		tTransferData.setNameAndValue("ScanNo", "");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		IssueDocQueryBL tIssueDocQueryBL = new IssueDocQueryBL();
		if (tIssueDocQueryBL.submitData(tVData, "") == false) {
			logger.debug("----------清单打印：补扫清单出错---------------");
		}
	}
}
