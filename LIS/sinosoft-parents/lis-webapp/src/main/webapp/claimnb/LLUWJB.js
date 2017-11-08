//               该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var ContNo;

//提交，保存按钮对应操作
function submitForm()
{
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
  initPolGrid();
  document.getElementById("fm").submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
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
  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  if( tSel == 0 || tSel == null ){
		alert( "请先选择一条记录" );
	}
	else
	{
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
	    tOldPrtSeq = PolGrid.getRowColData(tSel-1,9); 
		tPrtNo = PolGrid.getRowColData(tSel-1,6);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
		
		if(tNoticeType=="LP89"||tNoticeType=="LP81")
		{
			var strSQL = "";
//		    strSQL = "select missionid,submissionid from lwmission where "+
//		             "activityid = '0000001280' and missionprop2 = '"+tContNo+"'";
		    
			var sqlid0="LLUWJBSql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("claimnb.LLUWJBSql"); //指定使用的properties文件名
			mySql0.setSqlId(sqlid0);//指定使用的Sql的id
			mySql0.addSubPara(tContNo);//指定传入的参数
			strSQL=mySql0.getString();
		    
		    
		    arrResult = easyExecSql(strSQL);
	        if (arrResult != null) {
		        tMissionID = arrResult[0][0];
		        tSubMissionID = arrResult[0][1];
		        fmSave.MissionID.value = tMissionID;
				fmSave.SubMissionID.value = tSubMissionID;
      		}

	    }
	  	if(tNoticeType=="LP90"){
	  		fmSave.MissionID.value = PolGrid.getRowColData(tSel-1,7);
	  		fmSave.SubMissionID.value = PolGrid.getRowColData(tSel-1,8);
	  		fmSave.ClmNo.value = PolGrid.getRowColData(tSel-1,10);
	  		fmSave.BatNo.value = PolGrid.getRowColData(tSel-1,11);
  			//alert("fmSave.MissionID.value = tMissionID;==  "+fmSave.MissionID.value);return false;
	  		
	  	}
	  	fmSave.PrtSeq.value = tPrtSeq;
	  	fmSave.OldPrtSeq.value = tOldPrtSeq;
	  	fmSave.PrtNo.value = tPrtNo;
	  	fmSave.ContNo.value = tContNo ;
	  	fmSave.NoticeType.value = tNoticeType;
	  	fmSave.fmtransact.value = "PRINT";
	  	fmSave.target = "../f1print";
		//alert("tNoticeType"+tNoticeType);
		
	  if(tNoticeType=="LP89")
	    {
	   	  fmSave.action="../uw/MeetF1PSave.jsp";
	   	}
	   	if(tNoticeType=="LP90"){
	   		fmSave.action="../uw/LLUWF1PSave.jsp";
	   	}
	  //alert(fmSave.action);
		fmSave.submit();
		showInfo.close();


	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }
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

function returnParent()
{
    tContNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?ContNo="+tContNo;
}


// 查询按钮
function easyQueryClick()
{ 
	//if(document.all('NoticeType').value==null||document.all('NoticeType').value=="")
	//{
		//alert("请选择通知书类型！");
		//return;
		//}
	initPolGrid();
	var strSQL = "";
	// 书写SQL语句
	if(document.all('NoticeType').value=="LP81"||document.all('NoticeType').value=="LP89"||document.all('NoticeType').value=="LP90")
	{ 
//		strSQL = "select missionprop3,missionprop2,missionprop4,missionprop5,missionprop7,"
//		        +"missionprop1,missionid,submissionid,missionprop14,MissionProp8,MissionProp9 from lwmission where "
//		        +"activityid = '0000001280' or activityid='0000005551' and missionprop5 like 'LP%%'"
//		        +getWherePart('missionprop5','NoticeType')
//		        +getWherePart('missionprop2', 'ContNo')
//		        //+getWherePart('missionprop7', 'ManageCom', 'like');
		        
		        
  	var  NoticeType1 = window.document.getElementsByName(trim("NoticeType"))[0].value;
  	var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var sqlid1="LLUWJBSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("claimnb.LLUWJBSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(NoticeType1);//指定传入的参数
	mySql1.addSubPara(ContNo1);//指定传入的参数
	strSQL=mySql1.getString();
		        
		}
  else{
//	strSQL = "SELECT prtseq,otherno,agentcode,code,managecom,otherno FROM loprtmanager WHERE stateflag = '0' and code like 'LP%%' "
//	        + getWherePart('otherno', 'ContNo')
//			+ getWherePart('managecom', 'ManageCom', 'like')
//			+ getWherePart('code','NoticeType'); 
	
  	var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
  	var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
  	var  NoticeType2 = window.document.getElementsByName(trim("NoticeType"))[0].value;
	var sqlid2="LLUWJBSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("claimnb.LLUWJBSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(ContNo2);//指定传入的参数
	mySql2.addSubPara(ManageCom2);//指定传入的参数
	mySql2.addSubPara(NoticeType2);//指定传入的参数
	strSQL=mySql2.getString();
	
   }
	turnPage.queryModal(strSQL, PolGrid);
	
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html");
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{

  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}