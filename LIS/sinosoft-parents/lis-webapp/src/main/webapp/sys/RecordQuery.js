//***********************************************
//�������ƣ�RecordQuery.js
//�����ܣ�����������ѯ
//�������ڣ�2005-06-23 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

/*******************************************************************************
 * ������һҳ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */

function returnParent() {
	top.close();

}

/*******************************************************************************
 * ��ѯ����������Ϣ ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */

function queryRecord() {

	if (tPrtNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	// tongmeng 2007-12-21 add
	// �����ϱ����͵Ĳ�ѯ

	var sqlid1 = "RecordQuerySql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tPrtNo);// ָ������Ĳ���
	mySql1.addSubPara(tPrtNo);// ָ������Ĳ���
	mySql1.addSubPara(tPrtNo);// ָ������Ĳ���
	mySql1.addSubPara(tPrtNo);// ָ������Ĳ���
	var aSQL = mySql1.getString();

	// var aSQL = "select a1,a2,a3,a4,a5,a6,a9"
	// + " from (select '"+tPrtNo+"' as a1,"
	// + " nvl((select usercode"
	// + " from lduser"
	// + " where '1135241645000' = '1135241645000'"
	// + " and usercode = defaultoperator),"
	// + " '') as a2,"
	// + " to_char(indate, 'YYYY-MM-DD') || ' ' || intime as a3,"
	// + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime as a4,"
	// + " '1' as a5,"
	// + " (select activityname from lwactivity where activityid = a.activityid)
	// as
	// a6,"
	// + " outdate as a7,"
	// + " outtime as a8,"
	// + "(case when activityid='0000001110' then
	// decode(missionprop11,'1','�ϱ��б�','4','�����¼�','����Լ�˱�') end ) a9"
	// + " from lbmission a"
	// + " where 1 = 1 "
	// + " and missionid = (select missionid"
	// + " from lbmission"
	// + " where missionprop1 = '"+tPrtNo+"'"
	// + " and rownum = 1)"
	// + " and activityid not in ('0000001270', '0000001104', '0000001101')"
	// + " and outdate is not null"
	// + " union"
	// + " select '"+tPrtNo+"' as a1,"
	// + " '' as a2,"
	// + " to_char(indate, 'YYYY-MM-DD') || ' ' || intime as a3,"
	// + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime as a4,"
	// + " '1' as a5,"
	// + " (select activityname from lwactivity where activityid = b.activityid)
	// as
	// a6,"
	// + " outdate as a7,"
	// + " outtime as a8,"
	// + " (case when activityid='0000001110' then
	// decode(missionprop11,'1','�ϱ��б�','4','�����¼�','����Լ�˱�') end ) a9"
	// + " from lbmission b"
	// + " where 1 = 1 "
	// + " and missionid = (select missionid"
	// + " from lbmission"
	// + " where missionprop1 = '"+tPrtNo+"'"
	// + " and rownum = 1)"
	// + " and activityid in ('0000001270', '0000001104', '0000001101')"
	// + " and outdate is not null)"
	// + " order by a7, a8";

	turnPage2.queryModal(aSQL, RecordGrid);

}

// function queryPersonInfo(){
// var aSQL = "select a.customerno,a.name from ldperson a where
// a.customerno='"+customerNo+"'";
// var arrResult = easyExecSql(aSQL);
// document.all('CustomerNo').value = arrResult[0][0];
// document.all('CustomerName').value = arrResult[0][1];
// }

// function getContDetail(){
// //alert();
//	
// }

// function contInfoClick(){
//  
// var tSel = ContGrid.getSelNo();
// var tProposalContNo = ContGrid.getRowColData(tSel - 1,1); //��ͬͶ������
//	
// var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from
// lccustomerimpart where customerno='"+customerNo+"' and
// proposalcontno='"+tProposalContNo+"'";
// 
// turnPage.queryModal(aSQL, ImpartGrid);
//	
// }

/*******************************************************************************
 * ��ѯ��ť���� ���� �� �� ����ֵ�� ��
 * ********************************************************************
 */

// �������ѯ
function ShowQuest() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../uw/QuestQueryMain.jsp?ContNo=" + PrtNo + "&Flag=5", "",
			sFeatures);
}

// �˱���ѯ
function UWQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../uw/UWQueryMain.jsp?ContType=" + tContType + "&ContNo="
			+ tContNo, "", sFeatures);
}

// �������ѯ
function HealthQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	// alert("PrtNo"+PrtNo)
	window.open("../uw/UWManuHealthQMain.jsp?ContNo=" + tContNo + "&Flag=2", "",
			sFeatures);
}

// ���������ѯ
function MeetQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../uw/RReportQueryMain.jsp?ContNo=" + PrtNo + "&PrtNo="
			+ PrtNo + "&Flag=1", "", sFeatures);
}

