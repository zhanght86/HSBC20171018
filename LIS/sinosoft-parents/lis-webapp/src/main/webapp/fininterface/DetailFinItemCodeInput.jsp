 <html> 
 	<%
//创建日期：2008-08-11
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="java.util.*"%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String mStartDate = "";
	String mSubDate = "";
	String mEndDate = "";
        GlobalInput tGI = new GlobalInput();
        //PubFun PubFun=new PubFun();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("DetailFinItemCodeInput","1"+tGI.Operator);
	loggerDebug("DetailFinItemCodeInput","2"+tGI.ManageCom);
%>
<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
	var FinItemID = <%=request.getParameter("FinItemID")%>;
	var JudgementNo = <%=request.getParameter("JudgementNo")%>;
</script>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="DetailFinItemCodeInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="DetailFinItemCodeInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm   target=fraSubmit method=post>
	 <INPUT type=hidden name=hideOperate value=''>
    <INPUT type=hidden name=VersionNo value=''> 
    <INPUT type=hidden name=VersionState value=''> 
    <INPUT type=hidden name=FinItemID value=''>    
    <INPUT type=hidden name=JudgementNo value=''>           
    
	 <table>
    	<tr>    		 
    		 <td class= titleImg>
        		 明细科目分支影射定义
       	 </td>   		 
    	</tr>
    </table>

  <Div  id= "divDetailCodeQuery"  style= "display: ''">
      <table  class= common>
       	<tr  class= common>
      	  <td text-align: left colSpan=1>
  					<span id="spanDetailCodeGrid" >
  					</span>
  			  </td>
  			</tr>
    	</table>
    <Div  id= "divPage" align=center style= "display: none ">      
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
    </Div>
  </Div>
  <br>
  
   <hr></hr>
      
<table class= common border=0 width=100%>
  <table class= common>			
  	
			<tr class= common>
				<!--TD class= title>
					  判断条件序号
				</TD>
				<TD class=input>
				 	<Input class=common name=JudgementNo  elementtype=nacessary>
				</TD-->
				<TD class= title5>
		   	   层级条件组合数值
		    </TD>
		    <TD class=input5>
				 	 <Input class="wid common" id=LevelConditionValue name=LevelConditionValue  elementtype=nacessary verify="层级条件组合数值|len<=500">
				</TD>				
				<TD class= title5>
					  层级科目代码
				</TD>
				<TD class=input5>
				 	 <Input class="wid common" id=LevelCode name=LevelCode  verify="层级科目代码|len<=20" >
				</TD>				
			</tr>
			<!--<%
			String VersionNo =request.getParameter("VersionNo");
			loggerDebug("DetailFinItemCodeInput",VersionNo);
			String FinItemID =request.getParameter("FinItemID");
			 
			%>-->
		  <tr class= common>
				<TD class= title5>
		   	   下级科目判断条件
		    </TD>
		    <td class=input5><!--<Input class="code" name=NextJudgementNo  ondblclick="return showCodeList('NextJudgementNo',[this],[0,1],null,"1 AND VersionNo=#0001# AND FinItemID=#"0002"#",1,1);" onkeyup=" return showCodeListKey('NextJudgementNo',[this],[0,1],null,<%=VersionNo%>,1,1);">-->
		    	<Input class="code" id=NextJudgementNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  name=NextJudgementNo verify="下级科目判断条件|NOTNULL" readonly=true 
            ondblclick="initNextJudgementNo(this);" onkeyup="actionKeyUp(this);" elementtype=nacessary>  
          </TD>
				<TD class= title5>
					  条件组合数值描述
				</TD>
				<TD class=input5>
				 	 <Input class="wid common" id=ReMark name=ReMark  verify="条件组合数值描述|len<=500" >
				</TD>				
			</tr>								  					 		  
			
    </table>         
    					 
  	<p>       
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">   
    <INPUT VALUE="重  置" TYPE=button class= cssButton name="resetbutton" onclick="return resetAgain();">   
 <br /> <br /> <br /> <br />
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>

</body>
</html>
