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
 * @date     : 2006-03-01
 * @direction: ������ӡ��ѯ��ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var ReissuePrintGrid;    //ȫ�ֱ���, ������ӡ����

        //�ܺ�������ʼ������ҳ��
        function initForm()
        {
            try
            {
                initInputBox();
                initReissuePrintGrid();
                queryReissuePrintGrid();
            }
            catch (ex)
            {
                alert("�� ReissuePrintQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        //�����ĳ�ʼ��
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = NullToEmpty("<%=request.getParameter("EdorAcceptNo")%>");
                document.getElementsByName("ContNo")[0].value = NullToEmpty("<%=request.getParameter("ContNo")%>");
            }
            catch (ex)
            {
                alert("�� ReissuePrintQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        //������ӡ���в�ѯ MultiLine �ĳ�ʼ��
        function initReissuePrintGrid()
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
                iArray[1][2] = 120;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "100px";
                iArray[2][2] = 120;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "���˷ѽ��";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 7;
                iArray[3][23] = "0";

                iArray[4] = new Array();
                iArray[4][0] = "��������";
                iArray[4][1] = "65px";
                iArray[4][2] = 100;
                iArray[4][3] = 8;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 8;

                iArray[6] = new Array();
                iArray[6][0] = "��Ч����";
                iArray[6][1] = "65px";
                iArray[6][2] = 100;
                iArray[6][3] = 8;

                iArray[7] = new Array();
                iArray[7][0] = "��ӡ����";
                iArray[7][1] = "65px";
                iArray[7][2] = 100;
                iArray[7][3] = 8;

                iArray[8] = new Array();
                iArray[8][0] = "��ӡʱ��";
                iArray[8][1] = "65px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "��ӡ����Ա";
                iArray[9][1] = "65px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                
                iArray[10]=new Array();
								iArray[10][0]="����";
								iArray[10][1]="60px";
								iArray[10][2]=100;
								iArray[10][3]=2;
								iArray[10][4]="currency";
            }
            catch (ex)
            {
                alert("�� ReissuePrintQueryInit.jsp --> initReissuePrintGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                ReissuePrintGrid = new MulLineEnter("fm", "ReissuePrintGrid");
                ReissuePrintGrid.mulLineCount = 0;
                ReissuePrintGrid.displayTitle = 1;
                ReissuePrintGrid.locked = 1;
                ReissuePrintGrid.hiddenPlus = 1;
                ReissuePrintGrid.hiddenSubtraction = 1;
                ReissuePrintGrid.canChk = 0;
                ReissuePrintGrid.canSel = 0;
                ReissuePrintGrid.chkBoxEventFuncName = "";
                ReissuePrintGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                ReissuePrintGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� ReissuePrintQueryInit.jsp --> initReissuePrintGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

