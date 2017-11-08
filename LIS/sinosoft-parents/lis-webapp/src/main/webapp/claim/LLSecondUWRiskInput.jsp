<%
//程序名称：LLSecondUWRiskInput.jsp
//程序功能：险种核保信息界面-----理赔部分
//创建日期：2005-01-06 11:10:36
//创建人  ：HYQ
//更新记录：  更新人 yuejw   更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<html>
<head>
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		String tContNo=request.getParameter("ContNo");
		String tInsuredNo=request.getParameter("InsuredNo");
		String tSendFlag=request.getParameter("SendFlag");
		String tCaseNo=request.getParameter("CaseNo");
		String tBatNo=request.getParameter("BatNo");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="./LLSecondUWRisk.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="./LLSecondUWRiskInit.jsp"%>
	<title>险种信息 </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit" >
	<table class= common border=0 width=100%>
    	 <tr>
	        <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class= titleImg align= center>核保信息</td>
	     </tr>
        </table>
		<div class=maxbox1>
	    <Table  class= common>
		<TR  class= common>        
			<TD  class= title>赔案号</TD>
			<TD  class= input><Input class="readonly wid" readonly name="CaseNo" id=CaseNo ></TD>				
			<TD  class= title>批次号</TD>
			<TD  class= input><Input class="readonly wid" readonly name="BatNo" id=BatNo ></TD>
			<TD  class= title> 合同号</TD>
			<TD  class= input><Input class="readonly wid" readonly name="ContNo" id=ContNo  ></TD>
			<TD  class= input><Input class="readonly wid" readonly type= hidden name="InsuredNo" id=InsuredNo></TD>				
		</TR>

	    </Table> 
		</div>
	    <table class= common border=0 width=100%>
    	 <tr>
	        <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class= titleImg align= center>被保人信息：</td>
	     </tr>
        </table>
		<div class=maxbox1>
        <table  class= common>
          <TR  class= common>
             <TD  class= title>客户号码</TD>
             <td  class= input>
                 <Input class="readonly wid" readonly name=InsuredNo1 id=InsuredNo1 >
             </TD>
             <TD class= title> 姓名 </TD>
             <td class= input>
                <Input class="readonly wid" readonly name=Name id=Name >
             </TD>          
             <TD class= title>性别</TD>
             <TD class= input>
                <input class="codeno" name="Sex" id=Sex type="hidden" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="showCodeListKey('Sex',[this,SexName],[0,1]);" >
                <input class="readonly wid" name="SexName" id=SexName readonly="readonly">
            </TD>
        </TR>
        <TR  class= common>
            <TD CLASS=title> 被保人年龄 </TD>
            <TD CLASS=input COLSPAN=1>
                <Input class="readonly wid" readonly  name=InsuredAppAge >
            </TD>  
            <TD CLASS=title>国籍 </TD>
            <TD CLASS=input COLSPAN=1>
               <input class="codeno" name="NativePlace" id=NativePlace type="hidden" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);" >
               <input class="readonly wid" name="NativePlaceName" id=NativePlaceName readonly="readonly"> 
            </TD>
           
	        <TD class= title id=MainInsured style="display:">与主被保险人关系</TD>
            <TD class= input id=MainInsuredInput style="display:">  
               <input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured type="hidden" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('RelationToMainInsured',[this,RelationToMainInsuredName],[0,1]);" onkeyup="showCodeListKey('RelationToMainInsured',[this,RelationToMainInsuredName],[0,1]);" >
               <input class="readonly wid" name="RelationToMainInsuredName" id=RelationToMainInsuredName readonly="readonly">
            </TD>
	      
       </TR>
	   <TR class= common>
            <TD  class= title>职业名称 </TD>
            <TD  class= input>
                <Input class="readonly wid" name="OccupationCode" id=OccupationCode readonly="readonly" >
            </TD>
            <TD  class= title> 职业类别 </TD>
            <TD  class= input>
                <input class="codeno"   name="OccupationType"  id=OccupationType   type="hidden" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('OccupationType',[this,OccupationTypeName],[0,1]);" onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);" >
                <input class="readonly wid" name="OccupationTypeName" id=OccupationTypeName readonly="readonly"> 
            </TD>
          
            <TD  class= title id=MainAppnt   style="display:">与投保人关系</TD>
            <TD  class= input id=MainAppntInput   style="display:">
                <input class="codeno"   name="RelationToAppnt"  id=RelationToAppnt   type="hidden" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('RelationToAppnt',[this,RelationToAppntName],[0,1]);" onkeyup="showCodeListKey('RelationToAppnt',[this,RelationToAppntName],[0,1]);" >
                <input class="readonly wid" name="RelationToAppntName" id=RelationToAppntName readonly="readonly">
           </TD>
	   </TR>
	  </Table>
      </div>
	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
			<TD class= titleImg>该合同下险种信息列表：</TD>
		</TR>
	</Table>	
	<Div id=DivLCPol style="display:''">
		<Table  class= common>
			<tr  class= common>
				<td style="text-align: left" colSpan=1><span id="spanLLRiskGrid" ></span></td>
			</TR>
		</Table>
	</Div>
	<hr class=line>
	<Table>
		<TR><td>
			<input value="加费承保录入"  class=cssButton type=button id=addbutton  name= "AddFee"  onclick="showAdd();">
			<input value="特约承保录入"  class=cssButton type=button id=specbutton name= "Spec"    onclick="showSpec();">
		</td></TR>
	</Table>
    <hr class=line>
	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLUWSub);"></TD>
			<TD class= titleImg>查看选中险种保单的核保轨迹信息</TD>
		</TR>
	</Table>
	<Div id=DivLLUWSub style="display:''">
		<Table  class= common>
			<tr  class= common>
				<td style="text-align: left" colSpan=1><span id="spanLLUWSubGrid" ></span></td>
			</TR>
		</Table>
	</Div>
	<hr class=line>
	
	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
			<TD class= titleImg>为选中险种保单做核保结论</TD>
		</TR>
	</Table>
	<Div id = "DivUWResult" class=maxbox style = "display: ''">
	<!-- 核保结论 -->
		<Table  class= common>
			<TR  class= common>
				<TD class= title>险种核保结论</td>
				<td class=input><Input class=codeno readonly name=UWState id=UWState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lluwstate',[this,UWStateName],[0,1],null,fm.RiskCode.value,fm.RiskCode.value,1);" onkeyup="return showCodeListKey('lluwstate',[this,UWStateName],[0,1],null,fm.RiskCode.value,fm.RiskCode.value,1);">
				<Input class=codename name=UWStateName id=UWStateName readonly ></td>
				
				<TD class= title></td>
				<td class=input></td>
				<TD class=title></td>
				<td class=input></td>
			</TR>
		</Table>
		<Table class=common>
			<TR>
				<TD >核保意见</TD>
			</TR>
			<TR>
				<TD  class= input> <textarea name="UWIdea" id=UWIdea cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
			</TR>
		</Table>
		<hr class=line>
		<Input Value="确  定" class=cssButton Type=button onclick="uwSaveClick();">
		<Input Value="取  消" class=cssButton Type=button onclick="cancelClick();">
		<Input Value="返  回" class=cssButton Type=button onclick="top.close();">
	</Div>
	<!--隐藏区域-->
	<Input name="Operator"   id="Operator"   type=hidden><!--记录操作员 -->
	<Input name="ComCode"    id="ComCode"    type=hidden><!--记录登陆机构 -->
	<Input name="ManageCom"  id="ManageCom"  type=hidden><!--记录管理机构 -->
	<Input name="SendFlag"   id="SendFlag"   type=hidden><!--用途未知 -->
	<Input name="ProposalNo" id="ProposalNo" type=hidden><!--保单号码  -->
	<Input name="PolNo"      id="PolNo"      type=hidden><!--保单险种号  -->
	<Input name="RiskCode"   id="RiskCode"   type=hidden><!--险种编码-->
	
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
