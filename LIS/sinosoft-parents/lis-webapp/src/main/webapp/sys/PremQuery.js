//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClick();
	top.close();
}
//
//// ���ݷ��ظ�����
//function getQueryDetail()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼��" );
//	else
//	{
//	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
////		parent.VD.gVSwitch.deleteVar("PayNo");				
////		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
//		
//		if (cPayNo == "")
//		    return;
//		    
//		  var cIncomeType = PolGrid.getRowColData(tSel - 1,3);
//		  var cSumActuPayMoney = 	PolGrid.getRowColData(tSel - 1,4);
//		  //alert(cSumActuPayMoney);
//		  if (cIncomeType==0||cIncomeType==1||cIncomeType==2) {
//		  //var urlstr1="../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo+ "&SumActuPayMoney=" + cSumActuPayMoney;
//		  //alert(urlstr1); 
//				window.open("../sys/AllFeeQueryPDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
//			}	
//			else {
//				//var urlstr1="../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo+ "&SumActuPayMoney=" + cSumActuPayMoney;
//		  	//alert(urlstr1); 
//				window.open("../sys/AllFeeQueryEDetail.jsp?PayNo=" + cPayNo + "&SumActuPayMoney=" + cSumActuPayMoney);	
//							
//			}
//	}
//}



function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
		
		var sqlid1="PremQuerySQLl";
		var mySql1=new SqlClass();
		mySql1.setResourceName("sys.PremQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tContNo);//ָ������Ĳ���
		mySql1.addSubPara(tPolNo);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
		
//	strSQL = " select GrpContNo,DutyCode,PayPlanCode,(select decode(PayPlanType,0,'��������',01,'�����ӷ�',02,'ְҵ�ӷ�',03,'��Ч�����ӷ�',04,'��Чְҵ�ӷ�') from dual),PayTimes,"
//	       + " Rate,PayStartDate,PayEndDate,PaytoDate, (Select codename from ldcode where codetype = 'payintv' and code = lcprem.PayIntv), StandPrem,Prem,SumPrem"
//	       + " from LCPrem  where ContNo='" + tContNo + "'"
//	       + " and PolNo = '" + tPolNo + "'";
	}
	else
	if(tIsCancelPolFlag=="1"){//����������ѯ
	
		var sqlid2="PremQuerySQL2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("sys.PremQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(tPolNo);//ָ������Ĳ���
	    strSQL=mySql2.getString();	
	
//	  strSQL = "select LBPrem.PolNo,LBPrem.PayPlanCode,LBPrem.PayPlanType,LBPrem.PayTimes,LBPrem.Mult,LBPrem.Prem,LBPrem.SumPrem,LBPrem.Rate,LBPol.AppntName,LBPol.riskcode,LBPrem.PaytoDate,LBPrem.PayIntv ,,LCPrem.managefeerate from LBPol,LBPrem where LBPrem.PolNo = '" + tPolNo + "' and LBPol.PolNo = LBPrem.PolNo";			 
	}
	else {
	  alert("�������ʹ������!");
	  return;
	  }
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
    //alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}