<%
//��������:��ȫ�����ӡ(��ʼֵ��
//������:��ģ����嵥���͵ı����ӡ  
//�������ڣ�2003-11-17
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
//���У�
// * <p>bq1����ȫ�ս�(������-����)</p>
// * <p>bq2: ��ȫ�½�(�ۺ���-����)</p>
// * <p>bq3: ��ȫ�ս�(������-������ϸ)</p>
// * <p>bq4: ��ȫ�½�(�ۺ���-������ϸ)</p>
// * <p>bq5: ��ȫ�ս�(������-����)</p>
// * <p>bq6: ��ȫ�½�(�ۺ���-����)</p>
//add 
// * <p>bq7: ��ȫ�ս��嵥��������--���պϼƣ�</p>
// * <p>bq8: ��ȫ�½��嵥���ۺ���--���պϼƣ�</p>
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
      
%>
<script>
    var operator = "<%=tG1.Operator%>";   //��¼����Ա
    var manageCom = "<%=tG1.ManageCom%>"; //��¼��½����
    var comcode = "<%=tG1.ComCode%>"; //��¼��½����
</script>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="./BQXReportList.js"></SCRIPT>
<%@include file="BQXReportListInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
<form method=post name=fm id=fm>
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>
            ��������
          </TD>  
          <TD class= input5>	
          	<select name='ReportName' id='ReportName_Select' onChange='ReportNameSelect()'>
          		<OPTION VALUE="GrpInfo">�ŵ���Ϣͳ��</OPTION>
							<OPTION VALUE="GrpSingleInfo">�ŵ��¸�����Ϣͳ��</OPTION>
							<OPTION VALUE="GrpSingleAcc">�ŵ��¸����˻��켣�嵥</OPTION>
							<OPTION VALUE="GrpEdorInfo">���屣ȫʱЧͳ��</OPTION>
							<OPTION VALUE="GrpLoanInfo">���屣ȫ�軹��ͳ��</OPTION>
							<OPTION VALUE="GrpEdorUser">���屣ȫȨ����Ϣͳ��</OPTION>
          	</select>
          </TD>
          <TD  class= title5></TD>
          <TD class= input5></TD>
       </TR>
    </Table>  

<Div  id= "divGrpInfo" style= "display: ''">   
<div class="maxbox">
	   <Table class= common>
     <TR class= common><TD  class= title colSpan=4><center><strong>��������:�ŵ���Ϣͳ��</Strong></center></TD></TR> 
		 <TR class= common>
		 	<TD class= title5> �����ͬ��</TD>
  	   <TD class= input5> <Input class="wid" class=common name=GIGrpContNo id=GIGrpContNo></TD>
		 	<TD  class= title5> ����״̬ </TD>
  	   <TD  class= input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GIAppFlag id=GIAppFlag CodeData="0|^1|��Ч^4|ʧЧ" ondblclick="showCodeListEx('AppFlag', [this,GIAppFlagName],[0,1]);" onMouseDown="showCodeListEx('AppFlag', [this,GIAppFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('AppFlag', [this,GIAppFlagName],[0,1]);" ><input class="codename" name=GIAppFlagName id=GIAppFlagName readonly=true></TD>
  	 </TR>
  	  <TR class= common>
  	  	<td class=title5>���ֱ���</td>
  	  	<td class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=GIRiskCode id=GIRiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,GIRiskCodeName],[0,1],null,null,null,true,'400');" onMouseDown="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,GIRiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,GIRiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=GIRiskCodeName id=GIRiskCodeName readonly=true></td>
  	  	<TD class=title5>�������</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=GIManageCom id=GIManageCom readonly ondblclick="return showCodeList('station',[this,GIManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GIManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GIManageComName],[0,1]);"><input class=codename name=GIManageComName id=GIManageComName readonly></TD>
  	  </TR>
  	 <TR class= common>
  	   <TD class= title5>��Ч��������(��)</TD>          
  	   <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GIStartCValidate elementtype=nacessary>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#GIStartCValidate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GIStartCValidate id="GIStartCValidate"><span class="icon"><a onClick="laydate({elem: '#GIStartCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
  	   <TD class =title5>��Ч��������(ֹ)</TD>
  	   <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GIEndCValidate elementtype=nacessary>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#GIEndCValidate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GIEndCValidate id="GIEndCValidate"><span class="icon"><a onClick="laydate({elem: '#GIEndCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
		 </TR>
		 
    </Table> 




	<Div  id= "divGrpPol" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
        <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
        <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();"> 			
	</DIV>
    <!-- <INPUT VALUE="Ԥ  ��" class = cssButton TYPE=button onclick="QueryGrpInfo();">
 <INPUT VALUE="��ӡ����" class = cssButton TYPE=button onclick="PrintGrpInfo();">  --><br>
 <a href="javascript:void(0);" class="button" onClick="QueryGrpInfo();">Ԥ    ��</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpInfo();">��ӡ����</a><br>
