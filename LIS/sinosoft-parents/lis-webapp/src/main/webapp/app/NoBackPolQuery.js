//
//�������ƣ�NoBackPolQuery.js
//�����ܣ�δ¼�����ݲ�ѯ
//�������ڣ�2007-10-16 15:10
//������  ��HULY
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();

//�򵥲�ѯ

function easyPrint()
{
	//easyQueryPrint(2,'CodeGrid','turnPage');
	   var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();
}

function easyQuery()
{	
	if (fm.StartDate.value == "" || fm.EndDate.value == "" )
	{
			alert("��ʼʱ��ͽ���ʱ�䲻��Ϊ��!");
			return false;
	}
	
	initCodeGrid();
	
	// ��дSQL���
//	var strSQL = "select doccode,managecom,to_char(makedate,'yyyy-mm-dd'),maketime "
//             + " from es_doc_main "
//             + " where inputstate is null  and substr(doccode,3,2)!= '12' "
//             + "and subtype='UA001' and busstype='TB' "
//             + getWherePart('makedate','StartDate','>=')
//             + getWherePart('makedate','EndDate','<=')
//             + getWherePart('ManageCom','ManageCom','like')
//             + " and managecom like '"+comCode+"%' "
//             //+  "order by makedate,maketime";
             
            var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value;
           	var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
           	var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
         	var  sqlid1="NoBackPolQuerySql0";
         	var  mySql1=new SqlClass();
         	mySql1.setResourceName("app.NoBackPolQuerySql"); //ָ��ʹ�õ�properties�ļ���
         	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
         	mySql1.addSubPara(StartDate);//ָ������Ĳ���
         	mySql1.addSubPara(EndDate);//ָ������Ĳ���
         	mySql1.addSubPara(ManageCom);//ָ������Ĳ���
         	mySql1.addSubPara(comCode);//ָ������Ĳ���
         	
         	
          	
     var tBPOID = fm.BPOID.value;
     if(tBPOID!=null&&tBPOID !=""){
//        strSQL= strSQL+ " and substr(managecom,1,4) in (select (managecom) from bpoallotrate"
//                      + " where trim(BPOID) = '"+tBPOID+"') order by makedate,maketime";
        addStr= " and substr(managecom,1,4) in (select (managecom) from bpoallotrate"
                + " where trim(BPOID) = '"+tBPOID+"') ";
     }
//     else{
//     strSQL=strSQL+" order by makedate ,maketime";
//     }
     mySql1.addSubPara(addStr);//ָ������Ĳ���
     var strSQL=mySql1.getString();
     
	turnPage.queryModal(strSQL, CodeGrid);    

}


