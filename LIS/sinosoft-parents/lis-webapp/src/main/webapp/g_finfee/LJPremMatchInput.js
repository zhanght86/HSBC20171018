/***************************************************************
 * <p>ProName��LJMatchApplyInput.js</p>
 * <p>Title������ƥ�����</p>
 * <p>Description������ƥ�����</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-09
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var tOperate;

/**
 * ��ѯ��������
 */
function queryFee() {
	
	initFinDataGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql9");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.FeeManageCom.value);
	tSQLInfo.addSubPara(fm.FeeCustAccName.value);
	tSQLInfo.addSubPara(fm.FeeStartDate.value);
	tSQLInfo.addSubPara(fm.FeeEndDate.value);
	tSQLInfo.addSubPara(tMatchSerialNo);
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinDataGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ��ѯ�������
 */
function queryOutPay() {
	
	initOverDataGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql10");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.OutPayGrpName.value);
	tSQLInfo.addSubPara(fm.OutPayGrpContNo.value);
	tSQLInfo.addSubPara(fm.OutPayBussType.value);
	tSQLInfo.addSubPara(fm.OutPayManageCom.value);
	tSQLInfo.addSubPara(fm.OutPayPrtNo.value);
	tSQLInfo.addSubPara(fm.OutPayBussNo.value);
	tSQLInfo.addSubPara(tMatchSerialNo);
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), OverDataGrid, 0, 1);
	if (!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ��ѯҵ������
 */
function queryBuss() {

	initBusinessDataGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql11");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql24");
	}
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm1.DuePayManageCom.value);
	tSQLInfo.addSubPara(fm1.DuePayGrpName.value);
	tSQLInfo.addSubPara(fm1.DuePayGrpContNo.value);
	tSQLInfo.addSubPara(fm1.DuePayBussType.value);
	tSQLInfo.addSubPara(fm1.DuePayBussNo.value);
	tSQLInfo.addSubPara(fm1.DuePayBussNo.value);
	tSQLInfo.addSubPara(fm1.DuePayBussStartDate.value);
	tSQLInfo.addSubPara(fm1.DuePayBussEndDate.value);
	tSQLInfo.addSubPara(fm1.DuePayAgencyName.value);
	tSQLInfo.addSubPara(fm1.DuePayCoinsurance.value);
	
	turnPage3.queryModal(tSQLInfo.getString(), BusinessDataGrid, 0, 1);
	if (!turnPage3.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ѡ���������
 */
function optionFee() {
	
	if (FinDataGrid.getChkCount()==0) {
		alert("��ѡ���շ����ݣ�");
		return false;
	}
	
	document.getElementById("OptionFeeButton").disabled = true;
	if (ChoosedDataGrid.mulLineCount==0) {
	
	} else {
		
		for (var i=0; i<FinDataGrid.mulLineCount; i++) {
			
			if (FinDataGrid.getChkNo(i)) {
				
				var tPremSource = FinDataGrid.getRowColData(i, 1);//������Դ����
				var tSourceNo = FinDataGrid.getRowColData(i, 5);//���շѺ�
				
				for (var j=0; j<ChoosedDataGrid.mulLineCount; j++) {
				
					var tPremSource1 = ChoosedDataGrid.getRowColData(j, 1);
					var tSourceNo1 = ChoosedDataGrid.getRowColData(j, 3);
					
					if (tPremSource==tPremSource1 && tSourceNo==tSourceNo1) {
						
						alert("�����շ����ݵ�"+ (i+1) +"���Ѵ�����ѡ�������У�");
						document.getElementById("OptionFeeButton").disabled = false;
						return false;
					}
				}
			}
		}
	}
	
	var tCount; //��¼��ǰ
	for (var i=0; i<FinDataGrid.mulLineCount; i++) {

		if (FinDataGrid.getChkNo(i)) {
			 
			tCount = ChoosedDataGrid.mulLineCount;   
			ChoosedDataGrid.addOne("ChoosedDataGrid");
			
			var tPremSource = FinDataGrid.getRowColData(i, 1);//������Դ����
			var tPremSourceName = FinDataGrid.getRowColData(i, 2);//������Դ����
			var tBussType = FinDataGrid.getRowColData(i, 3);//ҵ����Դ����
			var tBussTypeName = FinDataGrid.getRowColData(i, 4);//ҵ����Դ
			var tTempFeeNo = FinDataGrid.getRowColData(i, 5);//���շѺ�
			var tCustBankCode = FinDataGrid.getRowColData(i, 6);//�ͻ������б���
			var tCustBankName = FinDataGrid.getRowColData(i, 7);//�ͻ�������
			var tCustBankAccNo = FinDataGrid.getRowColData(i, 8);//�ͻ��˺�
			var tCustAccName = FinDataGrid.getRowColData(i, 9);//�ͻ��˻���
			var tAppntNo = FinDataGrid.getRowColData(i, 10);//Ͷ����λ����
			var tGrpName = FinDataGrid.getRowColData(i, 11);//Ͷ����λ����
			var tInBankCode = FinDataGrid.getRowColData(i, 12);//�տ����б���
			var tInBankName = FinDataGrid.getRowColData(i, 13);//�տ�����
			var tInBankAccNo = FinDataGrid.getRowColData(i, 14);//�տ��˺�
			var tEnterAccDate = FinDataGrid.getRowColData(i, 15);//��������
			var tEnterMoney = FinDataGrid.getRowColData(i, 16);//���˽��
			
			ChoosedDataGrid.checkBoxSel(tCount+1);
			
			ChoosedDataGrid.setRowColData(tCount, 1, tPremSource);//������Դ����
			ChoosedDataGrid.setRowColData(tCount, 2, tPremSourceName);//������Դ
			ChoosedDataGrid.setRowColData(tCount, 3, tTempFeeNo);//��Դ����
			//ChoosedDataGrid.setRowColData(tCount, 4, );//������(��)
			ChoosedDataGrid.setRowColData(tCount, 5, tBussType);//ҵ�����ͱ���
			ChoosedDataGrid.setRowColData(tCount, 6, tBussTypeName);//ҵ������
			ChoosedDataGrid.setRowColData(tCount, 7, tTempFeeNo);//ҵ�����
			ChoosedDataGrid.setRowColData(tCount, 8, tCustBankCode);//�ͻ������б���
			ChoosedDataGrid.setRowColData(tCount, 9, tCustBankName);//�ͻ�������
			ChoosedDataGrid.setRowColData(tCount, 10, tCustBankAccNo);//�ͻ��˺�
			ChoosedDataGrid.setRowColData(tCount, 11, tCustAccName);//�ͻ��˻���
			ChoosedDataGrid.setRowColData(tCount, 12, tAppntNo);//Ͷ����λ����
			ChoosedDataGrid.setRowColData(tCount, 13, tGrpName);//Ͷ����λ����
			ChoosedDataGrid.setRowColData(tCount, 14, tInBankCode);//�շ����б���
			ChoosedDataGrid.setRowColData(tCount, 15, tInBankName);//�շ�����
			ChoosedDataGrid.setRowColData(tCount, 16, tInBankAccNo);//�շ��˺�
			ChoosedDataGrid.setRowColData(tCount, 17, tEnterAccDate);//��������
			ChoosedDataGrid.setRowColData(tCount, 18, tEnterMoney);//���˽��
		}
	}
	document.getElementById("OptionFeeButton").disabled = false;
}

/**
 * ѡ���������
 */
function optionOutPay() {
	
	if (OverDataGrid.getChkCount()==0) {
		alert("��ѡ���շ����ݣ�");
		return false;
	}
	
	document.getElementById("OptionOutPayButton").disabled = true;
	if (ChoosedDataGrid.mulLineCount==0) {
	
	} else {
		
		for (var i=0; i<OverDataGrid.mulLineCount; i++) {
			
			if (OverDataGrid.getChkNo(i)) {
				
				var tPremSource = OverDataGrid.getRowColData(i, 1);//������Դ����
				var tSourceNo = OverDataGrid.getRowColData(i, 3);//���շѺ�
				
				for (var j=0; j<ChoosedDataGrid.mulLineCount; j++) {
				
					var tPremSource1 = ChoosedDataGrid.getRowColData(j, 1);
					var tSourceNo1 = ChoosedDataGrid.getRowColData(j, 3);
					
					if (tPremSource==tPremSource1 && tSourceNo==tSourceNo1) {
						
						alert("����������ݵ�"+ (i+1) +"���Ѵ�����ѡ�������У�");
						document.getElementById("OptionOutPayButton").disabled = false;
						return false;
					}
				}
			}
		}
	}
	
	var tCount; //��¼��ǰ
	for (var i=0; i<OverDataGrid.mulLineCount; i++) {

		if (OverDataGrid.getChkNo(i)) {
			 
			tCount = ChoosedDataGrid.mulLineCount;   
			ChoosedDataGrid.addOne("ChoosedDataGrid");
			
			var tPremSource = OverDataGrid.getRowColData(i, 1);//������Դ
			var tPremSourceName = OverDataGrid.getRowColData(i, 2);//������Դ����
			var tOverNo = OverDataGrid.getRowColData(i, 3);//�����ˮ��
			var tGrpContNo = OverDataGrid.getRowColData(i, 4);//������
			var tAppntNo = OverDataGrid.getRowColData(i, 5);//Ͷ���˱���
			var tGrpName = OverDataGrid.getRowColData(i, 6);//Ͷ��������
			var tBussType = OverDataGrid.getRowColData(i, 7);//ҵ�����ͱ���
			var tBussTypeName = OverDataGrid.getRowColData(i, 8);//ҵ������
			var tBussNo = OverDataGrid.getRowColData(i, 9);//ҵ���
			var tOverMoney = OverDataGrid.getRowColData(i, 10);//��ɽ��
			var tMoney = OverDataGrid.getRowColData(i, 11);//����ʹ�ý��
			
			ChoosedDataGrid.checkBoxSel(tCount+1);
			
			ChoosedDataGrid.setRowColData(tCount, 1, tPremSource);//������Դ����
			ChoosedDataGrid.setRowColData(tCount, 2, tPremSourceName);//������Դ
			ChoosedDataGrid.setRowColData(tCount, 3, tOverNo);//��Դ����
			ChoosedDataGrid.setRowColData(tCount, 4, tGrpContNo);//������(��)
			ChoosedDataGrid.setRowColData(tCount, 5, tBussType);//ҵ�����ͱ���
			ChoosedDataGrid.setRowColData(tCount, 6, tBussTypeName);//ҵ������
			ChoosedDataGrid.setRowColData(tCount, 7, tBussNo);//ҵ�����
			//ChoosedDataGrid.setRowColData(tCount, 8, );//�ͻ������б���
			//ChoosedDataGrid.setRowColData(tCount, 9, );//�ͻ�������
			//ChoosedDataGrid.setRowColData(tCount, 10, );//�ͻ��˻���
			//ChoosedDataGrid.setRowColData(tCount, 11, );//�ͻ��˺�
			ChoosedDataGrid.setRowColData(tCount, 12, tAppntNo);//Ͷ����λ����
			ChoosedDataGrid.setRowColData(tCount, 13, tGrpName);//Ͷ����λ����
			//ChoosedDataGrid.setRowColData(tCount, 14, );//�շ����б���
			//ChoosedDataGrid.setRowColData(tCount, 15, );//�շ�����
			//ChoosedDataGrid.setRowColData(tCount, 16, );//�շ��˺�
			//ChoosedDataGrid.setRowColData(tCount, 17, );//��������
			ChoosedDataGrid.setRowColData(tCount, 18, tMoney);//���˽��
		}
	}
	
	document.getElementById("OptionOutPayButton").disabled = false;
}

/**
 * ��ѯ�����ƥ������
 */
function queryMatchData() {
	
	initMatchingConfirmGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql12");
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage5.queryModal(tSQLInfo.getString(), MatchingConfirmGrid, 0, 1);
	
	if (turnPage5.strQueryResult) {
		
		queryMatchPayInfo();
		queryMatchFeeInfo();
		c1.style.display = "";
		c2.style.display = "";
	} else {
		
		c1.style.display = "none";
		c2.style.display = "none";
		initChoosedData1Grid();
		initBusinessData1Grid();
	}
}

/**
 * ����ƥ��
 */
function premMatchClick() {
	
	document.getElementById("PremMatchButton").disabled = true;
	if (!beforePremMatchClick()) {
		document.getElementById("PremMatchButton").disabled = false;
		return false;
	}
	
	tOperate = "PREMMATCH";
	fmPub.Operate.value = tOperate;
	fm1.action = "./LJPremMatchSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm1);
}

/**
 * ƥ�����ǰ
 */
function beforePremMatchClick() {
	
	if (MatchingConfirmGrid.mulLineCount!=0) {

		alert("�ѽ���ƥ�䲻���ٴν��иò�����");
		return false;
	}
	
	//ҳ�沿�ֽ�����������Ľ���ʽУ��
	if (ChoosedDataGrid.getChkCount()==0) {
		alert("��ѡ�����ݲ���Ϊ�գ�");
		return false;
	}
	
	var tCount = 0; //��¼��ǰ
	var tCount1 = 0;
	for (var i=0; i<ChoosedDataGrid.mulLineCount; i++) {

		if (ChoosedDataGrid.getChkNo(i)) {
			
			var tMoney = ChoosedDataGrid.getRowColData(i, 18);
			var tCurOutMoney = ChoosedDataGrid.getRowColData(i, 19);
			
			if (tCurOutMoney==null || tCurOutMoney=="") {
				//��ɽ��Ϊ��,������У��
			} else {
				
				if (!isNumeric(tCurOutMoney)) {
					alert("��ѡ�����ݵ�"+(i+1)+"����ɽ�����Ч���֣�");
					return false;
				}
				
				if (Number(tCurOutMoney)>Number(tMoney)) {
					alert("����������ݵ�"+(i+1)+"�б���ʹ�ý��Ӧ����0�Ҳ����ڵ��˽�");
					return false;
				}
				
				if (!checkDecimalFormat(tCurOutMoney, 10, 2)) {
					alert("����������ݵ�"+(i+1)+"�б���ʹ�ý������λӦ������10�ֳ���С��Ӧ������2�ֳ���");
					return false;
				}
				tCount1++;
			}
			
			tCount++;
		}
	}
	
	if (tCount==0) {
		alert("��ѡ����ѡ�����ݣ�");
		return false;
	}
	
	if (tCount1!=1 && tCount1!=0) {
		alert("��ѡ�����ݴ�����ɽ�������һ�ʣ�");
		return false;
	}
	
	tCount = 0;
	for (var i=0; i<BusinessDataGrid.mulLineCount; i++) {

		if (BusinessDataGrid.getChkNo(i)) {
			
			var tCurOutMoney = BusinessDataGrid.getRowColData(i, 10);
			
			if (tCurOutMoney==null || tCurOutMoney=="") {
				//��ɽ��Ϊ��,������У��
			} else {
				
				if (!isNumeric(tCurOutMoney)) {
					alert("ҵ��Ӧ�����ݵ�"+(i+1)+"����ɽ�����Ч���֣�");
					return false;
				}
				
				if (!checkDecimalFormat(tCurOutMoney, 10, 2)) {
					alert("ҵ��Ӧ�����ݵ�"+(i+1)+"����ɽ������λӦ������10�ֳ���С��Ӧ������2�ֳ���");
					return false;
				}
			}
			
			tCount++;
		}
	}
	
	if (tCount==0) {
		alert("��ѡ��ҵ��Ӧ�����ݣ�");
		return false;
	}
	
	return true;
}

/**
 * ����ƥ��
 */
function cancelClick() {
	
	if (MatchingConfirmGrid.mulLineCount==0) {
		
		alert("û�пɽ��г��������ݣ�");
		return false;
	}
	
	tOperate = "CANCELCLICK";
	fmPub.Operate.value = tOperate;
	fm2.action = "./LJPremMatchSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm2);
}

/**
 *
 */
function matchConfirmClick() {
	
	var tInputConclusion = fm3.InputConclusion.value;
	if (tInputConclusion==null || tInputConclusion=="") {
		alert("ƥ����۲���Ϊ�գ�");
		return false;
	}
	
	if (tInputConclusion=="00") {
		
		if (MatchingConfirmGrid.mulLineCount!=1) {
			alert("���Ƚ���ƥ�䣡");
			return false;
		}
		
		var tInputDesc = fm3.InputDesc.value;
		if (tInputDesc.length>300) {
			alert("��������Ӧ��300���ڣ�");
			return false;
		}
	} else {
		
		var tInputDesc = fm3.InputDesc.value;
		if (tInputDesc==null || tInputDesc=="") {
			alert("�˻�ʱ��������������Ϊ�գ�");
			return false;
		}
		
		if (tInputDesc.length>300) {
			alert("��������Ӧ��300���ڣ�");
			return false;
		}
	}
	
	tOperate = "MATCHCONFIRM";
	fmPub.Operate.value = tOperate;
	fm3.action = "./LJPremMatchSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm3);
}

/**
 * ��ѯ��ѡƥ���¼�շ�����
 */
function queryMatchFeeInfo() {
	
	initChoosedData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql13");
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage6.queryModal(tSQLInfo.getString(), ChoosedData1Grid, 0, 1);
}

/**
 * ��ѯ��ѡƥ���¼Ӧ������
 */
function queryMatchPayInfo() {
	
	initBusinessData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql14");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql25");
	}
	
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage7.queryModal(tSQLInfo.getString(), BusinessData1Grid, 0, 1);
}

