<%
//**************************************************************************************************
//ҳ������: AddRelationSave
//ҳ�湦�ܣ����������˹�ϵ��¼
//�޸����ڣ�  �޸�ԭ��/���ݣ�
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("AddRelationSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("AddRelationSave","-----transact= "+transact);
    String tChk[] = request.getParameterValues("InpInsuredGridChk");
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************  
	String tCustomerNo[] = request.getParameterValues("InsuredGrid1");
	String tRelation[] = request.getParameterValues("InsuredGrid7");
	String tMainCustomerNo = request.getParameter("MainCustomerNo");
	String tPolNo[] = request.getParameterValues("InsuredGrid8");
    int tcontCount = tChk.length;
    for(int i = 0; i < tcontCount; i++){
    	if(tChk[i].equals("1")){
			LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
			tLCInsuredRelatedSchema.setCustomerNo(tCustomerNo[i]);
			tLCInsuredRelatedSchema.setMainCustomerNo(tMainCustomerNo);
			tLCInsuredRelatedSchema.setPolNo(tPolNo[i]);
			tLCInsuredRelatedSchema.setRelationToInsured(tRelation[i]);
			try{
				VData tVData = new VData();
		        tVData.add(tGI);
		        tVData.add(tLCInsuredRelatedSchema);
//		        AddRelationUI tAddRelationUI = new AddRelationUI();
//		       if(!tAddRelationUI.submitData(tVData,transact)){
//		            Content = "�ύʧ�ܣ�ԭ����: " +
//		            tAddRelationUI.mErrors.getError(0).errorMessage;
//		            FlagStr = "Fail";
//		        }
				String busiName="grpAddRelationUI";
				BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "�ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "�ύʧ��";
						FlagStr = "Fail";				
					}
				}

		        else{
		            Content = "�����ύ�ɹ�";
		            FlagStr = "Succ";
		        }
			}
			catch(Exception ex){
		        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
		        FlagStr = "Fail";
		    }
    	}
	}
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
