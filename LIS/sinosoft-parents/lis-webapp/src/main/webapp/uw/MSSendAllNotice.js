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
	
  tSQL_SubMissionId="select (case when uwstate is not null then uwstate else '1' end) from lccuwmaster where contno='"+fm.ContNo.value+"'";
  var tempSubMissionID = "";
  var brr = easyExecSql(tSQL_SubMissionId);
  tempSubMissionID = brr[0][0];
  if(tempSubMissionID == '4' )
  {
  	alert(" 存在已发送未回收的核保通知书,不允许再发送核保通知书! ");
  	return false;
  }
  //tongmeng 2009-05-11 add
  //对豁免险的校验
  
  
  
  var ContNo = document.all('ContNo').value;
  //alert(ContNo);
  //查询合同下是否有豁免险.
//  	var tSQL_HM = "select '1' from lcpol where riskcode='121301' and contno='"+ContNo+"' "
//  	            + " and uwflag not in ('1','2','a') "
//  	            + " union "
//  	            + " select '2' from lcpol where riskcode='121506' and contno='"+ContNo+"' "
//  	            + " and uwflag not in ('1','2','a') ";
  	var  sqlid1="MSSendAllNoticeSql0";
 	var  mySql1=new SqlClass();
 	mySql1.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 	mySql1.addSubPara(ContNo);//指定传入的参数
 	mySql1.addSubPara(ContNo);//指定传入的参数
 	var tSQL_HM=mySql1.getString();
  	var tFlag_HM = easyExecSql(tSQL_HM);
  	if(tFlag_HM!=null)
  	{
  		 var tSQL_CheckHM = "";
  		 var tFlag_CheckHM = "";
  		 if(tFlag_HM[0][0]=='1')
  		 {
  		 	 //校验投保人豁免险
//  		 	 tSQL_CheckHM = "select decode(count(*),0,0,1) from ( "
//	                    + " select round(nvl(sum(a.prem),0),2) x,round(nvl(sum(b.amnt),0),2) y "
//	                    + " from lcpol a,lcpol b "
//	                    + " where a.contno=b.contno and a.contno='"+ContNo+"' "
//	                    + " and a.riskcode<>'121301' and b.riskcode='121301' "
//	                    + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
////	                    + " and code='121301') ) A where A.x<>A.y ";
//  			tSQL_CheckHM = " select 1 from dual where ((select sum(a.prem)"
//				+ " from lcpol a"
//				+ " where a.riskcode <> '121301' and a.contno = '"+ContNo+"'"
//				+ " and a.uwflag not in ('1','2','a') "
//				+ " and a.riskcode in (select code1"
//				+ " from ldcode1 where codetype = 'freerisk'"
//				+ " and code = '121301')) <> (select sum(b.amnt)"
//				+ " from lcpol b where b.riskcode = '121301'"
//				+ " and b.contno = '"+ContNo+"' and uwflag not in ('1','2','a') )) ";
//  		 	prompt('tSQL_CheckHM',tSQL_CheckHM);
  			
  			var  sqlid2="MSSendAllNoticeSql1";
  		 	var  mySql2=new SqlClass();
  		 	mySql2.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
  		 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
  		 	mySql2.addSubPara(ContNo);//指定传入的参数
  		 	mySql2.addSubPara(ContNo);//指定传入的参数
  		 	tSQL_CheckHM=mySql2.getString();
  		 	 tFlag_CheckHM = easyExecSql(tSQL_CheckHM);
  			 if(tFlag_CheckHM!=null)
  		 		{
						if(tFlag_CheckHM[0][0]=='1')
						{
						 	alert('合同下豁免险种的保额不等于相应险种的累计保费,请修改豁免险保额后再下核保结论!');
						 	return false;
						}
  		 		}
  			//05-13 增加校验主险交费期间为趸交，不能带有附加豁免险
//  			 var AMainPolPaySql = "select lcpol.payintv from lcpol where contno = '"+ContNo+"'"
//  				 		+ " and riskcode in (select code1 from ldcode1 where codetype = 'freerisk'"
//						+ " and code = '121301') and uwflag not in ('1','2','a') ";
  			var  sqlid3="MSSendAllNoticeSql2";
  		 	var  mySql3=new SqlClass();
  		 	mySql3.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
  		 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
  		 	mySql3.addSubPara(ContNo);//指定传入的参数
  		 	var AMainPolPaySql=mySql3.getString();
  			 var AMainPolPay = easyExecSql(AMainPolPaySql);
  			 if(AMainPolPay!=null) {
  				 for(i=0;i<AMainPolPay.length;i++) {
  					 if(AMainPolPay[i][0]=='0') {
  						 alert("主险的交费期间为趸交，不能带有附加豁免险！");
  						 return false;
  					 }
  				 }
  			 }
  			 
  			 //05-13  增加校验主险交费期间与附加豁免险交费期间是否一致
//  			 var ACheckPayIntvSql = "select (case count(*) when 0 then 0 else 1 end) from ( "
//		                + " select a.payyears x,b.payyears y "
//		                + " from lcpol a,lcpol b "
//		                + " where a.contno=b.contno and a.contno='"+ContNo+"' "
//		                + " and a.riskcode<>'121301' and b.riskcode='121301' "
//		                + " and a.uwflag not in ('1','2','a') "
//		                + " and b.uwflag not in ('1','2','a') "
//		                + " and a.riskcode in (select code1 from ldcode1 where codetype='freerisk' "
//		                + " and code='121301') ) A where A.x<>A.y ";
  			var  sqlid4="MSSendAllNoticeSql3";
  		 	var  mySql4=new SqlClass();
  		 	mySql4.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
  		 	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
  		 	mySql4.addSubPara(ContNo);//指定传入的参数
  		    var ACheckPayIntvSql=mySql4.getString();
  			 var ACheckPayIntv = easyExecSql(ACheckPayIntvSql);
  			 if(ACheckPayIntv!=null) {
				if(ACheckPayIntv[0][0]=='1') {
					 alert('主险的交费期间与附加豁免险的交费期间不一致！');
					 return false;
				}
  			 }
  		 }
  		 else  if(tFlag_HM[0][0]=='2')
  		 {
  		 	 //校验被保人豁免险
//  		 	 tSQL_CheckHM = " select 1 from dual where ((select sum(a.prem)"
//							+ " from lcpol a"
//							+ " where a.riskcode <> '121506' and a.contno = '"+ContNo+"'"
//							+ " and uwflag not in ('1','2','a') "
//							+ " and a.riskcode in (select code1"
//							+ " from ldcode1 where codetype = 'freerisk'"
//							+ " and code = '121506')) <> (select sum(b.amnt)"
//							+ " from lcpol b where b.riskcode = '121506'"
//							+ " and b.contno = '"+ContNo+"' and uwflag not in ('1','2','a')))";
//  		 	 	prompt('tSQL_CheckHM',tSQL_CheckHM);
  		 	var  sqlid5="MSSendAllNoticeSql4";
  		 	var  mySql5=new SqlClass();
  		 	mySql5.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
  		 	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
  		 	mySql5.addSubPara(ContNo);//指定传入的参数
  		 	mySql5.addSubPara(ContNo);//指定传入的参数
  		    tSQL_CheckHM=mySql5.getString();
  		 	 tFlag_CheckHM = easyExecSql(tSQL_CheckHM);
  			 if(tFlag_CheckHM!=null)
  		 	 {
  		 			for(i=0;i<tFlag_CheckHM.length;i++)
  		 			{
							if(tFlag_CheckHM[i][0]=='1')
							{
						 		alert('被保人豁免险种的保额不等于相应险种的累计保费,请修改豁免险保额后再下核保结论!');
						 		return false;
							}
						}
  		 	 }
  			//05-13 增加校验主险交费期间为趸交，不能带有附加豁免险
//  			 var IMainPolPaySql = "select lcpol.insuredno,lcpol.payintv from lcpol where contno = '"+ContNo+"'"
//  				 		+ " and riskcode in (select code1 from ldcode1 where codetype = 'freerisk'"
//						+ " and code = '121506') and uwflag not in ('1','2','a') ";
  			var  sqlid6="MSSendAllNoticeSql5";
  		 	var  mySql6=new SqlClass();
  		 	mySql6.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
  		 	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
  		 	mySql6.addSubPara(ContNo);//指定传入的参数
  		 	var IMainPolPaySql=mySql6.getString();
  			 var IMainPolPay = easyExecSql(IMainPolPaySql);
  			 if(IMainPolPay!=null) {
  				 for(i=0;i<IMainPolPay.length;i++) {
  					 if(IMainPolPay[i][1]=='0') {
  						 alert("被保人："+IMainPolPay[i][1]+" 下的主险的交费期间为趸交，不能带有附加豁免险！");
  						 return false;
  					 }
  				 }
  			 }
  			 
  			 //05-13 增加增加校验主险交费期间与附加豁免险交费期间是否一致
//  			var ICheckPayIntvSql = "select a.insuredno from lcpol a, lcpol b where a.contno = '"+ContNo+"'"
//  						+ " and a.contno = b.contno and a.payyears != b.payyears"
//  						+ " and a.riskcode = '121506' and b.riskcode in (select code1"
//  						+ " from ldcode1 where codetype = 'freerisk'"
//  						+ " and code = '121506') and b.riskcode <> '121506'"
//  						+ " and a.uwflag not in ('1','2','a') "
//  						+ " and b.uwflag not in ('1','2','a') ";
  			var  sqlid7="MSSendAllNoticeSql6";
  		 	var  mySql7=new SqlClass();
  		 	mySql7.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
  		 	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
  		 	mySql7.addSubPara(ContNo);//指定传入的参数
  		 	var ICheckPayIntvSql=mySql7.getString();
  			var ICheckPayIntv = easyExecSql(ICheckPayIntvSql);
 			 if(ICheckPayIntv!=null) {
				for(i=0;i<ICheckPayIntv.length;i++) {
					alert("被保人："+ICheckPayIntv[i][0]+" 下的主险的交费期间与附加豁免险的交费期间不一致！");
					return false;
				}
 			 }
  		 }
  		}
