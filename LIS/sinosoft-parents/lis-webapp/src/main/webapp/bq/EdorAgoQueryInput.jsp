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
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-12-14, 2006-03-21, 2006-08-19, 2006-10-26
 * @direction: ��ȫ�˹��˱�Ͷ(��)���˼�����ȫ��ѯ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>������ȫ��ѯ</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
	<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="EdorAgoQuery.js"></script>
    <%@ include file="EdorAgoQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- �ͻ�����������ʾ -->
		<div class="maxbox1">
        <table class="common">
            <tr class="common">
                <td class="title5">�ͻ���</td>
                <td class="input5"><input type="text" class="readonly wid" name="CustomerNo" id=CustomerNo readonly></td>
                <td class="title5">�ͻ�����</td>
                <td class="input5"><input type="text" class="readonly wid" name="CustomerName" id=CustomerName readonly></td>
                
            </tr>
        </table></div>
        <!-- ���������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorGrid)"></td>
                <td class="titleImg">������ȫ������Ϣ</td>
           </tr>
        </table>
        <!-- �����������չ�� -->
        <div id="divEdorGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorGrid"></span></td>
                </tr>
            </table>
            <!-- �������������ҳ -->
            <div id="divTurnPageEdorGrid" align="center" style="display:'none'">
                <input type="button" class=cssButton90 value="��  ҳ" onclick="turnPageEdorGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageEdorGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageEdorGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageEdorGrid.lastPage()">
            </div>
            <!-- �ύ���ݲ�����ť -->
            <input type="button" class="cssButton" value=" Ӱ�����ϲ�ѯ " onclick="showImage()">
            <input type="button" class="cssButton" value="��ȫ�˱��ջ��ѯ" onclick="EdorUWQuery()">
            <br>
        </div>
        <div id="divPrintEdorItemLayer" style="display:'none'">
            <br>
            <!-- �˱�֪ͨ�۵�չ�� -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPrintGrid)"></td>
                    <td class="titleImg">������ȫ�����˱�֪ͨ����Ϣ</td>
                </tr>
            </table>
            <!-- �˱�֪ͨ���չ�� -->
            <div id="divPrintGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanPrintGrid"></span></td>
                    </tr>
                </table>
                <!-- ������Ŀ�����ҳ -->
                <div id="divTurnPagePrintGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePrintGrid.firstPage()">
                    <input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePrintGrid.previousPage()">
                    <input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePrintGrid.nextPage()">
                    <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePrintGrid.lastPage()">
                </div>
                <!-- ��ӡ���ݲ�����ť -->
                <input type="button" class="cssButton" value="�鿴�˱�֪ͨ��" onclick="printEdorUWNotice()">
            </div>
            <br>
            <!-- ������Ŀ�۵�չ�� -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorItemGrid)"></td>
                    <td class="titleImg">������ȫ������Ŀ��Ϣ</td>
                </tr>
            </table>
            <!-- ������Ŀ���չ�� -->
            <div id="divEdorItemGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanEdorItemGrid"></span></td>
                    </tr>
                </table>
                <!-- ������Ŀ�����ҳ -->
                <div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageEdorItemGrid.firstPage()">
                    <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageEdorItemGrid.previousPage()">
                    <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageEdorItemGrid.nextPage()">
                    <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageEdorItemGrid.lastPage()">
                </div>
                <!-- ��ѯ���ݲ�����ť -->
                <input type="button" class="cssButton" value=" ��ȫ��ϸ��ѯ " onclick="showEdorItemDetail()">
                <br>
            </div>
        </div>
        <div id="divPremSpecLayer" style="display:'none'">
            <br>
            <!-- �ӷ���Ŀ�۵�չ�� -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPremGrid)"></td>
                    <td class="titleImg">������ȫ������Ŀ�ӷ���Ϣ</td>
                </tr>
            </table>
            <!-- �ӷ���Ŀ���չ�� -->
            <div id="divPremGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanPremGrid"></span></td>
                    </tr>
                </table>
                <!-- �ӷ���Ŀ�����ҳ -->
                <div id="divTurnPagePremGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePremGrid.firstPage()">
                    <input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePremGrid.previousPage()">
                    <input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePremGrid.nextPage()">
                    <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePremGrid.lastPage()">
                </div>
            </div>
            <br>
            <!-- �ر�Լ���۵�չ�� -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSpecGrid)"></td>
                    <td class="titleImg">������ȫ������Ŀ�ر�Լ����Ϣ</td>
                </tr>
            </table>
            <!-- �ر�Լ�����չ�� -->
            <div id="divSpecGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanSpecGrid"></span></td>
                    </tr>
                </table>
                <!-- �ر�Լ�������ҳ -->
                <div id="divTurnPageSpecGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageSpecGrid.firstPage()">
                    <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageSpecGrid.previousPage()">
                    <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageSpecGrid.nextPage()">
                    <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageSpecGrid.lastPage()">
                </div>
            </div>
        </div>
        <br>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorAcceptNo" id=EdorAcceptNo>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="ContNo" id=ContNo>
        <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
        <input type="hidden" name="EdorValiDate" id=EdorValiDate>
        <input type="hidden" name="CurEdorAcceptNo" id=CurEdorAcceptNo>
        <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
        <!-- �ر�ҳ�������ť -->
        <input type="button" class="cssButton" value="   ��    ��   " onclick="returnParent()">
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
