
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var arrDataSet;
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

var tDate ;
//提交，保存按钮对应操作
function signPol() {
	if (PublicWorkPoolGrid.mulLineCount == 0) {
		alert("请在签单前选择一份保单");
		return;
	}

	var i = 0;
	var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,
			//"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 
	//document.all("signbutton").disabled = true;
	//showSubmitFrame(mDebug);
	lockScreen('lkscreen');
	document.getElementById("fm").submit(); //提交 
}


//缴费催办查询__暂时注销
function UrgPayQuery()
{ 
  var checkedRowNum = 0 ;
  var rowNum = PolGrid. mulLineCount ; 
  var count = -1;
  for ( var i = 0 ; i< rowNum ; i++ )
  { 
  	if( checkedRowNum > 1)
      break;
  	if(PolGrid.getChkNo(i)) 
  	{
  	   checkedRowNum = checkedRowNum + 1;
       count = i;
    }
  }  
  if(checkedRowNum == 1)
  {
  	var cProposalNo = PolGrid.getRowColData(count,1,PolGrid);//投保单号
  	//alert(cProposalNo);
  	window.open("../uw/OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures);
  }
  if(checkedRowNum == 2 || checkedRowNum == 0)
  {			
	alert("请只选择一条投保单进行缴费催办查询!");  	
  }
  
}

//发缴费催办通知书_暂时注销,前台及后台程序均已实现.只是报表未描述.待MS提出需求后在启用
function SendUrgPay()
{
  var checkedRowNum = 0 ;
  var rowNum = PolGrid. mulLineCount ; 
  var count = -1;
  for ( var i = 0 ; i< rowNum ; i++ )
  { 
  	if( checkedRowNum > 1)
      break;
  	if(PolGrid.getChkNo(i))
  	 {
  	   checkedRowNum = checkedRowNum + 1;
       count = i;
      }
  } 
  if(checkedRowNum == 1)
  {
  	var cProposalNo = PolGrid.getRowColData(count,1,PolGrid);//投保单号
        cOtherNoType="00"; //个人投保单主险号码类型
        cCode="15";        //缴费催办通知书单据类型  
   if (cProposalNo != "")
   {
	  	//showModalDialog("../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");	  
		var urlStr1 = "../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='提示';   //网页名称，可为空; 
	var iWidth=20+"cm";      //弹出窗口的宽度; 
	var iHeight=10+"cm";     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   }
  }
  if(checkedRowNum == 2 || checkedRowNum == 0)
  {			
	alert("请只选择一条投保单进行发缴费催办通知书!");  	
  }
  
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
 unlockScreen('lkscreen');  
  //content.
  document.all("signbutton").disabled=false;

  if (FlagStr == "Fail" )
  {   
	//if(content.length>1480)
	//{
	  //content="后台反馈的提示信息过多，已超过最大显示范围！请减少提交纪录数量(建议18条数据以下)";	
	//} 
	 alert(content);
//	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ; 
//    showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:800px;dialogHeight:450px");   
  }
  else
  { 
  	
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ; 
    //showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:800px;dialogHeight:450px");   
 	//initForm();
	easyQueryClick();
    //执行下一步操作
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

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
// 查询按钮
function easyQueryClick()
{
	initPolGrid();
	k++;
	
	var year = new Date().getFullYear();
	var month = new Date().getMonth()+ 1;
	var inputDay = fm.AppntCompDay.value;
	var tDay = new Date().getDate();
	var day = tDay;
	var strOperate="like";
	tDate = year+"-"+month+"-"+day;
	
	var tScanDate = "";
	var tSCanTime = "";
	//
	if(document.all('ScanDate').value!=null&&document.all('ScanDate').value!="")
	{
		tScanDate = document.all('ScanDate').value;
		tSCanTime = "00:00:00";
		if(document.all('SCanTime').value!=null&&document.all('SCanTime').value!="")
		{
			 tSCanTime = document.all('SCanTime').value;
		}
		tScanDate = tScanDate + " " + tSCanTime;
	}
	
	    var addStr1 = "  ";
	    var addStr2 = "  ";
	    var addStr3 = " ";
	    var addStr4 = "  ";
	    var sqlid1="ProposalSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalSignSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
//		mySql1.addSubPara(k);//指定传入的参数
//		mySql1.addSubPara(k);//指定传入的参数
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql1.addSubPara(fm.AppntCompDay.value);//指定传入的参数
		mySql1.addSubPara(comcode);//指定传入的参数
//	    var strSql =mySql1.getString();	
	
//	var strSql = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp5,lwmission.MissionProp6,lwmission.MissionProp7,LWMission.MissionID ,LWMission.SubMissionID,LWMission.missionprop3 from lwmission where "+k+"="+k
//	         + " and LWMission.ProcessID = '0000000003' " 
//             + " and LWMission.ActivityID = '0000001150' " 
//             + getWherePart('lwmission.MissionProp1','ContNo',strOperate)
//			 + getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//			 + getWherePart('lwmission.MissionProp3','AgentCode',strOperate)
//			 + getWherePart('lwmission.MissionProp7','ManageCom',strOperate)
//			 + getWherePart('lwmission.MissionProp6','AppntCompDay',' = ')
//			 + " and LWMission.MissionProp7 like '" + comcode + "%%'";  //集中权限管理体现	
			 if(document.all("SaleChnl").value!=""){
			 	addStr1 = " and exists(select 'x' from lccont where lccont.contno = lwmission.missionprop1 and lccont.salechnl='"+document.all("SaleChnl").value+"')";
			 }
			 
			 if(document.all('RiskCode').value!="")
			 {
			 	  addStr2 = " and exists (select '1' from lcpol where contno=lwmission.missionprop1 and riskcode= '"+document.all('RiskCode').value+"')";
			 	}
			 
			 if(document.all('EnterAccFlag').value=="Y")
			 {
			 	 	 addStr3 = " and exists (select '1' from ljtempfee where otherno=lwmission.missionprop1 and enteraccdate is not null and confflag='0' )";
			 }
			 else if(document.all('EnterAccFlag').value=="N")
			 {
			 	addStr3 = " and not exists (select '1' from ljtempfee where otherno=lwmission.missionprop1 and enteraccdate is not null and confflag='0' )";
			 }	
			 if(tScanDate!=null&&tScanDate!="")
			 {
			 	 addStr4 = " and '"+tScanDate+"'>=(select (case when min(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)) is not null then min(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)) else 'NOSCAN' end) from es_doc_main where doccode=lwmission.missionprop1) ";
			 }
			 	
//			 strSql=strSql+ " order by lwmission.MissionProp6 asc ";
			
		//	prompt('1',strSql);
	//查询SQL，返回结果字符串
  mySql1.addSubPara(addStr1);//指定传入的参数
  mySql1.addSubPara(addStr2);//指定传入的参数
  mySql1.addSubPara(addStr3);//指定传入的参数
  mySql1.addSubPara(addStr4);//指定传入的参数
  var strSql =mySql1.getString();
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	
    alert("未查询到满足条件的数据！");
     return false;
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 20 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  //showCodeName();
}

function easyQueryClick3()
{
  initPolGrid();
  	k++;
 
		 var year = new Date().getFullYear();
		 var month = new Date().getMonth()+ 1;
		 var inputDay = fm.AppntCompDay.value;
		 var tDay = new Date().getDate();
		 var day = tDay;
		 var strOperate="like";
		 tDate = year+"-"+month+"-"+day;
		 
		var sqlid2="ProposalSignSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalSignSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(k);//指定传入的参数
		mySql2.addSubPara(k);//指定传入的参数
		mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql2.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql2.addSubPara(fm.AppntCompDay.value);//指定传入的参数
		mySql2.addSubPara(comcode);//指定传入的参数
	    var strSql =mySql2.getString();	
		 
//	var strSql = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp5,lwmission.MissionProp6,lwmission.MissionProp7,LWMission.MissionID ,LWMission.SubMissionID from lwmission where "+k+"="+k
//	         + " and LWMission.ProcessID = '0000000003' " 
//             + " and LWMission.ActivityID = '0000001150' " 
//             + getWherePart('lwmission.MissionProp1','ContNo',strOperate)
//			 + getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//			 + getWherePart('lwmission.MissionProp3','AgentCode',strOperate)
//			 + getWherePart('lwmission.MissionProp7','ManageCom',strOperate)
//			 + getWherePart('lwmission.MissionProp6','AppntCompDay',' < ','4')
//			 + " and LWMission.MissionProp7 like '" + comcode + "%%'";  //集中权限管理体现	
//			 + " order by lwmission.MissionProp6 asc "
//					 ;
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {  	
   
     return "";
  }
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 20 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql  
  
 
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);


}

function easyQueryClick2()
{
  initPolGrid();
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
  showInfo.focus();

		var sqlid3="ProposalSignSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalSignSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(comcode);//指定传入的参数
		mySql3.addSubPara(fm.ProposalNo.value);//指定传入的参数
		mySql3.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql3.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql3.addSubPara(fm.RiskCode.value);//指定传入的参数
		mySql3.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql3.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql3.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql3.addSubPara(comcode);//指定传入的参数
	    var strSql =mySql3.getString();	


	// 书写SQL语句
//  var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName,UWDate from LCPol where 1=1 "
//				 + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+comcode+"','Ph')=1 "
//				 + "and AppFlag='0' "						// 投保状态				
//				 + "and ApproveFlag='9' "					// 已经复核
//				 + "and UWFlag in ('9','4') "			    // 核保通过
//				 + "and GrpPolNo='00000000000000000000' "	// 个人投保单
//				 + "and ContNo='00000000000000000000' "		// 个人投保单
//				 + getWherePart( 'ProposalNo' )
//				 + getWherePart( 'ManageCom','ManageCom', 'like' )
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'RiskCode' )
//				 + getWherePart( 'PrtNo' )
//				 + "and PrtNo in (select PrtNo from lcpol where  1=1 " 			
//				 + "and ApproveFlag='9' "					// 已经复核
//				 + "and UWFlag in ('9','4') "			    // 核保通过
//				 + "and GrpPolNo='00000000000000000000' "	// 个人投保单
//				 + "and ContNo='00000000000000000000' "		// 个人投保单
//				 + getWherePart( 'ManageCom','ManageCom', 'like' )
//				 + getWherePart( 'AgentCode' )
//				 + " and PolNo=MainPolNo )"	 
//				 + " and PolNo=MainPolNo "  //主险
//				 + " and ManageCom like '" + comcode + "%%'"
//				 + " Order by UWDate";

	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	showInfo.close();
    alert("未查询到满足条件的数据！");
     return false;
  }
  
  //设置查询起始位置
  //turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageLineNum = 20 ;
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  showInfo.close();
  //showCodeName();
}

