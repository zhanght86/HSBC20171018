<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ManuUWInput.jsp.
//程序功能：新契约个人人工核保
//创建日期：2005-12-29 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPrtNo = "";
	String tContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tNoType = "";
	String tReportFlag="";

	tPrtNo = request.getParameter("PrtNo");
	tContNo = request.getParameter("ContNo");
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
  tActivityID = request.getParameter("ActivityID");
  tNoType = request.getParameter("NoType");
	session.putValue("ContNo",tContNo);
	tReportFlag = request.getParameter("ReportFlag");
%>
<html>
<%
  //个人下个人
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var ContNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	var MissionID = "<%=tMissionID%>";
	var SubMissionID = "<%=tSubMissionID%>";
	var PrtNo = "<%=tPrtNo%>";
	var ActivityID = "<%=tActivityID%>";
	var NoType = "<%=tNoType%>";
	var uwgrade = "";
    var appgrade= "";
    var uwpopedomgrade = "";
    var codeSql = "code";
    var ReportFlag = "<%=tReportFlag%>";
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="ManuUW.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ManuUWInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./ManuUWCho.jsp">
    <!-- 保单查询条件 -->
    <div id="divSearch">
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
    </table>
    <table  class= common align=center>
      	<tr  class= common>
          <td  class= title>投保单号</TD>
          <td  class= input> <Input class= common name=QProposalNo > </TD>
          <td  class= title>印刷号码</TD>
          <td  class= input> <Input class=common name=QPrtNo> </TD>
          <td  class= title> 管理机构  </TD>
          <td  class= input>  <Input class="code" name=QManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">  </TD>
        </TR>
        <tr  class= common>
          <td  class= title> 核保级别  </TD>
          <td  class= input>  <Input class="code" name=UWGradeStatus value= "1" CodeData= "0|^1|本级保单^2|下级保单" ondblClick="showCodeListEx('Grade',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Grade',[this,''],[0,1]);">  </TD>
          <td  class= title>  投保人名称 </TD>
          <td  class= input> <Input class=common name=QAppntName > </TD>
          <td  class= title>  核保人  </TD>
          <td  class= input>   <Input class= common name=QOperator value= "">   </TD>
        </TR>
        <tr  class= common>
          <td  class= title>  保单状态 </TD>
          <td  class= input>  <Input class="code" readonly name=State value= "" CodeData= "0|^1|未人工核保^2|核保已回复^3|核保未回复" ondblClick="showCodeListEx('State',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('State',[this,''],[0,1]);"> </TD>

        </TR>
        <tr  class= common>
          <td  class= input>   <Input  type= "hidden" class= Common name = QComCode >  </TD>

       </TR>
    </table>
          <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
          <!--INPUT VALUE="撤单申请查询" class=common TYPE=button onclick="withdrawQueryClick();"-->
          <INPUT type= "hidden" name= "Operator" value= "">
    </div>
    <!--合同信息-->
	<DIV id=DivLCContButton STYLE="display:'none'">
	<table id="table1">
			<tr>
				<td>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);">
				</td>
				<td class="titleImg">合同信息
				</td>
			</tr>
	</table>
	</DIV>
	<div id="DivLCCont" STYLE="display:'none'">
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title" nowrap>投保单号码
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="PrtNo" VALUE CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="14">
	    		</td>
				<td CLASS="title" nowrap>管理机构
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="ManageCom"  MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title" nowrap>销售渠道
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SaleChnl" CLASS="readonly" readonly MAXLENGTH="2">
	    		</td>
			</tr>
			<tr CLASS="common">
				<td CLASS="title">业务员编码
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="AgentCode" MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">其它声明
	    		</td>
				<td CLASS="input" COLSPAN="3">
					<input NAME="Remark" CLASS="readonly" readonly MAXLENGTH="255">
	    		</td>
			</tr>
			  <input NAME="ProposalContNo" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="20">
				<input NAME="AgentGroup" type=hidden CLASS="readonly" readonly TABINDEX="-1" MAXLENGTH="12">
				<input NAME="AgentCode1" type=hidden MAXLENGTH="10" CLASS="readonly" readonly>
				<input NAME="AgentCom" type=hidden CLASS="readonly" readonly>
				<input NAME="AgentType" type=hidden CLASS="readonly" readonly>
		</table>
	</div>
	<div id=DivLCSendTrance STYLE="display:'none'">
		<table class = "common">
				<tr CLASS="common">
			    <td CLASS="title">上报标志
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendFlag" MAXLENGTH="10" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">核保结论
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendUWFlag" CLASS="readonly" readonly>
	    		</td>
				<td CLASS="title">核保意见
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="SendUWIdea" CLASS="readonly" readonly>
	    		</td>
			</tr>
		</table>
	</div>
