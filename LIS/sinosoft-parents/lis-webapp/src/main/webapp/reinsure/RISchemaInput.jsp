<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :ReComManage.jsp
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./RISchemaInput.js"></SCRIPT> 
<%@include file="./RISchemaInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
<form action="" method=post name=fm target="fraSubmit">
	<Div id= "divTempCessConclusion1" style= "display: ''">
		<table>
			<tr>
				<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
					OnClick= "showPage(this,divTempCessConclusion1);"></td>
				<td class= titleImg>�ٱ���ͬ</td></tr>
		</table>
  		<Table class= common>
	   		<TR class= common>
	   			<TD class= title5>�ٱ���ͬ���</TD>
	   			<TD class= input5>
	   				<Input class="codeno" name="RIContNo1" style="width:37.74%"
						ondblClick="showCodeList('ricontno',[this,RIContName1],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ricontno',[this,RIContName1],[0,1],null,null,null,1);" style= "display: ''"><Input 
						class= codename name= 'RIContName1' style="width:55.7%"   style= "display: ''" >
	   			</TD>
	   			<TD class= title5></TD>
		        <TD class= input5></TD>
				<TD class= title5></TD>
		        <TD class= input5></TD>
			</TR> 
	    </Table>
  		<BR>
  		<HR>
	</Div>
	<table>
		<tr>
			<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
				OnClick= "showPage(this,divLRCont);"></td>
			<td class= titleImg>�ٱ�����</td>
		</tr>
	</table>
  	<Div id= "divLRCont" style= "display: ''">
  		<table  class= common>
			<tr  class= common>
	            <td>
	          		<span id="spanPreceptSearchGrid" ></span>
				</td>
			</tr>
    	</table>
			<input class="cssButton" type="button" value="��  ѯ" onclick="queryClick()" style="display:none;" >
			<input class="cssButton" type="button" value="¼���·���" onclick="inputPrecept()" style="display:''" >
			<input class="cssButton" type="button" value="�ֳ����β�ѯ" onclick="accuRiskInfo()" style="display:''">
			<input class="cssButton" type="button" value="�����㷨��ѯ" onclick="receptAthInfo()" style="display:''">
			<input class="cssButton" type="button" value="��  ��" onclick="resetForm()" >
			<input class="cssButton" type="button" value="�  ��" name = "browse" onclick="browseForm()" style="display:none;">
			<input class="cssButton" type="button" value="��  ��" name = "edit" onclick="editForm()" style="display:''">
			<input class="cssButton" type="button" value="��  ��" onclick="ClosePage()" >
			<input class="cssButton" type="button" name='RelaTempPolBut' value="�����ٷֱ���" onclick="RelaTempPol()" style = "display:none;">
			<input class="cssButton" type="button" name='RelaGrpTempPolBut' value="���������ٷֱ���" onclick="RelaGrpTempPol()" style = "display:none;">
		</Div>
  	
  	<br><hr>
  	<table>
		<tr>
			<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
		     	OnClick= "showPage(this,divLRContInfo);"></td>
			<td class= titleImg>�ٱ�����</td>
		</tr>
	</table>
		<Div id="divLRContInfo" style="display: ''">
			<Table class=common>
				<TR class=common>
					<TD class=title5>�ٱ���ͬ���</TD>
					<TD class=input5><Input class=common name="RIContNo" readOnly
						elementtype=nacessary></TD>
					<TD class=title5>�ٱ��������</TD>
					<TD class=input5><Input class="common" name="RIPreceptNo"
						elementtype=nacessary verify="�ٱ��������|NOTNULL&len<20"></TD>
					<TD class=title5>�ٱ���������</TD>
					<TD class=input5><Input class="common" name="RIPreceptName"
						verify="�ٱ���������|NOTNULL&len<100" elementtype=nacessary></TD>
				</TR>
				<TR>
					<TD class=title5>
						<Div id="divAccumulate" style="display: ''">�ֳ����α���</Div>
					</TD>
					<TD class=input5><Input class="codeno" name="AccumulateDefNO"
						ondblClick="showCodeList('riaccmucode',[this,AccumulateDefName,RIRiskFeature],[0,1,2],null,null,null,1);"
						onkeyup="showCodeListKey('riaccmucode',[this,AccumulateDefName,RIRiskFeature],[0,1,2],null,null,null,1);"
						style="display: ''" verify="�ֳ����α���|NOTNULL" readonly="readonly"><Input
						class=codename name='AccumulateDefName' style="display: ''"
						elementtype=nacessary readonly="readonly"></TD>
					<TD class=title5>�ֱ���������</TD>
					<TD class=input5><Input class="codeno" name="PreceptType"
						ondblClick="showCodeList('riprecepttype',[this,PreceptTypeName],[0,1],null,fm.PType.value,'PreceptType',1);"
						onkeyup="showCodeListKey('riprecepttype',[this,PreceptTypeName],[0,1],null,fm.PType.value,'PreceptType',1);"
						readonly verify="�ֱ���������|NOTNULL"><Input class=codename
						name='PreceptTypeName' elementtype=nacessary readonly="readonly"> <Input
						name="PType" type="hidden"></TD>
					<TD class=title5>�ٱ�����״̬</TD>
					<TD class=input5><Input class="codeno" name="PreceptState"
						ondblClick="showCodeList('ristate',[this,PreceptStateName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ristate',[this,PreceptStateName],[0,1],null,null,null,1);"
						readonly value="02"><Input class=codename
						name='PreceptStateName' elementtype=nacessary readonly="readonly" value="δ��Ч">
					</TD>
				</TR>
				<TR style="display: ''" id="sfyf">
					<TD class=title5>�ֱ���˾��</TD>
					<TD class=input5><Input class="common" name="CompanyNum"
						elementtype=nacessary verify="�ֱ���˾��|NUM"></TD>
					<TD class=title5>�������</TD>
					<TD class=input5><Input class="common" name="DivisionNum"
						elementtype=nacessary verify="�������|NUM"></TD>
					<TD class=title5></TD>
					<TD class=input5></TD>
				</TR>
				<TR>
					<TD class=title5>�ֱ�����</TD>
					<TD class=input5><Input class="codeno" name="AttachFalg"
						ondblClick="showCodeList('ririskfeature',[this,RIRiskFeatureName],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('ririskfeature',[this,RIRiskFeatureName],[0,1],null,null,null,1);"
						readonly verify="�ֱ�����|NOTNULL"><Input class=codename
						name='RIRiskFeatureName' elementtype=nacessary readonly="readonly"></TD>
					<TD class=title5>
						<Div id="divArithmetic" style="display: ''">�ֱ��㷨</Div>
					</TD>
					<TD class=input5><Input class="codeno" name="ArithmeticID"
						ondblClick="showCodeList('riarithmetic',[this,ArithmeticName],[0,1]);"
						onkeyup="showCodeListKey('riarithmetic',[this,ArithmeticName],[0,1]);"
						style="display: ''"><Input class=codename
						name='ArithmeticName' style="display: ''"></TD>
					<TD class=title5><Div id="yfCode" style="display:none;">
