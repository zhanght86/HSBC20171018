/***************************************************************
 * <p>ProName:EdorCAInput.js</p>
 * <p>Title:  �˻����ת��</p>
 * <p>Description:�˻����ת��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-25
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

//ԭ����������Ϣ��ѯ
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCASql");
	tSQLInfo.setSqlId("EdorCASql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ�����������Ĳ�ѯ���!");
		return false;
	}
}

//�޸Ĺ��ı���������Ϣ��ѯ
function queryUpClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCASql");
	tSQLInfo.setSqlId("EdorCASql2");
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

//����ר��ҽ���˻���Ϣ��ѯ
function queryGroupAcc(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCASql");
	tSQLInfo.setSqlId("EdorCASql3");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage3.queryModal(tSQLInfo.getString(), GroupAccGrid, 1, 1);
}

//�˻����ת��
function moveClick(){
	
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
	
	var tInAmountFlag = false;
	var tOutAmountFlag = false;
	var tSumInAmount = 0;
	for (var i=0;i < OldInsuredInfoGrid.mulLineCount;i++) {
		
		if (OldInsuredInfoGrid.getChkNo(i)) {
			
			var tInsuredType = OldInsuredInfoGrid.getRowColData(i,1);
			var tInsuAccBala = OldInsuredInfoGrid.getRowColData(i,11);
			var tInAmount = OldInsuredInfoGrid.getRowColData(i,12);
			var tOutAmount = OldInsuredInfoGrid.getRowColData(i,13);
			
			if ((tInAmount==null || tInAmount=="") && (tOutAmount==null || tOutAmount=="")) {
				alert("��"+(i+1)+"��ת������ת������ͬʱΪ�գ�");
				return false;
			}
			
			if ((tInAmount!=null && tInAmount!="") && (tOutAmount!=null && tOutAmount!="")) {
				alert("��"+(i+1)+"��ת������ת������ͬʱ¼�룡");
				return false;
			}
			
			if (tInAmount!=null && tInAmount!="") {
				
				tInAmountFlag = true;
				
				if (!isNumeric(tInAmount)) {
					alert("��"+(i+1)+"��ת������Ҫ¼����ڵ���0�����֣�");
					return false;
				}
				
				tSumInAmount = tSumInAmount + parseFloat(tInAmount);
			}
			
			if (tOutAmount!=null && tOutAmount!="") {
				
				tOutAmountFlag = true;
				
				if (!isNumeric(tOutAmount)) {
					alert("��"+(i+1)+"��ת�������Ҫ¼����ڵ���0�����֣�");
					return false;
				}
				
				if (parseFloat(tOutAmount)>parseFloat(tInsuAccBala)) {
					alert("��"+(i+1)+"��ת�����ܴ����˻���Ϣ�ͣ�");
					return false;
				}
			}
			
			if (tInAmountFlag && tOutAmountFlag) {
				alert("ת������ת������ͬʱ¼�룡");
				return false;
			}
		}
	}
	
	if (GroupAccGrid.mulLineCount>0) {
		
		var tGroupName = GroupAccGrid.getRowColData(0,2);
		var tGroupAcc = GroupAccGrid.getRowColData(0,5);
		
		if (parseFloat(tSumInAmount)>parseFloat(tGroupAcc)) {
			alert("ת����֮�ʹ���"+tGroupName+"��Ϣ��!");
			return false;
		}
	}
	
	mOperate="UPDATE";
	fm.action = "./EdorCASave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	fm.action = "./EdorCASave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	
	queryOldClick();
	queryUpClick(2);
}
