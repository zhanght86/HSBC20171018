 
  <%
//�������ƣ�TempFeeQureyResult.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  String mAction = request.getParameter("fmAction");
  loggerDebug("TempFeeQueryResult","1111111");
  CErrors tError = null;  
  String Content = "";
  String FlagStr = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
 
  String tManageCom  = request.getParameter("ManageCom");  
  String StartDate  = request.getParameter("StartDate"); 
  String EndDate   = request.getParameter("EndDate"); 
  String Operator   = request.getParameter("Operator"); 
  String AgentCode = request.getParameter("AgentCode"); 
  String TempFeeNo = request.getParameter("TempFeeNo");
  String PrtNo = request.getParameter("PrtNo");
  String tMaxMoney = request.getParameter("MaxMoney");
  String tMinMoney = request.getParameter("MinMoney");  
  String tEnterAccStartDate = request.getParameter("EnterAccStartDate");
  String tEnterAccEndDate = request.getParameter("EnterAccEndDate"); 
  
  //loggerDebug("TempFeeQueryResult","chequeno:"+ChequeNo);
  loggerDebug("TempFeeQueryResult","----------mAction:"+mAction);
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("MaxMoney",tMaxMoney);
  tTransferData.setNameAndValue("MinMoney",tMinMoney);
  tTransferData.setNameAndValue("ManageCom",tManageCom);
  tTransferData.setNameAndValue("EnterAccStartDate",tEnterAccStartDate);
  tTransferData.setNameAndValue("EnterAccEndDate",tEnterAccEndDate);
  tTransferData.setNameAndValue("StartDate",StartDate);
  tTransferData.setNameAndValue("EndDate",EndDate);
  

  
  if(mAction.equals("QUERY")){
    String TempFeeStatus   = request.getParameter("TempFeeStatus");
    String RiskCode = request.getParameter("RiskCode");
    tTransferData.setNameAndValue("TempFeeStatus",TempFeeStatus);
       
    int recordCount = 0;
    double  PayMoney = 0;;   //���ѽ��
    
    
    //�ݽ��ѱ�
    LJTempFeeSet     mLJTempFeeSet     ; 
    LJTempFeeSchema  tLJTempFeeSchema  ;  
    VData tVData = new VData();
    
    tVData.addElement(tG);
    tLJTempFeeSchema = new LJTempFeeSchema(); 
    tLJTempFeeSchema.setRiskCode(RiskCode); 
    tLJTempFeeSchema.setOperator(Operator); 
    tLJTempFeeSchema.setAgentCode(AgentCode); 
    tLJTempFeeSchema.setTempFeeNo(TempFeeNo); 
    if(PrtNo!=null&&!PrtNo.trim().equals("")){
      tLJTempFeeSchema.setOtherNo(PrtNo);
      //tLJTempFeeSchema.setOtherNoType("4");
    }
    
    tVData.add(tLJTempFeeSchema);
    //tVData.addElement(StartDate);
   // tVData.addElement(EndDate);
   // tVData.addElement(TempFeeStatus);
  	tVData.addElement(tTransferData);
  	
    //TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();


    if (!tBusinessDelegate.submitData(tVData,mAction,"TempFeeQueryUI"))
    {
      loggerDebug("TempFeeQueryResult","��ѯ�ݽ��ѱ�ʱ����û��������ݴ���");
      
      if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
      { 
			Content = "��ѯʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
	   }
	   else
	   {
			Content = "��ѯʧ��";
			FlagStr = "Fail";				
	   }
    }   
    else
    {   
    	Content = " ��ѯ�ɹ�";
        FlagStr = "Succ";
        
  	    tVData.clear();
        mLJTempFeeSet = new LJTempFeeSet();
	      tVData = tBusinessDelegate.getResult();
        mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0));             
       
       //�ݽ��ѱ�õ���¼���ݣ�
       //�õ����ϲ�ѯ�����ļ�¼����Ŀ������ѭ��ʱ�õ�
       recordCount=mLJTempFeeSet.size();
       if (recordCount>0)
       {
       String strRecord="0|"+recordCount+"^";
       strRecord=strRecord+mLJTempFeeSet.encode(); 
       loggerDebug("TempFeeQueryResult","strRecord:"+strRecord);   
%>
        <script language="javascript">
        //����js�ļ�����ʾ���ݵĺ���
        parent.fraInterface.showRecord("<%=strRecord%>"); 
        </script>                
<%

    	}
    }
   }
  
%>                                        
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>




