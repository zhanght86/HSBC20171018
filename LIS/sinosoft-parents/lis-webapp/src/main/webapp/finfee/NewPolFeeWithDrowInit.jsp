
<%
	//�������ƣ�ConFeeInit.jsp
	//�����ܣ�
	//�������ڣ�2002-08-16 15:39:06
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput globalInput = (GlobalInput) session.getValue("GI");
	if (globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('PolNo').value = '';
    document.all('LeavingMoney').value = '';
  }
  catch(ex)
  {
    alert("��ConFeeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                   

function initForm()
{
  try
  {
    initInpBox();
    initBankGrid();  
  }
  catch(re)
  {
    alert("ConFeeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
    iArray[1][0]="���ֺ���";         		//����
    iArray[1][1]="100px";            	//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";         		//����
    iArray[2][1]="40px";            	//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�������";         		//����
    iArray[3][1]="60px";            	//�п�
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         		//����
    iArray[4][1]="60px";            	//�п�
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��������";         		//����
    iArray[5][1]="60px";            	//�п�
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="ǩ������";         		//����
    iArray[6][1]="50px";            	//�п�
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="����λ��";         		//����
    iArray[7][1]="40px";            	//�п�
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.selBoxEventFuncName = "showOne";    
    BankGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
