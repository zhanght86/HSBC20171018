<%
//**************************************************************************************************
//Name��LLClaimConfirmInput.jsp
//Function����������
//Author��zl
//Date: 2005-6-20 14:00
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

  String tClmNo = request.getParameter("claimNo");  //�ⰸ��
  String tUserCode = tG.Operator;
  String tMissionID = request.getParameter("MissionID");  //����������ID
  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
  String tBudgetFlag = request.getParameter("BudgetFlag");
  String tAuditPopedom = request.getParameter("AuditPopedom");
  String tAuditer = request.getParameter("Auditer");
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
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <SCRIPT src="LLClaimConfirm.js"></SCRIPT>
   <%@include file="LLClaimConfirmInit.jsp"%>
</head>
<body  onload="initForm();" >
<form action="./LLClaimConfirmSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--������Ϣ-->
  <table>
  <TR>
    <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>������Ϣ</TD>
  </TR>
  </table>
  <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
  <table  class= common>
     <TR  class= common>
<!--     <TD  class= title> �¼��� </TD> -->
    <Input class= "readonly" type='hidden' readonly name=AccNo>
     <TD  class= title> ������ </TD>
    <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName id=RptorName ></TD>
    <TD  class= title> ��������</TD>
    <TD  class= input> <Input class="wid" class= readonly readonly name=RgtClassName id=RgtClassName ></TD>
    </TR>
    <TR  class= common>
    <TD  class= title> �����˵绰 </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone id=RptorPhone ></TD>
    <TD  class= title> ������ͨѶ��ַ </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorAddress id=RptorAddress ></TD>
    <TD  class= title> ���������¹��˹�ϵ </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno disabled name=Relation id=Relation ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
    </TR>
    <TR  class= common>
     <TD  class= title> ���յص� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly name=AccidentSite id=AccidentSite ></TD>
     <TD  class= title> ������������ </TD>
    <TD  class= input> <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
    <TD  class= title> ��Ͻ���� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
    </TR>
    <TR  class= common>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeType id=AssigneeType ></TD>
    <TD  class= title> �����˴��� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeCode id=AssigneeCode ></TD>
    </TR>
    <TR  class= common>
    <TD  class= title> ���������� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeName id=AssigneeName ></TD>
    <TD  class= title> �������Ա� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeSex id=AssigneeSex ></TD>
    <TD  class= title> �����˵绰 </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone  id=AssigneePhone ></TD>
    </TR>
    <TR  class= common>
    <TD  class= title> �����˵�ַ </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
    <TD  class= title> �������ʱ� </TD>
    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip id=AssigneeZip ></TD>
      <TD class= title> ����ԭ��</TD>
      <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno disabled name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true></TD>
    </TR>
    <TR  class= common>
    <TD class= title>  ��������</TD>
    <TD class= input>  <input class="readonly wid" readonly  name="AcceptedDate" id="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font></TD>
    
    </TR> 
 </table>
  </Div>
  
  <!--��������Ϣ-->

  <Div id= "divSelfLLClaimSimpleGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSubReportGrid" ></span></td>
            </tr>
        </table>
        <!--<table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();"></td>
            </tr>
        </table> -->       
   </div>  
  
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
      <TD class= title> �ͻ����� </TD>
      <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
      <TD class= title> �ͻ��Ա� </TD>
      <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
    </TR>
    <TR class= common>
      <TD class= title> �籣��־</TD>
      <TD class= input> <Input class="readonly wid" readonly name=SocialInsuFlag id=SocialInsuFlag></TD>
      <TD class= title> �¹�����</TD>
      <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate" id="AccidentDate" ></TD>
	  <TD class= title>��������</TD>
	  <TD class= input><input class="readonly wid" readonly name="AccidentDate2" id="AccidentDate2" ></TD></tr>
<!--       <TD class= title> ����ԭ��</TD>
      <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno disabled name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true></font></TD> -->
    </TR>
