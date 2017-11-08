<%
//程序名称：OLDCodeQueryInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:48
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <SCRIPT src="./OLDCodeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./OLDCodeQueryInit.jsp"%>
  <title>公用代码表 </title>
</head>
<body  onload="initForm();" >
  <form action="./OLDCodeQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>
      编码类型
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=CodeType >
    </TD>
    <TD  class= title5>
      编码
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=Code >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      编码名称
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=CodeName >
    </TD>
    <TD  class= title5>
      编码别名
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=CodeAlias >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      机构代码
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=ComCode >
    </TD>
    <TD  class= title5>
      其它标志
    </TD>
    <TD  class= input5>
      <Input class= "common wid" name=OtherSign >
    </TD>
  </TR>
</table>

          <INPUT VALUE="查询" class="cssButton" TYPE=button onclick="submitForm();return false;"> 
          <INPUT VALUE="返回" class="cssButton" TYPE=button onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 公用代码表结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class = cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class = cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class = cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class = cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">	  			
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
