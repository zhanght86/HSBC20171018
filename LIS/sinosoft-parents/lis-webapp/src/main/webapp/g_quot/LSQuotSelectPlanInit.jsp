<%
/***************************************************************
 * <p>ProName��LSQuotSelectPlanInit.js</p>
 * <p>Title��ѡ�񱨼۷���</p>
 * <p>Description��ѡ�񱨼۷���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-19
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
		initNoPlanCombiGrid();
		initQuotPlanGrid();
		initBJQuotPlanGrid();
		
		queryNoPlanCombi();
		queryQuotPlan();
		queryBJQuotPlan();
		
		initFixedValue();//��Ŀѯ�۽����ճ�ʼ����չʾ
		
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
		
		if (tQuotQuery == "Y") {//��ѯ�������ʱ���������Ѵ�ӡ��δ��ӡ�������ذ�ť
		
			document.all("divBindPlan").style.display = "none";
			document.all("divFixedValue").style.display = "none";
			fm.SelectButton.style.display = "none";
			fm.DelButton.style.display = "none";
		
		} else {
		
			var tPrintState = getPrintState();
			if (tPrintState!=null && tPrintState=="-1") {//δ��ӡ
				
				//����Ʒ����Ϊ��ͨ���֡��������ֿ��Խ�������
				if (tTranProdType=="00"|| tTranProdType=="03") {
					document.all("divBindPlan").style.display = "";		
				} else {
					document.all("divBindPlan").style.display = "none";	
				}
				fm.SelectButton.style.display = "";
				fm.DelButton.style.display = "";	
			} else {//�Ѵ�ӡ
			
				document.all("divBindPlan").style.display = "none";
				document.all("divFixedValue").style.display = "none";
				fm.SelectButton.style.display = "none";
				fm.DelButton.style.display = "none";	
			}
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

/**
 * ������ķ������
 */
function initNoPlanCombiGrid() {
	
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
		iArray[i][0] = "���1";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ʒ�Χ";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		NoPlanCombiGrid = new MulLineEnter("fm", "NoPlanCombiGrid");
		NoPlanCombiGrid.mulLineCount = 0;
		NoPlanCombiGrid.displayTitle = 1;
		NoPlanCombiGrid.locked = 1;
		NoPlanCombiGrid.canSel = 0;
		NoPlanCombiGrid.canChk = 0;
		NoPlanCombiGrid.hiddenPlus = 1;
		NoPlanCombiGrid.hiddenSubtraction = 1;
		//NoPlanCombiGrid.selBoxEventFuncName = "";
		NoPlanCombiGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}

/**
 * ѯ�۷���
 */
function initQuotPlanGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ������(��)";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ����н(Ԫ)";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ";
		if (tTranProdType=='01') {//ֻ�н�����ʱչʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='01') {//ֻ�н�����ʱչʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {//������Ҳ����չʾ
			iArray[i][1] = "10px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�μ��籣ռ��";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ů����";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tQuotType=='01') {//��Ŀѯ��  
		
			iArray[i] = new Array();
			iArray[i][0] = "��͹������/��͹������";
			if (tTranProdType=='01') {
				iArray[i][1] = "18px";
			} else {
				iArray[i][1] = "0px";
			}
			iArray[i][2] = 300;
			if (tTranProdType=='01') {
				iArray[i++][3] = 0;
			} else {
				iArray[i++][3] = 3;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "��߹������/��߹������";
			if (tTranProdType=='01') {
				iArray[i][1] = "18px";
			} else {
				iArray[i][1] = "0px";
			}
			iArray[i][2] = 300;
			if (tTranProdType=='01') {
				iArray[i++][3] = 0;
			} else {
				iArray[i++][3] = 3;
			}
		} 
		
		if (tQuotType=='00') {//һ��ѯ��
			
			iArray[i] = new Array();
			iArray[i][0] = "��͹������/��͹������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�������/�������";
			if (tTranProdType=='01') {
				iArray[i][1] = "18px";
			} else {
				iArray[i][1] = "0px";
			}
			iArray[i][2] = 300;
			if (tTranProdType=='01') {
				iArray[i++][3] = 0;
			} else {
				iArray[i++][3] = 3;
			}
		} 
		
		QuotPlanGrid = new MulLineEnter("fm", "QuotPlanGrid");
		QuotPlanGrid.mulLineCount = 0;
		QuotPlanGrid.displayTitle = 1;
		QuotPlanGrid.locked = 1;
		QuotPlanGrid.canSel = 0;
		QuotPlanGrid.canChk = 1;
		QuotPlanGrid.hiddenPlus = 1;
		QuotPlanGrid.hiddenSubtraction = 1;
		//QuotPlanGrid.selBoxEventFuncName = "";
		QuotPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}

/**
 * ������ѡ����
 */
function initBJQuotPlanGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���۵���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ������(��)";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ����н(Ԫ)";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ";
		if (tTranProdType=='01') {//ֻ�н�����ʱչʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='01') {//ֻ�н�����ʱչʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {//������Ҳ����չʾ
			iArray[i][1] = "10px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�μ��籣ռ��";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ů����";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�������/�������";
		if (tTranProdType=='01') {
			iArray[i][1] = "18px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='01') {
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		BJQuotPlanGrid = new MulLineEnter("fm", "BJQuotPlanGrid");
		BJQuotPlanGrid.mulLineCount = 0;
		BJQuotPlanGrid.displayTitle = 1;
		BJQuotPlanGrid.locked = 1;
		BJQuotPlanGrid.canSel = 0;
		BJQuotPlanGrid.canChk = 1;
		BJQuotPlanGrid.hiddenPlus = 1;
		BJQuotPlanGrid.hiddenSubtraction = 1;
		//BJQuotPlanGrid.selBoxEventFuncName = "";
		BJQuotPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}
</script>
