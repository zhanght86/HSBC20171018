	/*********************************************************************
 *  �������ƣ�LRReInsureAnswerInput.js
 *  �����ܣ��ٱ��ظ�
 *  �������ڣ�2006-11-30 
 *  ������  ��zhang bin
 *  ����ֵ��  ��
 *  ���¼�¼��  ������    ��������     ����ԭ��/����
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
	*��ѯ�˱���������
	*/
 function QueryRiskInfo()
 { 	
 		divRiskInfo.style.display="";
 		divEdorInfo.style.display="none";
 		divClaimInfo.style.display="none";
 		
 	 /**
	  mSQL =" select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)), a.dutycode, (select amnt from lcduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode)) , (select distinct prem from lcduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode)), (case state when '00' then '���ظ�' when '01' then '�ѻظ�' when '02' then '���' end),a.polno,a.uwno,a.state  "
	  +" from lcreinsurtask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from lcreinsurtask where AuditType='01' group by polno,dutycode,auditcode,AuditAffixCode) b "
 		+" where a.polno=b.pno and a.dutycode=b.dcode and a.auditcode=b.acode and a.auditaffixcode=b.aacode and a.uwno=b.maxno and a.audittype='01'"
 		;
 		*/
 		var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRReInsureAnswerInputSql100");//ָ��ʹ�õ�Sql��id
	    mySql100.addSubPara("1");
	    mSQL=mySql100.getString();
 		
 		if(fmInfo.OpeFlag.value=="1"){
 			//mSQL=mSQL+"  and a.polno='"+fmInfo.ProposalNo.value+"' and a.dutycode='"+fmInfo.DutyCode.value+"' ";
 			var mySql101=new SqlClass();
				mySql101.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql101.setSqlId("LRReInsureAnswerInputSql101");//ָ��ʹ�õ�Sql��id
				mySql101.addSubPara(fmInfo.ProposalNo.value);//ָ������Ĳ���
				mySql101.addSubPara(fmInfo.DutyCode.value);//ָ������Ĳ���
	    		mSQL=mySql101.getString();
 			
 			
 		}
 		//mSQL = mSQL + " order by a.ModifyDate desc ";
 		/**
 		var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql102.setSqlId("LRReInsureAnswerInputSql102");//ָ��ʹ�õ�Sql��id
			mySql102.addSubPara(mSQL);//ָ������Ĳ���
	    	mSQL=mySql102.getString();
	    	*/
		turnPage1.queryModal(mSQL, RiskInfoGrid);
 }

function QueryEdorInfo(){
 		divRiskInfo.style.display="none";
 		divEdorInfo.style.display="";
 		divClaimInfo.style.display="none";
 		/**
		mSQL="select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)), a.dutycode, a.auditcode ,AuditAffixCode ,(select amnt from lpduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(edorno)=trim(auditcode) and trim(edortype)=trim(AuditAffixCode)), (select distinct prem from lpduty where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(edorno)=trim(auditcode) and trim(edortype)=trim(AuditAffixCode)),(case state when '00' then '���ظ�' when '01' then '�ѻظ�' when '02' then '���' end),a.polno,a.uwno,a.state "
 		+ " from lcreinsurtask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from lcreinsurtask where AuditType='03' group by polno,dutycode,auditcode,AuditAffixCode) b "
 		+ " where a.polno=b.pno and a.dutycode=b.dcode and a.auditcode=b.acode and a.auditaffixcode=b.aacode and a.uwno=b.maxno "
		+ " and AuditType='03' order by ModifyDate desc "
		*/
		var mySql103=new SqlClass();
			mySql103.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql103.setSqlId("LRReInsureAnswerInputSql103");//ָ��ʹ�õ�Sql��id
	    	mySql103.addSubPara("1");
	    	mSQL=mySql103.getString();
		turnPage2.queryModal(mSQL,EdorInfoGrid);
}

