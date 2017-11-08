<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="RePrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RePrintInit.jsp"%>
  <title>补打 </title>
</head>
<body  onload="initForm();" >
  <form  method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
		<td class= titleImg align= center>请输入保单查询条件：</td>
	</tr>
    </table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
    <tr  class= common>
         <TD  class= title5>   起始日期   </TD>
          <TD  class= input5>  <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
 </TD>
          <TD  class= title5>  结束日期 </TD>
          <TD  class= input5> 
 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>  </TD>  
      	  </tr>
      	<TR  class= common>   
          <TD  class= title5> 通知书类型 </TD>
      	  <TD  class= input5>  <input class="common wid" name="Code"  id="Code" 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('renoticetype', [this],null,null,null,null,1,200)"
           ondblclick="return showCodeList('renoticetype', [this],null,null,null,null,1,200)"
            onKeyUp="return showCodeListKey('renoticetype', [this],null,null,null,null,1)"></td>
           
          <TD  class= title5> 代理人编码 </TD>
          <!--TD  class= input> <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">  </TD-->
           <TD  class= input5><Input class="common wid" id="AgentCode" name=AgentCode >   </TD>
          </tr>
      	<TR  class= common>   
          <TD  class= title5>  印刷号 </TD>
          <TD  class= input5>  <Input class="common wid" id="OtherNo" name=OtherNo >   </TD>
         
          <input type=hidden id="PrtSeq" name="PrtSeq">
        </TR>
        <TR  class= common>
          
          <TD  class= title>  </TD>
          <TD  class= input>  </TD>
        </TR>
         
    </table>
    </div></div>
         <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> 
  </form>
  <form action="./RePrintSave.jsp" method=post name=fmSave target="fraSubmit">
    <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	  </td>
    	  <td class= titleImg>
    		通知书信息
    	  </td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="getLastPage();"> 	
  	</div>
  	<p>
      <INPUT VALUE="提交补打通知书数据" class= cssButton TYPE=button onClick="printPol();">
  	</p>
    
 <br><br><br><br>
  	 
      <input type=hidden id="fmtransact" name="fmtransact">
	  	<input type=hidden id="ContNo" name="ContNo">
	  	<input type=hidden id="PrtSeq" name="PrtSeq">
	  	<input type=hidden id="PrtNo" name="PrtNo">
	  	<input type=hidden id="MissionID" name="MissionID">
	  	<input type=hidden id="SubMissionID" name="SubMissionID">
	  	<input type=hidden id="ActivityID" name="ActivityID">
	  	<input type=hidden id="Code" name="Code">
  	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