//  	alert("校验失败！");
//  	return false;
//  	return;	 
//  alert(tempSubMissionID);
  document.all('SubMissionID').value = tempSubMissionID;
  document.all("ActivityID").value = tActivityID;//add by lzf 2013--3-11
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
lockScreen('lkscreen');  
  fm.submit(); //提交 
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
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
	  showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
	
		var	strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'uwnoticetype' and Code = '"+tCode+"'";

	

	
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
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		QuestQuery(tCode);
		divLCPol.style.display = 'none';
	}
else if(tCode=="86"||tCode=="88"||tCode=="BQ87")
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		//alert(document.all('ContNo').value);
		  //var tsql = "select riskcode,uwflag from lcpol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2')";
		    var  sqlid8="MSSendAllNoticeSql7";
		 	var  mySql8=new SqlClass();
		 	mySql8.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		 	mySql8.addSubPara(document.all('ContNo').value);//指定传入的参数
		 	var tsql=mySql8.getString();
		if(tCode=="BQ87")
		{
		   // tsql = "select riskcode,uwflag from lppol where contno = '"+document.all('ContNo').value+"' and mainpolno!=polno and uwflag in ('1','2') and edorno = '" + fm.EdorNo.value + "' and edortype = '" + fm.EdorType.value + "'";
		    var  sqlid9="MSSendAllNoticeSql8";
		 	var  mySql9=new SqlClass();
		 	mySql9.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		 	mySql9.addSubPara(document.all('ContNo').value);//指定传入的参数
		 	mySql9.addSubPara(fm.EdorNo.value);//指定传入的参数
		 	mySql9.addSubPara(fm.EdorType.value);//指定传入的参数
		    tsql=mySql9.getString();
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
		  	//tsql = "select riskstatname from lmrisk where riskcode = '"+brrResult[p][0]+"'";
		  	    var  sqlid10="MSSendAllNoticeSql9";
			 	var  mySql10=new SqlClass();
			 	mySql10.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
			 	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
			 	mySql10.addSubPara(brrResult[p][0]);//指定传入的参数
			    tsql=mySql10.getString();
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

		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="89"||tCode=="BQ88")
	{
		divnoticecontent.style.display = 'none';
		divUWSpec1.style.display = '';
		divUWSpec.style.display = '';
		fm.Content.value = "";
		divLCPol.style.display = 'none';
	}
