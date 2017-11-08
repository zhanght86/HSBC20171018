<%@include file="/i18n/language.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String tTempleteID = request.getParameter("TempleteID");
String billType = request.getParameter("tableType");

String tBusiName = "CreateXMLFile";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
TransferData tTransferData = new TransferData();
XmlExportNew tXmlExportNew = new XmlExportNew();
ArrayList tArr = new ArrayList();//
String Content ="";
InputStream tInput;
double total = 0;
double ProLosAmnt = 0;
double totalr = 0 ;
TextTag texttag = new TextTag();
SSRS tSSRS = new SSRS();
PubFun pubFun= new PubFun();

//中銀巨災再保帳單----START

if (billType.equals("001")) { 

		 tTempleteID = "003020";
		     StringBuffer sqlBillJz = new StringBuffer();
		     sqlBillJz.append("select b.Startdate,b.Enddate,b.Currency,a.Premchang,a.Commchang,c.Clmgetmoney,a.Returnpay,b.Operator,b.Uwoperator ");
		     sqlBillJz.append("from RIRecordTrace a, RIBsnsBillBatch b,Ripolrecord c ");
		     sqlBillJz.append("where a.Currency = b.Currency ");
		     sqlBillJz.append("and a.Billno = b.Billno ");
		     sqlBillJz.append("and a.Billbatchno = b.Batchno ");
		     sqlBillJz.append("and c.Eventno = a.Eventno ");
		     sqlBillJz.append("and a.Eventtype = '04' ");
			 tSSRS = new ExeSQL().execSQL(sqlBillJz.toString());	
			 String SdEd=tSSRS.GetText(1, 1)+"-"+tSSRS.GetText(1, 2);
			 texttag.add("SdEd" ,SdEd);
			 texttag.add("Currency",request.getParameter("CurrencyName"));
			 texttag.add("Premchang",tSSRS.GetText(1, 4));
			 texttag.add("Commchang",tSSRS.GetText(1, 5));
			 texttag.add("Clmgetmoney" ,tSSRS.GetText(1, 6));
			 texttag.add("Returnpay",tSSRS.GetText(1, 7));
			 texttag.add("Operator" ,tSSRS.GetText(1, 8));
			 texttag.add("Uwoperator" ,tSSRS.GetText(1, 9));
			 texttag.add("Date", pubFun.getCurrentDate());
	
	//中銀巨災再保帳單----END
		  }    
	
		
tTransferData.setNameAndValue("ArrayList",tArr);
tTransferData.setNameAndValue("TempleteID",tTempleteID);
VData tVData=new VData();
tVData.addElement(tTransferData);

if(!tBusinessDelegate.submitData(tVData,"",tBusiName))
{
	Content=""+"账单不存在"+"";
}
else
{
	tXmlExportNew = (XmlExportNew)tBusinessDelegate.getResult().get(0);
	if(texttag.size()!=0)
	{
		tXmlExportNew.addTextTag(texttag);
	}
	
	
	tInput = tXmlExportNew.getInputStream();
	request.setAttribute("PrintStream", tInput);
	request.getRequestDispatcher("../print/ShowPrintTest.jsp").forward(request,response);
}

%>
<html>
  <script type="text/javascript">
     myAlert("<%=Content%>");
     top.close();
  </script>
</html>