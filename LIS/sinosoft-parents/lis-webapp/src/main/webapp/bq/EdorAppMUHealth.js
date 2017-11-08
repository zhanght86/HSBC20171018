//程序名称：EdorAppMUHealth.js
//程序功能：保全人工核保体检资料录入
//创建日期：2005-06-13 13:13:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var flag;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{


	
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

  fm.action= "./EdorAppMUHealthSave.jsp";
  fm.submit(); //提交
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
    //parent.close();
  }
  else
  { 
	var showStr="操作成功";
  	showInfo.close();
  	alert(showStr);
  	//parent.close();
  	
    //执行下一步操作
  }
}


/*********************************************************************
 *  执行待保全体检通知书的EasyQuery
 *  描述:查询显示对象是体检通知书
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClickSingle()
{
  var strsql = "";
  var tContNo = "";
  var tEdorNo = "";
  tContNo = fm.ContNo.value;
  tEdorNo = fm.EdorNo.value;
  tMissionID = fm.MissionID.value;
  tInsuredNo = fm.InsureNo.value;

//  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.ActivityID = '0000000019'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
  
    var sqlid1="EdorAppMUHealthSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorAppMUHealthSql");
	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	mySql1.addSubPara(tMissionID);//指定传入参数
	var strsql = mySql1.getString();
  
  var brr = easyExecSql(strsql);

  //判断是否查询成功
  if (brr) 
  {
	brr[0][0]==null||brr[0][0]=='null'?'0':fm.SubMissionID.value  = brr[0][0];
    divSubmit.style.display = '';
   	divReturn.style.display = ''; 
  }
  else
  {
    alert("该任务节点未生成或已删除，不容许录入新体检通知书！");
    divSubmit.style.display = 'none';                 //隐藏提交按钮
   	divReturn.style.display = '';
    return "";
  }
}
   

function queryPEInfo()
{   
	var tContNo = "";
    var tEdorNo = "";
    tContNo = fm.ContNo.value;
    tEdorNo = fm.EdorNo.value;	 
    tInsuredNo = fm.InsureNo.value;
//	var strsql = "select ContNo,name,pedate,peaddress,PEBeforeCond,remark, "
//	             + " (case when trim(PrintFlag)='0' then '未打印' else case when trim(PrintFlag)='1' then '已打印' else case when trim(PrintFlag)='2' then '已回复' else '' end  end  end) "
//	             + " from LPPENotice where 1=1"
//				 + " and ContNo = '"+ tContNo + "' and edorno = '"+tEdorNo+"'"
//				 + " and customerno = '"+ tInsuredNo + "'";
	
	var sqlid2="EdorAppMUHealthSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorAppMUHealthSql");
	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	mySql2.addSubPara(tContNo);//指定传入参数
	mySql2.addSubPara(tEdorNo);
	mySql2.addSubPara(tInsuredNo);
	var strsql = mySql2.getString();
	
    brr = easyExecSql(strsql);

    if (!brr) 
    {
      fm.Hospital.value  = "";
      fm.IfEmpty.value  = "";
      fm.Note.value  = "";
      fm.PrintFlag.value  = "";
      return "";
    }
 
    //查询成功则拆分字符串，返回二维数组
    brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
    brr[0][3]==null||brr[0][3]=='null'?'0':fm.Hospital.value  = brr[0][3];
    brr[0][4]==null||brr[0][4]=='null'?'0':fm.IfEmpty.value  = brr[0][4];
    brr[0][5]==null||brr[0][5]=='null'?'0':fm.Note.value  = brr[0][5];
    brr[0][6]==null||brr[0][6]=='null'?'0':fm.PrintFlag.value  = brr[0][6];

 
}



function initInsureNo(tContNo,tEdorNo)
{
  var i,j,m,n;
  var returnstr;

//  var strSql = "select InsuredNo,concat(trim(name),'(被保人)') from lcinsured where 1=1 "
//	       + " and contno = '" +tContNo + "'"
//	       + " union select AppntNo, concat(trim(AppntName),'(投保人)') from LCAppnt where 1=1 "
//	       + " and contno = '" +tContNo + "'"
//	       + " union select insuredno,concat(trim(name),'(被保人)') from lpinsured where contno ='"+tContNo+"'"
//	       + " and edorno = '"+tEdorNo + "'"
//	       + " union select appntno,concat(trim(AppntName),'(投保人)') from lpappnt where contno ='"+tContNo+"'"
//	       + " and edorno = '"+tEdorNo + "'";
  
    var sqlid3="EdorAppMUHealthSq3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("bq.EdorAppMUHealthSql");
	mySql3.setSqlId(sqlid3); //指定使用SQL的id
	mySql3.addSubPara(tContNo);//指定传入参数
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tEdorNo);
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tEdorNo);
	var strSql = mySql3.getString();
  
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  if (turnPage.strQueryResult == "")
  {
    alert("查询失败！");
    return "";
  }

  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;

  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
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

  fm.InsureNo.CodeData = returnstr;
  return returnstr;
}

//初始化医院
function initHospital(tContNo,tEdorNo)
{
  var i,j,m,n;
  var returnstr;
	
//  var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
  
    var sqlid4="EdorAppMUHealthSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("bq.EdorAppMUHealthSql");
	mySql4.setSqlId(sqlid4); //指定使用SQL的id
	mySql4.addSubPara(tContNo);//指定传入参数;
	var strSql = mySql4.getString();
  
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

  //判断是否查询成功
  if (turnPage.strQueryResult == "")
  {
//  	strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lpcont where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+"')";
  	
  	var sqlid5="EdorAppMUHealthSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("bq.EdorAppMUHealthSql");
	mySql5.setSqlId(sqlid5); //指定使用SQL的id
	mySql5.addSubPara(tContNo);//指定传入参数;
	mySql5.addSubPara(tEdorNo);
	var strSql = mySql5.getString();
  	
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
    if (turnPage.strQueryResult == "")
    {
          alert("医院初始化失败！");
          return "";
    }
  }

  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
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

  fm.Hospital.CodeData = returnstr;

  return returnstr;
}
  
