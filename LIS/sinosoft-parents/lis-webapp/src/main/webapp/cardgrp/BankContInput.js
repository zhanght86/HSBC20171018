//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
this.window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "cardgrp.BankContInputSql";

//记录需要录入两遍时的两次值
//var theFirstValue="";
//var theSecondValue="";

//该文件中包含客户端需要处理的函数和事件
var showInfo1;

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag = false;
//
var arrCardRisk;

//工作流flag
var mWFlag = 0;
//使得从该窗口弹出的窗口能够聚焦


/*********************************************************************
 *  保存集体投保单的提交 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{   
//	alert(fm.PolAppntDate.value);
//        return;
///////////////add by yaory
//alert(fm.PolAppntDate.value.substring(4,5));
if(fm.PolAppntDate.value.length!=10 || fm.PolAppntDate.value.substring(4,5)!='-' || fm.PolAppntDate.value.substring(7,8)!='-' || fm.PolAppntDate.value.substring(0,1)=='0')
{
alert("请输入正确的日期格式！");
fm.PolAppntDate.focus();
return;
}
///////end add
	if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		alert("证件类型和证件号码必须同时填写或不填");
		return false;
		}
//	if((fm.PayMode.value=="4"||fm.PayMode.value=="7")&&fm.AppntBankCode.value==""&&fm.AppntAccName.value==""&&fm.AppntBankAccNo.value=="")
//	{
//
//		alert("请填入首期转帐开户行、首期帐户姓名、首期帐户号！");
//		return false;
//	}
//	if(fm.SecPayMode.value=="3"&&fm.SecAppntBankCode.value==""&&fm.SecAppntAccName.value==""&&fm.SecAppntBankAccNo.value=="")
//	{
//
//		alert("请填入续期转帐开户行、续期帐户姓名、续期帐户号！");
//		return false;
//	}
  //hanlin 由于校验的问题，暂时去掉，回头再加上，hl 20050502
  //校验已经加上 hl 20050512
  if( verifyInputNB("1") == false ) return false;

	/*如果是符合修改时使用，执行更新操作 20041125 wzw*/
	if(LoadFlag=="3"){
	  updateClick();
	  return;
	}

  if(fm.all('PayMode').value=='7'){

  }
  if(fm.all('SecPayMode').value=='7'){

  }
  //alert(fm.Remark.value);

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //      if (verifyInput2()== false) {
	//  if (!confirm("投保单录入可能有错误，是否继续保存？")) return false;
  //      }
  //alert("123456789,,mAction=="+mAction);
	if( mAction == "" )
	{
		//showSubmitFrame(mDebug);
		mAction = "INSERT";
		fm.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();

		if (fm.all('ProposalContNo').value == "") {
		  //alert("查询结果只能进行修改操作！");
		  mAction = "";
		} else {
		  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		  var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

		  //保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
		  tAction = fm.action;
		  //alert("taction==="+tAction);
		  fm.action = fm.action + "?BQFlag=" + BQFlag;
		  //alert("fm==="+fm.action);
		  fm.submit(); //提交
		  fm.action = tAction;
		}
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
	//alert("here 1!");
	//showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		showDiv(operateButton, "true");
		//showDiv(inputButton, "false");
		//alert("djfdjfldj")
		//window.top.parent.fraInterface.initForm();
		//alert(12313)
		//top.close();
		//alert("xingxing")


		if(approvefalg=="2")
		{
			//top.close();
		}
      top.close();
//	  try { goToPic(2) } catch(e) {}

	}
	mAction = "";
}



