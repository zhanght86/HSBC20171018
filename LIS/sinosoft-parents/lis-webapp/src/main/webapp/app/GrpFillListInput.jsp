<%
//程序名称：GrpFillListInput.jsp
//程序功能：团体无名单补名单查询
//创建日期：2006-04-08 16:30:57
//创建人  ：Chenrong
//更新记录：增加对兼业补名单查询的支持 周磊 2007-2-5
%>

<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="GrpFillList.js"></SCRIPT>
  <%@include file="GrpFillListInit.jsp"%>
  
  <title>投保单扫描件查询 </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <!-- 查询条件 --> 
    <div class="maxbox1">       		         
    <table  class= common>
      	<TR  class= common>
            <TD  class= title5>投保单号</TD>
            <TD  class= input5><Input class="wid" class= common name=PrtNo id=PrtNo > </TD>
            <TD  class= title5>团体合同号</TD>
            <TD  class= input5><Input class="wid" class= common name=GrpContNo id=GrpContNo > </TD>
           
    	</TR></table>
        <table  class= common>
      	<TR  class= common>
            <!-- <TD  class= title>合同号</TD> -->
            <TD  class= input><Input class="wid" class= common type=hidden name=ContNo id=ContNo ></TD>
            <!-- <TD  class= title>单证号</TD> -->
            <TD  class= input><Input class="wid" class= common type=hidden name=CertifyNo id=CertifyNo > </TD>
    	</TR>
    </table>
    </div>
    <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
    <!--<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>-->
    
  	
  	<!-- 保单信息部分 -->  
  	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>投保单信息</td>
    	</tr>
    </table> 
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
  			</tr>
    	</table>
        <Div  id= "divPage" align=center style= "display: '' ">
          
        </Div> 	
  	
  	
   
    
    <input type=hidden id="ManageCom" name="ManageCom" id="ManageCom">
	<input type=hidden id="Operator" name="Operator" id="Operator">
   <center><INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
          <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
          <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
          <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></center><br></div>
         <INPUT VALUE="补名单" class= cssButton TYPE=button onclick="addNameClick();">
          <!--<a href="javascript:void(0);" class="button" onClick="addNameClick();">补名单</a>-->
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
