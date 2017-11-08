var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardPlanAssignInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  if(CardPlanQueryGrid.checkValue() == false)
  {
	return false;
  }
  
  var nChkCount = CardPlanQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("请至少选择一个要分配的机构！");
    return false;
  }
  
  for(var i=0;i<CardPlanQueryGrid.mulLineCount;i++)
  {
    if(CardPlanQueryGrid.getChkNo(i)){
      if(CardPlanQueryGrid.getRowColData(i,5)==''){
        alert("请输入分配数量！");
        return false;
      }
    }
  }
  
  fm.OperateType.value = "UPDATE||ASSIGN";
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CardPlanAssignSave.jsp';
    document.getElementById("fm").submit(); //提交
}

//Click事件，当点击“查询”时触发该函数
function queryClick()
{
  if(fm.managecom.value.length != 2 ){
    alert("只有总公司才能操作此菜单，请重新登录！");
    return;
  } 
  
  initCardPlanQueryGrid();
  var strSql ='';
  var arrResult ;
  if(fm.CertifyCode.value=="" || fm.PlanType.value==""){
    alert("请输入【单证编码】和【计划类型】！");
    return false;
  }
  /*strSql = "select nvl(sum(a.sumcount),0) from lzcard a where a.receivecom = 'A86' and a.stateflag = '2' "
    	+ getWherePart('a.CertifyCode', 'CertifyCode');*/
  strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value]);
  arrResult = easyExecSql(strSql);
  if (arrResult != null) {
    fm.sumCount.value = arrResult[0][0];//库存总数
  }
  /*strSql = "select nvl(sum(a.assigncount), 0) from lzcardplan a where length(a.appcom) = 4 and a.relaplan is null"
    	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	    + getWherePart('a.PlanType', 'PlanType');*/
  strSql = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value,document.all('PlanType').value]);
  arrResult = easyExecSql(strSql);
  if (arrResult != null) {
    fm.sumAssign.value = arrResult[0][0];//已分配总计
  }
  fm.sumBalance.value = fm.sumCount.value - fm.sumAssign.value;//分配后结余
  
  /*strSql = "select a.planid, a.appcom, (select name from ldcom where comcode = a.appcom), a.retcount, a.assigncount "
	+" from lzcardplan a where a.planstate = 'R' and a.relaplan is null and a.relaprint is null "	
  	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	+ getWherePart('a.PlanType', 'PlanType')
  	+" order by a.appcom";*/
  strSql = wrapSql(tResourceName,"querysqldes3",[document.all('CertifyCode').value,document.all('PlanType').value]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("没有符合条件的单证记录！");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

function clearData()
{
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
    document.all('MakeDate').value = '';
    initCardPlanQueryGrid();
}

function queryClick2()
{
  if(fm.managecom.value.length != 2 ){
    alert("只有总公司才能操作此菜单，请重新登录！");
    return;
  } 
  if(fm.PlanType2.value==""){
    alert("请输入【计划类型】！");
    return false;
  }
  
  initCardPlanListGrid();
  /*var strSql = "select a.appcom, (select name from ldcom where comcode = a.appcom) name,"
			 +"a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode) certifyname,"
			 +"(select nvl(sum(sumcount), 0) from lzcard where certifycode = a.certifycode and receivecom = 'A86' and stateflag in ('2', '3')) sumcount,"
			 +"a.assigncount,"
			 +"(select Printery from lzcardprint where certifycode = a.certifycode"
			 +"		 and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1)"
			 +" from lzcardplan a"
 			 +" where a.planstate = 'R' and a.relaplan is null and a.assigncount > 0"
 			 + getWherePart('a.PlanType', 'PlanType2')
 			 + getWherePart('a.certifycode', 'CertifyCode2')			 
 			 + getWherePart('a.appcom', 'ManageCom2')
			 +" order by a.appcom, a.certifycode" ;*/
  var strSql = wrapSql(tResourceName,"querysqldes4",[document.all('PlanType2').value,document.all('CertifyCode2').value,document.all('ManageCom2').value]);

  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage2.strQueryResult) {
 	 alert("没有符合条件的单证记录！");
     return false;
    }else{
  	  turnPage2.pageDivName = "divCardPlanListGrid";
  	  turnPage2.queryModal(strSql, CardPlanListGrid);
	}

}

//[打印]按钮函数
function certifyPrint()
{
   	if(CardPlanListGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanListGrid','turnPage2');	
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='CertifyClassListNew')
  {
    displayMulLine();
  }else if( cName == 'HaveNumber' ) {
	displayNumberInfo();		
  }else if( cName == 'HaveLimit' ) {
	displayHaveLimit();		
  }else if( cName == 'HaveValidate' ) {
	displayHaveValidate();		
  }
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
    queryClick()
  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
    initCardRiskGrid();
  }
  catch(re)
  {
    alert("初始化页面错误，重置出错");
  }
}

