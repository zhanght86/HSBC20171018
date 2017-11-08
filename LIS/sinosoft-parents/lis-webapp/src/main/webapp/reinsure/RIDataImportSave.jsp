<%@include file="/i18n/language.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>

<%
	String FlagStr = "";
	String Content = "";
  
	String endDate = request.getParameter("EndDate");
	String processType = request.getParameter("ProcessType");
	VData tVData = new VData();
	
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EndDate",endDate);
    tTransferData.setNameAndValue("ProcessType",processType);

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
    tVData.add(tGI);
    tVData.add(tTransferData);

	RIImptMngUI tRIImptMngUI = new RIImptMngUI();
	if(processType.equals("21")){
		Content=""+"ҵ�����ݵ���"+"";
		String tChk[] = request.getParameterValues("InpAccmulateGridChk");  
		String[] accumulateDefNO = request.getParameterValues("AccmulateGrid1"); 
		String[] accumulateDefName = request.getParameterValues("AccmulateGrid2"); 
		String[] arithmetic = request.getParameterValues("AccmulateGrid3"); 
		String[] state = request.getParameterValues("AccmulateGrid4");	
        RIAccumulateDefSet tRIAccumulateDefSet = new RIAccumulateDefSet(); 
        RIAccumulateDefSchema tRIClaimStateSchema;		
		for(int i=0;i<tChk.length;i++){
			if(tChk[i].equals("0")) continue;
			tRIClaimStateSchema = new RIAccumulateDefSchema();
			tRIClaimStateSchema.setAccumulateDefNO(accumulateDefNO[i]);
			tRIClaimStateSchema.setAccumulateDefName(accumulateDefName[i]);
			tRIClaimStateSchema.setArithmeticID(arithmetic[i]);
			tRIClaimStateSchema.setState(state[i]);
			tRIAccumulateDefSet.add(tRIClaimStateSchema);			
		}
		tVData.add(tRIAccumulateDefSet);
	}else if(processType.equals("22")){
		Content=""+"�ֱ����ݵ���"+"";
		String tChk[] = request.getParameterValues("InpTaskListGridChk");  
		String[] arithmeticID = request.getParameterValues("TaskListGrid1"); 
		String[] arithmeticName = request.getParameterValues("TaskListGrid2"); 
		String[] arithmeticType = request.getParameterValues("TaskListGrid3");
		String[] calItemOrder = request.getParameterValues("TaskListGrid4");
		String[] itemCalType = request.getParameterValues("TaskListGrid5");
		String[] calClass = request.getParameterValues("TaskListGrid6");
		RIItemCalSet tRIItemCalSet = new RIItemCalSet();
		RIItemCalSchema tRIItemCalSchema;
		for(int i=0;i<tChk.length;i++){
			if(tChk[i].equals("0")) continue;
			tRIItemCalSchema = new RIItemCalSchema();
			tRIItemCalSchema.setArithmeticDefID("impt000001");
			tRIItemCalSchema.setArithmeticID(arithmeticID[i]);
			tRIItemCalSchema.setArithmeticName(arithmeticName[i]);
			tRIItemCalSchema.setArithmeticType(arithmeticType[i]);
			tRIItemCalSchema.setCalItemOrder(calItemOrder[i]);
			tRIItemCalSchema.setItemCalType(itemCalType[i]);
			tRIItemCalSchema.setCalClass(calClass[i]);
			tRIItemCalSet.add(tRIItemCalSchema);
 		}
		tVData.add(tRIItemCalSet);				
	}else if(processType.equals("31")){
		Content = ""+"ҵ������ɾ��"+"";
	}else if(processType.equals("32")){
		Content = ""+"�ֱ�����ɾ��"+"";
	}
	
	// ҵ����
	if (!tRIImptMngUI.submitData(tVData,processType)){ 
		Content += ""+"ʧ�ܣ�ԭ����:"+" " + tRIImptMngUI.mErrors.getError(0).errorMessage; 
		FlagStr = "Fail";				
	}else{
	  	Content += ""+"�ɹ�!"+" ";
	  	FlagStr = "Succ";
	}

%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
