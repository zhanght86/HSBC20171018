//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""	// "1--录入，"2"--查询
var mGrpFlag = ""; 	//个人集体标志,"0"表示个人,"1"表示集体.
var mySql = new SqlClass();
window.onfocus = myonfocus;

/*********************************************************************
 *  选择险种后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
	try	{
		if( cCodeName == "RiskInd" || cCodeName == "RiskGrp" )	
		{
			getRiskInput( Field.value, loadFlag );//loadFlag在页面出始化的时候声明
		}
	}
	catch( ex ) {
	}
}

/*********************************************************************
 *  根据LoadFlag设置一些Flag参数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
	if( cFlag == "1" )		// 个人投保单直接录入
	{
		mCurOperateFlag = "1";
		mGrpFlag = "0";
	}
	if( cFlag == "2" )		// 集体下个人投保单录入
	{
		mCurOperateFlag = "1";
		mGrpFlag = "1";
	}
	if( cFlag == "3" )		// 个人投保单明细查询
	{
		mCurOperateFlag = "2";
		mGrpFlag = "0";
	}
	if( cFlag == "4" )		// 集体下个人投保单明细查询
	{
		mCurOperateFlag = "2";
		mGrpFlag = "1";
	}
	if( cFlag == "5" )		// 集体下个人投保单明细查询
	{
		mCurOperateFlag = "2";
		mGrpFlag = "3";
	}
}

/*********************************************************************
 *  根据不同的险种,读取不同的代码
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getRiskInput( cRiskCode, cFlag ) {
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "";
	var tPolNo = "";

	convertFlag( cFlag );


	if( mGrpFlag == "0" )		// 个人投保单
		urlStr = "./riskinput/RiskAll.jsp";  

	if( mGrpFlag == "1" )		// 集体下个人投保单
		urlStr = "./riskgrp/RiskAll" + ".jsp";  
	if( mGrpFlag == "3" )		// 集体下个人投保单
		urlStr = "./riskinput/RiskAll.jsp"; 

	//读取险种的界面描述
//	showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800");
	var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//出始化公共信息
	emptyForm();

	fm.all( "RiskCode" ).value = cRiskCode;
	try	{
		showInfo.close();
	}	catch(ex){}

	if( mCurOperateFlag == "1" ) {             // 录入
		if( mGrpFlag == "1" )	{                  // 集体下个人投保单			
			getGrpPolInfo();                       // 带入部分集体信息
		}
		
		if( isSubRisk( cRiskCode ) == true ) {   // 附险
			tPolNo = getMainRiskNo( cRiskCode );   //弹出录入附险的窗口,得到主险保单号码
			
			try	{                                  //出始化附险信息
				initPrivateRiskInfo( tPolNo );
			}	catch(ex1) {
				alert( "初始化险种出错" + ex1 );
			}
		} // end of 附险if
		
		showInfo = null;
	} // end of 录入if
	
	mCurOperateFlag = "";
	mGrpFlag = "";
}

/*********************************************************************
 *  判断该险种是否是附险,在这里确定既可以做主险,又可以做附险的代码
 *  参数  ：  险种代码
 *  返回值：  无
 *********************************************************************
 */
function isSubRisk(cRiskCode) {
  //var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllProShow_BSql");
	mySql.setSqlId("AllProShow_BSql1");
	mySql.addSubPara(cRiskCode ); 
	var arrQueryResult = easyExecSql(mySql.getString(), 1, 0);
	if(arrQueryResult[0] == "S")    //需要转成大写
		return true;
	if(arrQueryResult[0] == "M")
		return false;

	if (arrQueryResult[0].toUpperCase() == "A")
		if (confirm("该险种既可以是主险,又可以是附险!选择确定进入主险录入,选择取消进入附险录入"))
			return false;
		else
			return true;

	return false;
}

