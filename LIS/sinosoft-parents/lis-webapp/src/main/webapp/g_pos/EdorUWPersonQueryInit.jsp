<%
/***************************************************************
 * <p>ProName��EdorUWPersonQueryInit.jsp</p>
 * <p>Title����Ա�ֲ�</p>
 * <p>Description����Ա�ֲ�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		initParam();
		initInpBox();
		initButton();
		initYearDistributionGrid();
		queryInfo();
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ���������
 */
function initOtherParam() {

	try {
	} catch (ex) {
	}
}


/**
 * ��ʼ������
 */
function initParam() {
	
	try {	
	} catch (re) {	
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�
 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}


/**
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
	
		string = "";
	}
	
	return string;
}


function initYearDistributionGrid(){

	var iArray = new Array();
	var i = 0;
	try{
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����(��)";
		iArray[i][1]="50px";
		iArray[i][2]=80; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ƽ������(��)";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ů����";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		 iArray[i]=new Array();
		iArray[i][0]="��߱���(Ԫ)";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���籣����";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="50px";
		iArray[i][2]=90;
		iArray[i++][3]=0;
		
		YearDistributionGrid = new MulLineEnter( "fm" , "YearDistributionGrid" );
		YearDistributionGrid.mulLineCount = 0;
		YearDistributionGrid.displayTitle = 1;
		YearDistributionGrid.hiddenPlus = 1;
		YearDistributionGrid.hiddenSubtraction = 1;
		YearDistributionGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
