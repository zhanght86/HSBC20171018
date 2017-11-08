var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CertifyDescInputSql";
//Click事件，当点击“查询”时触发该函数
function queryClick()
{
  initForm();
  /*
  var strSql = "select a.certifycode,a.certifyname,a.certifyclass,"
  +" (case a.certifyclass2 when '0' then '投保类' when '1' then '承保类' when '2' then '保全类' when '3' then '理赔类' when '4' then '财务类' when '5' then '条款' when '6' then '产品说明书' when '7' then '其他' end),"
  +" a.haveprice,a.havenumber,(case a.state when '0' then '启用' else '停用' end) "
  +" from lmcertifydes a where 1 = 1 "
  + getWherePart('a.CertifyCode', 'CertifyCode_1')
  + getWherePart('a.CertifyClass', 'CertifyClass_1')
  + getWherePart('a.CertifyClass2', 'CertifyClass2_1')
  + getWherePart('a.HaveNumber', 'HaveNumber_1');
  				
  if(fm.CertifyName_1.value!=null && fm.CertifyName_1.value!=""){
    strSql+=" and a.CertifyName like '%%" +fm.CertifyName_1.value+ "%'";
  }      
  strSql+=" order by a.certifycode";
  */
  var strSql = wrapSql(tResourceName,"lmcertifydes1",[trim(document.all('CertifyCode_1').value),document.all('CertifyClass_1').value
                           ,document.all('CertifyClass2_1').value,document.all('HaveNumber_1').value,document.all('CertifyName_1').value]);
  
  turnPage.pageDivName = "divCertifyDescGrid";
  turnPage.queryModal(strSql, CertifyDescGrid);
  if(CertifyDescGrid.mulLineCount<=0){
 	 alert("没有符合条件的单证记录！");
     return false;  
  }  	   				
}

function showDetail(parm1, parm2)
{
  var tCertifyCode = CertifyDescGrid.getRowColData(CertifyDescGrid.getSelNo()-1, 1);
  /*
  var sql="select a.certifycode,a.certifyname,a.certifyclass,a.certifyclass2,a.haveprice,a.havenumber,"
  	  +"a.certifylength,a.tackbackflag,a.certifyprice,a.unit,a.maxprintno,a.havelimit,a.maxunit1,a.maxunit2,"
  	  +"a.havevalidate,a.validate1,a.validate2,a.note from lmcertifydes a where a.certifycode='"
  	  + tCertifyCode+"'";
  //prompt("",sql);
  */
  var sql = wrapSql(tResourceName,"lmcertifydes2",[tCertifyCode]);
               
  var tResult = easyExecSql(sql);
  	document.all('CertifyCode').value = tResult[0][0];
    document.all('CertifyName').value = tResult[0][1];
    document.all('CertifyClass').value = tResult[0][2];
    document.all('CertifyClass2').value = tResult[0][3];
    //document.all('HavePrice').value = tResult[0][4];    
    document.all('HaveNumber').value = tResult[0][5];

    document.all('CertifyLength').value = tResult[0][6];
    document.all('TackBackFlag').value = tResult[0][7];
    document.all('CertifyPrice').value = tResult[0][8];
    document.all('Unit').value = tResult[0][9];
    document.all('MaxPrintNo').value = tResult[0][10];
    document.all('HaveLimit').value = tResult[0][11];
    document.all('MaxUnit1').value = tResult[0][12];
    document.all('MaxUnit2').value = tResult[0][13];
    
    document.all('HaveValidate').value = tResult[0][14];
    document.all('Validate1').value = tResult[0][15];
    document.all('Validate2').value = tResult[0][16];
    document.all('Note').value = tResult[0][17];
    showCodeName();
/**  var displayType = fm.CertifyClass.value;
  if(displayType=="D")
  {
    document.all('divShow').style.display="";
    document.all('divCardRisk').style.display="";
  }else{
    document.all('divShow').style.display="none";
    document.all('divCardRisk').style.display="none";
  }
  
    if(tResult[0][2]=="D"){      
      var riskSql="select a.riskcode,a.prem,a.premprop,a.premlot from lmcardrisk a "
      		+" where a.certifycode='" + tCertifyCode+"'";
  	  turnPage2.strQueryResult  = easyQueryVer3(riskSql, 1, 0, 1); 
      if (!turnPage2.strQueryResult) {
      	initCardRiskGrid();
 	    alert("没有符合条件的定额单险种信息！"); 	    
        return false;
      }else{
  	    turnPage2.pageDivName = "divCardRiskGrid";
  	    turnPage2.queryModal(riskSql, CardRiskGrid);
	  }
    }**/
}

