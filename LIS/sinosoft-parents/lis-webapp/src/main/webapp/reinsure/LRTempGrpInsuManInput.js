/*********************************************************************
 *  �������ƣ�LRTempInsuManInput.js
 *  �����ܣ��ٷֹ���
 *  �������ڣ�2007-10-09 
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
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var temp = new Array();
var mOperate="";
var ImportPath;
var oldDataSet ; 
var InputFlag="0";
var tSearchFlag="0";
var mAppFlag="";
var showInfo;

window.onfocus=myonfocus;

var mContPlanCode = "";
var mRiskCode = "";
var mDutyCode = "";
var mInsuredNo = "";
var mInsuredName = "";

//��ѡ����ʾ�ٷ������¼
function showTempList(){
	if(fm.ContType.value=='1')
	{
		showTempInd(); //��ʾ�����ٷ������¼
	}
	else
	{
		showTempGrp(); //��ʾ�����ٷ������¼
	}
}


//�����ٷ������¼
function showTempGrp()
{
	var typeRadio="";
	for(i = 0; i <fm.StateRadio.length; i++){
		if(fm.StateRadio[i].checked){
			typeRadio=fm.StateRadio[i].value;
			break;
		}
	}
	//var mSQL = " select a.PrtNo X1, a.ProposalGrpContNo X2,a.GrpContNo X3,decode(b.State, '01', '�ܹ�˾���', '02', '�ٱ����', '03', '������') X4,b.State X5, decode(a.appflag, '1', '��ǩ��', 'δǩ��')  X6, a.appflag X7, b.SerialNo X8 From ripoldutygrpview a,RIGrpState b where b.ProposalGrpContNo = a.ProposalGrpContNo "
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRTempGrpInsuManInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara("1");
	var mSQL=mySql100.getString();
	
	if(typeRadio=='1'){ //δ���
	//	mSQL = mSQL + " and b.state = '02' ";
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRTempGrpInsuManInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(1);//ָ������Ĳ���
	    mSQL=mySql101.getString();
	}
	if(typeRadio=='2'){ //�����
		//mSQL = mSQL + " and b.state = '03' ";
		var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql102.setSqlId("LRTempGrpInsuManInputSql102");//ָ��ʹ�õ�Sql��id
			mySql102.addSubPara(1);//ָ������Ĳ���
	    	mSQL=mySql102.getString();
	}
	//mSQL = mSQL + " and b.state <> '01' and b.proposalcontno='000000' and b.exetype='05' order by X8 desc  " ;
	/**
	var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql103.setSqlId("LRTempGrpInsuManInputSql103");//ָ��ʹ�õ�Sql��id
		mySql103.addSubPara(mSQL);//ָ������Ĳ���
	    mSQL=mySql103.getString();
	    */
	turnPage1.queryModal(mSQL, TempInsuListGrid);
}

//��ѯ������Ϣ
function QueryPolInfo()
{
	fm.TempUWConclusion.value="";
	fm.TempUWConclusionName.value="";
	
	if(fm.ContType.value=='1')
	{
		QueryIndNewInfo();
	}
	else
	{
		QueryGrpNewInfo();
	}
}

//��ѯ���ձ�����Ϣ
function QueryIndNewInfo()
{
	var tSel=TempInsuListGrid.getSelNo();
	var tContNo=TempInsuListGrid.getRowColData(tSel-1,2);
	if(fm.DeTailFlag.value=='1')
	{ //������
		//mSQL = " select a.insuredname A1, a.RiskCode A2, '000000' A3, a.Mult A4, a.Prem A5, a.Amnt A6, a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9, decode(b.State, '00', '��ͬ�ֱ�', '01', '�����ٷ�����', '02', '���ٷ����', '03', '�ٷ�', '04', '�ٷ�δʵ��') A10, b.State A11, b.uwconclusion A12 from ripolindview a left join RIDutyState b on (b.proposalgrpcontno = '000000' and b.proposalno = a.proposalno and b.dutycode = '000000' and b.state in ('00', '02', '03', '04')) where a.ProposalContNo ='"+tContNo+"' ";
	var mySql104=new SqlClass();
		mySql104.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql104.setSqlId("LRTempGrpInsuManInputSql104");//ָ��ʹ�õ�Sql��id
		mySql104.addSubPara(tContNo);//ָ������Ĳ���
	    mSQL=mySql104.getString();
	}
	else
	{ //������
		//mSQL = " select a.insuredname A1, a.RiskCode A2, a.dutycode A3, a.Mult A4, a.Prem A5, a.Amnt A6, a.RiskAmnt A7, a.AccAmnt A8, a.ProposalNo A9, decode(b.State, '00', '��ͬ�ֱ�', '01', '�����ٷ�����', '02', '���ٷ����', '03', '�ٷ�', '04', '�ٷ�δʵ��') A10, b.State A11, b.uwconclusion A12 from ripoldutyindview a left join RIDutyState b on (b.proposalgrpcontno = '000000' and b.proposalno = a.proposalno and b.dutycode = a.dutycode and b.state in ('00', '02', '03', '04')) where a.ProposalContNo ='"+tContNo+"' ";
		var mySql105=new SqlClass();
			mySql105.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql105.setSqlId("LRTempGrpInsuManInputSql105");//ָ��ʹ�õ�Sql��id
			mySql105.addSubPara(tContNo);//ָ������Ĳ���
	    	mSQL=mySql105.getString();
	}
	
	turnPage1.queryModal(mSQL, IndTempListGrid);
	fm.ContNo.value=tContNo;
	//��ʾ�ѹ��������α���
	showRelaPol();
}

