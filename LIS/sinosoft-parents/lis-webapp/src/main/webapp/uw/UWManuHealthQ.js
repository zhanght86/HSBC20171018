//程序名称：PEdorUWManuHealth.js
//程序功能：保全人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();

var arrResult;
var turnPage1=new turnPageClass();
var turnPage2=new turnPageClass();
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.action= "./UWManuHealthChk.jsp";
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//initInsureNo();
	//initHealthGrid();
	//initHealthOtherGrid();
	//initMainUWHealthGrid();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
   
    showInfo.close();
    alert(content);
    window.location.reload();
   
  }
  else
  { 
	  var showStr="操作成功！";
  	
  	showInfo.close();
  	alert(showStr);
  	if(fm.UWFlag.value!="1"){
	  	//top.opener.easyQueryClick();
  		jQuery("#publicSearch").click();
	   	top.close();
  	}
  	window.location.reload();
  	
    //执行下一步操作
  }
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
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

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


function manuchkhealthmain()
{
	document.getElementById("fm").submit();
}


// 查询按钮
function easyQueryClickSingle()
{
	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {
	   var sqlid908111949="DSHomeContSql908111949";
var mySql908111949=new SqlClass();
mySql908111949.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql908111949.setSqlId(sqlid908111949);//指定使用的Sql的id
mySql908111949.addSubPara(tContNo);//指定传入的参数
mySql908111949.addSubPara(tInsuredNo);//指定传入的参数
strsql=mySql908111949.getString();
	   
//	   strsql = "select a.ContNo,(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq)"
//		   			+ " ,a.CustomerNo,a.Name,a.PEDate,a.MakeDate,"
//		   			//+ " (select stateflag from loprtmanager where prtseq=(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq))"
//		   			+ " a.printflag "
//		   			+ " from LCPENotice a where 1=1"
//				    + " and exists (select 1 from loprtmanager  where oldprtseq=a.prtseq)"
//				    + " and ContNo = '"+ tContNo + "'"
//				    + " and Customerno = '"+ tInsuredNo + "'";
				 				 
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = MainHealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
 
}


// 查询按钮
function easyQueryClick(parm1,parm2)
{

	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	var tSelNo = MainHealthGrid.getSelNo()-1;
  var tPrtSeq = MainHealthGrid.getRowColData(tSelNo,8);
  fm.PrtSeq.value = tPrtSeq;
  var tCustomerno = MainHealthGrid.getRowColData(tSelNo,3);
  fm.CustomerNo.value = tCustomerno;
  var tNewPrtSeq = MainHealthGrid.getRowColData(tSelNo,2);
  
  var sqlid908112202="DSHomeContSql908112202";
var mySql908112202=new SqlClass();
mySql908112202.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql908112202.setSqlId(sqlid908112202);//指定使用的Sql的id
mySql908112202.addSubPara(tContNo);//指定传入的参数
var tPrtNoSql=mySql908112202.getString();

  
//  var tPrtNoSql = "select prtno from lccont where contno='"+tContNo+"'";
  var tPrtNo = "";
  var tArrPrtNo = easyExecSql(tPrtNoSql);
  if(tArrPrtNo!=null){
	  tPrtNo = tArrPrtNo[0][0];
  }
  document.all('PEName').value=MainHealthGrid.getRowColData(tSelNo,4);
	//查询体检通知书影像
	queryHealthPic(tPrtNo,tContNo,tNewPrtSeq);

  if(tContNo != "" && tPrtSeq != "")
  { 
		var sqlid908112558="DSHomeContSql908112558";
		var mySql908112558=new SqlClass();
		mySql908112558.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
		mySql908112558.setSqlId(sqlid908112558);//指定使用的Sql的id
		mySql908112558.addSubPara(tContNo);//指定传入的参数
		mySql908112558.addSubPara(tContNo);//指定传入的参数
		mySql908112558.addSubPara(tContNo);//指定传入的参数
		mySql908112558.addSubPara(tContNo);//指定传入的参数
		mySql908112558.addSubPara(tContNo);//指定传入的参数
		mySql908112558.addSubPara(tContNo);//指定传入的参数
		mySql908112558.addSubPara(tPrtSeq);//指定传入的参数
		var strsql1=mySql908112558.getString();
		
		//查询体检基本信息
//		var strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LCPENotice.customertype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno))),"
//                         + " decode(LCPENotice.customertype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno)),"
//                         + " customerno, customertype,PEAddress ,REPEDate, agentname, operator,makedate,modifydate,PEResult,hospitcode "
//                         + " from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
//			prompt("",strsql1);
       var arrReturn = new Array();
       //modify-zbx-20120417 单证编码
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//体检人姓名
        	document.all('Sex').value = arrReturn[0][2];//体检人性别
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//体检人年龄
        	//document.all('CustomerNo').value = arrReturn[0][4];//体检人年龄
        	document.all('PEAddress').value = arrReturn[0][6];
        	document.all('PEHospital').value = arrReturn[0][13];
        	document.all('PEDate').value = arrReturn[0][7]; 
        	document.all('AccName').value = arrReturn[0][8];      	        	      	 	
       	 	document.all('Operator').value = arrReturn[0][9];
        	document.all('MakeDate').value = arrReturn[0][10];
        	document.all('ReplyDate').value = arrReturn[0][11];
        	
        	var PEResult = arrReturn[0][12];
        	var t1 = PEResult.indexOf(":");
        	var t2 = PEResult.lastIndexOf(":");

        	document.all('PEResult').value = PEResult.substring(0,t1);
        	document.all('PEResultDes').value = PEResult.substring(t1+1,t2);
        	document.all('Note').value = PEResult.substring(t2+1);
        	
//        	var sqlid908113620="DSHomeContSql908113620";
//			var mySql908113620=new SqlClass();
//			mySql908113620.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
//			mySql908113620.setSqlId(sqlid908113620);//指定使用的Sql的id
//			mySql908113620.addSubPara(tContNo);//指定传入的参数
//			mySql908113620.addSubPara(arrReturn[0][4]);//指定传入的参数
//			mySql908113620.addSubPara(arrReturn[0][5]);//指定传入的参数
//			mySql908113620.addSubPara(tPrtSeq);//指定传入的参数
//			var addStr = " and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"')) ";
//			mySql908113620.addSubPara(addStr);//指定传入的参数
//			strsql1=mySql908113620.getString();
        	
        	strsql1="select 1 from LCPENotice where 1=1"	 
				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
				         + " and customertype='"+  arrReturn[0][5] +"'"
				         + " and PrtSeq <> '"+ tPrtSeq + "' and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"'))";
        	arrReturn = new Array();
	        arrReturn = easyExecSql(strsql1); 
	        if(arrReturn!=null)
	           document.all('RePETag').value = "Y";//复检标记：不是第一次录入
	        else 
	           document.all('RePETag').value = "N";
        } 
			
			var sqlid908113922="DSHomeContSql908113922";
var mySql908113922=new SqlClass();
mySql908113922.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql908113922.setSqlId(sqlid908113922);//指定使用的Sql的id
mySql908113922.addSubPara(tPrtNo);//指定传入的参数
mySql908113922.addSubPara(tPrtSeq);//指定传入的参数
var tstrsql=mySql908113922.getString();
			//查询必选体检项目信息
//			var tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor,'1'"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='0' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,102);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//记录必选体检项目个数
			
			var sqlid908114010="DSHomeContSql908114010";
var mySql908114010=new SqlClass();
mySql908114010.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql908114010.setSqlId(sqlid908114010);//指定使用的Sql的id
mySql908114010.addSubPara(tPrtNo);//指定传入的参数
mySql908114010.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql908114010.getString();
			//查询可选体检项目信息
//			tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='1' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthOtherGrid,0,0,102);
			//查询其他体检项目信息
			var sqlid914163929="DSHomeContSql914163929";
var mySql914163929=new SqlClass();
mySql914163929.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914163929.setSqlId(sqlid914163929);//指定使用的Sql的id
mySql914163929.addSubPara(tPrtNo);//指定传入的参数
mySql914163929.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql914163929.getString();
			
//			tstrsql = "select peitemdivname,peitemdivres,peitemdivdes"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and peitemcode='Other'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		        {
		           document.all('OtherPEItem').value = arrReturn[0][0];
		          fm.OtherPEItemRes.value = arrReturn[0][1];
		          fm.OtherPEItemDes.value = arrReturn[0][2];
		          fm.OtherPEItemResCode.value=arrReturn[0][3];
		        }  
		          
		     //查询既往病史信息
		     var sqlid914164022="DSHomeContSql914164022";
var mySql914164022=new SqlClass();
mySql914164022.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164022.setSqlId(sqlid914164022);//指定使用的Sql的id
mySql914164022.addSubPara(tPrtNo);//指定传入的参数
mySql914164022.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql914164022.getString();

		     
//			tstrsql = "select DisResult, DisDesb from LCPENoticeResult where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		       {
			     document.all('PastDiseaseCode').value = arrReturn[0][0];  	   
		         document.all('PastDiseaseRes').value = arrReturn[0][1];
		         document.all('PastDiseaseDes').value = arrReturn[0][2];
		       }
		          
			
		/**	//查询体检结论信息
	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
	          // + " and ContNo = '"+ tContNo + "'"
					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//查询其他体检信息			 
			var strSQL = "select * from LCPENotice where 1=1"	
					+" and ContNo = '"+tContNo+"'"
					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
		**/	
              
      
  }
  /**
  if(tContNo != "" && tPrtSeq != "")
  {
	   var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
				
				  //+ " and ContNo = '"+ tContNo + "'"
				  + " and PrtSeq = '"+ tPrtSeq + "'";
	 
	
turnPage.strQueryResult  = easyQueryVer3(tstrsql, 1, 0, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	
    alert("未查询到体检项目数据！");
     return false;
  }
  
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 30 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = tstrsql; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage1.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
  }
 
  //查询体检结论信息
  var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
          // + " and ContNo = '"+ tContNo + "'"
				 + " and PrtSeq = '"+ tPrtSeq + "'";
				  //查询SQL，返回结果字符串
				  //alert(Sql);
  turnPage.strQueryResult = easyQueryVer3(Sql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = DisDesbGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = Sql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  	//查询其他体检信息			 
	var strSQL = "select * from LCPENotice where 1=1"	
				+" and ContNo = '"+tContNo+"'"
				+" and PrtSeq = '"+tPrtSeq+"'"; 	
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strSQL);
      
   if (arrReturn == null) {
			  alert("未查询到体检信息！");
			} else {
			   displayHealth(arrReturn[0]);
			}  

**/			
  return true;
 
}

