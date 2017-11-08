var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CertifyDescUpdateInputSql";
//Click事件，当点击“查询”时触发该函数
function queryClick()
{
  initForm();			//如果不初始化form的话，当第一次查询出定额单了，再次查询的时候下面的div显示就不会消除
  showDiv(divShow,"");
  showDiv(divCardRisk,"");
  /*
  var strSql = "select a.certifycode,a.certifyname,a.certifyclass,"
  +"(case a.certifyclass2 when '0' then '投保类' when '1' then '承保类' when '2' then '保全类' when '3' then '理赔类' when '4' then '财务类' when '5' then '条款' when '6' then '产品说明书' when '7' then '其他' end),"
  +"a.haveprice,a.havenumber,(case a.state when '0' then '启用' else '停用' end),a.makedate from lmcertifydes a where 1 = 1 ";
  
  if(fm.CertifyName_1.value!=null && fm.CertifyName_1.value!=""){
    strSql+=" and a.CertifyName like '%%" +fm.CertifyName_1.value+ "%'";
  }  
  
  var sqlWhere = "" + getWherePart('a.CertifyCode', 'CertifyCode_1')
  				//+ getWherePart('a.CertifyName', 'CertifyName_1', 'like')
  				+ getWherePart('a.CertifyClass', 'CertifyClass_1')
  				+ getWherePart('a.CertifyClass2', 'CertifyClass2_1')
  				+ getWherePart('a.HaveNumber', 'HaveNumber_1')
  				+" order by a.certifycode";
  */
  var strSql = wrapSql(tResourceName,"lmcertifydes1",[trim(document.all('CertifyCode_1').value),document.all('CertifyClass_1').value
                                        ,document.all('CertifyClass2_1').value,document.all('HaveNumber_1').value,document.all('CertifyName_1').value]);
               
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("没有符合条件的单证记录！");
     return false;
    }else{
  	  turnPage.pageDivName = "divCertifyDescGrid";
  	  turnPage.queryModal(strSql, CertifyDescGrid);
	}
}

function showDetail(parm1, parm2)
{
  //以下信息可以修改
  var tCertifyCode = CertifyDescGrid.getRowColData(CertifyDescGrid.getSelNo()-1, 1);
  /*
  var sql="select a.certifycode,a.certifyname,a.state,a.havelimit,a.maxunit1,a.maxunit2,"
  	  +"a.havevalidate,a.validate1,a.validate2,a.note from lmcertifydes a where a.certifycode='"
  	  + tCertifyCode+"'";
  	  */
  var sql = wrapSql(tResourceName,"lmcertifydes2",[tCertifyCode]);
  var tResult = easyExecSql(sql);
  	document.all('CertifyCode').value = tResult[0][0];
    document.all('CertifyName').value = tResult[0][1];
    document.all('State').value = tResult[0][2];
    
    document.all('HaveLimit').value = tResult[0][3];
    document.all('MaxUnit1').value = tResult[0][4];    
    document.all('MaxUnit2').value = tResult[0][5];
    
    document.all('HaveValidate').value = tResult[0][6];
    document.all('Validate1').value = tResult[0][7];
    document.all('Validate2').value = tResult[0][8];    
    document.all('Note').value = tResult[0][9];
    showCodeName();
    
    //以下信息不可修改，单证定义修改轨迹
    /*
    var traceSql="select a.certifycode,a.certifyname,(case a.state when '0' then '启用' else '停用' end),a.havelimit,a.maxunit1,a.maxunit2,"
  	  +"a.havevalidate,a.validate1,a.validate2,a.note,a.makedate from lmcertifydestrace a where 1 = 1 and a.certifycode='"
  	  + tCertifyCode+"'";*/
    var traceSql = wrapSql(tResourceName,"lmcertifydes3",[tCertifyCode]);
  	  turnPage2.strQueryResult  = easyQueryVer3(traceSql, 1, 0, 1); 
  	  turnPage2.pageDivName = "divCertifyDescTraceGrid";
  	  turnPage2.queryModal(traceSql, CertifyDescTraceGrid);
}

