<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {    
    document.all('BankCode').value = '';
    document.all('BankName').value = '';
 
  }
  catch(ex)
  {
    alert("��BankQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��BankQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initBankGrid();
  }
  catch(re)
  {
    alert("��BankQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��AgentGrid
 ************************************************************
 */
function initBankGrid()
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
        iArray[1][0]="���б���";         //����
        iArray[1][1]="80px";         //���
        iArray[1][2]=80;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="��������";         //����
        iArray[2][1]="80px";         //���
        iArray[2][2]=80;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

  
        BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        BankGrid.mulLineCount = 5;   
        BankGrid.displayTitle = 1;
        BankGrid.canSel=1;
//        BankGrid.canChk=0;
        BankGrid.locked=1;
	      BankGrid.hiddenPlus=1;
	      BankGrid.hiddenSubtraction=1;
        BankGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��BankGridʱ����"+ ex);
      }
    }


</script>
