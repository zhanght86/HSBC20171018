<%
//�������ƣ�LoLoanQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
	document.all('ContNo').value = tContNo;
    document.all('PolNo').value = tPolNo;
  }
  catch(ex)
  {
    alert("LoLoanQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("LoLoanQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
	easyQueryClick();
  }
  catch(re)
  {
    alert("LoLoanQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var PolGrid; 
// ������Ϣ�б�ĳ�ʼ��
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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
       
      iArray[2]=new Array();
      iArray[2][0]="���ֺ���";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��ȫ������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

//      iArray[4]=new Array();
//      iArray[4][0]="��Ŀ";         		//����
//      iArray[4][1]="80px";            		//�п�
//      iArray[4][2]=200;            			//�����ֵ
//      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[4][10] = "LoanType";
//      iArray[4][11] = "0|^0|���^1|�潻";
//      iArray[4][12] = "3";
//      iArray[4][19] = "0";

      iArray[4]=new Array();
      iArray[4][0]="ʵ����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10] = "LoanType";
      iArray[5][11] = "0|^0|���^1|�潻";
      iArray[5][12] = "3";
      iArray[5][19] = "0";
    
       
      iArray[6]=new Array();
      iArray[6][0]="��ʼʱ��";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[7]=new Array();
      iArray[7][0]="���";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][23]="0";
      
	  iArray[8]=new Array();
      iArray[8][0]="���";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=7; 									//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][23]="0";
      
	  iArray[9]=new Array();
      iArray[9][0]="�����־";         		//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][10] = "LoanFlag";
      iArray[9][11] = "0|^0|δ����^1|����";
      iArray[9][12] = "8";
      iArray[9][19] = "0";
      
   
	  iArray[10]=new Array();
      iArray[10][0]="����Ա";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0; 
      
      iArray[11]=new Array();
			iArray[11][0]="����";
			iArray[11][1]="60px";
			iArray[11][2]=100;
			iArray[11][3]=2;
			iArray[11][4]="currency";	
     
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
