//               
//程序名称：LmRiskAppQueryInit.jsp
//程序功能：该文件中包含险种信息查询和银行利率查询需要处理的函数和事件
//创建日期：2005-10-31
//创建人  ：万泽辉

var turnPage ;
var turnPage1 ;
var showInfo;
var mDebug="0";
var tDisplay;
var arrDataSet;
var mySql = new SqlClass();

//险种信息查询函数

function LmRiskAppQuery()
{
  turnPage = new turnPageClass();
  var tRiskProp = document.all('RiskProp').value;
	// 书写SQL语句
	var strSQL = "";
  // strSQL = "select a.riskcode ,a.riskname ,a.riskver, b.destrate,(case a.riskperiod when 'L' then '长寿险' when 'M' then '年期险' when 'S' then '极短期险' end),a.startdate from lmriskapp a ,lmrisk b where a.riskcode = b.riskcode and a.riskprop = '"+tRiskProp+"'";
   mySql = new SqlClass();
	mySql.setResourceName("sys.LmRiskAppQueryInputSql");
	mySql.setSqlId("LmRiskAppQuerySql1");
	mySql.addSubPara(tRiskProp); 
   turnPage.queryModal(mySql.getString(),LmRiskAppGrid);
 	   
}

//银行利率查询
function LdBankRateQuery(){
	turnPage1 = new turnPageClass();
	var tDeclareDate = document.all('DeclareDate').value;
	var tEndDate = document.all('EndDate').value;
	var strSQL = "";
	//strSQL = "select (case periodflag when 'Y' then '年' when 'M' then '月' end),(case type when 'C' then '活期' when 'F' then '定期' end),(case depst_loan when 'L' then '贷款' when 'D' then '存款' end),declaredate,enddate,rate from  ldbankrate where declaredate > TO_DATE('"+tDeclareDate+"','YYYY-MM-DD') and enddate < TO_DATE('"+tEndDate+"','YYYY-MM-DD')";
	mySql = new SqlClass();
	mySql.setResourceName("sys.LmRiskAppQueryInputSql");
	mySql.setSqlId("LmRiskAppQuerySql2");
	mySql.addSubPara(tDeclareDate); 
	mySql.addSubPara(tEndDate); 
	turnPage1.queryModal(mySql.getString(),LdBankRateGrid);
	}
//返回函数
function GoBack(){
	
	  top.close();
	
	}