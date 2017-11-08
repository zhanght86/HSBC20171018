//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""	// "1--录入，"2"--查询
var mGrpFlag = ""; 	//个人集体标志,"0"表示个人,"1"表示集体.
var cflag = "WB";        //文件件录入位置

var arrGrpRisk = null;
var turnPage = new turnPageClass();
window.onfocus = myonfocus;
var hiddenBankInfo = "";
 

/*********************************************************************
 *  根据LoadFlag设置一些Flag参数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
	if( cFlag == "1" || cFlag == "99" || cFlag == "8")		// 个人投保单直接录入
	{
		mCurOperateFlag = "1";
		mGrpFlag = "0";
	}
	if( cFlag == "2" || cFlag == "7" || cFlag == "9")		// 集体下个人投保单录入
	{
		mCurOperateFlag = "1";
		mGrpFlag = "1";
	}
	if( cFlag == "3" )		// 个人投保单明细查询
	{
		mCurOperateFlag = "2";
		mGrpFlag = "0";
	}
	if( cFlag == "4" )		// 集体下个人投保单明细查询
	{
		mCurOperateFlag = "2";
		mGrpFlag = "1";
	}
	if( cFlag == "5" )		// 个人投保单复核查询
	{
		mCurOperateFlag = "2";
		mGrpFlag = "3";
	}
}

 
/**
 * 根据身份证号码获取出生日期
 */
function grpGetBirthdayByIdno() {
  var id = document.all("IDNo").value;
  
  if (document.all("IDType").value == "0") {
    if (id.length == 15) {
      id = id.substring(6, 12);
      id = "19" + id; 
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      document.all("Birthday").value = id; 
    } 
    else if (id.length == 18) {
      id = id.substring(6, 14);
      id = id.substring(0, 4) + "-" + id.substring(4, 6) + "-" + id.substring(6);
      document.all("Birthday").value = id; 
    }     
  }
}

/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm()
{
	//var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
	
	  var sqlid1="WbProposalInputSql1";
	  var mySql1=new SqlClass();
	  var PrtNo1 = fm.PrtNo.value;
	  mySql1.setResourceName("app.WbProposalInputSql");
	  mySql1.setSqlId(sqlid1);//指定使用SQL的id
	  mySql1.addSubPara(PrtNo1);//指定传入参数
	  var sSQL = mySql1.getString();
	  
	  var arrCount=easyExecSql(sSQL); 
  if(fm.DealType.value != "03" && arrCount==null)
  {
   	alert("还未保存成功,不容许您进行 [处理完毕] 确认！");
   	return;
  }
	fm.WorkFlowFlag.value = ActivityID ;				
	fm.MissionID.value = MissionID;
	fm.SubMissionID.value = SubMissionID;
	fm.ProposalContNo.value = prtNo;
	
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	//自动置复核状态
  var strDate = Date.parse(new Date());
  var urlStr1 = "./ProposalAutoApprove.jsp?prtNo=" + prtNo + "&DealType="+fm.DealType.value+"&strDate="+strDate;
  var sFeatures = "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable=0";
  //var strResult = window.showModalDialog(urlStr1, "", sFeatures);  
  var name='提示';   //网页名称，可为空; 
	var iWidth=1;      //弹出窗口的宽度; 
	var iHeight=1;     //弹出窗口的高度; 
	var iTop = 1; //获得窗口的垂直位置 
	var iLeft = 1;  //获得窗口的水平位置 
	var strResult = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	strResult.focus();
	lockScreen('lkscreen');  
	fm.action = "./BPOInputConfirm.jsp";
    document.getElementById("fm").submit(); //提交
} 

//问题件查询
function QuestQuery()
{
	var tProposalNo = document.all('ProposalNo').value;  //投保单号码
	if(tProposalNo == "" || tProposalNo=="null")
	{
	    alert("请先保存投保单!");
	}
	else
	{
	    window.open("../uw/QuestQueryMain.jsp?ContNo="+prtNo+"&Flag=1","window1");
	}
}
 
function showSaleChnl() {
  showCodeList('SaleChnl',[this]);
}

//查询中介投保单的代理机构
function showAgentSaleChnl() {
   var tRiskSql = "1 and OtherSign =#21# ";
   //alert("tRiskSql: "+tRiskSql);
   showCodeList('SaleChnl',[this],null,null,tRiskSql,'1',1);
}

function quaryagentgroup()
{
	var tAgentCode = document.all('AgentCode').value;
	
	if( tAgentCode!=null && tAgentCode!="" )
	{
		//var sql="select agentgroup from laagent where agentcode='"+tAgentCode+"'";
		
		  var sqlid2="WbProposalInputSql2";
		  var mySql2=new SqlClass();
		  mySql2.setResourceName("app.WbProposalInputSql");
		  mySql2.setSqlId(sqlid2);//指定使用SQL的id
		  mySql2.addSubPara(tAgentCode);//指定传入参数
		  var sql = mySql2.getString();
		 
		  var tResult = easyExecSql(sql, 1, 1, 1); 
		if(tResult!=null)
		{
			document.all('AgentGroup').value=tResult[0][0];
		}
	}	
} 

//校验投保日期
function checkapplydate() 
{
	if(trim(fm.PolApplyDate.value)==""){return ;}
    else if (fm.PolApplyDate.value.length == 8)
	{
		if(fm.PolApplyDate.value.indexOf('-')==-1)
		{
			var Year =     fm.PolApplyDate.value.substring(0,4);
			var Month =    fm.PolApplyDate.value.substring(4,6);
			var Day =      fm.PolApplyDate.value.substring(6,8);
			fm.PolApplyDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("您输入的投保日期有误!");
			fm.PolApplyDate.value = "";
			fm.PolApplyDate.focus();
			return;
			}
		}else{
			alert("您输入的投保日期格式有误!");
			fm.PolApplyDate.value = "";
			fm.PolApplyDate.focus();
			return;
		}
	}
	else if(fm.PolApplyDate.value.length == 10)
	{
		var Year =     fm.PolApplyDate.value.substring(0,4);
		var Month =    fm.PolApplyDate.value.substring(5,7);
		var Day =      fm.PolApplyDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的投保日期有误!");
		fm.PolApplyDate.value = "";
		fm.PolApplyDate.focus();
		return;
		}
	}
	else
	{
		alert("您输入的投保日期有误!");
        fm.PolApplyDate.value = "";
        fm.PolApplyDate.focus();
        return;
	}
}
 
function quaryOccupationType()
{
	
	//alert("1");
	//投保人
	if(document.all('AppntOccupationCode').value!=null && document.all('AppntOccupationCode').value!="")
	{
		//var Sql="select  OccupationType,OccupationName from LDOccupation where 1=1 "
	//	+"and occupationcode='"+document.all('AppntOccupationCode').value+"' ";
		
		  var sqlid3="WbProposalInputSql3";
		  var mySql3=new SqlClass();
		  var AppntOccupationCode3 = document.all('AppntOccupationCode').value;
		  mySql3.setResourceName("app.WbProposalInputSql");
		  mySql3.setSqlId(sqlid3);//指定使用SQL的id
		  mySql3.addSubPara(AppntOccupationCode3);//指定传入参数
		  var Sql = mySql3.getString();
		  
		  var tResult = easyExecSql(Sql, 1, 1, 1); 
	
		if(tResult!=null)
	    {                
		  document.all('AppntOccupationType').value=tResult[0][0];
		  //document.all('AppntOccupationCodeName').value=tResult[0][1];
		 }
     }
  //被保人
  for (var i=1 ;i<=3; i++)
  {
  		if(document.all('OccupationCode'+i).value!=null && document.all('OccupationCode'+i).value!="")
		{
//			 var Sql1="select  OccupationType,OccupationName from LDOccupation where 1=1 "
//			 +"and occupationcode='"+document.all('OccupationCode'+i).value+"' ";
			 
			  var sqlid4="WbProposalInputSql4";
			  var mySql4=new SqlClass();
			  var OccupationCode4 = document.all('OccupationCode'+i).value;
			  mySql4.setResourceName("app.WbProposalInputSql");
			  mySql4.setSqlId(sqlid4);//指定使用SQL的id
			  mySql4.addSubPara(OccupationCode4);//指定传入参数
			  var Sql1 = mySql4.getString();
			 
			  var tResult1 = easyExecSql(Sql1, 1, 1, 1); 
	  
		   if(tResult1!=null)
		   {
		  	document.all('OccupationType'+i).value=tResult1[0][0];
		  	//document.all('OccupationCodeName'+i).value=tResult[0][1];
		   }
		}
  }  
}

//检查录入的管理机构是否正确
function checkInputSalechnl()
{
  var arrSaleChnl = new Array(); 
 // var sql = "select 1 from ldcode where codetype = 'salechnl' and code='"+document.all("SaleChnl").value+"'"; 
  
  var sqlid5="WbProposalInputSql5";
  var mySql5=new SqlClass();
  var SaleChnl5 = document.all("SaleChnl").value;
  mySql5.setResourceName("app.WbProposalInputSql");
  mySql5.setSqlId(sqlid5);//指定使用SQL的id
  mySql5.addSubPara(SaleChnl5);//指定传入参数
  var sql = mySql5.getString();
  
  arrSaleChnl = easyExecSql( sql , 1, 0);
  //alert(arrSaleChnl);	
  if (arrSaleChnl == null)	
  {
   alert("销售渠道录入错误!");
   return false
  }
  	
  return true;	
}


