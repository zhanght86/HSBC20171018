<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ApplyRecallChk.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	  //�������
	  CErrors tError = null;
	  String FlagStr = "Fail";
		String Content="";
		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getAttribute("GI");
		
		RILMUWSet mRILMUWSet=new RILMUWSet();
		String tOpeData 	= request.getParameter("OpeData"); 
		String tOpeFlag 	= request.getParameter("OpeFlag"); 
		String tDutyCode	= request.getParameter("DutyCode"); 
		String tPolNo 		= request.getParameter("PolNo"); 
		String tEdorNo 		= request.getParameter("EdorNo"); 
		String tEdorType 	= request.getParameter("EdorType"); 
		String tCaseNo 		= request.getParameter("CaseNo"); 
		
		System.out.println("OpeData: "	+tOpeData); 
		System.out.println("OpeFlag: "	+tOpeFlag); 
		System.out.println("DutyCode: "	+tDutyCode); 
		System.out.println("PolNo: "		+tPolNo); 
		System.out.println("EdorNo: "		+tEdorNo); 
		System.out.println("EdorType: "	+tEdorType); 
		System.out.println("CaseNo: "		+tCaseNo); 
		
		String[] StrNum=request.getParameterValues("ReInsureGridNo"); 
		String[] ruleCode=request.getParameterValues("ReInsureGrid1"); 
		System.out.println(StrNum.length); 
		for(int i=0;i<StrNum.length;i++){ 
			System.out.println("ruleCode["+i+"]: "+ruleCode[i]); 
		}
		
		if(StrNum!=null){
	  RILMUWSchema tRILMUWSchema;
	  for(int i=0;i<StrNum.length;i++){
	  		tRILMUWSchema=new RILMUWSchema();
	      tRILMUWSchema.setRuleCode(ruleCode[i]);
	      mRILMUWSet.add(tRILMUWSchema);
			}
		}
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OpeData",tOpeData);
		tTransferData.setNameAndValue("OpeFlag",tOpeFlag);
		tTransferData.setNameAndValue("DutyCode",tDutyCode);
		tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("EdorType",tEdorType);
		tTransferData.setNameAndValue("CaseNo",tCaseNo);
		
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(mRILMUWSet);
		tVData.add(tG);
		
		// ���ݴ��� 
		String[] result=new String[StrNum.length];
		AutoUWErrorUI tAutoUWErrorUI = new AutoUWErrorUI(); 
		if (tAutoUWErrorUI.submitData(tVData,"") == false){ 
			Content = " "+"�Ժ�ʧ�ܣ�ԭ����:"+" " + tAutoUWErrorUI.mErrors.getError(0).errorMessage; 
			FlagStr = "Fail";
		}
	  else{
	  	result=tAutoUWErrorUI.getResult();
		  Content = " "+"�Ժ˳ɹ�!"+" ";
		  FlagStr = "Succ";
		}
%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
<%
	for (int i=0;i<result.length;i++){
%>
		var passName;
		if(<%=result[i]%>=="1"){
			passName="ͨ��";
		}
		else if(<%=result[i]%>=="0"){
			passName="δͨ��";
		}
		if(<%=result[i]%>=="1"||<%=result[i]%>=="0"){
			parent.fraInterface.ReInsureGrid.setRowColData(<%=i%>,3,passName+"");
		}
<%	
	}
%>
</script>
</html>
