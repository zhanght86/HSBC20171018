/**
 * <p>Title:需要输入统计项的的扫描报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：保全日结清单（个险）</p>
 * <p>bq2: 保全月结清单（个险)</p>
 * <p>bq3: 保全日结清单（法人)</p>
 * <p>bq4: 保全月结清单（法人)</p>
 * <p>bq5: 保全日结清单（银代)</p>
 * <p>bq6: 保全月结清单(（银代)</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * <p>add date and  月结 加金额</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

public class BQCheckBL {
private static Logger logger = Logger.getLogger(BQCheckBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mBQCode = "";

	private String[] mDay = null;

	private String mScanOper = "";

	private String mDefineCode = "";

	private String mManageCom = "";

	private String mOpt = "";

	  private String mAppOperator="";
	  private String mConfOperator="";	
	/**构造函数*/
	public BQCheckBL() {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理
	 * 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) { //打印

		//全局变量
		mDay = (String[]) cInputData.get(0);
		mManageCom = (String) cInputData.get(1);
		mBQCode = (String) cInputData.get(2);
		mDefineCode = (String) cInputData.get(3);
		mOpt = (String) cInputData.get(4);
	    mAppOperator=(String)cInputData.get(5);
	    mConfOperator=(String)cInputData.get(6);
		mGlobalInput.setSchema((GlobalInput) cInputData.get(7));
		if (mDay == null) {
			buildError("getInputData", "没有得到足够的信息！");

			return false;
		}
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");

			return false;
		}
		if (mDefineCode == "") {
			buildError("mDefineCode", "没有得到足够的信息！");

			return false;
		}


		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "BQCheckBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**传输数据的公共方法*/
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
			buildError("submitData", "不支持的操作字符串");

			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		if (!getPrintDataPay()) {
			return false;
		}

		return true;
	}

	private boolean getPrintDataPay() {
		logger.debug("报表代码类型qqqq：" + mDefineCode);

		XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
		FinDayTool tFinDayTool = new FinDayTool();
		ListTable tlistTable = new ListTable();
		String[] strArr = null;
		String xmlname = "保全报表" + mDefineCode;
		String displayname = "display" + mDefineCode;
		logger.debug("displayneme:" + displayname);

		//set:
		LDCodeDB tLDcodeDB = new LDCodeDB();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String msql = "select * from LDcode where codetype in ('"+"?var1?"+"','"+"?var2?"+"')";
		sqlbv.sql(msql);
		sqlbv.put("var1","edortypecode1");
		sqlbv.put("var2", "edortypecode");
		LDCodeSet tLDCodeSet = tLDcodeDB.executeQuery(sqlbv);
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();

		tlistTable.setName("BQ");

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		double TotalMoney = 0.0;
		int TotalNum = 0;
		String bq_sql = "";
		
		//团单合计
		if (mDefineCode.equals("bq10")) {
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				bq_sql = " select a.edortype, a.grpcontno,c.edorconfno,"
		                   + " (select lcgrppol.riskcode from lcgrppol, lmriskapp where grpcontno = b.grpcontno "
		                   + " and lcgrppol.riskcode = lmriskapp.riskcode and lmriskapp.subriskflag = 'M' and rownum = 1), "
		                   + " b.Managecom,b.grpname,c.getmoney,a.Operator,c.edorappdate, "		                     
		                   + " (select case when c.ApproveOperator is null then  a.Operator else  c.ApproveOperator end from dual),(select case when c.confdate is null then  a.edorvalidate else c.confdate end from dual)"
		                   + " from lpgrpedoritem a, lcgrpcont b, lpedorapp c "
		                   + " where a.grpcontno = b.grpcontno and a.grpcontno = c.otherno and a.edoracceptno = c.edoracceptno and c.otherno = b.grpcontno and a.EdorState = '0' and b.SaleChnl = '01'"
		                   + ReportPubFun.getWherePart("c.Operator", ReportPubFun.getParameterStr(mAppOperator, "?mAppOperator?"))
		       				+ ReportPubFun.getWherePart("(select  case when c.ApproveOperator is null then  c.Operator else  c.ApproveOperator end ) from dual)", ReportPubFun.getParameterStr(mConfOperator, "?mConfOperator?"))
		       				+ ReportPubFun.getWherePart("a.edortype", ReportPubFun.getParameterStr(mBQCode,"?mBQCode?"))
		       				+ ReportPubFun.getWherePartLike("a.ManageCom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		       				+ ReportPubFun.getWherePart("(select  case when c.confdate is null then  a.edorvalidate else c.confdate end  from dual)", ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1)	
						   + " order by 11,1";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				bq_sql = " select a.edortype, a.grpcontno,c.edorconfno,"
		                   + " (select lcgrppol.riskcode from lcgrppol, lmriskapp where grpcontno = b.grpcontno "
		                   + " and lcgrppol.riskcode = lmriskapp.riskcode and lmriskapp.subriskflag = 'M' limit 0,1), "
		                   + " b.Managecom,b.grpname,c.getmoney,a.Operator,c.edorappdate, "		                     
		                   + " (select case when c.ApproveOperator is null then  a.Operator else  c.ApproveOperator end from dual),(select case when c.confdate is null then  a.edorvalidate else c.confdate end from dual)"
		                   + " from lpgrpedoritem a, lcgrpcont b, lpedorapp c "
		                   + " where a.grpcontno = b.grpcontno and a.grpcontno = c.otherno and a.edoracceptno = c.edoracceptno and c.otherno = b.grpcontno and a.EdorState = '0' and b.SaleChnl = '01'"
		                   + ReportPubFun.getWherePart("c.Operator", ReportPubFun.getParameterStr(mAppOperator, "?mAppOperator?"))
		       				+ ReportPubFun.getWherePart("(select  case when c.ApproveOperator is null then  c.Operator else  c.ApproveOperator end ) from dual)", ReportPubFun.getParameterStr(mConfOperator, "?mConfOperator?"))
		       				+ ReportPubFun.getWherePart("a.edortype", ReportPubFun.getParameterStr(mBQCode,"?mBQCode?"))
		       				+ ReportPubFun.getWherePartLike("a.ManageCom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
		       				+ ReportPubFun.getWherePart("(select  case when c.confdate is null then  a.edorvalidate else c.confdate end  from dual)", ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1)	
						   + " order by 11,1";
			}
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(bq_sql);
			sqlbv2.put("mAppOperator", mAppOperator);
			sqlbv2.put("mConfOperator", mConfOperator);
			sqlbv2.put("mBQCode", mBQCode);
			sqlbv2.put("mManageCom", mManageCom);
			sqlbv2.put("date1",  mDay[0]);
			sqlbv2.put("date2", mDay[1]);
					
			xmlexport.createDocument("BQGrpDayCount.vts", "printer");
			xmlexport.addDisplayControl(displayname);
			logger.debug("保全bq10--sql:" + bq_sql);
			BQSSRS = BQExeSQL.execSQL(sqlbv2);
			logger.debug("大小：" + BQSSRS.getMaxRow());
			String tFLagDate = "3001-01-01";
			int tIntv = 0;
			boolean isDayChanged = true;
			for (int i = 1; i <= BQSSRS.getMaxRow(); i++) {
				strArr = new String[13];
				strArr[1] = BQSSRS.GetText(i, 1);
				strArr[2] = BQSSRS.GetText(i, 2);
				strArr[3] = BQSSRS.GetText(i, 3);
				strArr[4] = BQSSRS.GetText(i, 4);
				strArr[5] = BQSSRS.GetText(i, 5);
				strArr[6] = BQSSRS.GetText(i, 6);
				strArr[7] = BQSSRS.GetText(i, 7);
				strArr[8] = BQSSRS.GetText(i, 8);
				strArr[9] = BQSSRS.GetText(i, 9);
				strArr[10] = BQSSRS.GetText(i, 10);
				strArr[11] = BQSSRS.GetText(i, 11);
				strArr[12] = "";
				//防止为空
				if("".equals(strArr[11]) || strArr[11]==null)
				{
					strArr[11]=strArr[9]; 
				}
				tIntv = PubFun.calInterval(tFLagDate, BQSSRS.GetText(i, 11)
						.substring(0, 10), "D");
				tFLagDate = BQSSRS.GetText(i, 11);
				if (tIntv > 0) {
					String RailArr[] = (String[]) null;
					RailArr = new String[13];
					RailArr[0] = "";
					RailArr[1] = "共" + String.valueOf(TotalNum) + "件";
					RailArr[2] = "";
					RailArr[3] = "";
					RailArr[4] = "";
					RailArr[5] = "";
					RailArr[6] = "";
					RailArr[7] = "";
					RailArr[8] = "";
					RailArr[9] = "";
					RailArr[10] = "";
					RailArr[11] = "";
					RailArr[12] = "";
					tlistTable.add(RailArr);
					String RailSpaceArr[] = (String[]) null;
					RailSpaceArr = new String[13];
					RailSpaceArr[0] = "";
					RailSpaceArr[1] = "";
					RailSpaceArr[2] = "";
					RailSpaceArr[3] = "";
					RailSpaceArr[4] = "";
					RailSpaceArr[5] = "";
					RailSpaceArr[6] = "";
					RailSpaceArr[7] = "";
					RailSpaceArr[8] = "";
					RailSpaceArr[9] = "";
					RailSpaceArr[10] = "";
					RailSpaceArr[11] = "";
					RailSpaceArr[12] = "";
					tlistTable.add(RailSpaceArr);
					String HeadArr[] = (String[]) null;
					HeadArr = new String[13];
					HeadArr[0] = "序号";
					HeadArr[1] = "项目";
					HeadArr[2] = "集体保险单号";
					HeadArr[3] = "批单号";
					HeadArr[4] = "险种";
					HeadArr[5] = "机构";
					HeadArr[6] = "投保人";
					HeadArr[7] = "金额";
					HeadArr[8] = "申请人";
					HeadArr[9] = "申请日期";
					HeadArr[10] = "复核人";
					HeadArr[11] = "确认日期";
					HeadArr[12] = "备注";
					tlistTable.add(HeadArr);
					TotalNum = 0;
				}
				TotalNum++;
				strArr[0] = String.valueOf(TotalNum);
				tlistTable.add(strArr);
			}

			String LastRailArr[] = (String[]) null;
			LastRailArr = new String[13];
			LastRailArr[0] = "";
			LastRailArr[1] = "共" + String.valueOf(TotalNum) + "件";
			LastRailArr[2] = "";
			LastRailArr[3] = "";
			LastRailArr[4] = "";
			LastRailArr[5] = "";
			LastRailArr[6] = "";
			LastRailArr[7] = "";
			LastRailArr[8] = "";
			LastRailArr[9] = "";
			LastRailArr[10] = "";
			LastRailArr[11] = "";
			LastRailArr[12] = "";
			tlistTable.add(LastRailArr);
		} else if (mDefineCode.equals("bq7")) { //合计表

			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				bq_sql = " select a.edortype, a.grpcontno,c.edorconfno,"
				+ " (select lcgrppol.riskcode from lcgrppol, lmriskapp where grpcontno = b.grpcontno "
				+ " and lcgrppol.riskcode = lmriskapp.riskcode and lmriskapp.subriskflag = 'M' and rownum = 1), "
				+ " b.Managecom,b.grpname,c.getmoney,a.Operator,c.edorappdate, "		                     
				+ " (select case when c.ApproveOperator is null then  a.Operator else  c.ApproveOperator end from dual),(select case when c.confdate is null then a.edorvalidate else c.confdate end from dual)"
				+ " from lpgrpedoritem a, lcgrpcont b, lpedorapp c "
				+ " where a.grpcontno = b.grpcontno and a.grpcontno = c.otherno and a.edoracceptno = c.edoracceptno and c.otherno = b.grpcontno and a.EdorState = '0' "
				+ ReportPubFun.getWherePart("c.Operator", ReportPubFun.getParameterStr(mAppOperator, "?mAppOperator?"))
				+ ReportPubFun.getWherePart("(select  case when c.ApproveOperator is null then  c.Operator else c.ApproveOperator end from dual)", ReportPubFun.getParameterStr(mConfOperator, "?mConfOperator?"))
				+ ReportPubFun.getWherePart("a.edortype", ReportPubFun.getParameterStr(mBQCode,"?mBQCode?"))
				+ ReportPubFun.getWherePartLike("a.ManageCom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
				+ ReportPubFun.getWherePart("(select  case when c.confdate is null then  a.edorvalidate else c.confdate end  from dual)", ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1)	
				+ " order by 11,1";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				bq_sql = " select a.edortype, a.grpcontno,c.edorconfno,"
						+ " (select lcgrppol.riskcode from lcgrppol, lmriskapp where grpcontno = b.grpcontno "
						+ " and lcgrppol.riskcode = lmriskapp.riskcode and lmriskapp.subriskflag = 'M' limit 0,1), "
						+ " b.Managecom,b.grpname,c.getmoney,a.Operator,c.edorappdate, "		                     
						+ " (select case when c.ApproveOperator is null then  a.Operator else  c.ApproveOperator end from dual),(select case when c.confdate is null then a.edorvalidate else c.confdate end from dual)"
						+ " from lpgrpedoritem a, lcgrpcont b, lpedorapp c "
						+ " where a.grpcontno = b.grpcontno and a.grpcontno = c.otherno and a.edoracceptno = c.edoracceptno and c.otherno = b.grpcontno and a.EdorState = '0' "
						+ ReportPubFun.getWherePart("c.Operator", ReportPubFun.getParameterStr(mAppOperator, "?mAppOperator?"))
						+ ReportPubFun.getWherePart("(select  case when c.ApproveOperator is null then  c.Operator else c.ApproveOperator end from dual)", ReportPubFun.getParameterStr(mConfOperator, "?mConfOperator?"))
						+ ReportPubFun.getWherePart("a.edortype", ReportPubFun.getParameterStr(mBQCode,"?mBQCode?"))
						+ ReportPubFun.getWherePartLike("a.ManageCom",ReportPubFun.getParameterStr(mManageCom,"?mManageCom?"))
						+ ReportPubFun.getWherePart("(select  case when c.confdate is null then  a.edorvalidate else c.confdate end  from dual)", ReportPubFun.getParameterStr(mDay[0],"?date1?"), ReportPubFun.getParameterStr(mDay[1],"?date2?"), 1)	
						+ " order by 11,1";
			}
			
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(bq_sql);
			sqlbv1.put("mAppOperator", mAppOperator);
			sqlbv1.put("mConfOperator", mConfOperator);
			sqlbv1.put("mBQCode", mBQCode);
			sqlbv1.put("mManageCom", mManageCom);
			sqlbv1.put("date1",  mDay[0]);
			sqlbv1.put("date2", mDay[1]);
			
			xmlexport.createDocument("BQGrpSumCount.vts", "printer");
			xmlexport.addDisplayControl(displayname);
			logger.debug("保全bq7--sql:" + bq_sql);
			BQSSRS = BQExeSQL.execSQL(sqlbv1);
			logger.debug("大小：" + BQSSRS.getMaxRow());
			for (int i = 1; i <= BQSSRS.getMaxRow(); i++) {
				strArr = new String[13];
				strArr[0] = String.valueOf(i);
				strArr[1] = BQSSRS.GetText(i, 1);
				strArr[2] = BQSSRS.GetText(i, 2);
				strArr[3] = BQSSRS.GetText(i, 3);
				strArr[4] = BQSSRS.GetText(i, 4);
				strArr[5] = BQSSRS.GetText(i, 5);
				strArr[6] = BQSSRS.GetText(i, 6);
				strArr[7] = BQSSRS.GetText(i, 7);
				strArr[8] = BQSSRS.GetText(i, 8);
				strArr[9] = BQSSRS.GetText(i, 9);
				strArr[10] = BQSSRS.GetText(i, 10);
				strArr[11] = BQSSRS.GetText(i, 11);
				strArr[12] = "";
				TotalNum = i;
				tlistTable.add(strArr);
			}

		} else if (mDefineCode.equals("bq8")) {
			String tBq_lcp = "select  lpedormain.edortype,lpedormain.grppolno,lpedormain.edorno,lcpol.AppntName,"
					+ "sum(lpedormain.getMoney),lcpol.agentcode,lpedormain.Operator,lpedormain.ModifyDate,"
					+ "substr(lcpol.Managecom,1,4),lcpol.Managecom,lcpol.riskcode"
					+ " from lpedormain,lcpol,ldcode"
					+ " where lpedormain.polno=lcpol.polno and lpedormain.edortype=ldcode.code"
					+ " and lpedormain.EdorState='0' and lcpol.appflag='1'"
					+ ReportPubFun.getBQCodeTypeSql(mDefineCode)
					+ ReportPubFun.getBQChnlSql(mDefineCode, "lcpol")
					+ ReportPubFun.getWherePart("lpedormain.Operator", ReportPubFun.getParameterStr(mOpt, "?mOpt?"))
					+ ReportPubFun.getWherePart("lpedormain.edortype", ReportPubFun.getParameterStr(mBQCode, "?mBQCode?"))
					+ ReportPubFun.getWherePartLike("lpedormain.ManageCom",
							ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ ReportPubFun.getWherePart("lpedormain.ModifyDate",
							ReportPubFun.getParameterStr(mDay[0], "?date1?"), ReportPubFun.getParameterStr(mDay[1], "?date2?"), 1);
			String tBq_lcp_groupby = " group by lpedormain.edortype,lpedormain.grppolno,lpedormain.edorno,"
					+ "lcpol.AppntName,lcpol.agentcode,lpedormain.Operator,lpedormain.ModifyDate,"
					+ "substr(lcpol.Managecom,1,4),lcpol.Managecom,lcpol.riskcode";
			String tBq_lbp = StrTool.replace(tBq_lcp, "lcpol", "lbpol");
			String tBq_lbp_groupby = StrTool.replace(tBq_lcp_groupby, "lcpol",
					"lbpol");
			bq_sql = tBq_lcp
					+ tBq_lcp_groupby
					+ " union all ("
					+ tBq_lbp
					+ tBq_lbp_groupby
					+ ") "
					+ ReportPubFun.getBQtSql(mBQCode, mDefineCode, ReportPubFun.getParameterStr(mDay[0], "?date1?"),
							ReportPubFun.getParameterStr(mDay[1], "?date2?"), ReportPubFun.getParameterStr(mManageCom, "?mManageCom?")) + " order by 1,9,10,11";
			
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(bq_sql);
			sqlbv3.put("mOpt", mOpt);
			sqlbv3.put("mBQCode", mBQCode);
			sqlbv3.put("mManageCom", mManageCom);
			sqlbv3.put("date1",  mDay[0]);
			sqlbv3.put("date2", mDay[1]);
			
			xmlexport.createDocument("BQDayEndNoFinace.vts", "printer");
			xmlexport.addDisplayControl(displayname);
			logger.debug("保全sql:" + bq_sql);
			BQSSRS = BQExeSQL.execSQL(sqlbv3);
			logger.debug("大小：" + BQSSRS.getMaxRow());
			for (int i = 1; i <= BQSSRS.getMaxRow(); i++) {
				strArr = new String[13];
				strArr[0] = String.valueOf(i);
				strArr[1] = ReportPubFun.getBqItemName(BQSSRS.GetText(i, 1),
						mDefineCode, tLDCodeSet); //变更项目
				strArr[2] = BQSSRS.GetText(i, 2); //集体保险单号
				strArr[3] = BQSSRS.GetText(i, 3); //批单号
				strArr[4] = BQSSRS.GetText(i, 4); //投保人
				strArr[5] = BQSSRS.GetText(i, 5); //金额
				strArr[6] = getAgentName(BQSSRS.GetText(i, 6)); //代理人姓名
				strArr[7] = BQSSRS.GetText(i, 6); //代理人编码
				strArr[8] = BQSSRS.GetText(i, 7); //操作人编码
				strArr[9] = BQSSRS.GetText(i, 8); //修改时间
				strArr[10] = ReportPubFun.getMngName(BQSSRS.GetText(i, 9)); //分公司机构
				strArr[11] = ReportPubFun.getMngName(BQSSRS.GetText(i, 10)); //管理机构
				strArr[12] = ReportPubFun.getRiskName(BQSSRS.GetText(i, 11),
						tLMRiskAppSet); //险种名称 　
				TotalNum = i;
				TotalMoney = TotalMoney + Double.parseDouble(strArr[5]);
				tlistTable.add(strArr);
			}
			logger.debug("TotalMoney:" + TotalMoney);
			strArr = new String[13];
			strArr[0] = "总计";
			strArr[1] = "";
			strArr[2] = "件数";
			strArr[3] = String.valueOf(TotalNum) + "件";
			strArr[4] = "金额";
			strArr[5] = ReportPubFun.functionJD(TotalMoney, "0.00") + "元";
			strArr[6] = "";
			strArr[7] = "";
			strArr[8] = "";
			strArr[9] = "";
			strArr[10] = "";
			strArr[11] = "";
			strArr[12] = "";
		} else {
			//c表：
			String bq_lcp = "select  lpedormain.edortype,lpedormain.polno,lpedormain.edorno,lcpol.AppntName,"
					+ "lcpol.insuredname,lpedormain.getMoney,lcpol.agentcode,lpedormain.Operator,lpedormain.ModifyDate,"
					+ "substr(lcpol.Managecom,1,4),lcpol.Managecom,lcpol.riskcode,"
					+ "(select max(makedate) from loloan where polno = lcpol.polno and loantype = '1') "
					+ " from lpedormain,lcpol,ldcode"
					+ " where lpedormain.polno=lcpol.polno and lpedormain.edortype=ldcode.code "
					+ " and lpedormain.EdorState='0' and lcpol.appflag='1'"
					+ ReportPubFun.getBQCodeTypeSql(mDefineCode)
					+ ReportPubFun.getBQChnlSql(mDefineCode, "lcpol")
					+ ReportPubFun.getWherePart("lpedormain.Operator", ReportPubFun.getParameterStr(mOpt, "?mOpt?"))
					+ ReportPubFun.getWherePart("lpedormain.edortype", ReportPubFun.getParameterStr(mBQCode, "?mBQCode?"))
					+ ReportPubFun.getWherePartLike("lpedormain.ManageCom",
							ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ ReportPubFun.getWherePart("lpedormain.ModifyDate",
							ReportPubFun.getParameterStr(mDay[0], "?date1?"), ReportPubFun.getParameterStr(mDay[1], "?date2?"), 1);
			//b表：
			String bq_lbp = StrTool.replace(bq_lcp, "lcpol", "lbpol");
			String bq_lbp_wt = StrTool
					.replace(
							bq_lbp,
							"lpedormain.getMoney",
							"(select sum(getmoney) from ljagetendorse"
									+ " where endorsementno=lpedormain.edorno and feeoperationtype='WT' and feefinatype='TF')");
			
			SQLwithBindVariables tSQLwithBindVariables = new SQLwithBindVariables();
			bq_sql = "("
					+ bq_lcp
					+ ") union all ("
					+ bq_lbp
					+ " and lpedormain.edortype<>'WT') union all ("
					+ bq_lbp_wt
					+ " and lpedormain.edortype='WT') "
					+ getBQSql(tSQLwithBindVariables,mBQCode, mDefineCode, mDay[0], mDay[1],
							mManageCom) + " order by 1,10,11,12";
			
			
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.put(tSQLwithBindVariables);
			sqlbv4.sql(bq_sql);
			sqlbv4.put("mOpt", mOpt);
			sqlbv4.put("mBQCode", mBQCode);
			sqlbv4.put("mManageCom", mManageCom);
			
			xmlexport.createDocument("BQDayEnd.vts", "printer");
			xmlexport.addDisplayControl(displayname);
			logger.debug("保全sql:" + bq_sql);
			BQSSRS = BQExeSQL.execSQL(sqlbv4);
			logger.debug("大小：" + BQSSRS.getMaxRow());

			for (int i = 1; i <= BQSSRS.getMaxRow(); i++) {
				strArr = new String[16];
				strArr[0] = String.valueOf(i);
				strArr[1] = ReportPubFun.getBqItemName(BQSSRS.GetText(i, 1),
						mDefineCode, tLDCodeSet); //变更项目
				strArr[2] = BQSSRS.GetText(i, 2); //保险单号
				strArr[3] = BQSSRS.GetText(i, 3); //批单号
				strArr[4] = BQSSRS.GetText(i, 4); //投保人
				strArr[5] = BQSSRS.GetText(i, 5); //被保险人
				strArr[6] = BQSSRS.GetText(i, 6); //合计金额
				strArr[7] = getAgentName(BQSSRS.GetText(i, 7)); //代理人姓名
				strArr[8] = BQSSRS.GetText(i, 7); //代理人编码
				strArr[9] = BQSSRS.GetText(i, 8); //操作人编码
				strArr[10] = BQSSRS.GetText(i, 9); //日期
				strArr[11] = ReportPubFun.getMngName(BQSSRS.GetText(i, 10)); //分公司机构
				strArr[12] = ReportPubFun.getMngName(BQSSRS.GetText(i, 11)); //管理机构
				strArr[13] = ReportPubFun.getRiskName(BQSSRS.GetText(i, 12),
						tLMRiskAppSet); //险种名称
				strArr[14] = BQSSRS.GetText(i, 13); //产生垫交日期
				strArr[15] = getEndorReason(BQSSRS.GetText(i, 3), BQSSRS
						.GetText(i, 2), BQSSRS.GetText(i, 1));
				TotalNum = i;
				TotalMoney = TotalMoney + Double.parseDouble(strArr[6]);
				tlistTable.add(strArr);
			}
			logger.debug("TotalMoney:" + TotalMoney);
			strArr = new String[16];
			strArr[0] = "总计";
			strArr[1] = "";
			strArr[2] = "";
			strArr[3] = "件数";
			strArr[4] = String.valueOf(TotalNum) + "件";
			strArr[5] = "金额";
			strArr[6] = ReportPubFun.functionJD(TotalMoney, "0.00") + "元";
			strArr[7] = "";
			strArr[8] = "";
			strArr[9] = "";
			strArr[10] = "";
			strArr[11] = "";
			strArr[12] = "";
			strArr[13] = "";
			strArr[14] = "";
			strArr[15] = "";
		}
		if (!mDefineCode.equals("bq10") && !mDefineCode.equals("bq7")) {
			tlistTable.add(strArr);
		}
		if(strArr!=null&& strArr.length>0)
		{
			xmlexport.addListTable(tlistTable, strArr);
		}


		String CurrentDate = PubFun.getCurrentDate();
		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("sManageCom", ReportPubFun.getMngName(mManageCom));
		texttag.add("dManageCom", mGlobalInput.ManageCom);
		texttag.add("Operator", mGlobalInput.Operator);
		texttag.add("time", CurrentDate);
		logger.debug("大小" + texttag.size());
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		//xmlexport.outputDocumentToFile("e:\\",xmlname);//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	private String getEndorReason(String EdorNo, String PolNo, String EdorType) {
		String Reason = "";
		LCEdorReasonSet tLCEdorReasonSet = new LCEdorReasonSet();
		LCEdorReasonDB tLCEdorReasonDB = new LCEdorReasonDB();
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String ReasonSQL = "select * from lcedorreason where edorno='" + "?EdorNo?"
				+ "' and PolNo='" + "?PolNo?" + "' and EdorType='" + "?EdorType?" + "'";
		sqlbv6.sql(ReasonSQL);
		sqlbv6.put("EdorNo", EdorNo);
		sqlbv6.put("PolNo", PolNo);
		sqlbv6.put("EdorType", EdorType);
		logger.debug("ReasonSQL:" + ReasonSQL);
		tLCEdorReasonSet = tLCEdorReasonDB.executeQuery(sqlbv6);

		for (int i = 1; i <= tLCEdorReasonSet.size(); i++) {
			if (tLCEdorReasonSet.size() > 1) {
				Reason += i + ".";
			}
			Reason += tLCEdorReasonSet.get(i).getReason();
			if (tLCEdorReasonSet.get(i).getRemark() != null
					&& !tLCEdorReasonSet.get(i).getRemark().equals("")) {
				Reason += "(备注:" + tLCEdorReasonSet.get(i).getRemark() + ")";
			}
		}

		return Reason;
	}

	private String getAgentName(String agengCode) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(agengCode);
		if (!tLAAgentDB.getInfo()) {
			return "";
		}
		return tLAAgentDB.getName();
	}

	private static String getBQSql(SQLwithBindVariables sqlbv,String BQCode, String BQType, String sTime,
			String eTime, String mManageCom) {
		/** 由保全报表系统代码得到的得到对应的sql语句
		 * 对于保全的两个项目，满期/生存保险给付（LG）和红利领取（BG）
		 * 在批改表中没有两个项目记录 ，但是保全部门需要统计这两个项目
		 * 在个人费用类和银代费用类要加上这两个数据源
		 * @param  BQType保全代码,BQCode保全类型
		 *         sTime 开始时间，eTime 结束时间，
		 *         ManageCom 管理机构
		 * @return
		 */
		String add_lg = "";
		String add_bg = "";
		if (BQCode.equals("LG") || BQCode.equals("")) {
			add_lg = " union all (select 'LG' as edortype, ljagetdraw.polno,'' as edorno, lcpol.AppntName,"
					+ "lcpol.InsuredName,ljagetdraw.getmoney,lcpol.agentcode,ljagetdraw.Operator,ljagetdraw.ModifyDate,"
					+ "substr(lcpol.Managecom,1,4),lcpol.Managecom,lcpol.riskcode,null "
					+ "from ljagetdraw,lcpol "
					+ "where ljagetdraw.polno=lcpol.polno "
					+ "and lcpol.appflag='1' "
					+ ReportPubFun.getBQSaleChnlSql(BQType)
					+ ReportPubFun.getWherePartLike("ljagetdraw.ManageCom",
							ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ ReportPubFun.getWherePart("ljagetdraw.ModifyDate",ReportPubFun.getParameterStr(sTime, "?sTime?"), ReportPubFun.getParameterStr(eTime, "?eTime?"), 1) + ") ";
		}
		if (BQCode.equals("BG") || BQCode.equals("")) {
			add_bg = " union all (select 'BG' as edortype, LJABonusGet.OtherNo,'' as edorno, lcpol.AppntName,"
					+ "lcpol.InsuredName,LJABonusGet.getmoney,lcpol.agentcode,LJABonusGet.Operator,LJABonusGet.ModifyDate,"
					+ "substr(lcpol.Managecom,1,4),lcpol.Managecom,lcpol.riskcode,null "
					+ "from LJABonusGet,lcpol "
					+ "where LJABonusGet.OtherNo=lcpol.polno and enteraccdate is not null "
					+ "and lcpol.appflag='1' "
					+ ReportPubFun.getBQSaleChnlSql(BQType)
					+ ReportPubFun.getWherePartLike("LJABonusGet.ManageCom",
							ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
					+ ReportPubFun.getWherePart("LJABonusGet.ModifyDate",
							ReportPubFun.getParameterStr(sTime, "?sTime?"), ReportPubFun.getParameterStr(eTime, "?eTime?"), 1) + ") ";
		}
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("sTime", sTime);
		sqlbv.put("eTime", eTime);
		String add_sql = add_lg + add_bg;
		sqlbv.sql(add_sql);
		return add_sql;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "8613";
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "8613";

		String tDay[] = new String[2];
		tDay[0] = "2005-05-01";
		tDay[1] = "2006-05-01";

		VData tVData = new VData();
		tVData.add(tDay);
		tVData.add("8613");
		tVData.add("");
		tVData.add("bq2");
		tVData.add("001");
		tVData.add(tGlobalInput);

		BQCheckBL tBQCheckBL = new BQCheckBL();
		tBQCheckBL.submitData(tVData, "PRINTPAY");
	}
}
