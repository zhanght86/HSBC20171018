<%
//**************************************************************************************************
//Name��LLGrpBatchAuditInput.jsp
//Function������������������ҳ��
//Author��pd
//Date: 2005-10-21
//Desc: 
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  String CurrentDate = PubFun.getCurrentDate();
  String RptNo = request.getParameter("RptNo");
  String Flag = request.getParameter("Flag"); //1���װ��� 2���׸���
  String tIsNew = request.getParameter("isNew");

  String tMissionID = request.getParameter("MissionID");  //����������ID
  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID  
  
//=======================END========================
%>


<script type="text/JavaScript">
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LLGrpBatchAudit.js"></SCRIPT>
  <%@include file="LLGrpBatchAuditInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
  <!--������Ϣ-->
<!--   <table>
  <TR>
  <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
  <TD class= titleImg>������Ϣ</TD>
  </TR>
  </table>
  <Div  id= "divLLReport1" style= "display: ''">
  <table  class= common>
    <TR class= common>
    <TD class= title> ����ͻ��� </TD>
    <TD class= input> <Input class="common"  name=GrpCustomerNo onkeydown="ClientQuery2();"></TD>
    <TD class= title> ��λ���� </TD>
    <TD class= input> <Input class="common"  name=GrpName onkeydown="ClientQuery2();"></TD>
    <TD class= title> ���屣���� </TD>
    <TD class= input> <Input class="common"   name=GrpContNo onkeydown="ClientQuery2();"></TD>
    </TR>
  <TR class= common>
    <TD class= title> ����ԭ��</TD>
    <TD class= input><Input class=codeno  name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <input class="readonly" type=hidden dateFormat="short" name="AccidentDate2" >
<TD class= title> </TD>
<TD class= input></TD>
    <TD class= title> ���ֱ��� </TD>
    <TD class= input> <Input class=code name=Polno ondblclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onkeyup="return showCodeListKey('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');"></TD>
  </TR>
  <TR  class= common>
<!--     <TD  class= title> �¼��� </TD> -->
<!--       <Input class= "readonly" type="hidden" name=AccNo>
  <TD  class= title> �ⰸ�� </TD>
  <TD  class= input> <Input class="readonly" readonly name=RptNo ></TD>
  <TD  class= title> Ͷ������ </TD>
  <TD  class= input> <Input class= "readonly" readonly  name=Peoples ></TD>
  <TD  class= title> �������� </TD>
  <TD  class= input> <Input class= "readonly" readonly name=PeopleNo ></TD>
  </TR>
  </table>
  </Div> -->
    <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
  <table  class= common>
     
     <TR  class= common>
      <TD  class= title> ������ </TD>
      <TD  class= input> <Input class="common wid" readonly  name=RptNo id=RptNo ></TD>
      <TD class= title> ����ͻ��� </TD>
      <TD class= input> <Input class="common wid"  name=GrpCustomerNo id=GrpCustomerNo onkeydown="QueryOnKeyDown(3);"><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD class= title> ���屣���� </TD>
      <TD class= input> <Input class="common wid"   name=GrpContNo id=GrpContNo onkeydown="QueryOnKeyDown(3);"><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>

    <TR  class= common>
    <TD class= title> ��λ���� </TD>
    <TD class= input> <Input class="common wid"  name=GrpName id=GrpName onkeydown="QueryOnKeyDown(3);"><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> �������� </TD>
    <TD  class= input> <input class="common wid" readonly name="RptDate" id="RptDate" ></TD>
    <TD class= title> Ͷ�������� </TD>
    <TD class= input> <Input class="readonly wid" readonly name=Peoples id=Peoples></TD>
    </TR>
    
    <TR  class= common>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> �����˵绰 </TD>
    <TD  class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone ></TD>
    <TD class= title> ���ֱ��� </TD>
    <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=Polno id=Polno ondblclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onkeyup="return showCodeListKey('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');"></TD>
    </TR>

    <TR  class= common>
    <TD  class= title> ������ͨѶ��ַ </TD>
    <TD  class= input> <Input class="wid" class= common name=RptorAddress id=RptorAddress ></TD>
    <TD  class= title> ���������¹��˹�ϵ </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> ���յص� </TD>
    <TD  class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>
    </TR>

    <TR  class= common>
    <TD class= title> ����ԭ��</TD>
    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> �����˴��� </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeCode id=AssigneeCode onblur="queryAgent();"></TD>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeName id=AssigneeName ></TD>
    </TR>

    <TR  class= common>
    <TD  class= title> �������Ա� </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeSex id=AssigneeSex ondblclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,AssigneeSexName],[0,1]);"><input class=codename name=AssigneeSexName id=AssigneeSexName readonly=true></TD>
    <TD  class= title> �����˵绰 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneePhone id=AssigneePhone ></TD>
    <TD  class= title> �����˵�ַ </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeAddr id=AssigneeAddr  ></TD>
