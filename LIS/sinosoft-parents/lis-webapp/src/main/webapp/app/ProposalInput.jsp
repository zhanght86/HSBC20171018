<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//Modify by niuzj,2006-08-23,英大需要在录入受益人信息时增加一个“性别”字段
%>
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

//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体下个人投保单明细查询
// 5 -- 复核
// 6 -- 查询
// 7 -- 保全新保加人
// 8 -- 保全新增附加险
// 9 -- 无名单补名单
// 10-- 浮动费率
// 99-- 随动定制

	String tLoadFlag = "";
	String tInsuredPeoples="";
	String tInsuredAppAge="";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		tInsuredPeoples=request.getParameter("InsuredPeoples");
		tInsuredAppAge=request.getParameter("InsuredAppAge");
		loggerDebug("ProposalInput","被保人数为:="+tInsuredPeoples);
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}

	String temhh=request.getParameter("hh");
	if(temhh!=null && !temhh.equals("null") && temhh.equals("1"))
	{
	  loggerDebug("ProposalInput","fffffffffffffffffffffffff");
	  session.putValue("MainRiskPolNo","");
	}
	loggerDebug("ProposalInput","ddddddddddddddddddddd");
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("ProposalInput","LoadFlag:" + tLoadFlag);
	loggerDebug("ProposalInput","############tGI.ManageCom===="+tGI.ManageCom);
	String BankFlag = "";
	BankFlag = request.getParameter( "BankFlag" );
	String queryRiskarray="**-"+request.getParameter("tBpno");
	loggerDebug("ProposalInput",queryRiskarray);
	loggerDebug("ProposalInput","参数"+request.getParameter("tBpno"));
	String bankflag=request.getParameter("BankFlag");
    String EdorValiDate = request.getParameter("EdorValiDate");
    String CValiDate = request.getParameter("CValiDate");
	//add by ML 2006-03-30 为保障计划设置变量
	//根据被保人号，查询保障计划险种，然后初始化
  String InsuredNo=request.getParameter("InsuredNo");
  String grpriskflag="0";
  String queryRisk="";
  loggerDebug("ProposalInput","InsuredNo===="+InsuredNo);
  loggerDebug("ProposalInput","ProposalContNo===="+request.getParameter("ProposalContNo"));
  if(InsuredNo!=null && !InsuredNo.equals("null"))
  {
    ExeSQL exeSql = new ExeSQL();
    String MRStr = "";
//    SSRS MRSSRS = new SSRS();
//    MRSSRS = exeSql.execSQL("select contplancode from lcinsured where contno='"+request.getParameter("ProposalContNo")+"' and insuredno='"+InsuredNo+"'");
    //loggerDebug("ProposalInput","2222222222="+MRSSRS.GetText(1,1));
  String sql = "select contplancode from lcinsured where contno='"+request.getParameter("ProposalContNo")+"' and insuredno='"+InsuredNo+"'";
  TransferData sTransferData=new TransferData();
	sTransferData.setNameAndValue("SQL", sql);
	VData sVData = new VData();
	sVData.add(sTransferData);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(tBusinessDelegate.submitData(sVData, "getOneValue", "ExeSQLUI"))
	{
		MRStr = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
	 }
//    if(MRSSRS.MaxRow>0 && !MRSSRS.GetText(1,1).equals(""))
	if(MRStr.length()>0 && !"".equals(MRStr) )
    {
      grpriskflag="1";
      queryRisk="****"+request.getParameter("ProposalContNo")+"&"+MRStr;
      loggerDebug("ProposalInput","queryRisk" + queryRisk);
    }
  }
  // add End