/*********************************************************************
 *  校验投保单的输入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function verifyProposal() {
  var passVerify = true;
  var tflag = false; 
 
//  if(fm.AppntRelationToInsured.value=="00"){
//    if(fm.AppntCustomerNo.value!= fm.CustomerNo.value){		
//      alert("投保人与被保人关系为本人，但客户号不一致");
//      return false;
//    }
//  }  
  ////校验录入的险种是否不需要校验，如果需要返回true,否则返回false
//   if(needVerifyRiskcode("NotVerifyRiskcode")==true)
//   {
		if(verifyInput() == false) passVerify = false;
//		
//		BnfGrid.delBlankLine();
//		
//		if(BnfGrid.checkValue("BnfGrid") == false) passVerify = false;
//		
//		 //校验单证是否发放给业务员
//		 //if (!verifyPrtNo(document.all("PrtNo").value)) passVerify = false;
//    }
	
	try {
	  var strChkIdNo = "";
    	
		  //以年龄和性别校验身份证号
		  if (document.all("AppntIDType").value == "0") 
		    strChkIdNo = chkIdNo(document.all("AppntIDNo").value, document.all("AppntBirthday").value, document.all("AppntSex").value);
		  if (document.all("IDType").value == "0") 
		    strChkIdNo = chkIdNo(document.all("IDNo").value, document.all("Birthday").value, document.all("Sex").value);
		    
		  if (strChkIdNo != "") {
		    alert(strChkIdNo);
		    passVerify = false; 
	  	} 
	   
	  //校验职业和职业代码
//	  var arrCode = new Array();
//	  arrCode = verifyCode("职业（工种）", document.all("AppntWorkType").value, "code:OccupationCode", 1); 
//	  if (arrCode!=true && document.all("AppntOccupationCode").value!=arrCode[0]) {
//	    alert("投保人职业和职业代码不匹配！");
//	    passVerify = false;
//	  }
//	  arrCode = verifyCode("职业（工种）", document.all("WorkType").value, "code:OccupationCode", 1); 
//	  if (arrCode!=true && document.all("OccupationCode").value!=arrCode[0]) {
//	    alert("被保人职业和职业代码不匹配！");
//	    passVerify = false;
//	  }
	  var occupation =  document.all("AppntOccupationCode").value;
	  if(occupation != null && occupation != "")
	  {
//  	  var tsql = "select * from LDOccupation where OccupationVer='002' and OccupationCode = '" + occupation + "' " ;
	  	
	  	  var sqlid6="WbProposalInputSql6";
	  	  var mySql6=new SqlClass();
	  	  mySql6.setResourceName("app.WbProposalInputSql");
	  	  mySql6.setSqlId(sqlid6);//指定使用SQL的id
	  	  mySql6.addSubPara(occupation);//指定传入参数
	  	  var tsql = mySql6.getString();
	  	 
		  tResult = decodeEasyQueryResult(easyQueryVer3(tsql));
			if(tResult == null || tResult == "")
			{
				alert("录入的职业代码不在数据库定义的范围！");
				return false;
			}  
	  }
	    
	  //校验受益比例
	  var i;
	  var sumLiveBnf = new Array();
	  var sumDeadBnf = new Array();
    	  var bnfIDNo ;
	  for (i=0; i<BnfGrid.mulLineCount; i++) {
	    if (BnfGrid.getRowColData(i, 1) == "0") {
	      if (typeof(sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined") 
	        sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
	      sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumLiveBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 6));
	    }
	    else if (BnfGrid.getRowColData(i, 1) == "1") {
	      if (typeof(sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))]) == "undefined") 
	        sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = 0;
	      sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] = sumDeadBnf[parseInt(BnfGrid.getRowColData(i, 7))] + parseFloat(BnfGrid.getRowColData(i, 6));
	    }
	    //校验受益人身份证号码
	    if (BnfGrid.getRowColData(i, 3) == "0") 
	    {
	    	bnfIDNo = trim(BnfGrid.getRowColData(i, 4));
				if ((bnfIDNo.length!=15) && (bnfIDNo.length!=18))
				{
				  alert("受益人的身份证号"+bnfIDNo+"位数错误!");
			    passVerify = false;
				}
	    }
	  }
	  
	  for (i=0; i<sumLiveBnf.length; i++) {
  	  if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1) {
  	    alert("生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i] + " 。大于100%，不能提交！");
  	    passVerify = false; 
  	  }
  	  else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1) {
  	    alert("注意：生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i] + " 。小于100%");
  	    passVerify = false;
  	  }
	  } 
	  
	  for (i=0; i<sumDeadBnf.length; i++) {
  	  if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1) {
  	    alert("死亡受益人受益顺序 " + i + " 的受益比例和为：" + sumDeadBnf[i] + " 。大于100%，不能提交！");
  	    passVerify = false; 
  	  }
  	  else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1) {
  	    alert("注意：死亡受益人受益顺序 " + i + " 的受益比例和为：" + sumDeadBnf[i] + " 。小于100%");
  	    passVerify = false;
  	  }
	  } 
	  
	  if (trim(fm.BankCode.value)=="0101") {
      if (trim(fm.BankAccNo.value).length!=19 || !isInteger(trim(fm.BankAccNo.value))) {
        alert("工商银行的账号必须是19位的数字，最后一个星号（*）不要！\n如果客户填写无误，请一定发问题件！");
        passVerify = false;
      }
    }
    
    //校验客户是否死亡
    if (fm.AppntCustomerNo.value!="" && isDeath(fm.AppntCustomerNo.value)) {
      alert("投保人已经死亡！");
      passVerify = false;
    }
    
    if (fm.CustomerNo.value!="" && isDeath(fm.CustomerNo.value)) {
      alert("被保人已经死亡！");
      passVerify = false;
    }
	}
	catch(e) {}
	
	if (!passVerify) {
	  if (!confirm("投保单录入可能有错误，是否继续保存？")) return false;
	  else return true;
	}
}

//校验客户是否死亡
function isDeath(CustomerNo) {
  //var strSql = "select DeathDate from LDPerson where CustomerNo='" + CustomerNo + "'";
  
  var sqlid7="WbProposalInputSql7";
  var mySql7=new SqlClass();
  mySql7.setResourceName("app.WbProposalInputSql");
  mySql7.setSqlId(sqlid7);//指定使用SQL的id
  mySql7.addSubPara(CustomerNo);//指定传入参数
  var strSql = mySql7.getString();
 
  var arrResult = easyExecSql(strSql);
  
  if (arrResult == ""||arrResult == null) return false;
  else return true;
}

// 校验险种
function verifyRisk()
{	
	var tPrtNo = fm.PrtNo.value;
	var tPayMode = fm.NewPayMode.value;
	var tPayLocation = fm.PayLocation.value;
	var tPayIntv = fm.PayIntv.value;
	var tPrem = 0;
	var tpayendyear = 0;
	var allprem =0;
	var checkscanx =0;
	var checkscanr =0;
	for(var m=1;m<=3;m++)
	{
		var StrInsur = "";
		if(m==1)
			StrInsur = "被保人";
		else
			StrInsur = "第"+m+"被保人";
	    var InsurAge = calAgeNew(document.all("Birthday"+m).value,document.all("PolApplyDate").value);
		for(var n=1;n<=3;n++)
		{
			var arrReturn = new Array();
			var tSel ;
			eval("tSel = Risk"+m+n+"Grid.mulLineCount");
			var riskcode ="";
			var insuYear = "";
			var insuYearFlag = "";
			var prem ="";
		    var addprem ="";		    
			var sql ="";
			for(var i=0;i< tSel;i++)
			{			
			    eval("riskcode = Risk"+m+n+"Grid.getRowColData("+i+", 3)");
			    eval("insuYear = Risk"+m+n+"Grid.getRowColData("+i+", 7)");
			    eval("insuYearFlag = Risk"+m+n+"Grid.getRowColData("+i+", 8)");
			    if( riskcode == "312204" && insuYear == 5 && insuYearFlag =="Y")
			    {
			        if (!confirm(StrInsur+"：险种"+riskcode+"5年期产品已停售，请撤单！是否继续保存？")) return false;
			    }	
			    if( riskcode == "121501" )//|| riskcode == "112208"
			    {
			        if (!confirm(StrInsur+"：险种"+riskcode+"已停止销售，请撤单！是否继续保存？")) return false;
			    }			    
//			    sql = "select sysvarvalue from ldsysvar where sysvar ='tbcheckscandate'";
//			    sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
			    
			    var sqlid8="WbProposalInputSql8";
			    var mySql8=new SqlClass();
			    mySql8.setResourceName("app.WbProposalInputSql");
			    mySql8.setSqlId(sqlid8);//指定使用SQL的id
			    sql = mySql8.getString();
			    
  				var arrResult2 = easyExecSql(sql);				
			    if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
			    {
			    	// 校验新型产品说明书
	//		    	sql="select count(1) from lmriskapp where RiskType3 in ('2','4') and riskcode='"+riskcode+"'";
			    	
			    	  var sqlid9="WbProposalInputSql9";
			    	  var mySql9=new SqlClass();
			    	  mySql9.setResourceName("app.WbProposalInputSql");
			    	  mySql9.setSqlId(sqlid9);//指定使用SQL的id
			    	  mySql9.addSubPara(riskcode);//指定传入参数
			    	  sql = mySql9.getString();
			    	
			          var arrResult = easyExecSql(sql);
	//		        sql="select count(1) from es_doc_main where doccode='"+fm.PrtNo.value+"' and subtype='UR211'";
			        
			          var sqlid10="WbProposalInputSql10";
			    	  var mySql10=new SqlClass();
			    	  var PrtNo10 = fm.PrtNo.value;
			    	  mySql10.setResourceName("app.WbProposalInputSql");
			    	  mySql10.setSqlId(sqlid10);//指定使用SQL的id
			    	  mySql10.addSubPara(PrtNo10);//指定传入参数
			    	  sql = mySql10.getString();
			    	 
				    var arrResult1 = easyExecSql(sql);
				    
			   		if (arrResult!=null && arrResult1!=null && arrResult[0][0]!='0'&& arrResult1[0][0]=='0')
			   		{
			   		    checkscanx =1;			   		    
			   		}
			   		
			   		//校验人身保险投保提示
	//		   		sql="select count(1) from es_doc_main where doccode='"+fm.PrtNo.value+"' and subtype='UR200'";
			   		
			   		  var sqlid11="WbProposalInputSql11";
			    	  var mySql11=new SqlClass();
			    	  var PrtNo11 = fm.PrtNo.value;
			    	  mySql11.setResourceName("app.WbProposalInputSql");
			    	  mySql11.setSqlId(sqlid11);//指定使用SQL的id
			    	  mySql11.addSubPara(PrtNo11);//指定传入参数
			    	  sql = mySql11.getString();
				      var arrResult4 = easyExecSql(sql);
			   		if(arrResult4!=null && arrResult4[0][0]=='0' &&((parseInt(InsurAge+1)<insuYear && insuYearFlag=='A') || (1<insuYear && insuYearFlag=='Y')))
			   		{
			   			checkscanr =1;			   			
			   		}
			    }			    		        
		        
		        eval("prem = Risk"+m+n+"Grid.getRowColData("+i+", 11)");	
		        eval("addprem = Risk"+m+n+"Grid.getRowColData("+i+", 12)");
		        if(prem!=null && prem!='')
		        	allprem =  parseFloat(allprem) + parseFloat(prem);
		        if(addprem!=null && addprem!='')
		        	allprem =  parseFloat(allprem) + parseFloat(addprem);//包括加费
		        
		        //2010-3-12 ln add 对身份证明扫描件进行校验 取整单保费	
		        if(prem==null || prem=='')
		        	prem='0';
		        if(addprem==null || addprem=='')
		        	addprem='0';
		        eval("payendyear = Risk"+m+n+"Grid.getRowColData("+i+", 9)");	
		        eval("payendyearflag = Risk"+m+n+"Grid.getRowColData("+i+", 10)");		        
		        if(tPayIntv!=null && tPayIntv!='0')//期交
		        {
		//        	sql="select subriskflag,RiskPeriod from lmriskapp where riskcode='"+riskcode+"'";
		        	
		        	  var sqlid12="WbProposalInputSql12";
			    	  var mySql12=new SqlClass();
			    	  mySql12.setResourceName("app.WbProposalInputSql");
			    	  mySql12.setSqlId(sqlid12);//指定使用SQL的id
			    	  mySql12.addSubPara(riskcode);//指定传入参数
			    	  sql = mySql12.getString();
			        var subriskflag = easyExecSql(sql);
			        if(subriskflag[0][0]=='S' && subriskflag[0][1]!='L')//不为长期附加险 交费方式为趸交
			        {
			        	tPrem =  parseFloat(tPrem) + parseFloat(prem) + parseFloat(addprem);
			        }
			        else
			        {			        	
			        	if(payendyearflag=='Y')
			        		tpayendyear = payendyear;
			        	else
			        		tpayendyear = parseInt(payendyear) -  InsurAge;
			        	if(tPayIntv=='1')
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*12*tpayendyear;
			        	else if(tPayIntv=='3')
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*4*tpayendyear;
			        	else if(tPayIntv=='6')
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*2*tpayendyear;
			        	else
			        		tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem))*tpayendyear;			        		
			        }
		        }
		        else//趸交
		        	tPrem =  parseFloat(tPrem) + (parseFloat(prem) + parseFloat(addprem));
			}
			
		}
	}
	if(checkscanx==1)
		if (!confirm("存在分红型险种或万能型险种，无《新型产品说明书》！是否继续保存？")) return false;
	if(checkscanr==1)
		if (!confirm("投保资料明细提供不齐全，无《人身保险投保提示》！是否继续保存？")) return false;
	
	// 2009-3-5 ln add 对个险和中介进行首续期交费形式校验
				if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) == "11" || tPrtNo.substring(2,4) == "21"))
				{    
					//alert(allprem);
					if((parseFloat(allprem) - 1000) >0)
				   	{	 		
				   		if((tPayMode!=null && tPayMode!="" && (tPayMode=="0" || tPayMode=="5" || tPayMode=="b"))
					        			  && (tPayLocation!=null && tPayLocation!="" && (tPayLocation=="0" || tPayLocation=="4" || tPayLocation=="5" || tPayLocation=="b")))
					    {    		  
					    }
					    else
					    {
					        if (!confirm("首、续期交费应为银行转账、客户自交或其它！是否继续保存？")) return false;
					    }
				   	}
				}
				
	//2010-3-12 ln add 对身份证明扫描件进行校验
//				sql = "select sysvarvalue from ldsysvar where sysvar ='tbcheckhaveiddate'";
//				sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
				
				 var sqlid13="WbProposalInputSql13";
		    	  var mySql13=new SqlClass();
		    	  mySql13.setResourceName("app.WbProposalInputSql");
		    	  mySql13.setSqlId(sqlid13);//指定使用SQL的id
		    	  sql = mySql13.getString();
  				var checkdate = easyExecSql(sql);		//alert(checkdate);
 //				sql="select count(1) from es_doc_main where doccode='"+fm.PrtNo.value+"' and subtype='UR208'";
  				
  				 var sqlid14="WbProposalInputSql14";
		    	  var mySql14=new SqlClass();
		    	  var PrtNo14 = fm.PrtNo.value;
		    	  mySql14.setResourceName("app.WbProposalInputSql");
		    	  mySql14.setSqlId(sqlid14);//指定使用SQL的id
		    	  mySql14.addSubPara(PrtNo14);//指定传入参数
		    	  sql = mySql14.getString();
				var havescan = easyExecSql(sql);		
			    if(checkdate!=null && (calAgeNew(checkdate[0][0],document.all("PolApplyDate").value)>=0)&& havescan!=null && havescan[0][0]=='0' )
			    {
			    	var cPrem = 0;			    	
			    	if(tPayIntv!=null && tPayIntv!='0')//期交
			    	{
			    		if(tPayMode!=null && tPayMode=='0'&& tPayLocation!=null && tPayLocation=='0')
			    			cPrem = 200000;
			    	    else
			    	    	cPrem = 20000;
			    	}
			    	else//趸交
			    	{
			    		if(tPayMode!=null && tPayMode=='0')
			    			cPrem = 200000;
			    	    else
			    	    	cPrem = 20000;
			    	}
			        if(tPrem-cPrem>=0)
			        	if (!confirm("不符合反洗钱实施细则规定，未提供客户有效证件复印件！是否继续保存？")) return false;  	
				}
				
	return true;
	
}

// 校验必选告知是否录入
function verifyImpart()
{
	var tPrtNo = fm.PrtNo.value;
	var InsuSequenceNo = fm.SequenceNo1.value;
	// 2009-3-5 ln add 对个险和中介进行必选告知录入情况校验
	/*if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) == "11" || tPrtNo.substring(2,4) == "21"))
	{  		
		var tSel = ImpartGrid.mulLineCount;
		var impartver ="";
		var impartcode = "";
		var customernotype = "";
		var insuredno ="";	
		var count1 = 0;
		var count2 = 0;
		var count3 = 0;
		var count4 = 0;    
		for(var i=0;i< tSel;i++)
		{			
			impartver = ImpartGrid.getRowColData(i, 1);
			impartcode = ImpartGrid.getRowColData(i, 2);
			customernotype = ImpartGrid.getRowColData(i, 5);
			insuredno = ImpartGrid.getRowColData(i, 6);
			if( impartcode!=null && impartcode == "A0101" )
			{
			    	count1 = 1;
			}	
			if( impartcode!=null && impartcode == "A0120" )
			{
			    count2 = 1;
			}	
			if( impartcode!=null && impartcode == "A0152" )
			{
			    count3 = 1;
			}
			if( impartcode!=null && impartcode == "A0155" )
			{
			    count4 = 1;
			}	
		}
		if(count1 != 1)
			if (!confirm("没有录入告知(编码:A0101)！是否继续保存？")) return false;
		if(count2 != 1)
			if (!confirm("没有录入告知(编码:A0120)！是否继续保存？")) return false;
		if(count3 != 1)
			if (!confirm("没有录入告知(编码:A0152)！是否继续保存？")) return false;
		if(count4 != 1)
			if (!confirm("没有录入告知(编码:A0155)！是否继续保存？")) return false;
	}*/
	
	//所有保单类型的“投保申请日期”如在录入日期之后，保存时给出问题件，但能强行保存
	if(calAge(document.all("PolApplyDate").value)<0)
		if (!confirm("投保申请日期晚于录入日期！是否继续保存？")) return false;	
	
	//var strSql = "select sysvarvalue from ldsysvar where sysvar ='tbstartcheckdate'";
	
	var sqlid15="WbProposalInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.WbProposalInputSql");
	mySql15.setSqlId(sqlid15);//指定使用SQL的id
    var strSql = mySql15.getString();
  	var arrResult = easyExecSql(strSql);	
  	
 // 	var strSql1 = "select sysvarvalue from ldsysvar where sysvar ='tbcheckdate'";
  	
  	var sqlid16="WbProposalInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.WbProposalInputSql");
	mySql16.setSqlId(sqlid16);//指定使用SQL的id
    var strSql1 = mySql16.getString();
  
  	var arrResult1 = easyExecSql(strSql1);
  	if(arrResult!=null && arrResult[0][0]!='' && arrResult1!=null&& arrResult1[0][0]!='')
  	{
  		if((calAge(arrResult[0][0])>=0) && (calAgeNew(arrResult1[0][0],document.all("PolApplyDate").value)<0))
			if (!confirm("投保申请日期早于"+arrResult1[0][0]+"！是否继续保存？")) return false;	
  	}
  	
//	var strSql2 = "select sysvarvalue from ldsysvar where sysvar ='checkPrtNoDate'";
	
	var sqlid17="WbProposalInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName("app.WbProposalInputSql");
	mySql17.setSqlId(sqlid17);//指定使用SQL的id
    var strSql2 = mySql17.getString();
    
  	var arrResult2 = easyExecSql(strSql2);
  	var tsubPrtNo = tPrtNo.substring(0,6);
  	var tsubPrtNo2 = tPrtNo.substring(0,4);
  	if(arrResult!=null && arrResult[0][0]!='' && arrResult2!=null && arrResult2[0][0]!=''
  			&& (calAge(arrResult[0][0])>=0)&&(calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
  	{
  		if(tsubPrtNo2!="3110"){
  		if(tsubPrtNo != "861102" && tsubPrtNo != "861602" && tsubPrtNo != "862102" && tsubPrtNo != "861502")
  			if (!confirm("印刷号前六位必须为861102、861602、862102、861502！是否继续保存？")) return false;
  		}
  	}
	
	return true;
	
}

//录入校验
function checkInput()
{
    if ((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value=="00") || (fm.RelationToAppnt1.value!="" && fm.RelationToAppnt1.value=="00")) {
	    if(fm.AppntName.value!=fm.Name1.value)
	    {
	    	//alert("投保人和被保人关系为本人，姓名必须相同！");
	    	//return false;
	    }
    }
    //首期校验
    if(fm.NewPayMode.value==null || fm.NewPayMode.value=='' ||(fm.NewPayMode.value!='0' && fm.NewPayMode.value!='8'))
    {
    	if((fm.NewBankCode.value!=null && fm.NewBankCode.value!="")
    		||(fm.NewAccName.value!=null && fm.NewAccName.value!="")
    		||(fm.NewBankAccNo.value!=null && fm.NewBankAccNo.value!=""))
    		{
    			alert("首期交费形式为空或不为银行转账，不能录入首期帐户信息！");
	   			 return false;
    		}
    }
    else
    {
    	if((fm.NewAccName.value==null || fm.NewAccName.value=="")
    		||(fm.NewAccName.value==null || fm.NewAccName.value=="")
    		||(fm.NewAccName.value==null || fm.NewAccName.value==""))
    		{
    			alert("首期帐户信息必须录入！");
	   			 return false;
    		}
    	
    }
    
    //续期校验
    if(fm.PayLocation.value==null || fm.PayLocation.value=='' ||(fm.PayLocation.value!='0' && fm.PayLocation.value!='8'))
    {
    	if((fm.BankCode.value!=null && fm.BankCode.value!="")
    		||(fm.AccName.value!=null && fm.AccName.value!="")
    		||(fm.BankAccNo.value!=null && fm.BankAccNo.value!=""))
    		{
    			alert("续期交费形式为空或不为银行转账，不能录入续期帐户信息！");
	   			 return false;
    		}
    }
    else
    {
    	if((fm.BankCode.value==null || fm.BankCode.value=="")
    		||(fm.AccName.value==null || fm.AccName.value=="")
    		||(fm.BankAccNo.value==null || fm.BankAccNo.value==""))
    		{
    			alert("续期帐户信息必须录入！");
	   			 return false;
    		}
    	
    }
    
    if(fm.NewAccName.value!=null && fm.NewAccName.value!="" && fm.AppntName.value!=fm.NewAccName.value)
	    {
	    	
	    	alert("首期帐户名必须为投保人姓名！");
	    	return false;
	    }
	if(fm.AccName.value!=null && fm.AccName.value!="" && fm.AppntName.value!=fm.AccName.value)
	    {
	    	alert("续期帐户名必须为投保人姓名！");
	    	return false;
	    }
}

function checkSaleChnl()
{
	var SaleChnl = trim(document.all('SaleChnl').value);
	var AgentCode = trim(document.all('AgentCode').value);
	var tSubRiskFlag ;   //主附险标记
	var tResult ; 
	var prtNo = document.all("PrtNo").value;
	var riskType = prtNo.substring(2, 4);
	var AgentCom = trim(document.all('AgentCom').value);
	if(SaleChnl == null || SaleChnl == "")
	{
		alert("没有输入销售渠道!");
		return false;
	}
	if(SaleChnl == "03" )
	{
		if(AgentCom == "")
		{
			alert("销售渠道为银代时必须输入代理机构!");
			return false;
		}
	}
	if( riskType=="21" )
	{
		if(AgentCom == "")
		{
			alert("中介投保单必须输入代理机构!");
			return false;
		}
		
	}
	
	//中介数据迁移一期完成后需要校验中介代理机构必须和中介客户经理匹配
	//add by weikai
	//alert("AgentCom:"+AgentCom);
	if(AgentCom!=""&&AgentCom!=null)
	{
		if(AgentCom.substring(0,1)=='2')
		{
//			var tAgentCheck_sql="select 1 from laagent a,latree b where a.agentcode=b.agentcode "
//			+" and a.branchtype='3' and b.branchcode='1' and a.agentcode='"+AgentCode+"'";
			
			 var sqlid18="WbProposalInputSql18";
	    	  var mySql18=new SqlClass();
	    	  mySql18.setResourceName("app.WbProposalInputSql");
	    	  mySql18.setSqlId(sqlid18);//指定使用SQL的id
	    	  mySql18.addSubPara(AgentCode);//指定传入参数
	    	  var tAgentCheck_sql = mySql18.getString();
			
			var AgentCheckFlag = decodeEasyQueryResult(easyQueryVer3(tAgentCheck_sql));
			if(AgentCheckFlag == null || AgentCheckFlag == "")
			{
				alert("代理机构为2开头的中介代理机构，代理人必须为中介客户经理！！");
				return false;
			}
		}
	}
	
	if(SaleChnl == "02")
	{
	  if(AgentCom != null && AgentCom != "")
	  {
	  	alert("销售渠道为个人时不能输入代理机构!");
	  	return false;
	  }
	}
	
  if(AgentCom != null && AgentCom != "")
  {
  //	var tsql = "select * from lacom where agentcom = '" + AgentCom + "' " ;
  	
  	 var sqlid19="WbProposalInputSql19";
	 var mySql19=new SqlClass();
	 mySql19.setResourceName("app.WbProposalInputSql");
	 mySql19.setSqlId(sqlid19);//指定使用SQL的id
	 mySql19.addSubPara(AgentCom);//指定传入参数
	 var tsql = mySql19.getString();
	 tResult = decodeEasyQueryResult(easyQueryVer3(tsql));
		if(tResult == null || tResult == "")
		{
			alert("录入的代理机构不在数据库定义的范围！");
			return false;
		}  
  }
	
	if((document.all('RiskCode').value == null) && (trim(document.all('RiskCode').value) == ""))
	{
	  alert("险种编码不能为空!");
	  return false;
	}
	
//	var sql = "SELECT BranchType FROM laagent where agentcode='" + AgentCode + "'";

	var sqlid20 = "WbProposalInputSql20";
	var mySql20 = new SqlClass();
	mySql20.setResourceName("app.WbProposalInputSql");
	mySql20.setSqlId(sqlid20);//指定使用SQL的id
	mySql20.addSubPara(AgentCode);//指定传入参数
	var sql = mySql20.getString();
	var BranchType = decodeEasyQueryResult(easyQueryVer3(sql));
	if(BranchType == null || BranchType == "")
	{
		alert("该代理人的展业类型未知!");
		return false;
	}
	
//	var tRiskSql = "select SubRiskFlag from LMRiskApp where RiskCode = '"+trim(document.all('RiskCode').value)+"'";
	
	var sqlid21 = "WbProposalInputSql21";
	var mySql21 = new SqlClass();
	var RiskCode21 = trim(document.all('RiskCode').value);
	mySql21.setResourceName("app.WbProposalInputSql");
	mySql21.setSqlId(sqlid21);//指定使用SQL的id
	mySql21.addSubPara(RiskCode21);//指定传入参数
	var tRiskSql = mySql21.getString();
	
        tSubRiskFlag = decodeEasyQueryResult(easyQueryVer3(tRiskSql));
        //alert(tRiskSql);
        //alert(tSubRiskFlag);
        if(tSubRiskFlag == null || tSubRiskFlag == "")
	{
		alert("险种主附险标志未知!");
		return false;
	}
	//主险判断保单销售渠道和业务员的BranchType
        if(tSubRiskFlag[0][0]=="M")
        {
  //        sql = "select 1 from LDCode1 where codetype='salechnlagentctrl' and code='"+SaleChnl+"' and ( code1='"+BranchType[0][0]+"' or othersign='1')";  //othersign='1' 表示销售渠道和代理人无校验
         
          var sqlid22 = "WbProposalInputSql22";
      	  var mySql22 = new SqlClass();
      	  mySql22.setResourceName("app.WbProposalInputSql");
      	  mySql22.setSqlId(sqlid22);//指定使用SQL的id
      	  mySql22.addSubPara(SaleChnl);//指定传入参数
      	  mySql22.addSubPara(BranchType[0][0]);//指定传入参数
      	  sql = mySql22.getString();
          
          tResult = decodeEasyQueryResult(easyQueryVer3(sql));
          if(tResult == null || tResult == "")
	  {
		alert("保单的销售渠道与代理人不匹配!");
		return false;
	  }       
        }
       else  //附加险判断是否与主险销售渠道以及代理人一致
        {
	// sql = "select 1 from LCPol where PolNo='"+trim(document.all('MainPolNo').value)+"' and agentcode='"+AgentCode+"' and salechnl='"+SaleChnl+"'";	 
	
	  var sqlid23 = "WbProposalInputSql23";
 	  var mySql23 = new SqlClass();
 	  var MainPolNo23 = trim(document.all('MainPolNo').value);
 	  mySql23.setResourceName("app.WbProposalInputSql");
 	  mySql23.setSqlId(sqlid23);//指定使用SQL的id
 	  mySql23.addSubPara(MainPolNo23);//指定传入参数
 	  mySql23.addSubPara(AgentCode);//指定传入参数
 	  mySql23.addSubPara(SaleChnl);//指定传入参数
 	  sql = mySql23.getString();
	 
	 tResult = decodeEasyQueryResult(easyQueryVer3(sql));
          if(tResult == null || tResult == "")
	  {
		alert("附加险与主险的销售渠道与代理人不匹配!");
		return false;
	  }  
	}
        
	return true;
}

function checkComCode()
{
	if((document.all('PrtNo').readOnly == true) && (document.all('PrtNo').value != ""))
	{
		
	//	var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "'";
		
		  var sqlid24 = "WbProposalInputSql24";
	 	  var mySql24 = new SqlClass();
	 	  var PrtNo24 = trim(document.all('PrtNo').value) ;
	 	  mySql24.setResourceName("app.WbProposalInputSql");
	 	  mySql24.setSqlId(sqlid24);//指定使用SQL的id
	 	  mySql24.addSubPara(PrtNo24 );//指定传入参数
	 	  var sql = mySql24.getString();
		
		var ManageCom = decodeEasyQueryResult(easyQueryVer3(sql));
		var Managecom2 =trim(document.all('ManageCom').value);	
		if(ManageCom!=null)
		{
		   if(ManageCom[0][0]!=Managecom2)
		    {
			alert("扫描件与录入机构不是同一个机构!");
			return false;
		    }
	    } 
    }
	return true;
}

//查询管理机构
function initManageCom()
{
	/*(if((document.all('PrtNo').readOnly == true) && (document.all('PrtNo').value != ""))
	{		
		var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "'";
		var ManageCom = decodeEasyQueryResult(easyQueryVer3(sql));	
		fm.ScanManageCom.value = ManageCom[0][0];
	}*/
	fm.AgentManageCom.value = '';
	fm.AgentManageComName.value = '';
	var tSaleChnl = fm.SaleChnl.value;
    if(tSaleChnl!=null && tSaleChnl != '')
	{
		if(tSaleChnl == '03' || tSaleChnl == '05')
		{
			if(fm.AgentCom.value != null && fm.AgentCom.value != "")
			{
//				var strSql = "select ManageCom,(select name from ldcom where comcode=managecom),branchtype from LACom where AgentCom='" + fm.AgentCom.value +"'";
			  
				  var sqlid25 = "WbProposalInputSql25";
			 	  var mySql25 = new SqlClass();
			 	  var AgentCom25 = fm.AgentCom.value;
			 	  mySql25.setResourceName("app.WbProposalInputSql");
			 	  mySql25.setSqlId(sqlid25);//指定使用SQL的id
			 	  mySql25.addSubPara(AgentCom25);//指定传入参数
			 	  var strSql = mySql25.getString();
				  var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.AgentManageCom.value = arrResult[0][0];
			      fm.AgentManageComName.value = arrResult[0][1];
			      if(arrResult[0][2]!=null &&　arrResult[0][2]=='6')
			      {
			      	if(document.all('ManageCom').value != null && document.all('ManageCom').value != "")
			      	{
			      		var cManageCom = document.all('ManageCom').value;
			      		var length = cManageCom.length;
			      		for(var i=length;i<8;i++)
			      		{
			      			cManageCom = cManageCom +'0';
			      		}
			      		fm.AgentManageCom.value = cManageCom;
		//	      		var sql = "select name from ldcom where comcode='"+cManageCom+"'";
			      		
			      		 var sqlid26 = "WbProposalInputSql26";
					 	 var mySql26 = new SqlClass();
					 	 mySql26.setResourceName("app.WbProposalInputSql");
					 	 mySql26.setSqlId(sqlid26);//指定使用SQL的id
					 	 mySql26.addSubPara(cManageCom);//指定传入参数
					 	 var sql = mySql26.getString();
						var arrResult1 = easyExecSql(sql);	
						if(arrResult1 != null)
						{							
			     			fm.AgentManageComName.value = arrResult1[0][0];
						}						
			      	}			      	
			      }
				}
				
			}	
		}
		else
		{
			if(document.all('AgentCode').value != null && document.all('AgentCode').value != "")
			{
	//			var strSql = "select ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + fm.AgentCode.value +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
			   
				 var sqlid27 = "WbProposalInputSql27";
			 	 var mySql27 = new SqlClass();
			 	 var AgentCode27 = fm.AgentCode.value;
			 	 mySql27.setResourceName("app.WbProposalInputSql");
			 	 mySql27.setSqlId(sqlid27);//指定使用SQL的id
			 	 mySql27.addSubPara(AgentCode27);//指定传入参数
			 	 var strSql = mySql27.getString();
				 var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.AgentManageCom.value = arrResult[0][0];
			      fm.AgentManageComName.value = arrResult[0][1];
				}
			}  
		}			
	}	   
}

