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

	showInfo.focus();  //showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit();//提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  //var testPol = PolGrid.getRowColData();
  //alert(tSel);
  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
		//arrReturn = getQueryResult();
		//ContNo=arrReturn[0][0];
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
	  tOldPrtSeq = PolGrid.getRowColData(tSel-1,10); 
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
	
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.NoticeType.value = tNoticeType;
		fmSave.fmtransact.value = "PRINT";
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.target = "../f1print";
	  if(tNoticeType==89)
	    {
	   	  	//alert(11111);
	   	  	fmSave.action="./MeetF1PSave.jsp";
	   	}
	   	else if(tNoticeType == 03)
	   	{
	   		 fmSave.action="./BodyCheckPrintSave.jsp";
	   	}
	   	else if(tNoticeType == 04)
	   	{
	   		fmSave.action="./MeetF1PSave.jsp";
	   	}
	  	  else
	  	{
	  	
	  	fmSave.action="./UWF1PSave.jsp";
	  	} 	
		fmSave.submit();
		showInfo.close();


	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //执行下一步操作
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
	var tManageCom = fm.ManageCom.value;
	if(tManageCom != null && tManageCom == "")
	{
		alert("请录入管理机构！");
		return;
	}
	if(tManageCom.length < 4)
	{
		alert("请录入四位及以上的管理机构！");
		return;
	}
	initPolGrid();
	// 书写SQL语句
	//tongmeng 2007-11-12 modify
	//统一打印核保通知书
	var strSQL = "";
	// 书写SQL语句
//	var ssql = "select processid from lwcorresponding where busitype in ('1001')";
	
	var sqlid0="UWNoticeInputSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("uw.UWNoticeInputSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	var ssql=mySql0.getString();
	
	var tProcessID_TB = easyExecSql(ssql);
	
