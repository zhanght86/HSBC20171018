//�������ƣ�PEdorUWManuHealth.js
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();

var arrResult;
var turnPage1=new turnPageClass();
var turnPage2=new turnPageClass();
//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
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

  fm.action= "./UWManuHealthChk.jsp";
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//initInsureNo();
	//initHealthGrid();
	//initHealthOtherGrid();
	//initMainUWHealthGrid();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
   
    showInfo.close();
    alert(content);
    window.location.reload();
   
  }
  else
  { 
	  var showStr="�����ɹ���";
  	
  	showInfo.close();
  	alert(showStr);
  	if(fm.UWFlag.value!="1"){
	  	//top.opener.easyQueryClick();
  		jQuery("#publicSearch").click();
	   	top.close();
  	}
  	window.location.reload();
  	
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else
  {
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


function manuchkhealthmain()
{
	document.getElementById("fm").submit();
}


// ��ѯ��ť
function easyQueryClickSingle()
{
	// ��дSQL���
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {
	   var sqlid908111949="DSHomeContSql908111949";
var mySql908111949=new SqlClass();
mySql908111949.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql908111949.setSqlId(sqlid908111949);//ָ��ʹ�õ�Sql��id
mySql908111949.addSubPara(tContNo);//ָ������Ĳ���
mySql908111949.addSubPara(tInsuredNo);//ָ������Ĳ���
strsql=mySql908111949.getString();
	   
//	   strsql = "select a.ContNo,(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq)"
//		   			+ " ,a.CustomerNo,a.Name,a.PEDate,a.MakeDate,"
//		   			//+ " (select stateflag from loprtmanager where prtseq=(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq))"
//		   			+ " a.printflag "
//		   			+ " from LCPENotice a where 1=1"
//				    + " and exists (select 1 from loprtmanager  where oldprtseq=a.prtseq)"
//				    + " and ContNo = '"+ tContNo + "'"
//				    + " and Customerno = '"+ tInsuredNo + "'";
				 				 
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = MainHealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
 
}


// ��ѯ��ť
function easyQueryClick(parm1,parm2)
{

	// ��дSQL���
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	var tSelNo = MainHealthGrid.getSelNo()-1;
  var tPrtSeq = MainHealthGrid.getRowColData(tSelNo,8);
  fm.PrtSeq.value = tPrtSeq;
  var tCustomerno = MainHealthGrid.getRowColData(tSelNo,3);
  fm.CustomerNo.value = tCustomerno;
  var tNewPrtSeq = MainHealthGrid.getRowColData(tSelNo,2);
  
  var sqlid908112202="DSHomeContSql908112202";
var mySql908112202=new SqlClass();
mySql908112202.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql908112202.setSqlId(sqlid908112202);//ָ��ʹ�õ�Sql��id
mySql908112202.addSubPara(tContNo);//ָ������Ĳ���
var tPrtNoSql=mySql908112202.getString();

  
//  var tPrtNoSql = "select prtno from lccont where contno='"+tContNo+"'";
  var tPrtNo = "";
  var tArrPrtNo = easyExecSql(tPrtNoSql);
  if(tArrPrtNo!=null){
	  tPrtNo = tArrPrtNo[0][0];
  }
  document.all('PEName').value=MainHealthGrid.getRowColData(tSelNo,4);
	//��ѯ���֪ͨ��Ӱ��
	queryHealthPic(tPrtNo,tContNo,tNewPrtSeq);

  if(tContNo != "" && tPrtSeq != "")
  { 
		var sqlid908112558="DSHomeContSql908112558";
		var mySql908112558=new SqlClass();
		mySql908112558.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
		mySql908112558.setSqlId(sqlid908112558);//ָ��ʹ�õ�Sql��id
		mySql908112558.addSubPara(tContNo);//ָ������Ĳ���
		mySql908112558.addSubPara(tContNo);//ָ������Ĳ���
		mySql908112558.addSubPara(tContNo);//ָ������Ĳ���
		mySql908112558.addSubPara(tContNo);//ָ������Ĳ���
		mySql908112558.addSubPara(tContNo);//ָ������Ĳ���
		mySql908112558.addSubPara(tContNo);//ָ������Ĳ���
		mySql908112558.addSubPara(tPrtSeq);//ָ������Ĳ���
		var strsql1=mySql908112558.getString();
		
		//��ѯ��������Ϣ
//		var strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LCPENotice.customertype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno))),"
//                         + " decode(LCPENotice.customertype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno)),"
//                         + " customerno, customertype,PEAddress ,REPEDate, agentname, operator,makedate,modifydate,PEResult,hospitcode "
//                         + " from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
//			prompt("",strsql1);
       var arrReturn = new Array();
       //modify-zbx-20120417 ��֤����
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//���������
        	document.all('Sex').value = arrReturn[0][2];//������Ա�
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//���������
        	//document.all('CustomerNo').value = arrReturn[0][4];//���������
        	document.all('PEAddress').value = arrReturn[0][6];
        	document.all('PEHospital').value = arrReturn[0][13];
        	document.all('PEDate').value = arrReturn[0][7]; 
        	document.all('AccName').value = arrReturn[0][8];      	        	      	 	
       	 	document.all('Operator').value = arrReturn[0][9];
        	document.all('MakeDate').value = arrReturn[0][10];
        	document.all('ReplyDate').value = arrReturn[0][11];
        	
        	var PEResult = arrReturn[0][12];
        	var t1 = PEResult.indexOf(":");
        	var t2 = PEResult.lastIndexOf(":");

        	document.all('PEResult').value = PEResult.substring(0,t1);
        	document.all('PEResultDes').value = PEResult.substring(t1+1,t2);
        	document.all('Note').value = PEResult.substring(t2+1);
        	
//        	var sqlid908113620="DSHomeContSql908113620";
//			var mySql908113620=new SqlClass();
//			mySql908113620.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
//			mySql908113620.setSqlId(sqlid908113620);//ָ��ʹ�õ�Sql��id
//			mySql908113620.addSubPara(tContNo);//ָ������Ĳ���
//			mySql908113620.addSubPara(arrReturn[0][4]);//ָ������Ĳ���
//			mySql908113620.addSubPara(arrReturn[0][5]);//ָ������Ĳ���
//			mySql908113620.addSubPara(tPrtSeq);//ָ������Ĳ���
//			var addStr = " and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"')) ";
//			mySql908113620.addSubPara(addStr);//ָ������Ĳ���
//			strsql1=mySql908113620.getString();
        	
        	strsql1="select 1 from LCPENotice where 1=1"	 
				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
				         + " and customertype='"+  arrReturn[0][5] +"'"
				         + " and PrtSeq <> '"+ tPrtSeq + "' and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"'))";
        	arrReturn = new Array();
	        arrReturn = easyExecSql(strsql1); 
	        if(arrReturn!=null)
	           document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
	        else 
	           document.all('RePETag').value = "N";
        } 
			
			var sqlid908113922="DSHomeContSql908113922";
var mySql908113922=new SqlClass();
mySql908113922.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql908113922.setSqlId(sqlid908113922);//ָ��ʹ�õ�Sql��id
mySql908113922.addSubPara(tPrtNo);//ָ������Ĳ���
mySql908113922.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql908113922.getString();
			//��ѯ��ѡ�����Ŀ��Ϣ
//			var tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor,'1'"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='0' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,102);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			
			var sqlid908114010="DSHomeContSql908114010";
var mySql908114010=new SqlClass();
mySql908114010.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql908114010.setSqlId(sqlid908114010);//ָ��ʹ�õ�Sql��id
mySql908114010.addSubPara(tPrtNo);//ָ������Ĳ���
mySql908114010.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql908114010.getString();
			//��ѯ��ѡ�����Ŀ��Ϣ
//			tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='1' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthOtherGrid,0,0,102);
			//��ѯ���������Ŀ��Ϣ
			var sqlid914163929="DSHomeContSql914163929";
var mySql914163929=new SqlClass();
mySql914163929.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914163929.setSqlId(sqlid914163929);//ָ��ʹ�õ�Sql��id
mySql914163929.addSubPara(tPrtNo);//ָ������Ĳ���
mySql914163929.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql914163929.getString();
			
//			tstrsql = "select peitemdivname,peitemdivres,peitemdivdes"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and peitemcode='Other'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		        {
		           document.all('OtherPEItem').value = arrReturn[0][0];
		          fm.OtherPEItemRes.value = arrReturn[0][1];
		          fm.OtherPEItemDes.value = arrReturn[0][2];
		          fm.OtherPEItemResCode.value=arrReturn[0][3];
		        }  
		          
		     //��ѯ������ʷ��Ϣ
		     var sqlid914164022="DSHomeContSql914164022";
var mySql914164022=new SqlClass();
mySql914164022.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164022.setSqlId(sqlid914164022);//ָ��ʹ�õ�Sql��id
mySql914164022.addSubPara(tPrtNo);//ָ������Ĳ���
mySql914164022.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql914164022.getString();

		     
//			tstrsql = "select DisResult, DisDesb from LCPENoticeResult where 1=1 "
//					  + " and ContNo = '"+ tPrtNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		       {
			     document.all('PastDiseaseCode').value = arrReturn[0][0];  	   
		         document.all('PastDiseaseRes').value = arrReturn[0][1];
		         document.all('PastDiseaseDes').value = arrReturn[0][2];
		       }
		          
			
		/**	//��ѯ��������Ϣ
	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
	          // + " and ContNo = '"+ tContNo + "'"
					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//��ѯ���������Ϣ			 
			var strSQL = "select * from LCPENotice where 1=1"	
					+" and ContNo = '"+tContNo+"'"
					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
		**/	
              
      
  }
  /**
  if(tContNo != "" && tPrtSeq != "")
  {
	   var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
				
				  //+ " and ContNo = '"+ tContNo + "'"
				  + " and PrtSeq = '"+ tPrtSeq + "'";
	 
	
turnPage.strQueryResult  = easyQueryVer3(tstrsql, 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	
    alert("δ��ѯ�������Ŀ���ݣ�");
     return false;
  }
  
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 30 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = tstrsql; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage1.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
  }
 
  //��ѯ��������Ϣ
  var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
          // + " and ContNo = '"+ tContNo + "'"
				 + " and PrtSeq = '"+ tPrtSeq + "'";
				  //��ѯSQL�����ؽ���ַ���
				  //alert(Sql);
  turnPage.strQueryResult = easyQueryVer3(Sql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = DisDesbGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = Sql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  	//��ѯ���������Ϣ			 
	var strSQL = "select * from LCPENotice where 1=1"	
				+" and ContNo = '"+tContNo+"'"
				+" and PrtSeq = '"+tPrtSeq+"'"; 	
		
	var arrReturn = new Array();
        arrReturn = easyExecSql(strSQL);
      
   if (arrReturn == null) {
			  alert("δ��ѯ�������Ϣ��");
			} else {
			   displayHealth(arrReturn[0]);
			}  

**/			
  return true;
 
}

