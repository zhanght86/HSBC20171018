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
  	//parent.close();
  	top.close();
  	top.opener.easyQueryClick();
  	
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
	   var sqlid916152332="DSHomeContSql916152332";
var mySql916152332=new SqlClass();
mySql916152332.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916152332.setSqlId(sqlid916152332);//ָ��ʹ�õ�Sql��id
mySql916152332.addSubPara(tContNo);//ָ������Ĳ���
mySql916152332.addSubPara(tInsuredNo);//ָ������Ĳ���
strsql=mySql916152332.getString();
	   
//	   strsql = "select LPPENotice.ContNo,LPPENotice.PrtSeq,LPPENotice.CustomerNo,LPPENotice.Name,LPPENotice.PEDate,LPPENotice.MakeDate,LOPRTManager.StateFlag from LPPENotice,LOPRTManager where 1=1"
//				    + " and LPPENotice.PrtSeq = LOPRTManager.PrtSeq"
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
	tEdorNo = fm.EdorNo.value;
	var tSelNo = MainHealthGrid.getSelNo()-1;
  var tPrtSeq = MainHealthGrid.getRowColData(tSelNo,8);
  var tNewPrtSeq = MainHealthGrid.getRowColData(tSelNo,1);
  document.all('PEName').value=MainHealthGrid.getRowColData(tSelNo,4);
	//��ѯ���֪ͨ��Ӱ��
	queryHealthPic(tNewPrtSeq);

  if(tContNo != "" && tPrtSeq != "")
  { 
			//��ѯ��������Ϣ
		var sqlid916152617="DSHomeContSql916152617";
var mySql916152617=new SqlClass();
mySql916152617.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916152617.setSqlId(sqlid916152617);//ָ��ʹ�õ�Sql��id
mySql916152617.addSubPara(tContNo);//ָ������Ĳ���
mySql916152617.addSubPara(tContNo);//ָ������Ĳ���
mySql916152617.addSubPara(tContNo);//ָ������Ĳ���
mySql916152617.addSubPara(tContNo);//ָ������Ĳ���
mySql916152617.addSubPara(tContNo);//ָ������Ĳ���
mySql916152617.addSubPara(tContNo);//ָ������Ĳ���
mySql916152617.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql916152617.getString();	
		
//		var strsql1="select (select prtno from lpcont where contno='"+ tContNo +"' and edorno=a.edorno ),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(a.customernotype,'A',(select appntsex from lpappnt where contno='"+ tContNo + "' and edorno=a.edorno and appntno=a.customerno),(select sex from lpinsured where contno='"+ tContNo + "' and edorno=a.edorno and insuredno=a.customerno))),"
//                         + " decode(a.customernotype,'A',(select appntbirthday from lpappnt where contno='"+ tContNo + "' and edorno=a.edorno and appntno=a.customerno),(select birthday from lpinsured where contno='"+ tContNo + "' and edorno=a.edorno and insuredno=a.customerno)),"
//                         + " customerno, customernotype,PEAddress ,pedate, '', operator,makedate,modifydate,PEResult "
//                         + " from LPPENotice a where 1=1"	 
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
        	
        	var sqlid916160023="DSHomeContSql916160023";
					var mySql916160023=new SqlClass();
					mySql916160023.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
					mySql916160023.setSqlId(sqlid916160023);//ָ��ʹ�õ�Sql��id
					mySql916160023.addSubPara(tContNo);//ָ������Ĳ���
					mySql916160023.addSubPara(tEdorNo);//ָ������Ĳ���
					mySql916160023.addSubPara(arrReturn[0][4]);//ָ������Ĳ���
					mySql916160023.addSubPara(arrReturn[0][5]);//ָ������Ĳ���
					mySql916160023.addSubPara(tPrtSeq);//ָ������Ĳ���
					mySql916160023.addSubPara(tday);//ָ������Ĳ���
					mySql916160023.addSubPara(tday);//ָ������Ĳ���
					mySql916160023.addSubPara(time);//ָ������Ĳ���
					strsql1=mySql916160023.getString();
        	
//        	strsql1="select 1 from LPPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and edorno='"+tEdorNo+"' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customernotype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "' and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"'))";
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);   //prompt("",strsql1);  
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
		       else 
		          document.all('RePETag').value = "N";
        } 
			//��ѯ��ѡ�����Ŀ��Ϣ
			var sqlid916160602="DSHomeContSql916160602";
			var mySql916160602=new SqlClass();
			mySql916160602.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
			mySql916160602.setSqlId(sqlid916160602);//ָ��ʹ�õ�Sql��id
			mySql916160602.addSubPara(tContNo);//ָ������Ĳ���
			mySql916160602.addSubPara(tPrtSeq);//ָ������Ĳ���
			var tstrsql=mySql916160602.getString();
			
//			var tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor,'1'"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='0' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,102);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ��ѡ�����Ŀ��Ϣ
			var sqlid916160902="DSHomeContSql916160902";
			var mySql916160902=new SqlClass();
			mySql916160902.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
			mySql916160902.setSqlId(sqlid916160902);//ָ��ʹ�õ�Sql��id
			mySql916160902.addSubPara(tContNo);//ָ������Ĳ���
			mySql916160902.addSubPara(tPrtSeq);//ָ������Ĳ���
			tstrsql=mySql916160902.getString();
			
