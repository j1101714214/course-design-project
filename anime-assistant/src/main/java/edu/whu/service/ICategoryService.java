package edu.whu.service;

import edu.whu.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
public interface ICategoryService extends IService<Category> {

    List<Category> getAllCategories();

    Long addNewCategory(Category category);
}