/**
 * ��ѯ��ƥ���ϴ��ĸ�����Ϣ
 */
function queryAttachmentInfo() {

	initUploadFileGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql7");
	tSQLInfo.addSubPara(tMatchSerialNo);
	
	turnPage8.queryModal(tSQLInfo.getString(), UploadFileGrid, 0, 1);
	
	if (turnPage8.strQueryResult) {
		divUploadInfo.style.display = "";
	} else {
		divUploadInfo.style.display = "none";
	}
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
   var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
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
		
		dealFailSubmit();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		dealAfterSubmit();
	}	
}

function dealFailSubmit() {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="PREMMATCH") {
		
		document.getElementById("PremMatchButton").disabled = false;
	}
}

/**
 * �ύ�ɹ�����
 */
function dealAfterSubmit() {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="PREMMATCH") {
		
		document.getElementById("PremMatchButton").disabled = false;
		initQueryInfo();
		initQueryInfo();
		queryMatchData();
	} else if (tOperate=="CANCELCLICK") {
		
		initMatchingConfirmGrid();
		c1.style.display = "none";
		c2.style.display = "none";
	} else if (tOperate=="MATCHCONFIRM") {
		
		location.href = "./LJPremMatchQueryInput.jsp";
	}
}

/**
 * ���ز���
 */
