//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";

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

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
function initQuery() {
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName from LCPol where "
    				 + " PrtNo='" + prtNo + "'"
    				 + " and PolNo<>MainPolNo";
  
  //alert(strSql);
	turnPage.queryModal(strSql, PolGrid);
}

//�޸Ĳ���
var mSwitch = parent.VD.gVSwitch;
function modifyClick() { 
  if (PolGrid.getSelNo()) { 
  	var cPolNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 1); 	
  	mSwitch.deleteVar( "PolNo" );
  	mSwitch.addVar( "PolNo", "", cPolNo );
  	
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var prtNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    window.open("./ProposalEasyScan.jsp?LoadFlag=3&Type="+type+"&prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}

//�������ѯ
function QuestQuery()
{
	  var i = 0;
	  var checkFlag = 0;
	  var cProposalNo = "";
	  var cflag = "2";
	  
	  for (i=0; i<PolGrid.mulLineCount; i++) {
	    if (PolGrid.getSelNo(i)) { 
	      checkFlag = PolGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cProposalNo = PolGrid.getRowColData(checkFlag - 1, 1); 
	        //alert(cProposalNo);
	  }
	  else {
	    alert("����ѡ��һ��������Ϣ��"); 
	    return false;
	  }
	
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("../uwgrp/QuestQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1");
	
}



