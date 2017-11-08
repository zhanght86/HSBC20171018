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
<%
String tTempleteID = request.getParameter("TempleteID");
String tableType = request.getParameter("tableType");
//xf start
String Ricontno = request.getParameter("Ricontno");
String Batchno = request.getParameter("Batchno");
String Currency = request.getParameter("Currency");
String Billno = request.getParameter("Billno");
String printType = request.getParameter("printType");

String ReprotType = request.getParameter("ReprotType");
//xf end
ListTable tlistTableFirst = new ListTable();
ListTable tlistTableSecond = new ListTable();
ListTable tlistTable = new ListTable();
String []strArr=new String[1000];
String []strArrSend=new String[1000];

String tBusiName = "CreateXMLFile";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
TransferData tTransferData = new TransferData();
XmlExportNew tXmlExportNew = new XmlExportNew();
//HashMap tMap = new HashMap();
ArrayList tArr = new ArrayList();//鐎涙ɑ鏂侀弽鍥╊劮
String Content ="";
InputStream tInput;
TextTag texttag = new TextTag();
ExeSQL tExeSQL = new ExeSQL();
SSRS tSSRS = new SSRS();
SSRS tSSRSTWO = new SSRS();
PubFun pubFun= new PubFun();
tSSRS = null;
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
			tTempleteID = "003069";
		//
		String[] arr1 = {"0","50001","100001","200001","300001","500001","800001","1000001","2000001","3000001","5000001","10000001","20000000"};
		String[] arr2 = {"50000","100000","200000","300000","500000","800000","1000000","2000000","3000000","5000000","10000000","20000000","more"};
		String sqlSection3;
		String sqlSection4;
		for(int i=0;i<arr1.length;i++){
			if(arr2[i]=="more"){
				sqlSection3 ="SELECT  count (*),nvl(sum(d.aa),0),nvl((sum(d.aa)-sum(d.bb)),0),nvl((sum(d.aa)-sum(d.bb))/count (*),0) " +
		                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
		                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010010', 'L01010010011') and a.Curentamnt > '"+arr1[i]+"'" +
		                  " group by a.Riskcode, a.Dutycode) d";	
				sqlSection4 ="SELECT  count (*),nvl(sum(d.aa),0),nvl((sum(d.aa)-sum(d.bb)),0),nvl((sum(d.aa)-sum(d.bb))/count (*),0) " +
		                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
		                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010020', 'L01010010021') and a.Curentamnt > '"+arr1[i]+"'" +
		                  " group by a.Riskcode, a.Dutycode) d";	
			}
			else{
				sqlSection3 ="SELECT  count (*),nvl(sum(d.aa),0),nvl((sum(d.aa)-sum(d.bb)),0),nvl((sum(d.aa)-sum(d.bb))/count (*),0) " +
		                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
		                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010010', 'L01010010011') and a.Curentamnt between '"+arr1[i]+"' and '"+arr2[i]+"'" +
		                  " group by a.Riskcode, a.Dutycode) d";
				sqlSection4 ="SELECT  count (*),nvl(sum(d.aa),0),nvl((sum(d.aa)-sum(d.bb)),0),nvl((sum(d.aa)-sum(d.bb))/count (*),0) " +
		                  "FROM (SELECT a.Riskcode, a.Dutycode,sum(a.Curentamnt)  aa ,sum(a.Cessionamount) bb " +
		                  "FROM RIRecordTrace a where a.Accumulatedefno in  ('L01010010020', 'L01010010021') and a.Curentamnt between '"+arr1[i]+"' and '"+arr2[i]+"'" +
		                  " group by a.Riskcode, a.Dutycode) d";
			}
			tSSRS = new ExeSQL().execSQL(sqlSection3);
			tSSRSTWO = new ExeSQL().execSQL(sqlSection4);	
			tlistTableFirst.setName("FIRSTROW");
		    strArr=new String[tSSRS.getMaxCol()+1];
		        for (int a=1;a<=tSSRS.getMaxRow();a++)
		        {
		        	strArr[0]=arr1[i]+"-"+arr2[i];
		          /**将所得到得符合条件得记录逐条加入到strArr中相应得位置中*/
		          for (int b=1;b<=tSSRS.getMaxCol();b++)
		          {
		            strArr[b]=tSSRS.GetText(a,b) ;
		          }
		          tlistTableFirst.add(strArr);
		          strArr=new String[tSSRS.getMaxCol()+1];
		   
		        }
		   tlistTableSecond.setName("SECONDROW");
		   strArrSend=new String[tSSRSTWO.getMaxCol()+1];
		        for (int a=1;a<=tSSRSTWO.getMaxRow();a++)
		        {
		        	strArrSend[0]=arr1[i]+"-"+arr2[i];
		          /**将所得到得符合条件得记录逐条加入到strArr中相应得位置中*/
		          for (int b=1;b<=tSSRSTWO.getMaxCol();b++)
		          {
		        	  strArrSend[b]=tSSRSTWO.GetText(a,b) ;
		          }
		          tlistTableSecond.add(strArrSend);
		          strArrSend=new String[tSSRSTWO.getMaxCol()+1];
		        }
		}	 
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

