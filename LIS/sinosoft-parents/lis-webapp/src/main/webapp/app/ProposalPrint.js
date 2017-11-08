//               该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "app.ProposalPrintSql";


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
  initContGrid();
  document.getElementById("fm").submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
	var i = 0;
	var flag = 0;
	flag = 0;
	//判定是否有选择打印数据
	for( i = 0; i < ContGrid.mulLineCount; i++ )
	{
		if( ContGrid.getChkNo(i) == true ) {
//			alert("ContGrid.getRowColData(i,9):"+ContGrid.getRowColData(i,9)+"@@@@@@@");
//			alert("ContGrid.getRowColData(i,8):"+ContGrid.getRowColData(i,8)+"@@@@@@@");
//			alert("ContGrid.getRowColData(i,10):"+ContGrid.getRowColData(i,10)+"@@@@@@@");
				if(ContGrid.getRowColData(i,10) == "1"){
			alert("友情提示：即将打印VIP保单，请检查纸张首页是否匹配！");
		     }
		        else {
			alert("友情提示：即将打印普通保单，请检查纸张首页是否匹配！");
		     }
			flag = 1;
			break;
		}
	}
	//如果没有打印数据，提示用户选择
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
	//disable打印按钮，防止用户重复提交
	document.all("printButton").disabled=true;
	document.all("printVIPButton").disabled=true;
	document.getElementById("fm").submit();
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = ContGrid.getSelNo();
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
	//无论打印结果如何，都重新激活打印按钮
	showInfo.close();
	document.all("printButton").disabled=false;
	document.all("printVIPButton").disabled=false;
	if (FlagStr == "Fail" )
	{
		//如果打印失败，展现错误信息
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
	else
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		easyQueryClick();		
	}
	 //document.all("divErrorMInfo").style.display = "";
	 //easyQueryErrClick();
	 
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
	else
	{
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
	if(document.all('ManageCom').value=="86")
	{
		alert("管理机构不能为总公司！");
		return;
		}
		if(document.all('ManageCom').value=="" || document.all('ManageCom').value==null )
	{
		alert("管理机构不能为空");
		return;
		}	
	// 初始化表格
	initContGrid();
	//判断其是否是家庭单或者多主险
	
	var strManageComWhere = " and 1=1 ";
  var strPrtNoSQL = " ";
  var strContNoSQL = " ";
	if( fm.BranchGroup.value != '' )
	{
		//strManageComWhere += " AND EXISTS" + " ( SELECT AgentGroup FROM LABranchGroup WHERE AgentGroup=A.AgentGroup and BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
		strManageComWhere = " AND EXISTS" + " ( SELECT AgentGroup FROM LABranchGroup WHERE AgentGroup=A.AgentGroup and BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
	}
	//只要合同下有一个主险符合VIP保单的条件,则本合同就是VIP合同 hb-2009-02-11
	var strRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode not in ('311603')  and isvipcont(prtno,payintv,payyears,prem)= '0' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') )"; //
	//alert(document.all('RiskCode').value );
	if(document.all('RiskCode').value != null && document.all('RiskCode').value != "" )
	{
		strRiskCodeWhere +=  " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode= '"+document.all('RiskCode').value 
		+"' and isvipcont(prtno,payintv,payyears,prem)= '0' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') )"	
	}
  
   //alert(document.all('ContNo').value);
   //alert(document.all('PrtNo').value);
   //alert(document.all('Proposalcontno').value);
   var tContTraceSQL="";
   
   //排除合同下存在未签发保单的合同
   var tPrtSQL=" and not exists (select PolNo from LCPol where a.PrtNo = PrtNo and AppFlag <> '1')";
   
   var tEdorContSQL=" and 2=2 ";
   
   if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == "")
    )
   {
   	   //or exists (select 'X' from lccontprinttrace r where a.contno = a.contno and a.makedate <= sysdate and a.makedate >= substr((sysdate - 30), 0, 10)))
   	   //为提升性能，只查出最近十天签单的保单或者最近十天补打的保单，十天可以调整
   	   strContNoSQL=" and (a.signdate <= now() and a.signdate >= substr((subdate(now() , 10)), 1, 10)) ";
   
   	   tEdorContSQL= "  union  SELECT a.ContNo ContNo ,(SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno ), (SELECT distinct Proposalcontno from lccont  c where   c.contno=a.contno ),(SELECT distinct AppntName from lccont  c where   c.contno=a.contno ),(SELECT distinct signdate from lccont  c where   c.contno=a.contno ) CValiDate,(SELECT distinct signtime from lccont  c where   c.contno=a.contno ) CValiTime ,(SELECT distinct PrintCount from lccont  c where   c.contno=a.contno ),(SELECT distinct familytype from lccont  c where   c.contno=a.contno ),(SELECT distinct familycontno from lccont  c where   c.contno=a.contno ),'0' "
	                   +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
	                   + " and ManageCom like '" + comcode + "%%' "
	                   + getWherePart('ManageCom', 'ManageCom', 'like')
	                   + strRiskCodeWhere
	                   +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag=1 and (d.printcount=10 or  d.printcount<=0))"
	                   +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10)) ";            
   	}

