//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
var mDebug="0";
var InputFlag=true;
var addFlag = false; //是否有应收记录，是否允许添加
var contFlag = false; //判断是否有保单记录，是否允许添加
var edorFlag = false; //判断是否有保单记录，是否允许添加
var clmFlag = false; //判断是否有保单记录，是否允许添加
var urgeFlag = false; //判断是否有保单记录，是否允许添加

var ImportPath; 

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

var arrCardRisk;

var todayDate = new Date();
var sdate = todayDate.getDate();
var smonth= todayDate.getMonth() + 1;
var syear= todayDate.getYear();

var mySql=new SqlClass();
mySql.setJspName("../../finfee/FinFeeInputSql.jsp");
//提交，保存按钮对应操作
function submitForm(){
	
	if(FinFeeGrid.mulLineCount==0){
		alert(" 请录入财务收费信息！");
		return false;
	}
	if(TempToGrid.mulLineCount==0){
		alert(" 请添加业务费用信息！");
		return false;
	}
	if(!parseFloat(fm.OperateSub.value)==0){
		alert("财务收费金额与业务费用合计不匹配！");
		return false;
	}
	var ccontno="";
	//校验本次交费的币种是否与新契约录单时的币种一致，若不一致，则给出提示！
	//新单交费
	if(fm.InputNo1.value!=null&&fm.InputNo1.value!="")
	{
		ccontno=fm.InputNo1.value;
		}
	//续期交费
	else if(fm.InputNo3.value!=null&&fm.InputNo3.value!="")
	{
		ccontno=fm.InputNo3.value;
		}
	//续期预收交费
	else if
	(fm.InputNo5.value!=null&&fm.InputNo5.value!="")
	{
		ccontno=fm.InputNo5.value;
		}
	//不定期交费
	else if(fm.InputNo22.value!=null&&fm.InputNo22.value!="")
	{
		ccontno=fm.InputNo22.value;
		}
		//保全交费
	else if(fm.InputNo13.value!=null&&fm.InputNo13.value!="")
	{
		mySql.setSqlId("TempFeeInputInputSql_EdorCont");
    mySql.addPara('EdorAcceptNo', fm.InputNo13.value);
    var strArray1=easyExecSql(mySql.getString());
	  if(strArray1 != null && strArray1 != "")
	  {
	  	ccontno=strArray1[0][0];
	  	}
		}
		//理赔交费
//  	else if(fm.InputNo12.value!=null&&fm.InputNo12.value!="")
//	  {
//		mySql.setSqlId("TempFeeInputInputSql_ClaimCont");
//    mySql.addPara('ClmNo', fm.InputNo12.value);
//    var strArray1=easyExecSql(mySql.getString());
//	  if(strArray1 != null && strArray1 != "")
//	  {
//	  	ccontno=strArray1[0][0];
//	  	}
//		 }
	if(ccontno!="")
	{
    mySql.setSqlId("TempFeeInputInputSql_cur");
    mySql.addPara('OpeCurrency' , fm.OpeCurrency.value);
    mySql.addPara('ContNo',ccontno);
    var strArray=easyExecSql(mySql.getString());
	  if(strArray != null && strArray != ""){
	  	var Currency = strArray[0][0];
	  	if(Currency != fm.OpeCurrency.value){
	  			alert("本次交费所选币种与保单的原币种不一致，保单的币种编码为"+Currency+"，请重新选择！")
	  			return false;

	  	}
		}
	}
	fm.OperateType.value="INSERT";
	try {
		if( verifyInput() == true) //&& FinFeeGrid.checkValue("FinFeeGrid")&&MatchInfoGrid.checkValue("MatchInfoGrid")
		{
		  	var i = 0; 
		  	
		  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"; 
		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  	var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
			showInfo.focus(); 
				fm.action = "./TempFinFeeSave.jsp"; 
		  	document.getElementById("fm").submit(); //提交
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

//打印票据
function printInvoice()
{
  window.open("./TempFeeInputPrintMain.jsp");
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, Content)
{
	showInfo.close();
	try {
		if (FlagStr == "Fail" ) {             
	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;  
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  } else { 
		  var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + Content;
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		  var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		  clearFormData();
		  initTTempToGrid();
		  initTTempClassToGrid();
		  fmImport.fileName.value="";
		  fmReset();
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

function fmReset(){
	mDebug="0";
	InputFlag=true;
	addFlag = false; //是否有应收记录，是否允许添加
	contFlag = false; //判断是否有保单记录，是否允许添加
	edorFlag = false; //判断是否有保单记录，是否允许添加
	clmFlag = false; //判断是否有保单记录，是否允许添加
	urgeFlag = false; //判断是否有保单记录，是否允许添加
	addAction = 0;
	sumTempFee = 0.0;
	tempFee = 0.0;
	tempClassFee = 0.0;
	confirmFlag=false;
	fmImport.fileName.value="";
	clearFormData();
	FinFeeGrid.clearData();
}

function PrintData()
{
	
}

//添加财务交费
function addMul(){
	if(!verifyInput6()){
		return false;
	}
	FinFeeGrid.clearData();
	FinFeeGrid.addOne("FinFeeGrid");
	if(fm.PayMode.value=='1'){
		payCash();
	}
	if(fm.PayMode.value=='2'){
		payCheque();
	}
	if(fm.PayMode.value=='3'){
		payCashCheque();
	}
	if(fm.PayMode.value=='4'){
		payBankCount();
	}
	if(fm.PayMode.value=='5'){
		payTransAccout();
	}
	if(fm.PayMode.value=='6'){
		payPos();
	}
	if(fm.PayMode.value=='9')
	{
		payVoucher();
		}
	sumTempFee = parseFloat(FinFeeGrid.getRowColData(0,3)) * 1000000000;
	
	clearFinInfo();
}

function clearFinInfo(){
	fm.PayMode.value					=	'';
	fm.PayModeName.value			=	'';
	fm.Currency.value					=	'';
	fm.CurrencyName.value			=	'';
	fm.PayFee1.value					=	'';
	fm.PayFee2.value					=	'';
	
	fm.ChequeNo2.value				=	'';
//	fm.ChequeDate2.value			=	'';
	fm.BankCode2Name.value		=	'';
//	fm.BankCode2.value				=	'';
	fm.BankAccNo2.value				=	'';
	fm.AccName2.value					=	'';
	
	fm.AccName3.value					=	'';
//	fm.InBankCode3.value			=	'';
	fm.InBankCode3Name.value	=	'';
	fm.InBankAccNo3.value			=	'';
	fm.PayFee3.value					=	'';
	fm.ChequeNo3.value				=	'';
	fm.ChequeDate3.value			=	'';
//	fm.BankCode3.value				=	'';
	fm.BankCode3Name.value		=	'';
	fm.BankAccNo3.value				=	'';
	fm.AccName3.value					=	'';
	
	fm.PayFee4.value					=	'';
	fm.InBankCode4.value			=	'';
	fm.InBankCode4Name.value	=	'';
	fm.InBankAccNo4.value			=	'';
	fm.ChequeNo4.value				=	'';
	fm.ChequeDate4.value			=	'';
	
	fm.PayFee6.value					=	'';
	fm.InBankCode6.value			=	'';
	fm.InBankAccNo6.value			=	'';
	fm.PayFee7.value					=	'';
	fm.BankCode7.value				=	'';
	fm.BankAccNo7.value				=	'';
	fm.AccName7.value					=	'';
	fm.IDType7.value					=	'';
	fm.IDNo7.value						=	'';
	fm.ChequeNo2.value				=	'';
	fm.ChequeNo6.value				=	'';
	
	fm.PayFee9.value					=	'';
	fm.ChequeNo9.value				=	'';
	fm.ChequeDate9.value			=	'';
	fm.BankCode9Name.value		=	'';
//	fm.BankCode9.value				=	'';
	fm.BankAccNo9.value				=	'';
	fm.AccName9.value					=	'';
	fm.InBankCode9.value			=	'';
	fm.InBankCode9Name.value	=	'';
	fm.InBankAccNo9.value			=	'';
	
	fm.InBankCode6Name.value	=	''; //POS收款 收款银行名
	                           	
	fm.TempFeeNo1.value				=	'';
	fm.PayName.value					=	''
	
	return true;
}

//财务收费信息校验
function verifyInput6(){
	if((fm.ManageCom.value).length<4)
  {
    alert("收费或管理机构过大，只有中支或者营销服务部有收费权限");
    fm.PayMode.focus();
    return false;
  }
	
	if(TempToGrid. mulLineCount!=0){
		alert("请先撤销业务费用信息再进行确认！");
		return false;
	}
	
	if(fm.PayMode.value==''||fm.PayMode.value==null){
		alert("请选择交费类型！");
		return false;
	}
	if(fm.Currency.value==''||fm.Currency.value==null){
		alert("请选择币种信息！");
		return false;
	}
	if(fm.PayMode.value=='1'){
		if(fm.PayFee1.value==''||fm.PayFee1.value==null){
			alert("请录入交费金额！");
			return false;
		}
		if(!verifyNumber("交费金额",fm.PayFee1.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
	}
	//现金支票
	if(fm.PayMode.value=='2'){
		if(fm.PayFee2.value==''||fm.PayFee2.value==null){
			alert("请录入交费金额！");
			return false;
		}
	 if(!verifyNumber("交费金额",fm.PayFee2.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
		if(fm.ChequeNo2.value==''||fm.ChequeNo2.value==null){
			alert("请录入票据号码！");
			return false;
		}
		if(fm.ChequeDate2.value==''||fm.ChequeDate2.value==null){
			alert("请录入票据日期！");
			return false;
		}
		if(fm.BankCode2Name.value==''||fm.BankCode2Name.value==null){
			alert("请录入开户银行！");
			return false;
		}
		if(fm.BankAccNo2.value==''||fm.BankAccNo2.value==null){
			alert("请录入账号！");
			return false;
		}
		if(fm.AccName2.value==''||fm.AccName2.value==null){
			alert("请录入户名！");
			return false;
		}
		
				if(fm.InBankCode2.value==''||fm.InBankCode2.value==null){
			alert("请录入收款银行！");
			return false;
		}
		if(fm.InBankAccNo2.value==''||fm.InBankAccNo2.value==null){
			alert("请录入收款银行账号！");
			return false;
		}
			
	}
	//转账支票
	if(fm.PayMode.value=='3'){
		if(fm.PayFee3.value==''||fm.PayFee3.value==null){
			alert("请录入交费金额！");
			return false;
		}
		if(!verifyNumber("交费金额",fm.PayFee3.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
		if(fm.ChequeNo3.value==''||fm.ChequeNo3.value==null){
			alert("请录入票据号码！");
			return false;
		}
		if(fm.ChequeDate3.value==''||fm.ChequeDate3.value==null){
			alert("请录入票据日期！");
			return false;
		}
		if(fm.BankCode3Name.value==''||fm.BankCode3Name.value==null){
			alert("请录入开户银行！");
			return false;
		}
		if(fm.BankAccNo3.value==''||fm.BankAccNo3.value==null){
			alert("请录入银行账号！");
			return false;
		}
		if(fm.AccName3.value==''||fm.AccName3.value==null){
			alert("请录入户名！");
			return false;
		}
		if(fm.InBankCode3.value==''||fm.InBankCode3.value==null){
			alert("请录入收款银行！");
			return false;
		}
		if(fm.InBankAccNo3.value==''||fm.InBankAccNo3.value==null){
			alert("请录入收款银行账号！");
			return false;
		}
	}
	if(fm.PayMode.value=='4'){
		if(fm.PayFee4.value==''||fm.PayFee4.value==null){
			alert("请录入交费金额！");
			return false;
		}
		if(!verifyNumber("交费金额",fm.PayFee4.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
		if(fm.ChequeNo4.value==''||fm.ChequeNo4.value==null){
			alert("请录入票据号码！");
			return false;
		}
		if(fm.ChequeDate4.value==''||fm.ChequeDate4.value==null){
			alert("请录入票据日期！");
			return false;
		}
		if(fm.InBankCode4.value==''||fm.InBankCode4.value==null){
			alert("请录入收款银行！");
			return false;
		}
		if(fm.InBankAccNo4.value==''||fm.InBankAccNo4.value==null){
			alert("请录入收款银行账号！");
			return false;
		}
	}
	if(fm.PayMode.value=='5'){
		if(fm.PayFee5.value==''||fm.PayFee5.value==null){
			alert("请录入交费金额！");
			return false;
		}
		if(!verifyNumber("交费金额",fm.PayFee5.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
		if((fm.ActuGetNo5.value==''||fm.ActuGetNo5.value==null)&&(fm.OtherNo5.value==''||fm.OtherNo5.value==null)){
			alert("请录入实付通知书号和业务号码不能同时为空！");
			return false;
		}
	}
	if(fm.PayMode.value=='6'){
	if(fm.PayFee6.value==''||fm.PayFee6.value==null){
			alert("请录入交费金额！");
			return false;
		}
		if(!verifyNumber("交费金额",fm.PayFee6.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
		if(fm.ChequeNo6.value==''||fm.ChequeNo6.value==null){
			alert("请录入票据号码！");
			return false;
		}
		if(fm.InBankCode6.value==''||fm.InBankCode6.value==null){
			alert("请录入收款银行！");
			return false;
		}
		if(fm.InBankAccNo6.value==''||fm.InBankAccNo6.value==null){
			alert("请录入收款银行账号！");
			return false;
		}
	}
	
	//贷记凭证
	if(fm.PayMode.value=='9'){
		if(fm.PayFee9.value==''||fm.PayFee9.value==null){
			alert("请录入交费金额！");
			return false;
		}
	 if(!verifyNumber("交费金额",fm.PayFee9.value)){
			alert("交费金额不是有效数字！");
			return false;
		}
		if(fm.ChequeNo9.value==''||fm.ChequeNo9.value==null){
			alert("请录入票据号码！");
			return false;
		}
		if(fm.ChequeDate9.value==''||fm.ChequeDate9.value==null){
			alert("请录入票据日期！");
			return false;
		}
//		if(fm.BankCode9.value==''||fm.BankCode9.value==null){
//			alert("请录入开户银行！");
//			return false;
//		}
		if(fm.BankAccNo9.value==''||fm.BankAccNo9.value==null){
			alert("请录入账号！");
			return false;
		}
		if(fm.AccName9.value==''||fm.AccName9.value==null){
			alert("请录入户名！");
			return false;
		}
				if(fm.InBankCode9.value==''||fm.InBankCode9.value==null){
			alert("请录入开户银行！");
			return false;
		}
		if(fm.InBankAccNo9.value==''||fm.InBankAccNo9.value==null){
			alert("请录入账号！");
			return false;
		}
	}
	
	return true;
}

//现金缴费
function payCash(){
	temp = pointTwo(fm.PayFee1.value);
	fm.PayFee1.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
}

//现金支票
function payCheque(){
	temp=pointTwo(fm.PayFee2.value);
	fm.PayFee2.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo2.value); //票据号
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate2.value); //票据日期
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	FinFeeGrid.setRowColData(0,7,fm.BankCode2Name.value); //开户银行
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo2.value); //银行账号
	FinFeeGrid.setRowColData(0,10,fm.AccName2.value); //户名
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode2.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo2.value);
}

//现金支票
function payVoucher(){
	temp=pointTwo(fm.PayFee9.value);
	fm.PayFee2.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo9.value); //票据号
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate9.value); //票据日期
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	FinFeeGrid.setRowColData(0,7,fm.BankCode9Name.value); //开户银行
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo9.value); //银行账号
	FinFeeGrid.setRowColData(0,10,fm.AccName9.value); //户名
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode9.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo9.value);
}
//转账支票
function payCashCheque(){
	temp=pointTwo(fm.PayFee3.value);
	fm.PayFee3.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo3.value); //票据号
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate3.value);
	FinFeeGrid.setRowColData(0,7,fm.BankCode3Name.value);	
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo3.value);	
	FinFeeGrid.setRowColData(0,10,fm.AccName3.value);	
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode3.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo3.value);
	
}
//银行柜面
function payBankCount(){
	temp=pointTwo(fm.PayFee4.value);
	fm.PayFee4.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo4.value); //票据号
	FinFeeGrid.setRowColData(0,6,fm.ChequeDate4.value);
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
	FinFeeGrid.setRowColData(0,11,fm.InBankCode4.value); 
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo4.value); 
	
}

//内部转账
function payTransAccout(){
	temp=pointTwo(fm.PayFee5.value);
	fm.PayFee5.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);	
	FinFeeGrid.setRowColData(0,17,fm.ActuGetNo5.value);	
	FinFeeGrid.setRowColData(0,18,fm.Drawer5.value);	
	FinFeeGrid.setRowColData(0,19,fm.DrawerID.value);	
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
}

//Pos机
function payPos(){
	temp=pointTwo(parseFloat(fm.PayFee6.value));
	fm.PayFee6.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);
	FinFeeGrid.setRowColData(0,5,fm.ChequeNo6.value); //票据号
	FinFeeGrid.setRowColData(0,11,fm.InBankCode6.value);
	FinFeeGrid.setRowColData(0,12,fm.InBankAccNo6.value);
	FinFeeGrid.setRowColData(0,20,fm.CurrencyName.value);
	
}

/*********************************************************************
 *  收费方式选择内部转帐时，查询实付数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryLJAGet()
{
   if(verifyInput6())
   {
      EasyQueryPay();
   }
}

function EasyQueryPay ()
{
  // 拼SQL语句，从页面采集信息
  var strSql = "";
  mySql.setSqlId("TempFeeInputInputSql_34");
  strSql = mySql.getSQL();
  if (fm.all('ActuGetNo5').value!="")
  {
  	mySql.setSqlId("TempFeeInputInputSql_35");
  	mySql.addPara('ActuGetNo5',fm.all('ActuGetNo5').value);
  	strSql += mySql.getSQL();	
  }
  if(fm.all('OtherNo5').value!="")
	{
		mySql.setSqlId("TempFeeInputInputSql_36");
  	mySql.addPara('OtherNo5',fm.all('OtherNo5').value);
  	strSql += mySql.getSQL();
	}
  
  //查询付费机构sql,返回结果
  QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
  var strArray = easyExecSql(strSql );
  if(strArray==null){
  	alert("没有查询到数据！");
  	return false;
  }
  turnPage.queryModal(strSql, QueryLJAGetGrid);
} 

function GetRecord(){
  var tSel = QueryLJAGetGrid.getSelNo();
	fm.ActuGetNo5.value	= QueryLJAGetGrid.getRowColData(tSel-1,1);
	fm.OtherNo5.value		=	QueryLJAGetGrid.getRowColData(tSel-1,2);
	fm.PayFee5.value		=	pointTwo(QueryLJAGetGrid.getRowColData(tSel-1,3));
	fm.Drawer5.value		=	QueryLJAGetGrid.getRowColData(tSel-1,4);
	fm.DrawerID.value		=	QueryLJAGetGrid.getRowColData(tSel-1,5);
}

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

function queryClick(){
  fm.OperateType.value="QUERY";
  
  window.open("./FrameAccRDQuery.jsp?Serial=","windows1");
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	try {
		if( verifyInput()==true&&MatchInfoGrid.checkValue("MatchInfoGrid")) {	
			if (verifyInput4()==true){
		  	var i = 0;
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
				fm.action="./TempFinFeeSave.jsp";
		  	document.getElementById("fm").submit(); //提交
	  	}
	  	else{
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	alert(ex);
  }
}

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm("你确定要删除该累计方案吗？")){
		return false;
	}
	try {
		if( verifyInput()) {
			var i = 0;
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
			fm.action="./LRAccRDSave.jsp";
		  document.getElementById("fm").submit(); //提交
	  }else{
	  	
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

//保存校验
function verifyInput3(){
	var rowNum=FinFeeGrid. mulLineCount ; //行数 
	if(rowNum==0){
		alert("请录入财务交费记录!");
		return false;
	}
	for(var i=0;i<rowNum;i++)
	{
		num=i+1;
		//现金支票
		if(FinFeeGrid.getRowColData(i,1)=='2'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("交费方式为现金支票请录入票据号码!");
			return false;
		}
		//转账支票
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("交费方式为转账支票请录入票据号码!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,6)==null||FinFeeGrid.getRowColData(i,6)==''))
		{
			alert("交费方式为转账支票请录入票据日期!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,7)==null||FinFeeGrid.getRowColData(i,7)==''))
		{
			alert("交费方式为转账支票请录入开户银行!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,9)==null||FinFeeGrid.getRowColData(i,9)==''))
		{
			alert("交费方式为转账支票请录入银行账号!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='3'&&(FinFeeGrid.getRowColData(i,10)==null||FinFeeGrid.getRowColData(i,10)==''))
		{
			alert("交费方式为转账支票请录入户名!");
			return false;
		}
		//内部转账
		if(FinFeeGrid.getRowColData(i,1)=='5'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("交费方式为转账支票请录入票据号码!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='5'&&(FinFeeGrid.getRowColData(i,11)==null||FinFeeGrid.getRowColData(i,11)==''))
		{
			alert("交费方式为转账支票请录入收款银行!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='5'&&(FinFeeGrid.getRowColData(i,12)==null||FinFeeGrid.getRowColData(i,12)==''))
		{
			alert("交费方式为转账支票请录入收款银行账号!");
			return false;
		}
		//POS收款
		if(FinFeeGrid.getRowColData(i,1)=='6'&&(FinFeeGrid.getRowColData(i,5)==null||FinFeeGrid.getRowColData(i,5)==''))
		{
			alert("交费方式为转账支票请录入票据号码!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='6'&&(FinFeeGrid.getRowColData(i,11)==null||FinFeeGrid.getRowColData(i,11)==''))
		{
			alert("交费方式为转账支票请录入收款银行!");
			return false;
		}
		if(FinFeeGrid.getRowColData(i,1)=='6'&&(FinFeeGrid.getRowColData(i,12)==null||FinFeeGrid.getRowColData(i,12)==''))
		{
			alert("交费方式为转账支票请录入收款银行账号!");
			return false;
		}
		//银行批量代扣
		if(FinFeeGrid.getRowColData(i,1)=='7')
		{
			alert("银行批量代扣不能进行柜面交费!");
			return false;
		}
	}
	return true;
}

/**
	修改校验
*/
function verifyInput4(){
	if(!confirm("你确定要修改匹配信息吗？")){
		return false;
	}
	if(fm.FinFeeNo.value==''||fm.FinFeeNo.value==null){
		alert("请先查询财务收费信息再进行修改!");
		return false;
	}
	//重复性校验
	for(var n=0;n<MatchInfoGrid.mulLineCount;n++) 
	{ 
	   for(var m=n+1;m<MatchInfoGrid.mulLineCount;m++) 
	   { 
	     if(MatchInfoGrid.getRowColData(n,1)==MatchInfoGrid.getRowColData(m,1)) 
	     {
	         alert("不能录入重复的业务匹配编码");	
	         return false; 
	     }
	   }
	}
	return true;
}

function afterQuery(){
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm(){
  try{
	  fm.FinFeeNo.value='';
	  FinFeeGrid.clearData();
	  MatchInfoGrid.clearData();
  }
  catch(re){
  	alert("在CertifySendOutInput.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//提交前的校验、计算  
function beforeSubmit(){
  //添加操作
}

function afterCodeSelect( cCodeName, Field ){
	//选择了处理
	if(cCodeName == "TempFeeType")
	{ 
	  showTempFeeTypeInput(Field.value);
	  if(Field.value=="1"){
	  	AgentCode.style.display='';
			AgentCode1.style.display='none';
	  }else if(Field.value=="4"||Field.value=="5"){
	  	AgentCode.style.display='none';
			AgentCode1.style.display='none';
	  }
	  else{
	  	AgentCode.style.display='none';
			AgentCode1.style.display='';
	  }
	  clearOpeTypeInfo();
	}
	
	if(cCodeName == "paymodequery")
	{
		showTempClassInput(Field.value);
		//PayModePrem();
	}
	if(cCodeName == "comtobank"){
		fm.InBankAccNo3.value="";
	}
	
}

function clearOpeTypeInfo(){
	fm.PolicyCom.value='';
	fm.OpeCurrency.value='';
	fm.OpeCurrencyName.value='';
	fm.AgentCode.value='';
	fm.AgentName.value='';
	fm.AgentGroup.value='';
	fm.AgentCode1.value='';
	fm.AgentName1.value='';
	fm.AgentGroup1.value='';
	
	fm.InputNo1.value='';
	fm.InputNob.value='';
	fm.OpeFee1.value='';
	fm.OpeFee2.value='';
	fm.InputNo3.value='';
	fm.GetNoticeNo.value='';
	fm.OpeFee3.value='';
	fm.InputNo5.value='';
	fm.OpeFee4.value='';
	fm.InputNo7.value='';
	fm.InputNo8.value='';
	fm.OpeFee5.value='';
	fm.InputNo11.value='';
	fm.InputNo12.value='';
	fm.InputNo22.value='';
	
	fm.OpeFee6.value='';
	fm.TempFeeNo1.value='';
	
}

/*********************************************************************
 *  根据选择不同的交费方式，初始化页面 gaoht
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showTempClassInput(type)
{
	for(i=0;i<=9;i++)
	{
		
		if(i==type)
		{
			fm.all("divPayMode"+i).style.display='';
		}
		else
		{
		  fm.all("divPayMode"+i).style.display='none';
		}
	}
}

/********************************************************************
 *
 *
 *
 ********************************************************************
 */
function PayModePrem()
{
  var SumPrem =0.0;
	var TempCount = TempGrid.mulLineCount;
  for(i=0;i<TempCount;i++)
  {
  	SumPrem = SumPrem + pointTwo(parseFloat(TempGrid.getRowColData(i,3)));//交费金额累计
  }
  SumPrem = pointTwo(SumPrem);
  if (fm.all("PayMode").value == '0')
  {
  	fm.all("PayFee0").value = SumPrem;
  	}
  if (fm.all("PayMode").value == '1')
  {
  	fm.all("PayFee1").value = SumPrem;
  	}
  if (fm.all("PayMode").value == '2')
  {
  	fm.all("PayFee2").value = SumPrem;
    }
  if (fm.all("PayMode").value == '3')
  {
  	fm.all("PayFee3").value = SumPrem;
    }
  if (fm.all("PayMode").value == '4')
  {
  	fm.all("PayFee4").value = SumPrem;
  }
  
  if (fm.all("PayMode").value == '6')
  {
  	fm.all("PayFee6").value = SumPrem;
  }
  if (fm.all("PayMode").value == '7')
  {
  	fm.all("PayFee7").value = SumPrem;
  }
  if (fm.all("PayMode").value == '8')
  {
  	fm.all("PayFee8").value = SumPrem;
  }
  if (fm.all("PayMode").value == '9')
  {
  	fm.all("PayFee9").value = SumPrem;
  }
}

/*********************************************************************
 *  根据选择不同的暂交费类型  ，初始化页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showTempFeeTypeInput(type) {
    if (type==7)
     type = 3;
  for (i=0; i<9; i++) {
    if ((i+1) == type) {
      fm.all("TempFeeType" + (i+1)).style.display = '';
    }
    else {
      fm.all("TempFeeType" + (i+1)).style.display = 'none';
    }
  }
}

function queryAgent()
{
	if(fm.all('ManageCom').value==""){
		alert("请先录入收费机构信息！"); 
		return;
	}
	if(fm.AgentCode.value != ""&&fm.AgentCode.value != null){
		var cAgentCode = fm.AgentCode.value;
		var strSql = "";
  	mySql.setSqlId("TempFeeInputInputSql_13");
  	mySql.addPara('cAgentCode',cAgentCode);
  	var arrResult = easyExecSql( mySql.getString());
  	if (arrResult != null) {
  	  alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
  	} 
	}else{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
  	var strSql = "";
   	mySql.setSqlId("TempFeeInputInputSql_24");
   	mySql.addPara('AgentCode',fm.AgentCode.value);
   	var arrResult = easyExecSql(mySql.getString());
   	if(arrResult==null||arrResult=="NULL"||arrResult=="")
   	{
   	  alert("业务员编号错误，无交费机构信息");	
   	  fm.AgentCode.value="";
   	  fm.AgentName.value="";
   	  fm.AgentGroup.value="";
   	  fm.PolicyCom.value="";
   	  
   	  return false;
   	}
  	else
  	{
     fm.PolicyCom.value=arrResult;	  	  
    }
  	var aSql = "";
    mySql.setSqlId("TempFeeInputInputSql_25");
    mySql.addPara('AgentCode',fm.AgentCode.value);
  	arrResult = easyExecSql(mySql.getString());
   	if(arrResult==null||arrResult=="NULL"||arrResult=="")
   	{
   	  alert("业务员编号错误，无组别信息");	
   	  fm.AgentCode.value="";
   	  return false;
   	}
  	else
  	{
  		fm.AgentGroup.value=arrResult;	
      var nSql = "";        
      mySql.setSqlId("TempFeeInputInputSql_26");
      mySql.addPara('AgentCode',fm.AgentCode.value);
      arrResult = easyExecSql(mySql.getString());
      fm.AgentName.value=arrResult;
    } 
  }
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{
	if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.PolicyCom.value = arrResult[0][2];
  }
  var aSql = "";
  mySql.setSqlId("TempFeeInputInputSql_14");
  mySql.addPara('AgentCode',fm.AgentCode.value);
  arrResult = easyExecSql(mySql.getString());
  if(arrResult==null||arrResult=="NULL"||arrResult=="")
  {
    alert("业务员编号错误，无组别信息");	
    fm.AgentCode.value="";
    return false;
  }
  else
  {
   fm.AgentGroup.value=arrResult;
  } 
}

function confirm1(){
	if(!verifyInput7()){
		return false;
	}
	
	
  if(fm.TempFeeNo1.value == null || fm.TempFeeNo1.value == '')
	{
		//如果暂收据为空，需要生成临时暂收据
	  CreateTempNo();
	
	}
	else
	{
		CreateTempData();
	}	
	

}

function CreateTempData()
{
		if(fm.TempFeeType.value=='1'){//新单交费
		if(newPolicy()){
			clearOpeTypeInfo();
		}
	}
	//else if(fm.TempFeeType.value=='2'){//续期催缴
	//	if(conPolicy()){
	//		clearOpeTypeInfo();
	//	}
	//}
	else if(fm.TempFeeType.value=='3'){//续期预缴
		if(conBefPolicy()){
			clearOpeTypeInfo();
		}
	}else if(fm.TempFeeType.value=='4'){//保全交费
		if(edorPolicy()){
			clearOpeTypeInfo();
		}
	}else if(fm.TempFeeType.value=='5'){//理赔交费
		if(claimPolicy()){
			clearOpeTypeInfo();
		}
	}
	//else if(fm.TempFeeType.value=='6'){//不定期交费
	//	if(urgePayPolicy()){
	//		clearOpeTypeInfo();
	//	}
	//}
	
}

function CreateTempNo()
{
	 //var i = 0; 		  	
	 //var showStr="正在生成虚拟暂收据号，请您稍候并且不要修改屏幕上的值或链接其他页面"; 
	 //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	 //var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus(); 
	 fmSave.action = "./TempFinFeeCrTempNo.jsp?ManageCom="+fm.ManageCom.value; 
	 fmSave.submit(); //提交
	 return false;

}

function AfterCreateTempNo(tTempFeeNo)
{
	if(fm.TempFeeNo1.value == null || fm.TempFeeNo1.value == "")
	{
		fm.TempFeeNo1.value = tTempFeeNo;
	}
	
	if(fm.TempFeeNo1.value == null || fm.TempFeeNo1.value == "")
	{
		alert("生成虚拟暂收据号错误!");
		return;
	}
	else
	{
		CreateTempData();
	}
}

//新单
function newPolicy(){
	if(parseFloat(fm.OpeFee1.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee1.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	2	,	fm.TempFeeType.value			);	 
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value						); 
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee1.value))); 
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value				);	//业务币种信息
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value					); 	//管理机构，managecom是收费机构
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup.value					);	//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode.value					);	//代理人编码
	TempToGrid.setRowColData(count,	16,	fm.InputNob.value						);	//投保人
	TempToGrid.setRowColData(count,	11,	fm.InputNo1.value						); 	//业务号码
	TempToGrid.setRowColData(count,	12,	'6' 												);	//业务号码类型
	TempToGrid.setRowColData(count,	19	,	fm.OpeCurrencyName.value	);
	TempToGrid.setRowColData(count,	20	,	fm.TempFeeNo1.value				);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //	
	
	
	return true;
}

//续期催收
function conPolicy(){
	if(!addFlag){
		alert("没有查到应收记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee2.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee2.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.GetNoticeNo.value									);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee2.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);		//币种
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(count,	11,	fm.InputNo3.value											); 		//业务号码
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//业务号码类型   
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20	,	fm.TempFeeNo1.value									);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //	
	
	return true;
}
//续期预缴
function conBefPolicy(){
	if(!contFlag){
		alert("没有查到保单记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee3.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee3.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										); 		//交费通知号
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);		//
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee3.value)));		//
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);		//
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(count,	11,	fm.InputNo5.value											); 		//业务号码
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//业务号码类型
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //			
	
	return true;
}

//保全
function edorPolicy(){
	
	if(!edorFlag){
		alert("没有查到应收记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee4.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee4.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	////////
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo8.value											);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee4.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(count,	11,	fm.InputNo7.value											); 	  //业务号码
	TempToGrid.setRowColData(count,	12,	'10' 																	); 		//业务号码类型
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //		
	
	return true;
}

//理赔
function claimPolicy(){
	if(!clmFlag){
		alert("没有查到应收记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee5.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee5.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										);		
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);		
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee5.value)));		
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);		
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(count,	11,	fm.InputNo12.value										);		//业务号码
	TempToGrid.setRowColData(count,	12,	'9' 																	); 		//业务号码类型 理赔‘9’
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //		
	
	return true;
}

//不定期缴费
function urgePayPolicy(){
	if(!urgeFlag){
		alert("没有查到保单记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee6.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	tempFee = 1000000000 * parseFloat(fm.OpeFee6.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((sumTempFee-tempFee)/1000000000);
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee6.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(count,	11,	fm.InputNo22.value										); 		//业务号码
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//业务号码类型
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(count,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(count,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(count,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}

//新单
function modNewPolicy(){
	if(parseFloat(fm.OpeFee1.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee1.value); 
	//取得汇总信息 
	var count = TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0 ; i < count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1	,	3	,	fm.PayDate.value						);
	TempToGrid.setRowColData(tSel-1	,	4	,	pointTwo(parseFloat(fm.OpeFee1.value))); //费用金额
	TempToGrid.setRowColData(tSel-1	,	5	,	fm.OpeCurrency.value				);	//业务币种信息
	TempToGrid.setRowColData(tSel-1	,	7	,	fm.PolicyCom.value					); 	//管理机构，managecom是收费机构
	TempToGrid.setRowColData(tSel-1	,	9,	fm.AgentGroup.value					);	//代理人编码
	TempToGrid.setRowColData(tSel-1	,	10,	fm.AgentCode.value					);	//代理人编码
	TempToGrid.setRowColData(tSel-1	,	16,	fm.InputNob.value						);	//投保人
	TempToGrid.setRowColData(tSel-1	,	11,	fm.InputNo1.value						); 	//业务号码
	TempToGrid.setRowColData(tSel-1	,	12,	'6' 												);	//业务号码类型
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1	,	19,	fm.OpeCurrencyName.value		);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value					);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}

//续期催收
function modConPolicy(){
	if(!addFlag){
		alert("没有查到应收记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee2.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee2.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1,	1	,	fm.GetNoticeNo.value									);
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee2.value)));
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);		//币种
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(tSel-1,	9,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo3.value											); 		//业务号码
	TempToGrid.setRowColData(tSel-1,	12,	'2' 																	); 		//业务号码类型 
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}
//续期预缴
function modConBefPolicy(){
	if(!contFlag){
		alert("没有查到保单记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee3.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee3.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	////////
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo11.value										); 		//交费通知号
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);		//
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee3.value)));		//
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);		//
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(tSel-1,	9,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo5.value											); 		//业务号码
	TempToGrid.setRowColData(tSel-1,	12,	'2' 																	); 		//业务号码类型
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19	,	fm.OpeCurrencyName.value						);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));	
	
	return true;
}

//保全
function modEdorPolicy(){
	
	if(!edorFlag){
		alert("没有查到应收记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee4.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee4.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	////////
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo8.value											);
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee4.value)));
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(tSel-1,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo7.value											); 	  //业务号码
	TempToGrid.setRowColData(tSel-1,	12,	'10' 																	); 		//业务号码类型
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19	,	fm.OpeCurrencyName.value						);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));		
	
	return true;
}

//理赔
function modClaimPolicy(){
	if(!clmFlag){
		alert("没有查到应收记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee5.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee5.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo11.value										);		
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);		
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee5.value)));		
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);		
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(tSel-1,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo12.value										);		//业务号码
	TempToGrid.setRowColData(tSel-1,	12,	'9' 																	); 		//业务号码类型 理赔‘9’
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //	
	TempToGrid.setRowColData(tSel-1,	19,	fm.OpeCurrencyName.value							);
	TempToGrid.setRowColData(tSel-1,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));			
	
	return true;
}

//不定期缴费
function modUrgePayPolicy(){
	if(!urgeFlag){
		alert("没有查到保单记录");
		return false ;
	}
	if(parseFloat(fm.OpeFee6.value)<=0){
		alert("费用金额不能小于等于0");
		return false;
	}
	var tSel = TempToGrid.getSelNo();
	tempFee = 1000000000 * parseFloat(fm.OpeFee6.value); 
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		if(i!=(tSel-1)){
			tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
		}
	}
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		//InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	TempToGrid.setRowColData(tSel-1,	1	,	fm.InputNo11.value										);
	TempToGrid.setRowColData(tSel-1,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(tSel-1,	4	,	pointTwo(parseFloat(fm.OpeFee6.value)));
	TempToGrid.setRowColData(tSel-1,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(tSel-1,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(tSel-1,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(tSel-1,	11,	fm.InputNo22.value										); 		//业务号码
	TempToGrid.setRowColData(tSel-1,	12,	'2' 																	); 		//业务号码类型
	TempToGrid.setRowColData(tSel-1,	18,	fm.TempFeeType.value									);    //
	TempToGrid.setRowColData(tSel-1,	19	,	fm.OpeCurrencyName.value						);
	TempToGrid.setRowColData(tSel-1	,	20,	fm.TempFeeNo1.value										);
	TempToGrid.setRowColData(tSel-1,	2	,	FinFeeGrid.getRowColData(0,1));		
	return true;
}

function showInform(){
	
	var tSel = TempToGrid.getSelNo();
	
	showTempFeeTypeInput(TempToGrid.getRowColData(tSel-1,18));
	//新单
	if(TempToGrid.getRowColData(tSel-1,18)=="1"){
		fm.PayDate.value					= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee1.value					= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value			= TempToGrid.getRowColData(tSel-1,	5	 );
		fm.PolicyCom.value				= TempToGrid.getRowColData(tSel-1,	7	 );
		fm.AgentGroup.value				= TempToGrid.getRowColData(tSel-1,	9  );
		fm.AgentCode.value				= TempToGrid.getRowColData(tSel-1,	10 );
		fm.InputNob.value					= TempToGrid.getRowColData(tSel-1,	16 );
		fm.InputNo1.value         = TempToGrid.getRowColData(tSel-1,	11 );
		fm.TempFeeType.value      = TempToGrid.getRowColData(tSel-1,	18 );
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "新单交费" ;
	}else if(TempToGrid.getRowColData(tSel-1,18)=="2"){                
		                                                                 
		fm.GetNoticeNo.value 			= TempToGrid.getRowColData(tSel-1,	1	 );
		fm.PayDate.value		 			= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee2.value		 			= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value 			= TempToGrid.getRowColData(tSel-1,	5	 );		//币种
		fm.PolicyCom.value	 			= TempToGrid.getRowColData(tSel-1,	7	 ); 		//管理机构
		fm.AgentGroup1.value 			= TempToGrid.getRowColData(tSel-1,	9  );		//代理人编码
		fm.AgentCode1.value	 			= TempToGrid.getRowColData(tSel-1,	10 );		//代理人编码
		fm.InputNo3.value		 			= TempToGrid.getRowColData(tSel-1,	11 ); 		//业务号码
		fm.TempFeeType.value 			= TempToGrid.getRowColData(tSel-1,	18 );
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );    
		fm.TempFeeTypeName.value  = "续期催收交费" ; 
		                                                                 
	}else if(TempToGrid.getRowColData(tSel-1,18)=="3"){                
		                                                                 
		fm.InputNo11.value				=	TempToGrid.getRowColData(tSel-1,	1	 ); 		//交费通知号
		fm.PayDate.value					=	TempToGrid.getRowColData(tSel-1,	3	 );		//
		fm.OpeFee3.value					=	TempToGrid.getRowColData(tSel-1,	4	 );		//
		fm.OpeCurrency.value			=	TempToGrid.getRowColData(tSel-1,	5	 );		//
		fm.PolicyCom.value				=	TempToGrid.getRowColData(tSel-1,	7	 ); 		//管理机构
		fm.AgentGroup1.value			=	TempToGrid.getRowColData(tSel-1,	9	 );		//代理人编码
		fm.AgentCode1.value				=	TempToGrid.getRowColData(tSel-1,	10 );		//代理人编码
		fm.InputNo5.value					=	TempToGrid.getRowColData(tSel-1,	11 ); 		//业务号码
		fm.TempFeeType.value			=	TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "续期交费" ; 
		                                                                 
	}else if(TempToGrid.getRowColData(tSel-1,18)=="4"){                
		                                                                 
		fm.InputNo8.value					= TempToGrid.getRowColData(tSel-1,	1	 );
		fm.PayDate.value					= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee4.value					= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value			= TempToGrid.getRowColData(tSel-1,	5	 );
		fm.PolicyCom.value				= TempToGrid.getRowColData(tSel-1,	7	 ); 		//管理机构
		fm.AgentGroup1.value			= TempToGrid.getRowColData(tSel-1,	9  );		//代理人编码
		fm.AgentCode1.value				= TempToGrid.getRowColData(tSel-1,	10 );		//代理人编码
		fm.InputNo7.value					= TempToGrid.getRowColData(tSel-1,	11 ); 	  //业务号码
		fm.TempFeeType.value			= TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "保全交费" ; 
		                                                                 
	}else if(TempToGrid.getRowColData(tSel-1,18)=="5"){                
		fm.InputNo11.value				= TempToGrid.getRowColData(tSel-1,	1	 );		
		fm.PayDate.value					= TempToGrid.getRowColData(tSel-1,	3	 );		
		fm.OpeFee5.value					= TempToGrid.getRowColData(tSel-1,	4	 );		
		fm.OpeCurrency.value			= TempToGrid.getRowColData(tSel-1,	5	 );		
		fm.PolicyCom.value				= TempToGrid.getRowColData(tSel-1,	7	 ); 		//管理机构
		fm.AgentGroup1.value			= TempToGrid.getRowColData(tSel-1,	9  );		//代理人编码
		fm.AgentCode1.value				= TempToGrid.getRowColData(tSel-1,	10 );		//代理人编码
		fm.InputNo12.value				= TempToGrid.getRowColData(tSel-1,	11 );		//业务号码
		fm.TempFeeType.value			= TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "理赔收费" ; 
		
	}else if(TempToGrid.getRowColData(tSel-1,18)=="6"){
		
		fm.InputNo11.value	 			= TempToGrid.getRowColData(tSel-1,	1	 );
		fm.PayDate.value		 			= TempToGrid.getRowColData(tSel-1,	3	 );
		fm.OpeFee6.value		 			= TempToGrid.getRowColData(tSel-1,	4	 );
		fm.OpeCurrency.value 			= TempToGrid.getRowColData(tSel-1,	5	 );
		fm.PolicyCom.value	 			= TempToGrid.getRowColData(tSel-1,	7	 ); 		//管理机构
		fm.AgentGroup1.value 			= TempToGrid.getRowColData(tSel-1,	9  );		//代理人编码
		fm.AgentCode1.value	 			= TempToGrid.getRowColData(tSel-1,	10 );		//代理人编码
		fm.InputNo22.value	 			= TempToGrid.getRowColData(tSel-1,	11 ); 		//业务号码
		fm.TempFeeType.value 			= TempToGrid.getRowColData(tSel-1,	18 );    //
		fm.OpeCurrencyName.value  = TempToGrid.getRowColData(tSel-1,	19 );
		fm.TempFeeNo1.value				= TempToGrid.getRowColData(tSel-1,	20 );
		fm.TempFeeTypeName.value  = "不定期交费" ; 
		
	}
	
	return true;
	
}

function modifyData(){
	
  if(!verifyInput7()){
		return false;
	}	
	var tSel = TempToGrid.getSelNo();
	
	if(tSel == "" || tSel == null ||tSel=="null")  //判断是否被选中
	{
	    alert("请先选择要修改的记录");
	    return false;
	}
	
	showTempFeeTypeInput(TempToGrid.getRowColData(tSel-1,18));
	//新单
	if(TempToGrid.getRowColData(tSel-1,18)=="1"){
		modNewPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="2"){
		modConPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="3"){
		modConBefPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="4"){
		modEdorPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="5"){
		modClaimPolicy();
	}else if(TempToGrid.getRowColData(tSel-1,18)=="6"){
		modUrgePayPolicy();
	}
	
	return true;
}


function sumMoney(){
	tempFee = 0;
	//取得汇总信息 
	var count=TempToGrid.mulLineCount; //得到MulLine的行数 
	for(var i=0;i<count;i++){ 
		tempFee = tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	
	fm.OperateSum.value=pointTwo(tempFee/1000000000);
	fm.OperateSub.value=pointTwo((tempFee-sumTempFee)/1000000000);
	
	return true;
}

function verifyInput7(){
	if(FinFeeGrid. mulLineCount==0){
		alert("请先确认财务交费信息！");
		return false;
	}
	if(fm.TempFeeType.value==''||fm.TempFeeType.value==''){
		alert("请先选择交费类型");
		return false;
	}
	if(fm.OpeCurrency.value==''||fm.OpeCurrency.value==null){
		alert("业务币种不能为空");
		return false;
	}
  if(!_CheckCurrency())
	{
		alert("业务币种与选币种不一致");
		return false;
	}
	if(fm.OpeCurrency.value!=FinFeeGrid.getRowColData(0,4)){
		alert("与财务收费币种不一致！");
		return false;
	}
	if(fm.TempFeeType.value=='1'){
		if(fm.AgentCode.value==''||fm.AgentCode.value==null){
			alert("代理人号码不能为空");
			return false;
		}
		if(fm.InputNo1.value==''||fm.InputNo1.value==null){
			alert("投保单号不能为空");
			return false;
		}
		if(fm.OpeFee1.value==''||fm.OpeFee1.value==null){
			alert("费用金额不能为空");
			return false;
		}
		if(!verifyNumber("费用金额",fm.OpeFee1.value)){
			alert("交费金额不是有效数字");
			return false;
		}
		var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_15");
    mySql.addPara('PolNo' , fm.InputNo1.value);
    // select agentcode from lccont where contno='?PolNo?' ;
    var strArray=easyExecSql(mySql.getString());
	  if(strArray != null && strArray != ""){
	  	var agentCode = strArray[0][0];
	  	if(agentCode != fm.AgentCode.value){
	  		alert("保单代理人与录入代理人不符 ");
	  		return false;
	  	}
		}
	}
	if(fm.TempFeeType.value=='2'){
		if(fm.InputNo3.value==''||fm.InputNo3.value==null){
			alert("保单号不能为空");
			return false;
		}
		if(fm.OpeFee2.value==''||fm.OpeFee2.value==null){
			alert("费用金额不能为空");
			return false;
		}
		if(!verifyNumber("费用金额",fm.OpeFee2.value)){
			alert("交费金额不是有效数字");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='3'){
		if(fm.InputNo5.value==''||fm.InputNo5.value==null){
			alert("保单号不能为空");
			return false;
		}
		if(fm.OpeFee3.value==''||fm.OpeFee3.value==null){
			alert("费用金额不能为空");
			return false;
		}
		if(!verifyNumber("费用金额",fm.OpeFee3.value)){
			alert("交费金额不是有效数字");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='4'){
		if(fm.InputNo7.value==''||fm.InputNo7.value==null){
			alert("保全受理号不能为空");
			return false;
		}
		if(fm.OpeFee4.value==''||fm.OpeFee4.value==null){
			alert("费用金额不能为空");
			return false;
		}
		if(!verifyNumber("费用金额",fm.OpeFee4.value)){
			alert("交费金额不是有效数字");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='5'){
		if(fm.InputNo12.value==''||fm.InputNo12.value==null){
			alert("赔案号不能为空");
			return false;
		}
		if(fm.OpeFee5.value==''||fm.OpeFee5.value==null){
			alert("费用金额不能为空");
			return false;
		}
		if(!verifyNumber("费用金额",fm.OpeFee5.value)){
			alert("交费金额不是有效数字");
			return false;
		}
	}
	
	if(fm.TempFeeType.value=='6'){
		if(fm.InputNo22.value==''||fm.InputNo22.value==null){
			alert("保单号不能为空");
			return false;
		}
		if(fm.OpeFee6.value==''||fm.OpeFee6.value==null){
			alert("费用金额不能为空");
			return false;
		}
		if(!verifyNumber("费用金额",fm.OpeFee6.value)){
			alert("交费金额不是有效数字");
			return false;
		}
	}
	
	var NTempNo = fm.TempFeeNo1.value;
	if(fm.TempFeeNo1.value == '' || fm.TempFeeNo1.value == null)
	{
	}
	else
	{
		if( NTempNo.substr(0,2) == "TS")
		{
		}
		else
		{
			if(!_CheckTempNo())
			{
				alert("该暂收据已经回收，不能重复使用！");
				return false;
			}
		
			if(!_CheckDoc())
		  {  
			  alert("没有将该暂收据发放到所选择代理人！");
			  return false;
		  }
		}
		
		if( NTempNo.substr(0,2) == "TS")
		{
		}
		else
		{
		  if(!_CheckMaxDate())
		  {
			  if(!confirm('该暂收据已超过有效期，是否确定使用！')){      
          return;      
        } 
		  }
		}

		
	}
	
	if(fm.ManageCom.value.substring(0,4)!=fm.PolicyCom.value.substring(0,4)){
		if(!confirm("业务费用为异地缴费，要确定吗？")){
			return false;
		}
	}
	return true;
}

function getAppntName(){
	if(fm.InputNo1.value==null||fm.InputNo1.value==""){
		return true;
	}
	fm.InputNob.value = "";
	var strSql = "" ;
	mySql.setSqlId("TempFeeInputInputSql_17");
  mySql.addPara('PolNo' , fm.InputNo1.value);
	
	var TarrResult = easyQueryVer3( mySql.getString(), 1, 1, 1);
	if(TarrResult!=null&&TarrResult!=""){
	 	addFlag = true;
	 	var ArrData = decodeEasyQueryResult(TarrResult);
	 	if(ArrData[0][0]!=""&&ArrData[0][0]!=null){
	 		fm.InputNob.value = ArrData[0][0] ;
	 	}
	}
	return true;
}

function getAgentCode()
{
	if((fm.all('InputNo3').value!=null||fm.all('InputNo3').value!="")&&(fm.all('AgentCode').value==""||fm.all('AgentCode').value==null))
	{
		var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_20");
    mySql.addPara('InputNo3' , fm.InputNo3.value);
    // select AgentCode,managecom,agentgroup from ljspay where otherno='?InputNo3?' ;
	  var TarrResult = easyQueryVer3( mySql.getString(), 1, 1, 1);
	  if(TarrResult!=null&&TarrResult!=""){
	  	addFlag = true;
	  	var ArrData = decodeEasyQueryResult(TarrResult);
	  	if(ArrData[0][0]==""||ArrData[0][2]==""){
	  	  alert("未查到代理人信息"); 
	  	}
	  	if(ArrData[0][1]==""){
	  	  alert("未查到保单管理机构");
	  	}
	    fm.AgentCode1.value		= ArrData[0][0];
	    fm.PolicyCom.value  	=	ArrData[0][1];
	    fm.AgentGroup1.value	=	ArrData[0][2];
	    fm.AgentName1.value		=	ArrData[0][3];
	    fm.GetNoticeNo.value	=	ArrData[0][4];
	  }
	  else{
	  	alert("无催收交费数据，请进行续期催缴抽档");	 
	  	addFlag = false;
	  	fm.AgentCode1.value		= "";
	    fm.PolicyCom.value  	=	"";
	    fm.AgentGroup1.value	=	"";
	    fm.AgentName1.value		=	"";
	    return;
	  }
  }
}

/*********************************************************************
 *  异地代收——预交续期保费查询保单管理机构
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolicyCom()
{
	if (fm.InputNo5.value!=null&&fm.InputNo5.value!="")
	{
	  var flag = "";
	  var strSql2 = "";
    mySql.setSqlId("TempFeeInputInputSql_29");
    mySql.addPara('InputNo5',fm.InputNo5.value);
	  var arrResult2 = easyExecSql( mySql.getString());
	  if(arrResult2 == null)
	  {
	    var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
      mySql.addPara('InputNo5',fm.InputNo5.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("无此保单信息，管理机构查询失败");	
        fm.PolicyCom.value="";
        fm.AgentCode1.value="";
      	fm.AgentName1.value="";
      	fm.AgentGroup1.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_31"); //select agentcode from LCCont where contno='?InputNo5?'
        mySql.addPara('InputNo5',fm.InputNo5.value);
        var brrResult = easyExecSql( mySql.getString());
        
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
        	fm.AgentCode1.value="";
      		fm.AgentName1.value="";
      		fm.AgentGroup1.value="";
          alert("无此保单信息，代理人查询失败");	
          fm.AgentCode.value="";
          return false;
        }
        else
      	{
      		fm.AgentCode1.value=brrResult[0][0];
      		fm.AgentName1.value=brrResult[0][1];
      		fm.AgentGroup1.value=brrResult[0][2];
      	}
      }	
     }
     else
     {
      var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_32");
      mySql.addPara('InputNo5',fm.InputNo5.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("无此保单信息，管理机构查询失败");	
        fm.PolicyCom.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_33");
        mySql.addPara('InputNo5',fm.InputNo5.value);
        var brrResult = easyExecSql(mySql.getString());
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("无此保单信息，代理人查询失败");	
          fm.AgentCode1.value="";
      		fm.AgentName1.value="";
      		fm.AgentGroup1.value="";
          return false;
        }
        else
       	{
       		fm.AgentCode.value=brrResult;
       	}
      } 
    }
    contFlag = true;
	}
	return true;
}

function getEdorCode()
{
  if(fm.all('PayDate').value == null && fm.all('PayDate').value == "")
  {
  	alert("请录入交费日期");
  	return false;
  }
  
  if((fm.all('InputNo7').value!=null&&fm.all('InputNo7').value!="")&&(fm.all('InputNo8').value==null||fm.all('InputNo8').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
   	mySql.setSqlId("TempFeeInputInputSql_21");
   	mySql.addPara('InputNo7',fm.InputNo7.value);
   	mySql.addPara('PayDate',fm.all('PayDate').value);
   	var arrResult = easyExecSql( mySql.getString());
   	
   	if(arrResult == null ||arrResult == ""||arrResult=='null')
   	{
   	 alert("没有保全应收记录或已经银行在途，请确认!");
   	 return false;	
   	}
  	else
  	{
  	  fm.InputNo8.value=arrResult;	//交费收据号码
  	}
  }	
  
  if((fm.all('InputNo8').value!=null&&fm.all('InputNo8').value!="")&&(fm.all('InputNo7').value==null||fm.all('InputNo7').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
  	mySql.setSqlId("TempFeeInputInputSql_22");
  	mySql.addPara('InputNo8',fm.InputNo8.value);
  	var arrResult = easyExecSql( mySql.getString());
  	if(arrResult==null||arrResult=="")
  	{
  	 alert("保全受理号不存在，或者已逾期不能收费，请确认!");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else
  	{
  	 fm.InputNo7.value=arrResult;	//保全受理号
  	}
  }
  
  if(fm.all('InputNo8').value!=null&&fm.all('InputNo8').value!=""&&fm.all('InputNo7').value!=null&&fm.all('InputNo7').value!=""){
  	//InputNo7 受理号
  	//InputNo8 收据号
  	edorFlag=true;
   	var strSql = "";
  	mySql.setSqlId("TempFeeInputInputSql_22");
  	mySql.addPara('InputNo8',fm.InputNo8.value);
  	var arrResult = easyExecSql( mySql.getString());
  	if(arrResult==null||arrResult==""){
  	 alert("保全受理号不存在，或者已逾期不能收费，请确认!");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else{
  	 fm.InputNo7.value=arrResult;	//保全受理号
  	}
  }
  if (fm.all('InputNo7').value!=null&&fm.all('InputNo7').value!="")
  {
    var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_23");
    mySql.addPara('InputNo8',fm.InputNo8.value);
    mySql.addPara('PayDate',fm.all('PayDate').value);
    var arrResult = easyExecSql(mySql.getString());
    
    if(arrResult == null ||arrResult == ""||arrResult=='null'){
      alert("保全受理号不存在，或者已逾期不能收费，请确认!");
      return false;
    }else{
      fm.PolicyCom.value=arrResult;
    }         
  }
}

//理赔
function getLJSPayPolicyCom()
{
  if (fm.InputNo11.value!=null&&fm.InputNo11.value!=""&&fm.InputNo11.value!='null')	
  {
	  var strSql = "";             
    mySql.setSqlId("TempFeeInputInputSql_27");
    mySql.addPara('InputNo11',fm.InputNo11.value);
    var arrResult = easyExecSql( mySql.getString());
    if(arrResult!=null&&arrResult!=""&&arrResult!="null"){
    	clmFlag=true;
    	fm.PolicyCom.value=arrResult[0][0]; 
    	fm.InputNo12.value=arrResult[0][1]; //收据号
    }else{
    	alert("没有查询到理赔应收记录");
    }
  }
  else if (fm.InputNo12.value!=null&&fm.InputNo12.value!=""&&fm.InputNo12.value!='null')
  {
	  var strSql = "";
    mySql.setSqlId("TempFeeInputInputSql_28");
    mySql.addPara('InputNo12',fm.InputNo12.value);
    var arrResult = easyExecSql(mySql.getString());
    if(arrResult!=null&&arrResult!=""&&arrResult!="null")
    {
    	clmFlag=true;
    	fm.PolicyCom.value=arrResult[0][0];
    	fm.InputNo11.value=arrResult[0][1]; //理赔号
  	}else{
  		alert("没有查询到理赔应收记录");
  	}
  } 
}

//不定期缴
function getUrgePolicyCom()
{
	if (fm.InputNo22.value!=null&&fm.InputNo22.value!="")
	{
	  var flag = "";
	  var strSql2 = "";
    mySql.setSqlId("TempFeeInputInputSql_29"); //团单查询
    mySql.addPara('InputNo5',fm.InputNo22.value);
	  var arrResult2 = easyExecSql( mySql.getString());
	  if(arrResult2 == null)
	  {
	    var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
      mySql.addPara('InputNo22',fm.InputNo22.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("无此保单信息，管理机构查询失败");	
        fm.AgentCode1.value = "";
      	fm.AgentName1.value = "";
      	fm.AgentGroup1.value= "";
        fm.PolicyCom.value  = "";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_31"); //select agentcode from LCCont where contno='?InputNo22?'
        mySql.addPara('InputNo22',fm.InputNo22.value);
        var brrResult = easyExecSql( mySql.getString());
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("无此保单信息，代理人查询失败");	
          fm.AgentCode.value="";
          return false;
        }
        else
      	{
      		fm.AgentCode1.value=brrResult[0][0];
      		fm.AgentName1.value=brrResult[0][1];
      		fm.AgentGroup1.value=brrResult[0][2];
      	}
      }	
     }else
     	{ //个单查询
      var strSql = "";
      mySql.setSqlId("TempFeeInputInputSql_32");
      mySql.addPara('InputNo22',fm.InputNo22.value);
      var arrResult = easyExecSql(mySql.getString());
      if(arrResult==null||arrResult=="NULL"||arrResult=="")
      {
        alert("无此保单信息，管理机构查询失败");	
        fm.PolicyCom.value="";
        return false;
      }
      else
  	  {
        fm.PolicyCom.value=arrResult;	  	  
        var ASql = "";
        mySql.setSqlId("TempFeeInputInputSql_33");
        mySql.addPara('InputNo22',fm.InputNo22.value);
        var brrResult = easyExecSql(mySql.getString());
        if(brrResult==null||brrResult=="NULL"||brrResult=="")
        {
          alert("无此保单信息，代理人查询失败");	
          fm.AgentCode.value="";
          return false;
        }
        else
       	{
       		fm.AgentCode.value=brrResult;
       	}
      } 
    }
    urgeFlag=true;
	}	
}

function getImportPath(){
  var strSQL = "";
  strSQL = "select sysvarvalue from ldsysvar where sysvar='LJXmlPath'";
  
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未找到上传路径");
    return;
  }
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	
  ImportPath = turnPage.arrDataCacheSet[0][0];
}

function importData(){
	try{
		if(!confirm("确定要导入选中的文件数据吗？")){
			return false;
		}
		
		if(fmImport.fileName.value==""||fmImport.fileName.value==null){
			alert("录入导入文件路径！");
			return false;
		}
		
		if((fmImport.fileName.value).substr((fmImport.fileName.value.length-18),fmImport.fileName.value.length)=='LJFinFeeImport.xls'){
			alert("请将导入文件改名");
			return false;
		}
		var i = 0;
  	getImportPath();
  	
  	ImportFile = fmImport.all('FileName').value; 
  	
  	var showStr="正在上载数据……"; 
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  	fmImport.action = "./TempFinFeeImportSave.jsp?ImportPath="+ImportPath ; 
  	fmImport.submit(); //提交 
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}

function queryLJSPay(){
   if(fm.all('InputNo3').value!=""){
   }
   else{
    alert("请输入保单号");
   }
}

function clearFormData(){
	clearOpeTypeInfo();
	fm.TempFeeType.value="";
	fm.TempFeeTypeName.value="";
	TempToGrid.clearData();
	fm.OperateSum.value="";
	fm.OperateSub.value="";
	fm.addButton.style.display='';
		    //更新2010-2-26
    initTTempToGrid();  
    initTTempClassToGrid(); 
	return true;
}

function alink(){
	window.location.href="../temp/finfee/import/LJFinFeeImport.xls";
}


/**
 * 删除一行
 */
function _DeleteOne(strPageName, spanID, cObjInstance)
{
	var tStr;
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
    	tObjInstance = this;
    }
    else
    {
    	tObjInstance = cObjInstance;
      var spanName = spanID;
      spanID = eval(tObjInstance.formName + ".all('" + spanName + "')");
    }
    tObjInstance.errorString = "";
    var spanObj = eval(tObjInstance.formName + ".all('span" + t_StrPageName + "')");
    try
    {
      spanID.innerHTML = "";
      tObjInstance.errorString = "";
      tObjInstance.mulLineCount = tObjInstance.mulLineCount - 1;
      tStr = "<SPAN id=" + spanID.id + "></SPAN>";
      spanObj.innerHTML = replace(spanObj.innerHTML, tStr, "");
      _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance);
      
      sumMoney();
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _DeleteOne 函数中发生异常：" + ex, tObjInstance);
    }
}

//查询暂收据是否已下发到该业务员
function _CheckDoc()
{
	// 拼SQL语句，从页面采集信息
  var strSql = "";
  var agentCode = "";
  mySql.setSqlId("TempFeeInputInputSql_Temp");
  mySql.addPara('TempNo',fm.all('TempFeeNo1').value);
  agentCode = fm.AgentCode.value;

  if(agentCode == null || agentCode == "")
  {
  	agentCode = "D"+fm.AgentCode1.value;
  }
  else
  {
  	agentCode = "D"+fm.AgentCode.value;
  }
  
  mySql.addPara('AgentCode',agentCode);
  strSql = mySql.getSQL();
  
  //查询付费机构sql,返回结果
  var strArray = easyExecSql(strSql);
  if(strArray==null){
  	return false;
  }
  return true;
}

function _CheckCurrency()
{
	//第一步判断同一个业务号码（投保单号、保单号、保全受理号）与TTempToGrid的币种是否相同
	var flag="";
	var strSql = "";
	for(var i=0;i<TTempToGrid.mulLineCount;i++)
	{ 
		//新单
		if(fm.TempFeeType.value=='1')
		{
			//判断业务号码是否相同
		  if(fm.InputNo1.value == TTempToGrid.getRowColData(i,10))
		  {
			  //判断币种是否相同
			  if(fm.OpeCurrency.value	 != TTempToGrid.getRowColData(i,18))
			  {
				  flag = "0";
			  }
		  }
		}
		
		//续期
		if(fm.TempFeeType.value=='3')
		{
			//判断业务号码是否相同
		  if(fm.InputNo5.value == TTempToGrid.getRowColData(i,10))
		  {
			  //判断币种是否相同
			  if(fm.OpeCurrency.value	 != TTempToGrid.getRowColData(i,18))
			  {
				  flag = "0";
			  }
		  }
		}
		
		//保全
		if(fm.TempFeeType.value=='4')
		{
			//判断业务号码是否相同
		  if(fm.InputNo7.value == TTempToGrid.getRowColData(i,10))
		  {
			  //判断币种是否相同
			  if(fm.OpeCurrency.value	 != TTempToGrid.getRowColData(i,18))
			  {
				  flag = "0";
			  }
		  }
		}

	}
//	alert("flag:"+flag);
	//第二步判断与ljtempfee的币种是否相同
	if (flag == null || flag=="" || flag=="null")
	{
		var strSql = "";
		var OtherNo = "";
		if(fm.TempFeeType.value=='1')
		{
			OtherNo = fm.InputNo1.value;
		}
		if(fm.TempFeeType.value=='3')
		{
			OtherNo = fm.InputNo1.value;
		}
		if(fm.TempFeeType.value=='4')
		{
			OtherNo = fm.InputNo1.value;
		}
	  mySql.setSqlId("TempFeeInputInputSql_LJCurrency");
    mySql.addPara('OtherNo',OtherNo);
    strSql = mySql.getSQL();
  
    //查询付费机构sql,返回结果
    var strArray = easyExecSql(strSql);
    if(strArray != null){
  	  if(fm.OpeCurrency.value != strArray[0][0])
  	  {
  	  	flag = "0";
  	  }
    }
	}

	//第三步判断与lccont中的币种是否相同
	if (flag == null || flag=="" || flag=="null")
	{
		 var strSql = "";
		 var OtherNo = "";
		 if(fm.TempFeeType.value=='1')
		 {
			 OtherNo = fm.InputNo1.value;
		 }
		 if(fm.TempFeeType.value=='3')
		 {
			 OtherNo = fm.InputNo1.value;
		 }
		 if(fm.TempFeeType.value=='4')
		 {
			 OtherNo = fm.InputNo1.value;
		 }
		 
	   mySql.setSqlId("TempFeeInputInputSql_LCCurrency");
     mySql.addPara('OtherNo',OtherNo);
     strSql = mySql.getSQL();
     //查询付费机构sql,返回结果
     var strArray = easyExecSql(strSql);
     if(strArray != null){
  	 if(fm.OpeCurrency.value != strArray[0][0])
  	 {
  	    flag = "0";
  	 }
    }
	}
	
	//如果三步判断后flag==null，表示为第一次进行收付费，没有币种限制
	if (flag == null || flag=="" || flag=="null")
	{
		flag == "1";
	}
  
  if(flag == "0")
  {
  	return false;
  }
  else
  {
  	return true;
  }
}

//查询暂收据是否已进入暂收表
function _CheckTempNo()
{
	// 拼SQL语句，从页面采集信息
  var strSql = "";
  var agentCode = "";
  mySql.setSqlId("TempFeeInputInputSql_TempNo");
  mySql.addPara('TempNo',fm.all('TempFeeNo1').value);
  strSql = mySql.getSQL();
  
  //查询付费机构sql,返回结果
  var strArray = easyExecSql(strSql);

  if(strArray==null){
  	return true;
  }
  return false;
}

//查询暂收据是否已过最大日期
function _CheckMaxDate()
{
	// 拼SQL语句，从页面采集信息
  var strSql = "";
  var agentCode = "";
  mySql.setSqlId("TempFeeInputInputSql_MaxDate");
  mySql.addPara('TempNo',fm.all('TempFeeNo1').value);
  mySql.addPara('Date',fm.all('PayDate').value);
  strSql = mySql.getSQL();
  
  //查询付费机构sql,返回结果
  var strArray = easyExecSql(strSql);
  if(strArray==null){
  	return false;
  }
  return true;
}

//更新2010-2-26
//添加一笔纪录
function addRecord()
{
	  
	//现金添加到账日期
	var EnterAccDate=""; //到帐日期
//	if(FinFeeGrid.getRowColData(0,1)=="1"){
		EnterAccDate=fm.all('PayDate').value;
//	}
  var TempToGridCount=TempToGrid.mulLineCount; //TempToGrid的行数 
	//校验暂收据号码是否唯一
	var ccount=TTempToGrid.mulLineCount;
	for(c=0;c<TempToGridCount;c++){
		for(c1=0;c1<ccount;c1++){
			if(TempToGrid.getRowColData(c,20)!= TTempToGrid.getRowColData(c1,1)){
						alert(" 存在多个暂收据号码，请分开操作！");
						return false;
			}
		}
	} 
	
	//添加校验，校验业务合计金额是否与财务收费总金额相等，gaoln
	var tempFee =0; 
	for(var i=0;i<TempToGridCount;i++)
	{ 
		tempFee=tempFee + 1000000000 * parseFloat(TempToGrid.getRowColData(i,4)); 
	}
	if(pointTwo(tempFee)!=pointTwo(sumTempFee))
	{
		alert("业务金额合计必须等于财务收费总和，请修正！");
		return false;
	}
	 
  //暂交费分类信息(TempToGrid)赋给用于提交数据的(TTempClassToGrid)
 	for(i=0;i<TempToGridCount;i++)
 	{	
 		 var tcount=TTempClassToGrid.mulLineCount; //得到MulLine的行数 

 		 TTempClassToGrid.addOne("TTempClassToGrid");  
     TTempClassToGrid.setRowColData(tcount,1,TempToGrid.getRowColData(i,20));  
     TTempClassToGrid.setRowColData(tcount,2,FinFeeGrid.getRowColData(0,1));
     TTempClassToGrid.setRowColData(tcount,3,fm.all('PayDate').value);
     TTempClassToGrid.setRowColData(tcount,4,TempToGrid.getRowColData(i,4));
     TTempClassToGrid.setRowColData(tcount,5,TempToGrid.getRowColData(i,5));
     TTempClassToGrid.setRowColData(tcount,6,TempToGrid.getRowColData(i,19));
     TTempClassToGrid.setRowColData(tcount,7,EnterAccDate); 
     TTempClassToGrid.setRowColData(tcount,8,fm.all('ManageCom').value);   
     TTempClassToGrid.setRowColData(tcount,9,FinFeeGrid.getRowColData(0,5));  
     TTempClassToGrid.setRowColData(tcount,10,FinFeeGrid.getRowColData(0,6));   
     TTempClassToGrid.setRowColData(tcount,11,FinFeeGrid.getRowColData(0,7));    
     TTempClassToGrid.setRowColData(tcount,12,FinFeeGrid.getRowColData(0,9));    
     TTempClassToGrid.setRowColData(tcount,13,FinFeeGrid.getRowColData(0,10));    
     TTempClassToGrid.setRowColData(tcount,14,FinFeeGrid.getRowColData(0,11));
     TTempClassToGrid.setRowColData(tcount,15,FinFeeGrid.getRowColData(0,12));
     TTempClassToGrid.setRowColData(tcount,16,FinFeeGrid.getRowColData(0,13));
     TTempClassToGrid.setRowColData(tcount,17,TempToGrid.getRowColData(i,13));
     TTempClassToGrid.setRowColData(tcount,18,TempToGrid.getRowColData(i,14));
     TTempClassToGrid.setRowColData(tcount,19,FinFeeGrid.getRowColData(0,7));
     TTempClassToGrid.setRowColData(tcount,20,FinFeeGrid.getRowColData(0,14));
     TTempClassToGrid.setRowColData(tcount,21,FinFeeGrid.getRowColData(0,15));
		 TTempClassToGrid.setRowColData(tcount,22,TempToGrid.getRowColData(i,11));
		 TTempClassToGrid.setRowColData(tcount,23,TempToGrid.getRowColData(i,12));  
		 TTempClassToGrid.setRowColData(tcount,24,FinFeeGrid.getRowColData(0,19));   
     //汇总主表数据
     var scount=TTempToGrid.mulLineCount;
     if(scount<=0){
	 	 				TTempToGrid.addOne("TTempToGrid");                                                       
	 	 				TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	1,TempToGrid.getRowColData(i,20));      
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	2,TempToGrid.getRowColData(0,18));  
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	3,fm.all('PayDate').value);        
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	4,TempToGrid.getRowColData(i,4));  
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	5,EnterAccDate); 		 
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	6,fm.all('ManageCom').value);		   
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	7,TempToGrid.getRowColData(i,8));		
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	8,TempToGrid.getRowColData(i,9)); 	
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	9,TempToGrid.getRowColData(i,10)); 
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	10,TempToGrid.getRowColData(i,11));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	11,TempToGrid.getRowColData(i,12));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	12,TempToGrid.getRowColData(i,13));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	13,TempToGrid.getRowColData(i,14));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	14,TempToGrid.getRowColData(i,15));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	15,TempToGrid.getRowColData(i,16));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	16,TempToGrid.getRowColData(i,17));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	17,TempToGrid.getRowColData(i,1));
						TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	18,TempToGrid.getRowColData(i,5));						
     	
     }else{
     			 var findFlag=0;
					 for(j=0;j<scount;j++)
				 	 {	
				 	 		//新数据追加,老数据累加,暂不支持删除
//				 	 		alert(TTempToGrid.getRowColData(j,1)+"&&"+TempToGrid.getRowColData(i,20)+"||"+TTempToGrid.getRowColData(j,2)+"&&"+TempToGrid.getRowColData(i,2)+"||"+TTempToGrid.getRowColData(j,7)+"&&"+TempToGrid.getRowColData(i,8)+"||"+TTempToGrid.getRowColData(j,10)+"&&"+ TempToGrid.getRowColData(i,11)+"||"+TTempToGrid.getRowColData(j,11)+"&&"+TempToGrid.getRowColData(i,12));
				 	 		//alert(TTempToGrid.getRowColData(j,10)+"&&"+ TempToGrid.getRowColData(i,11)+"||"+TTempToGrid.getRowColData(j,11)+"&&"+TempToGrid.getRowColData(i,12));
//				 	 		if(TTempToGrid.getRowColData(j,1) == TempToGrid.getRowColData(i,20) &&
//				 	 			 TTempToGrid.getRowColData(j,2) == TempToGrid.getRowColData(i,21) &&
//				 	 			 TTempToGrid.getRowColData(j,7) == TempToGrid.getRowColData(i,8) &&
//				 	 			 TTempToGrid.getRowColData(j,10) == TempToGrid.getRowColData(i,11) &&
//				 	 			 TTempToGrid.getRowColData(j,11) == TempToGrid.getRowColData(i,12) 
//				 	 		){
				 	 		if(				 	 						 	 			 
				 	 			 TTempToGrid.getRowColData(j,10) == TempToGrid.getRowColData(i,11) &&
				 	 			 TTempToGrid.getRowColData(j,11) == TempToGrid.getRowColData(i,12) &&
				 	 			 TTempToGrid.getRowColData(j,2) == TempToGrid.getRowColData(i,18)  &&
				 	 			 TTempToGrid.getRowColData(j,1) == TempToGrid.getRowColData(i,20)
				 	 		){
//				 	 				alert(TTempToGrid.getRowColData(j,4)+"&&"+TempToGrid.getRowColData(i,4));
//				 	 				alert(pointTwo(parseFloat(TTempToGrid.getRowColData(j,4))+parseFloat(TempToGrid.getRowColData(i,4))));
									TTempToGrid.setRowColData(j,	4,pointTwo(parseFloat(TTempToGrid.getRowColData(j,4))+parseFloat(TempToGrid.getRowColData(i,4))));		 	 			
				 	 				findFlag=1;
				 	 		}				 	 		
					 } 
					 if(findFlag!=1){
					 				TTempToGrid.addOne("TTempToGrid"); 
				 	 				TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	1,TempToGrid.getRowColData(i,20));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	2,TempToGrid.getRowColData(0,18));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	3,fm.all('PayDate').value);
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	4,TempToGrid.getRowColData(i,4));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	5,EnterAccDate); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	6,fm.all('ManageCom').value);		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	7,TempToGrid.getRowColData(i,8));		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	8,TempToGrid.getRowColData(i,9)); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	9,TempToGrid.getRowColData(i,10)); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	10,TempToGrid.getRowColData(i,11));    
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	11,TempToGrid.getRowColData(i,12));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	12,TempToGrid.getRowColData(i,13));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	13,TempToGrid.getRowColData(i,14)); 		
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	14,TempToGrid.getRowColData(i,15));    
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	15,TempToGrid.getRowColData(i,16));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	16,TempToGrid.getRowColData(i,17));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	17,TempToGrid.getRowColData(i,1));
									TTempToGrid.setRowColData(TTempToGrid.mulLineCount-1,	18,TempToGrid.getRowColData(i,5));
					 }
		 }            
	}
	initFinFeeGrid();
	initTempToGrid();
	fm.TempFeeType.value="";
	fm.TempFeeTypeName.value="";
	fm.OperateSum.value="";
}
function submitForm1(){	
	if(TTempToGrid.mulLineCount==0 || TTempClassToGrid.mulLineCount==0){
		alert(" 请录入财务收费信息！");
		return false;
	}
	try {
		  	var i = 0; 		  	
		  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"; 
		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  	var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus(); 
				fmSave.action = "./TempFinFeeSave.jsp"; 
		  	fmSave.submit(); //提交
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}
//更新2010-2-26

//日期校验
function CheckDate(Filed)
{
	var tDate = Filed.value;
	var Year  = "";
	var Month = "";
	var Day   = "";
	//输入日期八位，YYYYMMDD格式
	if(tDate.length == 8)
	{
		if(tDate.indexOf('-') == -1)
		{		
			Year  = tDate.substring(0,4);
			Month = tDate.substring(4,6);
			Day   = tDate.substring(6,8);	
			tDate = Year+"-"+Month+"-"+Day;
		}
	    else
		{
			alert("您输入的日期有误，请重新输入！");
			return Filed.value = "";
		}
	}
	//输入日期10位，YYYY-MM-DD格式
	else if(tDate.length == 10)
	{
		if((tDate.substring(4,5) != '-')||(tDate.substring(7,8) != '-'))
		{
			alert("您输入的日期有误，请重新输入！");
			return Filed.value = "";
		}		
		Year  = tDate.substring(0,4);
		Month = tDate.substring(5,7);
		Day   = tDate.substring(8,10);	
		tDate = Year+"-"+Month+"-"+Day;	
	}
	//输入日期既不是YYYYMMDD格式，也不是YYYY-MM-DD格式
	else
	{
	    if(tDate == null||tDate == "")//输入为空，返回空值，不报错
	    {
	    	return Filed.value = "";
	    }
	    else//输入不为空，提示出错
	    {
	  	    alert("您输入的日期有误，请重新输入！");
	  	    return Filed.value = "";        	
	    }	
	}
    //校验输入日期是否为非零数字
	if((!isInteger(Year))||(!isInteger(Month))||(!isInteger(Day))||(Year == "0000")||(Month == "00")||(Day == "00"))
	{
	    alert("您输入的年月日有误，请重新输入！");
	    return Filed.value = "";
	}	    
    //对月分日期做进一步精确校验 
	if(Month>12){alert("您的输入有误，一年只有12个月，请重新输入！");return Filed.value = "";}
	if(Month=="01"&&Day>31){alert("您的输入有误，一月只有31日，请重新输入！");return Filed.value = ""; }
    if(Month=="02"&&Day>29){alert("您的输入有误，二月最多只有29日，请重新输入！");return Filed.value = "";}
    if(Month=="02"&&Day==29)//二月要判断是否为闰年
    {
    	if((Year%100==0)&&(Year%400!=0))//非闰年判断
    	{
    		alert("您的输入有误，非闰年二月只有28日，请重新输入！");return Filed.value = "";
    	}
    	if((Year%100!=0)&&(Year%4!=0))//非闰年判断
    	{
    		alert("您的输入有误，非闰年二月只有28日，请重新输入！");return Filed.value = "";
    	}
    } 
	if(Month=="03"&&Day>31){alert("您的输入有误，三月只有31日，请重新输入！");return Filed.value = "";}
	if(Month=="04"&&Day>30){alert("您的输入有误，四月只有30日，请重新输入！");return Filed.value = "";}
	if(Month=="05"&&Day>31){alert("您的输入有误，五月只有31日，请重新输入！");return Filed.value = "";}
	if(Month=="06"&&Day>30){alert("您的输入有误，六月只有30日，请重新输入！");return Filed.value = "";}
	if(Month=="07"&&Day>31){alert("您的输入有误，七月只有31日，请重新输入！");return Filed.value = "";}
	if(Month=="08"&&Day>31){alert("您的输入有误，八月只有31日，请重新输入！");return Filed.value = "";}
	if(Month=="09"&&Day>30){alert("您的输入有误，九月只有30日，请重新输入！");return Filed.value = "";}
	if(Month=="10"&&Day>31){alert("您的输入有误，十月只有31日，请重新输入！");return Filed.value = "";}
	if(Month=="11"&&Day>30){alert("您的输入有误，十一月只有30日，请重新输入！");return Filed.value = "";}
	if(Month=="12"&&Day>31){alert("您的输入有误，十二月只有31日，请重新输入！");return Filed.value = "";}
	
	Filed.value = tDate;//校验通过后，返回值
}