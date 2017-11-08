//程序名称：UWQuerySubReport.js
//程序功能：下级核保员分析报告查询
//创建日期：2002-09-24 11:10:36
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.UWQueryOldSubReportSql";
function queryClick() {
	// 书写SQL语句
	/*
  var strSql = "select a.polno, a.uwoperator, b.username, b.uwpopedom, a.ManageCom, a.ModifyDate, a.ModifyTime "
             + " from lcuwreport a, lduser b where a.uwoperator=b.usercode "
             + " and a.polno='" + PolNo + "'"
             + " order by a.ModifyDate ";
      */       
	var sqlid1="UWQueryOldSubReportSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(PolNo);
  turnPage.queryModal(mySql1.getString(), QuestGrid); 
  
  fm.Content.value = "";
}

function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = fm.all(parm1).all('QuestGridNo').value - 1;
	  
	  fm.ProposalNo.value = turnPage.arrDataCacheSet[index][0];
	  fm.Operator.value = turnPage.arrDataCacheSet[index][1];
	  
	  fm.action = "./UWManuReportQuery.jsp";
	  fm.submit();
  }
}

