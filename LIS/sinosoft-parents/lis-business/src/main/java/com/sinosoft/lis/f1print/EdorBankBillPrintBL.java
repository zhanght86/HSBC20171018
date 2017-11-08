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
 * 清单名称：保全转账清单
 */

public class EdorBankBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorBankBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorBankBill.vts";

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

	private String mOrderSQL = " order by a.managecom,sale,a.operator,edortype ";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorBankBillPrintBL() {
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
			tError.moduleName = "EdorBankBillPrintBL";
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
					+ "where p.contno = a.otherno " + " and p.salechnl = '"
					+ "?mSaleChnl?" + "')";
		}
		mUserCode = (String) mTransferData.getValueByName("UserCode");
		if (mUserCode != null && !"".equals(mUserCode)) {
			mUserCodeSQL = BqNameFun.getWherePart("a.operator", ReportPubFun.getParameterStr(mUserCode, "?mUserCode?"));
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType != null && !"".equals(mEdorType)) {
			mEdorTypeSQL = " and  exists (select 'X'" + " from lpedoritem p "
					+ " where p.contno = a.otherno "
					+ " and p.edoracceptno=a.edoracceptno "
					+ " and p.edortype = '" + "?mEdorType?" + "')";
		} else {
			mEdorTypeSQL = " and  exists (select 'X'"
					+ " from lpedoritem p "
					+ " where p.contno = a.otherno "
					+ " and p.edoracceptno=a.edoracceptno "
					+ " and p.edortype in (select code from ldcode where codetype='edortype' and othersign='1'))";
		}

		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		strsql = " select  a.managecom,"
				+ " (select (select codename from ldcode where code=b.salechnl and codetype='salechnl') from lccont b where b.contno = a.otherno) sale,"
				+ " a.otherno,"
				+ " a.edoracceptno,"
				+ " a.edorconfno,"
				+ " (select distinct edortype"
				+ " from lpedoritem c"
				+ " where c.edoracceptno = a.edoracceptno"
				+ " and c.grpcontno = '00000000000000000000') edortype,"
				+ " a.operator,"
				+ " (select username from lduser where usercode = a.operator),"
				+ " a.makedate,"
				+ " a.confdate,"
				+ " (case when (select g.sumactupaymoney from ljapay g where g.otherno = a.edoracceptno) is not null then (select g.sumactupaymoney from ljapay g where g.otherno = a.edoracceptno)  else (select (case when sum(g.sumgetmoney) is not null then sum(g.sumgetmoney)  else 0 end) from ljaget g  where g.otherno = (select c.edorconfno "
				+ " from lpedorapp c where c.edoracceptno = a.edoracceptno)) end), "
				+ " (select bankname from ldbank where bankcode=a.bankcode),"
				+ " a.bankaccno," + "  a.accname, "
				+ " ((select (select codename" + " from ldcode1"
				+ " where codetype = 'bankerror'" + " and code = r.bankcode"
				+ " and code1 = banksuccflag) failreason"
				+ " from lyreturnfrombankb r"
				+ " where r.paycode = (select s.getnoticeno" + " from ljapay s"
				+ " where s.otherno = a.edoracceptno)) union"
				+ " (select (select codename" + " from ldcode1"
				+ " where codetype = 'bankerror'" + " and code = r.bankcode"
				+ " and code1 = banksuccflag) failreason"
				+ " from lyreturnfrombankb r"
				+ " where r.paycode = (select g.actugetno" + " from ljaget g"
				+ " where g.otherno = a.edorconfno))),"
				+ " ((select i.sendbankcount " + " from ljspay i"
				+ " where i.otherno = a.edoracceptno) union"
				+ " (select o.sendbankcount" + " from ljaget o"
				+ " where o.otherno = a.edorconfno))" + " From lpedorapp a"
				+ " where 1=1" + " and a.confdate >= '" + "?mStartDate?" + "'"
				+ " and a.confdate <= '" + "?mEndDate?" + "'"
				+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + " and"
				+ " a.edorstate in ('0','b')" + " and a.payform = '4'" + mSaleChlSQL
				+ mEdorTypeSQL + mUserCodeSQL + mOrderSQL;
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mEdorType", mEdorType);
		sqlbv.put("mUserCode", mUserCode);
		
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
			strLine = new String[16];
			for (j = 1; j < 16; j++) {
				strLine[0]=String.valueOf(i);
				strLine[j] = tssrs.GetText(i, j );
			}
			tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 11));
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
		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorBankBill.vts");
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