//��ѯ������Ϣ
function QueryGrpNewInfo()
{
	
	var tSel=TempInsuListGrid.getSelNo();
	var tProPosalGrpNo=TempInsuListGrid.getRowColData(tSel-1,2);
	var tGrpContNo=TempInsuListGrid.getRowColData(tSel-1,3);
	fm.GrpContNo.value=tGrpContNo;
	fm.ProposalGrpContNo.value=tProPosalGrpNo;
	mAppFlag=TempInsuListGrid.getRowColData(tSel-1,7);
	
	//��ʾ�ѹ��������α���
	showRelaPol();
}

//��ʾ�ѹ��������α���
function showRelaPol()
{
	//var strSQL = " select count(*) from ridutystate a where trim(a.ProposalGrpContNo)='"+fm.ProposalGrpContNo.value+"' and a.state in ('00','03','04') and trim(a.UWConclusion)='02' ";
	var mySql106=new SqlClass();
		mySql106.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql106.setSqlId("LRTempGrpInsuManInputSql106");//ָ��ʹ�õ�Sql��id
		mySql106.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
	var strSQL=mySql106.getString();
	var arrResult = easyExecSql(strSQL);
	if(arrResult!='0')
	{
		//strSQL = " select * from (select a1.prtno X1,a1.grpcontno X2,(select contplancode from lcinsured where insuredno=a1.insuredno and ProposalContNo=a1.ProposalContNo) X3,a1.contno X4,a1.proposalno X5,a1.InsuredName X6,a1.InsuredNo X7,a1.riskcode X8,a2.dutycode X9,(select decode( state , '00' , '��ͬ�ֱ�' , '01' , '�����ٷ�����' , '02' , '�����' , '03' , '�ٷ�' , '04' , '�ٷ�δʵ��' , '��֪��״̬' ) from RIDutyState where ProposalNo=a1.ProposalNo and DutyCode=a2.dutycode) X10,(select State from RIDutyState where ProposalNo=a1.ProposalNo and DutyCode=a2.dutycode ) X11,(select UWConclusion from RIDutyState where ProposalNo=a1.ProposalNo and DutyCode=a2.dutycode ) X12 from lcpol a1,lcduty a2 where a1.polno=a2.polno and exists (select 'X' from lcgrpcont where grpcontno=a1.grpcontno and proposalgrpcontno='"+fm.ProposalGrpContNo.value+"' ) and exists (select 'X' from ridutystate where proposalno=a1.proposalno and dutycode=a2.dutycode and state in ('00','03','04') and UWConclusion='02')) x order by x.X11" ;	
		var mySql107=new SqlClass();
			mySql107.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql107.setSqlId("LRTempGrpInsuManInputSql107");//ָ��ʹ�õ�Sql��id
			mySql107.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
		    strSQL=mySql107.getString();
		turnPage3.queryModal(strSQL,RelaListGrid);	
		divRelaDel.style.display='';
	}
}

