
//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tDisplay;
var arrDataSet;
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

/**=========================================================================
    修改状态：开始
    修改原因：在SQL语句中区分主险和附险，并显示保单状态和保险止期
    修 改 人：万泽辉
    修改日期：2005.10.25
   =========================================================================
**/

function easyQueryClick()
{
	// 书写SQL语句
	var strSQL = "";
	if(tIsCancelPolFlag=="0"){
	
var sqlid43="ProposalQuerySql43";
var mySql43=new SqlClass();
mySql43.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql43.setSqlId(sqlid43);//指定使用的Sql的id
mySql43.addSubPara(tContNo );//指定传入的参数
strSQL=mySql43.getString();
		
//	 strSQL =   "(select a.grppolno,a.polno,a.prtno,a.appntname,a.insuredname,a.riskcode,b.riskshortname,a.signcom,"+
//	            "(case a.appflag when '1' then '承保' when '4' then '终止' end) as appflag,"+
//	            "a.amnt,a.cvalidate,a.enddate,a.payenddate,a.agentcode,a.uwflag, "+
//	            "(select codename from ldcode where codetype = 'payintv' and code = a.payintv),"+
//	            "a.paytodate,substr(a.polstate, 1, 4),a.payyears,a.prem,"+
//	            "(select nvl(sum(prem), 0) from lcprem where polno = a.polno and payplantype = '0' and payenddate = a.payenddate and length(dutycode)=6),"+
//	            "(select nvl(sum(prem), 0) from lcprem where polno = a.polno and payplantype in ('01', '03') and payenddate = a.payenddate ),"+ 
//	            "(select nvl(sum(prem), 0) from lcprem where polno = a.polno and payplantype in ('02', '04') and payenddate = a.payenddate ),"+
//	            "a.insuredappage "+
//	            "from lcpol a, lmrisk b where a.riskcode = b.riskcode  "+
//	            "and a.contno = '"+tContNo+"' and a.appflag in ('1','4','0'))"+//09-05-31  增加appflag=0的条件
//	            "order by a.appflag asc, a.cvalidate desc, a.riskcode asc ";
	}

	else
	 if(tIsCancelPolFlag=="1"){//销户保单查询
	 
	 var sqlid44="ProposalQuerySql44";
var mySql44=new SqlClass();
mySql44.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql44.setSqlId(sqlid44);//指定使用的Sql的id
mySql44.addSubPara(tContNo );//指定传入的参数
strSQL=mySql44.getString();
	 
	   //strSQL = "select LBPol.grppolno,LBPol.PolNo,LBPol.PrtNo,LBPol.AppntName,LBPol.InsuredName,LBPol.RiskCode,LBPol.ManageCom,LBPol.Prem ,LBPol.Amnt ,LBPol.CValiDate,LBpol.AgentCode,uwflag,substr(LBPol.PolState,1,4) from LBPol where LBPol.MainPolNo='" + tPolNo + "' and appflag<>'9' order byLBPol.proposalno";
//	   alert("销户保单查询"+strSQL);
	}
	else
	{
	  alert("保单类型传输错误!");
	  return ;
	}
	//查询SQL，返回结果字符串
//	prompt("",strSQL);
	turnPage1.pageLineNum = 5;
	turnPage1.queryModal(strSQL, PolGrid);

   //查询被保人信息部分
   initInsuredGrid();
   var strSQL1 = "";
   var arr=new Array;
   
var sqlid45="ProposalQuerySql45";
var mySql45=new SqlClass();
mySql45.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql45.setSqlId(sqlid45);//指定使用的Sql的id
mySql45.addSubPara(tContNo );//指定传入的参数
strSQL1=mySql45.getString();
   
//   strSQL1 = " select name,insuredno,(select codename from ldcode where codetype='sex' and code=sex),(select codename from ldcode where codetype='idtype' and code=idtype),idno,(select codename from ldcode where codetype='nativeplace' and code=nativeplace),(select codename from ldcode where codetype='occupationtype' and code=occupationtype),birthday from lcinsured  where 1=1	"
//	           +" and contno='"+ tContNo +"' "
//	           +" order by contno";


	 arr = easyExecSql(strSQL1);
    if (arr)
    {
        displayMultiline(arr,InsuredGrid);
    }
    else
    {
        initInsuredGrid();
    }
}


