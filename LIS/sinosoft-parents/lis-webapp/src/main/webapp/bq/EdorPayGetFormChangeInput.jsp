<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2006-03-16, 2006-05-16, 2006-06-28, 2006-10-24, 2006-11-08
 * @direction: ��ȫ�ո��ѷ�ʽ��������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ȫ�ո��ѷ�ʽ���</title>
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
    <script language="JavaScript" src="EdorPayGetFormChange.js"></script>
    <%@ include file="EdorPayGetFormChangeInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- ��ѯ�����۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSearchLayer)"></td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <!-- ��ѯ����¼���� -->
        <div id="divSearchLayer" style="display:''">
        <div class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="title5">��ȫ�����</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="EdorAcceptNo_srh" id="EdorAcceptNo_srh" onkeypress="queryOnKeyPress()"></td>
                    <td class="title5">��ȡ֪ͨ���</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="GetNoticeNo_srh" id="GetNoticeNo_srh" onkeypress="queryOnKeyPress()"></td>
                </tr>
                <tr class="common">
                    <td class="title5">��ȡ��</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="Drawer_srh" id="Drawer_srh" onkeypress="queryOnKeyPress()"></td>
                    <td class="title5">��ȡ��֤����</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="DrawerIDNo_srh" id="DrawerIDNo_srh" onkeypress="queryOnKeyPress()"></td>
                </tr>
            </table>
            <!-- ��ѯ���ݲ�����ť -->
            
            </div>
        </div>
			<!--<input type="button" class="cssButton" value=" �� ѯ " onclick="queryEdorInfoGrid()">-->
            <a href="javascript:void(0);" class="button" onClick="queryEdorInfoGrid();">��    ѯ</a>

        <!-- ��ȫ��Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorInfoList)"></td>
                <td class="titleImg">��ȫ������Ϣ</td>
           </tr>
        </table>
        <!-- ��ȫ��Ϣ���չ�� -->
        <div id="divEdorInfoList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorInfoGrid"></span></td>
                </tr>
            </table></div></div>
            <!-- ��ȫ��Ϣ�����ҳ -->
            <div id="divTurnPageEdorInfoGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageEdorInfoGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageEdorInfoGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageEdorInfoGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageEdorInfoGrid.lastPage()">
            
        </div>

        <!-- ��ʽ���¼���� -->
        
        	        <!-- ��ʽ����۵�չ�� -->
        		<table>
        		    <tr>
        		        <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPayGetFormInput)"></td>
        		        <td class="titleImg">�ո��ѷ�ʽ���</td>
        		    </tr>
        		</table>
                <div id="divPayGetFormInput" style="display:''">
                <div class="maxbox">
            <table class="common">
                <tr class="common">
                    <!--
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="codeno" name="ChangeType" verify="�������|NotNull" CodeData="0|^1|�շ�^2|����" ondblClick="showCodeListEx('nothis',[this,ChangeTypeName],[0,1])" onkeyup="showCodeListKeyEx('nothis',[this,ChangeTypeName],[0,1])"><input type="text" class="codename" name="ChangeTypeName" readonly></td>
                    -->
                    <td class="title5">�µķ�ʽ</td>
                    <td class="input5"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="FormType" id="FormType" verify="�ո��ѷ�ʽ|NotNull&Code:EdorGetPayForm"  ondblclick="showCodeList('EdorGetPayForm',[this,FormTypeName],[0,1])" onMouseDown="showCodeList('EdorGetPayForm',[this,FormTypeName],[0,1])" onkeyup="showCodeListKey('EdorGetPayForm',[this,FormTypeName],[0,1]);"><input type="text" class="codename" name="FormTypeName" id="FormTypeName" readonly></td>
 <td class="title5">��������</td>
                    <td class="input5"><input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" type="text" class="codeno" name="BankCode" id="BankCode" disabled verify="��������|Code:Bank" ondblclick="showCodeList('Bank',[this,BankCodeName],[0,1])" onMouseDown="showCodeList('Bank',[this,BankCodeName],[0,1])" onkeyup="showCodeListKey('Bank',[this,BankCodeName],[0,1])"><input type="text" class="CodeName" name="BankCodeName" id="BankCodeName" readonly disabled></td>
                </tr>
                <tr class="common" id="divBankInfo" style="display:''">
                   
                    <td class="title5">�����ʺ�</td>
                    <td class="input5"><input type="text" class="wid" class="coolConfirmBox" name="BankAccNo" id="BankAccNo" disabled></td>
                    <td class="title5">�ʻ���</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="AccName" id="AccName" disabled></td>
                </tr>
                <tr class="common">
                    <td class="title5">���˷���ȡ��</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="PayGetName" id="PayGetName"></td>
                    <td class="title5">��ȡ�����֤</td>
                    <td class="input5"><input type="text" class="wid" class="common" name="PersonID" id="PersonID"></td>
                </tr>
            </table>
        </div>
        </div>
        
        <!--<input type="button" class="cssButton" value=" �� �� " onClick="savePayGetForm()">-->
        <a href="javascript:void(0);" class="button" onClick="savePayGetForm();">��    ��</a>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorAcceptNo" id="EdorAcceptNo">
        <input type="hidden" name="GetNoticeNo" id="GetNoticeNo">
        <input type="hidden" name="ActuGetNo" id="ActuGetNo">
        <input type="hidden" name="ChangeType" id="ChangeType">
        <input type="hidden" name="PrtSeq" id="PrtSeq">
        <input type="hidden" name="CurrentManageCom" id="CurrentManageCom">
        <!-- �ύ���ݲ�����ť -->
        
       
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
    <br><br><br><br>
</body>
</html>
