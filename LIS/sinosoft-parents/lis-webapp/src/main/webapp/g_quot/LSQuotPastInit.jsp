<%
/***************************************************************
 * <p>ProName��LSQuotPastInit.jsp</p>
 * <p>Title��������Ϣ</p>
 * <p>Description��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
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
		initPastTotalGrid();
		initPastDetailGrid();
		initPersonUWGrid();
		divPastDetailTitle.style.display = "none";
		queryTotal();
		queryPersonUW();
		divRemark.style.display = "none";
		
		if (tFlag=="Quesnaire") {
			
			var menu=document.getElementById("tab1").getElementsByTagName("li");
		
			for(i=0;i<2;i++)
			{
			   menu[i].className=i==1?"now":""; 
				if (i==1) {
					document.getElementById("tablistdiv"+i).style.display = "block";
				} else {
					document.getElementById("tablistdiv"+i).style.display = "none";
				}
			}
		}
		
		if (tActivityID == "0800100001") {
			
			fm.PastTotalAddButton.style.display = "";
			fm.PastTotalModifyButton.style.display = "";
			fm.PastTotalDeleteButton.style.display = "";
			fm.PastDetailAddButton.style.display = "";
			fm.PastDetailModifyButton.style.display = "";
			fm.PastDetailDeleteButton.style.display = "";
			
			fm2.PerUWAddButton.style.display = "";
			fm2.PerUWModifyButton.style.display = "";
			fm2.PerUWDeleteButton.style.display = "";
			fm2.PerUWSaveButton.style.display = "none";
			
			divUWOpinion.style.display = "none";
			divUWDesc.style.display = "none";
			divAttachmentUpload.style.display = "";
			
		} else if (tActivityID == "0800100002" || tActivityID == "0800100003" || tActivityID == "0800100004") {
			
			fm.PastTotalAddButton.style.display = "none";
			fm.PastTotalModifyButton.style.display = "none";
			fm.PastTotalDeleteButton.style.display = "none";
			fm.PastDetailAddButton.style.display = "none";
			fm.PastDetailModifyButton.style.display = "none";
			fm.PastDetailDeleteButton.style.display = "none";
			
			fm2.PerUWAddButton.style.display = "none";
			fm2.PerUWModifyButton.style.display = "none";
			fm2.PerUWDeleteButton.style.display = "none";
			fm2.PerUWSaveButton.style.display = "";
			
			divUWOpinion.style.display = "";
			divUWDesc.style.display = "";
			divAttachmentUpload.style.display = "none";
		} else {
			
			fm.PastTotalAddButton.style.display = "none";
			fm.PastTotalModifyButton.style.display = "none";
			fm.PastTotalDeleteButton.style.display = "none";
			fm.PastDetailAddButton.style.display = "none";
			fm.PastDetailModifyButton.style.display = "none";
			fm.PastDetailDeleteButton.style.display = "none";
			
			fm2.PerUWAddButton.style.display = "none";
			fm2.PerUWModifyButton.style.display = "none";
			fm2.PerUWDeleteButton.style.display = "none";
			fm2.PerUWSaveButton.style.display = "none";
			
			divUWOpinion.style.display = "none";
			divUWDesc.style.display = "none";
			divAttachmentUpload.style.display = "none";
		}
		
		if (tActivityID == "0800100002" || tActivityID == "0800100003" || tActivityID == "0800100004" || tActivityID == "0800100005") {
			fm.QueryAllCal.style.display = "";
		} else {
			fm.QueryAllCal.style.display = "none";
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
		
		initTotalInp();
		initDetailInp();
		initPersonUWInp();
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ��������Ϣ
 */
function initTotalInp() {
	
	try {
		
		fm.InsuYear.value = "";
		fm.InsuranceCom.value = "";
		fm.InsuranceComName.value = "";
		fm.SumPrem.value = "";
		fm.SumLoss.value = "";
		fm.SumClaimPeople.value = "";
		fm.MaleRate.value = "";
		fm.FemaleRate.value = "";
	} catch (ex) {
		alert("��ʼ��������Ϣ¼��ؼ�����");
	}
}

/**
 * ��ʼ����ϸ��Ϣ
 */
function initDetailInp() {
	
	try {
		
		fm.DetailInsuYear.value = "";
		fm.DetailInsuranceComName.value = "";
		fm.GrpContNo.value = "";
		fm.ValDate.value = "";
		fm.EndDate.value = "";
		fm.NonMedSumPrem.value = "";
		fm.NonMedSumLoss.value = "";
		fm.NonMedPeople.value = "";
		fm.NonMedClmPeople.value = "";
		fm.MedSumPrem.value = "";
		fm.MedSumLoss.value = "";
		fm.MedPeople.value = "";
		fm.MedClmPeople.value = "";
	} catch (ex) {
		alert("��ʼ����ϸ��Ϣ¼��ؼ�����");
	}
}

