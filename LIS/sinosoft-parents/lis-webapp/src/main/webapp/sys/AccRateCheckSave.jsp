<%
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
    <%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //������Ϣ������У�鴦��
  //�������
  LMInsuAccRateSchema tLMInsuAccRateSchema   = new LMInsuAccRateSchema();
  LMAccGuratRateSchema tLMAccGuratRateSchema   = new LMAccGuratRateSchema();

  AccRateBL tAccRateBL = new AccRateBL();

  //�������
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼

  
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String mCurrentTime = PubFun.getCurrentTime();
  String mCurrentDate = PubFun.getCurrentDate();
  VData tVData = new VData();
  
  String tChecks[] = request.getParameterValues("InpLMInsuAccRateGridChk"); 
  String tFlag=request.getParameter("AccType"); //����Ǳ�֤���ʻ��ǽ�������
  loggerDebug("AccRateCheckSave","��֤���ʻ��ǽ�������(0/1):"+ tFlag);
  String transact=request.getParameter("transact");
  loggerDebug("AccRateCheckSave","ִ�� "+ transact + "����");
  GlobalInput tG = new GlobalInput();      
	tG = (GlobalInput)session.getValue("GI");

  
	
  tLMInsuAccRateSchema.setRiskCode(request.getParameter("RiskCode"));
  tLMInsuAccRateSchema.setBalaDate(request.getParameter("BalaDate"));
  tLMInsuAccRateSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
  tLMInsuAccRateSchema.setSRateDate(request.getParameter("SRateDate"));
  tLMInsuAccRateSchema.setARateDate(request.getParameter("ARateDate"));
  tLMInsuAccRateSchema.setRate(request.getParameter("Rate"));
  tLMInsuAccRateSchema.setRateIntv("Y");
  tLMInsuAccRateSchema.setRateState("1"); 
  tLMInsuAccRateSchema.setMakeDate(mCurrentDate);
  tLMInsuAccRateSchema.setMakeTime(mCurrentTime);


  tLMAccGuratRateSchema.setRiskCode(request.getParameter("RiskCode"));
  tLMAccGuratRateSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
  tLMAccGuratRateSchema.setRate(request.getParameter("GruRate"));
  tLMAccGuratRateSchema.setRateStartDate(request.getParameter("RateStartDate"));
  tLMAccGuratRateSchema.setRateEndDate(request.getParameter("RateEndDate"));
  tLMAccGuratRateSchema.setRateIntv("Y");
  tLMAccGuratRateSchema.setRateState("1"); 
  tLMAccGuratRateSchema.setMakeDate(mCurrentDate);
  tLMAccGuratRateSchema.setMakeTime(mCurrentTime);
  
  
  if("1".equals(tFlag)) //�������ʴ���
  {
//    if("delete".equals(transact)) //ɾ����¼
//    {
//  	  for(int nIndex = 0; nIndex < tChecks.length; nIndex++ ) 
//	  {
//	      // If this line isn't selected, continue
//	     if( !tChecks[nIndex].equals("1") ) 
//	     {
//	        continue;
//	     }
//
//	 }	
//    }
    
	  try
	  {
		   tVData.add(tLMInsuAccRateSchema);	 
		   tVData.add(tG);  
	    
	   //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	   tAccRateBL.submitData(tVData,transact);
	  }
	  catch(Exception ex)
	  {
	    Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  }
  }
  if("0".equals(tFlag)) //��֤���ʴ���
  {
//	    if("delete".equals(transact))  //ɾ����¼
//	    {
//	  	  for(int nIndex = 0; nIndex < tChecks.length; nIndex++ ) 
//		  {
//		      // If this line isn't selected, continue
//		     if( !tChecks[nIndex].equals("1") ) 
//		     {
//		        continue;
//		     }
//
//		 }	
//	    }
	  try
	  {
		   tVData.add(tLMAccGuratRateSchema);
		   tVData.add(tG);  
	   //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	   tAccRateBL.submitData(tVData,transact);
	  }
	  catch(Exception ex)
	  {
	    Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  } 

  }
  


  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tAccRateBL.mErrors;
    if (!tError.needDealError())
    {                          
      Content = " ����ɹ�";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

