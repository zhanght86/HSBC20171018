

var showInfo;
var mDebug="0";

var mSwitch = parent.VD.gVSwitch;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";

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




function getQueryDetail()
{	
        //var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1);
        
        var sqlid825172118="DSHomeContSql825172118";
		var mySql825172118=new SqlClass();
		mySql825172118.setResourceName("sys.GrpPolDetailQuerySql");//ָ��ʹ�õ�properties�ļ���
		mySql825172118.setSqlId(sqlid825172118);//ָ��ʹ�õ�Sql��id
		mySql825172118.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		var strSQL=mySql825172118.getString();
        
         var arrResult = easyExecSql(strSQL, 1, 0);                        
            if (arrResult !== null) {
		         var   polNo=arrResult[0][0];  
		         var  GrpPrtNo=arrResult[0][2];
        	}
        	     
    mSwitch.deleteVar("PolNo");
	mSwitch.addVar("PolNo", "", polNo);
	mSwitch.updateVar("PolNo", "", polNo);
	
	mSwitch.deleteVar("GrpContNo");
	mSwitch.addVar("GrpContNo", "", polNo);
	mSwitch.updateVar("GrpContNo", "", polNo);
	if(flag=="1"){
       easyScanWin = window.open("../cardgrp/GroupPolApproveInfo.jsp?LoadFlag=16&prtNo="+tPrtNo+"&polNo="+polNo+"&GrpPrtNo="+GrpPrtNo+"&SubType=TB1002&GrpContNo="+tGrpContNo, "", "status=no,resizable=yes,scrollbars=yes;"+sFeatures);   
	}else{
       easyScanWin = window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&prtNo="+tPrtNo+"&polNo="+polNo+"&GrpPrtNo="+GrpPrtNo+"&SubType=TB1002&GrpContNo="+tGrpContNo, "", "status=no,resizable=yes,scrollbars=yes;"+sFeatures);   
	}
		
}


function getQueryResult()
{
	
	var arrSelected = null;
	var tRow = PolGrid.getSelNo();
	
	if( tRow == null || tRow == 0 )
		return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0]= new Array();
	//������Ҫ���ص�����
	arrSelected[0][0] = PolGrid.getRowColData(tRow - 1,1);
	return arrSelected;
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
	    //var cPolNo = PolGrid.getRowColData(tSel - 1,1);
	    var cGrpContNo=document.all('GrpContNo').value;				

		
		if (cGrpContNo == "")
		    return;
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,5);
		  var cInsuredName = "";//PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,4);
		  window.open("../sys/GrpRelFeeQuery.jsp?GrpContNo=" + cGrpContNo + "&RiskCode=" + cRiskCode + "&AppntName=" + cAppntName,"",sFeatures);										
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/GetItemQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
	}
}

// �ݽ��Ѳ�ѯ
function TempFeeQuery()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	
	    
	  var cGrpContNo = document.all('GrpContNo').value; 		
		if (cGrpContNo == "")
		    return;
		    
		var sqlid825172231="DSHomeContSql825172231";