<!--     <TR class= common>
      <TD class= title> ����ҽԺ</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
      <TD class= title> �������� </TD>
      <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate2" id="AccidentDate2" ></TD>
      <TD class= title> ����ϸ��</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno disabled name=accidentDetail id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
    </TR> -->
    <TR class= common>
      <TD class= title> �������</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno disabled name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
	  <TD class= title> ��֤��ȫ��ʾ</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"  onclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
      <TD class= title> ��Ҫ��Ϣ�޸ı�ʾ</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name=IsModify id=IsModify disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"  onclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])" onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName id=IsModifyName readonly=true></TD>
    </TR>
  <TR class= common>
    <span id="spanText7" style= "display: none">
      <table>
      <TD style= "display: none" class= title> ���ս��1</TD>
      <TD style= "display: none" class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" ><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
      
      </table>
      </span>
    <!--TD class= title> ���ս��2</TD>
    <TD class= input> <Input class=codeno disabled name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name readonly=true></TD-->
     </tr>
   </table>

   <table class= common>
    <TR class= common>
      <TD class= title> ���ս������</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  class=code name=AccResult2 id=AccResult2 disabled ondblclick="return showCodeList('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onclick="return showCodeList('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onkeyup="return showCodeListKey('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"></TD>
      <TD class= title> ����ϸ�ڱ���</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  class=code name=accidentDetail id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this],[0,1],null,null,null,null,'400');"></TD>
      <TD class= title> ����ҽԺ����</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  class=code name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"></TD>
      </tr></table>
       <table class= common>
    <TR class= common>
      <TD class= title> ���ս��</TD></tr><TR class= common>
      <TD colspan="6" style="padding-left:16px"> 
      <span id="spanText9" style= "display: ''">
        <textarea name="AccResult2Name" disabled cols="197" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(4) "></textarea>
      </span>
      </TD>
    </TR>   
    </table>    

    

    <table class= common>
    <TR class= common>
      
      <TD class= title> ����ϸ��</TD></tr><TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText5" style= "display: ''">
        <textarea name="accidentDetailName" cols="197" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(1)"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <!--<span id="spanText6" style= "display: 'none'">
        <textarea disabled name="accidentDetailNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </TR>
    </table>

    <table class= common>
    <TR class= common>
      
      <TD class= title> ����ҽԺ</TD></tr><TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText5" style= "display: ''">
        <textarea name="TreatAreaName" cols="197" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(2)"></textarea>
      </span>
      <!--���ر���Ϊ��ʾ������-->
      <!--<span id="spanText6" style= "display: 'none'">
        <textarea disabled name="TreatAreaNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
<!--       <TD class= title> �������� </TD>-->
      <input class="readonly" type="hidden" dateFormat="short" name="AccidentDate3" >
    </TR>
    </table>

    <table class= common>
    <TR class= common>
      <TD class= title> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD align=left>
      <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">���</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">�߲�</span> </input> 
      <input style="vertical-align:middle" type="checkbox" value="04" name="claimType"> <span style="vertical-align:middle">�ش󼲲�</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="01" name="claimType"> <span style="vertical-align:middle">�м������ˡ����ۡ���Ҫ�����г�</span> </input>
