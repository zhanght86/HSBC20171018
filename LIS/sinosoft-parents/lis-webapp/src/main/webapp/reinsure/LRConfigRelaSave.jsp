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
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
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
  
  
  RIUnionAccumulateSet mRIUnionAccumulateSet = new RIUnionAccumulateSet();
    
  //LRConfigRelaSaveBL mLRConfigRelaSaveBL = new LRConfigRelaSaveBL();
  BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  String mState=request.getParameter("State");
  System.out.println("操作的类型是"+mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  System.out.println("开始进行获取数据的操作！！！");
	
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
 // try{
  	tVData.addElement(globalInput);
    tVData.addElement(mRIUnionAccumulateSet);
   // mLRConfigRelaSaveBL.submitData(tVData,mOperateType);
 // }catch(Exception ex){
 //   Content = mDescType+"失败，原因是:" + ex.toString();
 //   FlagStr = "Fail";
  //}
if(!blBusinessDelegate.submitData(tVData,mOperateType,"LRAccRDUI"))
   {    
        if(blBusinessDelegate.getCErrors()!=null&&blBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"失败，原因是:"+"" + blBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"保存失败"+"";
				   FlagStr = "Fail";				
				}
   }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  String result="";
  if (FlagStr==""){
    tError = blBusinessDelegate.getCErrors();
    if (!tError.needDealError()){
    TransferData sTransferData = (TransferData)blBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		result = (String)sTransferData.getValueByName("String");
		Content = mDescType+""+"成功，"+""+""+"累计风险编码："+""+result;
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>");
</script>
</html>