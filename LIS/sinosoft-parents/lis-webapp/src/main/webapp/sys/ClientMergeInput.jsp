<html>
<%
//程序名称：ClientMergeInput.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="ClientMergeInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ClientMergeInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ClientMergeSave.jsp" method=post name=fm target="fraSubmit">

	

    <table>
      <tr>
        <td><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></td>
        <td class= titleImg>客户合并查询条件</td></tr>
    </table>

    <div  id= "divLDPerson" style= "display: ''">
      <table class="common">
        <tr class="common">
          <td class="title">客户姓名</td>
          <td class="input"><Input class="common" name=Name ></td>
          
          <td class="title">客户性别</td>
          <td class="input"><Input class="codeno" name=AppntSex  verify="投保人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName readonly=true ></td>
          
          <td class="title">客户出生日期</td>
          <td class="input"><input class="coolDatePicker" dateFormat="short" name="Birthday"></td></tr>
          	
        <tr class="common">
        	<td class="title">证件类型</td>
          <td class="input"><Input class="codeno" name="AppntIDType" verify="投保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName readonly=true></td>	
          
          <td class="title">证件号码</td>
          <td class="input"><Input class="common" name=IDNo ></td>
     
          <td class="title"></td>
          <td class="input"><Input class="readonly" readonly name= ></td></tr>
         </table>
<input class=cssButton type="button" align=lift value=" 查  询 " onclick="queryClick()">
 
          
      <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 原客户信息 
	    	</td>
	    </tr>
	    </table>    
	<div id= "divOPolGrid" style= "display: ''" align=center >
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         
    </div>      
      
       
          
       

    
    </div>
    
    <!-- 客户列表 -->
    <table>
   	  <tr>
        <td class="common"><IMG  src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divClientList);"></td>
    	<td class= titleImg>相同客户信息列表</td></tr>
    </table>
    
	<div id="divClientList" style="display: ''">
      <table  class= common>
        <tr  class= common>
          <td text-align: left colSpan=1><span id="spanClientList" ></span></td></tr>
	  </table>
	  <div id= "divCustomerUnionInfo" style= "display: ''" >
	 <table class= common>
    	<TR  class= common>
            	
          <TD class= title> 原客户号码 </td>
          <TD class= input><input class= readonly  name=CustomerNo_OLD readonly ></td>
           <TD class = title>  </td>
          <TD class= input><input class=readonly  name= readonly ></td>
          <TD class= title> 合并后客户号码 </td>
          <TD class= input><input class= readonly  name=CustomerNo_NEW readonly ></td>
        </tr>
        <tr>
          <td class="title">合并方向</td>
          <td class="input">
			      <input type="radio" name="exchangeRadio"  value="1"  OnClick="exchangeCustomerNo();" >反向 
			      <input type="radio" name="exchangeRadio"  value="-1" OnClick="exchangeCustomerNo();">正向          
		      </td>
        </tr>
      </table>		
	</div>
	  <input class=cssButton type="button" value="客户合并" onclick="ClientMerge()">
	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
