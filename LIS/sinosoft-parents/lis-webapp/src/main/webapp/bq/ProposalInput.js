function getGrpPolInfo() {
  //var strSql = "select * from lcgrppol where prtNo='" + prtNo + "' and riskCode='" + riskCode + "'";
	var sqlid1 = 'ProposalInputSql1';
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bq.ProposalInputSql"); // 指定使用的properties文件名
	mySql1.addSubPara(prtNo);// 指定传入的参数
	mySql1.addSubPara(riskCode);// 指定传入的参数
	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
	var strSQL = mySql1.getString();
	
  var arrResult = easyExecSql(strSQL);
  //alert(arrResult);
  
  try { document.all('GrpPolNo').value = arrResult[0][0]; } catch(ex) {};
  try { document.all('ContNo').value = arrResult[0][1]; } catch(ex) {};
  try { document.all('GrpProposalNo').value = arrResult[0][2]; } catch(ex) {};
  try { document.all('PrtNo').value = arrResult[0][3]; } catch(ex) {};
  try { document.all('KindCode').value = arrResult[0][4]; } catch(ex) {};
  try { document.all('RiskCode').value = arrResult[0][5]; } catch(ex) {};
  try { document.all('RiskVersion').value = arrResult[0][6]; } catch(ex) {};
  try { document.all('SignCom').value = arrResult[0][7]; } catch(ex) {};
  try { document.all('ManageCom').value = arrResult[0][8]; } catch(ex) {};
  try { document.all('AgentCom').value = arrResult[0][9]; } catch(ex) {};
  try { document.all('AgentType').value = arrResult[0][10]; } catch(ex) {};
  try { document.all('SaleChnl').value = arrResult[0][11]; } catch(ex) {};
  try { document.all('AppGrpNo').value = arrResult[0][13]; } catch(ex) {};
  try { document.all('AppGrpName').value = arrResult[0][15]; } catch(ex) {};
  try { document.all('GrpAddressCode').value = arrResult[0][16]; } catch(ex) {};
  try { document.all('AppGrpAddress').value = arrResult[0][17]; } catch(ex) {};
  try { document.all('AppGrpZipCode').value = arrResult[0][18]; } catch(ex) {};
  try { document.all('BusinessType').value = arrResult[0][19]; } catch(ex) {};
  try { document.all('GrpNature').value = arrResult[0][20]; } catch(ex) {};
  try { document.all('Peoples2').value = arrResult[0][21]; } catch(ex) {};
  try { document.all('RgtMoney').value = arrResult[0][22]; } catch(ex) {};
  try { document.all('Asset').value = arrResult[0][23]; } catch(ex) {};
  try { document.all('NetProfitRate').value = arrResult[0][24]; } catch(ex) {};
  try { document.all('MainBussiness').value = arrResult[0][25]; } catch(ex) {};
  try { document.all('Corporation').value = arrResult[0][26]; } catch(ex) {};
  try { document.all('ComAera').value = arrResult[0][27]; } catch(ex) {};
  try { document.all('LinkMan1').value = arrResult[0][28]; } catch(ex) {};
  try { document.all('Department1').value = arrResult[0][29]; } catch(ex) {};
  try { document.all('HeadShip1').value = arrResult[0][30]; } catch(ex) {};
  try { document.all('Phone1').value = arrResult[0][31]; } catch(ex) {};
  try { document.all('E_Mail1').value = arrResult[0][32]; } catch(ex) {};
  try { document.all('Fax1').value = arrResult[0][33]; } catch(ex) {};
  try { document.all('LinkMan2').value = arrResult[0][34]; } catch(ex) {};
  try { document.all('Department2').value = arrResult[0][35]; } catch(ex) {};
  try { document.all('HeadShip2').value = arrResult[0][36]; } catch(ex) {};
  try { document.all('Phone2').value = arrResult[0][37]; } catch(ex) {};
  try { document.all('E_Mail2').value = arrResult[0][38]; } catch(ex) {};
  try { document.all('Fax2').value = arrResult[0][39]; } catch(ex) {};
  try { document.all('Fax').value = arrResult[0][40]; } catch(ex) {};
  try { document.all('Phone').value = arrResult[0][41]; } catch(ex) {};
  try { document.all('GetFlag').value = arrResult[0][42]; } catch(ex) {};
  try { document.all('Satrap').value = arrResult[0][43]; } catch(ex) {};
  try { document.all('EMail').value = arrResult[0][44]; } catch(ex) {};
  try { document.all('FoundDate').value = arrResult[0][45]; } catch(ex) {};
  //try { document.all('BankAccNo').value = arrResult[0][46]; } catch(ex) {};
  //try { document.all('BankCode').value = arrResult[0][47]; } catch(ex) {};
  try { document.all('GrpGroupNo').value = arrResult[0][48]; } catch(ex) {};
  try { document.all('PayIntv').value = arrResult[0][49]; } catch(ex) {};
  try { document.all('PayMode').value = arrResult[0][50]; } catch(ex) {};
  
  //新保加人在jsp中指定了生效日期，不要集体的
  if (loadFlag != "7") {
    try { document.all('CValiDate').value = arrResult[0][51]; } catch(ex) {};
  }
  
  try { document.all('GetPolDate').value = arrResult[0][52]; } catch(ex) {};
  try { document.all('SignDate').value = arrResult[0][53]; } catch(ex) {};
  try { document.all('FirstPayDate').value = arrResult[0][54]; } catch(ex) {};
  try { document.all('PayEndDate').value = arrResult[0][55]; } catch(ex) {};
  try { document.all('PaytoDate').value = arrResult[0][56]; } catch(ex) {};
  try { document.all('RegetDate').value = arrResult[0][57]; } catch(ex) {};
  try { document.all('Peoples').value = arrResult[0][58]; } catch(ex) {};
  //try { document.all('Mult').value = arrResult[0][59]; } catch(ex) {};
  //try { document.all('Prem').value = arrResult[0][60]; } catch(ex) {};
  //try { document.all('Amnt').value = arrResult[0][61]; } catch(ex) {};
  try { document.all('SumPrem').value = arrResult[0][62]; } catch(ex) {};
  try { document.all('SumPay').value = arrResult[0][63]; } catch(ex) {};
  try { document.all('Dif').value = arrResult[0][64]; } catch(ex) {};
  try { document.all('SSFlag').value = arrResult[0][65]; } catch(ex) {};
  try { document.all('PeakLine').value = arrResult[0][66]; } catch(ex) {};
  try { document.all('GetLimit').value = arrResult[0][67]; } catch(ex) {};
  try { document.all('GetRate').value = arrResult[0][68]; } catch(ex) {};
  try { document.all('MaxMedFee').value = arrResult[0][69]; } catch(ex) {};
  try { document.all('ExpPeoples').value = arrResult[0][70]; } catch(ex) {};
  try { document.all('ExpPremium').value = arrResult[0][71]; } catch(ex) {};
  try { document.all('ExpAmnt').value = arrResult[0][72]; } catch(ex) {};
  try { document.all('DisputedFlag').value = arrResult[0][73]; } catch(ex) {};
  try { document.all('BonusRate').value = arrResult[0][74]; } catch(ex) {};
  try { document.all('Lang').value = arrResult[0][75]; } catch(ex) {};
  try { document.all('Currency').value = arrResult[0][76]; } catch(ex) {};
  try { document.all('State').value = arrResult[0][77]; } catch(ex) {};
  try { document.all('LostTimes').value = arrResult[0][78]; } catch(ex) {};
  try { document.all('AppFlag').value = arrResult[0][79]; } catch(ex) {};
  try { document.all('ApproveCode').value = arrResult[0][80]; } catch(ex) {};
  try { document.all('ApproveDate').value = arrResult[0][81]; } catch(ex) {};
  try { document.all('UWOperator').value = arrResult[0][82]; } catch(ex) {};
  try { document.all('AgentCode').value = arrResult[0][83]; } catch(ex) {};
  try { document.all('AgentGroup').value = arrResult[0][84]; } catch(ex) {};
  try { document.all('AgentCode1').value = arrResult[0][85]; } catch(ex) {};
  try { document.all('Remark').value = arrResult[0][86]; } catch(ex) {};
  try { document.all('UWFlag').value = arrResult[0][87]; } catch(ex) {};
  try { document.all('OutPayFlag').value = arrResult[0][88]; } catch(ex) {};
  try { document.all('ApproveFlag').value = arrResult[0][89]; } catch(ex) {};
  try { document.all('EmployeeRate').value = arrResult[0][90]; } catch(ex) {};
  try { document.all('FamilyRate').value = arrResult[0][91]; } catch(ex) {};
  //try { document.all('AccName').value = arrResult[0][97]; } catch(ex) {};
  try { document.all('PrintCount').value = arrResult[0][98]; } catch(ex) {};
  try { document.all('LastEdorDate').value = arrResult[0][99]; } catch(ex) {};
  try { document.all('ManageFeeRate').value = arrResult[0][100]; } catch(ex) {};
  try { document.all('GrpSpec').value = arrResult[0][101]; } catch(ex) {};
  try { document.all('GetPolMode').value = arrResult[0][102]; } catch(ex) {};
  try { document.all('PolApplyDate').value = arrResult[0][103]; } catch(ex) {};
  try { document.all('CustomGetPolDate').value = arrResult[0][104]; } catch(ex) {};


 // var strSql = "select * from ldgrp where grpno='" + document.all('AppGrpNo').value + "'";
    var sqlid2 = 'ProposalInputSql2';
	var mySql2 = new SqlClass();
	mySql2.setResourceName("bq.ProposalInputSql"); // 指定使用的properties文件名
	mySql2.addSubPara(document.all('AppGrpNo').value);// 指定传入的参数
	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
	var strsql = mySql2.getString();
  var arrResult = easyExecSql(strsql);
  
  //alert(arrResult);
  
  try { document.all('GrpNo').value = arrResult[0][0]; } catch(ex) {};
  try { document.all('Password').value = arrResult[0][1]; } catch(ex) {};
  //try { document.all('GrpName').value = arrResult[0][2]; } catch(ex) {};
  //try { document.all('GrpAddressCode').value = arrResult[0][3]; } catch(ex) {};
  //try { document.all('GrpAddress').value = arrResult[0][4]; } catch(ex) {};
  //try { document.all('GrpZipCode').value = arrResult[0][5]; } catch(ex) {};
  try { document.all('BusinessType').value = arrResult[0][6]; } catch(ex) {};
  try { document.all('GrpNature').value = arrResult[0][7]; } catch(ex) {};
  try { document.all('Peoples').value = arrResult[0][8]; } catch(ex) {};
  try { document.all('RgtMoney').value = arrResult[0][9]; } catch(ex) {};
  try { document.all('Asset').value = arrResult[0][10]; } catch(ex) {};
  try { document.all('NetProfitRate').value = arrResult[0][11]; } catch(ex) {};
  try { document.all('MainBussiness').value = arrResult[0][12]; } catch(ex) {};
  try { document.all('Corporation').value = arrResult[0][13]; } catch(ex) {};
  try { document.all('ComAera').value = arrResult[0][14]; } catch(ex) {};
  try { document.all('LinkMan1').value = arrResult[0][15]; } catch(ex) {};
  try { document.all('Department1').value = arrResult[0][16]; } catch(ex) {};
  try { document.all('HeadShip1').value = arrResult[0][17]; } catch(ex) {};
  //try { document.all('Phone1').value = arrResult[0][18]; } catch(ex) {};
  try { document.all('E_Mail1').value = arrResult[0][19]; } catch(ex) {};
  try { document.all('Fax1').value = arrResult[0][20]; } catch(ex) {};
  try { document.all('LinkMan2').value = arrResult[0][21]; } catch(ex) {};
  try { document.all('Department2').value = arrResult[0][22]; } catch(ex) {};
  try { document.all('HeadShip2').value = arrResult[0][23]; } catch(ex) {};
  try { document.all('Phone2').value = arrResult[0][24]; } catch(ex) {};
  try { document.all('E_Mail2').value = arrResult[0][25]; } catch(ex) {};
  try { document.all('Fax2').value = arrResult[0][26]; } catch(ex) {};
  try { document.all('Fax').value = arrResult[0][27]; } catch(ex) {};
  //try { document.all('Phone').value = arrResult[0][28]; } catch(ex) {};
  try { document.all('GetFlag').value = arrResult[0][29]; } catch(ex) {};
  try { document.all('Satrap').value = arrResult[0][30]; } catch(ex) {};
  try { document.all('EMail').value = arrResult[0][31]; } catch(ex) {};
  try { document.all('FoundDate').value = arrResult[0][32]; } catch(ex) {};
  //try { document.all('BankAccNo').value = arrResult[0][33]; } catch(ex) {};
  //try { document.all('BankCode').value = arrResult[0][34]; } catch(ex) {};
  try { document.all('GrpGroupNo').value = arrResult[0][35]; } catch(ex) {};
  try { document.all('State').value = arrResult[0][36]; } catch(ex) {};
  try { document.all('Remark').value = arrResult[0][37]; } catch(ex) {};
  try { document.all('BlacklistFlag').value = arrResult[0][38]; } catch(ex) {};

}