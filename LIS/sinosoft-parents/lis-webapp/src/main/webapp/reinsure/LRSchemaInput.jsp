<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :ReComManage.jsp
//function :ReComManage
//Creator :
//date :2006-08-14
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
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

<SCRIPT src = "./LRSchemaInput.js"></SCRIPT> 
<%@include file="./LRSchemaInit.jsp"%>
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
						ondblClick="showCodeList('lrcontno',[this,RIContName1],[0,1],null,null,null,1);"
						onkeyup="showCodeListKey('lrcontno',[this,RIContName1],[0,1],null,null,null,1);" style= "display: ''"><Input 
						class= codename name= 'RIContName1' style="width:55.7%"   style= "display: ''">
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
		<div align=center>
			<input VALUE="��ҳ" TYPE="button" onclick="turnPage.firstPage();" class="cssButton">
			<input VALUE="��һҳ" TYPE="button" onclick="turnPage.previousPage();" class="cssButton">
			<input VALUE="��һҳ" TYPE="button" onclick="turnPage.nextPage();" class="cssButton">
			<input VALUE="βҳ" TYPE="button" onclick="turnPage.lastPage();" class="cssButton">
		</div>
		<input class="cssButton" type="button" value="��ѯ" onclick="queryClick()" style="display:none;" >
		<input class="cssButton" type="button" style="display:''" value="¼���·���" onclick="inputPrecept()" >
		<input class="cssButton" type="button" style="display:''" value="�ۼƷ��ղ�ѯ" onclick="accuRiskInfo()" >
		<input class="cssButton" type="button" value="����" onclick="resetForm()" >
		<input class="cssButton" type="button" value="� ��" name = "browse" onclick="browseForm()" style="display:none;">
		<input class="cssButton" type="button" value="�� ��" name = "edit" onclick="editForm()" style="display:''">
		<input class="cssButton" type="button" value="�ر�" onclick="ClosePage()" >
		<input class="cssButton" type="button" name='RelaTempPolBut' value="���������ٷֱ���" onclick="RelaTempPol()" style = "display:none;">
		<input class="cssButton" type="button" name='RelaGrpTempPolBut' value="���������ٷֱ���" onclick="RelaGrpTempPol()" style = "display:none;">			
  	</Div>
  	
  	<br><hr>
  	<table>
		<tr>
			<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
		     	OnClick= "showPage(this,divLRCont1);"></td>
			<td class= titleImg>�ٱ�����</td>
		</tr>
	</table>
	<Div id= "divLRCont1" style= "display: ''">
		<Table class= common>
	   		<TR class= common>
	   			<TD class= title5>�ٱ���ͬ���</TD>
	   			<TD class= input5>
	   				<Input class= common name="RIContNo" readonly="readonly" elementtype=nacessary> 
	   			</TD>
	   			<TD class= title5>�ٱ��������</TD>
				<TD class= input5>
	   				<Input class="common"  name="RIPreceptNo" elementtype=nacessary>
	   			</TD>
	   			<TD class= title5>�ٱ���������</TD>
				<TD class= input5>
					<Input class="common" name="RIPreceptName" >
				</TD>
			</TR> 
			<TR>
		      	<TD class= title5>
		      		<Div id= "divAccumulate" style= "display: ''">�ۼƷ������</Div>
		        </TD>
		        <TD class= input5>
					<Input class="codeno" name="AccumulateDefNO" style="width:37.74%"
			          ondblClick="showCodeList('lraccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);"
			          onkeyup="showCodeListKey('lraccmucode',[this,AccumulateDefName],[0,1],null,null,null,1);" style= "display: ''"><Input 
			          class= codename name= 'AccumulateDefName' style="width:55.7%"   style= "display: ''" elementtype=nacessary>
				</TD>
		      	<TD class= title5>�������</TD>
		   			<TD class= input5 >
		   				<Input class="common" name="DivisionNum"  elementtype=nacessary>
		   			</TD>
		   			<TD class= title5>�ֱ���˾��</TD>
		        <TD class= input5>
		   				<Input class="common" name= "CompanyNum"  elementtype=nacessary>
				</TD>
			</TR>
			<TR>
	     		<TD class= title5>�ֱ�����</TD>
		        <TD class= input5>
		   				<Input class="codeno" name="ReinsuranceType" style="width:37.74%" CodeData="0|^01|�����ֱ�|^02|�Ǳ����ֱ�|" 
		          ondblClick="showCodeListEx('reinsurancetype',[this,ReinsuranceTypeName],[0,1],null,null,null,1);"
		          onkeyup="showCodeListKeyEx('reinsurancetype',[this,ReinsuranceTypeName],[0,1],null,null,null,1);" ><Input 
		          class= codename name= 'ReinsuranceTypeName' style="width:55.7%" elementtype=nacessary>
	   			</TD>
			   	<TD class= title5>�ֱ���ʽ</TD>
	   			<TD class= input5>
	   				<Input class="codeno" name="ReinsuranceSubType" style="width:37.74%" 
	        	  ondblClick="showCodeList('reinsuresubtype',[this,ReinsuranceSubTypeName],[0,1],null,fm.ReinsuranceType.value,'othersign',1);"
	        	  onkeyup="showCodeListKey('reinsuresubtype',[this,ReinsuranceSubTypeName],[0,1],null,fm.ReinsuranceType.value,'othersign',1);" ><Input 
	        	  class= codename name= 'ReinsuranceSubTypeName' style="width:55.7%" elementtype=nacessary>
	   			</TD>  
	   			<TD class= title5>�����ֵ����</TD>
	   			
	   			<TD class= input5>
	   				<Input class="common" name= "HierarchyNumTypeName"  elementtype=nacessary>
	   			</TD>    			 			
			</TR>					
			<TR>	
	     		<TD class= title5>�ֱ���������</TD>
		        <TD class= input5>
		   				<Input class="codeno" name="PreceptType" style="width:37.74%" CodeData="0|^01|�����ٱ�����|^02|��ʱ�ֱ�����|" 
		          ondblClick="showCodeListEx('precepttype',[this,PreceptTypeName],[0,1],null,null,null,1);"
		          onkeyup="showCodeListKeyEx('precepttype',[this,PreceptTypeName],[0,1],null,null,null,1);" ><Input 
		          class= codename name= 'PreceptTypeName' style="width:55.7%" elementtype=nacessary>
	   			</TD>    			
	   			<TD class= title5>�ٱ�����״̬</TD>
	   			<TD class= input5>
	   				<Input class="codeno" name="PreceptState" style="width:37.74%" CodeData="0|^01|��Ч|^02|δ��Ч" 
	        	  ondblClick="showCodeListEx('preceptstate',[this,PreceptStateName],[0,1],null,null,null,1);"
	        	  onkeyup="showCodeListKeyEx('preceptstate',[this,PreceptStateName],[0,1],null,null,null,1);" ><Input 
	        	  class= codename name= 'PreceptStateName' style="width:55.7%" elementtype=nacessary>
	   			</TD>	
	   			<TD class= title5>
	      			<Div id= "divArithmetic" style= "display:none;">�ֱ��㷨</Div>
				</TD>				
	   			<TD class= input5>
	   				<Input class="codeno" name="ArithmeticID" style="width:37.74%" 
			          ondblClick="showCodeList('lrarithmetic',[this,ArithmeticName],[0,1],null,fm.AccumulateDefNO.value,'StandbyString2',1);" 
			          onkeyup="showCodeListKey('lrarithmetic',[this,ArithmeticName],[0,1],null,fm.AccumulateDefNO.value,'StandbyString2',1);" style= "display:none;" ><Input 
			          class= codename name= 'ArithmeticName' style="width:55.7%"  style= "display:none;"> 
	   			</TD>		   			   			
			</TR>	
		</Table>
	  <Div id= "divLRCont1" style= "display:none;">
 			<Table class= common>
		      <TR>
		      	<TD class= title5>
		          <Div id= "divCalCyc" style= "display: ''">�����ڼ�</Div>
		        </TD>
		        <TD class= input5>
		   				<Input class="codeno" name="CalFeeTerm" CodeData="0|^01|���¼���|^02|�������|" 
		          ondblClick="showCodeListEx('',[this,CalFeeTermName],[0,1],null,null,null,1);" 
		          onkeyup="showCodeListKeyEx('',[this,CalFeeTermName],[0,1],null,null,null,1);" style= "display: ''"><Input 
		          class= codename name= 'CalFeeTermName' style= "display: ''" >
		   			</TD> 
		      	<TD class= title5>
		   				<Div id= "divCalType" style= "display: ''">���㷽ʽ</Div>
		   			</TD>
		        <TD class= input5>
		        	<Input class="codeno" name="CalFeeType" style="width:37.74%" CodeData="0|^01|�����Ѽ���|^02|���������|" 
		          ondblClick="showCodeListEx('',[this,CalFeeTypeName],[0,1],null,null,null,1);"
		          onkeyup="showCodeListKeyEx('',[this,CalFeeTypeName],[0,1],null,null,null,1);" style= "display: ''"><Input 
		          class= codename name= 'CalFeeTypeName' style="width:55.7%" style= "display: ''">
		       	</TD>
		   			<TD class= title5></TD>
		        <TD class= input5></TD>
		      </TR>
	    	</Table>
	  </Div>
	    <Div id= "divEdit1" style= "display:none;">
		   	<input class="cssButton" type="button" value="����" onclick="schemaSubmit()" >
		  	<input class="cssButton" type="button" value="�޸�" onclick="updateClick()" >
		  	<input class="cssButton" type="button" value="ɾ��" onclick="deleteClick()" >
		</Div> 
	  </Div>
	  
	  <br><hr>
	  <table class=common>
			<tr>
				<td class=titleImg style="width:40%">���������</td>
				<td class=titleImg>�ֱ���˾����</td>
			</tr>
		</table>
		<table class=common>
			<tr class=common>
				<td text-align:left valign=top colSpan=1 style="width:40%">
					<span id="spanContCessGrid"></span>
				</td>
				<td style="text-align:left;" valign=top colSpan=1>
					<span id="spanScaleLineGrid"></span>
				</td>
			</tr>
		</table>
		 <Div id= "divEdit2" style= "display:none;">  
			<input class="cssButton" type="button" value="����" onclick="divComSubmit()" >
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
				<input class="cssButton" type="button" value="����" onclick="scaleSubmit()" >
			</Div>
	  </Div>
	  <br><hr>
	  
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
		   	<input class="cssButton" type="button" value="����" onclick="feerateSubmit()">
		</Div>
  	</Div> 	  
	<br><hr>	  
	  
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
		   	<input class="cssButton" type="button" value="����" onclick="factorSubmit()">
		</Div>
  	</Div> 
  	
  	<Div id= "divHidden" style= "display: '' ">
  		OperateNo <input type="text" name="OperateNo" >
  		CodeType <input type="text" name="CodeType" ><!--05:����-->
	  	OperateType <input type="text" name="OperateType" >
	  	Com <input type="text" name="Com">
   		Line <input type="text" name="Line">
   		UpDe <input type="text" name="UpDe">
   		DivisionNumBackup <Input type="text" name="DivisionNumBackup">
   		CompanyNumBackup <Input type="text" name="CompanyNumBackup">
   		SerialNo <Input type="text" name="SerialNo">
  	</Div>
 	<Input class="codeno" name=HierarchyNumType style = "display:none;" >
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>