//用一个SQL的临时变量进行查询
   var tTempSQL= "";
//   alert("_DBO:"+_DBO +" -----------"+"_DBM:"+_DBM);
   if(_DBT==_DBO){
	    tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
           +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
	+ " AND ( PrintCount <=0 OR PrintCount='10' )"
	+ getWherePart('a.contno', 'ContNo' )
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
	+ getWherePart('ManageCom', 'ManageCom', 'like')
	+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%' "
	+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
	+ " AND ( PrintCount <= 0 OR PrintCount='10')"
	+ getWherePart('a.familycontno', 'ContNo' )  //是否将其功能增强，即可以支持任意保单的保单号进行查询 
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
//	+ getWherePart('ManageCom', 'ManageCom', 'like')
+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;		
//	    alert(123);
   }else if(_DBT==_DBM){
	   tTempSQL="  ( select prtno,(select contno from lccont "
           +" where prtno = familyprt.prtno limit 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
	+ " AND ( PrintCount <=0 OR PrintCount='10' )"
	+ getWherePart('a.contno', 'ContNo' )
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
//	+ getWherePart('ManageCom', 'ManageCom', 'like')
	+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%' "
	+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
	+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
	+ " AND ( PrintCount <= 0 OR PrintCount='10')"
	+ getWherePart('a.familycontno', 'ContNo' )  //是否将其功能增强，即可以支持任意保单的保单号进行查询 
	+ getWherePart( 'PrtNo' )
	+ getWherePart( 'SaleChnl' )
	+ getWherePart('ManageCom', 'ManageCom', 'like')
+ strContNoSQL
	+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;		
   }
  

	var strSQL = "";	
/*	strSQL =tTempSQL+ " SELECT a.ContNo ContNo,a.PrtNo, a.Proposalcontno,AppntName,signdate,signtime,PrintCount,familytype,familycontno,'0' "
	  +" FROM LCCont A,familycont b where a.contno = b.contno "
    +" and a.prtno = b.prtno " //只管需要打印的保单
		+ strManageComWhere
		+ strRiskCodeWhere
		//+ tContTraceSQL 去除
	  + tEdorContSQL
		+ " order by ContNo asc,signdate asc ";
*/
///////////////拼接sql
var sqlid1="ProposalPrintSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(tTempSQL);
mySql1.addSubPara(strManageComWhere);
mySql1.addSubPara(strRiskCodeWhere);
mySql1.addSubPara(tEdorContSQL);
strSQL = mySql1.getString() ;
//console.log(tEdorContSQL);
/////////////////////
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
		alert("未查询到满足条件的数据！");
		return false;
	}

	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 30 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = ContGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
//查询VIP合同
function QueryVIPClick()
{

	if(document.all('ManageCom').value=="86")
	{
		alert("管理机构不能为总公司！");
		return;
		}
	// 初始化表格
	initContGrid();
	var strManageComWhere = " and 3=3 ";
	var tContTraceSQL="";
	var strContNoSQL = " and 4=4 ";
	var tEdorContSQL="";
	if( fm.BranchGroup.value != '' )
	{
		strManageComWhere += " AND EXISTS" + " ( SELECT AgentGroup FROM LABranchGroup WHERE AgentGroup=A.AgentGroup and BranchAttr LIKE '" + fm.BranchGroup.value + "%%') ";
	}
	//只要合同下有一个主险符合VIP保单的条件,则本合同就是VIP合同 hb-2009-02-11
	//排除特殊险种 311603 其在团单打印，个险不打印，add by pengst on 2009-06-11
	var strRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode not in ('311603')  and isvipcont(prtno,payintv,payyears,prem)= '1' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0'))"; //只管需要打印的保单"   
	//alert(document.all('RiskCode').value );
	if(document.all('RiskCode').value != null && document.all('RiskCode').value != "" )
	{
		strRiskCodeWhere +=  " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno and riskcode= '"+document.all('RiskCode').value 
		+"' and isvipcont(prtno,payintv,payyears,prem)= '1' and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol = '0') )"
	}
	
	   if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == ""))
   {
   	   // or exists (select 'X' from lccontprinttrace r where a.contno = a.contno and a.makedate <= sysdate and a.makedate >= substr((sysdate - 30), 0, 10)))";
   	   //为提升性能，只查出最近十天签单的保单或者最近十天补打的保单，十天可以调整
   	   strContNoSQL=" and (a.signdate <= now() and a.signdate >= substr((subdate(now() , 10)), 1, 10))";
       tEdorContSQL= "  union  SELECT a.ContNo ContNo ,(SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno ), (SELECT distinct Proposalcontno from lccont  c where   c.contno=a.contno ),(SELECT distinct AppntName from lccont  c where   c.contno=a.contno ),(SELECT distinct CValiDate from lccont  c where   c.contno=a.contno ) CValiDate,(SELECT distinct signtime from lccont  c where   c.contno=a.contno ) CValiTime ,(SELECT distinct PrintCount from lccont  c where   c.contno=a.contno ),(SELECT distinct familytype from lccont  c where   c.contno=a.contno ),(SELECT distinct familycontno from lccont  c where   c.contno=a.contno ),'1' "
	                   +" FROM lpedoritem  a where 1=1 and edortype='LR' and edorstate='0' "
	                   + " and ManageCom like '" + comcode + "%%' "
	                   + getWherePart('ManageCom', 'ManageCom', 'like')
	                   + strRiskCodeWhere
	                   +" and  exists (select 'X' from lccont d where  d.contno=a.contno and d.AppFlag='1' and (d.printcount=10 or  d.printcount<=0))"
	                   +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10)) ";   
   
   	}

