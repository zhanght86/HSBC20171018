<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EsDocManageSave.jsp
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.service.*" %>
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
tTransferData.setNameAndValue("ReasonCode",request.getParameter("DelReasonCode")); //ɾ����֤ԭ�����
tTransferData.setNameAndValue("serialno",request.getParameter("serialno"));
loggerDebug("EsDocManageSave","--DocID:"+request.getParameter("DOC_ID"));
loggerDebug("EsDocManageSave","--PrtNoA:"+request.getParameter("DOC_CODE"));
loggerDebug("EsDocManageSave","--PrtNoB:"+request.getParameter("PrtNo"));
loggerDebug("EsDocManageSave","--Reason:"+request.getParameter("DelReason"));
//������Ϣ������У�鴦��
  //EsDocManageUI tManageUI   = new EsDocManageUI();
String busiName="EsDocManageUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

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
  loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--transact:"+transact);
  
  if(transact.equals("UPDATE||MAIN"))
  {	  
	  tES_DOC_MAINSchema.setDocID(request.getParameter("DOC_ID"));
	  tES_DOC_MAINSchema.setDocCode(request.getParameter("DOC_CODE"));
	  tES_DOC_MAINSchema.setNumPages(request.getParameter("NUM_PAGES"));
	  tES_DOC_MAINSchema.setMakeDate(request.getParameter("INPUT_DATE"));
	  tES_DOC_MAINSchema.setMakeTime(request.getParameter("Input_Time"));
	  tES_DOC_MAINSchema.setScanOperator(request.getParameter("ScanOperator"));
	  tES_DOC_MAINSchema.setManageCom(request.getParameter("ManageCom"));
	  tES_DOC_MAINSchema.setInputState(request.getParameter("InputState"));
	  tES_DOC_MAINSchema.setOperator(request.getParameter("Operator"));
	  tES_DOC_MAINSchema.setInputStartDate(request.getParameter("InputStartDate"));
	  tES_DOC_MAINSchema.setInputStartTime(request.getParameter("InputStartTime"));
	  tES_DOC_MAINSchema.setInputEndDate(request.getParameter("InputEndDate"));
	  tES_DOC_MAINSchema.setInputEndTime(request.getParameter("InputEndTime"));
	  tES_DOC_MAINSchema.setDocFlag(request.getParameter("DOC_FLAGE"));
	  tES_DOC_MAINSchema.setBussType(request.getParameter("BussType"));
	  tES_DOC_MAINSchema.setScanNo(request.getParameter("ScanNo"));
	  //tES_DOC_MAINSchema.setDOC_EX_FLAG(request.getParameter("Doc_Ex_Flag"));
	  tES_DOC_MAINSchema.setDocRemark(request.getParameter("DOC_REMARK"));
	  //tES_DOC_MAINSchema.setSubType(request.getParameter("SubType")); //2009-2-10 ln add --�޸ĵ�֤����
  } 
  
  else if(transact.equals("UPDATE||PAGES") || transact.equals("DELETE||PAGES"))
  {
	  String tGridNo[] = request.getParameterValues("CodeGridNo");  //�õ�����е�����ֵ
	  String tChk[] = request.getParameterValues("InpCodeGridChk");    //������ʽ="MulLine������+Chk"
	  String tGrid1  [] = request.getParameterValues("CodeGrid1");  //�õ���1�е�����ֵ
	  String tGrid2  [] = request.getParameterValues("CodeGrid2");  //�õ���2�е�����ֵ
	  String tGrid3  [] = request.getParameterValues("CodeGrid3");  //�õ���2�е�����ֵ
	  String tGrid4  [] = request.getParameterValues("CodeGrid4");  //�õ���2�е�����ֵ
	  String tGrid5  [] = request.getParameterValues("CodeGrid5");  //�õ���2�е�����ֵ
	  String tGrid6  [] = request.getParameterValues("CodeGrid6");  //�õ���2�е�����ֵ
	  String tGrid7  [] = request.getParameterValues("CodeGrid7");  //�õ���2�е�����ֵ
	  String tGrid8  [] = request.getParameterValues("CodeGrid8");  //�õ���2�е�����ֵ
	  String tGrid9  [] = request.getParameterValues("CodeGrid9");  //�õ���2�е�����ֵ
	  String tGrid10 [] = request.getParameterValues("CodeGrid10"); //�õ���2�е�����ֵ
	  String tGrid11 [] = request.getParameterValues("CodeGrid11"); //�õ���2�е�����ֵ
	  //String tGrid12 [] = request.getParameterValues("CodeGrid12"); //�õ���2�е�����ֵ
	  String tGrid13 [] = request.getParameterValues("CodeGrid13");
	  
	  int count = tChk.length; //�õ����ܵ��ļ�¼��
	  
	  loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--20:" + count + ":" + tGridNo.length);  
	  
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
	  
	  loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--20:" + count + ":" + tGridNo.length);  
	  
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

    loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--before submitData");
    //tManageUI.submitData(tVData,transact);
    if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "����ʧ��";
			FlagStr = "Fail";				
		}
	}else{
			Content = "����ɹ�";
			FlagStr = "Success";	
	}
    
    loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--after  submitData");
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    //tError = tManageUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
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