%>
<head>
<script language="javascript">
	//alert('1');
	var LoadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
	var BQFlag = "<%=request.getParameter("BQFlag")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var tt="<%=request.getParameter("ProposalContNo")%>";
	var gg="<%=request.getParameter("tBpno")%>";   //add by Marlon 20060717 记录团体保单号
	var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var Operator = "<%=tGI.Operator%>";
	var checktype = "<%=request.getParameter("checktype")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var mainnoh = "";
	var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	if (ContType == "null")
	  ContType=1;
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var param="";
	var tMissionID = "<%=request.getParameter("MissionID")%>";
	var tSubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var ScanFlag = "<%=request.getParameter("ScanFlag")%>";
	var BankFlag = "<%=request.getParameter("BankFlag")%>";
	var ActivityID = "<%=request.getParameter("ActivityID")%>";
	var NoType = "<%=request.getParameter("NoType")%>";
	var NameType = "<%=request.getParameter("NameType")%>";
	var display = "<%=request.getParameter("display")%>";
	var BankFlag = "<%=request.getParameter("BankFlag")%>";
	var AppntChkFlag = "<%=request.getParameter("AppntChkFlag")%>";
	var InsuredChkFlag = "<%=request.getParameter("InsuredChkFlag")%>";
	var Peoples2="<%=request.getParameter("InsuredPeoples")%>"
	var InsuredAge = "<%=request.getParameter("InsuredAppAge")%>"
	if (InsuredAge == null || InsuredAge == "")
	{
	    InsruedAge = "0";
	}
    var EdorValiDate = "<%=EdorValiDate%>"; //Add By QianLy on 2007-01-24 for ST815 
	var CValiDate = "<%=CValiDate%>"; //Add By QianLy on 2007-01-24 for ST815 
	var specScanFlag = "<%=request.getParameter("specScanFlag")%>"  //特殊保单录入
	
	var tempSelPolNo = "<%=request.getParameter("SelPolNo")%>" 
	var NewChangeRiskPlanFlag = "<%=request.getParameter("NewChangeRiskPlanFlag")%>" 
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<script src="../common/javascript/MultiCom.js"></script>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<link rel="stylesheet" type="text/css"	href="../common/themes/default/easyui.css">
	
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="ProposalInput.js"></SCRIPT>
	<SCRIPT src="SpecDealByRisk.js"></SCRIPT>
	<script src="ProposalAutoMoveNew.js"></script>

	<%@include file="ProposalInit.jsp"%>

	<style>
.divRisk ul {width =100%;
	list-style-type: none;
	margin: 0;
	padding: 0;
	margin-bottom: 10px;
}

//
.demo li {
	margin: 5px;
	padding: 5px;
	width: 150px;
}

.divRisk li {
	float: left;
	width: 30%;
	margin-left: 10px;
	display: inline;
	height: 30px;
	background: #F7F7F7;
	margin-top: 10px;
}
</style>

	<%
		if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
		{
	%>
	  <script src="../common/javascript/jquery.js"></script>
  	<script src="../common/javascript/jquery.imageView.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
 	 	<script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  	<script src="../common/javascript/ScanPicView.js"></script>
  	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
		<SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
		<SCRIPT src="TabAutoScroll.js"></SCRIPT>
	<%
		}
	%>
	<%
	//add by liuyuxiao 2011-05-27
	if(request.getParameter("isTab")!=null&&request.getParameter("isTab").equals("yes")){
	%>
		<script language="javascript">
	  		//isInTab();
	  	</script>
	<%
	}
	//end add by liuyuxiao 2011-05-27
	%>
</head>

<body onLoad="initForm();window.document.ondblclick=AutoTab;">

