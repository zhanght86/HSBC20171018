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
	  var mySql=new SqlClass();
	 
  if(tContNo != "" && tInsuredNo != "")
  {
//	   strsql = "select LCPENotice.ContNo,LCPENotice.PrtSeq,LCPENotice.CustomerNo,LCPENotice.Name,LCPENotice.PEDate,LCPENotice.MakeDate,LOPRTManager.StateFlag from LCPENotice,LOPRTManager where 1=1"
//				    + " and LCPENotice.PrtSeq = LOPRTManager.PrtSeq"
//				    + " and ContNo = '"+ tContNo + "'"
//				    + " and Customerno = '"+ tInsuredNo + "'";
	   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
		  mySql.setSqlId("LLUWManuHealthQSql1");//ָ��ʹ�õ�Sql��id
			 mySql.addSubPara(tContNo);
			 mySql.addSubPara(tInsuredNo);
		  strsql= mySql.getString();		 				 
	
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
	var tRptNo = fm.RptNo.value;
	var tBatNo = fm.BatNo.value;
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
//		var strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=(case lluwpenotice.customertype when 'A' then (select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=lluwpenotice.customerno) else (select sex from lcinsured where contno='"+ tContNo + "' and insuredno=lluwpenotice.customerno) end)),"
//                         + " (case lluwpenotice.customertype when 'A' then (select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=lluwpenotice.customerno) else (select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=lluwpenotice.customerno) end),"
//                         + " customerno, customertype,PEAddress ,REPEDate, PEDoctor, operator,makedate,modifydate,PEResult "
//                         + " from lluwpenotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'"
//				         + " and clmno = '"+tRptNo+"'";
		var mySql=new SqlClass();
		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
			  mySql.setSqlId("LLUWManuHealthQSql2");//ָ��ʹ�õ�Sql��id
				 mySql.addSubPara(tContNo);
				 mySql.addSubPara(tPrtSeq);
				 mySql.addSubPara(tRptNo);
				 var strsql1= mySql.getString();	
		
//			prompt("",strsql1);
       var arrReturn = new Array();
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
        	
//        	strsql1="select 1 from lluwpenotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customertype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "' and (makedate<'"+tday+"' or (makedate='"+tday+"' and maketime<'"+time+"'))";
        	arrReturn = new Array();
        	
        	 mySql=new SqlClass();
 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
 			  mySql.setSqlId("LLUWManuHealthQSql3");//ָ��ʹ�õ�Sql��id
 				 mySql.addSubPara(tContNo);
 				 mySql.addSubPara(arrReturn[0][4]);
 				 mySql.addSubPara(arrReturn[0][5]);
 				mySql.addSubPara(tPrtSeq);
 				mySql.addSubPara(tday);
 				mySql.addSubPara(tday);
 				mySql.addSubPara(time);
 				  strsql1= mySql.getString();	
        	
		       arrReturn = easyExecSql(strsql1);     
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
		       else 
		          document.all('RePETag').value = "N";
        } 
			//��ѯ��ѡ�����Ŀ��Ϣ
//			var tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor,'1'"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='0' order by peitemcode,peitemdivcode";
			
	      	  mySql=new SqlClass();
	 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	 			  mySql.setSqlId("LLUWManuHealthQSql4");//ָ��ʹ�õ�Sql��id
	 				 mySql.addSubPara(tContNo);
	 				mySql.addSubPara(tPrtSeq);
	 				var tstrsql= mySql.getString();	
			
			turnPage.queryModal(tstrsql, HealthGrid,0,0,30);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ��ѡ�����Ŀ��Ϣ
//			tstrsql = "select peitemcode,peitemname,peitemdivcode,peitemdivname,peitemdivres,peitemdivdes,peitemdivnor"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and freepe='1' order by peitemcode,peitemdivcode";
	      	  mySql=new SqlClass();
	 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	 			  mySql.setSqlId("LLUWManuHealthQSql5");//ָ��ʹ�õ�Sql��id
	 				 mySql.addSubPara(tContNo);
	 				mySql.addSubPara(tPrtSeq);
	 				 tstrsql= mySql.getString();	
			turnPage.queryModal(tstrsql, HealthOtherGrid,0,0,30);
			//��ѯ���������Ŀ��Ϣ
//			tstrsql = "select peitemdivname,peitemdivres,peitemdivdes"
//			          + " from LCPENoticeReply  where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "' and peitemcode='other'";
		  	  mySql=new SqlClass();
	 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	 			  mySql.setSqlId("LLUWManuHealthQSql6");//ָ��ʹ�õ�Sql��id
	 				 mySql.addSubPara(tContNo);
	 				mySql.addSubPara(tPrtSeq);
	 				 tstrsql= mySql.getString();	
			arrReturn = new Array();
		       arrReturn = easyExecSql(tstrsql);     
		       if(arrReturn!=null)
		        {
		           document.all('OtherPEItem').value = arrReturn[0][0];
		          fm.OtherPEItemRes.value = arrReturn[0][1];
		          fm.OtherPEItemDes.value = arrReturn[0][2];
		        }  
		          
		     //��ѯ������ʷ��Ϣ
