package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: 收费员管理-->综合打印-->续期保费实收清单
 * </p>
 * <p>
 * Copyright: Copyright (＠) 2007 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * Description: 续期保费实收清单打印后台类
 * </p>
 * 
 * @author
 * @Rewrite: liuxiaosong<liuxs@sinosoft.com.cn>
 * @date: 2007-02-02
 * @version: 1.1
 */

public class XQInComeFeeDailyCheckQueryBL implements PrintService {
private static Logger logger = Logger.getLogger(XQInComeFeeDailyCheckQueryBL.class);
	// 辅助变量-------------------------------------------------------------------
	public CErrors mErrors = new CErrors(); // 错误处理
	private VData mInputData = new VData(); // 输入数据容器
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mResult = new VData(); // 输出数据容器
	// --------------------------------------------------------------------------
	// 业务变量-------------------------------------------------------------------
	private String mManageCom = ""; // 统计机构
	private String mStartDate = ""; // 开始时间
	private String mEndDate = ""; // 结束时间
	private String mSaleChnl = ""; // 渠道
	private String mIsToll = ""; // 保单类型 1孤儿单 0在职单
	private String mAgentCode = ""; // 业务员编码

	// --------------------------------------------------------------------------

	public XQInComeFeeDailyCheckQueryBL() {
	}

