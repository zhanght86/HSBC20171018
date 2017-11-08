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
this.window.onfocus = myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

function afterCodeSelect( cCodeName, Field )
{
//alert("cCodeName:"+cCodeName);
 	//alert("cCodeName==="+cCodeName);
	if(cCodeName=="CardType"){
	//alert(123);
	//进入单证录入页面 模仿proposalinput.jsp页面
	    var CardType =fm.CardType.value;
		//alert("fm.CardType.value:"+fm.CardType.value);
		parent.fraInterface.location ='MSCardContInputMain.jsp?CardType='+CardType;
	//getRiskInput();
	}
	//自动填写受益人信息
	  if (cCodeName == "customertype") {
	  var ContType="1";
	  	//alert("12:"+Field.value);
	  	
	    if (Field.value == "A") {
	   // alert("ContType:"+ContType);
	  	  if(ContType!="2")
	  	  {
          var index = BnfGrid.mulLineCount;
          //alert("document.all(AppntName).value:"+fm.AppntName.value)
          BnfGrid.setRowColData(index-1, 2, document.all("AppntName").value);
          BnfGrid.setRowColData(index-1, 3, document.all("AppntSex").value);

          BnfGrid.setRowColData(index-1, 4, document.all("AppntIDType").value);
          BnfGrid.setRowColData(index-1, 5, document.all("AppntIDNo").value);
          BnfGrid.setRowColData(index-1, 6,parent.VD.gVSwitch.getVar( "RelationToAppnt"));

          BnfGrid.setRowColData(index-1, 9, document.all("AppntHomeAddress").value);
          //hl
          BnfGrid.setRowColData(index-1, 13, document.all("AppntNo").value);
          //alert("toubaoren:"+document.all("AppntNo").value)

	  	  }
	  	  else
	  	  {
	  	    alert("投保人为团体，不能作为受益人！")
          var index = BnfGrid.mulLineCount;
          BnfGrid.setRowColData(index-1, 2, "");
          BnfGrid.setRowColData(index-1, 3, "");
          BnfGrid.setRowColData(index-1, 4, "");
          BnfGrid.setRowColData(index-1, 5, "");
          BnfGrid.setRowColData(index-1, 8, "");
	  	  }
	  	}
	  	else if (Field.value == "I") {
	  	  var index = BnfGrid.mulLineCount;
        BnfGrid.setRowColData(index-1, 2, document.all("Name").value);
        //BnfGrid.setRowColData(index-1, 3, document.all("IDType").value);
        BnfGrid.setRowColData(index-1, 3, document.all("Sex").value);
        BnfGrid.setRowColData(index-1, 4, document.all("IDType").value);
        BnfGrid.setRowColData(index-1, 5, document.all("IDNo").value);
        BnfGrid.setRowColData(index-1, 6, "00");
        //BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
        //hl
        BnfGrid.setRowColData(index-1, 9, document.all("HomeAddress").value);
     
        BnfGrid.setRowColData(index-1, 13, document.all("InsuredNo").value);
	//alert("4544564"+document.all("InsuredNo").value);
	  	}
	  	return;
    }
}
function returnparent(){
    parent.fraInterface.location ='CardTypeSelect.jsp';
}

/*********************************************************************
 *  保存集体投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
try{
        fm.PrtNo.disabled =false;
        fm.ManageCom.disabled =false;
        fm.AppntCustomerNo.disabled =false;
        fm.InsuredNo.disabled=false;
        }catch(ex){}
//if(checkInfo()==false) return false;
//if(verifyInput()==false) return false;
    //modify by zhangxing
   // if (verifyInputNB("1") == false) return false;
   // if (verifyInputNB("2") == false) return false;
   if(fm.PrtNo.value==null||fm.PrtNo.value==""){
       alert("请录入印刷号！");
       fm.PrtNo.focus();
       return ;
   }
   if(fm.FirstTrailDate){
    if (fm.FirstTrailDate.value.length != 10 || fm.FirstTrailDate.value.substring(4, 5) != '-' || fm.FirstTrailDate.value.substring(7, 8) != '-' || fm.FirstTrailDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.FirstTrailDate.focus();
        return;
    }
    }
   if(fm.PolExpireDate){
    if (fm.PolExpireDate.value.length != 10 || fm.PolExpireDate.value.substring(4, 5) != '-' || fm.PolExpireDate.value.substring(7, 8) != '-' || fm.PolExpireDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.PolExpireDate.focus();
        return;
    }
    }
   if(fm.PolApplyDate){
    if (fm.PolApplyDate.value.length != 10 || fm.PolApplyDate.value.substring(4, 5) != '-' || fm.PolApplyDate.value.substring(7, 8) != '-' || fm.PolApplyDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.PolApplyDate.focus();
        return;
    }
    }
    //卡号和投保单号不允许相同
	//if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	//{
	//	alert("卡号和投保单号不允许相同。\n 而输入的卡号和投保单号相同，不允许保存");
	//	return false;
	//}
	if(fm.AppntIDType&&fm.AppntIDNo){
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("证件类型和证件号码必须同时填写或不填");
        return false;
    }
   }
   if(fm.Mult){
		if(trim(document.all('Mult').value)== "")
		{
			alert('份数不能为空!');
			return false;
		}
		if(!isNumeric(trim(document.all('Mult').value)))
		{
				alert('份数必须为数字!');
				document.all('Mult').value = "";
				document.all('Mult').focus();
				return false;
		}
		if(parseFloat(trim(document.all('Mult').value)) == 0)
		{
			alert('份数不能为零!');
			document.all('Mult').value = "";
			document.all('Mult').focus();
			return false;
		}
  }
    //for (i = 0; i < BnfGrid.mulLineCount; i++)
    //{

        //如果受益人的受益份额和受益顺序没有录入则默认为1
    //    if (BnfGrid.getRowColData(i, 7) == null || BnfGrid.getRowColData(i, 7) == '')
    //    {
    //        BnfGrid.setRowColData(i, 7, "1");
    //    }
    //   if (BnfGrid.getRowColData(i, 6) == null || BnfGrid.getRowColData(i, 6) == '')
    //    {
    //        BnfGrid.setRowColData(i, 6, "1");
    //    }
    //}
    if(verifyInput()==false) return false;
    //险种的一些校验
    if(checkInfo()==false) return false;
		//将其他险种信息列表的信息赋值到其他单独控件里
   //setValueFromPolOtherGrid();

    //getdetailwork();
    //getdetailwork2();
lockScreen('lkscreen');  
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.fmAction.value = "INSERT";
    fm.submit();
    //提交
}
function submitForm1()
{
try{
        fm.PrtNo.disabled =false;
        fm.ManageCom.disabled =false;
        fm.AppntCustomerNo.disabled =false;
        fm.InsuredNo.disabled=false;
        }catch(ex){}
//if(checkInfo()==false) return false;
//if(verifyInput()==false) return false;
    //modify by zhangxing
   // if (verifyInputNB("1") == false) return false;
   // if (verifyInputNB("2") == false) return false;
   if(fm.ProposalContNo.value==null||fm.ProposalContNo.value==""){
      alert("请先保存，再做修改！");
      return false;
   }
   if(fm.PrtNo.value==null||fm.PrtNo.value==""){
       alert("请录入印刷号！");
       fm.PrtNo.focus();
       return ;
   }
   if(fm.FirstTrailDate){
    if (fm.FirstTrailDate.value.length != 10 || fm.FirstTrailDate.value.substring(4, 5) != '-' || fm.FirstTrailDate.value.substring(7, 8) != '-' || fm.FirstTrailDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.FirstTrailDate.focus();
        return;
    }
    }
   if(fm.PolExpireDate){
    if (fm.PolExpireDate.value.length != 10 || fm.PolExpireDate.value.substring(4, 5) != '-' || fm.PolExpireDate.value.substring(7, 8) != '-' || fm.PolExpireDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.PolExpireDate.focus();
        return;
    }
    }
   if(fm.PolApplyDate){
    if (fm.PolApplyDate.value.length != 10 || fm.PolApplyDate.value.substring(4, 5) != '-' || fm.PolApplyDate.value.substring(7, 8) != '-' || fm.PolApplyDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.PolApplyDate.focus();
        return;
    }
    }
    //卡号和投保单号不允许相同
	//if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	//{
	//	alert("卡号和投保单号不允许相同。\n 而输入的卡号和投保单号相同，不允许保存");
	//	return false;
	//}
	if(fm.AppntIDType&&fm.AppntIDNo){
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("证件类型和证件号码必须同时填写或不填");
        return false;
    }
   }
   if(fm.Mult){
		if(trim(document.all('Mult').value)== "")
		{
			alert('份数不能为空!');
			return false;
		}
		if(!isNumeric(trim(document.all('Mult').value)))
		{
				alert('份数必须为数字!');
				document.all('Mult').value = "";
				document.all('Mult').focus();
				return false;
		}
		if(parseFloat(trim(document.all('Mult').value)) == 0)
		{
			alert('份数不能为零!');
			document.all('Mult').value = "";
			document.all('Mult').focus();
			return false;
		}
  }
    //for (i = 0; i < BnfGrid.mulLineCount; i++)
    //{

        //如果受益人的受益份额和受益顺序没有录入则默认为1
    //    if (BnfGrid.getRowColData(i, 7) == null || BnfGrid.getRowColData(i, 7) == '')
    //    {
    //        BnfGrid.setRowColData(i, 7, "1");
    //    }
    //   if (BnfGrid.getRowColData(i, 6) == null || BnfGrid.getRowColData(i, 6) == '')
    //    {
    //        BnfGrid.setRowColData(i, 6, "1");
    //    }
    //}
    if(verifyInput()==false) return false;
    //险种的一些校验
    if(checkInfo()==false) return false;

		//将其他险种信息列表的信息赋值到其他单独控件里
   //setValueFromPolOtherGrid();

    //getdetailwork();
    //getdetailwork2();
lockScreen('lkscreen');  
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmAction.value = "UPDATE";
    fm.submit();
    //提交
}



function checkInfo(){
  //校验销售渠道和代理人
  var tSaleChnl=fm.SaleChnl.value;
  //根据险种校验不同卡单的一些规则
  var tCardType =fm.RiskCode.value;
  var tAgentCode =fm.AgentCode.value;
  //var tCheckSQL1 ="select count(1) from ldcode1 where code ='"+tSaleChnl+"' and code1=(select branchtype from laagent where agentcode ='"+tAgentCode+"') and codetype ='salechnlagentctrl'";
  var sqlid1 = 'CardContInputSql1';
	var mySql1 = new SqlClass();
	mySql1.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql1.addSubPara(tSaleChnl);// 指定传入的参数
	mySql1.addSubPara(tAgentCode);// 指定传入的参数
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	strsql = mySql1.getString();
  var tCheckValue =easyExecSql(strsql);
//  if(tCheckValue==0&&tCardType!="311603"){
  if(tCheckValue==0){
     alert("代理人与销售渠道不匹配！");
     return false;
  }
  /**
   * 学生计划
   * */
  if(tCardType!=null&&tCardType=="111603"){
	  if(check111603()==false){
		  return false;
	  }
  }
  /**
   * 金如意
   * */
  if(tCardType!=null&&tCardType=="141812"){
	  if(check141812()==false){
		  return false;
	  }
  }
  /**
   * 阳光旅程
   * */
  if(tCardType!=null&&tCardType=="311603"){
  	  if(check311603()==false){
  		  return false;
   	  }
  }
  return true;
}

