//               该文件中包含客户端需要处理的函数和事件

var mDebug="0";
var mOperate="";
var showInfo;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//提交，保存按钮对应操作
function submitForm()
{
//  var i = 0;
//  var showStr="正在准备打印数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //fm.hideOperate.value=mOperate;
	if (fm.fmtransact.value=="")
  {
    alert("操作控制数据丢失！");
  }
  //showSubmitFrame(mDebug);
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //执行下一步操作
  }
}


//给付凭证打印
function GPrint()
{ var tSel = PolGrid.getSelNo();	
 if( tSel == 0 || tSel == null )
		{
			alert( "请先选择一条记录，再点击给付凭证打印按钮。" );
			return;
		}
  fm.action="GetCredenceF1PSave.jsp";
  fm.target="f1print";
  fm.fmtransact.value="PRINT";
  submitForm();
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

// 查询按钮
function easyQueryClick()
{
//	alert("here");
	// 初始化表格
	initPolGrid();
	if( (fm.ActuGetNo.value == null || fm.ActuGetNo.value == "")
	&& (fm.OtherNo.value == null || fm.OtherNo.value == "")
	&& (fm.ShouldDate.value == null || fm.ShouldDate.value == "")
	&& (fm.AgentCode.value == null || fm.AgentCode.value == "")){
		alert("实付号码/其它号码/应付日期/代理人编码 请至少填写一项进行查询");
		return false;
		}
	// 书写SQL语句
	var strSQL = "";
	
	//zy 2009-07-14 17:31 调整查询逻辑
	//strSQL = "select ActuGetNo,OtherNo,OtherNoType,SumGetMoney,ShouldDate,ConfDate,Operator,ManageCom,AgentCode from LJAGet where OtherNoType in ('1','2','3','4','5','6','7','9','10') "			 
//	strSQL = "select ActuGetNo,OtherNo,OtherNoType,SumGetMoney,ShouldDate,ConfDate,Operator,ManageCom,AgentCode from LJAGet where 1=1 "			 
//				 + getWherePart( 'ActuGetNo' )
//				 + getWherePart( 'OtherNo' )
//				 + getWherePart( 'OtherNoType' )
//				 + getWherePart( 'ShouldDate' )				 
//				 + getWherePart( 'AgentCode' )
	var MngCom1="";
	var MngCom2="";
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
//		strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		MngCom1=managecomvalue;
	}	
	else
	{
//		strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' );
		MngCom2=window.document.getElementsByName(trim("MngCom"))[0].value;
	}
//		strSQL = strSQL +" and ActuGetNo not in (select otherno from LOPRTManager2 )"	
	//alert(strSQL);
	
	  	var  ActuGetNo0 = window.document.getElementsByName(trim("ActuGetNo"))[0].value;
	  	var  OtherNo0 = window.document.getElementsByName(trim("OtherNo"))[0].value;
	  	var  OtherNoType0 = window.document.getElementsByName(trim("OtherNoType"))[0].value;
	  	var  ShouldDate0 = window.document.getElementsByName(trim("ShouldDate"))[0].value;
	  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
		var sqlid0="GetCredenceInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.GetCredenceInputSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(ActuGetNo0);//指定传入的参数
		mySql0.addSubPara(OtherNo0);//指定传入的参数
		mySql0.addSubPara(OtherNoType0);//指定传入的参数
		mySql0.addSubPara(ShouldDate0);//指定传入的参数
		mySql0.addSubPara(AgentCode0);//指定传入的参数
		mySql0.addSubPara(MngCom1);//指定传入的参数
		mySql0.addSubPara(MngCom2);//指定传入的参数
		strSQL=mySql0.getString();
		
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}

//// 数据返回父窗口
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "请先选择一条记录，再点击给付明细按钮。" );
//	else
//	{
//	    var cActuGetNo = PolGrid.getRowColData(tSel - 1,1);				
////		parent.VD.gVSwitch.deleteVar("PayNo");				
////		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
//		
//		if (cActuGetNo == "")
//		    return;
//		    
//		  var cOtherNoType = PolGrid.getRowColData(tSel - 1,3);
//		  var cSumGetMoney = 	PolGrid.getRowColData(tSel - 1,4);
//		  //alert(cActuGetNo);
//		  //alert(cOtherNoType);
//		  //alert(cSumGetMoney);
//		  if (cOtherNoType==0||cOtherNoType==1||cOtherNoType==2) {
//				window.open("../sys/AllGetQueryDrawDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}	
//			else if (cOtherNoType==3){
//				window.open("../sys/AllGetQueryEdorDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);							
//			}
//			else if (cOtherNoType==4){
//				window.open("../sys/AllGetQueryTempDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==5){
//				window.open("../sys/AllGetQueryClaimDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==6){
//				window.open("../sys/AllGetQueryOtherDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			else if (cOtherNoType==7){
//				window.open("../sys/AllGetQueryBonusDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney);	
//			}
//			
//	}
//}

//暂交费退费打印给付凭证时使用
function initWithdraw() {
  try {
    //保存查询结果字符串
    turnPage.strQueryResult  = top.prtData;
    
    //使用模拟数据源，必须写在拆分之前
    turnPage.useSimulation   = 1;  
      
    //拆分字符串，返回二维数组
    var tArr                 = decodeEasyQueryResult(turnPage.strQueryResult);
    
    //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
    var filterArray          = new Array(0, 1, 2, 5, 7, 9, 18, 26, 29); 
    
    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    
    //过滤二维数组，使之与MULTILINE匹配
    turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
    
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = PolGrid;             
    
    //设置查询起始位置
    turnPage.pageIndex       = 0;  
    
    //在查询结果数组中取出符合页面显示大小设置的数组
    var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  }
  catch(e) {
    alert("初始化退费保单信息失败"); 
  } 
}

//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码	
//		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
		var sqlid1="GetCredenceInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.GetCredenceInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(cAgentCode);//指定传入的参数
		var strSql=mySql1.getString();
		
		var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
