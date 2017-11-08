//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";


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
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cReinsurItem = PolGrid.getRowColData(tSel - 1,1);
		  var cInsuredYear = PolGrid.getRowColData(tSel - 1,8);
		  window.open("ReEdorQueryPDetail.jsp?PolNo=" + cPolNo + "&ReinsurItem=" + cReinsurItem + "&InsuredYear=" + cInsuredYear);										
	}
}





// 给付查寻
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cReinsurItem = PolGrid.getRowColData(tSel - 1,1);
		  var cInsuredYear = PolGrid.getRowColData(tSel - 1,8);
		  window.open("ReClaimQueryPDetail.jsp?PolNo=" + cPolNo + "&ReinsurItem=" + cReinsurItem + "&InsuredYear=" + cInsuredYear);										
	}
}


//批改补退费查询
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName  + "&IsCancelPolFlag=" + tIsCancelPolFlag );										
	}
}



// 垫交/贷款费查询
function LoLoanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
           // alert("cPolNo"+cPolNo);
		  window.open("../sys/LoLoanQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag);										
	}
}




//保全查询
function PerEdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/AllPBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag );										
	}	
}

// 主险查寻
function MainRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo==cMainPolNo)
			myAlert(""+"您选择的是一条主险数据，请您选择一条附加险数据后，再点击主险查询按钮。"+"")
		else 
			{
									
				if (cMainPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// 书写SQL语句
						var mSQL = "";
						
						//mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";			 
						var mySql100=new SqlClass();
	 						mySql100.setResourceName("reinsure.RePolQuerySql"); //指定使用的properties文件名
	  						mySql100.setSqlId("RePolQuerySql100");//指定使用的Sql的id
	 		 				mySql100.addSubPara(cMainPolNo);//指定传入的参数
	  						mSQL=mySql100.getString();
						
						//查询SQL，返回结果字符串
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
					  //判断是否查询成功
					  if (!turnPage.strQueryResult) {
					  	PolGrid.clearData();
					  	myAlert(""+"数据库中没有满足条件的数据！"+"");
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
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo!=cMainPolNo)
			myAlert(""+"您选择的是一条附加险数据，请您选择一条主险数据后，再点击附加险查询按钮。"+"")
		else
			{
									
				if (cPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// 书写SQL语句
						var mSQL = "";
						
						//mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";			 
						var mySql101=new SqlClass();
	 						mySql101.setResourceName("reinsure.RePolQuerySql"); //指定使用的properties文件名
	  						mySql101.setSqlId("RePolQuerySql101");//指定使用的Sql的id
	 		 				mySql101.addSubPara(cMainPolNo);//指定传入的参数
	 		 				mySql101.addSubPara(cPolNo);//指定传入的参数
	  						mSQL=mySql101.getString();
						//查询SQL，返回结果字符串
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
					  //判断是否查询成功
					  if (!turnPage.strQueryResult) {
					  	PolGrid.clearData();
					  	myAlert(""+"数据库中没有满足条件的数据！"+"");
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
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    
	  window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag);	
		
	}
}

// 保单明细查询
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    
 
		    window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo);	
	}
}

// 理赔给付查询
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);										
	}	
}

//扫描件查询
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
	}	     
}

//集体保单扫描件查询
function ScanQuery2()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
	}	     
}

function GoBack(){
	
	top.opener.easyQueryClick();
	top.close();
	
	}