//发首交通知书
function SendFirstNotice()
{

  cOtherNoType="00"; //其他号码类型
  cCode="07";        //单据类型
  var checkedRowNum = 0 ;
  var rowNum = PolGrid. mulLineCount ; 
  var count = -1;
  
  for ( var i = 0 ; i< rowNum ; i++ )
  { 
  	if( checkedRowNum > 1)
      break;
  	if(PolGrid.getChkNo(i)) 
  	{
  	   checkedRowNum = checkedRowNum + 1;
       count = i;
    }
  }  
  if(checkedRowNum == 1)
  {
  	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
  	var cProposalNo = PolGrid.getRowColData(count,1,PolGrid);//投保单号
  	//showModalDialog("../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	
	var urlStr1 = "../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='提示';   //网页名称，可为空; 
	var iWidth=20+"cm";      //弹出窗口的宽度; 
	var iHeight=10+"cm";     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
  	showInfo.close();
  }
  else
  {
  	alert("请先选择一条保单发首期缴费通知书!");
  }
}

/*************************************************************
 *  查询拼串工具
 *  参数  ：  fieldName -- 字段名称
 *			 controlName -- 控件名称
 *			 strOperate -- 操作符
 *			 type -- 字段类型( 0:字符型　1:数字型  3:date )
 *  返回值：  拼好的串
 *************************************************************
 */
