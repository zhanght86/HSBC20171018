//程序名称：Underwrite.js
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnConfirmPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var pflag = "1";  //保单类型 1.个人单
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgry.ManuUWInputSql";
// 标记核保师是否已查看了相应的信息
var showPolDetailFlag ;
var showAppFlag ;
var showHealthFlag ;
var QuestQueryFlag ;

//提交，保存按钮对应操作
function submitForm(flag)
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;


  if(ReportFlag=="1"&fm.all('uwUpReport').value!="4")
  {
  	alert('对于疑难案例的上报，只能在核保流向中选择"返回下级"！');
   	fm.all('uwUpReport').value="4";
   	fm.all('uwUpReportName').value="返回下级";
   	return;
  }
  if(ReportFlag=="2"&fm.all('uwUpReport').value!="0")
  {
   	alert('对于疑难案例的上报，只能在核保流向中选择"处理完毕"！');
   	fm.all('uwUpReport').value="0";
   	fm.all('uwUpReportName').value="处理完毕";
   	return;

   }


  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  if(flag==1)
  {

    var tUWState = fm.uwState.value;                  //核保结论
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //上报标志
    var tUWPopedom = fm.uwPopedom.value;              //核保师级别

    if(tUWState == "")
     {
     	showInfo.close();
      alert("请先录入承保核保结论!");
    	return ;
    }
    if(tUWIdea == "")
     {
      showInfo.close();
      alert("请先录入承保核保意见!");
      return ;
    }

    k++;
/*
    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000001110'"
    						+" and missionid = '"+fm.MissionID.value+"'"
    						;
*/
		var sqlid1="ManuUWInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(k);
		mySql1.addSubPara(k);
		mySql1.addSubPara(fm.MissionID.value);
		
    turnConfirmPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    	alert("未查到工作流SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    //alert();
    fm.action = "./UWManuNormChk.jsp";
    fm.submit(); //提交
  	return;
  }
  else if(flag ==2)
  {
    //var tSelNo = PolAddGrid.getSelNo();
    //fm.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
    //fm.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

    var tUWState = fm.uwState.value;                  //核保结论
    //var tUWDelay = fm.UWDelay.value;
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //上报标志
    var tUWPopedom = fm.uwPopedom.value;              //核保师级别
    //var tUWPer = fm.uwPer.value;                      //核保师


    if(tUWState == "")
     {
     	showInfo.close();
      alert("请先录入承保核保结论!");
    	return ;
    }

  //疑难案例和再保上报需要录入核保级别和核保师
  //  if(tUpReport == "1" || tUpReport == "3"|| tUpReport == "2"|| tUpReport == "4")
  //  {
  //    if (tUWPopedom == "" || tUWPer == "")
  //    {
  //        showInfo.close();
  //        alert("核保师级别和核保师编码不能为空!!!");
  //        return ;
  //    }
  //  }

    if(tUWIdea == "")
     {
      showInfo.close();
      alert("请先录入承保核保意见!");
      return ;
    }


    k++;
/*
    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000001110'"
    						+" and missionid = '"+fm.MissionID.value+"'"
    						;
    						*/
var sqlid2="ManuUWInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(k);
		mySql2.addSubPara(k);
		mySql2.addSubPara(fm.MissionID.value);
		
    turnConfirmPage.strQueryResult = easyQueryVer3(mySql2.getString(), 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    	alert("未查到工作流SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    fm.action = "./UWManuNormChk.jsp";
    fm.submit(); //提交
    //alert("submit");
  	return;
  }
  else if(flag==0)
  	{
  	//alert("ok");
  	//return;   
  	//add by yaory for verify 
  	
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='"+ActivityID+"' and submissionid='"+SubMissionID+"'";

var sqlid3="ManuUWInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(MissionID);
		mySql3.addSubPara(ActivityID);
		mySql3.addSubPara(SubMissionID);


var DistriMark = easyExecSql(mySql3.getString());//再保标志
//alert(DistriMark);
//strSql = "select * from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"'"; 

var sqlid4="ManuUWInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(MissionID);
		mySql4.addSubPara(SubMissionID);


var rem=easyExecSql(mySql4.getString());	
//alert(rem);
//alert(fm.uwUpReport.value);
//还有一个评点
//alert(ContNo);
//return;

//strSql="select SuppRiskScore from lcprem where contno='"+ContNo+"' and PayPlanType='01'";

var sqlid5="ManuUWInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(ContNo);


var addremark=easyExecSql(mySql5.getString());
//累计保额
var peop=PolAddGrid.getRowColData(0,1);
//alert(peop);
//strSql="select healthyamntfb('"+peop+"','','1') from dual";
var sqlid6="ManuUWInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(peop);
		
var life=easyExecSql(mySql6.getString());
//strSql="select healthyamntfb('"+peop+"','','7') from dual";

var sqlid7="ManuUWInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(peop);

var acci=easyExecSql(mySql7.getString());
//strSql="select healthyamntfb('"+peop+"','','2') from dual";

var sqlid8="ManuUWInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(peop);

var nur=easyExecSql(mySql8.getString());
var add=life+acci;
//alert(add);
//alert(nur);
//return;
var re;
var fb="0";
if(add>3000000 || nur>1200000)
{
	fb="1";
}
if(add>500000 || nur>100000)
{
	re="1";
}else{
re="0";
}

//alert(DistriMark);
//alert(addremark);
//alert(fb);
if(rem==null)
{
if(((DistriMark==1) || (addremark!=null && addremark>100 && re==1) || fb=="1") && fm.uwUpReport.value==0 )
{
	
//	var tSql = " select * from lwmission where missionid = '"+MissionID+"' and submissionid ='"+SubMissionID+"'"
	//         + " and activityid in '0000001140'" ;
	      
	      var sqlid9="ManuUWInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(MissionID);  
		mySql9.addSubPara(SubMissionID); 
 turnConfirmPage.strQueryResult = easyQueryVer3(mySql9.getString(), 1, 0, 1);
  
    if (turnConfirmPage.strQueryResult) 
    {
    		alert("您没有处理再保呈报！");
        fm.ReDistribute.disabled='';	
        showInfo.close();
	      return;
    }         

}
}
    //var tSelNo = PolAddGrid.getSelNo();
    //fm.all('PrtNo').value = PolAddGrid.getRowColData(tSelNo - 1,3);
    //fm.all('PolNo').value = PolAddGrid.getRowColData(tSelNo - 1,1);

    var tUWState = fm.uwState.value;                  //核保结论
    //var tUWDelay = fm.UWDelay.value;
    var tUWIdea = fm.UWIdea.value;
    var tUpReport = fm.uwUpReport.value                //上报标志
    var tUWPopedom = fm.uwPopedom.value;              //核保师级别
    //var tUWPer = fm.uwPer.value;                      //核保师


    if(tUWState == "")
     {
     	showInfo.close();
      alert("请先录入承保核保结论!");
    	return ;
    }

    if(tUpReport == "")
     {
     	showInfo.close();
      alert("请先选择核保流向!");
    	return ;
    }



    if(tUWIdea == "")
     {
      showInfo.close();
      alert("请先录入承保核保意见!");
      return ;
    }

    k++;
/*
    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000001110'"
    						+" and missionid = '"+fm.MissionID.value+"'"
    						;

*/
  
	      var sqlid10="ManuUWInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(k);  
		mySql10.addSubPara(k); 
		mySql10.addSubPara(fm.MissionID.value);  

    turnConfirmPage.strQueryResult = easyQueryVer3(mySql10.getString(), 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    	alert("未查到工作流SubMissionID");
    	fm.SubConfirmMissionID.value = "";
    	return;
    }
    turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
    fm.SubConfirmMissionID.value = turnConfirmPage.arrDataCacheSet[0][0];
    
    /*
    var tSql = " select * from lwmission where  1=1 "
             + " and activityid in ('0000001106','0000001107','0000001108','0000001111','0000001112','0000001113','0000001130','0000001280','0000001290','0000001300','0000001140') "
             + " and missionid = '"+fm.MissionID.value+"'"
             + " and submissionid = '"+fm.SubMissionID.value+"'";
     */        
        var sqlid11="ManuUWInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId(sqlid11);
		mySql11.addSubPara(fm.MissionID.value);  
		mySql11.addSubPara(fm.SubMissionID.value); 
             
             
    turnConfirmPage.strQueryResult = easyQueryVer3(mySql11.getString(), 1, 0, 1);
   
    if (turnConfirmPage.strQueryResult) 
    {
    	 showInfo.close();
    	 alert("还有通知书没有回复, 不允许下核保结论")   
    	 return ; 	
    }
	else 
	{
         fm.action = "./UWManuNormChk.jsp";
         fm.submit(); //提交		
	}
  
    //alert("submit");
    return;

  }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    //var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  {
	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  	//var showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	//showInfo.close();
  	//alert(content);
  	//parent.close();

    //执行下一步操作
  }

}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit3( FlagStr, content )
{
	showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;

  if (FlagStr == "Fail" )
  {
    alert(content);
  }
  else
  {
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
    //执行下一步操作
    	InitClick();
  }

}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  if (FlagStr == "Fail" )
  {
    showInfo.close();
	alert(content);
     //parent.close();
  }
  else
  {
  	showInfo.close();
	alert(content);
	//easyQueryClick();
  	//parent.close();
  }

}
//提交后操作,服务器数据返回后执行的操作
function afterApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// 初始化表格
	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
	divLCPol2.style.display= "none";
	divMain.style.display = "none";

  }
  else
  {
  		makeWorkFlow();
  }
}

