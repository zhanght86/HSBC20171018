<%
//�������ƣ�LJSGetDrawInput.jsp
//�����ܣ�
//�������ڣ�2002-07-19 11:48:25
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<!--%@include file="../common/jsp/UsrCheck.jsp"%-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("��LJSGetDrawInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

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
    alert("��LJSGetDrawInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
// ��������Ϣ�б�ĳ�ʼ��
function initGetDrawGrid()
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
      iArray[1][0]="��ȡ֪ͨ���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���˱�����";    	//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=80;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="������������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ϴ���������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="������������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="��ȡ���";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      SubPayGrid = new MulLineEnter( "fm" , "SubPayGrid" );
      //��Щ���Ա�����loadMulLineǰ
      //SubPayGrid.mulLineCount = 10;
      SubPayGrid.displayTitle = 1;
      SubPayGrid.canSel = 0;
      //SubPayGrid.canChk=1;
      SubPayGrid.hiddenPlus = 1; 
    	SubPayGrid.hiddenSubtraction = 1;
      //SubPayGrid.chkBoxEventFuncName="CalSumMoney";      
      SubPayGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      //SubPayGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLJSGetGrid()
{
  var iArray = new Array();
    
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="25px";            		//�п�
    iArray[0][2]=10;            			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[1]=new Array();
    iArray[1][0]="���屣������";         		//����
    iArray[1][1]="145px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="���˱�������";         		//����
    iArray[2][1]="145px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="����������";         		//����
    iArray[3][1]="145px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="�ۼ�δ��ȡ���";         		//����
    iArray[4][1]="70px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0; 
    
    iArray[5]=new Array();
    iArray[5][0]="����Ա";         		//����
    iArray[5][1]="65px";            		//�п�
    iArray[5][2]=65;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    

    LJSGetGrid = new MulLineEnter( "fm" , "LJSGetGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    //LJSGetGrid.mulLineCount = 10;   
    LJSGetGrid.displayTitle = 1;
    LJSGetGrid.locked = 1;
    LJSGetGrid.canSel = 1;
    LJSGetGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    LJSGetGrid.hiddenSubtraction=1;    //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    LJSGetGrid.selBoxEventFuncName ="reportDetailClick";
    LJSGetGrid.loadMulLine(iArray); 
      
    //��Щ����������loadMulLine����
    //LJSGetGrid.setRowColData(1,1,"1");
  }
  catch(ex)
  {
    alert(ex);
  }
}
function initForm()
{
  try
  {
    initInpBox();
    // initSelBox();
    initGetDrawGrid();
  }
  catch(re)
  {
    alert("LJSGetDrawInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
