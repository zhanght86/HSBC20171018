//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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


function easyQueryClick()
{
	
	//alert("here");
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
		
var sqlid35="ProposalQuerySql35";
var mySql35=new SqlClass();
mySql35.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql35.setSqlId(sqlid35);//指定使用的Sql的id
mySql35.addSubPara(tPolNo);//指定传入的参数
strSQL=mySql35.getString();
		
	//strSQL = "select ActuGetNo,GetMoney,GetDate,ConfDate,Operator,ManageCom,AgentCode from LJAGetDraw where PolNo='" + tPolNo + "' ";			 
	}
	else
	if(tIsCancelPolFlag=="1"){//销户保单查询
	
	var sqlid36="ProposalQuerySql36";
var mySql36=new SqlClass();
mySql36.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql36.setSqlId(sqlid36);//指定使用的Sql的id
mySql36.addSubPara(tPolNo);//指定传入的参数
strSQL=mySql36.getString();
	
	//strSQL = "select ActuGetNo,GetMoney,GetDate,ConfDate,Operator,ManageCom,AgentCode from LJAGetDraw where PolNo='" + tPolNo + "' ";			 
	}
	else {
	  alert("保单类型传输错误!");
	  return;
	  }//alert(strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
    //alert("查询失败！");
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
		alert( "请先选择一条记录。" );
	else
	{
	    var cActuGetNo = PolGrid.getRowColData(tSel - 1,1);				
//		parent.VD.gVSwitch.deleteVar("PayNo");				
//		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
		
		if (cActuGetNo == "")
		    return;
		    
		  var cOtherNoType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumGetMoney = 	PolGrid.getRowColData(tSel - 1,4);
		  //alert(cActuGetNo);
		  //alert(cOtherNoType);
		  //alert(cSumGetMoney);
		  if (cOtherNoType==0||cOtherNoType==1||cOtherNoType==2) {
				window.open("../sys/AllGetQueryDrawDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}	
			else if (cOtherNoType==3){
				window.open("../sys/AllGetQueryEdorDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);							
			}
			else if (cOtherNoType==4){
				window.open("../sys/AllGetQueryTempDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			else if (cOtherNoType==5){
				window.open("../sys/AllGetQueryClaimDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			else if (cOtherNoType==6){
				window.open("../sys/AllGetQueryOtherDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			else if (cOtherNoType==7){
				window.open("../sys/AllGetQueryBonusDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			
	}
}


function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClick();
	top.close();
}

function easyQueryClick2()
{
	var strSQL = "";
	
var sqlid37="ProposalQuerySql37";
var mySql37=new SqlClass();
mySql37.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql37.setSqlId(sqlid37);//指定使用的Sql的id
mySql37.addSubPara(fm.all('ContNo').value);//指定传入的参数
strSQL=mySql37.getString();
	
//	strSQL = "select appntname,insuredname from LCCont where ContNo='"+fm.all('ContNo').value+"'";
	var arrResult = easyExecSql(strSQL);
	if(arrResult!=null){
	  fm.all('InsuredName').value = arrResult[0][1] ;
    fm.all('AppntName').value = arrResult[0][0];		 
    }
	
	}