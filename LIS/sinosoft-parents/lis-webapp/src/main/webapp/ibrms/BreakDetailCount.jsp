
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�CommandMain.jsp
//�����ܣ�Υ����������
//�������ڣ�2008-9-17
//������  ��
//���¼�¼��  
%>

<%
	String Business = "";
	try
	{
		Business = request.getParameter("Business");
	}
	catch( Exception e )
	{
		Business = "";
	}
	
		String ManageCom = "";
	try
	{
		ManageCom = request.getParameter("ManageCom");
	}
	catch( Exception e )
	{
		ManageCom = "";
	}
	
	String RuleStartDate = "";
	try
	{
		RuleStartDate = request.getParameter("RuleStartDate");
	}
	catch( Exception e )
	{
		Business = "";
	}
	
		String RuleEndDate = "";
	try
	{
		RuleEndDate = request.getParameter("RuleEndDate");
	}
	catch( Exception e )
	{
		RuleEndDate = "";
	}
	String templateid = "";
	try
	{
		templateid = request.getParameter("templateid");
	}
	catch( Exception e )
	{
		templateid = "";
	}
%>
<head >
<script> 
	var Business = "<%=Business%>";
	var ManageCom = "<%=ManageCom%>";
	var RuleStartDate = "<%=RuleStartDate%>";
	var RuleEndDate = "<%=RuleEndDate%>";
	var templateid = "<%=templateid%>";
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="CountQuery.js"></SCRIPT>
  <%@include file="CountQueryInit.jsp"%>



<body  onload="InitBreakDetail();initElementtype();initBreakDetailCountGrid();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>Υ��������ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox1">
<table class=common align=center>
	<TR class=common>
		 <td class=title>����</td>
          <td class=input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=ManageCom id=ManageCom verify="�������|code:comcode"  
                   ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" 
                   onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input name=ManageComName id=ManageComName class='codename' readonly=true  >
           </td>  
			
       <TD class=title>ҵ��ģ��</TD>
        <TD  class= input>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" verify="ҵ��ģ��|code:comcode" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename'  readonly=true>
	    </TD> 
	     <TD  class= title>
           ģ����
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=templateid readonly=true >
          </TD>
	</TR>
	<TR class=common>
	<TD class=title>
                          ��ʼ���ڣ�
        </TD>	  
      
         <TD  class= input>
          <!--<Input name=RuleStartDate class='coolDatePicker'  verify="��ʼ����|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
		<TD class=title>
                         �������ڣ�
        </TD>	  
         <TD  class= input>
          <!--<Input name=RuleEndDate class='coolDatePicker'  verify="��ʼ����|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
        
	</TR>
<TR>
</Table>
</Div>
  <p>
  <Div id= DivSelect style="display: ''">
     <input class=cssButton type="submit"  value='�鿴��ϸ' onclick="QueryBreakDetail()">     
  </Div>
<!--<a href="javascript:void(0);" class="button" onClick="QueryBreakDetail();">�鿴��ϸ</a>-->
  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	Υ�������б�
      </td></tr>
    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	        <span id="spanBreakDetailCountGrid" ></span>
		</td>
		<TD class=input style="display: none">						
		</TD>
	</tr>

     </table>
     <center>
        <INPUT VALUE="��  ҳ" TYPE=button  class="cssButton90" onclick="turnPage.firstPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton91" onclick="turnPage.previousPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton92" onclick="turnPage.nextPage()">
        <INPUT VALUE="β  ҳ" TYPE=button  class="cssButton93" onclick="turnPage.lastPage()"></center></Div>
<br>
<br>	    
  
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
