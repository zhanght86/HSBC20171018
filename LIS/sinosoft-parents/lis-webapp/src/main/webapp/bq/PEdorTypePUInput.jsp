<html> 
<% 
//�������ƣ� 
//�����ܣ����˱�ȫ
//�������ڣ�2005-4-28 09:18����
//������  ��Nicholas
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 

  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypePU.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypePUInit.jsp"%>
  
  <title>�������</title> 
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypePUSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
 <div class=maxbox1>
 <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
      <TD class = title > ������ </TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>������������</TD>
    	<TD class = input>    		
    		<Input class="readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class="readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
  </TABLE>
  </div>
 <TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo1);">
      </td>
      <TD class= titleImg>
        �ͻ�������Ϣ 
      </TD>
   </TR>
   </TABLE>
   <Div  id= "divAGInfo1" class=maxbox1 style= "display: ''">
   <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > Ͷ��������</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=AppntName id=AppntName >
      </TD>
      <TD class = title > ֤������ </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=AppntIDType id=AppntIDType><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true>
      </TD>
      <!--TD class = input >
      	<input class="readonly wid" readonly name=AppntIDType>
      </TD-->
     
      <TD class = title > ֤������ </TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>����������</TD>
    	<TD class = input>    		
    		<Input class="readonly wid" readonly name=InsuredName id=InsuredName ></TD>
    	</TD>
    	<TD class =title>֤������</TD>
      <TD class = input >
      	<Input class=codeno  readonly name=InsuredIDType id=InsuredIDType><input class=codename name=InsuredIDTypeName id=InsuredIDTypeName readonly=true>
      </TD>
    	<!--TD class = input>
    		<Input class="readonly wid" readonly name=InsuredIDType ></TD>
    	</TD-->
    	<TD class = title>֤������</TD>
    	<TD class = input>
    	<Input class="readonly wid" readonly name = InsuredIDNo id=InsuredIDNo ></TD>
    </TR>
  </TABLE>
</Div>

    <Div  id= "divMultiPol" style= "display: none">
	    <table>
	            <tr>
	                <td>
	                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
	                </td>
	                <td class= titleImg> ����������� </td>
	            </tr>
	    </table>
        
	    <Div  id= "divPolGrid" style= "display: ''">
	        <table  class= common>
	            <tr  class= common>
	                <td text-align: left colSpan=1>
	                    <span id="spanPolGrid" >
	                    </span> 
	                </td>
	            </tr>
	        </table>    
	    </Div>
	</Div>

   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
      </td>
      <td class= titleImg>
        ���ֻ�����Ϣ
      </td>
   </tr>
   </table>
	 
    <Div  id= "divPol" class=maxbox1 style= "display: ''">
      	<table  class= common>
      		<tr class common>
      			<td class = title>���ִ���</td>
      			<td class = input>
      				<input class="readonly wid" readonly name=RiskCode id=RiskCode>
      			</td>
      			<td class = title>��������</td>
      			<td class = input>
      				<input class="readonly wid" readonly name=RiskName id=RiskName>
      			</td>
		       <TD  class= title > ���Ѷ�Ӧ��</TD>
		       <TD  class= input > 
               <input class="readonly wid" readonly name=PaytoDate id=PaytoDate >
           </TD>
					</tr>    	

  			<tr>
		       <TD  class= title >���ѱ�׼</TD>
		       <TD  class= input > 
               	<input class="readonly wid" readonly name=Prem id=Prem >
               </TD>
		      <TD  class= title >��������</TD>
		      <TD  class= input > 
              <input class="readonly wid" readonly name=Amnt id=Amnt >
          </TD>		
       		 <TD  class= title >�� �� </TD>
		       <TD  class= input > 
               <input class="readonly wid" readonly name=Mult id=Mult >
           </TD>  	
  			</tr> 
  			
  			<tr>
  				<td class = title>�ۼƺ�������</td>
  				<td class = input>
  					<input class="readonly wid" readonly name=AddUpBonus id=AddUpBonus>
  				</td>
  				<td class = title>���˺���</td>
  				<td class = input>
  					<input class="readonly wid" readonly name=FinaleBonus id=FinaleBonus>
  				</td>
  				<td class = title>�����ֽ��ֵ</td>
  				<td class = input>
  					<input class="readonly wid" readonly name=tatolMoney id=tatolMoney>
  				</td>
					<!--td class = title></td>
					<td class = input>
						<input class="readonly wid" readonly name = >
					</td>
					<td class = title></td>
					<td class = input>
						<input class="readonly wid" readonly name = >
					</td-->
  			</tr>	
  			
    	</table>   
     </Div>

   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol3);">
      </td>
      <td class= titleImg>
        ����������Ϣ
      </td>
   </tr>
   </table>