/**
 * ��ʼ�����˺˱���Ϣ
 */
function initPersonUWInp() {
	
	try {
		
		fm2.ContNo.value = "";
		fm2.GrpName.value = "";
		fm2.InsuredName.value = "";
		fm2.IDType.value = "";
		fm2.IDTypeName.value = "";
		fm2.IDNo.value = "";
		fm2.Gender.value = "";
		fm2.GenderName.value = "";
		fm2.Birthday.value = "";
		fm2.Age.value = "";
		fm2.RiskName.value = "";
		fm2.LossAmount.value = "";
		fm2.Reason.value = "";
		fm2.Remark.value = "";
		fm2.UWOpinion.value = "";
		fm2.UWOpinionName.value = "";
		fm2.UWDesc.value = "";
	} catch (ex) {
		alert("��ʼ�����˺˱���Ϣ¼��ؼ�����");
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
function initPastTotalGrid() {
	
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
		iArray[i][0] = "������Ϣ��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ����Դ";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���չ�˾����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���չ�˾����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ܱ���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���⸶";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ܳ�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ա���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ů�Ա���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ů����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PastTotalGrid = new MulLineEnter("fm", "PastTotalGrid");
		PastTotalGrid.mulLineCount = 0;
		PastTotalGrid.displayTitle = 1;
		PastTotalGrid.locked = 0;
		PastTotalGrid.canSel = 1;
		PastTotalGrid.canChk = 0;
		PastTotalGrid.hiddenPlus = 1;
		PastTotalGrid.hiddenSubtraction = 1;
		PastTotalGrid.selBoxEventFuncName = "showPastTotal";
		PastTotalGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PastTotalGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initPastDetailGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϸ��Ϣ��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������Ч����";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ֹ����";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҽ�����ܱ���(Ԫ)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҽ�������⸶(Ԫ)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҽ���ձ���������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҽ���ճ�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҽ�����ܱ���(Ԫ)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҽ�������⸶(Ԫ)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҽ���ձ���������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҽ���ճ�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PastDetailGrid = new MulLineEnter("fm", "PastDetailGrid");
		PastDetailGrid.mulLineCount = 0;
		PastDetailGrid.displayTitle = 1;
		PastDetailGrid.locked = 0;
		PastDetailGrid.canSel = 1;
		PastDetailGrid.canChk = 0;
		PastDetailGrid.hiddenPlus = 1;
		PastDetailGrid.hiddenSubtraction = 1;
		PastDetailGrid.selBoxEventFuncName = "showPastDetail";
		PastDetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PastDetailGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initPersonUWGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���˺˱���Ϣ��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ����Դ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ����Դ";
		iArray[i][1] = "12px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ����";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ʹ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "8px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�⸶����";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�⸶���(Ԫ)";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��עԭ��";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ע";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tActivityID == "0800100001") {
			
			iArray[i] = new Array();
			iArray[i][0] = "�Ƿ��Ѻ˱�";
			iArray[i][1] = "13px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			iArray[i] = new Array();
			iArray[i][0] = "�Ƿ��Ѻ˱�";
			iArray[i][1] = "13px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱��ڵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱��ڵ�";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		//modify by ZhangC 20150113 
		iArray[i] = new Array();
		iArray[i][0] = "��֧��˾�˱��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��֧��˾�˱����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��֧��˾�˱�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tActivityID == "0800100003" || tActivityID == "0800100004") {
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱��������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱����";
			iArray[i][1] = "17px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱�����";
			iArray[i][1] = "20px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ܹ�˾�˱��������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ܹ�˾�˱����";
			iArray[i][1] = "17px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ܹ�˾�˱�����";
			iArray[i][1] = "20px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
		} else {
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱��������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱����";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾�˱�����";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ܹ�˾�˱��������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ܹ�˾�˱����";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ܹ�˾�˱�����";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����(˫������)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i++][7] = "downFile";
		
		
		PersonUWGrid = new MulLineEnter("fm2", "PersonUWGrid");
		PersonUWGrid.mulLineCount = 0;
		PersonUWGrid.displayTitle = 1;
		PersonUWGrid.locked = 0;
		PersonUWGrid.canSel = 1;
		PersonUWGrid.canChk = 0;
		PersonUWGrid.hiddenPlus = 1;
		PersonUWGrid.hiddenSubtraction = 1;
		PersonUWGrid.selBoxEventFuncName = "showPersonUWDetail";
		PersonUWGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PersonUWGridʱ����"+ ex);
	}
}
</script>
