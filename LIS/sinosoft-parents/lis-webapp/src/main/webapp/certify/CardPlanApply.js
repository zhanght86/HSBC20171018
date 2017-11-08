var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tResourceName="certify.CardPlanApplyInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  if(fm.managecom.value.length != 4 && fm.managecom.value.length != 6 ){
    alert("只有分公司和支公司才能操作此菜单，请重新登录！");
    return;
  } 

  if(fm.PlanType1.value=="")
  {
    alert("请录入计划类型！");
	return;
  }
  
  CardPlanGrid.delBlankLine("CardPlanGrid");
  var nmulLineCount = CardPlanGrid.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("请至少输入一个要申请的计划！ ");
    return false;
  }
  
  if(CardPlanGrid.checkValue()==false)
  {
	return false;
  }
  
  //校验不同行的单证编码不能相同
  for(var i=1; i<=CardPlanGrid.mulLineCount; i++){
    var certifyCode = CardPlanGrid.getRowColData(i-1,1);
    for(var j=i+1; j<=CardPlanGrid.mulLineCount; j++){
      var certifyCode2 = CardPlanGrid.getRowColData(j-1,1);
      if(certifyCode2==certifyCode){
        alert("第"+i+"行单证编码和第"+j+"行行单证编码相同，相同计划类型的单证编码最多只能申请一次，请检查！");
        return false;
      }
    }  
  }
  
  fm.OperateType.value = "INSERT||APPLY";
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CardPlanApplySave.jsp';
  document.getElementById("fm").submit(); //提交
}

//Click事件，当点击“查询”时触发该函数
function queryClick()
{
  if(fm.managecom.value.length != 4 && fm.managecom.value.length != 6 ){
    alert("只有分公司和支公司才能操作此菜单，请重新登录！");
    return;
  }
  
  initCardPlanQueryGrid();
  /*var strSql = "select a.planid, a.certifycode, a.plantype, (select certifyprice from lmcertifydes where certifycode=a.certifycode) price,a.appcount, (select certifyprice*a.appcount  from lmcertifydes  where certifycode = a.certifycode),a.appoperator, a.appcom, a.PlanState, a.makedate	"
  			+" from lzcardplan a where 1=1 and a.planstate='A' and a.appcom='"+ fm.managecom.value +"'"
  			+ getWherePart('a.PlanID', 'PlanID')
  			+ getWherePart('a.CertifyCode', 'CertifyCode')
  			+ getWherePart('a.PlanType', 'PlanType')
  			+" order by a.plantype, a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[fm.managecom.value,document.all('PlanID').value
                                        ,document.all('CertifyCode').value,document.all('PlanType').value]);
  //prompt("",strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("没有符合条件的单证记录！");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  var nChkCount = CardPlanQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("请至少选择一个要提交的计划！ ");
    return false;
  }
  if (confirm("您确实想修改该记录吗?"))
   {
    fm.OperateType.value = "UPDATE||APPLY";
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanApplySave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
}

function deleteClick()
{
  var nChkCount = CardPlanQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("请至少选择一个要提交的计划！ ");
    return false;
  }
  if (confirm("您确实想删除该记录吗?"))
   {
    fm.OperateType.value = "DELETE||APPLY";
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanApplySave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了删除操作！");
  }
}

function clearData()
{
    document.all('PlanID').value = '';
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
    document.all('MakeDate').value = '';
    //document.all('PlanState').value = '';
    initCardPlanQueryGrid();
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

function easyPrint()
{
   	if(CardPlanQueryGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanQueryGrid','turnPage');
}