function afterAddFeeApply( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
    alert(content);
    	// 初始化表格
 }
  else
  {
  	  var cPolNo=fm.ProposalNo.value;
	  var cMissionID =fm.MissionID.value;
	  var cSubMissionID =fm.SubMissionID.value;
	  var tSelNo = PolAddGrid.getSelNo()-1;
	  var cNo = PolAddGrid.getRowColData(tSelNo,1);
	  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
	  var cType = PolAddGrid.getRowColData(tSelNo,7);

	 if (cPrtNo != ""&& cNo !="" && cMissionID != "" )
	  {
	  	window.open("./UWManuAddMain.jsp?PrtNo="+cPrtNo+"&PolNo="+cPolNo+"&No="+cNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&Type="+cType,"window1",sFeatures);
	  	showInfo.close();
	  }
	  else
	  {
	  	showInfo.close();
	  	alert("请先选择保单!");
	  }
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

/*********************************************************************
 *  选择核保结后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {

	    //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName == "uwstate" ) {
			DoUWStateCodeSelect(Field.value);//loadFlag在页面出始化的时候声明
		}
}

/*********************************************************************
 *  根据不同的核保结论,处理不同的事务
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DoUWStateCodeSelect(cSelectCode) {

	if(trim(cSelectCode) == '6')//上保核保
	{
		 uwgrade = fm.all('UWGrade').value;
         appgrade= fm.all('AppGrade').value;
         if(uwgrade==null||uwgrade<appgrade)
         {
         	uwpopedomgrade = appgrade ;
         }
        else
         {
        	uwpopedomgrade = uwgrade ;
         }
        //alert(uwpopedomgrade);
        codeSql="#1# and Comcode like #"+ comcode+"%%#"+" and popedom > #"+uwpopedomgrade+"#"	;
        // alert(codeSql);
	}
	else
	codeSql="";
}


//既往投保信息
function showApp(cindex)
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uwgrp/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);
 else
	  window.open("../uwgrp/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures);

showInfo.close();

}

//以往核保记录
function showOldUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  if (cProposalNo != "")
  {
  	window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//当前核保记录
function showNewUWSub()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cContNo=fm.ProposalContNo.value;

  	//window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWErrMain.jsp?ContNo="+cContNo,"window1",sFeatures);

  	showInfo.close();

}


// 该投保单理赔给付查询
function ClaimGetQuery2()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolAddGrid.getRowColData(tSel - 1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);
	}
}
// 理赔给付查询
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cInsuredNo = fm.InsuredNo.value;
		if (cInsuredNo == "")
		    return;
		  window.open("../sys/AllClaimGetQueryMain.jsp?InsuredNo=" + cInsuredNo,"",sFeatures);
	}
}

//保单明细信息
function showPolDetail()
{
  //var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  ////showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //
  //cContNo=fm.ContNo.value;
  //if (cContNo != "")
  //{
  //	var tSelNo = PolAddGrid.getSelNo()-1;
  //	showPolDetailFlag[tSelNo] = 1 ;
  //	mSwitch.deleteVar( "ContNo" );
  //	mSwitch.addVar( "ContNo", "", cContNo );
  //	mSwitch.updateVar("ContNo", "", cContNo);
  //	window.open("../sys/AllProQueryMain.jsp?LoadFlag=3","window1");
  //
  //}
  var cContNo = fm.ProposalContNo.value;
  var cPrtNo = fm.PrtNo.value;
  var BankFlag = fm.all('SaleChnl').value;
  //alert("BankFlag="+BankFlag);

  window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}


//扫描件查询
function ScanQuery()
{

	  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

}


//体检资料查询
function showHealthQ()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthQMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

// 项目明细查询
function ItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolAddGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击明细查询按钮。" );
	else
	{
	    var cNo = PolAddGrid.getRowColData(tSel - 1,1);
	    var cSumGetMoney = 	PolAddGrid.getRowColData(tSel - 1,8);

		if (cNo == "")
		   {
		   	alert( "请先选择一条申请了承保项目的记录，再点击承保项目查询按钮。" );
		   	return;
		   }
		window.open("../sys/AllPBqItemQueryMain.jsp?No=" + cNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);

	}
}

//查看批单信息
function Prt()
{
	var tSel = PolAddGrid.getSelNo();
    if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击批单查看按钮。" );
	else
		{
			var cNo = PolAddGrid.getRowColData(tSel - 1,1);
			if (cNo == "")
		   {
			   	alert( "请先选择一条申请了承保项目的记录，再点击承保明细查询按钮。" );
			   	return;
		   }
			fm.all('No').value = PolAddGrid.getRowColData(tSel - 1,1);
			fm.all('PolNo').value = PolAddGrid.getRowColData(tSel - 1,2);

			var taction = parent.fraInterface.fm.action;
			var ttarget = parent.fraInterface.fm.target;
			parent.fraInterface.fm.action = "../f1printgrp/EndorsementF1P.jsp";
			parent.fraInterface.fm.target="f1print";
			fm.submit();

			parent.fraInterface.fm.action = taction;
			parent.fraInterface.fm.target=ttarget;
			fm.all('No').value = '';
			fm.all('PolNo').value = '';
		}
}


//体检资料
function showHealth()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//特约承保
function showSpec()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  tUWIdea = fm.all('UWIdea').value;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  if (cContNo != ""&& cPrtNo !="" && cMissionID != "" )
  {
  	window.open("./UWManuSpecMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//加费承保
function showAdd()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var cContNo=fm.ContNo.value;
  if (cContNo != "")
  {
	window.open("./UWManuAddMain.jsp?ContNo="+cContNo,"window1",sFeatures);
  }
  else
  {
  alert("请先选择保单!");
  }
}

//生存调查报告
function showRReport()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;

  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  window.open("./UWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window2",sFeatures);
  showInfo.close();
}

//核保报告书
function showReport()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;
  tUWIdea = fm.all('UWIdea').value;
  if (cContNo != "")
  {
  	window.open("../uwgrp/UWManuReportMain.jsp?ContNo="+cContNo,"window2",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//发核保通知书
function SendNotice()
{
  cContNo = fm.ContNo.value;
  fm.uwState.value = "8";

  if (cContNo != "")
  {
	//manuchk();
	 var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
     //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
 	var name='提示';   //网页名称，可为空; 
 	var iWidth=550;      //弹出窗口的宽度; 
 	var iHeight=250;     //弹出窗口的高度; 
 	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
 	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
 	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
 	showInfo.focus();
 	//[end]

	  var cMissionID =fm.MissionID.value;

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;
/*
	  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"
				 + " and LWMission.MissionProp2 = '"+ cContNo + "'"
				 + " and LWMission.ActivityID = '0000001105'"
				 + " and LWMission.ProcessID = '0000000003'"
				 + " and LWMission.MissionID = '" +cMissionID +"'";
				 */
				 var sqlid12="ManuUWInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId(sqlid12);
		mySql12.addSubPara(cPrtNo);  
		mySql12.addSubPara(cContNo); 
		mySql12.addSubPara(cMissionID); 
				 
				 
	turnPage.strQueryResult = easyQueryVer3(mySql12.getString(), 1, 1, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("不容许发放新的核保通知书,原因可能是:1.已发核保通知书,但未打印.2.未录入核保通知书内容.");
        fm.SubNoticeMissionID.value = "";
        return ;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    var tSubNoticeMissionID =   fm.SubNoticeMissionID.value ;
    if (cContNo != "" && cPrtNo != "" && cMissionID != "" && tSubNoticeMissionID != "" )
	  {
	  	fm.action = "./UWManuSendNoticeChk.jsp";
	    fm.submit();
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("请先选择保单!");
    }
  }
  else
  {
  	alert("请先选择保单!");
  }
}

function SendDanNotice()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //其他号码类型
  cCode="06";        //单据类型

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }

}
function SendRanNotice()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //其他号码类型
  cCode="00";        //单据类型

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }

}