/**
 * 校验11603--学生计划
 * */
function check111603(){
	
	var tPolApplyDate = fm.PolApplyDate.value;
    var tFirstTrialDate =fm.FirstTrailDate.value;
//    var tCValiDate =fm.CValiDate.value;
//    var tDaySQL ="select  to_char(trunc(to_date('"+tCValiDate+"')-to_date('"+tPolApplyDate+"','yyyy-mm-dd')))   from   dual";
//    var tDays =easyExecSql(tDaySQL);
    //var tForCValiDateSql = "select  to_char(trunc_date(adddate(to_date('"+fm.PolApplyDate.value+"','yyyy-mm-dd'),7)),'yyyy-mm-dd')   from   dual";
    var sqlid2 = 'CardContInputSql2';
	var mySql2 = new SqlClass();
	mySql2.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql2.addSubPara(fm.PolApplyDate.value);// 指定传入的参数
	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
	var tForCValiDateSql = mySql2.getString();
    
    var tCValiDate = easyExecSql(tForCValiDateSql);
    fm.CValiDate.value = tCValiDate;
    var tFirstTrailDate= fm.FirstTrailDate.value;
    //var tNowDaySQL ="select  to_char(trunc_date(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date(substr('"+tCValiDate+"',1,10),'yyyy-mm-dd'))),'yyyy-mm-dd')   from   dual";
    var sqlid3 = 'CardContInputSql3';
	var mySql3 = new SqlClass();
	mySql3.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql3.addSubPara(tCValiDate);// 指定传入的参数
	mySql3.setSqlId(sqlid3);// 指定使用的Sql的id
	var tNowDaySQL = mySql3.getString();
    var tIntervel =easyExecSql(tNowDaySQL);
    if(tIntervel >0){
      alert("生效日期不能早于录入日期，请重新确认!");
     return false;
    }

	//被保人年龄和投被保人关系的校验
	var rBirthday =fm.Birthday.value;
	var tRelationToInsured =fm.RelationToInsured.value;
	var tAge = getAge(rBirthday);//alert(tAge);
	for(i=0;i<BnfGrid.mulLineCount;i++){
		var tBnfRelation =BnfGrid.getRowColData(i,6);
		var tBnfName =BnfGrid.getRowColData(i,2);
		if((tBnfName!=null&&tBnfName!="")||(tBnfRelation!=null&&tBnfRelation!="")){
			if(tBnfName==null||tBnfName==""||tBnfRelation==null||tBnfRelation==""){
				alert("受益人信息录入不完整!");
				return false;
			}
		}
		if(fm.Name.value==tBnfName){
        	if(!confirm("身故受益人不能为被保险人本人，是否强制保存?")){
        		return false;
        	}
        }
		if(tAge<18&&tBnfRelation!="03"&&tBnfRelation!="04"&&tBnfRelation!="31"){
			alert("未成年人身故受益人必须为其父母，否则不能投保！");
			return false;
		}
		//受益人信息
		if(tBnfRelation=="30"){
			alert("身故受益人与被保险人关系有误，不能投保");
			return false;
		}
	}
	if(tAge<3||tAge>22){
	    alert("被保人投保年龄不符合投保规则规定，不能投保");
	    return false;
	}
	if(tAge<18&&(tRelationToInsured!="03"&&tRelationToInsured!="04")){
	    alert("投保人与被保险人关系不符合投保规则规定，不能投保");
	    return false;
	}
	if(tAge>=18&&tRelationToInsured=="30"){
	    alert("投保人与被保险人关系不符合投保规则规定，不能投保");
	    return false;
	}
	if(tAge>=18){
//		//被保人年龄满18周岁时，该被保险人投保份数大于6份，则提示“本保险最高投保份数限定为6份，不能投保”
		var tAgentCode =fm.AgentCode.value;
		var tManageCom =fm.ManageCom.value;
	    var tInsuredName =fm.Name.value;
	    var tInsuredSex = fm.Sex.value;
	    var tInsuredBirthday =fm.Birthday.value;
//	    var tMultSQL =" select count(1) from lcpol where agentcode ='"+tAgentCode+"' and managecom ='"+tManageCom+"' and insuredname='"+tInsuredName+"'"
//	                 +" and insuredbirthday='"+tInsuredBirthday+"' and riskcode ='111603' and prtno !='"+fm.PrtNo.value+"'";
//	    var tMultSQL ="select" 
//					+"(select count(1) from lccontstate where statetype='Available' and state='0' and enddate is null and polno in("
//					+"select polno from lcpol a where a.agentcode = '"+tAgentCode+"'"
//					//+" and a.managecom = '"+tManageCom+"'"
//					+" and exists( select 1 from lcinsured b where a.prtno=b.prtno" 
//					+" and b.name='"+tInsuredName+"'"
//					+" and b.sex='"+tInsuredSex+"'"
//					+" and b.birthday='"+tInsuredBirthday+"')"
//					+" and a.riskcode = '111603'"
//					+" and a.prtno != '"+fm.PrtNo.value+"'"
//					+" and a.appflag !='0'"
//					+" and a.uwflag not in ('1', '2', 'a')"
//					+" )) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
//			    	//+" and d.managecom = '"+tManageCom+"'"
//					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
//					+" and c.name='"+tInsuredName+"'"
//					+" and c.sex='"+tInsuredSex+"'"
//					+" and c.birthday='"+tInsuredBirthday+"')"
//					+" and d.riskcode = '111603'"
//					+" and d.prtno != '"+fm.PrtNo.value+"'"
//					+" and d.appflag = '0'"
//					+" and d.uwflag not in ('1', '2', 'a')"
//					//+" and d.prem = 30"
//					+") from dual";
	    
	    var sqlid4 = 'CardContInputSql4';
		var mySql4 = new SqlClass();
		mySql4.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		mySql4.addSubPara(tAgentCode);// 指定传入的参数
		mySql4.addSubPara(tInsuredName);// 指定传入的参数
		mySql4.addSubPara(tInsuredSex);// 指定传入的参数
		mySql4.addSubPara(tInsuredBirthday);// 指定传入的参数
		mySql4.addSubPara(fm.PrtNo.value);// 指定传入的参数
		mySql4.addSubPara(tAgentCode);// 指定传入的参数
		mySql4.addSubPara(tInsuredName);// 指定传入的参数
		mySql4.addSubPara(tInsuredSex);// 指定传入的参数
		mySql4.addSubPara(tInsuredBirthday);// 指定传入的参数
		mySql4.addSubPara(fm.PrtNo.value);// 指定传入的参数
		
		mySql4.setSqlId(sqlid4);// 指定使用的Sql的id
		var tMultSQL = mySql4.getString();
		var tCount =easyExecSql(tMultSQL);
	    if(tCount>=6){
	    	  alert("此被保险人本保险最高投保份数限定为6份，不能投保!");
	    	  return false;
	    }
	}else{
	    //选择“30-计划一”：管理机构为8611(北京)、8631(上海)时，该被保险人投保份数大于6份，则提示“该被保险人的累计身故保险金额总和超过规定限额，不能投保”
		var tAgentCode =fm.AgentCode.value;
	    var tManageCom =fm.ManageCom.value;
	    var tcheckManageCom ="";
	    if(tManageCom.length>=4){
	    	tcheckManageCom =tManageCom.substring(0,4);
	    }else{
	        tcheckManageCom =tManageCom+"00";
	    }
	    var tInsuredName =fm.Name.value;
	    var tInsuredBirthday =fm.Birthday.value;
	    var tInsuredSex = fm.Sex.value;
	    var tPrem = fm.Prem.value;
//	    var tMultSQL =" select count(1) from lcpol where agentcode ='"+tAgentCode+"' and managecom ='"+tManageCom+"' and insuredname='"+tInsuredName+"'"
//	                +" and insuredbirthday='"+tInsuredBirthday+"' and riskcode ='111603' and prtno !='"+fm.PrtNo.value+"' and appflag<>'4'"
//	                +" and uwflag not in ('1','2','a')";
/*	    var	tMultSQL ="select" 
	    			+"(select count(1) from lccontstate where statetype='Available' and state='0' and enddate is null and polno in("
	    			+"select polno from lcpol a where a.agentcode = '"+tAgentCode+"'"
	    			//+" and a.managecom = '"+tManageCom+"'"
	    			+" and exists( select 1 from lcinsured b where a.prtno=b.prtno" 
	    			+" and b.name='"+tInsuredName+"'"
	    			+" and b.sex='"+tInsuredSex+"'"
	    			+" and b.birthday='"+tInsuredBirthday+"')"
	    			+" and a.riskcode = '111603'"
	    			+" and a.prtno != '"+fm.PrtNo.value+"'"
	    			+" and a.appflag !='0'"
	    			+" and a.uwflag not in ('1', '2', 'a')";*/
	    
	    var sqlid5 = 'CardContInputSql5';
		var mySql5 = new SqlClass();
		mySql5.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		mySql5.addSubPara(tAgentCode);// 指定传入的参数
		mySql5.addSubPara(tInsuredName);// 指定传入的参数
		mySql5.addSubPara(tInsuredSex);// 指定传入的参数
		mySql5.addSubPara(tInsuredBirthday);// 指定传入的参数
		mySql5.addSubPara(fm.PrtNo.value);// 指定传入的参数
		mySql5.setSqlId(sqlid5);// 指定使用的Sql的id
		var tMultSQL = mySql5.getString();
	    if(tPrem =="30"&&(tcheckManageCom=="8611"||tcheckManageCom =="8631")){
	    	tMultSQL =tMultSQL+" and a.prem =30 )) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem = 30"
	    			+") from dual";
	    	//prompt("",tMultSQL);
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=6){
	        	alert("该被保险人的累计身故保险金额总和超过规定限额，不能投保!");
	        	return false;
	        }
	    }
	    if(tPrem=="30"&&(tcheckManageCom!="8611"&&tcheckManageCom!="8631")){
	        tMultSQL =tMultSQL+" and a.prem =30)) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem=30"
	    			+") from dual";
	        //prompt("",tMultSQL);
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=3){
	        	alert("该被保险人的累计身故保险金额总和超过规定限额，不能投保!");
	        	return false;
	        }
	    }
	    if(tPrem=="50"&&(tcheckManageCom =="8611"||tcheckManageCom=="8631")){
	        tMultSQL =tMultSQL+" and a.prem =50)) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem=50"
	    			+") from dual";
	        //prompt("",tMultSQL);
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=4){
	        	alert("该被保险人的累计身故保险金额总和超过规定限额，不能投保!");
	        	return false;
	        }
	    }
	    if(tPrem=="50"&&(tcheckManageCom !="8611"&&tcheckManageCom !="8631")){
	        tMultSQL =tMultSQL+" and a.prem =50)) + (select count(1) from lcpol d where d.agentcode='"+tAgentCode+"'"
			    	//+" and d.managecom = '"+tManageCom+"'"
					+" and exists( select 1 from lcinsured c where d.prtno=c.prtno" 
					+" and c.name='"+tInsuredName+"'"
					+" and c.sex='"+tInsuredSex+"'"
					+" and c.birthday='"+tInsuredBirthday+"')"
					+" and d.riskcode = '111603'"
					+" and d.prtno != '"+fm.PrtNo.value+"'"
					+" and d.appflag = '0'"
					+" and d.uwflag not in ('1', '2', 'a')"
					+" and d.prem=50"
	    			+") from dual";
		    var tCount =easyExecSql(tMultSQL);
	        if(tCount>=2){
	        	alert("该被保险人的累计身故保险金额总和超过规定限额，不能投保!");
	        	return false;
	        }
	    }
	}
	//一个没用的校验
	var PolStartDate =fm.CValiDate.value;
	//09-08-26  删除终止日期并删除相关校验