<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input> -->
      <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"><span style="vertical-align:middle"> ҽ��</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" > <span style="vertical-align:middle">���ּ���</span> </input>
      <!-- <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
      </td>
    </TR>
    </table>
    <table class= common>
    <TR  class= common>
      <TD  class= title> �¹����� </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px"> <textarea name="AccDesc" cols="197" rows="4" witdh=25% class="common" readonly ></textarea></TD>
    </tr>
    <TR class= common>
      <TD class= title> ��ע </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px"> <textarea name="Remark" cols="197" rows="4" witdh=25% class="common" readonly ></textarea></TD>
    </tr>
    </table>
  </span>
  </div>
  <table>
    <tr>
    <!--
    <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
    <td><input class=cssButton name=QueryReport value=" �¼���ѯ " type=button onclick="queryLLAccident()"></td>
    <td>
      <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
      <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
    </td>
     -->
    <td><input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onclick="showInsuredLCPol()"></td>
    <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>  
  <!--td><input class=cssButton name=QueryCont5 VALUE="��Ҫ��Ϣ�޸�" TYPE=button onclick="editImpInfo()"></td-->
    <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
    <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
   <td><INPUT class=cssButton name="AddAffix" type=hidden VALUE="�����������"  TYPE=button onclick="AddAffixClick();" ></td>
   <td><INPUT class=cssButton name="LoadAffix" type=hidden VALUE="����������"  TYPE=button onclick="LoadAffixClick();" ></td>           
    </tr>
  </table>
  <!--����������Ϣ-->
  <table>
   <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
    <td class= titleImg>����������Ϣ</td>
   </tr>
  </table>
  <Div  id= "divBaseUnit6" style= "display:''" class="maxbox1">
  <table class= common>
    <TR class= common>
    <TD  class= title> ��������</TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true></TD>
    <TD  class= title> ������ʶ</TD>
    <!--TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=rgtType id=rgtType CodeData="0|3^11|��ͨ����^12|���ϰ���^14|���Ѱ���" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeName],[0,1])"  onclick="return showCodeListEx('rgtType', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeName],[0,1])"><Input class=codename name=rgtTypeName readonly=true></TD-->
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=rgtType id=rgtType ondblclick="return showCodeList('llrgttype', [this,rgtTypeName],[0,1])"  onclick="return showCodeList('llrgttype', [this,rgtTypeName],[0,1])" onkeyup="return showCodeListKey('llrgttype', [this,rgtTypeName],[0,1])"><Input class=codename name=rgtTypeName id=rgtTypeName readonly=true></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
     </tr>
  </table>
  </div>
  
  <span id= "spanConclusion1" style= "display: ">
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
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit1);"></td>
    <td class= titleImg>�������ͼ�����Ϣ</td>
     </tr>
  </table>
  <Div  id= "divBaseUnit1" style= "display:''">
     <Table  class= common>
      <tr><td text-align: left colSpan=1>
       <span id="spanDutyKindGrid"></span>
      </td></tr>
     </Table>
  </div>
  <!--���ձ����������ͼ�����Ϣ-->
  <table>
     <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit2);"></td>
    <td class= titleImg>����������Ϣ</td>
     </tr>
  </table>
  <Div  id= "divBaseUnit2" style= "display:''">
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
  </span>
  <div id= "divBaseUnit5" style= "display: none">
  <table>
     <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
    <td class= titleImg>�����⸶����</td>
     </tr>
  </table>
  <Div  id= "divBaseUnit4" style= "display:''" class="maxbox1">
    <table class= common>
    <TR class= common>
      <TD  class= title>�⸶����</TD>
      <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=GiveType id=GiveType ondblclick="return showCodeList('llpayconclusion2',[this,GiveTypeName],[0,1]);" onclick="return showCodeList('llpayconclusion2',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion2',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName id=GiveTypeName readonly=true>
      <TD  class= title></TD>
      <TD  class= input><Input class="wid" class=readonly readonly ></TD>
       <TD  class= title></TD>
      <TD  class= input></TD>
      
    </tr>
    </table>
    <Div  id= "divGiveTypeUnit1" style= "display: 'none'">
    <table class= common>
      <TR class= common>
     <TD  class= title>�������</TD>
      <TD  class= input><Input class="wid" class=readonly readonly name=RealPay id=RealPay></TD>
      <TD  class= title>����ԭ��</TD>
      <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AdjReason id=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName id=AdjReasonName readonly=true>
      <TD  class= title></TD>
      <TD  class= input></TD>
      
      </tr>
    </table>
    <table class= common>
      <TR class= common>
      <TD class= title> ������ע </TD>
      </tr>
      <TR class= common>
      <TD colspan="6" style="padding-left:16px"> <textarea name="AdjRemark" id="AdjRemark" cols="199" rows="4" witdh=25% class="common" readonly ></textarea></TD>
      </tr>
     </table>
    </div>
    <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
    <table class= common>
      <TR class= common>
      <TD  class= title>�ܸ�ԭ��</TD>
      <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=GiveReason id=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class=codename name=GiveReasonName id=GiveReasonName readonly=true></td>
      <TD  class= title>�ܸ�����</TD>
      <TD  class= input><Input class="wid" class=readonly readonly name=GiveReasonDesc id=GiveReasonDesc></td>
      <TD  class= title></TD>
      <TD  class= input></TD>
      </tr>
    </table>
    <table class= common>
      <TR class= common >
      <TD class= title> ���ⱸע </TD>
      </tr>
      <TR class= common>
      <TD colspan="6" style="padding-left:16px"> <textarea name="SpecialRemark" id="SpecialRemark" cols="199" rows="4" witdh=25% class="common" readonly ></textarea></TD>
      </tr>
    </table>
    </div>
  </div>
  </div>
  
  <table>
  <tr>
  <td><INPUT class=cssButton name="MedicalFeeInp" VALUE=" ҽ�Ƶ�֤�鿴 "  TYPE=button onclick="showLLMedicalFeeInp();"></td>
    <td><INPUT class=cssButton type=hidden name="ViewReport" VALUE=" �鿴�ʱ� "  TYPE=button onclick="showQuerySubmit()"></td>
    <td><INPUT class=cssButton name="ViewInvest" VALUE=" �鿴���� "  TYPE=button onclick="showQueryInq()"></td>
