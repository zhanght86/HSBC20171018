//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 

var tResourceName="get.PayPlanCancelQuerySql";
var tResourceSQL1="PayPlanCancelQuerySql1";

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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else 
  {
    parent.fraMain.rows = "0,0,0,82,*";
  }
  parent.fraMain.rows = "0,0,0,0,*";
}


// ��ѯ��ť
function easyQueryClick()
{
  // ��ʼ�����
 if(trim(fm.GrpContNo.value)==""&&trim(fm.ContNo.value)==""){
 		alert("��¼���ŵ��Ż���˱�����!");
 		return;
 }
  initLFGetCancelLogGrid();
  divCancelContent.style.display = 'none';   //��ʾȷ�ϰ�Ť  
  
  divCancelLog.style.display ='';
	
  // ��дSQL���
  var strSQL = "";	
  //strSQL = "select b.grpcontno,a.contno,b.name,a.oldgetdate,a.oldgetmoney,a.oldoperator,a.oldmakedate,a.operator,a.makedate,a.remark,a.maketime "
  //       + "from lclfgetcancellog a,lcinsured b "
  //       + "where a.contno=b.contno and a.managecom like '"+manageCom+"%%'"
  //       + getWherePart( 'b.GrpContNo','GrpContNo' )
  //       + getWherePart( 'a.ContNo','ContNo' )
  //       + getWherePart( 'a.oldgetdate','GetDate' )
  //       + " order by makedate";
  //
	strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.ManageCom.value,fm.GrpContNo.value,fm.ContNo.value,fm.GetDate.value]); 
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ��,û�з�������������!");
    divCancelLog.style.display ='none';
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = LFGetCancelLogGrid;
          
  //����SQL���
  turnPage.strQuerySql = strSQL;
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}