function displayHealth(cArr)
{
	
  	try { document.all('Note').value = cArr[21]; } catch(ex) { };
}


// 无体检资料查询按钮
function easyQueryClickInit()
{
	fm.action= "./UWManuHealthQuery.jsp";
	document.getElementById("fm").submit();
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;
	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				HealthGrid.setRowColData( i, j+1, arrResult[i][j] );
				
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if

}

function initInsureNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	
		//alert(tPrtSeq);
	//查询SQL，返回结果字符串
// var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
//          + " and ContNo = '"+tContNo+"'";

//  var strSql = " select a1, a2, a3, a4, a5, a6, a7, a9"
//             + " from (select a.ContNo as a1,"
//             + " b.PrtSeq as a2,"
//             + " a.CustomerNo as a3,"
//             + " a.Name as a4,"
//             + " a.RePedate as a5,"
//             + " a.MakeDate as a6,"
//             + " a.PrintFlag as a7,"
//             + " row_number() over(partition by b.oldprtseq order by b.prtseq) as a8,"
//             + " b.oldprtseq a9"
//             + " from lcpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             + " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";
	var sqlid914164136="DSHomeContSql914164136";
var mySql914164136=new SqlClass();
mySql914164136.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164136.setSqlId(sqlid914164136);//指定使用的Sql的id
mySql914164136.addSubPara(tContNo);//指定传入的参数
var strSql=mySql914164136.getString();
	
	
//	var strSql ="select a.ContNo,(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq)"
//			+ " ,a.CustomerNo,a.Name,a.REPEDate,a.MakeDate,"
//   			+ " a.printflag,a.prtseq"
//   			+ " from LCPENotice a where 1=1"
//		    + " and exists (select 1 from loprtmanager  where oldprtseq=a.prtseq)"
//		    + " and ContNo = '"+ tContNo + "'";
		    //+ " and Customerno = '"+ tInsuredNo + "'";

 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
