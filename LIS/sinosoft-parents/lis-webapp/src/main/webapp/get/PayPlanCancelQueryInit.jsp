<%
//�������ƣ�PayPlanCancelQueryInit.jsp
//�����ܣ�
//�������ڣ�2005-7-6 11:24
//������  ��JL
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("��PayPlanCancelInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PayPlanCancelInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initDiv()
{      
  divCancelLog.style.display ='none';
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initDiv();    
    initLFGetCancelLogGrid();
  }
  catch(re)
  {
    alert("PayPlanCancelInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLFGetCancelLogGrid()
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
    iArray[1][0]="���˱�������";         		//����
    iArray[1][1]="145px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="���屣������";         		//����
    iArray[2][1]="145px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="��������";         		//����
    iArray[3][1]="85px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="��ȡ����";         		//����
    iArray[4][1]="70px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0; 
    
    iArray[5]=new Array();
    iArray[5][0]="��ȡ���";         		//����
    iArray[5][1]="65px";            		//�п�
    iArray[5][2]=65;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="�߸�����Ա";         		//����
    iArray[6][1]="70px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="�߸�����";         		//����
    iArray[7][1]="70px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="��������Ա";         		//����
    iArray[8][1]="70px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=0;                                   //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="��������";         		//����
    iArray[9][1]="70px";            		//�п�
    iArray[9][2]=60;            			//�����ֵ
    iArray[9][3]=0;                                   //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="����ԭ��";         		//����
    iArray[10][1]="0px";            		//�п�
    iArray[10][2]=0;            			//�����ֵ
    iArray[10][3]=0;                                   //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="����ʱ��";         		//����
    iArray[11][1]="80px";            		//�п�
    iArray[11][2]=0;            			//�����ֵ
    iArray[11][3]=1; 
    LFGetCancelLogGrid = new MulLineEnter( "document" , "LFGetCancelLogGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    //LFGetCancelLogGrid.mulLineCount = 10;   
    LFGetCancelLogGrid.displayTitle = 1;
    LFGetCancelLogGrid.locked = 1;
    LFGetCancelLogGrid.canSel = 1;
    LFGetCancelLogGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    LFGetCancelLogGrid.hiddenSubtraction=1;    //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ) 
    LFGetCancelLogGrid.selBoxEventFuncName ="reportDetailClick";
    LFGetCancelLogGrid.loadMulLine(iArray); 
      
    //��Щ����������loadMulLine����
  }
  catch(ex)
  {
    alert(ex);
  }
}

//����ʱ����
function reportDetailClick(parm1,parm2)
{
  var ex,ey;
  ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  divCancelContent.style.left=ex;
  divCancelContent.style.top =ey;
  divCancelContent.style.display = '';   //��ʾȷ�ϰ�Ť      
  document.all('CancelReason').value = LFGetCancelLogGrid.getRowColData(LFGetCancelLogGrid.getSelNo()-1,10);    //���
}

</script>
