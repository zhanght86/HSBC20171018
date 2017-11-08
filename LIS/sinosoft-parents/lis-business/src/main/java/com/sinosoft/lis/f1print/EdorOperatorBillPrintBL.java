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
 * 清单名称：保全人员统计
 */

public class EdorOperatorBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorOperatorBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorOperatorBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorOperatorBillPrintBL() {
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
			tError.moduleName = "EdorOperatorBillPrintBL";
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
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType != null && !"".equals(mEdorType)) {
			mEdorTypeSQL = " and a.usertype='"+"?mEdorType?"+"'";
		} 
		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		//modify by jiaqiangli 2009-10-27 保全员的所属机构请使用lduser.comcode而不要使用ldedoruser.managecom
		//ldedoruser.managecom为授权的操作员的登录机构
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " select rownum,(select comcode from lduser where usercode=a.usercode) tmanagecom,"
					+ " (select username from lduser b where b.usercode = a.usercode),"
					+ " (select username from lduser b where b.usercode = a.usercode),"
					+ " usercode," + " edorpopedom,"
					+ " (case usertype when '1' then '个险保全' when '2' then '团险保全' else '' end),"
					+ " validstartdate" + " from ldedoruser a"
					+ " where  a.validstartdate>= '" + "?mStartDate?" + "'"
					+ " and a.validstartdate<= '" + "?mEndDate?" + "'"
					//comment by jiaqiangli 2009-10-27 加上登录机构的权限控制 前台传入的mManageCom已作控制
					+ " and exists (select 1 from lduser c where c.usercode=a.usercode and c.comcode like concat('" + "?mManageCom?" + "','%')) " 
					+ mEdorTypeSQL ;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select (@rownum := @rownum + 1) AS rownum,tmanagecom,"
					+ " u_code1,"
					+ " u_code2,"
					+ " usercode," 
					+ " edorpopedom,"
					+ " usertype,"
					+ " validstartdate" 
					+ " from (select @rownum:= 0,(select comcode from lduser where usercode=a.usercode) tmanagecom,"
					+ " (select username from lduser b where b.usercode = a.usercode) u_code1,"
					+ " (select username from lduser b where b.usercode = a.usercode) u_code2,"
					+ " usercode," 
					+ " edorpopedom,"
					+ " (case usertype when '1' then '个险保全' when '2' then '团险保全' else '' end) usertype,"
					+ " validstartdate"
					+ " from ldedoruser  a "
					+ " where  a.validstartdate>= '" + "?mStartDate?" + "'"
					+ " and a.validstartdate<= '" + "?mEndDate?" + "'"
					//comment by jiaqiangli 2009-10-27 加上登录机构的权限控制 前台传入的mManageCom已作控制
					+ " and exists (select 1 from lduser c where c.usercode=a.usercode and c.comcode like concat('" + "?mManageCom?" + "','%')) " 
					+ mEdorTypeSQL +") t";
		}
		sqlbv.sql(strsql);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mEdorType", mEdorType);
		
		logger.debug("SQL:" + strsql);
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
			strLine = new String[8];
			for (j = 0; j < 8; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			//tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 8));
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
		//texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorOperatorBill.vts"); 
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