function afterSubmit4( FlagStr, content )
{
	//alert("here 1!");
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		showDiv(operateButton, "true");
		//showDiv(inputButton, "false");
		//alert("djfdjfldj")
		//window.top.parent.fraInterface.initForm();
		//alert(12313)
		//top.close();
		//alert("xingxing")


		if(approvefalg=="2")
		{
			//top.close();
		}


	}
	mAction = "";

}
function afterSubmit5( FlagStr, content )
{
	//alert("here 1!");
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		showDiv(operateButton, "true");

		top.close();


		if(approvefalg=="2")
		{
			//top.close();
		}


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
		fm.all('PrtNo').value = prtNo;
	}
	catch( re )
	{
		alert("在GroupPolInput.js-->resetForm函数中发生异常:初始化界面错误!");
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
	//下面增加相应的代码
	showDiv( operateButton, "false" );
	showDiv( inputButton, "true" );
/*暂时屏蔽，保全部分再作处理	fm.all('RiskCode').value = "";

//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
  if (BQFlag=="2") {
    var strSql = "select grppolno, grpno from lcgrppol where prtno='" + prtNo + "' and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    mOperate = 1;
    afterQuery(arrResult);
    //strSql = "select GrpNo,GrpName,GrpAddress,Satrap from LDGrp where GrpNo='" + arrResult[0][1] + "'";
    //arrResult = easyExecSql(strSql);
    //mOperate = 2;
    //afterQuery(arrResult);

    fm.all('RiskCode').value = BQRiskCode;
    fm.all('RiskCode').className = "readonly";
    fm.all('RiskCode').readOnly = true;
    fm.all('RiskCode').ondblclick = "";
  }
	fm.all('ContNo').value = "";
	fm.all('GrpProposalNo').value = "";*/
	if( verifyInput2() == false ) return false;
}

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick()
{
        if(this.ScanFlag == "1"){
	  alert( "有扫描件录入不允许查询!" );
          return false;
        }
	if( mOperate == 0 )
	{
		mOperate = 1;
		cContNo = fm.all( 'ContNo' ).value;
		showInfo = window.open("./ContQueryMain.jsp?ContNo=" + cContNo,"",sFeatures);
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
	//alert(fm.Remark.value);
  if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
	  alert("证件类型和证件号码必须同时填写或不填");
	  return false;
	}
  if((fm.PayMode.value=="4"||fm.PayMode.value=="7")&&fm.AppntBankCode.value==""&&fm.AppntAccName.value==""&&fm.AppntBankAccNo.value=="")
	{

		alert("请填入首期转帐开户行、首期帐户姓名、首期帐户号！");
		return false;
	}
  if(fm.SecPayMode.value=="3"&&fm.SecAppntBankCode.value==""&&fm.SecAppntAccName.value==""&&fm.SecAppntBankAccNo.value=="")
	{

		alert("请填入续期转帐开户行、续期帐户姓名、续期帐户号！");
		return false;
	}
  if( verifyInputNB("1") == false ) return false;
	var tGrpProposalNo = "";
	tGrpProposalNo = fm.all( 'ProposalContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "请先做投保单查询操作，再进行修改!" );
	else
	{
				ImpartGrid.delBlankLine();
		var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "UPDATE";
			fm.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
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
function deleteClick()
{
	var tGrpProposalNo = "";
	var tProposalContNo = "";
	if(LoadFlag==1)
	{
		tGrpProposalNo = fm.all( 'GrpContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "请先做投保单查询操作，再进行删除!" );
	else
	{
		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			fm.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			fm.submit(); //提交
		}
	 }

      }
      //top.close();
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

/*********************************************************************
 *  当点击“进入个人信息”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoInsured()
{
	//下面增加相应的代码

	var tAppntNo = fm.AppntNo.value;
	var tAppntName = fm.AppntName.value;
	fm.ContNo.value=fm.ProposalContNo.value;
	if( fm.ContNo.value == "" )
	{
		alert("您必须先录入合同信息才能进入被保险人信息部分。");
		return false;
	}

	//把集体信息放入内存
	mSwitch = parent.VD.gVSwitch;  //桢容错
	putCont();

	try { goToPic(2) } catch(e) {}

	try {
	  parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type+ "&MissionID=" + tMissionID+ "&SubMissionID=" + tSubMissionID+ "&AppntNo=" + tAppntNo+ "&AppntName=" + tAppntName+"&checktype=1"+"&scantype="+scantype ;
	}
	catch (e) {
		parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=1&type=" + type+ "&MissionID=" +tMissionID+ "&SubMissionID=" + tSubMissionID+ "&AppntNo=" + tAppntNo+ "&AppntName=" + tAppntName;
	}
}


/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm(wFlag)
{
	if (wFlag ==1 ) //录入完毕确认
	{
    //alert(ScanFlag);
    //var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
	
	var mysql1= new SqlClass();
    var sqlid1 = "BankContInputSql1";
	mysql1.setResourceName(sqlresourcename);
	mySql1.setSqlId(sqlid1);
	mysql1.addSubPara(fm.ContNo.value);
	
		turnPage.strQueryResult = easyQueryVer3(mysql1.getString(), 1, 0, 1);
		if (turnPage.strQueryResult) {
		  alert("该合同已经做过保存！");
		  return;
		}
		if(fm.all('ProposalContNo').value == "")
	  {
	    alert("合同信息未保存,不容许您进行 [录入完毕] 确认！");
	   	return;
	  }
    if(ScanFlag=="1"){
		  fm.WorkFlowFlag.value = "0000001099";
    }
    else{
		  fm.WorkFlowFlag.value = "0000001098";
	  }
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;			//录入完毕
  }
  else if (wFlag ==2)//复核完毕确认
  {
  	if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001001";					//复核完毕
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
		approvefalg="2";
	}
	  else if (wFlag ==3)
  {
  	if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001002";					//复核修改完毕
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else if(wFlag == 4)
	{
		 if(fm.all('ProposalContNo').value == "")
	   {
	   	  alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001021";					//问题修改
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./InputConfirm.jsp";
  fm.submit(); //提交
}

/*********************************************************************
 *  把合同信息放入内存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function putCont()
{
	delContVar();
	addIntoCont();
}

/*********************************************************************
 *  把合同信息放入加到变量中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addIntoCont()
{
	//try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
	// body信息
	try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
	// 集体信息
	//由"./AutoCreatLDGrpInit.jsp"自动生成
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
    try{mSwitch.addVar('ProposalContNo','',fm.ProposalContNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
    try{mSwitch.addVar('ContType','',fm.ContType.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('PolType','',fm.PolType.value);}catch(ex){};
    try{mSwitch.addVar('CardFlag','',fm.CardFlag.value);}catch(ex){};
    try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('AgentCom','',fm.AgentCom.value);}catch(ex){};
    try{mSwitch.addVar('AgentCode','',fm.AgentCode.value);}catch(ex){};
    try{mSwitch.addVar('AgentGroup','',fm.AgentGroup.value);}catch(ex){};
    try{mSwitch.addVar('AgentCode1','',fm.AgentCode1.value);}catch(ex){};
    try{mSwitch.addVar('AgentType','',fm.AgentType.value);}catch(ex){};
    try{mSwitch.addVar('SaleChnl','',fm.SaleChnl.value);}catch(ex){};
    try{mSwitch.addVar('Handler','',fm.Handler.value);}catch(ex){};
    try{mSwitch.addVar('Password','',fm.Password.value);}catch(ex){};
    try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
    try{mSwitch.addVar('AppntName','',fm.AppntName.value);}catch(ex){};
    try{mSwitch.addVar('AppntSex','',fm.AppntSex.value);}catch(ex){};
    try{mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDType','',fm.AppntIDType.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value);}catch(ex){};

    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('InsuredName','',fm.InsuredName.value);}catch(ex){};
    try{mSwitch.addVar('InsuredSex','',fm.InsuredSex.value);}catch(ex){};
    try{mSwitch.addVar('InsuredBirthday','',fm.InsuredBirthday.value);}catch(ex){};
    try{mSwitch.addVar('InsuredIDType','',fm.InsuredIDType.value);}catch(ex){};
    try{mSwitch.addVar('InsuredIDNo','',fm.InsuredIDNo.value);}catch(ex){};
    try{mSwitch.addVar('PayIntv','',fm.PayIntv.value);}catch(ex){};
    try{mSwitch.addVar('PayMode','',fm.PayMode.value);}catch(ex){};
    try{mSwitch.addVar('PayLocation','',fm.PayLocation.value);}catch(ex){};
    try{mSwitch.addVar('DisputedFlag','',fm.DisputedFlag.value);}catch(ex){};
    try{mSwitch.addVar('OutPayFlag','',fm.OutPayFlag.value);}catch(ex){};
    try{mSwitch.addVar('GetPolMode','',fm.GetPolMode.value);}catch(ex){};
    try{mSwitch.addVar('SignCom','',fm.SignCom.value);}catch(ex){};
    try{mSwitch.addVar('SignDate','',fm.SignDate.value);}catch(ex){};
    try{mSwitch.addVar('SignTime','',fm.SignTime.value);}catch(ex){};
    try{mSwitch.addVar('ConsignNo','',fm.ConsignNo.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('PrintCount','',fm.PrintCount.value);}catch(ex){};
    try{mSwitch.addVar('LostTimes','',fm.LostTimes.value);}catch(ex){};
    try{mSwitch.addVar('Lang','',fm.Lang.value);}catch(ex){};
    try{mSwitch.addVar('Currency','',fm.Currency.value);}catch(ex){};
    try{mSwitch.addVar('Remark','',fm.Remark.value);}catch(ex){};
    try{mSwitch.addVar('Peoples','',fm.Peoples.value);}catch(ex){};
    try{mSwitch.addVar('Mult','',fm.Mult.value);}catch(ex){};
    try{mSwitch.addVar('Prem','',fm.Prem.value);}catch(ex){};
    try{mSwitch.addVar('Amnt','',fm.Amnt.value);}catch(ex){};
    try{mSwitch.addVar('SumPrem','',fm.SumPrem.value);}catch(ex){};
    try{mSwitch.addVar('Dif','',fm.Dif.value);}catch(ex){};
    try{mSwitch.addVar('PaytoDate','',fm.PaytoDate.value);}catch(ex){};
    try{mSwitch.addVar('FirstPayDate','',fm.FirstPayDate.value);}catch(ex){};
    try{mSwitch.addVar('CValiDate','',fm.CValiDate.value);}catch(ex){};
    try{mSwitch.addVar('InputOperator','',fm.InputOperator.value);}catch(ex){};
    try{mSwitch.addVar('InputDate','',fm.InputDate.value);}catch(ex){};
    try{mSwitch.addVar('InputTime','',fm.InputTime.value);}catch(ex){};
    try{mSwitch.addVar('ApproveFlag','',fm.ApproveFlag.value);}catch(ex){};
    try{mSwitch.addVar('ApproveCode','',fm.ApproveCode.value);}catch(ex){};
    try{mSwitch.addVar('ApproveDate','',fm.ApproveDate.value);}catch(ex){};
    try{mSwitch.addVar('ApproveTime','',fm.ApproveTime.value);}catch(ex){};
    try{mSwitch.addVar('UWFlag','',fm.UWFlag.value);}catch(ex){};
    try{mSwitch.addVar('UWOperator','',fm.UWOperator.value);}catch(ex){};
    try{mSwitch.addVar('UWDate','',fm.UWDate.value);}catch(ex){};
    try{mSwitch.addVar('UWTime','',fm.UWTime.value);}catch(ex){};
    try{mSwitch.addVar('AppFlag','',fm.AppFlag.value);}catch(ex){};
    try{mSwitch.addVar('PolApplyDate','',fm.PolApplyDate.value);}catch(ex){};
    try{mSwitch.addVar('GetPolDate','',fm.GetPolDate.value);}catch(ex){};
    try{mSwitch.addVar('GetPolTime','',fm.GetPolTime.value);}catch(ex){};
    try{mSwitch.addVar('CustomGetPolDate','',fm.CustomGetPolDate.value);}catch(ex){};
    try{mSwitch.addVar('State','',fm.State.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};

   //新的数据处理
try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
try { mSwitch.addVar('AppntName','',fm.AppntName.value); } catch(ex) { };
try { mSwitch.addVar('AppntSex','',fm.AppntSex.value); } catch(ex) { };
try { mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value); } catch(ex) { };
try { mSwitch.addVar('AppntIDType','',fm.AppntIDType.value); } catch(ex) { };
try { mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value); } catch(ex) { };
try { mSwitch.addVar('AppntPassword','',fm.AppntPassword.value); } catch(ex) { };
try { mSwitch.addVar('AppntNativePlace','',fm.AppntNativePlace.value); } catch(ex) { };
try { mSwitch.addVar('AppntNationality','',fm.AppntNationality.value); } catch(ex) { };
try { mSwitch.addVar('AddressNo','',fm.AppntAddressNo.value); } catch(ex) { };
try { mSwitch.addVar('AppntRgtAddress','',fm.AppntRgtAddress.value); } catch(ex) { };
try { mSwitch.addVar('AppntMarriage','',fm.AppntMarriage.value); } catch(ex) { };
try { mSwitch.addVar('AppntMarriageDate','',fm.AppntMarriageDate.value); } catch(ex) { };
try { mSwitch.addVar('AppntHealth','',fm.AppntHealth.value); } catch(ex) { };
try { mSwitch.addVar('AppntStature','',fm.AppntStature.value); } catch(ex) { };
try { mSwitch.addVar('AppntAvoirdupois','',fm.AppntAvoirdupois.value); } catch(ex) { };
try { mSwitch.addVar('AppntDegree','',fm.AppntDegree.value); } catch(ex) { };
try { mSwitch.addVar('AppntCreditGrade','',fm.AppntCreditGrade.value); } catch(ex) { };
try { mSwitch.addVar('AppntOthIDType','',fm.AppntOthIDType.value); } catch(ex) { };
try { mSwitch.addVar('AppntOthIDNo','',fm.AppntOthIDNo.value); } catch(ex) { };
try { mSwitch.addVar('AppntICNo','',fm.AppntICNo.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpNo','',fm.AppntGrpNo.value); } catch(ex) { };
try { mSwitch.addVar('AppntJoinCompanyDate','',fm.AppntJoinCompanyDate.value); } catch(ex) { };
try { mSwitch.addVar('AppntStartWorkDate','',fm.AppntStartWorkDate.value); } catch(ex) { };
try { mSwitch.addVar('AppntPosition','',fm.AppntPosition.value); } catch(ex) { };
try { mSwitch.addVar('AppntSalary','',fm.AppntSalary.value); } catch(ex) { };
try { mSwitch.addVar('AppntOccupationType','',fm.AppntOccupationType.value); } catch(ex) { };
try { mSwitch.addVar('AppntOccupationCode','',fm.AppntOccupationCode.value); } catch(ex) { };
try { mSwitch.addVar('AppntWorkType','',fm.AppntWorkType.value); } catch(ex) { };
try { mSwitch.addVar('AppntPluralityType','',fm.AppntPluralityType.value); } catch(ex) { };
try { mSwitch.addVar('AppntDeathDate','',fm.AppntDeathDate.value); } catch(ex) { };
try { mSwitch.addVar('AppntSmokeFlag','',fm.AppntSmokeFlag.value); } catch(ex) { };
try { mSwitch.addVar('AppntBlacklistFlag','',fm.AppntBlacklistFlag.value); } catch(ex) { };
try { mSwitch.addVar('AppntProterty','',fm.AppntProterty.value); } catch(ex) { };
try { mSwitch.addVar('AppntRemark','',fm.AppntRemark.value); } catch(ex) { };
try { mSwitch.addVar('AppntState','',fm.AppntState.value); } catch(ex) { };
try { mSwitch.addVar('AppntOperator','',fm.AppntOperator.value); } catch(ex) { };
try { mSwitch.addVar('AppntMakeDate','',fm.AppntMakeDate.value); } catch(ex) { };
try { mSwitch.addVar('AppntMakeTime','',fm.AppntMakeTime.value); } catch(ex) { };
try { mSwitch.addVar('AppntModifyDate','',fm.AppntModifyDate.value); } catch(ex) { };
try { mSwitch.addVar('AppntModifyTime','',fm.AppntModifyTime.value); } catch(ex) { };
try { mSwitch.addVar('AppntHomeAddress','',fm.AppntHomeAddress.value); } catch(ex) { };
try { mSwitch.addVar('AppntHomeZipCode','',fm.AppntHomeZipCode.value); } catch(ex) { };
try { mSwitch.addVar('AppntHomePhone','',fm.AppntHomePhone.value); } catch(ex) { };
try { mSwitch.addVar('AppntHomeFax','',fm.AppntHomeFax.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpName','',fm.AppntGrpName.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpPhone','',fm.AppntGrpPhone.value); } catch(ex) { };
try { mSwitch.addVar('CompanyAddress','',fm.CompanyAddress.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpZipCode','',fm.AppntGrpZipCode.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpFax','',fm.AppntGrpFax.value); } catch(ex) { };
try { mSwitch.addVar('AppntPostalAddress','',fm.AppntPostalAddress.value); } catch(ex) { };
try { mSwitch.addVar('AppntZipCode','',fm.AppntZipCode.value); } catch(ex) { };
try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
try { mSwitch.addVar('AppntMobile','',fm.AppntMobile.value); } catch(ex) { };
try { mSwitch.addVar('AppntFax','',fm.AppntFax.value); } catch(ex) { };
try { mSwitch.addVar('AppntEMail','',fm.AppntEMail.value); } catch(ex) { };
try { mSwitch.addVar('AppntBankAccNo','',fm.all('AppntBankAccNo').value); } catch(ex) { };
try { mSwitch.addVar('AppntBankCode','',fm.all('AppntBankCode').value); } catch(ex) { };
try { mSwitch.addVar('AppntAccName','',fm.all('AppntAccName').value); } catch(ex) { };


}

/*********************************************************************
 *  把集体信息从变量中删除
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delContVar()
{
	try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
	// body信息
	try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
	// 集体信息
	//由"./AutoCreatLDGrpInit.jsp"自动生成
   try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
   try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ContType'); } catch(ex) { };
   try { mSwitch.deleteVar('FamilyType'); } catch(ex) { };
   try { mSwitch.deleteVar('PolType'); } catch(ex) { };
   try { mSwitch.deleteVar('CardFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
   try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
   try { mSwitch.deleteVar('Handler'); } catch(ex) { };
   try { mSwitch.deleteVar('Password'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntName'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntSex'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNam'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredSex'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
   try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
   try { mSwitch.deleteVar('PayLocation'); } catch(ex) { };
   try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
   try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
   try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
   try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ConsignNo'); } catch(ex) { };
   try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
   try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AccName'); } catch(ex) { };
   try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
   try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
   try { mSwitch.deleteVar('Lang'); } catch(ex) { };
   try { mSwitch.deleteVar('Currency'); } catch(ex) { };
   try { mSwitch.deleteVar('Remark'); } catch(ex) { };
   try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
   try { mSwitch.deleteVar('Mult'); } catch(ex) { };
   try { mSwitch.deleteVar('Prem'); } catch(ex) { };
   try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
   try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
   try { mSwitch.deleteVar('Dif'); } catch(ex) { };
   try { mSwitch.deleteVar('PaytoDate'); } catch(ex) { };
   try { mSwitch.deleteVar('FirstPayDate'); } catch(ex) { };
   try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
   try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
   try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
   try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
   try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('State'); } catch(ex) { };
   try { mSwitch.deleteVar('Operator'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeDate'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyTime'); } catch(ex) { };

    //新的删除数据处理
try { mSwitch.deleteVar  ('AppntNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntName'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntSex'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBirthday'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntIDType'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntIDNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntPassword'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntNativePlace'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntNationality'); } catch(ex) { };
try {mSwitch.deleteVar('AddressNo');}catch(ex){};
try { mSwitch.deleteVar  ('AppntRgtAddress'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntMarriage'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntMarriageDate'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntHealth'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntStature'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntAvoirdupois'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntDegree'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntCreditGrade'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntOthIDType'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntOthIDNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntICNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntJoinCompanyDate'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntStartWorkDate'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntPosition') } catch(ex) { };
try { mSwitch.deleteVar  ('AppntSalary'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntOccupationType'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntOccupationCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntWorkType'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntPluralityType');} catch(ex) { };
try { mSwitch.deleteVar  ('AppntDeathDate'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntSmokeFlag'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBlacklistFlag'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntProterty'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntRemark'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntState'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntOperator'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntMakeDate'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntMakeTime'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntModifyDate'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntModifyTime'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntHomeAddress'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntHomeZipCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntHomePhone'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntHomeFax'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntPostalAddress'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntZipCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntFax'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntMobile'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntEMail'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpName'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpPhone'); } catch(ex) { };
try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
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

		if( mOperate == 1 ){		// 查询投保单
			fm.all( 'ContNo' ).value = arrQueryResult[0][0];
			//"select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'"
			
			var mysql2= new SqlClass();
            var sqlid2 = "BankContInputSql2";
	        mysql2.setResourceName(sqlresourcename);
	        mySql2.setSqlId(sqlid2);
	        mysql2.addSubPara(arrQueryResult[0][0]);
	        arrResult = easyExecSql(mysql2.getString(), 1, 0);

			if (arrResult == null) {
			  alert("未查到投保单信息");
			} else {
			   displayLCContPol(arrResult[0]);
			}
		}

		if( mOperate == 2 )	{		// 投保人信息
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			//"select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'"
			
			var mysql3= new SqlClass();
            var sqlid3 = "BankContInputSql3";
	        mysql3.setResourceName(sqlresourcename);
	        mySql3.setSqlId(sqlid3);
	        mysql3.addSubPara(arrQueryResult[0][0]);
	        
	        arrResult = easyExecSql(mysql3.getString(), 1, 0);
			
			if (arrResult == null) {
			  alert("未查到投保人信息");
			} else {
			   displayAppnt(arrResult[0]);
			}
		}
	}

	mOperate = 0;		// 恢复初态

	showCodeName();

}
/*********************************************************************
 *  投保单信息初始化函数。以loadFlag标志作为分支
 *  参数  ：  投保单印刷号
 *  返回值：  无
 *********************************************************************
 */
function detailInit(PrtNo){
  try{

    if(PrtNo==null) return;

    //查询首期账号
    //alert("PrtNo=="+PrtNo);
    var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
               + "where trim(a.TempFeeNo)=trim(b.TempFeeNo) and a.PayMode='4' and b.TempFeeType='1'"
               + "and b.OtherNo='"+PrtNo+"' and b.operstate='0' ";

            var mysql4= new SqlClass();
            var sqlid4 = "BankContInputSql4";
	        mysql4.setResourceName(sqlresourcename);
	        mySql4.setSqlId(sqlid4);
	        mysql4.addSubPara(PrtNo);

    arrResult = easyExecSql(mysql4.getString(),1,0);

    if(arrResult==null){
    	//return;
    	//默认为续期为银行转账
//    	alert("aaa");
//      fm.all('PayMode').value="3";
    }
    else{
      displayFirstAccount();
      //如果首期交费方式为银行转账，续期同样为银行转账
      fm.all('PayMode').value="";
    }

         //"select * from LCCont where PrtNo='"+PrtNo+"'"
            var mysql5= new SqlClass();
            var sqlid5 = "BankContInputSql5";
	        mysql5.setResourceName(sqlresourcename);
	        mySql5.setSqlId(sqlid5);
	        mysql5.addSubPara(PrtNo);
    arrResult=easyExecSql(mysql5.getString(),1,0);

    if(arrResult==null){
      alert(未得到投保单信息);
      return;
    }
    else{
      displayLCCont();       //显示投保单详细内容
    }
//"select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'"
 var mysql6= new SqlClass();
            var sqlid6 = "BankContInputSql6";
	        mysql6.setResourceName(sqlresourcename);
	        mySql6.setSqlId(sqlid6);
	        mysql6.addSubPara(arrResult[0][19]);
    arrResult = easyExecSql(mysql6.getString(), 1, 0);

    if (arrResult == null) {
      alert("未查到投保人客户层信息");
    }
    else{
      //显示投保人信息
      displayAppnt(arrResult[0]);
    }
//"select * from LCAppnt where PrtNo='"+PrtNo+"'"

var mysql7= new SqlClass();
var sqlid7 = "BankContInputSql7";
mysql7.setResourceName(sqlresourcename);
mySql7.setSqlId(sqlid7);
mysql7.addSubPara(PrtNo);
	        
    arrResult=easyExecSql(mysql7.getString(),1,0);

    if(arrResult==null){
      alert("未得到投保人保单层信息");
      return;
    }else{
      displayContAppnt();       //显示投保人的详细内容
    }
    getAge();
    var tContNo = arrResult[0][1];
    var tCustomerNo = arrResult[0][3];		// 得到投保人客户号
    var tAddressNo = arrResult[0][9]; 		// 得到投保人地址号

    //alert("--tContNo=="+tContNo+"--tCustomerNo--"+tCustomerNo+"--tAddressNo--"+tAddressNo);


/**************
    arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
    if(arrResult==null){
      alert("未得到用户信息");
      return;
    }else{
      displayAppnt();       //显示投保人详细内容
      emptyUndefined();
    }
******************/
/******************
    arrResult=easyExecSql("select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+tCustomerNo+"'",1,0);
    if(arrResult==null){
      alert(未得到投保人地址信息);
      return;
    }else{
      displayAddress();       //显示投保人地址详细内容
    }

*********************/

    fm.AppntAddressNo.value=tAddressNo;

    //查询并显示投保人地址详细内容
    getdetailaddress();

    //alert("zzz");
    //查询投保人告知信息
    //var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
   // var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    
    var mysql8= new SqlClass();
 	var sqlid8 = "BankContInputSql8";
	mysql8.setResourceName(sqlresourcename);
	mySql8.setSqlId(sqlid8);
	mysql8.addSubPara(tCustomerNo);
	mysql8.addSubPara(tContNo);

    arrResult = easyExecSql(mysql8.getString(),1,0);
    
    var mysql9= new SqlClass();
 	var sqlid9 = "BankContInputSql9";
	mysql9.setResourceName(sqlresourcename);
	mySql9.setSqlId(sqlid9);
	mysql9.addSubPara(tCustomerNo);
	mysql9.addSubPara(tContNo);
	
    arrResult1 = easyExecSql(mysql9.getString(),1,0);


	    try{fm.all('Income0').value= arrResult[0][0];}catch(ex){};
	    try{fm.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};

   // var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver in ('01','02') and impartcode<>'001'";
    
    var mysql10= new SqlClass();
 	var sqlid10 = "BankContInputSql10";
	mysql10.setResourceName(sqlresourcename);
	mySql10.setSqlId(sqlid10);
	mysql10.addSubPara(tCustomerNo);
	mysql10.addSubPara(tContNo);
    turnPage.strQueryResult = easyQueryVer3(mysql10.getString(), 1, 0, 1);

    //判断是否查询成功,成功则显示告知信息
    if (turnPage.strQueryResult) {
      //查询成功则拆分字符串，返回二维数组
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      //设置初始化过的MULTILINE对象
      turnPage.pageDisplayGrid = ImpartGrid;
      //保存SQL语句
      turnPage.strQuerySql = strSQL1;
      //设置查询起始位置
      turnPage.pageIndex = 0; 
      //在查询结果数组中取出符合页面显示大小设置的数组
      arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
      //调用MULTILINE对象显示查询结果
      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
    else{
      initImpartGrid();
    }

    //如果有分支
    if(loadFlag=="5"||loadFlag=="25"){
      //showCodeName();
    }
    //alert("tContNo=="+tContNo);

/***********************************个险取消多业务员故取消
    //查询已有多业务员信息
    var muliAgentSQL="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "+
                     "from lccont a,labranchgroup b,lacommisiondetail c,laagent d "+
                     "where 1=1 "+
                     "and trim(a.contno)='" + tContNo + "' " +
                     "and trim(b.agentgroup)=trim(c.agentgroup) " +
                     "and trim(c.agentcode)!=trim(a.agentcode) " +
                     "and trim(d.agentcode)=trim(c.agentcode) " +
                     "and trim(d.agentcode)!=trim(a.agentcode) " +
                     "and trim(c.grpcontno)=trim(a.contno) "
                     ;
    //alert("--muliAgentSQL="+muliAgentSQL);
    turnPage.strQueryResult = easyQueryVer3(muliAgentSQL, 1, 0, 1);

    //alert("--turnPage.strQueryResult=="+turnPage.strQueryResult);
    //判断是否查询成功,成功则显示告知信息
    if (turnPage.strQueryResult) {
      //查询成功则拆分字符串，返回二维数组
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      //设置初始化过的MULTILINE对象
      turnPage.pageDisplayGrid = MultiAgentGrid;
      //保存SQL语句
      turnPage.strQuerySql = muliAgentSQL;
      //设置查询起始位置
      turnPage.pageIndex = 0;
      //在查询结果数组中取出符合页面显示大小设置的数组
      arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
      //调用MULTILINE对象显示查询结果
      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
      //显示多业务员
      displayMultiAgent();
    }
    else{
      //初始化多业务员列表
      //alert("saafas");
      initMultiAgentGrid();
    }
********************************************/
/***********************************/
    //查询主业务员信息
//    arrResult=easyExecSql("select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " +
//                          "from lccont a,labranchgroup b,lacommisiondetail c,laagent d " +
//                          "where 1=1 " +
//                          "and trim(a.contno)='" + tContNo + "' "+
//                          "and trim(b.agentgroup)=trim(c.agentgroup) " +
//                          "and trim(c.agentcode)=trim(a.agentcode) " +
//                          "and trim(d.agentcode)=trim(a.agentcode) " +
//                          "and trim(c.grpcontno)=trim(a.contno)",1,0);
//
//    if(arrResult==null){
//      //alert("未得到主业务员信息");
//      //return;
//    }else{
//      displayMainAgent();       //显示主业务员的详细内容
//    }
/**************************************/
//alert();
 //////////////////////////////////////////
 //查询业务员信息
 //queryAgent(); 

//////////////////////////////////////////////
//查询业务员告知


   //var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
   
    var mysql11= new SqlClass();
 	var sqlid11 = "BankContInputSql11";
	mysql11.setResourceName(sqlresourcename);
	mySql11.setSqlId(sqlid11);
	mysql11.addSubPara(tCustomerNo);
	mysql11.addSubPara(tContNo);
   
   turnPage.queryModal(mysql11.getString(), AgentImpartGrid);


//////////////////////////////////////////////////




  }
  catch(ex){}

}

/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAccount()
{
	try{fm.all('AppntBankAccNo').value= arrResult[0][24]; }catch(ex){};
	try{fm.all('AppntBankCode').value= arrResult[0][23]; }catch(ex){};
	try{fm.all('AppntAccName').value= arrResult[0][25]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayCustomer()
{
	try{fm.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAddress()
{
try{fm.all('AppntAddressNo').value= arrResult[0][0];}catch(ex){};
try{fm.all('AppntPostalAddress').value= arrResult[0][1];}catch(ex){};
try{fm.all('AppntZipCode').value= arrResult[0][2];}catch(ex){};
try{fm.all('AppntPhone').value= arrResult[0][3];}catch(ex){};
try{fm.all('AppntMobile').value= arrResult[0][4];}catch(ex){};
try{fm.all('AppntEMail').value= arrResult[0][5];}catch(ex){};
//try{fm.all('GrpName').value= arrResult[0][6];}catch(ex){};
try{fm.all('AppntGrpPhone').value= arrResult[0][6];}catch(ex){};
try{fm.all('CompanyAddress').value= arrResult[0][7];}catch(ex){};
try{fm.all('AppntGrpZipCode').value= arrResult[0][8];}catch(ex){};
/*
        try{fm.all('AppntPostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('AppntZipCode').value= arrResult[0][3]; }catch(ex){};
	try{fm.all('AppntPhone').value= arrResult[0][4]; }catch(ex){};
	try{fm.all('AppntMobile').value= arrResult[0][14]; }catch(ex){};
	try{fm.all('AppntEMail').value= arrResult[0][16]; }catch(ex){};
	//try{fm.all('AppntGrpName').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('AppntGrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{fm.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{fm.all('AppntGrpZipCode').value= arrResult[0][11]; }catch(ex){};
*/
}

/*********************************************************************
 *  把查询返回的合同中被保人数据显示到页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayContAppnt()
{
  try{fm.all('AppntGrpContNo').value= arrResult[0][0];}catch(ex){};
  try{fm.all('AppntContNo').value= arrResult[0][1];}catch(ex){};
  try{fm.all('AppntPrtNo').value= arrResult[0][2];}catch(ex){};
  try{fm.all('AppntNo').value= arrResult[0][3];}catch(ex){};
  try{fm.all('AppntGrade').value= arrResult[0][4];}catch(ex){};
  try{fm.all('AppntName').value= arrResult[0][5];}catch(ex){};
  try{fm.all('AppntSex').value= arrResult[0][6];}catch(ex){};
  try{fm.all('AppntBirthday').value= arrResult[0][7];}catch(ex){};
  try{fm.all('AppntType').value= arrResult[0][8];}catch(ex){};
  try{fm.all('AppntAddressNo').value= arrResult[0][9]; }catch(ex){};
  try{fm.all('AppntIDType').value= arrResult[0][10]; }catch(ex){};
  try{fm.all('AppntIDNo').value= arrResult[0][11]; }catch(ex){};
  try{fm.all('AppntNativePlace').value= arrResult[0][12];}catch(ex){};
  try{fm.all('AppntNationality').value= arrResult[0][13];}catch(ex){};
  try{fm.all('AppntRgtAddress').value= arrResult[0][14];}catch(ex){};
  try{fm.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};
  try{fm.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};
  try{fm.all('AppntHealth').value= arrResult[0][17];}catch(ex){};
  try{fm.all('AppntStature').value= arrResult[0][18];}catch(ex){};
  try{fm.all('AppntAvoirdupois').value= arrResult[0][19];}catch(ex){};
  try{fm.all('AppntDegree').value= arrResult[0][20];}catch(ex){};
  try{fm.all('AppntCreditGrade').value= arrResult[0][21];}catch(ex){};
  try{fm.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};
  try{fm.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};
  try{fm.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};
  try{fm.all('AppntJoinCompanyDate').value= arrResult[0][25];}catch(ex){};
  try{fm.all('AppntStartWorkDate').value= arrResult[0][26];}catch(ex){};
  try{fm.all('AppntPosition').value= arrResult[0][27];}catch(ex){};
  try{fm.all('AppntSalary').value= arrResult[0][28]; }catch(ex){};
  try{fm.all('AppntOccupationType').value= arrResult[0][29];}catch(ex){};
  try{fm.all('AppntOccupationCode').value= arrResult[0][30];}catch(ex){};
  try{fm.all('AppntWorkType').value= arrResult[0][31]; }catch(ex){};
  try{fm.all('AppntPluralityType').value= arrResult[0][32];}catch(ex){};
  try{fm.all('AppntSmokeFlag').value= arrResult[0][33];}catch(ex){};
  try{fm.all('AppntOperator').value= arrResult[0][34];}catch(ex){};
  try{fm.all('AppntManageCom').value= arrResult[0][35];}catch(ex){};
  try{fm.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
  try{fm.all('AppntMakeTime').value= arrResult[0][37]; }catch(ex){};
  try{fm.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
  try{fm.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
}
/*********************************************************************
 *  把查询返回的投保人数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
	//alert("here in 928 col");
try{fm.all('AppntNo').value= arrResult[0][0]; }catch(ex){};
try{fm.all('AppntName').value= arrResult[0][1]; }catch(ex){};
try{fm.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
try{fm.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
try{fm.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
try{fm.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
try{fm.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
try{fm.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
try{fm.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
try{fm.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
try{fm.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
try{fm.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
try{fm.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
try{fm.all('AppntStature').value= arrResult[0][13];}catch(ex){};
try{fm.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
try{fm.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
try{fm.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
try{fm.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
try{fm.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
try{fm.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
try{fm.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
try{fm.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
try{fm.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
try{fm.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
try{fm.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
try{fm.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
try{fm.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
try{fm.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
try{fm.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
try{fm.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
try{fm.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
try{fm.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
try{fm.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
try{fm.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
try{fm.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{fm.all('AppntVIPValue').value= arrResult[0][35];}catch(ex){};
try{fm.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
try{fm.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
try{fm.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
try{fm.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
try{fm.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
try{fm.all('AppntGrpName').value= arrResult[0][41];}catch(ex){};
try{fm.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};


//顺便将投保人地址信息等进行初始化
try{fm.all('AppntPostalAddress').value= "";}catch(ex){}; 
try{fm.all('AppntPostalAddress').value= "";}catch(ex){};
try{fm.all('AppntZipCode').value= "";}catch(ex){};
try{fm.all('AppntPhone').value= "";}catch(ex){};
try{fm.all('AppntFax').value= "";}catch(ex){};
try{fm.all('AppntMobile').value= "";}catch(ex){};
try{fm.all('AppntEMail').value= "";}catch(ex){};
//try{fm.all('AppntGrpName').value= "";}catch(ex){};
try{fm.all('AppntHomeAddress').value= "";}catch(ex){};
try{fm.all('AppntHomeZipCode').value= "";}catch(ex){};
try{fm.all('AppntHomePhone').value= "";}catch(ex){};
try{fm.all('AppntHomeFax').value= "";}catch(ex){};
try{fm.all('AppntGrpPhone').value= "";}catch(ex){};
try{fm.all('CompanyAddress').value= "";}catch(ex){};
try{fm.all('AppntGrpZipCode').value= "";}catch(ex){};
try{fm.all('AppntGrpFax').value= "";}catch(ex){};
}
/**
 *投保单详细内容显示
 */
function displayLCCont() {
	  //alert("aaa");
    try { fm.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };
    try { fm.all('ContNo').value = arrResult[0][1]; } catch(ex) { };
    try { fm.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };
    try { fm.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };
    try { fm.all('ContType').value = arrResult[0][4]; } catch(ex) { };
    try { fm.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };
    try { fm.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };
    try { fm.all('PolType ').value = arrResult[0][7]; } catch(ex) { };
    try { fm.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };
    try { fm.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };
    try { fm.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };
    try { fm.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };
    try { fm.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { fm.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { }; 
    try { fm.all('AgentCode1 ').value = arrResult[0][14]; } catch(ex) { };
//    try { fm.all('AgentType').value = arrResult[0][15]; } catch(ex) { }; 
    try { fm.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };
    try { fm.all('Handler').value = arrResult[0][17]; } catch(ex) { };
    try { fm.all('Password').value = arrResult[0][18]; } catch(ex) { };
    try { fm.all('AppntNo').value = arrResult[0][19]; } catch(ex) { };
    try { fm.all('AppntName').value = arrResult[0][20]; } catch(ex) { };
    try { fm.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };
    try { fm.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };
    try { fm.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };
    try { fm.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };
    try { fm.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };
    try { fm.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };
    try { fm.all('InsuredSex').value = arrResult[0][27]; } catch(ex) { };
    try { fm.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };
    try { fm.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };
    try { fm.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };
    try { fm.all('PayIntv').value = arrResult[0][31]; } catch(ex) { };
    try { fm.all('SecPayMode').value = arrResult[0][32]; } catch(ex) { };
    try { fm.all('PayLocation').value = arrResult[0][33]; } catch(ex) { };
    try { fm.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };
    try { fm.all('OutPayFlag').value = arrResult[0][35]; } catch(ex) { };
    try { fm.all('GetPolMode').value = arrResult[0][36]; } catch(ex) { };
    try { fm.all('SignCom').value = arrResult[0][37]; } catch(ex) { };
    try { fm.all('SignDate').value = arrResult[0][38]; } catch(ex) { };
    try { fm.all('SignTime').value = arrResult[0][39]; } catch(ex) { };
    try { fm.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };
    try { fm.all('SecAppntBankCode').value = arrResult[0][41]; } catch(ex) { };
    try { fm.all('SecAppntBankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { fm.all('SecAppntAccName').value = arrResult[0][43]; } catch(ex) { };
    try { fm.all('PrintCount').value = arrResult[0][44]; } catch(ex) { };
    try { fm.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };
    try { fm.all('Lang').value = arrResult[0][46]; } catch(ex) { };
    try { fm.all('Currency').value = arrResult[0][47]; } catch(ex) { };
    try { fm.all('Remark').value = arrResult[0][48]; } catch(ex) { };
    try { fm.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };
    try { fm.all('Mult').value = arrResult[0][50]; } catch(ex) { };
    try { fm.all('Prem').value = arrResult[0][51]; } catch(ex) { };
    try { fm.all('Amnt').value = arrResult[0][52]; } catch(ex) { };
    try { fm.all('SumPrem').value = arrResult[0][53]; } catch(ex) { };
    try { fm.all('Dif').value = arrResult[0][54]; } catch(ex) { };
    try { fm.all('PaytoDate').value = arrResult[0][55]; } catch(ex) { };
    try { fm.all('FirstPayDate').value = arrResult[0][56]; } catch(ex) { };
    try { fm.all('CValiDate').value = arrResult[0][57]; } catch(ex) { };
    try { fm.all('InputOperator ').value = arrResult[0][58]; } catch(ex) { };
    try { fm.all('InputDate').value = arrResult[0][59]; } catch(ex) { };
    try { fm.all('InputTime').value = arrResult[0][60]; } catch(ex) { };
    try { fm.all('ApproveFlag').value = arrResult[0][61]; } catch(ex) { };
    try { fm.all('ApproveCode').value = arrResult[0][62]; } catch(ex) { };
    try { fm.all('ApproveDate').value = arrResult[0][63]; } catch(ex) { };
    try { fm.all('ApproveTime').value = arrResult[0][64]; } catch(ex) { };
    try { fm.all('UWFlag').value = arrResult[0][65]; } catch(ex) { };
    try { fm.all('UWOperator').value = arrResult[0][66]; } catch(ex) { };
    try { fm.all('UWDate').value = arrResult[0][67]; } catch(ex) { };
    try { fm.all('UWTime').value = arrResult[0][68]; } catch(ex) { };
    try { fm.all('AppFlag').value = arrResult[0][69]; } catch(ex) { };
    try { fm.all('PolAppntDate').value = arrResult[0][70]; } catch(ex) { };
    try { fm.all('GetPolDate').value = arrResult[0][71]; } catch(ex) { };
    try { fm.all('GetPolTime').value = arrResult[0][72]; } catch(ex) { };
    try { fm.all('CustomGetPolDate').value = arrResult[0][73]; } catch(ex) { };
    try { fm.all('State').value = arrResult[0][74]; } catch(ex) { };
    try { fm.all('Operator').value = arrResult[0][75]; } catch(ex) { };
    try { fm.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };
    try { fm.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };
    try { fm.all('ModifyDate').value = arrResult[0][78]; } catch(ex) { };
    try { fm.all('ModifyTime').value = arrResult[0][79]; } catch(ex) { };
    try { fm.all('SellType').value = arrResult[0][87]; } catch(ex) { };

    try { fm.all('AppntBankCode').value = arrResult[0][90]; } catch(ex) { };
    try { fm.all('AppntBankAccNo').value = arrResult[0][91]; } catch(ex) { };
    try { fm.all('AppntAccName').value = arrResult[0][92]; } catch(ex) { };
    try { fm.all('PayMode').value = arrResult[0][93]; } catch(ex) { };

}
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }                                                                      ;
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}



//投保人客户号查询按扭事件
function queryAppntNo() {

	if (fm.all("AppntNo").value == "" && loadFlag == "1")
	{
    showAppnt1();
  }
  else if (loadFlag != "1" && loadFlag != "2")
  {
     alert("只能在投保单录入时进行操作！");
  }
  else
  {
  
 // "select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + fm.all("AppntNo").value + "'"
  
    var mysql12= new SqlClass();
 	var sqlid12 = "BankContInputSql12";
	mysql12.setResourceName(sqlresourcename);
	mySql12.setSqlId(sqlid12);
	mysql12.addSubPara(fm.all("AppntNo").value);
	
    arrResult = easyExecSql(mysql12.getString(), 1, 0);
    if (arrResult == null) {
      alert("未查到投保人信息");
      displayAppnt(new Array());
      emptyUndefined();
    }
    else
    {
      displayAppnt(arrResult[0]);
    }
  }
}

function showAppnt1()

{
	//alert("here in 1115 row");
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" ,"",sFeatures);
	}
}

function queryAgent()
{
	//alert("fm.all('AgentCode').value==="+fm.all('AgentCode').value);
	if(fm.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
  if(fm.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //团险查询业务员branchtype = '2'
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(fm.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
   //	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);
   
    var mysql13= new SqlClass();
 	var sqlid13 = "BankContInputSql13";
	mysql13.setResourceName(sqlresourcename);
	mySql13.setSqlId(sqlid13);
	mysql13.addSubPara(cAgentCode);
	
    var arrResult = easyExecSql(mySql13.getString()); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      fm.AgentManageCom.value = arrResult[0][2];
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("编码为:["+fm.all('AgentCode').value+"]的业务员不存在，请确认!");
    }
	}
}

//问题件录入
function QuestInput2()
{ 
	cContNo = fm.ContNo.value;  //保单号码
	if(cContNo == "")
	{
		alert("尚无合同投保单号，请先保存!");
	}
	else
	{
		window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery1(arrQueryResult)
{
      //fm.all("Donextbutton1").style.display="";
      //fm.all("Donextbutton2").style.display="none";
      //fm.all("Donextbutton3").style.display="";
      //fm.all("butBack").style.display="none";
      //详细信息初始化
      detailInit(arrQueryResult);

}
//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      fm.AgentManageCom.value = arrResult[0][2];

  	showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}

//返回查询结果，赋给多业务员multline
function afterQuery3(arrResult)
{
  if(arrResult!=null)
  {
		fm.all( tField ).all( 'MultiAgentGrid1').value = arrResult[0][0];
		fm.all( tField ).all( 'MultiAgentGrid2').value = arrResult[0][3];
		fm.all( tField ).all( 'MultiAgentGrid3').value = arrResult[0][2];
		fm.all( tField ).all( 'MultiAgentGrid4').value = arrResult[0][8];
		fm.all( tField ).all( 'MultiAgentGrid6').value = arrResult[0][1];
		fm.all( tField ).all( 'MultiAgentGrid7').value = arrResult[0][6];
  }

}



function queryAgent2()
{
	if(fm.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
	if(fm.all('AgentCode').value != "" && fm.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
   
   var mysql14= new SqlClass();
 	var sqlid14 = "BankContInputSql14";
	mysql14.setResourceName(sqlresourcename);
	mySql14.setSqlId(sqlid14);
	mysql14.addSubPara(cAgentCode);
	mysql14.addSubPara(fm.all('ManageCom').value);
   
    var arrResult = easyExecSql(mysql14.getString());
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+fm.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
}
function getdetail()
{
//alert("fm.AppntNo.value=="+fm.AppntNo.value);
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"' and customerno='"+fm.AppntNo.value+"'";

var mysql15= new SqlClass();
 	var sqlid15 = "BankContInputSql15";
	mysql15.setResourceName(sqlresourcename);
	mySql15.setSqlId(sqlid15);
	mysql15.addSubPara(cAgentCode);
	mysql15.addSubPara(fm.all('ManageCom').value);


var arrResult = easyExecSql(mysql15.getString());
if (arrResult != null) {
      fm.AppntBankCode.value = arrResult[0][0];
      fm.AppntAccName.value = arrResult[0][1];
    }
else{
     //fm.AppntBankCode.value = '';
     //fm.AppntAccName.value = '';
}
}
function getdetailwork()
{
  //alert("fm.AppntOccupationCode.value=="+fm.AppntOccupationCode.value);
  //var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
  
  var mysql16= new SqlClass();
 	var sqlid16 = "BankContInputSql16";
	mysql16.setResourceName(sqlresourcename);
	mySql16.setSqlId(sqlid16);
	mysql16.addSubPara(fm.AppntOccupationCode.value);
  
  var arrResult = easyExecSql(mysql16.getString());
  if (arrResult != null) {
    fm.AppntOccupationType.value = arrResult[0][0];

  }
  else{
    fm.AppntOccupationType.value = '';
  }
}

/********************
**
** 查询投保人地址信息
**
*********************/
function getdetailaddress(){
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
       
        var mysql17= new SqlClass();
 	var sqlid17 = "BankContInputSql17";
	mysql17.setResourceName(sqlresourcename);
	mySql17.setSqlId(sqlid17);
	mysql17.addSubPara(fm.AppntAddressNo.value);
	mysql17.addSubPara(fm.AppntNo.value);
       
       
        arrResult=easyExecSql(mysql17.getString());
   //alert("arrResult[0][1]=="+arrResult[0][1]);
   try{fm.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{fm.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{fm.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{fm.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{fm.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{fm.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{fm.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{fm.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{fm.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{fm.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   try{fm.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   try{fm.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   try{fm.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};
   try{fm.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{fm.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
   try{fm.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{fm.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
   try{fm.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   try{fm.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   try{fm.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   try{fm.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   try{fm.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{fm.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
   try{fm.all('AppntCity').value= arrResult[0][29];}catch(ex){};
   try{fm.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if(fm.AppntAddressNo.value=="")
    {
    	fm.AppntPostalAddress.value="";
    	fm.AddressNoName.value="";
    	fm.AppntProvinceName.value="";
    	fm.AppntProvince.value="";
    	fm.AppntCityName.value="";
    	fm.AppntCity.value="";
    	fm.AppntDistrictName.value="";
    	fm.AppntDistrict.value="";
    	fm.AppntZipCode.value="";
    	fm.AppntFax.value="";
    	fm.AppntEMail.value="";
    	fm.AppntDistrict.value="";
    //	fm.AppntGrpName.value="";
    	fm.AppntHomePhone.value="";
    	fm.AppntMobile.value="";
    	fm.AppntGrpPhone.value="";
    	}
    //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
    //alert("strsql :" + strsql);
    
    var mysql18= new SqlClass();
 	var sqlid18 = "BankContInputSql18";
	mysql18.setResourceName(sqlresourcename);
	mySql18.setSqlId(sqlid18);
	mysql18.addSubPara(fm.AppntNo.value);
    
    turnPage.strQueryResult  = easyQueryVer3(mysql18.getString(), 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    fm.all("AppntAddressNo").CodeData=tCodeData;
}
function afterCodeSelect( cCodeName, Field )
{
 //alert("afdasdf");
 if(cCodeName=="GetAddressNo"){
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
      
    var mysql19= new SqlClass();
 	var sqlid19 = "BankContInputSql19";
	mysql19.setResourceName(sqlresourcename);
	mySql19.setSqlId(sqlid19);
	mysql19.addSubPara(fm.AppntAddressNo.value);
	mysql19.addSubPara(fm.AppntNo.value);
      
        arrResult=easyExecSql(mysql19.getString());
   try{fm.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{fm.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{fm.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{fm.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{fm.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{fm.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{fm.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{fm.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{fm.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{fm.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   //try{fm.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   try{fm.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   try{fm.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};
   try{fm.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{fm.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
   try{fm.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{fm.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
   try{fm.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   try{fm.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   try{fm.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   try{fm.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   try{fm.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{fm.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
   try{fm.all('AppntCity').value= arrResult[0][29];}catch(ex){};
   try{fm.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
   showOneCodeName('province','AppntProvince','AppntProvinceName');
 	 showOneCodeName('city','AppntCity','AppntCityName');
	 showOneCodeName('district','AppntDistrict','AppntDistrictName');
   return;
 }
 if(cCodeName=="paymode"){
   	if(fm.all('PayMode').value=="4"){
   	  //divLCAccount1.style.display="";
    }
    else
    {
   	  //divLCAccount1.style.display="none";
      //alert("accountImg===");
    }
 }
 
 if(cCodeName=="BankAgentCode")
  {     
      var tAgentCode=fm.all('AgentCode').value;
      //alert(tAgentCode); 
      //var sqlstr="select name,agentgroup from labranchgroup where agentgroup=(select agentgroup from laagent where agentcode='" + tAgentCode+"')" ;
      
    var mysql20= new SqlClass();
 	var sqlid20 = "BankContInputSql20";
	mysql20.setResourceName(sqlresourcename);
	mySql20.setSqlId(sqlid20);
	mysql20.addSubPara(tAgentCode);
      
      arrResult = easyExecSql(mysql20.getString(),1,0);
      if(arrResult==null)
        {
				  alert("专管员信息查询出错");
				  return false;
        }else{
           
           try{fm.all('BranchAttr').value= arrResult[0][0];}catch(ex){};
           try{fm.all('AgentGroup').value= arrResult[0][1];}catch(ex){};
	           
				}

 	} 
 
 //alert("aaa");
 afterCodeSelect2( cCodeName, Field );
}

/*********************************************************************
 *  初始化工作流MissionID
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function initMissionID()
{
	if(tMissionID == "null" || tSubMissionID == "null")
	{
		//alert("tMissionID="+tMissionID);
		//alert("tSubMissionID="+tSubMissionID);
	  tMissionID = mSwitch.getVar('MissionID');
	  tSubMissionID = mSwitch.getVar('SubMissionID');
	}
	else
	{
	  mSwitch.deleteVar("MissionID");
	  mSwitch.deleteVar("SubMissionID");
	  mSwitch.addVar("MissionID", "", tMissionID);
	  mSwitch.addVar("SubMissionID", "", tSubMissionID);
	  mSwitch.updateVar("MissionID", "", tMissionID);
	  mSwitch.updateVar("SubMissionID", "", tSubMissionID);
	}
}
function FillPostalAddress()
{
	 if(fm.CheckPostalAddress.value=="1")
	 {
	 	fm.all('AppntPostalAddress').value=fm.all('CompanyAddress').value;
                fm.all('AppntZipCode').value=fm.all('AppntGrpZipCode').value;
                fm.all('AppntPhone').value= fm.all('AppntGrpPhone').value;
                fm.all('AppntFax').value= fm.all('AppntGrpFax').value;

	 }
	 else if(fm.CheckPostalAddress.value=="2")
	 {
	 	fm.all('AppntPostalAddress').value=fm.all('AppntHomeAddress').value;
                fm.all('AppntZipCode').value=fm.all('AppntHomeZipCode').value;
                fm.all('AppntPhone').value= fm.all('AppntHomePhone').value;
                fm.all('AppntFax').value= fm.all('AppntHomeFax').value;
	 }
	 else if(fm.CheckPostalAddress.value=="3")
	 {
	 	fm.all('AppntPostalAddress').value="";
                fm.all('AppntZipCode').value="";
                fm.all('AppntPhone').value= "";
                fm.all('AppntFax').value= "";
	 }
}
function AppntChk()
{
	var Sex=fm.AppntSex.value;
	//var i=Sex.indexOf("-");
	//Sex=Sex.substring(0,i);
	/*
  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
             + " union select * from ldperson where IDType = '"+fm.AppntIDType.value
             + "' and IDType is not null and IDNo = '"+fm.AppntIDNo.value
             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
     */  
    var mysql21= new SqlClass();
 	var sqlid21 = "BankContInputSql21";
	mysql21.setResourceName(sqlresourcename);
	mySql21.setSqlId(sqlid21);
	mysql21.addSubPara(fm.AppntName.value);
	mysql21.addSubPara(Sex);
	mysql21.addSubPara(fm.AppntBirthday.value);
	mysql21.addSubPara(fm.AppntNo.value);
	mysql21.addSubPara(fm.AppntIDType.value);
	mysql21.addSubPara(fm.AppntIDNo.value);
	mysql21.addSubPara(fm.AppntNo.value);
       
        arrResult = easyExecSql(mysql21.getString(),1,0);
        if(arrResult==null)
        {
				  alert("该没有与该投保人相似的客户,无需校验");
				  return false;
        }else{

					window.open("../uwgrp/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag,"window1",sFeatures);
				}
}

//在初始化body时自动效验投保人信息，
//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的被保人信息。
function AppntChkNew(){
  //alert("aa");
  var Sex=fm.AppntSex.value;
  /*
  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
             + " union select * from ldperson where IDType = '"+fm.AppntIDType.value
             + "' and IDType is not null and IDNo = '"+fm.AppntIDNo.value
             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
     */        
    var mysql58= new SqlClass();
 	var sqlid58 = "BankContInputSql58";
	mysql58.setResourceName(sqlresourcename);
	mySql58.setSqlId(sqlid58);
	mysql58.addSubPara(fm.AppntName.value);
	mysql58.addSubPara(Sex);
	mysql58.addSubPara(fm.AppntBirthday.value);
	mysql58.addSubPara(fm.AppntNo.value);
	mysql58.addSubPara(fm.AppntIDType.value);
	mysql58.addSubPara(fm.AppntIDNo.value);
	mysql58.addSubPara(fm.AppntNo.value);
       
    arrResult = easyExecSql(mysql58.getString(),1,0);
  if(arrResult==null)
  {
  	//disabled"投保人效验"按钮   //判断是否存在重复被保人？
		fm.AppntChkButton.disabled = true;
		fm.AppntChkButton2.disabled = true;

  }
  else{
  	fm.AppntChkButton.disabled = "";
  	fm.AppntChkButton2.disabled = "";
	}
}

function checkidtype()
{
	//alert("haha!");
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.AppntIDNo.value="";
  }
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(fm.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			theFirstValue="";
			theSecondValue="";
			//fm.all('AppntIDNo').focus();
			return;
		}
		else
		{
			fm.all('AppntBirthday').value=tBirthday;
			fm.all('AppntSex').value=tSex;
		}
	}
}
function getCompanyCode()
{
    var strsql = "";
    var tCodeData = "";
    //strsql = "select CustomerNo,GrpName from LDgrp ";
    
    var mysql22= new SqlClass();
 	var sqlid22 = "BankContInputSql22";
	mysql22.setResourceName(sqlresourcename);
	mySql22.setSqlId(sqlid22);
	mysql22.addSubPara("1");
	
    fm.all("AppntGrpNo").CodeData=tCodeData+easyQueryVer3(mysql22.getString(), 1, 0, 1);
}
function haveMultiAgent(){
	//alert("aa＝＝"+fm.all("multiagentflag").checked);
  if(fm.all("multiagentflag").checked){
  	DivMultiAgent.style.display="";
  }
  else{
    DivMultiAgent.style.display="none";
  }

}

//Muline 表单发佣分配表 parm1
function queryAgentGrid(Field)
{
	//alert("Field=="+Field);
	tField=Field;
	if(fm.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
	if(fm.all(Field).all('MultiAgentGrid1').value==""){
	//团险下查询业务员branchtype='2' modify by liuqh 2008-12-02
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}

	if(fm.all( Field ).all('MultiAgentGrid1').value != "")	 {
	var cAgentCode = fm.all( Field ).all('MultiAgentGrid1').value;  //保单号码
	//var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
	
	var mysql23= new SqlClass();
 	var sqlid23 = "BankContInputSql23";
	mysql23.setResourceName(sqlresourcename);
	mySql23.setSqlId(sqlid23);
	mysql23.addSubPara(cAgentCode);
	
    var arrResult = easyExecSql(mysql23.getString());
       //alert(arrResult);
    if (arrResult == null) {
      alert("编码为:["+fm.all( Field ).all('MultiAgentGrid1').value+"]的代理人不存在，请确认!");
    }
  }
}


//初始化主业务员信息
function displayMainAgent(){
  fm.all("BranchAttr").value = arrResult[0][3];
  fm.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent(){
   fm.all('multiagentflag').checked="true";
   haveMultiAgent();
}

//进行两次输入的校验
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("请再次录入！");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("两次录入结果不符，请重新录入！");
	        theFirstValue="";
	        theSecondValue="";
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	    }
      else{
      	theFirstValue = aObject.value;
      	if(theFirstValue==""){
      		theSecondValue="";
      	  alert("请录入内容！");
      	  return;
      	}
      	aObject.value="";
      	aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
	  //alert("theFirstValue="+theFirstValue);
	  if(theFirstValue!=""){
    	theSecondValue = aObject.value;
	    if(theSecondValue==""){
	    	alert("请再次录入！");
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	    if(theSecondValue==theFirstValue){
	      aObject.value = theSecondValue;
	      theSecondValue="";
	      theFirstValue="";
	      return;
	    }
	    else{
	      alert("两次录入结果不符，请重新录入！");
	      theFirstValue="";
	      theSecondValue="";
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	  }
    else{
    	theFirstValue = aObject.value;
     	theSecondValue="";
    	if(theFirstValue==""){
      	//alert("aa");
    	  return;
    	}
    	aObject.value="";
    	aObject.focus();
      return;
    }
  }
}


//校验投保日期
function checkapplydate(){
  if(fm.PolAppntDate.value.length==8){
  if(fm.PolAppntDate.value.indexOf('-')==-1){ 
  	var Year =     fm.PolAppntDate.value.substring(0,4);
  	var Month =    fm.PolAppntDate.value.substring(4,6);
  	var Day =      fm.PolAppntDate.value.substring(6,8);
  	fm.PolAppntDate.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保日期有误!");
   	   fm.PolAppntDate.value = "";  
   	   return;
  	  }
  }
}

else {var Year =     fm.PolAppntDate.value.substring(0,4);
	    var Month =    fm.PolAppntDate.value.substring(5,7);
	    var Day =      fm.PolAppntDate.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保日期有误!");
   	   fm.PolAppntDate.value = "";
   	   return;
  	     }
  }

}

//校验投保人出生日期
function checkappntbirthday(){
  if(fm.AppntBirthday.value.length==8){
  if(fm.AppntBirthday.value.indexOf('-')==-1){
  	var Year =     fm.AppntBirthday.value.substring(0,4);
  	var Month =    fm.AppntBirthday.value.substring(4,6);
  	var Day =      fm.AppntBirthday.value.substring(6,8);
  	fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保人出生日期有误!");
   	   fm.AppntBirthday.value = "";
   	   return;
  	  }
  }
}

else {var Year =     fm.AppntBirthday.value.substring(0,4);
	    var Month =    fm.AppntBirthday.value.substring(5,7);
	    var Day =      fm.AppntBirthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的投保人出生日期有误!");
   	   fm.AppntBirthday.value = "";
   	   return;
  	     }
  }
}


//校验被保人出生日期
function checkinsuredbirthday(){
	if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的被保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}

else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("您输入的被保人出生日期有误!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}

//通过出生日期计算投保人年龄
function getAge(){
	//alert("fm.AppntBirthday.value=="+fm.AppntBirthday.value);
  if(fm.AppntBirthday.value==""){
  	return;
  }
  //alert("fm.AppntBirthday.value.indexOf('-')=="+fm.AppntBirthday.value.indexOf('-'));
  if(fm.AppntBirthday.value.indexOf('-')==-1){
  	var Year =     fm.AppntBirthday.value.substring(0,4);
  	var Month =    fm.AppntBirthday.value.substring(4,6);
  	var Day =      fm.AppntBirthday.value.substring(6,8);
  	fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
  	if(calAge(fm.AppntBirthday.value)<0)
  	{
      alert("出生日期只能为当前日期以前");
      return;
    }
  	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    return;
  }
  if(calAge(fm.AppntBirthday.value)<0)
  	{
      alert("出生日期只能为当前日期以前!");
      return;
    }
  fm.AppntAge.value=calAge(fm.AppntBirthday.value);
  return;
}

//通过出生日期计算被保人年龄
function getAge2(){
	//alert("fm.Birthday.value=="+fm.Birthday.value);
  if(fm.Birthday.value==""){
  	return;
  }
  //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    return;
  }
  fm.InsuredAppAge.value=calAge(fm.Birthday.value);
  return;
}



//录入校验方法
//传入参数：verifyOrder校验的顺序
//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInputNB(verifyOrder)  
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志
	//遍历所有FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//元素校验属性verify不为NULL
			if (window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "" && window.document.forms[formsNum].elements[elementsNum].verifyorder == verifyOrder)
			{
				//进行校验verifyElement
				if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].verify, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].name+"."+window.document.forms[formsNum].elements[elementsNum].name))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}
	return passFlag;
}

//***********************************************************


function focuswrap()
{
	myonfocus(showInfo1);
}

//提交，保存按钮对应操作
//function submitForm()
//{
//}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
	//alert("here 2!");
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  if (FlagStr=="Succ" && mWFlag == 0)
  {
  //	if(confirm("是否继续录入其他客户？"))
  //	{
	//    if(fm.InsuredSequencename.value=="被保险人资料") 
	//    {
	//      //emptyFormElements();
	//      param="122";
	//      fm.pagename.value="122";
	//      fm.SequenceNo.value="2";
  //      fm.InsuredSequencename.value="第二被保险人资料";
  //      if (scantype== "scan")
  //      {
  //      setFocus();
  //      }
  //      noneedhome();
  //      return false;
	//    }
	//    if(fm.InsuredSequencename.value=="第二被保险人资料")
	//    {
	//      //emptyFormElements();
	//      param="123";
	//      fm.pagename.value="123";
	//      fm.SequenceNo.value="3";
  //      fm.InsuredSequencename.value="第三被保险人资料";
  //      if (scantype== "scan")
  //      {
  //      setFocus();
  //      }
  //      noneedhome();
  //      return false;
	//    }
	//    if(fm.InsuredSequencename.value=="第三被保险人资料")
	//    {
	//      //emptyFormElements();
	//      param="124";
	//      fm.pagename.value="124";
	//      fm.SequenceNo.value="";
  //      fm.InsuredSequencename.value="第四被保险人资料";
  //      if (scantype== "scan")
  //      {
  //        setFocus();
  //      }
  //      return false;
	//    }
  //  }
  }

  if(InsuredGrid.mulLineCount>0){
    fm.all('spanInsuredGrid0').all('InsuredGridSel').checked='true'
  }

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit3( FlagStr, content )
{
	//alert("here 2!");
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  if (FlagStr=="Succ" && mWFlag == 0)
  {
/****************
  	if(confirm("是否继续录入其他客户？"))
  	{
	    if(fm.InsuredSequencename.value=="第一被保险人资料")
	    {
	      //emptyFormElements();
	      param="122";
	      fm.pagename.value="122";
	      fm.SequenceNo.value="2";
        fm.InsuredSequencename.value="第二被保险人资料";
        if (scantype== "scan")
        {
        setFocus();
        }
        noneedhome();
        return false;
	    }
	    if(fm.InsuredSequencename.value=="第二被保险人资料")
	    {
	      //emptyFormElements();
	      param="123";
	      fm.pagename.value="123";
	      fm.SequenceNo.value="3";
        fm.InsuredSequencename.value="第三被保险人资料";
        if (scantype== "scan")
        {
        setFocus();
        }
        noneedhome();
        return false;
	    }
	    if(fm.InsuredSequencename.value=="第三被保险人资料")
	    {
	      //emptyFormElements();
	      param="124";
	      fm.pagename.value="124";
	      fm.SequenceNo.value="";
        fm.InsuredSequencename.value="第四被保险人资料";
        if (scantype== "scan")
        {
          setFocus();
        }
        return false;
	    }
    }
***************************/
  }
  //默认选择第一被保人
  fm.all('spanInsuredGrid0').all('InsuredGridSel').checked="true";
  getInsuredDetail("spanInsuredGrid0","");
  mSwitch.deleteVar('PolNo');
  mSwitch.deleteVar('SelPolNo');

}



//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,50,82,*";
    }
 	else
 	{
        parent.fraMain.rows = "0,0,0,82,*";
 	}
}

//问题件录入
function QuestInput()
{

    cContNo = fm.ContNo.value;  //保单号码
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
        if(mSwitch.getVar( "ProposalGrpContNo" )=="")
        {
            alert("尚无集体合同投保单号，请先保存!");
        }
		else
		{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if(cContNo == "")
        {
            alert("尚无合同投保单号，请先保存!");
        }
        else
        {
            window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
        }
    }
}
//问题件查询
function QuestQuery()
{
  cContNo = fm.all("ContNo").value;  //保单号码
  if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
  {
    if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
    {
     	alert("请先选择一个团体主险投保单!");
     	return ;
    }
    else
    {
      window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
    }
  }
  else
  {
    if(cContNo == "")
    {
	    alert("尚无合同投保单号，请先保存!");
    }
	  else
	  {
      window.open("../uwgrp/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
    }
  }
}


/*********************************************************************
 *  进入险种信息录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoRiskInfo()
{
  
  //把合同信息和被保人信息放入内存
  mSwitch = parent.VD.gVSwitch;  //桢容错
  putCont();   //注意该函数在BankContInput.js中

	if(fm.InsuredNo.value==""||fm.ContNo.value==""||InsuredGrid.mulLineCount=="0")
	{
		alert("请先添加，选择被保人");
		return false;
	}
	
	//mSwitch =parent.VD.gVSwitch;
	delInsuredVar();
	addInsuredVar();

  try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //选择险种单进入险种界面带出已保存的信息
 
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("请先选择被保险人险种信息！");
		return;
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){}
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',fm.all('PolAppntDate').value);}catch(ex){}
   
//  alert("LoadFlag="+LoadFlag);
//  alert("ContType="+ContType); 
//  alert("scantype="+scantype);
//  
//  alert("MissionID="+MissionID);
//  alert("SubMissionID="+SubMissionID); 
//  alert("BQFlag="+BQFlag);
//  
//  alert("EdorType="+EdorType);
//  alert("checktype="+checktype); 
//  alert("ScanFlag="+ScanFlag); 
   // alert("ActivityID="+ActivityID);
   // alert("NoType="+NoType);    
  parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1";
}

/*********************************************************************
 *  删除缓存中被保险人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delInsuredVar()
{
    try{mSwitch.deleteVar('ContNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredNo');}catch(ex){};
    try{mSwitch.deleteVar('PrtNo');}catch(ex){};
    try{mSwitch.deleteVar('GrpContNo');}catch(ex){};
 //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
 //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try{mSwitch.deleteVar('ExecuteCom');}catch(ex){};
    try{mSwitch.deleteVar('FamilyType');}catch(ex){};
    try{mSwitch.deleteVar('RelationToMainInsure');}catch(ex){};
    try{mSwitch.deleteVar('RelationToAppnt');}catch(ex){};
    try{mSwitch.deleteVar('AddressNo');}catch(ex){};
    try{mSwitch.deleteVar('SequenceNo');}catch(ex){};
    try{mSwitch.deleteVar('Name');}catch(ex){};
    try{mSwitch.deleteVar('Sex');}catch(ex){};
    try{mSwitch.deleteVar('Birthday');}catch(ex){};
    try{mSwitch.deleteVar('IDType');}catch(ex){};
    try{mSwitch.deleteVar('IDNo');}catch(ex){};
    try{mSwitch.deleteVar('RgtAddress');}catch(ex){};
    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try{mSwitch.deleteVar('Health');}catch(ex){};
    try{mSwitch.deleteVar('Stature');}catch(ex){};
    try{mSwitch.deleteVar('Avoirdupois');}catch(ex){};
    try{mSwitch.deleteVar('Degree');}catch(ex){};
    try{mSwitch.deleteVar('CreditGrade');}catch(ex){};
    try{mSwitch.deleteVar('BankCode');}catch(ex){};
    try{mSwitch.deleteVar('BankAccNo');}catch(ex){};
    try{mSwitch.deleteVar('AccName');}catch(ex){};
    try{mSwitch.deleteVar('JoinCompanyDate');}catch(ex){};
    try{mSwitch.deleteVar('StartWorkDate');}catch(ex){};
    try{mSwitch.deleteVar('Position');}catch(ex){};
    try{mSwitch.deleteVar('Salary');}catch(ex){};
    try{mSwitch.deleteVar('OccupationType');}catch(ex){};
    try{mSwitch.deleteVar('OccupationCode');}catch(ex){};
    try{mSwitch.deleteVar('WorkType');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){};
}

/*********************************************************************
 *  将被保险人信息加入到缓存中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addInsuredVar()
{
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
    //alert("ContNo:"+fm.ContNo.value);
    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
 //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
 //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('ExecuteCom','',fm.ExecuteCom.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('RelationToMainInsure','',fm.RelationToMainInsure.value);}catch(ex){};
    try{mSwitch.addVar('RelationToAppnt','',fm.RelationToAppnt.value);}catch(ex){};

    try{mSwitch.addVar('AddressNo','',fm.AppntAddressNo.value);}catch(ex){};
    try{mSwitch.addVar('SequenceNo','',fm.SequenceNo.value);}catch(ex){};
    try{mSwitch.addVar('Name','',fm.Name.value);}catch(ex){};
    try{mSwitch.addVar('Sex','',fm.Sex.value);}catch(ex){};
    try{mSwitch.addVar('Birthday','',fm.Birthday.value);}catch(ex){};
    try{mSwitch.addVar('IDType','',fm.IDType.value);}catch(ex){};
    try{mSwitch.addVar('IDNo','',fm.IDNo.value);}catch(ex){};
    try{mSwitch.addVar('RgtAddress','',fm.RgtAddress.value);}catch(ex){};
    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try{mSwitch.addVar('Health','',fm.Health.value);}catch(ex){};
    try{mSwitch.addVar('Stature','',fm.Stature.value);}catch(ex){};
    try{mSwitch.addVar('Avoirdupois','',fm.Avoirdupois.value);}catch(ex){};
    try{mSwitch.addVar('Degree','',fm.Degree.value);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',fm.CreditGrade.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',fm.JoinCompanyDate.value);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',fm.StartWorkDate.value);}catch(ex){};
    try{mSwitch.addVar('Position','',fm.Position.value);}catch(ex){};
    try{mSwitch.addVar('Salary','',fm.Salary.value);}catch(ex){};
    try{mSwitch.addVar('OccupationType','',fm.OccupationType.value);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',fm.OccupationCode.value);}catch(ex){};
    try{mSwitch.addVar('WorkType','',fm.WorkType.value);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};

}

/*********************************************************************
 *  添加被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addRecord()
{ 
	fm.SequenceNo.value="1";
	fm.RelationToMainInsured.value="00";
	//判断是否已经添加过投保人，没有的话不允许添加被保人
	if(!checkAppnt()){
	  alert("请先添加合同信息及投保人信息！");
	  fm.AppntName.focus();
	  return;
	}

  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==1){

	 if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	  {
	   alert("与投保人关系只能选择本人！");
	   fm.RelationToAppnt.value="00";
	   fm.RelationToAppntName.value="本人";
	   return ;
	  }
	  var tPrtNo=fm.all("PrtNo").value;

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    var mysql24= new SqlClass();
 	var sqlid24 = "BankContInputSql24";
	mysql24.setResourceName(sqlresourcename);
	mySql24.setSqlId(sqlid24);
	mysql24.addSubPara(tPrtNo);


    arrResult=easyExecSql(mysql24.getString(),1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}

  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==3){

	  var tPrtNo=fm.all("PrtNo").value;

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    var mysql59= new SqlClass();
 	var sqlid59 = "BankContInputSql59";
	mysql59.setResourceName(sqlresourcename);
	mySql59.setSqlId(sqlid59);
	mysql59.addSubPara(tPrtNo);

    arrResult=easyExecSql(mysql59.getString(),1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==5){

	  var tPrtNo=fm.all("PrtNo").value;

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

      var mysql60= new SqlClass();
 	var sqlid60 = "BankContInputSql60";
	mysql60.setResourceName(sqlresourcename);
	mySql60.setSqlId(sqlid60);
	mysql60.addSubPara(tPrtNo);

    arrResult=easyExecSql(mysql60.getString(),1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==6){

	  var tPrtNo=fm.all("PrtNo").value;

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

     var mysql61= new SqlClass();
 	var sqlid61 = "BankContInputSql61";
	mysql61.setResourceName(sqlresourcename);
	mySql61.setSqlId(sqlid61);
	mysql61.addSubPara(tPrtNo);

    arrResult=easyExecSql(mysql61.getString(),1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


/***************************************************
  if(LoadFlag==1)
  {
    if(fm.Marriage.value=="")
    {
      alert("请填写婚姻状况！");
    	return false;
    }
    if(fm.RelationToMainInsured.value=="")
    {
      alert("请填写与主被保险人关系！");
    	return false;
    }
    if(fm.RelationToAppnt.value=="")
    {
      alert("请填写与投保人关系！！");
    	return false;
    }
  }

*******************************************************/

  if (fm.all('PolTypeFlag').value==0)
  {
    if( verifyInputNB('2') == false ) return false;
  }

  if(fm.all('IDType').value=="0")
  {
    var strChkIdNo = chkIdNo(trim(fm.all('IDNo').value),trim(fm.all('Birthday').value),trim(fm.all('Sex').value));
    if (strChkIdNo != "")
    {
      alert(strChkIdNo);
	    return false;
    }
  }

  if(!checkself()) return false;

  if(!checkrelation()) return false;

  if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false) return false;

  //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
	ImpartGrid.delBlankLine();
	//  ImpartDetailGrid.delBlankLine();
	//alert("fm.AddressNo.value=="+fm.InsuredAddressNo.value);
  if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
  {
    alert("被保险人客户号为空，不能有地址号码");
    return false;
  }

  //hanlin 20050504
  fm.action="BankContInsuredSave.jsp";
  //end hanlin
  fm.all('ContType').value=ContType;
  fm.all( 'BQFlag' ).value = BQFlag;
  fm.all('fmAction').value="INSERT||CONTINSURED";
  fm.submit();
}

/*********************************************************************
 *  修改被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function modifyRecord()
{
/*
		    var tPrtNo=fm.all("PrtNo").value;
      arrResult=easyExecSql("select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'",1,0);
      for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[0][sequencenocout]){
	  	alert("已经存在该客户内部号");
	  	fm.SequenceNo.focus();
	  	return false;
	  	}
	    }
*/
	  //alert("here!");
	  if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	  {
	   alert("与投保人关系只能选择本人！");
	   fm.RelationToAppnt.value="00";
	   fm.RelationToAppntName.value="本人";
	   return ;
	  }

    if (fm.all('PolTypeFlag').value==0)
    {
        if( verifyInputNB('2') == false ) return false;
    }
    if(!checkself())
        return false;
    if (fm.Name.value=='')
    {
        alert("请选中需要修改的客户！")
        return false;
    }
    //alert("SelNo:"+InsuredGrid.getSelNo());
    if (InsuredGrid.mulLineCount==0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
    if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)
    {
      return false;
    }
   // if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)
   // {
  //    return false;
	//  }
	  ImpartGrid.delBlankLine();
	  //ImpartDetailGrid.delBlankLine();

    fm.all('ContType').value=ContType;
    fm.all('fmAction').value="UPDATE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    fm.submit();
}
/*********************************************************************
 *  删除被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function deleteRecord()
{
    if (fm.InsuredNo.value=='')
    {
        alert("请选中需要删除的客户！")
        return false;
    }
    if (InsuredGrid.mulLineCount==0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
     if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
    fm.all('ContType').value=ContType;
    fm.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    fm.submit();
}
/*********************************************************************
 *  返回上一页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnparent()
{
  	//alert("LoadFlag=="+LoadFlag);
  	var backstr=fm.all("ContNo").value;

	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(fm.all("PrtNo").value);
  	location.href="../cardgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + fm.all("PrtNo").value;
  }
  if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(fm.all("PrtNo").value);
    location.href="../cardgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + fm.all("PrtNo").value;
  }

	if(LoadFlag=="2")
	{
    location.href="../cardgrp/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + fm.all("GrpContNo").value+"&scantype="+scantype;
  }
	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+fm.all("PrtNo").value;
	    return;
	}
	else if (LoadFlag=="7")
	{
    location.href="../bq/GEdorTypeNI.jsp?BQFlag="+BQFlag;
	  return;
	}
	else if(LoadFlag=="4"||LoadFlag=="16"||LoadFlag=="13"||LoadFlag=="14"||LoadFlag=="23")
	{
	  if(Auditing=="1")
	  {
	  	top.close();
	  }
	  else
	  {
	    mSwitch.deleteVar("PolNo");
      parent.fraInterface.window.location = "../cardgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype;
	  }
	}
	else if (LoadFlag=="99")
	{
	  location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag+"&scantype="+scantype;
	  return;
	}
/*    else
    {
        location.href="ContInput.jsp?LoadFlag="+ LoadFlag;
	}  针对	各种情况的不同暂不支持else方式
*/
}
/*********************************************************************
 *  进入险种计划界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../cardgrp/GroupRiskPlan.jsp","",sFeatures);
}
/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect2( cCodeName, Field )
{
    try
    {
        //如果是无名单
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value!='0')
            {
                fm.all('InsuredPeoples').readOnly=false;
                fm.all('InsuredAppAge').readOnly=false;
            }
            else
            {
                fm.all('InsuredPeoples').readOnly=true;
                fm.all('InsuredAppAge').readOnly=true;
            }
        }
        if( cCodeName == "ImpartCode")
        {

        }
        if( cCodeName == "SequenceNo")
        {
	        if(Field.value=="1"&&fm.SamePersonFlag.checked==false)
	        {
	   	      //emptyInsured();
		        param="121";
		        fm.pagename.value="121";
            fm.InsuredSequencename.value="被保险人资料";
            fm.RelationToMainInsured.value="00";
	        }
	        if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
	        {
	        	if(InsuredGrid.mulLineCount==0)
	        	{
	        		alert("请先添加第一被保人");
	        		fm.SequenceNo.value="1";
	        		fm.SequenceNoName.value="被保险人";
	        		return false;
	        	}
	        	//emptyInsured();
            noneedhome();
		        param="122";
		        fm.pagename.value="122";
            fm.InsuredSequencename.value="第二被保险人资料";
	        }
	        if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
	        {
	      	  if(InsuredGrid.mulLineCount==0)
	   	      {
	   		      alert("请先添加第一被保人");
	   		      Field.value="1";
	        		fm.SequenceNo.value="1";
	        		fm.SequenceNoName.value="被保险人";

	   		      return false;
	   	      }
	   	      if(InsuredGrid.mulLineCount==1)
	   	      {
	   	      	alert("请先添加第二被保人");
	   	      	Field.value="1";
	        		fm.SequenceNo.value="2";
	        		fm.SequenceNoName.value="第二被保险人";
	   	      	return false;
	   	      }
	   	      //emptyInsured();
            noneedhome();
		        param="123";
		        fm.pagename.value="123";
            fm.InsuredSequencename.value="第三被保险人资料";
	        }
          if (scantype== "scan")
          {
            setFocus();
          }
        }
        if( cCodeName == "CheckPostalAddress")
        {
	        if(fm.CheckPostalAddress.value=="1")
	        {
	        	fm.all('PostalAddress').value=fm.all('GrpAddress').value;
            fm.all('ZipCode').value=fm.all('GrpZipCode').value;
            fm.all('Phone').value= fm.all('GrpPhone').value;

	        }
	        else if(fm.CheckPostalAddress.value=="2")
	        {
	        	fm.all('PostalAddress').value=fm.all('HomeAddress').value;
            fm.all('ZipCode').value=fm.all('HomeZipCode').value;
            fm.all('Phone').value= fm.all('HomePhone').value;
	        }
	        else if(fm.CheckPostalAddress.value=="3")
	        {
	        	fm.all('PostalAddress').value="";
            fm.all('ZipCode').value="";
            fm.all('Phone').value= "";
	        }
        }

    }
    catch(ex) {}

}
/*********************************************************************
 *  显示家庭单下被保险人的信息
 *  返回值：  无
 *********************************************************************
 */
function getInsuredInfo()
{
    //alert("hl in getInsuredInfo ContNo="+fm.all("ContNo").value);
    //alert("hl in getInsuredInfo GrpName="+fm.all("GrpName").value);
    var ContNo=fm.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
        //var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";


    var mysql25= new SqlClass();
 	var sqlid25 = "BankContInputSql25";
	mysql25.setResourceName(sqlresourcename);
	mySql25.setSqlId(sqlid25);
	mysql25.addSubPara(ContNo);

        //alert("strSQL=="+strSQL);

        turnPage.strQueryResult  = easyQueryVer3(mysql25.getString(), 1, 0, 1);
        //alert("turnPage==="+turnPage.strQueryResult);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = InsuredGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

        //默认将焦点指向第一被保人
        //alert("fm.InsuredGrid=="+fm.all('spanInsuredGrid0').all('InsuredGridSel').checked);
        fm.all('spanInsuredGrid0').all('InsuredGridSel').checked=true;
        getInsuredDetail("spanInsuredGrid0","");
        showInsuredCodeName();

    }
}

/*********************************************************************
 *  获得个单合同的被保人信息
 *  返回值：  无
 *********************************************************************
 */
function getProposalInsuredInfo()
{
    //alert("ContNo=="+fm.all("ContNo").value);
    var tContNo=fm.all("ContNo").value;
    
   // "select * from LCInsured where ContNo='"+tContNo+"'"
    
    var mysql26= new SqlClass();
 	var sqlid26 = "BankContInputSql26";
	mysql26.setResourceName(sqlresourcename);
	mySql26.setSqlId(sqlid26);
	mysql26.addSubPara(tContNo);
	
	
    arrResult=easyExecSql(mysql26.getString(),1,0);
    if(arrResult==null||InsuredGrid.mulLineCount>1)
    {
        return;
    }
    else
    {
    	if(InsuredGrid.mulLineCount=1){
        DisplayInsured();//该合同下的被投保人信息
        var tCustomerNo = arrResult[0][2];		// 得到被保人客户号
        var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
        
      // "select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'" 
    
    var mysql27= new SqlClass();
 	var sqlid27 = "BankContInputSql27";
	mysql27.setResourceName(sqlresourcename);
	mySql27.setSqlId(sqlid27);
	mysql27.addSubPara(tCustomerNo);
	
        arrResult=easyExecSql(mysql27.getString(),1,0);
      }
      if(arrResult==null)
      {
          //alert("未得到用户信息");
          //return;
      }
      else
      {
          //displayAppnt();       //显示被保人详细内容
          emptyUndefined();
          fm.InsuredAddressNo.value=tAddressNo;
          getdetailaddress2();//显示被保人地址详细内容
      }
    }
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
}

/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    //alert("---parm1=="+parm1+"---parm2=="+parm2);

    var InsuredNo=fm.all(parm1).all('InsuredGrid1').value;

    var ContNo = fm.ContNo.value;

    //被保人详细信息
   // var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";

    var mysql62= new SqlClass();
 	var sqlid62 = "BankContInputSql62";
	mysql62.setResourceName(sqlresourcename);
	mySql62.setSqlId(sqlid62);
	mysql62.addSubPara(tCustomerNo);
	
    arrResult=easyExecSql(mysql62.getString());

    if(arrResult!=null)
    {
        //displayAppnt();
        displayInsuredInfo();
    }

    //strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";

    var mysql28= new SqlClass();
 	var sqlid28 = "BankContInputSql28";
	mysql28.setResourceName(sqlresourcename);
	mySql28.setSqlId(sqlid28);
	mysql28.addSubPara(ContNo);
	mysql28.addSubPara(InsuredNo);


    arrResult=easyExecSql(mysql28.getString());

    if(arrResult!=null)
    {
        DisplayInsured();
    }

    var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
    //alert("arrResult[0][10]=="+arrResult[0][10]);
    fm.InsuredAddressNo.value=tAddressNo;

    //显示被保人地址信息
    getdetailaddress2();

    getInsuredPolInfo();

    getImpartInfo();

    //getImpartDetailInfo();
    getAge2();
    //在录单过程中不需要进行客户合并，故注释掉　hl 20050505
    //如果是复核状态，则进行重复客户校验
    if(LoadFlag=="5"){
      InsuredChkNew();
      return;
    }
}
/*********************************************************************
 *  把查询返回的客户数据显示到被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayCustomer2()
{
	try{fm.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户地址数据显示到被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayAddress2()
{
	try{fm.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{fm.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{fm.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{fm.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{fm.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('GrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{fm.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{fm.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}
/*********************************************************************
 *  显示被保人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayInsured()
{
    try{fm.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{fm.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{fm.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{fm.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{fm.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{fm.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{fm.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{fm.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{fm.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{fm.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{fm.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{fm.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{fm.all('Name').value=arrResult[0][12];}catch(ex){};
    try{fm.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{fm.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{fm.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{fm.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{fm.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{fm.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{fm.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{fm.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{fm.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{fm.all('Health').value=arrResult[0][22];}catch(ex){};
    try{fm.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{fm.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{fm.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{fm.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{fm.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{fm.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{fm.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{fm.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{fm.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{fm.all('Position').value=arrResult[0][32];}catch(ex){};
    try{fm.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{fm.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{fm.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{fm.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{fm.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{fm.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{fm.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{fm.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{fm.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{fm.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{fm.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{fm.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{fm.all('LicenseType').value=arrResult[0][53];}catch(ex){};
}
function displayissameperson()
{
	//alert("here!");
  //alert("fm.all( 'AppntName').value="+fm.all( "AppntName").value);
　try{fm.all('InsuredNo').value= fm.all("AppntNo").value;                 }catch(ex){};
　try{fm.all('Name').value= fm.all("AppntName").value;                    }catch(ex){};
　try{fm.all('Sex').value= fm.all("AppntSex").value;                      }catch(ex){};
　try{fm.all('Birthday').value= fm.all("AppntBirthday").value;            }catch(ex){};
　try{fm.all('IDType').value= fm.all("AppntIDType").value;                }catch(ex){};
　try{fm.all('IDNo').value= fm.all("AppntIDNo").value;                    }catch(ex){};
　try{fm.all('Password').value= fm.all( "AppntPassword" ).value;          }catch(ex){};
　try{fm.all('NativePlace').value= fm.all("AppntNativePlace").value;      }catch(ex){};
　try{fm.all('Nationality').value= fm.all("AppntNationality").value;      }catch(ex){};
　try{fm.all('InsuredAddressNo').value= fm.all("AppntAddressNo").value;   }catch(ex){};
　try{fm.all('RgtAddress').value= fm.all( "AppntRgtAddress" ).value;      }catch(ex){};
　try{fm.all('Marriage').value= fm.all( "AppntMarriage" ).value;          }catch(ex){};
　try{fm.all('MarriageDate').value= fm.all( "AppntMarriageDate" ).value;  }catch(ex){};
　try{fm.all('Health').value= fm.all( "AppntHealth" ).value;              }catch(ex){};
　try{fm.all('Stature').value= fm.all( "AppntStature" ).value;            }catch(ex){};
　try{fm.all('Avoirdupois').value= fm.all( "AppntAvoirdupois" ).value;    }catch(ex){};
　try{fm.all('Degree').value= fm.all( "AppntDegree" ).value;              }catch(ex){};
　try{fm.all('CreditGrade').value= fm.all( "AppntDegreeCreditGrade" ).value;}catch(ex){};
　try{fm.all('OthIDType').value= fm.all( "AppntOthIDType" ).value;}catch(ex){};
　try{fm.all('OthIDNo').value= fm.all( "AppntOthIDNo" ).value;}catch(ex){};
　try{fm.all('ICNo').value= fm.all("AppntICNo").value;}catch(ex){};
　try{fm.all('GrpNo').value= fm.all("AppntGrpNo").value;}catch(ex){};
　try{fm.all( 'JoinCompanyDate' ).value = fm.all("JoinCompanyDate").value; if(fm.all( 'JoinCompanyDate' ).value=="false"){fm.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
　try{fm.all('StartWorkDate').value= fm.all("AppntStartWorkDate").value;}catch(ex){};
　try{fm.all('Position').value= fm.all("AppntPosition").value;}catch(ex){};
　try{fm.all( 'Position' ).value = fm.all("Position").value;} catch(ex) { };
　try{fm.all('Salary').value= fm.all("AppntSalary").value;}catch(ex){};
　try{fm.all('OccupationType').value= fm.all("AppntOccupationType").value;}catch(ex){};
　try{fm.all('OccupationCode').value= fm.all("AppntOccupationCode").value;}catch(ex){};
　try{fm.all('WorkType').value= fm.all("AppntWorkType").value;}catch(ex){};
　try{fm.all('PluralityType').value= fm.all("AppntPluralityType").value;}catch(ex){};
　try{fm.all('DeathDate').value= fm.all("AppntDeathDate").value;}catch(ex){};
　try{fm.all('SmokeFlag').value= fm.all("AppntSmokeFlag").value;}catch(ex){};
　try{fm.all('BlacklistFlag').value= fm.all("AppntBlacklistFlag").value;}catch(ex){};
　try{fm.all('Proterty').value= fm.all("AppntProterty").value;}catch(ex){};
　//try{fm.all('Remark').value= fm.all("AppntRemark").value;}catch(ex){};
　try{fm.all('State').value= fm.all("AppntState").value;}catch(ex){};
　try{fm.all('Operator').value= fm.all("AppntOperator").value;}catch(ex){};
　try{fm.all('MakeDate').value= fm.all("AppntMakeDate").value;}catch(ex){};
　try{fm.all('MakeTime').value= fm.all("AppntMakeTime").value;}catch(ex){};
　try{fm.all('ModifyDate').value= fm.all("AppntModifyDate").value;}catch(ex){};
　try{fm.all('ModifyTime').value= fm.all("AppntModifyTime").value;}catch(ex){};
　try{fm.all('PostalAddress').value= fm.all("AppntPostalAddress").value;}catch(ex){};
　try{fm.all('ZipCode').value= fm.all("AppntZipCode").value;}catch(ex){};
　try{fm.all('Phone').value= fm.all("AppntPhone").value;}catch(ex){};
　try{fm.all('Fax').value= fm.all("AppntFax").value;}catch(ex){};
　try{fm.all('Mobile').value= fm.all("AppntMobile").value;}catch(ex){};
　try{fm.all('EMail').value= fm.all("AppntEMail").value;}catch(ex){};
　try{fm.all('GrpName').value= fm.all("AppntGrpName").value;}catch(ex){};
　try{fm.all('GrpPhone').value= fm.all("AppntGrpPhone").value;}catch(ex){};
　try{fm.all('GrpAddress').value= fm.all("CompanyAddress").value;}catch(ex){};
　try{fm.all('GrpZipCode').value= fm.all("AppntGrpZipCode").value;}catch(ex){};
　try{fm.all('GrpFax').value= fm.all("AppntGrpFax").value;}catch(ex){};
　try{fm.all('HomeAddress').value= fm.all("AppntHomeAddress").value;}catch(ex){};
　try{fm.all('HomePhone').value= fm.all("AppntHomePhone").value;}catch(ex){};
　try{fm.all('HomeZipCode').value= fm.all("AppntHomeZipCode").value;}catch(ex){};
　try{fm.all('HomeFax').value= fm.all("AppntHomeFax").value;}catch(ex){};
　try{fm.all('RelationToAppnt').value="00";}catch(ex){};
　try{fm.all('InsuredProvince').value= fm.all("AppntProvince").value;}catch(ex){};
　try{fm.all('InsuredCity').value= fm.all("AppntCity").value;}catch(ex){};
　try{fm.all('InsuredDistrict').value= fm.all("AppntDistrict").value;}catch(ex){};
　try{fm.all('LicenseType').value= fm.all("AppntLicenseType").value;}catch(ex){};
  try{fm.all('IncomeWay').value= fm.all("IncomeWay0").value;}catch(ex){};
  try{fm.all('Income').value= fm.all("Income0").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');

/***************************
  if(fm.all('Position').value=="false"){
	  fm.all('Position').value="";
	}
  if(fm.all('Salary').value=="false"){
  	fm.all( 'Salary' ).value="";
  }
***************************/
}
/*********************************************************************
 *  查询被保人告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid2();
    var InsuredNo=fm.all("InsuredNo").value;
    var ContNo=fm.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {

    //	var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";

	//    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

    var mysql29= new SqlClass();
 	var sqlid29 = "BankContInputSql29";
	mysql29.setResourceName(sqlresourcename);
	mySql29.setSqlId(sqlid29);
	mysql29.addSubPara(InsuredNo);
	mysql29.addSubPara(ContNo);

	    arrResult = easyExecSql(mysql29.getString(),1,0);
	    
	    
    var mysql30= new SqlClass();
 	var sqlid30 = "BankContInputSql30";
	mysql30.setResourceName(sqlresourcename);
	mySql30.setSqlId(sqlid30);
	mysql30.addSubPara(InsuredNo);
	mysql30.addSubPara(ContNo);
	
	    arrResult1 = easyExecSql(mysql30.getString(),1,0);



	    try{fm.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{fm.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};

      //  var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ProposalContNo='"+ContNo+"' and CustomerNoType='1' and ((impartver='01' and impartcode<>'001') or (impartver='02'))";
       
        var mysql31= new SqlClass();
 	var sqlid31 = "BankContInputSql31";
	mysql31.setResourceName(sqlresourcename);
	mySql31.setSqlId(sqlid31);
	mysql31.addSubPara(InsuredNo);
	mysql31.addSubPara(ContNo);
       
        turnPage.strQueryResult  = easyQueryVer3(mysql31.getString(), 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartInsuredGrid;
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
/*********************************************************************
 *  查询告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid2();
    var InsuredNo=fm.all("InsuredNo").value;
    var ContNo=fm.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
       // var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
       
       var mysql32= new SqlClass();
 	var sqlid32 = "BankContInputSql32";
	mysql32.setResourceName(sqlresourcename);
	mySql32.setSqlId(sqlid32);
	mysql32.addSubPara(InsuredNo);
	mysql32.addSubPara(ContNo);
       
       
        turnPage.strQueryResult  = easyQueryVer3(mysql32.getString(), 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartDetailGrid;
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
/*********************************************************************
 *  获得被保人险种信息，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo=fm.all("InsuredNo").value;
    var ContNo=fm.all("ContNo").value;
    //险种信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
        //alert("InsuredNo=="+InsuredNo+"--ContNo=="+ContNo);
       // var strSQL ="select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
        //alert(strSQL);
        
         var mysql33= new SqlClass();
 	var sqlid33 = "BankContInputSql33";
	mysql33.setResourceName(sqlresourcename);
	mySql33.setSqlId(sqlid33);
	mysql33.addSubPara(InsuredNo);
	mysql33.addSubPara(ContNo);
        
        turnPage.strQueryResult  = easyQueryVer3(mysql33.getString(), 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
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
}
/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人险种详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolDetail(parm1,parm2)
{
    var PolNo=fm.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}
/*********************************************************************
 *  根据家庭单类型，隐藏界面控件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  校验被保险人与主被保险人关系
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkself()
{
	if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value=="")
	{
	  fm.RelationToMainInsured.value="00";
	  return true;
  }
	else if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value!="00")
	{
	  alert("个人单中'与主被保险人关系'只能是'本人'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
	else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value==""&&InsuredGrid.mulLineCount==0)
	{
	  fm.RelationToMainInsured.value="00";
	  return true;
  }
  else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value!="00"&&InsuredGrid.mulLineCount==0)
  {
	  alert("家庭单中第一位被保险人的'与主被保险人关系'只能是'本人'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
  else{
    return true;
  }
}
/*********************************************************************
 *  校验保险人
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        if (fm.all('ContNo').value != "")
        {
            alert("团单的个单不能有多被保险人");
            return false;
        }
        else
            return true;
    }
    else
    {
        if (fm.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
          //  var strSQL="select * from LCInsured where contno='"+fm.all('ContNo').value +"'";
            
             var mysql34= new SqlClass();
 	var sqlid34 = "BankContInputSql34";
	mysql34.setResourceName(sqlresourcename);
	mySql34.setSqlId(sqlid34);
	mysql34.addSubPara(fm.all('ContNo').value);
            
            turnPage.strQueryResult  = easyQueryVer3(mysql34.getString(), 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
        else if (fm.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
        {
            alert("家庭单只能有一个主被保险人");
            return false;
        }
        else if (fm.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
          //  var strSql="select * from LCInsured where contno='"+fm.all('ContNo').value +"' and RelationToAppnt='00' ";
           
            var mysql35= new SqlClass();
 	var sqlid35 = "BankContInputSql35";
	mysql35.setResourceName(sqlresourcename);
	mySql35.setSqlId(sqlid35);
	mysql35.addSubPara(fm.all('ContNo').value);
           
            turnPage.strQueryResult  = easyQueryVer3(mysql35.getString(), 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("投保人已经是该合同号下的被保险人");
                return false;
            }
		    else
		        return true;
        }
        else
            return true;
    }
	//select count(*) from ldinsured

}
/*********************************************************************
 *  投保人与被保人相同选择框事件
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //对应未选同一人，又打钩的情况
    if ( fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
    {
      DivLCInsured.style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
      fm.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";

      displayissameperson();
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
      fm.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "";
      divSalary.style.display = "";
      try{fm.all('Name').value=""; }catch(ex){};
      try{fm.all('Sex').value= ""; }catch(ex){};
      try{fm.all('Birthday').value= ""; }catch(ex){};
      try{fm.all('IDType').value= "0"; }catch(ex){};
      try{fm.all('IDNo').value= ""; }catch(ex){};
      try{fm.all('Password').value= ""; }catch(ex){};
      try{fm.all('NativePlace').value= ""; }catch(ex){};
      try{fm.all('Nationality').value=""; }catch(ex){};
      try{fm.all('RgtAddress').value= ""; }catch(ex){};
      try{fm.all('Marriage').value= "";}catch(ex){};
      try{fm.all('MarriageDate').value= "";}catch(ex){};
      try{fm.all('Health').value= "";}catch(ex){};
      try{fm.all('Stature').value= "";}catch(ex){};
      try{fm.all('Avoirdupois').value= "";}catch(ex){};
      try{fm.all('Degree').value= "";}catch(ex){};
      try{fm.all('CreditGrade').value= "";}catch(ex){};
      try{fm.all('OthIDType').value= "";}catch(ex){};
      try{fm.all('OthIDNo').value= "";}catch(ex){};
      try{fm.all('ICNo').value="";}catch(ex){};
      try{fm.all('GrpNo').value= "";}catch(ex){};
      try{fm.all('JoinCompanyDate').value= "";}catch(ex){}
      try{fm.all('StartWorkDate').value= "";}catch(ex){};
      try{fm.all('Position').value= "";}catch(ex){};
      try{fm.all('Salary').value= "";}catch(ex){};
      try{fm.all('OccupationType').value= "";}catch(ex){};
      try{fm.all('OccupationCode').value= "";}catch(ex){};
      try{fm.all('WorkType').value= "";}catch(ex){};
      try{fm.all('PluralityType').value= "";}catch(ex){};
      try{fm.all('DeathDate').value= "";}catch(ex){};
      try{fm.all('SmokeFlag').value= "";}catch(ex){};
      try{fm.all('BlacklistFlag').value= "";}catch(ex){};
      try{fm.all('Proterty').value= "";}catch(ex){};
      //try{fm.all('Remark').value= "";}catch(ex){};
      try{fm.all('State').value= "";}catch(ex){};
      try{fm.all('Operator').value= "";}catch(ex){};
      try{fm.all('MakeDate').value= "";}catch(ex){};
      try{fm.all('MakeTime').value="";}catch(ex){};
      try{fm.all('ModifyDate').value= "";}catch(ex){};
      try{fm.all('ModifyTime').value= "";}catch(ex){};
      try{fm.all('PostalAddress').value= "";}catch(ex){};
      try{fm.all('PostalAddress').value= "";}catch(ex){};
      try{fm.all('ZipCode').value= "";}catch(ex){};
      try{fm.all('Phone').value= "";}catch(ex){};
      try{fm.all('Mobile').value= "";}catch(ex){};
      try{fm.all('EMail').value="";}catch(ex){};
      try{fm.all('GrpName').value= "";}catch(ex){};
      try{fm.all('GrpPhone').value= "";}catch(ex){};
      try{fm.all('GrpAddress').value="";}catch(ex){};
      try{fm.all('GrpZipCode').value= "";}catch(ex){};
      try{fm.all('RelationToAppnt').value= "";}catch(ex){};
      try{fm.all('Fax').value= "";}catch(ex){};
      try{fm.all('HomePhone').value= "";}catch(ex){};
      try{fm.all('IncomeWay').value=  "";}catch(ex){};
      try{fm.all('Income').value=  "";}catch(ex){};

    }
}
/*********************************************************************
 *  投保人客户号查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredNo()
{
    if (fm.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + fm.all("InsuredNo").value + "'", 1, 0);
       
      //"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + fm.all("InsuredNo").value + "'" 
        var mysql36= new SqlClass();
 	var sqlid36 = "BankContInputSql36";
	mysql36.setResourceName(sqlresourcename);
	mySql36.setSqlId(sqlid36);
	mysql36.addSubPara(fm.all("InsuredNo").value);
       
        arrResult = easyExecSql(mysql36.getString(), 1, 0);
        if (arrResult == null)
        {
          alert("未查到被保人信息");
          //displayAppnt(new Array());
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
        }
    }
}
/*********************************************************************
 *  投保人查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function showAppnt1()
function showInsured1()
{
	if( mOperate == 0 )
	{
		//mOperate = 2;
		mOperate = 3;  //被保人信息查询　mOperate = 3; hl20050503
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" ,"",sFeatures);
	}
}
/*********************************************************************
 *  显示投保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 //hanlin 20050503
//function displayAppnt()
function displayInsuredInfo()
{
	  //alert("asdfasfsf");
    try{fm.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{fm.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{fm.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{fm.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{fm.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{fm.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{fm.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{fm.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{fm.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{fm.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{fm.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{fm.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{fm.all('Health').value= arrResult[0][12];}catch(ex){};
    try{fm.all('Stature').value= arrResult[0][13];}catch(ex){};
    try{fm.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    try{fm.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{fm.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{fm.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{fm.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{fm.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{fm.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{fm.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{fm.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{fm.all('Position').value= arrResult[0][23];}catch(ex){};
    try{fm.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{fm.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{fm.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{fm.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{fm.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{fm.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{fm.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{fm.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{fm.all('Proterty').value= arrResult[0][32];}catch(ex){};
    //try{fm.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{fm.all('State').value= arrResult[0][34];}catch(ex){};
    try{fm.all('VIPValue1').value= arrResult[0][35];}catch(ex){};
    try{fm.all('Operator').value= arrResult[0][36];}catch(ex){};
    try{fm.all('MakeDate').value= arrResult[0][37];}catch(ex){};
    try{fm.all('MakeTime').value= arrResult[0][38];}catch(ex){};
    try{fm.all('ModifyDate').value= arrResult[0][39];}catch(ex){};
    try{fm.all('ModifyTime').value= arrResult[0][40];}catch(ex){};
    try{fm.all('LicenseType').value= arrResult[0][43];}catch(ex){};
    try{fm.all('GrpName').value= arrResult[0][44];}catch(ex){};



    //地址显示部分的变动
    try{fm.all('InsuredAddressNo').value= "";}catch(ex){};
    try{fm.all('PostalAddress').value=  "";}catch(ex){};
    try{fm.all('ZipCode').value=  "";}catch(ex){};
    try{fm.all('Phone').value=  "";}catch(ex){};
    try{fm.all('Mobile').value=  "";}catch(ex){};
    try{fm.all('EMail').value=  "";}catch(ex){};
    //try{fm.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{fm.all('GrpPhone').value=  "";}catch(ex){};
    try{fm.all('GrpAddress').value=  "";}catch(ex){};
    try{fm.all('GrpZipCode').value=  "";}catch(ex){};
}
/*********************************************************************
 *  查询返回后触发
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterQuery21( arrQueryResult )
{
  //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// 查询投保单
            fm.all( 'ContNo' ).value = arrQueryResult[0][0];

//"select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'"
    var mysql37= new SqlClass();
 	var sqlid37 = "BankContInputSql37";
	mysql37.setResourceName(sqlresourcename);
	mySql37.setSqlId(sqlid37);
	mysql37.addSubPara(arrQueryResult[0][0]);

            arrResult = easyExecSql(mysql37.getString(), 1, 0);

            if (arrResult == null)
            {
                alert("未查到投保单信息");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 3 )
        {		// 被保人信息
        	
        	
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	
  //   "select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'"   	
        	var mysql36= new SqlClass();
 	var sqlid63 = "BankContInputSql63";
	mysql63.setResourceName(sqlresourcename);
	mySql63.setSqlId(sqlid63);
	mysql63.addSubPara(arrQueryResult[0][0]);
        	
        	
        	arrResult = easyExecSql(mysql63.getString(), 1, 0);
        	if (arrResult == null)
        	{
        	    alert("未查到投保人信息");
        	}
        	else
        	{
        		 //hl 20050503
        	   // displayAppnt(arrResult[0]);
        	   //alert("arrResult[0][35]=="+arrResult[0][35]);
        	   displayInsuredInfo(arrResult[0]);
        	  // showCodeName();
        	}
        }
    }

	mOperate = 0;		// 恢复初态
	showCodeName();
}
/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailwork2()
{
    //var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    
    var mysql38= new SqlClass();
 	var sqlid38 = "BankContInputSql38";
	mysql38.setResourceName(sqlresourcename);
	mySql38.setSqlId(sqlid38);
	mysql38.addSubPara(fm.OccupationCode.value);
    
    var arrResult = easyExecSql(mysql38.getString());
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
    }
    else
    {
        fm.OccupationType.value = '';
    }
}
/*获得个人单信息，写入页面控件
function getProposalInsuredInfo(){
  var ContNo = fm.ContNo.value;
  //被保人详细信息
  var strSQL ="select * from ldperson where CustomerNo in (select InsuredNo from LCInsured where ContNo='"+ContNo+"')";
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null){
  	DisplayCustomer();
  }

  strSQL ="select * from LCInsured where ContNo = '"+ContNo+"'";
  arrResult=easyExecSql(strSQL);

  if(arrResult!=null){
  	   DisplayInsured();
  }else{


    return;
  }

  var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
  var InsuredNo=arrResult[0][2];
  var strSQL="select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+InsuredNo+"'";
  arrResult=easyExecSql(strSQL);
    if(arrResult!=null){
  	DisplayAddress();
    }

    getInsuredPolInfo();

}*/


/*********************************************************************
 *  把合同所有信息录入结束确认(团险)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
	alert("LoadFlag=="+LoadFlag);


    if (wFlag ==1 ) //录入完毕确认
    {
       // var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
       
       var mysql39= new SqlClass();
 	var sqlid39 = "BankContInputSql39";
	mysql39.setResourceName(sqlresourcename);
	mySql39.setSqlId(sqlid39);
	mysql39.addSubPara(fm.ContNo.value);
	
        turnPage.strQueryResult = easyQueryVer3(mysql39.getString(), 1, 0, 1);
        if (turnPage.strQueryResult)
        {
		    alert("该合同已经做过保存！");
		    return;
		}
		fm.AppntNo.value = AppntNo;
		fm.AppntName.value = AppntName;
		fm.WorkFlowFlag.value = "7999999999";
    }
    else if (wFlag ==2)//复核完毕确认
    {
  	    if(fm.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
            return;
        }
		fm.WorkFlowFlag.value = "0000001001";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag ==3)
    {
        if(fm.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
            return;
        }
		fm.WorkFlowFlag.value = "0000001002";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
    fm.submit(); //提交
}
/*********************************************************************
 *  查询被保险人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailaddress2()
{
    //alert("abc");
    //var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
   
    var mysql40= new SqlClass();
 	var sqlid40 = "BankContInputSql40";
	mysql40.setResourceName(sqlresourcename);
	mySql40.setSqlId(sqlid40);
	mysql40.addSubPara(fm.InsuredAddressNo.value);
	mysql40.addSubPara(fm.InsuredNo.value);
   
    arrResult=easyExecSql(mysql39.getString());
    try{fm.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
    try{fm.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
    try{fm.all('ZIPCODE').value= arrResult[0][2];}catch(ex){};
    try{fm.all('Phone').value= arrResult[0][3];}catch(ex){};
    try{fm.all('Mobile').value= arrResult[0][4];}catch(ex){};
    try{fm.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{fm.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try{fm.all('GrpPhone').value= arrResult[0][6];}catch(ex){};
    try{fm.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    try{fm.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
    try{fm.all('Fax').value= arrResult[0][9];}catch(ex){};
    try{fm.all('HomePhone').value= arrResult[0][10];}catch(ex){};
    //alert("arrResult[0][11]=="+arrResult[0][11]);
   // try{fm.all('GrpName').value= arrResult[0][11];}catch(ex){};
    //alert("fm.GrpName="+fm.GrpName.value);
   try{fm.all('InsuredProvince').value= arrResult[0][12];}catch(ex){};
   try{fm.all('InsuredCity').value= arrResult[0][13];}catch(ex){};
   try{fm.all('InsuredDistrict').value= arrResult[0][14];}catch(ex){};
   showOneCodeName('Province','InsuredProvince','InsuredProvinceName');
   showOneCodeName('City','InsuredCity','InsuredCityName');
   showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
}
/*********************************************************************
 *  查询保险计划
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
  //  strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
    //alert("strsql :" + strsql);
    
     var mysql41= new SqlClass();
 	var sqlid41 = "BankContInputSql41";
	mysql41.setResourceName(sqlresourcename);
	mySql41.setSqlId(sqlid41);
	mysql41.addSubPara(tProposalGrpContNo);

    
    turnPage.strQueryResult  = easyQueryVer3(mysql41.getString(), 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    	 divContPlan.style.display="";
    }
    else
    {
      //alert("保险计划没查到");
        divContPlan.style.display="none";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  查询处理机构
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    	//alert("1");
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	//strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	//alert("strsql :" + strsql);
	
	 var mysql42= new SqlClass();
 	var sqlid42 = "BankContInputSql42";
	mysql42.setResourceName(sqlresourcename);
	mySql42.setSqlId(sqlid42);
	mysql42.addSubPara(tProposalGrpContNo);
	
	
	turnPage.strQueryResult  = easyQueryVer3(mysql42.getString(), 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		divExecuteCom.style.display="";
	}
	else
	{
	    divExecuteCom.style.display="none";
    }
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
}
/*********************************************************************
 *  清空投保人数据
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
function emptyAppnt()
{
        try{fm.all('AppntVIPValue').value= ""; }catch(ex){};
        try{fm.all('AppntVIPFlagname').value= ""; }catch(ex){};
        try{fm.all('AppntName').value= ""; }catch(ex){};
        try{fm.all('AppntIDNo').value= ""; }catch(ex){};
        try{fm.all('AppntSex').value= ""; }catch(ex){};
        try{fm.all('AppntSexName').value= ""; }catch(ex){};
        try{fm.all('AppntBirthday').value= ""; }catch(ex){};
        try{fm.all('AppntAge').value= ""; }catch(ex){};
        try{fm.all('AppntMarriage').value= ""; }catch(ex){};
        try{fm.all('AppntMarriageName').value= ""; }catch(ex){};
        try{fm.all('AppntNativePlace').value= ""; }catch(ex){};
        try{fm.all('AppntNativePlaceName').value= ""; }catch(ex){};
        try{fm.all('AppntOccupationCode').value= ""; }catch(ex){};
        try{fm.all('AppntOccupationCodeName').value= ""; }catch(ex){};
        try{fm.all('AppntLicenseType').value= ""; }catch(ex){};
        try{fm.all('AppntLicenseTypeName').value= ""; }catch(ex){};
        try{fm.all('AppntAddressNo').value= ""; }catch(ex){};
        try{fm.all('AddressNoName').value= ""; }catch(ex){};
        try{fm.all('AppntProvince').value= ""; }catch(ex){};
        try{fm.all('AppntProvinceName').value= ""; }catch(ex){};
        try{fm.all('AppntCity').value= ""; }catch(ex){};
        try{fm.all('AppntCityName').value= ""; }catch(ex){};
        try{fm.all('AppntDistrict').value= ""; }catch(ex){};
        try{fm.all('AppntDistrictName').value= ""; }catch(ex){};
        try{fm.all('AppntPostalAddress').value= ""; }catch(ex){};
        try{fm.all('AppntZipCode').value= ""; }catch(ex){};
        try{fm.all('AppntMobile').value= ""; }catch(ex){};
        try{fm.all('AppntGrpPhone').value= ""; }catch(ex){};
        try{fm.all('AppntFax').value= ""; }catch(ex){};
        try{fm.all('AppntHomePhone').value= ""; }catch(ex){};
        try{fm.all('AppntGrpName').value= ""; }catch(ex){};
        try{fm.all('AppntEMail').value= ""; }catch(ex){};
        try{fm.all('PayMode').value= ""; }catch(ex){};
        try{fm.all('FirstPayModeName').value= ""; }catch(ex){};
        try{fm.all('AppntBankCode').value= ""; }catch(ex){};
        try{fm.all('FirstBankCodeName').value= ""; }catch(ex){};
        try{fm.all('AppntAccName').value= ""; }catch(ex){};
        try{fm.all('AppntBankAccNo').value= ""; }catch(ex){};
        try{fm.all('SecPayMode').value= ""; }catch(ex){};
        try{fm.all('PayModeName').value= ""; }catch(ex){};
        try{fm.all('SecAppntBankCode').value= ""; }catch(ex){};
        try{fm.all('AppntBankCodeName').value= ""; }catch(ex){};
        try{fm.all('SecAppntAccName').value= ""; }catch(ex){};
        try{fm.all('SecAppntBankAccNo').value= ""; }catch(ex){};
        try{fm.all('Income0').value= ""; }catch(ex){};
        try{fm.all('IncomeWay0').value= ""; }catch(ex){};
        try{fm.all('IncomeWayName0').value= ""; }catch(ex){};
        ImpartGrid.clearData();
	ImpartGrid.addOne();

}
function emptyInsured()
{

	try{fm.all('InsuredNo').value= ""; }catch(ex){};
	try{fm.all('VIPValue1').value= ""; }catch(ex){};
	try{fm.all('AppntVIPFlagname1').value= ""; }catch(ex){};
	try{fm.all('SequenceNo').value= ""; }catch(ex){};
	try{fm.all('ExecuteCom').value= ""; }catch(ex){};
	try{fm.all('FamilyID').value= ""; }catch(ex){};
	try{fm.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{fm.all('RelationToAppnt').value= ""; }catch(ex){};
	try{fm.all('RelationToAppntName').value= ""; }catch(ex){};
	try{fm.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{fm.all('SequenceNo').value= ""; }catch(ex){};
	try{fm.all('Name').value= ""; }catch(ex){};
	try{fm.all('Sex').value= ""; }catch(ex){};
	try{fm.all('SexName').value= ""; }catch(ex){};
	try{fm.all('RelationToMainInsuredName').value= ""; }catch(ex){};
	try{fm.all('SequenceNoName').value= ""; }catch(ex){};
	try{fm.all('Birthday').value= ""; }catch(ex){};
	try{fm.all('InsuredAppAge').value= ""; }catch(ex){};
	try{fm.all('IDType').value= ""; }catch(ex){};
	try{fm.all('IDNo').value= ""; }catch(ex){};
	try{fm.all('IncomeWayName').value= ""; }catch(ex){};
	try{fm.all('InsuredProvince').value= ""; }catch(ex){};
	try{fm.all('InsuredProvinceName').value= ""; }catch(ex){};
	try{fm.all('InsuredCity').value= ""; }catch(ex){};
	try{fm.all('InsuredCityName').value= ""; }catch(ex){};
	try{fm.all('InsuredDistrict').value= ""; }catch(ex){};
	try{fm.all('InsuredDistrictName').value= ""; }catch(ex){};
	try{fm.all('Income').value= ""; }catch(ex){};
	try{fm.all('LicenseType').value= ""; }catch(ex){};
	try{fm.all('LicenseTypeName').value= ""; }catch(ex){};
	try{fm.all('IDTypeName').value= ""; }catch(ex){};
	try{fm.all('InsuredAddressNoName').value= ""; }catch(ex){};
	try{fm.all('IncomeWay').value= ""; }catch(ex){};
	try{fm.all('NativePlace').value= ""; }catch(ex){};
	 try{fm.all('NativePlaceName').value= ""; }catch(ex){};
	try{fm.all('Nationality').value= ""; }catch(ex){};
	try{fm.all('RgtAddress').value= ""; }catch(ex){};
	try{fm.all('Marriage').value= ""; }catch(ex){};
	try{fm.all('MarriageName').value= ""; }catch(ex){};
	try{fm.all('MarriageDate').value= ""; }catch(ex){};
	try{fm.all('Health').value= ""; }catch(ex){};
	try{fm.all('Stature').value= ""; }catch(ex){};
	try{fm.all('Avoirdupois').value= ""; }catch(ex){};
	try{fm.all('Degree').value= ""; }catch(ex){};
	try{fm.all('CreditGrade').value= ""; }catch(ex){};
	try{fm.all('BankCode').value= ""; }catch(ex){};
	try{fm.all('BankAccNo').value= ""; }catch(ex){};
	try{fm.all('AccName').value= ""; }catch(ex){};
	try{fm.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{fm.all('StartWorkDate').value= ""; }catch(ex){};
	try{fm.all('Position').value= ""; }catch(ex){};
	try{fm.all('Salary').value= ""; }catch(ex){};
	//try{fm.all('OccupationType').value= ""; }catch(ex){};
	try{fm.all('OccupationCodeName').value= ""; }catch(ex){};
	try{fm.all('OccupationCode').value= ""; }catch(ex){};
	try{fm.all('WorkType').value= ""; }catch(ex){};
	try{fm.all('PluralityType').value= ""; }catch(ex){};
	try{fm.all('SmokeFlag').value= ""; }catch(ex){};
	try{fm.all('ContPlanCode').value= ""; }catch(ex){};
  try{fm.all('GrpName').value= ""; }catch(ex){};
  try{fm.all('HomeAddress').value= ""; }catch(ex){};
  try{fm.all('HomeZipCode').value= ""; }catch(ex){};
  try{fm.all('HomePhone').value= ""; }catch(ex){};
  try{fm.all('HomeFax').value= ""; }catch(ex){};
  try{fm.all('GrpFax').value= ""; }catch(ex){};
  try{fm.all('Fax').value= ""; }catch(ex){};
	emptyAddress();
	ImpartGrid.clearData();
	ImpartGrid.addOne();
	//ImpartDetailGrid.clearData();
	//ImpartDetailGrid.addOne();
}

/*********************************************************************
 *  清空客户地址数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function emptyAddress()
{
	try{fm.all('PostalAddress').value= "";  }catch(ex){};
	try{fm.all('ZipCode').value= "";  }catch(ex){};
	try{fm.all('Phone').value= "";  }catch(ex){};
	try{fm.all('Mobile').value= "";  }catch(ex){};
	try{fm.all('EMail').value= "";  }catch(ex){};
	//try{fm.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{fm.all('GrpPhone').value= "";  }catch(ex){};
	try{fm.all('GrpAddress').value= ""; }catch(ex){};
	try{fm.all('GrpZipCode').value= "";  }catch(ex){};
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo2(iIdNo)
{
	//alert("aafsd");
	if(fm.all('IDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			theFirstValue="";
			theSecondValue="";
			//fm.all('IDNo').focus();
			return;

		}
		else
		{
			fm.all('Birthday').value=tBirthday;
			fm.all('Sex').value=tSex;
		}

	}
}
/*********************************************************************
 *  合同信息录入完毕确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
	mWFlag = 1;
	if (wFlag ==1 ) //录入完毕确认
	{
	    /*
	    var tStr= "	select * from lwmission where 1=1 "
	    					+" and lwmission.processid = '0000000011'"
	    					+" and lwmission.activityid = '0000011001'"
	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    */
	    var mysql43= new SqlClass();
 	var sqlid43 = "BankContInputSql43";
	mysql43.setResourceName(sqlresourcename);
	mySql43.setSqlId(sqlid43);
	mysql43.addSubPara(fm.ProposalGrpContNo.value);
	    
	    turnPage.strQueryResult = easyQueryVer3(mysql43.getString(), 1, 0, 1);
	    if (turnPage.strQueryResult)
	    {
	        alert("该团单合同已经做过保存！");
	        return;
	    }
		if(fm.all('ProposalGrpContNo').value == "")
	    {
	        alert("团单合同信息未保存,不容许您进行 [录入完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//录入完毕
    }
    else if (wFlag ==2)//复核完毕确认
    {
        if(fm.all('ProposalGrpContNo').value == "")
	    {
	        alert("未查询出团单合同信息,不容许您进行 [复核完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000011002";					//复核完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(fm.all('ProposalGrpContNo').value == "")
	    {
	        alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000001002";					//复核修改完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		if(fm.all('ProposalGrpContNo').value == "")
	    {
	       alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
	       return;
	    }
	    fm.WorkFlowFlag.value = "0000001021";					//问题修改
	    fm.MissionID.value = MissionID;
	    fm.SubMissionID.value = SubMissionID;
	}
	else
		return;

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./GrpInputConfirm.jsp";
    fm.submit(); //提交
}
function getaddresscodedata2()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if(fm.InsuredAddressNo.value=="")
    {
    	fm.PostalAddress.value="";
    	fm.InsuredAddressNoName.value="";
    	fm.InsuredProvinceName.value="";
    	fm.InsuredProvince.value="";
     	fm.InsuredCityName.value="";
    	fm.InsuredCity.value="";
    	fm.InsuredDistrictName.value="";
    	fm.InsuredDistrict.value="";
    	fm.PostalAddress.value="";
    	fm.ZIPCODE.value="";
      fm.Mobile.value="";
     	fm.GrpPhone.value="";
      fm.Fax.value="";
    	fm.HomePhone.value="";
    	fm.EMail.value="";
    	}
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    
    var mysql44= new SqlClass();
 	var sqlid44 = "BankContInputSql44";
	mysql44.setResourceName(sqlresourcename);
	mySql44.setSqlId(sqlid44);
	mysql44.addSubPara(fm.ProposalGrpContNo.value);
    
    
    
    
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mysql44.getString(), 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    fm.all("InsuredAddressNo").CodeData=tCodeData;
}

function getImpartCode(parm1,parm2){
  //alert("hehe:"+fm.all(parm1).all('ImpartGrid1').value);
  var impartVer=fm.all(parm1).all('ImpartGrid1').value;
  window.open("../cardgrp/ImpartCodeSel.jsp?ImpartVer="+impartVer,"",sFeatures);
}
function checkidtype2()
{
	//alert("sdasf");
	if(fm.IDType.value=="")
	{
		alert("请先选择证件类型！");
	//	fm.IDNo.value="";
        }
}
function getallinfo()
{
 	if(fm.Name.value!=""&&fm.IDType.value!=""&&fm.IDNo.value!="")
 	{
 	/*
	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
                +"  and Name='"+fm.Name.value
                +"' and IDType='"+fm.IDType.value
                +"' and IDNo='"+fm.IDNo.value
		+"' order by a.CustomerNo";
		
*/
		 var mysql45= new SqlClass();
 	var sqlid45 = "BankContInputSql45";
	mysql45.setResourceName(sqlresourcename);
	mySql45.setSqlId(sqlid45);
	mysql45.addSubPara(fm.Name.value);
	mysql45.addSubPara(fm.IDType.value);
	mysql45.addSubPara(fm.IDNo.value);
	
             turnPage.strQueryResult  = easyQueryVer3(mysql45.getString(), 1, 0, 1);
             if (turnPage.strQueryResult != "")
             {
 		  mOperate = 2;
 		  window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
}
function DelRiskInfo()
{
	if(fm.InsuredNo.value=="")
	{
		alert("请先选择被保人");
		return false;
	}
	var tSel =PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("该客户没有险种或者您忘记选择了？");
		return false;
	}
	var tRow = PolGrid.getSelNo() - 1;
	var tpolno=PolGrid.getRowColData(tRow,1)
	fm.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	fm.submit(); //提交

}
function InsuredChk()
{
	var tSel =InsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("请先选择被保险人！");
		return false;
	}
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	/*
  var sqlstr="select * from ldperson where Name='"+tInsuredName
            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
            +"' and CustomerNo<>'"+tInsuredNo+"'"
            +" union select * from ldperson where IDType = '"+fm.IDType.value
            +"' and IDType is not null and IDNo = '"+fm.IDNo.value
            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
     
    */ 
     var mysql46= new SqlClass();
 	var sqlid46 = "BankContInputSql46";
	mysql46.setResourceName(sqlresourcename);
	mySql46.setSqlId(sqlid46);
	mysql46.addSubPara(tInsuredName);
	mysql46.addSubPara(tInsuredSex);
	mysql46.addSubPara(tBirthday);
	mysql46.addSubPara(tInsuredNo);
	mysql46.addSubPara(fm.IDType.value); 
    mysql46.addSubPara(fm.IDNo.value);
    mysql46.addSubPara(tInsuredNo);    
        arrResult = easyExecSql(mysql46.getString(),1,0);

        if(arrResult==null)
        {
	   alert("该没有与该被保人保人相似的客户,无需校验");
	   return false;
        }

    window.open("../uwgrp/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo,"window1",sFeatures);
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           fm.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           fm.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           fm.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           fm.all('BankAccNo').value="";
           fm.all('BankCode').value="";
           fm.all('AccName').value="";
	}

}
function AutoMoveForNext()
{
	if(fm.AutoMovePerson.value=="定制第二被保险人")
	{
		     //emptyFormElements();
		     param="122";
		     fm.pagename.value="122";
                     fm.AutoMovePerson.value="定制第三被保险人";
                     return false;
	}
	if(fm.AutoMovePerson.value=="定制第三被保险人")
	{
		     //emptyFormElements();
		     param="123";
		     fm.pagename.value="123";
                     fm.AutoMovePerson.value="定制第一被保险人";
                     return false;
	}
		if(fm.AutoMovePerson.value=="定制第一被保险人")
	{
		     //emptyFormElements();
		     param="121";
		     fm.pagename.value="121";
                     fm.AutoMovePerson.value="定制第二被保险人";
                     return false;
	}
}
function noneedhome()
{

    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);

        		break;
        	}
        }
      // var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
      
       var mysql47= new SqlClass();
 	var sqlid47 = "BankContInputSql47";
	mysql47.setResourceName(sqlresourcename);
	mySql47.setSqlId(sqlid47);
	mysql47.addSubPara(insuredno);
	mysql47.addSubPara(fm.ContNo.value);
	mysql47.addSubPara(insuredno); 
      
       arrResult=easyExecSql(mysql47.getString(),1,0);
       try{fm.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{fm.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{fm.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";

 var mysql48= new SqlClass();
 	var sqlid48 = "BankContInputSql48";
	mysql48.setResourceName(sqlresourcename);
	mySql48.setSqlId(sqlid48);
	mysql48.addSubPara(fm.BankAccNo.value);
	
arrResult = easyExecSql(mysql48.getString());
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// 在初始化body时自动效验投保人信息
function InsuredChkNew(){
	//alert("aaa");
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	
	/*
  var sqlstr="select * from ldperson where Name='"+tInsuredName
            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
            +"' and CustomerNo<>'"+tInsuredNo+"'"
            +" union select * from ldperson where IDType = '"+fm.IDType.value
            +"' and IDType is not null and IDNo = '"+fm.IDNo.value
            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
            
    */        
      var mysql49= new SqlClass();
 	var sqlid49 = "BankContInputSql49";
	mysql49.setResourceName(sqlresourcename);
	mySql49.setSqlId(sqlid49);
	mysql49.addSubPara(tInsuredName);
	mysql49.addSubPara(tInsuredSex);
	mysql49.addSubPara(tBirthday);
	mysql49.addSubPara(tInsuredNo);
	mysql49.addSubPara(fm.IDType.value); 
    mysql49.addSubPara(fm.IDNo.value);
    mysql49.addSubPara(tInsuredNo);  
	
  arrResult = easyExecSql(mysql49.getString(),1,0);
  if(arrResult==null)
  {
  	//disabled"投保人效验"按钮
		fm.InsuredChkButton.disabled = true;
//				  return false;
  }
  else
  {
		fm.InsuredChkButton.disabled = "";
					//如果有相同姓名、性别、生日不同客户号的用户显示"投保人校验"按钮
	}
}


//*******************************************************************
//将首期账号赋值到续期账号上
function theSameToFirstAccount(){
	//alert("aasf");
  if(fm.theSameAccount.checked==true){
  	fm.all('SecAppntBankCode').value=fm.all('AppntBankCode').value;
  	fm.all('SecAppntAccName').value=fm.all('AppntAccName').value;
//    alert(fm.all('AppntBankAccNo').value);
//    alert(fm.all('SecAppntBankAccNo').value);
  	fm.all('SecAppntBankAccNo').value=fm.all('AppntBankAccNo').value;
    return;
  }
  if(fm.theSameAccount.checked==false){
//  	fm.all('AppntBankCode').value='';
//  	fm.all('AppntAccName').value='';
//  	fm.all('AppntBankAccNo').value='';
    return;
  }
}

//显示首期账号信息
function displayFirstAccount(){
  fm.all('AppntBankCode').value = arrResult[0][0];
  fm.all('AppntAccName').value = arrResult[0][1];
  fm.all('AppntBankAccNo').value = arrResult[0][2];

  //查询出首期账号后，并同时赋值给续期账号
 	fm.all('SecAppntBankCode').value=fm.all('AppntBankCode').value;
	fm.all('SecAppntAccName').value=fm.all('AppntAccName').value;
 	fm.all('SecAppntBankAccNo').value=fm.all('AppntBankAccNo').value;

}

//强制进入人工核保
function forceUW(){
	//查询是否已经进行选择强制进入人工核保
  var ContNo=fm.all("ContNo").value;
 // var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
 
  var mysql50= new SqlClass();
 	var sqlid50 = "BankContInputSql50";
	mysql50.setResourceName(sqlresourcename);
	mySql50.setSqlId(sqlid50);
	mysql50.addSubPara(ContNo);
	 
  arrResult = easyExecSql(mysql50.getString(),1,0);
  if(arrResult==null){
  	alert("不存在该投保单！");
  }
  else{
    window.open("../uwgrp/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

//查询是否已经添加过投保人
function checkAppnt(){
  var ContNo=fm.all("ContNo").value;
 // var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  
  var mysql51= new SqlClass();
 	var sqlid51 = "BankContInputSql51";
	mysql51.setResourceName(sqlresourcename);
	mySql51.setSqlId(sqlid51);
	mysql51.addSubPara(ContNo);
	
  arrResult = easyExecSql(mysql51.getString(),1,0);
  if(arrResult==null){
  	return false;
  }
  else{
    return true;
  }

}

function exitAuditing(){
  if(confirm("确认该投保单的复核界面？")){
    top.close();
  }
  else{
    return;
  }
}

function exitAuditing2(){
  if(confirm("确认离开该投保单问题件修改界面？")){
    top.close();
  }
  else{
    return;
  }
}


function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择一条任务");
//	      return;
//	}

//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  //var MissionID = MissionID;
  //alert("MissionID="+MissionID);

//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
 // var SubMissionID = fm.all.SubMissionID.value;
  //alert("SubMissionID="+SubMissionID);

//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	//var ActivityID = fm.all.ActivityID.value;
	//alert("ActivityID="+ActivityID);

//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = fm.all.PrtNo2.value;
 // alert("PrtNo="+ fm.all.PrtNo2.value);

	//var NoType = fm.all.NoType.value;
	//alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("投保单号为空！");
		return;
	} 
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType=1";
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");

}

/*********************************************************************
 *  代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showCodeName()
{

	showAppntCodeName();
	showContCodeName();
	showInsuredCodeName();
}
/*********************************************************************
 *  合同代码框汉化
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
 function showContCodeName()
{
 
	showOneCodeName('comcode','ManageCom','ManageComName');  //代码汉化

	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');

	showOneCodeName('sellType','SellType','sellTypeName'); 

}
/*********************************************************************
 *  投保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppntCodeName()
{
	showOneCodeName('vipvalue','AppntVIPValue','AppntVIPFlagname');

	showOneCodeName('IDType','AppntIDType','AppntIDTypeName');

	showOneCodeName('Sex','AppntSex','AppntSexName');

	showOneCodeName('Marriage','AppntMarriage','AppntMarriageName');

	showOneCodeName('NativePlace','AppntNativePlace','AppntNativePlaceName');

	showOneCodeName('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');

	showOneCodeName('LicenseType','AppntLicenseType','AppntLicenseTypeName');

	showOneCodeName('GetAddressNo','AppntAddressNo','AddressNoName');

	showOneCodeName('province','AppntProvince','AppntProvinceName');

	showOneCodeName('city','AppntCity','AppntCityName');

	showOneCodeName('district','AppntDistrict','AppntDistrictName');

	showOneCodeName('paymode','PayMode','FirstPayModeName');

	showOneCodeName('continuepaymode','SecPayMode','PayModeName');

	showOneCodeName('bank','AppntBankCode','FirstBankCodeName');

	showOneCodeName('bank','SecAppntBankCode','AppntBankCodeName');
	showOneCodeName('incomeway','IncomeWay0','IncomeWayName0');
	//
	//showOneCodeName('GetAddressNo','AppntAddressNo','AddressNoName');
}

/*********************************************************************
 *  被保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredCodeName()
{

	showOneCodeName('SequenceNo','SequenceNo','SequenceNoName');

	showOneCodeName('vipvalue','VIPValue1','AppntVIPFlagname1');

	showOneCodeName('Relation','RelationToMainInsured','RelationToMainInsuredName');

	showOneCodeName('Relation','RelationToAppnt','RelationToAppntName');

	showOneCodeName('IDType','IDType','IDTypeName');

	showOneCodeName('Sex','Sex','SexName');

	showOneCodeName('Marriage','Marriage','MarriageName');

	showOneCodeName('OccupationCode','OccupationCode','OccupationCodeName');

	showOneCodeName('NativePlace','NativePlace','NativePlaceName');

	showOneCodeName('LicenseType','LicenseType','LicenseTypeName');

	showOneCodeName('GetAddressNo','InsuredAddressNo','InsuredAddressNoName');

	showOneCodeName('Province','InsuredProvince','InsuredProvinceName'); 

	showOneCodeName('City','InsuredCity','InsuredCityName');

	showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
	showOneCodeName('incomeway','IncomeWay','IncomeWayName');

}

//问题件影像查询
function QuestPicQuery(){
		  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("../uwgrp/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
	
}
//查询业务员告知信息
function getImpartInitInfo()
{
	var tContNo = fm.ContNo.value;

	//var strSQL ="select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";

    var mysql52= new SqlClass();
 	var sqlid52 = "BankContInputSql52";
	mysql52.setResourceName(sqlresourcename);
	mySql52.setSqlId(sqlid52);
	mysql52.addSubPara("1");

	turnPage.queryModal(mysql52.getString(), AgentImpartGrid);
	/*
        var aSQL="SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
		+"FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='"+tContNo+"' "
		+"WHERE a.impartver='05'";
*/
    var mysql53= new SqlClass();
 	var sqlid53 = "BankContInputSql53";
	mysql53.setResourceName(sqlresourcename);
	mySql53.setSqlId(sqlid53);
	mysql53.addSubPara(tContNo);


	turnPage.queryModal(mySql53.getString(),AgentImpartGrid);
	return true;
}

function getLostInfo()
{
	//var strSQL = "select bankcode from lacom where agentcom='"+ fm.all('AgentCom').value+"'";
	//alert("1:"+strSQL);
	
	
	var mysql54= new SqlClass();
 	var sqlid54 = "BankContInputSql54";
	mysql54.setResourceName(sqlresourcename);
	mySql54.setSqlId(sqlid54);
	mysql54.addSubPara(fm.all('AgentCom').value);
	
	arrResult = easyExecSql(mysql54.getString(),1,0);
	
	if(arrResult!==null)
	  { 
	  	//alert(arrResult[0][0]);
      fm.all('BankCode1').value=arrResult[0][0]; 
		}
		
	//strSQL = "select name from laagent where agentcode='" + fm.all('AgentCode').value +"'";
	//alert("2:"+strSQL);
	var mysql55= new SqlClass();
 	var sqlid55 = "BankContInputSql55";
	mysql55.setResourceName(sqlresourcename);
	mySql55.setSqlId(sqlid55);
	mysql55.addSubPara( fm.all('AgentCode').value);
	
	arrResult = easyExecSql(mysql55.getString(),1,0);
	
	if(arrResult!==null)
	  {
      fm.all('AgentName').value=arrResult[0][0];
		}
		
	//strSQL = "select name from labranchgroup where agentgroup='" + fm.all('AgentGroup').value +"'";
	//alert("3:"+strSQL);
	
	var mysql56= new SqlClass();
 	var sqlid56 = "BankContInputSql56";
	mysql56.setResourceName(sqlresourcename);
	mySql56.setSqlId(sqlid56);
	mysql56.addSubPara( fm.all('AgentCode').value);
	
	arrResult = easyExecSql(mysql56.getString(),1,0);
	
	if(arrResult!==null)
	  {
      fm.all('BranchAttr').value=arrResult[0][0];
		}
		
	//strSQL = "select BankAgent,AgentBankCode from lccont where contno='" + fm.all('ProposalContNo').value +"'";
	
	var mysql57= new SqlClass();
 	var sqlid57 = "BankContInputSql57";
	mysql57.setResourceName(sqlresourcename);
	mySql57.setSqlId(sqlid57);
	mysql57.addSubPara( fm.all('AgentCode').value);
	
  arrResult = easyExecSql(mysql57.getString(),1,0);
  
  if (arrResult!==null)
    {
      fm.all('CounterCode').value=arrResult[0][0];
      fm.all('AgentBankCode').value=arrResult[0][1];      
  	}

} 

    	
/**
 * 校验银行账号  090910增加  校验银行账号与银行编码的规则
 * 所有双录并且需判断LoadFlag的页面需加入该方法
 * 双录且不需判断的界面则去掉LoadFlag=='1'的校验
 * */
function checkAccNo(aObject,bObject){
	if(checkBankAccNo(aObject.value,bObject.value)==true){
		if(LoadFlag=='1'){
			confirmSecondInput(bObject,'onblur');
		}
	}else{
		bObject.focus();
	}
}
