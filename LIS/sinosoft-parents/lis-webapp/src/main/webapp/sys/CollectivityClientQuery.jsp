<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<title>客户信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <!--SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT-->
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./CollectivityClientQuery.js"></script> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CollectivityClientQueryInit.jsp"%>

</head>
<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm target=fraSubmit method=post>
     <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cav);"></td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
    <Div  id= "cav" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>
          单位编码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=GrpNo id=GrpNo >
          </TD>
          <TD  class= title5>
          单位名称
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=GrpName id=GrpName >
          </TD>
          </TR>
          <TR  class= common>
          <TD  class= title5>
          单位性质
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class="code" name=GrpNature id=GrpNature ondblclick="return showCodeList('GrpNature',[this]);" onclick="return showCodeList('GrpNature',[this]);" onkeyup="return showCodeListKey('GrpNature',[this]);">            
          </TD>
          <TD  class= title5></TD>
          <TD  class= input5>
       </TR>             
      	   
   </Table>  </Div>
      <!--<INPUT VALUE="查  询" class= cssButton TYPE=button onclick="easyQueryClick();"> -->
       <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCollectivityClient1);">
    		</TD>
    		<TD class= titleImg>
    			 客户信息
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divCollectivityClient1" style= "display: ''" align=center>
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      
 </Div>					

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>	
<!--<INPUT VALUE="返  回" class= cssButton TYPE=button onclick="returnParent();"> -->
<a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a> 				
</Form><br><br><br><br>
</body>
</html>
