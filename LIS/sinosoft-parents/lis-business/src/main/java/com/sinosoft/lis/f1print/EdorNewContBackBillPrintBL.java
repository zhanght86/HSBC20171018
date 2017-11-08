package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;

/** 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author pst
 * @version 1.0
 * @CreateDate：2009-04-11
 * 清单名称：新契约回访数据提取
 */

public class EdorNewContBackBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorNewContBackBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorNewContBackBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mRiskCode = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mRiskCodeSQL = "";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorNewContBackBillPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作 
	 * @return: boolean */
	public boolean submitData(VData cInputData, String cOperate) {
		//将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		//将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorNewContBackBillPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		//得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		//准备需要打印的数据
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
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		//处理渠道
		if (mSaleChnl != null && !"".equals(mSaleChnl)) {
			mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
					+ "where p.contno = c.contno " + " and p.salechnl = '"
					+ mSaleChnl + "')";
		}

		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		if (mRiskCode != null && !"".equals(mRiskCode)) {
			mRiskCodeSQL = "and c.riskcode='"+mRiskCode+"'";
		}	

		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " select /*+ INDEX (c IDX_LCPOL_SIGNDATE) */"
					+ " rownum,"
					+ " riskcode,"
					+ " (select codename"
					+ " from ldcode"
					+ " where codetype = 'salechnl'"
					+ " and code = c.salechnl),"
					+ " (select (case RiskType3 when '1' then '传统险' when '2' then '分红险' when '3' then '投连险' when '4' then '万能险' else '其他' end)"
					+ " from lmriskapp"
					+ " where riskcode = c.riskcode),"
					+ " contno,"
					//+ " polno,"
					+ " appntname,"
					+ " (select (case Appntsex when '0' then '男' when '1' then '女' end)"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select  (select codename from ldcode where codetype='idtype' and code=Idtype)"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select Idno"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select Appntbirthday"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select r.mobile"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					//add by jiaqiangli 三个电话的修改
					+ " (select r.homephone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select r.phone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select r.postaladdress"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select r.zipcode"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select name from laagent where agentcode = c.agentcode),"
					+ " c.agentcode,"
					+ " c.insuredname,"
					+ " c.insuredappage,"
					+ " c.amnt,"
					+ " c.prem,"
					+ " c.cvalidate,"
					+ " (select name from lacom where agentcom = c.agentcom),"
					+ " (case c.payintv when -1 then '不定期' else (case c.payintv when 0 then '趸交' else '期交' end) end),"
					+ " c.remark," + " (select (case count(*) when 0 then '' else '#' end)"
					+ " from lccspec" + " where contno = c.contno"
					+ " and SpecContent is not null)" + " from lcpol c"
					+ " where appflag = '1'"
					+ " and grppolno = '00000000000000000000'"
					+ " and SignDate >= '" + "?mStartDate?" + "'"
					+ " and SignDate <= '" + "?mEndDate?" + "'"
					+ " and c.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+   mRiskCodeSQL
					+ " and not exists (select 1 from Lcrnewstatehistory b where b.contno = c.contno )";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select (@rownum := @rownum + 1) AS rownum,t.* from (select "
					+ " @rownum:=0,"
					+ " riskcode,"
					+ " (select codename"
					+ " from ldcode"
					+ " where codetype = 'salechnl'"
					+ " and code = c.salechnl),"
					+ " (select (case RiskType3 when '1' then '传统险' when '2' then '分红险' when '3' then '投连险' when '4' then '万能险' else '其他' end)"
					+ " from lmriskapp"
					+ " where riskcode = c.riskcode),"
					+ " contno,"
					//+ " polno,"
					+ " appntname,"
					+ " (select (case Appntsex when '0' then '男' when '1' then '女' end)"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select  (select codename from ldcode where codetype='idtype' and code=Idtype)"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select Idno"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select Appntbirthday"
					+ " from LCAppnt"
					+ " where contno = c.contno"
					+ " and appntno = c.AppntNo),"
					+ " (select r.mobile"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					//add by jiaqiangli 三个电话的修改
					+ " (select r.homephone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select r.phone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select r.postaladdress"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select r.zipcode"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = c.contno)),"
					+ " (select name from laagent where agentcode = c.agentcode),"
					+ " c.agentcode,"
					+ " c.insuredname,"
					+ " c.insuredappage,"
					+ " c.amnt,"
					+ " c.prem,"
					+ " c.cvalidate,"
					+ " (select name from lacom where agentcom = c.agentcom),"
					+ " (case c.payintv when -1 then '不定期' else (case c.payintv when 0 then '趸交' else '期交' end) end),"
					+ " c.remark," + " (select (case count(*) when 0 then '' else '#' end)"
					+ " from lccspec" + " where contno = c.contno"
					+ " and SpecContent is not null)" + " from lcpol c"
					+ " where appflag = '1'"
					+ " and grppolno = '00000000000000000000'"
					+ " and SignDate >= '" + "?mStartDate?" + "'"
					+ " and SignDate <= '" + "?mEndDate?" + "'"
					+ " and c.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+   mRiskCodeSQL
					+ " and not exists (select 1 from Lcrnewstatehistory b where b.contno = c.contno ) ) t";
		}
		
		logger.debug("SQL:" + strsql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间无待打印清单！");
			return false;
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;
		int j;//计数

		int tGetPayCount = tssrs.getMaxRow();
		double tGetPayMoney = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[25];
			for (j = 0; j < 25; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			tContListTable.add(strLine);
		}

		mManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getChDate(mStartDate);
		mEndDate = BqNameFun.getChDate(mEndDate);
		//模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("SumPayCount", tGetPayCount);
//		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorNewContBackBill.vts"); 
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
