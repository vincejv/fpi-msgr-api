package com.abavilla.fpi.msgr.repo;

import javax.enterprise.context.ApplicationScoped;

import com.abavilla.fpi.fw.repo.AbsMongoRepo;
import com.abavilla.fpi.msgr.entity.TelegramLog;

@ApplicationScoped
public class TelegramLogRepo extends AbsMongoRepo<TelegramLog> {
}
