//***********************************************
//程序名称：RecordQuery.js
//程序功能：操作履历查询
//创建日期：2005-06-23 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件

//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

/*******************************************************************************
 * 返回上一页 参数 ： 无 返回值： 无
 * ********************************************************************
 */

function returnParent() {
	top.close();

}

/*******************************************************************************
 * 查询操作履历信息 参数 ： 无 返回值： 无
 * ********************************************************************
 */

function queryRecord() {

	if (tPrtNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	// tongmeng 2007-12-21 add
	// 增加上报类型的查询

	var sqlid1 = "RecordQuerySql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	mySql1.addSubPara(tPrtNo);// 指定传入的参数
	mySql1.addSubPara(tPrtNo);// 指定传入的参数
	mySql1.addSubPara(tPrtNo);// 指定传入的参数
	mySql1.addSubPara(tPrtNo);// 指定传入的参数
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
	// decode(missionprop11,'1','上报承保','4','返回下级','新契约核保') end ) a9"
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
	// decode(missionprop11,'1','上报承保','4','返回下级','新契约核保') end ) a9"
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
// var tProposalContNo = ContGrid.getRowColData(tSel - 1,1); //合同投保单号
//	
// var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from
// lccustomerimpart where customerno='"+customerNo+"' and
// proposalcontno='"+tProposalContNo+"'";
// 
// turnPage.queryModal(aSQL, ImpartGrid);
//	
// }

/*******************************************************************************
 * 查询按钮函数 参数 ： 无 返回值： 无
 * ********************************************************************
 */

// 问题件查询
function ShowQuest() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../uw/QuestQueryMain.jsp?ContNo=" + PrtNo + "&Flag=5", "",
			sFeatures);
}

// 核保查询
function UWQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../uw/UWQueryMain.jsp?ContType=" + tContType + "&ContNo="
			+ tContNo, "", sFeatures);
}

// 体检结果查询
function HealthQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	// alert("PrtNo"+PrtNo)
	window.open("../uw/UWManuHealthQMain.jsp?ContNo=" + tContNo + "&Flag=2", "",
			sFeatures);
}

// 生调结果查询
function MeetQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../uw/RReportQueryMain.jsp?ContNo=" + PrtNo + "&PrtNo="
			+ PrtNo + "&Flag=1", "", sFeatures);
}

// 影像资料查询
function ImageQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../uw/ImageQueryMain.jsp?ContNo=" + ContNo, "", sFeatures);
}

// 自核提示信息查询
function UWErrQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../uw/UWErrMain.jsp?ContNo=" + ContNo, "", sFeatures);
}

// 再保回复查询
function UpReportQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../uw/UWManuUpReportReply.jsp?ContNo=" + ContNo + "&PrtNo="
			+ tPrtNo + "&Flag=1", "", sFeatures);
}

// 核保通知书查询
function UWNoticQuery() {
	var PrtNo = tPrtNo;

	if (PrtNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../sys/UWNoticQuery.jsp?ContNo=" + PrtNo, "", sFeatures);
}

// 客户合并通知书查询
function KHHBNoticQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("没有得到投保单号信息！");
		return;
	}
	window.open("../sys/CustCombintQuery.jsp?ContNo=" + ContNo, "", sFeatures);

}

// 特约查询
function ShowSpecialQuery() {
	var ContNo = tContNo;

	if (ContNo == "") {
		alert("没有得到投保单号信息!");
		return;
	}
	window.open("../sys/SpecialQueryMain.jsp?ContNo=" + ContNo, "", sFeatures);

}

// 记事本查询
function ShowNotepadQuery() {
	var ContNo = tContNo;
	var PrtNo = tPrtNo;
	if (ContNo == "") {
		alert("没有得到投保单号信息!");
		return;
	}
	window.open("../sys/NotepadQueryMain.jsp?PrtNo=" + PrtNo, "", sFeatures);

}

// 返回
function GoBack() {
	top.close();
}

