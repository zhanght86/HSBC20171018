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

  fm.action= "./BQManuHealthChk.jsp";
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
   
    showInfo.close();
    alert(content);
   
  }
  else
  { 
	  var showStr="�����ɹ���";
  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
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
	fm.submit();
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
	   var sqlid916173255="DSHomeContSql916173255";
var mySql916173255=new SqlClass();
mySql916173255.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916173255.setSqlId(sqlid916173255);//ָ��ʹ�õ�Sql��id
mySql916173255.addSubPara(tContNo);//ָ������Ĳ���
mySql916173255.addSubPara(tInsuredNo);//ָ������Ĳ���
strsql=mySql916173255.getString();
	   
//	   strsql = "select LLUWPENotice.ContNo,LLUWPENotice.PrtSeq,LLUWPENotice.CustomerNo,LLUWPENotice.Name,LLUWPENotice.PEDate,LLUWPENotice.MakeDate,LOPRTManager.StateFlag from LLUWPENotice,LOPRTManager where 1=1"
//				    + " and LLUWPENotice.PrtSeq = LOPRTManager.PrtSeq"
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
  var tNewPrtSeq = MainHealthGrid.getRowColData(tSelNo,1);
  document.all('PEName').value=MainHealthGrid.getRowColData(tSelNo,4);
	//��ѯ���֪ͨ��Ӱ��
	queryHealthPic(tNewPrtSeq);

  if(tContNo != "" && tPrtSeq != "")
  { 
			//��ѯ��������Ϣ
		var sqlid916173533="DSHomeContSql916173533";
var mySql916173533=new SqlClass();
mySql916173533.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916173533.setSqlId(sqlid916173533);//ָ��ʹ�õ�Sql��id
mySql916173533.addSubPara(tContNo);//ָ������Ĳ���
mySql916173533.addSubPara(tContNo);//ָ������Ĳ���
mySql916173533.addSubPara(tContNo);//ָ������Ĳ���
mySql916173533.addSubPara(tContNo);//ָ������Ĳ���
mySql916173533.addSubPara(tContNo);//ָ������Ĳ���
mySql916173533.addSubPara(tContNo);//ָ������Ĳ���
mySql916173533.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql916173533.getString();
		
//		var strsql1="select (select prtno from lpcont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LLUWPENotice.customertype,'A',(select appntsex from lpappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select sex from lpinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno))),"
//                         + " decode(LLUWPENotice.customertype,'A',(select appntbirthday from lpappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select birthday from lpinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno)),"
//                         + " customerno, customertype,PEAddress ,pedate, '', operator,makedate,modifydate,PEResult "
//                         + " from LLUWPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
			
       var arrReturn = new Array();//prompt("",strsql1);
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//���������
        	document.all('Sex').value = arrReturn[0][2];//������Ա�
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//���������
        	//document.all('CustomerNo').value = arrReturn[0][4];//���������
        	document.all('PEAddress').value = arrReturn[0][6];
        	document.all('PEDate').value = arrReturn[0][7]; 
        	document.all('AccName').value = arrReturn[0][8];      	        	      	 	
       	 	document.all('Operator').value = arrReturn[0][9];
        	document.all('MakeDate').value = arrReturn[0][10];
        	document.all('ReplyDate').value = arrReturn[0][11];
        	//alert(arrReturn[0][12]);
        	var PEResult = arrReturn[0][12];
        	var t1 = PEResult.indexOf(":");
        	var t2 = PEResult.lastIndexOf(":");

        	document.all('PEResult').value = PEResult.substring(0,t1);
        	document.all('PEResultDes').value = PEResult.substring(t1+1,t2);
        	document.all('Note').value = PEResult.substring(t2+1);
        	
        	var sqlid917094943="DSHomeContSql917094943";
var mySql917094943=new SqlClass();
mySql917094943.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917094943.setSqlId(sqlid917094943);//ָ��ʹ�õ�Sql��id
mySql917094943.addSubPara(tContNo);//ָ������Ĳ���
mySql917094943.addSubPara(arrReturn[0][4]);//ָ������Ĳ���
mySql917094943.addSubPara(arrReturn[0][5]);//ָ������Ĳ���
mySql917094943.addSubPara(tPrtSeq);//ָ������Ĳ���
mySql917094943.addSubPara(tday);//ָ������Ĳ���
mySql917094943.addSubPara(tday);//ָ������Ĳ���
mySql917094943.addSubPara(time);//ָ������Ĳ���
strsql1=mySql917094943.getString();
        	
//        	strsql1="select 1 from LLUWPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customertype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "' and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"'))";
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);   //prompt("",strsql1);  
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
		       else 
		          document.all('RePETag').value = "N";
        } 
			//��ѯ��ѡ�����Ŀ��Ϣ
			var sqlid917095259="DSHomeContSql917095259";
