//Creator :范昕	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) //shwoInfo是什么？
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
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



function VersionStateQuery()
{
	window.open("./FrameVersionRuleQuery.jsp");
}
function queryClick()
{
	var VersionNo = document.all('VersionNo').value;

	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询！");
  	return;
  }
  
  var DistillMode = document.all('DistillMode').value;
	var DistillModeName = document.all('DistillModeName').value;
  showInfo=window.open("./FrameCostDataAcquisitionDefQuery.jsp?VersionNo=" + VersionNo  );
}

function CostDataBaseDefInputClick()
{
	var VersionNo = document.all('VersionNo').value;
	var AcquisitionID = document.all('AcquisitionID').value;
	var VersionState = document.all('VersionState').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询，然后再进行数据采集数据源定义");
  	return;
  }
  
  if (AcquisitionID == null||AcquisitionID == '')  
  {
  	alert("请先进行数据采集编号信息查询，然后再进行数据采集数据源定义");
  	return;
  }

  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from ficostdataacquisitiondef where versionno = '"+document.all('VersionNo').value+"' and AcquisitionID = '"+document.all('AcquisitionID').value+"' ";
  */
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CostDataAcquisitionDefInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("CostDataAcquisitionDefInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(document.all('VersionNo').value);//指定传入的参数
		mySql1.addSubPara(document.all('AcquisitionID').value);//指定传入的参数
		strSQL= mySql1.getString();
  
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("该组采集信息不存在，请确定完成添加该组信息后再进行数据采集数据源定义");
  	return false;
  }
	showInfo=window.open("./FrameCostDataBaseDefInput.jsp?VersionNo=" + VersionNo  + "&VersionState=" + VersionState + "&AcquisitionID="+AcquisitionID);
}

function CostDataKeyDefInputClick()
{
	var VersionNo = document.all('VersionNo').value;
	var AcquisitionID = document.all('AcquisitionID').value;
	var VersionState = document.all('VersionState').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询，然后再进行数据采集主键定义");
  	return;
  }
  
  if (AcquisitionID == null||AcquisitionID == '')  
  {
  	alert("请先进行数据采集编号信息查询，然后再进行数据采集主键定义");
  	return;
  }
  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from ficostdataacquisitiondef where versionno = '"+document.all('VersionNo').value+"' and AcquisitionID = '"+document.all('AcquisitionID').value+"' ";
  */
  	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.CostDataAcquisitionDefInputSql"); //指定使用的properties文件名
		mySql2.setSqlId("CostDataAcquisitionDefInputSql2");//指定使用的Sql的id
		mySql2.addSubPara(document.all('VersionNo').value);//指定传入的参数
		mySql2.addSubPara(document.all('AcquisitionID').value);//指定传入的参数
		strSQL= mySql2.getString();
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("该组采集信息不存在，请确定完成添加该组信息后再进行数据采集主键定义");
  	return false;
  }
  showInfo=window.open("./FrameCostDataKeyDefInput.jsp?VersionNo=" + VersionNo  + "&VersionState=" + VersionState + "&AcquisitionID="+AcquisitionID);
}

function submitForm()
{
	if(!beforeSubmit())
	{
		return false;
	}
  fm.OperateType.value = "INSERT||MAIN";
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
  fm.action = './CostDataAcquisitionDefSave.jsp';
  document.getElementById("fm").submit(); //提交
  //alert(1)
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if(!beforeSubmit())
	{
		return false;
	}
	if(fm.tempAcquisitionType.value != fm.AcquisitionType.value)
	{
		alert("不得对数据采集类型进行更改，请重新申请采集!");
		return false;
	}
	if(fm.tempCostOrDataID.value != fm.CostOrDataID.value)
	{
		alert("不得对费用编码进行更改，请重新申请采集!");
		return false;
	}
  if (confirm("您确实想修改该记录吗?"))
  {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataAcquisitionDefSave.jsp';
    document.getElementById("fm").submit(); //提交

  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value==null))
	 {
	 	alert("请先通过查询取得版本编号！！！");
	 	return false;
	 }
	 if((document.all('AcquisitionID').value=="")||(document.all('AcquisitionID').value==null))
	 {
	 	alert("请确定要删除的数据采集编号！！！");
	 	return false;
	 }
	 
  if (confirm("您确实要删除该记录吗？"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或连接其他的界面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataAcquisitionDefSave.jsp';
    document.getElementById("fm").submit();//提交

  }
}

