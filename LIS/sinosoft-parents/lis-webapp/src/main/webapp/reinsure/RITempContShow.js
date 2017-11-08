//该文件中包含客户端需要处理的函数和事件

//程序名称：RITempContShow.js
//程序功能：临分审核查询
//创建日期：2011-11-09
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


//查询按钮
function button134(){
	var sql = "select  a.Proposalgrpcontno,a.Proposalcontno,decode(a.State,'01','"+"临分发起"+"','02','"+"临分审核"+"','03','"+"临分处理"+"','04','"+"处理完毕"+"'),b.Riskcode,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno),b.Prem,b.Amnt,b.Riskamnt,a.Exetype,a.State,a.SerialNo " +
			" from RIGrpState a,lcpol b " +
			" where a.Proposalcontno=b.contno " +
			" and a.state='04' " +
			" and a.Proposalcontno like '"+fm.Polno.value+"%' ";
	turnPage.queryModal(sql, IndTempToalListGrid);
}

function  listSelect(){
	var tSel = IndTempToalListGrid.getSelNo();	
	var SerialNo = IndTempToalListGrid.getRowColData(tSel-1,11);
	mSQL = " select b.Contno A1,a.ProposalNo A2,(select x.Nameen from lcinsured x where x.Insuredno=b.Insuredno and x.Contno=b.Contno) A3,b.RiskCode A4,c.dutycode A5,c.Prem A6,c.Amnt A7,c.RiskAmnt A8,decode(a.State, '02', '"+"待临分审核"+"', '03', '"+"临分"+"', '04', '"+"临分处理完毕"+"') A9,a.State A10,(select x.Ricontno  from RITempContLink x where x.ProposalNo=a.Proposalno and x.Dutycode=a.Dutycode ) A11,(select x.Ripreceptno  from RITempContLink x where x.ProposalNo=a.Proposalno and x.Dutycode=a.Dutycode ) A12" +
			" from RIDutyState a, lcpol b, lcduty c " +
			" where 1=1 " +
			" and a.Proposalno=b.polno " +
			" and a.Proposalno=c.polno " +
			" and a.Dutycode=c.Dutycode " +
			" and a.SerialNo='"+SerialNo+"'" +
		    " and exists(select 'X'from RIGrpState d where d.SerialNo='"+SerialNo+"' and d.state='04')";
	turnPage2.queryModal(mSQL, IndTempListGrid);
}
//提交前的校验、计算  
function beforeSubmit(){
  //添加操作 
}

//使得从该窗口弹出的窗口能够聚焦
function myonfocus(){
 if(showInfo!=null){
   try{
     showInfo.focus();  
   }
   catch(ex){
     showInfo=null;
   }
 }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ){
  showInfo.close();
  if (FlagStr == "Fail" ){             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else{ 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI(content)) ;  
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}

function showDiv(cDiv,cShow){
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";  
  }
}
