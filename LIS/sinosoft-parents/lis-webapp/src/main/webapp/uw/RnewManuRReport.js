//�������ƣ�PEdorUWManuRReport.js
//�����ܣ���ȫ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  �����ˣ�ln    �������ڣ�2008-10-23   ����ԭ��/���ݣ������º˱�Ҫ������޸�

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
  	//alert("��¼������֪ͨ��,��δ��ӡ,������¼���µ�����֪ͨ��!");
  	//easyQueryClick();
  	parent.close();
  	return;
  	}
  	
  //ln 2008-10-23 add ¼��У��
  if(fm.RReport.value == "")
  {
  	alert("���ն������¼��!");
  	return;
  	}
  	if(fm.RReportReason.value == "")
  {
  	alert("����ԭ�����¼��!");
  	return;
  	}
  	if(fm.Contente.value == "")
  {
  	alert("�������ݱ���¼��!");
  	return;
  	}
  //end ln 2008-10-23 add ¼��У��
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}
function easyQueryClickSingle()
{

	// ��дSQL���
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tMissionID = fm.MissionID.value;


  if(tContNo != "" )
  {
  	 	
//  	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 //+ " and LWMission.MissionProp2 = '"+ tContNo + "'"
//				 + " and LWMission.ActivityID = (select activityid from lwactivity where functionId = '10047001')"
//				 + " and exists (select 1 from Lwprocess a  where  a.Processid=LWMission.Processid and a.Busitype='1004') "
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
  	
  	    var  sqlid1="RnewManuRReportSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	 	mySql1.addSubPara(tMissionID);//ָ������Ĳ���
	     strSql=mySql1.getString();
				
	turnPage.strQueryResult = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("������¼��������֪ͨ�飡");
    return "";
  }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
  
   turnPage = new turnPageClass();			 

  //easyQueryClick();
 				
  }
  return true;
}
function initCustomerNo(tPrtNo)
{
	var i,j,m,n;
	var returnstr;
	
//	var strSql = "select InsuredNo,name from lcinsured where 1=1 "
//	       + " and Prtno = '" +tPrtNo + "'"
//	       + " union select CustomerNo , Name from LCInsuredRelated where  polno in (select distinct polno from lcpol where Prtno = '" +tPrtNo+"')";
	    var  sqlid2="RnewManuRReportSql1";
	 	var  mySql2=new SqlClass();
	 	mySql2.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	 	mySql2.addSubPara(tPrtNo);//ָ������Ĳ���
	 	mySql2.addSubPara(tPrtNo);//ָ������Ĳ���
	    var strSql=mySql2.getString();
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("��ѯʧ��!!");
  			return "";
  		}
  	}
}
else
{
	alert("��ѯʧ��!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.CustomerNo.CodeData = returnstr;
  return returnstr;
}

// ��ѯ��ť
function easyQueryClick()
{	
	// ��дSQL���
	k++;
	var strSQL = "";
	var tCustomerNo = fm.CustomerNo.value;
	var tContNo = fm.ContNo.value;	
	var tMissionID = fm.MissionID.value;
	//alert(tProposalNo);

	//strSQL = "select RReportItemCode,RReportItemName from lcrreportitem where ContNo = '"+tContNo+"' and prtseq in(select distinct prtseq from lcrreport where ContNo = '"+tContNo+"' and customerno ='"+tCustomerNo+"')";
	    var  sqlid3="RnewManuRReportSql2";
	 	var  mySql3=new SqlClass();
	 	mySql3.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	 	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	 	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	 	mySql3.addSubPara(tCustomerNo);//ָ������Ĳ���
	    strSQL=mySql3.getString();

   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult)
  {    
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	//���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  	turnPage.pageDisplayGrid = InvestigateGrid;    
  	        
  	//����SQL���
  	turnPage.strQuerySql     = strSQL; 
  	
  	//���ò�ѯ��ʼλ��
  	turnPage.pageIndex       = 0;  
  	
  	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	
  	//����MULTILINE������ʾ��ѯ���
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 	}
 		
//  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp2 = '"+ tContNo + "'"
////				 + " and LWMission.ActivityID = '0000001104'"
////				 + " and LWMission.ProcessID = '0000000003'"
//				 + " and LWMission.ActivityID  in (select activityid from lwactivity  where functionid ='10010023')"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
    var  sqlid4="RnewManuRReportSql3";
	var  mySql4=new SqlClass();
	mySql4.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	mySql4.addSubPara(tMissionID);//ָ������Ĳ���
    strsql=mySql4.getString();
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

function initDefaultObj(tContNo)
{
	fm.RReport.value = '1';
	//fm.RReportTypeName.value = 'Ͷ����';
	//var strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
	    var  sqlid5="RnewManuRReportSql4";
		var  mySql5=new SqlClass();
		mySql5.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tContNo);//ָ������Ĳ���
	     var strsql=mySql5.getString();
	 turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
}

function afterCodeSelect(cCodeName, Field)
{
	 var tRReport = fm.RReport.value;
	var tContNo = fm.ContNo.value;
	if (cCodeName == "rreport")
	{
		
		if(tRReport == "1")
		{
			//strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
			    var  sqlid6="RnewManuRReportSql5";
				var  mySql6=new SqlClass();
				mySql6.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
				mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
				mySql6.addSubPara(tContNo);//ָ������Ĳ���
			     strsql=mySql6.getString();
	}
	if(tRReport == "2")
	{
			//strsql = " select insuredno,insuredname from lccont where  ContNo = '"+tContNo+"' ";
			    var  sqlid7="RnewManuRReportSql6";
				var  mySql7=new SqlClass();
				mySql7.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
				mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
				mySql7.addSubPara(tContNo);//ָ������Ĳ���
			     strsql=mySql7.getString();
	}
	  if(tRReport == "3")
  {
      //strsql = " select insuredno,name from lcinsured where ContNo = '"+tContNo+"' and sequenceno = '2'";
        var  sqlid8="RnewManuRReportSql7";
		var  mySql8=new SqlClass();
		mySql8.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(tContNo);//ָ������Ĳ���
	    strsql=mySql8.getString();
  }
  /*
	if(tRReport == "4")
	{
		strsql = " select AgentCode,Name from LAAgent where trim(AgentCode) in (select trim(AgentCode) from LCCont where ContNo = '"+tContNo+"')" ;
		fm.CustomerNo.value = strsql;
	}
	*/
	if(tRReport == "4")
  {
      //strsql = " select insuredno,name from lcinsured where ContNo = '"+tContNo+"' and sequenceno = '3'";
        var  sqlid9="RnewManuRReportSql8";
		var  mySql9=new SqlClass();
		mySql9.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(tContNo);//ָ������Ĳ���
	    strsql=mySql9.getString();
  }
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    alert("�����ڸý��ն���");
    initDefaultObj(tContNo);
    return ;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
 	
  

if(tRReport == "5")
	{
		
		initOperator();
	}

	}

}


function initOperator()
{
	
	var tContNo = fm.ContNo.value;
	
	//strsql = "select Operator from lccont where  ContNo = '"+tContNo+"'"
	var  sqlid10="RnewManuRReportSql9";
	var  mySql10=new SqlClass();
	mySql10.setResourceName("uw.RnewManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(tContNo);//ָ������Ĳ���
    strsql=mySql10.getString();
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerNo.value = "";
}

function initAgent()
{
	
}