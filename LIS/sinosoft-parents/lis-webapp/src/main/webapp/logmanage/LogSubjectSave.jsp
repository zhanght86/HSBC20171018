<%
//�������ƣ�LOAccUnitPriceSave.jsp
//�����ܣ�
//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String Transact = "";
  String Flag = "";

  String FlagStr = "";
  String Content = "";
  LogSubjectSchema tLogSubjectSchema   = new LogSubjectSchema();
  LogItemSchema tLogItemSchema = new LogItemSchema();

  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getAttribute("GI");
  Flag = request.getParameter("Flag");
  Transact = request.getParameter("Transact");
  //�������
  CErrors tError =new CErrors();
  VData tVData=new VData();
  TransferData tfd = new TransferData();
  tfd.setNameAndValue("Flag",Flag);

  String busiName = "LogComponentUI";
  BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	
  if(Flag != null && !"".equals(Flag)&&"LOG".equals(Flag)){
	  tLogSubjectSchema.setSubjectID(request.getParameter("LogSubjectID"));
	  tLogSubjectSchema.setSubjectDes(request.getParameter("LogSubjectDes"));
	  tLogSubjectSchema.setDept(request.getParameter("LogDept"));
	  tLogSubjectSchema.setServiceModule(request.getParameter("LogServiceModule"));
	  tLogSubjectSchema.setTaskCode(request.getParameter("TaskCode"));
	  tLogSubjectSchema.setTaskDescribe(request.getParameter("TaskCodeName"));
	  tLogSubjectSchema.setLogType(request.getParameter("LogType"));
	  tLogSubjectSchema.setSwitch(request.getParameter("Switch"));
	  tVData.add(tLogSubjectSchema);
  }else if(Flag != null && !"".equals(Flag)&&"ITEM".equals(Flag)){
	  tLogItemSchema.setItemID(request.getParameter("ItemID"));
	  tLogItemSchema.setItemDes(request.getParameter("ItemDes"));
	  tLogItemSchema.setGrammar(request.getParameter("Grammar"));
	  //tLogItemSchema.setClassName(request.getParameter("ClassName"));
	  //tLogItemSchema.setFunc(request.getParameter("Func"));
	  //tLogItemSchema.setKeyType(request.getParameter("KeyType"));
	  tLogItemSchema.setRemark(request.getParameter("Remark"));
	  //tLogItemSchema.setSwitch(request.getParameter("ItemSwitch"));
	  tVData.add(tLogItemSchema);
	  tfd.setNameAndValue("SubjectID",request.getParameter("SubIDForItem"));
  }else if(Flag != null && !"".equals(Flag)&&"DOMAIN".equals(Flag)){
	  //2011-11-17 modify
	  //
	  String tGrid1 [] = request.getParameterValues("LogItemGrid1"); //�õ���1�е�����ֵ
      String tGrid2 [] = request.getParameterValues("LogItemGrid2"); //�õ���2�е�����ֵ
      String tGrid4 [] = request.getParameterValues("LogItemGrid4"); //�õ���1�е�����ֵ
      String tGrid5 [] = request.getParameterValues("LogItemGrid5"); //�õ���2�е�����ֵ
	
      int gridCount = tGrid1.length;
      LogDomainToItemSet tLogDomainToItemSet = new LogDomainToItemSet();
      for(int i=0;i<gridCount;i++)
      {
    	  LogDomainToItemSchema tLogDomainToItemSchema = new LogDomainToItemSchema();
    	  tLogDomainToItemSchema.setSubjectID(tGrid1[i]);
    	  tLogDomainToItemSchema.setItemID(tGrid2[i]);
    	  tLogDomainToItemSchema.setKeyType(tGrid4[i]);
    	  tLogDomainToItemSchema.setSwitch(tGrid5[i]);
    	  
    	  tLogDomainToItemSet.add(tLogDomainToItemSchema);  
      }
     
	  //tLogDomainToItemSchema.setSubjectID(request.getParameter("SubIDForItem"));
	  //tLogDomainToItemSchema.setItemID(request.getParameter("ItemID"));
	  tVData.add(tLogDomainToItemSet);
  }
  
  tVData.add(tfd);
  tVData.add(tGI);
  if(Transact != null && !"".equals(Transact)){
	  //LogComponentUI tLogComponentUI = new LogComponentUI();
	  //if(!tLogComponentUI.submitData(tVData,Transact)){
     if (!tBusinessDelegate.submitData(tVData, Transact,busiName)){
		  FlagStr="fail";
	      Content ="����ʧ�ܣ�ԭ���ǣ�"+tBusinessDelegate.getCErrors().getFirstError();
	  }else{
	     FlagStr="Succ";
	     Content ="����ɹ�";
	  }
  }

  //��Ӹ���Ԥ����
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

