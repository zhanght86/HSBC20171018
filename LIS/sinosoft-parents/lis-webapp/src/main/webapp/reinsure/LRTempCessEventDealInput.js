	/*********************************************************************
 *  程序名称：LRTempCessInput.js
 *  程序功能：再保回复
 *  创建日期：2007-3-30 15:21
 *  创建人  ：zhang bin
 *  返回值：  无
 *  更新记录：  更新人    更新日期     更新原因/内容
 *********************************************************************
 */

var turnPage 	= new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
//alert(PrtNo);
window.onfocus=myonfocus;

/**
	*查询核保发起任务
	*/
 function QueryTempCessEvent(){ 	
	  /**
	  mSQL ="select z.eventno, z.grpcontno,z.riskcode,z.dutycode, z.insuredname,(case z.eventtype when '01' then '新单' when '02' then '续期' when'03' then '保全' else '理赔' end ), z.eventtype,z.insuredno,z.contno "
		+ " from ripolrecordbake z where z.eventno in (select a.a1 from (select eventno a1,contno a2,riskcode a3,dutycode a4 from ripolrecordbake where StandbyString5 is null or StandbyString5<>'Y') a ,"
		+ " (select distinct contno b1,riskcode b2,dutycode b3 from CasualRIContAssociate) b where trim(a.a2)=trim(b.b1) and a.a3=b.b2 and a.a4=b.b3)"
 		;
 		*/
 		var mySql100=new SqlClass();
			mySql100.setResourceName("reinsure.LRTempCessEventDealInputSql"); //指定使用的properties文件名
			mySql100.setSqlId("LRTempCessEventDealInputSql100");//指定使用的Sql的id
	    	mySql100.addSubPara("1");
	    	mSQL=mySql100.getString();
 		
		turnPage.queryModal(mSQL, TempEventGrid);
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

function showInputItem(){
	TempCessPreceptGrid.clearData();
	TempCessInputGrid.clearData();
	var tSel = TempEventGrid.getSelNo();
	var contNo=TempEventGrid.getRowColData(tSel-1,9);
	/**
	var strSQL = "select ricontno,ripreceptno from CasualRIContAssociate "
	+ " where trim(contNo)=trim('"+contNo+"') and riskcode='"+TempEventGrid.getRowColData(tSel-1,3)
	+"' and dutycode='"+TempEventGrid.getRowColData(tSel-1,4)
	+"' and ripreceptno not in (select distinct a.RIPreceptNo from reinsurerecordtrace a where a.eventno ='"+TempEventGrid.getRowColData(tSel-1,1)+"')"
	*/
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRTempCessEventDealInputSql"); //指定使用的properties文件名
		mySql101.setSqlId("LRTempCessEventDealInputSql101");//指定使用的Sql的id
		mySql101.addSubPara(contNo);//指定传入的参数
		mySql101.addSubPara(TempEventGrid.getRowColData(tSel-1,3));//指定传入的参数
		mySql101.addSubPara(TempEventGrid.getRowColData(tSel-1,4));//指定传入的参数
		mySql101.addSubPara(TempEventGrid.getRowColData(tSel-1,1));//指定传入的参数
	var strSQL=mySql101.getString();
	var arrReslut=easyExecSql(strSQL);
	if(arrReslut!=null){
		for (var i=0;i<arrReslut.length;i++){
			TempCessPreceptGrid.addOne();
			TempCessPreceptGrid.setRowColData(i,1,arrReslut[i][0]);
			TempCessPreceptGrid.setRowColData(i,2,arrReslut[i][1]);
		}
	}
}

function TempEventDeal(){
	if(verifyInput())
	{
		if(!verifyInput1()){
			return false;
		}
		var tSel = TempEventGrid.getSelNo();
		var eventType=TempEventGrid.getRowColData(tSel-1,7);
		var eventNo=TempEventGrid.getRowColData(tSel-1,1);
		var riskCode=TempEventGrid.getRowColData(tSel-1,3);
		var dutyCode=TempEventGrid.getRowColData(tSel-1,4);
		var contNo=TempEventGrid.getRowColData(tSel-1,9);
		var insuredNo=TempEventGrid.getRowColData(tSel-1,8);
		var tSel1 = TempCessPreceptGrid.getSelNo();
		var riContNo=TempCessPreceptGrid.getRowColData(tSel1-1,1);
		var riPreceptNo=TempCessPreceptGrid.getRowColData(tSel1-1,2);
		var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		fm.action="./LRTempCessEventDealSave.jsp?EventType="+eventType+"&EventNo="+eventNo
		+"&RiskCode="+riskCode+"&DutyCode="+dutyCode+"&ContNo="+contNo+"&InsuredNo="+insuredNo+"&RIPreceptNo="+riPreceptNo+"&RIContNo="+riContNo;
		fm.submit();
	}
}

function verifyInput1(){
	var tSel = TempEventGrid.getSelNo();
	if(tSel==0){
		myAlert(""+"请选择责任保单信息"+" ");
		return false;
	}
	var eventType=TempEventGrid.getRowColData(tSel-1,7);
	if(TempCessInputGrid.mulLineCount==0){
		myAlert(""+"请添加临分录入项"+" ");
		return false;
	}
	if(eventType=="04"){
		for(var i=0;i<TempCessInputGrid.mulLineCount;i++){
			if(TempCessInputGrid.getRowColData(i,10)==null||TempCessInputGrid.getRowColData(i,10)==""){
				myAlert(""+"请录入理赔摊回"+"");
				return false;
			}
		}
	}else{
		for(var i=0;i<TempCessInputGrid.mulLineCount;i++){
			if(TempCessInputGrid.getRowColData(i,4)==null||TempCessInputGrid.getRowColData(i,4)==""){
				myAlert(""+"请录入分保保额变量"+"");
				return false;
			}
			if(TempCessInputGrid.getRowColData(i,5)==null||TempCessInputGrid.getRowColData(i,5)==""){
				myAlert(""+"请录入分保保费变量"+"");
				return false;
			}
		}
	}
	for(var i=0;i<TempCessInputGrid.mulLineCount;i++){
		for(var j=4;j<TempCessInputGrid.colCount ;j++){
			if(TempCessInputGrid.getRowColData(i,j)!=null&&TempCessInputGrid.getRowColData(i,j)!=""){
				if(!isNumeric(TempCessInputGrid.getRowColData(i,j))){
					myAlert(""+"输入数字格式不对"+"");
					return false;
				}
			}else{
				TempCessInputGrid.setRowColData(i,j,"0");
			}
		}
	}
	return true;
}

function resetForm(){
	fm.InsuredNo	.value="";
	fm.InsuredName.value="";
	fm.RiskCode		.value="";
	fm.AppFlag		.value="";
	fm.AppFlagName.value="";
}
