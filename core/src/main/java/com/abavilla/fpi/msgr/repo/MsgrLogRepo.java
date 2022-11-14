package com.abavilla.fpi.msgr.repo;

import javax.enterprise.context.ApplicationScoped;

import com.abavilla.fpi.fw.repo.AbsMongoRepo;
import com.abavilla.fpi.msgr.entity.MsgrLog;

@ApplicationScoped
public class MsgrLogRepo extends AbsMongoRepo<MsgrLog> {
}
