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
 
  if(fm.SubMissionID.value == "")
  {
  	
  	parent.close();
  	return;
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

  document.getElementById("fm").submit(); //�ύ
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
	
	var tContNo = fm.ContNo.value;
	var tPrtNo = fm.PrtNo.value;
	
	//var strSQL = "select ContNo,SaleChnl,AgentCode,ManageCom,BankCode from lccont where contno='"+tContNo+"'";
//	var strSQL = " select ContNo,"
//             + " (select codename"
//             + " from ldcode"
//             + " where codetype = 'salechnl'"
//             + " and code = SaleChnl),"
//             + " AgentCode,"
//             + " ManageCom,"
//             + " BankCode"
//             + " from lccont"
//             + " where contno = '" + tContNo + "'";
//	
	var sqlid1="UWManuUpReportReplySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWManuUpReportReplySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("��ѯ����������Ϣʧ�ܣ�")
  	return "";
  }
else
	{
	document.all('ContNo').value = arrSelected[0][0];
	document.all('SaleChnl').value = arrSelected[0][1];
	document.all('AgentCode').value = arrSelected[0][2];
	document.all('ManageCom').value = arrSelected[0][3];
	//document.all('BankCode').value = arrSelected[0][4];
  }
	
	//var strSQl = "select ReportRemark,ReinsuDesc,ReinsuRemark,ReinsuredResult,usercode,makedate,modifydate,dealusercode from LCReinsureReport where contno = '"+tContNo+"'";
//	var strSQl = " select ReportRemark,"
//             + " ReinsuDesc,"
//             + " ReinsuRemark,"
//             + " (select codename"
//             + " from ldcode"
//             + " where codetype = 'uqreportstate'"
//             + " and code = ReinsuredResult),"
//             + " usercode,"
//             + " makedate,"
//             + " modifydate,"
//             + " dealusercode"
//             + " from LCReinsureReport"
//             + " where contno = '" + tContNo + "'";
	
	var sqlid2="UWManuUpReportReplySql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("uw.UWManuUpReportReplySql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	var strSQl=mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQl, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if(arrSelected == null)
  {
  	alert("û���ٱ��ظ���Ϣ!")
  	return "";
  }
else
	{
	document.all('ReportRemark').value = arrSelected[0][0];
	document.all('ReinsuDesc').value = arrSelected[0][1];
	document.all('ReinsuRemark').value = arrSelected[0][2];
	document.all('ReinsuredResult').value = arrSelected[0][3];
	document.all('Operator').value = arrSelected[0][4];
	document.all('MakeDate').value = arrSelected[0][5];
	document.all('ReplyDate').value = arrSelected[0][6];
	document.all('ReplyPerson').value = arrSelected[0][7];
	}
	

}

function querytrace(){
//alert(document.all('PrtNo').value);
   var tContNo = fm.ContNo.value;
//    var SQL = "select t.reportremark,(select d.codename from ldcode d where d.codetype = 'uqreportstate' and d.code = t.reinsuredresult),t.reinsudesc,t.reinsuremark,t.makedate,t.usercode,t.modifydate,t.dealusercode from lcreinsurereporttrace t where t.contno = '"+tContNo+"' order by t.makedate,t.maketime";
    
    var sqlid3="UWManuUpReportReplySql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("uw.UWManuUpReportReplySql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	var  SQL=mySql3.getString();
	
    turnPage.queryModal(SQL, TraceGrid);
   

}


