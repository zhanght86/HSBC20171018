

package com.sinosoft.productdef;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDProductCallBackBL {
	/** 错误的容器 */
	public CErrors mErrors = new CErrors();

	private TransferData mTransferData = null;

	private VData mResult = new VData();
	
	private String mRiskCode;
	
	private String mBatchNo;
	
	ListTable mtListTable = new ListTable();
	
    private MMap map = new MMap();
    
    private MMap mapDelete = new MMap();
	
	public PDProductCallBackBL() {
	}
	
	public boolean submitData(VData cInputData, String cOperate){
		if (!getInputData(cInputData)) {
			return false;
		}
		
		if (!dealData(cOperate)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 将传进来的数据转化成可用数据
	 * 
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
			
			mRiskCode = (String)mTransferData.getValueByName("RiskCode");
			
			if(mRiskCode == null || mRiskCode.trim().equals("")){
				mErrors.addOneError("传入的数据不完整。产品代码为空");
				return false;
			}
			
			mBatchNo = (String)mTransferData.getValueByName("batch");
			
			if(mBatchNo == null || mBatchNo.trim().equals("")){
				mErrors.addOneError("传入的数据不完整。批次号为空");
				return false;
			}
			
			System.out.println("传入的产品为：" + mRiskCode + " 批次号为：" + mBatchNo);
		} catch (Exception e) {
			mErrors.addOneError("传入的数据不完整。");
			System.out.println("传入的数据不完整，" + e.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 处理数据
	 * 
	 * @param operator
	 * @return
	 */
	private boolean dealData(String operator){
		
		if(operator != null && operator.trim().equals("callback")){
			//删除所有关于产品的信息
			deleteCurrentRiskInfo();
			
			//查出所有需要更新的表
			String findAllTablesSql = "select distinct tablecode from pd_lbriskinfo where riskcode = '" + mRiskCode + "' and serialno = '" + mBatchNo +  "'";
			ExeSQL tExeSQl = new ExeSQL();
			SSRS tSSRS = tExeSQl.execSQL(findAllTablesSql);
			//循环得到表明
			for(int i = 1; i <= tSSRS.MaxRow; i ++){
				String tableName = tSSRS.GetText(i, 1);
				System.out.println("tableName" + i + ":" + tableName);
				
				//查询出原表中这个表中有几条记录
				String countRecord = "select count(*) from pd_lbriskinfo where riskcode = '" + mRiskCode + "' and serialno = '" + mBatchNo + "' and tablecode = '" + tableName +"'";
				SSRS countSSRS = tExeSQl.execSQL(countRecord);
				String countNumStr = countSSRS.GetText(1, 1);
				int countNum = 0;
				
				if(countNumStr != null && !countNumStr.trim().equals("")){
					countNum = Integer.parseInt(countNumStr);
					System.out.println("一共有：" + countNum + "条记录！");
				}
				
				for(int k = 1; k <= countNum; k ++){			
					//查出所有列
					String findAllFieldSql = "select t.fieldcode, t.displayorder,t.isprimary from pd_basefield t where t.tablecode = upper('" + tableName + "')";
					SSRS tSSRS1 = tExeSQl.execSQL(findAllFieldSql);
					
					String updateSQL = "UPDATE " + tableName + "  ";
					String whereSQL = " WHERE 1 = 1 ";
					String insertSQL = "INSERT INTO " + tableName + " ( ";
					String valuesSQL = "  values( ";
					//标志位
					boolean isFirstFlag = true;
					boolean isInsertFirstFlag = true;
					boolean isValueFirstFlag = true;
					
					//pd_basefield表中无此表的列信息
					if(tSSRS1.MaxRow <= 0){
						continue;
					}
					
					for(int j = 1; j <= tSSRS1.MaxRow; j ++){
						String fieldName = tSSRS1.GetText(j, 1);
						
						//获得对应的值
						String findValeSql = "select standbyflag" + tSSRS1.GetText(j, 2) + " from pd_lbriskinfo where riskcode = '" + mRiskCode + "' and serialno = '" + mBatchNo + "' and tablecode = '" + tableName +"'";
						SSRS tSSRS2 = tExeSQl.execSQL(findValeSql);
						String fieldValue = tSSRS2.GetText(k, 1);
						System.out.println("fieldName" + j + ":" + fieldName + " value:" + fieldValue);
									
						//处理主键
						if(tSSRS1.GetText(j, 3).trim().equals("1")){//是主键 ，不更新，只是加到where条件
							whereSQL = whereSQL + " and " + fieldName + " = '" + fieldValue + "'";
						
						}else{//非主键 ，全部更新
							//拼装update 的SQL语句
							if(isFirstFlag){
								updateSQL = updateSQL + " SET " + fieldName + " = '" + fieldValue + "'";
								isFirstFlag = false;
							}else{
								updateSQL = updateSQL + " , " + fieldName + " = '" + fieldValue + "'";
							}
						}
						
						//拼装insert语句
						if(isInsertFirstFlag){
							insertSQL = insertSQL + fieldName;
							isInsertFirstFlag = false;
						}else{
							insertSQL = insertSQL + "," + fieldName;
						}
						
						if(isValueFirstFlag){
							valuesSQL = valuesSQL + "'" + fieldValue + "'";
							isValueFirstFlag = false;
						}else{
							valuesSQL = valuesSQL + ",'" + fieldValue + "'";
						}
					}
					
					//判断是否现在还有此记录
					String existSQL = "select count(*) from " + tableName + " " + whereSQL;
					SSRS exitSSRS = tExeSQl.execSQL(existSQL);
					
					String countStr = exitSSRS.GetText(1, 1);
					int countInt = 0;
					
					if(countStr != null && !countStr.trim().equals("")){
						countInt = Integer.parseInt(countStr);
					}
					
					if(countInt == 0){//需要insert语句
						insertSQL = insertSQL + " )" + valuesSQL + " )";
						System.out.println("插入语句为：" + insertSQL);
						this.map.put(insertSQL, "");
					}else{
						updateSQL = updateSQL + whereSQL;
						System.out.println("更新语句为：" + updateSQL);
						this.map.put(updateSQL , "");
					}				
				}
			}
			this.mResult.clear();
			this.mResult.add(this.map);
			//执行SQL
			PubSubmit tPubSubmit = new PubSubmit();
			
			if(!tPubSubmit.submitData(mResult, "update")){
				mErrors.addOneError("执行SQL出错。");
				return false;
			}
			return true;
		}
		return true;
	}
	
	/**
	 * 删除此产品的当前的所有信息
	 * 
	 * @return
	 */
	private boolean deleteCurrentRiskInfo(){
		//获得所有需要删除的表名
		String findAllTables = "select distinct b.Tablecode,a.standbyflag5 from Pd_Basetable a,PD_TableMap b where a.Tablecode=upper(b.tablecode) and trim(a.standbyflag2) in ('1','101')";
		ExeSQL tExeSQL = new ExeSQL();
		
		SSRS tSSRS = tExeSQL.execSQL(findAllTables);
		if(tSSRS == null){
			mErrors.addOneError("查询所有表名错误");
			return false;
		}

		for(int i = 1; i <= tSSRS.MaxRow; i ++){
			
			if(StrTool.cTrim(tSSRS.GetText(i, 2)).equals("")){
				continue;
			}
			
			String deleteSQL = tSSRS.GetText(i, 2).replaceAll("@RISKCODE@", this.mRiskCode);
			System.out.println("删除的语句为：" + deleteSQL);
			this.mapDelete.put(deleteSQL, "");
			
		}
		//添加到mResult中
		this.mResult.clear();
		this.mResult.add(this.mapDelete);
		
		PubSubmit tPubSubmit = new PubSubmit();
		if(!tPubSubmit.submitData(mResult, "delete")){
			mErrors.addOneError("执行SQL出错。");
			return false;
		}
		
		return true;
	}

}
