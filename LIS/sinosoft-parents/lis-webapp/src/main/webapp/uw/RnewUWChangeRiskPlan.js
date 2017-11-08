//程序名称：UWManuSpec.js
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var turnPage_OldRisk = new turnPageClass();
var turnPage_AddFee = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var operate = "";
var proposalno = "";
var serialno = "";
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库

//提交，保存按钮对应操作
function submitForm(tflag)
{
	fm.action="./RnewUWChangeRiskPlanChk.jsp";
	if(tflag=="1")
	   {
	   	var tSpecType=fm.HealthSpecTemp.value;
	   	var tSpecCode=fm.SpecTemp.value;
	   	var tRemark=fm.Remark.value;
	   	if(trim(tSpecType)=="null"||trim(tSpecType)==null||trim(tSpecType)=="")
	   	{
	   		alert("疾病系统编码不能为空!");
	   		return false;
	   		}
	   	if(trim(tSpecCode)=="null"||trim(tSpecCode)==null||trim(tSpecCode)=="")
	   	{
	   		alert("特约内容编码不能为空!");
	   		return false;
	   		}
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("特约内容不能为空!");
	   		return false;
	   		}
	     var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("请选择下发标记!");
	   		return false;
	   	}
	    fm.operate.value = "INSERT"
	    //alert(operate);
	    //return;
	   }
	else if(tflag=="2")   
		 {
		 	var tRemark=fm.Remark.value;
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("特约内容不能为空!");
	   		return false;
	   	}
	 	 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要修改的特约信息！");
		 	  	return;
		 	  } 
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能修改！");
		 	    return;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
     // alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);
			fm.operate.value = "UPDATE";
			fm.SpecCode.value=UWSpecGrid.getRowColData(tSelNo,9);
			fm.SpecType.value=UWSpecGrid.getRowColData(tSelNo,8);;
			//alert(operate);
			//return;
		 }
  else if(tflag=="3")
  	 {
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要删除的特约信息！");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能删除！");
		 	    return;	
		 	  }
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "DELETE";
  	  //alert(operate);
  	  //return;
     }
      else
  	 {  	    
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要修改下发标记的特约信息！");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能修改下发标记！");
		 	    return;	
		 	  }
		 	   var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("请选择下发标记!");
	   		return false;
	   	}
		 	  if (!confirm('确认修改?'))
			{
			return false;
			}
      fm.proposalContNo.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
      fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
  	  fm.operate.value   = "UPDATE";
  	  //alert(operate);
  	  //return;
     }
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

  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //执行下一步操作
  }
  initUWSpecGrid();
  initPolAddGrid();
  //initCancleGiven(mContNo,mInsuredNo);
  QueryPolSpecGrid(mContNo,mInsuredNo);
  queryRiskAddFee(mContNo,mInsuredNo);
  initOldRislPlanGrid('');
  queryOldRiskPlan();
  onRiskTabChange(1);
}
//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //执行下一步操作
    top.window.close();
  }
 
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
   // parent.close();
  }
  else
  { 
	var showStr="操作成功！";  	
  	showInfo.close();
  	alert(showStr);
  //	parent.close();
  	
    //执行下一步操作
  }
  //initUWSpecGrid();
 // QueryPolSpecGrid(mContNo,mInsuredNo);
  //queryRiskAddFee(mContNo,mInsuredNo);
  //initOldRislPlanGrid('');
  //queryOldRiskPlan();
  //onRiskTabChange(1);
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}

function QueryPolSpecGrid(tContNo,tInsuredNo)
{
	// 初始化表格
	// 书写SQL语句
	//alert("QueryPolSpecGrid"+tContNo);
	var strSQL = "";
	var i, j, m, n;	
	var tProposalContNo;
	//tongmeng 2008-10-08 add
	//启用合同特约表
//				 strSQL = "select a,b,c,case c when 'x' then '未发送' "
//			                          + " when '0' then '已发送未打印'"
//			                          + " when '1' then '已打印未回收'"
//                                + " when '2' then '已回收'"
//                         + " end,"
//                         + " d,e,f,g,h,n,p,q,w,(case w when 'Y' then '下发' else '不下发' end)"
//                + "   from (select s.speccontent as a,"
//                + "                s.serialno as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.oldprtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.oldprtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.proposalcontno as d,"
//                + "                s.serialno as e,"
//                + "                s.customerno f,s.spectype g,s.speccode h,s.specreason n,(concat(concat(s.makedate,' '),s.maketime)) p,s.operator q ,s.needprint w"
//                + "                from lccspec s "
//                + "                where s.contno = '"+tContNo+"' and s.customerno = '"+tInsuredNo+"' and spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp'))";
//               
	
    sql_id = "RnewUWChangeRiskPlanSql0";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tInsuredNo);//指定传入的参数
	str_sql = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWSpecGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}
 
  return true;	
}


