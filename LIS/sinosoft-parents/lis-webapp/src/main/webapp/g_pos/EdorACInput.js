/***************************************************************
 * <p>ProName:EdorACInput.js</p>
 * <p>Title:  Ͷ�������ϱ��</p>
 * <p>Description:Ͷ�������ϱ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();
var tContType = "01";

function selectIDInfo() {

	if(divIDInfo.style.display=='none') {
		divIDInfo.style.display = '';
	} else {
		divIDInfo.style.display = 'none';
	}
}
function selectPeopleInfo(){

	if(fm.LinkMan.value=='' || fm.Phone2.value ==''){
		alert("����¼����ϵ����Ϣ���ܹ�ѡ��");
		fm.TooContect.checked=false;
		return false;
	}

	if(divLinkPeopleInfo.style.display=='none') {
		divLinkPeopleInfo.style.display = '';
	} else {
		divLinkPeopleInfo.style.display = 'none';
	}
}

//ԭ����������Ϣ��ѯ
function queryOldClick(){

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorACSql");
	tSQLInfo.setSqlId("EdorACSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);

	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tPropEntry==null) {
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorACSql");
		tSQLInfo.setSqlId("EdorACSql1");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tEdorNo);

		var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

		if(arrResult != null){

			if(arrResult[0][0]=='POS'){
			
				// ��ѯͶ������Ϣ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql3");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
				
					fm.GrpName.value=tPropEntry[0][0];
					fm.SumNumPeople.value=tPropEntry[0][1];
					fm.MainBusiness.value=tPropEntry[0][2];
					fm.GrpNature.value=tPropEntry[0][3];
					fm.GrpNatureName.value=tPropEntry[0][4];
					fm.BusiCategory.value=tPropEntry[0][5];
					fm.BusiCategoryName.value=tPropEntry[0][6];
					fm.SocialInsuCode.value=tPropEntry[0][7];
					fm.Phone1.value=tPropEntry[0][8];
					fm.Fax.value=tPropEntry[0][9];
					fm.Corporation.value=tPropEntry[0][10];
					fm.CorIDType.value=tPropEntry[0][11];
					fm.CorIDTypeName.value=tPropEntry[0][12];
					fm.CorID.value=tPropEntry[0][13];
					fm.CorIDExpiryDate.value=tPropEntry[0][14];
					fm.AppntNo.value=tPropEntry[0][15];
				}

				//��ʼ��ʡ����
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql7");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
				
					fm.ProvinceCode.value=tPropEntry[0][1];
					fm.ProvinceName.value=tPropEntry[0][2];
					fm.CityCode.value=tPropEntry[0][3];
					fm.CityName.value=tPropEntry[0][4];
					fm.CountyCode.value=tPropEntry[0][5];
					fm.CountyName.value=tPropEntry[0][6];
					fm.DetailAddress.value=tPropEntry[0][7];
					fm.ZipCode.value=tPropEntry[0][8];
				}

				//��ʼ����ϵ����Ϣ
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql9");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
			
					fm.LinkMan.value=tPropEntry[0][0];
					fm.Phone2.value=tPropEntry[0][1];
					fm.EMail.value=tPropEntry[0][2];
					fm.MobilePhone.value=tPropEntry[0][3];
					fm.Department.value=tPropEntry[0][4];
					fm.IDType.value=tPropEntry[0][5];
					fm.IDTypeName.value=tPropEntry[0][6];
					fm.IDNo.value=tPropEntry[0][7];
					fm.IDEndDate.value=tPropEntry[0][8];
				}

				//������Ϣ��ʼ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql19");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);

				var kPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(kPropEntry != null){

					fm.AccName.value=kPropEntry[0][2];
					fm.BankAccNo.value=kPropEntry[0][3];
					fm.PayType.value=kPropEntry[0][4];
					fm.PayTypeName.value=kPropEntry[0][5];

					tSQLInfo = new SqlClass();
					tSQLInfo.setResourceName("g_pos.EdorACSql");
					tSQLInfo.setSqlId("EdorACSql20");
					tSQLInfo.addSubPara(tGrpContNo);
					tSQLInfo.addSubPara(arrResult[0][1]);
					tSQLInfo.addSubPara(arrResult[0][4]);

					var kPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
					if(kPropEntry != null){
						fm.HeadBankCode.value=kPropEntry[0][0];
						fm.BankCodeName.value=kPropEntry[0][1];
						fm.BankProvince.value=kPropEntry[0][2];
						fm.BankProvinceName.value=kPropEntry[0][3];
						fm.BankCity.value=kPropEntry[0][4];
						fm.BankCityName.value=kPropEntry[0][5];

					}
				}

				//��λ֤��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql21");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);
				tSQLInfo.addSubPara("00");

				var tIDRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(tIDRes != null){
				
					fm.GrpIDType.value=tIDRes[0][0];
					fm.GrpIDTypeName.value=tIDRes[0][1];
					fm.GrpID.value=tIDRes[0][2];
					fm.GrpIDExpiryDate.value=tIDRes[0][4];
				}

				//��λ֤��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql21");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);
				tSQLInfo.addSubPara("01");

				turnPage5.queryModal(tSQLInfo.getString(), IDInfoGrid, 1, 1);
				if(turnPage5.strQueryResult==false){
				
					fm.IDInfo.checked=false;
					divIDInfo.style.display='none';
				}else {
					
					fm.IDInfo.checked=true;
					divIDInfo.style.display='';
				}

				//����ϵ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql15");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(arrResult[0][1]);
				tSQLInfo.addSubPara(arrResult[0][4]);

				turnPage6.queryModal(tSQLInfo.getString(), TooContectGrid, 1, 1);
				
				if(turnPage6.strQueryResult==false){
					
						fm.TooContect.checked=false;
						divLinkPeopleInfo.style.display='none';
					}else {
						
						fm.TooContect.checked=true;
						divLinkPeopleInfo.style.display='';
					}
			} else if(arrResult[0][0]=='NB') {

				// Ͷ������Ϣ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql4");
				tSQLInfo.addSubPara(arrResult[0][1]);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry!=null){
				
					fm.GrpName.value=tPropEntry[0][0];
					fm.SumNumPeople.value=tPropEntry[0][1];
					fm.MainBusiness.value=tPropEntry[0][2];
					fm.GrpNature.value=tPropEntry[0][3];
					fm.GrpNatureName.value=tPropEntry[0][4];
					fm.BusiCategory.value=tPropEntry[0][5];
					fm.BusiCategoryName.value=tPropEntry[0][6];
					fm.SocialInsuCode.value=tPropEntry[0][7];
					fm.Phone1.value=tPropEntry[0][8];
					fm.Fax.value=tPropEntry[0][9];
					fm.Corporation.value=tPropEntry[0][10];
					fm.CorIDType.value=tPropEntry[0][11];
					fm.CorIDTypeName.value=tPropEntry[0][12];
					fm.CorID.value=tPropEntry[0][13];
					fm.CorIDExpiryDate.value=tPropEntry[0][14];
					fm.AppntNo.value=tPropEntry[0][15];
				}

					//��ʼ��ʡ����
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql5");
				tSQLInfo.addSubPara(tGrpContNo);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
					
					fm.ProvinceCode.value=tPropEntry[0][1];
					fm.ProvinceName.value=tPropEntry[0][2];
					fm.CityCode.value=tPropEntry[0][3];
					fm.CityName.value=tPropEntry[0][4];
					fm.CountyCode.value=tPropEntry[0][5];
					fm.CountyName.value=tPropEntry[0][6];
					fm.DetailAddress.value=tPropEntry[0][7];
					fm.ZipCode.value=tPropEntry[0][8];
				}

				//��ʼ����ϵ����Ϣ
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql6");
				tSQLInfo.addSubPara(tGrpContNo);
				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
				
					fm.LinkMan.value=tPropEntry[0][0];
					fm.Phone2.value=tPropEntry[0][1];
					fm.EMail.value=tPropEntry[0][2];
					fm.MobilePhone.value=tPropEntry[0][3];
					fm.Department.value=tPropEntry[0][4];
					fm.IDType.value=tPropEntry[0][5];
					fm.IDTypeName.value=tPropEntry[0][6];
					fm.IDNo.value=tPropEntry[0][7];
					fm.IDEndDate.value=tPropEntry[0][8];
				}

				//������Ϣ��ʼ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql18");
				tSQLInfo.addSubPara(tGrpContNo);

				var kPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(kPropEntry !=null){
				
					fm.AccName.value=kPropEntry[0][1];
					fm.BankAccNo.value=kPropEntry[0][2];
					fm.PayType.value=kPropEntry[0][3];
					fm.PayTypeName.value=kPropEntry[0][4];

					if(kPropEntry[0][0]!=""){
						tSQLInfo = new SqlClass();
						tSQLInfo.setResourceName("g_pos.EdorACSql");
						tSQLInfo.setSqlId("EdorACSql17");
						tSQLInfo.addSubPara(kPropEntry[0][0]);

						var kBankEntry =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);

						if(kBankEntry != null){
								fm.HeadBankCode.value=kBankEntry[0][0];
								fm.BankCodeName.value=kBankEntry[0][1];
								fm.BankProvince.value=kBankEntry[0][2];
								fm.BankProvinceName.value=kBankEntry[0][3];
								fm.BankCity.value=kBankEntry[0][4];
								fm.BankCityName.value=kBankEntry[0][5];
						}
					}
				}

				//��λ֤��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql13");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara("00");

				var tIDRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(tIDRes != null){
					fm.GrpIDType.value=tIDRes[0][0];
					fm.GrpIDTypeName.value=tIDRes[0][1];
					fm.GrpID.value=tIDRes[0][2];
					fm.GrpIDExpiryDate.value=tIDRes[0][4];
				}

				//��λ֤��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql13");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara("01");
				turnPage5.queryModal(tSQLInfo.getString(), IDInfoGrid, 1, 1);

				if(!turnPage5.strQueryResult){
					initIDInfoGrid();
					fm.IDInfo.checked=false;
					divIDInfo.style.display='none';
				}else{
					fm.IDInfo.checked=true;
					divIDInfo.style.display='';
				}

				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql14");
				tSQLInfo.addSubPara(tGrpContNo);
				turnPage6.queryModal(tSQLInfo.getString(), TooContectGrid, 1, 1);

				if(!turnPage6.strQueryResult){
					initTooContectGrid();
					fm.TooContect.checked=false;
					divLinkPeopleInfo.style.display='none';
				}else{
					fm.TooContect.checked=true;
					divLinkPeopleInfo.style.display='';
				}
			}
		}
	}else {

			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorACSql");
			tSQLInfo.setSqlId("EdorACSql3");
			tSQLInfo.addSubPara(tGrpContNo);
			tSQLInfo.addSubPara(tEdorNo);
			tSQLInfo.addSubPara(tEdorType);

			var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

			if(tPropEntry != null){
				fm.GrpName.value=tPropEntry[0][0];
				fm.SumNumPeople.value=tPropEntry[0][1];
				fm.MainBusiness.value=tPropEntry[0][2];
				fm.GrpNature.value=tPropEntry[0][3];
				fm.GrpNatureName.value=tPropEntry[0][4];
				fm.BusiCategory.value=tPropEntry[0][5];
				fm.BusiCategoryName.value=tPropEntry[0][6];
				fm.SocialInsuCode.value=tPropEntry[0][7];
				fm.Phone1.value=tPropEntry[0][8];
				fm.Fax.value=tPropEntry[0][9];
				fm.Corporation.value=tPropEntry[0][10];
				fm.CorIDType.value=tPropEntry[0][11];
				fm.CorIDTypeName.value=tPropEntry[0][12];
				fm.CorID.value=tPropEntry[0][13];
				fm.CorIDExpiryDate.value=tPropEntry[0][14];
				fm.AppntNo.value=tPropEntry[0][15];
			}

			//��ʼ��ʡ����
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql7");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
					fm.ProvinceCode.value=tPropEntry[0][1];
					fm.ProvinceName.value=tPropEntry[0][2];
					fm.CityCode.value=tPropEntry[0][3];
					fm.CityName.value=tPropEntry[0][4];
					fm.CountyCode.value=tPropEntry[0][5];
					fm.CountyName.value=tPropEntry[0][6];
					fm.DetailAddress.value=tPropEntry[0][7];
					fm.ZipCode.value=tPropEntry[0][8];
				}

				//��ʼ����ϵ����Ϣ
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql9");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);

				var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(tPropEntry != null){
					fm.LinkMan.value=tPropEntry[0][0];
					fm.Phone2.value=tPropEntry[0][1];
					fm.EMail.value=tPropEntry[0][2];
					fm.MobilePhone.value=tPropEntry[0][3];
					fm.Department.value=tPropEntry[0][4];
					fm.IDType.value=tPropEntry[0][5];
					fm.IDTypeName.value=tPropEntry[0][6];
					fm.IDNo.value=tPropEntry[0][7];
					fm.IDEndDate.value=tPropEntry[0][8];
				}

				//������Ϣ��ʼ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql19");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);

				var kPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

				if(kPropEntry != null){

					fm.AccName.value=kPropEntry[0][1];
					fm.BankAccNo.value=kPropEntry[0][2];
					fm.PayType.value=kPropEntry[0][3];
					fm.PayTypeName.value=kPropEntry[0][4];

					tSQLInfo = new SqlClass();
					tSQLInfo.setResourceName("g_pos.EdorACSql");
					tSQLInfo.setSqlId("EdorACSql20");
					tSQLInfo.addSubPara(tGrpContNo);
					tSQLInfo.addSubPara(tEdorNo);
					tSQLInfo.addSubPara(tEdorType);

					var kPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
					if(kPropEntry != null){
						fm.HeadBankCode.value=kPropEntry[0][0];
						fm.BankCodeName.value=kPropEntry[0][1];
						fm.BankProvince.value=kPropEntry[0][2];
						fm.BankProvinceName.value=kPropEntry[0][3];
						fm.BankCity.value=kPropEntry[0][4];
						fm.BankCityName.value=kPropEntry[0][5];
					}
				}

				//��λ֤��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql21");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);
				tSQLInfo.addSubPara("00");

				var tIDRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				if(tIDRes != null){
					fm.GrpIDType.value=tIDRes[0][0];
					fm.GrpIDTypeName.value=tIDRes[0][1];
					fm.GrpID.value=tIDRes[0][2];
					fm.GrpIDExpiryDate.value=tIDRes[0][4];
				}

				//��λ֤��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql21");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);
				tSQLInfo.addSubPara("01");

				turnPage5.queryModal(tSQLInfo.getString(), IDInfoGrid, 1, 1);
				if(turnPage5.strQueryResult==false){
					fm.IDInfo.checked=false;
					divIDInfo.style.display='none';
				}else {
					fm.IDInfo.checked=true;
					divIDInfo.style.display='';
				}

				//����ϵ��
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_pos.EdorACSql");
				tSQLInfo.setSqlId("EdorACSql15");
				tSQLInfo.addSubPara(tGrpContNo);
				tSQLInfo.addSubPara(tEdorNo);
				tSQLInfo.addSubPara(tEdorType);
				turnPage6.queryModal(tSQLInfo.getString(), TooContectGrid, 1, 1);

				if(turnPage6.strQueryResult==false){
						fm.TooContect.checked=false;
						divLinkPeopleInfo.style.display='none';
					}else {
						fm.TooContect.checked=true;
						divLinkPeopleInfo.style.display='';
					}
		}
}


//����
function saveClick(){

	IDInfoGrid.delBlankLine();
	TooContectGrid.delBlankLine();
	if(!verifyForm("fm")){
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.EdorACSql");
	tSQLInfo.setSqlId("EdorACSql22");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara("110000");
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tPropEntry!=null && tPropEntry[0][0]=="1") {
		if(fm.SocialInsuCode.value==''){
			alert("�������Ϊ�����ֹ�˾�ı�������ᱣ�յǼǺš�����Ϊ��!");
			return false;
		}
	}

	if(fm.CorIDType.value !=''){
		if(fm.CorID.value ==''){
			alert("��¼�뷨��֤������!");
			return false;
		}
	}

	if(fm.IDType.value !=''){
		if(fm.IDNo.value ==''){
			alert("��¼����ϵ��֤������!");
			return false;
		}
	}
	if(fm.DetailAddress.value ==""){
			alert("��¼�������ĵ�λ��ַ��Ϣ��");
			return false;
	}
	if(fm.ProvinceCode.value !=""){
		if(fm.CityCode.value =="" || fm.CountyCode.value ==""|| fm.DetailAddress.value==""){
			alert("��ϸ��ַ����¼���к���/��/��ϸ��Ϣ��");
			return false;
		}
		if(fm.DetailAddress.value ==""){
			alert("��¼�������ĵ�λ��ַ��Ϣ��");
			return false;
		}
	}
	
	// У����ϸ��ַ��Ϣ¼��
	if(!checkCity()){
		return false;
	}

	// У��������Ϣ
		var tflag1 = true;
		var bankFlag = 0;
		if(fm.HeadBankCode.value!=null || fm.HeadBankCode.value!=""){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContCommonSql");
			tSQLInfo.setSqlId("LCContCommonSql9");
			tSQLInfo.addSubPara(fm.HeadBankCode.value);
			bankFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		}
		if(bankFlag =='1'){
			if(fm.HeadBankCode.value==''&& fm.BankProvince.value==''&& fm.BankCity.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
				tflag1= true;
			}else if(fm.HeadBankCode.value !=''&& fm.BankProvince.value!=''&& fm.BankCity.value!='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
				tflag1= true;
			}else{
				tflag1=false;
			}
		
		} else {
			if(fm.HeadBankCode.value=='' && fm.AccName.value=='' && trim(fm.BankAccNo.value)==''){
				tflag1= true;
			}else if(fm.HeadBankCode.value !='' && fm.AccName.value!='' && trim(fm.BankAccNo.value)!=''){
				tflag1= true;
			}else{
				tflag1=false;
			}
		}
		if(tflag1==false){
			alert("����д���������Ϣ��");
			return false;
		
		}
		
	// У�鹴ѡ��λ֤����Ϣ
	if(fm.IDInfo.checked){
		IDInfoGrid.delBlankLine();
		var count = IDInfoGrid.mulLineCount;
		if(parseInt(count)==0){
			alert("����д��λ֤����ϸ��Ϣ��");
			return false;
		}
		// У�鵥λ֤����Ϣ
		if(!valIDNOInfo()){
			return false;
		}
	}

	// У�����ϵ���빴ѡ
	if(fm.TooContect.checked){
		TooContectGrid.delBlankLine();
		var count = TooContectGrid.mulLineCount;
		if(parseInt(count)==0){
			alert("����д��ϵ����ϸ��Ϣ��");
			return false;
		}
		// У�����ϵ����Ϣ
		if(!valTooConterInfo()){
			return false;
		}
	}

	if(fm.CorIDExpiryDate.value != ""){
		if(fm.CorIDExpiryDate.value <tCurrentDate){
			alert("����֤����Чֹ�ڱ�����ڵ�ǰ���ڣ�");
			return false;
		}
	}

	if(fm.IDEndDate.value !=""){
		if(fm.IDEndDate.value <tCurrentDate){
			alert("��ϵ��֤����Чֹ�ڱ�����ڵ�ǰ���ڣ�");
			return false;
		}
	}

	mOperate="SAVE";
	fm.action = "./EdorACSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
	fm.submit();
}
function submitFunc(){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {

	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}

	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}
/**
 * ѡ�����ǰ������ѡ��ʡ��
 */
