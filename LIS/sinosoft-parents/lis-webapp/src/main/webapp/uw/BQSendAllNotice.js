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
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPageQuest = new turnPageClass();
var turnPageQuestD = new turnPageClass();
var brrResult;
var strSQL = "";
//提交，保存按钮对应操作
function submitForm()
{
	
/*	 if(document.all('NoticeType').value=="82")
		{
			if (checkHospitalGridValue() == false)
   		{
    		  return;
   		}
  	}*/
  //tongmeng 2007-11-26 add
  //增加对工作流结点的校验,以及取出最大的活动结点为核保通知书发放的subMissionId
//  var tSQL_SubMissionId = "select (case when max(submissionid) is not null then max(submissionid) else 'x' end) from lwmission where missionid='"+tMissionID+"' "
//                          // + " and activityid in (select activityid from lwactivity where functionid='10020004') ";
//                            + " and activityid in(select activityid from lwactivity where functionid in( '10020004','10020305','10020314','10020332','10020330')) ";
        var sqlid1="BQSendAllNoticeSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.BQSendAllNoticeSql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(tMissionID);//指定传入参数
	 	var tSQL_SubMissionId = mySql1.getString();
  
  
  var tempSubMissionID = "";
  var brr = easyExecSql(tSQL_SubMissionId);//prompt("",tSQL_SubMissionId);return false;
  tempSubMissionID = brr[0][0];
  if(tempSubMissionID == 'x' )
  {
  	//alert(" 存在已发送未回收的核保通知书,不允许再发送核保通知书! ");
  	alert(" 请退出人工核保界面并重新进入后，再次进行操作! ");
  	return false;
  }
  //alert(document.all('SubMissionID').value);
  //document.all('SubMissionID').value = tempSubMissionID;
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  document.getElementById("fm").submit(); //提交 
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
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
		var content="操作成功！";
  	var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	parent.close();
    top.close(); 
   
  	

    //执行下一步操作
  }
}

function QuestQuery()
{	
	
	// 书写SQL语句

	k++;
   tCode = document.all('NoticeType').value;
	
//		var	strSQL = "select cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'uwnoticetype' and Code = '"+tCode+"'";
//		
		var sqlid2="BQSendAllNoticeSql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("uw.BQSendAllNoticeSql");
	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	 	mySql2.addSubPara(k);//指定传入参数
	 	mySql2.addSubPara(k);
	 	mySql2.addSubPara(tCode);
	 	var strSQL = mySql2.getString();
	

	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
   // alert("没有录入过问题键！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
			
			document.all('Content').value = returnstr
  		}
  		else
  		{
  			alert("没有录入过问题件！");
  			return "";
  		}
  	
  }
  else
  {
  	alert("没有录入过问题件！");
	return "";
  }

  if (returnstr == "")
  {
  	alert("没有录入过问题件！");
  }
  
   
 
  return returnstr;
}

