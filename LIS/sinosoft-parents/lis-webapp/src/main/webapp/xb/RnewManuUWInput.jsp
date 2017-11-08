
<%
//程序名称：RnewManuUWInput.jsp
//程序功能：续保人工核保
//创建日期：2005-05-04 16:20:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期      更新原因/内容
//            liurx     2005-06-17      界面一致、按钮功能开发
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>

<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

//=====从工作流人工核保页面传递过来的参数=====BGN===================================

    String tPrtNo = request.getParameter("PrtNo");  
    String tContNo = request.getParameter("ContNo");          //任务ID
    loggerDebug("RnewManuUWInput","tContNo:"+tContNo);

//=====从工作流人工核保页面传递过来的参数=====END===================================
%>

<script>
	var operator = "<%=tG.Operator%>";   //记录操作员
	var manageCom = "<%=tG.ManageCom%>"; //记录管理机构
	var comcode = "<%=tG.ComCode%>"; //记录登陆机构
</script>

<html>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./RnewManuUW.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="RnewManuUWInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form  action="./RnewManuUWSave.jsp" method=post name=fm id="fm" target="fraSubmit"> 

    <Div  id= "divEdorMainInfo" style= "display:  ">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                </td>
                <td class= titleImg> 申请任务列表信息 </td>
            </tr>
        </table>
        <Div id="divEdorMainGrid" style="display: ">
            <table  class=common>
                <tr  class=common>
                    <td><span id="spanEdorMainGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageEdorMainGrid" align="center" style="display:none;">
