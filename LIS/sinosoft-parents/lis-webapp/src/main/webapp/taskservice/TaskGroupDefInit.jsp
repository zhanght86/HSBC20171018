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
	queryTaskGroupInfo();
	initTaskGroupDetailGrid();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initTaskGroupGrid()
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
      iArray[1][0]="������б���";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�����������";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
      TaskGroupGrid = new MulLineEnter( "fm" , "TaskGroupGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskGroupGrid.mulLineCount = 1;   
      TaskGroupGrid.displayTitle = 1;
      TaskGroupGrid.canSel =1;
      TaskGroupGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      TaskGroupGrid.hiddenSubtraction=1;
	  TaskGroupGrid.selBoxEventFuncName = "onTaskGroupSelected";

	  TaskGroupGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}



function initTaskGroupDetailGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

			//var tSQL = "select taskcode,taskdescribe from ldtask order by taskcode";
			var mySql = new SqlClass();
 			mySql.setResourceName("taskservice.TaskGroupDefSql");
	    mySql.setSqlId("TaskGroupDefSql1");  
	    mySql.addSubPara("1");  
			var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));
			
      iArray[1]=new Array();
      iArray[1][0]="�������";          	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
  		iArray[1][10]="TaskCode";  //���ô���:
			iArray[1][11]=ttemp;
    
      iArray[2]=new Array();
      iArray[2][0]="�����������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      /*
      var tSQL1 = " select '000000','������' from dual "
               + " union "
               + " select taskcode,taskdescribe from ldtask  ";
			var ttemp1 = (easyQueryVer3(tSQL1, 1, 0, 1));
			
			
      iArray[2][10]="DependTaskCode";  //���ô���:
			iArray[2][11]=ttemp1;
*/

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=2; 
      iArray[4][10]="DependType"; 
      iArray[4][11]="0|^00|������^01|��������Ҫ��ȷִ����ϲ�ִ��^02|������ֻҪִ����ϼ���ִ��"; 

      iArray[5]=new Array();
      iArray[5][0]="ʧ�ܺ���";      	   		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="ActionAfterFail"; 
      iArray[5][11]="0|^00|�޶���^01|����"; 

      iArray[6]=new Array();
      iArray[6][0]="ִ��˳��";      	   		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=1; 

    
		
      TaskGroupDetailGrid = new MulLineEnter( "fm" , "TaskGroupDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TaskGroupDetailGrid.mulLineCount = 1;   
      TaskGroupDetailGrid.displayTitle = 1;
      //TaskGroupDetailGrid.canSel =1;
      //TaskGroupDetailGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      //TaskGroupDetailGrid.hiddenSubtraction=1;
	  	//TaskGroupDetailGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  	TaskGroupDetailGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
