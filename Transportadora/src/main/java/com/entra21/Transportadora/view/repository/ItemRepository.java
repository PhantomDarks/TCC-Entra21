package com.entra21.Transportadora.view.repository;
import com.entra21.Transportadora.model.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    public Optional<ItemEntity> findByLocalizador(String localizador);
    public Optional<Boolean> existsByLocalizador(String localizador);
}