//	var PolEndDate =fm.PolExpireDate.value;
//	var tDaySQL =" select  to_char(trunc(to_date('"+PolEndDate+"','yyyy-mm-dd')-to_date('"+PolStartDate+"','yyyy-mm-dd')))   from   dual";
//	tDays =easyExecSql(tDaySQL);
//	if(tDays!=364){
//		alert("保单止期应在保单生效日后一年!");
//	    return false;
//	}
	//同一人校验
	var tRelationToInsured =fm.RelationToInsured.value;
	var tAppntName =fm.AppntName.value;
	var tInsuredName =fm.Name.value;
	if(tRelationToInsured=="00"){
		if(tInsuredName!=tAppntName){
			alert("投保人与被保人关系为00-本人，但投被保人的姓名不相同，请确认后保存！");
	  	 	return false;
	  	}else{
	  	 	    //fm.SameFersonFlag.value="Y";
	  	}
	}
	//录入日期tNow->签单日期tS->生效日期tP
	//var tCSSQL =" select  to_char(trunc(to_date('"+PolEndDate+"')-to_date('"+PolStartDate+"','yyyy-mm-dd')))   from   dual";
//	var SignDate = fm.SignDate.value;
	tP=new Date(PolStartDate.replace("-",",")).getTime(); 
	//tS=new Date(SignDate.replace("-",",")).getTime();
	tNow=new Date(CurrentDate.replace("-",",")).getTime();