/*********************************************************************
 *  弹出录入附险的窗口,得到主险保单号码
 *  参数  ：  险种代码
 *  返回值：  无
 *********************************************************************
 */
function getMainRiskNo(cRiskCode)
{
	var urlStr = "../app/MainRiskNoInput.html";
	var tPolNo="";

//	tPolNo = window.showModalDialog(urlStr,tPolNo,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=310;      //弹出窗口的宽度; 
	var iHeight=100;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	return tPolNo;
}

/*********************************************************************
 *  初始化附险信息
 *  参数  ：  投保单号
 *  返回值：  无
 *********************************************************************
 */
function initPrivateRiskInfo(cPolNo) {
	if(cPolNo=="") {
		alert("没有主险保单号,不能进行附加险录入!");
		mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.
		return false
	}
	
	var arrLCPol = new Array(); 
	var arrQueryResult = null;
	// 主保单信息部分
	//arrQueryResult = easyExecSql("select * from lbpol where polno='"+cPolNo+"'", 1, 0);
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllProShow_BSql");
	mySql.setSqlId("AllProShow_BSql2");
	mySql.addSubPara(cPolNo );
	arrQueryResult = easyExecSql(mySql.getString(), 1, 0); 
	arrLCPol = arrQueryResult[0]; 	
	displayPol( arrLCPol );	
	
	if (arrLCPol == null)	{
		mCurOperateFlag="";        //清空当前操作类型,使得不能进行当前操作.
		alert("读取主险信息失败,不能进行附加险录入!");
		return false
	}
	
	fm.all("MainPolNo").value = cPolNo;
	// 投保人信息部分    
	//首先查询到对应的投保人信息
	var tAR;
			
	// 被保人信息部分
	arrQueryResult = null;
	//arrQueryResult = easyExecSql("select * from lcinsured where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[18]+"'", 1, 0);
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllProShow_BSql");
	mySql.setSqlId("AllProShow_BSql3");
	mySql.addSubPara(cPolNo );
	mySql.addSubPara(arrLCPol[18] );
	arrQueryResult = easyExecSql(mySql.getString(), 1, 0);
	tAR = arrQueryResult[0];
	displayPolInsured(tAR);	
	
	if (arrLCPol[18] == arrLCPol[26]) {
	  	fm.all("SamePersonFlag").checked = true;
		parent.fraInterface.isSamePersonQuery();
    	parent.fraInterface.fm.all("AppntCustomerNo").value = arrLCPol[18];
	} else {
  	//投保人信息
  	if (arrLCPol[28]=="1") {     //集体投保人信息
  	  arrQueryResult = null;
  	  //arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);	  		
  	  mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql4");
		mySql.addSubPara(cPolNo );
		mySql.addSubPara(arrLCPol[26] );
		 arrQueryResult = easyExecSql(mySql.getString(), 1, 0);	  		
  	  tAR = arrQueryResult[0];
  	  displayPolAppntGrp(tAR);
  	} else {                     //个人投保人信息
  	  arrQueryResult = null;
  	  //arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
  	  mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql5");
		mySql.addSubPara(cPolNo );
		mySql.addSubPara(arrLCPol[26] );
		 arrQueryResult = easyExecSql(mySql.getString(), 1, 0);	  
  	  tAR = arrQueryResult[0];
  	  displayPolAppnt(tAR);
  	}
	}

}

