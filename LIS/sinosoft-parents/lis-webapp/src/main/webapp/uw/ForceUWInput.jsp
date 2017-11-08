<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>   
<%
//程序名称：
//程序功能：
//创建日期：2005-05-19 11:10:36
//创建人  ：HL
//更新记录：  更新人    更新日期     更新原因/内容
%>     
<%  
	String tContNo = "";
	try
	{
		tContNo = request.getParameter("ContNo");
	}
	catch( Exception e )
	{ 
		tContNo = "";
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
  loggerDebug("ForceUWInput","############tGI.ManageCom===="+tGI.ManageCom);
%>
<head>
<script language="javascript">
	var ContNo = "<%=request.getParameter("ContNo")%>"
	var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var ContType = "<%=request.getParameter("ContType")%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置
	if (ContType == "null")
	  ContType=1;
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="ProposalAutoMove.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
<!--	
	#########################################################################
	                   引入本页ForceUW.js文件
	#########################################################################
-->
  <script src="ForceUW.js"></script>	
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
	<%@include file="ForceUWInit.jsp"%>
<!--	
	#########################################################################
	#########################################################################
	#########################################################################
-->
</head>
<body onload="initForm();initElementtype();">
  <form action="./ForceUWSave.jsp" method=post id="fm" name=fm target="fraSubmit">
    <table>
      <tr>
        <td>
          <table class="common">
            <tr class="common">
			        <td class="titleImg">
				        <IMG  src="../common/images/butExpand.gif" style= "cursor:hand;">
 			          强制进入人工核保
			        </td>
            </tr>
			    </table>
			    <div class="maxbox1">
			    <table class="common">
			      <tr class="common">
			        <td class="title5">
			          强制进入人工核保选择			        
			        </td>
			        <td class="input5">
			          <input class="codeno" id="ForceUWOpt" name="ForceUWOpt" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="强制进入人工核保选择|notnull&len<2" ondblclick="return showCodeList('forceuwopt',[this,ForceUWOptName],[0,1]);" onkeyup="return showCodeListKey('forceuwopt',[this,ForceUWOptName],[0,1]);"><input class="codename" id="ForceUWOptName" name="ForceUWOptName" elementtype=nacessary readonly="readonly">
			        </td>
			        <td class="title5">&nbsp;</td>
			        <td class="title5">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="4" class="title">强制进入人工核保原因</td>
			      </tr>
			      <tr class="common">
			        <td class="input" colspan='4'>
			          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea class="common" cols="110" rows="4" name="ForceUWRemark" id=ForceUWRemark verify="">			          
			          </textarea>
			        </td>			      
			      </tr>			    
			    </table>
			    </div>
			    <table>
			      <tr>
			        <td>
			          <input type="button" class="cssButton" id="saveButton" name="saveButton" value="保  存" onclick="submitForm();">
			        </td>
			        <td>
			          <input type="button" class="cssButton" id="quitButton" name="quitButton" value="返  回" onclick="returnParent();">
			        </td>
			      </tr>
			    </table>
			  </td>
      </tr>
      <tr>
        <td>
          <input type= "hidden" id="ContNo" name= "ContNo" value= "">
        </td>
      </tr>
    </table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>
