<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLClaimClearAccSave.jsp
//�����ܣ�Ͷ������
//�������ڣ�2007-09-10
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
    if(tG == null) 
    {
        loggerDebug("LLClaimClearAccSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimClearAccSave","û�л�ȡ������!!!");
        return;   
    }
    
    
    
    //׼������������Ϣ
    String tClmNo = request.getParameter("RptNo");
             
    TransferData tTransferData = new TransferData();    
    tTransferData.setNameAndValue("ClmNo",tClmNo);      
        
    loggerDebug("LLClaimClearAccSave","LLClaimClearAccSave.jsp����--"+tClmNo); 
  
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

        String busiName="LLClaimClearAccUI";
        String mDescType="����";
      	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
      	  if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
      	  {    
      	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
      	       { 
      				Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
      				FlagStr = "FAIL";
      		   }
      		   else
      		   {
      				Content = mDescType+"ʧ��";
      				FlagStr = "FAIL";				
      		   }
      	  }
      	  else
      	  {
      	     	Content = mDescType+"�ɹ�! ";
      	      	FlagStr = "SUCC";  
      	  }
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterMatchDutyPay("<%=FlagStr%>","<%=Content%>");
</script>
</html>