//���屣����ѯ
function SearchRecord()
{
	if(fm.GrpContNo.value==null||fm.GrpContNo.value=="")
	{
		myAlert(" "+"���������ٷ������б���ѡ�񱣵���"+" ");
		return false;
	}
	/**
	var mSQL =" select * from (select a.prtno X1,a.GrpContNo X2,(select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno)) X3,a.contno X4,a.proposalno X5,insuredname X6,insuredno X7,riskcode X8,dutycode X9,(select case state when '00' then '��ͬ�ֱ�' when '01' then '�����ٷ�����' when '02' then '�����' when '03' then '�ٷ�' when '04' then '�ٷ�δʵ��' else '��֪��״̬' end from RIDutyState where trim(proposalno)=trim(a.proposalno) and trim(dutycode)=trim(b.dutycode)) X10,(select state from RIDutyState where trim(proposalno)=trim(a.proposalno) and trim(dutycode)=trim(b.dutycode)) X11,(select UWConclusion from RIDutyState where trim(ProposalNo)=trim(a.ProposalNo) and trim(DutyCode)=trim(b.DutyCode) ) X12 from lcpol a , lcduty b where trim(a.polno)=trim(b.polno) "
	+ " and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"+fm.ProposalGrpContNo.value+"') "
	+ getWherePart("a.riskcode","RiskCode")
	+ getWherePart("b.DutyCode","DutyCode")
	+ getWherePart("a.insuredno","InsuredNo")
	+ getWherePart("a.insuredName","InsuredName","like")
	;
	*/
	var mySql108=new SqlClass();
		mySql108.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql108.setSqlId("LRTempGrpInsuManInputSql108");//ָ��ʹ�õ�Sql��id
		mySql108.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
		/**
		mySql108.addSubPara(getWherePart("a.riskcode","RiskCode"));//ָ������Ĳ���
		mySql108.addSubPara(getWherePart("b.DutyCode","DutyCode"));//ָ������Ĳ���
		mySql108.addSubPara(getWherePart("a.insuredno","InsuredNo"));//ָ������Ĳ���
		mySql108.addSubPara(getWherePart("a.insuredName","InsuredName","like"));//ָ������Ĳ���
		*/
		mySql108.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		mySql108.addSubPara(fm.DutyCode.value);//ָ������Ĳ���
		mySql108.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
		mySql108.addSubPara(fm.InsuredName.value);//ָ������Ĳ���
	var mSQL=mySql108.getString();
	if(fm.ContPlanCode.value!=""&&fm.ContPlanCode.value!=null)
	{
		//mSQL = mSQL + " and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno) and trim(contplancode)='"+fm.ContPlanCode.value+"')";
		var mySql109=new SqlClass();
			mySql109.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql109.setSqlId("LRTempGrpInsuManInputSql109");//ָ��ʹ�õ�Sql��id
			mySql109.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.DutyCode.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.InsuredName.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.ContPlanCode.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
			mySql109.addSubPara(fm.TempUWConclusionConf.value);//ָ������Ĳ���
			mSQL=mySql109.getString();
	}
	/**
	if(fm.TempUWConclusionConf.value!=""&&fm.TempUWConclusionConf.value!=null)
	{
		//mSQL = mSQL + " and exists (select * from RIDutyState where trim(ProposalGrpContNo)="+fm.ProposalGrpContNo.value+" and trim(ProposalNo)=trim(a.ProposalNo) and trim(State)='"+fm.TempUWConclusionConf.value+"') ";
		var mySql110=new SqlClass();
			mySql110.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql110.setSqlId("LRTempGrpInsuManInputSql110");//ָ��ʹ�õ�Sql��id
			mySql110.addSubPara(mSQL);//ָ������Ĳ���
			mySql110.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
			mySql110.addSubPara(fm.TempUWConclusionConf.value);//ָ������Ĳ���
			mSQL=mySql110.getString();
	}
	
	mSQL = mSQL + " and exists (select * from RIDutyState where trim(ProposalNo)=trim(a.ProposalNo)  and trim(state) = '02' )";
	mSQL = mSQL + " ) x order by x.X11";
  
  	var mySql111=new SqlClass();
		mySql111.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql111.setSqlId("LRTempGrpInsuManInputSql111");//ָ��ʹ�õ�Sql��id
		mySql111.addSubPara(mSQL);//ָ������Ĳ���
		mSQL=mySql111.getString();
		*/
	mContPlanCode = fm.ContPlanCode.value ;
	mRiskCode = fm.RiskCode.value ;
	mDutyCode = fm.DutyCode.value ;
	mInsuredNo = fm.InsuredNo.value ;
	mInsuredName = fm.InsuredName.value ;
	tSearchFlag = "1"; //���Ѳ�ѯ���
	turnPage2.queryModal(mSQL,SearchResultGrid);
}

