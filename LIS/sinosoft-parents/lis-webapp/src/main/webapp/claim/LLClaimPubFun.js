/**
 * 程序名称: LLClaimPubFun.js 
 * 程序功能: 理赔前台公用方法集 
 * 创建日期：2005-12-26
 */
var mySql = new SqlClass();
/*****************************************************************************************
 * 功能：日期格式转化函数
 * 描述：只允许输入YYYYMMDD和YYYY-MM-DD格式的日期，对YYYYMMDD日期可自动转化为YYYY-MM-DD格式；
 *       对非数字字符等有校验
 * 返回：YYYY-MM-DD格式的日期
 * 创建: 赵日新  2005-12-26 10:02
******************************************************************************************/
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

/*****************************************************************************************
 * 功能：查询赔案的一些重要标志
 * 描述：显示赔案是否做过调查\呈报\预付\二核，目前只能在理赔主界面使用
 * 返回：无
 * 创建: 周磊  2005-12-27 14:02
******************************************************************************************/
function QueryClaimFlag()
{
    var tFlag = "";
    
    //调查
    /*
    var strSql1 = "select count(*) from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";
               */
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql1");
	mySql.addSubPara(fm.RptNo.value );
    var tFiniFlag = easyExecSql(mySql.getString());
    if (tFiniFlag != '0')
    {
        tFlag = tFlag + "调查";
    }
    
    //呈报
    /*
    var strSql2 = "select count(*) from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";
     */          
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql2");
	mySql.addSubPara(fm.RptNo.value );
	
    var tSubState = easyExecSql(mySql.getString());
    if (tSubState != '0')
    {
        if (tFlag == "")
        {
            tFlag = tFlag + "呈报";
        }
        else
        {
            tFlag = tFlag + "、呈报";
        }
    }
    
    //预付
    /*
    var strSql3 = "select count(*) from llprepayclaim where "
                + " clmno = '" + fm.RptNo.value + "'";
     */           
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql3");
	mySql.addSubPara(fm.RptNo.value );
	
    var tPrey = easyExecSql(mySql.getString());
    if (tPrey != '0')
    {
        if (tFlag == "")
        {
            tFlag = tFlag + "预付";
        }
        else
        {
            tFlag = tFlag + "、预付";;
        }
    }
    
    //二核
    /*
    var strSql4 = "select count(*) from LLCUWBatch where "
                + " caseno = '" + fm.RptNo.value + "'";
    */            
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql4");
	mySql.addSubPara(fm.RptNo.value );
	
    var tState = easyExecSql(mySql.getString());
    if (tState != '0')
    {
        if (tFlag == "")
        {
            tFlag = tFlag + "二核";
        }
        else
        {
            tFlag = tFlag + "、二核";;
        }
    }
    
    fm.ClaimFlag.value = tFlag;
}

/*****************************************************************************************
 * 功能：校验是否有未核销的赔案产生的应收信息
 * 描述：主要是二核加费产生的应收信息
 * 参数：赔案号tClmNo
 * 返回：ture or false
 * 创建: 周磊  2006-1-6 10:02
******************************************************************************************/
function checkLjspay(tClmNo)
{
   /* var strQSql = "select count(1) from ljspay where othernotype = '9' and getnoticeno like 'CLMUW%%' "
                + " and otherno = '" + tClmNo + "'";*/
   // var strQSql = "select count(1) from ljspay   where  otherno ='"+tClmNo+"' ";

	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql5");
	mySql.addSubPara(tClmNo);
	
    var tCount = easyExecSql(mySql.getString());

    if (tCount == null)
    {
        alert("查询加费信息出错！");
        return false;
    }
    else if (tCount == 0)
    {
        return true; //没有对应的应收
    }
    else
    {
        alert("存在核保加费的应收信息未核销！");
        return false; //存在对应的应收
    }
}