//查询合同信息及投保人信息
function initContAndAppnt()
{
	var arrReturn1 = new Array();
	var arrReturn2 = new Array();

		try{
			
var sqlid46="ProposalQuerySql46";
var mySql46=new SqlClass();
mySql46.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql46.setSqlId(sqlid46);//指定使用的Sql的id
mySql46.addSubPara(tContNo );//指定传入的参数
var strSQL=mySql46.getString();
			
			//var strSQL = "select a.grpcontno,a.contno ,a.prtno,a.managecom,a.salechnl,a.agentcode,a.agentgroup,a.agentcom,a.insuredno,a.paymode,a.poltype,a.signcom,a.customgetpoldate ,(select name from ldcom where comcode = a.managecom)||(select b.name from labranchgroup b where b.agentgroup = (select d.agentgroup from laagent d where d.agentcode = a.agentcode)) from lccont a where  a.contno= '"+tContNo+"'";
			arrReturn1 = easyExecSql(strSQL);
			if (arrReturn1 == null) {
			  alert("未查到合同信息");
			} else {
			   displayCont(arrReturn1[0]);
			}
			
var sqlid47="ProposalQuerySql47";
var mySql47=new SqlClass();
mySql47.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql47.setSqlId(sqlid47);//指定使用的Sql的id
mySql47.addSubPara(tContNo );//指定传入的参数
var strSQL47=mySql47.getString();
			
			arrReturn2 =easyExecSql(strSQL47);
			//arrReturn2 =easyExecSql("select * from LCAppnt where ContNo= '" + tContNo + "'");
			if (arrReturn2 == null) {
			  alert("未查到投保人信息");
			} else {
			  displayAppnt(arrReturn2[0]);
			}
		}
		catch(ex)
		{
			alert( "请先选择一条非空记录。");
		}
}

 //合同显示
function displayCont(cArr)
  {
  	try { document.all('ContNo').value = cArr[1]; } catch(ex) { };
  	try { document.all('PrtNo').value = cArr[2]; } catch(ex) { };
  	try { document.all('ManageCom').value = cArr[3]; showOneCodeName('station','ManageCom','ManageComName'); } catch(ex) { };
  	try { document.all('SaleChnl').value = cArr[4]; showOneCodeName('salechnl','SaleChnl','SaleChnlName');} catch(ex) { };
  	try { document.all('AgentCode').value = cArr[5]; } catch(ex) { };
  	try { document.all('AgentGroup').value = cArr[6]; } catch(ex) { };
  	//try { document.all('AgentCom').value = cArr[7]; showOneCodeName('AgentCom','AgentCom','AgentComName');} catch(ex) { };
    try {
    	   document.all('AgentCom').value = cArr[7];
    	   if(document.all('AgentCom').value!=""&&document.all('AgentCom').value!=null)
    	   {
		   	
var sqlid48="ProposalQuerySql48";
var mySql48=new SqlClass();
mySql48.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
mySql48.setSqlId(sqlid48);//指定使用的Sql的id
mySql48.addSubPara(document.all('AgentCom').value );//指定传入的参数
var strSQL=mySql48.getString();
			
    	   //	var strSQL="select name from lacom where agentcom='"+document.all('AgentCom').value+"'";
    	   	var arrResult=easyExecSql(strSQL);
    	   	if(arrResult!=null)
    	   	{
    	   		document.all('AgentComName').value=arrResult[0];
    	   	}
    	   }
    	  } catch(ex) { };
  	try { document.all('InsuredNo').value = cArr[8]; } catch(ex) { };
  	try {
  		if(cArr[9] == 'A') document.all('PayMode').value = '是';
  	else
  		document.all('PayMode').value = '否';
  		/*
  		 switch(cArr[9]){
  		      case '1': document.all('PayMode').value = '现金' ;   break;
  		      case '2': document.all('PayMode').value = '现金支票';break;
  		      case '3': document.all('PayMode').value = '转帐支票';break;
  		      case '4': document.all('PayMode').value = '银行转帐';break;
  		      case '5': document.all('PayMode').value = '内部转帐';break;
  		      case '6': document.all('PayMode').value = '银行托收';break;
  		      case '7': document.all('PayMode').value = '其他';    break;
  		      defeat: document.all('PayMode').value = '其他';

  	   }*/
  	} catch(ex) { };
  	try { document.all('PolType').value = cArr[10]; } catch(ex) { };
    try { document.all('SignCom').value = cArr[11];showOneCodeName('comcode','SignCom','SignComName');} catch(ex) { };
  	try { document.all('CustomGetPolDate').value = cArr[12]; } catch(ex) { };
  	try { document.all('Name').value = cArr[13];}catch(ex){};
   }

