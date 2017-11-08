<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%> 
<%
//程序名称：EdorManuUWInsuredInput.jsp
//程序功能：保全被保人核保信息界面
//创建日期：2005-06-20 17:58:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");


	String toperator = tGI.Operator;   //记录操作员
	String tmanageCom = tGI.ManageCom; //记录管理机构
	String tComCode = tGI.ComCode; //记录登陆机构
	String tContNo = request.getParameter("ContNo");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tEdorAcceptNo= request.getParameter("EdorAcceptNo");
    String tEdorType= request.getParameter("EdorType");
    String tOtherNo     = request.getParameter("OtherNo");
    String tOtherNoType = request.getParameter("OtherNoType");
    String tEdorAppName = request.getParameter("EdorAppName");
    String tApptype     = request.getParameter("Apptype");
    String tManageCom   = request.getParameter("ManageCom");
    String tPrtNo = request.getParameter("PrtNo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="EdorManuUWInsured.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EdorManuUWInsuredInit.jsp"%>
  <title>被保人信息 </title>
</head>
<body  onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit" action= "./EdorAppManuUWSave.jsp">
<DIV id=DivLPInsured STYLE="display:''">
    <table class= common border=0 width=100%>
			
    	 <tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
			</td>
	        <td class= titleImg align= center>被保人信息</td>
	     </tr>
    </table>
	<div class=maxbox >
    <table  class= common>
        <TR  class= common>
          <TD  class= title>
            客户号码
          </TD>
          <td class= input><Input type="input" class="readonly wid" readonly name=InsuredNo id=InsuredNo></td>
          <TD  class= title>
            姓名
          </TD>
          <td class= input><Input type="input" class="readonly wid" readonly name=Name id=Name></td>
          <TD  class= title>
            性别
          </TD>
          <td class= input><Input type="input" class="readonly wid" readonly name=Sex id=Sex></td>
            
        </TR>
        <TR  class= common>
            <TD CLASS=title>
                出生日期
            </TD>
            <td class= input><Input type="input" class="coolDatePicker" readonly name=InsuredAppAge onClick="laydate({elem: '#InsuredAppAge'});" id="InsuredAppAge"><span class="icon"><a onClick="laydate({elem: '#InsuredAppAge'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

			</td>
            
            <TD CLASS=title>
                国籍
            </TD>
            <td class= input><Input type="input" class="readonly wid" readonly name=NativePlace id=NativePlace></td>
          
	        <TD  class= title id=MainInsured style="display:">与主被保险人关系</TD>
            <td class= input><Input type="input" class="readonly wid" readonly name=RelationToMainInsured id=RelationToMainInsured></td>   
               
	       
       </TR>
	    <TR class= common>
            <TD  class= title>
                职业
            </TD>
             <td class= input><Input type="input" class="readonly wid" readonly name=OccupationCode id=OccupationCode></td>
                
            <TD  class= title>
                职业类别
            </TD>
            <td class= input><Input type="input" class="readonly wid" readonly name=OccupationType id=OccupationType></td>
                            
             <TD  class= title id=MainAppnt   style="display:">与投保人关系</TD>
            <td class= input><Input type="input" class="readonly wid" readonly name=RelationToAppnt id=RelationToAppnt></td>
           
	    </TR>
	   
		</Table>
	</div>
</DIV>

 
<table>
  <tr>
    <td>
    <INPUT VALUE="  被保人健康告知查询  " class=cssButton TYPE=button onclick="queryHealthImpart();"> 
    <INPUT VALUE="  被保人体检资料查询  " class=cssButton TYPE=button onclick="queryHealthReportResult();"> 
    <INPUT VALUE="被保人保额累计提示信息" class=cssButton TYPE=button onclick="amntAccumulate();"> 
    </td>
  </tr>
  <tr>
    <td>
    <INPUT VALUE=" 被保人已承保保单查询 " class=cssButton TYPE=button onclick="queryProposal();"> 
    <INPUT VALUE="被保人未承保投保单查询" class=cssButton TYPE=button onclick="queryNotProposal();"> 
    <INPUT VALUE="被保人既往保全信息查询" class=cssButton TYPE=button onclick="queryEdor()"> 
    <INPUT VALUE="被保人既往理赔信息查询" class=cssButton TYPE=button onclick="queryClaim()"> 
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
 <hr class=line>
<table>
<tr>
  <td>
    <!--<input value="       加费录入       " class=cssButton type=button name= "AddFee"  onclick="showAdd();"> -->
    <!-- <input value="       特约录入       " class=cssButton type=button onclick="showSpec();"> -->
    <input value="   承保结论变更录入    " class=cssButton type=button onclick="NewChangeRiskPlan();">
  </td>
</tr>
</table>

 
<DIV id=DivLPPol STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>险种信息</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanRiskGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>
<br>
<DIV id=DivLPInsuredRelated STYLE="display:'none'">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>连生被保人信息</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanInsuredRelatedGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>
<br >

<Div  id= "divFormerRiskUWInfo" style= "display: 'none'">
	       <table>
			<tr>
	           	<td class= titleImg> 原险种级核保结论 </td>
			</tr>
	       </table>      		 
	       <Div  id= "divFormerRiskUW" style= "display: ''">
			 <table  class= common>
				<tr class=common>
					<td class=title> 原险种核保结论 </td>
					<td class=input><Input class="codeno" name=FormerUWState id=FormerUWState readonly=true><input class=codename name=FormerUWStateName id=FormerUWStateName readonly=true></td>
	            	<TD class= title>  </TD>
	            	<td class= input></td>            	                          
                    <td class=input><Input class="common wid" type=hidden name=FormerUWPopedomCode id=FormerUWPopedomCode></td><!--加这个只是为了跟下面对齐-->
				</tr>	
				<tr class=common>
					<TD class=title colspan=6 > 原险种核保意见 </TD>
				</tr>
				<tr class=common>
					<TD  class=input colspan=6 ><textarea name="FormerUWIdea" id=FormerUWIdea readonly cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
				</tr>
			  </table>
	        </DIV>
</DIV>
<Div  id= "divEdorManuUWResultInfo" style= "display: ''">
	       <table>
			<tr>
	           	<td class= titleImg> 险种级核保结论 </td>
			</tr>
	       </table>      		 
	       <Div  id= "divEdorManuUWInfo" style= "display: ''">
			 <table  class= common>
				<tr class=common>
					<td class=title> 核保结论 </td>
					<td class=input><Input class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" name=EdorUWState id=EdorUWState ondblclick="initUWState(this, edoruwstateName)" onkeyup="onKeyUpUWState(this, edoruwstateName);"><input class=codename name=edoruwstateName id=edoruwstateName readonly=true></td>
					<td class=title> 
					<div id = "divUwDelayTitle" style= "display: 'none'">
					延长时间
					</div> 
					</td>
					<td class=input>
					<div id = "divUwDelay" style= "display: 'none'">
					<Input class="multiDatePicker" name=UWDelay id=UWDelay >
					</div>
					</td>
					<!--td class=title> 上报核保 </td-->
					<td class=input><Input class="common wid" type=hidden name=UWPopedomCode id=UWPopedomCode></td>	
				</tr>	
				<tr class=common>
					<TD class=title colspan=6 > 保全核保意见 </TD>
				</tr>
				<tr class=common>
					<TD  class=input colspan=6 ><textarea name="UWIdea" id=UWIdea cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
				</tr>
			  </table>
	        </DIV>
	          
		    <INPUT VALUE=" 确 定 " class=cssButton TYPE=button onclick="UWSubmit();">
		    <INPUT VALUE=" 清 空 " class=cssButton TYPE=button onclick="UWcancel();">
		    <INPUT VALUE=" 返 回 " class=cssButton TYPE=button onclick="returnParent();">
</DIV>
<div id="divReturn" style= "display: 'none'">
<INPUT VALUE="         返回         " class=cssButton TYPE=button onclick="returnParent();">
</div>

<!--隐藏域-->
       <Input id=PolNo name=PolNo type=hidden>
       <Input id=ContNo name=ContNo type=hidden>
       <Input id=MissionID name=MissionID type=hidden>
       <Input id=SubMissionID name=SubMissionID type=hidden>
       <Input id=EdorNo name=EdorNo type=hidden>
       <Input id=EdorAcceptNo name=EdorAcceptNo type=hidden>
       <Input id=EdorType name=EdorType type=hidden >
       <input id=UWType type=hidden name= "UWType">
       <input id=ActionFlag type=hidden name= "ActionFlag"> 
       <Input id=OtherNo name=OtherNo type=hidden>
       <Input id=OtherNoType name=OtherNoType type=hidden>
       <Input id=EdorAppName name=EdorAppName type=hidden>
       <Input id=Apptype name=Apptype type=hidden>
       <Input id=ManageCom name=ManageCom type=hidden>
       <Input id=PrtNo name=PrtNo type=hidden>
       <Input id=InsuredSumLifeAmnt name=InsuredSumLifeAmnt type=hidden>
       <Input id=InsuredSumHealthAmnt name=InsuredSumHealthAmnt type=hidden>
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
