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
 * Description: 加保日结打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-11-14
 */
public class GrpFinaDayEdorWSWAPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpFinaDayEdorWSWAPrintBL.class);
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
	private String mCurrentDate = PubFun.getCurrentDate(); // 当前时间
	public static final String VTS_NAME = "GrpEdorDayFinaWSWA.vts"; // 模板名称

	public GrpFinaDayEdorWSWAPrintBL() {
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
			tError.moduleName = "GrpFinaDayEdorWSWAPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
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

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();
		Hashtable riskTable = new Hashtable();
		Hashtable tHashtable = new Hashtable();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 按险种小计
		strsql = "select (select riskname from lmrisk where riskcode = a.riskcode),"
				+ " a.grpcontno,"
				+ " (select signreportno from lcgrpcont where grpcontno =a.grpcontno) qbh,"
				+ " sum(a.bf),"
				+ " sum(a.lx),"
				+ " count(distinct a.endorsementno)"
				+ " from ( select p.getnoticeno endorsementno,"
				+ " p.riskcode,"
				+ " p.grpcontno,"
				+ " nvl(sum(p.sumactupaymoney),0) bf, "
				+ " (select nvl(sum(getmoney), 0) from ljagetendorse j where j.getnoticeno = p.getnoticeno) lx "
				+ " from ljapayperson p"
				+ " where 1=1"
				+ " and p.grppolno <> '00000000000000000000'"
				+ " and p.paytype in ('NI','WS','WA')"
				+ " and p.grpcontno in (select grpcontno from lpgrpedoritem where (edortype = 'NI' or edortype = 'WS' or edortype = 'WA') and edorstate = '0')"
				+ " and p.managecom like '"
				+ mManageCom
				+ "%' "
				+ " and p.confdate between '"
				+ mStartDate
				+ "' and '"
				+ mEndDate
				+ "' "
				+ " group by p.getnoticeno, p.riskcode, p.grpcontno) a"
				+ " group by a.riskcode,a.grpcontno";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有团体加保！");
			return false;
		}

		int VTS_COLUUM_RISK = 7;// 模板中按险种统计的表格列数

		String[] tContListTitle = new String[VTS_COLUUM_RISK];
		for (int iTitle = 0; iTitle < VTS_COLUUM_RISK; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}
		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			for (int i = 1; i <= tssrs.getMaxRow(); i++) {
				strLine = new String[VTS_COLUUM_RISK];
				strLine[0] = String.valueOf(i);
				strLine[1] = tssrs.GetText(i, 1);
				strLine[2] = tssrs.GetText(i, 2);
				strLine[3] = tssrs.GetText(i, 3);
				strLine[4] = BqNameFun.getRound(tssrs.GetText(i, 4));
				strLine[5] = BqNameFun.getRound(tssrs.GetText(i, 5));
				strLine[6] = tssrs.GetText(i, 6);

				if (!BqNameFun
						.dealHashTable(tHashtable, "RiskPrem", strLine[4])
						|| !BqNameFun.dealHashTable(tHashtable, "RiskInte",
								strLine[5])
						|| !BqNameFun.dealHashTable(tHashtable, "RiskCount",
								strLine[6])) {
					return false;
				}
				tContListTable.add(strLine);
			}
		}
		texttag.add("RiskPrem", BqNameFun.chgMoney((String) tHashtable
				.get("RiskPrem") == null ? "0.00" : (String) tHashtable
				.get("RiskPrem")));
		texttag.add("RiskInte", BqNameFun.chgMoney((String) tHashtable
				.get("RiskInte") == null ? "0.00" : (String) tHashtable
				.get("RiskInte")));
		texttag.add("RiskCount",
				BqNameFun.formatDoubleToInt((String) tHashtable
						.get("RiskCount") == null ? "0" : BqNameFun
						.getRound((String) tHashtable.get("RiskCount"))));

		strsql = "select (select edorname from lmedoritem where edorcode = trim(b.feeoperationtype) and appobj = 'G' and rownum = 1) en,"
				+ " sum(b.bf), sum(b.lx),count(distinct b.endorsementno)"
				+ " from (select p.getnoticeno endorsementno,"
				+ " p.grpcontno grpcontno,"
				+ " p.paytype feeoperationtype,"
				+ " nvl(sum(p.sumactupaymoney),0) bf, "
				+ " (select nvl(sum(getmoney), 0) from ljagetendorse j where j.getnoticeno = p.getnoticeno) lx "
				+ " from ljapayperson p"
				+ " where 1=1"
				+ " and p.grppolno <> '00000000000000000000'"
				+ " and p.paytype in ('NI','WS','WA')"
				+ " and p.grpcontno in (select grpcontno from lpgrpedoritem where edortype = 'NI' and edorstate = '0')"
				+ " and p.managecom like '"
				+ mManageCom
				+ "%' "
				+ " and p.confdate between '"
				+ mStartDate
				+ "' and '"
				+ mEndDate
				+ "' "
				+ " group by p.getnoticeno,p.grpcontno,p.paytype) b"
				+ " group by b.feeoperationtype";

		tssrs.Clear();
		tssrs = texesql.execSQL(strsql);

		int VTS_COLUMN_ITEM = 4;// 模板中按保全项目统计的表格列数

		String[] tTotalListTitle = new String[VTS_COLUMN_ITEM];
		for (int tTitle = 0; tTitle < VTS_COLUMN_ITEM; tTitle++) {
			tTotalListTitle[tTitle] = "TOTAL" + String.valueOf(tTitle);
		}
		ListTable tTotalListTable = new ListTable();
		tTotalListTable.setName("ITEM");
		String totalLine[] = null;
		for (int t = 1; t <= tssrs.getMaxRow(); t++) {
			totalLine = new String[VTS_COLUMN_ITEM];
			totalLine[0] = tssrs.GetText(t, 1);
			totalLine[1] = BqNameFun.getRound(tssrs.GetText(t, 2));
			totalLine[2] = BqNameFun.getRound(tssrs.GetText(t, 3));
			totalLine[3] = tssrs.GetText(t, 4);
			if (!BqNameFun.dealHashTable(tHashtable, "ITEMPrem", totalLine[1])
					|| !BqNameFun.dealHashTable(tHashtable, "ITEMInte",
							totalLine[2])
					|| !BqNameFun.dealHashTable(tHashtable, "ITEMCount",
							totalLine[3])) {
				return false;
			}
			tTotalListTable.add(totalLine);
		}
		texttag.add("ITEMPrem", BqNameFun.chgMoney((String) tHashtable
				.get("ITEMPrem") == null ? "0.00" : (String) tHashtable
				.get("ITEMPrem")));
		texttag.add("ITEMInte", BqNameFun.chgMoney((String) tHashtable
				.get("ITEMInte") == null ? "0.00" : (String) tHashtable
				.get("ITEMInte")));
		texttag.add("ITEMCount",
				BqNameFun.formatDoubleToInt((String) tHashtable
						.get("ITEMCount") == null ? "0" : BqNameFun
						.getRound((String) tHashtable.get("ITEMCount"))));

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
}
