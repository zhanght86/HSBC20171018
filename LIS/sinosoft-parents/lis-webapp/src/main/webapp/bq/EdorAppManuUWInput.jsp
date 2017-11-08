
<%
//程序名称：EdorAppManuUWInput.jsp
//程序功能：保全人工核保
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
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //保全受理号
    String tMissionID = request.getParameter("MissionID");          //任务ID
    String tSubMissionID = request.getParameter("SubMissionID");    //子任务ID
    String tActivityStatus = request.getParameter("ActivityStatus"); //当前活动状态
    String tEdorType = request.getParameter("EdorType");
    String tActivityID= request.getParameter("ActivityID"); 
    tActivityID=tActivityID.replace(" ", ""); 
//=====从工作流人工核保页面传递过来的参数=====END===================================
%>
<script>
	var EdorAcceptNo = "<%=tEdorAcceptNo%>";          //个人单的查询条件
	var MissionID = "<%=tMissionID%>";   //记录操作员
	var SubMissionID = "<%=tSubMissionID%>"; //记录管理机构
	var ActivityID = "<%=tActivityID%>"; //记录管理机构
	var ActivityStatus = "<%=tActivityStatus%>"; //记录登陆机构
	var EdorType = "<%=tEdorType%>";	
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
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./EdorAppManuUW.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="EdorAppManuUWInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form  action="./EdorAppManuUWSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <Div  id= "divEdorAppInfo1" style= "display: none">
      <table>
        <tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg> 保全受理信息 </td>
        </tr>
      </table>
      <Div  id= "divEdorAppInfo" class="maxbox1" style= "display: ''">
        <TABLE class=common>
        <!-- 显示保全受理的基本信息,包括变更金额 以供参考 -->
            <tr class=common>
                <td class=title> 保全受理号 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo id="EdorAcceptNo"></td>
                <td class=title> 客户/保单号 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=OtherNo id="OtherNo"></td>
                <td class=title> 号码类型 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=OtherNoTypeName id="OtherNoTypeName"></td>
            </tr>
            <tr class=common>
                <td class=title> 申请人 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=EdorAppName id="EdorAppName"></td>
                <td class=title> 申请方式 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=ApptypeName id="ApptypeName"></td>
                <td class=title> 管理机构 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=ManageComName id="ManageComName"></td>
            </tr>
            <tr class=common>
                <td class= title> 补/退费金额 </td>
                <td class= input><Input type="input" class="multiCurrency wid" readonly name=GetMoney id="GetMoney"></td>
                <td class=title> 补/退费利息 </td>
                <td class= input><Input type="input" class="multiCurrency wid" readonly name=GetInterest id="GetInterest"></td>
                <td class= title> </td>
                <td class= input> </td>
            </tr>
        </TABLE>
     </Div> 
    </Div>
    <Div  id= "divEdorMainInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                </td>
                <td class= titleImg> 申请任务列表信息 </td>
            </tr>
        </table>
        <Div id="divEdorMainGrid" style="display: ''">
            <table  class=common>
                <tr  class=common>
                    <td><span id="spanEdorMainGrid"></span></td>
                </tr>
            </table>
<!--            <div id="divTurnPageEdorMainGrid" align="center" style="display:none">
                <input type="button" class="cssButton90" value="首  页" onClick="turnPageEdorMainGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onClick="turnPageEdorMainGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onClick="turnPageEdorMainGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onClick="turnPageEdorMainGrid.lastPage()">
            </div>
