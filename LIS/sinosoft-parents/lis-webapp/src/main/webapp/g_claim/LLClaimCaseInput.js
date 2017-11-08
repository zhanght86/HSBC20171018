/***************************************************************
 * <p>ProName��LLClaimCaseInput.js</p>
 * <p>Title����ͨ����¼��</p>
 * <p>Description����ͨ����¼��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
/**
 * ���뽻����ת��
 */
function applyPageNo() {
	
	var strUrl="./LLClaimHandAppMain.jsp";
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"������ת�Ų�ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ����������Ϣ
 */
function saveAccept() {
	
	if(!checkAcceptInfo()) {
		return false;
	}
		
	fm.Operate.value="ACCEPTINSERT";
	submitForm();	
}
/**
 * ɾ��������Ϣ
 */
function deleteAccept() {
	
	if (fm.GrpRgtNo.value==null || fm.GrpRgtNo.value=="") {
		alert("���ȱ���������Ϣ��");
		return false;
	}
		
	fm.Operate.value="ACCEPTDELETE";
	submitForm();	
}
/**
 * �����ͻ�
 */
function addCustomer() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("���ȱ�������������Ϣ");
		return false;
	}	
	fm.Operate.value="CUSTOMERINSERT";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * �޸Ŀͻ�
 */
function modifyCustomer() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRegisterNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}

	tPageNo = turnPage1.pageIndex;
	tIndexNo = tSelNo+1;	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;		
	
	fm.Operate.value="CUSTOMERUPDATE";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();
}
/**
 * ɾ���ͻ�
 */
function deleteCustomer() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRegisterNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	fm.RegisterNo.value = tRegisterNo;
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);
	fm.CustomerNo.value = tCustomerNo;
	if(confirm("�Ƿ�ȷ��ɾ��ѡ�еĳ�������Ϣ��")) {
		fm.Operate.value="CUSTOMERDELETE";
		submitForm();		
	}

}
/**
 * δ����ͻ���Ϣ
 */
function noAcceptInfo() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("���ȱ�������������Ϣ");
		return false;
	}
	var strUrl="./LLClaimNoAcceptMain.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode="+mMode;
	window.open(strUrl,"δ����ͻ�",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

/**
 * ɾ����������ͻ���Ϣ
 */
function deleteAllAccept() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("���ȱ�������������Ϣ");
		return false;
	}	
	
	if (confirm("�Ƿ�ɾ������������ͻ���")) {
		fm.Operate.value="CUSTOMERDELETEALL";
		submitForm();		
	}	
}

/**
 * �����¼�
 */
function addCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}	
	fm.CaseSource.value = "01";
	fm.Operate.value="CASEINSERT";
	
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();
}

/**
 * �޸��¼�
 */
function modifyCase() {
	var tSelNo = EventlistGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼���Ϣ");
		return false;
	}
	var tRegisterNo = EventlistGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ�¼���Ϣ��");
		return;
	}	
	
	tPageNo = turnPage2.pageIndex;
	tIndexNo = tSelNo+1;	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;	
	
	fm.Operate.value="CASEUPDATE";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();
}
/**
 * ɾ���¼�
 */
function deleteCase() {
	var tSelNo = EventlistGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼���Ϣ");
		return false;
	}
	var tRegisterNo = EventlistGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ�¼���Ϣ��");
		return;
	}
	if (confirm("ȷ��ɾ�������¼���")) {
		fm.Operate.value="CASEDELETE";
		submitForm();		
	}	
}
/**
 * ��ҽ���˵�
 */
function easyBill() {

	var tSelNo = EventlistGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���¼���Ϣ");
		return false;
	}
	var strUrl="./LLClaimNoMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&AccidentDate="+fm.AccDate.value+"&Mode="+mMode;

	var tWidth=window.screen.availWidth;
	var tHeight=500;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"��ҽ���˵�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ҽ���˵�
 */