function checkProvince(){

	if(fm.ProvinceName.value == ""){
		alert("����ѡ��ʡ��");
		fm.City.value = "";
		fm.CityName.value = "";
	}
}
function clearCity(){

	fm.City.value = "";
	fm.CityName.value = "";
}
function clearCityAndCounty(){

	fm.CityName.value = "";
	fm.CityCode.value = "";
	fm.CountyName.value = "";
	fm.CountyCode.value = "";
}

/**
 * ��չ�����ѯ����Ŀ

 */
function clearInput(codeInput,nameInput) {
	codeInput.value = "";
	nameInput.value = "";
}

/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=='bankprovince') {

		if (returnType=='0') {
			return showCodeList('province',value2,value3,null,null,null,'1',180);
		} else {
			return showCodeListKey('province',value2,value3,null,null,null,'1',180);
		}

	} else if (value1=='bankcity') {
		
		if (isEmpty(fm.BankProvince)) {
			alert("��ѡ��ʡ��");
			return false;
		}
		
		var tProvince = fm.BankProvince.value;
		
		if (returnType=='0') {
			return showCodeList('city',value2,value3,null,tProvince,'upplacename','1',180);
		} else {
			return showCodeListKey('city',value2,value3,null,tProvince,'upplacename','1',180);
		}
	} else if (value1=='city') {

		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}

		var tProvince = fm.ProvinceCode.value;

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tProvince,'upplacename','1',180);
		} else {
			return showCodeListKey(value1,value2,value3,null,tProvince,'upplacename','1',180);
		}
	} else if (value1=='district') {

		if (isEmpty(fm.ProvinceName)) {
			alert("��ѡ��ʡ��");
			return false;
		}

		if (isEmpty(fm.CityName)) {
			alert("��ѡ���У�");
			return false;
		}

		var tCity = fm.CityCode.value;

		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tCity,'upplacename','1',180);
		} else {
			return showCodeListKey(value1,value2,value3,null,tCity,'upplacename','1',180);
		}
	}
}

