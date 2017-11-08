package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FIAboriginalDataSchema;

/**
 * <p>Title: TransType</p>
 *
 * <p>Description: 代码转换处理类借口定义</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author jw
 * @version 1.0
 */
public interface TransType {

    public String transInfo(String TransValue,FIAboriginalDataSchema tFIAboriginalDataSchema) throws Exception;

}
