<%
//**************************************************************************************************
//Name��LLClaimRegister.jsp
//Function�������Ǽ�
//Author��zl
//Date: 2005-6-14 20:04
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
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    String CurrentDate = PubFun.getCurrentDate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");

  String tClmNo = request.getParameter("claimNo");  //�ⰸ��
  String tIsNew = request.getParameter("isNew");  //�Ƿ�����
  String tClmState = request.getParameter("clmState");  //����״̬

  String tMissionID = request.getParameter("MissionID");  //����������ID
  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
//=======================END========================
%>
<head >
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLClaimRegister.js"></SCRIPT>
   <%@include file="LLClaimRegisterInit.jsp"%>
</head>
<body onload="initForm()" >
<form action="./LLClaimRegisterSave.jsp" method=post name=fm target="fraSubmit">
    <!--������Ϣ-->
    <table>
    <TR>
    <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>������Ϣ</TD>
    </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''">
    <table  class= common>
     <TR  class= common>
      <TD  class= title> �¼��� </TD>
        <TD  class= input> <Input class= "readonly" readonly name=AccNo></TD>
       <TD  class= title> �ⰸ�� </TD>
        <TD  class= input> <Input class="readonly" readonly  name=RptNo ></TD>
        <TD  class= title> ���������� </TD>
        <TD  class= input> <Input class= common name=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR  class= common>
        <TD  class= title> �����˵绰 </TD>
        <TD  class= input> <Input class= common name=RptorPhone ></TD>
      <TD  class= title> ������ͨѶ��ַ </TD>
        <TD  class= input> <Input class= common name=RptorAddress ></TD>
      <TD  class= title> ���������¹��˹�ϵ </TD>
      <TD  class= input> <Input class=codeno name=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR  class= common>
         <TD  class= title> ���յص� </TD>
        <TD  class= input> <Input class= common name=AccidentSite ></TD>
       <TD  class= title> ������������ </TD>
        <TD  class= input> <input class="readonly" readonly name="RptDate" ></TD>
      <TD  class= title> ��Ͻ���� </TD>
        <TD  class= input> <Input class= "readonly" readonly name=MngCom></TD>
      </TR>
    <TR  class= common>
      <TD  class= title> ���������� </TD>
        <TD  class= input> <Input class="readonly" readonly name=Operator ></TD>
      <TD  class= title> ���������� </TD>
        <TD  class= input> <Input class= codeno name=AssigneeType CodeData="0|^0|ҵ��Ա^1|�ͻ�" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName readonly=true></TD>
      <TD  class= title> �����˴��� </TD>
        <TD  class= input> <Input class= common name=AssigneeCode onblur="queryAgent();"></TD>
    </TR>
    <TR  class= common>
      <TD  class= title> ���������� </TD>
        <TD  class= input> <Input class= common name=AssigneeName ></TD>
      <TD  class= title> �������Ա� </TD>
        <TD  class= input> <Input class= codeno name=AssigneeSex ondblclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,AssigneeSexName],[0,1]);"><input class=codename name=AssigneeSexName readonly=true></TD>
      <TD  class= title> �����˵绰 </TD>
        <TD  class= input> <Input class= common name=AssigneePhone ></TD>
    </TR>
    <TR  class= common>
      <TD  class= title> �����˵�ַ </TD>
        <TD  class= input> <Input class= common name=AssigneeAddr ></TD>
      <TD  class= title> �������ʱ� </TD>
        <TD  class= input> <Input class= common name=AssigneeZip ></TD>
      <TD  class= title> ��������</TD>
        <TD  class= input> <Input class= codeno name=RgtClass ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class=codename name=RgtClassName readonly=true></TD>
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
        <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
        <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
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
        <TD class= input> <Input class=codeno name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" ><input class=codename name=AccResult1Name readonly=true></TD>
        <TD class= title> ���ս��2</TD>
        <TD class= input> <Input class=codeno name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name readonly=true></TD>
        </TR>
        <TR class= common>
        <TD class= title> ��֤��ȫ��ʾ</TD>
        <TD class= input> <Input class=codeno name=IsAllReady disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName readonly=true></TD>
        </tr>
    </table>

    <table class= common>
        <TR class= common>
        <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
        <TD align=left>
        <input type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> ��� </input>
<!--         <input type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> �߲� </input> -->
        <input type="checkbox" value="04" name="claimType"> �ش󼲲� </input>
        <input type="checkbox" value="01" name="claimType"> �˲� </input>
<!--         <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input> -->
        <input type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> ҽ�� </input>
