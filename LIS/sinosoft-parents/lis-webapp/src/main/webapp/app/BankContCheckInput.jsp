<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2006-02-14 11:51:42
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");

	}
	catch( Exception e )
	{
		tPNo = "";
	}

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "5";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "5";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script language="javascript">
	
	//从服务器端取得数据:
	var	tMissionID = "<%=request.getParameter("MissionID")%>";
	var	tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var NoType = "<%=request.getParameter("NoType")%>"; 
	var ManageCom = "<%=request.getParameter("ManageCom")%>";
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var type = "<%=request.getParameter("type")%>";
	//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == "null") BQFlag = "0";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	if (ScanFlag == "null") ScanFlag = "0";
	//保全调用会传险种过来
	var BQRiskCode = "<%=request.getParameter("riskCode")%>";
	//添加其它模块调用处理
	var LoadFlag ="<%=tLoadFlag%>"; //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	var ContType = 1;
	//Q：Auditing 是团体人工核保时，查询个单明细时作为标志 Auditing=1
	var Auditing = "<%=request.getParameter("Auditing")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	if(Auditing=="null") { Auditing=0; }    
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var checktype = "1";

</script>

 
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
   <script src="../common/javascript/VerifyInput.js"></script>
  <script src="../common/javascript/jquery.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
  <script src="../common/javascript/jquery.easyui.min.js"></script>

  <script src="../common/javascript/Signature.js"></script>
  <script src="ProposalAutoMoveNew.js"></script>
  <script src="TabAutoScroll.js"></script>
  <script src="BankContCheck.js"></script> 
  <%@include file="BankContCheckInit.jsp"%>  
  <script src="../common/javascript/MultiCom.js"></script>
<%
if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
{
%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="../common/javascript/ScanPicView.js"></script>
  <script language="javascript">window.document.onkeydown = document_onkeydown;</script>
<%
}
%>
  
</head>


<body  onload="initForm();initElementtype();window.document.ondblclick=AutoTab;" >
  <form action="./BankContSave.jsp" method=post name=fm id="fm" target="fraSubmit"> 

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                      引入合同信息录入控件         
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="BankContPage.jsp"/>  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                   多业务员MultiLine            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <div id="DivMultiAgent" style="display:none">
		<table>
			<tr>
			    <td class=common>
			        <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
			    </td>
			    <td class= titleImg>其他业务员信息</td>
			</tr>
		</table>
   
		<div id="divMultiAgent1" style="display: ">
			<table class="common">
				<tr class="common">
					<td style="text-align:left" colSpan="1"><span id="spanMultiAgentGrid"></span></td>
				</tr>
			</table>
		</div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                     业务员告知            
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

  <div id="DivAgentImpart" style="display:none">
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divAgentImpart1);">
            </td>
            <td class= titleImg>业务员告知</td>
        </tr>
    </table>
    <div id="divAgentImpart1" style="display: ">
      <table class="common">
        <tr class="common">
          <td class="common">
            <span id='spanAgentImpartGrid'></span>
          </td>
        </tr>
      </table>
    </div>
  </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  
 
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               引入投保人录入控件界面
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
<jsp:include page="BankComplexAppnt.jsp"/>
<!-- 
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <!-- 告知信息部分（列表） -->
    <div id="DivLCImpart" style="display:none">
		<table>
		  <tr>
		      <td class=common>
		          <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCImpart1);">
		      </td>
		      <td class= titleImg>投保人告知信息</td>
		  </tr>
		</table>
		<div id=DivIncome0 style="display: ">
			 <table  class="common">  
			   <tr class="common" style="display:none">
			     	<td class="title">您每年固定收入</td>
			      	<td>
			        	<input class=common name=Income0  verify="投保人收入|NUM&LEN<=8" verifyorder='1' >  万元
			      	</td>   
			     	<td  class="title">主要收入来源：</td>             
			     	<td class="input">
			       		<input class="codeno" name="IncomeWay0" verify="主要收入来源|LEN<4"  verifyorder="1" ondblclick="return showCodeList('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName0],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName0"  readonly="true">
			     	</td>              
			   </tr> 
			</table> 
		</div>  
		<div id="divLCImpart1" style="display: ">
		  <table class="common">
		      <tr class="common">
		          <td style="text-align:left" colSpan="1">
		              <span id="spanImpartGrid"></span>
		          </td>
		      </tr>
		  </table>
		</div>
    </div>
 
	<table class=common style="display:none">
	    <tr  class= common> 
	      <td  class= common>其它声明（200个汉字以内）</TD>
	    </tr>
	    <tr  class= common>
	      <td  class= common>
	        <textarea name="Remark1" verify="其他声明|len<800" verifyorder="1" cols="110" rows="2" class="common" >
	        </textarea>
	      </td>
	    </tr>
    </table>

    
    <div  id= "divButton" style= "display:none">
      <span id="operateButton" >
       	<table  class=common align=center>
       		<tr align=right>
       			<td class=button >
       				&nbsp;&nbsp;
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return submitForm();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
       			</td>
       			<td class=button width="10%" align=right>
       				<input class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">
       			</td> 
       		</tr>
       	</table>
      </span>
    </div>

  </div>
  <hr class=line> 

    <!--将被保人信息与投保人界面合并 hanlin 20050416-->
    <div  id= "divFamilyType" style="display:none">
      <table class=common>
        <tr class=common>
          <TD  class= input>
            <!--Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();"-->
            <input type="hidden" name=FamilyType value="1">
          </TD>
        </tr>
      </table>
    </div>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               被保人列表MultiLine
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    
    <div  id= "divTempFeeInput" style= "display: "> 
    	<table class=common>
    	  <tr>
    		  <td style="text-align: left" colSpan=1>
	  				<span id="spanInsuredGrid" > 
	  				</span>
	  			</td>
    		</tr>
    	</Table>
    </div>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                               引入被保人录入控件
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <jsp:include page="BankComplexInsured.jsp"/>

