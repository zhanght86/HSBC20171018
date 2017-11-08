//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrResult1;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var queryBug = 1;
function easyQueryClick()  
{
	// 初始化表格
	initPolGrid();
	//对输入的查询条件进行校验
	if( verifyInput2() == false ) 
	{
		return false;
	}
	//判断是否有查询条件，以提高查询速度----added by guanwei
	var i = 0;
	if(document.all("ManageCom").value != "")
	i = i+1;
	if(document.all("PolApplyDate").value != "")
	i = i+1;
	if(document.all("AppntNo").value != "")
	i = i+1;
	if(document.all("AppntName").value != "")
	i = i+1;	
	if(document.all("AppntIDNo").value != "")
	i = i+1;
	if(document.all("InsuredNo").value != "")
	i = i+1;	
	if(document.all("InsuredName").value != "")
	i = i+1;
	if(document.all("InsuredIDNo").value != "")
	i = i+1;
	if(document.all("uwState").value != "")
	i = i+1;
	if(document.all("AgentCode").value != "")
	i = i+1;
	//if(document.all("AgentGroup").value != "")
	//i = i+1;
	if(document.all("uwgrade").value != "")
	i = i+1;
	if(document.all("AppntNo").value != "")
	i = i+1;
	  if (i < 2 && document.all("ContNo").value =="" && document.all("tempfeeno").value == "")
	  {
	  	alert("查询条件不足！请至少输入投保单号或暂收收据号/划款协议书号 。如无法提供请输入至少两条其他查询条件");
	  	document.all("ContNo").focus();
	  	return;
	  	}
	//机构控制<如果没有选择管理机构则使用登陆时的登陆机构>
	//if(trim(fm.ManageCom.value)=="")
	//{
	//	//alert("登陆机构代码："+manageCom);
	//	fm.ManageCom.value=manageCom;
	//}

	if(document.all('ContNo').value!=""&&document.all('tempfeeno').value=="")
	{
		
		var sqlid1="ProposalQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all('ContNo').value);//指定传入的参数
	     var	strSQL=mySql1.getString();	
		
//	  var	strSQL = "select appflag from lccont where proposalcontno = '"+document.all('ContNo').value+"'";
	  arrResult1 = easyExecSql(strSQL,1,0);
	  if(arrResult1!=null&&arrResult1[0][0]=="1")
    	{
		   alert("该投保单已经签单!");
		   return;
	  	}
	  else if(arrResult1==null) {
	  	 alert("无此投保单信息!");
	  	 return;
	  	}
	 }
	
	if(document.all('ContNo').value==""&&document.all('tempfeeno').value!="")
	{
		
		var sqlid2="ProposalQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(document.all('tempfeeno').value);//指定传入的参数
	     var	strSQL=mySql2.getString();	
		
//	  var strSQL = "select appflag from lccont where contno = (select otherno from ljtempfee where tempfeeno = '"+document.all('tempfeeno').value+"' and rownum = '1')"	
	  arrResult1 = easyExecSql(strSQL,1,0);
	  if(arrResult1!=null&&arrResult1[0][0]=="1")
    	{
		   alert("该投保单已经签单!");
		   return;
	  	}
	  else if(arrResult1==null) {
	  	 alert("无此投保单信息!");
	  	 return;
	  	}
	}

	if(document.all('ContNo').value!=""&&document.all('tempfeeno').value!="")
	{
		
		var sqlid3="ProposalQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all('ContNo').value);//指定传入的参数
		mySql3.addSubPara(document.all('tempfeeno').value);//指定传入的参数
	     var	strSQL=mySql3.getString();	
		
//		var strSQL = "select appflag from lccont where proposalcontno = '"+document.all('ContNo').value+"' and contno = (select otherno from ljtempfee where tempfeeno = '"+document.all('tempfeeno').value+"' and rownum = '1')"	
	  arrResult1 = easyExecSql(strSQL,1,0);
	  if(arrResult1!=null&&arrResult1[0][0]=="1")
    	{
		   alert("该投保单已经签单!");
		   return;
	  	}
	  else if(arrResult1==null) {
	  	 alert("无此投保单信息!");
	  	 return;
	  	}
	}
	
	var strOperate="like";
	// 书写SQL语句
	
		var sqlid4="ProposalQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(document.all('ContNo').value);//指定传入的参数
		mySql4.addSubPara(document.all('PrtNo').value);//指定传入的参数
		mySql4.addSubPara(document.all('ManageCom').value);//指定传入的参数
		mySql4.addSubPara(document.all('PolApplyDate').value);//指定传入的参数
		
		mySql4.addSubPara(document.all('uwState').value);//指定传入的参数
		mySql4.addSubPara(document.all('AgentCode').value);//指定传入的参数
		mySql4.addSubPara(document.all('AppntNo').value);//指定传入的参数
		mySql4.addSubPara(document.all('AppntIDNo').value);//指定传入的参数
		
		mySql4.addSubPara(document.all('InsuredNo').value);//指定传入的参数
		mySql4.addSubPara(document.all('InsuredIDNo').value);//指定传入的参数
		mySql4.addSubPara(document.all('AppntName').value);//指定传入的参数
		mySql4.addSubPara(document.all('InsuredName').value);//指定传入的参数
		mySql4.addSubPara(document.all('uwgrade').value);//指定传入的参数
		mySql4.addSubPara(document.all('tempfeeno').value);//指定传入的参数
	
//	var strSql = "select l.ContNo,l.PrtNo,l.AppntName,l.insuredName,l.Operator,l.MakeDate,l.ApproveCode,l.ApproveDate,l.UWOperator,l.UWDate,l.UWFlag,(case UWFlag when '1' then '拒保' "+
//                               " when '2' then '延期' "+
//                               " when '4' then '次标准体' "+
//                               " when '9' then '标准体' "+
//                               " when 'a' then '撤保' "+
//                               " when 'z' then '核保订正' "+ 
//                               " end ),l.State,appntno from LCCont l where 1=1 and appflag='0'"
//    				+" and grpcontno='00000000000000000000'"
//    				+ getWherePart( 'l.ContNo','ContNo',strOperate)
//    				+ getWherePart( 'l.PrtNo','PrtNo',strOperate)
//    				+ getWherePart( 'l.ManageCom','ManageCom' , strOperate)
//    				+ getWherePart( 'l.PolApplyDate','PolApplyDate')
//    				+ getWherePart( 'l.UWFlag','uwState',strOperate )
//    				+ getWherePart( 'l.AgentCode','AgentCode',strOperate )
//    				+ getWherePart( 'l.AppntNo','AppntNo',strOperate)
//    				+ getWherePart( 'l.appntidno','AppntIDNo',strOperate)
//    				+ getWherePart( 'l.insuredno','InsuredNo',strOperate)
//    				+ getWherePart( 'l.insuredidno','InsuredIDNo',strOperate)
    				//alert(strSql);
    				
//   if(document.all('AppntName').value!="")
//   {
//   	strSql = strSql+" and l.AppntNo in (select CustomerNo from LDPerson where Name= '" + document.all('AppntName').value + "') ";
//   }
//   if(document.all('InsuredName').value!="")
//   {
//   	strSql = strSql+" and l.InsuredNo in (select CustomerNo from LDPerson where Name= '" + document.all('InsuredName').value + "') ";
//   }
//   if(document.all('uwgrade').value!="")
//    {
//    	strSql = strSql+" and exists (select 'x' from lwmission t where t.activityid in (select a.activityid from lwactivity a where a.functionid = '10010028') and trim(t.MissionProp2)=trim(l.ContNo) and t.MissionProp12 >= '"+document.all('uwgrade').value+"')";
//   	}
//   if(document.all('tempfeeno').value!="null"&&document.all('tempfeeno').value!="")
//    {
//    	strSql = strSql+" and exists (select 'x' from ljtempfee j where j.tempfeeno = '"+document.all('tempfeeno').value+"' and j.otherno = l.ContNo)";
//    	}
   var	strSql=mySql4.getString();
    			turnPage.strQueryResult = easyQueryVer3(strSql, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("无查询结果！");
    return "";
  }
	turnPage.queryModal(strSql, PolGrid);
}