���������ٱ�����</Div></TD>
					<TD class=input5>
						<Div id="yfName" style="display:none;">
							<Input class="codeno" name="YFType"
								ondblClick="showCodeList('rimainprecept',[this,YFName],[0,1],null,null,null,1);"
								onkeyup="showCodeListKey('rimainprecept',[this,YFName],[0,1],null,null,null,1);"
								readonly><Input class=codename name='YFName'
								elementtype=nacessary readonly="readonly">
						</Div>
					</TD>
				</TR>
				<tr>
					<TD class=title5><Div id="boCode" style="display:none;">����</Div></TD>
					<TD class=input5>
						<Div id="boName" style="display:none;">
							<Input class="codeno" name="BonusType"
								ondblClick="showCodeList('ribonustype',[this,BonusName],[0,1],null,null,null,1);"
								onkeyup="showCodeListKey('ribonustype',[this,BonusName],[0,1],null,null,null,1);"
								readonly><Input class=codename name='BonusName'
								elementtype=nacessary readonly="readonly">
						</Div>
					</TD>
				</tr>
			</Table>

			<Div id="divLRCont1" style="display:none;">
				<Table class=common>
					<TR>
						<TD class=title5>
							<Div id="divCalCyc" style="display: ''">�����ڼ�</Div>
						</TD>
						<TD class=input5><Input class="codeno" name="CalFeeTerm"
							CodeData="0|^01|���¼���|^02|�������|"
							ondblClick="showCodeListEx('',[this,CalFeeTermName],[0,1],null,null,null,1);"
							onkeyup="showCodeListKeyEx('',[this,CalFeeTermName],[0,1],null,null,null,1);"
							style="display: ''"><Input class=codename
							name='CalFeeTermName' style="display: ''"></TD>
						<TD class=title5>
							<Div id="divCalType" style="display: ''">���㷽ʽ</Div>
						</TD>
						<TD class=input5><Input class="codeno" name="CalFeeType"
							style="width: 37.74%" CodeData="0|^01|�����Ѽ���|^02|���������|"
							ondblClick="showCodeListEx('',[this,CalFeeTypeName],[0,1],null,null,null,1);"
							onkeyup="showCodeListKeyEx('',[this,CalFeeTypeName],[0,1],null,null,null,1);"
							style="display: ''"><Input class=codename
							name='CalFeeTypeName' style="width: 55.7%" style="display: ''">
						</TD>
						<TD class=title5></TD>
						<TD class=input5></TD>
					</TR>
				</Table>
			</Div>
			<Div id="divEdit1" style="display:none;">
				<input class="cssButton" type="button" value="��  ��"
					onclick="schemaSubmit()"> <input class="cssButton"
					type="button" value="��  ��" onclick="updateClick()"> <input
					class="cssButton" type="button" value="ɾ  ��"
					onclick="deleteClick()">
			</Div>
		</Div>

		<Div id= "divNormalRN" style= "display: ''" >	  
	  <br><hr>
	  <table class=common>
			<tr>
				<td class=titleImg style="width:50%">���������</td>
				<td class=titleImg>�ֱ���˾����</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td text-align:left valign=top colSpan=1 style="width:50%">
					<span id="spanContCessGrid"></span>
				</td>
				<td style="text-align:left;" valign=top colSpan=1>
					<span id="spanScaleLineGrid"></span>
				</td>
			</tr>
		</table>
		 <Div id= "divEdit2" style= "display:none;">  
			<input class="cssButton" type="button" value="��  ��" onclick="divComSubmit()" >
		 </Div>
	  <br><hr>
	  <table>
	    <tr>
	      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	      	OnClick= "showPage(this,divLRCont4);"></td>
	  	<td class= titleImg>�ֱ���������</td></tr>
	  </table>
	  <Div id= "divLRCont4" style= "display: ''">
		    <table class="common">		
				<tr class="common">
					<td style="text-align:left;" colSpan=1><span id="spanCessScaleGrid"></span></td>
				</tr>	
			</table>
			<br>
			<Div id= "divEdit3" style= "display:none;"> 
				<input class="cssButton" type="button" value="��  ��" onclick="scaleSubmit()" >
			</Div>
	  </Div>
	  <br><hr>
    </Div>	
	<table>
  	  <tr>
  	    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
  	    	OnClick= "showPage(this,divFeeRate2);"></td>
  		<td class= titleImg>����Ӷ��������</td></tr>
  	</table>
  	<Div id= "divFeeRate2" style= "display: ''">
	    <table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanFeeRateGrid"></span></td>
			</tr>
		</table>
		<Div id= "divEdit4" style= "display:none;"> 
		   	<input class="cssButton" type="button" value="��  ��" onclick="feerateSubmit()">
		</Div>
  	</Div> 	  
	<br><hr>	  

