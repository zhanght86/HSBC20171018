
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var showInfo;
var queryflag=0;////查询标志，限制在点击下载按钮前必须点击查询按钮，查看一下下载的记录:0表示未点击，1表示点击了记录，而且查询出了记录

// 查询按钮
function easyQueryClick()
{

	// 书写SQL语句
	//alert(CertifyCode);
	var str="";
	//根据卡号得到险种基本信息
//	var strSQL=" select d.riskcode,(select riskname from lmrisk where lmrisk.riskcode = d.riskcode),"
//	           + " d.insuyear, (case trim(d.insuyearflag) when 'Y' then '年' when 'D' then '日' else '月' end),d.prem"
//	           + " from LMCardRisk a,ratecard d where a.riskcode=d.riskcode and a.prem = d.prem"
//               + " and a.certifyCode='"+CertifyCode+"'"
               
                var sqlid1="SelfRiskInformationQuerySql1";
            	var mySql1=new SqlClass();
            	mySql1.setResourceName("selflist.SelfRiskInformationQuerySql");
            	mySql1.setSqlId(sqlid1); //指定使用SQL的id
            	mySql1.addSubPara(CertifyCode);//指定传入参数
            	var strSQL = mySql1.getString();
               
  
   //prompt("",strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
   //判断是否查询成功
   if (!turnPage.strQueryResult) 
   {
  		queryflag=0;
    	alert("没有查询到的对应的险种信息！");
    	return false;
  	}
    
  	queryflag=1;
  	//查询成功则拆分字符串，返回二维数组
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  	turnPage.pageDisplayGrid = RiskInfo;    
          
  	//保存SQL语句
  	turnPage.strQuerySql     = strSQL; 
  
  	//设置查询起始位置
  	turnPage.pageIndex       = 0;  
  
  	//在查询结果数组中取出符合页面显示大小设置的数组
  	arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 	//tArr=chooseArray(arrDataSet,[0]) 
  	//调用MULTILINE对象显示查询结果
  
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  	//displayMultiline(tArr, turnPage.pageDisplayGrid);
 
}
