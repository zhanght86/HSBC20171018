//程序名称：PDDiscountDefi.js
//程序功能：客户品质管理
//创建日期：2011-03-03
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
//定义sql配置文件
var tResourceName = "productdef.PDDiscountDefiInputSql";

function InsertClick() {
  if(checkRowSelect()){
  	myAlert(""+"该折扣已经保存过，不能再次保存"+"");
  	return;
  }
  fm.all('mOperator').value = "save";
  submitForm();
}

function update(){
	if(!checkRowSelect()){
		myAlert(""+"请先选中要修改的行"+"");
		return;
	}
	fm.all('mOperator').value = "update";
  submitForm();
}

function del(){
	if(!checkRowSelect()){
		myAlert(""+"请先选中要删除的行"+"");
		return;
	}
	fm.all('mOperator').value = "del";
  submitForm();
}

function submitForm()
{
	var showStr=""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fm.submit(); //提交
}

function setDiscountCode(tDiscountCode)
{
	fm.all('DiscountCode').value = tDiscountCode;
}

function defCalCode()
{
	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  if(FlagStr=="Succ"){
	  initAll();
	  queryPremDiscountGrid();
	}
}

function showPremDiscountDetail(){
   var tSelNo = PremDiscountGrid.getSelNo()-1;
   var tDiscountType = PremDiscountGrid.getRowColData(tSelNo,1);
   var tDiscountCode = PremDiscountGrid.getRowColData(tSelNo,2);
   //var tCampaignCode = PremDiscountGrid.getRowColData(tSelNo,3);
   var tAddFeeDiscFlag = PremDiscountGrid.getRowColData(tSelNo,4);
   //var tRiskCode = PremDiscountGrid.getRowColData(tSelNo,5);
   var tDutyCode = PremDiscountGrid.getRowColData(tSelNo,6);
   var tStartDate = PremDiscountGrid.getRowColData(tSelNo,7);
   var tEndDate = PremDiscountGrid.getRowColData(tSelNo,8);
   var tCalCode = PremDiscountGrid.getRowColData(tSelNo,9);
  
   fm.all('DiscountType').value = tDiscountType;
   fm.all('DiscountCode').value = tDiscountCode;
   //fm.all('CampaignCode').value = tCampaignCode;
   fm.all('AddFeeDiscFlag').value = tAddFeeDiscFlag;
   //fm.all('RiskCode').value = tRiskCode;
   fm.all('DutyCode').value = tDutyCode;
   fm.all('StartDate').value = tStartDate;
   fm.all('EndDate').value = tEndDate;
   fm.all('CalCode').value = tCalCode;
   //查询名称
  var mySql=new SqlClass();
	var sqlid = "PDDiscountDefiInputSql2";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(tDutyCode);//指定传入的参数
	mySql.addSubPara(tAddFeeDiscFlag);//指定传入的参数
	mySql.addSubPara(tDiscountType);//指定传入的参数
	var sql = mySql.getString();
	var result = easyExecSql(sql,1,1,1);
	if(result){
		fm.all('DiscounTypeName').value = result[0][0];
		fm.all('DutyCodeName').value = result[0][1];
		fm.all('AddFeeDiscFlagName').value = result[0][2];
	}
}

function queryPremDiscountGrid()
{
	var mySql=new SqlClass();
	var sqlid = "PDDiscountDefiInputSql1";
	mySql.setResourceName(tResourceName); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(tRiskCode);//指定传入的参数
	var sql = mySql.getString();
	initPremDiscountGrid();
  turnPage1.queryModal(sql,PremDiscountGrid);
}

/*********************************************************************
 *  投保人查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent(){
  top.close();
}

function defCalRule()
{
	//如果编码未录入,不允许进行规则定义	
	/*
	../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode=险种编码&RuleName=规则编码&RuleDes=规则描述(预留)&Creator=操作员
	&RuleStartDate=规则有效日期&Business=99&State=0
	*/
	 var tCalCode = fm.all('CalCode').value;
	 if(tCalCode==null||tCalCode=="")
	 {
	 	 myAlert(''+"请先保存信息生成算法编码!"+'');
	 	 return false
	 }
   var tRiskCode = fm.all('RiskCode').value;
   var tStartDate = fm.all('StartDate').value;
   
   var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+tRiskCode
             + "&RuleName="+tCalCode
             + "&RuleDes="+tRiskCode
             + "&CreateModul=1"
             + "&Creator="+operator
             + "&RuleStartDate="+tStartDate
             + "&Business=99&State=0";
   window.open(tURL);
}

function checkRowSelect(){
	var tSelNo = PremDiscountGrid.getSelNo();
	if(tSelNo<1){
		return false;
	}
	return true;
}
