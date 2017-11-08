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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕


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

var tSql = ""//"select sysdate from dual";

    var sqlid1="BankContInput1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	tSql=mySql1.getString();

//turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
//turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
var tSysDate = easyExecSql(tSql);
   
//var tSysDate = turnPage.arrDataCacheSet[0][0];    

if(fm.PolAppntDate.value>tSysDate)
{
	alert("投保日期应该小于当前日期");
	return ;
}
///////end add
	if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		if(fm.AppntIDType.value!="9"){
			alert("证件类型和证件号码必须同时填写或不填");
			return false;
		}
		}
		if((document.all('AppntBankCode').value!=null&&document.all('AppntBankCode').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntAccName').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntBankAccNo').value!='')
		)
		{
			fm.PayMode.value='0';
		}
//alert(fm.PayMode.value);
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
//tongmeng 2007-09-10 暂时注释掉此处
  if( verifyInputNB('1') == false ) return false;

	/*如果是符合修改时使用，执行更新操作 20041125 wzw*/
	if(LoadFlag=="3"){
	  updateClick();
	  return;
	}

  if(document.all('PayMode').value=='7'){

  }
  if(document.all('SecPayMode').value=='7'){

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
		getdetailwork();
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();

		if (document.all('ProposalContNo').value == "") {
		  //alert("查询结果只能进行修改操作！");
		  mAction = "";
		} else {
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
		  document.getElementById("fm").submit(); //提交
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
	//tongmeng 2007-10-12 modify
	//界面显示问题
	try { showInfo.close(); } catch(e) {}
	
	if( FlagStr == "Fail" )
	{
		//showInfo.close();
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
		//tongmeng 2009-06-02 modify
		//增加刷新个人工作池
		try
		{
			//fm.WorkFlowFlag.value = "0000001002"
			 var tempFlag = fm.WorkFlowFlag.value ;
			 //alert(tempFlag);
			 if(tempFlag!=null&&tempFlag=='0000001002')
			 {
			 	 top.opener.initQuerySelf();
			 	 top.close();
			 }
		}
		catch(e)
		{
		}
      //top.close();
//	  try { goToPic(2) } catch(e) {}

	}
	mAction = "";
}



function afterSubmit4( FlagStr, content )
{
	//alert("here 1!");
	try { showInfo.close(); } catch(e) {}
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
	//start.若在问题件修改环节修改合同操作成功，则校验修改后的投保人是否存在相同相似客户，提示进行投保人校验。
	//若投保人与被保人的关系为00－本人，则询问是否更新被保人信息。---yeshu,20071220
	else if(fm.fmAction.value=="UPDATE" && (LoadFlag=="3" || LoadFlag=="1"))
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
		
     if(fm.RelationToInsured.value == "00" )
     {
     //	if(confirm("投保人与被保人关系为本人，确认更新被保人信息？"))
     //	{
     var showStr="正在修改被保人信息，请稍后...";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

     		updateInsured();
     //}
    //else
    //	{ 
    //		alert("请修改投保人与被保人关系，否则将造成数据混乱！");
    //		}
    }
//  	var sqlstr = "select * from lcappnt a,ldperson b "
//		+" where a.appntname=b.name and a.appntsex=b.sex and a.appntbirthday=b.birthday"
//		+" and a.appntno='"+fm.AppntNo.value+"' and a.appntno<>b.customerno"
//		+" union "		
//		+" select * from lcappnt a,ldperson b "
//		+" where a.idtype=b.idtype and b.idtype is not null "
//		+" and b.idno=a.idno and b.idno is not null"
//		+" and a.appntno='"+fm.AppntNo.value+"' and a.appntno<>b.customerno";
		var sqlstr ="";
		
		var sqlid2="BankContInput2";
	    var mySql2=new SqlClass();
	    mySql2.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    	mySql2.addSubPara(fm.AppntNo.value);//指定传入的参数
	    mySql2.addSubPara(fm.AppntNo.value);
		sqlstr=mySql2.getString();
		
		var arrResult = easyExecSql(sqlstr,1,0);
        if(arrResult!=null)
        {
				  alert("投保人存在相似客户，请进行投保人校验。");
				}
  	
	}
	//end.---yeshu,20071220
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
	try { showInfo.close(); } catch(e) {}
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
		var iHeight=250;     //弹出窗口的高度; 
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
		document.all('PrtNo').value = prtNo;
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
/*暂时屏蔽，保全部分再作处理	document.all('RiskCode').value = "";

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

    document.all('RiskCode').value = BQRiskCode;
    document.all('RiskCode').className = "readonly";
    document.all('RiskCode').readOnly = true;
    document.all('RiskCode').ondblclick = "";
  }
	document.all('ContNo').value = "";
	document.all('GrpProposalNo').value = "";*/
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
		cContNo = document.all( 'ContNo' ).value;
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
	//alert('11111');
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
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
	if((document.all('AppntBankCode').value!=null&&document.all('AppntBankCode').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntAccName').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntBankAccNo').value!='')
		)
		{
			fm.PayMode.value='0';
		}
	//tongmeng 2007-12-05 modify
	//去掉此处
	/*
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
  {
    alert("投保人客户号为空，不能有地址号码");
    return false;
  }*/
  //tongmeng 2007-09-11 modify

  //增加【初审日期】的校验 --2010-03-18 --hanbin 
  //增加 在ServiceSpecification  描述之后开始校验 hanbin 2010-04-29
	//2010-5-5 去掉初审日期的校验