//	if(tS>tNow) {
//		alert("录入日期应该晚于签单日期.请重新确认!");
//	    return false;
//	}   
//	if(tP<tS){
//		alert("签单日期应该早于生效日期.请重新确认!");
//	    return false;
//	}
	//保险期限的开始日期不能早于签单日期、不能晚于签单日期5天以上，且不能早于录入日期、
	//不能晚于录入日期5天以上，保存时提示“保险期限的开始日期不能早于签单日期、不能晚于签单日期5天以上，
	//且不能早于录入日期、不能晚于录入日期5天以上
	//alert("PolStartDate:"+PolStartDate+"||SignDate:"+SignDate);
//	var tDaySQL ="select  to_char(trunc(to_date('"+PolStartDate+"')-to_date('"+SignDate+"','yyyy-mm-dd')))   from   dual";
//	var tDays =easyExecSql(tDaySQL);
//	if(tDays>5){
//		alert("保险期限的开始日期不能晚于签单日期5天以上.请重新确认!");
//	    return false;
//	}
	/*var tDaySQL1 ="select  to_char(trunc(datediff(to_date('"+PolStartDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";*/
	 var sqlid6 = 'CardContInputSql6';
		var mySql6 = new SqlClass();
		mySql6.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		mySql6.addSubPara(PolStartDate);// 指定传入的参数
		mySql6.setSqlId(sqlid6);// 指定使用的Sql的id
		var tDaySQL1 = mySql6.getString();
	
	var ttDays =easyExecSql(tDaySQL1);
	/*var tDaySQL2 ="select  to_char(trunc(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date('"+PolStartDate+"','yyyy-mm-dd'))))   from   dual";*/
	var sqlid7 = 'CardContInputSql7';
	var mySql7 = new SqlClass();
	mySql7.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql7.addSubPara(PolStartDate);// 指定传入的参数
	mySql7.setSqlId(sqlid7);// 指定使用的Sql的id
	var tDaySQL2 = mySql7.getString();
	
	var ttDays2 =easyExecSql(tDaySQL2);
	if(ttDays>5||ttDays2>5){
		//alert("保险期限的开始日期不能早于录入日期5天以上,不能晚于录入日期5天以上.请重新确认!");
	    //return false;
	}
//	var tDaySQL3 ="select  to_char(trunc(to_date(substr(sysdate,0,10),'yyyy-mm-dd')-to_date('"+SignDate+"','yyyy-mm-dd')))   from   dual";
//	var ttDays3 =easyExecSql(tDaySQL3);
//	if(ttDays3<0){
//		alert("签单日期应该早于录入日期!");
//	    return false;
//	}
    var tF =new Date(tFirstTrailDate.replace("-",",")).getTime(); 
    //alert(tPA+"||"+tF);
    /*var tDaySQL11 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";*/
    var sqlid8 = 'CardContInputSql8';
	var mySql8 = new SqlClass();
	mySql8.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql8.addSubPara(tFirstTrailDate);// 指定传入的参数
	mySql8.addSubPara(tPolApplyDate);// 指定传入的参数
	mySql8.setSqlId(sqlid8);// 指定使用的Sql的id
	var tDaySQL11 = mySql8.getString();
    
    var tDays11=easyExecSql(tDaySQL11);
    if(tDays11<0||tDays11>7){
       alert("投保日期不能晚于初审日期，且不能早于初审日期7天，请重新确认！");
       return false;
    }
    /*var tDaySQL12 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";*/
    var sqlid9 = 'CardContInputSql9';
	var mySql9 = new SqlClass();
	mySql9.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql9.addSubPara(tFirstTrailDate);// 指定传入的参数
	mySql9.setSqlId(sqlid9);// 指定使用的Sql的id
	var tDaySQL12 = mySql9.getString();
    
    var tDays12=easyExecSql(tDaySQL12);
    if(tDays12>0){
       alert("初审日期不能晚于录入日期(可以等于录入日期)！");
       return false;
    }
}

/**
 * 校验141812--金如意
 * */
function check141812(){
	var tCount = BnfGrid.mulLineCount ;
    for(i=0;i<BnfGrid.mulLineCount;i++){
      var tBnfName =BnfGrid.getRowColData(i,2);
      var tBnfRelation =BnfGrid.getRowColData(i,6);
      if((tBnfName!=null&&tBnfName!="")||(tBnfRelation!=null&&tBnfRelation!="")){
         if(tBnfName==null||tBnfName==""||tBnfRelation==null||tBnfRelation==""){
         alert("受益人信息录入不完整!");
         return false;
         }
      }
    }
    //关于投保日期校验
    //新保险法的需求 生效日期为只读并又系统自动算出  客户都懒到一定程度了-_-|||
    //var tForCValiDateSql = "select  to_char(trunc(to_date('"+fm.PolApplyDate.value+"')+7))   from   dual";
    var sqlid10 = 'CardContInputSql10';
	var mySql10 = new SqlClass();
	mySql10.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql10.addSubPara(fm.PolApplyDate.value);// 指定传入的参数
	mySql10.setSqlId(sqlid10);// 指定使用的Sql的id
	var tForCValiDateSql = mySql10.getString();
    
    var tCValiDate = easyExecSql(tForCValiDateSql);
    fm.CValiDate.value = tCValiDate;
   // var tNowDaySQL ="select  to_char(trunc(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date('"+tCValiDate+"','yyyy-mm-dd'))))   from   dual";
    
    var sqlid11 = 'CardContInputSql11';
	var mySql11 = new SqlClass();
	mySql11.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql11.addSubPara(tCValiDate);// 指定传入的参数
	mySql11.setSqlId(sqlid11);// 指定使用的Sql的id
	var tNowDaySQL = mySql11.getString();
    var tIntervel =easyExecSql(tNowDaySQL);
    if(tIntervel >0){
      alert("生效日期不能早于录入日期，请重新确认!");
      return false;
    }
    var tFirstTrailDate= fm.FirstTrailDate.value;
    var tPolApplyDate = fm.PolApplyDate.value;
    var tFirstTrialDate =fm.FirstTrailDate.value;
//    var tCValiDate =fm.CValiDate.value;
//    var tDaySQL ="select  to_char(trunc(to_date('"+tCValiDate+"')-to_date('"+tPolApplyDate+"','yyyy-mm-dd')))   from   dual";
//    var tDays =easyExecSql(tDaySQL);
    //var tNowDaySQL ="select  to_char(trunc(datediff(to_date(substr(now(),1,10),'yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";
    
    var sqlid12 = 'CardContInputSql12';
	var mySql12 = new SqlClass();
	mySql12.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql12.addSubPara(tPolApplyDate);// 指定传入的参数
	mySql12.setSqlId(sqlid12);// 指定使用的Sql的id
	var tNowDaySQL = mySql12.getString();
    
    var tIntervel =easyExecSql(tNowDaySQL);
    if(tIntervel <0){
      alert("投保日期应该早于录入日期!");
     return false;
    }
//    var tPA =new Date(tPolApplyDate.replace("-",",")).getTime(); 
//    var tNowDate =new Date(tPolApplyDate.replace("-",",")).getTime(); 
//    if(tDays!=5||tNowDate>tPA){
//       alert("投保日期应为生效日期的前5天，且不能早于录入日期.请重新确认!");
//       return false;
//    }
    //var tDaySQL1 ="select  to_char(trunc(to_date('"+tPolApplyDate+"')-to_date(substr(sysdate,0,10),'yyyy-mm-dd')))   from   dual";
    //var ttDays =easyExecSql(tDaySQL1);
    //if(ttDays<0){
    //  alert("投保日期不能早于录入日期.请重新确认!");
    //  return false;
    //}
   // var tDaySQL1 ="select  to_char(trunc(datediff(to_date('"+tCValiDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";
    
    var sqlid13 = 'CardContInputSql13';
	var mySql13 = new SqlClass();
	mySql13.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql13.addSubPara(tCValiDate);// 指定传入的参数
	mySql13.setSqlId(sqlid13);// 指定使用的Sql的id
	var tDaySQL1 = mySql13.getString();
    
    var ttDays =easyExecSql(tDaySQL1);
    //var tFirstTrailSQL="select  to_char(trunc(to_date('"+tFirstTrialDate+"')-to_date('"+tPolApplyDate+"','yyyy-mm-dd')))   from   dual";
    //var tFirstTrailDays =easyExecSql(tFirstTrailSQL);
    var tF =new Date(tFirstTrailDate.replace("-",",")).getTime(); 
    //alert(tPA+"||"+tF);
   // var tDaySQL11 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";
    
    var sqlid14 = 'CardContInputSql14';
	var mySql14 = new SqlClass();
	mySql14.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql14.addSubPara(tFirstTrailDate);// 指定传入的参数
	mySql14.addSubPara(tPolApplyDate);// 指定传入的参数
	mySql14.setSqlId(sqlid14);// 指定使用的Sql的id
	var tDaySQL11 = mySql14.getString();
    
    var tDays11=easyExecSql(tDaySQL11);
    if(tDays11<0||tDays11>7){
       alert("投保日期不能晚于初审日期，且不能早于初审日期7天，请重新确认！");
       return false;
    }
    //var tDaySQL12 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";
    var sqlid15 = 'CardContInputSql15';
	var mySql15 = new SqlClass();
	mySql15.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql15.addSubPara(tFirstTrailDate);// 指定传入的参数
	mySql15.setSqlId(sqlid15);// 指定使用的Sql的id
	var tDaySQL12 = mySql15.getString();
    
    var tDays12=easyExecSql(tDaySQL12);
    if(tDays12>0){
       alert("初审日期不能晚于录入日期(可以等于录入日期)！");
       return false;
    }
    //职业类别
    var WorkType=fm.OccupationType.value;
    if(WorkType>4){
      alert("被保险人职业不符合投保规则规定，请重新确认(职业类别为5类及5类以上)！");
      return false;
    }
    //被保人未满18岁 受益人必须为父母 31
    var tCount = BnfGrid.mulLineCount ;
    var rBirthday =fm.Birthday.value;
 	 var tRelationToInsured =fm.RelationToInsured.value;
 	 var tAge = getAge(rBirthday);
 	 if(tAge<3||tAge>60){
 	   alert("被保人投保年龄不符合投保规则规定，不能投保!");
 	   return false;
 	 }
 	 if(tAge>=18&&tRelationToInsured=="30"){
 	   alert("投保人与被保险人关系不符合投保规则规定，不能投保!");
 	   return false;
 	 }
 	 //如被保险人不满18周岁，投保人与被保人关系必须选择为“父母”
 	 if(tAge<18&&tRelationToInsured!="31"){
 	 	  alert("投保人与被保险人关系不符合投保规则规定，不能投保");
         return false;
 	 }
    for(i=0;i<BnfGrid.mulLineCount;i++){
    	var tBnfRelation =BnfGrid.getRowColData(i,6);
    	var tBnfName = BnfGrid.getRowColData(i,2);
    	if(tAge<18&&tBnfRelation!="31"){
    		alert("未成年人身故受益人必须为其父母，否则不能投保！");
    		return false;
        }
        //受益人信息
        if(tBnfRelation=="30"){
        	alert("身故受益人与被保险人关系有误，不能投保!");
        	return false;
        }
        if(fm.Name.value==tBnfName){
        	if(!confirm("身故受益人不能为被保险人本人，是否强制保存?")){
        		return false;
        	}
        }
    }
      
      //同一人校验
 	 	var tRelationToInsured =fm.RelationToInsured.value;
 	 	var tAppntName =fm.AppntName.value;
 	 	var tInsuredName =fm.Name.value;
 	 	if(tRelationToInsured=="00"){
 	 	 if(tInsuredName!=tAppntName){
 	 	    alert("投保人与被保人关系为00-本人，但投被保人的姓名不相同，请确认后保存！");
 	 	   return false;
 	 	  }else{
 	 	    //fm.SameFersonFlag.value="Y";
 	 	  }
 	 	}
      //身份证校验
      if(checkidtype()==false) return false;
      
      //09-08-24 新保险法相关需求 联系电话不能为空
      if(fm.AppntPhone.value==""||fm.AppntPhone.value==null){
   	   alert("联系电话不能为空！");
   	   return false;
      }
      //09-08-28 新保险法需求
      if(fm.ManageCom.value==""||fm.ManageCom.value==null){
   	   alert("管理机构不能为空！");
   	   return false;
      }
}
/**
 * 校验311603--阳光旅程
 * */
