<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
var tEdorAcceptNo = "<%=request.getParameter("EdorAcceptNo")%>";
var tContNo = "<%=request.getParameter("ContNo")%>";
var tEdorNo = "<%=request.getParameter("EdorNo")%>";
var tEdorType = "<%=request.getParameter("EdorType")%>";
var tPrtSeq = "<%=request.getParameter("PrtSeq")%>";
var tMissionID = "<%=request.getParameter("MissionID")%>";
var tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
var tActivityID = "<%=request.getParameter("ActivityID")%>";


</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="BQUWConfirm.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="BQUWConfirmInit.jsp"%>
  <title>保全二核结果确认 </title>
</head>
<body  onload="initForm();" >
  <form action="./EdorUWF1PSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <Div  id= "divInfo" style= "display: ''">
	<div  class="maxbox1">
        <TABLE class=common>
        <!-- 显示保全受理的基本信息,包括变更金额 以供参考 -->
            <tr class=common>
                <td class=title5> 保全受理号 </td>
                <td class= input5><Input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                <td class=title5> 保单号 </td>
                <td class= input5><Input type="input" class="readonly wid" readonly name=ContNo id=ContNo></td>                
            </tr>           
        </TABLE>
	</div>
         <Div  id= "divAppUWLable" style= "display: ''">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppUWInfo);">
                    </td>
                    <td class= titleImg> 保全核保结论 </td>
                </tr>
               </table>
           </DIV>
           <Div  id= "divAppUWInfo" style= "display: ''" class="maxbox1">
             <table  class= common>
                <tr class=common>
                    <td class=title5> 核保结论 </td>
                    <td class=input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name=AppUWState id=AppUWState disabled=true readonly ondblclick="showCodeList('edorappstate',[this,AppUWStateName],[0,1])" onclick="showCodeList('edorappstate',[this,AppUWStateName],[0,1])" onkeyup="showCodeListKey('edorappstate',[this,AppUWStateName],[0,1])"><input class=codename name=AppUWStateName id=AppUWStateName readonly=true disabled=true></td>    
                    <td class=title5></td> 
					<td class=input5></td>               
                </tr></table>
				<table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > 核保意见 </TD>
                </tr>
                <tr class=common>
                    <TD  colspan="6" style="padding-left:16px" >
					<textarea name="AppUWIdea" id="AppUWIdea" cols="199%" rows="4" witdh=100% class="common" readOnly=true disabled=true></textarea></TD>
                </tr>
              </table>
          </DIV>
     </Div> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 客户意见
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <!--<INPUT VALUE="首  页" class= cssButton TYPE=button onclick="getFirstPage();">
      <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="getNextPage();">
      <INPUT VALUE="尾  页" class= cssButton TYPE=button onclick="getLastPage();">-->
  	</div>
  	<div id="showBtn">
      <INPUT VALUE="保存"  class= cssButton TYPE=button onclick="saveData();">
      <INPUT VALUE="修改"  class= cssButton TYPE=button onclick="updateData();">
      <INPUT VALUE="二核结果确认完毕"   class= cssButton TYPE=button onclick="confirmOk();">
    </div>
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<!-- <input type=hidden id="ContNo" name="ContNo">  -->
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="SubMissionID" name="SubMissionID">
  	<input type=hidden id="ActivityID" name="ActivityID">
  	<!-- <input type=hidden id="EdorNo" name="EdorNo">  -->
  	<input type=hidden id="EdorType" name="EdorType">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="fmAction" name="fmAction">
  	<input type="hidden" name="ManageCom" id=ManageCom>
  	<input type="hidden" name="AppntName" id=AppntName>
    </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
