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
 * @date     : 2006-01-20, 2006-02-24
 * @direction: ��ȫ��������Ϣ��ѯ������ҳ��
 ******************************************************************************/
%>

    <!-- ���ñ�ȫ��������Ϣ������ҳ�� : ��ʼ -->


    <!-- ������Ϣ�۵�չ�� -->
    <table>
        <tr>
            <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divAgentBaseInfo)"></td>
            <td class="titleImg">������Ϣ</td>
        </tr>
    </table>
    <!-- ������Ϣչ�ֱ�� -->
    <div id="divAgentBaseInfo" class=maxbox1 style="display:''">
        <table class="common">
            <tr class="common">
                <td class="title">�����˱���</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_AgentCode name="Agent_AgentCode" readonly></td>
                <td class="title">������״̬</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_AgentState name="Agent_AgentState" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
            <tr class="common">
                <td class="title">����������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Name name="Agent_Name" readonly></td>
                <td class="title">�Ա�</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Sex name="Agent_Sex" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Birthday name="Agent_Birthday" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">����</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Nationality name="Agent_Nationality" readonly></td>
                <td class="title">����</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_NativePlace name="Agent_NativePlace" readonly></td>
                <td class="title">����״��</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Marriage name="Agent_Marriage" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">�������</td>
                <td class="input"><input type="text" class="codeno" name="Agent_ManageCom" id=Agent_ManageCom readonly><input type="text" class="codename" name="Agent_ManageComName" id=Agent_ManageComName readonly></td>
                <td class="title">չҵ����</td>
                <td class="input"><input type="text" class="codeno" name="Agent_AgentGroup" id=Agent_AgentGroup readonly><input type="text" class="codename" name="Agent_AgentGroupName" id=Agent_AgentGroupName readonly></td>
                <td class="title">֤������</td>
                <td class="input"><input type="text" class="codeno" name="Agent_IDNoType" id=Agent_IDNoType readonly><input type="text" class="codename" name="Agent_IDNoTypeName" id=Agent_IDNoTypeName readonly></td>
            </tr>
            <tr class="common">
                <td class="title">ԭ������λ</td>
                <td class="input" colspan="3"><input type="text" class= "common3" id=Agent_OldCom name="Agent_OldCom" readonly></td>
                <td class="title">֤������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_IDNo name="Agent_IDNo" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">���������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_AgentKind name="Agent_AgentKind" readonly></td>
                <td class="title">�����ʸ�</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_SaleQuaf name="Agent_SaleQuaf" readonly></td>
                <td class="title">�ʸ�֤����</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_QuafNo name="Agent_QuafNo" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">¼������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_EmployDate name="Agent_EmployDate" readonly></td>
                <td class="title">ת������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_InDueFormDate name="Agent_InDueFormDate" readonly></td>
                <td class="title">��˾����</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_OutWorkDate name="Agent_OutWorkDate" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">����������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_CautionerName name="Agent_CautionerName" readonly></td>
                <td class="title">�������Ա�</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_CautionerSex name="Agent_CautionerSex" readonly></td>
                <td class="title">���������֤</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_CautionerID name="Agent_CautionerID" readonly></td>
            </tr>
        </table>
    </div>
    <!-- ��ϵ��ʽ�۵�չ�� -->
    <table>
        <tr>
            <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divAgentLinkInfo)"></td>
            <td class="titleImg">��ϵ��ʽ</td>
        </tr>
    </table>
    <!-- ��ϵ��ʽչ�ֱ�� -->
    <div id="divAgentLinkInfo" class=maxbox style="display:''">
        <table class="common">
            <tr class="common">
                <td class="title">ͨѶ��ַ</td>
                <td class="input" colspan="3"><input type="text" class= "common3" id=Agent_PostalAddress name="Agent_PostalAddress" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_ZipCode name="Agent_ZipCode" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">�̶��绰</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Phone name="Agent_Phone" readonly></td>
                <td class="title">�ƶ��绰</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_Mobile name="Agent_Mobile" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_EMail name="Agent_EMail" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">������ϵ��</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_EmergentLink name="Agent_EmergentLink" readonly></td>
                <td class="title">������ϵ�˵绰</td>
                <td class="input"><input type="text" class="readonly wid" id=Agent_EmergentPhone name="Agent_EmergentPhone" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
    </div>
    <!-- ������Ϣ�۵�չ�� -->
    <table>
        <tr>
            <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divAgentUperInfo)"></td>
            <td class="titleImg">������Ϣ</td>
        </tr>
    </table>
    <!-- ������Ϣչ�ֱ�� -->
    <div id="divAgentUperInfo" class=maxbox style="display:''">
        <table class="common">
            <tr class="common">
                <td class="title">�����˱���</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_AgentCode name="Higher_AgentCode" readonly></td>
                <td class="title">������״̬</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_AgentState name="Higher_AgentState" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
            <tr class="common">
                <td class="title">����������</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Name name="Higher_Name" readonly></td>
                <td class="title">�Ա�</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Sex name="Higher_Sex" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Birthday name="Higher_Birthday" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">����</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Nationality name="Higher_Nationality" readonly></td>
                <td class="title">����</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_NativePlace name="Higher_NativePlace" readonly></td>
                <td class="title">����״��</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Marriage name="Higher_Marriage" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">ͨѶ��ַ</td>
                <td class="input" colspan="3"><input type="text" class= "common3" name="Higher_PostalAddress" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_ZipCode name="Higher_ZipCode" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">�̶��绰</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Phone name="Higher_Phone" readonly></td>
                <td class="title">�ƶ��绰</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_Mobile name="Higher_Mobile" readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_EMail name="Higher_EMail" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">������ϵ��</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_EmergentLink name="Higher_EmergentLink" readonly></td>
                <td class="title">������ϵ�˵绰</td>
                <td class="input"><input type="text" class="readonly wid" id=Higher_EmergentPhone name="Higher_EmergentPhone" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
    </div>

    <!-- ���ýű���ʼ��ѯ -->

    <script language="JavaScript">

        var sJsContNo, sJvContNo, sContNo;
        try
        {
            sJsContNo = top.opener.document.getElementsByName("ContNo")[0].value;
            sJvContNo = "<%=request.getParameter("ContNo")%>";
            sContNo = (sJsContNo != null && trim(sJsContNo) != "") ? trim(sJsContNo) : trim(sJvContNo);
        }
        catch (ex) {}

        checkQueryConditions();  //�ܺ���, ִ�д�������Ϣ��ѯ

        //����ѯ����
        function checkQueryConditions()
        {
            if (sContNo == null || trim(sContNo) == "")
            {
                alert("���棺�޷���ȡ��ͬ�š���ѯ��������Ϣʧ�ܣ� ");
                return;
            }
            else
            {
                queryAgentSelfInfo();
                queryAgentUperInfo();
            }
        }

        //��ѯ������������Ϣ
        function queryAgentSelfInfo()
        {
            var QuerySQL, arrResult;
//             QuerySQL = "select a.AgentCode, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'agentstate' "
//                      +            "and Code = a.AgentState), "
//                      +        "a.Name, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'sex' "
//                      +            "and Code = a.Sex), "
//                      +        "a.Birthday, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'nationality' "
//                      +            "and Code = a.Nationality), "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'nativeplacebak' "
//                      +            "and Code = a.NativePlace), "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'marriage' "
//                      +            "and Code = a.Marriage), "
//                      +        "a.ManageCom, "
//                      +        "(select Name "
//                      +           "from LDCom "
//                      +          "where trim(ComCode) = a.ManageCom), "
//                      +        "a.AgentGroup, "
//                      +        "GetBranchGlobalName(a.AgentGroup), "
//                      +        "a.IDType, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'idtype' "
//                      +            "and Code = a.IDType), "
//                      +        "a.OldCom, "
//                      +        "a.IDNo, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'agentkind' "
//                      +            "and Code = a.AgentKind), "
//                      +        "(case SaleQuaf when 'Y' then '��' when 'N' then '��' else 'δ֪' end), "
//                      +        "a.QuafNo, "
//                      +        "a.EmployDate, "
//                      +        "a.InDueFormDate, "
//                      +        "a.OutWorkDate, "
//                      +        "a.CautionerName, "
//                      +        "a.CautionerSex, "
//                      +        "a.CautionerID, "
//                      +        "a.PostalAddress, "
//                      +        "a.ZipCode, "
//                      +        "a.Phone, "
//                      +        "a.Mobile, "
//                      +        "a.EMail "
//                      +   "from LAAgent a "
//                      +  "where a.AgentCode = "
//                      +        "(select trim(AgentCode) "
//                      +           "from LCCont "
//                      +          "where ContNo = '" + sContNo + "') ";
            
            var sqlid1="EdorAgentQueryInclude1";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("bq.EdorAgentQueryIncludeSql");
        	mySql1.setSqlId(sqlid1);
        	 mySql1.addSubPara(sContNo);//ָ���������
            QuerySQL = mySql1.getString();
            
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("���棺��ѯ��������Ϣ�����쳣�� ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("Agent_AgentCode")[0].value      = arrResult[0][0];
                    document.getElementsByName("Agent_AgentState")[0].value     = arrResult[0][1];
                    document.getElementsByName("Agent_Name")[0].value           = arrResult[0][2];
                    document.getElementsByName("Agent_Sex")[0].value            = arrResult[0][3];
                    document.getElementsByName("Agent_Birthday")[0].value       = arrResult[0][4];
                    document.getElementsByName("Agent_Nationality")[0].value    = arrResult[0][5];
                    document.getElementsByName("Agent_NativePlace")[0].value    = arrResult[0][6];
                    document.getElementsByName("Agent_Marriage")[0].value       = arrResult[0][7];
                    document.getElementsByName("Agent_ManageCom")[0].value      = arrResult[0][8];
                    document.getElementsByName("Agent_ManageComName")[0].value  = arrResult[0][9];
                    document.getElementsByName("Agent_AgentGroup")[0].value     = arrResult[0][10];
                    document.getElementsByName("Agent_AgentGroupName")[0].value = arrResult[0][11];
                    document.getElementsByName("Agent_IDNoType")[0].value       = arrResult[0][12];
                    document.getElementsByName("Agent_IDNoTypeName")[0].value   = arrResult[0][13];
                    document.getElementsByName("Agent_OldCom")[0].value         = arrResult[0][14];
                    document.getElementsByName("Agent_IDNo")[0].value           = arrResult[0][15];
                    document.getElementsByName("Agent_AgentKind")[0].value      = arrResult[0][16];
                    document.getElementsByName("Agent_SaleQuaf")[0].value       = arrResult[0][17];
                    document.getElementsByName("Agent_QuafNo")[0].value         = arrResult[0][18];
                    document.getElementsByName("Agent_EmployDate")[0].value     = arrResult[0][19];
                    document.getElementsByName("Agent_InDueFormDate")[0].value  = arrResult[0][20];
                    document.getElementsByName("Agent_OutWorkDate")[0].value    = arrResult[0][21];
                    document.getElementsByName("Agent_CautionerName")[0].value  = arrResult[0][22];
                    document.getElementsByName("Agent_CautionerSex")[0].value   = arrResult[0][23];
                    document.getElementsByName("Agent_CautionerID")[0].value    = arrResult[0][24];
                    document.getElementsByName("Agent_PostalAddress")[0].value  = arrResult[0][25];
                    document.getElementsByName("Agent_ZipCode")[0].value        = arrResult[0][26];
                    document.getElementsByName("Agent_Phone")[0].value          = arrResult[0][27];
                    document.getElementsByName("Agent_Mobile")[0].value         = arrResult[0][28];
                    document.getElementsByName("Agent_EMail")[0].value          = arrResult[0][29];
                    document.getElementsByName("Agent_EmergentLink")[0].value   = arrResult[0][30];
                    document.getElementsByName("Agent_EmergentPhone")[0].value  = arrResult[0][31];
                }
                catch (ex)
                {
                    alert("���棺��ʾ��������Ϣ�����쳣�� ");
                    return;
                }
            } //arrResult != null
        }

        //��ѯ������������Ϣ
        function queryAgentUperInfo()
        {
            var QuerySQL, arrResult;
//             QuerySQL = "select a.AgentCode, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'agentstate' "
//                      +            "and Code = a.AgentState), "
//                      +        "a.Name, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'sex' "
//                      +            "and Code = a.Sex), "
//                      +        "a.Birthday, "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'nationality' "
//                      +            "and Code = a.Nationality), "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'nativeplacebak' "
//                      +            "and Code = a.NativePlace), "
//                      +        "(select CodeName "
//                      +           "from LDCode "
//                      +          "where 1 = 1 "
//                      +            "and CodeType = 'marriage' "
//                      +            "and Code = a.Marriage), "
//                      +        "a.PostalAddress, "
//                      +        "a.ZipCode, "
//                      +        "a.Phone, "
//                      +        "a.Mobile, "
//                      +        "a.EMail "

//                      +   "from LAAgent a "
//                      +  "where a.AgentCode = "
//                      +        "(select UpAgent "
//                      +          "from LATree "
//                      +         "where AgentCode = "
//                      +              "(select trim(AgentCode) "
//                      +                 "from LCCont "
//                      +                "where ContNo = '" + sContNo + "') "
//                      +        ") ";
            
            var sqlid2="EdorAgentQueryInclude2";
        	var mySql2=new SqlClass();
        	mySql2.setResourceName("bq.EdorAgentQueryIncludeSql");
        	mySql2.setSqlId(sqlid2);
            mySql2.addSubPara(sContNo);//ָ���������
            QuerySQL = mySql2.getString();
            
            try
            {
                arrResult = easyExecSql(QuerySQL, 1, 0);
            }
            catch (ex)
            {
                alert("���棺��ѯ������������Ϣ�����쳣�� ");
                return;
            }
            if (arrResult != null)
            {
                try
                {
                    document.getElementsByName("Higher_AgentCode")[0].value      = arrResult[0][0];
                    document.getElementsByName("Higher_AgentState")[0].value     = arrResult[0][1];
                    document.getElementsByName("Higher_Name")[0].value           = arrResult[0][2];
                    document.getElementsByName("Higher_Sex")[0].value            = arrResult[0][3];
                    document.getElementsByName("Higher_Birthday")[0].value       = arrResult[0][4];
                    document.getElementsByName("Higher_Nationality")[0].value    = arrResult[0][5];
                    document.getElementsByName("Higher_NativePlace")[0].value    = arrResult[0][6];
                    document.getElementsByName("Higher_Marriage")[0].value       = arrResult[0][7];
                    document.getElementsByName("Higher_PostalAddress")[0].value  = arrResult[0][8];
                    document.getElementsByName("Higher_ZipCode")[0].value        = arrResult[0][9];
                    document.getElementsByName("Higher_Phone")[0].value          = arrResult[0][10];
                    document.getElementsByName("Higher_Mobile")[0].value         = arrResult[0][11];
                    document.getElementsByName("Higher_EMail")[0].value          = arrResult[0][12];
                    document.getElementsByName("Higher_EmergentLink")[0].value   = arrResult[0][13];
                    document.getElementsByName("Higher_EmergentPhone")[0].value  = arrResult[0][14];
                }
                catch (ex)
                {
                    alert("���棺��ʾ������������Ϣ�����쳣�� ");
                    return;
                }
            } //arrResult != null
        }

    </script>

    <!-- ���ñ�ȫ��������Ϣ������ҳ�� : ���� -->

