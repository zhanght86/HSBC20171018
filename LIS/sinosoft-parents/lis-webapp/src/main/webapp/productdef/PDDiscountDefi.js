//�������ƣ�PDDiscountDefi.js
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2011-03-03
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
//����sql�����ļ�
var tResourceName = "productdef.PDDiscountDefiInputSql";

function InsertClick() {
  if(checkRowSelect()){
  	myAlert(""+"���ۿ��Ѿ�������������ٴα���"+"");
  	return;
  }
  fm.all('mOperator').value = "save";
  submitForm();
}

function update(){
	if(!checkRowSelect()){
		myAlert(""+"����ѡ��Ҫ�޸ĵ���"+"");
		return;
	}
	fm.all('mOperator').value = "update";
  submitForm();
}

function del(){
	if(!checkRowSelect()){
		myAlert(""+"����ѡ��Ҫɾ������"+"");
		return;
	}
	fm.all('mOperator').value = "del";
  submitForm();
}

function submitForm()
{
	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fm.submit(); //�ύ
}

function setDiscountCode(tDiscountCode)
{
	fm.all('DiscountCode').value = tDiscountCode;
}

function defCalCode()
{
	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
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
   //��ѯ����
  var mySql=new SqlClass();
	var sqlid = "PDDiscountDefiInputSql2";
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(tDutyCode);//ָ������Ĳ���
	mySql.addSubPara(tAddFeeDiscFlag);//ָ������Ĳ���
	mySql.addSubPara(tDiscountType);//ָ������Ĳ���
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
	mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(tRiskCode);//ָ������Ĳ���
	var sql = mySql.getString();
	initPremDiscountGrid();
  turnPage1.queryModal(sql,PremDiscountGrid);
}

/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnParent(){
  top.close();
}

function defCalRule()
{
	//�������δ¼��,��������й�����	
	/*
	../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode=���ֱ���&RuleName=�������&RuleDes=��������(Ԥ��)&Creator=����Ա
	&RuleStartDate=������Ч����&Business=99&State=0
	*/
	 var tCalCode = fm.all('CalCode').value;
	 if(tCalCode==null||tCalCode=="")
	 {
	 	 myAlert(''+"���ȱ�����Ϣ�����㷨����!"+'');
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
