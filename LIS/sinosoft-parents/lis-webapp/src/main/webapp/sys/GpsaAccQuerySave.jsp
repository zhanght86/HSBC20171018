<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorApproveSave.jsp
//程序功能：保全复核
//创建日期：2005-05-08 15:59:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%

	String FlagStr = "Fail";
	String Content = "";
	
    double tdAccBala = 0;
    double tdAccInterest = 0;    
        
  	//输出参数
    loggerDebug("GpsaAccQuerySave","----------------------GpsaAccQuerySave.jsp帐户价值查询----开始----------------------");
        
	try
	{        
        String tPolNo = request.getParameter("PolNo");
        String tDate = PubFun.getCurrentDate();
        String tEDate = PubFun.calDate(tDate,-1,"D",tDate);

        loggerDebug("GpsaAccQuerySave","--计算时间:"+tEDate);
        loggerDebug("GpsaAccQuerySave","--计算的保单号"+tPolNo); 
                
  		EdorCalZT tEdorCalZT = new EdorCalZT(new GlobalInput());

        TransferData tTransferData2 = tEdorCalZT.getPolBalance(tPolNo, tEDate);


        Double tDAccBala = (Double)tTransferData2.getValueByName("AccBala");
        Double tDAccInterest = (Double)tTransferData2.getValueByName("AccInterest");
       
        tdAccBala = Arith.round(tDAccBala.doubleValue(),2);
        tdAccInterest = Arith.round(tDAccInterest.doubleValue(),2);
           
        
        FlagStr = "Succ";
        Content = "帐户价值计算完毕";
        loggerDebug("GpsaAccQuerySave","--金额:"+tdAccBala+",利息"+tdAccInterest); 
	}
	catch(Exception e)
	{

		   e.printStackTrace();
	       Content = Content.trim()+".提示：帐户价值计算失败，异常终止!";
	}
	
	
    loggerDebug("GpsaAccQuerySave","----------------------GpsaAccQuerySave.jsp帐户价值查询----结束----------------------");        
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tdAccBala%>","<%=tdAccInterest%>");
</script>
</html>
   
   
   
 
