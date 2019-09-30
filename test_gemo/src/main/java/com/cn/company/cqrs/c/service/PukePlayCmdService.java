package com.cn.company.cqrs.c.service;


public interface PukePlayCmdService {

	PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex,
                            Long actionTime) throws Exception;

	ReadyToNextPanResult readyToNextPan(String playerId) throws Exception;

}
