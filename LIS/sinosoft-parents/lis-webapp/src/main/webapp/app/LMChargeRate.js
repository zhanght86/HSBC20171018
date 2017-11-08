//               该文件中包含客户端需要处理的函数和事件


var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var k=0;


// 数据返回父窗口
function returnLMChargeRate()
{		
	top.close();	
}

//保存数据
function saveLMChargeRate()
{	
	if(verifyInput2() == false) 
	  return false;   
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
	document.all('mOperate').value = "INSERT||MAIN";
	fm.action = "./LMChargeRateSave.jsp";
	fm.submit();
}

//更新数据
function updateLMChargeRate()
{	
	CaseGrid.delBlank;
	if (CaseGrid.mulLineCount <= 0)
	{
	    alert("请先查询手续费，再进行修改！");
	    return false;  
	}
	
	var tSelNO = CaseGrid.getSelNo();
	if (tSelNO <= 0)
	{
	    alert("请选择一条需要修改的手续费数据！");
	    return;
	}
	document.all('RiskCode').value=CaseGrid.getRowColData(tSelNO - 1,2);
	if(verifyInput2() == false) 
	  return false; 
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
	document.all('mOperate').value = "UPDATE||MAIN";
	fm.action = "./LMChargeRateSave.jsp";
	fm.submit();
}

//删除数据
function deleteLMChargeRate()
{	
	CaseGrid.delBlank;
	if (CaseGrid.mulLineCount <= 0)
	{
	    alert("请先查询手续费，再进行删除！");
	    return false;  
	}
	
	var tSelNO = CaseGrid.getSelNo();
	if (tSelNO <= 0)
	{
	    alert("请选择一条需要删除的手续费数据！");
	    return;
	}
	document.all('RiskCode').value=CaseGrid.getRowColData(tSelNO - 1,2);
    document.all('ChargeRate').value=CaseGrid.getRowColData(tSelNO -1,3);
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
	document.all('mOperate').value = "DELETE||MAIN";
	fm.action = "./LMChargeRateSave.jsp";
	fm.submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    if (fm.mOperate.value == "DELETE||MAIN")
    {
        fm.RiskCode.value = "";
        fm.ChargeRate.value = "";
    }
    selectLMChargeRate();
  }
}



function selectLMChargeRate()
{
  k++;	  	
  initCaseGrid();
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

   //书写SQL语句 	       
// var strSql = "select p.GrpContNo,p.RiskCode,c.ChargeRate from  LCGrpPol p,LMGrpCharge c" +
//               " where c.GrpPolNo=p.GrpPolNo"; 
    var sqlid1="LMChargeRateSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.LMChargeRateSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	var strSql=mySql1.getString();
  	          
  if (document.all('GrpContNo').value != "")	//集体合同号
  	strSql = strSql + " and p.GrpContNo= '" + document.all('GrpContNo').value + "'";
  //alert(strSql);
      
  if (document.all('RiskCode').value != '')	//险种编码
  	strSql = strSql + " AND p.RiskCode= '" + document.all('RiskCode').value + "'";
  turnPage.queryModal(strSql,CaseGrid);
  showInfo.close();

}

function flagLMChargeRate(parm1,parm2) 
{
    if(document.all(parm1).all('InpCaseGridSel').value == '1')
    {
        document.all('RiskCode').value=document.all(parm1).all('CaseGrid2').value;
        document.all('ChargeRate').value=document.all(parm1).all('CaseGrid3').value;
    
    }
}

function afterCodeSelect( cCodeName, Field ) {
	parameter1=cCodeName;
	parameter2=Field;
	//alert(cCodeName);
	if(parameter1=="GrpRisk")
	{
        document.all('ChargeRate').value = "";
        selectLMChargeRate(); 
    	return;
    }
}
