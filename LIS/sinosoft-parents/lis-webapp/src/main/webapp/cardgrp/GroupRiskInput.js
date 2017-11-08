 //               该文件中包含客户端需要处理的函数和事件
var mAction = "";
window.onfocus=myonfocus;
var mSwitch = top.opener.parent.VD.gVSwitch;

var showInfo1;
var mDebug="0";

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag=false;
//
var arrCardRisk;
//window.onfocus=focuswrap;

//使得从该窗口弹出的窗口能够聚焦
function focuswrap()
{
	myonfocus(showInfo1);
}

//提交，保存按钮对应操作
function submitForm()
{
 }


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  
}




//提交后操作,服务器数据返回后执行的操作
function afterQuery( FlagStr, content )
{
  
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
 
} 

//取消按钮对应操作
function cancelForm()
{

}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作
	
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


//Click事件，当点击增加图片时触发该函数
function addClick()
{
 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{

}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  
}           

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  
}

function InputPolicy()
{
	var newWindow = window.open("../cardgrp/NewProposal.jsp?RiskCode=111302");
}
function InputPolicyNoList()
{
	var newWindow = window.open("../cardgrp/NewProposal.jsp?NoListFlag=1&RiskCode=111302");
}
function grpInsuInfo()
{
	showInfo1 = window.open("../cardgrp/ContInsu.jsp");
}



//添加一笔纪录
function addRecord()
{
    RiskGrid.delBlankLine();
     fm.all('fmAction').value="INSERT||GROUPRISK";
    // alert(mOperate);
     fm.submit();
  }
  function deleteRecord()
{
    
     TempGrid.delCheckTrueLine("TempGrid");     
     //TempGrid.setRowColData(TempGrid.mulLineCount-1,1,fm.all('RiskCode').value);
     // TempGrid.setRowColData(TempGrid.mulLineCount-1,2,"[险种名称]");
     //TempGrid.setRowColData(TempGrid.mulLineCount-1,3,0);
     //TempGrid.setRowColData(TempGrid.mulLineCount-1,4,0);
  }
  function returnparent()
  {
  	parent.close();
}
  function grpFeeInput()
{
    
    parent.fraInterface.window.location = "../cardgrp/GrpFeeInput.jsp";     
}