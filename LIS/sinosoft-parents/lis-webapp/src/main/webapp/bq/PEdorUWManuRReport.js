//�������ƣ�PEdorUWManuRReport.js
//�����ܣ���ȫ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
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
  	alert("��¼������֪ͨ��,��δ��ӡ,������¼���µ�����֪ͨ��!");
  	//easyQueryClick();
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


function manuchkspecmain()
{
	fm.submit();
}


// ��ѯ��ť
function easyQueryClick()
{	
	// ��дSQL���
	k++;
	var strSQL = "";
	var tPolNo = fm.PolNo.value;	
	var tMissionID = fm.MissionID.value;
	var tEdorNo= fm.EdorNo.value;		
	//alert(tProposalNo);
	if (tPolNo != " ")
	{
//		strSQL = "select polno,operator,contente from lprreport where polno = '"+tPolNo+"' and replycontente is null";
		var sqlid1="PEdorUWManuRReportSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.PEdorUWManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tPolNo);//ָ������Ĳ���
		strSQL=mySql1.getString();
	}
	
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult)
   {    
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  fm.PolNo.value = turnPage.arrDataCacheSet[0][0];
	  fm.Operator.value = turnPage.arrDataCacheSet[0][1];
	  fm.Content.value = turnPage.arrDataCacheSet[0][2];
 }
//  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + tEdorNo +"'"
//				 + " and LWMission.MissionProp2 = '"+ tPolNo + "'"
//				 + " and LWMission.ActivityID = '0000000004'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";	
  var strsql = "";
  var sqlid="PEdorUWManuRReportSql2";
	var mySql=new SqlClass();
	mySql.setResourceName("bq.PEdorUWManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql.addSubPara(tPolNo);//ָ������Ĳ���
	mySql.addSubPara(tMissionID);//ָ������Ĳ���
	strsql=mySql.getString();
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) {
	    fm.SubMissionID.value = "";
	    return "";
      }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  
  return true;
}
