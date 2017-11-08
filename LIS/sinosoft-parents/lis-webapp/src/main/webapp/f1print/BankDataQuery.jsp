<%
//程序名称：BankDataQuery.jsp
//程序功能：财务报盘数据查询
//创建日期：2004-10-20
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
%>

<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 
  <SCRIPT src="./BankDataQuery.js"></SCRIPT>   
  <%@include file="./BankDataQueryInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>提取新契约手机号 </title>
</head>      
 
<body  onload="initForm();" >
  <form method=post name=fm id="fm">
  <table>
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    	</td>
			<td class= titleImg>
				请输入查询条件
			</td>
		</tr>
	</table>
  <div class="maxbox" id="maxbox" >
   <Table class= common>
     <TR class= common> 
          <TD  class= title>管理机构</TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" onDblClick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            				onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);">
          </TD>
		      <TD  class= title>银行代码</TD>
		      <TD  class= input>
		        <Input CLASS="code" name=BankCode id="BankCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" verify="银行代码|notnull&code:BankCode" onClick="return showCodeList('Bank',[this],null,null,null,null,1);" onDblClick="return showCodeList('Bank',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('Bank', [this],null,null,null,null,1);">
		      </TD>
       		<TD  class= title width="25%">制盘日期</TD>
       		<TD  class= input width="25%">
       		  <Input class= "coolDatePicker" dateFormat="short" name=SendDate id="SendDate" onClick="laydate({elem:'#SendDate'});"><span class="icon"><a onClick="laydate({elem: '#SendDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
     </TR>
     <TR  class= common>
          <TD  class= title>保单号</TD>
          <TD  class= input><Input class= "common wid" name=PolNo id="PolNo"></TD>
          <TD  class= title>帐户名</TD>
          <TD  class= input><Input class= "common wid" name=AccName id="AccName" ></TD>
          <TD class=title>
	  	制返盘状态
	  </TD>
	  <TD class=input>
	  	<input class=codeno name="ContType" id="ContType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|制盘^2|返盘" onClick="return showCodeListEx('SysCertCode', [this,ContTypeName],[0,1])" onDblClick="return showCodeListEx('SysCertCode', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('SysCertCode', [this,ContTypeName],[0,1])"><input class=codename name=ContTypeName id="ContTypeName" readonly=true>
	  </TD>	
     </TR>
     
     <TR class= common> 
          <TD  class= title>批次号</TD>
          <TD  class= input>
            <Input class="common wid" name=serialno id="serialno">
          </TD>
		      <TD  class= title>返盘日期</TD>
		      <TD  class= input>
		         <Input class= "coolDatePicker" dateFormat="short" name=BackDate id="BackDate" onClick="laydate({elem:'#BackDate'});"><span class="icon"><a onClick="laydate({elem: '#BackDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		      </TD>
		      <TD  class= title>代扣类型</TD>
          <TD class=input>
      		<input class="codeno" name="NoType" id="NoType"  style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('notypes',[this,NoTypeName],[0,1]);"      		
      		ondblClick="return showCodeList('notypes',[this,NoTypeName],[0,1]);"     
      		onkeyup="return showCodeList('notypes',[this,NoTypeName],[0,1]);"><input class=codename name=NoTypeName readonly=true>
      </TD>
     </TR> 
     <TR class = common>
     	<TD class=title>帐号</TD>
     		<TD class = input>
     			<Input class="common wid" name=AccNo id="AccNo">
     		</TD>
            <TD clasee=title></TD>
     		<TD class = input></TD>
            <TD clasee=title></TD>
     		<TD class = input></TD>
     </TR>
   	</Table>  
   	<p>
    <input type=hidden id="fmtransact" name="fmtransact">
    <!--数据区-->
    <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQuery()"> 	

  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
     <center>
      	<INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="getLastPage();"> 
     </center>					
  	</div>
    
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
