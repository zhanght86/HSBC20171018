//程序名称：EdorUWManuAdd.js
//程序功能：保全人工核保加费录入
//创建日期：2005-07-16 11:10:36
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var spanObj;
var hadAddFee = false;

//提交，保存按钮对应操作
function submitForm()
{
	
  var i = 0;
  var cPolNo = fm.PolNo2.value ;
  var cEdorNo = fm.EdorNo.value;
  if(cPolNo == null || cPolNo == "")
  {
  	alert("未选择加费的投保单!");
  	return;
  }
  
  if(fm.SubMissionID.value == "")
  {
  	alert("已录入核保通知书信息,但未打印,不容许录入新的核保通知书加费信息!");
  	var cPolNo = fm.PolNo.value;
  	var cContNo = fm.ContNo.value;
  	initForm(cEdorNo,cPolNo,cContNo, cMissionID, cSubMissionID);
  	
  	return;
  }
  
  
  var iAddPremCount = SpecGrid.mulLineCount;
  if (iAddPremCount < 1)
  {
  	fm.NoAddPrem.value = "Y";
  }
  
  if (!CalAllAddFee(''))
  {
    return;
  }
  //alert(9);
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

  fm.action= "./EdorUWManuAddSave.jsp";
  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
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
    //alert(content);
  }
  else
  { 
  }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
	fm.submit();
}

// 查询按钮
function initlist(tPolNo, tEdorNo)
{
	// 书写SQL语句
	k++;
	var strSQL = "";
//	strSQL = "select dutycode, 'Duty' from LPDuty where "+k+" = "+k
//		+ " and polno = '"+tPolNo+"' and EdorNo = '" + tEdorNo +"'";

	var sqlid1="EdorUWManuAddSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(k);//指定传入的参数
	mySql1.addSubPara(k);//指定传入的参数
	mySql1.addSubPara(tPolNo);//指定传入的参数
	mySql1.addSubPara(tEdorNo);//指定传入的参数
	strSQL=mySql1.getString();	
    str  = easyQueryVer3(strSQL, 1, 0, 1); 
    return str;
   
}

