//�������ƣ�UWErr.js
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��sxy
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPOLNO = "";
var turnPage = new turnPageClass();
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  if(!checkChk())
	{
		return false;
	}
  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//У���Ƿ�ѡ��������.
	
  //showSubmitFrame(mDebug);
  fm.submit(); //�ύ
  //alert("submit");
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //ִ����һ������
  }
  easyQueryClick();
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
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
	initUWErrGrid();
	
	// ��дSQL���
	var strSQL = "";
	var ContNo = document.all('ContNo').value;
	
	var PolNo = document.all('PolNo').value;
	
//��ͬ��,������,���ֱ���,��������,�˱���Ϣ,�޸�ʱ��,�Ƿ�����,Ͷ������,��ˮ��,�˱����к�,��ͬ���ֱ��,Ͷ������
//	strSQL = "select A.contno,A.insuredno,A.riskcode,A.riskname,A.uwerror,A.modifydate,A.passname, "
//	       + " A.x,A.serialno,A.uwno,A.flag,A.proposalno from ( "
//         + " select /*+index (a PK_LCUWERROR)*/a.contno,b.insuredno,b.riskcode, "
//         + " (select riskname from lmriskapp where riskcode=b.riskcode) riskname,a.uwerror, "
//         + " concat(concat(to_char(a.modifydate,'yyyy-mm-dd'),' '),a.modifytime) modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=(case when a.sugpassflag is not null then a.sugpassflag else 'N' end)) passname, "
//         + " a.proposalno x,a.serialno,a.uwno,'risk' flag,b.proposalno proposalno "
//	       + " from LCUWError a,lcpol b "
//         + " where a.proposalno=b.proposalno "
//	       + " and b.contno='"+ContNo+"' "
//	   //    + " and (a.uwno = (select max(b.uwno) "
//		//		 + " from LCUWError b where b.ContNo = '"+ContNo+"' "
//		//		 + " and b.PolNo = a.PolNo)) "
//				+" and (a.uwno in (select c.batchno "
//				+" from LCUWMaster c where c.ContNo = '"+ContNo+"' "
//				+ " and c.proposalno = a.proposalno)) "
//		//��ͬ�˱���Ϣ��ѯ
//         + " union "
//         + " select c.contno,d.insuredno,'000000' riskcode,'��ͬ�˱���Ϣ' riskname, c.uwerror, "
//         + "concat(concat( to_char(c.modifydate,'yyyy-mm-dd'),' '),c.modifytime) modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=(case when c.sugpassflag is not null then c.sugpassflag else 'N' end)) passname, "
//         + " c.proposalcontno x,c.serialno,c.uwno,'cont' flag,'999999999999999' proposalno "
//	       + " from LCCUWError c,lccont d "
//         + " where 1 = 1 "
//	       + " and c.ContNo = '"+ContNo+"' "
//         + " and c.contno=d.contno "
//	  //     + " and (c.uwno = (select max(d.uwno) "
//		//		 + " from LCCUWError d where d.ContNo = '"+ContNo+"')) "
//				+ " and (c.uwno in (select e.batchno "
//				 + " from LCCUWMaster e where e.ContNo = '"+ContNo+"')) "
//		//�����˺˱���Ϣ��ѯ
//		 + " union "
//         + " select c.contno,d.insuredno,'000000' riskcode,'�����˺˱���Ϣ' riskname, c.uwerror, "
//         + " concat(concat(to_char(c.modifydate,'yyyy-mm-dd'),' '),c.modifytime) modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=(case when c.sugpassflag is not null then c.sugpassflag else 'N' end)) passname, "
//         + " c.proposalcontno x,c.serialno,c.uwno,'insured' flag,'999999999999999' proposalno "
//	       + " from LCIndUWError c,lcinsured d "
//         + " where 1 = 1 "
//	       + " and c.ContNo in (select prtno from lccont where contno='"+ContNo+"') "
//         + " and c.proposalcontno=d.prtno "
//         + " and c.insuredno=d.insuredno "
//				+ " and (c.uwno in (select e.batchno "
//				 + " from LCIndUWMaster e where e.ContNo = '"+ContNo+"' and e.insuredno=c.insuredno)) "
//         + " ) A order by A.contno,A.insuredno,A.proposalno,A.riskcode,A.modifydate";
//	prompt("",strSQL);
	//execEasyQuery( strSQL );
	
	var sqlid1="UWErrSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.UWErrSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(PrtNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	mySql1.addSubPara(ContNo);//ָ������Ĳ���
	strSQL = mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
	  //�������ϵͳת������ݣ��������SQL��鲻���Ժ���Ϣ ����������Ĳ�ѯ����ѯ��ϵͳ���Ժ���Ϣ���������SQL����ѯ
	  //������������Ϊ���Զ��˱���Ϣ05-21
//	  var tSql = " select contno,insuredno,'','��ͬ�˱���Ϣ',uwerror,concat(concat(to_char(b.outdate, 'yyyy-mm-dd') , ' ' ), b.outtime),'������'"
//		  	 + " from lcuwerror a,lbmission b where contno='"+ContNo+"' and b.missionprop1=(select prtno from lccont where contno='"+ContNo+"')"
//		  	 + " and b.serialno=(select max(serialno) from lbmission where missionprop1=b.missionprop1 and activityid='0000001003')";
	
	  var sqlid2="UWErrSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWErrSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(PrtNo);//ָ������Ĳ���
		mySql2.addSubPara(ContNo);//ָ������Ĳ���
		mySql2.addSubPara(ContNo);//ָ������Ĳ���
		var tSql = mySql2.getString();
		
	  turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1); 
	  if(!turnPage.strQueryResult){
	    alert("���Զ��˱���Ϣ��");
	    return "";
	  }
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWErrGrid;    
          
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
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				UWErrGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}
function checkChk()
{
	var tCount = 0;
	var i;
	for(i=0;i<UWErrGrid.mulLineCount;i++)
	{
		  var t =UWErrGrid.getChkNo(i);
		  //alert(t);
			if(t)
			{
				tCount++;
			}
	}
	if(tCount<=0)
	{
		alert("��ѡ����Ҫ���ĵĺ˱���Ϣ!");
		return false;
	}
	return true;
}