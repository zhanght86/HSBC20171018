//�������ƣ�EdorUWManuRReport.js
//�����ܣ���ȫ�˹��˱�������鱨��¼��
//�������ڣ�2005-07-14 18:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var turnPage = new turnPageClass();


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
  var	tContNo = fm.ContNo.value;
  var   tEdorNo = fm.EdorNo.value;
  var   tPrtNo = fm.PrtNo.value;
  var	tMissionID = fm.MissionID.value;
  var	tSubMissionID = fm.SubMissionID.value;
  
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";  	
  	showInfo.close();
  	alert(showStr);
  	initForm(tContNo,tEdorNo,tPrtNo,tMissionID,tSubMissionID);
  	//parent.close();
  	
    //ִ����һ������
  }
}



//��ѯ����¼������ڵ�
function easyQueryClickSingle()
{//alert(66);
   
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tEdorNo = fm.EdorNo.value;
	tMissionID = fm.MissionID.value;
    if(tContNo != null && tContNo!="" && tEdorNo!=null && tEdorNo!="")
    {
  	 	
//  	strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.ActivityID = '0000000022'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";				
  	var strsql = "";
  	var sqlid1="EdorUWManuRReportSql1";
  	var mySql1=new SqlClass();
  	mySql1.setResourceName("bq.EdorUWManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
  	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
  	mySql1.addSubPara(tMissionID);//ָ������Ĳ���
  	strsql=mySql1.getString();  	
	turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
      alert("������ڵ�δ���ɻ���ɾ����������¼��������֪ͨ�飡");
      divSubmit.style.display = 'none';                 //�����ύ��ť
   	  divReturn.style.display = '';
      return "";
    }
    divSubmit.style.display = '';
   	divReturn.style.display = '';
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
  
    turnPage = new turnPageClass();			 
 				
  }
  return true;
}
//��ʼ�����ն�����Ϣ
function initInsureNo(tContNo,tEdorNo)
{
  var i,j,m,n;
  var returnstr;

//  var strSql = "select InsuredNo,concat(trim(name),'(������)') from lcinsured where 1=1 "
//	       + " and contno = '" +tContNo + "'"
//	       + " union select AppntNo, concat(trim(AppntName),'(Ͷ����)') from LCAppnt where 1=1 "
//	       + " and contno = '" +tContNo + "'"
//	       + " union select insuredno,concat(trim(name),'(������)') from lpinsured where contno ='"+tContNo+"'"
//	       + " and edorno = '"+tEdorNo + "'"
//	       + " union select appntno,concat(trim(appntname),'(Ͷ����)') from lpappnt where contno ='"+tContNo+"'"
//	       + " and edorno = '"+tEdorNo + "'"
//	       + " union select agentcode,concat(trim(name),'(������)') from laagent where trim(AgentCode) in "
//	       + " (select trim(AgentCode) from LpCont where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+"')" ;
  
    var strSql = "";
	var sqlid2="EdorUWManuRReportSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorUWManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tEdorNo);//ָ������Ĳ���	
	strSql=mySql2.getString();  
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult == "")
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }

  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;

  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
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

  fm.InsureNo.CodeData = returnstr;
  return returnstr;
}
//���ն��������б��õ�����ʱ��������
function queryRRInfo()
{	
    //������tCustomerName�к��пͻ���ݣ�Ͷ���ˡ������ˣ�����Ϣ��������ȡ����ʱӦ������Ϣ�޳�	
	var tCustomerName = fm.InsureNoname.value;
    var mName = "";
    if(tCustomerName!="")
    {
          for (var i = 0; i < tCustomerName.length; i++)
          {
              if (tCustomerName.substring(i,i + 1) == "(")
              {
                  break;
              }
              mName += tCustomerName.substring(i,i + 1);
          }
    }
	fm.InsureNoname.value = mName;
	
	//ѡ����ܶ���ʱ����ʾ����¼��������Ϣ
	var tContNo = "";
    var tEdorNo = "";
    tContNo = fm.ContNo.value;
    tEdorNo = fm.EdorNo.value;	 
    tInsuredNo = fm.InsureNo.value;
//	var strsql = "select contente,rreportreason "
//	             + " from LPRReport where 1=1"
//				 + " and ContNo = '"+ tContNo + "' and edorno = '"+tEdorNo+"'"
//				 + " and customerno = '"+ tInsuredNo + "'";
//    var strsql = "select contente "
//	             + " from LPRReport where 1=1"
//				 + " and ContNo = '"+ tContNo + "' and edorno = '"+tEdorNo+"'"
//				 + " and customerno = '"+ tInsuredNo + "'";				 
	    
    var strsql = "";
	var sqlid3="EdorUWManuRReportSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("bq.EdorUWManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql3.addSubPara(tInsuredNo);//ָ������Ĳ���
	strsql=mySql3.getString();  	
    brr = easyExecSql(strsql);

    if (!brr) 
    {
      fm.Contente.value  = "";
      fm.RReportReason.value  = "";
     }
    else
    {
       //��ѯ�ɹ������ַ��������ض�ά����
       brr[0][0]==null||brr[0][0]=='null'?'0':fm.Contente.value  = brr[0][0];
       //brr[0][1]==null||brr[0][1]=='null'?'0':fm.RReportReason.value  = brr[0][1];
    }
    
//    strsql = "select RReportItemCode,RReportItemName from lprreportitem "
//              + " where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+ "' "
//              + " and prtseq in(select distinct prtseq from lprreport "
//              + " where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+ "' " 
//              + " and customerno ='"+tInsuredNo+"')";
  
    var strsql = "";
	var sqlid4="EdorUWManuRReportSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("bq.EdorUWManuRReportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	mySql4.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	mySql4.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql4.addSubPara(tInsuredNo);//ָ������Ĳ���
	strsql=mySql4.getString();  	
    
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
    if (turnPage.strQueryResult)
    {    
      initInvestigateGrid();
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      turnPage.pageDisplayGrid = InvestigateGrid;    
      turnPage.strQuerySql     = strsql; 
      turnPage.pageIndex       = 0;  
  	  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 	}
    else
    {   
    	 initInvestigateGrid();
    }            
	
	return true;
}
