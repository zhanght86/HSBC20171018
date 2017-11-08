//程序名称：UWManuSpec.js
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var turnPage_OldRisk = new turnPageClass();
var turnPage_AddFee = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var operate = "";
var proposalno = "";
var serialno = "";
var poladdgridid="";
var sqlresourcename = "uw.BQChangeRiskPlanInputSql";

//提交，保存按钮对应操作
function submitForm(tflag)
{
	fm.action="./BQChangeRiskPlanChk.jsp";
	if(tflag=="1"){
	   	var tSpecType=fm.HealthSpecTemp.value;
	   	var tSpecCode=fm.SpecTemp.value;
	   	var tRemark=fm.Remark.value;
	   	if(trim(tSpecType)=="null"||trim(tSpecType)==null||trim(tSpecType)=="")
	   	{
	   		alert("疾病系统编码不能为空!");
	   		return false;
	   		}
	   	if(trim(tSpecCode)=="null"||trim(tSpecCode)==null||trim(tSpecCode)=="")
	   	{
	   		alert("特约内容编码不能为空!");
	   		return false;
	   		}
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("特约内容不能为空!");
	   		return false;
	   		}
	     var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("请选择下发标记!");
	   		return false;
	   	}
	    fm.operate.value = "INSERT"
	    //alert(operate);
	    //return;
	}
	else if(tflag=="2"){
		 	var tRemark=fm.Remark.value;
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("特约内容不能为空!");
	   		return false;
	   	}
	 	 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要修改的特约信息！");
		 	  	return;
		 	  } 
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能修改！");
		 	    return;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
     // alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);
			fm.operate.value = "UPDATE";
			fm.SpecCode.value=UWSpecGrid.getRowColData(tSelNo,9);
			fm.SpecType.value=UWSpecGrid.getRowColData(tSelNo,8);;
			//alert(operate);
			//return;
		 }
  else if(tflag=="3")
  	 {
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要删除的特约信息！");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能删除！");
		 	    return;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "DELETE";
  	  //alert(operate);
  	  //return;
     }
      else
  	 {  	    
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要修改下发标记的特约信息！");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能修改下发标记！");
		 	    return;	
		 	  }
		 	   var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("请选择下发标记!");
	   		return false;
	   	}
		 	  if (!confirm('确认修改?'))
			{
			return false;
			}
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "UPDATE";
  	  //alert(operate);
  	  //return;
     }
	   
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit()(); //提交
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
   // parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);
  	top.opener.queryRiskInfo();
  	
  //	parent.close();
  	
    //执行下一步操作
  }
  initUWSpecGrid();
  initPolAddGrid();
  //initCancleGiven(tContNo,tInsuredNo);
  QueryPolSpecGrid();
  queryRiskAddFee();
  initOldRislPlanGrid('');
  queryOldRiskPlan();
  //onRiskTabChange(1);
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //执行下一步操作
    top.window.close();
  }
 
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);  	
  //	parent.close();
  	
    //执行下一步操作
  }
  //initUWSpecGrid();
 // QueryPolSpecGrid(mContNo,mInsuredNo);
  //queryRiskAddFee(mContNo,mInsuredNo);
  //initOldRislPlanGrid('');
  //queryOldRiskPlan();
  //onRiskTabChange(1);
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}

function QueryPolSpecGrid()
{
	// 初始化表格
	// 书写SQL语句
	//alert("QueryPolSpecGrid"+tContNo);
	var strSQL = "";
	var i, j, m, n;	
	var tProposalContNo;
	//tongmeng 2008-10-08 add
	//启用合同特约表
				
               
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql4";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	strSQL=mySql1.getString();
	
  arrResult=easyExecSql(strSQL);
  
 
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWSpecGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}


function getSpecGridCho2()
{
    var tSelNo = UWSpecGrid.getSelNo()-1;
	//alert('tSelNo'+tSelNo);
	var proposalcontno = UWSpecGrid.getRowColData(tSelNo,5);
	var serialno = UWSpecGrid.getRowColData(tSelNo,2);//alert("serialno=="+serialno);
	//var tContent = fm.Remark.value;
	
    
    var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql7";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(proposalcontno);
	mySql1.addSubPara(serialno);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	mySql1.addSubPara("1");
	strSQL=mySql1.getString();
	
  var arrResult = easyExecSql(strSQL);
    if(arrResult!=null)
    {
			fm.SpecTemp.value=arrResult[0][1];
			fm.HealthSpecTemp.value=arrResult[0][0];
			fm.SpecTempname.value=arrResult[0][3];
			fm.HealthSpecTempName.value=arrResult[0][2];
			fm.NeedPrintFlag.value=arrResult[0][4];
			fm.IFNeedFlagName.value=arrResult[0][5];
          	fm.Remark.value = arrResult[0][6]; 
    }  
	
}

function getClickSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   //alert(tManageCom.length);
  // alert(sql_temp);
   showCodeList('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function getClickUpSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   showCodeListKey('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function queryInsuredInfo()
{
	var tContNo = fm.ContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tInsuredNo = fm.InsuredNo.value;
   var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
	
  arrResult=easyExecSql(strSQL);
  
    if(arrResult!=null)	
    {
        DisplayInsured(arrResult);
    }
 var strSQL2 = "";
	var sqlid1="BQChangeRiskPlanInputSql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tInsuredNo);
	strSQL2=mySql1.getString();
  arrResult2 = easyExecSql(strSQL2);
    if(arrResult2!=null)
    {
        fm.UWIdea.value=arrResult2[0][0];
    }
}

function DisplayInsured(arrResult)
{
    try{document.all('InsuredName').value= arrResult[0][0]; }catch(ex){};
    try{document.all('InsuredSex').value= arrResult[0][1]; }catch(ex){};
    try{document.all('RelationToAppnt').value= arrResult[0][4]; }catch(ex){};
    try{document.all('RelationToMainInsured').value= arrResult[0][5]; }catch(ex){};
    try{document.all('InsuredOccupationCode').value= arrResult[0][6]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][7]; }catch(ex){};
    try{document.all('InsuredPluralityType').value= arrResult[0][8]; }catch(ex){};
    try{document.all('InsuredAge').value= calPolCustomerAge(arrResult[0][3],arrResult[0][2]); }catch(ex){};
    console.log(document.all('InsuredOccupationCode'));
    quickGetName('occupationCode',document.all('InsuredOccupationCode'),document.all('InsuredOccupationCodeName'));
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
    quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
}

function calPolCustomerAge(BirthDate,PolAppntDate)
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


var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObj(strCode, showObjc, showObjn);
}

function queryOldRiskPlan()
{
	
	  
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql3";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	strSQL=mySql1.getString();
	turnPage_OldRisk.queryModal(strSQL, OldRislPlanGrid); 
}

//tongmeng 2008-10-09 add
//修改险种核保结论
function makeRiskUWState()
{
	//校验是否有险种被选择
	var tSelNo = OldRislPlanGrid.getSelNo();
  if(tSelNo<=0)
 	{
 			alert("请选择险种保单！");
 			return;
 	}
 //	alert('1'); 		
 		var olduwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,9);
 		var uwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,10);
 		var polno = OldRislPlanGrid.getRowColData(tSelNo - 1,11);
 		var mainpolno = OldRislPlanGrid.getRowColData(tSelNo - 1,12);
 		fm.PolNo.value=polno;
 		if(uwstate==null || uwstate =="")
 		{
 			alert("请先选择核保决定！");
 			return;
 		}
 		else if(uwstate == "c")
 		{
 			if(olduwstate == null || olduwstate == "")
 			{			     
 				alert("所选险种没有原核保结论，不能下维持结论");
 				return false;
 			}
 			else
 			
 			uwstate = olduwstate;//维持原条件承保
 		}
 		fm.uwstate.value = uwstate;
 		document.all('flag').value = "risk";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./BQInsuredUWInfoChk.jsp";
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
	//alert('3');
	document.getElementById("fm").submit();
}

//ln 2008-12-1 add
//承保计划变更结论及加费原因录入
function makeRiskUWIdea()
{
 //	alert('1');
 		document.all('flag').value = "Insured";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./BQInsuredUWInfoChk.jsp";
	 var i = 0;
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    //alert('3');
	document.getElementById("fm").submit();
}


//ln 2009-1-7 add
//取消提前给付特约
function cancleGiven1()
{
	fm.action="./BQCancleGivenChk.jsp";
	var i = 0;
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();//alert('3');
	document.getElementById("fm").submit();
}

//ln 2009-1-7 add
//取消提前给付特约
function initCancleGiven()
{
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
	
    var str  = easyExecSql(strSQL);;
    if(str != null)
    	fm.cancleGiven.disabled = false;
    else
        fm.cancleGiven.disabled = true;
}


//以下为加费程序处理调用函数
// 查询按钮
function initlist()
{//alert(636);
	// 书写SQL语句
	k++;
	
   
  var strSQL = "";
 
	var sqlid1="BQChangeRiskPlanInputSql5";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	strSQL=mySql1.getString();
	
	str  = easyQueryVer3(strSQL, 1, 0, 1);
	
	return str;
;

}

