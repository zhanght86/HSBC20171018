<%
//�������ƣ�LDCurrencyQueryInit.jsp
//�����ܣ�
//�������ڣ�2009-10-12 19:31:48
//������  ��ZhanPeng���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {    
  	document.all('CurrCode').value = '';
    document.all('CurrName').value = '';
    document.all('Validation').value = '';
    document.all('CodeAlias').value = '';             
  }
  catch(ex)
  {
    alert("��LDCurrencyQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                        

function initForm()
{
  try
  {
    initInpBox();   
	  initCodeGrid();
  }
  catch(re)
  {
    alert("LDCurrencyQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
function initCodeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="100px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

		    iArray[1]=new Array();
		    iArray[1][0]="���ִ���";         //����
		    iArray[1][1]="100px";         //���
		    iArray[1][2]=100;         //��󳤶�
		    iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		    iArray[2]=new Array();
		    iArray[2][0]="��������";         //����
		    iArray[2][1]="100px";         //���
		    iArray[2][2]=100;         //��󳤶�
		    iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		    iArray[3]=new Array();
		    iArray[3][0]="��Ч��־";         //����
		    iArray[3][1]="100px";         //���
		    iArray[3][2]=100;         //��󳤶�
		    iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[4]=new Array();
		    iArray[4][0]="�������";         //����
		    iArray[4][1]="100px";         //���
		    iArray[4][2]=100;         //��󳤶�
		    iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    

	   		CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      CodeGrid.mulLineCount = 5;   
	      CodeGrid.displayTitle = 1;
	      CodeGrid.canSel = 1;
	      CodeGrid.hiddenPlus = 1;
	      CodeGrid.hiddenSubtraction = 1; 
	      CodeGrid.loadMulLine(iArray);  
	      CodeGrid.selBoxEventFuncName = "selectItem";

      }
      catch(ex)
      {
        alert("��ʼ��CodeGridʱ����"+ ex);
      }
    }


</script>
