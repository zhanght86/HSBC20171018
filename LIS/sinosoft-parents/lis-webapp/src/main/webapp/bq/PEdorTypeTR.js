//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeTRInputSql";

function verify(i)
{   
	var tFlag=true;

      if (parseFloat(LoanGrid.getRowColData(i, 4))!= LoanGrid.getRowColData(i, 2))
      {
         tFlag=false;
      }
      if (parseFloat(LoanGrid.getRowColData(i, 5))!= LoanGrid.getRowColData(i, 3))
      {
         tFlag=false;
      }

  return tFlag;
}

function edorTypeTRSave()
{
  if (!LoanGrid.checkValue()) 
  {
    return false; 
  }
  
      var row = LoanGrid.mulLineCount;
    //用于判断在选择险种退保时，如果选择主险，则要求将附件险一起选择
    var tFlag=false;
    var i = 0;
    var tSReLoan=0;
    var tAReLoan=0;
      for(var m = 0; m < row; m++ )
      {
        if(LoanGrid.getChkNo(m))
        {
          if(!verifyErr(m))
          {
           return;  	
           }
          if (!verify(m)) 
          {
  	       if(!confirm("自垫还款金额发生变化，是否继续?"))
         	 {
  		       return false; 
  	       }  	       	
           }  	
          i = i+1;
          tAReLoan+=(parseFloat(LoanGrid.getRowColData(m, 4))+parseFloat(LoanGrid.getRowColData(m, 5)));
        }
        tSReLoan+=(parseFloat(LoanGrid.getRowColData(m, 2))+parseFloat(LoanGrid.getRowColData(m, 3)));
      }
      if(i == 0)
      {
        alert("请选择需要还垫的记录！");
        return false;
      }
  fm.SReLoan.value=tSReLoan;
  fm.AReLoan.value=tAReLoan;
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
 
  document.all('fmtransact').value = "INSERT||MAIN";
  fm.action="./PEdorTypeTRSubmit.jsp";
  fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, Result, loanResult)
{ 
  try { showInfo.close(); } catch (e) {}
  
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    queryBackFee();
}

function returnParent()
{
 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
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

function ChangCalInterest() {
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 加锁
	lockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 加锁
	
	fm.action="./PEdorTypeTRCount.jsp";
	fm.submit();
}

function QueryEdorInfo()
{
	var tEdortype = top.opener.document.all('EdorType').value;
	var strSQL;
	if(tEdortype!=null || tEdortype !='')
	{
//	    strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
	    
	var sqlid1="PEdorTypeTRInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdortype);//指定传入的参数
	strSQL=mySql1.getString();
	    
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
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

function verifyErr(i)
{   
	var tFlag=true;

  if (parseFloat(LoanGrid.getRowColData(i, 4))> parseFloat(LoanGrid.getRowColData(i, 2)))
   {
	      	alert("还垫金额不能大于欠款金额");
		      return false;
    }
    if (parseFloat(LoanGrid.getRowColData(i, 5))> parseFloat(LoanGrid.getRowColData(i, 3)))
    {
		    alert("还垫利息不能大于欠款利息");
		      return false;
   }
  return tFlag;
}