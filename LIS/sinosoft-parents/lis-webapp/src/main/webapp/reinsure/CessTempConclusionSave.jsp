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
		
		CasualRIContAssociateSet mCasualRIContAssociateSet=new CasualRIContAssociateSet(); 
		String tDutyCode= request.getParameter("DutyCode"); 
		String tPolNo 	= request.getParameter("PolNo"); 
		
	  String[] StrNum=request.getParameterValues("PreceptGridNo");
	  String[] rIContNo=request.getParameterValues("PreceptGrid1");
	  String[] rIPreceptNo=request.getParameterValues("PreceptGrid2");
	  String tChk[] = request.getParameterValues("InpPreceptGridChk");
	  CasualRIContAssociateSchema tCasualRIContAssociateSchema ;
		for(int index=0;index<tChk.length;index++){
			if(tChk[index].equals("0")) continue;
			tCasualRIContAssociateSchema = new CasualRIContAssociateSchema();
			tCasualRIContAssociateSchema.setRIContNo("");
			tCasualRIContAssociateSchema.setRIPreceptNo("");
			tCasualRIContAssociateSchema.setDataFlag("02");
			tCasualRIContAssociateSchema.setContNo("");
			tCasualRIContAssociateSchema.setRiskCode("");
			tCasualRIContAssociateSchema.setDutyCode(tDutyCode);
			tCasualRIContAssociateSchema.setNaturalRIContNo(rIContNo[index]);
			tCasualRIContAssociateSchema.setNaturalRIPreceptNo(rIPreceptNo[index]);
			mCasualRIContAssociateSet.add(tCasualRIContAssociateSchema);
		}
	  
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("DutyCode",tDutyCode);
		tTransferData.setNameAndValue("PolNo",tPolNo);
		
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(mCasualRIContAssociateSet);
		tVData.add(tG);
		
		// ���ݴ��� 
		LRTempConclusionUI tLRTempConclusionUI = new LRTempConclusionUI(); 
		if (tLRTempConclusionUI.submitData(tVData,"") == false){ 
			Content = " "+"����ʧ�ܣ�ԭ����:"+" " + tLRTempConclusionUI.mErrors.getError(0).errorMessage; 
			FlagStr = "Fail";
		}
    else{
		  Content = " "+"����ɹ�!"+" ";
		  FlagStr = "Succ";
		}

%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