function displayHealth(cArr)
{
	
  	try { document.all('Note').value = cArr[21]; } catch(ex) { };
}


// ��������ϲ�ѯ��ť
function easyQueryClickInit()
{
	fm.action= "./UWManuHealthQuery.jsp";
	document.getElementById("fm").submit();
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
				HealthGrid.setRowColData( i, j+1, arrResult[i][j] );
				
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if

}

function initInsureNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	
		//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
// var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
//          + " and ContNo = '"+tContNo+"'";

//  var strSql = " select a1, a2, a3, a4, a5, a6, a7, a9"
//             + " from (select a.ContNo as a1,"
//             + " b.PrtSeq as a2,"
//             + " a.CustomerNo as a3,"
//             + " a.Name as a4,"
//             + " a.RePedate as a5,"
//             + " a.MakeDate as a6,"
//             + " a.PrintFlag as a7,"
//             + " row_number() over(partition by b.oldprtseq order by b.prtseq) as a8,"
//             + " b.oldprtseq a9"
//             + " from lcpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             + " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";
	var sqlid914164136="DSHomeContSql914164136";
var mySql914164136=new SqlClass();
mySql914164136.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164136.setSqlId(sqlid914164136);//ָ��ʹ�õ�Sql��id
mySql914164136.addSubPara(tContNo);//ָ������Ĳ���
var strSql=mySql914164136.getString();
	
	
//	var strSql ="select a.ContNo,(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq)"
//			+ " ,a.CustomerNo,a.Name,a.REPEDate,a.MakeDate,"
//   			+ " a.printflag,a.prtseq"
//   			+ " from LCPENotice a where 1=1"
//		    + " and exists (select 1 from loprtmanager  where oldprtseq=a.prtseq)"
//		    + " and ContNo = '"+ tContNo + "'";
		    //+ " and Customerno = '"+ tInsuredNo + "'";

 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
