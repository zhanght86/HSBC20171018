<%
//**************************************************************************************************
//Name��LLClaimQueryInput.jsp
//Function���ⰸ��ѯ
//Author��zl
//Date: 2005-6-20 14:00
//Desc:
//�޸ģ�niuzj,2006-01-13,�������������ȡ��ʽ���ֶ�
//**************************************************************************************************
%>
<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
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
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
   <SCRIPT src="LLClaimQuery.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
   <%@include file="LLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
<form action="./LLClaimQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!--����������Ϣ-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>����������Ϣ</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" class="maxbox" style= "display: ''">
        <table  class= common>
           <TR  class= common>
			          <TD  class= title> �¼��� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccNo id="AccNo"></TD>
         	      <TD  class= title> ������ </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id="RptNo" ></TD>
          	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName id="RptorName" ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> �����˵绰 </TD>
              	<TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone id="RptorPhone" ></TD>
              	<TD  class= title> �������ֻ����� </TD>
              	<TD  class= input> <Input class= "readonly wid" readonly name=RptorMoPhone id="RptorMoPhone" ></TD>
                <TD  class= title> ��������ϸ��ַ </TD>
                <TD  class= input> <Input class= "common3 wid" readonly  name=RptorAddress ></TD>
            </TR>
           
            <TR  class= common>
            		<TD  class= title> �������ʱ� </TD>
								<TD  class= input><Input class= "readonly wid" readonly name="AppntZipCode" ></TD>
    	          <TD  class= title> ������������˹�ϵ </TD>
    	          <TD  class= input> <Input class=codeno disabled name=Relation id="Relation" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onClick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onDblClick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onKeyUp="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id="RelationName" readonly=true></TD>
                <TD  class= title> ���յص� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccidentSite ></TD>
            </TR>
            <TR  class= common>
       		      <TD  class= title> �������� </TD>
          	    <TD  class= input> <input class="readonly wid" readonly name="RptDate" ></TD>
			          <TD  class= title> ��Ͻ���� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=MngCom></TD>
          	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=Operator ></TD>
	          </TR>
            <TR  class= common>
        	      <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeType ></TD>
        	      <TD  class= title> �����˴��� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeCode ></TD>
          	    <TD  class= title> ���������� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeName ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> �������Ա� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeSex ></TD>
        	      <TD  class= title> �����˵绰 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone ></TD>
          	    <TD  class= title> �����˵�ַ </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> �������ʱ� </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip ></TD>
                 <TD class= title> ��������</TD>
                <TD class= input>  <input class="readonly wid" readonly  name="AcceptedDate";><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD class= title> �ͻ���������</TD>
                <TD class= input>  <input class="readonly wid" readonly  name="ApplyDate";><font size=1 color='#ff0000'><b>*</b></font></TD>           
            </TR>
            <TR  class= common>
            	  <TD  class= title>�������ȡ��ʽ</TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=GetMode ></TD>
          	   <TD  class= title>���������������</TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=AddAffixAccDate ></TD>
          	    <TD  class= title>
          	       <span id="spanRgtObjNo1" style= "display: none">       
          	           �����ⰸ��
          	       </span>
          	    </TD>
          	    
          	    <TD  class= input> 
          	       <span id="spanRgtObjNo2" style= "display: none"> 	
          	           <Input class="readonly wid" readonly name=RgtObjNo >
          	       </span>
          	    </TD>   
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
    <Div id= "divLLSubReport" class="maxbox1" style= "display: ''">
        <table>
            <tr>
                <td><input class=cssButton name=QueryCont1 VALUE=" ������ѯ "  TYPE=button onClick="showReport()"></td>
                <td><input class=cssButton name=QueryIssue value="�������ѯ" type=button onClick="queryLLIssue()"></td>
                <td><input class=cssButton name=QueryCont2 VALUE=" ������ѯ "  TYPE=button onClick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onClick="showOldInsuredCase()"></td>
                 <td><input class=cssButton name=QueryCont5 VALUE="��Ҫ��Ϣ�޸�" TYPE=button onClick="editImpInfo()"></td>
                <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onClick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onClick="colQueryImage();"></td>                
                <td><INPUT class=cssButton name="ReciInfoInp" VALUE="�ռ�����Ϣ"  TYPE=button onClick="showReciInfoInp();"></td>
                <td><INPUT class=cssButton name="MedicalFeeInp" VALUE=" ҽ�Ƶ�֤ "  TYPE=button onClick="showLLMedicalFeeInp();"></td>  
                <td><INPUT class=cssButton name="AccidentDesc" VALUE=" ��ע��Ϣ "  TYPE=button onClick="showClaimDesc()"></td>                              
            </tr>
        </table>
        <!--��������Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--�����˱���,��������-->
                    <TD class= title> �ͻ����� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerName></TD>
                    <TD class= title> ���� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge></TD>
                    <TD class= title> �Ա� </TD>
                    <TD class= input> <Input type=hidden name=customerSex onDblClick="return showCodeList('sex',[this,SexName],[0,1]);" onKeyUp="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="readonly wid" readonly name=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <!-- <TD class= title> VIP�ͻ���ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class="readonly wid" readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> �¹�����</TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate" ></font></TD>
					<TD class= title> ҽ�Ƴ������� </TD>
                    <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="MedicalAccidentDate" onBlur="CheckDate(fm.MedicalAccidentDate);" id="MedicalAccidentDate" onClick="laydate({elem:'#MedicalAccidentDate'});"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>              
                    <TD class= title> ������������ </TD>
                    <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="OtherAccidentDate" onBlur="CheckDate(fm.OtherAccidentDate);" id="OtherAccidentDate" onClick="laydate({elem:'#OtherAccidentDate'});"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>
                    
                    </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input type=hidden name=hospital id="hospital" onDblClick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onKeyUp="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class="readonly wid" readonly name=TreatAreaName readonly=true></TD>
					<TD class= title> ����ԭ��</TD>
                    <TD class= input><Input type=hidden name=occurReason onDblClick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onKeyUp="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class="readonly wid" readonly name=ReasonName readonly=true></font></TD>
                    <TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input type=hidden name=accidentDetail onDblClick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onKeyUp="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class="readonly wid" readonly name=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input type=hidden name=cureDesc onDblClick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onKeyUp="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class="readonly wid" readonly name=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 onDblClick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onKeyUp="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class="readonly wid" readonly name=AccResult1Name readonly=true></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 onDblClick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onKeyUp="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class="readonly wid" readonly name=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��֤��ȫ��ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsAllReady disabled CodeData="0|3^0|��^1|��" onDblClick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class="readonly wid" readonly name=IsAllReadyName readonly=true></TD>
                    <TD class= title> ��Ҫ��Ϣ�޸ı�ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsModify disabled CodeData="0|3^0|��^1|��" onDblClick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class="readonly wid" readonly name=IsModifyName readonly=true></TD>
                    <TD class= title> ������ʶ</TD>
                    <TD class= input> <Input class="readonly wid" name=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������ͣ�<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" name="claimType" onClick="callClaimType('02')"> ��� </input>
                        <input disabled type="checkbox" value="03" name="claimType" onClick="callClaimType('03')"> �߲� </input>
                        <input disabled type="checkbox" value="04" name="claimType"> �ش󼲲� </input>
                        <input disabled type="checkbox" value="01" name="claimType"> �м������ˡ����ۡ���Ҫ�����г� </input>
                        <input disabled type="checkbox" value="09" name="claimType" onClick="callClaimType('09')"> ���� </input>
                        <input disabled type="checkbox" value="00" name="claimType" onClick="callClaimType('00')"> ҽ�� </input>
                        <input disabled type="checkbox" value="05" name="claimType" > ���ּ��� </input>
                        <!-- <input disabled type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input> -->

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
    <!--����������Ϣ-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
            <td class= titleImg>����������Ϣ</td>
         </tr>
    </table>
    <Div  id= "divBaseUnit6" class="maxbox" style= "display:''">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title> ��������</TD>
    	        <TD  class= input> <Input type=hidden name=RgtConclusion onDblClick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class="readonly wid" readonly name=RgtConclusionName readonly=true></TD>
                <TD  class= title> ������ʶ</TD>
                <TD  class= input> <Input type=hidden name=rgtType onDblClick="return showCodeListEx('llrgttype', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeName],[0,1])"><Input class="readonly wid" readonly name=rgtTypeName readonly=true></TD>
                <TD  class= title> �ⰸ״̬</TD>
                <TD  class= input> <Input type=hidden name=ClmState onDblClick="return showCodeListEx('llclaimstate', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('llclaimstate', [this,ClmStateName],[0,1])"><Input class="readonly wid" readonly name=ClmStateName readonly=true></TD>
    	     </tr>
    	</table>
    </div>
    <Div id= "divPart2" style= "display:none">
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
            <Div  id= "divBaseUnit4" class="maxbox" style= "display:''">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>�⸶����</TD>
                        <TD  class= input><Input type=hidden name=GiveType onDblClick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onKeyUp="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class="readonly wid" readonly name=GiveTypeName readonly=true></font>
                        <TD  class= title></TD>
                        <TD  class= input><Input class="readonly wid" readonly ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <Div  id= "divGiveTypeUnit1" style= "display: none">
                    <table class= common>
                        <TR class= common>
                        	<td class=title>����</td>
    						<td class=input><Input class="common wid" name=Currency readonly=true></td>
                            <TD  class= title>�������</TD>
                            <TD  class= input><Input class="readonly wid" readonly name=RealPay></TD>
                            <TD  class= title>����ԭ��</TD>
                            <TD  class= input><Input type=hidden name=AdjReason onDblClick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onKeyUp="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class="readonly wid" readonly name=AdjReasonName readonly=true></font>
                        </tr>
                    </table>
                    <table class= common>
                        <TR class= common>
                            <TD class= title> ������ע </TD>
                        </tr>
                        <TR class= common>
                            <TD class= input> <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                        </tr>
                     </table>
                </div>
                <Div  id= "divGiveTypeUnit2" style= "display: none">
                    <table class= common>
                        <TR class= common>
                            <TD  class= title>�ܸ�ԭ��</TD>
                            <TD  class= input><Input type=hidden name=GiveReason onDblClick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onKeyUp="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class="readonly wid" readonly name=GiveReasonName readonly=true></td>
                            <TD  class= title>�ܸ�����</TD>
                            <TD  class= input><Input class="readonly wid" readonly name=GiveReasonDesc></td>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>
                    </table>
                    <table class= common>
                        <TR class= common >
                            <TD class= title> ���ⱸע </TD>
                        </tr>
                        <TR class= common>
                            <TD class= input> <textarea name="SpecialRemark" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <hr class="line">
        <table>
            <tr>
                <td><INPUT class=cssButton name="ViewReport" VALUE=" �鿴�ʱ� "  TYPE=button onClick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE=" �鿴���� "  TYPE=button onClick="showQueryInq()"></td>
                <td><INPUT class=cssButton name="SecondUWResult" VALUE=" ���˽��� "  TYPE=button onClick="SecondUWInput()"></td>
                <td><INPUT class=cssButton name="CreateNote" VALUE=" �����ѯ "  TYPE=button onClick="showExempt()"></td>
                <td><INPUT class=cssButton name="" VALUE=" �������� "  TYPE=button onClick="showPolDeal()"></td>
                <td><INPUT class=cssButton name="" VALUE=" ��ͬ���� "  TYPE=button onClick="showContDeal()"></td>
                <td><INPUT class=cssButton name="" VALUE="�����˷���"  TYPE=button onClick="showBnf()"></td>
                <td><INPUT class=cssButton name="" VALUE="����������۲�ѯ"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>
				<!--td><INPUT class=cssButton name="medicalFeeCal" VALUE=" ���ò鿴 "  TYPE=button onclick="showLLMedicalFeeCal();"></td-->
                <!--td><INPUT class=cssButton name="" VALUE=" �ʻ����� "  TYPE=button onclick="showLCInsureAccont()"></td-->
				<td><INPUT class=cssButton type=hidden name="MedicalFeeAdj" VALUE="���õ����鿴"  TYPE=button  onclick="showMedicalAdjClick()" ></td>
				<td><INPUT class=cssButton type=hidden name="MedicalFeeCal" VALUE=" ���ò鿴 "  TYPE=button onclick="showLLMedicalFeeCal();"></td>               
            </tr>
        </table>
        <!--��˹�����Ϣ-->
        <Span id="spanAudit" style="display: none">
            <hr class="line">
            <table>
              <TR>
               <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
               <TD class= titleImg>��˹���</TD>
             </TR>
            </table>
            <Div id= "divLLAudit" class="maxbox" style= "display: ''">
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>��˽���</TD>
                        <TD  class= input><Input type=hidden name=AuditConclusion id="AuditConclusion" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onClick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onDblClick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class="readonly wid" readonly name=AuditConclusionName id="AuditConclusionName" readonly=true></TD>
                        <TD  class= title>���ⱸע</TD>
                        <TD  class= input><Input class="readonly wid" readonly name=SpecialRemark1></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <Div id= "divLLAudit2" style= "display: none">
                     <table class= common>
                        <TR class= common>
                            <TD  class= title>�ܸ�ԭ��</TD>
                            <TD  class= input><Input type=hidden name=ProtestReason onDblClick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onKeyUp="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class="readonly wid" readonly name=ProtestReasonName readonly=true></TD>
                            <TD  class= title>�ܸ�����</TD>
                            <TD  class= input><Input class="common wid" disabled name=ProtestReasonDesc></TD>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>
                    </table>
                </div>
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>�����</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="AuditPer" ></TD>
                        <TD  class= title>�������</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="AuditDate" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title>������</TD>
                    </tr>
                    <TR class= common>
                        <TD class= input> <textarea name="AuditIdea" cols="100" rows="6" witdh=25% class="common" readonly ></textarea></TD>
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
        <Span id= "spanConfirm" style="display: none;">
            <hr class="line">
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowQueryDetail);"></td>
                    <td class= titleImg> ��������</td>
                </tr>
            </table>
            <Div  id= "divShowQueryDetail" class="maxbox" style="display: '';">
                <table class= common>
               	    <TR class= common>
        	            <TD  class= title>��������</TD>
                        <TD  class= input><Input type=hidden name="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onKeyUp="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" onfocus= "choiseQueryConclusionType();"><input class="readonly wid" readonly name=DecisionSPName readonly></TD>
                        <TD  class= title>������</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ExamPer" ></TD>
                        <TD  class= title>��������</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ExamDate" ></TD>
        	        </TR>
                </table>
                <Div id= "divLLQuery2" style= "display: none">
                     <table class= common>
                        <TR class= common>
                            <TD  class= title>��ͨ��ԭ��</TD>
                            <TD  class= input><Input type=hidden name=ExamNoPassReason onDblClick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);" onKeyUp="return showCodeListKey('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);"><input class="readonly wid" readonly name=ExamNoPassReasonName readonly=true></TD>
                            <TD  class= title>��ͨ������</TD>
                            <TD  class= input><Input class="common wid" name=ExamNoPassDesc></TD>
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
                        <TD  class= input> <textarea name="RemarkSP" cols="100%" rows="6" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                </table>
            </div>
         </span>
          <!--�᰸��Ϣ-->
        <Span id= "spanEndCase" style="display: none;">
            <hr class="line">
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowEndCase);"></td>
                    <td class= titleImg> �᰸��Ϣ</td>
                </tr>
            </table>
            <Div  id= "divShowEndCase" class="maxbox" style="display: '';">
                <table class= common>
                	<TR class= common>
                        <td class=title>����</td>
    					<td class=input><Input class="common wid" name=ClmCurrency readonly=true></td>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
        	        </TR>
               	    <TR class= common>
                        <TD  class= title>ʵ���⸶���</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndRealPay" ></TD>
                        <TD  class= title>�᰸��</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndPer" ></TD>
                        <TD  class= title>�᰸����</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndDate" ></TD>
        	        </TR>
               	    <TR class= common>
                        <TD  class= title>ʵ����־</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="confGetMoney" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
        	        </TR>
                </table>
            </div>
         </span>
    </div>
    <!-- ��������ԭ�� Add by zhaorx 2006-03-06 -->
    <span id= "divPart3" style= "display: none">
    	<!--��������ԭ��-->
        <table class= common>
            <TR class= common>
            	<TD  class= title> ��������ԭ��</TD>
            	<TD  class= input> <input class="readonly wid" readonly name=NoRgtReasonName readonly=true></TD>
            	<TD  class= title> </TD>
            	<TD  class= input> </TD>
            	<TD  class= title> </TD>
            	<TD  class= input> </TD>
            </tr>
        </table>
    </span> 
    
    <hr class="line">
    <table>
        <tr> 
        	 <td><INPUT class=cssButton name="PrtAgain" VALUE="��֤������Ϣ" TYPE=button onClick="PrtAgainInfo()"></td>
        	 <td><span id="spanIsShow" style= "display: none"><INPUT class=cssButton name="ConclusionSave" VALUE="ԭ�ⰸ�鿴"  TYPE=button onClick="OriClmLook()"></span></td>
           <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onClick="goToBack()"></td>
        </tr>
    </table>
    <%
    //������,������Ϣ��
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">
    <Input type=hidden id="RgtClass" name=RgtClass value="1" >
    <input type=hidden id="RgtClassName" name=RgtClassName value="����">

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
    <input type=hidden id="RgtClass" name="RgtClass"><!--���ո��մ���-->
    <Input type=hidden id="Phase" name="Phase"><!--���ý׶�-->

</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
