/***************************************************************
 * <p>ProName��EdorNCPub.js</p>
 * <p>Title��������Ϣ���÷���</p>
 * <p>Description��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/

/**
 * �ڶ���������ʼ������
 */
function pubInitContStep2(cObj , cContPlanType, cTranPremMode) {
	
	document.all("PlanCode").value = "";
	document.all("PlanDesc").value = "";
	document.all("SysPlanCode").value = "";
		
	if (cTranPremMode==null || cTranPremMode=="") {
		
	} else {
		
		cObj.PremMode.value = cTranPremMode;
		auotContShowCodeName('premmode', cTranPremMode, cObj, 'PremModeName');
		//��ҵ����ռ��,������
		if (cTranPremMode=="1") {//��ҵ����
			document.all("EnterpriseRate").readOnly = true;
			document.all("EnterpriseRate").value = "1";
		}
	}
	
	if (cContPlanType=="00" || cContPlanType=="") {//��ͨ���� Ĭ���ȶ�չʾ��ͨ����
		//�������ʹ���
		document.all("tdPlan5").style.display = "";
		document.all("tdPlan6").style.display = "";
		document.all("PlanType").value = "";
		document.all("PlanTypeName").value = "";
		
		//������ʶ����
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		//document.all("PlanFlag").value = "";
		//document.all("PlanFlagName").value = "";
		
		//���Ѽ��㷽ʽ����
		document.all("tdPlan10").style.display = "none";
		document.all("tdPlan11").style.display = "none";
		document.all("PremCalType").value = "";
		document.all("PremCalTypeName").value = "";
		
		//��������(������)
		document.all("tdPlan12").style.display = "none";
		document.all("tdPlan13").style.display = "none";
		document.all("PlanPeople").value = "";
		
		//����Ŀհ���
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
		document.all("tdPlan3").style.display = "none";
		document.all("tdPlan4").style.display = "none";
		
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		
		//ְҵ�����
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "1";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("OccupRate").value = "";
		document.all("spanOccupMid").style.display = "";
		document.all("spanOccupCode").style.display = "";
		
		//����
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		
		//����,�μ��籣ռ��,��Ůռ��
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		
		//����ռ��
		document.all("RetireRate").value = "0";
		
		//н��
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		
		//��ע
		document.all("OtherDesc").value = "";
		
	}	else if (cContPlanType=="02") {//�˻���
		
		//�������ʹ���
		document.all("tdPlan5").style.display = "";
		document.all("tdPlan6").style.display = "";
		document.all("PlanType").value = "";
		document.all("PlanTypeName").value = "";
		
		//������ʶ����
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		//document.all("PlanFlag").value = "";
		//document.all("PlanFlagName").value = "";
		
		//���Ѽ��㷽ʽ����
		document.all("tdPlan10").style.display = "none";
		document.all("tdPlan11").style.display = "none";
		document.all("PremCalType").value = "";
		document.all("PremCalTypeName").value = "";
		
		//��������(������)
		document.all("tdPlan12").style.display = "none";
		document.all("tdPlan13").style.display = "none";
		document.all("PlanPeople").value = "";
		
		//����Ŀհ���
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
		document.all("tdPlan3").style.display = "none";
		document.all("tdPlan4").style.display = "none";
		
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		
		//ְҵ�����
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "1";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("OccupRate").value = "";
		
		//����
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		
		//����,�μ��籣ռ��,��Ůռ��
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		
		//����ռ��
		document.all("RetireRate").value = "0";
		
		//н��
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		
		//��ע
		document.all("OtherDesc").value = "";
		
	} else if (cContPlanType=="01"){//������
		
		//�������ʹ���
		document.all("tdPlan5").style.display = "none";
		document.all("tdPlan6").style.display = "none";
		document.all("PlanType").value = "";
		document.all("PlanTypeName").value = "";
		
		//������ʶ����
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		document.all("PlanFlag").value = "";
		document.all("PlanFlagName").value = "";
		
		//���Ѽ��㷽ʽ����
		document.all("tdPlan10").style.display = "";
		document.all("tdPlan11").style.display = "";
		document.all("PremCalType").value = "";
		document.all("PremCalTypeName").value = "";
		
		//��������(������)
		document.all("tdPlan12").style.display = "none";
		document.all("tdPlan13").style.display = "none";
		document.all("PlanPeople").value = "";
		
		//����Ŀհ���
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
		document.all("tdPlan3").style.display = "none";
		document.all("tdPlan4").style.display = "none";
		
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		
		//ְҵ�����
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "1";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("OccupRate").value = "";
		
		//����
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		
		//����,�μ��籣ռ��,��Ůռ��
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		
		//����ռ��
		document.all("RetireRate").value = "";
		
		//н��
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		
		//��ע
		document.all("OtherDesc").value = "";
	} 
}

