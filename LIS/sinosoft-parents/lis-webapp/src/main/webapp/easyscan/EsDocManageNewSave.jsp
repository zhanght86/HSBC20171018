<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EsDocManageSave.jsp
//�����ܣ�
//�������ڣ�2009-09-09
//������  yanglh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%>
  <%@page import="java.util.HashSet"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");
	TransferData tTransferData=new TransferData();
	tTransferData.setNameAndValue("DocID",request.getParameter("DOC_ID"));
	tTransferData.setNameAndValue("PrtNoA",request.getParameter("DOC_CODE")); //�仯��
	tTransferData.setNameAndValue("PrtNoB",request.getParameter("PrtNo")); //�仯ǰ	 
	tTransferData.setNameAndValue("Reason",request.getParameter("DelReason")); //ɾ����֤ԭ��
	loggerDebug("EsDocManageNewSave","--DocID:"+request.getParameter("DOC_ID"));
	loggerDebug("EsDocManageNewSave","--PrtNoA:"+request.getParameter("DOC_CODE"));
	loggerDebug("EsDocManageNewSave","--PrtNoB:"+request.getParameter("PrtNo"));
	loggerDebug("EsDocManageNewSave","--Reason:"+request.getParameter("DelReason"));
	//������Ϣ������У�鴦��
  EsDocManageUI tManageUI   = new EsDocManageUI();

  //�������
  ES_DOC_MAINSchema tES_DOC_MAINSchema   = new ES_DOC_MAINSchema();
  ES_DOC_PAGESSet tES_DOC_PAGESSet   = new ES_DOC_PAGESSet();

  //�������
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";

  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  loggerDebug("EsDocManageNewSave","--EsDocManageNewSave.jsp--transact:"+transact);
  
  if(transact.equals("UPDATE||PAGES") || transact.equals("DELETE||PAGES"))
  {
	  String tGridNo[] = request.getParameterValues("CodeGridNo");  //�õ�����е�����ֵ
	  String tChk[] = request.getParameterValues("InpCodeGridChk");    //������ʽ="MulLine������+Chk"
	  String tGrid1  [] = request.getParameterValues("CodeGrid1");  
	  String tGrid2  [] = request.getParameterValues("CodeGrid2");  
	  String tGrid3  [] = request.getParameterValues("CodeGrid3");  
	  String tGrid4  [] = request.getParameterValues("CodeGrid4");  
	  String tGrid5  [] = request.getParameterValues("CodeGrid5");  
	  String tGrid6  [] = request.getParameterValues("CodeGrid6");  
	  String tGrid7  [] = request.getParameterValues("CodeGrid7");  
	  String tGrid8  [] = request.getParameterValues("CodeGrid8");  
	  String tGrid9  [] = request.getParameterValues("CodeGrid9");  
	  String tGrid10 [] = request.getParameterValues("CodeGrid10"); 
	  String tGrid11 [] = request.getParameterValues("CodeGrid11"); 
	  //String tGrid12 [] = request.getParameterValues("CodeGrid12"); 
	  String tGrid13 [] = request.getParameterValues("CodeGrid13");
	  
	  int count = tChk.length; //�õ����ܵ��ļ�¼��
	  loggerDebug("EsDocManageNewSave","--EsDocManageNewSave.jsp--20:" + count + ":" + tGridNo.length);  
	  for(int index=0; index< count; index++)
	  {
	    if(tChk[index].equals("1"))
	    {
	    //ѡ�е���
		    ES_DOC_PAGESSchema tPageSchema = new ES_DOC_PAGESSchema();
		    tPageSchema.setPageID(tGrid1[index]);
		    tPageSchema.setDocID(tGrid2[index]);
		    tPageSchema.setPageCode(tGrid3[index]);
		    tPageSchema.setHostName(tGrid4[index]);
		    tPageSchema.setPageName(tGrid5[index]);
		    tPageSchema.setPageFlag(tGrid6[index]);
		    tPageSchema.setPicPathFTP(tGrid7[index]);
		    tPageSchema.setPicPath(tGrid8[index]);
		    tPageSchema.setOperator(tGrid9[index]);
		    tPageSchema.setMakeDate(tGrid10[index]);
		    tPageSchema.setMakeTime(tGrid11[index]);
		    tPageSchema.setScanNo(tGrid13[index]);
		    //tPageSchema.setDoc_Type(tGrid12[index]);
		    
		    loggerDebug("EsDocManageNewSave","pageCOde="+tGrid3[index]);
	
		    tES_DOC_PAGESSet.add(tPageSchema);
	    }
	  }
  } 
  else if(transact.equals("DELETE||DOCID") || transact.equals("UPDATE||DOCID"))
  {	  
	  String tGridNo[] = request.getParameterValues("CodeGridNo");  //�õ�����е�����ֵ
	  String tChk[] = request.getParameterValues("InpCodeGridChk");    //������ʽ="MulLine������+Chk"
	  String tGrid2  [] = request.getParameterValues("CodeGrid2");  //�õ���2�е�����ֵ
	  String tGrid12 [] = request.getParameterValues("CodeGrid12"); //�õ���2�е�����ֵ
	  
	  int count = tChk.length; //�õ����ܵ��ļ�¼��
	  
	  loggerDebug("EsDocManageNewSave","--EsDocManageSave.jsp--20:" + count + ":" + tGridNo.length);  
	  
	  for(int index=0; index< count; index++)
	  {
	    if(tChk[index].equals("1"))
	    {
	    //ѡ�е���
		    tES_DOC_MAINSchema.setDocID(tGrid2[index]);
		  	tES_DOC_MAINSchema.setSubType(tGrid12[index]); //��֤����
		  	break;
	    }
	  }
  }
  
  try
  {
	    // ׼���������� VData
	    VData tVData = new VData();
	    tVData.add(tES_DOC_MAINSchema);
	    tVData.add(tES_DOC_PAGESSet);
	    tVData.add(tTransferData);
	    tVData.add(tG);
	
	    loggerDebug("EsDocManageNewSave","--EsDocManageSave.jsp--before submitData");
	    tManageUI.submitData(tVData,transact);
	    loggerDebug("EsDocManageNewSave","--EsDocManageSave.jsp--after  submitData");
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tManageUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " ����ɹ�! ";
      FlagStr = "Success";
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
