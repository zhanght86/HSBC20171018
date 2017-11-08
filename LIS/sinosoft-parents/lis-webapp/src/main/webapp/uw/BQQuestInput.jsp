<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：QuestInput.jsp
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
   <%@page import="com.sinosoft.lis.vschema.*"%>
   <%@page import="com.sinosoft.lis.db.*"%>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="BQQuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>核保问题件录入</title>
  <%@include file="BQQuestInputInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./BQQuestInputChk.jsp">
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);">
			</td>
			<td class= titleImg>待发送问题件信息</td>
		</tr>
	</table>
	<table>
 <Div  id= "asd" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
        <center>
    	<input CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();"></center>
    </div>
  </table>		
  <table  class=common >
		<tr class=common>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);">
			</td>
			<td class= titleImg>问题件信息</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
	<table class = common>
    <tr class = common>
	        <TD class= title>
            发送对象  
          </TD>
          <TD class="input">
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name = BackObj id = BackObj readonly onDblClick="return showCodeList('bqbackobj',[this,BackObjName],[0,1]);" onClick="return showCodeList('bqbackobj',[this,BackObjName],[0,1]);" onKeyUp="return showCodeListKey('bqbackobj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName id=BackObjName readonly = true>
          </TD> 
          
          <TD class= title>
    	  		是否下发
    			</TD>
        	<TD class="input" >
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=NeedPrintFlag id=NeedPrintFlag CodeData = "0|^Y|下发^N|不下发" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onKeyUp="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName  id=IFNeedFlagName readonly = true>
        	</TD>
        	<!-- <TD  class= title>
    	  		差错类型
    			</TD> 
        	<TD   class= input>--><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden class="hidden" name=ErrorType id=ErrorType  CodeData = "0|^1|不计入考核^2|保费计算有误^3|不符合投保规则^4|告知漏项^5|个人资料^6|计划填写^7|签名信息^8|身份证号^9|职业填写^10|其他" ondblclick= "showCodeListEx('ErrorType',[this,ErrorTypeName],[0,1]);" onclick= "showCodeListEx('ErrorType',[this,ErrorTypeName],[0,1]);" onKeyUp="showCodeListKeyEx('ErrorType',[this,ErrorTypeName],[0,1]);" ><Input class=hidden type=hidden name=ErrorTypeName id=ErrorTypeName readonly ="readonly" >
          <TD class=title> 接收对象 </TD>  
          <TD class="input"  ><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= "codeno" name = QuestionObj id=QuestionObj onDblClick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onClick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onKeyUp="return showCodeListKey('question',[this,QuestionObjName],[0,1]);" ><Input class = codename name=QuestionObjName id=QuestionObjName readonly = true> </TD></tr>
        </table>
        </Div>
		<div id= "divCustomerqustion" style= "display: none" >
    <table class = common >
    	<TR  class= common>
    		  
          
         
                     
          <TD  class = title> 客户号码 </TD>
          <TD class="input"  ><Input class="wid" class=common  name=CustomerNo id=CustomerNo ></TD>
           
          <TD  class = title>	客户姓名 </TD>
          <TD class="input"  ><Input class="wid" class=common  name=CustomerName id=CustomerName ></TD>
          <TD  class= title>
    	  问题件选择
    	</TD>
        <TD class="input" ><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center rihgt" class=code name=Quest id=Quest readonly 
        	ondblclick= "getClickIssueCode(Quest,Content);" onclick= "getClickIssueCode(Quest,Content);" onKeyUp="getKeyUpIssueCode(Quest,Content);" >
        </TD>
          </TR>     
          
     </table>
    </div>
    
    
   
    <div id ="divQuestionnaireFlag" style ="display :none">
     <table class = common >
         <tr class=common>
        	<TD  class= title>
    	 	 是否录入问卷
    		</TD><TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=Questionnaire id=Questionnaire  CodeData = "0|^Y|是^N|否" ondblclick= "showCodeListEx('Questionnaire',[this,QuestionnaireName],[0,1]);" onclick= "showCodeListEx('Questionnaire',[this,QuestionnaireName],[0,1]);" onKeyUp="showCodeListKeyEx('Questionnaire',[this,QuestionnaireName],[0,1]);" ><Input class=codename  name=QuestionnaireName id=QuestionnaireName readonly ="readonly" >
        	</TD>
            <TD  class= title></TD>
            <TD  class= input></TD><TD  class= title></TD>
            <TD  class= input></TD>
         </tr>
        </div>
     
    <div id="divQuestionnaire" style="display:none">
    <table class=common>
  <jsp:include page="Questionnaire.jsp"/>
  </table>
  </div>
  <table class = common>
    <TR  class= common> 
      <TD  class= title> 问题件内容 </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="Content" id="Content" cols="200" rows="4" class="common"></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="MissionID" name= "MissionID" value= "">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type= "hidden" id="hiddenQuestionOperator" name= "hiddenQuestionOperator" value="">
    <input type= "hidden" id="hiddenQuestionSeq" name= "hiddenQuestionSeq" value="">
    <input type= "hidden" id="hiddenProposalContNo" name= "hiddenProposalContNo" value="">
    <input type= "hidden" id="hiddenBackObj" name= "hiddenBackObj" value="">
    <input type= "hidden" id="hiddenQuestionPrint" name= "hiddenQuestionPrint" value="">
    <input type= "hidden" id="hiddenQuestionState" name= "hiddenQuestionState" value="">
    <input type= "hidden" id="FinanceO" name= "FinanceO" value="">
    <input type= "hidden" id="Occupation" name= "Occupation" value="">
    <input type= "hidden" id="HealthCheck" name= "HealthCheck" value="">
    <input type= "hidden" id="EdorNo" name= "EdorNo" value="">
    <input type= "hidden" id="EdorType" name= "EdorType" value="">
     
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" id="sure" name= "sure" class= cssButton  value="新 增" onClick="submitForm('0')">
<!--input type= "button" name= "sure" class= cssButton  value="删 除" onClick="submitForm('1')"-->
<input type= "button" id="sure" name= "sure" class= cssButton  value="修改下发标记" onClick="submitForm('2')">
<input type= "button" id="sure" name= "sure" class= cssButton  value="返 回" onClick="top.close();"><br><br><br><br>
</body>
</html>
