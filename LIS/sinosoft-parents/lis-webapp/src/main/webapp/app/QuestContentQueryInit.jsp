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
	initQuestQueryGrid();
  }
  catch(re)
  {
    //alert(initForm.re);
    alert("QuestContentInput.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initQuestQueryGrid()
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
      iArray[1][0]="���������";          	//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="���������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���Ͷ���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�Ƿ�������������";      	   		//����
      iArray[4][1]="30px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 

      iArray[5]=new Array();
      iArray[5][0]="������";      	   		//����
      iArray[5][1]="30px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0; 
      
      iArray[6]=new Array();
      iArray[6][0]="�޸�����";      	   		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0; 
      
      QuestQueryGrid = new MulLineEnter( "document" , "QuestQueryGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      QuestQueryGrid.mulLineCount = 5;   
      QuestQueryGrid.displayTitle = 1;
      QuestQueryGrid.canSel =1;
      //QuestGrid.canChk = 1;
      QuestQueryGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      QuestQueryGrid.hiddenSubtraction=1;
	  QuestQueryGrid.selBoxEventFuncName = "displayQuestContent";

	  QuestQueryGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}






</script>
