<%
//**************************************************************************************************
//Name��LLMedicalFeeAdjInput.jsp
//Function��ҽ�Ʒ��õ�����ʾ��Ϣ
//Author��quyang
//Date: 2005-7-05
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

	  String tRptNo = request.getParameter("tRptNo");	//�ⰸ��

//	  String tMissionID = request.getParameter("MissionID");  //����������ID
//	  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
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
    <SCRIPT src="LLMedicalFeeAdj.js"></SCRIPT>
    <%@include file="LLMedicalFeeAdjInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> ҽ�Ʒ��õ�����ʾ��Ϣ</td>
        </tr>
    </table>
    <Div id= "divLLSubReport" style= "display: ''">

        <!--��������Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
			        <TR class= common>
					<TD class= title>��������</TD>
					<TD  class= input> <Input class=codeno name=OperationType CodeData="0|3^A|����^B|סԺ^C|�˲�" ondblclick="return showCodeListEx('Feetype', [this,OperationTypeName],[0,1])"onkeyup="return showCodeListKeyEx('Feetype', [this,OperationTypeName],[0,1])" onfocus="choiseType();"><Input class=codename name=OperationTypeName readonly=true></TD>
					<TD  class= title> </TD>
					<TD  class= input> </TD>
					<TD  class= title> </TD>
					<TD  class= input> </TD>
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

		<Div id= "divLLSubReport0" style= "display: 'none'" >
		    <table class= common>
                
                    <!--��������-->
                    <TD class= title> �ⰸ�� </TD>
                    <TD class= input> <Input class="readonly" readonly name=ClmNo></TD>
                    <TD class= title> �������� </TD>
                    <TD class= input> <Input class="readonly" readonly name=DutyFeeType></TD>
                    <TD class= title> ���ô��� </TD>
                    <TD class= input> <Input class="readonly" readonly name=DutyFeeCode></TD>
					
                
				<TR class= common>
				<TD class= title> �������� </TD>
					<TD class= input> <Input class="readonly" readonly name=DutyFeeName></TD>
					<input type="hidden" name="DutyFeeStaNo" />
				</TR>
			</table>
		</Div>
				<!--����&סԺ-->
		<Div id= "divLLSubReport1" style= "display: 'none'" >
		     <table class= common>
                <TR class= common  >
                    <TD class= title> ҽԺ���</TD>
                    <TD class= input> <Input class="readonly" readonly name=HosID></TD>
                    <TD class= title> ҽԺ����</TD>
                    <TD class= input> <Input class="readonly" readonly name=HosName></TD>
                    <TD class= title> ҽԺ�ȼ�</TD>
                    <TD class= input><Input class=codeno name=HosGrade disabled ondblclick="return showCodeList('llhosgrade',[this,HosGradeName],[0,1]);" onkeyup="return showCodeListKey('llhosgrade',[this,HosGradeName],[0,1]);"><input class=codename name=HosGradeName readonly=true><font size=1 color='#ff0000'></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ��ʼʱ�� </TD>
                    <TD class= input>  <input class="readonly" dateFormat="short" name="StartDate" ></TD>
					<TD class= title> ����ʱ�� </TD>
                    <TD class= input>  <input class="readonly" dateFormat="short" name="EndDate" ></TD>
                    <TD class= title> ����</TD>
                    <TD class= input> <Input class="readonly" readonly name=DayCount></TD>
                </TR>
                <TR class= common>
                    <TD class= title> ԭʼ���</TD>
                    <TD class= input> <Input class="readonly" readonly name=OriSum></TD>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input class="common" MAXLENGTH=16 onclick="inputCheck()" onkeyup="inputCheck()" verify="�������|LEN<=16"  verifyorder="1" name=AdjSum><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input> <Input class=codeno name=AdjReason onclick="inputCheck()" onkeyup="inputCheck()" ondblclick="return showCodeList('llmedfeeadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('llmedfeeadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
				
            </table>
			<table class= common>
				<TR class= common>
					<TD class= title> ������ע</TD>
				</TR>
				<TR class= common>
					<TD class= input>
					<textarea name="AdjRemark" verify="������ע|len<1000" onclick="inputCheck()" onkeyup="inputCheck()" verifyorder="1" cols="100" rows="2" witdh=25% class="common"></textarea><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
			</table>
		</div>
        <!--�˲�-->
        <Div id= "divLLSubReport2" style= "display: 'none'" >
		    <table class= common>
                <TR class= common >
                    <TD class= title> �˲�����</TD>
                    <TD class= input> <Input class="readonly" readonly name=DefoType></TD>
                    <TD class= title> �˲д���</TD>
                    <TD class= input> <Input class="readonly" readonly name=DefoCode></TD>
                    <TD class= title> �˲м�������</TD>
                    <TD class= input><Input class="readonly" readonly name=DefoeName></TD>
                </TR>
                <TR class= common>
                    <TD class= title> �м��������� </TD>
                    <TD class= input>  <Input class="readonly" readonly name=DefoRate></TD>
					<TD class= title> ʵ�ʸ������� </TD>
                    <TD class= input>  <Input class="common" MAXLENGTH=10 onclick="inputCheck()" onkeyup="inputCheck()" verify="�������|LEN<=10"  verifyorder="1" name=RealRate><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input> <Input class=codeno name=AdjReason1 onclick="inputCheck()" onkeyup="inputCheck()" ondblclick="return showCodeList('llmedfeeadjreason',[this,AdjReason1Name],[0,1]);" onkeyup="return showCodeListKey('llmedfeeadjreason',[this,AdjReason1Name],[0,1]);"><input class=codename name=AdjReason1Name readonly=true><font size=1 color='#ff0000'><font size=1 color='#ff0000'><b>*</b></font></TD></TD>
                </TR>
                
            </table>
			<table class= common>
				<TR class= common>
					<TD class= title> ������ע</TD>
				</TR>
				<TR class= common>
					<TD class= input>
					<textarea name="AdjRemark1" verify="������ע|len<1000" onclick="inputCheck()" onkeyup="inputCheck()" verifyorder="1" cols="100" rows="2" witdh=25% class="common"></textarea><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
			</table>
		</div>
    </div>
    
	<!--����-->
	<Div id= "divLLSubReport3" style= "display: 'none'" >
	       <table class= common>
                <TR class= common >
                    <TD class= title> ԭʼ���</TD>
                    <TD class= input> <Input class="readonly" readonly name=OriSum1></TD>
                    <TD class= title> �������</TD>
                    <TD class= input> <Input class="common" MAXLENGTH=16 onclick="inputCheck()" onkeyup="inputCheck()" verify="�������|LEN<=16"  verifyorder="1" name=AdjSum1><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> ����ԭ��</TD>
                    <TD class= input> <Input class=codeno name=AdjReason2 onclick="inputCheck()" onkeyup="inputCheck()" ondblclick="return showCodeList('llmedfeeadjreason',[this,AdjReason2Name],[0,1]);" onkeyup="return showCodeListKey('llmedfeeadjreason',[this,AdjReason2Name],[0,1]);"><input class=codename name=AdjReason2Name readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD></TD>
                </TR>
                
            </table>
			<table class= common>
				<TR class= common>
					<TD class= title> ������ע</TD>
				</TR>
				<TR class= common>
					<TD class= input>
					<textarea name="AdjRemark2" verify="������ע|len<1000" onclick="inputCheck()" onkeyup="inputCheck()" verifyorder="1" cols="100" rows="2" witdh=25% class="common"></textarea><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
			</table>
		</div>
    </div>
    
    

    <hr>
    <INPUT class=cssButton name="ConclusionSave" VALUE="�޸�ȷ��"  TYPE=button onclick="saveClick()">
    <INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()">
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