<hr>
<table>
  <tr>
    <td nowrap>
    <INPUT VALUE=" 投保单明细查询 " class=cssButton TYPE=button onclick="showPolDetail();">
    <INPUT VALUE="  影像资料查询  " class=cssButton  TYPE=button onclick="ScanQuery();">
    <INPUT VALUE="自动核保提示信息" class=cssButton TYPE=button onclick="showNewUWSub();">
    <INPUT VALUE=" 问题件信息查询 " class=cssButton TYPE=button onclick="QuestQuery()">
    <INPUT VALUE="财务交费信息查询" class=cssButton TYPE=button onclick="showTempFee();">
    </td>
  </tr>
  <tr>
    <td>
    <INPUT VALUE="投保操作履历查询" class=cssButton TYPE=button onclick="QueryRecord()">
    <INPUT VALUE="    核保查询    " class=cssButton TYPE=button onclick="UWQuery()">
    </td>
  </tr>
</table>
<hr>
	<DIV id=DivLCAppntIndButton STYLE="display:'none'">
	<!-- 投保人信息部分 -->
	<table>
	<tr>
	<td>
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
	</td>
	<td class= titleImg>
	投保人信息
	</td>
	</tr>
	</table>
	</DIV>

	<DIV id=DivLCAppntInd STYLE="display:'none'">
	 <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            姓名
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly" readonly name="AppntName" value="">
	          </TD>
	          <td  class= title>
	            性别
	          </TD>
	          <td  class= input>
	            <Input name=AppntSex CLASS="readonly" >
	          </TD>
	          <td  class= title>
	            年龄
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="AppntBirthday">
	          </TD>
	        </TR>

	        <tr  class= common>
	            <Input CLASS="readonly" readonly type=hidden name="AppntNo" value="">
	            <Input CLASS="readonly" readonly  type=hidden name="AddressNo">
	          <td  class= title>
	            职业
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="OccupationCode">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="OccupationType">
	          </TD>
	          <td  class= title>
	            国籍
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly name="NativePlace">
	          </TD>
	        </TR>
	        <tr>
	        	<td  class= title>
           	 VIP标记
          	</TD>
          	<td  class= input>
            	<Input class="readonly" readonly name=VIPValue >

          	</TD>
	        <td  class="title" nowrap>
            	黑名单标记
          	</TD>
         	 <td  class= input>
            	<Input class="readonly" readonly name=BlacklistFlag >

          	</TD>
	        </TR>
	      </table>
	</DIV>
    <!-- 保单查询结果部分（列表） -->
   <DIV id=DivLCContInfo STYLE="display:''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单查询结果
    		</td>
    	</tr>
    </table>
    </Div>
         <Div  id= "divLCPol1" style= "display: ''" align = center>
       		<tr  class=common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class=cssButton TYPE=button onclick="getFirstPage();">
      <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="getNextPage();">
      <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="getLastPage();">
    </div>
