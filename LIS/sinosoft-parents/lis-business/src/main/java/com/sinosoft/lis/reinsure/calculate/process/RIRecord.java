

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.vschema.RIRecordSet;
import com.sinosoft.lis.schema.RIRecordSchema;

/**
 * <p>Title: </p>
 * <p>Description: 分保记录类用于记录保全分保中分保比例和分保保额</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RIRecord {
    private static RIRecordSet mRIRecordSet = new RIRecordSet();

    private RIRecord() {
        //不允许实例化使用
    }
    public static void setRIRecordSet(RIRecordSchema tRIRecordSchema){
       RIRecord.mRIRecordSet.add(tRIRecordSchema);
    }
    public static RIRecordSet getRIRecordSet(){
        return RIRecord.mRIRecordSet;
    }
    public static void destory(){
        RIRecord.mRIRecordSet.clear();
    }
}