var mSwitch = parent.VD.gVSwitch;


function queryDetailClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cProposalContNo = PolGrid.getRowColData(checkFlag - 1, 1); 	
  	var cprtno = PolGrid.getRowColData(checkFlag - 1, 2); 	
//  	mSwitch.deleteVar( "fm.value('PrtNo')" );
  	
//  	mSwitch.addVar( "fm.value('PrtNo')", "", cPrtNo);
  	 
    /*var strSql="";
   // strSql="select salechnl,cardflag from lccont where contno='"+cprtno+"'";
    strSql = "select case lmriskapp.riskprop"
           + " when 'I' then"
           + " '1'"
           + " when 'G' then"
           + " '2'"
           + " when 'Y' then"
           + " '3'"
           + " when 'T' then"
           + " '5'"
           + " end"
           + " from lmriskapp"
           + " where riskcode in (select riskcode"
           + " from lcpol"
           + " where polno = mainpolno"
           + " and contno = '"+cprtno+"')";
    arrResult = easyExecSql(strSql);
    var BankFlag = arrResult[0][0];
    //alert("a");
    */
	//去掉原来的判断投保单类型的逻辑，修改为按印刷号来判断投保单类型
	var BankFlag = "";
    var tSplitPrtNo = cprtno.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
    
			var sqlid5="ProposalQuerySql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(cprtno);//指定传入的参数
	    strSql=mySql5.getString();	
	
