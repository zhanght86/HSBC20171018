

var arrDataSet 
var showInfo;
var mDebug="0";
var FlagDel;
var turnPage = new turnPageClass();


function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
    if (FlagStr == "Fail" )
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
    else
    {
    	showDiv(inputButton,"false");
    }
}

function getcodedata2()
{
//	var sql="select subtype,subtypename from es_doc_def where busstype  like 'TB%'";
	
	var sqlid0="ScanLisSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("f1print.ScanLisSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	var sql=mySql0.getString();
	
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
    document.all("SubType").CodeData=tCodeData;
	
}

//根据起始日期进行查询出要该日期范围内的批次号码
function PrintNoticeBill()
{
    //if((fm.ManageCom.value=="")||(fm.ManageCom.value=="null"))
    //{
    //	alert("请选择管理机构!!");
    //	return ;
    //}
    if(fm.ManageCom.value!=null&&fm.ManageCom.value!=""){
      //if(fm.ManageCom.value.length<4){
      //   alert("管理机构应为4-8位！");
      //   return false;
      //}
    }else{
      alert("请录入管理机构！");
      return false;
    }
    
    if((fm.IssueStartDate.value=="")||(fm.IssueStartDate.value=="null"))
    {
    	alert("请选择起始日期!!");
    	return ;
    }
    
    if((fm.IssueEndDate.value=="")||(fm.IssueEndDate.value=="null"))
    {
    	alert("请选择结束日期!!");
    	return ;
    }
    
    //if((fm.BusiType.value=="")||(fm.BusiType.value=="null"))
    //{
    //	alert("请选择业务类型!!");
    //	return ;
    //}
    
    //if((fm.BusiPaperType.value=="")||(fm.BusiPaperType.value=="null"))
    //{
    //	alert("请选择单证类型!!");
    //	return ;
   // }
    
    
    	var i = 0;
    	//var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    	//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    	
    	fm.action = './ScanLisSave.jsp';
    	fm.target="f1print";
    	document.getElementById("fm").submit(); //提交
    	
}

