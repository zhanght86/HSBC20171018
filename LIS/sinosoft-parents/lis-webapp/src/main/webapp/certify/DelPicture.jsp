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
  <%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getValue("GI");
String tContno = request.getParameter("CertifyNo");
TransferData tTransferData=new TransferData();
tTransferData.setNameAndValue("ContNo",tContno);
loggerDebug("DelPicture","--ContNo:"+tContno);
//������Ϣ������У�鴦��
  //DELPictureUI tDELPictureUI = new DELPictureUI();

  //�������
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";

  
  //try
  {
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(tTransferData);
    tVData.add(tG);

    loggerDebug("DelPicture","--DELPicture.jsp--before submitData");
    //tDELPictureUI.submitData(tVData,transact);
    String busiName="DELPictureUI";
    String mDescType="����";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,transact,busiName))
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
    loggerDebug("DelPicture","--DELPicture.jsp--after  submitData");
  /*}
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tDELPictureUI.mErrors;
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
  }*/  

  //��Ӹ���Ԥ����

%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
