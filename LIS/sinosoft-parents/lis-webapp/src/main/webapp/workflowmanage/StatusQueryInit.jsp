<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                               
    document.all('NO').value = '';
  }
  catch(ex)
  {
    alert("��statusInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��statusInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  { 
    initInpBox(); 
      
    initSelBox();
   
    initNoGrid();
  }
  catch(re)
  {
    alert("��statusInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initNoGrid()
  {                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="MissionId";         		//����
      iArray[1][1]="10px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[2]=new Array();
      iArray[2][0]="MainMissionId";         		//����
      iArray[2][1]="10px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[3]=new Array();
      iArray[3][0]="ProcessId";         		//����
      iArray[3][1]="10px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[4]=new Array();
      iArray[4][0]="ҵ������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ҵ���";            //����
      iArray[5][1]="120px";             //�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��ͬ��";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      
      NoGrid = new MulLineEnter( "fm" , "NoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      NoGrid.mulLineCount = 5;   
      NoGrid.displayTitle = 1;
      NoGrid.locked = 1;
      NoGrid.canSel = 1;
      NoGrid.canChk = 0;
      NoGrid.hiddenPlus = 1;
      NoGrid.hiddenSubtraction = 1;
      NoGrid.loadMulLine(iArray);  
      NoGrid.selBoxEventFuncName = "GetStatus";        
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
