<%
/***************************************************************
 * <p>ProName��LLClaimCommendHospitalInit.jsp</p>
 * <p>Title��ҽԺ����ά��</p>
 * <p>Description��ҽԺ����ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
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
		
		initLLCommendHospitalGrid();
		
	} catch (re) {
		alert("��ʼ���������");
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
		
		fm.editHospitalButton.disabled=true;
		fm.deleteHospitalButton.disabled=true;
	
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

function initLLCommendHospitalGrid() {

	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i=0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=30;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҽԺ����";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҽԺ����";
		iArray[i][1]="200px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҽԺ�ȼ�����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="ҽԺ�ȼ�";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="ҽԺ״̬����";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=3;

		iArray[i]=new Array();
		iArray[i][0]="ҽԺ״̬";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҽԺ�绰";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���ڵ�ַ";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
    LLCommendHospitalGrid = new MulLineEnter("fm","LLCommendHospitalGrid");
    LLCommendHospitalGrid.mulLineCount = 0;
    LLCommendHospitalGrid.displayTitle = 1;
    LLCommendHospitalGrid.locked = 0;
    LLCommendHospitalGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
    LLCommendHospitalGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
    LLCommendHospitalGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
    LLCommendHospitalGrid.selBoxEventFuncName = "LLCommendHospitalGridClick"; //��������
    LLCommendHospitalGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
      alert(ex);
  }
}
</script>
