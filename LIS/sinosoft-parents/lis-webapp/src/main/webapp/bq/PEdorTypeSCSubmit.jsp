<%
//�������ƣ�PEdorTypeSCSubmit.jsp
//�����ܣ�
//�������ڣ�2007-12-10 16:49:22
//������  ��PST
//���¼�¼��  ������    ��������     ����ԭ��/����

%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %> 

<%

CErrors tError = null;
String tRela  = "";
String FlagStr = "";
String Content = "";
String transact = "";

GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");

transact = request.getParameter("fmtransact");
String edorNo = request.getParameter("EdorNo");
String edorType = request.getParameter("EdorType");
String contNo = request.getParameter("ContNo");   
String polNo = request.getParameter("PolNo");
String edoracceptNo = request.getParameter("EdorAcceptNo");
String tSpecContent=request.getParameter("Speccontent");
String grpContNo = request.getParameter("GrpContNo");
String appObj = request.getParameter("AppObj");

String tSpecType = request.getParameter("SpecType");

// ׼���������� VData
LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
tLPEdorItemSchema.setEdorNo(edorNo);
tLPEdorItemSchema.setContNo(contNo);
tLPEdorItemSchema.setPolNo(polNo);
tLPEdorItemSchema.setEdorAcceptNo(edoracceptNo);
tLPEdorItemSchema.setEdorType(edorType);

String tChk[] = request.getParameterValues("InpLCCSpecGridSel"); 
//String tPolNo[] = request.getParameterValues("LCCSpecGrid2");
String tSNNo[] = request.getParameterValues("LCCSpecGrid5");  
LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();   
if(tChk!=null&&("modify".equals(transact) || "delete".equals(transact)))
{         
for(int index=0;index<tChk.length;index++)
{
      if(tChk[index].equals("1"))
       {       
           //�޸���Լ or ɾ����Լ
    	  tLPCSpecSchema.setContNo(contNo);
    	  tLPCSpecSchema.setEdorNo(edorNo);
    	  tLPCSpecSchema.setEdorType(edorType);
    	  tLPCSpecSchema.setSpecContent(tSpecContent);
    	  tLPCSpecSchema.setSerialNo(tSNNo[index]);
    	  
       }
}  
}else
{         //������Լ
    	  tLPCSpecSchema.setContNo(contNo);
    	  tLPCSpecSchema.setEdorNo(edorNo);
    	  tLPCSpecSchema.setEdorType(edorType);
    	  tLPCSpecSchema.setSpecType(tSpecType);
    	  tLPCSpecSchema.setSpecContent(tSpecContent);
    	  tLPCSpecSchema.setGrpContNo("00000000000000000000");
}    




VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPEdorItemSchema);
tVData.add(tLPCSpecSchema);
//EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
System.out.println("--=="+  tSpecContent +"  ==--");
//if (!tEdorDetailUI.submitData(tVData, transact))
if (!tBusinessDelegate.submitData(tVData, transact ,busiName))
{
//       VData rVData = tEdorDetailUI.getResult();
       VData rVData = tBusinessDelegate.getResult();
//       Content = "����ʧ�ܣ�ԭ����:" + tEdorDetailUI.mErrors.getFirstError();
       Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError(); 
       FlagStr = "Fail";
 }
 else
 {
       Content = "����ɹ�";
       FlagStr = "Success";
  }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