<Div id= "divUNNormalRN" style= "display:none;" >  
</Div>
	<br>
	<table>
  	    <tr>
	  	    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	  	    	OnClick= "showPage(this,divLRCont2);"></td>
	  		<td class= titleImg>�������ֱ�Ҫ����Ϣ</td>
	  	</tr>
  	</table>
  	<Div id= "divLRCont2" style= "display: ''">
	    <table class="common">		
			<tr class="common">
				<td style="text-align:left;" colSpan=1><span id="spanFactorGrid"></span></td>
			</tr>
		</table>
		<Div id= "divEdit5" style= "display:none;"> 
		   	<input class="cssButton" type="button" value="��  ��" onclick="factorSubmit()">
		</Div>
  	</Div> 
  	
  	<Div id= "divHidden" style= "display: '' ">
  		OperateNo <input type="text" name="OperateNo" >
  		CodeType <input type="text" name="CodeType" >
	  	OperateType <input type="text" name="OperateType" >
	  	Com <input type="text" name="Com">
   		Line <input type="text" name="Line">
   		UpDe <input type="text" name="UpDe">
   		DivisionNumBackup <Input type="text" name="DivisionNumBackup">
   		CompanyNumBackup <Input type="text" name="CompanyNumBackup">
   		SerialNo <Input type="text" name="SerialNo">
   		Remark<Input type="text" name="Remark">
  	</Div>
 	<Input name=HierarchyNumType style = "display:none;" >
 	<Input name=RIRiskFeature type="hidden" >
 	<Input name=remark type="hidden" >
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
