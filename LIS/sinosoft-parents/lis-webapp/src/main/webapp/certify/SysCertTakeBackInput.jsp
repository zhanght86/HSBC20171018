<% 
//��ջ���
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.utility.*"%>
<html> 
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
	String tContNo = request.getParameter("ContNo"); 
	String tprtNo = request.getParameter("prtNo"); 
	String tAgentName = StrTool.unicodeToGBK(request.getParameter("AgentName"));
	loggerDebug("SysCertTakeBackInput","input��   "+tAgentName);
%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-07
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language = "javascript">
  var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var sysdate = "<%=tCurrentDate%>"; //��¼��½����
  var prtNo = "<%=request.getParameter("prtNo")%>";
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
     <script src="../common/javascript/EasyQuery.js"></script>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="SysCertTakeBackInput.js"></SCRIPT>
  <script src="../common/javascript/jquery.js"></script>
  <script src="../common/javascript/jquery.imageView.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>

	<script src="../common/javascript/Signature.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <%@include file="SysCertTakeBackInit.jsp"%>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="ProposalAutoMove3.js"></script>
</head>
<body  onload="initForm()" >
  <form action="./SysCertTakeBackSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
    
    <!-- ��֤���� -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title">��֤����</td>
        <td class="input"><input class="code" name="CertifyCode" value ="9995" readonly =true style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SysCertCode1', [this])"onkeyup="return showCodeListKey('SysCertCode1', [this])" ></td>
       <td class="title"></td><td class="input"></td>
       <td class="title"></td><td class="input"></td>
        </tr></table>
        <!--<input id="sqButton5" class=cssButton VALUE="ǩ����ѯ"  TYPE=button onclick="fm.SignatureFlag.value='true';signatureQuery(this);" cancut="0" codetype="PrtNo">-->
        <a id="sqButton5"  cancut="0" codetype="PrtNo" href="javascript:void(0);" class="button" onClick="fm.SignatureFlag.value='true';signatureQuery(this);">ǩ����ѯ</a>
    <!-- ���յ���Ϣ -->
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divTakeBackInfo);"></td>
          <td class="titleImg">������Ϣ</td></tr></table>

    <Div  id= "divTakeBackInfo" style= "display: ''" class="maxbox">
      <!-- ��֤����;������� -->
      <table class="common"> 
        <tr class="common">
          <td class="title">��֤����</td>
          <td class="input">
          	<!-- input class="common" name="CertifyNo" onblur="checkScan();queryagent();" -->
          	<input class="common" name="CertifyNo" onblur="checkScan();queryagent();">
          	<!--<input class="cssButton" type="button" value="�����յ�֤��ѯ" style="width:60" onclick="query()">-->
            <a href="javascript:void(0);" class="button"  onClick="query();">�����յ���ѯ</a>
            </td>

          <td class="title">��Ч����</td>
          <td class="input"><input class="readonly wid" readonly name="ValidDate" id="ValidDate"></td>
          <td class="title">������</td>
          <td class="input"><input class="common wid" name="SendOutCom" id="SendOutCom" verify="������|NOTNULL"></td>
          </tr>

        <tr class="common">
          

          <td class="title">ҵ��Ա����</td>
          <td class="input"><input class="common wid" name="AgentName" id="AgentName" ></td>

          <td class="title">������</td>
          <td class="input"><input class="common wid" name="ReceiveCom" id="ReceiveCom" verify="������|NOTNULL"></td>
          <td class="title">������</td>
          <td class="input"><input class="readonly wid" readonly name="Handler" id="Handler"></td>
          </tr>

        <tr class="common">
          

          <td class="title">��������</td>
          <td class="input"><input class="readonly wid" readonly name="HandleDate" id="HandleDate"></td>
           <td class="title">����Ա</td>
          <td class="input"><input class="readonly wid" readonly name="Operator" id="Operator"></td>
        
          <td class="title">�������</td>
          <td class="input"><input class="readonly wid" readonly name="MakeDate" id="MakeDate"></td>
          </tr>
        <tr class="common">
          <td class="title">���ղ���Ա</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackOperator" id="TakeBackOperator"></td>
        
          <td class="title">��������</td>
          <td class="input"><input class="common wid"  name="TakeBackDate"  id="TakeBackDate" verify="��������|DATE" onblur="confirmSecondInput(this,'onblur');" onFocus="setTakeBackDateFocus();"></td>
		
		  <TD  class= title>�ӳ��ʹ�ԭ��</TD>
		  <TD  class= input>
				     <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=reasoncode id=reasoncode ondblClick="showCodeList('delaybackreason',[this,reasondesc],[0,1]);" onClick="showCodeList('delaybackreason',[this,reasondesc],[0,1]);" onkeyup="showCodeListKey('delaybackreason',[this,reasondesc],[0,1]);"><input class="codename" name="reasondesc" id="reasondesc" value ="" >
		  </TD>
		</tr>
        <tr class="common">
          <td class="title">�������κ�</td>
          <td class="input"><input class="readonly wid" readonly name="SendNo" id="SendNo"></td>
        
          <td class="title">�������κ�</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackNo" id="TakeBackNo"></td>
          <td class="title">���ղ�������</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackMakeDate" id="TakeBackMakeDate"></td>
          </tr>
        
        <tr class="common">
          
          
          <td class="title">���ղ���ʱ��</td>
          <td class="input"><input class="readonly wid" readonly name="TakeBackMakeTime" id="TakeBackMakeTime"></td></tr>
        
      </table>
    </div>
<!--<table class="common">
    	<tr class="common">
    		<td class="input"><input class="cssButton" type="button" value="�� ֤ �� ��" onclick="submitForm()" ></td>
    		<td class="input"><input class="cssButton" type="button" value="�ѻ��յ�֤��ѯ" onclick="queryClick()" ></td>
    		<td class="input"><input class="cssButton" type="button" value="ɾ��ɨ���" onclick="DELPic()" ></td>
    		<td></td>
    		</tr>
    
   
    </table>-->
    
    <a href="javascript:void(0);" class="button" onClick="submitForm();">��֤����</a>
    <a href="javascript:void(0);" class="button" onClick="queryClick();">�ѻ��յ�֤��ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="DELPic();">ɾ��ɨ���</a>
  
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
    <input type="hidden" name="hideOperation">
    <input type="hidden" name="hideTakeBackDate">
    <input type="hidden" name="sql_where">
    <input type=hidden id="PrtNo" name="PrtNo" value='<%=tprtNo%>'>
    <input type=hidden id="PolNo" name="PolNo">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="EdorNo" name="EdorNo">
  	<input type=hidden id="SignatureFlag" name="SignatureFlag" value="false">
  	<input type=hidden id="LastCertifyNo" name="LastCertifyNo" value="">
  	<input type=hidden id="ScanFlag" name="ScanFlag" value="true">
  	<input type="hidden" name="QUERY_FLAG">
  	<input type="hidden" name="ManageCom">
  </form><br><br><br><br>
</body>
</html>
