	/*********************************************************************
 *  �������ƣ�ReInsureInput.js
 *  �����ܣ��ٱ��˱�����ҳ��
 *  �������ڣ�2007-03-21
 *  ������  ��zhangbin
 *  ����ֵ��  ��
 *  ���¼�¼��  ������    ��������     ����ԭ��/����
 *********************************************************************
 */

var showInfo ;
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
/*********************************************************************
 *  ��ѯ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function assgnOperate(){
	myAlert(opeFlag);
	var opeFlag=fmImport.OpeFlag.value;
	if(opeFlag=="01"){
		QueryRiskInfo();
	}
	else if(opeFlag=="03"){
		QueryEdorInfo();
	}
	else if(opeFlag=="04"){
		QueryClaimInfo();
	}
	else{
		myAlert(""+"�Ƿ���������"+"");
		return false;
	}
}
function 	QueryRiskInfo(){
 divLCDuty1.style.display="";
 divLLClaim.style.display="none";
 var tContNo = fm.ContNo.value;
 //��ѯcontno�������ۼƷ������µ�
 /**
  mSQL ="select a.insuredname,a.riskcode,b.dutycode,a.Mult,b.Amnt,b.Prem,(select case (select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditType)=trim('01')) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditType)=trim('01')) when '00' then '���ظ�' when '01' then '�ѻظ�' when '02' then '���' end from dual),a.polno,'','',"
  +" (select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditType)=trim('01')) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditType)=trim('01')) from lcpol a ,lcduty b where trim(a.polno)=trim(b.polno) and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode)  "
	+" and a.contno ='"+fmImport.OpeData.value+"' " 
  ;
  */
  var mySql100=new SqlClass();
	  mySql100.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql100.setSqlId("ReInsureInputSql100");//ָ��ʹ�õ�Sql��id
	  mySql100.addSubPara(fmImport.OpeData.value);//ָ������Ĳ���
	  mSQL=mySql100.getString();
  
	turnPage1.queryModal(mSQL, RiskInfoGrid);
}
function 	QueryEdorInfo(){
	divLCDuty1.style.display="";
 	divLLClaim.style.display="none";
 	//��ѯedorno�������ۼƷ����ı�ȫ
 	/**
 	mSQL ="select a.insuredname,a.riskcode,b.dutycode,a.Mult,b.Amnt,b.Prem,(select case (select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) when '00' then '���ظ�' when '01' then '�ѻظ�' when '02' then '���' end from dual), a.polno,b.edorno,b.edortype,"
  + " (select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(b.dutycode) and trim(AuditCode)=trim(a.edorno) and trim(AuditAffixCode)=trim(a.edortype)) from lppol a ,lpduty b where a.polno=b.polno and a.edorno=b.edorno and a.edortype=b.edortype "
	+ " and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode) and a.edorno ='"+fmImport.OpeData.value+"' "
	;
	*/
	var mySql101=new SqlClass();
	  mySql101.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql101.setSqlId("ReInsureInputSql101");//ָ��ʹ�õ�Sql��id
	  mySql101.addSubPara(fmImport.OpeData.value);//ָ������Ĳ���
	  mSQL=mySql101.getString();
	turnPage1.queryModal(mSQL, RiskInfoGrid);
}