function afterCodeSelect( cCodeName, Field ) {
		//alert("111:"+cCodeName);
		if( cCodeName == "addfeetype" ) {
	
			if(Field.value=='02')
			{
				
			}
		}
				if(cCodeName == "PlanPay")
		{//alert(cCodeName);
				if(Field.value=='02')
				{
						//获得当前条数的险种编码
						var tSelNo = PolAddGrid.getSelNo()-1;
						var t = PolAddGrid.lastFocusRowNo;
						//alert('t:'+t+":"+tSelNo);
						
						//alert('tSelNo'+tSelNo);
						if(tSelNo<0)
						{
							/*
							alert('请先选择一条记录!');
							return false;
							*/
							tSelNo = t;
						}
						//111801,111802,121705,121704
						var riskcode = PolAddGrid.getRowColData(tSelNo,1);
						if(riskcode==null||riskcode=='')
						{
							alert('请先选择险种!');
							return false;
						}
						else if(riskcode=='111801'||riskcode=='111802'
							||riskcode=='121705'||riskcode=='121704'
							||riskcode=='121701'||riskcode=='121702'
							
							//test
							//||riskcode=='112213'
							)
							{
								 PolAddGrid.setRowColData(tSelNo,5,'02',PolAddGrid);
					//			 var tSQL = "select medrate from ldoccupation where occupationcode='"+document.all('InsuredOccupationCode').value+"' ";
								    
								    var InsuredOccupationCode9 = document.all('InsuredOccupationCode').value;
								    var sqlid9="BQChangeRiskPlanInputSql9";
									var mySql9=new SqlClass();
									mySql9.setResourceName(sqlresourcename); //指定使用的properties文件名
									mySql9.setSqlId(sqlid9);//指定使用的Sql的id
									mySql9.addSubPara(InsuredOccupationCode9);//传入参数
									var tSQL = mySql9.getString();
									
								 
								 var tStr1  = easyExecSql(tSQL);
								 //alert(tStr1);
								 if(tStr1!=null)
								 {
								 	 PolAddGrid.setRowColData(tSelNo,6,tStr1[0][0],PolAddGrid);
								 }
								 return ;
							}
				}
		}


		
		if( cCodeName == "addfeedatetype" ) {
			//alert(cCodeName);
			var tFieldValue=Field.value;
			//alert(tFieldValue);
			//alert(eval(poladdgridid));
			//alert(poladdgridid);
//			alert(eval(poladdgridid).all('PolAddGrid9').value);
			var temppoladdgridid =  eval(poladdgridid);
			//alert(temppoladdgridid.all('PolAddGrid9').value);
//			var span = PolAddGrid.getSelNo()-1;
//			var span = PolAddGrid.getSelNo()-1;
//			spanObj = span;
//			if(spanObj<0){
//			   alert("请选择需要进行操作的加费记录!");
//			   return false;
//		    }
			//alert(temppoladdgridid.all('PolAddGrid9').value);
		    if(temppoladdgridid.all('PolAddGrid9').value==null||temppoladdgridid.all('PolAddGrid9').value==""){
		    	alert("请先选择要加费的险种信息！");
		    	temppoladdgridid.all('PolAddGrid15').value="";
		    	return false;
		    }
			//加费开始时间类型
			var tAddForm = temppoladdgridid.all('PolAddGrid15').value;
			//保单号码
			var tPolNo = temppoladdgridid.all('PolAddGrid12').value;//alert("tPolNo=="+tPolNo);
			//加费责任编码
			var tDutyCode = temppoladdgridid.all('PolAddGrid3').value;
			//缴费计划编码
			var tPayPlanCode = temppoladdgridid.all('PolAddGrid14').value;
			
//			var tSql = "select count(*) from lpprem a where a.payplancode like '000000%' "
//    			+" and contno='"+fm.ContNo.value+"' and edorno <> '"+fm.EdorNo.value+"' "
//    			+" and exists(select 1 from lpedoritem b where b.edorno = a.edorno and b.edorstate = '0')";
//		  	var arrResult =  easyExecSql(tSql);//prompt("",tSql);
//		  	if(arrResult[0][0]!="0"){
//		  		if(tAddForm == "0"||tAddForm == "1"){
//		  			alert("曾做过保全加费，加费开始时间类型只能为下期！");
//		  			tFieldValue="2";
////		  			PolAddGrid.setRowColData(spanObj,15,'2');
//		  			temppoladdgridid.all('PolAddGrid15').value='2';
//		  			//return false;
//		  		}
//		  	}//alert(676);
		  	//alert(tPayPlanCode);
			var tSql ;
		  	if(tPayPlanCode != null && tPayPlanCode!='')
		  	{
//		  		tSql = "select count(*) from lcprem where polno='"+tPolNo+"' "
//		  	     +" and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//		  	     //prompt('',tSql);
		  		
				    var sqlid10="BQChangeRiskPlanInputSql10";
					var mySql10=new SqlClass();
					mySql10.setResourceName(sqlresourcename); //指定使用的properties文件名
					mySql10.setSqlId(sqlid10);//指定使用的Sql的id
					mySql10.addSubPara(tPolNo);//传入参数
					mySql10.addSubPara(tDutyCode);//传入参数
					mySql10.addSubPara(tPayPlanCode);//传入参数
					tSql = mySql10.getString();
					
		  		
			  	var arrResult1 =  easyExecSql(tSql);
			  	if(arrResult1[0][0]!="0"){
			  		if(tAddForm != "3" && tAddForm != "4"){
			  			alert("非本次保全生成的加费，加费开始时间类型只能为终止或维持原条件！");
			  			temppoladdgridid.all('PolAddGrid15').value='';
			  			return false;
			  		}
			  	}
			  	else
			  	{
			  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
			  			alert("为本次保全加费，加费开始时间类型只能为追溯、当期加费或下期加费！");
			  			temppoladdgridid.all('PolAddGrid15').value='';
			  			return false;
			  		}
			  	}
		  	}		  	
		  	else
		  	{
		  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
		  			alert("为本次保全加费，加费开始时间类型只能为追溯、当期加费或下期加费！");
		  			temppoladdgridid.all('PolAddGrid15').value='';
		  			return false;
		  		}
		  	}
		    
	//	    var forPayIntv = "select payintv from lppol where edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"' and polno='"+tPolNo+"'";
		    
		    var sqlid11="BQChangeRiskPlanInputSql11";
			var mySql11=new SqlClass();
			mySql11.setResourceName(sqlresourcename); //指定使用的properties文件名
			mySql11.setSqlId(sqlid11);//指定使用的Sql的id
			mySql11.addSubPara(fm.EdorNo.value);//传入参数
			mySql11.addSubPara(fm.ContNo.value);//传入参数
			mySql11.addSubPara(tPolNo);//传入参数
			var forPayIntv = mySql11.getString();
		    
		    var arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("查询交费间隔失败！");
		    	return false;
		    }
		    var tPayIntv = arrPayIntv[0][0];
