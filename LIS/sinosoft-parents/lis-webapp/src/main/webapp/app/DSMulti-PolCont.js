var turnPage = new turnPageClass();
var mAction ="";
/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
 
function getInsuredDetail(parm1,parm2)
{	//alert("parm1: "+parm1+"   parm2: "+parm2);
    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;
//    alert("InsuredNo="+InsuredNo);
    var ContNo = fm.ContNo.value;
    emptyInsured();//清除被保人录入区域，重新赋值
    //被保人详细信息
	
		var sqlid1="DSMulti-PolContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(InsuredNo);//指定传入的参数
	    var strSQL =mySql1.getString();	
	
//    var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
	//由于在LDPerson表里未存入客户“身高---”，“体重---”信息，故从LCCustomerImpartParams（客户告知参数表）查询
	 //查询条件：合同号,客户号码，客户类型<1--客户号码为被保人>，告知编码<02--健康告知>，告知版别<000>，
	 
	 	var sqlid2="DSMulti-PolContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(ContNo);//指定传入的参数
		mySql2.addSubPara(InsuredNo);//指定传入的参数
	    var ssql =mySql2.getString();	
	 
//	 var ssql="select tt.impartparamname,tt.impartparam from LCCustomerImpartParams tt where tt.contno='"+ContNo+"' "
//		  +" and tt.customerno='"+InsuredNo+"' and tt.customernotype='1'"
//		  +" and tt.impartver='02' and tt.impartcode='000'";
	 var ssArr=easyExecSql(ssql);
	try
	{
	 arrResult[0][13]=ssArr[0][1];//身高
	 arrResult[0][14]=ssArr[1][1];//体重		
	}catch(ex){};
    if(arrResult!=null)
    {
        displayInsuredInfo();
    }
	
		 var sqlid3="DSMulti-PolContSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(prtNo);//指定传入的参数
		mySql3.addSubPara(InsuredNo);//指定传入的参数
	    strSQL =mySql3.getString();	
	
//    strSQL ="select * from LCInsured where ContNo = '"+prtNo+"' and InsuredNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
    fm.InsuredAddressNo.value=tAddressNo;
    //显示被保人地址信息
    getdetailaddress2();
    getInsuredPolInfo();
    getImpartInfo();
    getAge2();
    showInsuredCodeName();//显示各个项目的汉字信息
    //在录单过程中不需要进行客户合并，故注释掉　hl 20050505
    //如果是复核状态，则进行重复客户校验
    if(LoadFlag=="5" )
    {
      InsuredChkNew();
      return;
    }
}


function displayContInfo(arrResultCont){
	try{
		fm.ContNo.value             = arrResultCont[0][1];
		fm.InputNo.value            = arrResultCont[0][2];
		fm.contFillNo.value         = arrResultCont[0][3];
		fm.ProposalGrpContNo.value  = arrResultCont[0][5];
		fm.ContType.value           = arrResultCont[0][6];
		fm.FamilyID.value           = arrResultCont[0][8];
		fm.AgentCom.value     		= arrResultCont[0][13];
		fm.AgentCode.value          = arrResultCont[0][14];
		fm.SaleChnl.value           = arrResultCont[0][18];
		fm.PayIntv.value            = arrResultCont[0][21];
		fm.PolApplyDate.value       = arrResultCont[0][60];
		fm.FirstTrialOperator.value = arrResultCont[0][70];
		fm.FirstTrialDate.value     = arrResultCont[0][71];
		fm.Handler.value          	= arrResultCont[0][19];
		fm.AgentComName.value 		= arrResultCont[0][89];
		fm.Password.value           = arrResultCont[0][20];
		fm.SumPrem.value            = arrResultCont[0][43];
		fm.ApproveCode.value        = arrResultCont[0][52];
		fm.ApproveTime.value   		= arrResultCont[0][54];
		fm.OutPayFlag.value  		= arrResultCont[0][25];
		fm.PayMode.value     		= arrResultCont[0][22];
		fm.PayLocation.value 		= arrResultCont[0][23];
		fm.BankCode.value 			= arrResultCont[0][31];
		fm.AccName.value     		= arrResultCont[0][33];
		fm.BankAccNo.value   		= arrResultCont[0][32];
		fm.AutoPayFlag.value 		= arrResultCont[0][86];
		fm.UWOperator.value         = arrResultCont[0][56];
		fm.UWDate.value             = arrResultCont[0][57];
		fm.ImpartRemark.value       = arrResultCont[0][95];
	}catch(ex){
		alert("显示合同信息时出错！");
	}
}
//显示险种信息
function displayPol(arrResultPol){
	fm.GetYear.value      = arrResultPol[0][37];
	fm.LiveGetMode.value  = arrResultPol[0][82];
	//fm.BonusGetMode.value = arrResultPol[0][89];
	fm.StandbyFlag3.value = arrResultPol[0][102];
	fm.GetLimit.value     = arrResultPol[0][118];
	
	    var sqlid4="DSMulti-PolContSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(prtNo);//指定传入的参数
		mySql4.addSubPara(InputNo);//指定传入的参数
	    var tSql  =mySql4.getString();	
		
		var sqlid5="DSMulti-PolContSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(prtNo);//指定传入的参数
		mySql5.addSubPara(InputNo);//指定传入的参数
	    var tSql2  =mySql5.getString();	
		
		var sqlid6="DSMulti-PolContSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(prtNo);//指定传入的参数
		mySql6.addSubPara(InputNo);//指定传入的参数
	    var tSql3  =mySql6.getString();	
	
//	var tSql  = "select BonusGetMode from lbpopol where contno='"+prtNo+"' and inputno='"+InputNo+"' and RiskSequence ='1'";
//	var tSql2  = "select BonusGetMode from lbpopol where contno='"+prtNo+"' and inputno='"+InputNo+"' and RiskSequence ='2'";
//	var tSql3  = "select BonusGetMode from lbpopol where contno='"+prtNo+"' and inputno='"+InputNo+"' and RiskSequence ='3'";
	var arrResult;
	var arrResult2;
	var arrResult3;
	arrResult=easyExecSql(tSql);
	arrResult2=easyExecSql(tSql2);
	arrResult3=easyExecSql(tSql3);
	if(arrResult[0][0]!=null){
		fm.BonusGetMode1.value=arrResult[0][0];
	}
	if(arrResult2[0][0]!=null){
		fm.BonusGetMode2.value=arrResult2[0][0];
	}
	if(arrResult3[0][0]!=null){
		fm.BonusGetMode3.value=arrResult3[0][0];
	}
}
//其他需要显示的信息
function displayOther(){
	
		var sqlid7="DSMulti-PolContSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(prtNo);//指定传入的参数
		mySql7.addSubPara(InputNo);//指定传入的参数
	    var tSqlName  =mySql7.getString();	
	
//	var tSqlName = "select AppSignName,InsSignName2 from lbpocont where contno='"+prtNo+"' and inputno='"+InputNo+"'";
	var arrResultName;
	arrResultName=easyExecSql(tSqlName);
	if(arrResultName!=null){
		fm.AppSignName.value = arrResultName[0][0];
		if(arrResultName[0][1]!=null){
			fm.InsSignName2.value = arrResultName[0][1];
		}
	}
	
		var sqlid8="DSMulti-PolContSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(prtNo);//指定传入的参数
	    var tSql  =mySql8.getString();	
	
//	var tSql ="select managecom from es_doc_main where doccode ='"+prtNo+"'";
	var tManageCom = easyExecSql(tSql);
	if(tManageCom==null||tManageCom=="null"){
	fm.ManageCom.value = "";
	fm.ScanCom.value   = "";
	}else{
	fm.ManageCom.value = tManageCom[0][0];
	fm.ScanCom.value   = tManageCom[0][0];
	}
}
//显示投保人信息
function displayAppnt(arrResultAppnt){
	fm.AppntNo.value           = arrResultAppnt[0][5];
	fm.AppntFillNo.value       = arrResultAppnt[0][3];
	fm.RelationToInsured.value = arrResultAppnt[0][45];
	fm.AppntName.value         = arrResultAppnt[0][7];
	fm.AppntSex.value          = arrResultAppnt[0][8];
	fm.AppntBirthday.value     = arrResultAppnt[0][9];
	fm.AppntIDType.value       = arrResultAppnt[0][12];
	fm.AppntIDNo.value         = arrResultAppnt[0][13];
	fm.AppntNativePlace.value  = arrResultAppnt[0][14];
	fm.AppntRgtAddress.value   = arrResultAppnt[0][16];
	fm.AppntMarriage.value     = arrResultAppnt[0][17];
	fm.AppntOccupationCode.value=arrResultAppnt[0][32];
	fm.AppntWorkType.value     = arrResultAppnt[0][33];
	fm.AppntPluralityType.value= arrResultAppnt[0][34];
	fm.AppntPostalAddress.value= arrResultAppnt[0][59];
	fm.AppntZipCode.value      = arrResultAppnt[0][58];
	fm.AppntHomeAddress.value  = arrResultAppnt[0][55];
	fm.AppntHomeZipCode.value  = arrResultAppnt[0][54];
	fm.AppntCompanyPhone.value = arrResultAppnt[0][49];
	fm.AppntMobile.value       = arrResultAppnt[0][47];
	fm.AppntEMail.value        = arrResultAppnt[0][46];
	fm.AppntCompanyAddress.value= arrResultAppnt[0][51];
}
//显示被保人信息
function displayInsured(arrResultInsured){
	fm.InputNo.value         = arrResultInsured[0][2];
	fm.InsuredNo.value       = arrResultInsured[0][4];
	fm.InsuredFillNo.value   = arrResultInsured[0][3]
	//fm.RelationToAppnt.value = arrResultInsured[0][11];
	fm.InsuredAddressNo.value= arrResultInsured[0][12];
	fm.SequenceNo.value      = arrResultInsured[0][13];
	fm.Name.value            = arrResultInsured[0][14];
	fm.Sex.value             = arrResultInsured[0][15];
	fm.Birthday.value        = arrResultInsured[0][16];
	fm.IDType.value          = arrResultInsured[0][17];
	fm.IDNo.value            = arrResultInsured[0][18];
	fm.NativePlace.value     = arrResultInsured[0][19];
	fm.RgtAddress.value      = arrResultInsured[0][21];
	fm.Marriage.value        = arrResultInsured[0][22];
	fm.OccupationCode.value  = arrResultInsured[0][37];
	fm.WorkType.value        = arrResultInsured[0][38];
	fm.PluralityType.value   = arrResultInsured[0][39];
	fm.PostalAddress.value   = arrResultInsured[0][72];
	fm.ZipCode.value		 = arrResultInsured[0][71];
	fm.HomeAddress.value	 = arrResultInsured[0][68];
	fm.HomeZipCode.value	 = arrResultInsured[0][67];
	fm.CompanyPhone.value	 = arrResultInsured[0][62];
	fm.Mobile.value			 = arrResultInsured[0][60];
	fm.EMail.value			 = arrResultInsured[0][59];
	fm.CompanyAddress.value	 = arrResultInsured[0][64];
}
//投保人年龄
function getAge()
{
	if(fm.PolApplyDate.value=="")
	{
		return;
	}
	if(fm.PolApplyDate.value.indexOf('-')==-1)
	{
		var Year =fm.PolApplyDate.value.substring(0,4);
		var Month =fm.PolApplyDate.value.substring(4,6);
		var Day =fm.PolApplyDate.value.substring(6,8);
		fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.PolApplyDate.value)<0)
	{}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolApplyDate.value);
  	return ;
}
//被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修改>
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
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}

