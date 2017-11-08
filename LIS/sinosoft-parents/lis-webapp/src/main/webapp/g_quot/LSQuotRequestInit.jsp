<%
/***************************************************************
 * <p>ProName��LSQuotRequestInit.jsp</p>
 * <p>Title��ҵ������</p>
 * <p>Description��ҵ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<script language="JavaScript">
var tSubUWFlag = 0;//�Ƿ񾭹���֧��˾�˱���ǣ�0-��1-��

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
		tSQLInfo.setSqlId("LSQuotUWSql20");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			tSubUWFlag = 1;
		}
		
		initRequestGrid();
		queryClick();
		
		if (tActivityID == "0800100001") {
			
			divSubUWOpinion.style.display = "none";
			divBranchUWOpinion.style.display = "none";
			
			fm.AddButton.style.display = "";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
			fm.SaveButton.style.display = "none";
		} else if (tActivityID == "0800100002") {
			
			divSubUWOpinion.style.display = "";
			divBranchUWOpinion.style.display = "none";
			
			fm.RequestType.disabled = true;
			fm.RequestTypeName.disabled = true;
			fm.OtherTypeDesc.disabled = true;
			fm.RiskCode.disabled = true;
			fm.RiskName.disabled = true;
			fm.RequestDesc.disabled = true;
			
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.SaveButton.style.display = "";
		} else if (tActivityID == "0800100003") {
			
			if (tSubUWFlag==1) {
				divSubUWOpinion.style.display = "";
			} else {
				divSubUWOpinion.style.display = "none";
			}
			divBranchUWOpinion.style.display = "";
			
			fm.RequestType.disabled = true;
			fm.RequestTypeName.disabled = true;
			fm.OtherTypeDesc.disabled = true;
			fm.RiskCode.disabled = true;
			fm.RiskName.disabled = true;
			fm.RequestDesc.disabled = true;
			fm.SubUWOpinion.disabled = true;
			
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.SaveButton.style.display = "";
		} else {
			
			if (tSubUWFlag==1) {
				divSubUWOpinion.style.display = "";
			} else {
				divSubUWOpinion.style.display = "none";
			}
			divBranchUWOpinion.style.display = "";
			
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.SaveButton.style.display = "none";
		}
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
		
		divOtherTypeDescTitle.style.display = "none";
		divOtherTypeDescInput.style.display = "none";
		divRiskTitle.style.display = "none";
		divRiskInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "";
		divTD4.style.display = "";
		
		fm.RequestType.value = "";
		fm.RequestTypeName.value = "";
		fm.OtherTypeDesc.value = "";
		fm.RiskCode.value = "";
		fm.RiskName.value = "";
		fm.RequestDesc.value = "";
		fm.SubUWOpinion.value = "";
		fm.BranchUWOpinion.value = "";
		
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

/**
 * ��ʼ���б�
 */
function initRequestGrid() {
	
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
		iArray[i][0] = "ҵ��������ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ������������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ���������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ִ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ����������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		if (tActivityID == "0800100001") {
			
			iArray[i] = new Array();
			iArray[i][0] = "��֧��˾�˱����";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱����";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tActivityID == "0800100002") {
			
			iArray[i] = new Array();
			iArray[i][0] = "��֧��˾�˱����";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱����";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			if (tSubUWFlag==1) {
				
				iArray[i] = new Array();
				iArray[i][0] = "��֧��˾�˱����";
				iArray[i][1] = "60px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			} else {
				
				iArray[i] = new Array();
				iArray[i][0] = "��֧��˾�˱����";
				iArray[i][1] = "60px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱����";
			iArray[i][1] = "60px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		RequestGrid = new MulLineEnter("fm", "RequestGrid");
		RequestGrid.mulLineCount = 0;
		RequestGrid.displayTitle = 1;
		RequestGrid.locked = 0;
		RequestGrid.canSel = 1;
		RequestGrid.canChk = 0;
		RequestGrid.hiddenPlus = 1;
		RequestGrid.hiddenSubtraction = 1;
		RequestGrid.selBoxEventFuncName = "showDetail";
		RequestGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��RequestGridʱ����"+ ex);
	}
}
</script>
