
<%
	 /*******************************************************************************
	 * <p>Title: Lis 6.0</p>
	 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
	 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
	 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
	 * <p>WebSite: http://www.sinosoft.com.cn</p>
	 *
	 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
	 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
	 * @date     : 2005-11-29, 2005-12-03, 2006-02-15, 2006-02-28, 2006-03-23
	 * @direction: �������⸴Ч¼���ʼ��
	 ******************************************************************************/
%>

<!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->
<%@ page import="com.sinosoft.lis.pubfun.*"%>

<%
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	String sManageCom = tGlobalInput.ManageCom;
	tGlobalInput = null;
%>

<script language="JavaScript">
        var AvailableGrid;    //ȫ�ֱ���, ʧЧ��������

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initAvailableGrid();
            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("ManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ʧЧ��ͬ����Ϣ�б� MultiLine �ĳ�ʼ��
         */
        function initAvailableGrid()
        {
            var iArray = new Array();                          //������, ���ظ� MultiLine ���
            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "���";                          //����(˳���, ������)
                iArray[0][1] = "30px";                          //�п�
                iArray[0][2] = 30;                              //�����ֵ
                iArray[0][3] = 0;                               //�Ƿ���������: 0��ʾ������; 1��ʾ����

                iArray[1] = new Array();
                iArray[1][0] = "���ִ���";
                iArray[1][1] = "65px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "��������";
                iArray[2][1] = "120px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "�����˿ͻ���";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "����������";
                iArray[4][1] = "65px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��Ч����";
                iArray[5][1] = "65px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "����״̬";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;
                
                iArray[7] = new Array();
                iArray[7][0] = "ʧЧԭ��";
                iArray[7][1] = "120px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableInit.jsp --> initAvailableGrid �����з����쳣: ��ʼ���������");
            }
            try
            {
                AvailableGrid = new MulLineEnter("fm", "AvailableGrid");
                AvailableGrid.mulLineCount = 5;
                AvailableGrid.displayTitle = 1;
                AvailableGrid.locked = 1;
                AvailableGrid.hiddenPlus = 1;
                AvailableGrid.hiddenSubtraction = 1;
                AvailableGrid.canChk = 0;
                AvailableGrid.canSel = 0;
                AvailableGrid.chkBoxEventFuncName = "";
                AvailableGrid.selBoxEventFuncName = "";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                AvailableGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� LRNSpecialAvailableInit.jsp --> initAvailableGrid �����з����쳣: ��ʼ���������");
            }
        }
</script>

<!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

