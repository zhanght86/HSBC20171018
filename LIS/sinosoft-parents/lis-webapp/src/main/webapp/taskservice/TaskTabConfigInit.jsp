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
	queryLDUserGridInfo();
	//alert('2');
	initUserTabDetailGrid();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initLDUserGrid()
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
      iArray[1][0]="�û�����";             	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�û�����";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
      LDUserGrid = new MulLineEnter( "fm" , "LDUserGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LDUserGrid.mulLineCount = 1;   
      LDUserGrid.displayTitle = 1;
      LDUserGrid.canSel =1;
      LDUserGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LDUserGrid.hiddenSubtraction=1;
	 	  LDUserGrid.selBoxEventFuncName = "onLDUserSelected";

	  LDUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}



function initUserTabDetailGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

			//var tSQL = "select tasktabid,tasktabname from LDTaskTabConfig order by showorder";
			var mySql = new SqlClass();
			mySql = new SqlClass();
			mySql.setResourceName("taskservice.TaskTabConfigSql");
			mySql.setSqlId("TaskTabConfigSql1");
			mySql.addSubPara('1');  
			//turnPagePlan.queryModal(mySql.getString(),TaskPlanGrid);
			
			var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));
			
      iArray[1]=new Array();
      iArray[1][0]="��ǩ����";          	//����
      iArray[1][1]="80px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
  		iArray[1][10]="TaskTabID";  //���ô���:
			iArray[1][11]=ttemp;
		  iArray[1][12]="1|2";
		  iArray[1][13]="0|1";
    
      iArray[2]=new Array();
      iArray[2][0]="��ǩ����";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      UserTabDetailGrid = new MulLineEnter( "fm" , "UserTabDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UserTabDetailGrid.mulLineCount = 1;   
      UserTabDetailGrid.displayTitle = 1;
      //TaskGroupDetailGrid.canSel =1;
      //TaskGroupDetailGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      //TaskGroupDetailGrid.hiddenSubtraction=1;
	  	//TaskGroupDetailGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  	UserTabDetailGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