//查询扫描机构和代理人所属机构
function initScan()
{
	initManageCom();	
	
	//查询告知内容
	//alert(ImpartGrid.mulLineCount);	
	var i=0;
	var strSql="";
	for( ;i<ImpartGrid.mulLineCount; i++)
	{
		var impartver = ImpartGrid.getRowColData(i, 1);
		var impartcode= ImpartGrid.getRowColData(i, 2);
		//alert("impartver:"+impartver+"impartcode:"+impartcode);
		if( impartver!=null && impartver!="" && impartcode!=null && impartcode!="" )
		{
	//		strSql = "select impartcontent from ldimpart where impartver='" + impartver +"' and impartcode='" + impartcode +"'";
		  
			 var sqlid28 = "WbProposalInputSql28";
		 	 var mySql28 = new SqlClass();
		 	
		 	 mySql28.setResourceName("app.WbProposalInputSql");
		 	 mySql28.setSqlId(sqlid28);//指定使用SQL的id
		 	 mySql28.addSubPara(impartver);//指定传入参数
		 	 mySql28.addSubPara(impartcode);//指定传入参数
		 	 strSql = mySql28.getString();
			var arrResult = easyExecSql(strSql);
		    if (arrResult != null) {      
		      ImpartGrid.setRowColData(i, 3, arrResult[0][0]);
			}
		}		
	}
	    
}

//校验销售渠道与险种的关系
function checkSalechnlRiskCodeRela()
{
	
	if(document.all('RiskCode').value == "" )
	 {
	 	 alert("险种编码不能为空！");
	 	 return false;
	 }
	
	if(document.all('Salechnl').value == "" )
	 {
	 	 alert("销售渠道不能为空！");
	 	 return false;
	 }
//	var strSql="select Code from ldcode where CodeType='salechnlriskrela' and Code = '"+document.all('Salechnl').value+"'";
	
	 var sqlid29 = "WbProposalInputSql29";
	 var mySql29 = new SqlClass();
	 var Salechnl29 = document.all('Salechnl').value;
	 mySql29.setResourceName("app.WbProposalInputSql");
	 mySql29.setSqlId(sqlid29);//指定使用SQL的id
	 mySql29.addSubPara(Salechnl29);//指定传入参数
	 var strSql = mySql29.getString();
	var result = easyExecSql(strSql);
	
	if(result!=null)
	{
//    var strSubSql="select CodeType from ldcode1 where CodeType='salechnlriskrela' and Code = '"+document.all('Salechnl').value+"' and Code1='"+document.all('RiskCode').value+"'";
	
     var sqlid30 = "WbProposalInputSql30";
	 var mySql30 = new SqlClass();
	 var Salechnl30 = document.all('Salechnl').value;
	 var RiskCode30 = document.all('RiskCode').value;
	 mySql30.setResourceName("app.WbProposalInputSql");
	 mySql30.setSqlId(sqlid30);//指定使用SQL的id
	 mySql30.addSubPara(Salechnl30);//指定传入参数
	 mySql30.addSubPara(RiskCode30);//指定传入参数
	 var strSubSql = mySql30.getString();
    var tSubResult = easyExecSql(strSubSql);

    if(tSubResult == null)
     {
     	alert("销售渠道："+document.all('Salechnl').value+" 不允许销售"+document.all('RiskCode').value+"险种");
      return false;
     }
	}
	return true;
}

/*********************************************************************
 *  保存个人投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm() {
	document.all("save").disabled = true;
//	alert("document.all(save).disabled"+document.all("save").disabled);
//	alert("document.all(save).disabled"+document.all("save").src);
	if(document.all("aftersave").value==1)
	{
		return ;
	}
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var passVerify = true;
    var verifyGrade = "1";
 	
 //	var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
 	
 	 var sqlid31 = "WbProposalInputSql31";
	 var mySql31 = new SqlClass();
	 var PrtNo31 = fm.PrtNo.value;
	 mySql31.setResourceName("app.WbProposalInputSql");
	 mySql31.setSqlId(sqlid31);//指定使用SQL的id
	 mySql31.addSubPara(PrtNo31);//指定传入参数
	 var sSQL = mySql31.getString();
	var arrCount=easyExecSql(sSQL); 
 if (trim(document.all('ProposalNo').value) != "" || arrCount!=null) {
	  alert("该投保单已经保存，不允许再次保存，请重新进入录入界面！");
	  return false;
	} 
	
	//把表格中为空的记录去除
	clearRiskGridNone();	
 
	// 校验被保人是否同投保人，相同则进行复制
  //try { verifySamePerson(); } catch(e) {}
  	    	 
      	// 校验录入数据
    //if( verifyProposal() == false ) return;    
       
    for( var i=1 ;i<=3; i++)
	{
	    //var tFlag = 1;
		if(document.getElementById("DivInsuredAll"+i).style.display == "none")
		{
		    if(i==1)
		    {
		         alert("请至少录入一个被保人信息！");
		         return false;
		    }
			document.all("InsuredNum").value = i-1;
			break;
			//tFlag = 0;//没有被保人信息
		}
		else
			document.all("InsuredNum").value = i;		    
			
		//for( var j=1 ;j<=3 && tFlag == 1; j++)
		for( var j=1 ;j<=3; j++)
		{
			if(document.getElementById("divRisk"+i+j).style.display == "none")
		  	{
		  	    //alert(i);
		  	    //alert(j);
		  	    if(j==1)
			    {
			         var sI = "";
			         if(i==1) 
			         	sI = "被保人";
			         else  
			         	sI = "第"+i+"被保人";
			         	
			         alert("请" +sI+ "至少录入一个主险信息！");
			         return false;
			    }
		  		document.all("MainRiskNum"+i).value = j-1;
		  		break;
		  	}
		  	else
		  		document.all("MainRiskNum"+i).value = j;
		}
		/*if(document.all("RelationToMainInsured"+i).value==null || document.all("RelationToMainInsured"+i).value=="") 
		{
			alert("与第一被保险人关系不能为空！");
			return false;
		}	
		
		for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
		  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
		    try {
		      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1) + "1";
		       if(document.all(insuredName).value ==null || fm.elements[elementsNum].value == "")
		       
		    }
		    catch (ex) {}
		  }
		}	
	  	*/
	}
    
    /*alert(fm.InsuredNum.value);	
     alert(fm.MainRiskNum1.value);	
      alert(fm.MainRiskNum2.value);	
       alert(fm.MainRiskNum3.value);	
     return false;*/
         
    // 校验被保人同投保人的关系是否录入，相同则进行复制
    var tPrtNo = fm.PrtNo.value;
    if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) != "51"))
	{
		if(fm.AppntRelationToInsured.value==null || fm.AppntRelationToInsured.value=="")
    	{
    		alert("投保人与被保人的关系未录入！");
       		passVerify = false;
    	}
	}	
	if(tPrtNo!=null && trim(tPrtNo).length==14
	              && (tPrtNo.substring(2,4) == "51"))
	{
    	if(((DivInsuredAll1.style.display=="" && fm.RelationToAppnt1.value!=null && fm.RelationToAppnt1.value!=""))
            && ((DivInsuredAll2.style.display=="" && fm.RelationToAppnt2.value!=null && fm.RelationToAppnt2.value!="")||DivInsuredAll2.style.display=="none")
            && ((DivInsuredAll3.style.display=="" && fm.RelationToAppnt3.value!=null && fm.RelationToAppnt3.value!="")||DivInsuredAll3.style.display=="none")) 
        {        	
        }
        else
        {
        	alert("被保人与投保人的关系未录入或录入不完全！");
       		passVerify = false;
        }
	}
    /*if(fm.AppntRelationToInsured.value!=null && fm.AppntRelationToInsured.value!="")
    {}
    else if(((DivInsuredAll1.style.display=="" && fm.RelationToAppnt1.value!=null && fm.RelationToAppnt1.value!=""))
            && ((DivInsuredAll2.style.display=="" && fm.RelationToAppnt2.value!=null && fm.RelationToAppnt2.value!="")||DivInsuredAll2.style.display=="none")
            && ((DivInsuredAll3.style.display=="" && fm.RelationToAppnt3.value!=null && fm.RelationToAppnt3.value!="")||DivInsuredAll3.style.display=="none")) 
        {}
    else
       {
       		alert("投保人同被保人的关系未录入或录入不完全！");
       		passVerify = false;
       }*/
  
     //检验出生日期，如果空，从身份证号取
    try {checkBirthday(); } catch(e){}   
    //检验银行账号是否录入规范
    if(!verifyBankAccNo()) return false;
  
    if(verifyInput() == false) passVerify = false; 
    for( var i=1 ;i<=3; i++)
	{
		if(document.getElementById("DivInsuredAll"+i).style.display == "")
		{
			if(!eval("Bnf"+i+"Grid.checkValue()"))
			{
				passVerify = false;
				break;
			}			
				
		}
	}
 //   sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
     var sqlid32 = "WbProposalInputSql32";
	 var mySql32 = new SqlClass();
	 mySql32.setResourceName("app.WbProposalInputSql");
	 mySql32.setSqlId(sqlid32);//指定使用SQL的id
	 sql = mySql32.getString();
    
	var arrResult2 = easyExecSql(sql);		//alert("1136 "+arrResult2);
    if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
    {
    	if(verifyFirstTrialDate() == false) return false;
    }
    if (!passVerify) 
    {
		if (!confirm("投保单录入可能有错误，是否继续保存？")) return false;
    }
    
    if(checkInput() == false) return false;
    if(verifyRisk() == false) return false;  
    if(verifyImpart() == false) return false;  
    
		 
	document.all('fmAction').value = "INSERT";
	//alert("mAction"+mAction);
//	alert("OK 结束");
//	return;
	
	fm.action="../app/WbProposalSave.jsp"
 
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	lockScreen('lkscreen');   
	document.getElementById("fm").submit(); //提交
	document.all("aftersave").value="1";
}

/*  //增加【初审日期】的校验 --2010-03-18 --hanbin   */
function verifyFirstTrialDate() 
{
	  //alert(1);
	  if(fm.FirstTrialDate.value == null || fm.FirstTrialDate.value == "")
	  {
	  	alert("初审日期不得为空！");
	  	return false;
	  }
	  if(fm.FirstTrialDate.value < fm.PolApplyDate.value)
	  {
	  	 alert("初审日期不得早于投保申请日期！");
	  	 return false;
	  }
//	  strSql = "select makedate from es_doc_main a  where a.doccode = '"+fm.PrtNo.value+"' and a.busstype = 'TB' and a.subtype = 'UA001'";
	  var sqlid33 = "WbProposalInputSql33";
	  var mySql33 = new SqlClass();
	  var PrtNo33 = fm.PrtNo.value;
	  mySql33.setResourceName("app.WbProposalInputSql");
	  mySql33.setSqlId(sqlid33);//指定使用SQL的id
	  mySql33.addSubPara(PrtNo33);//指定传入参数
	  strSql = mySql33.getString();
	  
	  var scanDate = easyExecSql(strSql);
	  if(fm.FirstTrialDate.value >  scanDate)
	  {
	  	 alert("初审日期不得晚于扫描日期！");
	  	 return false;
	  }
}

//校验【初审日期】 -2010-03-19 - hanbin
function checkFirstTrialDate()
{
//	sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
	  var sqlid34 = "WbProposalInputSql34";
	  var mySql34 = new SqlClass();
	  mySql34.setResourceName("app.WbProposalInputSql");
	  mySql34.setSqlId(sqlid34);//指定使用SQL的id
	  sql = mySql34.getString();
	
	var arrResult2 = easyExecSql(sql);		//alert("1136 "+arrResult2);
    if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolApplyDate").value)>=0))
    {
	if(trim(fm.FirstTrialDate.value)==""){return ;}
    else if (fm.FirstTrialDate.value.length == 8)
	{
		if(fm.FirstTrialDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.FirstTrialDate.value.substring(0,4);
			var Month =    fm.FirstTrialDate.value.substring(4,6);
			var Day =      fm.FirstTrialDate.value.substring(6,8);
			fm.FirstTrialDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("您输入的投保日期有误!");
			fm.FirstTrialDate.value = "";  
			return;
			}
		}else{
			alert("您输入的投保日期格式有误!");
			fm.FirstTrialDate.value = "";  
			return;
		}
	}
	else if(fm.FirstTrialDate.value.length == 10)
	{
		var Year =     fm.FirstTrialDate.value.substring(0,4);
		var Month =    fm.FirstTrialDate.value.substring(5,7);
		var Day =      fm.FirstTrialDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的投保日期有误!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
		return;
		}
	}
	else
	{
		alert("您输入的投保日期有误!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
	}
    }

}

