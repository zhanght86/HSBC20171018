<%
//�������ƣ�GEdorAutoUWInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
//����ʱ��ѯ
function reportDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divEdorAutoUW.style.left=ex;
  	divEdorAutoUW.style.top =ey;
	divEdorAutoUW.style.display ='';
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	document.all('EdorNo').value='';
	document.all('GrpContNo').value = '';
  }
  catch(ex)
  {
    alert("��PGrpEdorUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PGrpEdorUWInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();  
      
    initPGrpEdorMainGrid();
  }
  catch(re)
  {
    alert("PGrpEdorUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPGrpEdorMainGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         		//�п�
      iArray[0][2]=10;          		//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";    		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���屣����";         	//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            		//�����ֵ
      iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[4]=new Array();
      iArray[4][0]="��Ч����";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            		//�����ֵ
      iArray[4][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���˷ѽ��";         	//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            		//�����ֵ
      iArray[5][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      PGrpEdorMainGrid = new MulLineEnter( "fm" , "PGrpEdorMainGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PGrpEdorMainGrid.mulLineCount = 10;   
      PGrpEdorMainGrid.displayTitle = 1;
      PGrpEdorMainGrid.canSel =0;
      PGrpEdorMainGrid.canChk = 1;
      PGrpEdorMainGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PGrpEdorMainGrid.hiddenSubtraction=1;
      PGrpEdorMainGrid.detailInfo="������ʾ��ϸ��Ϣ";
      PGrpEdorMainGrid.detailClick=reportDetailClick;
      PGrpEdorMainGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