//个单投保人信息
function displayAppnt(cArr)
   {
    try { document.all('AppntNo').value = cArr[3]; } catch(ex) { };
  	try { document.all('AppntName').value = cArr[5]; } catch(ex) { };
  	try { document.all('AppntSex').value = cArr[6]; showOneCodeName('sex','AppntSex','AppntSexName');} catch(ex) { };
  	try { document.all('AppntBirthday').value = cArr[7]; } catch(ex) { };
  	try { document.all('AppntIDType').value = cArr[10]; showOneCodeName('IDType','AppntIDType','AppntIDTypeName');} catch(ex) { };
  	try { document.all('AppntIDNo').value = cArr[11]; } catch(ex) { };
  	try { document.all('WorkType').value = cArr[29]; showOneCodeName('occupationtype','WorkType','WorkTypeName');} catch(ex) { };
  	try { document.all('NativePlace').value = cArr[12]; showOneCodeName('NativePlace','NativePlace','NativePlaceName');} catch(ex) { };
//=================add=========liuxiaosong=============2006-11-21==================start========================
  //vip客户信息
    try { 
    	var appntno=document.all('AppntNo').value;
    	//arrResult = easyExecSql("select vipvalue from ldperson where customerno='"+appntno+"'");
    	var tResourceName="sys.ProposalQuerySql";
		var tSQL = wrapSql(tResourceName,"querysqldes1",[appntno]);
		arrResult = easyExecSql(tSQL);
    	fm.VIPType.value=arrResult[0][0];
    	showOneCodeName('vipvalue','VIPType','VIPTypeName');
    }
    catch(ex) { };   
   }


// 保单明细查询 ， 数据返回父窗口
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
//	var tSel = 1;
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
        var prtNo = PolGrid.getRowColData(tSel-1,3); 
        var ContNo = document.all('ContNo').value;
        //alert("dfdf");

  var strSql="";
  //strSql="select case lmriskapp.riskprop"
  //      +" when 'I' then"
  //      +" '1'"
  //      +" when 'G' then"
  //      +" '2'"
  //      +" when 'Y' then"
  //      +" '3'"
  //      + " when 'T' then"
  //      + " '5'"
  //      +" end"
  //      +" from lmriskapp"
  //      +" where riskcode in (select riskcode"
  //      +" from lcpol"
  //      +" where polno = mainpolno"
  //      +" and contno = '"+ContNo+"')"
  //arrResult = easyExecSql(strSql);
  //var BankFlag = arrResult[0][0];
  
  //去掉原来的判断投保单类型的逻辑，修改为按印刷号来判断投保单类型
    var BankFlag = "";
    var tSplitPrtNo = prtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }
  //strSql="select salechnl ,cardflag from lccont where contno='"+ContNo+"'";
  //arrResult = easyExecSql(strSql);
  //var CardFlag = arrResult[0][1];
  //if(BankFlag==""||BankFlag==null||CardFlag=="3")
  //BankFlag="4";
  //alert("BankFlag="+BankFlag);//return false;
        if( tIsCancelPolFlag == "0"){
	    	if (GrpPolNo =="00000000000000000000") {
	    		//alert(BankFlag);
	    	 	window.open("./AllProQueryMain.jsp?LoadFlag=6&prtNo="+prtNo+"&ContNo="+ContNo+"&BankFlag="+BankFlag,"window1",sFeatures);
		    } else {
			window.open("./AllProQueryMain.jsp?LoadFlag=4","',sFeatures");
		    }
		} else {
		if( tIsCancelPolFlag == "1"){//销户保单查询
			if (GrpPolNo =="00000000000000000000")   {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1",sFeatures);
			} else {
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7","",sFeatures);
			}
	    } else {
	    	alert("保单类型传输错误!");
	    	return ;
	    }
	 }
   }
}

// 数据返回父窗口
function returnParent()
{
	//alert(tDisplay);
	if (tDisplay=="1")
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
	    		top.opener.afterQuery( arrReturn );
	    		top.close();
	    	}
	    	catch(ex)
	    	{
	    		alert( "请先选择一条非空记录，再点击返回按钮。");
	    	}
	    }
	 }
	 else
	 {
	    top.close();
	 }
}

// 交费查寻
function FeeQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);


		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  var cContNo = fm.ContNo.value;
		  window.open("../sys/RelFeeQueryMain.jsp?ContNo="+cContNo+"&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}


// 给付项查寻
function GetItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
      var cContNo = document.all('ContNo').value;

		if (cContNo== "" ||cPolNo == "")
		    return;
		  window.open("../sys/GetItemQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
	}
}

// 暂交费查询
function TempFeeQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    //var cPolNo = PolGrid.getRowColData(tSel - 1,2);
	      var cContNo = document.all('ContNo').value;

		if (cContNo == "")
		    return;
		  window.open("../sys/PolTempFeeQueryMain.jsp?ContNo=" + cContNo  + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);
	}
}

