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
	var bussno=fm.BussNo.value;
	var scanno = fm.ScanNo.value;
	
	
	
		var aSQL = "";
		if(subtype!=null && subtype!=""){
			var sqlid902174847="DSHomeContSql902174847";
			var mySql902174847=new SqlClass();
			mySql902174847.setResourceName("bq.ImageQuerySql");//ָ��ʹ�õ�properties�ļ���
			mySql902174847.setSqlId(sqlid902174847);//ָ��ʹ�õ�Sql��id
			mySql902174847.addSubPara(bussno);//ָ������Ĳ���
			mySql902174847.addSubPara(subtype);//ָ������Ĳ���
			aSQL=mySql902174847.getString();
			
		}else{
			var sqlid1="ImageQuerySql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("bq.ImageQuerySql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(bussno);//ָ������Ĳ���
		  aSQL=mySql1.getString();	
		}
	
		
//		if(subtype!=null && subtype!=""){
//			aSQL+= " and a.subtype=''"+subtype+"''";
//		}
//	
//	var aSQL="select a.doccode,a.subtype,(select subtypename from es_doc_def where subtype=a.subtype),a.docid,a.scanno,"
//		+"(select count(1) from es_doc_pages where docid=a.docid),a.makedate,a.maketime,a.scanoperator"
//		+" from es_doc_relation b,es_doc_main a where a.DocID = b.DocID and b.bussno='"+bussno+"'";

	
	
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
 	document.getElementById("fm").submit(); //�ύ    
 
} 
 
function initImageType(){
	
			var sqlid2="ImageQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("bq.ImageQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(BussType);//ָ������Ĳ���
	    var strSql=mySql2.getString();	
	
//	 var strSql = "select subtype,subtypename from es_doc_def where busstype like '"+BussType+"%' and validflag in ('0','2') order by subtype"; 
	  
	  var strResult = easyQueryVer3(strSql);
	  //alert(strResult);

	  if (strResult) {
	    document.all("subtype").CodeData = strResult;
	  }
}
 