//    alert("查询失败！");
	  /*09-06-11 新契约扫描体检资料直接录入体检信息会导致此处无法查询出信息
	  	此处将readonly的form 置为可修改*/
	  fm.PrtNo.value = tContNo;
	  fm.PEName.className = "common";
	  fm.PEName.readOnly = false;
	  fm.Sex.className = "common";
	  fm.Sex.readOnly = false;
	  fm.Age.className = "common";
	  fm.Age.readOnly = false;
    return "";
  }
  
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = MainHealthGrid;

 //保存SQL语句
  turnPage.strQuerySql   = strSql;
  
 //设置查询起始位置
  turnPage.pageIndex = 0; 
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //调用MULTILINE对象显示查询结果
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);


}

  function initCustomerNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	
		//alert(tPrtSeq);
	//查询SQL，返回结果字符串
//  var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
//             + " and ContNo = '"+tContNo+"'"
//             + " and CustomerNo = '"+tCustomerNo+"'";
//   var strSql = " select a1, a2, a3, a4, a5, a6, a7, a9"
//             + " from (select a.ContNo as a1,"
//             + " b.PrtSeq as a2,"
//             + " a.CustomerNo as a3,"
//             + " a.Name as a4,"
//             + " a.Pedate as a5,"
//             + " a.MakeDate as a6,"
//             + " a.PrintFlag as a7,"
//             + " row_number() over(partition by b.oldprtseq order by b.prtseq) as a8,"
//             + " b.oldprtseq a9"
//             + " from lcpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             //+ " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.customerno='"+tCustomerNo+"'"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";

	var sqlid914164341="DSHomeContSql914164341";