//  sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
//  var arrResult2 = easyExecSql(sql);
//  if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolAppntDate").value)>=0))
//  {
//	  if(fm.FirstTrialDate.value < fm.PolAppntDate.value)
//	  {
//	  	 alert("初审日期不得早于投保申请日期！");
//	  	 fm.FirstTrialDate.value = "";
//	  	 return false;
//	  }
//	  strSql = "select makedate from es_doc_main a  where a.doccode = '"+fm.ProposalContNo.value+"' and a.busstype = 'TB' and a.subtype = 'UA001'"; 
//	  var scanDate = easyExecSql(strSql);
//	  if(fm.FirstTrialDate.value >  scanDate)
//	  {
//	  	 alert("初审日期不得晚于扫描日期！");
//	  	 fm.FirstTrialDate.value = "";
//	  	 return false;
//	  }
//  }
  
  if( verifyInputNB('1') == false ) return false;
	var tGrpProposalNo = "";
	tGrpProposalNo = document.all( 'ProposalContNo' ).value;
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
			getdetailwork();
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			mAction = "UPDATE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			document.getElementById("fm").submit(); //提交
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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	var tGrpProposalNo = "";
	var tProposalContNo = "";
	if(LoadFlag==1)
	{
		tGrpProposalNo = document.all( 'GrpContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "请先做投保单查询操作，再进行删除!" );
	else
	{
		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			document.getElementById("fm").submit(); //提交
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

/*************************************************************************************
在录单保存时，系统生成的保费与收银录入的保费进行校验，
如果收银录入的保费与系统生成的保费不符，系统要进行提示，但仍可保存,流程仍可流转
所需参数：合同号<投保单号>;     返回:true or false
*************************************************************************************/
function checkpayfee(ContNo)
{
	var tContNo=ContNo;
	var tTempFee="";//收银录入的保费
	var tPremFee="";//系统生成的保费
	//查询收银录入的保费
//	var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//		+" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
   var tempfeeSQL="";
   
   var sqlid3="BankContInput3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql3.setSqlId(sqlid3);//指定使用的Sql的id
    mySql3.addSubPara(tContNo);
	tempfeeSQL=mySql3.getString();
   
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//查询系统生成的保费
//	var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//		+" and contno=(select contno from lccont where prtno= '"+tContNo+"')";	
	var premfeeSQL="";	
	var sqlid4="BankContInput4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql4.setSqlId(sqlid4);//指定使用的Sql的id
    mySql4.addSubPara(tContNo);
	premfeeSQL=mySql4.getString();
		
	var PremFeeArr=easyExecSql(premfeeSQL);	
	tPremFee=PremFeeArr[0][0];	
	if(PremFeeArr!=null)
	{
		tPremFee=PremFeeArr[0][0];
		if(tPremFee==null || tPremFee=="" || tPremFee=="null")
		{
		alert("查询该投保单下险种信息及生成保费数据失败！！！");
		return false;	
		}
	}
	//比较“查询收银录入的保费” 和 “查询系统生成的保费”是否相等，如不相等则弹出信息提示
	if(tTempFee!="" && tPremFee!="" && tTempFee!="null" && tPremFee!="null" && (tTempFee!=tPremFee))
	{
		var ErrInfo="注意：收银录入的保费["+tTempFee+"]与系统生成的保费["+tPremFee+"]不等。\n";
		ErrInfo=ErrInfo+"确定需要保存吗？确需保存请按“确定”，否则按“取消”。";
		if(confirm(ErrInfo)==false)
		{
		   return false;	
		}
	}
	return true;
}
/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm(wFlag)
{   
    //校验是否还有其他问题件
    if(checkOtherQuest()==false)
      return false;
	if (wFlag ==1 ) //录入完毕确认
	{
    //alert(ScanFlag);
    //var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		var tStr="";
		
		var sqlid5="BankContInput5";
	    var mySql5=new SqlClass();
		mySql5.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	    mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	    mySql5.addSubPara(fm.ContNo.value);
		tSql=mySql5.getString();
		
		turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
		if (turnPage.strQueryResult) {
		  alert("该合同已经做过保存！");
		  return;
		}
		if(document.all('ProposalContNo').value == "")
	  {
	    alert("合同信息未保存,不容许您进行 [录入完毕] 确认！");
	   	return;
	  }
	//添加录单费率错误的校验<收银录入的保费与系统生成的保费不符，系统要进行提示，但仍可保存>
	if(checkpayfee(document.all('ProposalContNo').value)==false)
	{
	return false;	
	}
 
   //alert("BankContInput.js_specScanFlag: "+specScanFlag);
   if(ScanFlag=="1" && specScanFlag != "bposcan"){  //正常有扫描件录入
     fm.WorkFlowFlag.value = "0000001099";
    }
    else if(ScanFlag=="1" && specScanFlag == "bposcan"){   //特殊投保单录入
     fm.WorkFlowFlag.value = "0000001094";
    }
    else{
		  fm.WorkFlowFlag.value = "0000001098";
	  }
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;			//录入完毕
  }
  else if (wFlag ==2)//复核完毕确认
  {
  	if(document.all('ProposalContNo').value == "")
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
		  //09-06-09 增加投被保人关系是本人而投被保人信息是否一致的校验
		  if(fm.RelationToInsured.value == "00" ){
			  if(fm.AppntNo.value!=fm.InsuredNo.value){
				  alert("投保人"+fm.AppntNo.value+" 与被保人"+fm.InsuredNo.value+"信息不一致，请确认投保人与被保人关系！");
				  return false;
			  }
		  }
  	if(document.all('ProposalContNo').value == "")
	   {
	   	  alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
	   	  return;
	   }
	  //tongmeng 2008-01-18 add
	  //增加所有表单的数据做校验
	  if(!verifyInputNB('1'))
	  {
	  	return false;
	  	}
	  if(!verifyInputNB('2'))
	  {
	  	return false;
	  }
		fm.WorkFlowFlag.value = "0000001002";					//复核修改完毕
		//tongmeng 2007-11-26 add 
		//增加对是否所有问题件都回复的教验
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else if(wFlag == 4)
	{
		 if(document.all('ProposalContNo').value == "")
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
//	alert("没有校验住！");return false;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "./InputConfirm.jsp";
  document.getElementById("fm").submit(); //提交
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

function checkOtherQuest(){
   if(ActivityID=='0000001002')
   {
   	//var tSql ="select missionprop9 from lwmission where activityid = '0000001002' and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' and  processid = '0000000003' ";
	var tSql="";
	var sqlid6="BankContInput6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql6.setSqlId(sqlid6);//指定使用的Sql的id
    mySql6.addSubPara(prtNo);
	mySql6.addSubPara(operator);
	tSql=mySql6.getString();
	
  	 var tOtherQuestFlag = easyExecSql(tSql);
   	if(tOtherQuestFlag !=""&& tOtherQuestFlag!=null)
  	 {
   	   if(tOtherQuestFlag !="1")
   	   {
  	       //有未回复的问题件
  	       alert("还有未回复的问题件，请将所有的的问题件回复后再进行[问题件修改完毕]操作！");
 	        return false;
  	    }
 	  }else{
  	      alert("没有能够查询到是否还有未回复的问题件！请确认后再进行[问题件修改完毕]操作");
 	      if(confirm("是否确认此操作？")==false)
  	      {
  	        return false;
 	      }
	    }
   }
   return true;
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
    try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };
    try{mSwitch.addVar('AppntName','',fm.AppntName.value);}catch(ex){};
    try{mSwitch.addVar('AppntSex','',fm.AppntSex.value);}catch(ex){};
    try{mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDType','',fm.AppntIDType.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value);}catch(ex){};
    try{mSwitch.addVar('AppIDPeriodOfValidity','',fm.AppIDPeriodOfValidity.value);}catch(ex){};

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
    try{mSwitch.addVar('IDPeriodOfValidity','',fm.IDPeriodOfValidity.value);}catch(ex){};

   //新的数据处理
try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
//tongmeng 2007-12-18 add
try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };
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
try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
try { mSwitch.addVar('CompanyAddress','',fm.CompanyAddress.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpZipCode','',fm.AppntGrpZipCode.value); } catch(ex) { };
try { mSwitch.addVar('AppntGrpFax','',fm.AppntGrpFax.value); } catch(ex) { };
try { mSwitch.addVar('AppntPostalAddress','',fm.AppntPostalAddress.value); } catch(ex) { };
try { mSwitch.addVar('AppntZipCode','',fm.AppntZipCode.value); } catch(ex) { };
try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
try { mSwitch.addVar('AppntMobile','',fm.AppntMobile.value); } catch(ex) { };
try { mSwitch.addVar('AppntFax','',fm.AppntFax.value); } catch(ex) { };
try { mSwitch.addVar('AppntEMail','',fm.AppntEMail.value); } catch(ex) { };
try { mSwitch.addVar('AppntBankAccNo','',document.all('AppntBankAccNo').value); } catch(ex) { };
try { mSwitch.addVar('AppntBankCode','',document.all('AppntBankCode').value); } catch(ex) { };
try { mSwitch.addVar('AppntAccName','',document.all('AppntAccName').value); } catch(ex) { };
try { mSwitch.addVar('AppIDPeriodOfValidity','',fm.AppIDPeriodOfValidity.value); } catch(ex) { };


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
   try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };
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
   try { mSwitch.deleteVar('AppIDPeriodOfValidity'); } catch(ex) { };

    //新的删除数据处理
try { mSwitch.deleteVar  ('AppntNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('RelationToInsured'); } catch(ex) { };
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
try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppIDPeriodOfValidity'); } catch(ex) { };
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
			document.all( 'ContNo' ).value = arrQueryResult[0][0];
			
			var sqlid7="BankContInput7";
			var mySql7=new SqlClass();
			mySql7.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
		    mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		    mySql7.addSubPara(arrQueryResult[0][0]);
			var tSql=mySql7.getString();
			
			//arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(tSql, 1, 0);

			if (arrResult == null) {
			  alert("未查到投保单信息");
			} else {
			   displayLCContPol(arrResult[0]);
			}
		}

		if( mOperate == 2 )	{		// 投保人信息
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
			var sqlid8="BankContInput8";
			var mySql8=new SqlClass();
			mySql8.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
		    mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		    mySql8.addSubPara(arrQueryResult[0][0]);
			var tSql=mySql8.getString();
			
			//arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(tSql, 1, 0);
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
	
//	detailInitServer();
//	return;
	
	//alert('1');
  try{

    if(PrtNo==null||PrtNo=='') return;

    //查询首期账号
    //alert("PrtNo=="+PrtNo);
//    var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
//               + "where trim(a.TempFeeNo)=trim(b.TempFeeNo) and a.PayMode='4' and b.TempFeeType='1' "
//               + "and b.OtherNo='"+PrtNo+"'";
    var accSQL="";
	var sqlid9="BankContInput9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql9.setSqlId(sqlid9);//指定使用的Sql的id
    mySql9.addSubPara(PrtNo);
	accSQL=mySql9.getString();
    arrResult = easyExecSql(accSQL,1,0);

    if(arrResult==null){
    	//return;
    	//默认为续期为银行转账
//    	alert("aaa");
//      document.all('PayMode').value="3";
    }
    else{
      displayFirstAccount();
      //如果首期交费方式为银行转账，续期同样为银行转账
      document.all('PayMode').value="";
    }
//alert('10');
    var accSQL10="";
    var sqlid10="BankContInput10";
	var mySql10=new SqlClass();
	mySql10.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql10.setSqlId(sqlid10);//指定使用的Sql的id
    mySql10.addSubPara(PrtNo);
	accSQL10=mySql10.getString();
	
    //arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
    arrResult=easyExecSql(accSQL10,1,0);
	
    if(arrResult==null){
      alert(未得到投保单信息);
      return;
    }
    else{
      displayLCCont();       //显示投保单详细内容
    }
//alert('11');
    var accSQL11="";
    var sqlid11="BankContInput11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql11.setSqlId(sqlid11);//指定使用的Sql的id
    mySql11.addSubPara(arrResult[0][19] );
	accSQL11=mySql11.getString();
    //arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);
	arrResult = easyExecSql(accSQL11, 1, 0);

    if (arrResult == null) {
      alert("未查到投保人客户层信息");
    }
    else{
      //显示投保人信息
      displayAppnt(arrResult[0]);
    }

    var accSQL12="";
    var sqlid12="BankContInput12";
	var mySql12=new SqlClass();
	mySql12.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql12.setSqlId(sqlid12);//指定使用的Sql的id
    mySql12.addSubPara(PrtNo);
	accSQL12=mySql12.getString();

    //arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
	arrResult=easyExecSql(accSQL12,1,0);

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
    var strSQL0="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
    var strSQL1="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    
    var sqlid13="BankContInput13";
	var mySql13=new SqlClass();
	mySql13.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql13.setSqlId(sqlid13);//指定使用的Sql的id
    mySql13.addSubPara(tCustomerNo);
	mySql13.addSubPara(tContNo);
	strSQL0=mySql13.getString();
	
    var sqlid14="BankContInput14";
	var mySql14=new SqlClass();
	mySql14.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql14.setSqlId(sqlid14);//指定使用的Sql的id
    mySql14.addSubPara(tCustomerNo);
	mySql14.addSubPara(tContNo);
	strSQL1=mySql14.getString();
	
	arrResult = easyExecSql(strSQL0,1,0);
    arrResult1 = easyExecSql(strSQL1,1,0);


	    try{document.all('Income0').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};

    var strSQL1="";//"select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver in ('01','02') and impartcode<>'001'";
    var sqlid15="BankContInput15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql15.setSqlId(sqlid15);//指定使用的Sql的id
    mySql15.addSubPara(tCustomerNo);
	mySql15.addSubPara(tContNo);
	strSQL1=mySql15.getString();
    turnPage.strQueryResult = easyQueryVer3(strSQL1, 1, 0, 1);

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


   var aSQL="";//"select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
   var sqlid16="BankContInput16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql16.setSqlId(sqlid16);//指定使用的Sql的id
    mySql16.addSubPara(tCustomerNo);
	mySql16.addSubPara(tContNo);
	aSQL=mySql16.getString();
	
   turnPage.queryModal(aSQL, AgentImpartGrid);


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
	try{document.all('AppntBankAccNo').value= arrResult[0][24]; }catch(ex){};
	try{document.all('AppntBankCode').value= arrResult[0][23]; }catch(ex){};
	try{document.all('AppntAccName').value= arrResult[0][25]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayCustomer()
{
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAddress()
{
try{document.all('AppntAddressNo').value= arrResult[0][0];}catch(ex){};
try{document.all('AppntPostalAddress').value= arrResult[0][1];}catch(ex){};
try{document.all('AppntZipCode').value= arrResult[0][2];}catch(ex){};
try{document.all('AppntPhone').value= arrResult[0][3];}catch(ex){};
try{document.all('AppntMobile').value= arrResult[0][4];}catch(ex){};
try{document.all('AppntEMail').value= arrResult[0][5];}catch(ex){};
//try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
try{document.all('AppntPhone').value= arrResult[0][6];}catch(ex){};
try{document.all('CompanyAddress').value= arrResult[0][7];}catch(ex){};
try{document.all('AppntGrpZipCode').value= arrResult[0][8];}catch(ex){};
/*
  try{document.all('AppntPostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntPhone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntMobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('AppntEMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('AppntGrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntGrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('AppntGrpZipCode').value= arrResult[0][11]; }catch(ex){};
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
	//alert("111:"+arrResult[0][43]);
  try{document.all('AppntGrpContNo').value= arrResult[0][0];}catch(ex){};
  try{document.all('AppntContNo').value= arrResult[0][1];}catch(ex){};
  try{document.all('AppntPrtNo').value= arrResult[0][2];}catch(ex){};
  try{document.all('AppntNo').value= arrResult[0][3];}catch(ex){};
  try{document.all('AppntGrade').value= arrResult[0][4];}catch(ex){};
  try{document.all('AppntName').value= arrResult[0][5];}catch(ex){};
  try{document.all('AppntSex').value= arrResult[0][6];}catch(ex){};
  try{document.all('AppntBirthday').value= arrResult[0][7];}catch(ex){};
  try{document.all('AppntType').value= arrResult[0][8];}catch(ex){};
  try{document.all('AppntAddressNo').value= arrResult[0][9]; }catch(ex){};
  try{document.all('AppntIDType').value= arrResult[0][10]; }catch(ex){};
  try{document.all('AppntIDNo').value= arrResult[0][11]; }catch(ex){};
  try{document.all('AppntNativePlace').value= arrResult[0][12];}catch(ex){};
  try{document.all('AppntNationality').value= arrResult[0][13];}catch(ex){};
  try{document.all('AppntRgtAddress').value= arrResult[0][14];}catch(ex){};
  try{document.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};
  try{document.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};
  try{document.all('AppntHealth').value= arrResult[0][17];}catch(ex){};
  try{document.all('AppntStature').value= arrResult[0][18];}catch(ex){};
  try{document.all('AppntAvoirdupois').value= arrResult[0][19];}catch(ex){};
  try{document.all('AppntDegree').value= arrResult[0][20];}catch(ex){};
  try{document.all('AppntCreditGrade').value= arrResult[0][21];}catch(ex){};
  //try{document.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};
  //try{document.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};
  //try{document.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};
  try{document.all('AppntJoinCompanyDate').value= arrResult[0][25];}catch(ex){};
  try{document.all('AppntStartWorkDate').value= arrResult[0][26];}catch(ex){};
  try{document.all('AppntPosition').value= arrResult[0][27];}catch(ex){};
  try{document.all('AppntSalary').value= arrResult[0][28]; }catch(ex){};
  try{document.all('AppntOccupationType').value= arrResult[0][29];}catch(ex){};
  try{document.all('AppntOccupationCode').value= arrResult[0][30];}catch(ex){};
  try{document.all('AppntWorkType').value= arrResult[0][31]; }catch(ex){};
  try{document.all('AppntPluralityType').value= arrResult[0][32];}catch(ex){};
  try{document.all('AppntSmokeFlag').value= arrResult[0][33];}catch(ex){};
  try{document.all('AppntOperator').value= arrResult[0][34];}catch(ex){};
  try{document.all('AppntManageCom').value= arrResult[0][35];}catch(ex){};
  try{document.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
  try{document.all('AppntMakeTime').value= arrResult[0][37]; }catch(ex){};
  try{document.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
  try{document.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
  try{document.all('RelationToInsured').value= arrResult[0][43];}catch(ex){};
  try{document.all('AppIDPeriodOfValidity').value= arrResult[0][45];}catch(ex){};
 
}
/*********************************************************************
 *  把查询返回的投保人数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
	//alert("here in 928 col"+arrResult[0][44]);
try{document.all('AppntNo').value= arrResult[0][0]; }catch(ex){};
try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
try{document.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
try{document.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
try{document.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
try{document.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
try{document.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
try{document.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
try{document.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
try{document.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
try{document.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
try{document.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
try{document.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
try{document.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
try{document.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
try{document.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
try{document.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
try{document.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
try{document.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
try{document.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
try{document.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
try{document.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
try{document.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{document.all('AppntVIPValue').value= arrResult[0][35];}catch(ex){};
try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
try{document.all('AppntGrpName').value= arrResult[0][41];}catch(ex){};
//alert(document.all('AppntGrpName').value);
try{document.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};
try{document.all('AppIDPeriodOfValidity').value= arrResult[0][47];}catch(ex){};
//tongmeng 2007-12-06 
//增加投保人关系字段
//try{document.all('RelationToInsured').value= arrResult[0][44];}catch(ex){};

//顺便将投保人地址信息等进行初始化
try{document.all('AppntPostalAddress').value= "";}catch(ex){}; 
try{document.all('AppntPostalAddress').value= "";}catch(ex){};
try{document.all('AppntZipCode').value= "";}catch(ex){};
try{document.all('AppntPhone').value= "";}catch(ex){};
try{document.all('AppntFax').value= "";}catch(ex){};
try{document.all('AppntMobile').value= "";}catch(ex){};
try{document.all('AppntEMail').value= "";}catch(ex){};
//try{document.all('AppntGrpName').value= "";}catch(ex){};
try{document.all('AppntHomeAddress').value= "";}catch(ex){};
try{document.all('AppntHomeZipCode').value= "";}catch(ex){};
try{document.all('AppntHomePhone').value= "";}catch(ex){};
try{document.all('AppntHomeFax').value= "";}catch(ex){};
try{document.all('AppntPhone').value= "";}catch(ex){};
try{document.all('CompanyAddress').value= "";}catch(ex){};
try{document.all('AppntGrpZipCode').value= "";}catch(ex){};
try{document.all('AppntGrpFax').value= "";}catch(ex){};

}
/**
 *投保单详细内容显示
 */
function displayLCCont() {
	  //alert("aaa");
    try { document.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };
    try { document.all('ContNo').value = arrResult[0][1]; } catch(ex) { };
    try { document.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };
    try { document.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };
    try { document.all('ContType').value = arrResult[0][4]; } catch(ex) { };
    try { document.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };
    try { document.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };
    try { document.all('PolType').value = arrResult[0][7]; } catch(ex) { };
    try { document.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };
    try { document.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };
    try { document.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };
    try { document.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };
    try { document.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { document.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { }; 
    try { document.all('AgentCode1').value = arrResult[0][14]; } catch(ex) { };
    //alert("bb");
//    try { document.all('AgentType').value = arrResult[0][15]; } catch(ex) { }; 
    try { document.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };
    try { document.all('Handler').value = arrResult[0][17]; } catch(ex) { };
    try { document.all('Password').value = arrResult[0][18]; } catch(ex) { };
    try { document.all('AppntNo').value = arrResult[0][19]; } catch(ex) { };
    try { document.all('AppntName').value = arrResult[0][20]; } catch(ex) { };
    try { document.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };
    try { document.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };
    try { document.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };
    try { document.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };
    try { document.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };
    try { document.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };
    try { document.all('InsuredSex').value = arrResult[0][27]; } catch(ex) { };
    try { document.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };
    try { document.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };
    try { document.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };
    try { document.all('PayIntv').value = arrResult[0][31]; } catch(ex) { };
    try { document.all('SecPayMode').value = arrResult[0][32]; } catch(ex) { };
    try { document.all('PayLocation').value = arrResult[0][33]; } catch(ex) { };
    try { document.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };
    try { document.all('OutPayFlag').value = arrResult[0][35]; } catch(ex) { };
    try { document.all('GetPolMode').value = arrResult[0][36]; } catch(ex) { };
    try { document.all('SignCom').value = arrResult[0][37]; } catch(ex) { };
    try { document.all('SignDate').value = arrResult[0][38]; } catch(ex) { };
    try { document.all('SignTime').value = arrResult[0][39]; } catch(ex) { };
    try { document.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };
    try { document.all('SecAppntBankCode').value = arrResult[0][41]; } catch(ex) { };
    try { document.all('SecAppntBankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { document.all('SecAppntAccName').value = arrResult[0][43]; } catch(ex) { };
    try { document.all('PrintCount').value = arrResult[0][44]; } catch(ex) { };
    try { document.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };
    try { document.all('Lang').value = arrResult[0][46]; } catch(ex) { };
    try { document.all('Currency').value = arrResult[0][47]; } catch(ex) { };
    try { document.all('Remark').value = arrResult[0][48]; } catch(ex) { };
    try { document.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };
    try { document.all('Mult').value = arrResult[0][50]; } catch(ex) { };
    try { document.all('Prem').value = arrResult[0][51]; } catch(ex) { };
    try { document.all('Amnt').value = arrResult[0][52]; } catch(ex) { };
    try { document.all('SumPrem').value = arrResult[0][53]; } catch(ex) { };
    try { document.all('Dif').value = arrResult[0][54]; } catch(ex) { };
    try { document.all('PaytoDate').value = arrResult[0][55]; } catch(ex) { };
    try { document.all('FirstPayDate').value = arrResult[0][56]; } catch(ex) { };
    try { document.all('CValiDate').value = arrResult[0][57]; } catch(ex) { };
    try { document.all('InputOperator ').value = arrResult[0][58]; } catch(ex) { };
    try { document.all('InputDate').value = arrResult[0][59]; } catch(ex) { };
    try { document.all('InputTime').value = arrResult[0][60]; } catch(ex) { };
    try { document.all('ApproveFlag').value = arrResult[0][61]; } catch(ex) { };
    try { document.all('ApproveCode').value = arrResult[0][62]; } catch(ex) { };
    try { document.all('ApproveDate').value = arrResult[0][63]; } catch(ex) { };
    try { document.all('ApproveTime').value = arrResult[0][64]; } catch(ex) { };
    try { document.all('UWFlag').value = arrResult[0][65]; } catch(ex) { };
    try { document.all('UWOperator').value = arrResult[0][66]; } catch(ex) { };
    try { document.all('UWDate').value = arrResult[0][67]; } catch(ex) { };
    try { document.all('UWTime').value = arrResult[0][68]; } catch(ex) { };
    try { document.all('AppFlag').value = arrResult[0][69]; } catch(ex) { };
    try { document.all('PolAppntDate').value = arrResult[0][70]; } catch(ex) { };
    try { document.all('GetPolDate').value = arrResult[0][71]; } catch(ex) { };
    try { document.all('GetPolTime').value = arrResult[0][72]; } catch(ex) { };
    try { document.all('CustomGetPolDate').value = arrResult[0][73]; } catch(ex) { };
    try { document.all('State').value = arrResult[0][74]; } catch(ex) { };
    try { document.all('Operator').value = arrResult[0][75]; } catch(ex) { };
    try { document.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };
    try { document.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };
    try { document.all('ModifyDate').value = arrResult[0][78]; } catch(ex) { };
    try { document.all('ModifyTime').value = arrResult[0][79]; } catch(ex) { };
    try { document.all('SellType').value = arrResult[0][87]; } catch(ex) { };
    try { document.all('AppntBankCode').value = arrResult[0][41]; } catch(ex) { };
    try { document.all('AppntBankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { document.all('AppntAccName').value = arrResult[0][43]; } catch(ex) { };
    try { document.all('PayMode').value = arrResult[0][93]; } catch(ex) { };
    //tongmeng 2009-04-15 Add
		//增加初审签名
		try { document.all('SignName').value = arrResult[0][100]; } catch(ex) { };
		
	//增加【初审日期】 2010-03-18 -hanbin 
	
	try { document.all('FirstTrialDate').value = arrResult[0][81]; } catch(ex) { };		
	try { document.all('XQremindFlag').value = arrResult[0][108]; } catch(ex) { };	
	showOneCodeName('XQremindFlag','XQremindFlag','XQremindFlagName');

}
//使得从该窗口弹出的窗口能够聚焦
/*function myonfocus()
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

*/

//投保人客户号查询按扭事件
function queryAppntNo() {

	if (document.all("AppntNo").value == "" && loadFlag == "1")
	{
    showAppnt1();
  }
  else if (loadFlag != "1" && loadFlag != "2")
  {
     alert("只能在投保单录入时进行操作！");
  }
  else
  {
  	var sql17="";
  	var sqlid17="BankContInput17";
	var mySql17=new SqlClass();
	mySql17.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql17.setSqlId(sqlid17);//指定使用的Sql的id
    mySql17.addSubPara(fm.AppntNo.value);
	sql17=mySql17.getString();
	
   // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
    arrResult = easyExecSql(sql17, 1, 0);
    if (arrResult == null) {
      alert("未查到投保人信息");
      displayAppnt(new Array());
      emptyUndefined();
    }
    else
    {
      displayAppnt(arrResult[0]);
      getdetailaddress();
      showCodeName();
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
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	/*if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}*/
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=3","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
    /*if(cAgentCode.length!=8){
    	return;
    }*/
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
   var sql18="";
  	var sqlid18="BankContInput18";
	var mySql18=new SqlClass();
	mySql18.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
    mySql18.setSqlId(sqlid18);//指定使用的Sql的id
    mySql18.addSubPara(cAgentCode);
	sql18=mySql18.getString();
    var strSQL=sql18;
   	//var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' ";//and a.managecom='"+document.all("ManageCom").value+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
//      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][3]+"]");
    }
    else
    {
      fm.AgentCode.value ="";
    	fm.BranchAttr.value ="";
    	fm.AgentGroup.value ="";
  	  fm.AgentName.value ="";
     alert("编码为:["+cAgentCode+"]的业务员不存在，或者不在该管理机构下，或者已经离职， 请确认!");
    }
	}
	initManageCom();
}

//查询管理机构
function initManageCom()
{	
	fm.ManageCom.value = '';
	fm.ManageComName.value = '';
	
			if(fm.AgentCom.value != null && fm.AgentCom.value != "")
			{
				var sql19="";
			  	var sqlid19="BankContInput19";
				var mySql19=new SqlClass();
				mySql19.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
			    mySql19.setSqlId(sqlid19);//指定使用的Sql的id
			    mySql19.addSubPara(fm.AgentCom.value);
				sql19=mySql19.getString();
				var strSql=sql19;
				//var strSql = "select ManageCom,(select name from ldcom where comcode=managecom),branchtype from LACom where AgentCom='" + fm.AgentCom.value +"'";
			    var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.ManageCom.value = arrResult[0][0];
			      fm.ManageComName.value = arrResult[0][1];
			      if(arrResult[0][2]!=null &&　arrResult[0][2]=='6')
			      {
			      	var cManageCom = '';
			      	if(document.all('PrtNo').value != "")
					{
						var sql20="";
					  	var sqlid20="BankContInput20";
						var mySql20=new SqlClass();
						mySql20.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
					    mySql20.setSqlId(sqlid20);//指定使用的Sql的id
					    mySql20.addSubPara(fm.PrtNo.value);
						sql20=mySql20.getString();
					    var sql=sql20;
						//var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "' and subtype='UA001'";
						cManageCom = easyExecSql(sql);	
					}
			      	if(cManageCom != null && cManageCom[0][0] != "")
			      	{
			      		var length = cManageCom[0][0].length;
			      		for(var i=length;i<8;i++)
			      		{
			      			cManageCom[0][0] = cManageCom[0][0] +'0';
			      		}
			      		fm.ManageCom.value = cManageCom[0][0];
						
						var sql21="";
					  	var sqlid21="BankContInput21";
						var mySql21=new SqlClass();
						mySql21.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
					    mySql21.setSqlId(sqlid21);//指定使用的Sql的id
					    mySql21.addSubPara(cManageCom[0][0]);
						sql21=mySql21.getString();
						var sql =sql21;
						
			      		//var sql = "select name from ldcom where comcode='"+cManageCom[0][0]+"'";
						var arrResult1 = easyExecSql(sql);	
						if(arrResult1 != null)
						{							
			     			fm.ManageComName.value = arrResult1[0][0];
						}						
			      	}			      	
			      }
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
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery1(arrQueryResult)
{
      //document.all("Donextbutton1").style.display="";
      //document.all("Donextbutton2").style.display="none";
      //document.all("Donextbutton3").style.display="";
      //document.all("butBack").style.display="none";
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
  	  initManageCom();
      //fm.AgentManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}

//返回查询结果，赋给多业务员multline
function afterQuery3(arrResult)
{
  if(arrResult!=null)
  {
		document.all( tField ).all( 'MultiAgentGrid1').value = arrResult[0][0];
		document.all( tField ).all( 'MultiAgentGrid2').value = arrResult[0][3];
		document.all( tField ).all( 'MultiAgentGrid3').value = arrResult[0][2];
		document.all( tField ).all( 'MultiAgentGrid4').value = arrResult[0][8];
		document.all( tField ).all( 'MultiAgentGrid6').value = arrResult[0][1];
		document.all( tField ).all( 'MultiAgentGrid7').value = arrResult[0][6];
  }

}



function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码
//	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
	var sql22="";
	var sqlid22="BankContInput22";
	var mySql22=new SqlClass();
	mySql22.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	mySql22.setSqlId(sqlid22);//指定使用的Sql的id
	mySql22.addSubPara(cAgentCode);
	mySql22.addSubPara(fm.ManageCom.value);
	sql22=mySql22.getString();
	var strSQL =sql22;
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03' and a.AgentCode='"+cAgentCode+"' and a.managecom='"+document.all("ManageCom").value+"'";
    var arrResult = easyExecSql(strSQL);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][3]+"]");
    }
    else{
     fm.AgentCode.value = "";
    	fm.BranchAttr.value = "";
    	fm.AgentGroup.value = "";
  	  fm.AgentName.value = "";
     alert("编码为:["+cAgentCode+"]的代理人不存在，或者不在该管理机构下，或者已经离职，请确认!");
     }
	}
}
function getdetail()
{
	//alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
	var sql23="";
	var sqlid23="BankContInput23";
	var mySql23=new SqlClass();
	mySql23.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	mySql23.setSqlId(sqlid23);//指定使用的Sql的id
	mySql23.addSubPara(fm.AppntBankAccNo.value);
	mySql23.addSubPara(fm.AppntNo.value);
	sql23=mySql23.getString();
	var strSql =sql23;
	
	//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"' and customerno='"+fm.AppntNo.value+"'";
	var arrResult = easyExecSql(strSql);
	if (arrResult != null)
	{
		fm.AppntBankCode.value = arrResult[0][0];
		fm.AppntAccName.value = arrResult[0][1];
	}
	else
	{
	     //fm.AppntBankCode.value = '';
	     //fm.AppntAccName.value = '';
	}
}
function getdetailwork()
{
	var sql24="";
	var sqlid24="BankContInput24";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	mySql24.setSqlId(sqlid24);//指定使用的Sql的id
	mySql24.addSubPara(fm.AppntOccupationCode.value);
	sql24=mySql24.getString();
	var strSql =sql24;
	
  //var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
  var arrResult = easyExecSql(strSql);
  if (arrResult != null) 
  {
    fm.AppntOccupationType.value = arrResult[0][0];
	showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
  }
  else
  {
    fm.AppntOccupationType.value ="";
    fm.AppntOccupationTypeName.value ="";
  }
}

/********************
**
** 查询投保人地址信息
**
*********************/
function getdetailaddress(){
	
	var sql25="";
	var sqlid25="BankContInput25";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	mySql25.setSqlId(sqlid25);//指定使用的Sql的id
	mySql25.addSubPara(fm.AppntAddressNo.value);
	mySql25.addSubPara(fm.AppntNo.value);
	sql25=mySql25.getString();
	var strSQL =sql25;
	
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        arrResult=easyExecSql(strSQL);
  //alert("arrResult[0][27]=="+arrResult[0][27]);
	displayDetailAddress(arrResult);
}

function displayDetailAddress(arrResult)
{
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('AddressNoName').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   try{
   		document.all('AppntPhone').value= arrResult[0][4];
   		if(document.all('AppntPhone').value==null||document.all('AppntPhone').value=="")
   		{
   			document.all('AppntPhone').value=arrResult[0][4];
   		}
   	}catch(ex){};
   try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
   try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
   try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{document.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
   try{document.all('AppntCity').value= arrResult[0][29];}catch(ex){};
   try{document.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
   try{document.all('AppntGrpName').value= arrResult[0][27];}catch(ex){};
   
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
    	fm.AppntPhone.value="";
    	}
		
	var sql26="";
	var sqlid26="BankContInput26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	mySql26.setSqlId(sqlid26);//指定使用的Sql的id
	mySql26.addSubPara(fm.AppntNo.value);
	sql26=mySql26.getString();
	strsql =sql26;
		
    //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
    document.all("AppntAddressNo").CodeData=tCodeData;
}
function afterCodeSelect( cCodeName, Field )
{
 //alert("afdasdf");
 if(cCodeName=="GetAddressNo"){
 	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        arrResult=easyExecSql(strSQL);
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('AppntAddressNoName').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   //try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   try{document.all('AppntPhone').value= arrResult[0][12];}catch(ex){};
   try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
   try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
   try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{document.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
   try{document.all('AppntCity').value= arrResult[0][29];}catch(ex){};
   try{document.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
   try{document.all('AppntGrpName').value= arrResult[0][27];}catch(ex){};
//	showOneCodeName('province','AppntProvince','AppntProvinceName');
//	showOneCodeName('city','AppntCity','AppntCityName');
//	showOneCodeName('district','AppntDistrict','AppntDistrictName');
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
   return;
 }
  if(cCodeName=="GetInsuredAddressNo"){
 	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
        arrResult=easyExecSql(strSQL);
   try{document.all('InsuredAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('InsuredAddressNoName').value= arrResult[0][2];}catch(ex){};
   
   try{document.all('PostalAddress').value= arrResult[0][2];}catch(ex){};
   try{document.all('ZIPCODE').value= arrResult[0][3];}catch(ex){};
   //try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{document.all('Fax').value= arrResult[0][5];}catch(ex){};
   //try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   //try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{document.all('HomePhone').value= arrResult[0][8];}catch(ex){};
   //try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   //try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
   //try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
   try{document.all('Phone').value= arrResult[0][4];}catch(ex){};
   //try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
   try{document.all('Mobile').value= arrResult[0][14];}catch(ex){};
   //try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
   try{document.all('EMail').value= arrResult[0][16];}catch(ex){};
   //try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
   //try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
   //try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
   //try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
   //try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
   try{document.all('InsuredProvince').value= arrResult[0][28];}catch(ex){};
   try{document.all('InsuredCity').value= arrResult[0][29];}catch(ex){};
   try{document.all('InsuredDistrict').value= arrResult[0][30];}catch(ex){};
//	showOneCodeName('province','InsuredProvince','InsuredProvinceName');
//	showOneCodeName('city','InsuredCity','InsuredCityName');
//	showOneCodeName('district','InsuredDistrict','InsuredDistrictName');
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
   return;
 }

 
 
	 if(cCodeName=="paymode")
	 {
	   	if(document.all('PayMode').value=="4")
	   	{
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
		var tAgentCode=document.all('AgentCode').value;
		//alert(tAgentCode); 
		
		var sql27="";
		var sqlid27="BankContInput27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
		mySql27.setSqlId(sqlid27);//指定使用的Sql的id
		mySql27.addSubPara(tAgentCode);
		sql27=mySql27.getString();
		var sqlstr =sql27;
		
		//var sqlstr="select name,agentgroup,managecom from labranchgroup where agentgroup=(select agentgroup from laagent where agentcode='" + tAgentCode+"')" ;
		arrResult = easyExecSql(sqlstr,1,0);
		if(arrResult==null)
		{
			alert("专管员信息查询出错");
			return false;
		}
		else
		{
			try{document.all('ManageCom').value= arrResult[0][2];}catch(ex){};//管理机构
			try{document.all('BranchAttr').value= arrResult[0][0];}catch(ex){};//营业部、营业组 
			try{document.all('AgentGroup').value= arrResult[0][1];}catch(ex){};//
			showOneCodeName('comcode','ManageCom','ManageComName');
		}

	}
	 
	if (cCodeName == "AgentCom") {
    	initManageCom();
    }

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
	 	document.all('AppntPostalAddress').value=document.all('CompanyAddress').value;
                document.all('AppntZipCode').value=document.all('AppntGrpZipCode').value;
                document.all('AppntPhone').value= document.all('AppntPhone').value;
                document.all('AppntFax').value= document.all('AppntGrpFax').value;

	 }
	 else if(fm.CheckPostalAddress.value=="2")
	 {
	 	document.all('AppntPostalAddress').value=document.all('AppntHomeAddress').value;
                document.all('AppntZipCode').value=document.all('AppntHomeZipCode').value;
                document.all('AppntPhone').value= document.all('AppntHomePhone').value;
                document.all('AppntFax').value= document.all('AppntHomeFax').value;
	 }
	 else if(fm.CheckPostalAddress.value=="3")
	 {
	 	document.all('AppntPostalAddress').value="";
                document.all('AppntZipCode').value="";
                document.all('AppntPhone').value= "";
                document.all('AppntFax').value= "";
	 }
}
function AppntChk()
{
	var Sex=fm.AppntSex.value;
	//var i=Sex.indexOf("-");
	//Sex=Sex.substring(0,i);
  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
             + " union select * from ldperson where 1=1"
             + " and IDType = '"+fm.AppntIDType.value
             + "' and IDType is not null "
             + " and IDNo = '"+fm.AppntIDNo.value
             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);
        if(arrResult==null)
        {
				  alert("该没有与该投保人相似的客户,无需校验");
				  return false;
        }else{

					window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag,"window1",sFeatures);
				}
}

//在初始化body时自动效验投保人信息，
//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的被保人信息。
function AppntChkNew(){
  //alert("aa");
  var Sex=fm.AppntSex.value;
  var sql28="";
  var sqlid28="BankContInput28";
  var mySql28=new SqlClass();
  mySql28.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
  mySql28.setSqlId(sqlid28);//指定使用的Sql的id
  mySql28.addSubPara(fm.AppntName.value);
  mySql28.addSubPara(Sex);
  mySql28.addSubPara(fm.AppntBirthday.value);
  
  mySql28.addSubPara(fm.AppntNo.value);
  mySql28.addSubPara(fm.AppntIDNo.value);
  mySql28.addSubPara(fm.AppntNo.value);
  
  sql28=mySql28.getString();
  var sqlstr =sql28;
  
  
//  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
//             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
//             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
//             + " union select * from ldperson where 1=1 " 
//             + " and IDNo = '"+fm.AppntIDNo.value
//             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
  //alert("sqlstr="+sqlstr);
  arrResult = easyExecSql(sqlstr,1,0);
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
	if(document.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			theFirstValue="";
			theSecondValue="";
			//document.all('AppntIDNo').focus();
			return;
		}
		else
		{
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
		}
	}
}
function getCompanyCode()
{
    var strsql = "";
    var tCodeData = "";
	
  var sql29="";
  var sqlid29="BankContInput29";
  var mySql29=new SqlClass();
  mySql29.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
  mySql29.setSqlId(sqlid29);//指定使用的Sql的id
  sql29=mySql29.getString();
  strsql =sql29;
	
    //strsql = "select CustomerNo,GrpName from LDgrp ";
    document.all("AppntGrpNo").CodeData=tCodeData+easyQueryVer3(strsql, 1, 0, 1);
}
function haveMultiAgent(){
	//alert("aa＝＝"+document.all("multiagentflag").checked);
  if(document.all("multiagentflag").checked){
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
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
	if(document.all(Field).all('MultiAgentGrid1').value==""){
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 {
	var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //保单号码
	
  var sql30="";
  var sqlid30="BankContInput30";
  var mySql30=new SqlClass();
  mySql30.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
  mySql30.setSqlId(sqlid30);//指定使用的Sql的id
  mySql30.addSubPara(cAgentCode);
  sql30=mySql30.getString();
  var strSql =sql30;
	
	//var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult == null) {
      alert("编码为:["+document.all( Field ).all('MultiAgentGrid1').value+"]的代理人不存在，请确认!");
    }
  }
}


//初始化主业务员信息
function displayMainAgent(){
  document.all("BranchAttr").value = arrResult[0][3];
  document.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent(){
   document.all('multiagentflag').checked="true";
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
function checkapplydate()
{
	if(trim(fm.PolAppntDate.value)==""){return ;}
    else if (fm.PolAppntDate.value.length == 8)
	{
		if(fm.PolAppntDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.PolAppntDate.value.substring(0,4);
			var Month =    fm.PolAppntDate.value.substring(4,6);
			var Day =      fm.PolAppntDate.value.substring(6,8);
			fm.PolAppntDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("您输入的投保日期有误!");
			fm.PolAppntDate.value = "";  
			return;
			}
		}else{
			alert("您输入的投保日期格式有误!");
			fm.PolAppntDate.value = "";  
			return;
		}
	}
	else if(fm.PolAppntDate.value.length == 10)
	{
		var Year =     fm.PolAppntDate.value.substring(0,4);
		var Month =    fm.PolAppntDate.value.substring(5,7);
		var Day =      fm.PolAppntDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的投保日期有误!");
		fm.PolAppntDate.value = "";
		fm.PolAppntDate.focus();
		return;
		}
	}
	else
	{
		alert("您输入的投保日期有误!");
		fm.PolAppntDate.value = "";
		fm.PolAppntDate.focus();
	}
		//增加系统对录入的投保日期校验
	if(checkPolDate(fm.ProposalContNo.value,fm.PolAppntDate.value)==false)
	{
	fm.PolAppntDate.value="";
	fm.PolAppntDate.focus();
	return;
	}

}


//校验【初审日期】 -2010-03-19 - hanbin
function checkFirstTrialDate()
{
	if(trim(fm.FirstTrialDate.value)==""){return ;}
    else if (fm.FirstTrialDate.value.length == 8)
	{
		if(fm.FirstTrialDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.FirstTrialDate.value.substring(0,4);
			var Month =    fm.FirstTrialDate.value.substring(4,6);
			var Day =      fm.FirstTrialDate.value.substring(6,8);
			fm.FirstTrialDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("您输入的投保日期有误!");
			fm.FirstTrialDate.value = "";  
			return;
			}
		}else{
			alert("您输入的投保日期格式有误!");
			fm.FirstTrialDate.value = "";  
			return;
		}
	}
	else if(fm.FirstTrialDate.value.length == 10)
	{
		var Year =     fm.FirstTrialDate.value.substring(0,4);
		var Month =    fm.FirstTrialDate.value.substring(5,7);
		var Day =      fm.FirstTrialDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的投保日期有误!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
		return;
		}
	}
	else
	{
		alert("您输入的投保日期有误!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
	}

}
/******************************************************************************
* 添加校验，增加系统对录入的投保日期校验，校验规则为：录入的投保日期控制在录单的系统当期日期60天内
* 所需参数：ContNo---合同号（印刷号）；  PolAppntDate---投保日期
*******************************************************************************/
function checkPolDate(ContNo,PolAppntDate)
{
	var tContNo = ContNo;//合同号
    var tPolAppntDate = PolAppntDate;//投保日期
     //投保日期只能为当前日期以前
    if (calAge(tPolAppntDate) < 0)
    {
        alert("投保日期只能为当前日期以前!");
        return false;
    }
    var DayIntv=60;//录单日期与投保日期的标准间隔天数，默认为60天
    //查询录单日期与投保日期的标准间隔天数
	
  var sql31="";
  var sqlid31="BankContInput31";
  var mySql31=new SqlClass();
  mySql31.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
  mySql31.setSqlId(sqlid31);//指定使用的Sql的id
  sql31=mySql31.getString();
  
  var DayIntvArr = easyExecSql(sql31);
    //var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //根据合同号获取录单日期<查询合同表，存在则取之，否则取系统当前日期>
    var tMakeDate = "";//录单的系统当期日期
    if(tContNo!=null && tContNo!="")
    {
		var sql32="";
        var sqlid32="BankContInput32";
        var mySql32=new SqlClass();
        mySql32.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
        mySql32.setSqlId(sqlid32);//指定使用的Sql的id
        mySql32.addSubPara(tContNo);
        sql32=mySql32.getString();
        var makedatesql=sql32;
		
		//var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //录单的系统当期日期为空，则 默认系统日期
    {    
    	var sysdatearr = easyExecSql("select to_char(now(),'yyyy-mm-dd') from dual");
    	tMakeDate = sysdatearr[0][0];//录单的系统当期日期，默认系统日期。
    }
    var Days = dateDiff(tPolAppntDate, tMakeDate, "D");//录单日期与投保日期的间隔
    if (Days > DayIntv || Days < 0)
    {
        var strInfo = "投保日期应在录单的系统当期日期 "+DayIntv+" 天内。";
        strInfo = strInfo +"\n而录单日期["+tMakeDate+"] — 投保日期["+tPolAppntDate+"]=="+Days+" 天。";
		strInfo = strInfo +"\n请重新填写投保日期。";
        alert(strInfo);
        return false;
    }
    return true;
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

//投保人年龄<投保人被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge()
{
	if(fm.AppntBirthday.value=="")
	{
		return;
	}
	if(fm.AppntBirthday.value.indexOf('-')==-1)
	{
		var Year =fm.AppntBirthday.value.substring(0,4);
		var Month =fm.AppntBirthday.value.substring(4,6);
		var Day =fm.AppntBirthday.value.substring(6,8);
		fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.AppntBirthday.value)<0)
	{
		alert("出生日期只能为当前日期以前!");
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}

//被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修
function getAge2()
{
	if(fm.Birthday.value=="")
	{
		return;
	}
	if(fm.Birthday.value.indexOf('-')==-1)
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(4,6);
		var Day =      fm.Birthday.value.substring(6,8);
		fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     fm.Birthday.value.substring(0,4);
	    var Month1 =    fm.Birthday.value.substring(5,7);
	    var Day1 =      fm.Birthday.value.substring(8,10);	
	    fm.Birthday.value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(fm.Birthday.value)<0)
  	{
		alert("出生日期只能为当前日期以前");
		fm.InsuredAppAge.value="";
		return;
    }
//    fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}



//录入校验方法
//传入参数：verifyOrder校验的顺序
//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInputNB(verifyOrder)  
{
	//alert('!!!:'+verifyOrder)
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
	unlockScreen('lkscreen');
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
  
//  alert();
//  if(InsuredGrid.mulLineCount>0){
//    document.all('spanInsuredGrid0').all('InsuredGridSel').checked='true'
//  }

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
  document.all('spanInsuredGrid0').all('InsuredGridSel').checked="true";
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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
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
            window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
        }
    }
}
//问题件查询
function QuestQuery()
{
  cContNo = document.all("ContNo").value;  //保单号码
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
      window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1","",sFeatures);
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
	delInsuredVar();
	addInsuredVar();
  	try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //选择险种单进入险种界面带出已保存的信息
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("请先选择被保险人险种信息！");
		return;
	}
	//加入控制：在录单和问题件修改时，如果存在险种必须先选择一个险种后才能进入险种信息
	if((LoadFlag=='1'||LoadFlag=='3') && (PolGrid.mulLineCount>0))
	{
		var tSelPol=PolGrid.getSelNo();
		if(tSelPol==null || tSelPol==0)
		{
			alert("请先选择一个险种后再进入险种信息页面！");
			return;
		}
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){}
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
	try{mSwitch.deleteVar('CValiDate');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolAppntDate').value);}catch(ex){}
  
  	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled+"&specScanFlag="+specScanFlag;
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
    try{mSwitch.deleteVar('IDPeriodOfValidity');}catch(ex){};
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
    try{mSwitch.addVar('IDPeriodOfValidity','',fm.IDPeriodOfValidity.value);}catch(ex){};

}

/*********************************************************************
 *  添加被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addRecord()
{ 
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
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
	  var tPrtNo=document.all("PrtNo").value;

	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        //fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}

  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==3){

	  var tPrtNo=document.all("PrtNo").value;

	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

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

	  var tPrtNo=document.all("PrtNo").value;

     	var sql33="";
        var sqlid33="BankContInput33";
        var mySql33=new SqlClass();
        mySql33.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
        mySql33.setSqlId(sqlid33);//指定使用的Sql的id
        mySql33.addSubPara(tContNo);
        sql33=mySql33.getString();
        var sqlstr=sql33;

	  //var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

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

	  var tPrtNo=document.all("PrtNo").value;

        var sql34="";
        var sqlid34="BankContInput34";
        var mySql34=new SqlClass();
        mySql34.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
        mySql34.setSqlId(sqlid34);//指定使用的Sql的id
        mySql34.addSubPara(tPrtNo);
        sql34=mySql34.getString();
        var sqlstr=sql34;

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

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

  if (document.all('PolTypeFlag').value==0)
  {
    if( verifyInputNB('2') == false ) return false;
  }

  if(document.all('IDType').value=="0")
  {
    var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
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
	getdetailwork2();
  //end hanlin
  document.all('ContType').value=ContType;
  document.all( 'BQFlag' ).value = BQFlag;
  document.all('fmAction').value="INSERT||CONTINSURED";
  document.getElementById("fm").submit();
}

/*********************************************************************
 *  修改被选中的被保险人
 *  此处添加被保人修改校验，如果修改了关键信息<姓名、性别、证件类型或号码、出生日期>或者 职业类别，
 *  则需先删除险种信息，因为这可能会变更客户号或重新计算保费
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function modifyRecord()
{
	lockScreen('lkscreen');  
	//如果loadflag=3问题件修改岗，修改被保人重要信息且投被保人关系为00-本人则先修改投保人信息
	var tName = fm.Name.value;
	var tIDType = fm.IDType.value;
	var tIDNo = fm.IDNo.value;
	var tSex = fm.Sex.value;
	var tBirthday = fm.Birthday.value;
	var tContNo = fm.PrtNo.value;
	if(LoadFlag=="3"){
		if(fm.RelationToInsured.value=="00"){
			
			var sql35="";
            var sqlid35="BankContInput35";
            var mySql35=new SqlClass();
	        mySql35.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql35.setSqlId(sqlid35);//指定使用的Sql的id
	        mySql35.addSubPara(tContNo);
			mySql35.addSubPara(tName);
			mySql35.addSubPara(tBirthday);
			
			mySql35.addSubPara(tSex);
			mySql35.addSubPara(tIDType);
			mySql35.addSubPara(tIDNo);
	        sql35=mySql35.getString();
	        var tCheckSql=sql35;
			
//			var tCheckSql = "select * from lcappnt where prtno='"+tContNo+"' and appntname='"+tName+"'"
//						+ " and appntbirthday='"+tBirthday+"' and appntsex='"+tSex+"'"
//						+ " and idtype='"+tIDType+"' and idno='"+tIDNo+"'";
			var tArr = easyExecSql(tCheckSql,1,0);
			if(!tArr){
				alert("投被保人关系为本人，且修改了被保人重要信息导致投被保人可能不是同一人，请修该投被保人关系或先修改投保人信息！");
				unlockScreen('lkscreen');
				return false;
			}
		}
	}
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		unlockScreen('lkscreen');
		return false;
	}/////////////////校验公共流是否已经流转完毕
	if (InsuredGrid.mulLineCount==0)
	{
		alert("该被保险人还没有保存，无法进行修改！");
		unlockScreen('lkscreen');
		return false;
	}
	var tselno=InsuredGrid.getSelNo();
	if(tselno==0)
	{
		alert("请选中需要修改被保人记录");
		unlockScreen('lkscreen');
		return false;
	}
	/*start.注释。MS要求无需删除险种即可修改被保人---yeshu,20071224
	var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
	var SSQL=" select t.insuredno,t.name,t.sex,t.birthday,t.idtype,t.idno,t.occupationcode"
		+" from lcinsured t where t.contno='"+fm.ContNo.value+"' and t.insuredno='"+tOldInsuredNo+"'";
	var tOldArr=easyExecSql(SSQL,1,0);
	//获取准备修改的信息,同查询出的信息比较，如果有一项不同，那么查询是否有险种，有则提示需先删除险种信息
	if( fm.Name.value!=tOldArr[0][1] || fm.IDType.value!=tOldArr[0][4] || fm.IDNo.value!=tOldArr[0][5] 
	  || fm.Sex.value!=tOldArr[0][2] || fm.Birthday.value!=tOldArr[0][3] || fm.OccupationCode.value!=tOldArr[0][6])
	{
		var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"' ";
		arrResult=easyExecSql(sqlstr,1,0);
		if(arrResult!=null)
		{
			alert("你可能修改了该被保人关键信息<姓名、性别、证件类型或号码、出生日期>或职业类别。请先删除该被保人下的险种信息。");
			return false;    
		}
	}	end.---yeshu,20071224*/
	if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	{
		alert("与投保人关系只能选择本人！");
		fm.RelationToAppnt.value="00";
		fm.RelationToAppntName.value="本人";
		unlockScreen('lkscreen');
		return ;
	}
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInputNB('2') == false ) {
        	unlockScreen('lkscreen');
        	return false;
        }
    }
    //校验被保险人与主被保险人关系
    if(!checkself()) { 
    	unlockScreen('lkscreen');
    	return false;
    }
   /* if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }*/
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)
    {
    	unlockScreen('lkscreen');
    	return false;
    }
	ImpartGrid.delBlankLine();
	getdetailwork2();
	document.all('ContType').value=ContType;
	document.all('fmAction').value="UPDATE||CONTINSURED";
	fm.action="BankContInsuredSave.jsp";
	document.getElementById("fm").submit();
}
/*********************************************************************
 *  删除被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function deleteRecord()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
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
	
	        var sql36="";
            var sqlid36="BankContInput36";
            var mySql36=new SqlClass();
	        mySql36.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql36.setSqlId(sqlid36);//指定使用的Sql的id
	        mySql36.addSubPara(fm.ContNo.value);
	        sql36=mySql36.getString();
	        var sqlstr=sql36;
	
    //var sqlstr="select polno from  lcpol where contNo='"+fm.ContNo.value+"'";
    arrResult=easyExecSql(sqlstr,1,0);
    
    if(arrResult!=null)
    {
    	alert("请先删除被保人险种信息！");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    document.getElementById("fm").submit();
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
  	var backstr=document.all("ContNo").value;

	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(document.all("PrtNo").value);
  	location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }
  if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(document.all("PrtNo").value);
    location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }

	if(LoadFlag=="2")
	{
    location.href="../app/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + document.all("GrpContNo").value+"&scantype="+scantype;
  }
	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+document.all("PrtNo").value;
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
      parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype;
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
    var newWindow = window.open("../app/GroupRiskPlan.jsp","",sFeatures);
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
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=true;
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
	        	document.all('PostalAddress').value=document.all('GrpAddress').value;
            document.all('ZipCode').value=document.all('GrpZipCode').value;
            document.all('Phone').value= document.all('Phone').value;

	        }
	        else if(fm.CheckPostalAddress.value=="2")
	        {
	        	document.all('PostalAddress').value=document.all('HomeAddress').value;
            document.all('ZipCode').value=document.all('HomeZipCode').value;
            document.all('Phone').value= document.all('HomePhone').value;
	        }
	        else if(fm.CheckPostalAddress.value=="3")
	        {
	        	document.all('PostalAddress').value="";
            document.all('ZipCode').value="";
            document.all('Phone').value= "";
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
    //alert("hl in getInsuredInfo ContNo="+document.all("ContNo").value);
    //alert("hl in getInsuredInfo GrpName="+document.all("GrpName").value);
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
		
		   var sql37="";
            var sqlid37="BankContInput37";
            var mySql37=new SqlClass();
	        mySql37.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql37.setSqlId(sqlid37);//指定使用的Sql的id
	        mySql37.addSubPara(ContNo);
	        sql37=mySql37.getString();
	        var strSQL=sql37;
		
        //var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";

        //alert("strSQL=="+strSQL);
		InsuredGrid.clearData();//add by weikai 如果不清 后面  displayMultiline(arrDataSet, turnPage.pageDisplayGrid) 会出错
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
        //alert("fm.InsuredGrid=="+document.all('spanInsuredGrid0').all('InsuredGridSel').checked);
        document.all('spanInsuredGrid0').all('InsuredGridSel').checked=true;
       getInsuredDetail("spanInsuredGrid0","");
//Yang Yalin Changed this,2005-10-8
         
	//			initInsuredServer();
				
        //showInsuredCodeName();  

    }
}

/*********************************************************************
 *  获得个单合同的被保人信息
 *  返回值：  无
 *********************************************************************
 */
function getProposalInsuredInfo()
{
    //alert("ContNo=="+document.all("ContNo").value);
    var tContNo=document.all("ContNo").value;
	
	        var sql38="";
            var sqlid38="BankContInput38";
            var mySql38=new SqlClass();
	        mySql38.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql38.setSqlId(sqlid38);//指定使用的Sql的id
	        mySql38.addSubPara(ContNo);
	        sql38=mySql38.getString();
	        var strSQL=sql38;
			
	 arrResult=easyExecSql(strSQL,1,0);
   // arrResult=easyExecSql("select * from LCInsured where ContNo='"+tContNo+"'",1,0);
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
        
		   var sql39="";
            var sqlid39="BankContInput39";
            var mySql39=new SqlClass();
	        mySql39.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql39.setSqlId(sqlid39);//指定使用的Sql的id
	        mySql39.addSubPara(tCustomerNo);
	        sql39=mySql39.getString();
	      
		 arrResult=easyExecSql(sql39,1,0);
       // arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
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
 function getInsuredDetail1(ContNo,InsuredNo){
 	    //add by wangyc
    parent.fraSubmit.window.location = "./ConfInfoQuery1.jsp?ContNo="
																			+ ContNo+"&InsuredNo="+InsuredNo;
 	}
function getInsuredDetail(parm1,parm2)
{
    //alert("---parm1=="+parm1+"---parm2=="+parm2);

    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;

    var ContNo = fm.ContNo.value;

    //被保人详细信息
	
	        var sql40="";
            var sqlid40="BankContInput40";
            var mySql40=new SqlClass();
	        mySql40.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql40.setSqlId(sqlid40);//指定使用的Sql的id
	        mySql40.addSubPara(InsuredNo);
	        sql40=mySql40.getString();
	        var strSQL=sql40;
    //var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";

    arrResult=easyExecSql(strSQL);

    if(arrResult!=null)
    {
        //displayAppnt();
        displayInsuredInfo();
    }

            var sql41="";
            var sqlid41="BankContInput41";
            var mySql41=new SqlClass();
	        mySql41.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql41.setSqlId(sqlid41);//指定使用的Sql的id
	        mySql41.addSubPara(ContNo);
			mySql41.addSubPara(InsuredNo);
	        sql41=mySql41.getString();
	        strSQL=sql41;

    //strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";

    arrResult=easyExecSql(strSQL);

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
	try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户地址数据显示到被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayAddress2()
{
	try{document.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}
/*********************************************************************
 *  显示被保人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayInsured()
{	
    try{document.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{document.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{document.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{document.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{document.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{document.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{document.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{document.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{document.all('Name').value=arrResult[0][12];}catch(ex){};
    try{document.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{document.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{document.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{document.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{document.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{document.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{document.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{document.all('Health').value=arrResult[0][22];}catch(ex){};
    try{document.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{document.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{document.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{document.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{document.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{document.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{document.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};
    try{document.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{document.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{document.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{document.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{document.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{document.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{document.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{document.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{document.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{document.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{document.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{document.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{document.all('LicenseType').value=arrResult[0][53];}catch(ex){};
    try{document.all('IDPeriodOfValidity').value=arrResult[0][64];}catch(ex){};
}
function displayissameperson()
{
	//alert("here!");
  //alert("document.all( 'AppntName').value="+document.all( "AppntName").value);
　try{document.all('InsuredNo').value= document.all("AppntNo").value;                 }catch(ex){};
　try{document.all('Name').value= document.all("AppntName").value;                    }catch(ex){};
　try{document.all('Sex').value= document.all("AppntSex").value;                      }catch(ex){};
　try{document.all('Birthday').value= document.all("AppntBirthday").value;            }catch(ex){};
　try{document.all('IDType').value= document.all("AppntIDType").value;                }catch(ex){};
　try{document.all('IDNo').value= document.all("AppntIDNo").value;                    }catch(ex){};
　try{document.all('Password').value= document.all( "AppntPassword" ).value;          }catch(ex){};
　try{document.all('NativePlace').value= document.all("AppntNativePlace").value;      }catch(ex){};
　try{document.all('Nationality').value= document.all("AppntNationality").value;      }catch(ex){};
　try{document.all('InsuredAddressNo').value= document.all("AppntAddressNo").value;   }catch(ex){};
　try{document.all('RgtAddress').value= document.all( "AppntRgtAddress" ).value;      }catch(ex){};
　try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
　try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
　try{document.all('Health').value= document.all( "AppntHealth" ).value;              }catch(ex){};
　try{document.all('Stature').value= document.all( "AppntStature" ).value;            }catch(ex){};
　try{document.all('Avoirdupois').value= document.all( "AppntAvoirdupois" ).value;    }catch(ex){};
　try{document.all('Degree').value= document.all( "AppntDegree" ).value;              }catch(ex){};
　try{document.all('CreditGrade').value= document.all( "AppntDegreeCreditGrade" ).value;}catch(ex){};
　try{document.all('OthIDType').value= document.all( "AppntOthIDType" ).value;}catch(ex){};
　try{document.all('OthIDNo').value= document.all( "AppntOthIDNo" ).value;}catch(ex){};
　try{document.all('ICNo').value= document.all("AppntICNo").value;}catch(ex){};
　try{document.all('GrpNo').value= document.all("AppntGrpNo").value;}catch(ex){};
　try{document.all( 'JoinCompanyDate' ).value = document.all("JoinCompanyDate").value; if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
　try{document.all('StartWorkDate').value= document.all("AppntStartWorkDate").value;}catch(ex){};
　try{document.all('Position').value= document.all("AppntPosition").value;}catch(ex){};
　try{document.all( 'Position' ).value = document.all("Position").value;} catch(ex) { };
　try{document.all('Salary').value= document.all("AppntSalary").value;}catch(ex){};
　try{document.all('OccupationType').value= document.all("AppntOccupationType").value;}catch(ex){};
　try{document.all('OccupationCode').value= document.all("AppntOccupationCode").value;}catch(ex){};
　try{document.all('WorkType').value= document.all("AppntWorkType").value;}catch(ex){};
　try{document.all('PluralityType').value= document.all("AppntPluralityType").value;}catch(ex){};
　try{document.all('DeathDate').value= document.all("AppntDeathDate").value;}catch(ex){};
　try{document.all('SmokeFlag').value= document.all("AppntSmokeFlag").value;}catch(ex){};
　try{document.all('BlacklistFlag').value= document.all("AppntBlacklistFlag").value;}catch(ex){};
　try{document.all('Proterty').value= document.all("AppntProterty").value;}catch(ex){};
　//try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
　try{document.all('State').value= document.all("AppntState").value;}catch(ex){};
　try{document.all('Operator').value= document.all("AppntOperator").value;}catch(ex){};
　try{document.all('MakeDate').value= document.all("AppntMakeDate").value;}catch(ex){};
　try{document.all('MakeTime').value= document.all("AppntMakeTime").value;}catch(ex){};
　try{document.all('ModifyDate').value= document.all("AppntModifyDate").value;}catch(ex){};
　try{document.all('ModifyTime').value= document.all("AppntModifyTime").value;}catch(ex){};
　try{document.all('PostalAddress').value= document.all("AppntPostalAddress").value;}catch(ex){};
　try{document.all('ZipCode').value= document.all("AppntZipCode").value;}catch(ex){};
　try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
　try{document.all('Fax').value= document.all("AppntFax").value;}catch(ex){};
　try{document.all('Mobile').value= document.all("AppntMobile").value;}catch(ex){};
　try{document.all('EMail').value= document.all("AppntEMail").value;}catch(ex){};
　try{document.all('GrpName').value= document.all("AppntGrpName").value;}catch(ex){};
　try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
　try{document.all('GrpAddress').value= document.all("CompanyAddress").value;}catch(ex){};
　try{document.all('GrpZipCode').value= document.all("AppntGrpZipCode").value;}catch(ex){};
　try{document.all('GrpFax').value= document.all("AppntGrpFax").value;}catch(ex){};
　try{document.all('HomeAddress').value= document.all("AppntHomeAddress").value;}catch(ex){};
　try{document.all('HomePhone').value= document.all("AppntHomePhone").value;}catch(ex){};
　try{document.all('HomeZipCode').value= document.all("AppntHomeZipCode").value;}catch(ex){};
　try{document.all('HomeFax').value= document.all("AppntHomeFax").value;}catch(ex){};
　try{document.all('RelationToAppnt').value="00";}catch(ex){};
　try{document.all('InsuredProvince').value= document.all("AppntProvince").value;}catch(ex){};
　try{document.all('InsuredCity').value= document.all("AppntCity").value;}catch(ex){};
　try{document.all('InsuredDistrict').value= document.all("AppntDistrict").value;}catch(ex){};
　try{document.all('LicenseType').value= document.all("AppntLicenseType").value;}catch(ex){};
  try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('Income').value= document.all("Income0").value;}catch(ex){};
  try{document.all('IDPeriodOfValidity').value= document.all("AppIDPeriodOfValidity").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');
  //alert(document.all('Mobile').value);
/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
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
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {

    	    var strSQL0="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";

	       var strSQL1="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

            var sql42="";
            var sqlid42="BankContInput42";
            var mySql42=new SqlClass();
	        mySql42.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql42.setSqlId(sqlid42);//指定使用的Sql的id
	        mySql42.addSubPara(InsuredNo);
			mySql42.addSubPara(ContNo);
	        sql42=mySql42.getString();
	        strSQL0=sql42;
			
			var sql43="";
            var sqlid43="BankContInput43";
            var mySql43=new SqlClass();
	        mySql43.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql43.setSqlId(sqlid43);//指定使用的Sql的id
	        mySql43.addSubPara(InsuredNo);
			mySql43.addSubPara(ContNo);
	        sql43=mySql43.getString();
	        strSQL1=sql43;


	    arrResult = easyExecSql(strSQL0,1,0);
	    arrResult1 = easyExecSql(strSQL1,1,0);



	    try{document.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};

        //var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ProposalContNo='"+ContNo+"' and CustomerNoType='1' ";
        //modify by jiaqiangli 2009-04-30
		
		    var sql44="";
            var sqlid44="BankContInput44";
            var mySql44=new SqlClass();
	        mySql44.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql44.setSqlId(sqlid44);//指定使用的Sql的id
	        mySql44.addSubPara(InsuredNo);
			mySql44.addSubPara(ContNo);
	        sql44=mySql44.getString();
	        var strSQL=sql44;
		
        //var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' ";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //告知信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
		   var sql45="";
            var sqlid45="BankContInput45";
            var mySql45=new SqlClass();
	        mySql45.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql45.setSqlId(sqlid45);//指定使用的Sql的id
	        mySql45.addSubPara(InsuredNo);
			mySql45.addSubPara(ContNo);
	        sql45=mySql45.getString();
	        var strSQL=sql45;
			
        //var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //险种信息初始化
    if(InsuredNo!=null&&InsuredNo!="")
    {
		
		    var sql46="";
            var sqlid46="BankContInput46";
            var mySql46=new SqlClass();
	        mySql46.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql46.setSqlId(sqlid46);//指定使用的Sql的id
	        mySql46.addSubPara(InsuredNo);
			mySql46.addSubPara(ContNo);
	        sql46=mySql46.getString();
	        var strSQL=sql46;
		
        //var strSQL ="select polno,riskcode,(select riskname from lmriskapp where riskcode=lcpol.riskcode),prem,amnt from lcpol "
        //	+" where insuredno='"+InsuredNo+"' and contno='"+ContNo+"'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
    var PolNo=document.all(parm1).all('PolGrid1').value
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
        if (document.all('ContNo').value != "")
        {
            alert("团单的个单不能有多被保险人");
            return false;
        }
        else
            return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
            var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
        {
            alert("家庭单只能有一个主被保险人");
            return false;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
            var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
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
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("投保人与被保人关系不是本人，不能进行该操作！");
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";

      displayissameperson();
      //alert("InsuredName: "+document.all('Name').value);
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "";
      divSalary.style.display = "";
      try{document.all('InsuredNo').value="";}catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= "0"; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('RgtAddress').value= ""; }catch(ex){};
      try{document.all('Marriage').value= "";}catch(ex){};
      try{document.all('MarriageDate').value= "";}catch(ex){};
      try{document.all('Health').value= "";}catch(ex){};
      try{document.all('Stature').value= "";}catch(ex){};
      try{document.all('Avoirdupois').value= "";}catch(ex){};
      try{document.all('Degree').value= "";}catch(ex){};
      try{document.all('CreditGrade').value= "";}catch(ex){};
      try{document.all('OthIDType').value= "";}catch(ex){};
      try{document.all('OthIDNo').value= "";}catch(ex){};
      try{document.all('ICNo').value="";}catch(ex){};
      try{document.all('GrpNo').value= "";}catch(ex){};
      try{document.all('JoinCompanyDate').value= "";}catch(ex){}
      try{document.all('StartWorkDate').value= "";}catch(ex){};
      try{document.all('Position').value= "";}catch(ex){};
      try{document.all('Salary').value= "";}catch(ex){};
      try{document.all('OccupationType').value= "";}catch(ex){};
      try{document.all('OccupationCode').value= "";}catch(ex){};
      try{document.all('WorkType').value= "";}catch(ex){};
      try{document.all('PluralityType').value= "";}catch(ex){};
      try{document.all('DeathDate').value= "";}catch(ex){};
      try{document.all('SmokeFlag').value= "";}catch(ex){};
      try{document.all('BlacklistFlag').value= "";}catch(ex){};
      try{document.all('Proterty').value= "";}catch(ex){};
      //try{document.all('Remark').value= "";}catch(ex){};
      try{document.all('State').value= "";}catch(ex){};
      try{document.all('Operator').value= "";}catch(ex){};
      try{document.all('MakeDate').value= "";}catch(ex){};
      try{document.all('MakeTime').value="";}catch(ex){};
      try{document.all('ModifyDate').value= "";}catch(ex){};
      try{document.all('ModifyTime').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('ZipCode').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('Mobile').value= "";}catch(ex){};
      try{document.all('EMail').value="";}catch(ex){};
      try{document.all('GrpName').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('GrpAddress').value="";}catch(ex){};
      try{document.all('GrpZipCode').value= "";}catch(ex){};
      try{document.all('RelationToAppnt').value= "";}catch(ex){};
      try{document.all('Fax').value= "";}catch(ex){};
      try{document.all('HomePhone').value= "";}catch(ex){};
	  try{document.all('InsuredAddressNo').value="";}catch(ex){};
	　try{document.all('InsuredProvince').value= "";}catch(ex){};
	　try{document.all('InsuredCity').value="";}catch(ex){};
	　try{document.all('InsuredDistrict').value="";}catch(ex){};
      try{document.all('IncomeWay').value=  "";}catch(ex){};
      try{document.all('Income').value=  "";}catch(ex){};
      try{document.all('IDPeriodOfValidity').value=  "";}catch(ex){};

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
    if (document.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
       
	       var sql47="";
            var sqlid47="BankContInput47";
            var mySql47=new SqlClass();
	        mySql47.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql47.setSqlId(sqlid47);//指定使用的Sql的id
	        mySql47.addSubPara(fm.InsuredNo.value);
	        sql47=mySql47.getString();
	        var strSQL=sql47;
	   
	   arrResult = easyExecSqlstrSQL(strSQL, 1, 0);
	    //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
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
          getdetailaddress2();
          showCodeName();
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
//	  alert("asdfasfsf");
	  //alert(arrResult[0][1]);
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
    try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{document.all('Position').value= arrResult[0][23];}catch(ex){};
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){};
    //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{document.all('State').value= arrResult[0][34];}catch(ex){};
    try{document.all('VIPValue1').value= arrResult[0][35];}catch(ex){};
    try{document.all('Operator').value= arrResult[0][36];}catch(ex){};
    try{document.all('MakeDate').value= arrResult[0][37];}catch(ex){};
    try{document.all('MakeTime').value= arrResult[0][38];}catch(ex){};
    try{document.all('ModifyDate').value= arrResult[0][39];}catch(ex){};
    try{document.all('ModifyTime').value= arrResult[0][40];}catch(ex){};
    try{document.all('LicenseType').value= arrResult[0][43];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][44];}catch(ex){};
    try{document.all('IDPeriodOfValidity').value= arrResult[0][47];}catch(ex){};



    //地址显示部分的变动
    //try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('GrpAddress').value=  "";}catch(ex){};
    try{document.all('GrpZipCode').value=  "";}catch(ex){};
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
            document.all( 'ContNo' ).value = arrQueryResult[0][0];
             
			var sql48="";
            var sqlid48="BankContInput48";
            var mySql48=new SqlClass();
	        mySql48.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql48.setSqlId(sqlid48);//指定使用的Sql的id
	        mySql48.addSubPara(arrQueryResult[0][0] );
	        sql48=mySql48.getString();
	        var strSQL=sql48;
			 
			 arrResult = easyExecSql(strSQL, 1, 0);
            //arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

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
        	
			var sql49="";
            var sqlid49="BankContInput49";
            var mySql49=new SqlClass();
	        mySql49.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql49.setSqlId(sqlid49);//指定使用的Sql的id
	        mySql49.addSubPara(arrQueryResult[0][0]  );
	        sql49=mySql49.getString();
	        var strSQL=sql49;
			arrResult = easyExecSql(strSQL, 1, 0);
			//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
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
	       var sql50="";
            var sqlid50="BankContInput50";
            var mySql50=new SqlClass();
	        mySql50.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql50.setSqlId(sqlid50);//指定使用的Sql的id
	        mySql50.addSubPara( fm.OccupationCode.value  );
	        sql50=mySql50.getString();
	        var strSQL=sql50;
	
   // var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    var arrResult = easyExecSql(strSQL);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
        showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
    }
    else
    {
        fm.OccupationType.value = "";
        fm.OccupationTypeName.value = "";
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
	//alert("LoadFlag=="+LoadFlag);


    if (wFlag ==1 ) //录入完毕确认
    {
		    var sql51="";
            var sqlid51="BankContInput51";
            var mySql51=new SqlClass();
	        mySql51.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql51.setSqlId(sqlid51);//指定使用的Sql的id
	        mySql51.addSubPara( fm.ContNo.value  );
	        sql51=mySql51.getString();
	        var tStr=sql51;
			    //alert('1');
        //var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
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
  	    if(document.all('ProposalContNo').value == "")
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
        if(document.all('ProposalContNo').value == "")
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
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "./InputConfirm.jsp";
    document.getElementById("fm").submit(); //提交
}
/*********************************************************************
 *  查询被保险人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailaddress2()
{
    //alert("fm.InsuredAddressNo.value"+fm.InsuredAddressNo.value);
	
	        var sql52="";
            var sqlid52="BankContInput52";
            var mySql52=new SqlClass();
	        mySql52.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql52.setSqlId(sqlid52);//指定使用的Sql的id
	        mySql52.addSubPara( fm.InsuredAddressNo.value  );
			mySql52.addSubPara( fm.InsuredNo.value  );
	        sql52=mySql52.getString();
	        var strSQL=sql52;
	
    //var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    arrResult=easyExecSql(strSQL);
    displayDetailAddress2(arrResult);
}

function displayDetailAddress2(arrResult)
{
      //alert("fm.InsuredAddressNo.value"+fm.InsuredAddressNo.value);
    try{document.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
    try{document.all('InsuredAddressNoName').value= arrResult[0][1];}catch(ex){};
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
    try{document.all('ZIPCODE').value= arrResult[0][2];}catch(ex){};
    try{document.all('Phone').value= arrResult[0][3];}catch(ex){};
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){};
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try
      {
      	document.all('Phone').value= arrResult[0][6];
      	if(document.all('Phone').value==null||document.all('Phone').value=="")
      	{
      		document.all('Phone').value=arrResult[0][3];
      	}
    	}catch(ex){};
    try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
    try{document.all('Fax').value= arrResult[0][9];}catch(ex){};
    try{document.all('HomePhone').value= arrResult[0][10];}catch(ex){};
    //alert("arrResult[0][11]=="+arrResult[0][11]);
   // try{document.all('GrpName').value= arrResult[0][11];}catch(ex){};
    //alert("fm.GrpName="+fm.GrpName.value);
   try{document.all('InsuredProvince').value= arrResult[0][12];}catch(ex){};
   try{document.all('InsuredCity').value= arrResult[0][13];}catch(ex){};
   try{document.all('InsuredDistrict').value= arrResult[0][14];}catch(ex){};
//   showOneCodeName('Province','InsuredProvince','InsuredProvinceName');
//   showOneCodeName('City','InsuredCity','InsuredCityName');
//   showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
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
	
	        var sql53="";
            var sqlid53="BankContInput53";
            var mySql53=new SqlClass();
	        mySql53.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql53.setSqlId(sqlid53);//指定使用的Sql的id
	        mySql53.addSubPara( tProposalGrpContNo );
	        sql53=mySql53.getString();
	        strsql=sql53;
	
    //strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
	
		     var sql54="";
            var sqlid54="BankContInput54";
            var mySql54=new SqlClass();
	        mySql54.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql54.setSqlId(sqlid54);//指定使用的Sql的id
	        mySql54.addSubPara( tProposalGrpContNo );
	        sql54=mySql54.getString();
	        strsql=sql54;
	
	//strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
        try{document.all('AppntVIPValue').value= ""; }catch(ex){};
        try{document.all('AppntVIPFlagname').value= ""; }catch(ex){};
        try{document.all('AppntName').value= ""; }catch(ex){};
        try{document.all('AppntIDNo').value= ""; }catch(ex){};
        try{document.all('AppntSex').value= ""; }catch(ex){};
        try{document.all('AppntSexName').value= ""; }catch(ex){};
        try{document.all('AppntBirthday').value= ""; }catch(ex){};
        try{document.all('AppntAge').value= ""; }catch(ex){};
        try{document.all('AppntMarriage').value= ""; }catch(ex){};
        try{document.all('AppntMarriageName').value= ""; }catch(ex){};
        try{document.all('AppntNativePlace').value= ""; }catch(ex){};
        try{document.all('AppntNativePlaceName').value= ""; }catch(ex){};
        try{document.all('AppntOccupationCode').value= ""; }catch(ex){};
        try{document.all('AppntOccupationCodeName').value= ""; }catch(ex){};
        try{document.all('AppntLicenseType').value= ""; }catch(ex){};
        try{document.all('AppntLicenseTypeName').value= ""; }catch(ex){};
        try{document.all('AppntAddressNo').value= ""; }catch(ex){};
        try{document.all('AddressNoName').value= ""; }catch(ex){};
        try{document.all('AppntProvince').value= ""; }catch(ex){};
        try{document.all('AppntProvinceName').value= ""; }catch(ex){};
        try{document.all('AppntCity').value= ""; }catch(ex){};
        try{document.all('AppntCityName').value= ""; }catch(ex){};
        try{document.all('AppntDistrict').value= ""; }catch(ex){};
        try{document.all('AppntDistrictName').value= ""; }catch(ex){};
        try{document.all('AppntPostalAddress').value= ""; }catch(ex){};
        try{document.all('AppntZipCode').value= ""; }catch(ex){};
        try{document.all('AppntMobile').value= ""; }catch(ex){};
        try{document.all('AppntPhone').value= ""; }catch(ex){};
        try{document.all('AppntFax').value= ""; }catch(ex){};
        try{document.all('AppntHomePhone').value= ""; }catch(ex){};
        try{document.all('AppntGrpName').value= ""; }catch(ex){};
        try{document.all('AppntEMail').value= ""; }catch(ex){};
        try{document.all('PayMode').value= ""; }catch(ex){};
        try{document.all('FirstPayModeName').value= ""; }catch(ex){};
        try{document.all('AppntBankCode').value= ""; }catch(ex){};
        try{document.all('FirstBankCodeName').value= ""; }catch(ex){};
        try{document.all('AppntAccName').value= ""; }catch(ex){};
        try{document.all('AppntBankAccNo').value= ""; }catch(ex){};
        try{document.all('SecPayMode').value= ""; }catch(ex){};
        try{document.all('PayModeName').value= ""; }catch(ex){};
        try{document.all('SecAppntBankCode').value= ""; }catch(ex){};
        try{document.all('AppntBankCodeName').value= ""; }catch(ex){};
        try{document.all('SecAppntAccName').value= ""; }catch(ex){};
        try{document.all('SecAppntBankAccNo').value= ""; }catch(ex){};
        try{document.all('Income0').value= ""; }catch(ex){};
        try{document.all('IncomeWay0').value= ""; }catch(ex){};
        try{document.all('IncomeWayName0').value= ""; }catch(ex){};
        try{document.all('AppIDPeriodOfValidity').value= ""; }catch(ex){};
        ImpartGrid.clearData();
	ImpartGrid.addOne();

}
function emptyInsured()
{

	try{document.all('InsuredNo').value= ""; }catch(ex){};
	try{document.all('VIPValue1').value= ""; }catch(ex){};
	try{document.all('AppntVIPFlagname1').value= ""; }catch(ex){};
	try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('ExecuteCom').value= ""; }catch(ex){};
	try{document.all('FamilyID').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{document.all('RelationToAppnt').value= ""; }catch(ex){};
	try{document.all('RelationToAppntName').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('Name').value= ""; }catch(ex){};
	try{document.all('Sex').value= ""; }catch(ex){};
	try{document.all('SexName').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsuredName').value= ""; }catch(ex){};
	try{document.all('SequenceNoName').value= ""; }catch(ex){};
	try{document.all('Birthday').value= ""; }catch(ex){};
	try{document.all('InsuredAppAge').value= ""; }catch(ex){};
	try{document.all('IDType').value= ""; }catch(ex){};
	try{document.all('IDNo').value= ""; }catch(ex){};
	try{document.all('IncomeWayName').value= ""; }catch(ex){};
	try{document.all('InsuredProvince').value= ""; }catch(ex){};
	try{document.all('InsuredProvinceName').value= ""; }catch(ex){};
	try{document.all('InsuredCity').value= ""; }catch(ex){};
	try{document.all('InsuredCityName').value= ""; }catch(ex){};
	try{document.all('InsuredDistrict').value= ""; }catch(ex){};
	try{document.all('InsuredDistrictName').value= ""; }catch(ex){};
	try{document.all('Income').value= ""; }catch(ex){};
	try{document.all('LicenseType').value= ""; }catch(ex){};
	try{document.all('LicenseTypeName').value= ""; }catch(ex){};
	try{document.all('IDTypeName').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNoName').value= ""; }catch(ex){};
	try{document.all('IncomeWay').value= ""; }catch(ex){};
	try{document.all('NativePlace').value= ""; }catch(ex){};
	 try{document.all('NativePlaceName').value= ""; }catch(ex){};
	try{document.all('Nationality').value= ""; }catch(ex){};
	try{document.all('RgtAddress').value= ""; }catch(ex){};
	try{document.all('Marriage').value= ""; }catch(ex){};
	try{document.all('MarriageName').value= ""; }catch(ex){};
	try{document.all('MarriageDate').value= ""; }catch(ex){};
	try{document.all('Health').value= ""; }catch(ex){};
	try{document.all('Stature').value= ""; }catch(ex){};
	try{document.all('Avoirdupois').value= ""; }catch(ex){};
	try{document.all('Degree').value= ""; }catch(ex){};
	try{document.all('CreditGrade').value= ""; }catch(ex){};
	try{document.all('BankCode').value= ""; }catch(ex){};
	try{document.all('BankAccNo').value= ""; }catch(ex){};
	try{document.all('AccName').value= ""; }catch(ex){};
	try{document.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{document.all('StartWorkDate').value= ""; }catch(ex){};
	try{document.all('Position').value= ""; }catch(ex){};
	try{document.all('Salary').value= ""; }catch(ex){};
	//try{document.all('OccupationType').value= ""; }catch(ex){};
	try{document.all('OccupationCodeName').value= ""; }catch(ex){};
	try{document.all('OccupationCode').value= ""; }catch(ex){};
	try{document.all('WorkType').value= ""; }catch(ex){};
	try{document.all('PluralityType').value= ""; }catch(ex){};
	try{document.all('SmokeFlag').value= ""; }catch(ex){};
	try{document.all('ContPlanCode').value= ""; }catch(ex){};
  try{document.all('GrpName').value= ""; }catch(ex){};
  try{document.all('HomeAddress').value= ""; }catch(ex){};
  try{document.all('HomeZipCode').value= ""; }catch(ex){};
  try{document.all('HomePhone').value= ""; }catch(ex){};
  try{document.all('HomeFax').value= ""; }catch(ex){};
  try{document.all('GrpFax').value= ""; }catch(ex){};
  try{document.all('Fax').value= ""; }catch(ex){};
  try{document.all('IDPeriodOfValidity').value= ""; }catch(ex){};
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
	try{document.all('PostalAddress').value= "";  }catch(ex){};
	try{document.all('ZipCode').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('Mobile').value= "";  }catch(ex){};
	try{document.all('EMail').value= "";  }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
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
	if(document.all('IDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			theFirstValue="";
			theSecondValue="";
			//document.all('IDNo').focus();
			return;

		}
		else
		{
			document.all('Birthday').value=tBirthday;
			document.all('Sex').value=tSex;
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
		
		   var sql55="";
            var sqlid55="BankContInput55";
            var mySql55=new SqlClass();
	        mySql55.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql55.setSqlId(sqlid55);//指定使用的Sql的id
	        mySql55.addSubPara( fm.ProposalGrpContNo.value );
	        sql55=mySql55.getString();
	         var tStr=sql55;
		
//	    var tStr= "	select * from lwmission where 1=1 "
//	    					+" and lwmission.processid = '0000000004'"
//	    					+" and lwmission.activityid = '0000002001'"
//	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
	    if (turnPage.strQueryResult)
	    {
	        alert("该团单合同已经做过保存！");
	        return;
	    }
		if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("团单合同信息未保存,不容许您进行 [录入完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//录入完毕
    }
    else if (wFlag ==2)//复核完毕确认
    {
        if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("未查询出团单合同信息,不容许您进行 [复核完毕] 确认！");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000002002";					//复核完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(document.all('ProposalGrpContNo').value == "")
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
		if(document.all('ProposalGrpContNo').value == "")
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
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./GrpInputConfirm.jsp";
    document.getElementById("fm").submit(); //提交
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
     	fm.Phone.value="";
      fm.Fax.value="";
    	fm.HomePhone.value="";
    	fm.EMail.value="";
    	}
		
		    var sql56="";
            var sqlid56="BankContInput56";
            var mySql56=new SqlClass();
	        mySql56.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql56.setSqlId(sqlid56);//指定使用的Sql的id
	        mySql56.addSubPara( fm.InsuredNo.value );
	        sql56=mySql56.getString();
	        strsql=sql56;
		
    //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
    document.all("InsuredAddressNo").CodeData=tCodeData;
}

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;
  window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer,"",sFeatures);
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
		
		    var sql57="";
            var sqlid57="BankContInput57";
            var mySql57=new SqlClass();
	        mySql57.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql57.setSqlId(sqlid57);//指定使用的Sql的id
	        mySql57.addSubPara( fm.Name.value );
			mySql57.addSubPara( fm.IDType.value );
			mySql57.addSubPara( fm.IDNo.value );
	        sql57=mySql57.getString();
	        strSQL=sql57;
		
//	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//                +"  and Name='"+fm.Name.value
//                +"' and IDType='"+fm.IDType.value
//                +"' and IDNo='"+fm.IDNo.value
//		+"' order by a.CustomerNo";
             turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
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
	document.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	document.getElementById("fm").submit(); //提交

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
	
	        var sql58="";
            var sqlid58="BankContInput58";
            var mySql58=new SqlClass();
	        mySql58.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql58.setSqlId(sqlid58);//指定使用的Sql的id
	        mySql58.addSubPara( tInsuredName );
			mySql58.addSubPara( tInsuredSex );
			mySql58.addSubPara( tBirthday );
			 mySql58.addSubPara( tInsuredNo );
			mySql58.addSubPara( fm.IDNo.value );
			mySql58.addSubPara( tInsuredNo );
	        sql58=mySql58.getString();
	        var sqlstr=sql58;
	
	
//  var sqlstr= "select * from ldperson where Name='"+tInsuredName
//            + "' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            + "' and CustomerNo<>'"+tInsuredNo+"'"
//            + " union select * from ldperson where 1=1 "
//
//            + " and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);

        if(arrResult==null)
        {
	   alert("该没有与该被保人保人相似的客户,无需校验");
	   return false;
        }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo,"window1",sFeatures);
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           document.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           document.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           document.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           document.all('BankAccNo').value="";
           document.all('BankCode').value="";
           document.all('AccName').value="";
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
		
		    var sql59="";
            var sqlid59="BankContInput59";
            var mySql59=new SqlClass();
	        mySql59.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql59.setSqlId(sqlid59);//指定使用的Sql的id
	        mySql59.addSubPara( insuredno );
			mySql59.addSubPara( fm.ContNo.value );
			mySql59.addSubPara( insuredno );
	        sql59=mySql59.getString();
	        var strhomea=sql59;
		
       //var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
	
	        var sql60="";
            var sqlid60="BankContInput60";
            var mySql60=new SqlClass();
	        mySql60.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql60.setSqlId(sqlid60);//指定使用的Sql的id
	        mySql60.addSubPara( fm.BankAccNo.value );
	        sql60=mySql60.getString();
	        var strSql=sql60;
	
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";
arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// 在初始化body时自动效验投保人信息
function InsuredChkNew(){
	//alert("aaa");
	//var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=fm.InsuredNo.value;
	var tInsuredName=document.all("Name").value;
	var tInsuredSex=fm.Sex.value;
	var tBirthday=fm.Birthday.value;
  
            var sql61="";
            var sqlid61="BankContInput61";
            var mySql61=new SqlClass();
	        mySql61.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql61.setSqlId(sqlid61);//指定使用的Sql的id
	        
			mySql61.addSubPara(tInsuredName);
		    mySql61.addSubPara(tInsuredSex);
			mySql61.addSubPara(tBirthday);
		    mySql61.addSubPara( tInsuredNo);
			mySql61.addSubPara( fm.IDNo.value);
		    mySql61.addSubPara(tInsuredNo);
			  
	        sql61=mySql61.getString();
	        var sqlstr=sql61;
  
//  var sqlstr= "select * from ldperson where Name='"+tInsuredName
//            + "' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            + "' and CustomerNo<>'"+tInsuredNo+"'"
//            + " union select * from ldperson where 1=1 "
//            + " and IDNo = '"+fm.IDNo.value
//            + "' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
  
  //alert(sqlstr);    
  
  arrResult = easyExecSql(sqlstr,1,0);
  
  if(arrResult==null)
  {
  	//disabled"投保人效验"按钮
		fm.InsuredChkButton.disabled = true;
		fm.InsuredChkButton2.disabled = true;
		
//				  return false;
  }
  else
  {
		fm.InsuredChkButton.disabled = "";
		fm.InsuredChkButton2.disabled = "";
					//如果有相同姓名、性别、生日不同客户号的用户显示"投保人校验"按钮
	}
}


//*******************************************************************
//将首期账号赋值到续期账号上
function theSameToFirstAccount(){
	//alert("aasf");
  if(fm.theSameAccount.checked==true){
  	document.all('SecAppntBankCode').value=document.all('AppntBankCode').value;
  	document.all('SecAppntAccName').value=document.all('AppntAccName').value;
//    alert(document.all('AppntBankAccNo').value);
//    alert(document.all('SecAppntBankAccNo').value);
  	document.all('SecAppntBankAccNo').value=document.all('AppntBankAccNo').value;
    return;
  }
  if(fm.theSameAccount.checked==false){
//  	document.all('AppntBankCode').value='';
//  	document.all('AppntAccName').value='';
//  	document.all('AppntBankAccNo').value='';
    return;
  }
}

//显示首期账号信息
function displayFirstAccount(){
  document.all('AppntBankCode').value = arrResult[0][0];
  //alert(document.all('AppntBankCode').value);
  document.all('AppntAccName').value = arrResult[0][1];
  document.all('AppntBankAccNo').value = arrResult[0][2];

  //查询出首期账号后，并同时赋值给续期账号
 	document.all('SecAppntBankCode').value=document.all('AppntBankCode').value;
	document.all('SecAppntAccName').value=document.all('AppntAccName').value;
 	document.all('SecAppntBankAccNo').value=document.all('AppntBankAccNo').value;

}

//强制进入人工核保
function forceUW(){
	//查询是否已经进行选择强制进入人工核保
  var ContNo=document.all("ContNo").value;
  
            var sql62="";
            var sqlid62="BankContInput62";
            var mySql62=new SqlClass();
	        mySql62.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql62.setSqlId(sqlid62);//指定使用的Sql的id
			mySql62.addSubPara(ContNo);
	        var sqlstr=mySql62.getString();
  
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
  	alert("不存在该投保单！");
  }
  else{
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

//查询是否已经添加过投保人
function checkAppnt(){
  var ContNo=document.all("ContNo").value;
  
            var sql63="";
            var sqlid63="BankContInput63";
            var mySql63=new SqlClass();
	        mySql63.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql63.setSqlId(sqlid63);//指定使用的Sql的id
			mySql63.addSubPara(ContNo);
	        var sqlstr=mySql63.getString();
  
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
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
 // var SubMissionID = document.all.SubMissionID.value;
  //alert("SubMissionID="+SubMissionID);

//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	//var ActivityID = document.all.ActivityID.value;
	//alert("ActivityID="+ActivityID);

//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = document.all('PrtNo2').value;
 // alert("PrtNo="+ document.all.PrtNo2.value);

	//var NoType = document.all.NoType.value;
	//alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("投保单号为空！");
		return;
	} 
	
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType=1";
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");

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
	
	//tongmeng 2008-01-03 add
	//汉化职业代码
	
}
/*********************************************************************
 *  合同代码框汉化
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
 function showContCodeName()
{
	quickGetName('comcode',fm.ManageCom,fm.ManageComName);
	quickGetName('SellType',fm.SellType,fm.sellTypeName);
}
/*********************************************************************
 *  投保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppntCodeName()
{

	quickGetName('vipvalue',fm.AppntVIPValue,fm.AppntVIPFlagname);
	quickGetName('Relation',fm.RelationToInsured,fm.RelationToInsuredName);
	quickGetName('IDType',fm.AppntIDType,fm.AppntIDTypeName);
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	quickGetName('Marriage',fm.AppntMarriage,fm.AppntMarriageName);
	quickGetName('NativePlace',fm.AppntNativePlace,fm.AppntNativePlaceName);
	quickGetName('OccupationCode',fm.AppntOccupationCode,fm.AppntOccupationCodeName);
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
	quickGetName('LicenseType',fm.AppntLicenseType,fm.AppntLicenseTypeName);
//  quickGetName('GetAddressNo',fm.AppntAddressNo,fm.AddressNoName);
//	quickGetName('province',fm.AppntProvince,fm.AppntProvinceName);
//	quickGetName('city',fm.AppntCity,fm.AppntCityName);
//	quickGetName('district',fm.AppntDistrict,fm.AppntDistrictName);
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	quickGetName('paymode',fm.PayMode,fm.FirstPayModeName);
	quickGetName('continuepaymode',fm.SecPayMode,fm.PayModeName);
	quickGetName('bank',fm.AppntBankCode,fm.FirstBankCodeName);
	quickGetName('bank',fm.SecAppntBankCode,fm.AppntBankCodeName);
	quickGetName('incomeway',fm.IncomeWay0,fm.IncomeWayName0);
	
	
}

/*********************************************************************
 *  被保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredCodeName()
{
/*
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
*/
	quickGetName('SequenceNo',fm.SequenceNo,fm.SequenceNoName);
	quickGetName('vipvalue',fm.VIPValue1,fm.AppntVIPFlagname1);
	quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
	quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
	quickGetName('IDType',fm.IDType,fm.IDTypeName);
	quickGetName('Sex',fm.Sex,fm.SexName);
	quickGetName('Marriage',fm.Marriage,fm.MarriageName);
	quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
	quickGetName('occupationtype',fm.OccupationType,fm.OccupationTypeName);
	quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);
	quickGetName('LicenseType',fm.LicenseType,fm.LicenseTypeName);
	//quickGetName('GetAddressNo',fm.InsuredAddressNo,fm.InsuredAddressNoName);
//	quickGetName('Province',fm.InsuredProvince,fm.InsuredProvinceName);
//	quickGetName('City',fm.InsuredCity,fm.InsuredCityName);
//	quickGetName('District',fm.InsuredDistrict,fm.InsuredDistrictName);
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
	quickGetName('incomeway',fm.IncomeWay,fm.IncomeWayName);
}

//问题件影像查询
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "") return;
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	
}
//查询业务员告知信息
function getImpartInitInfo()
{
	var tContNo = fm.ContNo.value;

	var strSQL ="select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";

	turnPage.queryModal(strSQL, AgentImpartGrid);
	
	        var sql64="";
            var sqlid64="BankContInput64";
            var mySql64=new SqlClass();
	        mySql64.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql64.setSqlId(sqlid64);//指定使用的Sql的id
			mySql64.addSubPara(tContNo);
	        var aSQL=mySql64.getString();
	
//        var aSQL="SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
//		+"FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='"+tContNo+"' "
//		+"WHERE a.impartver='05'";

	turnPage.queryModal(aSQL,AgentImpartGrid);
	return true;
}

function getLostInfo()
{
	//tongmeng 2007-09-10  add
	//显示代理机构名称
	if(document.all('AgentCom').value!=null&&document.all('AgentCom').value!='')
	{
		        var sql65="";
	            var sqlid65="BankContInput65";
	            var mySql65=new SqlClass();
		        mySql65.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
		        mySql65.setSqlId(sqlid65);//指定使用的Sql的id
				mySql65.addSubPara(document.all('AgentCom').value);
		        var strSQL=mySql65.getString();
		
		//var strSQL = "select name from lacom where agentcom='"+ document.all('AgentCom').value+"'";
		arrResult = easyExecSql(strSQL,1,0);
		if(arrResult!==null)
		{
			document.all('InputAgentComName').value=arrResult[0][0]; 
		}
	}
	//var strSQL = "select bankcode from lacom where agentcom='"+ document.all('AgentCom').value+"'";
	//alert("1:"+strSQL);
	//arrResult = easyExecSql(strSQL,1,0);
	//if(arrResult!==null)
	//  { 
	  	//alert(arrResult[0][0]);
      //tongmeng 2007-09-10 modify
      //本地化工作取消银行编码
      //document.all('BankCode1').value=arrResult[0][0]; 
	//	}
	if(document.all('AgentCode').value!=null&&document.all('AgentCode').value!='')
	{	
				 var sql66="";
	            var sqlid66="BankContInput66";
	            var mySql66=new SqlClass();
		        mySql66.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
		        mySql66.setSqlId(sqlid66);//指定使用的Sql的id
				mySql66.addSubPara(document.all('AgentCode').value);
		       strSQL=mySql66.getString();
			
		//strSQL = "select name from laagent where agentcode='" + document.all('AgentCode').value +"'";
		//alert("2:"+strSQL);
		arrResult = easyExecSql(strSQL,1,0);
		
		if(arrResult!==null)
		  {
	      document.all('AgentName').value=arrResult[0][0];
			}
	}
	
	if(document.all('AgentGroup').value!=null&&document.all('AgentGroup').value!='')
	{	
			 var sql67="";
            var sqlid67="BankContInput67";
            var mySql67=new SqlClass();
	        mySql67.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql67.setSqlId(sqlid67);//指定使用的Sql的id
			mySql67.addSubPara(document.all('AgentGroup').value);
	       strSQL=mySql67.getString();
		
	//strSQL = "select name from labranchgroup where agentgroup='" + document.all('AgentGroup').value +"'";
	//alert("3:"+strSQL);
	arrResult = easyExecSql(strSQL,1,0);
	
	if(arrResult!==null)
	  {
      document.all('BranchAttr').value=arrResult[0][0];
		}
	}
	
	if(document.all('ProposalContNo').value!=null&&document.all('ProposalContNo').value!='')
	{	
		    var sql68="";
            var sqlid68="BankContInput68";
            var mySql68=new SqlClass();
	        mySql68.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql68.setSqlId(sqlid68);//指定使用的Sql的id
			mySql68.addSubPara(document.all('ProposalContNo').value);
	       strSQL=mySql68.getString();
		
	//strSQL = "select BankAgent,AgentBankCode from lccont where ProposalContNo='" + document.all('ProposalContNo').value +"'";
  arrResult = easyExecSql(strSQL,1,0);
  
  if (arrResult!==null)
    {
      document.all('CounterCode').value=arrResult[0][0];
      //tongmeng 2007-09-10 本地化取消银行类型
      //document.all('AgentBankCode').value=arrResult[0][1];      
  	}
  }

} 

    	





var cacheWin=null;
/**
* 根据代码选择的代码查找并显示名称，显示指定的一个
* strCode - 代码选择的代码
* showObjCode - 代码存放的界面对象
* showObjName - 要显示名称的界面对象
*/
function quickGetName(strCode, showObjc, showObjn) {
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//内存中有数据标志
	var showObjn;
	var showObjc;
	//遍历所有FORM


	//如果代码栏的数据不为空，才查询，否则不做任何操作
	if (showObjc.value != "")
	{
		//寻找主窗口
		if(cacheWin==null)
		{
			 cacheWin = searchMainWindow(this);
			if (cacheWin == false) { cacheWin = this; }
		}

		//如果内容中有数据，从内容中取数据
		if(strCode!='OccupationCode')
		{
			if (cacheWin.parent.VD.gVCode.getVar(strCode))
			{
				arrCode = cacheWin.parent.VD.gVCode.getVar(strCode);
				cacheFlag = true;
			}
			else 
			{
				urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
				sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
				//连接数据库进行CODE查询，返回查询结果给strQueryResult
				//strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
				var name='提示';   //网页名称，可为空; 
				var iWidth=150;      //弹出窗口的宽度; 
				var iHeight=1;     //弹出窗口的高度; 
				var iTop = 1; //获得窗口的垂直位置 
				var iLeft = 1;  //获得窗口的水平位置 
				strQueryResult = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				strQueryResult.focus();
			}
		}
		else if(strCode=='OccupationCode')
		{
			
			var sql69="";
            var sqlid69="BankContInput69";
            var mySql69=new SqlClass();
	        mySql69.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql69.setSqlId(sqlid69);//指定使用的Sql的id
			mySql69.addSubPara(showObjc.value);
	       var tSQL=mySql69.getString();
			
//				var tSQL = "select trim(OccupationCode), trim(OccupationName), trim(OccupationType),  "
//				         + " (select codename from ldcode where codetype='occupationtype'  and trim(code)=trim(OccupationType)) from LDOccupation "
//				         + " where 1 = 1and worktype = 'GR' and OccupationCode='"+showObjc.value+"' order by OccupationCode ";
				strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);        
				         
		}
		//拆分成数组
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("页面缺少引用EasyQueryVer3.js");
				}
				strCodeSelect = "";
				for (i=0; i<arrCode.length; i++)
				{
					strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
					strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				//将拆分好的数据放到内存中
				cacheWin.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
				//无论是否有数据从服务器端得到,都设置该变量
				cacheWin.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
			}
			cacheFlag = false;
			for (i=0; i<arrCode.length; i++)
			{
				if (showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
					break;
				}
			}
		}
		catch(ex)
		{}
	}
}

/**
* 计算投保人被保人年龄《投保日期与生日之差=投保人被保人年龄》,2005-11-18日添加
* 参数，出生日期yy－mm－dd；投保日期yy－mm－dd
* 返回  年龄
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("生日"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("投保日期"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//当前月大于出生月
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//当前月小于出生月
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//当前月等于出生月的时候，看出生日
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}


//根据地址代码查询地址汉字信息,查询地址代码表<ldaddress>
//所需参数,地址级别<province--省;city--市;district--区/县;>,地址代码<代码表单名>,地址汉字信息<汉字表单名>
//返回,直接为--地址汉字信息<汉字表单名>--赋值
function getAddressName(strCode, strObjCode, strObjName)
{
	var PlaceType="";
	var PlaceCode="";
	//判断地址级别,<province--省--01;city--市--02;district--区/县--03;>
	switch(strCode)
	{
	   case "province":
	      	PlaceType="01";
	      	break;
	   case "city":
	      	PlaceType="02";
	      	break;
	   case "district":
	      	PlaceType="03";
	      	break;   
	   default:
	   		PlaceType="";   		   	
	}
	//遍历FORM中的所有ELEMENT
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//寻找代码选择元素
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjCode)
			{
				strObjCode = window.document.forms[formsNum].elements[elementsNum];
			}
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjName)
			{
				strObjName = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//如果代码栏的数据长度为[6]才查询，否则不做任何操作	
//	strObjName.value="";
	if(strObjCode.value !="")
	{
		PlaceCode=strObjCode.value;
		
			var sql70="";
            var sqlid70="BankContInput70";
            var mySql70=new SqlClass();
	        mySql70.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql70.setSqlId(sqlid70);//指定使用的Sql的id
			mySql70.addSubPara(PlaceCode);
			mySql70.addSubPara(PlaceType);
	      var strSQL=mySql70.getString();
		
		//var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
		var arrAddress=easyExecSql(strSQL);
		if(arrAddress!=null)
		{   
			strObjName.value = arrAddress[0][1];
		}	
			
		else
		{
			strObjName.value="";
		}	
	}	
}

//查询记事本
function checkNotePad(prtNo,LoadFlag){
	
			var sql71="";
            var sqlid71="BankContInput71";
            var mySql71=new SqlClass();
	        mySql71.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql71.setSqlId(sqlid71);//指定使用的Sql的id
			mySql71.addSubPara(prtNo);
	      var strSQL=mySql71.getString();
	
//	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('NotePadButton"+LoadFlag+"').value='记事本查看 （共"+arrResult[0][0]+"条）'");
	
}


//控制界面按钮的明暗显示
function ctrlButtonDisabled(tContNo,tLoadFlag){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  if(tLoadFlag==5){
    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "ApproveQuestQuery";
    arrButtonAndSQL[0][1] = "问题件查询";
	
			var sql72="";
            var sqlid72="BankContInput72";
            var mySql72=new SqlClass();
	        mySql72.setResourceName("app.BankContInputSql"); //指定使用的properties文件名
	        mySql72.setSqlId(sqlid72);//指定使用的Sql的id
			mySql72.addSubPara(tContNo);
	      var strSQL72=mySql72.getString();
	
    arrButtonAndSQL[0][2] = strSQL72;//"select * from lcissuepol where 2 = 2 and OperatePos in ('0', '1', '5', 'A', 'I') and ContNo = '"+tContNo+"'";
  }


  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
  	
}

/**投保人信息已修改，投保人与被保人关系为本人，同步修改被保人信息。
*---yeshu,20071220
*/
function updateInsured()
{
//	alert("开发截至2007-12-20，此功能待开发");
	document.all('InsuredNo').value=document.all('AppntNo').value;
	displayissameperson();
	modifyRecord();
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

/**
 * 投被保人强制关联
 * customerType: 0-投保人  1-被保人
 */
function customerForceUnion(customerType){
	var customerno = "";
	var prtNo = fm.PrtNo.value;
	if(customerType=="0"){
		customerno = fm.AppntNo.value;
	} else if(customerType="1"){
		customerno = fm.InsuredNo.value;
	}
	var newWindow = window.open("../app/CustomerForceUnionMain.jsp?customerno="+customerno+"&prtno="+prtNo,"CustomerForceUnionMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
