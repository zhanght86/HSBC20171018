//�������ƣ�UWQuerySubReport.js
//�����ܣ��¼��˱�Ա���������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var sqlresourcename = "uwgrp.UWQueryOldSubReportSql";
function queryClick() {
	// ��дSQL���
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
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(fm.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = fm.all(parm1).all('QuestGridNo').value - 1;
	  
	  fm.ProposalNo.value = turnPage.arrDataCacheSet[index][0];
	  fm.Operator.value = turnPage.arrDataCacheSet[index][1];
	  
	  fm.action = "./UWManuReportQuery.jsp";
	  fm.submit();
  }
}