<hr class=line>
    <div id="DivLCImpartInsured" STYLE="display: ">
    <!-- 告知信息部分（列表） -->
      <table>
        <tr>
          <td class=common>
            <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpartInsured1);">
          </td>
          <td class= titleImg>被保险人告知信息</td>
          <td><Input type="button" class="cssButton" name="insuworkimpart" value="被保人职业告知信息"></td>
          <td><Input type="button" class="cssButton" name="insuhealthmpart" value="被保人健康告知信息"></td>
        </tr>
      </table>
	  <div id=DivIncome style="display: ">
		 <table  class="common">  
		   <tr class="common" style="display:none">
		     <td class="title">您每年固定收入</td> 
		      <td>
		        <input class=common name=Income verify="被保险人收入|NUM&LEN<=8" verifyorder='2' >  万元
		      </td>
		     <td  class="title">主要收入来源：</td>             
		     <td class="input">
		       <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
		       <input class="codeno" name="IncomeWay" verify="被保人主要收入来源|LEN<4"  verifyorder="2" ondblclick="return showCodeList('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');" onkeyup="return showCodeListKey('incomeway', [this,IncomeWayName],[0,1],null,null,null,null,'220');"><Input class="codename" name="IncomeWayName" readonly="true">
		     </td>              
		   </tr> 	
		 </table> 
	  </div>     
      <div id="divLCImpartInsured1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align: left" colSpan=1>
            	<span id="spanImpartInsuredGrid" ></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
<hr class=line>    
     <!-- 被保人险种信息部分 -->
    <div id=DivLCPol STYLE="display: ">
      <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
          </td>
          <td class= titleImg>被保险人险种信息</td>
          <td>
			<Input class="cssButton" name=RiskButton type="button" value="被保险人险种信息" onclick="goToPic(0); top.fraPic.scrollTo(83, 541);">
		  </td>
        </tr>
      </table>
    
      <div  id= "divLCPol1" style= "display:  ">
        <table  class= common>
          <tr  class= common>
            <td style="text-align:left" colSpan=1>
              <span id="spanPolGrid" ></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    
    <!-- 受益人信息部分（列表） -->
     <div id="divLCBnf" style="display: "> 
	    <table>
	      <tr>
	        <td class=common>
			  	  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
	      	</td>
	        <td class= titleImg>受益人信息</td>
	        <td>
				<Input class="cssButton" name=BnfButton type="button" value="受益人信息" onclick="goToPic(0); top.fraPic.scrollTo(93, 792); ">
		  	</td>
	      </tr>
	    </table>
		<div id= "divLCBnf1" style= "display:  " >
	      <table class= common>
	        <tr class= common>
	      	  <td style="text-align: left" colSpan="1">
			  		<span id="spanLCBnfGrid"></span>
			  </td>
			</tr>
		  </table>
		</div>
	 </div>
	 
    <hr class=line>
    
    
 <!--
  ###############################################################################
                              随动定制按钮 
  ###############################################################################
