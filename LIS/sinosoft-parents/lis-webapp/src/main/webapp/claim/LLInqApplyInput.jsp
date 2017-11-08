<%
//程序名称：LLInqApplyInput.jsp
//程序功能：发起调查信息录入
//创建日期：2005-05-10
//创建人  ：zhoulei
//更新记录：yuejw
%>
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

	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI"); 
	  
	  String tClmNo = request.getParameter("claimNo");	//赔案号
	  String tCustomerNo = request.getParameter("custNo"); //出险人编码
	  String tCustomerName ="";
//	  String tCustomerName = request.getParameter("custName"); //出险人姓名
//	  tCustomerName =  new String(tCustomerName.getBytes("ISO-8859-1"),"GB2312");
	  String tVIPFlag = request.getParameter("custVip"); //vip标志
	  String tInitPhase = request.getParameter("initPhase"); //提起阶段
	  
	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID	

%>
    <title>调查信息录入</title>
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
    <script src="./LLInqApply.js"></script> 
    <%@include file="LLInqApplyInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
    <!--调查信息-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApply1);"></TD>
            <TD class= titleImg>发起调查</TD>
        </TR>
    </table>
    <Div id= "divLLInqApply1" style= "display: ''" class="maxbox">
        
        <TABLE class=common>
            <tr class=common>
                <td class=title>出险人代码</td>
                <td class= input><Input class="readonly wid" readonly  name=CustomerNo id=CustomerNo></td>
		        <td class=title>出险人姓名</td>
                <td class= input><Input class="readonly wid" readonly  name=CustomerName id=CustomerName></td>
                <td class=title> 调查批次 </td>
                <td class= input><Input class="readonly wid" readonly  name=BatNo id=BatNo></td>
		        <!--<td class=title>VIP标志</td>
                <td class= input><Input class="readonly" readonly  name=VIPFlag></td>  -->
            </tr>        	
            <tr class=common>
                <!--<td class=title> 提起阶段 </td>-->
                <!--<td class= input><Input class=codeno readonly  name="ApplyPhase" ondblclick="return showCodeList('llInitPhase',[this,ApplyPhaseName],[0,1]);" onkeyup="return showCodeListKey('llInitPhase',[this,ApplyPhaseName],[0,1]);"><input class=codename name="ApplyPhaseName" readonly=true></TD>-->
                
                <td class=title> 调查原因 </td>
                <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqReason" id="InqReason" ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"><input class=codename name="InqReasonName" id="InqReasonName" readonly=true></TD>                                 
                <td class=title> 调查机构 </td>
		        <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="InqOrg" id="InqOrg" ondblclick="return showCodeList('stati',[this,InqOrgName],[0,1],null,null,null,1);" onclick="return showCodeList('stati',[this,InqOrgName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('stati',[this,InqOrgName],[0,1],null,null,null,1);"><input class=codename name="InqOrgName" id="InqOrgName" readonly=true></TD> 	
		        <td ><input style="vertical-align:middle" type="checkbox" value="1" name="MoreInq" onclick="MoreInqClick()"><span style="vertical-align:middle">异地调查</span></input></td> 	
            </tr> 
            
            <!--
        	  <TR  class= common>
                <td class=title> 申请人 </td>
                <td class= input><Input class="readonly" readonly  name=Proposer ></td>      
                <td class=title> 申请时间 </td>
                <td class= input><Input class="readonly" readonly  name=ApplyTime ></td>   
                <td class=title> 申请机构 </td>
                <td class= input><Input class="readonly" readonly  name=ApplyOrg ></td>                         
		      </TR>  
		      -->
        </table>        
        <Table class= common>		
                <tr class= common>
	    	        <td class= title> 调查项目 </td>
    		    </tr> 
		        <tr class= common>       
	    	        <td colspan="6" style="padding-left:16px"> <textarea name="InqItem" cols="224"  rows="4" class="common"></textarea></td>
		        </tr>
    	    
	    	    <tr class= common>
	    	        <td class= title> 调查描述 </td>
    		    </tr> 
		        <tr class= common>       
	    	        <td colspan="6" style="padding-left:16px"> <textarea name="InqDesc" cols="224" rows="4" class="common"></textarea></td>
		        </tr>
        </TABLE>
    </Div>
    <table style="display:none">
            <tr>
                <td><input class=cssButton name="AddBatNo" value="申请批次号" type=button onclick="AddBatNoClick()"></td>
                <td><Input class=cssButton name="saveAdd"  value="保      存" type=button onclick="saveClick()"></td> 
                <td><Input class=cssButton name="doClose"  value="返     回" type=button onclick="top.close()"></td> 
            </tr>
        </table>
    <a href="javascript:void(0);" name="AddBatNo" class="button" onClick="AddBatNoClick();">申请批次号</a>
    <a href="javascript:void(0);" name="saveAdd" class="button" onClick="saveClick();">保    存</a>
    
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyGrid);"></TD>
            <TD class= titleImg> 该赔案已经发起的调查信息列表 </TD>
        </TR>
    </Table>       
    <Div  id= "divLLInqApplyGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
             </TR>
         </Table>
         <!--<table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table> --> 
    </Div><a href="javascript:void(0);" name="doClose" class="button" onClick="top.close();">返    回</a>
    <!--保存数据的隐藏表单-->
    <Input type=hidden id="InqOrg2" name="InqOrg2"><!--隐藏的调查机构，传递disabled时机构信息（注：input设置为disabled时，request.getParameter()取不到值）-->
    
    <Input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <!--<Input type=hidden id="BatNo" name="BatNo">   批次号-->    
    <Input type=hidden id="InqNo" name="InqNo"><!--调查序号-->
    <!--<Input type=hidden id="CustomerNo" name="CustomerNo"> 出险人代码-->
    <!--<Input type=hidden id="CustomerName" name="CustomerName"> 出险人姓名-->
    <!-- <Input type=hidden id="VIPFlag" name="VIPFlag">VIP标志-->
    <Input type=hidden id="InitPhase" name="InitPhase"><!--提起阶段-->    
    <Input type=hidden id="LocFlag" name="LocFlag"><!--本地标志-->
        <!--//工作流参数-->
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>			
</body>
</html>
