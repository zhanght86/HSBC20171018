<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�PEdorTypeNSInput.jsp
//�����ܣ����˱�ȫ
//�������ڣ�2005-7-20 10:08
//������  ��lizhuo
//���¼�¼��  ������    ��������     ����ԭ��/����
%>



<%@page import="com.sinosoft.lis.finfee.FinFeePubFun"%>
<html>
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>
<%

String tContNo = request.getParameter("ContNo");
loggerDebug("PEdorTypeNSInput","���ձ�����tContNo"+tContNo);

ExeSQL tExeSQL = new ExeSQL();
String tCvalidate="";
String tCurDate="";
String CureCvaliDate="";    
String PreCvaliDate="";   
String NextCvaliDate="";
if(!"".equals(tContNo) && tContNo !=null && !"null".equals(tContNo))
{
	String tSQL="select cvalidate from lcpol where polno=mainpolno and contno='"+tContNo+"'";
//	���ձ�������Ч��
    tCvalidate=tExeSQL.getOneValue(tSQL);
    tCurDate=PubFun.getCurrentDate();
    
    int tIntv=Integer.parseInt(tCurDate.substring(0,4))-Integer.parseInt(tCvalidate.substring(0,4));
    //CureCvaliDate=BqNameFun.calDate(Integer.parseInt(tCurDate.substring(0,4)),tCvalidate); 
    CureCvaliDate=FinFeePubFun.calOFDate(tCvalidate,tIntv,"Y",tCvalidate);
    
    NextCvaliDate=FinFeePubFun.calOFDate(CureCvaliDate,12,"M",tCvalidate);
    PreCvaliDate=PubFun.calDate(CureCvaliDate,-1,"Y","");   
	loggerDebug("PEdorTypeNSInput","��ǰ��Ч�գ�"+CureCvaliDate);
	loggerDebug("PEdorTypeNSInput","������Ч�գ�"+PreCvaliDate);
	loggerDebug("PEdorTypeNSInput","������Ч�գ�"+NextCvaliDate);
}

%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��������</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeNS.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
	<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- �������ýű� -->
    <script language="JavaScript" src="../app/SpecDealByRisk.js"></script>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeNS.js"></script>
    <%@ include file="PEdorTypeNSInit.jsp" %>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeNSSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
    <div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly>
				<input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
            </tr>
            <tr class="common">
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorItemAppDate" id=EdorItemAppDate readonly></td>
                <!--<td class="title">��Ч����</td>
                <td class="input"><input type="text" class="readonly" name="EdorValiDate" readonly></td>
               --> 
                 <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                 <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
	</div>
  <table>
    <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
    </tr>
  </table>
  <Div  id= "divAGInfo" class=maxbox1 style= "display: ''">
      <TABLE class=common>
        <TR  class= common>
      <TD  class= title > Ͷ��������</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=AppntName id=AppntName>
      </TD>
      <TD class = title > ֤������ </TD>
      <TD class = input >
        <Input class=codeno  "readonly wid" readonly name=AppntIDType id=AppntIDType ><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly>
      </TD>
      <TD class = title > ֤������ </TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=AppntIDNo id=AppntIDNo>
      </TD>
    </TR>
           <TR  class= common>
      <TD  class= title > ����������</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=InsuredName id=InsuredName >
      </TD>
      <TD class = title > ֤������ </TD>
      <TD class = input >
        <Input class=codeno  "readonly" readonly name=InsuredIDType id=InsuredIDType ><input class=codename name=InsuredIDTypeName id=InsuredIDTypeName readonly>
      </TD>

      <TD class = title > ֤������ </TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=InsuredIDNo id=InsuredIDNo>
      </TD>
    </TR>
      </TABLE>
  </Div>
  <table>
     <tr>
        <td class="common">
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
        </td>
        <td class= titleImg>
          ����������Ϣ
        </td>
     </tr>
  </table>
  <Div  id= "divLCGrpPol" style= "display: ''">
     <table  class= common>
        <tr  class= common>
            <td><span id="spanPolGrid"></span></td>
        </tr>
     </table>
  </DIV>
  <table>
     <tr>
        <td class="common">
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNewPol);">
        </td>
        <td class= titleImg>
          ����������Ϣ
        </td>
     </tr>
  </table>
  <Div  id= "divNewPol" style= "display: ''">
     <table  class= common>
        <tr  class= common>
            <td><span id="spanNewPolGrid"></span></td>
        </tr>
     </table>
  </DIV>
  <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <td class= titleImg>
        �����˽�����֪��Ϣ<font color=red>(����¼������¼��֪)</font>
      </td>
   </tr>
   </table>

  <Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
    <br> 
  <Div id= "divEdorquery" class=maxbox1 style="display: ''">
  	   <table class="common">
      <tr class=common>
          <td class=title>��������������</td>
          <td class=input><Input class="codeno"  id=NewAddType name=NewAddType verify="����|NotNull&Code:newaddtype" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('newaddtype',[this,NewAddTypeName],[0,1]);" onkeyup="return showCodeListKey('newaddtype',[this,NewAddTypeName],[0,1]);">
		  <input class=codename id=NewAddTypeName name=NewAddTypeName ></td>                    
          <td class="title"><Div id="divApproveMofiyReasonTitle" style="display:"> ��Ч�� </Div></td>
          <td class="input"><Div id="divApproveMofiyReasonInput" style="display:"><Input class="coolDatePicker" dateFormat="short" id=NewCvaliDate name=NewCvaliDate onClick="laydate({elem: '#NewCvaliDate'});" ><span class="icon"><a onClick="laydate({elem: '#NewCvaliDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></Div></td>      <!--"coolDatePicker" dateFormat="short"     -->        
          <td class="title">&nbsp;</td>
          <td class="title">&nbsp;</td>
          <td class="input">&nbsp;</td>
      </tr>
     </table> 
        <Input class="cssButton" type=Button name="DetailButton" value="����������Ϣ" onclick="gotoRisk()">
        &nbsp;
        <Input class="cssButton" type=Button value="  ɾ������  " onclick="deleteapp()">
        <br><br>

       <Input class="cssButton" type=Button value=" �� �� " onclick="edorTypeNSSave()">
        <Input class="cssButton" type=Button value=" �� �� " onclick="returnParent()">
        <Input class="cssButton" TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
</Div>

     <input type="hidden" name="fmtransact" id=fmtransact>
     <input type="hidden" name="ContType" id=ContType>
     <input type="hidden" name="addrFlag" id=addrFlag>
     <input type="hidden" name="CustomerNo" id=CustomerNo>
     <input type="hidden" name="EdorNo" id=EdorNo>
     <input type="hidden" name="PolNo" id=PolNo>
     <input type="hidden" name="MainPolNo" id=MainPolNo>
     <input type="hidden" name="InsuredNo" id=InsuredNo>
     <input type="hidden" name="EdorValiDate" id=EdorValiDate>

     <input type="hidden" name="CureCvaliDate" id=CureCvaliDate>
     <input type="hidden" name="PreCvaliDate" id=PreCvaliDate>
     <input type="hidden" name="NextCvaliDate" id=NextCvaliDate>
     <input type="hidden" name="CurDate" id=CurDate>
    <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
