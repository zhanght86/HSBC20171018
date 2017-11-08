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
 * @direction: �ŵ���ȫ�˹��˱��ֵ����ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sOperator = tGlobalInput.Operator;
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>


    <script language="JavaScript">

        var InsuredGrid;    //ȫ�ֱ���, �����˶���
        var PolGrid;        //ȫ�ֱ���, �ֵ����ֶ���

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
                initInsuredGrid();
                initPolGrid();
                queryInsuredGrid();
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("MissionID")[0].value = "<%=request.getParameter("MissionID")%>";
                document.getElementsByName("SubMissionID")[0].value = "<%=request.getParameter("SubMissionID")%>";
                document.getElementsByName("ActivityStatus")[0].value = "<%=request.getParameter("ActivityStatus")%>";
                document.getElementsByName("ActivityID")[0].value = "0000008006";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
                document.getElementsByName("GrpContNo")[0].value = "<%=request.getParameter("GrpContNo")%>";
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����˶��в�ѯ MultiLine �ĳ�ʼ��
         */
        function initInsuredGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

                iArray[1] = new Array();
                iArray[1][0] = "������";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "��������";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "�ֵ���";
                iArray[3][1] = "90px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�����˺�";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "����������";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "�Ա�";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 2;

                iArray[7] = new Array();
                iArray[7][0] = "��������";
                iArray[7][1] = "70px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "֤������";
                iArray[8][1] = "90px";
                iArray[8][2] = 150;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "֤������";
                iArray[9][1] = "120px";
                iArray[9][2] = 150;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "�˱�����";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 2;
                iArray[10][4] = "gedorcontuwstate";
                iArray[10][18] = 92;
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initInsuredGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                InsuredGrid = new MulLineEnter("fm", "InsuredGrid");
                InsuredGrid.mulLineCount = 1;
                InsuredGrid.displayTitle = 1;
                InsuredGrid.locked = 1;
                InsuredGrid.hiddenPlus = 1;
                InsuredGrid.hiddenSubtraction = 1;
                InsuredGrid.canChk = 0;
                InsuredGrid.canSel = 1;
                InsuredGrid.chkBoxEventFuncName = "";
                InsuredGrid.selBoxEventFuncName = "queryInsuredAndPol";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                InsuredGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initInsuredGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �ֵ����ֶ��в�ѯ MultiLine �ĳ�ʼ��
         */
        function initPolGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

                iArray[1] = new Array();
                iArray[1][0] = "������";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "��������";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "�������ֺ�";
                iArray[3][1] = "0px";
                iArray[3][2] = 0;
                iArray[3][3] = 3;

                iArray[4] = new Array();
                iArray[4][0] = "���ִ���";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "160px";
                iArray[5][2] = 200;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "��������";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "Ͷ������";
                iArray[7][1] = "70px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "��׼����";
                iArray[8][1] = "80px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "�����ӷ�";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "ְҵ�ӷ�";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][21] = 3;

                iArray[11] = new Array();
                iArray[11][0] = "�˱�����";
                iArray[11][1] = "70px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][4] = "gedorpoluwstate";
                iArray[11][18] = 92;
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initPolGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                PolGrid = new MulLineEnter("fm", "PolGrid");
                PolGrid.mulLineCount = 1;
                PolGrid.displayTitle = 1;
                PolGrid.locked = 1;
                PolGrid.hiddenPlus = 1;
                PolGrid.hiddenSubtraction = 1;
                PolGrid.canChk = 0;
                PolGrid.canSel = 1;
                PolGrid.chkBoxEventFuncName = "";
                PolGrid.selBoxEventFuncName = "initPolInfo";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                PolGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� GEdorManuUWInsuredInit.jsp --> initPolGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

