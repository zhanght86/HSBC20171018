package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 加补费日结打印类
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
 * @CreateDate：2005-11-15
 */
public class FinaDayEdorAddFeePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorAddFeePrintBL.class);
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
	public static final String VTS_NAME = "EdorAddFeeDayFina.vts"; // 模板名称

	public FinaDayEdorAddFeePrintBL() {
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
			tError.moduleName = "FinaDayEdorAddFeePrintBL";
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
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 按险种小计
		strsql = "select (select riskname from lmrisk where riskcode = b.riskcode),"
				+ " sum(a.bf),sum(a.lx),count(*) from ("
				+ " select  endorsementno,polno, "
				+ " (case when sum((case when feefinatype = 'BF' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'BF' then getmoney else 0 end))  else 0 end) bf, "
				+ " (case when sum((case when feefinatype = 'LX' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'LX' then getmoney else 0 end))  else 0 end) lx "
				+ " from ljagetendorse j where "
				+ " feefinatype in('BF','LX') and feeoperationtype in('CM','HI','IO','FM','YC') "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " group by j.endorsementno,j.polno "
				+ " union "
				+ " select  endorsementno,polno, "
				+ " (case when sum((case when feefinatype = 'BF' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'BF' then getmoney else 0 end))  else 0 end) bf, "
				+ " (case when sum((case when feefinatype = 'LX' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'LX' then getmoney else 0 end))  else 0 end) lx "
				+ " from ljagetendorse k where "
				+ " feefinatype in('BF','LX') and feeoperationtype = 'RB' "
				+ " and exists(select 'X' from lpedoritem where standbyflag3 in('CM','HI','IO','FM','YC') and edortype = 'RB' and edorno = k.endorsementno)  "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " group by k.endorsementno,k.polno "
				+ " ) a,lcpol b "
				+ " where a.polno = b.polno"
				+ " and (b.grpcontno is null or b.grpcontno = '00000000000000000000')"
				+ " group by b.riskcode ";
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate
					+ "之间没有客户重要资料变更、增补健康告知、职业类别变更、保险期限变更、缴费期限及方式变更的加补费！");
			return false;
		}
		String[] tContListTitle = new String[5];
		for (int iTitle = 0; iTitle < 5; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;
		int j;// 计数
		double riskPrem = 0;
		double riskInte = 0;
		int riskNum = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[5];
			strLine[0] = String.valueOf(i);
			strLine[1] = tssrs.GetText(i, 1);
			strLine[2] = BqNameFun.getRound(tssrs.GetText(i, 2));
			strLine[3] = BqNameFun.getRound(tssrs.GetText(i, 3));
			strLine[4] = tssrs.GetText(i, 4);

			riskPrem += Double.parseDouble(tssrs.GetText(i, 2));
			riskInte += Double.parseDouble(tssrs.GetText(i, 3));
			riskNum += Integer.parseInt(tssrs.GetText(i, 4));
			tContListTable.add(strLine);
		}

		texttag.add("RiskPrem", BqNameFun.getRound(riskPrem));
		texttag.add("RiskInte", BqNameFun.getRound(riskInte));
		texttag.add("RiskNum", riskNum);
		// 按保全项目小计
		double tItemPrem = 0;// 保费合计
		double tItemInte = 0;
		int tItemNum = 0;
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		strsql = "select (select edorname from lmedoritem where edorcode = trim(b.feeoperationtype) and appobj in('I','B') and rownum=1),sum(b.bf),sum(b.lx),count(*) from ("
				+ " select  endorsementno,polno,feeoperationtype, "
				+ " (case when sum((case when feefinatype = 'BF' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'BF' then getmoney else 0 end))  else 0 end) bf, "
				+ " (case when sum((case when feefinatype = 'LX' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'LX' then getmoney else 0 end))  else 0 end) lx "
				+ " from ljagetendorse j where "
				+ " feefinatype in('BF','LX') and feeoperationtype in('CM','HI','IO','FM','YC') "
				+ " and (j.grpcontno is null or j.grpcontno = '00000000000000000000')"
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%')"
				+ " and makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " group by j.endorsementno,j.polno,j.feeoperationtype "
				+ " union "
				+ " select  k.endorsementno,k.polno,k.feeoperationtype, "
				+ " (case when sum((case when k.feefinatype = 'BF' then k.getmoney else 0 end)) is not null then sum((case when k.feefinatype = 'BF' then k.getmoney else 0 end))  else 0 end) bf, "
				+ " (case when sum((case when k.feefinatype = 'LX' then k.getmoney else 0 end)) is not null then sum((case when k.feefinatype = 'LX' then k.getmoney else 0 end))  else 0 end) lx "
				+ " from ljagetendorse k,lpedoritem p where  k.feefinatype in('BF','LX') and k.feeoperationtype = 'RB' "
				+ " and k.endorsementno = p.edorno and p.standbyflag3 in('CM','HI','IO','FM','YC') and p.edortype = 'RB' "
				+ " and (k.grpcontno is null or k.grpcontno = '00000000000000000000')"
				+ " and k.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and k.makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " group by k.endorsementno,k.polno,k.feeoperationtype "
				+ " ) b group by b.feeoperationtype";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = "select (select edorname from lmedoritem where edorcode = trim(b.feeoperationtype) and appobj in('I','B') limit 0,1),sum(b.bf),sum(b.lx),count(*) from ("
					+ " select  endorsementno,polno,feeoperationtype, "
					+ " (case when sum((case when feefinatype = 'BF' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'BF' then getmoney else 0 end))  else 0 end) bf, "
					+ " (case when sum((case when feefinatype = 'LX' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'LX' then getmoney else 0 end))  else 0 end) lx "
					+ " from ljagetendorse j where "
					+ " feefinatype in('BF','LX') and feeoperationtype in('CM','HI','IO','FM','YC') "
					+ " and (j.grpcontno is null or j.grpcontno = '00000000000000000000')"
					+ " and managecom like concat('"
					+ "?mManageCom?"
					+ "','%')"
					+ " and makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " group by j.endorsementno,j.polno,j.feeoperationtype "
					+ " union "
					+ " select  k.endorsementno,k.polno,k.feeoperationtype, "
					+ " (case when sum((case when k.feefinatype = 'BF' then k.getmoney else 0 end)) is not null then sum((case when k.feefinatype = 'BF' then k.getmoney else 0 end))  else 0 end) bf, "
					+ " (case when sum((case when k.feefinatype = 'LX' then k.getmoney else 0 end)) is not null then sum((case when k.feefinatype = 'LX' then k.getmoney else 0 end))  else 0 end) lx "
					+ " from ljagetendorse k,lpedoritem p where  k.feefinatype in('BF','LX') and k.feeoperationtype = 'RB' "
					+ " and k.endorsementno = p.edorno and p.standbyflag3 in('CM','HI','IO','FM','YC') and p.edortype = 'RB' "
					+ " and (k.grpcontno is null or k.grpcontno = '00000000000000000000')"
					+ " and k.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') "
					+ " and k.makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " group by k.endorsementno,k.polno,k.feeoperationtype "
					+ " ) b group by b.feeoperationtype";
		}
		sqlbv2.sql(strsql);
		sqlbv2.put("mManageCom", mManageCom);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv2);
		String[] tTotalListTitle = new String[4];
		for (int tTitle = 0; tTitle < 4; tTitle++) {
			tTotalListTitle[tTitle] = "TOTAL" + String.valueOf(tTitle);
		}
		ListTable tTotalListTable = new ListTable();
		tTotalListTable.setName("Edor");
		String totalLine[] = null;
		int k;// 计数
		for (int t = 1; t <= tssrs.getMaxRow(); t++) {
			totalLine = new String[4];
			totalLine[0] = tssrs.GetText(t, 1);
			totalLine[1] = BqNameFun.getRound(tssrs.GetText(t, 2));
			totalLine[2] = BqNameFun.getRound(tssrs.GetText(t, 3));
			totalLine[3] = tssrs.GetText(t, 4);
			tItemPrem += Double.parseDouble(tssrs.GetText(t, 2));
			tItemInte += Double.parseDouble(tssrs.GetText(t, 3));
			tItemNum += Integer.parseInt(tssrs.GetText(t, 4));
			tTotalListTable.add(totalLine);
		}

		texttag.add("ItemPrem", BqNameFun.getRound(tItemPrem));
		texttag.add("ItemInte", BqNameFun.getRound(tItemInte));
		texttag.add("ItemNum", tItemNum);

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
}
