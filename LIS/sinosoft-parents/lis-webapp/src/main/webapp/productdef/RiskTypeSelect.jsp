<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");

	}
	catch( Exception e )
	{
		tPNo = "";
	}

//得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	
	
  System.out.println("LoadFlag:" + tLoadFlag);
%>
<script type="text/javascript">
  var ManageCom ="<%=tGI.ManageCom%>";
</script>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  
  

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 在本页中引入其他JS页面              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script> 
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/Calendar/Calendar.js"></script>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    引入本页ContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="RiskTypeSelect.js"></script>
 
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>


<body  onload="initForm();" >
  <form action="" method=post name=fm target="fraSubmit">
<table class=common>
			<TR class=common>
				<TD class=title5>险种编码</TD>
				<TD class=input5>
	<Input class="codeno" name=RiskCode  verify="险种编码|NOTNUlL&CODE:RiskCode" ondblclick="showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="showCodeListKey('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class="common" name="RiskCodeName" readonly="readonly">
					<!--input class=codeno name=CardType ondblclick="getcodedata2();return showCodeListEx('CardType',[this,CardTypeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('CardType',[this,CardTypeName],[0,1],null,null,null,1,'400');"><input class=codename name=CardTypeName readonly="readonly" elementtype=nacessary-->							
				</TD>
		</TR>
</table>
<%
          String today=PubFun.getCurrentDate();
          
          %>
			<input type=hidden id="" name="sysdate" value="<%=today%>">
			<input type=hidden id="" name="ManageCom" >
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
