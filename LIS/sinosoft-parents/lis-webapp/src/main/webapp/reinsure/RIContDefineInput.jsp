<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRContInput.jsp
//function :ReComManage
//Creator :
//date :2006-08-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./RIContDefineInput.js"></SCRIPT> 
<%@include file="./RIContDefineInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="./RIContDefineSave.jsp" method=post name=fm target="fraSubmit">
  <%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  
<table>
	<tr>
		<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
			OnClick= "showPage(this,divLRCont1);"></td>
		<td class= titleImg>������Ϣ</td>
	</tr>
</table>
  
<Div id= "divLRCont1" style= "display: ''" >
<br>
	<Div id= "divTable1" style= "display: ''" >
		<Table class= common>
			<TR class= common>
				<TD class= title5>��ͬ���</TD>
				<TD class= input5>
					<Input class= common name="RIContNo" id="ReContCodeId" elementtype=nacessary verify="��ͬ���|NOTNULL&len<20"> 
				</TD>
				<TD class= title5>��ͬ����</TD>
				<TD class= input5>
					<Input class= common  name="RIContName" id="ReContNameId" elementtype=nacessary verify="��ͬ����|NOTNULL&len<60"> 
				</TD>   			
			</TR> 
			<TR class= common>
				<TD class= title5>��ͬ״̬</TD>
 	    		<TD class= input5>
		 	    	<Input class=codeno readonly="readonly" NAME=ContState VALUE=""  
		 	    	ondblClick="showCodeList('ristate',[this,ContStateName],[0,1],null,null,null,1);" 
		 	    	onkeyup="showCodeListKey('ristate',[this,ContStateName],[0,1],null,null,null,1);"  verify="��ͬ״̬|NOTNULL"><input class=codename name=ContStateName readonly="readonly"  elementtype=nacessary>
				</TD>
    	  		<TD class= title5>��ʼ����</TD>
   				<TD class= input5><Input name=RValidate class="coolDatePicker" onClick="laydate({elem:'#RValidate'});" dateFormat='short'  id="RValidate">
<span class="icon"><a onClick="laydate({elem:'#RValidate'});"><img src="../common/laydate/skins/default/icon.png"/></a></span></TD>
</TR> 
			<TR class= common>
   				<TD class= title5>��ֹ����</TD>
	    	    <TD class= input5><Input name=RInvalidate class="coolDatePicker" onClick="laydate({elem:'#RInvalidate'});" dateFormat='short'  id="RInvalidate">
<span class="icon"><a onClick="laydate({elem:'#RInvalidate'});"><img src="../common/laydate/skins/default/icon.png"/></a></span></TD>
				    	    <TD class= title5>ǩ������</TD>
<!--	    	    <TD class= input5>-->
<!--	    	    	<Input class= "coolDatePicker" name= RSignDate verify="��ͬǩ������|DATE">-->
<!--	    	    </TD>-->
	    	    
	    	    <TD class= input5><Input name=RSignDate class="coolDatePicker" onClick="laydate({elem:'#RSignDate'});" dateFormat='short'  id="RSignDate">
