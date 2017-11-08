<html> 
<% 
//程序名称：
//程序功能：集体保全
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
   

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./GEdorTypeWT.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="GEdorTypeWTInit.jsp"%>
  
  
</head>
<body  onload="initForm();" >
  <form action="./GEdorTypeWTSubmit.jsp" method=post name=fm id=fm target="fraSubmit">   
<div class=maxbox1>  
  <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > 批单号</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorNo id=EdorNo >
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=EdorType id=EdorType>
      </TD>
     
      <TD class = title > 团体保单号 </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
      </TD>   
    </TR>
	<TR  class= common>
    <TD class = title > 保单生效日期 </TD>
      <TD class = input >
      	<input class = "coolDatePicker" readonly name=CValiDate onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

      </TD>
    
    <TD class = title > 保全生效日期 </TD>
    <TD class = input >
      	<input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

    </TD>
	  <TD ></TD>
      <TD ></TD>
	</TR>
  </TABLE> 
</div>
   <table>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPPolGrid);">
      </td>
      <td class= titleImg>
        退保信息
      </td>
   </table>
   
	<Div  id= "divWTFeeDetailGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanWTFeeDetailGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <!--INPUT VALUE="首页" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾页" TYPE=button onclick="getLastPage();"--> 					
  	</div>
  	
 	<hr class=line>
    <Div  id= "divLPInsured" style= "display: ''">
      <table  class= common> 
      <TR class = common >
          <TD  class= title width="5%">退费合计金额(扣除工本费10元)</TD>
          <TD  class= input width="20%"><Input class = "readonly wid" readonly  name=GetMoney id=GetMoney>元</TD>
          <TD  class= input ></TD>
		  <TD  class= input ></TD>
		  <TD  class= input ></TD>
		  <TD  class= input ></TD>
     	</TR>
            
      </table>
    </Div>

    <table class = common>
	<TR class= common>
       <TD  class= input > 
       		 <Input  class= cssButton type=Button hidden value="保存申请" onclick="edorTypeWTSave()">     	 
       		 <Input  class= cssButton type=Button value="返  回" onclick="edorTypeWTReturn()">
     	 </TD>
     	 </TR>
     	</table>
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" name="ContType">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
