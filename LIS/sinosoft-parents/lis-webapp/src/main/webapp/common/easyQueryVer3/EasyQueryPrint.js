/*****************************************************************
 *      Program NAME: �ͻ��˴�ӡ���ڴ���                    
 *        programmer: guoxiang                    
 *       Create DATE: 2004-04-19                                         
 * Modify programmer: guoxiang                                  
 *       Modify DATE: 2004-7-6 20:47 add function   easyQueryPrintEx                        
 *                    2004-7-21 14:59 add functon   easyQueryPrintShowCodeName 
 *                    2004-7-22 11:10 ��ǰ��win ������CCodeOperate.js�г�ͻ                 
 *****************************************************************                                               
 *****************************************************************
 */

//sFeatures����ӡ������ʽ  
var sFeatures = "status:0;help:0;close:0;scroll:0;dialogWidth:1200px;dialogHeight:800px;resizable=0";
	              + "dialogLeft:0;dialogTop:0;";
//�����ڵľ�� ��ǰ��win ������CCodeOperate.js�г�ͻ
var win_father=window;


//��ͨ
function easyQueryPrint(vFlag,vMultiline,vTurnPage) {
    //urlStr����ӡ����URL�Ͳ�ѯ����
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
    window.showModalDialog(urlStr, win_father, sFeatures);  
	
	
	//����ķ������ڵ�ַ������ʾ���ݣ����Ҽ�����ʾԴ���� ���Բ���
	//fm.action = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage;
	//fm.target = "_blank";
	//fm.submit();
}

//֧�ֺϼ���չ
function easyQueryPrintEx(vFlag,vMultiline,vTurnPage,vArray) {
    //urlStr����ӡ����URL�Ͳ�ѯ����
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage+"&PrintArray="+vArray;
    window.showModalDialog(urlStr, win_father, sFeatures);  
	
	
	//����ķ������ڵ�ַ������ʾ���ݣ����Ҽ�����ʾԴ���� ���Բ���
	//fm.action = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage+"&PrintArray="+vArray;
	//fm.target = "_blank";
	//fm.submit();
}

//֧�ִ�ӡshowcodename in multiline
function easyQueryPrintShowCodeName(vFlag,vMultiline,vTurnPage,vShowCodename){
    //urlStr����ӡ����URL�Ͳ�ѯ����
    var urlStr = "../f1print/EasyF1Print.jsp?Flag="+vFlag+"&GridName="+vMultiline+"&turnPageName="+vTurnPage+"&ShowCodename="+vShowCodename;
    window.showModalDialog(urlStr, win_father, sFeatures);  
}