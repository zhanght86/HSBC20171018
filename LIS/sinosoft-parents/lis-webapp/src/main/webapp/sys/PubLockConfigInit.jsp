<%
//�������ƣ�
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
	divAllLockInfo.style.display = "";
	divAllLockInfoButton.style.display = "";
	divLockBase.style.display = "none";
	divLockBaseButton.style.display = "none";
		divLockGroup.style.display = "none";
	divLockGroupButton.style.display = "none";
	divLockConfig.style.display = "none";
    	initInpBox();
    	initSelBox();    
	initAllLockInfoGrid();
	initLockBaseGrid();
	initLockGroupGrid();
	initLockGroupConfigGrid();
	 queryLockGroup();
	 queryAllLockModule();
	 queryAllLockGroup();
//	initParamGrid();
//	initMoreParamGrid();
	//queryTaskInfo();
	//queryTaskPlanInfo();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initAllLockInfoGrid()
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
      iArray[1][0]="ҵ���";          	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����ʱ��";      	   		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 

      iArray[5]=new Array();
      iArray[5][0]="������ʱ��(��)";      	   		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="������ʱ��(��)";      	   		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		
      AllLockInfoGrid = new MulLineEnter( "document" , "AllLockInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AllLockInfoGrid.mulLineCount = 5;   
      AllLockInfoGrid.displayTitle = 1;
      AllLockInfoGrid.canSel =1;
      AllLockInfoGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      AllLockInfoGrid.hiddenSubtraction=1;
	  AllLockInfoGrid.selBoxEventFuncName = "onAllLockInfoSelected";

	  AllLockInfoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLockBaseGrid()
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
      iArray[1][0]="����ģ�����";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����ģ������";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����ģ������";         		//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


		
      LockBaseGrid = new MulLineEnter( "document" , "LockBaseGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LockBaseGrid.mulLineCount = 5;   
      LockBaseGrid.displayTitle = 1;
      LockBaseGrid.canSel =0;
      LockBaseGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LockBaseGrid.hiddenSubtraction=1;
	  	LockBaseGrid.selBoxEventFuncName = "onTaskSelected";

	  	LockBaseGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLockGroupGrid()
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
      iArray[1][0]="�������������";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������������";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������������";         		//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


		
      LockGroupGrid = new MulLineEnter( "document" , "LockGroupGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LockGroupGrid.mulLineCount = 5;   
      LockGroupGrid.displayTitle = 1;
      LockGroupGrid.canSel =1;
      LockGroupGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LockGroupGrid.hiddenSubtraction=1;
	  LockGroupGrid.selBoxEventFuncName = "onTaskSelected";

	  LockGroupGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLockGroupConfigGrid()
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
      iArray[1][0]="�������������";             	//����
      iArray[1][1]="0px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=1;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�


      iArray[2]=new Array();
      iArray[2][0]="����ģ�����";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[2][4]="lockmodule";  //���ô���:
			iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      
      iArray[3]=new Array();
      iArray[3][0]="����ģ������";         		//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


		
      LockGroupConfigGrid = new MulLineEnter( "document" , "LockGroupConfigGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LockGroupConfigGrid.mulLineCount = 5;   
      LockGroupConfigGrid.displayTitle = 1;
      LockGroupConfigGrid.canSel =0;
      LockGroupConfigGrid.hiddenPlus=0;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LockGroupConfigGrid.hiddenSubtraction=0;
			LockGroupConfigGrid.addEventFuncName="addNewGroupConfig";
	  	LockGroupConfigGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>
