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
 * 清单名称：万能结算未结算清单
 */

public class EdorUniverUnCountBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorUniverUnCountBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorUniverUnCountBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mCountDate = "";

	private String mUserCode = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorUniverUnCountBillPrintBL() {
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
			tError.moduleName = "EdorUniverUnCountBillPrintBL";
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

		mCountDate = (String) mTransferData.getValueByName("CountDate");
		if (mCountDate == null || mCountDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:结算日期不能为空！"));
			return false;
		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}

		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if ("0".equals(mEdorType)) {  //万能险未结算保单清单
			texttag.add("Title", "万能险未结算保单清单");
			mEdorTypeSQL =  "  and not exists (select 'X' from lcinsureacctrace where  contno=a.contno and moneytype='LX' and paydate='"+"?mCountDate?"+"')";
		} else if ("1".equals(mEdorType))  {//万能险已结算保单清单
			texttag.add("Title", "万能险已结算保单清单");
			mEdorTypeSQL ="  and  exists (select 'X' from lcinsureacctrace where  contno=a.contno and moneytype='LX' and paydate='"+"?mCountDate?"+"')";
		 }
		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " Select rownum,CONTNO 保单号,"
					+ " POLNO 险种号,"
					+ " INSUACCNO 账户编码,"
					+ " RISKCODE 险种编码,"
					+ " (select concat(concat(trim(code) , '-') , trim(codename))"
					+ " from ldcode"
					+ " where '1209882020000' = '1209882020000'"
					+ " and codetype = 'finfeetype'"
					+ " and trim(code) = trim(MONEYTYPE)) 财务类型,"
					+ " MONEY 账户出入轨迹,"
					+ " PAYDATE 账户出入时间,"
					+ " (case"
					+ " when (select sum(MONEY)"
					+ " from LCInsureAccTrace r"
					+ " where r.POLNO = a.POLNO"
					+ " and r.PAYDATE >= '1900-01-01'"
					+ " and r.PAYDATE <= a.paydate) < 0 then "
					+ " 0"
					+ " else"
					+ " (select sum(MONEY)"
					+ " from LCInsureAccTrace r"
					+ " where r.POLNO = a.POLNO"
					+ " and r.PAYDATE >= '1900-01-01'"
					+ " and r.PAYDATE <= a.paydate) "
					+ " end) 账户累计金额"
					+ " From LCInsureAccTrace a"
					+ " where  1=1 "
					//add by jiaqiangli 2009-11-18
					+ " and exists (select 'X' from lcpol where managecom like concat('" + "?mManageCom?" + "','%') and appflag = '1' and polno=a.polno)"
					+  mEdorTypeSQL;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " Select (@rownum := @rownum + 1) AS rownum, 保单号,"
					+ " 险种号,"
					+ " 账户编码,"
					+ " 险种编码,"
					+ " 财务类型,"
					+ " 账户出入轨迹,"
					+ " 账户出入时间,"
					+ " 账户累计金额"
					+ " From (select @rownum:= 0,CONTNO 保单号,"
					+ " POLNO 险种号,"
					+ " INSUACCNO 账户编码,"
					+ " RISKCODE 险种编码,"
					+ " (select concat(concat(trim(code) , '-') , trim(codename))"
					+ " from ldcode"
					+ " where '1209882020000' = '1209882020000'"
					+ " and codetype = 'finfeetype'"
					+ " and trim(code) = trim(MONEYTYPE)) 财务类型,"
					+ " MONEY 账户出入轨迹,"
					+ " PAYDATE 账户出入时间,"
					+ " (case"
					+ " when (select sum(MONEY)"
					+ " from LCInsureAccTrace r"
					+ " where r.POLNO = a.POLNO"
					+ " and r.PAYDATE >= '1900-01-01'"
					+ " and r.PAYDATE <= a.paydate) < 0 then "
					+ " 0"
					+ " else"
					+ " (select sum(MONEY)"
					+ " from  LCInsureAccTrace r"
					+ " where r.POLNO = a.POLNO"
					+ " and r.PAYDATE >= '1900-01-01'"
					+ " and r.PAYDATE <= a.paydate) "
					+ " end) 账户累计金额"
					+ " from LCInsureAccTrace a"
					+ " where  1=1 "
					//add by jiaqiangli 2009-11-18
					+ " and exists (select 'X' from lcpol where managecom like concat('" + "?mManageCom?" + "','%') and appflag = '1' and polno=a.polno)"
					+  mEdorTypeSQL+") t";
		}
		
		sqlbv.sql(strsql);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mCountDate", mCountDate);
		logger.debug("SQL:" + strsql);
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "无待打印清单！");
			return false;
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;
		int j;//计数

		int tGetPayCount = tssrs.getMaxRow();
		double tGetPayMoney = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[9];
			for (j = 0; j < 9; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			//tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 8));
			tContListTable.add(strLine);
		}

		mManageCom = BqNameFun.getCBranch(mManageCom);
		mCountDate = BqNameFun.getChDate(mCountDate);

		//模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("mCountDate", mCountDate);
		//texttag.add("EndDate", mEndDate);
		texttag.add("SumPayCount", tGetPayCount);
		//texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorUniverUnCountBill.vts"); 
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