var mySql914164341=new SqlClass();
mySql914164341.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164341.setSqlId(sqlid914164341);//指定使用的Sql的id
mySql914164341.addSubPara(tContNo);//指定传入的参数
mySql914164341.addSubPara(tCustomerNo);//指定传入的参数
var strSql=mySql914164341.getString();
	
//	var strSql ="select a.ContNo,(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq)"
//		+ " ,a.CustomerNo,a.Name,a.PEDate,a.MakeDate,"
//			+ " a.printflag,a.prtseq"
//			+ " from LCPENotice a where 1=1"
//	    + " and exists (select 1 from loprtmanager  where oldprtseq=a.prtseq)"
//	    + " and ContNo = '"+ tContNo + "'"
//	    + " and Customerno = '"+ tCustomerNo + "'";
 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
 
  if (!turnPage.strQueryResult)
  {
    alert("查询失败！");
    return "";
  }
  
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = MainHealthGrid;

 //保存SQL语句
  turnPage.strQuerySql   = strSql;
  
 //设置查询起始位置
  turnPage.pageIndex = 0; 
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //调用MULTILINE对象显示查询结果
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   
}


//初始化医院
function initHospital(tContNo)
{
	var i,j,m,n;
	var returnstr;
	

	var sqlid914164447="DSHomeContSql914164447";
var mySql914164447=new SqlClass();
mySql914164447.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164447.setSqlId(sqlid914164447);//指定使用的Sql的id
mySql914164447.addSubPara(tContNo);//指定传入的参数
var strSql=mySql914164447.getString();
	
//	var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("医院初始化失败！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  //turnPage.pageDisplayGrid = VarGrid;    
          
  //保存SQL语句
  //turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  //turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("查询失败!!");
  			return "";
  		}
  	}
}
else
{
	alert("查询失败!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Hospital.CodeData = returnstr;
  return returnstr;
}


/*********************************************************************
 *  体检疾病信息保存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function saveDisDesb()
{   
	 /**
		if(DisDesbGrid.getRowColData(0,2)==null||DisDesbGrid.getRowColData(0,2)==""){
		alert("体检结果栏不能为空！");
		return false;		
		}			
		
	     var strsql1="select managecom from lccont where contno = '"+fm.ContNo.value+"'";
			
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('ManageCom').value = arrReturn[0][0];
        	//alert(document.all('ManageCom').value);
       }
     **/  
     if(fm.PEResultCode.value == null|| fm.PEResultCode.value == '')
       {
          alert("体检结论栏不能为空！");
          return false;
       }
       if(fm.PEResultCode.value == 'P'&& fm.PEResultDes.value == '')
       {
          alert("体检人体检结论为异常，结论详细信息必须录入！");
          return false;
       }
//     if(fm.PEHospital.value == null || fm.PEHospital.value == '')
//       {
//          alert("请选择体检医院！");
//          return false;
//       }
		
	   if(verifyInput() == false) return false;	
	   //增加体检医院不能为空的校验
	   if((fm.PEHospital.value==null || fm.PEHospital.value == '')&&(fm.PEAddress.value==null||fm.PEAddress.value=="")){
		   alert("体检医院不能为空！");
		   return false;
	   }
       //增加体检医院不为定点医院则必须录入陪检人的校验
       var tCheckSql = "select 1 from ldhospital where hosstate='0' and hospitalname='"+fm.PEAddress.value+"' ";
       var arrCheck = easyExecSql(tCheckSql);
       if(!arrCheck){
    	   if(fm.AccName.value==""){
    		   alert("体检医院不为定点医院，必须录入陪检人！");
    		   return false;
    	   }
       }
       if(fm.PEDate.value == null || fm.PEDate.value == '')
       {
          alert("请选择体检时间！");
          return false;
       }
       if(fm.PastDiseaseRes.value == null || fm.PastDiseaseRes.value == '')
       {
          alert("请选择既往病史情况！");
          return false;
       }
       
       if(fm.PastDiseaseCode.value == 'P'&& fm.PastDiseaseDes.value == '')
       {
          alert("体检人有既往病史，既往病史详细信息必须录入！");
          return false;
       }
       //校验体检项目是否全部录入
       var row = HealthGrid.mulLineCount;
       var ItemNum = 0;
       var flag =0;
       for (var i=0 ; i<row; i++)
       {
         if(HealthGrid.getRowColData(i,4)==null || HealthGrid.getRowColData(i,4)=="")
         {
          alert("尚有必选体检项目名称未选择！");
          return false;
         }  
         if(HealthGrid.getRowColData(i,8)!=null && HealthGrid.getRowColData(i,8)!="" && HealthGrid.getRowColData(i,8)=="1")
         {
           ItemNum++ ;
           if(HealthGrid.getRowColData(i,5)==null || HealthGrid.getRowColData(i,5)=="")
          //  || (HealthGrid.getRowColData(i,6)==null || HealthGrid.getRowColData(i,6)=="")
           // || (HealthGrid.getRowColData(i,7)==null || HealthGrid.getRowColData(i,7)=="")
           // )
          {
             flag = 1;             
          }
         }
       }
       
       if(ItemNum < parseInt(fm.ItemNum.value))
       {
             if(!confirm("尚有必选体检项未选择，是否继续保存？"))
                return false;             
       }
       
       //校验可选体检项目是否录入结论 必须选择项目名称
       row = HealthOtherGrid.mulLineCount;
       for (var i=0 ; i<row; i++)
       {
         if(HealthOtherGrid.getRowColData(i,4)==null || HealthOtherGrid.getRowColData(i,4)=="")
         {
          alert("尚有可选体检项目名称未选择！");
          return false;
         } 
        
           if(HealthOtherGrid.getRowColData(i,5)==null || HealthOtherGrid.getRowColData(i,5)=="")
          //  || (HealthOtherGrid.getRowColData(i,6)==null || HealthOtherGrid.getRowColData(i,6)=="")
           // || (HealthOtherGrid.getRowColData(i,7)==null || HealthOtherGrid.getRowColData(i,7)=="")
           // )
          {
             flag = 1;             
          }
       }       
       
       if(flag == 1)
       {
         if(!confirm("尚有未录入体检项，是否继续保存？"))
                return false;
       }

       if((fm.OtherPEItem.value!=null && fm.OtherPEItem.value!="")&& (fm.OtherPEItemResCode.value!="O") && (fm.OtherPEItemRes.value==null || fm.OtherPEItemRes.value==""))
       {
         if(!confirm("尚有其他体检项未录入，是否继续保存？"))
                return false;
       } 
      // var tActivityID = fm.ActivityID.value;    

       //alert(fm.PrtSeq.value);return false;
	var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 lockScreen('lkscreen');  
  fm.action= "./UWManuHealthQSave.jsp";
  document.getElementById("fm").submit(); //提交
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	var sqlid914164553="DSHomeContSql914164553";
var mySql914164553=new SqlClass();
mySql914164553.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164553.setSqlId(sqlid914164553);//指定使用的Sql的id
mySql914164553.addSubPara(prtSeq);//指定传入的参数
var strSQL=mySql914164553.getString();
	
	
//	var strSQL = " select distinct PEItemCode,PEItemName,FREEPE,PEItemResult from LCPENoticeItem where PrtSeq = '"+prtSeq+"'"
	          // + " and contno='"+contno+"'"
	           ;	
        
        	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //alert(turnPage.strQueryResult);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	
    //alert("未查询到满足条件的数据！");
     return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 30 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

      
       
}

