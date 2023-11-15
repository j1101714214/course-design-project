package edu.whu;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@Data
public class Category {

    /**
     * 分类信息id
     */
    private Long id;

    /**
     * 分类信息名称
     */
    private String name;

    /**
     * 分类详细信息
     */
    private String description;


}
