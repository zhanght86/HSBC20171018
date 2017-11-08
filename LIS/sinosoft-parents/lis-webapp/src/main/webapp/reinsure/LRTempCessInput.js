	/*********************************************************************
 *  程序名称：LRTempCessInput.js
 *  程序功能：再保回复
 *  创建日期：2007-3-30 15:21
 *  创建人  ：zhang bin
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
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
	*查询核保发起任务
	*/
 function QueryRiskInfo(){ 	
 		PreceptGrid.clearData();
 		/**
 		mSQL = "select x.a,x.b,x.c,x.d,x.e,x.f,(case x.g when '1' then '签单' else '未签单' end),x.g,x.h,(case x.i when '1' then '已下临分结论' else '未下临分结论' end),x.j,x.k from ("
		+ " select b.insuredname a,b.riskcode b,a.dutycode c,a.amnt d,a.prem e,a.polno f,b.appflag g,b.PolNo h, "
		+ "(case when (select count(1) from CasualRIContAssociate where trim(contno)=trim(b.contno)||','||trim(b.polno) "
		+ " and trim(b.riskcode)=trim(riskcode) and trim(a.dutycode)=trim(dutycode))>0 then '1' else '0' end) i,b.contno j,b.insuredNo k "
		+ " from lcduty a,lcpol b,(select distinct polNo pNo,dutyCode dCode,audittype atype from LCReInsurTask ) c "   //audittype: '01'为新单
		+ " where trim(a.polno)=trim(c.pNo) and trim(a.dutycode)=trim(c.dCode) and b.polno=a.polno and c.atype='01') x where 1=1 "
		+ getWherePart("x.a","InsuredNo")
	  + getWherePart("x.b","RiskCode")
	  + getWherePart("x.g","AppFlag")
	  + getWherePart("x.i","TempContClusion")
		;
 		*/
 		var mySql100=new SqlClass();
			mySql100.setResourceName("reinsure.LRTempCessInputSql"); //指定使用的properties文件名
			mySql100.setSqlId("LRTempCessInputSql100");//指定使用的Sql的id
			/**
			mySql100.addSubPara(getWherePart("x.a","InsuredNo"));//指定传入的参数
			mySql100.addSubPara(getWherePart("x.b","RiskCode"));//指定传入的参数
			mySql100.addSubPara(getWherePart("x.g","AppFlag"));//指定传入的参数
			mySql100.addSubPara(getWherePart("x.i","TempContClusion"));//指定传入的参数
			*/
			mySql100.addSubPara(fm.InsuredNo.value);//指定传入的参数
			mySql100.addSubPara(fm.RiskCode.value);//指定传入的参数
			mySql100.addSubPara(fm.AppFlag.value);//指定传入的参数
			mySql100.addSubPara(fm.TempContClusion.value);//指定传入的参数
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
		mySql101.setResourceName("reinsure.LRTempCessInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRTempCessInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(contNo);//指定传入的参数
		mySql101.addSubPara(polNo);//指定传入的参数
		mySql101.addSubPara(riskCode);//指定传入的参数
		mySql101.addSubPara(dutyCode);//指定传入的参数
		mySql101.addSubPara(RiskInfoGrid.getRowColData(tSel-1,3));//指定传入的参数	
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
		myAlert(""+"请选择责任保单信息"+" ");
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
		myAlert(""+"请选中再保方案"+" ");
		return false;
	}
	//此功能去掉
	//sameAccumulate();
	
	var dutyCode=RiskInfoGrid.getRowColData(tSel-1,3);
	var polNo=RiskInfoGrid.getRowColData(tSel-1,6);
	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
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
		mySql102.setResourceName("reinsure.LRTempCessInputSql"); //指定使用的properties文件名
		mySql102.setSqlId("LRTempCessInputSql102");//指定使用的Sql的id
		mySql102.addSubPara(polNo);//指定传入的参数
	var tSQL=mySql102.getString();
	var arrResult=easyExecSql(tSQL);
	if(arrResult==null){
		myAlert(""+"没有找到该保单"+"");
		return false;
	}
	if(arrResult[0]=="0"){
		myAlert(""+"未签单保单不能下临分结论"+"");
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
		myAlert(""+"请选择责任保单信息"+" ");
		return false;
	}
	//var appFlag = RiskInfoGrid.getRowColData(tSel-1,8);
	//if(appFlag=='1'){
	//	alert("责任保单已经签单，不能再保回复");
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