//查询体检资料影像
function queryHealthPic(tPrtNo,tContNo,tPrtSeq){
	//09-06-11增加如果新单同时扫描体检资料对扫描资料影像件的处理
	//新单时有体检资料、人工核保时也有体检资料，则以人工核保时为准
	//alert(tPrtNo+"   "+tContNo+"    "+tPrtSeq);
	
	var sqlid914164712="DSHomeContSql914164712";
var mySql914164712=new SqlClass();
mySql914164712.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164712.setSqlId(sqlid914164712);//指定使用的Sql的id
mySql914164712.addSubPara(tPrtSeq);//指定传入的参数
mySql914164712.addSubPara(tPrtNo);//指定传入的参数
var tSubtypeSql=mySql914164712.getString();
	
//	var tSubtypeSql = "select docid from es_doc_main where doccode='"+tPrtSeq+"' and subtype='UN103'"
//					+ " union "
//					+ " select docid from es_doc_main where doccode='"+tPrtNo+"' and subtype='UN103'";
	var tSubType = "";
	var QueryType = "";
	var DocID=easyExecSql(tSubtypeSql);
	if(DocID!=null){
		tSubType = "TB1013";
		QueryType = "1";
	}else{
		tSubType = "TB1020";
		QueryType = "0";
		
		var sqlid914164840="DSHomeContSql914164840";
var mySql914164840=new SqlClass();
mySql914164840.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914164840.setSqlId(sqlid914164840);//指定使用的Sql的id
mySql914164840.addSubPara(tContNo);//指定传入的参数
var strSQL=mySql914164840.getString();
		
		DocID = easyExecSql(strSQL);
	}//alert(tPrtSeq);
	//查询相应的打印流水号，根据流水号对应到体检影像。
	parent.fraPic.location="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo="+tPrtNo+"&BussNoType=11&BussType=TB&SubType=TB1013"+"&QueryType="+QueryType+"&DocID="+DocID;
	
}


