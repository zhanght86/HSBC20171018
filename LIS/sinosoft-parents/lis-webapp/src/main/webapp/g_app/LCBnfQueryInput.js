/***************************************************************
 * <p>ProName��LCBnfQueryInput.js</p>
 * <p>Title����������Ϣά��</p>
 * <p>Description����������Ϣά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * �ύ���ݺ󷵻ز���

 */
function afterSubmit(tFlagStr, tContent) {
	
	if (typeof(showInfo) == "object" && typeof(showInfo) != "unknown") {
	
		showInfo.close();
	}
	
	if (tFlagStr == "Fail") {
	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		getBnfInfo();
	}	
}

/**
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.submit();
}


function bnfSave(){
	
	BnfGrid.delBlankLine();		
	//У����������Ϣ
	if(valBnfInfo() == false){
		return false;	
	}
	mOperate="INSERT";
	submitForm();
}


function getBnfInfo(){
	
	//��ѯ��������Ϣ	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCBnfSql");
	tSQLInfo.setSqlId("LCBnfSql1");
	tSQLInfo.addSubPara(fm.mContNo.value);
	tSQLInfo.addSubPara(fm.InsuredSeqNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), BnfGrid, 1, 1);
}


/**
 *У����������Ϣ
 */
function valBnfInfo(){
	
	var tCount = BnfGrid.mulLineCount;
	var sumRate = 0;
	
	if(parseInt(tCount)==0){
		alert("��¼����������Ϣ!");
		return false;
	}
	if(parseInt(tCount)>0){
		if(!BnfGrid.checkValue("BnfGrid")){
			return false;
		}
		
		for (var i=0; i<tCount;i++){
			if(BnfGrid.getRowColData(i,8) !=''){
				if(BnfGrid.getRowColData(i,8)!='0' ){	
					if(BnfGrid.getRowColData(i,5)==null || BnfGrid.getRowColData(i,5) =='' && BnfGrid.getRowColData(i,6)==null || BnfGrid.getRowColData(i,6) =='' ){
						alert("�������������Ա�/�������ڣ�");
						return false;
					}
				}else if(BnfGrid.getRowColData(i,8)=='0'){
					if(BnfGrid.getRowColData(i,9).length!='15' && BnfGrid.getRowColData(i,9).length!='18'){
						alert("���������֤�������Ϊ15����18λ��");
						return false;
					}
				}
			}
		}
	}
		
	return true;
}