function afterCodeSelect( cCodeName, Field )
{

	var tCode = document.all('NoticeType').value;


	if(cCodeName=="bquwnotice" || cCodeName=="uwnoticetype")
	{
	if(tCode=="84"||tCode=="87"||tCode=="BQ83"||tCode=="BQ82")
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		QuestQuery(tCode);
		divLCPol.style.display = 'none';
	}
else if(tCode=="86"||tCode=="88"||tCode=="BQ87")
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		//alert(document.all('ContNo').value);
//		var tsql = "select riskcode,uwflag from lcpol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2')";
		
		var sqlid3="BQSendAllNoticeSql3";
	 	var mySql3=new SqlClass();
	 	mySql3.setResourceName("uw.BQSendAllNoticeSql");
	 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
	 	mySql3.addSubPara(document.all('ContNo').value);//指定传入参数
	 	var tsql = mySql3.getString();
		
		if(tCode=="BQ87")
		{
//		    tsql = "select riskcode,uwflag from lppol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2') and edorno = '" + fm.EdorNo.value + "' and edortype = '" + fm.EdorType.value + "'";
		    
		    var sqlid4="BQSendAllNoticeSql4";
		 	var mySql4=new SqlClass();
		 	mySql4.setResourceName("uw.BQSendAllNoticeSql");
		 	mySql4.setSqlId(sqlid4); //指定使用SQL的id
		 	mySql4.addSubPara(document.all('ContNo').value);//指定传入参数
		 	mySql4.addSubPara(fm.EdorNo.value);
		 	mySql4.addSubPara(fm.EdorType.value);
		 	tsql = mySql4.getString();
		    
		}
		brrResult=easyExecSql(tsql);
		if(brrResult==null)
		{
			QuestQuery(tCode);
		}
	else{
		var str = "经核保后";
		for (var p = 0;p<brrResult.length;p++)
		  {
//		  	tsql = "select riskstatname from lmrisk where riskcode = '"+brrResult[p][0]+"'";
		  	
		  	var sqlid5="BQSendAllNoticeSql5";
		 	var mySql5=new SqlClass();
		 	mySql5.setResourceName("uw.BQSendAllNoticeSql");
		 	mySql5.setSqlId(sqlid5); //指定使用SQL的id
		 	mySql5.addSubPara(brrResult[p][0]);//指定传入参数
		 	tsql = mySql5.getString();
		  	
			  var crrResult = easyExecSql(tsql);
			  if(crrResult!=null)
			    {
			      if(brrResult[p][1]=="1")
			        {
			      	str = str + "拒保" + crrResult[0][0];
			      	}	
			      else if (brrResult[p][1]=="2")
			      	{
			      	str = str + "延期" + crrResult[0][0];
			      	}
			    }
			    if(p+1<brrResult.length)
			    {
			    	str += "、";
			    }
			    else 
			    {
			        if(tCode!="BQ87")
			        {
			    	   str += "。";
			        }
			    }
			}

			
			document.all('Content').value = str;
		}
		divnoticecontent.style.display = 'none'; 
		divLCPol.style.display = 'none';
	}
else if(tCode=="81"||tCode=="BQ86")
	{

		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="89"||tCode=="BQ88")
	{
		divnoticecontent.style.display = 'none';
		//divUWSpec1.style.display = '';
		//divUWSpec.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="82")
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = '';
		
		}
else
	{
		//divUWSpec1.style.display = 'none';
		//divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = 'none';
	}
}
}

function initLWMission()
{
	var MissionID = fm.MissionID.value;
//		var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = '0000000100'"
//								;
							
		var sqlid6="BQSendAllNoticeSql6";
	 	var mySql6=new SqlClass();
	 	mySql6.setResourceName("uw.BQSendAllNoticeSql");
	 	mySql6.setSqlId(sqlid6); //指定使用SQL的id
	 	mySql6.addSubPara(MissionID);//指定传入参数
	 	var strSQL = mySql6.getString();
		
 var brr = easyExecSql(strSQL);//prompt("",strSQL);
	if ( brr )  //已经申请保存过
 	{
 		var tSubMissionID = brr[0][0];
 		fm.SubMissionID.value = tSubMissionID;
	}

}


function initLoprtManager()
{
	//tongmeng 2007-11-12 modify
	//增加业务员通知书查询
	var tContNo = document.all('ContNo').value;
//	var strSQL = "select prtseq,otherno,othernotype,agentcode,code,(select codename from ldcode where codetype='printstate' and code=stateflag) from loprtmanager where otherno = '"+tContNo+"'"
//	        + " and Code in ('TB90','TB89','14','00','06','81','82','83','84','85','86','87','88','89', 'BQ80', 'BQ81', 'BQ82', 'BQ83', 'BQ84', 'BQ85', 'BQ86', 'BQ87', 'BQ88', 'BQ89')"
//	        + " and (oldprtseq is null or prtseq = oldprtseq)";
	
	var sqlid7="BQSendAllNoticeSql7";
 	var mySql7=new SqlClass();
 	mySql7.setResourceName("uw.BQSendAllNoticeSql");
 	mySql7.setSqlId(sqlid7); //指定使用SQL的id
 	mySql7.addSubPara(tContNo);//指定传入参数
 	var strSQL = mySql7.getString();
	
	initPolGrid();       
	        
	        //查询SQL，返回结果字符串
//  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
//    
// initPolGrid();
//
//  //判断是否查询成功
//  if (!turnPage.strQueryResult) {
//  	PolGrid.clearData();
//  	alert("数据库中没有满足条件的数据！");
// 
//    return false;
//  }
// 
//  //查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
// 
//  //设置初始化过的MULTILINE对象
//  turnPage.pageDisplayGrid = PolGrid;   
//         
//  //保存SQL语句
//  turnPage.strQuerySql   = strSQL;
// 
//  //设置查询起始位置
//  turnPage.pageIndex = 0; 
// 
//  //在查询结果数组中取出符合页面显示大小设置的数组
//  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
// 
//  //调用MULTILINE对象显示查询结果
//      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 turnPage2.queryModal(strSQL, PolGrid); 
	        
}

