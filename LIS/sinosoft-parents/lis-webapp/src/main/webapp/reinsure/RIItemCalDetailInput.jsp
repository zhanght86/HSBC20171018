<%@include file="/i18n/language.jsp"%>
 <html>
<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">


<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "RIItemCalDetailInput.js"></SCRIPT>
<%@include file="./RIItemCalDetailInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
<form action="" method=post name=fm target="fraSubmit">
<table>
	<tr>
	    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	    	OnClick= "showPage(this,divCalItemType1);"></td>
		<td class= titleImg>��ѯ����</td>
	</tr>
</table>
<Div id="" style= "display: ''" align=left>
	<table  class= common>
   		<tr  class= common>
  	  		<td style="text-align:left;" colSpan=1>
				<span id="spanCalItemTypeGrid" ></span> 
	  		</td>
		</tr>
	</table>
</Div>
<Div id= "divCalItemType1" style= "display: ''" align=left>
<table class= common>
		<tr class= common>
			<TD class= title5>�㷨����</TD>
			<TD class=input5>
			 	 <Input class=common name=NEWArithmeticDefID  elementtype=nacessary readonly="readonly">
			</TD>
			<TD class= title5>��ϸ�㷨����</TD>
			<TD class=input5>
				<Input class="codeno" name="NewArithmeticType" 
		          ondblClick="showCodeList('riatithsubtype',[this,NewArithmeticTypeName],[0,1],null,null,null,1);"
		          onkeyup="showCodeListKey('riatithsubtype',[this,NewArithmeticTypeName],[0,1],null,null,null,1);" readonly="readonly"><Input class= codename 
		          name= 'NewArithmeticTypeName' readonly="readonly" elementtype = nacessary>
			</TD>	
			<TD class= title5></td>
			<TD class=input5></TD>
		</tr>
</table>
</Div>
<table class=common>
	<tr>
		<td>
			<INPUT class=cssButton name="addbutton" VALUE="��  ѯ"  TYPE=button onclick="getDetail();">
		</td>
	</tr>
</table>
<table>
	<tr> 
	    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	    	OnClick= "showPage(this,divCalItemDetail);"></td>
		<td class= titleImg>�㷨����ϸ��Ϣ</td>
	</tr>
</table>

<Div id= "divCalItemDetail" style= "display: ''" align=left>
	<table  class= common>
   		<tr  class= common>
  	  		<td style="text-align:left;" colSpan=1>
				<span id="spanKResultGrid" ></span> 
	  		</td>
		</tr>
	</table>
</Div>
<table>
	<tr>
	    <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	    	OnClick= "showPage(this,divCalItemType);"></td>
		<td class= titleImg>��ϸ�㷨����</td>
	</tr>
</table>
<div id = 'divCalItemType'>
	<table class= common>
		<tr class= common>
			<TD class= title5>�㷨����</TD>
			<TD class=input5>
			 	 <Input class=common name=ArithmeticDefID  elementtype=nacessary readonly="readonly">
			</TD>
			<TD class= title5>��ϸ�㷨����</TD>
			<TD class=input5>
				<Input class=common name=KArithmeticDefID elementtype = nacessary verify="��ϸ�㷨����|NOTNULL">
			</TD>
			<TD class= title5>��ϸ�㷨����</TD>
			<TD class=input5>
				<Input class=common name=KArithmeticDefName elementtype = nacessary verify="��ϸ�㷨����|NOTNULL">
			</TD>
		</tr>
		<tr class= common>
			<TD class= title5>��ϸ�㷨����</TD>
			<TD class=input5>
				<Input class="codeno" name="ArithmeticType" 
		          ondblClick="showCodeList('riatithsubtype',[this,ArithmeticTypeName],[0,1],null,null,null,1);"
		          onkeyup="showCodeListKey('riatithsubtype',[this,ArithmeticTypeName],[0,1],null,null,null,1);" readonly="readonly"><Input class= codename 
		          name= 'ArithmeticTypeName' readonly="readonly" verify="��ϸ�㷨����|NOTNULL" elementtype = nacessary >
			</TD>
			<TD class= title5>ҵ������</TD>
			<TD class=input5>
				<Input class=codeno name= Business verify="ҵ������|NOTNULL" readonly="readonly"  
					ondblClick="showCodeList('rieventtype',[this,BusinessName],[0,1],null,null,null,[1]);" 
					onkeyup="showCodeListKey('rieventtype',[this,BusinessName],[0,1],null,null,null,[1]);" readonly="readonly"><input class=codename 
					name=BusinessName  elementtype = nacessary  verify="ҵ������|NOTNULL" readonly="readonly">
			</TD>		
			<TD class= title5>����������</TD>
			 <TD class=input5>
				<Input class="codeno" name="CalItemID" 
		          ondblClick="showCodeList('ricalitem',[this,CalItemIDName],[0,1],null,null,null,1);"
		          onkeyup="showCodeListKey('ricalitem',[this,CalItemIDName],[0,1],null,null,null,1);"><Input class= codename 
		          name= 'CalItemIDName' readonly="readonly">
			</TD>	
		</tr>
		<tr class= common>
			 <TD class= title5>�������������</TD>
			 <TD class=input5>
				<Input class=codeno name="ItemCalType" verify="�������������|NOTNULL" readonly="readonly"  
					ondblClick="showCodeList('riitemcaltype',[this,ItemCalTypeName],[0,1],null,null,null,[1]);"
					onkeyup="showCodeListKey('riitemcaltype',[this,ItemCalTypeName],[0,1],null,null,null,[1]);"><input class=codename 
					name="ItemCalTypeName" readonly="readonly" elementtype = nacessary>
			</TD>
			 <TD class= title5>���������</TD>
			 <TD class=input5>
				<Input class=common name=CalItemOrder elementtype = nacessary  verify="���������|NOTNULL">
			</TD>
			<TD class= title5>�㷨����</TD>
			<TD class=input5>
				<Input class=common name=Remark >
			</TD>
		</tr>
	</table>
	
	<Div  id= "sqldiv" style= "display:none;" align=left>
		<table class=common>
				<tr  class= common>
					<TD  class= common>����SQL�㷨</TD>
				</tr>
				<tr  class= common>
					<TD  class= common>
						<textarea name="DistillSQL" verify="����SQL�㷨|len<4000" verifyorder="1" cols="70%" rows="5" witdh=50% class="common" ></textarea>
					</TD>
				</tr>
		</table>
	</div>	
	<Div  id= "classdiv" style= "display:none;" align=left>
		<table class=common>
			<tr class= common>
				<TD class= title5>�ɼ�������</TD>
				<TD class=input5>
				 	 <Input class=common name=DistillClass >
				</TD>
				<TD class= title5></TD>
				<TD class=input5></TD>
				<TD class= title5></TD>
				<TD class=input5></TD>
			</tr>
		</table> 	  
	</div>
	<Div  id= "numdiv" style= "display:none;" align=left>
		<table class=common>
			<tr class= common>
				<TD class= title5>�̶�����ֵ</TD>
				<TD class=input5>
					<Input class=common name=DoubleValue >
				</TD>
				<TD class= title5></TD>
				<TD class=input5></TD>
				<TD class= title5></TD>
				<TD class=input5></TD>
			</tr>
		</table> 	  
	</div>
</div>
<br>
<table class=common>
	<tr>
		<td>
		<INPUT class=cssButton name="querybutton" VALUE="¼�����㷨"  TYPE=button onclick="return newClick();">
			<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">
			<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
		</td>
	</tr>
</table>
<br>
<input type=hidden id="OperateType" name="OperateType">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
