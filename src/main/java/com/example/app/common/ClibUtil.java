package com.example.app.common;

import cma.music.RetArray2D;

public class ClibUtil {
    /*
     * 输出返回结果，RetArray2D
     */
    public void outputRst( RetArray2D retArray2D ) {
        /* 1. 请求信息 */
        System.out.println( "请求信息：request，所属类：RequestInfo" ) ;
        System.out.println( "\t" + "成员：" );
        System.out.println( "\t" + "returnCode（返回码）：" + retArray2D.request.errorCode ) ;
        System.out.println( "\t" + "returnMessage（提示信息）：" + retArray2D.request.errorMessage ) ;
        System.out.println( "\t" + "requestElems（检索请求的要素）：" + retArray2D.request.requestElems ) ;
        System.out.println( "\t" + "requestParams（检索请求的参数）：" + retArray2D.request.requestParams ) ;
        System.out.println( "\t" + "requestTime（请求发起时间）：" + retArray2D.request.requestTime ) ;
        System.out.println( "\t" + "responseTime（请求返回时间）：" + retArray2D.request.responseTime ) ;
        System.out.println( "\t" + "takeTime（请求耗时，单位ms）：" + retArray2D.request.takeTime ) ;
        System.out.println() ;

        /* 2. 返回的数据  */
        System.out.println( "结果集：retArray2D，所属类：RetArray2D" ) ;
        System.out.printf( "\t" + "成员：data，类：String[][]，值(记录数：%d，要素数：%d）：\n", retArray2D.data.length, retArray2D.data[0].length ) ;
        //遍历数据：retArray2D.data
        //行数：retArray2D.data.length
        System.out.println("\t---------------------------------------------------------------------") ;
        for( int iRow = 0; iRow < retArray2D.data.length; iRow ++ ) {
            System.out.print( "\t" ) ;
            //列数： retArray2D.data[iRow].length
            for( int iCol = 0; iCol < retArray2D.data[iRow].length; iCol ++ ) {
                System.out.print( retArray2D.data[iRow][iCol] + "\t" ) ;
            }
            System.out.println() ;
            //DEMO中，最多只输出10行
            if( iRow > 10 ) {
                System.out.println( "\t......" ) ;
                break ;
            }
        }
        System.out.println("\t---------------------------------------------------------------------") ;
    }



}