-->
    
    <div id="autoMoveButton" style="display:none">
	    <input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove('11');" class="cssButton">
	    <input type="button" name="Next" value="下一步" onclick="location.href='ContInsuredInput.jsp?LoadFlag='+LoadFlag+'&checktype=1&prtNo='+prtNo+'&scantype='+scantype" class="cssButton">
      <input class=cssButton id="queryintoriskbutton2" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
      <input value="返  回" class="cssButton" type="button" onclick="top.close();">
      <input type=hidden name="autoMoveFlag" id="autoMoveFlag">
      <input type=hidden name="autoMoveValue" id="autoMoveValue">
      <input type=hidden name="pagename" id="pagename" value="35">
    </div>

<!--###############################################################################
                              复核按钮 
  ###############################################################################
-->
    <div id = "divApproveContButton" style = "display:none;float: left">
    	<table>
    	  <tr>
    	    <td>
    	    	
            <input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
            <input class=cssButton id="demo1" name="AppntChkButton" VALUE=投保人校验 TYPE=hidden onclick='AppntChk();'>
            <input class=cssButton id="demo2" name="InsuredChkButton"  VALUE=被保人校验 TYPE=hidden onclick='InsuredChk();' disabled="disabled">
            <input class=cssButton id="riskbutton5" VALUE="强制进入人工核保" TYPE=button onclick="forceUW();">
            <input class=cssButton id="intoriskbutton2" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();">
          </td>
        </tr>
        <tr>
          <td>
          	<input class=cssButton VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();">
          	<input class=cssButton VALUE="问题件查询" name="ApproveQuestQuery" TYPE=button onclick="QuestQuery();">	
    	    <input class=cssButton name="NotePadButton5" VALUE="记事本查看" TYPE=button onclick="showNotePad();">
            <input class=cssButton id="Donextbutton4" VALUE=" 复  核  完  毕 " TYPE=button onclick="inputConfirm();">
          </td>
        </tr>
      </table>
    </div>
    <div id = "divCustomerUnionButton" style = "display: ">
		<table>
			<tr><td>
				<!--input class=cssButton id="Donextbutton5" VALUE="问题件录入" TYPE=button onClick="QuestInput();"-->
				<!--input class=cssButton id="intoriskbutton" VALUE="进入险种信息" TYPE=button onclick="intoRiskInfo();"-->

				<input id="demo1" name="AppntChkButton"  class=cssButton VALUE="投保人校验" TYPE=button onclick='GoToAppnt();'>
				<input id="demo2" name="InsuredChkButton" class=cssButton VALUE="被保人校验" TYPE=button  onclick='GoToInsured();'>
			</td></tr>
			<tr><td>
				<!--input class=cssButton VALUE="影像件查询" TYPE=button onclick="QuestPicQuery();"-->	
			    <!--input class=cssButton VALUE="问题件查询" name="ApproveQuestQuery" TYPE=button onclick="QuestQuery();"-->
				<input class=cssButton name="NotePadButtona" VALUE="记事本查看" TYPE=button onclick="showNotePad();">
				<input class=cssButton id="Donextbutton4" VALUE=" 客户号手工合并完毕 " TYPE=button onclick="UnionConfirm();">
			</td></tr>
		</table>
	</div>
	
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV\
                               隐藏控件位置                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
    <div  id= "HiddenValue" style="display:none;float: right">
    	<input type="hidden" id="fmAction" name="fmAction">
    	<!-- 工作流任务编码 -->
			<input type="hidden" id="WorkFlowFlag" name="WorkFlowFlag">
			<input type="hidden" name="MissionID" value= "">
      <input type="hidden" name="SubMissionID" value= "">
      
      <input type="hidden" name="ActivityID" value= "">
      <input type="hidden" name="NoType" value= "">
      <input type="hidden" name="PrtNo2" value= "">
      
      <input type="hidden" name="ContNo" value="" >
      <input type="hidden" name="ProposalGrpContNo" value="">
      <input type="hidden" name="SelPolNo" value="">
      <input type="hidden" name="BQFlag">  
      <input type='hidden' name="MakeDate">
      <input type='hidden' name="MakeTime">
      <input type='hidden' id="ContType" name="ContType">
      <input type='hidden' id="GrpContNo" name="GrpContNo" value="00000000000000000000">
      
      <input type="hidden" id="RollSpeedSelf" name="RollSpeedSelf">
      <input type="hidden" id="RollSpeedBase" name="RollSpeedBase">
      <input type="hidden" id="RollSpeedOperator" name="RollSpeedOperator">   
      <input type="hidden" id="Operator" name="Operator">
    </div>           
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->


  
  
</form>


  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