/*********************************************************************
 *  校验投保单的输入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function verifyProposal() {
	if( verifyInput() == false ) return false;
	
	BnfGrid.delBlankLine();
	if( BnfGrid.checkValue("BnfGrid") == false ) return false;
	return true;
}

/*********************************************************************
 *  保存个人投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

	showSubmitFrame(mDebug);
	// 校验录入数据
	if( verifyProposal() == false ) return;
	
	if( mAction == "" )
	{
//		showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		if (loadFlag=="1") 
		{
			mAction = "INSERTPERSON";
		}
		else
		{
			mAction = "INSERTGROUP";
		}
	
		fm.all( 'fmAction' ).value = mAction;
		fm.submit(); //提交
	}
}


/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
		content = "保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
		showDiv(operateButton,"true"); 
		showDiv(inputButton,"false");
	}
	mAction = ""; 
}

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
	try
	{
		initForm();
	}
	catch(re)
	{
		alert("在ProposalInput.js-->resetForm函数中发生异常:初始化界面错误!");
	}
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
		parent.fraMain.rows = "0,0,80,72,*";
}

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
	//下面增加相应的代码
	showDiv( operateButton, "false" ); 
	showDiv( inputButton, "true" ); 
}           

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick() {
  if (showInfo == null) 
    mOperate = 0;
    
	if( mOperate == 0 )
	{
		mOperate = 1;
		showInfo = window.open("../app/ProposalQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}           

/*********************************************************************
 *  Click事件，当点击“修改”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function updateClick()
{
	var tProposalNo = "";
	tProposalNo = fm.all( 'ProposalNo' ).value;
	if( tProposalNo == null || tProposalNo == "" )
		alert( "请先做投保单查询操作，再进行修改!" );
	else
	{
		// 校验录入数据
		if (fm.all('divLCAppntInd1').style.display == "none") {
      for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
    	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
    	    fm.elements[elementsNum].verify = "";
    	  }
    	} 
    }
		if( verifyProposal() == false ) return;

		var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )
		{
			showSubmitFrame(mDebug);
//			showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			if (loadFlag=="1")
			{
				mAction = "UPDATEPERSON";
			}
			else
			{
				mAction = "UPDATEGROUP";
			}
			
			fm.all( 'fmAction' ).value = mAction;
			fm.submit(); //提交
		}
	}
}           

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick() { 
	var tProposalNo = fm.all( 'ProposalNo' ).value;
	
	if( tProposalNo == null || tProposalNo == "" )
		alert( "请先做投保单查询操作，再进行删除!" );
	else {
		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )	{
			//showSubmitFrame(mDebug);
//			showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			mAction = "DELETE";
			fm.all( 'fmAction' ).value = mAction;
			fm.submit(); //提交
		}
	}
}           

/*********************************************************************
 *  Click事件，当点击“选择责任”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function chooseDuty()
{
	cRiskCode = fm.RiskCode.value;
	cRiskVersion = fm.RiskVersion.value
	
	if( cRiskCode == "" || cRiskVersion == "" )
	{
		alert( "您必须先录入险种和险种版本才能修改该投保单的责任项。" );
		return false
	}

	showInfo = window.open("../app/ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	return true
}           

/*********************************************************************
 *  Click事件，当点击“查询责任信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showDuty()
{
	//下面增加相应的代码
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "您必须先查询投保单才能查看该投保单的责任项。" );
		return false
	}
	
	var showStr = "正在查询数据，请您稍候......";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//	showModalDialog( "../app/ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var name='提示';   //网页名称，可为空; 
	var iWidth=18cm;      //弹出窗口的宽度; 
	var iHeight=14cm;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	showInfo.close();
}           

/*********************************************************************
 *  Click事件，当点击“关联暂交费信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "您必须先查询投保单才能进入暂交费信息部分。" );
		return false
	}
	
	showInfo = window.open( "../app/ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

/*********************************************************************
 *  Click事件，当双击“投保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppnt()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonMain.html" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}           

/*********************************************************************
 *  Click事件，当双击“被保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsured()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonMain.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
}           

/*********************************************************************
 *  Click事件，当双击“连带被保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubInsured( span, arrPara )
{
	if( mOperate == 0 )
	{
		mOperate = 4;
		spanObj = span;
		showInfo = window.open( "../sys/LDPersonMain.html" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}           

/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPol(cArr) 
{
	try 
	{
	    fm.all('ManageCom').value = cArr[12];
	    fm.all('SaleChnl').value = cArr[15];
	    fm.all('AgentCom').value = cArr[13];
	    fm.all('AgentCode').value = cArr[87];
	    fm.all('AgentGroup').value = cArr[88];
	    fm.all('Handler').value = cArr[82];
	    fm.all('AgentCode1').value = cArr[89];
	
	    fm.all('ContNo').value = cArr[1];
	    fm.all('GrpPolNo').value = cArr[2];

	    fm.all('Amnt').value = cArr[43];
	    fm.all('CValiDate').value = cArr[29];
	    fm.all('HealthCheckFlag').value = cArr[72];
	    fm.all('OutPayFlag').value = cArr[97];
	    fm.all('PayLocation').value = cArr[59];
	    fm.all('BankCode').value = cArr[102];
	    fm.all('BankAccNo').value = cArr[103];
	    fm.all('LiveGetMode').value = cArr[98];
	    fm.all('BonusGetMode').value = cArr[100];
	    fm.all('AutoPayFlag').value = cArr[65];
	    fm.all('InterestDifFlag').value = cArr[66];

	    fm.all('InsuYear').value = cArr[111];
	    fm.all('InsuYearFlag').value = cArr[110];
	    
	} catch(ex) {
	  alert("displayPol err:" + ex + "\ndata is:" + cArr);
	}
}

/*********************************************************************
 *  把保单中的投保人信息显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolAppnt(cArr) {
try {
	fm.all( 'AppntCustomerNo' ).value          = cArr[1];
	fm.all( 'AppntName' ).value                = cArr[6];
	fm.all( 'AppntSex' ).value                 = cArr[7];
	fm.all( 'AppntBirthday' ).value            = cArr[8];
	fm.all( 'AppntAge' ).value                 = "";
	fm.all( 'AppntIDType' ).value              = cArr[20];
	fm.all( 'AppntIDNo' ).value                = cArr[22];
	fm.all( 'AppntNativePlace' ).value         = cArr[9];
	fm.all( 'AppntRgtAddress' ).value          = cArr[52];
	fm.all( 'AppntMarriage' ).value            = cArr[11];
	fm.all( 'AppntNationality' ).value         = cArr[10];
	fm.all( 'AppntDegree' ).value              = cArr[48];
	fm.all( 'AppntSmokeFlag' ).value           = cArr[51];
	fm.all( 'AppntPostalAddress' ).value       = cArr[28];
	fm.all( 'AppntZipCode' ).value             = cArr[29];
	fm.all( 'AppntPhone' ).value               = cArr[30];
	fm.all( 'AppntMobile' ).value              = cArr[32];
	fm.all( 'AppntEMail' ).value               = cArr[33];
	fm.all( 'AppntGrpName' ).value             = cArr[37];
	fm.all( 'AppntGrpPhone' ).value            = cArr[38];
	fm.all( 'AppntGrpAddress' ).value          = cArr[40];
	fm.all( 'AppntGrpZipCode' ).value          = cArr[50];
	fm.all( 'AppntWorkType' ).value            = cArr[46];
	fm.all( 'AppntPluralityType' ).value       = cArr[47];
	fm.all( 'AppntOccupationType' ).value      = cArr[13];
	fm.all( 'AppntOccupationCode' ).value      = cArr[49];
} catch(ex) {
  alert("displayAppnt err:" + ex + "\ndata is:" + cArr);
}
//  fm.all('RelationToInsured').value = '';
}

/*********************************************************************
 *  把保单中的投保人数据显示到投保人部分
 *  参数  ：  集体客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{
	fm.all( 'AppntCustomerNo' ).value = cArr[0];
	fm.all( 'AppntName' ).value = cArr[2];
	fm.all( 'AppntSex' ).value = cArr[3];
	fm.all( 'AppntBirthday' ).value = cArr[4];
	fm.all( 'AppntIDType' ).value =cArr[19];
	fm.all( 'AppntIDNo' ).value = cArr[18];
//  fm.all('RelationToInsured').value = '';
	fm.all('AppntPhone').value = cArr[26];
	fm.all('AppntMobile').value = cArr[28];
	fm.all('AppntPostalAddress').value = cArr[24];
	fm.all('AppntZipCode').value = cArr[25];
	fm.all('AppntEMail').value = cArr[29];
}

/*********************************************************************
 *  把保单中的被保人数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolInsured(cArr) {
  
	fm.all( 'CustomerNo' ).value          = cArr[1];
	fm.all( 'Name' ).value                = cArr[6];
	fm.all( 'Sex' ).value                 = cArr[7];
	fm.all( 'Birthday' ).value            = cArr[8];
	fm.all( 'Age' ).value                 = "";
	fm.all( 'IDType' ).value              = cArr[20];
	fm.all( 'IDNo' ).value                = cArr[22];
	fm.all( 'NativePlace' ).value         = cArr[9];
	fm.all( 'RgtAddress' ).value          = cArr[52];
	fm.all( 'Marriage' ).value            = cArr[11];
	fm.all( 'Nationality' ).value         = cArr[10];
	fm.all( 'Degree' ).value              = cArr[49];
	fm.all( 'SmokeFlag' ).value           = cArr[51];
	fm.all( 'PostalAddress' ).value       = cArr[28];
	fm.all( 'ZipCode' ).value             = cArr[29];
	fm.all( 'Phone' ).value               = cArr[30];
	fm.all( 'Mobile' ).value              = cArr[32];
	fm.all( 'EMail' ).value               = cArr[33];
	fm.all( 'GrpName' ).value             = cArr[37];
	fm.all( 'GrpPhone' ).value            = cArr[38];
	fm.all( 'GrpAddress' ).value          = cArr[40];
	fm.all( 'GrpZipCode' ).value          = cArr[50];
	fm.all( 'WorkType' ).value            = cArr[46];
	fm.all( 'PluralityType' ).value       = cArr[47];
	fm.all( 'OccupationType' ).value      = cArr[13];
	fm.all( 'OccupationCode' ).value      = cArr[48];
	
//  fm.all('RelationToInsured').value = '';
}


/*********************************************************************
 *  把查询返回的客户数据显示到连带被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displaySubInsured()
{
	fm.all( spanObj ).all( 'SubInsuredGrid1' ).value = arrResult[0][0];
	fm.all( spanObj ).all( 'SubInsuredGrid2' ).value = arrResult[0][1];
	fm.all( spanObj ).all( 'SubInsuredGrid3' ).value = arrResult[0][2];
	fm.all( spanObj ).all( 'SubInsuredGrid4' ).value = arrResult[0][3];
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;
		
		if( mOperate == 1 )	{           // 查询保单明细
			var tPolNo = arrResult[0][0];
			
			// 查询保单明细
			queryDescPolDetail( tPolNo );
		}
		
		if( mOperate == 2 ) {		// 投保人信息	  
		//	arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql6");
		mySql.addSubPara(arrQueryResult[0][0]  ); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);
			if (arrResult == null) {
			  alert("未查到投保人信息");
			} else {
			   displayAppnt(arrResult[0]);
			}

	  }
		if( mOperate == 3 )	{		// 主被保人信息
			//arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql7");
		mySql.addSubPara(arrQueryResult[0][0]  ); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);
			if (arrResult == null) {
			  alert("未查到主被保人信息");
			} else {
			   displayInsured(arrResult[0]);
			}

	  }
		if( mOperate == 4 )	{		// 连带被保人信息
			displaySubInsured(arrResult[0]);
	  }
	}
	mOperate = 0;		// 恢复初态
	
	emptyUndefined(); 
}

/*********************************************************************
 *  根据查询返回的信息查询投保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{

	emptyForm();
   	parent.fraTitle.window.location = "./AllProposalQueryDetail.jsp?PolNo=" + cPolNo;
	//parent.fraTitle.window.submit();
}

function queryDescPolDetail( cPolNo )
{

	emptyForm();
   
	parent.fraTitle.window.location = "./AllDescProposalQueryDetail.jsp?PolNo=" + cPolNo;
		
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

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//*************************************************************
//被保人客户号查询按扭事件
function queryInsuredNo() {
  if (fm.all("CustomerNo").value == "") {
    showInsured1();
  }  else {
    //arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + fm.all("CustomerNo").value + "'", 1, 0);
    mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql8");
		mySql.addSubPara( fm.all("CustomerNo").value); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);
    if (arrResult == null) {
      alert("未查到主被保人信息");
      displayInsured(new Array());
      emptyUndefined(); 
    } else {
      displayInsured(arrResult[0]);
    }
  }
}

//*************************************************************
//投保人客户号查询按扭事件
function queryAppntNo() {
  if (fm.all("AppntCustomerNo").value == "" && loadFlag == "1") {
    showAppnt1();
  } else if (loadFlag != "1") {
    alert("只提供个人投保单客户号查询！");
  } else {
    //arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + fm.all("AppntCustomerNo").value + "'", 1, 0);
     mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql9");
		mySql.addSubPara( fm.all("AppntCustomerNo").value); 
		 arrResult = easyExecSql(mySql.getString(), 1, 0);
    if (arrResult == null) {
      alert("未查到投保人信息");
      displayAppnt(new Array());
      emptyUndefined(); 
    } else {
      displayAppnt(arrResult[0]);
    }
  }
}

//*************************************************************
//投保人与被保人相同选择框事件
var stackAppnt = new Array();
function isSamePerson() {
  showPage(this,divLCAppntInd1); 

  if (fm.all('divLCAppntInd1').style.display == "none") {
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
  	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
  	    stackAppnt.push(fm.elements[elementsNum].verify);
  	    fm.elements[elementsNum].verify = "";
  	  }
  	} 
  } else {
    //alert(stackAppnt);
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
  	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) { 	    
  	    fm.elements[elementsNum].verify = stackAppnt.shift();
  	  }
  	} 
  }
}  

/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt(cArr) {
try {
	fm.all( 'AppntCustomerNo' ).value          = cArr[0];
	fm.all( 'AppntName' ).value                = cArr[2];
	fm.all( 'AppntSex' ).value                 = cArr[3];
	fm.all( 'AppntBirthday' ).value            = cArr[4];
	fm.all( 'AppntAge' ).value                 = "i don't know";
	fm.all( 'AppntIDType' ).value              = cArr[16];
	fm.all( 'AppntIDNo' ).value                = cArr[18];
	fm.all( 'AppntNativePlace' ).value         = cArr[5];
	fm.all( 'AppntRgtAddress' ).value          = cArr[54];
	fm.all( 'AppntMarriage' ).value            = cArr[7];
	fm.all( 'AppntNationality' ).value         = cArr[6];
	fm.all( 'AppntDegree' ).value              = cArr[51];
	fm.all( 'AppntSmokeFlag' ).value           = cArr[53];
	fm.all( 'AppntPostalAddress' ).value       = cArr[24];
	fm.all( 'AppntZipCode' ).value             = cArr[25];
	fm.all( 'AppntPhone' ).value               = cArr[26];
	fm.all( 'AppntMobile' ).value              = cArr[28];
	fm.all( 'AppntEMail' ).value               = cArr[29];
	fm.all( 'AppntGrpName' ).value             = cArr[35];
	fm.all( 'AppntGrpPhone' ).value            = cArr[36];
	fm.all( 'AppntGrpAddress' ).value          = cArr[38];
	fm.all( 'AppntGrpZipCode' ).value          = cArr[52];
	fm.all( 'AppntWorkType' ).value            = cArr[48];
	fm.all( 'AppntPluralityType' ).value       = cArr[49];
	fm.all( 'AppntOccupationType' ).value      = cArr[9];
	fm.all( 'AppntOccupationCode' ).value      = cArr[50];
} catch(ex) {
  alert("displayAppnt err:" + ex + "\ndata is:" + cArr);
}
//  fm.all('RelationToInsured').value = '';
}

/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  集体客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayAppntGrp( cArr )
{
	fm.all( 'AppntCustomerNo' ).value = cArr[0];
	fm.all( 'AppntName' ).value = cArr[2];
	fm.all( 'AppntSex' ).value = cArr[3];
	fm.all( 'AppntBirthday' ).value = cArr[4];
	fm.all( 'AppntIDType' ).value =cArr[19];
	fm.all( 'AppntIDNo' ).value = cArr[18];
//  fm.all('RelationToInsured').value = '';
	fm.all('AppntPhone').value = cArr[26];
	fm.all('AppntMobile').value = cArr[28];
	fm.all('AppntPostalAddress').value = cArr[24];
	fm.all('AppntZipCode').value = cArr[25];
	fm.all('AppntEMail').value = cArr[29];
}

/*********************************************************************
 *  把查询返回的客户数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayInsured(cArr) {
  
try{    fm.all( 'CustomerNo' ).value          = cArr[0];}catch(ex){}
try{    fm.all( 'Name' ).value                = cArr[2];}catch(ex){}
try{    fm.all( 'Sex' ).value                 = cArr[3];}catch(ex){}
try{    fm.all( 'Birthday' ).value            = cArr[4];}catch(ex){}
        //try{  fm.all( 'Age' ).value                 = "i don't know";}catch(ex){}
try{    fm.all( 'IDType' ).value              = cArr[16];}catch(ex){}
try{	fm.all( 'IDNo' ).value                = cArr[18];}catch(ex){}
try{	fm.all( 'NativePlace' ).value         = cArr[5];}catch(ex){}
try{	fm.all( 'RgtAddress' ).value          = cArr[54];}catch(ex){}
try{	fm.all( 'Marriage' ).value            = cArr[7];}catch(ex){}
try{	fm.all( 'Nationality' ).value         = cArr[6];}catch(ex){}
try{	fm.all( 'Degree' ).value              = cArr[51];}catch(ex){}
try{	fm.all( 'SmokeFlag' ).value           = cArr[53];}catch(ex){}
try{	fm.all( 'PostalAddress' ).value       = cArr[24];}catch(ex){}
try{	fm.all( 'ZipCode' ).value             = cArr[25];}catch(ex){}
try{	fm.all( 'Phone' ).value               = cArr[26];}catch(ex){}
try{	fm.all( 'Mobile' ).value              = cArr[28];}catch(ex){}
try{	fm.all( 'EMail' ).value               = cArr[29];}catch(ex){}
try{	fm.all( 'GrpName' ).value             = cArr[35];}catch(ex){}
try{	fm.all( 'GrpPhone' ).value            = cArr[36];}catch(ex){}
try{	fm.all( 'GrpAddress' ).value          = cArr[38];}catch(ex){}
try{	fm.all( 'GrpZipCode' ).value          = cArr[52];}catch(ex){}
try{	fm.all( 'WorkType' ).value            = cArr[48];}catch(ex){}
try{	fm.all( 'PluralityType' ).value       = cArr[49];}catch(ex){}
try{	fm.all( 'OccupationType' ).value      = cArr[9];}catch(ex){}
try{	fm.all( 'OccupationCode' ).value      = cArr[50];}catch(ex){}	
//  fm.all('RelationToInsured').value = '';
}

//*********************************************************************
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
}           

//*********************************************************************
function showInsured1()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
}  

function isSamePersonQuery() {
//  divSamePerson.style.display = '';
  divLCAppntInd1.style.display = '';
}