// 保费项查询
function PremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        var cContNo = document.all('ContNo').value;

		if (cContNo == ""||cPolNo == "")
		    return;
		  window.open("../sys/PremQueryMain.jsp?ContNo=" + tContNo+ "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

// 帐户查询
function InsuredAccQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);


		if (cPolNo == "")
		    return;
		  window.open("../sys/InsureAccQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

// 给付查寻
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelGetQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

//生存领取查询
function LiveGetQuery()
{
	//alert('正在开发当中');
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
		var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		//<!-- XinYQ modifoed on 2006-02-22 : BGN -->
        var sOpenWinURL = "LiveGetQueryMain.jsp";
        var sParameters = "ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "保全生存领取查询", "left");
		//<!-- XinYQ modifoed on 2006-02-22 : END -->
	}
}

//<!-- XinYQ added on 2006-03-01 : BGN -->
/*============================================================================*/

/*
 * 代收代付查询
 */
function ProxyIncomePayQuery()
{
    var sOtherNo;
    try
    {
        sOtherNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex)
	{
		alert( "对不起, 无法获取保单合同号码信息。" );
		return;
	}
	if (sOtherNo != null && sOtherNo != "")
	{
        var sOpenWinURL = "ProxyIncomePayQueryMain.jsp";
        var sParameters = "OtherNo=" + sOtherNo;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "保全代收代付查询", "left");
	}
}

/*============================================================================*/

/*
 * 补发打印查询
 */
function ReissuePrintQuery()
{
    var sContNo;
    try
    {
        sContNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex) { return; }
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("警告：无法获取保单合同号码。查询补发打印信息失败！ ");
        return;
    }
    else
    {
        var sOpenWinURL = "ReissuePrintQueryMain.jsp";
        var sParameters = "EdorAcceptNo=null&ContNo=" + sContNo;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "补发打印查询", "left");
    }
}

/*============================================================================*/

/*
 * 代理人信息查询
 */
function AgentQuery()
{
    OpenWindowNew("../bq/EdorAgentQueryMain.jsp", "代理人信息查询", "left");
}

/*============================================================================*/
//<!-- XinYQ added on 2006-03-01 : END -->



//批改补退费查询
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName  + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
	}
}

function LCInsuAccQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		show( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
	    var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
        var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		OpenWindowNew("../sys/LCInsuAccQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"window017","left");
	}
}

// 垫交/贷款费查询
function LoLoanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;
           // alert("cPolNo"+cPolNo);
		  window.open("../sys/LoLoanQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}
}

//保全查询
function PerEdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);

		if (cPolNo == "")
		    return;

		  window.open("../sys/AllPBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);
	}
}


//保全查询
function BqEdorQueryClick()
{
    window.open("../sys/PolBqEdorMain.jsp?ContNo=" + tContNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
}


//红利分配查询
function BonusQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
        var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
		  window.open("../sys/BonusQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures );
	}
}

//万能险保单结算
function OmniQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
	    var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
        var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		window.open("../sys/OmniAccQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}

}

//===================add=============liuxiaosong=========2006-10-19============start===================
/**
 *保单密码修改轨迹查询
 */
function PwdChangeTrackQuery()
{
 	var ContNo = document.all('ContNo').value;
	window.open("../sys/PwdChangeTrackQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}
//===================add=============liuxiaosong=========2006-10-19============end=====================


//欠交保费查询
function PayPremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	  var cPolNo = PolGrid.getRowColData(tSel - 1,2);
    var cContNo = document.all('ContNo').value;

		if (cContNo == ""||cPolNo == "")
		    return;
		  window.open("../sys/PayPremQueryMain.jsp?ContNo=" + tContNo+ "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);
	}


}


// 主险查询
function MainRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);

		if (cPolNo==cMainPolNo)
			alert("您选择的是一条主险数据，请您选择一条附加险数据后，再点击主险查询按钮。")
		else
			{

				if (cMainPolNo == "")
				    return;

				  	initPolGrid();

						// 书写SQL语句
						var mSQL = "";

						var sqlid49="ProposalQuerySql49";
						var mySql49=new SqlClass();
						mySql49.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
						mySql49.setSqlId(sqlid49);//指定使用的Sql的id
						mySql49.addSubPara(cMainPolNo );//指定传入的参数
						mSQL=mySql49.getString();


//					    mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";

						//查询SQL，返回结果字符串
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);

					  //判断是否查询成功
					  if (!turnPage.strQueryResult) {
					  	PolGrid.clearData();
					  	alert("数据库中没有满足条件的数据！");
					    //alert("查询失败！");
					    return false;
					  }

					  //查询成功则拆分字符串，返回二维数组
					  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

					  //设置初始化过的MULTILINE对象
					  turnPage.pageDisplayGrid = PolGrid;

					  //保存SQL语句
					  turnPage.strQuerySql     = mSQL;

					  //设置查询起始位置
					  turnPage.pageIndex = 0;

					  //在查询结果数组中取出符合页面显示大小设置的数组
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

					  //调用MULTILINE对象显示查询结果
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}
	}
}