//			tstrsql = "select DisResult, DisDesb from LCPENoticeResult where 1=1 "
//					  + " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			
			  mySql=new SqlClass();
	 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	 			  mySql.setSqlId("LLUWManuHealthQSql7");//ָ��ʹ�õ�Sql��id
	 				 mySql.addSubPara(tContNo);
	 				mySql.addSubPara(tPrtSeq);
	 				 tstrsql= mySql.getString();	
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
	var tRptNo = fm.RptNo.value;
	var tBatNo = fm.BatNo.value;
		//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
// var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
//          + " and ContNo = '"+tContNo+"'";

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
//             //+ " and a.BatNo = '"+tBatNo+"'"
//             + " and a.ClmNo = '"+tRptNo+"'"
//             //+ " and a.contno = b.otherno"
//             + " and a.prtseq = b.oldprtseq)"
//             + " where a8 = 1";
  var mySql=new SqlClass();
   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql.setSqlId("LLUWManuHealthQSql8");//ָ��ʹ�õ�Sql��id
		 mySql.addSubPara(tContNo);
		mySql.addSubPara(tRptNo);
		var strSql= mySql.getString();	
 
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("δ�鵽�����Ϣ");
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

   var mySql=new SqlClass();
   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql.setSqlId("LLUWManuHealthQSql9");//ָ��ʹ�õ�Sql��id
		 mySql.addSubPara(tCustomerNo);
		var strSql= mySql.getString();	
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
	

//	var strSql = "select code,codename from ldcode where 1=1 "
//	       + "and codetype = 'hospitalcodeuw'"
//	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
	
	   var mySql=new SqlClass();
	   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
		  mySql.setSqlId("LLUWManuHealthQSql10");//ָ��ʹ�õ�Sql��id
			 mySql.addSubPara(tContNo);
			var strSql= mySql.getString();	
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
	
 
  fm.action= "./UWManuHealthQSave.jsp";
  document.getElementById("fm").submit(); //�ύ
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
//	var strSQL = " select distinct PEItemCode,PEItemName,FREEPE,PEItemResult from LCPENoticeItem where PrtSeq = '"+prtSeq+"'"
//	          // + " and contno='"+contno+"'"
//	           ;	
	   var mySql=new SqlClass();
	   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
		  mySql.setSqlId("LLUWManuHealthQSql11");//ָ��ʹ�õ�Sql��id
			 mySql.addSubPara(prtSeq);
			var strSQL= mySql.getString();	
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
//		var strsql1="select (select prtno from lccont where contno='"+ tContNo +"'),name,"
//                         + " (select codename from ldcode where codetype='sex' and code=(case LCPENotice.customertype when 'A' then (select appntsex from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno) else (select sex from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno) end)),"
//                         + " ( case LCPENotice.customertype when 'A' then (select appntbirthday from lcappnt where contno='"+ tContNo + "' and appntno=LCPENotice.customerno) else (select birthday from lcinsured where contno='"+ tContNo + "' and insuredno=LCPENotice.customerno) end),"
//                         + " customerno, customertype"
//                         + " from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "'"
//				         + " and PrtSeq = '"+ tPrtSeq + "'";
		   var mySql=new SqlClass();
		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
			  mySql.setSqlId("LLUWManuHealthQSql12");//ָ��ʹ�õ�Sql��id
			  mySql.addSubPara(tContNo);
				 mySql.addSubPara(tPrtSeq);
				var strsql1= mySql.getString();	
       var arrReturn = new Array();
       arrReturn = easyExecSql(strsql1);     
       if(arrReturn!=null)
       {
        	document.all('PrtNo').value = arrReturn[0][0];
        	document.all('PEName').value = arrReturn[0][1];//���������
        	document.all('Sex').value = arrReturn[0][2];//������Ա�
        	document.all('Age').value = calPolAppntAge(arrReturn[0][3],tday);//���������
        	//document.all('CustomerNo').value = arrReturn[0][4];//���������
        	
//        	strsql1="select 1 from LCPENotice where 1=1"	 
//				         + " and ContNo = '"+ tContNo + "' and customerno='"+  arrReturn[0][4] +"'"
//				         + " and customertype='"+  arrReturn[0][5] +"'"
//				         + " and PrtSeq <> '"+ tPrtSeq + "'";
        	mySql=new SqlClass();
 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
 			  mySql.setSqlId("LLUWManuHealthQSql13");//ָ��ʹ�õ�Sql��id
 			  mySql.addSubPara(tContNo);
 				 mySql.addSubPara(arrReturn[0][4]);
 				  mySql.addSubPara(arrReturn[0][5]);
 				  mySql.addSubPara(tPrtSeq);
 				strsql1= mySql.getString();	
        	arrReturn = new Array();
		       arrReturn = easyExecSql(strsql1);     
		       if(arrReturn!=null)
		          document.all('RePETag').value = "Y";//�����ǣ����ǵ�һ��¼��
		       else 
		          document.all('RePETag').value = "N";
        } 
			//��ѯ�����Ŀ��Ϣ