function getSpecGridCho2()
{
    var tSelNo = UWSpecGrid.getSelNo()-1;
	//alert('tSelNo'+tSelNo);
	var proposalcontno = UWSpecGrid.getRowColData(tSelNo,5);
	var serialno = UWSpecGrid.getRowColData(tSelNo,2);
	//var tContent = fm.Remark.value;
//	strSQL = "select spectype ,speccode ,"
//						+ " ( select codename from ldcode where codetype='healthspcetemp' and code=spectype),"
//                         + " ( select noti from LCCSpecTemplet where templetcode=speccode ),"
//	                      +" needprint ,(case needprint when 'Y' then '下发' else '不下发' end),speccontent"
//                + "     from lccspec  "
//                + "     where proposalcontno = '"+proposalcontno+"' and serialno='"+serialno+"'";
	sql_id = "RnewUWChangeRiskPlanSql1";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(proposalcontno);//指定传入的参数
	my_sql.addSubPara(serialno);//指定传入的参数
	str_sql = my_sql.getString();
    var arrResult = easyExecSql(str_sql);
    if(arrResult!=null)
    {
			fm.SpecTemp.value=arrResult[0][1];
			fm.HealthSpecTemp.value=arrResult[0][0];
			fm.SpecTempname.value=arrResult[0][3];
			fm.HealthSpecTempName.value=arrResult[0][2];
			fm.NeedPrintFlag.value=arrResult[0][4];
			fm.IFNeedFlagName.value=arrResult[0][5];
          fm.Remark.value = arrResult[0][6]; 
    }
	
	
  
	
}

function getClickSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   //alert(tManageCom.length);
  // alert(sql_temp);
   showCodeList('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function getClickUpSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   showCodeListKey('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function queryInsuredInfo(tContNo,tInsuredNo)
{
		//姓名,性别,年龄,与投保人关系,与主被保人关系,职业代码,职业类别,兼职
//    strSQL =" select name,sex,(select polapplydate from lccont where contno=a.contno),birthday,relationtoappnt,"
//           + " relationtomaininsured,occupationcode,occupationtype, "
//           + " pluralitytype ,(select substr(agentcode,5) from lccont where contno=a.contno)from lcinsured a "
//           + " where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";
    sql_id = "RnewUWChangeRiskPlanSql2";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tInsuredNo);//指定传入的参数
	str_sql = my_sql.getString();
    arrResult=easyExecSql(str_sql);
    if(arrResult!=null)
    {
        DisplayInsured(arrResult);
        if(arrResult[0][9]=="999999")
        divOperator.style.display=""; 
    }
    	//查询承保计划变更结论和加费原因 2008-12-1 ln add
//    strSQL =" select uwidea from RnewIndUWMaster "
//           + " where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";
    sql_id = "RnewUWChangeRiskPlanSql3";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tContNo);//指定传入的参数
	my_sql.addSubPara(tInsuredNo);//指定传入的参数
	str_sql = my_sql.getString();
    arrResult=easyExecSql(str_sql);
    if(arrResult!=null)
    {
        fm.UWIdea.value=arrResult[0][0];
    }
}

function DisplayInsured(arrResult)
{
    try{document.all('InsuredName').value= arrResult[0][0]; }catch(ex){};
    try{document.all('InsuredSex').value= arrResult[0][1]; }catch(ex){};
    try{document.all('RelationToAppnt').value= arrResult[0][4]; }catch(ex){};
    try{document.all('RelationToMainInsured').value= arrResult[0][5]; }catch(ex){};
   // alert(arrResult[0][8]);
    try{document.all('InsuredOccupationCode').value= arrResult[0][6]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][7]; }catch(ex){};
    try{document.all('InsuredPluralityType').value= arrResult[0][8]; }catch(ex){};
    try{document.all('InsuredAge').value= calPolCustomerAge(arrResult[0][3],arrResult[0][2]); }catch(ex){};
    quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
    quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
}

