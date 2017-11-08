	/*********************************************************************
 *  程序名称：LRReInsureAnswerInput.js
 *  程序功能：再保回复
 *  创建日期：2006-11-30 
 *  创建人  ：zhang bin
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
 *********************************************************************
 */

var arrResult1 = new Array();
var arrResult2 = new Array();
var arrResult3 = new Array();
var arrResult4 = new Array();
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var temp = new Array();
var mOperate="";
var ImportPath;
var oldDataSet ; 
var InputFlag="0";
//alert(PrtNo);
window.onfocus=myonfocus;

/**
	*查询核保发起任务
	*/
 function QueryRiskInfo()
 { 	
 		divRiskInfo.style.display="";
 		divEdorInfo.style.display="none";
 		divClaimInfo.style.display="none";
 		
 	 /**
	  mSQL =" select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)), a.dutycode, (select amnt from lcduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode)) , (select distinct prem from lcduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode)), (case state when '00' then '待回复' when '01' then '已回复' when '02' then '办结' end),a.polno,a.uwno,a.state  "
	  +" from lcreinsurtask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from lcreinsurtask where AuditType='01' group by polno,dutycode,auditcode,AuditAffixCode) b "
 		+" where a.polno=b.pno and a.dutycode=b.dcode and a.auditcode=b.acode and a.auditaffixcode=b.aacode and a.uwno=b.maxno and a.audittype='01'"
 		;
 		*/
 		var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRReInsureAnswerInputSql100");//指定使用的Sql的id
	    mySql100.addSubPara("1");
	    mSQL=mySql100.getString();
 		
 		if(fmInfo.OpeFlag.value=="1"){
 			//mSQL=mSQL+"  and a.polno='"+fmInfo.ProposalNo.value+"' and a.dutycode='"+fmInfo.DutyCode.value+"' ";
 			var mySql101=new SqlClass();
				mySql101.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
				mySql101.setSqlId("LRReInsureAnswerInputSql101");//指定使用的Sql的id
				mySql101.addSubPara(fmInfo.ProposalNo.value);//指定传入的参数
				mySql101.addSubPara(fmInfo.DutyCode.value);//指定传入的参数
	    		mSQL=mySql101.getString();
 			
 			
 		}
 		//mSQL = mSQL + " order by a.ModifyDate desc ";
 		/**
 		var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql102.setSqlId("LRReInsureAnswerInputSql102");//指定使用的Sql的id
			mySql102.addSubPara(mSQL);//指定传入的参数
	    	mSQL=mySql102.getString();
	    	*/
		turnPage1.queryModal(mSQL, RiskInfoGrid);
 }

function QueryEdorInfo(){
 		divRiskInfo.style.display="none";
 		divEdorInfo.style.display="";
 		divClaimInfo.style.display="none";
 		/**
		mSQL="select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)), a.dutycode, a.auditcode ,AuditAffixCode ,(select amnt from lpduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(edorno)=trim(auditcode) and trim(edortype)=trim(AuditAffixCode)), (select distinct prem from lpduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(edorno)=trim(auditcode) and trim(edortype)=trim(AuditAffixCode)),(case state when '00' then '待回复' when '01' then '已回复' when '02' then '办结' end),a.polno,a.uwno,a.state "
 		+ " from lcreinsurtask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from lcreinsurtask where AuditType='03' group by polno,dutycode,auditcode,AuditAffixCode) b "
 		+ " where a.polno=b.pno and a.dutycode=b.dcode and a.auditcode=b.acode and a.auditaffixcode=b.aacode and a.uwno=b.maxno "
		+ " and AuditType='03' order by ModifyDate desc "
		*/
		var mySql103=new SqlClass();
			mySql103.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql103.setSqlId("LRReInsureAnswerInputSql103");//指定使用的Sql的id
	    	mySql103.addSubPara("1");
	    	mSQL=mySql103.getString();
		turnPage2.queryModal(mSQL,EdorInfoGrid);
}