function QueryClaimInfo(){
	divRiskInfo.style.display="none";
 	divEdorInfo.style.display="none";
 	divClaimInfo.style.display="";
 	/**
	mSQL="select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.PolNo)),a.dutycode,a.auditcode ,(select sum(realpay) from llclaim where trim(caseno)=trim(a.auditcode) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) group by caseno,polno,dutycode),(case state when '00' then '���ظ�' when '01' then '�ѻظ�' when '02' then '���' end),a.polno,a.uwno,a.state "
	+ " from lcreinsurtask a, (select polno pno,dutycode dcode,auditcode acode,AuditAffixCode aacode,max(uwno) maxno from lcreinsurtask where AuditType='04' group by polno,dutycode,auditcode,AuditAffixCode) b "
 	+ " where a.polno=b.pno and a.dutycode=b.dcode and a.auditcode=b.acode and a.auditaffixcode=b.aacode and a.uwno=b.maxno "
	+ " and AuditType='04' order by ModifyDate desc "
	;
	*/
	var mySql104=new SqlClass();
		mySql104.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql104.setSqlId("LRReInsureAnswerInputSql104");//ָ��ʹ�õ�Sql��id
	    mySql104.addSubPara("1");
	    mSQL=mySql104.getString();
	turnPage3.queryModal(mSQL,ClaimInfoGrid);
}
 /**
	*��ѯ���ͻظ���Ϣ
	*/
 function QueryAnswerIdea()
 {
 	
 	var tSel=ReInsureAuditGrid.getSelNo();
 	var saFlag=ReInsureAuditGrid.getRowColData(tSel-1,9);
 	if(saFlag=="1") //������˱����͡�
 	{
	 	/**
	 	var mSql="select uwidea from lcreinsuruwidea where polno='"+ReInsureAuditGrid.getRowColData(tSel-1,8)+"'"
	 	+" and UWNo="+ReInsureAuditGrid.getRowColData(tSel-1,3)+""
	 	*/
	 	var mySql105=new SqlClass();
			mySql105.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql105.setSqlId("LRReInsureAnswerInputSql105");//ָ��ʹ�õ�Sql��id
			mySql105.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,8));//ָ������Ĳ���
			mySql105.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
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
			mySql106.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql106.setSqlId("LRReInsureAnswerInputSql106");//ָ��ʹ�õ�Sql��id
			mySql106.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,8));//ָ������Ĳ���
			mySql106.addSubPara(ReInsureAuditGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
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
	  var showStr=""+"���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
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
		mySql107.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql107.setSqlId("LRReInsureAnswerInputSql107");//ָ��ʹ�õ�Sql��id
		mySql107.addSubPara(tContNo);//ָ������Ĳ���
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
			myAlert(""+"��ѡ���ٱ�������ͣ�"+"");
			return false;
	}
	if(fm.AuditType.value=="01"){
		tSel = RiskInfoGrid.getSelNo();
		polNo=RiskInfoGrid.getRowColData(tSel-1,7);
		dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
		if(tSel==0||tSel==null)
		{
			myAlert(""+"��ѡ�����ֱ�����Ϣ��"+"");
			return false;
		}
		//tSql="select appflag from lcpol where proposalno='"+polNo+"'";
		var mySql108=new SqlClass();
			mySql108.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql108.setSqlId("LRReInsureAnswerInputSql108");//ָ��ʹ�õ�Sql��id
			mySql108.addSubPara(polNo);//ָ������Ĳ���
	    	tSql=mySql108.getString();
		
		
		arr=easyExecSql(tSql);
		if(arr==null){
			myAlert(""+"û�в�ѯ��������Ϣ"+"");
			return false;
		}else{
			if(arr[0]=="1"){
				myAlert(""+"������ǩ���������ٱ��ظ�"+"");
				return false;
			}
		}
		//tsql ="select state from lcreinsurtask where uwno=(select max(uwno) from lcreinsurtask where polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditType='01') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditType='01'";
		var mySql109=new SqlClass();
			mySql109.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql109.setSqlId("LRReInsureAnswerInputSql109");//ָ��ʹ�õ�Sql��id
			mySql109.addSubPara(polNo);//ָ������Ĳ���
			mySql109.addSubPara(dutyCode);//ָ������Ĳ���
			mySql109.addSubPara(polNo);//ָ������Ĳ���
			mySql109.addSubPara(dutyCode);//ָ������Ĳ���
	    	tsql=mySql109.getString();
		
		arr=easyExecSql(tsql);	
		if(arr=='01'||arr=='02') //������ѻظ�״̬
		{
	    myAlert(""+"������Ϊ�ѻظ�״̬�������ٻظ��˱�����!"+"");
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
			myAlert(""+"��ѡ�����ֱ�����Ϣ��"+"");
			return false;
		}
		//tsql ="select state from lcreinsurtask where uwno=(select max(uwno) from lcreinsurtask where polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+edorNo+"' and AuditAffixCode='"+edorType+"'  and AuditType='03') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+edorNo+"' and AuditAffixCode='"+edorType+"' and AuditType='03'";
		var mySql110=new SqlClass();
			mySql110.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql110.setSqlId("LRReInsureAnswerInputSql110");//ָ��ʹ�õ�Sql��id
			mySql110.addSubPara(polNo);//ָ������Ĳ���
			mySql110.addSubPara(dutyCode);//ָ������Ĳ���
			mySql110.addSubPara(edorNo);//ָ������Ĳ���
			mySql110.addSubPara(edorType);//ָ������Ĳ���		
			mySql110.addSubPara(polNo);//ָ������Ĳ���
			mySql110.addSubPara(dutyCode);//ָ������Ĳ���
			mySql110.addSubPara(edorNo);//ָ������Ĳ���		
			mySql110.addSubPara(edorType);//ָ������Ĳ���
	    	tsql=mySql110.getString();
		
		arr=easyExecSql(tsql);	
		if(arr=='01'||arr=='02'){
	    myAlert(""+"������Ϊ�ѻظ�״̬�������ٻظ��˱�����!"+"");
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
			myAlert(""+"��ѡ�����ֱ�����Ϣ��"+"");
			return false;
		}
		//tsql ="select state from lcreinsurtask where uwno=(select max(uwno) from lcreinsurtask where polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+caseNo+"' and AuditType='04') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+caseNo+"' and AuditType='04'";
		var mySql111=new SqlClass();
			mySql111.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql111.setSqlId("LRReInsureAnswerInputSql111");//ָ��ʹ�õ�Sql��id
			mySql111.addSubPara(polNo);//ָ������Ĳ���
			mySql111.addSubPara(dutyCode);//ָ������Ĳ���
			mySql111.addSubPara(caseNo);//ָ������Ĳ���
			mySql111.addSubPara(polNo);//ָ������Ĳ���		
			mySql111.addSubPara(dutyCode);//ָ������Ĳ���
			mySql111.addSubPara(caseNo);//ָ������Ĳ���
	    	tsql=mySql111.getString();
		arr=easyExecSql(tsql);	
		if(arr=='01'||arr=='02'){
	    myAlert(""+"������Ϊ�ѻظ�״̬�������ٻظ��˱�����!"+"");
	     return false;
		} 
	}
  var tRemark = fm.Remark.value; //�ٱ��ظ����
  
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
    var showStr=""+"�����������ݡ���"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fmImport.action = "./ReInsuUpLodeSave.jsp"; 
    fmImport.submit();
}

