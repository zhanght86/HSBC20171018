<%
/***************************************************************
 * <p>ProName��LLClaimMajorInit.jsp</p>
 * <p>Title���ش󰸼��ϱ�/¼��</p>
 * <p>Description���ش󰸼��ϱ�/¼��</p>
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
		
		initPageInfo();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
		fm.RptNo.value = mRptNo;
		fm.CustomerNo.value = mCustomerNo;
		fm.InputOperator.value = mOperator;
		fm.InputDate.value = mCurrentDate;
		fm.InputCom.value = mManageCom;
		fm.InputComName.value = "";
		fm.InputRemarks.value = "";					
		
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
		
		//�ж��ش󰸼��ϱ��Ƿ�����
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql2");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		var tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr!=null && tArr.length>0) {
			fm.printCase.style.display = "";
		} else {
			fm.printCase.style.display = "none";
		}
		if (mMode!=null && mMode=="3") {
			fm.reportMajor.style.display = "none";			
		} else if(mMode=="0") {			
			fm.reportMajor.style.display = "";			
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
</script>