//    alert("��ѯʧ�ܣ�");
	  /*09-06-11 ����Լɨ���������ֱ��¼�������Ϣ�ᵼ�´˴��޷���ѯ����Ϣ
	  	�˴���readonly��form ��Ϊ���޸�*/
	  fm.PrtNo.value = tContNo;
	  fm.PEName.className = "common";
	  fm.PEName.readOnly = false;
	  fm.Sex.className = "common";
	  fm.Sex.readOnly = false;
	  fm.Age.className = "common";
	  fm.Age.readOnly = false;
    return "";
  }
  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = MainHealthGrid;

 //����SQL���
  turnPage.strQuerySql   = strSql;
  
 //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //����MULTILINE������ʾ��ѯ���
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);


}

  function initCustomerNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	
		//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
//  var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
//             + " and ContNo = '"+tContNo+"'"
//             + " and CustomerNo = '"+tCustomerNo+"'";
//   var strSql = " select a1, a2, a3, a4, a5, a6, a7, a9"
//             + " from (select a.ContNo as a1,"
//             + " b.PrtSeq as a2,"
//             + " a.CustomerNo as a3,"
//             + " a.Name as a4,"
//             + " a.Pedate as a5,"
//             + " a.MakeDate as a6,"
//             + " a.PrintFlag as a7,"
//             + " row_number() over(partition by b.oldprtseq order by b.prtseq) as a8,"
//             + " b.oldprtseq a9"
//             + " from lcpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             //+ " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.customerno='"+tCustomerNo+"'"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";

	var sqlid914164341="DSHomeContSql914164341";