//查询已经加费项目
function QueryGrid(tEdorNo,tEdorType,tPolNo,tPolNo2)
{
	var strSQL = "";
	var i, j, m, n;	
	
	var EdorAppDate = "";
    //
//    strSQL =  " select Edorappdate from LPEdorMain where edoracceptno = '" + fm.EdorAcceptNo.value
//    		+ "' and contno = '" + fm.ContNo.value
//    		+ "' and EdorNo = '" + fm.EdorNo.value + "'";
    var sqlid2="EdorUWManuAddSql2";
    var mySql2=new SqlClass();
    mySql2.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
    mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    mySql2.addSubPara(fm.EdorAcceptNo.value);//指定传入的参数
    mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
    mySql2.addSubPara(fm.EdorNo.value);//指定传入的参数
    strSQL=mySql2.getString();   
        var urr = easyExecSql(strSQL);
    if ( urr )  
    {
        urr[0][0]==null||urr[0][0]=='null'?'0':EdorAppDate = urr[0][0];
    }

    //获取原加费信息
//	strSQL =   " select dutycode, PayPlanType, '', suppriskscore, SecInsuAddPoint, AddFeeDirect, Prem, PayPlanCode, PayStartDate, PayToDate, PayEndDate "
//	         + " from LPPrem where 1=1 "
//			 + " and payplancode like '000000__'"
//			 + " and ((PayStartDate <= '" + EdorAppDate + "' and PayEndDate >= '" + EdorAppDate + "') "
//			 + " or (PayStartDate >= '" + EdorAppDate + "'))"
//			 + " and PolNo ='"+tPolNo2+"' and edortype = '"+tEdorType+"' and edorno = '"+tEdorNo+"'"
//			 //+ " union "
//			 //+ " select dutycode, PayPlanType, '', suppriskscore, SecInsuAddPoint, AddFeeDirect, Prem, PayPlanCode, PayStartDate, PayToDate, PayEndDate "
//	         //+ " from LPPrem where 1=1 "
//			 //+ " and payplancode like '000000__'"
//			 //+ " and PayStartDate >= (select Edorvalidate from LPEdorMain where EdorNo = '" + fm.EdorNo.value + "')"
//			 //+ " and PolNo ='"+tPolNo2+"' and edortype = '"+tEdorType+"' and edorno = '"+tEdorNo+"'"
//			 ;
			 
	    var sqlid3="EdorUWManuAddSql3";
	    var mySql3=new SqlClass();
	    mySql3.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
	    mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	    mySql3.addSubPara(EdorAppDate);//指定传入的参数
	    mySql3.addSubPara(EdorAppDate);//指定传入的参数
	    mySql3.addSubPara(EdorAppDate);//指定传入的参数
	    mySql3.addSubPara(tPolNo2);//指定传入的参数
	    mySql3.addSubPara(tEdorType);//指定传入的参数
	    mySql3.addSubPara(tEdorNo);//指定传入的参数
	    strSQL=mySql3.getString();   
	    
	//alert(strSQL);
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  //判断是否查询成功
    if (turnPage.strQueryResult)
    {
    	hadAddFee = true; 
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
        //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
        turnPage.pageDisplayGrid = SpecGrid;    
          
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
    if (!turnPage.strQueryResult)
	{
		hadAddFee = false; 
	}
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
//    strSQL = "select CValiDate, PayToDate, PayEndDate from LPPol where PolNo = '" + tPolNo2 + "' and EdorNo = '" + tEdorNo + "' and EdorType = '" + tEdorType + "'";
	
    var sqlid4="EdorUWManuAddSql4";
    var mySql4=new SqlClass();
    mySql4.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
    mySql4.setSqlId(sqlid4);//指定使用的Sql的id
    mySql4.addSubPara(tPolNo2);//指定传入的参数
    mySql4.addSubPara(tEdorNo);//指定传入的参数
    mySql4.addSubPara(tEdorType);//指定传入的参数
    strSQL=mySql4.getString();   
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

    if (!turnPage.strQueryResult)
    {  
//        strSQL = "select CValiDate, PayToDate, PayEndDate from LCPol where PolNo = '" + tPolNo2 + "'";
        var sqlid6="EdorUWManuAddSql6";
        var mySql6=new SqlClass();
        mySql6.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
        mySql6.setSqlId(sqlid6);//指定使用的Sql的id
        mySql6.addSubPara(tPolNo2);//指定传入的参数
        strSQL=mySql6.getString();           
	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
    }

    //判断是否查询成功
    if (turnPage.strQueryResult)
    {  
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        fm.PayStartDate.value = turnPage.arrDataCacheSet[0][0];
        if (turnPage.arrDataCacheSet[0][1] != null && turnPage.arrDataCacheSet[0][1] != '')
        {
            fm.PayToDate.value = turnPage.arrDataCacheSet[0][1];
        }
        fm.PayEndDate.value = turnPage.arrDataCacheSet[0][2];
    }
   
    return true;	
}

//查询险种信息
function QueryPolAddGrid(tEdorNo,tContNo,tInsuredNo)
{
//   var strSql = "select EdorNo,EdorType,ContNo,PolNo,prtno,RiskCode,RiskVersion,"
//             + "  AppntName,InsuredName,Prem,MainPolNo from LPPol where "				 			
//			 + "  ContNo ='"+tContNo+"'"+" and EdorNo='"+tEdorNo+"'"
//			 + "  order by polno ";
   var strSql = "";
   var sqlid5="EdorUWManuAddSql5";
   var mySql5=new SqlClass();
   mySql5.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
   mySql5.setSqlId(sqlid5);//指定使用的Sql的id
   mySql5.addSubPara(tContNo);//指定传入的参数
   mySql5.addSubPara(tEdorNo);//指定传入的参数
   strSql=mySql5.getString();   
   var brr = easyExecSql(strSql);
   
   if(brr)
   {
   	 initPolAddGrid();
   	 turnPage.queryModal(strSql,PolAddGrid);
   	 
   	 divSubmit.style.display = '';
   	 divReturn.style.display = '';
   	 
   }
   else
   {
   	 alert("没有险种需要核保！");
   	 divSubmit.style.display = 'none';
   	 divReturn.style.display = '';
   	 return;
   }
  return true;	
}
	
function getPolGridCho()
{
  var tSelNo = PolAddGrid.getSelNo()-1;
  tRow = tSelNo;
  var cPolNo2 = PolAddGrid.getRowColData(tSelNo,4);
  var cPolNo = PolAddGrid.getRowColData(tSelNo,4);
  var tEdorType = PolAddGrid.getRowColData(tSelNo,2);
  fm.EdorType.value = tEdorType;
  var tEdorNo = fm.EdorNo.value;
  fm.PolNo.value = cPolNo
  fm.PolNo2.value = cPolNo2 ;
  if(cPolNo != null && cPolNo != "" )
  {
  	divAddInfo.style.display = '';
  	str = initlist(cPolNo2, tEdorNo);
    initUWSpecGrid(str);
    QueryGrid(tEdorNo,tEdorType,cPolNo,cPolNo2);	
    QueryAddReason(tEdorNo,tEdorType,cPolNo);    
  }	
}


//查询已经录入加费特约原因
function QueryAddReason(tEdorNo,tEdorType,tPolNo)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select addpremreason from LPUWMaster where edortype='"+tEdorType
//			 + "' and polno = '"+tPolNo+"' and edorno = '"+tEdorNo+"'";
	
	   var sqlid7="EdorUWManuAddSql7";
	   var mySql7=new SqlClass();
	   mySql7.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
	   mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	   mySql7.addSubPara(tEdorType);//指定传入的参数
	   mySql7.addSubPara(tPolNo);//指定传入的参数
	   mySql7.addSubPara(tEdorNo);//指定传入的参数
	   strSQL=mySql7.getString();   	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	fm.AddReason.value = "";
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.AddReason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

//初始化加费对象
function initAddObj(span)
{
	spanObj = span;
	var tSelNo = PolAddGrid.getSelNo()-1; 
    var tRiskCode = PolAddGrid.getRowColData(tSelNo,6);
	var tDutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;
	var tAddFeeType = document.all(spanObj).all('SpecGrid2').value;
	
    if(tAddFeeType = '01')
    {
//	var srtSql = "select AddFeeObject from LMDutyPayAddFee where 1=1 "
//	           + " and riskcode = '"+tRiskCode+"'"
//	           + " and DutyCode = '"+tDutyCode+"'"
//	           + " and AddFeeType = '01'";
	   var srtSql = "";
	   var sqlid8="EdorUWManuAddSql8";
	   var mySql8=new SqlClass();
	   mySql8.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
	   mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	   mySql8.addSubPara(tRiskCode);//指定传入的参数
	   mySql8.addSubPara(tDutyCode);//指定传入的参数
	   srtSql=mySql8.getString();   		
	turnPage.strQueryResult  = easyQueryVer3(srtSql, 1, 0, 1); 

  //判断是否查询成功
    if (!turnPage.strQueryResult) {
  	document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    document.all( spanObj ).all( 'SpecGrid1' ).value = turnPage.arrDataCacheSet[0][0];  

}
else
	{
		document.all( spanObj ).all( 'SpecGrid7' ).value = "";
    return "";
	}
    return true;	
	
}
	


/*********************************************************************
 *  Click事件，当双击“加费金额”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showUWSpec( span)
{
	spanObj = span;
	
	// 书写SQL语句
	var strSQL = "";
	var tRiskCode="";
	var tInsuredSex="";
	var tInsuredAppAge="";
	var tSuppRiskCore="";
	var tAddFeeKind="";
	var tPayEndYear="";
    //校验录入信息的完整性
	if(document.all( spanObj ).all( 'SpecGrid1' ).value == ""){
		alert("请录入加费类型信息！");
		return;
	}
	
	if(document.all( spanObj ).all( 'SpecGrid2' ).value == ""){
		alert("请录入加费原因信息！");
		return;
	}
	else
	tAddFeeKind=document.all( spanObj ).all( 'SpecGrid2' ).value;
	
 	if(document.all( spanObj ).all( 'SpecGrid5' ).value==""){
		alert("请录入加费评点信息！");
		return;
	}
	else
	tSuppRiskCore=document.all( spanObj ).all( 'SpecGrid5' ).value;
	
	
    //此处对职业加费处理还没有确定。
    if(tAddFeeKind=="1"||tAddFeeKind=="3")
    {
//	strSQL = "select AddFeeAMNT("+tAddFeeKind+",riskcode,polno,"+tSuppRiskCore+") from LCpol where polno='"+PolAddGrid.getRowColData(tRow,1)+"'";	
	   var srtSql = "";
	   var sqlid9="EdorUWManuAddSql9";
	   var mySql9=new SqlClass();
	   mySql9.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
	   mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	   mySql9.addSubPara(tAddFeeKind);//指定传入的参数
	   mySql9.addSubPara(tSuppRiskCore);//指定传入的参数
	   mySql9.addSubPara(PolAddGrid.getRowColData(tRow,1));//指定传入的参数
	   srtSql=mySql9.getString();  	   
	turnPage.strQueryResult  = easyQueryVer3(srtSql, 1, 0, 1);   

    //判断是否查询成功
    if (!turnPage.strQueryResult) 
    {
  	alert("保单加费评点计算失败！");
    return "";
    }
   
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    document.all( spanObj ).all( 'SpecGrid6' ).value = turnPage.arrDataCacheSet[0][0];	
    return true;	
    }
    else
  	{
    alert("未定义职业加费评点！");
    return "";
    }
   	
}    

function CalAllAddFee(span)
{
  var tEdorType = fm.EdorType.value;
  var AddCount = SpecGrid.mulLineCount ; 
  if(AddCount == 0)
  {
	  return true;
  }

  for (var j = 0; j < AddCount; j++)
  {

	if (SpecGrid.getRowColData(j, 1) == null || SpecGrid.getRowColData(j, 1) == '')
	{
        alert("必须录入责任编码");   
        return false;
	}

	if (SpecGrid.getRowColData(j, 2) == null || SpecGrid.getRowColData(j, 2) == '')
	{
		if (fm.OtherNoType.value == '4') //团体不用录
		{
			SpecGrid.setRowColData(j, 2, "01")
		}
	  else
	  {
        alert("必须录入加费类型");   
        return false;
    }
	}

	if (SpecGrid.getRowColData(j, 2) == '01' && fm.EdorType.value == 'RE')
	{
		SpecGrid.getRowColData(j, 2) == '03';
	}
  
	if (SpecGrid.getRowColData(j, 2) == '02' && fm.EdorType.value == 'RE')
	{
		SpecGrid.getRowColData(j, 2) == '04';
	}

	if (SpecGrid.getRowColData(j, 3) == null || SpecGrid.getRowColData(j, 3) == '')
    {
        alert("必须录入加费方式");   
        return false;
    }
    if (SpecGrid.getRowColData(j, 3) == '01' ||  tEdorType == "AA" ||  tEdorType == "NS")
    {
        SpecGrid.setRowColData(j, 9, fm.PayStartDate.value); 
    }
    else if (SpecGrid.getRowColData(j, 3) == '02')
    {
        SpecGrid.setRowColData(j, 9, fm.PayToDate.value); 
    }
    else
    {
        alert("请录入正确的加费方式！");   
        return false;
    }    


    SpecGrid.setRowColData(j, 10, fm.PayToDate.value); 
    SpecGrid.setRowColData(j, 11, fm.PayEndDate.value); 
    
    fm.GridRow.value = j;
    var sObj = "spanSpecGrid" + (j + 1);
    if (SpecGrid.getRowColData(j, 7) == null || SpecGrid.getRowColData(j, 7) == '')
    {
        alert("请录入正确的加费金额！");
        return false;
    }
  }
  return true;
}

function CalHealthAddFee(span)
{
    //alert(span);
    var spanObj = span;
    var i = SpecGrid.mulLineCount; 
    //alert(i);
    for (var j = 0; j < i; j++)
    {
		if (SpecGrid.getRowColData(j, 2) == null || SpecGrid.getRowColData(j, 2) == '')
		{
			alert("必须录入加费类型");   
			return false;
		}
		if (SpecGrid.getRowColData(j, 2) == '01' && fm.EdorType.value == 'RE')
		{
			SpecGrid.setRowColData(j, 2, '03');
		}
  
		if (SpecGrid.getRowColData(j, 2) == '02' && fm.EdorType.value == 'RE')
		{
			SpecGrid.setRowColData(j, 2, '04');
		}
		//alert(hadAddFee);
		if (hadAddFee) //查询初始化已有加费
		{
			var sObj = "spanSpecGrid" + (j + 1);
		}
	    else  //查询初始化没有加费
		{
			var sObj = "spanSpecGrid" + (j );
		}
        
        //alert(sObj);
        if (sObj == span)
        {
        	//alert(j );
            fm.GridRow.value = j ;
            break;
        }
    }
	
    var tSelNo = PolAddGrid.getSelNo()-1; 
    var tPolNo = PolAddGrid.getRowColData(tSelNo,4);
    var tMainPolNo = PolAddGrid.getRowColData(tSelNo,11);
    var tPrem ;//= PolAddGrid.getRowColData(tSelNo,10);
    //查出该责任的保费
    var tdutyCode = document.all( spanObj ).all( 'SpecGrid1' ).value;
    var tEdorNo = PolAddGrid.getRowColData(tSelNo, 1);
    var tEdorType = PolAddGrid.getRowColData(tSelNo, 2);

    var tSuppRiskScore = document.all( spanObj ).all( 'SpecGrid4' ).value;
    //如果附加险有定义加费算法，则走后台计算，如果没有定义加费计算，则在前台直接计算 zhangtao 2007-03-07
//	  var strSQL = "  select 1 from LMDutyPayAddFee where dutycode = '" + tdutyCode + "'";
	   var strSQL = "";
	   var sqlid10="EdorUWManuAddSql10";
	   var mySql10=new SqlClass();
	   mySql10.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
	   mySql10.setSqlId(sqlid10);//指定使用的Sql的id
	   mySql10.addSubPara(tdutyCode);//指定传入的参数
	   strSQL=mySql10.getString();  	   
	  var brr = easyExecSql(strSQL);
    if(tPolNo != tMainPolNo && !brr)  //附加险并且没有定义加费算法
    {
//	    strSQL = "  select sum(prem) from lpprem where payplantype = '0' and edorno = '" + tEdorNo + 
//	    			 "' and edortype = '" + tEdorType + 
//	    			 "' and polno = '" + tPolNo +
//	    			 "' and dutycode = '" + tdutyCode + "'";
	       var strSQL = "";
		   var sqlid11="EdorUWManuAddSql11";
		   var mySql11=new SqlClass();
		   mySql11.setResourceName("bq.EdorUWManuAddSql"); //指定使用的properties文件名
		   mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		   mySql11.addSubPara(tEdorNo);//指定传入的参数
		   mySql11.addSubPara(tEdorType);//指定传入的参数
		   mySql11.addSubPara(tPolNo);//指定传入的参数
		   mySql11.addSubPara(tdutyCode);//指定传入的参数
		   strSQL=mySql11.getString();  	   
	    
	    brr = easyExecSql(strSQL);
	    if ( brr ) 
	    {
	        brr[0][0]==null||brr[0][0]=='null'?'0':tPrem = brr[0][0];
	    }
	    else
	    {
	    	alert("责任项保费查询失败！");
	    	return;
	    }
     	var tAddPrem = tSuppRiskScore / 100 * tPrem;
     	tAddPrem = Math.round(tAddPrem * 100) / 100;
     	document.all( spanObj ).all( 'SpecGrid7' ).value = tAddPrem;  	
    }
    else
	{
	    document.all('DutyCode').value = document.all( spanObj ).all( 'SpecGrid1' ).value;
	    document.all('AddFeeType').value = document.all( spanObj ).all( 'SpecGrid2' ).value;
        document.all('SuppRiskScore').value = document.all( spanObj ).all( 'SpecGrid4' ).value;
	    document.all('SecondScore').value = document.all( spanObj ).all( 'SpecGrid5' ).value;
	    document.all('AddFeeObject').value = document.all( spanObj ).all( 'SpecGrid6' ).value;
	    
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

        fm.action= "./EdorUWCalAddFee.jsp";
        fm.submit(); //提交
    }
}
