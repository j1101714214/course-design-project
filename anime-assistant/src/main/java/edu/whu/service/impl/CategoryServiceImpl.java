package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.whu.domain.Category;
import edu.whu.dao.CategoryDao;
import edu.whu.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements ICategoryService {

    @Override
    public List<Category> getAllCategories() {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getId);
        return getBaseMapper().selectList(lqw);
    }

    @Override
    public Long addNewCategory(Category category) {
        int res = getBaseMapper().insert(category);
        if (res > 0) {
            return category.getId();
        }
        else {
            return null;
        }
    }

    @Override
    public Boolean updateCategory(Long id, Category category) {
        category.setId(id);
        return getBaseMapper().updateById(category) > 0;
    }
}
