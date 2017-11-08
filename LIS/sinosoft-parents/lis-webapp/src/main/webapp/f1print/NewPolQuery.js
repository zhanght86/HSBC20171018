

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
    
    if((fm.SignStartDate.value=="")||(fm.SignStartDate.value=="null"))
    {
    	alert("请输入承保开始日期!!");
    	return ;
    }
    if((fm.SignEndDate.value=="")||(fm.SignEndDate.value=="null"))
    {
    	alert("请输入承保结束日期!!");
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
    	
    	fm.action = './NewPolQuerySave.jsp';
    	fm.target="f1print";
    	document.getElementById("fm").submit(); //提交
    	
}

function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	var IssueStartDate = document.all('IssueStartDate').value;
	var IssueEndDate = document.all('IssueEndDate').value;
	var signStartDate = document.all('SignStartDate').value;
	var signEndDate = document.all('SignEndDate').value;
	var saleChnl = document.all('SaleChnl').value;
	/*
	if (ManageCom == "")
	{
			alert("请选择管理机构！");
			return;
	}
	*/
	if(ManageCom!=null&&ManageCom!=""){
	//if(ManageCom.length<4){
	//	alert("管理机构应为4位到8位！");
	//	return;
	//}  
	}else{
	    alert("请录入管理机构！");
	    return false;
	}
	//if(document.all('PrtNo').value=="")
	//{
	 if (signStartDate == "")
	  {
			alert("请输入承保开始日期！");
			return;
	  }
	  if (signEndDate == "")
	  {
			alert("请输入承保结束日期！");
			return;
  	}
  //}
	initPolGrid();
	// 书写SQL语句
	//var strSQL = " select a.PrtNo,(select RiskName from LMRiskApp where RiskCode=a.RiskCode) riskname,(select nvl(codename,a.salechnl) from ldcode where codetype='salechnl' and code=a.salechnl) salechnl ,a.managecom,(select Name from LAAgent where agentcode=a.agentcode) agentname,a.insuredname insuredname,a.appntname appntname,(select decode(min(c.makedate),null,'无扫描件',to_char(min(c.makedate),'yyyy-mm-dd')) from es_doc_main c where c.doccode=trim(a.prtno)) ScanDate,(select decode(max(d.makedate),null,'未发',to_char(max(d.makedate),'yyyy-mm-dd')) from lcuwsub d where d.ProposalNo=a.ProposalNo and uwflag='8') UWNoticeDate,(select decode(max(e.makedate),null,'未发',to_char(max(e.makedate),'yyyy-mm-dd')) from LCPENotice e where e.ProposalNo = a.ProposalNo) HealthDate,(select decode(max(f.makedate),null,'未发',to_char(max(f.makedate),'yyyy-mm-dd')) from LCRReport f where f.polno = a.ProposalNo) SurveyDate,getQuestSendDate(a.PolNo,'2') AgentQuestSendDate,getQuestSendDate(a.PolNo,'4') ManageQuestSendDate"
	var strSQL="";
             
        //判断类型
    var tScanType = document.all('ScanType').value;
    
 	var  IssueStartDate0 = "";
  	var  IssueEndDate0 = "";
  	var  SaleChnl0 = "";
    if(tScanType !=null&&tScanType!=""){
    	if(tScanType =="卡单"){
//    		strSQL = "  select a.prtno, a.contno, a.managecom,"
//             + "  (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt, "
//             + "    b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom)"
//             + " from lcpol a,lccont b where a.contno =b.contno  and b.conttype ='1' and a.appflag in ('1','4')"
//             + getWherePart('a.signdate','SignStartDate','>=')
//             + getWherePart('a.signdate','SignEndDate','<=')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
//             + " and a.ManageCom like '" + comcode + "%%'";
    		
    		
           if(IssueStartDate!=""&&IssueStartDate!=null){
        	   
//        	   strSQL=strSQL+getWherePart('a.makedate','IssueStartDate','>=');
        	   IssueStartDate0 = window.document.getElementsByName(trim("IssueStartDate"))[0].value;
           }
           if(IssueEndDate!=""&&IssueEndDate!=null){
        	   
//        	   strSQL=strSQL + getWherePart('a.Makedate','IssueEndDate','<=');
        	   IssueEndDate0 = window.document.getElementsByName(trim("IssueEndDate"))[0].value;
           }
           if(saleChnl!=""&&saleChnl!=null){
        	   
//        	   strSQL=strSQL + getWherePart('a.salechnl','SaleChnl','=');
        	   SaleChnl0 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
           }
//    	    strSQL =strSQL+" and b.cardflag ='4'";
    	    
//    	     strSQL+=" order by a.signdate";
    	     
 		  	var  SignStartDate0 = window.document.getElementsByName(trim("SignStartDate"))[0].value;
		  	var  SignEndDate0 = window.document.getElementsByName(trim("SignEndDate"))[0].value;
			var sqlid0="NewPolQuerySql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("f1print.NewPolQuerySql"); //指定使用的properties文件名
			mySql0.setSqlId(sqlid0);//指定使用的Sql的id
			mySql0.addSubPara(SignStartDate0);//指定传入的参数
			mySql0.addSubPara(SignEndDate0);//指定传入的参数
			mySql0.addSubPara(document.all("ManageCom").value);//指定传入的参数
			mySql0.addSubPara(comcode);//指定传入的参数
			mySql0.addSubPara(IssueStartDate0);//指定传入的参数
			mySql0.addSubPara(IssueEndDate0);//指定传入的参数
			mySql0.addSubPara(SaleChnl0);//指定传入的参数
			strSQL=mySql0.getString();
    	     
    	}else{
//	strSQL = "  select a.prtno, a.contno, a.managecom,"
//             + "  (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt, "
//             + "    b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom)"
//             + " from lcpol a,lccont b,es_doc_main c where a.contno =b.contno and b.prtno =c.doccode and c.subtype ='UA001' and b.conttype ='1' and a.appflag in ('1','4')"
//             + getWherePart('a.signdate','SignStartDate','>=')
//             + getWherePart('a.signdate','SignEndDate','<=')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
//             + " and a.ManageCom like '" + comcode + "%%'";
//           if(IssueStartDate!=""&&IssueStartDate!=null)
//             strSQL=strSQL+getWherePart('c.makedate','IssueStartDate','>=');
//           if(IssueEndDate!=""&&IssueEndDate!=null)
//             strSQL=strSQL + getWherePart('c.Makedate','IssueEndDate','<=');
//           if(saleChnl!=""&&saleChnl!=null)
//             strSQL=strSQL + getWherePart('a.salechnl','SaleChnl','=');
             
          var tScanTypesql1="";
          var tScanTypesql2="";
          var tScanTypesql3="";
          var tScanTypesql4="";
     	  if(tScanType =="个险"){
//     	     strSQL = strSQL
//                      + " and substr(a.prtno,1,4) = '8611'  ";
     	    tScanTypesql1=" and substr(a.prtno,1,4) = '8611'  ";
     	  }else if(tScanType =="中介"){
//      	    strSQL = strSQL
//      	   			  +" and substr(a.prtno,1,4) = '8621' ";
      	  tScanTypesql2=" and substr(a.prtno,1,4) = '8621' ";
      	 }else if(tScanType =="简易"){
//     	     strSQL = strSQL
//     	              +" and substr(a.prtno,1,4) = '8616' ";
     	    tScanTypesql3=" and substr(a.prtno,1,4) = '8616' ";
     	  }else if(tScanType =="银代"){
//     	     strSQL = strSQL
//     	     		  +" and substr(a.prtno,1,4) in ('8615','8625','8635') ";
     	    tScanTypesql4=" and substr(a.prtno,1,4) in ('8615','8625','8635') ";
     	  }
//     	  strSQL+=" order by a.signdate";
     	  
		  	var  SignStartDate1 = window.document.getElementsByName(trim("SignStartDate"))[0].value;
		  	var  SignEndDate1 = window.document.getElementsByName(trim("SignEndDate"))[0].value;
		  	var  IssueStartDate1 = window.document.getElementsByName(trim("IssueStartDate"))[0].value;
		  	var  IssueEndDate1 = window.document.getElementsByName(trim("IssueEndDate"))[0].value;
		  	var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
			var sqlid1="NewPolQuerySql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("f1print.NewPolQuerySql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(SignStartDate1);//指定传入的参数
			mySql1.addSubPara(SignEndDate1);//指定传入的参数
			mySql1.addSubPara(document.all("ManageCom").value);//指定传入的参数
			mySql1.addSubPara(comcode);//指定传入的参数
			mySql1.addSubPara(IssueStartDate1);//指定传入的参数
			mySql1.addSubPara(IssueEndDate1);//指定传入的参数
			mySql1.addSubPara(SaleChnl1);//指定传入的参数
			mySql1.addSubPara(tScanTypesql1);//指定传入的参数
			mySql1.addSubPara(tScanTypesql2);//指定传入的参数
			mySql1.addSubPara(tScanTypesql3);//指定传入的参数
			mySql1.addSubPara(tScanTypesql4);//指定传入的参数
			strSQL=mySql1.getString();
     	  
     	  
    	}
    }else{
//    	strSQL = "  select a.prtno, a.contno, a.managecom,"
//             + "  (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt, "
//             + "    b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom)"
//             + " from lcpol a,lccont b,es_doc_main c where a.contno =b.contno and c.subtype ='UA001' and b.prtno =c.doccode and b.conttype ='1' and a.appflag in('1','4')"
//             + getWherePart('a.signdate','SignStartDate','>=')
//             + getWherePart('a.signdate','SignEndDate','<=')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
//             + " and a.ManageCom like '" + comcode + "%%'";
//           if(IssueStartDate!=""&&IssueStartDate!=null)
//             strSQL=strSQL+getWherePart('c.makedate','IssueStartDate','>=');
//           if(IssueEndDate!=""&&IssueEndDate!=null)
//             strSQL=strSQL + getWherePart('c.Makedate','IssueEndDate','<=');
//           if(saleChnl!=""&&saleChnl!=null)
//             strSQL=strSQL + getWherePart('a.salechnl','SaleChnl','=');            
//            strSQL=strSQL + " union"
//             + "  select a.prtno, a.contno, a.managecom,"
//             + "  (select riskname from lmriskapp where riskcode =a.riskcode), a.appntname, a.insuredname,a.amnt, "
//             + "    b.prem,a.agentcode,a.signdate,(select name from lacom where agentcom =a.agentcom)"
//             + " from lcpol a,lccont b where a.contno =b.contno and b.cardflag ='4' and b.conttype ='1' and a.appflag in('1','4')"
//             + getWherePart('a.signdate','SignStartDate','>=')
//             + getWherePart('a.signdate','SignEndDate','<=')
//             + " and a.ManageCom like '" + document.all("ManageCom").value + "%%'"
//             + " and a.riskcode in (select riskcode from lmriskapp where subriskflag='M')"
//             + " and a.ManageCom like '" + comcode + "%%'";
//           if(IssueStartDate!=""&&IssueStartDate!=null)
//             strSQL=strSQL+getWherePart('a.makedate','IssueStartDate','>=');
//           if(IssueEndDate!=""&&IssueEndDate!=null)
//             strSQL=strSQL + getWherePart('a.Makedate','IssueEndDate','<=');
//           if(saleChnl!=""&&saleChnl!=null)
//             strSQL=strSQL + getWherePart('a.salechnl','SaleChnl','='); 
             
//            strSQL+=" order by signdate";
            
		  	var SignStartDate2 = window.document.getElementsByName(trim("SignStartDate"))[0].value;
		  	var SignEndDate2 = window.document.getElementsByName(trim("SignEndDate"))[0].value;
		  	var IssueStartDate2 = window.document.getElementsByName(trim("IssueStartDate"))[0].value;
		  	var IssueEndDate2 = window.document.getElementsByName(trim("IssueEndDate"))[0].value;
		  	var SaleChnl2 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
			var sqlid2="NewPolQuerySql2";
			var mySql2=new SqlClass();
			mySql2.setResourceName("f1print.NewPolQuerySql"); //指定使用的properties文件名
			mySql2.setSqlId(sqlid2);//指定使用的Sql的id
			mySql2.addSubPara(SignStartDate2);//指定传入的参数0
			mySql2.addSubPara(SignEndDate2);//指定传入的参数1
			mySql2.addSubPara(document.all("ManageCom").value);//指定传入的参数2
			mySql2.addSubPara(comcode);//指定传入的参数3
			mySql2.addSubPara(IssueStartDate2);//指定传入的参数4
			mySql2.addSubPara(IssueEndDate2);//指定传入的参数5
			mySql2.addSubPara(SaleChnl2);//指定传入的参数6
			mySql2.addSubPara(SignStartDate2);//指定传入的参数7
			mySql2.addSubPara(SignEndDate2);//指定传入的参数8
			mySql2.addSubPara(document.all("ManageCom").value);//指定传入的参数9
			mySql2.addSubPara(comcode);//指定传入的参数10
			mySql2.addSubPara(IssueStartDate2);//指定传入的参数11
			mySql2.addSubPara(IssueEndDate2);//指定传入的参数12
			mySql2.addSubPara(SaleChnl2);//指定传入的参数13
			strSQL=mySql2.getString();
    }
    //alert(strSQL);
    //document.all('saleChnl').value=strSQL;
    //判断类型
	prompt("abc",strSQL);
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
	/*
	if(cCodeName =="ScanType"){
	  if(Field.value=="卡单"){
	   fm.IssueStartDate.disabled =true;
	   fm.IssueEndDate.disabled =true;
	  }else{
	   fm.IssueStartDate.disabled =false;
	   fm.IssueEndDate.disabled =false;
	  }
	}
	*/
}

