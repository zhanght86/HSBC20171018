<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpQuestInput.jsp
//程序功能：团体问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
String hh = request.getParameter("Flag");
loggerDebug("GrpQuestInput","标志====="+hh);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpQuestInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>团体核保问题件录入</title>
  <%@include file="GrpQuestInputInit.jsp"%>
  <SCRIPT>var tFlag="<%=request.getParameter("Flag")%>";</SCRIPT>
</head>
<body  onload="initForm('<%=tGrpContNo%>','<%=tFlag%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./GrpQuestInputChk.jsp">
    <div class=maxbox>
    <table class = common>
	        <TD  class= title5>
            发送对象
          </TD>
          <%if(!hh.equals("2")){%>
          <TD class=input5>
          <Input class= "codeno" name =BackObj id=BackObj style="background:url(../common/images/select--bg_03.png) no-repeat right center"
		  ondblclick="return showCodeList('backobj',[this,BackObjName],[0,1]);" onkeyup="return showCodeListKey('BackObj',[this,BackObjName],[0,1]);">
		  <Input class = codename name=BackObjName id=BackObjName readonly = true>
          </TD>
          <%}else{%>
          <TD>
          <!--Input class=code name=BackObj CodeData="3|^0|男^1|女^2不详" ondblClick="showCodeListEx('BackObj',[this,BackObjName],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,BackObjName],[0,1]);">

          <Input class = codename name=BackObjName readonly = true-->
          <input class="code" name="BackObj" id=BackObj CodeData="0|^2|客户"" ondblclick="return showCodeListEx('BackObj', [this])"onkeyup="return showCodeListKeyEx('BackObj', [this])">
          <!--input class="code" name="BackObj" CodeData="0|^2|客户" ondblclick="return showCodeListEx('BackObj', [this])" onkeyup="return showCodeListKeyEx('BackObj', [this)"-->
          </TD>
          <%}%>
		  <td class=title5></td>
		  <td class=input5></td>
        </table>

       <table class = common>

       <TD  class= title5>
    	  问题件选择
    	</TD>
        <TD class=input5>
            <Input class=code name=Quest  id=Quest style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick= "showCodeListEx('GrpQuest',[this,Content],[0,1],null,null,null,null,500);" onkeyup="showCodeListKeyEx('GrpQuest',[this,Content],[0,1],null,null,null,null,500);" >
        </TD>
        <td class=title5></td>
		<td class=input5></td>
    </table>


  <table  class=common width="121%" height="37%">
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
    <input type= "hidden" name= "Flag" id=Flag value="">
    <input type= "hidden" name= "ProposalNoHide" id=ProposalNoHide value= "">
    <input type= "hidden" name= "GrpContNoHide" id=GrpContNoHide value= "">
    <input type= "hidden" name= "SerialNoHide"id=SerialNoHide value= "">
  </p>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<input class= cssButton type= "button" name= "sure" value="保  存" onClick="submitForm()">
<input class= cssButton type= "button" name= "modify" value="查  询" onClick="query()">
<!--<input class= cssButton type= "button" name= "modify" value="修 改" onClick="modify()">-->
</body>
</html>
