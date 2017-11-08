<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :ReComManageInput.jsp
//function :ReComManage
//Creator :
//date :2007-08-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./ReComManageInput.js"></SCRIPT> 
<%@include file="./ReComManageInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="./ReComManageSave.jsp" method=post name=fm target="fraSubmit">
  <%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  <Div id= "divLLReport1" style= "display: ''">
	<br>
		<Div id=divTable1 style="display:''">
   		<Table class= common>
   			<TR class= common>
   				<TD class= title5>公司代码</TD>
   				<TD class= input5>
   					<Input class= common name= ReComCode id="ReComCodeId"  elementtype=nacessary  verify="公司代码|NOTNULL"> 
   				</TD>
   				<TD class= title5>公司名称</TD>
	    	    <TD class= input5 colspan=3>
	    	    	<Input class= common name= ReComName id="ReComNameId" style="width:96%" elementtype=nacessary> 
	    	    </TD>
			</TR> 
    	  
			<TR>
    	  		<TD class= title5>邮政编码</TD>
   				<TD class= input5 >
   					<Input class= common name= PostalCode > 
   				</TD>
   				<TD class= title5>公司地址</TD>
	    	    <TD class= input5>
	    	    	<Input class= common name= Address > 
	    	    </TD>
    	    	<td  text-align:right class="title5">传真</td>
    	    	<td class="input5">
    	    		<Input class= common name= FaxNo> 
    	    	</td>
			</TR>
		</Table>
  	</Div>
  	
  	<Div id=divTable2 style="display:none;">
    	<Table class= common>
    	  <TR>
    	  	<TD class= title5>
公司类型
   				</TD>
   				<TD class= input5 >
   					<input class=codeno readonly="readonly" name="ComType" CodeData="0|^1|再保险公司^2|保险经纪公司|^3|其它|" 
    	      ondblclick="return showCodeListEx('State', [this,ComTypeName],[0,1],null,null,null,1);" 
    	      onkeyup="return showCodeListKeyEx('State', [this,ComTypeName],[0,1],null,null,null,1);"><input 
    	      class=codename name=ComTypeName readonly="readonly">
   				</TD>
   				<TD class= title5></TD>
    	    <TD class= input5 colspan=3>
    	    </TD>
    	  </TR>
   		</Table>
   	</Div>
   	<br>
   	
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divCertifyType);"></td>
    	<td class= titleImg>公司联系人</td></tr>
    </table>
    
   	<Div  id= "divCertifyType" style= "display: ''">
      <table  class= common>
          <tr  class= common>
            <td text-align:left colSpan=1>
          		<span id="spanRelateGrid" ></span>
        		</td>
      		</tr>
    	</table>
	</div>
	<br>
	<table>
    <tr>
      <td class=common>
      	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSerialRemark);">
      </td>
  	<td class= titleImg>注释</td></tr>
  </table>
    
	<Div  id= "divSerialRemark" style= "display: ''">
		<TR  class= common>
			<TD  class= input5 colspan="6">
			    <textarea name="Note" cols="100%" rows="3"  class="common">
			    </textarea>
			</TD>
		</TR>
  </Div> 	
  
  <input type="hidden" name="OperateType" >
   	
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>