//临分 ----START  

if (ReprotType!= null) {
if (ReprotType.equals("01")) {
	 
	 tTempleteID = "003018";
	    tlistTable.setName("TEMPROW");//注意这里是循环体表格名称   
	     SSRS aSSRS =new SSRS();
	      String aasql="SELECT a.Proposalcontno ,a.Insuredname,a.Signdate,a.Amnt,c.Codename  FROM lccont a, Rigrpstate b,ldcode c  where a.Proposalcontno = b.Proposalcontno and a.Currency = c.Code and c.Codetype = 'currcode' and b.State = '02'";
	     ExeSQL aExeSQL =new ExeSQL();
	     aSSRS=aExeSQL.execSQL(aasql);
	      strArr=new String[aSSRS.getMaxCol()];
	      for (int a=1;a<=aSSRS.getMaxRow();a++)
	      {
	        /**将所得到得符合条件得记录逐条加入到strArr中相应得位置中*/
	        for (int b=1;b<=aSSRS.getMaxCol();b++)
	        {
	          strArr[b-1]=aSSRS.GetText(a,b) ;
	        }
	        tlistTable.add(strArr);
	        strArr=new String[aSSRS.getMaxCol()];
	  }	      
 }
}    

//临分 ----END

if (Billno!= null){
//理賠帳單格式_中文----START
	 if (Billno.substring(0,2).equals("B3")&&printType.equals("01")) {
		tTempleteID = "002904";	
		String billno = request.getParameter("billno");
		StringBuffer sqlBill = new StringBuffer();
		sqlBill.append("select (select d.ComPanyName from RIComInfo d where a.Incomecompanyno=d.ComPanyNo),(select e.InsuredName from lcpol e where e.InsuredNo=a.Otherno),c.Polno,(select b.Getdutykind from LLClaimDetail b where   c.Polno = b.Polno and c.Riskcode = b.Riskcode  and c.Dutycode = b.Getdutycode), a.Riskcode,a.Curentamnt,a.Cessionamount,c.ClmGetMoney,a.Returnpay "+
					   " from RIRecordTrace a ,RIPolRecordBake c "+
					   " where  c.Eventtype='04' "+
					   " and c.Eventno = a.Eventno "+
					   " and a.Billno='"+billno+"' "+
					   " and a.Accumulatedefno in ('L01010010010','L01010010011')");

		tSSRS = new ExeSQL().execSQL(sqlBill.toString());	
		for(int i = 0 ;i < tSSRS.getMaxRow();i++){
				texttag.add("Incomecompanyno", tSSRS.GetText(i, 1));
				texttag.add("Otherno", tSSRS.GetText(i, 2));
				texttag.add("Polno", tSSRS.GetText(i, 3));
				texttag.add("Getdutykind", tSSRS.GetText(i, 4));
				texttag.add("Riskcode", tSSRS.GetText(i, 5));
				texttag.add("Curentamnt", tSSRS.GetText(i, 6));
				texttag.add("Cessionamount", tSSRS.GetText(i, 7));
				texttag.add("Realpay", tSSRS.GetText(i, 8));
				texttag.add("Returnpay", tSSRS.GetText(i, 9));
				texttag.add("Date", pubFun.getCurrentDate());
				
	 }
	}
//理賠帳單格式_中文----END

//理賠帳單格式_英文----START
 if (Billno.substring(0,2).equals("B3")&&printType.equals("02")) {
	 tTempleteID = "002970";
       StringBuffer sqlBillEn = new StringBuffer();
       sqlBillEn.append("select (select d.ComPanyName from RIComInfo d where a.Incomecompanyno=d.ComPanyNo),(select e.InsuredName from lcpol e where e.InsuredNo=a.Otherno),c.Polno,(select b.Getdutykind from LLClaimDetail b where   c.Polno = b.Polno and c.Riskcode = b.Riskcode  and c.Dutycode = b.Getdutycode), a.Riskcode,a.Curentamnt,a.Cessionamount,c.ClmGetMoney,a.Returnpay "+
					   " from RIRecordTrace a ,RIPolRecordBake c "+
					   " where c.Eventtype='04' "+
					   " and c.Eventno = a.Eventno "+
					   " and a.Accumulatedefno in ('L01010010010','L01010010011')");
		tSSRS = new ExeSQL().execSQL(sqlBillEn.toString());
		for(int i = 0 ;i < tSSRS.getMaxRow();i++){
			texttag.add("Incomecompanyno", tSSRS.GetText(i, 1));
			texttag.add("Otherno", tSSRS.GetText(i, 2));
			texttag.add("Polno", tSSRS.GetText(i, 3));
			texttag.add("Getdutykind", tSSRS.GetText(i, 4));
			texttag.add("Riskcode", tSSRS.GetText(i, 5));
			texttag.add("Curentamnt", tSSRS.GetText(i, 6));
			texttag.add("Cessionamount", tSSRS.GetText(i, 7));
			texttag.add("Realpay", tSSRS.GetText(i, 8));
			texttag.add("Returnpay", tSSRS.GetText(i, 9));
			texttag.add("Date", pubFun.getCurrentDate());
				
		 }
}
//理賠帳單格式_英文----END



     
//中銀巨災再保帳單----START
if (Billno.substring(0,2).equals("B4")){
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
		 texttag.add("Currency",tSSRS.GetText(1, 3));
		 texttag.add("Premchang",tSSRS.GetText(1, 4));
		 texttag.add("Commchang",tSSRS.GetText(1, 5));
		 texttag.add("Clmgetmoney" ,tSSRS.GetText(1, 6));
		 texttag.add("Returnpay",tSSRS.GetText(1, 7));
		 texttag.add("Operator" ,tSSRS.GetText(1, 8));
		 texttag.add("Uwoperator" ,tSSRS.GetText(1, 9));
		 texttag.add("Date", pubFun.getCurrentDate());
}
//中銀巨災再保帳單----END
 
  //账单公共部分----START
		
		StringBuffer sqlBill = new StringBuffer();
		sqlBill.append("select a.Feename,a.Summoney,d.Ricontname,c.Billtimes,a.Billitem  "+
					   "  from Ribsnsbilldetails a, Ribsnsbillrela b, RIBsnsBillBatch c ,RIBarGainInfo d  "+
					   " where a.Billno = b.Billno and a.Feecode = b.Feecode   and a.Billno = c.Billno  and c.RIContNo = d.Ricontno  and a.Batchno=c.Batchno   "+
					   "   and c.Batchno = '"+Batchno+"' "+
					   " and c.Ricontno ='"+Ricontno+"' "+
					   " and a.Currency = '"+Currency+"'  Order by a. Feecode  ");
		tSSRS = new ExeSQL().execSQL(sqlBill.toString());	
		
		double total = 0;
		double ProLosAmnt = 0;
		double totalr = 0 ;
//共保公共部分----END

 //共保账单----START
	if (Ricontno.equals("C01010040")||Ricontno.equals("C01010060")||Ricontno.equals("C01010070")) { 
		tTempleteID = "002876";	
		   if(tSSRS.getMaxRow()!=0){
		totalr = Double.parseDouble(tSSRS.GetText(1, 2))+ Double.parseDouble(tSSRS.GetText(2, 2));
		total = Double.parseDouble(tSSRS.GetText(3, 2))+Double.parseDouble(tSSRS.GetText(4, 2))+Double.parseDouble(tSSRS.GetText(5, 2))+Double.parseDouble(tSSRS.GetText(6, 2));
		ProLosAmnt = total - totalr;
		if (ProLosAmnt >= 0){
			texttag.add("ProLosAmntL", ProLosAmnt);
		}else {	texttag.add("ProLosAmntR", ProLosAmnt);
		}
				texttag.add("RIComName", tSSRS.GetText(7, 5));
				texttag.add("BillTimes", tSSRS.GetText(1, 4));
				texttag.add("BusyDate", pubFun.getCurrentDate().substring(0,4));//业务年度
				texttag.add("RIContNo", tSSRS.GetText(1, 3));
				texttag.add("FirstPrem", tSSRS.GetText(1, 2));
				texttag.add("Rprem", tSSRS.GetText(2, 2));
				texttag.add("CommChang", tSSRS.GetText(3, 2));
				texttag.add("DeathBenefit", tSSRS.GetText(4, 2));
				texttag.add("ReturnPay", tSSRS.GetText(5, 2));
				texttag.add("EarnedPay", tSSRS.GetText(6, 2));
				texttag.add("Total", totalr);
				texttag.add("PrintDate", pubFun.getCurrentDate());
		
		}			
  }    //共保账单----END
  
 //再保帳單格式_中文----START  13 
   if (Billno.substring(0,2).equals("B1")&&printType.equals("01")) { 
	   tTempleteID = "002877";	
	   if(tSSRS.getMaxRow()!=0){
		totalr = Double.parseDouble(tSSRS.GetText(1, 2))+ Double.parseDouble(tSSRS.GetText(7, 2));
		total = Double.parseDouble(tSSRS.GetText(2, 2))+Double.parseDouble(tSSRS.GetText(8, 2))+Double.parseDouble(tSSRS.GetText(3, 2))+Double.parseDouble(tSSRS.GetText(6, 2));
		ProLosAmnt = total - totalr;
		texttag.add("RIComName", tSSRS.GetText(9, 5));
		texttag.add("BillTimes", tSSRS.GetText(1, 4));
		texttag.add("RIContNo", tSSRS.GetText(1, 3));
		texttag.add("Name",tSSRS.GetText(11, 5));
		texttag.add("PrintDate", pubFun.getCurrentDate());
     if (Currency.equals("13")){
	
		if (ProLosAmnt >= 0){
			texttag.add("ProLosAmntHL", ProLosAmnt);
		}else {	texttag.add("ProLosAmntHR", ProLosAmnt);
		}
				texttag.add("CommChangH", tSSRS.GetText(2, 2));//分保佣金
				texttag.add("ChangeHL", tSSRS.GetText(8, 2));
				texttag.add("ReturnPayH", tSSRS.GetText(3, 2));
				texttag.add("Fee006H", tSSRS.GetText(6, 2));
				
				texttag.add("TotalH", totalr);
				texttag.add("PremChangH", tSSRS.GetText(1, 2));//分保保费
				texttag.add("ChangeHR", tSSRS.GetText(7, 2));
     }
				
      //再保帳單格式_中文----END
        //再保帳單格式_中文----START  14
     if (Currency.equals("14")){
    	
    	
    		if (ProLosAmnt >= 0){
    			texttag.add("ProLosAmntUL", ProLosAmnt);
    		}else {	texttag.add("ProLosAmntUR", ProLosAmnt);
    		}
			texttag.add("CommChangU", tSSRS.GetText(2, 2));//分保佣金
			texttag.add("ChangeUL", tSSRS.GetText(8, 2));
			texttag.add("ReturnPayU", tSSRS.GetText(3, 2));
			texttag.add("Fee006U", tSSRS.GetText(6, 2));
			
			texttag.add("TotalU", totalr);
			texttag.add("PremChangU", tSSRS.GetText(1, 2));//分保保费
			texttag.add("ChangeUR", tSSRS.GetText(7, 2));
            }
     }
   }
        //再保帳單格式_中文----END  14
        
  //再保帳單格式_英文----START 13
   if (Billno.substring(0,2).equals("B1")&&printType.equals("02")) { 
	   tTempleteID = "003019";	
	   if(tSSRS.getMaxRow()!=0){
    if (Currency.equals("13")){
		total = Double.parseDouble(tSSRS.GetText(3, 2))+Double.parseDouble(tSSRS.GetText(1, 2))+Double.parseDouble(tSSRS.GetText(7, 2))+Double.parseDouble(tSSRS.GetText(8, 2));
		totalr = total + Double.parseDouble(tSSRS.GetText(3, 2));
		
				texttag.add("PremChangH", tSSRS.GetText(1, 2));
				texttag.add("ChangeHR", tSSRS.GetText(7, 2));
				texttag.add("CommChangH", tSSRS.GetText(3, 2));
				texttag.add("ChangeHL", tSSRS.GetText(8, 2));
				
				texttag.add("TotalH", total);
				texttag.add("ReturnPayH", tSSRS.GetText(3, 2));
				texttag.add("BalanceH", totalr);
        } //再保帳單格式_英文----END 13
        
        //再保帳單格式_英文----START 14
    if (Currency.equals("14")){
    	total = Double.parseDouble(tSSRS.GetText(3, 2))+Double.parseDouble(tSSRS.GetText(1, 2))+Double.parseDouble(tSSRS.GetText(7, 2))+Double.parseDouble(tSSRS.GetText(8, 2));
		totalr = total + Double.parseDouble(tSSRS.GetText(3, 2));
		
				texttag.add("PremChangU", tSSRS.GetText(1, 2));
				texttag.add("ChangeUR", tSSRS.GetText(7, 2));
				texttag.add("CommChangU", tSSRS.GetText(3, 2));
				texttag.add("ChangeUL", tSSRS.GetText(8, 2));
				
				texttag.add("TotalU", total);
				texttag.add("ReturnPayU", tSSRS.GetText(3, 2));
				texttag.add("BalanceU", totalr);
        } //再保帳單格式_英文----END 14
	   }
	   }
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
Billno="";
Ricontno  = "";
Batchno ="";
Currency = "";
%>
<html>
  <script type="text/javascript">
     myAlert("<%=Content%>");
     top.close();
  </script>
</html>