//�������ƣ�SendAllnotice.js
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage  = new turnPageClass();
var turnPage1 = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
	 var i = 0;
     var showStr ="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr ="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
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
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	var showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
      showInfo.close();
       
  }
  else
  { 
	  var showStr="�����ɹ�";
  	  showInfo.close();
  	  alert(showStr);
  	  top.close();
  }
}

function afterCodeSelect( cCodeName, Field )
{

	var tCode = fm.NoticeType.value;
    if(tCode=="LP86")
	{
        //2006-02-09 Modify by zhaorx		
	 	var tCaseNo = fm.ClmNo.value; //�ⰸ��
	 	var tContNo = fm.ContNo.value;//��ͬ��
	 	var tFoundContent = "";       //�鵽�ĸ����վܱ�������Ϣ
	 	var i=0;
	 	var tSQLF = "";//SQL First 
	 	
//	 	tSQLF = " select passflag,(select riskstatname from lmrisk where exists (select 'X' from lcpol where lmrisk.riskcode = lcpol.riskcode and lcpol.polno = lluwmaster.polno )) "
//              + " from lluwmaster where 1=1 and passflag in ('1', '2') and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"' order by passflag ";
	 	
		var sqlid0="LLUWPSendAllSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(tCaseNo);//ָ������Ĳ���
		mySql0.addSubPara(tContNo);//ָ������Ĳ���
		tSQLF=mySql0.getString();
	 	
	 	var tReusltF = new Array();
	 	tReusltF = easyExecSql(tSQLF);
	 	if(!tReusltF)
	    {	
	    	alert("û��Ҫ���͵ľܱ����ڸ�����֪ͨ�飡"); 	    	
	    	return;
	    }
	    if(tReusltF.length != 0)
	    {	
	       for(i=1;i<=tReusltF.length;i++)
	       {  
	       	  if(tReusltF[i-1][0]=='1')//�ܱ�
	       	  {
	       	  	tFoundContent = tFoundContent + "��" + "�ܱ�" + tReusltF[i-1][1];
	       	  }
	       	  if(tReusltF[i-1][0]=='2')//����
	       	  { 
	       	  	tFoundContent = tFoundContent + "��" + "����" + tReusltF[i-1][1];
	       	  }
	       }
		    divUWSpec1.style.display = 'none';
			divUWSpec.style.display = 'none';
			divnoticecontent.style.display = 'none';//����֪ͨ������
			document.all.Content.value = "���˱���" + tFoundContent + "��";
	    } 
	}
	
else if(tCode=="LP81")
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = '';
		document.all.Content.value = "";
	}
	
else if(tCode=="LP89")
	{
		divnoticecontent.style.display = 'none';
		divUWSpec1.style.display = '';
		divUWSpec.style.display = '';
		fm.Content.value = "";
	}
    else
	{
		divUWSpec1.style.display = 'none';
		divUWSpec.style.display = 'none';
		divnoticecontent.style.display = 'none';
	}
}

function initLWMission()
{
	var MissionID = fm.MissionID.value;
//	var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//			   + " and activityid = '0000001270'"
			   
	var sqlid1="LLUWPSendAllSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(MissionID);//ָ������Ĳ���
	var strSQL=mySql1.getString();
								
    var brr = easyExecSql(strSQL);
	if ( brr )  //�Ѿ����뱣���
 	{
 		var tSubMissionID = brr[0][0];
 		fm.SubMissionID.value = tSubMissionID;
	}

}


function initLoprtManager()
{
	  var tContNo = document.all('ContNo').value;
//	  var strSQL  = "select prtseq,otherno,othernotype,agentcode,code,stateflag from loprtmanager where otherno = '"+tContNo+"'"
//	              + " and Code in ('LP00','LP06','LP81','LP82','LP83','LP86','LP88','LP89')"
//	              + " and (oldprtseq is null or prtseq = oldprtseq) ";//Modify by zhaorx 2006-09-01
	  
		var sqlid2="LLUWPSendAllSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(tContNo);//ָ������Ĳ���
		var strSQL=mySql2.getString();
	  
	  //��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	    
	  initPolGrid();
	
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult)
	  {
		  	PolGrid.clearData();
		  	alert("���ݿ���û���������������ݣ�");
		    return false;
	  }
 
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	 
	  //���ó�ʼ������MULTILINE����
	  turnPage.pageDisplayGrid = PolGrid;   
	         
	  //����SQL���
	  turnPage.strQuerySql   = strSQL;
	 
	  //���ò�ѯ��ʼλ��
	  turnPage.pageIndex = 0; 
	 
	  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
	 
	  //����MULTILINE������ʾ��ѯ���
	      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

	        
}


