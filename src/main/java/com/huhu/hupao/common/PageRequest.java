package com.huhu.hupao.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xxx
 * @date 2024/2/3 10:30
 */
@Data
public class PageRequest implements Serializable {


    



    //页面大小
    protected int pageSize=10;


    //当前是第几页
    protected int pageNum=1;
}
