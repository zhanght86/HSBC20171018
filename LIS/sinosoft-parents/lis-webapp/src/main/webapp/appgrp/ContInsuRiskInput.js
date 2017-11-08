//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
window.onfocus=myonfocus;
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
/*********************************************************************
 *  保存集体投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
	
}

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	
}

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
	
} 

/*********************************************************************
 *  "取消"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelForm()
{
	showDiv(operateButton,"true"); 
	showDiv(inputButton,"false"); 
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

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
	
}           

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick()
{
	
} 

/*********************************************************************
 *  Click事件，当点击“修改”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function updateClick()
{
	
}           

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick()
{
	
}           

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

function InputPolicy()
{
	var newWindow = window.open("../appgrp/NewProposal.jsp","",sFeatures);
}

function InputPolicyNoList()
{
	var newWindow = window.open("../appgrp/NewProposal.jsp","",sFeatures);
}
 function returnparent()
 {
  	parent.close();
}
 function deleteRecord()
{
    
     LCInsuredPolGrid.delCheckTrueLine("LCInsuredPolGrid");     
     //LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,1,fm.all('RiskCode').value);
     // LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,2,"[险种名称]");
     //LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,3,0);
     //LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,4,0);
  }

