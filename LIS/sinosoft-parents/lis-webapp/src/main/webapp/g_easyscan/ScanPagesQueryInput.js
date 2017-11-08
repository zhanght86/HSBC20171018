/***************************************************************
 * <p>ProName：ScanPagesQueryInput.js</p>
 * <p>Title：影像查询</p>
 * <p>Description：影像件查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
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

//查询影像件

function scanQuery() {

	if(tBussType==null||tBussType=="") {
		alert("传入的业务类型不能为空！");
		return false;
	}
	
	if (tBussNo==null || tBussNo=="") {
		alert("业务号码不能为空！");
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
		alert("没有查到影像件!");
		return false;
	}
}

//查询影像图片，根据单证细类

function showPages() {

	var tSelNo = ScanPagesGrid.getSelNo();
	if(tSelNo==0 || tSelNo==null) {
		alert("请选择一行记录进行操作！");
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
		alert("没有影像件");
	}
}

function document_onkeydown(){
	var keycode = event.keyCode;
	
	//判定是否按下小键盘END，旋转显示图片（Minim增加该方法）
	if (keycode == "35") {
		var rotation = (fm.all('divPages').filters.item(0).rotation + 1) % 4;
		document.all('divPages').filters.item(0).rotation = rotation;
		event.returnValue = false;
	}
	//判定是否按下PageUp
	if (keycode == "33") {
		try { hiddenPosition(); } catch(e) {}
		
		//循环图片队列
		for (var i=arrPicName.length; i >= 0; i--){
			//找到第一个满足条件的队列项

			if (i < pic_place){
				goToPic(i);
				break;
			}
		}
		event.returnValue = false;
	}
	//判定是否按下PageDown
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
	
	//判定是否按下小键盘上的*
	if ((keycode == "106")&&event.ctrlKey){
		//还原图片的实际大小，并将放大和缩小次数置为0
		fm.service.width = w
		s_img = 0;
		b_img = 0;
	}
	
	//判定是否按下小建盘的+，同时按住ctrl
	if ((keycode == "107")&&(event.ctrlKey&&event.altKey)) {
		try { hiddenPosition(); } catch(e) {}
		
		if (b_img <= 10){
			//判定是否缩小过

			if (s_img != 0){
				fm.service.width = w / (1 + 0.2 * (s_img - 1))
				s_img = s_img - 1;
			}else{
				b_img = b_img + 1;
				fm.service.width = w * (1 + 0.2 * b_img)
			}
		}
	}
	//判定是否按下小建盘的-，同时按住ctrl，不可改变到比原图还小

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
		//重新定位所选图片所在队列的位置
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
		alert("输入的页码超过页码范围");
	}
}