function SendAddNotice()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="02"; //其他号码类型
  cCode="83";        //单据类型

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }

}

function SendHealth()
{
	//
	}
//发首交通知书
function SendFirstNotice()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalContNo.value;
  cOtherNoType="00"; //其他号码类型
  cCode="07";        //单据类型

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//发催办通知书
function SendPressNotice()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fm.ProposalNo.value;
  cOtherNoType="00"; //其他号码类型
  cCode="06";        //单据类型

  if (cProposalNo != "")
  {
  	showModalDialog("./UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  	 }
  else

  {
  	showInfo.close();
  	alert("请先选择保单!");
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

var withdrawFlag = false;
//撤单申请查询,add by Minim
function withdrawQueryClick() {
  withdrawFlag = true;
  easyQueryClick();
}

/*********************************************************************
 *  执行新契约人工核保的EasyQuery
 *  描述:查询显示对象是合同保单.显示条件:合同未进行人工核保，或状态为待核保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	// 初始化表格

	HideChangeResult();
	initPolGrid();
	divLCPol1.style.display= "";
    divLCPol2.style.display= "none";
    divMain.style.display = "none";

	// 书写SQL语句
	k++;
	var uwgradestatus = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //保单所处状态
	var strSQL = "";
	if (uwgradestatus == "1")//本级保单
	{/*
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
				 + " and LWMission.ProcessID = '0000000003' " //承保核保工作流
				 + " and LWMission.ActivityID = '0000001100' " //承保核保工作流中的待人工核保任务节点
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp2','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') = (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //集中权限管理体现
	*/
		var sqlid13="ManuUWInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName(sqlresourcename);
		mySql13.setSqlId(sqlid13);
		mySql13.addSubPara(k); 
		mySql13.addSubPara(k); 
		mySql13.addSubPara(fm.QPrtNo.value);  
		mySql13.addSubPara(fm.QAppntName.value); 
		mySql13.addSubPara(fm.QProposalNo.value); 
		mySql13.addSubPara(fm.QManageCom.value);  
		mySql13.addSubPara(mOperate); 
		mySql13.addSubPara(comcode); 
	
	
	
	
	strSQL = mySql13.getString();
	
	
	
	
	}
	else
	  if (uwgradestatus == "2")//下级保单
	  {
	  /*
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
				 + " and LWMission.ProcessID = '0000000003' " //承保核保工作流
				 + " and LWMission.ActivityID = '0000001100' " //承保核保工作流中的待人工核保任务节点
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') > (select AppGrade from LCCUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //集中权限管理体现
	   */
	   
	   	var sqlid14="ManuUWInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName(sqlresourcename);
		mySql14.setSqlId(sqlid14);
		mySql14.addSubPara(k); 
		mySql14.addSubPara(k); 
		mySql14.addSubPara(fm.QPrtNo.value);  
		mySql14.addSubPara(fm.QAppntName.value); 
		mySql14.addSubPara(fm.QProposalNo.value); 
		mySql14.addSubPara(fm.QManageCom.value);  
		mySql14.addSubPara(mOperate); 
		mySql14.addSubPara(comcode); 
	   
	   strSQL = mySql14.getString();
	   
	   
	   }
	   else //本级+下级保单
	   {
	   /*
		strSQL = "select LWMission.MissionProp1,LWMission.MissionProp2,LWMission.MissionProp7,LWMission.MissionProp5,LWMission.MissionProp8,LWMission.MissionID ,LWMission.SubMissionID from LWMission where "+k+"="+k
				 //+ " and VERIFYOPERATEPOPEDOM(LCPol.Riskcode,LCPol.Managecom,'"+comcode+"','Pe')=1"
				 + " and LWMission.ProcessID = '0000000003' " //承保核保工作流
				 + " and LWMission.ActivityID = '0000001100' " //承保核保工作流中的待人工核保任务节点
				 //+ getWherePart( 'LWMission.DefaultOperator','QOperator' )
				 + getWherePart( 'LWMission.MissionProp1','QPrtNo' )
				 + getWherePart( 'LWMission.MissionProp9','QAppntName' )
				 + getWherePart( 'LWMission.MissionProp3','QProposalNo')
				 + getWherePart( 'LWMission.MissionProp10','QManageCom' )
				 + " and ((select UWPopedom from LDUser where usercode = '"+mOperate+"') >= (select AppGrade from LccUWMaster where trim(ContNo) = LWMission.MissionProp2 ))"
				 + " and LWMission.MissionProp10 like '" + comcode + "%%'";  //集中权限管理体现
	
	*/
	var sqlid15="ManuUWInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName(sqlresourcename);
		mySql15.setSqlId(sqlid15);
		mySql15.addSubPara(k); 
		mySql15.addSubPara(k); 
		mySql15.addSubPara(fm.QPrtNo.value);  
		mySql15.addSubPara(fm.QAppntName.value); 
		mySql15.addSubPara(fm.QProposalNo.value); 
		mySql15.addSubPara(fm.QManageCom.value);  
		mySql15.addSubPara(mOperate); 
		mySql15.addSubPara(comcode); 
	   
	   strSQL = mySql15.getString();
	
	
	
	
	
	
	}

     //alert(strSQL);
     var tOperator = fm.QOperator.value;
	if(state == "1")
	{
	/*
			strSQL = strSQL + " and  LWMission.ActivityStatus = '1'"
		      + " and ( LWMission.DefaultOperator is null or LWMission.DefaultOperator = '" + tOperator + "')";
	*/
	var sqlid16="ManuUWInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename);
		mySql16.setSqlId(sqlid16);
		mySql16.addSubPara(tOperator); 
		
		strSQL = strSQL + " "+mySql16.getString()+" ";
	
	}
	if(state == "2")
	{/*
		strSQL = strSQL + " and  LWMission.ActivityStatus = '3'"
		      + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
		*/      
		      var sqlid17="ManuUWInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName(sqlresourcename);
		mySql17.setSqlId(sqlid17);
		mySql17.addSubPara(tOperator); 
		
		strSQL = strSQL +" "+ mySql17.getString()+" ";
	}
	if(state == "3")
	{/*
		strSQL = strSQL + " and  LWMission.ActivityStatus = '2'"
		    + "and LWMission.DefaultOperator = '" + tOperator + "'" ;
		*/    
		    var sqlid18="ManuUWInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName(sqlresourcename);
		mySql18.setSqlId(sqlid18);
		mySql18.addSubPara(tOperator); 
		
		strSQL = strSQL +" "+ mySql18.getString()+" ";
		    
	}


	if (withdrawFlag) {
	  //strSQL = strSQL + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0') ";
	  /*strSQL = "select LCPol.ProposalNo,LCPol.PrtNo,LMRisk.RiskName,LCPol.AppntName,LCPol.InsuredName "
           + " from LCPol,LCUWMaster,LMRisk where 10=10 "
           + " and LCPol.AppFlag='0'  "
           + " and LCPol.UWFlag not in ('1','2','a','4','9')  "
           + " and LCPol.grppolno = '00000000000000000000' and LCPol.contno = '00000000000000000000' "
           + " and LCPol.ProposalNo = LCPol.MainPolNo  and LCPol.ProposalNo= LCUWMaster.ProposalNo  "
           + " and LCUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') "
           + " and LCPol.ManageCom like '" + manageCom + "%%'"
           + " and LMRisk.RiskCode = LCPol.RiskCode "
           + getWherePart( 'LCUWMaster.Operator','QOperator')
           + " and LCPol.PrtNo in (select prtno from LCApplyRecallPol where ApplyType='0')";
*/
 var sqlid19="ManuUWInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName(sqlresourcename);
		mySql19.setSqlId(sqlid19);
		mySql19.addSubPara(mOperate); 
mySql19.addSubPara(manageCom);
mySql19.addSubPara(fm.QOperator.value);
strSQL = mySql19.getString();
	  withdrawFlag = false;
	}

	//execEasyQuery( strSQL );
	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有没通过承保核保的个人单！");
    return "";
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;
}

/*********************************************************************
 *  执行multline的点击事件
 *  描述:此处生成工作流的发体检、生调、核保通知书等结点
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryAddClick(parm1,parm2)
{
	// 书写SQL语句
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //投保单所处状态
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	if(fm.all(parm1).all('InpPolGridSel').value == '1' ){
		//当前行第1列的值设为：选中
		tProposalNo = fm.all(parm1).all('PolGrid2').value;
		tPNo = fm.all(parm1).all('PolGrid1').value;
	}

	fm.all('ContNo').value = fm.all(parm1).all('PolGrid2').value;
	fm.all('MissionID').value = fm.all(parm1).all('PolGrid6').value;
	fm.all('PrtNo').value = fm.all(parm1).all('PolGrid1').value;
	fm.all('SubMissionID').value = fm.all(parm1).all('PolGrid7').value;
	var ContNo = fm.all('ContNo').value;

	//alert("contno11="+fm.all('ContNo').value);


	if(state == "1")
	{
		checkDouble(tProposalNo);
	//生成工作流新节点
	}
	else
		{
			makeWorkFlow();
			}

	// 初始化表格
	initPolAddGrid();
	initPolBox();
	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";
	if (uwgrade == "1")
	{
	/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '已暂停' end  from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'";
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
				  */
	    var sqlid20="ManuUWInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName(sqlresourcename);
		mySql20.setSqlId(sqlid20);
		mySql20.addSubPara(k); 
        mySql20.addSubPara(k);
        mySql20.addSubPara(ContNo);
	
	strSQL = mySql20.getString();
	
	
	
	}
	else
	{/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '已暂停' end from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'";
*/
	 var sqlid21="ManuUWInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName(sqlresourcename);
		mySql21.setSqlId(sqlid21);
		mySql21.addSubPara(k); 
        mySql21.addSubPara(k);
        mySql21.addSubPara(ContNo);
	
	strSQL = mySql21.getString();
	
	
	
	}
	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有没通过承保核保的个人单！");
 		window.location.href("./ManuUWInput.jsp");
    return "";
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolAddGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("查询easyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+fm.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+fm.all('PrtNo').value);
  	var arrSelected = new Array();

//	strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	
	 var sqlid22="ManuUWInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName(sqlresourcename);
		mySql22.setSqlId(sqlid22);
		mySql22.addSubPara(ContNo); 

	
	
	turnPage.strQueryResult  = easyQueryVer3(mySql22.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('ProposalContNo').value = arrSelected[0][0];
	fm.all('PrtNo').value = arrSelected[0][1];
	fm.all('ManageCom').value = arrSelected[0][2];
	fm.all('SaleChnl').value = arrSelected[0][3];
	fm.all('AgentCode').value = arrSelected[0][4];
	fm.all('AgentGroup').value = arrSelected[0][5];
	fm.all('AgentCode1').value = arrSelected[0][6];
	fm.all('AgentCom').value = arrSelected[0][7];
	fm.all('AgentType').value = arrSelected[0][8];
	fm.all('ReMark').value = arrSelected[0][9];
/*
	strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag from lcappnt,LDPerson where contno='"+ContNo+"'"
		+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	*/
	var sqlid23="ManuUWInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId(sqlid23);
		mySql23.addSubPara(ContNo); 
	
	turnPage.strQueryResult  = easyQueryVer3(mySql23.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('AppntName').value = arrSelected[0][0];
	fm.all('AppntSex').value = arrSelected[0][1];
	fm.all('AppntBirthday').value = arrSelected[0][2];
	fm.all('AppntNo').value = arrSelected[0][3];
	fm.all('AddressNo').value = arrSelected[0][4];
	fm.all('VIPValue').value = arrSelected[0][5];
	fm.all('BlacklistFlag').value = arrSelected[0][6];

  return true;
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initPolGrid();

		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			}// end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//申请要人工核保保单
function checkDouble(tProposalNo)
{
	fm.PolNoHide.value = tProposalNo;
	fm.action = "./ManuUWApply.jsp";
	fm.submit();
}

//选择要人工核保保单
function getPolGridCho()
{
	//setproposalno();
	var cSelNo = PolAddGrid.getSelNo()-1;

	codeSql = "code";
	fm.UWPopedomCode.value = "";
	fm.action = "./ManuUWCho.jsp";
	fm.submit();
}

/*********************************************************************
 *  生成工作流后续结点
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function makeWorkFlow()
{
	fm.action = "./ManuUWChk.jsp";
	fm.submit();
	}

function checkBackPol(ProposalNo) {
  //var strSql = "select * from LCApplyRecallPol where ProposalNo='" + ProposalNo + "' and InputState='0'";
  
  var sqlid24="ManuUWInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName(sqlresourcename);
		mySql24.setSqlId(sqlid24);
		mySql24.addSubPara(ProposalNo); 
  
  var arrResult = easyExecSql(mySql24.getString());
  //alert(arrResult);

  if (arrResult != null) {
    return false;
  }
  return true;
}

//  初始化核保师是否已查看了相应的信息标记数组
function initFlag(  tlength )
{
	// 标记核保师是否已查看了相应的信息
      showPolDetailFlag =  new Array() ;
      showAppFlag = new Array() ;
      showHealthFlag = new Array() ;
      QuestQueryFlag = new Array() ;

     var i=0;
	  for( i = 0; i < tlength ; i++ )
		{
			showPolDetailFlag[i] = 0;
			showAppFlag[i] = 0;
			showHealthFlag[i] = 0;
			QuestQueryFlag[i] = 0;
		}

	}
//下核保结论
function manuchk()
{

	flag = fm.all('UWState').value;
	var ProposalNo = fm.all('ProposalNo').value;
	var MainPolNo = fm.all('MainPolNoHide').value;

	if (trim(fm.UWState.value) == "") {
    alert("必须先录入核保结论！");
    return;
  }

	if (!checkBackPol(ProposalNo)) {
	  if (!confirm("该投保单有撤单申请，继续下此结论吗？")) return;
	}

    if (trim(fm.UWState.value) == "6") {
      if(trim(fm.UWPopedomCode.value) !="")
         fm.UWOperater.value = fm.UWPopedomCode.value
      else
         fm.UWOperater.value = operator;
}

    var tSelNo = PolAddGrid.getSelNo()-1;

	if(fm.State.value == "1"&&(fm.UWState.value == "1"||fm.UWState.value == "2"||fm.UWState.value =="4"||fm.UWState.value =="6"||fm.UWState.value =="9"||fm.UWState.value =="a")) {
      if( showPolDetailFlag[tSelNo] == 0 || showAppFlag[tSelNo] == 0 || showHealthFlag[tSelNo] == 0 || QuestQueryFlag[tSelNo] == 0 ){
         var tInfo = "";
         if(showPolDetailFlag[tSelNo] == 0)
            tInfo = tInfo + " [投保单明细信息]";
         if(showAppFlag[tSelNo] == 0)
            tInfo = tInfo + " [既往投保信息]";
         if( PolAddGrid.getRowColData(tSelNo,1,PolAddGrid) == PolAddGrid.getRowColData(tSelNo ,2,PolAddGrid)) {
         	if(showHealthFlag[tSelNo] == 0)
              tInfo = tInfo + " [体检资料录入]";
         }
         if(QuestQueryFlag[tSelNo] == 0)
            tInfo = tInfo + " [问题件查询]";
         if ( tInfo != "" ) {
         	tInfo = "有重要信息:" + tInfo + " 未查看,是否要下该核保结论?";
         	if(!window.confirm( tInfo ))
         	    return;
             }

        }
     }
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

	fm.action = "./UWManuNormChk.jsp";
	fm.submit();

	if (flag != "b"&&ProposalNo == MainPolNo)
	{
		initInpBox();
   		initPolBox();
		initPolGrid();
		initPolAddGrid();
	}
}

//function manuchk()
//{
//
//	flag = fm.all('UWState').value;
//	tUWIdea = fm.all('UWIdea').value;
//
//	//录入承保计划变更结论要区分主附险
//	if( flag == "b")
//	{
//		cProposalNo=fm.PolNoHide.value;
//	}
//	else
//	{
//		cProposalNo=fm.ProposalNo.value;
//	}
//
//	//alert("flag:"+flag);
//	if (cProposalNo == "")
//	{
//		alert("请先选择保单!");
//	}
//	else
//	{
//		if (flag == "0"||flag == "1"||flag == "4"||flag == "6"||flag == "9"||flag == "b")
//		{
//			showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//		}
//
//		if (flag == "2") //延期
//		{
//			//showModalDialog("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuDateMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//
//		if (flag == "3") //条件承保
//		{
//			//showModalDialog("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
//			window.open("./UWManuSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,"window1");
//		}
//		if (flag == "7") //问题件录入
//		{
//			QuestInput();
//		}
//
//		if (flag != "b")
//		{
//		initInpBox();
//    		initPolBox();
//		initPolGrid();
//		initPolAddGrid();
//		}
//	}
//}

//问题件录入
function QuestInput()
{
	cContNo = fm.ContNo.value;  //保单号码

	//var strSql = "select * from LCcUWMaster where ContNo='" + cContNo + "' and ChangePolFlag ='1'";
    //alert(strSql);
    
      var sqlid25="ManuUWInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId(sqlid25);
		mySql25.addSubPara(cContNo); 
    
    var arrResult = easyExecSql(mySql25.getString());
       //alert(arrResult);
    if (arrResult != null) {
      var tInfo = "承保计划变更未回复,确认要录入新的问题件?";
   if(!window.confirm( tInfo )){
          return;
        }
      }
	window.open("./QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window1",sFeatures);

}

//问题件回复
function QuestReply()
{
	cProposalNo = fm.ProposalNo.value;  //保单号码

	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestReplyMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);

	initInpBox();
    initPolBox();
	initPolGrid();

}

//问题件查询
function QuestQuery()
{
	cContNo = fm.ContNo.value;  //保单号码


  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("请先选择保单!");
  }

}

//生存调查报告查询
function RReportQuery()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]


  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;


  if (cContNo!= ""  )
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("./RReportQueryMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();

  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}

//保单撤销申请查询
function BackPolQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码


  if (cProposalNo != "")
  {
	window.open("./BackPolQueryMain.jsp?ProposalNo1="+cProposalNo,sFeatures);
  }
  else
  {
  	alert("请先选择保单!");
  }
}

//催办超时查询
function OutTimeQuery()
{
  cProposalNo = fm.ProposalNo.value;  //保单号码


  if (cProposalNo != "")
  {
	window.open("./OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo,sFeatures);
  }
  else
  {
  	alert("请先选择保单!");
  }
}

//保险计划变更
function showChangePlan()
{
	/*
  cProposalNo = fm.ProposalNo.value;  //保单号码
  cPrtNo = fm.PrtNoHide.value; //印刷号
  var cType = "ChangePlan";
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );

  if (cProposalNo != ""&&cPrtNo != "")
  {
  	 var strSql = "select * from LCIssuepol where ProposalNo='" + cProposalNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
     var arrResult = easyExecSql(strSql);
     if (arrResult != null) {
       var tInfo = "有未回复的问题件,确认要进行承保计划变更?";
       if(!window.confirm( tInfo ))
         	    return;
      }
    window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=3&Type="+cType+"&prtNo="+cPrtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");

   }
  else
  {
  	alert("请先选择保单!");
  }
  */

  var cPrtNo = fm.PrtNo.value;
  
  var strSql="";
 // strSql="select salechnl from lccont where contno='"+cPrtNo+"'";
  
  var sqlid26="ManuUWInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName(sqlresourcename);
		mySql26.setSqlId(sqlid26);
		mySql26.addSubPara(cPrtNo); 
  
  arrResult = easyExecSql(mySql26.getString());
  var BankFlag = arrResult[0][0];
  

  window.open("../appgrp/ProposalEasyScan.jsp?LoadFlag=25&prtNo="+cPrtNo+"&BankFlag="+BankFlag, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1",sFeatures);
}

//保险计划变更结论录入显示
function showChangeResultView()
{
	var cContNo = fm.ContNo.value;
	//var strSql = "select changepolreason from LCcUWMaster where ContNo='" + cContNo + "' ";
  //  alert(strSql);
  
  var sqlid27="ManuUWInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName(sqlresourcename);
		mySql27.setSqlId(sqlid27);
		mySql27.addSubPara(cContNo); 
  
  var arrResult = easyExecSql(mySql27.getString());
 
	fm.ChangeIdea.value = arrResult[0][0];
	//fm.PolNoHide.value = fm.ProposalNo.value;  //保单号码
	
	divUWResult.style.display= "none";
	divChangeResult.style.display= "";
}


//显示保险计划变更结论
function showChangeResult()
{
	
	fm.uwState.value = "b";
	fm.UWIdea.value = fm.ChangeIdea.value;
	var cContNo = fm.ProposalContNo.value;

	cChangeResult = fm.UWIdea.value;

	
	
	
	//	var strSql = "select * from LCIssuepol where ContNo='" + cContNo + "' and (( backobjtype in('1','4') and replyresult is null) or ( backobjtype in('2','3') and needprint = 'Y' and replyresult is null))";
      
      
       var sqlid28="ManuUWInputSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName(sqlresourcename);
		mySql28.setSqlId(sqlid28);
		mySql28.addSubPara(cContNo); 
      
        var arrResult = easyExecSql(mySql28.getString());
        //alert(arrResult);
       if (arrResult != null) {
       var tInfo = "有未回复的问题件,确认要进行承保计划变更?";
       if(!window.confirm( tInfo )){
       	      HideChangeResult()
         	    return;
          }
       }

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  	 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    
  	fm.action = "./UWManuNormChk.jsp";
  
  	fm.submit(); //提交
  
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}

//隐藏保险计划变更结论
function HideChangeResult()
{
	divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	fm.uwState.value = "";
	fm.UWIdea.value = "";
	fm.UWPopedomCode.value = "";
}


function cancelchk()
{
	fm.all('uwState').value = "";
	fm.all('UWPopedomCode').value = "";
	fm.all('UWIdea').value = "";
}

function setproposalno()
{
	var count = PolGrid.getSelNo();
	fm.all('ProposalNo').value = PolGrid.getRowColData(count - 1,1,PolGrid);
}

//附件险按钮隐藏函数
function hideAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "none";
	parent.fraInterface.divAddButton2.style.display= "none";
	parent.fraInterface.divAddButton3.style.display= "none";
	parent.fraInterface.divAddButton4.style.display= "none";
	parent.fraInterface.divAddButton5.style.display= "none";
	parent.fraInterface.divAddButton6.style.display= "none";
	parent.fraInterface.divAddButton7.style.display= "none";
	parent.fraInterface.divAddButton8.style.display= "none";
	parent.fraInterface.divAddButton9.style.display= "none";
	parent.fraInterface.divAddButton10.style.display= "none";
	//parent.fraInterface.fm.UWState.CodeData = "0|^4|通融承保^9|正常承保";
	//parent.fraInterface.UWResult.innerHTML="核保结论<Input class=\"code\" name=UWState CodeData = \"0|^4|通融/条件承保^9|正常承保\" ondblclick= \"showCodeListEx('UWState1',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState1',[this,''],[0,1]);\">";


}

//显示隐藏按钮
function showAddButton()
{
	parent.fraInterface.divAddButton1.style.display= "";
	parent.fraInterface.divAddButton2.style.display= "";
	parent.fraInterface.divAddButton3.style.display= "";
	parent.fraInterface.divAddButton4.style.display= "";
	parent.fraInterface.divAddButton5.style.display= "";
	parent.fraInterface.divAddButton6.style.display= "";
	parent.fraInterface.divAddButton7.style.display= "";
	parent.fraInterface.divAddButton8.style.display= "";
	parent.fraInterface.divAddButton9.style.display= "";
	parent.fraInterface.divAddButton10.style.display= "";
	//parent.fraInterface.UWResult.innerHTML="核保结论<Input class=\"code\" name=UWState CodeData = \"0|^1|拒保^2|延期^4|通融/条件承保^6|待上级核保^9|正常承保^a|撤销投保单\" ondblclick= \"showCodeListEx('UWState',[this,''],[0,1]);\" onkeyup=\"showCodeListKeyEx('UWState',[this,''],[0,1]);\">";
	//parent.fraInterface.fm.UWState.CodeData = "0|^1|拒保^2|延期^4|通融承保^6|待上级核保^9|正常承保^a|撤销投保单";
}

function showNotePad() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWNotePadMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&OperatePos=3", "window1",sFeatures);
  }
}

