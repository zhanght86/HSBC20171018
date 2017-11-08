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
 * @version  : 1.00, 1.01
 * @date     : 2006-07-05, 2006-12-05
 * @direction: �ŵ���ȫ�˹��˱��ֵ��������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ include file="../common/jsp/AccessCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���屣ȫ���˺˱�</title>
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
    <script language="JavaScript" src="GEdorManuUWInsured.js"></script>
    <%@ include file="GEdorManuUWInsuredInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ������ѯ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divPEdorSearchInput)"></td>
                <td class="titleImg">�����˲�ѯ</td>
            </tr>
        </table>
        <!-- ���˱�����ѯ¼�� -->
        <div id="divPEdorSearchInput" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">�ֵ���</td>
                    <td class="input"><input type="text" class="common wid" name="ContNo_Srh" id=ContNo_Srh></td>
                    <td class="title">�����˺�</td>
                    <td class="input"><input type="text" class="common wid" name="InsuredNo_Srh" id=ContNo_Srh></td>
                    <td class="title">Ա�����</td>
                    <td class="input"><input type="text" class="common wid" name="CustomerSeqNo_Srh" id=ContNo_Srh></td>
                </tr>
                    <td class="title">����������</td>
                    <td class="input"><input type="text" class="common wid" name="InsuredName_Srh" id=ContNo_Srh></td>
                    <td class="title">�Ա�</td>
                    <td class="input"><input type="text" class="codeno" name="Sex_Srh" id=Sex_Srh style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('Sex',[this,SexName_Srh],[0,1])" onkeyup="showCodeListKey('Sex',[this,SexName_Srh],[0,1])"><input type="text" class="codename" name="SexName_Srh" id=SexName_Srh readonly></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="Birthday_Srh" onClick="laydate({elem: '#Birthday_Srh'});" id="Birthday_Srh"><span class="icon"><a onClick="laydate({elem: '#Birthday_Srh'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>
                </tr>
                <tr class="common">
                    <td class="title">֤������</td>
                    <td class="input"><input class="codeno" name="IDType_Srh" id=IDType_Srh style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('IDType',[this,IDTypeName_Srh],[0,1])" onkeyup="showCodeListKey('IDType',[this,IDTypeName_Srh],[0,1])"><input class="codename" name="IDTypeName_Srh" id=IDTypeName_Srh readonly></td>
                    <td class="title">֤������</td>
                    <td class="input"><input type="text" class="common wid" name="IDNo_Srh" id=IDNo_Srh></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
            <!-- ��ѯ���ݲ�����ť -->
            <input type="button" class="cssButton" value=" �� ѯ " onclick="queryInsuredGrid()">
        </div>
        <!-- ������ѯ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divInsuredList)"></td>
                <td class="titleImg">�����˲�ѯ���</td>
           </tr>
        </table>
        <!-- ������ѯ���չ�� -->
        <div id="divInsuredList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanInsuredGrid"></span></td>
                </tr>
            </table>
            <!-- ������ѯ�����ҳ -->
            <div id="divTurnPageInsuredGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageInsuredGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageInsuredGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageInsuredGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageInsuredGrid.lastPage()">
            </div>
        </div>
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divContInfo)"></td>
                <td class="titleImg">��������Ϣ</td>
           </tr>
        </table>
        <!-- ������Ϣ�н��չ�� -->
        <div id="divContInfo" style="display:''">

                <div id="divContInfotr" style="display:'none'"> <!-- ��������Ϣ����,��ʱ���ص� -->
                    <table class="common">
                <tr class="common">
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                    <td class="title">���屣����</td>
                    <td class="input"><input type="text" class="readonly wid" name="GrpContNo" id=EdorAcceptNo readonly></td>
                    <td class="title">���˱�����</td>
                    <td class="input"><input type="text" class="readonly wid" name="ContNo" id=EdorAcceptNo readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">����ͻ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntNo" id=AppntNo readonly></td>
                    <td class="title">Ͷ����λ</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" id=AppntNo readonly></td>
                    <td class="title">Ͷ�����Ա�</td>
                    <td class="input"><input type="text" class="codeno" name="AppntSex" id=AppntSex readonly><input type="text" class="codename" name="AppntSexName" id=AppntSexName readonly></td>
                </tr>
               </table>
              </div>
               <table class="common">
                <tr class="common">
                    <td class="title">�����˿ͻ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredNo" id=InsuredNo readonly></td>
                    <td class="title">����������</td>
                    <td class="input"><input type="text" class="readonly wid" name="InsuredName" id=InsuredName readonly></td>
                    <td class="title">�������Ա�</td>
                    <td class="input"><input type="text" class="codeno" name="InsuredSex" id=InsuredSex readonly><input type="text" class="codename" name="InsuredSexName" id=InsuredSexName readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="coolDatePicker" name="InsuredBirthday" id=InsuredBirthday readonly onClick="laydate({elem: '#InsuredBirthday'});" id="InsuredBirthday"><span class="icon"><a onClick="laydate({elem: '#InsuredBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>
                    <td class="title">֤������</td>
                    <td class="input"><input type="text" class="codeno" name="InsuredIDType" id=InsuredIDType readonly><input type="text" class="codename" name="InsuredIDTypeName" id=InsuredIDTypeName readonly></td>
                    <td class="title">֤������</td>
                    <td class="input"><input type="text" class="readonly" name="InsuredIDNo" readonly></td>
                </tr>
            </table>
        </div>
        <!-- ��������Ϣ��ѯ��ť���� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divInsuerdInfo)"></td>
                <td class="titleImg">�����˸�����Ϣ��ѯ</td>
           </tr>
        </table>
        <div id="divInsuerdInfo" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="input">
                          <input type="button" class="cssButton" value="  ���˱�����ϸ��Ϣ  " onclick="showPolDetail()">
                        <input type="button" class="cssButton" value="   �����˽�����֪   " onclick="queryHealthApprize()">
                        <input type="button" class="cssButton" value="   �������������   " onclick="queryBodyInspect()">
                        <input type="button" class="cssButton" value="   �����˱����ۼ�   " onclick="queryAccumulateAmnt()">
                        <br><br>
                        <input type="button" class="cssButton" value="  �������ѳб�����  " onclick="queryAlreadyProposal()">
                        <input type="button" class="cssButton" value="  ������δ�б�����  " onclick="queryUnProposalCont()">
                        <input type="button" class="cssButton" value="   �����˼�����ȫ   " onclick="queryLastEdorTrack()">
                        <input type="button" class="cssButton" value="   �����˼�������   " onclick="queryLastClaimTrack()">
                    </td>
                </tr>
            </table>
        </div>
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divPolList)"></td>
                <td class="titleImg">���˱���������Ϣ</td>
           </tr>
        </table>
        <!-- ������Ϣ���չ�� -->
        <div id="divPolList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanPolGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ϣ�����ҳ -->
            <div id="divTurnPagePolGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePolGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePolGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePolGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePolGrid.lastPage()">
            </div>
        </div>
        <!-- �˱������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divEdorOperations)"></td>
                <td class="titleImg">��ȫ�˱�����</td>
           </tr>
        </table>
        <!-- �˱�������ť���� -->
        <div id="divEdorOperations" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="input">
                        <!--
                        <input type="button" class="cssButton" value="  �����˽�����֪��ѯ  " onclick="queryHealthApprize()">
                        <input type="button" class="cssButton" value="  ������������ϲ�ѯ  " onclick="queryBodyInspect()">
                        <input type="button" class="cssButton" value="  �����˱����ۼƲ�ѯ  " onclick="queryAccumulateAmnt()">
                        <br><br>
                        <input type="button" class="cssButton" value=" �������ѳб�������ѯ " onclick="queryAlreadyProposal()">
                        <input type="button" class="cssButton" value="������δ�б�Ͷ������ѯ" onclick="queryUnProposalCont()">
                        <input type="button" class="cssButton" value="�����˼�����ȫ��Ϣ��ѯ" onclick="queryLastEdorTrack()">
                        <input type="button" class="cssButton" value="�����˼���������Ϣ��ѯ" onclick="queryLastClaimTrack()">
                        <br><br>
                        <input type="button" class="cssButton" value="   ���˵��ӷ�¼��   " onclick="showAddinPremInput()">
                        <input type="button" class="cssButton" value="   ���˵���Լ¼��   " onclick="showSpecStipulation()">
                        <br><br>
                        <input type="button" class="cssButton" value=" �����˱�ͨ�����зֵ� " onclick="batchUWPassAll()">
                        -->
                        <input type="button" class="cssButton" value="       ���¼��     " onclick="showHealth()">
                        <input type="button" class="cssButton" value="       ����¼��     " onclick="showRReport()">
                        <input type="button" class="cssButton" value="       �ӷ�¼��     " onclick="showAdd()">
                        <input type="button" class="cssButton" value="       ��Լ¼��     " onclick="showSpec()">
                    </td>
                </tr>
            </table>
        </div>
        <!-- �����˱��۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divContUWInput)"></td>
                <td class="titleImg">�������ֺ˱�����</td>
            </tr>
        </table>
        <!-- �����˱�����¼�� -->
        <div id="divContUWInput" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">�˱�����</td>
                    <td class="input"><input type="text" class="codeno" name="UWState" id=UWState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="�ŵ��ֵ��˱�����|NotNull&Code:gedorpoluw" ondblClick="showCodeList('gedorpoluw',[this,UWStateName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('gedorpoluw',[this,UWStateName],[0,1],null,null,null,0,207)"><input type="text" class="codename" name="UWStateName" id=UWStateName readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="title">�˱����</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="input" colspan="6"><textarea class="common wid" name="UWIdea" id=UWIdea cols="108" rows="3" verify="�ŵ��ֵ��˱����|Len<255"></textarea></td>
                </tr>
            </table>
            <!-- �ύ���ݲ�����ť -->
            <input type="button" class="cssButton" value=" ȷ �� " onclick="saveContUW()">
            <input type="button" class="cssButton" value=" �� �� " onclick="resetContUW()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <br><br>
        </div>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="MissionID" id=MissionID>
        <input type="hidden" name="SubMissionID" id=SubMissionID>
        <input type="hidden" name="ActivityID" id=ActivityID>
        <input type="hidden" name="ActivityStatus" id=ActivityStatus>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="PrtNo" id=PrtNo >
        <input type="hidden" name="ProposalContNo" id=ProposalContNo>
        <input type="hidden" name="PolNo" id=PolNo>
        <input type="hidden" name="PayToDate" id=PayToDate>
        <input type="hidden" name="UWType" id=UWType>
        <input type="hidden" name="ActionFlag" id=ActionFlag>
        <input type="hidden" name="LoginOperator" id=LoginOperator>
        <input type="hidden" name="LoginManageCom" id=LoginManageCom>
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
	
<br/><br/><br/><br/>
</body>
</html>