function QueryClaimInfo(){
	divRiskInfo.style.display="none";
 	divEdorInfo.style.display="none";
 	divClaimInfo.style.display="";
 	/**
	mSQL="select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)),a.dutycode,a.auditcode ,(select sum(realpay) from llclaim where trim(caseno)=trim(a.auditcode) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) group by caseno,polno,dutycode),(case state when '00' then '待回复' when '01' then '已回复' when '02' then '办结' end),a.polno,a.uwno,a.state "
	+ " from lcreinsurtask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from lcreinsurtask where AuditType='04' group by polno,dutycode,auditcode,AuditAffixCode) b "
 	+ " where a.polno=b.pno and a.dutycode=b.dcode and a.auditcode=b.acode and a.auditaffixcode=b.aacode and a.uwno=b.maxno "
	+ " and AuditType='04' order by ModifyDate desc "
	;
	*/
	var mySql104=new SqlClass();
		mySql104.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
		mySql104.setSqlId("LRReInsureAnswerInputSql104");//指定使用的Sql的id
	    mySql104.addSubPara("1");
	    mSQL=mySql104.getString();
	turnPage3.queryModal(mSQL,ClaimInfoGrid);
}
 /**
	*查询发送回复信息
	*/
 function QueryAnswerIdea()
 {
 	
 	var tSel=ReInsureAuditGrid.getSelNo();
 	var saFlag=ReInsureAuditGrid.getRowColData(tSel-1,9);
 	if(saFlag=="1") //如果‘核保发送’
 	{
	 	/**
	 	var mSql="select uwidea from lcreinsuruwidea where polno='"+ReInsureAuditGrid.getRowColData(tSel-1,8)+"'"
	 	+" and UWNo="+ReInsureAuditGrid.getRowColData(tSel-1,3)+""
	 	*/
	 	var mySql105=new SqlClass();
			mySql105.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql105.setSqlId("LRReInsureAnswerInputSql105");//指定使用的Sql的id
			mySql105.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,8));//指定传入的参数
			mySql105.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,3));//指定传入的参数
	    var mSql=mySql105.getString();
	 	var arrResult=easyExecSql(mSql);
	 	if(arrResult!=null)
	 	{
	 		fm.SendAnswerRemark.value=arrResult[0];
	 	}
	}else
	{
		/**
		var mSql="select uwidea from lcreinsuridea where polno='"+ReInsureAuditGrid.getRowColData(tSel-1,8)+"'"
	 	+" and UWNo="+ReInsureAuditGrid.getRowColData(tSel-1,3)+""
	 	*/
	 	var mySql106=new SqlClass();
			mySql106.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql106.setSqlId("LRReInsureAnswerInputSql106");//指定使用的Sql的id
			mySql106.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,8));//指定传入的参数
			mySql106.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,3));//指定传入的参数
	    var mSql=mySql106.getString();
	 	var arrResult=easyExecSql(mSql);
	 	if(arrResult!=null)
	 	{
	 		fm.SendAnswerRemark.value=arrResult[0];
	 	}
	}
 }
 	
function AutoReInsure()
{
	  var showStr=""+"正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    fm.action = "./AutoReInsureChk.jsp";
    fm.submit();
} 	
function 	ReInsureAudit(){
 var tContNo = fm.ContNo.value;
// alert(tContNo);
//  mSQL ="select a.insuredname,(select riskcode from lcpol where polno=a.polno),a.uwerror from LCUWError a where a.contno ='" + tContNo +"' and SugPassFlag='R'";
	var mySql107=new SqlClass();
		mySql107.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
		mySql107.setSqlId("LRReInsureAnswerInputSql107");//指定使用的Sql的id
		mySql107.addSubPara(tContNo);//指定传入的参数
	    mSQL=mySql107.getString();
	turnPage.queryModal(mSQL, ReInsureGrid);
}
 	
