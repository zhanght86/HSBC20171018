//�������ƣ�PolStatus.js
//�����ܣ�����״̬��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var arrResult1;

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  initPolGrid();
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    alert("�Բ���û���ҵ���Ӧ����"); 
  }
  else
  { 
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



// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	var strSQL = "";
	var strOperate="like";
	if(document.all('ProposalContNo').value==null||trim(document.all('ProposalContNo').value)=='')
	{
		alert('��¼��ӡˢ��!');
		return false;
	}
	
	    var sqlid1="PolStatusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.PolStatusSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
	
//	strSQL = "select appflag from lccont where prtno = '"+document.all('ProposalContNo').value+"'";
	arrResult1 = easyExecSql(strSQL,1,0);
	if(arrResult1!=null&&arrResult1[0][0]=="1")
	{
	//	alert("��Ͷ�����Ѿ�ǩ��!");
	//	return;
		}
		
		var sqlid2="PolStatusSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.PolStatusSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		//mySql2.addSubPara('1');//ָ������Ĳ���
	//	mySql2.addSubPara(k);//ָ������Ĳ���
		mySql2.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
		mySql2.addSubPara(manageCom);//ָ������Ĳ���
	    strSQL=mySql2.getString();	
		//alert(document.all('ProposalContNo').value+":"+manageCom);
//	strSQL = "select ContNo,ProposalContNo,PrtNo,ManageCom,AgentCode,AppntName from LCCont where "+k+" = "+k
//				 + " and ContType = '1'"		 
//				 + getWherePart( 'prtno','ProposalContNo',strOperate )
//				 + " and ManageCom like '" + manageCom + "%%'";
  var tArray = new Array;
  tArray = easyExecSql(strSQL);
  if(tArray=="" || tArray==null)
  {
  	
		var sqlid3="PolStatusSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.PolStatusSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
		mySql3.addSubPara(manageCom);//ָ������Ĳ���
		mySql3.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
		mySql3.addSubPara(manageCom);//ָ������Ĳ���
		mySql3.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
		mySql3.addSubPara(document.all('ProposalContNo').value);//ָ������Ĳ���
	    strSQL=mySql3.getString();	
	
//  	strSQL = "select MissionProp1,'','',MissionProp3 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"'"+
//  	         " and MissionProp3 like '" + manageCom + "%%'"
//  	         + " and processid='0000000003' "
//  	         + " and activityid not in ('0000001090','0000001091')"
//  	         +" union "
//  	         + "select MissionProp1,'','',MissionProp3 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"'"+
//  	         " and MissionProp2 like '" + manageCom + "%%' "
//  	         + " and activityid not in ('0000001090','0000001091')"
//  	         + " and processid='0000000003' "
//  	         +" union "
//  	         + "select MissionProp1,'','',MissionProp3 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"' "
//  	         + " and activityid='0000001403' "
//  	         +" union "
//  	         + "select MissionProp1,'','',MissionProp13 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"' "
//  	         + " and activityid in ('0000001090','0000001091')"
//  	         ;
  	}

	  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("�޴�Ͷ������Ϣ��");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		initPolGrid();
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

function getStatus()
{
	initPolStatuGrid();
  var i = 0;
  var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  //showSubmitFrame(mDebug);
  //showSubmitFrame(1);
  //document.all('spanPolGrid').all('PolGridSel').checked='true';
  document.getElementById("fm").submit(); //�ύ
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
		parent.fraMain.rows = "0,0,*,0,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}