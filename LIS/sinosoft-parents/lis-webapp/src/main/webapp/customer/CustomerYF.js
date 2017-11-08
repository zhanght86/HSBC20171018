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
var currname = "";
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

var tResourceName = "customer.CustomerYFSql";
var tResourceSQL1="CustomerYFSql1";
var tResourceSQL2="CustomerYFSql2";
var tResourceSQL3="CustomerYFSql3";
var tResourceSQL4="CustomerYFSql4";
var tResourceSQL5="CustomerYFSql5";
var tResourceSQL6="CustomerYFSql6";
var tResourceSQL7="CustomerYFSql7";
var tResourceSQL8="CustomerYFSql8";
var tResourceSQL9="CustomerYFSql9";
var tResourceSQL10="CustomerYFSql10";
var tResourceSQL11="CustomerYFSql11";
var tResourceSQL12="CustomerYFSql12";
var tResourceSQL13="CustomerYFSql13";
var tResourceSQL14="CustomerYFSql14";
var tResourceSQL15="CustomerYFSql15";
var tResourceSQL16="CustomerYFSql16";
var tResourceSQL17="CustomerYFSql17";
var tResourceSQL18="CustomerYFSql18";
var tResourceSQL19="CustomerYFSql19";
var tResourceSQL20="CustomerYFSql20";
var tResourceSQL21="CustomerYFSql21";
var tResourceSQL22="CustomerYFSql22";