function standBill() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tCaeSelNo=EventlistGrid.getSelNo()-1;
	var CaseSource="";
	var tCaseNo= "";	
	if(tCaeSelNo<0){
		CaseSource="02";//�˵�����
	}else{
		CaseSource=fm.CaseSource.value;
		tCaseNo= EventlistGrid.getRowColData(tCaeSelNo,2);	
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql26");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	var tArr = easyExecSql(tSQLInfo.getString());
	var busson = fm.RegisterNo.value;
	var tBussType = "G_CM";
	var tSubType = "23003";
 	
 	if(tArr== null || tArr == "") {
 		
 	}else{
	 	busson = tArr[0][0];
		tBussType = tArr[0][1];
		tSubType = tArr[0][2];
 	}
	
	var strUrl="./LLClaimMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+tCaseNo+"&CustomerNo="+fm.CustomerNo.value+"&CaseSource="+CaseSource+"&Mode=0"
	+"&BussNo="+busson+"&BussType="+tBussType+"&SubType=23003";
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"ҽ���˵�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/**
 * ȡ���¼����ι���
 */
function offAssociate() {
	
	var rowNum = OnEventDutyListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(OnEventDutyListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	}

/*		
	var tSelNo = OnEventDutyListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���������¼��ѹ���������Ϣ");
		return false;
	}
	fm.PolNo.value = OnEventDutyListGrid.getRowColData(tSelNo,2);
	fm.DutyCode.value = OnEventDutyListGrid.getRowColData(tSelNo,5);
	fm.GetDutyCode.value = OnEventDutyListGrid.getRowColData(tSelNo,7);
*/
	fm.Operate.value="DUTYOFF";
	submitForm();		
}

/**
 * �����¼�����
 */
function onAssociate() {
	
	var rowNum = OffEventDutyListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(OffEventDutyListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("������ѡ��һ���¼�������Ϣ");
		return false;
	}
		
	fm.Operate.value="DUTYON";
	submitForm();		
}

/**
 * �������
 */
function caseOver() {

	if(confirm("�Ƿ�ȷ��������ϣ�")) {
		
		if(!checkBeforeOver()) {
			return false;
		}		
		fm.Operate.value="RGTOVER";
		submitForm();
	}
}
/**
 * ����
 */
function goBack() {
	
	//���ⰸ��������������������ҳ�棬����ǰУ��
	if(mMode=="2" || mMode=="1") {
		
		//����ǰУ��
		//window.location.href="./LLClaimSpecialCaseInput.jsp?GrpRgtNo="+fm.GrpRgtNo.value;
		top.close();
	}else {
		window.location.href="./LLClaimCaseAppInput.jsp";
	}
}
/**
 * �ͻ���Ϣ��������
 */
function customerImport() {
	
	if (fm.GrpRgtNo.value==null || fm.GrpRgtNo.value=="") {
		alert("���ȱ�������������Ϣ��");
		return false;
	}
	var strUrl="./LLClaimCustImportMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode=1";
	var tWidth=1200;
	var tHeight=800;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ͻ���Ϣ��������",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content, tGrpRegisterNo,tRegisterNo,tCaseNo) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		if (fm.Operate.value=="ACCEPTINSERT") {
			mGrpRegisterNo = tGrpRegisterNo;			
			window.location.href="LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRegisterNo+"&Mode=0";
		} else if (fm.Operate.value=="ACCEPTDELETE") {
			window.location.href="LLClaimCaseInput.jsp?Mode=0";
		}
		if (fm.Operate.value=="RGTOVER") {
			goBack();
		} else {
			
			if ((fm.Operate.value).indexOf("CASE")>=0) {

				initCaseInfo();				
				queryCustomerCaseList();
				//�޸ĺ���ѡ��ǰ��
				if (fm.Operate.value=="CASEUPDATE") {
					
					for (var i=0;i<fm.PageIndex.value;i++) {
						turnPage2.nextPage();
					}
					EventlistGrid.radioSel(fm.SelNo.value);
					showSelectCase();
				} else {
					initCaseInfo();
					initEventlistGrid();
				}
			} else if ((fm.Operate.value).indexOf("CUSTOMER")>=0) {
				initCustomerInfo();
				queryCustomerList();
				if (fm.Operate.value=="CUSTOMERUPDATE") {
					setSelRow(CustomerListGrid,turnPage1);
					showSelectCustomer();					
				} else {
					initCaseInfo();
					initEventlistGrid();
				}
				
			} else if ((fm.Operate.value).indexOf("DUTY")>=0) {
				queryCaseDutyInfo();
			} else {
				initForm();
			}			
		}
	
	}	
}
/**
 * ���ؽ�����ת��
 */
function afterAppPageNo(tSelectResult) {
	
	fm.all('PageNo').value = "";
	fm.all('PageNo').value = tSelectResult[0];
}

/**
 * ����У��¼������
 */
function checkInput(tObject) {
	
		tValue = tObject.value;
		if(((!/^[0-9]\d*$/.test(tValue)) && (!tValue==""))){
			
			alert("��������������") ;
			tObject.className = "warn" ;
			tObject.focus();
			tObject.value="";		
		}	
}
/**
 * ����У��¼����
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(tValue)) && (!tValue==""))){
		
		alert("��������ȷ�Ľ�") ;
		tObject.className = "warn" ;
		tObject.focus();
		tObject.value="";		
	}
}
//չʾ��ʾ��Ϣ
function showWarnInfo() {
		
	alert('��¼��[Ͷ��������]��س�����(֧��ģ����ѯ)��');
	fm.AppntName.focus();
	fm.AppntName.style.background = "#ffb900";
}
//չʾ��ʾ��Ϣ
function showWarnInfo1() {
		
	alert('��¼��[��Ҫ�������]��س�����(֧��ģ����ѯ)��');
	fm.AccResult1Name.focus();
	fm.AccResult1Name.style.background='#ffb900';
	fm.AccResult1.value = "";
}