// Ӱ�����ϲ�ѯ
function ImageQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../uw/ImageQueryMain.jsp?ContNo=" + ContNo, "", sFeatures);
}

// �Ժ���ʾ��Ϣ��ѯ
function UWErrQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../uw/UWErrMain.jsp?ContNo=" + ContNo, "", sFeatures);
}

// �ٱ��ظ���ѯ
function UpReportQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../uw/UWManuUpReportReply.jsp?ContNo=" + ContNo + "&PrtNo="
			+ tPrtNo + "&Flag=1", "", sFeatures);
}

// �˱�֪ͨ���ѯ
function UWNoticQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../sys/UWNoticQuery.jsp?ContNo=" + PrtNo, "", sFeatures);
}

// �ͻ��ϲ�֪ͨ���ѯ
function KHHBNoticQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("û�еõ�Ͷ��������Ϣ��");
		return;
	}
	window.open("../sys/CustCombintQuery.jsp?ContNo=" + ContNo, "", sFeatures);

}

// ��Լ��ѯ
function ShowSpecialQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("û�еõ�Ͷ��������Ϣ!");
		return;
	}
	window.open("../sys/SpecialQueryMain.jsp?ContNo=" + ContNo, "", sFeatures);

}

// ���±���ѯ
function ShowNotepadQuery() {
	var ContNo = tContNo;
	var PrtNo = tPrtNo;
	if (ContNo == "") {
		alert("û�еõ�Ͷ��������Ϣ!");
		return;
	}
	window.open("../sys/NotepadQueryMain.jsp?PrtNo=" + PrtNo, "", sFeatures);

}

// ����
function GoBack() {
	top.close();
}

