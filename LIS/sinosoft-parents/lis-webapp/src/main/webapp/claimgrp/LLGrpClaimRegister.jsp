<%
//**************************************************************************************************/
//Name��LLGrpClaimRegister.jsp
//Function�����������Ǽ�
//Author��pd
//Date: 2005-10-20
//Desc:
//**************************************************************************************************/
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
  String tIsClaimState =request.getParameter("isClaimState"); //�ж���������״̬��ѯ�Ľڵ�

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
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <SCRIPT src="LLGrpClaimRegister.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
   <%@include file="LLGrpClaimRegisterInit.jsp"%>
</head>
<body onload="initForm()" >
<form action="./LLClaimRegisterSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--������Ϣ-->
  <table>
  <TR class= common>
    <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>������Ϣ</TD>
  </TR>
  </table>

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
	<TD class= title> ����ԭ��</TD>
    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
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
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation ondblclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,1);" onclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1],null,null,null,1);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> ���յص� </TD>
    <TD  class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>
    </TR>

    <TR  class= common>
    <TD  class= title> �����˴��� </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeCode id=AssigneeCode onblur="queryAgent();"></TD>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeName id=AssigneeName ></TD>
	<TD  class= title> �������Ա� </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeSex id=AssigneeSex ondblclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);"  onclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);"onkeyup="return showCodeListKey('sex',[this,AssigneeSexName],[0,1]);"><input class=codename name=AssigneeSexName id=AssigneeSexName readonly=true></TD>
    </TR>

    <TR  class= common>
    <TD  class= title> �����˵绰 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneePhone id=AssigneePhone ></TD>
    <TD  class= title> �����˵�ַ </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeAddr id=AssigneeAddr ></TD>
	<TD  class= title> �������ʱ� </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeZip id=AssigneeZip ></TD>
	<!--     <TD  class= title> ��������</TD>-->
 	<Input class= code type=hidden name=RgtClass value="2"><input class=codename type=hidden name=RgtClassName readonly=true>
    </TR>

    <TR class= common>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeType id=AssigneeType CodeData="0|^0|ҵ��Ա^1|�ͻ�" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])" onclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName id=AssigneeTypeName readonly=true></TD>
  	<TD  class= title> ��Ͻ���� </TD>
    <TD  class= input> <Input class="wid" class= "readonly" readonly name=MngCom id=MngCom></TD>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class="wid" class="readonly" readonly name=Operator id=Operator ></TD>
    </TR>

    <TR class= common>