/**
 * �ύʱ,������������
 * 
 */
function dealRedundant(cObj, cContPlanType) {
	/*
		1.��ͨ����
		a) ��ͨ����:��ս����յı��Ѽ��㷽ʽ�ֶ�,����ְҵ���������һ��ְҵ����
		b) ��������:��ս���������������������
		2.������
		a) ��ͨ����:����ѡ��ı��Ѽ��㷽ʽ�ж������������������������
		3.�˻���
		a) �����˻�:��ս����յı��Ѽ��㷽ʽ�ֶ�,����ְҵ���������һ��ְҵ����
		b) �����˻�:��ս���������������������
	*/
		
	if (cContPlanType=="00") {//��ͨ����
		
		document.all("PremCalType").value = "";
		document.all("PlanPeople").value = "";
		
		var tPlanType = document.all("PlanType").value;
		var tOccupTypeFlag = document.all("OccupTypeFlag").value;
		
		if (tPlanType=="00") {//��ͨ����
		
			if (tOccupTypeFlag=="1") {//��ְҵ
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			} else if (tOccupTypeFlag=="2") {//��ְҵ
				
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
			} else {//���û��ְҵ���,��ô���ÿ�
			
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			}
		} else {//��������
			
			document.all("PlanFlag").value = "";
			document.all("OccupTypeFlag").value = "";
			document.all("OccupType").value = "";
			document.all("OccupTypeName").value = "";
			document.all("OccupMidType").value = "";
			document.all("OccupMidTypeName").value = "";
			document.all("OccupCode").value = "";
			document.all("OccupCodeName").value = "";
			document.all("MinOccupType").value = "";
			document.all("MinOccupTypeName").value = "";
			document.all("MaxOccupType").value = "";
			document.all("MaxOccupTypeName").value = "";
			document.all("MinAge").value = "";
			document.all("MaxAge").value = "";
			document.all("AvgAge").value = "";
			document.all("NumPeople").value = "";
			document.all("SocialInsuRate").value = "";
			document.all("MaleRate").value = "";
			document.all("FemaleRate").value = "";
			document.all("RetireRate").value = "";
			document.all("PremMode").value = "";
			document.all("PremModeName").value = "";
			document.all("EnterpriseRate").value = "";
			document.all("MinSalary").value = "";
			document.all("MaxSalary").value = "";
			document.all("AvgSalary").value = "";
			document.all("OccupRate").value = "";
		}
	} else if (cContPlanType=="01") {//������
		
		var tPremCalType = cObj.PremCalType.value;
		
		if (tPremCalType=="1") {//������
			
		} else {//���ǰ������Ľ�����,��հ�������¼�������
			
			document.all("PlanPeople").value = "";
		}
		document.all("PlanFlag").value = "";
		document.all("OccupTypeFlag").value = "";
		document.all("OccupType").value = "";
		document.all("OccupTypeName").value = "";
		document.all("OccupMidType").value = "";
		document.all("OccupMidTypeName").value = "";
		document.all("OccupCode").value = "";
		document.all("OccupCodeName").value = "";
		document.all("MinOccupType").value = "";
		document.all("MinOccupTypeName").value = "";
		document.all("MaxOccupType").value = "";
		document.all("MaxOccupTypeName").value = "";
		document.all("MinAge").value = "";
		document.all("MaxAge").value = "";
		document.all("AvgAge").value = "";
		document.all("NumPeople").value = "";
		document.all("SocialInsuRate").value = "";
		document.all("MaleRate").value = "";
		document.all("FemaleRate").value = "";
		document.all("RetireRate").value = "";
		document.all("PremMode").value = "";
		document.all("PremModeName").value = "";
		document.all("EnterpriseRate").value = "";
		document.all("MinSalary").value = "";
		document.all("MaxSalary").value = "";
		document.all("AvgSalary").value = "";
		document.all("OccupRate").value = "";
		setNumPeople(cObj, tPremCalType);
	} else {//�˻���
	
		document.all("PremCalType").value = "";
		document.all("PlanPeople").value = "";
		
		var tPlanType = document.all("PlanType").value;
		var tOccupTypeFlag = document.all("OccupTypeFlag").value;
		
		if (tPlanType=="02") {//�����˻�
		
			if (tOccupTypeFlag=="1") {//��ְҵ
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			} else if (tOccupTypeFlag=="2") {//��ְҵ
				
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
			} else {//���û��ְҵ���,��ô���ÿ�
			
				document.all("OccupType").value = "";
				document.all("OccupTypeName").value = "";
				document.all("OccupMidType").value = "";
				document.all("OccupMidTypeName").value = "";
				document.all("OccupCode").value = "";
				document.all("OccupCodeName").value = "";
				
				document.all("MinOccupType").value = "";
				document.all("MinOccupTypeName").value = "";
				document.all("MaxOccupType").value = "";
				document.all("MaxOccupTypeName").value = "";
				document.all("OccupRate").value = "";
			}
		} else {//�����˻�
			
			document.all("PlanFlag").value = "";
			document.all("OccupTypeFlag").value = "";
			document.all("OccupType").value = "";
			document.all("OccupTypeName").value = "";
			document.all("OccupMidType").value = "";
			document.all("OccupMidTypeName").value = "";
			document.all("OccupCode").value = "";
			document.all("OccupCodeName").value = "";
			document.all("MinOccupType").value = "";
			document.all("MinOccupTypeName").value = "";
			document.all("MaxOccupType").value = "";
			document.all("MaxOccupTypeName").value = "";
			document.all("MinAge").value = "";
			document.all("MaxAge").value = "";
			document.all("AvgAge").value = "";
			document.all("NumPeople").value = "";
			document.all("SocialInsuRate").value = "";
			document.all("MaleRate").value = "";
			document.all("FemaleRate").value = "";
			document.all("RetireRate").value = "";
			document.all("PremMode").value = "";
			document.all("PremModeName").value = "";
			document.all("EnterpriseRate").value = "";
			document.all("MinSalary").value = "";
			document.all("MaxSalary").value = "";
			document.all("AvgSalary").value = "";
			document.all("OccupRate").value = "";
		}
	}
}