function InitClick(){
	location.href  = "./ManuUWAll.jsp?type=1";
}

/*********************************************************************
 *  进入被保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredInfo()
{
	  var tSelNo = PolAddGrid.getSelNo();
		var tContNo = fm.ContNo.value;
		var tInsuredNo = PolAddGrid.getRowColData(tSelNo - 1,1);
		var tMissionID = fm.MissionID.value;
		var tSubMissionID = fm.SubMissionID.value;
		var tPrtNo = fm.PrtNo.value;
		var tSendFlag = fm.SendFlag.value;
		window.open("./InsuredUWInfoMain.jsp?ScanFlag=0&ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&PrtNo="+tPrtNo+"&SendFlag="+tSendFlag, "", sFeatures);
}

/*********************************************************************
 *  进入系统后
 *  描述:此处生成工作流的发体检、生调、核保通知书等结点
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initUW()
{
	//alert("ok");
        //add by yaory for init 再 保 呈 报 button
//        alert(MissionID);
//        alert(SubMissionID);
//       alert(ActivityID);
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001100' and submissionid='"+SubMissionID+"'";
//var DistriMark = easyExecSql(strSql);
//alert(strSql);
//alert(DistriMark);
//alert("ok");

//
//strSql="select healthyamntfb('"+peop+"','','1') from dual";
//var life=easyExecSql(strSql);
//strSql="select healthyamntfb('"+peop+"','','7') from dual";
//var acci=easyExecSql(strSql);
//strSql="select healthyamntfb('"+peop+"','','2') from dual";
//var nur=easyExecSql(strSql);
//var add=life+acci;
////alert(add);
////alert(nur);
////return;
//var re;
//var fb="0";
//if(add>3000000 || nur>1200000)
//{
//	fb="1";
//}
//if(add>500000 || nur>100000)
//{
//	re="1";
//}else{
//re="0";
//}
//alert("ok");
//alert(strSql);
//if(DistriMark!="1")
//{
//   //fm.ReDistribute.style.display='none';
//   fm.ReDistribute.disabled=true;
//}
	// 书写SQL语句
	k++;
	var uwgrade = fm.UWGradeStatus.value;
	var mOperate = fm.Operator.value;
	var state = fm.State.value;       //投保单所处状态
	var tProposalNo = "";
	var tPNo = "";
	var strSQL = "";

	fm.all('ContNo').value = ContNo;
	fm.all('MissionID').value = MissionID;
	fm.all('PrtNo').value = PrtNo;
	fm.all('SubMissionID').value = SubMissionID;
  makeWorkFlow();

	// 初始化表格
	initPolAddGrid();
	initPolBox();
	initSendTrace();

	DivLCContInfo.style.display = "none";
	divLCPol1.style.display= "none";
	divSearch.style.display= "none";
	divLCPol2.style.display= "";
	divMain.style.display = "";
	DivLCContButton.style.display="";
	DivLCCont.style.display="";
	DivLCAppntInd.style.display="";
	DivLCAppntIndButton.style.display="";

	if (uwgrade == "1")
	{
	/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'"
				  + " order by LCInsured.SequenceNo "
				  ;
				  //+ " union"
				  //+ " select '  ',LCPol.PolNo,LCPol.PrtNo,LCPol.RiskCode,LCPol.AppntName,LCPol.InsuredName,' ',' ' from LCPol where "+k+"="+k
				  //+ " and LCPol.prtno = (select prtno from lcpol where polno ='" + tProposalNo + "')"
				  //+ " and LCPol.polno <> '" + tProposalNo + "'"
				  //+ " order by 2 ";
				  
		*/		  
				    var sqlid29="ManuUWInputSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName(sqlresourcename);
		mySql29.setSqlId(sqlid29);
		mySql29.addSubPara(k); 
		mySql29.addSubPara(k);
		mySql29.addSubPara(ContNo);
		strSQL =mySql29.getString();		  
				  
	}
	else
	{
	/*
		strSQL = "select LCInsured.InsuredNo,LCInsured.Name,(select codename from ldcode where code = LCInsured.IDType and codetype = 'idtype'),LCInsured.IDNo from LCInsured where "+k+"="+k
				  + " and LCInsured.Contno = '" + ContNo + "'"
				  + " order by LCInsured.SequenceNo "
				  ;
		*/		  
				  
				    var sqlid30="ManuUWInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName(sqlresourcename);
		mySql30.setSqlId(sqlid30);
		mySql30.addSubPara(k); 
		mySql30.addSubPara(k);
		mySql30.addSubPara(ContNo);
		strSQL =mySql30.getString();	

	}

	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("没有未通过新契约核保的个人合同单！");
    return;
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolAddGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //alert("查询easyQueryAddClick"+arrDataSet.length);
  initFlag(  arrDataSet.length );

  //alert("contno21="+fm.all(parm1).all('PolGrid2').value);
  //alert("PrtNo="+fm.all('PrtNo').value);
  	var arrSelected = new Array();

	//strSQL = "select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,ReMark from lccont where contno='"+ContNo+"'";
	
	var sqlid31="ManuUWInputSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName(sqlresourcename);
		mySql31.setSqlId(sqlid31);

		mySql31.addSubPara(ContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql31.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('ProposalContNo').value = arrSelected[0][0];
	fm.all('PrtNo').value = arrSelected[0][1];
	fm.all('ManageCom').value = arrSelected[0][2];
	fm.all('SaleChnl').value = arrSelected[0][3];
	fm.all('AgentCode').value = arrSelected[0][4];
	fm.all('AgentGroup').value = arrSelected[0][5];
	fm.all('AgentCode1').value = arrSelected[0][6];
	fm.all('AgentCom').value = arrSelected[0][7];
	fm.all('AgentType').value = arrSelected[0][8];
	fm.all('ReMark').value = arrSelected[0][9];

	//strSQL = "select AppntName,AppntSex,AppntBirthday,AppntNo,AddressNo,VIPValue,BlacklistFlag,ldperson.OccupationCode,ldperson.OccupationType,lcappnt.NativePlace from lcappnt,LDPerson where contno='"+ContNo+"'"
	//	+ "and LDPerson.CustomerNo = LCAppnt.AppntNo";
	
	var sqlid32="ManuUWInputSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName(sqlresourcename);
		mySql32.setSqlId(sqlid32);
		mySql32.addSubPara(ContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql32.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	fm.all('AppntName').value = arrSelected[0][0];
	fm.all('AppntSex').value = arrSelected[0][1];
	fm.all('AppntBirthday').value = calAge(arrSelected[0][2]);
	fm.all('AppntNo').value = arrSelected[0][3];
	fm.all('AddressNo').value = arrSelected[0][4];
	fm.all('VIPValue').value = arrSelected[0][5];
	fm.all('BlacklistFlag').value = arrSelected[0][6];

	fm.all('OccupationCode').value = arrSelected[0][7];
	fm.all('OccupationType').value = arrSelected[0][8];
	fm.all('NativePlace').value = arrSelected[0][9];


	//查询最新的核保结论，如果有则显示
	//strSQL="select PassFlag,UWIdea from lccuwmaster where ContNo='"+ContNo+"'";
	
	var sqlid33="ManuUWInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId(sqlid33);
		mySql33.addSubPara(ContNo);
	
	var arr=easyExecSql(mySql33.getString());
	//alert(strSQL);
	//alert(arr);
	//alert(arr[0][1]);
	if(arr!=null){
		fm.all('uwState').value=arr[0][0];
		fm.all('UWIdea').value=arr[0][1];
	}

	//判断核保上报类型显示界面的不同div

  if(ReportFlag==""||ReportFlag==null){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		fm.button1.style.display="";
		fm.button2.style.display="none";
  }
	else if(ReportFlag=="1"){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		//疑难案例核保流向默认为返回下级
		fm.all('uwUpReport').value="4";
		fm.button1.style.display="none";
		fm.button2.style.display="";
	}
	else if(ReportFlag=="2"){
		divSugUWResult.style.display="none";
		divUWResult.style.display="";
		fm.all('uwUpReport').value="0";
		fm.button1.style.display="";
		fm.button2.style.display="none";
	}
	
	//再保判断 yaory
//strSql = "select missionprop16 from lwmission a where a.missionid='"+MissionID+"' and a.activityid='"+ActivityID+"' and submissionid='"+SubMissionID+"'";

var sqlid34="ManuUWInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName(sqlresourcename);
		mySql34.setSqlId(sqlid34);
		mySql34.addSubPara(MissionID);
		mySql34.addSubPara(ActivityID);
		mySql34.addSubPara(SubMissionID);

var DistriMark = easyExecSql(mySql34.getString());//再保标志
//alert(DistriMark);
//strSql = "select * from lwmission a where a.missionid='"+MissionID+"' and a.activityid='0000001140' and submissionid='"+SubMissionID+"'"; 

var sqlid35="ManuUWInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName(sqlresourcename);
		mySql35.setSqlId(sqlid35);
		mySql35.addSubPara(MissionID);
		mySql35.addSubPara(SubMissionID);


var rem=easyExecSql(mySql35.getString());	
//alert(rem);
//alert(fm.uwUpReport.value);
//还有一个评点
//alert(ContNo);
//return;
//strSql="select SuppRiskScore from lcprem where contno='"+ContNo+"' and PayPlanType='01'";

var sqlid36="ManuUWInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName(sqlresourcename);
		mySql36.setSqlId(sqlid36);
		mySql36.addSubPara(ContNo);


var addremark=easyExecSql(mySql36.getString());
//累计保额
var peop=PolAddGrid.getRowColData(0,1);

//strSql="select healthyamntfb('"+peop+"','','1') from dual";

var sqlid37="ManuUWInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName(sqlresourcename);
		mySql37.setSqlId(sqlid37);
		mySql37.addSubPara(peop);
		
var life=easyExecSql(mySql37.getString());
//strSql="select healthyamntfb('"+peop+"','','7') from dual";

var sqlid38="ManuUWInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName(sqlresourcename);
		mySql38.setSqlId(sqlid38);
		mySql38.addSubPara(peop);

var acci=easyExecSql(mySql38.getString());
//strSql="select healthyamntfb('"+peop+"','','2') from dual";

var sqlid39="ManuUWInputSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName(sqlresourcename);
		mySql39.setSqlId(sqlid39);
		mySql39.addSubPara(peop);

var nur=easyExecSql(mySql39.getString());
var add=life+acci;
//alert(add);
//alert(nur);
//return;
var re;
var fb="0";
if(add>3000000 || nur>1200000)
{
	fb="1";
}
if(add>500000 || nur>100000)
{
	re="1";
}else{
re="0";
}
//alert(DistriMark);
//alert(addremark);
//alert(fb);
//alert(rem);
if(rem==null)
{
	//alert("okk");
if(DistriMark==1 || (addremark!=null && addremark>100 && re==1) || fb=="1")
{
	
  fm.ReDistribute.disabled='';	
	
}else{
   fm.ReDistribute.disabled=true;
}
}else{

	fm.ReDistribute.disabled=true;
}

  return true;
}

/*********************************************************************
 *  初始化是否上报核保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initSendTrace()
{
	var tSql = "";
/*
	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
					+ " and otherno = '"+ContNo+"'"
					+ " and othernotype = '1'"
					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+ContNo+"')"
					;
	*/
	var sqlid40="ManuUWInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName(sqlresourcename);
		mySql40.setSqlId(sqlid40);
		mySql40.addSubPara(ContNo);
		mySql40.addSubPara(ContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql40.getString(), 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //判断是否查询成功
  if (turnPage.strQueryResult) {
		fm.all('SendFlag').value =arrSelected[0][0];
		fm.all('SendUWFlag').value =arrSelected[0][1];
		fm.all('SendUWIdea').value =arrSelected[0][2];
		DivLCSendTrance.style.display="";
	}

    divUWSave.style.display = "";
    divUWAgree.style.display = "none";

    /**
	if(fm.all('SendFlag').value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}
	else if(fm.all('SendFlag').value == '1')
	{
		divUWAgree.style.display = "";
		divUWSave.style.display = "none";
		//divUWButton2.style.display = "none";
		tSql = "select sugpassflag,suguwidea from lccuwmaster where 1=1 "
					+ " and contno = '"+ContNo+"'"
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

		fm.all('uwState').value = arrSelected[0][0];
		fm.all('UWIdea').value = arrSelected[0][1];
	}
    **/
}

/*********************************************************************
 *  告知整理
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ImpartToICD()
{
  var cContNo = fm.ContNo.value;//PolAddGrid.getRowColData(PolAddGrid.getSelNo()-1, 1);
  var PrtNo = fm.PrtNo.value;

  if (cContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/ImaprtToICDMain.jsp?ContNo="+cContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
  else {
  	alert("请先选择保单!");
  }
}
//发问题件通知书
function SendIssue()
{
  cContNo = fm.ContNo.value;

  if (cContNo != "")
  {
	//manuchk();
	 var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
     //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
 	var name='提示';   //网页名称，可为空; 
 	var iWidth=550;      //弹出窗口的宽度; 
 	var iHeight=250;     //弹出窗口的高度; 
 	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
 	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
 	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
 	showInfo.focus();
 	//[end]

	  var cMissionID =fm.MissionID.value;

	  var cPrtNo = fm.PrtNo.value;

	  fm.PrtNoHide.value = cPrtNo ;

	 // strsql = "select * from lcissuepol where contno = '" +cContNo+ "' and backobjtype = '3' and (state = '0' or state is null)";

var sqlid41="ManuUWInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName(sqlresourcename);
		mySql41.setSqlId(sqlid41);
		mySql41.addSubPara(cContNo);

	  turnPage.strQueryResult = easyQueryVer3(mySql41.getString(), 1, 1, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("不容许发放新的问题件通知单");
        fm.SubNoticeMissionID.value = "";
        return ;
    }

   // strsql = "select submissionid from lwmission where missionprop2 = '" +cContNo+ "'and missionprop1 = '"+cPrtNo+"' and activityid = '0000001022'";
   
   var sqlid42="ManuUWInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName(sqlresourcename);
		mySql42.setSqlId(sqlid42);
		mySql42.addSubPara(cContNo);
			mySql42.addSubPara(cPrtNo);
   
    turnPage.strQueryResult = easyQueryVer3(mySql42.getString(), 1, 1, 1);

        if (!turnPage.strQueryResult) {
    	showInfo.close();
     	alert("不容许发放新的问题件通知单");
        fm.SubNoticeMissionID.value = "";
        return ;
    }

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

    fm.SubNoticeMissionID.value = turnPage.arrDataCacheSet[0][0];
    fm.action = 'UWManuSendIssueCHK.jsp';
    fm.submit();
  }
  else
  {
  	alert("请先选择保单!");
  }
}
//问题件回销
function QuestBack()
{
		cContNo = fm.ContNo.value;  //保单号码

  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("./UWManuQuestQMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("请先选择保单!");
  }
}

/*********************************************************************
 *  显示核保级别、核保师
 *  add by xutao 2005-04-13
 *
 *********************************************************************
 */
function showUpDIV(){

    if (fm.uwUpReport.value == '1' || fm.uwUpReport.value == '3')
    {
        //显示
        //divUWup.style.display="";

    }
    else if (fm.uwUpReport.value == '2' || fm.uwUpReport.value == '4')
    {   //隐藏
        //divUWup.style.display="none";
        //fm.uwPopedom.value = "";
        //fm.uwPopedomname.value = "";
        //fm.uwPer.value = "";
        //fm.uwPername.value = "";
    }

}
/*********************************************************************
 *  显示暂收费信息
 *  add by heyq 2005-04-13
 *
 *********************************************************************
 */
function showTempFee()
{
	window.open("./UWTempFeeQryMain.jsp?PrtNo="+fm.PrtNo.value+"&AppntNo="+fm.all('AppntNo').value+"&AppntName="+fm.all('AppntName').value,"window11",sFeatures);
}

function SendAllNotice()
{

	window.open("./SendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID,"window1",sFeatures);
}

//查询体检结果
function queryHealthReportResult(){
	var tContNo = fm.ContNo.value;
	var tFlag = "1";

 window.open("./UWManuHealthQMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1",sFeatures);
}


//查询生调结果
function queryRReportResult(){

	var tContNo = fm.ContNo.value;
	var tFlag = "1";

 window.open("./RReportQueryMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1",sFeatures);

}

//保额累计
function amntAccumulate(){
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//已承保查询
function queryProposal(){

	window.open("./ProposalQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}


//未承保查询
function queryNotProposal(){

	window.open("./NotProposalQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//既往理赔查询
function queryClaim(){

	window.open("./ClaimQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//既往保全查询
function queryEdor(){

	window.open("./EdorQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//客户品质管理
function CustomerQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWCustomerQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}

//业务员品质管理
function AgentQuality() {

  if (ContNo!="" && PrtNo!="") {
  	window.open("../uwgrp/UWAgentQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}

//健康告知查询
function queryHealthImpart(){

	window.open("./HealthImpartQueryMain.jsp?CustomerNo="+fm.all('AppntNo').value,"window1",sFeatures);
}

//操作履历查询
function QueryRecord(){

	window.open("./RecordQueryMain.jsp?ContNo="+fm.all('ContNo').value,"window1",sFeatures);
}

function showBeforeHealthQ()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;


	window.open("../uwgrp/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);

showInfo.close();

}

//核保查询
function UWQuery(){

	window.open("./UWQueryMain.jsp?ContNo="+fm.all('ContNo').value,"window1",sFeatures);
}

/*********************************************************************
 *  修改保单生效日期
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function ChangeCvalidate()
 {
 	var tContNo = fm.ContNo.value;

 window.open("./UWChangeCvalidateMain.jsp?ContNo="+tContNo,"window1",sFeatures);

}


/*********************************************************************
 *  再保承保录入
 *  add by zhangxing 2005-06-28
 *
 *********************************************************************
 */
 function UWUpReport()
 {
 		var tContNo = fm.ContNo.value;
 		var tMissionID = fm.MissionID.value;
 		var tSubMissionID = fm.SubMissionID.value;

 window.open("./UWManuUpReportMain.jsp?ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID,"window1",sFeatures);
 }
 
 
 /*********************************************************************
 *  再保信息回复
 *  add by zhangxing 2005-08-10
 *
 *********************************************************************
 */
function showUpReportReply()
{
	  var tContNo = fm.ContNo.value;
 		var tMissionID = fm.MissionID.value;
 		var tSubMissionID = fm.SubMissionID.value;
 		var tFlag = "1";
 		var tPrtNo = fm.PrtNo.value;
 	
	window.open("./UWManuUpReportReplyMain.jsp?ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Flag="+tFlag,"window1",sFeatures);
}
