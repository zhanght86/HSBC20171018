//����SQL�������ú�̨�������ݻ�ȡ��
//����һ���ַ���
function queryForResult(sql) {
	
	post = "DTTableName=" + "DTT0113";

	post = encodeURI(post);
	post = encodeURI(post);// ���Σ��ܹؼ�
	Ext.Ajax.request( {
		url :'queryForInformation.jsp',
		method :'post',
		success : function(response, options) {
		alert("OK!");
			handleSuccess(Ext.decode(response.responseText));
		},
		failure : function(response, options) {
			alert("failure");
		},
		headers : {
			"Content-Type" :"application/x-www-form-urlencoded",
			"cache-control" :"no-cache"
		},
		params :post
	});

}

function decodeStringToArray(result) {

	var resultArray = new Array();

	var rows = result.split("^");

	var columns = null;

	for ( var i = 0; i < rows.length; i++) {
		columns = rows[i].split("/");

		resultArray[i] = new Array();

		resultArray[i] = columns;
	}

	return resultArray;
}