function clearData()
{
    document.all('CertifyCode_1').value = '';
    document.all('CertifyName_1').value = '';
    document.all('CertifyClass_1').value = '';
    document.all('CertifyClass2_1').value = '';
    document.all('HaveNumber_1').value = '';
    initCertifyDescGrid();
}

//提交，保存按钮对应操作
function submitForm()
{
  if(verifyInput() == false)
  {
	return;
  }
//  if(fm.CertifyClass.value=='D' && CardRiskGrid.mulLineCount==0){
//    alert("重要有价单证请录入险种信息！");
//    return false;
//  }
  if(fm.HaveLimit.value=='Y' && (fm.MaxUnit1.value=='' || fm.MaxUnit2.value=='')){
    alert("请录入限量领用数量！");
    return false;
  }  
  if(fm.HaveValidate.value=='Y' && (fm.Validate1.value=='' || fm.Validate2.value=='')){
    alert("请录入有效使用期限！");
    return false;
  }
  if((fm.CertifyClass.value=='D'||fm.CertifyClass.value=='B') && fm.TackBackFlag.value=='N'){
    alert("重要有价单证/重要空白单证的是否回收标志不能为N，请重新录入！");
    return false;
  }        
  
  fm.OperateType.value = "INSERT";
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CertifyDescSave.jsp';
  document.getElementById("fm").submit(); //提交
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='CertifyClass')
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
  }else{
    document.all('divShow').style.display="none";
    document.all('divCardRisk').style.display="none";
  }
  if(displayType=="D" || displayType=="B")
  {
    document.all('TackBackFlag').value="Y";
    document.all('TackBackFlagName').value="需要回收";
  }else{
    document.all('TackBackFlag').value="";
    document.all('TackBackFlagName').value="";
  } 
   
}

function displayNumberInfo()
{
	var vHaveNumber = fm.HaveNumber.value;
	if( vHaveNumber == 'Y' ) {
		fm.CertifyLength.value = '';
		fm.CertifyLength.readOnly = false;
	} else {
		fm.CertifyLength.value = 18;
		fm.CertifyLength.readOnly = true;
	}
}

function displayHaveLimit()
{
	var vHaveLimit = fm.HaveLimit.value;
	if( vHaveLimit == 'Y' ) {
		fm.MaxUnit1.value = '5';
		fm.MaxUnit1.readOnly = false;
		fm.MaxUnit2.value = '500';
		fm.MaxUnit2.readOnly = false;
	} else {
		fm.MaxUnit1.value = '0';
		fm.MaxUnit1.readOnly = true;
		fm.MaxUnit2.value = '0';
		fm.MaxUnit2.readOnly = true;
	}
}

function displayHaveValidate()
{
	var vHaveValidate = fm.HaveValidate.value;
	if( vHaveValidate == 'Y' ) {
		fm.Validate1.value = '30';
		fm.Validate1.readOnly = false;
		fm.Validate2.value = '90';
		fm.Validate2.readOnly = false;
	} else {
		fm.Validate1.value = '0';
		fm.Validate1.readOnly = true;
		fm.Validate2.value = '0';
		fm.Validate2.readOnly = true;
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
    initForm();
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
		if(verifyInput() == false)
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
/**function vertifyInput()
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
  if((fm.CertifyClass.value=="")||(fm.CertifyClass.value=="null"))
  {
    alert("请您录入单证类型！！！");
    return false;
  }
  if((fm.CertifyLength.value=="")||(fm.CertifyLength.value=="null"))
  {
    alert("请您录入单证号码长度！！！");
    return false;
  }
  if((fm.Note.value=="")||(fm.Note.value=="null"))
  {
    alert("请您录入注释信息！！！");
    return false;
  }  
  return;
}**/

