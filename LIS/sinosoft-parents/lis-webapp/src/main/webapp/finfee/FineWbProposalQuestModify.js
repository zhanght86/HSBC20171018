//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";


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
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		top.close();
	}
}

// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var queryBug = 1;
function initQuery() { 
	// ��ʼ�����
	initPolGrid();

	var addSQL = "";
	if(!(document.all('ManageCom').value==null||document.all('ManageCom').value==""))
	{
		 addSQL=addSQL+ " and a.managecom like '%25" + document.all('ManageCom').value + "%25'" ;
	}
	else
	{
		//�������Ϊ�գ���Ĭ���Ե�ǰ������ѯ
		addSQL=addSQL+" and a.managecom like '%25" + manageCom + "%25'" ;
	}	
	var strSql1 = "";
	if(_DBT==_DBO){
//		strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='�쳣������'  and  PolState='2002' and rownum=1 ) operator,d.bussno"
//		     +" ,a.managecom,d.bpoid,(case dealtype when '02' then '��������ؿɴ����쳣��' when '03' then '������޷�������쳣����������ɨ����޷�ʶ��' when '04' then '��������ϵͳ�������µ��쳣��' end) "
//			 +" ,a.makedate,a.maketime"
//			 +" ,d.dealtype "
//			 +" from ES_DOC_MAIN a,BPOMissionState d "                                    
//	                +" where a.doccode=d.bussno"
//		        +" and d.bussnotype='OF'"
//		        +" and d.dealtype in ('02','03','04') and a.makedate>=now()-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//		        + getWherePart('a.makedate','ScanDate','<=')
//		        +addSQL
//		        +" order by a.makedate,a.maketime "	;
		
	    
        var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
     	var  sqlid1="FineWbProposalQuestModifySql0";
     	var  mySql1=new SqlClass();
     	mySql1.setResourceName("finfee.FineWbProposalQuestModifySql"); //ָ��ʹ�õ�properties�ļ���
     	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
     	mySql1.addSubPara(manageCom);//ָ������Ĳ���
     	mySql1.addSubPara(ScanDate);//ָ������Ĳ���
     	mySql1.addSubPara(addSQL);//ָ������Ĳ���
     	strSql1=mySql1.getString();
	}else if(_DBT==_DBM){
//		strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='�쳣������'  and  PolState='2002' limit 0,1 ) operator,d.bussno"
//		     +" ,a.managecom,d.bpoid,(case dealtype when '02' then '��������ؿɴ����쳣��' when '03' then '������޷�������쳣����������ɨ����޷�ʶ��' when '04' then '��������ϵͳ�������µ��쳣��' end) "
//			 +" ,a.makedate,a.maketime"
//			 +" ,d.dealtype "
//			 +" from ES_DOC_MAIN a,BPOMissionState d "                                    
//	                +" where a.doccode=d.bussno"
//		        +" and d.bussnotype='OF'"
//		        +" and d.dealtype in ('02','03','04') and a.makedate>=now()-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//		        + getWherePart('a.makedate','ScanDate','<=')
//		        +addSQL
//		        +" order by a.makedate,a.maketime "	;
		    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
	     	var  sqlid2="FineWbProposalQuestModifySql1";
	     	var  mySql2=new SqlClass();
	     	mySql2.setResourceName("finfee.FineWbProposalQuestModifySql"); //ָ��ʹ�õ�properties�ļ���
	     	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	     	mySql2.addSubPara(manageCom);//ָ������Ĳ���
	     	mySql2.addSubPara(ScanDate);//ָ������Ĳ���
	     	mySql2.addSubPara(addSQL);//ָ������Ĳ���
	     	strSql1=mySql2.getString();
		
	}
	 
 
	turnPage.queryModal(strSql1, PolGrid);
}

