<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�InsuredUWInfoChk.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
//�������ڣ�2005-01-19 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
 	String tCaseNo = request.getParameter("CaseNo"); 
  String tBatNo = request.getParameter("BatNo");
  
 	String tContNo=request.getParameter("ContNo");
 	String tUWIdea=request.getParameter("UWIdea"); 		
  String tUWFlag = request.getParameter("uwState");	
  System.out.println(tUWFlag);
  System.out.println("tCaseNo:"+tCaseNo);
  System.out.println("tBatNo:"+tBatNo);
	
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
	 
 
  	LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
  	
  	
   
    tLLCUWMasterSchema.setCaseNo(tCaseNo);
    tLLCUWMasterSchema.setBatNo(tBatNo);
 		tLLCUWMasterSchema.setContNo(tContNo);
 	
 		tLLCUWMasterSchema.setPassFlag(tUWFlag);
 		tLLCUWMasterSchema.setUWIdea(tUWIdea);
 		
 	
	
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLLCUWMasterSchema);
	
		
		SecondManuUWUI tSecondManuUWUI = new SecondManuUWUI();
		
		try{
			System.out.println("this will save the data!!!");
			tSecondManuUWUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tSecondManuUWUI.mErrors;
			if (!tError.needDealError()){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}
			else{
				Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}	


%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
