/***************************************************************
 * <p>ProName��LSQuotPubBasic.js</p>
 * <p>Title��������Ϣ���÷���</p>
 * <p>Description��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
/**
 * ����ά��������Ϣ����
 */
function initOtherInfo(cObj, cTranProdType) {

	//������Ϣ���ִ���
	if (cTranProdType=="01" || cTranProdType=="03" || cTranProdType=="00") {//��Ʒ����Ϊ��ͨ���֡������ա���������ʱ,�ò��ֲ�����չʾ
		document.getElementById("divInfo5").style.display = "none";
	} else if (cTranProdType=="02") {//������˻�������,�ô�ֱ��չʾ
		
		document.getElementById("productButton").style.display = "";
		document.getElementById("divInfo5").style.display = "";
	}
}

/**
 * �ڶ���������ʼ������
 */
function pubInitQuotStep2(cObj, cQuotType, cTranProdType, cTranPremMode, cEnginObj) {
	document.getElementById("PlanCode").value = "";
	document.getElementById("PlanDesc").value = "";
	document.getElementById("SysPlanCode").value = "";
	if (cQuotType==tETQuotType) {//һ��ѯ��
		if (cTranPremMode==null || cTranPremMode=="") {
			
		} else {
		
			cObj.PremMode.value = cTranPremMode;
			auotQuotShowCodeName('premmode', cTranPremMode, cObj, 'PremModeName');
			//��ҵ����ռ��,������
			if (cTranPremMode=="1") {//��ҵ����
				document.getElementById("EnterpriseRate").readOnly = true;
				document.getElementById("EnterpriseRate").value = "1";
			}
		}
		
		if (cTranProdType=="00") {//��ͨ����
			//���ع�����Ϣ
			document.getElementById("divEngin").style.display = "none";
			
			//�������ʹ���
			document.getElementById("tdPlan5").style.display = "";
			document.getElementById("tdPlan6").style.display = "";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//������ʶ����
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//���Ѽ��㷽ʽ����
			document.getElementById("tdPlan10").style.display = "none";
			document.getElementById("tdPlan11").style.display = "none";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//��������(������)
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("PlanPeople").value = "";
			
			//����Ŀհ���
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			
			//ְҵ�����
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			document.getElementById("spanOccupMid").style.display = "";
			document.getElementById("spanOccupCode").style.display = "";
			
			//����
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//����,�μ��籣ռ��,��Ůռ��
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//����ռ��
			document.getElementById("RetireRate").value = "0";
			
			//н��
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//��ע
			document.getElementById("OtherDesc").value = "";
			
			initOtherInfo(cObj, cTranProdType);
		}	else if (cTranProdType=="02") {//�˻���
			//���ع�����Ϣ
			document.getElementById("divEngin").style.display = "none";
			
			//�������ʹ���
			document.getElementById("tdPlan5").style.display = "";
			document.getElementById("tdPlan6").style.display = "";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//������ʶ����
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//���Ѽ��㷽ʽ����
			document.getElementById("tdPlan10").style.display = "none";
			document.getElementById("tdPlan11").style.display = "none";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//��������(������)
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("PlanPeople").value = "";
			
			//����Ŀհ���
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			
			//ְҵ�����
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//����
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//����,�μ��籣ռ��,��Ůռ��
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//����ռ��
			document.getElementById("RetireRate").value = "0";
			
			//н��
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//��ע
			document.getElementById("OtherDesc").value = "";

			initOtherInfo(cObj, cTranProdType);
		} else {//������
			//���ع�����Ϣ
			document.getElementById("divEngin").style.display = "";
			
			//�������ʹ���
			document.getElementById("tdPlan5").style.display = "none";
			document.getElementById("tdPlan6").style.display = "none";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//������ʶ����
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//���Ѽ��㷽ʽ����
			document.getElementById("tdPlan10").style.display = "";
			document.getElementById("tdPlan11").style.display = "";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//��������(������)
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("PlanPeople").value = "";
			
			//����Ŀհ���
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			
			//ְҵ�����
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//����
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//����,�μ��籣ռ��,��Ůռ��
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//����ռ��
			document.getElementById("RetireRate").value = "";
			
			//н��
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//��ע
			document.getElementById("OtherDesc").value = "";

			initOtherInfo(cObj, cTranProdType);
		}
	} else {//��Ŀ��ѯ��
		if (cTranProdType=="00" || cTranProdType=="03") {
			
			//��������
			document.getElementById("tdPlan5").style.display = "";
			document.getElementById("tdPlan6").style.display = "";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//������ʶ
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//���Ѽ��㷽ʽ
			document.getElementById("tdPlan10").style.display = "none";
			document.getElementById("tdPlan11").style.display = "none";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//��������,���,���
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("tdPlan14").style.display = "none";
			document.getElementById("tdPlan15").style.display = "none";
			document.getElementById("tdPlan16").style.display = "none";
			document.getElementById("tdPlan17").style.display = "none";
			document.getElementById("PlanPeople").style.display = "";
			document.getElementById("EnginCost").style.display = "";
			document.getElementById("EnginArea").style.display = "";
			
			//�հ��д���
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
		
			document.getElementById("trPlan1").style.display = "none";
			
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("trPlan6").style.display = "none";
			document.getElementById("trPlan7").style.display = "none";
			document.getElementById("trEnginCondition").style.display = "none";
			document.getElementById("spanOccupMid").style.display = "";
			document.getElementById("spanOccupCode").style.display = "";
			
			document.getElementById("InsuPeriod").value = "";
			document.getElementsByName("InsuPeriodFlag").value = "";
			document.getElementById("InsuPeriodFlagName").value = "";
			
			//ְҵ�����
			
			document.getElementById("OccupTypeRadio1").checked = true;
			
			document.getElementById("OccupTypeFlag").value = "1";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//����
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//����,�μ��籣ռ��,��Ůռ��
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "0.5";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//����ռ��
			document.getElementById("RetireRate").value = "";
			
			//н��
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//������Ϣ
			document.getElementById("EnginType").value = "";
			document.getElementById("EnginDays").value = "";
			document.getElementById("EnginTypeName").value = "";
			
			document.getElementById("EnginDesc").value = "";
			document.getElementById("EnginCondition").value = "";
			
			//��ע
			document.getElementById("OtherDesc").value = "";
		}	else {//������
  		//��������
			document.getElementById("tdPlan5").style.display = "none";
			document.getElementById("tdPlan6").style.display = "none";
			document.getElementById("PlanType").value = "";
			document.getElementById("PlanTypeName").value = "";
			
			//������ʶ
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("PlanFlag").value = "";
			document.getElementById("PlanFlagName").value = "";
			
			//���Ѽ��㷽ʽ
			document.getElementById("tdPlan10").style.display = "";
			document.getElementById("tdPlan11").style.display = "";
			document.getElementById("PremCalType").value = "";
			document.getElementById("PremCalTypeName").value = "";
			
			//��������,���,���
			document.getElementById("tdPlan12").style.display = "none";
			document.getElementById("tdPlan13").style.display = "none";
			document.getElementById("tdPlan14").style.display = "none";
			document.getElementById("tdPlan15").style.display = "none";
			document.getElementById("tdPlan16").style.display = "none";
			document.getElementById("tdPlan17").style.display = "none";
			document.getElementById("PlanPeople").style.display = "";
			document.getElementById("EnginCost").style.display = "";
			document.getElementById("EnginArea").style.display = "";
			
			document.getElementById("tdEngin1").style.display = "none";
			document.getElementById("tdEngin2").style.display = "none";
			document.getElementById("tdEngin3").style.display = "none";
			document.getElementById("tdEngin4").style.display = "none";
			document.getElementById("tdEngin5").style.display = "";
			document.getElementById("tdEngin6").style.display = "";
			
			
			document.getElementById("InsuPeriod").value = "";
			document.getElementsByName("InsuPeriodFlag").value = "";
			document.getElementById("InsuPeriodFlagName").value = "";
			
			//�հ��д���
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			document.getElementById("tdPlan3").style.display = "none";
			document.getElementById("tdPlan4").style.display = "none";
			
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("trPlan6").style.display = "";
			document.getElementById("trPlan7").style.display = "";
			document.getElementById("trEnginCondition").style.display = "none";
			
			//ְҵ�����
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			
			document.getElementById("OccupTypeFlag").value = "";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			
			//����
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			
			//����,�μ��籣ռ��,��Ůռ��
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			
			//����ռ��
			document.getElementById("RetireRate").value = "";
			
			//н��
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			//������Ϣ
			document.getElementById("EnginType").value = "";
			document.getElementById("EnginDays").value = "";
			document.getElementById("EnginTypeName").value = "";
			
			document.getElementById("EnginDesc").value = "";
			document.getElementById("EnginCondition").value = "";
			
			//��ע
			document.getElementById("OtherDesc").value = "";
			
			document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, '-1', '-1', '0');//����һ�����������ڵķ�������
		}
	}
}

/**
 * ѡ�񷽰��󹫹�����
 */
