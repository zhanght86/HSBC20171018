
//�������ƣ�CollectorQuery.js
//�����ܣ��շ�Ա��ѯ
//�������ڣ�2005-09-30 11:10:36
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
 
var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLCollectorGrid()
{   
   var strSQL = "";

  /* strSQL = " select distinct a.agentcode,c.branchattr,a.managecom,a.name,(select codename from ldcode where codetype='sex' and code=sex),a.idno,a.agentstate,a.phone,a.mobile from laagent a,lccont d,LABranchGroup c "
   			+" where a.agentcode = (d.agentcode) and (a.AgentCode)='"+document.all('AgentCode').value+"' "
   			+" and a.AgentGroup = c.AgentGroup";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.CollectorQuerySql");
	mySql.setSqlId("CollectorQuerySql1");
	mySql.addSubPara(document.all('AgentCode').value); 
	turnPage.queryModal(mySql.getString(), CollectorGrid);


//�ж��Ƿ��ѯ�ɹ�
 turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
  if (!turnPage.strQueryResult) {
  	
  	CollectorGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
    return false;
  }
}