<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�BookAddSave.jsp
//�����ܣ�
//�������ڣ�2008-09-02 15:12:33
//������  ��dxy���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
  //������Ϣ������У�鴦��
  //�������   
 
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  CErrors tError = null; 
  //LRTemplateTUI tLRTemplateTUI  = new LRTemplateTUI();

  LRTemplateTSet tLRTemplateTSet = new LRTemplateTSet();
  LRTemplateSet tLRTemplateSet = new LRTemplateSet();
  LRTemplateBSet tLRTemplateBSet = new LRTemplateBSet();
  
  String BtnFlag=request.getParameter("BtnFlag");
  String id[] = request.getParameterValues("QueryGrpGrid9");
   
   String tRadio[] = request.getParameterValues("InpQueryGrpGridSel");  
   //������ʽ=�� Inp+MulLine������+Sel�� 
   
   int length=tRadio.length;
   for (int index=0;index<length;index++)
   {
    if(tRadio[index].equals("1")) 
    {
    	LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
    	LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();    
    	LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();  
    	
			tLRTemplateTSchema.setId(id[index]);
			tLRTemplateSchema.setId(id[index]);
			tLRTemplateBSchema.setId(id[index]);
			
			tLRTemplateTSchema.setState(BtnFlag);
			tLRTemplateSchema.setState(BtnFlag);
			tLRTemplateBSchema.setState(BtnFlag);
			loggerDebug("RuleQuerySave","BtnFlag:================ "+BtnFlag );
			loggerDebug("RuleQuerySave",id[index]);
		tLRTemplateTSet.add(tLRTemplateTSchema);
		tLRTemplateSet.add(tLRTemplateSchema);
		tLRTemplateBSet.add(tLRTemplateBSchema);
    }
}

  // ׼���������� VData  
 // LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(tLRTemplateTSet);
  tVData.add(tLRTemplateSet);
  tVData.add(tLRTemplateBSet);
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
    loggerDebug("RuleQuerySave","submit tLRTemplateTUI ");
   
    tBusinessDelegate.submitData(tVData, BtnFlag, "LRTemplateTUI");
    //tLRTemplateTUI.submitData(tVData,BtnFlag);
  }
   catch(Exception ex)
  {
    Content = "����ʧ��,ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {

    	Content = "����ɹ�! ";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = "����ʧ��,ԭ����" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  
    request.getRequestDispatcher("RuleQuerySaveResponse.jsp?Content="+Content+"&flag="+FlagStr).forward(request,response);


%>
<html>
<script language="javascript">

//		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>



