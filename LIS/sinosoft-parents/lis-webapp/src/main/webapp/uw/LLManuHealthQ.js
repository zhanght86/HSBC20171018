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

  fm.action= "./BQManuHealthChk.jsp";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
   
    showInfo.close();
    alert(content);
   
  }
  else
  { 
	  var showStr="操作成功！";
  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
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
	fm.submit();
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
	   var sqlid916173255="DSHomeContSql916173255";
var mySql916173255=new SqlClass();
mySql916173255.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql916173255.setSqlId(sqlid916173255);//指定使用的Sql的id
mySql916173255.addSubPara(tContNo);//指定传入的参数
mySql916173255.addSubPara(tInsuredNo);//指定传入的参数
strsql=mySql916173255.getString();
	   
//	   strsql = "select LLUWPENotice.ContNo,LLUWPENotice.PrtSeq,LLUWPENotice.CustomerNo,LLUWPENotice.Name,LLUWPENotice.PEDate,LLUWPENotice.MakeDate,LOPRTManager.StateFlag from LLUWPENotice,LOPRTManager where 1=1"
//				    + " and LLUWPENotice.PrtSeq = LOPRTManager.PrtSeq"
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
  var tNewPrtSeq = MainHealthGrid.getRowColData(tSelNo,1);
  document.all('PEName').value=MainHealthGrid.getRowColData(tSelNo,4);
	//查询体检通知书影像
	queryHealthPic(tNewPrtSeq);

  if(tContNo != "" && tPrtSeq != "")
  { 
			//查询体检基本信息
		var sqlid916173533="DSHomeContSql916173533";
var mySql916173533=new SqlClass();
mySql916173533.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql916173533.setSqlId(sqlid916173533);//指定使用的Sql的id
mySql916173533.addSubPara(tContNo);//指定传入的参数
mySql916173533.addSubPara(tContNo);//指定传入的参数
mySql916173533.addSubPara(tContNo);//指定传入的参数
mySql916173533.addSubPara(tContNo);//指定传入的参数
mySql916173533.addSubPara(tContNo);//指定传入的参数
mySql916173533.addSubPara(tContNo);//指定传入的参数
mySql916173533.addSubPara(tPrtSeq);//指定传入的参数
var strsql1=mySql916173533.getString();
		
//		var strsql1="select (select prtno from lpcont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LLUWPENotice.customertype,'A',(select appntsex from lpappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select sex from lpinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno))),"
//                         + " decode(LLUWPENotice.customertype,'A',(select appntbirthday from lpappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select birthday from lpinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno)),"
//                         + " customerno, customertype,PEAddress ,pedate, '', operator,makedate,modifydate,PEResult "
//                         + " from LLUWPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
			
       var arrReturn = new Array();//prompt("",strsql1);
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//体检人姓名
        	document.all('Sex').value = arrReturn[0][2];//体检人性别
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//体检人年龄
        	//document.all('CustomerNo').value = arrReturn[0][4];//体检人年龄
        	document.all('PEAddress').value = arrReturn[0][6];
        	document.all('PEDate').value = arrReturn[0][7]; 
        	document.all('AccName').value = arrReturn[0][8];      	        	      	 	
       	 	document.all('Operator').value = arrReturn[0][9];
        	document.all('MakeDate').value = arrReturn[0][10];
        	document.all('ReplyDate').value = arrReturn[0][11];
        	//alert(arrReturn[0][12]);
        	var PEResult = arrReturn[0][12];
        	var t1 = PEResult.indexOf(":");
        	var t2 = PEResult.lastIndexOf(":");

        	document.all('PEResult').value = PEResult.substring(0,t1);
        	document.all('PEResultDes').value = PEResult.substring(t1+1,t2);
        	document.all('Note').value = PEResult.substring(t2+1);
        	
        	var sqlid917094943="DSHomeContSql917094943";
var mySql917094943=new SqlClass();
mySql917094943.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917094943.setSqlId(sqlid917094943);//指定使用的Sql的id
mySql917094943.addSubPara(tContNo);//指定传入的参数
mySql917094943.addSubPara(arrReturn[0][4]);//指定传入的参数
mySql917094943.addSubPara(arrReturn[0][5]);//指定传入的参数
mySql917094943.addSubPara(tPrtSeq);//指定传入的参数
mySql917094943.addSubPara(tday);//指定传入的参数
mySql917094943.addSubPara(tday);//指定传入的参数
mySql917094943.addSubPara(time);//指定传入的参数
strsql1=mySql917094943.getString();
        	
//        	strsql1="select 1 from LLUWPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customertype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "' and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"'))";
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);   //prompt("",strsql1);  
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//复检标记：不是第一次录入
		       else 
		          document.all('RePETag').value = "N";
        } 
			//查询必选体检项目信息
			var sqlid917095259="DSHomeContSql917095259";
