<%
//�������ƣ�AbnormityErrAndRecordErr.jsp
//�����ܣ��쳣������ԭ���ѯ�Լ���¼�����
//�������ڣ�2007-08-01 14:32:57
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox(){   
}

// ������ĳ�ʼ��
function initSelBox(){  
}                                        

function initForm()
{
  try 
  {
    initInpBox();
    initSelBox();   
	initPolGrid();  
	initErrGrid();
	//alert(22);
	initQuery(); //alert(33);
	initErrQuery(); //alert(34);
  }
  catch(re) {
    alert("AbnormityErrAndRecordErrInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var PolGrid;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="¼����Ŀ";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[2]=new Array();
      iArray[2][0]="¼������";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0; 
      
      iArray[3]=new Array();
      iArray[3][0]="����Ա����";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0; 
      
      iArray[4]=new Array();
      iArray[4][0]="��¼���";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=90;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][10]="ForYN";
      iArray[4][11]="0|^0|�� ^1|��"; 

	  iArray[5]=new Array();
      iArray[5][0]="������";         		    //����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="������ˮ��";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      //PolGrid.canChk = 0;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;     
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


//����¼
function initErrGrid()
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
      iArray[1][0]="¼����Ŀ";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();             
      iArray[2][0]="¼������";         	
      iArray[2][1]="100px";            	
      iArray[2][2]=60;            		
      iArray[2][3]=0;    

      iArray[3]=new Array();
      iArray[3][0]="����Ա����";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=180;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
       
      iArray[4]=new Array();
      iArray[4][0]="����Ա����";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0; 

    ErrGrid = new MulLineEnter( "fm" , "ErrGrid" );
    ErrGrid.mulLineCount = 0;
    ErrGrid.displayTitle = 1;
    ErrGrid.hiddenPlus = 1;
    ErrGrid.hiddenSubtraction = 1; 
    ErrGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("��ʼ��initErrGrid����");
  }
}

</script>
