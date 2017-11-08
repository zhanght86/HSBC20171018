<%
/***************************************************************
 * <p>ProName��LDQuestionInit.jsp</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-04
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
		initQuestionGrid();
		queryClick("2");
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
		
		if (tShowStyle=="1") {
			
			divQuesType.style.display = "none";
		} else if (tShowStyle=="2"){
			
			divQuesType.style.display = "";
		}
		
		fm.QuesType.value = "";
		fm.QuesTypeName.value = "";
		fm.SendContent.value = "";
		fm.ReplyContent.value = "";
		divReplyContent.style.display = "none";
		//add by JingDian �µ���ȫ��û�����������չʾ����
		if("NB"==tOtherNoType || "POS"==tOtherNoType){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_busicommon.LDQuestiontSql");
			tSQLInfo.setSqlId("LDQuestiontSql3");
			tSQLInfo.addSubPara(tActivityID);
			var tResArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tResArr==null || tResArr[0][0]=="") {
				tNBPOSFlag = "1";
			}
			divMistake.style.display = "";
			fm.Mistake.value = "0";
			fm.Mistake.checked = false;
		}else {
			fm.Mistake.value = "";
			fm.Mistake.checked = false;
		}
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
		if (tShowStyle=="1") {
			
			fm.AddButton.style.display = "none";
			fm.ShowButton.style.display = "";
		} else if (tShowStyle=="2"){
			
			fm.AddButton.style.display = "";
			fm.ShowButton.style.display = "none";
		} else if (tShowStyle=="3"){
			
			fm.AddButton.style.display = "";
			fm.ShowButton.style.display = "none";
		}
		//add by JingDian �µ���ȫ��û�����������չʾ����
		if(tNBPOSFlag=="1"){
			fm.AddButton.style.display = "none";
		}
		
		//add by ZhangC 20141216 ����ѯ�۲�ѯʱ�������ģ�鰴ť��չʾ
		if (tShowFlag =="1") {
			
			fm.AddButton.style.display = "none";
			fm.ShowButton.style.display = "";
		} 
		
		fm.ModifyButton.style.display = "none";
		fm.DeleteButton.style.display = "none";
		fm.ReplyButton.style.display = "none";
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

/**
 * ��ʼ���б�
 */
function initQuestionGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tShowStyle=="1") {
			
			iArray[i] = new Array();
			iArray[i][0] = "���������";
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tShowStyle=="2"){
			
			iArray[i] = new Array();
			iArray[i][0] = "���������";
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ظ�����";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����״̬����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����״̬";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ͽڵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ͽڵ�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ظ��ڵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ظ��ڵ�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ظ�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if("NB"==tOtherNoType || "POS"==tOtherNoType){
			iArray[i] = new Array();
			iArray[i][0] = "������";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ظ���";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else {
			iArray[i] = new Array();
			iArray[i][0] = "������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ظ���";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		iArray[i] = new Array();
		iArray[i][0] = "��֤ϸ�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��֤ϸ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		QuestionGrid = new MulLineEnter("fm", "QuestionGrid");
		QuestionGrid.mulLineCount = 0;
		QuestionGrid.displayTitle = 1;
		QuestionGrid.locked = 0;
		QuestionGrid.canSel = 1;
		QuestionGrid.canChk = 0;
		QuestionGrid.hiddenPlus = 1;
		QuestionGrid.hiddenSubtraction = 1;
		QuestionGrid.selBoxEventFuncName = "showDetail";
		QuestionGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��QuestionGridʱ����"+ ex);
	}
}
</script>
