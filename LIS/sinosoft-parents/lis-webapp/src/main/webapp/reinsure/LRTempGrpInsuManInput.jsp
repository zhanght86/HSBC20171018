<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�LRTempInsuManInput.jsp
//�����ܣ������ٷֹ���
//�������ڣ�2007-10-09 11:10:36
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
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

<SCRIPT src="LRTempGrpInsuManInput.js"></SCRIPT>
<%@include file="LRTempGrpInsuManInit.jsp"%>
<title>�ٱ��ظ�</title>
</head>
<body  onload="initElementtype();initForm();" >
<form method=post name=fm target="fraSubmit" action= "" >
<%@include file="../common/jsp/InputButton.jsp"%>
	
	<table id= "divIndInfo" style= "display: ''">
		<tr>
	    	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpTempInsuList);">
			</td>
			<td class= titleImg>�ٷ������б�</td>
		</tr>
	</table>
	<Div  id= "divGrpTempInsuList" style= "display: ''">
		<Table  class= common>
			<TR  class= common8>
				<TD  class= title58>
				<input type="radio" name="StateRadio"  value="1" onclick="showTempList()" checked ><%="�ٷִ���"%>
				<input type="radio" name="StateRadio"  value="2" onclick="showTempList()">�������
 				</TD>
			</TR>
		</Table>  	
  		<br>
		<Div id= "divPolInfo" style= "display:none;">
			<Table class= common>
		 	    <TR>
		  	    	<TD class= title5>�ٱ���ͬ���</TD>
		  	 		<TD class= input5 >
		  	 			<Input class=readonly name= RIContNo readonly="readonly" > 
		  	 		</TD>
		  	 		<TD class= title5>�ٷַ������</TD>
		  	    	<TD class= input5>
		  	    		<Input class=readonly name= RIPreceptNo readonly="readonly" > 
		  	    	</TD>
		  	    	<td  class="title5">����Ͷ������</td>
		  	    	<td class="input5">
		  	    		<Input class=readonly name= ProposalGrpContNo readonly="readonly" > 
		  	    	</td>
		  	    </TR>
		  	    <TR>
		  	    	<TD class= title5>����Ͷ������</TD>
	  	 			<TD class= input5 >
	  	 				<Input class=readonly name= ContNo readonly="readonly" > 
	  	 			</TD>
	  	 			<TD class= title5></TD>
		  	    	<TD class= input5></TD>
		  	    	<td  class="title5"></td>
		  	    	<td class="input5"></td>
		  	    </TR>
			</Table>
	 	</Div>		
	  	<Div id= "divTempInsuListGrid" style= "display: ''">
	  		<table  class= common>
		  	   	<tr  class= common>
		  	   		<td style="text-align:left;" colSpan=1 >
						<span id="spanTempInsuListGrid" ></span> 
					</td> 
				</tr> 
			</table> 
			<Div id= "div3" align="center" style= "display: '' ">
				<INPUT VALUE="��ҳ" class=cssButton TYPE=button onclick="turnPage1.firstPage();"> 
			  <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
			  <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage1.nextPage();"> 
			  <INPUT VALUE="βҳ" class=cssButton TYPE=button onclick="turnPage1.lastPage();">  
			</Div>
	  	</Div>  	
	</Div> 
  	<hr>
	<Div id= "divGrpPolInfo" style= "display: '' ">
		<table>
			<tr>
			  	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
				</td>
				<td class= titleImg>��ѯ����</td>
			</tr>
  		</table>
  		<Div  id= "divRelaMode" style= "display: ''">
	  		<Table class= common>
		  	    <TR>
		  	    	<TD class= title5>������ʽ</TD>
					<TD class="input5">
			  	        <input class=codeno readonly="readonly" name="RelaMode" CodeData="0|^01|<%="���˱���"%>|^02|���ϼƻ�|^03|����|" 
				  	        ondblclick="return showCodeListEx('State', [this,ContTypeName],[0,1],null,null,null,1);" 
				  	        onkeyup="return showCodeListKeyEx('State', [this,ContTypeName],[0,1],null,null,null,1);" verify="��������|NOTNULL"><input 
				  	        class=codename name=ContTypeName readonly="readonly" >
					</TD>
		  	 		<TD class= title5>
		  	 			<Div id= "divContPlanCodeName" style= "display:none;">���ϼƻ�</Div>
		  	    	</TD>
		  	    	<TD class= input5>
		  	    		<Input class= common name= ContPlanMode style= "display:none;" > 
		  	    	</TD>
		  	    	<td class="title5">
		  	    		<Div id= "divDutyCodeName" style= "display:none;">���δ���</Div>
		  	    	</td>
		  	    	<td class="input5">
		  	    		<Input class= common name= DutyCode1 style= "display:none;" > 
		  	    	</td>
		  	    </TR>
			</Table>
		</Div> 	
		<Div  id= "divSearch" style= "display:none;">
			<Table class= common>
		  	    <TR>
		  	    	<TD class= title5>
