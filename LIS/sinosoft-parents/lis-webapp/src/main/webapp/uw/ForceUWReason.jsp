<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
//程序名称：ForceUWReason.jsp
//程序功能：强制人工核保原因查询
//创建日期：2005-10-11 11:10:36
//创建人  ：guanwei
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tReason = "";
	try
	{
	  tReason = request.getParameter("Reason");
	}
	catch( Exception e )
	{
		tReason = "";
	}

//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体下个人投保单明细查询
// 5 -- 复核
// 6 -- 查询
// 7 -- 保全新保加人
// 8 -- 保全新增附加险
// 9 -- 无名单补名单
// 10-- 浮动费率
// 99-- 随动定制
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
  loggerDebug("ForceUWReason","############tGI.ManageCom===="+tGI.ManageCom);
%>
<head>
<script language="javascript" type="">
	var ContNo = "<%=request.getParameter("ContNo")%>"
	var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	if (ContType == "null")
	  ContType=1;
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/Common.js" type=""></script>
  <script src="../common/cvar/CCodeOperate.js" type=""></script>
 <script src="../common/javascript/EasyQuery.js" type=""></script>
  <script src="../common/javascript/MulLine.js" type=""></script>
  <script src="../common/laydate/laydate.js" type=""></script>
  <script src="../common/javascript/EasyQuery.js" type=""></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></script>
  <script src="../common/javascript/VerifyInput.js" type=""></script>
<!--
	#########################################################################
	                   引入本页ForceUWReason.js文件
	#########################################################################
-->


  <%@include file = "ForceUWReasonInit.jsp"%>
  <script src="ForceUWReason.js" type=""></script>


<!--
	#########################################################################
	#########################################################################
	#########################################################################
-->

<!--
	#########################################################################
	                   引入本页ForceUWInit.js文件
	#########################################################################
-->

<!--
	#########################################################################
	#########################################################################
	#########################################################################
-->
</head>
<body onLoad="initForm();">
  <form method=post name=fm id="fm" target="fraSubmit" action="">
    <table>
      <tr>
        <td>
          <table class="common">
            <tr class="common">
              <td class="titleImg">
		<IMG  src="../common/images/butExpand.gif" style= "cursor:hand;" alt="">
 		 强制进入人工核保原因
	      </td>
            </tr>
	  </table>
	  <div class="maxbox1">
          <table class="common">
            <tr class="common">
              <td class="input" colspan='4'>
                <textarea  cols="75" rows="6" class="common" name="ForceUWReason" id="ForceUWReason"  readonly = <%=tReason%> readonly>
                </textarea>
              </td>
            </tr>
          </table>
          </div>
          <table>
            <tr>
              <td>
                <input type="button" class="cssButton" id="quitButton" name="quitButton" value="返  回" onClick="returnParent();">
              </td>
            </tr>
          </table>

      </tr>
    </table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>





