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
 * Description:退费日结打印类
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
public class FinaDayEdorRtnFeePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorRtnFeePrintBL.class);
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
	public static final String VTS_NAME = "EdorRtnFeeDayFina.vts"; // 模板名称

	public FinaDayEdorRtnFeePrintBL() {
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
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 按险种小计
		strsql = "select (select riskname from lmrisk where riskcode = b.riskcode),"
				+ " (case when b.salechnl = '3' then '代理' else (case when b.salechnl='2' then '团体' else '个人' end) end), "
				+ " substr(b.agentcom,1,2),(case when b.payintv='0' then '' else trim(cast(b.payyears as char(20))) end),-1*sum(a.bf),count(*) from "
				+ " (select  endorsementno,polno, "
				+ " (case when sum(getmoney) is not null then sum(getmoney)  else 0 end) bf "
				+ " from ljagetendorse j where "
				+ " feefinatype = 'TF' "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " group by j.endorsementno,j.polno "
				+ " ) a,lcpol b "
				+ " where a.polno = b.polno "
				+ " and (b.grpcontno = '00000000000000000000' or b.grpcontno is null)"
				+ " group by b.riskcode,b.salechnl,substr(b.agentcom,1,2),(case when b.payintv='0' then '' else trim(cast(b.payyears as char(20))) end) ";
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有退费！");
			return false;
		}
		String[] tContListTitle = new String[7];
		for (int iTitle = 0; iTitle < 7; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;
		int j;// 计数
		double riskPrem = 0;
		int riskNum = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[7];
			strLine[0] = String.valueOf(i);
			strLine[1] = tssrs.GetText(i, 1);
			strLine[2] = tssrs.GetText(i, 2);
			strLine[3] = tssrs.GetText(i, 3);
			strLine[4] = tssrs.GetText(i, 4);
			strLine[5] = BqNameFun.getPartRound(tssrs.GetText(i, 5));
			strLine[6] = tssrs.GetText(i, 6);

			riskPrem += Double.parseDouble(tssrs.GetText(i, 5));
			riskNum += Integer.parseInt(tssrs.GetText(i, 6));
			tContListTable.add(strLine);
		}

		String tRiskPrem = BqNameFun.getPartRound(riskPrem);
		texttag.add("RiskPrem", tRiskPrem);
		texttag.add("RiskNum", riskNum);
		// 按保全项目小计
		double tItemPrem = 0;// 保费合计
		int tItemNum = 0;
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = "select (select edorname from lmedoritem where edorcode = trim(b.feeoperationtype) and appobj in('I','B') and rownum=1),-1*sum(b.bf),count(*) from ( "
					+ " select  endorsementno,polno,feeoperationtype, "
					+ " (case when sum(getmoney) is not null then sum(getmoney)  else 0 end) bf "
					+ " from ljagetendorse j where "
					+ " feefinatype = 'TF' "
					+ " and (j.grpcontno = '00000000000000000000' or j.grpcontno is null)"
					+ " and managecom like concat('"
					+ "?mManageCom?"
					+ "','%') "
					+ " and makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " group by j.endorsementno,j.polno,j.feeoperationtype "
					+ " ) b group by b.feeoperationtype ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = "select (select edorname from lmedoritem where edorcode = trim(b.feeoperationtype) and appobj in('I','B') limit 0,1),-1*sum(b.bf),count(*) from ( "
					+ " select  endorsementno,polno,feeoperationtype, "
					+ " (case when sum(getmoney) is not null then sum(getmoney)  else 0 end) bf "
					+ " from ljagetendorse j where "
					+ " feefinatype = 'TF' "
					+ " and (j.grpcontno = '00000000000000000000' or j.grpcontno is null)"
					+ " and managecom like concat('"
					+ "?mManageCom?"
					+ "','%') "
					+ " and makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " group by j.endorsementno,j.polno,j.feeoperationtype "
					+ " ) b group by b.feeoperationtype ";
		}
		sqlbv2.sql(strsql);
		sqlbv2.put("mManageCom", mManageCom);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv2);
		String[] tTotalListTitle = new String[3];
		for (int tTitle = 0; tTitle < 3; tTitle++) {
			tTotalListTitle[tTitle] = "TOTAL" + String.valueOf(tTitle);
		}
		ListTable tTotalListTable = new ListTable();
		tTotalListTable.setName("Edor");
		String totalLine[] = null;
		int k;// 计数
		for (int t = 1; t <= tssrs.getMaxRow(); t++) {
			totalLine = new String[3];
			totalLine[0] = tssrs.GetText(t, 1);
			totalLine[1] = BqNameFun.getPartRound(tssrs.GetText(t, 2));
			totalLine[2] = tssrs.GetText(t, 3);
			tItemPrem += Double.parseDouble(tssrs.GetText(t, 2));
			tItemNum += Integer.parseInt(tssrs.GetText(t, 3));
			tTotalListTable.add(totalLine);
		}
		String ItemPrem = BqNameFun.getPartRound(tItemPrem);

		texttag.add("ItemPrem", ItemPrem);
		texttag.add("ItemNum", tItemNum);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		strsql = "select lj.feefinatype,(case when sum(lj.getmoney) is not null then sum(lj.getmoney)  else 0 end),count(*) from ("
				+ " select endorsementno,polno,feefinatype,getmoney "
				+ " from ljagetendorse a where 1=1 "
				+ " and exists(select 'X' from lpedoritem where edorno = a.endorsementno and standbyflag1 = '1') "
				+ " and feeoperationtype = 'CT' "// 减保在未清偿的情况下是不受理操作的
				+ " and feefinatype = 'GB' " + " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " union "
				+ " select  k.endorsementno,k.polno,k.feefinatype,k.getmoney "
				+ " from ljagetendorse k,lpedoritem p where 1=1 "
				+ " and exists(select 'X' from lpedoritem where edoracceptno = p.standbyflag1 and edortype = 'CT' and standbyflag1 = '1') "
				+ " and k.feefinatype = 'GB' "
				+ " and k.endorsementno = p.edorno and p.standbyflag3 = 'CT' and p.edortype = 'RB'  "
				// + " and p.managecom like '"+mManageCom+"%' "
				+ " and k.feeoperationtype = 'RB' "
				+ " and k.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and k.makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " ) lj group by lj.feefinatype ";
		sqlbv3.sql(strsql);
		sqlbv3.put("mManageCom", mManageCom);
		sqlbv3.put("mStartDate", mStartDate);
		sqlbv3.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv3);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			tXmlExport.addDisplayControl("displayContent");
			texttag.add("GBFee", BqNameFun.getRound(tssrs.GetText(1, 2)));
			texttag.add("GBNum", tssrs.GetText(1, 3));
		}

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