	/***************************************************************************
	 * 本类入口方法，供高层调用
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 **************************************************************************/
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate))
			return false;
		if (!checkInputData())
			return false;
		if (!dealData())
			return false;
		return true;
	}

	/***************************************************************************
	 * 获取传入数据
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 **************************************************************************/
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mManageCom = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始时间
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束时间
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl"); // 渠道
		mIsToll = (String) mTransferData.getValueByName("Bdlx");// 保单类型 1孤儿单
																// 2在职单
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");// 业务员编码
		return true;
	}

	/**
	 * 数据完整性、合法性校验:检验必录项是否非空
	 */
	private boolean checkInputData() {
		if ("".equals(mManageCom) || "".equals(mStartDate)
				|| "".equals(mEndDate)) {
			buildError("checkInputData", "输入数据不完整");
			return false;
		}
		return true;
	}

	/**
	 * 本类核心业务逻辑方法
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument("XQInComeFeeDailyCheckQuery.vts", "");
		TextTag tTextTag = new TextTag();
		ListTable tListTable = new ListTable();
		tListTable.setName("XBSR");
		String[] Title = { "机构代码", "投保人", "保费应收日", "保费实收日", "保单号", "交费次数",
				"交费形式", "孤儿单标记", "保费合计", "业务员姓名", "机构名称" };
		String[] tResult = null;// 查询结果

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		ExeSQL tVicExeSQL = new ExeSQL();
		SSRS tVicSSRS = new SSRS();
		/**
		 * 为了提高程序效率，分两级查询，主表为ljapayperson，而后以contno查询其他信息 目前数量级:ljapayperson
		 * 和lacommision 均为 2000万以上,
		 */
		String tMainSQL = "";// 查询主SQL
		String tOtherInfoSQL = "";// 其他辅助信息查询
		// 拼主SQL---------------------------------------------------------------
		tMainSQL = "select substr(a.managecom, 1, 6), a.lastpaytodate,"// 1管理机构编码
																		// 2保费应收日
				+ " a.enteraccdate, a.contno, a.paycount, sum(a.sumactupaymoney)"// 3保费实收日
																					// 4保单号
																					// 5交费次数
																					// 8保费合计
				+ " from ljapayperson a where 1=1"
				+ " and a.managecom like concat('"
				+ "?mManageCom?" + "','%')"
				+ " and a.managecom like concat('"
				+ "?mGlobalInput?" + "','%')"
				+ " and a.agentcom is null"
				+ " and a.paycount >= '2'"
				+ " and a.paytype = 'ZC'"
				+ " and a.confdate between '" + "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?" + "'";

		// 对可选条件进行组织
		if (mSaleChnl != null && !"".equals(mSaleChnl)
				&& (mAgentCode == null || "".equals(mAgentCode))) {
			tMainSQL += " and exists (select contno from lccont where salechnl='"
					+ "?mSaleChnl?" + "')";// 销售渠道
		}
		if (mAgentCode != null && !"".equals(mAgentCode)
				&& (mSaleChnl == null || "".equals(mSaleChnl))) {
			tMainSQL += " and exists (select contno from lccont where agentcode='"
					+ "?mAgentCode?" + "')";// 业务员编码
		}
		if (mAgentCode != null && mSaleChnl != null && !"".equals(mSaleChnl)
				&& !"".equals(mAgentCode)) {
			tMainSQL += " and exists (select contno from lccont where agentcode='"
					+ "?mAgentCode?" + "' and salechnl='" + "?mSaleChnl?" + "')";
		}
		tMainSQL += " and exists (select 1 from ljapay where othernotype in ('2','3','8') and payno=a.payno)";

		if (mIsToll != null && !"".equals(mIsToll)) {
			tMainSQL += " and exists (select grpcontno from lacommisiondetail where poltype='"
					+"?mIsToll?"+ "')";// 孤儿单或非孤儿单
		}

		tMainSQL += " group by substr(a.managecom, 1, 6), a.lastpaytodate, a.enteraccdate, a.contno, a.paycount";
		// ----------------------------------------------------------------------

		// 其他信息查询SQL---------------------------------------------------------
		tOtherInfoSQL = "select "
				+ " (select codename from ldcode where codetype='paymode' and code = a.paymode),"// 6交费形式
				+ " (select appntname from lcappnt where contno=a.contno and appntno=a.appntno),"// 1投保人
				+ " (select name from laagent where agentcode=trim(a.agentcode)),"// 9业务员姓名
				+ " (select name from ldcom d where d.comcode = a.managecom) "// 10机构
				+ " from (select contno,paymode,appntno,agentcode,managecom from lccont where ";
		String tOtherInfoSQL2 = "" + tOtherInfoSQL;
		// ---------------------------------------------------------------------

		// 查询数据并装载打印容器---------------------------------------------------
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tMainSQL);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mGlobalInput", mGlobalInput.ManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mSaleChnl", mSaleChnl);
		sqlbv1.put("mAgentCode", mAgentCode);
		sqlbv1.put("mIsToll", mIsToll);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		int tMaxRow = tSSRS.getMaxRow();
		if (tMaxRow < 1) {
			buildError("dealData", "没有相关记录!");
			return false;
		}
		for (int i = 1; i <= tMaxRow; i++) {
			// 组织主要数据
			tResult = new String[11];
			tResult[0] = tSSRS.GetText(1, 1);
			tResult[2] = tSSRS.GetText(i, 2);
			tResult[3] = tSSRS.GetText(i, 3);
			tResult[4] = tSSRS.GetText(i, 4);
			tResult[5] = tSSRS.GetText(i, 5);
			tResult[8] = tSSRS.GetText(i, 6);

			tResult[7] = "在职单";
			if ("1".equals(mIsToll)) {
				tResult[7] = "孤儿单";
			}

			// 二次查询辅助信息
			try {
				tOtherInfoSQL2 += " contno='" + "?var?" + "') a"; // 保单号
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tOtherInfoSQL2);
				sqlbv2.put("var", tResult[4]);
				tVicSSRS = tVicExeSQL.execSQL(sqlbv2);
				tResult[6] = tVicSSRS.GetText(1, 1);
				tResult[1] = tVicSSRS.GetText(1, 2);
				tResult[9] = tVicSSRS.GetText(1, 3);
				tResult[10] = tVicSSRS.GetText(1, 4);
				tOtherInfoSQL2 = tOtherInfoSQL;
			} catch (Exception ex) {
				logger.debug("查询辅助信息错误：" + ex.toString());
			}

			// 结果装入打印容器
			tListTable.add(tResult);
		}

		tXmlExport.addListTable(tListTable, Title);
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		// tXmlExport.outputDocumentToFile("D:\\", "test07-02-02");//测试输出
		mResult.clear();
		mResult.add(tXmlExport);
		return true;
	}

	/** 错误处理* */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "XQInComeFeeDailyCheckQueryBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "8623";
		tG.ManageCom = "8623";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "8623"); // 统计机构
		tTransferData.setNameAndValue("StartDate", ""); // 开始时间
		tTransferData.setNameAndValue("EndDate", "2006-12-31"); // 结束时间
		tTransferData.setNameAndValue("SaleChnl", ""); // 渠道
		tTransferData.setNameAndValue("AgentCode", ""); // 操作员
		tTransferData.setNameAndValue("Bdlx", ""); // 操作员

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		XQInComeFeeDailyCheckQueryBL tXQInComeFeeDailyCheckBL = new XQInComeFeeDailyCheckQueryBL();
		if (tXQInComeFeeDailyCheckBL.submitData(tVData, "") == false) {
			logger.debug("----------续收打印-->续期保费实收日结清单出错---------------");
		}
	}

}
