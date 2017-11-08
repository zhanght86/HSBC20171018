package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.AbstractBL;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.pubfun.ZHashReport;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class OperFeeRateBL extends AbstractBL {
private static Logger logger = Logger.getLogger(OperFeeRateBL.class);
	private String	StartDate;
	private String	EndDate;
	private String	Channel;
	private String	ManageCom;
	private String	ReportType;
	private String	ComType;
	private String  App;

	protected boolean dealData() {
		String c = "4";
		if (ComType.equals("1"))
			c = "4";
		else if (ComType.equals("2"))
			c = "6";
		else if (ComType.equals("3"))
			c = "8";
		String keySql = "select comcode from ldcom where char_length(trim(comcode))="
				+ c
				+ " and comcode!='8699' and comcode like concat('"
				+ "?ManageCom?"
				+ "','%') order by comcode";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(keySql);
		sqlbv1.put("ManageCom", ManageCom);
		String sqlname = "select comcode,shortname from ldcom where char_length(trim(comcode))="
				+ c
				+ " and comcode!='8699' and comcode like concat('"
				+ "?ManageCom?"
				+ "','%')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sqlname);
		sqlbv2.put("ManageCom", ManageCom);
		String sqlall = "";
		String sqlsub = "";
		StringBuffer sb = new StringBuffer();

		if (ReportType.equals("1")) {
			sb
					.append(
							"select distinct substr(managecom,1,"
									+ c
									+ ") m,count(1) over(partition by substr(managecom,1,"
									+ c + ")) s")
					.append(
							" from lcpol where payintv=12 and appflag='1' and polno=mainpolno and MANAGECOM like '")
					.append(ManageCom).append("%'").append(
							" and exists(select 1 from lccontstate x where x.polno=lcpol.polno and x.statetype='Available' and enddate is null) ");
			if (Channel.equals("1"))
				sb.append(" and SaleChnl='02'");
			else
				sb.append(" and salechnl in ('03', '05', '06', '08', '09') and exists (select 1 from lacom where agentcom = lcpol.agentcom and branchtype = '7')");
			if (App.equals("1")) {
				sb.append(ReportPubFun.getWherePart("cvalidate", StartDate,
						EndDate, 1));
			} else {
				sb.append("and (");
				sb.append("paytodate between '"+StartDate+"' and '"+EndDate);
				sb.append("' or (exists(select 1 from lcprem where polno=lcpol.polno and paytimes>1) and ");
				String nextStartDate=PubFun.calDate(StartDate, 1, "Y", null);
				String nextEndDate=PubFun.calDate(EndDate, 1, "Y", null);
				sb.append("paytodate between '"+nextStartDate+"' and '"+nextEndDate);
				sb.append("'))");
			}
			sqlall = sb.toString();
			sqlsub = sqlall + " and paylocation='0'";
		} else {
			sqlall = "select distinct substr(b.managecom, 1, "+c+")m ,"
					+ "count(1) over(partition by substr(b.managecom, 1, "+c+"))s "
					+ "from ljapayperson a, lcpol b "
					+ "where a.polno = b.polno "
					+ReportPubFun.getWherePart("a.lastpaytodate",StartDate,EndDate,1)
					+ "and a.paycount > 1 "
					+ "and b.payintv = 12 "
					+ "and b.appflag = '1' "
					+ "and b.polno = b.mainpolno "
					+ "and exists(select 1 from lccontstate x where x.polno=b.polno and x.statetype='Available' and enddate is null) "
					+ "and b.managecom like '86%' "
					+ "and not exists (select 1 from LOLOAN "
					+ "where loantype = '1' and payoffflag = '0' and LOLOAN.polno = b.polno)"
					+" and (length(trim(a.dutycode))<>10 or substr(a.dutycode,7,1)<>'1')";
			if (Channel.equals("1"))
				sqlall+=(" and SaleChnl='02'");
			else
                                sqlall+=(" and salechnl in ('03', '05', '06', '08', '09') and exists (select 1 from lacom where agentcom = b.agentcom and branchtype = '7') ");

			sqlsub = sqlall + " and exists (select 1 from ljtempfeeclass "
					+ "where tempfeeno = a.getnoticeno and paymode = '4')";
		}
		String sqlrate = "select a.m,a.s,nvl(b.s,0),nvl(b.s,0)/a.s from (" + sqlall
				+ ")a left outer join (" + sqlsub + ")b on (a.m=b.m)";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sqlrate);
		String name1 = "统计日期：" + PubFun.getCurrentDate();
		String name2 = "机构范围：" + ManageCom;
		String value1 = "承保期间："
				+ StartDate
				+ "至"
				+ EndDate;
		String value2 = "单位：件数";
		String title = "二级";
		if (ComType.equals("1"))
			title = "二级";
		else if (ComType.equals("2"))
			title = "三级";
		else if (ComType.equals("3"))
			title = "四级";
		title += "机构";
		if (Channel.equals("1"))
			title += "个险";
		else
			title += "中介";
		title += "期缴转帐";
		String colNames;
		if(App.equals("1"))
			colNames = "com;分公司名称;承保件数;转帐件数;转帐占比";
		else
			colNames = "com;分公司名称;应收件数;转帐件数;转帐占比";
		if (ReportType.equals("1")) {
			title += "占比率";
		} else if (ReportType.equals("2")) {
			title += "成功率";
			colNames = "com;分公司名称;实收件数;转帐件数;转帐成功率";
		}
		title += "统计表";
//		String st="select comcode,'=if(C'||(rownum+5)||'=0,0,D'||(rownum+5)||'/C'||(rownum+5)||')' from ldcom where length(trim(comcode))="
//			+ c
//			+ " and comcode!='8699' and comcode like '"
//			+ ManageCom
//			+ "%' order by comcode";
		ZHashReport rep = new ZHashReport(sqlbv1);
		rep.addSql(sqlbv2);
		rep.addSql(sqlbv3);
//		rep.addSql(st);
		rep.setColumnType(1,rep.StringType);
		rep.setColumnType(2,rep.IntType);
		rep.setColumnType(3,rep.IntType);
//		rep.setColumnType(5,rep.DoubleType);
		rep.setSumColumn(true);

		String[] cs = colNames.split(";");
		for (int i = 1; i <= cs.length; i++) {
			rep.addValue("COL" + i, cs[i - 1]);
		}
		rep.addValue("Title",title);
		rep.addValue("name1",name1);
		rep.addValue("name2",name2);
		rep.addValue("value1",value1);
		rep.addValue("value2",value2);

		XmlExport export=rep.createXml("OperFeeRate.vts");
		String[] sums=rep.getSumCols();
		String s=ReportPubFun.functionDivision(sums[3],sums[2],"0.00");
		TextTag tag=new TextTag();
		tag.add("DATA_SUMR",s);
		export.addTextTag(tag);

		return mResult.add(export);
	}

	protected boolean getInputData() {
		TransferData data = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		StartDate=(String) data.getValueByName("StartDate");
		EndDate=(String) data.getValueByName("EndDate");
		Channel=(String) data.getValueByName("Channel");
		ManageCom=(String) data.getValueByName("ManageCom");
		ReportType=(String) data.getValueByName("ReportType");
		ComType=(String) data.getValueByName("ComType");
		App=(String) data.getValueByName("App");
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OperFeeRateBL bl=new OperFeeRateBL();
		VData tVData=new VData();
		TransferData tTransferData=new TransferData();
		tTransferData.setNameAndValue("StartDate","2006-10-1");
		tTransferData.setNameAndValue("EndDate","2006-10-31");
		tTransferData.setNameAndValue("Channel","1");
		tTransferData.setNameAndValue("ManageCom","86");
		tTransferData.setNameAndValue("ReportType","2");
		tTransferData.setNameAndValue("ComType","1");
		tTransferData.setNameAndValue("App","1");
		tTransferData.setNameAndValue("TemplatePath","E:\\myeclipsework\\lis\\f1print\\template\\");
		tVData.add(tTransferData);
		if(bl.submitData(tVData))
			logger.debug("ok");

	}

}
