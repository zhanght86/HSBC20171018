
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //保单类型操作位置 1.个人
var str_sql = "",sql_id = "", my_sql = "";

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;

  if(fm.SubMissionID.value == "")
  {
  	alert("已录入生调通知书,但未打印,不容许录入新的生调通知书!");
  	//easyQueryClick();
  	parent.close();
  	return;
  	}
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

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="操作成功";  	
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
 	else {
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



 
  //查询合同信息及投保人信息

 function returnParent()
 {
 	var tContNo = fm.ContNo.value;
 	var tPrtSeq = fm.PrtSeq.value;
 
 
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();
	
	
		try{
//			sql = "select * from LCCont where ContNo= '"+tContNo+"'"
			sql_id = "RReportDealEachSql1";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RReportDealEachSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(tContNo);//指定传入的参数
			str_sql = my_sql.getString();
			arrReturn1 =easyExecSql(str_sql);
			
			if (arrReturn1 == null) {
			  alert("未查到合同信息");
			} else {
			   displayCont(arrReturn1[0]);
			}
//			select * from LCRReport where ContNo = '" + tContNo + "' and PrtSeq = '"+tPrtSeq+"'
			sql_id = "RReportDealEachSql2";
			my_sql = new SqlClass();
			my_sql.setResourceName("uw.RReportDealEachSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(tContNo);//指定传入的参数
			my_sql.addSubPara(tPrtSeq);//指定传入的参数
			str_sql = my_sql.getString();
			arrReturn2 =easyExecSql(str_sql);
			
			if (arrReturn1 == null) {
			  alert("未查到生调原因");
			} else {
			  displayAppnt(arrReturn2[0]);
			}
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录。");
			
		}
		
	
  }
 
  
  function displayCont(cArr)
  {     
 
  	try { document.all('ContNo1').value = cArr[1]; } catch(ex) { };
  	try { document.all('PrtNo').value = cArr[3]; } catch(ex) { };
  	try { document.all('ManageCom').value = cArr[9]; } catch(ex) { };
  	try { document.all('SaleChnl').value = cArr[16]; } catch(ex) { };
  	try { document.all('AgentCode').value = cArr[12]; } catch(ex) { };
  	try { document.all('AgentGroup').value = cArr[13]; } catch(ex) { };
  	try { document.all('AgentCom').value = cArr[11]; } catch(ex) { };
  		
   }
   function displayAppnt(cArr)
   {
   	
  	try { document.all('Contente').value = cArr[9]; } catch(ex) { };
  	try { document.all('CustomerNo1').value = cArr[6]; } catch(ex) { };
  	try { document.all('CustomerName').value = cArr[7]; } catch(ex) { };
  	try { document.all('RReportReason').value = cArr[22]; } catch(ex) { };
    showOneCodeName('rreportreason','RReportReason','RReportReasonname');
  	
   }
   
   function easyQueryClick()
{
	// 书写SQL语句
	var strsql = "";
	
	var tContNo = fm.ContNo.value;
	var tPrtSeq = fm.PrtSeq.value;

  if(tContNo != ""&& tPrtSeq != "")
  {
//	strsql = "select RReportItemCode,RReportItemName,Remark from LCRReportItem where 1=1"				
//				 + " and ContNo = '"+ tContNo + "'"
//				 + " and PrtSeq = '"+ tPrtSeq +"'";
	sql_id = "RReportDealEachSql3";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RReportDealEachSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tPrtSeq);//指定传入的参数
	strsql = my_sql.getString();		 
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = InvestigateGrid;    
          
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



