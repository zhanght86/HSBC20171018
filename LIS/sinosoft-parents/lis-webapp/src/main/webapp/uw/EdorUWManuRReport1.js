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
  //alert(fm.SubMissionID.value);return false;
 // var strsql = "select 1 from lprreport where EdorNo = '"+fm.EdorNo.value+"' and ContNo = '"+fm.ContNo.value+"' and replyflag in ('0','1')";
	
     var sqlid1="EdorUWManuRReport1Sql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(fm.EdorNo.value);//ָ���������
	 mySql1.addSubPara(fm.ContNo.value);//ָ���������
	 var strsql = mySql1.getString();
  
  var Result = easyExecSql(strsql);  
  //prompt("",strsql);
  //�ж��Ƿ��ѯ�ɹ�
  if (Result!=null) {
    alert("�ѷ�������֪ͨ��,��δ����,������¼����޸�����֪ͨ��!");
  	parent.close();
  	return;
  }
  	//alert("fm.EdorNo.value=="+fm.EdorNo.value);
  	//alert("fm.EdorType.value=="+fm.EdorType.value);
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
//				 + " and LWMission.ActivityID = '0000000100'"
//				 + " and LWMission.ProcessID = '0000000000' "
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
  	
  	 var sqlid2="EdorUWManuRReport1Sql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(tMissionID);//ָ���������
	 strsql = mySql2.getString();
				
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  //prompt("",strsql);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("������¼��������֪ͨ�飡");
    return "";
  }
  
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
  //alert(fm.SubMissionID.value);
   turnPage = new turnPageClass();			 

  //easyQueryClick();
 				
  }
  return true;
}
function initCustomerNo(tContNo)
{//alert(148);
	var i,j,m,n;
	var returnstr;
	
//	var strSql = "select InsuredNo,name from lpinsured where 1=1 "
//	       + " and ContNo = '" +tContNo + "'"
//	       + " union select CustomerNo , Name from LPInsuredRelated where  polno in (select distinct polno from lppol where contno = '" +tContNo+"')";
	
	 var sqlid3="EdorUWManuRReport1Sql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
	 mySql3.addSubPara(tContNo);//ָ���������
	 mySql3.addSubPara(tContNo);//ָ���������
	 var strSql = mySql3.getString();
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  //prompt("",strSql);
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("��ѯʧ�ܣ�");
    //return "";
//     strSql = "select InsuredNo,name from lcinsured where 1=1 "
//	       + " and ContNo = '" +tContNo + "'"
//	       + " union select CustomerNo , Name from LcInsuredRelated where  polno in (select distinct polno from lcpol where contno = '" +tContNo+"')";
//   
     var sqlid4="EdorUWManuRReport1Sql4";
	 var mySql4=new SqlClass();
	 mySql4.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql4.setSqlId(sqlid4);//ָ��ʹ��SQL��id
	 mySql4.addSubPara(tContNo);//ָ���������
	 mySql4.addSubPara(tContNo);//ָ���������
	 strSql = mySql4.getString();
     
     turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  //prompt("",strSql);
  }
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

//	strSQL = "select RReportItemCode,RReportItemName from lprreportitem where ContNo = '"+tContNo+"' and prtseq in(select distinct prtseq from lprreport where ContNo = '"+tContNo+"' and customerno ='"+tCustomerNo+"')";

	 var sqlid5="EdorUWManuRReport1Sql5";
	 var mySql5=new SqlClass();
	 mySql5.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql5.setSqlId(sqlid5);//ָ��ʹ��SQL��id
	 mySql5.addSubPara(tContNo);//ָ���������
	 mySql5.addSubPara(tContNo);//ָ���������
	 mySql5.addSubPara(tCustomerNo);//ָ���������
	 strSQL = mySql5.getString();
	
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
//				 + " and LWMission.ActivityID = '0000000311'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";	
  
     var sqlid6="EdorUWManuRReport1Sql6";
	 var mySql6=new SqlClass();
	 mySql6.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql6.setSqlId(sqlid6);//ָ��ʹ��SQL��id
	 mySql6.addSubPara(tContNo);//ָ���������
	 mySql6.addSubPara(tMissionID);//ָ���������
	 strsql = mySql6.getString();
  
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);    //prompt("",strsql);
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) {
	    //fm.SubMissionID.value = "";
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
//	var strsql = " select appntno,appntname from lpcont where  ContNo = '"+tContNo+"' ";
	
	 var sqlid7="EdorUWManuRReport1Sql7";
	 var mySql7=new SqlClass();
	 mySql7.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql7.setSqlId(sqlid7);//ָ��ʹ��SQL��id
	 mySql7.addSubPara(tContNo);//ָ���������
	 var strsql = mySql7.getString();
	 
	 turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
//prompt("",strsql);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
//      strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
      
     var sqlid8="EdorUWManuRReport1Sql8";
 	 var mySql8=new SqlClass();
 	 mySql8.setResourceName("uw.EdorUWManuRReport1Sql");
 	 mySql8.setSqlId(sqlid8);//ָ��ʹ��SQL��id
 	 mySql8.addSubPara(tContNo);//ָ���������
 	 strsql = mySql8.getString();
 	 
      turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
    //return "";
  }
   if (!turnPage.strQueryResult) {
   	return "";
   }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
  fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
  
