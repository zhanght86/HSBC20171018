<%
//**************************************************************************************************
//Name��LLClaimSimpleInput.jsp
//Function�����װ�������������Ϣ
//Author��zl
//Date: 2005-6-21 10:20
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

	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��

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
    <SCRIPT src="LLClaimSimple.js"></SCRIPT>
    <%@include file="LLClaimSimpleInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm target="fraSubmit">
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
          	    <TD  class= input> <Input class= "readonly" readonly  name=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> �����˵绰 </TD>
              	<TD  class= input> <Input class= "readonly" readonly  name=RptorPhone ></TD>
            	<TD  class= title> ������ͨѶ��ַ </TD>
                <TD  class= input> <Input class= "readonly" readonly  name=RptorAddress ></TD>
    	        <TD  class= title> ���������¹��˹�ϵ </TD>
    	        <TD  class= input> <Input class=codeno disabled name=Relation ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
            </TR>
            <TR  class= common>
       	        <TD  class= title> ���յص� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=AccidentSite ></TD>
       		    <TD  class= title> ������������ </TD>
          	    <TD  class= input> <input class="readonly" readonly name="RptDate" ></TD>
			    <TD  class= title> ��Ͻ���� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=MngCom></TD>
	        </TR>
            <TR  class= common>
        	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly" readonly name=Operator ></TD>
        	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeType ></TD>
        	    <TD  class= title> �����˴��� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeCode ></TD>
            </TR>
            <TR  class= common>
        	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeName ></TD>
        	    <TD  class= title> �������Ա� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeSex ></TD>
        	    <TD  class= title> �����˵绰 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneePhone ></TD>
            </TR>
            <TR  class= common>
        	    <TD  class= title> �����˵�ַ </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeAddr ></TD>
        	    <TD  class= title> �������ʱ� </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeZip ></TD>
        	    <TD  class= title> ��������</TD>
          	    <TD  class= input> <Input class= readonly readonly name=RgtClassName ></TD>
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
            <!--
                <td><input class=cssButton name=QueryPerson value="�����˲�ѯ" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value=" �¼���ѯ " type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
                </td>
             -->
                <td><input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
				<!--td><input class=cssButton name=QueryCont5 VALUE="��Ҫ��Ϣ�޸�" TYPE=button onclick="editImpInfo()"></td-->
                <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>                
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
                    <TD class= title> �����¹ʷ�������</TD>
                    <TD class= input>  <input class= "readonly" readonly name="AccidentDate" ></font></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input><Input class=codeno disabled name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input class=codeno disabled name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
                    <TD class= title> �������� </TD>
                    <TD class= input>  <input class= "readonly" readonly name="AccidentDate2" ></TD>
                    <TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input class=codeno disabled name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input class=codeno disabled name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʾ</TD>
                    <TD class= input> <Input class=codeno name=IsAllReady disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName readonly=true></TD>
                    <TD class= title> ��Ҫ��Ϣ�޸ı�ʾ</TD>
                    <TD class= input> <Input class=codeno name=IsModify disabled CodeData="0|3^0|��^1|��" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName readonly=true></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" name="claimType" onclick="callClaimType('02')"> ���� </input>
                        <input disabled type="checkbox" value="03" name="claimType" onclick="callClaimType('03')"> �߲� </input>
                        <input disabled type="checkbox" value="04" name="claimType"> �� </input>
                        <input disabled type="checkbox" value="01" name="claimType"> �˲� </input>
                        <input disabled type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input>
                        <input disabled type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> ҽ�� </input>
                        <input disabled type="checkbox" value="05" name="claimType" > ���ּ��� </input>
                        <input disabled type="checkbox" value="06" name="claimType" > ʧ�� </input>

                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="AccDesc" cols="100" rows="2" witdh=25% class="common" disabled readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="Remark" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <hr>
    <!--����������Ϣ-->
    <table>
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
    	        <Input class=codeno disabled name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
    	        </TD>
                <TD  class= title> ������ʶ</TD>
                <TD  class= input> <Input class=codeno disabled name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
    	        <td>&nbsp;&nbsp;</td>
    	     </tr>
    	</table>
    </div>
    <hr>
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
                    <TD  class= input><Input class=codeno name=GiveType disabled ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName readonly=true></font>
                    <TD  class= title></TD>
                    <TD  class= input><Input class=readonly readonly ></TD>
                    <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr>
            </table>
            <Div  id= "divGiveTypeUnit1" style= "display: 'none'">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>�������</TD>
                        <TD  class= input><Input class=common name=RealPay onblur="checkAdjMoney();"></TD>
                        <TD  class= title>����ԭ��</TD>
                        <TD  class= input><Input class=codeno name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName readonly=true></font>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
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
                        <TD  class= input><Input class=codeno name=GiveTypeDesc ondblclick="return showCodeList('llprotestreason',[this,GiveTypeDescName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveTypeDescName],[0,1]);"><input class=codename name=GiveTypeDescName readonly=true></td>
                        <TD  class= title>�ܸ�����</TD>
                        <TD  class= input><Input class=common name=GiveReason></td>
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
                     <td><INPUT class=cssButton name="saveUpdate" VALUE=" �����޸� "  TYPE=button onclick="SaveUpdate();" ></td>
                 </tr>
            </table>
        </div>
    </div>
    <hr>
    <table>
        <tr>
            <td><INPUT class=cssButton name="dutySet" VALUE=" ƥ�䲢���� "  TYPE=button onclick="showMatchDutyPay();"></td>
            <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="ҽ�Ƶ�֤����"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
            <td><INPUT class=cssButton name="" VALUE="��֤���õ���"  TYPE=button onclick="showLLMedicalFeeAdj();"></td>
            <td><INPUT class=cssButton name="medicalFeeCal" VALUE="���ü���鿴"  TYPE=button onclick="showLLMedicalFeeCal();"></td>
            <td><INPUT class=cssButton name="AccidentDesc" VALUE=" ��ע��Ϣ "  TYPE=button onclick="showClaimDesc()"></td>
            <td><INPUT class=cssButton name="" VALUE="�����˷���"  TYPE=button onclick="showBnf()"></td>
        </tr>
    </table>
    <hr>
    <!--���װ�����Ϣ-->
    <table>
         <tr>
         	 <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSimpleClaim);"></td>
             <td class= titleImg>���װ�����Ϣ</td>
         </tr>
    </table>
    <Div  id= "divSimpleClaim" style= "display:''">
        <table class= common>
            <TR class= common>
                <TD  class= title>���װ�������</TD>
                <TD  class= input><Input class=codeno name=SimpleConclusion ondblClick="showCodeList('llexamconclusion',[this,SimpleConclusionName],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,SimpleConclusionName],[0,1]);"><input class=codename name=SimpleConclusionName readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>
        </table>
    </div>
    <hr>
    <INPUT class=cssButton name="simpleClaim" VALUE="���װ���ȷ��"  TYPE=button onclick="confirmClick()">
    <INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()">

    <%
    //������,������Ϣ��
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden name=hideOperate value=''>
    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
    <input type=hidden id="clmState" name="clmState"><!--����״̬-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
    <input type=hidden id="RgtClass" name="RgtClass"><!--���ո��մ���-->
    <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--�Ƿ�Ԥ��-->
    <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--���Ȩ��-->

    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
