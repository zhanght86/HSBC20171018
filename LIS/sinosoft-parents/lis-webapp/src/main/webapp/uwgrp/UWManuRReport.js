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
var sqlresourcename = "";
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
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  	 	/*
  	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 //+ " and LWMission.MissionProp2 = '"+ tContNo + "'"
				 + " and LWMission.ActivityID = '0000001104'"
				 + " and (LWMission.ProcessID = '0000000003' or LWMission.ProcessID = '0000000000')"
				 + " and LWMission.MissionID = '" +tMissionID +"'";
				 */
		var sqlid1="UWManuRReportSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tMissionID);			
	turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
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
	/*
	var strSql = "select InsuredNo,name from lcinsured where 1=1 "
	       + " and Prtno = '" +tPrtNo + "'"
	       + " union select CustomerNo , Name from LCInsuredRelated where  polno in (select distinct polno from lcpol where Prtno = '" +tPrtNo+"')";
	*/
	var sqlid2="UWManuRReportSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tPrtNo);	
	    mySql2.addSubPara(tPrtNo);	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
  
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

var sqlid3="UWManuRReportSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);	
	    mySql3.addSubPara(tContNo);	
	     mySql3.addSubPara(tCustomerNo);	
   turnPage.strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 0, 1);  
  
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
 		/*
  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp2 = '"+ tContNo + "'"
				 + " and LWMission.ActivityID = '0000001104'"
				 + " and LWMission.ProcessID = '0000000003'"
				 + " and LWMission.MissionID = '" +tMissionID +"'";	
		*/		 
		var sqlid4="UWManuRReportSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tContNo);	
	    mySql4.addSubPara(tMissionID);	
				 
				 			 
	turnPage.strQueryResult = easyQueryVer3(mySql4.getString(), 1, 1, 1);    
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) {
	    fm.SubMissionID.value = "";
	    return "";
      }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   
  
  return true;
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
		
		var sqlid5="UWManuRReportSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tContNo);	

	    strsql = mySql5.getString();
	}
	if(tRReport == "2")
	{
			//strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
			
		var sqlid6="UWManuRReportSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tContNo);	

	    strsql = mySql6.getString();
	}
	if(tRReport == "4")
	{
	//	strsql = " select AgentCode,Name from LAAgent where trim(AgentCode) in (select trim(AgentCode) from LCCont where ContNo = '"+tContNo+"')" ;
		
		var sqlid7="UWManuRReportSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);	

	    strsql = mySql7.getString();
		
		fm.CustomerNo.value = strsql;
	}
	
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
      
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
 	
  
  if(tRReport == "3")
  {
   fm.CustomerNo.value = "";
 
  fm.CustomerName.value = "";
}
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
	
	var sqlid8="UWManuRReportSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tContNo);	
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql8.getString(), 1, 1, 1);  

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