//  var strsql = "select RReportReason,(select codename from ldcode where codetype='rreportreason' and code=a.RReportReason),Contente,proposalcontno,prtseq from lprreport a where 1=1"
//				 + " and edorno = '"+ fm.EdorNo.value + "'"
//				 + " and contno = '"+ tContNo + "'"
//				 + " and replyflag is null ";
  
     var sqlid9="EdorUWManuRReport1Sql9";
	 var mySql9=new SqlClass();
	 mySql9.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql9.setSqlId(sqlid9);//ָ��ʹ��SQL��id
	 mySql9.addSubPara(fm.EdorNo.value);//ָ���������
	 mySql9.addSubPara(tContNo);//ָ���������
	 strsql = mySql9.getString();
	 
  //prompt("",strsql);		
  var tExistFlag = easyExecSql(strsql); 
  if(tExistFlag!= null)
  { 
  	fm.RReportReason.value = tExistFlag[0][0];
  	fm.RReportReasonname.value = tExistFlag[0][1];
  	fm.Contente.value = tExistFlag[0][2];
  	//fm.ProposalContNo.value = tExistFlag[0][3];
  	fm.PrtSeq.value = tExistFlag[0][4];
  }
}

function afterCodeSelect(cCodeName, Field)
{
	 var tRReport = fm.RReport.value;
	var tContNo = fm.ContNo.value;
	if (cCodeName == "rreport")
	{
		
		if(tRReport == "1")
		{
	//		strsql = " select appntno,appntname from lccont where  ContNo = '"+tContNo+"' ";
			
			 var sqlid10="EdorUWManuRReport1Sql10";
			 var mySql10=new SqlClass();
			 mySql10.setResourceName("uw.EdorUWManuRReport1Sql");
			 mySql10.setSqlId(sqlid10);//ָ��ʹ��SQL��id
			 mySql10.addSubPara(tContNo);//ָ���������
			 strsql = mySql10.getString();
			
		}
		if(tRReport == "2")
		{
		//		strsql = " select insuredno,insuredname from lccont where  ContNo = '"+tContNo+"' ";
				
				 var sqlid11="EdorUWManuRReport1Sql11";
				 var mySql11=new SqlClass();
				 mySql11.setResourceName("uw.EdorUWManuRReport1Sql");
				 mySql11.setSqlId(sqlid11);//ָ��ʹ��SQL��id
				 mySql11.addSubPara(tContNo);//ָ���������
				 strsql = mySql11.getString(); 
		}
		  if(tRReport == "3")
		  {
//		      strsql = " select insuredno,name from lcinsured where ContNo = '"+tContNo+"' and sequenceno = '2'"; 
		      
		         var sqlid12="EdorUWManuRReport1Sql12";
				 var mySql12=new SqlClass();
				 mySql12.setResourceName("uw.EdorUWManuRReport1Sql");
				 mySql12.setSqlId(sqlid12);//ָ��ʹ��SQL��id
				 mySql12.addSubPara(tContNo);//ָ���������
				 strsql = mySql12.getString();
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
//	      strsql = " select insuredno,name from lcinsured where ContNo = '"+tContNo+"' and sequenceno = '3'"; 
	      
	         var sqlid13="EdorUWManuRReport1Sql13";
			 var mySql13=new SqlClass();
			 mySql13.setResourceName("uw.EdorUWManuRReport1Sql");
			 mySql13.setSqlId(sqlid13);//ָ��ʹ��SQL��id
			 mySql13.addSubPara(tContNo);//ָ���������
			 strsql = mySql13.getString();
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
	
//	strsql = "select Operator from lccont where  ContNo = '"+tContNo+"'"
	
	 var sqlid14="EdorUWManuRReport1Sql14";
	 var mySql14=new SqlClass();
	 mySql14.setResourceName("uw.EdorUWManuRReport1Sql");
	 mySql14.setSqlId(sqlid14);//ָ��ʹ��SQL��id
	 mySql14.addSubPara(tContNo);//ָ���������
	 strsql = mySql14.getString();
	
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