function easyQueryClick(){
	initPolGrid();
	
	var addSQL = "";
	
	if(!(document.all('ManageCom').value==null||document.all('ManageCom').value==""))
	{
		 addSQL=addSQL+ " and a.managecom like '%25" + document.all('ManageCom').value + "%25'" ;
	}
	else
	{
		//�������Ϊ�գ���Ĭ���Ե�ǰ������ѯ
		addSQL=addSQL+" and a.managecom like '%25" + manageCom + "%25'" ;
	}	
     var strSql1 = "";
     if(_DBT==_DBO){
//    	 strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='�쳣������'  and  PolState='2002' and rownum=1 ) operator,d.bussno,a.managecom,d.bpoid,(case dealtype when '02' then '��������ؿɴ����쳣��' when '03' then '������޷�������쳣����������ɨ����޷�ʶ��' when '04' then '��������ϵͳ�������µ��쳣��' end) "
//    			+" ,a.makedate,a.maketime,d.dealtype from ES_DOC_MAIN a,BPOMissionState d "                                    
//    	                +" where a.doccode=d.bussno"
//    		        +" and d.bussnotype='OF'"
//    		        +" and d.dealtype in ('02','03','04') and a.makedate>=sysdate-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//    		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//    		        + getWherePart('a.makedate','ScanDate','<=')
//    			      + getWherePart( 'BussNo','TempFeeNo' )
//    			      + getWherePart( 'BPOID','BPOID' )
//    			      + addSQL
//    			      +" order by a.makedate,a.maketime "	;
    	 
    	    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
    	    var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
    	    var  BPOID = window.document.getElementsByName(trim("BPOID"))[0].value;
	     	var  sqlid3="FineWbProposalQuestModifySql2";
	     	var  mySql3=new SqlClass();
	     	mySql3.setResourceName("finfee.FineWbProposalQuestModifySql"); //ָ��ʹ�õ�properties�ļ���
	     	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	     	mySql3.addSubPara(manageCom);//ָ������Ĳ���
	     	mySql3.addSubPara(ScanDate);//ָ������Ĳ���
	     	mySql3.addSubPara(TempFeeNo);//ָ������Ĳ���
	     	mySql3.addSubPara(BPOID);//ָ������Ĳ���
	     	mySql3.addSubPara(addSQL);//ָ������Ĳ���
	     	strSql1=mySql3.getString();
     }else if(_DBT==_DBM){
//    	 strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='�쳣������'  and  PolState='2002' limit 0,1 ) operator,d.bussno,a.managecom,d.bpoid,(case dealtype when '02' then '��������ؿɴ����쳣��' when '03' then '������޷�������쳣����������ɨ����޷�ʶ��' when '04' then '��������ϵͳ�������µ��쳣��' end) "
//    			+" ,a.makedate,a.maketime,d.dealtype from ES_DOC_MAIN a,BPOMissionState d "                                    
//    	                +" where a.doccode=d.bussno"
//    		        +" and d.bussnotype='OF'"
//    		        +" and d.dealtype in ('02','03','04') and a.makedate>=now()-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//    		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//    		        + getWherePart('a.makedate','ScanDate','<=')
//    			      + getWherePart( 'BussNo','TempFeeNo' )
//    			      + getWherePart( 'BPOID','BPOID' )
//    			      + addSQL
//    			      +" order by a.makedate,a.maketime "	;
    	 
    	    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
 	        var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
 	        var  BPOID = window.document.getElementsByName(trim("BPOID"))[0].value;
	     	var  sqlid4="FineWbProposalQuestModifySql3";
	     	var  mySql4=new SqlClass();
	     	mySql4.setResourceName("finfee.FineWbProposalQuestModifySql"); //ָ��ʹ�õ�properties�ļ���
	     	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	     	mySql4.addSubPara(manageCom);//ָ������Ĳ���
	     	mySql4.addSubPara(ScanDate);//ָ������Ĳ���
	     	mySql4.addSubPara(TempFeeNo);//ָ������Ĳ���
	     	mySql4.addSubPara(BPOID);//ָ������Ĳ���
	     	mySql4.addSubPara(addSQL);//ָ������Ĳ���
	     	strSql1=mySql4.getString();
     }			
	turnPage.queryModal(strSql1, PolGrid);
	
}


var mSwitch = parent.VD.gVSwitch;
function modifyClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cTempFeeNo = PolGrid.getRowColData(checkFlag - 1, 2); 	
  	mSwitch.deleteVar( "TempFeeNo" );
  	mSwitch.addVar( "TempFeeNo", "", cTempFeeNo );
  	
  
  	
  //var strSql = "select * from ldsystrace where PolNo='" + cTempFeeNo + "' and CreatePos='�쳣������'  and  PolState='2002'";
    var  sqlid5="FineWbProposalQuestModifySql4";
	var  mySql5=new SqlClass();
	mySql5.setResourceName("finfee.FineWbProposalQuestModifySql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(cTempFeeNo);//ָ������Ĳ���
	strSql=mySql5.getString();
  //prompt("",strSql);
  var arrResult = easyExecSql(strSql);
  //alert("arrResult[0][1]"+arrResult[0][1]);
  //alert("Operator"+Operator);
  if (arrResult!=null && arrResult[0][1]!=operator) {
    alert("��ӡˢ�ŵ�Ͷ�����Ѿ�������Ա��" + arrResult[0][1] + "���ڣ�" + arrResult[0][5] + "��λ�������������ܲ�������ѡ������ӡˢ�ţ�");
    return;
  }
    //������ӡˢ��
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + cTempFeeNo + "&CreatePos=�쳣������&PolState=2002&Action=INSERT";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  	
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var TempFeeNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    var tType=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 8);
    //alert("tType:"+tType);
    //alert("TempFeeNo:"+TempFeeNo);
    window.open("./FineWbProposalInputMain1.jsp?LoadFlag=3&DealType="+tType+"&TempFeeNo="+TempFeeNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  }
  else {
    alert("����ѡ��һ���շѵ���Ϣ��"); 
  }
  
    initQuery();
}
 
function unlock(TempFeeNo)
{
	//alert("TempFeeNo"+TempFeeNo);
	
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+TempFeeNo+"&CreatePos=�쳣������&PolState=2002&Action=DELETE";
  	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	initQuery();
}


