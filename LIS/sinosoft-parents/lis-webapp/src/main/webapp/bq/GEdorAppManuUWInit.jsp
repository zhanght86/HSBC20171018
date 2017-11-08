<%
/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱��������ʼ��
 ******************************************************************************/
%>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ��ʼ -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sOperator = tGlobalInput.Operator;
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>


    <script language="JavaScript">

        var GrpPolGrid;    //ȫ�ֱ���, �ŵ����ֶ���

        /**
         * �ܺ�������ʼ������ҳ��
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
                initEdorItemGrid();
                // initGrpPolGrid();
                queryEdorAppInfo();
                queryEdorItemInfo();
                queryGrpContInfo();
                //queryGrpPolGrid();
                //queryLastUWState();
            }
            catch (ex)
            {
                alert("�� GEdorAppManuUWInit.jsp --> initForm �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ������ĳ�ʼ��
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("MissionID")[0].value = "<%=request.getParameter("MissionID")%>";
                document.getElementsByName("SubMissionID")[0].value = "<%=request.getParameter("SubMissionID")%>";
                document.getElementsByName("ActivityStatus")[0].value = "<%=request.getParameter("ActivityStatus")%>";
                document.getElementsByName("ActivityID")[0].value = "0000008005";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("�� GEdorAppManuUWInit.jsp --> initHiddenArea �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
            }
            catch (ex)
            {
                alert("�� GEdorAppManuUWInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * ��ȫ��Ŀ�б�ĳ�ʼ��
         */
        function initEdorItemGrid()
        {
            var iArray = new Array();

            try
            {
                  iArray[0] = new Array();
                  iArray[0][0] = "���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
                  iArray[0][1] = "30px";                  //�п�
                  iArray[0][2] = 30;                      //�����ֵ
                  iArray[0][3] = 0;                       //�Ƿ���������,1��ʾ����0��ʾ������

                  iArray[1] = new Array();
                  iArray[1][0] = "��ȫ�����";
                  iArray[1][1] = "120px";
                  iArray[1][2] = 100;
                  iArray[1][3] = 0;

                  iArray[2] = new Array();
                  iArray[2][0] = "��������";
                  iArray[2][1] = "120px";
                  iArray[2][2] = 100;
                  iArray[2][3] = 0;

                  iArray[3] = new Array();
                  iArray[3][0] = "���屣����";
                  iArray[3][1] = "150px";
                  iArray[3][2] = 100;
                  iArray[3][3] = 0;

                  iArray[4] = new Array();
                  iArray[4][0] = "��Ч����";
                  iArray[4][1] = "80px";
                  iArray[4][2] = 100;
                  iArray[4][3] = 0;
                  iArray[4][21]  =  3;

                  iArray[5] = new Array();
                  iArray[5][0] = "���˷ѽ��ϼ�";
                  iArray[5][1] = "100px";
                  iArray[5][2] = 100;
                  iArray[5][3] = 0;
                  iArray[5][21]=  3;

                  iArray[6] = new Array();
                  iArray[6][0] = "���˷���Ϣ";
                  iArray[6][1] = "80px";
                  iArray[6][2] = 100;
                  iArray[6][3] = 0;
                  iArray[6][21]  =  3;

                  iArray[7] = new Array();
                  iArray[7][0] = "����״̬";
                  iArray[7][1] = "100px";
                  iArray[7][2] = 100;
                  iArray[7][3] = 0;

                  iArray[8] = new Array();
                  iArray[8][0] = "����״̬����";
                  iArray[8][1] = "0px";
                  iArray[8][2] = 100;
                  iArray[8][3] = 3;

                  iArray[9] = new Array();
                  iArray[9][0] = "������������";
                  iArray[9][1] = "0px";
                  iArray[9][2] = 0;
                  iArray[9][3] = 3;

                  iArray[10] = new Array();
                  iArray[10][0] = "�������ͱ���";
                  iArray[10][1] = "0px";
                  iArray[10][2] = 0;
                  iArray[10][3] = 3;

				iArray[11]=new Array();
				iArray[11][0]="����";         		
				iArray[11][1]="40px";            		 
				iArray[11][2]=60;            			
				iArray[11][3]=2;              		
				iArray[11][4]="Currency";              	  
				iArray[11][9]="����|code:Currency";
                 
                  EdorItemGrid = new MulLineEnter("document", "EdorItemGrid");
                  //��Щ���Ա�����loadMulLineǰ
                  EdorItemGrid.mulLineCount = 5;
                  EdorItemGrid.displayTitle = 1;
                  EdorItemGrid.locked = 1;
                  EdorItemGrid.canSel = 0;
                  EdorItemGrid.hiddenPlus = 1;
                  EdorItemGrid.hiddenSubtraction=1;
                  EdorItemGrid.loadMulLine(iArray);
            }
            catch(ex)
            {
                alert("�� GEdorAppManuUWInit.jsp --> initEdorItemGrid �����з����쳣: ��ʼ���������");
            }
        }

        /**
         * �ŵ����ֶ��в�ѯ MultiLine �ĳ�ʼ��
         */
        function initGrpPolGrid()
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
                iArray[1][0] = "�������ֺ�";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "������";
                iArray[2][1] = "110px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "��������";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "GEdorType";
                iArray[3][18] = 170;

                iArray[4] = new Array();
                iArray[4][0] = "���ִ���";
                iArray[4][1] = "65px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "��������";
                iArray[5][1] = "200px";
                iArray[5][2] = 200;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "���Ѽ��";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "Ͷ������";
                iArray[7][1] = "70px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "��������";
                iArray[8][1] = "70px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "��Ч����";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "�˱�����";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 2;
                iArray[10][4] = "gedorgrppoluwstate";
                iArray[10][18] = 78;
                
                GrpPolGrid = new MulLineEnter("document", "GrpPolGrid");
                          
                GrpPolGrid.mulLineCount = 5;
                GrpPolGrid.displayTitle = 1;
                GrpPolGrid.locked = 1;
                GrpPolGrid.hiddenPlus = 1;
                GrpPolGrid.hiddenSubtraction = 1;
                GrpPolGrid.canChk = 0;
                GrpPolGrid.canSel = 1;
                GrpPolGrid.chkBoxEventFuncName = "";
                GrpPolGrid.selBoxEventFuncName = "initGrpPolInfo";
                //�������Ա����� MulLineEnter loadMulLine ֮ǰ
                GrpPolGrid.loadMulLine(iArray);
       		
            }
            catch (ex)
            {
                alert("�� GEdorAppManuUWInit.jsp --> initGrpPolGrid �����з����쳣: ��ʼ���������");
            }
        }
        
        function initAgentGrid()
  			{                               
    			var iArray = new Array();
      
      		try
      		{
       			iArray[0]=new Array();
       			iArray[0][0]="���";         //����
       			iArray[0][1]="30px";         //����
       			iArray[0][2]=100;         //����
       			iArray[0][3]=0;         //����
       			
       			iArray[1]=new Array();
       			iArray[1][0]="�������ˮ��";         //����
       			iArray[1][1]="120px";         //���
       			iArray[1][2]=100;         //��󳤶�
       			iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[2]=new Array();
       			iArray[2][0]="�ŵ�����";         //����
       			iArray[2][1]="120px";         //���
       			iArray[2][2]=100;         //��󳤶�
       			iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[3]=new Array();
       			iArray[3][0]="��ȫ�����";         //����
       			iArray[3][1]="120px";         //���
       			iArray[3][2]=100;         //��󳤶�
       			iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[4]=new Array();
       			iArray[4][0]="������";         //����
       			iArray[4][1]="60px";         //���
       			iArray[4][2]=100;         //��󳤶�
       			iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[5]=new Array();
       			iArray[5][0]="�ظ���";         //����
       			iArray[5][1]="60px";         //���
       			iArray[5][2]=100;         //��󳤶�
       			iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
 
       			iArray[6]=new Array();
       			iArray[6][0]="���������";         //����
       			iArray[6][1]="60px";         //���
       			iArray[6][2]=100;         //��󳤶�
       			iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[7]=new Array();
       			iArray[7][0]="�·�����";         //����
       			iArray[7][1]="80px";         //���
       			iArray[7][2]=100;         //��󳤶�
       			iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

       			iArray[8]=new Array();
       			iArray[8][0]="�ظ�����";         //����
       			iArray[8][1]="80px";         //���
       			iArray[8][2]=100;         //��󳤶�
       			iArray[8][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
       			
       			iArray[9]=new Array();
       			iArray[9][0]="��������";         //����
       			iArray[9][1]="0px";         //���
       			iArray[9][2]=100;         //��󳤶�
       			iArray[9][3]=3;         //�Ƿ�����¼�룬0--���ܣ�1--����
       		
        		iArray[10]=new Array();
       			iArray[10][0]="�ظ�����";         //����
       			iArray[10][1]="0px";         //���
       			iArray[10][2]=100;         //��󳤶�
       			iArray[10][3]=3;         //�Ƿ�����¼�룬0--���ܣ�1--����
      		
       		AgentGrid = new MulLineEnter( "document" , "AgentGrid" ); 
       		
       		//��Щ���Ա�����loadMulLineǰ
       		//AgentGrid.mulLineCount = 1;   
       		AgentGrid.displayTitle = 1;
       		AgentGrid.canSel=1;
//     		AgentGrid.canChk=0;
       		AgentGrid.locked=1;
     		AgentGrid.hiddenPlus=1;
     		AgentGrid.hiddenSubtraction=1;
     		AgentGrid.selBoxEventFuncName = "ShowAskInfo"; //����RadioBoxʱ��Ӧ����
       		AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��AgentGridʱ����"+ ex);
      }
    }

    </script>

    <!-- ���� JSP Init ��ʼ��ҳ�� : ���� -->

