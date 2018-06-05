package com.jsq.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * Created by cbc on 2017/12/28.
 */
public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {



}