function pubShowPlanInfo(cObj, cQuotType, cTranProdType) {
	
	if (cQuotType==tETQuotType) {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
		
		cObj.SysPlanCode.value = PlanInfoGrid.getRowColData(tSelNo, 1);
		cObj.PlanCode.value = PlanInfoGrid.getRowColData(tSelNo, 2);
		cObj.PlanDesc.value = PlanInfoGrid.getRowColData(tSelNo, 3);
		var tPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		cObj.PlanType.value = tPlanType;
		cObj.PlanTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 5);
		
		cObj.PlanFlag.value = PlanInfoGrid.getRowColData(tSelNo, 6);
		cObj.PlanFlagName.value = PlanInfoGrid.getRowColData(tSelNo, 7);
		var tPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		cObj.PremCalType.value = tPremCalType;
		cObj.PremCalTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 9);
		
		var tOccupTypeFlag = PlanInfoGrid.getRowColData(tSelNo, 10);
		cObj.OccupTypeFlag.value = tOccupTypeFlag;//���⴦��
		//�����⴦��
		if (tOccupTypeFlag=="1") {
			
			cObj.OccupType.value = PlanInfoGrid.getRowColData(tSelNo, 12);
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
			auotQuotShowCodeName('occuptype', PlanInfoGrid.getRowColData(tSelNo, 12), cObj, 'OccupTypeName');
		} else if (tOccupTypeFlag=="2") {
			
			var tDoubOccupType = PlanInfoGrid.getRowColData(tSelNo, 12);
			var tDoubArr = tDoubOccupType.split("-");
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = tDoubArr[0];
			cObj.MaxOccupType.value = tDoubArr[1];
			cObj.OccupRate.value = PlanInfoGrid.getRowColData(tSelNo, 32);
			auotQuotShowCodeName('occuptype', tDoubArr[0], cObj, 'MinOccupTypeName');
			auotQuotShowCodeName('occuptype', tDoubArr[1], cObj, 'MaxOccupTypeName');
		} else {
			
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
		}
		
		cObj.OccupMidType.value = PlanInfoGrid.getRowColData(tSelNo, 14);
		cObj.OccupMidTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 15);
		cObj.OccupCode.value = PlanInfoGrid.getRowColData(tSelNo, 16);
		cObj.OccupCodeName.value = PlanInfoGrid.getRowColData(tSelNo, 17);
		
		cObj.MinAge.value = PlanInfoGrid.getRowColData(tSelNo, 18);
		cObj.MaxAge.value = PlanInfoGrid.getRowColData(tSelNo, 19);
		cObj.AvgAge.value = PlanInfoGrid.getRowColData(tSelNo, 20);
		
		cObj.NumPeople.value = PlanInfoGrid.getRowColData(tSelNo, 21);
		cObj.PlanPeople.value = PlanInfoGrid.getRowColData(tSelNo, 21);
		cObj.SocialInsuRate.value = PlanInfoGrid.getRowColData(tSelNo, 22);
  	
		var tSexRate = PlanInfoGrid.getRowColData(tSelNo, 23);//��Ů���������⴦��
		if (tSexRate==null || tSexRate=="") {
  	
		} else {
			
			tSexArr = tSexRate.split(":");
			cObj.MaleRate.value = tSexArr[0];
			cObj.FemaleRate.value = tSexArr[1];
		}
		
		cObj.RetireRate.value = PlanInfoGrid.getRowColData(tSelNo, 24);
		cObj.PremMode.value = PlanInfoGrid.getRowColData(tSelNo, 25);
		cObj.PremModeName.value = PlanInfoGrid.getRowColData(tSelNo, 26);
		cObj.EnterpriseRate.value = PlanInfoGrid.getRowColData(tSelNo, 27);
		cObj.MinSalary.value = PlanInfoGrid.getRowColData(tSelNo, 28);
		cObj.MaxSalary.value = PlanInfoGrid.getRowColData(tSelNo, 29);
		cObj.AvgSalary.value = PlanInfoGrid.getRowColData(tSelNo, 30);
		cObj.OtherDesc.value = PlanInfoGrid.getRowColData(tSelNo, 31);
		
		pubShowInfoControl(cObj, cQuotType, cTranProdType, tPlanType, tPremCalType, tOccupTypeFlag);
	} else {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
		var tSysPlanCode = PlanInfoGrid.getRowColData(tSelNo, 1);
		cObj.SysPlanCode.value = tSysPlanCode;
		var tPlanCode = PlanInfoGrid.getRowColData(tSelNo, 2);
		cObj.PlanCode.value = tPlanCode;
		cObj.PlanDesc.value = PlanInfoGrid.getRowColData(tSelNo, 3);
		var tPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		cObj.PlanType.value = tPlanType;
		cObj.PlanTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 5);
		
		cObj.PlanFlag.value = PlanInfoGrid.getRowColData(tSelNo, 6);
		cObj.PlanFlagName.value = PlanInfoGrid.getRowColData(tSelNo, 7);
		var tPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		cObj.PremCalType.value = tPremCalType;
		cObj.PremCalTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 9);
		
		cObj.InsuPeriod.value = PlanInfoGrid.getRowColData(tSelNo, 10);
		cObj.InsuPeriodFlag.value = PlanInfoGrid.getRowColData(tSelNo, 11);
		cObj.InsuPeriodFlagName.value = PlanInfoGrid.getRowColData(tSelNo, 12);
		
		var tOccupTypeFlag = PlanInfoGrid.getRowColData(tSelNo, 13);
		cObj.OccupTypeFlag.value = tOccupTypeFlag;//���⴦��
		//�����⴦��
		if (tOccupTypeFlag=="1") {
			
			cObj.OccupType.value = PlanInfoGrid.getRowColData(tSelNo, 15);
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
			auotQuotShowCodeName('occuptype', PlanInfoGrid.getRowColData(tSelNo, 15), cObj, 'OccupTypeName');
		} else if (tOccupTypeFlag=="2") {
			
			var tDoubOccupType = PlanInfoGrid.getRowColData(tSelNo, 15);
			var tDoubArr = tDoubOccupType.split("-");
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = tDoubArr[0];
			cObj.MaxOccupType.value = tDoubArr[1];
			cObj.OccupRate.value = PlanInfoGrid.getRowColData(tSelNo, 35);
			auotQuotShowCodeName('occuptype', tDoubArr[0], cObj, 'MinOccupTypeName');
			auotQuotShowCodeName('occuptype', tDoubArr[1], cObj, 'MaxOccupTypeName');
		} else {
			
			cObj.OccupType.value = "";
			cObj.OccupTypeName.value = "";
			cObj.MinOccupType.value = "";
			cObj.MinOccupTypeName.value = "";
			cObj.MaxOccupType.value = "";
			cObj.MaxOccupTypeName.value = "";
			cObj.OccupRate.value = "";
		}
		
		cObj.OccupMidType.value = PlanInfoGrid.getRowColData(tSelNo, 17);
		cObj.OccupMidTypeName.value = PlanInfoGrid.getRowColData(tSelNo, 18);
		cObj.OccupCode.value = PlanInfoGrid.getRowColData(tSelNo, 19);
		cObj.OccupCodeName.value = PlanInfoGrid.getRowColData(tSelNo, 20);
		
		cObj.MinAge.value = PlanInfoGrid.getRowColData(tSelNo, 21);
		cObj.MaxAge.value = PlanInfoGrid.getRowColData(tSelNo, 22);
		cObj.AvgAge.value = PlanInfoGrid.getRowColData(tSelNo, 23);
		
		var tNumPeople = PlanInfoGrid.getRowColData(tSelNo, 24);
		cObj.NumPeople.value = tNumPeople;
		cObj.PlanPeople.value = PlanInfoGrid.getRowColData(tSelNo, 24);
		cObj.SocialInsuRate.value = PlanInfoGrid.getRowColData(tSelNo, 25);
  	
		var tSexRate = PlanInfoGrid.getRowColData(tSelNo, 26);//��Ů���������⴦��
		
		if (tSexRate==null || tSexRate=="") {
			
			cObj.MaleRate.value = "";
			cObj.FemaleRate.value = "";
		} else {
			
			tSexArr = tSexRate.split(":");
			cObj.MaleRate.value = tSexArr[0];
			cObj.FemaleRate.value = tSexArr[1];
		}
		
		cObj.RetireRate.value = PlanInfoGrid.getRowColData(tSelNo, 27);
		cObj.PremMode.value = PlanInfoGrid.getRowColData(tSelNo, 28);
		cObj.PremModeName.value = PlanInfoGrid.getRowColData(tSelNo, 29);
		cObj.EnterpriseRate.value = PlanInfoGrid.getRowColData(tSelNo, 30);
		cObj.MinSalary.value = PlanInfoGrid.getRowColData(tSelNo, 31);
		cObj.MaxSalary.value = PlanInfoGrid.getRowColData(tSelNo, 32);
		cObj.AvgSalary.value = PlanInfoGrid.getRowColData(tSelNo, 33);
		cObj.OtherDesc.value = PlanInfoGrid.getRowColData(tSelNo, 34);
		
		if (cTranProdType=="01") {//��Ŀѯ�۽����ն��⴦������Ϣ
			
			cObj.PlanPeople.value = tNumPeople;
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql26");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tSysPlanCode);
			tSQLInfo.addSubPara(tPlanCode);
			
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				
				cObj.MaxEnginCost.value = tArr[0][0];
				cObj.MaxEnginArea.value = tArr[0][1];
				cObj.EnginType.value = tArr[0][2];
				cObj.EnginTypeName.value = tArr[0][3];
				cObj.EnginDesc.value = tArr[0][4];
				cObj.EnginCondition.value = tArr[0][5];
				cObj.EnginDays.value = tArr[0][6];
				cObj.EnginArea.value = tArr[0][7];
				cObj.EnginCost.value = tArr[0][8];
			}
			
			document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, tSysPlanCode, tPlanCode, '0');
			pubShowConditionCheck(cObj);
		}
		
		pubShowInfoControl(cObj, cQuotType, cTranProdType, tPlanType, tPremCalType, tOccupTypeFlag);
	}
}

