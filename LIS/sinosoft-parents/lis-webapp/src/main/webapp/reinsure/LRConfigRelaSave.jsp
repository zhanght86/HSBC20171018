<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�ReComManageSave.jsp
//�����ܣ�
//�������ڣ�2006-08-17
//������  ���ű�
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
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.reinsure.*"%>
 <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>


<%
  System.out.println("��ʼִ��Saveҳ��");
  
  GlobalInput globalInput = new GlobalInput( );
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
  
  
  RIUnionAccumulateSet mRIUnionAccumulateSet = new RIUnionAccumulateSet();
    
  //LRConfigRelaSaveBL mLRConfigRelaSaveBL = new LRConfigRelaSaveBL();
  BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  String mState=request.getParameter("State");
  System.out.println("������������"+mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  System.out.println("��ʼ���л�ȡ���ݵĲ���������");
	
	String[] strNumber;
	String[] strAssociatedCode;

  
 
	 strNumber					= request.getParameterValues("RGridNo");
	 strAssociatedCode 	= request.getParameterValues("RGrid1");
	 
	
  
	if(strNumber!=null){	  
  	int tLength = strNumber.length;
    for(int i = 0 ;i < tLength ;i++){
      RIUnionAccumulateSchema tRIUnionAccumulateSchema = new RIUnionAccumulateSchema();
      
	  tRIUnionAccumulateSchema.setAccumulateDefNO(request.getParameter("AccumulateDefNO"));  
	  tRIUnionAccumulateSchema.setUnionAccNo(request.getParameter("UNIONACCNO"));
      tRIUnionAccumulateSchema.setAssociatedCode(strAssociatedCode[i]);
      tRIUnionAccumulateSchema.setState("");
      tRIUnionAccumulateSchema.setStandbyFlag("");
      mRIUnionAccumulateSet.add(tRIUnionAccumulateSchema);
    }
  }
  if(mOperateType.equals("INSERT")){
    mDescType = ""+"�����ۼƷ���"+"";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = ""+"�޸��ۼƷ���"+"";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = ""+"ɾ���ۼƷ���"+"";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = ""+"��ѯ�ۼƷ���"+"";
  }
	
  VData tVData = new VData();
 // try{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIUnionAccumulateSet);
   // mLRConfigRelaSaveBL.submitData(tVData,mOperateType);
 // }catch(Exception ex){
 //   Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
 //   FlagStr = "Fail";
  //}
if(!blBusinessDelegate.submitData(tVData,mOperateType,"LRAccRDUI"))
   {    
        if(blBusinessDelegate.getCErrors()!=null&&blBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"ʧ�ܣ�ԭ����:"+"" + blBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"����ʧ��"+"";
				   FlagStr = "Fail";				
				}
   }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String result="";
  if (FlagStr==""){
    tError = blBusinessDelegate.getCErrors();
    if (!tError.needDealError()){
    TransferData sTransferData = (TransferData)blBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
		Content = mDescType+""+"�ɹ���"+""+""+"�ۼƷ��ձ��룺"+""+result;
    	FlagStr = "Succ";
    }
    else{
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