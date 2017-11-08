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
 * @date     : 2006-03-16, 2006-06-28, 2006-11-08
 * @direction: ��ȫ�ո��ѷ�ʽ�����ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>

    <script language="JavaScript">

        var ChgTrackGrid;    //ȫ�ֱ���, ����켣��Ϣ
        var EdorInfoGrid;    //ȫ�ֱ���, ��ȫ������Ϣ

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                //initChgTrackGrid();
                initEdorInfoGrid();
            }
            catch (ex)
            {
                alert("�� EdorPayGetFormChangeInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("CurrentManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("�� EdorPayGetFormChangeInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }


        /**
         * ����켣���� MultiLine �ĳ�ʼ��
         */
        function initChgTrackGrid()
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
                iArray[1][0] = "�������";
                iArray[1][1] = "50px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "ԭ��ʽ";
                iArray[2][1] = "55px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "�·�ʽ";
                iArray[3][1] = "55px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�¿�����";
                iArray[4][1] = "60px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                

                iArray[5] = new Array();
                iArray[5][0] = "���ʺ�";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "���ʻ���";
                iArray[6][1] = "55px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "����ȡ��";
                iArray[7][1] = "55px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "�����֤��";
                iArray[8][1] = "90px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "�������";
                iArray[9][1] = "60px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;
            }
            catch (ex)
            {
                alert("�� EdorPayGetFormChangeInit.jsp --> initChgTrackGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                ChgTrackGrid = new MulLineEnter("document", "ChgTrackGrid");
                ChgTrackGrid.mulLineCount = 0;
                ChgTrackGrid.displayTitle = 1;
                ChgTrackGrid.locked = 1;
                ChgTrackGrid.hiddenPlus = 1;
                ChgTrackGrid.hiddenSubtraction = 1;
                //ChgTrackGrid.canChk = 0;
                //ChgTrackGrid.canSel = 0;
                //ChgTrackGrid.chkBoxEventFuncName = "";
                //ChgTrackGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                ChgTrackGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorPayGetFormChangeInit.jsp --> initChgTrackGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������Ϣ���� MultiLine �ĳ�ʼ��
         */
        function initEdorInfoGrid()
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
                iArray[1][0] = "��ȫ�����";
                iArray[1][1] = "100px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "�������";
                iArray[3][1] = "60px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;
               

                iArray[4] = new Array();
                iArray[4][0] = "��ȫ��Ŀ";
                iArray[4][1] = "55px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
               

                iArray[5] = new Array();
                iArray[5][0] = "���˷ѽ��";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
                

                iArray[6] = new Array();
                iArray[6][0] = "���˷���Ϣ";
                iArray[6][1] = "0px";
                iArray[6][2] = 0;
                iArray[6][3] = 3;
    

                iArray[7] = new Array();
                iArray[7][0] = "����/������";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                

                iArray[8] = new Array();
                iArray[8][0] = "��Ч����";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
   

                iArray[9] = new Array();
                iArray[9][0] = "���˷�֪ͨ���";
                iArray[9][1] = "100px";
                iArray[9][2] = 0;
                iArray[9][3] = 0;
                
                iArray[10] = new Array();
                iArray[10][0] = "���ѷ�ʽ";
                iArray[10][1] = "40px";
                iArray[10][2] = 0;
                iArray[10][3] = 0;
                
                iArray[11] = new Array();
                iArray[11][0] = "��ȡ��";
                iArray[11][1] = "60px";
                iArray[11][2] = 0;
                iArray[11][3] = 0;
                
                iArray[12] = new Array();
                iArray[12][0] = "��ȡ�����֤";
                iArray[12][1] = "120px";
                iArray[12][2] = 0;
                iArray[12][3] = 0;
                
                iArray[13] = new Array();
                iArray[13][0] = "���б���";
                iArray[13][1] = "0px";
                iArray[13][2] = 0;
                iArray[13][3] = 3;
                
                iArray[14] = new Array();
                iArray[14][0] = "�����˺�";
                iArray[14][1] = "0px";
                iArray[14][2] = 0;
                iArray[14][3] = 3;
                
                iArray[15] = new Array();
                iArray[15][0] = "�˻���";
                iArray[15][1] = "0px";
                iArray[15][2] = 0;
                iArray[15][3] = 3;
                
                iArray[16] = new Array();
                iArray[16][0] = "ʵ��/ʵ�պ�";
                iArray[16][1] = "0px";
                iArray[16][2] = 0;
                iArray[16][3] = 3;
            }
            catch (ex)
            {
                alert("�� EdorPayGetFormChangeInit.jsp --> initEdorInfoGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                EdorInfoGrid = new MulLineEnter("document", "EdorInfoGrid");
                EdorInfoGrid.mulLineCount =5;
                EdorInfoGrid.displayTitle = 1;
                EdorInfoGrid.locked = 1;
                EdorInfoGrid.hiddenPlus = 1;
                EdorInfoGrid.hiddenSubtraction = 1;
                EdorInfoGrid.canChk = 0;
                EdorInfoGrid.canSel = 1;
                //EdorInfoGrid.chkBoxEventFuncName = "";
                EdorInfoGrid.selBoxEventFuncName = "ShowSubAccInfo";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                EdorInfoGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorPayGetFormChangeInit.jsp --> initEdorInfoGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

