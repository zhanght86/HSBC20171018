<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�GEdorTypeMultiDetailSubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
    //������Ϣ������У�鴦��
    loggerDebug("GEdorTypeMultiRiskSubmit","-----Detail submit---");
                 
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result="";
    TransferData tTransferData = new TransferData(); 
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    
    LCGrpPolSet tLCGrpPolSet   = new LCGrpPolSet();
    LPGrpEdorItemSchema tLPGrpEdorItemSchema   = new LPGrpEdorItemSchema();
    
    tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
    tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    loggerDebug("GEdorTypeMultiRiskSubmit","transact"+transact);
    transact=(String)request.getParameter("Transact");
    
    loggerDebug("GEdorTypeMultiRiskSubmit","transact"+transact);
    
    if (transact.equals("INSERT||EDORRISK"))
    {
        String tGrpPolNo[] = request.getParameterValues("RiskGrid1");    //��ͬ�� 
        String tRiskCode[] = request.getParameterValues("RiskGrid2"); //�ͻ���    
        String tChk[] = request.getParameterValues("InpRiskGridChk");             

        loggerDebug("GEdorTypeMultiRiskSubmit","tChk.length"+tChk.length);
        for(int index=0;index<tChk.length;index++)
        {
            if(tChk[index].equals("1"))
            {
                LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                //�ŵ�������Ϣ
                tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[index]);
               
	            loggerDebug("GEdorTypeMultiRiskSubmit","tGrpPolNo"+tGrpPolNo[index]);
                tLCGrpPolSet.add(tLCGrpPolSchema);
            }           
        }
    }
    else
    {
        String tGrpPolNo[] = request.getParameterValues("Risk2Grid1");    //��ͬ�� 
        String tRiskCode[] = request.getParameterValues("Risk2Grid2"); //�ͻ���    
        String tChk[] = request.getParameterValues("InpRisk2GridChk");             

        loggerDebug("GEdorTypeMultiRiskSubmit","tChk.length"+tChk.length);
        for(int index=0;index<tChk.length;index++)
        {
            if(tChk[index].equals("1"))
            {
                LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                //�ŵ�������Ϣ
                tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[index]);
               
	            loggerDebug("GEdorTypeMultiRiskSubmit","tGrpPolNo"+tGrpPolNo[index]);
                tLCGrpPolSet.add(tLCGrpPolSchema);
            }           
        }
    }   	
  
  
  GEdorRiskUI tGEdorRiskUI   = new GEdorRiskUI();
  try {
    // ׼���������� VData
  	VData tVData = new VData();  
	 	tVData.addElement(tG);
	 	tVData.addElement(tLPGrpEdorItemSchema);
	 	tVData.addElement(tLCGrpPolSet);
	 	
	 	//������˱�����Ϣ(��ȫ)	
    tGEdorRiskUI.submitData(tVData, transact);
	}
	catch(Exception ex) {
		Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="") 
  {
    CErrors tError = new CErrors(); 
    tError = tGEdorRiskUI.mErrors;
    
    if (!tError.needDealError()) 
    {                          
      Content = " ����ɹ�";
    	FlagStr = "Success";
    	
    	if (transact.equals("QUERY||MAIN")||transact.equals("QUERY||DETAIL"))	
    	{
      	    if (tGEdorRiskUI.getResult()!=null&&tGEdorRiskUI.getResult().size()>0) 
      	    {
      	    	Result = (String)tGEdorRiskUI.getResult().get(0);
      	    	
      	    	if (Result==null||Result.trim().equals(""))	
      	    	{
      	    		FlagStr = "Fail";
      	    		Content = "�ύʧ��!!";
      	    	}
      	    }
    	}
    }
    else 
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

