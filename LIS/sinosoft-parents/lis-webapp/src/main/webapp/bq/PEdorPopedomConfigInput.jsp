<html>
<%
//�������ƣ�PEdorPopedomConfigInput.jsp
//�����ܣ���ȫȨ������
//�������ڣ�2006-01-09 14:30:22
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head >
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <title>�û���ȫȨ������</title>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="PEdorPopedomConfig.js"></SCRIPT>
  <%@include file="PEdorPopedomConfigInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form name=fm id=fm method=post target="fraSubmit">
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrade);">
            </td>
            <td class= titleImg>Ȩ�޼���</td>
        </tr>
    </table>

            <!-- XinYQ modified 20 2006-12-23 : �������屣ȫȨ�� -->
            
            <div id="divGrade" style="display: "><div class="maxbox1">
                <table class="common">
                    <tr class="common">
                        <td class="title5" style="width:7%">Ȩ�����</td>
                        <td class="input5"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="EdorPopedomType" id="EdorPopedomType" verify="Ȩ�����|NotNull&Code:EdorPopedomType" onClick="return showCodeList('EdorPopedomType',[this,EdorPopedomTypeName],[0,1])" onDblClick="return showCodeList('EdorPopedomType',[this,EdorPopedomTypeName],[0,1])"   onKeyUp="showCodeListKey('EdorPopedomType',[this,EdorPopedomTypeName],[0,1])"><input type="text" class="codename" name="EdorPopedomTypeName" id="EdorPopedomTypeName" readonly></td>
                        <td class="title5" style="width:7%">
                            <div id="divEdorPopedomTitle" style="display:none">���˱�ȫȨ�޼���</div>
                            <!--<div id="divGEdorPopedomTitle" style="display:none">���屣ȫȨ�޼���</div>-->
                        </td>
                        <td class="input5">
                            <div id="divEdorPopedomInput" style="display:none"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="EdorPopedom" id="EdorPopedom" verify="����Ȩ�޼���|Code:EdorPopedom" onDblClick="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onClick=""="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onKeyUp="showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1])"><input type="text" class="codename" name="EdorPopedomName" id="EdorPopedomName" readonly></div>
                           <!--<div id="divGEdorPopedomInput" style="display:none"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="GEdorPopedom" id="GEdorPopedom" verify="�ŵ�Ȩ�޼���|Code:GEdorPopedom" ondblclick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onMouseDown="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onkeyup="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></div>-->

                    </tr>
                    <tr>
                    	<td class="title5" style="width:7%"><div id="divGEdorPopedomTitle" style="display:none">���屣ȫȨ�޼���</div></td>
                        <td class="input5"><div id="divGEdorPopedomInput" style="display:none"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="GEdorPopedom" id="GEdorPopedom" verify="�ŵ�Ȩ�޼���|Code:GEdorPopedom" onDblClick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onClick=""="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onKeyUp="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></div></td>
                        <td class="title5" style="width:7%"></td>
                        <td class="input5"></td>

                    </tr>
                </table>
            </div>
            </div>
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
            </td>
            <td class= titleImg>Ȩ������</td>
        </tr>
    </table>
    <Div  id= "divSelfGrid" style= "display:  ">
    <div class="maxbox1">
        <div id="divEdorPopedomMoney" style="display:none">

        
            <table class= common >
            	  <TR  class= common>
            	      <TD class= title5 style="width:7%"> �����ո��ѽ������ </TD>
            	      <td class= input5><Input class="wid" class="common" name=LimitGetMoneyI id=LimitGetMoneyI></td>
            	      <TD class= title5 style="width:7%"> �����ո��ѽ������ </TD>
            	      <td class= input5><Input class="wid" class="common" name=LimitGetMoneyY id=LimitGetMoneyY></td>
            	  </tr>
            </table>
        </div>
        <div id="divGEdorPopedomMoney" style="display:none">
            <table class= common >
            	  <TR  class= common>
            	      <TD class= title5 style="width:7%"> �����ո��ѽ������ </TD>
            	      <td class= input5><Input class="wid" class="common" name=LimitGetMoneyL id=LimitGetMoneyL></td>
            	      <TD class= title5 style="width:7%"> �����ո��ѽ������ </TD>
            	      <td class= input5><Input class="wid" class="common" name=LimitGetMoneyM id=LimitGetMoneyM></td>
            	  </tr>
            </table>
        </div>
        <table class= common style="display: " >
            <tr class="common">
                <TD class= title5 style="width:7%" > �������������޸�Ȩ�� </TD>
                <td class= input5><Input class="wid" class="common" name=ModifyAppDateNum id=ModifyAppDateNum></td>
                <td class="title5">&nbsp;</td>
                <td class="input5">&nbsp;</td>
            </TR>
        </table><br>
        <table  class= common>
            <tr  class= common>
                <td><span id="spanSelfGrid"></span></td>
            </tr>
        </table>
    </div>
    </Div>
    <br>
    <!--<INPUT class= cssButton TYPE=button VALUE=" �� �� " onclick="saveGradePopedom()">-->
    <a href="javascript:void(0);" class="button" onClick="saveGradePopedom();">��    ��</a>
     <input type=hidden id="ManageCom"       name= "ManageCom"  id= "ManageCom"><!--����Ҫ�ṩ����������Ȩ��ʱ�ٰѴ˸�Ϊ����ѡ�����ֻ���ز�Ĭ��Ϊ86-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
