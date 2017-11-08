<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
	<title>Jsp测试页面</title>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<script src="ServerRelation.js"></script>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ServerRelationInit.jsp"%>
</head>
<body onload= "initForm();">
	<%
		//      ../common/cvar/CCodeOperate.js 自动弹出下拉框
	%>
	<form action= "ServerRelationSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
    		</td>
    		 <td class= titleImg>
        		 更新公司信息
       		 </td>
    	</tr>
    </table>
    <Div  id= "divCode1" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            公司名称
          </TD>
          <TD  class= input5>
            <Input class="wid common" id=ManageCom name=ManageCom>
          </TD>
          <TD  class= title5>
            服务器名称
          </TD>
          <TD  class= input5>
            <Input class="wid common" id=Host_Name name=Host_Name >
          </TD>
        </TR>
        
      </table>
      <!--Input class=code name=ComCode ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);" >
      <!--Input class= common name=ComCode -->
    </Div>
    <input type=hidden id="fmtransact" name="fmtransact">
    
    <!-- 数据区 MultiLine -->
    <%--
    <table>
    	<tr>
        	<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			已有公司服务器信息
    		</td>
    	</tr>
    </table>
    
    <INPUT VALUE="添加关系" class= cssButton TYPE=button onclick="addNewComp()"> 	
    <INPUT VALUE="查询" class= cssButton TYPE=button onclick="easyQuery()"> 	
    <INPUT VALUE="保存修改" class= cssButton TYPE=button onclick="saveUpdate()">
    <INPUT VALUE="删除选中" class= cssButton TYPE=button onclick="deleteChecked()">
  	<Div  id= "divCodeGrid" style= "display: ''">
    --%>
  	<Div  id= "divCodeGrid" style= "display: none;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
				<span id="spanCodeGrid" >
				</span> 
		  	</td>
		</tr>
    	</table> 
      	<INPUT VALUE="首页" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="尾页" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
