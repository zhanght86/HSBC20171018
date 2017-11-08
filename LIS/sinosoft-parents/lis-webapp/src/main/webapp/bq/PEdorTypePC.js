// 该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var theFirstValue="";
var theSecondValue="";
var sqlresourcename = "bq.PEdorTypePCInputSql";

function verify() {

  //if (trim(fm.BankAccNo.value)!=trim(fm.BankAccNoAgain.value)) {
   // alert("两个账号不一致，请确认！");
    //return false;
  //}
  if (trim(fm.BankCode.value)=="0101") {
    if (trim(fm.BankAccNo.value).length!=19) {
      alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！");
      return false;
    }
  }
  if(trim(fm.BankAccNo.value)==null||trim(fm.BankAccNo.value)=="")
  {
      alert("银行帐号不能为空!");
      return false;
  }
  if(trim(document.all('AppntName').value)!=trim(document.all('AccName').value))
   {
          alert("户 名与投保人姓名不相同!");
          return false;
   }

 if(trim(document.all('AppntName').value)!=trim(document.all('AccName').value)){
    if (!confirm("账户名称和投保人名称不一样！按确定继续提交，按取消则返回更改！"))
      return false;
  }
 if(fm.PayLocation.value=="0" )
  {
            //银行划款或网上支付
     if(fm.BankCode.value == null || fm.BankCode.value == ""
             || fm.BankAccNo.value == null || fm.BankAccNo.value == ""
             || fm.AccName.value == null || fm.AccName.value == "")
     {
                alert("银行收付费信息不完整!");
                return false ;
     }
  } 

  return true;
}

function edorTypePCSave()
{
  if(document.all('PayLocation').value == "0"){
    if (verify() == false) return false;
  }else{
  	//当缴费期限为半年交、季交和月交时系统只默认缴费方式为银行自动转账
//  	var strsql = "select payintv  from lcpol where contno='"+fm.ContNo.value+"' and polno=mainpolno";
    
    var strsql = "";
	var sqlid1="PEdorTypePCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
	strsql=mySql1.getString();
    
    var aResult = easyExecSql(strsql,1,0);
    if(aResult!=null && aResult.length>0)
    {
    	if((aResult[0][0]=='1'|| aResult[0][0]=='3' || aResult[0][0]=='6') && document.all('PayLocation').value != "0")
    	{
    		alert("缴费期限为半年交、季交和月交时系统只默认缴费方式为银行自动转账");
    		return false;
    	}
    }
  }
  
  //var accountname = document.all('AccName').value;
  //if (accountname!= document.all('AppntName').value)
  //{
  //     alert("帐户户名与投保人姓名不一致！");
  //}
  searchOtherContTheAppntAssociated();

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('fmtransact').value = "UPDATE||MAIN";
  fm.submit();

}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            initBankInfo();
            top.opener.getEdorItemGrid();
        }
        catch (ex) {}
    }
}

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
      initForm();
  }
  catch(re)
  {
    alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

function afterQuery( arrQueryResult )
{
}

function afterCodeSelect( cCodeName, Field )
{
    //alert(cCodeName);
    try {
        if( cCodeName == "paylocation" )
        {
            if (document.all("PayLocation").value=="0") {
                divBank.style.display = "";
            } else {
                divBank.style.display = "none";
            }
        }
    }
    catch( ex ) {
    }
}

function initBankInfo()
{
     //var i = 0;

   var strSql = "";
/*   strSql = "select AppntNo,AppntName,Prem,Amnt,PayMode,BankCode,BankAccNo,AccName,PayLocation from LPCont where 1 =1" + " "
              + getWherePart('ContNo')
              + getWherePart('EdorNo')
              + getWherePart('EdorType')
 //alert(strSql);
*/   
	var sqlid2="PEdorTypePCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql2.addSubPara(fm.EdorNo.value);
	mySql2.addSubPara(fm.EdorType.value);
	strSql=mySql2.getString();
   
   var arrResult = easyExecSql(strSql, 1, 0);
   if (arrResult == null)
   {
       //alert("没有相应的保单信息");
           document.all('BankCode').value = "";
           document.all('BankAccNo').value = "";
           document.all('AccName').value = "";
           document.all('PayLocation').value = "";
   }
   else
   {
       displayBank(arrResult);
   }

   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
     //document.all('fmtransact').value = "QUERY||PayLocation";
     //fm.submit();

}

function displayBank(arrResult)
{
   document.all('BankCode').value = arrResult[0][5];
   document.all('BankAccNo').value = arrResult[0][6];
   //document.all('BankAccNoAgain').value = arrResult[0][6];
   document.all('AccName').value = arrResult[0][7];
   document.all('PayLocation').value = arrResult[0][8];
}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;

    var strSQL="";
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
/*      var strSQL =" Select a.appntno,'投保人',a.appntname,a.idtype||'-'||x.codename,a.idno,a.appntsex||'-'||sex.codename,a.appntbirthday From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'被保人',i.name,i.IDType||'-'||xm.codename,i.IDNo,i.Sex||'-'||sex.codename,i.Birthday From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
//alert("-----------"+strSQL+"------------");
*/    
    var strSQL ="";
    var sqlid3="PEdorTypePCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql3.addSubPara(fm.EdorNo.value);
	strSQL=mySql3.getString();
    
    }
    else
    {
        alert('没有相应的投保人或被保人信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
  //alert("没有相应的投保人或被保人信息！");
        return false;
    }

    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = PolGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}

function searchOtherContTheAppntAssociated()
{
   try
   {
       var i;
       var tFlag1 = 0;
       var tFlag2 = 0;
       var content1 = "以当前投保人为投保人的其它保单还有：";
       //var content2 = "以当前投保人为被保人的其它保单还有：";

       var sContNo = document.getElementsByName("ContNo")[0].value;
       var QuerySQL;
/*       QuerySQL = "select distinct ContNo "
                +   "from LCCont "
                +  "where 1 = 1 "
                +    "and ContNo <> '" + sContNo + "' "
                +     getWherePart("AppntNo", "AppntNo")
                +    "and AppFlag = '1'";
       //alert(QuerySQL);
*/       
    var sqlid4="PEdorTypePCInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(sContNo);//指定传入的参数
	mySql4.addSubPara(fm.AppntNo.value);
	QuerySQL=mySql4.getString();
       
       var arrResult1 = easyExecSql(QuerySQL,1,0);
       if ((arrResult1!=null) && (arrResult1.length!=0))
       {
          tFlag1 = 1;
          for (i=0;i<arrResult1.length;i++)
          {
               content1 = content1 + arrResult1[i][0] + " ";
          }
       }
       var tFlag = 0;
       var content = "";
       if (tFlag1==1)
       {
          tFlag = 1;
          content = content1;
       }
          //alert(tFlag);
       if (tFlag==1)
       {
            alert(content);
         }
         return;
  }
  catch(ex)
  {
     alert("PEdorTypePCInit.jsp-->查询当前投保人与其它保单的关联时产生错误!");
     return;
  }
}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}