<Div id = "divPol3" class=maxbox1 style= "display: ''">
	<table class = common>
		<tr class = common>
			
			<td class = title>��������ִ���</td>
			<td class = input>
				<input class="readonly wid" readonly name =NewRiskCode id=NewRiskCode >
			</td>
			<td class = title>�������������</td>
			<td class = input>	
				<input class="readonly wid" readonly name =NewRiskName id=NewRiskName >			
			</td>
			<td class = title>����󱣶�/����</td>
			<td class = input>
				<input class="readonly wid" readonly name =NewAmnt id=NewAmnt >
			</td>
		</tr>
	</table>
</Div>
 	
<Div id = "divIntv" style= "display: none">
	<table class = common>
		<tr class = common>
			<td class = title>
				�����ڼ�
			</td>
			<td class = input>
				<!--input class =codeno name =Intv ><input class=codename name=IntvName readonly=true-->
				<input class="codeno" name=InsuYear id=InsuYear style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" readonly=true verify="" ondblclick="initInsuYear(this,InsuYearName);" onkeyup="keyUpInsuYear(this,InsuYearName);"><input class="codename" name=InsuYearName id=InsuYearName readonly=true>
			</td>
			<td class = title></td>
			<td class = input>
				<input class="readonly wid" readonly name = >
			</td>
			<td class = title></td>
			<td class = input>
				<input class="readonly wid" readonly name = >
			</td>			
		</tr>
	</table>
</Div>
 	
<Div id = "divGetMode" style= "display: none">
	<table class = common>
		<tr class = common>
			<td class = title>
				��ȡ��ʽ
			</td>
			<td class = input>
				<!--input class =codeno name =GetMode ><input class=codename name=GetModeName readonly=true-->
				<input class="codeno" name=GetDutyKind id=GetDutyKind readonly=true style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="" ondblclick="initGetDutyCode(this,GetDutyKindName);" onkeyup="keyUpGetDutyCode(this,GetDutyKindName);"><input class="codename" name=GetDutyKindName id=GetDutyKindName readonly=true>
			</td>
			<td class = title></td>
			<td class = input>
				<input class="readonly wid" readonly name = >
			</td>
			<td class = title></td>
			<td class = input>
				<input class="readonly wid" readonly name = >
			</td>			
		</tr>
	</table>
</Div> 	
 	
  <table class = common>	

		 <Input class= cssButton type=Button value=" ��  �� " onclick="edorTypePUSave()">	     	 
       	 <Input class= cssButton type=Button value=" ��  �� " onclick="returnParent()">
       	 <Input class= cssButton TYPE=Button VALUE="���±��鿴" onclick="showNotePad()">
   </table>

	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" name="ContType">
	 <input type=hidden id="PolNo" name="PolNo">
	 <input type=hidden id="EdorNo" name="EdorNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">
	 <input type=hidden id="IninSuccessFlag" name="IninSuccessFlag">
	 <input type=hidden id="SpecialRiskFlag" name="SpecialRiskFlag">
	 <input type=hidden id="TempSaveField" name="TempSaveField">
	 <input type=hidden id="PageIsFormating" name="PageIsFormating">
	 <input type=hidden id="SpecialRiskInitFlag" name="SpecialRiskInitFlag">
	 <input type=hidden id="StrPolGrid" name="StrPolGrid">
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
