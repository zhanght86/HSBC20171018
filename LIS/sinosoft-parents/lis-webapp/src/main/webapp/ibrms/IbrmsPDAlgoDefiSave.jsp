<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�
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
  
  String tBtnFlag=request.getParameter("BtnFlag");
  String tID = request.getParameter("TempalteID");
   
  LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
  LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();    
  LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();  
    	
  tLRTemplateTSchema.setId(tID);
  tLRTemplateSchema.setId(tID);
  tLRTemplateBSchema.setId(tID);
			
  tLRTemplateTSchema.setState(tBtnFlag);
  tLRTemplateSchema.setState(tBtnFlag);
  tLRTemplateBSchema.setState(tBtnFlag);
  loggerDebug("IbrmsPDAlgoDefiSave","BtnFlag:================ "+tBtnFlag );
  loggerDebug("IbrmsPDAlgoDefiSave","TempalteID:"+tID);
  tLRTemplateTSet.add(tLRTemplateTSchema);
  tLRTemplateSet.add(tLRTemplateSchema);
  tLRTemplateBSet.add(tLRTemplateBSchema);

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
    loggerDebug("IbrmsPDAlgoDefiSave","submit tLRTemplateTUI ");
   
    tBusinessDelegate.submitData(tVData, tBtnFlag, "LRTemplateTUI");
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
    
    //request.getRequestDispatcher("RuleQuerySaveResponse.jsp?Content="+Content+"&flag="+FlagStr).forward(request,response);
%>
<html>
<script language="javascript">
	try
	{
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	}
	catch(e)
	{
		//alert(e);
		//top.afterSubmit("<%=FlagStr%>","<%=Content%>");
	}
</script>
</html>



