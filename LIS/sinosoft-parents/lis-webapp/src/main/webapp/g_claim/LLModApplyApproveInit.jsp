<%
/***************************************************************
 * <p>ProName��LLModApplyCheckInit.jsp</p>
 * <p>Title���ܹ�˾���������</p>
 * <p>Description���ܹ�˾���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ����
 * @version  : 8.0
 * @date     : 2015-03-11
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
		
		initQueryModItemGrid();
		initAcceptGrpGrid();
		initAcceptGrpaGrid();

		
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
//�����޸��б�
function initQueryModItemGrid() {     

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸�ԭ�����";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸�ԭ��";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������ͱ���";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���ֱ���";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="130px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���ϵ����������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���ϵ�����������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���ϵ�������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸���Ч����";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸���Чֹ��";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������ԭ��";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="115px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������κ�";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���˽��۱���";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���˽�������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
				
		QueryModItemGrid = new MulLineEnter("fm", "QueryModItemGrid");
		QueryModItemGrid.mulLineCount = 0;
		QueryModItemGrid.displayTitle = 1;
    	QueryModItemGrid.locked = 0;
		QueryModItemGrid.canSel = 1;
		QueryModItemGrid.hiddenPlus=1;
		QueryModItemGrid.hiddenSubtraction=1;
		QueryModItemGrid.selBoxEventFuncName = "showClmModApplyInfo";
		QueryModItemGrid.loadMulLine(iArray);
	} catch(ex) {
  		alert("��ʼ���������!");
	}  
}

//��ѡ�񱣵���Ϣ�б�
function initAcceptGrpGrid(){
        
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�б�����";
		iArray[i][1]="80px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="150px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ч����";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
	    iArray[i]=new Array();
		iArray[i][0]="������Чֹ��";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������κ�";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		AcceptGrpGrid = new MulLineEnter("fm", "AcceptGrpGrid");
		AcceptGrpGrid.mulLineCount = 0;
		AcceptGrpGrid.displayTitle = 1;
		AcceptGrpGrid.locked = 0;
		AcceptGrpGrid.canChk = 0;		
		AcceptGrpGrid.hiddenPlus=1;
		AcceptGrpGrid.hiddenSubtraction=1;
		AcceptGrpGrid.selBoxEventFuncName = "";
		AcceptGrpGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}



//��ѡ�񱣵�������Ϣ�б�
function initAcceptGrpaGrid(){
        
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�б�����";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="100px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ч����";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="100px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="150px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Чֹ��";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������κ�";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		AcceptGrpaGrid = new MulLineEnter("fm", "AcceptGrpaGrid");
		AcceptGrpaGrid.mulLineCount = 0;
		AcceptGrpaGrid.displayTitle = 1;
		AcceptGrpaGrid.locked = 0;
		AcceptGrpaGrid.canSel = 0;
		AcceptGrpaGrid.canChk = 0;
		AcceptGrpaGrid.hiddenPlus=1;		
		AcceptGrpaGrid.hiddenSubtraction=1;
		AcceptGrpaGrid.selBoxEventFuncName = "";
		AcceptGrpaGrid.loadMulLine(iArray);
		
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}


</script>
