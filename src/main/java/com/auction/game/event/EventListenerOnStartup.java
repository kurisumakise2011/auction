package com.auction.game.event;

import com.auction.game.entity.ItemMediaEntity;
import com.auction.game.propety.AutoStartProperty;
import com.auction.game.repository.ItemMediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Slf4j
@Component
public class EventListenerOnStartup {
    @Autowired
    private AutoStartProperty autoStartProperty;

    @Autowired
    private ItemMediaRepository itemMediaRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Application started, loading sliders");
        try {
            Arrays.stream(autoStartProperty.getSliders().split(","))
                    .map(url -> {
                        var entity = new ItemMediaEntity();
                        entity.setCategory("SLIDER");
                        entity.setMediaType("image/jpg");
                        entity.setUrl(url);
                        return entity;
                    }).forEach(
                    entity -> {
                        if (!itemMediaRepository.existsByUrlAndCategory(entity.getUrl(), entity.getCategory())) {
                            itemMediaRepository.save(entity);
                        }
                    });
        } catch (Exception e) {
            log.error("Error occurred while pre-initiating resources", e);
        }
    }

}