//    strSql="select salechnl,cardflag from lccont where contno='"+cprtno+"'";
    arrResult = easyExecSql(strSql);

    var CardFlag = arrResult[0][1]; 
    if(BankFlag==""||BankFlag==null||CardFlag=="3")
    BankFlag="4";       
  	 
    urlStr = "./ProposalEasyScan.jsp?LoadFlag=6&ContNo="+cProposalContNo+"&prtNo="+cprtno+"&BankFlag="+BankFlag+"&SubType=01" ,"status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";   
  
    window.open(urlStr,"",sFeatures);
  }
  else {
    alert("请先选择一条保单信息！"); 
  }
}


function queryMARiskClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cMainPolNo = PolGrid.getRowColData(tSel - 1,1);	
	    var cPrtNo = PolGrid.getRowColData(tSel - 1,2);			

		
		if (cMainPolNo == "")
		    return;
		    
		  window.open("./MainOddRiskQueryMain.jsp?MainPolNo=" + cMainPolNo + "&PrtNo=" + cPrtNo,"",sFeatures);										
	}
}

//扫描件查询
function ImageQuery() {
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else {
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (prtNo == "") return;
		    
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		window.open("../uw/ImageQueryMain.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}
//保费项查询
function PremQuery() {
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else {
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (prtNo == "") return;
	//	alert(prtNo);    
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		window.open("../sys/PolRiskQueryMain.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}


//操作履历查询
function OperationQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	window.open("../sys/RecordQueryMain.jsp?ContType=1&ContNo="+ContNo+"&PrtNo="+PrtNo,"",sFeatures);	
  }										
} 

function InsuredQuery(){
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	  window.open("ContInsuredQueryMain.jsp?prtNo="+PrtNo,"window1",sFeatures); 
  }										
	
	
}
function SpecQuery(){
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else {
	    var ContNo = PolGrid.getRowColData(tSel - 1,1);
	    var PrtNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (PrtNo == "" || ContNo == "") return;
			
	  window.open("../uw/UWManuSpecMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&QueryFlag=1","window1",sFeatures);  	
  }										
	
	
}

//保额累计
function amntAccumulate(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }
	window.open("./AmntAccumulateMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//已承保查询
function queryProposal(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }

	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}


//未承保查询
function queryNotProposal(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }

	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//既往理赔查询
function queryClaim(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }

	window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//既往保全查询
function queryEdor(){
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }

	window.open("../uw/EdorQueryMain.jsp?CustomerNo="+PolGrid.getRowColData(PolGrid.getSelNo() - 1,14),"window1",sFeatures);
}

//查询暂交付
function showTempFee() 
{
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }
   var tPrtno =PolGrid.getRowColData(PolGrid.getSelNo() - 1,2);
	 var tAppntNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1,14);
	 var tAppntName = PolGrid.getRowColData(PolGrid.getSelNo() - 1,3);
   //alert (Prtno);
  window.open("../uw/UWTempFeeQryMain.jsp?PrtNo=" + tPrtno + "&AppntNo=" + tAppntNo + "&AppntName=" +tAppntName,"",sFeatures);
} 


//查询暂交付
function statequery() 
{
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null ){
	  alert( "请先选择一条记录。" );
	  return;
  }
   var tProposalContno=PolGrid.getRowColData(PolGrid.getSelNo() - 1,1);
	 window.open("../uw/PolStatusMain.jsp?PrtNo="+tProposalContno,"window11",sFeatures);
}


//点击PolGrid触发的函数

function clickPolGrid(){
  //控制按钮的明暗	
  ctrlButtonDisabled();
}


