    <%
//�������ƣ�TempFeeInput.jsp
//�����ܣ������շѵ�����
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="ContInsuInput.js"></SCRIPT>
<%@include file="ContInsuInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
</head>
<body  onload="initForm();" >
<form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">

<DIV id=DivRiskHidden STYLE="display:''">
<TABLE class=common>

  <TR CLASS=common>
    <TD CLASS=title>
      *������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly" readonly NAME=ContNo>
    </TD>
    <TD CLASS=title>
      ������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly" readonly NAME=AgentCom>
    </TD>
    <TD CLASS=title>
      Ͷ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="readonly" readonly NAME=Handler>
    </TD>
  </TR>

</TABLE>
</DIV>
<TABLE class=common>

  <TR class=common>
  <td class=title>
  	*�ͻ��� 
    </TD>
    <TD CLASS=common >
      <Input NAME=CustomerNo  CLASS=common MAXLENGTH=20 >
    
    </TD>
     
    </tr>
    
    </table>
 <table>
 <tr>
 <td>
     <input type =button class=cssButton value="�� ѯ" onclick=""> 
     </td>
     
      </tr>
      </table>
      <table>
    	<tr>
        	<td >
		   <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
    		</td>
    		<td class= titleImg>��������Ϣ</td>
    	</tr>
    </table>
<DIV id=DivLCInsured STYLE="display:''">

    <table class=common>	
  <TR CLASS=common>
    <TD CLASS=title>
      *���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Name VALUE="" CLASS=common MAXLENGTH=20 verify="����|notnull" >
    </TD>
    <TD CLASS=title>
      *�Ա� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Sex VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('Sex', [this]);" onkeyup="return showCodeListKey('Sex', [this]);" verify="�������Ա�|notnull&code:Sex" >
    </TD>
    <TD CLASS=title>
      *�������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Birthday VALUE="" CLASS=common verify="�����˳�������|date&notnull" >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      *֤������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDType VALUE="" MAXLENGTH=1 CLASS=code ondblclick="return showCodeList('IDType', [this]);" onkeyup="return showCodeListKey('IDType', [this]);" verify="������֤������|code:IDType" >
    </TD>
    <TD CLASS=title>
      *֤������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=IDNo VALUE="" CLASS=common MAXLENGTH=20 >
    </TD>
    <TD CLASS=title>
      ����
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=NativePlace VALUE="" CLASS=code ondblclick="return showCodeList('NativePlace', [this]);" onkeyup="return showCodeListKey('NativePlace', [this]);" verify="�����˹���|code:NativePlace" >
    </TD>
  </TR>

  
  <TR CLASS=common>
  <TD CLASS=title>
      *��ַ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input class="code" NAME=AddressNo VALUE="" CLASS=common MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      �������ڵ� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=RgtAddress VALUE="" CLASS=common MAXLENGTH=80 >
    </TD>
    <TD CLASS=title>
      סַ 
    </TD>
    <TD CLASS=input COLSPAN=3>
      <Input NAME=HomeAddress VALUE="" CLASS=common3 MAXLENGTH=80 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      �������� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=HomeZipCode VALUE="" CLASS=common MAXLENGTH=6 verify="��������������|zipcode" >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��1��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone VALUE="" CLASS=common MAXLENGTH=18 >
    </TD>
    <TD CLASS=title>
      ��ϵ�绰��2��
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=Phone2 VALUE="" CLASS=common >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      ������λ
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=GrpName VALUE="" CLASS=common MAXLENGTH=60 >
    </TD>
    <TD CLASS=title>
      ְҵ�����֣� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=WorkType VALUE="" CLASS=common MAXLENGTH=10 >
    </TD>
    <TD CLASS=title>
      ��ְ�����֣� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=PluralityType VALUE="" CLASS=common MAXLENGTH=10 >
    </TD>
  </TR>

  <TR CLASS=common>
    <TD CLASS=title>
      *ְҵ���� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationCode VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('OccupationCode', [this,OccupationType],[0,2]);" onkeyup="return showCodeListKey('OccupationCode', [this,OccupationType],[0,2]);" verify="������ְҵ����|code:OccupationCode" >
    </TD>
    <TD CLASS=title>
      ְҵ��� 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=OccupationType VALUE="" CLASS=readonly readonly TABINDEX=-1 MAXLENGTH=10 verify="������ְҵ���|notnull&code:OccupationType" >
    </TD>
    <TD CLASS=title>
      *��ͥ������ 
    </TD>
    <TD CLASS=input COLSPAN=1>
      <Input NAME=FamilyContNo VALUE="" CLASS=common MAXLENGTH=10  >
    </TD>
  </TR>
  <!-- ��֪��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 ��֪��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
<tr>
<td>
      <input type =button class=cssButton value="���汻������Ϣ" onclick=""> 
      </td>
      <tr>
</TABLE>
</DIV>

<table>
    <TR class=common> 
    <TD class=common>
      <input type =button class=cssButton value="�� һ ��" onclick="returnparent();">      
     </TD>
         
     <TD   class=common>
      <input type =button class=cssButton value="¼�뱻����������Ϣ" onclick="InputPolicy();">      
     </TD>
     
     <TD   class=common>
      <input type =button class=cssButton value="��ѯ������������Ϣ" onclick="queryInsuRisk();">      
     </TD>
     
    </TR> 
    </table> 
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>




