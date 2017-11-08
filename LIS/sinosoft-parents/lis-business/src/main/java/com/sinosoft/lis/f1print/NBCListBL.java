package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author zy
 * @version 1.0
 */

public class NBCListBL {
private static Logger logger = Logger.getLogger(NBCListBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	// 取得的时间
	private String mDay[] = null;
	// 输入的查询sql语句
	private String msql = "";
	private String nsql = "";
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	public NBCListBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));// 销售渠道
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "NBCListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {
		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		String SaleChnl = "";
		String tManagecom = "";
		String tRiskCode = "";
		SSRS tSSRS = new SSRS();
		if ((mLCPolSchema.getSaleChnl() != null)
				&& (!mLCPolSchema.getSaleChnl().equals("")))
			SaleChnl = " and SaleChnl='" + "?SaleChnl?" + "' ";
		if ((mLCPolSchema.getManageCom() != null)
				&& (!mLCPolSchema.getManageCom().equals(""))) {
			tManagecom = " and c.managecom like concat('"
					+ "?managecom?" + "','%')";
			String sql = "select shortname from ldcom where comcode='"
					+ "?comcode?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("comcode", mLCPolSchema.getManageCom());
			ExeSQL mExeSQL = new ExeSQL();
			texttag.add("ManageCom", mExeSQL.getOneValue(sqlbv1));
		} else {
			tManagecom = " and c.managecom like concat('" + "?mGlobalInput?"
					+ "','%')";
			String sql = "select shortname from ldcom where comcode='"
					+ "?comcode?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("comcode", mGlobalInput.ManageCom);
			ExeSQL mExeSQL = new ExeSQL();
			texttag.add("ManageCom", mExeSQL.getOneValue(sqlbv2));
		}
		if ((mLCPolSchema.getRiskCode() != null)
				&& (!mLCPolSchema.getRiskCode().equals("")))
			tRiskCode = " and riskcode='" + "?riskcode?" + "' ";

		// msql="select
		// RiskCode,SaleChnl,PolNo,AppntName,AgentCode,AppntNo,RiskVersion from
		// LCPol where AppFlag='1' and SignDate>='"+mDay[0]+"' and
		// SignDate<='"+mDay[1]+"' and GrpPolNo = '00000000000000000000'
		// "+SaleChnl+tManagecom+" order by RiskCode";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			msql = "select /*+ INDEX (c IDX_LCPOL_SIGNDATE) */ riskcode,(select codename from ldcode where codetype='salechnl' and trim(code)=trim(c.salechnl)),"
					+ "(select (case RiskType3 when '1' then '传统险' when '2' then '分红险' when '3' then '投连险' when '4' then '万能险' else '其他' end) from lmriskapp where riskcode=c.riskcode),"
					+ "c.contno,c.appntname,(case d.appntSex when '0' then '男' when '1' then '女' end),"
					+ "e.mobile,"
					+ "e.phone,"
					+ "'',"
					+ "(case when e.PostalAddress is null then e.HomeAddress else e.PostalAddress end),"
					+ "(case when e.ZipCode is null then e.HomeZipCode else e.ZipCode end),"
					+ "(select name from laagent where agentcode=c.agentcode),c.agentcode,"
					+ "c.insuredname,c.InsuredAppAge ,c.amnt,c.prem,c.cvalidate, "
					+ "(select (case count(*) when 0 then '' else '#' end)decode(count(*),0,'','#') from lcspec where polno=c.polno and SpecContent is not null)"
					+ "from lcpol c,lcappnt d,lcaddress e where appflag='1' and grppolno='00000000000000000000' "
					+ " and c.contno=d.contno and d.appntno=c.appntno and e.CustomerNo=d.AppntNo and e.addressno=d.addressno "
					+ "and SignDate>='"
					+ "?date1?"
					+ "' and SignDate<='"
					+ "?date2?"
					+ "' ";
			msql = msql
					+ SaleChnl
					+ tManagecom
					+ tRiskCode
					+ " and not exists (select 1 from lcrnewstatehistory where contno = c.contno and riskcode=c.riskcode " +
							" union select 1 from lcrnewstatelog where polno=c.polno ) order by RiskCode";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			msql = "select  riskcode,(select codename from ldcode where codetype='salechnl' and trim(code)=trim(c.salechnl)),"
					+ "(select (case RiskType3 when '1' then '传统险' when '2' then '分红险' when '3' then '投连险' when '4' then '万能险' else '其他' end) from lmriskapp where riskcode=c.riskcode),"
					+ "c.contno,c.appntname,(case d.appntSex when '0' then '男' when '1' then '女' end),"
					+ "e.mobile,"
					+ "e.phone,"
					+ "'',"
					+ "(case when e.PostalAddress is null then e.HomeAddress else e.PostalAddress end),"
					+ "(case when e.ZipCode is null then e.HomeZipCode else e.ZipCode end),"
					+ "(select name from laagent where agentcode=c.agentcode),c.agentcode,"
					+ "c.insuredname,c.InsuredAppAge ,c.amnt,c.prem,c.cvalidate, "
					+ "(select (case count(*) when 0 then '' else '#' end)decode(count(*),0,'','#') from lcspec where polno=c.polno and SpecContent is not null)"
					+ "from lcpol c,lcappnt d,lcaddress e where appflag='1' and grppolno='00000000000000000000' "
					+ " and c.contno=d.contno and d.appntno=c.appntno and e.CustomerNo=d.AppntNo and e.addressno=d.addressno "
					+ "and SignDate>='"
					+ "?date1?"
					+ "' and SignDate<='"
					+ "?date2?"
					+ "' ";
			msql = msql
					+ SaleChnl
					+ tManagecom
					+ tRiskCode
					+ " and not exists (select 1 from lcrnewstatehistory where contno = c.contno and riskcode=c.riskcode " +
							" union select 1 from lcrnewstatelog where polno=c.polno ) order by RiskCode";
		}
		
		logger.debug(msql);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(msql);
		sqlbv3.put("date1", mDay[0]);
		sqlbv3.put("date2", mDay[1]);
		sqlbv3.put("SaleChnl", mLCPolSchema.getSaleChnl());
		sqlbv3.put("managecom", mLCPolSchema.getManageCom());
		sqlbv3.put("mGlobalInput", mGlobalInput.ManageCom);
		sqlbv3.put("riskcode", mLCPolSchema.getRiskCode());
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv3);
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("List");
		int n = 1;
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[20];
			strArr[0] = String.valueOf(n);
			n++;
			for (int j = 1; j <= tSSRS.MaxCol; j++) {
				strArr[j] = tSSRS.GetText(i, j);
			}
			tlistTable.add(strArr);
		}
		strArr = new String[20];
		strArr[0] = "num";
		strArr[1] = "Riskcode";
		strArr[2] = "SaleChnl";
		strArr[3] = "RiskType";
		strArr[4] = "Polno";
		strArr[5] = "AppntName";
		strArr[6] = "Mobile";
		strArr[7] = "Phone";
		strArr[8] = "Phone2";
		strArr[9] = "Address";
		strArr[10] = "Zip";
		strArr[11] = "Opeator";
		strArr[12] = "AggntNo";
		strArr[13] = "";
		strArr[14] = "";

		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("NBCList.vts", "printer");// 最好紧接着就初始化xml文档
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);

		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		xmlexport.addListTable(tlistTable, strArr);
		// xmlexport.outputDocumentToFile("e:\\","test");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
