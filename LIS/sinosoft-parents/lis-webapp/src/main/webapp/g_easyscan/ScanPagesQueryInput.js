/***************************************************************
 * <p>ProName��ScanPagesQueryInput.js</p>
 * <p>Title��Ӱ���ѯ</p>
 * <p>Description��Ӱ�����ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var showInfo;
var tSQLInfo = new SqlClass();
var arrPicName= new Array();
var pic_place = 0;
var s_img =0;
var b_img = 0;
var w  = 0;

//��ѯӰ���

function scanQuery() {

	if(tBussType==null||tBussType=="") {
		alert("�����ҵ�����Ͳ���Ϊ�գ�");
		return false;
	}
	
	if (tBussNo==null || tBussNo=="") {
		alert("ҵ����벻��Ϊ�գ�");
		return false;
	}
	divPages.style.display = 'none';
	divPages1.style.display = 'none';
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_easyscan.ScanPagesQuerySql");
	tSQLInfo.setSqlId("ScanPagesQuerySql1");
	tSQLInfo.addSubPara(tBussType);
	tSQLInfo.addSubPara(tBussNo);
	turnPage1.queryModal(tSQLInfo.getString(), ScanPagesGrid,"0","1");
	if (!turnPage1.strQueryResult) {
		alert("û�в鵽Ӱ���!");
		return false;
	}
}

//��ѯӰ��ͼƬ�����ݵ�֤ϸ��

function showPages() {

	var tSelNo = ScanPagesGrid.getSelNo();
	if(tSelNo==0 || tSelNo==null) {
		alert("��ѡ��һ�м�¼���в�����");
	}
	
	var tDocID = ScanPagesGrid.getRowColData(tSelNo-1,6);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_easyscan.ScanPagesQuerySql");
	tSQLInfo.setSqlId("ScanPagesQuerySql2");
	tSQLInfo.addSubPara(tDocID);  
	arrPicName = easyExecSql(tSQLInfo.getString());
	
	if(arrPicName!=null&&arrPicName!="") {
		
		fm.service.width = 1000;
		if(w!=0){
			fm.service.width = w;
		}
		
		fm.service.src = arrPicName[0];
		fm.all('divPages').filters.item(0).rotation = "0";
		divPages.style.display = '';
		divPages1.style.display = '';
		w = fm.service.width;
		document.getElementById('AllPage').innerHTML = arrPicName.length;
		fm.all('page').value = 1;
		pic_place = 0;
		s_img =0;
		b_img = 0;
		
	}else{
		fm.service.src = "";
		fm.service.width= 0;
		divPages.style.display = 'none';
		divPages1.style.display = 'none';
		alert("û��Ӱ���");
	}
}

function document_onkeydown(){
	var keycode = event.keyCode;
	
	//�ж��Ƿ���С����END����ת��ʾͼƬ��Minim���Ӹ÷�����
	if (keycode == "35") {
		var rotation = (fm.all('divPages').filters.item(0).rotation + 1) % 4;
		document.all('divPages').filters.item(0).rotation = rotation;
		event.returnValue = false;
	}
	//�ж��Ƿ���PageUp
	if (keycode == "33") {
		try { hiddenPosition(); } catch(e) {}
		
		//ѭ��ͼƬ����
		for (var i=arrPicName.length; i >= 0; i--){
			//�ҵ���һ�����������Ķ�����

			if (i < pic_place){
				goToPic(i);
				break;
			}
		}
		event.returnValue = false;
	}
	//�ж��Ƿ���PageDown
	if (keycode == "34") {
		try { hiddenPosition(); } catch(e) {}
		
		for (var i=0; i < arrPicName.length; i++){
			if (i > pic_place){
				goToPic(i);
				break;
			}
		}
		event.returnValue = false;
	}
	
	//�ж��Ƿ���С�����ϵ�*
	if ((keycode == "106")&&event.ctrlKey){
		//��ԭͼƬ��ʵ�ʴ�С�������Ŵ����С������Ϊ0
		fm.service.width = w
		s_img = 0;
		b_img = 0;
	}
	
	//�ж��Ƿ���С���̵�+��ͬʱ��סctrl
	if ((keycode == "107")&&(event.ctrlKey&&event.altKey)) {
		try { hiddenPosition(); } catch(e) {}
		
		if (b_img <= 10){
			//�ж��Ƿ���С��

			if (s_img != 0){
				fm.service.width = w / (1 + 0.2 * (s_img - 1))
				s_img = s_img - 1;
			}else{
				b_img = b_img + 1;
				fm.service.width = w * (1 + 0.2 * b_img)
			}
		}
	}
	//�ж��Ƿ���С���̵�-��ͬʱ��סctrl�����ɸı䵽��ԭͼ��С

	if ((keycode == "109")&&(event.ctrlKey&&event.altKey)) {
		try { hiddenPosition(); } catch(e) {}
		if (b_img != -2){
			fm.service.width = w * (1 + 0.2 * (b_img - 1))
			b_img = b_img - 1;
		}
	}

}

function goToPic(index) { 

	if (index != pic_place) {
		fm.service.src = arrPicName[index];
		//top.fraPic.centerPic.innerHTML = top.fraPic.arrPicName[index];
		//���¶�λ��ѡͼƬ���ڶ��е�λ��
		pic_place = index; 
		document.all('page').value = index+1;
	}
}

function  turnpage(){
	
	var pageno = document.all('page').value;
	pageno= pageno-1;
	if(0 <= pageno && pageno < arrPicName.length){
		goToPic(pageno);
	}else{
		alert("�����ҳ�볬��ҳ�뷶Χ");
	}
}
