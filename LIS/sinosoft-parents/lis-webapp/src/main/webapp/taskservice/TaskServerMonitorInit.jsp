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
//alert('1');

	initTaskServerPlanGrid();
//alert('2');
	queryTaskServerPlanGrid();
	
	initTaskServerConfigGrid();
	
//	alert('3');
	initServerGrid();
	queryServerGrid();
	
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initServerGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";          	//����
      iArray[1][1]="30px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����ֵ";      	   		//����
      iArray[3][1]="30px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1; 

   
      ServerGrid = new MulLineEnter( "fm" , "ServerGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ServerGrid.mulLineCount = 1;   
      ServerGrid.displayTitle = 1;
      ServerGrid.canSel =0;
      ServerGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ServerGrid.hiddenSubtraction=1;
	 // TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  ServerGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}


function initTaskServerPlanGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����ڵ�IP";          	//����
      iArray[1][1]="60px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����ڵ�˿�";         		//����
      iArray[2][1]="40px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ˢ������";      	   		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0; 

      iArray[4]=new Array();
      iArray[4][0]="���ˢ��ʱ��";      	   		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     

      iArray[5]=new Array();
      iArray[5][0]="�Ƿ���Ч";      	   		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2; 
 			iArray[5][10]="ValidFlag"; 
      iArray[5][11]="0|^0|��Ч^1|��Ч"; 
      
      iArray[6]=new Array();
      iArray[6][0]="���з�����";      	   		//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0; 

   
      TaskServerPlanGrid = new MulLineEnter( "fm" , "TaskServerPlanGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskServerPlanGrid.mulLineCount = 1;   
      TaskServerPlanGrid.displayTitle = 1;
      TaskServerPlanGrid.canSel =1;
      TaskServerPlanGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskServerPlanGrid.hiddenSubtraction=1;
	 	  TaskServerPlanGrid.selBoxEventFuncName = "onTaskServerSelected";

	  TaskServerPlanGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initTaskServerConfigGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";          	//����
      iArray[1][1]="30px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����ֵ";      	   		//����
      iArray[3][1]="30px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1; 

   
      TaskServerConfigGrid = new MulLineEnter( "fm" , "TaskServerConfigGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskServerConfigGrid.mulLineCount = 1;   
      TaskServerConfigGrid.displayTitle = 1;
      TaskServerConfigGrid.canSel =0;
      TaskServerConfigGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskServerConfigGrid.hiddenSubtraction=1;
	 // TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  	TaskServerConfigGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
