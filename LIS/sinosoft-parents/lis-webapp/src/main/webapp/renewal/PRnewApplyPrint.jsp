<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page	contentType="text/html;charset=GBK"	%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
	GlobalInput	tGI	= new GlobalInput();
	tGI	= (GlobalInput)session.getValue("GI");
%>
<script>
	var	managecom =	"<%=tGI.ManageCom%>"; //��¼�������
	var	comcode	= "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type"	content="text/html;	charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="PRnewApplyPrint.js"></SCRIPT>
  <LINK	href="../common/css/Project.css" rel=stylesheet	type=text/css>
  <LINK	href="../common/css/Project3.css" rel=stylesheet	type=text/css>
  <LINK	href="../common/css/mulLine.css" rel=stylesheet	type=text/css>
  <%@include file="PRnewApplyPrintInit.jsp"%>
  <title>��ӡ��������֪ͨ��	</title>   
</head>
<body  onload="initForm();"	>
  <form	action="./PRnewApplyPrintQuery.jsp"	method=post	name=fm	 id="fm" target="fraSubmit">
	<!-- Ͷ������Ϣ����	-->
	<table class= common border=0 width=100%>
		<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td	class= titleImg	align= center>�������ѯ������</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
	<table	class= common align=center>
		<TR	 class=	common>
		  <TR  class= common>
		  <TD  class= title5>  ������   </TD>
		  <TD  class= input5>  <Input class="common wid" name=ContNo >	</TD>
		  <TD  class= title5>   �������	 </TD>
		  <TD  class= input5>  <Input class="common wid" name=ManageCom  id=ManageCom 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return	showCodeList('station',[this]);"

          onDblClick="return	showCodeList('station',[this]);"
           onKeyUp="return showCodeListKey('station',[this]);">  </TD>   
		 </TR> 
		 <TR  class= common>
		 <TD  class= title5>	�����˱��� </TD>
		  <TD  class= input5>  <Input class="common wid" name=AgentCode >	</TD>
		 <TD  class= title5>	��������� </TD>
		  <TD  class= input5> <Input class="common wid" name=AgentGroup ></TD>
           </TR> 
            <TR  class= common>
		  <TD  class= title5> չҵ���� </TD>
		  <TD class="input5"	nowrap="true">
			<Input class="common wid" name="BranchGroup">
			<input name="btnQueryBranch" class=cssButton	type="button" value="�� ѯ"	onclick="queryBranch()"	style="width:60 ">
		  </TD>	 
		</TR> 
		  
	</table>
    </div></div>
		  <INPUT VALUE="��  ѯ" class= cssButton	TYPE=button	onclick="easyQueryClick();"> 
         <!--<a href="javascript:void(0);" class="button"onclick="easyQueryClick();">��   ѯ</a>-->
  </form>
  <form	action="./PRnewApplyPrintSave.jsp" method=post name=fmSave target="fraSubmit">
	<table>
		<tr>
			<td	class=common>
				<IMG  src= "../common/images/butExpand.gif"	style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
			</td>
			<td	class= titleImg>
				 ���ڴ���֪ͨ����Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''">
		<table	class= common>
			<tr	 class=	common>
				<td	text-align:	left colSpan=1>
					<span id="spanPolGrid" >
					</span>	
				</td>
			</tr>
		</table>
			<INPUT VALUE="��  ҳ"	class= cssButton90 TYPE=button onClick="getFirstPage();"> 
	  <INPUT VALUE="��һҳ"	class= cssButton91 TYPE=button onClick="getPreviousPage();">						
	  <INPUT VALUE="��һҳ"	class= cssButton92 TYPE=button onClick="getNextPage();">	
	  <INPUT VALUE="β  ҳ" class= cssButton93	TYPE=button	onclick="getLastPage();">					
	</div>
	<p>
	  <INPUT VALUE="��ӡ��������֪ͨ��"	class= cssButton TYPE=button onClick="printPol();"> 
	</p>
     <!--<a href="javascript:void(0);" class="button"onClick="printPol();">��ӡ��������֪ͨ��</a>-->
     <br><br><br><br>
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="PrtSeq" name="PrtSeq">
  </form>
  <span	id="spanCode"  style="display: none; position:absolute;	slategray"></span>
</body>
</html>	