function checkCoridtype(){

	if(fm.CorID.value.length>0 && fm.CorIDType.value=="") {
	 	 alert("����ѡ��֤�����ͣ�");
		 return false;
	}

	if(fm.CorIDType.value=="0"&&fm.CorID.value.length>0) {

		if((fm.CorID.value.length!=15) &&(fm.CorID.value.length!=18)){
			 alert("��������֤��λ������");
			 document.all('CorID').value="";
			 return false;
		}
		}
		if(!checkIdCard(fm.CorID.value)) {
			 document.all('CorID').value="";
			 document.all('CorID').className = "warn";
			 return false;
		}
	}



/**
 * У�����֤����
 */
function checkidtype(){

	if(fm.IDNo.value.length>0 && fm.IDType.value=="") {
		 alert("����ѡ��֤�����ͣ�");
		return false;
	}

	if(fm.IDType.value=="0"&&fm.IDNo.value.length>0) {

		if((fm.IDNo.value.length!=15) &&(fm.IDNo.value.length!=18)){
			alert("��������֤��λ������");
			document.all('IDNo').value="";
			return false;
		}

		if(!checkIdCard(fm.LinkID.value)) {

		document.all('IDNo').value="";
		document.all('IDNo').className = "warn";
		return false;
		}
	}
}

