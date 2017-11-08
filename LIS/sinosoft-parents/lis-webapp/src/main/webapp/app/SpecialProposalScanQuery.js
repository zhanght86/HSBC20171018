//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var sFeatures2 = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
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

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
	}
}

// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var queryBug = 1;
function ClickQuery() 
{
	// 初始化表格
	initPolGrid();

	var tQueryPrtNo = document.all('QueryPrtNo').value;  //印刷号
	
	//alert(tQueryPrtNo);
		
	if(tQueryPrtNo == null || tQueryPrtNo == "")
	{
	  	//必须录入印刷号,否则无法查询;
	  	alert("印刷号不能为空，请您录入！");
    	return false;
	}
	
	//alert(tQueryPrtNo);
	//return false;
  //查询工作流信息
  //var strWorkFlowSql = "select missionid from LWMission where Activityid in (select a.activityid  from lwactivity a where a.functionid in ( '10010006','10010020')) and MissionProp1='"+tQueryPrtNo+"'";
	//var sqlresourcename = "app.SpecialProposalScanQuerySql";
	var sqlid1="SpecialProposalScanQuerySql1";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.SpecialProposalScanQuerySql");
	mySql0.setSqlId(sqlid1);
  mySql0.addSubPara(tQueryPrtNo);//指定传入参数
  var strWorkFlowSql = mySql0.getString();
   
  var arrWorkFlowResult = easyExecSql(strWorkFlowSql);
	//alert(arrWorkFlowResult);
	if (arrWorkFlowResult == null) 
	{
  	alert("此投保单未发给外包方录入或已特殊录入完毕，不能进行录入或再次录入");
    return false;
  }

  
  //查询扫描件信息
  //var strSCANSql = "select ManageCom,SubType from ES_DOC_MAIN where doccode='"+tQueryPrtNo+"'";
  //var strSCANSql = "select ManageCom from ES_DOC_MAIN where doccode='"+tQueryPrtNo+"'";
//sqlresourcename = "app.SpecialProposalScanQuerySql";
	var sqlid2="SpecialProposalScanQuerySql2";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.SpecialProposalScanQuerySql");
	mySql1.setSqlId(sqlid2);
  mySql1.addSubPara(tQueryPrtNo);//指定传入参数
  var strSCANSql = mySql1.getString();
    var arrSCANResult = easyExecSql(strSCANSql);//prompt("",strSCANSql);
	//alert(arrSCANResult);
	if (arrSCANResult == null) 
	{
  	alert("扫描件信息查询失败");
    return false;
  }
  ManageCom = arrSCANResult[0][0];  //保单的管理机构  
  //SubType = arrSCANResult[0][1];
  if(SubType == "UA006")
  {
  	SubType = "05";
  }
                                                  
//	var strSql = "select a.doccode, a.numpages, b.defaultoperator, a.InputStartDate, a.InputStartTime, a.ManageCom,a.makedate,a.maketime "
//					+ "from ES_DOC_MAIN a,LWMission b " 
//					+ "where "+queryBug + "=" + queryBug
//					+ " and a.subtype='UA001' "
//					//+ " and (a.InputState='0' or a.InputState is null ) "
//					+ " and a.doccode=b.missionprop1 "
//				//	+ " and b.activityid = '0000001094'"
//			   + " and (b.defaultoperator is null or b.defaultoperator ='" + operator + "') "					
//					//+ "and exists(select 1 from ldcode1 b where substr(a.doccode,3,2)=trim(b.code1) and b.codetype='scaninput' and b.code='proposal' )"
//					//+ "and exists(select 1 from ldcode1 c where substr(a.doccode,3,2)=trim(c.code) and c.codetype='scaninputctrl' and trim(c.code1)='" + manageCom + "' and a.managecom like trim(c.comcode)||'%%')"
//					+ "and exists(select 1 from BPOALLOTRATE where a.managecom like concat(trim(BPOALLOTRATE.MANAGECOM),'%%' ))"
//					+ getWherePart("b.missionprop1","QueryPrtNo")
//					+ " order by a.modifydate asc,a.modifytime asc";
//	
	var  QueryPrtNo = window.document.getElementsByName(trim("QueryPrtNo"))[0].value;
	var sqlid3="SpecialProposalScanQuerySql3";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.SpecialProposalScanQuerySql");
	mySql2.setSqlId(sqlid3); //指定使用SQL的id
	mySql2.addSubPara(operator);//指定传入参数
	mySql2.addSubPara(QueryPrtNo);//指定传入参数
	var strSql = mySql2.getString();
	
  //alert(strSql);
  //prompt("",strSql);
  
  
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  
  //定义一个备份的结果返回字符串:目的是当通过下面的校验之后，将显示结果显示在multiline中
  var backQueryResult=turnPage.strQueryResult;
  //alert("查询已经外包的单子的结果集= "+backQueryResult);
  
  var backSql=strSql;
  //alert("备份的sql= "+backSql);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    PolGrid.clearData();  
    alert("没有符合条件的数据");
    return false;
  }
  else
  {
  	  //判断如果这个单子已经成为了正式保单，或外包方将需要的数据导入到核心业务系统中来，则提示不能录入
  	 // strSql="select 1 from LCCont where prtno='"+tQueryPrtNo+"'"
     //        +"  union all"
     //        +"  select 1 from BPOMissionState where BussNo='"+tQueryPrtNo+"' and  BussNoType='TB' and State <>'2'";   //不考虑已删除的异常件
      //alert("进行校验的sql= "+strSql);
      //prompt("进行校验的sql",strSql);
    // strSql="select 1 from lwmission where  activityid in (select a.activityid  from lwactivity a where a.functionid in ( '10010006','10010020')) and missionprop1='"+tQueryPrtNo+"' ";
  
    var sqlid4="SpecialProposalScanQuerySql4";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("app.SpecialProposalScanQuerySql");
 	mySql3.setSqlId(sqlid4); //指定使用SQL的id
 	mySql3.addSubPara(tQueryPrtNo);//指定传入参数
 	var strSql = mySql3.getString();
      //查询SQL，返回结果字符串
      turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
		//prompt("",strSql);
      //判断是否查询成功
      if (turnPage.strQueryResult)
      {  
          //没有符合条件的记录，证明通过校验，将数据显示在界面上
          //将备份的变量进行还原
          strSql=backSql;
          //alert("还原的sql= "+strSql);
          turnPage.strQueryResult=backQueryResult;
          //alert("还原的查询结果= "+turnPage.strQueryResult);
          
          //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
				  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
				  
				  //查询成功则拆分字符串，返回二维数组
				  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
				  
				  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
				  turnPage.pageDisplayGrid = PolGrid;    
				          
				  //保存SQL语句
				  turnPage.strQuerySql     = strSql; 
				  
				  //设置查询起始位置
				  turnPage.pageIndex       = 0;  
				  
				  //在查询结果数组中取出符合页面显示大小设置的数组
				  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
				  
				  //调用MULTILINE对象显示查询结果
				  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
				  
				  //控制是否显示翻页按钮
				  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) 
				  {
				      try { window.divPage.style.display = ""; } catch(ex) { }
				  } 
				  else 
				  {
				      try { window.divPage.style.display = "none"; } catch(ex) { }
				  }
      }
      else
      {
      		alert("此印刷号代表的保单已经录入过或外包方已经将数据导入到核心业务系统中，不需要重新录入");
      		//prompt("","此印刷号代表的保单已经录入过或外包方已经将数据导入到核心业务系统中，不需要重新录入");
      		return false;
      }
  }  
}


