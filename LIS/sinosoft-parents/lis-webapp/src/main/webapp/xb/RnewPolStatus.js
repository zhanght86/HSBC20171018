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

function getcodedata2()
{
	//var sql="select riskcode,riskname from lmriskapp where riskcode in (select riskcode from lcpol where contno ='"+fm.ProposalContNo.value+"' and appflag ='9')";
	var sql=wrapSQL('LMRiskApp1',[fm.ProposalContNo.value]);
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("��ѯ���ִ���:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("RiskCode").CodeData=tCodeData;
	
}

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
  document.getElementById("fm").submit();; //�ύ
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
		alert('��¼���ͬ��!');
		return false;
	}
	//strSQL = "select appflag from lccont where contno = '"+document.all('ProposalContNo').value+"'";
	strSQL=wrapSQL('LCCont1',[document.all('ProposalContNo').value]);
	arrResult1 = easyExecSql(strSQL,1,0);
	if(arrResult1!=null&&arrResult1[0][0]=="0")
	{
		alert("��Ͷ����δǩ��!");
		return;
		}
	//strSQL = "select ContNo,ProposalContNo,PrtNo,ManageCom,AgentCode,AppntName from LCCont where "+k+" = "+k
	//			 + " and ContType = '1'"		 
	//			 + " and AppFlag = '1'"	 
	//			 + getWherePart( 'contno','ProposalContNo',strOperate )
	//			 + " and ManageCom like '" + manageCom + "%%'";
	//strSQL = "select ContNo,prtno,PrtNo,ManageCom,AgentCode,AppntName,riskcode from LCPol where "+k+" = "+k
	//			 + " and ContType = '1'"		 
	//			 + " and AppFlag = '9'"	 
	//			 + getWherePart( 'contno','ProposalContNo',strOperate )
	//			 + getWherePart( 'riskcode','RiskCode',strOperate )
	//			 + " and ManageCom like '" + manageCom + "%%'";
		strSQL=wrapSQL('LCPol1',[fm.ProposalContNo.value,fm.RiskCode.value,manageCom]);
  var tArray = new Array;
  tArray = easyExecSql(strSQL);
  if(tArray=="" || tArray==null)
  {
  	//strSQL = "select MissionProp1,MissionProp2,'',MissionProp6 from LWMission "+
  	//         " where MissionProp2 = '"+document.all("ProposalContNo").value+"'"+
  	//         " and MissionProp3 like '" + manageCom + "%%'"
  	         //+" and ActivityID in ('0000001099','0000001098')"
  	        //+" union "
  	        //+ "select MissionProp1,MissionProp2,'',MissionProp6 from LWMission "+
  	        //" where MissionProp2 = '"+document.all("ProposalContNo").value+"'"+
  	        //" and MissionProp2 like '" + manageCom + "%%' "
  	        //+" union "
  	        //+ "select MissionProp1,MissionProp2,'',MissionProp6 from LWMission "+
  	        //" where MissionProp2 = '"+document.all("ProposalContNo").value+"' "
  	        //+ " and activityid='0000001403' ";
  	        
  	   strSQL=wrapSQL('LWMission1',[document.all("ProposalContNo").value,manageCom,
  	   															document.all("ProposalContNo").value,
  	   															manageCom,document.all("ProposalContNo").value]);
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
  document.getElementById("fm").submit();; //�ύ
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



/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSQL(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("xb.RnewPolStatusInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}