/**
 * ������Ϣ����������
 */
function pubPlanAfterCodeSelect(cObj, cQuotType, cCodeType, FieldValue) {
	
	if (cQuotType==tETQuotType) {//һ��ѯ��
		
		if	(cCodeType=="plantype") {//ѡ�񷽰�����
			
			if (FieldValue=="00") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else if (FieldValue=="01") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="02") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="03") {
				
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cCodeType=="engincaltype") {
			
			if (FieldValue=='1') {
				
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else {
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		}
	} else {//��Ŀѯ��
		
		if	(cCodeType=="plantype") {//ѡ�񷽰�����
			
			if (FieldValue=="00") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else if (FieldValue=="01") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="02") {
				
				document.getElementById("OccupTypeRadio1").checked = true;
				document.getElementById("OccupTypeFlag").value = "1";
				document.getElementById("trPlan1").style.display = "";
				document.getElementById("trOccupType1").style.display = "";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} else if (FieldValue=="03") {
				
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cCodeType=="engincaltype") {
			
			if (FieldValue=='1') {
				
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "";
				document.getElementById("tdEngin6").style.display = "";
				
			} else if (FieldValue=='2') {
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "";
				document.getElementById("tdPlan15").style.display = "";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "";
				document.getElementById("tdEngin2").style.display = "";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
				
			} else {
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "";
				document.getElementById("tdPlan17").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "";
				document.getElementById("tdEngin4").style.display = "";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
			}
		}
	}
}

/**
 * ѡ�񵥶�ְҵ���
 */
function pubChooseOccupFlag(cObj, cQuotFlag) {

	if (cQuotFlag=='1') {
		
		cObj.OccupTypeFlag.value = '1';
		document.getElementById("OccupTypeRadio1").checked = true;
		document.getElementById("OccupTypeRadio2").checked = false;
		document.getElementById("trOccupType1").style.display = "";
		document.getElementById("trOccupType2").style.display = "none";
	} else if (cQuotFlag=='2') {
		
		cObj.OccupTypeFlag.value = '2';
		document.getElementById("OccupTypeRadio2").checked = true;
		document.getElementById("OccupTypeRadio1").checked = false;
		document.getElementById("trOccupType1").style.display = "none";
		document.getElementById("trOccupType2").style.display = "";
	} else {
		
		cObj.OccupTypeFlag.value = '';
		document.getElementById("trOccupType1").style.display = "none";
		document.getElementById("trOccupType2").style.display = "none";
	}
}

/**
 * ѡ�񷽰���Ϣ��,����չʾ����
 */
function pubShowInfoControl(cObj, cQuotType, cTranProdType, cPlanType, cPremCalType, cOccupTypeFlag) {

	if (cQuotType==tETQuotType) {
		
		if (cTranProdType=="00") {//��ͨ����
			
			if (cPlanType=="00") {//��ͨ����
			
				document.getElementById("trPlan1").style.display = "";
				
				if (cOccupTypeFlag=="1") {
					
					document.getElementById("trOccupType1").style.display = "";
					document.getElementById("trOccupType2").style.display = "none";
				} else {
					document.getElementById("trOccupType1").style.display = "none";
					document.getElementById("trOccupType2").style.display = "";
				}
				
				pubChooseOccupFlag(cObj, cOccupTypeFlag);
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				
			} else {//��������
			
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cTranProdType=="01") {//����������
		
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "0";
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
				
			if (cPremCalType=="1") {
				
				document.getElementById("tdPlan10").style.display = "";
				document.getElementById("tdPlan11").style.display = "";
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else {
				
				document.getElementById("tdPlan10").style.display = "";
				document.getElementById("tdPlan11").style.display = "";
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			} 
		} else if (cTranProdType=="02") {
			
			if (cPlanType=="02") {//�����˻�
			
				document.getElementById("trPlan1").style.display = "";
				
				if (cOccupTypeFlag=="1") {
					
					document.getElementById("trOccupType1").style.display = "";
					document.getElementById("trOccupType2").style.display = "none";
				} else {
					document.getElementById("trOccupType1").style.display = "none";
					document.getElementById("trOccupType2").style.display = "";
				}
				pubChooseOccupFlag(cObj, cOccupTypeFlag);
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
			} else {//�����˻�
			
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
				document.getElementById("tdPlan5").style.display = "";
				document.getElementById("tdPlan6").style.display = "";
			}
		}
	} else {
		
		if (cTranProdType=="00" || cTranProdType=="03") {//��ͨ����
			
			if (cPlanType=="00") {//��ͨ����
			
				document.getElementById("trPlan1").style.display = "";
				
				if (cOccupTypeFlag=="1") {
					
					document.getElementById("trOccupType1").style.display = "";
					document.getElementById("trOccupType2").style.display = "none";
				} else {
					document.getElementById("trOccupType1").style.display = "none";
					document.getElementById("trOccupType2").style.display = "";
				}
				
				pubChooseOccupFlag(cObj, cOccupTypeFlag);
				document.getElementById("trPlan2").style.display = "";
				document.getElementById("trPlan3").style.display = "";
				document.getElementById("trPlan4").style.display = "";
				document.getElementById("trPlan5").style.display = "";
				document.getElementById("tdPlan8").style.display = "";
				document.getElementById("tdPlan9").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
			} else {//��������
			
				document.getElementById("trPlan1").style.display = "none";
				document.getElementById("trOccupType1").style.display = "none";
				document.getElementById("trOccupType2").style.display = "none";
				document.getElementById("trPlan2").style.display = "none";
				document.getElementById("trPlan3").style.display = "none";
				document.getElementById("trPlan4").style.display = "none";
				document.getElementById("trPlan5").style.display = "none";
				document.getElementById("tdPlan8").style.display = "none";
				document.getElementById("tdPlan9").style.display = "none";
				document.getElementById("tdPlan1").style.display = "";
				document.getElementById("tdPlan2").style.display = "";
			}
		} else if (cTranProdType=="01") {//����������
		
			document.getElementById("OccupTypeRadio1").checked = true;
			document.getElementById("OccupTypeFlag").value = "0";
			document.getElementById("trPlan1").style.display = "none";
			document.getElementById("trOccupType1").style.display = "none";
			document.getElementById("trOccupType2").style.display = "none";
			document.getElementById("trPlan2").style.display = "none";
			document.getElementById("trPlan3").style.display = "none";
			document.getElementById("trPlan4").style.display = "none";
			document.getElementById("trPlan5").style.display = "none";
			document.getElementById("tdPlan8").style.display = "none";
			document.getElementById("tdPlan9").style.display = "none";
			document.getElementById("tdPlan1").style.display = "";
			document.getElementById("tdPlan2").style.display = "";
			
			if (cPremCalType=="1") {//������
			
				document.getElementById("tdPlan12").style.display = "";
				document.getElementById("tdPlan13").style.display = "";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				document.getElementById("tdPlan3").style.display = "none";
				document.getElementById("tdPlan4").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "";
				document.getElementById("tdEngin6").style.display = "";
				
			} else if (cPremCalType=="2") {//�����
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "";
				document.getElementById("tdPlan15").style.display = "";
				document.getElementById("tdPlan16").style.display = "none";
				document.getElementById("tdPlan17").style.display = "none";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				document.getElementById("tdPlan3").style.display = "none";
				document.getElementById("tdPlan4").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "";
				document.getElementById("tdEngin2").style.display = "";
				document.getElementById("tdEngin3").style.display = "none";
				document.getElementById("tdEngin4").style.display = "none";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
				
			} else {//���������
				
				document.getElementById("tdPlan12").style.display = "none";
				document.getElementById("tdPlan13").style.display = "none";
				document.getElementById("tdPlan14").style.display = "none";
				document.getElementById("tdPlan15").style.display = "none";
				document.getElementById("tdPlan16").style.display = "";
				document.getElementById("tdPlan17").style.display = "";
				document.getElementById("tdPlan1").style.display = "none";
				document.getElementById("tdPlan2").style.display = "none";
				document.getElementById("tdPlan3").style.display = "none";
				document.getElementById("tdPlan4").style.display = "none";
				
				document.getElementById("tdEngin1").style.display = "none";
				document.getElementById("tdEngin2").style.display = "none";
				document.getElementById("tdEngin3").style.display = "";
				document.getElementById("tdEngin4").style.display = "";
				document.getElementById("tdEngin5").style.display = "none";
				document.getElementById("tdEngin6").style.display = "none";
			}
		}
	}
	
}

/**
 * �ύʱ,������������
 * 
 */
function dealRedundant(cObj, cQuotType, cTranProdType) {
	
	
	/*
	һ��ѯ��
		1.��ͨ����
		a) ��ͨ����:��ս����յı��Ѽ��㷽ʽ�ֶ�,����ְҵ���������һ��ְҵ����
		b) ��������:��ս���������������������
		2.������
		a) ��ͨ����:����ѡ��ı��Ѽ��㷽ʽ�ж������������������������
		3.�˻���
		a) �����˻�:��ս����յı��Ѽ��㷽ʽ�ֶ�,����ְҵ���������һ��ְҵ����
		b) �����˻�:��ս���������������������
	��Ŀѯ��
		1.��ͨ����
		a) ��ͨ����:��ս����յı��Ѽ��㷽ʽ�ֶ�,����ְҵ���������һ��ְҵ����
		b) ��������:��ս���������������������
		2.������
		a) ��ͨ����:����ѡ��ı��Ѽ��㷽ʽ�ж������������������������
	*/
	if (cQuotType==tETQuotType) {//һ��ѯ��
		
		if (cTranProdType=="00") {//��ͨ����
			
			document.getElementById("PremCalType").value = "";
			document.getElementById("PlanPeople").value = "";
			
			var tPlanType = document.getElementById("PlanType").value;
			var tOccupTypeFlag = document.getElementById("OccupTypeFlag").value;
			
			if (tPlanType=="00") {//��ͨ����
			
				if (tOccupTypeFlag=="1") {//��ְҵ
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				} else if (tOccupTypeFlag=="2") {//��ְҵ
					
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
				} else {//���û��ְҵ���,��ô���ÿ�
				
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				}
			} else {//��������
				
				document.getElementById("PlanFlag").value = "";
				document.getElementById("OccupTypeFlag").value = "";
				document.getElementById("OccupType").value = "";
				document.getElementById("OccupTypeName").value = "";
				document.getElementById("OccupMidType").value = "";
				document.getElementById("OccupMidTypeName").value = "";
				document.getElementById("OccupCode").value = "";
				document.getElementById("OccupCodeName").value = "";
				document.getElementById("MinOccupType").value = "";
				document.getElementById("MinOccupTypeName").value = "";
				document.getElementById("MaxOccupType").value = "";
				document.getElementById("MaxOccupTypeName").value = "";
				document.getElementById("OccupRate").value = "";
				document.getElementById("MinAge").value = "";
				document.getElementById("MaxAge").value = "";
				document.getElementById("AvgAge").value = "";
				document.getElementById("NumPeople").value = "";
				document.getElementById("SocialInsuRate").value = "";
				document.getElementById("MaleRate").value = "";
				document.getElementById("FemaleRate").value = "";
				document.getElementById("RetireRate").value = "";
				document.getElementById("PremMode").value = "";
				document.getElementById("PremModeName").value = "";
				document.getElementById("EnterpriseRate").value = "";
				document.getElementById("MinSalary").value = "";
				document.getElementById("MaxSalary").value = "";
				document.getElementById("AvgSalary").value = "";
			}
		} else if (cTranProdType=="01") {//������
			
			var tPremCalType = cObj.PremCalType.value;
			
			if (tPremCalType=="1") {//������
				
			} else {//���ǰ������Ľ�����,��հ�������¼�������
				
				document.getElementById("PlanPeople").value = "";
			}
			document.getElementById("PlanFlag").value = "";
			document.getElementById("OccupTypeFlag").value = "";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			document.getElementById("RetireRate").value = "";
			document.getElementById("PremMode").value = "";
			document.getElementById("PremModeName").value = "";
			document.getElementById("EnterpriseRate").value = "";
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			setNumPeople(cObj, tPremCalType);
		} else {//�˻���
		
			document.getElementById("PremCalType").value = "";
			document.getElementById("PlanPeople").value = "";
			
			var tPlanType = document.getElementById("PlanType").value;
			var tOccupTypeFlag = document.getElementById("OccupTypeFlag").value;
			
			if (tPlanType=="02") {//�����˻�
			
				if (tOccupTypeFlag=="1") {//��ְҵ
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				} else if (tOccupTypeFlag=="2") {//��ְҵ
					
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
				} else {//���û��ְҵ���,��ô���ÿ�
				
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				}
			} else {//�����˻�
				
				document.getElementById("PlanFlag").value = "";
				document.getElementById("OccupTypeFlag").value = "";
				document.getElementById("OccupType").value = "";
				document.getElementById("OccupTypeName").value = "";
				document.getElementById("OccupMidType").value = "";
				document.getElementById("OccupMidTypeName").value = "";
				document.getElementById("OccupCode").value = "";
				document.getElementById("OccupCodeName").value = "";
				document.getElementById("MinOccupType").value = "";
				document.getElementById("MinOccupTypeName").value = "";
				document.getElementById("MaxOccupType").value = "";
				document.getElementById("MaxOccupTypeName").value = "";
				document.getElementById("OccupRate").value = "";
				document.getElementById("MinAge").value = "";
				document.getElementById("MaxAge").value = "";
				document.getElementById("AvgAge").value = "";
				document.getElementById("NumPeople").value = "";
				document.getElementById("SocialInsuRate").value = "";
				document.getElementById("MaleRate").value = "";
				document.getElementById("FemaleRate").value = "";
				document.getElementById("RetireRate").value = "";
				document.getElementById("PremMode").value = "";
				document.getElementById("PremModeName").value = "";
				document.getElementById("EnterpriseRate").value = "";
				document.getElementById("MinSalary").value = "";
				document.getElementById("MaxSalary").value = "";
				document.getElementById("AvgSalary").value = "";
			}
		}
	} else {//��Ŀѯ��
		
		if (cTranProdType=="00" || cTranProdType=="03") {//��ͨ����
			
			document.getElementById("PremCalType").value = "";
			document.getElementById("PlanPeople").value = "";
			document.getElementById("EnginCost").value = "";
			document.getElementById("EnginArea").value = "";
			document.getElementById("EnginType").value = "";
			document.getElementById("EnginDays").value = "";
			document.getElementById("EnginDesc").value = "";
			document.getElementById("EnginCondition").value = "";
			
			var tPlanType = document.getElementById("PlanType").value;
			var tOccupTypeFlag = document.getElementById("OccupTypeFlag").value;
			
			if (tPlanType=="00") {//��ͨ����

				if (tOccupTypeFlag=="1") {//��ְҵ
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				} else if (tOccupTypeFlag=="2") {//��ְҵ
					
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
				} else {//���û��ְҵ���,��ô���ÿ�
				
					document.getElementById("OccupType").value = "";
					document.getElementById("OccupTypeName").value = "";
					document.getElementById("OccupMidType").value = "";
					document.getElementById("OccupMidTypeName").value = "";
					document.getElementById("OccupCode").value = "";
					document.getElementById("OccupCodeName").value = "";
					
					document.getElementById("MinOccupType").value = "";
					document.getElementById("MinOccupTypeName").value = "";
					document.getElementById("MaxOccupType").value = "";
					document.getElementById("MaxOccupTypeName").value = "";
					document.getElementById("OccupRate").value = "";
				}
			} else {//��������
				
				document.getElementById("PlanFlag").value = "";
				document.getElementById("OccupTypeFlag").value = "";
				document.getElementById("OccupType").value = "";
				document.getElementById("OccupTypeName").value = "";
				document.getElementById("OccupMidType").value = "";
				document.getElementById("OccupMidTypeName").value = "";
				document.getElementById("OccupCode").value = "";
				document.getElementById("OccupCodeName").value = "";
				document.getElementById("MinOccupType").value = "";
				document.getElementById("MinOccupTypeName").value = "";
				document.getElementById("MaxOccupType").value = "";
				document.getElementById("MaxOccupTypeName").value = "";
				document.getElementById("OccupRate").value = "";
				document.getElementById("MinAge").value = "";
				document.getElementById("MaxAge").value = "";
				document.getElementById("AvgAge").value = "";
				document.getElementById("NumPeople").value = "";
				document.getElementById("SocialInsuRate").value = "";
				document.getElementById("MaleRate").value = "";
				document.getElementById("FemaleRate").value = "";
				document.getElementById("RetireRate").value = "";
				document.getElementById("PremMode").value = "";
				document.getElementById("PremModeName").value = "";
				document.getElementById("EnterpriseRate").value = "";
				document.getElementById("MinSalary").value = "";
				document.getElementById("MaxSalary").value = "";
				document.getElementById("AvgSalary").value = "";
			}
		} else if (cTranProdType=="01") {//������
			
			var tPremCalType = document.getElementById("PremCalType").value;
			
			if (tPremCalType=="1") {//������,�����������
				
				document.getElementById("EnginCost").value = "";
				document.getElementById("EnginArea").value = "";
				document.getElementById("MaxEnginCost").value = "";
				document.getElementById("MaxEnginArea").value = "";
			} else if (tPremCalType=="2") {//�����
				
				document.getElementById("PlanPeople").value = "";
				document.getElementById("EnginArea").value = "";
				document.getElementById("MaxEnginArea").value = "";
			} else {//�����
				
				document.getElementById("PlanPeople").value = "";
				document.getElementById("EnginCost").value = "";
				document.getElementById("MaxEnginCost").value = "";
			}
			document.getElementById("PlanFlag").value = "";
			document.getElementById("OccupTypeFlag").value = "";
			document.getElementById("OccupType").value = "";
			document.getElementById("OccupTypeName").value = "";
			document.getElementById("OccupMidType").value = "";
			document.getElementById("OccupMidTypeName").value = "";
			document.getElementById("OccupCode").value = "";
			document.getElementById("OccupCodeName").value = "";
			document.getElementById("MinOccupType").value = "";
			document.getElementById("MinOccupTypeName").value = "";
			document.getElementById("MaxOccupType").value = "";
			document.getElementById("MaxOccupTypeName").value = "";
			document.getElementById("OccupRate").value = "";
			document.getElementById("MinAge").value = "";
			document.getElementById("MaxAge").value = "";
			document.getElementById("AvgAge").value = "";
			document.getElementById("NumPeople").value = "";
			document.getElementById("SocialInsuRate").value = "";
			document.getElementById("MaleRate").value = "";
			document.getElementById("FemaleRate").value = "";
			document.getElementById("RetireRate").value = "";
			document.getElementById("PremMode").value = "";
			document.getElementById("PremModeName").value = "";
			document.getElementById("EnterpriseRate").value = "";
			document.getElementById("MinSalary").value = "";
			document.getElementById("MaxSalary").value = "";
			document.getElementById("AvgSalary").value = "";
			
			if (document.getElementById("trEnginCondition").style.display=="none") {
				
				cObj.EnginCondition.value = "";
			}
			
			setNumPeople(cObj, tPremCalType);
		}
	}
}

/**
 * ���ݲ�Ʒ���Ͷ����������е�������ֵ(������)
 */
function setNumPeople(cObj, cPremCalType) {

	if (cPremCalType=="1") {
		
		cObj.NumPeople.value = cObj.PlanPeople.value;
	} else {
		
		cObj.NumPeople.value = "";
		cObj.PlanPeople.value = "";
	}
}

/**
 * ������ϸ����
 */
function showEnginFactorDiv(cQuotNo, cQuotBatNo, cSysPlanCode, cPlanCode, cFlag) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql24");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	tSQLInfo.addSubPara(cSysPlanCode);
	tSQLInfo.addSubPara(cPlanCode);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

	var tElementCode;//���ӱ���
	var tElementName;//��������
	var tControlFlag;//�Ƿ���¼���
	var tIsSelected;//����quotno���жϸ�����ֵ�Ƿ���ѯ����Լ����
	var tOElementValue;//ԭʼֵ
	var tNElementValue;//ѯ��ֵ
	
	var tInnerHTML0 = "<table class=common><tr class=common><td class=title></td><td class=input><td class=title></td><td class=input><td class=title></td><td class=input></td></tr>";
	var tInnerHTML1 = "<tr class=common><td class=title>������ϸ<span style='color: red'>*</span></td><td class=input colspan=5>";
	
	for (var i=0; i<tArr.length; i++) {
	
		tElementCode = tArr[i][0];
		tElementName = tArr[i][1];
		tControlFlag = tArr[i][2];
		tIsSelected = tArr[i][3];//�����ж��Ƿ�ѡ��
		tOElementValue = tArr[i][4];//ԭʼֵ
		tNElementValue = tArr[i][5];//ѯ��ֵ
		
		var tDisableFlag = "";
		if (cFlag=="1") {
			tDisableFlag = "disabled";
		}
		
		tInnerHTML1 += "<input type=checkbox name="+ tElementCode + " onclick=\"showConditionCheck();\" "+ tDisableFlag +" ";
		if (tIsSelected=='0') {//ѯ�۱���û�б��������

			tInnerHTML1 += ">"+ tElementName;
		} else {//ѯ���б����˸�����

			tInnerHTML1 += " checked>"+ tElementName;
		}
		var tFlag1 = tControlFlag.substring(0,1);
		var tFlag2 = tControlFlag.substring(1,2);
		tInnerHTML1 += "<input type=hidden name="+ tElementCode +"Flag value="+ tFlag2 +">";
		
		if (tFlag1=='1') {//����¼���

			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value="+ tFlag1 +"><input style='width:100px' class=common name="+ tElementCode +"Value value="+ tNElementValue +" "+ tDisableFlag +">";	
		} else {
			tInnerHTML1 += "<input type=hidden name=Hidden"+ tElementCode +" value=0>";	
		}
	}
	
	tInnerHTML1 += "</td></tr></table>";
	tInnerHTML0 = tInnerHTML0+tInnerHTML1;
	
	return tInnerHTML0;
}

/**
 * �Ƿ�չʾ����״��˵��¼����
 */
function pubShowConditionCheck(cObj) {
	
	var tArr = document.getElementById("divEnginFactor").getElementsByTagName("input");
	
	var tFlag = false;//Ĭ�ϲ�չʾ����״��˵��
	for (var i=0; i<tArr.length; i++) {
		
		if (tFlag==false) {
			var tField = tArr[i];
			
			if (tField.type=="checkbox") {//ֻ�и��ֶ�Ϊcheckboxʱ,�Ž��д���
				
				if (tField.checked) {//ѡ��ʱ,����ȡ��־ֵ,ȡ����־ֵΪ1ʱ,չʾ״��˵��
					
					var tCheckValue = eval(cObj.name+"."+ tField.name +"Flag.value");
					if (tCheckValue=="1") {
						tFlag = true;
					}	
				}
			}
		}
	}
	
	if (tFlag==true) {
		document.getElementById("trEnginCondition").style.display = "";
	} else {
		document.getElementById("trEnginCondition").style.display = "none";
	}
}

/**
 * �ύǰ��У��-������ϸ
 */
function checkEnginFactor(cObj) {
	
	var arrAll;
	arrAll = document.getElementById("divEnginFactor").getElementsByTagName("input");

	var tConfigCount = 0;
	for (var i=0; i<arrAll.length; i++) {
		
		var obj = arrAll[i];
		if (obj.type == "checkbox") {

			if(obj.checked) {
				//�����ѡ����,ȡѡ�е�ֵ���������Ƿ���Ҫ¼��

				var tNeedInput = eval(cObj.name+".Hidden"+ obj.name +".value");
      
				if (tNeedInput=='1') {//��Ҫ��ֵ
					
					var tInputValue = eval(cObj.name+"."+ obj.name +"Value.value");
					
					if (tInputValue==null || tInputValue=='') {
						alert("ѡ�е���Ϣ��¼�������Ϣ��");
						return false;
					} else {
						
						if (tInputValue.length>30) {
							alert("¼��ľ�����Ϣ��Ӧ����30�ֳ���");
							return false;
						}
					}
				}
				tConfigCount++;
			}
		}
	}
	if (tConfigCount=="0") {
		alert("��ѡ�񹤳���ϸ��");
		return false;
	}

	return true;
}

/**
 * ������Ϣ�ύǰУ��
 */
function beforeSaveEngin(cObj) {
	
	if (isEmpty(cObj.EnginName)) {
		alert("�������Ʋ���Ϊ�գ�");
		return false;
	}
	
	var tEnginName = cObj.EnginName.value;
	if (tEnginName.length>150) {
		alert("�������Ʋ��ܳ���150�ֳ���");
		return false;
	}
	
	if (isEmpty(cObj.EnginType)) {
		alert("�������Ͳ���Ϊ�գ�");
		return false;
	}
	
	//У�鹤�����
	var tEnginArea = cObj.EnginArea.value;
	if (tEnginArea==null || tEnginArea=="") {
	
	} else {
		
		//�������,ӦΪ����0����Ч����
		if (!isNumeric(tEnginArea) || Number(tEnginArea)<0) {
			alert("�������ӦΪ����0����Ч���֣�");
			return false;
		}
		
		if (!checkDecimalFormat(tEnginArea, 18, 2)) {
			alert("�����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
			return false;
		}
	}

	//У�鹤�����
	var tEnginCost = cObj.EnginCost.value;
	if (tEnginCost==null || tEnginCost=="") {
	
	} else {
		
		//�������,ӦΪ����0����Ч����
		if (!isNumeric(tEnginCost) || Number(tEnginCost)<0) {
			alert("�������ӦΪ����0����Ч���֣�");
			return false;
		}
		
		if (!checkDecimalFormat(tEnginCost, 18, 2)) {
			alert("�����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
			return false;
		}
	}
	
	if (!checkEnginFactor(cObj)) {
		return false;
	}
	
	if (isEmpty(cObj.EnginPlace)) {
		alert("���̵ص㲻��Ϊ�գ�");
		return false;
	}
	
	var tEnginPlace = cObj.EnginPlace.value;
	if (tEnginPlace.length>150) {
		alert("���̵ص㲻�ܳ���150�ֳ���");
		return false;
	}
	
	var tEnginStartDate = cObj.EnginStartDate.value;
	var tEnginEndDate = cObj.EnginEndDate.value;
	
	if (tEnginStartDate!=null && tEnginStartDate!=""&&tEnginEndDate!=null && tEnginEndDate!="") {
		if (tEnginStartDate>tEnginEndDate) {
			alert("�������ڲ������ڹ���ֹ�ڣ�");
			return false;
		}
	}
	
	if (isEmpty(cObj.EnginDesc)) {
		alert("���̸�������Ϊ�գ�");
		return false;
	}
	
	var tEnginDesc = cObj.EnginDesc.value;
	if (tEnginDesc.length>1000) {
		alert("���̸������ܳ���1000�ֳ���");
		return false;
	}

	if (document.getElementById("trEnginCondition").style.display=="") {
		
		if (isEmpty(cObj.EnginCondition)) {
			alert("��������ϸ��ѡ�˵�·�������������ˮ��վ�ȹ�����Ϣʱ������״��˵������Ϊ�գ�");
			return false;
		}
		
		var tEnginCondition = cObj.EnginCondition.value;
		if (tEnginCondition.length>1000) {
			alert("����״��˵�����ܳ���1000�ֳ���");
			return false;
		}
	}
	
	var tRemark = cObj.Remark.value;
	if (tRemark.length>1000) {
		alert("��ע���ܳ���1000�ֳ���");
		return false;
	}
	
	var tInsurerName = cObj.InsurerName.value;
	if (tInsurerName.length>150) {
		alert("�а������Ʋ��ܳ���150�ֳ���");
		return false;
	}
	
	if (isEmpty(cObj.ContractorName)) {
		alert("ʩ�������Ʋ���Ϊ�գ�");
		return false;
	}
	
	var tContractorName = cObj.ContractorName.value;
	if (tContractorName.length>150) {
		alert("ʩ�������Ʋ��ܳ���150�ֳ���");
		return false;
	}
	
	if (isEmpty(cObj.ContractorType)) {
		alert("ʩ�������ʲ���Ϊ�գ�");
		return false;
	}
	
	return true;
}

/**
 * �ύǰ����У��
 */
function pubBeforeSubmit(cObj, cQuotType, cTranProdType) {

	if (isEmpty(cObj.PlanDesc)) {
		alert("������������Ϊ�գ�");
		return false;
	}

	if (cQuotType==tETQuotType) {//һ��ѯ��
	
		if (cTranProdType=="00") {//��ͨ����
			
			var tPlanType = cObj.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("�������Ͳ���Ϊ�գ�");
				return false;
			}
			
			
			if (tPlanType=="00") {//��ͨ����
				
				//����У��
				var tPlanFlag = cObj.PlanFlag.value;
				if (tPlanFlag==null || tPlanFlag=="") {
					alert("������ʶ����Ϊ�գ�");
					return false;
				}
				
				//ְҵУ��
				var tOccupTypeFlag = cObj.OccupTypeFlag.value;
				if (tOccupTypeFlag=="1") {//��ְҵ���
					
					var tOccupType = cObj.OccupType.value;
					var tOccupMidType = cObj.OccupMidType.value;
					var tOccupCode = cObj.OccupCode.value;
					
					if (tOccupType==null || tOccupType=="") {
						alert("ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (tOccupMidType==null || tOccupMidType=="") {
						alert("ְҵ�з��಻��Ϊ�գ�");
						return false;
					}
					
					if (tOccupCode==null || tOccupCode=="") {
						alert("���ֲ���Ϊ�գ�");
						return false;
					}
				} else {
					
					var tMinOccupType = cObj.MinOccupType.value;
					var tMaxOccupType = cObj.MaxOccupType.value;
					var tOccupRate = cObj.OccupRate.value;
					
					if (tMinOccupType==null || tMinOccupType=="") {
						alert("���ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (tMaxOccupType==null || tMaxOccupType=="") {
						alert("���ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (Number(tMinOccupType)>=Number(tMaxOccupType)) {
						alert("���ְҵ���ӦС�����ְҵ���");
						return false;
					}
					if (tOccupRate==null || tOccupRate=="") {
						alert("ְҵ��������Ϊ�գ�");
						return false;
					}
					
				}
				
				//����
				var tMinAge = cObj.MinAge.value;
				var tMaxAge = cObj.MaxAge.value;
				var tAvgAge = cObj.AvgAge.value;
				if (tAvgAge==null || tAvgAge=="") {
					alert("ƽ�����䲻��Ϊ�գ�");
					return false;
				}
				
				if (tMinAge==null || tMinAge=="") {
				
				} else {
					
					if (Number(tMinAge)>Number(tAvgAge)) {
						alert("�������Ӧ������ƽ�����䣡");
						return false;
					}
				}
				
				if (tMaxAge==null || tMaxAge=="") {
				
				} else {
					
					if (Number(tMaxAge)<Number(tAvgAge)) {
						alert("�������Ӧ��С��ƽ�����䣡");
						return false;
					}
				}
				
				//����
				var tNumPeople = cObj.NumPeople.value;
				if (tNumPeople==null || tNumPeople=="") {
					alert("��������Ϊ�գ�");
					return false;
				}
				
				//�μ��籣ռ��
				var tSocialInsuRate = cObj.SocialInsuRate.value;
				if (tSocialInsuRate==null || tSocialInsuRate=="") {
					alert("�μ��籣ռ�Ȳ���Ϊ�գ�");
					return false;
				}
				
				if (!checkDecimalFormat(tSocialInsuRate, 1, 2)) {
					alert("�μ��籣ռ��С��λ��Ӧ����2λ��");
					return false;
				}
				
				//��Ů����
				var tMaleRate = cObj.MaleRate.value;
				var tFemaleRate = cObj.FemaleRate.value;
				if (tMaleRate==null || tMaleRate=="" || tFemaleRate==null || tFemaleRate=="") {
					alert("��Ů��������Ϊ��");
					return false
				}
				
				if (Number(tMaleRate)==0 && Number(tFemaleRate)==0) {
					alert("��Ů��������ͬʱΪ0");
					return false;
				}
				
				//����ռ��
				var tRetireRate = cObj.RetireRate.value;
				if (tRetireRate==null || tRetireRate=="") {
					alert("����ռ�Ȳ���Ϊ�գ�");
					return false;
				}
				
				if (!checkDecimalFormat(tRetireRate, 1, 2)) {
					alert("����ռ��С��λ��Ӧ����2λ��");
					return false;
				}
				
				//���ѷ�̯��ʽ
				var tPremMode = cObj.PremMode.value;
				if (tPremMode==null || tPremMode=="") {
					alert("���ѷ�̯��ʽ����Ϊ�գ�");
					return false;
				}
				//��ҵ����ռ��
				var tEnterpriseRate = cObj.EnterpriseRate.value;
				if (tPremMode=="1") {//��ҵ����
					
					if (Number(tEnterpriseRate)==1) {
						
					} else {
						alert("��ҵ����ʱ����ҵ����ռ��ӦΪ1��");
						return false;
					}
				} else if (tPremMode=="2") {//���˸���
				
					if (Number(tEnterpriseRate)==0) {
						
					} else {
						alert("���˸���ʱ����ҵ����ռ��ӦΪ0��");
						return false;
					}
				} else {
					
					if (tEnterpriseRate==null || tEnterpriseRate=="") {
						alert("��ҵ���˹�ͬ����ʱ����ҵ����ռ�Ȳ���Ϊ�գ�");
						return false;
					}
					
					if (!checkDecimalFormat(tEnterpriseRate, 1, 2)) {
						alert("��ҵ����ռ��С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				//��н
				var tMinSalary = cObj.MinSalary.value;
				var tMaxSalary = cObj.MaxSalary.value;
				var tAvgSalary = cObj.AvgSalary.value;
				
				if (tMinSalary==null || tMinSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMinSalary, 8, 2)) {//У�������н����λ��С��λ����
						alert("�����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				if (tMaxSalary==null || tMaxSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMaxSalary, 8, 2)) {//У�������н����λ��С��λ����
						alert("�����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				if (tAvgSalary==null || tAvgSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tAvgSalary, 8, 2)) {//У��ƽ����н����λ��С��λ����
						alert("ƽ����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}

				if ((tMinSalary!=null&&tMinSalary!="") && (tMaxSalary!=null&&tMaxSalary!="")) {//����������н��СУ��
					
					if (Number(tMinSalary)>Number(tMaxSalary)) {
						alert("�����нӦ�����������н��");
						return false;
					}
				}
				
				if ((tMinSalary!=null&&tMinSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//�����ƽ����н��СУ��
					
					if (Number(tMinSalary)>Number(tAvgSalary)) {
						alert("�����нӦ������ƽ����н��");
						return false;
					}
				}
				
				if ((tMaxSalary!=null&&tMaxSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//ƽ���������н��СУ��
					
					if (Number(tAvgSalary)>Number(tMaxSalary)) {
						alert("ƽ����нӦ�����������н��");
						return false;
					}
				}
			} else {//��������
				
				//����У��
			}
		} else if (cTranProdType=="01") {//������
			
			var tPremCalType = cObj.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("���Ѽ��㷽ʽ����Ϊ�գ�");
				return false;
			}
			
			if (tPremCalType=="1") {
				
				var tPlanPeople = cObj.PlanPeople.value;
				if (tPlanPeople==null || tPlanPeople=="") {
					alert("����������ʱ��������������Ϊ�գ�");
					return false;
				}
			}
		} else {
			
			var tPlanType = cObj.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("�������Ͳ���Ϊ�գ�");
				return false;
			}
			
			if (tPlanType=="02") {
			
				//ְҵУ��
				var tOccupTypeFlag = cObj.OccupTypeFlag.value;
				if (tOccupTypeFlag=="1") {//��ְҵ���
					
					var tOccupType = cObj.OccupType.value;
					
					if (tOccupType==null || tOccupType=="") {
						alert("ְҵ�����Ϊ�գ�");
						return false;
					}
				} else {
					
					var tMinOccupType = cObj.MinOccupType.value;
					var tMaxOccupType = cObj.MaxOccupType.value;
					var tOccupRate = cObj.OccupRate.value;
					
					if (tMinOccupType==null || tMinOccupType=="") {
						alert("���ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (tMaxOccupType==null || tMaxOccupType=="") {
						alert("���ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (Number(tMinOccupType)>=Number(tMaxOccupType)) {
						alert("���ְҵ���ӦС�����ְҵ���");
						return false;
					}
					if (tOccupRate==null || tOccupRate=="") {
						alert("ְҵ��������Ϊ�գ�");
						return false;
					}
				}
				
				//����
				var tMinAge = cObj.MinAge.value;
				var tMaxAge = cObj.MaxAge.value;
				var tAvgAge = cObj.AvgAge.value;
				if (tAvgAge==null || tAvgAge=="") {
					alert("ƽ�����䲻��Ϊ�գ�");
					return false;
				}
				
				if (tMinAge==null || tMinAge=="") {
				
				} else {
					
					if (Number(tMinAge)>Number(tAvgAge)) {
						alert("�������Ӧ������ƽ�����䣡");
						return false;
					}
				}
				
				if (tMaxAge==null || tMaxAge=="") {
				
				} else {
					
					if (Number(tMaxAge)<Number(tAvgAge)) {
						alert("�������Ӧ��С��ƽ�����䣡");
						return false;
					}
				}
				
				//����
				var tNumPeople = cObj.NumPeople.value;
				if (tNumPeople==null || tNumPeople=="") {
					alert("��������Ϊ�գ�");
					return false;
				}
				
				//�μ��籣ռ��
				var tSocialInsuRate = cObj.SocialInsuRate.value;
				if (tSocialInsuRate==null || tSocialInsuRate=="") {
					alert("�μ��籣ռ�Ȳ���Ϊ�գ�");
					return false;
				}
				
				if (!checkDecimalFormat(tSocialInsuRate, 1, 2)) {
					alert("�μ��籣ռ��С��λ��Ӧ����2λ��");
					return false;
				}
				
				//��Ů����
				var tMaleRate = cObj.MaleRate.value;
				var tFemaleRate = cObj.FemaleRate.value;
				if (tMaleRate==null || tMaleRate=="" || tFemaleRate==null || tFemaleRate=="") {
					alert("��Ů��������Ϊ��");
					return false
				}
				
				if (Number(tMaleRate)==0 && Number(tFemaleRate)==0) {
					alert("��Ů��������ͬʱΪ0");
					return false;
				}
				
				//����ռ��
				var tRetireRate = cObj.RetireRate.value;
				if (tRetireRate==null || tRetireRate=="") {
					alert("����ռ�Ȳ���Ϊ�գ�");
					return false;
				}
				
				if (!checkDecimalFormat(tRetireRate, 1, 2)) {
					alert("�μ��籣ռ��С��λ��Ӧ����2λ��");
					return false;
				}
				
				//���ѷ�̯��ʽ
				var tPremMode = cObj.PremMode.value;
				if (tPremMode==null || tPremMode=="") {
					alert("���ѷ�̯��ʽ����Ϊ�գ�");
					return false;
				}
				//��ҵ����ռ��
				var tEnterpriseRate = cObj.EnterpriseRate.value;
				if (tPremMode=="1") {//��ҵ����
					
					if (Number(tEnterpriseRate)==1) {
						
					} else {
						alert("��ҵ����ʱ����ҵ����ռ��ӦΪ1��");
						return false;
					}
				} else if (tPremMode=="2") {//���˸���
				
					if (Number(tEnterpriseRate)==0) {
						
					} else {
						alert("���˸���ʱ����ҵ����ռ��ӦΪ0��");
						return false;
					}
				} else {
					
					if (tEnterpriseRate==null || tEnterpriseRate=="") {
						alert("��ҵ���˹�ͬ����ʱ����ҵ����ռ�Ȳ���Ϊ�գ�");
						return false;
					}
					
					if (!checkDecimalFormat(tEnterpriseRate, 1, 2)) {
						alert("��ҵ����ռ��С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				//��н
				var tMinSalary = cObj.MinSalary.value;
				var tMaxSalary = cObj.MaxSalary.value;
				var tAvgSalary = cObj.AvgSalary.value;
				
				if (tMinSalary==null || tMinSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMinSalary, 8, 2)) {//У�������н����λ��С��λ����
						alert("�����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				if (tMaxSalary==null || tMaxSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMaxSalary, 8, 2)) {//У�������н����λ��С��λ����
						alert("�����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				if (tAvgSalary==null || tAvgSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tAvgSalary, 8, 2)) {//У��ƽ����н����λ��С��λ����
						alert("ƽ����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
      	
				if ((tMinSalary!=null&&tMinSalary!="") && (tMaxSalary!=null&&tMaxSalary!="")) {//����������н��СУ��
					
					if (Number(tMinSalary)>Number(tMaxSalary)) {
						alert("�����нӦ�����������н��");
						return false;
					}
				}
				
				if ((tMinSalary!=null&&tMinSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//�����ƽ����н��СУ��
					
					if (Number(tMinSalary)>Number(tAvgSalary)) {
						alert("�����нӦ������ƽ����н��");
						return false;
					}
				}
				
				if ((tMaxSalary!=null&&tMaxSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//ƽ���������н��СУ��
					
					if (Number(tAvgSalary)>Number(tMaxSalary)) {
						alert("ƽ����нӦ�����������н��");
						return false;
					}
				}
			} else {//�����˻�
			
				//��У��
			}
		}
	} else {
		
		if (cTranProdType=="00" || cTranProdType=="03") {//��ͨ����
			
			var tPlanType = cObj.PlanType.value;
			if (tPlanType==null || tPlanType=="") {
				alert("�������Ͳ���Ϊ�գ�");
				return false;
			}
			
			if (tPlanType=="00") {//��ͨ����
				
				//����У��
				var tPlanFlag = cObj.PlanFlag.value;
				if (tPlanFlag==null || tPlanFlag=="") {
					alert("������ʶ����Ϊ�գ�");
					return false;
				}
				
				var tInsuPeriod = cObj.InsuPeriod.value;
				var tInsuPeriodFlag = cObj.InsuPeriodFlag.value;
				if (tInsuPeriod==null || tInsuPeriod=="" || tInsuPeriodFlag==null || tInsuPeriodFlag=="") {
					alert("�������޲���Ϊ�գ�");
					return false;
				}
				
				//ְҵУ��
				var tOccupTypeFlag = cObj.OccupTypeFlag.value;
				if (tOccupTypeFlag=="1") {//��ְҵ���
					
					var tOccupType = cObj.OccupType.value;
					var tOccupMidType = cObj.OccupMidType.value;
					var tOccupCode = cObj.OccupCode.value;
					
					if (tOccupType==null || tOccupType=="") {
						alert("ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (tOccupMidType==null || tOccupMidType=="") {
						alert("ְҵ�з��಻��Ϊ�գ�");
						return false;
					}
					
					if (tOccupCode==null || tOccupCode=="") {
						alert("���ֲ���Ϊ�գ�");
						return false;
					}
				} else {
					
					var tMinOccupType = cObj.MinOccupType.value;
					var tMaxOccupType = cObj.MaxOccupType.value;
					var tOccupRate = cObj.OccupRate.value;
					
					if (tMinOccupType==null || tMinOccupType=="") {
						alert("���ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (tMaxOccupType==null || tMaxOccupType=="") {
						alert("���ְҵ�����Ϊ�գ�");
						return false;
					}
					
					if (Number(tMinOccupType)>=Number(tMaxOccupType)) {
						alert("���ְҵ���ӦС�����ְҵ���");
						return false;
					}
					
					if (tOccupRate==null || tOccupRate=="") {
						alert("ְҵ��������Ϊ�գ�");
						return false;
					}
				}
				
				//����
				var tMinAge = cObj.MinAge.value;
				var tMaxAge = cObj.MaxAge.value;
				var tAvgAge = cObj.AvgAge.value;
				if (tAvgAge==null || tAvgAge=="") {
					alert("ƽ�����䲻��Ϊ�գ�");
					return false;
				}
				
				if (tMinAge==null || tMinAge=="") {
				
				} else {
					
					if (Number(tMinAge)>Number(tAvgAge)) {
						alert("�������Ӧ������ƽ�����䣡");
						return false;
					}
				}
				
				if (tMaxAge==null || tMaxAge=="") {
				
				} else {
					
					if (Number(tMaxAge)<Number(tAvgAge)) {
						alert("�������Ӧ��С��ƽ�����䣡");
						return false;
					}
				}
				
				//����
				var tNumPeople = cObj.NumPeople.value;
				/*
				if (tNumPeople==null || tNumPeople=="") {
					alert("��������Ϊ�գ�");
					return false;
				}*/
				
				
				//�μ��籣ռ��
				var tSocialInsuRate = cObj.SocialInsuRate.value;
				if (tSocialInsuRate==null || tSocialInsuRate=="") {
					alert("�μ��籣ռ�Ȳ���Ϊ�գ�");
					return false;
				} else {
					
					if (!checkDecimalFormat(tSocialInsuRate, 1, 2)) {
						alert("�μ��籣ռ��С��λ��Ӧ����2λ��");
						return false;
					}
				}

				//��Ů����
				var tMaleRate = cObj.MaleRate.value;
				var tFemaleRate = cObj.FemaleRate.value;
				if (tMaleRate==null || tMaleRate=="" || tFemaleRate==null || tFemaleRate=="") {
					alert("��Ů�������ܿգ�");
					return false;
				} else if (tMaleRate!=null && tMaleRate!="" && tFemaleRate!=null && tFemaleRate!="") {
					
					if (Number(tMaleRate)==0 && Number(tFemaleRate)==0) {
						alert("��Ů��������ͬʱΪ0��");
						return false;
					}
				} 
				
				//����ռ��
				var tRetireRate = cObj.RetireRate.value;
				if (tRetireRate==null || tRetireRate=="") {
					
				} else {
					
					if (!checkDecimalFormat(tRetireRate, 1, 2)) {
						alert("�μ��籣ռ��С��λ��Ӧ����2λ��");
						return false;
					}
				}

				//���ѷ�̯��ʽ
				var tPremMode = cObj.PremMode.value;
				if (tPremMode==null || tPremMode=="") {

				} else {  
					
					//��ҵ����ռ��
					var tEnterpriseRate = cObj.EnterpriseRate.value;
					if (tPremMode=="1") {//��ҵ����
						
						if (Number(tEnterpriseRate)==1) {
							
						} else {
							alert("��ҵ����ʱ����ҵ����ռ��ӦΪ1��");
							return false;
						}
					} else if (tPremMode=="2") {//���˸���
					
						if (Number(tEnterpriseRate)==0) {
							
						} else {
							alert("���˸���ʱ����ҵ����ռ��ӦΪ0��");
							return false;
						}
					} else {
						
						if (tEnterpriseRate==null || tEnterpriseRate=="") {
							alert("��ҵ���˹�ͬ����ʱ����ҵ����ռ�Ȳ���Ϊ�գ�");
							return false;
						}
						
						if (!checkDecimalFormat(tEnterpriseRate, 1, 2)) {
							alert("��ҵ����ռ��С��λ��Ӧ����2λ��");
							return false;
						}
					}
				}
				
				//��н
				var tMinSalary = cObj.MinSalary.value;
				var tMaxSalary = cObj.MaxSalary.value;
				var tAvgSalary = cObj.AvgSalary.value;
				
				if (tMinSalary==null || tMinSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMinSalary, 8, 2)) {//У�������н����λ��С��λ����
						alert("�����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				if (tMaxSalary==null || tMaxSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tMaxSalary, 8, 2)) {//У�������н����λ��С��λ����
						alert("�����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}
				
				if (tAvgSalary==null || tAvgSalary=="") {
				
				} else {
					
					if (!checkDecimalFormat(tAvgSalary, 8, 2)) {//У��ƽ����н����λ��С��λ����
						alert("ƽ����н����λ��Ӧ����8λ,С��λ��Ӧ����2λ��");
						return false;
					}
				}

				if ((tMinSalary!=null&&tMinSalary!="") && (tMaxSalary!=null&&tMaxSalary!="")) {//����������н��СУ��
					
					if (Number(tMinSalary)>Number(tMaxSalary)) {
						alert("�����нӦ�����������н��");
						return false;
					}
				}
				
				if ((tMinSalary!=null&&tMinSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//�����ƽ����н��СУ��
					
					if (Number(tMinSalary)>Number(tAvgSalary)) {
						alert("�����нӦ������ƽ����н��");
						return false;
					}
				}
				
				if ((tMaxSalary!=null&&tMaxSalary!="") && (tAvgSalary!=null&&tAvgSalary!="")) {//ƽ���������н��СУ��
					
					if (Number(tAvgSalary)>Number(tMaxSalary)) {
						alert("ƽ����нӦ�����������н��");
						return false;
					}
				}
			} else {//��������
				
				//����У��
			}
		} else if (cTranProdType=="01") {//������
			
			var tPremCalType = cObj.PremCalType.value;
			if (tPremCalType==null || tPremCalType=="") {
				alert("���Ѽ��㷽ʽ����Ϊ�գ�");
				return false;
			}
			
			if (tPremCalType=="1") {//������
				
				var tPlanPeople = cObj.PlanPeople.value;
				if (tPlanPeople==null || tPlanPeople=="") {
					alert("����������ʱ��������������Ϊ�գ�");
					return false;
				}
			} else if (tPremCalType=="2") {//���������
				
				var tEnginCost = cObj.EnginCost.value;
				if (tEnginCost==null || tEnginCost=="") {
					alert("��������ۼ���ʱ����͹�����۲���Ϊ�գ�");
					return false;
				}
				
				//�������,ӦΪ����0����Ч����
				if (!isNumeric(tEnginCost) || Number(tEnginCost)<0) {
					alert("��͹������ӦΪ����0����Ч���֣�");
					return false;
				}
				
				if (!checkDecimalFormat(tEnginCost, 18, 2)) {
					alert("��͹����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
					return false;
				}
				
				var tMaxEnginCost = cObj.MaxEnginCost.value;
				if (tMaxEnginCost==null || tMaxEnginCost=="") {
					alert("��������ۼ���ʱ����߹�����۲���Ϊ�գ�");
					return false;
				}
				
				//�������,ӦΪ����0����Ч����
				if (!isNumeric(tMaxEnginCost) || Number(tMaxEnginCost)<0) {
					alert("��߹������ӦΪ����0����Ч���֣�");
					return false;
				}
				
				if (!checkDecimalFormat(tMaxEnginCost, 18, 2)) {
					alert("��߹����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
					return false;
				}
				if (Number(tEnginCost) > Number(tMaxEnginCost)) {
					alert("��͹������ӦС�ڵ�����߹�����ۣ�");
					return false;
				}
				
			} else {//���������
				
				var tEnginArea = cObj.EnginArea.value;
				if (tEnginArea==null || tEnginArea=="") {
					alert("�������������ʱ����͹����������Ϊ�գ�");
					return false;
				}
				
				//�������,ӦΪ����0����Ч����
				if (!isNumeric(tEnginArea) || Number(tEnginArea)<0) {
					alert("��͹������ӦΪ����0����Ч���֣�");
					return false;
				}
				
				if (!checkDecimalFormat(tEnginArea, 18, 2)) {
					alert("��͹����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
					return false;
				}
				
				var tMaxEnginArea = cObj.MaxEnginArea.value;
				if (tMaxEnginArea==null || tMaxEnginArea=="") {
					alert("�������������ʱ����߹����������Ϊ�գ�");
					return false;
				}
				
				//�������,ӦΪ����0����Ч����
				if (!isNumeric(tMaxEnginArea) || Number(tMaxEnginArea)<0) {
					alert("��߹������ӦΪ����0����Ч���֣�");
					return false;
				}
				
				if (!checkDecimalFormat(tMaxEnginArea, 18, 2)) {
					alert("��߹����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
					return false;
				}
				if (Number(tEnginArea) > Number(tMaxEnginArea)) {
					alert("��͹������ӦС�ڵ�����߹��������");
					return false;
				}
			}
			
			var tInsuPeriod = cObj.InsuPeriod.value;
			var tInsuPeriodFlag = cObj.InsuPeriodFlag.value;
			if (tInsuPeriod==null || tInsuPeriod=="" || tInsuPeriodFlag==null || tInsuPeriodFlag=="") {
				alert("�������޲���Ϊ�գ�");
				return false;
			}
			
			if (isEmpty(cObj.EnginType)) {
				alert("�������Ͳ���Ϊ�գ�");
				return false;
			}
			
			if (isEmpty(cObj.EnginDays)) {
				alert("ʩ����������Ϊ�գ�");
				return false;
			}
			
			if (isEmpty(cObj.EnginDesc)) {
				alert("���̸�������Ϊ�գ�");
				return false;
			}
			
			var tEnginDesc = cObj.EnginDesc.value;
			if (tEnginDesc.length>1000) {
				alert("���̸������ܳ���1000�ֳ���");
				return false;
			}
  		
			if (document.getElementById("trEnginCondition").style.display=="") {
				
				if (isEmpty(cObj.EnginCondition)) {
					alert("��������ϸ��ѡ�˵�·�������������ˮ��վ�ȹ�����Ϣʱ������״��˵������Ϊ�գ�");
					return false;
				}
		  		
				var tEnginCondition = cObj.EnginCondition.value;
				if (tEnginCondition.length>1000) {
					alert("����״��˵�����ܳ���1000�ֳ���");
					return false;
				}
			}
			
			if (!checkEnginFactor(cObj)) {
				return false;
			}
			
			if (document.getElementById("trEnginCondition").style.display=="") {
				
				if (isEmpty(cObj.EnginCondition)) {
					alert("��������ϸ��ѡ�˵�·�������������ˮ��վ�ȹ�����Ϣʱ������״��˵������Ϊ�գ�");
					return false;
				}
				
				var tEnginCondition = cObj.EnginCondition.value;
				if (tEnginCondition.length>1000) {
					alert("����״��˵�����ܳ���1000�ֳ���");
					return false;
				}
			}
		}
	}
	
	return true;
}

/**
 * �����޸�ǰУ��
 */
function pubBeforeModifyPlan(cObj, cTranProdType) {

	//��������/���Ѽ��㷽��������޸�
	if (cTranProdType=="00" || cTranProdType=="03" || cTranProdType=="02") {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPlanType = cObj.PlanType.value;
		var tMulPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		
		if (tPlanType!=tMulPlanType) {
			alert("�޸ķ���ʱ�����ܱ���������ͣ�");
			return false;
		}
	} else if (cTranProdType=="01") {
		
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPremCalType = cObj.PremCalType.value;
		var tMulPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		if (tPremCalType!=tMulPremCalType) {
			alert("�޸ķ���ʱ�����ܱ�����Ѽ��㷽ʽ��");
			return false;
		}
	}
	
	//��ͨ���֡��������֣����޸ķ���ʱ��ְҵ�����ɵ����ʱ������зǷ����ۿ۵������������ͣ�ְҵ���Ͳ����޸�
	if (tTranProdType=="00" || tTranProdType=="03") {
		
		var tSelNo = PlanInfoGrid.getSelNo()-1;
		var tOccupTypeFlagOld = PlanInfoGrid.getRowColData(tSelNo,10);
		if (tOccupTypeFlagOld=="1") {
			
			var tOccupTypeFlagNew = cObj.OccupTypeFlag.value;
			if (tOccupTypeFlagOld != tOccupTypeFlagNew) {
				
				var tSysPlanCode =  PlanInfoGrid.getRowColData(tSelNo,1);
				var tPlanCode =  PlanInfoGrid.getRowColData(tSelNo,2);
				
//				tSQLInfo = new SqlClass();
//				tSQLInfo.setResourceName("g_quot.LSQuotSql");
//				tSQLInfo.setSqlId("LSQuotSql39");
//				tSQLInfo.addSubPara(tQuotNo);
//				tSQLInfo.addSubPara(tQuotBatNo);
//				tSQLInfo.addSubPara(tSysPlanCode);
//				tSQLInfo.addSubPara(tPlanCode);
//				
//				var tArrNum = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//				if (tArrNum[0][0]>0) {
//					alert("������ϸ�к��зǷ����ۿ۵������������ͣ�ְҵ���Ͳ����޸ģ�"); 
//					return false;
//				}
			}
		}
	}

	return true;
}
