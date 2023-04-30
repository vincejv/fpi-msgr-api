package com.abavilla.fpi.msgr.service;

import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.msgr.config.TelegramApiKeyConfig;
import com.abavilla.fpi.msgr.entity.TelegramLog;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import com.abavilla.fpi.msgr.mapper.TelegramMsgReqMapper;
import com.abavilla.fpi.msgr.repo.TelegramLogRepo;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@ApplicationScoped
public class TelegramReqSvc extends MsgReqSvc<
  SendResponse, TelegramLog, TelegramLogRepo, TelegramMsgReqMapper, TelegramApiKeyConfig> {

  TelegramBot telegramBot;

  public Uni<SendResponse> postMsg(MsgrMsgReqDto msgReq, String fpiUser) {
    var msg = new SendMessage(msgReq.getRecipient(), msgReq.getContent())
      .parseMode(ParseMode.HTML)
      .disableWebPagePreview(true)
      .disableNotification(false);

    if (StringUtils.isNotBlank(msgReq.getReplyTo())) {
      msg = msg.replyToMessageId(NumberUtils.toInt(msgReq.getReplyTo()));
    }

    var builtMsg = msg;
    var log = mapper.mapToEntity(msgReq);
    log.setFpiSystem(identity.getPrincipal().getName());
    log.setFpiUser(fpiUser);
    log.setDateCreated(DateUtil.now());
    log.setDateUpdated(DateUtil.now());

    return repo.persist(log).chain(savedLog ->
      Uni.createFrom().item(telegramBot.execute(builtMsg))
        .chain(resp -> {
          log.setMessageId(resp.message().messageId());
          log.setDateUpdated(DateUtil.now());
          return repo.update(log).replaceWith(() -> resp);
        })
    );
  }

  public Uni<BaseResponse> toggleTyping(String recipient) {
    var action = new SendChatAction(recipient, ChatAction.typing);
    return Uni.createFrom().item(telegramBot.execute(action));
  }

  @PostConstruct
  public void init() {
    telegramBot = new TelegramBot(apiKeyConfig.getAccessToken());
  }

  @PreDestroy
  public void destroy() {
    telegramBot.shutdown();
  }

}
