package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
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
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 刘岩松 function :print InputEfficiency or print CheckEfficiency Business
 *         Logic layer
 * @version 1.0
 * @date 2003-04-04
 */
//

public class ForeceitPrintBL {
private static Logger logger = Logger.getLogger(ForeceitPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strFeeDate;
	private String strMngCom;
	private String strSaleChnl;

	// private String strEndDate;

	public ForeceitPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strFeeDate = (String) cInputData.get(1);
		// strEndDate = (String) cInputData.get(2);
		// tUWStatType = (String) cInputData.get(3);
		strSaleChnl = (String) cInputData.get(2);

		logger.debug("strMngCom:" + strMngCom);
		logger.debug("strFeeDate:" + strFeeDate);
		logger.debug("strSaleChnl:" + strSaleChnl);
		logger.debug("getInputData ok !");
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "UWStaticPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		ListTable alistTable = new ListTable();
		alistTable.setName("List");
		ExeSQL exeSQL = new ExeSQL();
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		String t_sql = "";
		double allprem = 0;
		logger.debug("start getPrintData !");
		DecimalFormat aDF = new DecimalFormat("0.00");
		if (strMngCom.length() == 8) { // 按照营销服务部查询。
			logger.debug("开始执行sql操作");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				t_sql = " select "
						+ " (select a1.otherno from ljtempfee a1 where a1.tempfeeno = a.tempfeeno and rownum = '1'),"
						+ // 投保单号 1
						" a.tempfeeno,"
						+ // 交费凭证号2
						" sum(a.paymoney),"
						+ // 总保费3
						"((case when (select codename from ldcode"
						+ " where codetype = 'paymode'"
						+ " and code = (select paymode from ljtempfeeclass b"
						+ " where b.tempfeeno = a.tempfeeno and rownum = '1')) is not null then (select codename from ldcode"
						+ " where codetype = 'paymode'"
						+ " and code = (select paymode from ljtempfeeclass b"
						+ " where b.tempfeeno = a.tempfeeno and rownum = '1'))  else '未交费' end) ) as p,"
						+ // 交费方式4
						" (select a2.payendyear from ljtempfee a2 where a2.tempfeeno = a.tempfeeno and exists(select 'x' from lmriskapp where riskcode = a2.riskcode and subriskflag = 'M') and rownum = '1'),"
						+ // 交费期间5
						" (select a3.agentcode from ljtempfee a3 where a3.tempfeeno = a.tempfeeno and rownum = '1') ,"
						+ // 业务员代码6
						" (select b.name from laagent b where b.agentcode = trim((select a4.agentcode from ljtempfee a4 where a4.tempfeeno = a.tempfeeno and rownum = '1'))), "
						+ // 业务员姓名7
						" (select username from lduser where usercode = (select a5.operator from ljtempfee a5 where a5.tempfeeno = a.tempfeeno and rownum = '1'))"
						+ // 操作员8
						" from ljtempfee a where a.policycom = '" + "?strMngCom?"
						+ "' and a.makedate = '" + "?strFeeDate?" + "'"
						+ " and a.tempfeetype in ('0','1','5','6')";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				t_sql = " select "
						+ " (select a1.otherno from ljtempfee a1 where a1.tempfeeno = a.tempfeeno limit 0,1),"
						+ // 投保单号 1
						" a.tempfeeno,"
						+ // 交费凭证号2
						" sum(a.paymoney),"
						+ // 总保费3
						"((case when (select codename from ldcode"
						+ " where codetype = 'paymode'"
						+ " and code = (select paymode from ljtempfeeclass b"
						+ " where b.tempfeeno = a.tempfeeno limit 0,1)) is not null then (select codename from ldcode"
						+ " where codetype = 'paymode'"
						+ " and code = (select paymode from ljtempfeeclass b"
						+ " where b.tempfeeno = a.tempfeeno limit 0,1))  else '未交费' end) ) as p,"
						+ // 交费方式4
						" (select a2.payendyear from ljtempfee a2 where a2.tempfeeno = a.tempfeeno and exists(select 'x' from lmriskapp where riskcode = a2.riskcode and subriskflag = 'M') limit 0,1),"
						+ // 交费期间5
						" (select a3.agentcode from ljtempfee a3 where a3.tempfeeno = a.tempfeeno limit 0,1) ,"
						+ // 业务员代码6
						" (select b.name from laagent b where b.agentcode = trim((select a4.agentcode from ljtempfee a4 where a4.tempfeeno = a.tempfeeno limit 0,1))), "
						+ // 业务员姓名7
						" (select username from lduser where usercode = (select a5.operator from ljtempfee a5 where a5.tempfeeno = a.tempfeeno limit 0,1))"
						+ // 操作员8
						" from ljtempfee a where a.policycom = '" + "?strMngCom?"
						+ "' and a.makedate = '" + "?strFeeDate?" + "'"
						+ " and a.tempfeetype in ('0','1','5','6')";
			}
			// " and a.salechnl = '" + strSaleChnl + "'";
			if (strSaleChnl != null && !strSaleChnl.equals("")) {
				if (strSaleChnl.equals("1")) {
					t_sql = t_sql
							+ " and exists (select 'x' from lmriskapp m "
							+ " where m.riskcode = a.riskcode and m.riskprop = 'I'"
							+ " and m.poltype = 'P')";
				}
				if (strSaleChnl.equals("2")) {
					t_sql = t_sql
							+ " and exists (select 'x' from lmriskapp m "
							+ " where m.riskcode = a.riskcode and m.riskprop = 'G'"
							+ " and m.poltype = 'P')";
				}
				if (strSaleChnl.equals("3")) {
					t_sql = t_sql
							+ " and exists (select 'x' from lmriskapp m "
							+ " where m.riskcode = a.riskcode and m.riskprop = 'Y'"
							+ " and m.poltype = 'P')";
				}
				if (strSaleChnl.equals("4")) {
					t_sql = t_sql + " and exists (select 'x' from lmriskapp m "
							+ " where m.riskcode = a.riskcode "
							+ " and m.poltype = 'C')";
				}
				if (strSaleChnl.equals("5")) {
					t_sql = t_sql + " and exists (select 'x' from lmriskapp m "
							+ " where m.riskcode = a.riskcode "
							+ " and m.riskprop = 'T')";
				}

			}

			t_sql = t_sql + " group by a.tempfeeno order by p";
			// " select aa," +
			// " ab," +
			// " ac," +
			// " nvl((select codename" +
			// " from ldcode" +
			// " where codetype = 'paymode'" +
			// " and code = (select paymode" +
			// " from ljtempfeeclass" +
			// " where otherno = aa" +
			// " and rownum = 1)),'未交费'), " +
			// " nvl((select lduser.username" +
			// " from lduser" +
			// " where usercode in (select operator" +
			// " from ljtempfeeclass" +
			// " where otherno = aa" +
			// " and rownum = 1)),'')," +
			// " case (select lcpol.payintv" +
			// " from lcpol" +
			// " where lcpol.prtno = aa" +
			// " and lcpol.polno = lcpol.mainpolno)" +
			// " when 0 then" +
			// " '趸缴'" +
			// " when 1 then" +
			// " '月缴'" +
			// " when 3 then" +
			// " '季缴'" +
			// " when 6 then" +
			// " '半年缴'" +
			// " when 12 then" +
			// " '年缴'" +
			// " end," +
			// " (select lcpol.payendyear || lcpol.payendyearflag" +
			// " from lcpol" +
			// " where lcpol.prtno = aa" +
			// " and lcpol.polno = lcpol.mainpolno)" +
			// " from" +
			// " (select b.proposalcontno as aa," +
			// " a.appntname as ab," +
			// " sum(a.prem) as ac" +
			// " from lcpol a, lccont b" +
			// " where a.managecom like '" + strMngCom + "%'" +
			// " and a.makedate = '" + strFeeDate + "'" +
			// " and a.salechnl = '" + strSaleChnl + "'" +
			// " and a.appflag <> '2'" +
			// " and b.contno = a.contno" +
			// " and (b.inputdate = '" + strFeeDate +
			// "' or b.inputdate is null)" +
			// " group by b.proposalcontno, a.appntname)";
			sqlbv1.sql(t_sql);
			sqlbv1.put("strMngCom", strMngCom);
			sqlbv1.put("strFeeDate", strFeeDate);
			TextTag texttag = new TextTag();
			xmlexport.createDocument("foreceitlist.vts", "printer");
			logger.debug("您的t_sql的执行结果是" + t_sql);
			ExeSQL exeSQL1 = new ExeSQL();
			SSRS ssrs = exeSQL1.execSQL(sqlbv1);
			int i_count = ssrs.getMaxRow();
			if (i_count == 0) {
				CError tError = new CError();
				tError.moduleName = "ForeceitPrintBL";
				tError.functionName = "getDutyGetClmInfo";
				tError.errorMessage = "在该天没有预收件！！！";
				this.mErrors.addOneError(tError);
				return false;
			}

			String[] cola = new String[9];
			cola[0] = "中支:";
			cola[1] = getUpComName(strMngCom);
			cola[2] = "营销服务部:";
			cola[3] = getComName(strMngCom);
			alistTable.add(cola);

			String[] colb = new String[9];
			colb[0] = "序号";
			colb[1] = "投保单号";
			colb[2] = "交费凭证号";
			colb[3] = "总保费";
			colb[4] = "交费方式";
			colb[5] = "交费期间";
			colb[6] = "业务员代码";
			colb[7] = "业务员姓名";
			colb[8] = "操作员";
			alistTable.add(colb);

			logger.debug("满足条件的记录共有" + i_count + "条");
			for (int i = 1; i <= i_count; i++) {
				String[] cols = new String[9];
				cols[0] = Integer.toString(i);
				cols[1] = ssrs.GetText(i, 1);
				cols[2] = ssrs.GetText(i, 2);
				cols[3] = ssrs.GetText(i, 3);
				allprem = allprem + Double.parseDouble(ssrs.GetText(i, 3));
				cols[4] = ssrs.GetText(i, 4);
				cols[5] = ssrs.GetText(i, 5);
				cols[6] = ssrs.GetText(i, 6);
				cols[7] = ssrs.GetText(i, 7);
				cols[8] = ssrs.GetText(i, 8);

				alistTable.add(cols);
			}
			String[] col = new String[9];
			xmlexport.addListTable(alistTable, col);
			String type = "";
			if (strSaleChnl.equals("1")) {
				type = "个人业务";
			} else if (strSaleChnl.equals("3")) {
				type = "银行代理";
			} else if (strSaleChnl.equals("4")) {
				type = "卡式业务";
			} else if (strSaleChnl.equals("5")) {
				type = "个险直销";
			} else {
				type = "";
			}

			texttag.add("TYPE", type);
			texttag.add("Num", i_count);
			texttag.add("NumPrem", aDF.format(allprem));
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
		} else if (strMngCom.equals("86")) {
			CError tError = new CError();
			tError.moduleName = "ForeceitPrintBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "不能按照总公司打印！！！";
			this.mErrors.addOneError(tError);
			return false;
		} else { // 按照中支或分公司查询.
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String sql = "select comcode from ldcom where upcomcode = '"
					+ "?strMngCom?" + "'";
			sqlbv2.sql(sql);
			sqlbv2.put("strMngCom", strMngCom);
			SSRS LOWSSRS = new SSRS();
			LOWSSRS = exeSQL.execSQL(sqlbv2);
			int all_count = 0;
			int i_count = 0;
			boolean flag = false;
			for (int p = 1; p <= LOWSSRS.getMaxRow(); p++) {
				// t_sql = " select distinct a.otherno," + //投保单号 1
				// " a.tempfeeno," + //交费凭证号2
				// " a.paymoney," + //总保费3
				// "(nvl((select codename from ldcode" +
				// " where codetype = 'paymode'" +
				// " and code = (select paymode from ljtempfeeclass b" +
				// " where b.tempfeeno = a.tempfeeno )),'未交费') ) as p ," +
				// //交费方式4
				// " a.payendyear," + //交费期间5
				// " a.agentcode," + //业务员代码6
				// " (select b.name from laagent b where b.agentcode =
				// trim(a.agentcode))," + //业务员姓名7
				// " (select username from lduser where usercode = a.operator) "
				// + //操作员8
				// " from ljtempfee a where a.managecom like '" +
				// LOWSSRS.GetText(p, 1) +
				// "%' and a.makedate = '" + strFeeDate + "'" +
				// " and a.tempfeetype in ('0','1','5','6')";
				//
				//
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					t_sql = " select "
							+ " (select a1.otherno from ljtempfee a1 where a1.tempfeeno = a.tempfeeno and rownum = '1'),"
							+ // 投保单号 1
							" a.tempfeeno,"
							+ // 交费凭证号2
							" sum(a.paymoney),"
							+ // 总保费3
							"((case when (select codename from ldcode"
							+ " where codetype = 'paymode'"
							+ " and code = (select paymode from ljtempfeeclass b"
							+ " where b.tempfeeno = a.tempfeeno and rownum = '1')) is not null then (select codename from ldcode"
							+ " where codetype = 'paymode'"
							+ " and code = (select paymode from ljtempfeeclass b"
							+ " where b.tempfeeno = a.tempfeeno and rownum = '1'))  else '未交费' end) ) as p,"
							+ // 交费方式4
							" (select a2.payendyear from ljtempfee a2 where a2.tempfeeno = a.tempfeeno and exists(select 'x' from lmriskapp where riskcode = a2.riskcode and subriskflag = 'M') and rownum = '1'),"
							+ // 交费期间5
							" (select a3.agentcode from ljtempfee a3 where a3.tempfeeno = a.tempfeeno and rownum = '1') ,"
							+ // 业务员代码6
							" (select b.name from laagent b where b.agentcode = trim((select a4.agentcode from ljtempfee a4 where a4.tempfeeno = a.tempfeeno and rownum = '1'))), "
							+ // 业务员姓名7
							" (select username from lduser where usercode = (select a5.operator from ljtempfee a5 where a5.tempfeeno = a.tempfeeno and rownum = '1'))"
							+ // 操作员8
							" from ljtempfee a where a.policycom like concat('"
							+ "?policycom?" + "','%') and a.makedate = '"
							+ "?strFeeDate?" + "'"
							+ " and a.tempfeetype in ('0','1','5','6')";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					t_sql = " select "
							+ " (select a1.otherno from ljtempfee a1 where a1.tempfeeno = a.tempfeeno limit 0,1),"
							+ // 投保单号 1
							" a.tempfeeno,"
							+ // 交费凭证号2
							" sum(a.paymoney),"
							+ // 总保费3
							"((case when (select codename from ldcode"
							+ " where codetype = 'paymode'"
							+ " and code = (select paymode from ljtempfeeclass b"
							+ " where b.tempfeeno = a.tempfeeno limit 0,1)) is not null then (select codename from ldcode"
							+ " where codetype = 'paymode'"
							+ " and code = (select paymode from ljtempfeeclass b"
							+ " where b.tempfeeno = a.tempfeeno limit 0,1))  else '未交费' end) ) as p,"
							+ // 交费方式4
							" (select a2.payendyear from ljtempfee a2 where a2.tempfeeno = a.tempfeeno and exists(select 'x' from lmriskapp where riskcode = a2.riskcode and subriskflag = 'M') limit 0,1),"
							+ // 交费期间5
							" (select a3.agentcode from ljtempfee a3 where a3.tempfeeno = a.tempfeeno limit 0,1) ,"
							+ // 业务员代码6
							" (select b.name from laagent b where b.agentcode = trim((select a4.agentcode from ljtempfee a4 where a4.tempfeeno = a.tempfeeno limit 0,1))), "
							+ // 业务员姓名7
							" (select username from lduser where usercode = (select a5.operator from ljtempfee a5 where a5.tempfeeno = a.tempfeeno limit 0,1))"
							+ // 操作员8
							" from ljtempfee a where a.policycom like concat('"
							+ "?policycom?" + "','%') and a.makedate = '"
							+ "?strFeeDate?" + "'"
							+ " and a.tempfeetype in ('0','1','5','6')";
				}
				
				// " and a.salechnl = '" + strSaleChnl + "'";
				if (strSaleChnl != null && !strSaleChnl.equals("")) {
					if (strSaleChnl.equals("1")) {
						t_sql = t_sql
								+ " and exists (select 'x' from lmriskapp m "
								+ " where m.riskcode = a.riskcode and m.riskprop = 'I'"
								+ " and m.poltype = 'P')";
					}
					if (strSaleChnl.equals("2")) {
						t_sql = t_sql
								+ " and exists (select 'x' from lmriskapp m "
								+ " where m.riskcode = a.riskcode and m.riskprop = 'G'"
								+ " and m.poltype = 'P')";
					}
					if (strSaleChnl.equals("3")) {
						t_sql = t_sql
								+ " and exists (select 'x' from lmriskapp m "
								+ " where m.riskcode = a.riskcode and m.riskprop = 'Y'"
								+ " and m.poltype = 'P')";
					}
					if (strSaleChnl.equals("4")) {
						t_sql = t_sql
								+ " and exists (select 'x' from lmriskapp m "
								+ " where m.riskcode = a.riskcode "
								+ " and m.poltype = 'C')";
					}
					if (strSaleChnl.equals("5")) {
						t_sql = t_sql
								+ " and exists (select 'x' from lmriskapp m "
								+ " where m.riskcode = a.riskcode "
								+ " and m.riskprop = 'T')";
					}
				}

				t_sql = t_sql + " group by a.tempfeeno order by p";
				xmlexport.createDocument("foreceitlist.vts", "printer");
				logger.debug("您的t_sql的执行结果是" + t_sql);
				sqlbv3.sql(t_sql);
				sqlbv3.put("policycom", LOWSSRS.GetText(p, 1));
				sqlbv3.put("strFeeDate", strFeeDate);
				ExeSQL exeSQL1 = new ExeSQL();
				SSRS ssrs = exeSQL1.execSQL(sqlbv3);
				i_count = ssrs.getMaxRow();
				if (i_count == 0) {
					continue;
				} else {
					flag = true;
				}

				String[] cola = new String[9];
				if (strMngCom.length() == 6) {
					cola[0] = "中支:";
					cola[2] = "营销服务部:";
				} else {
					cola[0] = "分公司:";
					cola[2] = "中支:";
				}

				cola[1] = getComName(strMngCom);
				cola[3] = getComName(LOWSSRS.GetText(p, 1));
				alistTable.add(cola);

				String[] colb = new String[9];
				colb[0] = "序号";
				colb[1] = "投保单号";
				colb[2] = "交费凭证号";
				colb[3] = "总保费";
				colb[4] = "交费方式";
				colb[5] = "交费期间";
				colb[6] = "业务员代码";
				colb[7] = "业务员姓名";
				colb[8] = "操作员";
				alistTable.add(colb);

				logger.debug("满足条件的记录共有" + i_count + "条");
				for (int i = 1; i <= i_count; i++) {
					String[] cols = new String[9];
					cols[0] = Integer.toString(i);
					cols[1] = ssrs.GetText(i, 1);
					cols[2] = ssrs.GetText(i, 2);
					cols[3] = ssrs.GetText(i, 3);
					allprem = allprem + Double.parseDouble(ssrs.GetText(i, 3));
					cols[4] = ssrs.GetText(i, 4);
					cols[5] = ssrs.GetText(i, 5);
					cols[6] = ssrs.GetText(i, 6);
					cols[7] = ssrs.GetText(i, 7);
					cols[8] = ssrs.GetText(i, 8);
					alistTable.add(cols);
				}

				String[] colc = new String[9];
				colc[0] = "";
				colc[1] = "";
				colc[2] = "";
				colc[3] = "";
				colc[4] = "";
				colc[5] = "";
				colc[6] = "";
				colc[7] = "";
				colc[8] = "";
				alistTable.add(colc);

				all_count = all_count + i_count;
			}
			if (flag == false) {
				CError tError = new CError();
				tError.moduleName = "ForeceitPrintBL";
				tError.functionName = "getDutyGetClmInfo";
				tError.errorMessage = "在该天没有预收件！！！";
				this.mErrors.addOneError(tError);
				return false;
			}

			String[] col = new String[9];
			xmlexport.addListTable(alistTable, col);
			String type = "";
			if (strSaleChnl.equals("1")) {
				type = "个人业务";
			} else if (strSaleChnl.equals("3")) {
				type = "银行代理";
			} else if (strSaleChnl.equals("4")) {
				type = "卡式业务";
			} else if (strSaleChnl.equals("5")) {
				type = "个险直销";
			} else {
				type = "";
			}
			logger.debug("**************************");
			logger.debug("**************************");
			logger.debug("**************************");
			logger.debug("**************************");
			logger.debug("strSaleChnl=" + strSaleChnl);
			logger.debug("**************************");
			logger.debug("**************************");
			logger.debug("**************************");
			logger.debug("**************************");

			TextTag texttag = new TextTag();
			texttag.add("TYPE", type);
			texttag.add("Num", all_count);
			texttag.add("NumPrem", aDF.format(allprem));
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}

		}

		// xmlexport.outputDocumentToFile("e:\\","PNoticeBill");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public String getUpComName(String comcode) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(comcode);
		if (!tLDComDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			tLDComDB.setComCode(tLDComDB.getUpComCode());
			if (!tLDComDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return tLDComDB.getName();
			}
		}
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}
}
