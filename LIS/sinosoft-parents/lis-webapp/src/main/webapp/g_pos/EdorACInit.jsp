<%
/***************************************************************
 * <p>ProName:EdorACInit.jsp</p>
 * <p>Title:  Ͷ�������ϱ��</p>
 * <p>Description:Ͷ�������ϱ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
%>

<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		initParam();
		initButton();
		initInpBox();
		if("02"==tContType){
			queryAppInfo();
		}else {
			initIDInfoGrid();
			initTooContectGrid();
			queryOldClick();
		}

	} catch (re) {
		alert("��ʼ���������!");
	}
}


/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorACSql");
		tSQLInfo.setSqlId("EdorACSql23");
		tSQLInfo.addSubPara(tGrpContNo);
		
		var tResContType = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tResContType!=null && tResContType[0][0]!=""){
			tContType = tResContType[0][0];
		}
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
		
		if(tActivityID=='1800401002'){
			if("02"==tContType){
				divGrpInfo.style.display='none';
				divAppInfo.style.display='';
				divButton01.style.display='none';
				divButton03.style.display='';
			} else {
				divGrpInfo.style.display='';
				divAppInfo.style.display='none';
				divButton01.style.display='';
				divButton03.style.display='none';
			}
			divButton02.style.display='none';
		} else {
			if("02"==tContType){
				divGrpInfo.style.display='none';
				divAppInfo.style.display='';
			} else {
				divGrpInfo.style.display='';
				divAppInfo.style.display='none';
			}
			divButton01.style.display='none';
			divButton02.style.display='';
			divButton03.style.display='none';
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
//��λ֤��
function initIDInfoGrid() {
	
	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";  
		iArray[i][9]="֤�����ͱ���|notnull&code:gidtype";
		iArray[i++][19]=1;
		
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����Ч����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Ч����|date";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����Чֹ��";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Чֹ��|date";
		
		IDInfoGrid = new MulLineEnter("fm", "IDInfoGrid");
		IDInfoGrid.mulLineCount = 0;
		IDInfoGrid.displayTitle = 1;
		IDInfoGrid.locked = 0;
		IDInfoGrid.canSel = 0;
		IDInfoGrid.canChk = 0;
		IDInfoGrid.hiddenSubtraction = 0;
		IDInfoGrid.hiddenPlus = 0;
		IDInfoGrid.selBoxEventFuncName = "";
		IDInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initTooContectGrid() {

	turnPage6 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ������";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ�˵绰";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ�˵绰|notnull&PHONE";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��EMAIL";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ��EMAIL|EMAIL";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ���ֻ���";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ���ֻ���|num&len<=11";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ�˲���";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��֤������";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5]="6";
		iArray[i][6]="0"; 
		iArray[i++][19]=1; 
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��֤������";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|num&len<=18";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��֤����Чֹ��";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Чֹ��|date";
		
		TooContectGrid = new MulLineEnter("fm", "TooContectGrid");
		TooContectGrid.mulLineCount = 0;
		TooContectGrid.displayTitle = 1;
		TooContectGrid.locked = 0;
		TooContectGrid.canSel = 0;
		TooContectGrid.canChk = 0;
		TooContectGrid.hiddenSubtraction = 0;
		TooContectGrid.hiddenPlus = 0;
		TooContectGrid.selBoxEventFuncName = "";
		TooContectGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
