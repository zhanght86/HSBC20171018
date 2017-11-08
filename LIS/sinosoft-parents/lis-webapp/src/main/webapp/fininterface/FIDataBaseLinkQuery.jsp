<html>
<%
//程序名称：数据接口配置管理
//程序功能：
//创建日期：2008-8-5
//创建人  ：范昕
%>
<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
</script>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="FIDataBaseLinkQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIDataBaseLinkQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
<form action="./FIDataBaseLinkQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
		<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIDataBaseLinkQuery);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </tr>
  </table>
  
   <Div id= "divFIDataBaseLinkQuery" style= "display: ''">
   <div class="maxbox">
   		<Table class= common>
		<TR  class= common>
			<TD  class= title5>接口编码</TD>
			<TD  class= input5><Input class="wid" class=common name=InterfaceCode id=InterfaceCode ></TD>
			<TD class="title5">操作员</TD>
      <TD class="input5">
      <input class="wid" class="readonly" readonly name="Operator"  id="Operator"></TD>
		</TR>
		 <TR  class= common>
			<TD  class= title5>接口名称</TD>
			<TD  class= input5><Input class="wid" class=common name=InterfaceName id=InterfaceName ></TD>
			<TD  class= title5>数据库类型</TD>
			<TD  class= input5><Input class="wid" class=common name=DBType id=DBType ></TD>
		</TR>
  	<TR  class= common>
			<TD  class= title5>IP</TD>
			<TD  class= input5><Input class="wid" class=common name=IP id=IP ></TD>
			<TD  class= title5>端口号</TD>
			<TD  class= input5><Input class="wid" class=common name=Port id=Port ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>数据库名称</TD>
			<TD  class= input5><Input class="wid" class=common name=DBName id=DBName ></TD>
			<TD  class= title5>服务名称</TD>
			<TD  class= input5><Input class="wid" class=common name=ServerName id=ServerName ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>用户名</TD>
			<TD  class= input5><Input class="wid" class=common name=UserName id=UserName ></TD>
			<TD  class= title5>密码</TD>
			<TD  class= input5><Input class="wid" class=common name=PassWord id=PassWord ></TD>
		</TR>
		<TR  class= common>
			<TD class="title5">管理机构</TD>
      <TD class="input5">
      <input class="wid" class="readonly" readonly name="ManageCom"></TD>
		</TR>
   </Table>
   	<!--<INPUT VALUE="查  询" TYPE=button onclick="submitForm();" class="cssButton">-->
     <a href="javascript:void(0);" class="button" onClick="submitForm();">查    询</a>
		</div>
  	</Div>
  	 <table>
    	<tr>
        	<td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIDataBaseLinkGrid);">
    		</td>
    		<td class= titleImg>
    			 查询结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divFIDataBaseLinkGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIDataBaseLinkGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left"><INPUT VALUE="返  回" TYPE=button onclick="ReturnData();" class="cssButton"></div>-->
      <div align="center"><INPUT VALUE="首  页" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="上一页" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="下一页" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();" class="cssButton93"></div>
  	</div>
    <a href="javascript:void(0);" class="button" onClick="ReturnData();">返    回</a>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
