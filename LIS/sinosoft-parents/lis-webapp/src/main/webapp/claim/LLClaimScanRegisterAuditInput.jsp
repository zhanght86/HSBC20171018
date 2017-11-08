
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tClmNo = request.getParameter("claimNo");	//赔案号
	String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
	String tActivityID = request.getParameter("ActivityID");  //工作流子任务ID
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
    <!--报案信息-->
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> 初审信息 </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" class=maxbox1 style= "display: ''">
        <table>
            <tr>
                <td><input class=cssButton name=QueryPerson value="客户查询" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>
            </tr>
        </table>
        <!--出险人信息表单-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>

                    <TD class= title> 初审结论</TD>
                    <TD class= input> <Input class=codeno name=IssueConclusion id=IssueConclusion CodeData="0|3^01|初审通过^02|材料不齐退回^03|其它退回" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('IssueConclusion', [this,IssueConclusionName],[0,1])" onkeyup="return showCodeListKeyEx('IssueConclusion', [this,IssueConclusionName],[0,1])" onfocus= "choiseIssueConclusion();">
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
                <td><INPUT class=cssButton name="CreateNote" VALUE="补充单证"  TYPE=button onclick="showRgtAddMAffixListClmScan()"></td>
            </tr>
        </table>
    </span>
	</div>
  <Div id= "divAccDesc" style= "display: none">
   <span id="operateButton1" >
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 退回说明 </TD>
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
            <td><INPUT class=cssButton name="RptConfirm" VALUE="初审结论保存"  TYPE=button onclick="IssueConclusionSave()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
  		</tr>
    </table>
    <input type=hidden id="Operate" name="Operate">
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="CustomerNo" name="customerNo"><!--出险人客户号-->
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