function calPolCustomerAge(BirthDate,PolAppntDate)
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


var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
}

function queryOldRiskPlan()
{
//	var tSQL = "select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),amnt,mult,prem,"
//	         + " concat(insuyear,insuyearflag),concat(payendyear,payendyearflag),payintv,uwflag,polno,mainpolno "
//	         + " from lcpol a "
//	         + " where contno='"+mContNo+"' and insuredno='"+mInsuredNo+"' and appflag='1' and rnewflag='-1' order by mainpolno,polno ";
	sql_id = "RnewUWChangeRiskPlanSql4";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(mContNo);//指定传入的参数
	my_sql.addSubPara(mInsuredNo);//指定传入的参数
	str_sql = my_sql.getString(); 
	turnPage_OldRisk.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1); 
	
  //判断是否查询成功
  if (turnPage_OldRisk.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage_OldRisk.arrDataCacheSet = decodeEasyQueryResult(turnPage_OldRisk.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage_OldRisk.pageDisplayGrid = OldRislPlanGrid;    
          
  //保存SQL语句
  turnPage_OldRisk.strQuerySql= str_sql; 
  //设置查询起始位置
  turnPage_OldRisk.pageIndex= 0;  
 
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet= turnPage_OldRisk.getData(turnPage_OldRisk.arrDataCacheSet, turnPage_OldRisk.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage_OldRisk.pageDisplayGrid);
	
}
}

//当切换分页时,处理相关业务逻辑
function onRiskTabChange(tPage)
{
	var tOldSelNo = OldRislPlanGrid.getSelNo();
	//如果切换到变更后险种计划时,先校验是否已经选择了险种
	if(tPage=='2')
	{
		if(tOldSelNo==null || tOldSelNo<=0)
		{
			alert("请先选择一个险种后再做承保计划变更！");
			//onRiskTabChange(1);
			return;
		}		
		
		//开始初始化选择的险种界面
		//获取SelPolNo 
	    var tSelPolNo = OldRislPlanGrid.getRowColData(tOldSelNo-1,10);  
	    //校验改险种是否有加费，有则不能做承保计划变更
//	    var strSQL = "select * from lcprem where polno='"+tSelPolNo+"' and payplancode like '000000%%'";
	    sql_id = "RnewUWChangeRiskPlanSql5";
		my_sql = new SqlClass();  
		my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(tSelPolNo);//指定传入的参数
		str_sql = my_sql.getString(); 
		var arrResult=easyExecSql(str_sql);
		if(arrResult!=null)
		{
			alert("请先删除该险种加费信息，再做承保计划变更！");
			//onRiskTabChange(1);
			return;
		}
	//	alert("tOldSelNo:"+tOldSelNo);
		//alert(tSelPolNo);
		//	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled+"&specScanFlag="+specScanFlag;
		document.getElementById("NewRiskPlan").src = "./UWManuChangeRiskMain.jsp?LoadFlag=25&ContType=1&scantype=scan&hh=1&checktype=1&ContNo="+mContNo+"&PrtNo="+mContNo+"&InsuredChkFlag=true&AppntChkFlag=false&SelPolNo="+tSelPolNo+"&NewChangeRiskPlanFlag=Y&PageFlag=2";
	}
	//处理浮动费率
	if(tPage =='3'){
			document.getElementById("NewRiskPlan").src = "./UWManuChangeRiskMain.jsp?LoadFlag=25&ContType=1&scantype=scan&hh=1&checktype=1&ContNo="+mContNo+"&PrtNo="+mContNo+"&InsuredChkFlag=true&InsuredNo="+mInsuredNo+"&NewChangeRiskPlanFlag=Y&PageFlag=3";
	}
	//处理tab的切换
	if(tPage=='1')
	{
		//
		queryOldRiskPlan();
		tab1c.style.display="";
		tab2c.style.display="none";
		document.getElementById("NewRiskPlan").src = " ";
	}
	else
	{
		tab1c.style.display="none";
		tab2c.style.display="";
	}
}

