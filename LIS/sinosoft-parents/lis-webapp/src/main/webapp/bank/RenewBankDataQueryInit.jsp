
<%
	//�������ƣ�RenewBankConfirmInit.jsp
	//�����ܣ�
	//�������ڣ�2002-06-19 11:10:36
	//������  ��hzm����
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
    alert("��RenewBankConfirmInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("RenewBankConfirmInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var CodeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initCodeGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="ӡˢ����";         		//����
	  iArray[1][1]="90px";            	//�п�
	  iArray[1][2]=20;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="��ͬ����";       		  //����
	  iArray[2][1]="130px";            	//�п�
	  iArray[2][2]=20;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[3]=new Array();
	  iArray[3][0]="Ͷ����";       		  //����
	  iArray[3][1]="60px";            	//�п�
	  iArray[3][2]=20;            			//�����ֵ
	  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
	  iArray[4][0]="���ִ���";       		//����
	  iArray[4][1]="60px";            	//�п�
	  iArray[4][2]=10;            			//�����ֵ
	  iArray[4][3]=2; 
	  iArray[4][4]="RiskCode"; 
      iArray[4][9]="���ִ���|code:riskcode&NOTNULL";
      
      iArray[5]=new Array();
	  iArray[5][0]="��������";	       	//����
	  iArray[5][1]="70px";            	//�п�
	  iArray[5][2]=10;            			//�����ֵ
	  iArray[5][3]=0; 
      
      iArray[6]=new Array();
	  iArray[6][0]="ת��״̬";			   	//����
	  iArray[6][1]="50px"; 	           	//�п�
	  iArray[6][2]=10;            			//�����ֵ
	  iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  iArray[6][10] = "State";
	  iArray[6][11] = "0|^1|�Ѵ��գ�ת��δȷ��^2|ת��ȷ�ϣ�δ����^3|ת��ȷ�ϣ��ѷ���";
	  iArray[6][12] = "6";
	  iArray[6][19] = "0";
		
      iArray[7]=new Array();
	  iArray[7][0]="ת��ȷ�ϲ���Ա";   	//����
	  iArray[7][1]="60px";            		//�п�
	  iArray[7][2]=10;            			//�����ֵ
	  iArray[7][3]=0; 
      
      iArray[8]=new Array();
	  iArray[8][0]="ȷ��ʱ��";			   	//����
	  iArray[8][1]="70px"; 	           	//�п�
	  iArray[8][2]=10;            			//�����ֵ
	  iArray[8][3]=0; 
    
      iArray[9]=new Array();
	  iArray[9][0]="ת�ʳ�������Ա";			   	//����
	  iArray[9][1]="60px"; 	           	//�п�
	  iArray[9][2]=10;            			//�����ֵ
	  iArray[9][3]=0; 
    
      iArray[10]=new Array();
	  iArray[10][0]="����ʱ��";			   	//����
	  iArray[10][1]="70px"; 	           	//�п�
	  iArray[10][2]=10;            			//�����ֵ
	  iArray[10][3]=0; 
    
	  CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  CodeGrid.mulLineCount = 5;   
	  CodeGrid.displayTitle = 1;
	  CodeGrid.hiddenSubtraction=1;
	  CodeGrid.hiddenPlus=1;
	  //CodeGrid.canChk = 1;
      CodeGrid.locked = 1;
	  CodeGrid.loadMulLine(iArray);  	
	} catch(ex) {
		alert(ex);
	}
}

</script>
