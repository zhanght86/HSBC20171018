//���ļ��а����ͻ�����Ҫ����ĺ������¼� 

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypeHIInputSql";

function edorTypePTReturn()
{
		initForm();
}

function edorTypeHISave()
{ 
	document.all('fmtransact').value ="";  
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./PEdorTypeHISubmit.jsp";
    fm.target = "fraSubmit";
	fm.submit();
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
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
  
    ShowOtherContNoInfo();
  }
}

   
//---------------------------
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


function ShowOtherContNoInfo()
{

	var rCMSQL="";
	// modify by jiaqiangli 2009-04-13 0Ͷ1��(Ϊ����һ�£�������IA��ʾ)
	if(fm.CustomerRole.value=='1')	
	//if(fm.CustomerRole.value=='I')
	{
/*		 rCMSQL = " select ContNo   "
	        +"	 from lccont    "
	        +"	 where "
	        +"		insuredno = '"+fm.CustomerNo.value+"' and appflag='1'";
*/
	var sqlid1="PEdorTypeHIInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(fm.CustomerNo.value);//ָ������Ĳ���
	rCMSQL=mySql1.getString();


		}else
			{
/*   rCMSQL = " select ContNo   "
	        +"	 from lccont    "
	        +"	 where "
	        +"	appflag='1' and 	appntno ='"+fm.CustomerNo.value+"'";  
*/    
    var sqlid2="PEdorTypeHIInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.CustomerNo.value);//ָ������Ĳ���
	rCMSQL=mySql2.getString();
     
     }
var result;
   try
  {
     result = easyExecSql(rCMSQL, 1, 0);
  }
  catch(ex)
  {
   alert("��ѯ�ͻ��±�����Ϣ�쳣��");
   return false;
  }
  if (result != null )
   {
   	 var tTempContNo="";
   	 for(var t=0;t<result.length;t++)
   	 {
   	 	   if(fm.ContNo.value!=result[t][0])
   	 	   {
   	 	   	 tTempContNo+=result[t][0]+",";
   	 	   	}
   	 }

   	 	if(tTempContNo!="")
   	 	{
   	 		tTempContNo=tTempContNo.substr(0,tTempContNo.lastIndexOf(","));
   	 		alert("������֪�����ͻ����������±���"+tTempContNo+"δ����������֪���������������Щ����");
   	 		}     
      return true;
   }			
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
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{

}

function returnParent()
{
	top.opener.initEdorItemGrid();
	top.opener.getEdorItemGrid();
	top.close();
	top.opener.focus();
}

function personQuery()
{
    //window.open("./LCPolQuery.html");
//    window.open("./LPTypeIAPersonQuery.html");
    window.open("./LPTypeIAPersonQuery.html","",sFeatures);
}


function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}

function selectContInfo()
{
/*		var strSql="SELECT distinct ContNo,"
								+ " case when exists(select 'X' from LCAppnt where ContNo=a.ContNo and AppntNo='" + document.all('CustomerNo').value + "') and exists(select 'X' from LCInsured where ContNo=a.ContNo and InsuredNo='" + document.all('CustomerNo').value + "') then 'Ͷ����,������'"
     								+ " when exists(select 'X' from LCAppnt where ContNo=a.ContNo and AppntNo='" + document.all('CustomerNo').value + "') then 'Ͷ����'"
     								+ " when exists(select 'X' from LCInsured where ContNo=a.ContNo and InsuredNo='" + document.all('CustomerNo').value + "') then '������'"
								+ " else 'δ֪'"
								+ " end,"
								+ " (select Name from LDCom where ComCode=(select ManageCom from LCCont where ContNo=a.ContNo))"
								+ " FROM LPEdorItem a"
								+ " WHERE EdorAcceptNo='" + document.all('EdorAcceptNo').value + "' and EdorType='HI'"
								+ " ORDER BY ContNo";
*/
	var strSql="";
	var sqlid3="PEdorTypeHIInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql3.addSubPara(document.all('CustomerNo').value);
	mySql3.addSubPara(document.all('CustomerNo').value);
	mySql3.addSubPara(document.all('CustomerNo').value);
	mySql3.addSubPara(document.all('EdorAcceptNo').value);
	strSql=mySql3.getString();
		
		turnPage2.queryModal(strSql, ContGrid);
}


function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}

//��ѯ���ͻ���ɫ
function initInpRole()
{
	  var tContNo=document.all('ContNo').value;
	  //�ж����Ƿ���Ͷ����
//	  var sql = " select 1 from lcappnt where contno = '" + tContNo + "' and appntno ='"+document.all('CustomerNo').value+"'";
    
    var sql="";
	var sqlid4="PEdorTypeHIInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tContNo);//ָ������Ĳ���
	mySql4.addSubPara(document.all('CustomerNo').value);
	sql=mySql4.getString();
    
    var mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.CustomerRole.value="0";
    }

//    sql = " select 1 from lcinsured where contno = '" + tContNo + "' and insuredno ='"+document.all('CustomerNo').value+"'";
    
	var sqlid5="PEdorTypeHIInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(tContNo);//ָ������Ĳ���
	mySql5.addSubPara(document.all('CustomerNo').value);
	sql=mySql5.getString();
    
    mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.CustomerRole.value="1";
    }
//    var tsql = " select 1 from lcinsured where contno = '" + tContNo + "' and insuredno = (select appntno from lccont where contno = '" + tContNo + "')";
     
    var tsql = "";
    var sqlid6="PEdorTypeHIInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(tContNo);//ָ������Ĳ���
	mySql6.addSubPara(tContNo);
	tsql=mySql6.getString();
     
     mrr = easyExecSql(tsql);
    if ( mrr )
    {
        fm.CustomerRole.value="1";
    }
    //alert(fm.CustomerRole.value);
}

function InfoNoticePrint()
{
    var tContNo    = document.all('ContNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ27' and otherno = '"+EdorAcceptNo+"' and standbyflag1='"+tContNo+"'";
    
    var strSQL = "";
    var sqlid7="PEdorTypeHIInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(EdorAcceptNo);//ָ������Ĳ���
	mySql7.addSubPara(tContNo);
	strSQL=mySql7.getString();
    
    var sResult;
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("��ѯ��ȫ�����֪��Ϣʧ��,���ȱ���");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    fm.submit();
}