function returnMenu() {
	
	location.href = "./LJPremMatchQueryInput.jsp";
}

/**
 * ��ʼ����ѯ��
 */
function initQueryInfo() {
	
	fm.FeeManageCom.value = "";
	fm.FeeManageComName.value = "";
	fm.FeeStartDate.value = "";
	fm.FeeCustAccName.value = "";
	fm.FeeEndDate.value = "";
	fm.OutPayGrpName.value = "";
	fm.OutPayGrpContNo.value = "";
	fm.OutPayBussType.value = "";
	fm.OutPayBussTypeName.value = "";
	fm.OutPayManageCom.value = "";
	fm.OutPayManageComName.value = "";
	fm.OutPayPrtNo.value = "";
	fm.OutPayBussNo.value = "";
	initFinDataGrid();
	initOverDataGrid();
	
	initChoosedDataGrid();
	fm1.DuePayGrpName.value = "";
	fm1.DuePayGrpContNo.value = "";
	fm1.DuePayBussType.value = "";
	fm1.DuePayBussTypeName.value = "";
	fm1.DuePayBussNo.value = "";
	fm1.DuePayBussStartDate.value = "";
	fm1.DuePayBussEndDate.value = "";
	fm1.DuePayAgencyName.value = "";
	fm1.DuePayCoinsurance.value = "";
	
	initBusinessDataGrid();
}

/**
 * ���ظ���
 */
function downLoadClick(parm1) {
	var tFileName = document.all("UploadFileGrid3").value;
	var tFilePath = document.all("UploadFileGrid4").value;
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}


/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='finbusstype') {
		
		var tSql = "finbusstypeoutpay";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	} else if (value1=='finbusstype1') {
		
		var tSql = "finbusstypepay";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}
