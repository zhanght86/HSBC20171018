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
  
  RIAccumulateDefSchema mRIAccumulateDefSchema = new RIAccumulateDefSchema();
  RIAccumulateRDCodeSet mRIAccumulateRDCodeSet = new RIAccumulateRDCodeSet();
    
  //LRAccRDUI mLRAccRDUI = new LRAccRDUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  String mDeTailType=request.getParameter("DeTailType");
  String mState=request.getParameter("State");
  System.out.println("������������"+mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  System.out.println("��ʼ���л�ȡ���ݵĲ���������");
  mRIAccumulateDefSchema.setAccumulateDefNO(	 request.getParameter("AccumulateDefNO"));
  mRIAccumulateDefSchema.setAccumulateDefName( request.getParameter("AccumulateDefName"));
  mRIAccumulateDefSchema.setDeTailFlag(	 			 request.getParameter("DeTailFlag"));
  mRIAccumulateDefSchema.setAccumulateMode(	   request.getParameter("AccumulateMode"));
  mRIAccumulateDefSchema.setRiskAmntFlag(	     request.getParameter("RiskAmntFlag"));
  mRIAccumulateDefSchema.setState(						 request.getParameter("State"));
  mRIAccumulateDefSchema.setStandbyFlag(request.getParameter("StandbyFlag"));
	
	String[] strNumber;
	String[] strAssociatedCode;
  String[] strAssociatedName;
  String[] strNoAcc;
  
  if(mDeTailType.equals("RISK")){
	 strNumber					= request.getParameterValues("RelateGridNo");
	 strAssociatedCode 	= request.getParameterValues("RelateGrid1");
	 strAssociatedName	= request.getParameterValues("RelateGrid2");
	 strNoAcc						= request.getParameterValues("RelateGrid4");
	}else{
		strNumber					= request.getParameterValues("DutyGridNo");
	  strAssociatedCode = request.getParameterValues("DutyGrid1");
	  strAssociatedName = request.getParameterValues("DutyGrid2");
	  strNoAcc				 	= request.getParameterValues("DutyGrid4");
	}
  
	if(strNumber!=null){	  
  	int tLength = strNumber.length;
    for(int i = 0 ;i < tLength ;i++){
      RIAccumulateRDCodeSchema tRIAccumulateRDCodeSchema = new RIAccumulateRDCodeSchema();
      
			tRIAccumulateRDCodeSchema.setAccumulateDefNO(request.getParameter("AccumulateDefNO"));  
      tRIAccumulateRDCodeSchema.setAssociatedCode(strAssociatedCode[i]);
      tRIAccumulateRDCodeSchema.setAssociatedName(strAssociatedName[i]);
      tRIAccumulateRDCodeSchema.setStandbyFlag(strNoAcc[i]);
      mRIAccumulateRDCodeSet.add(tRIAccumulateRDCodeSchema);
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
  //try{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIAccumulateDefSchema);
    tVData.addElement(mRIAccumulateRDCodeSet);
   // uiBusinessDelegate.submitData(tVData,mOperateType,"LRAccRDUI");
  //}catch(Exception ex){
   // Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    //FlagStr = "Fail";
  //}
if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRAccRDUI"))
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

  String tAccumulateDefNO = "";
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr==""){
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		tAccumulateDefNO = (String)sTransferData.getValueByName("AccumulateDefNO");
		Content = mDescType+""+"�ɹ���"+""+""+"�ۼƷ��ձ��룺"+""+tAccumulateDefNO;
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tAccumulateDefNO%>");
</script>
</html>