var mySql917095259=new SqlClass();
mySql917095259.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917095259.setSqlId(sqlid917095259);//ָ��ʹ�õ�Sql��id
mySql917095259.addSubPara(tContNo);//ָ������Ĳ���
mySql917095259.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql917095259.getString();

			
			
//			var tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor,'1'"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='0' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,30);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ��ѡ�����Ŀ��Ϣ
			var sqlid917095345="DSHomeContSql917095345";
var mySql917095345=new SqlClass();
mySql917095345.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917095345.setSqlId(sqlid917095345);//ָ��ʹ�õ�Sql��id
mySql917095345.addSubPara(tContNo);//ָ������Ĳ���
mySql917095345.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql917095345.getString();

			
//			tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='1' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthOtherGrid,0,0,30);
			//��ѯ���������Ŀ��Ϣ
			var sqlid917095430="DSHomeContSql917095430";
var mySql917095430=new SqlClass();
mySql917095430.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917095430.setSqlId(sqlid917095430);//ָ��ʹ�õ�Sql��id
mySql917095430.addSubPara(tContNo);//ָ������Ĳ���
mySql917095430.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql917095430.getString();
			
			
//			tstrsql = "select peitemdivname,peitemdivres,peitemdivdes"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and peitemcode='other'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		        {
		           document.all('OtherPEItem').value = arrReturn[0][0];
		          fm.OtherPEItemRes.value = arrReturn[0][1];
		          fm.OtherPEItemDes.value = arrReturn[0][2];
		        }  
		          
		     //��ѯ������ʷ��Ϣ
		  var sqlid917095511="DSHomeContSql917095511";
var mySql917095511=new SqlClass();
mySql917095511.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917095511.setSqlId(sqlid917095511);//ָ��ʹ�õ�Sql��id
mySql917095511.addSubPara(tContNo);//ָ������Ĳ���
mySql917095511.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql917095511.getString();
		     
		     
//			tstrsql = "select DisResult, DisDesb from LCPENoticeResult where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		       {
		         document.all('PastDiseaseRes').value = arrReturn[0][0];
		         document.all('PastDiseaseDes').value = arrReturn[0][1];
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
	fm.submit();
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
//alert(399);
  var sqlid917100009="DSHomeContSql917100009";
var mySql917100009=new SqlClass();
mySql917100009.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917100009.setSqlId(sqlid917100009);//ָ��ʹ�õ�Sql��id
mySql917100009.addSubPara(tContNo);//ָ������Ĳ���
var strSql=mySql917100009.getString();
  
//  var strSql = " select a1, a2, a3, a4, a5, a6, a7, a9"
//             + " from (select a.ContNo as a1,"
//             + " b.PrtSeq as a2,"
//             + " a.CustomerNo as a3,"
//             + " a.Name as a4,"
//             + " a.Pedate as a5,"
//             + " a.MakeDate as a6,"
//             + " a.PrintFlag as a7,"
//             + " row_number() over(partition by b.oldprtseq order by b.prtseq) as a8,"
//             + " b.oldprtseq a9"
//             + " from lluwpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             + " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";
//prompt("",strSql);
 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("δ��ѯ�������Ϣ��");
    return "";
  }
  //alert(427);
  
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
   var sqlid917100158="DSHomeContSql917100158";
