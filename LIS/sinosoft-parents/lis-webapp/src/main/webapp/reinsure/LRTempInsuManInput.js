/*********************************************************************
 *  程序名称：LRTempInsuManInput.js
 *  程序功能：临分管理
 *  创建日期：2007-10-09 
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
var polNo = ""; 
var SerialNo="";

//单选框显示临分申请记录
function showTempList(){
	
  	showTempGrp(); //显示个险临分申请记录

}

//查询个险保单信息
function QueryPolInfo(){
	//查询待审核的保单
	var sql="select  a.Proposalgrpcontno,a.Proposalcontno,decode(a.State,'01','"+"临分发起"+"','02','"+"临分审核"+"','03','"+"临分处理"+"','04','"+"处理完毕"+"'),b.Riskcode,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno),b.Prem,b.Amnt,b.Riskamnt,a.Exetype,a.State,a.SerialNo from RIGrpState a,lcpol b where a.Proposalcontno=b.contno and a.state='02'";
	turnPage1.queryModal(sql, IndTempToalListGrid);
}
function  listSelect(){
	var tSel = IndTempToalListGrid.getSelNo();	
	var SerialNo = IndTempToalListGrid.getRowColData(tSel-1,11);
	mSQL = " select b.Contno A1,a.ProposalNo A2,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A3,b.RiskCode A4,c.dutycode A5,c.Prem A6,c.Amnt A7,c.RiskAmnt A8,decode(a.State, '02', '"+"待临分审核"+"', '03', '"+"临分"+"', '04', '"+"临分处理完毕"+"') A9,a.State A10 " +
			" from RIDutyState a, lcpol b, lcduty c " +
			" where a.State in('02','03') " +
			" and a.Proposalno=b.polno " +
			" and a.Proposalno=c.polno " +
			" and a.Dutycode=c.Dutycode " +
			" and a.SerialNo='"+SerialNo+"'" +
		    " and exists(select 'X'from RIGrpState d where d.SerialNo='"+SerialNo+"' and d.state='02')";
	turnPage2.queryModal(mSQL, IndTempListGrid);
}

function afterSubmit(FlagStr, content ){ 
  showInfo.close();
  if (FlagStr == "Fail" ){             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +  encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" +  encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    location.reload(true);
  }
}
function TempConclusionAll(){
	if(mAppFlag!="1"){
		myAlert(""+"保单未签单不能下临分结论"+"");
		return false;
	}
	if(!verify1()){
		return false;
	}
	if (!confirm(""+"要对所有查询结果下临分结论吗？"+"")) 
	{
    return false;
	}
	if(!VerifySearch()){
		myAlert(""+"查询条件已经修改，请重新查询后再下结论！"+"");
		return false;
	}
	
	var showStr=""+"正在保存资料......"+"";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	fm.action = "./TempUWConclusionSave.jsp?ContType=2&CONOPETYPE=01&UWFLAG=0";
  fm.submit();
}

function TempConclusionSel(){
	var tSel=TempInsuListGrid.getSelNo();
	if(tSel==0){
		myAlert(" "+"请先选中临分任务"+" ");
		return false;
	}
	mAppFlag = TempInsuListGrid.getRowColData(tSel-1,7);
	if(mAppFlag!="1"){
		myAlert(""+"保单未签单不能下临分结论"+"");
		return false;
	}
	if(fm.TempUWConclusion.value==""||fm.TempUWConclusion.value==null){
		myAlert(""+"请选择临分结论"+"");
		return false;
	}
	if(fm.ContType.value=='1'){ //个险
		var Count=IndTempListGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(IndTempListGrid.getChkNo(i)==true){
				if(IndTempListGrid.getRowColData(i,11)=='03'){
					myAlert(''+"修改"+'"临分""+"状态的审核结论必须先在临分保单关联页面中取消临分保单关联，请点击[临时分保]按钮。"+"');
					return false;
				}
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"请在查询结果中选中责任保单"+"");
			return false;
		}
		var showStr=""+"正在保存资料......"+"";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
		//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论 
		fm.action = "./TempUWConclusionSave.jsp?ContType=1&CONOPETYPE=02&UWFLAG=0&SerialNo="+TempInsuListGrid.getRowColData(tSel-1,8);
	}else{ //团险
		var Count=SearchResultGrid.mulLineCount; 
		var chkFlag=false; 
		for (i=0;i<Count;i++){
			if(SearchResultGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if (chkFlag==false){
			myAlert(""+"请在查询结果中选中责任保单"+"");
			return false;
		}
		var showStr=""+"正在保存资料......"+""; 
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
		//CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'1'-核保临分结论，'0'-再保临分结论 
		fm.action = "./TempUWConclusionSave.jsp?ContType=2&CONOPETYPE=02&UWFLAG=0&SerialNo="+TempInsuListGrid.getRowColData(tSel-1,8);
	}
  fm.submit(); 
}

function TempCessButton(){

		var num=0;
		var Count=IndTempListGrid.mulLineCount; 
		var arr = new  Array(Count);
		var RIPolno;
		for (var i=0;i<Count;i++){
			if(IndTempListGrid.getChkNo(i)==true){
				chkFlag=true;
				RIPolno = IndTempListGrid.getRowColData(i,2);
				//arr[i][1] = IndTempListGrid.getRowColData(i,5);
				num = num +1;
			}
		}
		if(num==0){
			myAlert(""+"请先在临分列表中选择临分任务"+"")
			return;
		}
		var varSrc="&RIPolno='"+RIPolno+"'";
  		window.open("./FrameMainCessInfo.jsp?Interface=RIPreceptChoiceInput.jsp"+varSrc,"true");
	
}

function afterCodeSelect( cCodeName, Field ) 
{
	if(Field.value=='01')
	{
		divButton1.style.display='';
		divSearch.style.display='';
		divContPlanCodeName.style.display='none';
		divDutyCodeName.style.display='none';
		fm.DutyCode1.style.display='none';
		fm.ContPlanMode.style.display='none';
		fm.AllConClusion.style.display='';
	}
	else if(Field.value=='02')
	{
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='';
		fm.ContPlanMode.style.display='';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		fm.AllConClusion.style.display="none";
		
	}
	else if(Field.value=='03')
	{
		divButton1.style.display='none';
		divSearch.style.display='none';
		divContPlanCodeName.style.display='none';
		fm.ContPlanMode.style.display='none';
		divDutyCodeName.style.display='';
		fm.DutyCode1.style.display='';
		fm.AllConClusion.style.display="none";
	}
} 
function AuditEnd(){
	if(!confirm(""+"是否确认临分任务处理完毕"+"")){
		return false;
	}
	var  chos = IndTempToalListGrid.getSelNo();
	if(chos==0){
		myAlert(""+"请选择个险保单信息"+" "+"！"+"");
		return;
	}
	var state = IndTempToalListGrid.getRowColData(chos-1,10);
	if(state!="02"){
		myAlert(""+"请选择状态为临分审核的保单！"+"");
		return;
	}
	var SerialNo = IndTempToalListGrid.getRowColData(chos-1,11);
	var  sqlRIDutyState = "select count(*) from RIDutyState a where a.SerialNo='"+SerialNo+"' and a.state !='03'";
	var result = easyExecSql(sqlRIDutyState);

	var showStr=""+"正在处理数据......"+"";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(showStr)) ;
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//ContType:'1' 个险 '2' 团险 ；CONOPETYPE:'01'-全部下结论,'02'-选中的下结论,'03'-取消临分结论；UWFLAG:'0'-再保临分结论，'1'-核保临分结论，'2'-处理完毕
	fm.action = "./TempUWConclusionSave.jsp?CONOPETYPE=04&UWFLAG=2&SerialNo="+SerialNo; 
	fm.submit(); 
}

