//程序名称：SendAllnotice.js
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage  = new turnPageClass();
var turnPage1 = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
	 var i = 0;
     var showStr ="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
     var urlStr ="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
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
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
      showInfo.close();
       
  }
  else
  { 
	  var showStr="操作成功";
  	  showInfo.close();
  	  alert(showStr);
  	  top.close();
  }
}

function afterCodeSelect( cCodeName, Field )
{

	var tCode = fm.NoticeType.value;
    if(tCode=="LP86")
	{
        //2006-02-09 Modify by zhaorx		
	 	var tCaseNo = fm.ClmNo.value; //赔案号
	 	var tContNo = fm.ContNo.value;//合同号
	 	var tFoundContent = "";       //查到的附加险拒保延期信息
	 	var i=0;
	 	var tSQLF = "";//SQL First 
	 	
//	 	tSQLF = " select passflag,(select riskstatname from lmrisk where exists (select 'X' from lcpol where lmrisk.riskcode = lcpol.riskcode and lcpol.polno = lluwmaster.polno )) "
//              + " from lluwmaster where 1=1 and passflag in ('1', '2') and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"' order by passflag ";
	 	
		var sqlid0="LLUWPSendAllSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(tCaseNo);//指定传入的参数
		mySql0.addSubPara(tContNo);//指定传入的参数
		tSQLF=mySql0.getString();
	 	
	 	var tReusltF = new Array();
	 	tReusltF = easyExecSql(tSQLF);
	 	if(!tReusltF)
	    {	
	    	alert("没有要发送的拒保延期附加险通知书！"); 	    	
	    	return;
	    }
	    if(tReusltF.length != 0)
	    {	
	       for(i=1;i<=tReusltF.length;i++)
	       {  
	       	  if(tReusltF[i-1][0]=='1')//拒保
	       	  {
	       	  	tFoundContent = tFoundContent + "，" + "拒保" + tReusltF[i-1][1];
	       	  }
	       	  if(tReusltF[i-1][0]=='2')//延期
	       	  { 
	       	  	tFoundContent = tFoundContent + "，" + "延期" + tReusltF[i-1][1];
	       	  }
	       }
		    divUWSpec1.style.display = 'none';
			divUWSpec.style.display = 'none';
			divnoticecontent.style.display = 'none';//隐藏通知书内容
			document.all.Content.value = "经核保后" + tFoundContent + "。";
	    } 
	}
	
else if(tCode=="LP81")
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		document.all.Content.value = "";
	}
	
else if(tCode=="LP89")
	{
		divnoticecontent.style.display = 'none';
		divUWSpec1.style.display = '';
		divUWSpec.style.display = '';
		fm.Content.value = "";
	}
    else
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
	}
}

function initLWMission()
{
	var MissionID = fm.MissionID.value;
//	var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//			   + " and activityid = '0000001270'"
			   
	var sqlid1="LLUWPSendAllSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(MissionID);//指定传入的参数
	var strSQL=mySql1.getString();
								
    var brr = easyExecSql(strSQL);
	if ( brr )  //已经申请保存过
 	{
 		var tSubMissionID = brr[0][0];
 		fm.SubMissionID.value = tSubMissionID;
	}

}


function initLoprtManager()
{
	  var tContNo = document.all('ContNo').value;
//	  var strSQL  = "select prtseq,otherno,othernotype,agentcode,code,stateflag from loprtmanager where otherno = '"+tContNo+"'"
//	              + " and Code in ('LP00','LP06','LP81','LP82','LP83','LP86','LP88','LP89')"
//	              + " and (oldprtseq is null or prtseq = oldprtseq) ";//Modify by zhaorx 2006-09-01
	  
		var sqlid2="LLUWPSendAllSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(tContNo);//指定传入的参数
		var strSQL=mySql2.getString();
	  
	  //查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	    
	  initPolGrid();
	
	  //判断是否查询成功
	  if (!turnPage.strQueryResult)
	  {
		  	PolGrid.clearData();
		  	alert("数据库中没有满足条件的数据！");
		    return false;
	  }
 
	  //查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	 
	  //设置初始化过的MULTILINE对象
	  turnPage.pageDisplayGrid = PolGrid;   
	         
	  //保存SQL语句
	  turnPage.strQuerySql   = strSQL;
	 
	  //设置查询起始位置
	  turnPage.pageIndex = 0; 
	 
	  //在查询结果数组中取出符合页面显示大小设置的数组
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
	 
	  //调用MULTILINE对象显示查询结果
	      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

	        
}