var mySql917100158=new SqlClass();
mySql917100158.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917100158.setSqlId(sqlid917100158);//ָ��ʹ�õ�Sql��id
mySql917100158.addSubPara(tCustomerNo);//ָ������Ĳ���
var strSql=mySql917100158.getString();
   
   
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
//             + " from lluwpenotice a, loprtmanager b"
//             + " where 1 = 1"
//             //+ " and a.ContNo = '"+tContNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.customerno='"+tCustomerNo+"'"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";

 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
 //alert(487);
  if (!turnPage.strQueryResult)
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }
  //alert(493);
  
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
	

	var sqlid917100251="DSHomeContSql917100251";
var mySql917100251=new SqlClass();
mySql917100251.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917100251.setSqlId(sqlid917100251);//ָ��ʹ�õ�Sql��id
mySql917100251.addSubPara(tContNo);//ָ������Ĳ���
var strSql=mySql917100251.getString();
	
//	var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lpcont where ContNo = '"+tContNo+"')";
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
     if(fm.PEHospital.value == null || fm.PEHospital.value == '')
       {
          alert("��ѡ�����ҽԺ��");
          return false;
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
       
       if(fm.PEHospital.value == null || fm.PEHospital.value == '')
       {
          alert("��ѡ�����ҽԺ��");
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
	
 
  fm.action= "./LLManuHealthQSave.jsp";
  fm.submit(); //�ύ
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	
	var sqlid917100350="DSHomeContSql917100350";
var mySql917100350=new SqlClass();
mySql917100350.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917100350.setSqlId(sqlid917100350);//ָ��ʹ�õ�Sql��id
mySql917100350.addSubPara(prtSeq);//ָ������Ĳ���
var strSQL=mySql917100350.getString();
	
//	var strSQL = " select distinct PEItemCode,PEItemName,FREEPE,PEItemResult from LLUWPENoticeItem where PrtSeq = '"+prtSeq+"'"
//	          // + " and contno='"+contno+"'"
//	           ;	
        
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
function queryHealthPic(tPrtSeq){
	//��ѯ��Ӧ�Ĵ�ӡ��ˮ�ţ�������ˮ�Ŷ�Ӧ�����Ӱ��
	parent.fraPic.location="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo="+tPrtSeq+"&BussNoType=11&BussType=TB&SubType=TB1015";
	
}


//����������޸�ʱ��ԭ���������ݲ�ѯ��ʾ --modify ln 2008-11-6
function queryHealthInfo(aContNo,aPrtSeq)
{
	var tContNo=aContNo;
	var tPrtSeq=aPrtSeq;
	if(tContNo != "" && tPrtSeq != "")
	{
			//��ѯ��������Ϣ
		//var strsql1="select (select prtno from lpcont where contno='"+ tContNo +"'),name,"
        //                 + " (select codename from ldcode where codetype='sex' and code=decode(LPPENotice.customertype,'A',(select appntsex from lpappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select sex from lpinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno))),"
        //                 + " decode(LPPENotice.customertype,'A',(select appntbirthday from lpappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select birthday from lpinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno)),"
        //                 + " customerno, customertype"
        //                 + " from LPPENotice where 1=1"	 
		//		         + " and ContNo = '"+ tContNo + "'"
		//		         + " and PrtSeq = '"+ tPrtSeq + "'";
		//	turnPage.strQueryResult = easyQueryVer3(strsql1, 1, 1, 1);
		//if(!turnPage.strQueryResult){
		var sqlid917100655="DSHomeContSql917100655";
var mySql917100655=new SqlClass();
mySql917100655.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917100655.setSqlId(sqlid917100655);//ָ��ʹ�õ�Sql��id
mySql917100655.addSubPara(tContNo);//ָ������Ĳ���
mySql917100655.addSubPara(tContNo);//ָ������Ĳ���
mySql917100655.addSubPara(tContNo);//ָ������Ĳ���
mySql917100655.addSubPara(tContNo);//ָ������Ĳ���
mySql917100655.addSubPara(tContNo);//ָ������Ĳ���
mySql917100655.addSubPara(tContNo);//ָ������Ĳ���
mySql917100655.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql917100655.getString();
		
//		var	strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LLUWPENotice.customertype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno))),"
//                         + " decode(LLUWPENotice.customertype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LLUWPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LLUWPENotice.customerno)),"
//                         + " customerno, customertype"
//                         + " from LLUWPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
		//}
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     //prompt("",strsql1);
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//���������
        	document.all('Sex').value = arrReturn[0][2];//������Ա�
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//���������
        	//document.all('CustomerNo').value = arrReturn[0][4];//���������
        	
        	var sqlid917101000="DSHomeContSql917101000";
var mySql917101000=new SqlClass();
mySql917101000.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917101000.setSqlId(sqlid917101000);//ָ��ʹ�õ�Sql��id
mySql917101000.addSubPara(tContNo);//ָ������Ĳ���
mySql917101000.addSubPara(arrReturn[0][4]);//ָ������Ĳ���
mySql917101000.addSubPara(arrReturn[0][5]);//ָ������Ĳ���
mySql917101000.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql917101000.getString();
        	
//        	strsql1="select 1 from LLUWPENotice where 1=1"	 
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
			var sqlid917101118="DSHomeContSql917101118";
var mySql917101118=new SqlClass();
mySql917101118.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917101118.setSqlId(sqlid917101118);//ָ��ʹ�õ�Sql��id
mySql917101118.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql917101118.getString();
			
			
//			var tstrsql = "select a.peitemcode,a.peitemname,b.code,b.codename,'','','','1'"
//			          + " from LLUWPENoticeItem a,ldcode b where 1=1 and a.peitemcode = b.codetype"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and a.PrtSeq = '"+ tPrtSeq + "' order by a.peitemcode,b.code";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,30);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ���������Ŀ��Ϣ
			var sqlid917101259="DSHomeContSql917101259";
var mySql917101259=new SqlClass();
mySql917101259.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917101259.setSqlId(sqlid917101259);//ָ��ʹ�õ�Sql��id
mySql917101259.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql917101259.getString();
			
//			tstrsql = "select peitemname from LLUWPENoticeItem where 1=1 and peitemcode = 'other'"
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
	var sqlid917104424="DSHomeContSql917104424";
var mySql917104424=new SqlClass();
mySql917104424.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917104424.setSqlId(sqlid917104424);//ָ��ʹ�õ�Sql��id
mySql917104424.addSubPara(tPrtSeq);//ָ������Ĳ���
var sSql=mySql917104424.getString();
	
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
			var sqlid917104520="DSHomeContSql917104520";
var mySql917104520=new SqlClass();
mySql917104520.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917104520.setSqlId(sqlid917104520);//ָ��ʹ�õ�Sql��id
mySql917104520.addSubPara(tContNo);//ָ������Ĳ���
mySql917104520.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql917104520.getString();
			
//			var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag from LLUWPENotice where 1=1"	 
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
			var sqlid917104605="DSHomeContSql917104605";
var mySql917104605=new SqlClass();
mySql917104605.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917104605.setSqlId(sqlid917104605);//ָ��ʹ�õ�Sql��id
mySql917104605.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql917104605.getString();
			
//			var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			turnPage.queryModal(tstrsql, HealthGrid);	
			
			//��ѯ��������Ϣ
			var sqlid917104650="DSHomeContSql917104650";
var mySql917104650=new SqlClass();
mySql917104650.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917104650.setSqlId(sqlid917104650);//ָ��ʹ�õ�Sql��id
mySql917104650.addSubPara(tPrtSeq);//ָ������Ĳ���
var Sql=mySql917104650.getString();
			
//	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//	          // + " and ContNo = '"+ tContNo + "'"
//					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//��ѯ���������Ϣ
			var sqlid917104735="DSHomeContSql917104735";
var mySql917104735=new SqlClass();
mySql917104735.setResourceName("uw.LLManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql917104735.setSqlId(sqlid917104735);//ָ��ʹ�õ�Sql��id
mySql917104735.addSubPara(tContNo);//ָ������Ĳ���
mySql917104735.addSubPara(tPrtSeq);//ָ������Ĳ���
var strSQL=mySql917104735.getString();
						 
//			var strSQL = "select * from LLUWPENotice where 1=1"	
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