function 	QueryClaimInfo(){
	divLCDuty1.style.display="none";
 	divLLClaim.style.display="";
 	
 	var tContNo = fm.ContNo.value;
 	/**
  mSQL ="select (select insuredname from lcpol where polno=a.polno),a.riskcode,a.dutycode,sum(a.realpay),(select case (select state from RIUWTask where  uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(ClmNo)=trim(a.ClmNo) and trim(AuditType)=trim('04')) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(AuditType)=trim('04')) when '00' then '���ظ�' when '01' then '�ѻظ�' when '02' then '���' end from dual),"
  +" a.polno,a.ClmNo,(select state from RIUWTask where uwno= (select max(uwno) from RIUWTask where trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(ClmNo)=trim(a.ClmNo) and trim(AuditType)=trim('04')) and trim(polno)=trim(a.polno) and trim(dutycode)=trim(a.dutycode) and trim(AuditType)=trim('04'))"
  +" from llclaimdetail a where a.ClmNo='"+fmImport.OpeData.value+"' group by a.ClmNo,a.polno,a.riskcode,a.dutycode " 
  ;
  */
  var mySql102=new SqlClass();
	  mySql102.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql102.setSqlId("ReInsureInputSql102");//ָ��ʹ�õ�Sql��id
	  mySql102.addSubPara(fmImport.OpeData.value);//ָ������Ĳ���
	  mSQL=mySql102.getString();
	turnPage1.queryModal(mSQL,ClaimInfoGrid);
}

