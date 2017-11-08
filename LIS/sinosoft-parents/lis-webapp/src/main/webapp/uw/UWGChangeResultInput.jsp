<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWGChangeResultInput.jsp
//程序功能：事项索要材料录入
//创建日期：2006-10-10 15:50:36
//创建人  ：CHENRONG
//更新记录：  更新人          更新日期     更新原因/内容
//            liuxiaosong      2006-11-15   保全复用该页面，增加发送保全核保要求通知书功能
%>
<html> 

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="UWGChangeResult.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWGChangeResultInit.jsp"%>
  <title>事项索要材料录入</title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post id="fm" name=fm target="fraSubmit">
    <DIV id=DivLCContButton STYLE="display:''">
    	<table id="table1">
    		<tr>
    			<td class="common">
    				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivGrpContInfo);">
    			</td>
    			<td class="titleImg">集体合同信息</td>
    		</tr>
    	</table>
    </DIV>
    <DIV id=DivGrpContInfo class="maxbox1" STYLE="display:''">
        <table  class= common>
            <TR  class= common>
                <TD  class= title5>团体投保单号码</TD>
                <TD  class= input5><Input class="readonly wid" readonly name=GrpContNo id=GrpContNo ></TD>
                <TD></TD>
            </TR>
          	<TR>
              	<TD height="24"  class= title>通知书内容录入:</TD>
            </TR>     
            <TR>   
      		    <TD  class= input  colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="ChangeIdea" cols="100%" rows="5" witdh=100% class="common" elementtype=nacessary></textarea></TD>      		    
    	    </TR>
    	</table>
    </DIV>
    <hr class="line">
    <p>
        <INPUT VALUE="返  回"class="cssButton"  TYPE=button onclick="parent.close();"> 	
        <INPUT VALUE="确  定"class="cssButton"  TYPE=button onclick="saveChangeResult();"> 	
    </p>
    <input type="hidden" id="MissionID" name= "MissionID" value= "">
    <input type="hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type="hidden" id="EdorNo" name= "EdorNo">
    <input type="hidden" id="EdorType" name= "EdorType">
    <input type="hidden" id="PrtNo" name= "PrtNo">
  </form>
</body>
</html>