/*********************************************************************
 *  删除已保存个人投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetCont() {	
//	alert("document.all(save).disabled"+document.all("save").disabled);
//	alert("document.all(save).disabled"+document.all("save").src);
	
//	var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
	var sqlid35 = "WbProposalInputSql35";
	var mySql35 = new SqlClass();
	var PrtNo35 = fm.PrtNo.value;
    mySql35.setResourceName("app.WbProposalInputSql");
	mySql35.setSqlId(sqlid35);//指定使用SQL的id
	mySql35.addSubPara(PrtNo35);//指定传入参数
	var sSQL = mySql35.getString();
	
	var arrCount=easyExecSql(sSQL); 
	 if (trim(document.all('ProposalNo').value) == "" || arrCount==null) {
		  alert("该投保单尚未保存，不允许删除录入信息！");
		  return false;
		} 
//	var sSQL1 ="select 1 from lwmission where missionid='"+MissionID+"' and missionprop1='"+fm.PrtNo.value+"' and activityid in (select a.activityid  from lwactivity a where a.functionid in ( '10010016','10010017'))";
	
	var sqlid36 = "WbProposalInputSql36";
	var mySql36 = new SqlClass();
	var PrtNo36 = fm.PrtNo.value;
    mySql36.setResourceName("app.WbProposalInputSql");
	mySql36.setSqlId(sqlid36);//指定使用SQL的id
	mySql36.addSubPara(MissionID);//指定传入参数
	mySql36.addSubPara(PrtNo36);//指定传入参数
	var sSQL1 = mySql36.getString();
	var arrCount1=easyExecSql(sSQL1); 
	 if (arrCount1==null) {
		  alert("该投保单已经处理完毕，不允许删除录入信息！");
		  return false;
		}
	if(!confirm("确定要删除已保存数据，并重新保存吗？"))
			{
				return false;
			}
	document.all("fmAction").value = "DELETECONT";
    document.all("deleteCont").disabled = true;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var verifyGrade = "1"; 
	
	fm.action="../app/WbProposalSave.jsp"
 
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //提交
} 

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');
	if(fm.fmAction.value!="DELETE")
		document.all("save").disabled=false;
		
	try { showInfo.close(); } catch(e) {}
	if (FlagStr == "Fail" )
	{             
		//var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		alert("保存失败，原因是：" + content); 
		document.all("save").disabled=true; 
		if(fm.fmAction.value == "DELETECONT") 
			document.all("deleteCont").disabled = false;
		//inputQuestButton.style.display="";
	}
	else
	{ 
 
		if(loadFlag == '3'){
		//inputQuestButton.style.display="";
		}		
		  content = "处理成功！";
		  var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
		  
	    if(fm.fmAction.value == "INSERT")
	    {
	    	document.all("deleteCont").disabled=false;
	    	 //初始化投被保人客户号 add 2009-2-9 ln 
	//	 	var sSQL ="select appntno from lcappnt where contno='"+fm.ProposalNo.value+"'";
		 	
		 	var sqlid37 = "WbProposalInputSql37";
			var mySql37 = new SqlClass();
			var ProposalNo37 = fm.ProposalNo.value;
		    mySql37.setResourceName("app.WbProposalInputSql");
			mySql37.setSqlId(sqlid37);//指定使用SQL的id
			mySql37.addSubPara(ProposalNo37);//指定传入参数
			var sSQL = mySql37.getString();
		 	
			var arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{
				fm.AppntNo.value=arrInsuredAdd[0][0];
			}	
	//	    sSQL ="select insuredno from lcinsured where contno='"+fm.ProposalNo.value+"'"
	//		+ " and sequenceno in ('1','-1') ";
		    
		    var sqlid38 = "WbProposalInputSql38";
			var mySql38 = new SqlClass();
			var ProposalNo38 = fm.ProposalNo.value;
		    mySql38.setResourceName("app.WbProposalInputSql");
			mySql38.setSqlId(sqlid38);//指定使用SQL的id
			mySql38.addSubPara(ProposalNo38);//指定传入参数
		    sSQL = mySql38.getString();
		    
			var arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	    
				fm.CustomerNo1.value=arrInsuredAdd[0][0];		
			}
	
//			sSQL ="select insuredno from lcinsured where contno='"+fm.ProposalNo.value+"'"
//					+ " and sequenceno='2' ";
			
			var sqlid39 = "WbProposalInputSql39";
			var mySql39 = new SqlClass();
			var ProposalNo39 = fm.ProposalNo.value;
		    mySql39.setResourceName("app.WbProposalInputSql");
			mySql39.setSqlId(sqlid39);//指定使用SQL的id
			mySql39.addSubPara(ProposalNo39);//指定传入参数
		    sSQL = mySql39.getString();
		    
			
			//prompt('',sSQL);
			arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	    
				fm.CustomerNo2.value=arrInsuredAdd[0][0];			
			}
			
//			sSQL ="select insuredno from lcinsured where contno='"+fm.ProposalNo.value+"'"
//					+ " and sequenceno='3' ";
			
			var sqlid40 = "WbProposalInputSql40";
			var mySql40 = new SqlClass();
			var ProposalNo40 = fm.ProposalNo.value;
		    mySql40.setResourceName("app.WbProposalInputSql");
			mySql40.setSqlId(sqlid40);//指定使用SQL的id
			mySql40.addSubPara(ProposalNo40);//指定传入参数
		    sSQL = mySql40.getString();
			
			//prompt('',sSQL);
			arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	    
				fm.CustomerNo3.value=arrInsuredAdd[0][0];			
			}
			document.all("save").disabled=true;
			queryInsuredInfo();
			queryRiskInfo();
			getDealTypeInfo();
	    }
	    else if(fm.fmAction.value == "DELETECONT")
	    {
	    	document.all('ProposalNo').value ="";
	    	resetForm();	    	
	    }
					
	} 
	mAction = ""; 
}


function afterSubmitConfirm( FlagStr, content )
{
	unlockScreen('lkscreen');  
	try { showInfo.close(); } catch(e) {}
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
		document.all("aftersave").value="0";  
		//inputQuestButton.style.display="";
	}
	else
	{ 
		content = "处理成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  		
	  top.opener.unlock(prtNo);
	  top.close();
	} 

}

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick() { 
	var tPrtNo = document.all('PrtNo').value;
	
	if(tPrtNo==null || tPrtNo=="") {
		alert( "印刷号信息丢失，不能进行删除!" );
	}
	else 
	{
//		var sSQL ="select 1 from lccont where prtno='"+fm.PrtNo.value+"' ";
		
		var sqlid41 = "WbProposalInputSql41";
		var mySql41 = new SqlClass();
		var PrtNo41 = fm.PrtNo.value;
	    mySql41.setResourceName("app.WbProposalInputSql");
		mySql41.setSqlId(sqlid41);//指定使用SQL的id
		mySql41.addSubPara(PrtNo41);//指定传入参数
	    var sSQL = mySql41.getString();
		
		var arrCount=easyExecSql(sSQL); 
		if((document.all("ProposalNo").value!=null && document.all("ProposalNo").value != "")|| arrCount!=null)
	    {
	    	alert("该异常件已经保存，不能删除！");
	    	return false;
	    }
	    
		if(fm.DealType.value != "03")
		{
			alert("此保单非外包方无法处理的异常件，不能删除！");
			return false;
		}
		alert(fm.InsuredNum.value);		
		
		if(!confirm("确定要删除吗？"))
		{
			return false;
		}

		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )	
		{
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			lockScreen('lkscreen');
			document.getElementById("fm").submit(); //提交
		}
	}
}           

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
    if(document.all("ProposalNo").value!=null && document.all("ProposalNo").value != "")
    {
    	alert("该投保单已经保存，不能重置并重新保存！");
    	return false;
    }
    
	document.all("save").disabled=false;
	document.all("aftersave").value="0";
	//changeImage(document.all("save"),'../common/images/butSave.gif');
} 

/*********************************************************************
 *  初始化按钮状态
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initialSave()
{
//    var tSql = " select bussno from bpomissionstate where bussno='"+document.all("PrtNo").value+"' and State is not null and State!='0'";
   
    var sqlid42 = "WbProposalInputSql42";
	var mySql42 = new SqlClass();
	var PrtNo42 = document.all("PrtNo").value;
    mySql42.setResourceName("app.WbProposalInputSql");
	mySql42.setSqlId(sqlid42);//指定使用SQL的id
	mySql42.addSubPara(PrtNo42);//指定传入参数
    var tSql = mySql42.getString();
	
    
    var arrResult = easyExecSql( tSql );
	if (arrResult != null) 
	{
		document.all("ProposalNo").value = arrResult[0][0];
		//alert(document.all("ProposalNo").value);
		document.all("save").disabled=true;//已保存，不能重新保存
		
	} 
	else
	{
		document.all("save").disabled=false;
		document.all("aftersave").value="0";
		//changeImage(document.all("save"),'../common/images/butSave.gif');
	}
	
} 

/*********************************************************************
 *  "取消"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelForm()
{
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
}
 
/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	//if( cDebug == "1" )
		//parent.fraMain.rows = "0,0,50,82,*";
	//else 
		//parent.fraMain.rows = "0,0,80,72,*";
}

           
 /*********************************************************************
 *  Click事件，当点击“查询责任信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showDuty()
{
	//下面增加相应的代码
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "您必须先保存投保单才能查看该投保单的责任项！" );
		return false;
	}
	
	var showStr = "正在查询数据，请您稍候......";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
	//showModalDialog( "./ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var name='提示';   //网页名称，可为空; 
	var iWidth=18+"cm";      //弹出窗口的宽度; 
	var iHeight=14+"cm";     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	showInfo.close();
}           


/*********************************************************************
 *  Click事件，当点击“关联暂交费信息”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalNo.value;
	var prtNo = fm.PrtNo.value;
	
	if( cPolNo == "" )
	{
		alert( "您必须先保存投保单才能进入暂交费信息部分。" );
		return false
	}
	
	showInfo = window.open( "./ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL&prtNo=" + prtNo );
}           

/*********************************************************************
 *  Click事件，当双击“投保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppnt()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
} 

//校验投保人出生日期
function checkappntbirthday()
{
	if(fm.AppntBirthday.value.length==8)
	{
		if(fm.AppntBirthday.value.indexOf('-')==-1)
		{
			var Year =     fm.AppntBirthday.value.substring(0,4);
			var Month =    fm.AppntBirthday.value.substring(4,6);
			var Day =      fm.AppntBirthday.value.substring(6,8);
			fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("您输入的投保人出生日期有误!");
			fm.AppntBirthday.value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.AppntBirthday.value.substring(0,4);
		var Month =    fm.AppntBirthday.value.substring(5,7);
		var Day =      fm.AppntBirthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的投保人出生日期有误!");
		fm.AppntBirthday.value = "";
		return;
		}
	}
}


//校验被保人出生日期
function checkinsuredbirthday(insuredNo)
{
	if(document.all("Birthday"+insuredNo).length==8)
	{
		if(document.all("Birthday"+insuredNo).value.indexOf('-')==-1)
		{
			var Year =     document.all("Birthday"+insuredNo).value.substring(0,4);
			var Month =    document.all("Birthday"+insuredNo).value.substring(4,6);
			var Day =      document.all("Birthday"+insuredNo).value.substring(6,8);
			document.all("Birthday"+insuredNo).value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("您输入的被保人出生日期有误!");
			document.all("Birthday"+insuredNo).value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     document.all("Birthday"+insuredNo).value.substring(0,4);
		var Month =    document.all("Birthday"+insuredNo).value.substring(5,7);
		var Day =      document.all("Birthday"+insuredNo).value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的被保人出生日期有误!");
		document.all("Birthday"+insuredNo).value = "";
		return;
		}
	}
}
//投保人年龄<投保人被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge()
{
	if(fm.AppntBirthday.value=="")
	{
		return;
	}
	if(fm.AppntBirthday.value.indexOf('-')==-1)
	{
		var Year =fm.AppntBirthday.value.substring(0,4);
		var Month =fm.AppntBirthday.value.substring(4,6);
		var Day =fm.AppntBirthday.value.substring(6,8);
		fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.AppntBirthday.value)<0)
	{
		alert("出生日期只能为当前日期以前");
		fm.AppntAge.value="";
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	document.all('AppntAge').value=calAge(fm.AppntBirthday.value);
  	return ;
}
//被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge2(insuredNo)
{	
	if(document.all("Birthday"+insuredNo).value=="")
	{
		return;
	}
	if(document.all("Birthday"+insuredNo).value.indexOf('-')==-1)
	{
		var Year =     document.all("Birthday"+insuredNo).value.substring(0,4);
		var Month =    document.all("Birthday"+insuredNo).value.substring(4,6);
		var Day =      document.all("Birthday"+insuredNo).value.substring(6,8);
		document.all("Birthday"+insuredNo).value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     document.all("Birthday"+insuredNo).value.substring(0,4);
	    var Month1 =    document.all("Birthday"+insuredNo).value.substring(5,7);
	    var Day1 =      document.all("Birthday"+insuredNo).value.substring(8,10);	
	    document.all("Birthday"+insuredNo).value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(document.all("Birthday"+insuredNo).value)<0)
  	{
		alert("出生日期只能为当前日期以前");
		document.all("Age"+insuredNo).value="";
		return;
    }
    document.all('Age'+insuredNo).value=calAgeNew(document.all("Birthday"+insuredNo).value,document.all("PolApplyDate").value);
  	return ;
} 

function initialAge()
{
	//alert(1);
	if(document.all("AppntBirthday").value!=null && document.all("AppntBirthday").value!='')
		document.all('AppntAge').value=calAge(document.all("AppntBirthday").value);
	else
		document.all('AppntAge').value='0';

	for(var i=1;i<=3;i++)
	{
		if(document.all("Birthday"+i).value!=null && document.all("Birthday"+i).value!=''
			&& document.all("PolApplyDate").value!=null && document.all("PolApplyDate").value!='')
			document.all('Age'+i).value=calAgeNew(document.all("Birthday"+i).value,document.all("PolApplyDate").value);
		else
			document.all('Age'+i).value='0';
	}
			
} 

function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.AppntIDNo.value="";
	}
}    

function checkidtype2(index)
{//alert(index);
	if(document.all("IDType"+index).value==""&&document.all("IDNo"+index).value!="")
	{
		alert("请先选择证件类型！");
		document.all("IDNo"+index).value="";
	}
}      

/*********************************************************************
 *  Click事件，当双击“被保人客户号”录入框时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsured()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonMain.html" );
	}
}           

    

/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPol(cArr) 
{
	try 
	{
	    try { document.all('PrtNo').value = cArr[6]; } catch(ex) { };
	    try { document.all('ManageCom').value = cArr[12]; } catch(ex) { };
	    try { document.all('SaleChnl').value = cArr[15]; } catch(ex) { };
	    try { document.all('AgentCom').value = cArr[13]; } catch(ex) { };
	    try { document.all('AgentType').value = cArr[14]; } catch(ex) { };
	    try { document.all('AgentCode').value = cArr[87]; } catch(ex) { };
	    try { document.all('AgentGroup').value = cArr[88]; } catch(ex) { };
	    //try { document.all('Handler').value = cArr[82]; } catch(ex) { };
	    //try { document.all('AgentCode1').value = cArr[89]; } catch(ex) { };
	    try { document.all('Remark').value = cArr[90]; } catch(ex) { };
	
	    try { document.all('ContNo').value = cArr[1]; } catch(ex) { };
	    try { document.all('Mult').value = cArr[38]; } catch(ex) { };
	    
	    //try { document.all('Amnt').value = cArr[43]; } catch(ex) { };
	    try { document.all('CValiDate').value = cArr[29]; } catch(ex) { };
	    try { document.all('PolApplyDate').value = cArr[128]; } catch(ex) { };
	    try { document.all('HealthCheckFlag').value = cArr[72]; } catch(ex) { };
	    try { document.all('OutPayFlag').value = cArr[97]; } catch(ex) { };
	    try { document.all('PayLocation').value = cArr[59]; } catch(ex) { };
	    try { document.all('PayMode').value = cArr[58]; } catch(ex) { };
	    try { document.all('BankCode').value = cArr[102]; } catch(ex) { };
	    try { document.all('BankAccNo').value = cArr[103]; } catch(ex) { };
	    try { document.all('AccName').value = cArr[118]; } catch(ex) { };
	    try { document.all('LiveGetMode').value = cArr[98]; } catch(ex) { };
	    try { document.all('BonusGetMode').value = cArr[100]; } catch(ex) { };
	    try { document.all('AutoPayFlag').value = cArr[65]; } catch(ex) { };
	    try { document.all('InterestDifFlag').value = cArr[66]; } catch(ex) { };
     
	    try { document.all('InsuYear').value = cArr[111]; } catch(ex) { };
	    try { document.all('InsuYearFlag').value = cArr[110]; } catch(ex) { };
	    try { document.all('PolTypeFlag').value = cArr[69]; } catch(ex) { };
	    try { document.all('InsuredPeoples').value = cArr[24]; } catch(ex) { };
	    try { document.all('InsuredAppAge').value = cArr[22]; } catch(ex) { };
	    
	    try { document.all('PayIntv').value = cArr[57]; } catch(ex) { };

	    try { document.all('PayEndYear').value = cArr[109]; } catch(ex) { };
	    try { document.all('PayEndYearFlag').value = cArr[108]; } catch(ex) { };	   

	    try { document.all('StandbyFlag1').value = cArr[78]; } catch(ex) { };
	    try { document.all('StandbyFlag2').value = cArr[79]; } catch(ex) { };
	    try { document.all('StandbyFlag3').value = cArr[80]; } catch(ex) { };
	    
	} catch(ex) {
	  alert("displayPol err:" + ex + "\ndata is:" + cArr);
	}
}

/*********************************************************************
 *  把保单中的投保人信息显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolAppnt(cArr) 
{
	// 从LCAppntInd表取数据
	try { document.all('AppntCustomerNo').value = cArr[1]; } catch(ex) { };
	try { document.all('AppntRelationToInsured').value = cArr[4]; } catch(ex) { };
	try { document.all('AppntPassword').value = cArr[5]; } catch(ex) { };
	try { document.all('AppntName').value = cArr[6]; } catch(ex) { };
	try { document.all('AppntSex').value = cArr[7]; } catch(ex) { };
	try { document.all('AppntBirthday').value = cArr[8]; } catch(ex) { };
	try { document.all('AppntNativePlace').value = cArr[9]; } catch(ex) { };
	try { document.all('AppntNationality').value = cArr[10]; } catch(ex) { };
	try { document.all('AppntMarriage').value = cArr[11]; } catch(ex) { };
	try { document.all('AppntMarriageDate').value = cArr[12]; } catch(ex) { };
	try { document.all('AppntOccupationType').value = cArr[13]; } catch(ex) { };
	try { document.all('AppntStartWorkDate').value = cArr[14]; } catch(ex) { };
	try { document.all('AppntSalary').value = cArr[15]; } catch(ex) { };
	try { document.all('AppntHealth').value = cArr[16]; } catch(ex) { };
	try { document.all('AppntStature').value = cArr[17]; } catch(ex) { };
	try { document.all('AppntAvoirdupois').value = cArr[18]; } catch(ex) { };
	try { document.all('AppntCreditGrade').value = cArr[19]; } catch(ex) { };
	try { document.all('AppntIDType').value = cArr[20]; } catch(ex) { };
	try { document.all('AppntProterty').value = cArr[21]; } catch(ex) { };
	try { document.all('AppntIDNo').value = cArr[22]; } catch(ex) { };
	try { document.all('AppntOthIDType').value = cArr[23]; } catch(ex) { };
	try { document.all('AppntOthIDNo').value = cArr[24]; } catch(ex) { };
	try { document.all('AppntICNo').value = cArr[25]; } catch(ex) { };
	try { document.all('AppntHomeAddressCode').value = cArr[26]; } catch(ex) { };
	try { document.all('AppntHomeAddress').value = cArr[27]; } catch(ex) { };
	try { document.all('AppntPostalAddress').value = cArr[28]; } catch(ex) { };
	try { document.all('AppntZipCode').value = cArr[29]; } catch(ex) { };
	try { document.all('AppntPhone').value = cArr[30]; } catch(ex) { };
	try { document.all('AppntBP').value = cArr[31]; } catch(ex) { };
	try { document.all('AppntMobile').value = cArr[32]; } catch(ex) { };
	try { document.all('AppntEMail').value = cArr[33]; } catch(ex) { };
	try { document.all('AppntJoinCompanyDate').value = cArr[34]; } catch(ex) { };
	try { document.all('AppntPosition').value = cArr[35]; } catch(ex) { };
	try { document.all('AppntGrpNo').value = cArr[36]; } catch(ex) { };
	try { document.all('AppntGrpName').value = cArr[37]; } catch(ex) { };
	try { document.all('AppntGrpPhone').value = cArr[38]; } catch(ex) { };
	try { document.all('AppntGrpAddressCode').value = cArr[39]; } catch(ex) { };
	try { document.all('AppntGrpAddress').value = cArr[40]; } catch(ex) { };
	try { document.all('AppntDeathDate').value = cArr[41]; } catch(ex) { };
	try { document.all('AppntRemark').value = cArr[42]; } catch(ex) { };
	try { document.all('AppntState').value = cArr[43]; } catch(ex) { };
	try { document.all('AppntWorkType').value = cArr[46]; } catch(ex) { };
	try { document.all('AppntPluralityType').value = cArr[47]; } catch(ex) { };
	try { document.all('AppntOccupationCode').value = cArr[48]; } catch(ex) { };
	try { document.all('AppntDegree').value = cArr[49]; } catch(ex) { };
	try { document.all('AppntGrpZipCode').value = cArr[50]; } catch(ex) { };
	try { document.all('AppntSmokeFlag').value = cArr[51]; } catch(ex) { };
	try { document.all('AppntRgtAddress').value = cArr[52]; } catch(ex) { };
	try { document.all('AppntHomeZipCode').value = cArr[53]; } catch(ex) { };
	try { document.all('AppntPhone2').value = cArr[54]; } catch(ex) { };

}

/*********************************************************************
 *  把保单中的投保人数据显示到投保人部分
 *  参数  ：  集体客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{
	// 从LCAppntGrp表取数据
	try { document.all('AppntPolNo').value = cArr[0]; } catch(ex) { };
	try { document.all('AppntGrpNo').value = cArr[1]; } catch(ex) { };
	try { document.all('AppntRelationToInsured').value = cArr[2]; } catch(ex) { };
	try { document.all('AppntAppntGrade').value = cArr[3]; } catch(ex) { };
	try { document.all('AppntPassword').value = cArr[4]; } catch(ex) { };
	try { document.all('AppntGrpName').value = cArr[5]; } catch(ex) { };
	try { document.all('AppntGrpAddressCode').value = cArr[6]; } catch(ex) { };
	try { document.all('AppntGrpAddress').value = cArr[7]; } catch(ex) { };
	try { document.all('AppntGrpZipCode').value = cArr[8]; } catch(ex) { };
	try { document.all('AppntBusinessType').value = cArr[9]; } catch(ex) { };
	try { document.all('AppntGrpNature').value = cArr[10]; } catch(ex) { };
	try { document.all('AppntPeoples').value = cArr[11]; } catch(ex) { };
	try { document.all('AppntRgtMoney').value = cArr[12]; } catch(ex) { };
	try { document.all('AppntAsset').value = cArr[13]; } catch(ex) { };
	try { document.all('AppntNetProfitRate').value = cArr[14]; } catch(ex) { };
	try { document.all('AppntMainBussiness').value = cArr[15]; } catch(ex) { };
	try { document.all('AppntCorporation').value = cArr[16]; } catch(ex) { };
	try { document.all('AppntComAera').value = cArr[17]; } catch(ex) { };
	try { document.all('AppntLinkMan1').value = cArr[18]; } catch(ex) { };
	try { document.all('AppntDepartment1').value = cArr[19]; } catch(ex) { };
	try { document.all('AppntHeadShip1').value = cArr[20]; } catch(ex) { };
	try { document.all('AppntPhone1').value = cArr[21]; } catch(ex) { };
	try { document.all('AppntE_Mail1').value = cArr[22]; } catch(ex) { };
	try { document.all('AppntFax1').value = cArr[23]; } catch(ex) { };
	try { document.all('AppntLinkMan2').value = cArr[24]; } catch(ex) { };
	try { document.all('AppntDepartment2').value = cArr[25]; } catch(ex) { };
	try { document.all('AppntHeadShip2').value = cArr[26]; } catch(ex) { };
	try { document.all('AppntPhone2').value = cArr[27]; } catch(ex) { };
	try { document.all('AppntE_Mail2').value = cArr[28]; } catch(ex) { };
	try { document.all('AppntFax2').value = cArr[29]; } catch(ex) { };
	try { document.all('AppntFax').value = cArr[30]; } catch(ex) { };
	try { document.all('AppntPhone').value = cArr[31]; } catch(ex) { };
	try { document.all('AppntGetFlag').value = cArr[32]; } catch(ex) { };
	try { document.all('AppntSatrap').value = cArr[33]; } catch(ex) { };
	try { document.all('AppntEMail').value = cArr[34]; } catch(ex) { };
	try { document.all('AppntFoundDate').value = cArr[35]; } catch(ex) { };
	try { document.all('AppntBankAccNo').value = cArr[36]; } catch(ex) { };
	try { document.all('AppntBankCode').value = cArr[37]; } catch(ex) { };
	try { document.all('AppntGrpGroupNo').value = cArr[38]; } catch(ex) { };
	try { document.all('AppntState').value = cArr[39]; } catch(ex) { };
	try { document.all('AppntRemark').value = cArr[40]; } catch(ex) { };
	try { document.all('AppntBlacklistFlag').value = cArr[41]; } catch(ex) { };
	try { document.all('AppntOperator').value = cArr[42]; } catch(ex) { };
	try { document.all('AppntMakeDate').value = cArr[43]; } catch(ex) { };
	try { document.all('AppntMakeTime').value = cArr[44]; } catch(ex) { };
	try { document.all('AppntModifyDate').value = cArr[45]; } catch(ex) { };
	try { document.all('AppntModifyTime').value = cArr[46]; } catch(ex) { };
	try { document.all('AppntFIELDNUM').value = cArr[47]; } catch(ex) { };
	try { document.all('AppntPK').value = cArr[48]; } catch(ex) { };
	try { document.all('AppntfDate').value = cArr[49]; } catch(ex) { };
	try { document.all('AppntmErrors').value = cArr[50]; } catch(ex) { };
}

/*********************************************************************
 *  把保单中的被保人数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayPolInsured(cArr) 
{
	// 从LCInsured表取数据
	try { document.all('CustomerNo').value = cArr[1]; } catch(ex) { };
	try { document.all('SequenceNo').value = cArr[2]; } catch(ex) { };
	try { document.all('InsuredGrade').value = cArr[3]; } catch(ex) { };
	try { document.all('RelationToInsured').value = cArr[4]; } catch(ex) { };
	try { document.all('Password').value = cArr[5]; } catch(ex) { };
	try { document.all('Name').value = cArr[6]; } catch(ex) { };
	try { document.all('Sex').value = cArr[7]; } catch(ex) { };
	try { document.all('Birthday').value = cArr[8]; } catch(ex) { };
	try { document.all('NativePlace').value = cArr[9]; } catch(ex) { };
	try { document.all('Nationality').value = cArr[10]; } catch(ex) { };
	try { document.all('Marriage').value = cArr[11]; } catch(ex) { };
	try { document.all('MarriageDate').value = cArr[12]; } catch(ex) { };
	try { document.all('OccupationType').value = cArr[13]; } catch(ex) { };
	try { document.all('StartWorkDate').value = cArr[14]; } catch(ex) { };
	try { document.all('Salary').value = cArr[15]; } catch(ex) { };
	try { document.all('Health').value = cArr[16]; } catch(ex) { };
	try { document.all('Stature').value = cArr[17]; } catch(ex) { };
	try { document.all('Avoirdupois').value = cArr[18]; } catch(ex) { };
	try { document.all('CreditGrade').value = cArr[19]; } catch(ex) { };
	try { document.all('IDType').value = cArr[20]; } catch(ex) { };
	try { document.all('Proterty').value = cArr[21]; } catch(ex) { };
	try { document.all('IDNo').value = cArr[22]; } catch(ex) { };
	try { document.all('OthIDType').value = cArr[23]; } catch(ex) { };
	try { document.all('OthIDNo').value = cArr[24]; } catch(ex) { };
	try { document.all('ICNo').value = cArr[25]; } catch(ex) { };
	try { document.all('HomeAddressCode').value = cArr[26]; } catch(ex) { };
	try { document.all('HomeAddress').value = cArr[27]; } catch(ex) { };
	try { document.all('PostalAddress').value = cArr[28]; } catch(ex) { };
	try { document.all('ZipCode').value = cArr[29]; } catch(ex) { };
	try { document.all('Phone').value = cArr[30]; } catch(ex) { };
	try { document.all('BP').value = cArr[31]; } catch(ex) { };
	try { document.all('Mobile').value = cArr[32]; } catch(ex) { };
	try { document.all('EMail').value = cArr[33]; } catch(ex) { };
	try { document.all('JoinCompanyDate').value = cArr[34]; } catch(ex) { };
	try { document.all('Position').value = cArr[35]; } catch(ex) { };
	try { document.all('GrpNo').value = cArr[36]; } catch(ex) { };
	try { document.all('GrpName').value = cArr[37]; } catch(ex) { };
	try { document.all('GrpPhone').value = cArr[38]; } catch(ex) { };
	try { document.all('GrpAddressCode').value = cArr[39]; } catch(ex) { };
	try { document.all('GrpAddress').value = cArr[40]; } catch(ex) { };
	try { document.all('DeathDate').value = cArr[41]; } catch(ex) { };
	try { document.all('State').value = cArr[43]; } catch(ex) { };
	try { document.all('WorkType').value = cArr[46]; } catch(ex) { };
	try { document.all('PluralityType').value = cArr[47]; } catch(ex) { };
	try { document.all('OccupationCode').value = cArr[48]; } catch(ex) { };
	try { document.all('Degree').value = cArr[49]; } catch(ex) { };
	try { document.all('GrpZipCode').value = cArr[50]; } catch(ex) { };
	try { document.all('SmokeFlag').value = cArr[51]; } catch(ex) { };
	try { document.all('RgtAddress').value = cArr[52]; } catch(ex) { };
	try { document.all('HomeZipCode').value = cArr[53]; } catch(ex) { };
	try { document.all('Phone2').value = cArr[54]; } catch(ex) { };
	return;

}
 

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 )	{           // 查询保单明细
			var tPolNo = arrResult[0][0];
			
			// 查询保单明细
			queryPolDetail( tPolNo );
		}
		
		if( mOperate == 2 ) {		// 投保人信息	  
			var sqlid43 = "WbProposalInputSql43";
			var mySql43 = new SqlClass();
		    mySql43.setResourceName("app.WbProposalInputSql");
			mySql43.setSqlId(sqlid43);//指定使用SQL的id
			mySql43.addSubPara(arrQueryResult[0][0]);//指定传入参数
		    var sql43 = mySql43.getString();
	//		arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(sql43, 1, 0);
			if (arrResult == null) {
			  alert("未查到投保人信息");
			} else {
			   displayAppnt(arrResult[0]);
			}

	  }
	  for( var i=1; i<=3; i++ )
		if( mOperate == "3"+i )	{		// 主被保人信息
			var sqlid44 = "WbProposalInputSql44";
			var mySql44 = new SqlClass();
		    mySql44.setResourceName("app.WbProposalInputSql");
			mySql44.setSqlId(sqlid44);//指定使用SQL的id
			mySql44.addSubPara(arrQueryResult[0][0]);//指定传入参数
		    var sql44 = mySql44.getString();
	//	    arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(sql44, 1, 0);
			if (arrResult == null) {
			  alert("未查到主被保人信息");
			} else {
			   displayInsured(arrResult[0],i);
			}
			
			break;

	  }
		if( mOperate == 4 )	{		// 连带被保人信息
			displaySubInsured(arrResult[0]);
	  }
	}
	mOperate = 0;		// 恢复初态
	
	emptyUndefined(); 
}

function afterCodeSelect( cCodeName, Field )
{
 	//alert("cCodeName==="+cCodeName);
	 //自动填写受益人信息
	  var insurNo;
      if (cCodeName == "customertype1") {
        insurNo = 1;
        if (Field.value == "0") {
          //alert("1111111");          
          var index = Bnf1Grid.mulLineCount;
          Bnf1Grid.setRowColData(index-1, 4, document.all("AppntName").value);
          Bnf1Grid.setRowColData(index-1, 5, document.all("AppntIDType").value);
          Bnf1Grid.setRowColData(index-1, 6, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //修改受益人关系问题
          Bnf1Grid.setRowColData(index-1, 7,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          Bnf1Grid.setRowColData(index-1, 10, document.all("AppntHomeAddress").value);
          Bnf1Grid.setRowColData(index-1, 11, document.all("AppntIDEndDate").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = Bnf1Grid.mulLineCount;
        Bnf1Grid.setRowColData(index-1, 4, document.all("Name" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 5, document.all("IDType" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 6, document.all("IDNo" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 7, "00");
        Bnf1Grid.setRowColData(index-1, 10, document.all("HomeAddress" +insurNo).value);
        Bnf1Grid.setRowColData(index-1, 11, document.all("IDEndDate" +insurNo).value);
				//tongmeng 2007-12-28 add
					//身故受益人不能为本人
		var tempDeadBnf = Bnf1Grid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//默认为生存受益人
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('身故受益人不能为本人!');
          	//alert("3333333");
          	Bnf1Grid.setRowColData(index-1, 3, '');
          	Bnf1Grid.setRowColData(index-1, 4, '');
          	Bnf1Grid.setRowColData(index-1, 5, '');
          	Bnf1Grid.setRowColData(index-1, 6, '');
          	Bnf1Grid.setRowColData(index-1, 7, '');
          	Bnf1Grid.setRowColData(index-1, 10, '');
          	Bnf1Grid.setRowColData(index-1, 11, '');
          	return ;
          }
        }
        return;
    }
    else if (cCodeName == "customertype2") {
        insurNo = 2;
        if (Field.value == "0") {
          //alert("1111111");
          var index = Bnf2Grid.mulLineCount;
          Bnf2Grid.setRowColData(index-1, 4, document.all("AppntName").value);
          Bnf2Grid.setRowColData(index-1, 5, document.all("AppntIDType").value);
          Bnf2Grid.setRowColData(index-1, 6, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //修改受益人关系问题
          Bnf2Grid.setRowColData(index-1, 7,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          Bnf2Grid.setRowColData(index-1, 10, document.all("AppntHomeAddress").value);
          Bnf2Grid.setRowColData(index-1, 11, document.all("AppntIDEndDate").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = Bnf2Grid.mulLineCount;
        Bnf2Grid.setRowColData(index-1, 4, document.all("Name" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 5, document.all("IDType" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 6, document.all("IDNo" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 7, "00");
        Bnf2Grid.setRowColData(index-1, 10, document.all("HomeAddress" +insurNo).value);
        Bnf2Grid.setRowColData(index-1, 11, document.all("IDEndDate" +insurNo).value);
				//tongmeng 2007-12-28 add
					//身故受益人不能为本人
		var tempDeadBnf = Bnf2Grid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//默认为生存受益人
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('身故受益人不能为本人!');
          	//alert("3333333");
          	Bnf2Grid.setRowColData(index-1, 3, '');
          	Bnf2Grid.setRowColData(index-1, 4, '');
          	Bnf2Grid.setRowColData(index-1, 5, '');
          	Bnf2Grid.setRowColData(index-1, 6, '');
          	Bnf2Grid.setRowColData(index-1, 7, '');
          	Bnf2Grid.setRowColData(index-1, 10, '');
          	Bnf2Grid.setRowColData(index-1, 11, '');
          	return ;
          }
        }
        return;
    }
    else if (cCodeName == "customertype3") {
        insurNo = 3;
        if (Field.value == "0") {
          //alert("1111111");
          var index = Bnf3Grid.mulLineCount;
          Bnf3Grid.setRowColData(index-1, 4, document.all("AppntName").value);
          Bnf3Grid.setRowColData(index-1, 5, document.all("AppntIDType").value);
          Bnf3Grid.setRowColData(index-1, 6, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //修改受益人关系问题
          Bnf3Grid.setRowColData(index-1, 7,parent.VD.gVSwitch.getVar( "RelationToInsured"));
          Bnf3Grid.setRowColData(index-1, 10, document.all("AppntHomeAddress").value);
          Bnf3Grid.setRowColData(index-1, 11, document.all("AppntIDEndDate").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = Bnf3Grid.mulLineCount;
        Bnf3Grid.setRowColData(index-1, 4, document.all("Name" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 5, document.all("IDType" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 6, document.all("IDNo" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 7, "00");
        Bnf3Grid.setRowColData(index-1, 10, document.all("HomeAddress" +insurNo).value);
        Bnf3Grid.setRowColData(index-1, 11, document.all("IDEndDate" +insurNo).value);
				//tongmeng 2007-12-28 add
					//身故受益人不能为本人
		var tempDeadBnf = Bnf3Grid.getRowColData(index-1, 1);
          if(tempDeadBnf==null||trim(tempDeadBnf)=='')
          {
          	//默认为生存受益人
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('身故受益人不能为本人!');
          	//alert("3333333");
          	Bnf3Grid.setRowColData(index-1, 3, '');
          	Bnf3Grid.setRowColData(index-1, 4, '');
          	Bnf3Grid.setRowColData(index-1, 5, '');
          	Bnf3Grid.setRowColData(index-1, 6, '');
          	Bnf3Grid.setRowColData(index-1, 7, '');
          	Bnf3Grid.setRowColData(index-1, 10, '');
          	return ;
          }
        }
        return;
    }
    if (cCodeName == "AgentCom" || cCodeName == "salechnl") {
    	initManageCom();
    }
}

/*********************************************************************
 *  根据查询返回的信息查询投保单明细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryPolDetail()
{

	emptyForm(); 
	//var showStr = "正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	//parent.fraSubmit.window.location = "./ProposalQueryDetail.jsp?PolNo=" + cPolNo;
	//alert(BussNoType);
	parent.fraTitle.window.location = "./WbProposalQueryDetail.jsp?PrtNo=" + prtNo +"&BussNoType=" + BussNoType;
    //document.all('Name2').value = '被保人二';
    document.all('BussNoType').value = BussNoType;
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();  
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//*************************************************************
//被保人客户号查询按扭事件
function queryInsuredNo(i) {
  if (document.all("CustomerNo"+i).value == "") {
    showInsured1(i);
  //} else if (loadFlag != "1" && loadFlag != "2") {
  //  alert("只能在投保单录入时进行操作！");
  }  else {
	    var sqlid45 = "WbProposalInputSql45";
		var mySql45 = new SqlClass();
		var CustomerNo45 = document.all("CustomerNo"+i).value;
	    mySql45.setResourceName("app.WbProposalInputSql");
		mySql45.setSqlId(sqlid45);//指定使用SQL的id
		mySql45.addSubPara(CustomerNo45);//指定传入参数
	    var sql45 = mySql45.getString();
  //  arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + document.all("CustomerNo"+i).value + "'", 1, 0);
       arrResult = easyExecSql(sql45, 1, 0);
    if (arrResult == null) {
      alert("未查到主被保人信息");
      displayInsured(new Array(),i);
      emptyUndefined(); 
    } else {
      displayInsured(arrResult[0],i);
      getAge2(i);
    }
  }
}

//*************************************************************
//投保人客户号查询按扭事件
function queryAppntNo() {
  if (document.all("AppntNo").value == "" ) {
    showAppnt1();
  //} else if (loadFlag != "1" && loadFlag != "2") {
  //  alert("只能在投保单录入时进行操作！");
  } else {
	   var sqlid46 = "WbProposalInputSql46";
		var mySql46 = new SqlClass();
		var AppntNo46 = document.all("AppntNo").value;
	    mySql46.setResourceName("app.WbProposalInputSql");
		mySql46.setSqlId(sqlid46);//指定使用SQL的id
		mySql46.addSubPara(AppntNo46);//指定传入参数
	    var sql46 = mySql46.getString();
 //   arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
      arrResult = easyExecSql(sql46, 1, 0);
    if (arrResult == null) {
      alert("未查到投保人信息");
      displayAppnt(new Array());
      emptyUndefined(); 
    } else {
      displayAppnt(arrResult[0]);
      getAge();
    }
  }
}

//*************************************************************
//投保人与被保人相同选择框事件
function isSamePerson() {  
  //对应不选同一人的情况
  if (fm.SamePersonFlag1.checked == false) {
    document.all('DivInsuredBase1').style.display = ""; 
    //document.all('RelationToMainInsured1').readonly = false;
    //document.all('RelationToAppnt1').readonly = false; 
  } 
  else 
  {
  	if ((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value=="00") || (fm.RelationToAppnt1.value!="" && fm.RelationToAppnt1.value=="00"))//对应是同一人，又打钩的情况 
	  {
	  	document.all('DivInsuredBase1').style.display = "none";
	  	fm.RelationToAppnt1.value="00";
	  	fm.AppntRelationToInsured.value="00";
	  }
	  else //对应未选同一人，又打钩的情况
	  {
	    document.all('DivInsuredBase1').style.display = "";
	    fm.SamePersonFlag1.checked = false;
	    alert("投保人与被保人关系不是本人，不能进行该操作！");
	  }
   }   
  
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1) + "1";
	      if (document.all('DivInsuredBase1').style.display == "none") {
	        //alert(insuredName);
	        //alert(fm.elements[elementsNum].value);
	        document.all(insuredName).value = fm.elements[elementsNum].value;
	        document.all('Age1').value=calAgeNew(document.all("Birthday1").value,document.all("PolApplyDate").value);
	      }
	      else {
	        document.all(insuredName).value = "";
	      }
	    }
	    catch (ex) {}
	  }
	}

} 

//*************************************************************
//首续期账号一致选择框事件
function theSameToFirstAccount() {
  //打钩的情况
  if (fm.theSameAccount.checked == true) {
    document.all('BankCode').value = document.all('NewBankCode').value;
    document.all('AccName').value = document.all('NewAccName').value;
    document.all('BankAccNo').value = document.all('NewBankAccNo').value;
  }
  //对应不选的情况
  else if (fm.theSameAccount.checked == false) {
    document.all('BankCode').value = '';
    document.all('AccName').value = '';
    document.all('BankAccNo').value = '';
  }  

} 

//*************************************************************
//投保人数据清空事件
function initDeleteAppnt() {
  var appntName;
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {  
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      appntName = fm.elements[elementsNum].name;	        //alert(appntName);
	        //alert(insuredName);
	        document.all(appntName).value = "";
		    }
		    catch (ex) {}
	    
	}
  }

} 

//*************************************************************
//被保人数据清空事件
function initDeleteInsured(sInsuNum) {

  //alert("initDeleteInsured");
  //document.all("RelationToMainInsured"+sInsuNum).value = "";
  document.all("RelationToAppnt"+sInsuNum).value = ""; 
  //document.all("RelationToMainInsuredName"+sInsuNum).value = "";
  document.all("RelationToAppntName"+sInsuNum).value = "";
  
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {  
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1) + sInsuNum;	        //alert(insuredName);
	        //alert(insuredName);
	        document.all(insuredName).value = "";
		    }
		    catch (ex) {}
	    
	}
  }

} 

//*************************************************************
//保存时校验投保人与被保人相同选择框事件
function verifySamePerson() {
  if (fm.SamePersonFlag.checked == true) {
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
  	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
  	    try {
  	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
  	      if (document.all('DivLCInsured').style.display == "none") {
  	        document.all(insuredName).value = fm.elements[elementsNum].value;
  	      }
  	      else {
  	        document.all(insuredName).value = "";
  	      }
  	    }
  	    catch (ex) {}
  	  }
	  } 
  }
  else if (fm.SamePersonFlag.checked == false) { 

  }  
	
}  


/*********************************************************************
 *  把数组中的数据显示到投保人部分
 *  参数  ：  个人客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt(cArr) 
{
    //清空投保人信息
    //initDeleteAppnt();//--不用请清空
	// 从LDPerson表取数据
	try { document.all('AppntNo').value = cArr[0]; } catch(ex) { };
	try { document.all('AppntName').value = cArr[1]; } catch(ex) { };
	try { document.all('AppntSex').value = cArr[2]; } catch(ex) { };
	try { document.all('AppntBirthday').value = cArr[3]; } catch(ex) { };
	try { document.all('AppntIDType').value = cArr[4]; } catch(ex) { };
	try { document.all('AppntIDNo').value = cArr[5]; } catch(ex) { };
	try { document.all('AppntRelationToInsured').value = ''; } catch(ex) { };
	try { document.all('AppntAge').value=calAge(cArr[3]) } catch(ex) { };
	try { document.all('AppntNativePlace').value = cArr[7]; } catch(ex) { };
	try { document.all('AppntRgtAddress').value = cArr[9]; } catch(ex) { };
	try { document.all('AppntMarriage').value = cArr[10]; } catch(ex) { };
	try { document.all('AppntPostalAddress').value = ''; } catch(ex) { };
	try { document.all('AppntZipCode').value = ''; } catch(ex) { };
	try { document.all('AppntHomeAddress').value = ''; } catch(ex) { };
	try { document.all('AppntHomeZipCode').value = ''; } catch(ex) { };	
	try { document.all('AppntPhone').value = ''; } catch(ex) { };
	try { document.all('AppntPhone2').value = ''; } catch(ex) { };
	try { document.all('AppntEMail').value = ''; } catch(ex) { };
	try { document.all('AppntGrpName').value = cArr[41]; } catch(ex) { };
	try { document.all('AppntWorkType').value = cArr[27]; } catch(ex) { };
	try { document.all('AppntPluralityType').value = cArr[28]; } catch(ex) { };
	try { document.all('AppntOccupationCode').value = cArr[26]; } catch(ex) { };	
	try { document.all('AppntOccupationType').value = cArr[25]; } catch(ex) { };
	
	//try { document.all('AppntDegree').value = cArr[51]; } catch(ex) { };
	//try { document.all('AppntGrpZipCode').value = cArr[52]; } catch(ex) { };
	//try { document.all('AppntSmokeFlag').value = cArr[53]; } catch(ex) { };
	//try { document.all('AppntRgtAddress').value = cArr[54]; } catch(ex) { };

}
 

/*********************************************************************
 *  把查询返回的客户数据显示到被保人部分
 *  参数  ：  客户的信息
 *  返回值：  无
 *********************************************************************
 */