// ���ƽ��水ť��������ʾ
function ctrlButtonDisabled() {

	var tContNo = tPrtNo;
	var tSQL = "";
	var arrResult;
	var arrButtonAndSQL = new Array;

	arrButtonAndSQL[0] = new Array;
	arrButtonAndSQL[0][0] = "Button1";
	arrButtonAndSQL[0][1] = "�������ѯ";
	if (_DBT == _DBO) {
//		arrButtonAndSQL[0][2] = "select * from lcpenotice a, loprtmanager b where 1 = 1 and a.ProposalContNo = '"
//				+ tContNo + "' and a.prtseq = b.oldprtseq and rownum=1";
		
		var sqlid2="RecordQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[0][2]= mySql2.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[0][2] = "select * from lcpenotice a, loprtmanager b where 1 = 1 and a.ProposalContNo = '"
//				+ tContNo + "' and a.prtseq = b.oldprtseq limit 1";
		
		var sqlid3="RecordQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[0][2]= mySql3.getString();
		
	}

	arrButtonAndSQL[1] = new Array;
	arrButtonAndSQL[1][0] = "Button2";
	arrButtonAndSQL[1][1] = "���������ѯ";
	if (_DBT == _DBO) {
//		arrButtonAndSQL[1][2] = "select * from lcrreport where 1 = 1 and contno = '"
//				+ tContNo + "' and rownum = 1";
		
		var sqlid4="RecordQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);// ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[1][2]= mySql4.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[1][2] = "select * from lcrreport where 1 = 1 and contno = '"
//				+ tContNo + "' limit 1";
		
		var sqlid5="RecordQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);// ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[1][2]= mySql5.getString();
		
	}

	arrButtonAndSQL[2] = new Array;
	arrButtonAndSQL[2][0] = "Button3";
	arrButtonAndSQL[2][1] = "�˱���ѯ";
	if (_DBT == _DBO) {
//		arrButtonAndSQL[2][2] = "select 1 from lcuwsub where contno = '"
//				+ tContNo + "' and  passflag is not null and rownum = 1 ";
		
		var sqlid6="RecordQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);// ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[2][2]= mySql6.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[2][2] = "select 1 from lcuwsub where contno = '"
//				+ tContNo + "' and  passflag is not null limit 1 ";
		
		var sqlid7="RecordQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);// ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[2][2]= mySql7.getString();
		
	}

	arrButtonAndSQL[3] = new Array;
	arrButtonAndSQL[3][0] = "Button4";
	arrButtonAndSQL[3][1] = "�ٱ��ظ���ѯ";
	if (_DBT == _DBO) {

//		arrButtonAndSQL[3][2] = "select * from LCReinsureReport where proposalcontno = '"
//				+ tContNo + "' and rownum = 1";
		
		var sqlid8="RecordQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);// ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[3][2]= mySql8.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[3][2] = "select * from LCReinsureReport where proposalcontno = '"
//				+ tContNo + "' limit 1";
		
		var sqlid9="RecordQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);// ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[3][2]= mySql9.getString();

	}
	/*
	 * arrButtonAndSQL[4] = new Array; arrButtonAndSQL[4][0] = "Button5";
	 * arrButtonAndSQL[4][1] = "�ͻ��ϲ�֪ͨ���ѯ"; arrButtonAndSQL[4][2] = "select *
	 * from lcissuepol where 2 = 2 and issuetype='99' and OperatePos in ('0',
	 * '1', '5', 'A', 'I') and ProposalContNo = '"+tContNo+"' and rownum=1";
	 */
	arrButtonAndSQL[4] = new Array;
	arrButtonAndSQL[4][0] = "Button6";
	arrButtonAndSQL[4][1] = "�Ժ���ʾ��ѯ";
//	arrButtonAndSQL[4][2] = "select 1 from LCUWError a where proposalcontno = '"
//			+ tContNo
//			+ "' "
//			+ " and (a.uwno = (select c.batchno "
//			+ " from LCUWMaster c where c.ContNo = '"
//			+ tContNo
//			+ "' "
//			+ " and c.PolNo = a.PolNo)) "
//			+ " union "
//			+ " select 1 from LCCUWError a where proposalcontno = '"
//			+ tContNo
//			+ "' "
//			+ " and (a.uwno = (select c.batchno "
//			+ " from LCCUWMaster c where c.ContNo = '"
//			+ tContNo
//			+ "')) "
//			+ " union "
//			+ " select 1 from LCIndUWError a where proposalcontno = '"
//			+ tContNo
//			+ "' "
//			+ " and (a.uwno = (select c.batchno "
//			+ " from LCCUWMaster c where c.ContNo = '"
//			+ tContNo
//			+ "' and a.insuredno=c.insuredno))";

	var sqlid10="RecordQuerySql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);// ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(tContNo);// ָ������Ĳ���
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	arrButtonAndSQL[4][2]= mySql10.getString();
	
	
	arrButtonAndSQL[5] = new Array;
	arrButtonAndSQL[5][0] = "Button7";
	arrButtonAndSQL[5][1] = "�������ѯ";
	if(_DBT==_DBO){
//		arrButtonAndSQL[5][2] = "select * from lcissuepol where 2 = 2 and issuetype<>'99' and OperatePos in ('0', '1', '5', 'A', '6','I') and ProposalContNo = '"
//			+ tContNo + "' and rownum=1";
		
		var sqlid11="RecordQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);// ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[5][2]= mySql11.getString();
		
	}else if(_DBT==_DBM){
//		arrButtonAndSQL[5][2] = "select * from lcissuepol where 2 = 2 and issuetype<>'99' and OperatePos in ('0', '1', '5', 'A', '6','I') and ProposalContNo = '"
//			+ tContNo + "' limit 1";
		
		var sqlid12="RecordQuerySql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);// ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[5][2]= mySql12.getString();
		
	}
	/*
	 * arrButtonAndSQL[7] = new Array; arrButtonAndSQL[7][0] = "Button8";
	 * arrButtonAndSQL[7][1] = "�˱�֪ͨ���ѯ"; arrButtonAndSQL[7][2] = " select
	 * prtseq,otherno,othernotype,agentcode,code,stateflag from loprtmanager
	 * where otherno = '"+tContNo+"' and Code in
	 * ('00','06','81','82','83','84','85','86','87','88','89', 'BQ80', 'BQ81',
	 * 'BQ82', 'BQ83', 'BQ84', 'BQ85', 'BQ86', 'BQ87', 'BQ88', 'BQ89') and
	 * rownum=1";
	 */
	arrButtonAndSQL[6] = new Array;
	arrButtonAndSQL[6][0] = "Button9";
	arrButtonAndSQL[6][1] = "���±���ѯ";
	if(_DBT==_DBO){
//		arrButtonAndSQL[6][2] = "select * from LWNotePad where otherno='" + tContNo
//		+ "' and rownum=1";
		
		var sqlid13="RecordQuerySql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);// ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[6][2]= mySql13.getString();
		
	}else if(_DBT==_DBM){
//		arrButtonAndSQL[6][2] = "select * from LWNotePad where otherno='" + tContNo
//		+ "' limit 1";
		
		var sqlid14="RecordQuerySql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("sys.RecordQuerySql"); // ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);// ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(tContNo);// ָ������Ĳ���
		arrButtonAndSQL[6][2]= mySql14.getString();
		
	}

	for ( var i = 0; i < arrButtonAndSQL.length; i++) {
		if (arrButtonAndSQL[i][2] != null && arrButtonAndSQL[i][2] != "") {
			arrResult = easyExecSql(arrButtonAndSQL[i][2]);
			if (arrResult != null) {
				eval("document.all('" + arrButtonAndSQL[i][0]
						+ "').disabled=''");
			} else {
				eval("document.all('" + arrButtonAndSQL[i][0]
						+ "').disabled='true'");
			}
		} else {
			continue;
		}
	}

}
