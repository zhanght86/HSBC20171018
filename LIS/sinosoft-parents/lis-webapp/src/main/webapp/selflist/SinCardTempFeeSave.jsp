 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�SinCardTempFeeSave.jsp
//�����ܣ�
//�������ڣ�2008-3-10
//������  ��zy
//���¼�¼��  ������    ��������     ����ԭ��/����

%>

<SCRIPT src="SinCardTempFeeInput.js">
	alert("SinCardTempFeeInput");
</SCRIPT>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.list.*"%>
<%
  loggerDebug("SinCardTempFeeSave"," save------"); 
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  if(tG==null)
  {
    loggerDebug("SinCardTempFeeSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  
  else
	{
	
			String Operator  = tG.Operator ;  //�����½����Ա�˺�
		 	 String ManageCom = tG.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
		     // ���ձ���Ϣ��¼
			String tStartNo        = request.getParameter("fmStartNo");                     //������ʼ��
			String tEndNo          = request.getParameter("fmEndNo");                      //������ֹ��
			String tPayDate    = request.getParameterValues("TempToGrid3")[0]; 	          //��������
			String tPayMoney      = request.getParameterValues("TempToGrid4")[0];         //���ѽ��        
		  String tEnterAccDate = request.getParameterValues("TempToGrid5")[0];
		  String tManageCom    = request.getParameterValues("TempToGrid6")[0]; 				         //���ѻ���
			String tRiskCode      = request.getParameterValues("TempToGrid7")[0];          //���ֱ���
		  String tAgentGroup   = request.getParameterValues("TempToGrid8")[0];
		  String tAgentCode    = request.getParameterValues("TempToGrid9")[0];
	    String tPayIntv      = request.getParameterValues("TempToGrid12")[0]; //�ɷѷ�ʽ
		 	String tPayYears     = request.getParameterValues("TempToGrid13")[0];	
	   
		  // ���շ�����¼
		           
			String tCPayMode       = request.getParameterValues("TempClassToGrid2")[0];
		  String tCPayDate       = request.getParameterValues("TempClassToGrid3")[0];
		  String tCPayMoney      = request.getParameterValues("TempClassToGrid4")[0];
		  String tCEnterAccDate  = request.getParameterValues("TempClassToGrid5")[0];
		  String tCManageCom     = request.getParameterValues("TempClassToGrid6")[0];
		
	  	String tCChequeNo      = request.getParameterValues("TempClassToGrid7")[0];
		  String tChequeDate     = request.getParameterValues("TempClassToGrid8")[0];
		  String tBankcode       = request.getParameterValues("TempClassToGrid9")[0];
		  String tBankaccno      = request.getParameterValues("TempClassToGrid10")[0];
		  String tAccname        = request.getParameterValues("TempClassToGrid11")[0];
		  String tIDType         = request.getParameterValues("TempClassToGrid18")[0];
		  String tIDNo           = request.getParameterValues("TempClassToGrid19")[0];

      //����ǰ̨����
			TransferData tTransferData =new TransferData();
			tTransferData.setNameAndValue("tStartNo",tStartNo);
			tTransferData.setNameAndValue("tEndNo",tEndNo);
			tTransferData.setNameAndValue("tPayDate",tPayDate);
			tTransferData.setNameAndValue("tPayMoney",tPayMoney);
			tTransferData.setNameAndValue("tEnterAccDate",tEnterAccDate);
			tTransferData.setNameAndValue("tManageCom",tManageCom);
			tTransferData.setNameAndValue("tRiskCode",tRiskCode);
			tTransferData.setNameAndValue("tAgentGroup",tAgentGroup);	
			tTransferData.setNameAndValue("tAgentCode",tAgentCode);		
			tTransferData.setNameAndValue("tPayIntv",tPayIntv);
			tTransferData.setNameAndValue("tPayYears",tPayYears);	
			
			tTransferData.setNameAndValue("tCPayMode",tCPayMode);
			tTransferData.setNameAndValue("tCPayDate",tCPayDate);
			tTransferData.setNameAndValue("tCPayMoney",tCPayMoney);
			tTransferData.setNameAndValue("tCEnterAccDate",tCEnterAccDate);
			tTransferData.setNameAndValue("tCManageCom",tCManageCom);		
			tTransferData.setNameAndValue("tCChequeNo",tCChequeNo);
			tTransferData.setNameAndValue("tChequeDate",tChequeDate);			
			tTransferData.setNameAndValue("tBankcode",tBankcode);
			tTransferData.setNameAndValue("tBankaccno",tBankaccno);
			tTransferData.setNameAndValue("tAccname",tAccname);
			tTransferData.setNameAndValue("tIDType",tIDType);
			tTransferData.setNameAndValue("tIDNo",tIDNo);

			
		  // ׼���������� VData
		  VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);
		    
		        // ���ݴ���
		  SinCardTempFeeUI tSinCardTempFeeUI   = new SinCardTempFeeUI();
		  try
		  {
			  loggerDebug("SinCardTempFeeSave"," ��ʼ�ύSinCardTempFeeUI������------");      
			  
				if(!tSinCardTempFeeUI.submitData(tVData,"INSERT"))
				{
				 
				    Content = " ���ѱ���ʧ�ܣ�ԭ��:" + tSinCardTempFeeUI.mErrors.getFirstError();
			     	FlagStr = "Fail"; 
				}   
			
				if (FlagStr == "Fail")
				{
				    tError = tSinCardTempFeeUI.mErrors;
				    if (!tError.needDealError())
				    {
				    	Content = "���ѱ���ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else
				    {
				    	FlagStr = "Fail";
				    }
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Content = Content.trim()+"��ʾ���쳣��ֹ!";
			}
		
	} //end of else

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

