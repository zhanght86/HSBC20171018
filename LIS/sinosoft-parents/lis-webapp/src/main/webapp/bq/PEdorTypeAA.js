//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var selno;
var sqlresourcename = "bq.PEdorTypeAAInputSql";

function reback(){
	  document.all("fmtransact").value = "DELETE";             
	  var showStr="���ڼ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
    fm.action= "./PEdorTypeAASubmit.jsp";
    document.getElementById("fm").submit();;    
    
}

function edorTypeAASave()
{

		  selno = PolGrid.getSelNo()-1;    
	  if (selno <0)
	  {
	      alert("��ѡ��Ҫ�ӱ������֣�");
	      return;
	  }
	  var acontno = document.all("ContNo").value;
	  var aDate = document.all("EdorValiDate").value;
	  if(document.all("AAMoney").value == null){
		 alert("����д�ӱ���");
		 return;
	}
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit();;
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
{

	queryBackFee();
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {
  	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
     //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    	
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     if(fm.fmtransact.value == "DELETE"){
        initForm();
        fm.fmtransact.value='';      
     }
     else{
        updatepol();
     }
  }

}

function updatepol(){
	 var polno2 = PolGrid.getRowColData(selno,10);

	var strSQL = "";
	var sqlid1="PEdorTypeAAInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all("EdorNo").value);//ָ������Ĳ���
	mySql1.addSubPara(polno2);
	strSQL=mySql1.getString();
	 
	 var arrResult = easyExecSql(strSQL, 1, 0);
//	 var arrResult = easyExecSql("select a.amnt,a.mult,(select sum(prem) from lpprem where polno = a.polno and edorno = a.edorno) from lppol a where 1 =1 and a.edorno ='"+document.all("EdorNo").value+"' and a.edortype = 'AA' and polno = '" + polno2 + "'", 1, 0);
	 if(arrResult == null){
	 	  return false;
	 }	 
	 PolGrid.setRowColData(selno, 6, arrResult[0][0]);
	 PolGrid.setRowColData(selno, 7, arrResult[0][1]);
	 PolGrid.setRowColData(selno, 8, arrResult[0][2]);
}


//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           
       
//---------------------------
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

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}



function returnParent()
{
	top.opener.initEdorItemGrid();
  top.opener.getEdorItemGrid();
  top.close();
}




function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = '0000000003';
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}
function getMainPolInfo()
{
    
    //alert(tContNo);
    var tContNo= document.all('ContNo').value;		  
    // ��дSQL���
//    var strSQL ="select InsuredNo,InsuredName,RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Prem,Amnt,CValiDate,contno,insuyear,payyears,paytodate from LCPol where polno = mainpolno and ContNo='"+tContNo+"'";
    
    var strSQL = "";
	var sqlid2="PEdorTypeAAInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql2.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {
        return false;
    }
    
    //��ѯ�ɹ������ַ��������ض�ά����
    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����

    turnPage.pageDisplayGrid = MainPolGrid;    
    //����SQL���
    turnPage.strQuerySql = strSQL; 
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
    
    
}



function showchange(){
	 var no3 = PolGrid.mulLineCount;	
	 //alert(no3); 
	 var tEdorNo = document.all('EdorNo').value;
	 for(var i = 0; i < no3; i++){
	 	  var tPolNo = PolGrid.getRowColData(i,10);
	 	  //alert(tPolNo);
//	 	  var sql3 = "select a.amnt,a.mult,b.sumactupaymoney from lppol a,ljspayperson b where a.edorno = '" + tEdorNo + "' and a.polno = b.polno and a.polno = '" + tPolNo + "'";
	 	  
	var sql3 = "";
	var sqlid3="PEdorTypeAAInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql3.addSubPara(tPolNo);//ָ������Ĳ���
	sql3=mySql3.getString();
	 	  
	 	  //alert(sql3);
	 	  var bResult = easyExecSql(sql3);
	 	  //alert(bResult);
	 	  if(bResult == null){
	 	  	  continue;
	 	  }
	 	  PolGrid.setRowColData(i, 6, bResult[0][0]);
	    PolGrid.setRowColData(i, 7, bResult[0][1]);
	    PolGrid.setRowColData(i, 8, bResult[0][2]);

	    
	 }	 
}

/*********************************************************************
 *  ��ѯ��ȫ��Ŀ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolInfo()
{
    showchange();
    var tContNo=document.all("ContNo").value;


/*        var strSQL =" select (select riskcode from lcpol where polno = a.polno), "
					+ "	(select riskname "
					+ "		 from lmriskapp "
					+ "		where riskcode = "
					+ "					(select riskcode from lcpol where polno = a.polno)), "
					+ "	(case "
					+ "		when (select amntflag from lmduty where dutycode = a.dutycode) = '1' then "
					+ "		 a.amnt "
					+ "		else   "
					+ "		 a.mult "
					+ "	end),    "
					+ "	prem,    "
					+ "	'',      "
					+ "	'',      "
					+ "	'',      "
					+ "	a.polno, "
					+ "	(case when (select amntflag from lmduty where dutycode = a.dutycode)='1' then '������' else '������' end)		" 
			+ " from lcduty a  "
      + " where contno='"+tContNo+"' "
      + " and not exists (select 'Y' from LCPol where PolNo=a.PolNo and mainpolno!=polno and InsuYearFlag='Y' and InsuYear>1)  "
			+ " and not exists (select 'M' from LCPol where PolNo=a.PolNo and mainpolno!=polno and InsuYearFlag='M' and InsuYear>12) "
			+ " and not exists (select 'D' from LCPol where PolNo=a.PolNo and mainpolno!=polno and InsuYearFlag='D' and InsuYear>365)";
*/         
    var strSQL = "";
	var sqlid4="PEdorTypeAAInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql4.getString();           

        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            alert("û��Ͷ�������ջ򸽼�������ֹ�����ܽ��и����ռӱ�!");
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}
function getCustomerInfo()
{
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
//        var strSQL ="select AppntName,AppntIDType,AppntIDNo,InsuredName,InsuredIDType,InsuredIDNo from LCCont where ContNo='"+ContNo+"'";
        
    var strSQL = "";
	var sqlid5="PEdorTypeAAInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(ContNo);//ָ������Ĳ���
	strSQL=mySql5.getString();           
        
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = CustomerGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }

}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
/*      strSQL = " Select a.appntno,'Ͷ����',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
                                        +" Union"
                                        +" Select i.insuredno,'������',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
                                        +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
                                        +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
*/    //alert("-----------"+strSQL+"------------");
	var sqlid6="PEdorTypeAAInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(tContNo);//ָ������Ĳ���
	mySql6.addSubPara(tContNo);//ָ������Ĳ���
	strSQL=mySql6.getString();      
    }
    else
    {
        alert('û����Ӧ��Ͷ���˻򱻱�����Ϣ��');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
  //alert("û����Ӧ��Ͷ���˻򱻱�����Ϣ��");
        return false;
    }

    //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    turnPage.pageDisplayGrid = CustomerGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL;
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}    
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 