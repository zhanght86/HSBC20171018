var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var turnPageSingleInfoGrid = new turnPageClass();    //全局变量, 查询结果翻页, 必须有
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

function easyQueryClick()
{
	var sqlid826091225="DSHomeContSql826091225";
var mySql826091225=new SqlClass();
mySql826091225.setResourceName("sys.GrpPolSingleQuerySql");//指定使用的properties文件名
mySql826091225.setSqlId(sqlid826091225);//指定使用的Sql的id
mySql826091225.addSubPara(tGrpContNo);//指定传入的参数
mySql826091225.addSubPara(fm.RiskCode.value);//指定传入的参数
mySql826091225.addSubPara(fm.ContNo.value);//指定传入的参数
mySql826091225.addSubPara(fm.InsuredNo.value);//指定传入的参数
mySql826091225.addSubPara(fm.AppFlag.value);//指定传入的参数
mySql826091225.addSubPara(fm.Sex.value);//指定传入的参数
mySql826091225.addSubPara(fm.BirthDay.value);//指定传入的参数
mySql826091225.addSubPara(fm.Name.value);//指定传入的参数
var strSQL=mySql826091225.getString();
	
//	var strSQL = "select a.contno,a.InsuredNo,b.Name,a.RiskCode,a.CValidate,a.EndDate,b.BirthDay,decode(b.sex,'0','男','1','女','不详'),a.Prem,a.Amnt,"
//						 + " decode(a.AppFlag,'0','投保未生效','1','有效','2','增人/新增险种未生效','4',decode((select distinct '1' from LPEdorItem where edortype in ('GT','XT','ZT','CT','ES','AT','AX','WT','AZ') and ContNo = a.ContNo and edorstate = '0'),'1','退保失效','满期失效'),'其它') "
//						 //显示用contno 内部查询用polno
//						 //modify by jiaqiangli 2009-10-30
//						 + " ,a.polno " 
//						 + " from LCPol a,LCInsured b where 1=1 "
//    				 + " and a.GrpContNo='"+ tGrpContNo +"' and a.InsuredNo = b.InsuredNo and a.GrpContNo = b.GrpContNo and a.ContNo=b.ContNo "
//    				 + getWherePart('a.RiskCode', 'RiskCode')
//  				 	 + getWherePart('a.ContNo', 'ContNo')
//  				 	 + getWherePart('a.InsuredNo', 'InsuredNo')
//  				 	 + getWherePart('a.AppFlag', 'AppFlag')
//  				 	 + getWherePart('b.Sex', 'Sex')
//  				 	 + getWherePart('b.BirthDay', 'BirthDay')
//  					 + getWherePart('b.name', 'Name','like');
//
//  strSQL += " order by a.appflag,a.contno";
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = GrpPolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}
//Add By QianLy to show single people's Main Info on 2006-10-18
function querySingleInfo()
{
	var tSel = GrpPolGrid.getSelNo();
	var tContNo = GrpPolGrid.getRowColData(tSel-1,1);
	var insn = GrpPolGrid.getRowColData(tSel-1,2);
	
	var sqlid826091451="DSHomeContSql826091451";
var mySql826091451=new SqlClass();
mySql826091451.setResourceName("sys.GrpPolSingleQuerySql");//指定使用的properties文件名
mySql826091451.setSqlId(sqlid826091451);//指定使用的Sql的id
mySql826091451.addSubPara(insn);//指定传入的参数
mySql826091451.addSubPara(tContNo);//指定传入的参数
var strSQL=mySql826091451.getString();
	
//	var strSQL = "select distinct insuredno,"
//	          + " name,"
//	          + " (select codename from ldcode where codetype = 'sex' and code = sex),"
//	          + " birthday,"
//	          + " (select codename from ldcode where codetype = 'idtype' and code = idtype),"
//	          + " idno,"
//            + " occupationcode,"
//            + " occupationtype,"
//            + " RgtAddress,"
//            + " (select codename from ldcode where codetype = 'marriage' and code = marriage)"
//	          + " from lcinsured"
//	          + " where 1 = 1"
//	          + " and insuredno = '"+insn+"'"
//	          + " and contno = '"+tContNo+"'";	 
    try
    {
    	  document.all("divSingleInfo").style.display= "";
        turnPageSingleInfoGrid.pageDivName = "divTurnPageSingleInfoGrid";
        turnPageSingleInfoGrid.queryModal(strSQL, SingleInfoGrid);
    }
    catch (ex)
    {
        alert("警告：查询客户重要信息出现异常！ ");
    }
}
function getRisk(){
  var i = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	
	var sqlid826091551="DSHomeContSql826091551";
var mySql826091551=new SqlClass();
mySql826091551.setResourceName("sys.GrpPolSingleQuerySql");//指定使用的properties文件名
mySql826091551.setSqlId(sqlid826091551);//指定使用的Sql的id
mySql826091551.addSubPara(tGrpContNo);//指定传入的参数
strsql=mySql826091551.getString();
	
//	strsql = "select distinct a.RiskCode,b.riskname from LCPol a,lmrisk b where a.GrpContNo='"+ tGrpContNo +"'"
//           + " and a.riskcode=b.riskcode"
//           + " order by a.RiskCode";
	 
	//alert(strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			tCodeData = tCodeData + m+ "^"  + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];	
		}
	}
 
  return tCodeData;
}    

  function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先在个人保单信息中选择一条记录." );
	else
	{
		//显示用contno 内部查询用polno
		//modify by jiaqiangli 2009-10-30
		var cPolNo = GrpPolGrid.getRowColData( tSel - 1, 12 );
		if(cPolNo!="")
		{   try
		    {
		    	 var strUrl="./GpsaAccQueryMain.jsp?PolNo="+ cPolNo;
			     window.open(strUrl,"",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
		  
		    }
		    catch(ex)
		    {
			    alert( "没有发现父窗口的afterQuery接口。" + ex );
		    }

    }
   else{ alert( "请先查询个人保单信息." ); return;}

	}
	
}  

function GoBack(){
	top.opener.window.focus();
	
	top.window.close();
}




function  LCInsureAccFeeQuery(){
	
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先在个人保单信息中选择一条记录." );
	else
	{
		var cPolNo = GrpPolGrid.getRowColData( tSel - 1, 1 );
		if(cPolNo!="")
		{   try
		    {
			     window.open("./GpsLCInsureAccFeeQuery.jsp?PolNo="+ cPolNo,"",sFeatures);
		  
		    }
		    catch(ex)
		    {
			    alert( "没有发现父窗口的afterQuery接口。" + ex );
		    }

    }
   else{ alert( "请先查询个人保单信息." ); return;}

	}
	
	
	
	
}