//附加险查询
function OddRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);

		if (cPolNo!=cMainPolNo)
			alert("您选择的是一条附加险数据，请您选择一条主险数据后，再点击附加险查询按钮。")
		else
			{

				if (cPolNo == "")
				    return;

				  	initPolGrid();

						// 书写SQL语句
						var mSQL = "";


						var sqlid50="ProposalQuerySql50";
						var mySql50=new SqlClass();
						mySql50.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
						mySql50.setSqlId(sqlid50);//指定使用的Sql的id
						mySql50.addSubPara(cMainPolNo );//指定传入的参数
						mySql50.addSubPara(cPolNo );//指定传入的参数
						mSQL=mySql50.getString();

//						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";

						//查询SQL，返回结果字符串
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);

					  //判断是否查询成功
					  if (!turnPage.strQueryResult) {
					  	PolGrid.clearData();
					  	alert("数据库中没有满足条件的数据！");
					    //alert("查询失败！");
					    return false;
					  }

					  //查询成功则拆分字符串，返回二维数组
					  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

					  //设置初始化过的MULTILINE对象
					  turnPage.pageDisplayGrid = PolGrid;

					  //保存SQL语句
					  turnPage.strQuerySql     = mSQL;

					  //设置查询起始位置
					  turnPage.pageIndex = 0;

					  //在查询结果数组中取出符合页面显示大小设置的数组
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

					  //调用MULTILINE对象显示查询结果
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}
	}
}

// 保单明细查询
function PolClick1()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	 var cPrtNo = PolGrid.getRowColData(tSel - 1,3);
	 var cGrpContNo=PolGrid.getRowColData(tSel - 1,1);
   if (cPrtNo == "")
	 return;
   if(cGrpContNo=="00000000000000000000")
      window.open("../app/ContInputNoScanMain.jsp?prtNo="+ cPrtNo+"&LoadFlag=6","",sFeatures);
   else
	    window.open("../app/GroupPolApproveInfo.jsp?polNo="+ cGrpContNo+"&LoadFlag=16","",sFeatures);
	}
}


// 理赔给付查询 Modify by zhaorx 2005-03-06
//function ClaimGetQuery()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//
//	if( tSel == 0 || tSel == null )
//		alert( "请先选择一条记录。" );
//	else
//	{
//	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);
//		if (cPolNo == "")
//		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);
//	}
//}


//备注信息查询
function ShowRemark()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel-1,2);
		if (cPolNo == "")
		    return;
		  window.open("../sys/FrameRemarkMain.jsp?PolNo=" + cPolNo,"",sFeatures);
	}
}


//扫描件查询
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);


		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	}
}

//集体保单扫描件查询
function ScanQuery2()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录。" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);


		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	}
}

function GoBack(){

	//top.opener.easyQueryClick();
	top.close();

	}


function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
  }
}


function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
    if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码
	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
}


function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码
	
	var sqlid51="ProposalQuerySql51";
	var mySql51=new SqlClass();
	mySql51.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql51.setSqlId(sqlid51);//指定使用的Sql的id
	mySql51.addSubPara(cAgentCode );//指定传入的参数
	mySql51.addSubPara(document.all('ManageCom').value );//指定传入的参数
	var strSql =mySql51.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
     alert("dddddddddd"+arrResult);
	}
}


//保单服务状态-------------added by guanwei
function queryPolType()
{
	var cPolType = document.all('PolType').value;
//	var strSql = "select PolType from LACommisionDetail where AgentCode='" + document.all('AgentCode').value +"' and GrpContNo = '"+document.all('ContNo').value+"'";

	var sqlid52="ProposalQuerySql52";
	var mySql52=new SqlClass();
	mySql52.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql52.setSqlId(sqlid52);//指定使用的Sql的id
	mySql52.addSubPara(document.all('ContNo').value );//指定传入的参数
	mySql52.addSubPara(document.all('ContNo').value );//指定传入的参数
	var strSql =mySql52.getString();

//	var strSql = "    select 1 from lrascription where  contno='"+document.all('ContNo').value+"'    union    select 1 from lrascriptionb where  contno='"+document.all('ContNo').value+"'"
  var arrResult = easyExecSql(strSql);
    if (arrResult == "1")
    {
    document.all('PolType').value = "孤儿保单";
    }
    else
    {
    document.all('PolType').value = "正常保单";
    }
}

