<%
//�������ƣ�WorkFlowNotePadInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("��WorkFlowNotePadInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��WorkFlowNotePadInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {

    initInpBox();
    initSelBox();    
	initParam();
	
	if(fm.UnSaveFlag.value == "1")
	{
	   divButton.style.display = 'none';
	   divReturn.style.display = '';
	}
	else
	{
	   divButton.style.display = '';
	   divReturn.style.display = 'none';
	}
	initQuestGrid();
	initQuery();
  }
  catch(re)
  {
    alert("WorkFlowNotePadInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initQuestGrid() {                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			 //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			    //�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="ӡˢ��";    	     //����
    iArray[1][1]="90px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���±�����";         			//����
    iArray[2][1]="280px";            		//�п�
    iArray[2][2]=400;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="¼��λ��";         			//����
    iArray[3][1]="0px";            		//�п�
    iArray[3][2]=40;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                             
    iArray[4]=new Array();
    iArray[4][0]="¼��Ա";         			//����
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=40;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="¼������";         			//����
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=50;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="¼��ʱ��";         			//����
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=50;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
    //��Щ���Ա�����loadMulLineǰ                         
    QuestGrid.mulLineCount = 0;
    QuestGrid.displayTitle = 1;
    QuestGrid.canSel = 1;
    QuestGrid.hiddenPlus = 1;
    QuestGrid.hiddenSubtraction = 1;
    QuestGrid.loadMulLine(iArray); 
    QuestGrid.selBoxEventFuncName = "showContent";
 
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
