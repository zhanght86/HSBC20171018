//�������ƣ�EdorAppMUHealth.js
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2005-06-13 13:13:36
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var flag;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{


	
  var i = 0;
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

  fm.action= "./EdorAppMUHealthSave.jsp";
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
    //parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";
  	showInfo.close();
  	alert(showStr);
  	//parent.close();
  	
    //ִ����һ������
  }
}


/*********************************************************************
 *  ִ�д���ȫ���֪ͨ���EasyQuery
 *  ����:��ѯ��ʾ���������֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSingle()
{
  var strsql = "";
  var tContNo = "";
  var tEdorNo = "";
  tContNo = fm.ContNo.value;
  tEdorNo = fm.EdorNo.value;
  tMissionID = fm.MissionID.value;
  tInsuredNo = fm.InsureNo.value;

//  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.ActivityID = '0000000019'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" +tMissionID +"'";
  
    var sqlid1="EdorAppMUHealthSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorAppMUHealthSql");
	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	mySql1.addSubPara(tMissionID);//ָ���������
	var strsql = mySql1.getString();
  
  var brr = easyExecSql(strsql);

  //�ж��Ƿ��ѯ�ɹ�
  if (brr) 
  {
	brr[0][0]==null||brr[0][0]=='null'?'0':fm.SubMissionID.value  = brr[0][0];
    divSubmit.style.display = '';
   	divReturn.style.display = ''; 
  }
  else
  {
    alert("������ڵ�δ���ɻ���ɾ����������¼�������֪ͨ�飡");
    divSubmit.style.display = 'none';                 //�����ύ��ť
   	divReturn.style.display = '';
    return "";
  }
}
   

function queryPEInfo()
{   
	var tContNo = "";
    var tEdorNo = "";
    tContNo = fm.ContNo.value;
    tEdorNo = fm.EdorNo.value;	 
    tInsuredNo = fm.InsureNo.value;
//	var strsql = "select ContNo,name,pedate,peaddress,PEBeforeCond,remark, "
//	             + " (case when trim(PrintFlag)='0' then 'δ��ӡ' else case when trim(PrintFlag)='1' then '�Ѵ�ӡ' else case when trim(PrintFlag)='2' then '�ѻظ�' else '' end  end  end) "
//	             + " from LPPENotice where 1=1"
//				 + " and ContNo = '"+ tContNo + "' and edorno = '"+tEdorNo+"'"
//				 + " and customerno = '"+ tInsuredNo + "'";
	
	var sqlid2="EdorAppMUHealthSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorAppMUHealthSql");
	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	mySql2.addSubPara(tContNo);//ָ���������
	mySql2.addSubPara(tEdorNo);
	mySql2.addSubPara(tInsuredNo);
	var strsql = mySql2.getString();
	
    brr = easyExecSql(strsql);

    if (!brr) 
    {
      fm.Hospital.value  = "";
      fm.IfEmpty.value  = "";
      fm.Note.value  = "";
      fm.PrintFlag.value  = "";
      return "";
    }
 
    //��ѯ�ɹ������ַ��������ض�ά����
    brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
    brr[0][3]==null||brr[0][3]=='null'?'0':fm.Hospital.value  = brr[0][3];
    brr[0][4]==null||brr[0][4]=='null'?'0':fm.IfEmpty.value  = brr[0][4];
    brr[0][5]==null||brr[0][5]=='null'?'0':fm.Note.value  = brr[0][5];
    brr[0][6]==null||brr[0][6]=='null'?'0':fm.PrintFlag.value  = brr[0][6];

 
}



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
//	       + " union select appntno,concat(trim(AppntName),'(Ͷ����)') from lpappnt where contno ='"+tContNo+"'"
//	       + " and edorno = '"+tEdorNo + "'";
  
    var sqlid3="EdorAppMUHealthSq3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("bq.EdorAppMUHealthSql");
	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
	mySql3.addSubPara(tContNo);//ָ���������
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tEdorNo);
	mySql3.addSubPara(tContNo);
	mySql3.addSubPara(tEdorNo);
	var strSql = mySql3.getString();
  
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

//��ʼ��ҽԺ
function initHospital(tContNo,tEdorNo)
{
  var i,j,m,n;
  var returnstr;
	
//  var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
  
    var sqlid4="EdorAppMUHealthSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("bq.EdorAppMUHealthSql");
	mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
	mySql4.addSubPara(tContNo);//ָ���������;
	var strSql = mySql4.getString();
  
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult == "")
  {
//  	strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lpcont where ContNo = '"+tContNo+"' and edorno = '"+tEdorNo+"')";
  	
  	var sqlid5="EdorAppMUHealthSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("bq.EdorAppMUHealthSql");
	mySql5.setSqlId(sqlid5); //ָ��ʹ��SQL��id
	mySql5.addSubPara(tContNo);//ָ���������;
	mySql5.addSubPara(tEdorNo);
	var strSql = mySql5.getString();
  	
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
    if (turnPage.strQueryResult == "")
    {
          alert("ҽԺ��ʼ��ʧ�ܣ�");
          return "";
    }
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

  fm.Hospital.CodeData = returnstr;

  return returnstr;
}
  