<!--     <TD  class= title> �¼��� </TD> -->
      <Input class= "readonly" type="hidden" name=AccNo>
      <TD class= title> Ͷ�������� </TD>
      <TD class= input> <Input class="wid" class="readonly" readonly name=Peoples id=Peoples></TD>
      <TD class= title> ����Ԥ�����</TD>
      <TD class= input> <Input  class="wid"class="readonly"  readonly name=Grpstandpay id=Grpstandpay></TD>
      <TD class= title> ��������</TD>
      <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AcceptedDate";">-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AcceptedDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AcceptedDate id="AcceptedDate"><span class="icon"><a onClick="laydate({elem: '#AcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      <font size=1 color='#ff0000'><b>*</b></font></TD>            
    </TR>

  </table>
  </Div>

 

  <!--�ͻ���Ϣ-->
  <table class= common>
  <tr class= common>
    <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
  </tr>
  </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>        
  <table>
  <tr>
    <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
    <td class=titleImg> �ͻ���Ϣ </td>
  </tr>
  </table>

  <Div id= "divLLSubReport" style= "display: ''" class="maxbox">

  
  

  <!--��������Ϣ��-->
  <span id="spanSubReport" >
    <table class= common>

    <TR class= common>
      <!--�����˱���,��������-->
      <TD class= title> �ͻ����� </TD>
      <TD class= input> <Input class="readonly wid" readonly name=customerName id=customerName></TD>
      <TD class= title> ���� </TD>
      <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
      <TD class= title> �Ա� </TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
    </TR>

    <TR class= common>
      <TD class= title> �籣��ʾ</TD>
      <TD class= input> <Input class="readonly wid" readonly name=SocialInsuFlag id=SocialInsuFlag></TD>
      <TD class= title> ��������</TD>
      <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD class= title> �⸶����</TD>
      <TD class= input><Input class="readonly wid" readonly name=claimNum id=claimNum ></TD>
    </TR>

    
    <TR class= common>
      <TD class= title> �������</TD>

      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
      <TD class= title> ��֤��ȫ��ʾ</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady  CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
      <TD class= title></TD>
      <TD class= input></TD>

      <span id="spanText7" style= "display: none">
      <table style= "display: none">
      <TD class= title> ���ս��1</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" ><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
      </table>
      </span>
     </TR>
      <!--TD class= title> ���ս��2</TD>
      <TD class= input> <Input class=codeno name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name readonly=true></TD-->
      
      <!--       <TD class= title> Ԥ�����</TD>
      <TD class= input> <Input class="common"  name=standpay onblur="fm.adjpay.value=fm.standpay.value;"></TD> -->
          
    </table>
  </span>
  
  <table class= common>
    <TR class= common>
      <TD class= title> ���ս������</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onclick="return showCodeList('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"></TD>
      
      <TD class= title> ����ҽԺ����</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=hospital id=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onclick="showHospital(this.name,'TreatAreaName');" onkeyup="showHospital(this.name,'TreatAreaName');"></TD>
      <TD class= title> ����ϸ�ڱ���</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('accidentcode',[this],[0,1],null,null,null,null,'400');"></TD></TR></table>
      <table class= common>
    <TR class= common><TD class= title> ���ս��</TD></TR>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px"> 
      <span id="spanText9" style= "display: ''">
        <textarea name="AccResult2Name" id="AccResult2Name" cols="193" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(4)"></textarea>
      </span>
      </TD>
    </TR>   
  </table>

   <table class= common>
    <TR class= common>
      
      <TD class= title> ����ҽԺ</TD></TR>
      <TR class= common>
      <TD colspan="6" style="padding-left:16px"> 
      <span id="spanText5" style= "display: ''">
        <textarea name="TreatAreaName" cols="193" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(2)"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <span id="spanText6" style= "display: none">
        <textarea disabled name="TreatAreaNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      </TD>
<!--       <TD class= title> �������� </TD>-->
      <input class="readonly" type="hidden" dateFormat="short" name="AccidentDate2" >
    </TR>
    </table>

    <table class= common>
    <TR class= common>
      
      <TD class= title> ����ϸ��</TD></TR>
      <TR class= common>
      <TD colspan="6" style="padding-left:16px"> 
      <span id="spanText5" style= "display: ''">
        <textarea name="accidentDetailName" cols="193" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(1)"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <span id="spanText6" style= "display: none">
        <textarea disabled name="accidentDetailNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      </TD>
    </TR>
    </table>

    

    <table class= common>
    <TR class= common>
      <TD class= title> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD align=left>
      <input type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> ��� </input>
      <input type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> �߲� </input> 
      <input type="checkbox" value="04" name="claimType"> �ش󼲲� </input>
      <input type="checkbox" value="01" name="claimType"> �м������ˡ����ۡ���Ҫ�����г� </input>
<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input> -->
      <input type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> ҽ�� </input>
      <input type="checkbox" value="05" name="claimType" > ���ּ��� </input>
<!--       <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
      </td>
    </TR>
    </table>

    <table class= common>
    <TR  class= common>
      <TD  class= title> �¹����� </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText3" style= "display: ''">
        <textarea name="AccDesc" id="AccDesc" cols="193" rows="4" witdh=25% class="common"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <span id="spanText4" style= "display: none">
        <textarea disabled name="AccDescDisabled" id="AccDescDisabled" cols="199" rows="4" witdh=25% class="common"></textarea>
      </span>
      </TD>
    </tr>
    <TR class= common>
      <TD class= title> ��ע </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText1" style= "display: ''">
        <textarea name="Remark" id="Remark" cols="193" rows="4" witdh=25% class="common"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <span id="spanText2" style= "display: none">
        <textarea disabled name="RemarkDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      </TD>
    </tr>
    </table></div>

<!--   </span>
  </div> -->
