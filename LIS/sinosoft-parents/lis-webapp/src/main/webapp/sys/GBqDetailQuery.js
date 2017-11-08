//程序名称：
//程序功能：保全查询

var showInfo;
var mDebug    = "1";
var aEdorFlag = "0";
var turnPage  = new turnPageClass();  
var mEdorType;

//保单、客户查询标识
var mflag = "";  

/*********************************************************************
 *  页面层显示控制
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initDiv()
{
		//alert("This is initDiv");
    var EdorAcceptNo = fm.all('EdorAcceptNo').value;    
    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("保全受理号为空！");
        return;
    }
    var strSQL;
    
    //查询工作流保全申请确认节点
//    strSQL =  " select OtherNo, OtherNoType, EdorAppName, AppType, EdorAppDate , EdorState," 
//           + " (select CodeName from LDCode a where a.CodeType = 'EdorState' and a.Code = EdorState)," 
//           + " BankCode, "            
//           + " (select CodeName from LDCode where CodeType = 'EdorAppType' and Code = AppType) " 
//           + " from LPEdorApp "
//           + " where EdorAcceptNo = '" + EdorAcceptNo + "' "; 
    
        var sqlid1="GBqDetailQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.GBqDetailQuerySql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(EdorAcceptNo);//指定传入参数
	 	strSQL = mySql1.getString();
    
    var brr = easyExecSql(strSQL); 		
    
    //已经申请保存过
    if ( brr )  
    {
        //alert("已经申请保存过");
        //hasSaved = "Y";
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value            = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoType.value        = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.EdorAppName_Read.value   = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppType.value            = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.EdorAppDate_Read.value   = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.PEdorState.value         = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.PEdorStateName_Read.value= brr[0][6];        
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.BankCode.value           = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppType_Read.value       = brr[0][8];
        //brr[0][10]==null||brr[0][8]=='null'?'0':fm.OtherNoType_Read.value = brr[0][8];        
        //brr[0][8]==null||brr[0][8]=='null'?'0':fm.BankAccNo.value   = brr[0][8];
        //brr[0][9]==null||brr[0][9]=='null'?'0':fm.AccName.value     = brr[0][9];
        //brr[0][12]==null||brr[0][12]=='null'?'0':fm.BankName.value     = brr[0][12];
        //brr[0][13]==null||brr[0][13]=='null'?'0':fm.PayGetName_Read.value     = brr[0][13];
        //brr[0][14]==null||brr[0][14]=='null'?'0':fm.PersonID_Read.value     = brr[0][14];
 
        fm.EdorAcceptNo_Read.value= fm.EdorAcceptNo.value;
        fm.OtherNo_Read.value     = fm.OtherNo.value;
        //fm.OtherNoType_Read.value = fm.OtherNoName.value;
        //fm.EdorAppName_Read.value = fm.EdorAppName.value;
        //fm.AppType_Read.value     = fm.AppTypeName.value;
        //fm.EdorAppDate_Read.value = fm.EdorAppDate.value;
        //fm.PEdorStateName_Read.value = fm.PEdorStateName.value;
        //fm.PayGetName_Read.value = fm.PayGetName.value;
        //fm.PersonID_Read.value = fm.PersonID.value;
                				
                    
        
        //团体保单号
        if(fm.OtherNoType.value == "4")
        {
        		//查询保单详细信息	        		
        		displayContInfo(fm.OtherNo.value);          		
        		
        		//查询保全项目列表信息        		
        		getEdorItemGrid();          		
        		
        		//保全项目信息
        		divEdorItemInfo.style.display = '';        		
        }            
    }
    else
    {
        alert("没有该保全受理信息");
    }         
}

/*********************************************************************
 *  查询客户详细信息
 *  描述: 查询客户详细信息
 *  参数  ：  CustomerNo
 *  返回值：  无
 *********************************************************************
 */