function check311603(){
	//增加计算保单生效日期
	 //var tForCValiDateSql = "select  to_char(trunc(adddate(to_date('"+fm.PolApplyDate.value+"','yyyy-mm-dd'),7)))   from   dual";
	 var sqlid16 = 'CardContInputSql16';
		var mySql16 = new SqlClass();
		mySql16.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		mySql16.addSubPara(fm.PolApplyDate.value);// 指定传入的参数
		mySql16.setSqlId(sqlid16);// 指定使用的Sql的id
		var tForCValiDateSql = mySql16.getString();
	 
	 var tCValiDate = easyExecSql(tForCValiDateSql);
	 fm.CValiDate.value = tCValiDate;
//	var tAge =fm.InsuredAppAge.value;
	var tAge = getAge(fm.Birthday.value);
	fm.InsuredAppAge.value=tAge;
	if(fm.SaleChnl.value=="03"&&(fm.AgentCom.value==null||fm.AgentCom.value=="")){
		alert("销售渠道为“03-银行代理”则“代理机构”必须录入！");
	    return false;
	}
    var tCount = BnfGrid.mulLineCount ;
 	for(i=0;i<BnfGrid.mulLineCount;i++){
 		var tBnfName =BnfGrid.getRowColData(i,2);
    	var tBnfRelation =BnfGrid.getRowColData(i,6);
    	if((tBnfName!=null&&tBnfName!="")||(tBnfRelation!=null&&tBnfRelation!="")){
    		if(tBnfName==null||tBnfName==""||tBnfRelation==null||tBnfRelation==""){
    			alert("受益人信息录入不完整!");
    			return false;
    		}
    	}
    	if(tBnfRelation=="30"){
    		alert("身故受益人与被保险人关系有误，不能投保!");
    		return false;
    	}
    	if(fm.Name.value==tBnfName){
        	if(!confirm("身故受益人不能为被保险人本人，是否强制保存?")){
        		return false;
        	}
        }
//    	if(tAge<18&&tBnfRelation!="03"&&tBnfRelation!="04"){
//    		alert("未成年人身故受益人必须为其父母，否则不能投保！");
//    		return false;
//    	}
 	}
    //投保日期不能早于录入日期、不能晚于录入日期5天以上
 	var tFirstTrailDate = fm.FirstTrailDate.value;
 	var tPolApplyDate = fm.PolApplyDate.value;
    var tF =new Date(tFirstTrailDate.replace("-",",")).getTime(); 
    //alert(tPA+"||"+tF);
    //var tDaySQL11 ="select  to_char(trunc(datediffto_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date('"+tPolApplyDate+"','yyyy-mm-dd'))))   from   dual";
    var sqlid17 = 'CardContInputSql17';
	var mySql17 = new SqlClass();
	mySql17.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql17.addSubPara(tFirstTrailDate);// 指定传入的参数
	mySql17.addSubPara(tPolApplyDate);
	mySql17.setSqlId(sqlid17);// 指定使用的Sql的id
	var tDaySQL11 = mySql17.getString();
    
    var tDays11=easyExecSql(tDaySQL11);
    if(tDays11<0||tDays11>7){
       alert("投保日期不能晚于初审日期，且不能早于初审日期7天，请重新确认！");
       return false;
    }
    //var tDaySQL12 ="select  to_char(trunc(datediff(to_date('"+tFirstTrailDate+"','yyyy-mm-dd'),to_date(substr(now(),1,10),'yyyy-mm-dd'))))   from   dual";
    
    var sqlid18 = 'CardContInputSql18';
	var mySql18 = new SqlClass();
	mySql18.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql18.addSubPara(tFirstTrailDate);// 指定传入的参数
	mySql18.setSqlId(sqlid18);// 指定使用的Sql的id
	var tDaySQL12 = mySql18.getString();
    var tDays12=easyExecSql(tDaySQL12);
    if(tDays12>0){
       alert("初审日期不能晚于录入日期(可以等于录入日期)！");
       return false;
    }
    //同一被保险人只能购买一份，若业务员、代理机构、管理机构、被保险人姓名、性别、年龄、证件号码均一致，
    //则默认为同一人，保存时系统校验份数，只允许有效保单为一份
    var tAgentCode =fm.AgentCode.value;
    var tAgentCom =fm.AgentCom.value;
    var tManageCom =fm.ManageCom.value;
    var tInsuredName =fm.Name.value;
    var tInsuredSex=fm.Sex.value;
    var tIDNo =fm.IDNo.value;
    var tInsuredAge =fm.InsuredAppAge.value;
    var tMult =fm.Mult.value;
//    var tCountSQL =" select count(1) from lcpol a where agentcode ='"+tAgentCode+"'  and managecom='"+tManageCom+"' "
//                  +" and insuredname ='"+tInsuredName+"' and insuredsex='"+tInsuredSex+"' and insuredappage='"+tInsuredAge+"' "
//                  +" and exists (select 1 from lcinsured where idno ='"+tIDNo+"' and prtno =a.prtno)";
//    if(tAgentCom==null||tAgentCom ==""){
//		tCountSQL =tCountSQL+" and agentcom is null ";         
//    }
//    var tCountMult =easyExecSql(tCountSQL);
//    if(tCountMult>=1||tMult>1){
//    	alert("同一被保险人只能购买一份!");
//    	return false;
//    }
    //身份证校验
    //if(checkidtype()==false) return false;
    //var rBirthday =fm.Birthday.value;
	var tRelationToInsured =fm.RelationToInsured.value;
	//var tAge = getAge(rBirthday);
	if(tAge<18||tAge>70){
		alert("被保人投保年龄不符合投保规则规定，不能投保!");
	   	return false;
	}
	//如被保险人不满18周岁，此项必须选择为“父亲、母亲”  
	//2009-08-27此处修改为  31-父母
	//最终定下的卡单需求 去掉下面的校验
//	if(tAge<18&&tRelationToInsured!="31"){
//		alert("投保人与被保险人关系不符合投保规则规定，不能投保!");
//	 	return false;
//	}
	if(tAge>=18&&tRelationToInsured=="30"){
		alert("投保人与被保险人关系不符合投保规则规定，不能投保");
	 	return false;
	}
	//同一人校验
	var tRelationToInsured =fm.RelationToInsured.value;
	var tAppntName =fm.AppntName.value;
	var tInsuredName =fm.Name.value;
	if(tRelationToInsured=="00"){
		if(tInsuredName!=tAppntName){
			alert("投保人与被保人关系为00-本人，但投被保人的姓名不相同，请确认后保存！");
	 	   	return false;
		}else{
			//fm.SameFersonFlag.value="Y";
	 	    //alert("fm.SameFersonFlag.value:"+fm.SameFersonFlag.value);
	 	}
	}
	//校验身份证号码，但能够强行保存
	if(checkidtype()==false) return false;
}

