<%
/***************************************************************
 * <p>ProName��LLClaimHandAppInit.jsp</p>
 * <p>Title��������ת������ҳ��</p>
 * <p>Description��������ת������ҳ��</p>
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

		initHandNoListGrid();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		initQueryInfo();//��ʼ����ѯ����
		initPageNoInfo();//��ʼ��������ת��Ϣ
		
	} catch (re) {
		alert("��ʼ����������");
	}
}
/**
 * ��ʼ����ѯ����
 */
function initQueryInfo() {
	fm.QueryPageNo.value = "";
	fm.AppOperator.value = mOperator;
	fm.ManageCom.value = "";
	fm.AppStartDate.value = "";
	fm.AppEndDate.value = "";	
}
/**
 * ��ʼ��������ת��Ϣ
 */
function initPageNoInfo() {
	fm.PageNo.value = "";
	fm.SumNum.value = "";
	fm.Remark.value = "";
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
// ������ת����Ϣ�б�
function initHandNoListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="������ת��";             
    iArray[i][1]="120px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="�ܹ�������"; 
    iArray[i][1]="60px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�ѹ�������"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    HandNoListGrid = new MulLineEnter( "fm" , "HandNoListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    HandNoListGrid.mulLineCount = 0;   
    HandNoListGrid.displayTitle = 1;
    HandNoListGrid.locked = 0;
    HandNoListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    HandNoListGrid.selBoxEventFuncName ="selectPageNo"; //��Ӧ�ĺ���������������    
    HandNoListGrid.hiddenPlus=1;
    HandNoListGrid.hiddenSubtraction=1;
    HandNoListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->HandNoListGrid");
    }
}
</script>
