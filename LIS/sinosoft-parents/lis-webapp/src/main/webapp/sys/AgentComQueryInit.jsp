<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {    
    document.all('AgentCom').value = '';
    document.all('Name').value = '';
    document.all('ManageCom').value = '';
    document.all('Address').value = ''; 
    document.all('ZipCode').value = '';
    document.all('Phone').value = ''; 
    if(ManageCom != null && ManageCom !="")
    {
    	document.all('ManageCom').value = ManageCom;
    }
  }
  catch(ex)
  {
    alert("��AgentQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");       
  }
  catch(ex)
  {
    alert("��AgentQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initAgentGrid();
  }
  catch(re)
  {
    alert("AgentQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="30px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

        iArray[1]=new Array();
        iArray[1][0]="�����������";         //����
        iArray[1][1]="80px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="�������";         //����
        iArray[2][1]="80px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="�����������";         //����
        iArray[3][1]="80px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="����ע���ַ";         //����
        iArray[4][1]="100px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[5]=new Array();
        iArray[5][0]="�����ʱ�";         //����
        iArray[5][1]="80px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[6]=new Array();
        iArray[6][0]="�����绰";         //����
        iArray[6][1]="80px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
  
        AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 
        //��Щ���Ա�����loadMulLineǰ
        AgentGrid.mulLineCount = 5;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
        AgentGrid.locked=1;
	    AgentGrid.hiddenPlus=1;
	    AgentGrid.hiddenSubtraction=1;
        AgentGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("��ʼ��AgentGridʱ����"+ ex);
      }
    }

</script>
