<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2005-09-14 11:10:36
//创建人  ：yaory程序创建
//更新记录：  更新人    更新日期     更新原因/内容
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
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
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
        
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
  loggerDebug("ProposalInput","LoadFlag:" + tLoadFlag);
  //loggerDebug("ProposalInput","############tGI.ManageCom===="+tGI.ManageCom);
  String BankFlag = "";
  BankFlag = request.getParameter( "BankFlag" );
  String queryRiskarray="**-"+request.getParameter("tBpno");
  loggerDebug("ProposalInput","queryRiskarray:"+queryRiskarray);
  String bankflag=request.getParameter("BankFlag");
  //20050914-yaory-如果已经制定保障计划，险种只初始化保障计划险种
  //根据被保人号，查询保障计划险种，然后初始化
  String InsuredNo=request.getParameter("InsuredNo");
  String grpriskflag="0";
  String queryRisk="";
  //loggerDebug("ProposalInput","lllllllllllll"+InsuredNo);
  if(InsuredNo!=null && !InsuredNo.equals("null"))
  {
    ExeSQL exeSql = new ExeSQL();
    SSRS MRSSRS = new SSRS();
    MRSSRS = exeSql.execSQL("select contplancode from lcinsured where grpcontno='"+request.getParameter("tBpno")+"' and insuredno='"+InsuredNo+"'");
    //loggerDebug("ProposalInput","2222222222="+MRSSRS.GetText(1,1));
    if(MRSSRS.MaxRow>0 && !MRSSRS.GetText(1,1).equals(""))
    {
    
      grpriskflag="1";
      queryRisk="****"+request.getParameter("tBpno")+"&"+MRSSRS.GetText(1,1);
    }
  }
  


%>
<head>
<script language="javascript">
  var tInsuredNo="<%=InsuredNo%>";
	var LoadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
	var BQFlag = "<%=request.getParameter("BQFlag")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var tt="<%=request.getParameter("tBpno")%>";
	var PolTypeFlag="<%=request.getParameter("PolTypeFlag")%>";
        //alert(tt);
	var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var checktype = "<%=request.getParameter("checktype")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var mainnoh = "";
	var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	if (ContType == "null")
	  ContType=1;
	var EdorType = "<%=request.getParameter("EdorType")%>";
	var EdorTypeCal = "<%=request.getParameter("EdorTypeCal")%>";
	//alert(EdorTypeCal);
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
	 
	//alert("BankFlag="+BankFlag);
	//alert("display="+display);     
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>

	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

	<SCRIPT src="ProposalInput.js"></SCRIPT>
	<SCRIPT src="SpecDealByRisk.js"></SCRIPT> 

	<%@include file="ProposalInit.jsp"%>
	<%
	  if(bankflag==null)
	  {
	%>
	<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
 <%
 }else{
 %>
 <SCRIPT src="ProposalAutoMove3.js"></SCRIPT>
 <%
 }
 %>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%
  if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
  {
%>
	<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
        <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
<%
  }
%>
</head>

<body onload="initForm();">
  <form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
    <div  id= "divButton" style= "display: ''">
    <%@include file="../common/jsp/ProposalOperateButton.jsp"%>
   </div>

    <div id= "divRiskCode0" style="display:''">
      <table class=common>
         <tr class=common>
            <TD  class= title>
              险种编码
            </TD>
            
            <%if(grpriskflag.equals("1")) {%>
            <TD  class= input>
              <!--Input class=codeno name=RiskCode ondblclick="showCodeList('<%=queryRisk%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="return showCodeListKey('<%=queryRisk%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary-->
              <Input class=codeno name=RiskCode ondblclick="showCodeList('<%=queryRiskarray%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="return showCodeListKey('<%=queryRiskarray%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary>
              <!--Input class="codeno" name=RiskCode ondblclick="showCodeListEx('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="showCodeListKeyEx('RiskCode',[this,RiskCodeName],[0,1]);"><input class="codename" name="RiskCodeName" readonly="readonly"-->
              </TD>
            <%
            
              }
                else{ %>
            <TD  class= input>
               <Input class=codeno name=RiskCode ondblclick="showCodeList('<%=queryRiskarray%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="return showCodeListKey('<%=queryRiskarray%>',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary>
              </TD>
              <%} %>
         </tr>
      </table>
    </div>

    <!-- 隐藏信息 -->
    <div  id= "divALL0" style= "display: 'none'">
    <!-- 连带被保人信息部分（列表） -->
	    <div  id= "divLCInsured0" style= "display: 'none'">
        <table>
      	  <tr>
            <td class=common>
		  	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
      		  </td>
      		  <td class= titleImg>
      			  连带被保人信息
      		  </td>
      	  </tr>
        </table>
	      <div  id= "divLCInsured2" style= "display: ''">
          <table  class= common>
            <tr  class= common>
      	      <td text-align: left colSpan=1>
		  		  	  <span id="spanSubInsuredGrid" >
		  		  	  </span>
		  		    </td>
		  	    </tr>
		      </table>
	      </div>
	    </div>
      <!-- 受益人信息部分（列表） -->
      <table>
      	<tr>
          <td class=common>
		  	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
      		</td>
          <td class= titleImg>
      		  受益人信息
      		</td>
      	</tr>
      </table>
	    <div id= "divLCBnf1" style= "display: ''" >
      	<table class= common>
          <tr class= common>
      	    <td style="text-align: left" colSpan="1">
		  			  <span id="spanBnfGrid" >
		  			  </span>
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
      		<td class= titleImg>
      		  告知信息
      		</td>
      		</tr>
      		 </table>
	    <div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
          <tr  class= common>
      	    <td style="text-align: left" colSpan="1">
		  			  <span id="spanImpartGrid" >
		  			  </span>
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
      		<td class= titleImg>
      		  特约信息
      		</td>
      	</tr>
      </table>
	    <div  id= "divLCSpec1" style= "display: ''">
        <table  class= common>
          <tr  class= common>
      	    <td style="text-align:left" colSpan="1">
		  			  <span id="spanSpecGrid">
		  			  </span>
		  		  </td>
		  	  </tr>
		    </table>
		    <div id= "divHideButton" style= "display: ''">
  	  	  <input type="button" class=cssButton name= "back" value= "上一步" onclick="returnparent();">
  	  	</div>
		    <div id= "divHideButton" style= "display: ''">
  	  		<input type="button" name= "quest" value= "问题件录入">
  	  	</div>
	    </div>

      <!--可以选择的责任部分，该部分始终隐藏-->
	    <div id="divDutyGrid" style= "display: ''">
      	<table class= common>
          <tr class= common>
 	    		  <td style="text-align:left" colSpan="1">
		  			  <span id="spanDutyGrid" >
		  			  </span>
		  		  </td>
		  	  </tr>
		    </table>
		    <!--确定是否需要责任信息-->
	    </div>


	    <div id= "divPremGrid1" style= "display: ''">
	    	<table class= common>
	    		<tr class= common>
	    		<td style="text-align:left" colSpan="1">
	    			<span id="spanPremGrid" >
	    			</span>
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
    <input type=hidden name="BQFlag" id="BQFlag" value="">
    <input type='hidden' name="ProposalContNo" value="">
    <input type='hidden' name="WorkFlowFlag" value="">
    <input type='hidden' name="MissionID" value="">
    <input type='hidden' name="SubMissionID" value="">
    <input type=hidden id="BQContNo" name="BQContNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>


