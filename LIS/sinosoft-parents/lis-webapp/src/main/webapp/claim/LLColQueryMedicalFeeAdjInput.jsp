<%
//**************************************************************************************************
//Name��LLColQueryMedicalFeeAdjInput.jsp
//Function��ҽ�Ʒ��õ�����ʾ��Ϣ
//Author��
//Date: 
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
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tRptNo = request.getParameter("tRptNo");	//�ⰸ��
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
    <SCRIPT src="LLMedicalFeeAdj.js"></SCRIPT>
    <%@include file="LLMedicalFeeAdjInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> ҽ�Ʒ��õ�����ʾ��Ϣ</td>
        </tr>
    </table>
    <Div id= "divLLSubReport" style= "display: ''" class="maxbox">

        <!--��������Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
			    <TR class= common>
					<TD class= title> �������� </TD>
					<TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=OperationType id=OperationType CodeData="0|3^A|����^B|סԺ^C|�˲�" ondblclick="return showCodeListEx('Feetype', [this,OperationTypeName],[0,1])" onclick="return showCodeListEx('Feetype', [this,OperationTypeName],[0,1])"onkeyup="return showCodeListKeyEx('Feetype', [this,OperationTypeName],[0,1])" onfocus="choiseType();"><Input class=codename name=OperationTypeName id=OperationTypeName readonly=true></TD>
					<TD class= title> </TD>
					<TD class= input> </TD>
					<TD class= title> </TD>
					<TD class= input> </TD>
			    </TR>
			</table>
		 </span>

        <Div id= "divLLSubReportll" style= "display: ''" >
			<Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeInHosInpGrid"></span></td>
                </tr>
            </Table>
		</Div>

		<Div id= "divLLSubReport0" style= "display: none" >
		    <table class= common>
                
                    <!--��������-->
                    <TD class= title> �ⰸ�� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=ClmNo id=ClmNo></TD>
                    <TD class= title> �������� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=DutyFeeType id=DutyFeeType></TD>
                    <TD class= title> ���ô��� </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=DutyFeeCode id=DutyFeeCode></TD>

    				<TR class= common>
    				<TD class= title> �������� </TD>
					<TD class= input> <Input class="readonly wid" readonly name=DutyFeeName id=DutyFeeName></TD>
					<input type="hidden" name="DutyFeeStaNo" />
				</TR>
			</table>
		</Div>
		<!--����&סԺ-->
		<Div id= "divLLSubReport1" style= "display: none" >
		     <table class= common>
                <TR class= common  >
                    <TD class= title> ҽԺ���</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=HosID id=HosID></TD>
                    <TD class= title> ҽԺ����</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=HosName id=HosName></TD>
                    <TD class= title> ҽԺ�ȼ�</TD>
                    <TD class= input><Input type=hidden name=HosGrade disabled ondblclick="return showCodeList('llhosgrade',[this,HosGradeName],[0,1]);" onkeyup="return showCodeListKey('llhosgrade',[this,HosGradeName],[0,1]);"><input class="readonly wid" name=HosGradeName id=HosGradeName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��ʼʱ�� </TD>
                    <TD class= input>  <input class="readonly wid" readonly dateFormat="short" name="StartDate" id="StartDate" ></TD>
					<TD class= title> ����ʱ�� </TD>
                    <TD class= input>  <input class="readonly wid" readonly dateFormat="short" name="EndDate" id="EndDate" ></TD>
                    <TD class= title> ����</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=DayCount id=DayCount></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ԭʼ���</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=OriSum id=OriSum></TD>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input class="readonly wid" readonly MAXLENGTH=16 verify="�������|LEN<=16"  verifyorder="1" name=AdjSum id=AdjSum></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input> <Input type=hidden name=AdjReason ondblclick="return showCodeList('llmedfeeadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('llmedfeeadjreason',[this,AdjReasonName],[0,1]);"><input class="readonly wid" name=AdjReasonName id=AdjReasonName readonly=true></TD>
                </TR>
				
            </table>
			<table class= common>
				<TR class= common>
					<TD class= title> ������ע</TD>
				</TR>
				<TR class= common>
					<TD colspan="6" style="padding-left:16px">
					<textarea name="AdjRemark" id="AdjRemark" verify="������ע|len<1000" verifyorder="1" cols="228" rows="4" witdh=25% class="common" readonly ></textarea>
					</TD>
				</TR>
			</table>
		</div>
        <!--�˲�-->
        <Div id= "divLLSubReport2" style= "display: none" >
		    <table class= common>
                <TR class= common >
                    <TD class= title> �˲�����</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=DefoType id=DefoType></TD>
                    <TD class= title> �˲д���</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=DefoCode id=DefoCode></TD>
                    <TD class= title> �˲м�������</TD>
                    <TD class= input><Input class="readonly wid" readonly name=DefoeName id=DefoeName></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �м��������� </TD>
                    <TD class= input>  <Input class="readonly wid" readonly name=DefoRate id=DefoRate></TD>
					<TD class= title> ʵ�ʸ������� </TD>
                    <TD class= input>  <Input class="readonly wid" readonly MAXLENGTH=10 verify="�������|LEN<=10"  verifyorder="1" name=RealRate id=RealRate></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=AdjReason1 id=AdjReason1 ondblclick="return showCodeList('llmedfeeadjreason',[this,AdjReason1Name],[0,1]);" onclick="return showCodeList('llmedfeeadjreason',[this,AdjReason1Name],[0,1]);" onkeyup="return showCodeListKey('llmedfeeadjreason',[this,AdjReason1Name],[0,1]);"><input class="readonly wid" name=AdjReason1Name id=AdjReason1Name readonly=true></TD>
                </TR>
                
            </table>
			<table class= common>
				<TR class= common>
					<TD class= title> ������ע</TD>
				</TR>
				<TR class= common>
					<TD colspan="6" style="padding-left:16px">
					    <textarea name="AdjRemark1" id="AdjRemark1" verify="������ע|len<1000" verifyorder="1" cols="228" rows="4" witdh=25% class="common" readonly ></textarea>
					</TD>
				</TR>
			</table>
    </div>
    
	<!--����-->
	<Div id= "divLLSubReport3" style= "display: none" >
       <table class= common>
            <TR class= common>
                <TD class= title> ԭʼ���</TD>
                <TD class= input> <Input class="readonly wid" readonly name=OriSum1 id=OriSum1></TD>
                <TD class= title> �������</TD>
                <TD class= input> <Input class="readonly wid" readonly MAXLENGTH=16 verify="�������|LEN<=16"  verifyorder="1" name=AdjSum1 id=AdjSum1></TD>
                <TD class= title> ����ԭ��</TD>
                <TD class= input> <Input type=hidden name=AdjReason2 ondblclick="return showCodeList('llmedfeeadjreason',[this,AdjReason2Name],[0,1]);" onkeyup="return showCodeListKey('llmedfeeadjreason',[this,AdjReason2Name],[0,1]);"><input class="readonly wid" name=AdjReason2Name id=AdjReason2Name readonly=true></TD>
            </TR>
        </table>
		<table class= common>
			<TR class= common>
				<TD class= title> ������ע</TD>
			</TR>
			<TR class= common>
				<TD colspan="6" style="padding-left:16px">
				<textarea name="AdjRemark2" id="AdjRemark2" verify="������ע|len<1000" verifyorder="1" cols="228" rows="4" witdh=25% class="common" readonly ></textarea>
				</TD>
			</TR>
		</table>
    </div></Div>
    <!--INPUT class=cssButton name="ConclusionSave" VALUE="�޸�ȷ��"  TYPE=button onclick="saveClick()"-->
    <!--<INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()">--><br>
    <a href="javascript:void(0);" name="goBack" class="button" onClick="goToBack();">��    ��</a>
    <%
    //������,������Ϣ��
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->

    <Input type=hidden id="ClmNo2" name="ClmNo2"><!--�ⰸ��-->
    <input type=hidden id="isNew" name="isNew"><!--�Ƿ񴴽��½�-->
    <input type=hidden id="clmState" name="clmState"><!--����״̬-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10����-->

    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
