//               该文件中包含客户端需要处理的函数和事件
var arrDataSet 
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "app.ProposalComPrint_IDGFSql";
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;
  var flag = 0;
  flag = 0;
  
  for( i = 0; i < PolGrid.mulLineCount; i++ ) 
  {
    if( PolGrid.getChkNo(i) == true ) 
    {
       flag = 1;
       break;
    }
  }
	
  if( flag == 0 ) 
  {
    alert("请先选择一条记录，再打印保单");
    return false;
  }

  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  document.all("printButton").disabled=true;
  document.getElementById("fm").submit();
}


//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //代理人编码	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
var sqlid1="ProposalComPrint_IDGFSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(cAgentCode);

    
    var arrResult = easyExecSql(mySql1.getString());
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
    
var sqlid2="ProposalComPrint_IDGFSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(cAgentCode);

    
    var arrResult = easyExecSql(mySql2.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");	
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
   document.all("printButton").disabled=false;
  if (FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  } else {
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
   easyQueryClick();
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
}           

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}


// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	
	 if(document.all('ManageCom').value=="" || document.all('ManageCom').value==null )
	{
		alert("管理机构不能为空");
		return;
		}	
		
	   if(fm.ManageGrade.value==null || fm.ManageGrade.value=="" )
	  {
	  		 alert("机构级别不能为空");
	  		 return false;
	  }  
	  if(fm.ManageCom.value.length>fm.ManageGrade.value)
	  {
	  		 alert("你选的管理机构和级别不匹配，请重新选择");
	  		 return false;
	  }	
   //为提升性能，只查出最近十天签单的保单或者最近十天补打的保单，十天可以调整
   var  strContNoSQL=" and (a.signdate <= now() and a.signdate >= substr((subdate(now() , 10)), 1, 10)) ";// or exists (select 'X' from lccontprinttrace r where r.contno = a.contno and r.makedate <= sysdate and r.makedate >= substr((sysdate - 30), 0, 10)))";

	//用一个SQL的临时变量进行查询
   var tTempSQL= "";
/**   if(_DBT==_DBO){
	    tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
           +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and ( familycontno is  null or  familycontno='00000000000000000000')  "
	+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
	+ getWherePart('ManageCom', 'ManageCom', 'like')
	+ " and ManageCom like '" + comcode + "%%' "
	
	+ strContNoSQL
	
	+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and FamilyType in ('1','2')  and (familycontno is not null and  familycontno!='00000000000000000000')   "
	+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
	+ getWherePart('ManageCom', 'ManageCom', 'like')
	
	+ strContNoSQL
	
	+" ) "
	+ "  union  (SELECT (SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno )"
  +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
  + " and ManageCom like '" + comcode + "%%' "
  + getWherePart('ManageCom', 'ManageCom', 'like')
  +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag='1' and (d.printcount=10 or  d.printcount<=0))"
  +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10))) " 
	+") familyprt )"	;
	   }else if(_DBT==_DBM){
		    tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
               +" where prtno = familyprt.prtno limit 1) contno from "   
  +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and ( familycontno is  null or  familycontno='00000000000000000000')  "
		+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%' "
		
		+ strContNoSQL
		
		+" union  "
  +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2')  and (familycontno is not null and  familycontno!='00000000000000000000')   "
		+ " AND ( PrintCount <= 0 OR PrintCount = 10 )"
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		
		+ strContNoSQL
		
		+" ) "
		+ "  union  (SELECT (SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno )"
	  +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
	  + " and ManageCom like '" + comcode + "%%' "
	  + getWherePart('ManageCom', 'ManageCom', 'like')
	  +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag='1' and (d.printcount=10 or  d.printcount<=0))"
	  +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10))) " 
		+") familyprt )"	;
	   }
 */ 
