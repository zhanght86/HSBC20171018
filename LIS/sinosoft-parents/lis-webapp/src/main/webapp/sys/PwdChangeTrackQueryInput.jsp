<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-03-20
 * @direction: ��ȫ���������޸Ĺ켣��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���������޸Ĺ켣��ѯ</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PwdChangeTrackQueryMain.jsp";
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
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PwdChangeTrackQuery.js"></script>
    <%@ include file="PwdChangeTrackQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ������Ϣ��ʾ��� -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
        <!-- �޸Ĺ켣�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPwdTrackGrid)"></td>
                <td class="titleImg">�����޸Ĺ켣</td>
           </tr>
        </table>
        <!-- �޸Ĺ켣���չ�� -->
        <div id="divPwdTrackGrid" style="display: ">
            <table class="common">
                <tr class="common">
                    <td><span id="spanPwdTrackGrid"></span></td>
                </tr>
            </table>
            <!-- �޸Ĺ켣�����ҳ -->
            <div id="divTurnPagePwdTrackGrid" align="center" style="display:none;">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePwdTrackGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePwdTrackGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePwdTrackGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePwdTrackGrid.lastPage()">
            </div>
        </div>
        <br>
        <!-- �ύ���ݲ�����ť -->
        <input type="button" class="cssButton" value=" �� �� " onclick="returnParent()">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