//增加在体检修改时将原来体检的数据查询显示 --modify ln 2008-11-6
function queryHealthInfo(aContNo,aPrtSeq)
{
	var tContNo=aContNo;
	var tPrtSeq=aPrtSeq;
	if(tContNo != "" && tPrtSeq != "")
	{
			//查询体检基本信息
			
		var sqlid914165202="DSHomeContSql914165202";
var mySql914165202=new SqlClass();
mySql914165202.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914165202.setSqlId(sqlid914165202);//指定使用的Sql的id
mySql914165202.addSubPara(tContNo);//指定传入的参数
mySql914165202.addSubPara(tContNo);//指定传入的参数
mySql914165202.addSubPara(tContNo);//指定传入的参数
mySql914165202.addSubPara(tContNo);//指定传入的参数
mySql914165202.addSubPara(tContNo);//指定传入的参数
mySql914165202.addSubPara(tContNo);//指定传入的参数
mySql914165202.addSubPara(tPrtSeq);//指定传入的参数
var strsql1=mySql914165202.getString();

//		var strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LCPENotice.customertype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno))),"
//                         + " decode(LCPENotice.customertype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno)),"
//                         + " customerno, customertype"
//                         + " from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
			
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//体检人姓名
        	document.all('Sex').value = arrReturn[0][2];//体检人性别
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//体检人年龄
        	//document.all('CustomerNo').value = arrReturn[0][4];//体检人年龄
        	
        	var sqlid914165553="DSHomeContSql914165553";
var mySql914165553=new SqlClass();
mySql914165553.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914165553.setSqlId(sqlid914165553);//指定使用的Sql的id
mySql914165553.addSubPara(tContNo);//指定传入的参数
mySql914165553.addSubPara(arrReturn[0][4]);//指定传入的参数
mySql914165553.addSubPara(arrReturn[0][5]);//指定传入的参数
mySql914165553.addSubPara(tPrtSeq);//指定传入的参数
strsql1=mySql914165553.getString();
        	
//        	strsql1="select 1 from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customertype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "'";
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);     
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//复检标记：不是第一次录入
		       else 
		          document.all('RePETag').value = "N";
        } 
			//查询体检项目信息
			var sqlid914165708="DSHomeContSql914165708";
