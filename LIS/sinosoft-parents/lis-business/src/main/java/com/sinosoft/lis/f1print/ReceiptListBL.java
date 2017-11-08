package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class ReceiptListBL {
private static Logger logger = Logger.getLogger(ReceiptListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private int mCount1 = 0;
	private int mColnum = 0;
	private String sec_sqla;
	private String ContType_sql;
	private String SecPayMode_sql;
	private String Station_sql;
	private String AgentCode_sql;
	private String StartDate_sql;
	private String EndDate_sql;
	private String Branchattr_sql;
	private double mSumMoney = 0.00; // 收入合计
	private String mContType;
	private String mSecPayMode;
	private String mStation;
	private String mAgentCode;
	private String mBranchNo;
	private String mStartDate;
	private String mEndDate;
	// 为了批量打印接收营销服务部
	private String mBranchAttr = "";

	public ReceiptListBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mContType = (String) cInputData.get(0);
		mSecPayMode = (String) cInputData.get(1);
		mStation = (String) cInputData.get(2);
		mAgentCode = (String) cInputData.get(3);
		mBranchNo = (String) cInputData.get(4);
		mStartDate = (String) cInputData.get(5);
		mEndDate = (String) cInputData.get(6);

		// 批量打印时按照营销机构进行打印
		mBranchAttr = (String) cInputData.get(7);

		mContType = mContType.trim();
		mSecPayMode = mSecPayMode.trim();
		mStation = mStation.trim();
		mAgentCode = mAgentCode.trim();
		mBranchNo = mBranchNo.trim();
		mStartDate = mStartDate.trim();
		mEndDate = mEndDate.trim();
		mBranchAttr = mBranchAttr.trim();

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ReceiptListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 查询出首期或者是续期的银行代收正确清单
	private boolean getPrintData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		if ((mBranchNo == null || mBranchNo.equals("null") || mBranchNo
				.equals(""))) {
			logger.debug("PRINT with PrePrtBill.vts");
			xmlexport.createDocument("PrePrtBill.vts", "PRINT");
		} else {
			logger.debug("PRINT with BatchPrtBill.vts");
			xmlexport.createDocument("BatchPrtBill.vts", "PRINT");
		}

		ListTable alistTable = new ListTable();
		alistTable.setName("BILL");

		// 渠道查询条件
		if (mContType == null || mContType.equals("null")
				|| mContType.equals("")) {
			ContType_sql = "";
		} else {
			ContType_sql = " and n.SaleChnl = '" + "?mContType?" + "'";
		}

		// 交费方式查询条件
		if (mSecPayMode == null || mSecPayMode.equals("null")
				|| mSecPayMode.equals("")) {
			SecPayMode_sql = "";
		} else {
			SecPayMode_sql = "and n.PayLocation = '" + "?mSecPayMode?" + "'";
		}

		// 管理机构查询条件
		if (mStation == null || mStation.equals("null") || mStation.equals("")) {
			Station_sql = "";
		} else {
			Station_sql = " and n.ManageCom like concat('" + "?mStation?" + "','%') ";
		}

		// 业务员查询条件
		if (mAgentCode == null || mAgentCode.equals("null")
				|| mAgentCode.equals("")) {
			AgentCode_sql = "";
		} else {
			AgentCode_sql = " and n.AgentCode='" + "?mAgentCode?" + "' ";
		}

		// 起始日期查询
		if (mStartDate == null || mStartDate.equals("null")
				|| mStartDate.equals("")) {
			StartDate_sql = "";
		} else {
			StartDate_sql = " and m.MakeDate >='" + "?mStartDate?" + "' ";
		}

		// 终止日期查询
		if (mEndDate == null || mEndDate.equals("null") || mEndDate.equals("")) {
			EndDate_sql = "";
		} else {
			EndDate_sql = " and m.MakeDate <= '" + "?mEndDate?" + "' ";
		}

		// 营销服务部查询
		if (mBranchAttr == null || mBranchAttr.equals("null")
				|| mBranchAttr.equals("")) {
			Branchattr_sql = "";
		} else {
			Branchattr_sql = "  and exists (select a.AgentCode "
					+ " from LAAgent a, LABranchGroup b "
					+ "where a.AgentCode= trim(n.AgentCode) "
					+ "  and a.BranchCode = b.AgentGroup "
					+ "  and b.BranchAttr like  concat('" + "?mBranchAttr?" + "','%'))";
		}

		logger.debug("ReceiptListBL");
		if ((mBranchNo != null && !mBranchNo.equals("null") && !mBranchNo
				.equals(""))) {
			// 传入批次号时，续期批次收据打印清单
			sec_sqla = " select m.invoiceno,m.contno,n.AppntName,x.startpaydate,"
					+ " x.sumduepaymoney, "
					+ "(select RiskShortName from LMRisk where RiskCode in "
					+ "(select RiskCode from LCPol where ContNo = n.ContNo "
					+ " and PolNo = MainPolNo)) as RiskCode,"
					+ "(select distinct PayCount from LJSPayPerson where "
					+ " GetNoticeNo = x.GetNoticeNo and PolNo in (select PolNo"
					+ "  from LCPol where ContNo = n.ContNo "
					+ "   and PolNo = MainPolNo)),n.agentcode,"
					+ "(select LAAgent.Name from LAAgent where (AgentCode) = trim(n.AgentCode)) AgentName,"
					+ "(select LABranchGroup.Name from LABranchGroup, LAAgent "
					+ "  where LAAgent.AgentCode = trim(n.AgentCode) "
					+ "    and LABranchGroup.AgentGroup = LAAgent.AgentGroup) as AgentGroup "
					+ "   from lis.LDContInvoiceMap m,lis.LCCont n,lis.LJSPay x "
					+ "  where 1=1 and n.ContNo=x.OtherNo and m.ContNo=x.OtherNo "
					+ "    and m.ContNo=n.ContNo and m.OperType = '3' "
					+ "   and m.BatchNo='"
					+ mBranchNo
					+ "' "
					+ ContType_sql
					+ SecPayMode_sql
					+ Station_sql
					+ AgentCode_sql
					+ " order by AgentGroup,n.AgentCode,x.PayDate, n.ContNo";
			// 按区部组、服务人员、交费对应日、保单号码排序
		} else if (mBranchAttr != null && !mBranchAttr.equals("null")
				&& !mBranchAttr.equals("")) {
			// 传入营销服务部编号,续期批量打印
			sec_sqla = " select m.InvoiceNo,m.ContNo,n.AppntName,x.StartPayDate,"
					+ " x.SumDuePayMoney, "
					+ "(select RiskShortName from LMRisk where RiskCode in "
					+ "(select RiskCode from LCPol where ContNo = n.ContNo "
					+ " and PolNo = MainPolNo)) as RiskCode,"
					+ "(select distinct PayCount from LJSPayPerson where "
					+ " GetNoticeNo = x.GetNoticeNo and PolNo in (select PolNo"
					+ "  from LCPol where ContNo = n.ContNo "
					+ "   and PolNo = MainPolNo)), n.AgentCode,"
					+ "(select LAAgent.Name from LAAgent where (AgentCode) = trim(n.AgentCode)) AgentName,"
					+ "(select LABranchGroup.Name from LABranchGroup, LAAgent "
					+ "  where LAAgent.AgentCode = trim(n.AgentCode) "
					+ "    and LABranchGroup.AgentGroup = LAAgent.AgentGroup) as AgentGroup "
					+ "   from lis.LDContInvoiceMap m,lis.LCCont n,lis.LJSPay x"
					+ "  where 1=1 and n.ContNo=x.OtherNo and m.ContNo=x.OtherNo "
					+ "    and m.ContNo=n.ContNo and m.OperType = '3' "
					+ ContType_sql
					+ SecPayMode_sql
					+ Station_sql
					+ AgentCode_sql
					+ StartDate_sql
					+ EndDate_sql
					+ Branchattr_sql
					+ " order by x.PayDate,AgentGroup, n.AgentCode,n.ContNo";
		} else {
			// 营销服务部及批次号均为空时,预印收据打印清单
			sec_sqla = " select m.InvoiceNo,m.ContNo,n.AppntName,x.StartPayDate,"
					+ " x.SumDuePayMoney, "
					+ "(select RiskShortName from LMRisk where RiskCode in "
					+ "(select RiskCode from LCPol where ContNo = n.ContNo "
					+ " and PolNo = MainPolNo)) as RiskCode,"
					+ "(select distinct PayCount from LJSPayPerson where "
					+ " GetNoticeNo = x.GetNoticeNo and PolNo in (select PolNo"
					+ "  from LCPol where ContNo = n.ContNo "
					+ "   and PolNo = MainPolNo)), n.AgentCode,"
					+ "(select LAAgent.Name from LAAgent where (AgentCode) = trim(n.AgentCode)) AgentName,"
					+ "(select LABranchGroup.Name from LABranchGroup, LAAgent "
					+ "  where LAAgent.AgentCode = trim(n.AgentCode) "
					+ "    and LABranchGroup.AgentGroup = LAAgent.AgentGroup) as AgentGroup "
					+ "   from lis.LDContInvoiceMap m,lis.LCCont n,lis.LJSPay x"
					+ "  where 1=1 and n.ContNo=x.OtherNo and m.ContNo=x.OtherNo "
					+ "    and m.ContNo=n.ContNo and m.OperType = '3' "
					+ ContType_sql
					+ SecPayMode_sql
					+ Station_sql
					+ AgentCode_sql
					+ StartDate_sql
					+ EndDate_sql
					+ " order by x.PayDate,AgentGroup, n.AgentCode,n.ContNo";
			// 按对应日、区部组、收费人、保单号排序
		}
		logger.debug("查询的语句是" + sec_sqla);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sec_sqla);
		sqlbv.put("mContType", mContType);
		sqlbv.put("mSecPayMode", mSecPayMode);
		sqlbv.put("mStation", mStation);
		sqlbv.put("mAgentCode", mAgentCode);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mBranchAttr", mBranchAttr);
		ExeSQL sec_exesqla = new ExeSQL();
		SSRS sec_ssrsa = sec_exesqla.execSQL(sqlbv);
		String[] cols = null;
		if (sec_ssrsa.getMaxRow() > 0) {
			logger.debug("查询结果：共有" + sec_ssrsa.getMaxRow() + "条记录");

			// 对其明细信息进行赋值
			mColnum = sec_ssrsa.getMaxCol();
			for (int i = 1; i <= sec_ssrsa.getMaxRow(); i++) {
				cols = new String[mColnum];
				for (int j = 0; j <= mColnum - 1; j++) {
					if (sec_ssrsa.GetText(i, j + 1) == null
							|| sec_ssrsa.GetText(i, j + 1).equals("null")) {
						cols[j] = " ";
						continue;
					}
					if (j == 4) {
						mSumMoney = mSumMoney
								+ Double.parseDouble(sec_ssrsa
										.GetText(i, j + 1));
					}
					cols[j] = sec_ssrsa.GetText(i, j + 1).trim();
				}
				alistTable.add(cols);
				mCount1 = mCount1 + 1;
			}

			// 向容器中转入相应的数据
			if (mCount1 > 0) {
				logger.debug("开始执行最外部分的循环");
				xmlexport.addDisplayControl("displayinfo");
				// 客户名称
				xmlexport.addListTable(alistTable, cols);

				texttag.add("SysDate", PubFun.getCurrentDate());
				texttag.add("SumMoney", mSumMoney);
				texttag.add("SumAmnt", mCount1);

				if (texttag.size() > 0) {
					xmlexport.addTextTag(texttag);
				}
				// xmlexport.outputDocumentToFile("d:\\", "ReceiptListBL");
				// //输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;
			} else {
				logger.debug("失败=======================数据加入失败再试吧!!!!!！");
				buildError("ReceiptListBL", "无待打印清单！");
				return false;
			}
		} else {
			logger.debug("失败=======================无待打印清单！");
			buildError("ReceiptListBL", "无待打印清单！");
			return false;
		}
	}

	public String[] getSuccFlag(String aBankCode) {
		String[] aSuccFlag = new String[10];
		return aSuccFlag;
	}

	public static void main(String[] args) {
		String aContType = "";
		String aSecPayMode = "";
		String aStation = "";
		String aAgentCode = "";
		String aBranchno = "83210000740";
		String aStartDate = "";
		String aEndDate = "";
		String aBranchAttr = "";
		VData tVData = new VData();
		tVData.addElement(aContType);
		tVData.addElement(aSecPayMode);
		tVData.addElement(aStation);
		tVData.addElement(aAgentCode);
		tVData.addElement(aBranchno);
		tVData.addElement(aStartDate);
		tVData.addElement(aEndDate);
		tVData.addElement(aBranchAttr);

		ReceiptListBL tReceiptListBL = new ReceiptListBL();
		tReceiptListBL.submitData(tVData, "PRINT");
	}

	private void jbInit() throws Exception {
	}
}
