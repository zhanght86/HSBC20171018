<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                          
<script language="JavaScript">

function initInpBox()
{ 

  try
  {
    document.all('ContNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    document.all('RiskCode').value = '';
    document.all('MakeDate').value = '';
  }
  catch(ex)
  {
    alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();  
	initPolGrid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initPolGrid()
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="115px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="90px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0; 

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="¼��Ա";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="¼��ʱ��";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
