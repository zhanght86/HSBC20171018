<%
//�������ƣ�TaskServiceInput.jsp
//�����ܣ�
//�������ڣ�2004-12-15 
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
}

function initSelBox()
{  
}                                        

function initForm()
{
  try
  {
	
	initTaskRunLogTodayGrid();
	initTaskRunLogBeforeGrid();
	queryTaskRunLogTodayGrid();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initTaskRunLogTodayGrid()
{                               
    var iArray = new Array();      
    try
    {
    	//����ƻ����� ������б��� ������� ִ������ ִ��ʱ�� �������� ����ʱ�� ִ��״̬ ִ�н�� ���з�������Ϣ

    	
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����ƻ�����";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="������б���";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="ִ������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="ִ��ʱ��";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����ʱ��";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="ִ��״̬";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="ִ�н��";         		//����
      iArray[9][1]="180px";            		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="���з�������Ϣ";         		//����
      iArray[10][1]="180px";            		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
		
      TaskRunLogTodayGrid = new MulLineEnter( "fm" , "TaskRunLogTodayGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskRunLogTodayGrid.mulLineCount = 1;   
      TaskRunLogTodayGrid.displayTitle = 1;
      TaskRunLogTodayGrid.canSel =0;
      TaskRunLogTodayGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskRunLogTodayGrid.hiddenSubtraction=1;
	 	 // TaskRunLogTodayGrid.selBoxEventFuncName = "onTaskGroupSelected";

	  	TaskRunLogTodayGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}


function initTaskRunLogBeforeGrid()
{                               
    var iArray = new Array();      
    try
    {
    	//����ƻ����� ������б��� ������� ִ������ ִ��ʱ�� �������� ����ʱ�� ִ��״̬ ִ�н�� ���з�������Ϣ

    	
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����ƻ�����";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="������б���";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="ִ������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="ִ��ʱ��";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����ʱ��";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="ִ��״̬";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="ִ�н��";         		//����
      iArray[9][1]="180px";            		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="���з�������Ϣ";         		//����
      iArray[10][1]="180px";            		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
		
      TaskRunLogBeforeGrid = new MulLineEnter( "fm" , "TaskRunLogBeforeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskRunLogBeforeGrid.mulLineCount = 1;   
      TaskRunLogBeforeGrid.displayTitle = 1;
      TaskRunLogBeforeGrid.canSel =0;
      TaskRunLogBeforeGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskRunLogBeforeGrid.hiddenSubtraction=1;
	 	  //TaskRunLogBeforeGrid.selBoxEventFuncName = "onTaskGroupSelected";

	  	TaskRunLogBeforeGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
