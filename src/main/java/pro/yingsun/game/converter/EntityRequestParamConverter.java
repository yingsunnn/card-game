package pro.yingsun.game.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pro.yingsun.game.enumeration.EventEntity;

@Component
public class EntityRequestParamConverter implements Converter<String, EventEntity> {

  @Override
  public EventEntity convert(String source) {
    return EventEntity.from(source);
  }
}
