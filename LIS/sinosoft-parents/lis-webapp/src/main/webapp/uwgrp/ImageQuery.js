//***********************************************
//��������  ImageQuery.js
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2005-07-07 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "uwgrp.ImageQuerySql";

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
 

function  QueryImage(){ 
	
	var subtype = fm.subtype.value;
	var contno=fm.ContNo.value;
	//alert(subtype);��֤����
	//alert(fm.ContNo.value);
	var aSQL="";
	if(subtype!="")
	{
	//aSQL="select a.bussno,'����Լ',(select b.SubTypeName from es_doc_def b where b.subtype=a.subtype),a.docid from es_doc_relation a where subtype='"+subtype+"' and bussno='"+contno+"' and busstype='TB'";					
        var sqlid1="ImageQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(subtype);
		mySql1.addSubPara(contno);
		aSQL = mySql1.getString();
		
 }else{

 //aSQL="select a.bussno,'����Լ',(select b.SubTypeName from es_doc_def b where b.subtype=a.subtype),a.docid from es_doc_relation a where  bussno='"+contno+"' and busstype='TB'";	
        var sqlid2="ImageQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(contno);
	
		aSQL = mySql2.getString();

}
	turnPage.queryModal(aSQL, ImageGrid);  
}  
 

function  showImage(){ 
 //����ͼƬ
 	var tSelNo = ImageGrid.getSelNo()-1;
 	var tdocid = ImageGrid.getRowColData(tSelNo,4);	
 	//alert("tdocid first="+tdocid);
 	fm.DocID.value=tdocid ;
 	//alert("tdocid second="+fm.DocID.value);  
 	top.fraPic.centerPic.innerHTML = "";
 	fm.submit(); //�ύ    
 
} 
 

 