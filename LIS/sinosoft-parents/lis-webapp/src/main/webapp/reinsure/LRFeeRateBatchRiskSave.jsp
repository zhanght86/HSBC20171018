<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRFeeRateBatchSave11.jsp
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
  //LRFeeRateBatchRiskUI mLRFeeRateBatchRiskUI = new LRFeeRateBatchRiskUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
	
  RIAssociateFeeTableSet mRIAssociateFeeTableSet = new RIAssociateFeeTableSet();
  TransferData tTransferData = new TransferData();
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
    
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  System.out.println(" ��ʼ���л�ȡ���ݵĲ���! ");
  
  String[] StrNum							=request.getParameterValues("FeeRateBatchGridNo");
  String[] tAccumulateDefNO		=request.getParameterValues("FeeRateBatchGrid1");
  String[] tDeTailFlag			  =request.getParameterValues("FeeRateBatchGrid2");
  String[] tRIPreceptNo	      =request.getParameterValues("FeeRateBatchGrid4");
  String[] tAssociateCode			=request.getParameterValues("FeeRateBatchGrid5");
  String[] tAreaID		        =request.getParameterValues("FeeRateBatchGrid6");
  //String[] tAreaLevel			  =request.getParameterValues("FeeRateBatchGrid");
  String[] tIncomeCompanyNo	  =request.getParameterValues("FeeRateBatchGrid7");
  String[] tUpperLimit			  =request.getParameterValues("FeeRateBatchGrid9");
  String[] tLowerLimit		    =request.getParameterValues("FeeRateBatchGrid10");
  String[] tPremFeeTableNo		=request.getParameterValues("FeeRateBatchGrid11");
  String[] tComFeeTableNo			=request.getParameterValues("FeeRateBatchGrid13");
  
  if(StrNum!=null)
  {
  	for(int i=0;i<StrNum.length;i++)
  	{
  			RIAssociateFeeTableSchema 	mRIAssociateFeeTableSchema = new RIAssociateFeeTableSchema();
				
  	    mRIAssociateFeeTableSchema.setAccumulateDefNO (tAccumulateDefNO[i]);
  	    mRIAssociateFeeTableSchema.setDeTailFlag      (tDeTailFlag[i]);
  	    mRIAssociateFeeTableSchema.setRIPreceptNo     (tRIPreceptNo[i]);
  	    mRIAssociateFeeTableSchema.setAssociateCode   (tAssociateCode[i]);   
  	    mRIAssociateFeeTableSchema.setAreaID          (tAreaID[i]);
  	    mRIAssociateFeeTableSchema.setAreaLevel       ("0"); //������
  	    mRIAssociateFeeTableSchema.setIncomeCompanyNo (tIncomeCompanyNo[i]);
  	    mRIAssociateFeeTableSchema.setUpperLimit      (tUpperLimit[i]);
  	    mRIAssociateFeeTableSchema.setLowerLimit      (tLowerLimit[i]);
  	    mRIAssociateFeeTableSchema.setPremFeeTableNo  (tPremFeeTableNo[i]);
  	    mRIAssociateFeeTableSchema.setComFeeTableNo   (tComFeeTableNo[i]);
  	    mRIAssociateFeeTableSet.add(mRIAssociateFeeTableSchema);
  	}
  }
  
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
 // try
  //{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIAssociateFeeTableSet);
   // mLRFeeRateBatchRiskUI.submitData(tVData,mOperateType);
 // }
  //catch(Exception ex)
  //{
  //  Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
  //  FlagStr = "Fail";
  //}
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRFeeRateBatchRiskUI"))
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
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String result = "";
  TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
  result=(String) sTransferData.getValueByName("String");
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	System.out.println("����ɹ�!");
      Content = mDescType+""+"�ɹ�!"+"";
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>