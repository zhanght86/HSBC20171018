<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-15 11:48:42
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
	
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
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate =PubFun.getCurrentDate();
	//CardRisk
	String mCardRisk =request.getParameter( "CardRisk" )==null?"":request.getParameter( "CardRisk" );
	String mCardFace = "";
	mCardFace = "../riskinput/cardinput/Card"+mCardRisk+".jsp";
  loggerDebug("CardContInput","mCardRisk:" + mCardRisk+"mCardFace:"+mCardFace);
%>

<%
 if(mCardRisk.equals(""))
 {
%>
<script language="javascript">
	
	alert("卡单选择错误");
	parent.fraInterface.location='./CardTypeSelect.jsp';
	
</script>
<%
}

%>
<script language="javascript">
   var CardFace ="<%=mCardFace%>";
   var CardRisk ="<%=mCardRisk%>";
   var CurrentDate ="<%=tCurrentDate%>";
   //alert(CardFace);
   //fm.RiskCode.value =tRiskCode;
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 在本页中引入其他JS页面              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script> 
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
             引入本页ContInit.jsp页面                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<%@include file="CardContInit.jsp"%>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    引入本页ContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="CardContInput.js"></script>
 
</head>
<body  onload="initForm3();" >
<form action="./CardContSave.jsp" method=post name=fm id=fm target="fraSubmit">
 <jsp:include page="<%=mCardFace%>" />
 <Div id="HiddenButton" style="display:''" style= "float: left" >
   <INPUT class=cssButton id="riskbutton9" VALUE="上一步" TYPE=button onclick="returnparent();">
 </Div>
 <Div  id= "HiddenValue" style= "float: right" style="float: right" style="display:''">
    	
    	
    	<!--INPUT class=cssButton id="Donextbutton8" VALUE="保  存 "  TYPE=button onclick="submitForm();"-->
        <input class=cssButton name="addbutton" VALUE="保  存"  TYPE=button onclick="return submitForm();">      			
    	<INPUT class=cssButton id="Donextbutton8" VALUE="修  改"  TYPE=button onclick="submitForm1();" >
      <!-- INPUT class=cssButton id="Donextbutton9" VALUE="签  单"  TYPE=button onclick="submitForm2();"-->  
      
      <INPUT class=cssButton id="Donextbutton10" VALUE="删  除"  TYPE=button onclick="deleteClick2();">  	 	
    
    </div> 
    <Input type=hidden name=RiskCode value="<%=mCardRisk%>">
    </form>
 	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 	</body>
</html>