<table >
    <tr>
    <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
    <!-- <td><input class=cssButton type="hidden" name=QueryReport value="�¼���ѯ" type=button onclick="queryLLAccident()"></td> -->
    <td>
      <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
      <span id="operateButton23" style= "display: none"><input class=cssButton name=deletebutton  VALUE="ɾ  ��"  TYPE=button onclick="deleteClick()" ></span>
      <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
    </td>
    <!--td><input class=cssButton name=QueryCont1 VALUE="Ͷ������ѯ"  TYPE=button onclick=""></td-->
    <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
    <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
    <td><input class=cssButton name=QueryCont5 VALUE="�����ⰸ����" TYPE=button onclick="getLastCaseInfo()"></td>
    <!-- <td><input class=cssButton type=hidden name=QueryCont4 VALUE="������Ϣ��ѯ" TYPE=button onclick="showCard()"></td>
    <td><input class=cssButton type=hidden name=MedCertForPrt VALUE="��ӡ�˲м���֪ͨ"  TYPE=button onclick="PrintMedCert()"></td>-->
    <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>    
	<td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
    </tr>
  </table>
  <hr class=" line">

<!--   <span id="operateButton3" > -->
  <table>
  <tr>
  <!--td><input class=cssButton name=QueryReport2 value="���Ԥ�����" type=button onclick="operStandPayInfo()"></td-->
  <td><INPUT class=cssButton type=hidden name="DoHangUp" VALUE="��������"  TYPE=button onclick="showLcContSuspend()" ></td>
  <td><INPUT class=cssButton name="CreateNote" VALUE="�������ϻ���"  TYPE=button onclick="showRgtMAffixList()"></td>
  <!--<td><INPUT class=cssButton name="CreateNote" VALUE="����ȫ����������"  TYPE=button onclick="saveRgtMAffixListAll()"></td>
  --><td><INPUT class=cssButton name="CreateNote2" VALUE="������������"  TYPE=button onclick="showRgtAddMAffixList()"></td>
  <td><INPUT class=cssButton name="printPassRgt" VALUE="��ӡ��֤ǩ���嵥"  TYPE=button onclick="prtPassRgt()" ></td>
  <td><INPUT class=cssButton name="printDelayRgt" VALUE="��ӡ���䵥֤֪ͨ��"  TYPE=button onclick="prtDelayRgt()" ></td>
  <td><INPUT class=cssButton name="BeginInq" VALUE="�������"  TYPE=hidden onclick="showBeginInq()"></td>
  <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
  <td><INPUT class=cssButton name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
  <td><INPUT class=cssButton name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
  <td><INPUT class=cssButton type=hidden name="CreateNote2" VALUE="��ӡ��������"  TYPE=button onclick="showPrtAffix()"></td>
  </tr>
  </table>
<!--   </span> -->

  <hr class="line">

  <!--����������Ϣ*********************************Beg********************************************-->
  <table>
  <tr>
    <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLRegisterConclusion);"></td>
    <td class=titleImg> ����������Ϣ </td>
  </tr>
  </table>

  <Div id= "divLLRegisterConclusion" style= "display: ''" class="maxbox1"> 
  <table class= common>
    <TR class= common>
    <TD  class= title> ��������</TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onFocus="showDIV();"><input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title></TD> <TD  class= input></TD>
    <TD  class= title></TD> <TD  class= input></TD>
    
    </tr>
  </table></Div>
