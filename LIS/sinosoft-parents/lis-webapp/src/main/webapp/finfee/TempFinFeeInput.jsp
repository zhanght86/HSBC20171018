<%
//�������ƣ�TempFinFeeInput.jsp
//�����ܣ������շѵ�����
//�������ڣ�2009-11-21
//������  ��
//���¼�¼��  ������  �ű�   ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String ReadType = "readonly";
	String PayRead = "readonly=true";
	try 
	{
		String Type = request.getParameter("type");
		if (Type!=null)
		{
		 if (Type.equals("1"))
		 ReadType = "coolDatePicker";
		 PayRead = "";
		}
		loggerDebug("TempFinFeeInput","ReadType:     "+ReadType);
	}
	catch( Exception e )
	{ 
		ReadType = null;
	}
%>

<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="TempFinFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="TempFinFeeInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<Form action="" method=post name=fm id="fm" target="fraSubmit">
<script>
	   var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
 <Table>
   	<tr>
        <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame1);">    	 
         <td class= titleImg>
          ������Ϣ
         </td>
   	 </td>
   	</tr>
 </Table>
 <Div  id= "Frame1" class="maxbox1" style= "display: ''">          
  <Table class= common border=0 >
    <TR  class= common>
     <TD  class= title>
        �շѻ���
     </TD>          
     <TD  class= input>
 				<Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true >
 		</TD>
     <TD  class= title>
        ����Ա����
     </TD>
     <TD  class= input>
 				<Input class="readonly wid" name=Operator id="Operator" readonly=true>
 		</TD>
 		<TD class= title>
       ��������
     </TD>
     <TD class= input >
       <Input class= "coolDatePicker" name= PayDate verify="��������|DATE" id="PayDate" onClick="laydate({elem:'#PayDate'});"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
     </TD> 
    </TR>
  </TABLE>
 </Div>
 <!-- <input type =button class=cssButton value="��&nbsp;&nbsp;ѯ" onclick="queryClick();"> -->
 <Table>
   <tr>
      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFeeInput);"></td>
    	<td class= titleImg>¼������շ���Ϣ</td>
   </tr>
 </Table>
 <Div id= "divFinFeeInput" class="maxbox" style= "display: ''">
 		<Div id= "divTempFeeClassInput" style="display:''">
		<table  class= common border=0>
			<tr class= common>
				<TD  class= title>���ѷ�ʽ</TD>
 	     	<TD  class= input width="25%"><Input class="codeno select" name=PayMode id="PayMode" style="background: url(../common/images/select--bg_03.png) no-repeat center;" onMouseDown="return showCodeList('paymodequery',[this,PayModeName],[0,1]);" onDblClick="return showCodeList('paymodequery',[this,PayModeName],[0,1]);" onKeyUp="return showCodeListKey('paymodequery',[this,PayModeName],[0,1]);" ><input class=codename   name=PayModeName readonly=true elementtype=nacessary></TD>
 	     	<TD  class= title>����ѡ��</TD>
 	     	<TD  class= input><Input class=codeno name=Currency id="Currency" style="background: url(../common/images/select--bg_03.png) no-repeat center;" onMouseDown="return showCodeList('currency',[this,CurrencyName],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName],[0,1]);" ><input class=codename   name=CurrencyName readonly=true elementtype=nacessary></TD>
 	     	<TD  class= title><div id="divPayName" style="display:'none'">����������</div></TD>
 	     	<TD  class= input><div id="divPayNameInput" style="display:'none'"><input class="wid" name=PayName ></div></TD>
			</tr>
 	 	</Table>
 		</Div>
 		<!------------�ֽ�------------>
 		<Div id=divPayMode1  style="display:'none'" > <!--To need modify-->
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" name=PayFee1 id="PayFee1" elementtype=nacessary ></td>
 					<td class=title></td>
 					<td class=input></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</Div>
 		<!------------�ֽ�֧Ʊ------------>
 		<Div id=divPayMode2 style="display:'none'" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" name=PayFee2 id="PayFee2" elementtype=nacessary></td>
 					<td class=title>Ʊ�ݺ���</td>
 					<td class=input><Input class="wid" name=ChequeNo2 id="ChequeNo2" onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>Ʊ������</td>
 					<!--td class=input><Input class=coolDatePicker name=ChequeDate2 elementtype=nacessary></td-->
 					 <td class=input><Input class= "coolDatePicker" verify="Ʊ������|date"  dateFormat="short" name=ChequeDate2 elementtype=nacessary  onblur=" CheckDate(fm.ChequeDate2); " id="ChequeDate2" onClick="laydate({elem:'#ChequeDate2'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
 				</tr>
 				<tr class= common>
 					<td class=title>��������</td>
 					<!--td class=input><Input class=codeno name=BankCode2 ondblclick="return showCodeList('bank',[this,BankCode2Name],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCode2Name],[0,1]);"><input class=codename name=BankCode2Name readonly=true elementtype=nacessary></td-->
 					<td class=input><Input class="wid" name=BankCode2Name id="BankCode2Name" elementtype=nacessary></td>	
 					<td class=title>��  ��</td>
 					<td class=input><Input class="wid" name=BankAccNo2 id="BankAccNo2" onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>��  ��</td>
 					<td class=input><Input class="wid" name=AccName2 id="AccName2" elementtype=nacessary></td>	
 				</tr>
 				<tr class= common>
 					<td class=title>�տ�����</td>
 					<td class=input><Input class=codeno name=InBankCode2  id="InBankCode2" style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('comtobank',[this,InBankCode2Name],[0,1]);"  ondblclick="return showCodeList('comtobank',[this,InBankCode2Name],[0,1]);" onKeyUp="return showCodeListKey('comtobank',[this,InBankCode2Name],[0,1]);"><input class=codename   name=InBankCode2Name readonly=true elementtype=nacessary></td>
 					<td class=title>�տ������˺�</td>
 					<td class=input><Input class=code name=InBankAccNo2 id="InBankAccNo2" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onMouseDown="return showCodeList('AccNo',[this],null,null,[fm.InBankCode2.value],['bankcode']);" onDblClick="return showCodeList('AccNo',[this],null,null,[fm.InBankCode2.value],['bankcode']);" onKeyUp="return showCodeListKey('AccNo',[this],null,null,[fm.InBankCode2.value],['bankcode']);" readonly=true elementtype=nacessary></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</div>
 		<!------------ת��֧Ʊ------------>
 		<div id=divPayMode3 style="display:'none'" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" name=PayFee3 id="PayFee3" elementtype=nacessary></td>
 					<td class=title>Ʊ�ݺ���</td>
 					<td class=input><Input class="wid" id="ChequeNo3" name=ChequeNo3 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>Ʊ������</td>
 					<!--td class=input><Input class=coolDatePicker name=ChequeDate3 elementtype=nacessary></td-->
 					 <td class=input><Input class= "coolDatePicker" verify="Ʊ������|date"  dateFormat="short" name=ChequeDate3 elementtype=nacessary  onblur=" CheckDate(fm.ChequeDate3); " id="ChequeDate3" onClick="laydate({elem:'#ChequeDate3'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
 				</tr>
 				<tr class= common>
 					<td class=title>��������</td>
 					<!--td class=input><Input class=codeno name=BankCode3 ondblclick="return showCodeList('bank',[this,BankCode3Name],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCode3Name],[0,1]);"><input class=codename name=BankCode3Name readonly=true elementtype=nacessary></td-->
 					<td class=input><Input class="wid" id="BankCode3Name" name=BankCode3Name elementtype=nacessary></td>	
 					<td class=title>��  ��</td>
 					<td class=input><Input class="wid" id="BankAccNo3" name=BankAccNo3 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>��  ��</td>
 					<td class=input><Input class="wid" id="AccName3" name=AccName3 elementtype=nacessary></td>	
 				</tr>
 				<tr class= common>
 					<td class=title>�տ�����</td>
 					<td class=input><Input class=codeno name=InBankCode3 style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('comtobank',[this,InBankCode3Name],[0,1]);" onDblClick="return showCodeList('comtobank',[this,InBankCode3Name],[0,1]);" onKeyUp="return showCodeListKey('comtobank',[this,InBankCode3Name],[0,1]);"><input class=codename   name=InBankCode3Name readonly=true elementtype=nacessary></td>
 					<td class=title>�տ������˺�</td>
 					<td class=input><Input class=code name=InBankAccNo3 id="InBankAccNo3" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onMouseDown="return showCodeList('AccNo',[this],null,null,[fm.InBankCode3.value],['bankcode']);" onDblClick="return showCodeList('AccNo',[this],null,null,[fm.InBankCode3.value],['bankcode']);" onKeyUp="return showCodeListKey('AccNo',[this],null,null,[fm.InBankCode3.value],['bankcode']);" readonly=true elementtype=nacessary></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</div>
 		
 		<!------------���й���------------>
 		<div id=divPayMode4 style="display:'none'" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" id="PayFee4" name=PayFee4 elementtype=nacessary></td>
 					<td class=title>Ʊ�ݺ���</td>
 					<td class=input><Input class="wid" id="ChequeNo4" name=ChequeNo4 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>Ʊ������</td>
 					<!--td class=input><Input class=coolDatePicker name=ChequeDate4 elementtype=nacessary></td-->
					<td class=input><Input class= "coolDatePicker" verify="Ʊ������|date"  dateFormat="short" id="ChequeDate4" name=ChequeDate4 elementtype=nacessary  onblur=" CheckDate(fm.ChequeDate4); " id="ChequeDate4" onClick="laydate({elem:'#ChequeDate4'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate4'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
 				</tr>
 				<tr class= common>
 					<td class=title>�տ�����</td>
 					<td class=input><Input class=codeno name=InBankCode4 id="InBankCode4" style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('comtobank',[this,InBankCode4Name],[0,1]);" onDblClick="return showCodeList('comtobank',[this,InBankCode4Name],[0,1]);" onKeyUp="return showCodeListKey('comtobank',[this,InBankCode4Name],[0,1]);"><input class=codename   name=InBankCode4Name readonly=true elementtype=nacessary></td>
 					<td class=title>�տ������˺�</td>
 					<td class=input><Input class=code name=InBankAccNo4 style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" id="InBankAccNo4" onMouseDown="return showCodeList('AccNo',[this],null,null,[fm.InBankCode4.value],['bankcode']);"
                     onDblClick="return showCodeList('AccNo',[this],null,null,[fm.InBankCode4.value],['bankcode']);" onKeyUp="return showCodeListKey('AccNo',[this],null,null,[fm.InBankCode4.value],['bankcode']);" readonly=true elementtype=nacessary></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</div>
 		<!------------�ڲ�ת��------------>
 		
 		<div id=divPayMode5 style="display:'none'" >
 			<Table  class= common>
 				<tr class= common>
 						<td class=title>ʵ��֪ͨ���</td>
 						<td class=input><Input class="wid" id="ActuGetNo5" name=ActuGetNo5></td>
 						<td class=title>��ȫ�����/�ⰸ��/Ͷ����ӡˢ��</td>
 						<td class=input><Input class="wid" id="OtherNo5" name=OtherNo5 ></td>
 						<td class=title>
 							<!--<input class=cssButton type="button" value="��  ѯ" style="width:60" onclick="queryLJAGet()">-->
 						</td>
 						<td class=input></td>
 				</tr>
 				<tr class= common>
 						<td class=title>�������</td>
 						<td class=input><Input class="readonly wid"  id="PayFee5" name=PayFee5 readonly=true></td>
 						<td class=title>��ȡ��</td>
 						<td class=input><Input class="readonly wid" id="PayFee5" name=PayFee5 readonly=true></td>
 						<td class=title><Input class="wid" type=hidden name=EnterAccDate></td>
 						<td class=input><Input class="wid" type=hidden name=DrawerID></td>
 				</tr>
 				
 		    <Div  id= "divFinFee1" style= "display: ''" align=center>
 		    	<Table  class= common>
 		        <TR  class= common>
 		         <TD text-align: left colSpan=1>
 		           <span id="spanQueryLJAGetGrid" ></span> 
 		        </TD>
 		       </TR>
 		      </Table>
 		      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
      		</Div>
 		    </Div>
 		    
 			</table>
 		</Div>
 		<!--------------------Pos��------------------------->
 		<Div id=divPayMode6  style="display:'none';margin-top:20px;" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" id="PayFee6" name=PayFee6 elementtype=nacessary></td>
 					<td class=title></td>
 					<td class=input></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 				<tr class= common>
 					<td class=title>Ʊ�ݺ���</td>
 					<td class=input><Input class="wid" id="ChequeNo6" name=ChequeNo6 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					
 					<td class=title>
 							�տ�����
 					</td>
 					<td class=input>
 						<Input class=codeno name=InBankCode6 id="InBankCode6" style="background: url(../common/images/select--bg_03.png) no-repeat center;  "
 onDblClick="return showCodeList('comtobank',[this,InBankCode6Name],[0,1]);" onKeyUp="return showCodeListKey('comtobank',[this,InBankCode6Name],[0,1]);"><input class=codename   id="InBankCode6Name" name=InBankCode6Name readonly=true elementtype=nacessary>
 					</td>
 					<td class=title>
 							�տ������˺�
 					</td>
 					<td class=input>
 		   		  <Input class=code name=InBankAccNo6 id="InBankAccNo6" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onMouseDown="return showCodeList('AccNo',[this],null,null,[fm.InBankCode6.value],['bankcode']);" onDblClick="return showCodeList('AccNo',[this],null,null,[fm.InBankCode6.value],['bankcode']);" onKeyUp="return showCodeListKey('AccNo',[this],null,null,[fm.InBankCode6.value],['bankcode']);" readonly=true elementtype=nacessary>
 					</td>	
 				</tr> 
 			</Table>
 		</div>
 		<!--------------------------------------------->
 		<div id=divPayMode7 style="display:'none';" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" id="PayFee7" name=PayFee7 elementtype=nacessary></td>
 					<td class=title></td>
 					<td class=input></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 					<tr class= common>
 						<td class=title>��������</td>
 						<td class=input>
							<Input class=codeno  name=BankCode7 id="BankCode7" style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('bank',[this,BankCode7Name],[0,1]);" onDblClick="return showCodeList('bank',[this,BankCode7Name],[0,1]);" onKeyUp="return showCodeListKey('bank',[this,BankCode7Name],[0,1]);" readonly=true><input class=codename   id="BankCode7Name" name=BankCode7Name readonly=true elementtype=nacessary>
 						</td>
 						<td class=title>�����˺�</td>
 						<td class=input>
 							<Input class="wid" id="BankAccNo7" name=BankAccNo7 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary>
 						</td>
 						<td class=title>��  ��</td>
 						<td class=input>
 							<Input class="wid" id="AccName7" name=AccName7 elementtype=nacessary>
 						</td>
 					</tr>
 					<tr class= common>
 							<td class=title>֤������</td>
 							<td class=input>
									<Input class=codeno name=IDType7 id="IDType7" style="background: url(../common/images/select--bg_03.png) no-repeat center;  "
 onMouseDown="return showCodeList('idtype',[this,IDType7Name],[0,1]);"  onDblClick="return showCodeList('idtype',[this,IDType7Name],[0,1]);" onKeyUp="return showCodeListKey('idtype',[this,IDType7Name],[0,1]);"><input class=codename name=IDType7Name   readonly=true>
 							</td>	
 					    <td class=title>֤������</td>
 							<td class=input>
 		   				  <Input class="wid" id="IDNo7" name=IDNo7 onBlur="confirmSecondInput(this,'onblur');">
 							</td>
 					</tr>
 			</table>
 		</div>
 		
 		<div id=divPayMode8 style="display:'none'" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input>
 						<Input class="wid" id="PayFee8" name=PayFee8 >
 					</td>
 					<td class=title></td>
 					<td class=input></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</div>
 		<div id=divPayMode9 style="display:'none'" >
 		 	<Table  class= common>
 				 				<tr class= common>
 					<td class=title>���ѽ��</td>
 					<td class=input><Input class="wid" id="PayFee9" name=PayFee9 elementtype=nacessary></td>
 					<td class=title>Ʊ�ݺ���</td>
 					<td class=input><Input class="wid" id="ChequeNo9" name=ChequeNo9 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>Ʊ������</td>
 					<!--td class=input><Input class=coolDatePicker name=ChequeDate9 elementtype=nacessary></td-->
 					<td class=input><Input class= "coolDatePicker" verify="Ʊ������|date"  dateFormat="short" name=ChequeDate9 elementtype=nacessary  onblur=" CheckDate(fm.ChequeDate9); " id="ChequeDate9" onClick="laydate({elem:'#ChequeDate9'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate9'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
 				</tr>
 				<tr class= common>
 					<td class=title>��������</td>
 					<!--td class=input><Input class=codeno name=BankCode9 ondblclick="return showCodeList('bank',[this,BankCode9Name],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCode9Name],[0,1]);"><input class=codename name=BankCode9Name readonly=true elementtype=nacessary></td-->
 					<td class=input><Input class="wid" id="BankCode9Name" name=BankCode9Name elementtype=nacessary></td>	
 					<td class=title>��  ��</td>
 					<td class=input><Input class="wid" id="BankAccNo9" name=BankAccNo9 onBlur="confirmSecondInput(this,'onblur');" elementtype=nacessary></td>
 					<td class=title>��  ��</td>
 					<td class=input><Input class="wid" id="AccName9" name=AccName9 elementtype=nacessary></td>	
 				</tr>
 				<tr class= common>
 					<td class=title>�տ�����</td>
 					<td class=input><Input class=codeno name=InBankCode9 id="InBankCode9" style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('comtobank',[this,InBankCode9Name],[0,1]);" onDblClick="return showCodeList('comtobank',[this,InBankCode9Name],[0,1]);" onKeyUp="return showCodeListKey('comtobank',[this,InBankCode9Name],[0,1]);"><input class=codename   name=InBankCode9Name readonly=true elementtype=nacessary></td>
 					<td class=title>�տ������˺�</td>
 					<td class=input><Input class=code name=InBankAccNo9 id="InBankAccNo9"  onMouseDown="return showCodeList('AccNo',[this],null,null,[fm.InBankCode9.value],['bankcode']);" onDblClick="return showCodeList('AccNo',[this],null,null,[fm.InBankCode9.value],['bankcode']);" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onKeyUp="return showCodeListKey('AccNo',[this],null,null,[fm.InBankCode9.value],['bankcode']);" readonly=true elementtype=nacessary></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</div>
 		
 		<div id=divPayMode0 style="display:'none'" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title>
 							���ѽ��
 					</td>
 					<td class=input>
 						<Input class="wid" id="PayFee0" name=PayFee0>
 					</td>
 					<td class=title></td>
 					<td class=input></td>
 					<td class=title></td>
 					<td class=input></td>
 				</tr>
 			</table>
 		</div>
        <a href="javascript:void(0)" class=button onclick="addMul();">ȷ    ��</a>
        <br></br>
 		<!--<input type =button class=cssButton value="ȷ  ��" 	onclick="addMul();"> -->
 		<!--<input type =button class=cssButton	value="��  ��" 	onclick="ModMul();">-->
 		 <!--¼����ݽ��ѱ��� -->
 		   <Table  class= common>
 		     	<tr>
 		   	 		<td text-align: left colSpan=1>
			 					<span id="spanFinFeeGrid" >
			 					</span> 
							</td>
 		      </tr>
 		   </Table>
 </Div>
 
 <table>
   <tr>
      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame2);"></td>
    	<td class= titleImg>¼��ҵ�������Ϣ</td>
   </tr>
 </table>

 <Div  id= "Frame2" class="maxbox" style= "display: ''">
    <table class= common> 
    	<TR  class= common>
    		<TD  class= title>��������</TD>
   		  <TD  class= input><Input class=codeno name=TempFeeType id="TempFeeType" style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);"
 onDblClick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onKeyUp="return showCodeListKey('TempFeeType',[this,TempFeeTypeName],[0,1]);" ><input class=codename   name=TempFeeTypeName readonly=true></TD>
    	  <TD  class= title>���վݺ���</TD>
 	     	<TD  class= input><Input class="wid" id="TempFeeNo1" name=TempFeeNo1 onBlur="confirmSecondInput(this,'onblur');" ></TD>
 	     	<TD  class= title>ҵ�����</TD>
	  	  <TD  class= input><Input class=codeno name=OpeCurrency id="OpeCurrency" style="background: url(../common/images/select--bg_03.png) no-repeat center;  " onMouseDown="return showCodeList('currency',[this,OpeCurrencyName],[0,1]);" onDblClick="return showCodeList('currency',[this,OpeCurrencyName],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,OpeCurrencyName],[0,1]);"><input class=codename   name=OpeCurrencyName readonly=true elementtype=nacessary ></TD>
    	</TR>
    	<TR  class= common>
    		<TD  class= title>�������</TD>
    	  <TD  class= input><Input class="readonly wid"  id="PolicyCom" name=PolicyCom readonly=true></TD>
 	     	<TD  class= title></TD>
 	     	<TD  class= input></TD>
 	     	<TD  class= title></TD>
 	     	<TD  class= input></TD>
 	    </TR>
    </table>
    
    <Div id="AgentCode" style="display:">
   	<table class= common> 
   		<TR  class= common>
   			<TD  class= title>�����˺���</TD>
   			<td class="input" width="25%">
   				<Input class=code name=AgentCode id="AgentCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;"   onBlur="GetManageCom();" onClick="queryAgent();" onDblClick="queryAgent();" elementtype=nacessary ><!--ondblclick="queryAgent();" -->
   			</TD>
   			<TD class=title>����������</TD>
   			<TD  class= input><Input class="readonly wid"   tabindex=-1 id="AgentName" name=AgentName readonly=true ></TD>
   			<TD  class= title>���������</TD>
	 			<TD  class= input><Input class="readonly wid"  readonly tabindex=-1 id="AgentGroup" name=AgentGroup ></TD>
   		</TR>
   	</table>
  	</Div>
   	<Div id="AgentCode1" style="display:none">
   		<table class= common> 
   		  <TR  class= common>
   				<TD  class= title>�����˺���</TD>
   				<TD class="input" width="25%">
   					<Input class=readonly id="AgentCode1" name=AgentCode1 >
   				</TD>
   				<TD class=title>����������</TD>
   				<TD  class= input><Input class="readonly"  tabindex=-1 id="AgentName1" name=AgentName1 readonly=true></TD>
   				<TD  class= title>���������</TD>
	 		    <TD  class= input><Input class="readonly" readonly tabindex=-1 id="AgentGroup1" name=AgentGroup1></TD>
   			</TR>
   		</table>
  	</Div>
   <!-- �µ����� -->  
   <Div id="TempFeeType1" style="display:'none'"><!--To need modify-->
   	<table class= common>
   		<TR  class= common>
   			<TD  class= title>���ý��</TD>
        <TD  class= input>
	     		<Input class="wid" id="OpeFee1" name=OpeFee1 elementtype=nacessary >
        </TD>
	      <TD  class= title>
	        Ͷ������
	      </TD>          
	      <TD  class= input>
	        <Input class="wid" id="InputNo1" name=InputNo1 onBlur="confirmSecondInput(this,'onblur');getAppntName();" elementtype=nacessary >
	        <!--2009102403 == 200001 -->
	        <!--2009102101 <> 200001 -->
	      </TD>
	      <TD  class= title>
	        Ͷ����
	      </TD>
	      <TD  class= input>
	        <Input class="wid" id="InputNob" name=InputNob>
	      </TD>
	     </TR>
	   </table> 
   </Div> 
   
   <!-- ���ڴ��ս��� --> 
   <Div id="TempFeeType2" style="display:none">
	   <table class= common> 
	     <TR  class= common>
	     	<TD  class= title>���ý��</TD>
        <TD  class= input>
	     		<Input class="wid" id="OpeFee2" name=OpeFee2>
        </TD>
	      <TD  class= title >
	        ��������
	      </TD>        
	       <td class="input" width="25%">
	        <Input class="wid" id="InputNo3" name=InputNo3 onBlur="getAgentCode();" >
	      </TD>  
	      <TD class=title>
	      	<!--<Input class=cssButton type=button value="��  ѯ" onclick="queryLJSPay();">-->
	      </TD>
	      <TD  class= input>
	      	<Input type=hidden id="GetNoticeNo" name=GetNoticeNo>
	      </TD> 
	     </TR> 
	     <!--
	     	<TR>
	     	 <td class=title>
	     	    �ͻ�����
	     	 </td>
	     	 <td class=input>
	     	   <Input class="wid" name=AppntNo readonly=true>
	     	 </td>
	     	 <td class=title>
	     	    ��������
	     	 </td>          
	     	 <td class=input>
	     	 	<Input class="wid" name=PayCount readonly=true>
	     	 </td> 
	     		<TD class=title></TD>
	     		<TD  class= input></TD>
	     	</TR>
	   		-->
	   </table>  
   </Div>
   
   <!-- ����Ԥ�ս��� --> 
   <Div id="TempFeeType3" style="display:none">
	   <table class= common> 
	     <TR  class= common>
	     	<TD  class= title>���ý��</TD>
        <TD  class= input>
	     		<Input class="wid" id="OpeFee3" name=OpeFee3>
        </TD>
	      <TD  class= title>
	        ��������
	      </TD>          
	      <TD  class= input>
	        <Input class="wid" id="InputNo5" name=InputNo5 onBlur="getPolicyCom();" >
	      </TD>        
	      <td class=title></td>
	      <td class=input></td>
	     </TR>
	   </table>  
   </Div>
	
   <!-- ��ȫ���� --> 
   <Div id="TempFeeType4" style="display:none">
   <table class= common> 
     <TR  class= common>
     	<TD  class= title>���ý��</TD>
      <TD  class= input>
	    	<Input class="wid" id="OpeFee4" name=OpeFee4>
      </TD>
      <TD  class= title>
        ��ȫ�����
      </TD>          
      <TD  class= input>
        <Input class="wid" id="InputNo7" name=InputNo7 onBlur="getEdorCode();" >
      </TD>        
      <TD  class= title>
        ����֪ͨ����
      </TD>
      <TD  class= input>
        <Input class="wid" id="InputNo8" name=InputNo8 onBlur="getEdorCode();">
        <!--<Input class=cssButton type=button value="��  ѯ" onclick="queryLJSPayEdor();">-->
      </TD>
     </TR>
   </table>  
   </Div>
   
   <!-- ������� -->  
   <Div id="TempFeeType5" style="display:none">
   	<table class= common> 
   	  <TR  class= common>
   	  	<TD  class= title>���ý��</TD>
        <TD  class= input>
	     		<Input class="wid" id="OpeFee5" name=OpeFee5> 
        </TD>
   	    <TD  class= title>�����վݺ�</TD> 
   	    <TD  class= input><Input class="wid" id="InputNo11" name=InputNo11 onBlur="getLJSPayPolicyCom();" ></TD> 
   	    <TD  class= title>�ⰸ��</TD> 
   	    <TD  class= input><Input class="wid" id="InputNo12" name=InputNo12 onBlur="getLJSPayPolicyCom();" ></TD>
   	  </TR>
   	</table>  
   </Div>
   
   <!-- �����ڽ��� -->
   <Div id="TempFeeType6" style="display:none">
   <table class= common> 
     <TR  class= common>
     	<TD  class= title>���ý��</TD>
      <TD  class= input>
	    	<Input class="wid" id="OpeFee6" name=OpeFee6> 
      </TD>
      <TD class= title>��������</TD>
      <TD class= input><Input class="wid" id="InputNo22" name=InputNo22 onBlur="getUrgePolicyCom();" ></TD>
      <td class=title></td>
      <td class=input></td>
     </TR>
   </table>  
   </Div>   
   
   <!-- �������д��۽��� -->  
   <Div id="TempFeeType8" style="display:none">
   	<table class= common> 
   	  <TR  class= common>
   	      <TD  class= title>
   	        �ͻ�����
   	      </TD>          
   	      <TD  class= input>
   	        <Input class="wid" id="InputNo9" name=InputNo9 >
   	      </TD>        
   	      <td class=title>
   	       <!-- ��ע	-->
   	      </td>
   	      <td class=input>
   	        <!--Input class="wid" id="ReMark9" name=ReMark9 -->	
   	      </td> 
   	  </TR>
   	</table>  
   </Div>
   
   <!-- ��ȫ������ --> 
   <Div id="TempFeeType7" style="display:none">
   	 <table class= common> 
   	   <TR  class= common>
   	     <TD class= title>��ȫ�����</TD>
   	     <TD class= input><Input class="wid" id="InputNo13" name=InputNo13 ></TD>
   	     <TD class= title>����֪ͨ����</TD>
   	     <TD class= input><Input class="wid" id="InputNo14" name=InputNo14></TD> 
   	     <TD class=title></TD>
	       <TD class= input></TD>
   	   </TR>
   	 </table>  
   </Div>    
   
   <!--��������ɷ�-->
   <Div id="TempFeeType9" style="display:none">
   <table class= common> 
     <TR  class= common>
       <TD  class= title>�����</TD>          
       <TD  class= input><Input class="wid" id="InputNo99" name=InputNo99 ></TD>        
       <TD  class= title>��֤����</TD>
       <TD  class= input><Input class= codeno  name=CertifyFlag9 id="CertifyFlag9" style="background: url(../common/images/select--bg_03.png) no-repeat center;  "
  CodeData="0|^1|�����վ�^2|���㵥" onDblClick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('CertifyFlag', [this��ContTypeName],[0,1])"><input class=codename   name=ContTypeName readonly=true></TD>
       <TD  class= title>��֤����</TD>
       <TD  class= input><Input class="wid" id="InputNo19" name=InputNo19 onBlur="confirmSecondInput(this,'onblur');"></TD>
     </TR>
   </table>  
   </Div> 
    
   <table>    
    <TR>          
     <TD>
      <a href="javascript:void(0)" name="addButton" class=button onclick="confirm1();">��    ��</a>
      <!--<Input name="addButton" type=button class= cssButton value="��  ��" onClick="confirm1();">-->
      &nbsp;
      <a href="javascript:void(0)" name="modify" class=button onclick="modifyData();">��    ��</a>
      <!--<Input name="modify" type=button class= cssButton value="��  ��" onClick="modifyData();">-->
     </TD> 
     <!--�����ݽ��Ѻ��� type=hidden--> 
     <TD class= common>
      <input  class= common id="TempFeeNo"  name="TempFeeNo" type=hidden readonly >
     </TD>                                                
    </TR>
   </table> 
  <br>
  <Div  id= "divTempFeeInput" style= "display: ''">
  <!--¼����ݽ��ѱ��� -->
  
    <Table  class= common>
      	<tr>
  				  	 <td text-align: left colSpan=1>
					 <span id="spanTempToGrid" >
					 </span> 
					</td>
       </tr>
    </Table>
 	</Div>
 	
 </Div>
 <table class= common> 
   <TR  class= common>
     <TD class= title>ҵ����ϼ�</TD>
     <TD class= input><Input class="wid" id="OperateSum" name=OperateSum  readonly=true ></TD>
     <td class=title><div id=divSubName style= "display: 'none'">���</div></td>
     <td class=input><div id=divSubName style= "display: 'none'"><Input class="wid" id="OperateSub" name=OperateSub  readonly=true ></div></td>
     <TD class=title></TD>
	   <TD class= input></TD>
   </TR>
 </table>  
 </Div>
 <br>
 <!--����2010-2-26 -->
 <table class=common>
    <TR class=common>    
     <td class=common align=left>
       <a href="javascript:void(0)"  class=button onclick="addRecord();">���һ��</a>
       <a href="javascript:void(0)"  class=button onclick="clearFormData();">����ȫ������</a>
      <!--<input type =button class=cssButton value="���һ��" onClick="addRecord();">  
      <input type =button class=cssButton value="����ȫ������" onClick="clearFormData();">-->      
     </TD>
    </TR> 
 </table> 
 <!--����2010-2-26 -->
 <!--table>
 	<TR class=input>     
 		<td class=common>
 	  	<input type =button class=cssButton value="ȫ���ύ" onclick="submitForm();">
 	  	<input type =button class=cssButton value="����ȫ������" onclick="clearFormData();">
 	 	  <input type =button class=cssButton value="��ӡƱ��" onclick="printInvoice();">
 	 	</TD>
 	</TR>
 </table-->
	<br></br>
