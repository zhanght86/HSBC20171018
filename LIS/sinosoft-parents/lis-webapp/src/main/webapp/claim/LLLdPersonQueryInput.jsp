<%
//**************************************************************************************************
//Name��LLReportInput.jsp
//Function�������˲�ѯ
//Author��zl
//Date: 2005-6-9 15:31
//Desc: 
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
    
    String CurrentDate = PubFun.getCurrentDate();
    String tFlag = request.getParameter("tFlag");	////Ͷ���˿ͻ���
//=======================END========================
%>
<head>
<title>�ͻ���Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLLdPersonQuery.js"></script> 
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLLdPersonQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<!--��¼������-->
<br>
<form name=fm id=fm target=fraSubmit method=post>
	  <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanLDPerson);"></TD>
            <TD class= titleImg> ��ѯ����(����������һ���ѯ����) </TD>
        </TR>
    </Table>
	<div class=maxbox1>
    <span id= "spanLDPerson" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class="wid" class= common name=CustomerNo id=CustomerNo >  </TD>
                <TD class= title> ���� </TD>
                <TD class= input> <Input class="wid" class= common name=Name id=Name >  </TD>
                <TD class= title> ��������</TD>
                <TD class= input> <!--<Input class="multiDatePicker" dateFormat="short" name=Birthday onblur=" CheckDate(fm.Birthday); " >-->
                <Input class="coolDatePicker"  onblur=" CheckDate(fm.Birthday); " onClick="laydate({elem: '#Birthday'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>             
            <TR  class= common>
                <TD  class= title> �Ա�  </TD>
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Sex id=Sex class=codeno ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input class=codename name="SexName" id="SexName" readonly=true></TD>       
                <TD  class= title> ֤������ </TD>
                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IDType id=IDType class=codeno ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);"><input class=codename name="IDTypeName" id="IDTypeName" readonly=true></TD>
                <TD  class= title> ֤������ </TD>
                <TD  class= input> <Input class="wid" class= common name=IDNo id=IDNo > </TD>
            </TR>   
            <TR  class= common>
                <TD  class= title> �������� </TD>
                <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo > </TD>
                <!--  <TD  class= title> ���屣������ </TD>
                <TD  class= input> <Input class= common name=GrpContNo > </TD>-->
            </TR>   
        </Table>
    </span>  
	</div>
    <!--<table> 
        <tr>    
            <td> <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">  </td>
            <td> <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();">  </td>
            <!--td> <INPUT class=cssButton VALUE="����(���رմ���)" TYPE=button onclick="returnParent2();">  </td-->
            <!--
            <td>  <INPUT class=cssButton VALUE="�ͻ���Ϣ" TYPE=button onclick="returnParent();">  </td>
            <td> <INPUT class=cssButton VALUE="Ͷ������Ϣ" TYPE=button onclick="ProposalClick();"> </td>
            <td>  <INPUT class=cssButton VALUE="������Ϣ" TYPE=button onclick="PolClick();"> </td>
            <td>  <INPUT class=cssButton VALUE="����������Ϣ" TYPE=button onclick="DesPolClick();"> </td>
            -->
       <!-- </tr> 
    </table>-->
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();"> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></TD>
            <TD class= titleImg> �ͻ���Ϣ </TD>
        </TR>
    </Table>      
    <Div  id= "divLDPerson1" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanPersonGrid" ></span> </TD>
            </TR>
        </Table>
        <table align= center> 
            <tr>  
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table> 
    </Div>
    <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();">          
   <Input type=hidden id="Flag" name="Flag"><!--��ڱ��-->
   <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