<!--   </DIV> -->

  <span id= "spanConclusion2" style= "display: none">
    <table class= common>
    <TR class= common>
      <TD  class= title> ��������ԭ��</TD>
      <!--TD  class= input> <Input class=codeno name=NoRgtReason ondblclick="return showCodeList('llnorgtreason',[this,NoRgtReasonName],[0,1]);" onkeyup="return showCodeListKey('llnorgtreason',[this,NoRgtReasonName],[0,1]);"><input class=codename name=NoRgtReasonName readonly=true></TD-->
      <TD  class= input> <Input class=codeno name=NoRgtReason ondblclick="return showCodeList('llnorgtreason',[this,NoRgtReasonName],[0,1]);" onkeyup="return showCodeListKey('llnorgtreason',[this,NoRgtReasonName],[0,1]);"><input class=codename name=NoRgtReasonName readonly=true></TD>
    </tr>
    </table>
  </span>
  
  <span id= "spanConclusion3" style= "display: none">
    <table class= common>
    <TR class= common>
      <TD  class= title> �ӳ�����ԭ��</TD>
    </TR>
    <TR class= common>
         <TD class= input> <textarea name="DeferRgtReason" cols="100" rows="2" witdh=25% class="common"></textarea></TD>   
    </TR>
    </table>
  </span>  
  
  <span id= "spanConclusion4" style= "display: none">
    <table class= common>
    <TR class= common>
      <TD  class= title> �ӳ�������ע</TD>
    </TR>
    <TR class= common>
         <TD class= input> <textarea disabled name="DeferRgtRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>   
    </TR>
    </table>
  </span>  

    <table >
    <td><INPUT class=cssButton name="conclusionSave" VALUE="���۱���"  TYPE=button onclick="saveConclusionClick()"></td>
    </table>
    <td><INPUT class=cssButton type=hidden name="printNoRgt" VALUE="��ӡ��������֪ͨ��"  TYPE=button onclick="prtNoRgt()" ></td>

  <hr class="line">
    
   <span id= "spanLLClaimAudit" style= "display: none">
    <!--��˽�����Ϣ*********************************Beg********************************************-->
    <table>
    <tr>
    <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimAudit);"></td>
    <td class=titleImg> ��˽�����Ϣ </td>
    </tr>
    </table>
    <Div id= "divLLClaimAudit" style= "display: ">
      <table class= common>      
       <TR class= common>
         <TD class= title>������</TD>
       </tr>
       <TR class= common>
         <TD class= input> <textarea disabled name="AuditIdea" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
       </tr>      
      </table>  
    </Div>
    <hr>
   </span>
  
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
      <td class= titleImg>�������μ�����Ϣ</td>
     </tr>
    </table>

    <Div  id= "PolDutyCode" style= "display:''">
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

<!--     <br> -->
    <!--Ԥ����������Ϣ-->
    <table class= common>
    <TR class= common>
<!--       <TD  class= title> ������</TD> -->
 <Input class=common type=hidden name=standpay onblur="fm.adjpay.value=fm.standpay.value;"> 
<!--       <TD  class= title> ����Ϊ</TD> -->
 <Input class=common type=hidden name=adjpay onblur="checkAdjPay();">
<!--       <TD  class= title> ������ʶ</TD> -->
<Input class=codeno type=hidden name=rgtType CodeData="0|^11|��ͨ����^12|���ϰ���^14|���Ѱ���" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeTypeName],[0,1])" value = "11"><Input class=codename type=hidden name=rgtTypeTypeName readonly=true>
    </tr>
    </table>

  </span>
  <!--=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������Ϊ�������޸�
    �� �� �ˣ�P.D
    �޸����ڣ�2005.12.27
    =========================================================================
  -->
  <div id= "divBaseUnit5" style= "display: none">
    <table>
    <tr>
     <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
     <td class= titleImg>�����⸶����</td>
    </tr>
    </table>
    <Div  id= "divBaseUnit4" style= "display:''">
   <table class= common>
     <TR class= common>
    <TD  class= title>�⸶����</TD>
    <TD  class= input><Input class=codeno name=GiveType ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName readonly=true></font>
    <TD  class= title></TD>
    <TD  class= input><Input class=readonly readonly ></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
     </tr>
   </table>
   <Div  id= "divGiveTypeUnit1" style= "display: none">
     <table class= common>
    <TR class= common>
      <td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency ondblclick="return showCodeList('currency',[this,CurrencyName],[0,1]);" onkeyup="return showCodeListKey('currency',[this,CurrencyName],[0,1]);"><input class=codename name=CurrencyName readonly=true></td>
      <TD  class= title>�������</TD>
