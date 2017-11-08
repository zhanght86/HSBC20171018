//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sqlresourcename = "appgrp.GroupPolSignSql";
//提交，保存按钮对应操作
function signGrpPol()
{
	//只能是四位区站操作
	var tLine=comcode.length;
	if(tLine<4)
	{
		alert("只能在四位代码的机构进行系统操作！");
		return false;
	}
	
//	var tSel = GrpGrid.getSelNo();
//	var cPolNo = "";
//	if( tSel != null && tSel != 0 )
//		cPolNo = GrpGrid.getRowColData( tSel - 1, 1 );
    var isChk = false;
    var rowNum = GrpGrid.mulLineCount;
    for(var i=0; i<rowNum;i++)
    {
       if(GrpGrid.getChkNo(i))
       {
          isChk = true;
          break;
       }
    }
   if (isChk==false)
    {
      alert("请选择一张集体投保单后，再进行签单操作");
      return false;
    }
	else
	{
		var i = 0;
		var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		lockScreen('lkscreen');  
		document.getElementById("fm").submit(); //提交
	}
//	if( cPolNo == null || cPolNo == "" )
//		alert("请选择一张集体投保单后，再进行签单操作");
}

/*********************************************************************
 *  集体投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
	if( FlagStr == "Fail" )
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	 	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    //verify 
   // var sql="select dif from lcgrpcont where prtno='"+fm.tt.value+"'";
    
    var sqlid1="GroupPolSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.tt.value);
    
    var arrQueryResult = easyExecSql(mySql1.getString(), 1, 0);
    if(arrQueryResult!=null)
    {
    var money=arrQueryResult[0][0];
    if(money>0)
    {
    	alert("您所交的保费仍有剩余，请及时退费！");
    }
  }
		// 刷新查询结果
		easyQueryClick();		
	}
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  调用EasyQuery查询保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	// 初始化表格
	initGrpGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	/*
	strSQL = "select grpcontno,PrtNo,AppntNo,GrpName,ManageCom,AgentCode from LCGrpcont where 1=1 "
				 + "and AppFlag='0' "
				 + "and ApproveFlag='9' "
				 + "and UWFlag in ('9','4') "			// 核保通过
				 + getWherePart( 'GrpContNo' )
				 + getWherePart( 'ManageCom' )
				 + getWherePart( 'AgentCode' )
				 + getWherePart( 'AgentGroup' )
				 + getWherePart( 'GrpNo' )
				 + getWherePart( 'GrpName' );
				 */
				 if(comcode=="%")
				 {
				 /*
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop9,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid, "
	       + "case  when (select distinct 1 from  ljtempfee where otherno=lwmission.missionprop2) is  null then '未交费未签单' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is not null and confdate is null and confflag='0' ) is not null then '已缴费待签单' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is null and confdate is null and confflag='0' ) is not null then '未到帐未签单' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confdate is not null and confflag='1' and tempfeeno in (select tempfeeno from ljagettempfee)) is not null then '财务缴费错误未签单' else '状态查询出错' end "
	       + "from lwmission where 1=1"			 
	       + " and lwmission.processid = '0000000004'"
	       + " and lwmission.activityid = '0000002006' "
				 + getWherePart('lwmission.missionprop1','GrpContNo',strOperate)
				 + getWherePart('lwmission.missionprop2','PrtNo',strOperate);
				 //+ getWherePart('lwmission.missionprop5','AgentCode',strOperate)
				 //+ getWherePart('lwmission.missionprop6','AgentGroup',strOperate)
				 //+ getWherePart('lwmission.missionprop9','GrpNo',strOperate)
				 //+ getWherePart('lwmission.missionprop7','GrpName',strOperate)
				 //+ " and lwmission.missionprop4 like '" + comcode + "%'";  //集中权限管理体现	
				 //+ " order by lwmission.missionprop2"
				 //alert("ok");
				 
			*/	 
				 
		var sqlid2="GroupPolSignSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.GrpContNo.value);
		mySql2.addSubPara(fm.PrtNo.value);
				 
		strSQL = mySql2.getString();
				 
				 
				 
				 
//alert(strSQL);
	//easyQueryVer3(strSQL);
}else{
/*
	strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop9,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid ,"
	       + "case  when (select distinct 1 from  ljtempfee where otherno=lwmission.missionprop2) is  null then '未交费未签单' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is not null and confdate is null and confflag='0' ) is not null then '已缴费待签单' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confmakedate is null and confdate is null and confflag='0' ) is not null then '未到帐未签单' "
	       + "when (select distinct 1 from ljtempfee where otherno=lwmission.missionprop2 and confdate is not null and confflag='1' and tempfeeno in (select tempfeeno from ljagettempfee)) is not null then '财务缴费错误未签单' else '状态查询出错' end "
	       + "from lwmission where 1=1"			 
	       + " and lwmission.processid = '0000000004'"
	       + " and lwmission.activityid = '0000002006' "
				 + getWherePart('lwmission.missionprop1','GrpContNo',strOperate)
				 + getWherePart('lwmission.missionprop2','PrtNo',strOperate)
				 //+ getWherePart('lwmission.missionprop5','AgentCode',strOperate)
				 //+ getWherePart('lwmission.missionprop6','AgentGroup',strOperate)
				 //+ getWherePart('lwmission.missionprop9','GrpNo',strOperate)
				 //+ getWherePart('lwmission.missionprop7','GrpName',strOperate)
				 + " and lwmission.missionprop4 like '" + fm.ManageCom.value + "%%'"; //集中权限管理体现	
				 //+ " order by lwmission.missionprop2"
				 //alert("ok");
			*/	 
				 var sqlid3="GroupPolSignSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(fm.GrpContNo.value);
		mySql3.addSubPara(fm.PrtNo.value);
		mySql3.addSubPara(fm.ManageCom.value);
				 
		strSQL = mySql3.getString();
				 
				 
//alert(strSQL);
	//easyQueryVer3(strSQL);
}
	turnPage.queryModal(strSQL, GrpGrid);
}

/*********************************************************************
 *  显示EasyQuery的查询结果
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initGrpGrid();
		//HZM 到此修改
		GrpGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpGrid.loadMulLine(GrpGrid.arraySave);		
		//HZM 到此修改	
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//发首期交费通知书
function SendFirstNotice()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var tSel = GrpGrid.getSelNo();
	var cProposalNo = "";
	if( tSel != null && tSel != 0 )
		cProposalNo = GrpGrid.getRowColData( tSel - 1, 1 );
 
  cOtherNoType="01"; //其他号码类型
  cCode="57";        //单据类型
  
  
  if (cProposalNo != "")
  {
  	//showModalDialog("../uwgrp/GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var urlS1 = "../uwgrp/GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode"
	var name='提示';   //网页名称，可为空; 
	var iWidth=20+"cm";      //弹出窗口的宽度; 
	var iHeight=10+"cm";     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	var showInfo1 = window.open (urlS1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

