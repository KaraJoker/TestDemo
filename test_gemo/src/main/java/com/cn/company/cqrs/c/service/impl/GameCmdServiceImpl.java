package com.cn.company.cqrs.c.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.anbang.qipai.daboluo.cqrs.c.domain.BianXingWanFa;
import com.anbang.qipai.daboluo.cqrs.c.domain.PukeGame;
import com.anbang.qipai.daboluo.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.daboluo.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.daboluo.cqrs.c.service.GameCmdService;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.WaitingStart;
import com.dml.mpgame.game.extend.fpmpv.back.OnlineGameBackStrategy;
import com.dml.mpgame.game.extend.vote.FinishedByVote;
import com.dml.mpgame.game.extend.vote.MostPlayersWinVoteCalculator;
import com.dml.mpgame.game.extend.vote.OnlineVotePlayersFilter;
import com.dml.mpgame.game.extend.vote.VoteOption;
import com.dml.mpgame.game.join.FixedNumberOfPlayersGameJoinStrategy;
import com.dml.mpgame.game.leave.HostGameLeaveStrategy;
import com.dml.mpgame.game.leave.NoPlayerCancelGameGameLeaveStrategy;
import com.dml.mpgame.game.leave.OfflineAndNotReadyGameLeaveStrategy;
import com.dml.mpgame.game.leave.OfflineGameLeaveStrategy;
import com.dml.mpgame.game.leave.PlayerGameLeaveStrategy;
import com.dml.mpgame.game.leave.PlayerLeaveCancelGameGameLeaveStrategy;
import com.dml.mpgame.game.player.PlayerFinished;
import com.dml.mpgame.game.ready.FixedNumberOfPlayersGameReadyStrategy;
import com.dml.mpgame.game.watch.WatcherMap;
import com.dml.mpgame.server.GameServer;
import com.dml.shisanshui.pan.PanActionFrame;
import org.springframework.util.CollectionUtils;

@Component
public class GameCmdServiceImpl extends CmdServiceBase implements GameCmdService {

	@Override
	public PukeGameValueObject newPukeGame(String gameId, String playerId, Integer panshu, Integer renshu, Boolean dqef,
			Boolean dqsf, BianXingWanFa bx, Boolean bihuase, Boolean zidongzupai, Boolean yitiaolong) {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);

		PukeGame newGame = new PukeGame();

		newGame.setPanshu(panshu);
		newGame.setRenshu(renshu);
		newGame.setFixedPlayerCount(renshu);
		newGame.setDqef(dqef);
		newGame.setDqsf(dqsf);
		newGame.setBx(bx);
		newGame.setBihuase(bihuase);
		newGame.setZidongzupai(zidongzupai);
		newGame.setYitiaolong(yitiaolong);

		newGame.setVotePlayersFilter(new OnlineVotePlayersFilter());

		newGame.setJoinStrategy(new FixedNumberOfPlayersGameJoinStrategy(renshu));
		newGame.setReadyStrategy(new FixedNumberOfPlayersGameReadyStrategy(renshu));

		newGame.setLeaveByOfflineStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByOfflineStrategyBeforeStart(new OfflineAndNotReadyGameLeaveStrategy());

		newGame.setLeaveByHangupStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByHangupStrategyBeforeStart(new OfflineAndNotReadyGameLeaveStrategy());

		newGame.setLeaveByPlayerStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByPlayerStrategyBeforeStart(new HostGameLeaveStrategy(playerId));

		newGame.setBackStrategy(new OnlineGameBackStrategy());
		newGame.create(gameId, playerId);
		newGame.updatePlayerPosition(playerId);
		gameServer.playerCreateGame(newGame, playerId);

