var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tResourceName="certify.CardPlanConfInputSql";
//Click事件，当点击“查询”时触发该函数
function queryClick()
{
  if(fm.managecom.value.length != 4 ){
    alert("只有分公司才能操作此菜单，请重新登录！");
    return;
  } 

  initCardPlanQueryGrid();
  var tManagecom = fm.managecom.value;
  /*var strSql = "select a.appcom, a.plantype, max(a.appoperator), max(a.updatedate)"
			+" from lzcardplan a where a.relaplan is null and a.planstate = 'C' " 
  			+" and a.appcom like '"+ tManagecom +"%'"
  			+" group by a.appcom, a.plantype order by a.appcom, a.plantype";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[tManagecom]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("没有待调整的单证计划！");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

function showDetail(parm1, parm2)
{
  var tAppcom = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 1);
  var tPlantype = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 2);
  
  initCardPlanQueryDetailGrid();
  /*var strSql="select a.planid,(select certifyclass from lmcertifydes where certifycode = a.certifycode) certifyclass, "
			+" a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode), "
			+" (select certifyprice from lmcertifydes where certifycode=a.certifycode) price,a.appcount, a.concount, "
			+" (select certifyprice*a.concount  from lmcertifydes  where certifycode = a.certifycode)"
  			+" from lzcardplan a where a.relaplan is null and a.planstate = 'C' "
  			+" and a.appcom='" + tAppcom + "' and a.plantype='" + tPlantype + "'"
			+" order by certifyclass, a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes2",[tAppcom,tPlantype]);
  	  turnPage2.pageDivName = "divCardPlanQueryDetailGrid";
  	  turnPage2.queryModal(strSql, CardPlanQueryDetailGrid);
}

//查询此机构下所有单证的汇总结果，展示于页面
function queryPack()
{
  if(fm.managecom.value.length != 4 ){
    alert("只有分公司才能操作此菜单，请重新登录！");
    return;
  } 
  if(fm.PlanType.value==null || fm.PlanType.value==""){
    alert("请输入计划类型！");
    return;  
  }
  
  initCardPlanPackGrid();
  /*var strSql=" select k1,k2,k3,k4,k5,sumcount,(k5*sumcount) k6 from("
	  		+" select a.plantype k1, (select certifyclass from lmcertifydes where certifycode = a.certifycode) k2, "
			+" a.certifycode k3, (select certifyname from lmcertifydes where certifycode = a.certifycode) k4, (select certifyprice from lmcertifydes where certifycode=a.certifycode) k5,"
			+" sum(a.concount) sumcount from lzcardplan a where a.relaplan is null and a.planstate = 'C' "
	 		+" and a.appcom like '" + fm.managecom.value + "%'"
 			+" and a.PlanType = '" + fm.PlanType.value + "'"
 			+" group by a.plantype,a.certifycode ) order by k1,k3";*/
  var strSql = wrapSql(tResourceName,"querysqldes3",[fm.managecom.value,fm.PlanType.value]);
  turnPage3.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage3.strQueryResult) {
 	 alert("没有待汇总的单证计划！");
     return false;
    }else{ 					
  	  turnPage3.pageDivName = "divCardPlanPackGrid";
  	  turnPage3.queryModal(strSql, CardPlanPackGrid);
  	}
}

//Click事件，保存对所选机构的单证调整数量
function updateClick()
{
  if(CardPlanQueryDetailGrid.checkValue()==false){
	return false;
  }
  if(CardPlanQueryDetailGrid.getChkCount()==0){
    alert("请至少选择一个调整的计划！");
    return false;
  }
  for (var i=0;i<CardPlanQueryDetailGrid.mulLineCount;i++ )
  {
    if (CardPlanQueryDetailGrid.getChkNo(i))
    {
  	  var AppCount=CardPlanQueryDetailGrid.getRowColData(i,6);//申请数量
  	  var ConCount=CardPlanQueryDetailGrid.getRowColData(i,7);//调整数量
  	  if(AppCount/1 < ConCount/1){
  	    alert("第"+(i+1)+"行调整数量不能大于申请数量,请重新录入！");
        return false;
  	  }
  	}
  }  
  if (confirm("您确认调整保存吗?"))
  {
    fm.OperateType.value = "UPDATE||CONF";
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanConfSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了调整保存！");
  }
}

//提交，保存按钮对应操作
function submitForm()
{
  var tmulLineCount = CardPlanPackGrid.mulLineCount;
  if (tmulLineCount == null || tmulLineCount <= 0)
  {
    alert("至少要有一个要提交的计划！");
    return false;
  }
  
  if (confirm("您确认汇总提交吗，请确保所有支公司计划调整完毕！")){
    fm.OperateType.value = "INSERT||PACK";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanConfSave.jsp';
    document.getElementById("fm").submit(); //提交
  }else{
    alert("您取消了汇总提交操作！");
  }	 
}

//[打印]按钮函数
function CardPlanDetailPrint()
{
	//alert("查询到的记录行数："+CardPlanQueryDetailGrid.mulLineCount);
   	if(CardPlanQueryDetailGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanQueryDetailGrid','turnPage2');	
}

//[打印]按钮函数
function CardPlanPackPrint()
{
	//alert("查询到的记录行数："+CardPlanPackGrid.mulLineCount);
   	if(CardPlanPackGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanPackGrid','turnPage3');	
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
	initForm();
  }
}
