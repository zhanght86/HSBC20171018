//程序名称：EdorUWManuRReport.js
//程序功能：保全人工核保生存调查报告录入
//创建日期：2005-07-14 18:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();


//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
 
  if(fm.SubMissionID.value == "")
  {
  	//alert("已录入生调通知书,但未打印,不容许录入新的生调通知书!");
  	//easyQueryClick();
  	parent.close();
  	return;
  	}
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

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  var	tContNo = fm.ContNo.value;
  var   tEdorNo = fm.EdorNo.value;
  var   tPrtNo = fm.PrtNo.value;
  var	tMissionID = fm.MissionID.value;
  var	tSubMissionID = fm.SubMissionID.value;
  
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
  	initForm(tContNo,tEdorNo,tPrtNo,tMissionID,tSubMissionID);
  	//parent.close();
  	
    //执行下一步操作
  }
}



//查询生调录入任务节点
function easyQueryClickSingle()
{//alert(66);
   
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tEdorNo = fm.EdorNo.value;
	tMissionID = fm.MissionID.value;
    if(tContNo != null && tContNo!="" && tEdorNo!=null && tEdorNo!="")
    {
  	 	
//  	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.ActivityID = '0000000022'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";				
  	var strsql = "";
  	var sqlid1="EdorUWManuRReportSql1";
  	var mySql1=new SqlClass();
  	mySql1.setResourceName("bq.EdorUWManuRReportSql"); //指定使用的properties文件名
  	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
  	mySql1.addSubPara(tMissionID);//指定传入的参数
  	strsql=mySql1.getString();  	
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
    //判断是否查询成功
    if (!turnPage.strQueryResult) {
      alert("该任务节点未生成或已删除，不容许录入新生调通知书！");
      divSubmit.style.display = 'none';                 //隐藏提交按钮
   	  divReturn.style.display = '';
      return "";
    }
    divSubmit.style.display = '';
   	divReturn.style.display = '';
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
  
    turnPage = new turnPageClass();			 
 				
  }
  return true;
}
//初始化接收对象信息
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
//	       + " union select appntno,concat(trim(appntname),'(投保人)') from lpappnt where contno ='"+tContNo+"'"
//	       + " and edorno = '"+tEdorNo + "'"
//	       + " union select agentcode,concat(trim(name),'(代理人)') from laagent where trim(AgentCode) in "
//	       + " (select trim(AgentCode) from LpCont where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+"')" ;
  
    var strSql = "";
	var sqlid2="EdorUWManuRReportSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorUWManuRReportSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tEdorNo);//指定传入的参数
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tEdorNo);//指定传入的参数
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tEdorNo);//指定传入的参数	
	strSql=mySql2.getString();  
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
//接收对象下拉列表框得到焦点时触发函数
function queryRRInfo()
{	
    //由于在tCustomerName中含有客户身份（投保人、被保人）的信息，所以在取名字时应将此信息剔除	
	var tCustomerName = fm.InsureNoname.value;
    var mName = "";
    if(tCustomerName!="")
    {
          for (var i = 0; i < tCustomerName.length; i++)
          {
              if (tCustomerName.substring(i,i + 1) == "(")
              {
                  break;
              }
              mName += tCustomerName.substring(i,i + 1);
          }
    }
	fm.InsureNoname.value = mName;
	
	//选择接受对象时，显示其已录入生调信息
	var tContNo = "";
    var tEdorNo = "";
    tContNo = fm.ContNo.value;
    tEdorNo = fm.EdorNo.value;	 
    tInsuredNo = fm.InsureNo.value;
//	var strsql = "select contente,rreportreason "
//	             + " from LPRReport where 1=1"
//				 + " and ContNo = '"+ tContNo + "' and edorno = '"+tEdorNo+"'"
//				 + " and customerno = '"+ tInsuredNo + "'";
//    var strsql = "select contente "
//	             + " from LPRReport where 1=1"
//				 + " and ContNo = '"+ tContNo + "' and edorno = '"+tEdorNo+"'"
//				 + " and customerno = '"+ tInsuredNo + "'";				 
	    
    var strsql = "";
	var sqlid3="EdorUWManuRReportSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("bq.EdorUWManuRReportSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	mySql3.addSubPara(tEdorNo);//指定传入的参数
	mySql3.addSubPara(tInsuredNo);//指定传入的参数
	strsql=mySql3.getString();  	
    brr = easyExecSql(strsql);

    if (!brr) 
    {
      fm.Contente.value  = "";
      fm.RReportReason.value  = "";
     }
    else
    {
       //查询成功则拆分字符串，返回二维数组
       brr[0][0]==null||brr[0][0]=='null'?'0':fm.Contente.value  = brr[0][0];
       //brr[0][1]==null||brr[0][1]=='null'?'0':fm.RReportReason.value  = brr[0][1];
    }
    
//    strsql = "select RReportItemCode,RReportItemName from lprreportitem "
//              + " where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+ "' "
//              + " and prtseq in(select distinct prtseq from lprreport "
//              + " where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+ "' " 
//              + " and customerno ='"+tInsuredNo+"')";
  
    var strsql = "";
	var sqlid4="EdorUWManuRReportSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("bq.EdorUWManuRReportSql"); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	mySql4.addSubPara(tEdorNo);//指定传入的参数
	mySql4.addSubPara(tContNo);//指定传入的参数
	mySql4.addSubPara(tEdorNo);//指定传入的参数
	mySql4.addSubPara(tInsuredNo);//指定传入的参数
	strsql=mySql4.getString();  	
    
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
    if (turnPage.strQueryResult)
    {    
      initInvestigateGrid();
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      turnPage.pageDisplayGrid = InvestigateGrid;    
      turnPage.strQuerySql     = strsql; 
      turnPage.pageIndex       = 0;  
  	  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 	}
    else
    {   
    	 initInvestigateGrid();
    }            
	
	return true;
}