function SendUWReInsure()
{
	var tsql;
	var arr;
	if(fmInfo.OpeFlag.value=="1"){
		fm.AuditType.value="01";
	}
	if(fm.AuditType.value==""||fm.AuditType.value==null){
			myAlert(""+"请选择再保审核类型！"+"");
			return false;
	}
	if(fm.AuditType.value=="01"){
		tSel = RiskInfoGrid.getSelNo();
		polNo=RiskInfoGrid.getRowColData(tSel-1,7);
		dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
		if(tSel==0||tSel==null)
		{
			myAlert(""+"请选择险种保单信息！"+"");
			return false;
		}
		//tSql="select appflag from lcpol where proposalno='"+polNo+"'";
		var mySql108=new SqlClass();
			mySql108.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql108.setSqlId("LRReInsureAnswerInputSql108");//指定使用的Sql的id
			mySql108.addSubPara(polNo);//指定传入的参数
	    	tSql=mySql108.getString();
		
		
		arr=easyExecSql(tSql);
		if(arr==null){
			myAlert(""+"没有查询到保单信息"+"");
			return false;
		}else{
			if(arr[0]=="1"){
				myAlert(""+"保单已签单，不能再保回复"+"");
				return false;
			}
		}
		//tsql ="select state from lcreinsurtask where uwno=(select max(uwno) from lcreinsurtask where polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditType='01') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditType='01'";
		var mySql109=new SqlClass();
			mySql109.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql109.setSqlId("LRReInsureAnswerInputSql109");//指定使用的Sql的id
			mySql109.addSubPara(polNo);//指定传入的参数
			mySql109.addSubPara(dutyCode);//指定传入的参数
			mySql109.addSubPara(polNo);//指定传入的参数
			mySql109.addSubPara(dutyCode);//指定传入的参数
	    	tsql=mySql109.getString();
		
		arr=easyExecSql(tsql);	
		if(arr=='01'||arr=='02') //如果是已回复状态
		{
	    myAlert(""+"该任务为已回复状态，不能再回复核保申请!"+"");
	     return false;
		} 
	}
	if(fm.AuditType.value=="03"){
		tSel = EdorInfoGrid.getSelNo();
		polNo=EdorInfoGrid.getRowColData(tSel-1,9);
		dutyCode=EdorInfoGrid.getRowColData(tSel-1,3);
		edorNo=EdorInfoGrid.getRowColData(tSel-1,4);
		edorType=EdorInfoGrid.getRowColData(tSel-1,5);
		
		if(tSel==0||tSel==null){
			myAlert(""+"请选择险种保单信息！"+"");
			return false;
		}
		//tsql ="select state from lcreinsurtask where uwno=(select max(uwno) from lcreinsurtask where polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+edorNo+"' and AuditAffixCode='"+edorType+"'  and AuditType='03') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+edorNo+"' and AuditAffixCode='"+edorType+"' and AuditType='03'";
		var mySql110=new SqlClass();
			mySql110.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql110.setSqlId("LRReInsureAnswerInputSql110");//指定使用的Sql的id
			mySql110.addSubPara(polNo);//指定传入的参数
			mySql110.addSubPara(dutyCode);//指定传入的参数
			mySql110.addSubPara(edorNo);//指定传入的参数
			mySql110.addSubPara(edorType);//指定传入的参数		
			mySql110.addSubPara(polNo);//指定传入的参数
			mySql110.addSubPara(dutyCode);//指定传入的参数
			mySql110.addSubPara(edorNo);//指定传入的参数		
			mySql110.addSubPara(edorType);//指定传入的参数
	    	tsql=mySql110.getString();
		
		arr=easyExecSql(tsql);	
		if(arr=='01'||arr=='02'){
	    myAlert(""+"该任务为已回复状态，不能再回复核保申请!"+"");
	     return false;
		} 
	}
	if(fm.AuditType.value=="04"){
		tSel = ClaimInfoGrid.getSelNo();
		polNo=ClaimInfoGrid.getRowColData(tSel-1,7);
		dutyCode=ClaimInfoGrid.getRowColData(tSel-1,3);
		caseNo=ClaimInfoGrid.getRowColData(tSel-1,4);
		if(tSel==0||tSel==null)
		{
			myAlert(""+"请选择险种保单信息！"+"");
			return false;
		}
		//tsql ="select state from lcreinsurtask where uwno=(select max(uwno) from lcreinsurtask where polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+caseNo+"' and AuditType='04') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+caseNo+"' and AuditType='04'";
		var mySql111=new SqlClass();
			mySql111.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
			mySql111.setSqlId("LRReInsureAnswerInputSql111");//指定使用的Sql的id
			mySql111.addSubPara(polNo);//指定传入的参数
			mySql111.addSubPara(dutyCode);//指定传入的参数
			mySql111.addSubPara(caseNo);//指定传入的参数
			mySql111.addSubPara(polNo);//指定传入的参数		
			mySql111.addSubPara(dutyCode);//指定传入的参数
			mySql111.addSubPara(caseNo);//指定传入的参数
	    	tsql=mySql111.getString();
		arr=easyExecSql(tsql);	
		if(arr=='01'||arr=='02'){
	    myAlert(""+"该任务为已回复状态，不能再回复核保申请!"+"");
	     return false;
		} 
	}
  var tRemark = fm.Remark.value; //再保回复意见
  
  ReInsureUpload();
}