/*********************************************************************************************
 * 功能：对邮编的校验,要求邮编必须是6位数字
 * 描述：主要在录入“报案人邮编”时使用
 * 参数：邮编tPostCode
 * 返回：true/false
 * 创建：niuzj,2006-01-12
**********************************************************************************************/
function checkPostCode(tPostCode)
{
	  var ttPostCode = tPostCode.value;
	  if(ttPostCode == null || ttPostCode == "")
	  {
		   return true;
	  }
    else
    {
  	   if(ttPostCode.length == 6 && isInteger(ttPostCode))
  	   {
  	   	  return true;
  	   }
  	   else
  	   {
  	   	  alert("输入的邮编有误,请重新输入!");
  	   	  return false;
  	   }
    }
}


/*********************************************************************************************
 * 功能：防止同一用户同时打开两个窗口操作同一个赔案可能产生的错误。
 * 描述：用在有数据提交的操作前检验。补充说明，如只在主界面的js文件中添加检验，也是有漏洞的：
         在赔案状态改变之前用户已将分支功能的页面打开，而后在另一主界面窗口操作改变赔案状态，
         再回到先前已打开的分支界面中操作，检验失效。要解决此问题，需在分支界面的js中也加判断。
 * 参数：tClmNo赔案号（团体为分案号），tClmState1,tClmState2赔案状态（允许只使用一个）。
 * 返回：true/false
 * 创建：zhaorx 2006-11-22
**********************************************************************************************/
function KillTwoWindows(tClmNo,tClmState1,tClmState2)
{
    //var tSQLF = " select clmstate from llregister where rgtno = '"+tClmNo+"'";
    
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql6");
	mySql.addSubPara(tClmNo);
	
    var tClmState = easyExecSql(mySql.getString());  
    if(tClmState==null)//赔案处于立案保存之前，报案确认未定 
    {   
			  //var tSQLS   = " select rgtobj from llreport where rptno='"+tClmNo+"' ";
			  
			  	mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimPubFunSql");
				mySql.setSqlId("LLClaimPubFunSql7");
				mySql.addSubPara(tClmNo);
	
			  var tRgtObj = easyExecSql(mySql.getString());          
			  if(tRgtObj=='1')//个险
			  {/*
			  	  var tSQLT1 = " select activityid from lwmission where activityid='0000005005' "
			  	             + " and missionprop1='"+tClmNo+"' ";
			  	*/             
			    mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimPubFunSql");
				mySql.setSqlId("LLClaimPubFunSql8");
				mySql.addSubPara(tClmNo);
	        //prompt("防止同一用户同时打开两个窗口操作同一个赔案可能产生的错误",tSQLT1);
            var tActD1 = easyExecSql(mySql.getString());
            if(tActD1==null)//认为已报案确认，案件状态同立案
            {
            	  tClmState="20";
            }
            else//工作流存在，没有报案确认
            {
              	tClmState="10";
            } 			  	             
			  }
			  if(tRgtObj=='2')//团险
			  {
			  /*
			  	  var tSQLT2 = " select activityid from lwmission where activityid='0000005205' and exists "
			  	             + " (select 'X' from llreport where missionprop1=trim(rgtobjno) and rptno='"+tClmNo+"') ";
            */
            	mySql = new SqlClass();
				mySql.setResourceName("claim.LLClaimPubFunSql");
				mySql.setSqlId("LLClaimPubFunSql9");
				mySql.addSubPara(tClmNo);
				
            var tActD2 = easyExecSql(mySql.getString()); 	
            if(tActD2==null)
            {
            	  tClmState="20";
            }
            else
            {
              	tClmState="10";
            }            		  	
			  }
    } 
	  if(tClmState1!=tClmState&&tClmState2!=tClmState)
	   {
	    	 alert("赔案状态已发生变化，请重新登陆后操作！");
	    	 return false;
	   }    
    return true;      
}

/*****************************************************************************************
 * 功能：校验是否医院所在国家中选定的内容
 * 创建: yhy  2011-5-23 10:02
******************************************************************************************/