</form>
 <input type=hidden id="OperateType" name='OperateType'>
 <!--����2010-2-26 -->
 <Form action="./TempFinFeeSave.jsp" method=post id="fmSave" name=fmSave target="fraSubmit">
 	  <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFeeSave);">    	 
          <td class= titleImg>
           �洢����
          </td>    	 
    	 </td>
    	</tr>
    </Table>  
    <!--�ݽ��ѱ��� -->  
    <Table  class= common>
       <tr>
    	 		<td text-align: left colSpan=1>
	 						<span id="spanTTempToGrid" >
	 						</span> 
			 		</td>
       </tr>
    </Table>       
 	  <!--�ݽ��ѷ������ -->  
    <Table  class= common>
       <tr>
    	 		<td text-align: left colSpan=1>
	 						<span id="spanTTempClassToGrid" >
	 						</span> 
					</td>
       </tr>
    </Table>
    <table align=left>
		    <TR class=input>     
			     <td class=common>
                      <a href="javascript:void(0)" class=button onclick="submitForm1();">ȫ���ύ</a>
                      <a href="javascript:void(0)" class=button onclick="clearFormData();">����ȫ������</a>
				      <!--<input type =button class=cssButton value="ȫ���ύ" onClick="submitForm1();">
				      <input type =button class=cssButton value="����ȫ������" onClick="clearFormData();">-->
			     </TD>
		    </TR>
    </table>    
 </Form>    
 <!--����2010-2-26 -->
 <form action="" id="fmImport" name=fmImport target="fraSubmit" method=post ENCTYPE="multipart/form-data"> 	
 		<br></br>
  	<Table class= common align=left> 		
  	 		<TR class= common align=left>
  	 			<TD class= title align=left style="width:12%">
			      ѡ������ļ���
			    </TD>     
			    <TD>
			      <Input type="file" id="fileName" name=fileName class="wid1">
			      <!--<INPUT VALUE="��  ��" class=cssButton TYPE=button onClick="importData();">-->
                  <a href="javascript:void(0)"  class=button onclick="importData();">��    ��</a>
                  <a href="javascript:void(0)"  class=button onclick="return alink();">����ģ������</a>
			      <!--<input class="cssButton" type="button" value="����ģ������" onclick= "return alink();" >-->
			    </TD>		   
			  </TR>
		</Table> 
        
 </Form>
<!--=========================================================================-->

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<!--����-��Ų�ѯ���� -->
<Form action="./TempFeeTypeQuery.jsp" method=post id="fmTypeQuery" name=fmTypeQuery target="fraSubmit">
  <input type=hidden name=QueryNo>
  <input type=hidden name=QueryType>
</Form>
<br><br><br><br><br>
</body>
</html>
