<%
//�������ƣ�SelAssignDuty.jsp
//�����ܣ�
//�������ڣ�2008-09-26
//������  ��liuqh
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
	initAssignGrid();
	InitAssign();

  }
  catch(re)
  {
    alert(initForm.re);
    alert("SelUWAssignDutyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initAssignGrid()
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
      iArray[1][0]="�˱�ʦ����";          	//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="UWCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][5]="1|2|3|4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1|3|4";              	        //��������з������ô����еڼ�λֵ

      iArray[2]=new Array();
      iArray[2][0]="�˱�ʦ����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�˱�����";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�˱�״̬";      	   		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 

      iArray[5]=new Array();
      iArray[5][0]="���䵥������";      	   		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�Ѿ��ֳ�����";      	   		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�Ƿ�����";      	   		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=2; 
      iArray[7][10]="Planstate"; 
      iArray[7][11]="0|^0|����^1|��ֹ"; 
      
      

 
		
      AssignGrid = new MulLineEnter( "document" , "AssignGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AssignGrid.mulLineCount = 5;   
      AssignGrid.displayTitle = 1;
      AssignGrid.canSel =0;
      AssignGrid.canChk =1;
      AssignGrid.hiddenPlus=0;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      AssignGrid.hiddenSubtraction=0;
	  //AssignGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  AssignGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}






</script>
