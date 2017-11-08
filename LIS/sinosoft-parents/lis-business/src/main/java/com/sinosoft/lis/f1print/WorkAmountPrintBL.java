package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */


import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class WorkAmountPrintBL {
private static Logger logger = Logger.getLogger(WorkAmountPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/*
	 * @define globe variable
	 */
	private String strMngCom;

	// private String strAgentCode;
	private String strStartDate;
	private String strEndDate;

	// private String strFlag;
	private String strOperation;
	private String strSaleChnl;

	public WorkAmountPrintBL() {
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
		// strAgentCode = (String)cInputData.get(1);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);
		strOperation = (String) cInputData.get(4);
		logger.debug("strStartDate:" + strStartDate);
		logger.debug("strOperation:" + strOperation);
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
		cError.moduleName = "WorkAmountPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("WorkAmountSatic.vts", "printer");
		ListTable tlistTable = new ListTable();
		tlistTable.setName("Work");
		ListTable alistTable = new ListTable();
		alistTable.setName("WorkAmount");

		String tm="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tm="round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tm="ROUND(SUM(DATEDIFF(a.backdate , a.makedate)*24+(to_number(TIME_TO_SEC(to_date(a.backtime,'HH24:MI:SS'))-TIME_TO_SEC(to_date(a.maketime,'HH24:MI:SS')))/(60*60)))/COUNT(*),2)";
		}
		String strSQL = ""; // choose all Operator from LCPol between start date
							// and end date
		/**
		 * 录单
		 */
		if (strOperation.equals("0")) {
			logger.debug("开始执行strOperation＝" + strOperation + "的sql操作");
			if (strSaleChnl.equals("2")) {
				// if (!strMngCom.equals("86"))
				{
					strSQL = "select (select username from lduser where usercode=a.operator),count(*),"+tm+" from ldconttime a ,LCGrpCont b where a.prtno=b.prtno "
							+ "and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "' and b.managecom like '"
							+ strMngCom
							+ "%' and b.salechnl='"
							+ strSaleChnl
							+ "' and a.businesstype in ('2001','2002') group by a.operator ";
				}
				// else {
				// /**
				// * @todo 如果机构选择为总公司则查询总公司用户的数据
				// */
				// strSQL = "select (select username from lduser where
				// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
				// from ldconttime a ,LCGrpCont b where a.prtno=b.prtno "
				// + "and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "' and b.managecom like '" + strMngCom + "%' and
				// b.salechnl='"
				// + strSaleChnl +
				// "' and a.businesstype in ('2001','2002')" +
				// " and exists(select 'x' from lduser where usercode=a.operator
				// and comcode like '86%')" +
				// " group by a.operator ";
				// }
				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(strSQL);
				int i_count = ssrs.getMaxRow();
				logger.debug("i_count" + i_count);
				if (i_count == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有统计信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[3];

						cols[0] = ssrs.GetText(i, 1);
						cols[1] = ssrs.GetText(i, 2);
						cols[2] = ssrs.GetText(i, 3);

						logger.debug("cols[0]" + cols[0]);
						logger.debug("cols[1]" + cols[1]);
						logger.debug("cols[2]" + cols[2]);
						tlistTable.add(cols);
					}
				}
			} else {
				// if (!strMngCom.equals("86"))
				{
					strSQL = "select (select username from lduser where usercode=a.operator),count(*),"+tm+" from ldconttime a ,lccont b where a.prtno=b.prtno "
							+ "and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "' and b.managecom like '"
							+ strMngCom
							+ "%' and b.salechnl='"
							+ strSaleChnl
							+ "' and a.businesstype in ('1001','1002') group by a.operator ";
				}
				// else {
				// /**
				// * @todo 如果机构选择为总公司则查询总公司用户的数据
				// */
				// strSQL = "select (select username from lduser where
				// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
				// from ldconttime a ,lccont b where a.prtno=b.prtno "
				// + "and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "' and b.managecom like '" + strMngCom + "%' and
				// b.salechnl='"
				// + strSaleChnl +
				// "' and a.businesstype in ('1001','1002')" +
				// " and exists(select 'x' from lduser where usercode=a.operator
				// and comcode='86')" +
				// " group by a.operator ";
				// }
				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(strSQL);
				int i_count = ssrs.getMaxRow();
				logger.debug("i_count" + i_count);
				if (i_count == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有统计信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[3];

						cols[0] = ssrs.GetText(i, 1);
						cols[1] = ssrs.GetText(i, 2);
						cols[2] = ssrs.GetText(i, 3);

						logger.debug("cols[0]" + cols[0]);
						logger.debug("cols[1]" + cols[1]);
						logger.debug("cols[2]" + cols[2]);
						tlistTable.add(cols);
					}
				}
			}
		} else if (strOperation.equals("1")) { // 核保
			logger.debug("开始执行strOperation＝" + strOperation + "的sql操作");
			if (strSaleChnl.equals("2")) {
				// if (!strMngCom.equals("86"))
				{
					// strSQL = "select (select username from lduser where
					// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2),a.operator
					// from ldconttime a ,lccont b where a.prtno=b.prtno "
					// + "and a.backdate between '" + strStartDate + "' and '" +
					// strEndDate
					// + "' and b.managecom like '" + strMngCom + "%' and
					// b.salechnl='"
					// + strSaleChnl +
					// "' and a.businesstype ='1005' and b.uwflag='9' " +
					// " and exists(select 'x' from lduser where
					// usercode=a.operator and comcode like '" +
					// strMngCom + "%')" +
					// " group by a.operator ";
					strSQL = "select (select username from lduser where usercode = a.operator),"
							+ " count(*),"
							+ " round(sum((a.backdate - a.makedate) * 24 +"
							+ " (to_number(to_date(a.backtime, 'HH24:MI:SS') -"
							+ " to_date(a.maketime, 'HH24:MI:SS')) * 24)) /"
							+ " count(*),"
							+ " 2),"
							+ " a.operator"
							+ " from ldconttime a, LCGrpCont b"
							+ " where a.prtno = b.prtno"
							+ " and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "'"
							+ " and b.managecom like '"
							+ strMngCom
							+ "%'"
							+ " and b.salechnl = '"
							+ strSaleChnl
							+ "'"
							+ " and a.businesstype = '2005'"
							+ " and exists (select 'x'"
							+ " from lduser"
							+ " where usercode = a.operator"
							+ " and comcode like '"
							+ strMngCom
							+ "%')"
							+ " and not exists (select 'x'"
							+ " from loprtmanager"
							+ " where otherno = b.prtno"
							+ " and code in"
							+ " ('00', '06', '81', '82', '83', '84', '85', '86', '87', '88', '89'))"
							+ " and not exists"
							+ " (select 'x' from lcuwsendtrace where otherno = b.prtno)"
							+ " group by a.operator";

				}
				// else {

				// strSQL =
				// "select (select username from lduser where usercode =
				// a.operator),"
				// + " count(*),"
				// + " round(sum((a.backdate - a.makedate) * 24 +"
				// + " (to_number(to_date(a.backtime, 'HH24:MI:SS') -"
				// + " to_date(a.maketime, 'HH24:MI:SS')) * 24)) /"
				// + " count(*),"
				// + " 2),"
				// + " a.operator"
				// + " from ldconttime a, LCGrpCont b"
				// + " where a.prtno = b.prtno"
				// + " and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "'"
				// + " and b.managecom like '" + strMngCom + "%'"
				// + " and b.salechnl = '" + strSaleChnl + "'"
				// + " and a.businesstype = '2005'"
				// + " and exists (select 'x'"
				// + " from lduser"
				// + " where usercode = a.operator"
				// + " and comcode='86')"
				// + " and not exists (select 'x'"
				// + " from loprtmanager"
				// + " where otherno = b.prtno"
				// + " and code in"
				// +
				// " ('00', '06', '81', '82', '83', '84', '85', '86', '87',
				// '88', '89'))"
				// + " and not exists"
				// + " (select 'x' from lcuwsendtrace where otherno = b.prtno)"
				// + " group by a.operator";
				//
				// }
				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL2 = new ExeSQL();
				SSRS ssrs2 = exeSQL2.execSQL(strSQL);

				int i_count2 = ssrs2.getMaxRow();
				logger.debug("i_count2是" + i_count2);
				if (i_count2 == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count2; i++) {
						String[] cols1 = new String[5];
						cols1[0] = ssrs2.GetText(i, 1);
						cols1[1] = ssrs2.GetText(i, 2);
						cols1[2] = ssrs2.GetText(i, 3);
						logger.debug("cols1[0]" + cols1[0]);
						logger.debug("cols1[1]" + cols1[1]);
						logger.debug("cols1[2]" + cols1[2]);
						String strSQL1 = "";
						// strSQL1 = "select
						// count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
						// from ldconttime a ,lccont b where a.prtno=b.prtno "
						// + "and a.backdate between '" + strStartDate + "' and
						// '" +
						// strEndDate
						// + "' and b.managecom like '" + strMngCom + "%' and
						// b.salechnl='"
						// + strSaleChnl + "' and a.operator='" +
						// ssrs2.GetText(i, 4)
						// +
						// "' and a.businesstype='1005' and b.uwflag<>'9' " +
						// // " and exists(select 'x' from lduser where
						// usercode=a.operator and comcode='86')" +
						// " group by a.operator ";

						strSQL1 = " select count(*),"
								+ " round(sum((a.backdate - a.makedate) * 24 +"
								+ " (to_number(to_date(a.backtime, 'HH24:MI:SS') -"
								+ " to_date(a.maketime, 'HH24:MI:SS')) * 24)) /"
								+ " count(*),"
								+ " 2)"
								+ " from ldconttime a, LCGrpCont b"
								+ " where a.prtno = b.prtno"
								+ " and a.backdate between '"
								+ strStartDate
								+ "' and '"
								+ strEndDate
								+ "'"
								+ " and b.managecom like '"
								+ strMngCom
								+ "%'"
								+ " and b.salechnl = '"
								+ strSaleChnl
								+ "'"
								+ " and a.operator = '"
								+ ssrs2.GetText(i, 4)
								+ "'"
								+ " and a.businesstype = '2005'"
								+ " and (exists (select 'x'"
								+ " from loprtmanager"
								+ " where otherno = b.prtno"
								+ " and code in"
								+ " ('00', '06', '81', '82', '83', '84', '85', '86', '87', '88', '89'))"
								+ " or exists (select 'x' from lcuwsendtrace where otherno = b.prtno))"
								+ " group by a.operator";

						logger.debug("您的sql1的执行结果是" + strSQL1);
						ExeSQL exeSQL3 = new ExeSQL();
						SSRS ssrs3 = exeSQL3.execSQL(strSQL1);
						int i_count3 = ssrs3.getMaxRow();
						logger.debug("i_count3是" + i_count3);
						if (i_count3 == 0) {
							cols1[3] = "0";
							cols1[4] = "0";
						} else {
							cols1[3] = ssrs3.GetText(1, 1);
							cols1[4] = ssrs3.GetText(1, 2);
							logger.debug("cols1[3]" + cols1[3]);
							logger.debug("cols1[4]" + cols1[4]);
						}
						alistTable.add(cols1);
					}
				}
			} else {

				// if (!strMngCom.equals("86"))
				{

					strSQL = "select (select username from lduser where usercode = a.operator),"
							+ " count(*),"
							+ " round(sum((a.backdate - a.makedate) * 24 +"
							+ " (to_number(to_date(a.backtime, 'HH24:MI:SS') -"
							+ " to_date(a.maketime, 'HH24:MI:SS')) * 24)) /"
							+ " count(*),"
							+ " 2),"
							+ " a.operator"
							+ " from ldconttime a, lccont b"
							+ " where a.prtno = b.prtno"
							+ " and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "'"
							+ " and b.managecom like '"
							+ strMngCom
							+ "%'"
							+ " and b.salechnl = '"
							+ strSaleChnl
							+ "'"
							+ " and a.businesstype = '1005'"
							+ " and exists (select 'x'"
							+ " from lduser"
							+ " where usercode = a.operator"
							+ " and comcode like '"
							+ strMngCom
							+ "%')"
							+ " and not exists (select 'x'"
							+ " from loprtmanager"
							+ " where otherno = b.prtno"
							+ " and code in"
							+ " ('00', '06', '81', '82', '83', '84', '85', '86', '87', '88', '89'))"
							+ " and not exists"
							+ " (select 'x' from lcuwsendtrace where otherno = b.prtno)"
							+ " and not exists"
							+ " (select 'x' from lcreinsurereport where contno = b.contno)"
							+ " group by a.operator";

				}
				// else {
				// strSQL =
				// "select (select username from lduser where usercode =
				// a.operator),"
				// + " count(*),"
				// + " round(sum((a.backdate - a.makedate) * 24 +"
				// + " (to_number(to_date(a.backtime, 'HH24:MI:SS') -"
				// + " to_date(a.maketime, 'HH24:MI:SS')) * 24)) /"
				// + " count(*),"
				// + " 2),"
				// + " a.operator"
				// + " from ldconttime a, lccont b"
				// + " where a.prtno = b.prtno"
				// + " and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "'"
				// + " and b.managecom like '" + strMngCom + "%'"
				// + " and b.salechnl = '" + strSaleChnl + "'"
				// + " and a.businesstype = '1005'"
				// + " and exists (select 'x'"
				// + " from lduser"
				// + " where usercode = a.operator"
				// + " and comcode='86')"
				// + " and not exists (select 'x'"
				// + " from loprtmanager"
				// + " where otherno = b.prtno"
				// + " and code in"
				// +
				// " ('00', '06', '81', '82', '83', '84', '85', '86', '87',
				// '88', '89'))"
				// + " and not exists"
				// + " (select 'x' from lcuwsendtrace where otherno = b.prtno)"
				// + " and not exists"
				// + " (select 'x' from lcreinsurereport where contno =
				// b.contno)"
				// + " group by a.operator";
				//
				// }
				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL2 = new ExeSQL();
				SSRS ssrs2 = exeSQL2.execSQL(strSQL);

				int i_count2 = ssrs2.getMaxRow();
				logger.debug("i_count2是" + i_count2);
				if (i_count2 == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有打印信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count2; i++) {
						String[] cols1 = new String[5];
						cols1[0] = ssrs2.GetText(i, 1);
						cols1[1] = ssrs2.GetText(i, 2);
						cols1[2] = ssrs2.GetText(i, 3);
						logger.debug("cols1[0]" + cols1[0]);
						logger.debug("cols1[1]" + cols1[1]);
						logger.debug("cols1[2]" + cols1[2]);
						String strSQL1 = "";
						// strSQL1 = "select
						// count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
						// from ldconttime a ,lccont b where a.prtno=b.prtno "
						// + "and a.backdate between '" + strStartDate + "' and
						// '" +
						// strEndDate
						// + "' and b.managecom like '" + strMngCom + "%' and
						// b.salechnl='"
						// + strSaleChnl + "' and a.operator='" +
						// ssrs2.GetText(i, 4)
						// +
						// "' and a.businesstype='1005' and b.uwflag<>'9' " +
						// // " and exists(select 'x' from lduser where
						// usercode=a.operator and comcode='86')" +
						// " group by a.operator ";

						strSQL1 = " select count(*),"
								+ " round(sum((a.backdate - a.makedate) * 24 +"
								+ " (to_number(to_date(a.backtime, 'HH24:MI:SS') -"
								+ " to_date(a.maketime, 'HH24:MI:SS')) * 24)) /"
								+ " count(*)," + " 2)"
								+ " from ldconttime a, lccont b"
								+ " where a.prtno = b.prtno"
								+ " and a.backdate between '"
								+ strStartDate
								+ "' and '"
								+ strEndDate
								+ "'"
								+ " and b.managecom like '"
								+ strMngCom
								+ "%'"
								+ " and b.salechnl = '"
								+ strSaleChnl
								+ "'"
								+ " and a.operator = '"
								+ ssrs2.GetText(i, 4)
								+ "'"
								+ " and a.businesstype = '1005'"
								+ " and (exists (select 'x'"
								+ " from loprtmanager"
								+ " where otherno = b.prtno"
								+ " and code in"
								+ " ('00', '06', '81', '82', '83', '84', '85', '86', '87', '88', '89'))"
								+ " or exists (select 'x' from lcuwsendtrace where otherno = b.prtno)"
								+ " or exists"
								+ " (select 'x' from lcreinsurereport where contno = b.contno))"
								+ " group by a.operator";

						logger.debug("您的sql1的执行结果是" + strSQL1);
						ExeSQL exeSQL3 = new ExeSQL();
						SSRS ssrs3 = exeSQL3.execSQL(strSQL1);
						int i_count3 = ssrs3.getMaxRow();
						logger.debug("i_count3是" + i_count3);
						if (i_count3 == 0) {
							cols1[3] = "0";
							cols1[4] = "0";
						} else {
							cols1[3] = ssrs3.GetText(1, 1);
							cols1[4] = ssrs3.GetText(1, 2);
							logger.debug("cols1[3]" + cols1[3]);
							logger.debug("cols1[4]" + cols1[4]);
						}
						alistTable.add(cols1);
					}
				}

			}
		} else if (strOperation.equals("2")) { // 复核
			logger.debug("开始执行strOperation＝" + strOperation + "的sql操作");
			if (strSaleChnl.equals("2")) {
				// if (!strMngCom.equals("86"))
				{
					strSQL = "select (select username from lduser where usercode=a.operator),count(*),"+tm+" from ldconttime a ,LCGrpCont b where a.prtno=b.prtno "
							+ "and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "' and b.managecom like '"
							+ strMngCom
							+ "%' and b.salechnl='"
							+ strSaleChnl
							+ "' and a.businesstype in ('2003')"
							+ " and exists(select 'x' from lduser where usercode=a.operator and comcode like '"
							+ strMngCom + "%')" + " group by a.operator ";
				}
				// else {
				// strSQL = "select (select username from lduser where
				// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
				// from ldconttime a ,LCGrpCont b where a.prtno=b.prtno "
				// + "and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "' and b.managecom like '" + strMngCom + "%' and
				// b.salechnl='"
				// + strSaleChnl +
				// "' and a.businesstype in ('2003')" +
				// " and exists(select 'x' from lduser where usercode=a.operator
				// and comcode='86')" +
				// " group by a.operator ";
				//
				// }

				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(strSQL);
				int i_count = ssrs.getMaxRow();
				logger.debug("i_count" + i_count);
				if (i_count == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有统计信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[3];

						cols[0] = ssrs.GetText(i, 1);
						cols[1] = ssrs.GetText(i, 2);
						cols[2] = ssrs.GetText(i, 3);

						logger.debug("cols[0]" + cols[0]);
						logger.debug("cols[1]" + cols[1]);
						logger.debug("cols[2]" + cols[2]);
						tlistTable.add(cols);
					}
				}
			} else {
				// if (!strMngCom.equals("86"))
				{
					strSQL = "select (select username from lduser where usercode=a.operator),count(*),"+tm+" from ldconttime a ,lccont b where a.prtno=b.prtno "
							+ "and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "' and b.managecom like '"
							+ strMngCom
							+ "%' and b.salechnl='"
							+ strSaleChnl
							+ "' and a.businesstype in ('1003')"
							+ " and exists(select 'x' from lduser where usercode=a.operator and comcode like '"
							+ strMngCom + "%')" + " group by a.operator ";
				}
				// else {
				// strSQL = "select (select username from lduser where
				// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
				// from ldconttime a ,lccont b where a.prtno=b.prtno "
				// + "and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "' and b.managecom like '" + strMngCom + "%' and
				// b.salechnl='"
				// + strSaleChnl +
				// "' and a.businesstype in ('1003')" +
				// " and exists(select 'x' from lduser where usercode=a.operator
				// and comcode='86')" +
				// " group by a.operator ";
				//
				// }

				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(strSQL);
				int i_count = ssrs.getMaxRow();
				logger.debug("i_count" + i_count);
				if (i_count == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有统计信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[3];

						cols[0] = ssrs.GetText(i, 1);
						cols[1] = ssrs.GetText(i, 2);
						cols[2] = ssrs.GetText(i, 3);

						logger.debug("cols[0]" + cols[0]);
						logger.debug("cols[1]" + cols[1]);
						logger.debug("cols[2]" + cols[2]);
						tlistTable.add(cols);
					}
				}
			}
		} else if (strOperation.equals("3")) { // 问题件修改
			logger.debug("开始执行strOperation＝" + strOperation + "的sql操作");
			if (strSaleChnl.equals("2")) {
				// if (!strMngCom.equals("86"))
				{
					strSQL = "select (select username from lduser where usercode=a.operator),count(*),"+tm+" from ldconttime a ,LCGrpCont b where a.prtno=b.prtno "
							+ "and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "' and b.managecom like '"
							+ strMngCom
							+ "%' and b.salechnl='"
							+ strSaleChnl
							+ "' and a.businesstype in ('2004')"
							+ " group by a.operator ";
				}
				// else {
				// strSQL = "select (select username from lduser where
				// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
				// from ldconttime a ,LCGrpCont b where a.prtno=b.prtno "
				// + "and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "' and b.managecom like '" + strMngCom + "%' and
				// b.salechnl='"
				// + strSaleChnl +
				// "' and a.businesstype in ('2004')" +
				// " and exists(select 'x' from lduser where usercode=a.operator
				// and comcode='86')" +
				// " group by a.operator ";
				//
				// }
				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(strSQL);
				int i_count = ssrs.getMaxRow();
				logger.debug("i_count" + i_count);
				if (i_count == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有统计信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[3];

						cols[0] = ssrs.GetText(i, 1);
						cols[1] = ssrs.GetText(i, 2);
						cols[2] = ssrs.GetText(i, 3);

						logger.debug("cols[0]" + cols[0]);
						logger.debug("cols[1]" + cols[1]);
						logger.debug("cols[2]" + cols[2]);
						tlistTable.add(cols);
					}
				}
			} else {
				// if (!strMngCom.equals("86"))
				{
					strSQL = "select (select username from lduser where usercode=a.operator),count(*),"+tm+" from ldconttime a ,lccont b where a.prtno=b.prtno "
							+ "and a.backdate between '"
							+ strStartDate
							+ "' and '"
							+ strEndDate
							+ "' and b.managecom like '"
							+ strMngCom
							+ "%' and b.salechnl='"
							+ strSaleChnl
							+ "' and a.businesstype in ('1004')"
							+ " group by a.operator ";
				}
				// else {
				// strSQL = "select (select username from lduser where
				// usercode=a.operator),count(*),round(sum((a.backdate-a.makedate)*24+(to_number(to_date(a.backtime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*24))/count(*),2)
				// from ldconttime a ,lccont b where a.prtno=b.prtno "
				// + "and a.backdate between '" + strStartDate + "' and '" +
				// strEndDate
				// + "' and b.managecom like '" + strMngCom + "%' and
				// b.salechnl='"
				// + strSaleChnl +
				// "' and a.businesstype in ('1004')" +
				// " and exists(select 'x' from lduser where usercode=a.operator
				// and comcode='86')" +
				// " group by a.operator ";
				//
				// }
				logger.debug("您的sql的执行结果是" + strSQL);

				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSQL.execSQL(strSQL);
				int i_count = ssrs.getMaxRow();
				logger.debug("i_count" + i_count);
				if (i_count == 0) {
					CError tError = new CError();
					tError.moduleName = "PNoticeBillBL";
					tError.functionName = "getDutyGetClmInfo";
					tError.errorMessage = "在该时间段内没有统计信息，请确认起止日期是否正确！！！";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					for (int i = 1; i <= i_count; i++) {
						String[] cols = new String[3];

						cols[0] = ssrs.GetText(i, 1);
						cols[1] = ssrs.GetText(i, 2);
						cols[2] = ssrs.GetText(i, 3);

						logger.debug("cols[0]" + cols[0]);
						logger.debug("cols[1]" + cols[1]);
						logger.debug("cols[2]" + cols[2]);
						tlistTable.add(cols);
					}
				}
			}
		}

		String[] col = new String[3];
		String[] cols2 = new String[5];
		if (strOperation.equals("0")) {
			xmlexport.addDisplayControl("displayNotice1");
			xmlexport.addListTable(tlistTable, col);
		} else if (strOperation.equals("1")) {
			xmlexport.addDisplayControl("displayNotice2");
			xmlexport.addListTable(alistTable, cols2);
		} else if (strOperation.equals("2")) {
			xmlexport.addDisplayControl("displayNotice1");
			xmlexport.addListTable(tlistTable, col);
		} else if (strOperation.equals("3")) {
			xmlexport.addDisplayControl("displayNotice1");
			xmlexport.addListTable(tlistTable, col);
		}

		// appoint to a special output .vts file

		// String[] col = new String[7];
		// xmlexport.addDisplayControl("displaynotice1");
		// xmlexport.addListTable(alistTable, col);
		// texttag.add("MngCom", strMngCom);
		// texttag.add("StartDate", strStartDate);
		// texttag.add("EndDate", strEndDate);
		// if (texttag.size() > 0)
		// {
		// xmlexport.addTextTag(texttag);
		// }

		TextTag texttag = new TextTag();
		if (strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务工作量和时效统计");
		} else if (strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银行代理业务工作量和时效统计");
		} else if (strSaleChnl.equals("2")) {
			texttag.add("ListTitle", "团体业务工作量和时效统计");
		}
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("e:\\", "WorkAmount"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

}
