

var showInfo;
var mDebug="0";

var mSwitch = parent.VD.gVSwitch;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

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




function getQueryDetail()
{	
        //var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
        
        var sqlid825172118="DSHomeContSql825172118";
		var mySql825172118=new SqlClass();
		mySql825172118.setResourceName("sys.GrpPolDetailQuerySql");//指定使用的properties文件名
		mySql825172118.setSqlId(sqlid825172118);//指定使用的Sql的id
		mySql825172118.addSubPara(fm.GrpContNo.value);//指定传入的参数
		var strSQL=mySql825172118.getString();
        
         var arrResult = easyExecSql(strSQL, 1, 0);                        
            if (arrResult !== null) {
		         var   polNo=arrResult[0][0];  
		         var  GrpPrtNo=arrResult[0][2];
        	}
        	     
    mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);
	if(flag=="1"){
       easyScanWin = window.open("../cardgrp/GroupPolApproveInfo.jsp?LoadFlag=16&prtNo="+tPrtNo+"&polNo="+polNo+"&GrpPrtNo="+GrpPrtNo+"&SubType=TB1002&GrpContNo="+tGrpContNo, "", "status=no,resizable=yes,scrollbars=yes;"+sFeatures);   
	}else{
       easyScanWin = window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&prtNo="+tPrtNo+"&polNo="+polNo+"&GrpPrtNo="+GrpPrtNo+"&SubType=TB1002&GrpContNo="+tGrpContNo, "", "status=no,resizable=yes,scrollbars=yes;"+sFeatures);   
	}
		
}


function getQueryResult()
{
	
	var arrSelected = null;
	var tRow = PolGrid.getSelNo();
	
	if( tRow == null || tRow == 0 )
		return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0]= new Array();
	//设置需要返回的数组
	arrSelected[0][0] = PolGrid.getRowColData(tRow - 1,1);
	return arrSelected;
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
	    //var cPolNo = PolGrid.getRowColData(tSel - 1,1);
	    var cGrpContNo=document.all('GrpContNo').value;				

		
		if (cGrpContNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,5);
		  var cInsuredName = "";//PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,4);
		  window.open("../sys/GrpRelFeeQuery.jsp?GrpContNo=" + cGrpContNo + "&RiskCode=" + cRiskCode + "&AppntName=" + cAppntName,"",sFeatures);										
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/GetItemQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
	}
}

// 暂交费查询
function TempFeeQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	
	    
	  var cGrpContNo = document.all('GrpContNo').value; 		
		if (cGrpContNo == "")
		    return;
		    
		var sqlid825172231="DSHomeContSql825172231";
var mySql825172231=new SqlClass();
mySql825172231.setResourceName("sys.GrpPolDetailQuerySql");//指定使用的properties文件名
mySql825172231.setSqlId(sqlid825172231);//指定使用的Sql的id
mySql825172231.addSubPara(cGrpContNo);//指定传入的参数
var strSQL=mySql825172231.getString();
		
//		var strSQL="select AppntNo from LCGrpCont where GrpContNo='"+cGrpContNo+"'";
		var tAppntNo = easyExecSql(strSQL); 
		
		var sqlid825172315="DSHomeContSql825172315";
var mySql825172315=new SqlClass();
mySql825172315.setResourceName("sys.GrpPolDetailQuerySql");//指定使用的properties文件名
mySql825172315.setSqlId(sqlid825172315);//指定使用的Sql的id
mySql825172315.addSubPara(cGrpContNo);//指定传入的参数
var strSQL=mySql825172315.getString();
		
//		var strSQL="select APPntName from LJTempFee where OtherNo='"+cGrpContNo+"'group by APPntName";
		var tAppntName = easyExecSql(strSQL); 
		  
		window.open("../uw/ShowTempFee.jsp?Prtno="+cGrpContNo+"&AppntNo="+tAppntNo+"&AppntName="+tAppntName,"window1",sFeatures);  
		  //window.open("../sys/TempFeeQueryMain.jsp?GrpContNo=" + cGrpContNo + "&flag=0" + "&IsCancelPolFlag=0" );										
	
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PremQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;

		  window.open("../sys/InsureAccQueryMain.jsp?PolNo=" + cPolNo + "&flag=0"  + "&IsCancelPolFlag=0","",sFeatures );										
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
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,5);
		  var cInsuredName = "";//PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,4);
		  window.open("../sys/RelGetQueryMain.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
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
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,5);
		  var cInsuredName = "";//PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,4);
		  window.open("../sys/EdorQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
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
		  window.open("../sys/LoLoanQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/AllGBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0","",sFeatures);										
	}	
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
						
						var sqlid825172411="DSHomeContSql825172411";
var mySql825172411=new SqlClass();
mySql825172411.setResourceName("sys.GrpPolDetailQuerySql");//指定使用的properties文件名
mySql825172411.setSqlId(sqlid825172411);//指定使用的Sql的id
mySql825172411.addSubPara(cMainPolNo);//指定传入的参数
mSQL=mySql825172411.getString();
						
//						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";			 
						
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
{//alert(380);
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
						
						var sqlid825172527="DSHomeContSql825172527";
var mySql825172527=new SqlClass();
mySql825172527.setResourceName("sys.GrpPolDetailQuerySql");//指定使用的properties文件名
mySql825172527.setSqlId(sqlid825172527);//指定使用的Sql的id
mySql825172527.addSubPara(cMainPolNo);//指定传入的参数
mySql825172527.addSubPara(cPolNo);//指定传入的参数
mSQL=mySql825172527.getString();

						
//						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";			 
						
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
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    
 
		    window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);	
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

function ClaimQuery()
{
	      var tGrpContNo = document.all('GrpContNo').value;
          window.open("../sys/GRPClaimQueryMain.jsp?GrpContNo=" + tGrpContNo + "" ,"",sFeatures);										

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
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				
      var ContNo = document.all('PrtNo').value;
		
		  if (prtNo == "")
		    return;
		  if (ContNo == "")
	    {
		    alert("没有得到投保单号信息！");
		    return;
	    }
		   
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
//		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
        window.open("../uw/ImageQueryMainGrp.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}

function GoBack(){
	
	//top.opener.easyQueryClick();
	top.opener = null;
	top.close();
	
	}
	
function GrpPolSingleQueryClick(){
	
	
	  
	 
		try
		{
			
			window.open("./GrpPolSingleQueryMain.jsp?GrpContNo="+ document.all('GrpContNo').value,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}



}