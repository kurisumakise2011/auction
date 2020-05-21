package com.auction.game.event;

import com.auction.game.converter.UserProfileConverter;
import com.auction.game.entity.AuctioneerEntity;
import com.auction.game.entity.CredentialEntity;
import com.auction.game.entity.ItemMediaEntity;
import com.auction.game.entity.ProfileSettingsEntity;
import com.auction.game.entity.UserProfileEntity;
import com.auction.game.model.UserRole;
import com.auction.game.propety.AutoStartProperty;
import com.auction.game.repository.ItemMediaRepository;
import com.auction.game.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class EventListenerOnStartup {
    @Autowired
    private AutoStartProperty autoStartProperty;

    @Autowired
    private ItemMediaRepository itemMediaRepository;

    @Autowired
    private UserProfileConverter userProfileConverter;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordEncoder encoder;

    @EventListener
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
                            itemMediaRepository.saveAndFlush(entity);
                        }
                    });
        } catch (Exception e) {
            log.error("Error occurred while pre-initiating resources", e);
        }

        UserProfileEntity root = new UserProfileEntity();
        root.setUsername(autoStartProperty.getAdmin());
        root.setEmail(autoStartProperty.getEmail());

        ProfileSettingsEntity settings = new ProfileSettingsEntity();
        settings.setRole(UserRole.ADMIN);
        settings.setBanned(false);
        root.setSettings(settings);

        CredentialEntity credential = new CredentialEntity();
        credential.setPassword(encoder.encode(autoStartProperty.getPassword()));
        credential.setAlgo("MD5");
        credential.setExpired(false);
        root.setCredential(credential);

        AuctioneerEntity auctioneerEntity = new AuctioneerEntity();
        auctioneerEntity.setId(root.getId());
        root.setAuctioneerEntity(auctioneerEntity);

        try {
            userProfileRepository.saveAndFlush(root);
            log.info("root user created");
        } catch (Exception e) {
            log.info("root already created");
        }
    }

}