var prtNo = "";
function scanApplyClick() {
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	prtNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	
  	//将录入外包工作流扭转为特殊保单录入节点
  	var strDate = Date.parse(new Date());
  	//var strWorkFlowSql = "select missionid,submissionid,activityid from LWMission where Activityid in (select a.activityid  from lwactivity a where a.functionid = '10010006') and MissionProp1='"+prtNo+"'";
    
  	var sqlid5=("SpecialProposalScanQuerySql5");
    var mySql4=new SqlClass();
 	mySql4.setResourceName("app.SpecialProposalScanQuerySql");
 	mySql4.setSqlId(sqlid5); //指定使用SQL的id
 	mySql4.addSubPara(prtNo);//指定传入参数
 	var strWorkFlowSql = mySql4.getString();
 	
  	var arrWorkFlowResult = easyExecSql(strWorkFlowSql);
	  //alert("1: "+arrWorkFlowResult);
	  if (arrWorkFlowResult != null) 
	  {
	  	MissionID = arrWorkFlowResult[0][0];
      SubMissionID = arrWorkFlowResult[0][1];
      ActivityID = arrWorkFlowResult[0][2];
    	var urlStr1 = "./BPOInputConfirm1.jsp?PrtNo=" + prtNo + "&SpecType=1&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&WorkFlowFlag="+ActivityID+"&strDate="+strDate;
      //alert("2: "+urlStr1);
      var sFeatures1 = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
      //window.showModalDialog(urlStr1, "", sFeatures1);
	  var name='提示';   //网页名称，可为空; 
      var iWidth=400;      //弹出窗口的宽度; 
      var iHeight=200;     //弹出窗口的高度; 
      var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
      var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
      showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

      showInfo.focus();
    }
  		
  	//查询工作流特殊保单录入节点
  	//var strWorkFlowSql1 = "select missionid,submissionid,activityid,missionprop17,defaultoperator from LWMission where Activityid='0000001094' and MissionProp1='"+prtNo+"'";
    var sqlid6=("SpecialProposalScanQuerySql6");
    var mySql5=new SqlClass();
    mySql5.setResourceName("app.SpecialProposalScanQuerySql");
 	mySql5.setSqlId(sqlid6); //指定使用SQL的id
 	mySql5.addSubPara(prtNo);//指定传入参数
 	var strWorkFlowSql1 = mySql5.getString();
  	var arrWorkFlowResult1 = easyExecSql(strWorkFlowSql1);
	  //alert(arrWorkFlowResult1);
	  if (arrWorkFlowResult1 == null) 
	  {
    	alert("此投保单不符合特殊投保单录入的条件！");
      return false;
    }
      
    MissionID = arrWorkFlowResult1[0][0];
    SubMissionID = arrWorkFlowResult1[0][1];
    ActivityID = arrWorkFlowResult1[0][2];
  	SubType = arrWorkFlowResult1[0][3];
  	var operator1 = arrWorkFlowResult1[0][4];
  	//alert(SubType);
  	var urlStr;
  	var sFeatures;
  	var strReturn;
  	if(operator1!=null && operator1!='')
  	{
  		if(operator1!=operator)
  		{//不能申请
  			alert("该投保单已经被"+operator1+"申请，不能再次申请！");
  			return false;
  		}
  		else
  		{//不用重新申请
  			strReturn = '1';
  		}
  		
  	}
  	else
  	{//申请
  		urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state+"&strDate="+strDate;
	    //prompt("urlStr",urlStr);
	    sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1;";
	    //申请该印刷号
	    //strReturn = window.showModalDialog(urlStr, "", sFeatures);	 
		var name='提示';   //网页名称，可为空; 
		var iWidth=400;      //弹出窗口的宽度; 
		var iHeight=200;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		strReturn = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		strReturn.focus();		
  	}		
  	
  	//lockScreen('lkscreen');  
    //打开扫描件录入界面
    //sFeatures = "";
    //alert("SubType: "+SubType);
    sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;";
    if (strReturn == "1") 
    
      window.open("./DSContInputScanMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+prtNo+"&ManageCom="+ManageCom+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&SubType="+SubType+"&scantype=bposcan&InputTime=0", "", sFeatures+sFeatures2);
          
  }
  else {
    alert("请先选择一条保单信息！"); 
  }

}

function afterInput() {
	// 初始化表格
	     initPolGrid();

 //不用进行该校验 2009-2-13 ln delete
  /*
  //录入完成，询问是否完成该保单  
  var completeFlag = window.confirm("该印刷号对应的保单录入是否全部完成？\n选择是将不再查询出该印刷号！");  
  
  if (completeFlag)
   {
     var strSql = "select PrtNo from LCPol where PrtNo='" + prtNo + "'";
  
   
     var isComplete = easyExecSql(strSql);
    
     if (isComplete) {    
       var state = "1";
       var urlStr = "./ProposalScanApply.jsp?prtNo=" + prtNo + "&operator=" + operator + "&state=" + state;
       var sFeatures = "status:no;help:0;close:0;dialogWidth:400px;dialogHeight:200px;resizable=1";
       window.showModalDialog(urlStr, "", sFeatures);
       // 初始化表格
	     initPolGrid();
     }
     else {
       alert("该投保单（印刷号：" + prtNo + "）还没有进行录入，不能关闭扫描件！");
     }
   }*/
  return true;
}

