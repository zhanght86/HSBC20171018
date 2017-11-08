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
 * @CreateDate：2009-04-11 清单名称：满期金/年金/生存金给付清单
 */

public class EdorLGBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorLGBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorLGBill.vts";

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

	public EdorLGBillPrintBL() {
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
			tError.moduleName = "EdorLGBillPrintBL";
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
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		// 处理渠道
		if (mSaleChnl != null && !"".equals(mSaleChnl)) {
			mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
					+ "where p.contno = l.otherno " + " and p.salechnl = '"
					+ "?mSaleChnl?" + "')";
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType == null || "".equals(mEdorType)) {
			mErrors.addOneError(new CError("没有得到足够的信息:类型不能为空！"));
			return false;
		}
		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String tASQL = "";
		// 已领
		tASQL = " select substr(b.managecom, 1, 6), "
				+" (select d.shortname from ldcom d where d.comcode = b.managecom) mg, "
				+" (select lacom.name from lacom where lacom.agentcom = b.agentcom),"
				+" (select codename from ldcode where code = b.salechnl and codetype = 'salechnl')sale, b.appntname , "
				+" (select r.postaladdress from lcaddress r where (r.customerno, r.addressno) = "
				+"	 (select s.appntno, s.addressno from lcappnt s where s.contno = b.contno)), "
				+" (select r.zipcode from lcaddress r where (r.customerno, r.addressno) = "
				+"   (select s.appntno, s.addressno from lcappnt s where s.contno = b.contno)), "
				+" (select concat(concat(r.phone , ' ') , r.mobile) from lcaddress r where (r.customerno, r.addressno) = "
				+"   (select s.appntno, s.addressno from lcappnt s where s.contno = b.contno)), " 
				+" b.contno,  l.prtseq  领取通知书号,l.StandbyFlag5 险种名称,  "
				+ " (select cvalidate from lccont a where a.contno = l.otherno) 保险合同生效日期,  "
				+ " l.standbyflag6 应领日期,  "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 0) 保险金类型,  "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 1) 保险金受益人姓名, "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 2) 受益比例, "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 3)本期应领金额, "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 4)领取方式,  "
				+" (select f.name from laagent f where f.agentcode = b.agentcode), "
				+" (select f.agentcode from laagent f where f.agentcode = b.agentcode),"
				+" (select f.mobile from laagent f where f.agentcode = b.agentcode) "
				+ " from loprtmanager l,lccont b "
				+ " where l.otherno=b.contno and l.code = 'BQ17'"
				+ " and l.standbyflag6 >= '"+"?mStartDate?"+"' "
				+ " and l.standbyflag6 <= '"+"?mEndDate?"+"' "
				+ " and l.managecom like concat('"+"?mManageCom?"+"','%')"
				+  mSaleChlSQL
				+ " and  exists (select 'X' from lcinsureacctrace b where b.contno=l.otherno and b.insuaccno in ('000005','000009') and b.moneytype in ('YFLQ','EFLQ') and b.paydate>=l.standbyflag6 ) ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tASQL);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mSaleChnl", mSaleChnl);
		
// --未领
		String tSSQL = " select substr(b.managecom, 1, 6), "
				+" (select d.shortname from ldcom d where d.comcode = b.managecom) mg, "
				+" (select lacom.name from lacom where lacom.agentcom = b.agentcom),"
				+" (select codename from ldcode where code = b.salechnl and codetype = 'salechnl')sale, b.appntname , "
				+" (select r.postaladdress from lcaddress r where (r.customerno, r.addressno) = "
				+"	 (select s.appntno, s.addressno from lcappnt s where s.contno = b.contno)), "
				+" (select r.zipcode from lcaddress r where (r.customerno, r.addressno) = "
				+"   (select s.appntno, s.addressno from lcappnt s where s.contno = b.contno)), "
				+" (select concat(concat(r.phone , ' ') , r.mobile) from lcaddress r where (r.customerno, r.addressno) = "
				+"   (select s.appntno, s.addressno from lcappnt s where s.contno = b.contno)), " 
				+" b.contno,  l.prtseq  领取通知书号,l.StandbyFlag5 险种名称,  "
				+ " (select cvalidate from lccont a where a.contno = l.otherno) 保险合同生效日期,  "
				+ " l.standbyflag6 应领日期,  "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 0) 保险金类型,  "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 1) 保险金受益人姓名, "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 2) 受益比例, "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 3)本期应领金额, "
				+ " Get_StrArrayStrOfIndex(substr(l.remark,1,char_length(l.remark)-1), '|', 4)领取方式,  "
				+" (select f.name from laagent f where f.agentcode = b.agentcode), "
				+" (select f.agentcode from laagent f where f.agentcode = b.agentcode),"
				+" (select f.mobile from laagent f where f.agentcode = b.agentcode) "
				+ " from loprtmanager l,lccont b "
				+ " where l.otherno=b.contno and l.code = 'BQ17'"
				+ " and l.standbyflag6 >= '"+"?mStartDate?"+"' "
				+ " and l.standbyflag6 <= '"+"?mEndDate?"+"' "
				+ " and l.managecom like concat('"+"?mManageCom?"+"','%') "
				+ " and not exists (select 'X' from lcinsureacctrace b where b.contno=l.otherno and b.insuaccno in ('000005','000009') and b.moneytype in ('YFLQ','EFLQ') and b.paydate>=l.standbyflag6 ) "
				//+ " and  exists (select 'X' from lcinsureacctrace b where b.contno=l.otherno and b.insuaccno in ('000005','000009') and b.moneytype in ('YF','EF') and b.paydate=l.standbyflag6 ) ";
				//除满期终止外，其它终止状态下的给付数据不体现
				+" and not exists (select 1 from lccontstate where contno=l.otherno and statetype='Terminate' and state='1' and statereason in ('02','03','04','05','06','07','08'))"
				//失效状态下的给付数据不体现
				+" and not exists (select 1 from lccontstate where contno=l.otherno and statetype='Available' and state='1' and enddate is  null)"
				//自动垫交 的也不体现
		        +" and not exists (select 1 from lccontstate where contno=l.otherno and statetype='PayPrem' and state='1' and enddate is null)";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSSQL);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		sqlbv2.put("mManageCom", mManageCom);
		SQLwithBindVariables tExeSQL = null;
		if ("0".equals(mEdorType)) {
			texttag.add("Title", "未领清单");
			tExeSQL = sqlbv2;

		} else if ("1".equals(mEdorType)) {
			texttag.add("Title", "已领清单");
			tExeSQL = sqlbv1;
		}
		logger.debug("SQL:" + tExeSQL);
		tssrs = texesql.execSQL(tExeSQL);
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
			strLine = new String[22];
			for (j = 1; j < 22; j++) {
				strLine[0]=String.valueOf(i);
				strLine[j] = tssrs.GetText(i, j);
			}
			// tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 8));
			tContListTable.add(strLine);
		}

		mManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getChDate(mStartDate);
		mEndDate = BqNameFun.getChDate(mEndDate);
		// 模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("SumPayCount", tGetPayCount);
		// texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		// tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorLGBill.vts");
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
