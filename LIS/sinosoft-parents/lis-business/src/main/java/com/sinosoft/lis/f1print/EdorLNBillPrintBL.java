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
 * 清单名称：借款清单
 */

public class EdorLNBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorLNBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorLNBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mCountDate = "";

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

	public EdorLNBillPrintBL() {
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
			tError.moduleName = "EdorLNBillPrintBL";
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
//		if (mStartDate == null || mStartDate.trim().equals("")) {
//			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
//			return false;
//		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
//		if (mEndDate == null || mEndDate.trim().equals("")) {
//			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
//			return false;
//		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mCountDate = (String) mTransferData.getValueByName("CountDate");

		mUserCode = (String) mTransferData.getValueByName("UserCode");
		if (mUserCode != null && !"".equals(mUserCode)) {
			mUserCodeSQL = " and a.operator = '" + "?mUserCode?" + "'";
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if ("0".equals(mEdorType)) {  //借款期差30天即将满期的保单
			texttag.add("Title", "借款即将满期清单");
			mEdorTypeSQL =  " and payoffflag = '0'" 
                + " and (select (MONTHS_BETWEEN(to_date('"+"?mCountDate?"+"','yyyy-mm-dd') , loandate)) from dual) >= 5"
                + " and (select (MONTHS_BETWEEN(to_date('"+"?mCountDate?"+"','yyyy-mm-dd') , loandate)) from dual) < 6";
		} else if ("1".equals(mEdorType))  {//借款期满6个月未作还款的保单
			texttag.add("Title", "借款期满未还清单");
			mEdorTypeSQL =  " and payoffflag = '0'" 
			                + " and (select (MONTHS_BETWEEN(to_date('"+"?mCountDate?"+"','yyyy-mm-dd'), loandate)) from dual)>6";
		 }
		 else if ("2".equals(mEdorType))  {//已清偿借款
				texttag.add("Title", "已清偿借款清单");
				mEdorTypeSQL =    " and payoffflag = '1'" 
				                + " and payoffdate>='"+"?mStartDate?"+"'"
				                + " and payoffdate<='"+"?mEndDate?"+"'";
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
			strsql = " select substr(b.managecom, 1, 6), "
					+" (select d.shortname from ldcom d where d.comcode = b.managecom) mg, "
					+" (select lacom.name from lacom where lacom.agentcom = b.agentcom),"
					+" (select codename from ldcode where code = b.salechnl and codetype = 'salechnl')sale, b.appntname , "
					+" (select r.postaladdress from lcaddress r where (r.customerno, r.addressno) = "
					+"	 (select s.appntno, s.addressno from lcappnt s where s.contno = a.contno)), "
					+" (select r.zipcode from lcaddress r where (r.customerno, r.addressno) = "
					+"   (select s.appntno, s.addressno from lcappnt s where s.contno = a.contno)), "
					+" (select concat(concat(r.phone , ' ') , r.mobile) from lcaddress r where (r.customerno, r.addressno) = "
					+"   (select s.appntno, s.addressno from lcappnt s where s.contno = a.contno)), a.contno, " 
				    +" (select (select riskname from lmriskapp m  where m.riskcode = d.riskcode) from lcpol d "
				    +"	  where d.contno = b.contno and d.polno = d.mainpolno and rownum = 1),a.summoney,a.loandate,add_months(a.loandate,6), "
					+" (select f.name from laagent f where f.agentcode = b.agentcode), "
					+" (select f.agentcode from laagent f where f.agentcode = b.agentcode),"
					+" (select f.mobile from laagent f where f.agentcode = b.agentcode) "
					+ " from loloan a,lccont b"
					+ " where a.contno=b.contno and substr(a.contno, 11, 3) = '021'"
					//add by jiaqiangli 2009-11-18 增加管理机构控制
					+ " and  b.managecom like concat('" + "?mManageCom?" + "','%' )"
					+ " and loantype = '0'"
		            +mEdorTypeSQL + mUserCodeSQL +" order by mg,contno";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select substr(b.managecom, 1, 6), "
					+" (select d.shortname from ldcom d where d.comcode = b.managecom) mg, "
					+" (select lacom.name from lacom where lacom.agentcom = b.agentcom),"
					+" (select codename from ldcode where code = b.salechnl and codetype = 'salechnl')sale, b.appntname , "
					+" (select r.postaladdress from lcaddress r where (r.customerno, r.addressno) = "
					+"	 (select s.appntno, s.addressno from lcappnt s where s.contno = a.contno)), "
					+" (select r.zipcode from lcaddress r where (r.customerno, r.addressno) = "
					+"   (select s.appntno, s.addressno from lcappnt s where s.contno = a.contno)), "
					+" (select concat(concat(r.phone , ' ') , r.mobile) from lcaddress r where (r.customerno, r.addressno) = "
					+"   (select s.appntno, s.addressno from lcappnt s where s.contno = a.contno)), a.contno, " 
				    +" (select (select riskname from lmriskapp m  where m.riskcode = d.riskcode) from lcpol d "
				    +"	  where d.contno = b.contno and d.polno = d.mainpolno limit 0,1),a.summoney,a.loandate,add_months(a.loandate,6), "
					+" (select f.name from laagent f where f.agentcode = b.agentcode), "
					+" (select f.agentcode from laagent f where f.agentcode = b.agentcode),"
					+" (select f.mobile from laagent f where f.agentcode = b.agentcode) "
					+ " from loloan a,lccont b"
					+ " where a.contno=b.contno and substr(a.contno, 11, 3) = '021'"
					//add by jiaqiangli 2009-11-18 增加管理机构控制
					+ " and  b.managecom like concat('" + "?mManageCom?" + "','%' )"
					+ " and loantype = '0'"
		            +mEdorTypeSQL + mUserCodeSQL +" order by mg,contno";
		}
		
		logger.debug("SQL:" + strsql);
		sqlbv.sql(strsql);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mUserCode", mUserCode);
		sqlbv.put("mCountDate", mCountDate);
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			if("0".equals(mEdorType)||"1".equals(mEdorType))
			{
				CError.buildErr(this, "截止日期" + mCountDate + "之前无待打印清单！");
			}
			else
			{
				CError.buildErr(this, mStartDate + "至" + mEndDate + "之间无待打印清单！");
			}
			return false;
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;
		int j;//计数

		int tGetPayCount = tssrs.getMaxRow();
		double tGetPayMoney = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[17];
			for (j = 1; j < 17; j++) {
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
		//texttag.add("Title", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorLNBill.vts"); 
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
