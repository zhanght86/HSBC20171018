<%@include file="/i18n/language.jsp"%>
<%
//程序名称：ReComManageSave.jsp
//程序功能：
//创建日期：2006-08-17
//创建人  ：张斌
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
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
  System.out.println("开始执行Save页面");
  
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
  System.out.println("操作的类型是"+mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  System.out.println("开始进行获取数据的操作！！！");
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
    mDescType = ""+"新增累计方案"+"";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = ""+"修改累计方案"+"";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = ""+"删除累计方案"+"";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = ""+"查询累计方案"+"";
  }
	
  VData tVData = new VData();
  //try{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIAccumulateDefSchema);
    tVData.addElement(mRIAccumulateRDCodeSet);
   // uiBusinessDelegate.submitData(tVData,mOperateType,"LRAccRDUI");
  //}catch(Exception ex){
   // Content = mDescType+"失败，原因是:" + ex.toString();
    //FlagStr = "Fail";
  //}
if(!uiBusinessDelegate.submitData(tVData,mOperateType,"LRAccRDUI"))
   {    
        if(uiBusinessDelegate.getCErrors()!=null&&uiBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"失败，原因是:"+"" + uiBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"保存失败"+"";
				   FlagStr = "Fail";				
				}
   }

  String tAccumulateDefNO = "";
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr==""){
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		tAccumulateDefNO = (String)sTransferData.getValueByName("AccumulateDefNO");
		Content = mDescType+""+"成功，"+""+""+"累计风险编码："+""+tAccumulateDefNO;
    	FlagStr = "Succ";
    }
    else{
    	Content = mDescType+" "+"失败，原因是:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tAccumulateDefNO%>");
</script>
</html>