//	ssql ="select processid from lwcorresponding where busitype in ('1002')"
	
	var sqlid1="UWNoticeInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWNoticeInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	ssql=mySql1.getString();
		
	var tProcessID_BQ = easyExecSql(ssql);
	
	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,"
	        //+ " LWMission.MissionProp5 "
	        + "a.code "
	        + ",(select codename from ldcode where codetype='noticetype' and code=a.code) "
	        + ",LWMission.MissionProp7 "
	        + " ,c.salechnl,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 "
	        + " FROM LWMission,loprtmanager a,lccont c "
	        //+ " WHERE LWMission.ActivityID in ('0000001280','0000001017','0000001107','0000001302','0000001106','0000001108') "
	        //+ " and (LWMission.ProcessID = '0000000003' or LWMission.ProcessID = '0000000000')" //承保工作流 ||【0000001108】的时候要查询保全的工作流
	        + " WHERE LWMission.ActivityID  in(Select activityid from lwactivity where functionid in ('10010007','10010025','10010026','10010027','10010050','10010058','10010063','10010064','10010065','10010066','10010067','10010068')) "
	        + " and LWMission.processid in ('"+tProcessID_TB+"','"+tProcessID_BQ+"')"
	        + " and c.prtno=a.otherno "
	        + " and a.prtseq=LWMission.MissionProp3"
	        + " and LWMission.missionprop2 = c.contno "
	//tongmeng 2007-12-11 add
	if(document.all('NoticeType_Code').value!=null&&trim(document.all('NoticeType_Code').value)!='')   
	{
		strSQL = strSQL + " and exists (select '1' from loprtmanager where prtseq=LWMission.MissionProp3 and code='"+trim(document.all('NoticeType_Code').value)+"')"
	}     
	        
	        
	        strSQL = strSQL + getWherePart('LWMission.MissionProp1', 'PrtNo')
	        + getWherePart('LWMission.MissionProp2', 'ContNo')
			    + getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
			    + getWherePart('LWMission.MissionProp4','AgentCode')
			   // + getWherePart('LWMission.MissionProp5','NoticeType')
			    + getWherePart('c.salechnl','SaleChnl');
	//tongmeng 2007-10-30 add
	//增加核保下结论后系统直接发放的通知书的打印
	var tSQL_b = " union "
	           + "select a.prtseq,a.otherno,a.agentcode,a.code,(select codename from ldcode where codetype='noticetype' and code=a.code),a.managecom,(select salechnl from lccont where contno=a.otherno) "
	           + ",'TBJB','1',a.prtseq from loprtmanager a " 
                   + " where 1=1 and a.standbyflag7='TBJB' and a.stateflag='0' "
	           + getWherePart('a.otherno', 'ContNo')
     	           + getWherePart('a.managecom', 'ManageCom', 'like')
	           + getWherePart('a.agentcode','AgentCode')
	           + getWherePart('a.code','NoticeType')
	if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
	{
	   tSQL_b = tSQL_b + " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
	}	
	
	//增加转账不成功通知书打印 hanbin 2010-05-07
	//增加时间条件，否则数据太多，耗时太长 hanbin-2010-05-19
	var tSQL_c = " union "
        + " select '', a.prtno, a.agentcode, '21', (select codename from ldcode where codetype = 'noticetype' and code = '21'), a.managecom, a.salechnl,'TBBANK','','' from lccont a where a.conttype = '1' and a.appflag = '0' and a.uwflag not in ('a', '1', '2') "
        + " and exists(select polno from lyreturnfrombankb  where polno = prtno and banksuccflag != '0000' group by polno having(count(1)  = 3 ) ) " 
        + " and not exists(select 1 from loprtmanager where othernotype = '05' and otherno = a.prtno and code = '21') "
        + " and not exists(select 1 from ljtempfee where tempfeetype = '1' and otherno = a.prtno and enteraccdate is not null) "//保费已经到帐则不再打印通知书
        + " and not exists(select polno from lyreturnfrombankb  where polno = prtno and banksuccflag = '0000') "
        + " and a.makedate >= to_date((subStr(add_months(now(),-1),1,10)),'yyyy-mm-dd')  and a.makedate <= to_date(substr(now(),1,10),'yyyy-mm-dd')"
        + getWherePart('a.ContNo', 'ContNo')
	    + getWherePart('a.managecom', 'ManageCom', 'like')
        + getWherePart('a.agentcode','AgentCode')
        + getWherePart('a.salechnl','SaleChnl')
        if(document.all('NoticeType_Code').value!=null && trim(document.all('NoticeType_Code').value)!='')
		{
        	tSQL_c = tSQL_c + " and '21'='"+trim(document.all('NoticeType_Code').value)+"'"
		}
	//查询补打【转账不成功通知书】 数据 hanbin 2010-05-11
	var tSQL_c_rePrint = " union "
        + "select a.prtseq,a.otherno,a.agentcode,a.code,(select codename from ldcode where codetype='noticetype' and code=a.code),a.managecom,(select salechnl from lccont where contno=a.otherno) "
        + ",'TBBANK','1',a.prtseq from loprtmanager a " 
            + " where 1=1 and a.code='21' and a.stateflag='0' "
        + getWherePart('a.otherno', 'ContNo')
	    + getWherePart('a.managecom', 'ManageCom', 'like')
        + getWherePart('a.agentcode','AgentCode')
        + getWherePart('a.code','NoticeType')
		if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
		{
			tSQL_c_rePrint = tSQL_c_rePrint + " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
		}
	tSQL_c = tSQL_c + tSQL_c_rePrint;
         
	strSQL = strSQL + tSQL_b + tSQL_c;
	
	
	var  PrtNo2 = window.document.getElementsByName(trim("PrtNo"))[0].value;
	var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	var  SaleChnl2 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	var  NoticeType2 = window.document.getElementsByName(trim("NoticeType"))[0].value;
	var sqlid2="UWNoticeInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWNoticeInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tProcessID_TB);//指定传入的参数0
	mySql2.addSubPara(tProcessID_BQ);//指定传入的参数1
	mySql2.addSubPara(trim(document.all('NoticeType_Code').value));//指定传入的参数2
	
	mySql2.addSubPara(PrtNo2);//指定传入的参数3
	mySql2.addSubPara(ContNo2);//指定传入的参数4
	mySql2.addSubPara(ManageCom2);//指定传入的参数5
	mySql2.addSubPara(AgentCode2);//指定传入的参数6
	mySql2.addSubPara(SaleChnl2);//指定传入的参数7
	
	mySql2.addSubPara(ContNo2);//指定传入的参数8
	mySql2.addSubPara(ManageCom2);//指定传入的参数9
	mySql2.addSubPara(AgentCode2);//指定传入的参数10
	mySql2.addSubPara(NoticeType2);//指定传入的参数11
	mySql2.addSubPara(trim(document.all('SaleChnl').value));//指定传入的参数12
	
	mySql2.addSubPara(ContNo2);//指定传入的参数13
	mySql2.addSubPara(ManageCom2);//指定传入的参数14
	mySql2.addSubPara(AgentCode2);//指定传入的参数15
	mySql2.addSubPara(SaleChnl2);//指定传入的参数16
	mySql2.addSubPara(trim(document.all('NoticeType_Code').value));//指定传入的参数17
	mySql2.addSubPara(ContNo2);//指定传入的参数18
	mySql2.addSubPara(ManageCom2);//指定传入的参数19
	mySql2.addSubPara(AgentCode2);//指定传入的参数20
	mySql2.addSubPara(NoticeType2);//指定传入的参数21
	mySql2.addSubPara(trim(document.all('SaleChnl').value));//指定传入的参数22
	strSQL=mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);


  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("无待打印的核保通知书！");
    return false;
    }
    turnPage.queryModal(strSQL, PolGrid);
////查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//
//  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
//  turnPage.pageDisplayGrid = PolGrid;
//
//  //保存SQL语句
//  turnPage.strQuerySql     = strSQL;
//
//  //设置查询起始位置
//  turnPage.pageIndex       = 0;
//
//  //在查询结果数组中取出符合页面显示大小设置的数组
// arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
// //tArr=chooseArray(arrDataSet,[0])
//  //调用MULTILINE对象显示查询结果
//
//  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  //displayMultiline(tArr, turnPage.pageDisplayGrid);
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