var mySql825172231=new SqlClass();
mySql825172231.setResourceName("sys.GrpPolDetailQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql825172231.setSqlId(sqlid825172231);//ָ��ʹ�õ�Sql��id
mySql825172231.addSubPara(cGrpContNo);//ָ������Ĳ���
var strSQL=mySql825172231.getString();
		
//		var strSQL="select AppntNo from LCGrpCont where GrpContNo='"+cGrpContNo+"'";
		var tAppntNo = easyExecSql(strSQL); 
		
		var sqlid825172315="DSHomeContSql825172315";
var mySql825172315=new SqlClass();
mySql825172315.setResourceName("sys.GrpPolDetailQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql825172315.setSqlId(sqlid825172315);//ָ��ʹ�õ�Sql��id
mySql825172315.addSubPara(cGrpContNo);//ָ������Ĳ���
var strSQL=mySql825172315.getString();
		
//		var strSQL="select APPntName from LJTempFee where OtherNo='"+cGrpContNo+"'group by APPntName";
		var tAppntName = easyExecSql(strSQL); 
		  
		window.open("../uw/ShowTempFee.jsp?Prtno="+cGrpContNo+"&AppntNo="+tAppntNo+"&AppntName="+tAppntName,"window1",sFeatures);  
		  //window.open("../sys/TempFeeQueryMain.jsp?GrpContNo=" + cGrpContNo + "&flag=0" + "&IsCancelPolFlag=0" );										
	
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/PremQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;

		  window.open("../sys/InsureAccQueryMain.jsp?PolNo=" + cPolNo + "&flag=0"  + "&IsCancelPolFlag=0","",sFeatures );										
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
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,5);
		  var cInsuredName = "";//PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,4);
		  window.open("../sys/RelGetQueryMain.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
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
		  var cRiskCode = PolGrid.getRowColData(tSel - 1,5);
		  var cInsuredName = "";//PolGrid.getRowColData(tSel - 1,4);
		  var cAppntName = PolGrid.getRowColData(tSel - 1,4);
		  window.open("../sys/EdorQuery.jsp?PolNo=" + cPolNo + "&RiskCode=" + cRiskCode + "&InsuredName=" + cInsuredName + "&AppntName=" + cAppntName,"",sFeatures);										
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
		  window.open("../sys/LoLoanQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);										
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
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				

		
		if (cPolNo == "")
		    return;
//		  var cRiskCode = PolGrid.getRowColData(tSel - 1,3);
//		  var cInsuredName = PolGrid.getRowColData(tSel - 1,4);
//		  var cAppntName = PolGrid.getRowColData(tSel - 1,5);
		  window.open("../sys/AllGBqQueryMain.jsp?PolNo=" + cPolNo + "&flag=0","",sFeatures);										
	}	
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
						
						var sqlid825172411="DSHomeContSql825172411";
var mySql825172411=new SqlClass();
mySql825172411.setResourceName("sys.GrpPolDetailQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql825172411.setSqlId(sqlid825172411);//ָ��ʹ�õ�Sql��id
mySql825172411.addSubPara(cMainPolNo);//ָ������Ĳ���
mSQL=mySql825172411.getString();
						
//						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where PolNo='" + cMainPolNo +"'";			 
						
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
{//alert(380);
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
						
						var sqlid825172527="DSHomeContSql825172527";
var mySql825172527=new SqlClass();
mySql825172527.setResourceName("sys.GrpPolDetailQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql825172527.setSqlId(sqlid825172527);//ָ��ʹ�õ�Sql��id
mySql825172527.addSubPara(cMainPolNo);//ָ������Ĳ���
mySql825172527.addSubPara(cPolNo);//ָ������Ĳ���
mSQL=mySql825172527.getString();

						
//						mSQL = "select PolNo,PrtNo,RiskCode,InsuredName,AppntName,GrpPolNo,MainPolNo from LCPol where MainPolNo='" + cMainPolNo + "' and PolNo!='" + cPolNo + "'";			 
						
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
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		
		if (cPolNo == "")
		    return;		    
 
		    window.open("../sys/PolDetailQueryMain.jsp?PolNo=" + cPolNo,"",sFeatures);	
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

function ClaimQuery()
{
	      var tGrpContNo = document.all('GrpContNo').value;
          window.open("../sys/GRPClaimQueryMain.jsp?GrpContNo=" + tGrpContNo + "" ,"",sFeatures);										

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
	    var prtNo = PolGrid.getRowColData(tSel - 1,2);				
      var ContNo = document.all('PrtNo').value;
		
		  if (prtNo == "")
		    return;
		  if (ContNo == "")
	    {
		    alert("û�еõ�Ͷ��������Ϣ��");
		    return;
	    }
		   
//		  window.open("../sys/ClaimGetQueryMain.jsp?PolNo=" + cPolNo);		
//		  window.open("./ProposalEasyScan.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
        window.open("../uw/ImageQueryMainGrp.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
	}	     
}

function GoBack(){
	
	//top.opener.easyQueryClick();
	top.opener = null;
	top.close();
	
	}
	
function GrpPolSingleQueryClick(){
	
	
	  
	 
		try
		{
			
			window.open("./GrpPolSingleQueryMain.jsp?GrpContNo="+ document.all('GrpContNo').value,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}



}