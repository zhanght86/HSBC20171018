//***********************************************

//�����ܣ��˱���ѯ

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
  
   var sqlid824140817="DSHomeContSql824140817";
var mySql824140817=new SqlClass();
mySql824140817.setResourceName("bq.EdorGrpApproveQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824140817.setSqlId(sqlid824140817);//ָ��ʹ�õ�Sql��id
mySql824140817.addSubPara(EdorAcceptNo);//ָ������Ĳ���
var strSql=mySql824140817.getString();
   
//   var strSql="select edoracceptno,grpcontno,ApproveFlag,"
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapproveidea' and trim(code) = trim(ApproveFlag) ),"
//            + " ApproveOperator,ApproveDate,ApproveTime "
//            + " from lpgrpedormain where edoracceptno='"+EdorAcceptNo+ "'";

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
    var tEdorAcceptNo = EdorMainGrid.getRowColData(tSelNo,1); 
    var tContNo = EdorMainGrid.getRowColData(tSelNo,2); 
    var strSql ="" ;
    

    var sqlid824140941="DSHomeContSql824140941";
var mySql824140941=new SqlClass();
mySql824140941.setResourceName("bq.EdorGrpApproveQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824140941.setSqlId(sqlid824140941);//ָ��ʹ�õ�Sql��id
mySql824140941.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
strSql=mySql824140941.getString();
    
//    strSql = "select ApproveTimes,ApproveFlag, "
//             + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapproveidea' and trim(code) = trim(ApproveFlag) ),(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorapprovereason' and trim(code) = trim(ModifyReason) ),"
//             + " nvl(ApproveContent,''),ApproveDate,ApproveTime "
//             + " from LPApproveTrack a where 1=1"
//             + " and a.edoracceptno='"+tEdorAcceptNo+"' order by makedate,maketime ";
    
        divPolInfo.style.display = '';
        initRiskGrid();                     
        turnPage1.queryModal(strSql, RiskGrid);

    
} 

function showPolUW()
{
	var tSelNo = RiskGrid.getSelNo()-1;
	var tEdorNo = RiskGrid.getRowColData(tSelNo,10);
	var tEdorType = RiskGrid.getRowColData(tSelNo,1);
	var tPolNo = RiskGrid.getRowColData(tSelNo,2);
	
	var sqlid824141056="DSHomeContSql824141056";
var mySql824141056=new SqlClass();
mySql824141056.setResourceName("bq.EdorGrpApproveQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824141056.setSqlId(sqlid824141056);//ָ��ʹ�õ�Sql��id
mySql824141056.addSubPara(tEdorNo);//ָ������Ĳ���
mySql824141056.addSubPara(tEdorType);//ָ������Ĳ���
mySql824141056.addSubPara(tPolNo);//ָ������Ĳ���
var strsql=mySql824141056.getString();
	
//	var strsql = "select uwidea from lpuwmaster where edorno = '"+tEdorNo+"' and edortype = '"+tEdorType+"' and polno = '"+tPolNo+"'";
	var brr = easyExecSql(strsql, 1, 0,"","",1);
	if(brr)
	{
		fm.PolUWIdea.value = brr[0][0];
	}
	divPolUWIdiea.style.display = '';
}