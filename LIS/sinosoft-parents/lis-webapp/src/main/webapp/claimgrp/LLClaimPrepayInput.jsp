<%
//**************************************************************************************************
//Name：LLClaimPrepayInput.jsp
//Function：“预付管理”主界面
//Author：yuejw
//Date: 2005-7-5 16:00
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
	String tClmNo=request.getParameter("ClmNo"); //赔案号
	String tCustomerNo=request.getParameter("CustomerNo"); //调查序号      
	String tActivityID=request.getParameter("ActivityID"); //活动ID
	String tMissionID =request.getParameter("MissionID");  //工作流任务ID
	String tSubMissionID =request.getParameter("SubMissionID");  //工作流子任务ID	
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrepay.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPrepayInit.jsp"%>
    <title> 预付管理</title>
</head>
<body  onload="initForm();" >
<form  method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimDetailGrid);"></td>
            <td class= titleImg> 给付保项信息列表 </td>
        </tr>
    </table>
    <Div id= "divLLClaimDetailGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLClaimDetailGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>   
   
    </div>    

	<hr>
	    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLPrepayDetailInfo);"></td>
            <td class= titleImg> 保项的预付金额的处理 </td>
        </tr>
    </table>
   <Div id= "divLLPrepayDetailInfo" style= "display: ''" align = center>

        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLPrepayDetailGrid" ></span> </TD>
            </TR>
        </Table>
        <Table>
            <tr>
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick=" turnPage2.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick=" turnPage2.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick=" turnPage2.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick=" turnPage2.lastPage();"></td>
            </tr>
        </Table>
    </Div>    
    <table >
        <TR class= common>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayAdd" disabled VALUE="增  加" onclick="LLPrepayDetailAdd();"></TD>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayCancel" disabled VALUE="取  消" onclick="LLPrepayDetailCancel();"></TD>
        </TR>
    </Table>      
   <Div id= "divLLPrepayDetail" style= "display: 'none'" align = center>
		<table class= common>
		    <TR class= common>
	            <TD class= title> 输入预付金额 </TD>
	            <TD class= input><Input class= common name="PrepaySum" ></TD>		        
	            <TD class= title>支付方式</TD>
	            <td class= input><Input class=codeno readonly=true name="CasePayMode" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name="CasePayModeName" readonly=true></TD>
	            <TD class= title></TD>
	            <TD class= input></TD>
		    </TR>
		     <TR class= common>
            	<TD> <Input TYPE=button class=cssButton VALUE="保  存" onclick="LLPrepayDetailSave();"></TD>
            </TR>
		</table>
   </Div>      
	<hr>
	<Table>
        <TR>
            <TD> <Input TYPE=button class=cssButton name="Bnf" VALUE="受益人分配" onclick="showBnf();"></TD>        	
            <TD> <Input TYPE=button class=cssButton name="PrepayCofirm" VALUE="预付确认" onclick="LLClaimPrepayCofirm();"></TD>
            <TD> <Input TYPE=button class=cssButton VALUE="返   回" onclick="Return();"></TD>  
            <td><input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
            <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>                  
        </TR>
    </Table>
	
    <!--//保存数据用隐藏表单区-->
    <Input type=hidden name="Operator" >    <!--//当前操作人-->
    <Input type=hidden name="ComCode" >     <!--//当前机构-->
    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
    <Input type=hidden name="PrepayNo" >     <!--//预付批次号,每次进入预付页面初始化时生成，在处理过程中一直不变-->
    <!--//保存响应“ 给付保项信息列表”点击单选钮的函数时传入的数据-->
	<input type=hidden id="ClmNo"   name= "ClmNo">
	<input type=hidden id="CaseNo" name= "CaseNo">    
	<input type=hidden id="PolNo"   name= "PolNo">
	<input type=hidden id="GetDutyKind" name= "GetDutyKind">
	<input type=hidden id="GetDutyCode" name= "GetDutyCode">
	<input type=hidden id="CaseRelaNo" name= "CaseRelaNo">
	<input type=hidden id="GrpContNo"   name= "GrpContNo">
	<input type=hidden id="GrpPolNo" name= "GrpPolNo">    
	<input type=hidden id="ContNo"   name= "ContNo">
	<input type=hidden id="KindCode" name= "KindCode">
	<input type=hidden id="RiskCode" name= "RiskCode">
	<input type=hidden id="RiskVer" name= "RiskVer">    
	<input type=hidden id="DutyCode" name= "DutyCode">    
	
    <!--//保存上页传入的数据,用于“预付确认”时查询节点数据,为生成新节点准备数据-->  
    
	<input type=hidden id="tRptNo"   name= "tRptNo">
	<input type=hidden id="tRptorState"   name= "tRptorState">
	<input type=hidden id="tCustomerNo"   name= "tCustomerNo">
	<input type=hidden id="tCustomerName"   name= "tCustomerName">
	<input type=hidden id="tCustomerSex"   name= "tCustomerSex">
	<input type=hidden id="tAccDate"   name= "tAccDate">
	<input type=hidden id="tRgtClass"   name= "tRgtClass">
	<input type=hidden id="tRgtObj"   name= "tRgtObj">
	<input type=hidden id="tRgtObjNo"   name= "tRgtObjNo">
	<input type=hidden id="tPopedom"   name= "tPopedom">
	<input type=hidden id="tPrepayFlag"   name= "tPrepayFlag">
	<input type=hidden id="tAuditer"   name= "tAuditer">	
	<input type=hidden id="tComeWhere"   name= "tComeWhere">
	<input type=hidden id="tMngCom"   name= "tMngCom">
	<Input type=hidden id="tComFlag" name= "tComFlag">     <!--//权限跨级标志-->
	<input type=hidden id="tMissionID"   name= "tMissionID">
	<input type=hidden id="tSubMissionID" name= "tSubMissionID">
	<input type=hidden id="tActivityID" name= "tActivityID">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>	
