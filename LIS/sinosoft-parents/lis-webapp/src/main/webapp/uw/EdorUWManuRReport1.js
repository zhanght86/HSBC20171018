//程序名称：PEdorUWManuRReport.js
//程序功能：保全人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-23   更新原因/内容：根据新核保要求进行修改

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //保单类型操作位置 1.个人

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  //alert(fm.SubMissionID.value);return false;
 // var strsql = "select 1 from lprreport where EdorNo = '"+fm.EdorNo.value+"' and ContNo = '"+fm.ContNo.value+"' and replyflag in ('0','1')";
	
     var sqlid1="EdorUWManuRReport1Sql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 mySql1.addSubPara(fm.EdorNo.value);//指定传入参数
	 mySql1.addSubPara(fm.ContNo.value);//指定传入参数
	 var strsql = mySql1.getString();
  
  var Result = easyExecSql(strsql);  
  //prompt("",strsql);
  //判断是否查询成功
  if (Result!=null) {
    alert("已发送生调通知书,但未回收,不容许录入或修改生调通知书!");
  	parent.close();
  	return;
  }
  	//alert("fm.EdorNo.value=="+fm.EdorNo.value);
  	//alert("fm.EdorType.value=="+fm.EdorType.value);
  //ln 2008-10-23 add 录入校验
  if(fm.RReport.value == "")
  {
  	alert("接收对象必须录入!");
  	return;
  	}
  	if(fm.RReportReason.value == "")
  {
  	alert("生调原因必须录入!");
  	return;
  	}
  	if(fm.Contente.value == "")
  {
  	alert("生调内容必须录入!");
  	return;
  	}
  //end ln 2008-10-23 add 录入校验
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
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
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //执行下一步操作
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}
function easyQueryClickSingle()
{

	// 书写SQL语句
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tMissionID = fm.MissionID.value;


  if(tContNo != "" )
  {
  	 	
//  	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 //+ " and LWMission.MissionProp2 = '"+ tContNo + "'"
//				 + " and LWMission.ActivityID = '0000000100'"
//				 + " and LWMission.ProcessID = '0000000000' "
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
  	
  	 var sqlid2="EdorUWManuRReport1Sql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
	 mySql2.addSubPara(tMissionID);//指定传入参数
	 strsql = mySql2.getString();
				
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  //prompt("",strsql);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("不允许录入新生调通知书！");
    return "";
  }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
  //alert(fm.SubMissionID.value);
   turnPage = new turnPageClass();			 

  //easyQueryClick();
 				
  }
  return true;
}
function initCustomerNo(tContNo)
{//alert(148);
	var i,j,m,n;
	var returnstr;
	
//	var strSql = "select InsuredNo,name from lpinsured where 1=1 "
//	       + " and ContNo = '" +tContNo + "'"
//	       + " union select CustomerNo , Name from LPInsuredRelated where  polno in (select distinct polno from lppol where contno = '" +tContNo+"')";
	
	 var sqlid3="EdorUWManuRReport1Sql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql3.setSqlId(sqlid3);//指定使用SQL的id
	 mySql3.addSubPara(tContNo);//指定传入参数
	 mySql3.addSubPara(tContNo);//指定传入参数
	 var strSql = mySql3.getString();
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  //prompt("",strSql);
  //判断是否查询成功
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("查询失败！");
    //return "";
//     strSql = "select InsuredNo,name from lcinsured where 1=1 "
//	       + " and ContNo = '" +tContNo + "'"
//	       + " union select CustomerNo , Name from LcInsuredRelated where  polno in (select distinct polno from lcpol where contno = '" +tContNo+"')";
//   
     var sqlid4="EdorUWManuRReport1Sql4";
	 var mySql4=new SqlClass();
	 mySql4.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql4.setSqlId(sqlid4);//指定使用SQL的id
	 mySql4.addSubPara(tContNo);//指定传入参数
	 mySql4.addSubPara(tContNo);//指定传入参数
	 strSql = mySql4.getString();
     
     turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  //prompt("",strSql);
  }
  if (turnPage.strQueryResult == "")
  {
    alert("查询失败！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("查询失败!!");
  			return "";
  		}
  	}
}
else
{
	alert("查询失败!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.CustomerNo.CodeData = returnstr;
  return returnstr;
}

// 查询按钮
function easyQueryClick()
{	
	// 书写SQL语句
	k++;
	var strSQL = "";
	var tCustomerNo = fm.CustomerNo.value;
	var tContNo = fm.ContNo.value;	
	var tMissionID = fm.MissionID.value;
	//alert(tProposalNo);

//	strSQL = "select RReportItemCode,RReportItemName from lprreportitem where ContNo = '"+tContNo+"' and prtseq in(select distinct prtseq from lprreport where ContNo = '"+tContNo+"' and customerno ='"+tCustomerNo+"')";

	 var sqlid5="EdorUWManuRReport1Sql5";
	 var mySql5=new SqlClass();
	 mySql5.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql5.setSqlId(sqlid5);//指定使用SQL的id
	 mySql5.addSubPara(tContNo);//指定传入参数
	 mySql5.addSubPara(tContNo);//指定传入参数
	 mySql5.addSubPara(tCustomerNo);//指定传入参数
	 strSQL = mySql5.getString();
	
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult)
  {    
	  //查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	//设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  	turnPage.pageDisplayGrid = InvestigateGrid;    
  	        
  	//保存SQL语句
  	turnPage.strQuerySql     = strSQL; 
  	
  	//设置查询起始位置
  	turnPage.pageIndex       = 0;  
  	
  	//在查询结果数组中取出符合页面显示大小设置的数组
  	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	
  	//调用MULTILINE对象显示查询结果
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 	}
 		
//  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp2 = '"+ tContNo + "'"
//				 + " and LWMission.ActivityID = '0000000311'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";	
  
     var sqlid6="EdorUWManuRReport1Sql6";
	 var mySql6=new SqlClass();
	 mySql6.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql6.setSqlId(sqlid6);//指定使用SQL的id
	 mySql6.addSubPara(tContNo);//指定传入参数
	 mySql6.addSubPara(tMissionID);//指定传入参数
	 strsql = mySql6.getString();
  
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    //prompt("",strsql);
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) {
	    //fm.SubMissionID.value = "";
	    return "";
      }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  
  return true;
}

