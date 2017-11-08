//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;


// 查询按钮
function easyQueryClick() {   
	//首先查询历史修改信息
	// 初始化表格
	initPolGrid();

	if((fm.ManageCom.value==""||fm.ManageCom.value==null)){
		alert("请输入机构代码");
		return false;
	}
	var tManageCom = fm.ManageCom.value;
	// 书写SQL语句
	var strSQL = "";
	
//	strSQL = "select managecom,manauwtype,uwlevel, "
//			 			+ " concat(concat(to_char(makedate2,'YYYY-MM-DD') , ' ') , maketime2)"
//			 			+ " from LDUWDifferenceB "
//			 			+ " where 1=1 and managecom='"+tManageCom+"'"
//			 			+ " and difftype='0' "
//			 			+ " order by makedate2,maketime2";
	
	    var  sqlid1="RegionalDisparityInputSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.RegionalDisparityInputSql"); //指定使用的properties文件名
	 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	 	mySql1.addSubPara(tManageCom);//指定传入的参数
	     strSQL=mySql1.getString();
//	prompt("",strSQL);
	
	//查询SQL，返回结果字符串
	turnPage.queryModal(strSQL,PolGrid);
	
	//查询机构当前信息
	//strSQL = "select othersign,comcode from ldcode where codetype='station' and code='"+tManageCom+"'";

    var  sqlid2="RegionalDisparityInputSql1";
 	var  mySql2=new SqlClass();
 	mySql2.setResourceName("uw.RegionalDisparityInputSql"); //指定使用的properties文件名
 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
 	mySql2.addSubPara(tManageCom);//指定传入的参数
     strSQL=mySql2.getString();
	var arrResult = easyExecSql(strSQL); 
	if (arrResult != null) {
		fm.UWLevel.value = arrResult[0][0];
    	fm.UWClass.value = arrResult[0][1];
    	showOneCodeName('uwlevel','UWLevel','UWLevelName');
    	showOneCodeName('areauwclass','UWClass','UWClassName');
    } else {
    	fm.UWLevel.value = "";
    	fm.UWClass.value = "";
    }
}



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ) {
  showInfo.close();
  if (FlagStr == "Fail" ) {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
  } else {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
    initForm();
  }
  unlockScreen('lkscreen');
}


function queryAgent() {
	if(document.all('AgentCode').value == "")	{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comCode+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
		var cAgentCode = fm.AgentCode.value;  //保单号码
//	   	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//		         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' ";//and a.managecom='"+document.all("ManageCom").value+"'";
	    var  sqlid3="RegionalDisparityInputSql2";
	 	var  mySql3=new SqlClass();
	 	mySql3.setResourceName("uw.RegionalDisparityInputSql"); //指定使用的properties文件名
	 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	 	mySql3.addSubPara(cAgentCode);//指定传入的参数
	    strSQL=mySql3.getString();
	    var arrResult = easyExecSql(strSQL); 
	    //alert(arrResult);
	    if (arrResult != null) {
	    	fm.AgentGroup.value = arrResult[0][1];
	    	fm.AgentName.value = arrResult[0][3];
	    } else {
	    	fm.AgentCode.value ="";
	    	fm.AgentGroup.value ="";
	  	  	fm.AgentName.value ="";
	  	  	alert("编码为:["+cAgentCode+"]的业务员不存在，或者不在该管理机构下，或者已经离职，请确认!");
	    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
//为业务员代码、姓名
function afterQuery2(arrResult) {
	if(arrResult!=null) {
		fm.AgentCode.value = arrResult[0][0];
		fm.AgentGroup.value = arrResult[0][1];
		fm.AgentName.value = arrResult[0][3];
	}
}

/**
 * "修改"按钮
 * */
function UpdateAgent(){
	lockScreen('lkscreen');
	if(CheckData()==false){
		unlockScreen('lkscreen');
		return false;
	}
	var showStr="正在保存数据,请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();	document.getElementById("fm").submit();
}

function CheckData(){
	if(fm.ManageCom.value==null||fm.ManageCom.value==""){
		alert("机构代码不能为空！");
		return false;
	}
	if(fm.UWLevel.value==null||fm.UWLevel.value==""){
		if(!confirm("差异化级别为空，是否继续？")){
			return false;
		}
	}
	if(fm.ManageCom.value.length==2){
		alert("不能按总公司修改，至少按二级机构修改！");
		return false;
	}
	if(fm.ManageCom.value.length==4){
		if(!confirm("注意，即将修改二级机构"+fm.ManageCom.value+"下的所有机构的差异化级别，是否继续？")){
			return false;
		}
	}
}