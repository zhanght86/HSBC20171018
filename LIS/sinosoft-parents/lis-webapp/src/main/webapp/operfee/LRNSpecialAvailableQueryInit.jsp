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
 * @date     : 2005-12-03, 2006-02-15
 * @direction: �������⸴Ч��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var QueryGrid;    //ȫ�ֱ���, ʧЧ������ѯ

        //�ܺ�������ʼ������ҳ��
        function initForm()
        {
            try
            {
                initQueryGrid();
            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        //��Ч��ͬ��Ϣ��ѯ MultiLine �ĳ�ʼ��
        function initQueryGrid()
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
                iArray[1][0] = "��ͬ��";
                iArray[1][1] = "100px";
                iArray[1][2] = 120;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "��Ч����";
                iArray[2][1] = "45px";
                iArray[2][2] = 80;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "ʧЧԭ��";
                iArray[3][1] = "85px";
                iArray[3][2] = 120;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�������";
                iArray[4][1] = "115px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��ע";
                iArray[5][1] = "90px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "����Ա";
                iArray[6][1] = "60px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "��Ч����";
                iArray[7][1] = "60px";
                iArray[7][2] = 80;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "��Чʱ��";
                iArray[8][1] = "60px";
                iArray[8][2] = 80;
                iArray[8][3] = 0;

            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableQueryInit.jsp --> initQueryGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                QueryGrid = new MulLineEnter("fm", "QueryGrid");
                QueryGrid.mulLineCount = 5;
                QueryGrid.displayTitle = 1;
                QueryGrid.locked = 1;
                QueryGrid.hiddenPlus = 1;
                QueryGrid.hiddenSubtraction = 1;
                QueryGrid.canChk = 0;
                QueryGrid.canSel = 0;
                QueryGrid.chkBoxEventFuncName = "";
                QueryGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                QueryGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableQueryInit.jsp --> initQueryGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

