<%
//�������ƣ�LDExRateQueryInit.jsp
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
  	document.all('CurrCodeName').value = ''; 
    document.all('DestCode').value = '';    
    document.all('DestCodeName').value = '';
  }
  catch(ex)
  {
    alert("��LDExRateQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("LDExRateQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
		    iArray[1][0]="��ұ��ִ���";         //����
		    iArray[1][1]="100px";         //���
		    iArray[1][2]=100;         //��󳤶�
		    iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		    iArray[2]=new Array();
		    iArray[2][0]="������λ";         //����
		    iArray[2][1]="100px";         //���
		    iArray[2][2]=100;         //��󳤶�
		    iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		    iArray[3]=new Array();
		    iArray[3][0]="���ұ��ִ���";         //����
		    iArray[3][1]="100px";         //���
		    iArray[3][2]=100;         //��󳤶�
		    iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[4]=new Array();
		    iArray[4][0]="�ֻ������";         //����
		    iArray[4][1]="100px";         //���
		    iArray[4][2]=100;         //��󳤶�
		    iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[5]=new Array();
		    iArray[5][0]="�ֻ�������";         //����
		    iArray[5][1]="100px";         //���
		    iArray[5][2]=100;         //��󳤶�
		    iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[6]=new Array();
		    iArray[6][0]="�ֳ������";         //����
		    iArray[6][1]="100px";         //���
		    iArray[6][2]=100;         //��󳤶�
		    iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[7]=new Array();
		    iArray[7][0]="�ֳ�������";         //����
		    iArray[7][1]="100px";         //���
		    iArray[7][2]=100;         //��󳤶�
		    iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[8]=new Array();
		    iArray[8][0]="�м��";         //����
		    iArray[8][1]="100px";         //���
		    iArray[8][2]=100;         //��󳤶�
		    iArray[8][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[9]=new Array();
		    iArray[9][0]="��������";         //����
		    iArray[9][1]="100px";         //���
		    iArray[9][2]=100;         //��󳤶�
		    iArray[9][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    
		    iArray[10]=new Array();
		    iArray[10][0]="����ʱ��";         //����
		    iArray[10][1]="100px";         //���
		    iArray[10][2]=100;         //��󳤶�
		    iArray[10][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		    

	   		CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      CodeGrid.mulLineCount = 10;   
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