//tongmeng 2008-10-09 add
//修改险种核保结论
function makeRiskUWState()
{
	//校验是否有险种被选择
	var tSelNo = OldRislPlanGrid.getSelNo();
  if(tSelNo<=0)
 	{
 			alert("请选择险种保单！");
 			return;
 	}
 //	alert('1');
 		var polno = OldRislPlanGrid.getRowColData(tSelNo - 1,10);
 		var mainpolno = OldRislPlanGrid.getRowColData(tSelNo - 1,11);
 		var uwstate = OldRislPlanGrid.getRowColData(tSelNo - 1,9);
 		fm.PolNo.value=polno;
 		fm.uwstate.value = uwstate;
 		document.all('flag').value = "risk";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./InsuredUWInfoChk.jsp";
	 var i = 0;
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
//alert('3');
	document.getElementById("fm").submit();
}

//ln 2008-12-1 add
//承保计划变更结论及加费原因录入
function makeRiskUWIdea()
{
 //	alert('1');
 		document.all('flag').value = "Insured";
 	//	alert('2:'+document.all('flag').value);
	fm.action="./RnewInsuredUWInfoChk.jsp";
	 var i = 0;
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
//alert('3');
	document.getElementById("fm").submit();
}

//ln 2009-1-7 add
//取消提前给付特约
function cancleGiven1()
{
	fm.action="./CancleGivenChk.jsp";
	 var i = 0;
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
//alert('3');
	document.getElementById("fm").submit();
}

//ln 2009-1-7 add
//取消提前给付特约
function initCancleGiven(tContNo,tInsuredNo)
{
//	var strSQL = "select 1 from lcpol where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' "
//			+ " and riskcode in (select riskcode from lmriskapp where cancleforegetspecflag is not null and cancleforegetspecflag='1')"
//	        + " and (cancleforegetspecflag is null or cancleforegetspecflag<>'1')";
	sql_id = "RnewUWChangeRiskPlanSql6";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(tContNo);//指定使用的Sql的id
	my_sql.addSubPara(tInsuredNo);//指定传入的参数
	str_sql = my_sql.getString(); 
    var str  = easyExecSql(str_sql);;
    if(str != null)
    	fm.cancleGiven.disabled = false;
    else
        fm.cancleGiven.disabled = true;
}

//以下为加费程序处理调用函数
// 查询按钮
function initlist(tContNo,tInsuredNo)
{
	// 书写SQL语句
	k++;
	var strSQL = "";
//	strSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode,"
//	       + " c.cvalidate,c.payenddate,c.polno,c.mainpolno "
//	       + " from lcduty a,lmriskduty b,lcpol c where a.dutycode=b.dutycode  and a.polno=c.polno"
//         + " and c.contno='"+tContNo+"' and c.insuredno='"+tInsuredNo+"' and c.appflag='9' and c.rnewflag='-1'"
    sql_id = "RnewUWChangeRiskPlanSql7";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(tContNo);//指定使用的Sql的id
	my_sql.addSubPara(tInsuredNo);//指定传入的参数
	str_sql = my_sql.getString(); 
    str  = easyQueryVer3(str_sql, 1, 0, 1);
    return str;

}