//控制界面按钮的明暗显示
function ctrlButtonDisabled(){
	var tContNo = PolGrid.getRowColData(PolGrid.getSelNo()-1,1);
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  		var sqlid6="ProposalQuerySql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tContNo);//指定传入的参数
	    var strSql6=mySql6.getString();	
  
    	var sqlid7="ProposalQuerySql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(tContNo);//指定传入的参数
	    var strSql7=mySql7.getString();	
  
      	var sqlid8="ProposalQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(tContNo);//指定传入的参数
	    var strSql8=mySql8.getString();	
  
    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "Button1";
    arrButtonAndSQL[0][1] = "投保人已承保保单查询";
    arrButtonAndSQL[0][2] = strSql6;//"select * from lccont a, lcinsured b where 1 = 1 and a.contno = b.contno and a.appflag in ('1', '4') and b.insuredno in (select appntno from lcappnt where contno = '"+tContNo+"')";

    arrButtonAndSQL[1] = new Array;
    arrButtonAndSQL[1][0] = "Button2";
    arrButtonAndSQL[1][1] = "投保人未承保保单查询";
    arrButtonAndSQL[1][2] = strSql7//"select * from lccont a, lcinsured b where a.contno = b.contno  and a.appflag  in('0','2') and b.insuredno in (select appntno from lcappnt where contno='"+tContNo+"')";

    arrButtonAndSQL[2] = new Array;
    arrButtonAndSQL[2][0] = "Button3";
    arrButtonAndSQL[2][1] = "投保人既往保全查询";
    arrButtonAndSQL[2][2] =strSql8;// "select * from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno = (select appntno from lcappnt where contno='"+tContNo+"'))";

      	var sqlid9="ProposalQuerySql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(tContNo);//指定传入的参数
		mySql9.addSubPara(tContNo);//指定传入的参数
	    var strSql9=mySql9.getString();	

    arrButtonAndSQL[3] = new Array;
    arrButtonAndSQL[3][0] = "Button4";
    arrButtonAndSQL[3][1] = "投保人既往理赔查询";
    arrButtonAndSQL[3][2] =strSql9;// "select 1 from llregister a, llcase b where a.rgtno = b.caseno and b.CustomerNo in (select appntno from lcappnt where contno='"+tContNo+"') union select 1 from llreport a, llsubreport b, ldperson c where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' and b.CustomerNo in (select appntno from lcappnt where contno='"+tContNo+"')";

    arrButtonAndSQL[4] = new Array;
    arrButtonAndSQL[4][0] = "Button5";
    arrButtonAndSQL[4][1] = "投保单明细";
    arrButtonAndSQL[4][2] = "";

      	var sqlid10="ProposalQuerySql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(tContNo);//指定传入的参数
	    var strSql10=mySql10.getString();	

    arrButtonAndSQL[5] = new Array;
    arrButtonAndSQL[5][0] = "Button6";
    arrButtonAndSQL[5][1] = "投保单影像查询";
    arrButtonAndSQL[5][2] =strSql10;// "select * from es_doc_relation where bussno='"+tContNo+"' and bussnotype='11' and rownum=1";

    arrButtonAndSQL[6] = new Array;
    arrButtonAndSQL[6][0] = "Button7";
    arrButtonAndSQL[6][1] = "被保人查询";
    arrButtonAndSQL[6][2] = "";

    arrButtonAndSQL[7] = new Array;
    arrButtonAndSQL[7][0] = "Button8";
    arrButtonAndSQL[7][1] = "保费项查询";
    arrButtonAndSQL[7][2] = "";

    arrButtonAndSQL[8] = new Array;
    arrButtonAndSQL[8][0] = "Button9";
    arrButtonAndSQL[8][1] = "操作履历查询";
    arrButtonAndSQL[8][2] = "";

      	var sqlid11="ProposalQuerySql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ProposalQuerySql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(tContNo);//指定传入的参数
	    var strSql11=mySql11.getString();	

    arrButtonAndSQL[9] = new Array;
    arrButtonAndSQL[9][0] = "Button10";
    arrButtonAndSQL[9][1] = "暂交费查询";
    arrButtonAndSQL[9][2] =strSql11;// "select * from ljtempfee where otherno='"+tContNo+"' and rownum=1";

    arrButtonAndSQL[10] = new Array;
    arrButtonAndSQL[10][0] = "Button11";
    arrButtonAndSQL[10][1] = "状态查询";
    arrButtonAndSQL[10][2] = "";
    

  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
  	
}