/*=========================================================================
 * 修改原因：在发起“不自动续保通知书”的时候，首先判断一下是否对附加险下了
 *           不自动续保结论，否则不允许发“不自动续保通知书”
 * 修改人：  万泽辉
 * 修改时间：2005/12/13/10:50
 =========================================================================
 */
 function CheckBXBNotice()
 {
 	 var tCaseNo = fm.ClmNo.value; //赔案号
 	 var tContNo = fm.ContNo.value;//合同号
 	 
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = 'b' and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid3="LLUWPSendAllSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(tCaseNo);//指定传入的参数
		mySql3.addSubPara(tContNo);//指定传入的参数
		strSQL=mySql3.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("你还没有对任何附加险下过“不自动续保”的核保结论！");
 	 	 return false;
 	 }
 	 return true;
 }
 
 /*=========================================================================
 * 修改原因：在发起“拒保通知书”的时候，首先判断一下是否对主险或者附加险下了
 *           拒保结论，否则不允许发“拒保通知书”
 * 修改人：  万泽辉
 * 修改时间：2005/12/14/14:50
 =========================================================================
 */
 function CheckJBNotice()
 {
 	 var tContNo = fm.ContNo.value;//合同号
     var tCaseNo = fm.ClmNo.value; //赔案号
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '1'  and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid4="LLUWPSendAllSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(tCaseNo);//指定传入的参数
		mySql4.addSubPara(tContNo);//指定传入的参数
		strSQL=mySql4.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("你还没有对任何险种下过“拒保”的核保结论！");
 	 	 return false;
 	 }
 	 return true;
 }

 
/*=========================================================================
 * 修改原因：在发起“延期通知书”的时候，首先判断一下是否对主险或者附加险下了
 *           延期结论，否则不允许发“延期通知书”
 * 修改人：  万泽辉
 * 修改时间：2005/12/14/14:50
 =========================================================================
 */
 function CheckYQNotice()
 {
 	 var tContNo = fm.ContNo.value;//合同号
     var tCaseNo = fm.ClmNo.value; //赔案号
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '2'  and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid5="LLUWPSendAllSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(tCaseNo);//指定传入的参数
		mySql5.addSubPara(tContNo);//指定传入的参数
		strSQL=mySql5.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("你还没有对任何险种下过“延期”的核保结论！");
 	 	 return false;
 	 }
 	 return true;
 }
 
/*=========================================================================
 * 修改原因：在发起“加费承保通知书”的时候，首先判断一下是否对主险下了
 *           加费承保结论，否则不允许发“加费承保通知书”
 * 修改人：  万泽辉
 * 修改时间：2005/12/14/14:50
 =========================================================================
 */
 function CheckJFCBNotice()
 {
 	 var tContNo = fm.ContNo.value;//合同号
     var tCaseNo = fm.ClmNo.value; //赔案号
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '3' and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid6="LLUWPSendAllSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tCaseNo);//指定传入的参数
		mySql6.addSubPara(tContNo);//指定传入的参数
		strSQL=mySql6.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("你还没有对任何险种下过“加费承保”的核保结论！");
 	 	 return false;
 	 }
 	 return true;
 }
 
/*=========================================================================
 * 修改原因：在发起“特约承保通知书”的时候，首先判断一下是否对主险下了
 *           特约承保结论，否则不允许发“特约承保通知书”
 * 修改人：  万泽辉
 * 修改时间：2005/12/14/14:50
 =========================================================================
 */
 function CheckTYCBNotice()
 {
 	 var tContNo = fm.ContNo.value;//合同号
     var tCaseNo = fm.ClmNo.value; //赔案号
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '4' and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid7="LLUWPSendAllSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("claimnb.LLUWPSendAllSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(tCaseNo);//指定传入的参数
		mySql7.addSubPara(tContNo);//指定传入的参数
		strSQL=mySql7.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("你还没有对任何险种下过“特约承保”的核保结论！");
 	 	 return false;
 	 }
 	 return true;
 }