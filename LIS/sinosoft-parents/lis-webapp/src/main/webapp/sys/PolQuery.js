//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
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



// 交费查寻
function FeeQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelFeeQueryMain.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
		 // window.open("../sys/AllFeeQueryPDetail.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag);										
	}
}




// 给付项查寻
function GetItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = document.all('ContNo').value;
	
		if (cContNo== "" ||cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/GetItemQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"',sFeatures");										
	}
}

// 暂交费查询
function TempFeeQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    //var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
	      var cContNo = document.all('ContNo').value;
		
		if (cContNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PolTempFeeQueryMain.jsp?ContNo=" + cContNo  + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"',sFeatures");										
	}
}

// 保费项查询
function PremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PremQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
	}
}

// 帐户查询
function InsuredAccQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/InsureAccQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"',sFeatures");										
	}
}

// 给付查寻
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
	
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelGetQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
	}
}

//生存领取查询
function LiveGetQuery()
{
	//alert('正在开发当中');
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = document.all('ContNo').value;
	
		if (cContNo== "" ||cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
          window.open("../sys/RelGetQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"',sFeatures");
	}

}


//批改补退费查询
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName  + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);										
	}
}



// 垫交/贷款费查询
function LoLoanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
           // alert("cPolNo"+cPolNo);
		  window.open("../sys/LoLoanQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
	}
}

//保全查询
function PerEdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/AllPBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);										
	}	
}


//保全查询
function BqEdorQueryClick()
{
    window.open("../sys/PolBqEdorMain.jsp?ContNo=" + tContNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);	
}


//红利分配查询
function BonusQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
		  window.open("../sys/BonusQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);										
	}
}

//万能险保单结算
function WannengQuery()
{
	alert('正在开发当中');
}

//欠交保费查询
function OwePremQuery()
{
	alert('正在开发当中');
}


// 主险查寻
function MainRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo==cMainPolNo)
			alert("您选择的是一条主险数据，请您选择一条附加险数据后，再点击主险查询按钮。")
		else 
			{
									
				if (cMainPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// 书写SQL语句
						var mSQL = "";
						
var sqlid3="ProposalQuerySql3";
var mySql3=new SqlClass();
mySql3.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql3.setSqlId(sqlid3);//指定使用的Sql的id
mySql3.addSubPara(cMainPolNo);//指定传入的参数
mSQL=mySql3.getString();
						
						
						//mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";			 
						
						//查询SQL，返回结果字符串
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
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
					  turnPage.strQuerySql     = mSQL; 
					  
					  //设置查询起始位置
					  turnPage.pageIndex = 0;  
					  
					  //在查询结果数组中取出符合页面显示大小设置的数组
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
					  
					  //调用MULTILINE对象显示查询结果
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}	
	}
}
//function MainRiskQuery()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "请先选择一条记录。" );
//	else
//	{
//	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
//			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
//		
//		if (cPolNo==cMainPolNo)
//			alert("您选择的是一条主险数据，请您选择一条附加险数据后，再点击主险查询按钮。")
//		else
//			{
//					
//					parent.VD.gVSwitch.deleteVar("PolNo");				
//					parent.VD.gVSwitch.addVar("PolNo","",cMainPolNo);
//					
//					if (cMainPolNo == "")
//					    return;
//					    
//					var tSQL = "select GrpPolNo from LCPol where PolNo='" + cMainPolNo +"'";
//					var tData = easyExecSql(tSQL,1,0,1);    
//					var GrpPolNo = tData[0][0];
//			
//				    if (GrpPolNo =="00000000000000000000") {
//						window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
//					} else {
//						window.open("./AllProQueryMain.jsp?LoadFlag=4");	
//					}
//			}							
//	}
//}


//附加险查询
function OddRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo!=cMainPolNo)
			alert("您选择的是一条附加险数据，请您选择一条主险数据后，再点击附加险查询按钮。")
		else
			{
									
				if (cPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// 书写SQL语句
						var mSQL = "";
						
						var sqlid4="ProposalQuerySql4";
var mySql4=new SqlClass();
mySql4.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql4.setSqlId(sqlid4);//指定使用的Sql的id
mySql4.addSubPara(cMainPolNo);//指定传入的参数
mySql4.addSubPara(cPolNo);//指定传入的参数
mSQL=mySql4.getString();
						
						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";			 
						
						//查询SQL，返回结果字符串
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
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
					  turnPage.strQuerySql     = mSQL; 
					  
					  //设置查询起始位置
					  turnPage.pageIndex = 0;  
					  
					  //在查询结果数组中取出符合页面显示大小设置的数组
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
					  
					  //调用MULTILINE对象显示查询结果
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}	
	}
}

// 保单明细查询
function PolClick1()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cPrtNo = PolGrid.getRowColData(tSel - 1,3);				
	    var cGrpContNo=PolGrid.getRowColData(tSel - 1,1);	
            if (cPrtNo == "")
	      return;
            if(cGrpContNo=="00000000000000000000")            
              //window.open("../sys/PolDetailQueryMain.jsp");
              window.open("../app/ContInputNoScanMain.jsp?prtNo="+ cPrtNo+"&LoadFlag=6","",sFeatures);             
            else
	      // Window.open("./GrpPolDetailQueryMain.jsp?PrtNo="+ cGrpContNo+"&LoadFlag=16");
	      window.open("../app/GroupPolApproveInfo.jsp?polNo="+ cGrpContNo+"&LoadFlag=16","",sFeatures);		    		
	}
}


// 理赔给付查询
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
	}	
}
//备注信息查询
function ShowRemark()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel-1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/FrameRemarkMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
	}	
}


//扫描件查询
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1","",sFeatures);								
	}	     
}

//集体保单扫描件查询
function ScanQuery2()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1","",sFeatures);								
	}	     
}

function GoBack(){
	
	//top.opener.easyQueryClick();
	top.close();
	
	}
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
  }
}	
function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	
var sqlid5="ProposalQuerySql5";
var mySql5=new SqlClass();
mySql5.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql5.setSqlId(sqlid5);//指定使用的Sql的id
mySql5.addSubPara(cAgentCode);//指定传入的参数
mySql5.addSubPara(document.all('ManageCom').value);//指定传入的参数
var strSql =mySql5.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}	
function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	
	var sqlid6="ProposalQuerySql6";
var mySql6=new SqlClass();
mySql6.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql6.setSqlId(sqlid6);//指定使用的Sql的id
mySql6.addSubPara(cAgentCode);//指定传入的参数
mySql6.addSubPara(document.all('ManageCom').value);//指定传入的参数
var strSql =mySql6.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}  	