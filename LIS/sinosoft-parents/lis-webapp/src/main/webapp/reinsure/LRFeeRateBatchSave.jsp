<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRFeeRateSave.jsp
//�����ܣ�
//�������ڣ�2008-01-04
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
	<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@page import="com.sinosoft.lis.reinsure.*"%>
 	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%
  System.out.println("��ʼִ��Saveҳ��");
  
  //LRFeeRateBatchDefUI mLRFeeRateBatchDefUI = new LRFeeRateBatchDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
  RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema = new RIFeeRateTableTraceSchema();
	TransferData mTransferData = new TransferData();  
  CErrors tError = null;
  
  String mOperateType = request.getParameter("OperateType");
  
  System.out.println("������������"+mOperateType);
  
  mRIFeeRateTableTraceSchema.setFeeTableNo(request.getParameter("FeeTableNo"));
  mRIFeeRateTableTraceSchema.setFeeTableName(request.getParameter("FeeTableName"));
  mRIFeeRateTableTraceSchema.setBatchNo(request.getParameter("BatchNo"));
  mRIFeeRateTableTraceSchema.setSaveDataName(request.getParameter("SaveDataName"));
  mRIFeeRateTableTraceSchema.setInureDate(request.getParameter("InureDate"));
  mRIFeeRateTableTraceSchema.setLapseDate(request.getParameter("LapseDate"));
  mRIFeeRateTableTraceSchema.setState(request.getParameter("State"));
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  System.out.println(" ��ʼ���л�ȡ���ݵĲ���! ");
  if(mOperateType.equals("INSERT"))
  {
    mDescType = ""+"�������ʱ�"+"";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = ""+"�޸ķ��ʱ�"+"";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = ""+"ɾ�����ʱ�"+"";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = ""+"��ѯ���ʱ�"+"";
  }
  VData tVData = new VData();
  //try
  //{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIFeeRateTableTraceSchema);
   // mLRFeeRateBatchDefUI.submitData(tVData,mOperateType);
  //}
 // catch(Exception ex)
  //{
    //Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    //FlagStr = "Fail";
  //}
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRFeeRateBatchDefUI"))
   {    
        if(uiBusinessDelegate.getCErrors()!=null&&uiBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"ʧ�ܣ�ԭ����:"+"" + uiBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"����ʧ��"+"";
				   FlagStr = "Fail";				
				}
   }
	System.out.println("Content: "+Content);
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String result = "";
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
     	 Content = mDescType+""+"�ɹ���"+""+" "+"��ͬ��ţ�"+""+result;
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mDescType+" "+"ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); 
</script>
</html>