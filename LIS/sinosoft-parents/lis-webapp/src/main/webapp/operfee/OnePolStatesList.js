//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

// 查询按钮
function easyQueryClick()
{
	if(!beforeSubmit())
	 {
	  return false;
	 }
	// 初始化表格
	initIndiDueFeeListGrid();
/*	
	// 书写SQL语句
	var strSQL = "";
	var strSQLXQ = "";
	var strSQLXB = "";
	//查询主险信息
	if(_DBT==_DBO){
		strSQLXQ = "select ContNo,PolNo,RiskCode,(case PayIntv when -1 then '不定期交费' when 0 then '趸 交' when 1 then '月 交' when 3 then '季 交' when 6 then '半年交' when 12 then '年 交' else '' end) PayIv,'续期' xqxb,(case (select 1 from LJSpayPerson where Polno=LCPol.Polno and rownum=1) when 1 then '续期已催收' else '续期未催收' end) LJSState,Paytodate,PayEnddate,(case (select distinct(contno) from LPEdormain where contno=LCPol.contno and abs(LCPol.paytodate - edorappdate)<=60) when NULL then '无保全' else '保 全' end) BQ,(case (SELECT polno FROM ldsystrace WHERE polstate='4001' AND valiflag='1' AND polno=LCPol.PolNo) when NULL then '无死亡报案' else '已有死亡报案') LiPei,Agentcode from LCPol where 1=1 and AppFlag='1' "			 
			 + "and Paytodate < PayEnddate "
			 + getWherePart( 'ContNo','ContNo' )
			 + getWherePart( 'MainPolNo','MainPolNo' )
			 + getWherePart( 'RiskCode','RiskCode' )
			 ;
	}else if(_DBT==_DBM){
		strSQLXQ = "select ContNo,PolNo,RiskCode,(case PayIntv when -1 then '不定期交费' when 0 then '趸 交' when 1 then '月 交' when 3 then '季 交' when 6 then '半年交' when 12 then '年 交' else '' end) PayIv,'续期' xqxb,(case (select 1 from LJSpayPerson where Polno=LCPol.Polno limit 0,1) when 1 then '续期已催收' else '续期未催收' end) LJSState,Paytodate,PayEnddate,(case (select distinct(contno) from LPEdormain where contno=LCPol.contno and abs(LCPol.paytodate - edorappdate)<=60) when NULL then '无保全' else '保 全' end) BQ,(case (SELECT polno FROM ldsystrace WHERE polstate='4001' AND valiflag='1' AND polno=LCPol.PolNo) when NULL then '无死亡报案' else '已有死亡报案') LiPei,Agentcode from LCPol where 1=1 and AppFlag='1' "			 
			 + "and Paytodate < PayEnddate "
			 + getWherePart( 'ContNo','ContNo' )
			 + getWherePart( 'MainPolNo','MainPolNo' )
			 + getWherePart( 'RiskCode','RiskCode' )
			 ;
	}
	
				 
	strSQLXB = "select ContNo,PolNo,RiskCode,(case PayIntv when -1 then '不定期交费' when 0 then '趸 交' when 1 then '月 交' when 3 then '季 交' when 6 then '半年交' when 12 then '年 交' else '' end) PayIv,'续保' xqxb,(case (select 1 from LCRnewStateHistory where contno=LCPol.contno and riskcode=LCPol.riskcode and State='4') when 1 then '续保已催收' else '续保未催收' end) LJSState,Paytodate,PayEnddate,(case (select distinct(contno) from LPEdormain where contno=LCPol.contno and abs(LCPol.paytodate - edorappdate)<=60) when NULL then '无保全' else '保 全' end) BQ,(case (SELECT polno FROM ldsystrace WHERE polstate='4001' AND valiflag='1' AND polno=LCPol.PolNo) when NULL then '无死亡报案' else '已有死亡报案' end) LiPei,Agentcode from LCPol where 1=1 and AppFlag='1' "			 
				 + "and Paytodate = PayEnddate "
				 + getWherePart( 'ContNo','ContNo' )
				 + getWherePart( 'MainPolNo','MainPolNo' )
				 + getWherePart( 'RiskCode','RiskCode' )
                                 ;                                 
                                 
      strSQL = "select ContNo,PolNo,RiskCode,PayIv,xqxb,LJSState,PaytoDate,PayEndDate,BQ,LiPei,Agentcode from (  "+strSQLXQ + " union all "+ strSQLXB +") order by PolNo";	
      */
      var strSQL = "";
      var sqlId = "";
      if(_DBT==_DBO){
    	  sqlId = "OnePolStatesListSql1";
      }else if(_DBT==_DBO){
    	  sqlId = "OnePolStatesListSql2";
      }
		var mySql1 = new SqlClass();
		mySql1.setResourceName("operfee.OnePolStatesListSql");
		mySql1.setSqlId(sqlId);
		mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('MainPolNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('RiskCode'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('MainPolNo'))[0].value);
		mySql1.addSubPara(window.document.getElementsByName(trim('RiskCode'))[0].value);
		strSQL = mySql1.getString();
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("找不到满足查询条件的保单！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = IndiDueQueryGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
    initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作
    if((fm.ContNo.value == null||fm.ContNo.value =='')&&(fm.MainPolNo.value == null||fm.MainPolNo.value ==''))
     {
       alert("保单号或主险险种号码不能为空!");
       return false;
     }
    return true;
}           

function returnParent()
{
    top.close();
}
