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
 * @date     : 2006-12-06, 2006-12-18
 * @direction: Ӱ��Ǩ�Ƴ�ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        String sOperator = tGlobalInput.Operator;
        tGlobalInput = null;
    %>

    <script language="JavaScript">

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
            }
            catch (ex)
            {
                alert("�� ImgMoveInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
            }
            catch (ex)
            {
                alert("�� ImgMoveInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("OldManageCom")[0].value = "<%=sManageCom%>";
                document.getElementsByName("NewManageCom")[0].value = "86";
                showOneCodeName("Station", "OldManageCom", "OldManageComName");
                showOneCodeName("Station", "NewManageCom", "NewManageComName");
            }
            catch (ex)
            {
                alert("�� ImgMoveInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

