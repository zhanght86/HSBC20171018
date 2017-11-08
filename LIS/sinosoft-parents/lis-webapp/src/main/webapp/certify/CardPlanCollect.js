var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tResourceName="certify.CardPlanCollectInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  if(CardPlanQueryGrid.checkValue()==false || CardPlanQueryGrid2.checkValue()==false)
  {
	return false;
  }
  if(CardPlanQueryGrid.getChkCount()==0 && CardPlanQueryGrid2.getChkCount()==0){
    alert("请至少选择一个待汇总计划！");
    return false;
  }
  if (confirm("您确认印刷计划汇总吗?")){ 
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanCollectSave.jsp';
    document.getElementById("fm").submit(); //提交
  }else{
    alert("您取消了印刷计划汇总操作！");
  }
}

//Click事件，待汇总计划查询
function queryClickD()
{  
  if(fm.managecom.value.length != 2 ){
    alert("只有总公司才能操作此菜单，请重新登录！");
    return;
  } 

  document.getElementById("HZbutton").disabled = false;//可以汇总
  var strSql ='';
  var arrResult ;
  if(fm.PlanType.value==""){
    alert("请输入【计划类型】！");
    return false;
  }

  if(fm.PlanType.value=='6'){//自印单证
    divCardPlanQuery.style.display="none";
    divCardPlanQuery2.style.display='';
    initCardPlanQueryGrid2();
    /*strSql = "select a.certifycode,"
		+"(select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+"(select nvl(certifyprice, 0) from lmcertifydes where certifycode = a.certifycode),"
		+"(retcount - assigncount),"
		+"'0',"
		+"(retcount - assigncount),"
		+"(retcount - assigncount)*(select nvl(certifyprice, 0) from lmcertifydes where certifycode = a.certifycode),"
		+"a.appcom, '', '' "
		+" from lzcardplan a where a.planstate = 'R' and a.relaplan is null and a.relaprint is null"
	 	+  getWherePart('a.PlanType', 'PlanType')
	 	+  getWherePart('a.MakeDate', 'StartDate', '>=')
	 	+  getWherePart('a.MakeDate', 'EndDate', '<=')
	 	+  getWherePart('a.retcom', 'managecom', '=')
 		+" order by a.certifycode";*/
    strSql = wrapSql(tResourceName,"querysqldes1",[document.all('PlanType').value,document.all('StartDate').value
                                         ,document.all('EndDate').value,fm.managecom.value]);
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
    if (!turnPage.strQueryResult) {
 	  alert("没有符合条件的单证记录！");
      return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid2";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid2);
	}  
  }else{//非自印单证
    divCardPlanQuery.style.display='';
    divCardPlanQuery2.style.display="none";
    initCardPlanQueryGrid();
    /*strSql = "select a.certifycode,"
		+"(select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+"(select nvl(certifyprice, 0) from lmcertifydes where certifycode = a.certifycode),"
		+"sum(retcount - assigncount),"
		+" '0',"
		+"sum(retcount - assigncount),"
		+"sum(retcount - assigncount)*(select nvl(certifyprice, 0) from lmcertifydes where certifycode = a.certifycode)"
		+" from lzcardplan a where a.planstate = 'R' and a.relaplan is null and a.relaprint is null"
	 	+  getWherePart('a.PlanType', 'PlanType')
	 	+  getWherePart('a.MakeDate', 'StartDate', '>=')
	 	+  getWherePart('a.MakeDate', 'EndDate', '<=')
	 	//zy 2009-11-25 17:55 增加对批复机构的控制，防止数据重复
	 	+  getWherePart('a.retcom', 'managecom', '=')
 		+" group by a.certifycode, a.plantype order by a.certifycode";*/
    strSql = wrapSql(tResourceName,"querysqldes2",[document.all('PlanType').value,document.all('StartDate').value
                                     ,document.all('EndDate').value,fm.managecom.value]);
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
    if (!turnPage.strQueryResult) {
 	  alert("没有符合条件的单证记录！");
      return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
  }
}

//已汇总计划查询
function queryClickY()
{  
  if(fm.managecom.value.length != 2 ){
    alert("只有总公司才能操作此菜单，请重新登录！");
    return;
  } 
  
  document.getElementById("HZbutton").disabled = true;//不可以汇总
  var strSql ='';
  var arrResult ;
  if(fm.PlanType.value==""){
    alert("请输入【计划类型】！");
    return false;
  }
  
  if(fm.PlanType.value=='6'){//自印单证
    divCardPlanQuery.style.display="none";
    divCardPlanQuery2.style.display='';
    initCardPlanQueryGrid2();
    /*strSql = "select a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode), "
		+" a.certifyprice, a.sumcount - a.parentnum, a.parentnum, a.sumcount, a.sumcount * a.certifyprice, "
		+" a.managecom, a.startno, a.endno "
		+" from lzcardprint a where a.state = '0'"
	 	+ getWherePart('a.PlanType', 'PlanType')
	 	+  getWherePart('a.InputDate', 'StartDate', '>=')
	 	+  getWherePart('a.InputDate', 'EndDate', '<=')
 		+" order by a.certifycode";*/
    strSql = wrapSql(tResourceName,"querysqldes3",[document.all('PlanType').value,document.all('StartDate').value
                                     ,document.all('EndDate').value]);
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
    if (!turnPage.strQueryResult) {
 	  alert("没有符合条件的单证记录！");
      return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid2";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid2);
	}  
  }else{//非自印单证
    divCardPlanQuery.style.display='';
    divCardPlanQuery2.style.display="none";
    initCardPlanQueryGrid();
    /*strSql = "select a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode), "
		+" a.certifyprice, a.sumcount - a.parentnum, a.parentnum, a.sumcount, a.sumcount * a.certifyprice "
		+" from lzcardprint a where a.state = '0'"
		+  getWherePart('a.InputDate', 'StartDate', '>=')
	 	+  getWherePart('a.InputDate', 'EndDate', '<=')
	 	+  getWherePart('a.PlanType', 'PlanType')
 		+" order by a.certifycode";*/
    strSql = wrapSql(tResourceName,"querysqldes4",[document.all('PlanType').value,document.all('StartDate').value
                                     ,document.all('EndDate').value]);
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
    if (!turnPage.strQueryResult) {
 	  alert("没有符合条件的单证记录！");
      return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
  }
}

function calcSumNum()
{
  for(var i=0;i<CardPlanQueryGrid.mulLineCount;i++)
  {
    var certifyPrice = CardPlanQueryGrid.getRowColData(i,3);
    var ChildNum = isNaN(parseInt(CardPlanQueryGrid.getRowColData(i,4),10))?0:parseInt(CardPlanQueryGrid.getRowColData(i,4),10);  
    var parentNum = isNaN(parseInt(CardPlanQueryGrid.getRowColData(i,5),10))?0:parseInt(CardPlanQueryGrid.getRowColData(i,5),10);
    //alert(certifyPrice);
    //alert(ChildNum);
    //alert(parentNum);
    
    var sumNum = ChildNum + parentNum;
    var sumBalance = accMul(sumNum,certifyPrice);
    //var sumBalance = sumNum * certifyPrice;
    //alert(sumNum);
    //alert(sumBalance);
    
    CardPlanQueryGrid.setRowColData(i, 6, ""+sumNum);
    CardPlanQueryGrid.setRowColData(i, 7, ""+sumBalance);
  }
  
  for(var i=0;i<CardPlanQueryGrid2.mulLineCount;i++)
  {
    var certifyPrice = CardPlanQueryGrid2.getRowColData(i,3);
    var ChildNum = isNaN(parseInt(CardPlanQueryGrid2.getRowColData(i,4),10))?0:parseInt(CardPlanQueryGrid2.getRowColData(i,4),10);  
    var parentNum = isNaN(parseInt(CardPlanQueryGrid2.getRowColData(i,5),10))?0:parseInt(CardPlanQueryGrid2.getRowColData(i,5),10);
    //alert(certifyPrice);
    //alert(ChildNum);
    //alert(parentNum);
    
    var sumNum = ChildNum + parentNum;
    var sumBalance = accMul(sumNum,certifyPrice);
    //var sumBalance = sumNum * certifyPrice;
    //alert(sumNum);
    //alert(sumBalance);
    
    CardPlanQueryGrid2.setRowColData(i, 6, ""+sumNum);
    CardPlanQueryGrid2.setRowColData(i, 7, ""+sumBalance);
  }
}

function easyPrint()
{
  if(fm.PlanType.value=='6' && CardPlanQueryGrid2.mulLineCount<=0){
    alert("请先查询数据然后打印！");
    return false ;
  }else if(fm.PlanType.value!='6' && CardPlanQueryGrid.mulLineCount<=0){
    alert("请先查询数据然后打印！");
    return false ;
  }
	easyQueryPrint(2,'CardPlanQueryGrid','turnPage');
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    queryClick();
  }
}
