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
     //����ҳ��ؼ��ĳ�ʼ����
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
	//divTaskPlan.style.display = "";
	//divTaskPlanButton.style.display = "";
	//divTask.style.display = "none";
	//divTaskButton.style.display = "none";
	
	//$('#hourStart').combobox({ onLoadSuccess: function(){ $('#hourStart').combobox('clear'); }})
	
	
    	initInpBox();
    	initSelBox();    
	initTaskPlanGrid();
	initParamGrid();
	initMoreParamGrid();
	initCrontabGrid();
	//queryTaskInfo();
	queryTaskPlanInfo();
	//refreshTask();
	//refreshShowTask();
	initSimpleTable();
	
	//����������Ϣ����
	initLogItemGrid();
	
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
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����ƻ�����";          	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

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
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
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
      iArray[8][3]=0; 

      iArray[9]=new Array();
      iArray[9][0]="ѭ������";      	   		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0; 

      iArray[10]=new Array();
      iArray[10][0]="����״̬";      	   		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=2; 
      iArray[10][10]="RunStateList"; 
      iArray[10][11]="0|^0|�ȴ�^1|����^2|��ͣ^3|������ֹ^4|ǿ����ֹ^5|�쳣��ֹ^6|����"; 
      
      iArray[11]=new Array();
      iArray[11][0]="����ģʽ";      	   		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=2; 
      iArray[11][10]="PlanModeFlag"; 
      iArray[11][11]="0|^0|����ģʽ^1|ר��ģʽ"; 
      
      iArray[12]=new Array();
      iArray[12][0]="ʧ�ܺ���";      	   		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=2; 
      iArray[12][10]="RunAfterFlag"; 
      iArray[12][11]="0|^00|�޶���^01|����"; 
		
      TaskPlanGrid = new MulLineEnter( "fm" , "TaskPlanGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskPlanGrid.mulLineCount = 1;   
      TaskPlanGrid.displayTitle = 1;
      TaskPlanGrid.canSel =1;
      TaskPlanGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskPlanGrid.hiddenSubtraction=1;
	  TaskPlanGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  TaskPlanGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}
function initCrontabGrid()
{                               
    var iArray = new Array();      
    try
    {
    	// �� Сʱ �� �� �� ���� 
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��";          	//����
      iArray[1][1]="60px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=1;                       //�Ƿ���������,1��ʾ������0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="Сʱ";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��";      	   		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=1; 

      iArray[5]=new Array();
      iArray[5][0]="��";      	   		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";      	   		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=1; 

      CrontabGrid = new MulLineEnter( "fm" , "CrontabGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CrontabGrid.mulLineCount = 1;   
      CrontabGrid.displayTitle = 1;
      CrontabGrid.canSel =0;
      CrontabGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CrontabGrid.hiddenSubtraction=1;
	//  CrontabGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  CrontabGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}
function initParamGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";             	//����
      iArray[1][1]="200px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�


//		var tSQL = " select serverip||':'||serverport,'��Ч���' from LDTaskServer where validflag='1' ";
		
	/*mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql12");  
	mySql.addSubPara("��Ч�ڵ�"); 
	var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));*/
//	var ttemp = (easyQueryVer3(tSQL, 1, 0, 1));
      iArray[2]=new Array();
      iArray[2][0]="����ֵ";         		//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][4]="taskserver";  
    //  iArray[2][10]="ParamValue"; 
    //  iArray[2][11]=ttemp; 
    //  iArray[2][12]="1|1"; 
	 		iArray[2][19]="1"; 
	
      ParamGrid = new MulLineEnter( "fm" , "ParamGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ParamGrid.mulLineCount = 1;   
      ParamGrid.displayTitle = 1;
      ParamGrid.canSel =0;
      ParamGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ParamGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	  ParamGrid.loadMulLine(iArray);
	  
	  ParamGrid.setRowColData(0,1,'serverip:port');
    }
    catch(ex)
    {
      alert(ex);
    }
}


function initMoreParamGrid()
{                               
    var iArray = new Array();      
    try
    {
       iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";             	//����
      iArray[1][1]="200px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=1;                       //�Ƿ���������,1��ʾ������0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����ֵ";         		//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ������0��ʾ������



		
      MoreParamGrid = new MulLineEnter( "fm" , "MoreParamGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MoreParamGrid.mulLineCount = 1;   
      MoreParamGrid.displayTitle = 1;
      MoreParamGrid.canSel =0;
	  MoreParamGrid.hiddenPlus=0;
      MoreParamGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	  MoreParamGrid.loadMulLine(iArray);
//	  MoreParamGrid.delEventFuncName="MulLineSubtraction"; //���õ�JS��������������
//	  MoreParamGrid. hiddenPlus=0;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	  
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initTaskGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�������";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������


		
      TaskGrid = new MulLineEnter( "fm" , "TaskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskGrid.mulLineCount = 1;   
      TaskGrid.displayTitle = 1;
      TaskGrid.canSel =1;
      TaskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskGrid.hiddenSubtraction=1;
	  TaskGrid.selBoxEventFuncName = "onTaskSelected";

	  TaskGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

//����������Ϣ
function initLogItemGrid()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="���Ƶ�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[1][1]="80px";            		//�п�
		iArray[1][2]=10;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��־��ص�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[2][1]="100px";            		//�п�
		iArray[2][2]=10;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
	
		iArray[3]=new Array();
		iArray[3][0]="��־��ص�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[3][1]="100px";            		//�п�
		iArray[3][2]=10;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

		
		
		iArray[4]=new Array();
		iArray[4][0]="���Ƶ�ؼ���������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=10;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

		
		iArray[5]=new Array();                                                    
		iArray[5][0]="�Ƿ񱣴�";         			//����������Ϊ˳��ţ����������壬���Ҳ�
		iArray[5][1]="0px";            		//�п�                                
		iArray[5][2]=10;            			//�����ֵ                              
		iArray[5][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������   		
	

      
		LogItemGrid = new MulLineEnter( "fm" , "LogItemGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		
		LogItemGrid.mulLineCount = 0;
		LogItemGrid.displayTitle = 1;
		LogItemGrid.hiddenPlus = 1;
		 //  CollectivityGrid.locked = 0;
		LogItemGrid.hiddenSubtraction = 1;
		LogItemGrid.canSel=0;
		LogItemGrid.canChk=1;
		//LogItemGrid.selBoxEventFuncName = "ShowItem";
		LogItemGrid.loadMulLine(iArray);

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>