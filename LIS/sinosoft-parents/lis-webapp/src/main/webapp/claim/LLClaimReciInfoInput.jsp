<%
//**************************************************************************************************
//Name：LLClaimReciInfoInput.jsp
//Function：收件人信息录入
//Author：niuzj
//Date: 2005-10-24
//Desc:
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	String tClmNo = request.getParameter("ClmNo");	//赔案号
	loggerDebug("LLClaimReciInfoInput","tClmNo="+tClmNo);
	String tcustomerNo = request.getParameter("CustomerNo");	//出险人代码
	loggerDebug("LLClaimReciInfoInput","tcustomerNo="+tcustomerNo);
	String tIsShow = request.getParameter("IsShow");	//[保存]按钮能否显示,0-不显示,1-显示
	loggerDebug("LLClaimReciInfoInput","tIsShow="+tIsShow);
	String tRgtObj = request.getParameter("RgtObj");	//个险团险标志,1-个险,2-团险
	loggerDebug("LLClaimReciInfoInput","tRgtObj="+tRgtObj);
	
%>
<head >
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <SCRIPT src="LLClaimReciInfo.js"></SCRIPT>
   <%@include file="LLClaimReciInfoInit.jsp"%>
</head>
<title>收件人信息录入</title>
<body onload="initForm()" >
<form action="" method=post name=fm id=fm target="fraSubmit">
	<table>
     <TR>
       <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ReciInfoInput);"></TD>
       <TD class= titleImg>收件人信息</TD>
     </TR>
  </table>
	<Div  id= "ReciInfoInput" style= "display:''" class="maxbox">
	    <Table  class= common>
         <TR class= common>
				    <TD  class= title>收件人代码</TD>
				    <TD  class= input> <input class="wid" class=common name=ReciCode id=ReciCode></TD>
            <TD  class= title>收件人姓名</TD>
            <TD  class= input> <input class="wid" class=common name=ReciName id=ReciName></TD>
            <TD  class= title>收件人与出险人关系</TD>
				    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
	       </TR>
	       <TR class= common>
				    <TD  class= title>收件人地址</TD>
            <TD  class= input> <input class="wid" class=common name=ReciAddress id=ReciAddress></TD>
            <TD  class= title>收件人电话</TD>
            <TD  class= input> <input class="wid" class=common name=ReciPhone id=ReciPhone></TD>
            <TD  class= title>收件人手机</TD>
            <TD  class= input> <input class="wid" class=common name=ReciMobile id=ReciMobile></TD>
	       </TR>
	       <TR class= common>
				    <TD  class= title>收件人邮编</TD>
            <TD  class= input> <input class="wid" class=common name=ReciZip id=ReciZip></TD>
            <TD  class= title>收件人性别</TD>
            <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=ReciSex id=ReciSex ondblclick="return showCodeList('sex',[this,ReciSexName],[0,1]);" onclick="return showCodeList('sex',[this,ReciSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,ReciSexName],[0,1]);"><input class=codename name=ReciSexName id=ReciSexName readonly=true></TD>
            <TD  class= title>收件人Email</TD>
            <TD  class= input> <input class="wid" class=common name=ReciEmail id=ReciEmail></TD>
	       </TR>
	    </Table>
	    <Table class= common>
	       <TR class= common>
				    <TD  class= title>收件人细节</TD>
	       </TR>
	       <TR class= common>
            <TD colspan="6" style="padding-left:16px">
                <span id="spanText1" style= "display: ''">
                     <textarea  name="Remark" id="Remark" cols="224" rows="4" witdh=25% class="common"></textarea>
                </span>
            </TD>
         </tr>
	    </Table>
	</Div>
    <table style="display:none">
        <tr>
	        <td><input value="保  存" name=save class= cssButton type=button onclick="SaveClick();"></td>
	        <td><input value="返  回" name=return class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
    <a href="javascript:void(0);" name=save class="button" onClick="SaveClick();">保    存</a>
    <a href="javascript:void(0);" name=return class="button" onClick="returnParent();">返    回</a>
    <!--隐藏传递-->
    <input class=common name=ClmNo type=hidden >
    <input class=common name=CustomerNo type=hidden >
    <input class=common name=IsShow type=hidden >
    <input class=common name=RgtObj type=hidden >
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
