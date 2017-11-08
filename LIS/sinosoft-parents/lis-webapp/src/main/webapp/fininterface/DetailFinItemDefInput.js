//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mOperate="";
var turnPage = new turnPageClass();
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
function superaddClick()
{
	if((document.all('LevelConditionB').value == null)||(document.all('LevelConditionB').value == ''))
	{
		alert("该项不得为空，请重新填写！");
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
		return false;
	}
	if((document.all('LevelCondition').value == null)||(document.all('LevelCondition').value == ''))
	{
		document.all('LevelCondition').value = document.all('LevelConditionB').value;
		document.all('LevelConditionName').value = document.all('LevelConditionBName').value;
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
	}
	else
	{
		document.all('LevelCondition').value = document.all('LevelCondition').value + "," +document.all('LevelConditionB').value;
		document.all('LevelConditionName').value = document.all('LevelConditionName').value + "," +document.all('LevelConditionBName').value;
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
	}
}//add by fx

function clearClick()
{
		document.all('LevelConditionB').value = '';
		document.all('LevelConditionBName').value = '';
		document.all('LevelCondition').value = '';
		document.all('LevelConditionName').value = '';
}//add by fx

//进入页面自动查询

function initDetailDefQuery() 
{		
  	var strSQL = "";
  	/**
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and FinItemID ='"+FinItemID+"' ";
  	*/
  	//alert(strSQL);  	
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.DetailFinItemDefInputSql"); //指定使用的properties文件名
		mySql1.setSqlId("DetailFinItemDefInputSql1");//指定使用的Sql的id
		mySql1.addSubPara(VersionNo);//指定传入的参数
		mySql1.addSubPara(FinItemID);//指定传入的参数
		strSQL= mySql1.getString();
  	
  	turnPage.queryModal(strSQL, DetailDefGrid);
} 



function addClick()
{
	 if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
	//为了防止双击，点击增加后，屏蔽"增加"按钮
  mOperate="INSERT||MAIN";
  submitForm();
}


//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //提交前的检验
  if (!beforeSubmit2())
  {
  	return false;
  }	
	
  //下面增加相应的删除代码
  if ((document.all("JudgementNo").value==null)||(trim(document.all("JudgementNo").value)==''))
    alert("请确定要删除的判断条件续号！");
  else
  {

    if (confirm("您确实想删除该记录吗?"))
    {
      mOperate="DELETE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("您取消了删除操作！");
    }
  }
}


function updateClick()
{
  //提交前的检验
  if (!beforeSubmit2())
  {
  	return false;
  }		
   if (!beforeSubmit()) //beforeSubmit()函数
  {
  	return false;
  }
  else
  {
    if (confirm("您确实想修改该记录吗?"))
    {
      mOperate="UPDATE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("您取消了修改操作！");
    }
  }
}