function ResetForm()
{
	fm.ContPlanCode.value = "";
	fm.ContPlanCodeName.value = "";
	fm.RiskCode.value = "";
	fm.RiskCodeName.value = "";
	fm.DutyCode.value = "";
	fm.DutyCodeName.value = "";
	fm.InsuredNo.value = "";
	fm.InsuredName.value = "";
	tSearchFlag ="0"; //����Ѳ�ѯ���
	SearchResultGrid.clearData();
}

function afterSubmit(FlagStr, content )
{ 
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
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

function clearData()
{ 
	if(InputFlag=="0")
	{
		fm.Remark.value=""; 
	}
	InputFlag="1"; 
}

function TempConclusionAll()
{
	if(mAppFlag!="1")
	{
		myAlert(""+"����δǩ���������ٷֽ���"+"");
		return false;
	}
	if(!verify1())
	{
		return false;
	}
	if (!confirm(""+"Ҫ�����в�ѯ������ٷֽ�����"+"")) 
	{
    	return false;
	}
	if(!VerifySearch())
	{
		myAlert(""+"��ѯ�����Ѿ��޸ģ������²�ѯ�����½��ۣ�"+"");
		return false;
	}
	
	var showStr=""+"���ڱ�������......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.action = "./TempUWConclusionSave.jsp?ContType=2&CONOPETYPE=01&UWFLAG=0";
	fm.submit();
}

function VerifySearch(){
  if(mContPlanCode != fm.ContPlanCode.value) return false;
	if(mRiskCode != fm.RiskCode.value) return false;
	if(mDutyCode != fm.DutyCode.value) return false;
	if(mInsuredNo != fm.InsuredNo.value) return false;
	if(mInsuredName != fm.InsuredName.value) return false;
	
	return true;
}

function TempConclusionSel(){
	var tSel=TempInsuListGrid.getSelNo();
	if(tSel==0){
		myAlert(" "+"����ѡ���ٷ�����"+" ");
		return false;
	}
	mAppFlag = TempInsuListGrid.getRowColData(tSel-1,7);
	if(mAppFlag!="1"){
		myAlert(""+"����δǩ���������ٷֽ���"+"");
		return false;
	}
	if(fm.TempUWConclusion.value==""||fm.TempUWConclusion.value==null){
		myAlert(""+"��ѡ���ٷֽ���"+"");
		return false;
	}
	if(fm.ContType.value=='1'){ //����
		var Count=IndTempListGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(IndTempListGrid.getChkNo(i)==true){
				if(IndTempListGrid.getRowColData(i,11)=='03'){
					myAlert(''+"�޸�"+'"�ٷ�""+"״̬����˽��۱��������ٷֱ�������ҳ����ȡ���ٷֱ�������������[��ʱ�ֱ�]��ť��"+"');
					return false;
				}
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"���ڲ�ѯ�����ѡ�����α���"+"");
			return false;
		}
		var showStr=""+"���ڱ�������......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
		//CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ��� 
		fm.action = "./TempUWConclusionSave.jsp?ContType=1&CONOPETYPE=02&UWFLAG=0&SerialNo="+TempInsuListGrid.getRowColData(tSel-1,8);
	}else{ //����
		var Count=SearchResultGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(SearchResultGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"���ڲ�ѯ�����ѡ�����α���"+"");
			return false;
		}
		var showStr=""+"���ڱ�������......"+""; 
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
		//CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ��� 
		fm.action = "./TempUWConclusionSave.jsp?ContType=2&CONOPETYPE=02&UWFLAG=0&SerialNo="+TempInsuListGrid.getRowColData(tSel-1,8);
	}
  fm.submit(); 
}

function verify1(){
	if(tSearchFlag!="1"){
		myAlert(""+"���ѯ���ٹ������α���"+"");
		return false;
	}
	if(fm.TempUWConclusion.value==""||fm.TempUWConclusion.value==null){
		myAlert(""+"��ѡ���ٷֽ���"+"");
		return false;
	}
	return true;
}

function verify2(){
	if(fm.ProposalGrpContNo.value==''||fm.ProposalGrpContNo.value==null){
		myAlert(""+"�����������ٷ������б���ѡ�����屣��!"+"");
		return false;
	}
	if(fm.ContPlanMode.value==''||fm.ContPlanMode.value==null){
		myAlert(""+"���ϼƻ�����Ϊ��"+"");
		return false;
	}
	if(fm.DutyCode1.value==''||fm.DutyCode1.value==null){
		myAlert(""+"���α��벻��Ϊ��"+"");
		return false;
	}
	return true;
}