function afterCodeSelect( cCodeName, Field ) {

		if( cCodeName == "addfeetype" ) {
			
			if(Field.value=='02')
			{
				
			}
		}
		if(cCodeName == "PlanPay")
		{
				if(Field.value=='02')
				{
						//获得当前条数的险种编码
						var tSelNo = PolAddGrid.getSelNo()-1;
						var t = PolAddGrid.lastFocusRowNo;
					//	alert('t:'+t+":"+tSelNo);
						
						//alert('tSelNo'+tSelNo);
						if(tSelNo<0)
						{
							/*
							alert('请先选择一条记录!');
							return false;
							*/
							tSelNo = t;
						}
						//111801,111802,121705,121704
						var riskcode = PolAddGrid.getRowColData(tSelNo,1);
						if(riskcode==null||riskcode=='')
						{
							alert('请先选择险种!');
							return false;
						}
						else if(riskcode=='111801'||riskcode=='111802'
							||riskcode=='121705'||riskcode=='121704'
							||riskcode=='121701'||riskcode=='121702'
							//test
							//||riskcode=='121801'
							)
							{
								 PolAddGrid.setRowColData(tSelNo,5,'02',PolAddGrid);
//								 var tSQL = "select medrate from ldoccupation where occupationcode='"+document.all('InsuredOccupationCode').value+"' ";
							    sql_id = "RnewUWChangeRiskPlanSql8";
								my_sql = new SqlClass();  
								my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
								my_sql.setSqlId(document.all('InsuredOccupationCode').value);//指定使用的Sql的id
								str_sql = my_sql.getString(); 
								 var tStr1  = easyExecSql(str_sql);
								 //alert(tStr1);
								 if(tStr1!=null)
								 {
								 	 PolAddGrid.setRowColData(tSelNo,6,tStr1[0][0],PolAddGrid);
								 }
								 return ;
							}
				}
			}
}

//加费保存
function addFeeSave()
{
    var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("请选择需要保存的加费记录!");
		 return false;
	}
	
	var tAddFeeType = PolAddGrid.getRowColData(tSelNo,5);
    var tAddFeeNum = PolAddGrid.getRowColData(tSelNo,6);
    
  //加费方式
    if((tAddFeeType == "01") && ((tInsuredSumLifeAmnt-300000>0) && (tAddFeeNum-150>0)
		       || ((tInsuredSumHealthAmnt-200000>0) && (tAddFeeNum-125>0))))	
	/*
    alert(tAddFeeType);
    alert(tAddFeeNum);
    alert("tInsuredSumLifeAmnt"+tInsuredSumLifeAmnt);
    alert("tInsuredSumHealthAmnt"+tInsuredSumHealthAmnt);
    if((tAddFeeType == "01") && ((parseFloat(tInsuredSumLifeAmnt)-100000>0) && (parseFloat(tAddFeeNum)-150>0)
		       || ((parseFloat(tInsuredSumHealthAmnt)-100000>0) && (parseFloat(tAddFeeNum)-125>0))))  //测试用*/
		       {
		       		 alert("此单须再保呈报！");
		       		 document.all('UpReporFlag').value = "Y";
		       }
    
	fm.action="./RnewUWManuChangeRiskAddChk.jsp";
	var showStr="正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit();
}

//初始化查询加费数据
function queryRiskAddFee(tContNo,tInsuredNo)
{
//	var tSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode, "
//	         + " a.payplantype,a.addfeedirect,a.suppriskscore,a.prem,'',a.paystartdate,a.payenddate,'',a.polno,"
//	         + " c.mainpolno,a.payplancode "
//	         + " from lcprem a,lmriskduty b,lcpol c  where a.dutycode=b.dutycode and a.polno=c.polno"
//	         + " and c.appflag='9' and c.contno='"+tContNo+"' and c.insuredno='"+tInsuredNo+"' "
//	         + " and a.PayStartDate <= c.paytodate and a.payenddate >= c.paytodate"
//	         + " and a.payplancode like '000000%%' ";
	sql_id = "RnewUWChangeRiskPlanSql9";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(tContNo);//指定使用的Sql的id
	my_sql.setSqlId(tInsuredNo);//指定使用的Sql的id
	str_sql = my_sql.getString(); 
	 turnPage_AddFee.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);          
	//判断是否查询成功
  if (turnPage_AddFee.strQueryResult) {  
  //查询成功则拆分字符串，返回二维数组
  turnPage_AddFee.arrDataCacheSet = decodeEasyQueryResult(turnPage_AddFee.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage_AddFee.pageDisplayGrid = PolAddGrid;    
          
  //保存SQL语句
  turnPage_AddFee.strQuerySql     = tSQL; 
  
  //设置查询起始位置
  turnPage_AddFee.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage_AddFee.getData(turnPage_AddFee.arrDataCacheSet, turnPage_AddFee.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage_AddFee.pageDisplayGrid);
}
}

