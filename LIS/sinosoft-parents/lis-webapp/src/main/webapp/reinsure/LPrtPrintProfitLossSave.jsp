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
<%@page import= "java.text.DecimalFormat"%>
<%
String tTempleteID = request.getParameter("TempleteID");
String RiProfitNo = "";
String CalYear  = request.getParameter("CalYear");
String RIcomCode  = request.getParameter("RIcomCode");
String ContNo  = request.getParameter("ContNo");
DecimalFormat df = new DecimalFormat("###,##0.00");

ListTable tlistTable = new ListTable();
String tBusiName = "CreateXMLFile";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
TransferData tTransferData = new TransferData();
XmlExportNew tXmlExportNew = new XmlExportNew();
ArrayList tArr = new ArrayList();//   
String Content ="";
InputStream tInput;
TextTag texttag = new TextTag();
ExeSQL tExeSQL = new ExeSQL();
SSRS tSSRSH = new SSRS();
SSRS tSSRSU = new SSRS();
SSRS tSSRSTWO = new SSRS();
PubFun pubFun= new PubFun();
String fm ="FM9,999,999,999,999,990.00";

   String sqla= "SELECT d.Riprofitno  FROM RIProfitDef d where d.Recomcode ='"+RIcomCode+"' and d.Ricontno='"+ContNo+"' ";
	tSSRSH= new ExeSQL().execSQL(sqla);	
	RiProfitNo =tSSRSH.GetText(1,1);
						StringBuffer sqlBillJz = new StringBuffer();
						sqlBillJz.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt,To_char( r.Prolosamnt,'"+fm+"') THP, (1-b.Factorvalue), b.Factorcode, b.Currency,	 b.Recomcode");
						sqlBillJz.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno and r.State='03'   and b.Currency in ('13')  ");
						sqlBillJz.append(" and  b.Riprofitno = '"+RiProfitNo+"' ");
						sqlBillJz.append(" and b.Calyear = '"+CalYear+"' order by Factorcode ");
						tSSRSH= new ExeSQL().execSQL(sqlBillJz.toString());	
						if (tSSRSH.getMaxRow() !=0){
						texttag.add("CalYear",CalYear);
						texttag.add("ProRate" ,tSSRSH.GetText(7, 2));
						texttag.add("StratExpReserve",tSSRSH.GetText(10, 2));
						texttag.add("StratClaimReserve",tSSRSH.GetText(9, 2));
						texttag.add("CessPrem",tSSRSH.GetText(2, 2));
						texttag.add("ClaimPay" ,tSSRSH.GetText(3, 2));
						texttag.add("EndExpReserve" ,tSSRSH.GetText(5, 2));
						texttag.add("EndClaimReserve" ,tSSRSH.GetText(4, 2));
						texttag.add("ReturnPay" ,tSSRSH.GetText(8, 2));
						texttag.add("CessComm" ,tSSRSH.GetText(1, 2));
						texttag.add("LastYearLoss" ,tSSRSH.GetText(6, 2));
						texttag.add("ProLosAmnt" ,tSSRSH.GetText(1, 5));
						
						}
							 StringBuffer sqlBillJu = new StringBuffer();
						sqlBillJu.append("SELECT b.Factorname, To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt,To_char( r.Prolosamnt,'"+fm+"') THP, (1-b.Factorvalue), b.Factorcode, b.Currency,	 b.Recomcode");
						sqlBillJu.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno and r.State='03'  and b.Currency in ('14')  ");
						sqlBillJu.append(" and  b.Riprofitno = '"+RiProfitNo+"' ");
						sqlBillJu.append(" and b.Calyear = '"+CalYear+"' order by Factorcode  ");
						tSSRSU= new ExeSQL().execSQL(sqlBillJu.toString());	
						if (tSSRSU.getMaxRow() !=0){
						texttag.add("ProRateUs" ,tSSRSU.GetText(7, 2));
						texttag.add("StratExpReserveUs",tSSRSU.GetText(10, 2));
						texttag.add("StratClaimReserveUs" ,tSSRSU.GetText(9, 2));
						texttag.add("CessPremUs" ,tSSRSU.GetText(2, 2));
						texttag.add("ClaimPayUs" ,tSSRSU.GetText(3, 2));
						texttag.add("ReturnPayUs" ,tSSRSH.GetText(8, 2));
						texttag.add("EndExpReserveUs" ,tSSRSU.GetText(5, 2));
						texttag.add("EndClaimReserveUs" ,tSSRSU.GetText(4, 2));
						texttag.add("CessCommUs" ,tSSRSU.GetText(1, 2));
						texttag.add("LastYearLossUs" ,tSSRSU.GetText(6, 2));
						texttag.add("ProLosAmntUs" ,tSSRSU.GetText(1, 5));
						}

	     if (RIcomCode.equals("R0002")){
	    	 tTempleteID = "003150";  
	    	 
			   if(tSSRSH.getMaxRow()!=0){
				    Double YRate = Double.parseDouble(tSSRSH.GetText(2, 3))*Double.parseDouble(tSSRSH.GetText(11, 6));
				    Double Totle = Double.parseDouble(tSSRSH.GetText(1, 4))*Double.parseDouble(tSSRSH.GetText(7, 3));
					 texttag.add("YRate" ,df.format(YRate));
					 texttag.add("Totle" ,df.format(Totle));
			   }
			   if(tSSRSU.getMaxRow()!=0){	   
				    Double YRateUs = Double.parseDouble(tSSRSU.GetText(2, 3))*Double.parseDouble(tSSRSU.GetText(11, 6));
				    Double TotleUs = Double.parseDouble(tSSRSU.GetText(1, 4))*Double.parseDouble(tSSRSU.GetText(7, 3));
				    texttag.add("TotleUs" ,df.format(YRateUs));
				    texttag.add("YRateUs" ,df.format(TotleUs));
			   }
        }

		 if (RIcomCode.equals("R0003")){
	    	 tTempleteID = "003157";  
	    	 if(tSSRSH.getMaxRow()!=0){
				    Double WatPrem = Double.parseDouble(tSSRSH.GetText(2, 3))-Double.parseDouble(tSSRSH.GetText(1, 3));//A
				    Double ChReserve = Double.parseDouble(tSSRSH.GetText(10, 3))-Double.parseDouble(tSSRSH.GetText(5, 3));//B
				    Double Expense = WatPrem*Double.parseDouble(tSSRSH.GetText(11, 6));//C
				    Double Totle = Double.parseDouble(tSSRSH.GetText(1, 4))*Double.parseDouble(tSSRSH.GetText(7, 3));
					 texttag.add("WatPrem" ,df.format(WatPrem));
					 texttag.add("ChReserve" ,df.format(ChReserve));
					 texttag.add("Expense" ,df.format(Expense));
					 texttag.add("ResultCom" ,df.format(Totle));
			
			   }
			   if(tSSRSU.getMaxRow()!=0){	   
				   Double WatPremUs = Double.parseDouble(tSSRSU.GetText(2, 3))-Double.parseDouble(tSSRSU.GetText(1, 3));//A
				    Double ChReserveUs= Double.parseDouble(tSSRSU.GetText(10, 3))-Double.parseDouble(tSSRSU.GetText(5, 3));//B
				    Double ExpenseUs = WatPremUs*Double.parseDouble(tSSRSU.GetText(11, 6));//C
				    Double TotleUs = Double.parseDouble(tSSRSU.GetText(1, 4))*Double.parseDouble(tSSRSU.GetText(7, 3));
					 texttag.add("WatPremUs" ,df.format(WatPremUs));
					 texttag.add("ChReserveUs" ,df.format(ChReserveUs));
					 texttag.add("ExpenseUs" ,df.format(ExpenseUs));
					 texttag.add("ResultComUs" ,df.format(TotleUs));
			   }
        }	 
	     
	     if (RIcomCode.equals("R0004")){
        	 tTempleteID = "003153";  
        	  if(tSSRSH.getMaxRow()!=0){
				    Double WatPrem = Double.parseDouble(tSSRSH.GetText(2, 3))-Double.parseDouble(tSSRSH.GetText(1, 3));//A
				    Double ChReserve = Double.parseDouble(tSSRSH.GetText(10, 3))-Double.parseDouble(tSSRSH.GetText(5, 3));//B
				    Double Expense = WatPrem*Double.parseDouble(tSSRSH.GetText(11, 6));//C
				    Double Totle = Double.parseDouble(tSSRSH.GetText(1, 4))*Double.parseDouble(tSSRSH.GetText(7, 3));
					 texttag.add("WatPrem" ,df.format(WatPrem));
					 texttag.add("ChReserve" ,df.format(ChReserve));
					 texttag.add("Expense" ,df.format(Expense));
					 texttag.add("Result" ,tSSRSU.GetText(1, 5));
					 texttag.add("ResultCom" ,df.format(Totle));
			   }
			   if(tSSRSU.getMaxRow()!=0){	   
				   Double WatPrem = Double.parseDouble(tSSRSU.GetText(2, 3))-Double.parseDouble(tSSRSU.GetText(1, 3));//A
				    Double ChReserve = Double.parseDouble(tSSRSU.GetText(10, 3))-Double.parseDouble(tSSRSU.GetText(5, 3));//B
				    Double Expense = WatPrem*Double.parseDouble(tSSRSU.GetText(11, 6));//C
				    Double Totle = Double.parseDouble(tSSRSU.GetText(1, 4))*Double.parseDouble(tSSRSU.GetText(7, 3));
					 texttag.add("WatPremUs" ,df.format(WatPrem));
					 texttag.add("ChReserveUs" ,df.format(ChReserve));
					 texttag.add("ExpenseUs", df.format(Expense));
					 texttag.add("ResultUs" ,tSSRSU.GetText(1, 5));
					 texttag.add("ResultComUs" ,df.format(Totle));
			   }
        }	 
		//h
		if (RIcomCode.equals("R0005")){
	    	 tTempleteID = "003156";  
			   if(tSSRSH.getMaxRow()!=0){
				   Double WatPrem = Double.parseDouble(tSSRSH.GetText(2, 3))-Double.parseDouble(tSSRSH.GetText(1, 3));//A
				    Double Expense = WatPrem*Double.parseDouble(tSSRSH.GetText(12, 6));//C
				    Double Totle = Double.parseDouble(tSSRSH.GetText(1, 4))*Double.parseDouble(tSSRSH.GetText(8, 3));
					 texttag.add("YRate" ,df.format(Expense));
					 texttag.add("Totle" ,df.format(Totle));
						texttag.add("WatPrem" ,df.format(WatPrem));
						texttag.add("FProRate" ,tSSRSH.GetText(8, 2));
						texttag.add("FStratExpReserve",tSSRSH.GetText(11 ,2));
						texttag.add("FStratClaimReserve",tSSRSH.GetText(10, 2));
						texttag.add("FReturnPay" ,tSSRSH.GetText(9, 2));
						texttag.add("FLastYearLoss" ,tSSRSH.GetText(7, 2));
						
			   }
			   if(tSSRSU.getMaxRow()!=0){	
				   Double WatPrem = Double.parseDouble(tSSRSU.GetText(2, 3))-Double.parseDouble(tSSRSU.GetText(1, 3));//A
				    Double Expense = WatPrem*Double.parseDouble(tSSRSU.GetText(12, 6));//C
				    Double TotleUs = Double.parseDouble(tSSRSU.GetText(1, 4))*Double.parseDouble(tSSRSU.GetText(8, 3));
				    texttag.add("TotleUs" ,df.format(TotleUs));
				    texttag.add("YRateUs" ,df.format(Expense));
					texttag.add("WatPremUs" ,df.format(WatPrem));
					texttag.add("FProRateUs" ,tSSRSU.GetText(8, 2));
					texttag.add("FStratExpReserveUs",tSSRSU.GetText(11 ,2));
					texttag.add("FStratClaimReserveUs",tSSRSU.GetText(10, 2));
					texttag.add("FReturnPayUs" ,tSSRSU.GetText(9, 2));
					texttag.add("FLastYearLossUs" ,tSSRSU.GetText(7, 2));
			   }
        }	//TRAN
		if (RIcomCode.equals("R0007")){
	    	 tTempleteID = "003161";  
	   	  if(tSSRSH.getMaxRow()!=0){
	   		Double YRate = Double.parseDouble(tSSRSH.GetText(3, 3))*Double.parseDouble(tSSRSH.GetText(12, 3));
		    Double Totle = Double.parseDouble(tSSRSH.GetText(1, 4))*Double.parseDouble(tSSRSH.GetText(8, 3));
	    	 texttag.add("ProRate" ,tSSRSH.GetText(8, 2));
				texttag.add("StratExpReserve",tSSRSH.GetText(11, 2));
				texttag.add("StratClaimReserve",tSSRSH.GetText(10, 2));
				texttag.add("CessPrem",tSSRSH.GetText(3, 2));
				texttag.add("ClaimPay" ,tSSRSH.GetText(4, 2));
				texttag.add("EndExpReserve" ,tSSRSH.GetText(6, 2));
				texttag.add("EndClaimReserve" ,tSSRSH.GetText(5, 2));
				texttag.add("ReturnPay" ,tSSRSH.GetText(9, 2));
				texttag.add("CessComm" ,tSSRSH.GetText(2, 2));
				texttag.add("LastYearLoss" ,tSSRSH.GetText(7, 2));
				texttag.add("ProLosAmnt" ,tSSRSH.GetText(1, 5));
				texttag.add("BusinessTax" ,tSSRSH.GetText(1, 2));
				texttag.add("Result" ,df.format(YRate));
				texttag.add("TotalResult" ,df.format(Totle));
				
	   	  }
	   	  
		  if(tSSRSU.getMaxRow()!=0){
				Double YRateUs = Double.parseDouble(tSSRSU.GetText(3, 3))*Double.parseDouble(tSSRSU.GetText(12, 3));
			    Double TotleUs = Double.parseDouble(tSSRSU.GetText(1, 4))*Double.parseDouble(tSSRSU.GetText(8, 3));
			  texttag.add("StratExpReserveUs",tSSRSU.GetText(11, 2));
				texttag.add("StratClaimReserveUs" ,tSSRSU.GetText(10, 2));
				texttag.add("CessPremUs" ,tSSRSU.GetText(3, 2));
				texttag.add("ClaimPayUs" ,tSSRSU.GetText(4, 2));
				texttag.add("ReturnPayUs" ,tSSRSH.GetText(9, 2));
				texttag.add("EndExpReserveUs" ,tSSRSU.GetText(6, 2));
				texttag.add("EndClaimReserveUs" ,tSSRSU.GetText(5, 2));
				texttag.add("CessCommUs" ,tSSRSU.GetText(2, 2));
				texttag.add("LastYearLossUs" ,tSSRSU.GetText(7, 2));
				texttag.add("ProLosAmntUs" ,tSSRSU.GetText(1, 5));
				texttag.add("BusinessTax" ,tSSRSH.GetText(1, 2));
				texttag.add("ResultUs" ,df.format(YRateUs));
				texttag.add("TotalResultUs" ,df.format(TotleUs));
		 
		  }
       }

		if (RIcomCode.equals("R0006"))
		{
	    	 tTempleteID = "003185"; 
	    	 StringBuffer sqlRGA = new StringBuffer();
	    	 StringBuffer sqlRGA_US = new StringBuffer();
		    	 sqlRGA.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt, To_char( r.Prolosamnt,'"+fm+"')THP, (1 - b.Factorvalue),b.Factorcode, b.Currency,b.Recomcode	");
		    	 sqlRGA.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno and r.State='03'   and b.Currency ='13' ");
		    	 sqlRGA.append(" and  b.Riprofitno = 'Y01010060' ");
		    	 sqlRGA.append(" and  b.Calyear = '"+CalYear+"'  ");
		    	 sqlRGA.append(" order by  b.Riprofitno ");
    	 		 Double TotalER1 = 0.0;
    	 		 Double TotalER2 = 0.0;
 				 Double TotalER3 = 0.0;
    	 		 Double TotalERUs1 = 0.0;
    	 		 Double TotalERUs2 = 0.0;
	 			 Double TotalERUs3 = 0.0;
		    	 sqlRGA_US.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt, To_char( r.Prolosamnt,'"+fm+"') THP, (1 - b.Factorvalue),b.Factorcode, b.Currency,b.Recomcode	");
		    	 sqlRGA_US.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno  and r.State='03'   and b.Currency ='14' ");
		    	 sqlRGA_US.append(" and  b.Riprofitno = 'Y01010060'");
		    	 sqlRGA_US.append(" and  b.Calyear = '"+CalYear+"'  ");
		    	 sqlRGA_US.append(" order by  b.Riprofitno ");
	    	 tSSRSU = new ExeSQL().execSQL(sqlRGA_US.toString());
	    	 tSSRSTWO = new ExeSQL().execSQL(sqlRGA.toString());
	    	 texttag.add("CalYear",CalYear);
	    	 if(tSSRSTWO.MaxRow!=0){
	    		 texttag.add("CessPrem",tSSRSTWO.GetText(1, 2));
	    		 texttag.add("CessComm",tSSRSTWO.GetText(2, 2));
	    		 texttag.add("ReCessPrem",tSSRSTWO.GetText(7, 2));
	    		 Double ResultReserve = Double.parseDouble(tSSRSTWO.GetText(12, 3))-Double.parseDouble(tSSRSTWO.GetText(4, 3));
	    		 texttag.add("ResultReserve",df.format(ResultReserve));
	    		 Double TotalPrem = Double.parseDouble(tSSRSTWO.GetText(1, 3))-Double.parseDouble(tSSRSTWO.GetText(2, 3))-Double.parseDouble(tSSRSTWO.GetText(7, 3))-ResultReserve;
	    		 texttag.add("TotalPrem",df.format(TotalPrem));
	    		 texttag.add("StratExpReserve",tSSRSTWO.GetText(12, 2));
	    		 texttag.add("EndExpReserve",tSSRSTWO.GetText(4, 2));
	    		 texttag.add("StartChangeIBNR",tSSRSTWO.GetText(11, 2));
	    		 texttag.add("EndChangeIBNR",tSSRSTWO.GetText(3, 2));
	    		 Double ResultIBNR = Double.parseDouble(tSSRSTWO.GetText(11, 3))-Double.parseDouble(tSSRSTWO.GetText(3, 3));
	    		 texttag.add("ResultIBNR",df.format(ResultIBNR));
	    		 Double ReiExpenses = TotalPrem*0.01;
	    		 texttag.add("ReiExpenses",df.format(ReiExpenses));
	    		 Double Riskcharge = TotalPrem*0.05;
	    		 texttag.add("Riskcharge",df.format(Riskcharge));
	    		 Double ResultProfit = TotalPrem - ReiExpenses - Riskcharge;
	    		 texttag.add("ResultProfit",df.format(ResultProfit));
	    		 texttag.add("ProRate",tSSRSTWO.GetText(6, 2));
	    		 Double TotalRefund = ResultProfit*Double.parseDouble(tSSRSTWO.GetText(6, 3));
	    		 TotalER1 = TotalRefund;
	    		 texttag.add("TotalRefund",df.format(TotalRefund));
	    	 }
	    	 if(tSSRSU.MaxRow!=0){
	    		 texttag.add("CessPremUs",tSSRSU.GetText(1, 2));
	    		 texttag.add("CessCommUs",tSSRSU.GetText(2, 2));
	    		 texttag.add("ReCessPremUs",tSSRSU.GetText(7, 2));
	    		 Double ResultReserve = Double.parseDouble(tSSRSU.GetText(12, 3))-Double.parseDouble(tSSRSU.GetText(4, 3));
	    		 texttag.add("ResultReserveUs",df.format(ResultReserve));
	    		 Double TotalPrem = Double.parseDouble(tSSRSU.GetText(1, 3))-Double.parseDouble(tSSRSU.GetText(2, 3))-Double.parseDouble(tSSRSU.GetText(7, 3))-ResultReserve;
	    		 texttag.add("TotalPremUs",df.format(TotalPrem));
	    		 texttag.add("StratExpReserveUs",tSSRSU.GetText(12, 2));
	    		 texttag.add("EndExpReserveUs",tSSRSU.GetText(4, 2));
	    		 texttag.add("StartChangeIBNRUs",tSSRSU.GetText(11, 2));
	    		 texttag.add("EndChangeIBNRUs",tSSRSU.GetText(3, 2));
	    		 Double ResultIBNR = Double.parseDouble(tSSRSU.GetText(11, 3))-Double.parseDouble(tSSRSU.GetText(3, 3));
	    		 texttag.add("ResultIBNRUs",df.format(ResultIBNR));
	    		 Double ReiExpenses = TotalPrem*0.01;
	    		 texttag.add("ReiExpensesUs",df.format(ReiExpenses));
	    		 Double Riskcharge = TotalPrem*0.05;
	    		 texttag.add("RiskchargeUs",df.format(Riskcharge));
	    		 Double ResultProfit = TotalPrem - ReiExpenses - Riskcharge;
	    		 texttag.add("ResultProfitUs",df.format(ResultProfit));
	    		 texttag.add("ProRateUs",tSSRSU.GetText(6, 2));
	    		 Double TotalRefund = ResultProfit*Double.parseDouble(tSSRSU.GetText(6, 3));
	    		 TotalERUs1 = TotalRefund;
	    		 texttag.add("TotalRefundUs",df.format(TotalRefund));
	    	 }
	    	 
	    	 sqlRGA = new StringBuffer();
	    	 sqlRGA_US = new StringBuffer();
	    	 sqlRGA.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt, To_char( r.Prolosamnt,'"+fm+"') THP, (1 - b.Factorvalue),b.Factorcode, b.Currency,b.Recomcode	");
	    	 sqlRGA.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno  and r.State='03'   and b.Currency ='13' ");
	    	 sqlRGA.append(" and  b.Riprofitno = 'Y01010070' ");
	    	 sqlRGA.append(" and  b.Calyear = '"+CalYear+"'  ");
	    	 sqlRGA.append(" order by  b.Riprofitno ");
    	 
	    	 sqlRGA_US.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt, To_char( r.Prolosamnt,'"+fm+"') THP, (1 - b.Factorvalue),b.Factorcode, b.Currency,b.Recomcode	");
	    	 sqlRGA_US.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno   and r.State='03'  and b.Currency ='14' ");
	    	 sqlRGA_US.append(" and  b.Riprofitno = 'Y01010070'");
	    	 sqlRGA_US.append(" and  b.Calyear = '"+CalYear+"'  ");
	    	 sqlRGA_US.append(" order by  b.Riprofitno ");
    	 tSSRSU = new ExeSQL().execSQL(sqlRGA_US.toString());
    	 tSSRSTWO = new ExeSQL().execSQL(sqlRGA.toString());
    	 if(tSSRSTWO.MaxRow!=0){
    		 texttag.add("CessPrem1",tSSRSTWO.GetText(1, 2));
    		 texttag.add("CessComm1",tSSRSTWO.GetText(2, 2));
    		 texttag.add("ReCessPrem1",tSSRSTWO.GetText(7, 2));
    		 Double ResultReserve = Double.parseDouble(tSSRSTWO.GetText(12, 3))-Double.parseDouble(tSSRSTWO.GetText(4, 3));
    		 texttag.add("ResultReserve1",df.format(ResultReserve));
    		 Double TotalPrem = Double.parseDouble(tSSRSTWO.GetText(1, 3))-Double.parseDouble(tSSRSTWO.GetText(2, 3))-Double.parseDouble(tSSRSTWO.GetText(7, 3))-ResultReserve;
    		 texttag.add("TotalPrem1",df.format(TotalPrem));
    		 texttag.add("StratExpReserve1",tSSRSTWO.GetText(12, 2));
    		 texttag.add("EndExpReserve1",tSSRSTWO.GetText(4, 2));
    		 texttag.add("StartChangeIBNR1",tSSRSTWO.GetText(11, 2));
    		 texttag.add("EndChangeIBNR1",tSSRSTWO.GetText(3, 2));
    		 Double ResultIBNR = Double.parseDouble(tSSRSTWO.GetText(11, 3))-Double.parseDouble(tSSRSTWO.GetText(3, 3));
    		 texttag.add("ResultIBNR1",df.format(ResultIBNR));
    		 Double ReiExpenses = TotalPrem*0.01;
    		 texttag.add("ReiExpenses1",df.format(ReiExpenses));
    		 Double Riskcharge = TotalPrem*0.05;
    		 texttag.add("Riskcharge1",df.format(Riskcharge));
    		 Double ResultProfit = TotalPrem - ReiExpenses - Riskcharge;
    		 texttag.add("ResultProfit1",df.format(ResultProfit));
    		 texttag.add("ProRate1",tSSRSTWO.GetText(6, 2));
    		 Double TotalRefund = ResultProfit*Double.parseDouble(tSSRSTWO.GetText(6, 3));
    		 TotalER2 = TotalRefund;
    		 texttag.add("TotalRefund1",df.format(TotalRefund));
    	 }
    	 if(tSSRSU.MaxRow!=0){
    		 texttag.add("CessPremUs1",tSSRSU.GetText(1, 2));
    		 texttag.add("CessCommUs1",tSSRSU.GetText(2, 2));
    		 texttag.add("ReCessPremUs1",tSSRSU.GetText(7, 2));
    		 Double ResultReserve = Double.parseDouble(tSSRSU.GetText(12, 3))-Double.parseDouble(tSSRSU.GetText(4, 3));
    		 texttag.add("ResultReserveUs1",df.format(ResultReserve));
    		 Double TotalPrem = Double.parseDouble(tSSRSU.GetText(1, 3))-Double.parseDouble(tSSRSU.GetText(2, 3))-Double.parseDouble(tSSRSU.GetText(7, 3))-ResultReserve;
    		 texttag.add("TotalPremUs1",TotalPrem);
    		 texttag.add("StratExpReserveUs1",tSSRSU.GetText(12, 2));
    		 texttag.add("EndExpReserveUs1",tSSRSU.GetText(4, 2));
    		 texttag.add("StartChangeIBNRUs1",tSSRSU.GetText(11, 2));
    		 texttag.add("EndChangeIBNRUs1",tSSRSU.GetText(3, 2));
    		 Double ResultIBNR = Double.parseDouble(tSSRSU.GetText(11, 3))-Double.parseDouble(tSSRSU.GetText(3,3));
    		 texttag.add("ResultIBNRUs1",df.format(ResultIBNR));
    		 Double ReiExpenses = TotalPrem*0.01;
    		 texttag.add("ReiExpensesUs1",df.format(ReiExpenses));
    		 Double Riskcharge = TotalPrem*0.05;
    		 texttag.add("RiskchargeUs1",df.format(Riskcharge));
    		 Double ResultProfit = TotalPrem - ReiExpenses - Riskcharge;
    		 texttag.add("ResultProfitUs1",df.format(ResultProfit));
    		 texttag.add("ProRateUs1",tSSRSU.GetText(6, 2));
    		 Double TotalRefund = ResultProfit*Double.parseDouble(tSSRSU.GetText(6, 3));
    		 TotalERUs2 = TotalRefund;
    		 texttag.add("TotalRefundUs1",df.format(TotalRefund));
    	 }
    	 sqlRGA = new StringBuffer();
    	 sqlRGA_US = new StringBuffer();
    	 sqlRGA.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt, To_char( r.Prolosamnt,'"+fm+"') THP, (1 - b.Factorvalue),b.Factorcode, b.Currency,b.Recomcode	");
    	 sqlRGA.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno  and r.State='03'  and b.Currency ='13' ");
    	 sqlRGA.append(" and  b.Riprofitno = 'Y01010080' ");
    	 sqlRGA.append(" and  b.Calyear = '"+CalYear+"'  ");
    	 sqlRGA.append(" order by  b.Riprofitno ");
	 
    	 sqlRGA_US.append("SELECT b.Factorname,  To_char( b.Factorvalue,'"+fm+"') THF,b.Factorvalue, r.Prolosamnt, To_char( r.Prolosamnt,'"+fm+"') THP, (1 - b.Factorvalue),b.Factorcode, b.Currency,b.Recomcode	");
    	 sqlRGA_US.append(" FROM Riprolossvalue b ,Riprolossresult r  where  b.Riprofitno = r.Riprofitno and b.Batchno= r.Batchno  and r.State='03'  and b.Currency ='14' ");
    	 sqlRGA_US.append(" and  b.Riprofitno = 'Y01010080'");
    	 sqlRGA_US.append(" and  b.Calyear = '"+CalYear+"'  ");
    	 sqlRGA_US.append(" order by  b.Riprofitno ");
	 tSSRSU = new ExeSQL().execSQL(sqlRGA_US.toString());
	 tSSRSTWO = new ExeSQL().execSQL(sqlRGA.toString());
	 if(tSSRSTWO.MaxRow!=0){
		 texttag.add("CessPrem2",tSSRSTWO.GetText(1, 2));
		 texttag.add("CessComm2",tSSRSTWO.GetText(2, 2));
		 texttag.add("ReCessPrem2",tSSRSTWO.GetText(7, 2));
		 Double ResultReserve = Double.parseDouble(tSSRSTWO.GetText(12, 3))-Double.parseDouble(tSSRSTWO.GetText(4, 3));
		 texttag.add("ResultReserve2",df.format(ResultReserve));
		 Double TotalPrem = Double.parseDouble(tSSRSTWO.GetText(1, 3))-Double.parseDouble(tSSRSTWO.GetText(2, 3))-Double.parseDouble(tSSRSTWO.GetText(7, 3))-ResultReserve;
		 texttag.add("TotalPrem2",df.format(TotalPrem));
		 texttag.add("StratExpReserve2",tSSRSTWO.GetText(12, 2));
		 texttag.add("EndExpReserve2",tSSRSTWO.GetText(4, 2));
		 texttag.add("StartChangeIBNR2",tSSRSTWO.GetText(11, 2));
		 texttag.add("EndChangeIBNR2",tSSRSTWO.GetText(3, 2));
		 Double ResultIBNR = Double.parseDouble(tSSRSTWO.GetText(11, 3))-Double.parseDouble(tSSRSTWO.GetText(3, 3));
		 texttag.add("ResultIBNR2",df.format(ResultIBNR));
		 Double ReiExpenses = TotalPrem*0.01;
		 texttag.add("ReiExpenses2",df.format(ReiExpenses));
		 Double Riskcharge = TotalPrem*0.05;
		 texttag.add("Riskcharge2",df.format(Riskcharge));
		 Double ResultProfit = TotalPrem - ReiExpenses - Riskcharge;
		 texttag.add("ResultProfit2",df.format(ResultProfit));
		 texttag.add("ProRate2",tSSRSTWO.GetText(6, 2));
		 Double TotalRefund = ResultProfit*Double.parseDouble(tSSRSTWO.GetText(6, 3));
		 TotalER3 = TotalRefund;
		 texttag.add("TotalRefund2",df.format(TotalRefund));
	 }
	 if(tSSRSU.MaxRow!=0){
		 texttag.add("CessPremUs2",tSSRSU.GetText(1, 2));
		 texttag.add("CessCommUs2",tSSRSU.GetText(2, 2));
		 texttag.add("ReCessPremUs2",tSSRSU.GetText(7, 2));
		 Double ResultReserve = Double.parseDouble(tSSRSU.GetText(12, 3))-Double.parseDouble(tSSRSU.GetText(4, 3));
		 texttag.add("ResultReserveUs2",df.format(ResultReserve));
		 Double TotalPrem = Double.parseDouble(tSSRSU.GetText(1, 3))-Double.parseDouble(tSSRSU.GetText(2, 3))-Double.parseDouble(tSSRSU.GetText(7, 3))-ResultReserve;
		 texttag.add("TotalPremUs2",df.format(TotalPrem));
		 texttag.add("StratExpReserveUs2",tSSRSU.GetText(12, 2));
		 texttag.add("EndExpReserveUs2",tSSRSU.GetText(4, 2));
		 texttag.add("StartChangeIBNRUs2",tSSRSU.GetText(11, 2));
		 texttag.add("EndChangeIBNRUs2",tSSRSU.GetText(3, 2));
		 Double ResultIBNR = Double.parseDouble(tSSRSU.GetText(11, 3))-Double.parseDouble(tSSRSU.GetText(3, 3));
		 texttag.add("ResultIBNRUs2",df.format(ResultIBNR));
		 Double ReiExpenses = TotalPrem*0.01;
		 texttag.add("ReiExpensesUs2",df.format(ReiExpenses));
		 Double Riskcharge = TotalPrem*0.05;
		 texttag.add("RiskchargeUs2",df.format(Riskcharge));
		 Double ResultProfit = TotalPrem - ReiExpenses - Riskcharge;
		 texttag.add("ResultProfitUs2",df.format(ResultProfit));
		 texttag.add("ProRateUs2",tSSRSU.GetText(6, 2));
		 Double TotalRefund = ResultProfit*Double.parseDouble(tSSRSU.GetText(6, 3));
		 TotalERUs3 = TotalRefund;
		 texttag.add("TotalRefundUs2",df.format(TotalRefund));
	 }
	 Double TotalER = TotalER1+TotalER2+TotalER3;
	 Double TotalERUs = TotalERUs1+TotalERUs2+TotalERUs3;
	 texttag.add("TotalER",df.format(TotalER));
	 texttag.add("TotalERUs",df.format(TotalERUs));
      }	

         
	
  
tTransferData.setNameAndValue("ArrayList",tArr);
tTransferData.setNameAndValue("TempleteID",tTempleteID);
VData tVData=new VData();
tVData.addElement(tTransferData);

if(!tBusinessDelegate.submitData(tVData,"",tBusiName))
{
	Content=""+"ÕËµ¥²»´æÔÚ "+"";
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
  
</html>
<script type="text/javascript">
     myAlert("<%=Content%>");
     top.close();
  </script>