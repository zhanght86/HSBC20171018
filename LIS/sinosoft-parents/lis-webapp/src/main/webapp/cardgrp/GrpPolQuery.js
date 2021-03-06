//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var mySql = new SqlClass();
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		var cPrtNo = GrpPolGrid.getRowColData( tSel - 1, 2 );
		var cGrpContNo=GrpPolGrid.getRowColData( tSel-1, 1 );
		
		try
		{
			window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + cPrtNo + "&GrpContNo=" + cGrpContNo+"&flag=1","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpPolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initGrpPolGrid();
	var strSQL ="";
		
	/*var strSQL = "select GrpContNo,PrtNo,GrpName,CValiDate,Peoples2,Prem,Amnt,SignCom,decode(appflag ,'1','有效','4','失效','其它') from LCGrpCont b where 1=1 "
    				 + " and AppFlag in ('1', '4')  and cardflag='1'"
    				 + getWherePart( 'PrtNo' )
    				 + getWherePart( 'GrpContNo' )
    				 //+ getWherePart( 'appntno','CustomerNo' )
    				 + getWherePart( 'ManageCom','ManageCom','like' )
    				 + getWherePart( 'AgentCode' )
    				 + getWherePart( 'AgentGroup' )
    				 //+ getWherePart( 'GrpName','GrpName','like' )
    				;*/
    mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql1");
	mySql.addSubPara(fm.PrtNo.value ); 	
	mySql.addSubPara(fm.GrpContNo.value ); 	
	mySql.addSubPara(fm.ManageCom.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.AgentGroup.value ); 	
	mySql.addSubPara(comCode ); 			
	if(document.all('RiskCode').value!=null&&document.all('RiskCode').value!="")
	{
	//	strSQL = strSQL+"and exists (select 1 from lcgrppol a where riskcode='"+document.all('RiskCode').value+"' and b.grpcontno=a.grpcontno) "
		mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql2");
	mySql.addSubPara(fm.PrtNo.value ); 	
	mySql.addSubPara(fm.GrpContNo.value ); 	
	mySql.addSubPara(fm.ManageCom.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.AgentGroup.value ); 
	mySql.addSubPara(document.all('RiskCode').value); 
	mySql.addSubPara(comCode ); 		
	}
    				 	
  if (tDisplay!="4") {
	//  strSQL = strSQL + " and specflag = '0' ";
	  mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql3");
	mySql.addSubPara(fm.PrtNo.value ); 	
	mySql.addSubPara(fm.GrpContNo.value ); 	
	mySql.addSubPara(fm.ManageCom.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 	
	mySql.addSubPara(fm.AgentGroup.value ); 
	mySql.addSubPara(document.all('RiskCode').value); 
	mySql.addSubPara(comCode ); 	
	}
	
 /* strSQL = strSQL + " and ManageCom like '" + comCode + "%%'"
    				      + " Order by grpContNo desc";*/
  
 turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
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


// 数据返回父窗口
function returnParentBQ()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResultBQ();
			//alert(arrReturn);
			
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录，再点击返回按钮。");
			//alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResultBQ()
{
	var arrSelected = null;
	var tRow = GrpPolGrid.getSelNo();
	//alert(tRow);
	if (tRow==0 || tRow==null) return arrSelected;
  
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = GrpPolGrid.getRowData(tRow-1);
	//alert(arrSelected[0][0]);
	//tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//设置需要返回的数组
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}

function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	//if(document.all('ManageCom').value==""){
	//	 alert("请先录入管理机构信息！");
	//	 return;
	//}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   /*	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	         + "and a.AgentCode = b.AgentCode and a.branchtype in ('1','4') and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);*/
    mySql = new SqlClass();
	mySql.setResourceName("cardgrp.GrpPolQueryInputSql");
	mySql.setSqlId("GrpPolQuerySql4");
	mySql.addSubPara(cAgentCode );  
    
    var arrResult = easyExecSql(mySql.getString());
    //alert(arrResult);
    if (arrResult != null) 
    {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	fm.AgentGroup.value = arrResult[0][1];
  	  //fm.AgentName.value = arrResult[0][3];
      fm.ManageCom.value = arrResult[0][2];
     
      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //   	
    	//    //fm.ManageCom.value = fm.AgentManageCom.value;
    	//    //fm.ManageComName.value = fm.AgentManageComName.value;
    	//    //showCodeName('comcode','ManageCom','AgentManageComName');  //代码汉化
    	//   
      //}
      //showContCodeName();
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，请确认!");
    }
   
	}
}

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	fm.AgentGroup.value = arrResult[0][1];
  	  //fm.AgentName.value = arrResult[0][3];
      //fm.ManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}