var mySql917095259=new SqlClass();
mySql917095259.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917095259.setSqlId(sqlid917095259);//指定使用的Sql的id
mySql917095259.addSubPara(tContNo);//指定传入的参数
mySql917095259.addSubPara(tPrtSeq);//指定传入的参数
var tstrsql=mySql917095259.getString();

			
			
//			var tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor,'1'"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='0' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,30);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//记录必选体检项目个数
			//查询可选体检项目信息
			var sqlid917095345="DSHomeContSql917095345";
var mySql917095345=new SqlClass();
mySql917095345.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917095345.setSqlId(sqlid917095345);//指定使用的Sql的id
mySql917095345.addSubPara(tContNo);//指定传入的参数
mySql917095345.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql917095345.getString();

			
//			tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='1' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthOtherGrid,0,0,30);
			//查询其他体检项目信息
			var sqlid917095430="DSHomeContSql917095430";
var mySql917095430=new SqlClass();
mySql917095430.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917095430.setSqlId(sqlid917095430);//指定使用的Sql的id
mySql917095430.addSubPara(tContNo);//指定传入的参数
mySql917095430.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql917095430.getString();
			
			
//			tstrsql = "select peitemdivname,peitemdivres,peitemdivdes"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and peitemcode='other'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		        {
		           document.all('OtherPEItem').value = arrReturn[0][0];
		          fm.OtherPEItemRes.value = arrReturn[0][1];
		          fm.OtherPEItemDes.value = arrReturn[0][2];
		        }  
		          
		     //查询既往病史信息
		  var sqlid917095511="DSHomeContSql917095511";
var mySql917095511=new SqlClass();
mySql917095511.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917095511.setSqlId(sqlid917095511);//指定使用的Sql的id
mySql917095511.addSubPara(tContNo);//指定传入的参数
mySql917095511.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql917095511.getString();
		     
		     
//			tstrsql = "select DisResult, DisDesb from LCPENoticeResult where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		       {
		         document.all('PastDiseaseRes').value = arrReturn[0][0];
		         document.all('PastDiseaseDes').value = arrReturn[0][1];
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
	fm.submit();
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
//alert(399);
  var sqlid917100009="DSHomeContSql917100009";
var mySql917100009=new SqlClass();
mySql917100009.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917100009.setSqlId(sqlid917100009);//指定使用的Sql的id
mySql917100009.addSubPara(tContNo);//指定传入的参数
var strSql=mySql917100009.getString();
  
//  var strSql = " select a1, a2, a3, a4, a5, a6, a7, a9"
//             + " from (select a.ContNo as a1,"
//             + " b.PrtSeq as a2,"
//             + " a.CustomerNo as a3,"
//             + " a.Name as a4,"
//             + " a.Pedate as a5,"
//             + " a.MakeDate as a6,"
//             + " a.PrintFlag as a7,"
//             + " row_number() over(partition by b.oldprtseq order by b.prtseq) as a8,"
//             + " b.oldprtseq a9"
//             + " from lluwpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             + " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";
//prompt("",strSql);
 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("未查询到体检信息！");
    return "";
  }
  //alert(427);
  
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
   var sqlid917100158="DSHomeContSql917100158";
