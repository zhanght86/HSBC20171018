<%
//**************************************************************************************************
//Name��LLReportInput.jsp
//Function�������Ǽ�
//Author��zl
//Date: 2005-6-9 15:31
//Desc: 
//�޸ģ�niuzj,2006-01-12,�����������ʱ�
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
	String tIsNew = request.getParameter("isNew");	//������
	
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
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT> <!-- modify wyc -->
    <SCRIPT src="LLReport.js"></SCRIPT>
    
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form method=post name=fm id=fm target="fraSubmit">
    <!--������Ϣ-->
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>

    <Div id= "divLLReport1" style= "display: ''" class="maxbox">
        <table class= common>
            <TR class= common>
                <TD class= title> �¼��� </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
                <TD class= title> ������ </TD>
                <TD class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>
            <TR class= common>
                <TD class= title> �����˵绰 </TD>
                <TD class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone></TD>
                <TD class= title> �����˵�ַ </TD>
                <TD class= input> <Input class="wid" class= common name=RptorAddress id=RptorAddress ></TD>
                <TD class= title> �������ʱ� </TD>
                <TD class= input> <Input class="wid" class= common name=PostCode id=PostCode onblur="checkPostCode(fm.PostCode);"></TD>   
            </TR>
            <TR class= common>
            	  <TD class= title> ������������˹�ϵ </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation onfocus= "getRptorInfo()" ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);"  onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD class= title> ������ʽ </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name="RptMode" id="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" id="RptModeName" value="�绰����" readonly=true></TD>
                <TD class= title> ���յص� </TD>
                <TD class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>
            </TR>
            <TR class= common>
            	  <TD class= title> �������� </TD>
                <TD class= input>  <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
                <TD class= title> ��Ͻ���� </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
        	  </TR>
            <TR class= common>
            	  <TD class= title> ��������</TD>
          	    <TD class= input> <Input class= codeno value="1" name=RgtClass  id=RgtClassreadonly ><input class=codename name=RgtClassName id=RgtClassName readonly=true value="����"></TD>
            </TR>
        </table>
    </Div>
    
	<br>
    <!--�ͻ���Ϣ-->
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
                    <TD class= title> �ͻ����� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> �Ա� </TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input  class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
	                <TD class= title> �¹�����</TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE" onblur="CheckDate(fm.AccidentDate);" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>    
					<TD class= title> ҽ�Ƴ������� </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>              
                    <TD class= title> ������������ </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#OtherAccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=OtherAccidentDate id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    </TR>
                <TR class= common>
                    <TD class= title> ����ҽԺ</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=hospital id=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onclick="showHospital(this.name,'TreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                	<TD class= title> ����ϸ��</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> ���ս��1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name id=AccResult1Name readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ���ս��2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
               </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> �������� <font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">���</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">�߲�</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"><span style="vertical-align:middle"> �ش󼲲�</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span style="vertical-align:middle">�м������ˡ����ۡ���Ҫ�����г� </span></input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">����</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">ҽ��</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"> <span style="vertical-align:middle">���ּ���</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > ʧҵʧ�� </input>  -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �¹����� </TD>
                </tr>
                <TR class= common>
                    <TD style="padding-left:16px" colspan="6"> 
                        <span id="spanText3" style= "display: ''">
                            <textarea name="AccDesc" id="AccDesc" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
                <TR class= common>
                    <TD class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD style="padding-left:16px" colspan="6">
                        <span id="spanText1" style= "display: ''">
                            <textarea name="Remark" id="Remark" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                        <!--���ر���Ϊ��ʾ������-->
                        <br><br>
                        <span id="spanText2" style= "display: none">
                            <textarea  name="RemarkDisabled" id="RemarkDisabled" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
            </table>
        </span>
    </div>
     <table>
            <tr>
                <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value="�¼���ѯ" type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton2" style= "display: none"><input class=cssButton name=addbutton  VALUE="��  ��"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton3" style= "display: none"><input class=cssButton name=updatebutton  VALUE="��  ��"  TYPE=button onclick="updateClick()" ></span>
                </td>
                <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="�����ⰸ��ѯ" TYPE=button onclick="showOldInsuredCase()"></td>
                <td><input class=cssButton type=hidden  name=MedCertForPrt VALUE="��ӡ�˲м���֪ͨ"  TYPE=button onclick="PrintMedCert()"></td>
                <td><input class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
            </tr>
        </table>
        <hr class="line">
    <span id="operateButton4" >
        <table>
            <tr>
                <td><INPUT class=cssButton name="DoHangUp" VALUE="��������"  TYPE=button onclick="showLcContSuspend()" ></td>
                <td><INPUT class=cssButton name="CreateNote" VALUE="���ɵ�֤"  TYPE=button onclick="showAffix()"></td>
                <td><INPUT class=cssButton name="PrintCertify" VALUE="��ӡ��֤"  TYPE=button onclick="showPrtAffix()"></td>                
                <td><INPUT class=cssButton name="BeginInq" VALUE="�������"  TYPE=button onclick="showBeginInq()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
                <!-- <td><INPUT class=cssButton name="Condole" VALUE="����ο��"  TYPE=button onclick="showCondole()"></td> -->
                <td><INPUT class=cssButton name="SubmitReport" VALUE="����ʱ�"  TYPE=button onclick="showBeginSubmit()"></td>
                <td><INPUT class=cssButton name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="AccidentDesc" VALUE="��ע��Ϣ"  TYPE=button onclick="showClaimDesc()"></td>
            </tr>
        </table>
    </span>
    <hr class="line">
    <table>
        <tr>
            <td><INPUT class=cssButton name="RptConfirm" VALUE="����ȷ��"  TYPE=button onclick="reportConfirm()"></td>
            <td><INPUT class=cssButton type=hidden  name="BatchCreateNote" VALUE="��֤������ӡ����"  TYPE=button onclick="showPrtControl()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table><!--<br>
    <a href="javascript:void(0);" name="RptConfirm" class="button" onClick="reportConfirm();">����ȷ��</a>
    <a href="javascript:void(0);" name="BatchCreateNote" class="button" onClick="showPrtControl();">��֤������ӡ����</a>
    <a href="javascript:void(0);" name="goBack" class="button" onClick="goToBack();">��    ��</a>-->
    <%
    //������,������Ϣ��
    %>
    <Input type=hidden id="customerNo" name="customerNo"><!--�����˴���-->
    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->
	<Input type=hidden id="BaccDate" name="BaccDate"><!--��̨���¹��������ڣ�ÿ�β�ѯʱ���£�У��ʱʹ��-->
    
    <!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">
    
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
