<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <!--SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT-->
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GroupPolSign.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupPolSignInit.jsp"%>
  <title>����ǩ�� </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupPolSignSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>�������ѯ����������</td>
		  </tr>
	  </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      <TR  class= common>
        <Input type="hidden" class= "common wid" name=PrtNo id="PrtNo">
        <Input type="hidden" class= "common wid" name=tt id="tt">
        <TD  class= title5>�������κ�</TD>
        <TD  class= input5>
          <Input class= "common wid" name=GrpContNo id="GrpContNo">
        </TD>
        <TD class=title5>�������</TD>
				<TD class=input5>
					<Input  style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="�������|code:comcode&notnull"
            onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');"
           ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');" ><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				</TD>
      </TR>  
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  <!-- <INPUT VALUE="��  ѯ" TYPE=button class= cssButton onclick="easyQueryClick();">  -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrp1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" TYPE=button class= cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class= cssButton91 onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class= cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" TYPE=button class= cssButton93  onclick="getLastPage();"> 					
  	</div>
  	<p>
      <a href="javascript:void(0)" class=button onclick="signGrpPol();">ǩ������</a>
      <!-- <INPUT VALUE="ǩ������" TYPE=button class= cssButton  onclick="signGrpPol();">  -->
      <!--INPUT VALUE="���߰�֪ͨ��" TYPE=button class= cssButton  onclick="signGrpPol();"> 
      <input value="�����ڽ���֪ͨ��" class=cssButton type=button onclick="SendFirstNotice();"--> 
  	</p>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
