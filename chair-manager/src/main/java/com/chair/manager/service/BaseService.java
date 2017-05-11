package com.chair.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chair.manager.mapper.plugin.ChairMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 公用方法做抽取，方便使用
 * 
 * @param <T>
 */
public abstract class BaseService<T> {
    
    @Autowired
    private ChairMapper<T> mapper;

    /**
     * 根据主键ID查询数据
     * 
     * @param id
     * @return
     */
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询集合
     * 
     * @param t
     * @return
     */
    public List<T> queryList(T t) {
        return mapper.select(t);
    }

    /**
     * 分页查询
     * @param t
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryListPage(T t, Integer page, Integer rows) {
        // 设置分页参数
        PageHelper.startPage(page, rows);
        //查询
        List<T> list = this.queryList(t);
        return new PageInfo<T>(list);
    }
    
    /**
     * 查询数量
     * 
     * @return
     */
    public Integer queryCount(T t){
        return this.mapper.selectCount(t);
    }
    
    /**
     * 新增数据
     * @param t
     * @return 受影响数据行数
     */
    public Integer save(T t){
        return this.mapper.insert(t);
    }
    
    /**
     * 新增数据，不为null作为参数插入
     * @param t
     * @return 受影响数据行数
     */
    public Integer saveSelective(T t){
        return this.mapper.insertSelective(t);
    }
    
    /**
     * 更新数据
     * @param t
     * @return 受影响数据行数
     */
    public Integer update(T t){
        return this.mapper.updateByPrimaryKey(t);
    }
    
    /**
     * 更新数据，不为null作为参数插入
     * @param t
     * @return 受影响数据行数
     */
    public Integer updateSelective(T t){
        return this.mapper.updateByPrimaryKeySelective(t);
    }
    
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    public Integer deleteById(Integer id){
        return this.mapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Integer deleteByIds(Integer[] ids){
        return this.mapper.deleteByIDS(ids);
    }

}
