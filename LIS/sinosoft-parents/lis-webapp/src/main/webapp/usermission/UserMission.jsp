<!-- sunsheng 20110524 ��ҳ�Ż�����ʾ�˵�ҳ��-->
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
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
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
		<title>�û�������Ϣ</title>
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
			      <td class= titleImg align= center>���˹�����ͳ����Ϣ</td>
		       </tr>
             </table>
             <div  id="divPersonalWorkPool">
             	<div class="maxbox">
                <table class=common >
			        <tr class=common>
				       <td class=title5>�û���</td>
				       <td  class= input5>
            	         <Input class= code name=UserName id="UserName" readonly=true >
                 	 </td> 
		          </tr>
		          <tr class=common>
				     <td class=title5>���ڼ�</td>
				     <td  class= input5>
            	         <Input class= code name=ExtendA id="ExtendA" readonly=true >
                 	 </td> 
                 	 <td class=title5>������</td>
				     <td  class= input5>
            	         <Input class= code name=EmergencyA id="EmergencyA"  readonly=true >
                 	 </td> 
		          </tr>
		           <tr class=common>
				     <td class=title5>�ӿ��</td>
				     <td  class= input5>
            	         <Input class= code name = SpeedUpA id="SpeedUpA" readonly=true >
                 	 </td> 
                 	 <td class=title5>��׼��</td>
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
						����������Ϣ
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
				<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
					onclick="turnPage.firstPage();">
				<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button
					onclick="turnPage.previousPage();">
				<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button
					onclick="turnPage.nextPage();">
				<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button
					onclick="turnPage.lastPage();">												
			</div>
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor: hand;"
							OnClick="showPage(this,divPublicGrpGrid);">
					</td>
					<td class=titleImg>
						����������Ϣ
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
				<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
					onclick="turnPage2.firstPage();">
				<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button
					onclick="turnPage2.previousPage();">
				<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button
					onclick="turnPage2.nextPage();">
				<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button
					onclick="turnPage2.lastPage();">
			</div>
			<br>
			<br>
			<br>
			<br>
		</form>								
	</body>
</html>