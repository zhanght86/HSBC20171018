<%
/***************************************************************
 * <p>ProName��LLClaimNoMedicalInit.jsp</p>
 * <p>Title����ҽ���˵���ʼ��ҳ��</p>
 * <p>Description����ҽ���˵���ʼ��ҳ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-05-01
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

		initMaimInfoGrid();
		initMajorBillGrid();
		QueryDefoInfo();
		QueryMajorInfo();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		fm.tRgtNo.value=tRgtNo;
		fm.tCustomerNo.value=tCustomerNo;
		fm.tCaseNo.value=tCaseNo;
		
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
		if(tMode=="0"){
		
			document.getElementById("addMaimBill1").style.display="";
			document.getElementById("modifyMaimBill1").style.display="";
			document.getElementById("deleteMaimBill1").style.display="";
			document.getElementById("resetDefoInfo1").style.display="";
					 
			document.getElementById("addMajorBill1").style.display="";
			document.getElementById("modifyMajorBill1").style.display="";
			document.getElementById("deleteMajorBill1").style.display="";
			document.getElementById("resetMajorInfo1").style.display="";
		
		}else if(tMode=="1"){
			
			document.getElementById("addMaimBill1").style.display="none";
			document.getElementById("modifyMaimBill1").style.display="none";
			document.getElementById("deleteMaimBill1").style.display="none";
			document.getElementById("resetDefoInfo1").style.display="none";
					 
			document.getElementById("addMajorBill1").style.display="none";
			document.getElementById("modifyMajorBill1").style.display="none";
			document.getElementById("deleteMajorBill1").style.display="none";
			document.getElementById("resetMajorInfo1").style.display="none";
		
		}
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
// ��ʼ���˲��˵�
function initMaimInfoGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˲���ˮ��";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�˲����ͱ���";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�˲�����";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˲д������";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
	
		iArray[i]=new Array();
		iArray[i][0]="�˲д�������";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
	
		iArray[i]=new Array();
		iArray[i][0]="�˲м������";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
	
		iArray[i]=new Array();
		iArray[i][0]="�˲м�������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˲д���";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˲д�������";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�м���������";
		iArray[i][1]="70px";
		iArray[i][2]=300;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
    
    MaimInfoGrid = new MulLineEnter( "fm" , "MaimInfoGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    MaimInfoGrid.mulLineCount = 0;   
    MaimInfoGrid.displayTitle = 1;
    MaimInfoGrid.locked = 0;
    MaimInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    MaimInfoGrid.hiddenPlus=1;
    MaimInfoGrid.hiddenSubtraction=1;
    MaimInfoGrid.selBoxEventFuncName ="getDefoInfo"; //��Ӧ�ĺ���������������
    MaimInfoGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
// ��ʼ���˲��˵�
function initMajorBillGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ؼ���ˮ��";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�ؼ����ʹ���";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�ؼ�����";
		iArray[i][1]="60px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ؼ�����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ؼ�����";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="ҽ�ƻ���";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
				
		iArray[i]=new Array();
		iArray[i][0]="ȷ��ʱ��";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
    
    MajorBillGrid = new MulLineEnter( "fm" , "MajorBillGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    MajorBillGrid.mulLineCount = 1;   
    MajorBillGrid.displayTitle = 1;
    MajorBillGrid.locked = 0;
    MajorBillGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    MajorBillGrid.hiddenPlus=1;
    MajorBillGrid.hiddenSubtraction=1;
    MajorBillGrid.selBoxEventFuncName ="getMajorInfo"; //��Ӧ�ĺ���������������
    MajorBillGrid.loadMulLine(iArray);
     
}
    catch(ex){
        alert("ex");
    }
}
</script>