<!--     <TD  class= title> ��������</TD>-->
 <Input class= code type=hidden name=RgtClass value="2"><input class=codename type=hidden name=RgtClassName readonly=true>
    </TR>

    <TR class= common>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeType id=AssigneeType CodeData="0|^0|ҵ��Ա^1|�ͻ�" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])"  onclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName id=AssigneeTypeName readonly=true></TD>
    <TD  class= title> �������ʱ� </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeZip id=AssigneeZip ></TD>
    <TD class= title>  ��������</TD>
    <TD class= input>  <input class="wid" class="readonly" readonly  name="AcceptedDate" id="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR class= common>
<!--     <TD  class= title> �¼��� </TD> -->
      <Input class="wid" class= "readonly" type="hidden" name=AccNo id=AccNo>
    </TR>

    <TR  class= common>
    <TD  class= title> ��Ͻ���� </TD>
    <TD  class= input> <Input class="wid" class= "readonly" readonly name=MngCom></TD>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class="wid" class="readonly" readonly name=OOperator ></TD>
      <TD class= title> ����Ԥ�����</TD>
      <TD class= input> <Input class="wid" class="readonly"  readonly name=Grpstandpay></TD>
    </TR>

  </table>
  </Div>
  <!--��������Ϣ-->
  <table class= common>
  <tr class= common>
  <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
  </tr>
  </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage3.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage3.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage3.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage3.lastPage();"></td>
            </tr>
        </table>        
  <table>
  <tr>
  <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
  <td class=titleImg> �ͻ���Ϣ </td>
  </tr>
  </table>
  <Div id= "divLLSubReport" style= "display: ''" class="maxbox1">
  
  <!--��������Ϣ��-->
  <span id="spanSubReport" >
  <table class= common>
  <TR class= common>
    <!--�����˱���,��������-->
    <TD class= title> �ͻ����� </TD>
    <TD class= input> <Input class="readonly wid" readonly name=customerName id=customerName></TD>
    <TD class= title> �ͻ����� </TD>
    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
    <TD class= title> �ͻ��Ա� </TD>
    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
  </TR>
  <TR class= common>
    <TD class= title> ֤������</TD>
    <TD class= input> <Input class="readonly wid" readonly name=IDTypeName id=IDTypeName></TD>
    <Input class="readonly" type = 'hidden' readonly name=IDType id=IDType>
    <TD class= title> ֤������</TD>
    <TD class= input> <Input class="readonly wid" readonly name=IDNo id=IDNo></TD>
      <TD class= title> ��������</TD>
      <TD class= input>  <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate" onclick="checkapplydate()"><font size=1 color='#ff0000'><b>*</b></font>-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" onclick="checkapplydate()" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
  </TR>
  </table>

  <table class= common>
  <TR class= common>
    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD align=left>
		<input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">���</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"><span style="vertical-align:middle"> �߲�</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"> <span style="vertical-align:middle">�ش󼲲�</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span style="vertical-align:middle">�м������ˡ����ۡ���Ҫ�����г�</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">����</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">ҽ��</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"> <span style="vertical-align:middle">���ּ���</span> </input>
    </td>
  </TR>
  </table></div>
<table>
  <tr>
  <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
  <td><input class=cssButton name=addbutton     VALUE="��  ��"  TYPE=button onclick="saveClick()" ></td>
  <td><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></td>
  <td><input class=cssButton name=deletebutton  VALUE="ɾ  ��" TYPE=button onclick="deleteClick()" ></td>
  <td><input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onclick="showInsuredLCPol()"></td>
  <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
  <td><input class=cssButton name=QueryCont5 VALUE="�����ⰸ����" TYPE=button onclick="getLastCaseInfo()"></td>
  <td><input class=cssButton name=DiskOutput VALUE="������Ϣ����" TYPE=button onclick="PrintReportClass();"></td>
  <td><input class=cssButton name=DiskInput VALUE="��������Ϣ����" TYPE=button onclick="getin()">
  <td><input class=cssButton type=button name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
  </td>
  </tr>
  </table>