var mySql914164341=new SqlClass();
mySql914164341.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164341.setSqlId(sqlid914164341);//ָ��ʹ�õ�Sql��id
mySql914164341.addSubPara(tContNo);//ָ������Ĳ���
mySql914164341.addSubPara(tCustomerNo);//ָ������Ĳ���
var strSql=mySql914164341.getString();
	
//	var strSql ="select a.ContNo,(select max(prtseq) from loprtmanager where oldprtseq=a.prtseq)"
//		+ " ,a.CustomerNo,a.Name,a.PEDate,a.MakeDate,"
//			+ " a.printflag,a.prtseq"
//			+ " from LCPENotice a where 1=1"
//	    + " and exists (select 1 from loprtmanager  where oldprtseq=a.prtseq)"
//	    + " and ContNo = '"+ tContNo + "'"
//	    + " and Customerno = '"+ tCustomerNo + "'";
 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
 
  if (!turnPage.strQueryResult)
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }
  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = MainHealthGrid;

 //����SQL���
  turnPage.strQuerySql   = strSql;
  
 //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //����MULTILINE������ʾ��ѯ���
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   
}


//��ʼ��ҽԺ
function initHospital(tContNo)
{
	var i,j,m,n;
	var returnstr;
	

	var sqlid914164447="DSHomeContSql914164447";
var mySql914164447=new SqlClass();
mySql914164447.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164447.setSqlId(sqlid914164447);//ָ��ʹ�õ�Sql��id
mySql914164447.addSubPara(tContNo);//ָ������Ĳ���
var strSql=mySql914164447.getString();
	
//	var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("ҽԺ��ʼ��ʧ�ܣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = VarGrid;    
          
  //����SQL���
  //turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("��ѯʧ��!!");
  			return "";
  		}
  	}
}
else
{
	alert("��ѯʧ��!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.Hospital.CodeData = returnstr;
  return returnstr;
}


/*********************************************************************
 *  ��켲����Ϣ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function saveDisDesb()
{   
	 /**
		if(DisDesbGrid.getRowColData(0,2)==null||DisDesbGrid.getRowColData(0,2)==""){
		alert("�����������Ϊ�գ�");
		return false;		
		}			
		
	     var strsql1="select managecom from lccont where contno = '"+fm.ContNo.value+"'";
			
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('ManageCom').value = arrReturn[0][0];
        	//alert(document.all('ManageCom').value);
       }
     **/  
     if(fm.PEResultCode.value == null|| fm.PEResultCode.value == '')
       {
          alert("������������Ϊ�գ�");
          return false;
       }
       if(fm.PEResultCode.value == 'P'&& fm.PEResultDes.value == '')
       {
          alert("�����������Ϊ�쳣��������ϸ��Ϣ����¼�룡");
          return false;
       }
//     if(fm.PEHospital.value == null || fm.PEHospital.value == '')
//       {
//          alert("��ѡ�����ҽԺ��");
//          return false;
//       }
		
	   if(verifyInput() == false) return false;	
	   //�������ҽԺ����Ϊ�յ�У��
	   if((fm.PEHospital.value==null || fm.PEHospital.value == '')&&(fm.PEAddress.value==null||fm.PEAddress.value=="")){
		   alert("���ҽԺ����Ϊ�գ�");
		   return false;
	   }
       //�������ҽԺ��Ϊ����ҽԺ�����¼������˵�У��
       var tCheckSql = "select 1 from ldhospital where hosstate='0' and hospitalname='"+fm.PEAddress.value+"' ";
       var arrCheck = easyExecSql(tCheckSql);
       if(!arrCheck){
    	   if(fm.AccName.value==""){
    		   alert("���ҽԺ��Ϊ����ҽԺ������¼������ˣ�");
    		   return false;
    	   }
       }
       if(fm.PEDate.value == null || fm.PEDate.value == '')
       {
          alert("��ѡ�����ʱ�䣡");
          return false;
       }
       if(fm.PastDiseaseRes.value == null || fm.PastDiseaseRes.value == '')
       {
          alert("��ѡ�������ʷ�����");
          return false;
       }
       
       if(fm.PastDiseaseCode.value == 'P'&& fm.PastDiseaseDes.value == '')
       {
          alert("������м�����ʷ��������ʷ��ϸ��Ϣ����¼�룡");
          return false;
       }
       //У�������Ŀ�Ƿ�ȫ��¼��
       var row = HealthGrid.mulLineCount;
       var ItemNum = 0;
       var flag =0;
       for (var i=0 ; i<row; i++)
       {
         if(HealthGrid.getRowColData(i,4)==null || HealthGrid.getRowColData(i,4)=="")
         {
          alert("���б�ѡ�����Ŀ����δѡ��");
          return false;
         }  
         if(HealthGrid.getRowColData(i,8)!=null && HealthGrid.getRowColData(i,8)!="" && HealthGrid.getRowColData(i,8)=="1")
         {
           ItemNum++ ;
           if(HealthGrid.getRowColData(i,5)==null || HealthGrid.getRowColData(i,5)=="")
          //  || (HealthGrid.getRowColData(i,6)==null || HealthGrid.getRowColData(i,6)=="")
           // || (HealthGrid.getRowColData(i,7)==null || HealthGrid.getRowColData(i,7)=="")
           // )
          {
             flag = 1;             
          }
         }
       }
       
       if(ItemNum < parseInt(fm.ItemNum.value))
       {
             if(!confirm("���б�ѡ�����δѡ���Ƿ�������棿"))
                return false;             
       }
       
       //У���ѡ�����Ŀ�Ƿ�¼����� ����ѡ����Ŀ����
       row = HealthOtherGrid.mulLineCount;
       for (var i=0 ; i<row; i++)
       {
         if(HealthOtherGrid.getRowColData(i,4)==null || HealthOtherGrid.getRowColData(i,4)=="")
         {
          alert("���п�ѡ�����Ŀ����δѡ��");
          return false;
         } 
        
           if(HealthOtherGrid.getRowColData(i,5)==null || HealthOtherGrid.getRowColData(i,5)=="")
          //  || (HealthOtherGrid.getRowColData(i,6)==null || HealthOtherGrid.getRowColData(i,6)=="")
           // || (HealthOtherGrid.getRowColData(i,7)==null || HealthOtherGrid.getRowColData(i,7)=="")
           // )
          {
             flag = 1;             
          }
       }       
       
       if(flag == 1)
       {
         if(!confirm("����δ¼�������Ƿ�������棿"))
                return false;
       }

       if((fm.OtherPEItem.value!=null && fm.OtherPEItem.value!="")&& (fm.OtherPEItemResCode.value!="O") && (fm.OtherPEItemRes.value==null || fm.OtherPEItemRes.value==""))
       {
         if(!confirm("�������������δ¼�룬�Ƿ�������棿"))
                return false;
       } 
      // var tActivityID = fm.ActivityID.value;    

       //alert(fm.PrtSeq.value);return false;
	var i = 0;
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
 lockScreen('lkscreen');  
  fm.action= "./UWManuHealthQSave.jsp";
  document.getElementById("fm").submit(); //�ύ
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	var sqlid914164553="DSHomeContSql914164553";
var mySql914164553=new SqlClass();
mySql914164553.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164553.setSqlId(sqlid914164553);//ָ��ʹ�õ�Sql��id
mySql914164553.addSubPara(prtSeq);//ָ������Ĳ���
var strSQL=mySql914164553.getString();
	
	
//	var strSQL = " select distinct PEItemCode,PEItemName,FREEPE,PEItemResult from LCPENoticeItem where PrtSeq = '"+prtSeq+"'"
	          // + " and contno='"+contno+"'"
	           ;	
        
        	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //alert(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	
    //alert("δ��ѯ���������������ݣ�");
     return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 30 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

      
       
}