function displayInsured(cArr,i) 
{
    //清空被保人信息
    //initDeleteInsured(i); //--不用清空
	// 从LDPerson表取数据
	try { document.all('CustomerNo'+i).value = cArr[0]; } catch(ex) { };
	try { document.all('Name'+i).value = cArr[1]; } catch(ex) { };
	try { document.all('Sex'+i).value = cArr[2]; } catch(ex) { };
	try { document.all('Birthday'+i).value = cArr[3]; } catch(ex) { };
	try { document.all('IDType'+i).value = cArr[4]; } catch(ex) { };
	try { document.all('IDNo'+i).value = cArr[5]; } catch(ex) { };
	//try { document.all('RelationToMainInsured'+i).value = ''; } catch(ex) { };
	try { document.all('RelationToAppnt'+i).value = ''; } catch(ex) { };
	try { document.all('Age'+i).value=calAge(cArr[3]) } catch(ex) { };
	try { document.all('NativePlace'+i).value = cArr[7]; } catch(ex) { };
	try { document.all('RgtAddress'+i).value = cArr[9]; } catch(ex) { };
	try { document.all('Marriage'+i).value = cArr[10]; } catch(ex) { };
	try { document.all('HomeAddress'+i).value = ''; } catch(ex) { };
	try { document.all('HomeZipCode'+i).value = ''; } catch(ex) { };	
	try { document.all('Phone'+i).value = ''; } catch(ex) { };
	try { document.all('Phone2'+i).value = ''; } catch(ex) { };
	try { document.all('GrpName'+i).value = cArr[41]; } catch(ex) { };
	try { document.all('WorkType'+i).value = cArr[27]; } catch(ex) { };
	try { document.all('PluralityType'+i).value = cArr[28]; } catch(ex) { };
	try { document.all('OccupationCode'+i).value = cArr[26]; } catch(ex) { };	
	try { document.all('OccupationType'+i).value = cArr[25]; } catch(ex) { };
	
}