���屣����
	  	 			</TD>
	  	 			<TD class= input5 >
	  	 				<Input class= common name= GrpContNo readonly="readonly" > 
	  	 			</TD>
	  				<TD class= title5>���ϼƻ�</TD>
		  	  		<TD class= input5>
						<Input class="codeno" name='ContPlanCode'
					  	    ondblClick="showCodeList('lrgrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
					  	    onkeyup="showCodeListKey('lrgrpcontplan',[this,ContPlanCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);" ><Input 
					  	    class= codename name='ContPlanCodeName' >
		  	  		</TD>
		  	  		<td  text-align:right class="title5">���ֱ���</td>
		  	  		<td class="input5">
						<Input class="codeno" name='RiskCode'
					  	    ondblClick="showCodeList('lrgrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
					  	    onkeyup="showCodeListKey('lrgrpcontrisk',[this,RiskCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);" ><Input 
					  	    class= codename name='RiskCodeName'  >
		  	  		</td>
		  	    </TR>
		  	    <TR>
		  	  		<TD class= title5>���δ���</TD>
		  			<TD class= input5 >
		  				<Input class="codeno" name='DutyCode'
					  	    ondblClick="showCodeList('lrgrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);"
					  	    onkeyup="showCodeListKey('lrgrpcontduty',[this,DutyCodeName],[0,1],null,null,fm.GrpContNo.value,1,180);" ><Input 
					  	    class= codename name='DutyCodeName' >
		  			</TD>
		  	 		<TD class= title5>�����˱���</TD>
		  	    	<TD class= input5>
		  	    		<Input class= common name= InsuredNo > 
		  	    	</TD>
		  	    	<td  text-align:right class="title5">����������</td>
		  	    	<td class="input5">
		  	    		<Input class= common name= InsuredName> 
		  	    	</td>
		  	    </TR>
			</Table>
  	  		<Div id= "divSearch2" style= "display:none;"> 
  	  			<Table class= common>
					<TR>
						<TD class= title5>�ٷֺ˱�����</TD>
						<TD class= input5 >
							<input class=codeno readonly="readonly" name="TempUWConclusionConf" CodeData="0|^00|��ͬ�ֱ�|^02|�����|^04|�ٷ�δʵ��|" 
						    ondblclick="return showCodeListEx('', [this,TempUWConclusionConfName],[0,1],null,null,null,1);" 
						    onkeyup="return showCodeListKeyEx('', [this,TempUWConclusionConfName],[0,1],null,null,null,1);" verify="��������|NOTNULL"><input 
						    class=codename name=TempUWConclusionConfName readonly="readonly" >
						</TD>
						<TD class= title5>
						</TD>
						<TD class= input5>
						</TD>
						<td class="title5">
						</td>
						<td class="input5">
						</td>
					</TR>
				</Table>
			</Div>
		</Div> 
		<br>
  	
		<Div id='divButton1' style= "display:none;">
	  		<INPUT VALUE="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;ѯ&nbsp;&nbsp;" class=cssButton TYPE=button onclick="SearchRecord();">  &nbsp;&nbsp;
	  		<INPUT VALUE="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;" class=cssButton TYPE=button onclick="ResetForm();"> &nbsp;&nbsp;
	  		<table>
					<tr>
				    	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearchResult);">
						</td>
						<td class= titleImg>��ѯ���</td>
					</tr>
	  		</table>
			<Div  id= "divSearchResult" style= "display: ''">
				<table  class= common>
		  		   	<tr  class= common>
		  		   		<td style="text-align:left;" colSpan=1 >
							<span id="spanSearchResultGrid" ></span> 
						</td> 
					</tr> 
				</table> 
				<Div id= "div4" align="center" style= "display: '' ">
					<INPUT VALUE="��ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
				  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
				  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
				  	<INPUT VALUE="βҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();">  
				</Div>
  			</Div>   		
  		</Div>
	</Div>
	<br>
	
	<Div  id='divButton2' style= "display: ''">
  	<Table class= common>
  		<TR>
  			<TD class= title5>�ٷֽ���</TD>
  			<TD class= input5 >
  				<input class=codeno readonly="readonly" name="TempUWConclusion" CodeData="0|^00|��ͬ�ֱ�^04|�ٷ�δʵ��|^99|ȡ������|" 
  		    ondblclick="return showCodeListEx('State', [this,TempUWConclusionName],[0,1],null,null,null,1);" 
  		    onkeyup="return showCodeListKeyEx('State', [this,TempUWConclusionName],[0,1],null,null,null,1);" verify="�ٷֺ˱�����|NOTNULL"><input 
  		    class=codename name=TempUWConclusionName readonly="readonly" >
  			</TD>
  			<TD class= input5>
  				<INPUT VALUE="��ѡ�н���½���" name="SelConClusion" class=cssButton TYPE=button onclick="TempConclusionSel();">
  			</TD>
  			<TD class= title5>
					<INPUT VALUE="�����н���½���" name="AllConClusion" class=cssButton TYPE=button onclick="TempConclusionAll();">
  			</TD>
  			<TD class= title5>
  			</TD>
  			<TD class="title5">
  			</TD>
  			<td class="input5"></td>
  		</TR>
  	</Table>
  	<hr><br>
  	<Div  id= "divButton3" style= "display: ''"> 
  		<INPUT VALUE="�� ʱ �� ��" class=cssButton TYPE=button onclick="TempCessButton();"> 
  		&nbsp;&nbsp;
  		<INPUT VALUE="�� �� �� ��" class=cssButton TYPE=button onclick="AuditEnd();"> 
  	</Div>
	</Div>
	<br>
	<Div  id= "divTempConlusionList" style= "display:none;"> 
	  	<table>
				<tr>
			    	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRelaList);">
					</td>
					<td class= titleImg>
