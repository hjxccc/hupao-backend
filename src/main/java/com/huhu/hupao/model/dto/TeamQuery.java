package com.huhu.hupao.model.dto;

import com.huhu.hupao.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author xxx
 * @date 2024/2/3 0:58
 */
//为什么需要请求参数包装类   1.请求参数名称和实体类不一样  2.有一些参数用不到，如果要自动生成字段，会增加理解成本
    //3.可能有些字段需要隐藏不能发给前端。4.有些字段是不关心的5.多个
    @Data
    @EqualsAndHashCode(callSuper = true)
public class TeamQuery extends PageRequest {
    /**
     * id
     */
    private Long id;


    /**
     * id 列表
     */
    private List<Long> idList;

    /**
     * 搜索关键词（同时对队伍名称和描述搜索）
     */
    private String searchText;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;




}
