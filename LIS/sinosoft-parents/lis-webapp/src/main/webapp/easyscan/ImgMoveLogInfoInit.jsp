<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-26
 * @direction: Ӱ��Ǩ����־��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        String sOperator = tGlobalInput.Operator;
        tGlobalInput = null;
    %>

    <script language="JavaScript">

        var TaskLogGrid;      //ȫ�ֱ���, Ǩ��������Ϣ
        var MoveErrorGrid;    //ȫ�ֱ���, ���δ�����Ϣ

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
                initTaskLogGrid();
                initMoveErrorGrid();
                //queryTaskLogGrid();
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("OldManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("NewManageCom")[0].value = "86";
                showOneCodeName("Station", "OldManageCom", "OldManageComName");
                showOneCodeName("Station", "NewManageCom", "NewManageComName");
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * Ǩ��������Ϣ MultiLine �ĳ�ʼ��
         */
        function initTaskLogGrid()
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
                iArray[1][0] = "Ǩ�����κ�";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "Ǩ������";
                iArray[2][1] = "90px";
                iArray[2][2] = 100;
                iArray[2][3] = 2;
                iArray[2][4] = "Station";

                iArray[3] = new Array();
                iArray[3][0] = "Ǩ�����";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "Station";

                iArray[4] = new Array();
                iArray[4][0] = "ɨ������";
                iArray[4][1] = "90px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][21] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "ɨ��ֹ��";
                iArray[5][1] = "90px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
                iArray[5][21] = 3;

                iArray[6] = new Array();
                iArray[6][0] = "��Ǩ������";
                iArray[6][1] = "90px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "�ɹ�Ǩ����";
                iArray[7][1] = "90px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "��������";
                iArray[8][1] = "60px";
                iArray[8][2] = 60;
                iArray[8][3] = 0;
                iArray[8][21] = 3;
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initTaskLogGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                TaskLogGrid = new MulLineEnter("fm", "TaskLogGrid");
                TaskLogGrid.mulLineCount = 3;
                TaskLogGrid.displayTitle = 1;
                TaskLogGrid.locked = 1;
                TaskLogGrid.hiddenPlus = 1;
                TaskLogGrid.hiddenSubtraction = 1;
                TaskLogGrid.canChk = 0;
                TaskLogGrid.canSel = 1;
                TaskLogGrid.chkBoxEventFuncName = "";
                TaskLogGrid.selBoxEventFuncName = "queryMoveErrorGrid";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                TaskLogGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initTaskLogGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ���δ�����Ϣ MultiLine �ĳ�ʼ��
         */
        function initMoveErrorGrid()
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
                iArray[1][0] = "Ǩ�����κ�";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "Ӱ��ɨ����";
                iArray[2][1] = "90px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "Ӱ��ɨ������";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;
                iArray[3][21] = 3;

                iArray[4] = new Array();
                iArray[4][0] = "Ӱ���ļ�·��";
                iArray[4][1] = "370px";
                iArray[4][2] = 500;
                iArray[4][3] = 0;
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initMoveErrorGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                MoveErrorGrid = new MulLineEnter("fm", "MoveErrorGrid");
                MoveErrorGrid.mulLineCount = 3;
                MoveErrorGrid.displayTitle = 1;
                MoveErrorGrid.locked = 1;
                MoveErrorGrid.hiddenPlus = 1;
                MoveErrorGrid.hiddenSubtraction = 1;
                MoveErrorGrid.canChk = 0;
                MoveErrorGrid.canSel = 1;
                MoveErrorGrid.chkBoxEventFuncName = "";
                MoveErrorGrid.selBoxEventFuncName = "initDocID";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                MoveErrorGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� ImgMoveLogInfoInit.jsp --> initMoveErrorGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