�ٷֽ����б�
					</td>
				</tr>
	  	</table>
		<Div  id= "divRelaList" style= "display: ''">
			<!--�ŵ������б�-->
			<Div  id= "divIndRelaList" style= "display: ''">
				<table  class= common>
		  		   	<tr  class= common>
		  		   		<td style="text-align:left;" colSpan=1 >
							<span id="spanIndRelaListGrid" ></span> 
						</td> 
					</tr> 
				</table> 
				<Div id= "div7" align="center" style= "display: '' ">
					<INPUT VALUE="��ҳ" class=cssButton TYPE=button onclick="turnPage5.firstPage();"> 
				  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage5.previousPage();"> 					
				  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage5.nextPage();"> 
				  	<INPUT VALUE="βҳ" class=cssButton TYPE=button onclick="turnPage5.lastPage();">  
				</Div>
  			</Div>
  			<!--��������б�-->
  	  		<Div  id= "divGrpRelaList" style= "display: ''">
				<table  class= common>
	  		   		<tr  class= common>
		  		   		<td style="text-align:left;" colSpan=1 >
								<span id="spanRelaListGrid" ></span> 
						</td> 
					</tr> 
				</table> 
				<Div id= "div6" align="center" style= "display: '' ">
					<INPUT VALUE="��ҳ" class=cssButton TYPE=button onclick="turnPage3.firstPage();"> 
				  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage3.previousPage();"> 					
				  	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage3.nextPage();"> 
				  	<INPUT VALUE="βҳ" class=cssButton TYPE=button onclick="turnPage3.lastPage();">  
				</Div>
  			</Div>
		</Div> 
  	<Div  id= "divRelaDel" style= "display:none;">
  		<Table class= common>
  		    <TR>
  		    	<TD class= title5>ȡ���ٷֽ���</TD>
  		 			<TD class= input5 >
  		 				<input class=codeno readonly="readonly" name="DeleteType" CodeData="0|^01|ȡ��ѡ����^02|ȡ����ͬ�ֱ�����^03|ȡ���ٷ�δʵ�ֽ���|" 
			  		        ondblclick="return showCodeListEx('State', [this,DeleteTypeName],[0,1],null,null,null,1);" 
			  		        onkeyup="return showCodeListKeyEx('State', [this,DeleteTypeName],[0,1],null,null,null,1);"><input 
			  		        class=codename name=DeleteTypeName readonly="readonly" >
  		 			</TD>
  		    	<TD class= input5>
  		    		<INPUT VALUE="ȡ���ٷֽ���" class=cssButton TYPE=button onclick="DeleteRelaList();"> 
  		    	</TD>
  		    	<TD class= title5>
  		    	</TD>
  		    	<td  text-align:right class="title5">
  		    	</td>
  		    	<td class="input5">
  		    	</td>
  		    </TR>
  		</Table>
		</Div>
	</Div>
	<Div id="divText1" style="display:none;">
		OpeFlag<input type="text" name="OpeFlag">
		ContType<input type="text" name="ContType">
		DeTailFlag<input type="text" name="DeTailFlag">
	</Div>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>