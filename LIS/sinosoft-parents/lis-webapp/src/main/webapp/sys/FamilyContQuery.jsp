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
  <SCRIPT src="FamilyContQuery.js"></SCRIPT>
  <%@include file="FamilyContQueryInit.jsp"%>
  <title>家庭单查询 </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm  id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        
 <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入查询条件</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5> 家庭单号 </TD>
          <TD  class= input5> <Input class= common name=FamilyIDNo > </TD>
        </TR>
    </table>
    </div>
    </div>
    <a href="javascript:void(0);" class="button"onclick="QueryCont();">合同查询</a>
    <a href="javascript:void(0);" class="button"onClick="QueryFee();">暂费查询</a>
         <!-- <INPUT VALUE="合同查询" class= cssButton TYPE=button  onclick="QueryCont();"> 
          <INPUT VALUE="暂费查询" class= cssButton TYPE=button onClick="QueryFee();"> 		-->			
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFamilyCardInfoGrid);">
    		</td>
    		<td class= titleImg>
    			 合同信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divFamilyCardInfoGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFamilyCardInfoGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">				
  	</div>
    <a href="javascript:void(0);" class="button"onClick="PolClick();">保单查询</a>

  	<!--<INPUT VALUE=" 保单查询 " class = cssButton TYPE=button onClick="PolClick();">-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