//��ѯ�������Ӱ��
function queryHealthPic(tPrtNo,tContNo,tPrtSeq){
	//09-06-11��������µ�ͬʱɨ��������϶�ɨ������Ӱ����Ĵ���
	//�µ�ʱ��������ϡ��˹��˱�ʱҲ��������ϣ������˹��˱�ʱΪ׼
	//alert(tPrtNo+"   "+tContNo+"    "+tPrtSeq);
	
	var sqlid914164712="DSHomeContSql914164712";
var mySql914164712=new SqlClass();
mySql914164712.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164712.setSqlId(sqlid914164712);//ָ��ʹ�õ�Sql��id
mySql914164712.addSubPara(tPrtSeq);//ָ������Ĳ���
mySql914164712.addSubPara(tPrtNo);//ָ������Ĳ���
var tSubtypeSql=mySql914164712.getString();
	
//	var tSubtypeSql = "select docid from es_doc_main where doccode='"+tPrtSeq+"' and subtype='UN103'"
//					+ " union "
//					+ " select docid from es_doc_main where doccode='"+tPrtNo+"' and subtype='UN103'";
	var tSubType = "";
	var QueryType = "";
	var DocID=easyExecSql(tSubtypeSql);
	if(DocID!=null){
		tSubType = "TB1013";
		QueryType = "1";
	}else{
		tSubType = "TB1020";
		QueryType = "0";
		
		var sqlid914164840="DSHomeContSql914164840";
var mySql914164840=new SqlClass();
mySql914164840.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914164840.setSqlId(sqlid914164840);//ָ��ʹ�õ�Sql��id
mySql914164840.addSubPara(tContNo);//ָ������Ĳ���
var strSQL=mySql914164840.getString();
		
		DocID = easyExecSql(strSQL);
	}//alert(tPrtSeq);
	//��ѯ��Ӧ�Ĵ�ӡ��ˮ�ţ�������ˮ�Ŷ�Ӧ�����Ӱ��
	parent.fraPic.location="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo="+tPrtNo+"&BussNoType=11&BussType=TB&SubType=TB1013"+"&QueryType="+QueryType+"&DocID="+DocID;
	
}


