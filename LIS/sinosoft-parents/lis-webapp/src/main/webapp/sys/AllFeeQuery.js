//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
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



// 查询按钮
function easyQueryClick()
{
	
//	alert("here");
	// 初始化表格

	if((fm.PayNo.value==null||fm.PayNo.value=="")&&(fm.IncomeNo.value==null||fm.IncomeNo.value=="")&&(fm.IncomeType.value==null||fm.IncomeType.value=="")&&(fm.PayDate.value==null||fm.PayDate.value=="")&&(fm.AgentCode.value==null||fm.AgentCode.value==""))
	{
		alert("无查询条件。除管理机构外，请再至少输入一条查询条件");
		fm.PayNo.focus();
		return;
		}
	document.all('feequery').disabled = true;
	initPolGrid();
	// 书写SQL语句
	var strSQL = "";
	
	/*strSQL = "select PayNo,IncomeNo,IncomeType,SumActuPayMoney,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay where 1=1 "			 
				 + getWherePart( 'PayNo' )
				 + getWherePart( 'IncomeNo' )
				 + getWherePart( 'IncomeType' )
				 + getWherePart( 'PayDate' )				 
				 + getWherePart( 'AgentCode' )*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllFeeQuerySql");
	mySql.setSqlId("AllFeeQuerySql1");
	mySql.addSubPara(fm.PayNo.value ); 
	mySql.addSubPara(fm.IncomeNo.value ); 	
	mySql.addSubPara(fm.IncomeType.value ); 	
	mySql.addSubPara(fm.PayDate.value ); 	
	mySql.addSubPara(fm.AgentCode.value ); 				 
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
		//strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		mySql = new SqlClass();
		mySql.setResourceName("sys.AllFeeQuerySql");
		mySql.setSqlId("AllFeeQuerySql2");
		mySql.addSubPara(fm.PayNo.value ); 
		mySql.addSubPara(fm.IncomeNo.value ); 	
		mySql.addSubPara(fm.IncomeType.value ); 	
		mySql.addSubPara(fm.PayDate.value ); 	
		mySql.addSubPara(fm.AgentCode.value ); 	
		mySql.addSubPara(managecomvalue ); 		
	}	
	else
	{
		//strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' )+" and managecom like '"+managecomvalue+"%%'";
		mySql = new SqlClass();
		mySql.setResourceName("sys.AllFeeQuerySql");
		mySql.setSqlId("AllFeeQuerySql3");
		mySql.addSubPara(fm.PayNo.value ); 
		mySql.addSubPara(fm.IncomeNo.value ); 	
		mySql.addSubPara(fm.IncomeType.value ); 	
		mySql.addSubPara(fm.PayDate.value ); 	
		mySql.addSubPara(fm.AgentCode.value ); 	
		mySql.addSubPara(fm.MngCom.value  ); 
		mySql.addSubPara(managecomvalue); 
	}		

	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("不存相对应的实收信息，请核实！");
     document.all('feequery').disabled = false;
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  
}



// 数据返回父窗口
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击费用明细按钮。" );
	else
	{
	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
		
		if (cPayNo == "")
		    return;
		    
		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
		 if (cIncomeType==1) {
		  	window.open("../sys/AllFeeQueryGDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
			}	
/*			if(cIncomeType==2)
			{
				window.open("../sys/AllFeeQueryPDetail.jsp?PolNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
			}*/
			
			else {
				window.open("../sys/AllFeeQueryPerDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");	
							
			}
	}
}

function queryAgent()
{
	if(document.all('MngCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('MngCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + trim(fm.AgentCode.value) +"'";
    mySql = new SqlClass();
		mySql.setResourceName("sys.AllFeeQuerySql");
		mySql.setSqlId("AllFeeQuerySql4");
		mySql.addSubPara(trim(fm.AgentCode.value) );  
    var arrResult = easyExecSql(mySql.getString());
    if (arrResult != null) {
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
      } 
	}
}


//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  }
}