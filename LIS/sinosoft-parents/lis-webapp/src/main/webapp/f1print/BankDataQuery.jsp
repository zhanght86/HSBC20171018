<%
//�������ƣ�BankDataQuery.jsp
//�����ܣ����������ݲ�ѯ
//�������ڣ�2004-10-20
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  <title>��ȡ����Լ�ֻ��� </title>
</head>      
 
<body  onload="initForm();" >
  <form method=post name=fm id="fm">
  <table>
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    	</td>
			<td class= titleImg>
				�������ѯ����
			</td>
		</tr>
	</table>
  <div class="maxbox" id="maxbox" >
   <Table class= common>
     <TR class= common> 
          <TD  class= title>�������</TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" onDblClick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            				onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);">
          </TD>
		      <TD  class= title>���д���</TD>
		      <TD  class= input>
		        <Input CLASS="code" name=BankCode id="BankCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" verify="���д���|notnull&code:BankCode" onClick="return showCodeList('Bank',[this],null,null,null,null,1);" onDblClick="return showCodeList('Bank',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('Bank', [this],null,null,null,null,1);">
		      </TD>
       		<TD  class= title width="25%">��������</TD>
       		<TD  class= input width="25%">
       		  <Input class= "coolDatePicker" dateFormat="short" name=SendDate id="SendDate" onClick="laydate({elem:'#SendDate'});"><span class="icon"><a onClick="laydate({elem: '#SendDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
     </TR>
     <TR  class= common>
          <TD  class= title>������</TD>
          <TD  class= input><Input class= "common wid" name=PolNo id="PolNo"></TD>
          <TD  class= title>�ʻ���</TD>
          <TD  class= input><Input class= "common wid" name=AccName id="AccName" ></TD>
          <TD class=title>
	  	�Ʒ���״̬
	  </TD>
	  <TD class=input>
	  	<input class=codeno name="ContType" id="ContType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|����^2|����" onClick="return showCodeListEx('SysCertCode', [this,ContTypeName],[0,1])" onDblClick="return showCodeListEx('SysCertCode', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('SysCertCode', [this,ContTypeName],[0,1])"><input class=codename name=ContTypeName id="ContTypeName" readonly=true>
	  </TD>	
     </TR>
     
     <TR class= common> 
          <TD  class= title>���κ�</TD>
          <TD  class= input>
            <Input class="common wid" name=serialno id="serialno">
          </TD>
		      <TD  class= title>��������</TD>
		      <TD  class= input>
		         <Input class= "coolDatePicker" dateFormat="short" name=BackDate id="BackDate" onClick="laydate({elem:'#BackDate'});"><span class="icon"><a onClick="laydate({elem: '#BackDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		      </TD>
		      <TD  class= title>��������</TD>
          <TD class=input>
      		<input class="codeno" name="NoType" id="NoType"  style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('notypes',[this,NoTypeName],[0,1]);"      		
      		ondblClick="return showCodeList('notypes',[this,NoTypeName],[0,1]);"     
      		onkeyup="return showCodeList('notypes',[this,NoTypeName],[0,1]);"><input class=codename name=NoTypeName readonly=true>
      </TD>
     </TR> 
     <TR class = common>
     	<TD class=title>�ʺ�</TD>
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
    <!--������-->
    <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQuery()"> 	

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
      	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();"> 
     </center>					
  	</div>
    
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
