<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//�������
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  VData tVData = new VData();

  LCGrpCustomerImportLogSet tLCGrpCustomerImportLogSet=new LCGrpCustomerImportLogSet(); //��������˵��������־��
/*
  String tRgtNo[] = request.getParameterValues("DiskErrQueryGrid1");  //������
  String tBatchNo[] = request.getParameterValues("DiskErrQueryGrid5"); //���κ�
  String tID[] = request.getParameterValues("DiskErrQueryGrid6"); //�������

  String tChk[] = request.getParameterValues("InpDiskErrQueryGridChk"); //������ʽ=�� Inp+MulLine������+Chk��

    for(int index=0;index<tChk.length;index++)
    {
       LCGrpCustomerImportLogSchema tLCGrpCustomerImportLogSchema=new LCGrpCustomerImportLogSchema();
       tLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo[index]); //������
       tLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo[index]); //���κ�
       tLCGrpCustomerImportLogSchema.setID(tID[index]); //�������
       tLCGrpCustomerImportLogSet.add(tLCGrpCustomerImportLogSchema);
    }            
*/


     LCGrpCustomerImportLogSchema tLCGrpCustomerImportLogSchema=new LCGrpCustomerImportLogSchema();
     tLCGrpCustomerImportLogSchema.setRgtNo(request.getParameter("tRptNo")); //������
     tLCGrpCustomerImportLogSchema.setBatchNo(request.getParameter("tBatchNo")); //���κ�
     tLCGrpCustomerImportLogSchema.setID(request.getParameter("tID")); //�������
     tLCGrpCustomerImportLogSchema.setOperator(request.getParameter("Operator")); //��½�û�
     tLCGrpCustomerImportLogSet.add(tLCGrpCustomerImportLogSchema);

  tVData.add(tG);
  tVData.add(tLCGrpCustomerImportLogSet);

//  DiskDeleteInsuredBL tDiskDeleteInsuredBL = new DiskDeleteInsuredBL();
//  
//  if(!tDiskDeleteInsuredBL.submitData(tVData,""))
//  {
//      Content = " ɾ��ʧ�ܣ�ԭ����: " + tDiskDeleteInsuredBL.mErrors.getError(0).errorMessage;
//      FlagStr = "Fail";
//  }
String busiName="grpDiskDeleteInsuredBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "ɾ��ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "ɾ��ʧ��";
		FlagStr = "Fail";				
	}
}

  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "")
  {
    //tError = tDiskDeleteInsuredBL.mErrors;
     tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " ɾ���ɹ�! ";
      FlagStr = "Succ";
    }
    else                                                                           
    {
      Content = " ɾ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
  //��Ӹ���Ԥ����
  loggerDebug("DiskDeleteInsured","--------------------"+Content);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
