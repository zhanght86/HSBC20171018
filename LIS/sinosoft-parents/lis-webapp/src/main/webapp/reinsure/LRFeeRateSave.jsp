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
  //LRFeeRateDefUI mLRFeeRateDefUI = new LRFeeRateDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getAttribute("GI"));
  RIFeeRateTableDefSchema 	mRIFeeRateTableDefSchema = new RIFeeRateTableDefSchema();
  RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema = new RIFeeRateTableTraceSchema();
  RIFeeRateTableClumnDefSet mRIFeeRateTableClumnDefSet = new RIFeeRateTableClumnDefSet();
	TransferData mTransferData = new TransferData();  
  CErrors tError = null;
  String mDeleteType = request.getParameter("DeleteType");
  String mOperateType = request.getParameter("OperateType");
  
  mTransferData.setNameAndValue("DeleteType", mDeleteType);
  
  System.out.println("������������"+mOperateType);
  System.out.println("������"+mDeleteType);
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  System.out.println(" ��ʼ���л�ȡ���ݵĲ���! ");
  mRIFeeRateTableDefSchema.setFeeTableNo	(request.getParameter("FeeTableNo"	));
  mRIFeeRateTableDefSchema.setFeeTableName(request.getParameter("FeeTableName"));
  mRIFeeRateTableDefSchema.setFeeTableType(request.getParameter("FeeTableType"));
  mRIFeeRateTableDefSchema.setReMark			(request.getParameter("ReMark"));
  mRIFeeRateTableDefSchema.setState			(request.getParameter("State"));
  
  String[] StrNum							=request.getParameterValues("TableClumnDefGridNo");
  String[] tFeeClumnName			=request.getParameterValues("TableClumnDefGrid1");
  String[] tFeeClumnDataCode	=request.getParameterValues("TableClumnDefGrid2");
  String[] tFeeClumnType			=request.getParameterValues("TableClumnDefGrid4");
  String[] tTagetClumn				=request.getParameterValues("TableClumnDefGrid5");
  String[] tTagetDLClumn			=request.getParameterValues("TableClumnDefGrid7");
  String[] tTagetULClumn			=request.getParameterValues("TableClumnDefGrid9");
  
  if(StrNum!=null){
  	RIFeeRateTableClumnDefSchema tRIFeeRateTableClumnDefSchema;
  	for(int i=0;i<StrNum.length;i++){
  			tRIFeeRateTableClumnDefSchema=new RIFeeRateTableClumnDefSchema();
  			
  	    tRIFeeRateTableClumnDefSchema.setFeeTableNo				(request.getParameter("FeeTableNo"	));
  	    tRIFeeRateTableClumnDefSchema.setFeeTableName     (request.getParameter("FeeTableName"));
  	    tRIFeeRateTableClumnDefSchema.setFeeClumnName     (tFeeClumnName[i]);
  	    tRIFeeRateTableClumnDefSchema.setFeeClumnDataCode (tFeeClumnDataCode[i]);
  	    tRIFeeRateTableClumnDefSchema.setFeeClumnType     (tFeeClumnType[i]);
  	    tRIFeeRateTableClumnDefSchema.setTagetClumn       (tTagetClumn[i]);
  	    tRIFeeRateTableClumnDefSchema.setTagetULClumn     (tTagetULClumn[i]);
  	    tRIFeeRateTableClumnDefSchema.setTagetDLClumn     (tTagetDLClumn[i]);
  	    
  	    mRIFeeRateTableClumnDefSet.add(tRIFeeRateTableClumnDefSchema);
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
 // {
  	tVData.addElement(globalInput);
  	tVData.addElement(mTransferData);
    tVData.addElement(mRIFeeRateTableDefSchema);
    tVData.addElement(mRIFeeRateTableTraceSchema);
    tVData.addElement(mRIFeeRateTableClumnDefSet);
   // mLRFeeRateDefUI.submitData(tVData,mOperateType);
 // }
  //catch(Exception ex)
  //{
  //  Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
  //  FlagStr = "Fail";
 // }
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRFeeRateDefUI"))
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
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	System.out.println("����ɹ�!");
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
      	Content = mDescType+""+"�ɹ���"+""+" "+"���ʱ��ţ�"+""+result;
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