//用一个SQL的临时变量进行查询
	   var tTempSQL="";
	   if(_DBT==_DBO){
		   tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
               +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
  +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
		+ " AND ( PrintCount <=0 OR PrintCount='10' )"
		+ getWherePart('a.contno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
//		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ strContNoSQL
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
  +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
		+ " AND ( PrintCount <= 0 OR PrintCount='10')"
		+ getWherePart('a.familycontno', 'ContNo' )  //是否将其功能增强，即可以支持任意保单的保单号进行查询 
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		//+ getWherePart('ManageCom', 'ManageCom', 'like')
    + strContNoSQL
		+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;
	   }else if(_DBT==_DBM){
		   tTempSQL="( select prtno,(select contno from lccont "
               +" where prtno = familyprt.prtno limit 0,1) contno from "   
  +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'  and (familycontno is  null or  familycontno='00000000000000000000') "
		+ " AND ( PrintCount <=0 OR PrintCount='10' )"
		+ getWherePart('a.contno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		//+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ strContNoSQL
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
  +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2') and (familycontno is not null and  familycontno!='00000000000000000000')  "
		+ " AND ( PrintCount <= 0 OR PrintCount='10')"
		+ getWherePart('a.familycontno', 'ContNo' )  //是否将其功能增强，即可以支持任意保单的保单号进行查询 
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ getWherePart('ManageCom', 'ManageCom', 'like')
    + strContNoSQL
		+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;
	   }
  		

	var strSQL = "";	
/*	strSQL =tTempSQL+ " SELECT a.ContNo ContNo,a.PrtNo, a.Proposalcontno,AppntName,signdate,signtime,PrintCount,familytype,familycontno,'1' "
	  +" FROM LCCont A,familycont b where a.contno = b.contno " 
    +" and a.prtno = b.prtno "
    + strManageComWhere
		+ strRiskCodeWhere
		//+ tContTraceSQL 去除
		+tEdorContSQL //添加保全补发保单的情况
		+ " order by ContNo asc,signdate asc";
*/
///////////////拼接sql
var sqlid2="ProposalPrintSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(tTempSQL);
mySql2.addSubPara(strManageComWhere);
mySql2.addSubPara(strRiskCodeWhere);
mySql2.addSubPara(tEdorContSQL);
strSQL = mySql2.getString() ;
/////////////////////
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
		alert("未查询到满足条件的数据！");
		return false;
	}

	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	turnPage.pageLineNum = 30 ;
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = ContGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL ;
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function queryBranch()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
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
	
  var addStr3 = " and 3=3 ";
  var addStr4 = " and 4=4 ";
  var addStr5 = " and 5=5 ";
if(fm.ErrorContNo.value != null) 
  { addStr3 = " and ContNo = '"+fm.ErrorContNo.value+"'"; }
if(fm.MakeDate.value != null && fm.MakeDate.value !="" ) 
  { addStr4 = " and MakeDate = '"+fm.MakeDate.value+ "'"; }
if(fm.ManageCom.value != null) 
  { addStr5 = " and ManageCom like '"+fm.ManageCom.value+"%'"; }
/*    
  var strSQL="select contno,Errmsg,makedate,maketime,ManageCom from LDSysErrLog where 1=1 "
  + getWherePart( 'ContNo','ErrorContNo' )
  + getWherePart( 'MakeDate' );
  + getWherePart( 'ManageCom' );
*/
var sqlid22="ProposalPrintSql22";
var mySql22=new SqlClass();
mySql22.setResourceName(sqlresourcename);
mySql22.setSqlId(sqlid22);
mySql22.addSubPara(addStr3);
mySql22.addSubPara(addStr4);
mySql22.addSubPara(addStr5);
	strSQL = mySql22.getString() ;
	turnPage.strQueryResult  = easyQueryVer3(mySql22.getString(), 1, 0, 1);

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