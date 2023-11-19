package edu.whu.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author Akihabara
 * @version 1.0
 * @description PhotoMetadata: 图片元数据
 * @date 2023/11/12 19:29
 */
@Data
@ApiModel(value = "图片元数据", description = "与photo_metadata表关联")
@NoArgsConstructor
@TableName("public.photo_metadata")
public class PhotoMetadata implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "图片id")
    private Long id;

    /**
     * 所属用户id
     */
    @TableField("user_id")
    @ApiModelProperty(value = "所属任务id")
    private Long userId;

    /**
     * 图片地址
     */
    @TableField("photo_name")
    @ApiModelProperty(value = "图片存储地址")
    private String photoName;

    /**
     * 图片拍摄时间
     */
    @TableField(value = "original_time")
    @ApiModelProperty(value = "图片拍摄时间")
    private String originalTime;

    /**
     * 图片拍摄地点
     */
    @TableField("location")
    @ApiModelProperty(value = "图片拍摄地点")
    private String location;

    /**
     * 图片拍摄设备
     */
    @TableField("device")
    @ApiModelProperty(value = "图片拍摄设备")
    private String device;

    /**
     * 图片所属类别
     */
    @TableField("category")
    @ApiModelProperty(value = "图片所属类别")
    private Long category;
}