/*=========================================================================
 * �޸�ԭ���ڷ��𡰲��Զ�����֪ͨ�顱��ʱ�������ж�һ���Ƿ�Ը���������
 *           ���Զ��������ۣ��������������Զ�����֪ͨ�顱
 * �޸��ˣ�  �����
 * �޸�ʱ�䣺2005/12/13/10:50
 =========================================================================
 */
 function CheckBXBNotice()
 {
 	 var tCaseNo = fm.ClmNo.value; //�ⰸ��
 	 var tContNo = fm.ContNo.value;//��ͬ��
 	 
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = 'b' and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid3="LLUWPSendAllSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tCaseNo);//ָ������Ĳ���
		mySql3.addSubPara(tContNo);//ָ������Ĳ���
		strSQL=mySql3.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("�㻹û�ж��κθ������¹������Զ��������ĺ˱����ۣ�");
 	 	 return false;
 	 }
 	 return true;
 }
 
 /*=========================================================================
 * �޸�ԭ���ڷ��𡰾ܱ�֪ͨ�顱��ʱ�������ж�һ���Ƿ�����ջ��߸���������
 *           �ܱ����ۣ������������ܱ�֪ͨ�顱
 * �޸��ˣ�  �����
 * �޸�ʱ�䣺2005/12/14/14:50
 =========================================================================
 */
 function CheckJBNotice()
 {
 	 var tContNo = fm.ContNo.value;//��ͬ��
     var tCaseNo = fm.ClmNo.value; //�ⰸ��
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '1'  and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid4="LLUWPSendAllSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(tCaseNo);//ָ������Ĳ���
		mySql4.addSubPara(tContNo);//ָ������Ĳ���
		strSQL=mySql4.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("�㻹û�ж��κ������¹����ܱ����ĺ˱����ۣ�");
 	 	 return false;
 	 }
 	 return true;
 }

 
/*=========================================================================
 * �޸�ԭ���ڷ�������֪ͨ�顱��ʱ�������ж�һ���Ƿ�����ջ��߸���������
 *           ���ڽ��ۣ���������������֪ͨ�顱
 * �޸��ˣ�  �����
 * �޸�ʱ�䣺2005/12/14/14:50
 =========================================================================
 */
 function CheckYQNotice()
 {
 	 var tContNo = fm.ContNo.value;//��ͬ��
     var tCaseNo = fm.ClmNo.value; //�ⰸ��
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '2'  and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid5="LLUWPSendAllSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tCaseNo);//ָ������Ĳ���
		mySql5.addSubPara(tContNo);//ָ������Ĳ���
		strSQL=mySql5.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("�㻹û�ж��κ������¹������ڡ��ĺ˱����ۣ�");
 	 	 return false;
 	 }
 	 return true;
 }
 
/*=========================================================================
 * �޸�ԭ���ڷ��𡰼ӷѳб�֪ͨ�顱��ʱ�������ж�һ���Ƿ����������
 *           �ӷѳб����ۣ������������ӷѳб�֪ͨ�顱
 * �޸��ˣ�  �����
 * �޸�ʱ�䣺2005/12/14/14:50
 =========================================================================
 */
 function CheckJFCBNotice()
 {
 	 var tContNo = fm.ContNo.value;//��ͬ��
     var tCaseNo = fm.ClmNo.value; //�ⰸ��
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '3' and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid6="LLUWPSendAllSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tCaseNo);//ָ������Ĳ���
		mySql6.addSubPara(tContNo);//ָ������Ĳ���
		strSQL=mySql6.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("�㻹û�ж��κ������¹����ӷѳб����ĺ˱����ۣ�");
 	 	 return false;
 	 }
 	 return true;
 }
 
/*=========================================================================
 * �޸�ԭ���ڷ�����Լ�б�֪ͨ�顱��ʱ�������ж�һ���Ƿ����������
 *           ��Լ�б����ۣ�������������Լ�б�֪ͨ�顱
 * �޸��ˣ�  �����
 * �޸�ʱ�䣺2005/12/14/14:50
 =========================================================================
 */
 function CheckTYCBNotice()
 {
 	 var tContNo = fm.ContNo.value;//��ͬ��
     var tCaseNo = fm.ClmNo.value; //�ⰸ��
 	 var strSQL = "";
// 	 strSQL = " select * from lluwmaster where passflag = '4' and caseno = '"+tCaseNo+"' and contno = '"+tContNo+"'";
 	 
		var sqlid7="LLUWPSendAllSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("claimnb.LLUWPSendAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(tCaseNo);//ָ������Ĳ���
		mySql7.addSubPara(tContNo);//ָ������Ĳ���
		strSQL=mySql7.getString();
 	 
 	 var tRusult = easyExecSql(strSQL);
 	 
 	 if(tRusult =="" || tRusult == null)
 	 {
 	 	 alert("�㻹û�ж��κ������¹�����Լ�б����ĺ˱����ۣ�");
 	 	 return false;
 	 }
 	 return true;
 }