function showCountry(){
   if(fm.hospitalContr1.value=='4'){   
      document.getElementById('DivotherCountry').style.display='';         
   }else{
   	  document.getElementById('DivotherCountry').style.display='none';  	
   }
   if(fm.hospitalContr2.value=='4'){   
      document.getElementById('otherCountryDiv').style.display='';         
   }else{
   	  document.getElementById('otherCountryDiv').style.display='none';  	
   }
   if(fm.hospitalContr3.value=='4'){   
      document.getElementById('otherCountryDiv1').style.display='';         
   }else{
   	  document.getElementById('otherCountryDiv1').style.display='none';  	
   }
}

/*********************************************************************************************
 * 功能：对电话的校验,要求电话至少8位数值
 * 描述：主要在录入“报案人电话”时使用
 * 参数：邮编tRptorPhone
 * 返回：true/false
 * 创建：yhy,2011-06-13
**********************************************************************************************/
function checkPhone(tRptorPhone)
{
	  var tRptorPhone1 = tRptorPhone.value;
	  if(tRptorPhone1 == null || tRptorPhone1 == "")
	  {
		   return true;
	  }
    else
    {
  	   if(tRptorPhone1.length >= 8 && isInteger(tRptorPhone1) && tRptorPhone1.length <= 20)
  	   {
  	    
  	   	  return true;
  	   }
  	   else
  	   {
  	   	  alert("输入的电话有误,请重新输入!");
  	   	  tRptorPhone.value = "";
  	   	  return false;
  	   }
    }
}

//判断字符串是否为空
function isnull(str){
	return str == "" || str == null;
}
//参数为出生年月,如1980-5-9 
function getAge(birth){
    var nowYear = mCurrentDate.substring(0,4);	
    var nowMonth = mCurrentDate.substring(5,7);
    var nowDate = mCurrentDate.substring(8,10);
    var oneYear = birth.substring(0,4);
    var oneMonth = birth.substring(5,7);
    var oneDate = birth.substring(8,10);
    var age = parseInt(nowYear) - parseInt(oneYear);
    if(parseInt(nowMonth) < parseInt(oneMonth) 
    		|| (parseInt(nowMonth) == parseInt(oneMonth) && nowDate < oneDate)) {
    	age--;
    }
	if (age <= 0){
        age = 0
    }
    return age;
}

//中间跳转，当codeNo值为空时，清空codeName的值
function getCodeName(strCode, showObjCode, showObjName){
	try{
		if(isnull(document.all(showObjCode).value)){
			document.all(showObjName).value = "";
		}else{
			showOneCodeName(strCode, showObjCode, showObjName);
		}
	}catch(e){
	}
}

//取消ie11部分输入框单击显示历史输入信息功能
/*****************************************************************
 * BEGIN
 *****************************************************************/
if(document.attachEvent){
	window.attachEvent("onload",dealAutoComplete);
}else{
	window.addEventListener("load",dealAutoComplete,false);
}
document.getElementsByClassName = function(className, oBox) {
	// 适用于获取某个HTML区块内部含有某一特定className的所有HTML元素
	this.d = oBox || document;
	var children = this.d.getElementsByTagName('*') || document.all;
	var elements = new Array();
	for (var ii = 0; ii < children.length; ii++) {
		var child = children[ii];
		var classNames = child.className.split(' ');
		for (var j = 0; j < classNames.length; j++) {
			if (classNames[j] == className) {
				elements.push(child);
				break;
			}
		}
	}
	return elements;
}
function dealAutoComplete(){
	var tElement = document.getElementsByClassName("codeno");
	for(var i=0;i<tElement.length;i++){
		tElement[i].setAttribute('autocomplete','off');
	}
}
//提交后信息返回提示框
function showAfterInfo(FlagStr,content,Flag){
	var tFlag = FlagStr.substring(0,1);
	tFlag = tFlag=="S"?"S":"F";
	var urlStr="../common/jsp/MessagePage.jsp?picture="+tFlag+"&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    return false;
}
//提交信息框显示
function showBeforeInfo(){
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    return window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
}
//查询对象名称
function getObName(tCode, tName,tSqlId){
	if (isnull(document.all(tCode).value)) {
		document.all(tName).value = "";
		return;
	}
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId(tSqlId);
	mySql.addSubPara(document.all(tCode).value);

	var tICDName = easyExecSql(mySql.getString());
	if (tICDName) {
		document.all(tName).value = tICDName;
	}
}
//查询疾病类型名称
function getResultName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql10";
	getObName(tCode, tName,tSqlId);
}
//查询治疗方式名称
function getCureName(tCode,tName){
	var tSqlId = "LLClaimPubFunSql11";
	getObName(tCode, tName,tSqlId);
}

