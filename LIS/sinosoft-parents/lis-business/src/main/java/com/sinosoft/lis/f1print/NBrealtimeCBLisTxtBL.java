package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TxtExport;
import com.sinosoft.utility.VData;
/**
 * <p>Title: 实时承保业绩清单</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: SinoSoft</p>
 *
 * @author
 * @version 1.0
 * @rewrite :liuxs
 * @update : 2007-1-30
 * @versino :1.1
 */

public class NBrealtimeCBLisTxtBL {
private static Logger logger = Logger.getLogger(NBrealtimeCBLisTxtBL.class);

	public CErrors mErrors = new CErrors();
	private GlobalInput mGlobalInput = new GlobalInput();
	// 业务数据
	private String ManageCom = "";
	private String BranchCode = "";
	private String StartDate = "";
	private String EndDate = "";
	private String AccType = "";
	private String fileNameDate = "";

	public NBrealtimeCBLisTxtBL() {
	}

	/**
	 * 本类入口方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean submitData(VData cInputData, String cOperate)
			throws Exception {
		if (!getInputData(cInputData))
			return false;
		if (!check())
			return false;
		if (!queryData())
			return false;
		return true;
	}

	private boolean getInputData(VData cInputData) {
		// 获取从外部传入的信息，并保存入相应的变量中
		ManageCom = (String) cInputData.get(0);
		StartDate = (String) cInputData.get(1);
		BranchCode = (String) cInputData.get(2);
		EndDate = (String) cInputData.get(3);
		AccType = (String) cInputData.get(4); // 01按业务员统计02按险种统计03按保单统计04明细信息
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 5));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	/**
	 * 查询数据库，生成txt文件
	 * 
	 * @return boolean
	 */
	private boolean queryData() throws Exception {
		fileNameDate = PubFun.getCurrentDate().toString().replaceAll(":", "-");
		TxtExport txtExport = new TxtExport();
		txtExport.createTxtDocument(FileManager.getFileName(
				FileManager.AgentQuery, mGlobalInput.Operator, fileNameDate,
				fileNameDate), null);
		// 拼sql
		String sql = "";
		String tPubSQL = "Select a.Managecom As 管理机构代码, (Select Trim(Name) From Ldcom Where Comcode = a.Managecom) As 管理机构名称,"
				+ " a.Contno As 保单号, a.Prtno As 投保书号, (select sum(p.sumactupaymoney) from ljapayperson p where p.polno = a.polno and p.paytype = 'ZC' and p.paycount = 1) as 规模保费,"
				+ " ((select sum(p.sumactupaymoney)  from ljapayperson p  where p.polno = a.polno  and p.paytype = 'ZC'  and p.paycount = 1)*"
				+ " (case when (select rate from laratestandprem   where riskcode = a.riskcode"
				+ " and trim(year) = (case a.payintv when 0 then 0 else a.payendyear end)"
				+ " and trim(payintv) = trim(a.payintv)"
				+ " and a.cvalidate >= startdate"
				+ " and a.cvalidate <= enddate) is not null then (select rate from laratestandprem   where riskcode = a.riskcode"
				+ " and trim(year) = (case a.payintv when 0 then 0 else a.payendyear end)"
				+ " and trim(payintv) = trim(a.payintv)"
				+ " and a.cvalidate >= startdate"
				+ " and a.cvalidate <= enddate)  else 0 end)) as 标准保费,"
				+ " (case a.Mainpolno when a.Polno then '主险' else '附险' end) As 主附险标记, a.Riskcode As 险种代码,"
				+ "(select riskshortname from lmrisk where riskcode = a.Riskcode) as 险种名称,"
				+ "  a.Payendyear As 缴费年期,"
				+ " (case a.Payintv when 0 then '趸缴' when 12 then '年缴' when 1 then '月缴'  end) As 缴费间隔, a.Agentcode As 业务员号,"
				+ " (Select Name From Laagent Where Agentcode = Trim(a.Agentcode)) As 业务员姓名, "
				+ " (select substr(branchseries,1,12) from Labranchgroup where Agentgroup=trim(a.Agentgroup)) as 区代码, "
				+ "  (select name from Labranchgroup where Agentgroup=(select substr(branchseries,1,12) from Labranchgroup where Agentgroup=trim(a.Agentgroup))) as 区名称, "
				+ " (select substr(branchseries,14,12) from Labranchgroup where Agentgroup=trim(a.Agentgroup)) as 部代码, "
				+ " (select name from Labranchgroup where Agentgroup=(select substr(branchseries,14,12) from Labranchgroup where Agentgroup=trim(a.Agentgroup))) as 部名称, "
				+ " a.Agentgroup as 组代码, "
				+ " (select name from Labranchgroup where Agentgroup=trim(a.Agentgroup)) as 组名称, "
				+ " (select b.polapplydate from lccont b where b.contno=a.contno),"
				+ " (select b.signdate from lccont b where b.contno=a.contno),"
				+ " (select b.customgetpoldate from lccont b where b.contno=a.contno),"
				+ " (select b.getpoldate from lccont b where b.contno=a.contno) "
				+ " From Lcpol a ";

		if (AccType.equals("01") || AccType.equals("05")) {
			sql = tPubSQL
					+ " Where 1 = 1 and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1) "
					+ " and  a.signdate >=to_date('"+ "?StartDate?" + "','yyyy-mm-dd')"
					+ " and  a.riskcode in (select riskcode from lmriskapp where riskprop='I')"
					+ " and  a.Managecom Like concat('" + "?ManageCom?"
					+ "','%')";

			sql += " and exists (select 'x' from ljtempfee c where 1 = 1 ";
			if (AccType.equals("01")) { // 预收交单
				sql += " And c.Paydate Between to_date('"+ "?StartDate?" + "','yyyy-mm-dd') And to_date('"+ "?EndDate?" + "','yyyy-mm-dd') ";
			} else { // 预收到帐
				sql += " and c.Enteraccdate Between to_date('"+ "?StartDate?" + "','yyyy-mm-dd')"
						+ " And to_date('"+ "?EndDate?" + "','yyyy-mm-dd') ";

			}
			sql += "  and c.otherno=a.contno and  c.managecom like concat('" + "?ManageCom?"
					+ "','%') and c.confflag='1' and c.othernotype='6')";

		}
		if (AccType.equals("02") || AccType.equals("03")) {
			sql = tPubSQL + " Where 1 = 1 And a.Managecom Like concat('" + "?ManageCom?"
					+ "','%')" + "  and a.signdate Between to_date('"+ "?StartDate?" + "','yyyy-mm-dd') And to_date('"+ "?EndDate?" + "','yyyy-mm-dd') ";
			if (AccType.equals("02")) // 承保出单
			{
				sql += " and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1)";
			} else // 承保回单
			{
				sql += " and exists "
						+ "(select 'x' from lccont where Customgetpoldate Between to_date('"+ "?StartDate?" + "','yyyy-mm-dd') And to_date('"+ "?EndDate?" + "','yyyy-mm-dd') and contno=a.contno) and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1)";
			}
		}

		if (AccType.equals("04")) {
			sql = tPubSQL + " Where 1 = 1 And a.Managecom Like concat('" + "?ManageCom?"
					+ "','%')" + "  and a.signdate Between to_date('"+ "?StartDate?" + "','yyyy-mm-dd') And to_date('"+ "?EndDate?" + "','yyyy-mm-dd') ";

			sql += " and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1) And Not Exists"
					+ "  (Select 'X'  From Lpedoritem s "
					+ "  Where s.Contno = a.Contno And s.Edortype In ('CT', 'PT') and s.standbyflag1 ='1' And s.Edorvalidate >= to_date('"+ "?StartDate?" + "','yyyy-mm-dd') )";

		}
		sql += "order by a.agentgroup ";

		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("StartDate", StartDate);
		sqlbv1.put("EndDate", EndDate);
		sqlbv1.put("ManageCom", ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv1);
		int maxrow = nSSRS.getMaxRow();
		String[] cols = new String[24];
		if (maxrow > 0) {
			for (int i = 1; i <= maxrow; i++) {

				cols[0] = i + "";
				cols[1] = nSSRS.GetText(i, 1);
				cols[2] = nSSRS.GetText(i, 2);
				cols[3] = nSSRS.GetText(i, 3);
				cols[4] = nSSRS.GetText(i, 4);
				cols[5] = nSSRS.GetText(i, 5);
				cols[6] = nSSRS.GetText(i, 6);
				cols[7] = nSSRS.GetText(i, 7);
				cols[8] = nSSRS.GetText(i, 8);
				cols[9] = nSSRS.GetText(i, 9);
				cols[10] = nSSRS.GetText(i, 10);
				cols[11] = nSSRS.GetText(i, 11);
				cols[12] = nSSRS.GetText(i, 12);
				cols[13] = nSSRS.GetText(i, 13);
				cols[14] = nSSRS.GetText(i, 14);
				cols[15] = nSSRS.GetText(i, 15);
				cols[16] = nSSRS.GetText(i, 16);
				cols[17] = nSSRS.GetText(i, 17);
				cols[18] = nSSRS.GetText(i, 18);
				cols[19] = nSSRS.GetText(i, 19);
				cols[20] = nSSRS.GetText(i, 20);
				cols[21] = nSSRS.GetText(i, 21);
				cols[22] = nSSRS.GetText(i, 22);
				cols[23] = nSSRS.GetText(i, 23);

				txtExport.outArray(cols);
			}

		}

		String fn = txtExport.getFileName();

		return true;
	}

	/**
	 * check BranchLevel
	 * 
	 * @return boolean
	 */
	private boolean check() {
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "NBrealtimeCBLisTxtBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) throws Exception {

		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		VData tVData = new VData();
		tVData.addElement("8621");
		tVData.addElement("2007-06-01");
		tVData.addElement("");
		tVData.addElement("2007-06-30");
		tVData.addElement("01");
		tVData.addElement(tG);

		logger.debug("***********" + tVData.size());
		tVData.addElement(tG);

		NBrealtimeCBLisTxtBL tNBrealtimeCBLisTxtBL = new NBrealtimeCBLisTxtBL();
		if (!tNBrealtimeCBLisTxtBL.submitData(tVData, "")) {
			logger.debug("Wrong!");
		} else {
			logger.debug("Ok!");
		}

	}

}