//*********************************************************************
function showAppnt1()
{
//alert(mOperate);
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}           

//*********************************************************************
function showInsured1(i)
{
	if( mOperate == 0 )
	{
		mOperate = "3"+i;
		//alert(mOperate);
		showInfo = window.open( "../sys/LDPersonQuery.html" );
	}
}  

function isSamePersonQuery(SequenceNo) {
  if(SequenceNo == 1)
  { 	  
  	  fm.SamePersonFlag1.checked = true;
	  document.all("divSamePerson1").style.display = "";
	  document.all("DivInsuredBase1").style.display = "none";
	  //document.all('RelationToMainInsured1').readonly = true;
      //document.all('RelationToAppnt1').readonly = true;
	  document.getElementById("img11").src="../common/images/butCollapse.gif";
  }  
}

//问题件录入
function QuestInput()
{
	cProposalNo = fm.ProposalNo.value;  //保单号码
	
	if(cProposalNo == "")
	{
		alert("尚无投保单号，请先保存!");
	}
	else
	
	{	
		window.open("../uw/MSQuestInputMain.jsp?ContNo="+prtNo+"&Flag=0&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID,"window1");
		//window.open("../uw/QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");
	}
}

//显示投保人年龄
function showAppntAge() {
  var age = calAge(document.all("AppntBirthday").value);
  var today = new Date();
  
  document.all("AppntBirthday").title = "投保人到今天 " + today.toLocaleString() 
                                + " \n的年龄为：" + age + " 岁!";
}

//显示被保人年龄
function showAge() {
  var age = calAge(document.all("Birthday").value);
  var today = new Date();
  
  document.all("Birthday").title = "被保人到今天 " + today.toLocaleString() 
                           + " \n的年龄为：" + age + " 岁!";
}

//检验投保人出生日期，如果空，且身份证号有，则从身份证取。取不到返回空格;
function checkBirthday()
{
	try{
		  var strBrithday = "";
		  if(trim(document.all("AppntBirthday").value)==""||document.all("AppntBirthday").value==null)
		  {
		  	if (trim(document.all("AppntIDType").value) == "0") 
		  	 {
		  	   strBrithday=	getBirthdatByIdNo(document.all("AppntIDNo").value);
		  	   if(strBrithday=="") passVerify=false;
		  	   
	           document.all("AppntBirthday").value= strBrithday;
		  	 }	     
	      }	
	 }
	 catch(e)
	 {
	 	
	 }
}

//检验银行账号录入是否规范
function verifyBankAccNo()
{
	if(document.all("NewBankAccNo").value!=""&&document.all("NewBankAccNo").value!=null
		  	&&document.all("NewBankCode").value!=""&&document.all("NewBankCode").value!=null)
	{
		  if(!checkBankAccNo(document.all("NewBankCode").value,document.all("NewBankAccNo").value))
		  	  return false;
	}	
	if(document.all("BankAccNo").value!=""&&document.all("BankAccNo").value!=null
		  	&&document.all("BankCode").value!=""&&document.all("BankCode").value!=null)
	{
		  if(!checkBankAccNo(document.all("BankCode").value,document.all("BankAccNo").value))
			  return false;
	}
	return true;
}

//校验录入的险种是否不需要校验，如果需要返回true,否则返回false
//cSysVar为特殊处理类型
function needVerifyRiskcode(cSysVar)
{
  	  
  try { 
  	   var riskcode=document.all("RiskCode").value;
 	   
//       var tSql = "select Sysvarvalue from LDSysVar where Sysvar='"+cSysVar+"'";   
       
        var sqlid47 = "WbProposalInputSql47";
		var mySql47 = new SqlClass();
	    mySql47.setResourceName("app.WbProposalInputSql");
		mySql47.setSqlId(sqlid47);//指定使用SQL的id
		mySql47.addSubPara(cSysVar);//指定传入参数
	    var tSql = mySql47.getString();
       
       var tResult = easyExecSql(tSql, 1, 1, 1);       
       var strRiskcode = tResult[0][0];
       var strValue=strRiskcode.split("/");
       var i=0;
	   while(i<strValue.length)
	   {
	   	if(riskcode==strValue[i])
	   	{
           return false;
	   	}
	   	i++;
	   }   	   
  	 }
  	catch(e)
  	 {}
  	 
  	return true; 
  	
	
}
  
function queryAgent()
{
   /*
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	*/
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
//	var strSql = "select AgentCode,Name,AgentGroup,ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
   
	var sqlid48 = "WbProposalInputSql48";
	var mySql48 = new SqlClass();
	 mySql48.setResourceName ("app.WbProposalInputSql");
    mySql48.setSqlId(sqlid48);//指定使用SQL的id
	mySql48.addSubPara(cAgentCode);//指定传入参数
	var strSql = mySql48.getString();
    
	
	var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {      
      //fm.AgentManageComName.value = arrResult[0][4];
      //fm.AgentManageCom.value = arrResult[0][3];
      //fm.ManageComName.value = arrResult[0][4];
      //fm.ManageCom.value = arrResult[0][3];
      fm.AgentGroup.value = arrResult[0][2];
      fm.AgentName.value = arrResult[0][1];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}	
	
	initManageCom();
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	//fm.AgentGroup.value = arrResult[0][1];
  }
}

function queryAgent2()
{
    /*
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！"); 
		 return;
	}
	*/
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //保单号码	
//	var strSql = "select AgentCode,Name,AgentGroup,ManageCom,(select name from ldcom where comcode=managecom) from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
	var sqlid49 = "WbProposalInputSql49";
	var mySql49 = new SqlClass();
	var ManageCom49 = document.all('ManageCom').value;
	 mySql49.setResourceName ("app.WbProposalInputSql");
    mySql49.setSqlId(sqlid49);//指定使用SQL的id
	mySql49.addSubPara(cAgentCode);//指定传入参数
	mySql49.addSubPara(ManageCom49);//指定传入参数
	var strSql = mySql49.getString();
	
	var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {      
      //fm.AgentManageComName.value = arrResult[0][4];
      //fm.AgentManageCom.value = arrResult[0][3];
      //fm.ManageComName.value = arrResult[0][4];
      //fm.ManageCom.value = arrResult[0][3];
      fm.AgentGroup.value = arrResult[0][2];
      fm.AgentName.value = arrResult[0][1];
      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("编码为:["+document.all('AgentCode').value+"]的代理人不存在，请确认!");
     }
	}
	
	initManageCom();	
}

