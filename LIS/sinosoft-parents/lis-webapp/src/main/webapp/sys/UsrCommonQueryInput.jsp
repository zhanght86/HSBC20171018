<%
//�������ƣ�UsrCommonQueryInput.jsp
//�����ܣ�ϵͳ�û���Ϣ��ѯ
//�������ڣ�2005-11-30(Ϊ������Чͳ��ui\f1print\WorkAchieveStatInput.jsp���ṩ�û��Ĳ�ѯ��������)
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    String tManageCom = "";
    String tUserCode = "";
    String tQueryFlag = "";    //���������Ǻδ�����
    try
    {
        tManageCom = request.getParameter( "ManageCom" );
        //loggerDebug("UsrCommonQueryInput","---tManageCom:"+tManageCom);
        tQueryFlag = request.getParameter("queryflag");
        //loggerDebug("UsrCommonQueryInput","---tQueryFlag:"+tQueryFlag);
        tUserCode = request.getParameter( "UserCode" );
    }
    catch( Exception e1 )
    {
        //loggerDebug("UsrCommonQueryInput","---Exception:"+e1);
    }
%>

<html>
<script>
    var managecom = "<%=tManageCom%>";
    var queryFlag = "<%=tQueryFlag%>";
    var usercode = "<%=tUserCode%>";
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./UsrCommonQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./UsrCommonQueryInit.jsp"%>
  <title>�û���ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <!--�û���ѯ���� -->
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUsr);">
            </td>
            <td class= titleImg>
                �û���ѯ����
            </td>
        </tr>
    </table>
  <Div  id= "divUsr" style= "display: ''" class="maxbox">
  <table  class= common>
      <TR  class= common>
          <TD  class= title>   �������  </TD>
        <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="ManageCom" id="ManageCom" verify="�������|Code:Station" ondblclick="showCodeList('Station',[this,ManageComName],[0,1])" onMouseDown="showCodeList('Station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('Station',[this,ManageComName],[0,1])"><input type="text" class="codename" name="ManageComName" id="ManageComName" readonly></td>
        <TD class= title>   �û�����  </TD>
        <TD  class= input>  <Input class="wid" class=common  name=UsrCode id=UsrCode > </TD>
        <TD class= title>   �û�����  </TD>
        <TD  class= input>  <Input class="wid" class=common  name=UsrName id=UsrName > </TD>
      </TR>
        <!-- XinYQ modified 20 2006-12-23 : �������屣ȫȨ�� -->
        <tr class="common">
            <td class="title">������ȫȨ�޼���</td>
            <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="EdorPopedom" id="EdorPopedom" verify="����Ȩ�޼���|Code:EdorPopedom" ondblclick="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onMouseDown="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onkeyup="showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1])"><input type="text" class="codename" name="EdorPopedomName" id="EdorPopedomName" readonly></td>
            <td class="title">�ŵ���ȫȨ�޼���</td>
            <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="GEdorPopedom" id="GEdorPopedom" verify="�ŵ�Ȩ�޼���|Code:GEdorPopedom" ondblclick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onMouseDown="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onkeyup="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></td>
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>
        </tr>
  </table>
 <!-- <table>
    <tr>
        <td><INPUT class=cssButton VALUE=" �� ѯ " TYPE=button onclick="easyQueryClick()"></td>
        
    </tr>
  </table>-->
  </Div>
<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
    <table>
        <tr>
        <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUsrGrid)">
            </td>
            <td class= titleImg>
                 �û���Ϣ�б�
            </td>
        </tr>
    </table>
    <Div  id= "divUsrGrid" style= "display: ''">
      <table  class= common>
            <tr  class= common>
                <td><span id="spanUsrGrid"></span></td>
            </tr>
      </table>
      <!--<div align="left"><INPUT class=cssButton VALUE=" �� �� " TYPE=button onclick="returnParent()"></div>-->
            <div align="center" style= "display:''">
                <!--<input type="button" class="cssButton" value="��  ҳ" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPage.lastPage()">-->
            </div>
    </div><a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	  <br/><br/><br/><br/>
  </form>
</body>
</html>
