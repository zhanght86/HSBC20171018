//***********************************************
//�������ƣ�EdorAppUWQuery.js
//�����ܣ��˱���ѯ
//�������ڣ�2005-07-21 19:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

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
 *  ��ѯ�����˱���Ϣ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryEdorMain() 
{
  
//   var strSql="select edorno,contno,uwstate,"
//            + " (select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'edorcontuwstate' and trim(code) = trim(uwstate) ),"
//            + " uwoperator,uwdate,uwtime "
//            + " from lpedormain where edoracceptno='"+EdorAcceptNo+ "'";
   
    var sqlid1="EdorAppUWQuerySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorAppUWQuerySql");
	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	mySql1.addSubPara(EdorAcceptNo);//ָ���������
	var strSql = mySql1.getString();

    var crr = easyExecSql(strSql, 1, 0,"","",1);
    if (!crr)
    {
        //divEdorMainInfo.style.display='none';
        alert("��ȫ������û��������");
        divPolInfo.style.display = 'none';
        return;
    }
    else
    {
        initEdorMainGrid();
        turnPage.queryModal(strSql, EdorMainGrid);
        divEdorMainInfo.style.display='';
        divPolInfo.style.display = 'none';
    }   
}

//��ʾ��ȫ������Ϣ
function  showRiskInfo()
{
    
    var tSelNo = EdorMainGrid.getSelNo()-1;
    var tEdorNo = EdorMainGrid.getRowColData(tSelNo,1); 
    var tContNo = EdorMainGrid.getRowColData(tSelNo,2); 
//    var strSql = "select uwidea from lpuwmastermain where edorno = '"+tEdorNo+"' and contno = '"+tContNo+"'";
    
    var sqlid2="EdorAppUWQuerySql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorAppUWQuerySql");
	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	mySql2.addSubPara(tEdorNo);//ָ���������
	mySql2.addSubPara(tContNo);
	var strSql = mySql2.getString();
    
    var brr = easyExecSql(strSql, 1, 0,"","",1);
    divPolUWIdiea.style.display = 'none';
    divContUWIdiea.style.display = '';
    if(brr)
    {

    	fm.ContUWIdea.value = brr[0][0];
    }

//    strSql = "select edortype,polno,riskcode,(select b.riskname from lmrisk b where b.riskcode = a.riskcode), "
//             + " uwflag,(select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'edoruwstate' and trim(code) = trim(uwflag) ),"
//             + " uwcode,uwdate,uwtime,edorno "
//             + " from LPPol a where 1=1"
//             + " and a.edorno='"+tEdorNo+"'";
    
    var sqlid3="EdorAppUWQuerySql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("bq.EdorAppUWQuerySql");
	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
	mySql3.addSubPara(tEdorNo);//ָ���������
	strSql = mySql3.getString();
    
    
    brr=easyExecSql(strSql, 1, 0,"","",1);
    if(brr)
    {
        divPolInfo.style.display = '';
        initRiskGrid();                     
        turnPage1.queryModal(strSql, RiskGrid);
    }
    else
    {
        alert("û�����ֽ��к˱���");
        divPolInfo.style.display = 'none';
        return;
    }
} 

function showPolUW()
{
	var tSelNo = RiskGrid.getSelNo()-1;
	var tEdorNo = RiskGrid.getRowColData(tSelNo,10);
	var tEdorType = RiskGrid.getRowColData(tSelNo,1);
	var tPolNo = RiskGrid.getRowColData(tSelNo,2);
//	var strsql = "select uwidea from lpuwmaster where edorno = '"+tEdorNo+"' and edortype = '"+tEdorType+"' and polno = '"+tPolNo+"'";
	
	    var sqlid4="EdorAppUWQuerySql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("bq.EdorAppUWQuerySql");
		mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
		mySql4.addSubPara(tEdorNo);//ָ���������
		mySql4.addSubPara(tEdorType);
		mySql4.addSubPara(tPolNo);
		var strSql = mySql4.getString();
	
	var brr = easyExecSql(strsql, 1, 0,"","",1);
	if(brr)
	{
		fm.PolUWIdea.value = brr[0][0];
	}
	divPolUWIdiea.style.display = '';
}