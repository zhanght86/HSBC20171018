<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LDUWUserSave.jsp
//�����ܣ�
//�������ڣ�2005-01-24 18:15:01
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  //<%@page import="com.sinosoft.lis.config.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  LDUWUserSchema tLDUWUserSchema   = new LDUWUserSchema();
  LDUWUserSchema tLDUWUserSchema1   = new LDUWUserSchema();
  //OLDEdorUserUI tOLDEdorUserUI   = new OLDEdorUserUI();
  //BusinessService tOLDEdorUserUI = new OLDEdorUserUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  String busiName="configOLDEdorUserUI";
  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";

  
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
	
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
   tLDUWUserSchema.setUserCode(request.getParameter("UserCode"));
    tLDUWUserSchema.setUWType("3");
    tLDUWUserSchema.setUWPopedom(request.getParameter("UWPopedom"));
    tLDUWUserSchema.setUpUWPopedom(request.getParameter("UpUwPopedom"));
    tLDUWUserSchema.setEdorPopedom("1");
      if(transact.equals("UPDATE||MAIN"))
    {
    	tLDUWUserSchema1.setUserCode(request.getParameter("UserCode1"));
    	tLDUWUserSchema1.setUWType(request.getParameter("UWType1"));
    	tLDUWUserSchema1.setUWPopedom(request.getParameter("UWPopedom1"));   
    }    
    
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	tVData.add(tLDUWUserSchema);
	if(transact.equals("UPDATE||MAIN"))
	{
	  tVData.add(tLDUWUserSchema1);
	  loggerDebug("LDEdorUserSave","UPDATE MAIN " + tLDUWUserSchema1.getUserCode());
	}
  	tVData.add(tG);
  	  loggerDebug("LDEdorUserSave","������:"+transact);
    tBusinessDelegate.submitData(tVData,transact,busiName);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
