//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
var turnPageAll = new turnPageClass();
// 查询按钮
function easyQueryClickAll()
{
	// 初始化表格
	initAllNoticeGrid();
	//if((fm.EdorAcceptNo.value == null || fm.EdorAcceptNo.value == "")&& (fm.ContNo.value == null || fm.ContNo.value == "")){
	//	alert("查询条件至少要录入保全受理号或个人保单号！");
	//	return;
	//}
	// 书写SQL语句
	//if((fm.StartDate.value == null || trim(fm.StartDate.value) == "")||(fm.EndEate.value == null || trim(fm.EndEate.value) == "")){
	//	 	alert("请录入下发日期的统计区间!");
	//	 	return;
	//}
	//
	//var tManageCom = fm.ManageCom.value;
	//if(tManageCom.length<4){
	//	alert("请以分公司及分公司以下的机构进行查询！");
	//	return;	
	//}
	
	var strSQL = "";
	var strLike = "like";
	var strEqual="=";
//	var sEdorPart = getWherePart('b.EdorAcceptNo','EdorAcceptNo',strEqual);
	
  		
	//查询全部
//	strSQL = " select EdorAcceptNo,ContNo,InsuredNo,NoticeType,NoticeTypeName,CreateOperator,ManageCom,MakeDate,AskContent,ReplyContent from ( "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'0' NoticeType,'保全审批问题件' NoticeTypeName, a.StandbyFlag2 CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,a.StandbyFlag5 AskContent,a.StandbyFlag6 ReplyContent from LOPrtManager a, LPEdorItem b, LCCont c where a.Code = 'BQ38' "
//         + " and a.OtherNo = c.ContNo and a.standbyflag1 = b.EdorAcceptNo and b.ContNo = c.ContNo and b.Managecom like '"+manageCom+"%'"
//				 + sEdorPart 
//				 + " union "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,(case a.Code when 'BQ33' then '4' when 'BQ37' then '5' end) NoticeType,(case a.Code when 'BQ33' then '保全拒绝通知书' when 'BQ37' then '保全审核通知书' else '' end) NoticeTypeName, "
//				 + " a.StandbyFlag3 CreateOperator,b.ManageCom ManageCom, a.MakeDate MakeDate,a.StandbyFlag2 AskContent,'' ReplyContent from LOPrtmanager a,LPEdorItem b,LCCont c where a.standbyflag1 = b.EdorAcceptNo "
//     	   + " and a.OtherNo = b.ContNo and b.ContNo = c.ContNo and a.Code in ('BQ37','BQ33') and b.ManageCom like '"+manageCom+"%'"
//				 + sEdorPart 
//				 + " union "
//				 + " select b.EdorAcceptNo EdorAcceptNo,c.ContNo ContNo,c.InsuredNo InsuredNo,'1' NoticeType,'人工核保问题件' NoticeTypeName,a.Operator CreateOperator,b.ManageCom ManageCom, "
//         + " a.MakeDate MakeDate,a.Issuecont AskContent,a.ReplyResult ReplyContent "
//         + " from LPIssuePol a,LPEdorItem b,LCCont c where a.EdorNo = b.EdorNo and a.ContNo = b.ContNo and   "
//         + " c.ContNo = b.ContNo and b.Managecom like '"+manageCom+"%' "
//         + sEdorPart +  ") aa where 1 = 1 ";
	//if(fm.StartDate.value != null && fm.StartDate.value != ""){
	//	 	strSQL+= " and MakeDate >= '"+fm.StartDate.value+"'";
	//}
	//
	//if(fm.EndEate.value != null && fm.EndEate.value != ""){
	//	 	strSQL+= " and MakeDate <= '"+fm.EndEate.value+"'";
	//}				 
//	strSQL+= " order by MakeDate "; 
	
	var sqlid1="EdorNoticeQuerySql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("bq.EdorNoticeQuerySql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(manageCom);//指定传入参数
 	mySql1.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
 	mySql1.addSubPara(manageCom);
 	mySql1.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
 	mySql1.addSubPara(manageCom);
 	mySql1.addSubPara(window.document.getElementsByName(trim("EdorAcceptNo"))[0].value);
 	strSQL = mySql1.getString();
	
    
	turnPageAll.queryModal(strSQL, AllNoticeGrid); 
	var rowNum=AllNoticeGrid.mulLineCount;
	if(rowNum < 1)
	{
		alert("没有查到相关的问题件记录!");
	}

}

function returnParent()
{
    
	top.close();

  
}

function ShowInfo()
{
    var i = AllNoticeGrid.getSelNo();

    if (i != '0')
    {
        i = i - 1;
        
        var tAskInfo = AllNoticeGrid.getRowColData(i,9); 

        fm.AskInfo.value=tAskInfo;
        var tReplyInfo = AllNoticeGrid.getRowColData(i,10);
         fm.ReplyInfo.value=tReplyInfo;
    }	
}






