<%
//�������ƣ�ReportQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('EdorNo').value = '';
    document.all('ContNo').value = '';
    
    //easyQueryClick();
    
  }
  catch(ex)
  {
    alert("��AllGBqQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    
    initInpBox();
    initEdorPrintGrid();   
  }
  catch(ex)
  {
    alert("ChangeEdorPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initEdorPrintGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";    	//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;      
      
      iArray[2]=new Array();
      iArray[2][0]="��ȫ������";    	//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ȫ��ͬ��";         			//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      EdorPrintGrid = new MulLineEnter( "fm" , "EdorPrintGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      EdorPrintGrid.mulLineCount = 10;   
      EdorPrintGrid.displayTitle = 1;
      EdorPrintGrid.canSel = 1;
      EdorPrintGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      EdorPrintGrid.hiddenSubtraction=1;    //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      EdorPrintGrid.selBoxEventFuncName = "reportDetailClick";
      EdorPrintGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}



</script>
