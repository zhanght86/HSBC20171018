//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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



// ���Ѳ�Ѱ
function FeeQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelFeeQueryMain.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
		 // window.open("../sys/AllFeeQueryPDetail.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag);										
	}
}




// �������Ѱ
function GetItemQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = document.all('ContNo').value;
	
		if (cContNo== "" ||cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/GetItemQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"',sFeatures");										
	}
}

// �ݽ��Ѳ�ѯ
function TempFeeQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    //var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
	      var cContNo = document.all('ContNo').value;
		
		if (cContNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PolTempFeeQueryMain.jsp?ContNo=" + cContNo  + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"',sFeatures");										
	}
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

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PremQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
	}
}

// �ʻ���ѯ
function InsuredAccQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/InsureAccQueryMain.jsp?PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"',sFeatures");										
	}
}

// ������Ѱ
function GetQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
	
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/RelGetQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
	}
}

//������ȡ��ѯ
function LiveGetQuery()
{
	//alert('���ڿ�������');
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = document.all('ContNo').value;
	
		if (cContNo== "" ||cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
          window.open("../sys/RelGetQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName + "&IsCancelPolFlag=" + tIsCancelPolFlag,"',sFeatures");
	}

}


//���Ĳ��˷Ѳ�ѯ
function EdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,6);
		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/EdorQuery.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName  + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);										
	}
}



// �潻/����Ѳ�ѯ
function LoLoanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
           // alert("cPolNo"+cPolNo);
		  window.open("../sys/LoLoanQueryMain.jsp?ContNo=" + tContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag,"",sFeatures);										
	}
}

//��ȫ��ѯ
function PerEdorQueryClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/AllPBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);										
	}	
}


//��ȫ��ѯ
function BqEdorQueryClick()
{
    window.open("../sys/PolBqEdorMain.jsp?ContNo=" + tContNo + "&flag=0" + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);	
}


//���������ѯ
function BonusQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
        var cContNo = document.all('ContNo').value;
		if (cContNo== "" ||cPolNo == "")
		    return;
		  window.open("../sys/BonusQueryMain.jsp?ContNo=" + cContNo + "&PolNo=" + cPolNo + "&IsCancelPolFlag=" + tIsCancelPolFlag ,"",sFeatures);										
	}
}

//�����ձ�������
function WannengQuery()
{
	alert('���ڿ�������');
}

//Ƿ�����Ѳ�ѯ
function OwePremQuery()
{
	alert('���ڿ�������');
}


// ���ղ�Ѱ
function MainRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo==cMainPolNo)
			alert("��ѡ�����һ���������ݣ�����ѡ��һ�����������ݺ��ٵ�����ղ�ѯ��ť��")
		else 
			{
									
				if (cMainPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// ��дSQL���
						var mSQL = "";
						
var sqlid3="ProposalQuerySql3";
var mySql3=new SqlClass();
mySql3.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
mySql3.addSubPara(cMainPolNo);//ָ������Ĳ���
mSQL=mySql3.getString();
						
						
						//mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";			 
						
						//��ѯSQL�����ؽ���ַ���
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
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
					  turnPage.strQuerySql     = mSQL; 
					  
					  //���ò�ѯ��ʼλ��
					  turnPage.pageIndex = 0;  
					  
					  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
					  
					  //����MULTILINE������ʾ��ѯ���
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}	
	}
}
//function MainRiskQuery()
//{
//	var arrReturn = new Array();
//	var tSel = PolGrid.getSelNo();
//	
//	if( tSel == 0 || tSel == null )
//		alert( "����ѡ��һ����¼��" );
//	else
//	{
//	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
//			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
//		
//		if (cPolNo==cMainPolNo)
//			alert("��ѡ�����һ���������ݣ�����ѡ��һ�����������ݺ��ٵ�����ղ�ѯ��ť��")
//		else
//			{
//					
//					parent.VD.gVSwitch.deleteVar("PolNo");				
//					parent.VD.gVSwitch.addVar("PolNo","",cMainPolNo);
//					
//					if (cMainPolNo == "")
//					    return;
//					    
//					var tSQL = "select GrpPolNo from LCPol where PolNo='" + cMainPolNo +"'";
//					var tData = easyExecSql(tSQL,1,0,1);    
//					var GrpPolNo = tData[0][0];
//			
//				    if (GrpPolNo =="00000000000000000000") {
//						window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
//					} else {
//						window.open("./AllProQueryMain.jsp?LoadFlag=4");	
//					}
//			}							
//	}
//}


