package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLPRTPubFunBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
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
 * Title:财务收付-->财务日结-->理赔日结-->扣回质押贷款日结:
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
 * @author
 * @version 1.0
 */
public class LLDetainInpawnFeeDayCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLDetainInpawnFeeDayCheckBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private GlobalInput mG = new GlobalInput();
	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mStatiCode = ""; // 统计机构
	private String mFinDayType = ""; // 日结类型
	private String mNCLType = ""; // 申请类型

	public LLDetainInpawnFeeDayCheckBL() {

	}

	/**
	 * 主函数------主要用于 设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */
	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {
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

		return true;
	}

	// 得到外部传入的数据,将数据备份到本类中
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData; // 得到外部传入的数据,将数据备份到本类中

		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束日期
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mFinDayType = (String) mTransferData.getValueByName("FinDayType"); // 日结类型
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		if (mStatiCode == null || mStatiCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLDetainBlockFeeDayCheckBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“--统计机构--”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mStartDate.equals("") || mEndDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLDetainBlockFeeDayCheckBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“开始时间或结束时间”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		String tSysTime = StrTool.getHour() + "时" + StrTool.getMinute() + "分"
				+ StrTool.getSecond() + "秒";

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("DetainInpawnFeeDayCheck.vts", "");

		// 序号 保单号 保单质押贷款本金 保单质押贷款利息
		// C03 保单结算保单质押贷款 C0301 清偿贷款 HK
		// C03 保单结算保单质押贷款 C0302 清偿利息 LX
		ListTable tListTable = new ListTable();
		tListTable.setName("DI");
		String[] Title = new String[4]; // 定义列表标题
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";

		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where t.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where t.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";

		String strSQL = "select distinct t.contno"
				+ ",("
				+ " select sum(j.pay) from ljagetclaim j where 1=1 "
				+ " and j.contno=t.contno and j.feeoperationtype='C03' and j.feefinatype='HK'"
				+ " and t.opconfirmdate between to_date('"
				+ "?mStartDate?"
				+ "','yyyy-mm-dd') and to_date('"
				+ "?mEndDate?"
				+ "','yyyy-mm-dd')"
				+ " )"
				+ ",("
				+ " select sum(j.pay) from ljagetclaim j where 1=1 "
				+ " and j.contno=t.contno and j.feeoperationtype='C03' and j.feefinatype='LX'"
				+ " and t.opconfirmdate between to_date('"
				+ "?mStartDate?"
				+ "','yyyy-mm-dd') and to_date('"
				+ "?mEndDate?"
				+ "','yyyy-mm-dd')"
				+ " )"
				+ " from ljagetclaim t where 1=1"
				+ " and t.feeoperationtype='C03' and t.feefinatype in('HK','LX') "
				+ " and t.opconfirmdate between to_date('"
				+ "?mStartDate?"
				+ "','yyyy-mm-dd') and to_date('"
				+ "?mEndDate?"
				+ "','yyyy-mm-dd') "
				+ " and t.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%')" + tNCLType + " "// Add by zhaorx 2006-03-17
				+ " order by t.contno ";
		logger.debug("================================================");
		logger.debug("====查询语句===" + strSQL);
		logger.debug("================================================");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mStatiCode", mStatiCode);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		int tMaxRow = tSSRS.getMaxRow();

		double tCapSum = 0; // 查询 "总计--------保单质押贷款本金"
		double tAccSum = 0; // 查询 "总计--------保单质押贷款利息"
		for (int i = 1; i <= tMaxRow; i++) {
			String[] Stra = new String[4]; // 定义列表标题
			Stra[0] = String.valueOf(i);// 序号
			Stra[1] = tSSRS.GetText(i, 1);// 合同号（保单号）
			Stra[2] = tSSRS.GetText(i, 2);// 保单质押贷款本金
			Stra[3] = tSSRS.GetText(i, 3);// 保单质押贷款利息
			tCapSum = tCapSum + Double.parseDouble(Stra[2]);
			tAccSum = tAccSum + Double.parseDouble(Stra[3]);
			tListTable.add(Stra);
		}
		// 格式化金额数据 ,保留两位小数
		// String str_tSumReturn=new DecimalFormat("0.00").format(tSumReturn)
		String str_tCapSum = new DecimalFormat("0.00").format(tCapSum);
		String str_tAccSum = new DecimalFormat("0.00").format(tAccSum);

		// 查询“填报单位”的 名称
		String tStatiComName = "";
		String strComSQL = " select name from ldcom where comcode='"
				+ "?mStatiCode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strComSQL);
		sqlbv2.put("mStatiCode", mStatiCode);
		ExeSQL tComExeSQL = new ExeSQL();
		SSRS tComSSRS = new SSRS();
		tComSSRS = tComExeSQL.execSQL(sqlbv2);
		if (tComSSRS.MaxRow > 0) {
			tStatiComName = tComSSRS.GetText(1, 1);
		}
		// 查询制表人 姓名
		String tOperatorName = "";
		String strOperatorSQL = "select username from lduser where usercode='"
				+ "?Operator?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strOperatorSQL);
		sqlbv3.put("Operator", mG.Operator);
		ExeSQL tOperatorExeSQL = new ExeSQL();
		SSRS tOperatorSSRS = new SSRS();
		tOperatorSSRS = tOperatorExeSQL.execSQL(sqlbv3);
		if (tOperatorSSRS.MaxRow > 0) {
			tOperatorName = tOperatorSSRS.GetText(1, 1);
		}

		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("StartDate", mStartDate); // 统计日期：$=/StartDate$至$=/EndDate$
		tTextTag.add("EndDate", mEndDate);
		tTextTag.add("CurrentDate", SysDate); // 制表日期：$=/CurrentDate$
		tTextTag.add("CurrentTime", tSysTime);
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		tTextTag.add("ManageComName", tStatiComName); // 填报单位：$=/ManageComName$
		tTextTag.add("Operator", tOperatorName); // 制表：$=/Operator
		tTextTag.add("dayMoney", str_tCapSum);// 总计--------保单质押贷款本金<$=/dayMoney$>
		tTextTag.add("dayLx", str_tAccSum);// 总计--------保单质押贷款利息<$=/dayLx$>

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("---------准备返回前台-----------------");
		mResult.clear();
		mResult.add(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		return true;
	}

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}

}
