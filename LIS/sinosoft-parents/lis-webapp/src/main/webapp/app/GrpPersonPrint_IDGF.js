// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sqlresourcename = "app.GrpPersonPrint_IDGFSql";
function printGrpPol()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ; 
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
  // showSubmitFrame(mDebug);
  //document.all("GrpPolButton").disabled=true;
  document.all('fmtransact').value="PRINT||ALLGRPPERSON";
  document.getElementById("fm").submit();
}


//�ύ�����水ť��Ӧ����
function printGrpPerson()
{
  var i = 0;
  var flag = 0;
  
  for( i = 0; i < PolGrid.mulLineCount; i++ )
  {
    if( PolGrid.getChkNo(i) == true )
    {
       flag = 1;
       break;
    }
  }

  if( flag == 0 )
  {
    alert("������ѡ��һ����¼���ٴ�ӡ�ŵ�����ƾ֤!");
    return false;
  }

  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  // showSubmitFrame(mDebug);
  //document.all("GrpPersonButton").disabled=true;
  document.all('fmtransact').value="PRINT||GRPPERSON";
  document.getElementById("fm").submit();
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //document.all("GrpPersonButton").disabled=false;
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


// ��ѯ�ŵ���ť
function queryGrpPol()
{
  if(document.all("GrpContNo").value == "" && document.all("PrtNo").value == "")
  {
    alert("�������ŵ��Ż�ӡˢ�ţ�");
    return false;
  }

  // ֻ��ʾ�ŵ���Ϣ
  divGrpPerson.style.display = "none" ;
  divGrpPol.style.display ='';
  
  // ��ʼ�����
  initGrpPolGrid();

  // ��дSQL���
  /*
  var strSQL = "select Grpcontno,prtno,'',grpname,peoples2,cvalidate "
               + "from lcgrpCont a where AppFlag in ('1','4') "
//               + "and riskcode IN (select riskcode from LMRiskApp where SubRiskFlag = 'M') "
               + getWherePart('GrpContNo')
               + getWherePart('PrtNo')
               + " and ManageCom like '" + comcode + "%%'";
   */      
   
var sqlid1="GrpPersonPrint_IDGFSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.GrpContNo.value);
mySql1.addSubPara(fm.PrtNo.value);
mySql1.addSubPara(comcode);
         
               
  turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ��ѯ���������������ݣ�");
    divGrpPol.style.display = "none" ;
    return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 10 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = GrpPolGrid;
          
  //����SQL���
  turnPage.strQuerySql     = strSQL ;
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


// ��ѯ�ŵ���ť
function queryGrpPerson()
{
  if(document.all("GrpContNo").value == "" && document.all("PrtNo").value == "")
  {
    alert("�������ŵ��Ż�ӡˢ�ţ�");
    return false;
  }

  // ֻ��ʾ�ŵ�������Ϣ
  divGrpPol.style.display = "none" ;
  divGrpPerson.style.display ='';
  
  // ��ʼ�����
  initPolGrid();

  // ��дSQL���
  /*
  var strSQL = "select contno,grpcontno,prtno,'',appntname,insuredname,cvalidate "
               + "from LCCont a where AppFlag in ('1','4') "
//               + "and riskcode IN (select riskcode from LMRiskApp where SubRiskFlag = 'M') "
//               + "and mainpolno = polno "
               + getWherePart('GrpContNo')
               + getWherePart('PrtNo')
               + getWherePart('ContNo')
               + " and ManageCom like '" + comcode + "%%'";
   */            
var sqlid2="GrpPersonPrint_IDGFSql2";
var mySql2=new SqlClass();
mySql2.setResourceName(sqlresourcename);
mySql2.setSqlId(sqlid2);
mySql2.addSubPara(fm.GrpContNo.value);
mySql2.addSubPara(fm.PrtNo.value);
mySql2.addSubPara(fm.ContNo.value);
mySql2.addSubPara(comcode);
          
  turnPage.strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ��ѯ���������������ݣ�");
    divGrpPerson.style.display = "none" ;
    return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 10 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;
          
  //����SQL���
  turnPage.strQuerySql     = strSQL ; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}