/*
var sqlid3="ProposalComPrint_IDGFSql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(fm.ManageCom.value);
mySql3.addSubPara(comcode);


var sqlid6="ProposalComPrint_IDGFSql6";
var mySql6=new SqlClass();
mySql6.setResourceName(sqlresourcename);
mySql6.setSqlId(sqlid6);
mySql6.addSubPara(fm.ManageCom.value);

var sqlid7="ProposalComPrint_IDGFSql7";
var mySql7=new SqlClass();
mySql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mySql7.addSubPara(comcode);
mySql7.addSubPara(fm.ManageCom.value);

var tTempSQL= mySql3.getString()+ strContNoSQL + mySql6.getString() + strContNoSQL +mySql7.getString();	
*/
	//	+ " and ManageCom like '" + comcode + "%%')"
	//	+ " union "
  //  +" (SELECT f.PrtNo "
	//  +" FROM lccontprinttrace f where 1=1 and RePrintFlag=0 "
	//  +" and ManageCom like '" + comcode + "%%' "
	//  + getWherePart('ManageCom', 'ManageCom', 'like')
	//  +" and (f.makedate <= sysdate and f.makedate >= substr((sysdate - 100), 0, 10)) "
  // 	+" and  exists (select 'X' from lccont d where  d.prtno=f.prtno and d.printcount='-1'))" 					
	//	+" ) familyprt )"	;		
			
	// 书写SQL语句
	
	var strSQL = "";
/**
	strSQL = tTempSQL+ "select m,count(p) cp from (select a.contno p, substr(managecom,1,"+document.all('ManageGrade').value+") m from LCCont a,familycont b "
               + "where a.contno=b.contno and a.prtno=b.prtno and AppFlag = '1'  and EXISTS (SELECT 'X' from lcpol c where  c.contno=a.contno "
               +" and c.mainpolno=c.polno and c.riskcode not in ('311603') and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') and isvipcont(prtno,payintv,payyears,prem)= '0' )" //只管需要打印的保单" and riskcode<>'112401'
//               + "and not exists ( select PolNo from LCPol where a.PrtNo = PrtNo and AppFlag <> '1' ) "
	               + " ) " //去掉 and mainpolno=polno
//               + " and ISVIPPOL(prtno,payintv,payyears,prem)='0' "//判断VIP的逻辑在6.0中可能不正确 -2009-02-09
//               + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+comcode+"','Pj')=1) "//6.0中不用这个校验
               +"group by m order by cp desc";
*/

		var sqlid8="";
		if(_DBT==_DBO){
			sqlid8="ProposalComPrint_IDGFSql8";
		}else if(_DBT==_DBM){
			sqlid8="ProposalComPrint_IDGFSql8_MYSQL";
		}
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(window.document.getElementsByName(trim(controlName))[0].value);
		mySql8.addSubPara(comcode);
		mySql8.addSubPara(window.document.getElementsByName(trim(controlName))[0].value);
		mySql8.addSubPara(comcode);
		mySql8.addSubPara(window.document.getElementsByName(trim(controlName))[0].value);
		mySql8.addSubPara(document.all('ManageGrade').value);
//	  turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 0, 1);  
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
     return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 100 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL ; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html","",sFeatures);
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}


// 查询按钮
function easyQueryErrClick()
{

	// 初始化表格
	initErrorGrid();
	//判断其是否是家庭单或者多主险
	
/*
  var strSQL="select contno,Errmsg,makedate,maketime,ManageCom from LDSysErrLog where 1=1 "
  + getWherePart( 'ContNo','ErrorContNo' )
  + getWherePart( 'MakeDate' );
  + getWherePart( 'ManageCom' );
*/	
var sqlid5="ProposalComPrint_IDGFSql5";
var mySql5=new SqlClass();
mySql5.setResourceName(sqlresourcename);
mySql5.setSqlId(sqlid5);
mySql5.addSubPara(fm.ErrorContNo.value);
mySql5.addSubPara(fm.MakeDate.value);
mySql5.addSubPara(fm.ManageCom.value);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql5.getString(), 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
		alert("未查询到满足条件的数据！");
		return ;
	}

	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 30 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = ErrorGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}