var mySql914165708=new SqlClass();
mySql914165708.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914165708.setSqlId(sqlid914165708);//指定使用的Sql的id
mySql914165708.addSubPara(tPrtSeq);//指定传入的参数
var tstrsql=mySql914165708.getString();

			
//			var tstrsql = "select a.peitemcode,a.peitemname,b.code,b.codename,'','','','1'"
//			          + " from LCPENoticeItem a,ldcode b where 1=1 and a.peitemcode = b.codetype"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and a.PrtSeq = '"+ tPrtSeq + "' order by a.peitemcode,b.code";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,102);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//记录必选体检项目个数
			//查询其他体检项目信息
			var sqlid914165800="DSHomeContSql914165800";
var mySql914165800=new SqlClass();
mySql914165800.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914165800.setSqlId(sqlid914165800);//指定使用的Sql的id
mySql914165800.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql914165800.getString();
			
//			tstrsql = "select peitemname from LCPENoticeItem where 1=1 and peitemcode = 'other'"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		          document.all('OtherPEItem').value = arrReturn[0][0];
			
		/**	//查询体检结论信息
	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
	          // + " and ContNo = '"+ tContNo + "'"
					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//查询其他体检信息			 
			var strSQL = "select * from LCPENotice where 1=1"	
					+" and ContNo = '"+tContNo+"'"
					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
		**/	
		}

		
  return true;
}

