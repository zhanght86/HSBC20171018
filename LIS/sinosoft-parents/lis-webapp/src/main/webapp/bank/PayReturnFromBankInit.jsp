<%
//�������ƣ�PayReturnFromBankInit.jsp
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
  	initBankGrid();
  	initQuery();
  }
  catch(re) {
    alert("InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
var BankGrid;
function initBankGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���κ�";         		//����
    iArray[1][1]="60px";            	//�п�
    iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="���б���";         		//����
    iArray[2][1]="25px";            	//�п�
    iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][4]="bank";  
    
    iArray[3]=new Array();
    iArray[3][0]="�����ļ�����";         		//����
    iArray[3][1]="110px";            	//�п�
    iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="��������";         		//����
    iArray[4][1]="35px";            	//�п�
    iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.loadMulLine(iArray);  
    
    //��Щ����������loadMulLine����
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
