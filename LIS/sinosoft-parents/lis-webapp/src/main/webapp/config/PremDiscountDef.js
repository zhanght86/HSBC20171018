//�������ƣ�UWCustomerQuality.js
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
function queryClick()
{
	// ��ʼ�����
	//initQuestGrid();
	//initContent();
	
	// ��дSQL���
//  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
//             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
//             + getWherePart('Operator', 'Operator')
//             + getWherePart('MakeDate', 'MakeDate');
	
	var  Operator1 = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  MakeDate1 = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid2="PremDiscountDefInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("config.PremDiscountDefInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(PrtNo);//ָ������Ĳ���
	mySql2.addSubPara(ContNo);//ָ������Ĳ���
	mySql2.addSubPara(OperatePos);//ָ������Ĳ���
	mySql2.addSubPara(Operator2);//ָ������Ĳ���
	mySql2.addSubPara(MakeDate2);//ָ������Ĳ���
	var strSql=mySql2.getString();
  
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function InsertClick() {
  
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit(); //�ύ
}

function setDiscountCode(tDiscountCode)
{
	document.all('DiscountCode').value = tDiscountCode;
}



function defCalCode()
{
	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initAll();
  easyQueryClick();
}



function easyQueryClick(){
 
   /*          
   var tSql = " select discounttype,discountcode,CampaignCode,AddFeeDiscFlag,RiskCode,DutyCode,StartDate,EndDate,CalCode "
            + " from lmdiscount "
            +getWherePart('DiscountType','DiscountTypeQ')
            +getWherePart('DiscountCode','DiscountCodeQ')
            +getWherePart('CampaignCode','CampaignCodeQ')
            +getWherePart('AddFeeDiscFlag','AddFeeDiscFlagQ')
            +getWherePart('RiskCode','RiskCodeQ')
            
            +getWherePart('DutyCode','DutyCodeQ')
            +getWherePart('StartDate','StartDateQ','>=')
            +getWherePart('EndDate','EndDateQ','<=');
            
            
              document.all('DiscountType').value = tDiscountType;
   document.all('DiscountCode').value = tDiscountCode;
   document.all('CampaignCode').value = tCampaignCode;
   document.all('AddFeeDiscFlag').value = tAddFeeDiscFlag;
   document.all('RiskCode').value = tRiskCode;
   document.all('DutyCode').value = tDutyCode;
   document.all('StartDate').value = tStartDate;
   document.all('EndDate').value = tEndDate;
   document.all('CalCode').value = tCalCode;
    */
   var tDiscountTypeQ = document.all('DiscountTypeQ').value;
   var tDiscountCodeQ = document.all('DiscountCodeQ').value;
   var tCampaignCodeQ = document.all('CampaignCodeQ').value;
   var tAddFeeDiscFlagQ = document.all('AddFeeDiscFlagQ').value;
   var tRiskCodeQ = document.all('RiskCodeQ').value;
   var tDutyCodeQ = document.all('DutyCodeQ').value;
   var tStartDateQ = document.all('StartDateQ').value;
   var tEndDateQ = document.all('EndDateQ').value;
   //var tCalCode = PremDiscountGrid.getRowColData(tSelNo,9);
   
    var sqlid1="PremDiscountDefInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("config.PremDiscountDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(tDiscountTypeQ);//ָ������Ĳ���
    mySql1.addSubPara(tDiscountCodeQ);//ָ������Ĳ���
    mySql1.addSubPara(tCampaignCodeQ);//ָ������Ĳ���
    mySql1.addSubPara(tAddFeeDiscFlagQ);//ָ������Ĳ���
    mySql1.addSubPara(tRiskCodeQ);//ָ������Ĳ���
    mySql1.addSubPara(tDutyCodeQ);//ָ������Ĳ���
    mySql1.addSubPara(tStartDateQ);//ָ������Ĳ���
    mySql1.addSubPara(tEndDateQ);//ָ������Ĳ���
         
//	alert('no save11121');
	    var tempSQL2 =mySql1.getString();	
			initPremDiscountGrid();
   	 turnPage1.queryModal(tempSQL2,PremDiscountGrid);	        
             

   
}

function showPremDiscountDetail(){
   

   var tSelNo = PremDiscountGrid.getSelNo()-1;
   var tDiscountType = PremDiscountGrid.getRowColData(tSelNo,1);
   var tDiscountCode = PremDiscountGrid.getRowColData(tSelNo,2);
   var tCampaignCode = PremDiscountGrid.getRowColData(tSelNo,3);
   var tAddFeeDiscFlag = PremDiscountGrid.getRowColData(tSelNo,4);
   var tRiskCode = PremDiscountGrid.getRowColData(tSelNo,5);
   var tDutyCode = PremDiscountGrid.getRowColData(tSelNo,6);
   var tStartDate = PremDiscountGrid.getRowColData(tSelNo,7);
   var tEndDate = PremDiscountGrid.getRowColData(tSelNo,8);
   var tCalCode = PremDiscountGrid.getRowColData(tSelNo,9);
  
   document.all('DiscountType').value = tDiscountType;
   document.all('DiscountCode').value = tDiscountCode;
   document.all('CampaignCode').value = tCampaignCode;
   document.all('AddFeeDiscFlag').value = tAddFeeDiscFlag;
   document.all('RiskCode').value = tRiskCode;
   document.all('DutyCode').value = tDutyCode;
   document.all('StartDate').value = tStartDate;
   document.all('EndDate').value = tEndDate;
   document.all('CalCode').value = tCalCode;
//   document.all('DiscountType').value = tDiscountType;
   
 
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
	 var tCalCode = document.all('CalCode').value;
/*	 if(tCalCode==null||tCalCode=="")
	 {
	 	 alert('����¼���㷨����!');
	 	 return false
	 }
	 */
   var tRiskCode = document.all('RiskCode').value;
   var tStartDate = document.all('StartDate').value;
   var toperator = document.all('AddFeeDiscFlagQ').value;
   var tRiskCodeQ = document.all('RiskCodeQ').value;
   var tDutyCodeQ = document.all('DutyCodeQ').value;
   var tStartDateQ = document.all('StartDateQ').value;
   var tEndDateQ = document.all('EndDateQ').value;
   
   var tURL = "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+tRiskCode
             + "&RuleName="+tCalCode
             + "&RuleDes="+tRiskCode
             + "&Creator="+operator
             + "&RuleStartDate="+tStartDate
             + "&Business=99&State=0"
             + "&RuleModul=DS&RuleInputName=CalCode";
             
   window.open(tURL);
}