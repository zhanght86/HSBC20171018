<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	
  System.out.println("111");
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");

  LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

  tLOPRTManagerSchema.setPrtSeq(request.getParameter("PrtSeq"));
  
  CErrors tErrors = new CErrors( );

  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  
	  VData tVData = new VData();
    VData mResult = new VData();

     tVData.addElement(tG);
     tVData.addElement(tLOPRTManagerSchema);

	   String strErrMsg = "";
	   boolean Flag=true;
	   String FlagStr = "";
     String Content = "";
     
		
     RePrintUI tRePrintUI = new RePrintUI();
     try
  {
    tRePrintUI.submitData(tVData,"CONFIRM");
  }
  catch(Exception ex)
  {
    Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tErrors = tRePrintUI.mErrors;
    if (!tErrors.needDealError())
    {                          
    	Content = " �����ύ�ɹ�! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	
    	Content = " �����ύʧ�ܣ����������Ƿ���ȷ";
    	FlagStr = "Fail";
    }
  }     
	   
        System.out.println("�������ύ");
        mResult = tRePrintUI.getResult();
        
  
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
