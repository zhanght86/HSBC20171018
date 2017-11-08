//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;

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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		
		if (cPolNo == "")
		    return;
		    
		var GrpPolNo = PolGrid.getRowColData(tSel-1,1);
        
        //alert("dfdf");
        if( tIsCancelPolFlag == "0"){
	    	if (GrpPolNo =="00000000000000000000") {
	    	 	window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
		    } else {
			window.open("./AllProQueryMain.jsp?LoadFlag=4");	
		    }
		} else {
		if( tIsCancelPolFlag == "1"){//销户保单查询
			if (GrpPolNo =="00000000000000000000")   {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} else {
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	    } else {
	    	alert("保单类型传输错误!");
	    	return ;
	    }
	 }
 }
}

//销户保单的查询函数
function getQueryDetail_B()
{
	
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	  var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		if (cPolNo == "")
			return;
		var GrpPolNo = PolGrid.getRowColData(tSel-1,6);
	    if (GrpPolNo =="00000000000000000000") 
	    {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} 
			else 
			{
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	}
}

function DateCompare(date1,date2)
{
  var strValue=date1.split("-");
  var date1Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);

  strValue=date2.split("-");
  var date2Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return -1;
}

// 保单明细查询
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);	
	    var prtNo=	PolGrid.getRowColData(tSel - 1,2);	    
		if (cPolNo == "")
		    return;
	var NoType = 1;
	
	 var strSql="";
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
    
//    strSql = "select case lmriskapp.riskprop"
//            +" when 'I' then '1'"
//	        +" when 'G' then '2'"
//	        +" when 'Y' then '3'"
//	        +" when 'T' then '5'"
//           + " end"
//           + " from lmriskapp"
//           + " where riskcode in (select riskcode"
//           + " from lcpol"
//           + " where polno = mainpolno"
//           + " and prtno = '"+prtNo+"')"
           
        var  sqlid1="PolModifyQuearySql0";
   	 	var  mySql1=new SqlClass();
   	 	mySql1.setResourceName("uw.PolModifyQuearySql"); //指定使用的properties文件名
   	 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
   	 	mySql1.addSubPara(prtNo);//指定传入的参数
   	 	strSql=mySql1.getString();
           
    arrResult = easyExecSql(strSql);
    if(arrResult==null){
    	if(_DBT==_DBO){
//    		strSql = " select * from ("
//                + " select case missionprop5"
//                + " when '05' then '3'"
//                + " when '12' then '3'"
//                + " when '13' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001099'"
//                + " union"
//                + " select case missionprop5"
//                + " when 'TB05' then '3'"
//                + " when 'TB12' then '3'"
//                + " when 'TB06' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001098'"
//                + ") where rownum=1";
    		    var  sqlid2="PolModifyQuearySql1";
    	   	 	var  mySql2=new SqlClass();
    	   	 	mySql2.setResourceName("uw.PolModifyQuearySql"); //指定使用的properties文件名
    	   	 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    	   	 	mySql2.addSubPara(prtNo);//指定传入的参数
    	   	    mySql2.addSubPara(prtNo);//指定传入的参数
    	   	 	strSql=mySql2.getString();
    	}else if(_DBT==_DBM){
//    		strSql = " select * from ("
//                + " select case missionprop5"
//                + " when '05' then '3'"
//                + " when '12' then '3'"
//                + " when '13' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001099'"
//                + " union"
//                + " select case missionprop5"
//                + " when 'TB05' then '3'"
//                + " when 'TB12' then '3'"
//                + " when 'TB06' then '5'"
//                + " else '1'"
//                + " end"
//                + " from lbmission"
//                + " where missionprop1 = '"+prtNo+"'"
//                + " and activityid = '0000001098'"
//                + ") ax  limit 1";
    		
    		var  sqlid3="PolModifyQuearySql2";
  	   	 	var  mySql3=new SqlClass();
  	   	 	mySql3.setResourceName("uw.PolModifyQuearySql"); //指定使用的properties文件名
  	   	 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
  	   	 	mySql3.addSubPara(prtNo);//指定传入的参数
  	   	    mySql3.addSubPara(prtNo);//指定传入的参数
  	   	 	strSql=mySql3.getString();
    	}
        
        arrResult = easyExecSql(strSql);               
    }
    var BankFlag = arrResult[0][0];

    
    //var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+prtNo+"'";
        var  sqlid4="PolModifyQuearySql3";
 	 	var  mySql4=new SqlClass();
 	 	mySql4.setResourceName("uw.PolModifyQuearySql"); //指定使用的properties文件名
 	 	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
 	 	mySql4.addSubPara(prtNo);//指定传入的参数
 	    var strSql2=mySql4.getString();
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
    
	  window.open("../uw/PolModifyMain.jsp?LoadFlag=1&prtNo="+prtNo+"&PolNo="+cPolNo+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");    	    
	}
}

