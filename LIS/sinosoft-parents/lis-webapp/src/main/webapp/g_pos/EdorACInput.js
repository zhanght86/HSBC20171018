/***************************************************************
 * <p>ProName:EdorACInput.js</p>
 * <p>Title:  投保人资料变更</p>
 * <p>Description:投保人资料变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
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
var mOperate = "";//操作状态
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
		alert("请先录入联系人信息才能勾选！");
		fm.TooContect.checked=false;
		return false;
	}

	if(divLinkPeopleInfo.style.display=='none') {
		divLinkPeopleInfo.style.display = '';
	} else {
		divLinkPeopleInfo.style.display = 'none';
	}
}

//原被保险人信息查询
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
			
				// 查询投保人信息表
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

				//初始化省市县
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

				//初始化联系人信息
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

				//银行信息初始化
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

				//单位证件
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

				//单位证件
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

				//多联系人
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

				// 投保人信息表
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

					//初始化省市县
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

				//初始化联系人信息
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

				//银行信息初始化
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

				//单位证件
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

				//单位证件
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

			//初始化省市县
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

				//初始化联系人信息
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

				//银行信息初始化
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

				//单位证件
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

				//单位证件
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

				//多联系人
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


//保存
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
			alert("管理机构为北京分公司的保单【社会保险登记号】不能为空!");
			return false;
		}
	}

	if(fm.CorIDType.value !=''){
		if(fm.CorID.value ==''){
			alert("请录入法人证件号码!");
			return false;
		}
	}

	if(fm.IDType.value !=''){
		if(fm.IDNo.value ==''){
			alert("请录入联系人证件号码!");
			return false;
		}
	}
	if(fm.DetailAddress.value ==""){
			alert("请录入完整的单位地址信息！");
			return false;
	}
	if(fm.ProvinceCode.value !=""){
		if(fm.CityCode.value =="" || fm.CountyCode.value ==""|| fm.DetailAddress.value==""){
			alert("详细地址必须录入市和区/县/详细信息！");
			return false;
		}
		if(fm.DetailAddress.value ==""){
			alert("请录入完整的单位地址信息！");
			return false;
		}
	}
	
	// 校验详细地址信息录入
	if(!checkCity()){
		return false;
	}

	// 校验银行信息
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
			alert("请填写银行相关信息！");
			return false;
		
		}
		
	// 校验勾选单位证件信息
	if(fm.IDInfo.checked){
		IDInfoGrid.delBlankLine();
		var count = IDInfoGrid.mulLineCount;
		if(parseInt(count)==0){
			alert("请填写单位证件详细信息！");
			return false;
		}
		// 校验单位证件信息
		if(!valIDNOInfo()){
			return false;
		}
	}

	// 校验多联系人请勾选
	if(fm.TooContect.checked){
		TooContectGrid.delBlankLine();
		var count = TooContectGrid.mulLineCount;
		if(parseInt(count)==0){
			alert("请填写联系人详细信息！");
			return false;
		}
		// 校验多联系人信息
		if(!valTooConterInfo()){
			return false;
		}
	}

	if(fm.CorIDExpiryDate.value != ""){
		if(fm.CorIDExpiryDate.value <tCurrentDate){
			alert("法人证件有效止期必须大于当前日期！");
			return false;
		}
	}

	if(fm.IDEndDate.value !=""){
		if(fm.IDEndDate.value <tCurrentDate){
			alert("联系人证件有效止期必须大于当前日期！");
			return false;
		}
	}

	mOperate="SAVE";
	fm.action = "./EdorACSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
	fm.submit();
}
function submitFunc(){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {

	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}

	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
}
/**
 * 选择城市前必须先选择省份
 */