<span class="icon"><a onClick="laydate({elem:'#RSignDate'});"><img src="../common/laydate/skins/default/icon.png"/></a></span></TD>
   				
	    	    
			</TR>
			<TR class= common> 
	    	   			<TD class= title5>��ͬ����</TD>
	    	    <TD class= input5>
		   			<Input class="codeno" readonly="readonly" name= "ReCountType"  
		    	      ondblClick="showCodeList('riconttype',[this,ReCountTypeName],[0,1],null,null,null,1);"
		    	      onkeyup="showCodeListKey('riconttype',[this,ReCountTypeName],[0,1],null,null,null,1); "verify="��ͬ����|NOTNULL"><Input 
		    	      class= codename name='ReCountTypeName' readonly="readonly" elementtype=nacessary>
	    	    </TD>
		    	<TD class= title5>���÷�ʽ</TD>
	   			<TD class= input5>
	   				<input class=codeno readonly="readonly" name="UseType"  
			          ondblclick="return showCodeList('riusetype', [this,UseTypeName],[0,1],null,null,null,1);" 
			          onkeyup="return showCodeListKey('riusetype', [this,UseTypeName],[0,1],null,null,null,1);" verify="���÷�ʽ|NOTNULL"><input 
			          class=codename name=UseTypeName readonly="readonly" elementtype=nacessary> 
	   			</TD> 
			</TR>
    	</Table>
	</Div>
  	
  	<Div id= "divTable3" style= "display: ''" >
    	<Table class= common>
    	  <TR class= common>
    	  	<TD class= title5>
    	      <Div id= "divTitle1" style= "display:none;" >��ͬ����</Div>
    	    </TD>
   				<TD class="input5">
   					<Div id= "divInput1" style= "display:none;" >
    	    		<input type="radio" name="ContModType"  value="0" OnClick="ChooseOldCont();" checked>�½���ͬ
			        <input type="radio" name="ContModType"  value="1" OnClick="ChooseOldCont();">��ͬ�޸�
    	    	</Div>
    	    </TD>
    	    <TD class= title5>
    	    	<Div id= "divTitle2" style= "display:none;" >�޸ĺ�ͬ����</Div>
    	    </TD>
    	    <TD class= input5>
    	    	
    	    	<Div id= "divInput2" style= "display:none;" >
    	    		<Input class= common name="ModRIContNo" id="ModReContCodeId" elementtype=nacessary> 
    	      </Div>
    	    </TD>
    	    
    	    <TD class= title5>
    	    	<Div id= "divTitle3" style= "display:none;" >�޸ĺ�ͬ����</Div>
    	    </TD>
    	    <TD class= input5>
    	    	<Div id= "divInput3" style= "display:none;" >
    	    		<Input class= common  name="ModRIContName" id="ModReContNameId" > 
    	      </Div>
    	    </TD>
    	    
    	  </TR>
   		</Table>
   	</Div>
   	
  	<Div id= "divTable2" style= "display:none;" >
    	<Table class= common>
    	  <TR class= common>
    	  	<TD class= title5>
    	      <Div id= "divTitle4" style= "display: ''" >�ٷֺ�ͬ��־</Div>
    	    </TD>
   				<TD class="input5">
   					<Div id= "divInput4" style= "display: ''" >
    	      	<input class=codeno readonly="readonly" name="ContType" CodeData="0|^01|������ͬ|^02|�ٷֺ�ͬ|^03|�����ٷ�|" 
    	      	ondblclick="return showCodeListEx('State', [this,ContTypeName],[0,1],null,null,null,1);" 
    	      	onkeyup="return showCodeListKeyEx('State', [this,ContTypeName],[0,1],null,null,null,1);"><input 
    	      	class=codename name=ContTypeName readonly="readonly">
    	    	</Div>
    	    </TD>
    	    <TD class= title5>
    	    	<Div id= "divTitle5" style= "display: ''" >��ͬ��������</Div>
    	    </TD>
    	    <TD class= input5>
    	    	<Div id= "divInput5" style= "display: ''" >
    	    		<input class=codeno readonly="readonly" name="RiskType" CodeData="0|^01|������|^02|ҽ����|^03|�ؼ���|" 
    	      		ondblclick="return showCodeListEx('State', [this,RiskTypeName],[0,1],null,null,null,1);" 
    	      		onkeyup="return showCodeListKeyEx('State', [this,RiskTypeName],[0,1],null,null,null,1);" ><input 
    	      		class=codename name=RiskTypeName readonly="readonly" elementtype=nacessary>
    	      </Div>
    	    </TD>
    	    <TD class= title5>
    	    	<Div id= "divTitle6" style= "display: ''" ></Div>
    	    </TD>
    	    <TD class= input5>
    	    	<Div id= "divInput6" style= "display: ''" >
    	      </Div>
    	    </TD>
    	  </TR>
   		</Table>
   	</Div>
  </Div>
  <br>
  <div id="divHidden" style= "display:none;">
	  <table>
	    <tr>
	      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	      	OnClick= "showPage(this,divLRCont2);"></td>
	  	<td class= titleImg>��ͬ�ֱ���Ϣ</td></tr>
	  </table>
	  <Div id= "divLRCont2" style= "display: ''">
			  <table class="common">		
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanContCessGrid"></span></td>
					</tr>
				</table>
		    </div>
	  </Div>
  <br>
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divCertifyType);"></td>
    	<td class= titleImg>��ͬǩ����</td></tr>
    </table>
    
   	<Div  id= "divCertifyType" style= "display: ''">
      <table  class= common>
          <tr  class= common>
            <td text-align:left colSpan=1>
          		<span id="spanSignerGrid" ></span>
        		</td>
      		</tr>
    	</table>
	</div>
	<br>
	
  <Div id= "divdivLRCont4" style= "display:none;">
     <table>
    	  <tr>
         <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;"
         	OnClick= "showPage(this,divdivLRCont3);"></td>
     	<td class= titleImg>��ͬ���ֱ���Ϣ</td></tr>
     </table>
    	<Div  id= "divdivLRCont3" style= "display: ''">
      	<table  class= common>
      	     <tr  class= common>
      	       <td text-align:left colSpan=1>
      	     		<span id="spanFactorGrid" ></span>
      	   		</td>
      	 		</tr>
     		</table>
   		</div>
  </Div> 
  <br>
   
  <input class="cssButton" type="button" value="��һ��" onclick="nextStep()">
  <input type="hidden" name="OperateType" >
  
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
