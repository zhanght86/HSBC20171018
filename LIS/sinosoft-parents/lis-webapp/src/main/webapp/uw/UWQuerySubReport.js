//�������ƣ�UWQuerySubReport.js
//�����ܣ��¼��˱�Ա���������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();

function queryClick() {
	// ��дSQL���
//  var strSql = "select a.polno, a.uwoperator, b.username, b.uwpopedom, a.ManageCom, a.ModifyDate, a.ModifyTime "
//             + " from lcuwreport a, lduser b where a.uwoperator=b.usercode and b.uwpopedom<="
//             + " (select uwpopedom from lduser where usercode='" + operator + "')"
//             + " and a.polno='" + PolNo + "'";

  var sqlid1="UWQuerySubReportSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWQuerySubReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(PolNo);//ָ������Ĳ���
	mySql1.addSubPara(operator);//ָ������Ĳ���
	var strSql=mySql1.getString();
	
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}

function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  
	  fm.ProposalNo.value = turnPage.arrDataCacheSet[index][0];
	  fm.Operator.value = turnPage.arrDataCacheSet[index][1];
	  
	  fm.action = "./UWManuReportQuery.jsp";
	  fm.submit();
  }
}