<hr>
<table>
  <tr>
    <td nowrap>
    <!--INPUT VALUE=" 投保人既往投保信息 " class=cssButton TYPE=button onclick="showApp(1);"-->
    <INPUT VALUE=" 投保人健康告知查询 " class=cssButton TYPE=button onclick="queryHealthImpart()">
    <INPUT VALUE=" 投保人体检资料查询 " class=cssButton TYPE=button onclick="showBeforeHealthQ()">
    <INPUT VALUE=" 投保人保额累计信息 " class=cssButton TYPE=button onclick="amntAccumulate();">
    <!--<INPUT VALUE=" 投保人影像资料查询 " class=cssButton TYPE=button onclick=""> -->
    </td>
  </tr>
  <tr>
    <td nowrap>
    <INPUT VALUE="投保人已承保保单查询" class=cssButton TYPE=button onclick="queryProposal();">
    <INPUT VALUE="投保人未承保保单查询" class=cssButton TYPE=button onclick="queryNotProposal();">
    <INPUT VALUE=" 投保人既往保全查询 " class=cssButton TYPE=button onclick="queryEdor()">
    <INPUT VALUE=" 投保人既往理赔查询 " class=cssButton TYPE=button onclick="queryClaim();">
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
    <!--INPUT VALUE="家庭既往投保信息" class=cssButton TYPE=button onclick="showApp(2);"-->
    <!--INPUT VALUE="被保人既往投保信息" class=cssButton TYPE=button onclick="showApp(2);"-->
    <!--INPUT VALUE="以往核保记录" class=common TYPE=button onclick="showOldUWSub();"-->
  <hr></hr>
   <Div  id= "divMain" style= "display: 'none'">
    <!--附加险-->

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 被保人信息
    		</td>
    	    </tr>
        </table>
        <Div  id= "divLCPol2" style= "display: 'none'" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>

    </Div>
<hr>
<table class= common border=0 width=100% >

	  <div id=divUWButton1 style="display:''">
<table>
  <tr>
    <td nowrap>
          <input value=" 体检通知录入 " class=cssButton type=button onclick="showHealth();" width="200">
          <input value=" 生调申请录入 " class=cssButton type=button onclick="showRReport();">

          <input value="  承保计划变更  " class=cssButton type=button onclick="showChangePlan();">
          <input value="承保计划变更结论录入" class=cssButton type=button onclick="showChangeResultView();">
          <input value="  再保回复信息  " class=cssButton type=button onclick="showUpReportReply();" width="200">
    </td>
  </tr>
  <tr>
    <td nowrap>
          <input value=" 查询体检结果 " class="cssButton" type="button" onclick="queryHealthReportResult();">
          <input value=" 查询生调结果 " class="cssButton" type="button" onclick="queryRReportResult();">
          <input value="  发核保通知书  " class = cssButton type = button onclick="SendAllNotice()">
          <!--input value="   记   事   本     " class=cssButton type=button onclick="showNotePad();" width="200"-->
         <!--edit by yaory-->
          <input value="     再 保 呈 报    " id="ReDistribute"  name="ReDistribute" class=cssButton type=button onclick="UWUpReport();" width="200">
    </td>
  </tr>

  <tr>
  	<td nowrap>
  		<input value=" 客户品质管理 " class=cssButton type=button onclick="CustomerQuality();" >
  		<input value="业务员品质管理" class=cssButton type=button onclick="AgentQuality();" >
  		<input value="修改保单生效日期" class=cssButton type=button onclick="ChangeCvalidate();" >
  	 </td>
  </tr>
