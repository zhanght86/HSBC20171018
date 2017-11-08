package com.sinosoft.ibrms;
import org.apache.log4j.Logger;
/**
 * 本类是所有规则校验的接口
 * 每一个函数代表一种业务规则校验类型
 * main是准备数据的sample
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.sinosoft.ibrms.bom.*;
import com.sinosoft.lis.db.LRResultMainDB;
import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LRResultDetailSchema;
import com.sinosoft.lis.schema.LRResultMainSchema;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.VData;

public class GrpRuleUI {
private static Logger logger = Logger.getLogger(GrpRuleUI.class);
	
	//规则模块，根据这个参数来决定执行的业务规则模块
	private String Business;
		
	
	/**
	 * funciton: 个险自动核保
	 * @param cont
	 * @param appnt
	 * @param agent
	 * @param insureds
	 * @param pols
	 * @param mainpols
	 * @param subpols
	 * @param subpol2s
	 * @param bnfs
	 * @return
	 */
public List AutoUWIndUI(BOMCont cont,BOMAgent agent,BOMInsured[] insureds,BOMPol[] pols,BOMMainPol[] mainpols,BOMSubPol[] subpols,BOMSubPol2[] subpol2s,BOMBnf[] bnfs){
		
		
		List list = new ArrayList();
		list.add(cont);
		list.add(agent);
		list.add(insureds);
		list.add(pols);
		list.add(mainpols);
		list.add(subpols);
		list.add(subpol2s);
		list.add(bnfs);
		Business = "01";
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		
		RuleTask ruleTask = new RuleTask(list,Business,"");
		
		//Map map = new HashMap();
		long startTime = System.currentTimeMillis();
		List results = (List) ruleTask.execute(); //execRule(list,sql,parameter,boms);
		long time1 = System.currentTimeMillis()-startTime;
		String contNo = cont.getContNo();
		logger.debug("------ rule task execute time is "+(System.currentTimeMillis()-startTime));
		UpdateRuleResultTask task = new UpdateRuleResultTask();
		task.setBusiness(Business);
		task.setContNo(contNo);
		task.setCurrentDate(currentDate);
		task.setCurrentTime(currentTime);
		task.setResults(results);
		task.setOperator("AutoUWIndUI");
		task.setManageCom(cont.getManageCom());
		task.setTime((int)time1);
		UpdateRuleResultThread.getInstance().addTask(task);
		
		List notPassArrayList=new ArrayList();
		SQLTaskResult rs=null;
		for(int i=0;i<results.size();i++)
		{
			 rs=(SQLTaskResult)results.get(i);
			if(rs.getErrors().getErrorCount()==0)
			{
				notPassArrayList.add(rs);
			}
		}
		return notPassArrayList;
	}

	public static void main(String[] args){}

}