//合同、投、被保人信息，保存
function saveInsured(wFlag){
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//如果是三码录入，需先校验是否所有错误内容都已经确认
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="INSERT";
	document.all( 'fmAction' ).value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //提交
}

//合同、投、被保人，修改
function updateInsured(wFlag){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

	//如果是三码录入，需先校验是否所有错误内容都已经确认
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	document.all( 'fmAction' ).value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //提交
}
//险种信息，修改
function updateRisk(wFlag){
	saveRisk(wFlag);
}
//险种信息，保存
function saveRisk(wFlag){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//如果是三码录入，需先校验是否所有错误内容都已经确认
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	document.all( 'fmAction' ).value = mAction;
	fm.action = "DSProposalSave.jsp";
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //提交
}
//生存保险金、年金、红利，修改
function updateGet(wFlag){
	saveGet(wFlag);
}
//生存保险金、年金、红利，保存
function saveGet(wFlag){
	fm.ContType.value="2";
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//如果是三码录入，需先校验是否所有错误内容都已经确认
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	fm.action="DSGetSave.jsp";
	document.all( 'fmAction' ).value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	
	fm.submit(); //提交
}

function saveImpart(wFlag){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//如果是三码录入，需先校验是否所有错误内容都已经确认
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	//disabled(false);
	mAction="INSERT";
	fm.fmAction.value=mAction;
	fm.action="DSImpartSave.jsp";
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //提交
	//disabled(true);
}
function updateImpart(wFlag){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	if(InputTime=="2"){
		if(!beforeSubmit(wFlag))
		{
		  return false;
		}
	}
	mAction="UPDATE";
	fm.fmAction.value=mAction;
	fm.action="DSImpartSave.jsp";
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //提交
}
//显示告知信息
function displayImpart(){
	if(InputTime=="2"){
		
		var sqlid9="DSMulti-PolContSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(prtNo);//指定传入的参数
	    var ImpartSql  =mySql9.getString();	
		
//		var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer<>'A03' and InputNo='3' order by impartver,ImpartCode";
		var arrResult;
		arrResult=easyExecSql(ImpartSql);
		if(arrResult!=null)
		{  
			turnPage.queryModal(ImpartSql, ImpartGrid,null,null,33);
		}else{
			//var ImpartSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = '101' order by ImpartCode";
			//turnPage.queryModal(ImpartSql2, ImpartGrid,null,null,27);
		}
		
	    var sqlid10="DSMulti-PolContSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(prtNo);//指定传入的参数
	    var AgentSql  =mySql10.getString();	
		
//		var AgentSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer='A03' and InputNo='3' order by impartver,impartcode";
		var arrResult2;
		arrResult2=easyExecSql(AgentSql);
		if(arrResult2!=null){
			turnPage.queryModal(AgentSql, AgentImpartGrid);
		}else{
			
		var sqlid11="DSMulti-PolContSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
//		mySql11.addSubPara(prtNo);//指定传入的参数
	    var AgentSql2  =mySql11.getString();	
			
//			var AgentSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = 'A03' order by ImpartCode";
			turnPage.queryModal(AgentSql2,AgentImpartGrid);
		}
	}else{
		
		var sqlid12="DSMulti-PolContSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(prtNo);//指定传入的参数
		mySql12.addSubPara(InputNo);//指定传入的参数
	    var ImpartSql  =mySql12.getString();	
		
//		var ImpartSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillNo from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer<>'A03' and InputNo='"+InputNo+"' order by impartver,impartcode";
		var arrResult;
		arrResult=easyExecSql(ImpartSql);
		if(arrResult!=null)
		{  
			turnPage.queryModal(ImpartSql, ImpartGrid,null,null,33);
		}else{
			//var ImpartSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = '101' order by ImpartCode";
			//turnPage.queryModal(ImpartSql2, ImpartGrid,null,null,27);
		}
		
		var sqlid13="DSMulti-PolContSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(prtNo);//指定传入的参数
		mySql13.addSubPara(InputNo);//指定传入的参数
	    var AgentSql  =mySql13.getString();	
		
//		var AgentSql="select impartcode,impartver,impartcontent,impartparammodle,PrtFlag,Insured1,Insured2,insured3,fillno from LBPOCustomerImpart where contno='"+prtNo+"' and ImpartVer='A03' and InputNo='"+InputNo+"' order by impartver,impartcode";
		var arrResult2;
		arrResult2=easyExecSql(AgentSql);
		if(arrResult2!=null){
			turnPage.queryModal(AgentSql, AgentImpartGrid);
		}else{
			//var AgentSql2="SELECT ImpartVer,ImpartCode, ImpartContent FROM LDImpart WHERE ImpartVer = 'A03' order by ImpartCode";
			//turnPage.queryModal(AgentSql2,AgentImpartGrid);
		}
	}
	//有些告知无须填写是否 暂时做如下处理
	//disabled(true);
}
//有些告知无须填写是否 暂时做如下处理
function disabled(tFlag){
   try{
	var tempOrder="document.all('ImpartGrid5')[0].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid6')[0].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid5')[31].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid6')[31].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid5')[32].disabled="+tFlag+";";
	  			eval(tempOrder);
	var tempOrder="document.all('ImpartGrid6')[32].disabled="+tFlag+";";
	  			eval(tempOrder);
	  			}catch(ex){}
}
//显示合同信息
function displayCont(){
	fm.PrtNo.value = prtNo;
	fm.ProposalContNo.value = prtNo;
	fm.MissionID.value=tMissionID;
	fm.SubMissionID.value=tSubMissionID;
	fm.ContNo.value=prtNo;
	fm.InputNo.value=InputNo;
	fm.InputTime.value=InputTime;//alert(InputTime);
	//如果InputTime的值是2，表示三码录入,须将除button之外的INPUT都置为disabled
	if(InputTime=="2"){
		
		var sqlid14="DSMulti-PolContSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(prtNo);//指定传入的参数
	    var tContSql2  =mySql14.getString();	
		
//  		var tContSql2="select * from lbpocont where contno='"+prtNo+"' and InputNo='3'";
  		var arrResultCont2;
		arrResultCont2=easyExecSql(tContSql2);
		displayContInfo(arrResultCont2);
		
		var sqlid15="DSMulti-PolContSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		mySql15.addSubPara(prtNo);//指定传入的参数
	    var tAppntSql2  =mySql15.getString();	
		
//		var tAppntSql2 = "select * from lbpoappnt where contno='"+prtNo+"' and InputNo='3'";
		var arrResultAppnt2;
		arrResultAppnt2=easyExecSql(tAppntSql2);
		displayAppnt(arrResultAppnt2);
		
		var sqlid16="DSMulti-PolContSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(prtNo);//指定传入的参数
	    var tInsuredSql2  =mySql16.getString();	
		
//		var tInsuredSql2 = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='1' and InputNo='3'";
		var arrResultInsured2;
		arrResultInsured2=easyExecSql(tInsuredSql2);
		displayInsured(arrResultInsured2);
		
		var sqlid17="DSMulti-PolContSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(prtNo);//指定传入的参数
		mySql17.addSubPara(prtNo);//指定传入的参数
	    var tBnfSql2  =mySql17.getString();	
		
//		var tBnfSql2 = "select 1,b.BnfType,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress,b.FillNo,b.insuredno,(select distinct(lbpopol.mainpolno) from lbpopol where contno='"+prtNo+"' and lbpopol.risksequence=b.fillno and lbpopol.inputno=b.inputno) from lbpobnf b where b.contno='"+prtNo+"' and b.inputno='3'"   
		var arrResultBnf2;
		arrResultBnf2=easyExecSql(tBnfSql2);
		turnPage.queryModal(tBnfSql2,BnfGrid);
		
		var sqlid18="DSMulti-PolContSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(prtNo);//指定传入的参数
	    var tRiskSql2  =mySql18.getString();	
		
//		var tRiskSql2="select b.risksequence,(select lbpoinsured.sequenceno from lbpoinsured where lbpoinsured.insuredno = b.insuredno and inputno='3'),b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.polno,b.fillno,b.insuredno,b.mainpolno from lbpopol b where b.contno='"+prtNo+"' and b.inputno='3' order by risksequence,fillno"
		var arrResultRisk2;
		arrResultRisk2=easyExecSql(tRiskSql2);
		turnPage.queryModal(tRiskSql2,PolGrid);
		var tPolSql2="select * from lbpopol where contno='"+prtNo+"' and inputno='3'";
		var arrResultPol2;
		arrResultPol2=easyExecSql(tPolSql2);
		displayPol(arrResultPol2);
		displayOther();
		//--------------------------------------------------------------------------------//
		//*************除multline外，将所有的element置灰************************//
		//遍历所有的input，并将其置灰
		for(i=0;i<document.fm.elements.length;i++){
  			if(document.fm.elements[i].tagName=="INPUT" 
  			//&& (document.fm.elements[i].name.indexOf('Grid')==-1 || 
  			//document.fm.elements[i].name ==null ||document.fm.elements[i].name=='')
  			&&document.fm.elements[i].className.indexOf('muline')==-1
  			//&&document.fm.elements[i].name.indexOf('Grid')==-1 
  			){
  			//alert(document.fm.elements[i].name);
  			
  				document.fm.elements[i].disabled=true;
  				if(document.fm.elements[i].type=="button"){
  					document.fm.elements[i].disabled=false;
  				}
  			}
  		}
  		fm.InsuredSequencename.disabled=false;
  		//查询错误信息表，将校验出错的字段置空并领其可修改
		
		var sqlid19="DSMulti-PolContSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(prtNo);//指定传入的参数
	    var tErrorCont  =mySql19.getString();	
		
//  		var tErrorCont = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOCont' group by errortag";
  		var arrResultErrorCont;//prompt("",tErrorSql);
  		arrResultErrorCont=easyExecSql(tErrorCont);
  		if(arrResultErrorCont!=null){
  			for(var k=0;k<arrResultErrorCont.length;k++){
  				//alert(arrResultErrorCont[k][0]);
  				try{
	  				var ele="";
	  				ele=arrResultErrorCont[k][0];
	  				//将所有需进行确认的字段置成可以修改
	  				document.all(""+ele+"").disabled=false;
	  				//拼字符串，将需进行确认的字段高亮显示
	  				var warn="fm."+ele+".className=\"warn\";"
	  				//将需进行确认的字段置空
	  				//document.all(""+ele+"").value="";
	  				eval(warn);//alert(warn);
	  				var tObj = document.all(""+ele+"");
	  				//添加事件 此处是为了达到鼠标放到某需校验的字段上时可以显示第一次和第二次的录入结果
					var tTitle = "";
					
		var sqlid20="DSMulti-PolContSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(prtNo);//指定传入的参数
		mySql20.addSubPara(arrResultErrorCont[k][0]);//指定传入的参数
	    var tSQL  =mySql20.getString();	
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorCont[k][0]+"' and otherno='1' and tablename='LBPOCont' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);
     				if(arrResultTitle!=null){
	     				for(ti=0;ti<arrResultTitle.length;ti++)
	     				{
	     					tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
	     					if(ti==0)
	     					{
	     						tTitle = tTitle + "    ";
	     					}
	     				}
	     				tObj.setAttribute('title',tTitle);
     				}
  				}catch(ex){
  					alert("初始化界面出错！");
  				}
  			}
  		}
  		//处理投保人信息
		
		var sqlid21="DSMulti-PolContSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(prtNo);//指定传入的参数
	    var tErrorAppnt  =mySql21.getString();	
		
//  		var tErrorAppnt = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOAppnt' group by errortag";
  		var arrResultErrorAppnt;//prompt("",tErrorSql);
  		arrResultErrorAppnt=easyExecSql(tErrorAppnt);
  		if(arrResultErrorAppnt!=null){
  			for(k=0;k<arrResultErrorAppnt.length;k++){
  				//alert(arrResultErrorAppnt[k][0]);
  				try{
	  				var ele="";
	  				if(arrResultErrorAppnt[k][0]=="CompanyAddress"||arrResultErrorAppnt[k][0]=="CompanyPhone"
	  				   ||arrResultErrorAppnt[k][0]=="EMail"||arrResultErrorAppnt[k][0]=="HomeAddress"
	  				   ||arrResultErrorAppnt[k][0]=="HomeZipCode"||arrResultErrorAppnt[k][0]=="IDNo"
	  				   ||arrResultErrorAppnt[k][0]=="IDType"||arrResultErrorAppnt[k][0]=="Marriage"
	  				   ||arrResultErrorAppnt[k][0]=="Mobile"||arrResultErrorAppnt[k][0]=="Name"
	  				   ||arrResultErrorAppnt[k][0]=="NativePlace"||arrResultErrorAppnt[k][0]=="PluralityType"
	  				   ||arrResultErrorAppnt[k][0]=="RgtAddress"||arrResultErrorAppnt[k][0]=="Sex"
	  				   ||arrResultErrorAppnt[k][0]=="WorkType"||arrResultErrorAppnt[k][0]=="OccupationCode"
	  				   ||arrResultErrorAppnt[k][0]=="Birthday"||arrResultErrorAppnt[k][0]=="PostalAddress"
	  				   ||arrResultErrorAppnt[k][0]=="ZipCode"){
	  				   	ele="Appnt"+arrResultErrorAppnt[k][0];
	  				   }else{
	  				   	ele=arrResultErrorAppnt[k][0];
	  				   }
	  				//将所有需进行确认的字段置成可以修改
	  				document.all(""+ele+"").disabled=false;
	  				//拼字符串，将需进行确认的字段高亮显示
	  				var warn="fm."+ele+".className=\"warn\";"
	  				//将需进行确认的字段置空
	  				//document.all(""+ele+"").value="";
	  				eval(warn);//alert(warn);
	  				var tObj = document.all(""+ele+"");
	  				//添加事件 此处是为了达到鼠标放到某需校验的字段上时可以显示第一次和第二次的录入结果
						var tTitle = "";
						
		var sqlid22="DSMulti-PolContSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(prtNo);//指定传入的参数
		mySql22.addSubPara(arrResultErrorAppnt[k][0]);//指定传入的参数
	    var tSQL  =mySql22.getString();	
						
//						var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorAppnt[k][0]+"' and otherno='1' and tablename='LBPOAppnt' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);
     				if(arrResultTitle!=null){
	     				for(ti=0;ti<arrResultTitle.length;ti++)
	     				{
	     					tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
	     					if(ti==0)
	     					{
	     						tTitle = tTitle + "    ";
	     					}
	     				}
	     				tObj.setAttribute('title',tTitle);
	  				}
  				}catch(ex){
  					alert("初始化界面出错！");
  				}
  			}
  		}
  		//处理被保人信息
		
		var sqlid23="DSMulti-PolContSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
		mySql23.addSubPara(prtNo);//指定传入的参数
	    var tErrorInsured  =mySql23.getString();	
		
//  		var tErrorInsured = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOInsured' group by errortag";
  		var arrResultErrorInsured;//prompt("",tErrorSql);
  		arrResultErrorInsured=easyExecSql(tErrorInsured);
  		if(arrResultErrorInsured!=null){
  			for(k=0;k<arrResultErrorInsured.length;k++){
  				//alert(arrResultErrorInsured[k][0]);
  				try{
	  				var ele="";
	  				ele=arrResultErrorInsured[k][0];
	  				//将所有需进行确认的字段置成可以修改
	  				document.all(""+ele+"").disabled=false;
	  				//拼字符串，将需进行确认的字段高亮显示
	  				var warn="fm."+ele+".className=\"warn\";"
	  				//将需进行确认的字段置空
	  				//document.all(""+ele+"").value="";
	  				eval(warn);//alert(warn);
	  				var tObj = document.all(""+ele+"");
	  				//添加事件 此处是为了达到鼠标放到某需校验的字段上时可以显示第一次和第二次的录入结果
						var tTitle = "";
						
		var sqlid24="DSMulti-PolContSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql24.setSqlId(sqlid24);//指定使用的Sql的id
		mySql24.addSubPara(prtNo);//指定传入的参数
		mySql24.addSubPara(arrResultErrorInsured[k][0]);//指定传入的参数
	    var tSQL  =mySql24.getString();	
						
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorInsured[k][0]+"' and otherno='1' and tablename='LBPOInsured' order by errorcount ";
     				var arrResultTitle = easyExecSql(tSQL);
     				if(arrResultTitle!=null){
	     				for(ti=0;ti<arrResultTitle.length;ti++)
	     				{
	     					tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
	     					if(ti==0)
	     					{
	     						tTitle = tTitle + "    ";
	     					}
	     				}
	     				tObj.setAttribute('title',tTitle);
	  				}
  				}catch(ex){
  					alert("初始化界面出错！");
  				}
  			}
  		}
		
	    var sqlid25="DSMulti-PolContSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql25.setSqlId(sqlid25);//指定使用的Sql的id
		mySql25.addSubPara(prtNo);//指定传入的参数
	    var tErrorPol  =mySql25.getString();	
		
//  		var tErrorPol = "select errortag from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOPol' and otherno='1' group by errortag";
  		var arrResultErrorPol;//prompt("",tErrorSql);
  		arrResultErrorPol=easyExecSql(tErrorPol);
  		if(arrResultErrorPol!=null){
  			for(k=0;k<arrResultErrorPol.length;k++){
  				if(arrResultErrorPol[k][0]=="LiveGetMode"){
  					fm.LiveGetMode.className="warn";
  					fm.LiveGetMode.disabled=false;
  					var tObj = document.all("LiveGetMode");
  					var tTitle = "";
					
		 var sqlid26="DSMulti-PolContSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql26.setSqlId(sqlid26);//指定使用的Sql的id
		mySql26.addSubPara(prtNo);//指定传入的参数
		mySql26.addSubPara(arrResultErrorPol[k][0]);//指定传入的参数
	    var tSQL  =mySql26.getString();	
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="GetYear"){
  					fm.GetYear.className="warn";
  					fm.GetYear.disabled=false;
  					var tObj = document.all("GetYear");
  					var tTitle = "";
					
		 var sqlid27="DSMulti-PolContSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql27.setSqlId(sqlid27);//指定使用的Sql的id
		mySql27.addSubPara(prtNo);//指定传入的参数
		mySql27.addSubPara(arrResultErrorPol[k][0]);//指定传入的参数
	    var tSQL  =mySql27.getString();	
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="GetLimit"){
  					fm.GetLimit.className="warn";
  					fm.GetLimit.disabled=false;
  					var tObj = document.all("GetLimit");
  					var tTitle = "";
					
		var sqlid28="DSMulti-PolContSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql28.setSqlId(sqlid28);//指定使用的Sql的id
		mySql28.addSubPara(prtNo);//指定传入的参数
		mySql28.addSubPara(arrResultErrorPol[k][0]);//指定传入的参数
	    var tSQL  =mySql28.getString();	
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  				if(arrResultErrorPol[k][0]=="StandbyFlag3"){
  					fm.StandbyFlag3.className="warn";
  					fm.StandbyFlag3.disabled=false;
  					var tObj = document.all("StandbyFlag3");
  					var tTitle = "";
					
		var sqlid29="DSMulti-PolContSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql29.setSqlId(sqlid29);//指定使用的Sql的id
		mySql29.addSubPara(prtNo);//指定传入的参数
		mySql29.addSubPara(arrResultErrorPol[k][0]);//指定传入的参数
	    var tSQL  =mySql29.getString();	
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+arrResultErrorPol[k][0]+"' and otherno='1' and tablename='LBPOPol' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);
	     			if(arrResultTitle!=null){
		     			for(ti=0;ti<arrResultTitle.length;ti++){
		     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
		     				if(ti==0){
		     					tTitle = tTitle + "    ";
		     				}
		     			}
		     			tObj.setAttribute('title',tTitle);
	     			}
  				}
  			}
  		}
  		/**
  		   MultLine需要特殊处理
  		*/
  		/**受益人*/
		
		var sqlid30="DSMulti-PolContSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql30.setSqlId(sqlid30);//指定使用的Sql的id
		mySql30.addSubPara(prtNo);//指定传入的参数
		mySql30.addSubPara(arrResultErrorPol[k][0]);//指定传入的参数
	    var tBnfGridSql  =mySql30.getString();	
		
//  		var tBnfGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOBnf' and bussno='"+prtNo+"' and bussnotype='TB' ";
  		var arrResultCBnfGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCBnfGrid=easyExecSql(tBnfGridSql);
  		if(arrResultCBnfGrid!=null){
	  		for(a=0;a<arrResultCBnfGrid.length;a++){
	  			var tFillNo=arrResultCBnfGrid[a][0];
	  			var tFieldName=arrResultCBnfGrid[a][1];
	  			var tLineCount=0;
	  			tLineCount=getLineCount("BnfGrid",tFieldName);
	  			var tTitle = "";
				
		var sqlid31="DSMulti-PolContSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid31);//指定使用的Sql的id
		mySql31.addSubPara(prtNo);//指定传入的参数
		mySql31.addSubPara(tFieldName);//指定传入的参数
		mySql31.addSubPara(tFillNo);//指定传入的参数
	    var tSQL  =mySql31.getString();	
				
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOBnf' and otherno='"+tFillNo+"' order by errorcount ";
     			var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			BnfGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
	  			BnfGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
	  			var tempOrder="document.all('BnfGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}
  		/**险种*/
		
		var sqlid32="DSMulti-PolContSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql32.setSqlId(sqlid32);//指定使用的Sql的id
		mySql32.addSubPara(prtNo);//指定传入的参数
	    var tPolGridSql  =mySql32.getString();	
		
//  		var tPolGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOPol' and bussno='"+prtNo+"' ";
  		var arrResultCPolGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCPolGrid=easyExecSql(tPolGridSql);
  		if(arrResultCPolGrid!=null){
	  		for(a=0;a<arrResultCPolGrid.length;a++){
	  			var tFillNo=arrResultCPolGrid[a][0];
	  			var tFieldName=arrResultCPolGrid[a][1];
	  			if(tFieldName=="RiskCode"||tFieldName=="Amnt"||tFieldName=="Mult"||tFieldName=="InsuYear"
	  				||tFieldName=="PayYears"||tFieldName=="StandbyFlag2"||tFieldName=="StandbyFlag1"
	  				||tFieldName=="Remark"||tFieldName=="Prem"){
		  			var tLineCount=0;//alert(tFieldName);
		  			tLineCount=getLineCount("PolGrid",tFieldName);
		  			//alert("tFillNo: "+tFillNo);
		  			//alert("tLineCount: "+tLineCount);
		  			//if(tFieldName=="Name"){
		  			//	tLineCount=3;
		  			//}
		  			var tTitle = "";
					
							var sqlid33="DSMulti-PolContSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql33.setSqlId(sqlid33);//指定使用的Sql的id
		mySql33.addSubPara(prtNo);//指定传入的参数
		mySql33.addSubPara(tFieldName);//指定传入的参数
		mySql33.addSubPara(tFillNo);//指定传入的参数
	    var tSQL  =mySql33.getString();	
					
//					var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOPol' and otherno='"+tFillNo+"' order by errorcount ";
	     			var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
	     			for(ti=0;ti<arrResultTitle.length;ti++)
	     			{
	     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
	     				if(ti==0)
		     			{
		     				tTitle = tTitle + "    ";
		     			}
	     			}
	     			//alert(tTitle);
		  			//alert("tFillNo: "+tFillNo);
		  			//alert("tLineCount: "+tLineCount);
		  			//if(tFieldName=="Name"){
		  			//	tLineCount=3;
		  			//}
		  			PolGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
		  			PolGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
		  			var tempOrder="document.all('PolGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
		  			//alert("tempOrder"+tempOrder);
		  			eval(tempOrder);
	  			}
	  		}
  		}
  		//健康告知
		
		var sqlid34="DSMulti-PolContSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql34.setSqlId(sqlid34);//指定使用的Sql的id
		mySql34.addSubPara(prtNo);//指定传入的参数
//		mySql34.addSubPara(tFieldName);//指定传入的参数
//		mySql34.addSubPara(tFillNo);//指定传入的参数
	    var tCusImpartGridSql  =mySql34.getString();	
		
//  		var tCusImpartGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOCustomerImpart' and bussno='"+prtNo+"' and otherno>0 and otherno<9 ";
  		var arrResultCusImpartGrid;
  		//prompt("",tBnfGridSql);
  		arrResultCusImpartGrid=easyExecSql(tCusImpartGridSql);
  		if(arrResultCusImpartGrid!=null){
	  		for(a=0;a<arrResultCusImpartGrid.length;a++){
	  			var tFillNo=arrResultCusImpartGrid[a][0];
	  			var tFieldName=arrResultCusImpartGrid[a][1];
	  			var tLineCount=0;//alert(tFieldName);
	  			tLineCount=getLineCount("ImpartGrid",tFieldName);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			var tTitle = "";
				
		var sqlid35="DSMulti-PolContSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql35.setSqlId(sqlid35);//指定使用的Sql的id
		mySql35.addSubPara(prtNo);//指定传入的参数
		mySql35.addSubPara(tFieldName);//指定传入的参数
		mySql35.addSubPara(tFillNo);//指定传入的参数
	    var tSQL  =mySql35.getString();	
				
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOCustomerImpart' and otherno='"+tFillNo+"' order by errorcount ";
     			var arrResultTitle = easyExecSql(tSQL);//prompt("",tSQL);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
     			//alert(tTitle);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			ImpartGrid.setRowColTitle((tFillNo-1),tLineCount,tTitle);
	  			ImpartGrid.setRowColClass((tFillNo-1),tLineCount,'warn');
	  			var tempOrder="document.all('ImpartGrid"+tLineCount+"')["+(tFillNo-1)+"].disabled=false;";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}
  		//业务员告知
		
		var sqlid36="DSMulti-PolContSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql36.setSqlId(sqlid36);//指定使用的Sql的id
		mySql36.addSubPara(prtNo);//指定传入的参数
	    var tAgeImpartGridSql  =mySql36.getString();	
		
//  		var tAgeImpartGridSql="select otherno,errortag from lbpodatadetailerror where tablename='LBPOCustomerImpart' and bussno='"+prtNo+"' and otherno>8";
  		var arrResultAgeImpartGrid;
  		//prompt("",tAgeImpartGridSql);
  		arrResultAgeImpartGrid=easyExecSql(tAgeImpartGridSql);
  		if(arrResultAgeImpartGrid!=null){//alert(arrResultAgeImpartGrid.length);
	  		for(a=0;a<arrResultAgeImpartGrid.length;a++){
	  			var tFillNo=arrResultAgeImpartGrid[a][0];
	  			var tFieldName=arrResultAgeImpartGrid[a][1];
	  			var tLineCount=0;//alert(tFieldName);
	  			tLineCount=getLineCount("ImpartGrid",tFieldName);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			var tTitle = "";
				
		var sqlid37="DSMulti-PolContSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql37.setSqlId(sqlid37);//指定使用的Sql的id
		mySql37.addSubPara(prtNo);//指定传入的参数
	    var tSQL  =mySql37.getString();	
				
//				var tSQL = "select errorcount,content from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and errortag='"+tFieldName+"' and tablename='LBPOCustomerImpart' and otherno='"+tFillNo+"' order by errorcount ";
     			var arrResultTitle = easyExecSql(tSQL);/**prompt("",tSQL);*/ //alert("arrResultTitle.length:"+arrResultTitle.length);
     			for(ti=0;ti<arrResultTitle.length;ti++)
     			{
     				tTitle = tTitle + "第"+arrResultTitle[ti][0]+"次录入结果:"+arrResultTitle[ti][1];
     				if(ti==0)
	     			{
	     				tTitle = tTitle + "    ";
	     			}
     			}
     			//alert(tTitle);
	  			//alert("tFillNo: "+tFillNo);
	  			//alert("tLineCount: "+tLineCount);
	  			//if(tFieldName=="Name"){
	  			//	tLineCount=3;
	  			//}
	  			AgentImpartGrid.setRowColTitle((tFillNo-8-1),tLineCount,tTitle);
	  			AgentImpartGrid.setRowColClass((tFillNo-8-1),tLineCount,'warn');
	  			var tempOrder="document.all('AgentImpartGrid"+tLineCount+"')["+(tFillNo-8-1)+"].disabled=false;";
	  			//alert("tempOrder"+tempOrder);
	  			eval(tempOrder);
	  		}
  		}
  		fm.BonusGetMode1.disabled='';
  		fm.BonusGetMode2.disabled='';
  		fm.BonusGetMode3.disabled='';
  		//-------------------------------------------------------------------------------//
	}else{
		
		var sqlid38="DSMulti-PolContSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql38.setSqlId(sqlid38);//指定使用的Sql的id
		mySql38.addSubPara(prtNo);//指定传入的参数
		mySql38.addSubPara(InputNo);//指定传入的参数
	    var tContSql  =mySql38.getString();	
		
//		var tContSql="select * from lbpocont where contno='"+prtNo+"' and InputNo='"+InputNo+"'";
		var arrResultCont;
		arrResultCont=easyExecSql(tContSql);
		if(arrResultCont!=null){
			displayContInfo(arrResultCont);
		}
		
		var sqlid39="DSMulti-PolContSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql39.setSqlId(sqlid39);//指定使用的Sql的id
		mySql39.addSubPara(prtNo);//指定传入的参数
		mySql39.addSubPara(InputNo);//指定传入的参数
	    var tAppntSql  =mySql39.getString();	
		
//		var tAppntSql = "select * from lbpoappnt where contno='"+prtNo+"' and InputNo='"+InputNo+"'";
		var arrResultAppnt;
		arrResultAppnt=easyExecSql(tAppntSql);
		if(arrResultAppnt!=null){
			displayAppnt(arrResultAppnt);
		}
		
		var sqlid40="DSMulti-PolContSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql40.setSqlId(sqlid40);//指定使用的Sql的id
		mySql40.addSubPara(prtNo);//指定传入的参数
		mySql40.addSubPara(InputNo);//指定传入的参数
	    var tInsuredSql  =mySql40.getString();	
		
//		var tInsuredSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='1' and InputNo='"+InputNo+"'";
		var arrResultInsured;
		arrResultInsured=easyExecSql(tInsuredSql);
		if(arrResultInsured!=null){
			displayInsured(arrResultInsured);
		}
		//var tBnfSql = "select a.sequenceno,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress from lbpoinsured a,lbpobnf b where a.contno=b.contno and a.inputno=b.inputno and a.contno='"+prtNo+"' and a.inputno='"+InputNo+"'";
		
		var sqlid41="DSMulti-PolContSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql41.setSqlId(sqlid41);//指定使用的Sql的id
		mySql41.addSubPara(prtNo);//指定传入的参数
		mySql41.addSubPara(prtNo);//指定传入的参数
		mySql41.addSubPara(InputNo);//指定传入的参数
	    var tBnfSql  =mySql41.getString();	
		
//		var tBnfSql = "select 1,b.BnfType,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress,b.FillNo,b.insuredno,(select distinct(lbpopol.mainpolno) from lbpopol where contno='"+prtNo+"' and lbpopol.risksequence=b.fillno and lbpopol.inputno=b.inputno) from lbpobnf b where b.contno='"+prtNo+"' and b.inputno='"+InputNo+"'"   
		var arrResultBnf;//prompt("",tBnfSql);
		arrResultBnf=easyExecSql(tBnfSql);
		if(arrResultBnf!=null){
			turnPage.queryModal(tBnfSql,BnfGrid);
		}
		
			var sqlid42="DSMulti-PolContSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql42.setSqlId(sqlid42);//指定使用的Sql的id
		mySql42.addSubPara(InputNo);//指定传入的参数
		mySql42.addSubPara(prtNo);//指定传入的参数
		mySql42.addSubPara(InputNo);//指定传入的参数
	    var tRiskSql  =mySql42.getString();	
		
//		var tRiskSql="select b.risksequence,(select lbpoinsured.sequenceno from lbpoinsured where lbpoinsured.insuredno = b.insuredno and inputno='"+InputNo+"'),b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.polno,b.fillno,b.insuredno,b.mainpolno from lbpopol b where b.contno='"+prtNo+"' and b.inputno='"+InputNo+"' order by risksequence,fillno"
		var arrResultRisk;//prompt("",tRiskSql);
		arrResultRisk=easyExecSql(tRiskSql);
		if(arrResultRisk!=null){
			turnPage.queryModal(tRiskSql,PolGrid);
		}
		
		var sqlid43="DSMulti-PolContSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql43.setSqlId(sqlid43);//指定使用的Sql的id
		mySql43.addSubPara(prtNo);//指定传入的参数
		mySql43.addSubPara(InputNo);//指定传入的参数
	    var tPolSql  =mySql43.getString();	
		
//		var tPolSql="select * from lbpopol where contno='"+prtNo+"' and inputno='"+InputNo+"'";
		var arrResultPol;
		arrResultPol=easyExecSql(tPolSql);
		if(arrResultPol!=null){
			displayPol(arrResultPol);
		}
		displayOther();
	}
}
//
function afterCodeSelect( cCodeName, Field ){
	
	if(cCodeName=="SequenceNo"){
		//清空投保人信息
		fm.InsuredNo.value="";
		fm.RelationToMainInsured.value="";
		fm.Name.value="";
		fm.Sex.value="";
		fm.Birthday.value="";
		fm.IDType.value="";
		fm.IDNo.value="";
		fm.NativePlace.value="";
		fm.RgtAddress.value="";
		fm.Marriage.value="";
		fm.PostalAddress.value="";
		fm.ZipCode.value="";
		fm.HomeAddress.value="";
		fm.HomeZipCode.value="";
		fm.Mobile.value="";
		fm.CompanyPhone.value="";
		fm.EMail.value="";
		fm.CompanyAddress.value="";
		fm.WorkType.value="";
		fm.PluralityType.value="";
		fm.OccupationCode.value="";
		initBnfGrid();
		
		var sqlid44="DSMulti-PolContSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql44.setSqlId(sqlid44);//指定使用的Sql的id
		mySql44.addSubPara(prtNo);//指定传入的参数
		mySql44.addSubPara(Field.value);//指定传入的参数
		mySql44.addSubPara(InputNo);//指定传入的参数
	    var tSql  =mySql44.getString();	
		
//		var tSql = "select * from lbpoinsured where contno='"+prtNo+"' and SequenceNo='"+Field.value+"' and InputNo='"+InputNo+"'";
		var arrResult;
		arrResult=easyExecSql(tSql);
		if(arrResult!=null){
			displayInsured(arrResult);
			
		var sqlid45="DSMulti-PolContSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql45.setSqlId(sqlid45);//指定使用的Sql的id
		mySql45.addSubPara(prtNo);//指定传入的参数
		mySql45.addSubPara(InputNo);//指定传入的参数
		mySql45.addSubPara(Field.value);//指定传入的参数
	    var tBnfSql  =mySql45.getString();	
			
//			var tBnfSql = "select a.sequenceno,b.name,b.idtype,b.idno,b.relationtoinsured,b.bnflot,b.bnfgrade,b.bnfaddress from lbpoinsured a,lbpobnf b where a.contno=b.contno and a.inputno=b.inputno and a.contno='"+prtNo+"' and a.inputno='"+InputNo+"' and a.SequenceNo='"+Field.value+"'";
			var arrResultBnf;
			arrResultBnf=easyExecSql(tBnfSql);
			if(arrResultBnf!=null){
				turnPage.queryModal(tBnfSql,BnfGrid);
			}
		}
	}
	if(cCodeName=="SequenceNo2"){
		initPolGrid();
		
		var sqlid46="DSMulti-PolContSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql46.setSqlId(sqlid46);//指定使用的Sql的id
		mySql46.addSubPara(Field.value);//指定传入的参数
		mySql46.addSubPara(prtNo);//指定传入的参数
		mySql46.addSubPara(Field.value);//指定传入的参数
		mySql46.addSubPara(InputNo);//指定传入的参数
	   mySql46.addSubPara(InputNo);//指定传入的参数
	    var tSql  =mySql46.getString();	
		
//		var tSql = "select "+Field.value+",b.riskcode,b.amnt,b.mult,b.standbyflag1,b.Insuyear,b.payyears,b.prem,b.standbyflag2,b.remark,b.risksequence,b.polno  from lbpopol b where insuredno  in (select insuredno from lbpoinsured a where contno='"+prtNo+"' and  sequenceno='"+Field.value+"' and inputno='"+InputNo+"') and inputno='"+InputNo+"'";
		var arrResult;
		arrResult=easyExecSql(tSql);
		if(arrResult!=null){
			turnPage.queryModal(tSql,PolGrid);
		}
	}
}

function afterSubmit4( FlagStr, content ){
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
	}
	mAction = "";	
}
//录入完毕
function inputConfirm(wFlag){
	if(InputTime==2){
		if(!beforeSubmit(wFlag)){
			return false;	
		}
	}
	fm.InputTime.value = InputTime;//alert("InputTime:"+InputTime);
	if(InputTime==2){
		fm.WorkFlowFlag.value = ActivityID;//alert(fm.WorkFlowFlag.value);
	}else{
		fm.WorkFlowFlag.value = ActivityID;
	}
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;			//录入完毕
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
	fm.action = "./DSInputConfirm.jsp";
	lockScreen('lkscreen');  
	fm.submit(); //提交
}

/**
* 计算投保人被保人年龄《投保日期与生日之差=投保人被保人年龄》
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

//三码录入的校验函数
function beforeSubmit(wFlag){
	//alert("wFlag:"+wFlag);
	//查询错误信息表，将错误的字段、错误字段名查询出
	
			var sqlid47="DSMulti-PolContSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql47.setSqlId(sqlid47);//指定使用的Sql的id
		mySql47.addSubPara(prtNo);//指定传入的参数
	    var errorMessage  =mySql47.getString();	
	
//	var errorMessage="select errortag,errorcontent from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename in ('LBPOInsured','LBPOCont','LBPOPol','LBPOCustomerImpart')";
	var errorMessageResult;
	errorMessageResult=easyExecSql(errorMessage);
	if(errorMessageResult!=null){
	for(var k=0;k<errorMessageResult.length;k++){
			//对是否已进行过错误信息确认进行校验，如果不是所有错误信息都已经校验则不允许保存
  			if(errorMessageResult[k][0]!="ImpartContent"&&errorMessageResult[k][0]!="ImpartParamModle"&&errorMessageResult[k][0]!="AgentGroup"&&errorMessageResult[k][0]!="NewPayMode"&&errorMessageResult[k][0]!="LBPOCustomerImpart"&&errorMessageResult[k][0]!="Prem"&&errorMessageResult[k][0]!="Amnt"
  			   &&errorMessageResult[k][0]!="RnewFlag"&&errorMessageResult[k][0]!="RiskCode"&&errorMessageResult[k][0]!="Mult"){
  				//alert(arrResultError[k][0]);
  				try{
	  				var ele="";
	  				var eleName="";
	  				ele=errorMessageResult[k][0];
	  				eleName=errorMessageResult[k][1];
	  				//tongmeng 2009-03-19 modify
	  				//如果不需要录入信息,界面直接录空格即可
	  				//alert(document.all(""+ele+"").name+""+":"+document.all(""+ele+"").verifyorder);
	  				if(wFlag!=0){
		  				if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)&&document.all(""+ele+"").verifyorder==wFlag){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  				
		  					alert(eleName+",请确认！");
		  					return false;
		  				}
	  				}else{
	  					if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  					alert(eleName+",请确认！");
		  					return false;
		  				}
	  				}
	  				
  				}catch(ex){
  					//alert("初始化界面出错！");
  				}
  				//特殊element特殊处理
  				if(errorMessageResult[k][0]=="AgentGroup"){
	  				if(fm.AgentGroup){
	  					if(fm.AgentGroup.value==""){
	  						alert("代理人组别未进行确认！");
	  						return false;
	  					}
	  				}
  				}
  				if(errorMessageResult[k][0]=="NewPayMode"){
	  				if(fm.NewPayMode){
	  					if(fm.NewPayMode.value==""){
	  						alert("首期交费形式未进行确认！");
	  						return false;
	  					}
	  				}
  				}
  				
  			}
  		}
  		}
  		//投保人特殊处理
		
		var sqlid48="DSMulti-PolContSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName("app.DSMulti-PolContSql"); //指定使用的properties文件名
		mySql48.setSqlId(sqlid48);//指定使用的Sql的id
		mySql48.addSubPara(prtNo);//指定传入的参数
	    var tErrorAppnt  =mySql48.getString();	
		
//  		var tErrorAppnt = "select errortag,errorcontent from lbpodatadetailerror where bussno='"+prtNo+"'and bussnotype='TB' and tablename='LBPOAppnt'";
  		var arrResultErrorAppnt;//prompt("",tErrorSql);
  		arrResultErrorAppnt=easyExecSql(tErrorAppnt);
  		if(arrResultErrorAppnt!=null){
  			for(k=0;k<arrResultErrorAppnt.length;k++){
  				//alert(arrResultErrorAppnt[k][0]);
	  				var ele="";
	  				if(arrResultErrorAppnt[k][0]=="CompanyAddress"||arrResultErrorAppnt[k][0]=="CompanyPhone"
	  				   ||arrResultErrorAppnt[k][0]=="EMail"||arrResultErrorAppnt[k][0]=="HomeAddress"
	  				   ||arrResultErrorAppnt[k][0]=="HomeZipCode"||arrResultErrorAppnt[k][0]=="IDNo"
	  				   ||arrResultErrorAppnt[k][0]=="IDType"||arrResultErrorAppnt[k][0]=="Marriage"
	  				   ||arrResultErrorAppnt[k][0]=="Mobile"||arrResultErrorAppnt[k][0]=="Name"
	  				   ||arrResultErrorAppnt[k][0]=="NativePlace"||arrResultErrorAppnt[k][0]=="PluralityType"
	  				   ||arrResultErrorAppnt[k][0]=="RgtAddress"||arrResultErrorAppnt[k][0]=="Sex"
	  				   ||arrResultErrorAppnt[k][0]=="WorkType"||arrResultErrorAppnt[k][0]=="OccupationCode"
	  				   ||arrResultErrorAppnt[k][0]=="Birthday"||arrResultErrorAppnt[k][0]=="PostalAddress"
	  				   ||arrResultErrorAppnt[k][0]=="ZipCode"){
	  				   	ele="Appnt"+arrResultErrorAppnt[k][0];
	  				   }else{
	  				   	ele=arrResultErrorAppnt[k][0];
	  				   }
	  				   var eleName=arrResultErrorAppnt[k][1];
	  				   //tongmeng 2009-03-19 modify
	  				//如果不需要录入信息,界面直接录空格即可
	  				   if(wFlag!=0){
		  				if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)&&document.all(""+ele+"").verifyorder==wFlag){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  				
		  					alert(eleName+",请确认！");
		  					return false;
		  				}
	  					}else{
	  					if(((document.all(""+ele+"").value)==""||document.all(""+ele+"").value==null)){
		  				//&&document.all(""+ele+"").verifyorder="1"
		  					alert(eleName+",请确认！");
		  					return false;
		  				}
	  				}
  			}
  		}
  	//将所有被disabled的element都置成disabled=false
  	for(i=0;i<document.fm.elements.length;i++){
	  	if(document.fm.elements[i].tagName=="INPUT" 
	  	//&& (document.fm.elements[i].name.indexOf('Grid')==-1 || 
	  	//document.fm.elements[i].name ==null ||document.fm.elements[i].name=='')
	  	&&document.fm.elements[i].className.indexOf('muline')==-1
	  	//&&document.fm.elements[i].name.indexOf('Grid')==-1
	  	&&document.fm.elements[i].verifyorder==wFlag
	  	)
	  	{
	  	//alert(document.fm.elements[i].name);
	  		document.fm.elements[i].disabled=false;
	  		if(document.fm.elements[i].type=="button"){
	  			document.fm.elements[i].disabled=false;
	  		}
	  	}
  	}
  	if(wFlag==2){
	  	for(i=0;i<document.fm.elements.length;i++){
	  		if(document.fm.elements[i].name.indexOf('PolGrid')!=-1
	  		||document.fm.elements[i].name.indexOf('BnfGrid')!=-1){
	  			document.fm.elements[i].disabled=false;
	  		}
	  	}
  	}
  	if(wFlag==4){
  		for(i=0;i<document.fm.elements.length;i++){
	  		if(document.fm.elements[i].name.indexOf('ImpartGrid')!=-1
	  		||document.fm.elements[i].name.indexOf('AgentImpartGrid')!=-1){
	  			document.fm.elements[i].disabled=false;
	  		}
	  	}
  	}
  	//将INPUTTIME、INPUTNO等公用的放开
  	//fm.InputTime.disabled=false;
  	fm.fmAction.disabled=false;
  	fm.WorkFlowFlag.disabled=false;
  	fm.MissionID.disabled=false;
  	fm.SubMissionID.disabled=false;
  	fm.ActivityID.disabled=false;
  	fm.NoType.disabled=false;
  	fm.PrtNo2.disabled=false;
  	fm.FamilyType.disabled=false;
  	fm.FamilyID.disabled=false;
  	fm.PolType.disabled=false;
  	fm.CardFlag.disabled=false;
  	fm.SequenceNo.disabled=false;
  	fm.ContNo.disabled=false;
  	fm.ProposalContNo.disabled=false;
  	fm.ProposalGrpContNo.disabled=false;
  	fm.SelPolNo.disabled=false;
  	fm.InputNo.disabled=false;
  	fm.InputTime.disabled=false;
  	fm.GrpContNo.disabled=false;
  	fm.ManageCom.disabled=false;
  	fm.PrtNo.disabled=false;
  	fm.AppntFillNo.disabled=false;
  	fm.InsuredFillNo.disabled=false;
  	fm.AppntNo.disabled=false;
  	fm.InsuredNo.disabled=false;//alert(fm.InsuredNo.value);
  	fm.contFillNo.disabled=false;
  	fm.ContType.disabled=false;
  	fm.BonusGetMode1.disabled=false;
  	fm.BonusGetMode2.disabled=false;
  	fm.BonusGetMode3.disabled=false;
  	return true;
}


function afterSubmit( FlagStr, content ){
	unlockScreen('lkscreen');  
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
		//刷新界面
		//displayImpart();
		//displayCont();
		top.opener.easyQueryClickSelf();
		top.close();
	}
	mAction = "";
}

//返回该字段在MultLine中某列的位置
function getLineCount(GridName,tFieldName){
	var tLineCount;
	//受益人
	if(GridName=="BnfGrid"){
		if(tFieldName=="IDType"){
			tLineCount=4;
		}
		if(tFieldName=="IDNo"){
			tLineCount=5;
		}
		if(tFieldName=="Name"){
			tLineCount=3;
		}
		if(tFieldName=="BnfType"){
			tLineCount=2;
		}
		if(tFieldName=="RelationToInsured"){
			tLineCount=6;
		}
		if(tFieldName=="BnfLot"){
			tLineCount=7;
		}
		if(tFieldName=="BnfGrade"){
			tLineCount=8;
		}
		if(tFieldName=="BnfAddress"){
			tLineCount=9;
		}
	}
	if(GridName=="ImpartGrid"){
		if(tFieldName=="ImpartContent"){
			tLineCount=3;
		}
		if(tFieldName=="ImpartParamModle"){
			tLineCount=4;
		}
		if(tFieldName=="PrtFlag"){
			tLineCount=5;
		}
		if(tFieldName=="Insured1"){
			tLineCount=6;
		}
		if(tFieldName=="Insured2"){
			tLineCount=7;
		}
		if(tFieldName=="insured3"){
			tLineCount=8;
		}
	}
	if(GridName=="PolGrid"){
		if(tFieldName=="RiskCode"){
			tLineCount=3;
		}
		if(tFieldName=="Amnt"){
			tLineCount=4;
		}
		if(tFieldName=="Mult"){
			tLineCount=5;
		}
		if(tFieldName=="StandbyFlag1"){
			tLineCount=6;
		}
		if(tFieldName=="InsuYear"){
			tLineCount=7;
		}
		if(tFieldName=="PayYears"){
			tLineCount=8;
		}
		if(tFieldName=="Prem"){
			tLineCount=9;
		}
		if(tFieldName=="StandbyFlag2"){
			tLineCount=10;
		}
		if(tFieldName=="Remark"){
			tLineCount=11;
		}
		if(tFieldName=="PolNo"){
			tLineCount=12;
		}
	}
	return tLineCount;
}

/*********************************************************************
 *  投保人与被保人相同选择框事件
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function isSamePerson()
{
	//alert("1272");
    //对应未选同一人，又打钩的情况
     //对应未选同一人，又打钩的情况
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("投保人与被保人关系不是本人，不能进行该操作！");
    }
    
    if ( fm.SamePersonFlag.checked==true/*&&fm.RelationToAppnt.value!="00"*/)
    {
      //DivLCInsured.style.display = "none";
      //divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      //fm.RelationToAppnt.value="00"
      //alert(1289);
      displayissameperson();
    }
    //对应是同一人，又打钩的情况
    //else if (fm.SamePersonFlag.checked == true)
    //{
    //  document.all('DivLCInsured').style.display = "none";
    //  divLCInsuredPerson.style.display = "none";
    //  divSalary.style.display = "none";

    //  displayissameperson();
    //}
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "";
      //divSalary.style.display = "";
      if(InsuredGrid.mulLineCount==0)
	  {
		//fm.SequenceNo.value="1";//在第一次录入被保险人时默认为“1－被保险人”
		//fm.RelationToMainInsured.value="00";//与第一被保险人关系:当被保险人客户内部号为“1－被保险人”时，默认为“00－本人”
	  }
	  else
	  {
	   //try{document.all('SequenceNo').value=""; }catch(ex){};
       try{document.all('RelationToMainInsured').value=""; }catch(ex){};
	  }
      //try{document.all('InsuredNo').value=""; }catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= ""; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('InsuredPlace').value= ""; }catch(ex){};
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
      //try{document.all('RelationToAppnt').value= "";}catch(ex){};
      try{document.all('Fax').value= "";}catch(ex){};
      try{document.all('HomePhone').value= "";}catch(ex){};
      try{document.all('HomeAddress').value=  "";}catch(ex){};
      try{document.all('HomeZipCode').value=  "";}catch(ex){};
      try{document.all('RgtAddress').value=  "";}catch(ex){};
      try{document.all('CompanyPhone').value=  "";}catch(ex){};
      try{document.all('CompanyAddress').value=  "";}catch(ex){};

    }
}

