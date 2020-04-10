package project.imaginarium.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.imaginarium.data.models.offers.Offer;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, String> {

    Optional<Offer> findByName(String name);
}