// 控制界面按钮的明暗显示
function ctrlButtonDisabled() {

	var tContNo = tPrtNo;
	var tSQL = "";
	var arrResult;
	var arrButtonAndSQL = new Array;

	arrButtonAndSQL[0] = new Array;
	arrButtonAndSQL[0][0] = "Button1";
	arrButtonAndSQL[0][1] = "体检结果查询";
	if (_DBT == _DBO) {
//		arrButtonAndSQL[0][2] = "select * from lcpenotice a, loprtmanager b where 1 = 1 and a.ProposalContNo = '"
//				+ tContNo + "' and a.prtseq = b.oldprtseq and rownum=1";
		
		var sqlid2="RecordQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
		mySql2.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[0][2]= mySql2.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[0][2] = "select * from lcpenotice a, loprtmanager b where 1 = 1 and a.ProposalContNo = '"
//				+ tContNo + "' and a.prtseq = b.oldprtseq limit 1";
		
		var sqlid3="RecordQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql3.setSqlId(sqlid3);// 指定使用的Sql的id
		mySql3.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[0][2]= mySql3.getString();
		
	}

	arrButtonAndSQL[1] = new Array;
	arrButtonAndSQL[1][0] = "Button2";
	arrButtonAndSQL[1][1] = "生调结果查询";
	if (_DBT == _DBO) {
//		arrButtonAndSQL[1][2] = "select * from lcrreport where 1 = 1 and contno = '"
//				+ tContNo + "' and rownum = 1";
		
		var sqlid4="RecordQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql4.setSqlId(sqlid4);// 指定使用的Sql的id
		mySql4.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[1][2]= mySql4.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[1][2] = "select * from lcrreport where 1 = 1 and contno = '"
//				+ tContNo + "' limit 1";
		
		var sqlid5="RecordQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql5.setSqlId(sqlid5);// 指定使用的Sql的id
		mySql5.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[1][2]= mySql5.getString();
		
	}

	arrButtonAndSQL[2] = new Array;
	arrButtonAndSQL[2][0] = "Button3";
	arrButtonAndSQL[2][1] = "核保查询";
	if (_DBT == _DBO) {
//		arrButtonAndSQL[2][2] = "select 1 from lcuwsub where contno = '"
//				+ tContNo + "' and  passflag is not null and rownum = 1 ";
		
		var sqlid6="RecordQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql6.setSqlId(sqlid6);// 指定使用的Sql的id
		mySql6.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[2][2]= mySql6.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[2][2] = "select 1 from lcuwsub where contno = '"
//				+ tContNo + "' and  passflag is not null limit 1 ";
		
		var sqlid7="RecordQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql7.setSqlId(sqlid7);// 指定使用的Sql的id
		mySql7.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[2][2]= mySql7.getString();
		
	}

	arrButtonAndSQL[3] = new Array;
	arrButtonAndSQL[3][0] = "Button4";
	arrButtonAndSQL[3][1] = "再保回复查询";
	if (_DBT == _DBO) {

//		arrButtonAndSQL[3][2] = "select * from LCReinsureReport where proposalcontno = '"
//				+ tContNo + "' and rownum = 1";
		
		var sqlid8="RecordQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql8.setSqlId(sqlid8);// 指定使用的Sql的id
		mySql8.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[3][2]= mySql8.getString();
		
	} else if (_DBT == _DBM) {
//		arrButtonAndSQL[3][2] = "select * from LCReinsureReport where proposalcontno = '"
//				+ tContNo + "' limit 1";
		
		var sqlid9="RecordQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql9.setSqlId(sqlid9);// 指定使用的Sql的id
		mySql9.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[3][2]= mySql9.getString();

	}
	/*
	 * arrButtonAndSQL[4] = new Array; arrButtonAndSQL[4][0] = "Button5";
	 * arrButtonAndSQL[4][1] = "客户合并通知书查询"; arrButtonAndSQL[4][2] = "select *
	 * from lcissuepol where 2 = 2 and issuetype='99' and OperatePos in ('0',
	 * '1', '5', 'A', 'I') and ProposalContNo = '"+tContNo+"' and rownum=1";
	 */
	arrButtonAndSQL[4] = new Array;
	arrButtonAndSQL[4][0] = "Button6";
	arrButtonAndSQL[4][1] = "自核提示查询";
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
	mySql10.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
	mySql10.setSqlId(sqlid10);// 指定使用的Sql的id
	mySql10.addSubPara(tContNo);// 指定传入的参数
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	mySql10.addSubPara(tContNo);
	arrButtonAndSQL[4][2]= mySql10.getString();
	
	
	arrButtonAndSQL[5] = new Array;
	arrButtonAndSQL[5][0] = "Button7";
	arrButtonAndSQL[5][1] = "问题件查询";
	if(_DBT==_DBO){
//		arrButtonAndSQL[5][2] = "select * from lcissuepol where 2 = 2 and issuetype<>'99' and OperatePos in ('0', '1', '5', 'A', '6','I') and ProposalContNo = '"
//			+ tContNo + "' and rownum=1";
		
		var sqlid11="RecordQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql11.setSqlId(sqlid11);// 指定使用的Sql的id
		mySql11.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[5][2]= mySql11.getString();
		
	}else if(_DBT==_DBM){
//		arrButtonAndSQL[5][2] = "select * from lcissuepol where 2 = 2 and issuetype<>'99' and OperatePos in ('0', '1', '5', 'A', '6','I') and ProposalContNo = '"
//			+ tContNo + "' limit 1";
		
		var sqlid12="RecordQuerySql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql12.setSqlId(sqlid12);// 指定使用的Sql的id
		mySql12.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[5][2]= mySql12.getString();
		
	}
	/*
	 * arrButtonAndSQL[7] = new Array; arrButtonAndSQL[7][0] = "Button8";
	 * arrButtonAndSQL[7][1] = "核保通知书查询"; arrButtonAndSQL[7][2] = " select
	 * prtseq,otherno,othernotype,agentcode,code,stateflag from loprtmanager
	 * where otherno = '"+tContNo+"' and Code in
	 * ('00','06','81','82','83','84','85','86','87','88','89', 'BQ80', 'BQ81',
	 * 'BQ82', 'BQ83', 'BQ84', 'BQ85', 'BQ86', 'BQ87', 'BQ88', 'BQ89') and
	 * rownum=1";
	 */
	arrButtonAndSQL[6] = new Array;
	arrButtonAndSQL[6][0] = "Button9";
	arrButtonAndSQL[6][1] = "记事本查询";
	if(_DBT==_DBO){
//		arrButtonAndSQL[6][2] = "select * from LWNotePad where otherno='" + tContNo
//		+ "' and rownum=1";
		
		var sqlid13="RecordQuerySql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql13.setSqlId(sqlid13);// 指定使用的Sql的id
		mySql13.addSubPara(tContNo);// 指定传入的参数
		arrButtonAndSQL[6][2]= mySql13.getString();
		
	}else if(_DBT==_DBM){
//		arrButtonAndSQL[6][2] = "select * from LWNotePad where otherno='" + tContNo
//		+ "' limit 1";
		
		var sqlid14="RecordQuerySql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("sys.RecordQuerySql"); // 指定使用的properties文件名
		mySql14.setSqlId(sqlid14);// 指定使用的Sql的id
		mySql14.addSubPara(tContNo);// 指定传入的参数
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
