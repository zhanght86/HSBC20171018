
//程序名称：ManageIssueDoc.js
//程序功能：
//创建日期：2006-04-02
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
window.onfocus=myonfocus;
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
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

//Click事件，当点击“查询”图片时触发该函数
function easyQueryClick()
{
	var comcode = document.all('ManageCom').value;
	if(comcode == null || comcode == "")
	{
		alert("管理机构不允许为空！");
		return;
	}

		
	// 书写SQL语句
//	var strSQL = "select doccode,MissionProp2,numpages,managecom,m.makedate,m.createoperator "
//			+ "from lwmission m,es_doc_main d where ActivityID = '0000003001' "
//			+ "and m.MissionProp1=d.DocCode"
//			+ " and subtype like 'UA%' and busstype='TB' " 
//			+ getWherePart('d.doccode','BussNo','=','0')
//			+ getWherePart('d.managecom','ManageCom','like','0')
//			+ getWherePart('m.makedate','StartDate','>=','0')
//			+ getWherePart('m.makedate','EndDate','<=','0')
//			+ " order by subtype,managecom,makedate,doccode ";
	
  	var  BussNo0 = window.document.getElementsByName(trim("BussNo"))[0].value;
  	var  ManageCom0 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
	var sqlid0="AcceptOtherDocSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("easyscan.AcceptOtherDocSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(BussNo0);//指定传入的参数
	mySql0.addSubPara(ManageCom0);//指定传入的参数
	mySql0.addSubPara(StartDate0);//指定传入的参数
	mySql0.addSubPara(EndDate0);//指定传入的参数
	var strSQL=mySql0.getString();
	
	//prompt("",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
	document.all('Accept').disabled = false;
}           

//提交，保存按钮对应操作
function submitForm()
{
	if(!verifyInput())
		return ;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	document.all('Accept').disabled = true;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //提交
//  document.all('Content').value = "";
}

//校验函数
/*function vertifyInput()
{
	var content = trim(document.all('Content').value);
	if(content == null || content == "")
	{
		alert("请录入申请原因！");
		return false;
	}
	return true;
}*/

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	try{
		showInfo.close();
	}catch(ex){}
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //执行下一步操作
  }
  easyQueryClick();
}
