//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
this.window.onfocus=myonfocus;
var flag = '0';
/*********************************************************************
 *  保存个体投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
	if(fm.PrtNo.value=='')
	{ 
		 alert("请输入印刷号信息!!");
		 return;
	}
	if(fm.ContNo.value!="")
	{ 
		 alert("合同信息已保存!!");
		 return;
	}
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
	fm.stype.value = "1";
	fm.action="FirstTrialContSave.jsp"
	fm.submit();  
}

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	initImpartGrid();
	initDetail();
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
		showDiv(inputButton, "false");	
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
		showInfo = window.open("./ContQueryMain.jsp?ContNo=" + cContNo);
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
		if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		alert("证件类型和证件号码输入有误");	
		return false;		
		}
	
	var tGrpProposalNo = "";
	tGrpProposalNo = document.all( 'ProposalContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "请先做投保单查询操作，再进行修改!" );
	else
	{
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
			document.all( 'fmAction' ).value = mAction;
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
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			fm.submit(); //提交
		}
	 }
  }
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
	//alert("1");
	//alert(fm.ContNo.value);
	//alert(fm.FamilyType.value);
	var tAppntNo = fm.AppntNo.value;
	var tAppntName = fm.AppntName.value;
	fm.ContNo.value=fm.ProposalContNo.value;
	if( fm.ContNo.value == "" )
	{
		alert("您必须先录入合同信息才能进入被保险人信息部分。");
		return false
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
	strSql = "select missionid from lwmission where activityid = '0000001062' and missionprop1 ='"+fm.PrtNo.value+"'";
	arrResult=easyExecSql(strSql);
	if(arrResult!=null)
	{
		alert('已保存,不能再次保存!!');
		return;
	}
	var strSql = "select missionid from lwmission where (activityid = '0000001108' or activityid = '0000001106') and missionid = '" + fm.MissionID.value +"'";
	arrResult=easyExecSql(strSql);
	if(arrResult!=null)
	{
		alert('有未打印的通知书!请打印!!');
		return;
	}
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
	fm.stype.value = "0";
	fm.action = "./FirstTrialContSave.jsp";
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
try { mSwitch.addVar('AddressNo','',fm.AddressNo.value); } catch(ex) { };		
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
try { mSwitch.addVar('AppntBankAccNo','',document.all('AppntBankAccNo').value); } catch(ex) { };    
try { mSwitch.addVar('AppntBankCode','',document.all('AppntBankCode').value); } catch(ex) { };    
try { mSwitch.addVar('AppntAccName','',document.all('AppntAccName').value); } catch(ex) { };      
   
   
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
  //alert("here:" + arrQueryResult + "\n" + mOperate);
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( flag == 1 )	{		// 查询投保单	
			arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("未查到投保人信息");
			} else {
			    displayAppnt(arrResult[0]);
			}
		}
		if( flag == 2 )	{		// 投保人信息
			arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("未查到投保人信息");
			} else {
			  displayInuserd(arrResult[0]);
			}
		}
	}
	
	mOperate = 0;		// 恢复初态
}
/*********************************************************************
 *  投保单信息初始化函数。以loadFlag标志作为分支
 *  参数  ：  投保单印刷号
 *  返回值：  无
 *********************************************************************
 */
