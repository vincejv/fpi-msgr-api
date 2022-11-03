package com.abavilla.fpi.msgr.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.abavilla.fpi.fw.exceptions.ApiSvcEx;
import com.abavilla.fpi.fw.exceptions.FPISvcEx;
import com.abavilla.fpi.fw.service.AbsRepoSvc;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.meta.ext.dto.msgr.MsgrReqReply;
import com.abavilla.fpi.msgr.config.MetaApiKeyConfig;
import com.abavilla.fpi.msgr.entity.MsgrErrorApiResp;
import com.abavilla.fpi.msgr.entity.MsgrLog;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import com.abavilla.fpi.msgr.mapper.MsgrMsgReqMapper;
import com.abavilla.fpi.msgr.repo.MsgrLogRepo;
import io.quarkus.logging.Log;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
public class MsgrReqSvc extends AbsRepoSvc<MsgrMsgReqDto, MsgrLog, MsgrLogRepo> {

  @Inject
  MetaMsgrApiSvc metaMsgrApiSvc;

  /**
   * Mapper used for converting between {@link MsgrMsgReqDto} and {@link MsgrLog} entity
   */
  @Inject
  MsgrMsgReqMapper mapper;

  /**
   * OIDC Identity provider
   */
  @Inject
  SecurityIdentity identity;

  @Inject
  MetaApiKeyConfig metaApiKeyConfig;

  public Uni<MsgrReqReply> postMsg(MsgrMsgReqDto msgReq, String appSource) {
    var log = mapper.mapToEntity(msgReq);
    log.setRecipient(msgReq.getRecipient());
    log.setMsgContent(msgReq.getContent());
    log.setPageId(metaApiKeyConfig.getPageId());
    log.setFpiUser(identity.getPrincipal().getName());
    log.setFpiSystem(appSource);
    log.setDateCreated(DateUtil.now());
    log.setDateUpdated(DateUtil.now());

    return repo.persist(log)
      .chain(saved ->
        metaMsgrApiSvc.sendMsg(msgReq.getContent(), msgReq.getRecipient())
          .chain(resp-> {
            saved.setMetaMsgId(resp.getMid());
            log.setDateUpdated(DateUtil.now());
            return repo.update(saved);
          })
          .onFailure(ApiSvcEx.class).recoverWithUni(throwable -> {
            var apiEx = (ApiSvcEx) throwable;
            Log.error("metaMsgrApiSvc returned an error", apiEx);
            saved.setApiError(apiEx.getJsonResponse(MsgrErrorApiResp.class).getError());
            log.setDateUpdated(DateUtil.now());
            return repo.update(saved);
          })
      ).chain(saved -> {
        if (saved.getApiError() != null || StringUtils.isBlank(saved.getMetaMsgId())) {
          throw new FPISvcEx("Failed to send messenger message");
        }
        var reply = new MsgrReqReply();
        reply.setMid(saved.getMetaMsgId());
        reply.setRecipientId(saved.getRecipient());
        return Uni.createFrom().item(reply);
      });
  }

  public Uni<MsgrReqReply> toggleTyping(String recipient, boolean isTyping) {
    return metaMsgrApiSvc.sendTypingIndicator(recipient, isTyping)
      .onFailure().recoverWithUni(ex-> Uni.createFrom().failure(
        new FPISvcEx("Failed to update typing status", ex)));
  }

}
