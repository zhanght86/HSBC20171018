
<%
	//�������ƣ�itemUpdateInit.jsp
	//�����ܣ���BOM�ʹ����ĳ�ʼ��
	//�������ڣ�2008-8-13
	//������  ��
	//���¼�¼��
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
	var tResourceNameItem = 'ibrms.itemUpdateSql';
	function initInpBox() {
		try {
			var ItemEName = document.all("ItemEName").value;
			var bomName = document.all("bomName").value;
		//	var itemsql = "select * from lrbomitem where name='" + ItemEName
		//			+ "' and bomname='" + bomName + "'";
			var sqlid1="itemUpdateSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceNameItem); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(ItemEName);//ָ������Ĳ���
		mySql1.addSubPara(bomName);//ָ������Ĳ���
	  var strSql=mySql1.getString();	
	  
			//prompt(this,itemsql);
			var arrResult = easyExecSql(strSql, 1, 0);
			var i = arrResult.length;
			if (arrResult == null) {
				return;
			} else {
				for (j = 0; j < i; j++) {
					document.all('ItemEName').value = arrResult[j][0];
					document.all('bomName').value = arrResult[j][1];
					document.all('CNName').value = arrResult[j][2];
					document.all('Connector').value = arrResult[j][3];
					document.all('IsHierarchical').value = arrResult[j][4];
					document.all('IsBase').value = arrResult[j][5];
					document.all('CommandType').value = arrResult[j][8];
					document.all('SourceType').value = arrResult[j][6];
					document.all('Source').value = arrResult[j][7];
					// ����ԤУ��ĳ�ʼ��
					document.all('PreCheck').value = arrResult[j][9];
						document.all('Valid').value = arrResult[j][10];
					document.all('Description').value = arrResult[j][11];
					var CommandType = arrResult[j][8];
					if (CommandType == '') {
						//temitemsql = "select code1,codename from ldcode1 where 1=2";
						document.all('PreCheck').CodeData = '';
					} else {
						//temitemsql = "select code1,codename from ldcode1 where code='"
							//	+ CommandType + "'";
								var sqlid2="itemUpdateSql2";
								var mySql2=new SqlClass();
								mySql2.setResourceName(tResourceNameItem); //ָ��ʹ�õ�properties�ļ���
								mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
								mySql2.addSubPara(CommandType);//ָ������Ĳ���
							  var strSql2=mySql2.getString();	
					
					//prompt(this,temitemsql);
						var tem = easyQueryVer3(strSql2, 1, 1, 1);
						if (tem == false) {
							document.all('PreCheck').CodeData = '';
						} else {
							document.all('PreCheck').CodeData = tem;
						}
				}
				if(document.all('PreCheck').value!='')
				{
					var sqlid3="itemUpdateSql3";
					var mySql3=new SqlClass();
					mySql3.setResourceName(tResourceNameItem); //ָ��ʹ�õ�properties�ļ���
					mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
					mySql3.addSubPara(document.all('CommandType').value);//ָ������Ĳ���
					mySql3.addSubPara(document.all('PreCheck').value);//ָ������Ĳ���
					 var strSql3=mySql3.getString();	
					//var temsql = "select codename from ldcode1 where code='"
					//		+ arrResult[j][8] + "' and code1='"
					//		+ arrResult[j][9] + "'";
					//prompt(this,temsql);
					var temResult = easyExecSql(strSql3, 1, 0);
					if (temResult != null) {
							document.all('ibrmsPreCheckName').value = temResult[0][0];
					} else {
							document.all('ibrmsPreCheckName').value = '';
					}

				}
			}
		}

		} catch (ex) {

		}
	}

	function initSelBox() {
		try {

		} catch (ex) {
			alert("��itemUpdateInit.jsp   InitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm1(viewFlag) {
		try {
			//alert('1');
			initInpBox();
			//alert('2');
			initSelBox();
			//alert('3');
			initButton(viewFlag)
		} catch (re) {
			alert("itemUpdateInit.jsp   InitForm�����з����쳣:��ʼ���������!");
		}
	}

	function initButton(viewFlag) {
		if (viewFlag == 'view') {
			document.all("ok").style.display = "none";
			document.all("ret").style.display = "none";
			document.all("close").style.display = "";			
		} else {
			document.all("ok").style.display = "";
			document.all("ret").style.display = "";
			document.all("close").style.display = "none";
		}
	}
</script>
