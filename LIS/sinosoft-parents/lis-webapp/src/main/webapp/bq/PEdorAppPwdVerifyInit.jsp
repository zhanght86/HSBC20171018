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
 * @date     : 2006-11-06
 * @direction: ��ȫ�������������ĿУ�鱣�������ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->


    <script language="JavaScript">

        var ContPwdGrid;    //ȫ�ֱ���, ����������

        //�ܺ�������ʼ��ҳ��
        function initForm()
        {
            try
            {
                initInputBox();
                initContPwdGrid();
                queryContPwdGrid();
                focusContPwdGrid();
            }
            catch (ex)
            {
                alert("�� PEdorAppPwdInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        //�����ĳ�ʼ��
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
                document.getElementsByName("OtherNo")[0].value = "<%=request.getParameter("OtherNo")%>";
                document.getElementsByName("OtherNoType")[0].value = "<%=request.getParameter("OtherNoType")%>";
                document.getElementsByName("EdorItemAppDate")[0].value = "<%=request.getParameter("EdorItemAppDate")%>";
                document.getElementsByName("AppType")[0].value = "<%=request.getParameter("AppType")%>";
                showOneCodeName("EdorNoType", "OtherNoType", "OtherNoTypeName");
                showOneCodeName("EdorAppType", "AppType", "AppTypeName");
            }
            catch (ex)
            {
                alert("�� PEdorAppPwdInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        //����������� MultiLine �ĳ�ʼ��
        function initContPwdGrid()
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
                iArray[2][0] = "Ͷ���˿ͻ���";
                iArray[2][1] = "80px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "Ͷ��������";
                iArray[3][1] = "70px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "�����˿ͻ���";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "����������";
                iArray[5][1] = "70px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "��Ч����";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                iArray[6][21] = 3;

                iArray[7] = new Array();
                iArray[7][0] = "��������";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 4;
                iArray[7][9] = "��������|NotNull&Len<=6";
            }
            catch (ex)
            {
                alert("�� EdorLiveInquiryInit.jsp --> initContPwdGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                ContPwdGrid = new MulLineEnter("fm", "ContPwdGrid");
                ContPwdGrid.mulLineCount = 1;
                ContPwdGrid.displayTitle = 1;
                ContPwdGrid.locked = 1;
                ContPwdGrid.hiddenPlus = 1;
                ContPwdGrid.hiddenSubtraction = 1;
                ContPwdGrid.canChk = 0;
                ContPwdGrid.canSel = 0;
                ContPwdGrid.chkBoxEventFuncName = "";
                ContPwdGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                ContPwdGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� PEdorAppPwdInit.jsp --> initContPwdGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

