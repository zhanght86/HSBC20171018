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
 * 清单名称：保全操作时效清单
 */

public class EdorValiConfirmBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorValiConfirmBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorValiConfirmBill.vts";

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

	private String mOrderSQL = "order by a.managecom,a.otherno";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorValiConfirmBillPrintBL() {
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
			tError.moduleName = "EdorValiConfirmBillPrintBL";
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
					+ " and p.edortype in (select code from ldcode where codetype='edortype'))";
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
			strsql = " select rownum," + " a.managecom," + " a.otherno,"
					+ " a.edoracceptno,"
					+ " (select username from lduser where usercode = a.operator),"
					+ " concat(concat(substr(a.makedate,1,10) , ' ') , a.maketime),"
					+ " concat(concat(substr(a.confdate,1,10) , ' ') , a.conftime),"
					+ " (case"
					+ " when (select (datediff(to_date(concat(concat(substr(to_char(a.confdate,'yyyy-mm-dd'),1,10),' '),a.conftime),'yyyy-MM-dd HH24:mi:ss'),"
					+ " to_date(concat(concat(substr(to_char(a.makedate,'yyyy-mm-dd'),1,10),' '),a.maketime),'yyyy-MM-dd HH24:mi:ss'))) * 24" + " from dual) > 1 then"
					+ " '超时'" + " else" + " '未超时'" + " end)" + " From lpedorapp a"
					+ " where 1 = 1" + " and a.othernotype = '3'"
					+ " and a.edorstate = '0'" + " and a.confdate >= '"
					+ "?mStartDate?" + "'" + " and a.confdate <= '" + "?mEndDate?" + "'"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+ mEdorTypeSQL + mUserCodeSQL+mOrderSQL;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select (@rownum := @rownum + 1) AS rownum, managecom, otherno,"
					+ " edoracceptno,"
					+ " u,"
					+ " m,"
					+ " c,"
					+ " mc" 
					+ " From (select @rownum:= 0,a.managecom, a.otherno,"
					+ " a.edoracceptno,"
					+ " (select username from lduser where usercode = a.operator) u,"
					+ " concat(concat(substr(a.makedate,1,10) , ' ') , a.maketime) m,"
					+ " concat(concat(substr(a.confdate,1,10) , ' ') , a.conftime) c,"
					+ " (case"
					+ " when (select (datediff(to_date(concat(concat(substr(to_char(a.confdate,'yyyy-mm-dd'),1,10),' '),a.conftime),'yyyy-MM-dd HH24:mi:ss') ,"
					+ " to_date(concat(concat(substr(to_char(a.makedate,'yyyy-mm-dd'),1,10),' '),a.maketime),'yyyy-MM-dd HH24:mi:ss'))) * 24" + " from dual) > 1 then"
					+ " '超时'" + " else" + " '未超时'" + " end) mc"
					+ " from lpedorapp a"
					+ " where 1 = 1" + " and a.othernotype = '3'"
					+ " and a.edorstate = '0'" + " and a.confdate >= '"
					+ "?mStartDate?" + "'" + " and a.confdate <= '" + "?mEndDate?" + "'"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+ mEdorTypeSQL + mUserCodeSQL+mOrderSQL+") t";
		}
		
		logger.debug("SQL:" + strsql);
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mEdorType", mEdorType);
		sqlbv.put("mUserCode", mUserCode);
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
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorValiConfirmBill.vts"); 
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
