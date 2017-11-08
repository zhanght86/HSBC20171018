package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.f1print.PrintService;
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
 * Title:财务收付-->财务日结-->理赔日结-->拒赔退保金日结:
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

public class LLRefusePolFeeDayCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLRefusePolFeeDayCheckBL.class);

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

	public LLRefusePolFeeDayCheckBL() {

	}

	/**
	 * 主函数------主要用于 设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-8-1");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2005-8-31"); // 结束日期
		tTransferData.setNameAndValue("StatiCode", "86");// 统计机构
		tTransferData.setNameAndValue("FinDayType", "4");// 日结类型
		tTransferData.setNameAndValue("NCLType", "1");// 理赔类型

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLRefusePolFeeDayCheckBL tLLRefusePolFeeDayCheckBL = new LLRefusePolFeeDayCheckBL();
		if (tLLRefusePolFeeDayCheckBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔日结-->拒赔退保金日结 出错---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔日结-->拒赔退保金日结开始---------LLRefusePolFeeDayCheckBL---");
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
		logger.debug("-----理赔日结-->拒赔退保金日结结束----LLRefusePolFeeDayCheckBL---");

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
			tError.moduleName = "LLRefusePolFeeDayCheckBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取“--统计机构--”信息发生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mStartDate.equals("") || mEndDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLRefusePolFeeDayCheckBL";
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
		tXmlExport.createDocument("RefusePolFeeDayCheck.vts", "");

		// 系统时间---------------------------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		String tSysTime = StrTool.getHour() + "时" + StrTool.getMinute() + "分"
				+ StrTool.getSecond() + "秒";

		// **个人业务（除短期意外险****/
		ListTable tListTable = new ListTable();
		tListTable.setName("PES");
		String[] Title = new String[4]; // 定义列表标题
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		// Title[4] = "";

		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";

		// 首先 查询统计“序号----险种----缴费期限----本日金额----本月累计”，用于外层循环
		String strSQL = " select distinct b.riskcode,(select riskname from lmrisk where riskcode=b.riskcode) "
				+ " ,b.payyears,b.payendyearflag,sum(case when a.pay is null then 0 else a.pay end)"
				// +" ,("
				// +" select sum(nvl(j.pay,0)) from ljagetclaim j where 1=1 and
				// j.managecom like '"+mStatiCode+"%' and j.feefinatype = 'TB'
				// and j.feeoperationtype = 'D02' "
				// +" and j.opconfirmdate between
				// last_day(Add_months('"+mStartDate+"',-1)+1) and
				// '"+mEndDate+"' "
				// +" and j.polno in( select c.polno from lcpol c where
				// c.riskcode=b.riskcode and c.payyears=b.payyears and
				// c.payendyearflag=b.payendyearflag )"
				// +" )"
				// by niuzj,2005-12-12,对于拒赔案件，其终了红利统计到财务科目04中
				// +" from ljagetclaim a ,lcpol b,llclaimpolicy c "
				// 针对90000014694赔案做的修改,by niuzj,2005-12-17
				+ "  from ljagetclaim a ,lcpol b "
				+ " where 1=1 and a.polno=b.polno "
				+ " and ((a.feefinatype = 'TB' and a.feeoperationtype = 'D02') "
				+ " or (a.feefinatype = 'EF' and a.feeoperationtype = 'C05' "
				+ " and (exists (select p.polno from llclaimpolicy p where p.clmno=a.otherno and p.polno=a.polno and p.givetype='1')))) "
				+ " and a.managecom like concat('"
				+ "?staticode?"
				+ "','%') "
				+ " and a.opconfirmdate between '"
				+ "?startdate?"
				+ "' and  '"
				+ "?enddate?"
				+ "' "
				+ tNCLType
				+ " "
				// +" and a.otherno=c.clmno and a.polno=c.polno "
				// +" and c.givetype='1' "
				// +" and a.polno not in(select p.polno from llclaimpolicy p
				// where p.clmno=a.otherno and p.polno=a.polno and
				// p.givetype='0') "
				+ " group by b.riskcode,b.payyears,b.payendyearflag "
				+ " order by b.riskcode,b.payyears,b.payendyearflag ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);

		double tSumDaily = 0;// 总计-----本日金额
		// double tSumMonthly=0;//总计-----本月累计

		int tMaxRow = tSSRS.MaxRow;
		logger.debug(" -------------tMaxRow======" + tMaxRow);
		for (int i = 1; i <= tMaxRow; i++) {
			logger.debug("------第" + i
					+ "次循环开始----------------------------");
			String tRowNum = String.valueOf(i); // 序号
			String tRiskCode = tSSRS.GetText(i, 1); // 险种
			String tRiskCodeName = tSSRS.GetText(i, 2); // 险种名称
			String tPayYears = tSSRS.GetText(i, 3); // 交费年期（缴费期限）
			String tPayEndYearFlag = tSSRS.GetText(i, 4); // 终交年龄年期标志
			String tPayYear = "";
			String tDaily = tSSRS.GetText(i, 5);
			/** 查询--本日应付--** */
			// String tMonthly = tSSRS.GetText(i,6); /**查询--本月应付--***/
			// 为数组赋值，然后Add到listable里
			String[] Stra = new String[5];
			Stra[0] = tRowNum;
			Stra[1] = tRiskCodeName;
			Stra[2] = tPayYears;
			Stra[3] = tDaily;
			// Stra[4] = tMonthly;
			// 总计----本日应付*****本月应付
			tSumDaily = tSumDaily + Double.parseDouble(tDaily);
			// / tSumMonthly = tSumMonthly + Double.parseDouble(tMonthly);
			tListTable.add(Stra);
		}
		// 格式化金额数据 new DecimalFormat("0.00").format(t_Sum_Return),保留两位小数
		String str_tSumDaily = new DecimalFormat("0.00").format(tSumDaily);
		// String str_tSumMonthly =new
		// DecimalFormat("0.00").format(tSumMonthly);

		// 查询“填报单位”的 名称
		String tStatiComName = "";
		String strComSQL = " select name from ldcom where comcode='"
				+ "?staticode?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strComSQL);
		sqlbv1.put("staticode", mStatiCode);
		ExeSQL tComExeSQL = new ExeSQL();
		SSRS tComSSRS = new SSRS();
		tComSSRS = tComExeSQL.execSQL(sqlbv1);
		if (tComSSRS.MaxRow > 0) {
			tStatiComName = tComSSRS.GetText(1, 1);
		}
		// 查询制表人 姓名
		String tOperatorName = "";
		String strOperatorSQL = "select username from lduser where usercode='"
				+ "?usercode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strOperatorSQL);
		sqlbv2.put("usercode", mG.Operator);
		ExeSQL tOperatorExeSQL = new ExeSQL();
		SSRS tOperatorSSRS = new SSRS();
		tOperatorSSRS = tOperatorExeSQL.execSQL(sqlbv2);
		if (tOperatorSSRS.MaxRow > 0) {
			tOperatorName = tOperatorSSRS.GetText(1, 1);
		}

		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tTextTag.add("RPFDCStaTime", SysDate); // $=/RPFDCStaTime$----当前时间
		tTextTag.add("CurrentTime", tSysTime);
		tTextTag.add("PerExcShoCom", tStatiComName); // 填报单位：$=/PerExcShoCom$
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		tTextTag.add("SumDaily", str_tSumDaily); // $=/SumDaily$---总计----本日应付
		// tTextTag.add("SumMonthly", str_tSumMonthly);
		// //$=/SumMonthly---总计----本月应付
		tTextTag.add("PerExcShoMaker", tOperatorName); // 制表：$=/PerExcShoMaker$
		tTextTag.add("StartDate", mStartDate);
		tTextTag.add("EndDate", mEndDate);
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
