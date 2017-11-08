<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html> 
<%
//程序名称：WorkAchieveStatInput.jsp
//程序功能：工作绩效统计清单
//创建日期：2005-11-29 17:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容 
%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="./WorkAchieveStat.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="WorkAchieveStatInit.jsp"%>
<title>工作绩效统计打印 </title>   
</head>
<body  onload="initForm();" >
  <form  action="./WorkAchieveStatSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 查询部分 -->
    <table class= common border=0 width=100%>
    	<tr>
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class=titleImg align= center>请输入查询条件：</td>
	</tr>
	</table>
	<div class=maxbox1>
    <table  class=common>
       <TR>
          <TD  class="title5">管理机构<font color=red> *</font></TD>
          <TD  class="input5"><Input class= code name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('ComCode',[this]);" onkeyup="return showCodeListKey('ComCode',[this]);"></TD>
          <TD  class="title5"></TD>
          <TD  class="input5"></TD> 
       </TR>
       <TR> 
          <TD  class="title5">统计起期<font color=red> *</font></TD>
          <TD  class="input5"><input class="coolDatePicker" dateFormat="short" name="StartDate" onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>	</TD>
          <TD  class="title5">统计止期<font color=red> *</font></TD>
          <TD  class="input5"><input class="coolDatePicker" dateFormat="short" name="EndDate" onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
       </TR>
       <TR  class= common>
 		  <td class="title5">用户类型</td>
		  <td class="input5">
			  <Input class="codeno" name=UsrType id=UsrType CodeData="0|^1|经办人^2|复核人^0|全部" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeListEx('UsrType', [this,UsrTypeName],[0,1]);" onkeyup="return showCodeListKeyEx('UsrType', [this,UsrTypeName],[0,1]);" >
			  <input class="codename" name=UsrTypeName id=UsrTypeName readonly=true>
          </td>        
          <TD  class="title5">用户代码</TD>
		  <td class="input5" COLSPAN="1">
			  <input NAME="UsrCode" id=UsrCode VALUE="" MAXLENGTH="10" CLASS="code" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryUsr();" >
          </td>     
      </TR>
       <TR  class= common>
 		  <td class="title5">处理状态</td>
		  <td class="input5">
			  <Input class="codeno" name=EdorState id=EdorState CodeData="0|^1|生效^2|未生效^0|全部" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeListEx('EdorState', [this,EdorStateName],[0,1]);" onkeyup="return showCodeListKeyEx('EdorState', [this,EdorStateName],[0,1]);" >
			  <input class="codename" name=EdorStateName id=EdorStateName readonly=true>
          </td>        
          <TD  class="title5">保全项目</TD>
          <TD  class="input5"><Input class= codeno name=EdorType id=EdorType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeListEx('EdorType', [this,EdorTypeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKeyEx('EdorType', [this,EdorTypeName],[0,1],null,null,null,null,'200');">
		  <input class="codename" name=EdorTypeName id=EdorTypeName readonly=true></TD>    
      </TR>
    </table>
	</div>
<br>
  	<p>
      <INPUT VALUE="打  印" class= cssButton TYPE=button onclick="printBill();"> 
  	</p>
  	<input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
