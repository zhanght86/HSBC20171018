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
 * @date     : 2005-11-21, 2006-02-16, 2006-10-14
 * @direction: ������ȫ�������¼���ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>
    <%
        String sCurrentDate = PubFun.getCurrentDate();
    %>

    <script language="JavaScript">

        var InquiryTrackGrid;    //ȫ�ֱ���, �����������

        //�ܺ�������ʼ��ҳ��
        function initForm()
        {
            try
            {
                initInputBox();
                initInquiryTrackGrid();
            }
            catch (ex)
            {
                alert("�� EdorLiveInquiryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        //�����ĳ�ʼ��
        function initInputBox()
        {
            try
            {
                document.getElementsByName("CurrentDate")[0].value = "<%=sCurrentDate%>";
            }
            catch (ex)
            {
                alert("�� EdorLiveInquiryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        //�����켣���� MultiLine �ĳ�ʼ��
        function initInquiryTrackGrid()
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
                iArray[1][0] = "�ͻ�����";
                iArray[1][1] = "70px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "��������";
                iArray[2][1] = "70px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;
                iArray[2][21] = 3;

                iArray[3] = new Array();
                iArray[3][0] = "�������";
                iArray[3][1] = "70px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;
                iArray[3][21] = 2;

                iArray[4] = new Array();
                iArray[4][0] = "��������";
                iArray[4][1] = "70px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][21] = 3;

                iArray[5] = new Array();
                iArray[5][0] = "����ԭ��";
                iArray[5][1] = "90px";
                iArray[5][2] = 150;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "����Ա����";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "��ע";
                iArray[7][1] = "110px";
                iArray[7][2] = 150;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("�� EdorLiveInquiryInit.jsp --> initInquiryTrackGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                InquiryTrackGrid = new MulLineEnter("fm", "InquiryTrackGrid");
                InquiryTrackGrid.mulLineCount = 3;
                InquiryTrackGrid.displayTitle = 1;
                InquiryTrackGrid.locked = 1;
                InquiryTrackGrid.hiddenPlus = 1;
                InquiryTrackGrid.hiddenSubtraction = 1;
                InquiryTrackGrid.canChk = 0;
                InquiryTrackGrid.canSel = 0;
                InquiryTrackGrid.chkBoxEventFuncName = "";
                InquiryTrackGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                InquiryTrackGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� EdorLiveInquiryInit.jsp --> initInquiryTrackGrid �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