function ReInsureUpload() {
  var i = 0;
  var tImportFile = fmImport.all('FileName').value;
  if ( tImportFile.indexOf("\\")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("\\")+1);
  if ( tImportFile.indexOf("/")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("/")+1);
  if ( tImportFile.indexOf("_")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("_"));
  if ( tImportFile.indexOf(".")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("."));
    var showStr=""+"正在上载数据……"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fmImport.action = "./ReInsuUpLodeSave.jsp"; 
    fmImport.submit();
}

function DownLoad(){
	 tSel = ReInsureAuditGrid.getSelNo();
	 if(tSel==0||tSel==null){
			myAlert(""+"请先选择再保审核任务信息！"+"");
			return false;
	 }
	 
   var FilePath = ReInsureAuditGrid.getRowColData(tSel - 1, 8);  
   if (FilePath==""||FilePath==null){
	   myAlert(""+"没有附件"+","+"不能进行下载操作！"+"");
	   return false;
   }   
   
   //var showStr="正在下载数据……";
   fmImport.action = "./DownLoadSave.jsp?FilePath="+FilePath;
   fmImport.submit();
}
//保存回复信息
function UpLodeReInsure(FilePath,FileName){
	var checkFlag;
	var polNo;
	var dutyCode;
	var edorNo;
	var edorType;
	var caseNo;
	var remark;
	var opeFlag=fm.AuditType.value;
	if(opeFlag=="04"){ 
		checkFlag=ClaimInfoGrid.getSelNo();
		polNo=ClaimInfoGrid.getRowColData(checkFlag - 1,7); 
		dutyCode=ClaimInfoGrid.getRowColData(checkFlag - 1,3); 
		caseNo=ClaimInfoGrid.getRowColData(checkFlag - 1,4); 
	}
	if(opeFlag=="01"){ 
		checkFlag=RiskInfoGrid.getSelNo();
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,7);
	}
	if(opeFlag=="03"){
		checkFlag=EdorInfoGrid.getSelNo();
		dutyCode=EdorInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = EdorInfoGrid.getRowColData(checkFlag - 1,9);
		edorNo= EdorInfoGrid.getRowColData(checkFlag - 1,4);
		edorType=EdorInfoGrid.getRowColData(checkFlag - 1,5);
	}
	if(InputFlag=="0"){
		fm.Remark.value="";
	}
	remark=fm.Remark.value;
	fm.action = "./SendReInsureChk.jsp?OpeFlag="+opeFlag+"&FilePath="+FilePath+"&FileName="+FileName+"&PolNo="+polNo+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&DutyCode="+dutyCode+"&RemarkInfo="+remark;
	fm.submit();
}