function detailInit(PrtNo){
try{
  if(PrtNo==null)
    return;
  arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
  if(arrResult==null){
    alert(未得到投保单信息);
    return;
  }else{
    displayLCCont();       //显示投保单详细内容
  }
  arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
  if(arrResult==null){
    alert("未得到投保人信息");
    return;
  }else{
    displayContAppnt();       //显示投保人的详细内容
    //displayAccount();			//显示头投保人帐户信息
  }
  var tContNo = arrResult[0][1];  
  var tCustomerNo = arrResult[0][3];		// 得到投保人客户号
  var tAddressNo = arrResult[0][9]; 		// 得到投保人地址号
  getdetailaddress();
/*  
  arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
  if(arrResult==null){
    alert("未得到用户信息");
    return;
  }else{
    displayAppnt();       //显示投保人详细内容
    emptyUndefined();
  } 
*/ 
 /*   
  arrResult=easyExecSql("select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+tCustomerNo+"'",1,0);
  if(arrResult==null){
    alert(未得到投保人地址信息);
    return;
  }else{
    displayAddress();       //显示投保人地址详细内容
  }*/
  fm.AddressNo.value=tAddressNo;
  getdetailaddress();//显示投保人地址详细内容
  var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
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
  if(loadFlag=="5"||loadFlag=="25"){
    showCodeName();
  }
}catch(ex){}
 
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
try{document.all('AddressNo').value= arrResult[0][0];}catch(ex){}; 
try{document.all('AppntPostalAddress').value= arrResult[0][1];}catch(ex){}; 
try{document.all('AppntZipCode').value= arrResult[0][2];}catch(ex){}; 
try{document.all('AppntPhone').value= arrResult[0][3];}catch(ex){}; 
try{document.all('AppntMobile').value= arrResult[0][4];}catch(ex){}; 
try{document.all('AppntEMail').value= arrResult[0][5];}catch(ex){}; 
//try{document.all('GrpName').value= arrResult[0][6];}catch(ex){}; 
try{document.all('AppntGrpPhone').value= arrResult[0][6];}catch(ex){}; 
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
try{document.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};              
try{document.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};             
try{document.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};            
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
}
/*********************************************************************
 *  把查询返回的投保人数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
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
try{document.all('VIPValue').value= arrResult[0][35];}catch(ex){};           
try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};        
try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};        
try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};      
try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};      
try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};      
try{document.all('AppntGrpName').value= arrResult[0][41];}catch(ex){}; 

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
try{document.all('AppntGrpPhone').value= "";}catch(ex){}; 
try{document.all('CompanyAddress').value= "";}catch(ex){}; 
try{document.all('AppntGrpZipCode').value= "";}catch(ex){};  
try{document.all('AppntGrpFax').value= "";}catch(ex){};  
}
/**
 *投保单详细内容显示
 */
