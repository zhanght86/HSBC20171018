<%
//�������ƣ�WriteToFileInit.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
var filePath;
function initInpBox() {
//  var strSql = "select SysVarValue from LDSysVar where SysVar = 'DisplayBankFilePath'";
  
  var sqlid4="WriteToFileInputSql4";
  var mySql4=new SqlClass();
  mySql4.setResourceName("bank.WriteToFileInputSql");
  mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
  var strSql = mySql4.getString();
  filePath = easyExecSql(strSql);
  document.all('Url').value = filePath;
  
  document.all('fmtransact').value = "";
} 

function initForm() {
  try {
  	getFilePath();
  	
  	//initInpBox();
  	
  	initBankGrid();
  	
    initBankFileDealDiv();
    //alert('@@@');
  	easyQueryClick();
  	//alert('@@@1');
  } catch(re) {
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
    iArray[1][0]="���κ�";         		//����
    iArray[1][1]="200px";            	//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ļ���";         		//����
    iArray[2][1]="200px";            	//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.selBoxEventFuncName = "showStatistics";
    BankGrid.loadMulLine(iArray);  
    
    //��Щ����������loadMulLine����
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