<!--                <input type="button" class="cssButton90" value="首  页" onClick="turnPageEdorMainGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onClick="turnPageEdorMainGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onClick="turnPageEdorMainGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onClick="turnPageEdorMainGrid.lastPage()">
-->            </div>
        </div>

    </Div>
    <!-- 显示保全受理的详细信息 -->

      <div id=DivDetailInfo style="display: ">
          <!--
           <DIV id=DivLPContButton STYLE="display: ">
              <table id="table1">
               <tr>
                <td>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLPCont);">
                </td>
                <td class="titleImg">合同信息
                </td>
               </tr>
             </table>
           </DIV>
          <div id="DivLPCont" STYLE="display: ">
            <table class="common" id="table2">
              <tr CLASS="common">
                <td CLASS="title" nowrap>保单号码
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="ContNo" CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
                </td>
                <td CLASS="title" nowrap>管理机构
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="lpManageCom"  MAXLENGTH="10" CLASS="readonly wid" readonly>
                </td>
                <td CLASS="title" nowrap>销售渠道
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="SaleChnl" CLASS="readonly wid" readonly MAXLENGTH="2">
                </td>
              </tr>
              <tr CLASS="common">
                <td CLASS="title">代理人编码
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
                </td>
                <td CLASS="title">其它声明
                </td>
                <td CLASS="input" COLSPAN="1">
                    <input NAME="Remark" CLASS="readonly wid" readonly MAXLENGTH="255">
                </td>
                <td CLASS="title">
                </td>
                <td CLASS="input" COLSPAN="1">
                </td>
              </tr>
           </table>
         </div>
        -->         
        <!--
         <table  >
           <tr>
            <td nowrap>
                <INPUT VALUE="保全影像查询" class=cssButton  TYPE=button onclick="ScanDetail()">
                <INPUT VALUE="保全核保影像" class=cssButton  TYPE=button onclick="UWScanQuery()">
                <INPUT VALUE="自动核保信息" class=cssButton TYPE=button onclick="showNewUWSub()">
                <INPUT VALUE=" 核保查询 " class=cssButton TYPE=button onclick="UWQuery()">
                <INPUT VALUE="保全明细查询" class=cssButton TYPE=button onclick="EdorDetailQuery()">
                <INPUT VALUE="投保单影像查询" class=cssButton  TYPE=button onclick="ScanQuery()">
                <INPUT VALUE="保单信息查询" class=cssButton  TYPE=button onclick="ContInfoQuery()">
                <INPUT VALUE="保全操作轨迹" class=cssButton TYPE=button onclick="MissionQuery()">
                <INPUT VALUE="投保操作履历查询" class=cssButton TYPE=button onclick="QueryRecord()">
           -->
            </td>
           </tr>
         </table>
						<DIV id=DivLCAutoUWButton STYLE="display: ">
						<!-- 自动核保提示信息部分 -->
						<table>
						<tr>
						<td class="common">
						<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAutoUW);">
						</td>
						<td class= titleImg>
						自动核保提示信息
						</td>
						</tr>
						</table>
						</DIV>
						
						<DIV id=DivLCAutoUW STYLE="display: ">
							<table  class= common>
					        	<tr  class= common>
					    	  		<td style=" text-align: left" colSpan=1 >
										<span id="spanUWErrGrid" >
										</span> 
									</td>
								</tr>
							</table>
                        <center>
					     <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();">
							<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();">
							<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();">
							<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();">
                        </center>
						</div>
						  <p>
						  	  <INPUT VALUE="审阅完毕" class= cssButton TYPE=button name='AutoUWButton' onClick="submitFormUW();"> 					
					    </P>

        <DIV id=DivLPAppntIndButton STYLE="display: ">
           <!-- 投保人信息部分 -->
           <table>
             <tr>
                  <td class="common">
                     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPAppntInd);">
                  </td>
                  <td class= titleImg>
                       投保人信息
                  </td>
             </tr>
           </table>
        </DIV>
        <DIV id=DivLPAppntInd class="maxbox1" STYLE="display: ">
          <table  class= common>
            <TR  class= common>
              <TD  class= title>
                姓名
              </TD>
              <TD  class= input>
                <Input CLASS="readonly wid" readonly name="AppntName" id="AppntName" value="">
              </TD>
              <TD  class= title>
                性别
              </TD>
              <TD  class= input>
                <Input CLASS="readonly wid" readonly type=hidden name="AppntSex" id="AppntSex" >
                <Input CLASS="readonly wid" readonly name="AppntSexName" id="AppntSexName" >
                
              </TD>
              <TD  class= title>
                年龄
              </TD>
              <TD  class= input>
              <input CLASS="readonly wid" readonly name="AppntBirthday" id="AppntBirthday">
              </TD>
            </TR>
 
            <tr  class= common>
	            <Input CLASS="readonly wid" readonly type=hidden name="AppntNo" id="AppntNo" value="">
	            <Input CLASS="readonly wid" readonly  type=hidden name="AddressNo" id="AddressNo">
	          <td  class= title>
	            职业
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="OccupationCode" id="OccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="OccupationCodeName" id="OccupationCodeName">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="OccupationType" id="OccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="OccupationTypeName" id="OccupationTypeName">
	          </TD>
	         	<td  class= title>
           	 年收入
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=income id="income" >
          	</TD>
	        </TR>
	        
          <tr>
	        	<td  class= title>
           	 身高
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=Stature id="Stature" > 
          	</TD>
	        	<td  class= title>
           	 体重
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=Weight id="Weight" >
          	</TD>
          	<td  class= title>
           	 BMI值
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=BMI id="BMI" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
	            国籍
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="NativePlace" id="NativePlace" type="hidden">
	          <input CLASS="readonly wid" readonly name="NativePlaceName" id="NativePlaceName">
	          </TD>
	          <td  class= title>
	            累计寿险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntSumLifeAmnt" id="AppntSumLifeAmnt" >
	          </TD>
	        	<td  class= title>
           	 累计重大疾病保额
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=AppntSumHealthAmnt id="AppntSumHealthAmnt" >
          	</TD>	        	
	        </TR>
	        
	        <tr>        	
	         <td  class="title" nowrap>
            	累计医疗险保额
           </TD>
         	 <td  class= input>
            	<Input CLASS="readonly wid" readonly name=AppntMedicalAmnt  id="AppntMedicalAmnt">
           </TD>        
           <td  class= title>
	            累计意外险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntAccidentAmnt" id="AppntAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 累计风险保额
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=AppntSumAmnt id="AppntSumAmnt" >
          	</TD>
	      	</tr>	
          </table>
        </DIV>

        <table>
           <tr>
             <td nowrap>
               <INPUT VALUE=" 投保人健康告知查询 " class=cssButton TYPE=button onClick="queryHealthImpart();">
               <INPUT VALUE=" 投保人既往投保资料查询 " class=cssButton TYPE=button onClick="showApp(1);">
               <!--
               <INPUT VALUE=" 投保人体检资料查询 " class=cssButton TYPE=button onclick="showBeforeHealthQ();">
               <INPUT VALUE=" 投保人保额累计信息 " class=cssButton TYPE=button onclick="amntAccumulate();">
               <INPUT VALUE=" 投保人影像资料查询 " class=cssButton TYPE=button onclick=""> 
               -->
              </td>
           </tr>
           <!--
           <tr>
               <td nowrap>
               <INPUT VALUE="投保人已承保保单查询" class=cssButton TYPE=button onclick="queryProposal();">
               <INPUT VALUE="投保人未承保保单查询" class=cssButton TYPE=button onclick="queryNotProposal();">
               <INPUT VALUE=" 投保人既往保全查询 " class=cssButton TYPE=button onclick="queryEdor();">
              <INPUT VALUE=" 投保人既往理赔查询 " class=cssButton TYPE=button onclick="queryClaim();">
               </td>
          </tr>
          -->
          <tr>
               <td>
               </td>
          </tr>
        </table>

        <Div  id= "divLPPolButton" style= "display:  " >
               <table>
                 <tr>
                    <td class=common>
                       <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPPol);">
                    </td>
                    <td class= titleImg>
                        被保人信息
                    </td>
                 </tr>
                 </table>
        </div>
        <div id="divLPPol"  style="display: ">
                <table class=common>
                   <tr class=common>
                     <td><span id="spanPolAddGrid"></span></td>
                   </tr>
                </table>
         </div>
          <jsp:include page="RnewUWInsuredShow.jsp"/>

         <table>
           <tr>
             <td>
               <input value="体检通知录入" class=cssButton type=button onClick="showHealth();" width="200">
               <input value="生调申请录入" class=cssButton type=button onClick="showRReport();">
               <INPUT VALUE="  问题件录入  " class=cssButton TYPE=button onClick="QuestInput();" name="uwButtonQU">
               <input VALUE="核保分析报告"  class=cssButton name="ReportButton" TYPE=button onClick="showUWReport();">
               <input value=" 记 事 本 " class=cssButton type=button onClick="showNotePad();" width="200">
               <!--input value="加费承保录入" class=cssButton type=button name= "AddFee"  onclick="showAdd();">
               <input value="特约承保录入" class=cssButton type=button onclick="showSpec();"-->        
             </td>
           </tr>
           <tr>
             <td >
               <input value=" 查询体检结果 "  class="cssButton" type="button" name=uwButton21 onClick="queryHealthReportResult();">
               <input value=" 查询生调结果 "  class="cssButton" type="button" name=uwButton22 onClick="queryRReportResult();">
               <input value="  问题件查询  " class=cssButton type=button onClick="QuestQuery();">
               <!--INPUT VALUE=" 投保单信息查询 " class=cssButton TYPE=button name="uwButton1" onclick="showPolDetail();"-->
               <INPUT VALUE="保单信息查询" class=cssButton  TYPE=button onClick="ContInfoQuery()">
               <INPUT VALUE="  影像资料查询  " class=cssButton TYPE=button name="uwButton2" onClick="ScanQuery();">
               <INPUT VALUE="操作履历查询" class=cssButton TYPE=button name="uwButton6" onClick="QueryRecord()">                           
             </td>           
           </tr>
           <tr>
             <td>
             	 <INPUT VALUE="自动核保信息查询" class=cssButton TYPE=hidden name="AutoUWDetail" onClick="alert('该功能未提供(QueryAutoUWDetail())');"> 
               <input value="体检医院品质管理"   class=cssButton type=button name="uwButtonHospital" onClick="HospitalQuality();" >
  		         <input value="业务员品质管理"   class=cssButton type=button name="uwButton26" onClick="AgentQuality();" >
  		         <input value=" 客户品质管理 "   class=cssButton type=button name="uwButton25" onClick="CustomerQuality();" >
  		         <input value="  问题件差错  " class=cssButton type=button name="wButtonQuestErr" onClick="IssueMistake();">
  		         <input value=" 核保通知书发送状态查询 " class="cssButton" type="button" name="uwButton23" onClick="SendAllNotice_MS()">
               <!--input value="业务员品质管理" class=cssButton type=button onclick="AgentQuality();" -->
             </td>
           </tr>
          </table>

    </div>
         <Div  id= "divFormerContUWInfo" style= "display: none">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFormerContUW);">
                    </td>
                    <td class= titleImg> 原保单核保结论 </td>
                </tr>
               </table>
           <Div  id= "divFormerContUW" class="maxbox1" style= "display:  ">
             <table  class= common>
                <tr class=common>
                    <td class=title> 原保单核保结论 </td>
                    <td class=input><Input class="codeno" name=FormerUWState id="FormerUWState" readonly=true><input class=codename name=FormerUWStateName id="FormerUWStateName" readonly=true></td>
                    <td class=title></td>
                    <td class=input></td>
                    <td class=title></td>
                    <td class=input><Input class="common wid" type=hidden name=FormerPopedomCode id="FormerPopedomCode"></td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 原保单核保意见 </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="FormerUWIdea" readonly cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
                </tr>
              </table>
            </DIV>
         </DIV>
         <Div  id= "divRnewUWResultInfo" style= "display:  ">
          <Div  id= "divContUWLable" style= "display:  ">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRnewUWInfo);">
                    </td>
                    <td class= titleImg> 保单核保结论 </td>
                </tr>
               </table>
           </Div>
           <Div  id= "divAppUWLable" style= "display: none">
               <table>
                <tr>
                    <td class= common>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRnewUWInfo);">
                    </td>
                    <td class= titleImg> 保全申请核保结论 </td>
                </tr>
               </table>
           </DIV>
           <Div  id= "divRnewUWInfo" class="maxbox1" style= "display:  ">
             <table  class= common>
                <tr class=common>
                    <td class=title5> 核保结论 </td>
                    <td class=input5><Input class="codeno" name=EdorUWState id="EdorUWState" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="initUWState(this, edoruwstateName)" 
                    	ondblclick="initUWState(this, edoruwstateName)" 
                    	onkeyup="onKeyUpUWState(this, edoruwstateName);">
                    	<input class=codename name=EdorUWStateName id="EdorUWStateName" readonly=true></td>
                    <td class=title5>
                    <div id = "divUwDelayTitle" style= "display: none">
                    延长时间
                    </div>
                    </td>
                    <td class=input>
                    <div id = "divUwDelay" style= "display: none">
                    <Input class="coolDatePicker" name=UWDelay id="UWDelay" onClick="laydate({elem:'#UWDelay'});"><span class="icon"><a onClick="laydate({elem: '#UWDelay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </div>
                    </td>
                    <!--td class=title> 上报核保 </td-->
                    <td class=input5><Input class="common wid" type=hidden name=UWPopedomCode id="UWPopedomCode"></td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 核保意见 </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="UWIdea" id="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
                </tr>
              </table>
            </DIV>
             <br>
	            <INPUT VALUE=" 确 定 " class=cssButton TYPE=button onClick="UWSubmit();">
	            <INPUT VALUE=" 清 空 " class=cssButton TYPE=button onClick="UWcancel();">
	            <INPUT VALUE=" 下核保结论 " class=cssButton TYPE=hidden onClick="window.location.reload();">
             </br>
             <br>
	            <INPUT VALUE=" 核保完成 " class=cssButton TYPE=button onClick="FinishSubmit();">
	            <INPUT VALUE=" 返 回 " class=cssButton TYPE=button onClick="returnParent();">
            </br>
         </DIV>

    <!-- 隐藏域-->
    <input type=hidden id="ContNo"       name= "ContNo">
    <input type=hidden id="ProposalNo"       name= "ProposalNo">
    <input type=hidden id="RiskCode"       name= "RiskCode">
    <input type=hidden id="MissionID"       name= "MissionID">
    <input type=hidden id="SubMissionID"    name= "SubMissionID">
    <input type=hidden id="ActivityStatus"  name= "ActivityStatus">
    <input type=hidden id="UWType"          name= "UWType">
    <input type=hidden id="EdorNo"          name= "EdorNo">
    <input type=hidden id="ActionFlag"      name= "ActionFlag">
    <input type=hidden id="PrtNo"           name= "PrtNo">
    <input type=hidden id="ProposalContNo"  name= "ProposalContNo">
    <input type=hidden id="OtherNoType"     name= "OtherNoType">
    <input type=hidden id="Apptype"         name= "Apptype">
    <input type=hidden id="ManageCom"       name= "ManageCom">
    <input type=hidden id="AppntNamew"      name= "AppntNamew">
    <input type=hidden id="PaytoDate"       name= "PaytoDate">
    <input type=hidden id="EdorType"        name= "EdorType">
    <input type= "hidden" name= "hiddenNoticeType" value= "UWSENDALL">
  </form>
<br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