function ReInsureOver(){
	for (i=0; i<RiskInfoGrid.mulLineCount; i++){
    if (RiskInfoGrid.getSelNo(i)){
      checkFlag = RiskInfoGrid.getSelNo();
      break;
    }
  }
  var	State = RiskInfoGrid.getRowColData(checkFlag - 1, 6);
  if (State!=''+"已回复"+''){
  	myAlert(""+"该险种处于"+""+State+""+"状态不能进行办结"+"");
  }
}
 		
function afterSubmit(FlagStr, content ){ 
  showInfo.close();
  if (FlagStr == "Fail" ){             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    location.reload(true);
  }
}
   
function clearData(){ 
	if(InputFlag=="0"){
		fm.Remark.value=""; 
	}
	InputFlag="1"; 
}

function showAnswerIdea()
{
	//QueryAnswerIdea();
	var checkFlag=ReInsureAuditGrid.getSelNo();
	fmInfo.SendAnswerRemark.value= ReInsureAuditGrid.getRowColData(checkFlag - 1,6);
	
}

function showTaskInfo(){
	fmInfo.SendAnswerRemark.value="";
	
	if(fmInfo.OpeFlag.value=="1"){
		fm.AuditType.value="01";
	}
	var opeFlag=fm.AuditType.value;
	if(opeFlag=="01"){
		QueryReInsureAudit();
	}
	if(opeFlag=="03"){
		QueryEdorAudit();
	}
	if(opeFlag=="04"){
		QueryClaimAudit();
	}
}

/**
*查询新单任务发起与回复信息
*/
function  QueryReInsureAudit(){
	var checkFlag=RiskInfoGrid.getSelNo();
	var  polNo= RiskInfoGrid.getRowColData(checkFlag - 1,7);
	var  dutyCode= RiskInfoGrid.getRowColData(checkFlag - 1,3);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'核保发送'"
	+ " from LCReInsurUWIdea a where a.AuditType='01' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'再保回复'"
	+ " from LCReInsurIdea a where a.AuditType='01' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"') order by uwno desc "
	;	
	*/
	var mySql112=new SqlClass();
		mySql112.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
		mySql112.setSqlId("LRReInsureAnswerInputSql112");//指定使用的Sql的id
		mySql112.addSubPara(polNo);//指定传入的参数
		mySql112.addSubPara(dutyCode);//指定传入的参数	
		mySql112.addSubPara(polNo);//指定传入的参数
		mySql112.addSubPara(dutyCode);//指定传入的参数		
	    mSql=mySql112.getString();
	turnPage1.queryModal(mSql,ReInsureAuditGrid);
}
 
function QueryEdorAudit(){
	var checkFlag=EdorInfoGrid.getSelNo();
	var polNo= EdorInfoGrid.getRowColData(checkFlag - 1,9);
	var dutyCode= EdorInfoGrid.getRowColData(checkFlag - 1,3);
	var edorNo= EdorInfoGrid.getRowColData(checkFlag - 1,4);
	var edorType=EdorInfoGrid.getRowColData(checkFlag - 1,5);
	
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.UWIdea ,a.ModifyDate,a.AdjunctPath,'核保发送' "
	+ " from LCReInsurUWIdea a where a.AuditType='03' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"'"
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'再保回复' "
	+ " from LCReInsurIdea a where a.AuditType='03' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"') order by uwno desc "
	;
	*/
	var mySql113=new SqlClass();
		mySql113.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
		mySql113.setSqlId("LRReInsureAnswerInputSql113");//指定使用的Sql的id
		mySql113.addSubPara(polNo);//指定传入的参数
		mySql113.addSubPara(dutyCode);//指定传入的参数	
		mySql113.addSubPara(edorNo);//指定传入的参数
		mySql113.addSubPara(edorType);//指定传入的参数		
		mySql113.addSubPara(polNo);//指定传入的参数
		mySql113.addSubPara(dutyCode);//指定传入的参数
		mySql113.addSubPara(edorNo);//指定传入的参数	
		mySql113.addSubPara(edorType);//指定传入的参数
	    mSql=mySql113.getString();
	
	turnPage1.queryModal(mSql,ReInsureAuditGrid);
}

