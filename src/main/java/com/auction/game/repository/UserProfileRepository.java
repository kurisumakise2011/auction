package com.auction.game.repository;

import com.auction.game.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {

    UserProfileEntity findByUsername(String username);

    UserProfileEntity findByEmail(String email);

    @Query("select u.id from UserProfileEntity u where u.username = ?1")
    String findIdByUsername(String username);

    @Modifying
    @Query("update ProfileSettingsEntity s set s.banned = ?1 where s.id = (select ss.id from UserProfileEntity u join u.settings ss where u.id = ?2)")
    int updateBanned(boolean banned, String id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Modifying
    @Query("update UserProfileEntity e set e.description = ?1 where e.id = ?2")
    int updateDescription(String description, String id);

    @Modifying
    @Query("update CredentialEntity c set c.password = ?1, c.algo = ?2 where c.id = ?3")
    int updateCredentials(String password, String algo, String id);

    @Modifying
    @Query("update UserProfileEntity e set e.image = ?1 where e.id = ?2")
    int updateImage(byte[] image, String id);

    @Query("select e.image from UserProfileEntity e where e.id = ?1")
    byte[] getImageById(String userId);
}
