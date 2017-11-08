package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * Created by IntelliJ IDEA.
 * User: HanLin
 * Date: 2006-1-4
 * Time: 9:57:08
 * To change this template use File | Settings | File Templates.
 */

public class NBErrorRateReportPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(NBErrorRateReportPrintBL.class);

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	/*
	 * @define globe variable
	 */
	private String mMngCom;
	private String mStartDate;
	private String mSQL;
	private String mEndDate;
	private String mSaleChnl;
	private String mErrorPos;
	private String mPrintType;
	private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

	public NBErrorRateReportPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// if (!cOperate.equals("PRINT")) {
		// buildError("submitData", "不支持的操作字符串");
		// return false;
		// }

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		logger.debug("###############################");
		logger.debug("##########Print Sucess!########");
		logger.debug("###############################");
		return true;
	}

	/**
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mMngCom = (String) mTransferData.getValueByName("ManageCom");
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mErrorPos = (String) mTransferData.getValueByName("ErrorPos");
		mPrintType = (String) mTransferData.getValueByName("PrintType");

		logger.debug("mMngCom==" + mMngCom);
		logger.debug("mStartDate==" + mStartDate);
		logger.debug("mEndDate==" + mEndDate);
		logger.debug("mErrorPos==" + mErrorPos);
		logger.debug("mPrintType==" + mPrintType);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * ***** name : buildError function : Prompt error message return :error
	 * message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "NBErrorRateReportPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * ***** name : getPrintData() function :get print data parameter : null
	 * return :true or false
	 */

	private boolean getPrintData() {
		ListTable tListTable = new ListTable();
		tListTable.setName("List");
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		int chushen = 0;
		int ludan = 0;
		int fuhe = 0;
		int chengping = 0;

		int chushen2 = 0;
		int ludan2 = 0;
		int fuhe2 = 0;
		int chengping2 = 0;

		// 判断是以打印清单还是报表
		if (mPrintType != null && mPrintType.equals("1")) {
			xmlexport.createDocument("NBErrorReport.vts", "printer"); // appoint
			// to a
			// special
			// output
			// .vts
			// file
			// 管理机构如果是86则报表显示分公司的汇总
			if (mMngCom != null && mMngCom.equals("86")) { // 按照总公司查询。
				mSQL = "select comcode,name from ldcom where upcomcode = '86'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(mSQL);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS subSSRS = tExeSQL.execSQL(sqlbv);
				// 循环每个机构取得机构代码
				for (int i = 0; i < subSSRS.getMaxRow(); i++) {
					if (mErrorPos == null || mErrorPos.equals("")) {
						mSQL = "select distinct (select name from ldcom where trim(comcode) = '"
								+ "?Text?"
								+ "'),"
								+ " case errorpos"
								+ " when '1' then"
								+ " '初审差错'"
								+ " when '2' then"
								+ " '录单差错'"
								+ " when '3' then"
								+ " '复核差错'"
								+ " when '4' then"
								+ " '成品保单差错'"
								+ " end,"
								+ " tempa.errorpos,"
								+ " case tempa.erroroperator"
								+ " when '00000000000000000000' then"
								+ " '00000000000000000000'"
								+ " else 'yes'"
								+ "end"
								+ " from (select distinct b.managecom as managecom,"
								+ " a.errorpos,"
								+ " erroroperator as erroroperator,"
								+ " '0000000000'"
								+ " from (select errorpos, erroroperator, otherno"
								+ " from lcerrorreport"
								+ " where erroroperator = '00000000000000000000'"
								+ " and makedate between '"
								+ "?mStartDate?"
								+ "' and "
								+ " '"
								+ "?mEndDate?"
								+ "') a,"
								+ " lccont b"
								+ " where 1 = 1"
								+ " and a.otherno = b.proposalcontno(+)"
								+ " union"
								+ " select distinct b.comcode as managecom,"
								+ " a.errorpos,"
								+ " a.erroroperator,"
								+ " operatorpos"
								+ " from (select distinct errorpos, erroroperator, operatorpos"
								+ " from lcerrorreport"
								+ " where erroroperator <> '00000000000000000000'"
								+ " and makedate between '"
								+ "?mStartDate?"
								+ "' and "
								+ " '"
								+ "?mEndDate?"
								+ "') a,"
								+ " lduser b"
								+ " where 1 = 1"
								+ " and a.erroroperator = b.usercode) tempa"
								+ " where 1 = 1"
								+ " and (managecom like concat('"
								+ "?Text?"
								+ "','%') or managecom is null)"
								+ " order by tempa.errorpos";
					} else {
						mSQL = "select distinct (select name from ldcom where trim(comcode) = '"
								+ "?Text?"
								+ "'),"
								+ " case errorpos"
								+ " when '1' then"
								+ " '初审差错'"
								+ " when '2' then"
								+ " '录单差错'"
								+ " when '3' then"
								+ " '复核差错'"
								+ " when '4' then"
								+ " '成品保单差错'"
								+ " end,"
								+ " tempa.errorpos,"
								+ " case tempa.erroroperator"
								+ " when '00000000000000000000' then"
								+ " '00000000000000000000'"
								+ " else 'yes'"
								+ "end"
								+ " from (select distinct b.managecom as managecom,"
								+ " a.errorpos,"
								+ " erroroperator as erroroperator,"
								+ " '0000000000'"
								+ " from (select errorpos, erroroperator, otherno"
								+ " from lcerrorreport"
								+ " where erroroperator = '00000000000000000000'"
								+ " and errorpos='"
								+ mErrorPos
								+ "'"
								+ " and makedate between '"
								+ "?mStartDate?"
								+ "' and "
								+ " '"
								+ "?mEndDate?"
								+ "') a,"
								+ " lccont b"
								+ " where 1 = 1"
								+ " and a.otherno = b.proposalcontno(+)"
								+ " union"
								+ " select distinct b.comcode as managecom,"
								+ " a.errorpos,"
								+ " a.erroroperator,"
								+ " operatorpos"
								+ " from (select distinct errorpos, erroroperator, operatorpos"
								+ " from lcerrorreport"
								+ " where erroroperator <> '00000000000000000000'"
								+ " and errorpos='"
								+ mErrorPos
								+ "'"
								+ " and makedate between '"
								+ "?mStartDate?"
								+ "' and "
								+ " '"
								+ "?mEndDate?"
								+ "') a,"
								+ " lduser b"
								+ " where 1 = 1"
								+ " and a.erroroperator = b.usercode) tempa"
								+ " where 1 = 1"
								+ " and (managecom like concat('"
								+ "?Text?"
								+ "','%') or managecom is null)"
								+ " order by tempa.errorpos";

					}
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(mSQL);
					sqlbv1.put("Text", subSSRS.GetText(i + 1, 1));
					sqlbv1.put("mStartDate", mStartDate);
					sqlbv1.put("mEndDate", mEndDate);
					SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
					int[][] tOperatorPos = { { 0, 0, 0 }, { 0, 0, 0 },
							{ 0, 0, 0 }, { 0, 0, 0 } };

					for (int j = 0; j < tSSRS.getMaxRow(); j++) {
						String tSQL = "";
						ExeSQL aExeSQL = new ExeSQL();
						SSRS aSSRS = new SSRS();
						String[] tList = new String[10];
						// 根据操作员和

						tList[0] = tSSRS.GetText(j + 1, 1);
						tList[1] = tSSRS.GetText(j + 1, 2);
						tList[2] = "";

						// 查询操作总数
						// 根据操作位置进行判断
						// 1-初审，2-录单，3-复核，4-成品保单
						if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("1")) { // 初审
							logger.debug("j=" + j);
							logger.debug("subSSRS.GetText(i + 1, 1)"
									+ subSSRS.GetText(i + 1, 1));
							if (subSSRS.GetText(i + 1, 1) != null
									&& !subSSRS.GetText(i + 1, 1).equals("")) {
								tSQL = "select count(*) from ljtempfee where policycom like concat('"
										+ "?Text?" + "','%') and tempfeetype = '1' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "'";
							} else {
								tSQL = "select 0 from dual";
							}
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("2")) { // 录单
//							tSQL = "select count(*) from lbmission where activityid in ('0000001098','0000001099') "
							tSQL = "select count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid in('10010016','10010017')) "
									+ " and outdate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "'"
									+ " and MissionProp3 like concat('"
									+ "?Text?" + "','%')";
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("3")) { // 复核
							/*tSQL = "select count(*) from lbmission where activityid in ('0000001001')"*/
							tSQL = "select count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid ='10010003')"
									+ " and outdate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "'"
									+ " and MissionProp8 like concat('"
									+ "?Text?" + "','%')";
						}
						// else if (tSSRS.GetText(j + 1, 3) != null &&
						// tSSRS.GetText(j + 1, 3).equals("4")) { //成品保单
						// tSQL =
						// "select count(*) from lccont where managecom like '" +
						// subSSRS.GetText(i + 1, 1) +
						// "%' and appflag = '1'" +
						// " and signdate between '" + mStartDate +
						// "' and '" + mEndDate + "'";
						// }
						else {
							tSQL = "select 0 from dual";
						}
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(tSQL);
						sqlbv2.put("Text", subSSRS.GetText(i + 1, 1));
						sqlbv2.put("mStartDate", mStartDate);
						sqlbv2.put("mEndDate", mEndDate);
						aSSRS = aExeSQL.execSQL(sqlbv2);
						tList[3] = aSSRS.GetText(1, 1);
						if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("1")) {
							chushen = chushen
									+ Integer.parseInt(aSSRS.GetText(1, 1));
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("2")) {
							ludan = ludan
									+ Integer.parseInt(aSSRS.GetText(1, 1));
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("3")) {
							fuhe = fuhe + Integer.parseInt(aSSRS.GetText(1, 1));
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("4")) {
							chengping = chengping
									+ Integer.parseInt(aSSRS.GetText(1, 1));
						}

						// 查询录单发出问题件
						if (tSSRS.GetText(j + 1, 4) != null
								&& tSSRS.GetText(j + 1, 4).equals(
										"00000000000000000000")) {
							if (subSSRS.GetText(i + 1, 1) != null
									&& !subSSRS.GetText(i + 1, 1).equals("")) {
								tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001099','0000001098') and erroroperator='"
										+ "?Text4?"
										+ "' and errorpos='"
										+ "?Text3?"
										+ "' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
										+ "?Text1?" + "','%'))";
							} else {
								tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001099','0000001098') and erroroperator='"
										+ "?Text4?"
										+ "' and errorpos='"
										+ "?Text3?"
										+ "' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
							}

						} else {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001099','0000001098') "
									+ " and errorpos='"
									+ "?Text3?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '" + "?mEndDate?" + "'";
						}
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSQL);
						sqlbv3.put("Text4", tSSRS.GetText(j + 1, 4));
						sqlbv3.put("Text3", tSSRS.GetText(j + 1, 3));
						sqlbv3.put("Text1", subSSRS.GetText(i + 1, 1));
						sqlbv3.put("mStartDate", mStartDate);
						sqlbv3.put("mEndDate", mEndDate);
						aSSRS = aExeSQL.execSQL(sqlbv3);
						tOperatorPos[Integer.parseInt(tSSRS.GetText(j + 1, 3)) - 1][0] += Integer
								.parseInt(aSSRS.GetText(1, 1));

						// tOperatorPos1 += Integer.parseInt(aSSRS.GetText(1, 1));
						int pos1 = Integer.parseInt(aSSRS.GetText(1, 1));
						tList[4] = aSSRS.GetText(1, 1);

						// 复核发出问题件
						if (tSSRS.GetText(j + 1, 4) != null
								&& tSSRS.GetText(j + 1, 4).equals(
										"00000000000000000000")) {
							if (subSSRS.GetText(i + 1, 1) != null
									&& !subSSRS.GetText(i + 1, 1).equals("")) {
								tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
										+ "?Text4?"
										+ "' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "' and errorpos='"
										+ "?Text3?"
										+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
										+ "?Text1?"
										+ "','%'))";
							} else {
								tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
										+ "?Text4?"
										+ "' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "' and errorpos='"
										+ "?Text3?"
										+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
							}

						} else {
							tSQL = "select count(*) from (select distinct otherno,erroroperator from lcerrorreport where operatorpos in ('0000001001') "
									+ " and errorpos='"
									+ "?Text3?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "'"
									+ " and erroroperator in ";
							if (mErrorPos == null || mErrorPos.equals("")) {
								tSQL = tSQL
										+ "(select "
										+ " tempa.erroroperator"
										+ " from (select distinct b.managecom as managecom,"
										+ " a.errorpos,"
										+ " erroroperator as erroroperator,"
										+ " '0000000000'"
										+ " from (select errorpos, erroroperator, otherno"
										+ " from lcerrorreport"
										+ " where erroroperator = '00000000000000000000'"
										+ " and makedate between '"
										+ "?mStartDate?"
										+ "' and "
										+ " '"
										+ "?mEndDate?"
										+ "') a left join lccont b on a.otherno = b.proposalcontno "
										+ " where 1 = 1"
										+ " union"
										+ " select distinct b.comcode as managecom,"
										+ " a.errorpos,"
										+ " a.erroroperator,"
										+ " operatorpos"
										+ " from (select distinct errorpos, erroroperator, operatorpos"
										+ " from lcerrorreport"
										+ " where erroroperator <> '00000000000000000000'"
										+ " and makedate between '"
										+ "?mStartDate?"
										+ "' and "
										+ " '"
										+ "?mEndDate?"
										+ "') a,"
										+ " lduser b"
										+ " where 1 = 1"
										+ " and a.erroroperator = b.usercode) tempa"
										+ " where 1 = 1"
										+ " and (managecom like concat('"
										+ "?Text1?"
										+ "','%') or managecom is null)" + ")) g";
							} else {
								tSQL = tSQL
										+ "(select"
										+ " tempa.erroroperator"
										+ " from (select distinct b.managecom as managecom,"
										+ " a.errorpos,"
										+ " erroroperator as erroroperator,"
										+ " '0000000000'"
										+ " from (select errorpos, erroroperator, otherno"
										+ " from lcerrorreport"
										+ " where erroroperator = '00000000000000000000'"
										+ " and errorpos='"
										+ "?mErrorPos?"
										+ "'"
										+ " and makedate between '"
										+ "?mStartDate?"
										+ "' and "
										+ " '"
										+ "?mEndDate?"
										+ "') a left join lccont b on a.otherno = b.proposalcontno "
										+ " where 1 = 1"
										+ " union"
										+ " select distinct b.comcode as managecom,"
										+ " a.errorpos,"
										+ " a.erroroperator,"
										+ " operatorpos"
										+ " from (select distinct errorpos, erroroperator, operatorpos"
										+ " from lcerrorreport"
										+ " where erroroperator <> '00000000000000000000'"
										+ " and errorpos='"
										+ "?mErrorPos?"
										+ "'"
										+ " and makedate between '"
										+ "?mStartDate?"
										+ "' and "
										+ " '"
										+ "?mEndDate?"
										+ "') a,"
										+ " lduser b"
										+ " where 1 = 1"
										+ " and a.erroroperator = b.usercode) tempa"
										+ " where 1 = 1"
										+ " and (managecom like concat('"
										+ "?Text1?"
										+ "','%') or managecom is null)" + ")) g";

							}
						}
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql(tSQL);
						sqlbv4.put("mEndDate", mEndDate);
						sqlbv4.put("mStartDate", mStartDate);
						sqlbv4.put("Text1", subSSRS.GetText(i + 1, 1));
						sqlbv4.put("Text3", subSSRS.GetText(i + 1, 3));
						sqlbv4.put("Text4", subSSRS.GetText(i + 1, 4));
						sqlbv4.put("mErrorPos", mErrorPos);
						aSSRS = aExeSQL.execSQL(sqlbv4);

						tOperatorPos[Integer.parseInt(tSSRS.GetText(j + 1, 3)) - 1][1] += Integer
								.parseInt(aSSRS.GetText(1, 1));
						int pos2 = Integer.parseInt(aSSRS.GetText(1, 1));

						tList[5] = aSSRS.GetText(1, 1);
						// 核保发出问题件
						tList[6] = "0";

						/**
						 * todo 保全发出问题件
						 */

						if (tSSRS.GetText(j + 1, 4) != null
								&& tSSRS.GetText(j + 1, 4).equals(
										"00000000000000000000")) {
							if (subSSRS.GetText(i + 1, 1) != null
									&& !subSSRS.GetText(i + 1, 1).equals("")) {
								tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
										+ "?Text4?"
										+ "' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "' and errorpos='"
										+ "?Text3?"
										+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
										+ "?Text1?" + "','%'))";
							} else {
								tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
										+ "?Text4?"
										+ "' and makedate between '"
										+ "?mStartDate?"
										+ "' and '"
										+ "?mEndDate?"
										+ "' and errorpos='"
										+ "?Text3?"
										+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
							}

						} else {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') "
									+ " and errorpos='"
									+ "?Text3?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '" + "?mEndDate?" + "'";
						}
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql(tSQL);
						sqlbv5.put("Text4", tSSRS.GetText(j + 1, 4));
						sqlbv5.put("Text3", tSSRS.GetText(j + 1, 3));
						sqlbv5.put("mStartDate", mStartDate);
						sqlbv5.put("mEndDate", mEndDate);
						sqlbv5.put("Text1", subSSRS.GetText(i + 1, 1));
						aSSRS = aExeSQL.execSQL(sqlbv5);
						tOperatorPos[Integer.parseInt(tSSRS.GetText(j + 1, 3)) - 1][2] += Integer
								.parseInt(aSSRS.GetText(1, 1));

						int pos3 = 0;
						tList[7] = aSSRS.GetText(1, 1);

						// 差错总数
						// if (tSSRS.GetText(i + 1, 5) != null && tSSRS.GetText(i + 1,
						// 5).equals("00000000000000000000")) {
						// if (tSSRS.GetText(i + 1, 6) != null && !tSSRS.GetText(i + 1, 6).equals("")) {
						// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
						// tSSRS.GetText(i + 1, 5) + "' and exists(select 'x' from ljtempfee where
						// ljtempfee.otherno=lcerrorreport.otherno and ljtempfee.managecom like '" +
						// tSSRS.GetText(i + 1, 6) + "%')";
						// } else {
						// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
						// tSSRS.GetText(i + 1, 5) + "' and not exists(select 'x' from ljtempfee where
						// ljtempfee.otherno=lcerrorreport.otherno )";
						// }
						//
						// } else {
						// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
						// tSSRS.GetText(i + 1, 5) + "'";
						// }
						// aSSRS = aExeSQL.execSQL(tSQL);
						// tList[8] = aSSRS.GetText(1, 1);

						// if (tSSRS.GetText(j + 1, 4) != null &&
						// tSSRS.GetText(j + 1,
						// 4).equals("00000000000000000000")) {
						// if (subSSRS.GetText(i + 1, 1) != null &&
						// !subSSRS.GetText(i + 1, 1).equals("")) {
						// tSQL =
						// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
						// tSSRS.GetText(j + 1, 4) +
						// "' and makedate between '" + mStartDate +
						// "' and '" + mEndDate +
						// "' and errorpos='" +
						// tSSRS.GetText(j + 1, 3) + "' and exists(select 'x' from ljtempfee where
						// ljtempfee.otherno=(select contno from lccont where proposalcontno =
						// lcerrorreport.otherno) and ljtempfee.policycom like '" +
						// subSSRS.GetText(i + 1, 1) + "%')";
						// } else {
						// tSQL =
						// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
						// tSSRS.GetText(j + 1, 4) +
						// "' and makedate between '" + mStartDate +
						// "' and '" + mEndDate +
						// "' and errorpos='" +
						// tSSRS.GetText(j + 1, 3) + "' and not exists(select 'x' from ljtempfee where
						// ljtempfee.otherno=(select contno from lccont where proposalcontno =
						// lcerrorreport.otherno))";
						// }
						//
						// } else {
						// tSQL =
						// "select count(*) from (select distinct otherno,erroroperator from
						// lcerrorreport where " +
						// " errorpos='" +
						// tSSRS.GetText(j + 1, 3) +
						// "' and makedate between '" + mStartDate +
						// "' and '" + mEndDate + "'" +
						// " and erroroperator in";
						// if (mErrorPos == null || mErrorPos.equals("")) {
						// tSQL = tSQL +
						// "(select "
						// + " tempa.erroroperator"
						// +
						// " from (select distinct b.managecom as managecom,"
						// + " a.errorpos,"
						// + " erroroperator as erroroperator,"
						// + " '0000000000'"
						// +
						// " from (select errorpos, erroroperator, otherno"
						// + " from lcerrorreport"
						// +
						// " where erroroperator = '00000000000000000000'"
						// + " and makedate between '" +
						// mStartDate + "' and "
						// + " '" + mEndDate + "') a,"
						// + " lccont b"
						// + " where 1 = 1"
						// +
						// " and a.otherno = b.proposalcontno(+)"
						// + " union"
						// +
						// " select distinct b.comcode as managecom,"
						// + " a.errorpos,"
						// + " a.erroroperator,"
						// + " operatorpos"
						// +
						// " from (select distinct errorpos, erroroperator, operatorpos"
						// + " from lcerrorreport"
						// +
						// " where erroroperator <> '00000000000000000000'"
						// + " and makedate between '" +
						// mStartDate + "' and "
						// + " '" + mEndDate + "') a,"
						// + " lduser b"
						// + " where 1 = 1"
						// +
						// " and a.erroroperator = b.usercode) tempa"
						// + " where 1 = 1"
						// + " and (managecom like '" +
						// subSSRS.GetText(i + 1, 1) +
						// "%' or managecom is null)"
						// +
						// "))";
						// } else {
						// tSQL = tSQL +
						// "(select"
						// + " tempa.erroroperator"
						// +
						// " from (select distinct b.managecom as managecom,"
						// + " a.errorpos,"
						// + " erroroperator as erroroperator,"
						// + " '0000000000'"
						// +
						// " from (select errorpos, erroroperator, otherno"
						// + " from lcerrorreport"
						// +
						// " where erroroperator = '00000000000000000000'"
						// + " and errorpos='" + mErrorPos + "'"
						// + " and makedate between '" +
						// mStartDate + "' and "
						// + " '" + mEndDate + "') a,"
						// + " lccont b"
						// + " where 1 = 1"
						// +
						// " and a.otherno = b.proposalcontno(+)"
						// + " union"
						// +
						// " select distinct b.comcode as managecom,"
						// + " a.errorpos,"
						// + " a.erroroperator,"
						// + " operatorpos"
						// +
						// " from (select distinct errorpos, erroroperator, operatorpos"
						// + " from lcerrorreport"
						// +
						// " where erroroperator <> '00000000000000000000'"
						// + " and errorpos='" + mErrorPos + "'"
						// + " and makedate between '" +
						// mStartDate + "' and "
						// + " '" + mEndDate + "') a,"
						// + " lduser b"
						// + " where 1 = 1"
						// +
						// " and a.erroroperator = b.usercode) tempa"
						// + " where 1 = 1"
						// + " and (managecom like '" +
						// subSSRS.GetText(i + 1, 1) +
						// "%' or managecom is null)"
						// +
						// "))";
						//
						// }
						// }
						// aSSRS = aExeSQL.execSQL(tSQL);
						// *********计算总数的时候不应该再去查数据库了 modify by haopan*********************//
						// tList[8] = aSSRS.GetText(1, 1);
						tList[8] = String.valueOf(Integer.parseInt(tList[4])
								+ Integer.parseInt(tList[5])
								+ Integer.parseInt(tList[7]));
						logger.debug("@@@@@@@@tList[4]=" + tList[4]);
						logger.debug("@@@@@@@@tList[5]=" + tList[5]);
						logger.debug("@@@@@@@@tList[7]=" + tList[7]);
						logger.debug("@@@@@@@@tList[8]=" + tList[8]);
						// Integer.parseInt(tList[4])+Integer.parseInt(tList[5]);
						if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("1")) {
							// chushen2 = chushen2 +
							// Integer.parseInt(aSSRS.GetText(1,
							// 1));
							chushen2 = chushen2 + Integer.parseInt(tList[8]);
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("2")) {
							// ludan2 = ludan2 +
							// Integer.parseInt(aSSRS.GetText(1,
							// 1));
							ludan2 = ludan2 + Integer.parseInt(tList[8]);
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("3")) {
							// fuhe2 = fuhe2 +
							// Integer.parseInt(aSSRS.GetText(1, 1));
							fuhe2 = fuhe2 + Integer.parseInt(tList[8]);
						} else if (tSSRS.GetText(j + 1, 3) != null
								&& tSSRS.GetText(j + 1, 3).equals("4")) {
							// chengping2 = chengping2 +
							// Integer.parseInt(aSSRS.GetText(1,
							// 1));
							chengping2 = chengping2
									+ Integer.parseInt(tList[8]);
						}
						double tErrorRate = (Double.parseDouble(tList[8]) / Double
								.parseDouble(tList[3])) * 100;
						tList[9] = mDecimalFormat.format(tErrorRate) + "%";
						tListTable.add(tList);
					}

					// 特殊处理成品保单差错率

					String tSQL = "";
					ExeSQL aExeSQL = new ExeSQL();
					SSRS aSSRS = new SSRS();
					String[] tList = new String[10];
					// 根据操作员和
					tSQL = "select name from ldcom where comcode = '"
							+ "?comcode?" + "'";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(tSQL);
					sqlbv6.put("comcode", subSSRS.GetText(i + 1, 1));
					aSSRS = aExeSQL.execSQL(sqlbv6);
					tList[0] = aSSRS.GetText(1, 1);
					tList[1] = "成品保单差错";
					tList[2] = "";

					// 查询操作总数
					tSQL = "select count(*) from lccont where managecom like concat('"
							+ "?managecom?"
							+ "','%') and appflag = '1'"
							+ " and  signdate between '" + "?mStartDate?"
							+ "' and '" + "?mEndDate?" + "'";
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(tSQL);
					sqlbv7.put("managecom", subSSRS.GetText(i + 1, 1));
					sqlbv7.put("mStartDate", mStartDate);
					sqlbv7.put("mEndDate", mEndDate);
					aSSRS = aExeSQL.execSQL(sqlbv7);
					tList[3] = aSSRS.GetText(1, 1);
					chengping = chengping
							+ Integer.parseInt(aSSRS.GetText(1, 1));

					// 查询录单发出问题件
					// if (tSSRS.GetText(j + 1, 4) != null &&
					// tSSRS.GetText(j + 1,
					// 4).equals("00000000000000000000")) {
					// if (subSSRS.GetText(i + 1, 1) != null &&
					// !subSSRS.GetText(i + 1, 1).equals("")) {
					// tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos
					// in ('0000001099','0000001098') and erroroperator='" +
					// tSSRS.GetText(j + 1, 4) +
					// "' and errorpos='" +
					// tSSRS.GetText(j + 1, 3) +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate + "' and exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=lcerrorreport.otherno and ljtempfee.policycom like '" +
					// subSSRS.GetText(i + 1, 1) + "%')";
					// } else {
					// tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos
					// in ('0000001099','0000001098') and erroroperator='" +
					// tSSRS.GetText(j + 1, 4) +
					// "' and errorpos='" +
					// tSSRS.GetText(j + 1, 3) +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate + "' and not exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=lcerrorreport.otherno )";
					// }
					//
					// } else {
					// tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos
					// in ('0000001099','0000001098') " +
					// " and errorpos='" +
					// tSSRS.GetText(j + 1, 3) +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate + "'";
					// }
					// aSSRS = aExeSQL.execSQL(tSQL);
					// tOperatorPos[Integer.parseInt(tSSRS.GetText(j + 1, 3)) -
					// 1][0] += Integer.parseInt(aSSRS.GetText(1, 1));

					// tOperatorPos1 += Integer.parseInt(aSSRS.GetText(1, 1));
					int pos1 = Integer.parseInt(aSSRS.GetText(1, 1));
					tList[4] = 0 + "";

					// 复核发出问题件
					if (subSSRS.GetText(i + 1, 1) != null
							&& !subSSRS.GetText(i + 1, 1).equals("")) {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
								+ "00000000000000000000"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '"
								+ "?mEndDate?"
								+ "' and errorpos='4"
								+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
								+ "?policycom?" + "','%'))";
					} else {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
								+ "00000000000000000000"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '"
								+ "?mEndDate?"
								+ "' and errorpos='4"
								+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
					}
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(tSQL);
					sqlbv8.put("mStartDate", mStartDate);
					sqlbv8.put("mEndDate", mEndDate);
					sqlbv8.put("policycom", subSSRS.GetText(i + 1, 1));
					aSSRS = aExeSQL.execSQL(sqlbv8);
					// tOperatorPos[Integer.parseInt(tSSRS.GetText(j + 1, 3)) -
					// 1][1] += Integer.parseInt(aSSRS.GetText(1, 1));
					int pos2 = Integer.parseInt(aSSRS.GetText(1, 1));

					tList[5] = aSSRS.GetText(1, 1);
					// 核保发出问题件
					tList[6] = "0";

					/**
					 * todo 保全发出问题件
					 */
					if (subSSRS.GetText(i + 1, 1) != null
							&& !subSSRS.GetText(i + 1, 1).equals("")) {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
								+ "00000000000000000000"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '"
								+ "?mEndDate?"
								+ "' and errorpos='4"
								+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
								+ "?policycom?" + "','%'))";
					} else {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
								+ "00000000000000000000"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '"
								+ "?mEndDate?"
								+ "' and errorpos='4"
								+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
					}
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql(tSQL);
					sqlbv9.put("mStartDate", mStartDate);
					sqlbv9.put("mEndDate", mEndDate);
					sqlbv9.put("policycom", subSSRS.GetText(i + 1, 1));
					aSSRS = aExeSQL.execSQL(sqlbv9);

					// tOperatorPos[Integer.parseInt(tSSRS.GetText(j + 1, 3)) -
					// 1][2] += Integer.parseInt(aSSRS.GetText(1, 1));

					int pos3 = 0;
					tList[7] = aSSRS.GetText(1, 1);

					// 差错总数
					// if (tSSRS.GetText(i + 1, 5) != null && tSSRS.GetText(i + 1,
					// 5).equals("00000000000000000000")) {
					// if (tSSRS.GetText(i + 1, 6) != null && !tSSRS.GetText(i + 1, 6).equals("")) {
					// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "' and exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=lcerrorreport.otherno and ljtempfee.managecom like '" +
					// tSSRS.GetText(i + 1, 6) + "%')";
					// } else {
					// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "' and not exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=lcerrorreport.otherno )";
					// }
					//
					// } else {
					// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "'";
					// }
					// aSSRS = aExeSQL.execSQL(tSQL);
					// tList[8] = aSSRS.GetText(1, 1);

					// if (subSSRS.GetText(i + 1, 1) != null &&
					// !subSSRS.GetText(i + 1, 1).equals("")) {
					// tSQL =
					// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
					// "00000000000000000000" +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate +
					// "' and errorpos='4" +
					// "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select
					// contno from lccont where proposalcontno = lcerrorreport.otherno) and
					// ljtempfee.policycom like '" +
					// subSSRS.GetText(i + 1, 1) + "%')";
					// } else {
					// tSQL =
					// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
					// "00000000000000000000" +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate +
					// "' and errorpos='4" +
					// "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select
					// contno from lccont where proposalcontno = lcerrorreport.otherno) )";
					// }
					//
					// aSSRS = aExeSQL.execSQL(tSQL);
					// tList[8] = aSSRS.GetText(1, 1);
					tList[8] = String.valueOf(Integer.parseInt(tList[4])
							+ Integer.parseInt(tList[5])
							+ Integer.parseInt(tList[7]));
					// chengping2 = chengping2 +
					// Integer.parseInt(aSSRS.GetText(1, 1));
					chengping2 = chengping2 + Integer.parseInt(tList[8]);
					double tErrorRate = (Double.parseDouble(tList[8]) / Double
							.parseDouble(tList[3])) * 100;
					tList[9] = mDecimalFormat.format(tErrorRate) + "%";
					if (!tList[8].equals("0")) {
						tListTable.add(tList);
					}
				}

				// 查询LCErrorReport表取得相关数据
				// mSQL = "select ";
			} else { // 按照总公司以外的机构查询。
				if (mErrorPos == null || mErrorPos.equals("")) {
					mSQL = "select (select name from ldcom where trim(comcode) = trim(tempa.managecom)),"
							+ " case errorpos"
							+ " when '1' then"
							+ " '初审差错'"
							+ " when '2' then"
							+ " '录单差错'"
							+ " when '3' then"
							+ " '复核差错'"
							+ " when '4' then"
							+ " '成品保单差错'"
							+ " end,"
							+ " (select username from lduser where usercode = tempa.erroroperator),"
							+ " tempa.errorpos,"
							+ " tempa.erroroperator,"
							+ " tempa.managecom"
							+ " from (select distinct b.managecom as managecom,"
							+ " a.errorpos,"
							+ " erroroperator as erroroperator,"
							+ " '0000000000'"
							+ " from (select errorpos, erroroperator, otherno"
							+ " from lcerrorreport"
							+ " where erroroperator = '00000000000000000000'"
							+ " and makedate between '"
							+ "?mStartDate?"
							+ "' and "
							+ " '"
							+ "?mEndDate?"
							+ "') a,"
							+ " lccont b"
							+ " where 1 = 1"
							+ " and a.otherno = b.proposalcontno(+)"
							+ " union"
							+ " select distinct b.comcode as managecom,"
							+ " a.errorpos,"
							+ " a.erroroperator,"
							+ " operatorpos"
							+ " from (select distinct errorpos, erroroperator, operatorpos"
							+ " from lcerrorreport"
							+ " where erroroperator <> '00000000000000000000'"
							+ " and makedate between '"
							+ "?mStartDate?"
							+ "' and "
							+ " '"
							+ "?mEndDate?"
							+ "') a,"
							+ " lduser b"
							+ " where 1 = 1"
							+ " and a.erroroperator = b.usercode) tempa"
							+ " where 1 = 1"
							+ " and (managecom like concat('"
							+ "?mMngCom?"
							+ "','%') or managecom is null)"
							+ " order by tempa.managecom, tempa.errorpos, tempa.erroroperator";
				} else {
					mSQL = "select (select name from ldcom where trim(comcode) = trim(tempa.managecom)),"
							+ " case errorpos"
							+ " when '1' then"
							+ " '初审差错'"
							+ " when '2' then"
							+ " '录单差错'"
							+ " when '3' then"
							+ " '复核差错'"
							+ " when '4' then"
							+ " '成品保单差错'"
							+ " end,"
							+ " (select username from lduser where usercode = tempa.erroroperator),"
							+ " tempa.errorpos,"
							+ " tempa.erroroperator,"
							+ " tempa.managecom"
							+ " from (select distinct b.managecom as managecom,"
							+ " a.errorpos,"
							+ " erroroperator as erroroperator,"
							+ " '0000000000'"
							+ " from (select errorpos, erroroperator, otherno"
							+ " from lcerrorreport"
							+ " where erroroperator = '00000000000000000000'"
							+ " and errorpos='"
							+ "?mErrorPos?"
							+ "'"
							+ " and makedate between '"
							+ "?mStartDate?"
							+ "' and "
							+ " '"
							+ "?mEndDate?"
							+ "') a,"
							+ " lccont b"
							+ " where 1 = 1"
							+ " and a.otherno = b.proposalcontno(+)"
							+ " union"
							+ " select distinct b.comcode as managecom,"
							+ " a.errorpos,"
							+ " a.erroroperator,"
							+ " operatorpos"
							+ " from (select distinct errorpos, erroroperator, operatorpos"
							+ " from lcerrorreport"
							+ " where erroroperator <> '00000000000000000000'"
							+ " and errorpos='"
							+ "?mErrorPos?"
							+ "'"
							+ " and makedate between '"
							+ "?mStartDate?"
							+ "' and "
							+ " '"
							+ "?mEndDate?"
							+ "') a,"
							+ " lduser b"
							+ " where 1 = 1"
							+ " and a.erroroperator = b.usercode) tempa"
							+ " where 1 = 1"
							+ " and (managecom like concat('"
							+ "?mMngCom?"
							+ "','%') or managecom is null)"
							+ " order by tempa.managecom, tempa.errorpos, tempa.erroroperator";

				}
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(mSQL);
				sqlbv10.put("mMngCom", mMngCom);
				sqlbv10.put("mEndDate", mEndDate);
				sqlbv10.put("mStartDate", mStartDate);
				sqlbv10.put("mErrorPos", mErrorPos);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(sqlbv10);
				logger.debug("mSQL===" + mSQL);
				int[][] tOperatorPos = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 },
						{ 0, 0, 0 } };

				for (int i = 0; i < tSSRS.getMaxRow(); i++) {
					String tSQL = "";
					ExeSQL aExeSQL = new ExeSQL();
					SSRS aSSRS = new SSRS();
					String[] tList = new String[10];
					// 根据操作员和

					tList[0] = tSSRS.GetText(i + 1, 1);
					tList[1] = tSSRS.GetText(i + 1, 2);
					tList[2] = tSSRS.GetText(i + 1, 3);

					// 查询操作总数
					// 根据操作位置进行判断
					// 1-初审，2-录单，3-复核，4-成品保单
					if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("1")) { // 初审
						logger.debug("i=" + i);
						logger.debug("tSSRS.GetText(i + 1, 6)"
								+ tSSRS.GetText(i + 1, 6));
						if (tSSRS.GetText(i + 1, 6) != null
								&& !tSSRS.GetText(i + 1, 6).equals("")) {
							tSQL = "select count(*) from ljtempfee where policycom like concat('"
									+ "?Text6?"
									+ "','%') and tempfeetype = '1' and makedate between '"
									+ "?mStartDate?" + "' and '" + "?mEndDate?" + "'";
						} else {
							tSQL = "select 0 from dual";
						}
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("2")) { // 录单
//						tSQL = "select count(*) from lbmission where activityid in ('0000001098','0000001099') and defaultoperator='"
						tSQL = "select count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid in('10010016','10010017')) and defaultoperator='"
								+ "?Text5?"
								+ "' and outdate between '"
								+ "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("3")) { // 复核
//						tSQL = "select count(*) from lbmission where activityid in ('0000001001') and defaultoperator='"
						tSQL = "select count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid ='10010003') and defaultoperator='"
								+ "?Text5?"
								+ "' and outdate between '"
								+ "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("4")) { // 成品保单
						tSQL = "select count(*) from lccont where managecom like concat('"
								+ "?mMngCom?"
								+ "','%') and appflag = '1'"
								+ " and signdate between '"
								+ "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					}
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql(tSQL);
					sqlbv11.put("mStartDate", mStartDate);
					sqlbv11.put("mEndDate", mEndDate);
					sqlbv11.put("mMngCom", mMngCom);
					sqlbv11.put("Text5", tSSRS.GetText(i + 1, 5));
					sqlbv11.put("Text6", tSSRS.GetText(i + 1, 6));
					logger.debug("tSQL==" + tSQL);
					aSSRS = aExeSQL.execSQL(sqlbv11);
					tList[3] = aSSRS.GetText(1, 1);
					if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("1")) {
						chushen = chushen
								+ Integer.parseInt(aSSRS.GetText(1, 1));
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("2")) {
						ludan = ludan + Integer.parseInt(aSSRS.GetText(1, 1));
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("3")) {
						fuhe = fuhe + Integer.parseInt(aSSRS.GetText(1, 1));
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("4")) {
						chengping = chengping
								+ Integer.parseInt(aSSRS.GetText(1, 1));
					}

					// 查询录单发出问题件
					if (tSSRS.GetText(i + 1, 5) != null
							&& tSSRS.GetText(i + 1, 5).equals(
									"00000000000000000000")) {
						if (tSSRS.GetText(i + 1, 6) != null
								&& !tSSRS.GetText(i + 1, 6).equals("")) {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001099','0000001098') and erroroperator='"
									+ "?Text5?"
									+ "' and errorpos='"
									+ "?Text4?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
									+ "?Text6?" + "','%'))";
						} else {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001099','0000001098') and erroroperator='"
									+ "?Text5?"
									+ "' and errorpos='"
									+ "?Text4?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
						}

					} else {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001099','0000001098') and erroroperator='"
								+ "?Text5?"
								+ "' and errorpos='"
								+ "?Text4?"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					}
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(tSQL);
					sqlbv12.put("Text5", tSSRS.GetText(i + 1, 5));
					sqlbv12.put("Text4", tSSRS.GetText(i + 1, 4));
					sqlbv12.put("Text6", tSSRS.GetText(i + 1, 6));
					sqlbv12.put("mStartDate", mStartDate);
					sqlbv12.put("mEndDate", mEndDate);
					aSSRS = aExeSQL.execSQL(sqlbv12);
					tOperatorPos[Integer.parseInt(tSSRS.GetText(i + 1, 4)) - 1][0] += Integer
							.parseInt(aSSRS.GetText(1, 1));

					// tOperatorPos1 += Integer.parseInt(aSSRS.GetText(1, 1));
					int pos1 = Integer.parseInt(aSSRS.GetText(1, 1));
					tList[4] = aSSRS.GetText(1, 1);

					// 复核发出问题件
					if (tSSRS.GetText(i + 1, 5) != null
							&& tSSRS.GetText(i + 1, 5).equals(
									"00000000000000000000")) {
						if (tSSRS.GetText(i + 1, 6) != null
								&& !tSSRS.GetText(i + 1, 6).equals("")) {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
									+ "?Text5?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "' and errorpos='"
									+ "?Text4?"
									+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
									+ "?Text6?" + "','%'))";
						} else {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
									+ "?Text5?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "' and errorpos='"
									+ "?Text4?"
									+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) )";
						}

					} else {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000001001') and erroroperator='"
								+ "?Text5?"
								+ "' and errorpos='"
								+ "?Text4?"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					}
					SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
					sqlbv13.sql(tSQL);
					sqlbv13.put("Text4", tSSRS.GetText(i + 1, 4));
					sqlbv13.put("Text5", tSSRS.GetText(i + 1, 5));
					sqlbv13.put("Text6", tSSRS.GetText(i + 1, 6));
					sqlbv13.put("mStartDate", mStartDate);
					sqlbv13.put("mEndDate", mEndDate);
					aSSRS = aExeSQL.execSQL(sqlbv13);
					logger.debug("tSQL==" + tSQL);
					tOperatorPos[Integer.parseInt(tSSRS.GetText(i + 1, 4)) - 1][1] += Integer
							.parseInt(aSSRS.GetText(1, 1));
					int pos2 = Integer.parseInt(aSSRS.GetText(1, 1));

					tList[5] = aSSRS.GetText(1, 1);
					// 核保发出问题件
					tList[6] = "0";

					/**
					 * todo 保全发出问题件
					 */

					if (tSSRS.GetText(i + 1, 5) != null
							&& tSSRS.GetText(i + 1, 5).equals(
									"00000000000000000000")) {
						if (tSSRS.GetText(i + 1, 6) != null
								&& !tSSRS.GetText(i + 1, 6).equals("")) {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
									+ "?Text5?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "' and errorpos='"
									+ "?Text4?"
									+ "' and exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno) and ljtempfee.policycom like concat('"
									+ "?Text6?" + "','%'))";
						} else {
							tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
									+ "?Text5?"
									+ "' and makedate between '"
									+ "?mStartDate?"
									+ "' and '"
									+ "?mEndDate?"
									+ "' and errorpos='"
									+ "?Text4?"
									+ "' and not exists(select 'x' from ljtempfee where ljtempfee.otherno=(select contno from lccont where proposalcontno = lcerrorreport.otherno))";
						}

					} else {
						tSQL = "select count(distinct otherno) from lcerrorreport where operatorpos in ('0000002001') and erroroperator='"
								+ "?Text5?"
								+ "' and errorpos='"
								+ "?Text4?"
								+ "' and makedate between '"
								+ "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					}
					SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
					sqlbv14.sql(tSQL);
					sqlbv14.put("Text4", tSSRS.GetText(i + 1, 4));
					sqlbv14.put("Text5", tSSRS.GetText(i + 1, 5));
					sqlbv14.put("Text6", tSSRS.GetText(i + 1, 6));
					sqlbv14.put("mStartDate", mStartDate);
					sqlbv14.put("mEndDate", mEndDate);
					aSSRS = aExeSQL.execSQL(sqlbv14);
					logger.debug("tSQL==" + tSQL);
					tOperatorPos[Integer.parseInt(tSSRS.GetText(i + 1, 4)) - 1][2] += Integer
							.parseInt(aSSRS.GetText(1, 1));

					int pos3 = 0;
					if (aSSRS != null && aSSRS.getMaxRow() > 0) {
						tList[7] = aSSRS.GetText(1, 1);
					} else {
						tList[7] = "";
					}
					// 差错总数
					// if (tSSRS.GetText(i + 1, 5) != null && tSSRS.GetText(i + 1,
					// 5).equals("00000000000000000000")) {
					// if (tSSRS.GetText(i + 1, 6) != null && !tSSRS.GetText(i + 1, 6).equals("")) {
					// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "' and exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=lcerrorreport.otherno and ljtempfee.managecom like '" +
					// tSSRS.GetText(i + 1, 6) + "%')";
					// } else {
					// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "' and not exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=lcerrorreport.otherno )";
					// }
					//
					// } else {
					// tSQL = "select count(*) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "'";
					// }
					// aSSRS = aExeSQL.execSQL(tSQL);
					// tList[8] = aSSRS.GetText(1, 1);

					// if (tSSRS.GetText(i + 1, 5) != null &&
					// tSSRS.GetText(i + 1, 5).equals("00000000000000000000")) {
					// if (tSSRS.GetText(i + 1, 6) != null &&
					// !tSSRS.GetText(i + 1, 6).equals("")) {
					// tSQL =
					// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate + "' and errorpos='" +
					// tSSRS.GetText(i + 1, 4) + "' and exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=(select contno from lccont where proposalcontno =
					// lcerrorreport.otherno) and ljtempfee.policycom like '" +
					// tSSRS.GetText(i + 1, 6) + "%')";
					// } else {
					// tSQL =
					// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate + "' and errorpos='" +
					// tSSRS.GetText(i + 1, 4) + "' and not exists(select 'x' from ljtempfee where
					// ljtempfee.otherno=(select contno from lccont where proposalcontno =
					// lcerrorreport.otherno))";
					// }
					//
					// } else {
					// tSQL =
					// "select count(distinct otherno) from lcerrorreport where erroroperator='" +
					// tSSRS.GetText(i + 1, 5) + "' and errorpos='" +
					// tSSRS.GetText(i + 1, 4) +
					// "' and makedate between '" + mStartDate +
					// "' and '" + mEndDate + "'";
					// }
					// aSSRS = aExeSQL.execSQL(tSQL);

					// tList[8] = aSSRS.GetText(1, 1);
					tList[8] = String.valueOf(Integer.parseInt(tList[4])
							+ Integer.parseInt(tList[5])
							+ Integer.parseInt(tList[7]));
					logger.debug("**********tList[4]=" + tList[4]);
					logger.debug("**********tList[5]=" + tList[5]);
					logger.debug("**********tList[7]=" + tList[7]);
					logger.debug("**********tList[8]=" + tList[8]);

					if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("1")) {
						// chushen2 = chushen2 +
						// Integer.parseInt(aSSRS.GetText(1, 1));
						chushen2 = chushen2 + Integer.parseInt(tList[8]);
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("2")) {
						// ludan2 = ludan2 +
						// Integer.parseInt(aSSRS.GetText(1, 1));
						ludan2 = ludan2 + Integer.parseInt(tList[8]);
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("3")) {
						// fuhe2 = fuhe2 +
						// Integer.parseInt(aSSRS.GetText(1, 1));
						fuhe2 = fuhe2 + Integer.parseInt(tList[8]);
					} else if (tSSRS.GetText(i + 1, 4) != null
							&& tSSRS.GetText(i + 1, 4).equals("4")) {
						// chengping2 = chengping2 +
						// Integer.parseInt(aSSRS.GetText(1, 1));
						chengping2 = chengping2 + Integer.parseInt(tList[8]);
					}
					double tErrorRate = (Double.parseDouble(tList[8]) / Double
							.parseDouble(tList[3])) * 100;
					tList[9] = mDecimalFormat.format(tErrorRate) + "%";
					if (!tList[8].equals("0")) {
						tListTable.add(tList);
					}
				}
				// 取得汇总数据

				/**
				 * @todo 汇总数据需要根据不同的差错率类型统计不同的汇总值 例如：初审差错就只需要统计前面初审差错的汇总值
				 */

				String[] tErrorPos = { "初审差错", "录单差错", "复核差错", "成品保单差错" };
				for (int i = 0; i < 4; i++) {

					if (mErrorPos != null && !mErrorPos.equals("")
							&& !mErrorPos.equals("" + (i + 1))) {
						continue;
					}
					String[] tList2 = new String[10];
					String tSQL = "";
					tList2[0] = "汇总";
					tList2[1] = tErrorPos[i];
					tList2[2] = "";
					if (i == 0) {
						tSQL = "select count(*) from ljtempfee where makedate between '"
								+ "?mStartDate?"
								+ "' and '"
								+ "?mEndDate?"
								+ "' and tempfeetype = '1' and policycom like concat('"
								+ "?mMngCom?" + "','%')";
					} else if (i == 1) {
//						tSQL = "select count(*) from lbmission where activityid in ('0000001099','0000001098') and missionprop3 like '"
						tSQL = "select count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid in('10010016','10010017')) and missionprop3 like concat('"
								+ "?mMngCom?" + "','%') and outdate between '"
								+ "?mStartDate?" + "' and '" + "?mEndDate?" + "'";
					} else if (i == 2) {
//						tSQL = "select count(*) from lbmission where activityid in ('0000001001') and missionprop8 like '"
						tSQL = "select count(*) from lbmission where activityid in (select activityid from lwactivity  where functionid ='10010003') and missionprop8 like concat('"
								+ "?mMngCom?" + "','%') and outdate between '"
								+ "?mStartDate?" + "' and '" + "?mEndDate?" + "'";
					} else if (i == 3) {
						tSQL =
						// "select count(*) from lbmission where activityid in ('0000001150') and
						// missionprop7 like '" +
						// mMngCom + "%' and makedate between '" +
						// mStartDate + "' and '" + mEndDate + "'";
						"select count(*) from lccont where managecom like concat('"
								+ "?mMngCom?" + "','%') and appflag = '1'"
								+ " and  signdate between '" + "?mStartDate?"
								+ "' and '" + "?mEndDate?" + "'";
					}
					SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
					sqlbv15.sql(tSQL);
					sqlbv15.put("mMngCom", mMngCom);
					sqlbv15.put("mStartDate", mStartDate);
					sqlbv15.put("mEndDate", mEndDate);
					ExeSQL bExeSQL = new ExeSQL();
					SSRS bSSRS = bExeSQL.execSQL(sqlbv15);
					// if (i == 0) {
					// tList2[3] = chushen + "";
					// } else if (i == 1) {
					// tList2[3] = ludan + "";
					// } else if (i == 2) {
					// tList2[3] = fuhe + "";
					// } else if (i == 3) {
					// tList2[3] = chengping + "";
					// }
					tList2[3] = bSSRS.GetText(1, 1);
					tList2[4] = String.valueOf(tOperatorPos[i][0]);
					tList2[5] = String.valueOf(tOperatorPos[i][1]);
					tList2[6] = "";
					tList2[7] = String.valueOf(tOperatorPos[i][2]);
					if (i == 0) {
						tList2[8] = chushen2 + "";
					} else if (i == 1) {
						tList2[8] = ludan2 + "";
					} else if (i == 2) {
						tList2[8] = fuhe2 + "";
					} else if (i == 3) {
						tList2[8] = chengping2 + "";
					}
					double tErrorRate = (Double.parseDouble(tList2[8]) / Double
							.parseDouble(tList2[3])) * 100;
					if (tList2[8].trim().equals("0")) {
						tList2[9] = 0 + "";
					} else {
						tList2[9] = mDecimalFormat.format(tErrorRate) + "%";
					}
					tListTable.add(tList2);
				}

			} // end of 按总公司以外的机构查询
		} else { // 界面选择差错清单
			xmlexport.createDocument("NBErrorList.vts", "printer"); // appoint
			// to a
			// special
			// output
			// .vts file
			if (mErrorPos != null && !mErrorPos.equals("")) { // 差错类型不为空.
				if (!mErrorPos.equals("1")) { // 差错类型不为初审差错.
					String tSQL = " select (select username from lduser where usercode = a.erroroperator) as erroroperator,"
							+ " '',"
							+ " otherno,"
							+ " appntname,"
							+ " agentcode, "
							+ " case operatorpos"
							+ " when '0000001099' then"
							+ " '录单'"
							+ " when '0000001098' then"
							+ " '录单'"
							+ " when '0000001001' then"
							+ " '复核'"
							+ " else"
							+ " '保全'"
							+ " end,"
							+ " errorcontent"
							+ " from lcerrorreport a, lccont b"
							+ " where 1 = 1"
							+ " and a.makedate between '"
							+ "?mStartDate?"
							+ "' and "
							+ " '"
							+ "?mEndDate?"
							+ "'"
							+ " and b.managecom like concat('"
							+ "?mMngCom?"
							+ "','%')"
							+ " and b.prtno = a.otherno";
					tSQL = tSQL + " and a.errorpos = '" + "?mErrorPos?" + "'";
					tSQL = tSQL
							+ " and (exists(select 'x' from lduser where usercode = a.erroroperator and comcode like concat('"
							+ "?mMngCom?"
							+ "','%')) or a.erroroperator = '00000000000000000000')";
					tSQL = tSQL + " order by erroroperator";
					ExeSQL tExeSQL = new ExeSQL();
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql(tSQL);
					sqlbv16.put("mStartDate", mStartDate);
					sqlbv16.put("mEndDate", mEndDate);
					sqlbv16.put("mMngCom", mMngCom);
					sqlbv16.put("mErrorPos", mErrorPos);
					logger.debug("tSQL==" + tSQL);
					SSRS tSSRS = tExeSQL.execSQL(sqlbv16);
					for (int i = 0; i < tSSRS.getMaxRow(); i++) {
						String[] tList = new String[7];
						tList[0] = tSSRS.GetText(i + 1, 1);
						tList[1] = String.valueOf(i + 1);
						tList[2] = tSSRS.GetText(i + 1, 3);
						tList[3] = tSSRS.GetText(i + 1, 4);
						tList[4] = tSSRS.GetText(i + 1, 5);
						tList[5] = tSSRS.GetText(i + 1, 6);
						tList[6] = tSSRS.GetText(i + 1, 7);

						tListTable.add(tList);

					}
				} else { // 初审差错
					String tSQL = " select (select username from lduser where usercode = a.erroroperator) as erroroperator,"
							+ " '',"
							+ " otherno,"
							+ " (select b.appntname from lccont b where b.prtno = a.otherno ),"
							+ " (select c.agentcode from lccont c where c.prtno = a.otherno ), "
							+ " case operatorpos"
							+ " when '0000001099' then"
							+ " '录单'"
							+ " when '0000001098' then"
							+ " '录单'"
							+ " when '0000001001' then"
							+ " '复核'"
							+ " else"
							+ " '保全'"
							+ " end,"
							+ " errorcontent"
							+ " from lcerrorreport a"
							+ " where 1 = 1"
							+ " and a.makedate between '"
							+ "?mStartDate?"
							+ "' and " + " '" + "?mEndDate?" + "'";
					tSQL = tSQL + " and a.errorpos = '1'";
					tSQL = tSQL
							+ " and exists(select 'x' from ljtempfee j where j.otherno = (select contno from lccont where proposalcontno = a.otherno) and j.policycom like concat('"
							+ "?mMngCom?" + "','%'))";
					tSQL = tSQL + " order by erroroperator";
					ExeSQL tExeSQL = new ExeSQL();
					logger.debug("tSQL==" + tSQL);
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					sqlbv17.sql(tSQL);
					sqlbv17.put("mStartDate", mStartDate);
					sqlbv17.put("mEndDate", mEndDate);
					sqlbv17.put("mMngCom", mMngCom);
					SSRS tSSRS = tExeSQL.execSQL(sqlbv17);
					for (int i = 0; i < tSSRS.getMaxRow(); i++) {
						String[] tList = new String[7];
						tList[0] = tSSRS.GetText(i + 1, 1);
						tList[1] = String.valueOf(i + 1);
						tList[2] = tSSRS.GetText(i + 1, 3);
						tList[3] = tSSRS.GetText(i + 1, 4);
						tList[4] = tSSRS.GetText(i + 1, 5);
						tList[5] = tSSRS.GetText(i + 1, 6);
						tList[6] = tSSRS.GetText(i + 1, 7);

						tListTable.add(tList);

					}

				} // end of 初审差错

			} else { // 差错类型为空
			// 初审部分
				int k = 0;
				String tSQL = " select (select username from lduser where usercode = a.erroroperator) as erroroperator,"
						+ " '',"
						+ " otherno,"
						+ " (select b.appntname from lccont b where b.prtno = a.otherno ),"
						+ " (select c.agentcode from lccont c where c.prtno = a.otherno ), "
						+ " case operatorpos"
						+ " when '0000001099' then"
						+ " '录单'"
						+ " when '0000001098' then"
						+ " '录单'"
						+ " when '0000001001' then"
						+ " '复核'"
						+ " else"
						+ " '保全'"
						+ " end,"
						+ " errorcontent"
						+ " from lcerrorreport a"
						+ " where 1 = 1"
						+ " and a.makedate between '"
						+ "?mStartDate?"
						+ "' and "
						+ " '" + "?mEndDate?" + "'";
				tSQL = tSQL + " and a.errorpos = '1'";
				tSQL = tSQL
						+ " and exists(select 'x' from ljtempfee j where j.otherno = (select contno from lccont where proposalcontno = a.otherno) and j.policycom like concat('"
						+ "?mMngCom?" + "','%'))";
				tSQL = tSQL + " order by erroroperator";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(tSQL);
				sqlbv18.put("mStartDate", mStartDate);
				sqlbv18.put("mEndDate", mEndDate);
				sqlbv18.put("mMngCom", mMngCom);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(sqlbv18);
				for (int i = 0; i < tSSRS.getMaxRow(); i++) {
					String[] tList = new String[7];
					tList[0] = tSSRS.GetText(i + 1, 1);
					tList[1] = String.valueOf(i + 1);
					tList[2] = tSSRS.GetText(i + 1, 3);
					tList[3] = tSSRS.GetText(i + 1, 4);
					tList[4] = tSSRS.GetText(i + 1, 5);
					tList[5] = tSSRS.GetText(i + 1, 6);
					tList[6] = tSSRS.GetText(i + 1, 7);
					tListTable.add(tList);
					k = i;
				}

				// 加上非初审部分
				tSQL = " select (select username from lduser where usercode = a.erroroperator) as erroroperator,"
						+ " '',"
						+ " otherno,"
						+ " appntname,"
						+ " agentcode, "
						+ " case operatorpos"
						+ " when '0000001099' then"
						+ " '录单'"
						+ " when '0000001098' then"
						+ " '录单'"
						+ " when '0000001001' then"
						+ " '复核'"
						+ " else"
						+ " '保全'"
						+ " end,"
						+ " errorcontent"
						+ " from lcerrorreport a, lccont b"
						+ " where 1 = 1"
						+ " and a.makedate between '"
						+ "?mStartDate?"
						+ "' and "
						+ " '"
						+ "?mEndDate?"
						+ "'"
						+ " and b.managecom like concat('"
						+ "?mMngCom?" + "','%')" + " and b.prtno = a.otherno";
				tSQL = tSQL + " and a.errorpos != '1'";
				tSQL = tSQL
						+ " and (exists(select 'x' from lduser where usercode = a.erroroperator and comcode like concat('"
						+ "?mMngCom?" + "','%')) or a.erroroperator = '00000000000000000000')";
				tSQL = tSQL + " order by erroroperator";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(tSQL);
				sqlbv19.put("mStartDate", mStartDate);
				sqlbv19.put("mEndDate", mEndDate);
				sqlbv19.put("mMngCom", mMngCom);
				SSRS ttSSRS = tExeSQL.execSQL(sqlbv19);
				for (int j = 0; j < ttSSRS.getMaxRow(); j++) {
					String[] ttList = new String[7];
					ttList[0] = ttSSRS.GetText(j + 1, 1);
					ttList[1] = String.valueOf(k + j + 1);
					ttList[2] = ttSSRS.GetText(j + 1, 3);
					ttList[3] = ttSSRS.GetText(j + 1, 4);
					ttList[4] = ttSSRS.GetText(j + 1, 5);
					ttList[5] = ttSSRS.GetText(j + 1, 6);
					ttList[6] = ttSSRS.GetText(j + 1, 7);

					tListTable.add(ttList);

				}
			}

		} // end of else 界面选择差错清单.

		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("SysDate", PubFun.getCurrentDate());
		// 将数据装入表单
		String[] col2 = new String[6];
		xmlexport.addListTable(tListTable, col2);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("D:\\", "NBErrorRateReportPrintBL");
		// //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	public CErrors getErrors() {
		return null;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		VData tVData = new VData();
		tTransferData.setNameAndValue("StartDate", "2006-04-26");
		tTransferData.setNameAndValue("EndDate", "2006-04-26");
		tTransferData.setNameAndValue("ManageCom", "86");
		tTransferData.setNameAndValue("ErrorPos", "4");
		tTransferData.setNameAndValue("PrintType", "1");
		tVData.add(tTransferData);
		NBErrorRateReportPrintBL tNBErrorRateReportPrintBL = new NBErrorRateReportPrintBL();
		tNBErrorRateReportPrintBL.submitData(tVData, "PRINT");
	}

}
