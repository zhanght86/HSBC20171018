//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var tTempLoanRate=0;
var sqlresourcename = "bq.PEdorTypeLNInputSql";

function verify()
{
  for (i=0; i<LoanGrid.mulLineCount; i++)
  {
    if (LoanGrid.getRowColData(i, 5) == "")
    {
      continue;
    }
    else
    {
      if (parseFloat(LoanGrid.getRowColData(i, 5)) > LoanGrid.getRowColData(i, 2))
      {
        alert("第" + (i+1) + "行还垫大于垫款，请确认金额！");
        return false;
      }
    }
  }

  return true;
}

function saveEdorTypeLN()
{
        //界面校验
    if (!verifyInput2())
    {
        return false;
    }

    //校验贷款金额，不能大于最高限额
    if ((document.all('MaxLoan').value-0) > (document.all('SaveMaxLoan').value-0))
    {
        alert("贷款金额最高为"+document.all('SaveMaxLoan').value+"，您输入的贷款金额过高！");
        return;
    }
    //校验贷款金额，不能大于最高限额
    if ((document.all('MaxLoan').value-0) < 1000)
    {
        alert("贷款金额不能低于1000");
        return;
    }
    if(tTempLoanRate-document.all('LoanRate').value!=0)
    {
    	if(!confirm("利率发生调整，需要通过审批，是否继续"))
    	{
    		        return;
    		}
    }
    document.all('fmtransact').value ="INSERT||MAIN";
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action="./PEdorTypeLNSubmit.jsp"
    fm.submit();
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent, OtherParam)
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
            top.opener.getEdorItemGrid();
            document.getElementsByName("Interest")[0].value = OtherParam;
            fm.MaxLoan.value= "";
			fm.LoanRate.value = "";
			fm.Currency.value = "";
			fm.SaveMaxLoan.value = "";
            initLoanGrid();
            queryBackFee();
        }
        catch (ex) {}
    }
}

//查询后初始化MulLine
function afterCount(tFlagStr,tContent,tMulArray){
   //初始化MulLine
        if (tFlagStr == "Success"){
        	displayMultiline(tMulArray,LoanGrid,turnPage);
 		} else {
         	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
         	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
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

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionID为空！");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}

function queryCustomerGrid()
 {
  try
    {

    var tContNo=document.all("ContNo").value;
    //alert(tContNo);
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno,'投保人',a.appntname,a.idtype||'-'||x.codename,a.idno,a.appntsex||'-'||sex.codename,a.appntbirthday From lcappnt a "
                   + " Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                   + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                   + " Union"
                   + " Select i.insuredno,'被保人',i.name,i.IDType||'-'||xm.codename,i.IDNo,i.Sex||'-'||sex.codename,i.Birthday From lcinsured i "
                   + " Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                   + " Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
//alert(strSQL);
*/        
    var strSQL = "";
    var sqlid2="PEdorTypeLNInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(tContNo);//指定传入的参数
	mySql2.addSubPara(tContNo);
	strSQL=mySql2.getString();
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }

       turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = CustomerGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    }
    catch(ex)
    {
        alert("在PEdorTypeEA.js-->queryCustomer()函数中发生异常:"+ex);
    }
   }

   function getPolInfo(tContNo)
{
      var tContno=document.all('ContNo').value;
    //alert(tContNo);
    //var tContNo;
    // 书写SQL语句
 //   var strSQL ="select RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Amnt,Prem,'','','','' from LCPol where ContNo='"+tContNo+"' and AppFlag = '1'";

	var strSQL = "";
    var sqlid3="PEdorTypeLNInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tContNo);//指定传入的参数
	strSQL=mySql3.getString();

    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //判断是否查询成功
    if (!turnPage.strQueryResult)
    {
        return false;
    }
    //alert(turnPage.strQueryResult);
    //查询成功则拆分字符串，返回二维数组

    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象

    turnPage.pageDisplayGrid = LoanGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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

function ShowLoanInfo(){
	var rate = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 7);
	var currency = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 9);
	var thisLoan = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 10);
	var maxHaveLoan = LoanGrid.getRowColData(LoanGrid.getSelNo()-1, 6);
	fm.MaxLoan.value= thisLoan;
	fm.LoanRate.value = rate;
	fm.Currency.value = currency;
	fm.SaveMaxLoan.value = maxHaveLoan;
}









