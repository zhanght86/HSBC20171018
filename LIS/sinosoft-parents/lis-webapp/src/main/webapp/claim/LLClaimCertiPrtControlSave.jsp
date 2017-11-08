 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLClaimCertiPrtControlSave.jsp
//文件功能：理赔单证打印控制
//创建日期：2005-10-15  创建人 ：                   
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("Operate");
    if(tG == null)
    {
        //loggerDebug("LLClaimCertiPrtControlSave","登录信息没有获取!!!");
        return;
     } 
     //接收MulLine表格中数据集合   
     LOPRTManagerSet tLOPRTManagerSet=new LOPRTManagerSet(); //打印管理表
     String tGridNo[] = request.getParameterValues("ClaimCertiGridNO");  //得到序号列的所有值
     String tGrid1[] = request.getParameterValues("ClaimCertiGrid1"); //印刷流水号
     
     String tChk[] = request.getParameterValues("InpClaimCertiGridChk");; //参数格式=” Inp+MulLine对象名+Chk”
    if (tChk!=null&&tChk.length>0)
    {
	    for(int index=0;index<tChk.length;index++)
	    {
	      if(tChk[index].equals("1")) 
	      {          
              LOPRTManagerSchema tLOPRTManagerSchema=new LOPRTManagerSchema();
	          tLOPRTManagerSchema.setPrtSeq(tGrid1[index]); //单证代码
	          tLOPRTManagerSet.add(tLOPRTManagerSchema);              
	      }
	    } 	        
    }
         
    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tLOPRTManagerSet);

//        LLClaimCertiPrtControlUI tLLClaimCertiPrtControlUI = new LLClaimCertiPrtControlUI();
//       if (tLLClaimCertiPrtControlUI.submitData(tVData,tOperate) == false)
//        {
//			Content = "信息提交错误，原因是: " + tLLClaimCertiPrtControlUI.mErrors.getError(0).errorMessage;
//			FlagStr = "FAIL";
//        } 
String busiName="LLClaimCertiPrtControlUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "信息提交错误，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
		}
		else
		{
				   Content = "信息提交错误";
				   FlagStr = "Fail";				
		}
   }

        else 
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>    
