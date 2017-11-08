<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%                                                                                
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//String sMissionID = request.getParameter("MissionID");
	//String sSubMissionID = request.getParameter("SubMissionID");                  
	//String sActivityID = request.getParameter("ActivityID");
	String sPrtNo = request.getParameter("PrtNo");
    String tOtherFlag = request.getParameter("otherFlag");
    //String sNoType = request.getParameter("NoType"); 
    //String sUnSaveFlag = request.getParameter("UnSaveFlag"); //如果只是查询，则隐藏保存的按钮，UnSaveFlag为"1"   
  //个人下个人
%>                                                                                
                                                                                  
<script> 
var QueryFlag = <%=request.getParameter("QueryFlag")%>;
var operator = "<%=tGI.Operator%>";   //记录操作员
var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
var tPrtNo = "<%= sPrtNo %>";
var tOtherFlag = "<%=tOtherFlag %>";
var ErrManageCom;
function initParam()       
{                                                                  
	fm.PrtNo.value = tPrtNo;
}	
</script> 
  
  <SCRIPT src="MSQuestMistakeInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="MSQuestMistakeInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  <title>问题件差错记录</title>
  
</head>


<body  onload="initForm();" >
  <form action="./MSQuestMistakeSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  <div id= "divNotePad" style= "display: '';margin-top:10px" >
  <div class="maxbox1">
	  <table class=common >
	  		<tr class=common>
	  			<td class=title5>
	  				印刷号</td>
	  			<td class=input5>
    	 		<Input class="readonly wid" name="PrtNo" id="PrtNo" readonly >
    	 		</td>
                <td class=title5></td>
	  			<td class=input5></td>
	  		</tr>
	 </table> 
	 </div>
	 <table>  
	    	<tr>
		        <td class=common>
		          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNotePadInfo);">
		    	</td>
		    	<td class= titleImg>
		    	 已保存差错记录 
		    	</td>
	    	</tr>
	    </table>        
		<div id= "divNotePadInfo" style= "display: ''" >

		    <Div  id= "divQuestGrid" style= "display: ''" align = center>
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  				<span id="spanQuestGrid">
		  				</span> 
		  		  		</td>
		  			</tr>
		    	</table>
			         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
			         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
			         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
			         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage.lastPage();"> 	      
		    </div>
			<br>
			<div class="maxbox1">
			  <table class= common>
			    <tr>
			      <td class=title5>所属岗位</td>
        		  <TD  class= input5>
        		  	<Input  class="codeno" name=ErrManageCom id="ErrManageCom" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="所属岗位|NOTNULL" CodeData = "0|^1|分公司初审岗^2|分公司扫描岗^3|体检录入岗^4|总公司新契约^5|总公司核保岗^10|其他" onClick="showCodeListEx('ErrManageCom',[this,ErrManageComName],[0,1]);" ondblclick= "showCodeListEx('ErrManageCom',[this,ErrManageComName],[0,1]);" onKeyUp="showCodeListKeyEx('ErrManageCom',[this,ErrManageComName],[0,1]);" ><Input class=codename  name=ErrManageComName id="ErrManageComName" readonly ="readonly" >
        		  </TD>
			      <TD  class= title5>差错类型</TD>
        		  <TD  class= input5>
        		  	<Input class="codeno" name=ErrorType id="ErrorType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="差错类型|NOTNULL" onClick="return showCodeList('ErrorType', [this,ErrorTypeName],[0,1],null,'#2# and codealias=#'+fm.ErrManageCom.value+'#','2');"  ondblclick="return showCodeList('ErrorType', [this,ErrorTypeName],[0,1],null,'#2# and codealias=#'+fm.ErrManageCom.value+'#','2');" onKeyUp="return showCodeListKey('ErrorType', [this,ErrorTypeName],[0,1],null,'#2# and codealias=#'+fm.ErrManageCom.value+'#','2');" ><Input class=codename  name=ErrorTypeName id="ErrorTypeName" readonly ="readonly" >
        		  </TD>
			    </tr>
			  </table>
			  </div>
			  <table class= common>
			    <TR  class= title> 
			      <TD width="100%" height="15%"  class= title> 差错内容</TD>
			    </TR>
			    <TR  class= title>
			      <TD height="85%"  class= title><textarea name="Content" verify="记事内容|len<800" verifyorder="1" cols="130" rows="5" class= common ></textarea></TD>
			    </TR>
			  </table>
			<div id= "divButton" style= "display: ''" >			  
			  <INPUT CLASS=cssButton VALUE="新  增" TYPE=button onClick="addNote();">
			  <INPUT CLASS=cssButton VALUE="修  改" TYPE=button onClick="UpdateMistake();">
			  <INPUT CLASS=cssButton VALUE="删  除" TYPE=button onClick="DeleteMistake();">
			  <!--<INPUT CLASS=cssButton VALUE="清  空" TYPE=button onclick="fm.Content.value=''">-->
			  <!-- modified by liuyuxiao 2011-05-24 隐去返回，在tab中无用 -->
			  <INPUT class=cssButton VALUE="返  回" TYPE=button onClick="top.close();" style= "display: none">	
			</div>
			<div id= "divReturn" style= "display: none" >			  
			  <INPUT class=cssButton VALUE="返  回" TYPE=button onClick="top.close();">	
			</div>
	  </div>	   
   </div> 	 
   
      	 <Input type=hidden id="MissionID" name="MissionID" >
    	 <Input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <Input type=hidden id="ActivityID" name="ActivityID" >
    	 <Input type=hidden id="NoType" name="NoType" >
    	 <Input type=hidden id="SerialNo" name="SerialNo" >
    	 <Input type=hidden id="maction" name="maction" >
    	    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
