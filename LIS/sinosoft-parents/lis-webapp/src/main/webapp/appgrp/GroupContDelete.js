

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "appgrp.GroupContDeleteSql";
//提交，保存按钮对应操作
function submitForm()
{
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
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //提交
  alert("submit");
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    alert(content);
  }
  else
  { 
    alert("操作成功");
  }
  querygrp();
  document.all("GrpContNo").value = "";
  document.all("PrtNo").vlaue = "";
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
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

//查询集体单
function querygrp()
{
	// 初始化表格                                                                                                                                                           
	initGrpGrid();	
	
	if(fm.QGrpProposalNo.value==null||fm.QGrpProposalNo.value==""){
	  alert("请录入投保单号！");
	  return false;
	}
	// 书写SQL语句
	var str = "";	
	
	var strsql = "";
	if(manageCom=="%")
	{
	
		strsql = "select a.ProposalGrpContNo,a.GrpContNo,a.PrtNo,a.GrpName,a.ManageCom from LCGrpCont a where (markettype='0' or markettype is null)  and managecom like '"+manageCom+"%' "
				 + getWherePart( 'a.ProposalGrpContNo','QGrpProposalNo' )
				 +" and cardflag is null";
		/*
		var sqlid1="GroupContDeleteSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(manageCom);
		mySql1.addSubPara(fm.QGrpProposalNo.value);
			
			strsql =	mySql1.getString(); */
				 
	}else{
	
	strsql = "select a.ProposalGrpContNo,a.GrpContNo,a.PrtNo,a.GrpName,a.ManageCom from LCGrpCont a where (markettype='0' or markettype is null) and managecom like '"+manageCom+"%%' "
				 + getWherePart( 'a.ProposalGrpContNo','QGrpProposalNo' )
				 +" and cardflag is null";
	/*		
				var sqlid2="GroupContDeleteSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(manageCom);
		mySql2.addSubPara(fm.QGrpProposalNo.value);
			
			strsql =	mySql2.getString(); 
				*/
				
				}
				 
				
				 
				
	if (document.all("QState").value == "已签单")
		strsql = strsql + " and a.AppFlag = '1' "
	else if (document.all("QState").value != null && document.all("QState").value != "")
		strsql = strsql + " and (a.AppFlag <> '1' or a.AppFlag is null)"
	strsql = strsql + " order by  a.GrpContNo ";
	
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有符合条件集体单！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strsql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  document.all("GrpContNo").value = "";
  return true;
}


//
function GrpContSelect(parm1,parm2)
{
	var cPrtNo = "";
	var cGrpPolNo = "";
	
	if(document.all(parm1).all('InpGrpGridSel').value == '1' )
	{
		//当前行第1列的值设为：选中
   		cPrtNo = document.all(parm1).all('GrpGrid3').value;	//流水号
   		cGrpContNo = document.all(parm1).all('GrpGrid2').value;	//集体合同号
   		cGrpProposalNo = document.all(parm1).all('GrpGrid1').value;	//集体投保单号
   		//alert(cGrpContNo);
   	}
   	if(cPrtNo == null || cPrtNo == "" || cGrpContNo == null || cGrpContNo == "")
   	{
   		alert("请选择一条有效的团体单!");
   	}
   	else
   	{
		document.all("GrpContNo").value = cGrpContNo;
		document.all("PrtNo").vlaue = cPrtNo;
   	}
}

function deleteGrpCont()
{
	//只能是四位区站操作
	var tLine=manageCom.length;
	//if(tLine<4)
	//{
	//	alert("只能在四位代码的机构进行系统操作！");
	//	return false;
	//}
	
	if (document.all("GrpContNo").value == null || document.all("GrpContNo").value == "")
	{
		alert("请先选择一条集体保单！");
		return;
	}

	if (!confirm("确认要删除该保单吗？"))
	{
		return;
	}

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	lockScreen('lkscreen');  
	fm.action = "./GroupContDeleteChk.jsp";
	document.getElementById("fm").submit();
}


function deleteCont()
{
	
	//只能是四位区站操作
	var tLine=manageCom.length;
	//if(tLine<4)
	//{
	//	alert("只能在四位代码的机构进行系统操作！");
	//	return false;
	//}
	
	if (document.all("GrpContNo").value == null || document.all("GrpContNo").value == "")
	{
		alert("请先选择一条集体保单！");
		return;
	}
  else
  {
	       window.open("./InGrpContDeleteMain.jsp?GrpContNo="+ document.all("GrpContNo").value,"",sFeatures);
	   
	}

}