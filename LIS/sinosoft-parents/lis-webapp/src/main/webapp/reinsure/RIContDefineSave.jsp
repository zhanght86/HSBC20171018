<%@include file="/i18n/language.jsp"%>
<%
//程序名称：RIContDefineSave.jsp
//程序功能：
//创建日期：2007-02-28
//创建人  ：zhangbin
//更新记录：  更新人: zhangbin 更新日期  2008-4-14   更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page contentType="text/html;charset=GBK" %>

<%
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
  TransferData tTransferData = new TransferData();
  RIBarGainInfoSchema mRIBarGainInfoSchema = new RIBarGainInfoSchema();
 	RIBarGainSignerSet mRIBarGainSignerSet = new RIBarGainSignerSet();
  RICalFactorValueSet mRICalFactorValueSet = new RICalFactorValueSet();  
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  String mModType			= request.getParameter("ModType");
  String mModRIContNo	= request.getParameter("ModRIContNo");
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String ContentNO = "";	
  String mDescType = "";	//将操作标志的英文转换成汉字的形式
  
  mRIBarGainInfoSchema.setRIContNo       (	request.getParameter("RIContNo")   				);
  mRIBarGainInfoSchema.setRIContName     (  request.getParameter("RIContName") 				);
  mRIBarGainInfoSchema.setContType       (	request.getParameter("ReCountType")   			);
  mRIBarGainInfoSchema.setRiskType       (	request.getParameter("RiskType")   				);  
  //mRIBarGainInfoSchema.setReInsuranceType(	request.getParameter("ReInsuranceType")   );
  mRIBarGainInfoSchema.setCValiDate      (	request.getParameter("RValidate")  				);
  mRIBarGainInfoSchema.setEndDate        (	request.getParameter("RInvalidate")				);
  mRIBarGainInfoSchema.setRISignDate     (  request.getParameter("RSignDate")  				);
  mRIBarGainInfoSchema.setState(  request.getParameter("ContState")  				);
  mRIBarGainInfoSchema.setGIType(  request.getParameter("BillingCycle")  );//账单周期
  mRIBarGainInfoSchema.setMatchMode(request.getParameter("UseType"));
  
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
    mDescType = ""+"新增再保合同"+"";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = ""+"修改再保合同信息"+"";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = ""+"删除再保合同"+"";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = ""+"查询再保合同"+"";
  }
  VData tVData = new VData();
  	tTransferData.setNameAndValue("ModType",mModType);
  	tTransferData.setNameAndValue("ModRIContNo",mModRIContNo);
  	tVData.addElement(tTransferData);
  	tVData.addElement(globalInput);
    tVData.addElement(mRIBarGainInfoSchema);
    tVData.addElement(mRIBarGainSignerSet);
    tVData.addElement(mRICalFactorValueSet);
    
	BusinessDelegate uiBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	if (!uiBusinessDelegate.submitData(tVData, mOperateType,
			"LRContManageUI")) {
		if (uiBusinessDelegate.getCErrors() != null
				&& uiBusinessDelegate.getCErrors().getErrorCount() > 0) {
			Content = ""+"保存失败，原因是："+""
					+ uiBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		} else {
			Content = ""+"保存失败"+"";
			FlagStr = "Fail";
		}
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	String result = "";
	if (FlagStr == "") {
		tError = uiBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			TransferData sTransferData = (TransferData) uiBusinessDelegate
					.getResult().getObjectByObjectName("TransferData",
							0);
			result = (String) sTransferData.getValueByName("ContNo");
			Content = mDescType + ""+"成功，"+"" + " "+"合同编号："+"" + result;
			FlagStr = "Succ";
		} else {
			Content = mDescType + " "+"失败，原因是:"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>
