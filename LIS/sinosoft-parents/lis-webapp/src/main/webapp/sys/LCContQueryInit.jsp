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
 * @date     : 2006-02-20
 * @direction: ����״̬��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var ContStateGrid;    //ȫ�ֱ���, ����״̬����

        //�ܺ�������ʼ������ҳ��
        function initForm()
        {
            try
            {
                initInputBox();
                initContStateGrid();
                queryContStateGrid();
            }
            catch (ex)
            {
                alert("�� LCContQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        //�����ĳ�ʼ��
        function initInputBox()
        {
            try
            {
                document.getElementsByName("ContNo")[0].value = "<%=request.getParameter("ContNo")%>";
            }
            catch (ex)
            {
                alert("�� LCContQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        //����״̬���в�ѯ MultiLine �ĳ�ʼ��
        function initContStateGrid()
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
                iArray[1][1] = "110px";
                iArray[1][2] = 120;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "���ִ���";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "��������";
                iArray[3][1] = "90px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "״̬����";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "����״̬";
                iArray[5][1] = "50px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "״̬ԭ��";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "״̬��ʼ����";
                iArray[7][1] = "60px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;

                iArray[8] = new Array();
                iArray[8][0] = "״̬��������";
                iArray[8][1] = "60px";
                iArray[8][2] = 100;
                iArray[8][3] = 8;

                iArray[9] = new Array();
                iArray[9][0] = "��ע";
                iArray[9][1] = "100px";
                iArray[9][2] = 120;
                iArray[9][3] = 0;
            }
            catch (ex)
            {
                alert("�� LCContQueryInit.jsp --> initContStateGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                ContStateGrid = new MulLineEnter("fm", "ContStateGrid");
                ContStateGrid.mulLineCount = 0;
                ContStateGrid.displayTitle = 1;
                ContStateGrid.locked = 1;
                ContStateGrid.hiddenPlus = 1;
                ContStateGrid.hiddenSubtraction = 1;
                ContStateGrid.canChk = 0;
                ContStateGrid.canSel = 0;
                ContStateGrid.chkBoxEventFuncName = "";
                ContStateGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                ContStateGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� LCContQueryInit.jsp --> initContStateGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

