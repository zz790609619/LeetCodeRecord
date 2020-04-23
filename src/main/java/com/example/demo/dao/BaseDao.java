package com.example.demo.dao;

import java.util.List;

public interface BaseDao<T,K>{

    
    /**
     * 根据主键查询单一记录
     * @param Object key
     * @return T
     */
    public <T> T selectByPrimaryKey(K key);
    
     /**
     * 根据条件查询多条记录
     * @param T t
     * @return List<T>
     */
    public  List<T> selectByEntityList(T t);
    
     /**
     * 根据条件查询单条记录
     * @param T t
     * @return List<T>
     */
    public  T selectByEntity(T t);
    
     /**
     * 根据条件查询ID
     * @param T t
     * @return K
     */
     public  K selectById(T t);
  
     /**
     * 根据条件查询ID集合
     * @param T t
     * @return List<K>
     */
     public  List<K> selectByIds(T t);
    
     /**
     * 根据主键删除单条记录
     * @param String key
     *
     */
    public void deleteByPrimaryKey(K key);
    
     /**
     * 插入单条记录（全字段） 
     * @param T t 
     * @return int
     */
    public int insert(T t);
    
     /**
     * 插入单条记录（字段为空则不添加） 
     * @param T t 
     * @return int
     */
    public int insertSelective(T t);
    
     /**
     *  根据主键修改
     * @param T t 
     * @return int
     */
    public int updateByPrimaryKey(T t);


     /**
     *  根据ids 查询
     * @param K  keys
     * @return List<T>
     */
    public List<T> selectEntityListByPrimaryKeys(List<K> keys);

     
}