//查询治疗医院名称
function getHospitalName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql12";
	getObName(tCode, tName,tSqlId);
}
//查询治疗医生名称
function getDoctorName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql13";
	getObName(tCode, tName,tSqlId);
}

//查询城市名称
function getCityName(tCode, tName,tSqlId) {
	var tSqlId = "LLClaimPubFunSql15";
	getObName(tCode, tName,tSqlId);
}

//查询区名称
function getAreaName(tCode, tName) {
	var tSqlId = "LLClaimPubFunSql16";
	getObName(tCode, tName,tSqlId);
}

//显示疾病查询页面
function showDisease(tCode, tName){
	var strUrl = "LLQueryDiseaseMain.jsp?code=" + tCode + "&name=" + tName;
	openPage(strUrl);
}

//显示治疗方式查询页面
function showCure(tCode, tName){
	var strUrl = "LLQueryCureMain.jsp?code=" + tCode + "&name=" + tName;
	openPage(strUrl);
}

//增加医生查询页面
function showDoctor(tCode, tName) {
	var strUrl = "LLColQueryDoctorMain.jsp?codeno=" + tCode + "&codename="
			+ tName;
	openPage(strUrl);
}

//增加核保对象查询页面
function showTransfer(tCode,tName){
	var strUrl = "LLColQueryTransferMain.jsp?codeno=" + tCode +"&codename=" + tName;
	openPage(strUrl);
}

//显示医院查询页面
function showHospital(tCode, tName) {
	var strUrl = "LLColQueryHospitalInput.jsp?codeno=" + tCode + "&codename=" + tName;
	openPage(strUrl);
}

//设置全部按钮禁用或启用 tFlag=true 禁用 tFlag=false 启用
function setAllButton(tFlag){
	var elementsNum = 0;// FORM中的元素数
	for (elementsNum = 0; elementsNum < fm.elements.length; elementsNum++) {
		if (fm.elements[elementsNum].type == "button") {
			fm.elements[elementsNum].disabled = tFlag;
		}
	}
}

