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
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:财务收付-->财务日结-->理赔日结-->理赔－终了红利日结:
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

public class LLEndBonusDayCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLEndBonusDayCheckBL.class);

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

	public LLEndBonusDayCheckBL() {

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

		logger.debug("-----理赔日结-->理赔－终了红利日结开始---------LLEndBonusDayCheckBL---");
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
		logger.debug("-----理赔日结-->理赔－终了红利日结结束----LLEndBonusDayCheckBL---");

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
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中

		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束日期
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mFinDayType = (String) mTransferData.getValueByName("FinDayType"); // 日结类型

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		if (mStatiCode == null || mStatiCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLEndBonusDayCheckBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“--统计机构--”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mStartDate.equals("") || mEndDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLEndBonusDayCheckBL";
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

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("EndBonusDayCheck.vts", "");

		// 系统时间---------------------------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// **个人业务****/
		ListTable tListTable = new ListTable();
		tListTable.setName("ZL");
		String[] Title = new String[3]; // 定义列表标题
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		// Title[3] = "";

		// 首先 查询统计“序号--单位--本日红利--本月红利”，用于外层循环
		String strSQL = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strSQL = " select distinct rownum ,a.managecom,(select name from ldcom where comcode=a.managecom) "
					+ " ,sum((case when a.pay is not null then a.pay  else 0 end))"
					+ " ,("
					+ " select sum((case when j.pay is not null then j.pay  else 0 end)) from ljagetclaim j where 1=1  and  j.managecom like concat('"
					+ "?mStatiCode?"
					+ "','%') "
					+ " and j.feefinatype = 'TF' and j.feeoperationtype = 'D01' and j.subfeeoperationtype='C0502' "
					+ " and j.opconfirmdate between last_day(Add_months('"
					+ "?mStartDate?"
					+ "',-1)+1) and '"
					+ "?mEndDate?"
					+ "' "
					+ " and j.polno in( select c.polno from lcpol c where c.managecom=a.managecom )"
					+ " )"
					+ " from ljagetclaim a ,lcpol b where 1=1 and a.polno=b.polno "
					+ " and a.feefinatype = 'EF' and a.feeoperationtype = 'C05'  and a.subfeeoperationtype='C0502' "
					+ " and a.managecom like concat('"
					+ "?mStatiCode?"
					+ "','%') and a.opconfirmdate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?" + "' " + " group by rownum,a.managecom ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strSQL = " select (@rownum := @rownum + 1) AS rownum,managecom,name,apay,jpay from (select distinct @rownum:= 0,a.managecom,(select name from ldcom where comcode=a.managecom) name "
					+ " ,sum((case when a.pay is not null then a.pay  else 0 end)) apay"
					+ " ,("
					+ " select sum((case when j.pay is not null then j.pay  else 0 end)) from ljagetclaim j where 1=1  and  j.managecom like concat('"
					+ "?mStatiCode?"
					+ "','%') "
					+ " and j.feefinatype = 'TF' and j.feeoperationtype = 'D01' and j.subfeeoperationtype='C0502' "
					+ " and j.opconfirmdate between last_day(Add_months('"
					+ "?mStartDate?"
					+ "',-1)+1) and '"
					+ "?mEndDate?"
					+ "' "
					+ " and j.polno in( select c.polno from lcpol c where c.managecom=a.managecom )"
					+ " ) jpay"
					+ " from ljagetclaim a ,lcpol b where 1=1 and a.polno=b.polno "
					+ " and a.feefinatype = 'EF' and a.feeoperationtype = 'C05'  and a.subfeeoperationtype='C0502' "
					+ " and a.managecom like concat('"
					+ "?mStatiCode?"
					+ "','%') and a.opconfirmdate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?" + "' " + ") t group by rownum,managecom ";
		}
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("mStatiCode", mStatiCode);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);

		double tSumDaily = 0;// 总计-----本日金额
		// double tSumMonthly=0;//总计-----本月累计

		int tMaxRow = tSSRS.MaxRow;
		logger.debug(" -------------tMaxRow======" + tMaxRow);
		for (int i = 1; i <= tMaxRow; i++) {
			logger.debug("------第" + i
					+ "次循环开始----------------------------");
			String tRowNum = tSSRS.GetText(i, 1);// 序号
			String tMngCode = tSSRS.GetText(i, 2);//
			String tMngName = tSSRS.GetText(i, 3);//
			String tDaily = tSSRS.GetText(i, 4);// **
			// String tMonthly=tSSRS.GetText(i,5); /**查询--本月应付--***/

			// 为数组赋值，然后Add到listable里
			String[] Stra = new String[3];
			Stra[0] = tRowNum;
			Stra[1] = tMngName;
			Stra[2] = tDaily;
			// Stra[3] = tMonthly;
			// 总计----本日应付*****本月应付
			tSumDaily = tSumDaily + Double.parseDouble(tDaily);
			// tSumMonthly = tSumMonthly + Double.parseDouble(tMonthly);
			tListTable.add(Stra);
		}
		// 格式化金额数据 ,保留两位小数
		// String str_tSumReturn=new DecimalFormat("0.00").format(tSumReturn)
		String str_tSumDaily = new DecimalFormat("0.00").format(tSumDaily);
		// String str_tSumMonthly = new
		// DecimalFormat("0.00").format(tSumMonthly);

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

		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("StartDate", mStartDate);// 统计日期：$=/StartDate$至$=/EndDate$
		tTextTag.add("EndDate", mEndDate);
		tTextTag.add("CurrentDate", SysDate);// 制表日期：$=/CurrentDate$
		tTextTag.add("AgentCom", tStatiComName);// 填报单位：$=/AgentCom$
		tTextTag.add("SumDaily", str_tSumDaily);// $=/SumDaily$
		// tTextTag.add("SumMonthly",str_tSumMonthly);//$=/SumMonthly$
		tTextTag.add("Operator", tOperatorName);// 制表：$=/Operator$

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("------------------打包数据，准备返回前台----------------");
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
