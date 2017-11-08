package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
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
 * Title:
 * </p>
 * <p>
 * Description : 二次核保加费日结
 * </p>
 * <p>
 * Copyright : Copyright (c) 2002
 * </p>
 * <p>
 * Company :
 * </p>
 * 
 * @author : zhaorx,2006-02-22
 * @version : 1.0
 *          <P>
 */

public class LLUWAddFeeDayCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(LLUWAddFeeDayCheckBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间
	private String mManageCom = ""; // 统计机构
	private String mManageComName = "";
	private String mStartDate = ""; // 实收起期
	private String mEndDate = ""; // 实收止期
	private String mNCLType = ""; // 申请类型

	public LLUWAddFeeDayCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("---统计清单：二次核保加费日结---LLUWAddFeeDayCheckBL测试---开始---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		logger.debug("---统计清单：二次核保加费日结---LLUWAddFeeDayCheckBL测试---结束---");

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
		mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		mManageComName = (String) mTransferData.getValueByName("ManageComName");
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 实收起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 实收止期
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("LLUWAddFeeDayCheck.vts", "");

		logger.debug("*************************************************");
		logger.debug("*********以下为“ListTable实例”准备数据*************");
		// 新建一个ListTable实例
		ListTable tListTable = new ListTable();
		tListTable.setName("UWAF");
		// 定义列表标题，共5列,采用循环的方式
		String[] Title = new String[5];
		for (int i = 1; i <= 5; i++) {
			Title[i - 1] = "";
		}
		// First 查询SQL
		// String tSQLF = " select
		// d.payintv,a.riskcode,sum(nvl(a.SumActuPayMoney,0)),sum(nvl(b.pay,0))/count(a.getnoticeno)"
		// + " from ljapayperson a,ljapayclaim b,lcpol d where 1=1 "
		// + " and a.managecom like '" +mManageCom+ "%' "
		// + " and a.getnoticeno = b.getnoticeno and a.polno = b.polno and
		// a.contno = b.contno and a.riskcode = b.riskcode "
		// + " and a.polno = d.polno "
		// + " and exists (select 'X' from ljapay c where c.getnoticeno =
		// a.getnoticeno "
		// + " and c.confdate between '" +mStartDate+ "' and '" +mEndDate+ "') "
		// + " and b.feeoperationtype = 'C30' and b.subfeeoperationtype =
		// 'C3002' and b.feefinatype = 'LX' and b.othernotype = '5' "
		// + " group by d.payintv,a.riskcode order by d.payintv,a.riskcode ";
		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";
		String tSQLF = " select b.payintv,tab.rc,sum((case when tab.sapm is not null then tab.sapm  else 0 end)),sum((case when a.pay is not null then a.pay  else 0 end)) from ljapayclaim a,lcpol b, "
				+ " (select t.getnoticeno as gnn,t.riskcode as rc,sum((case when t.sumactupaymoney is not null then t.sumactupaymoney  else 0 end)) as sapm from ljapayperson t "
				+ " where 1=1 and t.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and exists (select 'X' from ljapay c where c.getnoticeno = t.getnoticeno "
				+ " and c.confdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "') "
				+ " group by t.getnoticeno, t.riskcode) tab "
				+ " where a.actugetno = tab.gnn and a.polno = b.polno and a.riskcode = tab.rc "
				+ " and a.feeoperationtype = 'C30' and a.subfeeoperationtype = 'C3002' and a.feefinatype = 'LX' and a.othernotype = '5'"
				+ tNCLType + "" + " group by b.payintv, tab.rc ";

		logger.debug("**************以下执行外层查询语句********************");
		double addFeeSum = 0.00;// 加费总计
		double addFeePaySum = 0.00;// 利息总计
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQLF);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRSF = new SSRS();
		tSSRSF = tExeSQL.execSQL(sqlbv1);
		if (tSSRSF.getMaxRow() != 0) {
			for (int index = 1; index <= tSSRSF.getMaxRow(); index++) {
				String payIntv = tSSRSF.GetText(index, 1);// 缴费期限
				String riskCode = tSSRSF.GetText(index, 2);// 险种代码
				String addFee = tSSRSF.GetText(index, 3);// 二核加费
				String addFeePay = tSSRSF.GetText(index, 4);// 加费利息
				// Second 查询SQL
				String tSQLS = " select riskname from lmrisk where riskcode = '"
						+ "?riskCode?" + "'";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSQLS);
				sqlbv3.put("riskCode", riskCode);
				String riskName = tExeSQL.getOneValue(sqlbv3);// 险种名称

				String[] Stra = new String[5];
				Stra[0] = String.valueOf(index);// 序号
				Stra[1] = riskName; // 险种名称
				Stra[2] = payIntv; // 缴费期限
				Stra[3] = addFee; // 二核加费
				Stra[4] = addFeePay; // 加费利息
				tListTable.add(Stra);

				addFeeSum = addFeeSum + Double.parseDouble(addFee);// 加费总计
				addFeePaySum = addFeePaySum + Double.parseDouble(addFeePay);// 利息总计
			}
		} else // 查询无结果
		{
			CError tError = new CError();
			tError.moduleName = "LLUWAddFeeDayCheckBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("*****以上为“ListTable实例”准备数据完成，成功！！！*****");
		logger.debug("***********************************************");

		logger.debug("*********以下为“TextTag实例”准备数据*************");
		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 为表头准备数据
		String staTimes = mStartDate + "至" + mEndDate;
		// 为表头赋值
		tTextTag.add("StaTimes", staTimes);// 统计日期：$=/StaTimes$
		tTextTag.add("MakeTime", mCurrentTime);// 制表时间：$=/MakeTime$
		tTextTag.add("MakeDate", mCurrentDate);// 制表日期：$=/MakeDate$
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		// 填报单位：$=/StaCompany$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("StaCompany", tMngCom);
		// 为合计项目赋值
		tTextTag
				.add("SumDayMoney", new DecimalFormat("0.00").format(addFeeSum)); // 加费总计：$=/SumDayMoney$
		tTextTag.add("SumDayPay", new DecimalFormat("0.00")
				.format(addFeePaySum));// 利息总计：$=/SumDayPay$
		// 为表尾赋值 Third 查询制表人姓名SQL
		String tSQLT = " select username from lduser where usercode = '"
				+ "?Operator?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQLT);
		sqlbv2.put("Operator", mGlobalInput.Operator);
		String userName = tExeSQL.getOneValue(sqlbv2);
		tTextTag.add("Operator", userName);// 制表：$=/Operator$

		logger.debug("**以上为“TextTag实例”准备数据完成，成功！！！******");
		logger.debug("**********************************************");
		// 添加动态列表数组，参数为一个TextTag
		xmlexport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
		}
		logger.debug("---以下 将XmlExport打包，返回前台--");
		mResult.clear();
		mResult.add(xmlexport);
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

		tTransferData.setNameAndValue("StartDate", "2005-01-01"); // 实收起期
		tTransferData.setNameAndValue("EndDate", "2005-11-01"); // 实收止期
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLUWAddFeeDayCheckBL tLLUWAddFeeDayCheckBL = new LLUWAddFeeDayCheckBL();
		if (tLLUWAddFeeDayCheckBL.submitData(tVData, "") == false) {
			logger.debug("---统计清单：二次核保加费日结---失败----");
		}
		int n = tLLUWAddFeeDayCheckBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLUWAddFeeDayCheckBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