var mySql=new SqlClass();
//mySql.setJspName("../../customer/CustomerYFInputSql.jsp");
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
	fm.OperateType.value="INSERT";
	try {
		if( verifyInput() == true) //&& FinFeeGrid.checkValue("FinFeeGrid")&&MatchInfoGrid.checkValue("MatchInfoGrid")
		{
			//if (verifyInput3()==true){
		  	var i = 0; 
		  	
		  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"; 
		  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		  	var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
				fm.action = "./CustomerYFSave.jsp"; 
				
				document.all("signbutton").disabled = true;
		  	document.getElementById("fm").submit(); //提交
	  	//}
	  	//else{
	  	//}
	  }
  } catch(ex){
  	showInfo.close( );
  	alert(ex);
  }
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
{
	showInfo.close();
	document.all("signbutton").disabled=false;
	try {
		if (FlagStr == "Fail" ) {             
	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	  } else { 
		  var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		  var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		  clearFormData();
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
	clearFormData();
	FinFeeGrid.clearData();
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
	if(fm.PayMode.value=='5'){
	currname = QueryLJAGetGrid.getRowColData(0,4);
		payTransAccout();
	}
	
	if(fm.PayMode.value=='6'){
		payPos();
	}
	
	sumTempFee = parseFloat(pointTwo(FinFeeGrid.getRowColData(0,3)));
	//clearFinInfo();
	QueryLJAGetGrid.clearData('QueryLJAGetGrid');  
	
}

function clearFinInfo(){
	
	
	fm.PayMode.value					=	'';
	fm.PayModeName.value			=	'';
	fm.Currency.value					=	'';
	fm.CurrencyName.value			=	'';
	fm.PayFee1.value					=	'';
	fm.PayFee2.value					=	'';
	fm.InBankCode2.value			=	'';
	fm.InBankCode2Name.value	=	'';
	fm.InBankAccNo2.value			=	'';
	
	fm.PayFee3.value					=	'';
	fm.ChequeNo3.value				=	'';
	fm.ChequeDate3.value			=	'';
	fm.BankCode3.value				=	'';
	fm.BankAccNo3.value				=	'';
	fm.AccName3.value					=	'';
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
	                           	
	fm.BankCode3Name.value		=	''; //转账支票 开户银行名
	fm.InBankCode6Name.value	=	''; //POS收款 收款银行名
	                           	
	fm.TempFeeNo1.value				=	'';
	fm.PayName.value					=	''

	return true;
}

//财务收费信息校验
function verifyInput6(){

	if(TempToGrid. mulLineCount!=0){
		alert("请先撤销业务费用信息再进行确认！");
		return false;
	}
	
	if(fm.PayMode.value==''||fm.PayMode.value==null){
		alert("请选择交费类型！");
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
	if(fm.PayMode.value=='2'){
		if(fm.PayFee2.value==''||fm.PayFee2.value==null){
			alert("请录入交费金额！");
			return false;
		}
		if(fm.ChequeNo2.value==''||fm.ChequeNo2.value==null){
			alert("请录入票据号码！");
			return false;
		}
		if(!verifyNumber("交费金额",fm.PayFee2.value)){
			alert("交费金额不是有效数字！");
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
			alert("请录入支票日期！");
			return false;
		}
		if(fm.BankCode3.value==''||fm.BankCode3.value==null){
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
		//if(fm.InBankCode3.value==''||fm.InBankCode3.value==null){
		//	alert("请录入收款银行！");
		//	return false;
		//}
		//if(fm.InBankAccNo3.value==''||fm.InBankAccNo3.value==null){
		//	alert("请录入收款银行账号！");
		//	return false;
		//}
	}
	if(fm.PayMode.value=='5'){
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
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
	
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
	FinFeeGrid.setRowColData(0,7,fm.InBankCode2.value);
	FinFeeGrid.setRowColData(0,8,fm.InBankCode2Name.value);
	FinFeeGrid.setRowColData(0,9,fm.InBankAccNo2.value);
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
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
	FinFeeGrid.setRowColData(0,7,fm.BankCode3.value);	
	FinFeeGrid.setRowColData(0,9,fm.BankAccNo3.value);	
	FinFeeGrid.setRowColData(0,10,fm.AccName3.value);	
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
}
//内部转账
function payTransAccout(){
	temp=pointTwo(fm.PayFee5.value);
	fm.PayFee5.value=temp;
	FinFeeGrid.setRowColData(0,1,fm.PayMode.value);
	FinFeeGrid.setRowColData(0,2,fm.PayModeName.value);
	FinFeeGrid.setRowColData(0,3,temp);
	FinFeeGrid.setRowColData(0,4,fm.Currency.value);	
	FinFeeGrid.setRowColData(0,5,currname);	
	//FinFeeGrid.setRowColData(0,17,fm.ActuGetNo5.value);	
	//FinFeeGrid.setRowColData(0,18,fm.Drawer5.value);	
	//FinFeeGrid.setRowColData(0,19,fm.DrawerID.value);	
	//FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	//FinFeeGrid.setRowColData(0,19,fm.PayName.value);
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
	FinFeeGrid.setRowColData(0,18,fm.TempFeeNo1.value);
	FinFeeGrid.setRowColData(0,19,fm.PayName.value);
}

/*********************************************************************
 *  收费方式选择内部转帐时，查询实付数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryLJAGet()
{
  
      EasyQueryPay();
   
}

function EasyQueryPay ()
{

  // 拼SQL语句，从页面采集信息
  var strSQL = "";
  var strSQL2 = "";

  //mySql.setSqlId("CustomerYFInputSql_34");
  //strSql = mySql.getSQL();

  if (document.all('ActuGetNo5').value!="")
  {

  	//mySql.setSqlId("CustomerYFInputSql_35");
  	//mySql.addPara('ActuGetNo5',document.all('ActuGetNo5').value);
  	//strSql += mySql.getSQL();	
  	strSQL2 = strSQL2 + " and actugetno='"+ document.all('ActuGetNo5').value+"'";  

  }
  if(document.all('OtherNo5').value!="")
	{
		//mySql.setSqlId("CustomerYFInputSql_36");
  	//mySql.addPara('OtherNo5',document.all('OtherNo5').value);
  	//strSql += mySql.getSQL();
  	strSQL2 = strSQL2 + " and otherno='"+ document.all('OtherNo5').value+"'";  

	}
  
  //查询付费机构sql,返回结果
  QueryLJAGetGrid.clearData('QueryLJAGetGrid'); 
 

  strSQL = wrapSql(tResourceName,tResourceSQL1,[strSQL2]); 
 
  var strArray = easyExecSql(strSQL );
  if(strArray==null){
  	alert("没有查询到数据！");
  	return false;
  }
  turnPage.queryModal(strSQL, QueryLJAGetGrid);
} 


function GetRecord(){
  var tSel = QueryLJAGetGrid.getSelNo();
	fm.ActuGetNo5.value	= QueryLJAGetGrid.getRowColData(tSel-1,1);
	fm.OtherNo5.value		=	QueryLJAGetGrid.getRowColData(tSel-1,2);
	fm.PayFee5.value		=	pointTwo(QueryLJAGetGrid.getRowColData(tSel-1,3));
	fm.CurrencyName.value		=	QueryLJAGetGrid.getRowColData(tSel-1,4);
	fm.Currency.value		=	QueryLJAGetGrid.getRowColData(tSel-1,5);
	
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
			alert("交费方式为转账支票请录入支票日期!");
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
	  }else if(Field.value=="4"||Field.value=="5"||Field.value=="6"){
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
			document.all("divPayMode"+i).style.display='';
		}
		else
		{
		  document.all("divPayMode"+i).style.display='none';
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
  if (document.all("PayMode").value == '0')
  {
  	document.all("PayFee0").value = SumPrem;
  	}
  if (document.all("PayMode").value == '1')
  {
  	document.all("PayFee1").value = SumPrem;
  	}
  if (document.all("PayMode").value == '2')
  {
  	document.all("PayFee2").value = SumPrem;
    }
  if (document.all("PayMode").value == '3')
  {
  	document.all("PayFee3").value = SumPrem;
    }
  if (document.all("PayMode").value == '4')
  {
  	document.all("PayFee4").value = SumPrem;
  }
  //if (document.all("PayMode").value == '5')
  //{
  //	document.all("PayFee5").value = SumPrem;
  //}
  if (document.all("PayMode").value == '6')
  {
  	document.all("PayFee6").value = SumPrem;
  }
  if (document.all("PayMode").value == '7')
  {
  	document.all("PayFee7").value = SumPrem;
  }
  if (document.all("PayMode").value == '8')
  {
  	document.all("PayFee8").value = SumPrem;
  }
  if (document.all("PayMode").value == '9')
  {
  	document.all("PayFee9").value = SumPrem;
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
      document.all("TempFeeType" + (i+1)).style.display = '';
    }
    else {
      document.all("TempFeeType" + (i+1)).style.display = 'none';
    }
  }
}

function queryAgent()
{
	if(document.all('ManageCom').value==""){
		alert("请先录入收费机构信息！"); 
		return;
	}
	if(fm.AgentCode.value != ""&&fm.AgentCode.value != null){
		var cAgentCode = fm.AgentCode.value;
		var strSql = "";
  	//mySql.setSqlId("CustomerYFInputSql_13");
  	//mySql.addPara('cAgentCode',cAgentCode);
  	
    strSql = wrapSql(tResourceName,tResourceSQL2,[cAgentCode]); 
  	var arrResult = easyExecSql( strSql);
  	if (arrResult != null) {
  	  alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
  	} 
	}else{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

function GetManageCom()
{
  if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
  {
  	var strSql = "";
   	//mySql.setSqlId("CustomerYFInputSql_24");
   	//mySql.addPara('AgentCode',fm.AgentCode.value);
   	strSql = wrapSql(tResourceName,tResourceSQL3,[fm.AgentCode.value]); 
   	
   	var arrResult = easyExecSql(strSql);
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
    //mySql.setSqlId("CustomerYFInputSql_25");
    //mySql.addPara('AgentCode',fm.AgentCode.value);
    
    aSql = wrapSql(tResourceName,tResourceSQL4,[fm.AgentCode.value]); 
    
  	arrResult = easyExecSql(aSql);
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
      //mySql.setSqlId("CustomerYFInputSql_26");
      //mySql.addPara('AgentCode',fm.AgentCode.value);
      
      nSql = wrapSql(tResourceName,tResourceSQL5,[fm.AgentCode.value]); 
      arrResult = easyExecSql(nSql);
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
  //mySql.setSqlId("CustomerYFInputSql_14");
  //mySql.addPara('AgentCode',fm.AgentCode.value);
  
  aSql = wrapSql(tResourceName,tResourceSQL6,[fm.AgentCode.value]); 
  
  arrResult = easyExecSql(aSql);
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
	if(fm.TempFeeType.value=='1'){//新单交费
		newPolicy();
	}else if(fm.TempFeeType.value=='2'){//续期催缴
		conPolicy();
	}else if(fm.TempFeeType.value=='3'){//续期预缴
		conBefPolicy();
	}else if(fm.TempFeeType.value=='4'){//保全交费
		edorPolicy();
	}else if(fm.TempFeeType.value=='6'){//理赔交费
		claimPolicy();
	/*}else if(fm.TempFeeType.value=='6'){//不定期交费
		urgePayPolicy ();
	}*/
	clearOpeTypeInfo();
}
}
//新单
function newPolicy(){
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee1.value));
	
	//取得汇总信息
	var count=TempToGrid.mulLineCount; //得到MulLine的行数
	for(var i=0;i<count;i++){
		tempFee=tempFee + 1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	
	//alert("sumTempFee: "+pointTwo(sumTempFee));
	//alert("tempFee: "+pointTwo(tempFee));
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none';
	}
	
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value			);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee1.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value	);		//业务币种信息
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value		); 		//管理机构，managecom是收费机构
	TempToGrid.setRowColData(count,	9,	fm.AgentGroup.value		);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode.value		);		//代理人编码
	TempToGrid.setRowColData(count,	16,	fm.InputNob.value			);		//投保人
	TempToGrid.setRowColData(count,	11,	fm.InputNo1.value	); 				//业务号码
	TempToGrid.setRowColData(count,	12,	'6' ); 											//业务号码类型
	TempToGrid.setRowColData(count,	18	,	fm.TempFeeType.value	);
	
}
//续期催收
function conPolicy(){
	if(!addFlag){
		alert("没有查到应收记录");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee2.value));
	
	//取得汇总信息
	var count=TempToGrid.mulLineCount; //得到MulLine的行数
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    
	
}
//续期预缴
function conBefPolicy(){
	if(!contFlag){
		alert("没有查到保单记录");
		return false ;
	}
	
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee3.value));
	
	//取得汇总信息
	var count=TempToGrid.mulLineCount; //得到MulLine的行数
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
}

