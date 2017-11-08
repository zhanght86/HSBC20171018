<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.sinosoft.service.*"%><%@page
	import="com.sinosoft.utility.*"%><%@page
	import="com.sinosoft.lis.pubfun.*"%><%@page import="java.io.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//注意，此处不要引用UsrCheck.jsp,里面有非法内容，下面自己通过代码检查
	try {
		if (session == null) {
			System.out.println("session is null");
			return;
		}
		GlobalInput _gi = (GlobalInput)session.getAttribute("GI");
		if (_gi == null) {
			session.setAttribute("GI",null);
			out.println("网页超时，请您重新登录");
			%>
			<script>
			try {
				top.window.location ="../indexlis.jsp";
			} catch (exception) {
				top.window.location ="../indexlis.jsp";
			}
			</script>
			<%
			return;
		}
		String _userCode = _gi.Operator;
		String _comCode =_gi.ComCode;
		String _manageCom = _gi.ManageCom;
		if ((_userCode.length()==0) || (_userCode.compareTo("")==0)||(_comCode.length()==0) || (_comCode.compareTo("")==0) ||(_manageCom.length()==0) || (_manageCom.compareTo("") == 0))
		{
			session.setAttribute("GI",null);
			String ContentErr = " 请您重新登录！";
			System.out.println(ContentErr);
			%>
			<script>
			try {
				top.window.location ="../indexlis.jsp";
			} catch (exception) {
				top.window.location ="../indexlis.jsp";
			}
			</script>
			<%
			return;
		}
	}
	catch(Exception exception)
	{
		String ContentErr = " exception:请您重新登录！";
		System.out.println(ContentErr);
		out.println("网页超时，请您重新登录");
		%>
		<script>
		try {
			top.window.location ="../indexlis.jsp";
		} catch (exception) {
			top.window.location ="../indexlis.jsp";
		}
		</script>
		<%
		return;
	}
	System.out.println("+++++++++++++++++++++++ Begin +++++++++++++++++++++++++++++++++++++++++");
	request.setCharacterEncoding("UTF-8");
	String Action = request.getParameter("Action");
	//loggerInfo("active",Action);
	System.out.println("Action:"+Action);
	String Para = request.getParameter("Para");
	//loggerInfo("active",Para);
	System.out.println("Para:"+Para);
	String ActionId = request.getParameter("ActionId");
	//loggerInfo("active",ActionId);
	System.out.println("ActionId:"+ActionId);
	//long t = System.currentTimeMillis();
	System.out.println("++++++++++++++++++++++++ End ++++++++++++++++++++++++++++++++++++");
	
	BusinessDelegate tBusinessDelegate; 
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("Action", Action);
	tTransferData.setNameAndValue("Para", Para);
	tTransferData.setNameAndValue("ActionId", ActionId);

    VData mVData = new VData();
    mVData.add(tTransferData); 
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(mVData, "", "activeUI"))
  	{
        loggerDebug("active", tBusinessDelegate.getCErrors().getFirstError());
	  }
	  else
	  {
		 System.out.println("+++++++++++ Result ++++++++++");
		 String a = (String)tBusinessDelegate.getResult().getObject(0);
         response.getWriter().println(a);
         System.out.println("----------- Result ----------");
    }        
%>