function InitBnfType(){
   var tCount =BnfGrid.mulLineCount()-1;
   //alert(tCount);
   BnfGrid.setRowColData(tCount,1,"1");
}

//311603 通过年龄计算被保人生日
function calBirthday(){
   var tAge =fm.InsuredAppAge.value;
   //var tSQL="select year(now())-concat(concat('"+tAge+"','-'),substr(now(),6,6)) from dual";
   
    var sqlid19 = 'CardContInputSql19';
    var mySql19 = new SqlClass();
	mySql19.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql19.addSubPara(tAge);// 指定传入的参数
	mySql19.setSqlId(sqlid19);// 指定使用的Sql的id
	var tSQL = mySql19.getString();
   fm.Birthday.value =easyExecSql(tSQL);
   //alert(fm.Birthday.value);
}

//进行两次输入的校验
function confirmSecondInput1(aObject,aEvent)
{
	{
		if(theFirstValue!="")
		{
			theSecondValue = aObject.value;
			if(theSecondValue=="")
			{
				alert("请再次录入！");
				aObject.value="";
				aObject.focus();
				return;
			}
			if(theSecondValue==theFirstValue)
			{
				aObject.value = theSecondValue;
				theSecondValue="";
				theFirstValue="";
				return;
			}
			else
			{
				alert("两次录入结果不符，请重新录入！");
				theFirstValue="";
				theSecondValue="";
				aObject.value="";
				aObject.focus();
				return;
			}
		}
		else
		{
			theFirstValue = aObject.value;
			theSecondValue="";
			if(theFirstValue=="")
			{
				return;
			}
			aObject.value="";
			aObject.focus();
			return;
		}
	}
}
function checkidtype()
{
	if(fm.IDType.value=="")
	{
		//alert("请先选择证件类型！");
		//fm.IDNo.value="";
    }
//   alert("+++++++++"+fm.Name.value+"++++++++");
if(fm.Name.value=="")
{
	return false;
	}
  var iIdNo = fm.IDNo.value;
    if(fm.IDType.value=="0")
	{
		if ((iIdNo.length!=15) && (iIdNo.length!=18))
        {
//            strReturn = "证件号码位数有误，请重新输入!";
//            alert(strReturn);
            if(!confirm("证件号码位数有误, 是否强制保存？")){
            	fm.IDNo.focus();
            	fm.IDNo.select();
            	return false;
            }
        } 
        if(getBirthdaySexByIDNo(iIdNo)==false) return false;
        //getAge();
        //getallinfo(); 	     
	}
    
    return true;
}

/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('IDType').value=="0")
	{ 
		document.all('CalBirthday').value=getBirthdatByIdNo(iIdNo);
		document.all('CalSex').value=getSexByIDNo(iIdNo);
//		alert("计算：CalBirthday-"+fm.CalBirthday.value+";CalSex-"+fm.CalSex.value);
//		alert("录入：Birthday-"+fm.Birthday.value+";Sex-"+fm.Sex.value);
		var tCardType =fm.RiskCode.value;
//		if(tCardType!=null&&tCardType=="311603"){
		  //只校验性别
			if(document.all('CalSex').value!=fm.Sex.value){
			   if(!confirm("身份证号码与性别不一致! 是否强制保存？")){
			      return false ;
			   }
			}
//		}else{
			if(document.all('CalBirthday').value!=fm.Birthday.value||document.all('CalSex').value!=fm.Sex.value){
		 	  if(!confirm("身份证号码与性别或出生日期不一致! 是否强制保存？")){
			      return false ;
			   }
			}
//		}
		
		//if(document.all('Sex').value=="0")
		//  document.all('SexName').value="男";
		//else
		//	{
		//	 document.all('SexName').value="女";
		//	}
	}
	return true;
}



function getAge(tBirthday)
{
    var BirthDay=tBirthday;
    var Age ="";
	if(tBirthday=="")
	{
		return;
	}
	if(tBirthday.indexOf('-')==-1)
	{
		var Year =tBirthday.substring(0,4);
		var Month =tBirthday.substring(4,6);
		var Day =tBirthday.substring(6,8);
		BirthDay = Year+"-"+Month+"-"+Day;
	}
	if(calAge(BirthDay)<0)
	{
		alert("出生日期只能为当前日期以前");
		//fm.AppntAge.value="";
		return;
	}
	if(!fm.PolApplyDate||(fm.PolApplyDate.value==null||fm.PolApplyDate.value=="")){
	  Age=calPolAppntAge(BirthDay,fm.CValiDate.value);
	}else{
	  Age=calPolAppntAge(BirthDay,fm.PolApplyDate.value);
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    //tPolApplyDate="2009-03-27";
	//Age=calPolAppntAge(BirthDay,fm.PolApplyDate.value);
  	return Age;
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
function queryAgent()
{
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  }
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //保单号码
    //alert("cAgentCode=="+cAgentCode);
    //如果业务员代码长度为8则自动查询出相应的代码名字等信息
    //alert("cAgentCode=="+cAgentCode);
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
/*   	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' and a.managecom like '"+document.all("ManageCom").value+"%'";*/
   //alert(strSQL);
   	
    var sqlid20 = 'CardContInputSql20';
    var mySql20 = new SqlClass();
	mySql20.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	mySql20.addSubPara(cAgentCode);// 指定传入的参数
	mySql20.addSubPara(document.all("ManageCom").value);// 指定传入的参数
	mySql20.setSqlId(sqlid20);// 指定使用的Sql的id
	var strSQL = mySql20.getString();
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
      //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][3]+"]");
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
}
//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
	fm.AgentCode.value = arrResult[0][0];
	fm.BranchAttr.value = arrResult[0][10];
	fm.AgentGroup.value = arrResult[0][1];
	//alert(12);
	fm.AgentName.value = arrResult[0][3];
	//fm.AgentManageCom.value = arrResult[0][2];
	fm.ManageCom.value=arrResult[0][2];
	//showOneCodeName('comcode','ManageCom','ManageComName');
	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	}
}