</DIV>
</Div>
<Div  id= "divGrpSingleInfo" style= "display: none"> 
 	<div class="maxbox"> 
 <Table class= common>
  <TR class= common><TD  class= title colSpan=4><center><strong>��������:�ŵ��¸�����Ϣͳ��</Strong></center></TD></TR>  
	<TR class= common>
		<TD class= title5> �����ͬ��</TD>
    <TD class= input5> <Input class="wid" class=common name=GSGrpContNo elementtype=nacessary></TD>
		<TD  class= title5> ����״̬ </TD>
    <TD  class= input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GSAppFlag id=GSAppFlag CodeData="0|^1|��Ч^4|ʧЧ" ondblclick="return showCodeListEx('AppFlag', [this,GSAppFlagName],[0,1]);" onMouseDown="return showCodeListEx('AppFlag', [this,GSAppFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('AppFlag', [this,GSAppFlagName],[0,1]);" ><input class="codename" name=GSAppFlagName id=GSAppFlagName readonly=true></TD>
  </TR>
  <TR class= common>
    <TD class= title5>��Ч��������(��)</TD>          
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSStartCValidate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GSStartCValidate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GSStartCValidate id="GSStartCValidate"><span class="icon"><a onClick="laydate({elem: '#GSStartCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class =title5>��Ч��������(ֹ)</TD>
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSEndCValidate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GSEndCValidate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GSEndCValidate id="GSEndCValidate"><span class="icon"><a onClick="laydate({elem: '#GSEndCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
 </Table>

 

	<Div  id= "divGrpSinglePol" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpSinglePolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage0.firstPage();"> 
        <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage0.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage0.nextPage();"> 
        <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage0.lastPage();"> 			
	</DIV>   
  <!--  <INPUT VALUE="Ԥ  ��" class = cssButton TYPE=button onclick="QueryGrpSingleInfo();">
 <INPUT VALUE="��ӡ����" class = cssButton TYPE=button onclick="PrintGrpSingleInfo();">-->
 <a href="javascript:void(0);" class="button" onClick="QueryGrpSingleInfo();">Ԥ    ��</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpSingleInfo();">��ӡ����</a><br>
   
</DIV>   
</Div>
<Div  id= "divGrpSingleAcc" style= "display: none">
	<div class="maxbox">
	<Table class= common>
  	<TR class= common><TD  class= title colSpan=4><center><strong>��������:�ŵ��¸����˻��켣�嵥</Strong></center></TD></TR>
  	<TR class= common>
			<TD class= title5> �����ͬ��</TD>
    	<TD class= input5> <Input class="wid" class=common name=GSAGrpContNo id=GSAGrpContNo elementtype=nacessary></TD>
    	<TD class= title5> ���˱�����</TD>
    	<TD class= input5> <Input class="wid" class=common name=GSAContNo id=GSAContNo elementtype=nacessary></TD>
    </TR>
    <TR class= common>
    	<TD class= title5>��������(��)</TD>          
    	<TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSAStartPayDate>-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#GSAStartPayDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GSAStartPayDate id="GSAStartPayDate"><span class="icon"><a onClick="laydate({elem: '#GSAStartPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
    	<TD class =title5>��������(ֹ)</TD>
    	<TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSAEndPayDate>-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#GSAEndPayDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GSAEndPayDate id="GSAEndPayDate"><span class="icon"><a onClick="laydate({elem: '#GSAEndPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
  	</TR> 
  </Table>

 

	<Div  id= "divGrpSingleTrace" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpSingleAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();"> 			
	</DIV> 
   <!-- <INPUT VALUE="Ԥ  ��" class = cssButton TYPE=button onclick="QueryGrpSingleAcc();">
 <INPUT VALUE="��ӡ����" class = cssButton TYPE=button onclick="PrintGrpSingleAcc();">-->
 <a href="javascript:void(0);" class="button" onClick="QueryGrpSingleAcc();">Ԥ    ��</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpSingleAcc();">��ӡ����</a><br>