//增加被保人信息
function addInsuredInput()
{  
  var i ;
  for( i=1 ;i<=3; i++)
  {
  	if(document.getElementById("DivInsuredAll"+i).style.display == "none")
  	{
  		document.getElementById("DivInsuredAll"+i).style.display = "";
  		alert("增加成功！");
  		break;
  	}
  }
  
  queryInsuredInput();
}

//删除被保人信息
function deleteInsuredInput()
{      
  for( var i=3 ;i>=1; i--)
  {
  	if(document.getElementById("DivInsuredAll"+i).style.display == "")
  	{
  		document.getElementById("DivInsuredAll"+i).style.display = "none";
  		//增加被保人录入界面清除方法;
  		//清除被保人信息
  		initDeleteInsured(i);  		
  		//清除险种、受益人等信息  		
  		for( var j=1 ;j<=3; j++ )
  		{  			
  			if(document.getElementById("divRisk"+i+j).style.display == "")
  			{
  				//alert("Risk" +i +j +"Grid.clearData('Risk" +i +j +"Grid')");
  				eval("Risk" +i +j +"Grid.clearData('Risk" +i +j +"Grid')");
  				eval("Risk" +i +j +"Grid.addOne('Risk" +i +j +"Grid')");
  			}  				
  		}
  		eval("Bnf" +i +"Grid.clearData('Bnf" +i +"Grid')");
  		eval("DealType" +i +"Grid.clearData('DealType" +i +"Grid')");
  		eval("Bnf" +i +"Grid.addOne('Bnf" +i +"Grid')");
  		eval("DealType" +i +"Grid.addOne('DealType" +i +"Grid')");
  		 		
  		alert("删除成功！");
  		break;
  	}
  }
  
  queryInsuredInput();
  	
}

//查询被保人增删按钮状态
function queryInsuredInput()
{    
  	if((DivInsuredAll3).style.display == "")
  	    document.all("addInsured").disabled = true;
  	else
  	    document.all("addInsured").disabled = false;
  	    
  	if((DivInsuredAll1).style.display == "none")
  	    document.all("deleteInsured").disabled = true; 
  	else
  	    document.all("deleteInsured").disabled = false; 	
}

//初始化xml查询被保人显示情况
function initInsuredInput(sInsuNum)
{    
    var name;
  	for(var i=1;i<=sInsuNum ; i++)
  	{
  	   name ="DivInsuredAll"+i;
  	   document.getElementById(name).style.display = "";
  	}
  	
  	queryInsuredInput(); 	
}

//增加主险信息
// sRiskNum  被保人序号
function addMainRiskInput(sRiskNum)
{  
  var i ;
  for( i=1 ;i<=3; i++)
  {
  	if(document.getElementById("divRisk"+sRiskNum+i).style.display == "none")
  	{
  		document.getElementById("divRisk"+sRiskNum+i).style.display = "";
  		alert("增加成功！");
  		break;
  	}
  }
  
  queryMainRiskInput();
}

//删除主险信息
// sRiskNum  被保人序号
function deleteMainRiskInput(sRiskNum)
{      
  var i ;
  for( i=3 ;i>=1; i--)
  {
  	if(document.getElementById("divRisk"+sRiskNum+i).style.display == "")
  	{
  		document.getElementById("divRisk"+sRiskNum+i).style.display = "none";
  		//alert("Risk" +sRiskNum +i +"Grid");
  		//清空grid内容
  		eval("Risk" +sRiskNum +i +"Grid.clearData('Risk" +sRiskNum +i +"Grid')");
  		eval("Risk" +sRiskNum +i +"Grid.addOne('Risk" +sRiskNum +i +"Grid')");
  		alert("删除成功！");
  		break;
  	}
  }
  
  queryMainRiskInput();
  	
}

//查询主险增删按钮状态
function queryMainRiskInput()
{    
  for(var i=1 ;i<=3; i++)
  {
     if(document.getElementById("divRisk"+i+3).style.display == "")
  	    document.all("addMainRisk"+i).disabled = true;
  	 else
  	    document.all("addMainRisk"+i).disabled = false;
  	    
  	 if(document.getElementById("divRisk"+i+1).style.display == "none")
  		document.all("deleteMainRisk"+i).disabled = true;
  	 else
  	    document.all("deleteMainRisk"+i).disabled = false;
  	   
  	 if(document.getElementById("divRisk"+i+2).style.display == "none")
  	    document.all("MainRiskNo"+i+"1").value = "-1";
  	 else
  	    document.all("MainRiskNo"+i+"1").value = "1";
  }  
  	 	
}

//初始化xml查询被保人主险显示情况
function initMainRiskInput(sInsu,sRiskNum)
{
        //alert(sInsu);
        //alert("sRiskNum:"+sRiskNum);     
  		for(var j=1;j<=sRiskNum ; j++)
	  	{
	  	   document.getElementById("divRisk"+sInsu+j).style.display = "";
	  	}
  	
  	queryMainRiskInput(); 	
}

//初始化汉字信息
function showCodeName()
{
	if(fm.ManageCom.value!="")
	{
//		var strSql = "select Name from LDCom where ComCode= '"+document.all('ManageCom').value+"'";
		
		var sqlid50 = "WbProposalInputSql50";
		var mySql50 = new SqlClass();
		var ManageCom50 = document.all('ManageCom').value;
		 mySql50.setResourceName ("app.WbProposalInputSql");
	    mySql50.setSqlId(sqlid50);//指定使用SQL的id
		mySql50.addSubPara(ManageCom50);//指定传入参数
		var strSql = mySql50.getString();
		 var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.ManageComName.value = arrResult[0][0];
	      }
	}
	//showOneCodeNameRefresh('comcode','ManageCom','ManageComName');
	if(fm.AgentCom.value!="")
	{
//		var strSql = "select Name from LACom where AgentCom= '"+document.all('AgentCom').value+"'";
		
		var sqlid51 = "WbProposalInputSql51";
		var mySql51 = new SqlClass();
		var AgentCom51 = document.all('AgentCom').value;
		 mySql51.setResourceName ("app.WbProposalInputSql");
	    mySql51.setSqlId(sqlid51);//指定使用SQL的id
		mySql51.addSubPara(AgentCom51);//指定传入参数
		var strSql = mySql51.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.InputAgentComName.value = arrResult[0][0];
	      }
	}
	//showOneCodeNameRefresh('AgentCom','AgentCom','InputAgentComName');
	/*if(fm.AgentManageCom.value!="")
	{
		var strSql = "select Name from LDCom where ComCode= '"+document.all('AgentManageCom').value+"'";
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.AgentManageComName.value = arrResult[0][0];
	      }
	}*/
	if(fm.AppntOccupationCode.value!="")
	{
//		var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all('AppntOccupationCode').value+"'";
	    
		var sqlid52 = "WbProposalInputSql52";
		var mySql52 = new SqlClass();
		var AppntOccupationCode52 = document.all('AppntOccupationCode').value;
		 mySql52.setResourceName ("app.WbProposalInputSql");
	    mySql52.setSqlId(sqlid52);//指定使用SQL的id
		mySql52.addSubPara(AppntOccupationCode52);//指定传入参数
		var strSql = mySql52.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.AppntOccupationCodeName.value = arrResult[0][0];
	      }
	}
	
	//showOneCodeNameRefresh('comcode','AgentManageCom','AgentManageComName');
	if(fm.SaleChnl.value!="")
	{
//		var strSql = "select codename from ldcode where codetype = 'salechnl' and code='"+document.all('SaleChnl').value+"'";
	  
		var sqlid53 = "WbProposalInputSql53";
		var mySql53 = new SqlClass();
		var SaleChnl53 = document.all('SaleChnl').value;
		 mySql53.setResourceName ("app.WbProposalInputSql");
	    mySql53.setSqlId(sqlid53);//指定使用SQL的id
		mySql53.addSubPara(SaleChnl53);//指定传入参数
		var strSql = mySql53.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.SaleChnlName.value = arrResult[0][0];
	      }
	}
	/*if(fm.SaleChnl.value!="")
	showOneCodeNameRefresh('salechnl','SaleChnl','SaleChnlName');*/
	/*if(fm.ScanManageCom.value!="")
	showOneCodeName('comcode','ScanManageCom','ScanManageComName');*/
	if(fm.AppntSex.value!="")
	showOneCodeNameRefresh('Sex','AppntSex','AppntSexName');
	if(fm.AppntRelationToInsured.value!="")
	showOneCodeNameRefresh('Relation','AppntRelationToInsured','RelationToInsuredName');
	if(fm.AppntIDType.value!="")
	showOneCodeNameRefresh('IDType','AppntIDType','AppntIDTypeName');
	if(fm.AppntNativePlace.value!="")
	showOneCodeNameRefresh('NativePlace','AppntNativePlace','AppntNativePlaceName');
	if(fm.AppntMarriage.value!="")
	showOneCodeNameRefresh('Marriage','AppntMarriage','AppntMarriageName');
	//if(fm.AppntOccupationCode.value!="")
	//showOneCodeNameRefresh('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	if(fm.AppntOccupationType.value!="")
	showOneCodeNameRefresh('OccupationType','AppntOccupationType','AppntOccupationTypeName');	
	if(fm.AppntOccupationType.value!="")
	showOneCodeNameRefresh('ibrmsflag','AppntSocialInsuFlag','AppntSocialInsuFlagName');
	
	if(fm.NewPayMode.value!="")
	{
//		var strSql = "select codename from ldcode where codetype = 'paylocation2' and code='"+document.all('NewPayMode').value+"'";
	   
		var sqlid54 = "WbProposalInputSql54";
		var mySql54 = new SqlClass();
		var NewPayMode54 = document.all('NewPayMode').value;
		 mySql54.setResourceName ("app.WbProposalInputSql");
	    mySql54.setSqlId(sqlid54);//指定使用SQL的id
		mySql54.addSubPara(NewPayMode54);//指定传入参数
		var strSql = mySql54.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.NewPayModeName.value = arrResult[0][0];
	      }
	}
	/*if(fm.NewPayMode.value!="")
	showOneCodeNameRefresh('paylocation2','NewPayMode','NewPayModeName');	*/
	if(fm.NewBankCode.value!="")
	showOneCodeNameRefresh('bank','NewBankCode','AppntBankCodeName');
	if(fm.PayLocation.value!="")
	{
//		var strSql = "select codename from ldcode where codetype = 'paylocation' and code='"+document.all('PayLocation').value+"'";
	   
		var sqlid55 = "WbProposalInputSql55";
		var mySql55 = new SqlClass();
		var PayLocation55 = document.all('PayLocation').value;
		 mySql55.setResourceName ("app.WbProposalInputSql");
	    mySql55.setSqlId(sqlid55);//指定使用SQL的id
		mySql55.addSubPara(PayLocation55);//指定传入参数
		var strSql = mySql55.getString();
		var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.PayLocationName.value = arrResult[0][0];
	      }
	}	
	/*if(fm.PayLocation.value!="")
	showOneCodeNameRefresh('paylocation','PayLocation','PayLocationName');	*/
	if(fm.BankCode.value!="")
	showOneCodeNameRefresh('bank','BankCode','ReNBankCodeName');	


    for(var i=1; i<=3; i++)
    {
        if(document.getElementById("DivInsuredAll"+i).style.display == "")
	  	{
	  	   // var RelationToMainInsured = "RelationToMainInsured"+i;
	  	    var RelationToAppnt = "RelationToAppnt"+i;
	  	    var Sex = "Sex"+i;
	  	    var IDType = "IDType"+i;
	  	    var NativePlace = "NativePlace"+i;
	  	    var Marriage = "Marriage"+i;
	  	    //var OccupationCode = "OccupationCode"+i;
	  	    var OccupationType = "OccupationType"+i;
	  	    var SocialInsuFlag = "SocialInsuFlag"+i;
	  	    
	  	    //var RelationToMainInsuredName = "RelationToMainInsuredName"+i;
	  	    var RelationToAppntName = "RelationToAppntName"+i;
	  	    var SexName = "SexName"+i;
	  	    var IDTypeName = "IDTypeName"+i;
	  	    var NativePlaceName = "NativePlaceName"+i;
	  	    var MarriageName = "MarriageName"+i;
	  	    //var OccupationCodeName = "OccupationCodeName"+i;
	  	    var OccupationTypeName = "OccupationTypeName"+i;
	  	    //if(document.all("RelationToMainInsured"+i).value!="")
	  		//showOneCodeName('Relation',RelationToMainInsured,RelationToMainInsuredName);
	  		var SocialInsuFlagName = "SocialInsuFlagName"+i;
	  		if(document.all("RelationToAppnt"+i).value!="")
	    	showOneCodeNameRefresh('Relation',RelationToAppnt,RelationToAppntName);
	    	if(document.all("Sex"+i).value!="")
			showOneCodeNameRefresh('Sex',Sex,SexName);
			if(document.all("IDType"+i).value!="")
			showOneCodeNameRefresh('IDType',IDType,IDTypeName);
			if(document.all("NativePlace"+i).value!="")
			showOneCodeNameRefresh('NativePlace',NativePlace,NativePlaceName);
			if(document.all("Marriage"+i).value!="")
			showOneCodeNameRefresh('Marriage',Marriage,MarriageName);
			//if(document.all("OccupationCode"+i).value!="")
			//showOneCodeNameRefresh('occupationcode',OccupationCode,OccupationCodeName);
			if(document.all("OccupationCode"+i).value!="")
			{
//				var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all("OccupationCode"+i).value+"'";
			   
				var sqlid56 = "WbProposalInputSql56";
				var mySql56 = new SqlClass();
				var OccupationCode56 = document.all("OccupationCode"+i).value;
				 mySql56.setResourceName ("app.WbProposalInputSql");
			    mySql56.setSqlId(sqlid56);//指定使用SQL的id
				mySql56.addSubPara(OccupationCode56);//指定传入参数
				var strSql = mySql56.getString();
				var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      document.all("OccupationCodeName"+i).value = arrResult[0][0];
			      }
			}
			if(document.all("OccupationType"+i).value!="")
			showOneCodeNameRefresh('OccupationType',OccupationType,OccupationTypeName);
			if(document.all("SocialInsuFlag"+i).value!="")
			showOneCodeNameRefresh('ibrmsflag',SocialInsuFlag,SocialInsuFlagName);
	  	}
	  	else
	  	   break ;
    		
    }
}

function clearRiskGridNone()
{
  for(var m = 1;m<=3;m++)
  	for(var n =1;n<=3;n++)
  	{
  		  eval("Risk"+m+n+"Grid.checkValue('Risk"+m+n+"Grid')");
		  var num ;
		  eval("num = Risk"+m+n+"Grid.mulLineCount");
		  if(num < 1 )
		  {
		  	document.getElementById("divRisk"+m+n).style.display = "none" ;
		  }
  	}  
}

//查询合同的所有数据库的信息
//
function queryAllContInfo()
{
	emptyForm();
	queryAppntInfo();
    queryContNo();   
    initManageCom();
    queryInsuredInfo();
    queryRiskInfo();    
    getLCBnfInfo();
    getDealTypeInfo();
	getImpartInfo();//告知
	showCodeName();
}

//【查询合同以及缴费信息】－－－
function queryContNo()
{
//	var sSQL ="select NewBankCode,NewAccName,NewBankAccNo,BankCode,AccName,BankAccNo"
//	        +" ,'' "
//	         //不可修改的内容 
//	        +" ,prtno,polapplydate"
//	        +" ,(select managecom from es_doc_main where doccode=l.prtno and subtype='UA001' and rownum=1)"
//	        +" ,agentcom,agentcode"
//	        +" ,managecom,agentgroup,salechnl,remark"
//	        +" ,NewPayMode,PayLocation,AutoPayFlag,RnewFlag,GetPolMode,OutPayFlag,PayIntv"
//	        +" ,(select a.name from LAAgent a where a.AgentCode=l.agentcode)"
//	        +" ,(select a.name from LACom a where a.AgentCom=l.agentcom)"
//	       //--不可修改的内容
//	        +" ,signname "
//	        +" ,firsttrialdate "
//	        +" ,xqremindflag "
//			+" from lccont l where contno='"+fm.ProposalNo.value+"'";
	//prompt('',sSQL);
	var sqlid57 = "WbProposalInputSql57";
	var mySql57 = new SqlClass();
	var ProposalNo57 = fm.ProposalNo.value;
	 mySql57.setResourceName ("app.WbProposalInputSql");
    mySql57.setSqlId(sqlid57);//指定使用SQL的id
	mySql57.addSubPara(ProposalNo57);//指定传入参数
	var sSQL = mySql57.getString();
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.NewBankCode.value=arrInsuredAdd[0][0];
		fm.NewAccName.value=arrInsuredAdd[0][1];
		fm.NewBankAccNo.value=arrInsuredAdd[0][2];
		fm.BankCode.value=arrInsuredAdd[0][3];
		fm.AccName.value=arrInsuredAdd[0][4];
		fm.BankAccNo.value=arrInsuredAdd[0][5];
		//fm.SelPolNo.value=arrInsuredAdd[0][6];
		fm.PrtNo.value=arrInsuredAdd[0][7];
		fm.PolApplyDate.value=arrInsuredAdd[0][8];
		fm.ManageCom.value=arrInsuredAdd[0][9];
		fm.AgentCom.value=arrInsuredAdd[0][10];
		fm.AgentCode.value=arrInsuredAdd[0][11];
		//fm.AgentManageCom.value=arrInsuredAdd[0][12];
		fm.AgentGroup.value=arrInsuredAdd[0][13];
		fm.SaleChnl.value=arrInsuredAdd[0][14];
		fm.Remark.value=arrInsuredAdd[0][15];
		fm.NewPayMode.value=arrInsuredAdd[0][16];
		fm.PayLocation.value=arrInsuredAdd[0][17];
		fm.AutoPayFlag.value=arrInsuredAdd[0][18];
		fm.RnewFlag.value=arrInsuredAdd[0][19];
		fm.GetPolMode.value=arrInsuredAdd[0][20];
		fm.OutPayFlag.value=arrInsuredAdd[0][21];
		fm.PayIntv.value=arrInsuredAdd[0][22];
		fm.AgentName.value=arrInsuredAdd[0][23];	
		//fm.InputAgentComName.value=arrInsuredAdd[0][24];
		fm.SignName.value=arrInsuredAdd[0][25];
		
		fm.FirstTrialDate.value=arrInsuredAdd[0][26];
		fm.XQremindFlag.value=arrInsuredAdd[0][27];
		showOneCodeNameRefresh('XQremindFlag','XQremindFlag','XQremindFlagName');
	}
}