//投保人客户号查询按扭事件
function queryAppntNo() 
{
	if (document.all("AppntNo").value == "" )
	{
		showAppnt1();
	}
	else if (loadFlag != "1" && loadFlag != "2")
	{
		alert("只能在投保单录入时进行操作！");
	}
	else
	{
		
		var sqlid21 = 'CardContInputSql21';
	    var mySql21 = new SqlClass();
		mySql21.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		mySql21.addSubPara(document.all("AppntNo").value);// 指定传入的参数
		mySql21.setSqlId(sqlid21);// 指定使用的Sql的id
		 //"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0
		arrResult = easyExecSql(mySql21.getString());
		if (arrResult == null) 
		{
			alert("未查到投保人信息");
			displayAppnt(new Array());
			emptyUndefined();
		}
		else
		{
			displayAppnt(arrResult[0]);
			showAppntCodeName();
			getdetailaddress();
		}
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
    	
    	var sqlid22 = 'CardContInputSql22';
	    var mySql22 = new SqlClass();
		mySql22.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		mySql22.addSubPara(document.all("InsuredNo").value);// 指定传入的参数
		mySql22.setSqlId(sqlid22);// 指定使用的Sql的id
		//"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0
    	arrResult = easyExecSql(mySql22.getString());
        if (arrResult == null)
        {
          alert("未查到被保人信息");
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
          showInsuredCodeName();
        }
        
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
       
       var sqlid23 = 'CardContInputSql23';
	   var mySql23 = new SqlClass();
	   mySql23.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	   mySql23.addSubPara(insuredno);// 指定传入的参数
	   mySql23.addSubPara(fm.ContNo.value);// 指定传入的参数
	   mySql23.addSubPara(insuredno);// 指定传入的参数
	   mySql23.setSqlId(sqlid23);// 指定使用的Sql的id
	   var strhomea = mySql23.getString();
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}

function displayInsuredInfo()
{
	  //alert("asdfasfsf");
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    //try{document.all('InsuredPlace').value= arrResult[0][9]; }catch(ex){};
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
   /* if(arrResult[0][13]!=null&&arrResult[0][13]!=0){
      try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    }
    if(arrResult[0][14]!=null&&arrResult[0][14]!=0){
      try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    }*/
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
    try{document.all('GrpName').value= arrResult[0][41];}catch(ex){};



    //地址显示部分的变动
    try{document.all('InsuredAddressNo').value= "";}catch(ex){};
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
 *  被保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredCodeName()
{
try{
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
//	quickGetName('Province',fm.InsuredProvince,fm.InsuredProvinceName);
//	quickGetName('City',fm.InsuredCity,fm.InsuredCityName);
//	quickGetName('District',fm.InsuredDistrict,fm.InsuredDistrictName);
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
	//quickGetName('incomeway',fm.IncomeWay,fm.IncomeWayName);
	}catch(ex){}
}
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
			var iWidth=1;      //弹出窗口的宽度; 
			var iHeight=1;     //弹出窗口的高度; 
			var iTop = 1; //获得窗口的垂直位置 
			var iLeft = 1;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
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
		//var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
		
		   var sqlid24 = 'CardContInputSql24';
		   var mySql24 = new SqlClass();
		   mySql24.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		   mySql24.addSubPara(PlaceCode);// 指定传入的参数
		   mySql24.addSubPara(PlaceType);// 指定传入的参数
		   mySql24.setSqlId(sqlid24);// 指定使用的Sql的id
		   var strSQL = mySql24.getString();
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
        var sqlid25 = 'CardContInputSql25';
		   var mySql25 = new SqlClass();
		   mySql25.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		   mySql25.addSubPara(arrQueryResult[0][0]);// 指定传入的参数
		   mySql25.setSqlId(sqlid25);// 指定使用的Sql的id
		   //"select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0
        	arrResult = easyExecSql(mySql25.getString());
        	if (arrResult == null)
        	{
        	    alert("未查到投保人信息");
        	}
        	else
        	{
        	   displayInsuredInfo(arrResult[0]);
        	  // showCodeName();
        	}
    }

	mOperate = 0;		// 恢复初态
	//showCodeName();
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
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" ,"",sFeatures);
}
/*********************************************************************
 *  把查询返回的投保人数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
	try{document.all('AppntCustomerNo').value= arrResult[0][0]; }catch(ex){};
	try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
	try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
	try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
	try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
	try{document.all('AppntPlace').value= arrResult[0][9]; }catch(ex){};
	try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
	try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
	try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
	/*if(arrResult[0][13]!=null&&arrResult[0][13]!=0)
	{
		try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
	}
	if(arrResult[0][14]!=null&&arrResult[0][14]!=0)
	{
		try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
	}*/
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
	try{document.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};	
	//alert(arrResult[0][44]);
	try{document.all('RelationToInsured').value= arrResult[0][44];}catch(ex){};	
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

/*********************************************************************
 *  投保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppntCodeName()
{
try{
	quickGetName('vipvalue',fm.AppntVIPValue,fm.AppntVIPFlagname);
	quickGetName('IDType',fm.AppntIDType,fm.AppntIDTypeName);
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	quickGetName('Marriage',fm.AppntMarriage,fm.AppntMarriageName);
	quickGetName('NativePlace',fm.AppntNativePlace,fm.AppntNativePlaceName);
	quickGetName('OccupationCode',fm.AppntOccupationCode,fm.AppntOccupationCodeName);
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
//  quickGetName('province',fm.AppntProvince,fm.AppntProvinceName);
//  quickGetName('city',fm.AppntCity,fm.AppntCityName);
//  quickGetName('district',fm.AppntDistrict,fm.AppntDistrictName);
	//getAddressName('province','AppntProvince','AppntProvinceName');
	//getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	quickGetName('paymode',fm.PayMode,fm.PayModeName);
	quickGetName('paylocation',fm.SecPayMode,fm.SecPayModeName);
	quickGetName('bank',fm.AppntBankCode,fm.AppntBankCodeName);
	quickGetName('bank',fm.SecAppntBankCode,fm.SecAppntBankCodeName);
	//quickGetName('incomeway',fm.IncomeWay0,fm.IncomeWayName0);
	quickGetName('relation',fm.RelationToInsured,fm.RelationToInsuredName);
	}catch(ex){}

}

/********************
**
** 查询投保人地址信息
**
*********************/
function getdetailaddress()
{
	var sqlid26 = 'CardContInputSql26';
	   var mySql26 = new SqlClass();
	   mySql26.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	   mySql26.addSubPara(fm.AppntAddressNo.value);// 指定传入的参数
	   mySql26.addSubPara(fm.AppntNo.value);// 指定传入的参数
	   mySql26.setSqlId(sqlid26);// 指定使用的Sql的id
	   var strSQL = mySql26.getString();
  // var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
   arrResult=easyExecSql(strSQL);
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
   //try{document.all('AppntPhone').value= arrResult[0][12];}catch(ex){};
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

function showAppnt1()
{
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" ,"",sFeatures);
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult ) 
{
	if( arrQueryResult != null ) 
	{
		arrResult = arrQueryResult;
			// 投保人信息
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
		var sqlid27 = 'CardContInputSql27';
		   var mySql27 = new SqlClass();
		   mySql27.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
		   mySql27.addSubPara(arrQueryResult[0][0]);// 指定传入的参数
		   mySql27.setSqlId(sqlid27);// 指定使用的Sql的id
		   //"select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0
			arrResult = easyExecSql(mySql27.getString());
			if (arrResult == null) 
			{
				alert("未查到投保人信息");
			} 
			else 
			{
				displayAppnt(arrResult[0]);
			}
	}
	mOperate = 0;		// 恢复初态
	//showCodeName();
}

function afterSubmit4(FlagStr, content)
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //var tContSQL ="select contno ,proposalno from lcpol where prtno ='"+fm.PrtNo.value+"'";
        var sqlid28 = 'CardContInputSql28';
 	   var mySql28 = new SqlClass();
 	   mySql28.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
 	   mySql28.addSubPara(fm.PrtNo.value);// 指定传入的参数
 	   mySql28.setSqlId(sqlid28);// 指定使用的Sql的id
 	   var tContSQL = mySql28.getString();

        var arrResult = easyExecSql(tContSQL);
        if(arrResult==null||arrResult==""||arrResult=="null"){}else{
           document.all('ProposalContNo').value = arrResult[0][0];
           document.all('ProposalNo').value = arrResult[0][1];
           //alert(document.all('ProposalNo').value);
        }
       // var tt = "select amnt,prem from lcpol where prtno='"+fm.PrtNo.value+"'";
        var sqlid29 = 'CardContInputSql29';
  	   var mySql29 = new SqlClass();
  	   mySql29.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
  	   mySql29.addSubPara(fm.PrtNo.value);// 指定传入的参数
  	   mySql29.setSqlId(sqlid29);// 指定使用的Sql的id
  	   var tt = mySql29.getString();

        var Result = easyExecSql(tt, 1, 0);
        //alert(Result);
        if (Result != null)
        {
            fm.Amnt.value = Result[0][0];
            fm.Prem.value = Result[0][1];
        }
        backInfo();
        //禁止修改一些东西
        try{
        fm.PrtNo.disabled =true;
        fm.ManageCom.disabled =true;
        fm.AppntCustomerNo.disabled =true;
        fm.InsuredNo.disabled=true;
        }catch(ex){}

    }

}
function backInfo(){
  // var tappntSQL =" select b.postaladdress,b.zipcode,b.phone,b.homeaddress,b.homezipcode,b.homephone,a.* from lcappnt a,lcaddress b where a.prtno ='"+fm.PrtNo.value+"' and a.appntno=b.customerno";
   
   var sqlid30 = 'CardContInputSql30';
	   var mySql30 = new SqlClass();
	   mySql30.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
	   mySql30.addSubPara(fm.PrtNo.value);// 指定传入的参数
	   mySql30.setSqlId(sqlid30);// 指定使用的Sql的id
	   var tappntSQL = mySql30.getString();
   var arrResult =easyExecSql(tappntSQL);
  // var tinsurdSQL =" select * from lcinsured  where prtno ='"+fm.PrtNo.value+"'";
   
   var sqlid31 = 'CardContInputSql31';
   var mySql31 = new SqlClass();
   mySql31.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
   mySql31.addSubPara(fm.PrtNo.value);// 指定传入的参数
   mySql31.setSqlId(sqlid31);// 指定使用的Sql的id
   var tinsurdSQL = mySql31.getString();
   var arrResult1 =easyExecSql(tinsurdSQL);
   //投保人
   try{fm.AppntCustomerNo.value = arrResult[0][9];}catch(ex){}
   try{fm.AppntName.value = arrResult[0][11];}catch(ex){}
   try{fm.AppntPhone.value =arrResult[0][2];}catch(ex){}
   try{fm.AppntPostalAddress.value =arrResult[0][0];}catch(ex){}
   try{fm.AppntZipCode.value= arrResult[0][1];}catch(ex){}
   //被保人
   try{fm.InsuredNo.value =arrResult1[0][2];}catch(ex){}
   try{fm.Name.value =arrResult1[0][12];}catch(ex){}
   try{fm.Sex.value =arrResult1[0][13];}catch(ex){}
   try{fm.Birthday.value =arrResult1[0][14];}catch(ex){}
   try{fm.IDType.value =arrResult1[0][15];}catch(ex){}
   try{fm.IDNo.value =arrResult1[0][16];}catch(ex){}
   
   
}