/**
 * У�鵥λ֤������
 */
function valIDNOInfo(){
	var tCount = IDInfoGrid.mulLineCount;
	if(parseInt(tCount)>0){
		if(!IDInfoGrid.checkValue("IDInfoGrid")){
			return false;
		}

	for (var i=0;i<tCount;i++){
			if(IDInfoGrid.getRowColData(i,1) !=''){
				if(IDInfoGrid.getRowColData(i,3)==''){
					alert("��"+(i+1)+"�С�֤�����ͱ��롿��Ϊ����¼�롾֤�����롿��");
					return false;
				}
			}else {
				if(IDInfoGrid.getRowColData(i,3)!=''){
					alert("��"+(i+1)+"�С�֤�����롿��Ϊ����¼�롾֤�����ͱ��롿��");
					IDInfoGrid.getRowColData(i,3)="";
					return false;
				}
			}

			if (IDInfoGrid.getRowColData(i,1)==fm.GrpIDType.value) {
				alert("��"+(i+1)+"�С�֤�����ͱ��롿��¼��ġ���λ֤�����͡��ظ�������е�����");
				return false;
			}

			if(IDInfoGrid.getRowColData(i,5) !=''){
				if(IDInfoGrid.getRowColData(i,5)<tCurrentDate){
					alert("��"+(i+1)+"�е�λ֤����Чֹ������ڵ�ǰ����");
					return false;
				}
			}

			for (var j=0;j<i;j++) {

				if(IDInfoGrid.getRowColData(i,1)== IDInfoGrid.getRowColData(j,1)){
					alert("��["+(i+1)+"]��֤�����ͱ������["+(j+1)+"]��֤�����ͱ����ظ���");
					return false;
				}
			}
		}
	}

	return true;
}

