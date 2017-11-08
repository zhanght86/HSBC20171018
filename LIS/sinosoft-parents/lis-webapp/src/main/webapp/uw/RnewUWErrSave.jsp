<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWErrSave.jsp
//程序功能：自动核保审阅功能
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  loggerDebug("RnewUWErrSave","----");
  //险种核保错误信息表
  //主键 ProposalNo,uwno,SerialNo
  LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
//被保人核保错误信息表
  //主键 uwno,SerialNo
  RnewIndUWErrorSet tRnewIndUWErrorSet = new RnewIndUWErrorSet();
  //合同核保错误信息表
  //主键 ProposalContNo,uwno,SerialNo
  LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
	//获取界面选择的数据
	String[] tChk = request.getParameterValues("InpUWErrGridChk"); 
	//合同号,被保人,险种编码,险种名称,核保信息,修改时间,是否审阅,投保单号,流水号,
	//核保序列号,合同险种标记,投保单号
	String[] tRiskFlag = request.getParameterValues("UWErrGrid11"); 
	String[] tSerialNo = request.getParameterValues("UWErrGrid9"); 
	String[] tUWNo = request.getParameterValues("UWErrGrid10"); 
	String[] tProposalNo = request.getParameterValues("UWErrGrid12"); 
	String[] tContno = request.getParameterValues("UWErrGrid1"); 
	for(int index=0;index<tChk.length;index++)
  	{
      loggerDebug("RnewUWErrSave","index:"+index+":"+tChk[index]);

      if(tChk[index].equals("1"))           
      {
           //选中
           //判断是合同层还是险种层
           String tFlag = tRiskFlag[index];
           if(tFlag.equals("risk"))
           {
             //险种核保信息
             LCUWErrorSchema tempLCUWErrorSchema = new LCUWErrorSchema();
             tempLCUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempLCUWErrorSchema.setUWNo(tUWNo[index]);
             tempLCUWErrorSchema.setProposalNo(tProposalNo[index]);
             tLCUWErrorSet.add(tempLCUWErrorSchema);
           }
           else if(tFlag.equals("insured"))
           {
             //被保人核保信息
             RnewIndUWErrorSchema tempRnewIndUWErrorSchema = new RnewIndUWErrorSchema();
             tempRnewIndUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempRnewIndUWErrorSchema.setUWNo(tUWNo[index]);
             tRnewIndUWErrorSet.add(tempRnewIndUWErrorSchema);
           }
           else
           {
             //合同核保信息
             LCCUWErrorSchema tempLCCUWErrorSchema = new LCCUWErrorSchema();
             tempLCCUWErrorSchema.setProposalContNo(tContno[index]);
             tempLCCUWErrorSchema.setSerialNo(tSerialNo[index]);
             tempLCCUWErrorSchema.setUWNo(tUWNo[index]);
             tLCCUWErrorSet.add(tempLCCUWErrorSchema);
           }
      }
      else
      {      
            loggerDebug("RnewUWErrSave","该行未被选中");
      }
   	}               



  // 准备传输数据 VData
   VData tVData = new VData();

	tVData.addElement(tLCUWErrorSet);
	tVData.addElement(tRnewIndUWErrorSet);
	tVData.addElement(tLCCUWErrorSet);

  // 数据传输
  Flag = "QUERY||LCUWERROR";
  /*
  RnewUWErrUI tRnewUWErrUI  = new RnewUWErrUI();
	if (!tRnewUWErrUI.submitData(tVData,Flag))
	{
      		Content = " 处理失败，原因是: " + tRnewUWErrUI.mErrors.getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
	} // end of if
  */
  String busiName="RnewUWErrUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(!tBusinessDelegate.submitData(tVData,Flag,busiName)){
	  Content = " 处理失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	  FlagStr = "Fail";
  }else
  {
		
  }// end of if
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " 处理成功! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 处理失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
loggerDebug("RnewUWErrSave","------end------");
loggerDebug("RnewUWErrSave",FlagStr);
loggerDebug("RnewUWErrSave",Content);
%>
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.releaseAutoUWButton();
</script>
</html>

