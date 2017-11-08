//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sqlresourcename = "bq.PEdorTypeENInputSql";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//此函数未使用
/*
function edorTypePTReturn()
{
		initForm();
}
*/


function edorTypeENSave()
{
	  var row = PolGrid.mulLineCount;
    //用于判断在选择险种变更时，如果选择主险，则要求将附件险一起选择
    var tFlag=false;
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
         var riskcode = PolGrid.getRowColData(m,1);
		     var mainRiskcode = PolGrid.getRowColData(m,3);	
         if(riskcode==mainRiskcode)
         {
        	tFlag=true;
         }
          i = i+1;
        var rnewFlag = PolGrid.getRowColData(m,7);
				if(rnewFlag == '' || rnewFlag == null)
				{
					alert("第"+(m+1)+"行的险种续保状态变更未做选择，请选择。");
					return;
				}  
        }
      }
      if(i == 0)
      {
        alert("请选择需要变更的险种！");
        return false;
      }
      //
      var tNoChek=false;
      for(var k = 0; k < row;k++ )
      {
        if(!PolGrid.getChkNo(k))
        {
         var riskcode = PolGrid.getRowColData(k,1);
		     var mainRiskcode = PolGrid.getRowColData(k,3);	
         if(riskcode!=mainRiskcode)
         {
          tNoChek=true;
         }
       }
      }
      if(tNoChek&&tFlag)
      {
        alert("已经选择主险，请选择附加险一起变更");
        return false;
      }	
	//return;


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
  fm.submit();
}

function customerQuery()
{
//	window.open("./LCAppntIndQuery.html");
	window.open("./LCAppntIndQuery.html","",sFeatures);
}

//提交，保存按钮对应操作
//此函数未使用
/*
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  initLCAppntIndGrid();
 //  showSubmitFrame(mDebug);
  fm.submit(); //提交
}
*/

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
{
  showInfo.close();
  if (FlagStr == "Success" )
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
    /***************将保存的险种已做标记置为1**********************/
    var rowNum=PolGrid.mulLineCount;
    for (i=0;i<rowNum;i++)
		{
			if(PolGrid.getChkNo(i))
			{
				PolGrid.setRowColData(i,9,"1")
			}
		}
    
  }
  else if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content + "  请正确操作并重新保存！" ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  }
 

}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

//---------------------------
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 /*
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}
*/
/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
/*
function afterQuery( arrQueryResult )
{

}
*/

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
	var ActivityID = '0000000003';
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
function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
//	var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
    
    var strSQL = "";
	var sqlid1="PEdorTypeENInputSql1";
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
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //职业类别
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //职业类别
}
function Edorquery()
{
	//alert("$$$$$$$$$")
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

function checkMainPol(spanId)
{
	var rowNum=PolGrid.mulLineCount;//总行数
	var rowLine = spanId.substr(11,1); //当前被选中的行数；
	
	if(document.all(spanId).all('InpPolGridChk').value=='1')
	{
		var riskcode = PolGrid.getRowColData(rowLine,1);
		var mainRiskcode = PolGrid.getRowColData(rowLine,3);
		//alert(riskcode+"||"+mainRiskcode);
		if(riskcode==mainRiskcode)
		{
			var i;
			for(i = 0;i<rowNum;i++)
			{
				var tMainRiskCode = PolGrid.getRowColData(i,3);
				var tRiskCode = PolGrid.getRowColData(i,1);
				var hasAdd = false;
				if(tMainRiskCode == mainRiskcode && tRiskCode != tMainRiskCode)
				{
					//alert(tRiskCode);
					PolGrid.checkBoxSel(i+1);
					hasAdd = true;
				}
			}
			//if(hasAdd)
			//{
			//	alert("提示：你选中的是短期主险，如要做变更请对其下的附加险一起变更！");
			//}
		}
		else
		{
			var i;
			var isShortPol = false;
			for(i = 0; i< rowNum; i++)
			{
				var tMainRiskCode = PolGrid.getRowColData(i,1);
				if(tMainRiskCode == mainRiskcode)
				{
					isShortPol = true;
					PolGrid.checkBoxSel(i+1);
				}
			}
			if(isShortPol)
			{
				var i;
				/***************有可能存在短期主险下有多个短期附加险的可能*********************/
				for(i = 0;i < rowNum; i++)
				{
					var tRiskcode = PolGrid.getRowColData(i,1) 
					var tMainRiskcode = PolGrid.getRowColData(i,3);
					if(tMainRiskcode == mainRiskcode && tRiskcode != tMainRiskcode)
					{
						PolGrid.checkBoxSel(i+1);
					}
				}
				alert("提示：你选中的是短期附加险，如要做变更请对其主险一起变更！");	
				return ;
			}
		}
	}
	
	if(document.all(spanId).all('InpPolGridChk').value=='0')
	{
		var hasAppSaveFlag = PolGrid.getRowColData(rowLine,9);
		//alert(hasAppSaveFlag);
		if(hasAppSaveFlag == '1')
		{
			
			if(!confirm("提示：您已对该险种进行了变更保存操作，确定去掉该选项？"))
			{
				PolGrid.checkBoxSel(rowLine+1);
				return;
			}
			
		}
		var riskcode = PolGrid.getRowColData(rowLine,1);
		var mainRiskcode = PolGrid.getRowColData(rowLine,3);
		//alert(riskcode+"||"+mainRiskcode);
		if(riskcode==mainRiskcode)
		{
			var i;
			//alert("提示：你选中的是短期主险，如要做变更请对其下的附加险一起变更！");
			for(i = 0;i<rowNum;i++)
			{
				var tRiskCode = PolGrid.getRowColData(i,3);
				if(tRiskCode == mainRiskcode)
				{
					//alert(tRiskCode);
					PolGrid.checkBoxNotSel(i+1);
				}
			}
		}
		else
		{
			var i;
			var isShortPol = false;
			for(i = 0; i< rowNum; i++)
			{
				var tRiskCode = PolGrid.getRowColData(i,1);
				if(tRiskCode == mainRiskcode)
				{
					isShortPol = true;
					PolGrid.checkBoxNotSel(i+1);
				}
			}
			if(isShortPol)
			{
				var i;
				/***************有可能存在短期主险下有多个短期附加险的可能*********************/
				for(i = 0;i < rowNum; i++)
				{
					var tRiskcode = PolGrid.getRowColData(i,1) 
					var tMainRiskcode = PolGrid.getRowColData(i,3);
					if(tMainRiskcode == mainRiskcode && tRiskcode != tMainRiskcode)
					{
						//alert("取消所有短期附加险的复选框");
						PolGrid.checkBoxNotSel(i+1);
					}
				}
				
			}
		}
	}

	//alert("一会儿别忘了改数据库！LMRisk,LMRiskApp,LMRiskedoritem表")
	
}

function verify()
{
	var k=0;
  for (i=0; i<PolGrid.mulLineCount; i++)
  {
     
      if (PolGrid.getChkNo(i)&& (parseFloat(PolGrid.getRowColData(i, 7)) > PolGrid.getRowColData(i, 6)))
      {
        alert("第" + (i+1) + "行实领大于可领红利，请确认金额！");
        return false;
      } if (PolGrid.getChkNo(i)&& (parseFloat(PolGrid.getRowColData(i, 7))<=0))
      {
        alert("第" + (i+1) + "行无可领红利,请返回！");
        return false;
      }else if(PolGrid.getChkNo(i))
      	{
           k++;
       }   
  }
  if(k==0)
  {
  	    alert("请至少选中一条要还的还款记录，要么请返回");
        return false;
  	}
  
  return true;
}