		return new PukeGameValueObject(newGame);
	}

	@Override
	public PukeGameValueObject newPukeGameLeaveAndQuit(String gameId, String playerId, Integer panshu, Integer renshu,
			Boolean dqef, Boolean dqsf, BianXingWanFa bx, Boolean bihuase, Boolean zidongzupai, Boolean yitiaolong) {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);

		PukeGame newGame = new PukeGame();

		newGame.setPanshu(panshu);
		newGame.setRenshu(renshu);
		newGame.setFixedPlayerCount(renshu);
		newGame.setDqef(dqef);
		newGame.setDqsf(dqsf);
		newGame.setBx(bx);
		newGame.setBihuase(bihuase);
		newGame.setZidongzupai(zidongzupai);
		newGame.setYitiaolong(yitiaolong);

		newGame.setVotePlayersFilter(new OnlineVotePlayersFilter());

		newGame.setJoinStrategy(new FixedNumberOfPlayersGameJoinStrategy(renshu));
		newGame.setReadyStrategy(new FixedNumberOfPlayersGameReadyStrategy(renshu));

		newGame.setLeaveByOfflineStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByOfflineStrategyBeforeStart(new PlayerGameLeaveStrategy());

		newGame.setLeaveByHangupStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByHangupStrategyBeforeStart(new PlayerGameLeaveStrategy());

		newGame.setLeaveByPlayerStrategyAfterStart(new PlayerLeaveCancelGameGameLeaveStrategy());
		newGame.setLeaveByPlayerStrategyBeforeStart(new PlayerGameLeaveStrategy());

		newGame.setBackStrategy(new OnlineGameBackStrategy());
		newGame.create(gameId, playerId);
		newGame.updatePlayerPosition(playerId);
		gameServer.playerCreateGame(newGame, playerId);

		return new PukeGameValueObject(newGame);
	}

	@Override
	public PukeGameValueObject joinGame(String playerId, String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		gameServer.join(playerId, gameId);
		PukeGame newGame = (PukeGame) gameServer.findGame(gameId);
		newGame.updatePlayerPosition(playerId);
		return new PukeGameValueObject(newGame);
	}

	@Override
	public PukeGameValueObject leaveGameByHangup(String playerId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		Game game = gameServer.findGamePlayerPlaying(playerId);
		((PukeGame) game).updatePlayerPosition(playerId);
		PukeGameValueObject pukeGameValueObject = gameServer.leaveByHangup(playerId);
		if (game.getState().name().equals(FinishedByVote.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
			gameServer.finishGame(game.getId());
		}
		return pukeGameValueObject;
	}

	@Override
	public PukeGameValueObject leaveGame(String playerId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		Game game = gameServer.findGamePlayerPlaying(playerId);
		((PukeGame) game).updatePlayerPosition(playerId);
		PukeGameValueObject pukeGameValueObject = gameServer.leaveByPlayer(playerId);
		if (game.getState().name().equals(FinishedByVote.name) || game.getState().name().equals(Finished.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
			gameServer.finishGame(game.getId());
		}
		return pukeGameValueObject;
	}

	@Override
	public PukeGameValueObject leaveGameByOffline(String playerId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		Game game = gameServer.findGamePlayerPlaying(playerId);
		((PukeGame) game).updatePlayerPosition(playerId);
		PukeGameValueObject pukeGameValueObject = gameServer.leaveByHangup(playerId);
		if (game.getState().name().equals(FinishedByVote.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
			gameServer.finishGame(game.getId());
		}
		return pukeGameValueObject;
	}

	@Override
	public PukeGameValueObject backToGame(String playerId, String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		return gameServer.back(playerId, gameId);
	}

	@Override
	public ReadyForGameResult readyForGame(String playerId, Long currentTime) throws Exception {
		ReadyForGameResult result = new ReadyForGameResult();
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGameValueObject pukeGameValueObject = gameServer.ready(playerId, currentTime);
		result.setPukeGame(pukeGameValueObject);
		if (pukeGameValueObject.getState().name().equals(Playing.name)) {
			PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
			PanActionFrame firstActionFrame = pukeGame.createJuAndStartFirstPan(currentTime);
			result.setFirstActionFrame(firstActionFrame);
		}
		return result;
	}

	@Override
	public ReadyForGameResult cancelReadyForGame(String playerId, Long currentTime) throws Exception {
		ReadyForGameResult result = new ReadyForGameResult();
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGameValueObject pukeGameValueObject = gameServer.cancelReady(playerId, currentTime);
		result.setPukeGame(pukeGameValueObject);
		return result;
	}

	@Override
	public PukeGameValueObject finish(String playerId, Long currentTime) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
		// 在准备阶段不会发起投票
		if (pukeGame.getState().name().equals(WaitingStart.name)) {
			// 是主机的话直接解散，不是的话自己走人
			if (pukeGame.getCreatePlayerId().equals(playerId)) {
				pukeGame.cancel();
				gameServer.finishGame(pukeGame.getId());
			} else {
				pukeGame.quit(playerId);
			}
		} else {
			pukeGame.launchVoteToFinish(playerId, new MostPlayersWinVoteCalculator(), currentTime, 99000);
			pukeGame.voteToFinish(playerId, VoteOption.yes);
		}

		if (pukeGame.getState().name().equals(FinishedByVote.name)) {
			gameServer.finishGame(pukeGame.getId());
		}
		return new PukeGameValueObject(pukeGame);
	}

	@Override
	public PukeGameValueObject voteToFinish(String playerId, Boolean yes) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
		if (yes) {
			pukeGame.voteToFinish(playerId, VoteOption.yes);
		} else {
			pukeGame.voteToFinish(playerId, VoteOption.no);
		}

		if (pukeGame.getState().name().equals(FinishedByVote.name)) {
			gameServer.finishGame(pukeGame.getId());
		}
		return new PukeGameValueObject(pukeGame);
	}

	@Override
	public PukeGameValueObject voteToFinishByTimeOver(String playerId, Long currentTime) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
		pukeGame.voteToFinishByTimeOver(currentTime);

		if (pukeGame.getState().name().equals(FinishedByVote.name)) {
			gameServer.finishGame(pukeGame.getId());
		}
		return new PukeGameValueObject(pukeGame);
	}

	@Override
	public PukeGameValueObject finishGameImmediately(String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		pukeGame.finish();
		pukeGame.setState(new Finished());
		pukeGame.updateAllPlayersState(new PlayerFinished());
		gameServer.finishGame(gameId);
		return new PukeGameValueObject(pukeGame);
	}

	@Override
	public void bindPlayer(String playerId, String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		gameServer.bindPlayer(playerId, gameId);
	}

	@Override
	public PukeGameValueObject joinWatch(String playerId, String nickName, String headimgurl, String gameId)
			throws Exception {
		WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
		watcherMap.join(playerId, nickName, headimgurl, gameId);
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGameValueObject majiangGameValueObject = gameServer.getInfo(playerId, gameId);
		return majiangGameValueObject;
	}

	@Override
	public PukeGameValueObject leaveWatch(String playerId, String gameId) throws Exception {
		WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
		watcherMap.leave(playerId, gameId);
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGameValueObject majiangGameValueObject = gameServer.getInfo(playerId, gameId);
		return majiangGameValueObject;
	}

	@Override
	public Map getwatch(String gameId) {
		WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
		return watcherMap.getWatch(gameId);
	}

	@Override
	public void recycleWatch(String gameId) {
		WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
		watcherMap.recycleWatch(gameId);
	}

	@Override
	public PukeGameValueObject newMajiangGamePlayerLeaveAndQuit(String gameId, String playerId, Integer panshu,
			Integer renshu, Boolean dqef, Boolean dqsf, BianXingWanFa bx, Boolean bihuase, Boolean zidongzupai,
			Boolean yitiaolong) {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);

		PukeGame newGame = new PukeGame();

		newGame.setPanshu(panshu);
		newGame.setRenshu(renshu);
		newGame.setFixedPlayerCount(renshu);
		newGame.setDqef(dqef);
		newGame.setDqsf(dqsf);
		newGame.setBx(bx);
		newGame.setBihuase(bihuase);
		newGame.setZidongzupai(zidongzupai);
		newGame.setYitiaolong(yitiaolong);

		newGame.setVotePlayersFilter(new OnlineVotePlayersFilter());

		newGame.setJoinStrategy(new FixedNumberOfPlayersGameJoinStrategy(renshu));
		newGame.setReadyStrategy(new FixedNumberOfPlayersGameReadyStrategy(renshu));

		newGame.setLeaveByOfflineStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByOfflineStrategyBeforeStart(new OfflineAndNotReadyGameLeaveStrategy());

		newGame.setLeaveByHangupStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByHangupStrategyBeforeStart(new OfflineAndNotReadyGameLeaveStrategy());

		newGame.setLeaveByPlayerStrategyAfterStart(new OfflineGameLeaveStrategy());
		newGame.setLeaveByPlayerStrategyBeforeStart(new NoPlayerCancelGameGameLeaveStrategy());

		newGame.setBackStrategy(new OnlineGameBackStrategy());
		newGame.create(gameId, playerId);
		newGame.updatePlayerPosition(playerId);
		gameServer.playerCreateGame(newGame, playerId);

		return new PukeGameValueObject(newGame);
	}

	@Override
	public Set<String> listGameId() {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		if (!CollectionUtils.isEmpty(gameServer.getGameIdGameMap())) {
			Set<String> stringSet = gameServer.getGameIdGameMap().keySet();
			Set<String> gameIdSet = new HashSet<>();
			for (String gameId : stringSet) {
				gameIdSet.add(gameId);
			}
			return gameIdSet;
		}
		return new HashSet<>();
	}

}
