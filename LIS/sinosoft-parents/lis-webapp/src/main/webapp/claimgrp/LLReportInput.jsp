<%
//**************************************************************************************************
//Name��LLReportInput.jsp
//Function�������Ǽ�
//Author��zl
//Date: 2005-6-9 15:31
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
  String CurrentDate = PubFun.getCurrentDate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");

  String tClmNo = request.getParameter("claimNo");  //�ⰸ��
  String tIsNew = request.getParameter("isNew");  //������
  
  String tMissionID = request.getParameter("MissionID");  //����������ID
  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID  
//=======================END========================
%>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="LLReport.js"></SCRIPT>
  <%@include file="LLReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form method=post name=fm target="fraSubmit">
  <!--������Ϣ-->
  <table>
   <TR>
    <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>������Ϣ</TD>
   </TR>
  </table>

  <Div id= "divLLReport1" style= "display: ''">
   <table class= common>
    <TR class= common>
     <TD class= title> �¼��� </TD>
     <TD class= input> <Input class= "readonly" readonly name=AccNo></TD>
     <TD class= title> �ⰸ�� </TD>
     <TD class= input> <Input class="readonly" readonly  name=RptNo ></TD>
     <TD class= title> ���������� </TD>
     <TD class= input> <Input class= common name=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR class= common>
     <TD class= title> �����˵绰 </TD>
     <TD class= input> <Input class= common name=RptorPhone ></TD>
     <TD class= title> ������ͨѶ��ַ </TD>
     <TD class= input> <Input class= common name=RptorAddress ></TD>
     <TD class= title> ������������˹�ϵ </TD>
     <TD class= input> <Input class=codeno name=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR class= common>
     <TD class= title> ������ʽ </TD>
     <TD class= input> <Input class=codeno name="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" value="�绰����" readonly=true></TD>
     <TD class= title> ���յص� </TD>
     <TD class= input> <Input class= common name=AccidentSite ></TD>
     <TD class= title> �������� </TD>
    <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
    </TR>
    <TR class= common>
     <TD class= title> ��Ͻ���� </TD>
     <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>
     <TD class= title> ���������� </TD>
     <TD class= input> <Input class="readonly" readonly name=Operator ></TD>
    <TD class= title> ��������</TD>
     <TD class= input> <Input class= codeno value="1" name=RgtClass ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class=codename name=RgtClassName readonly=true value="����"></TD>
     <!--TD class= title> ����״̬ </TD>
     <TD class= input> <Input class= "readonly" readonly name=CaseState></TD-->
    </TR>
   </table>
  </Div>
  <hr>

  <!--��������Ϣ-->
  <table class= common>
   <tr class= common>
    <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
   </tr>
  </table>
  <table>
   <tr>
    <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
    <td class=titleImg> ��������Ϣ </td>
   </tr>
  </table>
  <Div id= "divLLSubReport" style= "display: ''">
   <table>
    <tr>
     <td><input class=cssButton name=QueryPerson value="�����˲�ѯ" type=button onclick="showInsPerQuery()" ></td>
     <td><input class=cssButton type=hidden name=QueryReport value="�¼���ѯ" type=button onclick="queryLLAccident()"></td>
     <td>
    <span id="operateButton2" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
    <span id="operateButton3" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
     </td>
     <!--td><input class=cssButton name=QueryCont1 VALUE="Ͷ������ѯ"  TYPE=button onclick=""></td-->
     <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
     <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
     <td><input class=cssButton type=hidden name=MedCertForPrt VALUE="��ӡ�˲м���֪ͨ"  TYPE=button onclick="PrintMedCert()"></td>
     <td><input class=cssButton type=hidden name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
     <td><input class=cssButton type=hidden name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
    </tr>
   </table>
   <!--��������Ϣ��-->
   <span id="spanSubReport" >
    <table class= common>
     <TR class= common>
    <!--�����˱���,��������-->
    <TD class= title> ���������� </TD>
    <TD class= input> <Input class="readonly" readonly name=customerName></TD>
    <TD class= title> ���������� </TD>
    <TD class= input> <Input class="readonly" readonly name=customerAge></TD>
    <TD class= title> �������Ա� </TD>
    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=readonly readonly name=SexName readonly=true></TD>
     </TR>
     <TR class= common>
    <TD class= title> VIP�ͻ���ʾ</TD>
    <TD class= input> <Input class="readonly" readonly name=IsVip></TD>
    <TD class= title> �¹�����</TD>
    <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="AccidentDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> ����ԭ��</TD>
    <TD class= input><Input class=codeno name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
     </TR>
     <TR class= common>
    <TD class= title> ����ҽԺ</TD>
    <TD class= input> <Input class=codeno name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
    <TD class= title> �������� </TD>
    <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="AccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> ����ϸ��</TD>
    <TD class= input> <Input class=codeno name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName readonly=true></TD>
     </TR>
     <TR class= common>
    <TD class= title> �������</TD>
    <TD class= input> <Input class=codeno name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
    <TD class= title> ���ս��1</TD>
    <TD class= input> <Input class=codeno name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
    <TD class= title> ���ս��2</TD>
    <TD class= input> <Input class=codeno name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name readonly=true></TD>
     </TR>
     <!--TR class= common>
    <TD class= title> ������ʶ</TD>
    <TD class= input> <Input class=codeno name=IsDead ondblclick="return showCodeList('llDieFlag',[this,IsDeadName],[0,1]);" onkeyup="return showCodeListKey('llDieFlag',[this,IsDeadName],[0,1]);"><input class=codename name=IsDeadName readonly=true></TD>
     </tr-->
    </table>

    <table class= common>
     <TR class= common>
    <TD class= title> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD align=left>
      <input type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> ��� </input>
