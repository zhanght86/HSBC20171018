/***************************************************************
 * <p>ProName��FinAccItemInput.js</p>
 * <p>Title����֧��Ŀ�������</p>
 * <p>Description�������ƿ�Ŀ�µķ�֧��Ŀ</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ʯȫ��
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬

var tSQLInfo = new SqlClass();

var conditionCode2 = "1";//��ѯ��֧����2����
var conditionCode3 = "1";//��ѯ��֧����3����

/**
 * �ύ����
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.action = "./FinAccItemSave.jsp";
	fm.submit(); //�ύ
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		if (mOperate!="DELETE") {
			queryClick();
			clearInfo();
		} else {
			resetClick();
		}
	}
}

/**
 * ��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemSql");
	tSQLInfo.setSqlId("FinAccItemSql1");
	tSQLInfo.addSubPara(fm.AccItemCode1.value);
	tSQLInfo.addSubPara(fm.AccItemCode2.value);
	tSQLInfo.addSubPara(fm.AccItemCode3.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinAccItemGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}

/**
 * ��ʾ��֧��Ŀ��Ϣ
 */
function showFinAccItemInfo() {
	
	var tSelNo = FinAccItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�������б���Ϣ��");
		return false;
	}
	
	fm.AccItemCode1.disabled = true;
	fm.AccItemName1.disabled = true;
	fm.AccItemCode2.disabled = true;
	fm.AccItemName2.disabled = true;
	fm.AccItemCode3.disabled = true;
	fm.AccItemName3.disabled = true;
	
	fm.AccItemName.value = FinAccItemGrid.getRowColData(tSelNo-1, 2);
	fm.AccItemCode1.value = FinAccItemGrid.getRowColData(tSelNo-1, 3);
	fm.AccItemName1.value = FinAccItemGrid.getRowColData(tSelNo-1, 4);
	fm.AccItemCode2.value = FinAccItemGrid.getRowColData(tSelNo-1, 5);
	fm.AccItemName2.value = FinAccItemGrid.getRowColData(tSelNo-1, 6);
	fm.AccItemCode3.value = FinAccItemGrid.getRowColData(tSelNo-1, 7);
	fm.AccItemName3.value = FinAccItemGrid.getRowColData(tSelNo-1, 8);
	fm.FinCode.value = FinAccItemGrid.getRowColData(tSelNo-1, 9);
	fm.FinName.value = FinAccItemGrid.getRowColData(tSelNo-1, 10);
	fm.Remark.value = FinAccItemGrid.getRowColData(tSelNo-1, 11);
	
	conditionCode2 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"#";
	conditionCode3 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"# and Code2=#"+fm.AccItemCode2.value+"#";
}

/**
 * ����
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenAccItemName.value = fm.AccItemName.value;
	fm.AccItemCode.value = fm.AccItemCode1.value + fm.AccItemCode2.value + fm.AccItemCode3.value;
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * �޸�
 */
function updateClick() {
	
	var tSelNo = FinAccItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����֧��Ŀ��Ϣ��");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.AccItemCode.value = FinAccItemGrid.getRowColData(tSelNo-1, 1);
	fm.HiddenAccItemName.value = fm.AccItemName.value;
	mOperate = "UPDATE";
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tSelNo = FinAccItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����֧��Ŀ��Ϣ��");
		return false;
	}
	
	if (!confirm("ȷ��Ҫɾ���÷�֧��Ŀ��")) {
		return false
	}
	
	fm.AccItemCode.value = FinAccItemGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * ����
 */
function resetClick() {
	
	clearInfo();
	initFinAccItemGrid();
}

/**
 * ���������
 */
function clearInfo() {
	
	fm.AccItemCode1.disabled = false;
	fm.AccItemName1.disabled = false;
	fm.AccItemCode2.disabled = false;
	fm.AccItemName2.disabled = false;
	fm.AccItemCode3.disabled = false;
	fm.AccItemName3.disabled = false;
	
	fm.AccItemCode.value = "";
	fm.AccItemName.value = "";
	fm.AccItemCode1.value = "";
	fm.AccItemName1.value = "";
	fm.AccItemCode2.value = "";
	fm.AccItemName2.value = "";
	fm.AccItemCode3.value = "";
	fm.AccItemName3.value = "";
	fm.FinCode.value = "";
	fm.FinName.value = "";
	fm.Remark.value = "";
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {
	
	if (Field.name=="AccItemCode1") {
		
		fm.AccItemCode2.value = "";
		fm.AccItemName2.value = "";
		fm.AccItemCode3.value = "";
		fm.AccItemName3.value = "";
		conditionCode2 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"#";
		fm.AccItemName.value = fm.AccItemName1.value;
	}
	
	if (Field.name=="AccItemCode2") {
		
		fm.AccItemCode3.value = "";
		fm.AccItemName3.value = "";
		conditionCode3 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"# and Code2=#"+fm.AccItemCode2.value+"#";
		
		if (Field.value!="0000") {
			fm.AccItemName.value = fm.AccItemName1.value + "-" + fm.AccItemName2.value;
		}
	}
	
	if (Field.name=="AccItemCode3") {
	
		if (Field.value!="0000") {
			fm.AccItemName.value = fm.AccItemName1.value + "-" + fm.AccItemName2.value + "-" + fm.AccItemName3.value;
		}
	}
}

/**
 * ��ѯ��֧��Ŀ����֮ǰ��У��
 */
function beforQueryCode(obj, Field) {
	
	var tAccItemCode = Field.value;
	if (tAccItemCode==null || tAccItemCode=="") {
		
		alert("����ѡ���ϼ���֧��Ŀ���룡");
		field.focus();
		return false;
	}
	
	return true;
}

/**
 * ģ����ѯ��ƿ�Ŀ
 */
function fuzzyQueryFinCode(Field, FieldName) {
	
	var objCodeName = FieldName.value;
	if (objCodeName=="") {
		return false;
	}
	
	if (window.event.keyCode == "13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
			alert("�������ƿ�Ŀ���ƣ�");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_account.FinAccItemSql");
			tSQLInfo.setSqlId("FinAccItemSql2");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (arr==null) {
				alert("�����ڸû�ƿ�Ŀ��");
				return false;
			} else {
				if (arr.length == 1) {
					Field.value = arr[0][0];
					FieldName.value = arr[0][1];
				}else {
					var queryCondition = "1 and finname like #%"+objCodeName+"%#";
					showCodeList('finaccount', [Field, FieldName], [0,1], null, queryCondition, '1', 1, '300');
				}
			}
		}
	}
}


/**
 * ��������
 */
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "��֧��Ŀ����^|��֧��Ŀ����^|��֧��Ŀ����1^|��֧��Ŀ����1^|��֧��Ŀ����2^|��֧��Ŀ����2^|��֧��Ŀ����3^|��֧��Ŀ����3^|��ƿ�Ŀ����^|��ƿ�Ŀ����^|��ע^|¼����^|¼������^|����޸Ĳ�����^|����޸�����";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemSql");
	tSQLInfo.setSqlId("FinAccItemSql1");
	tSQLInfo.addSubPara(fm.AccItemCode1.value);
	tSQLInfo.addSubPara(fm.AccItemCode2.value);
	tSQLInfo.addSubPara(fm.AccItemCode3.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