//��ʾ�ٱ�������� 
function  QueryReInsureAudit(){
	 var opeData = fmImport.OpeData.value;
	 var opeFlag = fmImport.OpeFlag.value;
	 var mSql;
	 if(opeFlag=="01"){
			//mSql = "select b.insuredname,b.riskcode,a.uwno,a.uwoperator,a.uwidea,a.makedate,a.adjunctpath from lcreinsuruwidea a ,lcpol b where a.polno=b.polno and b.contno='"+opeData+"' union select b.insuredname,b.riskcode,a.uwno,a.operator,a.uwidea,a.makedate,a.adjunctpath from lcreinsuridea a ,lcpol b where a.polno=b.polno and b.contno='"+opeData+"' order by uwno";	
			var mySql103=new SqlClass();
	 			mySql103.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  			mySql103.setSqlId("ReInsureInputSql103");//ָ��ʹ�õ�Sql��id
	  			mySql103.addSubPara(opeData);//ָ������Ĳ���
	  			mySql103.addSubPara(opeData);//ָ������Ĳ���
	  			mSql=mySql103.getString();
	 }
	 else if(opeFlag=="03"){
		/**
		  mSql = "select b.insuredname,b.riskcode,c.dutycode,a.uwno,a.uwoperator,a.uwidea,a.makedate,a.adjunctpath,'�˱�����' from lcreinsuruwidea a ,lppol b,lpduty c where trim(a.polno)=trim(b.polno) and trim(a.dutycode)=trim(c.dutycode) and  trim(b.polno)=trim(c.polno) and trim(b.edortype)=trim(c.edortype) and trim(b.edorno)=trim(c.edorno) and trim(a.AuditCode)=trim(b.edorno) and trim(a.AuditAffixCode)=trim(b.edortype) and b.edorno='"+opeData+"' and a.AuditType='03' "
		  + " union select b.insuredname,b.riskcode,c.dutycode,a.uwno,a.ReInsurer,a.uwidea,a.makedate,a.adjunctpath,'�ٱ��ظ�' from lcreinsuridea a ,lppol b,lpduty c where trim(a.polno)=trim(b.polno) and trim(a.dutycode)=trim(c.dutycode) and  trim(b.polno)=trim(c.polno) and trim(b.edortype)=trim(c.edortype) and trim(b.edorno)=trim(c.edorno) and trim(a.AuditCode)=trim(b.edorno) and trim(a.AuditAffixCode)=trim(b.edortype) and b.edorno='"+opeData+"' and a.AuditType='03' ";	
	*/
	var mySql104=new SqlClass();
	 	mySql104.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql104.setSqlId("ReInsureInputSql104");//ָ��ʹ�õ�Sql��id
	  	mySql104.addSubPara(opeData);//ָ������Ĳ���
	  	mySql104.addSubPara(opeData);//ָ������Ĳ���
	  	mSql=mySql104.getString();
	 }   
	 else if(opeFlag=="04"){
			//mSql = "select b.insuredname,b.riskcode,a.uwno,a.uwoperator,a.uwidea,a.makedate,a.adjunctpath from lcreinsuruwidea a ,lcpol b where a.polno=b.polno and b.contno='"+opeData+"' union select b.insuredname,b.riskcode,a.uwno,a.operator,a.uwidea,a.makedate,a.adjunctpath from lcreinsuridea a ,lcpol b where a.polno=b.polno and b.contno='"+opeData+"' order by uwno";	
	 		var mySql105=new SqlClass();
	 			mySql105.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  			mySql105.setSqlId("ReInsureInputSql105");//ָ��ʹ�õ�Sql��id
	  			mySql105.addSubPara(opeData);//ָ������Ĳ���
	  			mySql105.addSubPara(opeData);//ָ������Ĳ���
	  			mSql=mySql105.getString();
	 }   
	 turnPage2.queryModal(mSql,ReInsureAuditGrid); 
} 
/**
*�����ٱ�����
*/
function AutoReInsure(){
	var showStr=""+"���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	var polNo;
	var dutyCode;
	var edorNo;
	var edorType;
	var caseNo;
	fm.RunRuleFlag.value="1";
	var tOpeData=fmImport.OpeData.value;
	var tOpeFlag=fmImport.OpeFlag.value;
	var tSel="";
	if(tOpeFlag=="01"){
		tSel=RiskInfoGrid.getSelNo();
		if(tSel==0){
			myAlert(""+"��ѡ��������Ϣ"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(tSel - 1,3);
		polNo = RiskInfoGrid.getRowColData(tSel - 1,8);
	}
	if(tOpeFlag=="03"){
		tSel=RiskInfoGrid.getSelNo();
		if(tSel==0){
			myAlert(""+"��ѡ��������Ϣ"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
		polNo 	= RiskInfoGrid.getRowColData(tSel-1,8);
		edorNo	= RiskInfoGrid.getRowColData(tSel-1,9);
		edorType=RiskInfoGrid.getRowColData(tSel-1,10);
	}
	if(tOpeFlag=="04"){
		tSel=ClaimInfoGrid.getSelNo();
		if(tSel==0){
			myAlert(""+"��ѡ��������Ϣ"+"");
			return false;
		}
		dutyCode=ClaimInfoGrid.getRowColData(tSel-1,3);
		polNo		=ClaimInfoGrid.getRowColData(tSel - 1,6); 
		caseNo	=ClaimInfoGrid.getRowColData(tSel - 1,7); 
	}
	fm.action = "./AutoReInsureChk.jsp?OpeData="+tOpeData+"&OpeFlag="+tOpeFlag+"&PolNo="+polNo+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&DutyCode="+dutyCode;
	fm.submit();
}

function SendUWReInsure(){
	var checkFlag="";
	var	polNo = "";
	var dutyCode="";
	var tsql="";
	if(fmImport.OpeFlag.value=="01"){
		checkFlag=RiskInfoGrid.getSelNo();
		if(checkFlag==null||checkFlag==""){
			myAlert(""+"��ѡ��������Ϣ"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,8);
		/**
		tsql ="select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where polno='" 
		+polNo + "' and dutycode='"+dutyCode+"'  and AuditType='01') and polno='" +polNo + "' and dutycode='"+dutyCode+"' and AuditType='01'";
	*/
		var mySql106=new SqlClass();
	 		mySql106.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  		mySql106.setSqlId("ReInsureInputSql106");//ָ��ʹ�õ�Sql��id
	  		mySql106.addSubPara(polNo);//ָ������Ĳ���
	  		mySql106.addSubPara(dutyCode);//ָ������Ĳ���
	  		mySql106.addSubPara(polNo);//ָ������Ĳ���
	  		mySql106.addSubPara(dutyCode);//ָ������Ĳ���
	  		tsql=mySql106.getString();
	}
	if(fmImport.OpeFlag.value=="04"){
		checkFlag=ClaimInfoGrid.getSelNo();
		if(checkFlag==null||checkFlag==""){
			myAlert(""+"��ѡ��������Ϣ"+"");
			return false;
		}
		polNo=ClaimInfoGrid.getRowColData(checkFlag - 1,6); 
		dutyCode=ClaimInfoGrid.getRowColData(checkFlag - 1,3); 
		caseNo=ClaimInfoGrid.getRowColData(checkFlag - 1,7); 
		/**
		tsql ="select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where polno='" 
		+polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+caseNo+"' and AuditType='04') and polno='" +polNo 
		+ "' and dutycode='"+dutyCode+"' and AuditCode='"+caseNo+"' and AuditType='04'"
		;
		*/
		var mySql107=new SqlClass();
	 		mySql107.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  		mySql107.setSqlId("ReInsureInputSql107");//ָ��ʹ�õ�Sql��id
	  		mySql107.addSubPara(polNo);//ָ������Ĳ���
	  		mySql107.addSubPara(dutyCode);//ָ������Ĳ���
	  		mySql107.addSubPara(caseNo);//ָ������Ĳ���
	  		mySql107.addSubPara(polNo);//ָ������Ĳ���
	  		mySql107.addSubPara(dutyCode);//ָ������Ĳ���
	  		mySql107.addSubPara(caseNo);//ָ������Ĳ���
	  		tsql=mySql107.getString();
	}
	if(fmImport.OpeFlag.value=="03"){
		checkFlag=RiskInfoGrid.getSelNo();
		if(checkFlag==null||checkFlag==""){
			myAlert(""+"��ѡ��������Ϣ"+"");
			return false;
		}
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,8);
		edorNo= RiskInfoGrid.getRowColData(checkFlag - 1,9);
		edorType=RiskInfoGrid.getRowColData(checkFlag - 1,10);
		/**
		tsql ="select state from RIUWTask where uwno=(select max(uwno) from RIUWTask where polno='" 
		+ polNo + "' and dutycode='"+dutyCode+"' and AuditCode='"+edorNo+"' and AuditAffixCode='"+edorType+"' and AuditType='03') and polno='" +polNo 
		+ "' and dutycode='"+dutyCode+"' and AuditCode='"+edorNo+"' and AuditAffixCode='"+edorType+"' and AuditType='03'"
		;
		*/
		var mySql108=new SqlClass();
	 		mySql108.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  		mySql108.setSqlId("ReInsureInputSql108");//ָ��ʹ�õ�Sql��id
	  		mySql108.addSubPara(polNo);//ָ������Ĳ���
	  		mySql108.addSubPara(dutyCode);//ָ������Ĳ���
	  		mySql108.addSubPara(edorNo);//ָ������Ĳ���
	  		mySql108.addSubPara(edorType);//ָ������Ĳ���
	  		mySql108.addSubPara(polNo);//ָ������Ĳ���
	  		mySql108.addSubPara(dutyCode);//ָ������Ĳ���
	  		mySql108.addSubPara(edorNo);//ָ������Ĳ���
	  		mySql108.addSubPara(edorType);//ָ������Ĳ���
	  		tsql=mySql108.getString();
	}
	ImportFile = fmImport.all('FileName').value;	
  var tRemark = fm.Remark.value;
  
  //alert(PolNo);
	var arr=easyExecSql(tsql);	
	if(arr=='00'||arr=='01'){
    myAlert(""+"������δ��ᣬ���ܷ����ٱ����!"+"");
    return false;
	}
  if (checkFlag){ 
    ReInsureUpload();
	}
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
    fmImport.action = "./UpLodeSave.jsp";
    fmImport.submit();
}

function DownLoad(){
	var checkFlag;
	checkFlag = ReInsureAuditGrid.getSelNo();
	if(checkFlag==null||checkFlag==""){
		myAlert(""+"��ѡ���������"+"");
		return false;
	}
  var FilePath = ReInsureAuditGrid.getRowColData(checkFlag - 1,8);
  if (FilePath==""){
  myAlert(""+"û�и���"+","+"���ܽ������ز�����"+"")	
  return false;
  }   
  //var showStr="�����������ݡ���";
//var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fmInfo.action = "./DownLoadSave.jsp?FilePath="+FilePath;
  fmInfo.submit();
}
/**
* ������
*/
function ReInsureOver(){
	var opeFlag=fmImport.OpeFlag.value;
	var checkFlag;
	var polNo;
	var dutyCode;
	var edorNo;
	var edorType;
	var caseNo;
	var	State;
	
	if(opeFlag=="01"){
		checkFlag = RiskInfoGrid.getSelNo();
		State = RiskInfoGrid.getRowColData(checkFlag - 1, 11);
		if (State!="01"){
	  	myAlert(""+"���������δ�ظ�"+","+"���ܽ��а��"+"");
	  	return false;
	  }
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,8);
	}
	if(opeFlag=="03"){
		checkFlag = RiskInfoGrid.getSelNo();
		State = RiskInfoGrid.getRowColData(checkFlag - 1, 11);
		if (State!='01'){
	  	myAlert(""+"���������δ�ظ�"+","+"���ܽ��а��"+"");
	  	return false;
	  }
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,8);
		edorNo= RiskInfoGrid.getRowColData(checkFlag - 1,9);
		edorType=RiskInfoGrid.getRowColData(checkFlag - 1,10);
	}
	if(opeFlag=="04"){
		checkFlag = ClaimInfoGrid.getSelNo();
		State = ClaimInfoGrid.getRowColData(checkFlag - 1,8);
		if (State!='01'){
	  	myAlert(""+"���������δ�ظ�"+","+"���ܽ��а��"+"");
	  	return false;
	  }
		polNo=ClaimInfoGrid.getRowColData(checkFlag - 1,6); 
		dutyCode=ClaimInfoGrid.getRowColData(checkFlag - 1,3); 
		caseNo=ClaimInfoGrid.getRowColData(checkFlag - 1,7); 
	}
	var overFlag="TRUE"; 
	fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeFlag="+opeFlag+"&PolNo="+polNo+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&DutyCode="+dutyCode;
	fm.submit();
}
 		
function afterSubmit( FlagStr, content )
{
	if(showInfo!=null){
  	showInfo.close();
	}
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
    if(fm.RunRuleFlag.value!="1"){
    	assgnOperate();
    }else{
    	fm.RunRuleFlag.value="";
    	//var tSel = RiskInfoGrid.getSelNo();
    	//var txt=" ������"+ReInsureGrid.getRowColData(tSel-1,1)+" �ı�������: "+ReInsureGrid.getRowColData(tSel-1,3);
    	//for(var i=0;i<ReInsureGrid.mulLineCount;i++){
    	//	txt=txt+" "+ReInsureGrid.getRowColData(tSel-1,1)+"";
    	//}
    }
  }
}

function UpLodeReInsure(FilePath,FileName){
	var checkFlag;
	var polNo;
	var dutyCode;
	var edorNo;
	var edorType;
	var caseNo;
	var opeFlag=fmImport.OpeFlag.value;
	if(opeFlag=="04"){ 
		checkFlag=ClaimInfoGrid.getSelNo();
		polNo=ClaimInfoGrid.getRowColData(checkFlag - 1,6); 
		dutyCode=ClaimInfoGrid.getRowColData(checkFlag - 1,3); 
		caseNo=ClaimInfoGrid.getRowColData(checkFlag - 1,7); 
	}
	if(opeFlag=="01"||opeFlag=="03"){ 
		checkFlag=RiskInfoGrid.getSelNo();
		dutyCode=RiskInfoGrid.getRowColData(checkFlag - 1,3);
		polNo = RiskInfoGrid.getRowColData(checkFlag - 1,8);
		edorNo= RiskInfoGrid.getRowColData(checkFlag - 1,9);
		edorType=RiskInfoGrid.getRowColData(checkFlag - 1,10);
	}
	if(InputFlag=="0"){
		fm.Remark.value="";
	}
	var overFlag="FALSE";
	fm.action = "./SendUWReInsureChk.jsp?OverFlag="+overFlag+"&OpeFlag="+opeFlag+"&FilePath="+FilePath+"&FileName="+FileName+"&PolNo="+polNo+"&EdorNo="+edorNo+"&EdorType="+edorType+"&CaseNo="+caseNo+"&DutyCode="+dutyCode;
	fm.submit();
}

function showRule(){
	ReInsureGrid.clearData();
	if(InputFlag!=0){
		fm.Remark.value="";
	}
	var opeFlag=fmImport.OpeFlag.value;
	var strSQL="";
	var tSel="";
	if(opeFlag=="01"||opeFlag=="03"){
		tSel=RiskInfoGrid.getSelNo();
		//strSQL="select RuleCODE,RuleName,'',PreceptCode from RILMUW a where a.preceptcode in (select RIPreceptNo from RIPrecept where AccumulateDefNO in (select AccumulateDefNO from RIAccumulateRDCode where AssociatedCode='"+RiskInfoGrid.getRowColData(tSel-1,3)+"')) and CalItemType='"+fmImport.OpeFlag.value+"' order by RuleOrder";
		var mySql109=new SqlClass();
	 		mySql109.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  		mySql109.setSqlId("ReInsureInputSql109");//ָ��ʹ�õ�Sql��id
	  		mySql109.addSubPara(RiskInfoGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
	  		mySql109.addSubPara(fmImport.OpeFlag.value);//ָ������Ĳ���
	  		strSQL=mySql109.getString();
	}
	if(opeFlag=="04"){
		tSel=ClaimInfoGrid.getSelNo();
		//strSQL="select RuleCODE,RuleName,'',PreceptCode from RILMUW a where a.preceptcode in (select RIPreceptNo from RIPrecept where AccumulateDefNO in (select AccumulateDefNO from RIAccumulateRDCode where AssociatedCode='"+ClaimInfoGrid.getRowColData(tSel-1,3)+"')) and CalItemType='"+fmImport.OpeFlag.value+"' order by RuleOrder";
		var mySql110=new SqlClass();
	 		mySql110.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  		mySql110.setSqlId("ReInsureInputSql110");//ָ��ʹ�õ�Sql��id
	  		mySql110.addSubPara(ClaimInfoGrid.getRowColData(tSel-1,3));//ָ������Ĳ���
	  		mySql110.addSubPara(fmImport.OpeFlag.value);//ָ������Ĳ���
	  		strSQL=mySql110.getString();
	}
	var strArray=easyExecSql(strSQL);
	
	if(strArray==null){
		return false;
	}else
	{
		for(var i=0;i<strArray.length;i++){
			ReInsureGrid.addOne(); 
			ReInsureGrid.setRowColData(i,1,strArray[i][0]+""); 
			ReInsureGrid.setRowColData(i,2,strArray[i][1]+""); 
			ReInsureGrid.setRowColData(i,3,strArray[i][2]+""); 
			ReInsureGrid.setRowColData(i,4,strArray[i][3]+""); 
		}
	}
	//QueryReInsureAudit();
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
	var  polNo= RiskInfoGrid.getRowColData(checkFlag - 1,8);
	var  dutyCode= RiskInfoGrid.getRowColData(checkFlag - 1,3);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'�˱�����'"
	+ " from LCReInsurUWIdea a where a.AuditType='01' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'�ٱ��ظ�'"
	+ " from LCReInsurIdea a where a.AuditType='01' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' ) order by uwno desc"
	;
	*/
	var mySql111=new SqlClass();
	 	mySql111.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql111.setSqlId("ReInsureInputSql111");//ָ��ʹ�õ�Sql��id
	  	mySql111.addSubPara(polNo);//ָ������Ĳ���
	  	mySql111.addSubPara(dutyCode);//ָ������Ĳ���
	  	mySql111.addSubPara(polNo);//ָ������Ĳ���
	  	mySql111.addSubPara(dutyCode);//ָ������Ĳ���
	  	mSql=mySql111.getString();	
	turnPage2.queryModal(mSql,ReInsureAuditGrid);
}
 
function QueryEdorAudit(){
	var checkFlag=RiskInfoGrid.getSelNo();
	var polNo= RiskInfoGrid.getRowColData(checkFlag - 1,8);
	var dutyCode= RiskInfoGrid.getRowColData(checkFlag - 1,3);
	var edorNo= RiskInfoGrid.getRowColData(checkFlag - 1,9);
	var edorType=RiskInfoGrid.getRowColData(checkFlag - 1,10);
	/**
	mSql = "select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.UWIdea ,a.ModifyDate,a.AdjunctPath,'�˱�����' "
	+ " from LCReInsurUWIdea a where a.AuditType='03' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"'"
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.UWIdea,a.ModifyDate,a.AdjunctPath,'�ٱ��ظ�' "
	+ " from LCReInsurIdea a where a.AuditType='03' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+edorNo+"' and a.AuditAffixCode='"+edorType+"'"
	;
	*/
	var mySql112=new SqlClass();
	 	mySql112.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql112.setSqlId("ReInsureInputSql112");//ָ��ʹ�õ�Sql��id
	  	mySql112.addSubPara(polNo);//ָ������Ĳ���
	  	mySql112.addSubPara(dutyCode);//ָ������Ĳ���
	  	mySql112.addSubPara(edorNo);//ָ������Ĳ���
	  	mySql112.addSubPara(edorType);//ָ������Ĳ���
	  	mySql112.addSubPara(polNo);//ָ������Ĳ���
	  	mySql112.addSubPara(dutyCode);//ָ������Ĳ���
	  	mySql112.addSubPara(edorNo);//ָ������Ĳ���
	  	mySql112.addSubPara(edorType);//ָ������Ĳ���
	  	mSql=mySql112.getString();	
	turnPage2.queryModal(mSql,ReInsureAuditGrid);
}

function QueryClaimAudit(){
	var checkFlag=ClaimInfoGrid.getSelNo();
	var polNo= ClaimInfoGrid.getRowColData(checkFlag - 1,6);
	var dutyCode= ClaimInfoGrid.getRowColData(checkFlag - 1,3);
	var caseNo= ClaimInfoGrid.getRowColData(checkFlag - 1,7);
	/**
	mSql = "select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.UWOperator,a.ModifyDate,a.AdjunctPath,'�˱�����',a.UWIdea "
	+ " from LCReInsurUWIdea a where a.AuditType='04' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+caseNo+"'"
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),a.dutycode,a.UWNo,a.ReInsurer,a.ModifyDate,a.AdjunctPath,'�ٱ��ظ�',a.UWIdea "
	+ " from LCReInsurIdea a where a.AuditType='04' and a.PolNo='"+polNo+"' and a.dutycode='"+dutyCode+"' and a.AuditCode='"+caseNo+"'"
	;
	*/
	var mySql113=new SqlClass();
	 	mySql113.setResourceName("reinsure.ReInsureInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql113.setSqlId("ReInsureInputSql113");//ָ��ʹ�õ�Sql��id
	  	mySql113.addSubPara(polNo);//ָ������Ĳ���
	  	mySql113.addSubPara(dutyCode);//ָ������Ĳ���
	  	mySql113.addSubPara(caseNo);//ָ������Ĳ���
	  	mySql113.addSubPara(polNo);//ָ������Ĳ���
	  	mySql113.addSubPara(dutyCode);//ָ������Ĳ���
	  	mySql113.addSubPara(caseNo);//ָ������Ĳ���
	  	mSql=mySql113.getString();	
	turnPage2.queryModal(mSql,ReInsureAuditGrid);
}

function showInfomation(){
	tSel=ReInsureAuditGrid.getSelNo();
	fmInfo.RemarkInfo.value=ReInsureAuditGrid.getRowColData(tSel-1,6)+"";
}

/**���������¼���
*/
function clearData(){ 
	if(InputFlag=="0"){
		fm.Remark.value=""; 
	}
	InputFlag="1"; 
}