//自动计算加费信息
function AutoCalAddFee(span)
{
	spanObj = span;

  
  //alert(document.all( spanObj ).all( 'PolAddGrid4' ).value);
  //首先判断有没有加费算法
  //险种编码
  var tRiskCode = document.all( spanObj ).all( 'PolAddGrid1' ).value;
  // alert(tRiskCode);
  //加费类别
  var tAddFeeType = document.all( spanObj ).all( 'PolAddGrid4' ).value;
  //加费方式
  var tAddFeeMethod = document.all( spanObj ).all( 'PolAddGrid5' ).value;
  //责任编码
  var tDutyCode = document.all( spanObj ).all( 'PolAddGrid3' ).value;
  
  if(tRiskCode==null||tRiskCode=='')
  {
  	alert("请选择险种!");
  	return;
  }
  if(tAddFeeType==null||tAddFeeType=='')
  {
  	alert("请选择加费类别!");
  	return;
  }
  if(tAddFeeMethod==null||tAddFeeMethod=='')
  {
  	alert("请选择加费方式!");
  	return;
  }
  
 //  var tSelNo = PolAddGrid.lastFocusRowNo;
 //  alert(tSelNo);
 //alert(mInsuredNo);

//  var tSQL = " select 1 from LMDutyPayAddFee where "
//           + " riskcode='"+tRiskCode+"' and dutycode='"+tDutyCode+"' and addfeetype='"+tAddFeeType+"' "
//           + " and  addfeeobject='"+tAddFeeMethod+"'";
  sql_id = "RnewUWChangeRiskPlanSql10";
	my_sql = new SqlClass();  
	my_sql.setResourceName("uw.RnewUWChangeRiskPlanSql"); //指定使用的properties文件名
	my_sql.setSqlId(tRiskCode);//指定使用的Sql的id
	my_sql.setSqlId(tDutyCode);//指定使用的Sql的id
	my_sql.setSqlId(tAddFeeType);//指定使用的Sql的id
	my_sql.setSqlId(tAddFeeMethod);//指定使用的Sql的id
	str_sql = my_sql.getString(); 
   var arrRes =  easyQueryVer3(str_sql, 1, 0, 1);
   //alert("arrRes:"+arrRes);
   if(arrRes==null||!arrRes)
   {
   	  alert("此险种无需进行加费，或者该险种没有此加费类型!");
   	  document.all( spanObj ).all( 'PolAddGrid7' ).value='0';
   	  return;
   }
   //准备数据
  var tPolNo =  document.all( spanObj ).all( 'PolAddGrid12' ).value;
  var tAddFeePoint = document.all( spanObj ).all( 'PolAddGrid6' ).value;
  if(tAddFeeMethod=='01'&&(tAddFeePoint==null||tAddFeePoint<='0'))
  {
  	alert("按EM值加费,请输入加费评点!");
  	return;
  }
 // if(tAddFeeMethod!='01' && !(tAddFeePoint==null||tAddFeePoint==''))
 // {
 // 	 alert("其他加费方式,请不要录入加费评点!");
 // 	 return;
 // }
  var i = 0;
  var showStr="正在计算加费数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   //提交数据库
  fm.action= "./UWCalHealthAddFeeChk.jsp?AddFeePolNo="+tPolNo+"&AddFeeRiskCode="+tRiskCode+"&AddFeeDutyCode="+tDutyCode+"&AddFeeType="+tAddFeeType+"&AddFeeMethod="+tAddFeeMethod+"&AddFeeInsuredNo="+mInsuredNo+"&AddFeePoint="+tAddFeePoint;
  document.getElementById("fm").submit(); //提交
}


//加费删除
//created by guanwei
function deleteData()
{ 
	var tSelNo = PolAddGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		 alert("请选择需要删除的加费记录!");
		 return false;
	}
	
	var tPolNo = PolAddGrid.getRowColData(tSelNo,12);
	var tDutyCode = PolAddGrid.getRowColData(tSelNo,3);
	var tPayPlanCode = PolAddGrid.getRowColData(tSelNo,14);
	if(tPayPlanCode==null||tPayPlanCode=='')
	{
		 alert("该记录并没有保存,请刷新页面重新查询!");
		 return false;
	}
	//initForm(cPolNo,cContNo, cMissionID, cSubMissionID);
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
  fm.action = "./UWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
  document.getElementById("fm").submit();
  
  //var cPolNo= fm.PolNo2.value ;   
  //alert(cPolNo);
  
}