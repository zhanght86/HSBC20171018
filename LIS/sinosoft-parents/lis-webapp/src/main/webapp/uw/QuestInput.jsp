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
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="QuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>核保问题件录入</title>
  <%@include file="QuestInputInit.jsp"%>
<script language="javascript">
	var LoadFlag ="<%=tFlag%>"; //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
  var tMissionID = "<%=tMissionID%>";
  var tSubMissionID = "<%=tSubMissionID%>";
  var tActivityID = "<%=tActivityID%>";
</script>  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./QuestInputChk.jsp">
  	
  		<table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
			</td>
			<td class= titleImg>问题件信息</td>
		</tr>
	</table>
	<div class=maxbox1>
	<table class = common>
	        <TD  class= title>
            发送对象  
          </TD>
          <TD>
          <Input class= "codeno" id=BackObj name = BackObj CodeData = "" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('backobj',[this,BackObjName],[0,1]);" onkeyup="return showCodeListKeyEx('BackObj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName id=BackObjName readonly = true>
          </TD> 
		  <td  class= title>   </TD>
		  <td  class= input>   </TD>
		  <td  class= title>   </TD>
		  <td  class= input>   </TD>
        </table>
	
		<div id= "divCustomerqustion" style= "display: none" >
    <table class = common >
    	<TR  class= common>
    		  
          
          <TD class=title> 接收对象 </TD>  
          <TD class=input ><Input class= "codeno" name = QuestionObj id=QuestionObj style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('question',[this,QuestionObjName],[0,1]);" onkeyup="return showCodeListKey('question',[this,QuestionObjName],[0,1]);" ><Input class = codename name=QuestionObjName readonly = true> </TD>
                     
          <TD  class = title> 客户号码 </TD>
          <TD  class=input><Input class="common wid"  name=CustomerNo id=CustomerNo ></TD>
           
          <TD  class = title>	客户姓名 </TD>
          <TD  class=input ><Input class="common wid"  name=CustomerName id=CustomerName></TD>
          </TR>     
          
     </table>
    </div>
     
   <table class = common>
    
       <TD  class= title>
    	  问题件选择
    	</TD>
        <TD >
            <Input class=code name=Quest id=Quest style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick= "showCodeListEx('Quest',[this,Content],[0,1],null,null,null,null,770);" onkeyup="showCodeListKeyEx('Quest',[this,Content],[0,1],null,null,null,null,770);" >
        </TD>
		<td  class= title>   </TD>
		  <td  class= input>   </TD>
		  <td  class= title>   </TD>
		  <td  class= input>   </TD>
    
    </table>

    
  <table class = common align = center width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="13%"  class= title> 问题件内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
  </div>
  <p> 
    <!--读取信息-->
    <input type= "hidden" name= "Flag" value="">
    <input type= "hidden" name= "ContNo" value= "">
    <input type= "hidden" name= "MissionID" value= "">
    <input type= "hidden" name= "SubMissionID" value= "">
    <input type= "hidden" name= "ActivityID" value= "">
  </p>
</form>
  
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<input type= "button" name= "sure" class= cssButton  value="确  认" onClick="submitForm()">
</body>
</html>
