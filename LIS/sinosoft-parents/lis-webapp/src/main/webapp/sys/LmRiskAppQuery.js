//               
//�������ƣ�LmRiskAppQueryInit.jsp
//�����ܣ����ļ��а���������Ϣ��ѯ���������ʲ�ѯ��Ҫ����ĺ������¼�
//�������ڣ�2005-10-31
//������  �������

var turnPage ;
var turnPage1 ;
var showInfo;
var mDebug="0";
var tDisplay;
var arrDataSet;
var mySql = new SqlClass();

//������Ϣ��ѯ����

function LmRiskAppQuery()
{
  turnPage = new turnPageClass();
  var tRiskProp = document.all('RiskProp').value;
	// ��дSQL���
	var strSQL = "";
  // strSQL = "select a.riskcode ,a.riskname ,a.riskver, b.destrate,(case a.riskperiod when 'L' then '������' when 'M' then '������' when 'S' then '��������' end),a.startdate from lmriskapp a ,lmrisk b where a.riskcode = b.riskcode and a.riskprop = '"+tRiskProp+"'";
   mySql = new SqlClass();
	mySql.setResourceName("sys.LmRiskAppQueryInputSql");
	mySql.setSqlId("LmRiskAppQuerySql1");
	mySql.addSubPara(tRiskProp); 
   turnPage.queryModal(mySql.getString(),LmRiskAppGrid);
 	   
}

//�������ʲ�ѯ
function LdBankRateQuery(){
	turnPage1 = new turnPageClass();
	var tDeclareDate = document.all('DeclareDate').value;
	var tEndDate = document.all('EndDate').value;
	var strSQL = "";
	//strSQL = "select (case periodflag when 'Y' then '��' when 'M' then '��' end),(case type when 'C' then '����' when 'F' then '����' end),(case depst_loan when 'L' then '����' when 'D' then '���' end),declaredate,enddate,rate from  ldbankrate where declaredate > TO_DATE('"+tDeclareDate+"','YYYY-MM-DD') and enddate < TO_DATE('"+tEndDate+"','YYYY-MM-DD')";
	mySql = new SqlClass();
	mySql.setResourceName("sys.LmRiskAppQueryInputSql");
	mySql.setSqlId("LmRiskAppQuerySql2");
	mySql.addSubPara(tDeclareDate); 
	mySql.addSubPara(tEndDate); 
	turnPage1.queryModal(mySql.getString(),LdBankRateGrid);
	}
//���غ���
function GoBack(){
	
	  top.close();
	
	}