function QueryClaimAudit(){
	var checkFlag=ClaimInfoGrid.getSelNo();
	var polNo= ClaimInfoGrid.getRowColData(checkFlag - 1,7);
	var dutyCode= ClaimInfoGrid.getRowColData(checkFlag - 1,3);
	var caseNo= ClaimInfoGrid.getRowColData(checkFlag - 1,4);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.UWIdea,a.ModifyDate,a.AdjunctPath,'核保发送' "
	+ " from LCReInsurUWIdea a where a.AuditType='04' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+caseNo+"'"
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'再保回复' "
	+ " from LCReInsurIdea a where a.AuditType='04' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+caseNo+"') order by uwno desc "
	;
	*/
	var mySql114=new SqlClass();
		mySql114.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
		mySql114.setSqlId("LRReInsureAnswerInputSql114");//指定使用的Sql的id
		mySql114.addSubPara(polNo);//指定传入的参数
		mySql114.addSubPara(dutyCode);//指定传入的参数	
		mySql114.addSubPara(caseNo);//指定传入的参数	
		mySql114.addSubPara(polNo);//指定传入的参数		
		mySql114.addSubPara(dutyCode);//指定传入的参数
		mySql114.addSubPara(caseNo);//指定传入的参数
	    mSql=mySql114.getString();
	
	turnPage1.queryModal(mSql,ReInsureAuditGrid);
}

function afterCodeSelect(cCodeName,Field){
	//选择了处理
	if( cCodeName == "audittype"){
		if(fm.AuditType.value=="01"){
			fmImport.ConclusionButton.style.display="";
			QueryRiskInfo();
		}
		if(fm.AuditType.value=="03"){
			fmImport.ConclusionButton.style.display="none";
			QueryEdorInfo();
		}
		if(fm.AuditType.value=="04"){
			fmImport.ConclusionButton.style.display="none";
			QueryClaimInfo();
		}
		turnPage.queryModal(strSQL,PayoutGrid);
	}
}

	function TempCessConclusion(){
		var tsql;
		var arr;
		if(fm.AuditType.value==""||fm.AuditType.value==null){
			myAlert(""+"请选择再保审核类型！"+"");
			return false;
		}
		if(fm.AuditType.value=="01"){
			tSel = RiskInfoGrid.getSelNo();
			polNo=RiskInfoGrid.getRowColData(tSel-1,7);
			dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
			if(tSel==0||tSel==null)
			{
				myAlert(""+"请选择险种保单信息！"+"");
				return false;
			}
			//tsql="select appflag from lcpol where proposalno='"+polNo+"'";
			var mySql115=new SqlClass();
				mySql115.setResourceName("reinsure.LRReInsureAnswerInputSql"); //指定使用的properties文件名
				mySql115.setSqlId("LRReInsureAnswerInputSql115");//指定使用的Sql的id
				mySql115.addSubPara(polNo);//指定传入的参数
	    		tsql=mySql115.getString();
			
			
			arr=easyExecSql(tsql);
			if(arr[0]==null){
				myAlert(""+"没有此保单信息"+"");
				return false;
			}
			if(arr[0]!='1'){
				myAlert(""+"未签单保单不能下临分结论"+"");
				return false;
			}
			fm.action = "./CessTempConclusionSave.jsp?PolNo="+polNo+"&DutyCode="+dutyCode;
			fm.submit();
		}
	}
	
	function initTempCess(){
		if(fmInfo.OpeFlag.value=='1'){
  		divTempCessInter.style.display='none';
  		QueryRiskInfo();
  	}else{
  		divTempCessInter.style.display='';
  	}
	}