//			var tstrsql = "select a.peitemcode,a.peitemname,b.code,b.codename,'','','','1'"
//			          + " from LCPENoticeItem a,ldcode b where 1=1 and a.peitemcode = b.codetype"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and a.PrtSeq = '"+ tPrtSeq + "' order by a.peitemcode,b.code";
			
			mySql=new SqlClass();
	 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	 			  mySql.setSqlId("LLUWManuHealthQSql14");//ָ��ʹ�õ�Sql��id
	 				  mySql.addSubPara(tPrtSeq);
	 				 var tstrsql= mySql.getString();	
			turnPage.queryModal(tstrsql, HealthGrid,0,0,30);	
			document.all('ItemNum').value = HealthGrid.mulLineCount;//��¼��ѡ�����Ŀ����
			//��ѯ���������Ŀ��Ϣ
//			tstrsql = "select peitemname from LCPENoticeItem where 1=1 and peitemcode = 'other'"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			mySql=new SqlClass();
	 		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
	 			  mySql.setSqlId("LLUWManuHealthQSql15");//ָ��ʹ�õ�Sql��id
	 				  mySql.addSubPara(tPrtSeq);
	 				  tstrsql= mySql.getString();	
			
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
//  var sSql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//        // + " and ContNo = '"+ tContNo + "'"
//			 + " and PrtSeq = '"+ tPrtSeq + "'";	 
	var mySql=new SqlClass();
	   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
		  mySql.setSqlId("LLUWManuHealthQSql16");//ָ��ʹ�õ�Sql��id
			  mySql.addSubPara(tPrtSeq);
			  var sSql= mySql.getString();	
  var arrSReturn = new Array();
	arrSReturn = easyExecSql(sSql);
	if (arrSReturn == null) 
	{} 
	else
	{
		if(tContNo != "" && tPrtSeq != "")
		{
			//��ѯ��������Ϣ
//			var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag from LCPENotice where 1=1"	 
//			         + " and ContNo = '"+ tContNo + "'"
//			         + " and PrtSeq = '"+ tPrtSeq + "'";
			 mySql=new SqlClass();
			   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
				  mySql.setSqlId("LLUWManuHealthQSql17");//ָ��ʹ�õ�Sql��id
					  mySql.addSubPara(tContNo);
					  mySql.addSubPara(tPrtSeq);
					  var strsql1= mySql.getString();	
			
//			prompt("",strsql1);
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
//			var tstrsql = "select distinct peitemcode,peitemname,FREEPE,PEItemResult from LCPENoticeItem where 1=1"
//					  //+ " and ContNo = '"+ tContNo + "'"
//					  + " and PrtSeq = '"+ tPrtSeq + "'";
			
			 mySql=new SqlClass();
			   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
				  mySql.setSqlId("LLUWManuHealthQSql18");//ָ��ʹ�õ�Sql��id
					  mySql.addSubPara(tPrtSeq);
					  var tstrsql= mySql.getString();	
			turnPage.queryModal(tstrsql, HealthGrid);	
			
			//��ѯ��������Ϣ
//	    var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
//	          // + " and ContNo = '"+ tContNo + "'"
//					 + " and PrtSeq = '"+ tPrtSeq + "'";	  
		 mySql=new SqlClass();
		   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
			  mySql.setSqlId("LLUWManuHealthQSql19");//ָ��ʹ�õ�Sql��id
				  mySql.addSubPara(tPrtSeq);
				  var Sql= mySql.getString();	
		  turnPage.queryModal(Sql, DisDesbGrid);	
		  
				//��ѯ���������Ϣ			 
//			var strSQL = "select * from LCPENotice where 1=1"	
//					+" and ContNo = '"+tContNo+"'"
//					+" and PrtSeq = '"+tPrtSeq+"'"; 	
			 mySql=new SqlClass();
			   mySql.setResourceName("uw.LLUWManuHealthQSql"); //ָ��ʹ�õ�properties�ļ���
				  mySql.setSqlId("LLUWManuHealthQSql20");//ָ��ʹ�õ�Sql��id
				  mySql.addSubPara(tContNo);
					  mySql.addSubPara(tPrtSeq);
					  var strSQL= mySql.getString();	
			var arrReturn = new Array();
			arrReturn = easyExecSql(strSQL);
			if (arrReturn == null) {} 
			else  {displayHealth(arrReturn[0]);}  	
			
		}
		
	}
		
  return true;
}
