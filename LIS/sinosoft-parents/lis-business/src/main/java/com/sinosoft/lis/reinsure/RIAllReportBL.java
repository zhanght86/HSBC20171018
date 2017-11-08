

package com.sinosoft.lis.reinsure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class RIAllReportBL {
	private String base;
	private String errMsg;
	private String runDate;
	private String mOperate = "";
	private GlobalInput mGlobalInput = new GlobalInput();

	public RIAllReportBL(String runDate) {
		this.runDate=runDate;
		this.base = new ExeSQL().getOneValue("SELECT a.sysvarvalue FROM ldsysvar a WHERE a.sysvar='RITempDir'");
		
		String[] spDate = this.runDate.split("-");
		String year = spDate[0];
		String month = spDate[1];
		String day = spDate[2];
		this.base = base + File.separator + year + File.separator + month
				+ File.separator + day ;
		
	}

	public boolean execute() throws IOException {
		File base = new File(this.base);
		if (!base.exists()) {
			base.mkdirs();
		}
		if (!base.exists() || !base.isDirectory()) {
			return false;
		}
		if ("".equals(this.mOperate)) {
			return this.exportTraditional();
		}
		return false;
	}

	private boolean exportTraditional() {
		String sql = "select * from (SELECT a.contno Pol_num, a.polno Pol_no, getcodename('appflag', a.appflag) Policy_status, (select decode(availstate(a.contno, '"
				+ runDate
				+ "', '"
				+ runDate
				+ "'),'0','有效','失效') from dual) Coverage_status, (select CodeName from LDCode where trim(CodeType) = lower('contterminatereason') and Code = ((select max(StateReason) from lccontstate where StateType = 'Terminate' and state = '1' and contno = a.contno and (polno = a.polno or polno = '000000')))) Termination_reason, rt.incomecompanyno Income_company_no, a.riskcode Product_Code, max(rt.CessionAmount) Current_Cession_Amount, (case when (sum(rt.curentamnt)=0 or sum(rt.CessionAmount)=0) then max(rt.cessionrate) else round(sum(rt.CessionAmount) / sum(rt.curentamnt),6) end) Cession_Rate, decode(a.reinsureflag, '', '否', '是') Reinsure_type from lcpol a, RIRecordTrace rt where a.contno = rt.contno and a.riskcode = rt.riskcode and rt.EventType not in ('04','11') and not exists (SELECT k.unikey FROM riunikey k WHERE k.unikey=a.contno||','||a.polno||','||rt.incomecompanyno||','||'"+runDate.substring(0,7)+"' and k.keytype='02') GROUP BY a.contno, a.polno, a.appflag, a.riskcode, rt.incomecompanyno,a.reinsureflag ORDER BY a.contno, a.polno, rt.incomecompanyno) where rownum<=5000";
		
		String insertSql = "insert into riunikey SELECT Pol_num||','||Pol_no||','||Income_company_no||','||'"+runDate.substring(0,7)+"','02' from (SELECT a.contno Pol_num, a.polno Pol_no, getcodename('appflag', a.appflag) Policy_status, (select decode(availstate(a.contno, '"
				+ runDate
				+ "', '"
				+ runDate
				+ "'),'0','有效','失效') from dual) Coverage_status, (select CodeName from LDCode where trim(CodeType) = lower('contterminatereason') and Code = ((select max(StateReason) from lccontstate where StateType = 'Terminate' and state = '1' and contno = a.contno and (polno = a.polno or polno = '000000')))) Termination_reason, rt.incomecompanyno Income_company_no, a.riskcode Product_Code, max(rt.CessionAmount) Current_Cession_Amount, (case when (sum(rt.curentamnt)=0 or sum(rt.CessionAmount)=0) then max(rt.cessionrate) else round(sum(rt.CessionAmount) / sum(rt.curentamnt),6) end) Cession_Rate, decode(a.reinsureflag, '', '否', '是') Reinsure_type from lcpol a, RIRecordTrace rt where a.contno = rt.contno and a.riskcode = rt.riskcode and rt.EventType not in ('04','11') and not exists (SELECT k.unikey FROM riunikey k WHERE k.unikey=a.contno||','||a.polno||','||rt.incomecompanyno||','||'"+runDate.substring(0,7)+"' and k.keytype='02') GROUP BY a.contno, a.polno, a.appflag, a.riskcode, rt.incomecompanyno,a.reinsureflag ORDER BY a.contno, a.polno, rt.incomecompanyno) where rownum<=5000";

		String dest = base + File.separator  + "RI_ALL_" + runDate
				+ ".txt";
		System.out.println(dest);
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(dest)), "UTF-8"));
			String title = "Pol_num | Pol_no | Policy_status | Coverage_status | Termination_reason | Income_company_no | Product_Code | Current_Cession_Amount | Cession_Rate | Reinsure_type";
			writer.write(title + "\n");
			ExeSQL exeSQL = new ExeSQL();
			while (true) {
				SSRS data = exeSQL.execSQL(sql);
				if(data==null){
					break;
				}
				if(data.getMaxRow()==0){
					break;
				}
				for (int i = 1; i <= data.getMaxRow(); i++) {
					for (int j = 1; j <= data.getMaxCol(); j++) {
						String cData = data.GetText(i, j);
						if (cData == null || cData.equals("null")) {
							cData = "";
						}
						writer.write(cData);
						if (j != data.getMaxCol()) {
							writer.write(" |");
						}
					}
					writer.write("\n");
					writer.flush();
				}
				if(!exeSQL.execUpdateSQL(insertSql)){
					writer.flush();
					writer.close();
					return false;
				}
			}
			writer.flush();
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.errMsg = e.getMessage();
			return false;
		}
	}
}