//保全
function edorPolicy(){
	
	if(!edorFlag){
		alert("没有查到应收记录");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee4.value));
	
	//取得汇总信息
	var count=TempToGrid.mulLineCount; //得到MulLine的行数
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
	
}

//理赔
function claimPolicy(){
	if(!clmFlag){
		alert("没有查到应收记录");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee5.value));
	
	//取得汇总信息
	var count=TempToGrid.mulLineCount; //得到MulLine的行数
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
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
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
}

//不定期缴费
function urgePayPolicy(){
	
	if(!urgeFlag){
		alert("没有查到保单记录");
		return false ;
	}
	tempFee = 1000000000 * parseFloat(pointTwo(fm.OpeFee6.value));
	
	//取得汇总信息
	var count=TempToGrid.mulLineCount; //得到MulLine的行数
	for(var i=0;i<count;i++){
		tempFee=tempFee+1000000000 * parseFloat(pointTwo(TempToGrid.getRowColData(i,4)));
	}
	tempClassFee=tempClassFee/1000000000;
  tempFee=tempFee/1000000000;
	
	if(pointTwo(tempFee)==pointTwo(sumTempFee)){
		InputFlag = false;
	}else if(tempFee>sumTempFee){
		alert("业务金额合计大于财务收费总和，请重新录入业务费用信息！");
		return false;
	}
	
	fm.OperateSum.value=pointTwo(tempFee);
	fm.OperateSub.value=Math.round((parseFloat(pointTwo(tempFee))-parseFloat(pointTwo(sumTempFee)))*100)/100;
	
	if(!InputFlag){
		fm.addButton.style.display='none'; 
	}
	TempToGrid.addOne("TempToGrid");
	TempToGrid.setRowColData(count,	1	,	fm.InputNo11.value										);
	TempToGrid.setRowColData(count,	3	,	fm.PayDate.value											);
	TempToGrid.setRowColData(count,	4	,	pointTwo(parseFloat(fm.OpeFee6.value)));
	TempToGrid.setRowColData(count,	5	,	fm.OpeCurrency.value									);
	TempToGrid.setRowColData(count,	7	,	fm.PolicyCom.value										); 		//管理机构
	TempToGrid.setRowColData(count,	9 ,	fm.AgentGroup1.value									);		//代理人编码
	TempToGrid.setRowColData(count,	10,	fm.AgentCode1.value										);		//代理人编码
	TempToGrid.setRowColData(count,	11,	fm.InputNo22.value											); 		//业务号码
	TempToGrid.setRowColData(count,	12,	'2' 																	); 		//业务号码类型
	TempToGrid.setRowColData(count,	18,	fm.TempFeeType.value									);    //
	
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
			alert("赔案号不能为空");
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
	
	if(fm.ManageCom.value.substring(0,4)!=fm.PolicyCom.value.substring(0,4)){
		if(!confirm("业务费用为异地缴费，要确定吗？")){
			return false;
		}
	}
	return true;
}