/*********************************************************************
 *  delete事件，当点击“删除”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick2()
{
try{
        fm.PrtNo.disabled =false;
        fm.ManageCom.disabled =false;
        fm.AppntCustomerNo.disabled =false;
        fm.InsuredNo.disabled=false;
        }catch(ex){}
    if (trim(fm.ProposalContNo.value) == "")
    {
        alert("请先保存卡单，再进行删除操作!")
        return;
    }
   // strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='4'";
    
    var sqlid32 = 'CardContInputSql32';
    var mySql32 = new SqlClass();
    mySql32.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
    mySql32.addSubPara(fm.ProposalContNo.value);// 指定传入的参数
    mySql32.setSqlId(sqlid32);// 指定使用的Sql的id
    var strSql = mySql32.getString();
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("该保单不存在，无法进行删除!");
        return;
    }
    else
    {
        var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if (mAction == "")
        {
            //showSubmitFrame(mDebug);
            //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "DELETE";
            document.all('fmAction').value = mAction;
            fm.action = "CardContSave.jsp";
            fm.submit();
            //提交
        }
    }


    //top.close();
}

function checkPrtNo(){
   var tPrtNo =fm.PrtNo.value
   if(tPrtNo!=null&&tPrtNo.length!=14){
       alert("印刷号不为14位，请重新录入!");
       return false;
   }

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
     //对应未选同一人，又打钩的情况
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("投保人与被保人关系不是本人，不能进行该操作！");
    }
    
    if ( fm.SamePersonFlag.checked==true
       //&&fm.RelationToAppnt.value!="00"
       )
    {
      //DivLCInsured.style.display = "none";
      //divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      //fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
      //document.all('DivLCInsured').style.display = "none";
      //divLCInsuredPerson.style.display = "none";
      //divSalary.style.display = "none";

      displayissameperson();
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      //divLCInsuredPerson.style.display = "";
     // divSalary.style.display = "";
      //if(InsuredGrid.mulLineCount==0)
	  //{
		//fm.SequenceNo.value="1";//在第一次录入被保险人时默认为“1－被保险人”
		//fm.RelationToMainInsured.value="00";//与第一被保险人关系:当被保险人客户内部号为“1－被保险人”时，默认为“00－本人”
	  //}
	  //else
	  //{
	  // try{document.all('SequenceNo').value=""; }catch(ex){};
     //  try{document.all('RelationToMainInsured').value=""; }catch(ex){};
	 // }
      try{document.all('InsuredNo').value=""; }catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= "0"; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('InsuredPlace').value= ""; }catch(ex){};
      try{document.all('Marriage').value= "";}catch(ex){};
      try{document.all('MarriageDate').value= "";}catch(ex){};
      try{document.all('Health').value= "";}catch(ex){};
      /*try{document.all('Stature').value= "";}catch(ex){};
      try{document.all('Avoirdupois').value= "";}catch(ex){};*/
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
     /* try{document.all('IncomeWay').value=  "";}catch(ex){};
      try{document.all('Income').value=  "";}catch(ex){};*/

    }
}

function displayissameperson()
{
  try{fm.SequenceNo.value="1";}catch(ex){};//在第一次录入被保险人时默认为“1－被保险人”
  try{fm.RelationToMainInsured.value="00";}catch(ex){};//与第一被保险人关系:当被保险人客户内部号为“1－被保险人”时，默认为“00－本人”
//  try{fm.RelationToAppnt.value="00";}catch(ex){};
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
　try{document.all('InsuredPlace').value= document.all( "AppntPlace" ).value;      	  }catch(ex){};
　try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
　try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
　try{document.all('Health').value= document.all( "AppntHealth" ).value;              }catch(ex){};
　//try{document.all('Stature').value= document.all( "AppntStature" ).value;            }catch(ex){};
　//try{document.all('Avoirdupois').value= document.all( "AppntAvoirdupois" ).value;    }catch(ex){};
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
  /*try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('Income').value= document.all("Income0").value;}catch(ex){};
  try{document.all('Stature').value= document.all("AppntStature").value;}catch(ex){};
  try{document.all('Avoirdupois').value= document.all("AppntAvoirdupois").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');*/

/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
  }
***************************/
}

function getcodedata2()
{
	/*var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and lmriskapp.PolType ='C' "
	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
	         +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+document.all('ManageCom').value+"' "
	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode) in ('C') order by RiskCode";*/
	
	var sqlid33 = 'CardContInputSql33';
    var mySql33 = new SqlClass();
    mySql33.setResourceName("card.CardContInputSql"); // 指定使用的properties文件名
    mySql33.addSubPara(fm.sysdate.value);// 指定传入的参数
    mySql33.addSubPara(fm.sysdate.value);// 指定传入的参数
    mySql33.addSubPara(fm.sysdate.value);// 指定传入的参数
    mySql33.addSubPara(document.all('ManageCom').value);// 指定传入的参数
    mySql33.setSqlId(sqlid33);// 指定使用的Sql的id
    var sql = mySql33.getString();
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("查询险种代码:",sql);
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
    //alert(tCodeData);
    document.all("RiskCode").CodeData=tCodeData;
	
}

function InitBnfType(parm1,parm2){
  var tCount =BnfGrid.mulLineCount -1;
  BnfGrid.setRowColData(tCount,1,"1");
}
