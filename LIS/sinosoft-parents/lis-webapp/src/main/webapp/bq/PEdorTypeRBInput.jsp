
<%
//�������ƣ�
//�����ܣ����˱�ȫ����
//�������ڣ�2005-09-20 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ȫ����</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeRB.jsp";
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
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeRB.js"></script>
    <%@ include file="PEdorTypeRBInit.jsp" %>
</head>
<body  onload="initForm();" >
  <form name="fm" id=fm method=post target="fraSubmit">
    <div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
            </tr>
            <tr class="common">
                <td class="title">������������</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">��Ч����</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
	</div>
    <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
      </td>
      <td class= titleImg>
        ��ȫ��ʷ��¼
      </td>
    </tr>
   </table>
  <Div  id= "divEdorItemGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
    </Div>

    <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLastEdorItemGrid);">
      </td>
      <td class= titleImg>
        ��ǰ���˱�ȫ
      </td>
    </tr>
   </table>
  <Div  id= "divLastEdorItemGrid" style= "display: ''">
            <table class= common>
                <tr class= common>
                    <td><span id="spanLastEdorItemGrid"></span></td>
                </tr>
            </table>
    </Div>

        <!-- XinYQ added on 2007-05-25 : BGN -->
        <!-- ��ȫԭ���۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorReasonLayer)"></td>
                <td class="titleImg">��ȫ���ԭ��</td>
            </tr>
        </table>
        <!-- ��ȫԭ��¼���� -->
        <div id="divEdorReasonLayer" class=maxbox1 style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">����ԭ��</td>
                    <td class="input"><input type="text" class="codeno" name="EdorReason" id=EdorReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="����ԭ��|NotNull&Code:PEdorRBReason" ondblclick="showCodeList('PEdorRBReason',[this,EdorReasonName],[0,1])" onkeyup="showCodeListKey('PEdorRBReason',[this,EdorReasonName],[0,1])"><input type="text" class="codename" name="EdorReasonName" id=EdorReasonName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="title">��ע��Ϣ</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="input" colspan="6"><textarea class="common" name="Remark" id=Remark cols="108" rows="3" verify="��ע��Ϣ|Len<200"></textarea></td>
                </tr>
            </table>
        </div>
        <!-- XinYQ added on 2007-05-25 : END -->

   <br>
   <div id= "divEdorQuery" style="display: ''">
        <Input class="cssButton" type=button name="btnSaveEdorRB" value=" �� �� " onclick="saveEdorTypeRB()">
        <Input class="cssButton" type=button value=" �� �� " onclick="returnParent()">
        <Input class="cssButton" type=button value="���±��鿴" onclick="showNotePad()">
    </div>

        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
        <br><br><br><br>
    <input type=hidden name="fmtransact">
    <input type=hidden name="ContType">
    <input type=hidden name="EdorNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
