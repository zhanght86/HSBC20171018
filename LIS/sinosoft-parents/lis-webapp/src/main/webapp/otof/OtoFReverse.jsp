<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
    loggerDebug("OtoFReverse","Branch:"+Branch);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  
  <SCRIPT src="OtoFReverse.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="OtoFReverseInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./OtoFReverseSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table class=common >
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,qwert);"></td>
        <td class= titleImg>�������ѯ������</td></tr>
		</table>
        <Div  id= "qwert" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>ƾ֤����</TD>
          <TD  class= input5>
			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=VoucherType id=VoucherType  CodeData="0|^1|���ձ���^2|Ԥ�ձ���^3|��������^4|���֧��^5|��ȫӦ��^6|��ȡ�����^7|����Ӧ��^8|ʵ��^9|��ȫ�շ�^10|ҵ��ԱӶ��^11|����ԱӶ��^12|���ն�������|M" ondblClick="showCodeListEx('VoucherType',[this,VoucherTypeName],[0,1]);" onclick="showCodeListEx('VoucherType',[this,VoucherTypeName],[0,1]);" onkeyup="showCodeListKeyEx('VoucherType',[this,VoucherTypeName],[0,1]);"><Input class=codename name=VoucherTypeName id=VoucherTypeName readonly>
		  </TD>       
          <TD  class= title5>�������</TD>
          <TD  class= input5>
			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=ManageCom id=ManageCom ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql1,'1',1,250);" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,codeSql1,'1',1,250);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,codeSql1,'1',1,250);"><Input class=codename name=ManageComName id=ManageComName readonly>
		  </TD></TR>
          <TR  class= common>
          <TD  class= title5>ҵ��������</TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#TransDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=TransDate id="TransDate"><span class="icon"><a onClick="laydate({elem: '#TransDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>ƾ֤��</TD>
	<TD  class= input5><Input class="common wid"  name=VoucherID id=VoucherID></TD>
        </TR>
       <TR  class= common>
	
	<TD  class= title5>������</TD>
	<TD  class= input5><Input class="common wid"  name=PolNo id=PolNo></TD>
	<TD  class= title5>�վݺ�</TD>
	<TD  class= input5><Input class="common wid"  name=BussNo id=BussNo></TD>
       </TR>        
    </table>
		</Div>
		<!--<INPUT VALUE="����ƾ֤��ѯ" class= cssButton TYPE=button onclick="easyQuery();">-->
        <a href="javascript:void(0);" class="button" onClick="easyQuery();">����ƾ֤��ѯ</a>					
    <table >
    	<tr>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);"></td>
      	<td class= titleImg align= center>����ƾ֤��Ϣ</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
	  					<span id="spanCodeGrid" ></span> 
				  	</td>
	  			</tr>
	    	</table>
		<center>
      <INPUT CLASS=cssButton90 VALUE="��    ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β    ҳ" TYPE=button onclick="turnPage.lastPage();"></center>
      </Div>
        <table>
    	<tr>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
      	<td class= titleImg>��¼���������</td>
    	</tr>
    	</table>
        <Div  id= "peer" style= "display: ''" class="maxbox1">
    	<table class= common>
    	<tr class= common>
    	<td class= title5>��������</td>
    <TD class=input5> <Input class="coolDatePicker wid" dateFormat="short" name=AccountingDate id=AccountingDate></TD>  
    	<td class= title5></td>
        <TD class=input5></TD>
    	</tr></table>   
        <table class= common> 	    	    	
				<TR class= common>
					<TD class= title5>ƾ֤����ԭ��</TD>
				</TR>
				<TR  class= common>
					<TD style="padding-left:16px" colspan="4"><textarea name="Reason" cols="168%" rows="4" witdh=25% class="common"></textarea></TD>
				</TR>
	 		</table>
  	</div>
<!--    <INPUT VALUE="����ƾ֤����" class= cssButton TYPE=button onclick="OtoFReverse();">
    	 <INPUT VALUE="����ѯ��������" class= cssButton TYPE=button onclick="OtoFReverseAll();">--><br>
         <a href="javascript:void(0);" class="button" onClick="OtoFReverse();">����ƾ֤����</a>
         <a href="javascript:void(0);" class="button" onClick="OtoFReverseAll();">����ѯ��������</a>
   <INPUT type= "hidden" name= "fmAction" value= ""> 
   <INPUT type= "hidden" name= "ComCode" value= ""> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql1 = "1  and comcode like #"+<%=Branch%>+"%# and char_length(trim(comcode))<=6"  ;
	var codeSql = "1  and code like #"+<%=Branch%>+"%# and char_length(trim(code))<=6"  ;
	fm.all('ComCode').value="<%=Branch%>";
</script>
