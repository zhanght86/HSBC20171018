<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRAccRDInput.jsp
//function :
//Creator :zhangbin
//date :2007-3-5
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
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

<SCRIPT src = "./LRAccRDInput.js"></SCRIPT> 
<%@include file="./LRAccRDInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="./ReComManageSave.jsp" method=post name=fm target="fraSubmit">
  <%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  <Div id= "divLLReport1" style="display: ''" >
	<br>
		<Div id=divTable1 style="display:''" >
   		<Table class= common>
   			<TR class= common>
   				<TD class= title5>
�ۼƷ��ձ���
   				</TD>
   				<TD class= input5>
   					<Input class= common name= AccumulateDefNO id="ReComCodeId" elementtype=nacessary verify="�ۼƷ��ձ���|NOTNULL"> 
   				</TD>
   				<TD class= title5>
�ۼƷ�������
    	    </TD>
    	    <TD class= input5 >
   					<Input class= common name= AccumulateDefName elementtype=nacessary verify="�ۼƷ�������|NOTNULL"> 
   				</TD>
   				<TD class= title5>
�ۼƷ���״̬
    	    </TD>
    	    <TD class= input5 > 
    	    	<Input class=codeno readonly="readonly" NAME=State VALUE="" CodeData="0|^01|��Ч|^02|δ��Ч" ondblClick="showCodeListEx('',[this,stateName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('',[this,stateName],[0,1],null,null,null,1);"  verify="�ۼƷ���״̬|NOTNULL"><input class=codename name=stateName readonly="readonly"  elementtype=nacessary>
   				</TD>
    	  </TR> 
    		<TR class= common>
   				<TD class= title5>
   			 		<Div  id= "divLRAccRD6" style= "display:none;">��ϸ����</Div>
   				</TD>
   				<TD class= input5>
   				  <Div  id= "divLRAccRD6" style= "display:none;">
   					<Input class="codeno" name="DeTailFlag" CodeData="0|^01|�������ּ���|^02|�������μ���|" 
	  	      	  ondblClick="showCodeListEx('detailflag',[this,DeTailFlagName],[0,1],null,null,null,1);"
	  	      	  onkeyup="showCodeListKeyEx('detailflag',[this,DeTailFlagName],[0,1],null,null,null,1);" verify="��ϸ����|NOTNULL"><Input 
	  	      	  class= codename name= 'DeTailFlagName' elementtype=nacessary>
	  	      	  <Div>
   				</TD>
   				<TD class= title5>
   			       <Div  id= "divLRAccRD7" style= "display:none;">�ۼƱ��</Div>
    	        </TD>
    	    <TD class= input5 >
    	    	<Div  id= "divLRAccRD7" style= "display:none;">
   						<Input class="codeno" name="StandbyFlag" CodeData="0|^01|�ۼ�|^02|���ۼ�|"  value="01"
	  	      		  ondblClick="showCodeListEx('StandbyFlag',[this,StandbyFlagName],[0,1],null,null,null,1);"
	  	      		  onkeyup="showCodeListKeyEx('StandbyFlag',[this,StandbyFlagName],[0,1],null,null,null,1);" verify="�ۼƱ��|NOTNULL"><Input 
	  	      		  class= codename name= 'StandbyFlagName' value="�ۼ�" elementtype=nacessary>
	  	      </Div>
   			</TD>
   				<TD class= title5>
    	    </TD>
    	    <TD class= input5 >
   			</TD>
    	  </TR> 
	  	 </Table>     	  
	  </Div>
    <Div id=divTable3 style="display:none;" >
    	<Table class= common>
    	  <TR>
    	  	<TD class="title5">
    	  		<Div  id= "divLRAccRD1" style= "display: ''">�������ι���</Div>
    	  	</TD>
    	    <TD  class= input58>
    	    	<Div  id= "divLRAccRD2" style= "display: ''">
    	    		<Input class="codeno" name="GetDutyFlag" value="02" CodeData="0|^01|����|^02|������|" 
	  	      		  ondblClick="showCodeListEx('GetDutyFlag',[this,GetDutyFlagName],[0,1],null,null,null,1);"
	  	      		  onkeyup="showCodeListKeyEx('GetDutyFlag',[this,GetDutyFlagName],[0,1],null,null,null,1);" verify="�ۼƷ������|NOTNULL"><Input 
	  	      		  class= codename name= 'GetDutyFlagName'  elementtype=nacessary>
	  	      </Div>
    	    </TD>
   				<TD class= title5>
   					<Div  id= "divLRAccRD3" style= "display: ''">���ձ���<br>ת�����</Div>
   				</TD>
    	    <TD class= input5>
    	    	<Div id= "divLRAccRD4" style= "display: ''">
    	    		<Input class="codeno" name="RiskAmntFlag" CodeData="0|^01|����Ҫת��|^02|��Ҫת��|" 
	  	      	  ondblClick="showCodeListEx('RiskAmntFlag',[this,RiskAmntFlagName],[0,1],null,null,null,1);"
	  	      	  onkeyup="showCodeListKeyEx('RiskAmntFlag',[this,RiskAmntFlagName],[0,1],null,null,null,1);"><Input 
	  	      	  class= codename name= 'RiskAmntFlagName'>
	  	      </Div>
    	    </TD>
    	    <TD class= title5>
   			 		<Div  id= "divLRAccRD5" style= "display: ''">�ۼ�����</Div>
   				</TD>
   				<TD class= input5 >
   					<Div  id= "divLRAccRD6" style= "display: ''">
   						<Input class="codeno" name="AccumulateMode" value="02" CodeData="0|^01|���˵���ͬ�ۼ�|^02|���˶��ͬ�ۼ�|^03|���˶��ͬ�ۼ�" 
	  	      	  ondblClick="showCodeListEx('AccumulateMode',[this,AccumulateModeName],[0,1],null,null,null,1);"
	  	      	  onkeyup="showCodeListKeyEx('AccumulateMode',[this,AccumulateModeName],[0,1],null,null,null,1);" verify="�ۼƷ������|NOTNULL"><Input 
	  	      	  class= codename name= 'AccumulateModeName' >
	  	      </Div>
   				</TD>
    	  </TR>
   		</Table>
   	</Div>
   	<br>
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divCertifyType);"></td>
    	<td class= titleImg>��������</td></tr>
    </table>
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
			<Div  id= "divCertifyType2" style= "display:none;">
		    <table  class= common>
		        <tr  class= common>
		          <td style="text-align:left;" colSpan=1>
		        		<span id="spanDutyGrid" ></span>
		      		</td>
		    		</tr>
		  	</table>
			</div>
		</Div>
		<br>
		<!--  <input class="cssButton" type=button value="���������ۼ�" onclick="configRelation()">-->
  	<input type="hidden" name="OperateType" >
  	<input type="hidden" name="DeTailType" >
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>