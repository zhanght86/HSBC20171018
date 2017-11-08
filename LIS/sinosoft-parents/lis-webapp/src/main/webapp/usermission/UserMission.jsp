<!-- sunsheng 20110524 首页优化，显示菜单页面-->
<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%	
	String tContNo = "";
	String tFlag = request.getParameter("type");
  	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
%>
<script>	
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script><head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
        
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
		<SCRIPT src="../common/TreeView/MzTreeView.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<%@include file="UserMissionInit.jsp"%>
		<SCRIPT src="UserMission.js"></SCRIPT>
		<title>用户任务信息</title>
	</head>
	<body onLoad="initForm();">
		<div style="border: 0px solid #799AE1;">
			<center>
				<iframe src="../whatsnew.xml" name="msFrame" frameborder="no" scrolling="no"
					height="40%" width="100%" align="middle"></iframe>
			</center>
		</div>
		<form method=post name=fm id="fm" target="fraSubmit">	
			<table class= common>
    	       <tr>
    	          <td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divPersonalWorkPool);">
				   </td>
			      <td class= titleImg align= center>个人工作池统计信息</td>
		       </tr>
             </table>
             <div  id="divPersonalWorkPool">
             	<div class="maxbox">
                <table class=common >
			        <tr class=common>
				       <td class=title5>用户名</td>
				       <td  class= input5>
            	         <Input class= code name=UserName id="UserName" readonly=true >
                 	 </td> 
		          </tr>
		          <tr class=common>
				     <td class=title5>超期件</td>
				     <td  class= input5>
            	         <Input class= code name=ExtendA id="ExtendA" readonly=true >
                 	 </td> 
                 	 <td class=title5>紧急件</td>
				     <td  class= input5>
            	         <Input class= code name=EmergencyA id="EmergencyA"  readonly=true >
                 	 </td> 
		          </tr>
		           <tr class=common>
				     <td class=title5>加快件</td>
				     <td  class= input5>
            	         <Input class= code name = SpeedUpA id="SpeedUpA" readonly=true >
                 	 </td> 
                 	 <td class=title5>标准件</td>
				     <td  class= input5>
            	         <Input class= code name=StandardA id="StandardA" readonly=true >
                 	 </td> 
		          </tr>
		        </table>
		    </div>
		    </div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divOneselfGrpGrid);">
					</td>
					<td class=titleImg>
						个人任务信息
					</td>
				</tr>
			</table>
			<div id="divOneselfGrpGrid" style="display: ''" align=center>
			    <table class=common>
					<tr class=common>
						<td style=" text-align: left" colSpan=1>
							<span id="spanOneselfGrpGrid"> </span>
						<br></td>
					</tr>
				</table>
				<INPUT VALUE="首  页" class=cssButton90 TYPE=button
					onclick="turnPage.firstPage();">
				<INPUT VALUE="上一页" class=cssButton91 TYPE=button
					onclick="turnPage.previousPage();">
				<INPUT VALUE="下一页" class=cssButton92 TYPE=button
					onclick="turnPage.nextPage();">
				<INPUT VALUE="尾  页" class=cssButton93 TYPE=button
					onclick="turnPage.lastPage();">												
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divPublicGrpGrid);">
					</td>
					<td class=titleImg>
						公共任务信息
					</td>
				</tr>
			</table>
			<div id="divPublicGrpGrid" style="display: ''" align=center>
				<table class=common>
					<tr class=common>
						<td style=" text-align: left" colSpan=1>
							<span id="spanPublicGrpGrid"> </span>
						<br></td>
					</tr>
				</table>
				<INPUT VALUE="首  页" class=cssButton90 TYPE=button
					onclick="turnPage2.firstPage();">
				<INPUT VALUE="上一页" class=cssButton91 TYPE=button
					onclick="turnPage2.previousPage();">
				<INPUT VALUE="下一页" class=cssButton92 TYPE=button
					onclick="turnPage2.nextPage();">
				<INPUT VALUE="尾  页" class=cssButton93 TYPE=button
					onclick="turnPage2.lastPage();">
			</div>
			<br>
			<br>
			<br>
			<br>
		</form>								
	</body>
</html>