package com.abavilla.fpi.msgr.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.abavilla.fpi.fw.entity.AbsItem;
import com.abavilla.fpi.fw.mapper.IMapper;
import com.abavilla.fpi.fw.repo.AbsMongoRepo;
import com.abavilla.fpi.fw.service.AbsRepoSvc;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public abstract class
MsgReqSvc<Q, I extends AbsItem, R extends AbsMongoRepo<I>,
  M extends IMapper, K> extends AbsRepoSvc<MsgrMsgReqDto, I, R> {

  @Inject
  K apiKeyConfig;

  /**
   * Mapper used for converting between {@link MsgrMsgReqDto} and {@link I} entity
   */
  @Inject
  M mapper;

  /**
   * OIDC Identity provider
   */
  @Inject
  SecurityIdentity identity;

  public abstract Uni<Q> postMsg(MsgrMsgReqDto msgReq, String fpiUser);

}