<!--  ����/סԺ --> 
	<table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
                <td class= titleImg>����/סԺ¼����Ϣ</td>
             </tr>
    </table>
    <Div  id= "divPayPlan1" style= "display: ''">
  <table class= common>
  <tr class= common>
  <td text-align: left colSpan=1><span id="spanMedFeeInHosInpGrid" ></span></td>
  </tr>
  <!-- </table>
        <table >
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>    -->     
  </table>
  </div>

        <table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
                <td class= titleImg>��������¼����Ϣ</td>
             </tr>
        </table>
        <Div  id= "peer" style= "display: ''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeCaseInfoGrid"></span></td>
                </tr>
            </Table></div>
  <!--<tr>
  <td><input class=cssButton name=addbutton2  VALUE="��  ��"  TYPE=button onclick="StandPaySave()" ></td>
  <td><input class=cssButton name=updateFeebutton  VALUE="�� ��"  TYPE=button onclick="StandPaySave()" ></td>
  <td><input class=cssButton name=deleteFeebutton  VALUE="ɾ ��"  TYPE=button onclick="deleteFeeClick()" ></td>
  </tr>-->
  </table>
<!--������Ϣ-->
        <table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,air);"></td>
                <td class= titleImg>���ַ���</td>
             </tr>
        </table><Div  id= "air" style= "display: ''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeOtherGrid"></span></td>
                </tr>
            </Table></div>
<!--�籣����������-->
        <table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,wer);"></td>
                <td class= titleImg>�籣����������</td>
             </tr>
        </table><Div  id= "wer" style= "display: ''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeThreeGrid"></span></td>
                </tr>
            </Table></div>

  <!--����������Ϣ-->
<!--    <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
  <td class= titleImg>����������Ϣ</td>
   </tr>
  </table>
  <Div  id= "divBaseUnit6" style= "display:''">
  <table class= common>
  <TR class= common>
  <TD  class= title>��������</TD>
  <TD  class= input>
  <Input class=codeno  name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
  </TD>
   <TD  class= title> ������ʶ</TD>
  <TD  class= input> <Input class=codeno  name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
  <td>&nbsp;&nbsp;</td>
   </tr> 
  </table>
  </div>  -->
  
  <table>
  <tr>
  	<!--
  <td><input class=cssButton name="QueryReport" value="Ԥ�����" type=button onclick="operStandPayInfo()"></td>
  -->
  <td><input class=cssButton VALUE="�˻��ʽ����" TYPE=button name="LCInsureAcc"  disabled onclick="ctrllcinsureacc();"></td>
  <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="ҽ�Ƶ�֤¼��"  TYPE=button onclick="showLLMedicalFeeInp();"></td>    
  <td><INPUT class=cssButton name="dutySet" VALUE=" ƥ�䲢���� "  TYPE=button onclick="showMatchDutyPay();"></td>
  <td><input class=cssButton name="QuerydutySet" value="�����ѯ" type=button onclick="QuerydutySetInfo()"></td>
  <td><INPUT class=cssButton name="BeginInq" VALUE="�������"  TYPE=button onclick="showBeginInq()"></td>
  <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
  <td><INPUT class=cssButton name="SubmitReport" VALUE="����ʱ�"  TYPE=button onclick="showBeginSubmit()"></td>
  <td><INPUT class=cssButton name="ViewReport" VALUE=" �鿴�ʱ� " TYPE=button onclick="showQuerySubmit()"></td>
  <td><INPUT class=cssButton name="CreateNote" VALUE="�����˷���" TYPE=button onclick="showBnf()"></td>
  <!--<td><INPUT class=cssButton name="ScanInfo" VALUE="ɨ�����Ϣ"  TYPE=button onclick="showScanInfo();" ></td>  
  <td><INPUT class=cssButton name="AddAffix" VALUE="�����������"  TYPE=button onclick="AddAffixClick();" ></td>
  <td><INPUT class=cssButton name="LoadAffix" VALUE="����������"  TYPE=button onclick="LoadAffixClick();" ></td>
  -->
  </tr>
  </table>
  

    <!--�����ⰸ������Ϣ-->
    <table>
     <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimGrid);"></td>
      <td class= titleImg>�ⰸ������Ϣ</td>
     </tr>
    </table>

    <Div  id= "divClaimGrid" style= "display:''">
     <Table  class= common>
      <tr><td text-align: left colSpan=1>
       <span id="spanClaimGrid"></span>
      </td></tr>
     </Table>
    </div>

    <!--�����ⰸ�������ͼ�����Ϣ-->
    <table>
     <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DutyKind);"></td>
      <td class= titleImg>�������ͼ�����Ϣ</td>
     </tr>
    </table>
    
    <Div  id= "DutyKind" style= "display:''">
     <Table  class= common>
      <tr><td text-align: left colSpan=1>
         <span id="spanDutyKindGrid"></span>
      </td></tr>
     </Table>
    </div>
    
    <!--���ձ����������ͼ�����Ϣ-->
        	<table>
        	     <tr>
        	        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyKind);"></td>
        	        <td class= titleImg>����������Ϣ</td>
        	     </tr>
        	</table>
        	<Div  id= "PolDutyKind" style= "display:''">
        	     <Table  class= common>
        	          <tr><td text-align: left colSpan=1>
        	               <span id="spanPolDutyKindGrid"></span>
        	          </td></tr>
        	     </Table>
        	</div>
    
        <!--����ƥ����Ϣ-->
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit3);"></td>
                <td class= titleImg>���������Ϣ</td>
             </tr>
        </table>
        <Div  id= "divBaseUnit3" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
                </tr>
            </Table>
        </div>
        
   <!--��ȫƥ����Ϣ-->   
     <table>
     <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyCode);"></td>
      <td class= titleImg>��ȫ��Ŀ��Ϣ</td>
     </tr>
    </table>

    <Div  id= "LPEdorItem" style= "display:''">
    <Table  class= common>
      <tr>
      <td text-align: left colSpan=1><span id="spanLPEdorItemGrid"></span></td>
      </tr>
    </Table>
    </div> 

  <div id= "divBaseUnit5" style= "display: none">
  <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
  <td class= titleImg>�����⸶����</td>
   </tr>
  </table>
  <Div  id= "divBaseUnit4" style= "display:''" class="maxbox">
  <table class= common>
  <TR class= common>
    <TD  class= title>�⸶����</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GiveType id=GiveType  ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName id=GiveTypeName readonly=true>
    <TD  class= title></TD>
    <TD  class= input><Input class="wid" class=readonly readonly ></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
  </tr>
  </table>
  <Div  id= "divGiveTypeUnit1" style= "display: ''">
  <table class= common>
    <TR class= common>
    <TD  class= title>�������</TD>