//保单签单日期
function querySignDate()
{
	var tSignDate = document.all('SignDate').value;
	
		var sqlid53="ProposalQuerySql53";
	var mySql53=new SqlClass();
	mySql53.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql53.setSqlId(sqlid53);//指定使用的Sql的id
	mySql53.addSubPara(document.all('ContNo').value );//指定传入的参数
	var strSql =mySql53.getString();
	
//	var strSql = "select SignDate,LostTimes from LCCont where  ContNo = '"+document.all('ContNo').value+"'";
  var arrResult = easyExecSql(strSql);
  if(arrResult)
  {
	  document.all('SignDate').value = arrResult[0][0];
	  document.all('PrintTimes').value = arrResult[0][1]+"次";
  //alert ('querySignDate============='+arrResult);
  }
}

//保单打印日期
function queryMakeDate()
{
	var tMakeDate = document.all('MakeDate').value;
	
			var sqlid54="ProposalQuerySql54";
	var mySql54=new SqlClass();
	mySql54.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql54.setSqlId(sqlid54);//指定使用的Sql的id
	mySql54.addSubPara(document.all('ContNo').value );//指定传入的参数
	var strSql =mySql54.getString();
	
	//var strSql = "select max(MakeDate) from lccontprint where  ContNo = '"+document.all('ContNo').value+"'";
  var arrResult = easyExecSql(strSql);
  if(arrResult == ""||arrResult == null||arrResult =="null"  )
  {
   document.all('MakeDate').value = "未获得保单打印日期";
  }
  else
  {
  document.all('MakeDate').value = arrResult;
  }
}

//问题件查询
function ShowQuest()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/QuestQueryMain.jsp?ContNo="+ContNo+"&Flag=5","",sFeatures);
}


//核保查询
function UWQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/UWQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}


//操作履历查询
function OperationQuery()
{
	var PrtNo = document.all('PrtNo').value;
	var ContNo = document.all('ContNo').value;
	
	var sqlid55="ProposalQuerySql55";
	var mySql55=new SqlClass();
	mySql55.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql55.setSqlId(sqlid55);//指定使用的Sql的id
	mySql55.addSubPara(ContNo );//指定传入的参数
	var strSql55 =mySql55.getString();
	
	var ContType=easyExecSql(strSql55,1,0,1)[0][0]
	//var ContType=easyExecSql("select conttype from lccont where contno='"+ContNo+"'",1,0,1)[0][0]

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../sys/RecordQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo+"&ContType="+ContType,"",sFeatures);
}

//体检结果查询
function HealthQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/UWManuHealthQMain.jsp?ContNo="+ContNo+"&Flag=2","",sFeatures);
}


//生调结果查询
function MeetQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/RReportQueryMain.jsp?ContNo="+ContNo+"&PrtNo="+ContNo+"&Flag=1","",sFeatures);
}

////被保人既往体检资料查询
//function InsuredHealthQuery()
//{
//	var ContNo = document.all('PrtNo').value;
//	var InsuredNo = document.all('InsuredNo').value;
//
//	if (ContNo == "")
//	{
//		    alert("没有得到投保单号信息！");
//		    return;
//	}
//	window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+ContNo+"&CustomerNo="+InsuredNo+"&type=1");
//}

////投保人既往健康告知查询
//function ImpartQuery()
//{
//	var ContNo = document.all('PrtNo').value;
//	var AppntNo = document.all('AppntNo').value;
//
//	if (ContNo == "")
//	{
//		    alert("没有得到投保单号信息！");
//		    return;
//	}
//	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+AppntNo);
//}
//
////被保人既往健康告知查询
//function InsuredImpartQuery()
//{
//	var ContNo = document.all('PrtNo').value;
//	var InsuredNo = document.all('InsuredNo').value;
//
//	if (ContNo == "")
//	{
//		    alert("没有得到投保单号信息！");
//		    return;
//	}
//	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+InsuredNo);
//}