function afterQuery1( arrQueryResult1 )
{
	var arrResult1 = new Array();
	if( arrQueryResult1 != null )
	{
   
		arrResult1 = arrQueryResult1;
		arrResult1[0][6] = arrResult1[0][6].split("@@").join("||");
		arrResult1[0][7] = arrResult1[0][7].split("@@").join("||");
		arrResult1[0][6] = arrResult1[0][6].split("@").join("|");
		arrResult1[0][7] = arrResult1[0][7].split("@").join("|");
		document.all('AcquisitionID').value = arrResult1[0][0];
		document.all('AcquisitionType').value = arrResult1[0][1];
		document.all('tempAcquisitionType').value = arrResult1[0][1];
		document.all('DataSourceType').value = arrResult1[0][2];
		document.all('CostOrDataID').value = arrResult1[0][3];
		document.all('tempCostOrDataID').value = arrResult1[0][3];
		document.all('DistillMode').value = arrResult1[0][4];
		 
	if(document.all('DistillMode').value=='1')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display=''; 
		 document.all('DistillClass').value = '';
		 document.all('DistillClassForOne').value = '';
	}
			
  if(document.all('DistillMode').value=='2')
  {
		 classdiv.style.display='';
		 sqldiv.style.display='none';	
		 document.all('DistillSQL').value = '';
     document.all('DistillSQLForOne').value = '';
	}		
		document.all('Remark').value = arrResult1[0][5];
		document.all('DistillSQL').value = arrResult1[0][6];
		document.all('DistillSQLForOne').value = arrResult1[0][7];
		document.all('DistillClass').value = arrResult1[0][8];
		document.all('DistillClassForOne').value = arrResult1[0][9];


		document.all('addbutton').disabled = false;
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;
		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}		
		showCodeName();
	}
	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //释放“增加”按钮

  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //resetForm();
	fm.OperateType.value="";
  }
  else
  {
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		if(fm.OperateType.value == "DELETE||MAIN")
		{
			document.all('AcquisitionID').value = '';
			document.all('tempAcquisitionID').value = '';
    	document.all('AcquisitionType').value = '';
    	document.all('tempAcquisitionType').value = '';
    	document.all('DataSourceType').value = '';
    	document.all('CostOrDataID').value = '';
    	document.all('DistillMode').value = '';
    	document.all('DistillSQL').value = '';
    	document.all('DistillSQLForOne').value = '';
    	document.all('DistillClass').value = '';
    	document.all('DistillClassForOne').value = '';
    	document.all('Remark').value = '';
    	document.all('AcquisitionTypeName').value = '';
    	document.all('CostOrDataName').value = '';
    	document.all('DistillModeName').value = '';
    	document.all('DataSourceTypeName').value = '';
    	document.all('tempCostOrDataID').value = '';

    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none'; 
		}
    fm.OperateType.value="";
   
  }

}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('VersionState').value = arrResult[0][5];	
		document.all('VersionState2').value = arrResult[0][9];	
		
			document.all('AcquisitionID').value = '';
			document.all('tempAcquisitionID').value = '';
    	document.all('AcquisitionType').value = '';
    	document.all('tempAcquisitionType').value = '';
    	document.all('DataSourceType').value = '';
    	document.all('CostOrDataID').value = '';
    	document.all('DistillMode').value = '';
    	document.all('DistillSQL').value = '';
    	document.all('DistillSQLForOne').value = '';
    	document.all('DistillClass').value = '';
    	document.all('DistillClassForOne').value = '';
    	document.all('Remark').value = '';
    	document.all('AcquisitionTypeName').value = '';
    	document.all('CostOrDataName').value = '';
    	document.all('DistillModeName').value = '';
    	document.all('DataSourceTypeName').value = '';
    	document.all('tempCostOrDataID').value = ''

    	classdiv.style.display='none';
		 	sqldiv.style.display='none'; 
    //当版本状态不为02-维护的时候，增删改按钮为灰色		
		if (arrResult[0][5] == "01"||arrResult[0][5] == "03"||arrResult[0][5] == ""||arrResult[0][5] == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		if (arrResult[0][5] == "02")
		{
			document.all('addbutton').disabled = false;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
			
		
		//来自于Common\javascript\Common.js，根据代码选择的代码查找并显示名称
		showCodeName(); 

	}
}

