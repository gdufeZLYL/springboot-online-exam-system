package com.zzqnxx.dao;

import com.zzqnxx.model.Admin;

public interface AdminDao {

    /**
     * 更新密码
     * @param id
     * @param password
     * @return
     */
    boolean updatePassword(int id, String password);

    /**
     * 查询管理员
     * @param username
     * @param password
     * @return
     */
    Admin queryAdminByUsernameAndPassword(String username,
                                            String password);

    /**
     * 查询管理员
     * @param username
     * @return
     */
    Admin queryAdminByUsername(String username);
}