//			tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='1' order by peitemcode,peitemdivcode";
			turnPage.queryModal(tstrsql, HealthOtherGrid,0,0,102);
			//��ѯ���������Ŀ��Ϣ
			var sqlid916161004="DSHomeContSql916161004";
			var mySql916161004=new SqlClass();
			mySql916161004.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
			mySql916161004.setSqlId(sqlid916161004);//ָ��ʹ�õ�Sql��id
			mySql916161004.addSubPara(tContNo);//ָ������Ĳ���
			mySql916161004.addSubPara(tPrtSeq);//ָ������Ĳ���
			tstrsql=mySql916161004.getString();
			
//				tstrsql = "select peitemdivname,peitemdivres,peitemdivdes"
//				          + " from LCPENoticeReply  where 1=1 "
//						  + " and ContNo = '"+ tContNo + "'"
//						  + " and PrtSeq = '"+ tPrtSeq + "' and peitemcode='other'";
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		        {
		           document.all('OtherPEItem').value = arrReturn[0][0];
		           fm.OtherPEItemRes.value = arrReturn[0][1];
		           fm.OtherPEItemDes.value = arrReturn[0][2];
		        }  
		          
		     //��ѯ������ʷ��Ϣ
		  var sqlid916161108="DSHomeContSql916161108";
var mySql916161108=new SqlClass();
mySql916161108.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916161108.setSqlId(sqlid916161108);//ָ��ʹ�õ�Sql��id
mySql916161108.addSubPara(tContNo);//ָ������Ĳ���
mySql916161108.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql916161108.getString();   
		  
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
	var tEdorNo = fm.EdorNo.value;
	
		//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
// var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
//          + " and ContNo = '"+tContNo+"'";
//alert(399);

	var sqlid916161238="DSHomeContSql916161238";
var mySql916161238=new SqlClass();
mySql916161238.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916161238.setSqlId(sqlid916161238);//ָ��ʹ�õ�Sql��id
mySql916161238.addSubPara(tEdorNo);//ָ������Ĳ���
mySql916161238.addSubPara(tContNo);//ָ������Ĳ���
var strSql=mySql916161238.getString();
	
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
//             + " from lppenotice a, loprtmanager b"
//             + " where 1 = 1"
//             + " and a.EdorNo = '"+tEdorNo+"'"
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
   var sqlid916161400="DSHomeContSql916161400";
var mySql916161400=new SqlClass();
mySql916161400.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916161400.setSqlId(sqlid916161400);//ָ��ʹ�õ�Sql��id
mySql916161400.addSubPara(tCustomerNo);//ָ������Ĳ���
var strSql=mySql916161400.getString();
   
   
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
//             + " from lppenotice a, loprtmanager b"
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
	
	var sqlid916161509="DSHomeContSql916161509";
var mySql916161509=new SqlClass();
mySql916161509.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916161509.setSqlId(sqlid916161509);//ָ��ʹ�õ�Sql��id
mySql916161509.addSubPara(tContNo);//ָ������Ĳ���
var strSql=mySql916161509.getString();
	
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
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
 
	fm.action= "./BQManuHealthQSave.jsp";
  fm.submit(); //�ύ
}

function init()
{
	
	var prtSeq =fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	var sqlid916161922="DSHomeContSql916161922";
var mySql916161922=new SqlClass();
mySql916161922.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916161922.setSqlId(sqlid916161922);//ָ��ʹ�õ�Sql��id
mySql916161922.addSubPara(prtSeq);//ָ������Ĳ���
var strSQL=mySql916161922.getString();
	
//	var strSQL = " select distinct PEItemCode,PEItemName,FREEPE,PEItemResult from LpPENoticeItem where PrtSeq = '"+prtSeq+"'"
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
        //                 + " (select codename from ldcode where codetype='sex' and code=decode(LPPENotice.customernotype,'A',(select appntsex from lpappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select sex from lpinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno))),"
        //                 + " decode(LPPENotice.customernotype,'A',(select appntbirthday from lpappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select birthday from lpinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno)),"
        //                 + " customerno, customernotype"
        //                 + " from LPPENotice where 1=1"	 
		//		         + " and ContNo = '"+ tContNo + "'"
		//		         + " and PrtSeq = '"+ tPrtSeq + "'";
		//	turnPage.strQueryResult = easyQueryVer3(strsql1, 1, 1, 1);
		//if(!turnPage.strQueryResult){
		var sqlid916164456="DSHomeContSql916164456";
var mySql916164456=new SqlClass();
mySql916164456.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916164456.setSqlId(sqlid916164456);//ָ��ʹ�õ�Sql��id
mySql916164456.addSubPara(tContNo);//ָ������Ĳ���
mySql916164456.addSubPara(tContNo);//ָ������Ĳ���
mySql916164456.addSubPara(tContNo);//ָ������Ĳ���
mySql916164456.addSubPara(tContNo);//ָ������Ĳ���
mySql916164456.addSubPara(tContNo);//ָ������Ĳ���
mySql916164456.addSubPara(tContNo);//ָ������Ĳ���
mySql916164456.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql916164456.getString();
		
//		var	strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=decode(LPPENotice.customernotype,'A',(select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno))),"
//                         + " decode(LPPENotice.customernotype,'A',(select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LPPENotice.customerno),(select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LPPENotice.customerno)),"
//                         + " customerno, customernotype"
//                         + " from LPPENotice where 1=1"	 
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
        	
        	var sqlid916165400="DSHomeContSql916165400";
var mySql916165400=new SqlClass();
mySql916165400.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916165400.setSqlId(sqlid916165400);//ָ��ʹ�õ�Sql��id
mySql916165400.addSubPara(tContNo);//ָ������Ĳ���
mySql916165400.addSubPara(arrReturn[0][4]);//ָ������Ĳ���
mySql916165400.addSubPara(arrReturn[0][5]);//ָ������Ĳ���
mySql916165400.addSubPara(tPrtSeq);//ָ������Ĳ���
strsql1=mySql916165400.getString();
        	
//        	strsql1="select 1 from LPPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customernotype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "'";
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);     
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
		       else 
		          document.all('RePETag').value = "N";
        } 
			//��ѯ�����Ŀ��Ϣ
			var sqlid916165515="DSHomeContSql916165515";
