<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPNo = "";
	String tBussNoType = "";
	try
	{
		tPNo = request.getParameter("PNo");
		tBussNoType = request.getParameter("BussNoType");
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//LoadFlag   3-- 异常件
//           4-- 抽查件
 	
 		String tLoadFlag = ""; 
		tLoadFlag = request.getParameter( "LoadFlag" );
		loggerDebug("WbProposalEasyScanInput","tLoadFlag"+tLoadFlag);
	 

%>
<head >
<script>
  var loadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
  var LoadFlag = loadFlag;
  var prtNo = "<%=request.getParameter("prtNo")%>";　
  var	MissionID = "<%=request.getParameter("MissionID")%>";
	var	SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var BussNoType = "<%=request.getParameter("BussNoType")%>";
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<script src="../common/javascript/EasyQuery.js"></script>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	
	<%@include file="WbProposalInit.jsp"%>
	<SCRIPT src="WbProposalInput.js"></SCRIPT>
	<SCRIPT src="ProposalAutoMove3.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>		

  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
</head>

<body  onload="initForm(); " >
  <form action="./WbProposalSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    　
    <!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      引入合同信息录入控件         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="WBContPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->      
<hr size=3 noshade>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               引入投保人录入控件界面
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="WbAppntPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<hr size=3 noshade>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               引入被保人录入控件
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="WbInsuredPage.jsp"/>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<hr size=3 noshade>
    <!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                     客户告知信息            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <Div id="DivImpart" style="display:">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divImpart);">
            </td>
            <td class= titleImg>
                客户告知<font color="maroon" >（告知内容请用/分隔）</font>
            </td>
        </tr>
    </table>
    <div id="divImpart" style="display:">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanImpartGrid'>
            </span>
          </td>
        </tr>
      </table>
    </div>
  </Div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
--> 
       
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>
    <!------------------------------------------------>    
   <!-- 
    可以选择的责任部分，该部分始终隐藏
	<Div  id= "divDutyGrid" style= "display: 'none'">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		确定是否需要责任信息
	</Div>
	 -->
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
		<input  type=hidden id="fmLoadFlag" name="fmLoadFlag">
		<input type=hidden name="DealType" value="<%=request.getParameter("DealType")%>" >
		<input type=hidden name="aftersave" value="0"   >
		<!-- 工作流任务编码 -->
		<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
		<input type="hidden" name="MissionID" value= "">
    <input type="hidden" name="SubMissionID" value= "">
    <input type="hidden" name="BussNoType" value= "">
    <input type="hidden" name="ActivityID" value= "">
    <input type="hidden" name="ProposalContNo" value= "">
    <input type="hidden" name="ProposalNo" value= ""><!-- 合同号 -->
    <input type="hidden" name="InsuredNum" value= "0">
    <input type="hidden" name="MainRiskNum1" value= "0">
    <input type="hidden" name="MainRiskNum2" value= "0">
    <input type="hidden" name="MainRiskNum3" value= "0">
  
  <Div  id= "divButton" style= "display: ''">
  <br>
  <%@include file="./ProposalOperateButtonSpec.jsp"%>
  <input class=cssButton id="11" name="deleteCont" disabled VALUE=" 删除已保存信息 " TYPE=button onClick="resetCont();">
  </Div> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>


