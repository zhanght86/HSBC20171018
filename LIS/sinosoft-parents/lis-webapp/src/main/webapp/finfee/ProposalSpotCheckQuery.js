//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;

/*********************************************************************
 *  ���и��˳��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function approvePol1()
{
	var tSel = PolGrid.getSelNo();
	if( tSel == null || tSel == 0 )
		alert("��ѡ��һ���շѵ����ٽ��и��˳�����");
	else
	{
		var i = 0;
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		showSubmitFrame(mDebug);
		document.getElementById("fm").submit();//�ύ
	}
}

/*********************************************************************
 *  Ͷ�������˵��ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	
	//���ӡˢ�ŵ�����
    var TempFeeNo = PolGrid.getRowColData(PolGrid.getSelNo()-1, 2);
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+TempFeeNo+"&CreatePos=���˳��&PolState=2002&Action=DELETE";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
	
  // ˢ�²�ѯ���
	easyQueryClick();		
}

function unlock(TempFeeNo)
{
	//alert("TempFeeNo"+TempFeeNo);	
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+TempFeeNo+"&CreatePos=���˳��&PolState=2002&Action=DELETE";
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    easyQueryClick();		
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if (cShow=="true")
		cDiv.style.display="";
	else
		cDiv.style.display="none";  
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
        

/*********************************************************************
 *  ����EasyQuery��ѯ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
function easyQueryClick()
{
	var addSQL = "";
	var tManageCom= document.all('ManageCom').value;

	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���			 
	var strSql = "";
	if(_DBT==_DBO){
		strSql = "select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and polstate='2002' and CreatePos='���˳��' and rownum=1) operator,d.bussno,a.managecom,d.bpoid,a.makedate,a.maketime"
            +" from ES_DOC_MAIN a,BPOMissionState d"
            +" where a.doccode=d.bussno"
            +" and d.bussnotype='OF'"
            +" and d.dealtype='01' and a.makedate>=now()-30 and a.makedate<=now() and d.makedate>=now()-30 and d.makedate<=now() "
            +" and d.state='0' and a.managecom like '"+ComCode+"%%' "
            + getWherePart('a.makedate','ScanDate','<=')
	           + getWherePart('d.BussNo','TempFeeNo' );
		
//		    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
//		    var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
//	     	var  sqlid1="ProposalSpotCheckQuerySql0";
//	     	var  mySql1=new SqlClass();
//	     	mySql1.setResourceName("finfee.ProposalSpotCheckQuerySql"); //ָ��ʹ�õ�properties�ļ���
//	     	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
//	     	mySql1.addSubPara(ComCode);//ָ������Ĳ���
//	     	mySql1.addSubPara(ScanDate);//ָ������Ĳ���
//	     	mySql1.addSubPara(TempFeeNo);//ָ������Ĳ���
//	     	strSql=mySql1.getString();
	}else if(_DBT==_DBM){
		strSql = "select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and polstate='2002' and CreatePos='���˳��' limit 0,1) operator,d.bussno,a.managecom,d.bpoid,a.makedate,a.maketime"
            +" from ES_DOC_MAIN a,BPOMissionState d"
            +" where a.doccode=d.bussno"
            +" and d.bussnotype='OF'"
            +" and d.dealtype='01' and a.makedate>=now()-30 and a.makedate<=now() and d.makedate>=now()-30 and d.makedate<=now() "
            +" and d.state='0' and a.managecom like '"+ComCode+"%%' "
            + getWherePart('a.makedate','ScanDate','<=')
	           + getWherePart('d.BussNo','TempFeeNo' );
		
//		var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
//	    var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
//     	var  sqlid2="ProposalSpotCheckQuerySql1";
//     	var  mySql2=new SqlClass();
//     	mySql2.setResourceName("finfee.ProposalSpotCheckQuerySql"); //ָ��ʹ�õ�properties�ļ���
//     	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
//     	mySql2.addSubPara(ComCode);//ָ������Ĳ���
//     	mySql2.addSubPara(ScanDate);//ָ������Ĳ���
//     	mySql2.addSubPara(TempFeeNo);//ָ������Ĳ���
//     	strSql=mySql2.getString();
	}
	             
	if(!(document.all('ManageCom').value==null||document.all('ManageCom').value==""))
	{
		 strSql=strSql+ " and a.managecom like '%25" + document.all('ManageCom').value + "%25'" ;
	}
	else
	{
		//�������Ϊ�գ���Ĭ���Ե�ǰ������ѯ
		strSql=strSql+" and a.managecom like '%25" + ComCode + "%25'" ;
	}
	
	if(!(document.all('BPOID').value==null||document.all('BPOID').value==""))
	{
		 strSql=strSql+ " and d.BPOID = '" + document.all('BPOID').value + "'" ;
	}
	
	strSql=strSql+ addSQL+" order by a.makedate,a.maketime" ;
	
  //prompt("",strSql);
  //alert(strSql);
	//turnPage.queryModal(strSql, PolGrid);
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {  
    //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
    PolGrid.clearData();  
    return false;
  }
  
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
}



function SpotCheck() 
{ 
  if (PolGrid.getSelNo() == 0) {
    alert("����ѡ��һ���շѵ���Ϣ��");
    return false;
  } 
  
  var TempFeeNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
  mSwitch.deleteVar( "TempFeeNo" );
  mSwitch.addVar( "TempFeeNo", "", TempFeeNo );
  var strSql = "";
  if(_DBT==_DBO){
	  //strSql = "select * from ldsystrace where PolNo='" + TempFeeNo + "' and CreatePos='���˳��'  and  PolState='2002' and rownum='1'";
	  var  sqlid1="ProposalSpotCheckQuerySql0";
   	  var  mySql1=new SqlClass();
   	  mySql1.setResourceName("finfee.ProposalSpotCheckQuerySql"); //ָ��ʹ�õ�properties�ļ���
   	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   	  mySql1.addSubPara(TempFeeNo);//ָ������Ĳ���
   	  strSql=mySql1.getString();
  }else if(_DBT==_DBM){
	  //strSql = "select * from ldsystrace where PolNo='" + TempFeeNo + "' and CreatePos='���˳��'  and  PolState='2002' limit 0,1";
	  var  sqlid2="ProposalSpotCheckQuerySql1";
   	  var  mySql2=new SqlClass();
   	  mySql2.setResourceName("finfee.ProposalSpotCheckQuerySql"); //ָ��ʹ�õ�properties�ļ���
   	  mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
   	  mySql2.addSubPara(TempFeeNo);//ָ������Ĳ���
   	  strSql=mySql2.getString();
	  
  }
  
  //prompt("",strSql);
  var arrResult = easyExecSql(strSql);
  //alert("arrResult[0][1]"+arrResult[0][1]);
  //alert("Operator"+Operator);
  if (arrResult!=null && arrResult[0][1]!=Operator) {
    alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
    return;
  }
  //������ӡˢ��
  var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + TempFeeNo + "&CreatePos=���˳��&PolState=2002&Action=INSERT";
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  easyScanWin = window.open("./FineWbProposalInputMain1.jsp?LoadFlag=4&DealType=01&TempFeeNo="+TempFeeNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");    
  
}


