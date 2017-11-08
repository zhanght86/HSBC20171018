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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全自垫清偿日结打印类
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
 * @CreateDate：2005-09-29
 */
public class FinaDayEdorTRPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorTRPrintBL.class);
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
	public static final String VTS_NAME = "EdorTRDayFina.vts"; // 模板名称

	public FinaDayEdorTRPrintBL() {
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
			tError.moduleName = "FinaDayEdorTRPrintBL";
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

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";

		// strsql = "select nvl(substr(b.agentcom,0,2),''),(select riskname from
		// lmrisk where riskcode = b.riskcode),(case when b.payintv='0' then ''
		// else to_char(b.payyears) end),"
		// + " nvl(sum((select sum(getmoney) from ljagetendorse where
		// endorsementno = a.edorno and feeoperationtype = a.edortype and polno
		// = a.polno and feefinatype = 'HK')),0),"
		// + " nvl(sum((select sum(getmoney) from ljagetendorse where
		// endorsementno = a.edorno and feeoperationtype = a.edortype and polno
		// = a.polno and feefinatype = 'LX')),0) "
		// + " from lpedoritem a,lcpol b where "
		// + " edortype = 'TR' and edoracceptno in(select otherno from ljapay
		// where othernotype = '10' "
		// + " and confdate between '"+mStartDate+"' and '"+mEndDate+"') and
		// a.polno = b.polno "
		// + " and a.managecom like '"+mManageCom+"%' "
		// + " group by b.agentcom,b.riskcode,(case when b.payintv='0' then ''
		// else to_char(b.payyears) end) ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		strsql = "select (case when substr(b.agentcom,1,2) is not null then substr(b.agentcom,1,2)  else '' end),(select riskname from lmrisk where riskcode = b.riskcode),(case when b.payintv='0' then '' else trim(cast(b.payyears as char(20))) end),"
				+ " sum(a.hk),sum(a.lx) from ("
				+ " select  endorsementno,polno, "
				+ " (case when sum((case when feefinatype = 'HK' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'HK' then getmoney else 0 end))  else 0 end)  hk, "
				+ " (case when sum((case when feefinatype = 'LX' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'LX' then getmoney else 0 end))  else 0 end) lx "
				+ " from ljagetendorse j where "
				+ " feefinatype in('HK','LX') and feeoperationtype = 'TR' "
				+ " and (j.grpcontno = '00000000000000000000' or j.grpcontno is null)"
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
				+ " (case when sum((case when feefinatype = 'HK' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'HK' then getmoney else 0 end))  else 0 end) hk, "
				+ " (case when sum((case when feefinatype = 'LX' then getmoney else 0 end)) is not null then sum((case when feefinatype = 'LX' then getmoney else 0 end))  else 0 end) lx "
				+ " from ljagetendorse k where "
				+ " feefinatype in('HK','LX') and feeoperationtype = 'RB' "
				+ " and exists(select 'X' from lpedoritem where standbyflag3 = 'TR' and edortype = 'RB' and edorno = k.endorsementno)  "
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
				+ " where a.polno = b.polno group by (case when substr(b.agentcom,1,2) is not null then substr(b.agentcom,1,2)  else '' end),b.riskcode,b.riskcode,(case when b.payintv='0' then '' else trim(cast(b.payyears as char(20))) end) ";
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间未发生保费自垫清偿！");
			return false;
		}
		String[] tContListTitle = new String[6];
		for (int iTitle = 0; iTitle < 6; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("AG");
		String strLine[] = null;
		int j;// 计数
		double dayPrem = 0;
		double dayInterest = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[6];
			strLine[0] = String.valueOf(i);
			for (j = 1; j < 4; j++) {
				strLine[j] = tssrs.GetText(i, j);
			}
			for (j = 4; j < 6; j++) {
				strLine[j] = BqNameFun.getRound(tssrs.GetText(i, j));
			}
			dayPrem += Double.parseDouble(tssrs.GetText(i, 4));
			dayInterest += Double.parseDouble(tssrs.GetText(i, 5));
			tContListTable.add(strLine);
		}

		String tDayPrem = BqNameFun.getRound(dayPrem);
		String tDayInterest = BqNameFun.getRound(dayInterest);

		// String tManageCom = BqNameFun.getCBranch(mGlobalInput.ManageCom);
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
		texttag.add("DayPrem", tDayPrem);
		texttag.add("DayInterest", tDayInterest);
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
