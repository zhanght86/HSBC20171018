<%@include file="/i18n/language.jsp"%>
<html>

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
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "RIItemCal.js"></SCRIPT>
<%@include file="./RIItemCalInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
	<form action="" method=post name=fm target="fraSubmit">
	<table>
   	  	<tr>
        	<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        		OnClick= "showPage(this,divArithmetic);"></td>
    		<td class= titleImg>�㷨��Ϣ��ѯ</td>
    	</tr>
	</table>
	<Div id= "divArithmetic" style= "display: ''" >
		<table class= common>
			<tr class= common>
				<TD class= title5>�㷨�������</TD>
				<TD class=input5>
				 	 <Input class=common name=ArithmeticDefID >
				</TD>
				<TD class= title5>�㷨����</TD>
				<TD class=input5>
				 	  <Input class=codeno name= ArithContType  readonly="readonly"  
				 	  	CodeData="0|^01|��ͬ�����㷨^2|�ٷַ����㷨" 
				 	  	ondblClick="showCodeListEx('riatithconttype',[this,ArithContTypeName],[0,1],null,null,null,[1]);" 
				 	  	onkeyup="showCodeListKeyEx('riatithconttype',[this,ArithContTypeName],[0,1],null,null,null,[1]);"><input class=codename 
				 	  	name=ArithContTypeName readonly="readonly">
				</TD>
				<TD class= title5></TD>
				<TD class=input5></TD>				
			</tr>
		</table>
	</div>
	<br>
	<INPUT VALUE="�㷨��Ϣ��ѯ" TYPE=button class= cssbutton onclick="billQuery()">
	<br><br>
	<Div  id= "Task" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanResultGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<p></p>
	    <Div  id= "divPage" align=center style= "display: '' ">
		      <INPUT CLASS=common VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();">
		      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
		      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
		      <INPUT CLASS=common VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
	    </Div>				
	</Div>
    <br>
	<Div  id= "Task1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanKResultGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <p></p>
      <Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=common VALUE="��ҳ" TYPE=button onclick="turnPage1.firstPage();">
      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();">
      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();">
      <INPUT CLASS=common VALUE="βҳ" TYPE=button onclick="turnPage1.lastPage();">
      </Div>					
    </Div>
    
	<table class= common>
		<tr class= common>
				 <TD class= title5>
��ϸ�㷨�������
				 </TD>
				 <TD class=input5>
				 	 <!--<Input class=common name=ArithmeticDefID elementtype=nacessary>-->
				 	 <Input class=common name=KArithmeticDefID  elementtype=nacessary>
				</TD>
				<TD class= title5>
��ϸ�㷨����
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=ArithmeticID elementtype = nacessary >
				</TD>
		</tr>
				<tr class= common>
				 <TD class= title5>
��ϸ�㷨����
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=ArithmeticName elementtype = nacessary >
				</TD>
				<TD class= title5>��ϸ�㷨����</TD>
				<TD class=input5>
				 	  <Input class=codeno name= ArithmeticType verify="�㷨����|NOTNULL" readonly="readonly"  
				 	  	CodeData="0|^00|������ȡ�㷨^02|���������㷨^03|����׼���㷨^04|�ٱ��������������^05|�ֱ��������㷨^14|�����Ժ��㷨" 
				 	  	ondblClick="showCodeListEx('arithmetictype',[this,ArithmeticTypeName],[0,1],null,null,null,[1]);" 
				 	  	onkeyup="showCodeListKeyEx('arithmetictype',[this,ArithmeticTypeName],[0,1],null,null,null,[1]);"><input class=codename 
				 	  	name=ArithmeticTypeName readonly="readonly" elementtype = nacessary>
				</TD>
				</TD>
		</tr>
		
		
				<tr class= common>
				 <TD class= title5>
���������
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=CalItemID  elementtype = nacessary >
				</TD>
				<TD class= title5>
����������
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=CalItemName elementtype = nacessary >
				</TD>
		</tr>
		
				<tr class= common>
				 <TD class= title5>
���������
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=CalItemOrder elementtype = nacessary >
				</TD>
				<TD class= title5>
���������
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=CalItemType >
				</TD>
		</tr>
		
				<tr class= common>
				 <TD class= title5>
�������������
				 </TD>
				 <TD class=input5>
				 	  <Input class=codeno name= DistillMode verify="���ݲɼ���ʽ|NOTNULL" readonly="readonly"  
				 	  	CodeData="0|^1|�̶�ֵ^2|ͨ��Sql^3|ͨ����" 
				 	  	ondblClick="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" 
				 	  	onkeyup="showCodeListKeyEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);"><input class=codename 
				 	  	name=DistillModeName readonly="readonly">
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
				<TD class= title5>
�ɼ�������
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=DistillClass >
				</TD>
				<TD class= title5>
				 </TD>
				 <TD class=input5>
				</TD>
	</tr>
 </table> 	  
</div>
<Div  id= "numdiv" style= "display:none;" align=left>
	<table class=common>
	 <tr class= common>
				<TD class= title5>
�̶�����ֵ
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=DoubleValue >
				</TD>
				<TD class= title5>
				 </TD>
				 <TD class=input5>
				</TD>
	</tr>
 </table> 	  
</div>

	<table class= common>
		<tr class= common>
				 <TD class= title5>
�������洢�ֶ�
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=TarGetClumn elementtype = nacessary >
				</TD>
				<TD class= title5>
����
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=ReMark >
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
�����ַ�������1
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=StandbyString1  >
				</TD>
				<TD class= title5>
�����ַ�������2
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=StandbyString2 >
				</TD>
		</tr>
						<tr class= common>
				 <TD class= title5>
�����ַ�������3
				 </TD>
				 <TD class=input5>
				 	 <Input class=common name=StandbyString3 >
				</TD>
		</tr>
		
	</table> 	

<hr></hr>

  <INPUT VALUE="���" TYPE=button class= cssbutton name= addbutton onclick="add()">   
  <INPUT VALUE="�޸�" TYPE=button class= cssbutton name= updatebutton onclick="update()">
  <INPUT VALUE="ɾ��" TYPE=button class= cssbutton name= deletebutton onclick="cd()">
	</table>
</Div>
 <input type=hidden id="OperateType" name="OperateType">
 <INPUT TYPE=hidden NAME=tempAcquisitionID VALUE=''>
 <INPUT TYPE=hidden NAME=tempAcquisitionType VALUE=''>
 <INPUT TYPE=hidden NAME=tempCostOrDataID VALUE=''>
 <INPUT type=hidden name=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>