<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRConfigRelaInput.jsp
//function :
//Creator :zhangbin
//date :2008-8-26
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
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

<SCRIPT src = "./LRConfigRelaInput.js"></SCRIPT> 
<%@include file="./LRConfigRelaInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="./ReComManageSave.jsp" method=post name=fm target="fraSubmit">
  <%@include file="../common/jsp/InputButton.jsp"%>
   
   	<Div id= "divCertifyType" style= "display: ''">
		  <Div  id= "divCertifyType1" style= "display: ''">
		    <table  class= common>
		        <tr  class= common>
		          <td style="text-align:left;" colSpan=1>
		        		<span id="spanRelateGrid" ></span>
		      		</td>
		    		</tr>
		  	</table>
			</div>
			<div align=center>
				<input VALUE="��ҳ" TYPE="button" onclick="turnPage.firstPage();" class="cssButton">
				<input VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();" class="cssButton">
				<input VALUE="��һҳ" TYPE="button" onclick="turnPage.nextPage();" class="cssButton">
				<input VALUE="βҳ" TYPE="button" onclick="turnPage.lastPage();" class="cssButton">
			</div>
		</Div>
		<input class="cssButton" type="button" style="display:''" value="¼���������ۼ�" onclick="inputRela()" >
		<input class="cssButton" type="button" value="�ر�" onclick="ClosePage()" >
		<br>
		<hr>
  
  
  
	<br>
		<Div id=divTable1 style="display:''" >
   		<Table class= common>
   			<TR class= common>
   				<TD class= title5>
�ۼƷ��ձ���
   				</TD>
   				<TD class= input5>
   					<Input class= common readonly="readonly" name= AccumulateDefNO id="AccumulateDefNO"  verify="�ۼƷ��ձ���|NOTNULL"  elementtype=nacessary>
   					
   				</TD>
   				<TD class= title5>
�����ۼƱ���
    	    </TD>
    	    <TD class= input5 >
   					<Input class= common name= UNIONACCNO elementtype=nacessary verify="�����ۼƱ���|NOTNULL&len<11" > 
   				</TD>
   				<TD class= title5>
   				
    	    </TD>
    	    <TD class= input5 > 
    	    	
   				</TD> 
    	  </TR> 
    		<TR class= common>
   				
   				<TD class= title5>
    	    </TD>
    	    <TD class= input5 >
   					
   				</TD>
   				<TD class= title5>
    	    </TD>
    	    <TD class= input5 > 
    	    	
   				</TD>
    	  </TR> 
    		
	  	 </Table>     	  
	
	  
	
		<Div id= "divLRRela" style= "display: ''">
			  <table class="common">		
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanRGrid"></span></td>
					</tr>
				</table>
		    </div>
		   	<INPUT class=cssButton name="addbutton" VALUE="����"  TYPE=button onclick="return submitForm();">
			<INPUT class=cssButton name="updatebutton" VALUE="�޸�"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="ɾ��"  TYPE=button onclick="return deleteClick();">
			
  	</Div> 
  	
   
		
  	<input type="hidden" name="OperateType" >
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>