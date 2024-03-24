package pro.yingsun.game.lock;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class LockSignature {

  String key;
  String threadName;
}
