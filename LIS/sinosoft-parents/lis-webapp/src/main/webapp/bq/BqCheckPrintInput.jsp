<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<% 
//�������ƣ�BqCheckPrintInput.jsp
//�����ܣ���ȫ�վ����ߴ�ӡ����̨
//�������ڣ�2005-08-02 16:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/���� 
%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
<SCRIPT src="./BqCheckPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="BqCheckPrintInit.jsp"%>
<title>��ȫ�վݴ�ӡ </title>   
</head>
<body  onload="initForm();initElementtype();" >
  <form  action="./BqCheckPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ��ѯ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class=common>
                <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" >
            </td>
			<td class= titleImg align= center>�������ѯ������</td>
	</tr>
	</table>
	<div class=maxbox>
    <table  class= common align=center>
       <TR  class= common>
          <TD  class= title5>  ��ȫ�����   </TD>
          <TD  class= input5>  <Input class="common wid" name=EdorAcceptNo id=EdorAcceptNo> </TD> 
          <TD  class= title5>  ������   </TD>
          <TD  class= input5>  <Input class="common wid" name=ContNo id=ContNo> </TD> 
       </tr>
       <tr class= common>
          <TD  class= title5>   �������  </TD>
          <!--<TD  class= input><Input class= code name=ManageCom ondblclick="return showCodeList('ComCode',[this]);" onkeyup="return showCodeListKey('ComCode',[this]);"></TD>-->
          <TD  class= input5><Input class= "code" style="background:url(../common/images/select--bg_03.png) no-repeat right 

center" name=ManageCom id=ManageCom ondblclick="return showCodeListEx('nothis', [this],[0,1],null,null,null,1,'260');" onkeyup="return showCodeListKeyEx('nothis', [this],[0,1],null,null,null,1,'260');"></TD>
 		  <td class="title5">ҵ��Ա����</td>
		  <td class="input5" COLSPAN="1">
		      <input NAME="AgentCode" style="background:url(../common/images/select--bg_03.png) no-repeat right 

center" id=AgentCode VALUE="" MAXLENGTH="10" CLASS="code" verifyorder="1"  ondblclick="return queryAgent();" ></td>	  
       </TR>
       <tr class= common>
          <TD class=title5>�վ�����</TD>
          <td class=input5>
			<Input class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right 

center" name=InvoiceType id=InvoiceType ondblclick="showCodeList('bqinvoicetotal',[this,InvoiceName],[0,1])" onkeyup="showCodeListKey('bqinvoicetotal',[this,InvoiceName],[0,1])">
			<input class=codename name=InvoiceName id=InvoiceName readonly>
		  </td>
          <TD class=title5> </TD>
          <TD class=title5> </TD>	       
       </TR>
   </table>
   </div>
		  <a href="javascript:void(0);" class="button" onClick="easyQueryClickSelf();">��    ѯ</a>
  <br><br>
   <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCheckGrid);">
    		</td>
    		<td class= titleImg>
    			 ��ӡ�վ������б�
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCheckGrid" style= "display: ''; text-align:center;">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanNoticeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table  style="text-align:center">
	     <INPUT VALUE="��ҳ" class=cssButton90 class=cssButton TYPE=button onclick="getFirstPage();"> 
         <INPUT VALUE="��һҳ" class=cssButton91 class=cssButton TYPE=button onclick="getPreviousPage();"> 					
         <INPUT VALUE="��һҳ" class=cssButton92 class=cssButton TYPE=button onclick="getNextPage();"> 
         <INPUT VALUE="βҳ" class=cssButton93 class=cssButton TYPE=button onclick="getLastPage();"> 					
       </table>
   </div>
  	<table class= common>
  		<tr class= common>
  			<td class= title5>�վݺ�</td>
  			<td class= input5>
				<Input class= common name=CheckNo id=CheckNo elementtype=nacessary verify="�վݺ�|notnull">
				<INPUT VALUE="��ӡ�վ�" class= cssButton TYPE=button onclick="printCheck();">
			</td>
  			<!--
  			<td class= title>�վ�����</td>
  			<td class= input><Input class= common name=CertifyCode  elementtype=nacessary verify="�վ�����|notnull" readonly = true type=hidden></td>  
  			--->
  			<td class= title5></td>
  			<td class= input5></td>  			
  			<td class= title5></td>
  			<td class= input5></td>
			
  		</tr>
  	</table> 	
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<Input type=hidden id="CertifyCode" name="CertifyCode">
  	<input type=hidden id="SelEdorAcceptNo" name="SelEdorAcceptNo">
  	<input type=hidden id="ChequeType" name="ChequeType">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html> 
