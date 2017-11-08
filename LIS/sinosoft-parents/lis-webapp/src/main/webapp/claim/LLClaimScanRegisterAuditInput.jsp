
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	String tMissionID = request.getParameter("MissionID");  //����������ID
	String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
	String tActivityID = request.getParameter("ActivityID");  //������������ID
    String CurrentDate = PubFun.getCurrentDate();
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="LLClaimScanRegisterAudit.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <%@include file="LLClaimScanRegisterAuditInit.jsp"%>
</head>

<body  onload="initForm()" >

<form action="./LLClaimScanRegisterAuditSave.jsp"  method=post name=fm id=fm target="fraSubmit">
    <!--������Ϣ-->
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> ������Ϣ </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" class=maxbox1 style= "display: ''">
        <table>
            <tr>
                <td><input class=cssButton name=QueryPerson value="�ͻ���ѯ" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();"></td>
            </tr>
        </table>
        <!--��������Ϣ��-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>

                    <TD class= title> �������</TD>
                    <TD class= input> <Input class=codeno name=IssueConclusion id=IssueConclusion CodeData="0|3^01|����ͨ��^02|���ϲ����˻�^03|�����˻�" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('IssueConclusion', [this,IssueConclusionName],[0,1])" onkeyup="return showCodeListKeyEx('IssueConclusion', [this,IssueConclusionName],[0,1])" onfocus= "choiseIssueConclusion();">
					<input class=codename name=IssueConclusionName id=IssueConclusionName readonly=true></TD>       
					<TD class= title> </TD>
                    <TD class= input> </TD>
 				</TR>
             </table>
        </span>
    </div>
    <hr class=line>
   <Div id= "divAffix" style= "display: none">
    <span id="operateButton" >
        <table>
            <tr>
                <td><INPUT class=cssButton name="CreateNote" VALUE="���䵥֤"  TYPE=button onclick="showRgtAddMAffixListClmScan()"></td>
            </tr>
        </table>
    </span>
	</div>
  <Div id= "divAccDesc" style= "display: none">
   <span id="operateButton1" >
            <table class= common>
                <TR  class= common>
                    <TD  class= title> �˻�˵�� </TD>
                </tr>
                <TR class= common>
                    <TD class= input> 
                        <span id="spanText3" style= "display: ''">
                            <textarea name="AccDesc" id=AccDesc cols="100" rows="2" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>

            </table>
    </span>
	</div>
    <hr class=line>
    <table>
        <tr>
            <td><INPUT class=cssButton name="RptConfirm" VALUE="������۱���"  TYPE=button onclick="IssueConclusionSave()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
  		</tr>
    </table>
    <input type=hidden id="Operate" name="Operate">
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="CustomerNo" name="customerNo"><!--�����˿ͻ���-->
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
