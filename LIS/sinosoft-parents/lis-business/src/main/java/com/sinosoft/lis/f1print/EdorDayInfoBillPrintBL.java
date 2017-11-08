package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author pst
 * @version 1.0
 * @CreateDate：2009-04-11 清单名称：保全业务日结（清单）
 */

public class EdorDayInfoBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorDayInfoBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorDayInfoBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mMakeDate = "";

	private String mSaleChlSQL = "";

	private String mEdorType = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";

	private String mDayTypeSQL = "";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorDayInfoBillPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorDayInfoBillPrintBL";
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
		// mStartDate = (String) mTransferData.getValueByName("StartDate");
		// if (mStartDate == null || mStartDate.trim().equals("")) {
		// mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
		// return false;
		// }
		// mEndDate = (String) mTransferData.getValueByName("EndDate");
		// if (mEndDate == null || mEndDate.trim().equals("")) {
		// mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
		// return false;
		// }
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mUserCode = (String) mTransferData.getValueByName("UserCode");
		if (mUserCode != null && !"".equals(mUserCode)) {
			mUserCodeSQL = " and  exists (select 'X' from lpedorapp c where c.edoracceptno = a.edoracceptno and c.confoperator= '" + "?mUserCode?" + "')";
		}
		mMakeDate = (String) mTransferData.getValueByName("MakeDate");
		if (mMakeDate != null && !"".equals(mMakeDate)) {
			mEdorTypeSQL = " and exists (select 'X' from lpedorapp where edoracceptno=a.edoracceptno and confdate='"
					+ "?mMakeDate?" + "')";// p.edorappdate = '" + mMakeDate + "'";
		} else {
			mEdorTypeSQL = "";
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType != null && !"".equals(mEdorType)) {
			mDayTypeSQL = " and  edortype in  (select code" + " from ldcode p "
					+ " where p.codetype ='edortypedoc' "
					+ " and p.othersign = '" + "?mEdorType?" + "')";
		} else {
			mDayTypeSQL = " and  edortype in  (select code" + " from ldcode p "
					+ " where p.codetype ='edortypedoc')";
		}
		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		strsql = " select "
				+ "(case (select othersign"
				+ " from ldcode p " // 0-永久,1-长期,2-短期
				+ " where p.codetype ='edortypedoc' "
				+ " and p.code = a.edortype) when '0' then '永久' when '1' then '长期' when '2' then '短期' else '' end) sign,"
//				+ " substr(a.managecom,0,4),"
//				+ " a.managecom mg,"
				+ " a.contno,"
				+ " a.edoracceptno,"
				+ " (select c.edorconfno"
				+ " from lpedorapp c"
				+ " where c.edoracceptno = a.edoracceptno) edorconfno,"
				+ " (select codename from ldcode where codetype='edortype' and code=a.edortype),"
				+ " (case when (select g.sumactupaymoney from ljapay g where g.otherno = a.edoracceptno) is not null then (select g.sumactupaymoney from ljapay g where g.otherno = a.edoracceptno)  else (select (case when sum(g.sumgetmoney) is not null then sum(g.sumgetmoney)  else 0 end) from ljaget g  where g.otherno = (select c.edorconfno "
				+ " from lpedorapp c where c.edoracceptno = a.edoracceptno)) end), "
				+ " a.makedate,"
				+ " (select l.appntname from lccont l where l.contno = a.contno),"
				+ " (select concat(concat(r.phone , ' ') , r.mobile)" + " from lcaddress r"
				+ " where (r.customerno, r.addressno) ="
				+ " (select s.appntno, s.addressno" + " from lcappnt s"
				+ " where s.contno = a.contno))," + " (select f.name"
				+ " from laagent f, lccont b"
				+ " where f.agentcode = b.agentcode"
				+ " and b.contno = a.contno)," + " (select f.mobile"
				+ " from laagent f, lccont b"
				+ " where f.agentcode = b.agentcode"
				+ " and b.contno = a.contno)" + " From lpedoritem a"
				+ " where 1 = 1" + " and a.grpcontno = '00000000000000000000'"
				+ " and a.edorstate in ('0','b')" + " and a.managecom like concat('" //添加保全回退的情况
				+ "?mManageCom?" + "','%')" + mEdorTypeSQL + mUserCodeSQL + mDayTypeSQL
				+ " order by sign,edorconfno ";
		logger.debug("SQL:" + strsql);
		sqlbv.sql(strsql);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mMakeDate", mMakeDate);
		sqlbv.put("mUserCode", mUserCode);
		sqlbv.put("mEdorType", mEdorType);
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间无待打印清单！");
			return false;
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;
		int j;// 计数

		int tGetPayCount = tssrs.getMaxRow();
		double tGetPayMoney = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[12];
			for (j = 0; j < 12; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			// tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 8));
			tContListTable.add(strLine);
		}

		mManageCom = BqNameFun.getCBranch(mManageCom);
		// mStartDate = BqNameFun.getChDate(mStartDate);
		// mEndDate = BqNameFun.getChDate(mEndDate);
		// 模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("MakeDate", mMakeDate);
		// texttag.add("EndDate", mEndDate);
		texttag.add("SumPayCount", tGetPayCount);
		// texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		// tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorDayInfoBill.vts");
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
