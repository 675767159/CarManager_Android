package com.qcwp.carmanager.model;

import com.google.auto.value.AutoValue;
import com.minstone.testsqldelight.HockeyPlayerModel;
import com.squareup.sqldelight.RowMapper;

/**
 * Created by qyh on 2017/6/13.
 */
@AutoValue
public abstract class HockeyPlayer implements HockeyPlayerModel {

    public static final Factory<HockeyPlayer> FACTORY = new Factory<>(new Creator<HockeyPlayer>() {
        @Override public HockeyPlayer create(long _id, long player_number, String name) {
            return new AutoValue_HockeyPlayer(_id, player_number, name);
        }
    });

    public static final RowMapper<HockeyPlayer> SELECT_ALL_MAPPER = FACTORY.selectAllMapper();
}