function initDefaultObj(tContNo)
{
	fm.RReport.value = '1';
	//fm.RReportTypeName.value = '投保人';
//	var strsql = " select appntno,appntname from lpcont where  ContNo = '"+tContNo+"' ";
	
	 var sqlid7="EdorUWManuRReport1Sql7";
	 var mySql7=new SqlClass();
	 mySql7.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql7.setSqlId(sqlid7);//指定使用SQL的id
	 mySql7.addSubPara(tContNo);//指定传入参数
	 var strsql = mySql7.getString();
	 
	 turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
//prompt("",strsql);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
//      strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
      
     var sqlid8="EdorUWManuRReport1Sql8";
 	 var mySql8=new SqlClass();
 	 mySql8.setResourceName("uw.EdorUWManuRReport1Sql");
 	 mySql8.setSqlId(sqlid8);//指定使用SQL的id
 	 mySql8.addSubPara(tContNo);//指定传入参数
 	 strsql = mySql8.getString();
 	 
      turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
    //return "";
  }
   if (!turnPage.strQueryResult) {
   	return "";
   }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
  
//  var strsql = "select RReportReason,(select codename from ldcode where codetype='rreportreason' and code=a.RReportReason),Contente,proposalcontno,prtseq from lprreport a where 1=1"
//				 + " and edorno = '"+ fm.EdorNo.value + "'"
//				 + " and contno = '"+ tContNo + "'"
//				 + " and replyflag is null ";
  
     var sqlid9="EdorUWManuRReport1Sql9";
	 var mySql9=new SqlClass();
	 mySql9.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql9.setSqlId(sqlid9);//指定使用SQL的id
	 mySql9.addSubPara(fm.EdorNo.value);//指定传入参数
	 mySql9.addSubPara(tContNo);//指定传入参数
	 strsql = mySql9.getString();
	 
  //prompt("",strsql);		
  var tExistFlag = easyExecSql(strsql); 
  if(tExistFlag!= null)
  { 
  	fm.RReportReason.value = tExistFlag[0][0];
  	fm.RReportReasonname.value = tExistFlag[0][1];
  	fm.Contente.value = tExistFlag[0][2];
  	//fm.ProposalContNo.value = tExistFlag[0][3];
  	fm.PrtSeq.value = tExistFlag[0][4];
  }
}

