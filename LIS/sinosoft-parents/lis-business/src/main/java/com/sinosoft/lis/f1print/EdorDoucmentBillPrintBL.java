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
 * 清单名称：保全档案清单
 */

public class EdorDoucmentBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorDoucmentBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorDoucmentBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";
	
	private String mDateSQL="";

	private String mEdorTypeSQL = "";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorDoucmentBillPrintBL() {
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
			tError.moduleName = "EdorDoucmentBillPrintBL";
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
		mDateSQL= " and  exists(select 'X' from lpedorapp c where c.edoracceptno = a.edoracceptno and c.confdate >= '"
		+ "?mStartDate?" + "'" 
		+ " and c.confdate <= '" 
		+ "?mEndDate?" + "')";
			
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mUserCode = (String) mTransferData.getValueByName("UserCode");
		if (mUserCode != null && !"".equals(mUserCode)) {
			mUserCodeSQL = " and   exists	(select 'X' from lpedorapp c where c.edoracceptno = a.edoracceptno and c.confoperator= '" + "?mUserCode?" + "')";
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType != null && !"".equals(mEdorType)) {
			mEdorTypeSQL = " and  edortype in  (select code" + " from ldcode p "
					+ " where p.codetype ='edortypedoc' "
					+ " and p.othersign = '" + "?mEdorType?" + "')";
		} else {
			mEdorTypeSQL = " and  edortype in  (select code" + " from ldcode p "
			+ " where p.codetype ='edortypedoc')";	}

		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " select "
				+ " (select codename from ldcode where codetype='edortype' and code=a.edortype),"
				+ " (case (select othersign"
				+ " from ldcode"
				+ " where codetype = 'edortypedoc'"
				+ " and code = a.edortype) when '0' then '永久' when '1' then '长期' when '2' then '短期' else '' end) doclength,"
				+ " (select (select riskname"
				+ " from lmriskapp m"
				+ " where m.riskcode = d.riskcode)"
				+ " from lcpol d"
				+ " where d.contno = a.contno"
				+ " and d.polno = d.mainpolno and rownum=1),"
				+ " a.contno,"
				+ " (select c.edorconfno"
				+ " from lpedorapp c"
				+ " where c.edoracceptno = a.edoracceptno) edorconfno,"
				+ " (select l.appntname from lccont l where l.contno = a.contno),"
				+ " (select l.insuredname from lccont l where l.contno = a.contno),"
				+ " (select username from lduser where usercode=(select operator from lpedorapp where edoracceptno = a.edoracceptno)) edorappname," //从LPEdorApp里取操作员
				                                                                          //为兼容保全项目程序BUG导致的LPEdorItem的Operator
				+ " (select c.confdate" + " from lpedorapp c"							 //LPEdorApp的Operator的不一致
				+ " where c.edoracceptno = a.edoracceptno) confdate,'',''"
				+ " From lpedoritem a" + " where 1 = 1"
				+ " and a.grpcontno = '00000000000000000000'"
				+ " and a.edorstate in ('0','b')"  //添加保全回退的情况
				+ mDateSQL
				+ " and a.managecom like concat('"+ "?mManageCom?" + "','%')" 
				+ mEdorTypeSQL + mUserCodeSQL +" order by doclength,confdate,edorappname,edorconfno ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select "
					+ " (select codename from ldcode where codetype='edortype' and code=a.edortype),"
					+ " (case (select othersign"
					+ " from ldcode"
					+ " where codetype = 'edortypedoc'"
					+ " and code = a.edortype) when '0' then '永久' when '1' then '长期' when '2' then '短期' else '' end) doclength,"
					+ " (select (select riskname"
					+ " from lmriskapp m"
					+ " where m.riskcode = d.riskcode)"
					+ " from lcpol d"
					+ " where d.contno = a.contno"
					+ " and d.polno = d.mainpolno limit 1),"
					+ " a.contno,"
					+ " (select c.edorconfno"
					+ " from lpedorapp c"
					+ " where c.edoracceptno = a.edoracceptno) edorconfno,"
					+ " (select l.appntname from lccont l where l.contno = a.contno),"
					+ " (select l.insuredname from lccont l where l.contno = a.contno),"
					+ " (select username from lduser where usercode=(select operator from lpedorapp where edoracceptno = a.edoracceptno)) edorappname," //从LPEdorApp里取操作员
					                                                                          //为兼容保全项目程序BUG导致的LPEdorItem的Operator
					+ " (select c.confdate" + " from lpedorapp c"							 //LPEdorApp的Operator的不一致
					+ " where c.edoracceptno = a.edoracceptno) confdate,'',''"
					+ " From lpedoritem a" + " where 1 = 1"
					+ " and a.grpcontno = '00000000000000000000'"
					+ " and a.edorstate in ('0','b')"  //添加保全回退的情况
					+ mDateSQL
					+ " and a.managecom like concat('"+ "?mManageCom?" + "','%')" 
					+ mEdorTypeSQL + mUserCodeSQL +" order by doclength,confdate,edorappname,edorconfno ";
		}
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
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
			strLine = new String[12];
			for (j = 1; j < 12; j++) {
				strLine[0]=String.valueOf(i);
				strLine[j] = tssrs.GetText(i, j);
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
		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorDoucmentBill.vts"); 
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