else if(tCode=="82")
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = '';
		
		}
else
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
		divLCPol.style.display = 'none';
	}
}
}

function initLWMission()
{
	var MissionID = fm.MissionID.value;
//		var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = '0000001270'"
//								;
		    var  sqlid11="MSSendAllNoticeSql10";
		 	var  mySql11=new SqlClass();
		 	mySql11.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		 	mySql11.addSubPara(MissionID);//指定传入的参数
		 	var strSQL=mySql11.getString();
								
 var brr = easyExecSql(strSQL);
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
	    var  sqlid12="MSSendAllNoticeSql11";
	 	var  mySql12=new SqlClass();
	 	mySql12.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
	 	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
	 	mySql12.addSubPara(tContNo);//指定传入的参数
	 	var strSQL=mySql12.getString();
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
     // tSQL = "select remark from loprtmanager where prtseq = '"+tPrtseq+"'";
        var  sqlid13="MSSendAllNoticeSql12";
	 	var  mySql13=new SqlClass();
	 	mySql13.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
	 	mySql13.setSqlId(sqlid13);//指定使用的Sql的id
	 	mySql13.addSubPara(tPrtseq);//指定传入的参数
	 	tSQL=mySql13.getString();
      arrResult=easyExecSql(tSQL);
      document.all('result').value = arrResult[0][0];
    	return;
    	} 
    if(tCode == "89")
    {
    	//alert(1);
      divresult.style.display = '';
      //tSQL = "select askcode,(select CodeName from LDcode where LDcode.Code = LCRReportPrt.askcode and CodeType = 'rreporttype' ) from LCRReportPrt where prtseq = '"+tPrtseq+"'";
        var  sqlid14="MSSendAllNoticeSql13";
	 	var  mySql14=new SqlClass();
	 	mySql14.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
	 	mySql14.setSqlId(sqlid14);//指定使用的Sql的id
	 	mySql14.addSubPara(tPrtseq);//指定传入的参数
	 	tSQL=mySql14.getString();
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
//			strSQL = "select A.speccontent"
//                         + " ,(select username from lduser where usercode=A.operator) ,A.makedate,'' "
//                         + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) else '被保险人' end)"
//                         + " ,A.a,''"
//                         + " ,A.iNo"             
//                + "   from"
//			    + "(select speccontent"
//                         + " ,operator ,makedate,contno cNo"                         
//                         + " ,(select codename from ldcode where codetype='printstate' and code=s.prtflag) a"
//                         + " ,s.customerno iNo"             
//                + "   from lccspec s "
//                + "   where contno = '"+tContNo+"' and prtflag is not null ) A";
			
			var  sqlid15="MSSendAllNoticeSql14";
		 	var  mySql15=new SqlClass();
		 	mySql15.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		 	mySql15.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql15.getString();
	
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
//			strSQL = "select A.speccontent"
//                         + " ,(select username from lduser where usercode=A.operator) ,A.makedate,'' "
//                         + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) else '被保险人' end)"
//                         + " ,'Y',A.iNo"             
//                + "   from"
//				+ "(select speccontent"
//                         + " ,operator ,makedate,contno cNo "                         
//                         + " ,s.customerno iNo"             
//                + "   from lccspec s "
//                + "   where contno = '"+tContNo+"' and prtflag is null and needprint='Y') A";
			
			var  sqlid16="MSSendAllNoticeSql15";
		 	var  mySql16=new SqlClass();
		 	mySql16.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		 	mySql16.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql16.getString();
	
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
function queryAddFee(tContNo)
{     
//			strSQL = "select rc ,(select riskname from lmriskapp where riskcode=A.rc) riskname"
//			       + " ,a,payplancode,prem,polno,SuppRiskScore,(select username from lduser where usercode=A.operator),makedate,'' "
//			       + " ,(case when (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) is not null then (select codename from ldcode where codetype='question' and code=(select sequenceno from lcinsured where contno=A.cNo and insuredno=A.iNo)) else '被保险人' end)"
//			       + " ,'Y',iNo"
//			       + " from "
//			       + " (select (select riskcode from lcpol where polno=p.polno) rc,payplantype,contno cNo"
//			       + " ,(case trim(payplantype) when '01' then '健康加费' when '02' then '职业加费' when '03' then '居住地加费' when '04' then '爱好加费' end) a,payplancode,prem,polno,operator,makedate"
//			       + " ,(select insuredno from lcpol where polno=p.polno) iNo,SuppRiskScore"
//			       + " from lcprem p where  payplancode like '000000%%' "
//                + " and contno = '"+tContNo+"'"
//                + " and exists(select 1 from lcuwmaster where polno=p.polno and addpremflag is not null and addpremflag='1')"//需要打印通知书
//                + ") A";
			
			var  sqlid17="MSSendAllNoticeSql16";
		 	var  mySql17=new SqlClass();
		 	mySql17.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		 	mySql17.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql17.getString();
                
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

//查询承保计划变更
function queryUWPlan(tContNo)
{     
//			strSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt, mult,prem, concat(payendyear,payendyearflag),concat(insuyear,insuyearflag),payintv"
//			    + " ,(select concat(concat(a.uwflag,'-'),codename) from ldcode where codetype='uwstate' and code=a.uwflag)"
//			    + " ,(select username from lduser where usercode=b.operator),b.modifydate,'','投保人','Y'  "
//			    + " from lcpol a,lcuwmaster b  "
//			    + " where a.contno = '"+tContNo+"' and a.proposalno=b.proposalno and b.changepolflag is not null and b.changepolflag='1' "
//			    + " order by a.mainpolno,a.polno  ";
			var  sqlid18="MSSendAllNoticeSql17";
		 	var  mySql18=new SqlClass();
		 	mySql18.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		 	mySql18.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql18.getString();
                
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
function queryUWExistD(tContNo)
{			 
//			strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//			        + " ,'投保人'"
//			        + " ,(select codename from ldcode where codetype='printstate' and code=l.replyflag)"
//			       + " ,(case l.ReplyOperator when null then '未回复' else '已回复' end)"			      
//			       + " from LCRReport l where 1=1 "
//                + " and contno = '"+tContNo+"' and replyflag is not null";
			
			var  sqlid19="MSSendAllNoticeSql18";
		 	var  mySql19=new SqlClass();
		 	mySql19.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		 	mySql19.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql19.getString();
                
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
function queryUWExist(tContNo)
{
//			strSQL = "select Contente,(select username from lduser where usercode=l.operator),makedate"
//			        + " ,'投保人'"
//			       + " ,'Y'"
//			       + " from LCRReport l where 1=1 "
//                + " and contno = '"+tContNo+"' and replyflag is null";
			
			var  sqlid20="MSSendAllNoticeSql19";
		 	var  mySql20=new SqlClass();
		 	mySql20.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		 	mySql20.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql20.getString();

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
function queryUWHealthD(tContNo)
{
//			strSQL = "select '', (select username from lduser where usercode=A.operator), A.makedate, '', (case when (select codename from ldcode where codetype='question' and code=A.customercode) is not null then (select codename from ldcode where codetype='question' and code=A.customercode) else '被保险人' end), A.printstate, A.replystate, A.prtseq"
//                +" from (select operator, makedate, "
//                +" ( case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno='"+tContNo+"' and insuredno=customerno) end) customercode,"
//                +" (select codename from ldcode where codetype='printstate' and code=printflag) printstate,"
//		        + " (case peresult when null then '未回复' else '已回复' end) replystate, "
//                +" prtseq "
//			                   + " from LCPENotice where 1=1 "
//                               + " and contno = '"+tContNo+"' and printflag is not null) A";
			
			var  sqlid21="MSSendAllNoticeSql20";
		 	var  mySql21=new SqlClass();
		 	mySql21.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
		 	mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		 	mySql21.addSubPara(tContNo);//指定传入的参数
		 	mySql21.addSubPara(tContNo);//指定传入的参数
		 	strSQL=mySql21.getString();
  //alert(strSQL);
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
//    tSQL = "select peitemname from lcpenoticeitem where 1=1 "
//	       + " and contno = '"+ tContNo +"' and prtseq = '"+turnPage.arrDataCacheSet[i][7]+"' order by peitemcode" ;
    
    var  sqlid22="MSSendAllNoticeSql21";
 	var  mySql22=new SqlClass();
 	mySql22.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
 	mySql22.setSqlId(sqlid22);//指定使用的Sql的id
 	mySql22.addSubPara(tContNo);//指定传入的参数
 	mySql22.addSubPara(turnPage.arrDataCacheSet[i][7]);//指定传入的参数
 	tSQL=mySql22.getString();
 	
	arrResult=easyExecSql(tSQL);
	for(var j=0; j<arrResult.length; j++)
	   tContent = tContent + arrResult[j][0]+" ;";
	turnPage.arrDataCacheSet[i][0] = tContent;
  }    
 
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
function queryUWHealth(tContNo)
{
//			strSQL = "select '', (select username from lduser where usercode=A.operator), A.makedate, '', (case when (select codename from ldcode where codetype='question' and code=A.customercode) is not null then (select codename from ldcode where codetype='question' and code=A.customercode) else '被保险人' end), '', A.prtseq"
//                +" from (select operator, makedate, (case customertype when 'A' then '0' else (select sequenceno from lcinsured where contno='"+tContNo+"' and insuredno=customerno) end) customercode, prtseq"
//			                   + " from LCPENotice where 1=1 "
//                               + " and contno = '"+tContNo+"' and printflag is null) A";
			
			    var  sqlid23="MSSendAllNoticeSql22";
			 	var  mySql23=new SqlClass();
			 	mySql23.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
			 	mySql23.setSqlId(sqlid23);//指定使用的Sql的id
			 	mySql23.addSubPara(tContNo);//指定传入的参数
			 	mySql23.addSubPara(tContNo);//指定传入的参数
			 	strSQL=mySql23.getString();
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
//    tSQL = "select peitemname from lcpenoticeitem where 1=1 "
//	       + " and contno = '"+ tContNo +"' and prtseq = '"+turnPage.arrDataCacheSet[i][6]+"' order by peitemcode" ;
    var  sqlid24="MSSendAllNoticeSql23";
 	var  mySql24=new SqlClass();
 	mySql24.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
 	mySql24.setSqlId(sqlid24);//指定使用的Sql的id
 	mySql24.addSubPara(tContNo);//指定传入的参数
 	mySql24.addSubPara(turnPage.arrDataCacheSet[i][6]);//指定传入的参数
 	tSQL=mySql24.getString();
 	
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
function queryAllNeedQuestion(tContNo)
{	
	//initQuestGrid();
//	strSQL = " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,'','',needprint from lcissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='')"
//	       + " and backobjtype in ('3','4') and needprint='Y' "
//	       + " union "
//	       + " select proposalcontno,issuetype,issuecont,replyresult,(select username from lduser where usercode=a.operator),makedate,operatepos, "
//	       + " backobj,StandbyFlag2,serialno,(select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2) "
//	       + " ,(case when (select codename from ldcode where codetype='question' and code=a.questionobjtype) is not null then (select codename from ldcode where codetype='question' and code=a.questionobjtype) else '被保险人' end),questionobj,needprint from lcissuepol a where contno='"+tContNo+"' "
//	       //+ " and a.prtseq is null "
//	       + " and (state is null or state='')"
//	       + " and backobjtype in ('1','2') and needprint='Y'  ";
	var  sqlid25="MSSendAllNoticeSql24";
 	var  mySql25=new SqlClass();
 	mySql25.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
 	mySql25.setSqlId(sqlid25);//指定使用的Sql的id
 	mySql25.addSubPara(tContNo);//指定传入的参数
 	mySql25.addSubPara(tContNo);//指定传入的参数
 	strSQL=mySql25.getString();

  //查询SQL，返回结果字符串
  initQuestGrid();
  turnPageQuest.queryModal(strSQL,QuestGrid);
 // turnPageQuest.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
/*
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
  */
}

//查询所有已发送问题件
function queryQuestD(tContNo)
{
	initQuestDGrid();
	if(_DBT==_DBO){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//			+ " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//			+ " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			+ " (case a.ReplyMan when null then '未回复' when '' then '未回复' else '已回复' end),needprint "
//			+ "  from lcissuepol a where contno='"+tContNo+"' "
//			+ " and state is not null "
//			+ " and backobjtype in ('3','4') and needprint='Y' "
//			+ " union "
//			+ " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//			+ " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//			+ " (case when (select codename from ldcode where codetype='question' and code=questionobjtype) is not null then (select codename from ldcode where codetype='question' and code=questionobjtype) else '被保险人' end), (select codename from ldcode where codetype='printstate' and code=state),"
//			+ " (case a.ReplyMan when null then '未回复' when '' then '未回复' else '已回复' end),needprint "
//			+ "  from lcissuepol a where contno='"+tContNo+"' "
//			+ " and state is not null "
//			+ " and backobjtype in ('1','2') and needprint='Y'  "
//			;
		var  sqlid26="MSSendAllNoticeSql25";
	 	var  mySql26=new SqlClass();
	 	mySql26.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
	 	mySql26.setSqlId(sqlid26);//指定使用的Sql的id
	 	mySql26.addSubPara(tContNo);//指定传入的参数
	 	mySql26.addSubPara(tContNo);//指定传入的参数
	 	strSQL=mySql26.getString();
	}else if(_DBT==_DBM){
//		strSQL = " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " '', (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then '未回复' when '' then '未回复' else '已回复' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null and state !=''"
//		       + " and backobjtype in ('3','4') and needprint='Y' "
//		       + " union "
//		       + " select proposalcontno,issuetype,issuecont,(select username from lduser where usercode=a.operator),makedate, "
//		       + " (select codename from ldcode where codetype='backobj' and comcode=a.BackObjType and othersign=a.standbyflag2), "
//		       + " (case when (select codename from ldcode where codetype='question' and code=questionobjtype) is not null then (select codename from ldcode where codetype='question' and code=questionobjtype) else '被保险人' end), (select codename from ldcode where codetype='printstate' and code=state),"
//			   + " (case a.ReplyMan when null then '未回复' when '' then '未回复' else '已回复' end),needprint "
//		       + "  from lcissuepol a where contno='"+tContNo+"' "
//		       + " and state is not null and state !='' "
//		       + " and backobjtype in ('1','2') and needprint='Y'  "
//		       ;
		var  sqlid27="MSSendAllNoticeSql26";
	 	var  mySql27=new SqlClass();
	 	mySql27.setResourceName("uw.MSSendAllNoticeSql"); //指定使用的properties文件名
	 	mySql27.setSqlId(sqlid27);//指定使用的Sql的id
	 	mySql27.addSubPara(tContNo);//指定传入的参数
	 	mySql27.addSubPara(tContNo);//指定传入的参数
	 	strSQL=mySql27.getString();
	}
  //查询SQL，返回结果字符串
  turnPageQuestD.queryModal(strSQL,QuestDGrid);
  /*
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
  */
}