</table>
<hr>
          <span id= "divAddButton1" style= "display: ''">
          	<!--input value="体检资料查询" class=common type=button onclick="showHealthQ();" width="200"-->
          </span>
          <!--input value="体检回销" class=cssButton type=button onclick="showHealthQ();" -->
          <!--INPUT VALUE="契调回销" class=cssButton TYPE=button onclick="RReportQuery();"-->
          <!--INPUT VALUE="保全明细查询" class= common TYPE=button onclick="Prt();"-->
          <!--INPUT VALUE="保全项目查询" class= common TYPE=button onclick="ItemQuery();"-->
          <!--INPUT VALUE="问题件录入" class=cssButton TYPE=button onclick="QuestInput();"-->
          <!--input value="问题件回销" class=cssButton type=button onclick="QuestBack();"-->
          <!--input value="问题件查询" class=cssButton type=button onclick="QuestQuery();"-->
          <!--input value="告知整理" class=cssButton type=button onclick="ImpartToICD();"-->
          <!--INPUT  VALUE="既往理赔查询" class=cssButton TYPE=button onclick="ClaimGetQuery();"-->

          <span  id= "divAddButton2" style= "display: ''">
          	<!--INPUT VALUE="生存调查查询" class=common TYPE=button onclick="RReportQuery();"-->
          </span>

          <span  id= "divAddButton3" style= "display: ''">
          	<!--input value="撤单申请查询" class=common type=button onclick="BackPolQuery();"-->
          </span>
          <span  id= "divAddButton4" style= "display: ''">
          	<!--input value="催办查询" class=common type=button onclick="OutTimeQuery();"-->
          </span>

          <span  id= "divAddButton5" style= "display: ''">
          <!--input value="体检资料录入" class=common type=button onclick="showHealth();" width="200"-->
          </span>


          <span  id= "divAddButton6" style= "display: ''">
          	<!--input value="生调请求说明" class=common type=button onclick="showRReport();"-->
          </span>

        <br></br>

           <!--input value="发核保通知书" class=cssButton type=button onclick="SendNotice();"-->
           <!--input value="发问题件通知书" class=cssButton type=button onclick="SendIssue();"-->
            <!--input value="发拒保通知书" class=cssButton type=button onclick="SendRanNotice();"-->
           <!--input value="发延期通知书" class=cssButton type=button onclick="SendDanNotice();"-->
           <!--input value="发加费通知书" class = cssButton type= button onclick="SendAddNotice()"-->

          <!--tr> <hr> </hr>  </tr-->
          <span  id= "divAddButton7" style= "display: ''">

          <!--input value="发首期交费通知书" class=cssButton type=button onclick="SendFirstNotice();"-->
          </span>
          <!--<input value="发催办通知书" type=button onclick="SendPressNotice();">-->
          <span  id= "divAddButton8" style= "display: ''">
          <!--input value="发核保通知书" class=common type=button onclick="SendNotice();">-->
          </span>
          <!--input value="发体检通知书" class=common type=button onclick="SendHealth();"-->
          <!--input value="核保分析报告录入" class=cssButton type=button onclick="showReport();" width="200"-->
           <span  id= "divAddButton9" style= "display: ''">

      </div>
    </table>
    	  <input type="hidden" name= "PrtNoHide" value= "">
    	  <input type="hidden" name= "PolNoHide" value= "">
    	  <input type="hidden" name= "MainPolNoHide" value= "">
    	  <input type="hidden" name= "NoHide" value= "">
    	  <input type="hidden" name= "TypeHide" value= "">
          <INPUT  type= "hidden" class= Common name= UWGrade value= "">
          <INPUT  type= "hidden" class= Common name= AppGrade value= "">
          <INPUT  type= "hidden" class= Common name= PolNo  value= "">
          <INPUT  type= "hidden" class= Common name= "ContNo"  value= "">
          <INPUT  type= "hidden" class= Common name= "YesOrNo"  value= "">
          <INPUT  type= "hidden"  class= Common name= "MissionID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "SubMissionID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "ActivityID"  value= "">
          <INPUT  type= "hidden"  class= Common name= "NoType"  value= "">
          <INPUT  type= "hidden"  class= Common name= "SubConfirmMissionID"  value= "">
          <INPUT  type= "hidden" class= Common name= SubNoticeMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= UWPopedomCode value= "">

    <div id = "divUWResult" style = "display: ''">
        <!-- 核保结论 -->
        <table class=common >
            <tr><td class= titleImg align= center>核保结论：</td></tr>
            <!--
            <tr class = common>
                <td class=title >建议结论</td>
                <td class =input><Input class= common name="SugIndUWFlag"  ></td>
            </tr>
            -->

            <tr>
                <td class= title>
                    <!--span id= "UWResult"> 保全核保结论 <Input class="code" name=uwstate value= "1" CodeData= "0|^1|本级保单^2|下级保单" ondblClick="showCodeListEx('uwstate',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('uwstate',[this,''],[0,1]);">  </span-->
                    核保结论
                </td>

                <td class=input>
                    <Input class=codeno name=uwState ondblclick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly elementtype=nacessary>
                </td>

                <td class=title8>
                    核保流向
                </td>

                <td class=input8>
                    <Input class="codeno" name=uwUpReport ondblclick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onkeyup="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary>
                </td>

            </TR>
        </table>
    <table class=common>
		<tr>
		      	<td height="24"  class= title>
            		核保意见
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<td  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      		<td>
      			<input name=uwPopedom type=hidden>

      			</td>
      		</tr>
	  </table>
