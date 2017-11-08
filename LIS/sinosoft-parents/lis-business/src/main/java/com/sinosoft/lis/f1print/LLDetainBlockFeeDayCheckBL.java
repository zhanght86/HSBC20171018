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
 * Title:财务收付-->财务日结-->理赔日结-->理赔扣回自垫保费及利息日结:
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

public class LLDetainBlockFeeDayCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLDetainBlockFeeDayCheckBL.class);

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

	public LLDetainBlockFeeDayCheckBL() {

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

		logger.debug("-----理赔日结-->理赔扣回自垫保费及利息日结开始---------LLDetainBlockFeeDayCheckBL---");
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
		logger.debug("-----理赔日结-->理赔扣回自垫保费及利息日结结束----LLDetainBlockFeeDayCheckBL---");

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
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		mFinDayType = (String) mTransferData.getValueByName("FinDayType"); // 日结类型

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

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("DetainBlockFeeDayCheck.vts", "");

		// 系统时间---------------------------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		String tSysTime = StrTool.getHour() + "时" + StrTool.getMinute() + "分"
				+ StrTool.getSecond() + "秒";
		/** ******************************************************************* */
		ListTable tListTable = new ListTable();
		tListTable.setName("KH");
		String[] Title = new String[5]; // 定义列表标题
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		// Title[5] = "";
		// Title[6] = "";

		// 序号 险种 缴费期限 本日保费 本日利息 本月保费 本月利息
		// C07 保单自动垫缴 C0701 自动垫缴 BF
		// C07 保单自动垫缴 C0702 自动垫缴利息 LX
		double tSumDMoney = 0;// $=$=/DMoney--总计--本日金额
		// double tSumMMoney = 0; //$=$=/MMoney--总计--本月累计
		double tSumDAccrual = 0; // $=$=/DAccrual--总计--本日利息
		// double tSumMAccrual = 0; //$=$=/MAccrual--总计--本月利息

		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";

		String strSQL = "select distinct b.riskcode,(select riskname from lmrisk where riskcode=b.riskcode),b.payyears,b.payendyearflag "
				// 以下查询 “本日金额”
				+ ",( "
				+ " select sum(jj.pay)  from ljagetclaim jj ,lcpol j where 1=1 "
				+ " and j.riskcode= b.riskcode and j.payyears= b.payyears and j.payendyearflag=b.payendyearflag"
				+ " and jj.polno=j.polno and jj.FeeFinaType = 'BF' and jj.FeeOperationType = 'C07'"
				+ " and jj.opconfirmdate >= '"
				+ "?mStartDate?"
				+ "'  and jj.opconfirmdate <= '"
				+ "?mEndDate?"
				+ "'  and jj.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%')"
				+ " group by j.riskcode,b.payyears,b.payendyearflag"
				+ ")"
				// 以下查询 “本月金额累计”
				+ ",( "
				+ " select sum(jj.pay)  from ljagetclaim jj ,lcpol j where 1=1 "
				+ " and j.riskcode= b.riskcode and j.payyears= b.payyears and j.payendyearflag=b.payendyearflag"
				+ " and jj.polno=j.polno  and jj.FeeFinaType = 'BF' and jj.FeeOperationType = 'C07'"
				+ " and jj.opconfirmdate >= (last_day(Add_months('"
				+ "?mStartDate?"
				+ "',-1))+1) and jj.opconfirmdate <= '"
				+ "?mEndDate?"
				+ "'  and jj.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%')"
				+ " group by j.riskcode,b.payyears,b.payendyearflag"
				+ ")"
				// 以下查询 “本日利息”
				+ ",( "
				+ " select sum(jj.pay)  from ljagetclaim jj ,lcpol j where 1=1 "
				+ " and j.riskcode= b.riskcode and j.payyears= b.payyears and j.payendyearflag=b.payendyearflag"
				+ " and jj.polno=j.polno and jj.FeeFinaType = 'LX' and jj.FeeOperationType = 'C07' "
				+ " and jj.opconfirmdate >= '"
				+ "?mStartDate?"
				+ "' and jj.opconfirmdate <= '"
				+ "?mEndDate?"
				+ "'  and jj.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%')"
				+ " group by j.riskcode,b.payyears,b.payendyearflag"
				+ ")"
				// 以下查询 “本月利息累积”
				+ ",( "
				+ " select sum(jj.pay)  from ljagetclaim jj ,lcpol j where 1=1 "
				+ " and j.riskcode= b.riskcode and j.payyears= b.payyears and j.payendyearflag=b.payendyearflag"
				+ " and jj.polno=j.polno  and jj.FeeFinaType = 'LX' and jj.FeeOperationType = 'C07' "
				+ " and jj.opconfirmdate >= (last_day(Add_months('"
				+ "?mStartDate?"
				+ "',-1))+1) and jj.opconfirmdate <= '"
				+ "?mEndDate?"
				+ "'  and jj.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%')"
				+ " group by j.riskcode,b.payyears,b.payendyearflag"
				+ ")"

				+ " from ljagetclaim a ,lcpol b where 1=1 "
				// <#######and b.polno in(select c.polno from LJAPayPerson c
				// where c.paycount>0)######,判断首续期>
				+ " and a.polno=b.polno "
				+ " and a.FeeFinaType in('LX','BF') and a.FeeOperationType = 'C07'" // 《此处还需要加入
																					// 业务类型、财务类型
																					// 判断》
				+ " and a.opconfirmdate >= '"
				+ "?mStartDate?"
				+ "' and a.opconfirmdate <= '"
				+ "?mEndDate?"
				+ "'  and a.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%') "
				+ tNCLType
				+ " "
				+ " group by b.riskcode,b.payyears,  b.payendyearflag "
				+ " order by b.riskcode ";

		logger.debug("===========================================================");
		logger.debug("====查询语句===" + strSQL);
		logger.debug("===========================================================");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("mStatiCode", mStatiCode);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		int tMaxRow = tSSRS.MaxRow;
		for (int i = 1; i <= tMaxRow; i++) {
			String[] Stra = new String[7]; // 定义列表标题
			String tRowNum = String.valueOf(i);// 序号
			String tRiskCode = tSSRS.GetText(i, 1); // 险种
			String tRiskName = tSSRS.GetText(i, 2); // 险种名称
			String tPayYears = tSSRS.GetText(i, 3); // 交费年期（缴费期限）
			String tPayEndYearFlag = tSSRS.GetText(i, 4); // 终交年龄年期标志
			String tDMoney = tSSRS.GetText(i, 5);// 本日金额
			// String tMMoney = tSSRS.GetText(i,6);//本月累计
			String tDAccrual = tSSRS.GetText(i, 7);// 本日利息
			// String tMAccrual =tSSRS.GetText(i,8);//本月利息

			Stra[0] = tRowNum;// 序号
			Stra[1] = tRiskName;// 险种
			Stra[2] = tPayYears;// 缴费期限
			Stra[3] = tDMoney;// 本日金额
			Stra[4] = tDAccrual;
			// Stra[4] = tMMoney;//本月累计
			// Stra[5] = tDAccrual;//本日利息
			// Stra[6] = tMAccrual;//本月利息

			tSumDMoney = tSumDMoney + Double.parseDouble(tDMoney);// $=$=/DMoney--总计--本日金额
			// tSumMMoney = tSumMMoney + Double.parseDouble(tMMoney);
			// //$=$=/MMoney--总计--本月累计
			tSumDAccrual = tSumDAccrual + Double.parseDouble(tDAccrual);// $=$=/DAccrual--总计--本日利息
			// tSumMAccrual = tSumMAccrual +
			// Double.parseDouble(tMAccrual);//$=$=/MAccrual--总计--本月利息

			tListTable.add(Stra);
		}
		// 格式化金额数据 ,保留两位小数
		// String str_tSumReturn=new DecimalFormat("0.00").format(tSumReturn)
		String str_tSumDMoney = new DecimalFormat("0.00").format(tSumDMoney);
		// String str_tSumMMoney = new DecimalFormat("0.00").format(tSumMMoney);
		String str_tSumDAccrual = new DecimalFormat("0.00")
				.format(tSumDAccrual);
		// String str_tSumMAccrual = new
		// DecimalFormat("0.00").format(tSumMAccrual);

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
				+"?Operator?"+ "'";
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
		tTextTag.add("StartDate", mStartDate);// 统计日期：$=/StartDate$至$=/EndDate$
		tTextTag.add("EndDate", mEndDate);
		tTextTag.add("CurrentDate", SysDate);// 制表日期：$=/CurrentDate$
		tTextTag.add("CurrentTime", tSysTime);
		tTextTag.add("ManageComName", tStatiComName);// 填报单位：$=/ManageComName$
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		tTextTag.add("DMoney", str_tSumDMoney);// $=/dayMoney$--总计--本日金额
		// tTextTag.add("MMoney",str_tSumMMoney);//$=$=/MMoney--总计--本月累计
		tTextTag.add("DAccrual", str_tSumDAccrual);// $=$=/DAccrual--总计--本日利息
		// tTextTag.add("MAccrual",str_tSumMAccrual);//$=$=/MAccrual--总计--本月利息
		tTextTag.add("Operator", tOperatorName);// 制表：$=/Operator

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