// 查询按钮
function easyQueryClick()
{    
	// 初始化表格
	initPolGrid();

	if((fm.ContNo.value==""||fm.ContNo.value==null)&&(fm.PrtNo.value==""||fm.PrtNo.value==null)){
		alert("保单号码与印刷号码不能全为空！");
		return false;
	}
	// 书写SQL语句
	var strSQL = "";
	
//	strSQL = "select contno,PrtNo,AppntName,"
//	             + " (select riskcode from lcpol where contno=a.contno and insuredno=a.insuredno and polno=mainpolno and (risksequence='1' or risksequence='-1')),"
//	             + " InsuredName,GrpContNo,CValiDate "
//				 +"from LCCont a where 1=1 and appflag='1'"
//				 + getWherePart( 'ContNo' )
//				 + getWherePart( 'GrpContNo' )
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'ManageCom','ManageCom','like')
//				 + getWherePart( 'RiskCode' )
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'AppntName' )
//				 + getWherePart( 'InsuredNo' )
//				 + getWherePart( 'InsuredName' )
//				 + " and ManageCom like '" + comCode + "%%'"
//				 + "order by prtno";
	
	   var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
       var  GrpContNo = window.document.getElementsByName(trim("GrpContNo"))[0].value;
       var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
       var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
       var  RiskCode = window.document.getElementsByName(trim("RiskCode"))[0].value;
       var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
       var  AppntName = window.document.getElementsByName(trim("AppntName"))[0].value;
       var  InsuredNo = window.document.getElementsByName(trim("InsuredNo"))[0].value;
       var  InsuredName = window.document.getElementsByName(trim("InsuredName"))[0].value;
   	   var  sqlid5="PolModifyQuearySql4";
   	   var  mySql5=new SqlClass();
   	   mySql5.setResourceName("uw.PolModifyQuearySql"); //指定使用的properties文件名
   	   mySql5.setSqlId(sqlid5);//指定使用的Sql的id
   	   mySql5.addSubPara(ContNo);//指定传入的参数
   	   mySql5.addSubPara(GrpContNo);//指定传入的参数
   	   mySql5.addSubPara(PrtNo);//指定传入的参数
   	   mySql5.addSubPara(ManageCom);//指定传入的参数
   	   mySql5.addSubPara(RiskCode);//指定传入的参数
   	   mySql5.addSubPara(AgentCode);//指定传入的参数
 	   mySql5.addSubPara(AppntName);//指定传入的参数
 	   mySql5.addSubPara(InsuredNo);//指定传入的参数
 	   mySql5.addSubPara(InsuredName);//指定传入的参数
 	   mySql5.addSubPara(comCode);//指定传入的参数
   	   strSQL=mySql5.getString();
		 
	//prompt("",strSQL);
	
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //showCodeName();
}


// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			//alert(arrReturn);
			//alert("返回"+arrReturn);
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录，再点击返回按钮。");
			//alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	var tRow = PolGrid.getSelNo();
	//alert(arrGrid);
	//alert("safsf");
	if( tRow == 0 || tRow == null || arrDataSet == null )
		      return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = PolGrid.getRowData(tRow-1);
	//alert(arrSelected[0][1]);
	//tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//设置需要返回的数组
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}

//查询代理人的方式
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码	
		//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
		var  sqlid6="PolModifyQuearySql5";
 	 	var  mySql6=new SqlClass();
 	 	mySql6.setResourceName("uw.PolModifyQuearySql"); //指定使用的properties文件名
 	 	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
 	 	mySql6.addSubPara(cAgentCode);//指定传入的参数
 	    var strSql=mySql6.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
			alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
    }
	}	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}