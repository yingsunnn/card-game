package pro.yingsun.game.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.yingsun.game.dto.Game;
import pro.yingsun.game.dto.GameCreation;
import pro.yingsun.game.lock.DistributedLock;
import pro.yingsun.game.lock.LockKey;
import pro.yingsun.game.lock.LockSignature;
import pro.yingsun.game.queue.EventProducer;
import pro.yingsun.game.respository.GameRepository;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class GameServiceTest {

  @Mock
  private GameRepository gameRepository;
  @Mock
  private DistributedLock lock;
  @Mock
  private EventProducer eventProducer;
  @InjectMocks
  private GameService gameService;

  @Test
  void testCreateGame_addedTwoDecksToShoe() {
    Game game = Game.builder().gameId("test_id").build();
    when(this.lock.lock(LockKey.GAMES)).thenReturn(Optional.of(LockSignature.builder().build()));
    when(this.gameRepository.createNewGame()).thenReturn(game);

    this.gameService.createGame(GameCreation.builder().shoeSize(2).build());

    assertThat(game.getShoe()).isNotEmpty().hasSize(104);
  }
}