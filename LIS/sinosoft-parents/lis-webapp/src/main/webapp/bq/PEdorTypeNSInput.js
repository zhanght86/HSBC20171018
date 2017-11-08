//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var PolNo = "";
var prtNo = "";
var str_sql = "",sql_id = "", my_sql = "";
var mSwitch = parent.VD.gVSwitch;

//初始化险种信息和已录入信息
function initQuery() {	
//  var strSql = "select prtno from lcpol where PolNo='" + document.all('PolNo').value + "'";
  sql_id = "PEdorTypeNSInputSql20";
  my_sql = new SqlClass();
  my_sql.setResourceName("bq.PEdorTypeNSInputSql"); //指定使用的properties文件名
  my_sql.setSqlId(sql_id);//指定使用的Sql的id
  my_sql.addSubPara(document.all('PolNo').value);//指定传入的参数
  str_sql = my_sql.getString();
	prtNo = easyExecSql(str_sql);	
  
//	var strSql = "select code, codename from ldcode1 where code1 in (select distinct riskcode from lcpol where mainpolno=polno and PrtNo='" + prtNo + "')";
	 sql_id = "PEdorTypeNSInputSql21";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeNSInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(prtNo);//指定传入的参数
	  str_sql = my_sql.getString();
	fm.RiskCode.CodeData = easyQueryVer3(str_sql);	
	
  LPInsuredGrid.clearData("LPInsuredGrid");
//	var strSql = "select distinct riskcode, ProposalNo from lcpol where PrtNo='" + prtNo
//	           + "' and riskcode in (select riskcode from lmriskapp where SubRiskFlag='S') and appflag='2' order by proposalno";
	sql_id = "PEdorTypeNSInputSql22";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeNSInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(prtNo);//指定传入的参数
	  str_sql = my_sql.getString();
	turnPage.pageLineNum = 500;
	turnPage.queryModal(strSql, LPInsuredGrid);  
  	
	PolNo = document.all('PolNo').value;
}

//打开险种录入界面
function afterCodeSelect(cCodeName, Field) {
  if (LPInsuredGrid.mulLineCount >= 500) {
    alert("超出该批次最大处理范围，请重新增加一个保全项目！");
    return;
  }
  
  if (cCodeName == "EdorRisk") {
		showInfo = window.open("./ProposalMain.jsp?risk="+Field.value+"&prtNo="+prtNo+"&loadFlag=8"+"&polNo="+fm.PolNo.value);
  }
}

//保存申请
function edorSave() {
  if (LPInsuredGrid.mulLineCount == 0) {
    alert("必须录入后才能保存申请！");
    return;
  }
  
  for (i=0; i<LPInsuredGrid.mulLineCount; i++) {
    if (LPInsuredGrid.getRowColData(i, 2) == "") {
      alert("必须完成所有的险种录入才能保存申请！");
      return;
    }
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
  
  document.all('fmtransact').value = "INSERT";
  fm.submit();
}

//显示提交结果
function afterSubmit( FlagStr, content ) {
	showInfo.close();
           
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//返回，关闭当前窗口
function returnParent() {
  top.close();
}
