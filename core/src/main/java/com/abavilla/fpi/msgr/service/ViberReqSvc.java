package com.abavilla.fpi.msgr.service;

import javax.enterprise.context.ApplicationScoped;

import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.msgr.config.ViberApiKeyConfig;
import com.abavilla.fpi.msgr.entity.ViberLog;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import com.abavilla.fpi.msgr.mapper.ViberMsgReqMapper;
import com.abavilla.fpi.msgr.repo.ViberLogRepo;
import com.abavilla.fpi.msgr.rest.ViberApi;
import com.abavilla.fpi.viber.ext.dto.SendResponse;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ViberReqSvc extends MsgReqSvc<SendResponse,
  ViberLog, ViberLogRepo, ViberMsgReqMapper, ViberApiKeyConfig> {

  @RestClient
  ViberApi api;

  public Uni<SendResponse> postMsg(MsgrMsgReqDto msgReq, String fpiUser) {
    var msg = mapper.mapToMsgReq(msgReq);
    var log = mapper.mapToEntity(msg);

    log.setFpiSystem(identity.getPrincipal().getName());
    log.setFpiUser(fpiUser);
    log.setDateCreated(DateUtil.now());
    log.setDateUpdated(DateUtil.now());

    return repo.persist(log).chain(savedLog ->
      api.sendMessage(msg, apiKeyConfig.getAuthToken())
      .chain(resp -> {
        mapper.mapResponseToEntity(savedLog, resp);
        savedLog.setDateUpdated(DateUtil.now());
        return repo.update(savedLog).replaceWith(() -> resp);
      })
    );
  }

}