//【查询投保人信息】－－－
function queryAppntInfo()
{
    //alert(fm.ProposalNo.value);
//	var sSQL ="select a.AppntName,a.idtype,a.idno, b.postaladdress,b.zipcode,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.appntno,a.addressno,a.RelationToInsured"
//			//不可修改的内容 
//	        +" ,a.appntsex,a.appntbirthday,a.NativePlace,a.RgtAddress,a.Marriage,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--不可修改的内容
//			+ " from lcappnt a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.appntno and b.addressno=a.addressno";
	var sqlid58 = "WbProposalInputSql58";
	var mySql58 = new SqlClass();
	var ProposalNo58 = fm.ProposalNo.value;
	 mySql58.setResourceName ("app.WbProposalInputSql");
    mySql58.setSqlId(sqlid58);//指定使用SQL的id
	mySql58.addSubPara(ProposalNo58);//指定传入参数
	var sSQL = mySql58.getString();
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.AppntName.value=arrInsuredAdd[0][0];
		fm.AppntIDType.value=arrInsuredAdd[0][1];
		fm.AppntIDNo.value=arrInsuredAdd[0][2];
		fm.AppntPostalAddress.value=arrInsuredAdd[0][3];
		fm.AppntZipCode.value=arrInsuredAdd[0][4];
		fm.AppntHomeAddress.value=arrInsuredAdd[0][5];		
		fm.AppntHomeZipCode.value=arrInsuredAdd[0][6];
		fm.AppntPhone.value=arrInsuredAdd[0][7];
		fm.AppntPhone2.value=arrInsuredAdd[0][8];
		fm.AppntEMail.value=arrInsuredAdd[0][9];
		fm.AppntGrpName.value=arrInsuredAdd[0][10];
		fm.AppntNo.value=arrInsuredAdd[0][11];
		//fm.AppntAddressNo.value=arrInsuredAdd[0][12];
		//alert(fm.AppntAddressNo.value);
		fm.AppntRelationToInsured.value=arrInsuredAdd[0][13];
		fm.AppntSex.value=arrInsuredAdd[0][14];
		fm.AppntBirthday.value=arrInsuredAdd[0][15];
		document.all('AppntAge').value=calAge(fm.AppntBirthday.value);
		fm.AppntNativePlace.value=arrInsuredAdd[0][16];
		fm.AppntRgtAddress.value=arrInsuredAdd[0][17];
		fm.AppntMarriage.value=arrInsuredAdd[0][18];
		fm.AppntPluralityType.value=arrInsuredAdd[0][19];
		fm.AppntOccupationCode.value=arrInsuredAdd[0][20];
		fm.AppntOccupationType.value=arrInsuredAdd[0][21];
		fm.AppntIDEndDate.value=arrInsuredAdd[0][22];
		fm.AppntSocialInsuFlag.value=arrInsuredAdd[0][23];
	}
}

//【查询被保人信息】－－－
function queryInsuredInfo()
{
//	var sSQL ="select a.Name,a.idtype,a.idno,b.postaladdress,b.zipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
//			//不可修改的内容 
//	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
//	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--不可修改的内容
//			+ " from lcinsured a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.insuredno and sequenceno in ('1','-1') and b.addressno=a.addressno";
//	//prompt('',sSQL);
	var sqlid59 = "WbProposalInputSql59";
	var mySql59 = new SqlClass();
	var ProposalNo59 = fm.ProposalNo.value;
	 mySql59.setResourceName ("app.WbProposalInputSql");
    mySql59.setSqlId(sqlid59);//指定使用SQL的id
	mySql59.addSubPara(ProposalNo59);//指定传入参数
	var sSQL = mySql59.getString();
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name1.value=arrInsuredAdd[0][0];
		fm.IDType1.value=arrInsuredAdd[0][1];
		fm.IDNo1.value=arrInsuredAdd[0][2];
		fm.HomeAddress1.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode1.value=arrInsuredAdd[0][4];
		fm.Phone1.value=arrInsuredAdd[0][5];
		fm.Phone21.value=arrInsuredAdd[0][6];
		fm.EMail1.value=arrInsuredAdd[0][7];
		fm.GrpName1.value=arrInsuredAdd[0][8];
		fm.CustomerNo1.value=arrInsuredAdd[0][9];
		//fm.AddressNo1.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo1.value);
		fm.RelationToAppnt1.value=arrInsuredAdd[0][11];
		
		//fm.SequenceNoCode1.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured1.value=arrInsuredAdd[0][13];
		//fm.RelationToAppnt1.value=arrInsuredAdd[0][14];
		fm.Sex1.value=arrInsuredAdd[0][15];
		fm.Birthday1.value=arrInsuredAdd[0][16];	
		document.all('Age1').value=calAgeNew(document.all("Birthday1").value,document.all("PolApplyDate").value);	
		fm.NativePlace1.value=arrInsuredAdd[0][17];
		fm.RgtAddress1.value=arrInsuredAdd[0][18];
		fm.Marriage1.value=arrInsuredAdd[0][19];
		fm.WorkType1.value=arrInsuredAdd[0][20];
		fm.PluralityType1.value=arrInsuredAdd[0][21];		
		fm.OccupationCode1.value=arrInsuredAdd[0][22];
		fm.OccupationType1.value=arrInsuredAdd[0][23];
		fm.IDEndDate1.value=arrInsuredAdd[0][24];
		fm.SocialInsuFlag1.value=arrInsuredAdd[0][25];
		(DivInsuredAll1).style.display = "";
	}
	
//	sSQL ="select a.Name,a.idtype,a.idno,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
//			//不可修改的内容 
//	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
//	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--不可修改的内容
//			+ " from lcinsured a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.insuredno and sequenceno='2' and b.addressno=a.addressno";
//	//prompt('',sSQL);
	var sqlid60 = "WbProposalInputSql60";
	var mySql60 = new SqlClass();
	var ProposalNo60 = fm.ProposalNo.value;
	 mySql60.setResourceName ("app.WbProposalInputSql");
    mySql60.setSqlId(sqlid60);//指定使用SQL的id
	mySql60.addSubPara(ProposalNo60);//指定传入参数
	sSQL = mySql60.getString();
	arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name2.value=arrInsuredAdd[0][0];
		fm.IDType2.value=arrInsuredAdd[0][1];
		fm.IDNo2.value=arrInsuredAdd[0][2];
		fm.HomeAddress2.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode2.value=arrInsuredAdd[0][4];
		fm.Phone2.value=arrInsuredAdd[0][5];
		fm.Phone22.value=arrInsuredAdd[0][6];
		//fm.EMail2.value=arrInsuredAdd[0][7];
		fm.GrpName2.value=arrInsuredAdd[0][8];
		fm.CustomerNo2.value=arrInsuredAdd[0][9];
		//fm.AddressNo2.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo2.value);
		fm.RelationToAppnt2.value=arrInsuredAdd[0][11];
		
		//fm.SequenceNoCode2.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured2.value=arrInsuredAdd[0][13];
		//fm.RelationToAppnt2.value=arrInsuredAdd[0][14];
		fm.Sex2.value=arrInsuredAdd[0][15];
		fm.Birthday2.value=arrInsuredAdd[0][16];	
		document.all('Age2').value=calAgeNew(document.all("Birthday2").value,document.all("PolApplyDate").value);	
		fm.NativePlace2.value=arrInsuredAdd[0][17];
		fm.RgtAddress2.value=arrInsuredAdd[0][18];
		fm.Marriage2.value=arrInsuredAdd[0][19];
		fm.WorkType2.value=arrInsuredAdd[0][20];
		fm.PluralityType2.value=arrInsuredAdd[0][21];		
		fm.OccupationCode2.value=arrInsuredAdd[0][22];
		fm.OccupationType2.value=arrInsuredAdd[0][23];
		fm.IDEndDate2.value=arrInsuredAdd[0][24];
		fm.SocialInsuFlag2.value=arrInsuredAdd[0][25];
		(DivInsuredAll2).style.display = "";
	}
	
//	sSQL ="select a.Name,a.idtype,a.idno,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
//			//不可修改的内容 
//	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
//	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType,a.idexpdate,a.socialinsuflag"	        
//	       //--不可修改的内容
//			+ " from lcinsured a ,lcaddress b where contno='"+fm.ProposalNo.value+"'"
//			+ " and b.customerno=a.insuredno and sequenceno='3' and b.addressno=a.addressno";
//	//prompt('',sSQL);
	var sqlid61 = "WbProposalInputSql61";
	var mySql61 = new SqlClass();
	var ProposalNo61 = fm.ProposalNo.value;
	 mySql61.setResourceName ("app.WbProposalInputSql");
    mySql61.setSqlId(sqlid61);//指定使用SQL的id
	mySql61.addSubPara(ProposalNo61);//指定传入参数
	sSQL = mySql61.getString();
	arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name3.value=arrInsuredAdd[0][0];
		fm.IDType3.value=arrInsuredAdd[0][1];
		fm.IDNo3.value=arrInsuredAdd[0][2];
		fm.HomeAddress3.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode3.value=arrInsuredAdd[0][4];
		fm.Phone3.value=arrInsuredAdd[0][5];
		fm.Phone23.value=arrInsuredAdd[0][6];
		//fm.EMail3.value=arrInsuredAdd[0][7];
		fm.GrpName3.value=arrInsuredAdd[0][8];
		fm.CustomerNo3.value=arrInsuredAdd[0][9];
		//fm.AddressNo3.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo3.value);
		fm.RelationToAppnt3.value=arrInsuredAdd[0][11];
		
		//fm.SequenceNoCode3.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured3.value=arrInsuredAdd[0][13];
		//fm.RelationToAppnt3.value=arrInsuredAdd[0][14];
		fm.Sex3.value=arrInsuredAdd[0][15];
		fm.Birthday3.value=arrInsuredAdd[0][16];	
		document.all('Age3').value=calAgeNew(document.all("Birthday3").value,document.all("PolApplyDate").value);	
		fm.NativePlace3.value=arrInsuredAdd[0][17];
		fm.RgtAddress3.value=arrInsuredAdd[0][18];
		fm.Marriage3.value=arrInsuredAdd[0][19];
		fm.WorkType3.value=arrInsuredAdd[0][20];
		fm.PluralityType3.value=arrInsuredAdd[0][21];		
		fm.OccupationCode3.value=arrInsuredAdd[0][22];
		fm.OccupationType3.value=arrInsuredAdd[0][23];
		fm.IDEndDate3.value=arrInsuredAdd[0][24];
		fm.SocialInsuFlag3.value=arrInsuredAdd[0][25];
		(DivInsuredAll3).style.display = "";
	}
}

//获得险种信息
function queryRiskInfo()
{
	var tContNo=document.all('ProposalNo').value;
	for(var i=1;i<=3;i++)
	{
		var StrSequenceno =  " and sequenceno ='"+i+"' ";
		if(i == 1)
			StrSequenceno =  " and sequenceno in ('1','-1') ";
		for(var j=1;j<=3;j++)
		{
			var StrRiskSequenceno =  " and risksequence ='"+j+"' ";
			if(j == 1)
				StrRiskSequenceno =  " and risksequence in ('1','-1') ";
//			var sSQL="select '',risksequence,riskcode,(select riskname from lmriskapp where riskcode=a.riskcode)"
//				+" ,amnt,mult,insuyear,insuyearflag,payendyear,payendyearflag,standprem"
//				+" ,(select decode(sum(prem),null,'',sum(prem)) from lcprem where polno=a.polno and AddFeeDirect='03' and payplancode like '000000%%')"
//				+ ",inputprem"
//				+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno "+StrSequenceno+") "+StrRiskSequenceno+" order by polno ";
//			//prompt('',sSQL);
			var sqlid62 = "WbProposalInputSql62";
			var mySql62 = new SqlClass();
			 mySql62.setResourceName ("app.WbProposalInputSql");
		    mySql62.setSqlId(sqlid62);//指定使用SQL的id
			mySql62.addSubPara(tContNo);//指定传入参数
			mySql62.addSubPara(tContNo);//指定传入参数
			mySql62.addSubPara(StrSequenceno);//指定传入参数
			mySql62.addSubPara(StrRiskSequenceno);//指定传入参数
			var sSQL = mySql62.getString();
			var arrInsuredAdd=easyExecSql(sSQL); 
			if(arrInsuredAdd!=null)
			{	
				document.all("MainRiskNo"+i+j).value = arrInsuredAdd[0][1];			
				//alert("turnPage.queryModal(sSQL,Risk"+i+"1Grid)");
				eval("turnPage.queryModal(sSQL,Risk"+i+j+"Grid)");
				document.getElementById("divRisk"+i+j).style.display = "";
			} 	
		}		
	}
	queryMainRiskInput();
	
}

//获得受益人信息
function getLCBnfInfo()
{
//	var tContNo=document.all('ProposalNo').value;
//		var sSQL="select '',(select risksequence from lcpol where polno=a.polno),bnftype"
//		   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//		    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//			+" ,PostalAddress,IDExpDate,''"
//			+" from lcbnf a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno in ('1','-1')) order by bnfno ";
////		prompt('',sSQL);
		var sqlid63 = "WbProposalInputSql63";
		var mySql63 = new SqlClass();
		 mySql63.setResourceName ("app.WbProposalInputSql");
	    mySql63.setSqlId(sqlid63);//指定使用SQL的id
		mySql63.addSubPara(tContNo);//指定传入参数
		mySql63.addSubPara(tContNo);//指定传入参数
		var sSQL = mySql63.getString();
		turnPage.queryModal(sSQL,Bnf1Grid);
		
//		sSQL="select '',(select risksequence from lcpol where polno=a.polno),bnftype"
//		   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//		    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//			+" ,PostalAddress,IDExpDare,''"
//			+" from lcbnf a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='2') order by bnfno ";
//		
		var sqlid64 = "WbProposalInputSql64";
		var mySql64 = new SqlClass();
		 mySql64.setResourceName ("app.WbProposalInputSql");
	    mySql64.setSqlId(sqlid64);//指定使用SQL的id
		mySql64.addSubPara(tContNo);//指定传入参数
		mySql64.addSubPara(tContNo);//指定传入参数
		sSQL = mySql64.getString();
		turnPage.queryModal(sSQL,Bnf2Grid);
		
//		sSQL="select '',(select risksequence from lcpol where polno=a.polno),bnftype"
//		   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//		    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//			+" ,PostalAddress,IDExpDare,''"
//			+" from lcbnf a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='3') order by bnfno ";
//		
		var sqlid65 = "WbProposalInputSql65";
		var mySql65 = new SqlClass();
		 mySql65.setResourceName ("app.WbProposalInputSql");
	    mySql65.setSqlId(sqlid65);//指定使用SQL的id
		mySql65.addSubPara(tContNo);//指定传入参数
		mySql65.addSubPara(tContNo);//指定传入参数
		sSQL = mySql65.getString();
		turnPage.queryModal(sSQL,Bnf3Grid);
}

//获得处理方式信息
function getDealTypeInfo()
{
	var tContNo=document.all('ProposalNo').value;
//		var sSQL="select '',risksequence,getyear,getyearflag,'' "
//			+" ,(select max(getdutykind) from lcget where polno=a.polno and livegettype='0' and (getdutykind is not null or getdutykind<>'')),LiveGetMode,BonusGetMode "
//			+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno in ('1','-1')) and polno=mainpolno order by risksequence";
//		//prompt('',sSQL);
		var sqlid66 = "WbProposalInputSql66";
		var mySql66 = new SqlClass();
		 mySql66.setResourceName ("app.WbProposalInputSql");
	    mySql66.setSqlId(sqlid66);//指定使用SQL的id
		mySql66.addSubPara(tContNo);//指定传入参数
		mySql66.addSubPara(tContNo);//指定传入参数
		var sSQL = mySql66.getString();
		turnPage.queryModal(sSQL,DealType1Grid);
		
//		sSQL="select '',risksequence,getyear,getyearflag,'' "
//			+" ,'',LiveGetMode,BonusGetMode "
//			+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='2') and polno=mainpolno order by risksequence ";
		
		var sqlid67 = "WbProposalInputSql67";
		var mySql67 = new SqlClass();
		 mySql67.setResourceName ("app.WbProposalInputSql");
	    mySql67.setSqlId(sqlid67);//指定使用SQL的id
		mySql67.addSubPara(tContNo);//指定传入参数
		mySql67.addSubPara(tContNo);//指定传入参数
		sSQL = mySql67.getString();
		turnPage.queryModal(sSQL,DealType2Grid);
//		sSQL="select '',risksequence,getyear,getyearflag,'' "
//			+" ,'',LiveGetMode,BonusGetMode "
//			+" from lcpol a where contno='"+tContNo+"' and exists(select 1 from lcinsured where contno='"+tContNo+"' and insuredno=a.insuredno and sequenceno='3') and polno=mainpolno order by risksequence ";
		
		var sqlid68 = "WbProposalInputSql68";
		var mySql68 = new SqlClass();
		 mySql68.setResourceName ("app.WbProposalInputSql");
	    mySql68.setSqlId(sqlid68);//指定使用SQL的id
		mySql68.addSubPara(tContNo);//指定传入参数
		mySql68.addSubPara(tContNo);//指定传入参数
		sSQL = mySql68.getString();
		turnPage.queryModal(sSQL,DealType3Grid);

}

//获得告知信息
function getImpartInfo()
{
	var tContNo=document.all('ProposalNo').value;
//	var sSQL="select impartver,impartcode,impartcontent,impartparammodle,customernotype,decode(customernotype,'1',(select decode(sequenceno,'-1','',sequenceno) from lcinsured where contno=a.contno and insuredno=a.customerno),'')"	   
//		+" from lccustomerimpart a where contno='"+tContNo+"' order by customernotype ";
//	//prompt('',sSQL);
	
	var sqlid69 = "WbProposalInputSql69";
	var mySql69 = new SqlClass();
	 mySql69.setResourceName ("app.WbProposalInputSql");
    mySql69.setSqlId(sqlid69);//指定使用SQL的id
	mySql69.addSubPara(tContNo);//指定传入参数
	var sSQL = mySql69.getString();
	turnPage.queryModal(sSQL,ImpartGrid,null,null,100);
	
}




