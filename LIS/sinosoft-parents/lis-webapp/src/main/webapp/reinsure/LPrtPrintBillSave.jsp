<%@include file="/i18n/language.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%
	String tTempleteID = request.getParameter("TempleteID");
	String billType = request.getParameter("BillType");
	String RIComCode = request.getParameter("RIComCode");
	String billprintType = request.getParameter("billprintType");
	String RICoName = request.getParameter("RIContName");
	String CalYear = request.getParameter("CalYear");
	String seaType = request.getParameter("seaType");
	String RIContNo = request.getParameter("RIContNo");
	String RIComName = request.getParameter("RIComName");
	DecimalFormat df = new DecimalFormat(",###.##");
	String tBusiName = "CreateXMLFile";
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	TransferData tTransferData = new TransferData();
	XmlExportNew tXmlExportNew = new XmlExportNew();
	ListTable tlistTable = new ListTable();
	ArrayList tArr = new ArrayList();//
	String[] strArr = new String[1000];
	String Content = "";
	InputStream tInput;
	String Startdate = null;
	String Enddate = null;
	double total = 0;
	double ProLosAmnt = 0;
	double Amnt = 0;
	double totalr = 0;
	TextTag texttag = new TextTag();
	SSRS tSSRS = new SSRS();
	SSRS aSSRS = new SSRS();
	SSRS bSSRS = new SSRS();
	SSRS cSSRS = new SSRS();
	SSRS dSSRS = new SSRS();
	PubFun pubFun = new PubFun();
	ExeSQL aExeSQL = new ExeSQL();
	String Billtimes = CalYear + seaType;
	SimpleDateFormat sdf = new SimpleDateFormat("MMMM  dd , yyyy",
			Locale.ENGLISH);
	texttag.add("RIComName", RIComName);
	texttag.add("RIContNo", RICoName);
	texttag.add("PrintDate", pubFun.getCurrentDate());
	texttag.add("EngDate", sdf.format(new Date()));
	String value = null;
	if (billType.equals("B2")) {
		String Billtime = CalYear + request.getParameter("monType");
		tTempleteID = "002876";
		tlistTable.setName("COBILL");
		String aasql = " SELECT c.Feename,decode (r.Debcre,'01', c.Summoney,0 ) Debit,decode (r.Debcre,'02', c.Summoney,0 )  Credit   FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r    where c.Billno = b.Billno   and b.Currency=c.Currency and b.Batchno = c.Batchno and  b.Billno = r.Billno and c.Feecode=r.Feecode and   r.Billitemtype='01' and b.State in ('02', '03')   and b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' order by Debcre desc  ";
		String asql = " SELECT sum(c.Summoney),r.Debcre  FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno = b.Billno  and b.Batchno = c.Batchno and  b.Billno = r.Billno  and b.Currency=c.Currency and c.Feecode=r.Feecode and  r.Billitemtype='01' and b.State in ('02', '03')  and b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "'  group by r.Debcre  order by Debcre ";
		tSSRS = aExeSQL.execSQL(aasql);
		strArr = new String[tSSRS.getMaxCol()];
		for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
			for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
				if (tSSRS.GetText(a, b).equals("0")) {
					tSSRS.SetText(a, b, null);
				}
				strArr[b - 1] = tSSRS.GetText(a, b);
				if (b >= 2 && tSSRS.GetText(a, b) != null) {
					strArr[b - 1] = df.format(Double.parseDouble(tSSRS
							.GetText(a, b)));
				}
			}
			tlistTable.add(strArr);
			strArr = new String[tSSRS.getMaxCol()];
		}

		tSSRS = aExeSQL.execSQL(asql);
		texttag.add("BillTimes", Billtime);
		texttag.add("BusyDate", CalYear);
		if (tSSRS.getMaxRow() != 0) {
			Amnt = Double.parseDouble(tSSRS.GetText(1, 1))
					- Double.parseDouble(tSSRS.GetText(2, 1));
			ProLosAmnt = Math.abs(Amnt);
			if (Amnt >= 0) {
				texttag.add("ProLosAmntR", df.format(ProLosAmnt));
				texttag.add("Total", df.format(Double.parseDouble(tSSRS
						.GetText(1, 1))));
			} else {
				texttag.add("ProLosAmntL", df.format(ProLosAmnt));
				texttag.add("Total", df.format(Double.parseDouble(tSSRS
						.GetText(2, 1))));
			}
		}
	}
	if (billType.equals("B1")) {
		String Billtime = CalYear + seaType;
		texttag.add("BillTimes", Billtime);
		tTempleteID = "002877";
		tlistTable.setName("Rowinfo");
		String sqlH1 = " SELECT c.Feename,c.Summoney FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno = b.Billno  and b.Currency=c.Currency and b.Batchno = c.Batchno and  b.Billno = r.Billno and b.Currency='13'and c.Feecode=r.Feecode and r.Debcre='01' and  r.Billitemtype='01' and b.State in ('02', '03') and b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' order by Feename   ";
		aSSRS = aExeSQL.execSQL(sqlH1);
		String sqlH2 = " SELECT c.Feename,c.Summoney FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno = b.Billno  and b.Currency=c.Currency  and b.Batchno = c.Batchno and  b.Billno = r.Billno and b.Currency='13'and c.Feecode=r.Feecode and r.Debcre='02' and  r.Billitemtype='01' and b.State in ('02', '03') and b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' order by Feename   ";
		bSSRS = aExeSQL.execSQL(sqlH2);
		String sqlU1 = " SELECT c.Feename,c.Summoney FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno = b.Billno  and b.Currency=c.Currency and b.Batchno = c.Batchno and  b.Billno = r.Billno and b.Currency='14'and c.Feecode=r.Feecode and r.Debcre='01' and  r.Billitemtype='01' and b.State in ('02', '03') and b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' order by Feename   ";
		cSSRS = aExeSQL.execSQL(sqlU1);
		String sqlU2 = " SELECT c.Feename,c.Summoney FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno = b.Billno  and b.Currency=c.Currency and b.Batchno = c.Batchno and  b.Billno = r.Billno and b.Currency='14'and c.Feecode=r.Feecode and r.Debcre='02' and  r.Billitemtype='01' and b.State in ('02', '03') and b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' order by Feename   ";
		dSSRS = aExeSQL.execSQL(sqlU2);
		int i = Math.max(
				Math.max(aSSRS.getMaxRow(), cSSRS.getMaxRow()),
				Math.max(cSSRS.getMaxRow(), dSSRS.getMaxRow()));
		strArr = new String[8];
		for (int a = 1; a <= i; a++) {
			for (int b = 1; b <= 8; b++) {
				if (b == 1) {
					strArr[b - 1] = aSSRS.GetText(a, b);
				}
				if (b == 2 && a <= aSSRS.getMaxRow()) {
					strArr[b - 1] = df.format(Double.parseDouble(aSSRS
							.GetText(a, b)));
				}
				if (b == 3) {
					strArr[b - 1] = bSSRS.GetText(a, b - 2);
				}
				if (b == 4 && a <= bSSRS.getMaxRow()) {
					strArr[b - 1] = df.format(Double.parseDouble(bSSRS
							.GetText(a, b - 2)));
				}
				if (b == 5) {
					strArr[b - 1] = cSSRS.GetText(a, b - 4);
				}
				if (b == 6 && a <= cSSRS.getMaxRow()) {
					strArr[b - 1] = df.format(Double.parseDouble(cSSRS
							.GetText(a, b - 4)));
				}
				if (b == 7) {
					strArr[b - 1] = dSSRS.GetText(a, b - 6);
				}
				if (b == 8 && a <= dSSRS.getMaxRow()) {
					strArr[b - 1] = df.format(Double.parseDouble(dSSRS
							.GetText(a, b - 6)));
				}
			}
			tlistTable.add(strArr);
			strArr = new String[8];
		}
		String asql = " SELECT sum(c.Summoney),r.Debcre  FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno =  b.Billno  and b.Currency=c.Currency and b.Batchno = c.Batchno and  b.Billno = r.Billno and b.Currency='13' and c.Feecode=r.Feecode and r.Billitemtype='01' and b.State in   ('02', '03') and  b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' group by r.Debcre  order by Debcre ";
		tSSRS = aExeSQL.execSQL(asql);

		if (tSSRS.getMaxRow() != 0) {
			Amnt = Double.parseDouble(tSSRS.GetText(1, 1))
					- Double.parseDouble(tSSRS.GetText(2, 1));
			ProLosAmnt = Math.abs(Amnt);
			if (Amnt >= 0) {
				texttag.add("ProLosAmntHR", df.format(ProLosAmnt));
				texttag.add("TotalH",  df.format(Double.parseDouble(tSSRS.GetText(1, 1))));
			} else {
				texttag.add("ProLosAmntHL", df.format(ProLosAmnt));
				texttag.add("TotalH",  df.format(Double.parseDouble(tSSRS.GetText(2, 1))));
			}
		}
		String aasql = " SELECT sum(c.Summoney),r.Debcre  FROM RIBsnsBillBatch b, Ribsnsbilldetails c , Ribsnsbillrela r  where c.Billno =  b.Billno  and b.Currency=c.Currency and b.Batchno = c.Batchno and  b.Billno = r.Billno and b.Currency='14' and c.Feecode=r.Feecode and r.Billitemtype='01' and b.State in  ('02', '03') and  b.Billno = '"
				+ billprintType
				+ "'   and b.Billtimes = '"
				+ Billtime
				+ "' group by r.Debcre  order by Debcre ";
		bSSRS = aExeSQL.execSQL(aasql);
		if (bSSRS.getMaxRow() != 0) {
			Amnt = Double.parseDouble(bSSRS.GetText(1, 1))
					- Double.parseDouble(bSSRS.GetText(2, 1));
			ProLosAmnt = Math.abs(Amnt);
			if (Amnt >= 0) {
				texttag.add("ProLosAmntUR", df.format(ProLosAmnt));
				texttag.add("TotalU",  df.format(Double.parseDouble(bSSRS.GetText(1, 1))));
			} else {
				texttag.add("ProLosAmntUL", df.format(ProLosAmnt));
				texttag.add("TotalU",  df.format(Double.parseDouble(bSSRS.GetText(2, 1))));
			}
		}
	}
	if (billType.equals("B5")) {
		tTempleteID = "003019";
		StringBuffer sqlBilly = new StringBuffer();
		sqlBilly.append(" SELECT c.Feename, sum( c.Summoney),b.Currency "
				+ "   FROM   RIBsnsBillBatch b, Ribsnsbilldetails c  "
				+ "   where  c.Billno=b.Billno and b.Batchno=c.Batchno  and b.Currency=c.Currency and b.Currency  IN ('13','14')  and  b.State in ('02','03') "
				+ "   and b.Incomecompanyno = '"
				+ RIComCode
				+ "' "
				+ "   and b.Billtimes = '"
				+ Billtimes
				+ "' "
				+ "    group by b.Currency ,c.Feename order by Currency ");
		double totaly = 0.0;
		double ProLosAmnty = 0.0;
		double totalry = 0.0;
		tSSRS = new ExeSQL().execSQL(sqlBilly.toString());
		if (tSSRS.getMaxRow() != 0) {
			totaly = Double.parseDouble(tSSRS.GetText(4, 2))
					+ Double.parseDouble(tSSRS.GetText(5, 2))
					- Double.parseDouble(tSSRS.GetText(2, 2))
					- Double.parseDouble(tSSRS.GetText(3, 2));
			totalry = totaly - Double.parseDouble(tSSRS.GetText(9, 2));
			texttag.add("RIComName", RICoName);
			texttag.add("BillTimes", Billtimes);
			texttag.add("PremChangH", df.format(Double.parseDouble(tSSRS.GetText(4, 2))));
			texttag.add("ChangeHR", df.format(Double.parseDouble(tSSRS.GetText(5, 2))));
			texttag.add("CommChangH", df.format(Double.parseDouble(tSSRS.GetText(2, 2))));
			texttag.add("ChangeHL", df.format(Double.parseDouble(tSSRS.GetText(3, 2))));

			texttag.add("TotalH", df.format(totaly));
			texttag.add("ReturnPayH", df.format(Double.parseDouble(tSSRS.GetText(9, 2))));
			texttag.add("BalanceH", df.format(totalry));
		}
		if (tSSRS.getMaxRow() > 11) {
			totaly = Double.parseDouble(tSSRS.GetText(15, 2))
					+ Double.parseDouble(tSSRS.GetText(16, 2))
					- Double.parseDouble(tSSRS.GetText(14, 2))
					- Double.parseDouble(tSSRS.GetText(13, 2));
			totalry = totaly - Double.parseDouble(tSSRS.GetText(20, 2));
			texttag.add("PremChangU", df.format(Double.parseDouble(tSSRS.GetText(15, 2))));
			texttag.add("ChangeUR", df.format(Double.parseDouble(tSSRS.GetText(16, 2))));
			texttag.add("CommChangU", df.format(Double.parseDouble(tSSRS.GetText(13, 2))));
			texttag.add("ChangeUL", df.format(Double.parseDouble(tSSRS.GetText(14, 2))));
			texttag.add("TotalU", df.format(totaly));
			texttag.add("ReturnPayU", tSSRS.GetText(20, 2));
			texttag.add("BalanceU", df.format(totalry));
		}
	}
	if (billType.equals("B3")) {
		tTempleteID = "002904";
		texttag.add("Incomecompanyno", RICoName);
		texttag.add("CalYear", CalYear);
		texttag.add("seaType", seaType);
		if (seaType.equals("Q1")) {
			Startdate = CalYear + "-" + 1 + "-" + 1;
			Enddate = CalYear + "-" + 3 + "-" + 31;
		}
		if (seaType.equals("Q2")) {
			Startdate = CalYear + "-" + 4 + "-" + 1;
			Enddate = CalYear + "-" + 6 + "-" + 30;
		}
		if (seaType.equals("Q3")) {
			Startdate = CalYear + "-" + 7 + "-" + 1;
			Enddate = CalYear + "-" + 9 + "-" + 30;
		}
		if (seaType.equals("Q4")) {
			Startdate = CalYear + "-" + 10 + "-" + 1;
			Enddate = CalYear + "-" + 12 + "-" + 31;
		}
		tlistTable.setName("LLCInfo");
		StringBuffer sqlBillp = new StringBuffer();
		sqlBillp.append("SELECT m.Polno,m.Getdutykind,m.Riskcode,sum(Curentamnt),sum(Clmgetmoney),sum(Returnpay)  "
				+ " FROM (select c.Polno,  (SELECT d.codename  FROM ldcode d "
				+ " where d.code in (select b. Getdutykind   from LLClaimDetail b, RIPolRecordBake c  where c.Polno = b.Polno  and c.Riskcode = b.Riskcode and c.Dutycode = b.Getdutycode)) Getdutykind,  "
				+ " a.Riskcode,a.Curentamnt,  c.Clmgetmoney, a.Returnpay,a.Cessionamount    from RIRecordTrace a, RIPolRecordBake c "
				+ " where c.Eventno = a.Eventno and  c.RIContNo ='"
				+ RIContNo
				+ "' "
				+ "  and   a.RIDate  between To_Date('"
				+ Startdate
				+ "' , 'yyyy-mm-dd')    and   To_Date('"
				+ Enddate
				+ "' , 'yyyy-mm-dd')  "
				+ "  and   a.IncomeCompanyNo='"
				+ RIComCode
				+ "'   "
				+ "  and c.Eventtype = '04') m  group by  m.Polno,m.Getdutykind,m.Riskcode");
		tSSRS = new ExeSQL().execSQL(sqlBillp.toString());
		if (tSSRS.getMaxRow() != 0) {
			strArr = new String[tSSRS.getMaxCol()];
			for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
				for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
					strArr[b - 1] = tSSRS.GetText(a, b);
				}
				tlistTable.add(strArr);
				strArr = new String[tSSRS.getMaxCol()];
			}
		}
	}
	if (billType.equals("B6")) {
		tTempleteID = "002970";
		texttag.add("Incomecompanyno", RICoName);
		texttag.add("CalYear", CalYear);
		texttag.add("seaType", seaType);
		if (seaType.equals("Q1")) {
			Startdate = CalYear + "-" + 1 + "-" + 1;
			Enddate = CalYear + "-" + 3 + "-" + 31;
		}
		if (seaType.equals("Q2")) {
			Startdate = CalYear + "-" + 4 + "-" + 1;
			Enddate = CalYear + "-" + 6 + "-" + 30;
		}
		if (seaType.equals("Q3")) {
			Startdate = CalYear + "-" + 7 + "-" + 1;
			Enddate = CalYear + "-" + 9 + "-" + 30;
		}
		if (seaType.equals("Q4")) {
			Startdate = CalYear + "-" + 10 + "-" + 1;
			Enddate = CalYear + "-" + 12 + "-" + 31;
		}
		tlistTable.setName("LLCInfo");
		StringBuffer sqlBillEn = new StringBuffer();
		sqlBillEn
				.append("SELECT m.Polno,m.Getdutykind,m.Riskcode,sum(Curentamnt),sum(Clmgetmoney),sum(Returnpay)  "
						+ " FROM (select c.Polno,  (SELECT d.codename  FROM ldcode d "
						+ " where d.code in (select b. Getdutykind   from LLClaimDetail b, RIPolRecordBake c  where c.Polno = b.Polno  and c.Riskcode = b.Riskcode and c.Dutycode = b.Getdutycode)) Getdutykind,  "
						+ " a.Riskcode,a.Curentamnt,  c.Clmgetmoney, a.Returnpay,a.Cessionamount    from RIRecordTrace a, RIPolRecordBake c "
						+ " where c.Eventno = a.Eventno   "
						+ "  and   a.RIDate  between To_Date('"
						+ Startdate
						+ "' , 'yyyy-mm-dd')    and   To_Date('"
						+ Enddate
						+ "' , 'yyyy-mm-dd')  "
						+ "  and   a.IncomeCompanyNo='"
						+ RIComCode
						+ "'   "
						+ "  and c.Eventtype = '04') m  group by  m.Polno,m.Getdutykind,m.Riskcode");
		tSSRS = new ExeSQL().execSQL(sqlBillEn.toString());
		if (tSSRS.getMaxRow() != 0) {
			strArr = new String[tSSRS.getMaxCol()];
			for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
				for (int b = 1; b <= tSSRS.getMaxCol(); b++) {
					strArr[b - 1] = tSSRS.GetText(a, b);
				}
				tlistTable.add(strArr);
				strArr = new String[tSSRS.getMaxCol()];

			}
		}
	}

	tTransferData.setNameAndValue("ArrayList", tArr);
	tTransferData.setNameAndValue("TempleteID", tTempleteID);
	VData tVData = new VData();
	tVData.addElement(tTransferData);

	if (!tBusinessDelegate.submitData(tVData, "", tBusiName)) {
		Content = ""+" "+"";
	} else {
		tXmlExportNew = (XmlExportNew) tBusinessDelegate.getResult()
				.get(0);
		if (texttag.size() != 0) {
			tXmlExportNew.addTextTag(texttag);
		}
		if (tlistTable.getName() != null) {
			tXmlExportNew.addListTable(tlistTable, strArr);
		}

		tInput = tXmlExportNew.getInputStream();
		request.setAttribute("PrintStream", tInput);
		request.getRequestDispatcher("../print/ShowPrintTest.jsp")
				.forward(request, response);
	}
%>
<html>
<script type="text/javascript">
     myAlert("<%=Content%>");
	top.close();
</script>
</html>