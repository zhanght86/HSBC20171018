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
 * @date     : 2007-03-20
 * @direction: ��ȫ���������޸Ĺ켣��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var PwdTrackGrid;    //ȫ�ֱ���, �޸Ĺ켣����

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initPwdTrackGrid();
                queryPwdTrackGrid();
            }
            catch (ex)
            {
                alert("�� PwdChangeTrackQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
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
            }
            catch (ex)
            {
                alert("�� PwdChangeTrackQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �޸Ĺ켣���в�ѯ MultiLine �ĳ�ʼ��
         */
        function initPwdTrackGrid()
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
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "Ͷ���˿ͻ���";
                iArray[2][1] = "80px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "Ͷ��������";
                iArray[3][1] = "80px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�����˿ͻ���";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "����������";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "�޸�����";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "�޸�ʱ��";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "����Ա";
                iArray[8][1] = "80px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
            }
            catch (ex)
            {
                alert("�� PwdChangeTrackQueryInit.jsp --> initPwdTrackGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                PwdTrackGrid = new MulLineEnter("fm", "PwdTrackGrid");
                PwdTrackGrid.mulLineCount = 0;
                PwdTrackGrid.displayTitle = 1;
                PwdTrackGrid.locked = 1;
                PwdTrackGrid.hiddenPlus = 1;
                PwdTrackGrid.hiddenSubtraction = 1;
                PwdTrackGrid.canChk = 0;
                PwdTrackGrid.canSel = 0;
                PwdTrackGrid.chkBoxEventFuncName = "";
                PwdTrackGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                PwdTrackGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PwdChangeTrackQueryInit.jsp --> initPwdTrackGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