function DownLoad(){
	 tSel = ReInsureAuditGrid.getSelNo();
	 if(tSel==0||tSel==null){
			myAlert(""+"����ѡ���ٱ����������Ϣ��"+"");
			return false;
	 }
	 
   var FilePath = ReInsureAuditGrid.getRowColData(tSel - 1, 8);  
   if (FilePath==""||FilePath==null){
	   myAlert(""+"û�и���"+","+"���ܽ������ز�����"+"");
	   return false;
   }   
   
   //var showStr="�����������ݡ���";
   fmImport.action = "./DownLoadSave.jsp?FilePath="+FilePath;
   fmImport.submit();
}
//����ظ���Ϣ
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
  if (State!=''+"�ѻظ�"+''){
  	myAlert(""+"�����ִ���"+""+State+""+"״̬���ܽ��а��"+"");
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
*��ѯ�µ���������ظ���Ϣ
*/
function  QueryReInsureAudit(){
	var checkFlag=RiskInfoGrid.getSelNo();
	var  polNo= RiskInfoGrid.getRowColData(checkFlag - 1,7);
	var  dutyCode= RiskInfoGrid.getRowColData(checkFlag - 1,3);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'�˱�����'"
	+ " from LCReInsurUWIdea a where a.AuditType='01' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'�ٱ��ظ�'"
	+ " from LCReInsurIdea a where a.AuditType='01' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"') order by uwno desc "
	;	
	*/
	var mySql112=new SqlClass();
		mySql112.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql112.setSqlId("LRReInsureAnswerInputSql112");//ָ��ʹ�õ�Sql��id
		mySql112.addSubPara(polNo);//ָ������Ĳ���
		mySql112.addSubPara(dutyCode);//ָ������Ĳ���	
		mySql112.addSubPara(polNo);//ָ������Ĳ���
		mySql112.addSubPara(dutyCode);//ָ������Ĳ���		
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
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.UWIdea ,a.ModifyDate,a.AdjunctPath,'�˱�����' "
	+ " from LCReInsurUWIdea a where a.AuditType='03' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"'"
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'�ٱ��ظ�' "
	+ " from LCReInsurIdea a where a.AuditType='03' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"') order by uwno desc "
	;
	*/
	var mySql113=new SqlClass();
		mySql113.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql113.setSqlId("LRReInsureAnswerInputSql113");//ָ��ʹ�õ�Sql��id
		mySql113.addSubPara(polNo);//ָ������Ĳ���
		mySql113.addSubPara(dutyCode);//ָ������Ĳ���	
		mySql113.addSubPara(edorNo);//ָ������Ĳ���
		mySql113.addSubPara(edorType);//ָ������Ĳ���		
		mySql113.addSubPara(polNo);//ָ������Ĳ���
		mySql113.addSubPara(dutyCode);//ָ������Ĳ���
		mySql113.addSubPara(edorNo);//ָ������Ĳ���	
		mySql113.addSubPara(edorType);//ָ������Ĳ���
	    mSql=mySql113.getString();
	
	turnPage1.queryModal(mSql,ReInsureAuditGrid);
}

function QueryClaimAudit(){
	var checkFlag=ClaimInfoGrid.getSelNo();
	var polNo= ClaimInfoGrid.getRowColData(checkFlag - 1,7);
	var dutyCode= ClaimInfoGrid.getRowColData(checkFlag - 1,3);
	var caseNo= ClaimInfoGrid.getRowColData(checkFlag - 1,4);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.UWIdea,a.ModifyDate,a.AdjunctPath,'�˱�����' "
	+ " from LCReInsurUWIdea a where a.AuditType='04' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+caseNo+"'"
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'�ٱ��ظ�' "
	+ " from LCReInsurIdea a where a.AuditType='04' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+caseNo+"') order by uwno desc "
	;
	*/
	var mySql114=new SqlClass();
		mySql114.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql114.setSqlId("LRReInsureAnswerInputSql114");//ָ��ʹ�õ�Sql��id
		mySql114.addSubPara(polNo);//ָ������Ĳ���
		mySql114.addSubPara(dutyCode);//ָ������Ĳ���	
		mySql114.addSubPara(caseNo);//ָ������Ĳ���	
		mySql114.addSubPara(polNo);//ָ������Ĳ���		
		mySql114.addSubPara(dutyCode);//ָ������Ĳ���
		mySql114.addSubPara(caseNo);//ָ������Ĳ���
	    mSql=mySql114.getString();
	
	turnPage1.queryModal(mSql,ReInsureAuditGrid);
}

function afterCodeSelect(cCodeName,Field){
	//ѡ���˴���
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
			myAlert(""+"��ѡ���ٱ�������ͣ�"+"");
			return false;
		}
		if(fm.AuditType.value=="01"){
			tSel = RiskInfoGrid.getSelNo();
			polNo=RiskInfoGrid.getRowColData(tSel-1,7);
			dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
			if(tSel==0||tSel==null)
			{
				myAlert(""+"��ѡ�����ֱ�����Ϣ��"+"");
				return false;
			}
			//tsql="select appflag from lcpol where proposalno='"+polNo+"'";
			var mySql115=new SqlClass();
				mySql115.setResourceName("reinsure.LRReInsureAnswerInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql115.setSqlId("LRReInsureAnswerInputSql115");//ָ��ʹ�õ�Sql��id
				mySql115.addSubPara(polNo);//ָ������Ĳ���
	    		tsql=mySql115.getString();
			
			
			arr=easyExecSql(tsql);
			if(arr[0]==null){
				myAlert(""+"û�д˱�����Ϣ"+"");
				return false;
			}
			if(arr[0]!='1'){
				myAlert(""+"δǩ�������������ٷֽ���"+"");
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
