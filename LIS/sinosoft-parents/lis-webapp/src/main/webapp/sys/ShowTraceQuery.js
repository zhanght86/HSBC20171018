//�������ƣ�ShowTraceQuery.js
//�����ܣ���������켣��ѯ
//�������ڣ�2005-11-24 11:19
//������  ����Ρ
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLTraceGrid()
{   
   var strSQL = "";

  /* strSQL =" select startserdate,endserdate,b.agentcode,name "
           +"  from laagent a, lacommisiondetail b "
           +" where a.agentcode = b.agentcode "
           +"   and grpcontno = '"+fm.all('ContNo').value+"'"
           +" union select startserdate,endserdate,c.agentcode,name "
           +"  from laagent a, lacommisiondetailb c "
           +" where a.agentcode = c.agentcode "
           +"   and grpcontno = '"+fm.all('ContNo').value+"'"
           +" order by startserdate "*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.ShowTraceQuerySql");
	mySql.setSqlId("ShowTraceQuerySql1");
	mySql.addSubPara(fm.all('ContNo').value ); 
	mySql.addSubPara(fm.all('ContNo').value ); 
	turnPage.queryModal(mySql.getString(), TraceGrid);


//�ж��Ƿ��ѯ�ɹ�
 turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
  if (!turnPage.strQueryResult) {
  	
  	TraceGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
    return false;
  }
}