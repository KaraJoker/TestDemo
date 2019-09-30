package com.cn.company.cqrs.c.service.impl;

import org.springframework.stereotype.Component;

import com.anbang.qipai.daboluo.cqrs.c.domain.PukeGame;
import com.anbang.qipai.daboluo.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.daboluo.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.daboluo.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.daboluo.cqrs.c.service.PukePlayCmdService;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.player.PlayerNotInGameException;
import com.dml.mpgame.server.GameServer;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pan.PanActionFrame;

@Component
public class PukePlayCmdServiceImpl extends CmdServiceBase implements PukePlayCmdService {

	public Dao findDaoByGameIdAndPlayerIdAndIndex(String gameId, String playerId, String index) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		return pukeGame.findDaoByPlayerIdAndIndex(playerId, index);
	}

	@Override
	public PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex,
			Long actionTime) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		String gameId = gameServer.findBindGameId(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}

		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		PukeActionResult pukeActionResult = pukeGame.chupai(playerId, toudaoIndex, zhongdaoIndex, weidaoIndex,
				actionTime);

		if (pukeActionResult.getJuResult() != null) {// 全部结束
			gameServer.finishGame(gameId);
		}

		return pukeActionResult;
	}

	@Override
	public ReadyToNextPanResult readyToNextPan(String playerId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		String gameId = gameServer.findBindGameId(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);

		ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
		pukeGame.readyToNextPan(playerId);
		if (pukeGame.getState().name().equals(Playing.name)) {// 开始下一盘了
			PanActionFrame firstActionFrame = pukeGame.getJu().getCurrentPan().findLatestActionFrame();
			readyToNextPanResult.setFirstActionFrame(firstActionFrame);
		}
		readyToNextPanResult.setPukeGame(new PukeGameValueObject(pukeGame));
		return readyToNextPanResult;
	}

}