//		    forPayIntv = "select paystartdate,paytodate,payenddate from lpprem where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' and payplancode not like '000000%'";
		   
		    var sqlid12="BQChangeRiskPlanInputSql12";
			var mySql12=new SqlClass();
			mySql12.setResourceName(sqlresourcename); //指定使用的properties文件名
			mySql12.setSqlId(sqlid12);//指定使用的Sql的id
			mySql12.addSubPara(fm.EdorNo.value);//传入参数
			mySql12.addSubPara(tPolNo);//传入参数
			forPayIntv = mySql12.getString();
		    
		    arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("查询加费起止日期失败！");
		    	return false;
		    }
		    //首期交费日期
		    var tBeginAdd = arrPayIntv[0][0];
		    var tToAdd = arrPayIntv[0][1];
		    var tEndAdd = arrPayIntv[0][2]//交费止期;
		    //加费起期
		  /*	var strSQL1 = "select "
	       			+ " firstpaydate,paytodate,payenddate "
	       			+ " from lpprem a "
         			+ " where a.edorno='"+fm.EdorNo.value+"' and a.contno='"+fm.ContNo.value+"' and a.polno='"+tPolNo+"' "
         			+ " and dutycode= and payplancode=";
		    //prompt("",strSQL);
		    var arrRes =  easyExecSql(strSQL1);
		    if(arrRes==null||arrRes==""){
		    	alert("查询加费起期失败！");
		    	return false;
		    }
		    //首期交费日期
		    var tBeginAdd=arrRes[0][0];*/
		    //得到当前日期
	  		var CurrentDate = getCurrentDate("-");
	  		
	  		//交费间隔为趸交加费开始时间类型只能为追溯
	  		if(tPayIntv=="0"){
	  			if(tAddForm == "2"||tAddForm == "1"){
	  				alert("该保单交费间隔为趸交，加费开始时间类型只能为追溯！");
//	  				PolAddGrid.setRowColData(spanObj,15,'0');
					tFieldValue = "0";

//	  				PolAddGrid.setRowColData(spanObj,9,tBeginAdd);	  				
	  			}
	  			temppoladdgridid.all('PolAddGrid15').value=tFieldValue;
	  		}
	  		if(tFieldValue=='0')	
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tEndAdd;
	  		}
	  			
	  		else if(tFieldValue=='1' || tFieldValue=='2')//当期、下期	
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tToAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tEndAdd;
	  		}
	  			
	  		else if(tFieldValue=='3')//终止
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tToAdd;
	  		}
	  		else if(tFieldValue=='4')//维持原条件
	  		{
	  			temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	  			temppoladdgridid.all('PolAddGrid10').value=tEndAdd;
	  		}	  						
	  			    
		}
}

//校验 加费类型
function CheckAddType(tSelNo)
{
	var tRiskCode = PolAddGrid.getRowColData(tSelNo,1);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tAddFeeType = PolAddGrid.getRowColData(tSelNo,4);
	var tAddFeeMethod = PolAddGrid.getRowColData(tSelNo,5);
	var tAddFeePoint = PolAddGrid.getRowColData(tSelNo,6);
	/*var tSQL = " select 1 from LMDutyPayAddFee where "
           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
           + " and  addfeeobject='"+tAddFeeMethod+"'";
    //prompt('',tSQL);
   var arrRes =  easyQueryVer3(tSQL, 1, 0, 1);
   //alert("arrRes:"+arrRes);
   if(arrRes==null||!arrRes)
   {
   	  alert("此险种无需进行加费，或者该险种没有此加费类型!");
   	  PolAddGrid.getRowColData(tSelNo,7,'0');
   	  return false;
   }*/

  if((tAddFeeMethod!=null && tAddFeeMethod=='01')&&(tAddFeePoint==null||tAddFeePoint<='0'))
  {
  	alert("按EM值加费,请输入加费评点!");
  	return false;
  }
  
  return true;
}

