<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="AllGetQuery.js"></SCRIPT>
  <%@include file="AllGetQueryInit.jsp"%>
  <title>给付查询 </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入给付查询条件：</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title> 实付号码 </TD>
          <TD  class= input> <Input class= "common wid" name=ActuGetNo > </TD>
          <TD  class= title>  其它号码  </TD>
          <TD  class= input>  <Input class= "common wid" name=OtherNo > </TD>
         
          <TD  class= title> 其它号码类型 </TD>
          <TD  class= input>	<Input class=code name=OtherNoType verify="其它号码类型"
           CodeData="0|^2|生存领取合同号^4|暂交费退费暂收据号^5|赔付赔案号^6|其他退费合同号^7|红利给付合同号^10|保全批改号"
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="showCodeListEx('OtherNoType',[this]);"
            ondblClick="showCodeListEx('OtherNoType',[this]);"
             onKeyUp="showCodeListKeyEx('OtherNoType',[this]);"></TD>          
        </TR>
        <TR  class= common>
          <TD  class= title>  应付日期</TD>
          <TD  class= input>
          <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#ShouldDate'});"dateFormat="short" 
name=ShouldDate id=ShouldDate><span class="icon"><a onClick="laydate({elem: '#ShouldDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </TD>
        
          <TD  class= title>  管理机构 </TD>
          <TD  class= input><Input class="common wid" name=MngCom verify="管理机构|code:comcode"
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('comcode',[this],null,null,null,null,1);"
          onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);"
           onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" readonly> </TD>
          <TD  class= title> 代理人编码  </TD> 
          <TD  class= input> <Input class="common wid" name=AgentCode 
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return queryAgent();" 
           ondblclick="return queryAgent();" > </TD>
        </TR>
    </table>
    </div>
    </div>
    <a href="javascript:void(0);" class="button" id="getquery" onClick="easyQueryClick();">查   询</a>
    <a href="javascript:void(0);" class="button" onClick="getQueryDetail();">给付明细</a>
          <!--<INPUT VALUE="查  询" class= cssButton TYPE=button name="getquery" onClick="easyQueryClick();"> 
          <INPUT VALUE="给付明细" class= cssButton TYPE=button onClick="getQueryDetail();"> 	-->				
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGet1);">
    		</td>
    		<td class= titleImg>
    			 给付信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGet1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         <div align="center">
      <INPUT VALUE="首  页" class= "cssButton90" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= "cssButton91" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= "cssButton92" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class= "cssButton93" TYPE=button onClick="turnPage.lastPage();">				
  	</div>
    </div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
