<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<html> 
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
%>
<script>
  var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
  var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ProposalComPrint_IDGF.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalComPrintInit_IDGF.jsp"%>
  <title>打印个人保单 </title>
</head>
<body  onload="initForm();" >
  <form action="./ProposalComPrintSave_IDGF.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- 个人保单信息部分 -->
<TABLE>
    <TR>
        <TD class= common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
        </TD>
        <TD class = titleImg>
            请输入投保单查询条件：
        </TD>
    </TR>
</TABLE>	
<div class="maxbox1">
<Div id= "divLCPolContion" style= "display: ''">		
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5> 管理机构 </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" type="text" class="codeno" name=ManageCom onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" ></TD>
          <TD  class= title5> 机构级别 </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" type="text" class="codeno" name=ManageGrade  CodeData="0|^4|二级机构|^6|三级机构^8|四级机构" onClick="showCodeListEx('MangeComGrade',[this,MangeComGradeName],[0,1]);" ondblClick="showCodeListEx('MangeComGrade',[this,MangeComGradeName],[0,1]);" onkeyup="showCodeListKeyEx('MangeComGrade',[this,MangeComGradeName],[0,1]);"><input class="codename" name="MangeComGradeName" ></TD>
        </TR>
    </table>
 </div>   
 </div>
 <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查询保单</a>
 <a href="javascript:void(0)" class=button name='printButton' onclick="printPol();">打印保单</a> 
<!-- <INPUT VALUE="查询保单" class= cssButton TYPE=button onclick="easyQueryClick();"> 
<INPUT VALUE="打印保单" class= cssButton name='printButton'  TYPE=button onclick="printPol();"> --> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
  	</div>
  	<Div id= "divErrorMInfo" style= "display: ''">		
	<TABLE>
    <TR>
        <TD class= common> 
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divErrorInfo);">
        </TD>
        <TD class = titleImg>
            保单打印出错信息查询
        </TD>
    </TR>
</TABLE>	
		<div class="maxbox1">
		<Div id= "divErrorInfo" style= "display: ''">		
		<table class= common align=center>
			<TR class= common>
				<TD class= title5>保单号</TD>
				<TD class= input5> <Input class= "common wid" name=ErrorContNo > </TD>
				<TD class= title5>日期</TD>
        <TD class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
      </TR>
      <TR class= common> 
         <TD  class= title5> 管理机构 </TD>
         <TD  class= input5>  <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" type="text" class="codeno" name=EManageCom onclick="return showCodeList('station',[this,EManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,EManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,EManageComName],[0,1]);"><input class="codename" name="EManageComName" ></TD>		
			</TR>
		</table>
  </Div>
  </div>
  <div>
    <a href="javascript:void(0)" class=button onclick="easyQueryErrClick();">查  询</a>
  </div>
  <br>
	<!-- <INPUT VALUE="查   询" class="cssButton" TYPE=button onclick="easyQueryErrClick();"> -->
		<Div id= "divLCPrintError" style= "display: ' '" align=center>
			<table class= common>
				<tr class= common>
					<td text-align: left colSpan=1>
						<span id="spanErrorGrid" ></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首 页" class="cssButton90" TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="getNextPage();">
			<INPUT VALUE="尾 页" class="cssButton93" TYPE=button onclick="getLastPage();">
		</div>
	 </div>	
</div>	
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