//投保人保额累计查询
function　AmntAccumulateQuery()
{
	var ContNo = document.all('PrtNo').value;
	var AppntNo = document.all('AppntNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+AppntNo,"",sFeatures);
}
//被保人保额累计查询
function InsuredAmntAccumulateQuery()
{
	var ContNo = document.all('PrtNo').value;
	var InsuredNo = document.all('InsuredNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);
}

//影像资料查询
function ImageQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}

//自核提示信息查询
function  UWErrQuery()
{
	var ContNo = document.all('ContNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/UWErrMain.jsp?ContNo="+ContNo,"",sFeatures);
}


//再保回复查询
function UpReportQuery()
{
	var ContNo = document.all('ContNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../uw/UWManuUpReportReply.jsp?ContNo="+ContNo+"&Flag=1","",sFeatures);
}


//核保通知书查询
function  UWNoticQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../sys/UWNoticQuery.jsp?ContNo="+ContNo,"",sFeatures);
}

//客户合并通知书查询
function  KHHBNoticQuery()
{
	var ContNo = document.all('PrtNo').value;

	if (ContNo == "")
	{
		    alert("没有得到投保单号信息！");
		    return;
	}
	window.open("../sys/CustCombintQuery.jsp?ContNo="+ContNo,"",sFeatures);

}

//被保人已承保保单查询
function  InsuredqueryProposal()
{
var InsuredNo = InsuredGrid. getRowColData(0,2)

	if (InsuredNo == "")
	{
		    alert("没有得到被保人信息！");
		    return;
	}
	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);
}

//被保人未承保保单查询
function  InsuredqueryNotProposal()
{
	var InsuredNo = InsuredGrid. getRowColData(0,2)
	if (InsuredNo == "")
	{
		    alert("没有得到被保人信息！");
		    return;
	}
	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+InsuredNo,"",sFeatures);
}

//投保人已承保保单查询
function  AppntqueryProposal()
{
	var AppntNo = document.all('AppntNo').value;
	if (AppntNo == "")
	{
		    alert("没有得到投保人信息！");
		    return;
	}
	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+AppntNo,"",sFeatures);
}

//投保人未承保保单查询
function  AppntqueryNotProposal()
{
	var AppntNo = document.all('AppntNo').value;
	//alert(AppntNo);
	if (AppntNo == "")
	{
		alert("没有得到投保人信息！");
		return;
	}
	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+AppntNo,"",sFeatures);
}


//<!-- XinYQ modified on 2006-02-20 : BGN -->
/*============================================================================*/

/**
 * 保单状态查询
 */
function StateQuery()
{
    var sContNo;
    try
    {
        //sContNo = PolGrid.getRowColData(0,3);
        sContNo = document.getElementsByName("ContNo")[0].value;
    }
    catch (ex) { return; }
    if (sContNo == null || trim(sContNo) == "")
    {
        alert("警告：无法获取保单合同号码。查询保单状态失败！ ");
        return;
    }
    else
    {
        var sOpenWinURL = "LCContQueryMain.jsp";
        var sParameters = "ContNo=" + sContNo;
        OpenWindowNew(sOpenWinURL + "?" + sParameters, "保单状态查询", "left");
    }
}

/*============================================================================*/
//<!-- XinYQ modified on 2006-02-20 : END -->


//首期暂缴费查询
function NewTempFeeQuery()
{
	var Prtno =document.all('PrtNo').value;
	var AppntNo = document.all('AppntNo').value;
	var AppntName = document.all('AppntName').value;
// alert("Prtno"+Prtno);
//  alert("AppntNo"+AppntNo);
//   alert("AppntName"+AppntName);
  var tSel = PolGrid.getSelNo();
  if( tSel == 0 || tSel == null )
  	alert( "请先选择一条记录。" );
  else
   window.open("../uw/UWTempFeeQry.jsp?Prtno=" + Prtno + " &AppntNo=" + AppntNo + "&AppntName= " +AppntName,"",sFeatures);
}


//收费员基本信息查询
function ShowCollectorQuery()
{
	var AgentCode = document.all('AgentCode').value;

	if (AgentCode == "")
	{
	 alert("没有得到收费员编号信息!");
	 return;
	}
	 window.open("../sys/CollectorQueryMain.jsp?AgentCode="+AgentCode,"",sFeatures);
}

//保单服务轨迹查询
function ShowTraceQuery()
{
	var ContNo = document.all('ContNo').value;
  //alert(ContNo);
	if (ContNo == "")
	{
	 alert("没有得到收费员编号信息!");
	 return;
	}
	 window.open("../sys/ShowTraceQuery.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

//报盘数据查询
function BaoPanShuju()
{
	window.open("../sys/BankDataQueryMain.jsp","",sFeatures);
	}

/*******************************************************************
 * 开始
 * 下面是“渠道类”查询处理函数
 * 作者： 万泽辉
 * 日期： 2005-10-26
 ******************************************************************/


// 工资历史查询
function ShowWageHistoQuery(){
   var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("没有得到代理人编号信息!");
		     return;
	}
	    window.open("../sys/LAwageQueryInput.jsp?ContNo="+tContNo,"",sFeatures);

	}


//组织关系查询
function ShowOrganizationQuery(){
	var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("没有得到代理人编号信息!");
		     return;
	}
	    window.open("../sys/LAAgentQuery.jsp?ContNo="+tContNo,"",sFeatures);

}

