package com.example.app.common;

import cma.cimiss.RetArray2D;
import cma.cimiss.client.DataQueryClient;
import com.example.app.demo.ClibUtil;

import java.util.HashMap;

public class Test {
    public static void main(String[] args){
        DataQueryClient client = new DataQueryClient();
//        String userId = "DZQX_QXT";
//        String pwd = "dzqxt@6600";
        String userId = "BEJN_DZQX_QXT";
        String pwd = "123456";

        String interfaceId = "getSurfEleByTime";

        HashMap<String,String> params = new HashMap<String,String>();

        params.put("dataCode","SURF_CHN_MUL_HOR");
        params.put("elements","Station_ID_C,PRE_1h,PRS,RHU,VIS,WIN_S_Avg_2mi,WIN_D_Avg_2mi,Q_PRS");
        //检索要素：站号、站名、小时降水、气压、相对湿度、能见度、2分钟平均风速、2分钟风向

        params.put("times","20220705000000");
        params.put("orderby","Station_ID_C:ASC");
        params.put("limitCnt","10");

        RetArray2D retArray2D = new RetArray2D();

        try {
            //初始化接口服务连接资源
            client.initResources() ;
            //调用接口
            int rst = client.callAPI_to_array2D(userId, pwd, interfaceId, params, retArray2D) ;
            //输出结果
            if(rst == 0) { //正常返回
                ClibUtil clibUtil = new ClibUtil() ;
                clibUtil.outputRst( retArray2D ) ;
            } else { //异常返回
                System.out.println( "[error] StaElemSearchAPI_CLIB_callAPI_to_array2D." ) ;
                System.out.printf( "\treturn code: %d. \n", rst ) ;
                System.out.printf( "\terror message: %s.\n", retArray2D.request.errorMessage ) ;
            }
        } catch (Exception e) {
            //异常输出
            e.printStackTrace() ;
        } finally {
            //释放接口服务连接资源
            client.destroyResources() ;
        }
    }
}