<form action="./ProposalSave.jsp" method=post id="fm" name=fm target="fraSubmit">
	<div  id= "divButton" style= "display:  ">
		<%@include file="../common/jsp/ProposalOperateButton.jsp"%>
	</div>
     <table>
      	<tr>
          <td class=common>
		  	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
      		</td>
          <td class= titleImg>请输入险种编码</td>
      	</tr>
      </table>
    <div id= "divRiskCode0" class="maxbox1" style="display: ">
      <table class=common>
         <tr class=common>
            <TD  class= title>险种编码</TD>
            <%
            loggerDebug("ProposalInput","tLoadFlag===" + tLoadFlag);
            loggerDebug("ProposalInput","BankFlag====" + BankFlag);
            loggerDebug("ProposalInput","grpriskflag====" + grpriskflag);
            %>
            <%if(tLoadFlag.equals("8") || tLoadFlag.equals("7"))
            {
            %>
            	<TD  class= input colspan=3>
              	<Input class="codeno" name=RiskCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' id="RiskCode" onDblClick="showCodeListEx('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1);" onKeyUp="showCodeListKeyEx('RiskCode',[this,RiskCodeName],[0,1]);"><input class="common" name="RiskCodeName" id="RiskCodeName" readonly>
            	</TD>
            <%
            }
            else if(!tLoadFlag.equals("2") && !tLoadFlag.equals("23") && !tLoadFlag.equals("8") && !tLoadFlag.equals("13"))
            {
					loggerDebug("ProposalInput","BankFlag==="+BankFlag);
					if( BankFlag==null || (!BankFlag.equals("1") && !BankFlag.equals("5")))
					{
            		%>
                	<TD  class= input colspan=3>
	                	<Input class="codeno" name=RiskCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' id="RiskCode"  verify="险种代码|NOTNUlL&CODE:RiskCode" onDblClick="showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onKeyUp="showCodeListKey('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class="common" id="RiskCodeName" name="RiskCodeName" readonly>
	                	<input type="button" class=cssButton name= "back" value= "进入险种界面" onClick="intorisk();">
                	</TD>
             		<%
               		}
               		else if( BankFlag!=null && BankFlag.equals("5") )
					{
					%>
                		<TD  class= input colspan=3>
	                	<Input class="codeno" name=RiskCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' id="RiskCode"  verify="险种代码|NOTNUlL&CODE:directriskcode" onDblClick="showCodeList('directriskcode',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onKeyUp="showCodeListKey('directriskcode',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class="common" id="RiskCodeName" name="RiskCodeName" readonly>
	                	<input type="button" class=cssButton name= "back" value= "进入险种界面" onClick="intorisk();">
                		</TD>
             		<%
					}
					else
               		{
             		%>
            			<TD  class= input colspan=3>
              			<Input class="codeno" name=RiskCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' id="RiskCode" verify="险种代码|NOTNUlL&CODE:BankRiskCode" onDblClick="showCodeList('BankRiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onKeyUp="showCodeListKey('BankRiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class="common" name="RiskCodeName" id="RiskCodeName" readonly>
              			<input type="button" class=cssButton name= "back" value= "进入险种界面" onClick="intorisk();">
             			</TD>
            		<%
            	 	}
            ///add by ML 2006-03-30 如果已经制定保障计划，险种只初始化保障计划所对应的险种
            }else if(tLoadFlag.equals("2") && grpriskflag.equals("1"))
            	{
            	//loggerDebug("ProposalInput","处理保障计划");
            	%>
            		<TD  class= input colspan=3>
            		<Input class=codeno name=RiskCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' id="RiskCode" onDblClick="showCodeList('<%=queryRisk%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onKeyUp="return showCodeListKey('<%=queryRisk%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=common id="RiskCodeName" name=RiskCodeName readonly=true elementtype=nacessary>
            		</TD>
            	<%
            ///add End
            }else
            	{
            	%>
            		<TD  class= input colspan=3>
                <Input class=codeno name=RiskCode style='background: url("../common/images/select--bg_03.png") no-repeat right;' id="RiskCode" onDblClick="showCodeList('<%=queryRiskarray%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onKeyUp="return showCodeListKey('<%=queryRiskarray%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=common id="RiskCodeName" name=RiskCodeName readonly=true elementtype=nacessary>
                </TD>
              <%
              }
              %>
			  <td class=title></td>
		      <td class=input></td>
         </tr>
      </table>
    </div>
		
    <!-- 隐藏信息 -->
    <div  id= "divALL0" style= "display: none">
    <!-- 连带被保人信息部分（列表） -->
	    <div  id= "divLCInsured0" style= "display: none">
        <table>
      	  <tr>
            <td class=common>
		  	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
      		  </td>
      		  <td class= titleImg> 连带被保人信息</td>
      	  </tr>
        </table>
	      <div  id= "divLCInsured2" style= "display:  ">
	          <table  class= common>
	            <tr  class= common>
	      	      <td text-align: left colSpan=1><span id="spanSubInsuredGrid" ></span></td>
			  	</tr>
			  </table>
	      </div>
	    </div>
	    <!--后加的-->
<DIV id=DivInsured STYLE="display: ">
<!-- 连带被保人信息部分（列表） -->
<Div  id= "divLCInsured0" style= "display: none">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsured1);">
</td>
<td class= titleImg>
被保人信息
</td>
</tr>
</table>

<Div  id= "divInsured" style= "display: none">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanInsuredGrid" >
</span>
</td>
</tr>
</table>
</div>

</div>

