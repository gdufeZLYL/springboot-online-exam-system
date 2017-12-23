package com.zzqnxx.dao;


import com.zzqnxx.model.Paper;

import java.util.List;

public interface PaperDao {

    /**
     * 添加试卷信息
     * @param paper
     * @return
     */
    int insertPaper(Paper paper);

    /**
     * 删除试卷信息
     * @param id
     * @return
     */
    boolean deletePaper(int id);

    /**
     * 更新试卷信息
     * @param id
     * @param paperName
     * @return
     */
    boolean updatePaper(int id, String paperName);

    /**
     * 根据试卷名称获取试卷信息
     * @param paperName
     * @return
     */
    List<Paper> queryByPaperName(String paperName);

    /**
     * 获取试卷数量
     * @return
     */
    int queryAllCount();

    /**
     * 分页获取试卷信息
     * @param page
     * @param num
     * @return
     */
    List<Paper> queryAll(int page, int num);

    /**
     * 检查该试卷ID是否存在
     * @param paperId
     * @return
     */
    boolean checkPaperId(int paperId);

    /**
     * 获取试卷数量
     * @return
     */
    int queryCountByIdAndName(String id, String paperName);

    /**
     * 分页获取试卷信息
     * @param id
     * @param paperName
     * @param page
     * @param num
     * @return
     */
    List<Paper> queryByIdAndName(String id, String paperName, int page, int num);
}