function afterCodeSelect( cName, Filed)
{   
	if(fm.DistillMode.value=='1')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display=''; 
		 document.all('DistillClass').value = '';
		 document.all('DistillClassForOne').value = '';
	}
			
  if(fm.DistillMode.value=='2')
  {
		 classdiv.style.display='';
		 sqldiv.style.display='none';	
		 document.all('DistillSQL').value = '';
     document.all('DistillSQLForOne').value = '';
	}			

}

function AcquisitionClick()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询！");
  	return;
  }
	initAcquisition();
	document.all('AcquisitionID').value ='';
	document.all('AcquisitionID').value = document.all('tempAcquisitionID').value;
}

function beforeSubmit()
{
	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value==null))
	 {
	 	alert("请先通过查询取得版本编号！！！");
	 	return false;
	 }
	 if((document.all('AcquisitionID').value=="")||(document.all('AcquisitionID').value==null))
	 {
	 	alert("数据采集编号为空，请先进行申请！！！");
	 	return false;
	 }
	 if((document.all('AcquisitionType').value=="")||(document.all('AcquisitionType').value==null))
	 {
	  alert("数据采集类型为空！");
	  return false
	 }
	 if((document.all('DataSourceType').value=="")||(document.all('DataSourceType').value==null))
	 {
	  alert("采集数据库类型为空！");
	  return false
	 }
	 if((document.all('CostOrDataID').value=="")||(document.all('CostOrDataID').value==null))
	 {
	  alert("费用或数据类型编码为空！");
	  return false
	 }
	 if((document.all('DistillMode').value=="")||(document.all('DistillMode').value==null))
	 {
	  alert("数据采集方式为空！");
	  return false
	 }
	 if(document.all('DistillMode').value=="1")
	 {
	 	if(((document.all('DistillSQL').value =="")||(document.all('DistillSQL').value ==null))&&((document.all('DistillSQLForOne').value =="")||(document.all('DistillSQLForOne').value ==null)))
	 	{
	 		alert("采集SQL不得为空！");
	 		return false;
	 	}
	 }
	 if(document.all('DistillMode').value=="2")
	 {
	 	if((document.all('DistillClass').value=="")||(document.all('DistillClass').value==null))
	 	{
	 		alert("采集处理类不得为空！");
	 		return false;
	 	}
	 }
	 return true;
}

function resetAgain()
{
			document.all('AcquisitionID').value = '';
			document.all('tempAcquisitionID').value = '';
    	document.all('AcquisitionType').value = '';
    	document.all('tempAcquisitionType').value = '';
    	document.all('DataSourceType').value = '';
    	document.all('CostOrDataID').value = '';
    	document.all('DistillMode').value = '';
    	document.all('DistillSQL').value = '';
    	document.all('DistillSQLForOne').value = '';
    	document.all('DistillClass').value = '';
    	document.all('DistillClassForOne').value = '';
    	document.all('Remark').value = '';
    	document.all('AcquisitionTypeName').value = '';
    	document.all('CostOrDataName').value = '';
    	document.all('DistillModeName').value = '';
    	document.all('DataSourceTypeName').value = '';
    	document.all('tempCostOrDataID').value = '';

    	
    	document.all('updatebutton').disabled = true;	
    	document.all('deletebutton').disabled = true;
    	classdiv.style.display='none';
		 	sqldiv.style.display='none'; 
		
}