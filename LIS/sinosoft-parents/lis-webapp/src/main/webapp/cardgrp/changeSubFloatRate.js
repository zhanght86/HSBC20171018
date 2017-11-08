//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";

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

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
	}
}

// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
function initQuery() {
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var strSql = "select ProposalNo,PrtNo,RiskCode,FloatRate,AppntName,InsuredName from LCPol where "
    				 + " PrtNo='" + prtNo + "'"
    				 + " and PolNo<>MainPolNo";
  
  //alert(strSql);
	turnPage.queryModal(strSql, PolGrid);
}

//修改操作
var mSwitch = parent.VD.gVSwitch;
function modifyClick() { 
  if (PolGrid.getSelNo()) { 
  	var cPolNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
  	//type="ChangePlan";
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    window.open("../cardgrp/ProposalEasyScan.jsp?LoadFlag=10&Type="+type+"&prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}



