<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //System.out.println("werererererrrerwer");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="FeeInfoQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FeeInfoQueryInit.jsp"%>

</head>
<body  onload="initElementtype();initForm();" >
  <form action="PriceInfoIputSave.jsp" method=post name=fm target="fraSubmit">	
	
	<Table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	  </table>
    <table  class= common align=center>
      <TR  class= common>
        <TD  class= title5>
���ʱ���
        </TD>
        <TD  class= input5>
         <!--<Input class= common name=QureyFeeCode >-->
         <Input class=codeno name=QureyFeeCode ondblclick="return showCodeList('FeeRate',[this,QueryRiskCodeName],[0,1]);" onkeyup="return showCodeListKey('FeeRate',[this,QueryRiskCodeName],[0,1]);"  ><input class=codename name=QueryRiskCodeName readonly="readonly" elementtype=nacessary>
        </TD>
        <TD  class= title5>
���ʱ����α��
        </TD>
        <TD  class= input5>
          <input class=codeno name=QureyBatchNo
          ondblclick="return showCodeList('feebatch',[this,QureyBatchNoName],[0,1],null,[fm.QureyFeeCode.value],['feetableno'],1,'500');" onkeyup="return findFeecode();"  
          onkeyup="return showCodeListKey('feebatch',[this,QureyBatchNoName],[0,1],null,[fm.QureyFeeCode.value],['feetableno'],1,'500');"
          ><input class=codename name=QureyBatchNoName readonly="readonly" elementtype=nacessary>
          
        </TD>
      </TR> 
 	</Table> 
 	<br>
	<INPUT VALUE="��ѯ" class= cssbutton TYPE=button onclick="easyQueryClick();">
	&nbsp;&nbsp;
	<INPUT VALUE="����" class= cssbutton TYPE=button onclick="resetInput();">
  
  <Div  id= "divFeeInfo" style= "display:none;" align=left>  
  	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFeeGrid);">
    		</TD>
    		<TD class= titleImg>
���ʱ���Ϣ
    		</TD>
    	</TR>
  	</Table> 
  </Div>
 	<Div  id= "divFeeGrid" style= "display:none;" align=center>
 	  <Table  class= common>
 	    <TR  class= common>
 	     	<td style="text-align:left;" colSpan=1>
 	           <span id="spanFeeGrid" ></span> 
 	 			</TD>
 	    </TR>
 	  </Table>					
 	   <!--  <INPUT VALUE="��  ҳ" class= cssbutton TYPE=button onclick="getFirstPage();"> 
 	     <INPUT VALUE="��һҳ" class= cssbutton TYPE=button onclick="getPreviousPage();"> 					
 	     <INPUT VALUE="��һҳ" class= cssbutton TYPE=button onclick="getNextPage();"> 
 	     <INPUT VALUE="β  ҳ" class= cssbutton TYPE=button onclick="getLastPage();"> -->
 	   <INPUT VALUE="��ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
		 	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();"> 					
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
			<INPUT VALUE="βҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();"> 		
 	</Div>
 </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