function queryresult() 
{
	  var tSelNo = PolGrid.getSelNo();
    var tCode = PolGrid.getRowColData(tSelNo - 1,5);
    var tPrtseq = PolGrid.getRowColData(tSelNo - 1,1);
    var tResult = "";
    //alert(tCode);
    
    if(tCode == "81")
    {
      divresult.style.display = '';
//      tSQL = "select remark from loprtmanager where prtseq = '"+tPrtseq+"'";
      
        var sqlid8="BQSendAllNoticeSql8";
	   	var mySql8=new SqlClass();
	   	mySql8.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql8.setSqlId(sqlid8); //指定使用SQL的id
	   	mySql8.addSubPara(tPrtseq);//指定传入参数
	   	tSQL = mySql8.getString();
      
      arrResult=easyExecSql(tSQL);
      document.all('result').value = arrResult[0][0];
    	return;
    	} 
    if(tCode == "89")
    {
    	//alert(1);
      divresult.style.display = '';
 //     tSQL = "select askcode,(select CodeName from LDcode where LDcode.Code = LCRReportPrt.askcode and CodeType = 'rreporttype' ) from LCRReportPrt where prtseq = '"+tPrtseq+"'";
      
        var sqlid9="BQSendAllNoticeSql9";
	   	var mySql9=new SqlClass();
	   	mySql9.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql9.setSqlId(sqlid9); //指定使用SQL的id
	   	mySql9.addSubPara(tPrtseq);//指定传入参数
	   	tSQL = mySql9.getString();
      
      arrResult=easyExecSql(tSQL);
      //alert(arrResult.length);
      for(var i=1;i<=arrResult.length;i++ )
       { 
         tResult = tResult+i+":"+arrResult[i-1][1]+" ";
       }
      document.all('result').value = tResult;
    	return;
    	}
    else 
    	divresult.style.display = 'none';

	}

//已发特约 
function queryUWSpecD(tContNo)
{
	if(_DBT==_DBO){
//		strSQL = "select speccontent"
//            + " ,operator ,makedate,'' "
//            + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) else '被保险人' end)"
//            + " ,a,''"
//            + " ,iNo"             
//   + "   from"
//   + "(select speccontent"
//            + " ,operator ,makedate,contno cNo"                         
//            + " ,(select codename from ldcode where codetype='printstate' and code=s.prtflag) a"
//            + " ,s.customerno iNo"             
//   + "   from lpcspec s "
//   + "   where contno = '"+tContNo+"' and prtflag is not null and needprint='Y') s";
		
		var sqlid10="BQSendAllNoticeSql10";
	   	var mySql10=new SqlClass();
	   	mySql10.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql10.setSqlId(sqlid10); //指定使用SQL的id
	   	mySql10.addSubPara(tContNo);//指定传入参数
	   	strSQL = mySql10.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select speccontent"
//            + " ,operator ,makedate,'' "
//            + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) else '被保险人' end)"
//            + " ,a,''"
//            + " ,iNo"             
//   + "   from"
//   + "(select speccontent"
//            + " ,operator ,makedate,contno cNo"                         
//            + " ,(select codename from ldcode where codetype='printstate' and code=s.prtflag) a"
//            + " ,s.customerno iNo"             
//   + "   from lpcspec s "
//   + "   where contno = '"+tContNo+"' and prtflag is not null and prtflag!='' and needprint='Y') s";
		
		var sqlid11="BQSendAllNoticeSql11";
	   	var mySql11=new SqlClass();
	   	mySql11.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql11.setSqlId(sqlid11); //指定使用SQL的id
	   	mySql11.addSubPara(tContNo);//指定传入参数
	   	strSQL = mySql11.getString();
		
	}
			
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWSpecD.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWSpecDGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  return true;	
}