//����������޸�ʱ��ԭ���������ݲ�ѯ��ʾ --modify ln 2008-11-6
function queryHealthInfo(aContNo,aPrtSeq)
{
	var tContNo=aContNo;
	var tPrtSeq=aPrtSeq;
	if(tContNo != "" && tPrtSeq != "")
	{
			//��ѯ��������Ϣ
			
		var sqlid914165202="DSHomeContSql914165202";
var mySql914165202=new SqlClass();
mySql914165202.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914165202.setSqlId(sqlid914165202);//ָ��ʹ�õ�Sql��id
mySql914165202.addSubPara(tContNo);//ָ������Ĳ���
mySql914165202.addSubPara(tContNo);//ָ������Ĳ���
mySql914165202.addSubPara(tContNo);//ָ������Ĳ���
mySql914165202.addSubPara(tContNo);//ָ������Ĳ���
mySql914165202.addSubPara(tContNo);//ָ������Ĳ���
mySql914165202.addSubPara(tContNo);//ָ������Ĳ���
mySql914165202.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql914165202.getString();

//		var strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LCPENotice.customertype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno))),"
//                         + " decode(LCPENotice.customertype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno)),"
//                         + " customerno, customertype"
//                         + " from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
			
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//���������
        	document.all('Sex').value = arrReturn[0][2];//������Ա�
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//���������
        	//document.all('CustomerNo').value = arrReturn[0][4];//���������
        	
        	var sqlid914165553="DSHomeContSql914165553";
var mySql914165553=new SqlClass();
mySql914165553.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914165553.setSqlId(sqlid914165553);//ָ��ʹ�õ�Sql��id
mySql914165553.addSubPara(tContNo);//ָ������Ĳ���
mySql914165553.addSubPara(arrReturn[0][4]);//ָ������Ĳ���
mySql914165553.addSubPara(arrReturn[0][5]);//ָ������Ĳ���
mySql914165553.addSubPara(tPrtSeq);//ָ������Ĳ���
strsql1=mySql914165553.getString();
        	
//        	strsql1="select 1 from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customertype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "'";
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);     
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
		       else 
		          document.all('RePETag').value = "N";
        } 
			//��ѯ�����Ŀ��Ϣ
			var sqlid914165708="DSHomeContSql914165708";