</div>

        <!--****************************************************************
            修改人：续涛，修改时间20050420，修改原因：呈报
            ****************************************************************
        -->
<!--
        <div id = "divUWup" style = "display: ''">

          <table  class= common align=center>
              <TR>


                  <td class=title8>
                      核保师级别
                  </td>


                  <td class=input8>
                      <Input class="codeno" name=uwPopedom ondblclick="return showCodeList('uwPopedom',[this,uwPopedomname],[0,1]);" onkeyup="return showCodeListKey('uwPopedom',[this,uwPopedomname],[0,1]);"><Input class="codename" name=uwPopedomname readonly>
                  </td>
                  <td class=title></td>
                  <td class=input></td>
                  <td class=title>
                      核保师
                  </td>

                  <td class=input>
                      <Input class="codeno" name=uwPer ondblclick="return showCodeList('uwper',[this,uwPername],[0,1]);" onkeyup="return showCodeListKey('uwper',[this,uwPername],[0,1]);"><Input class="codename" name=uwPername readonly>
                  </td>
              </TR>
          </table>
<!--
  </div>
-->

<div id = "divSugUWResult" style = "display: 'none'">
  <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>建议核保结论：</td>
	  </tr>
	</table>
  <table class=common>
    	<tr class = common>
				<td class=title nowrap >
					建议结论
				</td>
				<td class =input>
					<Input class=codeno ondblclick="return showCodeList('contuwstate',[this,SugIndUWFlagName],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,SugIndUWFlagName],[0,1]);" name="SugIndUWFlag"><input type="text" class="codename" name="SugIndUWFlagName" readonly="readonly">
				</td>
				</tr><tr class="common">
				<td class=title nowrap >
					建议核保意见
				</td>
				<td class =input colspan=3>
					<!--Input class= common name="SugIndUWIdea"  -->
          <textarea name="SugIndUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea>
        </td>
	  	</tr>
  	</table>
<!--
  	<table  class= common align=center>

    	  	<tr  class= common>
          		<td height="29"  class= title>
          		 	<span id= "UWResult">核保结论<Input class="code" name=uwstate ondblclick="return showCodeList('uwstate',[this]);" onkeyup="return showCodeListKey('uwstate',[this]);">  </span>
	   			      <span id= "UWDelay" style = "display: 'none'">延长时间<Input class="common" name=UWDelay value= ""> </span>
	   		        <span id= "UWPopedom" style = "display: 'none'">上报核保<Input class="code" name=UWPopedomCode ondblclick="showCodeList('UWPopedomCode1', [this]);" onkeyup="return showCodeListKey('UWPopedomCode1', [this]);"> </span>
	   		 	    <span id= "Delay" style = "display: 'none'">本单核保师<Input class="common" name=UWOperater value= ""> </span>
	   		 	   </TD>
          </TR>
		<tr></tr>
          	<td height="24"  class= title>
            		核保意见
          	</TD>
		<tr></tr>
      		<td  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
	  </table>
-->

</div>
  	<div id = "divUWSave" style = "display: ''">
    		<INPUT VALUE="确  定" class=cssButton id="button1" style="display:" TYPE=button onclick="submitForm(0);">
    		<INPUT VALUE="返回下级" class=cssButton id="button2" style="display:none" TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="取  消" class=cssButton TYPE=button onclick="cancelchk();">
        <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="InitClick();">
        <INPUT class=cssButton VALUE="记事本查看" TYPE=button onclick="showNotePad();">
    </div>
  	<div id = "divUWAgree" style = "display: 'none'">
    		<INPUT VALUE="同  意" class=cssButton TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="不同意" class=cssButton TYPE=button onclick="submitForm(2);">
    		<INPUT VALUE="返  回" class=cssButton TYPE=button onclick="InitClick();">
  	</div>

    <div id = "divChangeResult" style = "display: 'none'">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		承保计划变更结论录入:
          	</TD>
		<tr></tr>
      		<td  class= input><textarea name="ChangeIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    	 </table>
    	 <INPUT VALUE="确  定" class=cssButton TYPE=button onclick="showChangeResult();">
    	 <INPUT VALUE="取  消" class=cssButton TYPE=button onclick="HideChangeResult();">
    </div>
  </Div>
  <table  class= common align=center>

	</table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
