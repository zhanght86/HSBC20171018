<%
//**************************************************************************************************
//Name��LLClaimQueryInput.jsp
//Function���ⰸ��ѯ
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
    String tPhase = request.getParameter("phase");
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
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLClaimQuery.js"></SCRIPT>
   <%@include file="LLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
<form action="./LLClaimQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!--������Ϣ-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''" class=" maxbox">
        <table  class= common>
             <TR  class= common>
          <TD  class= title> �¼��� </TD>
                <TD  class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
               <TD  class= title> �ⰸ�� </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
                <TD  class= title> ���������� </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
                <TD  class= title> �����˵绰 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone id=RptorPhone ></TD>
              <TD  class= title> ������ͨѶ��ַ </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorAddress id=RptorAddress ></TD>
              <TD  class= title> ���������¹��˹�ϵ </TD>
              <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=Relation id=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class="wid" class=readonly readonly name=RelationName id=RelationName readonly=true></TD>
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
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone id=AssigneePhone ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> �����˵�ַ </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
              <TD  class= title> �������ʱ� </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip id=AssigneeZip ></TD>
              <TD  class= title> ��������</TD>
                <TD  class= input> <Input class="wid" class= readonly readonly name=RgtClassName id=RgtClassName ></TD>
            </TR>
        </table>
    </Div>
 
    <!--��������Ϣ-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
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
                    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
					<TD class=title>�籣��־</TD>
					<TD class=input><Input class="readonly wid" readonly name=SocialInsuFlagName id=SocialInsuFlagName></TD>		
                    <TD class= title> �����¹ʷ�������</TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate" id="AccidentDate" ></font></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input><Input type=hidden name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ReasonName id=ReasonName readonly=true></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input type=hidden name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class="wid" class=readonly readonly name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> �������� </TD>
                    <TD class= input>  <input class="wid" class= "readonly" readonly name="AccidentDate2" id="AccidentDate2" ></TD>
                    <TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input type=hidden name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class="wid" class=readonly readonly name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input type=hidden name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class="wid" class=readonly readonly name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class="wid" class=readonly readonly name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class="wid" class=readonly readonly name=AccResult2Name id=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʾ</TD>
                    <TD class= input> <Input type=hidden name=IsAllReady disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class="wid" class=readonly readonly name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
                    <TD class= title> ��Ҫ��Ϣ�޸ı�ʾ</TD>
                    <TD class= input> <Input type=hidden name=IsModify disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class="wid" class=readonly readonly name=IsModifyName id=IsModifyName readonly=true></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
		<TD align=left>
			<input style="vertical-align:middle" disabled type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">���</span> </input> 
			<input style="vertical-align:middle" disabled type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">�߲�</span> </input> 
			<input style="vertical-align:middle" disabled type="checkbox" value="04" name="claimType"> <span style="vertical-align:middle">�ش󼲲�</span> </input>
			<input style="vertical-align:middle" disabled type="checkbox" value="01" name="claimType">	<span style="vertical-align:middle">�м������ˡ����ۡ���Ҫ�����г�</span> </input> 
			<!--<input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input> -->
			<input style="vertical-align:middle" disabled type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">ҽ��</span> </input> 
			<input style="vertical-align:middle" disabled type="checkbox" value="05" name="claimType"> <span style="vertical-align:middle">���ּ���</span> </input> 
			<!-- <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->
		</td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> <textarea name="AccDesc" id="AccDesc" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
        
    </div>
    
    <table>
            <tr>
                <td><input class=cssButton name=QueryCont1 VALUE=" ������ѯ "  TYPE=button onclick="showReport()"></td>
                <td><input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton type = "hidden" name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
                <td><input class=cssButton type = "hidden" name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton type = "hidden" name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>                
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
              <TD  class= input> <Input type=hidden name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class="wid" class=readonly readonly name=RgtConclusionName id=RgtConclusionName readonly=true></TD>
                <TD  class= title> ������ʶ</TD>
                <TD  class= input> <Input type=hidden name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeName],[0,1])"><Input class="wid" class=readonly readonly name=rgtTypeName id=rgtTypeName readonly=true></TD>
                <TD  class= title> �ⰸ״̬</TD>
                <TD  class= input> <Input type=hidden name=ClmState ondblclick="return showCodeListEx('llclaimstate', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('llclaimstate', [this,ClmStateName],[0,1])"><Input class="wid" class=readonly readonly name=ClmStateName id=ClmStateName readonly=true></TD>
           </tr>
      </table>
    </div>
    <Div id= "divPart2" style= "display:'none'">
        
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
            <Div  id= "divBaseUnit4" style= "display:''">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>�⸶����</TD>
                        <TD  class= input><Input type=hidden name=GiveType ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=" wid" class=readonly readonly name=GiveTypeName id=GiveTypeName readonly=true></font>
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
                            <TD  class= input><Input class=" wid" class=readonly readonly name=RealPay id=RealPay></TD>
                            <TD  class= title>����ԭ��</TD>
                            <TD  class= input><Input type=hidden name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=" wid" class=readonly readonly name=AdjReasonName id=AdjReasonName readonly=true></font>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>
                    </table>
                    <table class= common>
                        <TR class= common>
                            <TD class= title> ������ע </TD>
                        </tr>
                        <TR class= common>
                            <TD colspan="6" style="padding-left:16px"> <textarea name="AdjRemark" id="AdjRemark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                        </tr>
                     </table>
                </div>
                <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
                    <table class= common>
                        <TR class= common>
                            <TD  class= title>�ܸ�ԭ��</TD>
                            <TD  class= input><Input type=hidden name=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class="wid" class=readonly readonly name=GiveReasonName id=GiveReasonName readonly=true></td>
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
                            <TD colspan="6" style="padding-left:16px"> <textarea name="SpecialRemark" id="SpecialRemark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <table>
            <tr>
                <td><INPUT class=cssButton name="AccidentDesc" VALUE=" ��ע��Ϣ "  TYPE=button onclick="showClaimDesc()"></td>
                <td><INPUT class=cssButton name="ViewReport" VALUE=" �鿴�ʱ� "  TYPE=button onclick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE=" �鿴���� "  TYPE=button onclick="showQueryInq()"></td>
                <td><INPUT class=cssButton type = "hidden" name="" VALUE=" ���˽��� "  TYPE=button onclick="SecondUWInput()"></td>
                <td><INPUT class=cssButton name="" VALUE="����������ѯ"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>
       		    <td><INPUT class=cssButton name="MedicalFeeInp" VALUE=" ҽ�Ƶ�֤ "  TYPE=button onclick="showLLMedicalFeeInp();"></td>
				<td><INPUT class=cssButton name="CreateNote" VALUE="�����˷���"  TYPE=button onclick="showBnf()"></td>      			
                <td><INPUT class=cssButton type = "hidden" name="medicalFeeCal" VALUE=" ���ò鿴 "  TYPE=button onclick="showLLMedicalFeeCal();"></td>
                <td><INPUT class=cssButton type = "hidden" name="CreateNote" VALUE=" �����ѯ "  TYPE=button onclick="showExempt()"></td>
                <td><INPUT class=cssButton type = "hidden" name="" VALUE=" �������� "  TYPE=button onclick="showPolDeal()"></td>
            </tr>
    </table>
    <table>
            <TR>
                <td><INPUT class=cssButton type = "hidden" name="" VALUE=" ��ͬ���� "  TYPE=button onclick="showContDeal()"></td>
                <!--td><INPUT class=cssButton name="" VALUE=" �ʻ����� "  TYPE=button onclick="showLCInsureAccont()"></td-->
            </tr>
        </table>
        <!--��˹�����Ϣ-->
        <Span id="spanAudit" style="display: 'none'">
            
            <table>
              <TR>
               <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
               <TD class= titleImg>��˹���</TD>
             </TR>
            </table>
            <Div id= "divLLAudit" style= "display: ''" class="maxbox1">
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>��˽���</TD>
                        <TD  class= input><Input type=hidden name=AuditConclusion ondblclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class="wid" class=readonly readonly name=AuditConclusionName id=AuditConclusionName readonly=true></TD>
                        <TD  class= title>���ⱸע</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name=SpecialRemark1 id=SpecialRemark1></TD>
                        <TD  class= title>�ܸ�ԭ��</TD>
                            <TD  class= input><Input type=hidden name=ProtestReason ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ProtestReasonName id=ProtestReasonName readonly=true></TD>
                        
                    </tr>
                </table>
                <Div id= "divLLAudit2" style= "display: 'none'">
                     <table class= common>
                        <TR class= common>
                            
                            <TD  class= title>�ܸ�����</TD>
                            <TD  class= input><Input class="wid" class=common disabled name=ProtestReasonDesc id=ProtestReasonDesc></TD>
                            <TD  class= title></TD>
                            <TD  class= input></TD><TD  class= title></TD>
                        <TD  class= input></TD>
                        </tr>
                    </table>
                </div>
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>�����</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="AuditPer" id="AuditPer" ></TD>
                        <TD  class= title>�������</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="AuditDate" id="AuditDate" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title>������</TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="AuditIdea" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                 </table>
            </Div>
        </span>
        <!--=========================================================================
            �޸�״̬������
            �޸�ԭ������Ϊ�����Ϣ��
            �� �� �ˣ�����
            �޸����ڣ�2005.05.13
            =========================================================================
        -->
          <!--����������Ϣ-->
        <Span id= "spanConfirm" style="display: 'none';">
            
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowQueryDetail);"></td>
                    <td class= titleImg> ��������</td>
                </tr>
            </table>
            <Div  id= "divShowQueryDetail" style="display: '';" class="maxbox1">
                <table class= common>
                     <TR class= common>
                      <TD  class= title>��������</TD>
                        <TD  class= input><Input type=hidden name="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" onfocus= "choiseQueryConclusionType();"><input class="wid" class=readonly readonly name=DecisionSPName id=DecisionSPName readonly></TD>
                        <TD  class= title>������</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ExamPer" id="ExamPer" ></TD>
                        <TD  class= title>��������</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ExamDate" id="ExamDate" ></TD>
                  </TR>
                </table>
                <Div id= "divLLQuery2" style= "display: 'none'">
                     <table class= common>
                        <TR class= common>
                            <TD  class= title>��ͨ��ԭ��</TD>
                            <TD  class= input><Input type=hidden name=ExamNoPassReason ondblclick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);" onkeyup="return showCodeListKey('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ExamNoPassReasonName id=ExamNoPassReasonName readonly=true></TD>
                            <TD  class= title>��ͨ������</TD>
                            <TD  class= input><Input class="wid" class=common name=ExamNoPassDesc id=ExamNoPassDesc></TD>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>        
                    </table>
                </div>
                <table class= common>
                    <TR class= common>
                        <TD  class= title> �������</TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="RemarkSP" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                </table>
            </div>
         </span>
          <!--�᰸��Ϣ-->
        <Span id= "spanEndCase" style="display: 'none';">
            
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowEndCase);"></td>
                    <td class= titleImg> �᰸��Ϣ</td>
                </tr>
            </table>
            <Div  id= "divShowEndCase" style="display: '';" class="maxbox1">
                <table class= common>
                     <TR class= common>
                        <TD  class= title>ʵ���⸶���</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ClmEndRealPay" id="ClmEndRealPay" ></TD>
                        <TD  class= title>�᰸��</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ClmEndPer" id="ClmEndPer" ></TD>
                        <TD  class= title>�᰸����</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ClmEndDate" id="ClmEndDate" ></TD>
                  </TR>
                     <TR class= common>
                        <TD  class= title>ʵ����־</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="confGetMoney" id="confGetMoney" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                  </TR>
                </table>
            </div>
         </span>
    </div>
    
    <table>
        <tr>
            <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <%
    //������,������Ϣ��
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
    <input type=hidden id="RgtClass" name="RgtClass"><!--���ո��մ���-->
    <Input type=hidden id="Phase" name="Phase"><!--���ý׶�-->
	<Input type=hidden id=SocialInsuFlag name=SocialInsuFlag>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
