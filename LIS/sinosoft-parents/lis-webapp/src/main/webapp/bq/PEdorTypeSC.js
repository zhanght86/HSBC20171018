//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPageP = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeSCInputSql";

function returnParent()
{
  top.opener.getEdorItemGrid();
  top.close();
  top.opener.focus();
}

function edorTypeSCInert()
{
		var selno = LCCSpecGrid.getSelNo()-1;
	  if (selno>=0)
	  {
	      alert("���Ѿ�ѡ��ĳ����Լ����ɾ�������߽����޸Ĳ�����");
	      return;
	   }
	   if(fm.Speccontent.value=="" || fm.Speccontent.value==null)
	   {
	   	   alert("��Լ����Ϊ�գ����������ݣ�");
	      return;
	   	}	
    fm.SpecType.value="bq";//��ȫ������Լ�ı��,hs,��Ч�ջ�����Լ,yg, Ա����Լ
    if(!confirm("ȷ��������Լ"))
    {
    	return ;
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
    fm.fmtransact.value="insert";
    fm.submit();

}
function edorTypeSCModify()
{
		var selno = LCCSpecGrid.getSelNo()-1;
	  if (selno <0)
	  {
	      alert("��ѡ��Ҫ�������Լ��");
	      return;
	   }	
	  var showStr="�����޸����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmtransact.value="modify";
    fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  } 
  initSpecBox();
  initPSpecBox();

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

//�����޸�
function edorTypeSCCancel()
{
     document.all('Speccontent').value ="";
}

//ɾ����Լ
function edorTypeSCDelete()
{
		var selno = LCCSpecGrid.getSelNo()-1;
	  if (selno<0)
	  {
	      alert("��ѡ��Ҫɾ������Լ��");
	      return;
	   }	
    if(!confirm("ȷ��ɾ����Լ"))
    {
    	return ;
    }
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmtransact.value="delete";
    fm.submit();
}

function initPreSpecBox()
{
	var tEdorNo=fm.EdorNo.value;
	var tContNo=fm.ContNo.value;
	var tEdorType=fm.EdorType.value;  
	var tPolNo=fm.PolNo.value;
//	var tSQL="select contno,grpcontno,EndorsementNo,nvl(SpecContent,''),SerialNo,PrtSeq,SpecType from  LCCSpec  b where ContNo='"+tContNo+"' ";  //�ų�������Լ��//and not exists (select 'X' from LPCSpec  a where  a.SerialNo=b.SerialNo and a.ContNo=b.ContNo  and a.EdorNo='"+tEdorNo+"' and a.EdorType='SC')
	
	var tSQL = "";
	var sqlid1="PEdorTypeSCInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	tSQL=mySql1.getString();
	
	turnPage.strQueryResult=easyQueryVer3(tSQL, 1, 1, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {

	    return ;

   }
    //��ѯ�ɹ������ַ��������ض�ά����    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid =LCPReSpecGrid;    
    //����SQL���
    turnPage.strQuerySql = tSQL; 
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

	}

function initSpecBox()
{
	var tEdorNo=fm.EdorNo.value;
	var tContNo=fm.ContNo.value;
	var tEdorType=fm.EdorType.value;  
	var tPolNo=fm.PolNo.value;
//	var tSQL="select contno,grpcontno,EndorsementNo,nvl(SpecContent,''),SerialNo,PrtSeq,SpecType from  LCCSpec  b where ContNo='"+tContNo+"' and   SpecType  not in ('hx','mn','nf','qt','sj','xi','xu','zx','ch')";  //�ų�������Լ��//and not exists (select 'X' from LPCSpec  a where  a.SerialNo=b.SerialNo and a.ContNo=b.ContNo  and a.EdorNo='"+tEdorNo+"' and a.EdorType='SC')
	
	var tSQL = "";
	var sqlid2="PEdorTypeSCInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	tSQL=mySql2.getString();
	
	turnPage.strQueryResult=easyQueryVer3(tSQL, 1, 1, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    {

	    return ;

   }
    //��ѯ�ɹ������ַ��������ض�ά����    
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid =LCCSpecGrid;    
    //����SQL���
    turnPage.strQuerySql = tSQL; 
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	

	}
function showSpecInfo()
{
	var selno = LCCSpecGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	fm.Speccontent.value = LCCSpecGrid.getRowColData(selno, 4);

}

function initPSpecBox()
{
	initLPCSpecGrid();
	var tEdorNo=fm.EdorNo.value;
	var tContNo=fm.ContNo.value;
	var tEdorType=fm.EdorType.value;  
	var tPolNo=fm.PolNo.value;
//	var tSQL="select contno,grpcontno,EndorsementNo,nvl(SpecContent,''),SerialNo,PrtSeq,SpecType from LPCSpec where EdorNo='"+tEdorNo+"' and EdorType='"+tEdorType+"' and contno='"+tContNo+"' and  SpecType  not in ('hx','mn','nf','qt','sj','xi','xu','zx','ch')";	
	
	var tSQL = "";
	var sqlid3="PEdorTypeSCInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql3.addSubPara(tEdorType);
	mySql3.addSubPara(tContNo);
	tSQL=mySql3.getString();	
	turnPageP.queryModal(tSQL,LPCSpecGrid);	

	}
function showPSpecInfo()
{
	var selno = LPCSpecGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���������");
	      return;
	}	
	fm.Speccontent.value = LPCSpecGrid.getRowColData(selno, 4);

}	

function getClickUpSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   showCodeListKey('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}

function getClickSpecTemp()
{
  	var tTempMain = document.all('HealthSpecTemp').value;
		sql_temp = " 1  and  temptype in (select distinct codealias from ldcode where codetype=#healthspcetemp# and code=#"+tTempMain+"#)  ";
   //alert(tManageCom.length);
  // alert(sql_temp);
   showCodeList('spectemp',[document.all('SpecTemp'),document.all('SpecTempname'),document.all('SpecReason'),document.all('Remark'),document.all('SpecCode'),document.all('SpecType')],[0,1,2,3,0,4],null,sql_temp,"1",1);
}
