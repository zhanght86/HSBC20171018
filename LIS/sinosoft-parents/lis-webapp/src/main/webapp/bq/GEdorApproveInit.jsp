<%
//�������ƣ�EdorApproveInit.jsp
//�����ܣ���ȫ����

%>

<script language="JavaScript">

//ҳ���ʼ��
function initForm()
{
    try
    {
        initInpBox();
        initEdorMainGrid();
        initEdorItemGrid();
        initQuery();
    }
    catch (ex)
    {
        alert("�� GEdorApproveInit.jsp --> initForm �����з����쳣:��ʼ���������");
    }
}


//��ʼ��ҳ��Ԫ��
function initInpBox()
{
    try
    {
       document.all('EdorAcceptNo').value     = NullToEmpty("<%= tEdorAcceptNo %>");
       document.all('MissionID').value        = NullToEmpty("<%= tMissionID %>");
       document.all('SubMissionID').value     = NullToEmpty("<%= tSubMissionID %>");
    }
    catch (ex)
    {
        alert("�� GEdorApproveInit.jsp --> initInpBox �����з����쳣:��ʼ���������");
    }
}

// ��ȫ�����б�ĳ�ʼ��
function initEdorMainGrid()
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
        iArray[1][0] = "����������";
        iArray[1][1] = "120px";
        iArray[1][2] = 150;
        iArray[1][3] = 0;

        iArray[2] = new Array();
        iArray[2][0] = "���屣����";
        iArray[2][1] = "120px";
        iArray[2][2] = 150;
        iArray[2][3] = 0;

        iArray[3] = new Array();
        iArray[3][0] = "���ս��Ѷ�Ӧ��";
        iArray[3][1] = "0px";
        iArray[3][2] = 0;
        iArray[3][3] = 3;

        iArray[4] = new Array();
        iArray[4][0] = "��������";
        iArray[4][1] = "0px";
        iArray[4][2] = 0;
        iArray[4][3] = 3;

        iArray[5] = new Array();
        iArray[5][0] = "��Ч����";
        iArray[5][1] = "100px";
        iArray[5][2] = 100;
        iArray[5][3] = 0;
        iArray[5][21] = 3;

        iArray[6] = new Array();
        iArray[6][0] = "���˷ѽ��";
        iArray[6][1] = "120px";
        iArray[6][2] = 150;
        iArray[6][3] = 0;
        iArray[6][21] = 3;

        iArray[7] = new Array();
        iArray[7][0] = "���˷���Ϣ";
        iArray[7][1] = "120px";
        iArray[7][2] = 150;
        iArray[7][3] = 0;
        iArray[7][21] = 3;

        iArray[8] = new Array();
        iArray[8][0] = "����״̬";
        iArray[8][1] = "120px";
        iArray[8][2] = 100;
        iArray[8][3] = 0;

        iArray[9] = new Array();
        iArray[9][0] = "����״̬����";
        iArray[9][1] = "0px";
        iArray[9][2] = 0;
        iArray[9][3] = 3;

		iArray[10]=new Array();
		iArray[10][0]="����";         		
		iArray[10][1]="50px";            		 
		iArray[10][2]=60;            			
		iArray[10][3]=2;              		
		iArray[10][4]="Currency";              	  
		iArray[10][9]="����|code:Currency";

        EdorMainGrid = new MulLineEnter("fm", "EdorMainGrid");
        //��Щ���Ա�����loadMulLineǰ
        EdorMainGrid.mulLineCount = 1;
        EdorMainGrid.displayTitle = 1;
        EdorMainGrid.locked = 1;
        EdorMainGrid.canSel = 1;
        EdorMainGrid.hiddenPlus = 1;
        EdorMainGrid.hiddenSubtraction=1;
        EdorMainGrid.selBoxEventFuncName = "showEdorItemListAuto";
        EdorMainGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� GEdorApproveInit.jsp --> initEdorMainGrid �����з����쳣:��ʼ���������");
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
      		
       		AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 
       		
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

// ��ȫ��Ŀ�б�ĳ�ʼ��
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
        iArray[1][0] = "����������";
        iArray[1][1] = "120px";
        iArray[1][2] = 150;
        iArray[1][3] = 0;

        iArray[2] = new Array();
        iArray[2][0] = "��������";
        iArray[2][1] = "120px";
        iArray[2][2] = 150;
        iArray[2][3] = 0;

        iArray[3] = new Array();
        iArray[3][0] = "���屣����";
        iArray[3][1] = "100px";
        iArray[3][2] = 150;
        iArray[3][3] = 0;

        iArray[4] = new Array();
        iArray[4][0] = "��Ч����";
        iArray[4][1] = "90px";
        iArray[4][2] = 100;
        iArray[4][3] = 0;
        iArray[4][21] = 3;

        iArray[5] = new Array();
        iArray[5][0] = "���˷ѽ��";
        iArray[5][1] = "90px";
        iArray[5][2] = 100;
        iArray[5][3] = 0;
        iArray[5][21] = 3;

        iArray[6] = new Array();
        iArray[6][0] = "���˷���Ϣ";
        iArray[6][1] = "90px";
        iArray[6][2] = 100;
        iArray[6][3] = 0;
        iArray[6][21] = 3;

        iArray[7] = new Array();
        iArray[7][0] = "����״̬";
        iArray[7][1] = "90px";
        iArray[7][2] = 100;
        iArray[7][3] = 0;

        iArray[8] = new Array();
        iArray[8][0] = "����״̬����";
        iArray[8][1] = "0px";
        iArray[8][2] = 0;
        iArray[8][3] = 3;

        iArray[9] = new Array();
        iArray[9][0] = "��������";
        iArray[9][1] = "0px";
        iArray[9][2] = 0;
        iArray[9][3] = 3;

        iArray[10] = new Array();
        iArray[10][0] = "�������ͱ���";
        iArray[10][1] = "0px";
        iArray[10][2] = 0;
        iArray[10][3] = 3;
        
        iArray[11] = new Array();
        iArray[11][0] = "�����㷨";
        iArray[11][1] = "0px";
        iArray[11][2] = 0;
        iArray[11][3] = 0;

		iArray[12]=new Array();
		iArray[12][0]="����";         		
		iArray[12][1]="50px";            		 
		iArray[12][2]=60;            			
		iArray[12][3]=2;              		
		iArray[12][4]="Currency";              	  
		iArray[12][9]="����|code:Currency";

        EdorItemGrid = new MulLineEnter("fm" , "EdorItemGrid");
        //��Щ���Ա�����loadMulLineǰ
        EdorItemGrid.mulLineCount = 1;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.locked = 1;
        EdorItemGrid.canSel = 1;
        EdorItemGrid.hiddenPlus = 1;
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.selBoxEventFuncName = "getEdorItemInfo";
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("�� GEdorApproveInit.jsp --> initEdorItemGrid �����з����쳣:��ʼ���������");
    }
}

</script>
