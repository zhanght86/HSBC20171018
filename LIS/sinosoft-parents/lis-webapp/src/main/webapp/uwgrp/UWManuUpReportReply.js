//�������ƣ�PEdorUWManuRReport.js
//�����ܣ���ȫ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //�������Ͳ���λ�� 1.����
var sqlresourcename = "uwgrp.UWManuUpReportReplySql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
 
  if(fm.SubMissionID.value == "")
  {
  	
  	parent.close();
  	return;
  	}
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

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

//��ʼ��������Ϣ
function easyQueryClick()
{
	
	var tContNo = fm.ProposalNoHide.value;
	
	//var strSQL = "select ContNo,SaleChnl,AgentCode,ManageCom,BankCode from lccont where contno='"+tContNo+"'";
	
	var sqlid1="UWManuUpReportReplySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("��ѯ����������Ϣʧ��")
  	return "";
  }
else
	{
	fm.all('ContNo').value = arrSelected[0][0];
	fm.all('SellType').value = arrSelected[0][1];
	fm.all('AgentCode').value = arrSelected[0][2];
	fm.all('ManageCom').value = arrSelected[0][3];
	fm.all('BankCode').value = arrSelected[0][4];
  }
	
	//var strSQl = "select ReportRemark,ReinsuDesc,ReinsuRemark,ReinsuredResult,usercode,makedate,modifydate from LCReinsureReport where contno = '"+tContNo+"'";
	
	var sqlid2="UWManuUpReportReplySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
	
	turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("û���ٱ��ظ���Ϣ!")
  	return "";
  }
else
	{
	fm.all('ReportRemark').value = arrSelected[0][0];
	fm.all('ReinsuDesc').value = arrSelected[0][1];
	fm.all('ReinsuRemark').value = arrSelected[0][2];
	fm.all('ReinsuredResult').value = arrSelected[0][3];
	fm.all('Operator').value = arrSelected[0][4];
	fm.all('MakeDate').value = arrSelected[0][5];
	fm.all('ReplyDate').value = arrSelected[0][6];
	}
	

}


