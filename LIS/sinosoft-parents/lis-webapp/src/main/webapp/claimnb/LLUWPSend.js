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
   //	initLoprtManager();
  	top.close();
    //ִ����һ������
  }
}







function QuestQuery()
{	
	
	// ��дSQL���

	k++;
   tCode = document.all('NoticeType').value;
	
//		var	strSQL = "select cont from ldcodemod where "+k+"="+k				 	
//				 + " and codetype = 'uwnoticetype' and Code = '"+tCode+"'";

		var sqlid0="LLUWPSendSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("claimnb.LLUWPSendSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addPara(k);//ָ������Ĳ���
		mySql0.addPara(k);//ָ������Ĳ���
		mySql0.addSubPara(tCode);//ָ������Ĳ���
		var strSQL=mySql0.getString();

	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
   // alert("û��¼����������");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
			
			document.all('Content').value = returnstr
  		}
  		else
  		{
  			alert("û��¼����������");
  			return "";
  		}
  	
  }
  else
  {
  	alert("û��¼����������");
	return "";
  }

  if (returnstr == "")
  {
  	alert("û��¼����������");
  }
  
   
 
  return returnstr;
}


function initLWMission()
{
	var MissionID = fm.MissionID.value;
//		var strSQL = "select submissionid from lwmission where missionid ='"+MissionID+"'"
//							 + " and activityid = '0000001270'"
								;
		var sqlid1="LLUWPSendSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("claimnb.LLUWPSendSql"); //ָ��ʹ�õ�properties�ļ���
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
	var tContNo = fm.ContNo.value;
//	var strSQL = "select prtseq,otherno,othernotype,agentcode,code,stateflag from loprtmanager where otherno = '"+tContNo+"'"
//	        + " and Code = 'LP81'";
	
	var sqlid2="LLUWPSendSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("claimnb.LLUWPSendSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	var strSQL=mySql2.getString();
	       
	        
	        //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
    
 initPolGrid();

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
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

