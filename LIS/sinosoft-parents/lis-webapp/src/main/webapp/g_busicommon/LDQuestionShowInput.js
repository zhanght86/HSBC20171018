/***************************************************************
 * <p>ProName��LDQuestionShowInput.js</p>
 * <p>Title��������Ի�չʾ</p>
 * <p>Description��������Ի�չʾ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-13
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �Ի�չʾ
 */
function showDetail() {
	
	var tLimitSendNode = "";
	var tLimitReplyNode = "";
	if (tOtherNoType=="QUOT") {//modify by songsz 20140522 ����ѯ�۽ڵ�����
		
		if (tActivityID=="0800100001") {//ѯ��¼��
			
			tLimitSendNode = " and a.sendnode in('0800100002','0800100003') ";
			tLimitReplyNode = " and a.replynode='0800100001' ";
		} else if (tActivityID=="0800100002") {//�к�
			
			tLimitSendNode = " and a.sendnode in('0800100002','0800100003') ";
		} 
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_busicommon.LDQuestiontSql");
	tSQLInfo.setSqlId("LDQuestiontSql2");
	tSQLInfo.addSubPara(tOtherNoType);
	tSQLInfo.addSubPara(tOtherNo);
	if (tSubOtherNo==null || tSubOtherNo=="" || tSubOtherNo=="null") {
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara(tSubOtherNo);
	}
	tSQLInfo.addSubPara(tLimitSendNode);
	tSQLInfo.addSubPara(tLimitReplyNode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null) {
		
		var tInnerHTMLStart = "<table class=common>";
		var tInnerHTMLEnd =   "</table>";
		var tInnerHTML = "";
		
		var tSendDate = "";
		var tSendTime = "";
		var tSender = "";
		var tSendContent = "";
		var tReplyDate = "";
		var tReplyTime = "";
		var tReplier = "";
		var tReplyContent = "";
		
		for (i=0;i<tArr.length;i++) {
			
			tSendDate = tArr[i][0];
			tSendTime = tArr[i][1];
			tSender = tArr[i][2];
			tSendContent = tArr[i][3];
			tReplyDate = tArr[i][4];
			tReplyTime = tArr[i][5];
			tReplier = tArr[i][6];
			tReplyContent = tArr[i][7];
			
			tInnerHTML = tInnerHTML +"<tr class=common>"
															+"	<td class=title colSpan=6>"+tSendDate+"&nbsp;&nbsp;"+tSendTime+"&nbsp;&nbsp;"+tSender+"</td>"
															+"</tr>"
															+"<tr class=common>"
															+"	<td class=input colSpan=6><textarea cols=80 rows=3>Q:"+tSendContent+"</textarea></td>"
															+"</tr>";
															
			if (tReplyContent!=null && tReplyContent!="") {
				
			tInnerHTML = tInnerHTML +"<tr class=common>"
															+"	<td class=title colSpan=6>"+tReplyDate+"&nbsp;&nbsp;"+tReplyTime+"&nbsp;&nbsp;"+tReplier+"</td>"
															+"</tr>"
															+"<tr class=common>"
															+"	<td class=input colSpan=6><textarea cols=80 rows=3>A:"+tReplyContent+"</textarea></td>"
															+"</tr>";
			}
		}
		
		document.all("divShowDetail").innerHTML = tInnerHTMLStart+tInnerHTML+tInnerHTMLEnd;
	}
}
