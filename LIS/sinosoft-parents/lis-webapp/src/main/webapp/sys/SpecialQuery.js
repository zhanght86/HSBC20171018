//�������ƣ�SpecialQuery.js
//�����ܣ���Լ��ѯ
//�������ڣ�2005-09-29 11:10:36
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLSpecGrid()
{   
   var strSQL = "";
  /* strSQL = "select (select riskname from lmriskapp where riskcode=a.RiskCode) ,a.InsuredName,a.Prem,a.Amnt,b.SpecContent from LCPol a,LCSpec b where 1=1"
           +" and a.ContNo=b.ContNo and a.ContNo='"+fm.all('ContNo').value+"'" 
           +" order by a.ContNo";*/
	     
	mySql = new SqlClass();
	mySql.setResourceName("sys.SpecialQuerySql");
	mySql.setSqlId("SpecialQuerySql1");
	mySql.addSubPara(fm.all('ContNo').value );       
//   var arr= easyExecSql(strSQL);
//    if(arr)
//    {
//        displayMultiline(arr,SpecGrid);
//    }

	turnPage.queryModal(mySql.getString(), SpecGrid);



//�ж��Ƿ��ѯ�ɹ�
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
  	
  	SpecGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
    return false;
  }

}

