function displayissameperson()
{
  //try{fm.SequenceNo.value="1";}catch(ex){};//在第一次录入被保险人时默认为“1－被保险人”
  try{fm.RelationToMainInsured.value="00";}catch(ex){};//与第一被保险人关系:当被保险人客户内部号为“1－被保险人”时，默认为“00－本人”
//  try{fm.RelationToAppnt.value="00";}catch(ex){};
　//try{document.all('InsuredNo').value= document.all("AppntNo").value;                 }catch(ex){};
　try{document.all('Name').value= document.all("AppntName").value;                    }catch(ex){};
　try{document.all('Sex').value= document.all("AppntSex").value;                      }catch(ex){};
　try{document.all('Birthday').value= document.all("AppntBirthday").value;            }catch(ex){};
　try{document.all('IDType').value= document.all("AppntIDType").value;                }catch(ex){};
　try{document.all('IDNo').value= document.all("AppntIDNo").value;                    }catch(ex){};
　try{document.all('Password').value= document.all( "AppntPassword" ).value;          }catch(ex){};
　try{document.all('NativePlace').value= document.all("AppntNativePlace").value;      }catch(ex){};
　try{document.all('Nationality').value= document.all("AppntNationality").value;      }catch(ex){};
　try{document.all('InsuredAddressNo').value= document.all("AppntAddressNo").value;   }catch(ex){};
　try{document.all('InsuredPlace').value= document.all( "AppntPlace" ).value;      	  }catch(ex){};
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
　try{document.all('CompanyAddress').value= document.all("AppntCompanyAddress").value;}catch(ex){};
　try{document.all('GrpZipCode').value= document.all("AppntGrpZipCode").value;}catch(ex){};
　try{document.all('GrpFax').value= document.all("AppntGrpFax").value;}catch(ex){};
　try{document.all('HomeAddress').value= document.all("AppntHomeAddress").value;}catch(ex){};
　try{document.all('HomePhone').value= document.all("AppntHomePhone").value;}catch(ex){};
　try{document.all('HomeZipCode').value= document.all("AppntHomeZipCode").value;}catch(ex){};
　try{document.all('HomeFax').value= document.all("AppntHomeFax").value;}catch(ex){};
　//try{document.all('RelationToAppnt').value="00";}catch(ex){};
　try{document.all('InsuredProvince').value= document.all("AppntProvince").value;}catch(ex){};
　try{document.all('InsuredCity').value= document.all("AppntCity").value;}catch(ex){};
　try{document.all('InsuredDistrict').value= document.all("AppntDistrict").value;}catch(ex){};
　try{document.all('LicenseType').value= document.all("AppntLicenseType").value;}catch(ex){};
  try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('RgtAddress').value= document.all("AppntRgtAddress").value;}catch(ex){};
  try{document.all('CompanyPhone').value= document.all("AppntCompanyPhone").value;}catch(ex){};
  try{document.all('Avoirdupois').value= document.all("AppntAvoirdupois").value;}catch(ex){};
  //showOneCodeName('incomeway','IncomeWay','IncomeWayName');

/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
  }
***************************/
}