function verify3(){
	if(fm.ProposalGrpContNo.value==''||fm.ProposalGrpContNo.value==null){
		myAlert(""+"�����������ٷ������б���ѡ�����屣��!"+"");
		return false;
	}
	if(fm.DutyCode1.value==''||fm.DutyCode1.value==null){
		myAlert(""+"���α��벻��Ϊ��"+"");
		return false;
	}
	return true;
}

/**
*��ѯ�ŵ�
*/
function QueryGrpNewAudit(){
	var tSel = GrpTempInsuListGrid.getSelNo();	
 	var tGrpContNo = GrpTempInsuListGrid.getRowColData(tSel-1,2);
	/**
	mSql = "select * from (select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,UWOperator,a.UWIdea ,ModifyDate,AdjunctPath ,'�˱�����',(select case State when '00' then '���ظ�' when '01' then '�ѻظ�' else '���' end from LCReInsurTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and trim(AuditCode)=trim(AuditCode) and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) "
	+ " from LCReInsurUWIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000' and AuditCode='000000' and AuditAffixCode='000000' "
	+ " union all"
	+ " select (select insuredname from lcpol where trim(polno)=trim(a.polno)),(select riskcode from lcpol where trim(polno)=trim(a.polno)),dutycode,a.UWNo,ReInsurer,a.UWIdea,ModifyDate,AdjunctPath ,'�ٱ��ظ�',(select case State when '00' then '���ظ�' when '01' then '�ѻظ�' else '���' end from LCReInsurTask where trim(a.ProposalGrpContNo)=trim(ProposalGrpContNo) and a.AuditType=AuditType and trim(a.PolNo)=trim(PolNo) and trim(a.dutycode)=trim(dutycode) and AuditCode=AuditCode and AuditAffixCode=AuditAffixCode and a.UWNo=UWNo) "
	+ " from LCReInsurIdea a where trim(a.ProposalGrpContNo)='"+tGrpContNo+"' and a.AuditType='05' and a.PolNo='000000' and a.dutycode='000000'  and AuditCode='000000' and AuditAffixCode='000000') order by uwno desc"
	;
	*/
	var mySql112=new SqlClass();
		mySql112.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql112.setSqlId("LRTempGrpInsuManInputSql112");//ָ��ʹ�õ�Sql��id
		mySql112.addSubPara(tGrpContNo);//ָ������Ĳ���
		mySql112.addSubPara(tGrpContNo);//ָ������Ĳ���
		mSql=mySql112.getString();	
	turnPage6.queryModal(mSql,ReInsureAuditGrid);
}

/**
* ȡ���ٷֽ���
*/
function DeleteRelaList()
{
	//����
	if(fm.DeleteType.value=="01")
	{
		var Count=RelaListGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++)
		{
			if(RelaListGrid.getChkNo(i)==true)
			{
				if(RelaListGrid.getRowColData(i,11)=="03")
				{
					myAlert(""+"ȡ���ٷ�״̬����[��ʱ�ֱ�]��ť"+","+"�����ٷֱ�������ҳ����в���."+"");
					return false;
				}
				chkFlag=true;
			}
		}
		if (chkFlag==false)
		{
			myAlert(""+"���ڹ����б�ѡ��Ҫɾ���ļ�¼"+"");
			return false;
		}
	}
	else if(fm.DeleteType.value=="02")
	{
		if (!confirm(""+"Ҫȡ���˱�������"+"")) 
		{
 		  return false;
		}
	}
	else
	{
		if (!confirm(""+"Ҫȡ���ٷ�δʵ�ֺ˱�������"+"")) 
		{
 		  return false;
		}
	}
	var showStr=""+"����ɾ������......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//ContType:'1' ���� '2' ���� ��CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'1'-�˱��ٷֽ��ۣ�'0'-�ٱ��ٷֽ��� 
	fm.action = "./TempUWConclusionSave.jsp?ContType=2&CONOPETYPE=03&UWFLAG=0"; 
	fm.submit(); 
}