//�����ղ�ѯ
function OddRiskQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
			var cMainPolNo = PolGrid.getRowColData(tSel - 1,7);
		
		if (cPolNo!=cMainPolNo)
			alert("��ѡ�����һ�����������ݣ�����ѡ��һ���������ݺ��ٵ�������ղ�ѯ��ť��")
		else
			{
									
				if (cPolNo == "")
				    return;		    
				  
				  	initPolGrid();
	
						// ��дSQL���
						var mSQL = "";
						
						var sqlid4="ProposalQuerySql4";
var mySql4=new SqlClass();
mySql4.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
mySql4.addSubPara(cMainPolNo);//ָ������Ĳ���
mySql4.addSubPara(cPolNo);//ָ������Ĳ���
mSQL=mySql4.getString();
						
						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";			 
						
						//��ѯSQL�����ؽ���ַ���
					  turnPage.strQueryResult  = easyQueryVer3(mSQL, 1, 0, 1);  
					  
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
					  turnPage.strQuerySql     = mSQL; 
					  
					  //���ò�ѯ��ʼλ��
					  turnPage.pageIndex = 0;  
					  
					  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
					  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
					  
					  //����MULTILINE������ʾ��ѯ���
					  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			}	
	}
}

// ������ϸ��ѯ
function PolClick1()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cPrtNo = PolGrid.getRowColData(tSel - 1,3);				
	    var cGrpContNo=PolGrid.getRowColData(tSel - 1,1);	
            if (cPrtNo == "")
	      return;
            if(cGrpContNo=="00000000000000000000")            
              //window.open("../sys/PolDetailQueryMain.jsp");
              window.open("../app/ContInputNoScanMain.jsp?prtNo="+ cPrtNo+"&LoadFlag=6","",sFeatures);             
            else
	      // Window.open("./GrpPolDetailQueryMain.jsp?PrtNo="+ cGrpContNo+"&LoadFlag=16");
	      window.open("../app/GroupPolApproveInfo.jsp?polNo="+ cGrpContNo+"&LoadFlag=16","",sFeatures);		    		
	}
}


// ���������ѯ
function ClaimGetQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
	}	
}
//��ע��Ϣ��ѯ
function ShowRemark()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel-1,2);				
		if (cPolNo == "")
		    return;
		  window.open("../sys/FrameRemarkMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
	}	
}


//ɨ�����ѯ
function ScanQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1","",sFeatures);								
	}	     
}

//���屣��ɨ�����ѯ
function ScanQuery2()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var prtNo = PolGrid.getRowColData(tSel - 1,3);				

		
		if (prtNo == "")
		    return;
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1","",sFeatures);								
	}	     
}

function GoBack(){
	
	//top.opener.easyQueryClick();
	top.close();
	
	}
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
  }
}	
function queryAgent()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(document.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(document.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	
var sqlid5="ProposalQuerySql5";
var mySql5=new SqlClass();
mySql5.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
mySql5.addSubPara(cAgentCode);//ָ������Ĳ���
mySql5.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
var strSql =mySql5.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}	
function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	
	var sqlid6="ProposalQuerySql6";
var mySql6=new SqlClass();
mySql6.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
mySql6.addSubPara(cAgentCode);//ָ������Ĳ���
mySql6.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
var strSql =mySql6.getString();
	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}  	