<%
/***************************************************************
 * <p>ProName��LCBalanceOnInit.jsp</p>
 * <p>Title�����ڽ���ά��</p>
 * <p>Description�����ڽ���ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-05
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
		queryBalanceInfo();		
		
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
		fm.GrpPropNo.value=tGrpPropNo;
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if(tFlag=='0'){
			fm.BalanceSaveButton.style.display='';
		}else if(tFlag=='1' ||tFlag=='3' ){
			fm.BalanceSaveButton.style.display='none';
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
 