function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	//var sDate = document.all('StartDate').value;
	//var eDate = document.all('EndDate').value;
	
	if (ManageCom == "")
	{
			alert("请选择管理机构！");
			return;
	}
	
	if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("管理机构应为4位到8位！");
	//	return;
	//}  
	}else{
	   alert("请录入管理机构！");
	   return false;
	}
	
	if(fm.IssueStartDate.value==null||fm.IssueStartDate.value==""
	      ||fm.IssueEndDate.value==null||fm.IssueEndDate.value==""){
	   alert("开始日期或结束日期没有录入！");
	   return false;
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
	initPolGrid();
	// 书写SQL语句
	//var strSQL = " select a.PrtNo,(select RiskName from LMRiskApp where RiskCode=a.RiskCode) riskname,(select nvl(codename,a.salechnl) from ldcode where codetype='salechnl' and code=a.salechnl) salechnl ,a.managecom,(select Name from LAAgent where agentcode=a.agentcode) agentname,a.insuredname insuredname,a.appntname appntname,(select decode(min(c.makedate),null,'无扫描件',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(a.prtno)) ScanDate,(select decode(max(d.makedate),null,'未发',to_char(max(d.makedate),'yyyy-mm-dd')) from lcuwsub d where d.ProposalNo=a.ProposalNo and uwflag='8') UWNoticeDate,(select decode(max(e.makedate),null,'未发',to_char(max(e.makedate),'yyyy-mm-dd')) from LCPENotice e where e.ProposalNo = a.ProposalNo) HealthDate,(select decode(max(f.makedate),null,'未发',to_char(max(f.makedate),'yyyy-mm-dd')) from LCRReport f where f.polno = a.ProposalNo) SurveyDate,getQuestSendDate(a.PolNo,'2') AgentQuestSendDate,getQuestSendDate(a.PolNo,'4') ManageQuestSendDate"
//	var strSQL = " select a.doccode,a.docid, a.managecom,a.subtype,"
//             + " a.numpages,a.makedate, a.scanoperator,a.docremark "
//             + "   from es_doc_main a where 1=1"
//             + getWherePart('a.Makedate','IssueStartDate','>=')
//             + getWherePart('a.Makedate','IssueEndDate','<=')
//             + getWherePart('a.scanoperator','OperaterNo')
             //+ getWherePart('a.subtype','SubType')
             //+ getWherePart('a.operator','BusiPaperType')
             //+ getWherePart('a.PrtNo','PrtNo')
             //+ getWherePart('a.RiskCode','RiskCode')
             //+ getWherePart('a.InsuredName','InsuredName')
             //+ getWherePart('d.salechnl','SaleChnl')
             //+ getWherePart('a.agentcode','AgentCode')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.ManageCom like '" + comcode + "%%'"
             //+ " order by a.makedate ";
             
           //  alert(fm.ScanType.vlaue);
             /*
             if(fm.SubType.value!=null&&fm.SubType.vlaue!=''){
             alert(1);
               strSQL=strSQL+" and a.busstype like 'TB%' and a.subtype ='"+fm.SubType.value+"'";
             }else{
             alert(2);
               strSQL =strSQL+" and a.busstype like 'TB%'";
             }*/
//              strSQL =strSQL+" and a.busstype like 'TB%'"
//                     + getWherePart('a.subtype','SubType');
      var tScanType = document.all('ScanType').value;
      var tScanTypeid1 ="";
      var tScanTypeid2 ="";
      var tScanTypeid3 ="";
      var tScanTypeid4 ="";
       if(tScanType =="个险"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) = '8611' order by a.makedate ";
          tScanTypeid1 ="1";
       }else if(tScanType =="中介"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) = '8621' order by a.makedate ";
          tScanTypeid2 ="1";
       }else if(tScanType =="简易"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) = '8616' order by a.makedate ";
          tScanTypeid3 ="1";
       }else if(tScanType =="银代"){
//          strSQL = strSQL+" and substr(a.doccode,1,4) in ('8615','8625','8635') order by a.makedate";
          tScanTypeid4 ="1";
//       }else {
//          strSQL = strSQL+" order by a.makedate ";
       }
       
       
	  	var  IssueStartDate1 = window.document.getElementsByName(trim("IssueStartDate"))[0].value;
	  	var  IssueEndDate1 = window.document.getElementsByName(trim("IssueEndDate"))[0].value;
	  	var  OperaterNo1 = window.document.getElementsByName(trim("OperaterNo"))[0].value;
	  	var  SubType1 = window.document.getElementsByName(trim("SubType"))[0].value;
		var sqlid1="ScanLisSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.ScanLisSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(IssueStartDate1);//指定传入的参数
		mySql1.addSubPara(IssueEndDate1);//指定传入的参数
		mySql1.addSubPara(OperaterNo1);//指定传入的参数
		mySql1.addSubPara(document.all("ManageCom").value );//指定传入的参数
		mySql1.addSubPara(comcode);//指定传入的参数
		mySql1.addSubPara(SubType1);//指定传入的参数
		mySql1.addSubPara(tScanTypeid1);//指定传入的参数
		mySql1.addSubPara(tScanTypeid2);//指定传入的参数
		mySql1.addSubPara(tScanTypeid3);//指定传入的参数
		mySql1.addSubPara(tScanTypeid4);//指定传入的参数
		var strSQL=mySql1.getString();
       
       
    //判断类型
	//prompt("abc",strSQL);
	turnPage.queryModal(strSQL, PolGrid);    
	
}


function afterQuery(arrResult)
{
  
  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
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
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
			parent.fraMain.rows = "0,0,0,0,*";
    }
    else
    {
  		parent.fraMain.rows = "0,0,0,0,*";
    }
}
function queryCom()
{
	showInfo = window.open("../certify/AgentTrussQuery.html");
	}
function afterQuery(arrResult)
{
  if(arrResult!=null)
  { 	
	    fm.AgentGroup.value = arrResult[0][3];
	    
	}
}

function afterCodeSelect( cCodeName, Field )
{
	if(cCodeName=="busitype"){
    if(Field.value=="TB"){
      document.all("BusiPaperType").CodeData="0^00|全部类型^01|投保书类^02|通知书类^03|其它单证类";	
    }
    
		return;
	}
}

