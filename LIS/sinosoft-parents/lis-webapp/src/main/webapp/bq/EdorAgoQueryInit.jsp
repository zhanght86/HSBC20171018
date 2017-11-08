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
 * @date     : 2005-12-14, 2006-03-21, 2006-07-13, 2006-08-19, 2006-10-25
 * @direction: ��ȫ�˹��˱�Ͷ(��)���˼�����ȫ��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var EdorGrid;        //ȫ�ֱ���, ������ȫ������Ϣ
        var PrintGrid;       //ȫ�ֱ���, ������ȫ�����˱�֪ͨ����Ϣ
        var EdorItemGrid;    //ȫ�ֱ���, ������ȫ������Ŀ��Ϣ
        var PremGrid;        //ȫ�ֱ���, ������ȫ������Ŀ�ӷ���Ϣ
        var SpecGrid;        //ȫ�ֱ���, ������ȫ������Ŀ�ر�Լ����Ϣ

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initEdorGrid();
                initPrintGrid();
                initEdorItemGrid();
                initPremGrid();
                initSpecGrid();
                queryCustomerInfo();
                queryCustomerEdor();
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("CustomerNo")[0].value = "<%=request.getParameter("CustomerNo")%>";
                document.getElementsByName("CurEdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȫ������Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initEdorGrid()
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
                iArray[1][1] = "110px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "110px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "Ͷ��������";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "������������";
                iArray[4][1] = "90px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��Ч����";
                iArray[5][1] = "90px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "�˱�����";
                iArray[6][1] = "100px";
                iArray[6][2] = 120;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "����״̬";
                iArray[7][1] = "100px";
                iArray[7][2] = 120;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "��ȫ�����";
                iArray[8][1] = "0px";
                iArray[8][2] = 0;
                iArray[8][3] = 3;
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initEdorGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                EdorGrid = new MulLineEnter("fm", "EdorGrid");
                EdorGrid.mulLineCount = 0;
                EdorGrid.displayTitle = 1;
                EdorGrid.locked = 1;
                EdorGrid.hiddenPlus = 1;
                EdorGrid.hiddenSubtraction = 1;
                EdorGrid.canChk = 0;
                EdorGrid.canSel = 1;
                EdorGrid.chkBoxEventFuncName = "";
                EdorGrid.selBoxEventFuncName = "queryUWPrintEdorItem";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                EdorGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> EdorGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȫ�����˱�֪ͨ���ѯ MultiLine �ĳ�ʼ��
         */
        function initPrintGrid()
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
                iArray[1][0] = "��ӡ��ˮ��";
                iArray[1][1] = "110px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "110px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "֪ͨ������";
                iArray[3][1] = "160px";
                iArray[3][2] = 200;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�����������";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "�����������";
                iArray[5][1] = "130px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "��ӡ������";
                iArray[6][1] = "0px";
                iArray[6][2] = 0;
                iArray[6][3] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "��������";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initPrintGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                PrintGrid = new MulLineEnter("fm", "PrintGrid");
                PrintGrid.mulLineCount = 0;
                PrintGrid.displayTitle = 1;
                PrintGrid.locked = 1;
                PrintGrid.hiddenPlus = 1;
                PrintGrid.hiddenSubtraction = 1;
                PrintGrid.canChk = 0;
                PrintGrid.canSel = 1;
                PrintGrid.chkBoxEventFuncName = "";
                PrintGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                PrintGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initPrintGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȫ������Ŀ��Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initEdorItemGrid()
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
                iArray[1][1] = "105px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "��ȫ��Ŀ";
                iArray[3][1] = "80px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�ͻ���";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "���ֺ�";
                iArray[5][1] = "110px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "������������";
                iArray[6][1] = "65px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "��Ч����";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "�˱�����";
                iArray[8][1] = "65px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "����״̬";
                iArray[9][1] = "0px";
                iArray[9][2] = 0;
                iArray[9][3] = 3;
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initEdorItemGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
                EdorItemGrid.mulLineCount = 0;
                EdorItemGrid.displayTitle = 1;
                EdorItemGrid.locked = 1;
                EdorItemGrid.hiddenPlus = 1;
                EdorItemGrid.hiddenSubtraction = 1;
                EdorItemGrid.canChk = 0;
                EdorItemGrid.canSel = 1;
                EdorItemGrid.chkBoxEventFuncName = "";
                EdorItemGrid.selBoxEventFuncName = "queryEdorPremSpec";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                EdorItemGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initEdorItemGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȫ������Ŀ�ӷ���Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initPremGrid()
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
                iArray[1][0] = "���ֺ�";
                iArray[1][1] = "85px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "���α���";
                iArray[2][1] = "60px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "�ӷ�����";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�ӷ�����";
                iArray[4][1] = "60px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "�ڶ��������˼ӷ�����";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "�ӷѶ���";
                iArray[6][1] = "55px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "�ӷѽ��";
                iArray[7][1] = "55px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "�ӷ�����";
                iArray[8][1] = "55px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "��������";
                iArray[9][1] = "55px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "�ӷ�ֹ��";
                iArray[10][1] = "55px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][21] = 3;
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initPremGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                PremGrid = new MulLineEnter("fm", "PremGrid");
                PremGrid.mulLineCount = 1;
                PremGrid.displayTitle = 1;
                PremGrid.locked = 1;
                PremGrid.hiddenPlus = 1;
                PremGrid.hiddenSubtraction = 1;
                PremGrid.canChk = 0;
                PremGrid.canSel = 0;
                PremGrid.chkBoxEventFuncName = "";
                PremGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                PremGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initPremGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȫ������Ŀ�ر�Լ����Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initSpecGrid()
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
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "���ֺ�";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "��Լ����";
                iArray[4][1] = "250px";
                iArray[4][2] = 300;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initSpecGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                SpecGrid = new MulLineEnter("fm", "SpecGrid");
                SpecGrid.mulLineCount = 1;
                SpecGrid.displayTitle = 1;
                SpecGrid.locked = 1;
                SpecGrid.hiddenPlus = 1;
                SpecGrid.hiddenSubtraction = 1;
                SpecGrid.canChk = 0;
                SpecGrid.canSel = 0;
                SpecGrid.chkBoxEventFuncName = "";
                SpecGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                SpecGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorAgoQueryInit.jsp --> initSpecGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

