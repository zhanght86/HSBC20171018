//程序名称：UWCustomerQuality.js
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
function queryClick()
{
	// 初始化表格
	//initQuestGrid();
	//initContent();
	
	// 书写SQL语句
//  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
//             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
//             + getWherePart('Operator', 'Operator')
//             + getWherePart('MakeDate', 'MakeDate');
	
	var  Operator1 = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  MakeDate1 = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid2="PremDiscountDefInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("config.PremDiscountDefInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(PrtNo);//指定传入的参数
	mySql2.addSubPara(ContNo);//指定传入的参数
	mySql2.addSubPara(OperatePos);//指定传入的参数
	mySql2.addSubPara(Operator2);//指定传入的参数
	mySql2.addSubPara(MakeDate2);//指定传入的参数
	var strSql=mySql2.getString();
  
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function InsertClick() {
  
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit(); //提交
}

function setDiscountCode(tDiscountCode)
{
	document.all('DiscountCode').value = tDiscountCode;
}



function defCalCode()
{
	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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
		mySql1.setResourceName("config.PremDiscountDefInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(tDiscountTypeQ);//指定传入的参数
    mySql1.addSubPara(tDiscountCodeQ);//指定传入的参数
    mySql1.addSubPara(tCampaignCodeQ);//指定传入的参数
    mySql1.addSubPara(tAddFeeDiscFlagQ);//指定传入的参数
    mySql1.addSubPara(tRiskCodeQ);//指定传入的参数
    mySql1.addSubPara(tDutyCodeQ);//指定传入的参数
    mySql1.addSubPara(tStartDateQ);//指定传入的参数
    mySql1.addSubPara(tEndDateQ);//指定传入的参数
         
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
	 var tCalCode = document.all('CalCode').value;
/*	 if(tCalCode==null||tCalCode=="")
	 {
	 	 alert('请先录入算法编码!');
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