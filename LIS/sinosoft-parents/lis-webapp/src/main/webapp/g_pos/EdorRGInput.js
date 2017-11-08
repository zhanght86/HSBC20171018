/***************************************************************
 * <p>ProName:EdorRGInput.js</p>
 * <p>Title:  ������ȡ</p>
 * <p>Description:������ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-27
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

//ԭ����������Ϣ��ѯ
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRGSql");
	tSQLInfo.setSqlId("EdorRGSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ�����������Ĳ�ѯ���!");
		return false;
	}
}

//�޸Ĺ��ı���������Ϣ��ѯ
function queryUpClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRGSql");
	tSQLInfo.setSqlId("EdorRGSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(NullToEmpty(tEdorNo));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);
	
	if(o=='1'){
		if(!turnPage2.strQueryResult){
			alert("δ��ѯ�����������Ĳ�ѯ���!");
			return false;
		}
	}
}

//��λ�����˻���Ϣ��ѯ
function queryGrpBank(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRGSql");
	tSQLInfo.setSqlId("EdorRGSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.GrpBankName.value = strQueryResult[0][0];
		fm.GrpBankAccName.value = strQueryResult[0][1];
		fm.GrpBankAccNo.value = strQueryResult[0][2];
	}
}

//������ȡ
function getClick(){
	
	var rowNum = OldInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (OldInsuredInfoGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (fm.ExpiryGetMode.value=="") {
		alert("��ȡ��ʽ����Ϊ�գ�");
		return false;
	}
	
	if (fm.ExpiryGetMode.value=="0") {
	
		if (fm.ExpiryPayMode.value=="") {
			alert("֧����ʽ����Ϊ�գ�");
			return false;
		}
		
		if (fm.ExpiryPayMode.value=="0") {
			
			if (fm.GrpBankName.value=="" || fm.GrpBankAccName.value=="" || fm.GrpBankAccNo.value=="") {
				alert("��λ�����˻���Ϣ��������");
				return false;
			}
		}
	} else if (fm.ExpiryGetMode.value=="1") {
		
		if (fm.TransMode.value=="") {
			alert("ת�����Ͳ���Ϊ�գ�");
			return false;
		}
		
		if (fm.TransMode.value=="1") {
			
			if (fm.TransAmount.value=="") {
				alert("ת������Ϊ�գ�");
				return false;
			}
			
			if (!isNumeric(fm.TransAmount.value)) {
				alert("ת�������Ҫ¼�����0�����֣�");
				return false;
			}
			
			if (fm.TransAmount.value<=0) {
				alert("ת�������Ҫ¼�����0�����֣�");
				return false;
			}
			
			if (fm.ExpiryPayMode.value=="") {
				alert("֧����ʽ����Ϊ�գ�");
				return false;
			}
		
			if (fm.ExpiryPayMode.value=="0") {
				
				if (fm.GrpBankName.value=="" || fm.GrpBankAccName.value=="" || fm.GrpBankAccNo.value=="") {
					alert("��λ�����˻���Ϣ��������");
					return false;
				}
			}
		}
	}
	
	for (var i=0;i < OldInsuredInfoGrid.mulLineCount;i++) {
		
		if (OldInsuredInfoGrid.getChkNo(i)) {
			
			var tExpiryAmount = OldInsuredInfoGrid.getRowColData(i,7);
			var tBankName = OldInsuredInfoGrid.getRowColData(i,8);
			var tBankAccName = OldInsuredInfoGrid.getRowColData(i,9);
			var tBankAccNo = OldInsuredInfoGrid.getRowColData(i,10);
			
			if (fm.ExpiryGetMode.value=="1" && fm.TransMode.value=="1") {
				
				if (parseFloat(fm.TransAmount.value)>parseFloat(tExpiryAmount)) {
					alert("��"+(i+1)+"��ת�����ܴ������ڽ��!");
					return false;
				}
			}
			
			if (fm.ExpiryPayMode.value=="1") {
				
				if (tBankName=="" || tBankAccName=="" || tBankAccNo=="") {
					alert("��"+(i+1)+"�и��������˻���Ϣ��������");
					return false;
				}
			}
		}
	}
	
	mOperate="UPDATE";
	fm.action = "./EdorRGSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

//��������
function deleteOperate(){
	
	var rowNum = UpdateInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (UpdateInsuredInfoGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	mOperate="DELETE";
	fm.action = "./EdorRGSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

function submitFunc(){
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	initInpBox();
	queryOldClick();
	queryUpClick(2);
}

/**
 * ��������
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=="expirygetmode") {
		
		if (fm.ExpiryGetMode.value=="0") {
			
			TransModeTitle.style.display = "none";
			TransModeInput.style.display = "none";
			fm.TransMode.value = "";
			fm.TransModeName.value = "";
			
			TransAmountTitle.style.display = "none";
			TransAmountInput.style.display = "none";
			fm.TransAmount.value = "";
			
			td1.style.display = "";
			td2.style.display = "";
			td3.style.display = "";
			td4.style.display = "";
			
			divPayMode.style.display = "";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		} else if (fm.ExpiryGetMode.value=="1") {
			
			TransModeTitle.style.display = "";
			TransModeInput.style.display = "";
			fm.TransMode.value = "";
			fm.TransModeName.value = "";
			
			TransAmountTitle.style.display = "none";
			TransAmountInput.style.display = "none";
			fm.TransAmount.value = "";
			
			td1.style.display = "";
			td2.style.display = "";
			td3.style.display = "none";
			td4.style.display = "none";
			
			divPayMode.style.display = "none";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		}
	} else if (cCodeType=="expirypaymode") {
		
		if (fm.ExpiryPayMode.value=="0") {
		
			divGrpBank.style.display = "";
		} else if (fm.ExpiryPayMode.value=="1") {
			
			divGrpBank.style.display = "none";
		}
	} else if (cCodeType=="transmode") {
		
		if (fm.TransMode.value=="0") {
			
			TransAmountTitle.style.display = "none";
			TransAmountInput.style.display = "none";
			fm.TransAmount.value = "";
			
			td1.style.display = "";
			td2.style.display = "";
			td3.style.display = "none";
			td4.style.display = "none";
			
			divPayMode.style.display = "none";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		} else if (fm.TransMode.value=="1") {
			
			TransAmountTitle.style.display = "";
			TransAmountInput.style.display = "";
			fm.TransAmount.value = "";
			
			td1.style.display = "none";
			td2.style.display = "none";
			td3.style.display = "none";
			td4.style.display = "none";
			
			divPayMode.style.display = "";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		}
	}
}
