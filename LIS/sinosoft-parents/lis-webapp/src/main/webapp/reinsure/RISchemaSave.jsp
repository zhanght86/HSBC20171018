<%@include file="/i18n/language.jsp"%>

<%
	//�������ƣ�LRSchemaSave.jsp
	//�����ܣ�
	//�������ڣ�2007-2-1
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getAttribute("GI"));

	RIPreceptSchema mRIPreceptSchema = new RIPreceptSchema();
	RIDivisionLineDefSet mRIDivisionLineDefSet = new RIDivisionLineDefSet();
	RIIncomeCompanySet mRIIncomeCompanySet = new RIIncomeCompanySet();
	RIRiskDivideSet mRIRiskDivideSet = new RIRiskDivideSet();
	RIRiskDivideSet testRIRiskDivideSet = new RIRiskDivideSet();
	RICalFactorValueSet mRICalFactorValueSet = new RICalFactorValueSet();
	LRPreceptBL mLRPreceptBL = new LRPreceptBL();

	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	String mRIPreceptNo = request.getParameter("RIPreceptNo");
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
	String upDe = request.getParameter("UpDe");
	String MAXLIMIT = "999999999.00"; //����޶�

	if (mOperateType.equals("SCHMINSERT")
			|| request.getParameter("AttachFalg").equals("01")) {
		mRIPreceptSchema.setRIContNo(request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(request
				.getParameter("RIPreceptNo"));
		mRIPreceptSchema.setAccumulateDefNO(request
				.getParameter("AccumulateDefNO"));
		mRIPreceptSchema.setDivisionNum(request
				.getParameter("DivisionNum"));
		mRIPreceptSchema.setCompanyNum(request
				.getParameter("CompanyNum"));
		mRIPreceptSchema.setRIPreceptName(request
				.getParameter("RIPreceptName"));
		mRIPreceptSchema.setPreceptType(request
				.getParameter("PreceptType"));
		//mRIPreceptSchema.setArithmeticID(		request.getParameter("ArithmeticID"));
		//mRIPreceptSchema.setStandbyString1(request.getParameter("ReinsuranceSubType"));
		//mRIPreceptSchema.setStandbyString2(request.getParameter("ReinsuranceType"));
		mRIPreceptSchema.setHierarchyNumType(request
				.getParameter("HierarchyNumTypeName"));
		mRIPreceptSchema.setBonus(request.getParameter("BonusType"));
		//mRIPreceptSchema.setStandbyString3(request.getParameter("HierarchyNumType"));
		mRIPreceptSchema.setAttachFalg(request
				.getParameter("AttachFalg"));
		mRIPreceptSchema.setState(request.getParameter("PreceptState"));
		mRIPreceptSchema.setArithmeticID(request
				.getParameter("ArithmeticID"));
		if (request.getParameter("AttachFalg") == "01"
				|| request.getParameter("AttachFalg").equals("01")) {
			mRIPreceptSchema.setRIMainPreceptNo(request
					.getParameter("YFType"));
		} else {
			mRIPreceptSchema.setRIMainPreceptNo("");
		}
	}
	if (mOperateType.equals("DELETE")) {
		mRIPreceptSchema.setRIContNo(request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(request
				.getParameter("RIPreceptNo"));
		mRIPreceptSchema.setAccumulateDefNO(request
				.getParameter("AccumulateDefNO"));
		mRIPreceptSchema.setDivisionNum(request
				.getParameter("DivisionNum"));
		mRIPreceptSchema.setCompanyNum(request
				.getParameter("CompanyNum"));
		mRIPreceptSchema.setRIPreceptName(request
				.getParameter("RIPreceptName"));
		mRIPreceptSchema.setArithmeticID("ArithmeticID");
		mRIPreceptSchema.setBonus(request.getParameter("BonusType"));
	}
	if (mOperateType.equals("UPDATE")) {
		mRIPreceptSchema.setRIContNo(request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(request
				.getParameter("RIPreceptNo"));
		mRIPreceptSchema.setAccumulateDefNO(request
				.getParameter("AccumulateDefNO"));
		mRIPreceptSchema.setDivisionNum(request
				.getParameter("DivisionNum"));
		mRIPreceptSchema.setCompanyNum(request
				.getParameter("CompanyNum"));
		mRIPreceptSchema.setRIPreceptName(request
				.getParameter("RIPreceptName"));
		mRIPreceptSchema.setPreceptType(request
				.getParameter("PreceptType"));
		mRIPreceptSchema.setBonus(request.getParameter("BonusType"));
		mRIPreceptSchema.setArithmeticID(request
				.getParameter("ArithmeticID"));
		mRIPreceptSchema.setAttachFalg(request
				.getParameter("AttachFalg"));
		//mRIPreceptSchema.setStandbyString1(request.getParameter("ReinsuranceSubType"));
		//mRIPreceptSchema.setStandbyString2(request.getParameter("ReinsuranceType"));
		//mRIPreceptSchema.setStandbyString3(request.getParameter("HierarchyNumType"));
		mRIPreceptSchema.setHierarchyNumType(request
				.getParameter("HierarchyNumTypeName"));
		mRIPreceptSchema.setState(request.getParameter("PreceptState"));
		if (request.getParameter("AttachFalg") == "01"
				|| request.getParameter("AttachFalg").equals("01")) {
			mRIPreceptSchema.setRIMainPreceptNo(request
					.getParameter("YFType"));
		} else {
			mRIPreceptSchema.setRIMainPreceptNo("");
		}

	}
	if (mOperateType.equals("DIVCOMINSERT")
			|| request.getParameter("AttachFalg").equals("01")) {
		String[] StrNum1 = request.getParameterValues("ContCessGridNo");
		String[] divisionLineValue = request
				.getParameterValues("ContCessGrid3");
		String[] divisionLineType = request
				.getParameterValues("ContCessGrid5");
		String[] DivisionFactor = request
				.getParameterValues("ContCessGrid6");
		String[] currency = request.getParameterValues("ContCessGrid8");
		String[] StrNum2 = request
				.getParameterValues("ScaleLineGridNo");
		String[] incomeCompanyNo = request
				.getParameterValues("ScaleLineGrid1");
		String[] incomeCompanyType = request
				.getParameterValues("ScaleLineGrid4");
		if (StrNum1 != null) {
			RIDivisionLineDefSchema tRIDivisionLineDefSchema;
			for (int i = 0; i < StrNum1.length; i++) {
				tRIDivisionLineDefSchema = new RIDivisionLineDefSchema();
				tRIDivisionLineDefSchema.setRIContNo(request
						.getParameter("RIContNo"));
				tRIDivisionLineDefSchema.setRIPreceptNo(request
						.getParameter("RIPreceptNo"));
				tRIDivisionLineDefSchema.setDivisionLineOrder(i + 1);
				if ("MaxLimit".equals(divisionLineValue[i])) {
					tRIDivisionLineDefSchema
							.setDivisionLineValue(MAXLIMIT);
					tRIDivisionLineDefSchema
							.setStandByTwo(divisionLineValue[i]);
				} else {
					tRIDivisionLineDefSchema
							.setDivisionLineValue(divisionLineValue[i]);
				}
				tRIDivisionLineDefSchema
						.setDivisionLineType(divisionLineType[i]);
				tRIDivisionLineDefSchema
						.setDivisionFactor(DivisionFactor[i]);
				tRIDivisionLineDefSchema.setCurrency(currency[i]);
				tRIDivisionLineDefSchema.setStandByOne(request
						.getParameter("HierarchyNumType"));
				mRIDivisionLineDefSet.add(tRIDivisionLineDefSchema);
			}
		}
		if (StrNum2 != null) {
			RIIncomeCompanySchema tRIIncomeCompanySchema;
			for (int i = 0; i < StrNum2.length; i++) {
				tRIIncomeCompanySchema = new RIIncomeCompanySchema();
				tRIIncomeCompanySchema.setRIContNo(request
						.getParameter("RIContNo"));
				tRIIncomeCompanySchema.setRIPreceptNo(request
						.getParameter("RIPreceptNo"));
				tRIIncomeCompanySchema.setIncomeCompanyOrder(i + 1);
				tRIIncomeCompanySchema
						.setIncomeCompanyNo(incomeCompanyNo[i]);
				tRIIncomeCompanySchema
						.setIncomeCompanyType(incomeCompanyType[i]);

				mRIIncomeCompanySet.add(tRIIncomeCompanySchema);
			}
		}
	}

	//jintao 8.25 10:00
	if (mOperateType.equals("SCALEINSERT")
			|| mOperateType.equals("SCHFEEDIV")
			|| mOperateType.equals("UPDATE")) {
		String[] StrNum1 = request.getParameterValues("ContCessGridNo");
		String[] divisionLineValue = request
				.getParameterValues("ContCessGrid3"); //�����ֵ
		String[] divisionLineType = request
				.getParameterValues("ContCessGrid5"); //��������ʹ���

		//ȥ����ͷֳ�����  
		ArrayList lineList = new ArrayList();
		for (int i = 0; i < divisionLineType.length; i++) {
			if ("MaxLimit".equals(divisionLineValue[i])) {
				divisionLineValue[i] = MAXLIMIT;
			}
			// �����������ʹ��벻���ڡ�04��----��ͷֳ������뵽linelist��
			if (!divisionLineType[i].equals("04")) {
				lineList.add(divisionLineValue[i]);
			}
		}

		//�� ����õ���lineListֵת�����ַ������� �� ����divLine����
		String[] divLine = (String[]) lineList.toArray(new String[0]);

		//�õ��ֱ�����������
		String[] StrNum2 = request
				.getParameterValues("ScaleLineGridNo");
		String[] incomeCompanyNo = request
				.getParameterValues("ScaleLineGrid1");
		String[] incomeCompanyType = request
				.getParameterValues("ScaleLineGrid4");

		String com = request.getParameter("Com");
		String line = request.getParameter("Line");

		int comNum = Integer.parseInt(com); //����
		int lineNum = Integer.parseInt(line); //����

		//���ֱ���������mulLine�е�ֵ������ά����
		String scaleSet[][] = new String[comNum][lineNum];
		for (int i = 1; i <= comNum; i++) { //��˾
			scaleSet[i - 1] = request
					.getParameterValues("CessScaleGrid" + (i + 1));
		}

		//����ֱ�����
		RIRiskDivideSchema tRIRiskDivideSchema;
		int k = 1;
		for (int i = divLine.length - 1; i >= 0; i--) { //�Ա�����ѭ��
			for (int j = 0; j < comNum; j++) { //�Թ�˾ѭ��
				tRIRiskDivideSchema = new RIRiskDivideSchema();
				tRIRiskDivideSchema.setRIContNo(request
						.getParameter("RIContNo"));
				tRIRiskDivideSchema.setRIPreceptNo(request
						.getParameter("RIPreceptNo"));

				//��¼�������
				String areaId;
				if (k < 10) {
					areaId = "0" + k;
				} else {
					areaId = k + "";
				}
				tRIRiskDivideSchema.setAreaID(areaId);
				//���ò㼶
				tRIRiskDivideSchema.setAreaLevel(lineNum - i);
				//��ӷֱ���˾����
				tRIRiskDivideSchema
						.setIncomeCompanyNo(incomeCompanyNo[j]);
				//������������
				tRIRiskDivideSchema
						.setUpperLimit(divLine[divLine.length - 1 - i]);
				//������������
				if (i == divLine.length - 1) {
					tRIRiskDivideSchema.setLowerLimit(0);
				} else {
					tRIRiskDivideSchema
							.setLowerLimit(divLine[divLine.length - 2
									- i]);
				}
				tRIRiskDivideSchema.setPossessScale(scaleSet[j][i]);
				if (mOperateType.equals("SCALEINSERT")) {
					tRIRiskDivideSchema
							.setReMark(new Date().toString());
				}
				if (mOperateType.equals("UPDATE")
						|| mOperateType.equals("SCHFEEDIV")) {
					tRIRiskDivideSchema.setReMark(request
							.getParameter("remark"));
				}
				mRIRiskDivideSet.add(tRIRiskDivideSchema);
				k++;
			}
		}
	}
	if (mOperateType.equals("FEERATEINSERT")) {
		String tRIPreceptNo = request.getParameter("RIPreceptNo");
		String[] StrNum = request.getParameterValues("FeeRateGridNo");
		String[] AreaID = request.getParameterValues("FeeRateGrid5");
		String[] PremFeeTableNo = request
				.getParameterValues("FeeRateGrid6");
		String[] ComFeeTableNo = request
				.getParameterValues("FeeRateGrid8");

		if (StrNum != null) {
			RIRiskDivideSchema tRIRiskDivideSchema;
			for (int i = 0; i < StrNum.length; i++) {
				tRIRiskDivideSchema = new RIRiskDivideSchema();
				tRIRiskDivideSchema.setRIPreceptNo(tRIPreceptNo);
				tRIRiskDivideSchema.setAreaID(AreaID[i]);
				tRIRiskDivideSchema
						.setPremFeeTableNo(PremFeeTableNo[i]);
				tRIRiskDivideSchema.setComFeeTableNo(ComFeeTableNo[i]);
				mRIRiskDivideSet.add(tRIRiskDivideSchema);

			}
		}
	}
	if (mOperateType.equals("SCHFEEDIV")) {
		String tRIPreceptNo = request.getParameter("RIPreceptNo");
		String tRIContNo = request.getParameter("RIContNo");
		String[] ComPanyNo = request.getParameterValues("FeeRateGrid1");
		String[] StrNum = request.getParameterValues("FeeRateGridNo");
		String[] AreaID = request.getParameterValues("FeeRateGrid5");
		String[] PremFeeTableNo = request
				.getParameterValues("FeeRateGrid6");
		String[] ComFeeTableNo = request
				.getParameterValues("FeeRateGrid8");

		if (StrNum != null) {
			RIRiskDivideSchema tRIRiskDivideSchema;
			for (int i = 0; i < StrNum.length; i++) {
				tRIRiskDivideSchema = new RIRiskDivideSchema();
				tRIRiskDivideSchema.setRIPreceptNo(tRIPreceptNo);
				tRIRiskDivideSchema.setRIContNo(tRIContNo);
				tRIRiskDivideSchema.setIncomeCompanyNo(ComPanyNo[i]);
				tRIRiskDivideSchema.setAreaID(AreaID[i]);
				tRIRiskDivideSchema
						.setPremFeeTableNo(PremFeeTableNo[i]);
				tRIRiskDivideSchema.setComFeeTableNo(ComFeeTableNo[i]);
				testRIRiskDivideSet.add(tRIRiskDivideSchema);

			}
		}
	}

	if (mOperateType.equals("FACTORINSERT")) {
		mRIPreceptSchema.setRIContNo(request.getParameter("RIContNo"));
		mRIPreceptSchema.setRIPreceptNo(request
				.getParameter("RIPreceptNo"));
		String tRIPreceptNo = request.getParameter("RIPreceptNo");
		String tRIContNo = request.getParameter("RIContNo");
		String[] StrNum = request.getParameterValues("FactorGridNo");
		String[] FactorCode = request.getParameterValues("FactorGrid8");
		String[] FactorName = request
				.getParameterValues("FactorGrid16");
		String[] ReComCode = request.getParameterValues("FactorGrid1");
		String[] FactorValue = request
				.getParameterValues("FactorGrid10");
		String[] Currency = request.getParameterValues("FactorGrid3");
		String[] FactorType = request.getParameterValues("FactorGrid5");
		String[] Remark = request.getParameterValues("FactorGrid11");
		String[] ValueType = request.getParameterValues("FactorGrid12");
		String[] FactorID = request.getParameterValues("FactorGrid13");
		String[] SerialNo = request.getParameterValues("FactorGrid14");
		if (StrNum != null) {
			RICalFactorValueSchema tRICalFactorValueSchema;
			for (int i = 0; i < StrNum.length; i++) {
				tRICalFactorValueSchema = new RICalFactorValueSchema();
				tRICalFactorValueSchema.setSerialNo(SerialNo[i]);
				tRICalFactorValueSchema.setReContCode(tRIContNo);
				tRICalFactorValueSchema.setRIPreceptNo(tRIPreceptNo);
				tRICalFactorValueSchema.setFactorCode(FactorCode[i]);
				tRICalFactorValueSchema.setFactorName(FactorName[i]);
				tRICalFactorValueSchema.setReComCode(ReComCode[i]);
				tRICalFactorValueSchema.setFactorValue(FactorValue[i]);
				tRICalFactorValueSchema.setCurrency(Currency[i]);
				tRICalFactorValueSchema.setFactorType(FactorType[i]);
				tRICalFactorValueSchema.setRemark(Remark[i]);
				tRICalFactorValueSchema.setValueType(ValueType[i]);
				tRICalFactorValueSchema.setFactorID(FactorID[i]);
				mRICalFactorValueSet.add(tRICalFactorValueSchema);
			}
		}
	}
	if (mOperateType.equals("SCHMINSERT")) {
		mDescType = ""+"�����ٱ�����"+"";
	}
	if (mOperateType.equals("DIVCOMINSERT")) {
		mDescType = ""+"���������,�����ٱ���˾��"+"";
	}
	if (mOperateType.equals("SCALEINSERT")) {
		mDescType = ""+"�ֱ�����"+"";
	}
	if (mOperateType.equals("FACTORINSERT")) {
		mDescType = ""+"�������ֱ�Ҫ����Ϣ"+"";
	}
	if (mOperateType.equals("UPDATE")) {
		mDescType = ""+"�޸��ٱ�������Ϣ"+"";
	}
	if (mOperateType.equals("DELETE")) {
		mDescType = ""+"ɾ���ٱ�����"+"";
	}
	if (mOperateType.equals("QUERY")) {
		mDescType = ""+"��ѯ�ٱ�����"+"";
	}
	if (mOperateType.equals("FEERATEINSERT")) {
		mDescType = ""+"������������"+"";
	}
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("RIPreceptNo", mRIPreceptNo);
	tVData.addElement(tTransferData);
	try {
		tVData.addElement(globalInput);
		if (mOperateType.equals("SCHMINSERT")
				|| request.getParameter("AttachFalg").equals("01")) {
			tVData.addElement(mRIPreceptSchema);
		}
		if (mOperateType.equals("DELETE")) {
			tVData.addElement(mRIPreceptSchema);
		}
		if (mOperateType.equals("UPDATE")) {
			tVData.addElement(mRIPreceptSchema);
			tVData.addElement(mRIRiskDivideSet);
			tVData.addElement(upDe);
		}
		if (mOperateType.equals("DIVCOMINSERT")
				|| request.getParameter("AttachFalg").equals("01")) {
			tVData.addElement(mRIDivisionLineDefSet);
			tVData.addElement(mRIIncomeCompanySet);
		}
		if (mOperateType.equals("SCALEINSERT")) {
			tVData.addElement(mRIRiskDivideSet);
		}
		if (mOperateType.equals("FACTORINSERT")) {
			tVData.addElement(mRIPreceptSchema);
			tVData.addElement(mRICalFactorValueSet);
		}
		if (mOperateType.equals("FEERATEINSERT")) {
			tVData.addElement(mRIRiskDivideSet);
		}
		//jintao 10:00  8.25
		if (mOperateType.equals("SCHFEEDIV")) {
			tVData.addElement(mRIRiskDivideSet);
			tVData.addElement(testRIRiskDivideSet);
		}
		if (!mLRPreceptBL.submitData(tVData, mOperateType)) {
			tError = mLRPreceptBL.mErrors;
		}
	} catch (Exception ex) {
		Content = mDescType + ""+"ʧ�ܣ�ԭ����:"+"" + ex.toString();
		FlagStr = "Fail";
	}
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	String result = "";
	if (FlagStr == "") {
		tError = mLRPreceptBL.mErrors;
		if (!tError.needDealError()) {
			TransferData sTransferData = (TransferData) mLRPreceptBL
					.getResult().getObjectByObjectName("TransferData",
							0);
			result = (String) sTransferData.getValueByName("PreceptNo");
			Content = mDescType + ""+"�ɹ���"+"" + " "+"�ٱ�������"+"" + result;
			if (mOperateType.equals("SCHMINSERT")) {
				Content = Content + ""+"������������ߺͷֱ�������!"+"";
			}
			if (mOperateType.equals("DIVCOMINSERT")) {
				Content = Content + ""+"�������÷ֱ�����!"+"";
			}
			if (mOperateType.equals("SCALEINSERT")) {
				Content = Content;
			}
			if (mOperateType.equals("FACTORINSERT")) {
				Content = Content;
			}
			if (mOperateType.equals("UPDATE")) {
				mDescType = ""+"�޸��ٱ�������Ϣ"+"";
			}
			if (mOperateType.equals("DELETE")) {
				mDescType = ""+"ɾ���ٱ�����"+"";
			}
			if (mOperateType.equals("QUERY")) {
				mDescType = ""+"��ѯ�ٱ�����"+"";
			} else {
				mDescType = ""+"�����ɹ�"+"";
			}
			FlagStr = "Succ";
		} else {
			Content = mDescType + " "+"ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>");
</script>
</html>