/**
 * �ύǰ����У��
 */
function pubBeforeSubmit(cObj, cContPlanType) {

	if (isEmpty(cObj.PlanDesc)) {
		alert("������������Ϊ�գ�");
		return false;
	}
	if (cContPlanType=="00") {//��ͨ����
		
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
			var tOccupRate = cObj.OccupRate.value;
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
				
				if (tMinOccupType==null || tMinOccupType=="") {
					alert("���ְҵ�����Ϊ�գ�");
					return false;
				}
				
				if (tMaxOccupType==null || tMaxOccupType=="") {
					alert("���ְҵ�����Ϊ�գ�");
					return false;
				}
				
				if (Number(tMinOccupType)>Number(tMaxOccupType)) {
					alert("���ְҵ����ܸ������ְҵ���");
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
		} else {//��������
			
			//����У��
		}
	} else if (cContPlanType=="01") {//������
		
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
				
				if (Number(tMinOccupType)>Number(tMaxOccupType)) {
					alert("���ְҵ����ܸ������ְҵ���");
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
				return flase;
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
	return true;
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
 * ѡ�񷽰��󹫹�����
 */
function pubShowPlanInfo(cObj, cContPlanType) {
	
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
		auotContShowCodeName('occuptype', PlanInfoGrid.getRowColData(tSelNo, 12), cObj, 'OccupTypeName');
	} else if (tOccupTypeFlag=="2") {
		
		var tDoubOccupType = PlanInfoGrid.getRowColData(tSelNo, 12);
		var tDoubArr = tDoubOccupType.split("-");
		cObj.OccupType.value = "";
		cObj.OccupTypeName.value = "";
		cObj.MinOccupType.value = tDoubArr[0];
		cObj.MaxOccupType.value = tDoubArr[1];
		cObj.OccupRate.value = PlanInfoGrid.getRowColData(tSelNo, 32);
		auotContShowCodeName('occuptype', tDoubArr[0], cObj, 'MinOccupTypeName');
		auotContShowCodeName('occuptype', tDoubArr[1], cObj, 'MaxOccupTypeName');
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
	
	pubShowInfoControl(cObj, cContPlanType, tPlanType, tPremCalType, tOccupTypeFlag);

}


/**
 * ѡ�񷽰���Ϣ��,����չʾ����
 */
function pubShowInfoControl(cObj, cContPlanType, cPlanType, cPremCalType, cOccupTypeFlag) {
		
	if (cContPlanType=="00") {//��ͨ����
		
		if (cPlanType=="00") {//��ͨ����
		
			document.all("trPlan1").style.display = "";
			
			if (cOccupTypeFlag=="1") {
				
				document.all("trOccupType1").style.display = "";
				document.all("trOccupType2").style.display = "none";
			} else {
				document.all("trOccupType1").style.display = "none";
				document.all("trOccupType2").style.display = "";
			}
			
			pubChooseOccupFlag(cObj, cOccupTypeFlag);
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "";
			document.all("tdPlan9").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else {//��������
		
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	} else if (cContPlanType=="01") {//����������
	
		document.all("OccupTypeRadio1").checked = true;
		document.all("OccupTypeFlag").value = "0";
		document.all("trPlan1").style.display = "none";
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
		document.all("trPlan2").style.display = "none";
		document.all("trPlan3").style.display = "none";
		document.all("trPlan4").style.display = "none";
		document.all("trPlan5").style.display = "none";
		document.all("tdPlan8").style.display = "none";
		document.all("tdPlan9").style.display = "none";
		document.all("tdPlan1").style.display = "";
		document.all("tdPlan2").style.display = "";
			
		if (cPremCalType=="1") {
			
			document.all("tdPlan12").style.display = "";
			document.all("tdPlan13").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else {
			
			document.all("tdPlan12").style.display = "none";
			document.all("tdPlan13").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	} else if (cContPlanType=="02") {
		
		if (cPlanType=="02") {//�����˻�
		
			document.all("trPlan1").style.display = "";
			
			if (cOccupTypeFlag=="1") {
				
				document.all("trOccupType1").style.display = "";
				document.all("trOccupType2").style.display = "none";
			} else {
				document.all("trOccupType1").style.display = "none";
				document.all("trOccupType2").style.display = "";
			}
			pubChooseOccupFlag(cObj, cOccupTypeFlag);
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		} else {//�����˻�
		
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	}
}
/**
 * ѡ�񵥶�ְҵ���
 */
function pubChooseOccupFlag(cObj, cQuotFlag) {

	if (cQuotFlag=='1') {
		
		cObj.OccupTypeFlag.value = '1';
		document.all("OccupTypeRadio1").checked = true;
		document.all("trOccupType1").style.display = "";
		document.all("trOccupType2").style.display = "none";
	} else if (cQuotFlag=='2') {
		
		cObj.OccupTypeFlag.value = '2';
		document.all("OccupTypeRadio2").checked = true;
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "";
	} else {
		
		cObj.OccupTypeFlag.value = '';
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
	}
}
/**
 * ������Ϣ����������
 */
function pubPlanAfterCodeSelect(cObj, cCodeType, FieldValue) {
	
	if	(cCodeType=="quotplantype") {//ѡ�񷽰�����
		
		if (FieldValue=="00") {
			
			document.all("OccupTypeRadio1").checked = true;
			document.all("OccupTypeFlag").value = "1";
			document.all("trPlan1").style.display = "";
			document.all("trOccupType1").style.display = "";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "";
			document.all("tdPlan9").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else if (FieldValue=="01") {
			
			document.all("OccupTypeRadio1").checked = true;
			document.all("OccupTypeFlag").value = "1";
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		} else if (FieldValue=="02") {
			
			document.all("OccupTypeRadio1").checked = true;
			document.all("OccupTypeFlag").value = "1";
			document.all("trPlan1").style.display = "";
			document.all("trOccupType1").style.display = "";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "";
			document.all("trPlan3").style.display = "";
			document.all("trPlan4").style.display = "";
			document.all("trPlan5").style.display = "";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		} else if (FieldValue=="03") {
			
			document.all("trPlan1").style.display = "none";
			document.all("trOccupType1").style.display = "none";
			document.all("trOccupType2").style.display = "none";
			document.all("trPlan2").style.display = "none";
			document.all("trPlan3").style.display = "none";
			document.all("trPlan4").style.display = "none";
			document.all("trPlan5").style.display = "none";
			document.all("tdPlan8").style.display = "none";
			document.all("tdPlan9").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	} else if (cCodeType=="engincaltype") {
		
		if (FieldValue=='1') {
			
			document.all("tdPlan12").style.display = "";
			document.all("tdPlan13").style.display = "";
			document.all("tdPlan1").style.display = "none";
			document.all("tdPlan2").style.display = "none";
		} else {
			
			document.all("tdPlan12").style.display = "none";
			document.all("tdPlan13").style.display = "none";
			document.all("tdPlan1").style.display = "";
			document.all("tdPlan2").style.display = "";
		}
	}
}
/**
 * �����޸�ǰУ��
 */
function pubBeforeModifyPlan(cObj, cContPlanType) {

	//��������/���Ѽ��㷽��������޸�
	if (cContPlanType=="00" || cContPlanType=="03" || cContPlanType=="02") {
	
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPlanType = cObj.PlanType.value;
		var tMulPlanType = PlanInfoGrid.getRowColData(tSelNo, 4);
		
		if (tPlanType!=tMulPlanType) {
			alert("�޸ķ���ʱ�����ܱ���������ͣ�");
			return false;
		}
	} else if (cContPlanType=="01") {
		
		var tSelNo = PlanInfoGrid.getSelNo()-1;
	
		var tPremCalType = cObj.PremCalType.value;
		var tMulPremCalType = PlanInfoGrid.getRowColData(tSelNo, 8);
		if (tPremCalType!=tMulPremCalType) {
			alert("�޸ķ���ʱ�����ܱ�����Ѽ��㷽ʽ��");
			return false;
		}
	}
	return true;
}


/**
 * �ύǰ��У��-������ϸ
 */
function checkEnginFactor(cObj) {
	
	var arrAll;
	arrAll = document.all("divEnginFactor").getElementsByTagName("input");

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
