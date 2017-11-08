package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:团体退费日结打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：QianLy
 * @version：1.0
 * @CreateDate：2006-12-05 Modified By QianLy on 2006-12-20
 */
public class GrpFinaDayEdorRtnFeePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpFinaDayEdorRtnFeePrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String mManageCom = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mCurrentDate = PubFun.getCurrentDate();// 当前时间
	public static final String VTS_NAME = "GrpEdorDayFinaRtnFee.vts";// 模板名称

	public GrpFinaDayEdorRtnFeePrintBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinaDayEdorRtnFeePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
			return false;
		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
			return false;
		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = mGlobalInput.ManageCom;
		}
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();
		Hashtable riskTable = new Hashtable();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 按险种小计
		strsql = "select (select riskname from lmrisk where riskcode = b.riskcode),"
				+ " b.grpcontno,"
				+ " (select signreportno from lcgrpcont where grpcontno =  b.grpcontno),"
				+ " (case when b.payintv = '0' then '趸交' else to_char(b.payyears) end),"
				+ " -1*sum(a.bf),"
				+ " count(distinct a.endorsementno)"
				+ " from (select endorsementno, polno, nvl(sum(getmoney), 0) bf"
				+ "         from ljagetendorse j"
				+ "        where feefinatype = 'TF'"
				+ "          and managecom like '"
				+ mManageCom
				+ "%'"
				+ "          and makedate between '"
				+ mStartDate
				+ "' and '"
				+ mEndDate
				+ "'"
				+ "          group by j.endorsementno, j.polno) a,"
				+ " lcpol b"
				+ " where a.polno = b.polno"
				+ "   and b.grpcontno is not null"
				+ "   and b.grpcontno <> '00000000000000000000'"
				+ "   group by b.riskcode,"
				+ "   b.grpcontno,"
				+ "   substr(b.agentcom, 1, 2),"
				+ "   (case when b.payintv = '0' then '趸交' else to_char(b.payyears) end)"
				+ "";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有团体退费！");
			return false;
		}

		int VTS_COLUMN_RISK = 7;// 模板中按险种
		String[] tContListTitle = new String[VTS_COLUMN_RISK];
		for (int iTitle = 0; iTitle < VTS_COLUMN_RISK; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;

		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[VTS_COLUMN_RISK];
			strLine[0] = String.valueOf(i);
			strLine[1] = tssrs.GetText(i, 1);
			strLine[2] = tssrs.GetText(i, 2);
			strLine[3] = tssrs.GetText(i, 3);
			strLine[4] = tssrs.GetText(i, 4);
			strLine[5] = BqNameFun.getRound(tssrs.GetText(i, 5));
			strLine[6] = tssrs.GetText(i, 6);

			// 添加到小计处理
			if (!BqNameFun.dealHashTable(riskTable, "RiskPrem", strLine[5])// 按险种统计保费
					|| !BqNameFun.dealHashTable(riskTable, "RiskNum",
							strLine[6]) // 按险种统计件数
			) {
				return false;
			}
			tContListTable.add(strLine);
		}
		texttag.add("RiskPrem", BqNameFun.getRoundMoney(riskTable
				.get("RiskPrem") == null ? "0.00" : (String) riskTable
				.get("RiskPrem")));
		texttag.add("RiskNum", BqNameFun.formatDoubleToInt(riskTable
				.get("RiskNum") == null ? "0" : BqNameFun
				.getRound((String) riskTable.get("RiskNum"))));

		// 按保全项目小计－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

		strsql = "select (select edorname" + "          from lmedoritem"
				+ "         where edorcode = trim(b.feeoperationtype)"
				+ "           and appobj = 'G'" + "           and rownum = 1),"
				+ " -1 * sum(b.bf)," + " count(distinct b.endorsementno)"
				+ " from (select endorsementno,"
				+ "              feeoperationtype,"
				+ "              nvl(sum(getmoney), 0) bf"
				+ "         from ljagetendorse j"
				+ "        where feefinatype = 'TF'"
				+ "          and j.grpcontno is not null"
				+ "          and j.grpcontno <> '00000000000000000000'"
				+ "          and managecom like '" + mManageCom + "%'"
				+ "          and makedate between '" + mStartDate + "' and '"
				+ mEndDate + "'"
				+ "          group by j.endorsementno, j.feeoperationtype) b"
				+ " group by b.feeoperationtype" + "";
		tssrs.Clear();
		tssrs = texesql.execSQL(strsql);
		int VTS_COLUMN_EDOR = 3;
		String[] tTotalListTitle = new String[VTS_COLUMN_EDOR];
		for (int tTitle = 0; tTitle < VTS_COLUMN_EDOR; tTitle++) {
			tTotalListTitle[tTitle] = "TOTAL" + String.valueOf(tTitle);
		}
		ListTable tTotalListTable = new ListTable();
		tTotalListTable.setName("Edor");
		String totalLine[] = null;
		for (int t = 1; t <= tssrs.getMaxRow(); t++) {
			totalLine = new String[VTS_COLUMN_EDOR];
			totalLine[0] = tssrs.GetText(t, 1);
			totalLine[1] = BqNameFun.getRound(tssrs.GetText(t, 2));
			totalLine[2] = tssrs.GetText(t, 3);
			// 添加到小计处理
			if (!BqNameFun.dealHashTable(riskTable, "ItemPrem", totalLine[1])
					|| !BqNameFun.dealHashTable(riskTable, "ItemNum",
							totalLine[2])) {
				return false;
			}
			tTotalListTable.add(totalLine);
		}
		texttag.add("ItemPrem", BqNameFun.getRoundMoney(riskTable
				.get("ItemPrem") == null ? "0.00" : (String) riskTable
				.get("ItemPrem")));
		texttag.add("ItemNum", BqNameFun.formatDoubleToInt(riskTable
				.get("ItemNum") == null ? "0" : BqNameFun
				.getRound((String) riskTable.get("ItemNum"))));

		String tManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getFDate(mStartDate);
		mEndDate = BqNameFun.getFDate(mEndDate);
		mCurrentDate = BqNameFun.getFDate(mCurrentDate);
		// 模版自上而下的元素
		texttag.add("ManageCom", tManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("CurrentDate", mCurrentDate);
		texttag.add("CurrentTime", StrTool.getHour() + "时"
				+ StrTool.getMinute() + "分" + StrTool.getSecond() + "秒");
		texttag.add("Operator", CodeNameQuery
				.getOperator(mGlobalInput.Operator));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);
		tXmlExport.addListTable(tTotalListTable, tTotalListTitle);
		// tXmlExport.outputDocumentToFile("d:\\", "P001260"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void jbInit() throws Exception {
	}
}
