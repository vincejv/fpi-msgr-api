package com.abavilla.fpi.msgr.repo;

import com.abavilla.fpi.fw.repo.AbsMongoRepo;
import com.abavilla.fpi.msgr.entity.MsgrLog;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MsgrLogRepo extends AbsMongoRepo<MsgrLog> {
}