<!--     <td><INPUT class=cssButton type=hidden name="" VALUE=" ���˽��� "  TYPE=button onclick="SecondUWInput()"></td>
    <td><INPUT class=cssButton type=hidden name="CreateNote" VALUE=" �����ѯ "  TYPE=button onclick="showExempt()"></td>
    <td><INPUT class=cssButton type=hidden name="" VALUE=" �������� "  TYPE=button onclick="showPolDeal()"></td>
    <td><INPUT class=cssButton name="medicalFeeCal" VALUE=" ���ò鿴 "  TYPE=button onclick="showLLMedicalFeeCal();"></td>
    <td><INPUT class=cssButton type=hidden name="" VALUE=" ��ͬ���� "  TYPE=button onclick="showContDeal()"></td> -->
    <td><INPUT class=cssButton name="BeneficiaryAssign" VALUE="�����˷���"  TYPE=button onclick="showBnf()"></td>    
    <td><INPUT class=cssButton name="AccidentDesc" VALUE=" ��ע��Ϣ "  TYPE=button onclick="showClaimDesc()"></td>
  </tr>
  </table>
  <table>
  <tr>
    <td><INPUT class=cssButton type=hidden name="" VALUE="�����˷���"  TYPE=button onclick="showBnf()"></td>
    <!--td><INPUT class=cssButton name="" VALUE=" �ʻ����� "  TYPE=button onclick="showLCInsureAccont()"></td-->
  </tr>
  </table>
  <!--��˹�����Ϣ-->
  
  <table>
  <TR>
   <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
   <TD class= titleImg>��˹���</TD>
   </TR>
  </table>
  <Div id= "divLLAudit" style= "display: ''" class="maxbox">
  <table class= common>
    <TR class= common>
    <TD class= title>������</TD>
    </tr>
    <TR class= common>
    <TD colspan="6" style="padding-left:16px"> <textarea name="AuditIdea" cols="197" rows="4" witdh=25% class="common" readonly ></textarea></TD>
    </tr>
   </table>
   <table class= common>
    <TR class= common>
    <TD  class= title>��˽���</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AuditConclusion id=AuditConclusion ondblclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class=codename name=AuditConclusionName id=AuditConclusionName readonly=true></TD>
    <TD  class= title>���ⱸע</TD>
    <TD  class= input><Input class="wid" class=common disabled name=SpecialRemark1 id=SpecialRemark1></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
    </tr>
  </table>
  <Div id= "divLLAudit2" style= "display: 'none'">
     <table class= common>
    <TR class= common>
      <TD  class= title>�ܸ�ԭ��</TD>
      <TD  class= input><Input class=codeno disabled name=ProtestReason ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class=codename name=ProtestReasonName readonly=true></TD>
      <TD  class= title>�ܸ�����</TD>
      <TD  class= input><Input class=common disabled name=ProtestReasonDesc></TD>
      <TD  class= title></TD>
      <TD  class= input></TD>
    </tr>
    </table>
  </div>
  </Div>
  
  <!--=========================================================================
  �޸�״̬������
  �޸�ԭ������Ϊ�����Ϣ��
  �� �� �ˣ�����
  �޸����ڣ�2005.05.13
  =========================================================================
  -->
  <!--����������Ϣ-->
  <table>
  <tr>
    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowConfirmDetail);"></td>
    <td class= titleImg> ��������</td>
  </tr>
  </table>
  <Div  id= "divShowConfirmDetail" style="display: '';" class="maxbox">
  <table class= common>
    <TR class= common>
    <TD  class= title> �������</TD>
    </tr>
    <TR class= common>
    <TD  colspan="6" style="padding-left:16px"> <textarea name="RemarkSP" cols="197%" rows="4" witdh=25% class="common"></textarea></TD>
    </tr>
  </table>
  <table class= common>
     <TR class= common>
    <TD  class= title>��������</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="DecisionSP" id="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);"  onClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" onfocus= "choiseConfirmConclusionType();"><input class=codename name=DecisionSPName id=DecisionSPName readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title></TD>
    <TD  class= input><Input class="wid" class=readonly readonly ></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
    </TR>
  </table>
  <Div id= "divLLConfirm2" style= "display: none">
     <table class= common>
    <TR class= common>
      <TD  class= title>��ͨ��ԭ��</TD>
      <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ExamNoPassReason id=ExamNoPassReason ondblclick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1],null,null,null,null,350);" onclick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1],null,null,null,null,350);" onkeyup="return showCodeListKey('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);"><input class=codename name=ExamNoPassReasonName id=ExamNoPassReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD  class= title>��ͨ������</TD>
      <TD  class= input><Input class="wid" class=common name=ExamNoPassDesc id=ExamNoPassDesc><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD  class= title></TD>
      <TD  class= input></TD>
    </tr>
    </table>
  </div>
  </div>
  
  <table>
  <tr>
    <td><INPUT class=cssButton name="ConfirmSave" VALUE="����ȷ��"  TYPE=button onclick="ConfirmSaveClick()"></td>
    <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
  </tr>
  </table>
  <%
  //������,������Ϣ��
  %>
  <Input type=hidden name=customerNo >
  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->

  <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
  <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
  <input type=hidden id="clmState" name="clmState"><!--����״̬-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
  <input type=hidden id="RgtClass" name="RgtClass"><!--���ո��մ���-->
  <input type=hidden id="RgtState" name="RgtState"><!--��������-->
  <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--�Ƿ�Ԥ��-->
  <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--���Ȩ��-->
  <input type=hidden id="Auditer" name="Auditer"><!--�����-->
  <input type=hidden id="UserCode" name="UserCode"><!--�����û�-->
  <input type=hidden id="ManageComAll" name="ManageComAll"><!--���˽᰸����-->
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