<!--       <TD  class= input><Input class=common name=RealPay onblur="checkAdjMoney();"></TD> -->
      <TD  class= input><Input class=common name=RealPay onblur="checkCurrency();"></TD>
      <TD  class= title>����ԭ��</TD>
      <TD  class= input><Input class=codeno name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1],null,null,null,null,220);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName readonly=true></font>
      <TD  class= title></TD>
      <TD  class= input></TD>
    </tr>
     </table>
     <Div  id= "divGiveTypeUnit3" style= "display: none">
      <table class= common>
      <TD  class= title>��������״̬</TD>
      <TD  class= input><Input class=codeno name=polstate ondblclick="return showCodeList('newpolstate',[this,polstateName],[0,1],null,null,null,null,120);" onkeyup="return showCodeListKey('newpolstate',[this,polstateName],[0,1]);"><input class=codename name=polstateName readonly=true></font>
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
      <TD class= input> <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
    </tr>
      </table>
   </div>
   <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
     <table class= common>
    <TR class= common>
      <TD  class= title>�ܸ�ԭ��</TD>
      <TD  class= input><Input class=codeno name=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class=codename name=GiveReasonName readonly=true></td>
      <TD  class= title>�ܸ�����</TD>
      <TD  class= input><Input class=common name=GiveReasonDesc></td>
      <TD  class= title></TD>
      <TD  class= input></TD>       
    </tr>
     </table>
     <table class= common>
    <TR class= common >
      <TD class= title> ���ⱸע </TD>
    </tr>
    <TR class= common>
      <TD class= input> <textarea name="SpecialRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
    </tr>
     </table>
   </div>
   <table>
      <tr>
     <td><INPUT class=cssButton name="addUpdate" VALUE=" ����޸� "  TYPE=button onclick="AddUpdate()" ></td>
     <td><INPUT class=cssButton name="saveUpdate" VALUE=" �����޸Ľ�� "  TYPE=button onclick="SaveUpdate();" ></td>
      </tr>
   </table>
    </div>
  </div>
  <!--=========================================================================
    �޸�״̬������
    �޸�ԭ������Ϊ�������޸�
    �� �� �ˣ�P��D
    �޸����ڣ�2005.12.27
    =========================================================================
  -->
 

  <table>
  <tr>
    <td><input class=cssButton VALUE="�˻��ʽ����" TYPE=button name="LCInsureAcc" onclick="ctrllcinsureacc();"></td>
    <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="ҽ�Ƶ�֤¼��"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
		<td><input class=cssButton VALUE="�������ο�����Ϣ" TYPE=button onclick="ctrlclaimduty();"></td>		
    <td><INPUT class=cssButton name="dutySet" VALUE="ƥ�䲢����"  TYPE=button onclick="showMatchDutyPay();"></td>
    <!--td><INPUT class=cssButton name="conclusionSave1" VALUE="�����޸�"  TYPE=button onclick="saveConclusionClick1()"></td-->
    <!--td><INPUT class=cssButton name="ScanInfo" VALUE="ɨ�����Ϣ"  TYPE=button onclick="showScanInfo();" ></td-->
    <!--<td><INPUT class=cssButton name="AddAffix" VALUE="�����������"  TYPE=button onclick="AddAffixClick();" ></td>
    <td><INPUT class=cssButton name="LoadAffix" VALUE="����������"  TYPE=button onclick="LoadAffixClick();" ></td>
    -->    
    <td><INPUT class=cssButton name="FFF" type=hidden VALUE="������ѯ"  TYPE=button onclick="LLQueryUWMDetailClick();" ></td> 
    <td><INPUT class=cssButton name="medicalFeeCal" type=hidden VALUE="��֤������Ϣ"  TYPE=button onclick="showLLMedicalFeeCal();" ></td>
  </tr>
  </table>
  <!--*****************************************end************************************************-->

  <hr class="line">

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
  <Input type=hidden id="AllManageCom" name="AllManageCom"><!--��½��վ-->
  <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
  <Input type=hidden id="ManageCom" name="ManageCom"><!--��½��վǰ��������-->
  <input type=hidden id="tOperator" name="tOperator"><!--��½�û�����-->
  <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
  <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
  <input type=hidden id="clmState" name="clmState"><!--����״̬-->
  <!--input type=hidden id="RgtClass" name="RgtClass"--><!--���ո��մ���-->
  <input type=hidden id="isClaimState" name= "isClaimState"><!--�ж���������״̬��ѯ�Ľڵ�-->
  
  <!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
  <input type=hidden id="PrtSeq" name="PrtSeq">
   
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <input type=hidden id="PolNo" name= "PolNo">
  <input type=hidden id="tSQL" name= "tSQL">
  <input type=hidden id="Operate" name="Operate">
  <input type=hidden id="RgtState" name="RgtState">
  <input type=hidden id="IsVip" name="IsVip">
  <input type=hidden class=common  name="AccRiskCode" > 
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
