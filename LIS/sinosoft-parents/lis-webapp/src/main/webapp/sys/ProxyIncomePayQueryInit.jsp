<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-11-03, 2005-12-03, 2006-02-08
 * @direction: ���մ�����ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var ProxyGrid;    //ȫ�ֱ���, ���մ�������

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initProxyGrid();
                queryProxyGrid();
            }
            catch (ex)
            {
                alert("�� ProxyIncomePayQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("OtherNo")[0].value = "<%=request.getParameter("OtherNo")%>";
            }
            catch (ex)
            {
                alert("�� ProxyIncomePayQueryInit.jsp --> InitInpBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ���մ������� MultiLine �ĳ�ʼ��
         */
        function initProxyGrid()
        {
            var iArray = new Array();                           //������, ���ظ� MultiLine ���

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 10;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 1��ʾ����; 0��ʾ������

                iArray[1] = new Array();
                iArray[1][0] = "��ȫ�����";
                iArray[1][1] = "90px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "��ȫ��Ŀ";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 2;
                iArray[2][4] = "PEdorType";

                iArray[3] = new Array();
                iArray[3][0] = "���˷ѽ��";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 7;
                iArray[3][21] = 3;
                iArray[3][23] = "0";

                iArray[4] = new Array();
                iArray[4][0] = "��������";
                iArray[4][1] = "60px";
                iArray[4][2] = 100;
                iArray[4][3] = 8;
                iArray[4][21] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 2;
                iArray[5][4] = "Bank";

                iArray[6] = new Array();
                iArray[6][0] = "�˺�";
                iArray[6][1] = "110px";
                iArray[6][2] = 150;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "�˻���";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "��������";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 8;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "������";
                iArray[9][1] = "50px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 2;

                iArray[10] = new Array();
                iArray[10][0] = "����ɹ�ԭ��";
                iArray[10][1] = "80px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                
                iArray[11]=new Array();
								iArray[11][0]="����";
								iArray[11][1]="60px";
								iArray[11][2]=100;
								iArray[11][3]=2;
								iArray[11][4]="currency";
            }
            catch (ex)
            {
                alert("�� ProxyIncomePayQueryInit.jsp --> initProxyGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                ProxyGrid = new MulLineEnter("fm", "ProxyGrid");
                ProxyGrid.mulLineCount = 1;
                ProxyGrid.displayTitle = 1;
                ProxyGrid.locked = 1;
                ProxyGrid.hiddenPlus = 1;
                ProxyGrid.hiddenSubtraction = 1;
                ProxyGrid.canChk = 0;
                ProxyGrid.canSel = 0;
                ProxyGrid.chkBoxEventFuncName = ""
                ProxyGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                ProxyGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� ProxyIncomePayQueryInit.jsp --> initProxyGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