//血缘关系查询
function ShowConsanguinityQuery(){
	var tContNo = document.all('ContNo').value;
	if( tContNo == ""){
		    alert("没有找到代理人编号相关的信息");
		    return;
		}
		window.open("../sys/LARearRelationQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}


// 福利历史查询
function ShowWelfareHistoQuery(){
	var tContNo = document.all('ContNo').value;
	if( tContNo == ""){
		    alert("没有找到代理人编号的相关信息");
		    return;
		}
		window.open("../sys/LAwageWelareQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}


//考核历史查询
function ShowCheckHistoQuery(){
	var tContNo = document.all('ContNo').value;
	if(tContNo == ""){
		   alert("没有找到代理人的编号信息");
		   return;
		}
		window.open("../sys/LAAssessAccessoryQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}

//业务员基本信息查询
function ShowOperInfoQuery(){
	var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("没有得到代理人编号信息!");
		     return;
	}
	    window.open("../sys/LAAgentQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}


//业务员历史信息查询
function ShowOperHistoQuery(){
	var tContNo = document.all('ContNo').value;

	if (tContNo == "")
	{
		     alert("没有得到代理人编号信息!");
		     return;
	}
	    window.open("../sys/LAAgentBQueryInput.jsp?ContNo="+tContNo,"",sFeatures);
	}

/*******************************************************************
 * 结束
 * 作者： 万泽辉
 * 日期： 2005-10-26
 ******************************************************************/

/*******************************************************************
 * 开始
 * 下面是“保全类”查询处理函数
 * 作者： 万泽辉
 * 日期： 2005-10-31
 ******************************************************************/


 // 险种信息查询
 function ShowRiskInfoQuery(){
 	    window.open("../sys/LmRiskAppQueryInput.jsp","",sFeatures);
 	}


// 银行利率信息查询
 function ShowBankRateQuery(){
 	    window.open("../sys/LdBankRateQueryInput.jsp","",sFeatures);
 	}

//被保人查询
function InsuredQuery(){
    PrtNo = document.all('PrtNo').value;
		if (PrtNo == "") return;
	  window.open("../app/ContInsuredQueryMain.jsp?prtNo="+PrtNo,"window1",sFeatures);
}


//查询回单日期 added by guanwei 20060519
function QueryGetPolDate()
{
	
	var sqlid56="ProposalQuerySql56";
	var mySql56=new SqlClass();
	mySql56.setResourceName("sys.ProposalQuerySql"); //指定使用的properties文件名
	mySql56.setSqlId(sqlid56);//指定使用的Sql的id
	mySql56.addSubPara(tContNo );//指定传入的参数
	var PolDateSQL =mySql56.getString();
	
//	var PolDateSQL = " select getpoldate,AccName,BankCode,BankAccNo "+
//									 "   from LCCont where ContNo = '"+tContNo+"'";
	var arrResultPolDate = easyExecSql(PolDateSQL);
	
	
	if(arrResultPolDate != null)
	{
	
	if(arrResultPolDate[0][0]!=""&&arrResultPolDate[0][0]!=null&&arrResultPolDate[0][0]!="null")
	{
		document.all('GetPolDate').value = arrResultPolDate[0][0];
		}
	else
		{
			document.all('GetPolDate').value = "";
			}
	if(arrResultPolDate[0][1]!=""&&arrResultPolDate[0][1]!=null&&arrResultPolDate[0][1]!="null")
	{
		document.all('RNAccName').value = arrResultPolDate[0][1];
		}
	else
		{
			document.all('RNAccName').value = "";
			}
	if(arrResultPolDate[0][2]!=""&&arrResultPolDate[0][2]!=null&&arrResultPolDate[0][2]!="null")
	{
		document.all('RNBankCode').value = arrResultPolDate[0][2];
		}
	else
		{
			document.all('RNBankCode').value = "";
			}
	if(arrResultPolDate[0][3]!=""&&arrResultPolDate[0][3]!=null&&arrResultPolDate[0][3]!="null")
	{
		document.all('RNAccNo').value = arrResultPolDate[0][3];
		}
	else
		{
			document.all('RNAccNo').value = "";
			}
		}
	}