/**
 * У�����ϵ����Ϣ
 */
function valTooConterInfo(){
	var tCount = TooContectGrid.mulLineCount;

	if(parseInt(tCount)>0){
		if(!TooContectGrid.checkValue("TooContectGrid")){
			return false;
		}

		for (var i=0; i<tCount;i++){

			var tTooName = TooContectGrid.getRowColData(i,1);
			var tTooPHONE = TooContectGrid.getRowColData(i,2);

			if(TooContectGrid.getRowColData(i,6) !=''){
				if(TooContectGrid.getRowColData(i,7)==''){
					alert("��"+(i+1)+"�ж���ϵ�ˡ�֤�����͡���Ϊ����¼�롾֤�����롿��");
					return false;
				}
				if(TooContectGrid.getRowColData(i,6)=='0'){
					if(TooContectGrid.getRowColData(i,7).length!='15' && TooContectGrid.getRowColData(i,7).length!='18'){
						alert("��"+(i+1)+"�ж���ϵ�ˡ����֤���롿����Ϊ15����18λ��");
						return false;
					}
				}
			}

			if(TooContectGrid.getRowColData(i,7)!=''){
				if(TooContectGrid.getRowColData(i,6)==''){
					alert("��"+(i+1)+"�ж���ϵ�ˡ�֤�����롿��Ϊ����¼�롾֤�����͡���");
					TooContectGrid.getRowColData(i,6)='';
					return false;
				}
			}

			if(TooContectGrid.getRowColData(i,8)!=''){

				if(TooContectGrid.getRowColData(i,8)<tCurrentDate){
					alert("��"+(i+1)+"�ж���ϵ����Чֹ������ڵ�ǰ����");
					return false;
				}
			}



			//��Ա�ظ���У��
		for(var j=i+1;j<tCount;j++){
			var mTooName = TooContectGrid.getRowColData(j,1);
			var mTooPHONE = TooContectGrid.getRowColData(j,2);
				if (tTooName==mTooName && tTooPHONE==mTooPHONE) {
					alert("��["+(i+1)+"]����ϵ�����["+(j+1)+"]����ϵ���ظ���");
					return false;
				}
			}
		}

	}
	return true;
}

 /**
 * У����ϸ��ַ��Ϣ
 */
