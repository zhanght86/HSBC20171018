<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CancleGivenChk.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
//�������ڣ�2009-1-7 17:10:36
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
	loggerDebug("CancleGivenChk","@@@Flag:"+Flag);
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
		
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	
 		String tContNo=request.getParameter("ContNo");
 		String tInsuredNo = request.getParameter("InsuredNo");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("InsuredNo",tInsuredNo);	 
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tTransferData);
		
		/*CancleGivenUI tCancleGivenUI = new CancleGivenUI();
		
		try{
			loggerDebug("CancleGivenChk","this will save the data!!!");
			tCancleGivenUI.submitData(tVData,"submit");
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tCancleGivenUI.mErrors;
			if (!tError.needDealError()){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}
			else{
				Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}*/
		String busiName="CancleGivenUI";
		  String mDescType="����";
		  loggerDebug("CancleGivenChk","this will save the data!!!");
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData,"submit",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"ʧ��";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"�ɹ�! ";
			      	FlagStr = "Succ";  
			  }
%>                      
<html>
<script language="javascript">	
			parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
