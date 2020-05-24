package com.auction.game.repository;

import com.auction.game.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, String> {

    @Query("from ItemEntity i join i.holder h  where h.id = ?1 and i.id = ?2")
    ItemEntity findById(String userId, String id);

    @Query("from ItemEntity i where i.id = ?1")
    Optional<ItemEntity> findById(String id);

    @Query("from ItemEntity i join i.holder h where h.id = ?1")
    List<ItemEntity> myItems(String userId);

    @Query("from ItemEntity i join i.holder h where h.id = ?1 and i.title LIKE %?2%")
    List<ItemEntity> findItemsByTitle(String userId, String title);

    @Query("from ItemEntity i join i.holder h where h.id = ?1 and i.description LIKE %?2%")
    List<ItemEntity> findItemsByDescription(String userId, String description);

    @Query("from ItemEntity i join i.holder h where h.id = ?1 and i.title LIKE %?2% and i.description LIKE %?3%")
    List<ItemEntity> findItemsByTitleAndDescription(String userId, String title, String description);

    @Query("from ItemEntity i where i.title LIKE %?1%")
    List<ItemEntity> findItemsByTitle(String title);

    @Query("from ItemEntity i where i.description LIKE %?1%")
    List<ItemEntity> findItemsByDescription(String description);

    @Query("from ItemEntity i where i.title LIKE %?1% and i.description LIKE %?2%")
    List<ItemEntity> findItemsByTitleAndDescription(String title, String description);

    @Query(value = "SELECT * FROM t_item ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<ItemEntity> findAllWithLimit(int limit);

    @Query("select count(i) > 0 from ItemEntity i join i.holder h where i.id = ?1 and h.id = ?2")
    boolean isUserOwner(String id, String userId);
}