//待发特约
function querySpec(tContNo)
{	
//			strSQL = "select speccontent"
//                         + " ,operator ,makedate,'' "
//                         + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) else '被保险人' end)"
//                         + " ,'Y',iNo"             
//                + "   from"
//				+ "(select speccontent"
//                         + " ,operator ,makedate,contno cNo "                         
//                         + " ,s.customerno iNo"             
//                + "   from lpcspec s "
//                + "   where contno = '"+tContNo+"' and (prtflag is null or prtflag='') and needprint='Y') s";
			
			var sqlid12="BQSendAllNoticeSql12";
		   	var mySql12=new SqlClass();
		   	mySql12.setResourceName("uw.BQSendAllNoticeSql");
		   	mySql12.setSqlId(sqlid12); //指定使用SQL的id
		   	mySql12.addSubPara(tContNo);//指定传入参数
		   	strSQL = mySql12.getString();
			
			
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWSpec.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWSpecGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
  return true;	
}

//查询加费
function queryAddFee(tContNo,tEdorNo,tEdorType)
{     
//			strSQL = "select rc ,(select riskname from lmriskapp where riskcode=rc) riskname"
//			       + " ,a,payplancode,prem,polno,SuppRiskScore,operator,makedate,'' "
//			       + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) else '被保险人' end)"
//			       + " ,'Y',iNo"
//			       + " from "
//			       + " (select (select riskcode from lcpol where polno=p.polno) rc,payplantype,contno cNo"
//			       + " ,(case trim(payplantype) when '01' then '健康加费' when '02' then '职业加费' when '03' then '居住地加费' when '04' then '爱好加费' end) a,payplancode,prem,polno,operator,makedate"
//			       + " ,(select insuredno from lcpol where polno=p.polno) iNo,SuppRiskScore"
//			       + " from lpprem p where  payplancode like '000000%%' "
//                + " and contno = '"+tContNo+"') s where exists (select  1 from LPCUWMaster a where contno = '"+tContNo+"' and EdorNo = '"+ tEdorNo +"' and EdorType='"+tEdorType+"' and (SugPassFlag is null or SugPassFlag=''))";
                
			var sqlid13="BQSendAllNoticeSql13";
		   	var mySql13=new SqlClass();
		   	mySql13.setResourceName("uw.BQSendAllNoticeSql");
		   	mySql13.setSqlId(sqlid13); //指定使用SQL的id
		   	mySql13.addSubPara(tContNo);//指定传入参数
		   	mySql13.addSubPara(tContNo);
		   	mySql13.addSubPara(tEdorNo);
		   	mySql13.addSubPara(tEdorType);
		   	strSQL = mySql13.getString();
			
			
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWAddFee.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWAddFeeGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

//查询加费
function queryAddFeeD(tContNo,tEdorNo,tEdorType)
{
//	strSQL = "select rc ,(select riskname from lmriskapp where riskcode=rc) riskname"
//		+ " ,a,payplancode,prem,polno,SuppRiskScore,operator,makedate,'' "
//		+ " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=cNo and insuredno=iNo)) else '被保险人' end)"
//		+ " ,'Y',iNo"
//		+ " from "
//		+ " (select (select riskcode from lcpol where polno=p.polno) rc,payplantype,contno cNo"
//		+ " ,(case trim(payplantype) when '01' then '健康加费' when '02' then '职业加费' when '03' then '居住地加费' when '04' then '爱好加费' end) a,payplancode,prem,polno,operator,makedate"
//		+ " ,(select insuredno from lcpol where polno=p.polno) iNo,SuppRiskScore"
//		+ " from lpprem p where  payplancode like '000000%%' "
//		+ " and contno = '"+tContNo+"') s where exists (select  1 from LPCUWMaster a where contno = '"+tContNo+"' and EdorNo = '"+ tEdorNo +"' and EdorType='"+tEdorType+"' and SugPassFlag='0')";
	
	var sqlid14="BQSendAllNoticeSql14";
   	var mySql14=new SqlClass();
   	mySql14.setResourceName("uw.BQSendAllNoticeSql");
   	mySql14.setSqlId(sqlid14); //指定使用SQL的id
   	mySql14.addSubPara(tContNo);//指定传入参数
   	mySql14.addSubPara(tContNo);
   	mySql14.addSubPara(tEdorNo);
   	mySql14.addSubPara(tEdorType);
   	strSQL = mySql14.getString();
	
	
	//查询SQL，返回结果字符串
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

	//判断是否查询成功
	if (!turnPage.strQueryResult) {
		divUWAddFeeD.style.display = 'none';
		return false;
	}
	
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	
	//设置初始化过的MULTILINE对象
	turnPage.pageDisplayGrid = UWAddFeeDGrid;   
	
	//保存SQL语句
	turnPage.strQuerySql   = strSQL;
	
	//设置查询起始位置
	turnPage.pageIndex = 0; 
	
	//在查询结果数组中取出符合页面显示大小设置的数组
	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
	
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
}

//查询承保计划变更
function queryUWPlan(tContNo)
{     
	if(_DBT==_DBO){
//		strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv,"
//		    + " b.operator,b.modifydate,'','投保人','Y'  "
//		    + " from lcpol a,lcuwmaster b  "
//		    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag='1' "
//		    + " order by a.mainpolno,a.polno  ";
		
		var sqlid15="BQSendAllNoticeSql15";
	   	var mySql15=new SqlClass();
	   	mySql15.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql15.setSqlId(sqlid15); //指定使用SQL的id
	   	mySql15.addSubPara(tContNo);//指定传入参数
	   	strSQL = mySql15.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv,"
//		    + " b.operator,b.modifydate,'','投保人','Y'  "
//		    + " from lcpol a,lcuwmaster b  "
//		    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag!='' and b.changepolflag='1' "
//		    + " order by a.mainpolno,a.polno  ";
		
		var sqlid16="BQSendAllNoticeSql16";
	   	var mySql16=new SqlClass();
	   	mySql16.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql16.setSqlId(sqlid16); //指定使用SQL的id
	   	mySql16.addSubPara(tContNo);//指定传入参数
	   	strSQL = mySql16.getString();
		
	}
			
                
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWPlan.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWPlanGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

//查询已发生调
function queryUWExistD(tEdorNo,tContNo)
{			 
	if(_DBT==_DBO){
//		strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//	        + " ,'投保人'"
//	        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//	       + " ,(case l.ReplyOperator when null then '未回复' else '已回复' end)"			      
//	       + " from LPRReport l where 1=1 "
//        + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and replyflag is not null";
//		
		var sqlid17="BQSendAllNoticeSql17";
	   	var mySql17=new SqlClass();
	   	mySql17.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql17.setSqlId(sqlid17); //指定使用SQL的id
	   	mySql17.addSubPara(tEdorNo);//指定传入参数
	   	mySql17.addSubPara(tContNo);
	   	strSQL = mySql17.getString();
		
	}else if(_DBT==_DBM){
//			strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//			        + " ,'投保人'"
//			        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//			       + " ,(case l.ReplyOperator when null then '未回复' else '已回复' end)"			      
//			       + " from LPRReport l where 1=1 "
//                + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and replyflag is not null and replyflag!=''";
			
			var sqlid18="BQSendAllNoticeSql18";
		   	var mySql18=new SqlClass();
		   	mySql18.setResourceName("uw.BQSendAllNoticeSql");
		   	mySql18.setSqlId(sqlid18); //指定使用SQL的id
		   	mySql18.addSubPara(tEdorNo);//指定传入参数
		   	mySql18.addSubPara(tContNo);
		   	strSQL = mySql18.getString();
			
	}
                
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWExistD.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWExistDGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

}

//查询待发生调
function queryUWExist(tEdorNo,tContNo)
{
//			strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//			        + " ,'投保人'"
//			       + " ,'Y'"
//			       + " from LPRReport l where 1=1 "
//                + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and (replyflag is null or replyflag='')";

			var sqlid19="BQSendAllNoticeSql19";
		   	var mySql19=new SqlClass();
		   	mySql19.setResourceName("uw.BQSendAllNoticeSql");
		   	mySql19.setSqlId(sqlid19); //指定使用SQL的id
		   	mySql19.addSubPara(tEdorNo);//指定传入参数
		   	mySql19.addSubPara(tContNo);
		   	strSQL = mySql19.getString();
			
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWExist.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWExistGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

}

//查询已发体检
function queryUWHealthD(tEdorNo,tContNo)
{
	if(_DBT==_DBO){
//		strSQL = "select '', operator, makedate, '', (case when (select codename from ldcode where codetype='question' and code=customercode) is not null then (select codename from ldcode where codetype='question' and code=customercode) else '被保险人' end), printstate, replystate, prtseq"
//            +" from (select operator, makedate, "
//            +" (case customernotype when 'A' then '0' else (select sequenceno from lpinsured where edorno='"+tEdorNo+"' and contno='"+tContNo+"' and insuredno=customerno) end) customercode,"
//            +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//	        + " (case printflag when  '1' then '未回复' else '已回复' end) replystate, "
//            +" prtseq "
//		                   + " from LPPENotice where 1=1 "
//                           + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and printflag is not null) s";
//		
		var sqlid20="BQSendAllNoticeSql20";
	   	var mySql20=new SqlClass();
	   	mySql20.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql20.setSqlId(sqlid20); //指定使用SQL的id
	   	mySql20.addSubPara(tEdorNo);//指定传入参数
	   	mySql20.addSubPara(tContNo);
		mySql20.addSubPara(tEdorNo);
	   	mySql20.addSubPara(tContNo);
	   	strSQL = mySql20.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select '', operator, makedate, '', (case when (select codename from ldcode where codetype='question' and code=customercode) is not null then (select codename from ldcode where codetype='question' and code=customercode) else '被保险人' end), printstate, replystate, prtseq"
//            +" from (select operator, makedate, "
//            +" (case customernotype when 'A' then '0' else (select sequenceno from lpinsured where edorno='"+tEdorNo+"' and contno='"+tContNo+"' and insuredno=customerno) end) customercode,"
//            +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//	        + " (case printflag when  '1' then '未回复' else '已回复' end) replystate, "
//            +" prtseq "
//		                   + " from LPPENotice where 1=1 "
//                           + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and printflag is not null and printflag!='') s";
		
		var sqlid21="BQSendAllNoticeSql21";
	   	var mySql21=new SqlClass();
	   	mySql21.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql21.setSqlId(sqlid21); //指定使用SQL的id
	   	mySql21.addSubPara(tEdorNo);//指定传入参数
	   	mySql21.addSubPara(tContNo);
	   	mySql21.addSubPara(tEdorNo);
	   	mySql21.addSubPara(tContNo);
	   	strSQL = mySql21.getString();
		
	}
			
  //prompt("",strSQL);
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWHealthD.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var m = turnPage.arrDataCacheSet.length;
  var tContent ="";
  var arrResult = "";
  var tSQL;
  //alert(m);
  //查询体检内容
  for(var i=0; i<m; i++)
  {
//    tSQL = "select peitemname from lppenoticeitem where 1=1 "
//	       + " and edorno='"+tEdorNo+"' and contno = '"+ tContNo +"' and prtseq = '"+turnPage.arrDataCacheSet[i][7]+"' order by peitemcode" ;
//	
    var sqlid22="BQSendAllNoticeSql22";
   	var mySql22=new SqlClass();
   	mySql22.setResourceName("uw.BQSendAllNoticeSql");
   	mySql22.setSqlId(sqlid22); //指定使用SQL的id
   	mySql22.addSubPara(tEdorNo);//指定传入参数
   	mySql22.addSubPara(tContNo);
   	mySql22.addSubPara(turnPage.arrDataCacheSet[i][7]);
   	tSQL = mySql22.getString();
    
    arrResult=easyExecSql(tSQL);//prompt("",tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][0] = tContent;
  }    
 //alert(627);
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWHealthDGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

}

//查询待发体检
function queryUWHealth(tEdorNo,tContNo)
{
//			strSQL = "select '', operator, makedate, '', (case when (select codename from ldcode where codetype='question' and code=customercode) is not null then (select codename from ldcode where codetype='question' and code=customercode) else '被保险人' end), '', prtseq"
//                +" from (select operator, makedate, (case customernotype when 'A' then '0' else (select sequenceno from lpinsured where edorno='"+tEdorNo+"' and contno='"+tContNo+"' and insuredno=customerno) end) customercode, prtseq"
//			                   + " from LPPENotice where 1=1 "
//                               + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and (printflag is null or printflag='')) s";
//			
			var sqlid23="BQSendAllNoticeSql23";
		   	var mySql23=new SqlClass();
		   	mySql23.setResourceName("uw.BQSendAllNoticeSql");
		   	mySql23.setSqlId(sqlid23); //指定使用SQL的id
		   	mySql23.addSubPara(tEdorNo);//指定传入参数
		   	mySql23.addSubPara(tContNo);
		   	mySql23.addSubPara(tEdorNo);
		   	mySql23.addSubPara(tContNo);
		   	strSQL = mySql23.getString();
			
  //alert(strSQL);
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	divUWHealth.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var m = turnPage.arrDataCacheSet.length;
  var tContent ="";
  var arrResult = "";
  var tSQL;
  //alert(m);
  //查询体检内容
  for(var i=0; i<m; i++)
  {
//    tSQL = "select peitemname from lppenoticeitem where 1=1 "
//	       + " and edorno='"+tEdorNo+"' and contno = '"+ tContNo +"' and prtseq = '"+turnPage.arrDataCacheSet[i][6]+"' order by peitemcode" ;
//    
    var sqlid24="BQSendAllNoticeSql24";
   	var mySql24=new SqlClass();
   	mySql24.setResourceName("uw.BQSendAllNoticeSql");
   	mySql24.setSqlId(sqlid24); //指定使用SQL的id
   	mySql24.addSubPara(tEdorNo);//指定传入参数
   	mySql24.addSubPara(tContNo);
   	mySql24.addSubPara(turnPage.arrDataCacheSet[i][6]);
   	tSQL = mySql24.getString();
    
	arrResult=easyExecSql(tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][0] = tContent;
  }    
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = UWHealthGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
//alert(695);
}

function checkHospitalGridValue()
{
    var rowNum = UWSpecGrid.mulLineCount;
    var selCount = 0;
    for (i = 0;i < rowNum; i++)
    {
        if (UWSpecGrid.getChkNo(i) == true)
        {
            selCount = selCount + 1;       
        }
    }
    if (selCount == 0)
    {
        alert("没有选中特约信息!");
        return false;
    }
    return true;
}

//tongmeng 2007-11-08 add
//初始化查询所有需要回复的问题件
function queryAllNeedQuestion(tEdorNo,tContNo)
{	
	//initQuestGrid();
//	strSQL = " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,'',serialno,(select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType) "
//	       + " ,'','',needprint from lpissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and edorno='"+tEdorNo+"' "
//	       + " and (state is null or state='') "
//	       + " and backobjtype ='4' and needprint='Y' "
//	       + " union "
//	       + " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,'',serialno,(select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType) "
//	       + " ,'','',needprint from lpissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and edorno='"+tEdorNo+"' "
//	       + " and (state is null or state='') "
//	       + " and backobjtype ='2' and needprint='Y'  ";

	var sqlid25="BQSendAllNoticeSql25";
   	var mySql25=new SqlClass();
   	mySql25.setResourceName("uw.BQSendAllNoticeSql");
   	mySql25.setSqlId(sqlid25); //指定使用SQL的id
   	mySql25.addSubPara(tContNo);//指定传入参数
   	mySql25.addSubPara(tEdorNo);
   	mySql25.addSubPara(tContNo);
   	mySql25.addSubPara(tEdorNo);
   	strSQL = mySql25.getString();
	
	
  //查询SQL，返回结果字符串
  turnPageQuest.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPageQuest.strQueryResult) {
  	divQuest.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPageQuest.arrDataCacheSet = decodeEasyQueryResult(turnPageQuest.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPageQuest.pageDisplayGrid = QuestGrid;   
         
  //保存SQL语句
  turnPageQuest.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPageQuest.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPageQuest.getData(turnPageQuest.arrDataCacheSet, turnPageQuest.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPageQuest.pageDisplayGrid);
}

//查询所有已发送问题件
function queryQuestD(tEdorNo,tContNo)
{
	if(_DBT==_DBO){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " ( case a.ReplyMan when null then (case when (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') is not null then (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') else '未回复' end) else '已回复' end),needprint "
//		       + "  from lpissuepol a where contno='"+tContNo+"' "
//		       + " and edorno='"+tEdorNo+"' "
//		       + " and state is not null "
//		       + " and backobjtype ='4' and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then (case when (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') is not null then (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') else '未回复' end) else '已回复' end),needprint "
//		       + "  from lpissuepol a where contno='"+tContNo+"' "
//		       + " and edorno='"+tEdorNo+"' "
//		       + " and state is not null "
//		       + " and backobjtype ='2' and needprint='Y'  "
//		       ;
		
		
			var sqlid26="BQSendAllNoticeSql26";
		   	var mySql26=new SqlClass();
		   	mySql26.setResourceName("uw.BQSendAllNoticeSql");
		   	mySql26.setSqlId(sqlid26); //指定使用SQL的id
		   	mySql26.addSubPara(tContNo);//指定传入参数
		   	mySql26.addSubPara(tEdorNo);
		   	mySql26.addSubPara(tContNo);
		   	mySql26.addSubPara(tEdorNo);
		   	strSQL = mySql26.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " ( case a.ReplyMan when null then (case when (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') is not null then (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') else '未回复' end) else '已回复' end),needprint "
//		       + "  from lpissuepol a where contno='"+tContNo+"' "
//		       + " and edorno='"+tEdorNo+"' "
//		       + " and state is not null and state!='' "
//		       + " and backobjtype ='4' and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='bqbackobj' and comcode=a.BackObjType), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then (case when (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') is not null then (select '已回复' from LOPRTManager where prtseq=a.prtseq and stateflag='2') else '未回复' end) else '已回复' end),needprint "
//		       + "  from lpissuepol a where contno='"+tContNo+"' "
//		       + " and edorno='"+tEdorNo+"' "
//		       + " and state is not null and state!='' "
//		       + " and backobjtype ='2' and needprint='Y'  "
		       ;
		
		var sqlid27="BQSendAllNoticeSql27";
	   	var mySql27=new SqlClass();
	   	mySql27.setResourceName("uw.BQSendAllNoticeSql");
	   	mySql27.setSqlId(sqlid27); //指定使用SQL的id
	   	mySql27.addSubPara(tContNo);//指定传入参数
	   	mySql27.addSubPara(tEdorNo);
	   	mySql27.addSubPara(tContNo);
	   	mySql27.addSubPara(tEdorNo);
	   	strSQL = mySql27.getString();
	
		
	}
	
  //查询SQL，返回结果字符串
  turnPageQuestD.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPageQuestD.strQueryResult) {
  	divQuestD.style.display = 'none';
    return false;
 }
 
  //查询成功则拆分字符串，返回二维数组
  turnPageQuestD.arrDataCacheSet = decodeEasyQueryResult(turnPageQuestD.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPageQuestD.pageDisplayGrid = QuestDGrid;   
         
  //保存SQL语句
  turnPageQuestD.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPageQuestD.pageIndex = 0; 
 
 //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPageQuestD.getData(turnPageQuestD.arrDataCacheSet, turnPageQuestD.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPageQuestD.pageDisplayGrid);	
}