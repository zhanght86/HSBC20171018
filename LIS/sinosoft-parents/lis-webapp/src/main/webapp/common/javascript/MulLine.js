/**	��MulLine�����ã�ʹ�ò���Ҫ��ÿ����������
 *	Ŀǰ����MulLine��ButtonLock
 *	����ƴ���������ļ���
 *
 */
function loadJS(url){
    document.write("<script type='text/javascript' src='" + url + "'><\/script>");
}
function loadCSS(url){
    document.write("<link type='text/css' href='" + url + "' rel='stylesheet' \/>");
}
function loadJQuery15(url){
	if(typeof(jQuery)=="undefined")
	{
		loadJS(url);
	}
}
function loadJQuery(){
	var url1 = "../common/javascript/jquery-1.7.2.js";
	var url2 = "../common/javascript/flexigrid.js";
	// var url0 = "../common/javascript/jquery.js";

	var url3 = "../common/javascript/jquery.MulLine.js";
	var url4 = "../common/css/flexigrid.pack.css";
	loadJS(url2);
	loadJS(url3);
	loadJQuery15(url1);
	// loadJS(url0);
	loadCSS(url4);
}
function loadButtonLockAndXqk(){
	var url1 = "../common/javascript/jquery-1.7.2.js";
	loadJQuery15(url1);
	loadJS("../common/cvar/xqkchinese.core.js");	//����ƴ���������ļ���
	loadJS("../common/javascript/jquery.ButtonLock.js");
}
if(typeof(jQuery)=="undefined")
{
	//loadJS("../common/javascript/MulLine(js).js");
	loadButtonLockAndXqk();
  loadJQuery();

}
else{
//alert(typeof(jQuery));
	loadJS("../common/javascript/MulLine(js).js");
	loadButtonLockAndXqk();
}