//校验 加费开始时间类型
function CheckAddDateType(tSelNo,tDutyCode,tPayPlanCode,tPolNo,tAddForm)
{
	//alert("tSelNo:"+tSelNo+"tAddForm:"+tAddForm);
	var tFieldValue = tAddForm ;
	var tThisFlag = true;//是本次保全加费
	var tSql = "";
	/*tSql = "select count(*) from lpprem a where a.payplancode like '000000%' "
    			+" and contno='"+fm.ContNo.value+"' and edorno <> '"+fm.EdorNo.value+"' "
    			+" and exists(select 1 from lpedoritem b where b.edorno = a.edorno and b.edorstate = '0')";
		  	var arrResult =  easyExecSql(tSql);//prompt("",tSql);
		  	if(arrResult[0][0]!="0"){
		  		if(tAddForm == "0"||tAddForm == "1"){
		  			alert("曾做过保全加费，加费开始时间类型只能为下期！");
		  			tFieldValue="2";
//		  			PolAddGrid.setRowColData(spanObj,15,'2');
		  			PolAddGrid.getRowColData(tSelNo,15,'2');
		  			//return false;
		  		}
		  	}*/
		  	if(tPayPlanCode != null && tPayPlanCode!='')
		  	{
//		  		tSql = "select count(*) from lcprem where polno='"+tPolNo+"' "
//		  	     +" and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//		  	     //prompt('',tSql);
		  		
		  		var sqlid13="BQChangeRiskPlanInputSql13";
				var mySql13=new SqlClass();
				mySql13.setResourceName(sqlresourcename); //指定使用的properties文件名
				mySql13.setSqlId(sqlid13);//指定使用的Sql的id
				mySql13.addSubPara(tPolNo);//传入参数
				mySql13.addSubPara(tDutyCode);//传入参数
				mySql13.addSubPara(tPayPlanCode);//传入参数
				tSql = mySql13.getString();
		  		
			  	var arrResult1 =  easyExecSql(tSql);
			  	if(arrResult1[0][0]!="0"){
			  		tThisFlag = false;
			  		if(tAddForm != "3" && tAddForm != "4"){
			  			alert("非本次保全生成的加费，加费开始时间类型只能为终止或维持原条件！");
			  			PolAddGrid.getRowColData(tSelNo,15,'');
			  			return false;
			  		}
			  	}
			  	else
			  	{
			  		tThisFlag = true;
			  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
			  			alert("为本次保全加费，加费开始时间类型只能为追溯、当期加费或下期加费！");
			  			PolAddGrid.getRowColData(tSelNo,15,'');
			  			return false;
			  		}
			  	}
		  	}		  	
		  	else
		  	{
		  		tThisFlag = true;
		  		if(tAddForm != "0" && tAddForm != "1" && tAddForm != "2"){
		  			alert("为本次保全加费，加费开始时间类型只能为追溯、当期加费或下期加费！");
		  			PolAddGrid.getRowColData(tSelNo,15,'');
		  			return false;
		  		}
		  	}
		    
//		    var forPayIntv = "select payintv from lppol where edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"' and polno='"+tPolNo+"'";
		   

	  		var sqlid14="BQChangeRiskPlanInputSql14";
			var mySql14=new SqlClass();
			mySql14.setResourceName(sqlresourcename); //指定使用的properties文件名
			mySql14.setSqlId(sqlid14);//指定使用的Sql的id
			mySql14.addSubPara(fm.EdorNo.value);//传入参数
			mySql14.addSubPara(fm.ContNo.value);//传入参数
			mySql14.addSubPara(tPolNo);//传入参数
			var forPayIntv = mySql14.getString();
		    
		    var arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("查询交费间隔失败！");
		    	return false;
		    }
		    var tPayIntv = arrPayIntv[0][0];
//		    forPayIntv = "select paystartdate,paytodate,payenddate from lpprem where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' and payplancode not like '000000%'";
		    
		    var sqlid15="BQChangeRiskPlanInputSql15";
			var mySql15=new SqlClass();
			mySql15.setResourceName(sqlresourcename); //指定使用的properties文件名
			mySql15.setSqlId(sqlid15);//指定使用的Sql的id
			mySql15.addSubPara(fm.EdorNo.value);//传入参数
			mySql15.addSubPara(tPolNo);//传入参数
			forPayIntv = mySql15.getString();
		    
		    arrPayIntv =  easyExecSql(forPayIntv);
		    if(!arrPayIntv){
		    	alert("查询加费起止日期失败！");
		    	return false;
		    }
		    //首期交费日期
		    var tBeginAdd = arrPayIntv[0][0];
		    var tToAdd = arrPayIntv[0][1];
		    var tEndAdd = arrPayIntv[0][2]//交费止期;
		   
		    //得到当前日期
	  		var CurrentDate = getCurrentDate("-");
	  		
	  		//交费间隔为趸交加费开始时间类型只能为追溯
	  		if(tPayIntv=="0" && tThisFlag == true){
	  			if(tAddForm == "2"||tAddForm == "1"){
	  				alert("该保单交费间隔为趸交，加费开始时间类型只能为追溯！");
//	  				PolAddGrid.setRowColData(spanObj,15,'0');
					tFieldValue = "0";
	  				PolAddGrid.getRowColData(tSelNo,15,'0');
//	  				PolAddGrid.setRowColData(spanObj,9,tBeginAdd);	  				
	  			}
	  		}
	  		if(tFieldValue=='0')	
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tBeginAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tEndAdd);
	  		}
	  			
	  		else if(tFieldValue=='1' || tFieldValue=='2')//当期、下期	
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tToAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tEndAdd);
	  		}
	  			
	  		else if(tFieldValue=='3')//终止
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tBeginAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tToAdd);
	  			
	  		}
	  		else if(tFieldValue=='4')//维持原条件
	  		{
	  			PolAddGrid.getRowColData(tSelNo,9,tBeginAdd);
	  			PolAddGrid.getRowColData(tSelNo,10,tEndAdd);
	  			
	  		}
	  	return true;
}