function TempCessButton(){
	var tSel=TempInsuListGrid.getSelNo();
	var SerialNo="";
	var mAppFlag ="";
	if(fm.ContType.value=='1'){ //����
		if(fm.ContNo.value==null||fm.ContNo.value==""){
			myAlert(""+"�������ٷ��б���ѡ���ٷ�����"+"");
			return false;
		}
		SerialNo=TempInsuListGrid.getRowColData(tSel-1,8);
	    mAppFlag = TempInsuListGrid.getRowColData(tSel-1,7);
		if(mAppFlag!="1"){
			myAlert(""+"����δǩ�����ܽ�����ʱ�ֱ�"+"");
			return false;
		}
		var varSrc="&OperateNo='"+fm.ContNo.value+"'&CodeType='01'&SerialNo='"+SerialNo+"'";
		//window.open("./FrameMainCessInfo.jsp?Interface=LRSchemaInput.jsp"+varSrc,"true"); 
  		window.open("./FrameMainCessInfo.jsp?Interface=RIPreceptChoiceInput.jsp"+varSrc,"true");
	}else{ //����
		if(fm.ProposalGrpContNo.value==null||fm.ProposalGrpContNo.value==""){
			myAlert(""+"�������ٷ��б���ѡ���ٷ�����"+"");
			return false;
		}
		SerialNo=TempInsuListGrid.getRowColData(tSel-1,8);
	    mAppFlag = TempInsuListGrid.getRowColData(tSel-1,7);
		var varSrc="&OperateNo='"+fm.ProposalGrpContNo.value+"'&CodeType='05'&SerialNo='"+SerialNo+"'";
  	window.open("./FrameMainCessInfo.jsp?Interface=LRSchemaInput.jsp"+varSrc,"true"); 
	}
}

function AuditEnd()
{
	if(!confirm(""+"�Ƿ�ȷ���ٷ����������"+""))
	{
		return false;
	}
	if(fm.ContType.value=='1')
	{ //����
		var tSel=TempInsuListGrid.getSelNo();
		if(tSel==0){
			myAlert(""+"��ѡ���������"+"");
			return false;
		}
		if(fm.DeTailFlag.value=='1')
		{ 
			//������
			//var tSql = " select count(*) from ridutystate a where a.state='02' and exists (select 'X' from Ripolindview b where b.ProposalNo=a.proposalno and b.ProposalContNo='"+TempInsuListGrid.getRowColData(tSel-1,2)+"') ";
			var mySql113=new SqlClass();
				mySql113.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql113.setSqlId("LRTempGrpInsuManInputSql113");//ָ��ʹ�õ�Sql��id
				mySql113.addSubPara(TempInsuListGrid.getRowColData(tSel-1,2));//ָ������Ĳ���
			var tSql=mySql113.getString();	
			var arr=easyExecSql(tSql);
			if(arr!='0')
			{
				myAlert(" "+"�ٷ������б���δ���ٷֽ��۵ı���"+" ");
				return false;
			}	
		}else{ //������
			//var tSql = " select count(*) from ridutystate a where a.state='02' and exists (select 'X' from ripoldutyindview b where b.ProposalNo=a.proposalno and b.dutycode=a.dutycode and b.ProposalContNo='"+TempInsuListGrid.getRowColData(tSel-1,2)+"') ";
			var mySql114=new SqlClass();
				mySql114.setResourceName("reinsure.LRTempGrpInsuManInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql114.setSqlId("LRTempGrpInsuManInputSql114");//ָ��ʹ�õ�Sql��id
				mySql114.addSubPara(TempInsuListGrid.getRowColData(tSel-1,2));//ָ������Ĳ���
			var tSql=mySql114.getString();	
			var arr=easyExecSql(tSql);
			if(arr!='0'){
				myAlert(" "+"�ٷ������б���δ���ٷֽ��۵ı���"+" ");
				return false;
			}	
		}
	}
	else
	{ //����
		
	}
	var showStr=""+"���ڴ�������......"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//ContType:'1' ���� '2' ���� ��CONOPETYPE:'01'-ȫ���½���,'02'-ѡ�е��½���,'03'-ȡ���ٷֽ��ۣ�UWFLAG:'0'-�ٱ��ٷֽ��ۣ�'1'-�˱��ٷֽ��ۣ�'2'-�������
	fm.action = "./TempUWConclusionSave.jsp?CONOPETYPE=04&UWFLAG=2&SerialNo="+TempInsuListGrid.getRowColData(tSel-1,8); 
	fm.submit(); 
}

function afterCodeSelect( cCodeName, Field ) 
{
	if(Field.value=='01'){
		divButton1.style.display='';
		divSearch.style.display='';
		divContPlanCodeName.style.display='none';
		divDutyCodeName.style.display='none';
		fm.DutyCode1.style.display='none';
		fm.ContPlanMode.style.display='none';
		
	}else if(Field.value=='02'){
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='';
		fm.ContPlanMode.style.display='';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		
	}else if(Field.value=='03'){
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='none';
		fm.ContPlanMode.style.display='none';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		
	}
}
