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

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
 //alert(fm.ReportRemark.value);
 if(fm.ReportReason.value.trim()=="")
 {
 	alert("¼��ĳʱ�ԭ��Ϊ�գ�������¼�룡");
 	return false;
 	}
 if(fm.ReportRemark.value.trim()=="")
 {
 	alert("¼��ĳʱ�ԭ������Ϊ�գ�������¼�룡");
 	return false;
 	}
  if(fm.SubMissionID.value == "")
  {
  	
  	parent.close();
  	return;
  	}
  if(!checkPolConfirm()){
	  return false;
  }
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
lockScreen('lkscreen');  
  fm.submit(); //�ύ
}

//add by lzf 2013-04-03
function checkPolConfirm()
{
  var mMissionID = fm.MissionID.value;
//  var sql = " select count(1) from lwmission where activityid not in (select activityid from lwactivity where functionid in "
//	         +"('10010012','10010021','10010022','10010023','10010024','10010028','10010037','10010039','10010049'))"
//             + " and missionid = '"+mMissionID+"'";
  
    var sqlid1="UWManuUpReportSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWManuUpReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(('10010012','10010021','10010022','10010023','10010024','10010028','10010037','10010039','10010049'));//ָ������Ĳ���
	mySql1.addSubPara(mMissionID);//ָ������Ĳ���
	var sql=mySql1.getString();
  
  var rs = 0;
  rs = easyExecSql(sql); 
  
  if (rs>=1) {
	alert("����֪ͨ������ٱ��ʱ�����û�лظ��������������ٱ��ʱ�����");	
	return false;
  }
  return true;
}
//end 

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
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
	var showStr="�����ɹ���";  	
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
	
//	var strSQL = "select PrtNo,ContNo,SaleChnl,AgentCode,ManageCom,BankCode,InsuredName from lccont where contno='"+tContNo+"'";
	
	 var sqlid2="UWManuUpReportSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWManuUpReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(tContNo);//ָ������Ĳ���
		var strSQL=mySql2.getString();
		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	document.all('PrtNo').value = arrSelected[0][0];
	document.all('ContNo').value = arrSelected[0][1];
	document.all('SaleChnl').value = arrSelected[0][2];
	document.all('AgentCode').value = arrSelected[0][3];
	document.all('ManageCom').value = arrSelected[0][4];
	//document.all('BankCode').value = arrSelected[0][5];
    document.all('CustomerName').value = arrSelected[0][6];
}


function initLWMission()
{
	var MissionID = fm.MissionID.value;
//	var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = (select activityid from lwactivity where functionid ='10010039')"
//								;
//						
	 var sqlid3="UWManuUpReportSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.UWManuUpReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(MissionID);//ָ������Ĳ���
		var strSQL=mySql3.getString();
		
  var brr = easyExecSql(strSQL);
 
	if ( brr )  //�Ѿ����뱣���
 	{
 		var tSubMissionID = brr[0][0];
 		fm.SubMissionID.value = tSubMissionID;
 		
	}	 

}