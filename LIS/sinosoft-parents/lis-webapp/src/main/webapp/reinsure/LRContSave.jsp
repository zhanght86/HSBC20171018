<%@include file="/i18n/language.jsp"%>
<%
//�������ƣ�LRContSave.jsp
//�����ܣ�
//�������ڣ�2007-02-28
//������  ��zhangbin
//���¼�¼��  ������: zhangbin ��������  2008-4-14   ����ԭ��/����
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
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
  TransferData tTransferData = new TransferData();
  RIBarGainInfoSchema mRIBarGainInfoSchema = new RIBarGainInfoSchema();
 	RIBarGainSignerSet mRIBarGainSignerSet = new RIBarGainSignerSet();
  RICalFactorValueSet mRICalFactorValueSet = new RICalFactorValueSet();  
  
  //LRContManageUI mLRContManageUI = new LRContManageUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  String mModType			= request.getParameter("ModType");
  String mModRIContNo	= request.getParameter("ModRIContNo");
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String ContentNO = "";	
  String mDescType = "";	//��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  mRIBarGainInfoSchema.setRIContNo       (	request.getParameter("RIContNo")   				);
  mRIBarGainInfoSchema.setRIContName     (  request.getParameter("RIContName") 				);
  mRIBarGainInfoSchema.setContType       (	request.getParameter("ContType")   				);
  mRIBarGainInfoSchema.setRiskType       (	request.getParameter("RiskType")   				);  
  mRIBarGainInfoSchema.setReInsuranceType(	request.getParameter("ReInsuranceType")   );
  mRIBarGainInfoSchema.setCValiDate      (	request.getParameter("RValidate")  				);
  mRIBarGainInfoSchema.setEndDate        (	request.getParameter("RInvalidate")				);
  mRIBarGainInfoSchema.setRISignDate     (  request.getParameter("RSignDate")  				);
  mRIBarGainInfoSchema.setState(  request.getParameter("ContState")  				);
  mRIBarGainInfoSchema.setGIType(  request.getParameter("BillingCycle")  ); //�˵�����
  
  String[] StrNum        = request.getParameterValues("SignerGridNo");
  String[] StrCompanyNo  = request.getParameterValues("SignerGrid2"	);
  String[] StrConSigner  = request.getParameterValues("SignerGrid3"	);
  String[] StrDuty       = request.getParameterValues("SignerGrid4"	);
  String[] StrRelaTel    = request.getParameterValues("SignerGrid5"	);
  String[] StrMobileNo   = request.getParameterValues("SignerGrid6"	);
  String[] StrFaxNo      = request.getParameterValues("SignerGrid7"	);
  String[] StrEmail      = request.getParameterValues("SignerGrid8"	);
  
  PubFun tPubFun = new PubFun();
  String currentDate = tPubFun.getCurrentDate();
  String currentTime = tPubFun.getCurrentTime();
  if(StrNum!=null){ 
  	for(int i=0;i<StrNum.length;i++){
  		RIBarGainSignerSchema	tRIBarGainSignerSchema=new RIBarGainSignerSchema();
  	  tRIBarGainSignerSchema.setRIContNo (request.getParameter("RIContNo"));
  	  
  	  tRIBarGainSignerSchema.setReComCode(StrCompanyNo[i]				);
  	  tRIBarGainSignerSchema.setRelaName (StrConSigner[i]				);
  	  tRIBarGainSignerSchema.setDuty     (StrDuty [i]						);
  	  tRIBarGainSignerSchema.setRelaTel  (StrRelaTel[i]					);
  	  tRIBarGainSignerSchema.setMobileTel(StrMobileNo[i]				);
  	  tRIBarGainSignerSchema.setFaxNo    (StrFaxNo[i]						);
  	  tRIBarGainSignerSchema.setEmail    (StrEmail[i]						);
      tRIBarGainSignerSchema.setOperator (globalInput.Operator	);
      
      mRIBarGainSignerSet.add(tRIBarGainSignerSchema);
  	}
  }
System.out.println("aaaaaaaaaa: "+mModType);  
	String[] strNum      =request.getParameterValues("FactorGridNo");    
	String[] factorName  =request.getParameterValues("FactorGrid1"); 
	String[] factorCode  =request.getParameterValues("FactorGrid2"); 
	String[] factorValue =request.getParameterValues("FactorGrid3");
	
	if(strNum!=null){
		for(int i=0;i<strNum.length;i++){
			RICalFactorValueSchema tRICalFactorValueSchema=new RICalFactorValueSchema();
		  tRICalFactorValueSchema.setReContCode(request.getParameter("RIContNo"));
		  tRICalFactorValueSchema.setRIPreceptNo("S000000000");
		  tRICalFactorValueSchema.setFactorCode(factorCode[i]);
		  tRICalFactorValueSchema.setFactorName(factorName[i]);
		  tRICalFactorValueSchema.setFactorValue(factorValue[i]); 
		  tRICalFactorValueSchema.setFactorClass("01");
		  
		  mRICalFactorValueSet.add(tRICalFactorValueSchema);                          
		}                                                                               
	}
	
  if(mOperateType.equals("INSERT"))
  {
    mDescType = ""+"�����ٱ���ͬ"+"";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = ""+"�޸��ٱ���ͬ��Ϣ"+"";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = ""+"ɾ���ٱ���ͬ"+"";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = ""+"��ѯ�ٱ���ͬ"+"";
  }
  VData tVData = new VData();
 // try
  //{
  	tTransferData.setNameAndValue("ModType",mModType);
  	tTransferData.setNameAndValue("ModRIContNo",mModRIContNo);
  	tVData.addElement(tTransferData);
  	tVData.addElement(globalInput);
    tVData.addElement(mRIBarGainInfoSchema);
    tVData.addElement(mRIBarGainSignerSet);
    tVData.addElement(mRICalFactorValueSet);
    
   // mLRContManageUI.submitData(tVData,mOperateType);
  //}
  //catch(Exception ex)
  //{
  //  Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
  //  FlagStr = "Fail";
  //}
  if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRContManageUI"))
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
  String result="";
  if (FlagStr=="")
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	System.out.println("����ɹ�!");
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
      Content = mDescType+""+"�ɹ���"+""+" "+"��ͬ��ţ�"+""+result;
    	ContentNO = result;
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