	/*********************************************************************
 *  �������ƣ�LRTempCessInput.js
 *  �����ܣ��ٱ��ظ�
 *  �������ڣ�2007-3-30 15:21
 *  ������  ��zhang bin
 *  ����ֵ��  ��
 *  ���¼�¼��  ������    ��������     ����ԭ��/����
 *********************************************************************
 */

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
//alert(PrtNo);
window.onfocus=myonfocus;

/**
	*��ѯ�˱���������
	*/
 function QueryRiskInfo(){ 	
 		PreceptGrid.clearData();
 		/**
 		mSQL = "select x.a,x.b,x.c,x.d,x.e,x.f,(case x.g when '1' then 'ǩ��' else 'δǩ��' end),x.g,x.h,(case x.i when '1' then '�����ٷֽ���' else 'δ���ٷֽ���' end),x.j,x.k from ("
		+ " select b.insuredname a,b.riskcode b,a.dutycode c,a.amnt d,a.prem e,a.polno f,b.appflag g,b.PolNo h, "
		+ "(case when (select count(1) from CasualRIContAssociate where trim(contno)=trim(b.contno)||','||trim(b.polno) "
		+ " and trim(b.riskcode)=trim(riskcode) and trim(a.dutycode)=trim(dutycode))>0 then '1' else '0' end) i,b.contno j,b.insuredNo k "
		+ " from lcduty a,lcpol b,(select distinct polNo pNo,dutyCode dCode,audittype atype from LCReInsurTask ) c "   //audittype: '01'Ϊ�µ�
		+ " where trim(a.polno)=trim(c.pNo) and trim(a.dutycode)=trim(c.dCode) and b.polno=a.polno and c.atype='01') x where 1=1 "
		+ getWherePart("x.a","InsuredNo")
	  + getWherePart("x.b","RiskCode")
	  + getWherePart("x.g","AppFlag")
	  + getWherePart("x.i","TempContClusion")
		;
 		*/
 		var mySql100=new SqlClass();
			mySql100.setResourceName("reinsure.LRTempCessInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql100.setSqlId("LRTempCessInputSql100");//ָ��ʹ�õ�Sql��id
			/**
			mySql100.addSubPara(getWherePart("x.a","InsuredNo"));//ָ������Ĳ���
			mySql100.addSubPara(getWherePart("x.b","RiskCode"));//ָ������Ĳ���
			mySql100.addSubPara(getWherePart("x.g","AppFlag"));//ָ������Ĳ���
			mySql100.addSubPara(getWherePart("x.i","TempContClusion"));//ָ������Ĳ���
			*/
			mySql100.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
			mySql100.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
			mySql100.addSubPara(fm.AppFlag.value);//ָ������Ĳ���
			mySql100.addSubPara(fm.TempContClusion.value);//ָ������Ĳ���
	    	mSQL=mySql100.getString();
 		
		turnPage.queryModal(mSQL, RiskInfoGrid);
 }

function afterSubmit(FlagStr, content )
{ 
  showInfo.close();
  if (FlagStr == "Fail" ){             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    location.reload(true);
  }
}

function afterCodeSelect(cCodeName,Field){
}

function showPrecept(){
	PreceptGrid.clearData();
	tSel = RiskInfoGrid.getSelNo();
	contNo=RiskInfoGrid.getRowColData(tSel-1,11);
	insuredNo=RiskInfoGrid.getRowColData(tSel-1,12);
	riskCode=RiskInfoGrid.getRowColData(tSel-1,2);
	dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
	polNo=RiskInfoGrid.getRowColData(tSel-1,9);;
	/**
	var strSQL = "select a.RIContNo,a.RIPreceptNo,a.AccumulateDefNO,"
	+ " (case when(select count(1) from CasualRIContAssociate where contno='"+contNo+"'||"+"','||'"+polNo+"' and riskcode='"+riskCode+"' and dutyCode='"+dutyCode+"' and trim(NaturalRIPreceptNo)=trim(a.RIPreceptNo))>0 then '1' else '0' end)"
	+ " from riprecept a where a.accumulatedefno in (select accumulatedefno from accumulateRDcode where associatedcode='"+RiskInfoGrid.getRowColData(tSel-1,3)+"') "
	;
	*/
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRTempCessInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRTempCessInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(contNo);//ָ������Ĳ���
		mySql101.addSubPara(polNo);//ָ������Ĳ���
		mySql101.addSubPara(riskCode);//ָ������Ĳ���
		mySql101.addSubPara(dutyCode);//ָ������Ĳ���
		mySql101.addSubPara(RiskInfoGrid.getRowColData(tSel-1,3));//ָ������Ĳ���	
	var strSQL=mySql101.getString();
	turnPage2.queryModal(strSQL, PreceptGrid);
	for(var i=0;i<PreceptGrid.mulLineCount;i++){
		if(PreceptGrid.getRowColData(i,4)=='1'){
			PreceptGrid.checkBoxSel(i+1);
		}
	}
}

function TempCessConcls(){
	var tSel = RiskInfoGrid.getSelNo();
	if(tSel==0){
		myAlert(""+"��ѡ�����α�����Ϣ"+" ");
		return false;
	}
	if(!verifyAppFlag()){
		return false;
	}
	var count=PreceptGrid.mulLineCount; 
	var chkFlag=false; 
	for (i=0;i<count;i++){
		if(PreceptGrid.getChkNo(i)==true){
			chkFlag=true;
		}
	}
	if (chkFlag==false){
		myAlert(""+"��ѡ���ٱ�����"+" ");
		return false;
	}
	//�˹���ȥ��
	//sameAccumulate();
	
	var dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
	var polNo=RiskInfoGrid.getRowColData(tSel-1,6);
	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	
	fm.action="./CessTempConclusionSave.jsp?DutyCode="+dutyCode+"&PolNo="+polNo
	fm.submit();
}

function verifyAppFlag(){
	var tSel = RiskInfoGrid.getSelNo();
	var polNo = RiskInfoGrid.getRowColData(tSel-1,6);
	//var tSQL = "select AppFlag from LCPol where trim(PolNo)='"+polNo+"' ";
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRTempCessInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql102.setSqlId("LRTempCessInputSql102");//ָ��ʹ�õ�Sql��id
		mySql102.addSubPara(polNo);//ָ������Ĳ���
	var tSQL=mySql102.getString();
	var arrResult=easyExecSql(tSQL);
	if(arrResult==null){
		myAlert(""+"û���ҵ��ñ���"+"");
		return false;
	}
	if(arrResult[0]=="0"){
		myAlert(""+"δǩ�������������ٷֽ���"+"");
		return false;
	}
	return true;
}
function sameAccumulate(){
	for(var i=0;i<PreceptGrid.mulLineCount;i++){
		if(PreceptGrid.getChkNo(i)){
			for(var j=0;j<PreceptGrid.mulLineCount;j++){
				if(PreceptGrid.getRowColData(i,3)==PreceptGrid.getRowColData(j,3)){
					PreceptGrid.checkBoxSel(j+1);
				}
			}
		}
	}
	return true;
}

function ReinsureAnswer(){
	var tSel = RiskInfoGrid.getSelNo();
	if(tSel==0){
		myAlert(""+"��ѡ�����α�����Ϣ"+" ");
		return false;
	}
	//var appFlag = RiskInfoGrid.getRowColData(tSel-1,8);
	//if(appFlag=='1'){
	//	alert("���α����Ѿ�ǩ���������ٱ��ظ�");
	//	return false;
	//}
	
	var dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
	var proposalNo=RiskInfoGrid.getRowColData(tSel-1,6);
  window.open("./FrameReinsureAnswer.jsp?DutyCode="+dutyCode+"&ProposalNo="+proposalNo+"&OpeFlag=1");
}

function resetForm(){
	fm.InsuredNo.value="";
	fm.InsuredName.value="";
	fm.RiskCode.value="";
	fm.AppFlag.value="";
	fm.AppFlagName.value="";
	fm.TempContClusion.value="";
	fm.TempContClusionName.value="";
	
	RiskInfoGrid.clearData();
	PreceptGrid.clearData();
}