var mySql917100158=new SqlClass();
mySql917100158.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917100158.setSqlId(sqlid917100158);//指定使用的Sql的id
mySql917100158.addSubPara(tCustomerNo);//指定传入的参数
var strSql=mySql917100158.getString();
   
   
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
//             + " from lluwpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             //+ " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.customerno='"+tCustomerNo+"'"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";

 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
 //alert(487);
  if (!turnPage.strQueryResult)
  {
    alert("查询失败！");
    return "";
  }
  //alert(493);
  
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
	

	var sqlid917100251="DSHomeContSql917100251";
var mySql917100251=new SqlClass();
mySql917100251.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917100251.setSqlId(sqlid917100251);//指定使用的Sql的id
mySql917100251.addSubPara(tContNo);//指定传入的参数
var strSql=mySql917100251.getString();
	
//	var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lpcont where ContNo = '"+tContNo+"')";
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
     if(fm.PEHospital.value == null || fm.PEHospital.value == '')
       {
          alert("请选择体检医院！");
          return false;
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
       
       if(fm.PEHospital.value == null || fm.PEHospital.value == '')
       {
          alert("请选择体检医院！");
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
	
 
  fm.action= "./LLManuHealthQSave.jsp";
  fm.submit(); //提交
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	
	var sqlid917100350="DSHomeContSql917100350";
var mySql917100350=new SqlClass();
mySql917100350.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917100350.setSqlId(sqlid917100350);//指定使用的Sql的id
mySql917100350.addSubPara(prtSeq);//指定传入的参数
var strSQL=mySql917100350.getString();
	
//	var strSQL = " select distinct PEItemCode,PEItemName,FREEPE,PEItemResult from LLUWPENoticeItem where PrtSeq = '"+prtSeq+"'"
//	          // + " and contno='"+contno+"'"
//	           ;	
        
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
function queryHealthPic(tPrtSeq){
	//查询相应的打印流水号，根据流水号对应到体检影像。
	parent.fraPic.location="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo="+tPrtSeq+"&BussNoType=11&BussType=TB&SubType=TB1015";
	
}


//增加在体检修改时将原来体检的数据查询显示 --modify ln 2008-11-6
function queryHealthInfo(aContNo,aPrtSeq)
{
	var tContNo=aContNo;
	var tPrtSeq=aPrtSeq;
	if(tContNo != "" && tPrtSeq != "")
	{
			//查询体检基本信息
		//var strsql1="select (select prtno from lpcont where contno='"+ tContNo +"'),name,"
        //                 + " (select codename from ldcode where codetype='sex' and code=decode(LPPENotice.customertype,'A',(select appntsex from lpappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select sex from lpinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno))),"
        //                 + " decode(LPPENotice.customertype,'A',(select appntbirthday from lpappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select birthday from lpinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno)),"
        //                 + " customerno, customertype"
        //                 + " from LPPENotice where 1=1"	 
		//		         + " and ContNo = '"+ tContNo + "'"
		//		         + " and PrtSeq = '"+ tPrtSeq + "'";
		//	turnPage.strQueryResult = easyQueryVer3(strsql1, 1, 1, 1);
		//if(!turnPage.strQueryResult){
		var sqlid917100655="DSHomeContSql917100655";
var mySql917100655=new SqlClass();
mySql917100655.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917100655.setSqlId(sqlid917100655);//指定使用的Sql的id
mySql917100655.addSubPara(tContNo);//指定传入的参数
mySql917100655.addSubPara(tContNo);//指定传入的参数
mySql917100655.addSubPara(tContNo);//指定传入的参数
mySql917100655.addSubPara(tContNo);//指定传入的参数
mySql917100655.addSubPara(tContNo);//指定传入的参数
mySql917100655.addSubPara(tContNo);//指定传入的参数
mySql917100655.addSubPara(tPrtSeq);//指定传入的参数
var strsql1=mySql917100655.getString();
		
//		var	strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LLUWPENotice.customertype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno))),"
//                         + " decode(LLUWPENotice.customertype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno)),"
//                         + " customerno, customertype"
//                         + " from LLUWPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
		//}
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     //prompt("",strsql1);
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//体检人姓名
        	document.all('Sex').value = arrReturn[0][2];//体检人性别
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//体检人年龄
        	//document.all('CustomerNo').value = arrReturn[0][4];//体检人年龄
        	
        	var sqlid917101000="DSHomeContSql917101000";
var mySql917101000=new SqlClass();
mySql917101000.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917101000.setSqlId(sqlid917101000);//指定使用的Sql的id
mySql917101000.addSubPara(tContNo);//指定传入的参数
mySql917101000.addSubPara(arrReturn[0][4]);//指定传入的参数
mySql917101000.addSubPara(arrReturn[0][5]);//指定传入的参数
mySql917101000.addSubPara(tPrtSeq);//指定传入的参数
var strsql1=mySql917101000.getString();
        	
//        	strsql1="select 1 from LLUWPENotice where 1=1"	 
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
			var sqlid917101118="DSHomeContSql917101118";
var mySql917101118=new SqlClass();
mySql917101118.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917101118.setSqlId(sqlid917101118);//指定使用的Sql的id
mySql917101118.addSubPara(tPrtSeq);//指定传入的参数
var tstrsql=mySql917101118.getString();
			
			
//			var tstrsql = "select a.peitemcode,a.peitemname,b.code,b.codename,'','','','1'"
//			          + " from LLUWPENoticeItem a,ldcode b where 1=1 and a.peitemcode = b.codetype"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and a.PrtSeq = '"+ tPrtSeq + "' order by a.peitemcode,b.code";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,30);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//记录必选体检项目个数
			//查询其他体检项目信息
			var sqlid917101259="DSHomeContSql917101259";
var mySql917101259=new SqlClass();
mySql917101259.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917101259.setSqlId(sqlid917101259);//指定使用的Sql的id
mySql917101259.addSubPara(tPrtSeq);//指定传入的参数
tstrsql=mySql917101259.getString();
			
//			tstrsql = "select peitemname from LLUWPENoticeItem where 1=1 and peitemcode = 'other'"
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
	var sqlid917104424="DSHomeContSql917104424";
var mySql917104424=new SqlClass();
mySql917104424.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917104424.setSqlId(sqlid917104424);//指定使用的Sql的id
mySql917104424.addSubPara(tPrtSeq);//指定传入的参数
var sSql=mySql917104424.getString();
	
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
			var sqlid917104520="DSHomeContSql917104520";
var mySql917104520=new SqlClass();
mySql917104520.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917104520.setSqlId(sqlid917104520);//指定使用的Sql的id
mySql917104520.addSubPara(tContNo);//指定传入的参数
mySql917104520.addSubPara(tPrtSeq);//指定传入的参数
var strsql1=mySql917104520.getString();
			
//			var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag from LLUWPENotice where 1=1"	 
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
			var sqlid917104605="DSHomeContSql917104605";
var mySql917104605=new SqlClass();
mySql917104605.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917104605.setSqlId(sqlid917104605);//指定使用的Sql的id
mySql917104605.addSubPara(tPrtSeq);//指定传入的参数
var tstrsql=mySql917104605.getString();
			
//			var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			turnPage.queryModal(tstrsql, HealthGrid);	
			
			//查询体检结论信息
			var sqlid917104650="DSHomeContSql917104650";
var mySql917104650=new SqlClass();
mySql917104650.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917104650.setSqlId(sqlid917104650);//指定使用的Sql的id
mySql917104650.addSubPara(tPrtSeq);//指定传入的参数
var Sql=mySql917104650.getString();
			
//	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//	          // + " and ContNo = '"+ tContNo + "'"
//					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//查询其他体检信息
			var sqlid917104735="DSHomeContSql917104735";
var mySql917104735=new SqlClass();
mySql917104735.setResourceName("uw.LLManuHealthQSql");//指定使用的properties文件名
mySql917104735.setSqlId(sqlid917104735);//指定使用的Sql的id
mySql917104735.addSubPara(tContNo);//指定传入的参数
mySql917104735.addSubPara(tPrtSeq);//指定传入的参数
var strSQL=mySql917104735.getString();
						 
//			var strSQL = "select * from LLUWPENotice where 1=1"	
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
