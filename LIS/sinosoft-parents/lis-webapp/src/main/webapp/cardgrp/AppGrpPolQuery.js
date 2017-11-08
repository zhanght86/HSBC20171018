//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
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

function getcodedata2()
{
	var sql="select RiskCode, RiskName from LMRiskApp where (enddate is null or enddate>'"+fm.sysdate.value+"') and riskprop in ('G','D') "
	         +" union select riskcode,(select riskname from lmrisk where riskcode=lmriskcomctrl.riskcode) from LMRiskComCtrl where "
	         +" startdate<='"+fm.sysdate.value+"' and (enddate is null or enddate>'"+fm.sysdate.value+"') and ManageComGrp='"+document.all('ManageCom').value+"' "
	         +"  and (select distinct(riskprop) from lmriskapp where riskcode =lmriskcomctrl.riskcode)in ('G','D') order by RiskCode";
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
	var tSel = GrpPolGrid.getSelNo();
	
  if (GrpPolGrid.getSelNo() == 0) {
    alert("请先选择一条保单信息！");
    return false;
  } 
  var polNo = GrpPolGrid.getRowColData(GrpPolGrid.getSelNo() - 1, 1);
  var prtNo = GrpPolGrid.getRowColData(GrpPolGrid.getSelNo() - 1, 2);
        mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);

   //var tsql="select subtype From es_doc_main where doccode='"+polNo+"'";
   //			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			if(crr[0][0]=="1000")
 	//			{
 	//			 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000011099' and missionprop1='"+polNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}

  easyScanWin = window.open("./GroupPolApproveInfo.jsp?LoadFlag=16&SubType="+SubType+"&prtNo="+polNo+"&polNo="+polNo+"&CardFlag=1", "", sFeatures);    
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpPolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	
	// 初始化表格
	initGrpPolGrid();
		
	//alert(contNo);
	// 书写SQL语句
	var strSQL = "";
	var strOperate="like";
	//if(manageCom=="%")
	//{
	var tRiskCode = fm.RiskCode.value;
	if(tRiskCode==""){
	strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate,AppFlag from LCGrpCont where 1=1 "
				 +" and AppFlag='0' and cardflag ='1'"
				 + getWherePart( 'PrtNo','PrtNo',strOperate )
				 + getWherePart( 'ProposalGrpContNo','GrpProposalNo',strOperate )
				 + getWherePart( 'ManageCom','ManageCom',strOperate )
				 + getWherePart( 'AgentCode','CManagerCode',strOperate )
				 //+ getWherePart( 'AgentGroup','AgentGroup',strOperate )
				 + getWherePart( 'SaleChnl','SaleChnl',strOperate )
				+ "order by PrtNo"
				;
	   }else {
	   strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate,AppFlag from LCGrpCont where 1=1 "
				 +" and AppFlag='0' and cardflag ='1'"
				 + getWherePart( 'PrtNo','PrtNo',strOperate )
				 + getWherePart( 'ProposalGrpContNo','GrpProposalNo',strOperate )
				 + getWherePart( 'ManageCom','ManageCom',strOperate )
				 + getWherePart( 'AgentCode','CManagerCode',strOperate )
				 //+ getWherePart( 'AgentGroup','AgentGroup',strOperate )
				 + getWherePart( 'SaleChnl','SaleChnl',strOperate )
				//+ "order by PrtNo"
				;
	    strSQL = strSQL+" and prtno in (select prtno from lcpol where riskcode = '"+tRiskCode+"') order by prtno";
	   }
		//	}else{
		//	strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate,AppFlag from LCGrpCont where 1=1 and managecom like '"+manageCom+"%%'"
		//		 +" and AppFlag='0'"
		//		 + getWherePart( 'PrtNo','PrtNo',strOperate )
		//		 + getWherePart( 'ProposalGrpContNo','GrpProposalNo',strOperate )
				 //+ getWherePart( 'AgentCode','AgentCode',strOperate )
				 //+ getWherePart( 'AgentGroup','AgentGroup',strOperate )
		//		 + getWherePart( 'SaleChnl','SaleChnl',strOperate )
		//		+ "order by PrtNo"
		//}
//alert(strSQL);
	//execEasyQuery( strSQL );
	turnPage.queryModal(strSQL, GrpPolGrid);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initGrpPolGrid();
		//HZM 到此修改
		GrpPolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpPolGrid.loadMulLine(GrpPolGrid.arraySave);		
		//HZM 到此修改
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//GrpPolGrid.delBlankLine();
	} // end of if
}
/*********************************************************************
 *  点击扫描件查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var prtNo = GrpPolGrid.getRowColData(tSel - 1,2);				

		
		if (prtNo == "")
		    return;


    //var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' and subtype!='3000' "; //这里排出查协议的扫描件，如果既有协议又有"团险投保书_年金险种投保单"扫描件那么会有问题。
 	//			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			  if(crr[0][0]=="1000")
 	//			  {
 	//			    tsubtype="TB1002";
 	//			  }else{
 	//			    tsubtype="TB1003";
 	//			  }
 	//		  }else{
 	//		     tsubtype="TB1002";
 	//	    }
 	var strSql2="select missionprop5 from lbmission where activityid='0000011099' and missionprop1='"+prtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
		 window.open("../sys/ProposalEasyScan.jsp?BussType=TB&BussNoType=12&SubType="+SubType+"&prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
	}	     
}
