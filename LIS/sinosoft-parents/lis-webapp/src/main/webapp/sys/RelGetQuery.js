//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
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


function easyQueryClick()
{
	
	//alert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
		
var sqlid35="ProposalQuerySql35";
var mySql35=new SqlClass();
mySql35.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
mySql35.addSubPara(tPolNo);//ָ������Ĳ���
strSQL=mySql35.getString();
		
	//strSQL = "select ActuGetNo,GetMoney,GetDate,ConfDate,Operator,ManageCom,AgentCode from LJAGetDraw where PolNo='" + tPolNo + "' ";			 
	}
	else
	if(tIsCancelPolFlag=="1"){//����������ѯ
	
	var sqlid36="ProposalQuerySql36";
var mySql36=new SqlClass();
mySql36.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
mySql36.addSubPara(tPolNo);//ָ������Ĳ���
strSQL=mySql36.getString();
	
	//strSQL = "select ActuGetNo,GetMoney,GetDate,ConfDate,Operator,ManageCom,AgentCode from LJAGetDraw where PolNo='" + tPolNo + "' ";			 
	}
	else {
	  alert("�������ʹ������!");
	  return;
	  }//alert(strSQL);
	
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




// ���ݷ��ظ�����
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼��" );
	else
	{
	    var cActuGetNo = PolGrid.getRowColData(tSel - 1,1);				
//		parent.VD.gVSwitch.deleteVar("PayNo");				
//		parent.VD.gVSwitch.addVar("PayNo","",cPayNo);
		
		if (cActuGetNo == "")
		    return;
		    
		  var cOtherNoType = PolGrid.getRowColData(tSel - 1,3);
		  var cSumGetMoney = 	PolGrid.getRowColData(tSel - 1,4);
		  //alert(cActuGetNo);
		  //alert(cOtherNoType);
		  //alert(cSumGetMoney);
		  if (cOtherNoType==0||cOtherNoType==1||cOtherNoType==2) {
				window.open("../sys/AllGetQueryDrawDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}	
			else if (cOtherNoType==3){
				window.open("../sys/AllGetQueryEdorDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);							
			}
			else if (cOtherNoType==4){
				window.open("../sys/AllGetQueryTempDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			else if (cOtherNoType==5){
				window.open("../sys/AllGetQueryClaimDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			else if (cOtherNoType==6){
				window.open("../sys/AllGetQueryOtherDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			else if (cOtherNoType==7){
				window.open("../sys/AllGetQueryBonusDetail.jsp?ActuGetNo=" + cActuGetNo + "&SumGetMoney=" + cSumGetMoney,"",sFeatures);	
			}
			
	}
}


function returnParent()
{
	//top.opener.initSelfGrid();
	//top.opener.easyQueryClick();
	top.close();
}

function easyQueryClick2()
{
	var strSQL = "";
	
var sqlid37="ProposalQuerySql37";
var mySql37=new SqlClass();
mySql37.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
mySql37.addSubPara(fm.all('ContNo').value);//ָ������Ĳ���
strSQL=mySql37.getString();
	
//	strSQL = "select appntname,insuredname from LCCont where ContNo='"+fm.all('ContNo').value+"'";
	var arrResult = easyExecSql(strSQL);
	if(arrResult!=null){
	  fm.all('InsuredName').value = arrResult[0][1] ;
    fm.all('AppntName').value = arrResult[0][0];		 
    }
	
	}