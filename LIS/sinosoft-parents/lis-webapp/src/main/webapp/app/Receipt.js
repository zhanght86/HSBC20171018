var turnPage = new turnPageClass();
var arrResult1;
var showInfo;
/**
 * @author lzj at 2016/4/29
 * @param id
 * @returns document.getElementById(id)
 * @desc ���ڼ򻯺�������
 */
function $(id){
	return document.getElementById(id);
}
/**
 * add by lzj at 2016/4/29
 * @desc ����ӡˢ��(������)��ǩ����������ѯ
 */
function easyQueryClick(){
	// ��ʼ�����
	initPolGrid();
	//-----------------
	var strSQL ="";
	var sqlid1="ReceiptSql1";
	var mySql1=new SqlClass();
	/*if(($("ProposalContNo").value==null||trim($("ProposalContNo").value)=='') && ($("ValidStartDate").value==null||trim($("ValidStartDate").value)=='')){
		alert('��¼��ӡˢ�Ż�ǩ������!');
		return false;
	}*/
	mySql1.setResourceName("app.ReceiptSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara($("ProposalContNo").value);//ָ������Ĳ���
	mySql1.addSubPara($("ValidStartDate").value);//ָ������Ĳ���
	strSQL=mySql1.getString();
	var flag=turnPage.queryModal(strSQL, PolGrid);
	if(flag==false){
		alert("��ʾ:û����Ҫ�ҵĲ�ѯ�����");
		return false;
	}
  return true;
}
/**
 * �ж϶�ѡ��checkbox�Ƿ�ѡ��
 */
function isSelect(){
	var flag=false;
	for(var i=0;i<PolGrid.mulLineCount;i++){
		if(PolGrid.getChkNo(i)){
			flag=true;
		}
	}
	if(flag==true){
		return true;
	}else{
		alert("��ѡ��Ҫ���µ����ݡ���ִ�и��²�����");return false;
	}
}

/**
 * @author add by lzj  at 2016/04/29
 * @returns {Boolean}
 * @desc ����updateData()���ڸ��¡���2016/05/05�����²����ύ��ui/app/ReceiptUpdate.jsp���д���
 */
function updateData(){
	var getSelectChkRow=0;
	var strSQL="";
	var iArray = new Array(0);
	var mySql185=new SqlClass();
	mySql185.setResourceName("app.ContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql185.setSqlId(sqlid185);//ָ��ʹ�õ�Sql��id
	var str="update lccont l set l.accname='�ν�' where l.prtno in(";
	var strPro="";
	for(var i=0;i<PolGrid.mulLineCount;i++){
		if(PolGrid.getChkNo(i)){ 
			++getSelectChkRow;
			for(var j=0;j<getSelectChkRow;j++){
				if(iArray.indexOf(PolGrid.getRowColData(i,3)) == -1){
					iArray.push(PolGrid.getRowColData(i,3));
			}
		}
	}
}
	//��Ҫ����ƴ��sql���
	var len=iArray.length;
	for(var i=0;i<len;i++){
		if(i < len-1){
			strPro=strPro+"'"+iArray[i]+"',";
		}else{
			strPro=strPro+"'"+iArray[i]+"')";
		}
	}
	strSQL=str+strPro;
	if(confirm("ȷ��������ѡ�е�"+getSelectChkRow+"������?")){
		try{
		arrResult1 = easyExecSql(strSQL,1,0);
		}catch(ex){
			alert(ex);
		}
		easyQueryClick();
		}else{
		return false;
	}
}
/**
 * �����������Ƿ�Ϊ�� add by lzj at 2016/05/06
 * @returns {Boolean}
 */
function isNull(){
	if($("ValidStartDate1").value==null || trim($("ValidStartDate1").value)==''){
		alert("��ѡ���������!");return false;
	}
	/*if(($("ProposalContNo").value==null||trim($("ProposalContNo").value)=='') && ($("ValidStartDate").value==null||trim($("ValidStartDate").value)=='')){
		alert("���Ƚ��в�ѯ��Ȼ����и��²���!");return false;
	}*/
	return true;
}
/**
 * ����������ڲ�Ϊ�ա�����������Ҫѡ�����ʱ���ύ��ReceiptUpdate.jsp���и��²���
 */
function submitForm(){
	if(isSelect() && isNull()){
		var showStr="�����޸����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��...";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		fm.action="ReceiptUpdate.jsp"; //�ύ��ReceiptUpdate.jsp���и��²���
		$("fm").submit(); //�ύ
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){
	//���۴�ӡ�����Σ������¼����ӡ��ť
	showInfo.close();
	if (FlagStr == "Fail" ){
		//�����ӡʧ�ܣ�չ�ִ�����Ϣ
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
	}
	easyQueryClick();
}
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}