function checkProvince(){

	if(fm.ProvinceName.value == ""){
		alert("请先选择省份");
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
 * 清空关联查询的项目

 */
function clearInput(codeInput,nameInput) {
	codeInput.value = "";
	nameInput.value = "";
}

/**
 * 模拟下拉操作
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
			alert("请选择省！");
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
			alert("请选择省！");
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
			alert("请选择省！");
			return false;
		}

		if (isEmpty(fm.CityName)) {
			alert("请选择市！");
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
	 	 alert("请先选择证件类型！");
		 return false;
	}

	if(fm.CorIDType.value=="0"&&fm.CorID.value.length>0) {

		if((fm.CorID.value.length!=15) &&(fm.CorID.value.length!=18)){
			 alert("输入的身份证号位数错误");
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
 * 校验身份证类型
 */
function checkidtype(){

	if(fm.IDNo.value.length>0 && fm.IDType.value=="") {
		 alert("请先选择证件类型！");
		return false;
	}

	if(fm.IDType.value=="0"&&fm.IDNo.value.length>0) {

		if((fm.IDNo.value.length!=15) &&(fm.IDNo.value.length!=18)){
			alert("输入的身份证号位数错误");
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
 * 校验单位证件类型
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
					alert("第"+(i+1)+"行【证件类型编码】不为空需录入【证件号码】！");
					return false;
				}
			}else {
				if(IDInfoGrid.getRowColData(i,3)!=''){
					alert("第"+(i+1)+"行【证件号码】不为空需录入【证件类型编码】！");
					IDInfoGrid.getRowColData(i,3)="";
					return false;
				}
			}

			if (IDInfoGrid.getRowColData(i,1)==fm.GrpIDType.value) {
				alert("第"+(i+1)+"行【证件类型编码】与录入的【单位证件类型】重复，请进行调整！");
				return false;
			}

			if(IDInfoGrid.getRowColData(i,5) !=''){
				if(IDInfoGrid.getRowColData(i,5)<tCurrentDate){
					alert("第"+(i+1)+"行单位证件有效止期需大于当前日期");
					return false;
				}
			}

			for (var j=0;j<i;j++) {

				if(IDInfoGrid.getRowColData(i,1)== IDInfoGrid.getRowColData(j,1)){
					alert("第["+(i+1)+"]行证件类型编码与第["+(j+1)+"]行证件类型编码重复！");
					return false;
				}
			}
		}
	}

	return true;
}

/**
 * 校验多联系人信息
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
					alert("第"+(i+1)+"行多联系人【证件类型】不为空需录入【证件号码】！");
					return false;
				}
				if(TooContectGrid.getRowColData(i,6)=='0'){
					if(TooContectGrid.getRowColData(i,7).length!='15' && TooContectGrid.getRowColData(i,7).length!='18'){
						alert("第"+(i+1)+"行多联系人【身份证号码】必须为15或者18位！");
						return false;
					}
				}
			}

			if(TooContectGrid.getRowColData(i,7)!=''){
				if(TooContectGrid.getRowColData(i,6)==''){
					alert("第"+(i+1)+"行多联系人【证件号码】不为空需录入【证件类型】！");
					TooContectGrid.getRowColData(i,6)='';
					return false;
				}
			}

			if(TooContectGrid.getRowColData(i,8)!=''){

				if(TooContectGrid.getRowColData(i,8)<tCurrentDate){
					alert("第"+(i+1)+"行多联系人有效止期需大于当前日期");
					return false;
				}
			}



			//人员重复性校验
		for(var j=i+1;j<tCount;j++){
			var mTooName = TooContectGrid.getRowColData(j,1);
			var mTooPHONE = TooContectGrid.getRowColData(j,2);
				if (tTooName==mTooName && tTooPHONE==mTooPHONE) {
					alert("第["+(i+1)+"]行联系人与第["+(j+1)+"]行联系人重复！");
					return false;
				}
			}
		}

	}
	return true;
}

 /**
 * 校验详细地址信息
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
			alert("联系地址不存在或者关联不正确");
			return false;
		}
	}
	return true;
}
/**
** 保存个单投保资料
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
		alert("未录入任何投保人信息！");
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
		alert("请先录入证件类型！");
		return false;
	}
	if(fm2.IDType.value=="0"&&fm2.IDNo.value=="") {
		alert("请录入身份证！");
		return false;
	}
	if(fm2.IDType.value=="0"&&fm2.IDNo.value.length>0) {

		if((fm2.IDNo.value.length!=15) &&(fm2.IDNo.value.length!=18)){
			alert("输入的身份证号位数错误");
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
			fm2.InsuredGenderName.value ='男';
		}else if(fm2.InsuredGender.value=='1'){
			fm2.InsuredGenderName.value ='女';
		}
		fmfra.IDGender.value = fm2.InsuredGender.value;
		fmfra.IDBirthDay.value = fm2.InsuredBirthDay.value;
		fmfra.IDFlag.value = "1";
	}else{
		fmfra.IDFlag.value = "0";
	}
	return true;
}
//检查身份证的性别、出生日期
function checkIDInfo(){
	if(fmfra.IDFlag.value == "1"){
		if(fmfra.IDGender.value != fm2.InsuredGender.value){
			alert("录入的性别和身份证性别不一致！");
			return false;
		}
		if(fmfra.IDBirthDay.value != fm2.InsuredBirthDay.value){
			alert("录入的出生日期和身份证出生日期不一致！");
			return false;
		}
	}
	return true;
}
/**
*查询历史数据
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
 * 下拉框选择后处理
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
