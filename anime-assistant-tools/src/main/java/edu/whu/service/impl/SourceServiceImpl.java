package edu.whu.service.impl;

import edu.whu.domain.Source;
import edu.whu.dao.SourceDao;
import edu.whu.service.ISourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceDao, Source> implements ISourceService {

}
