/***************************************************************
 * <p>ProName��LDQuestionInput.js</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var mQuesID = "";
var tSQLInfo = new SqlClass();
var tNBPOSFlag = "";

/**
 * ��ѯ
 */
function queryClick(tMark) {
	
	var tLimitSendNode = "";
	var tLimitReplyNode = "";
	if (tOtherNoType=="QUOT") {//modify by songsz 20140522 ����ѯ�۽ڵ�����
		
		if (tActivityID=="0800100001") {//ѯ��¼��
			
			tLimitSendNode = " and a.sendnode in('0800100002','0800100003') ";
			tLimitReplyNode = " and a.replynode='0800100001' ";
		} else if (tActivityID=="0800100002") {//�к�
			
			tLimitSendNode = " and a.sendnode in('0800100002','0800100003') ";
		} 
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_busicommon.LDQuestiontSql");
	if(_DBT == _DBO ){
		tSQLInfo.setSqlId("LDQuestiontSql1");
	}else if( _DBT == _DBM){
	    tSQLInfo.setSqlId("LDQuestiontSql11");
    }
	tSQLInfo.addSubPara(tOtherNoType);
	tSQLInfo.addSubPara(tOtherNo);
	if (tSubOtherNo==null || tSubOtherNo=="" || tSubOtherNo=="null") {
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara(tSubOtherNo);
	}
	tSQLInfo.addSubPara(fm.SendStartDate.value);
	tSQLInfo.addSubPara(fm.SendEndDate.value);
	tSQLInfo.addSubPara(fm.QuesState.value);
	tSQLInfo.addSubPara(fm.ReplyStartDate.value);
	tSQLInfo.addSubPara(fm.ReplyEndDate.value);
	tSQLInfo.addSubPara(tLimitSendNode);
	tSQLInfo.addSubPara(tLimitReplyNode);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuestionGrid);
	if (tMark=="1") {
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ���������
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	
	submitForm();
}

/**
 * �޸������
 */
function modifyClick() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	mQuesID = QuestionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "MODIFY";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ɾ�������
 */
function deleteClick() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	mQuesID = QuestionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * �ظ������
 */
function replyClick() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	if (fm.ReplyContent.value ==null || fm.ReplyContent.value == "") {
		alert("�ظ����ݲ���Ϊ�գ�");
		return false;
	}
	
	mQuesID = QuestionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "REPLY";
	submitForm();
}

/**
 * չʾ�������Ϣ
 */
function showDetail() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.QuesType.value = QuestionGrid.getRowColData(tSelNo-1, 2);
	fm.QuesTypeName.value = QuestionGrid.getRowColData(tSelNo-1, 3);
	fm.SendContent.value = QuestionGrid.getRowColData(tSelNo-1, 4);
	fm.ReplyContent.value = QuestionGrid.getRowColData(tSelNo-1, 5);
	
	var tState = QuestionGrid.getRowColData(tSelNo-1, 6);
	var tSelSendNode = QuestionGrid.getRowColData(tSelNo-1, 8);
	var tSelReplyNode = QuestionGrid.getRowColData(tSelNo-1, 11);
	var tMaketake = QuestionGrid.getRowColData(tSelNo-1, 14);
	//add by dianj ����Ӱ��֤ϸ��
	fm.SubType.value = QuestionGrid.getRowColData(tSelNo-1, 17);
	fm.SubTypeName.value = QuestionGrid.getRowColData(tSelNo-1, 18);
	
	if (tSelSendNode==tActivityID) {
		
		if (tState=="0") {//δ�ظ�,�����޸ļ�ɾ��
			
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "none";
		} else {//���������չʾ��ذ�ť
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "";
		}
	} else if (tSelReplyNode==tActivityID) {
		
		if (tState=="0" || tState=="1") {//δ�ظ�,�����޸ļ�ɾ��
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "";
			divReplyContent.style.display = "";
		} else {//���������չʾ��ذ�ť
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "";
		}
	} else if("NB"==tOtherNoType || "POS"==tOtherNoType){alert("4");
		fm.ModifyButton.style.display = "none";
		fm.DeleteButton.style.display = "none";
		fm.ReplyButton.style.display = "none";
		divReplyContent.style.display = "";
	}else {
		
		if (tState=="0" || tState=="1") {//��Ϊ�ѻظ����ύ״̬,�����Գ����ظ�
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "";
			divReplyContent.style.display = "";
		} else if (tState=="2") {
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "";
		}
	}
	if("1"==tMaketake){
		fm.Mistake.checked = true;
		fm.Mistake.value="1";
	}else {
		fm.Mistake.checked = false;
		fm.Mistake.value="0";
	}
	///add by dianj ����Ӱ���������֤����
	if(fm.QuesType.value == "00"){
		divSubTypeName.style.display="";
		divSubTypeCode.style.display="";
	} else {
		divSubTypeName.style.display="none";
		divSubTypeCode.style.display="none";
	}
	//add by ZhangC 20141216 ѯ�۲�ѯʱ�������������ťչʾ
	//�� tShowFlag = 1 ʱ��ֻչʾ���Ի�չʾ����ť
	if (tShowFlag=="1") {
		
		fm.AddButton.style.display = "none";
		fm.ModifyButton.style.display = "none";
		fm.DeleteButton.style.display = "none";
		fm.ReplyButton.style.display = "none";
		fm.ShowButton.style.display = "";
	}
}

