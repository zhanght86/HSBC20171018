<%
//�������ƣ�TempFeeWithdrawInit.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
}                                     

function initForm() {
  try {
  	initInpBox();
  	initFeeGrid();
  }
  catch(re) {
    alert("TempFeeWithdrawInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
var FeeGrid;
function initFeeGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�˷�ԭ��";         		//����
    iArray[1][1]="120px";            		//�п�
    iArray[1][3]= 2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="bz_cancel";            

    iArray[2]=new Array();
    iArray[2][0]="�ݽ����վݺ���";      //����
    iArray[2][1]="120px";            		//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";                //����
    iArray[3][1]="60px";            		//�п�
    iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][4]="tempfeetype";     

    //iArray[4]=new Array();
    //iArray[4][0]="���ֱ���";         		//����
    //iArray[4][1]="40px";            		//�п�
    //iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="Ͷ��������";         		//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="Ͷ����ӡˢ��";         		//����
    iArray[5][1]="100px";            		//�п�
    iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="���ѽ��";         			//����
    iArray[6][1]="100px";            		//�п�
    iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="��������";         		//����
    iArray[7][1]="100px";            		//�п�
    iArray[7][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="��������";         		//����
    iArray[8][1]="100px";            		//�п�
    iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="�˷ѽ��";         		//����
    iArray[9][1]="100px";            		//�п�
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="������������";         		//����
    iArray[10][1]="0px";            		//�п�
    iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    FeeGrid = new MulLineEnter( "fm" , "FeeGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    FeeGrid.mulLineCount = 5;   
    FeeGrid.displayTitle = 1;
    FeeGrid.hiddenPlus = 1;
    FeeGrid.hiddenSubtraction = 1;
    FeeGrid.canChk = 1;
    FeeGrid.loadMulLine(iArray);  
    
    //��Щ����������loadMulLine����
    //FeeGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