//进入明细科目分支影射定义页面
function intoDetailCode()
{
  //下面增加相应的代码
  //mOperate="QUERY||MAIN";   
  var VersionNo = document.all('VersionNo').value;
  //VersionState在fm没有，需要在DetailFinItemCodeInput.jsp的fm中加上VersionState
  //var VersionState = document.all('VersionState').value;
  //alert(VersionState);
  //alert(fm.VersionState.value);
  var FinItemID = document.all('FinItemID').value;
  var JudgementNo = document.all('JudgementNo').value;
    
  if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询，然后再进行明细科目分支影射定义");
  	return;
  }
  
  if (FinItemID == null||FinItemID == '')  
  {
  	alert("请先进行科目信息查询，然后再进行明细科目分支影射定义");
  	return;
  }
  
  if (JudgementNo == null||JudgementNo == '')  
  {
  	alert("请先进行明细科目判断条件定义，然后再进行明细科目分支影射定义");
  	return;
  }
           
  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from FIDetailFinItemDef where versionno = '"+document.all('VersionNo').value+"' and FinItemID = '"+document.all('FinItemID').value+"' and JudgementNo = '"+document.all('JudgementNo').value+"' ";
  */
  	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.DetailFinItemDefInputSql"); //指定使用的properties文件名
		mySql2.setSqlId("DetailFinItemDefInputSql2");//指定使用的Sql的id
		mySql2.addSubPara(document.all('VersionNo').value);//指定传入的参数
		mySql2.addSubPara(document.all('FinItemID').value);//指定传入的参数
		mySql2.addSubPara(document.all('JudgementNo').value);//指定传入的参数
		strSQL= mySql2.getString();
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("该组明细科目判断条件信息不存在，请确定完成添加该组信息后再进行明细科目分支影射定义");
  	return false;
  }
   
  showInfo=window.open("./FrameDetailFinItemCodeInput.jsp?VersionNo=" + VersionNo + "&VersionState=" + VersionState + "&FinItemID="+FinItemID + "&JudgementNo="+JudgementNo );
  //parent.fraInterface.window.location = "./ProposalGrpInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type;  
	//showInfo=window.open("./LABranchGroupQuery.jsp?BranchType='"+document.all('BranchType').value + "'");    	
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("在DetailFinItemDefInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}


//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}



function submitForm()
{

  //提交前的检验
 

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

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("操作控制数据丢失！");
  }
  fm.action="DetailFinItemDefSave.jsp";
  //lockButton(); 
  document.getElementById("fm").submit(); //提交

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
		mOperate="";
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

//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
    //执行下一步操作
    //resetForm();
    if(mOperate=="DELETE||MAIN")
    {
    	 document.all('JudgementNo').value = '';
  		 document.all('LevelCondition').value = '';
  		 document.all('LevelConditionName').value = '';
  		 document.all('FirstMark').value = ''; 
    	 document.all('FirstMarkName').value = '';  
    	 document.all('ReMark').value = '';  
  		 document.all('LevelConditionB').value = '';
  		 document.all('LevelConditionBName').value = '';
  		 
  		 document.all('JudgementNo').readOnly = false;	
    }
    mOperate="";
    initDetailDefQuery();    
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


//增加、删除、修改提交前的校验、计算
function beforeSubmit()
{			
  if((fm.JudgementNo.value=="")||(fm.JudgementNo.value=="null"))
  {
    alert("请您录入判断条件续号！");
    return ;
  }
  if(trim(document.all('LevelCondition').value).length > 500)
  {
  	alert("层级条件组合输入项内容过长，请重新输入！");
  	document.all('JudgementNo').value = '';
  	document.all('LevelCondition').value = '';
  	document.all('LevelConditionName').value = '';
  	document.all('LevelConditionB').value = '';
  	document.all('LevelConditionBName').value = '';
    document.all('FirstMark').value = ''; 
    document.all('FirstMarkName').value = '';  
    document.all('ReMark').value = '';  
  }//add by fx
  
  if (!verifyInput2())
  {
  	return false;
  }
    
    return true;
}


//删除和修改前必须选中一条记录
function beforeSubmit2()
{		
	var tSel = DetailDefGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请您先选择一条记录，再进行修改和删除的操作!" );
		return;
	}
    return true;
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


//选中mulline中一行，自动给输入框赋值
 function ShowDetailDef()
{
 	var arrResult = new Array();

	var tSel = DetailDefGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	//设置需要返回的数组
	// 书写SQL语句		
  	var strSQL = "";
  	/**
		strSQL = "select a.VersionNo,a.FinItemID,a.JudgementNo,func_1803_01(a.LevelCondition),a.FirstMark,a.ReMark from FIDetailFinItemDef a where a.VersionNo='"+
	          DetailDefGrid.getRowColData(tSel-1,1)+"' and a.FinItemID='"+
	          DetailDefGrid.getRowColData(tSel-1,2)+"' and a.JudgementNo='"+
	          DetailDefGrid.getRowColData(tSel-1,3)+"'";
	          */
		//alert(strSQL);
		var mySql3=new SqlClass();
			mySql3.setResourceName("fininterface.DetailFinItemDefInputSql"); //指定使用的properties文件名
			mySql3.setSqlId("DetailFinItemDefInputSql3");//指定使用的Sql的id
			mySql3.addSubPara(DetailDefGrid.getRowColData(tSel-1,1));//指定传入的参数
			mySql3.addSubPara(DetailDefGrid.getRowColData(tSel-1,2));//指定传入的参数
			mySql3.addSubPara(DetailDefGrid.getRowColData(tSel-1,3));//指定传入的参数
			strSQL= mySql3.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
    alert("查询失败！");
    return false;
  }
	//查询成功则拆分字符串，返回二维数组
  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult); 
  	document.all('VersionNo').value = arrResult[0][0];
  	//alert(VersionNo);  
  	document.all('FinItemID').value = arrResult[0][1];
  	//alert(FinItemID);    	
  	document.all('JudgementNo').value = arrResult[0][2];
  	document.all('LevelCondition').value = arrResult[0][3];
  	document.all('FirstMark').value = arrResult[0][4];  	  	  	  	
  	document.all('ReMark').value = arrResult[0][5];  	  	  	  	
	}	 
	//选中mulline赋值之后，JudgementNo不能进行修改，只能修改非主键信息
		document.all('JudgementNo').readOnly = true;	
		//document.all('JudgementNo').disabled = true;	
		showCodeName(); 
}

function resetAgain()
{
			 document.all('JudgementNo').value = '';
  		 document.all('LevelCondition').value = '';
  		 document.all('LevelConditionName').value = '';
  		 document.all('FirstMark').value = ''; 
    	 document.all('FirstMarkName').value = '';  
    	 document.all('ReMark').value = '';  
  		 document.all('LevelConditionB').value = '';
  		 document.all('LevelConditionBName').value = '';
  		 
  		 document.all('JudgementNo').readOnly = false;
}