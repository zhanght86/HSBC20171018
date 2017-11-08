<%@include file="/i18n/language.jsp"%>
<%@page import="org.omg.PortableInterceptor.SYSTEM_EXCEPTION"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>
<%
	String tTempleteID = request.getParameter("TempleteID");
	String tableType = request.getParameter("tableType");
	ListTable tlistTableFirst = new ListTable();
	ListTable tlistTableSecond = new ListTable();
	ListTable tlistTable = new ListTable();
	String []strArr=new String[1000];
	String []strArrSend=new String[1000];
	System.out.println(tTempleteID);
	String tBusiName = "CreateXMLFile";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	TransferData tTransferData = new TransferData();
	XmlExportNew tXmlExportNew = new XmlExportNew();
	ArrayList tArr = new ArrayList();
	//HashMap tMap = new HashMap();
	String Content ="";
	InputStream tInput;
	int total=0;
	Double Count1=0.0;
	Double Count2=0.0;
	Double Count3=0.0;
	int Second=0;
	Double Second1=0.0;
	Double Second2=0.0;
	Double Second3=0.0;
	
	TextTag texttag = new TextTag();
	System.out.println("___________________________");	
	ExeSQL tExeSQL = new ExeSQL();
	SSRS tSSRS = new SSRS();
	SSRS tSSRSTWO = new SSRS();
	SSRS tSSRSTHR = new SSRS();
	
	PubFun pubFun= new PubFun();

	if(tableType!=null){
		//section1----START
				if(tableType.equals("section1")){
					String EndDate = request.getParameter("EndDate");
					tTempleteID = "003068";
					texttag.add("EndDate", EndDate);
				}
		//section1----END
		//section2----START
				if(tableType.equals("section2"))
				{
					tTempleteID = "002856";
					//死亡自留额
				    String sql = "select nvl(sum(a.Curentamnt-a.Cessionamount),0),count(*) from RIRecordTrace a where a.Accumulatedefno in ('L01010010010', 'L01010010011')";
				    String sql2 = "select nvl(sum(aa.Riskamnt),0),count(*) from lcpol aa, lcduty x where aa.Insuredno not in (select b.Otherno from RIRecordTrace b) and aa.Polno = x.Polno and x.Dutycode in (select c.Getdutycode from RIAccumulateGetDuty c where c.Accumulatedefno in ('L01010010010', 'L01010010011'))";
					tSSRS = tExeSQL.execSQL(sql);
					tSSRSTWO = tExeSQL.execSQL(sql2);
					String individaul_life =String.valueOf(Double.parseDouble(tSSRS.GetText(1, 1)) + Double.parseDouble(tSSRSTWO.GetText(1, 1)));
					String number_life =  String.valueOf(Double.parseDouble(tSSRS.GetText(1, 2)) + Double.parseDouble(tSSRSTWO.GetText(1, 2)));
					//意外自留额
					String sql3 = "select nvl(sum(a.Curentamnt-a.Cessionamount),0),count(*) from RIRecordTrace a where a.Accumulatedefno in ('L01010010020','L01010010030')";
					String sql4 = "select nvl(sum(aa.Riskamnt),0),count(*) from lcpol aa, lcduty x where aa.Insuredno not in (select b.Otherno from RIRecordTrace b) and aa.Polno = x.Polno and x.Dutycode in (select c.Getdutycode from RIAccumulateGetDuty c where c.Accumulatedefno in ('L01010010010', 'L01010010011'))";
					tSSRS = tExeSQL.execSQL(sql3);
					tSSRSTWO = tExeSQL.execSQL(sql4);
					String individual_pa = String.valueOf(Double.parseDouble(tSSRS.GetText(1, 1)) + Double.parseDouble(tSSRSTWO.GetText(1, 1)));
					String number_pa = String.valueOf(Double.parseDouble(tSSRS.GetText(1, 2)) + Double.parseDouble(tSSRSTWO.GetText(1, 2)));
					texttag.add("individaul_life", individaul_life);
					texttag.add("individual_pa", individual_pa);
					texttag.add("number_life", number_life);
					texttag.add("number_pa", number_pa);
				}
		//section2----END
		//section3----START
				if(tableType.equals("section3")){
					tTempleteID = "003540";
				String sqlSection3;
				String sqlSection4;
				String sqlSection5;
				for(int i=1;i<14;i++){
					sqlSection5 ="SELECT   a.Codealias,a.Code,a.Codename,a.Comcode  FROM  ldcode a    where a.Codetype = 'ribilldate'   and a.Comcode = '"+i+"' ";
					tSSRSTHR = new ExeSQL().execSQL(sqlSection5);
					if(tSSRSTHR.GetText(1, 3).equals("and more")){
						sqlSection3 ="SELECT  decode(count(*),0,999999999,count(*)),nvl(sum(d.aa),''),nvl((sum(d.aa)-sum(d.bb)),''), round((sum(d.aa) - sum(d.bb)) / count(*),2.1) " +
				                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
				                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010010', 'L01010010011') and a.Curentamnt > '"+tSSRSTHR.GetText(1, 2)+"'" +
				                  " group by a.Riskcode, a.Dutycode) d";	
						sqlSection4 ="SELECT decode(count(*),0,999999999,count(*)),nvl(sum(d.aa),0),nvl((sum(d.aa)-sum(d.bb)),0), round((sum(d.aa) - sum(d.bb)) / count(*),2.1) " +
				                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
				                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010020', 'L01010010021') and a.Curentamnt > '"+tSSRSTHR.GetText(1, 2)+"'" +
				                  " group by a.Riskcode, a.Dutycode) d";	
					}else{
						sqlSection3 ="SELECT decode(count(*),0,999999999,count(*)),nvl(sum(d.aa),''),nvl((sum(d.aa)-sum(d.bb)),''), round((sum(d.aa) - sum(d.bb)) / count(*),2.1)" +
				                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
				                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010010', 'L01010010011') and a.Curentamnt between '"+tSSRSTHR.GetText(1, 2)+"' and '"+tSSRSTHR.GetText(1, 3)+"'" +
				                  " group by a.Riskcode, a.Dutycode) d";
						sqlSection4 ="SELECT  decode(count(*),0,999999999,count(*)),nvl(sum(d.aa),0),nvl((sum(d.aa)-sum(d.bb)),0), round((sum(d.aa) - sum(d.bb)) / count(*),2.1) " +
				                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
				                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010020', 'L01010010021') and a.Curentamnt between '"+tSSRSTHR.GetText(1, 2)+"' and '"+tSSRSTHR.GetText(1, 3)+"'" +
				                  " group by a.Riskcode, a.Dutycode) d";
					}
					tSSRS = new ExeSQL().execSQL(sqlSection3);
					tSSRSTWO = new ExeSQL().execSQL(sqlSection4);	
					tSSRSTHR = new ExeSQL().execSQL(sqlSection5);
					tlistTableFirst.setName("FIRSTROW");
					if(tSSRS.GetText(1,1).equals("999999999")){
						System.out.println(tSSRS.GetText(1,1));
					     
					     strArr=new String[tSSRS.getMaxCol()+1];
				        for (int a=1;a<=tSSRS.getMaxRow();a++)
				        {
				        	strArr[0]=tSSRSTHR.GetText(1, 1);
				          tlistTableFirst.add(strArr);
				          strArr=new String[tSSRS.getMaxCol()+1];
				        }
						
					}else {
						
				    strArr=new String[tSSRS.getMaxCol()+1];
				        for (int a=1;a<=tSSRS.getMaxRow();a++)
				        {
				        	strArr[0]=tSSRSTHR.GetText(1, 1);
				        	 total=Integer.parseInt(tSSRS.GetText(1,1))+total;
				        	 Count1=Double.parseDouble(tSSRS.GetText(1,2))+Count1;
				        	 Count2=Double.parseDouble(tSSRS.GetText(1,3))+Count2;
				        	 Count3=Double.parseDouble(tSSRS.GetText(1,4))+Count3;
				        	 
				          for (int b=1;b<=tSSRS.getMaxCol();b++)
				          {
				        	  
				            strArr[b]=tSSRS.GetText(a,b) ;
				          }
				          tlistTableFirst.add(strArr);
				          strArr=new String[tSSRS.getMaxCol()+1];
				        }
				            }
					
				   tlistTableSecond.setName("SECONDROW");
				   if(tSSRSTWO.GetText(1,1).equals("999999999")){
					     strArr=new String[tSSRSTWO.getMaxCol()+1];
				        for (int a=1;a<=tSSRSTWO.getMaxRow();a++)
				        {
				        	strArr[0]=tSSRSTHR.GetText(1, 1);
				        	tlistTableSecond.add(strArr);
				          strArr=new String[tSSRSTWO.getMaxCol()+1];
				        }
					}else {
				    strArr=new String[tSSRSTWO.getMaxCol()+1];
				        for (int a=1;a<=tSSRSTWO.getMaxRow();a++)
				        {
				        	strArr[0]=tSSRSTHR.GetText(1, 1);
				        	Second=Integer.parseInt(tSSRSTWO.GetText(1,1))+Second;
				        	Second1=Double.parseDouble(tSSRSTWO.GetText(1,2))+Second1;
				        	Second2=Double.parseDouble(tSSRSTWO.GetText(1,3))+Second2;
				        	Second3=Double.parseDouble(tSSRSTWO.GetText(1,4))+Second3;
				          for (int b=1;b<=tSSRSTWO.getMaxCol();b++)
				          {
				            strArr[b]=tSSRSTWO.GetText(a,b) ;
				          }
				          tlistTableSecond.add(strArr);
				          strArr=new String[tSSRSTWO.getMaxCol()+1];
				   
				        }
				      }	  
			      }
				texttag.add("Count", total);
				texttag.add("Count1", Count1);
				texttag.add("Count2", Count2);
				texttag.add("Count3", Count3);
				texttag.add("Second", Second);
				texttag.add("Second1", Second1);
				texttag.add("Second2", Second2);
				texttag.add("Second3", Second3);
				
				}
		//section3----END
		//section4----START
				if(tableType.equals("section4")){
					tTempleteID = "003070";	
						}
		//section4----END
		//section5----START
				if(tableType.equals("section5")){
					tTempleteID = "002859";	
						}
		//section5----END
		//section6----START
				if(tableType.equals("section6")){
					tTempleteID = "002860";	
						}
		//section6----END
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
	if(tlistTableFirst.getName()!=null){
		tXmlExportNew.addListTable(tlistTableFirst, strArr);  
	}
	if(tlistTableSecond.getName()!=null){
		tXmlExportNew.addListTable(tlistTableSecond, strArrSend);
	}
	if(tlistTable.getName()!=null){
		tXmlExportNew.addListTable(tlistTable, strArr);
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