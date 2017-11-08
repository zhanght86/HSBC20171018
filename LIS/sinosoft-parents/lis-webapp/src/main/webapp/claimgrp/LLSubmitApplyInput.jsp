<%
//程序名称：LLSubmitApplyInput.jsp
//程序功能：发起呈报信息录入,并建立呈报节点
//创建日期：2005-05-10
//创建人  ：yuejw
//更新记录： 
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI"); 	  
	  String tClmNo = request.getParameter("claimNo");	//赔案号
	  String tCustomerNo = request.getParameter("custNo"); //出险人编码	  
	  String tCustomerName = request.getParameter("custName"); //出险人姓名
	  tCustomerName =  new String(tCustomerName.getBytes("ISO-8859-1"),"GB2312");
	  String tVIPFlag = request.getParameter("custVip"); //vip标志
	  
	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID	
%>	
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLSubmitApply.js"></SCRIPT> 
    <%@include file="LLSubmitApplyInit.jsp"%>
        
</head>
<body onload="initForm()">
<form name=fm id=fm target=fraSubmit method=post>

	  <Table>
         <TR>
              <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubmitApplyForm);"></TD>
              <TD class= titleImg> 发起呈报信息 </TD>
         </TR>
    </Table>
    <Div  id= "divLLSubmitApplyForm" style= "display: ''" class="maxbox1">    
        <TABLE class=common>
        	<tr class=common>
                <td class= title> 客户号码 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=CustomerNo id=CustomerNo ></td>      
                <td class= title> 客户姓名 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=CustomerName id=CustomerName ></td>      
                <!-- <td class= title> VIP标志 </td>
                <td class= input><Input type="input" class="readonly" readonly name=VIPFlag ></td>    -->
            </tr>              
        </TABLE> 
        <Table class= common>     
            <tr class= common>           	               
                <td class= title> 呈报描述 </td>
            </tr> 
            <tr class= common>                  
                <td colspan="6" style="padding-left:16px"><textarea name="SubDesc" cols="198" rows="4" witdh=25% class="common" ></textarea></td>
            </tr>
        </TABLE> 
        <!--<Input class=cssButton value=" 保 存 " type=button onclick="saveClick()">     
        <Input class=cssButton value=" 返 回 " type=button onclick="top.close()"> --> 
    </Div>
    <a href="javascript:void(0);" class="button" onClick="saveClick();">保    存</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    <%
    //******************
    //保存数据的隐藏表单
    //******************
    %>    
    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--登陆信息中的管理机构-->
    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="SubType" name="SubType"><!-- 呈报类型-->
    <Input type=hidden id="SubPer" name="SubPer"><!--呈报人-->
    <Input type=hidden id="SubDate" name="SubDate"><!--呈报日期-->
    <Input type=hidden id="SubDept" name="SubDept"><!--呈报机构-->    
    <Input type=hidden id="SubRCode" name="SubRCode"><!--呈报原因-->    
    <Input type=hidden id="SubCount" name="SubCount"><!--呈报次数-->    
    <Input type=hidden id="InitPhase" name="InitPhase"><!--提起阶段-->
    <Input type=hidden id="SubState" name="SubState"><!--呈报状态-->  
     <!--登陆信息中的管理机构的前四位代码，用于填充呈报节点中机构-->
    <Input type=hidden id="MngCom" name="MngCom">
    <Input type=hidden id="FilialeDirector" name="FilialeDirector"><!--分公司主任核赔员-->
    
    <!--//工作流参数-->
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
</Form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>