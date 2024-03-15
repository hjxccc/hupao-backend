package com.huhu.hupao.model.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author author
 * @since 2024-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tag")
//@ApiModel(value="Tag对象", description="标签")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

   // @ApiModelProperty(value = "id")
  //  @TableId(value = "id", type = IdType.AUTO)
    private Long id;

   // @ApiModelProperty(value = "标签名称")
    private String tagName;

  //  @ApiModelProperty(value = "用户 id")
    private Long userId;

 //   @ApiModelProperty(value = "父标签 id")
    private Long parentId;

  //  @ApiModelProperty(value = "0 - 不是, 1 - 父标签")
    private Integer isParent;

  //  @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

//    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @TableLogic
    private Integer isDelete;


}
