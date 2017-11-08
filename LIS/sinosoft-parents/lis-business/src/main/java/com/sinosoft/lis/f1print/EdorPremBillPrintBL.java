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
 * 清单名称：保全费用清单
 */

public class EdorPremBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorPremBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorPremBill.vts";

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

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorPremBillPrintBL() {
		
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
			tError.moduleName = "EdorPremBillPrintBL";
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
		}else //没有输入则查询所有需要可能补退费的项目
		{
			mEdorTypeSQL = " and  exists (select 'X'" + " from lpedoritem p "
			+ " where p.contno = a.otherno "
			+ " and p.edoracceptno=a.edoracceptno "
			+ " and p.edortype in  (select code from ldcode where codetype='edortype' and othersign='1'))";
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
				+ " (select (select codename from ldcode where code=b.salechnl and codetype='salechnl') from lccont b where b.contno = a.otherno),"
				+ " a.otherno," + " a.edoracceptno," + " a.edorconfno,"
				+ " (select (select codename from ldcode where codetype='edortype' and code=c.edortype) " + " from lpedoritem c"
				+ " where c.edoracceptno = a.edoracceptno"
				+ " ) edortype,"
				+ " (select (case when sum(e.sumactupaymoney) is not null then sum(e.sumactupaymoney)  else 0 end)" + " from ljapay e"
				+ " where e.otherno = a.edoracceptno) pay,"
				+ " (select (case when sum(g.sumgetmoney) is not null then sum(g.sumgetmoney)  else 0 end)" + " from ljaget g"
				+ " where g.otherno = a.edorconfno) get," + " (select codename"
				+ " from ldcode" + " where codetype = 'edorgetpayform'"
				+ " and code = a.getform)," + " a.operator,"
				+ " (select username from lduser where usercode = a.operator),"
				+ " a.makedate,"
				+" (case "
				+" when  (select sum(g.sumgetmoney) "
				+"	from ljaget g "
				+" where g.otherno = a.edorconfno)  is not null then "
				+"	0 "
				+" when  (select sum(e.sumactupaymoney) "
				+"	from ljapay e "
				+" where e.otherno = a.edoracceptno) is not null then "
				+"	1 "
				+" else "
				+"	-1 "
				+" end)"
				+ " From lpedorapp a"
				+ " where 1=1" 
				+ " and a.confdate >= '" + "?mStartDate?" + "'"
				+ " and a.confdate <= '" + "?mEndDate?" + "'"
				+ " and a.managecom like concat('" + "?mManageCom?" + "','%')"
				+ " and a.edorstate in ('0','b') and othernotype='3'" + mSaleChlSQL + mEdorTypeSQL
				+ mUserCodeSQL+" order by a.managecom,a.operator,edortype";
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mEdorType", mEdorType);
		sqlbv.put("mManageCom", mManageCom);
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

		int tCount = tssrs.getMaxRow();
        int tGetPayCount=0;
        double tGetPayMoney=0;
        int tGetGetCount=0;
        double tGetGetMoney=0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[13];
			for (j = 1; j < 13; j++) {
				strLine[0]=String.valueOf(i);
				strLine[j] = tssrs.GetText(i, j);
			}
			//付费 Get
			if("0".equals(tssrs.GetText(i, 13)))
			{
				tGetGetCount++;
				tGetGetMoney+=Double.parseDouble(tssrs.GetText(i, 8));

			}
			if("1".equals(tssrs.GetText(i, 13)))
			{
				tGetPayCount++;
				tGetPayMoney+=Double.parseDouble(tssrs.GetText(i, 7));
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
        
		//件数
		texttag.add("SumPayCount", tGetPayCount);
		//
		texttag.add("SumGetCount", tGetGetCount);
		//金额
		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag.add("SumGetMoney", PubFun.round(tGetGetMoney, 2));
		texttag.add("PrintDate",  BqNameFun.getChDate((PubFun.getCurrentDate())));

		texttag.add("Num", tCount);

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorPremBill.xml");
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
