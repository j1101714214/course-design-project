package whu.edu.aria2rpc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import whu.edu.aria2rpc.entity.DownloadInfo;

@Mapper
public interface DownloadInfoDao extends BaseMapper<DownloadInfo> {
}
