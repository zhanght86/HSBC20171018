<%
//�������ƣ�PersonPayPlanCancelInit.jsp
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
    document.all('GetNoticeNo').value = '';
    document.all('ContNo').value = '';
    document.all('InsuredName').value = '';
    document.all('GetDate').value = '';
  }
  catch(ex)
  {
    alert("��PersonPayPlanCancelInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PersonPayPlanCancelInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initDiv()
{      
  divLCGet.style.display ='';
  //divCancelCommit.style.display ='none';
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initDiv();    
    initLJSGetGrid();
  }
  catch(re)
  {
    alert("PersonPayPlanCancelInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
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
    iArray[1][0]="֪ͨ�����";         		//����
    iArray[1][1]="145px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="���˱�������";         		//����
    iArray[2][1]="145px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="���屣������";         		//����
    iArray[3][1]="145px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="����������";         		//����
    iArray[4][1]="70px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0; 
    
    iArray[5]=new Array();
    iArray[5][0]="��ȡ���";         		//����
    iArray[5][1]="65px";            		//�п�
    iArray[5][2]=65;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="Ӧ��ȡ����";         		//����
    iArray[6][1]="75px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="�߸�����";         		//����
    iArray[7][1]="75px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����Ա";         		//����
    iArray[8][1]="60px";            		//�п�
    iArray[8][2]=60;            			//�����ֵ
    iArray[8][3]=0;                                   //�Ƿ���������,1��ʾ����0��ʾ������
    
    LJSGetGrid = new MulLineEnter( "document" , "LJSGetGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LJSGetGrid.mulLineCount = 10;   
    LJSGetGrid.displayTitle = 1;
    LJSGetGrid.locked = 1;
    LJSGetGrid.canSel = 1;
    LJSGetGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    LJSGetGrid.hiddenSubtraction=1;    //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    //LJSGetGrid.selBoxEventFuncName ="reportDetailClick";
    LJSGetGrid.loadMulLine(iArray); 
      
    //��Щ����������loadMulLine����
    //LJSGetGrid.setRowColData(1,1,"1");
  }
  catch(ex)
  {
    alert(ex);
  }
}

/*
//����ʱ����
function reportDetailClick(parm1,parm2)
{
  var ex,ey;
  ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  divCancelCommit.style.left=ex;
  divCancelCommit.style.top =ey;
  divCancelCommit.style.display = '';   //��ʾȷ�ϰ�Ť
}
*/
</script>
