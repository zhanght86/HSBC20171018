<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                          
<script language="JavaScript">

function initInpBox()
{ 
  try
  {
  	fm.RiskCode.value = '';
    fm.bDate.value = '';  
    fm.eDate.value = ''; 
    fm.ManageCom.value='';
    fm.RiskCodeName.value='';

  }
  catch(ex)
  {
    alert("��OtoFAccInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();  
		//initCodeGrid();
  }
  catch(re)
  {
    alert("��OtoFAccInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCodeGrid()
{                               
    var iArray = new Array();
      
      try
      {
		      iArray[0]=new Array();
		      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		      iArray[0][1]="30px";            	//�п�
		      iArray[0][2]=10;            			//�����ֵ
		      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		      iArray[1]=new Array();
		      iArray[1][0]="�������";         		//����
		      iArray[1][1]="60px";            	//�п�
		      iArray[1][2]=100;            			//�����ֵ
		      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		      iArray[2]=new Array();
		      iArray[2][0]="��������";         		//����
		      iArray[2][1]="60px";          		//�п�
		      iArray[2][2]=100;            			//�����ֵ
		      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		      iArray[3]=new Array();
		      iArray[3][0]="��ȡ����";     		//����
		      iArray[3][1]="60px";          		//�п�
		      iArray[3][2]=100;            			//�����ֵ
		      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		      iArray[4]=new Array();
		      iArray[4][0]="��������";         	//����
		      iArray[4][1]="70px";            	//�п�
		      iArray[4][2]=100;            			//�����ֵ
		      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      		      		      		
		      iArray[5]=new Array();
		      iArray[5][0]="�˻���ʼ����";         	//����
		      iArray[5][1]="70px";            	//�п�
		      iArray[5][2]=100;            			//�����ֵ
		      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      
		      iArray[6]=new Array();
		      iArray[6][0]="���������";         	//����
		      iArray[6][1]="70px";            	//�п�
		      iArray[6][2]=100;            			//�����ֵ
		      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      
		      iArray[7]=new Array();
		      iArray[7][0]="���չ����";         	//����
		      iArray[7][1]="70px";            	//�п�
		      iArray[7][2]=100;            			//�����ֵ
		      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      
		      iArray[8]=new Array();
		      iArray[8][0]="������Ϣ";         	//����
		      iArray[8][1]="70px";            	//�п�
		      iArray[8][2]=100;            			//�����ֵ
		      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      		      		      
		      iArray[9]=new Array();
		      iArray[9][0]="�˱���";         	//����
		      iArray[9][1]="50px";            	//�п�
		      iArray[9][2]=100;            			//�����ֵ
		      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      
		      iArray[10]=new Array();
		      iArray[10][0]="��������";         	//����
		      iArray[10][1]="50px";            	//�п�
		      iArray[10][2]=100;            			//�����ֵ
		      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      
		      iArray[11]=new Array();
		      iArray[11][0]="���ڸ���";         	//����
		      iArray[11][1]="50px";            	//�п�
		      iArray[11][2]=100;            			//�����ֵ
		      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      
		      iArray[12]=new Array();
		      iArray[12][0]="�˱����ü�������ȡ����";         	//����
		      iArray[12][1]="70px";            	//�п�
		      iArray[12][2]=100;            			//�����ֵ
		      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		      



		      CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 
		      CodeGrid.mulLineCount = 5;   
		      CodeGrid.displayTitle = 1;
		      CodeGrid.canSel = 0;
      		CodeGrid.canChk=1;
		      CodeGrid.locked = 1;
		      CodeGrid.hiddenPlus = 1;
		      CodeGrid.hiddenSubtraction = 1;
		      CodeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