<!--       <input type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> �߲� </input> -->
      <input type="checkbox" value="04" name="claimType"> �ش󼲲� </input>
      <input type="checkbox" value="01" name="claimType"> �˲� </input>
<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input> -->
      <input type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> ҽ�� </input>
<!--       <input type="checkbox" value="05" name="claimType" > ���ּ��� </input> -->
      <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input>
    </td>
     </TR>
    </table>
    <table class= common>
     <TR  class= common>
    <TD  class= title> �¹����� </TD>
     </tr>
     <TR class= common>
    <TD class= input> 
      <span id="spanText3" style= "display: ''">
       <textarea name="AccDesc" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <span id="spanText4" style= "display: 'none'">
       <textarea disabled name="AccDescDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
    </TD>
     </tr>
     <TR class= common>
    <TD class= title> ��ע </TD>
     </tr>
     <TR class= common>
    <TD class= input>
      <span id="spanText1" style= "display: ''">
       <textarea name="Remark" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <span id="spanText2" style= "display: 'none'">
       <textarea disabled name="RemarkDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
    </TD>
     </tr>
    </table>
   </span>
  </div>
  <hr>
  <span id="operateButton4" >
   <table>
    <tr>
     <td><INPUT class=cssButton name="DoHangUp" VALUE="��������"  TYPE=button onclick="showLcContSuspend()" ></td>
     <td><INPUT class=cssButton name="CreateNote" VALUE="���ɵ�֤"  TYPE=button onclick="showAffix()"></td>
     <td><INPUT class=cssButton name="CreateNote" VALUE="��ӡ��֤"  TYPE=button onclick="showPrtAffix()"></td>     
     <td><INPUT class=cssButton name="BeginInq" VALUE="�������"  TYPE=button onclick="showBeginInq()"></td>
     <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
     <td><INPUT class=cssButton type=hidden name="Condole" VALUE="����ο��"  TYPE=button onclick="showCondole()"></td>
     <td><INPUT class=cssButton type=hidden name="SubmitReport" VALUE="����ʱ�"  TYPE=button onclick="showBeginSubmit()"></td>
     <td><INPUT class=cssButton type=hidden name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
     <td><INPUT class=cssButton name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
    </tr>
   </table>
  </span>
  <hr>
  <table>
   <tr>
    <td><INPUT class=cssButton name="RptConfirm" VALUE="����ȷ��"  TYPE=button onclick="reportConfirm()"></td>
    <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
   </tr>
  </table>
  <%
  //������,������Ϣ��
  %>
  <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->
  <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
  <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->

  <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
  <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
  
  <!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
  <input type=hidden id="PrtSeq" name="PrtSeq">
  
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