//弹出页面
function openPage(strUrl){
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	
	var win = window.open(strUrl,"",'top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	win.resizeTo(intPageWidth,intPageHeight);
}

//案件用户变更校验
function userChange(activityid,processid,clmNo,user){
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPubFunSql");
	mySql.setSqlId("LLClaimPubFunSql14");
	mySql.addSubPara(clmNo);
	mySql.addSubPara(activityid);
	mySql.addSubPara(processid);
	var nowUser = easyExecSql(mySql.getString());
	if(nowUser){
		if(user==nowUser){
			return false;
		}
	}
	alert("操作用户已改变，请重新登录后操作！");
	return true;
}

/**
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * mulline排序方法 　　　　BEGIN
 * @param tsortturnPage
 * @param i
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 */
function gridOrderByName(colName,cObjInstance){
	var tObjInstance = cObjInstance;
	var sortTurnPage = tObjInstance.SortPage;
	try{
		if(sortTurnPage == null || sortTurnPage == "") throw "init";
		for(i=1;i<tObjInstance.arraySaveOra.length;i++){
			if(colName == tObjInstance.arraySaveOra[i][0]){sortData(sortTurnPage,i);break;}
		}
	}catch(ex){
		switch (ex){
			case "init":alert("请先查询！");break;
			default:alert("在 MulLine.js --> OrderByName 函数中发生异常：" +  ex);break;
		}
	}
}
function sortData(tsortturnPage,i){
    var sortturnPage =tsortturnPage;
    allowsortcol=i-1;//为什么不能用page取得？不归属与turnpageclass对象...?
    if(sortturnPage.sortdesc[allowsortcol]!="asc"){
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.sort(sortDataParam);
        sortturnPage.sortdesc[allowsortcol]="asc";
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++){
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }else{
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.reverse();
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++){
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    //设置查询起始位置
    sortturnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    var arrDataSet = sortturnPage.getData(sortturnPage.arrDataCacheSet, sortturnPage.pageIndex, sortturnPage.pageLineNum);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, sortturnPage.pageDisplayGrid, sortturnPage);
    //控制是否显示翻页按钮 ,排序后允许翻页 add by jnn
    //if (cTurnPage.queryAllRecordCount > cTurnPage.pageLineNum)
    if(sortturnPage.showTurnPageDiv==1){
        try{
            window.document.all(sortturnPage.pageDivName).style.display = "";
        }
        catch (ex) {}
    }else{
    	try{
            window.document.all(sortturnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
    }
    if(sortturnPage.showTurnPageDiv==1){
		try{
			sortturnPage.pageDisplayGrid.setPageMark(sortturnPage);
		}
		catch(ex){}
  	}
}

function sortDataParam(x,y){
	var sort1 = x[allowsortcol];
	var sort2 = y[allowsortcol];
	if(!isNaN(sort1) && !isNaN(sort2)
			&& sort1.split('.')[0].length != sort2.split('.')[0].length){
		if (sort1.split('.')[0].length > sort2.split('.')[0].length){
			for(var i=0;i<(sort1.split('.')[0].length-sort2.split('.')[0].length);i++){
				sort2 = "0" + sort2;
			}
	    }else{
	    	for(var i=0;i<(sort2.split('.')[0].length-sort1.split('.')[0].length);i++){
	    		sort1 = "0" + sort1;
			}
	    }
	}
    if (sort1 > sort2){
        return 1;
    }else if (sort1 < sort2){
        return -1;
    }else{
        return 0;
    }
    //根据二维数组的第三列的第一个字母的ASCII码来降序排序
}

/**
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * END
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 */

//改变上一个指定父节点元素的class  
/** 
 * tElement:子元素  
 * tElementName：父元素Name 
 * tElementClass：父元素改变后的className 
 */
function setPNodeClassByName(tElement,tElementName,tElementClass){
	//给定最多循环10次
	for(var i=0;tElement.parentNode != null || i < 10;i++){
		tElement = tElement.parentNode;
		if(tElement.nodeName == tElementName){
			tElement.className = tElementClass;
			return;
		}
	}
}

//校验账号合法
function checkAcc(tEle){
	if(isnull(tEle.value)){
		return;
	}
	if(!/^\d{16,19}$/.test(tEle.value)){
		alert("账号输入错误，请重新输入！");
		tEle.value = "";
		tEle.focus();
	}
}

//将空转化为空字符串
function nullToEmpty(string){
	if ((string == "null") || (string == "undefined")){
		string = "";
	}
	return string;
}

//日期控件显示
document.getElementsByClassName = function(className, oBox) {
	// 适用于获取某个HTML区块内部含有某一特定className的所有HTML元素
	this.d = oBox || document;
	var children = this.d.getElementsByTagName('*') || document.all;
	var elements = new Array();
	for (var ii = 0; ii < children.length; ii++) {
		var child = children[ii];
		var classNames = child.className.split(' ');
		for (var j = 0; j < classNames.length; j++) {
			if (classNames[j] == className) {
				elements.push(child);
				break;
			}
		}
	}
	return elements;
}

function formatDatePicker(element){ 
	formatDate(element);
}
function formatDate(element){
    try{
        if (element != null){
            var sCurrentValue = element.value;
            if (sCurrentValue != null && sCurrentValue != "") {
                if ((sCurrentValue.length != 8 && sCurrentValue.length != 10) || (!isDate(sCurrentValue) && !isDateI(sCurrentValue) && !isDateN(sCurrentValue))){
                    var oldClass = element.className;
                    element.className = "warndate";
                    alert("日期格式错误，标准日期格式：YYYY-MM-DD");
                    element.className = oldClass;
                    element.value = "";
                    element.focus();
                }
                else if (isDateI(sCurrentValue))
                {
                    //日期格式 yyyy/mm/dd
                    element.value = replace(sCurrentValue, "/", "-");
                    if (!isDate(element.value)) {
                        var oldClass = element.className;
                        element.className = "warndate";
                        alert("日期格式错误，标准日期格式：YYYY-MM-DD");
                        element.className = oldClass;
                        element.value = "";
                        element.focus();
                    }
                }
                else if (isDateN(sCurrentValue)){
                    element.value = sCurrentValue.substring(0, 4) + "-" + sCurrentValue.substring(4, 6) + "-" + sCurrentValue.substring(6, 8);
                    if (!isDate(element.value)) {
                        var oldClass = element.className;
                        element.className = "warndate";
                        alert("日期格式错误，标准日期格式：YYYY-MM-DD");
                        element.className = oldClass;
                        element.value = "";
                        element.focus();
                    }
                }
            }
        }
    }
    catch (ex) {}
}

if (window.attachEvent){ 
	window.attachEvent('onload',initDatePicker);
}else{
	window.addEventListener('load',initDatePicker,false);
}

function initDatePicker(){
	if(fm.all){
		return;
	}
	var DatePickers = document.getElementsByClassName('multiDatePicker');
	var i=0;  
	while(i<DatePickers.length){
		DatePickers[i].setAttribute("onblur", "formatDatePicker(this);");
		if (DatePickers[i].getAttribute("DatePickerFlag") == null){
			DatePickers[i].setAttribute("DatePickerFlag", "1");
			DatePickers[i].maxLength=10;
			DatePickers[i].insertAdjacentHTML("afterEnd", "<img   src='../common/images/calendar.gif' vspace='1' onclick='calendar(" + DatePickers[i].name + ",\"" + DatePickers[i].id + "\")'>");
		}
		DatePickers[i].className = "coolDatePicker"; 
		i++;
	}
	var DatePickers = document.getElementsByClassName('coolDatePicker');
	var i=0;  
	while(i<DatePickers.length){
		DatePickers[i].setAttribute("onblur", "formatDatePicker(this);");
		if (DatePickers[i].getAttribute("DatePickerFlag") == null){
			DatePickers[i].setAttribute("DatePickerFlag", "1");
			DatePickers[i].maxLength=10;
			DatePickers[i].insertAdjacentHTML("afterEnd", "<img   src='../common/images/calendar.gif' vspace='1' onclick='calendar(" + DatePickers[i].name + ",\"" + DatePickers[i].id + "\")'>");
		}
		i++;
	}
}

//提交数据后返回提示框
function myPromptBox(FlagStr,content,Flag){
	var dheight = 0;
	var dWidth = 0;
	
 	var sh=document.body.scrollHeight;//滚动条的高度
	var ch =document.body.clientHeight;	
	dheight = sh>ch? sh:ch;
	
	var sw = document.body.scrollWidth;
	var cw = document.body.clientWidth;
	dWidth = sw>cw? sw:cw; 
	
	var bDiv = document.createElement('div'); 
	bDiv.id = 'promptmask';
	bDiv.className = "datagrid-mask";
	bDiv.style.width = dWidth;
	bDiv.style.height = dheight;
	bDiv.style.position = "absolute";
	bDiv.style.left = "0";
	bDiv.style.top = "0";
	bDiv.style.display = "block";
	bDiv.style.background = "#ccc";
	bDiv.style.opacity = "0.3";
	bDiv.style.filter = "alpha(opacity=30)";
	
	document.body.appendChild(bDiv); 
	
	var mDiv = document.createElement('div'); 
	mDiv.id = 'promptmsg';
	mDiv.className = "promptmsg";
	mDiv.style.width = 400;
	mDiv.style.height = 200;
	mDiv.style.top = (ch-200)/2;
	mDiv.style.left = (cw-500)/2;
	mDiv.style.display = "block";
	mDiv.style.position = "fixed";
	if(fm && fm.all){
		mDiv.style.position = "absolute";
	}
	mDiv.style.padding = "0px 0px 10px 0px";
	mDiv.style.border = "2px solid #6593CF";
	mDiv.style.color = "#222";
	mDiv.style.background = "#fff";
//	mDiv.style.cursor = "wait";
	mDiv.style.textAlign = "center";
	mDiv.innerHTML = "<div style='height:55;background:#9cf;text-align:right;padding-top:7;padding-right:8;'>" +
			"<img src='../common/images/hidemenu.gif' onClick='hidePromptBox();' style='cursor:pointer;'></img></div>" +
			"<div style='padding:8px 10px 5px 10px;word-break: break-all;: break-word;font-size:14px;max-height:85;'>"+content+"</div>" +
			"<div style='position:absolute; bottom:15px;left:165;'><img src='../common/images/butOk.gif' onClick='hidePromptBox();afterOk(\""+FlagStr+"\",\""+Flag+"\");' style='cursor:pointer'></img></div>";
	
	document.body.appendChild(mDiv); 
}

function hidePromptBox(){
	document.body.removeChild(promptmask);
	document.body.removeChild(promptmsg);
}
function afterOk(FlagStr,Flag){
}

function llLockPage(msg){ 
	var loadMsg = "Loading........";
	if(msg != null && msg != ""){
			loadMsg = msg;
	}
	var dheight = 0;
	var dWidth = 0;
 	var sh=document.body.scrollHeight;//滚动条的高度
	var ch =document.body.clientHeight;	
	if(sh > ch){
		dheight=sh;
	}else{
		dheight=ch;
	}
	var sw = document.body.scrollWidth;
	var cw =document.body.clientWidth;
	if(sw >cw ){
		dWidth=sw; 
	}else{
		dWidth=cw;
	}
	var bDiv = document.createElement('div'); 
	bDiv.id = 'datagridMask';
	bDiv.className = "datagrid-mask";
	bDiv.style.width = dWidth;
	bDiv.style.height = dheight;
	bDiv.style.display = "block";
	bDiv.style.position = "absolute";
	bDiv.style.left = "0";
	bDiv.style.top = "0";
	bDiv.style.background = "#ccc";
	bDiv.style.opacity = "0.3";
	bDiv.style.filter = "alpha(opacity=30)";
	
	document.body.appendChild(bDiv); 
	
	var mDiv = document.createElement('div');
	mDiv.id = 'datagridMaskMsg';
	mDiv.className = "datagrid-mask-msg";
	mDiv.style.position = "absolute";
	mDiv.style.cursor1 = "wait";
	mDiv.style.top = (ch-16)/2;
	mDiv.style.left = (cw-250)/2;
	mDiv.style.display = "block";
	mDiv.style.position = "fixed";
	if(fm && fm.all){
		mDiv.style.position = "absolute";
	}
	mDiv.style.width = "auto";
	mDiv.style.height = "16px";
	mDiv.style.padding = "12px 5px 10px 30px";
	mDiv.style.background = "#fff url('images/pagination_loading.gif') no-repeat scroll 5px 10px";
	mDiv.style.border = "2px solid #6593CF";
	mDiv.style.color = "#222";
	mDiv.innerHTML = msg;
	
	document.body.appendChild(mDiv); 
} 

function llUnLockPage(){ 
	document.body.removeChild(datagridMask);
	document.body.removeChild(datagridMaskMsg);
}
