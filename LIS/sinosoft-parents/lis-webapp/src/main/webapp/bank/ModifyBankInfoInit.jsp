<%
//�������ƣ�ModifyBankInfoInit.jsp
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
	//document.all('PrtNo2').value = '';
	document.all('TempfeeNo').value = '';
	document.all('TempfeeNo1').value = '';
	document.all('BankCode').value = '';
	document.all('AccName').value = '';
	document.all('AccNo').value = '';
	//document.all('PayMode').value = '';
	//document.all('GetNoticeNo2').value = '';
	//document.all('PayModeName').value = '';
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
    iArray[1][0]="�ݽ��Ѻ�";         		//����
    iArray[1][1]="80px";            	//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="����֪ͨ���";         		//����
    iArray[2][1]="100px";            	//�п�
    iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="���ѷ�ʽ";         		//����
    iArray[3][1]="40px";            	//�п�
    iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][10]="PayMode";
    iArray[3][11]="0|^1|�ֽ�|^2|�ֽ�֧Ʊ|^3|ת��֧Ʊ^4|����ת��^5|�ڲ�ת��";
    
    iArray[4]=new Array();
    iArray[4][0]="���ѽ��";         		//����
    iArray[4][1]="50px";            	//�п�
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="���д���";         		//����
    iArray[5][1]="50px";            	//�п�
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�����˺�";         		//����
    iArray[6][1]="100px";            	//�п�
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�˻�����";         		//����
    iArray[7][1]="50px";            	//�п�
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BankGrid.mulLineCount = 1;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    //BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    //BankGrid.selBoxEventFuncName = "showStatistics";
    BankGrid.loadMulLine(iArray);  
    
    //��Щ����������loadMulLine����
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