function displayCustomerInfo(CustomerNo)
{
    var strSQL;    
//    strSQL =  " select CustomerNo, Name, Sex, Birthday, " 
//            + " IDType, IDNo, Marriage, Health, " 
//            + " RgtAddress, VIPValue, Salary, GrpName " 
//            + " from LDPerson "
//            + " where CustomerNo = '" + CustomerNo + "'";
    
    var sqlid2="GBqDetailQuerySql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("sys.GBqDetailQuerySql");
 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
 	mySql2.addSubPara(CustomerNo);//指定传入参数
 	strSQL = mySql2.getString();
    
    var brr = easyExecSql(strSQL);    
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.CustomerNo.value  = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.Name.value        = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.Sex.value         = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.Birthday.value    = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.IDType.value      = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.IDNo.value        = brr[0][5];
        //brr[0][6]==null||brr[0][6]=='null'?'0':fm.Marriage.value    = brr[0][6];
        //brr[0][7]==null||brr[0][7]=='null'?'0':fm.Health.value      = brr[0][7];
        
        //brr[0][8]==null||brr[0][8]=='null'?'0':fm.RgtAddress.value  = brr[0][8];
        //brr[0][9]==null||brr[0][9]=='null'?'0':fm.VIPValue.value    = brr[0][9];
        //brr[0][10]==null||brr[0][10]=='null'?'0':fm.Salary.value    = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.GrpName.value   = brr[0][11];
    }            
}


/*********************************************************************
 *  查询保单详细信息
 *  描述: 查询保单详细信息
 *  参数  ：  ContNo
 *  返回值：  无
 *********************************************************************
 */
function displayContInfo(ContNo)
{
  //alert("this is displayContInfo"); 	
    var strSQL;
    
    /*
    strSQL =  " select ContNo, AppntName, InsuredName, Prem, Amnt, " 
           + " AgentCode, GetPolDate, CValiDate, PaytoDate " 
           + " from lccont "
           + " where ContNo = '" + ContNo + "'";
    */
//    strSQL = " select GrpContNo, GrpName, CValiDate, Peoples2, Prem, Amnt"  
//           + " from LCGrpCont "
//           + " where GrpContNo = '" + ContNo + "'";   
    
    var sqlid3="GBqDetailQuerySql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("sys.GBqDetailQuerySql");
 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
 	mySql3.addSubPara(ContNo);//指定传入参数
 	strSQL = mySql3.getString();
    
 
    var brr = easyExecSql(strSQL);
    
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNoApp.value = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.GrpName.value   = brr[0][1];        
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.CValiDate.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppntName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Prem.value      = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.Amnt.value      = brr[0][5];                
        //brr[0][5]==null||brr[0][5]=='null'?'0':fm.AgentCode.value   = brr[0][5];
        //brr[0][6]==null||brr[0][6]=='null'?'0':fm.GetPolDate.value    = brr[0][6];
    }            
    else{  	
  			alert("查询保单详细信息失败！");
  			return;
  	}
  }



