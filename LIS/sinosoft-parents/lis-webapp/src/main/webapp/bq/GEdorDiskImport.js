/*******************************************************************************
* <p>Title: ��ȫ-�ŵ����̵���</p>
* <p>Description: �ŵ����̵���js�ļ�</p>
* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: �п���Ƽ��ɷ����޹�˾</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : ��ȫ-�ŵ����̵���
* @author   : zhangtao
* @version  : 1.00 
* @date     : 2006-11-24
* @modify   : 2006-11-25
******************************************************************************/

var mDebug="1";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass();	//ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
window.onfocus=myonfocus;
var arrDataSet;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	getImportPath();
	 
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
	document.all('ImportPath').value = ImportPath;
	fm.action = "../bq/GEdorDiskImportSave.jsp?EdorNo=" + fm.EdorNo.value + "&EdorAcceptNo=" + fm.EdorAcceptNo.value + "&EdorType=" + fm.EdorType.value + "&EdorValiDate=" + fm.EdorValiDate.value + "&BQFlag=Y";
	fm.submit(); //�ύ
}

/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ����ʾ���������־
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	     
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    getErrorLogAfterSubmit();
}

function getImportPath ()
{
	// ��дSQL���
	var strSQL = "";
//	strSQL = "select SysvarValue from ldsysvar where sysvar ='ImportPath'";
	var sqlid1="GEdorDiskImportSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorDiskImportSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	strSQL=mySql1.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("δ�ҵ��ϴ�·��");
		return;
	}
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	ImportPath = turnPage.arrDataCacheSet[0][0];
	
}

/**********************************
 *�����鿴������־
 **********************************/
function getErrorLogAfterSubmit(){
	var str=fm.FileName.value;//�ļ�ȫ·��
	//��ȡ��ȫ����ź����κ�
	//var edoracceptno=str.substring(str.indexOf("_",1)+1,str.lastIndexOf("_"));//index������
	var batchno=str.substring(str.lastIndexOf("_")+1,str.indexOf("."));

	try{
//		var querySQL="select GrpContNo,OtherNo,StandbyFlag1,BatchNo,ID,ErrorInfo,Operator,MakeDate,MakeTime "
//		+"from LCGrpImportLog where othernotype='10' "
//		+" and grpcontno = '" + fm.GrpContNo.value + "' "
//		+" and standbyflag1 = '" + fm.EdorType.value + "' "
//		+" and otherno = '" + fm.EdorAcceptNo.value + "' ";
		//+" and batchno = '" + batchno + "'";
		var querySQL="";
		var sqlid1="GEdorDiskImportSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.GEdorDiskImportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
		querySQL=mySql1.getString();		
		try
		{
			turnPage.queryModal(querySQL,DataGrid);
		}
		catch (ex)
		{
			alert("��ѯ������־�쳣!");
		}
	}
	catch(ex){
		alert("GEdorDiskImportInit.js-->fillDataGrid�����з����쳣:δ�ܻ����ȷ����!");
	} 
}
/**********************************
 *��������ѯ������־
 **********************************/
function queryErrorLog(){
    
	var grpcontno=fm.qGrpContNo.value;
	var edoracceptno=fm.qEdorAcceptNo.value;
	var edortype=fm.qEdorType.value;
	var operator=fm.qOperator.value;
	var makedate=fm.errorDate.value;
	var batchno=fm.qBatchNo.value;
	 
	if(grpcontno==""&&edoracceptno==""&&edortype==""&&operator==""&&makedate==""&&batchno==""){
		alert("�������ѯ������");
		return;
	}
	try{
//		var querySQL="select GrpContNo,OtherNo,StandbyFlag1,BatchNo,ID,ErrorInfo,Operator,MakeDate,MakeTime "
//		+"from LCGrpImportLog where 1=1 and othernotype='10'"
//		+ getWherePart('GrpContNo','qGrpContNo')
//		+ getWherePart('OtherNo','qEdorAcceptNo')
//		+ getWherePart('standbyflag1','qEdorType')		
//		+ getWherePart('batchno','qBatchNo')
//		+ getWherePart('Operator','qOperator')
//		+ getWherePart('MakeDate','errorDate');		 
		//"grpcontno='880000005763' and standbyflag1='IR' and otherno='6120061122000011' and othernotype='10'";
		var querySQL="";
		var sqlid1="GEdorDiskImportSql3";
		var mySql1=new SqlClass();
		mySql1.setResourceName("bq.GEdorDiskImportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(grpcontno);//ָ������Ĳ���
		mySql1.addSubPara(edoracceptno);//ָ������Ĳ���
		mySql1.addSubPara(edortype);//ָ������Ĳ���
		mySql1.addSubPara(batchno);//ָ������Ĳ���
		mySql1.addSubPara(operator);//ָ������Ĳ���
		mySql1.addSubPara(makedate);//ָ������Ĳ���
		querySQL=mySql1.getString();		
		try
		{
			turnPage.queryModal(querySQL,DataGrid);
		}
		catch (ex)
		{
			alert("��ѯ������־�쳣!");
		}
	}
	catch(ex){
		alert("GEdorDiskImportInit.js-->queryErrorLog�����з����쳣:δ�ܻ����ȷ����!");
	} 
}

/**************************************
 *����
 *************************************/
function returnParent()
{
    try
    {
    	  top.opener.initForm();
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}