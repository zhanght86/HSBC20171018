

//DeleteSendToBankInput.js该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var showInfo;

//提交，保存按钮对应操作
function submitForm() {
  if (BankGrid.getSelNo() == 0) {
    alert("请先选择一条要取消的银行数据，银行在途数据不能取消！");
  }
  else {     
    fm.TempFeeNo.value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);
    fm.Action.value = "DELETE";   
    fm.butSave.disabled = false;
    var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
}

//提交，保存按钮对应操作
function submitForm2() {
  if (BankGrid.getSelNo() == 0) {
    alert("请先选择一条要取消的银行数据！");
  }
  else {     
    fm.TempFeeNo.value = BankGrid.getRowColData(BankGrid.getSelNo()-1, 1);   
    fm.Action.value = "UPDATE";  
    fm.butSave2.disabled = false;
    var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
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
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ) {
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}   

// 查询按钮
function easyQueryClick() {
  if (fm.PrtNo.value == "") {
    alert("请先输入印刷号");
    return; 
  }
  
  fm.butQuery.disabled = true;
	// 书写SQL语句		
	//modify by ml 2006-03-17	for improving efficiency     
	//zy 2008-11-01 11:50 修改查询逻辑
//	var strSql = "select /*+rule+*/ a.tempfeeno, a.paymoney, a.bankcode, a.accname, a.bankaccno, b.appntno, b.bankonthewayflag, b.riskcode, b.sendbankcount"
//             + " from ljtempfeeclass a, ljspay b where a.tempfeeno=b.getnoticeno and exists "
//          	 + " (select 1 from ljtempfee where tempfeeno=a.tempfeeno and otherno in "
//          	 + " (select contno from lccont where prtno='" + fm.PrtNo.value + "'" 
//          	 + " union "
//          	 + " select familycontno from lccont where prtno='" + fm.PrtNo.value + "'" 
//          	 + " union "
//          	 + " select '" + fm.PrtNo.value + "' from dual " 
//          	 +"union "
//          	 + " select prtno from lccont where prtno='" + fm.PrtNo.value + "' "
//          	 + " ))" ;
	
	  var sqlid1="DeleteSendToBankInputSql1";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bank.DeleteSendToBankInputSql");
	  mySql1.setSqlId(sqlid1);//指定使用SQL的id
	  mySql1.addSubPara(fm.PrtNo.value);//指定传入参数
	  mySql1.addSubPara(fm.PrtNo.value);//指定传入参数
	  mySql1.addSubPara(fm.PrtNo.value);//指定传入参数
	  mySql1.addSubPara(fm.PrtNo.value);//指定传入参数
	  var strSql = mySql1.getString();
	
	
// zy 2009-06-05 11:23 add by hezy 优化查询逻辑
//   var strSql = "select  a.tempfeeno, a.paymoney, a.bankcode, a.accname, a.bankaccno, b.appntno, b.bankonthewayflag, b.riskcode, b.sendbankcount "
//							+ " from ljtempfeeclass a, ljspay b,ljtempfee c "
//							+ " where '1244162938000' = '1244162938000' "
//							+ "and a.tempfeeno = b.getnoticeno  and a.tempfeeno = c.tempfeeno   and c.otherno in "
//							+ "(select contno from lccont where prtno = '" + fm.PrtNo.value + "' "
//							+ "  union select familycontno from lccont where prtno = '" + fm.PrtNo.value + "' and familycontno is not null "
//							+ "union select '" + fm.PrtNo.value + "'  from dual "
//							+ "union select prtno from lccont where prtno = '" + fm.PrtNo.value + "') "
//							;
       	
				     
  //alert(strSql);

	turnPage.queryModal(strSql, BankGrid);
	  if (!turnPage.strQueryResult) {
    alert("对不起,未查到相应数据。");
    return ;
  }
}

