<%
/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱����������ʼ��
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

        var AllGrid;     //ȫ�ֱ���, ����������
        var SelfGrid;    //ȫ�ֱ���, �ҵ��������

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initAllGrid();
                initSelfGrid();
                querySelfGrid();
            }
            catch (ex)
            {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("ActivityID")[0].value = "0000008005";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ���������в�ѯ MultiLine �ĳ�ʼ��
         */
        function initAllGrid()
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
                iArray[1][0] = "�����������";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "�������������";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "��ȫ�����";
                iArray[3][1] = "110px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�ͻ�/������";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "������";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "�������";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][4] = "Station";
                iArray[7][18] = 235;

                iArray[8] = new Array();
                iArray[8][0] = "��ǰ�״̬";
                iArray[8][1] = "0px";
                iArray[8][2] = 0;
                iArray[8][3] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "�˱�״̬";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "¼��Ա";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "¼������";
                iArray[11][1] = "70px";
                iArray[11][2] = 100;
                iArray[11][3] = 0;
                iArray[11][21] = 3;
            }
            catch (ex)
            {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initAllGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                AllGrid = new MulLineEnter("document", "AllGrid");
                AllGrid.mulLineCount = 5;
                AllGrid.displayTitle = 1;
                AllGrid.locked = 1;
                AllGrid.hiddenPlus = 1;
                AllGrid.hiddenSubtraction = 1;
                AllGrid.canChk = 0;
                AllGrid.canSel = 1;
                AllGrid.chkBoxEventFuncName = "";
                AllGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                AllGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initAllGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �ҵ�������в�ѯ MultiLine �ĳ�ʼ��
         */
        function initSelfGrid()
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
                iArray[1][0] = "�����������";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "�������������";
                iArray[2][1] = "0px";
                iArray[2][2] = 0;
                iArray[2][3] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "��ȫ�����";
                iArray[3][1] = "110px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�ͻ�/������";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "������";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "�������";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][4] = "Station";
                iArray[7][18] = 235;

                iArray[8] = new Array();
                iArray[8][0] = "��ǰ�״̬";
                iArray[8][1] = "0px";
                iArray[8][2] = 0;
                iArray[8][3] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "�˱�״̬";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "¼��Ա";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "¼������";
                iArray[11][1] = "70px";
                iArray[11][2] = 100;
                iArray[11][3] = 0;
                iArray[11][21] = 3;
            }
            catch (ex)
            {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initSelfGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                SelfGrid = new MulLineEnter("document", "SelfGrid");
                SelfGrid.mulLineCount = 0;
                SelfGrid.displayTitle = 1;
                SelfGrid.locked = 1;
                SelfGrid.hiddenPlus = 1;
                SelfGrid.hiddenSubtraction = 1;
                SelfGrid.canChk = 0;
                SelfGrid.canSel = 1;
                SelfGrid.chkBoxEventFuncName = "";
                SelfGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                SelfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� WFGrpEdorManuUWApplyInit.jsp --> initSelfGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

