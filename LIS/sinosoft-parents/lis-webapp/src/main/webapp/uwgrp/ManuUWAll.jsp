<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
	String tFlag = "";
	tFlag = request.getParameter("type");
%>
<html>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var operFlag = "<%=tFlag%>";		//区分团险和个险的标志
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>";     //记录登陆机构

</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ManuUWAll.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ManuUWAllInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./ManuUWAllChk.jsp">

    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch1);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：</td>
		  </tr>
	</table>
  <div class="maxbox1">
	<Div  id= "divSearch1" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
      		<td  class=title>管理机构</td>
          <td  class=input>
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center"  class="codeno" name=ManageCom id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </td>          
          <TD  class= title>投保单号</TD>
          <TD  class= input>
            <Input class= "common wid" name=ContNo id="ContNo">
          </TD>
          <TD  class= title>投保单位</TD>
          <TD  class= input>
            <Input class= "common wid" name=AppntName id="AppntName">
          </TD>  
        </TR>
        <tr class="common">
        	<TD  class= title>复核日期 </TD>
          <TD  class= input>
            <Input class="coolDatePicker" onClick="laydate({elem: '#ApproveDate'});" verify="复核日期|DATE" dateFormat="short" name=ApproveDate id="ApproveDate"><span class="icon"><a onClick="laydate({elem: '#ApproveDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
				</tr>
    </table>
    </Div>
    </div>
    <a href="javascript:void(0)" class=button onclick="easyQueryClickAll();">查  询</a>
    <a href="javascript:void(0)" id="riskbutton" class=button onclick="ApplyUW();">申  请</a>
    <!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickAll();">
 		<INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();"> -->
   <DIV id="DivLCContInfo" STYLE="display:''">
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>共享工作池</td>
    	</tr>
    </table>
    </Div>
      <Div  id= "divGrpGrid" style= "display: ''" align = center>
        <table  class= common >
       		<tr  class=common>
      	  	<td text-align: left colSpan=1 >
  					  <span id="spanGrpGrid" >
  					  </span>
  			  	</td>
  			  </tr>
  			</table>
         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();">
         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();">
         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();">
         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">
    </div>
  <br>
    <div class="maxbox1">
    <table  class= common>
      	<TR  class= common>
      		<td  class=title>管理机构</td>
          <td  class=input>
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=ManageCom1 id="ManageCom1" onclick="showCodeList('station',[this,ManageComName1],[0,1]);" ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 readonly=true>
          </td>          
          <TD  class= title>投保单号</TD>
          <TD  class= input>
            <Input class= "common wid" name=ContNoSelf id="ContNoSelf">
          </TD>
          <TD  class= title>投保单位</TD>
          <TD  class= input>
            <Input class= "common wid" name=AppntNameSelf id="AppntNameSelf">
          </TD>
        </TR>
        <TR class=common>
        	<TD  class= title>复核日期</TD>
          <TD  class= input>
            <Input class="coolDatePicker" onClick="laydate({elem: '#ApproveDateSelf'});" verify="复核日期|DATE" dateFormat="short" name=ApproveDateSelf id="ApproveDateSelf"><span class="icon"><a onClick="laydate({elem: '#ApproveDateSelf'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      	</TR>
   </table>
   </div>
   <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
   <!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> -->
   <DIV id="DivLCContInfo" STYLE="display:''">
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>待核保投保单</td>
    	</tr>
    </table>
    </Div>
         <Div  id= "divSelfGrpGrid" style= "display: ''" >
         <table  class= common >
       		<tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSelfGrpGrid" >
  					</span>
  			  	</td>
  			</tr>
  		 </table>
       <div style="display:none" align = center>
         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>

    <!--INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();"-->
    <table>
    	 <Input type=hidden name="ApplyType" id="ApplyType">
    	 <Input type=hidden name="MissionID" id="MissionID">
    	 <Input type=hidden name="SubMissionID" id="SubMissionID">
    	 <Input type=hidden name="ActivityID" id="ActivityID">
         <Input type=hidden name="priva" ><!--add by yaory-->
    </table>
    <br>
    <br>
    <br>
    <br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
