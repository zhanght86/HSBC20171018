//***********************************************
//�������ƣ�EdorUWQuery.js
//�����ܣ��˱���ѯ
//�������ڣ�2005-07-12 19:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  ��ѯ��ͬ��Ϣ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function querycont() 
{
//   var strSql="select contno,(select concat(concat(trim(code),'-'),trim(codename) from ldcode where codetype = 'station' and code = ManageCom),"
//                   +" (select codename from ldcode where codetype = 'salechnl' and code = salechnl),"
//                   +" AgentCode,Remark "
//                   +" from LPCont where ContNo='"+ ContNo+ "'"
//                   +" and edorno = '"+EdorNo+"'";
   var strSql = "";
   var sqlid1="EdorUWQuerySql1";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.EdorUWQuerySql"); //ָ��ʹ�õ�properties�ļ���
   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   mySql1.addSubPara(ContNo);//ָ������Ĳ���
   mySql1.addSubPara(EdorNo);//ָ������Ĳ���
   strSql=mySql1.getString();
   var brr = easyExecSql(strSql);
   if(brr)
   {   
   	     brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.ManageCom.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':fm.SaleChnl.value  = brr[0][2];
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.AgentCode.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.Remark.value  = brr[0][4];
   }
   else
   {
//   	     strSql="select contno,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'station' and code = ManageCom),"
//   	               +" (select codename from ldcode where codetype = 'salechnl' and code = salechnl),"
//   	               +" AgentCode,Remark "
//                   +" from LCCont where ContNo='"+ ContNo+ "'";   	     
   	  var sqlid2="EdorUWQuerySql2";
      var mySql2=new SqlClass();
      mySql2.setResourceName("bq.EdorUWQuerySql"); //ָ��ʹ�õ�properties�ļ���
      mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
      mySql2.addSubPara(ContNo);//ָ������Ĳ���
      strSql=mySql2.getString();   	     
         brr = easyExecSql(strSql);  
         if(brr)
         {   
   	        brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNo.value  = brr[0][0];
            brr[0][1]==null||brr[0][1]=='null'?'0':fm.ManageCom.value  = brr[0][1];
            brr[0][2]==null||brr[0][2]=='null'?'0':fm.SaleChnl.value  = brr[0][2];
            brr[0][3]==null||brr[0][3]=='null'?'0':fm.AgentCode.value  = brr[0][3];
            brr[0][4]==null||brr[0][4]=='null'?'0':fm.Remark.value  = brr[0][4];
         } 
         else
         {
        	alert("��ͬ��Ϣ��ѯʧ�ܣ�");
        	return "";
         }     
   }	
}

//��ѯ��������Ϣ
function queryinsured()
{ 
//    var strSql = "select InsuredNo,Name,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where code = LPInsured.IDType and codetype = 'idtype'),IDNo"
//                      +" from lpinsured where 1=1"
//                      +" and ContNo='"+ContNo+"'"
//                      +" and EdorNo='"+EdorNo+"'"
//				      +" order by SequenceNo ";				      
    var strSql = "";
    var sqlid3="EdorUWQuerySql3";
    var mySql3=new SqlClass();
    mySql3.setResourceName("bq.EdorUWQuerySql"); //ָ��ʹ�õ�properties�ļ���
    mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
    mySql3.addSubPara(ContNo);//ָ������Ĳ���
    mySql3.addSubPara(EdorNo);//ָ������Ĳ���
    strSql=mySql3.getString();    
	var brr = easyExecSql(strSql);
    if (brr)
    {
        initPolAddGrid();
        turnPage.queryModal(strSql, PolAddGrid);
    }
    else
    {   
//    	strSql = "select InsuredNo,Name,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where code = LCInsured.IDType and codetype = 'idtype'),IDNo"
//                      +" from lcinsured where 1=1"
//                      +" and ContNo='"+ContNo+"'"
//				      +" order by SequenceNo ";			      
    	var sqlid4="EdorUWQuerySql4";
        var mySql4=new SqlClass();
        mySql4.setResourceName("bq.EdorUWQuerySql"); //ָ��ʹ�õ�properties�ļ���
        mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
        mySql4.addSubPara(ContNo);//ָ������Ĳ���
        strSql=mySql4.getString();        
    	brr = easyExecSql(strSql);
    	if (brr)
        {
            initPolAddGrid();
            turnPage.queryModal(strSql, PolAddGrid);
        }
        else
        {	
           alert("��������Ϣ��ѯʧ�ܣ�");
    	   return "";
    	}
    }			  
}
//��ʾ��ȫ������Ϣ
function  showRiskInfo()
{
	
	var tSelNo = PolAddGrid.getSelNo()-1;
    var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);	
  
//	var strSql = "select a.polno,a.MainPolNo,a.riskcode,b.riskname,a.Prem,a.Amnt,a.CValiDate,a.EndDate,a.PayIntv,a.PayYears "
//	                        + " from LPPol a,lmrisk b where 1=1"
//							+ " and a.contno='"+ContNo+"'"
//							+ " and a.edorno='"+EdorNo+"'"
//							+ " and a.insuredno ='"+tInsuredNo+"'"
//							+ " and a.riskcode = b.riskcode";
	var strSql = "";
    var sqlid5="EdorUWQuerySql5";
    var mySql5=new SqlClass();
    mySql5.setResourceName("bq.EdorUWQuerySql"); //ָ��ʹ�õ�properties�ļ���
    mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
    mySql5.addSubPara(ContNo);//ָ������Ĳ���
    mySql5.addSubPara(EdorNo);//ָ������Ĳ���
    mySql5.addSubPara(tInsuredNo);//ָ������Ĳ���
    strSql=mySql5.getString();
	
    var brr=easyExecSql(strSql);
    if(brr)
    {
	    DivPolInfo.style.display = '';
	    initRiskGrid();						
	    turnPage.queryModal(strSql, RiskGrid);
	}
     else
	{
		DivPolInfo.style.display = 'none';
		return;
	}
} 

//���ֺ˱��켣��ѯ
function showRiskResult()
{
	
   var tSelNo = RiskGrid.getSelNo()-1;
   var tPolNo = RiskGrid.getRowColData(tSelNo,1);   
   
   window.open("./EdorRiskTraceMain.jsp?ContNo="+ContNo+"&PolNo="+tPolNo+"&EdorNo="+EdorNo,"", "");
	
}


//��ͬ�˱��켣��ѯ
function QueryContTrace()
{

	window.open("./EdorContTraceMain.jsp?ContNo="+ContNo+"&EdorNo="+EdorNo,"", ""); 
	
} 