function afterCodeSelect(cCodeName, Field)
{
	 var tRReport = fm.RReport.value;
	var tContNo = fm.ContNo.value;
	if (cCodeName == "rreport")
	{
		
		if(tRReport == "1")
		{
	//		strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
			
			 var sqlid10="EdorUWManuRReport1Sql10";
			 var mySql10=new SqlClass();
			 mySql10.setResourceName("uw.EdorUWManuRReport1Sql");
			 mySql10.setSqlId(sqlid10);//指定使用SQL的id
			 mySql10.addSubPara(tContNo);//指定传入参数
			 strsql = mySql10.getString();
			
		}
		if(tRReport == "2")
		{
		//		strsql = " select insuredno,insuredname from lccont where  ContNo = '"+tContNo+"' ";
				
				 var sqlid11="EdorUWManuRReport1Sql11";
				 var mySql11=new SqlClass();
				 mySql11.setResourceName("uw.EdorUWManuRReport1Sql");
				 mySql11.setSqlId(sqlid11);//指定使用SQL的id
				 mySql11.addSubPara(tContNo);//指定传入参数
				 strsql = mySql11.getString(); 
		}
		  if(tRReport == "3")
		  {
//		      strsql = " select insuredno,name from lcinsured where ContNo = '"+tContNo+"' and sequenceno = '2'"; 
		      
		         var sqlid12="EdorUWManuRReport1Sql12";
				 var mySql12=new SqlClass();
				 mySql12.setResourceName("uw.EdorUWManuRReport1Sql");
				 mySql12.setSqlId(sqlid12);//指定使用SQL的id
				 mySql12.addSubPara(tContNo);//指定传入参数
				 strsql = mySql12.getString();
		  }
  /*
	if(tRReport == "4")
	{
		strsql = " select AgentCode,Name from LAAgent where trim(AgentCode) in (select trim(AgentCode) from LCCont where ContNo = '"+tContNo+"')" ;
		fm.CustomerNo.value = strsql;
	}
	*/
		if(tRReport == "4")
	  {
//	      strsql = " select insuredno,name from lcinsured where ContNo = '"+tContNo+"' and sequenceno = '3'"; 
	      
	         var sqlid13="EdorUWManuRReport1Sql13";
			 var mySql13=new SqlClass();
			 mySql13.setResourceName("uw.EdorUWManuRReport1Sql");
			 mySql13.setSqlId(sqlid13);//指定使用SQL的id
			 mySql13.addSubPara(tContNo);//指定传入参数
			 strsql = mySql13.getString();
	  }
	//查询SQL，返回结果字符串
	  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
	
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) {
	      
	    alert("不存在该接收对象！");
	    initDefaultObj(tContNo);
	    return ;
	  }
	  
	  //查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
	 
	  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
	 	
  

		if(tRReport == "5")
		{
			
			initOperator();
		}

	}

}


function initOperator()
{
	
	var tContNo = fm.ContNo.value;
	
//	strsql = "select Operator from lccont where  ContNo = '"+tContNo+"'"
	
	 var sqlid14="EdorUWManuRReport1Sql14";
	 var mySql14=new SqlClass();
	 mySql14.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql14.setSqlId(sqlid14);//指定使用SQL的id
	 mySql14.addSubPara(tContNo);//指定传入参数
	 strsql = mySql14.getString();
	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerNo.value = "";  
  
}

function initAgent()
{
	
}