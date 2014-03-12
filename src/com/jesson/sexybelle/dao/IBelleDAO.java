package com.jesson.sexybelle.dao;

import com.jesson.sexybelle.model.Belle;

import java.util.List;

/**
 * Created by zhangdi on 14-3-6.
 */
public interface IBelleDAO {

    /**
     * 数据库插入一条记录
     *
     * @param belle
     * @return
     * @throws Exception
     */
    public boolean insert(Belle belle) throws Exception;

    /**
     * 批量插入
     *
     * @param list
     * @return
     * @throws Exception
     */
    public boolean insertAll(List<Belle> list) throws Exception;

    /**
     * 根据type查询
     *
     * @param type  类型
     * @param id    开始id
     * @param count 个数
     * @return
     * @throws Exception
     */
    public List<Belle> findAllByType(int type, long id, int count) throws Exception;

    /**
     * 随机选择
     * @param type
     * @param count
     * @return
     * @throws Exception
     */
    public List<Belle> randomFindByType(int type, int count) throws Exception;

}
