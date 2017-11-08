//TempFeeWithdrawInput.js该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var tFees;
var sqlresourcename = "app.TempFeeWithdrawInputSql";
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var checkFlag = 0;
  //alert("行============="+FeeGrid.mulLineCount);
  //对下拉框的取值进行校验
  if( verifyInput2() == false ) return false;
  
  for (i=0; i<FeeGrid.mulLineCount; i++) {
    if (FeeGrid.getChkNo(i)) { 
      checkFlag = checkFlag + 1;
    }
  }
  //alert("xuanzhong============="+checkFlag);
  if (checkFlag>=1) { 
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   //lockScreen('lkscreen'); 

	//document.getElementById('TFConfirm').disabled = true; 
    document.getElementById("fm").submit(); //提交
  }
  else {
    alert("请先选择一条暂交费信息！"); 
  }
}

function PrintInform(){
var tempfeeno = fm.TempFeeNo.value;

  if (tempfeeno != "") {
  /*
    var strSql = "select ActuGetNo/*, OtherNo, OtherNoType, PayMode, SumGetMoney, StartGetDate  from ljaget where otherno ='"+tempfeeno+"' "
               //+ "(select tempfeeno from ljtempfee where otherno in "
               //+ "(select proposalno from lcpol where prtno='" + prtNo + "') union "
               //+ "select tempfeeno from ljtempfee where otherno in "
               //+ "(select '" + prtNo + "' from ldsysvar where sysvar='onerow' )"
               + " and ConfDate is null";
  //prompt("",strSql);
 */
var sqlid1="TempFeeWithdrawInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(tempfeeno); 
 
             
  turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(),1,0,1);  
  
  //add by jiaqiangli 2007-12-27
  if (!turnPage.strQueryResult ){
     alert("未查询到相关数据或是没有进行退费确认");
     return false;
     }else{
       fm3.PrtData1.value =easyExecSql(mySql1.getString());
       //alert(fm3.PrtData1.value);
     }
//  else
//     alert("查询数据成功！");

  }
  else {
    alert("请输入收费号码!"); 
    return false;
  }
     //var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    //document.getElementById("PrintFee").disabled=true;
    document.getElementById("fm3").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
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
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}           

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

var queryBug = 0;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
//暂交费号码查询按钮
function tempFeeNoQuery(afterQuery) {
	if(document.all("TempFeeNo").value == "" && document.all("PrtNo").value == "")
	{
		alert("查询条件不足！请输入暂收据收据号或者印刷号/保单号！");
		return;
		}
  var tempFeeNo = fm.TempFeeNo.value;
  var prtNo = fm.PrtNo.value;
  
  //只显示出该暂交费号码的未退费的险种
  //已核销、退费（ConfFlag）的不显示出来
  //zy 2007-12-29
  //按照一条收据只显示一条记录的原则进行暂收费信息的查询
  if (prtNo == "") { 
  /*
    var strSql = "select '', TempFeeNo, TempFeeType, APPntName, '', sum(PayMoney), PayDate, EnterAccDate, sum(PayMoney),'' from LJTempFee where 1=1 "
               + " and ConfFlag = '0' and enteraccdate is not null and managecom like '"+document.all('mComcode').value+"%' "
               //and not exists (select tempfeeno from ljagettempfee where tempfeeno=LJTempFee.tempfeeno)"
              // + " and exists (select 1 from ljtempfeeclass where enteraccdate is not null and enteraccdate<>'3000-1-1' and tempfeeno=ljtempfee.tempfeeno)"
               + getWherePart('TempFeeNo')
               + getWherePart('PayMoney')
               + getWherePart('PayDate')
               + getWherePart('EnterAccDate')
               + getWherePart('ManageCom')
               + getWherePart('Operator')
               + " group by TempFeeNo,TempFeeType,APPntName,PayDate,EnterAccDate";
              // + " group by TempFeeNo,TempFeeType,APPntName,OtherNo,PayDate,EnterAccDate,othernotype";
  */
var sqlid2="TempFeeWithdrawInputSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(document.all('mComcode').value);
mySql2.addSubPara(fm.TempFeeNo.value); 
mySql2.addSubPara(fm.PayMoney.value); 
mySql2.addSubPara(fm.PayDate.value); 
mySql2.addSubPara(fm.EnterAccDate.value); 
mySql2.addSubPara(fm.ManageCom.value);  
mySql2.addSubPara(fm.Operator.value); 
  var strSql = mySql2.getString();
  
  }
  else {
  /*
    var strSql = "select '', TempFeeNo, TempFeeType, APPntName, '', sum(PayMoney), PayDate, EnterAccDate,sum(PayMoney),'' from LJTempFee where 1=1 "
               + " and ConfFlag = '0' and enteraccdate is not null and managecom like '"+document.all('mComcode').value+"%' "
               // and not exists (select tempfeeno from ljagettempfee where tempfeeno=LJTempFee.tempfeeno)"
              // + " and exists (select 1 from ljtempfeeclass where enteraccdate is not null and enteraccdate<>'3000-1-1' and tempfeeno=ljtempfee.tempfeeno)"
               + getWherePart('TempFeeNo')
               + getWherePart('PayMoney')
               + getWherePart('PayDate')
               + getWherePart('EnterAccDate')
               + getWherePart('ManageCom')
               + getWherePart('Operator')
               + " and OtherNo='" + prtNo + "'"
               + " group by TempFeeNo,TempFeeType,APPntName,PayDate,EnterAccDate";
               //+ " group by TempFeeNo,TempFeeType,APPntName,OtherNo,PayDate,EnterAccDate,othernotype";               
*/
var sqlid3="TempFeeWithdrawInputSql3";
var mySql3=new SqlClass();
mySql3.setResourceName(sqlresourcename);
mySql3.setSqlId(sqlid3);
mySql3.addSubPara(document.all('mComcode').value);
mySql3.addSubPara(fm.TempFeeNo.value); 
mySql3.addSubPara(fm.PayMoney.value); 
mySql3.addSubPara(fm.PayDate.value); 
mySql3.addSubPara(fm.EnterAccDate.value); 
mySql3.addSubPara(fm.ManageCom.value);  
mySql3.addSubPara(fm.Operator.value);  
mySql3.addSubPara(prtNo); 
  
  var strSql = mySql3.getString();
  }
  //alert(strSql);
             
  turnPage.queryModal(strSql, FeeGrid);
  
  if (FeeGrid.mulLineCount <= 0 )
     alert("未查询到相关数据！");
  else
     alert("查询数据成功！");
}
