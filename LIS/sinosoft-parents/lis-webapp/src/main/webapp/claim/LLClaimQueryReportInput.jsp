<%
//**************************************************************************************************
//Name��LLClaimQueryReportInput.jsp
//Function��������ѯ
//Author��zl
//Date: 2005-8-23 18:09
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
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    String CurrentDate = PubFun.getCurrentDate();
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
    <SCRIPT src="LLClaimQueryReport.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimQueryReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form method=post name=fm id=fm target="fraSubmit">
    <!--������Ϣ-->
    <table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQueryReport1);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>

    <Div id= "divLLClaimQueryReport1" class=maxbox style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> �¼��� </TD>
                <TD class= input> <Input class="readonly wid" readonly name=AccNo id=AccNo></TD>
                <TD class= title> ������ </TD>
                <TD class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <input class="readonly wid" readonly name=RptorName id=RptorName ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �����˵绰 </TD>
                <TD class= input> <input class="readonly wid" readonly name=RptorPhone id=RptorPhone ></TD>
                <TD class= title> �����˵�ַ </TD>
                <TD class= input> <input class="readonly wid" readonly name=RptorAddress id=RptorAddress ></TD>
                <TD class= title> �������ʱ� </TD>
                <TD class= input> <Input class="readonly wid" readonly name=PostCode id=PostCode ></TD>   
            </TR>
            <TR class= common>
            	  <TD class= title> ������������˹�ϵ </TD>
                <TD class= input> <Input type=hidden name=Relation id=Relation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class="readonly wid" readonly name=RelationName ></TD>
                <TD class= title> ������ʽ </TD>
                <TD class= input> <Input type=hidden name="RptMode" id=RptMode  value="01" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class="readonly wid" readonly name="RptModeName" value="�绰����" ></TD>
                <TD class= title> ���յص� </TD>
                <TD class= input> <input class="readonly wid" readonly name=AccidentSite id=AccidentSite ></TD>
            </TR>
            <TR class= common>
            	  <TD class= title> ������������ </TD>
                <TD class= input>  <input class="readonly wid" readonly name="RptDate" id=RptDate ></TD>
                <TD class= title> ��Ͻ���� </TD>
                <TD class= input> <Input class="readonly wid" readonly name=MngCom id=MngCom></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
            </TR>
            <TR class= common>
            	  <TD class= title> ��������</TD>
          	    <TD class= input> <Input type=hidden value="1" name=RgtClass id=RgtClass style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class="readonly wid" readonly name=RgtClassName  value="����"></TD>
            </TR>
        </table>
    </Div>
    <hr class=line>

    <!--��������Ϣ-->
    <table class= common>
        <tr class= common>
            <td style="text-align: left" colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> �ͻ���Ϣ </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" class=maxbox style= "display: ''">
        <table>
            <tr>
                <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
                <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
            </tr>
        </table>
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
                    <TD class= input> <Input type=hidden name=customerSex id=customerSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="readonly wid" readonly name=SexName id=SexName ></TD>
                </TR>
                <TR class= common>
                    <!-- <TD class= title> VIP�ͻ���ʶ</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class="readonly wid" readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> �¹�����</TD>
                    <TD class= input>  <Input class="readonly wid" readonly name="AccidentDate" id=AccidentDate ></TD>
					<TD class= title> ҽ�Ƴ������� </TD>
                    <TD class= input>  <input class="multiDatePicker common" dateFormat="short" name="MedicalAccidentDate" onblur="CheckDate(fm.MedicalAccidentDate);" onClick="laydate({elem: '#MedicalAccidentDate'});" id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>              
                    <TD class= title> ������������ </TD>
                    <TD class= input>  <input class="multiDatePicker common" dateFormat="short" name="OtherAccidentDate" onblur="CheckDate(fm.OtherAccidentDate);" onClick="laydate({elem: '#OtherAccidentDate'});" id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>
               </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input type=hidden name=hospital id=hospital style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1]);" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1]);"><input class="readonly wid" readonly name=TreatAreaName id=TreatAreaName ></TD>
					<TD class= title> ����ԭ��</TD>
                    <TD class= input><Input type=hidden name=occurReason id=occurReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class="readonly wid" readonly name=ReasonName id=ReasonName ></TD>
                    <TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input type=hidden name=accidentDetail id=accidentDetail style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class="readonly wid" readonly name=accidentDetailName id=accidentDetailName ></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input type=hidden name=cureDesc id=cureDesc style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class="readonly wid" readonly name=cureDescName ></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 id=AccResult1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class="readonly wid" readonly name=AccResult1Name id=AccResult1Name ></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 id=AccResult2 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class="readonly wid" readonly name=AccResult2Name id=AccResult2Name ></TD>
                </TR>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������� </TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> ��� </input>
                        <input disabled type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> �߲� </input>
                        <input disabled type="checkbox" value="04" name="claimType"> �ش󼲲� </input>
                        <input disabled type="checkbox" value="01" name="claimType"> �м������ˡ����ۡ���Ҫ�����г� </input>
                        <input disabled type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> ���� </input>
                        <input disabled type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> ҽ�� </input>
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
                    <TD class= input> 
                        <textarea disabled name="AccDescDisabled" id=AccDescDisabled cols="100" rows="2" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD class= input>
                        <textarea disabled name="RemarkDisabled" id=RemarkDisabled cols="100" rows="2" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </span>
    </div>
    <hr class=line>
    <span id="operateButton4" >
        <table>
            <tr>
                <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
                <td><INPUT class=cssButton name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
            </tr>
        </table>
    </span>
    <hr class=line>
    <table>
        <tr>
        	  <td><INPUT class=cssButton name="PrtAgain" VALUE="��֤������Ϣ" TYPE=button onclick="PrtAgainInfo()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <%
    //������,������Ϣ��
    %>
    <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
    <Input type=hidden id="Phase" name="Phase"><!--���ý׶�-->

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br /><br /><br /><br />
</body>
</html>