/**
* 计算投保人被保人年龄《投保日期与生日之差=投保人被保人年龄》,2005-11-18日添加
* 参数，出生日期yy－mm－dd；投保日期yy－mm－dd
* 返回  年龄
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("生日"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("投保日期"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//当前月大于出生月
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//当前月小于出生月
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//当前月等于出生月的时候，看出生日
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}

//增加在体检修改时将原来体检的数据查询显示
function queryHealthInfo1(aContNo,aPrtSeq)
{
	var tContNo=aContNo;
	var tPrtSeq=aPrtSeq;
	
	//查询体检结论信息
	var sqlid914165849="DSHomeContSql914165849";
var mySql914165849=new SqlClass();
mySql914165849.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914165849.setSqlId(sqlid914165849);//指定使用的Sql的id
mySql914165849.addSubPara(tPrtSeq);//指定传入的参数
var sSql=mySql914165849.getString();
	
//  var sSql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//        // + " and ContNo = '"+ tContNo + "'"
//			 + " and PrtSeq = '"+ tPrtSeq + "'";	 
  var arrSReturn = new Array();
	arrSReturn = easyExecSql(sSql);
	if (arrSReturn == null) 
	{} 
	else
	{
		if(tContNo != "" && tPrtSeq != "")
		{
			//查询体检基本信息
			var sqlid914170000="DSHomeContSql914170000";
var mySql914170000=new SqlClass();
mySql914170000.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914170000.setSqlId(sqlid914170000);//指定使用的Sql的id
mySql914170000.addSubPara(tContNo);//指定传入的参数
mySql914170000.addSubPara(tPrtSeq);//指定传入的参数
var strsql1=mySql914170000.getString();
			
//			var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag from LCPENotice where 1=1"	 
//			         + " and ContNo = '"+ tContNo + "'"
//			         + " and PrtSeq = '"+ tPrtSeq + "'";
			
			var arrReturn = new Array();
			arrReturn = easyExecSql(strsql1);     
			if(arrReturn!=null)
			{
			
				document.all('PEAddress').value = arrReturn[0][0];
				document.all('PEDoctor').value = arrReturn[0][1];
				document.all('PEDate').value = arrReturn[0][2];
			 	document.all('REPEDate').value = arrReturn[0][3];
			 	document.all('Operator').value = arrReturn[0][4];
				document.all('MakeDate').value = arrReturn[0][5];
				document.all('ReplyDate').value = arrReturn[0][6];
				document.all('MasculineFlag').value = arrReturn[0][7];
			}
			//查询体检项目信息
			var sqlid914170054="DSHomeContSql914170054";
var mySql914170054=new SqlClass();
mySql914170054.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914170054.setSqlId(sqlid914170054);//指定使用的Sql的id
mySql914170054.addSubPara(tPrtSeq);//指定传入的参数
var tstrsql=mySql914170054.getString();
			
//			var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			turnPage.queryModal(tstrsql, HealthGrid);	
			
			//查询体检结论信息
			var sqlid914170134="DSHomeContSql914170134";
var mySql914170134=new SqlClass();
mySql914170134.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914170134.setSqlId(sqlid914170134);//指定使用的Sql的id
mySql914170134.addSubPara(tPrtSeq);//指定传入的参数
var Sql=mySql914170134.getString();
			
//	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//	          // + " and ContNo = '"+ tContNo + "'"
//					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//查询其他体检信息
				var sqlid914170227="DSHomeContSql914170227";
var mySql914170227=new SqlClass();
mySql914170227.setResourceName("uw.UWManuHealthQSql");//指定使用的properties文件名
mySql914170227.setSqlId(sqlid914170227);//指定使用的Sql的id
mySql914170227.addSubPara(tContNo);//指定传入的参数
mySql914170227.addSubPara(tPrtSeq);//指定传入的参数
var strSQL=mySql914170227.getString();
							 
//			var strSQL = "select * from LCPENotice where 1=1"	
//					+" and ContNo = '"+tContNo+"'"
//					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
			
		}
		
	}
		
  return true;
}
