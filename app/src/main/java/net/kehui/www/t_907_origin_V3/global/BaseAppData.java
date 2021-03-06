package net.kehui.www.t_907_origin_V3.global;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import net.kehui.www.t_907_origin_V3.dao.DataDao;
import net.kehui.www.t_907_origin_V3.entity.Data;

/**
 * @author li.md
 * @date 2019/7/5
 */
@Database(entities = Data.class, version = 1)
public abstract class BaseAppData extends RoomDatabase {
    public abstract DataDao dataDao();
}
