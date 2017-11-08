<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ApplyRecallChk.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	  //输出参数
	  CErrors tError = null;
	  String FlagStr = "Fail";
		String Content="";
		GlobalInput tG = new GlobalInput(); 
		tG=(GlobalInput)session.getAttribute("GI"); 
		
		String tEventType		= request.getParameter("EventType"); 
		String tEventNo 		= request.getParameter("EventNo"); 
		String tRiskCode		= request.getParameter("RiskCode"); 
		String tDutyCode  	= request.getParameter("DutyCode");
		String tContNo  		= request.getParameter("ContNo");   
		String tInsuredNo 	= request.getParameter("InsuredNo");  
		String tRIContNo		= request.getParameter("RIContNo"); 
		String tRIPreceptNo	= request.getParameter("RIPreceptNo"); 
		
	  String[] StrNum				 =request.getParameterValues("TempCessInputGridNo");
	  String[] riComNo		 	 =request.getParameterValues("TempCessInputGrid1");
	  String[] AmountChang	 =request.getParameterValues("TempCessInputGrid4");
	  String[] PremChang     =request.getParameterValues("TempCessInputGrid5");
	  String[] CommChang     =request.getParameterValues("TempCessInputGrid6");
	  String[] CurentAmnt    =request.getParameterValues("TempCessInputGrid7");
	  String[] CessionAmount =request.getParameterValues("TempCessInputGrid8");
	  String[] CessionRate   =request.getParameterValues("TempCessInputGrid9");
	  String[] ReturnPay     =request.getParameterValues("TempCessInputGrid10");
	  
	  for(int i=0;i<StrNum.length;i++){
	  	System.out.println("  a再保公司： "+riComNo[i]);
	  }
	  
	  ReinsureRecordTraceSet tReinsureRecordTraceSet = new ReinsureRecordTraceSet();
	  ReinsureRecordTraceSchema tReinsureRecordTraceSchema;
	  for(int i=0;i<StrNum.length;i++){
	  	tReinsureRecordTraceSchema=new ReinsureRecordTraceSchema();
	  	tReinsureRecordTraceSchema.setAccumulateDefNO(riComNo[i]);
	  	tReinsureRecordTraceSchema.setOtherNo("ABC");
	  	tReinsureRecordTraceSchema.setContNo(tContNo);
	  	tReinsureRecordTraceSchema.setAccumulateCode(tDutyCode);
	  	tReinsureRecordTraceSchema.setRIPreceptNo(tRIPreceptNo);
	  	tReinsureRecordTraceSchema.setAreaID("ABC");
	  	tReinsureRecordTraceSchema.setEventNo(tEventNo);   
			tReinsureRecordTraceSchema.setAmountChang(AmountChang[i]);	
			tReinsureRecordTraceSchema.setPremChang(PremChang[i]);	
			tReinsureRecordTraceSchema.setCommChang(CommChang[i]);	
			tReinsureRecordTraceSchema.setCurentAmnt(CurentAmnt[i]);	
			tReinsureRecordTraceSchema.setCessionAmount(CessionAmount[i]);	
			tReinsureRecordTraceSchema.setCessionRate(CessionRate[i]);	
			tReinsureRecordTraceSchema.setReturnPay(ReturnPay[i]);	
	  	tReinsureRecordTraceSchema.setReinsureType("1");
	   	tReinsureRecordTraceSet.add(tReinsureRecordTraceSchema);
	  }
	  
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EventType"				,tEventType);
		tTransferData.setNameAndValue("EventNo" 				,tEventNo );
		tTransferData.setNameAndValue("RiskCode"				,tRiskCode);
		tTransferData.setNameAndValue("DutyCode"  			,tDutyCode);
		tTransferData.setNameAndValue("ContNo"  				,tContNo);
		tTransferData.setNameAndValue("InsuredNo" 			,tInsuredNo);
		tTransferData.setNameAndValue("RIPreceptNo"			,tRIPreceptNo);
		//// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tReinsureRecordTraceSet);
		tVData.add(tG);
		
		//// 数据传输 
		LRTempCessEventDealBL tLRTempCessEventDealBL = new LRTempCessEventDealBL(); 
		if (tLRTempCessEventDealBL.submitData(tVData,"") == false){ 
			Content = " "+"保存失败，原因是:"+" " + tLRTempCessEventDealBL.mErrors.getError(0).errorMessage; 
			FlagStr = "Fail";
		}
    else{
		  Content = " "+"保存成功!"+" ";
		  FlagStr = "Succ";
		}

%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
