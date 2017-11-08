<%
//Name: LLLcContSuspendSave.jsp
//Function������������Ϣ�ύ
//Date��
//Author ��yuejinwei
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//�������
LCContHangUpStateSet tLCContHangUpStateSet = new LCContHangUpStateSet(); //���˱�����Ϣ����
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
//LLLcContSuspendUI tLLLcContSuspendUI = new LLLcContSuspendUI();
String busiName="grpLLLcContSuspendUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLLcContSuspendSave","ҳ��ʧЧ,�����µ�½");
}
else //ҳ����Ч
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    //����MulLine��������ݼ���
    String tGridNo[] = request.getParameterValues("LLLcContSuspendGridNo");  //�õ�����е�����ֵ
    String tGrid1[] = request.getParameterValues("LLLcContSuspendGrid2"); //�õ���2�У���ͬ��
    String tGrid9[] = request.getParameterValues("LLLcContSuspendGrid9"); //�õ���9�У���ȫ����״̬
    String tGrid10[] = request.getParameterValues("LLLcContSuspendGrid10"); //�õ���10�У����ڹ���״̬

    int Count = tGridNo.length; //�õ����ܵ��ļ�¼��
	for(int index=0;index < Count;index++)
    {
       LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema(); //���˱�����
       tLCContHangUpStateSchema.setContNo(tGrid1[index]); //��ͬ��
       tLCContHangUpStateSchema.setInsuredNo("000000"); //�������˺���
       tLCContHangUpStateSchema.setPolNo("000000"); //�������ֺ�
       tLCContHangUpStateSchema.setPosFlag(tGrid9[index]); //��ȫ����״̬
       tLCContHangUpStateSchema.setRNFlag(tGrid10[index]); //���ڹ���״̬
       tLCContHangUpStateSet.add(tLCContHangUpStateSchema);
    }
    
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));

    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(tLCContHangUpStateSet);
        tVData.add(mTransferData);

        //���ݴ���
//	      if (!tLLLcContSuspendUI.submitData(tVData,""))
//	      {
//            Content = " �����ύtLLLcContSuspendUIʧ�ܣ�ԭ����: " + tLLLcContSuspendUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//	      }
		if(!tBusinessDelegate.submitData(tVData,"",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ύtLLLcContSuspendUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ύtLLLcContSuspendUIʧ��";
				FlagStr = "Fail";				
			}
		}

	      else
	      {
	    	Content = " �����ύ�ɹ�! ";
    	    FlagStr = "Succ";
          }
    }
    catch(Exception ex)
    {
        Content = "�����ύtLLLcContSuspendUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }

    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
        //tError = tLLLcContSuspendUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {
          	Content = " �����ύ�ɹ�! ";
    	    FlagStr = "Succ";
        }
        else
        {
      	    Content = " �����ύLLLcContSuspendUIʧ�ܣ�ԭ����:" + tError.getFirstError();
    	    FlagStr = "Fail";
        }
    }
    loggerDebug("LLLcContSuspendSave","------LLLcContSuspendSave.jsp end------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