function checkCity(){
	
	var ProvinceCode =document.all('ProvinceCode').value;
	var CityCode =document.all('CityCode').value;
	var CountyCode =document.all('CountyCode').value;

	if(ProvinceCode !=""){
		if(CityCode ==''){
			CityCode ='0';
		}
		if(CountyCode ==''){
			CountyCode ='0';
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorBBSql");
		tSQLInfo.setSqlId("EdorBBSql4");
		tSQLInfo.addSubPara(ProvinceCode);
		tSQLInfo.addSubPara(CityCode);
		tSQLInfo.addSubPara(CountyCode);

		var arrResult = easyExecSql(tSQLInfo.getString());
		if(arrResult =='0'){
			alert("��ϵ��ַ�����ڻ��߹�������ȷ");
			return false;
		}
	}
	return true;
}
/**
** �������Ͷ������
**/
function saveAppClick(){
	if(!verifyForm2("fm2")){
		return false;
	}
	if(!checkidtype()){
		return false;
	}
	if(!checkIDInfo()){
		return false;
	}
	if(""==fm2.AppntName.value &&""==fm2.IDType.value && ""==fm2.IDNo.value && ""==fm2.InsuredGender.value && ""==fm2.InsuredBirthDay.value && ""==fm2.CorIDExpiryDate.value){
		alert("δ¼���κ�Ͷ������Ϣ��");
		return false;
	}
	mOperate="SAVEAPP";
	fm2.action = "./EdorACSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
	fm2.submit();
}

function checkidtype(){
	if(fm2.IDType.value==""){
		fm2.IDNo.value="";
		fm2.IDType.focus();
		alert("����¼��֤�����ͣ�");
		return false;
	}
	if(fm2.IDType.value=="0"&&fm2.IDNo.value=="") {
		alert("��¼�����֤��");
		return false;
	}
	if(fm2.IDType.value=="0"&&fm2.IDNo.value.length>0) {

		if((fm2.IDNo.value.length!=15) &&(fm2.IDNo.value.length!=18)){
			alert("��������֤��λ������");
			fm2.all('IDNo').value="";
			return false;
		}
		if(!checkIdCard(fm2.IDNo.value)) {
			fm2.all('IDNo').value="";
			fm2.all('IDNo').className = "warn";
			return false;
		}
		fm2.InsuredBirthDay.value =getBirthdatByIdNo(fm2.all('IDNo').value);
		fm2.InsuredGender.value = getSex(fm2.all('IDNo').value);

		if(fm2.InsuredGender.value=='0'){
			fm2.InsuredGenderName.value ='��';
		}else if(fm2.InsuredGender.value=='1'){
			fm2.InsuredGenderName.value ='Ů';
		}
		fmfra.IDGender.value = fm2.InsuredGender.value;
		fmfra.IDBirthDay.value = fm2.InsuredBirthDay.value;
		fmfra.IDFlag.value = "1";
	}else{
		fmfra.IDFlag.value = "0";
	}
	return true;
}
//������֤���Ա𡢳�������
function checkIDInfo(){
	if(fmfra.IDFlag.value == "1"){
		if(fmfra.IDGender.value != fm2.InsuredGender.value){
			alert("¼����Ա�����֤�Ա�һ�£�");
			return false;
		}
		if(fmfra.IDBirthDay.value != fm2.InsuredBirthDay.value){
			alert("¼��ĳ������ں����֤�������ڲ�һ�£�");
			return false;
		}
	}
	return true;
}
/**
*��ѯ��ʷ����
*/
function queryAppInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorACSql");
	tSQLInfo.setSqlId("EdorACSql24");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	var tAppRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tAppRes!=null && tAppRes[0][0]!="") {
		fm2.IAppntNo.value= tAppRes[0][5];
		fm2.AppntName.value= tAppRes[0][6];
		fm2.InsuredGender.value= tAppRes[0][7];
		fm2.InsuredGenderName.value= tAppRes[0][8];
		fm2.IDType.value= tAppRes[0][9];
		fm2.IDTypeName.value= tAppRes[0][10];
		fm2.IDNo.value= tAppRes[0][11];
		fm2.CorIDExpiryDate.value= tAppRes[0][12];
		fm2.InsuredBirthDay.value= tAppRes[0][13];
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorACSql");
	tSQLInfo.setSqlId("EdorACSql25");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	var tAppRes2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	if (tAppRes2!=null && tAppRes2[0][0]!="") {
		fm2.IAppntNo.value= tAppRes2[0][5];
		fm2.AppntName.value= tAppRes2[0][6];
		fm2.InsuredGender.value= tAppRes2[0][7];
		fm2.InsuredGenderName.value= tAppRes2[0][8];
		fm2.IDType.value= tAppRes2[0][9];
		fm2.IDTypeName.value= tAppRes2[0][10];
		fm2.IDNo.value= tAppRes2[0][11];
		fm2.CorIDExpiryDate.value= tAppRes2[0][12];
		fm2.InsuredBirthDay.value= tAppRes2[0][13];
	}
}
/**
 * ������ѡ�����
 */
function afterCodeSelect(tSelectValue, tObj) {
	
	if(tSelectValue == "province"){
		if(tObj.name=="BankProvince"){
			fm.BankCity.value="";
			fm.BankCityName.value="";
		}else {

			fm.CityName.value="";
			fm.CityCode.value="";
			fm.CountyName.value="";
			fm.CountyCode.value="";
		}
	} else if(tSelectValue == "city"){

		if(tObj.name=="BankCity"){

		}else {
			fm.CountyName.value="";
			fm.CountyCode.value="";
		}
	}
}