<!--     <TD  class= input><Input class=common name=RealPay onblur="checkAdjMoney();"></TD> -->
    <TD  class= input><Input class="wid" class=common name=RealPay id=RealPay ></TD>
    <TD  class= title>����ԭ��</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AdjReason id=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName id=AdjReasonName readonly=true>
    <TD  class= title>��������״̬</TD>
      <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=polstate id=polstate ondblclick="return showCodeList('newpolstate',[this,polstateName],[0,1],null,null,null,null,120);" onclick="return showCodeList('newpolstate',[this,polstateName],[0,1],null,null,null,null,120);" onkeyup="return showCodeListKey('newpolstate',[this,polstateName],[0,1]);"><input class=codename name=polstateName id=polstateName readonly=true></td>
    </tr>
  </table>
  <Div  id= "divGiveTypeUnit3" style= "display: none">
      <table class= common>
      
      <TD  class= title></TD>
      <TD  class= input></TD>
      <TD  class= title></TD>
      <TD  class= input></TD>            
      </table>
  </Div>
  <table class= common>
    <TR class= common>
    <TD class= title> ������ע </TD>
    </tr>
    <TR class= common>
    <TD colspan="6" style="padding-left:16px"> <textarea name="AdjRemark" cols="197" rows="4" witdh=25% class="common"></textarea></TD>
    </tr>
   </table>
  </div>
  <Div  id= "divGiveTypeUnit2" style= "display: none">
  <table class= common>
    <TR class= common>
    <TD  class= title>�ܸ�ԭ��</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GiveReason id=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class=codename name=GiveReasonName readonly=true></td>
    <TD  class= title>�ܸ�����</TD>
    <TD  class= input><Input class="wid" class=common name=GiveTypeDesc id=GiveTypeDesc></td>
    <TD  class= title></TD>
    <TD  class= input></TD>      
    </tr>
  </table>
  <table class= common>
    <TR class= common >
    <TD class= title> ���ⱸע </TD>
    </tr>
    <TR class= common>
    <TD colspan="6" style="padding-left:16px"> <textarea name="SpecialRemark" cols="197" rows="4" witdh=25% class="common"></textarea></TD>
    </tr>
  </table>
  </div>
  <table>
   <tr>
     <td><INPUT class=cssButton name="addUpdate" VALUE=" ����޸� "  TYPE=button onclick="AddUpdate()" ></td>
     <td><INPUT class=cssButton name="saveUpdate" VALUE=" �����޸�"  TYPE=button onclick="SaveUpdate();" ></td>
   </tr>
  </table>
  </div>
  </div>
  

  <Div  id= "divSimpleClaim2" style= "display:''">
  <Div  id= "divSimpleClaim" style= "display:''">
  <Input class=codeno type = "hidden" name=SimpleConclusion value = "1">
  </div>
  <INPUT class=cssButton name="simpleClaim" VALUE="��������ȷ��"  TYPE=button onclick="confirmClick()">
  <INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()">
  </div>

  <!--���װ���������Ϣ-->
  <Div  id= "divSimpleClaim3" style= "display:''">
  <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSimpleClaim);"></td>
   <td class= titleImg>��������������Ϣ</td>
   </tr>
  </table>
