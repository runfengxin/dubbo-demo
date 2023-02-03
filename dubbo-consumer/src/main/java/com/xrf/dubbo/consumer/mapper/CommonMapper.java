package com.xrf.dubbo.consumer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper<T> extends BaseMapper<T> {

    /**
     * 全量插入,等价于insert
     *
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 只更新不为null的字段
     *
     * @param
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}