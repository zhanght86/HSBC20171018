<%
//**************************************************************************************************
//Name��LLClaimReciInfoInput.jsp
//Function���ռ�����Ϣ¼��
//Author��niuzj
//Date: 2005-10-24
//Desc:
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	String tClmNo = request.getParameter("ClmNo");	//�ⰸ��
	loggerDebug("LLClaimReciInfoInput","tClmNo="+tClmNo);
	String tcustomerNo = request.getParameter("CustomerNo");	//�����˴���
	loggerDebug("LLClaimReciInfoInput","tcustomerNo="+tcustomerNo);
	String tIsShow = request.getParameter("IsShow");	//[����]��ť�ܷ���ʾ,0-����ʾ,1-��ʾ
	loggerDebug("LLClaimReciInfoInput","tIsShow="+tIsShow);
	String tRgtObj = request.getParameter("RgtObj");	//�������ձ�־,1-����,2-����
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
<title>�ռ�����Ϣ¼��</title>
<body onload="initForm()" >
<form action="" method=post name=fm id=fm target="fraSubmit">
	<table>
     <TR>
       <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ReciInfoInput);"></TD>
       <TD class= titleImg>�ռ�����Ϣ</TD>
     </TR>
  </table>
	<Div  id= "ReciInfoInput" style= "display:''" class="maxbox">
	    <Table  class= common>
         <TR class= common>
				    <TD  class= title>�ռ��˴���</TD>
				    <TD  class= input> <input class="wid" class=common name=ReciCode id=ReciCode></TD>
            <TD  class= title>�ռ�������</TD>
            <TD  class= input> <input class="wid" class=common name=ReciName id=ReciName></TD>
            <TD  class= title>�ռ���������˹�ϵ</TD>
				    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
	       </TR>
	       <TR class= common>
				    <TD  class= title>�ռ��˵�ַ</TD>
            <TD  class= input> <input class="wid" class=common name=ReciAddress id=ReciAddress></TD>
            <TD  class= title>�ռ��˵绰</TD>
            <TD  class= input> <input class="wid" class=common name=ReciPhone id=ReciPhone></TD>
            <TD  class= title>�ռ����ֻ�</TD>
            <TD  class= input> <input class="wid" class=common name=ReciMobile id=ReciMobile></TD>
	       </TR>
	       <TR class= common>
				    <TD  class= title>�ռ����ʱ�</TD>
            <TD  class= input> <input class="wid" class=common name=ReciZip id=ReciZip></TD>
            <TD  class= title>�ռ����Ա�</TD>
            <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=ReciSex id=ReciSex ondblclick="return showCodeList('sex',[this,ReciSexName],[0,1]);" onclick="return showCodeList('sex',[this,ReciSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,ReciSexName],[0,1]);"><input class=codename name=ReciSexName id=ReciSexName readonly=true></TD>
            <TD  class= title>�ռ���Email</TD>
            <TD  class= input> <input class="wid" class=common name=ReciEmail id=ReciEmail></TD>
	       </TR>
	    </Table>
	    <Table class= common>
	       <TR class= common>
				    <TD  class= title>�ռ���ϸ��</TD>
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
	        <td><input value="��  ��" name=save class= cssButton type=button onclick="SaveClick();"></td>
	        <td><input value="��  ��" name=return class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
    <a href="javascript:void(0);" name=save class="button" onClick="SaveClick();">��    ��</a>
    <a href="javascript:void(0);" name=return class="button" onClick="returnParent();">��    ��</a>
    <!--���ش���-->
    <input class=common name=ClmNo type=hidden >
    <input class=common name=CustomerNo type=hidden >
    <input class=common name=IsShow type=hidden >
    <input class=common name=RgtObj type=hidden >
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