function displayLCCont() {
    try { document.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };                    
    try { document.all('ContNo').value = arrResult[0][1]; } catch(ex) { };                       
    try { document.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };               
    try { document.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };                        
    try { document.all('ContType').value = arrResult[0][4]; } catch(ex) { };                     
    try { document.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };                
    try { document.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };                     
    try { document.all('PolType ').value = arrResult[0][7]; } catch(ex) { };                     
    try { document.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };                     
    try { document.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };                    
    try { document.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };                 
    try { document.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };                    
    try { document.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { document.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { };                 
    try { document.all('AgentCode1 ').value = arrResult[0][14]; } catch(ex) { };                 
    try { document.all('AgentType').value = arrResult[0][15]; } catch(ex) { };                   
    try { document.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };                    
    try { document.all('Handler ').value = arrResult[0][17]; } catch(ex) { };                    
    try { document.all('Password').value = arrResult[0][18]; } catch(ex) { };                    
    try { document.all('AppntNo ').value = arrResult[0][19]; } catch(ex) { };                    
    try { document.all('AppntName').value = arrResult[0][20]; } catch(ex) { };                   
    try { document.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };                    
    try { document.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };              
    try { document.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };                 
    try { document.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };                   
    try { document.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };                   
    try { document.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };                 
    try { document.all('InsuredSex ').value = arrResult[0][27]; } catch(ex) { };                 
    try { document.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };             
    try { document.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };              
    try { document.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };                 
    try { document.all('PayIntv ').value = arrResult[0][31]; } catch(ex) { };                    
    try { document.all('PayMode ').value = arrResult[0][32]; } catch(ex) { };                    
    try { document.all('PayLocation').value = arrResult[0][33]; } catch(ex) { };                 
    try { document.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };                
    try { document.all('OutPayFlag ').value = arrResult[0][35]; } catch(ex) { };                 
    try { document.all('GetPolMode ').value = arrResult[0][36]; } catch(ex) { };                 
    try { document.all('SignCom ').value = arrResult[0][37]; } catch(ex) { };                    
    try { document.all('SignDate').value = arrResult[0][38]; } catch(ex) { };                    
    try { document.all('SignTime').value = arrResult[0][39]; } catch(ex) { };                    
    try { document.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };                   
    try { document.all('BankCode').value = arrResult[0][41]; } catch(ex) { };                    
    try { document.all('BankAccNo').value = arrResult[0][42]; } catch(ex) { };                   
    try { document.all('AccName ').value = arrResult[0][43]; } catch(ex) { };                    
    try { document.all('PrintCount ').value = arrResult[0][44]; } catch(ex) { };                 
    try { document.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };                   
    try { document.all('Lang ').value = arrResult[0][46]; } catch(ex) { };                       
    try { document.all('Currency').value = arrResult[0][47]; } catch(ex) { };                    
    try { document.all('Remark').value = arrResult[0][48]; } catch(ex) { };                      
    try { document.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };                    
    try { document.all('Mult ').value = arrResult[0][50]; } catch(ex) { };                       
    try { document.all('Prem ').value = arrResult[0][51]; } catch(ex) { };                       
    try { document.all('Amnt ').value = arrResult[0][52]; } catch(ex) { };                       
    try { document.all('SumPrem ').value = arrResult[0][53]; } catch(ex) { };                    
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
    try { document.all('UWOperator ').value = arrResult[0][66]; } catch(ex) { };                 
    try { document.all('UWDate').value = arrResult[0][67]; } catch(ex) { };                      
    try { document.all('UWTime').value = arrResult[0][68]; } catch(ex) { };                      
    try { document.all('AppFlag ').value = arrResult[0][69]; } catch(ex) { };                    
    try { document.all('PolApplyDate').value = arrResult[0][70]; } catch(ex) { };                
    try { document.all('GetPolDate ').value = arrResult[0][71]; } catch(ex) { };                 
    try { document.all('GetPolTime ').value = arrResult[0][72]; } catch(ex) { };                 
    try { document.all('CustomGetPolDate ').value = arrResult[0][73]; } catch(ex) { };           
    try { document.all('State').value = arrResult[0][74]; } catch(ex) { };                       
    try { document.all('Operator').value = arrResult[0][75]; } catch(ex) { };                    
    try { document.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };                    
    try { document.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };                    
    try { document.all('ModifyDate ').value = arrResult[0][78]; } catch(ex) { };                 
    try { document.all('ModifyTime ').value = arrResult[0][79]; } catch(ex) { };    
                                                                                             
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
	 flag = '1';
	 if (document.all("AppntNo").value == "" && loadFlag == "1") {                    
    showAppnt1();                                                            
    } else if (loadFlag != "1" && loadFlag != "2") {                         
     alert("只能在投保单录入时进行操作！");                                 
    } else {              
   //alert("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'");                             
    if (arrResult == null) {                                                 
      alert("未查到投保人信息");                                             
      displayAppnt(new Array());                                             
      emptyUndefined();                                                      
    } else {                                                                 
      displayAppnt(arrResult[0]);                                            
    }                                                                        
  }                                                                          
}                                                                            
                                                                             
function showAppnt1()                                                        
{                                                                            
	if( mOperate == 0 )                                                          
	{                                                                            
		mOperate = 2;                                                        
		showInfo = window.open( "../sys/LDPersonQueryNew.html" );               
	}                                                                            
}                                                                            
function showInsured1()                                                        
{                                                                            
	if( mOperate == 0 )                                                          
	{                                                                            
		mOperate = 1;                                                        
		showInfo = window.open( "../sys/LDPersonQueryNew.html" );               
	}                                                                            
}                                                                                
function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //团险业务员查询branchtype='2' Modify by liuqh 2008-12-02
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}

//问题件录入
function QuestInput()
{
	cContNo = fm.ContNo.value;  //保单号码
	if(cContNo == "")
	{
		alert("尚无合同投保单号，请先保存!");
	}
	else
	{	
		window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1");
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
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
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
	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
}                                                                             
function getdetail()
{
var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"'";
var arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.AppntBankCode.value = arrResult[0][0];
      fm.AppntAccName.value = arrResult[0][1];
    }
else{  
     fm.AppntBankCode.value = '';
     fm.AppntAccName.value = '';
}	
}	                                                                             
function getdetailwork()
{
var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
var arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.AppntOccupationType.value = arrResult[0][0];

    }
else{  
     fm.AppntOccupationType.value = '';
}	
}                                                                             
function getdetailaddress(){
 	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        arrResult=easyExecSql(strSQL);                         
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};         
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};          
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
   try{document.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};       
   try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};         
   try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};             
   try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};          
   try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};              
   try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};                 
   try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};            
   try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};         
   try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};             
   try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){}; 
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
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
    document.all("AddressNo").CodeData=tCodeData;
} 
function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="GetAddressNo"){
 	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        arrResult=easyExecSql(strSQL);                         
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};         
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};          
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
   try{document.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};       
   try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};        
   try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};             
   try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};          
   try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};              
   try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};                 
   try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};            
   try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};         
   try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};             
   try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};          
 }	
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
                document.all('AppntPhone').value= document.all('AppntGrpPhone').value;
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
	var i=Sex.indexOf("-");
	Sex=Sex.substring(0,i);
        var sqlstr="select *from ldperson where Name='"+fm.AppntName.value+"' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value+"' and CustomerNo<>'"+fm.AppntNo.value+"'";
        arrResult = easyExecSql(sqlstr,1,0);
        if(arrResult==null)
        {
	  alert("该没有与该投保人相似的客户,无需校验");
	  return false;
        }	
	window.open("../uwgrp/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A","window1");
}                                                                             
function queryInsuredNo()
{
	 flag = '2';
	 if (document.all("InsuredNo").value == "" && loadFlag == "1") {                    
    showInsured1();                                                            
    } else if (loadFlag != "1" && loadFlag != "2") {                         
     alert("只能在投保单录入时进行操作！");                                 
    } else {              
    if (arrResult == null) {                                                 
      alert("未查到投保人信息");                                             
      //displayAppnt(new Array());                                             
      emptyUndefined();                                                      
    } else {                                                                 
      //displayAppnt(arrResult[0]);                                            
    }                                                                        
  } 	 
}                                                                             
function displayInuserd()                                                                         
{
try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};              
try{document.all('InsuredName').value= arrResult[0][1]; }catch(ex){};            
try{document.all('InsuredSex').value= arrResult[0][2]; }catch(ex){};             
try{document.all('InsuredtBirthday').value= arrResult[0][3]; }catch(ex){};        
try{document.all('InsuredtIDType').value= arrResult[0][4]; }catch(ex){};          
try{document.all('InsuredtIDNo').value= arrResult[0][5]; }catch(ex){};        
try{document.all('InsuredMarriage').value= arrResult[0][11];}catch(ex){}; 
}  
function AddInsured()
{
	if(GrpGrid.mulLineCount==1)
	{
		 alert('个单下面只能有一个保险人!!');
		 return;
	}
	if(fm.ContNo.value=="")
	 { 
	 	  alert("请先保存添加保单!!");
	 	  return;
	 }
	if(fm.InsuredName.value==""||fm.InsuredIDType.value==""||fm.InsuredIDNo.value==""||fm.InsuredBirthday.value==""||fm.InsuredSex.value=="")
	{
		alert("请输入完整资料!!");
		return;
	}
	
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
	fm.action="FirstTrialInsuredSave.jsp"
	fm.fmAction.value='INSERT||CONTINSURED';
	fm.submit();  
}                                                                             
//发通知书
function showHealth()
{
	var tSel = GrpGrid.mulLineCount;
	if(tSel=='0')
	{
		 alert("请先录入被保人");
		 return;
	}
  MakeWorkFlow();
	var strSQL = "select missionid from lwmission where activityid = '0000001106' and missionid = '" + fm.MissionID.value +"'";
	arrResult=easyExecSql(strSQL);
	if(arrResult!=null)
	{
		 alert('请先打印已发体检通知书！！');
		 return;
	}
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

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("../uwgrp/UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}
//生调通知书录入
function showReport()
{
	var tSel = GrpGrid.mulLineCount;

	if(tSel=='0')
	{
		 alert("请先录入被保人");
		 return;
	}
	MakeWorkFlow();
	var strSQL = "select missionid from lwmission where activityid = '0000001108' and missionid = '" + fm.MissionID.value +"'";
	arrResult=easyExecSql(strSQL);
	if(arrResult!=null)
	{
		 alert('请先打印已发体检通知书！！');
		 return;
	}
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

  var cContNo=fm.ContNo.value;

  var cMissionID =fm.MissionID.value; 
  var cSubMissionID =fm.SubMissionID.value; 
  var cPrtNo = fm.PrtNo.value;

  window.open("../uwgrp/UWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID,"window2");  	
  showInfo.close();  
}
//自动工作流初始化
function MakeWorkFlow()
{
	fm.action="FirstTrialAutoChk.jsp"
	fm.submit();
}
//初始化投保人,被保人信息
function initDetail()
{
	 	var strSQL = "select contno,agentcode,agentgroup,appntno,appntname,AppntSex,appntbirthday,appntidtype,appntidno,salechnl from lccont where prtno = '" + prtNo +"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
    	fm.ContNo.value=arrResult[0][0];
    	fm.AgentCode.value=arrResult[0][1];
    	fm.AgentGroup.value=arrResult[0][2];
    	fm.AppntNo.value=arrResult[0][3];
    	fm.AppntName.value=arrResult[0][4];
    	fm.AppntSex.value=arrResult[0][5];
    	fm.AppntBirthday.value=arrResult[0][6];
    	fm.AppntIDType.value=arrResult[0][7];
    	fm.AppntIDNo.value=arrResult[0][8];
    	fm.SaleChnl.value=arrResult[0][9];
    }
    if(fm.ContNo.value!='')
    {
      strSQL = "select insuredno,name from lcinsured where trim(contno) = '" + fm.ContNo.value +"'"
      arrResult=easyExecSql(strSQL);
      if(arrResult!=null)
      {
      	for(i=0;i<arrResult.length;i++)
      	{
      		GrpGrid.addOne("GrpGrid");
      		GrpGrid.setRowColData(GrpGrid.mulLineCount-1,1,arrResult[i][0]);
      		GrpGrid.setRowColData(GrpGrid.mulLineCount-1,2,arrResult[i][1]);
      	}
      }
    }
}
//删除被保人
function DelInsured()
{
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<GrpGrid.mulLineCount; i++) {
    if (GrpGrid.getSelNo(i)) { 
      checkFlag = GrpGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
    fm.InsuredName.value = GrpGrid.getRowColData(checkFlag - 1, 2);
    fm.InsuredNo.value = GrpGrid.getRowColData(checkFlag - 1, 1);
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = 'FirstTrialInsuredSave.jsp';
    fm.fmAction.value='DELETE||CONTINSURED';
    fm.submit();
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}
//打印体检通知书
function PrintHealth()
{
	var strSQL = "select missionid,submissionid,missionprop3 from lwmission where activityid = '0000001106' and missionprop1 = '" + fm.PrtNo.value +"'";
	arrResult=easyExecSql(strSQL);
	if(arrResult==null)
	{
		 alert('没有需要打印的体检通知书!!');
	}
	window.open( "../uwgrp/BodyCheckPrintSave.jsp?PrtSeq="+arrResult[0][2]+"&MissionID="+arrResult[0][0]+"&SubMissionID="+arrResult[0][1] ); 
}                                                                           
//打印生调通知书
function PrintReport()
{
	var strSQL = "select missionid,submissionid,missionprop3 from lwmission where activityid = '0000001108' and missionprop1 = '" + fm.PrtNo.value +"'";
	arrResult=easyExecSql(strSQL);
	if(arrResult==null)
	{
		 alert('没有需要打印的体检通知书!!');
	}
	fm.PrtSeq.value = arrResult[0][2];
	fm.MissionID.value = arrResult[0][0];
	fm.SubMissionID.value = arrResult[0][1];
	fm.action = '../uwgrp/MeetF1PSave.jsp';
	fm.submit();
}                                                                               
//判断是不是投保人和被保人是不是同一人
function isSamePerson()
{
	if (fm.SamePersonFlag.checked==true) 
  { 
  	  document.all["DivLCInsuredInd"].style.display = 'none';
      displayissameperson();      
  }else if(fm.SamePersonFlag.checked==false)
  {
  	  document.all["DivLCInsuredInd"].style.display = '';
      Quickissameperson();  	
  }
}                                                                            
function displayissameperson()
{
	    fm.InsuredNo.value = fm.AppntNo.value;
    	fm.InsuredName.value = fm.AppntName.value;
    	fm.InsuredSex.value= fm.AppntSex.value;
    	fm.InsuredBirthday.value=fm.AppntBirthday.value;
    	fm.InsuredIDType.value=fm.AppntIDType.value;
    	fm.InsuredIDNo.value=fm.AppntIDNo.value;
}                          
function Quickissameperson()
{
	    fm.InsuredNo.value = "";
    	fm.InsuredName.value = "";
    	fm.InsuredSex.value= "";
    	fm.InsuredBirthday.value="";
    	fm.InsuredIDType.value="";
    	fm.InsuredIDNo.value="";	
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
		document.all('AppntBirthday').value=getBirthdatByIdNo(iIdNo);
		document.all('AppntSex').value=getSexByIDNo(iIdNo);
	}	
} 
function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.AppntIDNo.value=""; 
  }
}   
function getBirthdaySexByIDNo1(iIdNo)
{
	if(document.all('InsuredIDType').value=="0")
	{
		document.all('InsuredBirthday').value=getBirthdatByIdNo(iIdNo);
		document.all('InsuredSex').value=getSexByIDNo(iIdNo);
	}	
} 
function checkidtype1()
{
	if(fm.InsuredIDType.value==""&&fm.InsuredIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.InsuredIDNo.value=""; 
  }
}                                                
                                                                             
                                                                             
                                                                             
                                                                             