</Div>
</Div>
<Div  id= "divGrpLoanInfo" style= "display: none">
	<div class="maxbox">
	<Table class= common>
  	<TR class= common><TD  class= title colSpan=4><center><strong>��������:���屣ȫ�軹��ͳ��</Strong></center></TD></TR>
  	<TR class= common>
  		<TD class=title5>�������</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GLManageCom id=GLManageCom ondblclick="return showCodeList('station',[this,GLManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GLManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GLManageComName],[0,1]);"><input class=codename name=GLManageComName id=GLManageComName readonly></TD>
  		<TD  class= title5>���״̬</TD><TD  class= input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GLPayOffFlag id=GLPayOffFlag CodeData="0|^0|δ����^1|����" ondblclick="return showCodeListEx('PayOffFlag', [this,GLPayOffFlagName],[0,1]);" onMouseDown="return showCodeListEx('PayOffFlag', [this,GLPayOffFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('PayOffFlag', [this,GLPayOffFlagName],[0,1]);" ><input class="codename" name=GLPayOffFlagName id=GLPayOffFlagName readonly=true></TD>
  	</TR>
  <TR class= common>
    <TD class= title5>�����������(��)</TD>          
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GLStartLoanDate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GLStartLoanDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GLStartLoanDate id="GLStartLoanDate"><span class="icon"><a onClick="laydate({elem: '#GLStartLoanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class =title5>�����������(ֹ)</TD>
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GLEndLoanDate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GLEndLoanDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GLEndLoanDate id="GLEndLoanDate"><span class="icon"><a onClick="laydate({elem: '#GLEndLoanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
  </Table>

 		
	
		<Div  id= "divGrpLoan" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpLoanGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage3.firstPage();"> 
        <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage3.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage3.nextPage();"> 
        <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage3.lastPage();"> 			
	</DIV>
   <!-- <INPUT VALUE="Ԥ  ��" class = cssButton TYPE=button onclick="QueryGrpLoanInfo();">
 		<INPUT VALUE="��ӡ����" class = cssButton TYPE=button onclick="PrintGrpLoanInfo();">-->
        <a href="javascript:void(0);" class="button" onClick="QueryGrpLoanInfo();">Ԥ    ��</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpLoanInfo();">��ӡ����</a><br>

</Div>
</Div>
<Div  id= "divGrpEdorInfo" style= "display: none">
	<div class="maxbox">
	<Table class= common>
  	<TR class= common><TD  class= title colSpan=4><center><strong>��������:���屣ȫʱЧͳ��</Strong></center></TD></TR>
  	<TR class= common>
  		<TD class= title5>�����ͬ��</TD><TD class= input5> <Input class="wid" class=common name=GEGrpContNo id=GEGrpContNo></TD>
  		<TD class=title5>�������</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GEManageCom id=GEManageCom ondblclick="return showCodeList('station',[this,GEManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GEManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GEManageComName],[0,1]);"><input class=codename name=GEManageComName id=GEManageComName readonly></TD>
  	</TR>
  	<TR class=common>
  		<TD class= title5>��ȫ��Ŀ</TD><TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="GEEdorType"  id="GEEdorType"  readonly=true ondblclick="getEdorInfo();return showCodeListEx('EdorType',[this,GEEdorTypeName],[0,1],'', '', '', true);" onMouseDown="getEdorInfo();return showCodeListEx('EdorType',[this,GEEdorTypeName],[0,1],'', '', '', true);" onkeyup="getEdorInfo();return showCodeListKeyEx('GEEdorType',[this,GEEdorTypeName],[0,1],'', '', '', true);"><input class=codename name="GEEdorTypeName" id="GEEdorTypeName" readonly=true></TD> 
  		<TD class= title5>����Ա</TD> <TD class= input5><Input class="wid" class=common name=GEdorOperator id=GEdorOperator></TD>
  	</TR>
  	<TR class= common>
    <TD class= title5>��ȫȷ����������(��)</TD>          
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorConfStartDate elementtype=nacessary>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorConfStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GEdorConfStartDate id="GEdorConfStartDate"><span class="icon"><a onClick="laydate({elem: '#GEdorConfStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class =title5>��ȫȷ����������(ֹ)</TD>
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorConfEndDate elementtype=nacessary>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorConfEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GEdorConfEndDate id="GEdorConfEndDate"><span class="icon"><a onClick="laydate({elem: '#GEdorConfEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
  </Table>

	 

	
	<Div  id= "divGrpEdor" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpEdorGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage5.firstPage();"> 
        <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage5.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage5.nextPage();"> 
        <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage5.lastPage();"> 			
	</DIV>
   <!-- <INPUT VALUE="Ԥ  ��" class = cssButton TYPE=button onclick="QueryGrpEdorInfo();">
	 <INPUT VALUE="��ӡ����" class = cssButton TYPE=button onclick="PrintGrpEdorInfo();">-->
      <a href="javascript:void(0);" class="button" onClick="QueryGrpEdorInfo();">Ԥ    ��</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpEdorInfo();">��ӡ����</a><br>
