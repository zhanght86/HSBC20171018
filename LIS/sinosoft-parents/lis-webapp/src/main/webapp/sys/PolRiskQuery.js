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
//	alert(1);
	// ��ʼ�����
	initPolGrid();
	//alert(tPrtNo);
	// ��дSQL���
	
		var sqlid1="PolRiskQuerySql";
		var mySql1=new SqlClass();
		mySql1.setResourceName("sys.PolRiskQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tPrtNo);//ָ������Ĳ���
	    var strSQL=mySql1.getString();	
	
//	var strSQL = "";
//	strSQL = 
//	" select a.contno,a.polno,"+
//       " a.riskcode,"+
//       " (select riskname from lmrisk where trim(riskcode) = trim(a.riskcode)),"+
//       " a.amnt,"+
//       " a.mult,"+
//       " a.prem,"+
//       " a.standprem,"+
//       " nvl((select codename"+
//       " from ldcode"+
//       " where codetype = 'uwstate'"+
//       " and trim(code) = trim(a.uwflag)),"+
//       " '')"+
//       " from lcpol a where a.prtno='"+tPrtNo+"' order by a.polno";
 // alert(strSQL);
	
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

// �������ѯ
function PremQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = PolGrid.getRowColData(tSel - 1,1);
		
		if (cContNo == ""||cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PremQueryMain.jsp?ContNo=" + cContNo+ "&PolNo=" + cPolNo + "&IsCancelPolFlag=0");										
	}
}