var mySql914165708=new SqlClass();
mySql914165708.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914165708.setSqlId(sqlid914165708);//ָ��ʹ�õ�Sql��id
mySql914165708.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql914165708.getString();

			
//			var tstrsql = "select a.peitemcode,a.peitemname,b.code,b.codename,'','','','1'"
//			          + " from LCPENoticeItem a,ldcode b where 1=1 and a.peitemcode = b.codetype"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and a.PrtSeq = '"+ tPrtSeq + "' order by a.peitemcode,b.code";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,102);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ���������Ŀ��Ϣ
			var sqlid914165800="DSHomeContSql914165800";
var mySql914165800=new SqlClass();
mySql914165800.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914165800.setSqlId(sqlid914165800);//ָ��ʹ�õ�Sql��id
mySql914165800.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql914165800.getString();
			
//			tstrsql = "select peitemname from LCPENoticeItem where 1=1 and peitemcode = 'other'"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		          document.all('OtherPEItem').value = arrReturn[0][0];
			
		/**	//��ѯ��������Ϣ
	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
	          // + " and ContNo = '"+ tContNo + "'"
					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//��ѯ���������Ϣ			 
			var strSQL = "select * from LCPENotice where 1=1"	
					+" and ContNo = '"+tContNo+"'"
					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
		**/	
		}

		
  return true;
}