<Div id= "divLLAudit" style= "display: 'none'" class="maxbox1">
    <table class= common>
   <TR class= common>
     <TD class= title>������</TD>
   </tr>
   <TR class= common>
     <TD colspan="6" style="padding-left:16px"> <textarea name="AuditIdea" cols="197" rows="4" witdh=25% class="common"></textarea></TD>
   </tr>
  </table>
</div>
  <Div  id= "divSimpleClaim" style= "display:''">
  <table class= common>
  <TR class= common>
  <TD  class= title>����������˽���</TD>
  <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SimpleConclusion2 id=SimpleConclusion2 ondblClick="showCodeList('llexamconclusion2',[this,SimpleConclusion2Name],[0,1],null,'1 and code <> #0#',1,1,'150');" onClick="showCodeList('llexamconclusion2',[this,SimpleConclusion2Name],[0,1],null,'1 and code <> #0#',1,1,'150');" onkeyup="showCodeListKeyEx('llexamconclusion2',[this,SimpleConclusion2Name],[0,1],null,'1 and code <> #0#',1,1,'150');"><input class=codename name=SimpleConclusion2Name id=SimpleConclusion2Name readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
  <TD  class= title>���ⱸע</TD>
  <TD  class= input><Input class="wid" class=common name=SpecialRemark1 id=SpecialRemark1></TD>
  <TD class=title><!--�ܸ�ԭ��--></TD>
		<TD class=input><!--<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ProtestReason id=ProtestReason
			ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);"
			onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input
			class=codename name=ProtestReasonName id=ProtestReasonName readonly=true>--></TD>
  </tr>
  </table>
  </div>
<Div id="divLLAudit2" style="display: none">
<table class=common>
	<TR class=common>
		
		<TD class=title>�ܸ�����</TD>
		<TD class=input><Input class="wid" class=common name=ProtestReasonDesc id=ProtestReasonDesc></TD>
        <TD class=title></TD><TD class=input></TD>
        <TD class=title></TD><TD class=input></TD>
	</tr>
</table>
</div>
  

  
  
   
  <!--<INPUT class=cssButton name="ConclusionUp" VALUE="��Ȩ���ϱ�"  TYPE=button onclick="ConclusionUpClick()">-->
    <INPUT class=cssButton name="simpleClaim2" VALUE="���۱���"  TYPE=button onclick="confirmClick2()">
  <INPUT class=cssButton name="ConclusionSave" VALUE="���ȷ��"  TYPE=button onclick="ConclusionSaveClick()">
  <!--<INPUT class=cssButton name="ConclusionBack" VALUE="����ȷ��"  TYPE=button onclick="AuditConfirmClick()">
  --><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()">
  </div>

  <%
  //������,������Ϣ��
  %>
  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden name=hideOperate value=''>
  <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
  <Input type=hidden id="ManageCom" name="ManageCom"><!--��½��վǰ��������-->
  <Input type=hidden id="AllManageCom" name="AllManageCom"><!--��½��վ-->
  <Input type=hidden id="Operator" name="Operator"><!--��½�û�-->
  <Input type=hidden id="tOperator" name="tOperator"><!--�����û�-->
  <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
  <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
  <input type=hidden id="clmState" name="clmState"><!--����״̬-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
  <input type=hidden id="RgtClass" name="RgtClass" value ="2"><!--���ո��մ���-->
  
  <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--�Ƿ�Ԥ��-->
  <Input type=hidden id="customerNo" name="customerNo" ><!-- �����˱��� -->
  <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--���Ȩ��-->
  <input type=hidden id="clmState2" name="clmState2"><!--����״̬-->
  <input type=hidden id="Flag" name="Flag"><!--����ҳ���ж�-->
  
  <input type=hidden id="RgtNo" name="RgtNo">
  <input type=hidden id="tPolNo" name= "tPolNo">
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <input type=hidden id="tSQL" name= "tSQL">
  <input type=hidden class=common  name="AccRiskCode" > 
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
