
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.changePwd.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.service.*" %>           
<html>
<body>
<% 
    String FlagStr = "false";
  	GlobalInput tG1 = new GlobalInput();
	tG1=(GlobalInput)session.getValue("GI");
	String usercode = tG1.Operator;
	loggerDebug("changPwd","usercode" + usercode);
	String CharFlag = "false";
	String NumFlag = "false";
	String OtherFlag = "true";
	String Content = "";
	// zy 2009-04-10 17:17 系统密码必须是数据和字母组合而成
	String tnewPwd = request.getParameter("newPwd");
	for(int i = 0;i < tnewPwd.length();i++)
	{
		int j = tnewPwd.charAt(i);
		if(j >= 48 &&j <= 57)
		{
			CharFlag = "true";
		}else if (j >= 65 &&j <= 90||j >= 97 &&j <= 122)
		{
			NumFlag = "true";
		}else
		{
			OtherFlag = "false";
		}
	}
	loggerDebug("changPwd","CharFlag" + CharFlag);
	loggerDebug("changPwd","NumFlag" + NumFlag);
	if(CharFlag == "false"||NumFlag == "false"||OtherFlag == "false")
	{
		FlagStr = "false";
		Content = "密码不是只包含字母和数字的组合，请重新输入！";
	}else{	
	//LisIDEA tLisIdea = new LisIDEA();
	 String busiName="LDChangePwdUI";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	String oldpwd = request.getParameter("oldPwd");
	String newpwd = request.getParameter("newPwd");
	LDUserSchema oldUserSchema = new LDUserSchema();
	LDUserSchema newUserSchema = new LDUserSchema();
	
	oldUserSchema.setUserCode(usercode);
	oldUserSchema.setPassword(oldpwd);
	loggerDebug("changPwd","oldpwd:" + oldpwd);
	
	newUserSchema.setUserCode(usercode);
	newUserSchema.setPassword(newpwd);
    loggerDebug("changPwd","newpwd:" + newpwd);
    
    
    VData tData = new VData();
    tData.add(oldUserSchema);
    tData.add(newUserSchema);
   // LDChangePwdUI tLDChangePwdUI = new LDChangePwdUI();
   // if (tLDChangePwdUI.submitData(tData,"changePwd")) {
	 if( tBusinessDelegate.submitData(tData, "changePwd",busiName)){  
        FlagStr = "true";
        Content = "密码修改成功！";
    } else {
        FlagStr = "false";
        Content = "密码修改失败，原因是:"+tBusinessDelegate.getCErrors().getFirstError();;
    }
  }    
%>  

<script>
	//parent.fraInterface.afterSubmit("<%=FlagStr%>");
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
	
</body>

</html>