function getAgentCode()
{
	if((document.all('InputNo3').value!=null||document.all('InputNo3').value!="")&&(document.all('AgentCode').value==""||document.all('AgentCode').value==null))
	{
		var strSql = "";
    //mySql.setSqlId("CustomerYFInputSql_20");
    //mySql.addPara('InputNo3' , fm.InputNo3.value);
    
    strSql = wrapSql(tResourceName,tResourceSQL7,[fm.InputNo3.value]); 
    
    // select AgentCode,managecom,agentgroup from ljspay where otherno='?InputNo3?' ;
	  var TarrResult = easyQueryVer3( strSql, 1, 1, 1);
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
    //mySql.setSqlId("CustomerYFInputSql_29");
    //mySql.addPara('InputNo5',fm.InputNo5.value);
    strSql2 = wrapSql(tResourceName,tResourceSQL8,[fm.InputNo5.value]); 
    
	  var arrResult2 = easyExecSql(strSql2);
	  if(arrResult2 == null)
	  {
	  	contFlag=true;
	    var strSql = "";
      //mySql.setSqlId("CustomerYFInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
     // mySql.addPara('InputNo5',fm.InputNo5.value);
     
     strSql = wrapSql(tResourceName,tResourceSQL9,[fm.InputNo5.value]); 
     
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_31"); //select agentcode from LCCont where contno='?InputNo5?'
        //mySql.addPara('InputNo5',fm.InputNo5.value);
        
        ASql = wrapSql(tResourceName,tResourceSQL10,[fm.InputNo5.value]); 
        
        var brrResult = easyExecSql(ASql);
        
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
      //mySql.setSqlId("CustomerYFInputSql_32");
      //mySql.addPara('InputNo5',fm.InputNo5.value);
      
      
      strSql = wrapSql(tResourceName,tResourceSQL11,[fm.InputNo5.value]); 
      
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_33");
        //mySql.addPara('InputNo5',fm.InputNo5.value);
        ASql = wrapSql(tResourceName,tResourceSQL12,[fm.InputNo5.value]); 
        
        var brrResult = easyExecSql(ASql);
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
	}
}

function getEdorCode()
{
  if(document.all('PayDate').value == null && document.all('PayDate').value == "")
  {
  	alert("请录入交费日期");
  	return false;
  }
  
  if((document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")&&(document.all('InputNo8').value==null||document.all('InputNo8').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
   	//mySql.setSqlId("CustomerYFInputSql_21");
   	//mySql.addPara('InputNo7',fm.InputNo7.value);
   	//mySql.addPara('PayDate',document.all('PayDate').value);
   	
   	strSql = wrapSql(tResourceName,tResourceSQL13,[fm.InputNo7.value,document.all('PayDate').value,document.all('PayDate').value]); 
   	
   	var arrResult = easyExecSql(strSql);
   	
   	if(arrResult == null ||arrResult == ""||arrResult=='null')
   	{
   	 alert("没有查询到保全应收记录!!!");
   	 return false;	
   	}
  	else
  	{
  	  fm.InputNo8.value=arrResult;	//交费收据号码
  	}
  }	
  
  if((document.all('InputNo8').value!=null&&document.all('InputNo8').value!="")&&(document.all('InputNo7').value==null||document.all('InputNo7').value==""))
  {
  	edorFlag=true;
   	var strSql = "";
  	//mySql.setSqlId("CustomerYFInputSql_22");
  	//mySql.addPara('InputNo8',fm.InputNo8.value);
  	
  	
  	strSql = wrapSql(tResourceName,tResourceSQL14,[fm.InputNo8.value]); 
  	
  	var arrResult = easyExecSql(strSql);
  	if(arrResult==null||arrResult=="")
  	{
  	 alert("该保全申请号不存在，或已经逾期");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else
  	{
  	 fm.InputNo7.value=arrResult;	//保全受理号
  	}
  }
  
  if(document.all('InputNo8').value!=null&&document.all('InputNo8').value!=""&&document.all('InputNo7').value!=null&&document.all('InputNo7').value!=""){
  	//InputNo7 受理号
  	//InputNo8 收据号
  	edorFlag=true;
   	var strSql = "";
  	//mySql.setSqlId("CustomerYFInputSql_22");
  	//mySql.addPara('InputNo8',fm.InputNo8.value);
  	
  	
  	strSql = wrapSql(tResourceName,tResourceSQL14,[fm.InputNo8.value]); 
  	
  	var arrResult = easyExecSql( strSql);
  	if(arrResult==null||arrResult==""){
  	 alert("该保全申请号不存在，或已经逾期");	
  	 fm.InputNo7.value="";
  	 fm.InputNo8.value="";
  	}
  	else{
  	 fm.InputNo7.value=arrResult;	//保全受理号
  	}
  }
  if (document.all('InputNo7').value!=null&&document.all('InputNo7').value!="")
  {
    var strSql = "";
   // mySql.setSqlId("CustomerYFInputSql_23");
    //mySql.addPara('InputNo8',fm.InputNo8.value);
    //mySql.addPara('PayDate',document.all('PayDate').value);
    
    
    
    strSql = wrapSql(tResourceName,tResourceSQL15,[fm.InputNo8.value,document.all('PayDate').value,document.all('PayDate').value]); 
    
    var arrResult = easyExecSql(strSql);
    
    if(arrResult == null ||arrResult == ""||arrResult=='null'){
      alert("该保全申请号不存在，或已经逾期");
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
    //mySql.setSqlId("CustomerYFInputSql_27");
    //mySql.addPara('InputNo11',fm.InputNo11.value);
    strSql = wrapSql(tResourceName,tResourceSQL16,[fm.InputNo11.value]); 
    
    var arrResult = easyExecSql(strSql);
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
    //mySql.setSqlId("CustomerYFInputSql_28");
    //mySql.addPara('InputNo12',fm.InputNo12.value);
    
     strSql = wrapSql(tResourceName,tResourceSQL17,[fm.InputNo12.value]); 
    var arrResult = easyExecSql(strSql);
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
    //mySql.setSqlId("CustomerYFInputSql_29"); //团单查询
    //mySql.addPara('InputNo5',fm.InputNo22.value);
    
     strSql2 = wrapSql(tResourceName,tResourceSQL18,[fm.InputNo22.value]); 
    
	  var arrResult2 = easyExecSql( strSql2);
	  if(arrResult2 == null)
	  {
	  	urgeFlag=true;
	    var strSql = "";
      //mySql.setSqlId("CustomerYFInputSql_30"); //select managecom from LCCont where contno='?InputNo5?'
      //mySql.addPara('InputNo22',fm.InputNo22.value);
      
      strSql = wrapSql(tResourceName,tResourceSQL19,[fm.InputNo22.value]); 
      
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_31"); //select agentcode from LCCont where contno='?InputNo22?'
        //mySql.addPara('InputNo22',fm.InputNo22.value);
        
        ASql = wrapSql(tResourceName,tResourceSQL20,[fm.InputNo22.value]);  
        
        var brrResult = easyExecSql(ASql);
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
      //mySql.setSqlId("CustomerYFInputSql_32");
      //mySql.addPara('InputNo22',fm.InputNo22.value);
      
      strSql = wrapSql(tResourceName,tResourceSQL21,[fm.InputNo22.value]);  
      
      var arrResult = easyExecSql(strSql);
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
        //mySql.setSqlId("CustomerYFInputSql_33");
        //mySql.addPara('InputNo22',fm.InputNo22.value);
        
        ASql = wrapSql(tResourceName,tResourceSQL22,[fm.InputNo22.value]);  
        var brrResult = easyExecSql(ASql);
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
	}	
}



function queryLJSPay(){
   if(document.all('InputNo3').value!=""){
   }
   else{
    alert("请输入保单号");
   }
}

function clearFormData(){
	//clearOpeTypeInfo();
	fm.TempFeeType.value="";
	fm.TempFeeTypeName.value="";
	TempToGrid.clearData();
	fm.OperateSum.value="";
	fm.OperateSub.value="";
	fm.addButton.style.display='';
	return true;
}

