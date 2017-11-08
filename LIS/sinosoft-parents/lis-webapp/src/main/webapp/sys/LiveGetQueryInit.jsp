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
 * @date     : 2005-12-02, 2006-02-22, 2006-05-12, 2006-11-14
 * @direction: �ۺϲ�ѯ������ȡ��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var LiveGetGrid;      //ȫ�ֱ���, ������ȡ����
        var BankProxyGrid;    //ȫ�ֱ���, ���մ�������

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initLiveGetGrid();
                initBankProxyGrid();
                queryBankInfo();
                queryLiveGetGrid();
            }
            catch (ex)
            {
                alert("�� LiveGetQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("ContNo")[0].value = "<%=request.getParameter("ContNo")%>";
                document.getElementsByName("PolNo")[0].value = "<%=request.getParameter("PolNo")%>";
                document.getElementsByName("RiskCode")[0].value = "<%=request.getParameter("RiskCode")%>";
            }
            catch (ex)
            {
                alert("�� LiveGetQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȡ���� MultiLine �ĳ�ʼ��
         */
        function initLiveGetGrid()
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
                iArray[1][0] = "ʵ������";
                iArray[1][1] = "105px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "���α���";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "�������α���";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "������������";
                iArray[4][1] = "110px";
                iArray[4][2] = 150;
                iArray[4][3] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "�������";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 7;
                iArray[5][21] = 3;
                iArray[5][23] = "0";

                iArray[6] = new Array();
                iArray[6][0] = "Ӧ������";
                iArray[6][1] = "60px";
                iArray[6][2] = 100;
                iArray[6][3] = 8;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "ҵ���������";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "����������";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 8;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "������������";
                iArray[9][1] = "60px";
                iArray[9][2] = 100;
                iArray[9][3] = 8;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "�Ƿ�����";
                iArray[10][1] = "45px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][21] = 2;
                
                iArray[11]=new Array();
								iArray[11][0]="����";
								iArray[11][1]="60px";
								iArray[11][2]=100;
								iArray[11][3]=2;
								iArray[11][4]="currency";
            }
            catch (ex)
            {
                alert("�� LiveGetQueryInit.jsp --> initLiveGetGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                LiveGetGrid = new MulLineEnter("fm", "LiveGetGrid");
                LiveGetGrid.mulLineCount = 0;
                LiveGetGrid.displayTitle = 1;
                LiveGetGrid.locked = 1;
                LiveGetGrid.hiddenPlus = 1;
                LiveGetGrid.hiddenSubtraction = 1;
                LiveGetGrid.canChk = 0;
                LiveGetGrid.canSel = 1;
                LiveGetGrid.chkBoxEventFuncName = "";
                LiveGetGrid.selBoxEventFuncName = "queryBankProxyGrid";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                LiveGetGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� LiveGetQueryInit.jsp --> initLiveGetGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ���մ������� MultiLine �ĳ�ʼ��
         */
        function initBankProxyGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 1��ʾ����; 0��ʾ������

                iArray[1] = new Array();
                iArray[1][0] = "��ȡ֪ͨ���";
                iArray[1][1] = "110px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "��������";
                iArray[2][1] = "70px";
                iArray[2][2] = 200;
                iArray[2][3] = 2;
                iArray[2][4] = "Bank";

                iArray[3] = new Array();
                iArray[3][0] = "�����˺�";
                iArray[3][1] = "120px";
                iArray[3][2] = 150;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�˻���";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 8;
                iArray[5][21] = 3;

                iArray[6] = new Array();
                iArray[6][0] = "������";
                iArray[6][1] = "65px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 2;

                iArray[7] = new Array();
                iArray[7][0] = "����ɹ�ԭ��";
                iArray[7][1] = "130px";
                iArray[7][2] = 150;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("�� LiveGetQueryInit.jsp --> initBankProxyGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                BankProxyGrid = new MulLineEnter("fm", "BankProxyGrid");
                BankProxyGrid.mulLineCount = 0;
                BankProxyGrid.displayTitle = 1;
                BankProxyGrid.locked = 1;
                BankProxyGrid.hiddenPlus = 1;
                BankProxyGrid.hiddenSubtraction = 1;
                BankProxyGrid.canChk = 0;
                BankProxyGrid.canSel = 0;
                BankProxyGrid.chkBoxEventFuncName = "";
                BankProxyGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                BankProxyGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� LiveGetQueryInit.jsp --> initBankProxyGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