</Div>
</Div>
<Div  id= "divGrpEdorUser" style= "display: none">
	<div class="maxbox">
		<Table class= common>
  		<TR class= common><TD  class= title colSpan=4><center><strong>��������:���屣ȫȨ����Ϣͳ��</Strong></center></TD></TR>
			<TR class= common>
  			<TD class= title5>Ȩ�޼���</TD><TD class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" readonly name="GEdorPopedom" id="GEdorPopedom" verify="�ŵ�Ȩ�޼���|Code:GEdorPopedom" ondblclick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onMouseDown="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onkeyup="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></TD>
  			<TD class= title5>��������</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GEUManageCom id=GEUManageCom ondblclick="return showCodeList('station',[this,GEUManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GEUManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GEUManageComName],[0,1]);"><input class=codename name=GEUManageComName id=GEUManageComName readonly></TD>
  		</TR>
			<TR class= common>
    		<TD class= title5>�û�����ʱ������(��)</TD>          
    		<TD class= input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorUserStartDate>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorUserStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GEdorUserStartDate id="GEdorUserStartDate"><span class="icon"><a onClick="laydate({elem: '#GEdorUserStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
    		<TD class= title5>�û�����ʱ������(ֹ)</TD>
    		<TD class= input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorUserEndDate>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorUserEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=GEdorUserEndDate id="GEdorUserEndDate"><span class="icon"><a onClick="laydate({elem: '#GEdorUserEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
			</TR>
			<TR class= common>
  			<TD class= title5>����</TD><TD class= input5><input class="wid" type="text" class="common" name="GEdorUserCode" id="GEdorUserCode"></TD>
  			<TD class= title5>����</TD><TD class= input5><input class="wid" type="text" class="common" name="GEdorUserName" id="GEdorUserName"></TD>
  		</TR>
  		<TR class= common>
  			<TD class= title5>��Ա״̬</TD><TD class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GEUserState id=GEUserState CodeData="0|^0|��Ч^1|ʧЧ" ondblclick="return showCodeListEx('UserState', [this,GEUserStateName],[0,1]);" onMouseDown="return showCodeListEx('UserState', [this,GEUserStateName],[0,1]);" onkeyup="return showCodeListKeyEx('UserState', [this,GEUserStateName],[0,1]);" ><input class="codename" name=GEUserStateName id=GEUserStateName readonly=true></TD>
  			<TD class= title5></TD><TD class= input5></TD>
  		</TR>	
		</Table>
		

	 	
 
	
		<Div  id= "divGrpEdorUserInfo" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpEdorUserGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage6.firstPage();"> 
        <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage6.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage6.nextPage();"> 
        <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage6.lastPage();"> 			
	</DIV>
   <!-- <INPUT VALUE="Ԥ  ��" class = cssButton TYPE=button onclick="QueryGrpEdorUserInfo();">
	 	<INPUT VALUE="��ӡ����" class = cssButton TYPE=button onclick="PrintGrpEdorUserInfo();">-->
        <a href="javascript:void(0);" class="button" onClick="QueryGrpEdorUserInfo();">Ԥ    ��</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpEdorUserInfo();">��ӡ����</a>
</Div>
</Div>
 </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
<br><br><br><br>
</body>
</html>