<!-- 
        <input type="checkbox" value="05" name="claimType" > ���ּ��� </input> -->
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
    <span id="operateButton3" >
    <table>
  <tr>
    <td><INPUT class=cssButton name="DoHangUp" VALUE="��������"  TYPE=button onclick="showLcContSuspend()" ></td>
    <td><INPUT class=cssButton name="CreateNote" VALUE="��֤����"  TYPE=button onclick="showRgtMAffixList()"></td>
        <td><INPUT class=cssButton name="CreateNote" VALUE="���䵥֤"  TYPE=button onclick="showRgtAddMAffixList()"></td>
    <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
    <td><INPUT class=cssButton type=hidden name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
      <td><INPUT class=cssButton  name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
      <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="ҽ�Ƶ�֤¼��"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
  </tr>
    </table>
  </span>
    <hr>
    <!--����������Ϣ*********************************Beg********************************************-->
    <table>
    <tr>
    <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLRegisterConclusion);"></td>
    <td class=titleImg> ����������Ϣ </td>
    </tr>
    </table>
    <Div id= "divLLRegisterConclusion" style= "display: ''">
  <table class= common>
      <TR class= common>
      <TD  class= title> ��������</TD>
      <TD  class= input> <Input class=codeno name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onFocus="showDIV();"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD  class= title> </TD>
      <TD  class= input> </TD>
      <TD  class= title> </TD>
      <TD  class= input> </TD>
      </tr>
  </table>
  <span id= "spanConclusion1" style= "display: none">
    
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
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyCode);"></td>
        <td class= titleImg>���������Ϣ</td>
         </tr>
    </table>
    <Div  id= "PolDutyCode" style= "display:''">
        <Table  class= common>
        <tr>
        <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
        </tr>
        </Table>
    </div>
    <br>
    <!--Ԥ����������Ϣ-->
    <table class= common>
        <TR class= common>
        <TD  class= title> Ԥ�����</TD>
        <TD  class= input> <Input class=common name=standpay onblur="fm.adjpay.value=fm.standpay.value;"></TD>
        <TD  class= title> ����Ϊ</TD>
        <TD  class= input> <Input class=common name=adjpay onblur="checkAdjPay();"></TD>
        <TD  class= title> ������ʶ</TD>
        <TD  class= input> <Input class=codeno name=rgtType CodeData="0|^11|��ͨ����^12|���ϰ���^14|���Ѱ���" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
        </tr>
    </table>
    </span>
    <span id= "spanConclusion2" style= "display: none">
    <table class= common>
        <TR class= common>
      <TD  class= title> ��������ԭ��</TD>
      <TD  class= input> <Input class=codeno name=NoRgtReason ondblclick="return showCodeList('llnorgtreason',[this,NoRgtReasonName],[0,1]);" onkeyup="return showCodeListKey('llnorgtreason',[this,NoRgtReasonName],[0,1]);"><input class=codename name=NoRgtReasonName readonly=true></TD>
      <TD  class= title> </TD>
      <TD  class= input> </TD>
      <TD  class= title> </TD>
      <TD  class= input> </TD>
        </tr>
    </table>
    </span>
    </DIV>
    <hr>
    <table>
    <tr>
    <td><INPUT class=cssButton name="conclusionSave" VALUE="���۱���"  TYPE=button onclick="saveConclusionClick()"></td>
    <td><INPUT class=cssButton name="dutySet" VALUE="ƥ�䲢����"  TYPE=button onclick="showMatchDutyPay();"></td>
    <td><INPUT class=cssButton name="medicalFeeCal" VALUE="��֤������Ϣ"  TYPE=button onclick="showLLMedicalFeeCal();" ></td>
    <td><INPUT class=cssButton name="printPassRgt" VALUE="��ӡ��֤ǩ���嵥"  TYPE=button onclick="prtPassRgt()" ></td>
    <td><INPUT class=cssButton name="printNoRgt" VALUE="��ӡ��������֪ͨ��"  TYPE=button onclick="prtNoRgt()" ></td>
    <td><INPUT class=cssButton name="printDelayRgt" VALUE="��ӡ���䵥֤֪ͨ��"  TYPE=button onclick="prtDelayRgt()" ></td>
    </tr>
    </table>
    <!--*****************************************end************************************************-->
    <hr>
    <table>
    <tr>
    <td><INPUT class=cssButton name="RgtConfirm" VALUE="����ȷ��"  TYPE=button onclick="RegisterConfirm()"></td>
    <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
    </tr>
    </table>
    <%
    //������,������Ϣ��
    %>
    <input type=hidden name=hideOperate value=''>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
    <input type=hidden id="isRegisterExist" name="isRegisterExist"><!--�Ƿ�Ϊ��������-->

    <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
    <input type=hidden id="clmState" name="clmState"><!--����״̬-->
    <!--input type=hidden id="RgtClass" name="RgtClass"--><!--���ո��մ���-->
  
  <!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">
     
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"    name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