/**
 * ������Ի�չʾ
 */
function showClick() {
	
	window.open("../g_busicommon/LDQuestionShowMain.jsp?OtherNoType="+tOtherNoType+"&OtherNo="+tOtherNo+"&SubOtherNo="+tSubOtherNo+"&ActivityID="+tActivityID+"&ShowStyle="+tShowStyle,"������Ի�չʾ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �ύ
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./LDQuestionSave.jsp?OtherNoType="+tOtherNoType+"&OtherNo="+tOtherNo+"&SubOtherNo="+tSubOtherNo+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&QuesID="+mQuesID;
	fm.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	initForm();
}

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="INSERT" || mOperate=="MODIFY") {
		
		//add by dianj ����Ӱ���������֤ϸ���Ǳ���¼�����
		if(fm.QuesType.value=="00" && fm.SubType.value ==""){
			alert("Ӱ�����������ѡ��֤���ͣ�");
			return false;
		}
		if (tShowStyle=="2") {
			
			if (fm.QuesType.value=="") {
				
				alert("��������Ͳ���Ϊ�գ�");
				return false;
			}
		}
		if (fm.SendContent.value=="") {
			
			alert("�������ݲ���Ϊ�գ�");
			return false;
		}
		
		if (fm.SendContent.value.length>1500) {
			
			alert("�������ݹ�����");
			return false;
		}
	}
	
	if (mOperate=="REPLY") {
		
		if (fm.ReplyContent.value=="") {
			
			alert("�ظ����ݲ���Ϊ�գ�");
			return false;
		}
		
		if (fm.ReplyContent.value.length>1500) {
			
			alert("�ظ����ݹ�����");
			return false;
		}
	}
	
	return true;
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

	if (value1=="questype") {
		
		var tSql = "1 and codetype=#"+value1+"# and codeexp=#"+tActivityID+"# and othersign=#"+tOtherNoType+"# and code in (select code from ldcode where codetype=#questype#  and (othersign is null or othersign in (select activityid from lbmission b where missionprop1=#"+tOtherNo+"#))) ";
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
	if (value1=="subtype") {
		
		var tSql = "1 and b.doccode = substr(#"+tOtherNo+"#,1,18) ";
		
		if (returnType=='0') {
			return showCodeList('subtype',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('subtype',value2,value3,null,tSql,'1','1',null);
		}
	}
}
/**
** �Ƿ������
**/
function checkClick(){
	if(fm.Mistake.checked){
		fm.Mistake.value="1";
	}else {
		fm.Mistake.value="0";
	}
}
//�ж��������Ӱ���������������
function afterCodeSelect(o, p){
	 
	if(o=='queryexp'){
		if(p.name=="QuesType" && p.value=="00" ){
			divSubTypeName.style.display="";
			divSubTypeCode.style.display="";
		} else {
			divSubTypeName.style.display="none";
			divSubTypeCode.style.display="none";
		}
	}
}
