<%
//Name: LLLcContSuspendSave.jsp
//Function：保单挂起信息提交
//Date：
//Author ：yuejinwei
//Modify : zhoulei
//Modify date : 2005-11-19 14:18
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
loggerDebug("LLLcContSuspendSave","------LLLcContSuspendSave.jsp start------");
//输入参数
LCContHangUpStateSet tLCContHangUpStateSet = new LCContHangUpStateSet(); //个人保单信息集合
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
//LLLcContSuspendUI tLLLcContSuspendUI = new LLLcContSuspendUI();
String busiName="LLLcContSuspendUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLLcContSuspendSave","页面失效,请重新登陆");
}
else //页面有效
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //接收赔案不相关保单MulLine表格中数据集合
    String tGridNo[] = request.getParameterValues("LLLcContSuspendGridNo");  //得到序号列的所有值
    String tGrid1[] = request.getParameterValues("LLLcContSuspendGrid2"); //得到第2列，合同号
    String tGrid9[] = request.getParameterValues("LLLcContSuspendGrid9"); //得到第9列，保全挂起状态
    String tGrid10[] = request.getParameterValues("LLLcContSuspendGrid10"); //得到第10列，续期挂起状态

    //接收赔案相关保单MulLine表格中数据集合
    String tIGridNo[] = request.getParameterValues("LLLcContInGridNo");  //得到序号列的所有值
    String tIGrid1[] = request.getParameterValues("LLLcContInGrid2"); //得到第2列，合同号
    String tIGrid9[] = request.getParameterValues("LLLcContInGrid9"); //得到第9列，保全挂起状态
    String tIGrid10[] = request.getParameterValues("LLLcContInGrid10"); //得到第10列，续期挂起状态

    int Count = 0;
    if (tGridNo != null)
    {
        Count = tGridNo.length; //得到接受到的赔案不相关保单记录数
    }
	for(int index=0;index < Count;index++)
    {
       LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema(); //个人保单表
       tLCContHangUpStateSchema.setContNo(tGrid1[index]); //合同号
       tLCContHangUpStateSchema.setInsuredNo("000000"); //被保险人号码
       tLCContHangUpStateSchema.setPolNo("000000"); //保单险种号
       tLCContHangUpStateSchema.setPosFlag(tGrid9[index]); //保全挂起状态
       tLCContHangUpStateSchema.setRNFlag(tGrid10[index]); //续期挂起状态
       tLCContHangUpStateSet.add(tLCContHangUpStateSchema);
    }

    int CountI = 0;
    if (tIGridNo != null)
    {
        CountI = tIGridNo.length; //得到接受到的赔案相关保单记录数
    }
	for(int index=0;index < CountI;index++)
    {
       LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema(); //个人保单表
       tLCContHangUpStateSchema.setContNo(tIGrid1[index]); //合同号
       tLCContHangUpStateSchema.setInsuredNo("000000"); //被保险人号码
       tLCContHangUpStateSchema.setPolNo("000000"); //保单险种号
       tLCContHangUpStateSchema.setPosFlag(tIGrid9[index]); //保全挂起状态
       tLCContHangUpStateSchema.setRNFlag(tIGrid10[index]); //续期挂起状态
       tLCContHangUpStateSet.add(tLCContHangUpStateSchema);
    }
  
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));

    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(tLCContHangUpStateSet);
        tVData.add(mTransferData);

        //数据传输
//	      if (!tLLLcContSuspendUI.submitData(tVData,""))
//	      {
//            Content = " 数据提交tLLLcContSuspendUI失败，原因是: " + tLLLcContSuspendUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//	      }
		 if(!tBusinessDelegate.submitData(tVData,"",busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "数据提交tLLLcContSuspendUI失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "数据提交tLLLcContSuspendUI失败";
						   FlagStr = "Fail";				
				}
		   }


	      else
	      {
	    	Content = " 数据提交成功! ";
    	    FlagStr = "Succ";
          }
    }
    catch(Exception ex)
    {
        Content = "数据提交tLLLcContSuspendUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail")
    {
        //tError = tLLLcContSuspendUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {
          	Content = " 数据提交成功! ";
    	    FlagStr = "Succ";
        }
        else
        {
      	    Content = " 数据提交LLLcContSuspendUI失败，原因是:" + tError.getFirstError();
    	    FlagStr = "Fail";
        }
    }
    loggerDebug("LLLcContSuspendSave","------LLLcContSuspendSave.jsp end------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
