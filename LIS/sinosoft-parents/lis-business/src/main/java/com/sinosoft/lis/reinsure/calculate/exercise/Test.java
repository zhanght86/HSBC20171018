

package com.sinosoft.lis.reinsure.calculate.exercise;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.db.RIPolRecordDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class Test {
	public Test() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private int AP = 0;
	private static Test test = null;

	private Test(int a) {
		AP = a;
	}

	public static Test getObject(int a) {
		if (test == null) {
			System.out.println(" wei kong ");
			test = new Test(a);
		} else {
			System.out.println(" bu wei kong ");
		}
		return test;
	}

	public void destory() {
		test = null;
	}

	public void setAP(int a) {
		AP = a;
	}

	public int getAP() {
		return AP;
	}

	public static void main(String arg[]) {
		VData tVData = new VData();
		RIPubFun tRIPubFun1 = new RIPubFun();
		PubSubmit tPubSubmit = new PubSubmit();
		MMap mMap;
		for (int i = 1; i <= 71429; i++) {
			String insuredNo1 = tRIPubFun1.getCessRecordSerialNo();
			String insuredNo2 = tRIPubFun1.getCessRecordSerialNo();

			String sql1 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '03', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159020', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1500, 1500, 200000, 250000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, to_date('02-05-2008', 'dd-mm-yyyy'), null, null, null, null, 1000, 1000, 150000, 130000, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-05-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql2 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '03', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159022', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1500, 1500, 200000, 250000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 1000, 1000, 300000, 130000, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-05-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql3 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '01', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159020', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1000, 1000, 120000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, to_date('01-03-2008', 'dd-mm-yyyy'), null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-03-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql4 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '03', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159022', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1500, 1500, 0, 250000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 1000, 1000, 200000, 130000, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-05-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql5 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '02', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159020', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1200, 1200, 120000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, to_date('01-04-2008', 'dd-mm-yyyy'), null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-04-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql6 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '02', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159021', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1200, 1200, 120000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, to_date('02-04-2008', 'dd-mm-yyyy'), null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-04-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql7 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '02', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159022', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1200, 1200, 300000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-04-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql8 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '01', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159021', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1000, 1000, 120000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, to_date('02-03-2008', 'dd-mm-yyyy'), null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-03-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql9 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '02', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159023', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1200, 1200, 500000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-04-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql10 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '04', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159020', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1000, 1000, 150000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 50000, null, 'L001000001', 'C000000001', null, '02', null, to_date('02-05-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql11 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '04', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159023', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1000, 1000, 300000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 100000, null, 'L001000001', 'C000000001', null, '02', null, to_date('02-05-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql12 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '01', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159022', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1000, 1000, 300000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-03-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql13 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '01', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159023', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo2
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1000, 1000, 500000, 150000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, null, null, null, null, null, 0, 0, 0, 0, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-03-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";
			String sql14 = "insert into RIPOLRECORD (BATCHNO, EVENTNO, EVENTTYPE, RECORDTYPE, DATAFLAG, UNIONKEY, GRPCONTNO, PROPOSALGRPCONTNO, GRPPOLNO, GRPPROPOSALNO, CONTNO, POLNO, PROPOSALNO, CONTPLANCODE, RISKCODE, DUTYCODE, YEARS, INSUREDYEAR, SALECHNL, CVALIDATE, ENDDATE, INSUREDNO, INSUREDNAME, INSUREDSEX, INSUREDAGE, IDTYPE, IDNO, OCCUPATIONTYPE, OCCUPATIONCODE, STANDPREM, PREM, RISKAMNT, AMNT, MULT, PAYINTV, PAYYEARS, PAYENDYEARFLAG, PAYENDYEAR, GETYEARFLAG, GETYEAR, INSUYEARFLAG, INSUYEAR, ACCIYEARFLAG, ACCIYEAR, ACCIENDDATE, GETSTARTDATE, GETSTARTTYPE, PEAKLINE, GETLIMIT, GETINTV, PAYNO, PAYCOUNT, PAYMONEY, LASTPAYTODATE, CURPAYTODATE, EDORNO, FEEOPERATIONTYPE, FEEFINATYPE, ACCAMNT, PRESTANDPREM, PREPREM, PRERISKAMNT, PREAMNT, CLMNO, CLMFEEOPERATIONTYPE, CLMFEEFINATYPE, STANDGETMONEY, GETRATE, CLMGETMONEY, ACCDATE, ACCUMULATEDEFNO, RICONTNO, RIPRECEPTNO, NODESTATE, REINSREFLAG, GETDATE, STANDBYSTRING1, STANDBYSTRING2, STANDBYSTRING3, STANDBYSTRING4, STANDBYSTRING5, STANDBYDOUBLE1, STANDBYDOUBLE2, STANDBYDOUBLE3, STANDBYDATE1, STANDBYDATE2, MAKEDATE, MAKETIME) values ('0000000031', '"
					+ tRIPubFun1.getCessRecordSerialNo()
					+ "', '03', null, '02', '1', '000000000135088', '000000000135088', '000000000766027', '000000000766027', '000000039159020', '000000289181026', '000000289181026', '1', '6801', '004100', 1, 1, '01', to_date('01-01-2008', 'dd-mm-yyyy'), to_date('31-12-2008', 'dd-mm-yyyy'), '"
					+ insuredNo1
					+ "', '张三', '0', 30, '01', '110105', '1', '1', 1500, 1500, 150000, 250000, 1, 0, 1, 'Y', 1, 'Y', 1, 'Y', 1, 'Y', 1, to_date('01-01-2008', 'dd-mm-yyyy'), null, null, 0, 0, 0, null, 0, 0, null, to_date('01-05-2008', 'dd-mm-yyyy'), null, null, null, null, 1000, 1000, 120000, 130000, null, null, null, 0, 0, 0, null, 'L001000001', 'C000000001', null, '02', null, to_date('01-05-2008', 'dd-mm-yyyy'), null, null, null, null, null, 0, 0, 0, null, null, to_date('26-03-2008', 'dd-mm-yyyy'), '00:00:00')";

			mMap = new MMap();
			tVData.clear();
			mMap.put(sql1, "INSERT");
			mMap.put(sql2, "INSERT");
			mMap.put(sql3, "INSERT");
			mMap.put(sql4, "INSERT");
			mMap.put(sql5, "INSERT");
			mMap.put(sql6, "INSERT");
			mMap.put(sql7, "INSERT");
			mMap.put(sql8, "INSERT");
			mMap.put(sql9, "INSERT");
			mMap.put(sql10, "INSERT");
			mMap.put(sql11, "INSERT");
			mMap.put(sql12, "INSERT");
			mMap.put(sql13, "INSERT");
			mMap.put(sql14, "INSERT");

			tVData.add(mMap);
			if (tPubSubmit.submitData(tVData, "")) {

			}
		}
		try {
		} catch (Exception ex) {
			System.out.println(" info: " + ex.getMessage());
		}
	}

	private void jbInit() throws Exception {
	}
}