</DIV>
<!--以上后加的-->
	    
      <!-- 受益人信息部分（列表） -->
      <table>
      	<tr>
          <td class=common>
		  	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
      		</td>
          <td class= titleImg>受益人信息</td>
      	</tr>
      </table>
	    <div id= "divLCBnf1" style= "display: none" >
      	<table class= common>
          <tr class= common>
      	    <td style="text-align: left" colSpan="1">
		  			  <span id="spanBnfGrid" ></span>
		  	</td>
		  </tr>
	  </table>
	  </div>
      <!-- 告知信息部分（列表） -->
      <table>
        <tr>
          <td class=common>
		  	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      		</td>
      		<td class= titleImg>告知信息</td>
      	</tr>
      </table>
	    <div  id= "divLCImpart1" style= "display:  ;">
        <table  class= common>
          <tr  class= common>
      	    <td style="text-align: left" colSpan="1">
		  			  <span id="spanImpartGrid" ></span>
		  	</td>
		  </tr>
		</table>
	    </div>
      <!-- 特约信息部分（列表） -->
      <table>
      	<tr>
          <td class=common>
		  	    <IMG src= "../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCSpec1);">
      		</td>
      		<td class= titleImg>特约信息</td>
      	</tr>
      </table>
	    <div  id= "divLCSpec1" style= "display:  ;">
        <table  class= common>
          <tr  class= common>
      	    <td style="text-align:left" colSpan="1">
		  			  <span id="spanSpecGrid"></span>
		  	</td>
		  </tr>
		</table>
		
		<table> 
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif" style="cursor: hand;" OnClick="showPage(this,divDiscount);"></td>
				<td class=titleImg>可选折扣信息</td>
			</tr>
		</table>
		<Div id="divDiscount" style="display:  ">
		<table class=common>
			<TR class=common>
				<td style="text-align:left"  colSpan=1><span id="spanDiscountGrid"></span></td>
			</tr>
		</table>
		</div>
		
		<div id= "divHideButton" style= "display:  ;">
  	  	  <input type="button" class=cssButton name= "back" value= "上一步" onClick="returnparent();">
  	  	</div>
		<div id= "divHideButton" style= "display:  ">
  	  		<input type="button" name= "quest" value= "问题件录入">
  	  	</div>
	    </div>

      <!--可以选择的责任部分，该部分始终隐藏-->
	    <div id="divDutyGrid" style= "display:  ">
      	<table class= common>
          <tr class= common>
 	    		  <td style="text-align:left" colSpan="1">
		  			  <span id="spanDutyGrid" ></span>
		  		  </td>
		  </tr>
		</table>
	    </div>


	    <div id= "divPremGrid1" style= "display:  ;">
	    	<table class= common>
	    		<tr class= common>
	    		<td style="text-align:left" colSpan="1">
	    			<span id="spanPremGrid" ></span>
	    		</td>
	    		</tr>
	    	</table>
	    </div>
    </div>
    <!--add by yaory-->
     <input type=hidden id="tLoadFlag" name="tLoadFlag" value="<%=tLoadFlag%>">
    <!--end add-->
    <input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
	  <input type=hidden id="inpNeedPremGrid" name="inpNeedPremGrid" value="0">
	  <input type=hidden id="fmAction" name="fmAction">
	  <input type="hidden" class="common" name="SelPolNo" value="">
	  <input type=hidden id="ContType" name="ContType" value="">
    <input type=hidden name=BQFlag>
    <input type='hidden' name="InsuredPeoples" value="">
    <input type='hidden' name="InsuredAppAge" value="">
    <input type='hidden' name="ProposalContNo" value="">
    <input type='hidden' name="WorkFlowFlag" value="">
    <input type='hidden' name="MissionID" value="">
    <input type='hidden' name="SubMissionID" value="">
    <input type=hidden id="BQContNo" name="BQContNo">
    <!-- 增加是否是双岗录入的标识，用于定制随动 -->
    <input type=hidden id="IFDS" name="IFDS" value="0">
    
      <input type="hidden" id="RollSpeedSelf" name="RollSpeedSelf">
      <input type="hidden" id="RollSpeedBase" name="RollSpeedBase">
      <input type="hidden" id="RollSpeedOperator" name="RollSpeedOperator">   
      <input type="hidden" id="Operator" name="Operator">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>