/**
* ����Ͷ���˱��������䡶Ͷ������������֮��=Ͷ���˱��������䡷,2005-11-18�����
* ��������������yy��mm��dd��Ͷ������yy��mm��dd
* ����  ����
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("����"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("Ͷ������"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//��ǰ�´��ڳ�����
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//��ǰ��С�ڳ�����
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}

//����������޸�ʱ��ԭ���������ݲ�ѯ��ʾ
function queryHealthInfo1(aContNo,aPrtSeq)
{
	var tContNo=aContNo;
	var tPrtSeq=aPrtSeq;
	
	//��ѯ��������Ϣ
	var sqlid914165849="DSHomeContSql914165849";
var mySql914165849=new SqlClass();
mySql914165849.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914165849.setSqlId(sqlid914165849);//ָ��ʹ�õ�Sql��id
mySql914165849.addSubPara(tPrtSeq);//ָ������Ĳ���
var sSql=mySql914165849.getString();
	
//  var sSql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//        // + " and ContNo = '"+ tContNo + "'"
//			 + " and PrtSeq = '"+ tPrtSeq + "'";	 
  var arrSReturn = new Array();
	arrSReturn = easyExecSql(sSql);
	if (arrSReturn == null) 
	{} 
	else
	{
		if(tContNo != "" && tPrtSeq != "")
		{
			//��ѯ��������Ϣ
			var sqlid914170000="DSHomeContSql914170000";
var mySql914170000=new SqlClass();
mySql914170000.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914170000.setSqlId(sqlid914170000);//ָ��ʹ�õ�Sql��id
mySql914170000.addSubPara(tContNo);//ָ������Ĳ���
mySql914170000.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql914170000.getString();
			
//			var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag from LCPENotice where 1=1"	 
//			         + " and ContNo = '"+ tContNo + "'"
//			         + " and PrtSeq = '"+ tPrtSeq + "'";
			
			var arrReturn = new Array();
			arrReturn = easyExecSql(strsql1);     
			if(arrReturn!=null)
			{
			
				document.all('PEAddress').value = arrReturn[0][0];
				document.all('PEDoctor').value = arrReturn[0][1];
				document.all('PEDate').value = arrReturn[0][2];
			 	document.all('REPEDate').value = arrReturn[0][3];
			 	document.all('Operator').value = arrReturn[0][4];
				document.all('MakeDate').value = arrReturn[0][5];
				document.all('ReplyDate').value = arrReturn[0][6];
				document.all('MasculineFlag').value = arrReturn[0][7];
			}
			//��ѯ�����Ŀ��Ϣ
			var sqlid914170054="DSHomeContSql914170054";
var mySql914170054=new SqlClass();
mySql914170054.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914170054.setSqlId(sqlid914170054);//ָ��ʹ�õ�Sql��id
mySql914170054.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql914170054.getString();
			
//			var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			turnPage.queryModal(tstrsql, HealthGrid);	
			
			//��ѯ��������Ϣ
			var sqlid914170134="DSHomeContSql914170134";
var mySql914170134=new SqlClass();
mySql914170134.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914170134.setSqlId(sqlid914170134);//ָ��ʹ�õ�Sql��id
mySql914170134.addSubPara(tPrtSeq);//ָ������Ĳ���
var Sql=mySql914170134.getString();
			
//	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//	          // + " and ContNo = '"+ tContNo + "'"
//					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//��ѯ���������Ϣ
				var sqlid914170227="DSHomeContSql914170227";
var mySql914170227=new SqlClass();
mySql914170227.setResourceName("uw.UWManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql914170227.setSqlId(sqlid914170227);//ָ��ʹ�õ�Sql��id
mySql914170227.addSubPara(tContNo);//ָ������Ĳ���
mySql914170227.addSubPara(tPrtSeq);//ָ������Ĳ���
var strSQL=mySql914170227.getString();
							 
//			var strSQL = "select * from LCPENotice where 1=1"	
//					+" and ContNo = '"+tContNo+"'"
//					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
			
		}
		
	}
		
  return true;
}
