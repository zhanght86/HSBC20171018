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
 * 清单名称：保全理赔数据提取
 */

public class EdorCLaimBackBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorCLaimBackBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorCLaimBackBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mBillType = "";

	private String mRiskCode = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mRiskCodeSQL = "";
	
	private String mBillTypeSQL="";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorCLaimBackBillPrintBL() {
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
			tError.moduleName = "EdorCLaimBackBillPrintBL";
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

		mBillType = (String) mTransferData.getValueByName("BillType");
		if (mBillType == null || "".equals(mBillType)) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		} 
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		if (mRiskCode != null && !"".equals(mRiskCode)) {
			mRiskCodeSQL = " and exists (select 'X'"
			+ " from lcpol c"
			+ " where c.contno = a.otherno"
			+ " and c.polno = c.mainpolno and c.riskcode='"+"?mRiskCode?"+"')";
		}	

		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String bqstrsql = "";
		String lpstrsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			bqstrsql = " select rownum,"
					+ " (select c.riskcode"
					+ " from lcpol c"
					+ " where c.contno = a.otherno"
					+ " and c.polno = c.mainpolno and rownum=1),"
					+ " (select (select codename"
					+ " from ldcode"
					+ " where code = b.salechnl"
					+ " and codetype = 'salechnl')"
					+ " from lccont b"
					+ " where b.contno = a.otherno),"
					+ " (select (select (case RiskType3 when '1' then '传统险' when '2' then '分红险' when '3' then '投连险' when '4' then '万能险' else '其他' end)"
					+ " from lmriskapp"
					+ " where riskcode = c.riskcode)"
					+ " from lcpol c"
					+ " where c.contno = a.otherno"
					+ " and c.polno = c.mainpolno and rownum=1),"
					+ " (select Appntname from LCAppnt where contno = a.otherno),"
					+ " (select (case Appntsex when '0' then '男' when '1' then '女' end)"
					+ " from LCAppnt"
					+ " where contno = a.otherno),"
					//+ " (select (select codename from ldcode where codetype='idtype' and code=Idtype) from LCAppnt where contno = a.otherno),"
					//+ " (select Idno from LCAppnt where contno = a.otherno),"
					//+ " (select Appntbirthday from LCAppnt where contno = a.otherno),"
					+ " (select r.mobile"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.homephone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.phone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select name from Lcinsured where contno = a.otherno and rownum=1),"
					+ " (select (case sex when '0' then '男' when '1' then '女' end) from Lcinsured where contno = a.otherno and rownum=1),"
					+ " (select floor((select months_between(now(), Birthday) from dual) / 12)"
					+ " from Lcinsured"
					+ " where contno = a.otherno and rownum=1),"
					+ " (select (select codename"
					+ " from ldcode"
					+ " where code = r.Relationtoinsured"
					+ " and codetype = 'relation')"
					+ " from Lcappnt r"
					+ " where r.contno = a.otherno),"
					+ " (select (select edorname"
					+ " from lmedoritem"
					+ " where edorcode = c.edortype"
					+ " and appobj = 'I')"
					+ " from lpedoritem c"
					+ " where c.edoracceptno = a.edoracceptno and rownum=1),"
					+ " ( select sum(sumgetmoney) from ljaget e where e.othernotype='10' and e.otherno=a.edorconfno ), "
					+ " (select codename from ldcode where codetype='edorgetpayform' and code=a.getform),"
					+ " (select max(d.enteraccdate)"
					+ " from ljaget d"
					+ " where d.otherno = a.edorconfno"
					+ " and d.othernotype = '10'),"
					+ " a.accname,"
					+ " a.bankaccno,"
					+ " ((case when a.edorconfno is not null then a.edorconfno  else (select edorno from lpedoritem where edoracceptno=a.edoracceptno and rownum=1) end)),"
					+ " a.managecom"
					+ " from lpedorapp a"
					+ " where a.othernotype = '3'"
					+ " and exists ( select 'X' from ljaget t where othernotype='10' and t.otherno=a.edorconfno and t.sumgetmoney>0 and t.enteraccdate is not null)"
					+ " and a.confdate >='" + "?mStartDate?" + "'"
					+ " and a.confdate <= '" + "?mEndDate?" + "'"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+ mBillTypeSQL + mRiskCodeSQL;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			bqstrsql = " select (@rownum := @rownum + 1) AS rownum,t.* from (select @rownum:=0,"
					+ " (select c.riskcode"
					+ " from lcpol c"
					+ " where c.contno = a.otherno"
					+ " and c.polno = c.mainpolno limit 0,1),"
					+ " (select (select codename"
					+ " from ldcode"
					+ " where code = b.salechnl"
					+ " and codetype = 'salechnl')"
					+ " from lccont b"
					+ " where b.contno = a.otherno),"
					+ " (select (select (case RiskType3 when '1' then '传统险' when '2' then '分红险' when '3' then '投连险' when '4' then '万能险' else '其他' end)"
					+ " from lmriskapp"
					+ " where riskcode = c.riskcode)"
					+ " from lcpol c"
					+ " where c.contno = a.otherno"
					+ " and c.polno = c.mainpolno limit 0,1),"
					+ " (select Appntname from LCAppnt where contno = a.otherno),"
					+ " (select (case Appntsex when '0' then '男' when '1' then '女' end)"
					+ " from LCAppnt"
					+ " where contno = a.otherno),"
					//+ " (select (select codename from ldcode where codetype='idtype' and code=Idtype) from LCAppnt where contno = a.otherno),"
					//+ " (select Idno from LCAppnt where contno = a.otherno),"
					//+ " (select Appntbirthday from LCAppnt where contno = a.otherno),"
					+ " (select r.mobile"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.homephone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.phone"
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select name from Lcinsured where contno = a.otherno limit 0,1),"
					+ " (select (case sex when '0' then '男' when '1' then '女' end) from Lcinsured where contno = a.otherno limit 0,1),"
					+ " (select floor((select months_between(now(), Birthday) from dual) / 12)"
					+ " from Lcinsured"
					+ " where contno = a.otherno limit 0,1),"
					+ " (select (select codename"
					+ " from ldcode"
					+ " where code = r.Relationtoinsured"
					+ " and codetype = 'relation')"
					+ " from Lcappnt r"
					+ " where r.contno = a.otherno),"
					+ " (select (select edorname"
					+ " from lmedoritem"
					+ " where edorcode = c.edortype"
					+ " and appobj = 'I')"
					+ " from lpedoritem c"
					+ " where c.edoracceptno = a.edoracceptno limit 0,1),"
					+ " ( select sum(sumgetmoney) from ljaget e where e.othernotype='10' and e.otherno=a.edorconfno ), "
					+ " (select codename from ldcode where codetype='edorgetpayform' and code=a.getform),"
					+ " (select max(d.enteraccdate)"
					+ " from ljaget d"
					+ " where d.otherno = a.edorconfno"
					+ " and d.othernotype = '10'),"
					+ " a.accname,"
					+ " a.bankaccno,"
					+ " ((case when a.edorconfno is not null then a.edorconfno  else (select edorno from lpedoritem where edoracceptno=a.edoracceptno limit 0,1) end)),"
					+ " a.managecom"
					+ " from lpedorapp a"
					+ " where a.othernotype = '3'"
					+ " and exists ( select 'X' from ljaget t where othernotype='10' and t.otherno=a.edorconfno and t.sumgetmoney>0 and t.enteraccdate is not null)"
					+ " and a.confdate >='" + "?mStartDate?" + "'"
					+ " and a.confdate <= '" + "?mEndDate?" + "'"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+ mBillTypeSQL + mRiskCodeSQL 
					+ " ) t";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(bqstrsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mRiskCode", mRiskCode);
		int MaxRow=0;
		if("01".equals(mBillType))//保全回访
		{
			logger.debug("保全回访SQL:" + bqstrsql);
			MaxRow=21;
			tssrs = texesql.execSQL(sqlbv);
		}else
		{

			logger.debug("理赔回访SQL:" + lpstrsql);
			//tssrs = texesql.execSQL(lpstrsql);	
			return false;
		}				
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
			strLine = new String[MaxRow];
			for (j = 0; j < MaxRow; j++) {
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
//		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorCLaimBackBill.vts"); 
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
