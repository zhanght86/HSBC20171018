<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorApproveSave.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 15:59:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%

	String FlagStr = "Fail";
	String Content = "";
	
    double tdAccBala = 0;
    double tdAccInterest = 0;    
        
  	//�������
    loggerDebug("GpsaAccQuerySave","----------------------GpsaAccQuerySave.jsp�ʻ���ֵ��ѯ----��ʼ----------------------");
        
	try
	{        
        String tPolNo = request.getParameter("PolNo");
        String tDate = PubFun.getCurrentDate();
        String tEDate = PubFun.calDate(tDate,-1,"D",tDate);

        loggerDebug("GpsaAccQuerySave","--����ʱ��:"+tEDate);
        loggerDebug("GpsaAccQuerySave","--����ı�����"+tPolNo); 
                
  		EdorCalZT tEdorCalZT = new EdorCalZT(new GlobalInput());

        TransferData tTransferData2 = tEdorCalZT.getPolBalance(tPolNo, tEDate);


        Double tDAccBala = (Double)tTransferData2.getValueByName("AccBala");
        Double tDAccInterest = (Double)tTransferData2.getValueByName("AccInterest");
       
        tdAccBala = Arith.round(tDAccBala.doubleValue(),2);
        tdAccInterest = Arith.round(tDAccInterest.doubleValue(),2);
           
        
        FlagStr = "Succ";
        Content = "�ʻ���ֵ�������";
        loggerDebug("GpsaAccQuerySave","--���:"+tdAccBala+",��Ϣ"+tdAccInterest); 
	}
	catch(Exception e)
	{

		   e.printStackTrace();
	       Content = Content.trim()+".��ʾ���ʻ���ֵ����ʧ�ܣ��쳣��ֹ!";
	}
	
	
    loggerDebug("GpsaAccQuerySave","----------------------GpsaAccQuerySave.jsp�ʻ���ֵ��ѯ----����----------------------");        
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tdAccBala%>","<%=tdAccInterest%>");
</script>
</html>
   
   
   
 
