<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-13, 2006-11-01, 2006-11-22
 * @direction: ��ȫ�����˱����ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var CustomerGrid;    //ȫ�ֱ���, �����ͻ����
        var OldBnfGrid;      //ȫ�ֱ���, ԭ�����˱��
        var NewBnfGrid;      //ȫ�ֱ���, �������˱��

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                EdorQuery();
                initHiddenArea();
                initInputBox();
                initCustomerGrid();
                initOldBnfGrid();
                initNewBnfGrid();
                queryPolInfo();
                queryCustomerGrid();
                queryOldBnfGrid();
                checkEdorState();
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value;
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                //�ı���
                document.getElementsByName("EdorAcceptNo")[0].value = top.opener.document.getElementsByName("EdorAcceptNo")[0].value;
                document.getElementsByName("EdorNo")[0].value = top.opener.document.getElementsByName("EdorNo")[0].value;
                document.getElementsByName("EdorType")[0].value = top.opener.document.getElementsByName("EdorType")[0].value;
                document.getElementsByName("ContNo")[0].value = top.opener.document.getElementsByName("ContNo")[0].value;
                document.getElementsByName("PolNo")[0].value = top.opener.document.getElementsByName("PolNo")[0].value;
                document.getElementsByName("InsuredNo")[0].value = top.opener.document.getElementsByName("InsuredNo")[0].value;
                document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
                document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ͻ���Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initCustomerGrid()
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
                iArray[1][0] = "��ɫ";
                iArray[1][1] = "100px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "�ͻ���";
                iArray[2][1] = "100px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "����";
                iArray[3][1] = "100px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�Ա�";
                iArray[4][1] = "100px";
                iArray[4][2] = 100;
                iArray[4][3] = 2;
                iArray[4][4] = "Sex";
                
                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "100px";
                iArray[5][2] = 100;
                iArray[5][3] = 8;
                //iArray[5][4] = "Sex";

                iArray[6] = new Array();
                iArray[6][0] = "֤������";
                iArray[6][1] = "100px";
                iArray[6][2] = 150;
                iArray[6][3] = 2;
                iArray[6][4] = "IDType";

                iArray[7] = new Array();
                iArray[7][0] = "֤������";
                iArray[7][1] = "150px";
                iArray[7][2] = 200;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initCustomerGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
                CustomerGrid.mulLineCount = 0;
                CustomerGrid.displayTitle = 1;
                CustomerGrid.locked = 1;
                CustomerGrid.hiddenPlus = 1;
                CustomerGrid.hiddenSubtraction = 1;
                CustomerGrid.canChk = 0;
                CustomerGrid.canSel = 0;
                CustomerGrid.chkBoxEventFuncName = "";
                CustomerGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                CustomerGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initCustomerGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ԭ��������Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initOldBnfGrid()
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
                iArray[1][0] = "�����˺�";
                iArray[1][1] = "55px";
                iArray[1][2] = 100;
                iArray[1][3] = 2;
                iArray[1][10] = "InsuredCodeList";
                iArray[1][11] = getInsuredCodeList();
                iArray[1][12] = "1|2";
                iArray[1][13] = "0|1";
                iArray[1][19] = 1;

                iArray[2] = new Array();
                iArray[2][0] = "����������";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "���������";
                iArray[3][1] = "45px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "NewBnfType";

                iArray[4] = new Array();
                iArray[4][0] = "����������";
                iArray[4][1] = "50px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��";
                iArray[5][1] = "0px";
                iArray[5][2] = 0;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "֤������";
                iArray[6][1] = "40px";
                iArray[6][2] = 100;
                iArray[6][3] = 2;
                iArray[6][4] = "IDType";

                iArray[7] = new Array();
                iArray[7][0] = "֤������";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "�뱻���˹�ϵ";
                iArray[8][1] = "50px";
                iArray[8][2] = 100;
                iArray[8][3] = 2;
                iArray[8][4] = "Relation";

                iArray[9] = new Array();
                iArray[9][0] = "����˳��";
                iArray[9][1] = "40px";
                iArray[9][2] = 100;
                iArray[9][3] = 2;
                iArray[9][4] = "BnfOrder";

                iArray[10] = new Array();
                iArray[10][0] = "����ݶ�";
                iArray[10][1] = "40px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "����";
                iArray[11][1] = "40px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][10] = "CustomerType";
                iArray[11][11] = getCustomerType();
                iArray[11][19] = 1;
                
                iArray[12] = new Array();
                iArray[12][0] = "�Ա�";
                iArray[12][1] = "80px";
                iArray[12][2] = 100;
                iArray[12][3] = 2;
                iArray[12][4] = "Sex";
                
                iArray[13] = new Array();
                iArray[13][0] = "��������";
                iArray[13][1] = "80px";
                iArray[13][2] = 100;
                iArray[13][3] = 8;
                
                iArray[14] = new Array();
                iArray[14][0] = "סַ";
                iArray[14][1] = "80px";
                iArray[14][2] = 100;
                iArray[14][3] = 1;
                                
                iArray[15] = new Array();
                iArray[15][0] = "�ʱ�";
                iArray[15][1] = "40px";
                iArray[15][2] = 100;
                iArray[15][3] = 1;
                                
                iArray[16] = new Array();
                iArray[16][0] = "��ע��Ϣ";
                iArray[16][1] = "40px";
                iArray[16][2] = 100;
                iArray[16][3] = 1;
                
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initOldBnfGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                OldBnfGrid = new MulLineEnter("fm", "OldBnfGrid");
                OldBnfGrid.mulLineCount = 0;
                OldBnfGrid.displayTitle = 1;
                OldBnfGrid.locked = 1;
                OldBnfGrid.hiddenPlus = 0;
                OldBnfGrid.hiddenSubtraction = 0;
                OldBnfGrid.canChk = 0;
                OldBnfGrid.canSel = 0;
                OldBnfGrid.chkBoxEventFuncName = "";
                OldBnfGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                OldBnfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initOldBnfGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ����������Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initNewBnfGrid()
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
                iArray[1][0] = "�����˺�";
                iArray[1][1] = "55px";
                iArray[1][2] = 100;
                iArray[1][3] = 2;
                iArray[1][9] = "������|NotNull";
                iArray[1][10] = "InsuredCodeList";
                iArray[1][11] = getInsuredCodeList();
                iArray[1][12] = "1|2";
                iArray[1][13] = "0|1";
                iArray[1][19] = 1;

                iArray[2] = new Array();
                iArray[2][0] = "����������";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "���������";
                iArray[3][1] = "45px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "NewBnfType";
                iArray[3][9] = "���������|NotNull&Code:NewBnfType";

                iArray[4] = new Array();
                iArray[4][0] = "����������";
                iArray[4][1] = "50px";
                iArray[4][2] = 100;
                iArray[4][3] = 1;
                iArray[4][9] = "����������|NotNull&Len<=60";

                iArray[5] = new Array();
                iArray[5][0] = "��";
                iArray[5][1] = "0px";
                iArray[5][2] = 0;
                iArray[5][3] = 0;
               
                iArray[6] = new Array();
                iArray[6][0] = "֤������";
                iArray[6][1] = "40px";
                iArray[6][2] = 100;
                iArray[6][3] = 2;
                iArray[6][4] = "IDType";
                iArray[6][9] = "֤������|NotNull&Code:IDType";

                iArray[7] = new Array();
                iArray[7][0] = "֤������";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 1;
                iArray[7][9] = "֤������|NotNull&Len<=20";

                iArray[8] = new Array();
                iArray[8][0] = "�뱻���˹�ϵ";
                iArray[8][1] = "50px";
                iArray[8][2] = 100;
                iArray[8][3] = 2;
                iArray[8][4] = "Relation";
                iArray[8][9] = "�뱻���˹�ϵ|NotNull&Code:Relation";

                iArray[9] = new Array();
                iArray[9][0] = "����˳��";
                iArray[9][1] = "40px";
                iArray[9][2] = 100;
                iArray[9][3] = 2;
                iArray[9][4] = "BnfOrder";
                iArray[9][9] = "����˳��|NotNull&Code:BnfOrder";

                iArray[10] = new Array();
                iArray[10][0] = "����ݶ�";
                iArray[10][1] = "40px";
                iArray[10][2] = 100;
                iArray[10][3] = 1;
                iArray[10][9] = "����ݶ�|NotNull&Value>=0&Value<=1";

                iArray[11] = new Array();
                iArray[11][0] = "����";
                iArray[11][1] = "40px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][10] = "CustomerType";
                iArray[11][11] = getCustomerType();
                iArray[11][19] = 1;
                
                iArray[12] = new Array();
                iArray[12][0] = "�Ա�";
                iArray[12][1] = "80px";
                iArray[12][2] = 100;
                iArray[12][3] = 2;
                iArray[12][4] = "Sex";
                
                iArray[13] = new Array();
                iArray[13][0] = "��������";
                iArray[13][1] = "80px";
                iArray[13][2] = 100;
                iArray[13][3] = 8;
                iArray[13][9] = "��������|Date";
                
                iArray[14] = new Array();
                iArray[14][0] = "סַ";
                iArray[14][1] = "80px";
                iArray[14][2] = 100;
                iArray[14][3] = 1;
                                
                iArray[15] = new Array();
                iArray[15][0] = "�ʱ�";
                iArray[15][1] = "40px";
                iArray[15][2] = 100;
                iArray[15][3] = 1;
                iArray[15][9] = "�ʱ�|Len=6";
                                
                iArray[16] = new Array();
                iArray[16][0] = "��ע��Ϣ";
                iArray[16][1] = "40px";
                iArray[16][2] = 100;
                iArray[16][3] = 1;
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initNewBnfGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                NewBnfGrid = new MulLineEnter("fm", "NewBnfGrid");
                NewBnfGrid.mulLineCount = 0;
                NewBnfGrid.displayTitle = 1;
                NewBnfGrid.locked = 0;
                NewBnfGrid.hiddenPlus = 0;
                NewBnfGrid.hiddenSubtraction = 0;
                NewBnfGrid.canChk = 0;
                NewBnfGrid.canSel = 0;
                NewBnfGrid.chkBoxEventFuncName = "";
                NewBnfGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                NewBnfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorTypeBCInit.jsp --> initNewBnfGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

