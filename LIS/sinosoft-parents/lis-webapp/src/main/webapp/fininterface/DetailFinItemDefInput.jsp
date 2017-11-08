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
	loggerDebug("DetailFinItemDefInput","1"+tGI.Operator);
	loggerDebug("DetailFinItemDefInput","2"+tGI.ManageCom);
%>
<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
	var FinItemID = <%=request.getParameter("FinItemID")%>;		   
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
  <SCRIPT src="DetailFinItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="DetailFinItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr> <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDetailDefQuery);"></td>   		 
    		 <td class= titleImg>
        		 明细科目判断条件定义
       	 </td>   		 
    	</tr>
    </table>

  <Div  id= "divDetailDefQuery" style= "display: ''">
      <table  class= common>
       	<tr  class= common>
      	  <td text-align: left colSpan=1>
  					<span id="spanDetailDefGrid" >
  					</span>
  			  </td>
  			</tr>
    	</table>
    <Div  id= "divPage" align=center style= "display: 'none' ">      
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
    </Div>
  </Div>
 <div class="maxbox1">
      
<table class= common border=0 width=100%>
  <table class= common>			
  	
			<tr class= common>
				<TD class= title>
					  判断条件序号
				</TD>
				<TD class=input>
				 	<Input class="wid" class=common name=JudgementNo id=JudgementNo  elementtype=nacessary verify="判断条件序号|len<=10" >
				</TD>
				<TD class= title>
		   	   层级条件组合
		    </TD>
		    <TD class= input>
		    	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=LevelCondition id=LevelCondition  verify="层级条件组合|len<=500" ondblclick="return showCodeList('none',[this,LevelConditionName],[0,1],null);" onclick="return showCodeList('none',[this,LevelConditionName],[0,1],null);" onkeyup=" return showCodeListKey('none',[this,LevelConditionName],[0,1],null);"><input class=codename name=LevelConditionName id=LevelConditionName readonly=true elementtype=nacessary>
		    	<!--<Input class=common name=LevelCondition verify="层级条件组合|len<=500" >-->
		    </TD>
		    <TD class= common align=left>
		   	   <INPUT VALUE="追  加" TYPE=button class= cssButton name="superaddbutton" onclick="return superaddClick();">
		    </TD>
		     <TD class= common align=left>
		     	 <INPUT VALUE="清  空" TYPE=button class= cssButton name="clearbutton" onclick="return clearClick();">
		    </TD>
		    <TD class= common align=left>
		     	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=LevelConditionB id=LevelConditionB  verify="层级条件组合|len<=500" ondblclick="return showCodeList('LevelConditionB',[this,LevelConditionBName],[0,1],null,'FIAboriginalData','TableID');" onclick="return showCodeList('LevelConditionB',[this,LevelConditionBName],[0,1],null,'FIAboriginalData','TableID');" onkeyup=" return showCodeListKey('LevelConditionB',[this,LevelConditionBName],[0,1],null,'FIAboriginalData','TableID');"><input class=codename name=LevelConditionBName id=LevelConditionBName readonly=true >  
		    </TD>
			</tr>
		  <tr class= common>
				<TD class= title>
					  首次条件判断标志
				</TD>
				<TD class=input>				
				 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FirstMark id=FirstMark verify="首次条件判断标志|NOTNULL" readonly=true CodeData="0|^N|否^Y|是" ondblClick="showCodeListEx('FirstMark',[this,FirstMarkName],[0,1],null,null,null,[1]);" onClick="showCodeListEx('FirstMark',[this,FirstMarkName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('FirstMark',[this,FirstMarkName],[0,1],null,null,null,[1]);"><input class=codename name=FirstMarkName id=FirstMarkName readonly=true>				 
				</TD>
				<TD class= title>
		   	   描述
		    </TD>
		    <TD class= input>
		  		<Input class="wid" class=common name=ReMark id=ReMark verify="描述|len<=500" >
		    </TD>
			</tr>					 
		  
    </table>         
    </div>					 
  	<p>       
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">   
    <INPUT VALUE="重  置" TYPE=button class= cssButton name="resetbutton" onclick="return resetAgain();">   
    <INPUT type=hidden name=hideOperate value=''>
    <INPUT type=hidden name=VersionNo value=''> 
    <INPUT type=hidden name=FinItemID value=''>    
    <INPUT type=hidden name=VersionState value=''>        
	<!--script>
   	fm.VersionState.value = <%=request.getParameter("VersionState")%>;
	</script-->
   <br> 
   <br> 
   <hr class="line">
   <INPUT VALUE="明细科目分支影射定义" TYPE=button class= cssButton name="intobutton3" onclick="return intoDetailCode();">
   <br>    
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>

</body>
</html>
