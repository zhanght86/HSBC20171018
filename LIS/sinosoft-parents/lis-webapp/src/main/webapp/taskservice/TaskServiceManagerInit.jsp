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
	initTaskPlanGrid();
//alert('2');
	queryTaskPlanInfo();
//	alert('3');
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initTaskPlanGrid()
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
      iArray[1][0]="����ƻ�����";          	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�Ƿ�����";      	   		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=2; 
      iArray[4][10]="RunFlagList"; 
      iArray[4][11]="0|^0|�ȴ�^1|����"; 

      iArray[5]=new Array();
      iArray[5][0]="ѭ������";      	   		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="RecycleTypeList"; 
      iArray[5][11]="0|^11|ÿ����һ��^21|ÿСʱһ��^31|ÿ��һ��^41|ÿ��һ��^51|ÿ��һ��^61|ÿ��һ��^72|���"; 

      iArray[6]=new Array();
      iArray[6][0]="��ʼʱ��";      	   		//����
      iArray[6][1]="140px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0; 

      iArray[7]=new Array();
      iArray[7][0]="��ֹʱ��";      	   		//����
      iArray[7][1]="140px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0; 

      iArray[8]=new Array();
      iArray[8][0]="ѭ�����";            //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=3; 

      iArray[9]=new Array();
      iArray[9][0]="ѭ������";      	   		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=3; 

      iArray[10]=new Array();
      iArray[10][0]="����״̬";      	   		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=2; 
      iArray[10][10]="RunStateList"; 
      iArray[10][11]="0|^0|�ȴ�^1|����^2|��ͣ^3|������ֹ^4|ǿ����ֹ^5|�쳣��ֹ^6|����"; 
      
      iArray[11]=new Array();
      iArray[11][0]="����/����";      	   		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=2; 
      iArray[11][10]="RunStateList"; 
      iArray[11][11]="0|^T|����^G|����"; 
	  
	  iArray[12]=new Array();
      iArray[12][0]="�������";      	   		//����
      iArray[12][1]="170px";            		//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=0; 
      TaskPlanGrid = new MulLineEnter( "fm" , "TaskPlanGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskPlanGrid.mulLineCount = 1;   
      TaskPlanGrid.displayTitle = 1;
      TaskPlanGrid.canSel =1;
      TaskPlanGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskPlanGrid.hiddenSubtraction=1;
	 // TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  TaskPlanGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}


</script>