/*********************************************************************
 *  MulLine的RadioBox点击事件，显示项目明细按钮
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getEdorItemDetail()
{
    var tSelNo = EdorItemGrid.getSelNo() - 1;    
    fm.EdorNo.value =          EdorItemGrid.getRowColData(tSelNo, 2);
    fm.ContNo.value =          EdorItemGrid.getRowColData(tSelNo, 6);   
    fm.InsuredNo.value =       EdorItemGrid.getRowColData(tSelNo, 7);
    fm.PolNo.value =           EdorItemGrid.getRowColData(tSelNo, 8);
    fm.EdorItemAppDate.value = EdorItemGrid.getRowColData(tSelNo, 10);
    fm.EdorValiDate.value =    EdorItemGrid.getRowColData(tSelNo, 11);
    fm.MakeDate.value =        EdorItemGrid.getRowColData(tSelNo, 14);
    fm.MakeTime.value =        EdorItemGrid.getRowColData(tSelNo, 15);
    fm.EdorItemState.value =   EdorItemGrid.getRowColData(tSelNo, 20);
    fm.EdorType.value =        EdorItemGrid.getRowColData(tSelNo, 21);    
    fm.EdorTypeCal.value =        EdorItemGrid.getRowColData(tSelNo, 22);    
    fm.all('ContType').value = "1";
    fm.CustomerNoBak.value = fm.InsuredNo.value;
    fm.ContNoBak.value = fm.ContNo.value;
//    var strSQL =  " select edorname from lmedoritem where appobj='G' and edorcode='"+fm.EdorType.value+"' ";
    
    var sqlid4="GBqDetailQuerySql4";
 	var mySql4=new SqlClass();
 	mySql4.setResourceName("sys.GBqDetailQuerySql");
 	mySql4.setSqlId(sqlid4); //指定使用SQL的id
 	mySql4.addSubPara(fm.EdorType.value);//指定传入参数
 	var strSQL = mySql4.getString();
    
    var tResult = easyExecSql(strSQL);
    if(tResult.length>0)
    {
    	fm.EdorTypeName.value= tResult[0][0] ;
    }else{
    	alert("没有查询到项目名称") ;
    }
}

/*********************************************************************
 *  查询保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPEdorItemGrid()
{
		//alert("This is getPEdorItemGrid");
		initEdorItemGrid();
    var tEdorAcceptNo = fm.all('EdorAcceptNo').value;
    if(tEdorAcceptNo != null) {    	
    	
//        var strSQL = "select EdorAcceptNo, EdorNo, (select distinct concat(concat(EdorCode,'-'),"
//                   + "EdorName) from LMEdorItem m where m.EdorCode = EdorType and appobj='G'), "
//                   + "EdorTypeCal, DisplayType, GrpContNo, '', '', '', EdorAppDate, "
//                   + "EdorValiDate, (select CodeName from LDCode a where a.CodeType = '"
//                   + "edorappreason' and a.Code = AppReason), AppReason, GetMoney, "
//                   + "MakeDate, MakeTime, ModifyDate, Operator, (select CodeName from "
//                   + "LDCode b where b.CodeType = 'edorstate' and b.Code = EdorState), "
//                   + "EdorState,EdorType from LPGrpEdorItem where EdorAcceptNo = '"
//                   + tEdorAcceptNo + "'order by MakeDate asc, MakeTime asc";
                    //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";        
        //turnPage.queryModal(strSQL, EdorItemGrid);
        
        
        var sqlid5="GBqDetailQuerySql5";
     	var mySql5=new SqlClass();
     	mySql5.setResourceName("sys.GBqDetailQuerySql");
     	mySql5.setSqlId(sqlid5); //指定使用SQL的id
     	mySql5.addSubPara(fm.EdorType.value);//指定传入参数
     	var strSQL = mySql5.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult) {        	
            displayMultiline(arrResult,EdorItemGrid);
        } else {
        	alert("保全明细查询失败！");
        	return;
        }
    }
}

/*********************************************************************
 *  返回
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent()
{	
	
	top.close();
}

//保全查询
function QueryEdorClick()
{
	var arrReturn = new Array();
	var cEdorAcceptNo = fm.all('EdorAcceptNo').value;
	var tSel = EdorItemGrid.getSelNo();
	if( tSel == 0 || tSel == null ){
		alert( "请先选择一条保全项目记录。" );
		return;
	} else{     
		var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);				
		if(tEdortype == 0 || tEdortype == null){
		  	alert("本次保全申请没有保全项目，请另选择保全申请");
		  	return false;
		}
		fm.all('EdorType').value=tEdortype;
		fm.all('ContType').value = '2';
	    var cEdorType = EdorItemGrid.getRowColData(tSel - 1, 1);	    
	    var cPolNo = EdorItemGrid.getRowColData(tSel - 1, 3); 
		  //if (cEdorType == ""||cContNo == "")
		    //return;
		  //var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
	    //window.open("../bq/PEdorType"+tEdortype+"Input.jsp?splflag=1");
	   if (fm.EdorItemState.value == '0' || fm.EdorItemState.value == '6') {
	   		QueryEdorRecipt();
	   } else {
	   		//window.open("../bq/PEdorType" +tEdortype+ ".jsp", "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	   	   detailEdorType();
	   	}
	}	
}

//==================
function QueryPEdor()
{
	var newWindow = OpenWindowNew("../bq/QueryPEdor.jsp?Interface= ../sys/BqDetailQuery.jsp","保全批改查询","left");
}

//====================	
function QueryEdorRecipt()
{	
	var newWindow = OpenWindowNew("../f1print/AppEndorsementF1PJ1.jsp");
	fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
  fm.target = "f1print";
  fm.submit();
}

//==================
function QueryPhoto()
{
	 var EdorAcceptNo    = fm.all('EdorAcceptNo').value;
	 var MissionID       = fm.all('MissionID').value;
   var SubMissionID    = fm.all('SubMissionID').value;
//	 var str = "select DocId from es_doc_relation where BussNo = '" 
//	         + EdorAcceptNo 
//	         + "' and BussType = 'BQ' and RelaFlag = '0'";
	 
	var sqlid6="GBqDetailQuerySql6";
  	var mySql6=new SqlClass();
  	mySql6.setResourceName("sys.GBqDetailQuerySql");
  	mySql6.setSqlId(sqlid6); //指定使用SQL的id
  	mySql6.addSubPara(EdorAcceptNo);//指定传入参数
  	var str = mySql6.getString();
	 
	 var arrResult = easyExecSql(str);
	 if(arrResult == null){
	 	  alert("此次保全申请没有相关扫描影像资料！");
	 	  return; 
	 }
	 var PrtNo = arrResult[0][0];
	 
//	 var sql="select a.CodeAlias from LDCode a,es_doc_relation b where a.CodeType = 'bqscan' and a.Code = b.SubType and b.BussType = 'BQ' and b.BussNo = '"+ EdorAcceptNo + "'";
	 
	    var sqlid7="GBqDetailQuerySql7";
	  	var mySql7=new SqlClass();
	  	mySql7.setResourceName("sys.GBqDetailQuerySql");
	  	mySql7.setSqlId(sqlid7); //指定使用SQL的id
	  	mySql7.addSubPara(EdorAcceptNo);//指定传入参数
	  	var sql = mySql7.getString();
	 
	 var tResult = easyExecSql(sql 1, 0);
	 if(tResult == null){
		  alert("查询保全扫描子业务类型编码失败！");
		  return;
	 }
	 var varSrc = "&ScanFlag = 1&prtNo=" + PrtNo + "&EdorAcceptNo=" + EdorAcceptNo + "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID + "&SubType=" + tResult[0][0];
	 var newWindow = OpenWindowNew("../bq/EdorScan.jsp?" + varSrc,"保全扫描影像","left");
}

/*********************************************************************
 *  查询保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getEdorItemGrid()
{ 
	//alert("This is getEdorItemGrid");	
  initEdorItemGrid();
  var tEdorAcceptNo = fm.all('EdorAcceptNo').value;
  var tOtherNoType  = fm.all('OtherNoType').value;
  var arrResult     = "";  
  if(tEdorAcceptNo != null)
    {
      
		  
		  //团体保单号
		  if(tOtherNoType == '4')
		   { 		    	
//         var strSQL = " select EdorAcceptNo, EdorNo, (select distinct concat(concat(EdorCode"
//                    + ",'-'),EdorName) from LMEdorItem m where  m.EdorCode = EdorType and appobj='G'), "
//                    + "EdorTypeCal, DisplayType, GrpContNo, '', '', '', EdorAppDate, "
//                    + "EdorValiDate, (select CodeName from LDCode a where a.CodeType = '"
//                    + "edorappreason' and a.Code = AppReason), AppReason, GetMoney, "
//                    + "MakeDate, MakeTime, ModifyDate, Operator, (select CodeName from "
//                    + "LDCode b where b.CodeType = 'edorstate' and b.Code = EdorState), "
//                    + "EdorState,EdorType,EdorTypeCal from LPGrpEdorItem where EdorAcceptNo = '" 
//                    + tEdorAcceptNo + "' order by MakeDate asc, MakeTime asc";
	                   //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";
         
        var sqlid8="GBqDetailQuerySql8";
 	  	var mySql8=new SqlClass();
 	  	mySql8.setResourceName("sys.GBqDetailQuerySql");
 	  	mySql8.setSqlId(sqlid8); //指定使用SQL的id
 	  	mySql8.addSubPara(tEdorAcceptNo);//指定传入参数
 	  	var strSQL = mySql8.getString();
         
	       turnPage.queryModal(strSQL, EdorItemGrid);
		  }
    }
    else
	  {
	    	alert("保全受理号为空！")
	     	return;		      	
	  }	
}


	
function detailEdorType()
{
		
	switch(fm.all('EdorType').value) 
		{
			case "WT":
				if (fm.all('ContType').value =='1')
				{
					var newWindow = window.open("../bq/PEdorTypeWT.html","PEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				else
				{
					var newWindow = window.open("../bq/GEdorTypeWT.html","GEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				break;
				
			default:
				//alert(fm.all('ContType').value);
				if (fm.all('ContType').value == '1') //打开默认个体保全项目的明细界面
				{
					alert("P");
			 		window.open("../bq/PEdorType" + trim(fm.all('EdorType').value) + ".jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			 	}
				else  //打开团险保全明细
				{
				  //打开团险单选保全明细综合页面
				  if (fm.all('EdorType').value=="BB" ||
				      fm.all('EdorType').value=="PR" ||
				  	  fm.all('EdorType').value=="IC" || 
				  	  fm.all('EdorType').value=="GT" || 
				  	  fm.all('EdorType').value=="IO" || 
				  	  fm.all('EdorType').value=="GC" || 
				  	  fm.all('EdorType').value=="YC" || 
				  	  fm.all('EdorType').value=="PT" ||
				  	  fm.all('EdorType').value=="AA" ||
				  	  fm.all('EdorType').value=="GA" ||
				  	  fm.all('EdorType').value=="GB" ||
				  	  fm.all('EdorType').value=="GM" ||
				  	  fm.all('EdorType').value=="BG") 
				  {
					  var newWindow = window.open("../bq/GEdorTypeDetail.jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				      newWindow.focus();
				  }
				  else if (fm.all('EdorType').value=="BC" ||
				         fm.all('EdorType').value=="RE" ||				         
				  		   fm.all('EdorType').value=="RC" ||
				  		   fm.all('EdorType').value=="GC")
				  {
				  	  window.open("../bq/GEdorTypeRiskDetail.jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  //打开团险复选保全明细综合页面 del by wenhuan
				  else if (fm.all('EdorType').value=="ZT") 
				  {
				      window.open("../bq/GEdorTypeMultiDetail.jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  //打开团险复选保全明细综合页面
				  else if ( fm.all('EdorType').value=="LT" ) 
				  {
				      window.open("../bq/GEdorTypeCT.jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				      //window.open("./GEdorTypeMultiRisk.jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  else if ( fm.all('EdorType').value=="NR" ) 
				  {
				      window.open("../bq/GrpEdorTypeNR.jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  else if ( fm.all('EdorType').value=="BS" ) //add by wanzh 2006-04-18 进入‘保险期间中断’操作页面
				  {
				  	  
				  	  var tGrpContNo    = fm.OtherNo.value;
				  	  var tEdorType     = fm.EdorType.value;
				  	  var tEdorAcceptNo = fm.EdorAcceptNo.value;
				      window.open("../bq/GrpEdorTypeBSMain.jsp?GrpContNo="+tGrpContNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo, "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=20,left=20,toolbar=20,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  else if ( fm.all('EdorType').value=="BR" ) //add by wanzh 2006-04-18 进入‘保险期间恢复’操作页面
				  {
				  	  var tGrpContNo    = fm.OtherNo.value;
				  	  var tEdorType     = fm.EdorType.value;
				  	  var tEdorAcceptNo = fm.EdorAcceptNo.value;
				      window.open("../bq/GrpEdorTypeBRMain.jsp?GrpContNo="+tGrpContNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo, "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=20,left=20,toolbar=20,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  else //打开默认团险保全项目的明细界面
				  {				  
				  	//alert("G");
				  	//alert(fm.all('EdorType').value);
					  window.open("../bq/GEdorType" + trim(fm.all('EdorType').value) + ".jsp", "PEdorType" + trim(fm.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
			  }
				break;
		}
			
}