//加费保存
function addFeeSave()
{
    var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("请选择需要保存的加费记录!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,12);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanType = PolAddGrid.getRowColData(tSelNo,4);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,14);
	var tAddForm = PolAddGrid.getRowColData(tSelNo,15);	
	
	//校验 加费开始时间类型
	if(!CheckAddDateType(tSelNo,tDutyCode,tPayPlanCode,tPolNo,tAddForm))
		return false;
	
	//校验加费方式是否存在
	if((tAddForm=='0' ||tAddForm=='1'||tAddForm=='2') && !CheckAddType(tSelNo))
		return false;
	
	if(tPayPlanCode == null || tPayPlanCode == '')
	{}
	else
	{		
//		var strSQL =" select dutycode,payplantype,AddFeeDirect,suppriskscore,prem from lpprem " 
//	           + " where polno='"+tPolNo+"' and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' "
//	           +" and edorno='"+fm.EdorNo.value+"'";
//	    //prompt('',strSQL); 
		
		var sqlid16="BQChangeRiskPlanInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(tPolNo);//传入参数
		mySql16.addSubPara(tDutyCode);//传入参数
		mySql16.addSubPara(tPayPlanCode);//传入参数
		mySql16.addSubPara(fm.EdorNo.value);//传入参数
		var strSQL = mySql16.getString();
		
	    var arrResult=easyExecSql(strSQL);
	    if(arrResult!=null)
	    {
	    	//校验如果是承保做的加费，则不能修改别的信息，只能修改加费开始时间类型
	        //还原为承保信息
	        PolAddGrid.setRowColData(tSelNo,3,arrResult[0][0]);
	        PolAddGrid.setRowColData(tSelNo,4,arrResult[0][1]);
			PolAddGrid.setRowColData(tSelNo,5,arrResult[0][2]);
			PolAddGrid.setRowColData(tSelNo,6,arrResult[0][3]);
			PolAddGrid.setRowColData(tSelNo,7,arrResult[0][4]);
	    }
	}  
	
	if(tAddForm != null && tAddForm!='')
	{
		if(tAddForm =='3' || tAddForm =='4')
		{
			//交费时间类型不变直接提示成功
//			var strSQL =" select addform from lpprem " 
//		           + " where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//		    //prompt('',strSQL); 
//			
			var sqlid17="BQChangeRiskPlanInputSql17";
			var mySql17=new SqlClass();
			mySql17.setResourceName(sqlresourcename); //指定使用的properties文件名
			mySql17.setSqlId(sqlid17);//指定使用的Sql的id
			mySql17.addSubPara(fm.EdorNo.value);//传入参数
			mySql17.addSubPara(tPolNo);//传入参数
			mySql17.addSubPara(tDutyCode);//传入参数
			mySql17.addSubPara(tPayPlanCode);//传入参数
			var strSQL = mySql17.getString();
			
			var arrResult=easyExecSql(strSQL);
			if(arrResult!=null && arrResult[0][0]==tAddForm)
			{
				  var FlagStr = "Succ";
				  var Content = "人工核保加费成功!";
				  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
				  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
					var name='提示';   //网页名称，可为空; 
					var iWidth=550;      //弹出窗口的宽度; 
					var iHeight=250;     //弹出窗口的高度; 
					var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
					var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
					showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

					showInfo.focus();				  
					afterSubmit(FlagStr,Content);
				  return true;
			}
		}		
		
		//校验如果有本次保全相同类型加费 如果有一个是终止则可以进行加费
//		var strSQL1 =" select addform from lpprem " 
//	           + " where edorno='"+fm.EdorNo.value+"' and polno='"+tPolNo+"' "
//	           + " and dutycode='"+tDutyCode+"' and payplantype='"+tPayPlanType+"' and PayPlanCode like '000000%' "
//	           + " and (addform is null or addform='' or addform<>'3')";//排除终止的加费
//	    //prompt('',strSQL1); 
		
		var sqlid18="BQChangeRiskPlanInputSql18";                              
		var mySql18=new SqlClass();                                            
		mySql18.setResourceName(sqlresourcename); //指定使用的properties文件名         
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id                                
		mySql18.addSubPara(fm.EdorNo.value);//传入参数                             
		mySql18.addSubPara(tPolNo);//传入参数                                      
		mySql18.addSubPara(tDutyCode);//传入参数                                   
		mySql18.addSubPara(tPayPlanCode);//传入参数                                
		var strSQL1 = mySql18.getString();                                      
		
		var arrResult1=easyExecSql(strSQL1);
		if(arrResult1!=null)
		{
		    if(tPayPlanCode == null || tPayPlanCode == '') //lpprem表有非终止记录，不允许新增记录
		    {
		       alert("请先终止或删除相同加费类别的加费记录!");
			 	return false;
		    }
		    else //在现有记录基础上做修改
		    {
			    if( (tAddForm != arrResult1[0][0]) || (tAddForm=='3') 
					    ||(tAddForm=='4' && arrResult1[0][0] ==''))
				{}
				else
				{
					alert("请先终止或删除相同加费类别的加费记录!");
	//				alert(arrResult1[0][0]);
	//				PolAddGrid.setRowColData(tSelNo,15,arrResult1[0][0]);
			 		return false;
				}
			}
		}
	}	
    
	fm.action="./BQManuChangeRiskAddChk.jsp";
	var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit()();
}

//初始化查询加费数据
function queryRiskAddFee()
{
	var tSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql6";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);
	mySql1.addSubPara(fm.InsuredNo.value);//指定传入的参数
	mySql1.addSubPara(fm.EdorNo.value);
	tSQL=mySql1.getString();
	 turnPage_AddFee.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);          
	//判断是否查询成功
  if (turnPage_AddFee.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage_AddFee.arrDataCacheSet = decodeEasyQueryResult(turnPage_AddFee.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage_AddFee.pageDisplayGrid = PolAddGrid;    
          
  //保存SQL语句
  turnPage_AddFee.strQuerySql     = tSQL; 
  
  //设置查询起始位置
  turnPage_AddFee.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage_AddFee.getData(turnPage_AddFee.arrDataCacheSet, turnPage_AddFee.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage_AddFee.pageDisplayGrid);
  }
}

//自动计算加费信息
function AutoCalAddFee(span)
{
	spanObj = span;

  
  //alert(document.all( spanObj ).all( 'PolAddGrid4' ).value);
  //首先判断有没有加费算法
  //险种编码
  var tRiskCode = document.all( spanObj ).all( 'PolAddGrid1' ).value;
  // alert(tRiskCode);
  //加费类别
  var tAddFeeType = document.all( spanObj ).all( 'PolAddGrid4' ).value;
  //加费方式
  var tAddFeeMethod = document.all( spanObj ).all( 'PolAddGrid5' ).value;
  //责任编码
  var tDutyCode = document.all( spanObj ).all( 'PolAddGrid3' ).value;
  
  var tEdorType = document.all( 'EdorType' ).value;
  
  var tInsuredNo = document.all( 'InsuredNo' ).value;
  
  if(tRiskCode==null||tRiskCode=='')
  {
  	alert("请选择险种!");
  	return;
  }
  if(tAddFeeType==null||tAddFeeType=='')
  {
  	alert("请选择加费类别!");
  	return;
  }
  if(tAddFeeMethod==null||tAddFeeMethod=='')
  {
  	alert("请选择加费方式!");
  	return;
  }
  
 //  var tSelNo = PolAddGrid.lastFocusRowNo;
 //  alert(tSelNo);
 //alert(mInsuredNo);

//  var tSQL = " select 1 from LMDutyPayAddFee where "
//           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
//           + " and  addfeeobject='"+tAddFeeMethod+"'";
  
    var sqlid19="BQChangeRiskPlanInputSql19";                              
	var mySql19=new SqlClass();                                            
	mySql19.setResourceName(sqlresourcename); //指定使用的properties文件名         
	mySql19.setSqlId(sqlid19);//指定使用的Sql的id                                
	mySql19.addSubPara(tRiskCode);//传入参数                             
	mySql19.addSubPara(tDutyCode);//传入参数                                      
	mySql19.addSubPara(tAddFeeType);//传入参数                                   
	mySql19.addSubPara(tAddFeeMethod);//传入参数                                
	var tSQL = mySql19.getString();     
  
   var arrRes =  easyQueryVer3(tSQL, 1, 0, 1);
   //alert("arrRes:"+arrRes);
   if(arrRes==null||!arrRes)
   {
   	  alert("此险种无需进行加费，或者该险种没有此加费类型!");
   	  document.all( spanObj ).all( 'PolAddGrid7' ).value='0';
   	  return;
   }
   //准备数据
  var tPolNo =  document.all( spanObj ).all( 'PolAddGrid12' ).value;
  var tAddFeePoint = document.all( spanObj ).all( 'PolAddGrid6' ).value;
  if(tAddFeeMethod=='01'&&(tAddFeePoint==null||tAddFeePoint<='0'))
  {
  	alert("按EM值加费,请输入加费评点!");
  	return;
  }
 // if(tAddFeeMethod!='01' && !(tAddFeePoint==null||tAddFeePoint==''))
 // {
 // 	 alert("其他加费方式,请不要录入加费评点!");
 // 	 return;
 // }
    var i = 0;
    var showStr="正在计算加费数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   //提交数据库
  fm.action= "./BQCalHealthAddFeeChk.jsp?AddFeePolNo="+tPolNo+"&AddFeeRiskCode="+tRiskCode+"&AddFeeDutyCode="+tDutyCode+"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+tAddFeeMethod+"&AddFeeInsuredNo="+tInsuredNo+"&AddFeePoint="+tAddFeePoint+"&EdorType="+tEdorType;
  document.getElementById("fm").submit(); //提交
}


//加费删除
//created by guanwei
function deleteData()
{ 
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("请选择需要删除的加费记录!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,12);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,14);
	if(tPayPlanCode==null||tPayPlanCode=='')
	{
		 alert("该记录并没有保存,请刷新页面重新查询!");
		 return false;
	}
	
	//校验如果是承保做的加费，则不能删除
//	var strSQL =" select 1 from lcprem " 
//           + " where polno='"+tPolNo+"' and dutycode='"+tDutyCode+"' and payplancode='"+tPayPlanCode+"' ";
//    //prompt('',strSQL);

	var sqlid20="BQChangeRiskPlanInputSql20";                              
	var mySql20=new SqlClass();                                            
	mySql20.setResourceName(sqlresourcename); //指定使用的properties文件名         
	mySql20.setSqlId(sqlid20);//指定使用的Sql的id                                
	mySql20.addSubPara(tPolNo);//传入参数                             
	mySql20.addSubPara(tDutyCode);//传入参数                                      
	mySql20.addSubPara(tPayPlanCode);//传入参数                                                   
	var strSQL = mySql20.getString();     
	
    var arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        alert("非本次保全生成的加费，不能删除！");
        return false;
    }
	//initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./BQManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
    document.getElementById("fm").submit();
  
  //var cPolNo= fm.PolNo2.value ;   
  //alert(cPolNo);
  
}


//计算加费起期
function addFormMethodNew(span){
	
	showCodeList('addfeedatetype',[document.all(span).all('PolAddGrid15')],null,null,null,null,null,null);
	
	//showCodeListKey('addfeedatetype',[document.all(span).all('PolAddGrid15')],null,null,null,null,null,null);
	//alert("test:"+document.all(span).all('PolAddGrid15').value);
	//alert(span);
	poladdgridid = span;
	
}
//计算年交加费起期
function payYear(tBeginAdd,tFieldValue,CurrentDate){
	//2008-12-02
	//var tempDate=tBeginAdd.substr(0,4);
	/*
	 *取首期交费日期的月日与当前日期的月日相比，如果当前日期小于首期交费，当期为当前日期年减一，下期算法也一样
	*/
	//首期交费日期的月、日
	//alert("wktest1"+temppoladdgrididtest);
	//alert("wktest2"+eval(poladdgridid));
	var temppoladdgridid =  eval(poladdgridid);
	//alert("wktest2"+eval(poladdgridid).all('PolAddGrid15').value);
	var tempDate=tBeginAdd.substr(4,tBeginAdd.length);
	//alert("tempDate2=="+tempDate2);return false;
	//当前日期的月、日
	var tempDate2=CurrentDate.substr(4,tBeginAdd.length);
	//当前日期的年份
	var tempDate3=CurrentDate.substr(0,4);
		  	
	
	var strSQL = "";
	var sqlid1="BQChangeRiskPlanInputSql7";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdorNo);
	mySql1.addSubPara(tContNo);//指定传入的参数
	mySql1.addSubPara(tInsuredNo);
	strSQL=mySql1.getString();
	var arrResult =  easyExecSql(strSQL);
	//prompt("",strSql);
	//追溯
	if(tFieldValue=="0"){
		//alert(678);
//		PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		temppoladdgridid.all('PolAddGrid9').value=tBeginAdd;
	}
	//当期
	if(tFieldValue=="1"){
		//tempDate=tempDate+1;
		//var tempDate3=tempDate+1;
		//tBeginAdd=tempDate3+tempDate2;
		//alert("tBeginAdd=="+tBeginAdd);
		//PolAddGrid.setRowColData(spanObj,9,"2009-12-30");
		//alert("getCurrentDate=="+getCurrentDate("-"));
		//tBeginAdd=tempDate4+tempDate2;
		//PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		 		
		if(arrResult==null||arrResult==""){
			//alert(729);
//			PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
			temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate
		}else{
			//alert("321324324");
			tempDate3 = parseInt(tempDate3)-1;
//			PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
			temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate
		}
	}
	//下期
	if(tFieldValue=="2"){
		if(arrResult==null||arrResult==""){
		//alert(739);
		tempDate3 = parseInt(tempDate3)+1;
//		PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
		temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate;
		}else{
			//alert("321==");
//			PolAddGrid.setRowColData(spanObj,9,tempDate3+tempDate);
			temppoladdgridid.all('PolAddGrid9').value=tempDate3+tempDate
		}
	}
}
//计算月交加费起期
function payMonth(tBeginAdd,tFieldValue,CurrentDate){
	/*2008-12-31
	 *取首期交费日期的月日与当前日期的月日相比，如果当前日期小于首期交费，当期为当前日期年减一，下期算法也一样
	*/
	//首期交费日期的日
	//var tempDate=tBeginAdd.substr(8,tBeginAdd.length);
	//alert("tempDate2=="+tempDate2);return false;
	//拆分出当前日期的日
	var tempStr=CurrentDate.split("-");
	var tempDate = tempStr[2];
	var tempMonth = tempStr[1];
	var tempYear = tempStr[0];
	
	//var BeginDate=tBeginAdd.substr(8,tBeginAdd.length);
	//alert("tempDate2=="+tempDate2);return false;
	//拆分出当前日期的日
	var BeginStr=CurrentDate.split("-");
	var BeginDate = tempStr[2];
	
	var inttempDate = parseInt(tempDate);
	var intBeginDate = parseInt(BeginDate);
	//当前日期的年份
	//var tempDate3=CurrentDate.substr(0,4);
	//追溯
	if(tFieldValue=="0"){
//		PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		temppoladdgridid.document.all('PolAddGrid9').value=tBeginAdd;
	}
	//当期
	if(tFieldValue=="1"){
		//tempDate=tempDate+1;
		//var tempDate3=tempDate+1;
		//tBeginAdd=tempDate3+tempDate2;
		//alert("tBeginAdd=="+tBeginAdd);
		//PolAddGrid.setRowColData(spanObj,9,"2009-12-30");
		//alert("getCurrentDate=="+getCurrentDate("-"));
		//tBeginAdd=tempDate4+tempDate2;
		//PolAddGrid.setRowColData(spanObj,9,tBeginAdd);
		 		
		if(inttempDate<intBeginDate){
			tempMonth = parseInt(tempMonth)-1;
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}else{
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}
	}
	//下期
	if(tFieldValue=="2"){
		if(inttempDate<intBeginDate){
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}else{
			tempMonth = parseInt(tempMonth)+1;
//			PolAddGrid.setRowColData(spanObj,9,tempYear+"-"+tempMonth+"-"+tempDate);
			temppoladdgridid.document.all('PolAddGrid9').value=tempYear+"-"+tempMonth+"-"+tempDate;
		}
	}	  	
}
//计算季交加费起期
function paySeason(tBeginAdd,tFieldValue,spanObj,CurrentDate){
	
}
//计算半年交加费起期
function payHalfYear(tBeginAdd,tFieldValue,spanObj,CurrentDate){

}
function verifyChoose(){
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0){
		 alert("请先选择一条加费记录!");
		 return false;
	}
}
