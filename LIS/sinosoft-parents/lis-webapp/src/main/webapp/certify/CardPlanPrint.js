var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tResourceName="certify.CardPlanPrintInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  if(vertifyInput() == false)
  {
	return;
  }
  var nChkCount = CardPrintQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("请至少选择一个要印刷的单证！ ");
    return false;
  }
  for(var i=0;i<CardPrintQueryGrid.mulLineCount;i++)
  {
    if(CardPrintQueryGrid.getChkNo(i)){        
      if(CardPrintQueryGrid.getRowColData(i,8)=='' || CardPrintQueryGrid.getRowColData(i,8)<=0){
        alert("请输入印刷单价, 且印刷单价要大于0！");
        return false;
      }
      
      if(CardPrintQueryGrid.getRowColData(i,9)==''){
        alert("请输入印刷厂名称！");
        return false;
      }   

      if(CardPrintQueryGrid.getRowColData(i,12)!='' && isDate(CardPrintQueryGrid.getRowColData(i,12))==false){
        alert("请输入正确格式的使用截止日期：yy-mm-dddd！");
        return false;
      }      
    }
  }
  if (confirm("您确认有号单证的起止号码、印刷单价、印刷厂名称、使用截止日期等信息均录入正确，并且要印刷吗?")){ 
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanPrintSave.jsp';
    document.getElementById("fm").submit(); //提交
  }else{
    alert("您取消了印刷操作！");
  }
}

//待印刷查询
function queryClick()
{
  if(fm.managecom.value.length != 2 && fm.managecom.value.length != 4 ){
    alert("只有总公司和分公司才能操作此菜单，请重新登录！");
    return;
  } 
  
  initCardPrintQueryGrid();
  fm.buttonP.disabled = false;//可以印刷
  /*var strSql = "select a.prtno, a.plantype, a.certifycode, "
  	+" (select b.certifyname from lmcertifydes b where b.certifycode=a.certifycode), a.sumcount,"
  	+" case a.plantype when '6' then a.startno "
	+"  else lpad(((select nvl(max(endno), 0) + 1 from lzcardprint where certifycode = a.certifycode)),"
	+"  (select certifylength from lmcertifydes where certifycode = a.certifycode), '0') end, "
  	+" case a.plantype when '6' then a.endno "
	+"  else lpad(((select nvl(max(endno), 0) + a.sumcount from lzcardprint where certifycode = a.certifycode)),"
	+"  (select certifylength from lmcertifydes where certifycode = a.certifycode),'0') end,"  	
  	+" a.certifyprice, "
  	+" (select Printery from lzcardprint where certifycode = a.certifycode and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1),"
	+" (select Phone    from lzcardprint where certifycode = a.certifycode and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1),"
	+" (select LinkMan  from lzcardprint where certifycode = a.certifycode and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1),"
  	+" '' "
	+" from lzcardprint a where a.state = '0'"
  	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	+ getWherePart('a.PlanType', 'PlanType')
  	+ getWherePart('a.ManageCom', 'managecom')
  	+" order by a.plantype, a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('PlanType').value,fm.managecom.value]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
  if (!turnPage.strQueryResult) {
 	 alert("没有符合条件的单证记录！");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPrintQueryGrid";
  	  turnPage.queryModal(strSql, CardPrintQueryGrid);
	}
}

//已印刷查询
function queryClick2()
{
  if(fm.managecom.value.length != 2 && fm.managecom.value.length != 4 ){
    alert("只有总公司和分公司才能操作此菜单，请重新登录！");
    return;
  } 
  
  initCardPrintQueryGrid();
  fm.buttonP.disabled = true;//不可以印刷
 /* var strSql = "select a.prtno, a.plantype, a.certifycode,"
  +"(select b.certifyname from lmcertifydes b where b.certifycode=a.certifycode),"
  +" a.sumcount, a.startno,a.endno, a.certifyprice, a.printery, a.phone, a.linkman, a.maxdate "
	+" from lzcardprint a where a.state in ('1','2')"
  	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	+ getWherePart('a.PlanType', 'PlanType')
  	+ getWherePart('a.ManageCom', 'managecom')
  	+" order by a.plantype, a.certifycode, a.startno";*/
  var strSql = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value,document.all('PlanType').value,fm.managecom.value]);
  	
  turnPage.pageDivName = "divCardPrintQueryGrid";
  turnPage.queryModal(strSql, CardPrintQueryGrid); 	
  if (CardPrintQueryGrid.mulLineCount==0){
 	 alert("没有符合条件的单证记录！");
     return false;
  }   
}

function showDetail(parm1, parm2)
{
  var tPrtNo = CardPrintQueryGrid.getRowColData(CardPrintQueryGrid.getSelNo()-1, 1);
  /*var sql="select a.maxdate,a.printery,a.phone,a.linkman from lzcardprint a where a.prtno='"
  	     + tPrtNo+"'";*/
  var sql = wrapSql(tResourceName,"querysqldes3",[tPrtNo]);
  var tResult = easyExecSql(sql);
  	document.all('MaxDate').value = tResult[0][0];
    document.all('Printery').value = tResult[0][1];
    document.all('Phone').value = tResult[0][2];
    document.all('LinkMan').value = tResult[0][3];
}

function clearData()
{
  initForm();
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

//校验输入
function vertifyInput()
{
  return true;
}