//***********************************************
//�������ƣ�EdorUWImageQuery.js
//�����ܣ���ȫ�˱�Ӱ�����ϲ�ѯ
//�������ڣ�2005-11-01
//�� �� �ˣ�XinYQ
//���¼�¼��������     ��������     ����ԭ��/����
//***********************************************

//���ļ��а����ͻ�����Ҫ����ĺ������¼�


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

function returnParent()
{
  top.close();
}


/*********************************************************************
 *  ��ѯ��ͬ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */


function  QueryImage()
{
	var subtype = fm.subtype.value;
	var contno = fm.ContNo.value;
	//alert(subtype);��֤����
	//alert(fm.ContNo.value);
//	var aSQL = "select a.bussno, ";
	var aSQL = "";
	if(subtype != "")
	{
//    aSQL = aSQL + "'" + document.getElementsByName("subtypname")[0].value + "', ";
//    aSQL = aSQL + "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";
    
     var subtypname1 = document.getElementsByName("subtypname")[0].value;
     var sqlid1="EdorUWImageQuerySql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.EdorUWImageQuerySql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(subtypname1);//ָ���������
	 mySql1.addSubPara(subtype);//ָ���������
	 mySql1.addSubPara(contno);//ָ���������
	 aSQL = mySql1.getString();
  }
  else
  {
//    aSQL = aSQL + "'��ȫ�˱�', "
//    aSQL = aSQL + "(select b.SubTypeName from es_doc_def b where b.busstype='TB' and b.subtype=a.subtype),a.docid from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";
    
     var sqlid2="EdorUWImageQuerySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.EdorUWImageQuerySql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(contno);//ָ���������
	 aSQL = mySql2.getString();
  }
	turnPage.queryModal(aSQL, ImageGrid);
}


function  showImage()
{
 //����ͼƬ
 	var tSelNo = ImageGrid.getSelNo()-1;
 	var tdocid = ImageGrid.getRowColData(tSelNo,4);
 	//alert("tdocid first="+tdocid);
 	fm.DocID.value=tdocid ;
 	//alert("tdocid second="+fm.DocID.value);
 	top.fraPic.centerPic.innerHTML = "";
 	fm.submit(); //�ύ
}