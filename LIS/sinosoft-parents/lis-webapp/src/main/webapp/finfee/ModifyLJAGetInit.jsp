<%
//�������ƣ�ModifyLJAGetInit.jsp
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
  }
  catch(re) {
    alert("InitForm �����з����쳣:��ʼ���������!");
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
    iArray[1][0]="ʵ��֪ͨ���";         		//����
    iArray[1][1]="100px";            	//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="��������";         		//����
    iArray[2][1]="100px";            	//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="Ͷ����";         		//����
    iArray[3][1]="40px";            	//�п�
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="���ʽ";         		//����
    iArray[4][1]="40px";            	//�п�
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="���ѽ��";         		//����
    iArray[5][1]="40px";            	//�п�
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="���д���";         		//����
    iArray[6][1]="40px";            	//�п�
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�����˺�";         		//����
    iArray[7][1]="100px";            	//�п�
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array(); 
    iArray[8][0]="�˻�����";         		//����
    iArray[8][1]="40px";            	//�п�
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="֤������";         		//����
    iArray[9][1]="40px";            	//�п�
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array(); 
    iArray[10][0]="֤������";         		//����
    iArray[10][1]="100px";            	//�п�
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.selBoxEventFuncName = "showOne";
    BankGrid.loadMulLine(iArray);  
    
    //��Щ����������loadMulLine����
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
