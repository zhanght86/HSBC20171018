<%
/***************************************************************
 * <p>ProName:EdorREInput.jsp</p>
 * <p>Title:  ������Ч</p>
 * <p>Description:������Ч</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-24
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
		
	} catch (re) {
		alert("��ʼ���������!");
	}
}


/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		tContPlanType = getContPlanType(tGrpContNo);
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�
 */
function initInpBox() {
	
	try {
		if("01"==tContPlanType){
			fm.REtype.value="32";
			queryInfoClick();
			queryEdorValDate();
		} else if("00"==tContPlanType){
			fm.REtype.value="31";
			queryClick();
		}
		auotContShowCodeName("surrender",fm.REtype.value,fm,"REtypeName");
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
			divButton01.style.display=''
		}else {
			divButton01.style.display='none'
		}
		if("01"==tContPlanType){
			tdGetMoneyName.style.display='none';
			tdGetMoney.style.display='none';
			tdDaysName.style.display='';
			tdDays.style.display='';
			tdInterestName.style.display='none';
			tdInterest.style.display='none';
			tdValDateName.style.display='';
			tdValDate.style.display='';
		} else if("00"==tContPlanType){
			tdGetMoneyName.style.display='';
			tdGetMoney.style.display='';
			tdDaysName.style.display='none';
			tdDays.style.display='none';
			tdInterestName.style.display='';
			tdInterest.style.display='';
			tdValDateName.style.display='none';
			tdValDate.style.display='none';
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
