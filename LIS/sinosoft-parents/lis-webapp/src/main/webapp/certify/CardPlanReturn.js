var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardPlanReturnInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  if(vertifyInput() == false)
  {
	return;
  }
  var nChkCount = CardPlanDetailGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("请至少选择一个要批复的单证！ ");
    return false;
  }
  for(var i=0;i<CardPlanDetailGrid.mulLineCount;i++)
  {
    if(CardPlanDetailGrid.getChkNo(i)){
      if(CardPlanDetailGrid.getRowColData(i,9)==''){
        alert("请输入批复数量！");
        return false;
      }
       if(CardPlanDetailGrid.getRowColData(i,9)-CardPlanDetailGrid.getRowColData(i,8)>0){
        alert("批复数量不能大于调整数量，请重新录入！");
        return false;
      }
    }
  }
  
  fm.OperateType.value = "UPDATE||RETURN";
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CardPlanReturnSave.jsp';
  document.getElementById("fm").submit(); //提交
}

//Click事件，当点击“查询”时触发该函数
function queryClick(flag)
{
  if(fm.managecom.value.length != 2 && fm.managecom.value.length != 4 ){
    alert("只有总公司和分公司才能操作此菜单，请重新登录！");
    return;
  } 

  initCardPlanQueryGrid();
  initCardPlanDetailGrid();
  var tManagecom = fm.managecom.value;
  if(flag=='D'){
    fm.PFbutton.disabled = false;//可以批复
    if(tManagecom.length==2){//总公司登录
      fm.PlanState.value = 'S';
      /*var strSql = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 			+" where a.planstate = 'S' and length(a.appcom) = '4' "
 			+ getWherePart('a.certifycode', 'CertifyCode')
 			+ getWherePart('a.PlanType', 'PlanType')
 			+ getWherePart('a.UpdateDate', 'StartDate', '>=')
 			+ getWherePart('a.UpdateDate', 'EndDate', '<=')
 			+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
      var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value
                                           ,document.all('PlanType').value,document.all('StartDate').value,document.all('EndDate').value]);
    }else if(tManagecom.length==4){
      fm.PlanState.value = 'C';//分公司登录
      /*var strSql = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 			+" where a.planstate = 'C' and a.appcom like '" + tManagecom + "%'"
 			+ getWherePart('a.certifycode', 'CertifyCode')
 			+ getWherePart('a.PlanType', 'PlanType')
 			+ getWherePart('a.UpdateDate', 'StartDate', '>=')
 			+ getWherePart('a.UpdateDate', 'EndDate', '<=')
 			+" and exists (select 1 from lzcardplan b where b.planid=a.relaplan and b.planstate='R') "
 			+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
      var strSql = wrapSql(tResourceName,"querysqldes2",[tManagecom,document.all('CertifyCode').value
                                           ,document.all('PlanType').value,document.all('StartDate').value,document.all('EndDate').value]);
    }else{
      alert("此查询只能总公司和分公司使用!");
      return false;
    }
  }else if(flag=='Y'){
    fm.PFbutton.disabled = true;//不可以批复
    if(fm.StartDate.value=='' || fm.EndDate.value==''){
      alert("请录入【起始日期】和【终止日期】！");
      return false;
    }
    if(tManagecom=='86'){
      alert("总公司无上级已批复计划！");
    }else{
      fm.PlanState.value = 'R';//分公司登录
      /*
      var strSql = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 			+" where a.planstate = 'R' and a.appcom!=a.retcom and a.appcom = '" + tManagecom + "'"
 			+ getWherePart('a.certifycode', 'CertifyCode')
 			+ getWherePart('a.PlanType', 'PlanType')
 			+ getWherePart('a.UpdateDate', 'StartDate', '>=')
 			+ getWherePart('a.UpdateDate', 'EndDate', '<=')
 			+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
      var strSql = wrapSql(tResourceName,"querysqldes3",[tManagecom,document.all('CertifyCode').value
                                           ,document.all('PlanType').value,document.all('StartDate').value,document.all('EndDate').value]);
    }
  }
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("没有查询到单证记录！");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

function showDetail(parm1, parm2)
{
  var tManageCom = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 1);
  var tPlanType = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 2);
  if(fm.managecom.value=='86'){//总公司登录
      /*var strSql = "select a.planid, a.appcom, a.plantype,a.certifycode,"
      	  +"(select certifyname from lmcertifydes where certifycode=a.certifycode),(select certifyprice from lmcertifydes where certifycode=a.certifycode) price, a.appcount, a.ConCount, a.ConCount,(select certifyprice*a.ConCount  from lmcertifydes  where certifycode = a.certifycode)"
    	  +" from lzcardplan a where a.planstate = 'S' and a.appcom = '" + tManageCom
    	  +"' and a.plantype ='" + tPlanType + "'"
    	  + getWherePart('a.certifycode', 'CertifyCode')  
    	  + getWherePart('a.UpdateDate', 'StartDate', '>=')
 		  + getWherePart('a.UpdateDate', 'EndDate', '<=')  	  
    	  +" order by a.certifycode";*/
      var strSql = wrapSql(tResourceName,"querysqldes4",[tManageCom,tPlanType,document.all('CertifyCode').value
                                           ,document.all('StartDate').value,document.all('EndDate').value]);
    }else{
      var mPlanState = fm.PlanState.value;
      if(mPlanState=='C'){//分公司登录，待批复
         /*var strSql = " select a.planid, a.appcom, a.plantype,a.certifycode,"
      	  +"(select certifyname from lmcertifydes where certifycode=a.certifycode),(select certifyprice from lmcertifydes where certifycode=a.certifycode) price, a.appcount,a.ConCount, a.ConCount,(select certifyprice*a.ConCount  from lmcertifydes  where certifycode = a.certifycode)"
		  +" from lzcardplan a where a.planstate = 'C' and a.appcom = '" + tManageCom
  		  +"' and a.plantype ='" + tPlanType + "'"
  		  + getWherePart('a.certifycode', 'CertifyCode')
    	  + getWherePart('a.UpdateDate', 'StartDate', '>=')
 		  + getWherePart('a.UpdateDate', 'EndDate', '<=')    		  
  		  +" order by a.certifycode";*/
         var strSql = wrapSql(tResourceName,"querysqldes5",[tManageCom,tPlanType,document.all('CertifyCode').value
                                              ,document.all('StartDate').value,document.all('EndDate').value]);
  	  }else if(mPlanState=='R'){//分公司登录，已批复
        /* var strSql = " select a.planid, a.appcom, a.plantype,a.certifycode,"
      	  +"(select certifyname from lmcertifydes where certifycode=a.certifycode),(select certifyprice from lmcertifydes where certifycode=a.certifycode) price, a.appcount, a.ConCount, a.retcount,(select certifyprice*a.retcount  from lmcertifydes  where certifycode = a.certifycode)"
		  +" from lzcardplan a where a.planstate = 'R' and a.appcom!=a.retcom and a.appcom = '" + tManageCom
  		  +"' and a.plantype ='" + tPlanType + "'"
  		  + getWherePart('a.certifycode', 'CertifyCode')
    	  + getWherePart('a.UpdateDate', 'StartDate', '>=')
 		  + getWherePart('a.UpdateDate', 'EndDate', '<=')    		  
  		  +" order by a.certifycode";*/
         var strSql = wrapSql(tResourceName,"querysqldes6",[tManageCom,tPlanType,document.all('CertifyCode').value
                                              ,document.all('StartDate').value,document.all('EndDate').value]);
  	  }
    }
  
  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage2.strQueryResult) {
 	 alert("查询单证计划明细出错！");
     return false;
    }else{
  	  turnPage2.pageDivName = "CardPlanDetailGrid";
  	  turnPage2.queryModal(strSql, CardPlanDetailGrid);
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
    queryClick('D');
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

//校验输入
function vertifyInput()
{
  return true;
}
