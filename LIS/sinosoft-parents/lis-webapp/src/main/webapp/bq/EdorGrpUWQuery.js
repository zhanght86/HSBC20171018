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
  
   var sqlid824100247="DSHomeContSql824100247";
var mySql824100247=new SqlClass();
mySql824100247.setResourceName("bq.EdorGrpUWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824100247.setSqlId(sqlid824100247);//ָ��ʹ�õ�Sql��id
mySql824100247.addSubPara(EdorAcceptNo);//ָ������Ĳ���
var strSql=mySql824100247.getString();
   
//   var strSql="select edorno,grpcontno,uwstate,"
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorgrpuwstate' and trim(code) = trim(uwstate) ),"
//            + " uwoperator,uwdate,uwtime "
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
    var tEdorNo = EdorMainGrid.getRowColData(tSelNo,1); 
    var tContNo = EdorMainGrid.getRowColData(tSelNo,2); 
    var strSql ="" ;
    

    var sqlid824100400="DSHomeContSql824100400";
var mySql824100400=new SqlClass();
mySql824100400.setResourceName("bq.EdorGrpUWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824100400.setSqlId(sqlid824100400);//ָ��ʹ�õ�Sql��id
mySql824100400.addSubPara(tEdorNo);//ָ������Ĳ���
strSql=mySql824100400.getString();
    
//    strSql = "select uwno,appntname, "
//             + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorgrpuwstate' and trim(code) = trim(passflag) ),uwidea,"
//             + " operator,makedate,maketime "
//             + " from lpguwsubmain a where 1=1"
//             + " and a.edorno='"+tEdorNo+"' and passflag in (select code from ldcode where codetype='edorgrpuwstate') order by makedate,maketime ";
    
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
	
	var sqlid824100546="DSHomeContSql824100546";
var mySql824100546=new SqlClass();
mySql824100546.setResourceName("bq.EdorGrpUWQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824100546.setSqlId(sqlid824100546);//ָ��ʹ�õ�Sql��id
mySql824100546.addSubPara(tEdorNo);//ָ������Ĳ���
mySql824100546.addSubPara(tEdorType);//ָ������Ĳ���
mySql824100546.addSubPara(tPolNo);//ָ������Ĳ���
var strsql=mySql824100546.getString();
	
	
//	var strsql = "select uwidea from lpuwmaster where edorno = '"+tEdorNo+"' and edortype = '"+tEdorType+"' and polno = '"+tPolNo+"'";
	var brr = easyExecSql(strsql, 1, 0,"","",1);
	if(brr)
	{
		fm.PolUWIdea.value = brr[0][0];
	}
	divPolUWIdiea.style.display = '';
}