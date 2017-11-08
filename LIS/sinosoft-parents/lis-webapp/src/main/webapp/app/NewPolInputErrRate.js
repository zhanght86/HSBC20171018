//
//程序名称：ProposalNoRespQuery.js
//程序功能：查询个单未回复清单
//创建日期：2007-03-20 18:02
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();

//简单查询
function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	var sDate = document.all('StartDate').value;
	var eDate = document.all('EndDate').value;
	/*
	if (ManageCom == "")
	{
			alert("请选择管理机构！");
			return;
	}
	*/
	if(ManageCom!=null&&ManageCom!=""){
	if(ManageCom.length<4){
		alert("管理机构应为4位到8位！");
		return;
	}  
	}
	//if(document.all('PrtNo').value=="")
	//{
	// if (sDate == "")
	//  {
	//		alert("请输入录单开始日期！");
	//		return;
	//  }
	 // if (eDate == "")
	 // {
	//		alert("请输入录单结束日期！");
	//		return;
  	//}
  //}
	initCodeGrid();
	// 书写SQL语句
	//var strSQL = " select a.PrtNo,(select RiskName from LMRiskApp where RiskCode=a.RiskCode) riskname,(select nvl(codename,a.salechnl) from ldcode where codetype='salechnl' and code=a.salechnl) salechnl ,a.managecom,(select Name from LAAgent where agentcode=a.agentcode) agentname,a.insuredname insuredname,a.appntname appntname,(select decode(min(c.makedate),null,'无扫描件',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(a.prtno)) ScanDate,(select decode(max(d.makedate),null,'未发',to_char(max(d.makedate),'yyyy-mm-dd')) from lcuwsub d where d.ProposalNo=a.ProposalNo and uwflag='8') UWNoticeDate,(select decode(max(e.makedate),null,'未发',to_char(max(e.makedate),'yyyy-mm-dd')) from LCPENotice e where e.ProposalNo = a.ProposalNo) HealthDate,(select decode(max(f.makedate),null,'未发',to_char(max(f.makedate),'yyyy-mm-dd')) from LCRReport f where f.polno = a.ProposalNo) SurveyDate,getQuestSendDate(a.PolNo,'2') AgentQuestSendDate,getQuestSendDate(a.PolNo,'4') ManageQuestSendDate"
//	var strSQL = "  select a.prtno,(select riskname from lmriskapp where riskcode =a.riskcode),"
//             + " a.appntname,a.insuredname,a.amnt, "
//             + "  (select prem from lccont where contno =a.contno),"
//             + " a.agentcode, a.managecom "
//             + " from lcpol a ,es_doc_main b,lccont c where mainpolno =polno and a.prtno =b.doccode and b.doccode=c.prtno  and b.subtype ='UA001' and a.appflag ='0' and c.conttype ='1'"
//             + getWherePart('b.Makedate','StartDate','>=')
//             + getWherePart('b.Makedate','EndDate','<=')
//             + getWherePart('d.prtno','prtNo')
//             //+ getWherePart('a.PrtNo','PrtNo')
//             //+ getWherePart('a.RiskCode','RiskCode')
//             //+ getWherePart('a.InsuredName','InsuredName')
//             //+ getWherePart('d.salechnl','SaleChnl')
//             //+ getWherePart('a.agentcode','AgentCode')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comCode + "%%'";
	
	  var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value;
	  var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
	  var  prtNo = window.document.getElementsByName(trim("prtNo"))[0].value;
	  var sqlid1="NewPolInputErrRateSql0";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("app.NewPolInputErrRateSql"); //指定使用的properties文件名
	  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	  mySql1.addSubPara(StartDate);//指定传入的参数
	  mySql1.addSubPara(EndDate);//指定传入的参数
	  mySql1.addSubPara(prtNo);//指定传入的参数
	  mySql1.addSubPara(document.all("ManageCom").value);//指定传入的参数
	  mySql1.addSubPara(comCode);//指定传入的参数
	  var strSQL=mySql1.getString();
	  
    //判断类型
    var tScanType = document.all('ScanType').value;
       if(tScanType =="个险"){
          strSQL = strSQL+" and substr(b.doccode,1,4) = '8611' order by a.prtno ";
       }else if(tScanType =="中介"){
          strSQL = strSQL+" and substr(b.doccode,1,4) = '8621' order by a.prtno ";
       }else if(tScanType =="简易"){
          strSQL = strSQL+" and substr(b.doccode,1,4) = '8616' order by a.prtno ";
       }else if(tScanType =="银代"){
          strSQL = strSQL+" and substr(b.doccode,1,4) in ('8615','8625','8635') order by a.prtno";
       }else {
          strSQL = strSQL+" order by a.prtno ";
       }
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, CodeGrid);    
}
 
function easyPrint()
{

 if(fm.ManageCom.value==""||fm.ManageCom.value==null){
   alert("请录入管理机构！");
   return false;
 }
 if(fm.StartDate.value==null||fm.StartDate.value==""
     ||fm.EndDate.value==null||fm.EndDate.value==""){
      alert("请录入扫描开始时间和扫描结束时间");
      return false;
     }
	var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();
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
	var cAgentCode = fm.AgentCode.value;  //代理人编码	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
	var sqlid2="NewPolInputErrRateSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.NewPolInputErrRateSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(cAgentCode);//指定传入的参数
	var strSql=mySql2.getString();
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else
    {
			
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
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
	var sqlid3="NewPolInputErrRateSql2";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.NewPolInputErrRateSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(cAgentCode);//指定传入的参数
	var strSql=mySql3.getString();
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
}