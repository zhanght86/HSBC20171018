<%
/*******************************************************************************

 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <script language="JavaScript">

        var EdorItemGrid;    //ȫ�ֱ���, ������ȫ������Ŀ��Ϣ

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initEdorItemGrid();
                queryCustomerInfo();
                queryEdorItem();
            }
            catch (ex)
            {
                alert("�� GEdorAgoQueryInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("CustomerNo")[0].value = "<%=request.getParameter("CustomerNo")%>";
            }
            catch (ex)
            {
                alert("�� GEdorAgoQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ȫ������Ŀ��Ϣ��ѯ MultiLine �ĳ�ʼ��
         */
        function initEdorItemGrid()
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
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "120px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "��������";
                iArray[3][1] = "60px";
                iArray[3][2] = 50;
                iArray[3][3] = 2;
                iArray[3][4] = "GEdorType";
                iArray[3][18] = 170;

                iArray[4] = new Array();
                iArray[4][0] = "���屣����";
                iArray[4][1] = "120px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��ȫ������������";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
                
                iArray[6] = new Array();
                iArray[6][0] = "��ȫ��Ч����";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;           

                iArray[7] = new Array();
                iArray[7][0] = "���˷Ѻϼ�";
                iArray[7][1] = "100px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "���˷���Ϣ";
                iArray[8][1] = "100px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                
                
                iArray[9]=new Array();
				iArray[9][0]="����";          
				iArray[9][1]="50px";              
				iArray[9][2]=60;             
				iArray[9][3]=2;               
				iArray[9][4]="Currency";                 
				iArray[9][9]="����|code:Currency";

                
                                   
            }
            catch (ex)
            {
                alert("�� GEdorAgoQueryInit.jsp --> initEdorItemGrid �����з����쳣: ��ʼ���������");
            }

            try
            {
                EdorItemGrid = new MulLineEnter("document", "EdorItemGrid");
                EdorItemGrid.mulLineCount = 5;
                EdorItemGrid.displayTitle = 1;
                EdorItemGrid.locked = 1;
                EdorItemGrid.hiddenPlus = 1;
                EdorItemGrid.hiddenSubtraction = 1;
                EdorItemGrid.canChk = 0;
                EdorItemGrid.canSel = 1;
                EdorItemGrid.chkBoxEventFuncName = "";
                EdorItemGrid.selBoxEventFuncName = "initHide";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                EdorItemGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("�� GEdorAgoQueryInit.jsp --> initEdorItemGrid �����з����쳣: ��ʼ���������");
            }
        }
    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