function getWherePart( fieldName, controlName, strOperate, fieldType )
{
	var strWherePart = "";
	var value = "";
	if( controlName == "" || controlName == null ) controlName = fieldName;
	value = eval( "fm." + trim( controlName ) + ".value" );
	if( value == "" || value == null ) return strWherePart;
	if( fieldType == null || fieldType == "" ) fieldType = "0";
	if( strOperate == "" || strOperate == null ) strOperate = "=";
	if( fieldType == "0" )	// 0:字符型
	{
		if(strOperate == "like")
		{
			strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%25'";
		}
		else
		{
			strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + "'";
		}
	}	
	if( fieldType == "1" )	// 1:数字型
	{
		strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + trim( value ) + " ";
	}
	if(fieldType == "3") //3:date
	{
		strWherePart = " and "+ trim( fieldName )+ trim(strOperate ) + "to_date('"+trim( value )+"','YYYY-MM-DD')"  + " ";	
	}
	if(fieldType == "4")//4:天数差
	{
			strWherePart = " and "+ trim(fieldName) + trim(strOperate)+ "trim(datediff(now() , "+trim(value)+")) ";	
			//strWherePart = " and "+fieldName +" <'"+tDate+"'";	
			
	}
	return strWherePart;
}	

function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	//if(document.all('ManageCom').value==""){
	//	 alert("请先录入管理机构信息！");
	//	 return;
	//}
  if(document.all('AgentCode').value == "")	{
  	//tongmeng 2007-09-10 modify
  	//查询代理人需要查询各种渠道。
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
      	//tongmeng 2007-09-10 modify
  	//查询代理人需要查询各种渠道。
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
		var sqlid4="ProposalSignSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalSignSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(cAgentCode);//指定传入的参数
	    var strSQL =mySql4.getString();	
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode " 
//	         + " and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL);
    //alert(arrResult);
    if (arrResult != null) 
    {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
//  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
     
      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //   	
    	//    fm.ManageCom.value = fm.AgentManageCom.value;
    	//    fm.ManageComName.value = fm.AgentManageComName.value;
    	//    //showCodeName('comcode','ManageCom','AgentManageComName');  //代码汉化
    	//   
      //}
      //showContCodeName();
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，请确认!");
    }
   
	}
}

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
  	  //fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}
