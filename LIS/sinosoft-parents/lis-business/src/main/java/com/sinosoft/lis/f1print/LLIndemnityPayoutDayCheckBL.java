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
 * Title:财务收付-->财务日结-->理赔日结-->理赔－赔款支出日结:
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

public class LLIndemnityPayoutDayCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLIndemnityPayoutDayCheckBL.class);

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

	public LLIndemnityPayoutDayCheckBL() {

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

		logger.debug("-----理赔日结-->理赔－赔款支出日结开始---------LLIndemnityPayoutDayCheckBL---");
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
		logger.debug("-----理赔日结--理赔－赔款支出日结结束----LLIndemnityPayoutDayCheckBL---");

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
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		if (mStatiCode == null || mStatiCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLIndemnityPayoutDayCheckBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“--统计机构--”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mStartDate.equals("") || mEndDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLIndemnityPayoutDayCheckBL";
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
		tXmlExport.createDocument("IndemnityPayoutDayCheck.vts", "");

		// 系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		String tSysTime = StrTool.getHour() + "时" + StrTool.getMinute() + "分"
				+ StrTool.getSecond() + "秒";

		// **个人业务****
		ListTable tListTable = new ListTable();
		tListTable.setName("IPDC");
		String[] Title = new String[3]; // 定义列表标题
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		// Title[3] = "";

		// 查询语句“ 序号--险种--本日应付--本月应付”
		// 赔款支出日结<短险赔付，包括短医、短意>----业务类型：A；子业务类型：；财务类型：PK ；
		// 以下主要是 日期的问题《》
		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where j.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where j.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";

		String strSQL = "select distinct j.riskcode,(select riskname from lmrisk where riskcode = j.riskcode),sum(j.pay)"
				// +",("
				// +" select sum(jj.pay) from ljagetclaim jj where 1 = 1 and
				// jj.riskcode = j.riskcode"
				// +" and jj. feefinatype = 'PK' and jj.feeoperationtype = 'A' "
				// +" and jj.managecom like '"+mStatiCode+"%' "
				// +" and jj.opconfirmdate between
				// (last_day(add_months('"+mStartDate+"', -1)) + 1) and
				// '"+mEndDate+"' "
				// +" )"
				// +" from ljagetclaim j, lirisktype p,llclaimpolicy c "
				// by niuzj,2005-12-17
				+ " from ljagetclaim j, lmriskapp p "
				+ " where 1 = 1 and j.riskcode=p.riskcode and p.riskperiod !='L' "
				+ " and ((j.feefinatype = 'PK' and j.feeoperationtype = 'A') "
				// Modifyed by niuzj 2005-11-15,QC4695号BUG,在原基础上统计"年度红利和终了红利"
				+ " or (j.feefinatype = 'EF' and j.feeoperationtype = 'C05' "
				+ " and (exists (select p.polno from llclaimpolicy p where p.clmno=j.otherno and p.polno=j.polno and p.givetype='0')))) "
				+ " and j.managecom like concat('"
				+ "?mStatiCode?"
				+ "','%') and j.opconfirmdate between '"
				+ "?mStartDate?"
				+ "' and  '"
				+ "?mEndDate?" + "' "
				// +" and j.otherno=c.clmno and j.polno=c.polno "
				// +" and c.givetype='0' "
				// +" and j.polno not in(select m.polno from llclaimpolicy m
				// where m.clmno=j.otherno and m.polno=j.polno and
				// m.givetype='1') "
				+ tNCLType + "" + " group by j.riskcode order by j.riskcode";

		logger.debug("==================================================");
		logger.debug("====查询语句===" + strSQL);
		logger.debug("==================================================");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("mStatiCode", mStatiCode);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		int tMaxRow = tSSRS.MaxRow;
		logger.debug("*************总共查询出" + tMaxRow + "条记录************");

		double tSumDaily = 0;// 总计-----本日金额
		double tSumMonthly = 0;// 总计-----本月累计

		for (int i = 1; i <= tMaxRow; i++) {
			logger.debug("=========================================");
			logger.debug("************第" + i + "次循环开始**************");
			String tRowNum = String.valueOf(i); // 序号
			String tRiskCode = tSSRS.GetText(i, 1); // 险种
			String tRiskName = tSSRS.GetText(i, 2); // 险种名称
			String tDMoney = tSSRS.GetText(i, 3); // 本日金额
			// String tMMoney = tSSRS.GetText(i, 4); //本月累计

			String[] Stra = new String[4]; // 定义列表标题
			Stra[0] = tRowNum; // 序号
			Stra[1] = tRiskName; // 险种
			Stra[2] = tDMoney; // 本日金额
			// Stra[3] = tMMoney; //本月累计

			tSumDaily = tSumDaily + Double.parseDouble(tDMoney);
			// tSumMonthly=tSumMonthly+Double.parseDouble(tMMoney);

			tListTable.add(Stra);
			logger.debug("************第" + i + "次循环结束**************");
		}
		// 格式化金额数据 ,保留两位小数
		// String str_tSumReturn=new DecimalFormat("0.00").format(tSumReturn)
		String str_tSumDaily = new DecimalFormat("0.00").format(tSumDaily);
		String str_tSumMonthly = new DecimalFormat("0.00").format(tSumMonthly);

		// 查询“填报单位”的 名称
		String tStatiComName = "";
		String strComSQL = " select name from ldcom where comcode='"
				+ "?mStatiCode?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strComSQL);
		sqlbv3.put("mStatiCode", mStatiCode);
		ExeSQL tComExeSQL = new ExeSQL();
		SSRS tComSSRS = new SSRS();
		tComSSRS = tComExeSQL.execSQL(sqlbv3);
		if (tComSSRS.MaxRow > 0) {
			tStatiComName = tComSSRS.GetText(1, 1);
		}
		// 查询制表人 姓名
		String tOperatorName = "";
		String strOperatorSQL = "select username from lduser where usercode='"
				+ "?Operator?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strOperatorSQL);
		sqlbv2.put("Operator", mG.Operator);
		ExeSQL tOperatorExeSQL = new ExeSQL();
		SSRS tOperatorSSRS = new SSRS();
		tOperatorSSRS = tOperatorExeSQL.execSQL(sqlbv2);
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
		tTextTag.add("MngCom", tStatiComName); // 填报单位：$=/AgentCom$
		tTextTag.add("DayMoney", str_tSumDaily); // $=/DayMoney$--总计--本日金额
		// tTextTag.add("MuthMoney",str_tSumMonthly); //$=/MuthMoney$--总计--本月累计
		tTextTag.add("Operator", tOperatorName); // 制表：$=/Operator

		logger.debug("===================================================");
		logger.debug("*********");
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