var mySql916165515=new SqlClass();
mySql916165515.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916165515.setSqlId(sqlid916165515);//ָ��ʹ�õ�Sql��id
mySql916165515.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql916165515.getString();
			
//			var tstrsql = "select a.peitemcode,a.peitemname,b.code,b.codename,'','','','1'"
//			          + " from LPPENoticeItem a,ldcode b where 1=1 and a.peitemcode = b.codetype"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and a.PrtSeq = '"+ tPrtSeq + "' order by a.peitemcode,b.code";
			turnPage.queryModal(tstrsql, HealthGrid,0,0,102);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ���������Ŀ��Ϣ
			var sqlid916165612="DSHomeContSql916165612";
var mySql916165612=new SqlClass();
mySql916165612.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916165612.setSqlId(sqlid916165612);//ָ��ʹ�õ�Sql��id
mySql916165612.addSubPara(tPrtSeq);//ָ������Ĳ���
tstrsql=mySql916165612.getString();
			
//			tstrsql = "select peitemname from LPPENoticeItem where 1=1 and peitemcode = 'other'"
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
	var sqlid916170014="DSHomeContSql916170014";
var mySql916170014=new SqlClass();
mySql916170014.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916170014.setSqlId(sqlid916170014);//ָ��ʹ�õ�Sql��id
mySql916170014.addSubPara(tPrtSeq);//ָ������Ĳ���
var sSql=mySql916170014.getString();
	
//  var sSql = "select DisDesb,DisResult,ICDCode from LPPENoticeResult where 1=1"
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
			var sqlid916170350="DSHomeContSql916170350";
var mySql916170350=new SqlClass();
mySql916170350.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916170350.setSqlId(sqlid916170350);//ָ��ʹ�õ�Sql��id
mySql916170350.addSubPara(tContNo);//ָ������Ĳ���
mySql916170350.addSubPara(tPrtSeq);//ָ������Ĳ���
var strsql1=mySql916170350.getString();

			
//			var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag from LPPENotice where 1=1"	 
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
			var sqlid916170747="DSHomeContSql916170747";
var mySql916170747=new SqlClass();
mySql916170747.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916170747.setSqlId(sqlid916170747);//ָ��ʹ�õ�Sql��id
mySql916170747.addSubPara(tPrtSeq);//ָ������Ĳ���
var tstrsql=mySql916170747.getString();
			
//			var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LPPENoticeItem where 1=1"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			turnPage.queryModal(tstrsql, HealthGrid);	
			
			//��ѯ��������Ϣ
			var sqlid916170835="DSHomeContSql916170835";
var mySql916170835=new SqlClass();
mySql916170835.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916170835.setSqlId(sqlid916170835);//ָ��ʹ�õ�Sql��id
mySql916170835.addSubPara(tPrtSeq);//ָ������Ĳ���
var Sql=mySql916170835.getString();

			
//	    var Sql = "select DisDesb,DisResult,ICDCode from LPPENoticeResult where 1=1"
//	          // + " and ContNo = '"+ tContNo + "'"
//					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//��ѯ���������Ϣ
			var sqlid916170926="DSHomeContSql916170926";
var mySql916170926=new SqlClass();
mySql916170926.setResourceName("uw.BQManuHealthQSql");//ָ��ʹ�õ�properties�ļ���
mySql916170926.setSqlId(sqlid916170926);//ָ��ʹ�õ�Sql��id
mySql916170926.addSubPara(tContNo);//ָ������Ĳ���
mySql916170926.addSubPara(tPrtSeq);//ָ������Ĳ���
var strSQL=mySql916170926.getString();	
				
						 
//			var strSQL = "select * from LPPENotice where 1=1"	
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