-->        </div>
     </Div>
    <Div  id= "divReasonList" style= "display: ''">
    	<table class=common>
                <tr class=common>
                    <TD class=title colspan=6 > 强制人工核保原因说明栏 </TD>
                </tr>
                <tr class=common>
                    <TD  colspan="6" style="padding-left:16px" ><textarea name="InFo1" cols="226%" rows="4" witdh=100% readonly class="common"></textarea></TD>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 保全审批意见栏 </TD>
                </tr>
                <tr class=common>
                    <TD  colspan="6" style="padding-left:16px" ><textarea name="InFo2" cols="226%" rows="4" witdh=100% readonly class="common"></textarea></TD>
                </tr>
         </table>
    </Div>
    
    <!-- 显示保全受理的详细信息 -->
    <div id=DivDetailInfo1 style="display:none">
      <div id=DivDetailInfo style="display:none">
           <DIV id=DivLPContButton STYLE="display:''">
              <table id="table1">
               <tr>
                <td class="common">
                <img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLPCont);">
                </td>
                <td class="titleImg">合同信息
                </td>
               </tr>
             </table>
           </DIV>
          <div id="DivLPCont" class="maxbox1" STYLE="display:''">
            <table class="common" id="table2">
              <tr CLASS="common">
                <td CLASS="title" nowrap>保单号码
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="ContNo" id="ContNo" class="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
                </td>
                <td CLASS="title" nowrap>管理机构
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="lpManageCom" id="lpManageCom"  MAXLENGTH="10" class="readonly wid" readonly>
                </td>
                <td CLASS="title" nowrap>销售渠道
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="SaleChnl" id="SaleChnl" class="readonly wid" readonly MAXLENGTH="2">
                </td>
              </tr>
              <tr CLASS="common">
                <td CLASS="title">代理人编码
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="AgentCode" id="AgentCode" MAXLENGTH="10" class="readonly wid" readonly>
                </td>
                <td CLASS="title">其它声明
                </td>
                <td CLASS="input" COLSPAN="1">
                    <input NAME="Remark" id="Remark" class="readonly wid" readonly MAXLENGTH="255">
                </td>
                <td CLASS="title">
                </td>
                <td CLASS="input" COLSPAN="1">
                </td>
              </tr>
           </table>
         </div>                       
        </div>
      </div><!-- 合同信息不显示 -->
        
        <DIV id=DivLPAppntIndButton STYLE="display:''">
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
        <DIV id=DivLPAppntInd class="maxbox" STYLE="display:''">
          <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            姓名
	          </TD>
	          <td  class= input>
	            <Input class="readonly wid" readonly name="AppntName" id="AppntName" value="">
	          </TD>
	          <td  class= title>
	            性别
	          </TD>
	          <td  class= input>
	            <Input name=AppntSex class="readonly wid" type="hidden">
	            <Input name=AppntSexName id="AppntSexName" class="readonly wid" >
	          </TD>
	          <td  class= title>
	            年龄
	          </TD>
	          <td  class= input>
	          <input class="readonly wid" readonly  type=hidden name="AppntBirthday">
	          <input class="readonly wid" readonly name="AppntAge" id="AppntAge">
	          </TD>
	        </TR>

	        <tr  class= common>
	          <td  class= title>
	            职业
	          </TD>
	          <td  class= input>
	          <input class="readonly wid" readonly name="OccupationCode" type="hidden">
	          <input class="readonly wid" readonly name="OccupationCodeName" id="OccupationCodeName">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input class="readonly wid" readonly name="OccupationType" type="hidden">
	          <input class="readonly wid" readonly name="OccupationTypeName" id="OccupationTypeName">
	          </TD>
	         	<td  class= title>
           	 年收入
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency wid" readonly name=income id="income" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
           	 身高
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=Stature id="Stature" >
          	</TD>
	        	<td  class= title>
           	 体重
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=Weight id="Weight" >
          	</TD>
          	<td  class= title>
           	 BMI值
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=BMI id="BMI" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
	            国籍
	          </TD>
	          <td  class= input>
	          <input class="readonly wid" readonly name="NativePlace" type="hidden">
	          <input class="readonly wid" readonly name="NativePlaceName" id="NativePlaceName">
	          </TD>
	          <td  class= title>
	            累计寿险保额
	          </TD>
	          <td  class= input>
	          <input class="multiCurrency wid" readonly name="AppntSumLifeAmnt" id="AppntSumLifeAmnt" >
	          </TD>
	        	<td  class= title>
	           	 累计重大疾病保额
	          	</TD>
	          	<td  class= input>
	            	<Input class="multiCurrency wid" readonly name=AppntSumHealthAmnt id="AppntSumHealthAmnt" >
	          </TD>
	        </TR>
	        <tr>	        	
	         <td  class="title" nowrap>
            	累计医疗险保额
	           </TD>
	         	 <td  class= input>
	            	<Input class="multiCurrency wid" readonly name=AppntMedicalAmnt id="AppntMedicalAmnt" >
	           </TD>	      	
	      	  <td  class= title>
	            累计意外险保额
	          </TD>
	          <td  class= input>
	          <input class="multiCurrency wid" readonly name="AppntAccidentAmnt" id="AppntAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 累计风险保额
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency wid" readonly name=AppntSumAmnt id="AppntSumAmnt" >
          	</TD>          	
	        </tr>
	        <tr>	      	
          	<td  class= title>
           	 累计保费
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency wid" readonly name=SumPrem id="SumPrem">
          	</TD>
	        </tr>
	      </table>
	      <table>
			  <tr>
			    <td nowrap>			    
			    <INPUT VALUE=" 投保人健康告知查询 " class=cssButton TYPE=button name="uwButton9" onClick="queryHealthImpart()">
			    <INPUT VALUE=" 投保人既往投保资料查询 " class=cssButton TYPE=button name="uwButton30" onClick="showApp(1)">			
			    </td>
			  </tr>			  
		   </table>
        </DIV>
        
        <DIV id=DivOld1 STYLE="display:none">
	        <table >
	           <tr>
	             <td nowrap>
	               <!--INPUT VALUE=" 投保人既往投保信息 " class=cssButton TYPE=button onclick="showApp(1);"-->
	               <INPUT VALUE=" 投保人健康告知查询 " class=cssButton TYPE=button onClick="queryHealthImpart();">
	               <INPUT VALUE=" 投保人体检资料查询 " class=cssButton TYPE=button onClick="showBeforeHealthQ();">
	               <INPUT VALUE=" 投保人保额累计信息 " class=cssButton TYPE=button onClick="amntAccumulate();">
	               <!--<INPUT VALUE=" 投保人影像资料查询 " class=cssButton TYPE=button onclick=""> -->
	              </td>
	           </tr>
	           <tr>
	               <td nowrap>
	               <INPUT VALUE="投保人已承保保单查询" class=cssButton TYPE=button onClick="queryProposal();">
	               <INPUT VALUE="投保人未承保保单查询" class=cssButton TYPE=button onClick="queryNotProposal();">
	               <INPUT VALUE=" 投保人既往保全查询 " class=cssButton TYPE=button onClick="queryEdor();">
	              <INPUT VALUE=" 投保人既往理赔查询 " class=cssButton TYPE=button onClick="queryClaim();">
	               </td>
	          </tr>
	          <tr>
	               <td>
	               </td>
	          </tr>
	        </table>
        </DIV>
        <Div  id= "divLPPolButton" style= "display: ''" >
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
        <div id="divLPPol" style="display:''">
                <table class=common>
                   <tr class=common>
                     <td><span id="spanPolAddGrid"></span></td>
                   </tr>
                </table>
         </div>
         <!--被保人信息明细-->
    	<jsp:include page="EdorInsuredShow.jsp"/>
         <table>
		  <tr>
		    <td nowrap>
		          <input value=" 体检通知录入 "        class=cssButton type=button name="uwButton16" onClick="showHealth();" width="200">
		          <input value=" 生调申请录入 "        class=cssButton type=button name="uwButton17" onClick="showRReport();">		          
		          <INPUT VALUE="  问题件录入  " class=cssButton TYPE=button onClick="QuestInput();" name="uwButtonQU">
		          <input VALUE="核保分析报告"  class=cssButton name="ReportButton" TYPE=button onClick="showUWReport();">		          
		          <INPUT VALUE="  记事本  " class=cssButton id="NotePadButton" TYPE=button onClick="showNotePad();">
		    </td>
		  </tr>
		  <tr>
		    <td nowrap>
		          <input value=" 查询体检结果 "  class="cssButton" type="button" name=uwButton21 onClick="queryHealthReportResult();">
		          <input value=" 查询生调结果 "  class="cssButton" type="button" name=uwButton22 onClick="queryRReportResult();">
		          <input value="  查询问题件  " class=cssButton type=button name=uwButtonIs onClick="QuestQuery();">
		          <INPUT VALUE="二核结果查询" class=cssButton type=hidden name=uwButtonTe onClick="QueryTwiceEdorResult();">
		    	  <INPUT VALUE="保全明细查询" class=cssButton TYPE=button onClick="EdorDetailQuery()">                 
	                <INPUT VALUE="保单信息查询" class=cssButton  TYPE=button onClick="ContInfoQuery()">
	                <INPUT VALUE="影像资料查询" class=cssButton  TYPE=button onClick="ScanQuery()">
	                <INPUT VALUE="操作履历查询" class=cssButton TYPE=button onClick="MissionQuery()">
	                <INPUT VALUE="自动核保信息" class=cssButton TYPE=button onClick="showNewUWSub()">  			 
		    </td>
		  </tr>
		  <tr>
            <td nowrap>
              <!--   <INPUT VALUE="保全影像查询" class=cssButton  TYPE=button onclick="ScanDetail()">  -->
               <!--  <INPUT VALUE="保全核保影像" class=cssButton  TYPE=button onclick="UWScanQuery()">   -->                
              <!--   <INPUT VALUE=" 核保查询 " class=cssButton TYPE=button onclick="UWQuery()">   -->
                
                <!--INPUT VALUE="投保操作履历查询" class=cssButton TYPE=button onclick="QueryRecord()"-->
            </td>
           </tr>
		  <tr>
		  	<td nowrap>		  		    		          
		  		    <input value="体检医院品质管理"   class=cssButton type=hidden name="uwButtonHospital" onClick="HospitalQuality();" >
		  		    <input value="业务员品质管理"   class=cssButton type=hidden name="uwButton26" onClick="AgentQuality();" >
		  		    <input value=" 客户品质管理 "   class=cssButton type=hidden name="uwButton25" onClick="CustomerQuality();" >
		  		 <!--    <input value="  问题件差错  " class=cssButton type=button name="wButtonQuestErr" onclick="IssueMistake();"> -->
		            <input value=" 通知书状态查询 " class="cssButton" type="button" name="uwButton23" onClick="SendAllNotice()">
		  		<!-- <input value="撤单申请查询" name=uwButton28 class=cssButton type=button onclick="BackPolQuery();"> -->
		  	 </td>
		  </tr>
		</table>        
     
         <Div  id= "divFormerContUWInfo" style= "display: none">
               <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFormerContUW);">
                    </td>
                    <td class= titleImg> 原保单核保结论 </td>
                </tr>
               </table>
           <Div  id= "divFormerContUW" style= "display: ''">
             <table  class= common>
                <tr class=common>
                    <td class=title> 原保单核保结论 </td>
                    <td class=input><Input class="codeno" name=FormerUWState id="FormerUWState" readonly=true><input class=codename name=FormerUWStateName readonly=true></td>
                    <td class=title> </td>
                    <td class=input></td>
                    <td class=title> </td>
                    <td class=input><Input class="common wid" type=hidden name=FormerPopedomCode></td>
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
         <Div  id= "divEdorAppUWResultInfo" style= "display: ''">
          <Div  id= "divContUWLable" style= "display: ''">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppUWInfo);">
                    </td>
                    <td class= titleImg> 合同核保结论 </td>
                </tr>
               </table>
           </Div>           
           <Div  id= "divEdorAppUWInfo" class="maxbox1" style= "display: ''">
             <table  class= common>
                <tr class=common>
                    <td class=title5> 核保结论 </td>
                    <td class=input5><Input class="codeno" name=EdorUWState id="EdorUWState" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="initUWState(this, edoruwstateName)" onDblClick="initUWState(this, edoruwstateName)" onKeyUp="onKeyUpUWState(this, edoruwstateName);"><input class=codename name=EdorUWStateName id="edoruwstateName" readonly=true></td>
                    <td class=title5>
                    <div id = "divUwDelayTitle" style= "display: none">
                    延长时间
                    </div>
                    </td>
                    <td class=input>
                    <div id = "divUwDelay" style= "display: none">
                    <Input class="multiDatePicker" name=UWDelay id="UWDelay" onClick="laydate({elem:'#UWDelay'});"><span class="icon"><a onClick="laydate({elem: '#UWDelay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </div>
                    </td>
                    <!--td class=title> 上报核保 </td-->
                    <td class=input5><Input class="common wid" type=hidden name=UWPopedomCode id="UWPopedomCode"></td>
                </tr>
                </table>
                <table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > 核保意见 </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan="6" style="padding-left:16px" ><textarea name="UWIdea" id="UWIdea" cols="226%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
              </table>
            </DIV>
            <br>
            <INPUT VALUE=" 确 定 " class=cssButton TYPE=button name=uwconfirm onClick="UWSubmit();" >
            <INPUT VALUE=" 清 空 " class=cssButton TYPE=button name=uwcancel onClick="UWcancel();">
            <hr class="line">
            <Div  id= "divAppUWLable" style= "display: none">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppUWInfo);">
                    </td>
                    <td class= titleImg> 保全核保结论 </td>
                </tr>
               </table>
           </DIV>
           <Div  id= "divAppUWInfo" style= "display: none">
             <table  class= common>
                <tr class=common>
                    <td class=title5> 核保结论 </td>
                    <td class=input5><Input class="codeno" name=AppUWState id="AppUWState" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="showCodeList('edorappstate',[this,AppUWStateName],[0,1])"  ondblclick="showCodeList('edorappstate',[this,AppUWStateName],[0,1])" onKeyUp="showCodeListKey('edorappstate',[this,AppUWStateName],[0,1])"><input class=codename name=AppUWStateName id="AppUWStateName" readonly=true></td>
                    <td class=title5>  </td>
                    <td class=input5>  </td>                  
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > 核保意见 </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="AppUWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
                </tr>
              </table>
            </DIV> 
            <Div  id= "divSendNotice" style= "display: none">
             <table  class= common>
                <tr class=common>
                    <td class=title5> 打印核保通知书 </td>
                    <td class=input5><Input class="codeno" name=SendNoticeFlag id="SendNoticeFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center rihgt; " readonly  onClick="showCodeList('ibrmsflag',[this,SendNoticeFlagName],[0,1])" onDblClick="showCodeList('ibrmsflag',[this,SendNoticeFlagName],[0,1])" onKeyUp="showCodeListKey('ibrmsflag',[this,SendNoticeFlagName],[0,1])"><input class=codename name=SendNoticeFlagName id="SendNoticeFlagName" readonly=true></td> 
                    <td class=title5>  </td>
                    <td class=input5>  </td>                
                </tr>                
              </table>
            </DIV>           
            <INPUT VALUE=" 核保完成 " class=cssButton TYPE=button onClick="returnUWApp();">
            <INPUT VALUE=" 返 回 " class=cssButton TYPE=button onClick="returnParent();">
            
         </DIV>

    <!-- 隐藏域-->
    <input type=hidden id="MissionID"       name= "MissionID">
    <input type=hidden id="SubMissionID"    name= "SubMissionID">
    <input type=hidden id="ActivityStatus"  name= "ActivityStatus">
    <input type=hidden id="UWType"          name= "UWType">
    <input type=hidden id="EdorNo"          name= "EdorNo">
    <input type=hidden id="ActionFlag"      name= "ActionFlag">
    <input type=hidden id="AppntNo"         name= "AppntNo">
    <input type=hidden id="InsuredNo"         name= "InsuredNo">
    <input type=hidden id="PrtNo"           name= "PrtNo">
    <input type=hidden id="ProposalContNo"  name= "ProposalContNo">
    <input type=hidden id="OtherNoType"     name= "OtherNoType">
    <input type=hidden id="Apptype"         name= "Apptype">
    <input type=hidden id="ManageCom"       name= "ManageCom">
    <input type=hidden id="AppntNamew"      name= "AppntNamew">
    <input type=hidden id="PaytoDate"       name= "PaytoDate">
    <input type=hidden id="EdorType"        name= "EdorType">
    <input type=hidden id="ActivityID"      name= "ActivityID">
    
    <input type= "hidden" name= "hiddenNoticeType" value= "UWSENDALL">
  </form>
<Br><Br><Br><Br><Br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
