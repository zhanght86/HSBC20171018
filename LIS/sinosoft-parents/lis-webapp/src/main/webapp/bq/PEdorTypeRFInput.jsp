<html> 
<% 
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: 保单还款
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeRF.js"></SCRIPT>
  <%@include file="PEdorTypeRFInit.jsp"%> 
</head>

<body  onload="initForm();" >
  <form action="./PEdorTypeRFSubmit.jsp" method=post name=fm id=fm target="fraSubmit">   
  <div class=maxbox1>  
  <TABLE class=common>
    <TR  class= common> 
      <TD  class= title >
        保全受理号
      </TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > 
        批改类型 
      </TD>
      <TD class = input >
      	<Input class=codeno  readonly=true name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>    
      <TD class = title >  
        保单号 
      </TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>   
    </TR>
    <TR class= common> 
    	<TD class =title>柜面受理日期</TD>
    	<TD class = input>    		
    		<input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
		  <TD class= title>
		    
		  </TD>
		  <TD class= input>
		    <Input class= "readOnly wid" readonly  name= >
		  </TD>
    </TR>
  </TABLE>
  </div>  
  <table>
   	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerGrid);">
      </td>
      <td class= titleImg>
        客户基本信息
      </td>
   	</tr>
  </table> 
  <Div  id= "divCustomerGrid" style= "display: ''">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
	        			<span id="spanCustomerGrid" >
	        			</span> 
        	  	</td>
        	</tr>
        </table>					
  </div> 

  <table>
   	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLoanGrid);">
      </td>
      <td class= titleImg>
        贷款详细信息
      </td>
   	</tr>
  </table> 
   <Div  id= "divLoanGrid" style= "display: ''">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
	        			<span id="spanLoanGrid" >
	        			</span> 
        	  	</td>
        	</tr>
        </table>					
  </div>   
  <Div id= "divEdorquery" style="display: ''"> 
     		 <Input class= cssButton type=Button value="保  存" onclick="edorTypeRFSave()">   	
     		 <Input class= cssButton type=Button value="返  回" onclick="returnParent()">
     		 <Input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad()">
</Div>
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" name="ContType">
	 <input type=hidden id="PolNo" name="PolNo">
	 <input type=hidden id="EdorNo" name="EdorNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">
	 <input type=hidden id="PayOffFlag" name="PayOffFlag">
	 
	   <br><br><br><br>
 <%@ include file="PEdorFeeDetail.jsp" %> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
	
</script>
</html>