function clearData()
{
    document.all('CertifyCode_1').value = '';
    document.all('CertifyName_1').value = '';
    document.all('CertifyClass_1').value = '';
    document.all('CertifyClass2_1').value = '';
    document.all('HaveNumber_1').value = '';
    //document.all('HavePrice_1').value = '';
    initCertifyDescGrid();
}

//提交，保存按钮对应操作
function submitForm()
{
  if (confirm("您确实想修改该记录吗?"))
   {
	  if(vertifyInput() == false)
	  {
		return;
	  }
    fm.OperateType.value = "UPDATE";
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();   // showSubmitFrame(mDebug);    
    fm.action = './CertifyDescUpdateSave.jsp';
    document.getElementById("fm").submit(); //提交
    
  }else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
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

function displayMulLine()
{
  var displayType = fm.CertifyClass.value;
  if(displayType=="D")
  {
    document.all('divShow').style.display="";
    document.all('divCardRisk').style.display="";
  }
  if(displayType=="P")
  {
    document.all('divShow').style.display="none";
    document.all('divCardRisk').style.display="none";
  }
}

function displayNumberInfo()
{
	var vHaveNumber = fm.HaveNumber.value;
	if( vHaveNumber == 'Y' ) {
		fm.CertifyLength.value = '';
		fm.CertifyLength.disabled = false;
	} else {
		fm.CertifyLength.value = 0;
		fm.CertifyLength.disabled = true;
	}
}

function displayHaveLimit()
{
	var vHaveLimit = fm.HaveLimit.value;
	if( vHaveLimit == 'Y' ) {
		fm.MaxUnit1.value = '5';
		fm.MaxUnit1.disabled = false;
		fm.MaxUnit2.value = '500';
		fm.MaxUnit2.disabled = false;
	} else {
		fm.MaxUnit1.value = '0';
		fm.MaxUnit1.disabled = true;
		fm.MaxUnit2.value = '0';
		fm.MaxUnit2.disabled = true;
	}
}

function displayHaveValidate()
{
	var vHaveValidate = fm.HaveValidate.value;
	if( vHaveValidate == 'Y' ) {
		fm.Validate1.value = '30';
		fm.Validate1.disabled = false;
		fm.Validate2.value = '90';
		fm.Validate2.disabled = false;
	} else {
		fm.Validate1.value = '0';
		fm.Validate1.disabled = true;
		fm.Validate2.value = '0';
		fm.Validate2.disabled = true;
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

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
      parent.fraMain.rows = "0,0,0,0,*";
  }
   else
   {
      parent.fraMain.rows = "0,0,0,0,*";
   }
}

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  if (confirm("您确实想修改该记录吗?"))
   {
		if(vertifyInput() == false)
		{
			return;
		}
    fm.OperateType.value = "UPDATE";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	showSubmitFrame(mDebug);
    
    fm.action = './CertifyDescSave.jsp';
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
  alert("您无法执行删除操作！！！！");
  return ;
//  if (confirm("您确实要删除该记录吗？"))
//  {
//    if(fm.CertifyCode.value!=fm.CertifyCode_1.value)
//    {
//      alert("单证号码进行了修改，您无法执行删除操作！！！");
//      return;
//    }
//    fm.OperateType.value = "DELETE";
//    var i = 0;
//    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或连接其他的界面";
//    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
//    showSubmitFrame(mDebug);
//    fm.action = './CertifyDescSave.jsp';
//
//    document.getElementById("fm").submit();//提交
//    resetForm();
//  }
//  else
//  {
//    fm.OperateType.value = "";
//    alert("您已经取消了修改操作！");
//  }
}

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

//校验输入
function vertifyInput()
{
  if((fm.CertifyCode.value=="")||(fm.CertifyCode.value=="null"))
  {
    alert("请您录入单证号码！！！");
    return false;
  }
  if((fm.CertifyName.value=="")||(fm.CertifyName.value=="null"))
  {
    alert("请您录入单证名称！！！");
    return false;
  }
  if((fm.HaveLimit.value=="")||(fm.HaveLimit.value=="null"))
  {
    alert("是否限量领用！！！");
    return false;
  }
  if((fm.HaveValidate.value=="")||(fm.HaveValidate.value=="null"))
  {
    alert("是否有使用期！！！");
    return false;
  } 
  return;
}
